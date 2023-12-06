fun main() {
    part1("day6_sample").check(288)
    part1("day6_puzzle").println().check(1108800)

    part2("day6_sample").check(71503)
    part2("day6_puzzle").println()
}

private fun part1(file: String): Int {
    val times = readLines(file)[0].getNumbers()
    val distances = readLines(file)[1].getNumbers()
    return times.zip(distances).toMap().map { (maxTime, requiredDistance) ->
        (0..maxTime).map { delta ->
            ((maxTime - delta) * delta) to delta
        }.filter { it.first > requiredDistance }.size
    }.fold(1) { acc, time -> acc * time }
}

private fun part2(file: String): Int {
    TODO()
}

private fun String.getNumbers() = substringAfter(':').trim().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
