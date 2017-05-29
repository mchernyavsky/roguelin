package ru.spbau.mit.softwaredesign.roguelike.screens

import asciiPanel.AsciiPanel
import ru.spbau.mit.softwaredesign.roguelike.creatures.Player
import ru.spbau.mit.softwaredesign.roguelike.world.Point
import ru.spbau.mit.softwaredesign.roguelike.world.Tile
import ru.spbau.mit.softwaredesign.roguelike.world.World
import java.awt.Color
import java.awt.event.KeyEvent
import kotlin.system.exitProcess

class PlayScreen(terminal: AsciiPanel) : Screen(terminal) {
    private var world: World = World(90, 32)
    private val player: Player = world.player

    private val width: Int = 80
    private val height: Int = 23

    private val messages: MutableList<String> = mutableListOf()
    private var subscreen: Screen? = null

    init {
        player.messages = messages
        player.update()
    }

    override fun repaint() {
        val left = Math.max(0, Math.min(player.position.x - width / 2, world.width - width))
        val top =  Math.max(0, Math.min(player.position.y - height / 2, world.height - height))
        val offset = Point(left, top)
        displayTiles(terminal, offset)
        displayMessages(terminal, messages)
        val stats = String.format(" %3d/%3d health %9s", player.health, player.maxHealth, hunger())
        terminal.write(stats, 1, 23)
        subscreen?.repaint()
    }

    override fun dispatchKeyEvent(key: KeyEvent): Screen? {
        if (subscreen != null) {
            subscreen = subscreen?.dispatchKeyEvent(key)
        } else {
            when (key.keyCode) {
                KeyEvent.VK_LEFT, KeyEvent.VK_H -> player.moveBy(Point(-1, 0))
                KeyEvent.VK_DOWN, KeyEvent.VK_J -> player.moveBy(Point(0, 1))
                KeyEvent.VK_UP, KeyEvent.VK_K -> player.moveBy(Point(0, -1))
                KeyEvent.VK_RIGHT, KeyEvent.VK_L -> player.moveBy(Point(1, 0))
                KeyEvent.VK_Y -> player.moveBy(Point(-1, -1))
                KeyEvent.VK_U -> player.moveBy(Point(1, -1))
                KeyEvent.VK_B -> player.moveBy(Point(-1, 1))
                KeyEvent.VK_N -> player.moveBy(Point(1, 1))

                KeyEvent.VK_D -> subscreen = DropScreen(terminal, player)
                KeyEvent.VK_E -> subscreen = EatScreen(terminal, player)
                KeyEvent.VK_W -> subscreen = EquipScreen(terminal, player)

                KeyEvent.VK_P -> player.pickup()
                KeyEvent.VK_Q -> if (playerIsTryingToExit()) return exit()

                KeyEvent.VK_ESCAPE -> exitProcess(0)
            }
        }

        if (subscreen == null) {
            world.update()
        }

        if (player.health < 1) {
            return LoseScreen(terminal)
        }

        return this
    }

    private fun hunger(): String =
            if (player.food < player.maxFood * 0.15) "Starving"
            else if (player.food < player.maxFood * 0.50) "Hungry"
            else if (player.food > player.maxFood * 0.85) "Full"
            else ""

    private fun displayTiles(terminal: AsciiPanel, offset: Point) {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val position = Point(x, y) + offset
                if (player.canSee(position)) {
                    terminal.write(world.getGlyph(position), x, y, world.getColor(position))
                } else {
                    terminal.write(player.fieldOfView.getTile(position).glyph, x, y, Color.darkGray)
                }
            }
        }
    }

    private fun displayMessages(terminal: AsciiPanel, messages: MutableList<String>) {
        val top = height - messages.size
        messages.forEachIndexed { index, message ->
            terminal.writeCenter(message, index + top)
        }

        messages.clear()
    }

    private fun playerIsTryingToExit(): Boolean = world.getTile(player.position) == Tile.EXIT

    private fun exit(): Screen {
        if (player.inventory.items.find { it.name == "Grail" } != null) {
            return WinScreen(terminal)
        }

        return LoseScreen(terminal)
    }
}
