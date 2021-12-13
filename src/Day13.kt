fun main() {
    val input = readInput("day13")

    solve(input)
}

private fun solve(input: List<String>) {
    //Coords are different: y => List<x>
    val coords = mutableMapOf<Int, MutableSet<Int>>()
    for (i in input.filter { it.isNotEmpty() && !it.startsWith("fold along") }) {
        val s = i.split(",")
        val y = coords[s[1].toInt()] ?: mutableSetOf()
        y.add(s[0].toInt())
        coords[s[1].toInt()] = y
    }

    val folds = mutableListOf<Pair<String, Int>>()
    for (i in input.filter { it.isNotEmpty() && it.startsWith("fold along") }) {
        val s = i.removePrefix("fold along ")
        val splitted = s.split("=")
        folds.add(Pair(splitted[0], splitted[1].toInt()))
    }

    for (f in folds) {
        if (f.first == "y") {
            foldY(f.second, coords)
            //println(coords)
        } else {
            foldX(f.second, coords)
            //println(coords)
        }
        println("Visible Dots: ${calculateVisibleDots(coords)}")
    }
    displayValue(coords)
}

private fun foldY(foldLine: Int, coords: MutableMap<Int, MutableSet<Int>>) {
    val maxY = foldLine * 2
    for (i in 0 until foldLine) {
        mergeY(i, maxY - i, coords)
    }

    for (i in foldLine..maxY) {
        coords.remove(i)
    }
}

fun mergeY(line1: Int, line2: Int, coords: MutableMap<Int, MutableSet<Int>>) {
    val list = coords[line1] ?: mutableSetOf()
    val list2 = coords[line2] ?: mutableSetOf()
    list2.forEach {
        list.add(it)
    }
    coords[line1] = list
}

private fun foldX(foldLine: Int, coords: MutableMap<Int, MutableSet<Int>>) {
    //println("fold x: $foldLine")
    for (c in coords.entries) {
        val marked = mutableSetOf<Int>()
        for (v in c.value) {
            if (v < foldLine) {
                marked.add(v)
            } else if(v > foldLine) {
               marked.add(foldLine - (v - foldLine))
            }
        }

        coords[c.key] = marked
    }

}

private fun calculateVisibleDots(coords: MutableMap<Int, MutableSet<Int>>): Int {
    return coords.map { it.value.size }.sum()
}

private fun displayValue(coords: MutableMap<Int, MutableSet<Int>>) {
    for(c in coords.toSortedMap()) {
        if(c.value.isNotEmpty()) {
            val biggest = c.value.maxOf { it }
            var s = ""
            for (i in 0..biggest) {
                s += if (c.value.contains(i)) "⬛" else "⬜"
            }
            println(s)
        }
    }
}

