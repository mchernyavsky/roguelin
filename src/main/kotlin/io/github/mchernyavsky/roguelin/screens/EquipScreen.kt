package io.github.mchernyavsky.roguelin.screens

import asciiPanel.AsciiPanel
import io.github.mchernyavsky.roguelin.creatures.Player
import io.github.mchernyavsky.roguelin.items.Armor
import io.github.mchernyavsky.roguelin.items.Item
import io.github.mchernyavsky.roguelin.items.Weapon

class EquipScreen(terminal: AsciiPanel, player: Player) : InventoryScreen(terminal, player) {
    override val actionName: String = "equip"
    override fun isAcceptable(item: Item): Boolean = item is Armor || item is Weapon
    override fun use(item: Item) {
        when {
            item is Armor && item == player.armor -> player.unequipArmor()
            item is Armor && item != player.armor -> player.equipArmor(item)
            item is Weapon && item == player.weapon -> player.unequipWeapon()
            item is Weapon && item != player.weapon -> player.equipWeapon(item)
            else -> Unit
        }
    }
}
