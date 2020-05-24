package ru.spbau.mit.softwaredesign.roguelike.screens

import asciiPanel.AsciiPanel
import ru.spbau.mit.softwaredesign.roguelike.creatures.Player
import ru.spbau.mit.softwaredesign.roguelike.items.Food
import ru.spbau.mit.softwaredesign.roguelike.items.Item

class EatScreen(terminal: AsciiPanel, player: Player) : InventoryScreen(terminal, player) {
    override val actionName: String = "eat"
    override fun isAcceptable(item: Item): Boolean = item is Food
    override fun use(item: Item) = if (item is Food) player.eat(item) else Unit
}
