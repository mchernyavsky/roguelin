package ru.spbau.mit.softwaredesign.roguelike.screens

import asciiPanel.AsciiPanel
import ru.spbau.mit.softwaredesign.roguelike.creatures.Player
import ru.spbau.mit.softwaredesign.roguelike.items.Item

class DropScreen(terminal: AsciiPanel, player: Player) : InventoryScreen(terminal, player) {
    override val actionName: String = "drop"

    override fun isAcceptable(item: Item): Boolean = true

    override fun use(item: Item) = player.drop(item)
}
