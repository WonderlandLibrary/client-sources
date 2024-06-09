package com.cout970.fira

import org.apache.logging.log4j.Logger

object Log {
    lateinit var log: Logger

    fun info(vararg msg: Any?) {
        log.info(msg.joinToString(", ") { it.toString() })
    }

    fun error(vararg msg: Any?) {
        log.error(msg.joinToString(", ") { it.toString() })
    }
}