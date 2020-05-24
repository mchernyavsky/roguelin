package io.github.mchernyavsky.roguelin.world

import java.awt.Color

abstract class GameObject(val glyph: Char, val color: Color, var position: Point = Point(0, 0))
