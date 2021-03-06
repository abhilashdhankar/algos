package pour

import io.vavr.Tuple
import io.vavr.Tuple2
import io.vavr.collection.List
import io.vavr.kotlin.list
import org.junit.Test

class PouringTest {
    @Test
    fun testWith2Glasses() {
        val initialState = list(Cup(0, 4), Cup(0, 9))
        val pouring = Pouring(initialState)

        pouring.solution(list(Cup(0, 4), Cup(6, 9)))
                .take(1).forEach { path ->
                    println(path)
                }

    }

    @Test
    fun createDotPath() {
        val initialState = list(Cup(0, 4), Cup(0, 9))
        val pouring = Pouring(initialState)
        println("strict digraph G {")
        pouring.pathSets.take(3)
                .forEach { paths ->
                    paths.forEach { path ->
                        val movesAndStateForPath = getMovesAndStateForPath(path, initialState)
                        if (!movesAndStateForPath.isEmpty) {
                            val moveAndState = movesAndStateForPath.last()
                            val move = moveAndState._1
                            val states = moveAndState._2
                            val from = states._1
                            val to = states._2
                            println("\"$from\" -> \"$to\" [label=\"$move\"]")

                        }
                    }
                }
        println("}")
    }

    fun getMovesAndStateForPath(path: Path, initialState: State): List<Tuple2<Move, Tuple2<State, State>>> {
        val res = path.history.reverse().foldLeft(Tuple.of(List.empty<Tuple2<State, State>>(), initialState)) { tup, move ->
            val oldState = tup._2
            val newState = move.change(oldState)

            val updatedList = tup._1.prepend(Tuple.of(oldState, newState))
            Tuple.of(updatedList, newState)
        }
        return path.history.reverse().zip(res._1.reverse())
    }

    @Test
    fun testWith3Glasses() {
        val initialState = list(Cup(12, 12), Cup(0, 8), Cup(0, 5))
        val pouring = Pouring(initialState)
        pouring.solution(list(Cup(6, 12), Cup(6, 8), Cup(0, 5)))
                .take(1).forEach { println(it) }

    }
}