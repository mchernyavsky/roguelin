package ru.spbau.mit.softwaredesign.roguelike.world

import java.awt.Color

class Tile private constructor(glyph: Char, color: Color) : GameObject(glyph, color) {
    val isGround: Boolean
        get() = this !== wall && this !== bounds

    val isDiggable: Boolean
        get() = this === wall

    companion object {
        val floor: Tile = Tile(250.toChar(), asciiPanel.AsciiPanel.yellow)
        val wall: Tile = Tile(177.toChar(), asciiPanel.AsciiPanel.yellow)
        val bounds: Tile = Tile('x', asciiPanel.AsciiPanel.brightBlack)
        val exit: Tile = Tile('<', asciiPanel.AsciiPanel.white)
        val unknown: Tile = Tile(' ', asciiPanel.AsciiPanel.white)
    }
}
