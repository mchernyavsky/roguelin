package io.github.mchernyavsky.roguelin.creatures

import asciiPanel.AsciiPanel
import io.github.mchernyavsky.roguelin.world.World

class Bat(world: World) : Enemy(world, 'w', AsciiPanel.brightBlue, "bat", 15, 15, 5, 0) {
    override fun update() {
        wander()
        wander()
    }
}
