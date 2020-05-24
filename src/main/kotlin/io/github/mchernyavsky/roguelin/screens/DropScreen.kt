package io.github.mchernyavsky.roguelin.screens

import asciiPanel.AsciiPanel
import io.github.mchernyavsky.roguelin.creatures.Player
import io.github.mchernyavsky.roguelin.items.Item

class DropScreen(terminal: AsciiPanel, player: Player) : InventoryScreen(terminal, player) {
    override val actionName: String = "drop"
    override fun isAcceptable(item: Item): Boolean = true
    override fun use(item: Item) = player.drop(item)
}
