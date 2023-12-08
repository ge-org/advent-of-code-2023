fun main() {
    part1("day8_sample1").check(2)
    part1("day8_sample2").check(6)
    part1("day8_puzzle").println().check(21251)
}

private fun part1(file: String): Int = readLines(file).getMap().let { map ->
    var step = 0
    var node = "AAA"
    while (node != "ZZZ") {
        node = when (map.instructions[step % map.instructions.size]) {
            'R' -> map.nodes[node]!!.second
            else -> map.nodes[node]!!.first
        }
        step += 1
    }
    step
}

private fun List<String>.getMap() = Map(
    instructions = this[0].toList(),
    nodes = drop(2).associate { line ->
        val name = line.substringBefore(" =")
        name to line.substringAfter("(").split(", ").let {
            it[0] to it[1].dropLast(1)
        }
    },
)

data class Map(
    val instructions: List<Char>,
    val nodes: kotlin.collections.Map<String, Pair<String, String>>,
)
