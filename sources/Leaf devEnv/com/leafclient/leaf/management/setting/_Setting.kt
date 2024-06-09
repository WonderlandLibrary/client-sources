package com.leafclient.leaf.management.setting

import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

/**
 * Creates a [Setting] instance and adds it into the settings list
 */
fun <T: Any> SettingContainer.setting(label: String, description: String, value: T)
        = Setting(label, description, value).also { settings.add(it) }

/**
 * Creates a [Setting] instance and adds it into the settings list
 */
fun <T: Any> SettingContainer.setting(label: String, description: String, value: T, applicator: SettingApplicator<T>)
        = Setting(label, description, value).also {
    applicator.invoke(it)
    settings.add(it)
}

/**
 * Creates a [Setting] instance and adds it into the settings list
 */
fun <T: Any> SettingContainer.setting(label: String, value: T)
        = Setting(label, "", value).also { settings.add(it) }

/**
 * Creates a [Setting] instance and adds it into the settings list
 */
fun <T: Any> SettingContainer.setting(label: String, value: T, applicator: SettingApplicator<T>)
        = Setting(label, "", value).also {
    applicator.invoke(it)
    settings.add(it)
}

/**
 * Creates a [Setting] instance and adds it into the settings list,
 * this [Setting] is only made to contain sub settings
 */
fun SettingContainer.settingContainer(label: String)
        = Setting(label, "", emptyArray<Any>()).also {
    settings.add(it)
}

/**
 * Creates a [Setting] instance and adds it into the settings list,
 * this [Setting] is only made to contain sub settings
 */
fun SettingContainer.settingContainer(label: String, description: String)
        = Setting(label, description, emptyArray<Any>()).also {
    settings.add(it)
}

/**
 * Forces casting of this [KProperty0] into a setting
 */
@Suppress("unchecked_cast")
val <T: Any> KProperty0<T>.asSetting: Setting<T>
    get() {
        this.isAccessible = true
        return this.getDelegate() as Setting<T>
    }

/**
 * A type alias
 */
typealias SettingApplicator<T> = Setting<T>.() -> Unit