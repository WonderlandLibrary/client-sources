package com.tangykiwi.kiwiclient.mixin;

import com.mojang.authlib.GameProfile;
import com.tangykiwi.kiwiclient.mixininterface.IChatHUDLine;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = ChatHudLine.class)
public class ChatHudLineMixin implements IChatHUDLine {
    @Shadow
    @Final
    private Text content;
    @Unique private int id;
    @Unique private GameProfile sender;

    @Override
    public String kiwiclient$getText() {
        return content.getString();
    }

    @Override
    public int kiwiclient$getId() {
        return id;
    }

    @Override
    public void kiwiclient$setId(int id) {
        this.id = id;
    }

    @Override
    public GameProfile kiwiclient$getSender() {
        return sender;
    }

    @Override
    public void kiwiclient$setSender(GameProfile profile) {
        sender = profile;
    }
}
