package de.ptrckkk.aoc24

class PuzzleSolverDay03 : PerDayPuzzleSolver() {

    companion object {
        private val MUL_REG_EX = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
        private val MUL_DO_DONT_REG_EX = """(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))""".toRegex()
        private val MULTIPLICANDS_REX_EX = """\d{1,3}""".toRegex()
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/3](https://adventofcode.com/2024/day/3).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleOne(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile).joinToString("")
        val allMulOperations = extractAllMulOperations(fileContent)
        val sumOfMulOperations = computeSumOfMuls(allMulOperations)
        return sumOfMulOperations
    }

    /**
     * For the task description, see
     * [https://adventofcode.com/2024/day/3#part2](https://adventofcode.com/2024/day/3#part2).
     *
     * @see [PerDayPuzzleSolver.solvePuzzleOne]
     */
    override fun solvePuzzleTwo(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile).joinToString("")
        val allMulDoDontOperations = extractAllMulDoAndDontOperations(fileContent)
        val allEnabledMulOperations = extractEnabledMulOperations(allMulDoDontOperations)
        val sumOfMulOperations = computeSumOfMuls(allEnabledMulOperations)
        return sumOfMulOperations
    }

    /**
     * Extracts all **valid** `mul` operations from the given [content].
     *
     * Returned values are in the form "mul([number1],[number2])".
     */
    private fun extractAllMulOperations(content: String): List<String> {
        val allMulOperations = MUL_REG_EX.findAll(content)
        return allMulOperations.map { it.value }.toList()
    }

    /**
     * Extracts all **valid** `mul` operations, `do`, and `don't` operations from the given [content]).
     *
     * Note that `do` operations are also matched in case "do" are the final letters of an operation, such as in "undo".
     * However, this is fine according to the task description and leads to the correct solution!
     * Returned values are in the form "mul([number1],[number2])", "do()", "don't()".
     */
    private fun extractAllMulDoAndDontOperations(content: String): List<String> {
        val allOperations = MUL_DO_DONT_REG_EX.findAll(content)
        return allOperations.map { it.value }.toList()
    }

    /**
     * Given valid `mul` operations in the form "mul([number1],[number2])" this function computes the sum of the
     * multiplication of the given [muls].
     */
    private fun computeSumOfMuls(muls: List<String>): Int = muls.sumOf {
        val (multiplicand1, multiplicand2) = extractMultiplicandsFromMul(it)
        multiplicand1 * multiplicand2
    }

    /**
     * This function extracts the operands from the given valid [mulOperation]. That is, given
     * [mul]="mul([number1],[number2])", thus function returns the values for number1 and number2 in that order.
     */
    private fun extractMultiplicandsFromMul(mulOperation: String): Pair<Int, Int> {
        val stringifiedMultiplicands = MULTIPLICANDS_REX_EX.findAll(mulOperation).map { it.value }.toList()
        if (stringifiedMultiplicands.size != 2) {
            println("Did not extract exactly two numbers from input '$mulOperation'! Returning a pair of zeros now!")
            return Pair(0, 0)
        }

        return Pair(stringifiedMultiplicands[0].toInt(), stringifiedMultiplicands[1].toInt())
    }

    /**
     * Based on the given [operations], this function extracts all those `mul` operations which are actually to be
     * executed. [operations] can hold "mul([number1],[number2])", "do()", and "don't()" operations.
     */
    private fun extractEnabledMulOperations(operations: List<String>): List<String> {
        // As per the task description, "mul" operations prior to any "do" or "don`t" are to be executed
        var booleanExtractModeIsOn = true
        val enabledMulOperations = mutableListOf<String>()
        operations.forEach {
            when (it) {
                "do()" -> booleanExtractModeIsOn = true
                "don't()" -> booleanExtractModeIsOn = false
                else -> {
                    if (booleanExtractModeIsOn) {
                        enabledMulOperations.add(it)
                    }
                }
            }
        }
        return enabledMulOperations
    }

}
