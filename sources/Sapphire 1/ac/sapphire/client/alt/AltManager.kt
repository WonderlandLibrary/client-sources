package ac.sapphire.client.alt

class AltManager {
    var lastAlt: IAlt? = null
    val alts = mutableListOf<IAlt>()

    fun add(alt: IAlt) {
        alts.add(alt)
    }

    fun get(index: Int): IAlt? {
        return if (index >= alts.size) null else alts[index]
    }

    fun getByEmail(email: String): IAlt? {
        return alts.find { it.email == email.lowercase() }
    }

    fun getAll(): List<IAlt> {
        return alts
    }

    fun indexOf(alt: IAlt): Int {
        return alts.indexOf(alt)
    }

    fun set(index: Int, alt: IAlt) {
        alts[index] = alt
    }

    fun remove(index: Int) {
        alts.removeAt(index)
    }

    fun remove(alt: IAlt) {
        alts.remove(alt)
    }
}