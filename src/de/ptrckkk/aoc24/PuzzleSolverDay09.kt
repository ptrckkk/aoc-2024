package de.ptrckkk.aoc24

class PuzzleSolverDay09 : PerDayPuzzleSolver() {

    override fun solvePuzzleOne(pathToInputFile: String): Long {
        val inputLine = inputUtil.readContentOfResourceFile(pathToInputFile).first()
        val expandedBlocks = expandBlocks(inputLine)
        val expandedBlocksAfterShift = shiftBlocks(expandedBlocks)
        return computeChecksum(expandedBlocksAfterShift)
    }

    override fun solvePuzzleTwo(pathToInputFile: String): Long {
        TODO("Not yet implemented")
    }

    private fun expandBlocks(line: String): String {
        var isBlockPosition = true
        var index = 0
        val expandedBlocks = StringBuilder()

        line.map { char ->
            char.digitToInt()
        }.forEach { number ->
            val fillChar = if (isBlockPosition) index.toString() else  '.'
            for (i in 0..<number) {
                expandedBlocks.append(fillChar)
            }

            if (isBlockPosition) {
                index++
            }
            isBlockPosition = !isBlockPosition
        }

        return expandedBlocks.toString()
    }

    private fun shiftBlocks(expandedBlocks: String): String {
        val chars = expandedBlocks.toCharArray()
        var leftPointer = 0
        var rightPointer = chars.size - 1

        while (leftPointer < rightPointer) {
            var posNextDot = leftPointer
            while (chars[posNextDot] != '.' && posNextDot < rightPointer) {
                posNextDot++
            }

            var posNextNumber = rightPointer
            while (chars[posNextNumber] == '.' && posNextNumber > leftPointer) {
                posNextNumber--
            }

            if (posNextDot < posNextNumber) {
                chars[posNextDot] = chars[posNextNumber]
                chars[posNextNumber] = '.'
            }

            leftPointer = posNextDot + 1
            rightPointer = posNextNumber - 1
        }

        return chars.joinToString("")
    }

    private fun computeChecksum(shiftedBlocks: String): Long {
        return shiftedBlocks.toCharArray().filter { it != '.' }.mapIndexed { index, n ->
            (index * n.digitToInt()).toLong()
        }.sum()
    }

}
