package de.ptrckkk.aoc24.util

import java.io.IOException

/**
 * This class provides various helper methods regarding reading/processing the input of puzzles.
 */
class InputUtil {

    /**
     * Reads the content of a file located within the `resources` folder.
     *
     * For example if you wish to read "myfolder/myfile.txt", pass "myfolder/myfile.txt" to this function.
     *
     * Note that this function throws an [IOException] in case the file cannot be found or is not readable!
     */
    fun readContentOfResourceFile(fileName: String): List<String> {
        val resourceUrl = this::class.java.classLoader.getResource(fileName)
            ?: throw IOException("File '$fileName' does not exist in resource folder!")
        return resourceUrl.readText().lines()
    }

    /**
     * This function takes a list of strings, [lines], where each line is in the format
     * "{number1}{arbitrarily many spaces}{number2}". This function splits the two numbers and returns two lists
     * containing all "number ones" as the first element in the returned pair and all "number twos" as the second
     * element in the returned pair.
     */
    fun readTwoNumbersWhichAreSeparatedBySpaces(lines: List<String>): Pair<List<Int>, List<Int>> {
        val splitSpacesRexEx = "\\s+".toRegex()
        val listOfFirstValues = mutableListOf<Int>()
        val listOfSecondValues = mutableListOf<Int>()

        lines.forEach {
            val numbers = it.split(splitSpacesRexEx)
            listOfFirstValues.add(numbers[0].toInt())
            listOfSecondValues.add(numbers[1].toInt())
        }

        return Pair(listOfFirstValues, listOfSecondValues)
    }

}