package io.github.mchernyavsky.roguelin.creatures

import io.github.mchernyavsky.roguelin.items.Armor
import io.github.mchernyavsky.roguelin.items.Weapon
import io.github.mchernyavsky.roguelin.world.Line
import io.github.mchernyavsky.roguelin.world.Point
import io.github.mchernyavsky.roguelin.world.World
import java.awt.Color

abstract class Enemy(
    world: World,
    glyph: Char, color: Color,
    name: String,
    maxHealth: Int,
    visionRadius: Int,
    originAttack: Int,
    originDefense: Int,
    armor: Armor? = null,
    weapon: Weapon? = null
) : Creature(
    world,
    glyph,
    color,
    name,
    maxHealth,
    visionRadius,
    originAttack,
    originDefense,
    armor,
    weapon
) {
    override fun notify(message: String) {}

    override fun canSee(otherPosition: Point): Boolean {
        val visionRadiusSquare = (position.x - otherPosition.x) * (position.x - otherPosition.x) +
                (position.y - otherPosition.y) * (position.y - otherPosition.y)
        if (visionRadiusSquare > maxVisionRadius * maxVisionRadius) {
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