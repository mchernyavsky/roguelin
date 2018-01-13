package ru.spbau.mit.softwaredesign.roguelike.screens

import asciiPanel.AsciiPanel
import java.awt.event.KeyEvent

abstract class Screen(protected val terminal: AsciiPanel) {
    abstract fun repaint()

    abstract fun dispatchKeyEvent(key: KeyEvent): Screen?
}
