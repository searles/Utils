package at.searles.commons.util

import java.util.*

class IntIntMap<A>: Iterable<A> {

    private val map = HashMap<Long, A>()

    operator fun get(x: Int, y: Int): A? {
        return map[compose(x, y)]
    }

    fun getValue(x: Int, y: Int): A {
        return this[x, y]!!
    }

    override fun iterator(): Iter<A> {
        return Iter(this)
    }

    fun containsKey(x: Int, y: Int): Boolean {
        return map.containsKey(compose(x, y))
    }

    fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    fun size(): Int {
        return map.size
    }

    operator fun set(x: Int, y: Int, value: A) {
        map[compose(x, y)] = value
    }

    class Iter<A> internal constructor(parent: IntIntMap<A>) : Iterator<A> {
        private val iterator: Iterator<Map.Entry<Long, A>>
        private var last: Map.Entry<Long, A>? = null

        init {
            iterator = parent.map.entries.iterator()
        }

        override fun hasNext(): Boolean {
            return iterator.hasNext()
        }

        override fun next(): A {
            val current = iterator.next()
            last = current
            return current.value
        }

        fun getX(): Int {
            return getX(last!!.key)
        }

        fun getY(): Int {
            return getY(last!!.key)
        }
    }

    companion object {
        private fun getX(l: Long): Int {
            return l.shr(32).toInt()
        }

        private fun getY(l: Long): Int {
            return l.and(0xffffffff).toInt()
        }

        private fun compose(x: Int, y: Int): Long {
            return x.toLong().shl(32).or(
                    y.toLong().and(0xffffffff)
            )
        }
    }
}