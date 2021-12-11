fun main() {
    val input = readInput("day11")

    solve(input)
    solve2(input)
}

private fun solve(input: List<String>) {
    println("Part1")
    val lines = input.map { it.trim().toList().map { c -> c.toString().toInt() }.toMutableList() }.toMutableList()

    var flashes = 0
    for(i in 0..99) {
        flashes = nextStep(lines, i, flashes)
    }

    println("Flashes: $flashes")
}

private fun solve2(input: List<String>) {
    println("Part2")
    val lines = input.map { it.trim().toList().map { c -> c.toString().toInt() }.toMutableList() }.toMutableList()

    var i = 0
    do {
        nextStep(lines, 0, 0)
        i++
    } while (!allOcZero(lines))

    println("Steps: $i")
}

fun allOcZero(lines: MutableList<MutableList<Int>>): Boolean {
    for ((i,v) in lines.withIndex()) {
        for (j in 0 until lines[i].size) {
            val currentValue = v[j]
            if(currentValue != 0) {
                return false
            }
        }
    }
    return true
}

private fun nextStep(lines: MutableList<MutableList<Int>>, step: Int, flashes: Int): Int {
    var f = flashes
    val oc = mutableListOf<Pair<Int, Int>>()
    for ((i,v) in lines.withIndex()) {
        for (j in 0 until lines[i].size) {
            val currentValue = v[j]
            if(currentValue == 9) {
                v[j] = 0
                f++
                oc.add(Pair(i, j))
            } else {
                v[j] = currentValue + 1
            }
        }
    }
    //println("Do Step")
    //displayOctopuses(lines)

    if(oc.isNotEmpty()) {
        f += flash(lines, oc, 0)
    }

    //println("After step ${step + 1}:")
    //displayOctopuses(lines)
    return f
}

private fun flash(lines: MutableList<MutableList<Int>>, oc: List<Pair<Int, Int>>, flashes: Int): Int {
    var f = flashes
    val newOc = mutableListOf<Pair<Int, Int>>()
    for(o in oc) {
        val i = o.first
        val j = o.second
            getNext(lines, i - 1, j)?.let {
                if(it == 0) {
                    newOc.add(Pair(i - 1, j))
                    f++
                }
            }
            getNext(lines, i - 1, j + 1)?.let {
                if(it == 0) {
                    newOc.add(Pair(i - 1, j + 1))
                    f++
                }
            }
            getNext(lines, i, j + 1)?.let {
                if(it == 0) {
                    newOc.add(Pair(i, j + 1))
                    f++
                }
            }
            getNext(lines, i + 1, j + 1)?.let {
                if(it == 0) {
                    newOc.add(Pair(i + 1, j + 1))
                    f++
                }
            }
            getNext(lines, i + 1, j)?.let {
                if(it == 0) {
                    newOc.add(Pair(i + 1, j))
                    f++
                }
            }
            getNext(lines, i, j - 1)?.let {
                if(it == 0) {
                    newOc.add(Pair(i, j -1))
                    f++
                }
            }
            getNext(lines, i - 1, j - 1)?.let {
                if(it == 0) {
                    newOc.add(Pair(i - 1, j - 1))
                    f++
                }
            }
        getNext(lines, i + 1, j - 1)?.let {
            if(it == 0) {
                newOc.add(Pair(i + 1, j - 1))
                f++
            }
        }
        //println("Next Flash")
        //displayOctopuses(lines)
    }
    if(oc.isNotEmpty()) {
        f = flash(lines, newOc, f)
    }
    return f
}

fun getNext(lines: MutableList<MutableList<Int>>, i: Int, j: Int): Int? {
    if(i < 0 || j < 0 || i > 9 || j > 9 ) {
        return null
    }
    val v = lines[i][j]
    if(v == 9) {
        lines[i][j] = 0
    } else if (v != 0) {
        lines[i][j] = v + 1
    } else {
        return null
    }
    return lines[i][j]
}

private fun displayOctopuses(octopuses: List<List<Int>>) {
    for(o in octopuses) {
        println(o.toString())
    }
}
