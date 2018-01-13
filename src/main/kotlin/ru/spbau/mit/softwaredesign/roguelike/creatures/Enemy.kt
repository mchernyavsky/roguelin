package ru.spbau.mit.softwaredesign.roguelike.creatures

import ru.spbau.mit.softwaredesign.roguelike.items.Armor
import ru.spbau.mit.softwaredesign.roguelike.items.Weapon
import ru.spbau.mit.softwaredesign.roguelike.world.Line
import ru.spbau.mit.softwaredesign.roguelike.world.Point
import ru.spbau.mit.softwaredesign.roguelike.world.World
import java.awt.Color

abstract class Enemy(
        world: World,
        glyph: Char, color: Color,
        name: String,
        maxHealth: Int,
        visionRadius: Int,
        originAttack: Int, originDefense: Int,
        armor: Armor? = null, weapon: Weapon? = null
) : Creature(world, glyph, color, name, maxHealth, visionRadius,
             originAttack, originDefense, armor, weapon) {
    override fun notify(message: String) {}

    override fun canSee(otherPosition: Point): Boolean {
        if ((position.x - otherPosition.x) * (position.x - otherPosition.x)
                + (position.y - otherPosition.y) * (position.y - otherPosition.y) > visionRadius * visionRadius) {
            return false
        }

        for (linePoint in Line(position, otherPosition)) {
            if (world.getTile(linePoint).isGround || linePoint == otherPosition) {
                continue
            }
            return false
        }
        return true
    }

    override fun move(newPosition: Point) {
        if (newPosition == position) {
            return
        }

        world.getCreature(newPosition)?.let {
            if (it is Player) {
                attack(it)
            }
            return
        }

        val tile = world.getTile(newPosition)
        if (tile.isGround) {
            position = newPosition
        } else {
            notifyAll("bump into a wall")
        }
    }

    fun wander() {
        val x = (Math.random() * 3).toInt() - 1
        val y = (Math.random() * 3).toInt() - 1
        val newPosition = position + Point(x, y)
        if (world.getTile(newPosition).isGround) {
            move(newPosition)
        }
    }
}