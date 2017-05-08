package ru.spbau.mit.softwaredesign.roguelike.screens

import asciiPanel.AsciiPanel
import ru.spbau.mit.softwaredesign.roguelike.creatures.Player
import ru.spbau.mit.softwaredesign.roguelike.items.Armor
import ru.spbau.mit.softwaredesign.roguelike.items.Item
import ru.spbau.mit.softwaredesign.roguelike.items.Weapon

class EquipScreen(terminal: AsciiPanel, player: Player) : InventoryScreen(terminal, player) {
    override val actionName: String = "equip"

    override fun isAcceptable(item: Item): Boolean = item is Armor || item is Weapon

    override fun use(item: Item) = when (item) {
        is Armor -> player.equipArmor(item)
        is Weapon -> player.equipWeapon(item)
        else -> Unit
    }
}
