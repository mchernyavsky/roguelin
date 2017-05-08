package ru.spbau.mit.softwaredesign.roguelike.items

import asciiPanel.AsciiPanel
import ru.spbau.mit.softwaredesign.roguelike.world.GameObject
import java.awt.Color

sealed class Item(glyph: Char, color: Color, val name: String) : GameObject(glyph, color)

class Armor(glyph: Char, color: Color, name: String, val defense: Int) : Item(glyph, color, name) {
    companion object Factory {
        fun createLightArmor(): Armor = Armor('[', AsciiPanel.brightBlack, "leathermail", 3)

        fun createMediumArmor(): Armor = Armor('[', AsciiPanel.white, "chainmail", 5)

        fun createHeavyArmor(): Armor = Armor('[', AsciiPanel.brightWhite, "platemail", 7)

        fun createRandomArmor(): Armor = when ((Math.random() * 3).toInt()) {
            0 -> createLightArmor()
            1 -> createMediumArmor()
            else -> createHeavyArmor()
        }
    }
}

class Weapon(glyph: Char, color: Color, name: String, val attack: Int) : Item(glyph, color, name) {
    companion object Factory {
        fun createDagger(): Weapon = Weapon(')', AsciiPanel.brightBlack, "dagger", 5)

        fun createSword(): Weapon = Weapon(')', AsciiPanel.white, "sword", 10)

        fun createClaymore(): Weapon = Weapon(')', AsciiPanel.brightWhite, "claymore", 15)

        fun createRandomWeapon(): Weapon = when ((Math.random() * 3).toInt()) {
            0 -> createDagger()
            1 -> createSword()
            else -> createClaymore()
        }
    }
}

class Food(glyph: Char, color: Color, name: String, val value: Int) : Item(glyph, color, name) {
    companion object Factory {
        fun createFruit(): Food = Food('f', AsciiPanel.green, "fruit", 100)

        fun createBread(): Food = Food('b', AsciiPanel.yellow, "bread", 200)

        fun createMeat(): Food = Food('m', AsciiPanel.red, "meat", 300)

        fun createRandomFood(): Food = when ((Math.random() * 3).toInt()) {
            0 -> createFruit()
            1 -> createBread()
            else -> createMeat()
        }
    }
}

class Misc(glyph: Char, color: Color, name: String) : Item(glyph, color, name) {
    companion object Factory {
        fun createRock(): Misc = Misc(',', AsciiPanel.brightBlack, "rock")

        fun createGrail(): Misc = Misc('G', AsciiPanel.brightYellow, "Grail")
    }
}
