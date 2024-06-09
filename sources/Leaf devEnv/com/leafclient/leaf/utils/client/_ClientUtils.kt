package com.leafclient.leaf.utils.client

import com.leafclient.leaf.management.event.EventManager
import fr.shyrogan.publisher4k.subscription.Subscription
import net.minecraft.client.Minecraft
import net.minecraft.util.text.TextComponentString

/**
 * Prints the specified message in chat as leaf
 */
fun printInfo(message: String)
        = Minecraft.getMinecraft().ingameGUI.chatGUI.printChatMessage(TextComponentString("§a§lLeaf §7$message"))

/**
 * Prints the specified message in chat as leaf
 */
fun printError(message: String)
        = Minecraft.getMinecraft().ingameGUI.chatGUI.printChatMessage(TextComponentString("§a§lLeaf §c$message"))

/**
 * Registers a [Subscription] as a task to the [EventManager], meaning it will
 * run only once.
 */
@Suppress("unchecked_cast")
fun <T: Any> Subscription<T>.registerTask()
        = EventManager.registerTask(this as Subscription<Any>)