package ru.spbau.mit.softwaredesign.roguelike.world

class FieldOfView(private val world: World) {
    private val tiles: Array<Array<Tile>> = Array(world.width) { Array(world.height) { Tile.unknown } }
    private val visible: Array<BooleanArray> = Array(world.width) { BooleanArray(world.height) }

    fun getTile(position: Point): Tile = tiles[position.x][position.y]

    fun isVisible(position: Point): Boolean =
        position.x in 0 until world.width && position.y in 0 until world.height && visible[position.x][position.y]

    fun update(view: Point, visionRadius: Int) {
        visible.forEach { it.fill(false) }
        for (x in -visionRadius until visionRadius) {
            for (y in -visionRadius until visionRadius) {
                if (x * x + y * y > visionRadius * visionRadius) {
                    continue
                }

                for (linePoint in Line(view, view + Point(x, y))) {
                    val tile = world.getTile(linePoint)
                    if (tile == Tile.bounds) {
                        break
                    }

                    tiles[linePoint.x][linePoint.y] = tile
                    visible[linePoint.x][linePoint.y] = true

                    if (tile == Tile.wall) {
                        break
                    }
                }
            }
        }
    }
}
