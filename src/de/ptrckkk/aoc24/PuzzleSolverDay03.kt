package de.ptrckkk.aoc24

class PuzzleSolverDay03 : PerDayPuzzleSolver() {

    companion object {
        private val MUL_REG_EX = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
        private val MUL_DO_DONT_REG_EX = """(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))""".toRegex()
        private val MULTIPLICANDS_REX_EX = """\d{1,3}""".toRegex()
    }

    override fun solvePuzzleOne(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile).joinToString("")
        val allMulOperations = extractAllMulOperations(fileContent)
        val sumOfMulOperations = computeSumOfMuls(allMulOperations)
        return sumOfMulOperations
    }

    override fun solvePuzzleTwo(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile).joinToString("")
        val allMulDoDontOperations = extractAllMulDoAndDontOperations(fileContent)
        val allEnabledMulOperations = extractEnabledMulOperations(allMulDoDontOperations)
        val sumOfMulOperations = computeSumOfMuls(allEnabledMulOperations)
        return sumOfMulOperations
    }

    private fun extractAllMulOperations(content: String): List<String> {
        val allMulOperations = MUL_REG_EX.findAll(content)
        return allMulOperations.map { it.value }.toList()
    }

    private fun extractAllMulDoAndDontOperations(content: String): List<String> {
        val allOperations = MUL_DO_DONT_REG_EX.findAll(content)
        return allOperations.map { it.value }.toList()
    }

    private fun computeSumOfMuls(muls: List<String>): Int = muls.sumOf {
        val (multiplicand1, multiplicand2) = extractMultiplicandsFromMul(it)
        multiplicand1 * multiplicand2
    }

    private fun extractMultiplicandsFromMul(mulOperation: String): Pair<Int, Int> {
        val stringifiedMultiplicands = MULTIPLICANDS_REX_EX.findAll(mulOperation).map { it.value }.toList()
        if (stringifiedMultiplicands.size != 2) {
            println("Did not extract exactly two numbers from input '$mulOperation'! Returning a pair of zeros now!")
            return Pair(0, 0)
        }

        return Pair(stringifiedMultiplicands[0].toInt(), stringifiedMultiplicands[1].toInt())
    }

    private fun extractEnabledMulOperations(operations: List<String>): List<String> {
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
