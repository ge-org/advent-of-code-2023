import kotlin.math.pow

fun main() {
    part1("day4_sample").check(13)
    part1("day4_puzzle").println().check(18653)

    part2("day4_sample").check(30)
    part2("day4_puzzle").println()
}

private fun part1(file: String): Int = readLines(file).sumOf { line ->
    line.substringAfter(": ").split(" | ").map {
        it.split(" ").filter(String::isNotBlank).map(String::toInt)
    }.let { numbers ->
        numbers[1].count { it in numbers[0] }.let {
            2.0.pow(it - 1).toInt()
        }
    }
}

private fun part2(file: String): Int {
    TODO()
}
