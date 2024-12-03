package de.ptrckkk.aoc24

class PuzzleSolverDay03 : PerDayPuzzleSolver() {

    companion object {
        private val MUL_REG_EX = """mul\(\d{1,3},\d{1,3}\)""".toRegex()
        private val MULTIPLICANDS_REX_EX = """\d{1,3}""".toRegex()
    }

    override fun solvePuzzleOne(pathToInputFile: String): Int {
        val fileContent = inputUtil.readContentOfResourceFile(pathToInputFile).joinToString("")
        val allMulOperations = extractAllMulOperations(fileContent)
        val sumOfMulOperations = allMulOperations.sumOf {
            val (multiplicand1, multiplicand2) = extractMultiplicandsFromMul(it)
            multiplicand1 * multiplicand2
        }
        return sumOfMulOperations
    }

    override fun solvePuzzleTwo(pathToInputFile: String): Int {
        TODO("Not yet implemented")
    }

    private fun extractAllMulOperations(content: String): List<String> {
        val allMulOperations = MUL_REG_EX.findAll(content)
        return allMulOperations.map { it.value }.toList()
    }

    private fun extractMultiplicandsFromMul(mulOperation: String): Pair<Int, Int> {
        val stringifiedMultiplicands = MULTIPLICANDS_REX_EX.findAll(mulOperation).map { it.value }.toList()
        if (stringifiedMultiplicands.size != 2) {
            println("Did not extract exactly two numbers from input '$mulOperation'! Returning a pair of zeros now!")
            return Pair(0, 0)
        }

        return Pair(stringifiedMultiplicands[0].toInt(), stringifiedMultiplicands[1].toInt())
    }

}
