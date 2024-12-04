package de.ptrckkk.aoc24.day02

import de.ptrckkk.aoc24.util.InputUtil
import kotlin.math.abs

class SolverDay02 {

    private val inputUtil = InputUtil()

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/1](https://adventofcode.com/2024/day/2).
     */
    fun solvePuzzle1ByDeterminingNumberOfSafeReports(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val reportList = inputUtil.readFileWithVaryingNumberOfNumbers(fileContent)
        return determineNumberOfSafeReports(reportList)
    }

    /**
     * For the given [reportList], this function determines the number of safe reports. A report is considered safe if
     * it respects the following rules:
     * 1. Rule 1: "The levels are either all increasing or all decreasing"
     * 1. Rule 2: "Any two adjacent levels differ by at least one and at most three"
     */
    private fun determineNumberOfSafeReports(reportList: List<List<Int>>): Int {
        var numberSafeReports = 0

        reportList.forEach reportLoop@{ report ->
            if (report.size == 1) {
                // Regard a report with a single level as safe
                numberSafeReports++
                return@reportLoop
            }

            val differenceFirstTwoLevels = report[0] - report[1]
            if (differenceFirstTwoLevels == 0) {
                // No difference => this report cannot be safe as Rule 1 is violated!
                return@reportLoop
            }

            val levelsShouldBeIncreasing = differenceFirstTwoLevels < 0
            for (i in 0..<(report.size - 1)) {
                val difference = report[i] - report[i + 1]
                if ((levelsShouldBeIncreasing && difference > 0) || (!levelsShouldBeIncreasing && difference < 0)) {
                    // Rule 1 violated
                    return@reportLoop
                }

                val absDifference = abs(difference)
                if (absDifference < 1 || absDifference > 3) {
                    // Rule 2 violated
                    return@reportLoop
                }
            }

            // All rules respected => safe report
            numberSafeReports++
        }

        return numberSafeReports
    }

}
