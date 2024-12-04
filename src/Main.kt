import de.ptrckkk.aoc24.day02.SolverDay02

fun main() {
    // Day 02, first Puzzle
    val solverDay02 = SolverDay02()
    val expectedSampleResultDay2Puzzle1 = 2
    require(
        expectedSampleResultDay2Puzzle1 == solverDay02.solvePuzzle1ByDeterminingNumberOfSafeReports("example-input-day-02.txt"),
    ) { "Example solution for first puzzle of day 2 should be $expectedSampleResultDay2Puzzle1!" }
    println("Solution Day 2, Puzzle 1: " + solverDay02.solvePuzzle1ByDeterminingNumberOfSafeReports("input-day-02.txt")) // 369


    /*
    // Day 01, first Puzzle
    val solverDay01 = SolverDay01()
    val expectedSampleResultDay1Puzzle1 = 11
    require(
        expectedSampleResultDay1Puzzle1 == solverDay01.solvePuzzle1ByComputingTotalDistance("example-input-day-01.txt"),
    ) { "Example solution for first puzzle of day 1 should be $expectedSampleResultDay1Puzzle1!" }
    println("Solution Day 1, Puzzle 1: " + solverDay01.solvePuzzle1ByComputingTotalDistance("input-day-01.txt"))
    // Day 02, second Puzzle
    val expectedSampleResultDay1Puzzle2 = 31
    require(
        expectedSampleResultDay1Puzzle2 == solverDay01.solvePuzzle2ByComputingSimilarityScore("example-input-day-01.txt"),
    ) { "Example solution for seconds puzzle of day 1 should be $expectedSampleResultDay1Puzzle2!" }
    println("Solution Day 1, Puzzle 2: " + solverDay01.solvePuzzle2ByComputingSimilarityScore("input-day-01.txt"))
    */
}
