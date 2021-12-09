fun main() {
    val input = readInput("day09")

    solve(input)
}

private fun solve(input: List<String>) {
    println("Part1")
    val lines = input.map { it.trim().toList().map { c -> c.toString().toInt() } }

    val size = lines.size
    val length = lines[0].size
    println("Puzzle Length: $length x $size")

    val heatPoints = mutableListOf<HeatPoint>()

    for ((i,v) in lines.withIndex()) {
        for(j in 0 until length) {
            val currentValue = v[j]
            val heatPoint = createHeatPoint(currentValue, i, j, lines, size, length )
            heatPoints.add(heatPoint)
        }
    }

    val lowPoints = heatPoints.filter { it.isLowPoint() }
    val sumRiskLevels = lowPoints.sumOf { it.calculateRiskLevel() }

    println("Summe Risk Levels: $sumRiskLevels")

    println("Part2")
    val basingSizes = mutableListOf<Int>()
    for (lowPoint in lowPoints) {
        val basinHeatPoints = mutableListOf<HeatPoint>()
        createBasin(lowPoint, lines, size, length, basinHeatPoints)
        basingSizes.add(basinHeatPoints.size)
    }

    basingSizes.sortDescending()
    val largestBasin = basingSizes[0] * basingSizes[1] * basingSizes[2]
    println("Multiply 3 largest Basins: $largestBasin")
}

private fun createHeatPoint(currentValue: Int, i: Int, j: Int, lines: List<List<Int>>, size: Int, length: Int): HeatPoint {
    val a = if(i - 1 < 0) 99 else lines[i - 1][j]
    val b = if(j + 1 >= length) 99 else lines[i][j + 1]
    val c = if(i + 1 >= size) 99 else lines[i + 1][j]
    val d = if(j - 1 < 0) 99 else lines[i][j -1]

    return HeatPoint(a, b, c, d, currentValue,i, j)
}

data class HeatPoint(
    val a: Int,
    val b: Int,
    val c: Int,
    val d: Int,
    val v: Int,
    val i: Int,
    val j: Int
) {

    fun isLowPoint(): Boolean {
        return v < a && v < b && v < c && v < d
    }

    fun calculateRiskLevel(): Int {
        return v + 1
    }
}


private fun createBasin(heatPoint: HeatPoint, lines: List<List<Int>>, size: Int, length: Int, currentBasinHeatPoints: MutableList<HeatPoint>) {
    if(heatPoint.a < 9) {
        val basinHeatPoint = createHeatPoint(heatPoint.a, heatPoint.i - 1, heatPoint.j, lines, size, length)
        if(!currentBasinHeatPoints.contains(basinHeatPoint)) {
            currentBasinHeatPoints.add(basinHeatPoint)
            createBasin(basinHeatPoint, lines, size, length, currentBasinHeatPoints)
        }
    }
    if(heatPoint.b < 9) {
        val basinHeatPoint = createHeatPoint(heatPoint.b, heatPoint.i, heatPoint.j + 1, lines, size, length)
        if(!currentBasinHeatPoints.contains(basinHeatPoint)) {
            currentBasinHeatPoints.add(basinHeatPoint)
            createBasin(basinHeatPoint, lines, size, length, currentBasinHeatPoints)
        }
    }
    if(heatPoint.c < 9) {
        val basinHeatPoint = createHeatPoint(heatPoint.c, heatPoint.i + 1, heatPoint.j, lines, size, length)
        if(!currentBasinHeatPoints.contains(basinHeatPoint)) {
            currentBasinHeatPoints.add(basinHeatPoint)
            createBasin(basinHeatPoint, lines, size, length, currentBasinHeatPoints)
        }
    }
    if(heatPoint.d < 9) {
        val basinHeatPoint = createHeatPoint(heatPoint.d, heatPoint.i, heatPoint.j - 1, lines, size, length)
        if(!currentBasinHeatPoints.contains(basinHeatPoint)) {
            currentBasinHeatPoints.add(basinHeatPoint)
            createBasin(basinHeatPoint, lines, size, length, currentBasinHeatPoints)
        }
    }
}





