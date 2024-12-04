package de.ptrckkk.aoc24

private typealias TwoDLetterGrid = List<List<Char>>

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
        findNumberOfXmas(letterGrid, WORD_TO_FIND)

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
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val letterGrid = fileContentTo2DArray(fileContent)
        return findNumberOfXmasCrosses(letterGrid)
    }

    /**
     * Transform the given [fileContent] into a two-dimensional letter grid/array by mapping each letter in
     * [fileContent] to an entry in the returned grid.
     */
    private fun fileContentTo2DArray(fileContent: List<String>): TwoDLetterGrid =
        List(fileContent.size) { lineIndex ->
            List(fileContent[lineIndex].length) { charIndex ->
                fileContent[lineIndex][charIndex]
            }
        }

    /**
     * Higher-order function for solving the puzzles.
     *
     * [resultCountFun] is applied for each and every cell/entry of [grid]. This function is responsible for  computing
     * the desired result count.
     */
    private fun iterateOverGridAndDetermineResultCount(
        grid: TwoDLetterGrid,
        resultCountFun: (rowIndex: Int, columnIndex: Int) -> Boolean
    ): Int {
        var resultCount = 0

        grid.indices.forEach { rowIndex ->
            grid[rowIndex].indices.forEach { columnIndex ->
                if (resultCountFun(rowIndex, columnIndex)) {
                    resultCount++
                }
            }
        }

        return resultCount
    }

    private fun transposeGrid(letterGrid: TwoDLetterGrid): TwoDLetterGrid {
        val cols = letterGrid[0].size
        val rows = letterGrid.size
        return List(cols) { j ->
            List(rows) { i ->
                letterGrid[i][j]
            }
        }
    }

    private fun getDiagonalLines(letterGrid: TwoDLetterGrid): List<List<Char>> {
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

    private fun findOccurrencesInLinesForward(letterGrid: TwoDLetterGrid): Int {
        var count = 0
        letterGrid.forEach { row ->
            val stringifiedRow = row.joinToString("")
            count += REG_EX_WORD_TO_FIND.findAll(stringifiedRow).count()
        }
        return count
    }

    private fun findOccurrencesInLinesBackward(letterGrid: TwoDLetterGrid): Int {
        var count = 0
        letterGrid.forEach { row ->
            val stringifiedRow = row.reversed().joinToString("")
            count += REG_EX_WORD_TO_FIND.findAll(stringifiedRow).count()
        }
        return count
    }

    private fun findNumberOfXmas(letterGrid: TwoDLetterGrid, wordToFind: String): Int {
        val firstLetterOfWordToFind = wordToFind[0]
        val reversedWordToFind = wordToFind.reversed()
        return iterateOverGridAndDetermineResultCount(letterGrid) { rowIndex, columnIndex ->
            if (letterGrid[rowIndex][columnIndex] == firstLetterOfWordToFind) {
                if (hasSearchWordToTheTop(letterGrid, wordToFind, rowIndex, columnIndex)) {
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
    }

    private fun hasSearchWordToTheTop(
        letterGrid: TwoDLetterGrid, wordToFind: String, rowOfFirstLetter: Int, columnOfFirstLetter: Int
    ): Boolean {
        for (offset in 1..<wordToFind.length) {
            val rowToCheck = rowOfFirstLetter - offset
            if (rowToCheck < 0 || letterGrid[rowToCheck][columnOfFirstLetter] != wordToFind[offset]) {
                return false
            }
        }

        return true
    }

    private fun hasSearchWordToTheTopLeft(
        letterGrid: TwoDLetterGrid, wordToFind: String, rowOfFirstLetter: Int, columnOfFirstLetter: Int
    ): Boolean {
        for (offset in 1..<wordToFind.length) {
            val rowToCheck = rowOfFirstLetter - offset
            val columnToCheck = columnOfFirstLetter - offset
            if (rowToCheck < 0 || columnToCheck < 0 ||
                letterGrid[rowToCheck][columnOfFirstLetter] != wordToFind[offset]
            ) {
                return false
            }
        }

        return true
    }

    private fun hasSearchWordToTheLeft(
        letterGrid: TwoDLetterGrid, wordToFind: String, rowOfFirstLetter: Int, columnOfFirstLetter: Int
    ): Boolean {
        for (offset in 1..<wordToFind.length) {
            val columnToCheck = columnOfFirstLetter - offset
            if (columnToCheck < 0 || letterGrid[rowOfFirstLetter][columnOfFirstLetter] != wordToFind[offset]) {
                return false
            }
        }

        return true
    }

    private fun hasSearchWordToTheBottomLeft(
        letterGrid: TwoDLetterGrid, wordToFind: String, rowOfFirstLetter: Int, columnOfFirstLetter: Int
    ): Boolean {
        for (offset in 1..<wordToFind.length) {
            val rowToCheck = rowOfFirstLetter + offset
            val columnToCheck = columnOfFirstLetter - offset
            if (rowToCheck >= letterGrid.size || columnToCheck < 0 ||
                letterGrid[rowToCheck][columnToCheck] != wordToFind[offset]
            ) {
                return false
            }
        }

        return true
    }

    private fun hasSearchWordToTheBottom(
        letterGrid: TwoDLetterGrid, wordToFind: String, rowOfFirstLetter: Int, columnOfFirstLetter: Int
    ): Boolean {
        for (offset in 1..<wordToFind.length) {
            val rowToCheck = rowOfFirstLetter + offset
            if (rowToCheck >= letterGrid.size || letterGrid[rowToCheck][columnOfFirstLetter] != wordToFind[offset]
            ) {
                return false
            }
        }

        return true
    }

    private fun hasSearchWordToTheBottomRight(
        letterGrid: TwoDLetterGrid, wordToFind: String, rowOfFirstLetter: Int, columnOfFirstLetter: Int
    ): Boolean {
        for (offset in 1..<wordToFind.length) {
            val rowToCheck = rowOfFirstLetter + offset
            val columnToCheck = columnOfFirstLetter + offset
            if (rowToCheck >= letterGrid.size || columnToCheck >= letterGrid[rowToCheck].size || letterGrid[rowOfFirstLetter][columnOfFirstLetter] != wordToFind[offset]
            ) {
                return false
            }
        }

        return true
    }

    /**
     * This function solves Puzzle 2 by counting the number of X-MAS crosses.
     */
    private fun findNumberOfXmasCrosses(letterGrid: TwoDLetterGrid): Int =
        iterateOverGridAndDetermineResultCount(letterGrid) { rowIndex, columnIndex ->
            if (rowIndex == 0 || rowIndex == letterGrid.size - 1 ||
                columnIndex == 0 || columnIndex == letterGrid[rowIndex].size - 1 ||
                letterGrid[rowIndex][columnIndex] != 'A'
            ) {
                false
            } else {
                // Check if we have the following constellations
                // M     M
                //    A
                // S     S
                if (letterGrid[rowIndex - 1][columnIndex - 1] == 'M' && letterGrid[rowIndex + 1][columnIndex + 1] == 'S' &&
                    letterGrid[rowIndex - 1][columnIndex + 1] == 'M' && letterGrid[rowIndex + 1][columnIndex - 1] == 'S'
                ) {
                    true
                }
                // M     S
                //    A
                // M     S
                else if (letterGrid[rowIndex - 1][columnIndex - 1] == 'M' && letterGrid[rowIndex + 1][columnIndex + 1] == 'S' &&
                    letterGrid[rowIndex - 1][columnIndex + 1] == 'S' && letterGrid[rowIndex + 1][columnIndex - 1] == 'M'
                ) {
                    true
                }
                // S     M
                //    A
                // S     M
                else if (letterGrid[rowIndex - 1][columnIndex - 1] == 'S' && letterGrid[rowIndex + 1][columnIndex + 1] == 'M' &&
                    letterGrid[rowIndex - 1][columnIndex + 1] == 'M' && letterGrid[rowIndex + 1][columnIndex - 1] == 'S'
                ) {
                    true
                }
                // S     S
                //    A
                // M     M
                else if (letterGrid[rowIndex - 1][columnIndex - 1] == 'S' && letterGrid[rowIndex + 1][columnIndex + 1] == 'M' &&
                    letterGrid[rowIndex - 1][columnIndex + 1] == 'S' && letterGrid[rowIndex + 1][columnIndex - 1] == 'M'
                ) {
                    true
                } else {
                    false
                }
            }
        }

}
