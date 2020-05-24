package ru.spbau.mit.softwaredesign.roguelike.creatures

import asciiPanel.AsciiPanel
import mu.KotlinLogging
import org.slf4j.Logger
import ru.spbau.mit.softwaredesign.roguelike.items.*
import ru.spbau.mit.softwaredesign.roguelike.world.FieldOfView
import ru.spbau.mit.softwaredesign.roguelike.world.Point
import ru.spbau.mit.softwaredesign.roguelike.world.World

class Player(world: World) : Creature(world, '@', AsciiPanel.brightWhite, "player", 100, 7, 20, 5) {
    private val logger: Logger = KotlinLogging.logger {}

    val fieldOfView: FieldOfView = FieldOfView(world)
    val inventory: Inventory = Inventory(20)
    val maxFood: Int = 1000
    var food: Int = maxFood / 3 * 2
    var messages: MutableList<String>? = null

    override fun update() {
        fieldOfView.update(position, maxVisionRadius)
        modifyFood(-1)
    }

    override fun notify(message: String) {
        messages?.add(message)
        logger.info(message)
    }

    override fun canSee(otherPosition: Point): Boolean = fieldOfView.isVisible(otherPosition)

    override fun move(newPosition: Point) {
        if (newPosition == position) {
            return
        }

        world.getCreature(newPosition)?.let {
            attack(it)
            return
        }

        val tile = world.getTile(newPosition)
        if (tile.isGround) {
            position = newPosition
            modifyFood(-1)
        } else if (tile.isDiggable) {
            dig(newPosition)
        }
    }

    override fun attack(other: Creature) {
        super.attack(other)
        modifyFood(-1)
    }

    override fun dig(position: Point) {
        super.dig(position)
        modifyFood(-5)
    }

    fun eat(food: Food) {
        inventory.remove(food)
        modifyFood(food.value)
        if (food.value < 0) {
            notify("Gross!")
        }
    }

    fun modifyFood(amount: Int) {
        food += amount

        if (food > maxFood) {
            food = maxFood
            notify("You can't believe your stomach can hold that much!")
            modifyHealth(-1)
        }

        if (food < 1) {
            modifyHealth(-health)
        }
    }

    fun equipArmor(armor: Armor) {
        unequipArmor()
        this.armor = armor
        notifyAll("equip a ${armor.name}")
    }

    fun unequipArmor() = armor?.let {
        armor = null
        notifyAll("unequip a ${it.name}")
    }

    fun equipWeapon(weapon: Weapon) {
        unequipWeapon()
        this.weapon = weapon
        notifyAll("equip a ${weapon.name}")
    }

    fun unequipWeapon() = weapon?.let {
        weapon = null
        notifyAll("unequip a ${it.name}")
    }

    fun pickup() {
        val item = world.getItem(position) ?: return
        if (inventory.isFull) {
            notify("Inventory is full.")
        } else {
            world.removeItem(item)
            inventory.add(item)
            notifyAll("pickup a ${item.name}")
        }
    }

    fun drop(item: Item) {
        if (world.dropItem(position, item)) {
            if (item === armor) {
                unequipArmor()
            } else if (item === weapon) {
                unequipWeapon()
            }

            inventory.remove(item)
            notifyAll("drop a ${item.name}")
        } else {
            notify("There's nowhere to drop the ${item.name}.")
        }
    }
}
