package de.ptrckkk.aoc24

import de.ptrckkk.aoc24.util.InputUtil

/**
 * This class facilitates solving both puzzles of each day.
 */
abstract class PerDayPuzzleSolver{

    protected val inputUtil = InputUtil()

    /**
     * Solves the first puzzle of a day using the provided [pathToInputFile].
     *
     * [pathToInputFile] is the path within the resource directory pointing to the input file, such as
     * "my-folder/input.txt".
     */
    abstract fun solvePuzzleOne(pathToInputFile: String): Int

    /**
     * Solves the second puzzle of a day using the provided [pathToInputFile].
     *
     * [pathToInputFile] is the path within the resource directory pointing to the input file, such as
     * "my-folder/input.txt".
     */
    abstract fun solvePuzzleTwo(pathToInputFile: String): Int

}
