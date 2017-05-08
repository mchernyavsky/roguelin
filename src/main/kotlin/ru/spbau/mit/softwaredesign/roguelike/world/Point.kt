package ru.spbau.mit.softwaredesign.roguelike.world

data class Point(val x: Int, val y: Int) {
    val neighbors: Array<Point> by lazy {
        arrayOf(Point(x - 1, y - 1), Point(x, y - 1), Point(x + 1, y - 1),
                Point(x - 1, y),                      Point(x + 1, y),
                Point(x - 1, y + 1), Point(x, y + 1), Point(x + 1, y + 1))
    }

    operator fun plus(element: Point) = Point(x + element.x, y + element.y)

    operator fun minus(element: Point) = Point(x - element.x, y - element.y)
}