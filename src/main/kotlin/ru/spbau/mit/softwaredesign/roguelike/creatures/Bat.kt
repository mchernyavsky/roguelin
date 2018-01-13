package ru.spbau.mit.softwaredesign.roguelike.creatures

import asciiPanel.AsciiPanel
import ru.spbau.mit.softwaredesign.roguelike.world.World

class Bat(world: World) : Enemy(world, 'w', AsciiPanel.brightBlue, "bat", 15, 15, 5, 0) {
    override fun update() {
        wander()
        wander()
    }
}
