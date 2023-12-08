fun main() {
    testHands()
    part1("day7_sample").check(6440)
    part1("day7_puzzle").println().check(248179786)
}

private fun part1(file: String): Int = readLines(file)
    .map { line -> line.split(" ").let { Hand(it.last().toInt(), it.first()) } }
    .sortedWith(compareBy(
        { it.type },
        { it.value.map { char -> ALPHABET.reversed()[STRENGTHS.indexOf(char)] }.joinToString("") },
    ))
    .mapIndexed { idx, hand -> hand.bid * (idx + 1) }
    .sum()

private const val STRENGTHS = "AKQJT98765432"
private const val ALPHABET = "abcdefghijklmnopqrstuvxyz"

private data class Hand(
    val bid: Int,
    val value: String,
)

private val Hand.isFiveOfAKind: Boolean get() = value.toSet().size == 1
private val Hand.isFourOfAKind: Boolean get() = value.frequency().any { it.value == 4 }
private val Hand.isFullHouse: Boolean get() = value.frequency().values.run { has(3, 1) && has(2, 1) }
private val Hand.isThreeOfAKind: Boolean get() = value.frequency().values.run { has(3, 1) && has(1, 2) }
private val Hand.isTwoPairs: Boolean get() = value.frequency().values.run { has(2, 2) }
private val Hand.isOnePair: Boolean get() = value.frequency().values.run { has(2, 1) && has(1, 3) }
private val Hand.isHighCard: Boolean get() = value.frequency().values.all { it == 1 }

private val Hand.type get() = when {
    isFiveOfAKind -> 7
    isFourOfAKind -> 6
    isFullHouse -> 5
    isThreeOfAKind -> 4
    isTwoPairs -> 3
    isOnePair -> 2
    isHighCard -> 1
    else -> 0
}

private fun Collection<Int>.has(x: Int, times: Int): Boolean = count { it == x } == times

private fun testHands() {
    Hand(0, "AAAAA").isFiveOfAKind.check(true)
    Hand(0, "AA8AA").isFourOfAKind.check(true)
    Hand(0, "23332").isFullHouse.check(true)
    Hand(0, "TTT98").isThreeOfAKind.check(true)
    Hand(0, "23432").isTwoPairs.check(true)
    Hand(0, "A23A4").isOnePair.check(true)
    Hand(0, "23456").isHighCard.check(true)
}
