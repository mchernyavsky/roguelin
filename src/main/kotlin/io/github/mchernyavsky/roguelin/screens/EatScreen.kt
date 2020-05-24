package io.github.mchernyavsky.roguelin.screens

import asciiPanel.AsciiPanel
import io.github.mchernyavsky.roguelin.creatures.Player
import io.github.mchernyavsky.roguelin.items.Food
import io.github.mchernyavsky.roguelin.items.Item

class EatScreen(terminal: AsciiPanel, player: Player) : InventoryScreen(terminal, player) {
    override val actionName: String = "eat"
    override fun isAcceptable(item: Item): Boolean = item is Food
    override fun use(item: Item) = if (item is Food) player.eat(item) else Unit
}
