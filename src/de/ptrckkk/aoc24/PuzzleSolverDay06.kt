package de.ptrckkk.aoc24

enum class WalkingDirection {
    LEFT, RIGHT, UP, DOWN
}

/**
 * A two-dimensional list where
 * - the outer elements denote the rows,
 * - the inner elements denote elements within that row,
 * - and the [Boolean] value is `true` when at that position is an obstacle.
 */
typealias RoomMap = List<List<Boolean>>
/**
 * The current postion of the guard (row and colum indexes) as well as the [WalkingDirection].
 */
typealias Position = Triple<Int, Int, WalkingDirection>

class PuzzleSolverDay06 : PerDayPuzzleSolver() {

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/6](https://adventofcode.com/2024/day/6).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleOne(pathToInputFile: String): Long {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val (roomMap, initialPosition) = buildRoomMapWithInitialGuardPostion(fileContent)
        return letGuardWalkUntilLeavingAreaAndCountDistinctPositions(roomMap, initialPosition).toLong()
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/6#part2](https://adventofcode.com/2024/day/6#part2).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleTwo]
     */
    override fun solvePuzzleTwo(pathToInputFile: String): Long {
        TODO("Not yet implemented")
    }

    private fun buildRoomMapWithInitialGuardPostion(fileContent: List<String>): Pair<RoomMap, Position> {
        var initialPosition: Position? = null
        val roomMap = List(fileContent.size) { rowIndex ->
            List(fileContent[rowIndex].length) { columnIndex ->
                when (fileContent[rowIndex][columnIndex]) {
                    '.' -> false
                    '#' -> true
                    else -> {
                        // In the given inputs, the initial walking direction is always up
                        initialPosition = Position(rowIndex, columnIndex, WalkingDirection.UP)
                        false
                    }
                }
            }
        }

        if (initialPosition == null) {
            throw IllegalStateException("Could not determine the start position of the guard!")
        }

        return roomMap to initialPosition!!
    }

    private fun letGuardWalkUntilLeavingAreaAndCountDistinctPositions(
        roomMap: RoomMap, startPosition: Position
    ): Int {
        // The assumption is that the start position is within the area
        var hasLeftArea = false

        // Visited positions with row indexes as the key and column indexes as the value
        val distinctVisitedPositions = mutableMapOf<Int, Int>()
        distinctVisitedPositions[startPosition.first] = startPosition.second

        var currentPosition = startPosition
        while (!hasLeftArea) {
            currentPosition = computeNextPosition(currentPosition, roomMap)
            if (currentPosition.first >= 0 && currentPosition.second >= 0) {
                distinctVisitedPositions[currentPosition.first] = currentPosition.second
            } else {
                hasLeftArea = true
            }
        }

        return distinctVisitedPositions.size
    }

    private fun computeNextPosition(currentPosition: Position, roomMap: RoomMap): Position {
        val (currentRow, currentColumn, direction) = currentPosition

        if (direction == WalkingDirection.UP && (currentRow - 1 >= 0) && !roomMap[currentRow - 1][currentColumn]) {
            // Can walk up
            return Position(currentRow - 1, currentColumn, WalkingDirection.UP)
        } else if (direction == WalkingDirection.UP && (currentRow - 1 == -1)) {
            // Out-of area by walking up
            return Position(-1, currentColumn, WalkingDirection.UP)
        } else if (direction == WalkingDirection.UP && (currentRow - 1 >= 0) && roomMap[currentRow - 1][currentColumn]) {
            // Walk right as upwards is an obstacle
            return Position(currentRow, currentColumn + 1, WalkingDirection.RIGHT)
        } else if (direction == WalkingDirection.RIGHT && (currentColumn + 1 < roomMap[currentRow].size && !roomMap[currentRow - 1][currentColumn])) {
            // Can walk right
            return Position(currentRow, currentColumn + 1, WalkingDirection.RIGHT)
        } else if (direction == WalkingDirection.RIGHT && (currentColumn + 1 == roomMap[currentRow].size)) {
            // Out-of area by walking right
            return Position(currentRow, currentColumn + 1, WalkingDirection.RIGHT)
        } else if (direction == WalkingDirection.RIGHT && (currentColumn + 1 < roomMap[currentRow].size && roomMap[currentRow - 1][currentColumn])) {
            // Walk down as rightwards is an obstacle
            return Position(currentRow + 1, currentColumn, WalkingDirection.DOWN)
        } else if (direction == WalkingDirection.DOWN && (currentRow + 1 < roomMap.size && !roomMap[currentRow + 1][currentColumn])) {
            // Can walk down
            return Position(currentRow + 1, currentColumn, WalkingDirection.DOWN)
        } else if (direction == WalkingDirection.DOWN && (currentRow + 1 == roomMap.size)) {
            // Out-of-area by walking down
            return Position(currentRow + 1, currentColumn, WalkingDirection.DOWN)
        } else if (direction == WalkingDirection.DOWN && (currentRow + 1 < roomMap.size && roomMap[currentRow + 1][currentColumn])) {
            // Walk left as downwards is an obstacle
            return Position(currentRow, currentColumn - 1, WalkingDirection.LEFT)
        } else if (direction == WalkingDirection.LEFT && (currentColumn - 1 >= 0 && !roomMap[currentRow][currentColumn - 1])) {
            // Can walk left
            return Position(currentRow, currentColumn - 1, WalkingDirection.LEFT)
        } else if (direction == WalkingDirection.LEFT && (currentColumn - 1 == -1)) {
            // Out-of-area by walking left
            return Position(currentRow, -1, WalkingDirection.LEFT)
        } else if (direction == WalkingDirection.LEFT && (currentColumn - 1 >= 0 && roomMap[currentRow][currentColumn - 1])) {
            // Walk top as leftwards is an obstacle
            return Position(currentRow - 1, currentColumn, WalkingDirection.UP)
        } else {
            throw IllegalStateException("Case not caught! Very bad, Mr. programmer!")
        }
    }

}
