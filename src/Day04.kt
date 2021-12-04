fun main() {
    val input = readInput("day04")

    solve(input)
}

private fun solve(input: List<String>) {
    val numbers = input[0].split(',').map { it.trim().toInt() }
    println(numbers)

    val bingoTables = createBingoTables(input)
    println("Number of Bingo tables: ${bingoTables.size}")

    for (number in numbers) {
        bingoTables.forEach {
            it.addNumber(number)
        }
    }
}

private fun createBingoTables(input: List<String>): MutableList<BingoTable> {
    val bingoTables = mutableListOf<BingoTable>()

    val bingoRows = mutableListOf<BingoRow>()
    for (i in 2 until input.size) {
        val inputRow = input[i]
        if (inputRow.isNotEmpty()) {
            val numbers = inputRow.split(" ").filter { it.trim().isNotEmpty() }
            val row = BingoRow(
                numbers[0].toInt(),
                numbers[1].toInt(),
                numbers[2].toInt(),
                numbers[3].toInt(),
                numbers[4].toInt()
            )
            bingoRows.add(row)
        }
        if(bingoRows.size == 5) {
            val rows = mutableListOf<BingoRow>()
            rows.addAll(bingoRows)
            val bingoTable = BingoTable(
                rows
            )
            bingoTables.add(bingoTable)
            bingoRows.clear()
        }
    }

    return bingoTables
}

data class BingoTable(
    val rows: List<BingoRow>
) {
    private var isSolved = false
    private var allNumbers: Set<Int> = mutableSetOf()
    private var correctNumbers: Set<Int> = mutableSetOf()

    fun addNumber(number: Int) {
        if (isSolved) {
            return
        }
        allNumbers = allNumbers.plus(number)
        rows.firstOrNull { it.containsNumber(number) }?.let {
            correctNumbers = correctNumbers.plus(number)
        }
        isSolved()
        if (isSolved) {
            println("BINGO! Winning Score: ${calculateWinningScore()}")
        }
    }

    private fun isSolved(): Boolean {
        //Horizontal Check
        val isHorizontalSolved = rows.firstOrNull { it.isSolved(allNumbers) } != null
        //Vertical Check
        val isVerticalSolved = isVerticalSolved(rows, allNumbers)
        isSolved = isHorizontalSolved || isVerticalSolved
        return isSolved
    }

    private fun isVerticalSolved(rows: List<BingoRow>, allNumbers: Set<Int>): Boolean {
        for (i in 1..5) {
            val verticalNumbers = rows.map { it.getNumberAtPosition(i) }
            val isSolved = allNumbers.containsAll(verticalNumbers)
            if (isSolved) {
                return true
            }
        }
        return false
    }

    private fun calculateWinningScore(): Int {
        val summ = summOfAllUnmarkedNumbers()
        val lastCorrectNumber = correctNumbers.last()
        return summ * lastCorrectNumber
    }

    private fun summOfAllUnmarkedNumbers(): Int {
        val numbers = rows.map { it.getNumbers() }.flatten()
        return numbers.filter { !correctNumbers.contains(it) }.sum()
    }
}

data class BingoRow(
    val number1: Int,
    val number2: Int,
    val number3: Int,
    val number4: Int,
    val number5: Int
) {

    fun isSolved(numbers: Set<Int>): Boolean {
        return numbers.containsAll(listOf(number1, number2, number3, number4, number5))
    }

    fun containsNumber(number: Int): Boolean {
        return listOf(number1, number2, number3, number4, number5).contains(number)
    }

    fun getNumberAtPosition(position: Int): Int {
        return when (position) {
            1 -> {
                number1
            }
            2 -> {
                number2
            }
            3 -> {
                number3
            }
            4 -> {
                number4
            }
            else -> {
                number5
            }
        }
    }

    fun getNumbers(): List<Int> {
        return listOf(number1, number2, number3, number4, number5)
    }

}




