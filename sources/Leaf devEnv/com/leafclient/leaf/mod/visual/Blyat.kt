package com.leafclient.leaf.mod.visual

import com.leafclient.leaf.event.game.entity.PlayerChatEvent
import com.leafclient.leaf.event.game.network.PacketEvent
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.network.play.server.SPacketChat
import net.minecraft.util.text.TextComponentString

class Blyat: ToggleableMod("Blyat", Category.VISUAL, "") {

    /**
     * Encodes the characters using the [leafAlphabet]
     */
    @Subscribe(-5)
    val onPlayerChat = Listener<PlayerChatEvent> { e ->
        val builder = StringBuilder()
        e.message
            .chars()
            .forEach { char ->
                val latinAlphabetIndex = latinAlphabet.indexOf(char.toChar())
                if(latinAlphabetIndex == -1)
                    builder.append(char.toChar())
                else
                    builder.append(leafAlphabet[latinAlphabetIndex])
            }

        e.message = builder.toString()
    }

    /**
     * Modifies the characters in the received message to decode them
     */
    @Subscribe(-5)
    val onPlayerReceiveChat = Listener<PacketEvent.Receive> { e ->
        val packet = e.packet
        if(packet is SPacketChat) {
            val builder = StringBuilder()
            packet.chatComponent.formattedText
                .chars()
                .forEach { char ->
                    val leafAlphabetIndex = leafAlphabet.indexOf(char.toChar())
                    if(leafAlphabetIndex == -1)
                        builder.append(char.toChar())
                    else
                        builder.append(latinAlphabet[leafAlphabetIndex])
                }

            e.packet = SPacketChat(TextComponentString(builder.toString()))
        }
    }

    companion object {
        private val leafAlphabet =
            arrayOf(
                'Ѡ', 'Ꙭ', 'б', 'Ю', 'у', 'У', 'э', 'ю', 'Ф', 'ф', 'З', 'н', 'й', // titties on the second char is pog
                'm', 'в', 'и', 'Э', 'ь', 'Ь', 'ы', 'Ы', 'к', 'ъ', 'Ъ', 'ш', 'щ', 'Й',
                'Щ', 'ц', 'л', 'Б', 'д', 'Д', 'Г', '֎', 'Л', 'я', 'K', 'Я', 'Ш', 'N',
                'о', 'Ц', 'г', 'м', 'ж', 'Ч', 'ч', 'Ж', 'з', 'п', 'П', 'И'
            )

        private val latinAlphabet =
            arrayOf(
                ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            )
    }

}