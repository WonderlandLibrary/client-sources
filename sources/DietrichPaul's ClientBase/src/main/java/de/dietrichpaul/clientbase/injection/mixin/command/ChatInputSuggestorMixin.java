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

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.CommandList;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.command.CommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatInputSuggestor.class)
public class ChatInputSuggestorMixin {

    @Unique
    private boolean clientBaseCommand;

    @Redirect(method = "refresh", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;peek()C"))
    public char onRefresh(StringReader instance) {
        char peek = instance.peek();
        clientBaseCommand = peek == CommandList.COMMAND_PREFIX;
        if (clientBaseCommand) return '/';
        return peek;
    }

    @Redirect(method = "refresh", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;getCommandDispatcher()Lcom/mojang/brigadier/CommandDispatcher;"))
    public CommandDispatcher<CommandSource> onParseCommandDispatcher(ClientPlayNetworkHandler instance) {
        if (clientBaseCommand) {
            return ClientBase.INSTANCE.getCommandList().getDispatcher();
        }
        return instance.getCommandDispatcher();
    }

    @Redirect(method = "showUsages", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;getCommandDispatcher()Lcom/mojang/brigadier/CommandDispatcher;"))
    public CommandDispatcher<CommandSource> onUsageCommandDispatcher(ClientPlayNetworkHandler instance) {
        if (clientBaseCommand) {
            return ClientBase.INSTANCE.getCommandList().getDispatcher();
        }
        return instance.getCommandDispatcher();
    }


}
