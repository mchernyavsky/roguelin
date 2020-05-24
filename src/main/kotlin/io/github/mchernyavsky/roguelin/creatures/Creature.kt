package io.github.mchernyavsky.roguelin.creatures

import io.github.mchernyavsky.roguelin.items.Armor
import io.github.mchernyavsky.roguelin.items.Food
import io.github.mchernyavsky.roguelin.items.Weapon
import io.github.mchernyavsky.roguelin.world.GameObject
import io.github.mchernyavsky.roguelin.world.Point
import io.github.mchernyavsky.roguelin.world.World

import java.awt.Color
import kotlin.math.max

abstract class Creature(
    val world: World,
    glyph: Char, color: Color,
    val name: String,
    val maxHealth: Int,
    val maxVisionRadius: Int,
    val originAttack: Int, val originDefense: Int,
    armor: Armor? = null, weapon: Weapon? = null
) : GameObject(glyph, color) {
    var health: Int = maxHealth

    var armor: Armor? = armor
        protected set

    var weapon: Weapon? = weapon
        protected set

    val attack: Int
        get() = originAttack + (weapon?.attack ?: 0)

    val defense: Int
        get() = originDefense + (armor?.defense ?: 0)

    abstract fun update()

    abstract fun notify(message: String)

    fun notifyAll(message: String) {
        for (x in -maxVisionRadius until maxVisionRadius) {
            for (y in -maxVisionRadius until maxVisionRadius) {
                if (x * x + y * y > maxVisionRadius * maxVisionRadius) {
                    continue
                }

                val other = world.getCreature(position + Point(x, y)) ?: continue
                if (other === this) {
                    other.notify("You $message.")
                } else if (other.canSee(position)) {
                    other.notify(String.format("The $name ${makeSecondPersonView(message)}."))
                }
            }
        }
    }

    abstract fun canSee(otherPosition: Point): Boolean

    abstract fun move(newPosition: Point)

    fun moveBy(offset: Point) = move(position + offset)

    open fun attack(other: Creature) {
        val amount = (max(0, attack - other.defense) * Math.random()).toInt() + 1
        other.modifyHealth(-amount)
        notifyAll("attack the ${other.name} for $amount attack")
    }

    open fun dig(position: Point) {
        world.dig(position)
        notifyAll("dig")
    }

    fun modifyHealth(amount: Int) {
        health += amount
        if (health > maxHealth) {
            health = maxHealth
        } else if (health < 1) {
            die()
        }
    }

    private fun die() {
        world.removeCreature(this)
        val corpse = Food('%', color, "$name's corpse", maxHealth / 2)
        world.dropItem(position, corpse)
        notifyAll("die")
    }

    private fun makeSecondPersonView(text: String): String {
        val words = text.split(' ').toTypedArray()
        words[0] += "s"
        return words.joinToString(" ")
    }
}
