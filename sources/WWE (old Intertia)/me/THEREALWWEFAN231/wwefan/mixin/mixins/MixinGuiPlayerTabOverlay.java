package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.text.*;
import wwe.*;
import net.minecraft.scoreboard.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.network.*;
import java.util.*;
import wwe.utils.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(value = { GuiPlayerTabOverlay.class }, priority = 9999)
public abstract class MixinGuiPlayerTabOverlay
{
    boolean wweUser;
    
    public MixinGuiPlayerTabOverlay() {
        super();
        this.wweUser = false;
    }
    
    @Shadow
    public abstract void func_175245_a(final int p0, final int p1, final int p2, final NetworkPlayerInfo p3);
    
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
    public int onDrawStringWithShadow(final FontRenderer fontRenderer, String string, final float n, final float n2, int rgb) {
        try {
            if (vlwv.xXAB(string)) {
                string = TextFormatting.DARK_RED + StringUtils.stripControlCodes(string);
                rgb = -5636096;
            }
            else if (vlwv.Zqnd().UsVJ(string)) {
                rgb = KrtY.DnMA(1L, 1.0f).getRGB();
            }
        }
        catch (Exception ex) {}
        return fontRenderer.drawStringWithShadow(string, n + (this.wweUser ? 9 : 0), n2, rgb);
    }
    
    @Inject(method = { "renderPlayerlist" }, at = { @At("HEAD") }, cancellable = true)
    public void renderPlayerlist(final int n, final Scoreboard scoreboard, final ScoreObjective scoreObjective, final CallbackInfo callbackInfo) {
        this.wweUser = false;
    }
    
    @Inject(method = { "renderPlayerlist" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetworkPlayerInfo;getGameProfile()Lcom/mojang/authlib/GameProfile;", ordinal = 1) }, locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void renderPlayerlist(final int n, final Scoreboard scoreboard, final ScoreObjective scoreObjective, final CallbackInfo callbackInfo, final NetHandlerPlayClient netHandlerPlayClient, final List list, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b, final int n7, final int n8, final int n9, final int n10, final int n11, final List list2, final List list3, final int n12, final int n13, final int n14, final int n15, final int n16, final NetworkPlayerInfo networkPlayerInfo) {
        if (vlwv.xXAB(networkPlayerInfo.getGameProfile().getName())) {
            this.wweUser = true;
            Wrapper.mc.getTextureManager().bindTexture(new ResourceLocation("textures/wwe/wwelogo.png"));
            Gui.drawScaledCustomSizeModalRect(n15 + 9, n16, 8.0f, 8.0f, 8, 8, 8, 8, 8.0f, 8.0f);
        }
    }
    
    @Inject(method = { "renderPlayerlist" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;drawPing(IIILnet/minecraft/client/network/NetworkPlayerInfo;)V") }, cancellable = true)
    public void renderPlayerlistWWEUser(final int n, final Scoreboard scoreboard, final ScoreObjective scoreObjective, final CallbackInfo callbackInfo) {
        this.wweUser = false;
    }
}
