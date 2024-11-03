package net.silentclient.client.mixin.mixins;

import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;
import net.silentclient.client.Client;
import net.silentclient.client.cosmetics.StaticResourceLocation;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mixin.ducks.TextureManagerExt;
import net.silentclient.client.mods.render.TabMod;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.Players;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiPlayerTabOverlay.class)
public abstract class GuiPlayerTabOverlayMixin {
    @Shadow @Final private Minecraft mc;

    @Shadow @Final private static Ordering<NetworkPlayerInfo> field_175252_a;

    @Shadow public abstract String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn);

    @Shadow private IChatComponent header;

    @Shadow private IChatComponent footer;

    @Shadow protected abstract void drawScoreboardValues(ScoreObjective p_175247_1_, int p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_);

    @Shadow protected abstract void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn);

    /**
     * @author kirillsaint
     * @reason Custom Tab
     */
    @Overwrite
    public void renderPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn)
    {
        try {
            NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
            List<NetworkPlayerInfo> list = field_175252_a.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());
            int i = 0;
            int j = 0;

            for (NetworkPlayerInfo networkplayerinfo : list)
            {
                boolean isSilent = false;
                try {
                    EntityPlayer entityplayer = networkplayerinfo.getGameProfile().getName().equalsIgnoreCase(Client.getInstance().getAccount().getUsername()) ? this.mc.thePlayer : this.mc.theWorld.getPlayerEntityByUUID(networkplayerinfo.getGameProfile().getId());
                    if(entityplayer != null) {
                        isSilent = Boolean.parseBoolean(Players.getPlayerStatus(networkplayerinfo.getGameProfile().getName().equalsIgnoreCase(Client.getInstance().getAccount().getUsername()), networkplayerinfo.getGameProfile().getName(), entityplayer.getUniqueID(), (AbstractClientPlayer) entityplayer));
                    }
                } catch (Exception err) {

                }
                int k = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkplayerinfo)) + (isSilent && (!Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() || Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Show Nametag Icons").getValBoolean()) ? 9 : 0);
                i = Math.max(i, k);
                if (scoreObjectiveIn != null && scoreObjectiveIn.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                    k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getValueFromObjective(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                    j = Math.max(j, k);
                }
            }

            list = list.subList(0, Math.min(list.size(), 80));
            int l3 = list.size();
            int i4 = l3;
            int j4;

            for (j4 = 1; i4 > 20; i4 = (l3 + j4 - 1) / j4)
            {
                ++j4;
            }

            boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
            int l;

            if (scoreObjectiveIn != null)
            {
                if (scoreObjectiveIn.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS)
                {
                    l = 90;
                }
                else
                {
                    l = j;
                }
            }
            else
            {
                l = 0;
            }

            int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
            int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
            int k1 = 10;
            int l1 = i1 * j4 + (j4 - 1) * 5;
            List<String> list1 = null;
            List<String> list2 = null;

            if (this.header != null && !(Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Disable Header").getValBoolean()))
            {
                list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);

                for (String s : list1)
                {
                    l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s));
                }
            }

            if (this.footer != null && !(Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Disable Footer").getValBoolean()))
            {
                list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);

                for (String s2 : list2)
                {
                    l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s2));
                }
            }

            boolean background = !Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() || Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Background").getValBoolean();

            if (list1 != null)
            {
                if(background) {
                    Gui.drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);
                }

                for (String s3 : list1)
                {
                    int i2 = this.mc.fontRendererObj.getStringWidth(s3);
                    this.mc.fontRendererObj.drawStringWithShadow(s3, (float)(width / 2 - i2 / 2), (float)k1, -1);
                    k1 += this.mc.fontRendererObj.FONT_HEIGHT;
                }

                ++k1;
            }

            if(background) {
                Gui.drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + i4 * 9, Integer.MIN_VALUE);
            }

            for (int k4 = 0; k4 < l3; ++k4)
            {
                int l4 = k4 / i4;
                int i5 = k4 % i4;
                int j2 = j1 + l4 * i1 + l4 * 5;
                int k2 = k1 + i5 * 9;
                if(background) {
                    Gui.drawRect(j2, k2, j2 + i1, k2 + 8, 553648127);
                }
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                if (k4 < list.size())
                {
                    NetworkPlayerInfo networkplayerinfo1 = (NetworkPlayerInfo)list.get(k4);
                    String s1 = this.getPlayerName(networkplayerinfo1);
                    GameProfile gameprofile = networkplayerinfo1.getGameProfile();

                    boolean isSilent = false;
                    try {
                        EntityPlayer entityplayer = gameprofile.getName().equalsIgnoreCase(Client.getInstance().getAccount().getUsername()) ? this.mc.thePlayer : this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
                        if(entityplayer != null) {
                            isSilent = Boolean.parseBoolean(Players.getPlayerStatus(gameprofile.getName().equalsIgnoreCase(Client.getInstance().getAccount().getUsername()), gameprofile.getName(), entityplayer.getUniqueID(), (AbstractClientPlayer) entityplayer));
                        }
                        if(isSilent && entityplayer != null && (!Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() || Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Show Nametag Icons").getValBoolean())) {
                            ColorUtils.setColor(-1);
                            this.mc.getTextureManager().bindTexture(((AbstractClientPlayerExt) entityplayer).silent$getPlayerIcon().getLocation());
                            Gui.drawModalRectWithCustomSizedTexture(j2, k2, 8, 8, 8, 8, 8, 8);
                            j2 += 9;
                        }
                    } catch (Exception err) {

                    }

                    if (flag)
                    {
                        EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
                        boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm"));
                        ((TextureManagerExt) this.mc.getTextureManager()).waitBindTexture(new StaticResourceLocation(networkplayerinfo1.getLocationSkin().getResourcePath()), new StaticResourceLocation(DefaultPlayerSkin.getDefaultSkinLegacy().getResourcePath()));
                        int l2 = 8 + (flag1 ? 8 : 0);
                        int i3 = 8 * (flag1 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0F, (float)l2, 8, i3, 8, 8, 64.0F, 64.0F);
                        if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT))
                        {
                            int j3 = 8 + (flag1 ? 8 : 0);
                            int k3 = 8 * (flag1 ? -1 : 1);
                            Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0F, (float)j3, 8, k3, 8, 8, 64.0F, 64.0F);
                        }

                        j2 += 9;
                    }

                    if (networkplayerinfo1.getGameType() == WorldSettings.GameType.SPECTATOR)
                    {
                        s1 = EnumChatFormatting.ITALIC + s1;
                        this.mc.fontRendererObj.drawStringWithShadow(s1, (float)j2, (float)k2, -1862270977);
                    }
                    else
                    {
                        this.mc.fontRendererObj.drawStringWithShadow(s1, (float)j2, (float)k2, -1);
                    }

                    if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != WorldSettings.GameType.SPECTATOR)
                    {
                        int k5 = (j2 - (isSilent && (!Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() || Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Show Nametag Icons").getValBoolean()) ? 9 : 0)) + i + 1;
                        int l5 = k5 + l;

                        if (l5 - k5 > 5)
                        {
                            this.drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                        }
                    }

                    this.drawPing(i1, j2 - (flag ? 9 : 0) - (isSilent && (!Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() || Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Show Nametag Icons").getValBoolean()) ? 9 : 0), k2, networkplayerinfo1);
                }
            }

            if (list2 != null)
            {
                k1 = k1 + i4 * 9 + 1;
                if(background) {
                    Gui.drawRect(width / 2 - l1 / 2 - 1, k1 - 1, width / 2 + l1 / 2 + 1, k1 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);
                }

                for (String s4 : list2)
                {
                    int j5 = this.mc.fontRendererObj.getStringWidth(s4);
                    this.mc.fontRendererObj.drawStringWithShadow(s4, (float)(width / 2 - j5 / 2), (float)k1, -1);
                    k1 += this.mc.fontRendererObj.FONT_HEIGHT;
                }
            }
        } catch(Exception err) {
            Client.logger.catching(err);
        }
    }

    public void drawModalRectWithCustomSizedTexture(final float x, final float y, final float u, final float v, final float width, final float height, final float textureWidth, final float textureHeight) {
        final float f = 1.0f / textureWidth;
        final float f2 = 1.0f / textureHeight;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(4, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + height) * f2).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex((u + width) * f, (v + height) * f2).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + width) * f, v * f2).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + width) * f, v * f2).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * f, v * f2).endVertex();
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + height) * f2).endVertex();
        tessellator.draw();
    }

    private int getPingWidth(NetworkPlayerInfo networkPlayerInfoIn) {
        if(!Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Show Ping Numbers").getValBoolean()) {
            return 10;
        }
        int ping = 0;
        EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfoIn.getGameProfile().getId());
        if(entityplayer == null || entityplayer != mc.thePlayer) {
            ping = networkPlayerInfoIn.getResponseTime();
        }

        if(entityplayer != null && entityplayer == mc.thePlayer) {
            ping = Client.getInstance().ping;
        }

        return mc.fontRendererObj.getStringWidth(String.valueOf(ping));
    }

    @Inject(method = "drawPing", at = @At("HEAD"), cancellable = true)
    public void numbersPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn, CallbackInfo ci) {
        if(Client.getInstance().getModInstances().getModByClass(TabMod.class).isEnabled() && Client.getInstance().getSettingsManager().getSettingByClass(TabMod.class, "Show Ping Numbers").getValBoolean()) {
            EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(networkPlayerInfoIn.getGameProfile().getId());
            int ping = 0;
            if(entityplayer == null || entityplayer != mc.thePlayer) {
                ping = networkPlayerInfoIn.getResponseTime();
            }

            if(entityplayer != null && entityplayer == mc.thePlayer) {
                ping = Client.getInstance().ping;
            }

            int x = (p_175245_2_ + p_175245_1_) - (mc.fontRendererObj.getStringWidth(String.valueOf(ping)) >> 1) - 2;
            int y = p_175245_3_ + 2;

            int color;

            if (ping > 500) {
                color = 11141120;
            } else if (ping > 300) {
                color = 11184640;
            } else if (ping > 200) {
                color = 11193344;
            } else if (ping > 135) {
                color = 2128640;
            } else if (ping > 70) {
                color = 39168;
            } else if (ping >= 0) {
                color = 47872;
            } else {
                color = 11141120;
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            mc.fontRendererObj.drawStringWithShadow("   " + (ping == 0 ? "?" : ping), (2 * x) - 10, 2 * y, color);
            GlStateManager.scale(2.0f, 2.0f, 2.0f);
            GlStateManager.popMatrix();
            ci.cancel();
        }
    }
}
