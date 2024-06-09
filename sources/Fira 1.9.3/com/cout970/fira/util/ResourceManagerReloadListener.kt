package com.cout970.fira.util

import com.cout970.fira.gui.config.ModuleConfig
import net.minecraft.client.resources.IResourceManager
import net.minecraft.client.resources.IResourceManagerReloadListener

object ResourceManagerReloadListener : IResourceManagerReloadListener {

    override fun onResourceManagerReload(resourceManager: IResourceManager) {
        ModuleConfig.createConfig()
    }
}