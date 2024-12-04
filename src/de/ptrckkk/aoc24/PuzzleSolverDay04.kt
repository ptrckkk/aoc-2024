package de.ptrckkk.aoc24

private typealias TwoDGrid = List<List<Char>>

class PuzzleSolverDay04 : PerDayPuzzleSolver() {


    companion object {
        private const val WORD_TO_FIND = "XMAS"
        private val REG_EX_WORD_TO_FIND = WORD_TO_FIND.toRegex()
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/4](https://adventofcode.com/2024/day/4).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleOne(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        var totalCount = 0

        val letterGrid = fileContentTo2DArray(fileContent)
        totalCount += findOccurrencesInLinesForward(letterGrid)
        totalCount += findOccurrencesInLinesBackward(letterGrid)

        val transposedGrid = transposeGrid(letterGrid)
        totalCount += findOccurrencesInLinesForward(transposedGrid)
        totalCount += findOccurrencesInLinesBackward(transposedGrid)

        val diagonalizedGrid = getDiagonalLines(letterGrid)
        totalCount += findOccurrencesInLinesForward(diagonalizedGrid)
        totalCount += findOccurrencesInLinesBackward(diagonalizedGrid)

        return totalCount
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/4#part2](https://adventofcode.com/2024/day/4#part2).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleTwo]
     */
    override fun solvePuzzleTwo(pathToInputFile: String): Int {
        TODO("Not yet implemented")
    }

    private fun fileContentTo2DArray(fileContent: List<String>): TwoDGrid {
        val outerArray = mutableListOf<List<Char>>()
        fileContent.forEach { line ->
            val innerArray = mutableListOf<Char>()
            line.forEach { char ->
                innerArray.add(char)
            }
            outerArray.add(innerArray.toList())
        }
        return outerArray.toList()
    }

    private fun transposeGrid(letterGrid: TwoDGrid): TwoDGrid {
        val cols = letterGrid[0].size
        val rows = letterGrid.size
        return List(cols) { j ->
            List(rows) { i ->
                letterGrid[i][j]
            }
        }
    }

    private fun getDiagonalLines(letterGrid: TwoDGrid): List<List<Char>> {
        val diagonalLines = mutableListOf<List<Char>>()

        // Upper-right part from left to right
        var column = 0
        while (column < letterGrid[0].size) {
            val line = mutableListOf<Char>()
            line.add(letterGrid[0][column])
            var nextRow = 1
            var nextColumn = column + 1
            while (nextRow < letterGrid.size && nextColumn < letterGrid[nextRow].size) {
                line.add(letterGrid[nextRow][nextColumn])
                nextRow++
                nextColumn++
            }
            if (line.size >= WORD_TO_FIND.length) {
                diagonalLines.add(line)
            }
            column++
        }

        // Bottom-left part from left to right excluding the diagonal line at the very top which has already been
        // computed by "Upper-right part from left to right"
        var row = 1
        while (row < letterGrid.size) {
            val line = mutableListOf<Char>()
            line.add(letterGrid[row][0])
            var nextRow = row + 1
            var nextColumn = 1
            while (nextRow < letterGrid.size && nextColumn < letterGrid[nextRow].size) {
                line.add(letterGrid[nextRow][nextColumn])
                nextRow++
                nextColumn++
            }
            if (line.size >= WORD_TO_FIND.length) {
                diagonalLines.add(line)
            }
            row++
        }

        // Top-left part from left to right
        column = 0
        while (column < letterGrid[0].size) {
            val line = mutableListOf<Char>()
            line.add(letterGrid[0][column])
            var nextRow = 1
            var nextColumn = column - 1
            while (nextRow < letterGrid.size && nextColumn >= 0) {
                line.add(letterGrid[nextRow][nextColumn])
                nextRow++
                nextColumn--
            }
            if (line.size >= WORD_TO_FIND.length) {
                diagonalLines.add(line)
            }
            column++
        }

        // Bottom-right part from right to left, again excluding the one line already computed by
        // "Top-left part from left to right"
        row = letterGrid.size - 1
        column = letterGrid[0].size - 1
        while (row >= 1) {
            val line = mutableListOf<Char>()
            line.add(letterGrid[row][column])
            var nextRow = row + 1
            var nextColumn = column - 1
            while (nextRow < letterGrid.size && nextColumn >= 0) {
                line.add(letterGrid[nextRow][nextColumn])
                nextRow++
                nextColumn--
            }
            if (line.size >= WORD_TO_FIND.length) {
                diagonalLines.add(line)
            }
            row--
        }
        return diagonalLines.toList()
    }

    private fun findOccurrencesInLinesForward(letterGrid: TwoDGrid): Int {
        var count = 0
        letterGrid.forEach { row ->
            val stringifiedRow = row.joinToString("")
            count += REG_EX_WORD_TO_FIND.findAll(stringifiedRow).count()
        }
        return count
    }

    private fun findOccurrencesInLinesBackward(letterGrid: List<List<Char>>): Int {
        var count = 0
        letterGrid.forEach { row ->
            val stringifiedRow = row.reversed().joinToString("")
            count += REG_EX_WORD_TO_FIND.findAll(stringifiedRow).count()
        }
        return count
    }

}