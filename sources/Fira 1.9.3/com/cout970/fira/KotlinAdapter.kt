package com.cout970.fira

import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.FMLModContainer
import net.minecraftforge.fml.common.ILanguageAdapter
import net.minecraftforge.fml.common.ModContainer
import net.minecraftforge.fml.relauncher.Side
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

@Suppress("unused")
class KotlinAdapter : ILanguageAdapter {

    init {
        try {
            Class.forName("kotlin.jvm.internal.Intrinsics")
        } catch (error: NoClassDefFoundError) {
            FMLCommonHandler.instance().raiseException(error, "Fira Client requires the Kotlin standard library, please install Forgelin or similar", true)
        }
    }

    override fun supportsStatics() = false

    override fun setProxy(target: Field?, proxyTarget: Class<*>?, proxy: Any?) {
        target?.set(null, proxy)
    }

    override fun getNewInstance(mod: FMLModContainer?, modClass: Class<*>, loader: ClassLoader?, factory: Method?): Any? {
        modClass.declaredFields.forEach { field ->
            if (field.modifiers and Modifier.STATIC != 0 && field.type == modClass) {
                return field.get(null)
            }
        }
        error("Unable to find main instance")
    }

    override fun setInternalProxies(mod: ModContainer?, side: Side?, loader: ClassLoader?) = Unit
}