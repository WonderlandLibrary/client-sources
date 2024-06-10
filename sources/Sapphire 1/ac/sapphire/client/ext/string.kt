package ac.sapphire.client.ext

import java.util.*

operator fun StringJoiner.plusAssign(seq: CharSequence) {
    add(seq)
}
