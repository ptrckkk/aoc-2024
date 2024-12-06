package de.ptrckkk.aoc24

class PuzzleSolverDay05: PerDayPuzzleSolver() {

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/5](https://adventofcode.com/2024/day/5).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleOne(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val (pageOrderingRules, pageNumbersOfUpdates) = splitInputContent(fileContent)

        val pageOrderingRulesMapping = buildPageOrderingRulesMapping(pageOrderingRules)
        val predecessorMapping = buildPredecessorMapping(pageOrderingRulesMapping)

        val correctlyOrderedUpdates = determineCorrectlyOrderedUpdates(pageNumbersOfUpdates, predecessorMapping)
        val middleElements = determineMiddleElements(correctlyOrderedUpdates)

        return middleElements.sum()
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/5#part2](https://adventofcode.com/2024/day/5#part2).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleTwo]
     */
    override fun solvePuzzleTwo(pathToInputFile: String): Int {
        TODO("Not yet implemented")
    }

    private fun splitInputContent(inputContent: List<String>): Pair<List<String>, List<String>> {
        val pageOrderingRules = mutableListOf<String>()
        val pageNumbersOfUpdates = mutableListOf<String>()

        var readingPageOrderingRules = true
        inputContent.forEach {
            if (it.isEmpty()) {
                readingPageOrderingRules = false
            } else if (readingPageOrderingRules) {
                pageOrderingRules.add(it)
            } else {
                pageNumbersOfUpdates.add(it)
            }
        }

        return Pair(pageOrderingRules.toList(), pageNumbersOfUpdates.toList())
    }

    private fun buildPageOrderingRulesMapping(pageOrderingRules: List<String>): Map<Int, List<Int>> {
        val mapping = mutableMapOf<Int, MutableList<Int>>()

        pageOrderingRules.forEach {
            val numbers = it.split("|").map { it.toInt() }
            // TODO: Improve by using apply
            if (mapping.containsKey(numbers[0])) {
                mapping[numbers[0]]!!.add(numbers[1])
            } else {
                mapping[numbers[0]] = mutableListOf(numbers[1])
            }
        }

        return mapping.toMap()
    }

    private fun buildPredecessorMapping(pageOrderingRulesMapping: Map<Int, List<Int>>): Map<Int, List<Int>> {
        val predecessorMapping = mutableMapOf<Int, MutableList<Int>>()

        pageOrderingRulesMapping.forEach { (key, value) ->
            value.forEach {
                if (predecessorMapping.containsKey(it)) {
                    predecessorMapping[it]!!.add(key)
                } else {
                    predecessorMapping[it] = mutableListOf(key)
                }
            }
        }

        return predecessorMapping.toMap()
    }

    private fun determineCorrectlyOrderedUpdates(
        pageNumbersOfUpdates: List<String>, predecessorMapping: Map<Int, List<Int>>
    ): List<List<Int>> {
        val correctlyOrderedUpdates = mutableListOf<List<Int>>()

        pageNumbersOfUpdates.forEach { stringifiedUpdateList ->
            val updateList = stringifiedUpdateList.split(",").map { it.toInt() }
            val isCorrectlyOrdered = updateList.subList(0, updateList.size - 1).indices.all { index ->
                doesXComeBeforeY(updateList[index], updateList[index + 1], predecessorMapping)
            }
            if (isCorrectlyOrdered) {
                correctlyOrderedUpdates.add(updateList)
            }
        }

        return correctlyOrderedUpdates
    }

    private fun doesXComeBeforeY(x: Int, y: Int, predecessorMapping: Map<Int, List<Int>>): Boolean {
        if (!predecessorMapping.containsKey(y)) {
            return false
        }

        val directPredecessorsOfY = predecessorMapping[y]!!
        return directPredecessorsOfY.contains(x)
    }

    private fun determineMiddleElements(correctlyOrderedUpdates: List<List<Int>>): List<Int> =
        correctlyOrderedUpdates.map {
            it[it.size / 2]
        }

}