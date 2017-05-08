package ru.spbau.mit.softwaredesign.roguelike.world

import java.awt.Color

class Tile(glyph: Char, color: Color) : GameObject(glyph, color) {
    val isGround: Boolean
        get() = this != Tile.WALL && this != Tile.BOUNDS

    val isDiggable: Boolean
        get() = this == Tile.WALL

    companion object {
        val FLOOR = Tile(250.toChar(), asciiPanel.AsciiPanel.yellow)
        val WALL = Tile(177.toChar(), asciiPanel.AsciiPanel.yellow)
        val BOUNDS = Tile('x', asciiPanel.AsciiPanel.brightBlack)
        val EXIT = Tile('<', asciiPanel.AsciiPanel.white)
        val UNKNOWN = Tile(' ', asciiPanel.AsciiPanel.white)
    }
}

