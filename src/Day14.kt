fun main() {
    val input = readInput("day14")

    solve(input)
    solve2(input)
}

private fun solve(input: List<String>) {
    var template = input.first()
    val newInput = input.drop(1)

    val instructionMap = mutableMapOf<String, String>()
    for (i in newInput) {
        if (i.isNotBlank()) {
            val s = i.split("->")
            instructionMap[s[0].trim()] = s[1].trim()
        }
    }
    println("Template: $template")
    println("Number of instructions: ${instructionMap.size}")

    for(i in 0..9) {
        template = doPairInsertion(template, instructionMap)
    }

    calculate(template)
}

fun calculate(template: String) {
    val charsMap = mutableMapOf<Char, Float>()
    template.forEach{
        charsMap[it] = charsMap.getOrDefault(it, 0f) + 1f
    }
    println(charsMap.toSortedMap())
    val max = charsMap.maxOf { it.value }
    val min = charsMap.minOf { it.value }
    println("Min: $min, Max: $max")
    println(max - min)
}

private fun doPairInsertion(template: String, instructionMap: MutableMap<String, String>): String {
    var temp = template.first().toString()
    for(c in 0..template.length - 2) {
        val pair = "${template[c]}${template[c+1]}"
        val instruction = instructionMap[pair]
        temp += if(instruction != null) {
            "$instruction${template[c+1]}"
        } else {
            template[c+1]
        }
    }
    return temp
}

private fun solve2(input: List<String>) {
    val template = input.first()
    val newInput = input.drop(1)

    val instructionMap = mutableMapOf<String, Char>()
    for (i in newInput) {
        if (i.isNotBlank()) {
            val s = i.split("->")
            instructionMap[s[0].trim()] = s[1].trim()[0]
        }
    }
    println("Template: $template")
    println("Number of instructions: ${instructionMap.size}")
    val charsMap = mutableMapOf<Char, Double>()
    template.forEach{
        charsMap[it] = charsMap.getOrDefault(it, 0.0) + 1.0
    }

    var map = mutableMapOf<String, Double>()
    for(c in 0..template.length - 2) {
        val pair = "${template[c]}${template[c+1]}"
        map[pair] = map.getOrDefault(pair, 0.0) + 1.0
    }

    for(i in 0..39) {
        val map2 = mutableMapOf<String, Double>()
        for (e in map) {
            val instruction = instructionMap[e.key]
            if (instruction != null) {
                charsMap[instruction] = charsMap.getOrDefault(instruction, 0.0) + e.value
                val newValue = "${e.key[0]}$instruction"
                map2[newValue] = map2.getOrDefault(newValue, 0.0) + e.value
                val newValue2 = "$instruction${e.key[1]}"
                map2[newValue2] = map2.getOrDefault(newValue2, 0.0) + e.value
            }
        }
        map = map2
    }

    println(charsMap.toSortedMap())
    val max = charsMap.maxOf { it.value }.toBigDecimal()
    val min = charsMap.minOf { it.value }.toBigDecimal()
    println("Min: $min, Max: $max")
    println((max - min).toPlainString())
}

