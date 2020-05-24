package ru.spbau.mit.softwaredesign.roguelike.world

class Line(from: Point, to: Point) : Iterable<Point> {
    private val points: List<Point>

    init {  // Bresenham's line algorithm
        val xDistance = Math.abs(to.x - from.x)
        val yDistance = Math.abs(to.y - from.y)
        val xStep = if (from.x < to.x) 1 else -1
        val yStep = if (from.y < to.y) 1 else -1

        var xCurrent = from.x
        var yCurrent = from.y
        var error = xDistance - yDistance

        points = mutableListOf(Point(xCurrent, yCurrent))
        while (xCurrent != to.x || yCurrent != to.y) {
            val doubleError = error * 2
            if (doubleError > -xDistance) {
                error -= yDistance
                xCurrent += xStep
            }
            if (doubleError < xDistance) {
                error += xDistance
                yCurrent += yStep
            }
            points.add(Point(xCurrent, yCurrent))
        }
    }

    override fun iterator(): Iterator<Point> {
        return points.iterator()
    }
}
