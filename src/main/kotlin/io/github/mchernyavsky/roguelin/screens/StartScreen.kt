package io.github.mchernyavsky.roguelin.screens

import asciiPanel.AsciiPanel

import java.awt.event.KeyEvent

class StartScreen(terminal: AsciiPanel) : Screen(terminal) {
    override fun repaint() {
        terminal.write("Roguelin", 1, 1)
        terminal.writeCenter("-- press [enter] to start --", 22)
    }

    override fun dispatchKeyEvent(key: KeyEvent): Screen =
        if (key.keyCode == KeyEvent.VK_ENTER) PlayScreen(terminal) else this
}
