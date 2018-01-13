package ru.spbau.mit.softwaredesign.roguelike.world

import ru.spbau.mit.softwaredesign.roguelike.creatures.Bat
import ru.spbau.mit.softwaredesign.roguelike.creatures.Creature
import ru.spbau.mit.softwaredesign.roguelike.creatures.Player
import ru.spbau.mit.softwaredesign.roguelike.creatures.Zombie
import ru.spbau.mit.softwaredesign.roguelike.items.*
import java.awt.Color


class World(val width: Int, val height: Int) {
    val player: Player = Player(this)
    private var tiles: Array<Array<Tile>> = Array(width) { Array(height) { Tile.UNKNOWN } }
    private val creatures: MutableList<Creature> = mutableListOf()
    private val items: MutableList<Item> = mutableListOf()

    init {
        WorldInitializer.initialize(this)
    }

    fun getGlyph(position: Point): Char = getGameObject(position).glyph

    fun getColor(position: Point): Color = getGameObject(position).color

    fun getTile(position: Point): Tile {
        if (position.x in 0 until width && position.y in 0 until height) {
            return tiles[position.x][position.y]
        }
        return Tile.BOUNDS
    }

    fun getCreature(position: Point): Creature? = creatures.find { it.position == position }

    fun getItem(position: Point): Item? = items.find { it.position == position }

    fun removeCreature(creature: Creature) = creatures.remove(creature)

    fun removeItem(item: Item) = items.remove(item)

    fun dig(position: Point) {
        if (getTile(position).isDiggable) {
            tiles[position.x][position.y] = Tile.FLOOR
        }
    }

    fun update() = creatures.forEach(Creature::update)

    fun putOnRandomEmptySpace(creature: Creature) {
        val occupiedCells = creatures.map { it.position }.filterNotNull().toSet()
        val point = getRandomEmptyPoint(occupiedCells)
        creature.position = point
        creatures.add(creature)
    }

    fun putOnRandomEmptySpace(item: Item) {
        val occupiedCells = items.map { it.position }.filterNotNull().toSet()
        val point = getRandomEmptyPoint(occupiedCells)
        item.position = point
        items.add(item)
    }

    fun dropItem(position: Point, item: Item): Boolean {
        val occupiedCells = items.map { it.position }.filterNotNull().toSet()
        val points: List<Point> = listOf(position, *position.neighbors)
        points.forEach {
            if (getTile(it).isGround && !occupiedCells.contains(it)) {
                item.position = it
                items.add(item)
                return true
            }
        }
        return false
    }

    private fun getGameObject(position: Point): GameObject {
        getCreature(position)?.let {
            return it
        }
        getItem(position)?.let {
            return it
        }
        return getTile(position)
    }

    private fun getRandomEmptyPoint(occupiedCells: Set<Point> = setOf()): Point {
        val randomPoints = generateSequence {
            Point((Math.random() * width).toInt(), (Math.random() * height).toInt())
        }
        return randomPoints.find { getTile(it).isGround && !occupiedCells.contains(it) }!!
    }

    companion object WorldInitializer {
        fun initialize(world: World) {
            randomizeTiles(world)
            addExit(world)
            addItems(world, 5, 5, 5, 100)
            addCreatures(world, 10, 10)
        }

        private fun randomizeTiles(world: World) {
            for (x in 0 until world.width) {
                for (y in 0 until world.height) {
                    world.tiles[x][y] = if (Math.random() < 0.7) Tile.FLOOR else Tile.WALL
                }
            }
        }

        private fun addExit(world: World) {
            val (x, y) = world.getRandomEmptyPoint()
            world.tiles[x][y] = Tile.EXIT
        }

        private fun addItems(world: World, numArmors: Int, numWeapons: Int, numFood: Int, numMiscs: Int) {
            repeat(numArmors) {
                world.putOnRandomEmptySpace(Armor.createRandomArmor())
            }
            repeat(numWeapons) {
                world.putOnRandomEmptySpace(Weapon.createRandomWeapon())
            }
            repeat(numFood) {
                world.putOnRandomEmptySpace(Food.createRandomFood())
            }
            repeat(numMiscs) {
                world.putOnRandomEmptySpace(Misc.createRock())
            }
            world.putOnRandomEmptySpace(Misc.createGrail())
        }

        private fun addCreatures(world: World, numBats: Int, numZombies: Int) {
            repeat(numBats) {
                world.putOnRandomEmptySpace(Bat(world))
            }
            repeat(numZombies) {
                world.putOnRandomEmptySpace(Zombie(world))
            }
            world.putOnRandomEmptySpace(world.player)
        }
    }
}
