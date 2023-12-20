fun main() {
    part1("day15_sample").check(1320)
    part1("day15_puzzle").println().check(521341)
}

private fun part1(file: String): Int = readFile(file).split(',').sumOf { input ->
    input.fold<Int>(0) { sum, char ->
        ((char.code + sum) * 17) % 256
    }
}
