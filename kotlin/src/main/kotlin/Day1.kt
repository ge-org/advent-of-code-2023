fun main() {
    part1("day1_1_sample").check(142)
    part1("day1_puzzle").println().check(55002)

    part2("day1_2_sample").check(281)
    part2("day1_puzzle").println().check(55093)
}

private fun part1(file: String): Int = readLines(file).sumFirstAndLastDigit()

private fun part2(file: String): Int = readLines(file).map {
    if (!startsAndEndsWithDigit(it)) substituteFirstAndLastLiteralDigit(it) else it
}.sumFirstAndLastDigit()

private fun List<String>.sumFirstAndLastDigit(): Int = sumOf(::concatenateFirstAndLastDigit)

private fun concatenateFirstAndLastDigit(line: String) = line.filter(Char::isDigit).let { "${it.first()}${it.last()}".toInt() }

private fun startsAndEndsWithDigit(line: String) = line.first().isDigit() && line.last().isDigit()

private fun substituteFirstAndLastLiteralDigit(line: String): String = line.mapIndexedNotNull { idx, char ->
    when {
        char.isDigit() -> char
        else -> line.literalDigitsStartingAt(idx).firstNotNullOfOrNull { literalDigits[it] }
    }
}.joinToString()

private fun String.literalDigitsStartingAt(start: Int): List<String> = (3..5).map { literalLength ->
    substring(start, (start + literalLength).coerceAtMost(length))
}

private val literalDigits = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)
