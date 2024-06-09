package com.client.glowclient.sponge.mixin;

import net.minecraft.client.gui.*;
import com.google.common.collect.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import javax.annotation.*;
import net.minecraft.scoreboard.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.util.text.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ GuiPlayerTabOverlay.class })
public abstract class MixinGuiPlayerTabOverlay extends Gui
{
    @Shadow
    @Final
    private static Ordering<NetworkPlayerInfo> field_175252_a;
    @Shadow
    @Final
    private Minecraft field_175250_f;
    @Shadow
    private ITextComponent field_175256_i;
    @Shadow
    private ITextComponent field_175255_h;
    
    public MixinGuiPlayerTabOverlay() {
        super();
    }
    
    @Shadow
    public abstract String getPlayerName(final NetworkPlayerInfo p0);
    
    @Shadow
    protected abstract void drawPing(final int p0, final int p1, final int p2, final NetworkPlayerInfo p3);
    
    @Shadow
    protected abstract void drawScoreboardValues(final ScoreObjective p0, final int p1, final String p2, final int p3, final int p4, final NetworkPlayerInfo p5);
    
    @Overwrite
    public void renderPlayerlist(final int n, final Scoreboard scoreboard, @Nullable final ScoreObjective scoreObjective) {
        final List<NetworkPlayerInfo> sortedCopy = MixinGuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(this.mc.player.connection.getPlayerInfoMap());
        int max = 0;
        int max2 = 0;
        for (final NetworkPlayerInfo networkPlayerInfo : sortedCopy) {
            max = Math.max(max, this.mc.fontRenderer.getStringWidth(this.getPlayerName(networkPlayerInfo)));
            if (scoreObjective != null && scoreObjective.getRenderType() != IScoreCriteria.EnumRenderType.HEARTS) {
                max2 = Math.max(max2, this.mc.fontRenderer.getStringWidth(" " + scoreboard.getOrCreateScore(networkPlayerInfo.getGameProfile().getName(), scoreObjective).getScorePoints()));
            }
        }
        List<NetworkPlayerInfo> list;
        if (!HookTranslator.v7) {
            list = sortedCopy.subList(0, Math.min(sortedCopy.size(), 80));
        }
        else {
            list = sortedCopy.subList(0, Math.min(sortedCopy.size(), 1000));
        }
        int i;
        int n2;
        int n3;
        for (n2 = (i = list.size()), n3 = 1; i > 20; i = (n2 + n3 - 1) / n3) {
            ++n3;
        }
        final boolean b = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
        int n4;
        if (scoreObjective != null) {
            if (scoreObjective.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) {
                n4 = 90;
            }
            else {
                n4 = max2;
            }
        }
        else {
            n4 = 0;
        }
        final int n5 = Math.min(n3 * ((b ? 9 : 0) + max + n4 + 13), n - 50) / n3;
        final int n6 = n / 2 - (n5 * n3 + (n3 - 1) * 5) / 2;
        int n7 = 10;
        int n8 = n5 * n3 + (n3 - 1) * 5;
        List<String> listFormattedStringToWidth = null;
        if (this.header != null) {
            listFormattedStringToWidth = (List<String>)this.mc.fontRenderer.listFormattedStringToWidth(this.header.getFormattedText(), n - 50);
            final Iterator<String> iterator2 = listFormattedStringToWidth.iterator();
            while (iterator2.hasNext()) {
                n8 = Math.max(n8, this.mc.fontRenderer.getStringWidth((String)iterator2.next()));
            }
        }
        List<String> listFormattedStringToWidth2 = null;
        if (this.footer != null) {
            listFormattedStringToWidth2 = (List<String>)this.mc.fontRenderer.listFormattedStringToWidth(this.footer.getFormattedText(), n - 50);
            final Iterator<String> iterator3 = listFormattedStringToWidth2.iterator();
            while (iterator3.hasNext()) {
                n8 = Math.max(n8, this.mc.fontRenderer.getStringWidth((String)iterator3.next()));
            }
        }
        if (listFormattedStringToWidth != null) {
            drawRect(n / 2 - n8 / 2 - 1, n7 - 1, n / 2 + n8 / 2 + 1, n7 + listFormattedStringToWidth.size() * this.mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);
            for (final String s : listFormattedStringToWidth) {
                this.mc.fontRenderer.drawStringWithShadow(s, (float)(n / 2 - this.mc.fontRenderer.getStringWidth(s) / 2), (float)n7, -1);
                n7 += this.mc.fontRenderer.FONT_HEIGHT;
            }
            ++n7;
        }
        drawRect(n / 2 - n8 / 2 - 1, n7 - 1, n / 2 + n8 / 2 + 1, n7 + i * 9, Integer.MIN_VALUE);
        for (int j = 0; j < n2; ++j) {
            final int n9 = j / i;
            final int n10 = j % i;
            int n11;
            if (HookTranslator.v13) {
                n11 = n6 + n9 * (n5 - 9) + n9 * 5;
            }
            else {
                n11 = n6 + n9 * n5 + n9 * 5;
            }
            final int n12 = n7 + n10 * 9;
            if (!HookTranslator.v13) {
                drawRect(n11, n12, n11 + n5, n12 + 8, 553648127);
            }
            else {
                drawRect(n11, n12, n11 + n5 - 9, n12 + 8, 553648127);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            if (j < list.size()) {
                final NetworkPlayerInfo networkPlayerInfo2 = list.get(j);
                final GameProfile gameProfile = networkPlayerInfo2.getGameProfile();
                if (b && !HookTranslator.v13) {
                    final EntityPlayer playerEntityByUUID = this.mc.world.getPlayerEntityByUUID(gameProfile.getId());
                    final boolean b2 = playerEntityByUUID != null && playerEntityByUUID.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameProfile.getName()) || "Grumm".equals(gameProfile.getName()));
                    this.mc.getTextureManager().bindTexture(networkPlayerInfo2.getLocationSkin());
                    Gui.drawScaledCustomSizeModalRect(n11, n12, 8.0f, (float)(8 + (b2 ? 8 : 0)), 8, 8 * (b2 ? -1 : 1), 8, 8, 64.0f, 64.0f);
                    if (playerEntityByUUID != null && playerEntityByUUID.isWearing(EnumPlayerModelParts.HAT)) {
                        Gui.drawScaledCustomSizeModalRect(n11, n12, 40.0f, (float)(8 + (b2 ? 8 : 0)), 8, 8 * (b2 ? -1 : 1), 8, 8, 64.0f, 64.0f);
                    }
                    n11 += 9;
                }
                String playerName;
                if (!HookTranslator.v11) {
                    playerName = this.getPlayerName(networkPlayerInfo2);
                }
                else {
                    playerName = "";
                }
                if (networkPlayerInfo2.getGameType() == GameType.SPECTATOR) {
                    this.mc.fontRenderer.drawStringWithShadow(TextFormatting.ITALIC + playerName, (float)n11, (float)n12, -1862270977);
                }
                else {
                    this.mc.fontRenderer.drawStringWithShadow(playerName, (float)n11, (float)n12, -1);
                }
                if (scoreObjective != null && networkPlayerInfo2.getGameType() != GameType.SPECTATOR) {
                    final int n13 = n11 + max + 1;
                    final int n14 = n13 + n4;
                    if (n14 - n13 > 5) {
                        this.drawScoreboardValues(scoreObjective, n12, gameProfile.getName(), n13, n14, networkPlayerInfo2);
                    }
                }
                if (!HookTranslator.v12) {
                    this.drawPing(n5, n11 - (b ? 9 : 0), n12, networkPlayerInfo2);
                }
            }
        }
        if (listFormattedStringToWidth2 != null) {
            int n15 = n7 + i * 9 + 1;
            drawRect(n / 2 - n8 / 2 - 1, n15 - 1, n / 2 + n8 / 2 + 1, n15 + listFormattedStringToWidth2.size() * this.mc.fontRenderer.FONT_HEIGHT, Integer.MIN_VALUE);
            for (final String s2 : listFormattedStringToWidth2) {
                this.mc.fontRenderer.drawStringWithShadow(s2, (float)(n / 2 - this.mc.fontRenderer.getStringWidth(s2) / 2), (float)n15, -1);
                n15 += this.mc.fontRenderer.FONT_HEIGHT;
            }
        }
    }
}
