package com.leafclient.leaf.management.setting

import kotlin.reflect.KProperty0

/**
 * Represents an object used by the client to store settings
 */
abstract class SettingContainer(property: KProperty0<*>? = null) {

    /**
     * Contains the various settings
     */
    val settings: MutableList<Setting<out Any>> = if(property != null && property.getDelegate() is Setting<*>) {
        (property.getDelegate() as Setting<*>).settings
    } else {
        mutableListOf()
    }

}