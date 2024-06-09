package com.leafclient.leaf.management.file

import java.io.File

abstract class ClientFile(val file: File) {

    /**
     * Reads the content of [file] and applies every effects
     * around it
     */
    open fun read() {}

    /**
     * Saves the content of [file]
     */
    open fun save() {}

}