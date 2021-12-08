fun main() {
    val input = readInput("day08")

    //Part1
    solve(input)
    //Part2
    solve2(input)
}

private fun solve(input: List<String>) {
    println("Part1")
    val lines = input.map { it.split("|")[1] }.map { it.split(" ").filter { line -> line.trim().isNotEmpty() } }

    var summe = 0
    for (line in lines) {
        summe += line.count { isNumberAvaiable(it) }
    }

    println("Summe: $summe")
}

private fun solve2(input: List<String>) {
    println("Part2")
    val lines = input.map {
        it.split("|")
    }.map {
        val l = it.map { s ->
            s.split(" ").filter { line -> line.trim().isNotEmpty() }
        }
        l.flatten()
    }

    val outputs = mutableListOf<Int>()

    for (line in lines) {
        val numberMap = mutableMapOf<Int, String>()

        for (s in line) {
            determineNumber(s)?.let {
                numberMap.put(it.first, it.second)
            }
        }
        if (numberMap.size != 4) {
            println("ERROR: Not all numbers found!")
        }

        val three = line.first { it.length == 5 && containsChars(it, numberMap[7]!!, 3) }
        numberMap[3] = three

        val nine = line.first { it.length == 6 && containsChars(it, three, 5) && containsChars(it, numberMap[4]!!, 4) }
        numberMap[9] = nine

        val zero = line.first { it.length == 6 && containsChars(it, numberMap[1]!!, 2) && it != nine }
        numberMap[0] = zero

        val six = line.first { it.length == 6 && it != numberMap[1]!! && it != nine && it != zero }
        numberMap[6] = six

        val five = line.first { it.length == 5 && containsChars(it, six, 5) }
        numberMap[5] = five

        val two = line.first { it.length == 5 && containsChars(it, six, 4) && it != three }
        numberMap[2] = two

        val sublist = line.subList(line.size - 4, line.size)
        var output = ""
        for (s in sublist) {
            val num = numberMap.entries.firstOrNull { s.length == it.value.length && containsChars(s, it.value, s.length) }
            if(num == null) {
                println("Konnte keine Zahl für $s ermitteln")
            }
            output += num!!.key.toString()
        }
        outputs.add(output.toInt())
    }

    val totalOutput = outputs.sum()
    println("Total Output: $totalOutput")

}

private fun isNumberAvaiable(s: String): Boolean {
    return when (s.length) {
        2, 4, 3, 7 -> {
            true
        }
        else -> false
    }
}

private fun determineNumber(s: String): Pair<Int, String>? {
    return when (s.length) {
        2 -> {
            Pair(1, s)
        }
        4 -> {
            Pair(4, s)
        }
        3 -> {
            Pair(7, s)
        }
        7 -> {
            Pair(8, s)
        }
        else -> null
    }
}

private fun containsChars(s: String, sCompare: String, n: Int): Boolean {
    var correct = 0
    for (c in s.toCharArray()) {
        if (sCompare.contains(c)) {
            correct++
        }
    }
    return correct == n
}





