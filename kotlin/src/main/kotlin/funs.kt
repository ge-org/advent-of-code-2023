import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun Any?.println(): Any? = this.also { println(this) }

fun Any?.check(expected: Any): Any? = this.also { kotlin.check(this == expected) }

fun readLines(name: String) = Thread.currentThread().contextClassLoader
    .getResourceAsStream(name).use { inputStream ->
        BufferedReader(InputStreamReader(inputStream as InputStream)).readLines()
    }
