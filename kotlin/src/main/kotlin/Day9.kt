fun main() {
    part1("day9_sample").check(114)
    part1("day9_puzzle").println().check(1987402313)
}

private fun part1(file: String): Int {
    val histories = readLines(file).map { it.split(" ").map { it.toInt() } }
    val newHistories = histories.map { listOf(it) + addSequences(it) }
    return newHistories.sumOf {
        extrapolate(it).map { it.last() }.last()
    }
}

private fun addSequences(history: List<Int>): List<List<Int>> {
    tailrec fun rec(h: List<Int>, acc: MutableList<List<Int>>): MutableList<List<Int>> {
        val next = getNextSequence(h).also { acc.add(it) }
        return if (next.allZeros) acc.addLast(0) else rec(next, acc)
    }
    return rec(history, mutableListOf())
}

private fun getNextSequence(values: List<Int>): List<Int> = values.windowed(2).map { it.last() - it.first() }

private val List<Int>.allZeros: Boolean get() = all { it == 0 }

private fun MutableList<List<Int>>.addLast(value: Int): MutableList<List<Int>> {
    val withZero = last().toMutableList().apply { add(value) }
    return apply { set(lastIndex, withZero) }
}

private fun extrapolate(histories: List<List<Int>>): List<List<Int>> =
    histories.reversed().foldIndexed(histories.reversed()) { idx, acc, _ ->
        val nextRow = if (idx != acc.lastIndex) acc[idx + 1] else return@foldIndexed acc
        val newValue = acc[idx].last() + nextRow.last()
        val updatedRow = nextRow.toMutableList().apply { add(newValue) }
        acc.toMutableList().apply { set(idx + 1, updatedRow) }
    }
