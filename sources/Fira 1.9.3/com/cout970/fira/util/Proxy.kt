package com.cout970.fira.util

import com.cout970.fira.MOD_ID
import net.minecraftforge.common.config.Config
import net.minecraftforge.common.config.ConfigManager
import java.lang.reflect.Field
import kotlin.reflect.KProperty

interface Proxy<T> {
    fun get(): T
    fun set(t: T)

    operator fun getValue(parent: Any, property: KProperty<*>): T = get()

    operator fun setValue(parent: Any, property: KProperty<*>, t: T) = set(t)
}

class FieldProxy<T>(private val field: Field, private val instance: Any?) : Proxy<T> {

    init {
        field.isAccessible = true
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(): T = field.get(instance) as T

    override fun set(t: T) {
        field.set(instance, t)
        ConfigManager.sync(MOD_ID, Config.Type.INSTANCE)
    }
}