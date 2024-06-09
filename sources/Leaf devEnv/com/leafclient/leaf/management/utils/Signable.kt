package com.leafclient.leaf.management.utils

import com.leafclient.leaf.management.Contributor

/**
 * Marks an object that contains a [description] so it can be displayed into
 * user interfaces.
 */
interface Signable {

    /**
     * Returns the [description]
     */
    val contributors: List<Contributor>

}

/**
 * Tries to get the contributors of this object or and empty list
 */
val Any.contributors: List<Contributor>
    get() = if(this is Signable) this.contributors else emptyList()