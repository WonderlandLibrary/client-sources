package com.leafclient.leaf.management

import com.leafclient.leaf.Leaf

/**
 * A data class used inside of Spicy to recognize and provide information about a contributor.
 * This is useful for UIs and future features of the client
 */
data class Contributor(val name: String, val description: String, val links: Array<String>) {

    /**
     * A method used to know if another object is equal to this [Contributor]
     */
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as Contributor

        if (name != other.name) return false
        if (description != other.description) return false
        if (!links.contentEquals(other.links)) return false

        return true
    }

    /**
     * The hashcode of this class
     */
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + links.contentHashCode()
        return result
    }

}

/**
 * Tries to get the [Contributor] with [name] if its registered inside of the client.
 */
fun contributor(name: String): Contributor? {
    return Leaf.contributors
        .firstOrNull { it.name.equals(name, true) }
}