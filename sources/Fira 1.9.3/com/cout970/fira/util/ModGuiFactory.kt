package com.cout970.fira.util

import com.cout970.fira.MOD_ID
import com.cout970.fira.MOD_NAME
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.config.GuiConfig

class ModGuiFactory : IModGuiFactory {

    override fun hasConfigGui(): Boolean = true

    override fun createConfigGui(parentScreen: GuiScreen?): GuiScreen = ModGuiConfig(parentScreen)

    override fun runtimeGuiCategories(): MutableSet<IModGuiFactory.RuntimeOptionCategoryElement> = mutableSetOf()

    override fun initialize(minecraftInstance: Minecraft?) {}
}

class ModGuiConfig(parentScreen: GuiScreen?)
    : GuiConfig(parentScreen, MOD_ID, MOD_NAME)