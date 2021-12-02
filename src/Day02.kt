import java.lang.IllegalArgumentException

fun main() {
    val input = readInput("day02")

    var horizontalPosition = 0
    var depth = 0

    input.forEach { line ->
        if(line.contains("forward")) {
            val value = line.removePrefix("forward").trim().toInt()
            horizontalPosition += value
        } else if (line.contains("down")) {
            val value = line.removePrefix("down").trim().toInt()
            depth += value
        } else if (line.contains("up")) {
            val value = line.removePrefix("up").trim().toInt()
            depth -= value
        }else {
            throw IllegalArgumentException("Unkown command: $line")
        }
    }

    println("Part1")
    println("Horizontal: $horizontalPosition, Depth: $depth")
    println("Multiplying Value: ${horizontalPosition * depth}")

    //Part2

    horizontalPosition = 0
    depth = 0
    var aim = 0

    input.forEach { line ->
        if(line.contains("forward")) {
            val value = line.removePrefix("forward").trim().toInt()
            horizontalPosition += value
            depth += aim * value
        } else if (line.contains("down")) {
            val value = line.removePrefix("down").trim().toInt()
            aim += value
        } else if (line.contains("up")) {
            val value = line.removePrefix("up").trim().toInt()
            aim -= value
        }else {
            throw IllegalArgumentException("Unkown command: $line")
        }
    }

    println("Part2")
    println("Horizontal: $horizontalPosition, Depth: $depth")
    println("Multiplying Value: ${horizontalPosition * depth}")
}

