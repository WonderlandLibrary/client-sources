package com.client.glowclient.sponge.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import com.mojang.authlib.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.text.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.text.event.*;
import java.util.*;
import net.minecraftforge.event.*;

@Mixin({ EntityPlayer.class })
public abstract class MixinEntityPlayer extends EntityLivingBase
{
    @Shadow
    private GameProfile field_146106_i;
    @Shadow
    private final Collection<ITextComponent> suffixes;
    @Shadow
    private final Collection<ITextComponent> prefixes;
    @Shadow
    private String displayname;
    @Shadow
    public float eyeHeight;
    
    public MixinEntityPlayer() {
        super((World)HookTranslator.mc.world);
        this.suffixes = new LinkedList<ITextComponent>();
        this.prefixes = new LinkedList<ITextComponent>();
        this.eyeHeight = this.getDefaultEyeHeight();
    }
    
    @Shadow
    public float getDefaultEyeHeight() {
        return 1.62f;
    }
    
    @Overwrite
    public String getName() {
        HookTranslator.m4(this.gameProfile);
        return this.gameProfile.getName();
    }
    
    @Inject(method = { "isPushedByWater" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsPushedByWater(final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v16) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Overwrite
    public ITextComponent getDisplayName() {
        final TextComponentString textComponentString = new TextComponentString("");
        if (!this.prefixes.isEmpty()) {
            final Iterator<ITextComponent> iterator = this.prefixes.iterator();
            while (iterator.hasNext()) {
                ((ITextComponent)textComponentString).appendSibling((ITextComponent)iterator.next());
            }
        }
        ((ITextComponent)textComponentString).appendSibling((ITextComponent)new TextComponentString(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getDisplayNameString())));
        if (!this.suffixes.isEmpty()) {
            final Iterator<ITextComponent> iterator2 = this.suffixes.iterator();
            while (iterator2.hasNext()) {
                ((ITextComponent)textComponentString).appendSibling((ITextComponent)iterator2.next());
            }
        }
        ((ITextComponent)textComponentString).getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
        ((ITextComponent)textComponentString).getStyle().setHoverEvent(this.getHoverEvent());
        ((ITextComponent)textComponentString).getStyle().setInsertion(this.getName());
        return (ITextComponent)textComponentString;
    }
    
    public String getDisplayNameString() {
        if (HookTranslator.m3()) {
            HookTranslator.m5(this.getName());
            return "";
        }
        return this.displayname = ForgeEventFactory.getPlayerDisplayName((EntityPlayer)EntityPlayer.class.cast(this), this.getName());
    }
}
