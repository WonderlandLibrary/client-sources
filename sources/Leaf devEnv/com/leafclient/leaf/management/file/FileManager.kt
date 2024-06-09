package com.leafclient.leaf.management.file

import com.leafclient.leaf.event.client.ClientLoadedEvent
import com.leafclient.leaf.management.event.EventManager
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import java.util.concurrent.Executors

/**
 * Contains and manage asynchronously the [ClientFile]s
 */
object FileManager {

    private val files = mutableListOf<ClientFile>()

    /**
     * Adds specified file to the [FileManager]
     */
    fun add(file: ClientFile) {
        files += file
    }

    /**
     * Equivalent of the [add] method
     */
    operator fun plusAssign(file: ClientFile) {
        files += file
    }

    /**
     * Removes specified file from the [FileManager]
     */
    fun remove(file: ClientFile) {
        files -= file
    }

    /**
     * Equivalent of [remove] method
     */
    operator fun minusAssign(file: ClientFile) {
        files -= file
    }

    /**
     * Invokes the [ClientFile.read] method to each [files]
     */
    fun readAll(async: Boolean = true) {
        if(async) {
            Thread {
                readAll(false)
            }.run()
        } else {
            println("Reading files..")
            files
                .filter { it.file.exists() }
                .forEach(ClientFile::read)
        }
    }

    /**
     * Invokes the [ClientFile.read] method to each [files]
     */
    fun saveAll(async: Boolean = true) {
        if(async) {
            Thread {
                saveAll(false)
            }.run()
        } else {
            files
                .filter { it.file.exists() }
                .forEach(ClientFile::read)
        }
    }

    @Subscribe
    val onClientLoaded = Listener<ClientLoadedEvent> {
        readAll()
    }

    init {
        EventManager.register(this)
    }

}