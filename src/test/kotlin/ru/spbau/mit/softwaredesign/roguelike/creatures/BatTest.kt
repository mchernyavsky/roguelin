package ru.spbau.mit.softwaredesign.roguelike.creatures

import asciiPanel.AsciiPanel

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.spbau.mit.softwaredesign.roguelike.world.World

internal class BatTest {
    @Test
    fun constructorTest() {
        val bat = Bat((World(90, 32)))
        assertEquals(bat.glyph, 'w')
        assertEquals(bat.color, AsciiPanel.brightBlue)
        assertEquals(bat.name, "bat")
        assertEquals(bat.maxHealth, 15)
        assertEquals(bat.maxVisionRadius, 15)
        assertEquals(bat.originAttack, 5)
        assertEquals(bat.originDefense, 0)
        assertNull(bat.armor)
        assertNull(bat.weapon)
    }

    @Test
    fun updateTest() {
        val bat = Bat((World(90, 32)))
        val oldPosition = bat.position
        bat.update()
        val newPosition = bat.position
        val offset = newPosition - oldPosition
        assertTrue(offset.x in -2..2)
        assertTrue(offset.y in -2..2)
    }
}