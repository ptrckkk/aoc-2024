package de.ptrckkk.aoc24

import kotlin.math.abs

class PuzzleSolverDay01: PerDayPuzzleSolver() {

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/1](https://adventofcode.com/2024/day/1).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleOne(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val (list1, list2) = inputUtil.readTwoNumbersWhichAreSeparatedBySpaces(fileContent)
        val sortedList1 = list1.sorted()
        val sortedList2 = list2.sorted()
        return computeTotalDistancesBetweenLists(sortedList1, sortedList2)
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/1#part2](https://adventofcode.com/2024/day/1#part2).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleTwo]
     */
    override fun solvePuzzleTwo(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val (list1, list2) = inputUtil.readTwoNumbersWhichAreSeparatedBySpaces(fileContent)
        return computeSimilarityScoreForLists(list1, list2)
    }

    /**
     * Computes the sum of the distances of [list1] and [list2] element-wise.
     *
     * This function computes the result for the first puzzle.
     */
    private fun computeTotalDistancesBetweenLists(list1: List<Int>, list2: List<Int>): Int =
        list1.indices.sumOf { i -> abs(list1[i] - list2[i]) }

    /**
     * This function computes the result for the second puzzle.
     */
    private fun computeSimilarityScoreForLists(list1: List<Int>, list2: List<Int>): Int {
        // First, compute a map in linear time to have constant access times when computing the element-wise similarity
        // score
        val number2Count = mutableMapOf<Int, Int>()
        list2.forEach {
            number2Count[it] = number2Count.getOrDefault(it, 0) + 1
        }

        return list1.sumOf { numberInList1 ->
            numberInList1 * number2Count.getOrDefault(numberInList1, 0)
        }
    }

}