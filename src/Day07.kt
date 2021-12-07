import kotlin.math.abs

fun main() {
    val input = readInput("day07")

    //Part1
    solve(input)
    //Part2
    solve2(input)
}

private fun solve(input: List<String>) {
    println("Part1")
    val craps = input[0].split(",").map { it.trim().toInt() }.toMutableList().sorted()
    val medianPos = craps.size.div(2)
    val median = craps[medianPos]
    println("Median at pos: $medianPos with value $median")

    var fuel = 0
    for(crap in craps) {
        fuel += abs(crap - median)
    }

    println("Total Fuel: $fuel")
}


private fun solve2(input: List<String>) {
    println("Part2")
    val craps = input[0].split(",").map { it.trim().toInt() }.toMutableList()
    val summe = craps.sum()
    val arithmeticAverage = summe.div(craps.size)
    println("Arithmetic Average: $arithmeticAverage")

    val fuels = mutableListOf<Fuel>()
    for(j in arithmeticAverage - 10 .. arithmeticAverage + 10) {
        var fuelValue = 0
        for (crap in craps) {
            val steps = abs(crap - j)
            for (i in 1..steps) {
                fuelValue += i
            }
        }
        fuels.add(Fuel(j, fuelValue))
    }

    val minFuel = fuels.minOf { it.fuel }
    println("Total Fuel: $minFuel")
}

data class Fuel(
    val arithmeticAverage: Int,
    val fuel: Int
)





