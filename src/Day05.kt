fun main() {
    val input = readInput("day05")

    solve(input)
}

private fun solve(input: List<String>) {
    val vents = input.map { it.split("->") }.map {
        createVent(it)
    }
    println("Anzahl Vents: ${vents.size}")

    solvePart1(vents)

    solvePart2(vents)
}

private fun solvePart1(vents: List<Vent>) {
    println("Part1")
    val notDiagonalVents = vents.filter { it.isNotDiagonal() }
    println("Nicht diagonale Vents: ${notDiagonalVents.size}")

    val paths = mutableListOf<Pair<Int, Int>>()
    val duplicatePaths = mutableListOf<Pair<Int, Int>>()
    for (vent in notDiagonalVents) {
        val ventPath = vent.getPath()
        for(p in ventPath) {
            if( !paths.contains(p)) {
                paths.add(p)
            } else if (!duplicatePaths.contains(p)) {
                duplicatePaths.add(p)
            }
        }
    }

    println("Number of points where at least two lines overlap: ${duplicatePaths.size} ")
}

private fun solvePart2(vents: List<Vent>) {
    println("Part2")

    val paths = mutableListOf<Pair<Int, Int>>()
    val duplicatePaths = mutableListOf<Pair<Int, Int>>()
    for (vent in vents) {
        val ventPath = vent.getPath()
        for(p in ventPath) {
            if( !paths.contains(p)) {
                paths.add(p)
            } else if (!duplicatePaths.contains(p)) {
                duplicatePaths.add(p)
            }
        }
    }

    println("Number of points where at least two lines overlap: ${duplicatePaths.size} ")
}


private fun createVent(points: List<String>): Vent {
    val point1 = points[0].split(",")
    val point2 = points[1].split(",")
    return Vent(Pair(point1[0].trim().toInt(), point1[1].trim().toInt()), Pair(point2[0].trim().toInt(), point2[1].trim().toInt()))
}

data class Vent(
    val p1: Pair<Int, Int>,
    val p2: Pair<Int, Int>
) {

    fun isNotDiagonal(): Boolean {
        return p1.first == p2.first || p1.second == p2.second
    }

    fun getPath(): List<Pair<Int, Int>> {
        val points = mutableListOf<Pair<Int, Int>>()
        points.add(p1)
        return getNextPoint(p1, p2, points)
    }

    private fun getNextPoint(currentPoint: Pair<Int, Int>, finalPoint: Pair<Int, Int>, points: MutableList<Pair<Int, Int>>): MutableList<Pair<Int, Int>> {
        if(currentPoint == finalPoint) {
            return points
        }

        val newPoint = Pair(getNextCord(currentPoint.first, finalPoint.first), getNextCord(currentPoint.second, finalPoint.second))
        points.add(newPoint)
        return getNextPoint(newPoint, finalPoint, points)
    }

    private fun getNextCord(x1: Int, x2: Int): Int {
        return if(x1 == x2) {
            x1
        } else if (x1 < x2) {
            x1 + 1
        } else {
            x1 - 1
        }
    }

}
