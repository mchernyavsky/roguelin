package ru.spbau.mit.softwaredesign.roguelike.screens

import asciiPanel.AsciiPanel
import ru.spbau.mit.softwaredesign.roguelike.creatures.Player
import ru.spbau.mit.softwaredesign.roguelike.items.Armor
import ru.spbau.mit.softwaredesign.roguelike.items.Item
import ru.spbau.mit.softwaredesign.roguelike.items.Weapon

class EquipScreen(terminal: AsciiPanel, player: Player) : InventoryScreen(terminal, player) {
    override val actionName: String = "equip"

    override fun isAcceptable(item: Item): Boolean = item is Armor || item is Weapon

    override fun use(item: Item) {
        when (item) {
            is Armor -> if (item == player.armor) player.unequipArmor() else player.equipArmor(item)
            is Weapon -> if (item == player.weapon) player.unequipWeapon() else player.equipWeapon(item)
        }
    }
}
