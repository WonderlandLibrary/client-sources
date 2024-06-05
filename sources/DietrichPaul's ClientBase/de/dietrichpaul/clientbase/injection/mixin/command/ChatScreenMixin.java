/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.injection.mixin.command;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.CommandList;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {

    @Shadow public abstract boolean sendMessage(String chatText, boolean addToHistory);

    public ChatScreenMixin(Text title) {
        super(title);
    }

    @Redirect(method = "keyPressed", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;sendMessage(Ljava/lang/String;Z)Z"))
    public boolean onSendMessage(ChatScreen instance, String chatText, boolean addToHistory) {
        String command = chatText.trim();
        if (command.startsWith(Character.toString(CommandList.COMMAND_PREFIX))) {
            client.inGameHud.getChatHud().addToMessageHistory(command); // stupid intellij, client != null >:(
            ClientBase.INSTANCE.getCommandList().process(command);
            return true;
        }
        return sendMessage(chatText, addToHistory);
    }

}
