package com.leafclient.leaf.command

import com.leafclient.commando.context.CommandContext
import com.leafclient.commando.executor.CommandExecutor
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.mod.ModManager
import com.leafclient.leaf.utils.client.printError
import com.leafclient.leaf.utils.client.printInfo
import net.minecraft.client.Minecraft

object ToggleCommand: CommandExecutor<Minecraft> {

    override fun execute(mc: Minecraft, context: CommandContext) {
        val name = context.arg<String>("modName")
        val mod = ModManager.mods
            .firstOrNull { it.label.equals(name, true) }

        if(mod == null || mod !is ToggleableMod) {
            printError("Cannot find $name as a toggleable mod!")
            return
        }

        mod.isRunning = !mod.isRunning
        printInfo("§b${mod.label} §7running state is now §b${mod.isRunning}")
    }

    fun register() = CommandManager
        .newCommand("toggle", "t")
        .description("Toggles a mod")
        .argument("modName", String::class.java)
        .executor(ToggleCommand)
        .done()

}