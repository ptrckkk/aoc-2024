package de.ptrckkk.aoc24

import kotlin.math.abs

class PuzzleSolverDay02 : PerDayPuzzleSolver() {

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/2](https://adventofcode.com/2024/day/2).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleOne(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val reportList = inputUtil.readFileWithVaryingNumberOfNumbers(fileContent)
        return determineNumberOfSafeReports(reportList)
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/2#part2](https://adventofcode.com/2024/day/2#part2).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleTwo(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile)
        val reportList = inputUtil.readFileWithVaryingNumberOfNumbers(fileContent)
        return determineNumberOfSafeReportsWithDampener(reportList)
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

            val hasViolation = hasLevelViolation(report)
            if (hasViolation) {
                return@reportLoop
            }

            // All rules respected => safe report
            numberSafeReports++
        }

        return numberSafeReports
    }

    /**
     * For the given [reportList], this function determines the number of safe reports. A report is considered safe if
     * it respects the following rules:
     * 1. Rule 1: "The levels are either all increasing or all decreasing"
     * 1. Rule 2: "Any two adjacent levels differ by at least one and at most three"
     * 1. Rule 3: "If removing a single level from an unsafe report would make it safe, the report instead counts as
     *    safe"
     */
    private fun determineNumberOfSafeReportsWithDampener(reportList: List<List<Int>>): Int {
        var numberSafeReports = 0

        reportList.forEach reportLoop@{ report ->
            if (report.size == 1) {
                // Regard a report with a single level as safe
                numberSafeReports++
                return@reportLoop
            }

            val hasViolationBeforeAnyRemoval = hasLevelViolation(report)
            if (!hasViolationBeforeAnyRemoval) {
                numberSafeReports++
                return@reportLoop
            }

            // In a brute-force fashion, remove one element after the other. Note that there are more efficient ways to
            // achieve the same result, such as first finding problematic results
            var i = 0
            while (i < report.size) {
                val reportWithRemoval = report.toMutableList()
                reportWithRemoval.removeAt(i)
                if (!hasLevelViolation(reportWithRemoval)) {
                    numberSafeReports++
                    return@reportLoop
                }
                i++
            }
        }

        return numberSafeReports
    }

    /**
     * For the given [report], this function determines whether there is a violation - if there is a violation, `true`
     * is returned and `false` otherwise.
     */
    private fun hasLevelViolation(report: List<Int>): Boolean {
        val differenceFirstTwoLevels = report[0] - report[1]
        if (differenceFirstTwoLevels == 0) {
            // No difference => this report cannot be safe as Rule 1 is violated!
            return true
        }

        val levelsShouldBeIncreasing = differenceFirstTwoLevels < 0
        for (i in 0..<(report.size - 1)) {
            val difference = report[i] - report[i + 1]
            if ((levelsShouldBeIncreasing && difference > 0) || (!levelsShouldBeIncreasing && difference < 0)) {
                // Rule 1 violated
                return true
            }

            val absDifference = abs(difference)
            if (absDifference < 1 || absDifference > 3) {
                // Rule 2 violated
                return true
            }
        }

        return false
    }

}
