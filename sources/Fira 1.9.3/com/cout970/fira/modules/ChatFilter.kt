package com.cout970.fira.modules

import com.cout970.fira.Config
import com.cout970.fira.Log
import com.cout970.fira.util.Utils
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.util.text.ChatType
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.client.event.ClientChatEvent
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.random.Random

/**
 * Filtro de mensajes de spam del chat
 */
object ChatFilter {

    fun hud(): String = "ChatFilter ${Config.Chat.chatPrivateMsgTarget}"

    @SubscribeEvent
    fun sendChatMsg(event: ClientChatEvent) {

        if (!Config.Chat.directChat) return

        // Ignorar comandos
        if (event.message.startsWith("/")) {
            return
        }

        // Chat general
        if (event.message.startsWith("!")) {
            event.message = event.message.substring(1)
            return
        }

        // Chat privado
        """^@([^\s]*) (.*)$""".toRegex()
                .matchEntire(event.message)
                ?.let { result ->
                    val (user, msg) = result.destructured

                    Config.Chat.chatPrivateMsgTarget = user
                    event.message = "/tell $user $msg"
                    return
                }

        // Chat privado persistente
        if (Config.Chat.chatPrivateMsgTarget.isNotBlank()) {
            event.message = "/tell ${Config.Chat.chatPrivateMsgTarget} ${event.message}"
            return
        }

        Utils.chatMsg("${TextFormatting.RED}${I18n.format("text.fira_client.chat_filter.warning1")}")
        Utils.chatMsg("${TextFormatting.RED}${I18n.format("text.fira_client.chat_filter.warning2")}")
        Utils.ping()

        event.isCanceled = true
    }

    @SubscribeEvent
    fun onChatMsg(event: ClientChatReceivedEvent) {
        if (!Config.Chat.enableChatFilter) return

        // Solo filtramos mensajes de los usuarios
        if (event.type != ChatType.CHAT) return

        // Comprobamos varias propiedades del mensaje
        val purple = event.message.formattedText.contains(TextFormatting.LIGHT_PURPLE.toString())
        val green = event.message.formattedText.contains(TextFormatting.GREEN.toString())
        val link = event.message.unformattedText.matches(""".*http(s)?://\w+\.?\w+.*""".toRegex())

        // Si el texto es verde (span), azul (muertes), o contiene un link (span) se censura
        val spam = green || link
        val death = event.message.formattedText.contains(TextFormatting.DARK_AQUA.toString())

        // El mensaje a examinar
        val msg = event.message

        // Mensaje directo
        if (purple) {
            val receiveRegex = "^([^\\s]*) whispers: (.*)$".toRegex()
            val sendRegex = "^to ([^\\s]*): (.*)$".toRegex()
            val receiveMatch = receiveRegex.matchEntire(msg.unformattedText)

            // Si el formato coincide
            if (receiveMatch != null) {
                val (name, text) = receiveMatch.destructured
                val newText = processText(name, text, Config.Chat.chatPrivateMsgFormat)

                event.message = TextComponentString(newText)

                if (Config.Chat.enablePing) {
                    // Sonido de mensaje
                    Utils.ping()
                }
                return
            }

            val sendMatch = sendRegex.matchEntire(msg.unformattedText)

            // Si el formato coincide
            if (sendMatch != null) {
                val (otherName, text) = sendMatch.destructured
                val ownName = Minecraft.getMinecraft().player.name
                val newText = processText("$ownName -> $otherName", text, Config.Chat.chatPrivateMsgFormat)

                event.message = TextComponentString(newText)
                return
            }
        } else if (Config.Chat.chatShowOnlyPrivate) {
            // Evitamos que se muestre
            event.isCanceled = true
            return
        }

        // Mensaje de spam
        if (spam) {
            // Texto a mostrar en chat o log
            val newText = "${TextFormatting.GRAY}[Spam] " + event.message.formattedText

            if (Config.Chat.showChatSpam) {
                // Mostramos el mensaje modificado
                event.message = TextComponentString(newText)
                return
            } else if (event.message.unformattedText.parseLine()?.first !in Config.Chat.spamWhitelist) {

                // Conservamos el mensaje en el log del juego
                Log.info(newText)

                // Evitamos que se muestre
                event.isCanceled = true
                return
            }
            // Resto de la funcion
        }

        // Mensaje de muerte
        if (death && !Config.Chat.showChatDeaths) {
            // Evitamos que se muestre
            event.isCanceled = true
            return
        }

        // Mensaje de tipo raro, ignorando
        if (msg !is TextComponentString) return

        // Separamos el nombre del usuario y el contenido del mensaje
        val match = msg.unformattedText.parseLine() ?: return
        var (name, text) = match

        if (name in Config.Chat.chatBlacklist) {
            // Conservamos el mensaje en el log del juego
            Log.info(msg.formattedText)

            // Evitamos que se muestre
            event.isCanceled = true
        }

        Config.Chat.chatCensored.forEach { word ->
            text = text.replace(word, "*".repeat(word.length))
        }

        // Creamos un contenido nuevo con el formato que nos guste
        val newText = processText(name, text, Config.Chat.chatMsgFormat)

        // Modificamos el mensaje
        event.message = TextComponentString(newText)
    }

    /**
     * Separa el nombre del usuario del resto del mensaje
     */
    private fun String.parseLine(): Pair<String, String>? {
        // Separamos el nombre del usuario y el contenido del mensaje
        val regex = "^<([^>]+)> (.*)$".toRegex()
        val match = regex.matchEntire(this) ?: return null

        return match.groupValues[1] to match.groupValues[2]
    }

    /**
     * Reemplaza valores del patron [chatMsgFormat]
     */
    private fun processText(name: String, text: String, chatMsgFormat: String): String {
        var msg = chatMsgFormat

        msg = msg.replace("{BLACK}", TextFormatting.BLACK.toString())
        msg = msg.replace("{DARK_BLUE}", TextFormatting.DARK_BLUE.toString())
        msg = msg.replace("{DARK_GREEN}", TextFormatting.DARK_GREEN.toString())
        msg = msg.replace("{DARK_AQUA}", TextFormatting.DARK_AQUA.toString())
        msg = msg.replace("{DARK_RED}", TextFormatting.DARK_RED.toString())
        msg = msg.replace("{DARK_PURPLE}", TextFormatting.DARK_PURPLE.toString())
        msg = msg.replace("{GOLD}", TextFormatting.GOLD.toString())
        msg = msg.replace("{GRAY}", TextFormatting.GRAY.toString())
        msg = msg.replace("{DARK_GRAY}", TextFormatting.DARK_GRAY.toString())
        msg = msg.replace("{BLUE}", TextFormatting.BLUE.toString())
        msg = msg.replace("{GREEN}", TextFormatting.GREEN.toString())
        msg = msg.replace("{AQUA}", TextFormatting.AQUA.toString())
        msg = msg.replace("{RED}", TextFormatting.RED.toString())
        msg = msg.replace("{LIGHT_PURPLE}", TextFormatting.LIGHT_PURPLE.toString())
        msg = msg.replace("{YELLOW}", TextFormatting.YELLOW.toString())
        msg = msg.replace("{WHITE}", TextFormatting.WHITE.toString())
        msg = msg.replace("{OBFUSCATED}", TextFormatting.OBFUSCATED.toString())
        msg = msg.replace("{BOLD}", TextFormatting.BOLD.toString())
        msg = msg.replace("{STRIKETHROUGH}", TextFormatting.STRIKETHROUGH.toString())
        msg = msg.replace("{UNDERLINE}", TextFormatting.UNDERLINE.toString())
        msg = msg.replace("{ITALIC}", TextFormatting.ITALIC.toString())
        msg = msg.replace("{RESET}", TextFormatting.RESET.toString())

        msg = msg.replaceFirst("@name", name)
        msg = msg.replaceFirst("@text", text)

        return msg
    }
}