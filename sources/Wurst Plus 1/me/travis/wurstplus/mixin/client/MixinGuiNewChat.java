package me.travis.wurstplus.mixin.client;

import me.travis.wurstplus.util.ChatUtils;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.gui.Gui;
import me.travis.wurstplus.module.ModuleManager;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiNewChat.class })
public abstract class MixinGuiNewChat
{
        @ModifyVariable(method = "printChatMessageWithOptionalDeletion", at = @At("HEAD"))
        private net.minecraft.util.text.ITextComponent addTimestamp(net.minecraft.util.text.ITextComponent componentIn)
        {
            if (ModuleManager.isModuleEnabled("ChatTimeStamps"))
            {
                net.minecraft.util.text.TextComponentString newComponent = new net.minecraft.util.text.TextComponentString(ChatUtils.getChatTimestamp());
                newComponent.appendSibling(componentIn);
                return newComponent;
            }

            return componentIn;
        }

    @Redirect(method = "drawChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V", ordinal = 0))
    private void overrideChatBackgroundColour(int left, int top, int right, int bottom, int color)
    {
        if (!ModuleManager.isModuleEnabled("RemoveChatBox"))
        {
            Gui.drawRect(left, top, right, bottom, color);
        }
    }
}