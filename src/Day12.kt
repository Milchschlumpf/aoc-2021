fun main() {
    val input = readInput("day12")

    solve(input)
}

private fun solve(input: List<String>) {

    val routeMap = mutableMapOf<String, MutableList<String>>()
    for(i in input) {
        val s = i.split("-")
        val x = routeMap[s[0]] ?: mutableListOf()
        x.add(s[1])
        routeMap[s[0]] = x

        if(s[1] != "end") {
            val y = routeMap[s[1]] ?: mutableListOf()
            y.add(s[0])
            routeMap[s[1]] = y
        }
    }

    println("Size: ${routeMap.size}")
    println("Part1")
    determinePathThroughCave(routeMap, false)
    println("Part2")
    determinePathThroughCave(routeMap, true)
}

private fun determinePathThroughCave(routeMap: MutableMap<String, MutableList<String>>, isPart2: Boolean) {
    val startPaths = routeMap["start"]?.map {
        val p = Path()
        p.steps.add("start")
        p.isSmallCaveVisitTwice = isPart2
        p
    }?.toMutableSet()

    val finishedPaths = getNextStep(startPaths!!, routeMap)
    println("Total Paths: ${finishedPaths.size}")
    //displayPaths(finishedPaths)
}

private fun getNextStep(paths: MutableSet<Path>, routeMap: MutableMap<String, MutableList<String>> ): MutableSet<Path> {
    val paths2 = mutableSetOf<Path>()
    for(p in paths) {
        if(p.isFinished) {
            paths2.add(p)
            continue
        }
        try {
            val next = routeMap[p.steps.last()]!!
            for (n in next) {
                if(n == "start") {
                    continue
                }
                val p2 = p.copy(steps = p.steps.toMutableList(), isFinished = p.isFinished, visitedSmallCave = p.visitedSmallCave.toMutableList())
                p2.addStep(n)
                paths2.add(p2)
            }
        } catch(n: NullPointerException) {
            println("lol")
        }
    }

    paths2.removeIf { it.isDeadEnd }

    return if(paths2.all { it.isFinished }) {
        paths2
    } else {
        getNextStep(paths2, routeMap)
    }
}

data class Path(
    val steps: MutableList<String> = mutableListOf(),
    var isFinished: Boolean = false,
    var isDeadEnd: Boolean = false,
    var isSmallCaveVisitTwice: Boolean = false,
    val visitedSmallCave: MutableList<String> = mutableListOf()
) {

    fun addStep(s: String) {
        if(s.isLowerCase()) {
            if(!steps.contains(s)) {
                steps.add(s)
            } else {
                if(isSmallCaveVisitTwice) {
                    if(visitedSmallCave.isEmpty()) {
                        steps.add(s)
                        visitedSmallCave.add(s)
                    } else {
                        isDeadEnd = true
                    }
                } else {
                    isDeadEnd = true
                }
            }
        } else {
            steps.add(s)
        }
        if(s == "end") {
            isFinished = true
        }
    }
}

private fun displayPaths(paths: Set<Path>) {
    paths.forEach {
        println(it.steps.toString())
    }
}

