package com.leafclient.leaf.command

import com.leafclient.commando.CommandManager
import com.leafclient.commando.exception.CommandException
import com.leafclient.commando.parser.ArgumentParsers
import com.leafclient.leaf.command.parser.KeyParser
import com.leafclient.leaf.event.game.entity.PlayerChatEvent
import com.leafclient.leaf.management.event.EventManager
import com.leafclient.leaf.utils.client.keyboard.Key
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.Minecraft
import net.minecraft.util.text.TextComponentString

object CommandManager: CommandManager<Minecraft>('.', ' ') {

    @Subscribe
    val onChat = Listener { e: PlayerChatEvent ->
        try {
            if(runIfCommand(Minecraft.getMinecraft(), e.message)) {
                e.isCancelled = true
            }
        } catch (ex: CommandException) {
            Minecraft.getMinecraft().ingameGUI.chatGUI
                .printChatMessage(TextComponentString("§c${ex.smallStacktrace}"))
            e.isCancelled = true
        }
    }

    init {
        EventManager.register(this)
        ArgumentParsers.register(Key::class.java, KeyParser())
        ToggleCommand.register()
    }

}