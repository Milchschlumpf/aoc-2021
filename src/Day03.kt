import util.binaryToDecimal

fun main() {
    val input = readInput("day03")

    val size = input.size
    val binaryInputSize = input[0].toCharArray().size - 1

    var gammaRateBits = ""
    for (i in 0..binaryInputSize) {
        var zeroBits = 0

        input.forEach { line ->
            val char = line[i]
            if(char == '0') {
                zeroBits++
            }
        }
        val oneBits = size - zeroBits
        gammaRateBits += if(zeroBits > oneBits) "0" else "1"
    }

    var epsilonRateBits = ""
    gammaRateBits.forEach {
        epsilonRateBits += if(it == '0') "1" else "0"
    }

    val gammaRate = binaryToDecimal(gammaRateBits)
    val epsilonRate = binaryToDecimal(epsilonRateBits)

    println("Gamme Rate in Bits: $gammaRateBits , Decimal: $gammaRate")
    println("Gamme Rate in Bits: $epsilonRateBits , Decimal: $epsilonRate")
    println("Power Consumption: ${gammaRate * epsilonRate}")


    //Part2
    solvePart2(input)
}

private fun solvePart2(input: List<String>) {
    println("Part2")

    val onxygenRatingBinary = findOxygenRating(input, 0)
    val onxygenRating = binaryToDecimal(onxygenRatingBinary)
    println("Oxygen Rating: $onxygenRatingBinary")
    val co2RatingBinary = findCO2Rating(input, 0)
    val co2Rating = binaryToDecimal(co2RatingBinary)
    println("CO2 Rating: $co2RatingBinary")

    println("Life Support Rating: ${onxygenRating.toInt() * co2Rating.toInt()}")
}

fun findOxygenRating(input: List<String>, index: Int): String {
    var zeroBits = 0
    var oneBits = 0

    if(input.size == 1) {
        return input[0]
    }

    val zeroBitInputs = mutableListOf<String>()
    val oneBitInputs = mutableListOf<String>()

    input.forEach { line ->
        val char = line[index]
        if(char == '0') {
            zeroBits++
            zeroBitInputs.add(line)
        } else {
            oneBits++
            oneBitInputs.add(line)
        }
    }

    return if(oneBitInputs.size >= zeroBitInputs.size) {
        findOxygenRating(oneBitInputs, index + 1)
    } else {
        findOxygenRating(zeroBitInputs, index + 1)
    }
}

fun findCO2Rating(input: List<String>, index: Int): String {
    var zeroBits = 0
    var oneBits = 0

    if(input.size == 1) {
        return input[0]
    }

    val zeroBitInputs = mutableListOf<String>()
    val oneBitInputs = mutableListOf<String>()

    input.forEach { line ->
        val char = line[index]
        if(char == '0') {
            zeroBits++
            zeroBitInputs.add(line)
        } else {
            oneBits++
            oneBitInputs.add(line)
        }
    }

    return if(zeroBitInputs.size <= oneBitInputs.size) {
        findCO2Rating(zeroBitInputs, index + 1)
    } else {
        findCO2Rating(oneBitInputs, index + 1)
    }
}

