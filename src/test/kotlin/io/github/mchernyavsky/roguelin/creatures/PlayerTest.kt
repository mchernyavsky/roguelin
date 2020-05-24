package io.github.mchernyavsky.roguelin.creatures

import asciiPanel.AsciiPanel

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.github.mchernyavsky.roguelin.items.Armor
import io.github.mchernyavsky.roguelin.items.Food
import io.github.mchernyavsky.roguelin.items.Misc
import io.github.mchernyavsky.roguelin.items.Weapon
import io.github.mchernyavsky.roguelin.world.World

class PlayerTest {
    @Test
    fun constructorTest() {
        val player = World(90, 32).player
        assertEquals(player.glyph, '@')
        assertEquals(player.color, AsciiPanel.brightWhite)
        assertEquals(player.name, "player")
        assertEquals(player.maxHealth, 100)
        assertEquals(player.maxVisionRadius, 7)
        assertEquals(player.originAttack, 20)
        assertEquals(player.originDefense, 5)
        assertNull(player.armor)
        assertNull(player.weapon)
        assertEquals(player.maxFood, 1000)
        assertEquals(player.food, 1000 / 3 * 2)
    }

    @Test
    fun updateTest() {
        val player = World(90, 32).player
        val oldFood = player.food
        player.update()
        val newFood = player.food
        assertEquals(newFood, oldFood - 1)
    }

    @Test
    fun notifyTest() {
        val player = World(90, 32).player
        assertTrue(player.messages?.isEmpty() ?: true)
        player.notify("test")
        assertTrue(player.messages?.isNotEmpty() ?: true)
    }

    @Test
    fun canSeeTest() {
        val player = World(90, 32).player
        assertFalse(player.canSee(player.position))
    }

    @Test
    fun attackTest() {
        val player = World(90, 32).player
        player.attack(player)
        assertTrue(player.health >= player.maxHealth - (player.attack - player.defense))
    }

    @Test
    fun digTest() {
        val player = World(90, 32).player
        val oldFood = player.food
        player.dig(player.position)
        val newFood = player.food
        assertEquals(newFood, oldFood - 5)
    }

    @Test
    fun modifyFoodTest() {
        val player = World(90, 32).player
        player.modifyFood(2000)
        assertEquals(player.food, 1000)
        player.modifyFood(-1000)
        assertEquals(player.health, 0)
    }

    @Test
    fun eatTest() {
        val player = World(90, 32).player
        val fruit = Food.createFruit()
        val oldFood = player.food
        player.eat(fruit)
        assertEquals(player.food, oldFood + fruit.value)
    }

    @Test
    fun weaponTest() {
        val player = World(90, 32).player
        val claymore = Weapon.createClaymore()
        player.equipWeapon(claymore)
        assertEquals(player.attack, player.originAttack + claymore.attack)
        val dagger = Weapon.createDagger()
        player.equipWeapon(dagger)
        assertEquals(player.attack, player.originAttack + dagger.attack)
    }

    @Test
    fun armorTest() {
        val player = World(90, 32).player
        val heavy = Armor.createHeavyArmor()
        player.equipArmor(heavy)
        assertEquals(player.defense, player.originDefense + heavy.defense)
        val light = Armor.createLightArmor()
        player.equipArmor(light)
        assertEquals(player.defense, player.originDefense + light.defense)
    }

    @Test
    fun dropTest() {
        val world = World(90, 32)
        val player = world.player
        player.pickup()
        val item = Misc.createRock()
        world.dropItem(player.position, item)
        assertEquals(world.getItem(item.position), item)
    }
}
