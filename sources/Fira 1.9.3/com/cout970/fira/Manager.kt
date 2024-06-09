package com.cout970.fira

import com.cout970.fira.gui.Keybinds
import com.cout970.fira.gui.config.ModuleConfig
import com.cout970.fira.modules.*
import com.cout970.fira.util.ResourceManagerReloadListener
import com.cout970.fira.util.Utils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.minecraft.client.resources.IReloadableResourceManager
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Config
import net.minecraftforge.common.config.ConfigManager
import net.minecraftforge.fml.client.event.ConfigChangedEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object Manager {

    val GSON: Gson = GsonBuilder().setPrettyPrinting().create()

    fun init(event: FMLPreInitializationEvent) {
        Log.log = event.modLog
        MinecraftForge.EVENT_BUS.register(Utils)
        MinecraftForge.EVENT_BUS.register(Keybinds)
        MinecraftForge.EVENT_BUS.register(HUD)
        MinecraftForge.EVENT_BUS.register(ChatFilter)
        MinecraftForge.EVENT_BUS.register(ElytraTweaks)
        MinecraftForge.EVENT_BUS.register(Debug)
        MinecraftForge.EVENT_BUS.register(AutoPilot)
        MinecraftForge.EVENT_BUS.register(ElytraFly)
        MinecraftForge.EVENT_BUS.register(PacketFly)
        MinecraftForge.EVENT_BUS.register(ScreenInventory)
        MinecraftForge.EVENT_BUS.register(CrystalPvP)
        MinecraftForge.EVENT_BUS.register(FiraClient)
        MinecraftForge.EVENT_BUS.register(YawLock)
        MinecraftForge.EVENT_BUS.register(this)

        try {
            (Utils.mc.resourceManager as IReloadableResourceManager)
                    .registerReloadListener(ResourceManagerReloadListener)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        Keybinds.init()
        ModuleConfig.createConfig()
    }

    @SubscribeEvent
    fun onConfigChangedEvent(event: ConfigChangedEvent.OnConfigChangedEvent) {
        if (event.modID == MOD_ID) {
            ConfigManager.sync(MOD_ID, Config.Type.INSTANCE)
        }
    }
}