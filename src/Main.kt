import de.ptrckkk.aoc24.PuzzleSolverDay01
import de.ptrckkk.aoc24.PuzzleSolverDay02

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
