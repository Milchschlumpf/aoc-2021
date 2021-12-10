fun main() {
    val input = readInput("day10")

    solve(input)
}

private fun solve(input: List<String>) {
    val lines = input.map { it.trim().toList().map { c -> c.toString() } }

    val openChunks = listOf("(", "[", "{", "<")

    var summe = 0
    val incompleteLines = mutableListOf<MutableList<String>>()
    for ((i,v) in lines.withIndex()) {
        val openChunksInLine = mutableListOf<String>()

        var isIncomplete = true
        for (j in 0 until lines[i].size) {
            val currentValue = v[j]
            if(openChunks.contains(currentValue)) {
                openChunksInLine.add(currentValue)
            } else {
                val lastValue = openChunksInLine[openChunksInLine.size - 1]
                if(isCorrectEnding(lastValue, currentValue)) {
                    openChunksInLine.removeLast()
                } else {
                    isIncomplete = false
                    summe += getPoints(currentValue)
                    break
                }
            }
        }
        if(isIncomplete) {
            incompleteLines.add(openChunksInLine)
        }
    }

    println("Part1")
    println("Summe: $summe")

    println("Part2")
    println("Incompleted Lines: ${incompleteLines.size}")

    val totalPointsList = mutableListOf<Long>()
    for(incompleteLine in incompleteLines) {
        var totalPoints: Long = 0
        for(s in incompleteLine.reversed()) {
            val ending = getCorrectEnding(s)
            val point = getPointsPart2(ending)
            totalPoints = ( totalPoints * 5 ) + point
        }
        totalPointsList.add(totalPoints)
    }

    totalPointsList.sort()
    val middleScore = totalPointsList[( totalPointsList.size - 1 ) / 2]
    println("Middle Score: $middleScore")
}

private fun isCorrectEnding(lastValue: String, currentValue: String): Boolean {
    return when(lastValue) {
        "(" -> {
            currentValue == ")"
        }
        "[" -> {
            currentValue == "]"
        }
        "{" -> {
            currentValue == "}"
        }
        "<" -> {
            currentValue == ">"
        }
        else -> {
            println("Unknown char: $lastValue")
            false
        }
    }
}

private fun getPoints(s: String): Int {
    return when(s) {
        ")" -> {
            3
        }
        "]" -> {
            57
        }
        "}" -> {
            1197
        }
        ">" -> {
            25137
        }
        else -> {
            println("Unkown char: $s")
            0
        }
    }
}

private fun getCorrectEnding(value: String): String {
    return when(value) {
        "(" -> {
           ")"
        }
        "[" -> {
            "]"
        }
        "{" -> {
            "}"
        }
        "<" -> {
            ">"
        }
        else -> {
            println("Unknown char: $value")
           ""
        }
    }
}

private fun getPointsPart2(s: String): Int {
    return when(s) {
        ")" -> {
            1
        }
        "]" -> {
            2
        }
        "}" -> {
            3
        }
        ">" -> {
            4
        }
        else -> {
            println("Unkown char: $s")
            0
        }
    }
}




