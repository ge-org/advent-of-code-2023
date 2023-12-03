fun main() {
    part1("day2_sample").check(8)
    part1("day2_puzzle").println().check(2795)  
}

private fun part1(file: String): Int = getGames(file).filter { game ->
    game.draws.none {
        it.red > maxRed || it.green > maxGreen || it.blue > maxBlue
    }
}.sumOf { it.id }

private fun part2(file: String): Int {
    TODO()
}

private fun getGames(file: String): List<Game> = readLines(file).map { line ->
    val idAndDraws = line.split(":")
    val id = idAndDraws[0].replace("Game ", "").toInt()
    val draws = idAndDraws[1].split(';').map {
        it.split(',').fold(Draw(0, 0, 0)) { draw, split ->
            when {
                split.contains("red") -> draw.copy(red = split.replace("red", "").trim().toInt())
                split.contains("green") -> draw.copy(green = split.replace("green", "").trim().toInt())
                split.contains("blue") -> draw.copy(blue = split.replace("blue", "").trim().toInt())
                else -> draw
            }
        }
    }
    Game(id, draws)
}

private const val maxRed = 12
private const val maxGreen = 13
private const val maxBlue = 14

private data class Game(
    val id: Int,
    val draws: List<Draw>,
)

private data class Draw(
    val red: Int,
    val green: Int,
    val blue: Int,
)
