package ru.spbau.mit.softwaredesign.roguelike.screens

import asciiPanel.AsciiPanel
import ru.spbau.mit.softwaredesign.roguelike.creatures.Player
import ru.spbau.mit.softwaredesign.roguelike.items.Item

import java.awt.event.KeyEvent

abstract class InventoryScreen(terminal: AsciiPanel, protected var player: Player) : Screen(terminal) {
    protected abstract val actionName: String

    private val letters: CharRange = 'a'..'z'

    override fun repaint() {
        val items = player.inventory.items.filter(this::isAcceptable)

        val x = 4
        val y = 23 - items.size

        if (items.isNotEmpty()) {
            terminal.clear(' ', x, y, 20, items.size)
        }

        items.zip(letters).mapIndexed { index, (item, letter) ->
            var line = "$letter - ${item.name}"
            if (item == player.weapon || item == player.armor) {
                line += " (equipped)"
            }
            terminal.write(line, x, y + index)
        }

        terminal.clear(' ', 0, 23, 80, 1)
        terminal.write("What would you like to $actionName?", 2, 23)
        terminal.repaint()
    }

    override fun dispatchKeyEvent(key: KeyEvent): Screen? {
        if (key.keyCode == KeyEvent.VK_ESCAPE) {
            return null
        }

        val index = letters.indexOf(key.keyChar)
        val items = player.inventory.items.filter(this::isAcceptable)
        val item = items.getOrNull(index)
        item?.let { use(item) }

        return this
    }

    protected abstract fun isAcceptable(item: Item): Boolean

    protected abstract fun use(item: Item)
}
