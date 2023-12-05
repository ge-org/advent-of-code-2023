fun main() {
    part1("day3_sample").check(4361)
    part1("day3_puzzle").println()
}

private fun part1(file: String): Int {
    val schematic = readLines(file).getParts()
    return schematic.map { parts ->
        parts.filterIndexed { idx, part ->
            val prev = if (idx != 0) parts[idx - 1] else null
            val next = if (idx < parts.lastIndex) parts[idx + 1] else null
            val prevRow = if (part.row != 0) schematic[part.row - 1] else emptyList()
            val nextRow = if (part.row < schematic.lastIndex) schematic[part.row + 1] else emptyList()
            val hasHorizontalSymbol = prev is Part.Symbol || next is Part.Symbol
            val hasVerticalSymbolAbove = prevRow.filterIsInstance<Part.Symbol>().any {
                val range = it.index
                val num = part as? Part.Number ?: return@any false
//                val newRange = num.range.first.minus(1)..num.range.last.plus(1)
                (num.range.first - 1) <= range && range <= (num.range.last + 1)
            }
            val hasVerticalSymbolBelow = nextRow.filterIsInstance<Part.Symbol>().any {
                val range = it.index
                val num = part as? Part.Number ?: return@any false
                val newRange = num.range.first.minus(1)..num.range.last.plus(1)
                newRange.first <= range && range <= newRange.last
            }
            val isP = hasHorizontalSymbol || hasVerticalSymbolAbove || hasVerticalSymbolBelow
            if (!isP && part is Part.Number) println("SKIP: $part") else if (part is Part.Number) println("KEEP: $part")
            if (idx == parts.lastIndex) println("")
            isP
        }
    }.flatten().sumOf { it.intValue }
}

private fun part2(file: String): Int {
    TODO()
}

private fun List<String>.getParts(): List<List<Part>> {
//    val regex = "[^.]?[^-]\\d?[^.]?".toRegex()
    val regex = "[^.-0123456789]?|[1-9][0-9]*".toRegex()
    return mapIndexed { row, line ->
        regex.findAll(line).map { result ->
            result.groups.drop(2).map { match ->
                println(match)
                match?.let {
                    match.value.toIntOrNull()?.let {
                        Part.Number(row, match.range, it)
                    } ?: Part.Symbol(row, match.range.first, match.value)
                } ?: Part.None(row)
            }
        }.toList().flatten()
    }
}

private sealed class Part {
    abstract val row: Int

    data class None(override val row: Int) : Part()

    data class Symbol(
        override val row: Int,
        val index: Int,
        val value: String,
    ) : Part()

    data class Number(
        override val row: Int,
        val range: IntRange,
        val value: Int,
    ) : Part()
}

//private val Part.range get() = when (this) {
//    is Part.None -> null
//    is Part.Number -> span
//    is Part.Symbol -> span
//}

private val Part.intValue get() = when (this) {
    is Part.None -> 0
    is Part.Number -> value
    is Part.Symbol -> 0
}

private fun IntRange.contains(range1: IntRange): Boolean = range1.isEmpty() || (contains(range1.first) && contains(range1.last))
