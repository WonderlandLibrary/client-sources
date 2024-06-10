package ac.sapphire.client.model

class Timer {
    private var start: Long = 0

    init {
        reset()
    }

    fun reset() {
        start = System.currentTimeMillis()
    }

    fun hasReached(ms: Long): Boolean {
        return System.currentTimeMillis() - start >= ms
    }
}