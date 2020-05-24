package io.github.mchernyavsky.roguelin.creatures

import asciiPanel.AsciiPanel
import io.github.mchernyavsky.roguelin.world.World

class Zombie(world: World) : Enemy(world, 'z', AsciiPanel.green, "zombie", 30, 5, 7, 2) {
    override fun update() {
        val player = position.neighbors.map(world::getCreature).find { it is Player }

        if (player != null) {
            if (Math.random() > 0.5) {
                attack(player)
            }

            return
        }

        if (Math.random() > 0.75) {
            wander()
        }
    }
}
