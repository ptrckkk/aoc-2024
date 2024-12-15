package de.ptrckkk.aoc24


/**
 * A two-dimensional list where
 * - the outer elements denote the rows,
 * - the inner elements denote elements within that row,
 * - and the [Boolean] value is `true` when at that position is an obstacle.
 */
typealias AreaMap = List<List<Boolean>>
typealias MutableAreaMap = MutableList<MutableList<Boolean>>

/**
 * The current position of the guard (row and colum in that order).
 */
typealias Cell = Pair<Int, Int>

fun AreaMap.isObstacleAt(row: Int, column: Int): Boolean = this[row][column]

fun AreaMap.isWithinArea(row: Int, column: Int): Boolean {
    return row >= 0 && column >= 0 && row < this.size && column < this[row].size
}

private enum class GuardsWalkingDirection(private val direction: Pair<Int, Int>) {
    // Directions are in the form: row, column
    UP(Pair(-1, 0)),
    DOWN(Pair(1, 0)),
    LEFT(Pair(0, -1)),
    RIGHT(Pair(0, 1));

    fun yDirection(): Int = direction.first
    fun xDirection(): Int = direction.second
}

class PuzzleSolverDay06 : PerDayPuzzleSolver() {

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/6](https://adventofcode.com/2024/day/6).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleOne(pathToInputFile: String): Long {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val (roomMap, initialPosition) = buildRoomMapWithInitialGuardPosition(fileContent)
        val visitedCells = computeGuardsVisitedCell(roomMap, initialPosition)
        return visitedCells.first.size.toLong()
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/6#part2](https://adventofcode.com/2024/day/6#part2).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleTwo]
     */
    override fun solvePuzzleTwo(pathToInputFile: String): Long {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val (roomMap, initialPosition) = buildRoomMapWithInitialGuardPosition(fileContent)
        val mutableRoomMap = makeRoomMapMutable(roomMap)
        val visitedCellsWithoutInitialPosition = computeGuardsVisitedCell(roomMap, initialPosition).first.toMutableSet()
        visitedCellsWithoutInitialPosition.remove(initialPosition)
        val loopCount = visitedCellsWithoutInitialPosition.count { newObstaclePosition ->
            doesGuardEndInLoopWhenWalkingWithAdditionalObstacle(mutableRoomMap, newObstaclePosition, initialPosition)
        }

        return loopCount.toLong()
    }

    private fun buildRoomMapWithInitialGuardPosition(fileContent: List<String>): Pair<AreaMap, Cell> {
        var guardsStartCell: Cell? = null
        val roomMap = List(fileContent.size) { rowIndex ->
            List(fileContent[rowIndex].length) { columnIndex ->
                when (fileContent[rowIndex][columnIndex]) {
                    '.' -> false
                    '#' -> true
                    else -> {
                        // In the given inputs, the initial walking direction is always up
                        guardsStartCell = Cell(rowIndex, columnIndex)
                        false
                    }
                }
            }
        }

        if (guardsStartCell == null) {
            throw IllegalStateException("Could not determine the start position of the guard!")
        }

        return roomMap to guardsStartCell!!
    }

    private fun makeRoomMapMutable(roomMap: AreaMap): MutableAreaMap =
        MutableList(roomMap.size) { rowIndex ->
            roomMap[rowIndex].toMutableList()
        }

    private fun computeGuardsVisitedCell(
        roomMap: AreaMap, guardsStartCell: Cell
    ): Pair<Set<Pair<Int, Int>>, Boolean> {
        // Initial position is in form: row, column
        var y = guardsStartCell.first
        var x = guardsStartCell.second

        // By default, the guard always walks up (derived from the task description)
        var direction = GuardsWalkingDirection.UP

        // The initial position is visited by default
        val visitedCells = mutableSetOf(guardsStartCell)
        val visitedPositionsWithDirection = mutableSetOf(
            Triple(guardsStartCell.first, guardsStartCell.second, direction)
        )

        var isWithinArea = true
        var guardWalksInLoop = false

        while (isWithinArea && !guardWalksInLoop) {
            // Look-ahead
            val newY = y + direction.yDirection()
            val newX = x + direction.xDirection()
            if (roomMap.isWithinArea(newY, newX)) {
                if (roomMap.isObstacleAt(newY, newX)) {
                    // Obstacle => change direction (and continue walking in next iteration)
                    direction = when (direction) {
                        GuardsWalkingDirection.UP -> GuardsWalkingDirection.RIGHT
                        GuardsWalkingDirection.RIGHT -> GuardsWalkingDirection.DOWN
                        GuardsWalkingDirection.DOWN -> GuardsWalkingDirection.LEFT
                        else -> GuardsWalkingDirection.UP
                    }
                } else {
                    // No obstacle => do housekeeping and continue walking
                    visitedCells.add(newY to newX)

                    val positionsWithDirection = Triple(newY, newX, direction)
                    if (visitedPositionsWithDirection.contains(positionsWithDirection)) {
                        guardWalksInLoop = true
                        continue
                    }
                    visitedPositionsWithDirection.add(positionsWithDirection)

                    y = newY
                    x = newX
                }
            } else {
                isWithinArea = false
            }
        }

        return visitedCells to guardWalksInLoop
    }

    private fun doesGuardEndInLoopWhenWalkingWithAdditionalObstacle(
        roomMap: MutableAreaMap, positionObstacle: Cell, guardsStartCell: Cell
    ): Boolean {
        roomMap[positionObstacle.first][positionObstacle.second] = true
        val endsInLoop = computeGuardsVisitedCell(roomMap, guardsStartCell).second
        roomMap[positionObstacle.first][positionObstacle.second] = false
        return endsInLoop
    }

}