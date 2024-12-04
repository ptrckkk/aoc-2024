import de.ptrckkk.aoc24.PuzzleSolverDay01
import de.ptrckkk.aoc24.PuzzleSolverDay02
import de.ptrckkk.aoc24.PuzzleSolverDay03
import de.ptrckkk.aoc24.PuzzleSolverDay04

/**
 * Asserts that the [expected] value equals the [actual] value. If these values are not the same, the given [message]
 * is output along with the [expected] and [actual] values.
 */
fun assertResult(expected: Int, actual: Int, message: String) {
    require(
        expected == actual,
    ) { "$message! expected=$expected != actual=$actual" }
}

fun main() {
    // Day 4, example Puzzle 1
    val solverDay04 = PuzzleSolverDay04()
    val actualSampleResultDay4Puzzle1 = solverDay04.solvePuzzleOne("example-input-day-04.txt")
    assertResult(18, actualSampleResultDay4Puzzle1, "Solution for 'Example Day 4, Puzzle 1' wrong")

    // Day 4, Puzzle 1
    val actualResultDay4Puzzle1 = solverDay04.solvePuzzleOne("input-day-04.txt")
    assertResult(2500, actualResultDay4Puzzle1, "Solution for 'Day 4, Puzzle 1' wrong")

    // Day 4, example Puzzle 2
    val actualSampleResultDay4Puzzle2 = solverDay04.solvePuzzleTwo("example-input-day-04-puzzle-2.txt")
    assertResult(9, actualSampleResultDay4Puzzle2, "Solution for 'Example Day 4, Puzzle 2' wrong")

    // Day 4, Puzzle 2
    val actualResultDay4Puzzle2 = solverDay04.solvePuzzleTwo("input-day-04.txt")
    assertResult(1933, actualResultDay4Puzzle2, "Solution for 'Day 4, Puzzle 2' wrong")

    // Day 3, example Puzzle 1
    val solverDay03 = PuzzleSolverDay03()
    val actualSampleResultDay3Puzzle1 = solverDay03.solvePuzzleOne("example-input-day-03.txt")
    assertResult(161, actualSampleResultDay3Puzzle1, "Solution for 'Example Day 3, Puzzle 1' wrong")

    // Day 3, Puzzle 1
    val actualResultDay3Puzzle1 = solverDay03.solvePuzzleOne("input-day-03.txt")
    assertResult(191183308, actualResultDay3Puzzle1, "Solution for 'Day 3, Puzzle 1' wrong")

    // Day 3, example Puzzle 2
    val actualSampleResultDay3Puzzle2 = solverDay03.solvePuzzleTwo("example-input-day-03.txt")
    assertResult(48, actualSampleResultDay3Puzzle2, "Solution for 'Example Day 3, Puzzle 2' wrong")

    // Day 3, Puzzle 2
    val actualResultDay3Puzzle2 = solverDay03.solvePuzzleTwo("input-day-03.txt")
    assertResult(92082041, actualResultDay3Puzzle2, "Solution for 'Day 3, Puzzle 2' wrong")

    // Day 2, example Puzzle 1
    val solverDay02 = PuzzleSolverDay02()
    val actualSampleResultDay2Puzzle1 = solverDay02.solvePuzzleOne("example-input-day-02.txt")
    assertResult(2, actualSampleResultDay2Puzzle1, "Solution for 'Example Day 2, Puzzle 1' wrong")

    // Day 2, Puzzle 1
    val actualResultDay2Puzzle1 = solverDay02.solvePuzzleOne("input-day-02.txt")
    assertResult(369, actualResultDay2Puzzle1, "Solution for 'Day 2, Puzzle 1' wrong")

    // Day 2, example Puzzle 2
    val actualSampleResultDay2Puzzle2 = solverDay02.solvePuzzleTwo("example-input-day-02.txt")
    assertResult(4, actualSampleResultDay2Puzzle2, "Solution for 'Example Day 2, Puzzle 2' wrong")

    // Day 2, Puzzle 2
    val actualResultDay2Puzzle2 = solverDay02.solvePuzzleTwo("input-day-02.txt")
    assertResult(428, actualResultDay2Puzzle2, "Solution for 'Day 2, Puzzle 2' wrong")

    // Day 1, example Puzzle 1
    val solverDay01 = PuzzleSolverDay01()
    val actualSampleResultDay1Puzzle1 = solverDay01.solvePuzzleOne("example-input-day-01.txt")
    assertResult(11, actualSampleResultDay1Puzzle1, "Solution for 'Example Day 1, Puzzle 1' wrong")

    // Day 1, Puzzle 1
    val actualResultDay1Puzzle1 = solverDay01.solvePuzzleOne("input-day-01.txt")
    assertResult(1941353, actualResultDay1Puzzle1, "Solution for 'Day 1, Puzzle 1' wrong")

    // Day 1, example Puzzle 2
    val actualSampleResultDay1Puzzle2 = solverDay01.solvePuzzleTwo("example-input-day-01.txt")
    assertResult(31, actualSampleResultDay1Puzzle2, "Solution for 'Example Day 1, Puzzle 2' wrong")

    // Day 1, Puzzle 2
    val actualResultDay1Puzzle2 = solverDay01.solvePuzzleTwo("input-day-01.txt")
    assertResult(22539317, actualResultDay1Puzzle2, "Solution for 'Day 1, Puzzle 2' wrong")
}
