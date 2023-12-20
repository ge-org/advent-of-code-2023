import kotlin.collections.Map

fun main() {
    part1("day19_sample").check(19114)
    part1("day19_puzzle").println().check(323625)
}

private fun part1(file: String): Int = readFile(file).split("\n\n").let {
    val workflows = getWorkflows(it[0])
    getParts(it[1]).filter { part ->
        isAccepted(part, workflows)
    }.sumOf { part ->
        part.x + part.m + part.s + part.a
    }
}

private fun isAccepted(part: Object, workflows: Map<String, Workflow>): Boolean {
    fun isAccepted(obj: Object, workflow: Workflow): Boolean? = workflow.rules.fold(null as? Boolean) { isOK, rule ->
        if (isOK != null) return@fold isOK

        when (val action = rule.action) {
            is Action.Comparison -> if (matchesComparison(obj, action)) {
                when (val then = action.then) {
                    Action.Operation.Accept -> true
                    Action.Operation.Reject -> false
                    is Action.Operation.Workflow -> isAccepted(obj, workflows[then.name]!!)
                }
            } else {
                null
            }

            Action.Operation.Accept -> true
            Action.Operation.Reject -> false
            is Action.Operation.Workflow -> isAccepted(obj, workflows[action.name]!!)
        }
    }

    return isAccepted(part, workflows["in"]!!) ?: false
}

private fun getWorkflows(input: String): Map<String, Workflow> = input.lines().map { line ->
    val name = line.substringBefore('{')
    val rules: List<Rule> = line.substringAfter('{').dropLast(1).split(',').map {
        if (it.contains(':')) {
            val parts = it.split(':')
            val operation = getOperation(parts[1])
            when {
                parts[0].contains('>') -> Rule(
                    action = Action.Comparison.Greater(getRating(parts[0].split('>')), operation),
                )

                else -> Rule(
                    action = Action.Comparison.Lower(getRating(parts[0].split('<')), operation),
                )
            }
        } else {
            Rule(getOperation(it))
        }
    }
    Workflow(name, rules)
}.associateBy { it.name }

private fun getParts(input: String): List<Object> = input.lines().map { line ->
    line.drop(1).dropLast(1).split(',').fold(Object(0, 0, 0, 0)) { obj, keyValue ->
        val rating = getRating(keyValue.split('='))
        obj.copy(
            x = (rating as? Rating.X)?.value ?: obj.x,
            m = (rating as? Rating.M)?.value ?: obj.m,
            s = (rating as? Rating.S)?.value ?: obj.s,
            a = (rating as? Rating.A)?.value ?: obj.a,
        )
    }
}

private fun getOperation(text: String) = when (text) {
    "R" -> Action.Operation.Reject
    "A" -> Action.Operation.Accept
    else -> Action.Operation.Workflow(text)
}

private fun getRating(input: List<String>): Rating = when (input[0]) {
    "x" -> Rating.X(input[1].toInt())
    "m" -> Rating.M(input[1].toInt())
    "s" -> Rating.S(input[1].toInt())
    "a" -> Rating.A(input[1].toInt())
    else -> error("Invalid rating input")
}

private fun matchesComparison(part: Object, comparison: Action.Comparison): Boolean {
    val value = when (comparison.rating) {
        is Rating.A -> part.a
        is Rating.M -> part.m
        is Rating.S -> part.s
        is Rating.X -> part.x
    }
    return when (comparison) {
        is Action.Comparison.Greater -> value > comparison.rating.value
        is Action.Comparison.Lower -> value < comparison.rating.value
    }
}

private data class Workflow(
    val name: String,
    val rules: List<Rule>,
)

private data class Rule(
    val action: Action,
)

private sealed class Rating {
    abstract val value: Int

    data class X(override val value: Int) : Rating()
    data class M(override val value: Int) : Rating()
    data class S(override val value: Int) : Rating()
    data class A(override val value: Int) : Rating()
}

private sealed class Action {
    sealed class Comparison : Action() {
        abstract val rating: Rating
        abstract val then: Operation

        data class Greater(override val rating: Rating, override val then: Operation) : Comparison()
        data class Lower(override val rating: Rating, override val then: Operation) : Comparison()
    }

    sealed class Operation : Action() {
        data object Accept : Operation()
        data object Reject : Operation()
        data class Workflow(val name: String) : Operation()
    }
}

private data class Object(
    val x: Int,
    val m: Int,
    val s: Int,
    val a: Int,
)
