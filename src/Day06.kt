import java.math.BigInteger

fun main() {
    val input = readInput("day06")

    solve(input, 18)
    solve(input, 256)
}

private fun solve(input: List<String>, maxDays: Int) {
    val fishes = input[0].split(",").map { it.trim().toInt() }.toMutableList()
    val fishMap = mutableMapOf<Int, BigInteger>()

    for(fish in fishes) {
        val dayOfFirstNewborn = fish + 1
        fishMap[dayOfFirstNewborn] = fishMap[dayOfFirstNewborn]?.plus(BigInteger.valueOf(1)) ?: BigInteger.valueOf(1)
    }

    val nonPregnantFishMap = mutableMapOf<Int, BigInteger>()
    var day = 1
    for (i in 1 until maxDays + 1) {
        val nonPregnantFishes = nonPregnantFishMap[day] ?: BigInteger.valueOf(0)
        val numberFishes = fishMap[day] ?: BigInteger.valueOf(0)
        val nextDay = getNextDay(day)
        val numberPregnantFishes = numberFishes - nonPregnantFishes

        nonPregnantFishMap[nextDay] = numberPregnantFishes
        fishMap[nextDay] = fishMap[nextDay]?.plus(numberPregnantFishes) ?: numberPregnantFishes
        day++
        if(day == 8) {
            day = 1
        }
    }

    var summe = BigInteger.valueOf(0)
    fishMap.values.forEach {
        summe += it
    }
    println("Total Fishes: $summe")
}

private fun getNextDay(day: Int): Int {
    return when (day) {
        1 -> {
            3
        }
        2 -> {
            4
        }
        3 -> {
            5
        }
        4 -> {
            6
        }
        5 -> {
            7
        }
        6 -> {
            1
        }
        else -> {
            2
        }
    }
}





