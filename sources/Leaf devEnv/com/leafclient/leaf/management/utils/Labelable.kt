package com.leafclient.leaf.management.utils

/**
 * Marks an object that contains a [label] which should always be unique
 * when they're used.
 */
interface Labelable {

    /**
     * Returns the [label] of this object
     */
    val label: String

}

/**
 * Tries to get the label of this object or returns null
 */
val Any.label: String?
    get() = if(this is Labelable) this.label else null