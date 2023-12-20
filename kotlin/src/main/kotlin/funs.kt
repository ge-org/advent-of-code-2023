import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun <T> T.println(): T = this.also { println(this) }

fun <T> T.check(expected: Any): T = this.also { check(this == expected) { "expected $expected but is $this" } }

fun readFile(name: String): String = Thread.currentThread().contextClassLoader
    .getResourceAsStream(name).use { inputStream ->
        BufferedReader(InputStreamReader(inputStream as InputStream)).readText().trim()
    }

fun readLines(name: String): List<String> = readFile(name).lines()

fun String.frequency() = toList().groupingBy { it }.eachCount()
