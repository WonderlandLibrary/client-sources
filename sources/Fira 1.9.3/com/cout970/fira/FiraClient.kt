package com.cout970.fira

import com.cout970.fira.coremod.Keep
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

const val MOD_ID = "fira_client"
const val MOD_NAME = "Fira Client"
const val MOD_VERSION = "1.9.3"

@Mod(
        modid = MOD_ID,
        name = MOD_NAME,
        version = MOD_VERSION,
        modLanguage = "kotlin",
        modLanguageAdapter = "com.cout970.fira.KotlinAdapter",
        acceptedMinecraftVersions = "[1.12]",
        clientSideOnly = true
)
@Keep
object FiraClient {

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        Manager.init(event)
    }
}