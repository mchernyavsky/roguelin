package ru.spbau.mit.softwaredesign.roguelike.screens

import asciiPanel.AsciiPanel

import java.awt.event.KeyEvent

class WinScreen(terminal: AsciiPanel) : Screen(terminal) {
    override fun repaint() {
        terminal.write("You won!", 1, 1)
        terminal.writeCenter("-- press [enter] to restart --", 22)
    }

    override fun dispatchKeyEvent(key: KeyEvent): Screen =
        if (key.keyCode == KeyEvent.VK_ENTER) PlayScreen(terminal) else this
}
