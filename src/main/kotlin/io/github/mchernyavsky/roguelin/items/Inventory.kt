package io.github.mchernyavsky.roguelin.items

import java.util.*

class Inventory(val size: Int) {
    val items: LinkedList<Item> = LinkedList()

    val isFull: Boolean
        get() = items.size == size

    operator fun get(i: Int): Item? = items.getOrNull(i)

    fun add(item: Item) = if (isFull) Unit else items.addLast(item)

    fun remove(item: Item) = items.remove(item)
}
