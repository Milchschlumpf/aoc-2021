fun main() {
    val input = readInput("day01")

    var counter = 0
    var lastSonarValue = 0
    var firstItem = true

    input.forEach { line ->
        val sonarValue = line.trim().toInt()
        if (firstItem) {
            firstItem = false
        } else if (sonarValue > lastSonarValue) {
            counter += 1
        }
        lastSonarValue = sonarValue
    }

    println("Part1 Number increases: $counter")

    //Part2
    counter = 0
    firstItem = true
    val size = input.size
    var loopCounter = 0
    var lastSum = 0

    do {
        val sum = sumSonarValues(input[loopCounter].toInt(), input[loopCounter + 1].toInt(), input[loopCounter + 2].toInt())
        if (firstItem) {
            firstItem = false
        } else if (sum > lastSum) {
            counter += 1
        }
        lastSum = sum
        loopCounter++
    } while (loopCounter <= size - 3)

    println("Part2 Number increases: $counter")
}

fun sumSonarValues(value1: Int, value2: Int, value3: Int): Int {
    return value1 + value2 + value3
}
