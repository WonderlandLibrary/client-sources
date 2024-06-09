package com.leafclient.leaf.management.utils

/**
 * Marks an object that contains a [description] so it can be displayed into
 * user interfaces.
 */
interface Describable {

    /**
     * Returns the [description]
     */
    val description: String

}

/**
 * Tries to get the description of this object or returns null
 */
val Any.description: String?
    get() = if(this is Describable) this.description else null