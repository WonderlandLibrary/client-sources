/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.friendsystem.Friend;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.ReplaceStrUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public class GuiPlayerTabOverlay
extends Gui {
    public static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new PlayerComparator());
    private final Minecraft mc;
    private final GuiIngame guiIngame;
    private ITextComponent footer;
    private ITextComponent header;
    private long lastTimeOpened;
    private boolean isBeingRendered;
    public static String staffname;

    public GuiPlayerTabOverlay(Minecraft mcIn, GuiIngame guiIngameIn) {
        this.mc = mcIn;
        this.guiIngame = guiIngameIn;
    }

    public static List<EntityPlayer> getPlayers() {
        List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(Minecraft.player.connection.getPlayerInfoMap());
        ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
        for (NetworkPlayerInfo player : list) {
            if (player == null) continue;
            players.add(Minecraft.getMinecraft().world.getPlayerEntityByName(player.getGameProfile().getName()));
        }
        return players;
    }

    public static List<EntityPlayer> getPlayers2() {
        List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(Minecraft.player.connection.getPlayerInfoMap());
        ArrayList<EntityPlayer> players = new ArrayList<EntityPlayer>();
        for (NetworkPlayerInfo player : list) {
            if (player == null) continue;
            players.add(Minecraft.getMinecraft().world.getPlayerEntityByName(player.getGameProfile().getName()));
        }
        return players;
    }

    public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }

    public void updatePlayerList(boolean willBeRendered) {
        if (willBeRendered && !this.isBeingRendered) {
            this.lastTimeOpened = Minecraft.getSystemTime();
        }
        this.isBeingRendered = willBeRendered;
    }

    /*
     * WARNING - void declaration
     */
    public void renderPlayerlist(int width, Scoreboard scoreboardIn, @Nullable ScoreObjective scoreObjectiveIn) {
        block40: {
            boolean flag;
            int l3;
            block39: {
                void var19_44;
                boolean flag2;
                int l32;
                if (!Panic.stop) break block39;
                NetHandlerPlayClient nethandlerplayclient = Minecraft.player.connection;
                List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
                int i = 0;
                int j = 0;
                for (NetworkPlayerInfo networkplayerinfo : list) {
                    int k = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkplayerinfo));
                    i = Math.max(i, k);
                    if (scoreObjectiveIn == null || scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) continue;
                    k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getOrCreateScore(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                    j = Math.max(j, k);
                }
                list = list.subList(0, Math.min(list.size(), 80));
                int i4 = l32 = list.size();
                int j4 = 1;
                while (i4 > 20) {
                    i4 = (l32 + ++j4 - 1) / j4;
                }
                boolean bl = flag2 = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
                int l = scoreObjectiveIn != null ? (scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS ? 90 : j) : 0;
                int i1 = Math.min(j4 * ((flag2 ? 9 : 0) + i + l + 13), width - 50) / j4;
                int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
                int k1 = 10;
                int l1 = i1 * j4 + (j4 - 1) * 5;
                List<String> list1 = null;
                if (this.header != null) {
                    list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);
                    for (String string : list1) {
                        l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(string));
                    }
                }
                List<String> list2 = null;
                if (this.footer != null) {
                    list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);
                    for (String s1 : list2) {
                        l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s1));
                    }
                }
                if (list1 != null) {
                    GuiPlayerTabOverlay.drawRect(width / 2 - l1 / 2 - 1, (double)(k1 - 1), (double)(width / 2 + l1 / 2 + 1), (double)(k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT), Integer.MIN_VALUE);
                    for (String s2 : list1) {
                        int i2 = this.mc.fontRendererObj.getStringWidth(s2);
                        this.mc.fontRendererObj.drawStringWithShadow(s2, width / 2 - i2 / 2, k1, -1);
                        k1 += this.mc.fontRendererObj.FONT_HEIGHT;
                    }
                    ++k1;
                }
                GuiPlayerTabOverlay.drawRect(width / 2 - l1 / 2 - 1, (double)(k1 - 1), (double)(width / 2 + l1 / 2 + 1), (double)(k1 + i4 * 9), Integer.MIN_VALUE);
                boolean bl2 = false;
                while (var19_44 < l32) {
                    void l4 = var19_44 / i4;
                    void i5 = var19_44 % i4;
                    int j2 = j1 + l4 * i1 + l4 * 5;
                    int k2 = k1 + i5 * 9;
                    GuiPlayerTabOverlay.drawRect(j2, (double)k2, (double)(j2 + i1), (double)(k2 + 8), 0x20FFFFFF);
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                    GlStateManager.enableAlpha();
                    GlStateManager.enableBlend();
                    GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                    if (var19_44 < list.size()) {
                        int k5;
                        int l5;
                        NetworkPlayerInfo networkplayerinfo1 = list.get((int)var19_44);
                        GameProfile gameprofile = networkplayerinfo1.getGameProfile();
                        if (flag2) {
                            EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                            boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
                            this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                            int l2 = 8 + (flag1 ? 8 : 0);
                            int i3 = 8 * (flag1 ? -1 : 1);
                            Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0f, l2, 8.0f, i3, 8.0f, 8.0f, 64.0f, 64.0f);
                            if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                                int j3 = 8 + (flag1 ? 8 : 0);
                                int k3 = 8 * (flag1 ? -1 : 1);
                                Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0f, j3, 8.0f, k3, 8.0f, 8.0f, 64.0f, 64.0f);
                            }
                            j2 += 9;
                        }
                        String s4 = this.getPlayerName(networkplayerinfo1);
                        if (networkplayerinfo1.getGameType() == GameType.SPECTATOR) {
                            this.mc.fontRendererObj.drawStringWithShadow(TextFormatting.ITALIC + s4, j2, k2, -1862270977);
                        } else {
                            this.mc.fontRendererObj.drawStringWithShadow(s4, j2, k2, -1);
                        }
                        if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != GameType.SPECTATOR && (l5 = (k5 = j2 + i + 1) + l) - k5 > 5) {
                            this.drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                        }
                        this.drawPing2(i1, j2 - (flag2 ? 9 : 0), k2, networkplayerinfo1, 255);
                    }
                    ++var19_44;
                }
                if (list2 == null) break block40;
                k1 = k1 + i4 * 9 + 1;
                GuiPlayerTabOverlay.drawRect(width / 2 - l1 / 2 - 1, (double)(k1 - 1), (double)(width / 2 + l1 / 2 + 1), (double)(k1 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT), Integer.MIN_VALUE);
                for (String s3 : list2) {
                    int j5 = this.mc.fontRendererObj.getStringWidth(s3);
                    this.mc.fontRendererObj.drawStringWithShadow(s3, width / 2 - j5 / 2, k1, -1);
                    k1 += this.mc.fontRendererObj.FONT_HEIGHT;
                }
                break block40;
            }
            NetHandlerPlayClient nethandlerplayclient = Minecraft.player.connection;
            List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
            int i = 0;
            int j = 0;
            for (NetworkPlayerInfo networkplayerinfo : list) {
                int k = this.mc.fontRendererObj.getStringWidth("                        " + this.getPlayerName(networkplayerinfo));
                i = Math.max(i, k);
                if (scoreObjectiveIn == null || scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) continue;
                k = Fonts.mntsb_15.getStringWidth("                        " + scoreboardIn.getOrCreateScore(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
                j = Math.max(j, k);
            }
            list = list.subList(0, Math.min(list.size(), 180));
            int i4 = l3 = list.size();
            int j4 = 1;
            while (i4 > 40) {
                i4 = (l3 + ++j4 - 1) / j4;
            }
            boolean bl = flag = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
            int l = scoreObjectiveIn != null ? (scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS ? 90 : j) : 0;
            int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
            int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
            int k1 = 10;
            int l1 = i1 * j4 + (j4 - 1) * 5;
            List<String> list1 = null;
            if (this.header != null) {
                list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);
                for (String string : list1) {
                    l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(string));
                }
            }
            Object list2 = null;
            if (this.footer != null) {
                list2 = Fonts.neverlose500_17.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);
                Iterator iterator = list2.iterator();
                while (iterator.hasNext()) {
                    String s1 = (String)iterator.next();
                    l1 = Math.max(l1, Fonts.neverlose500_17.getStringWidth(s1));
                }
            }
            float f = width / 2 - l1 / 2 - 1 + 15;
            float y1 = k1 + 20;
            float p1 = k1 + i4 * 9 + 1;
            float x2 = (float)(width / 2 + l1 / 2) + 9.5f - 15.0f;
            float y2 = 0.0f;
            if (list2 != null) {
                y2 = p1 + (float)(list2.size() * this.mc.fontRendererObj.FONT_HEIGHT) + (float)(k1 + 2 - k1 - 2);
            }
            int alpher = (int)(GuiIngame.tabAlpha * 1.25f);
            int color6 = ColorUtils.swapAlpha(ColorUtils.getColor(255, 255, 255), MathUtils.clamp((float)alpher * 1.25f, 26.0f, 255.0f));
            if (GuiIngame.tabAlpha > 26.0f) {
                if (GuiPlayerTabOverlay.getPlayers().size() != 1) {
                    Fonts.mntsb_20.drawStringWithShadow("Online: " + GuiPlayerTabOverlay.getPlayers().size(), f - 10.0f, y1 - 8.0f, color6);
                }
                if (!this.mc.isSingleplayer()) {
                    Fonts.mntsb_20.drawStringWithShadow("Ip: " + this.mc.getCurrentServerData().serverIP, x2 - (float)Fonts.mntsb_20.getStringWidth("Ip: " + this.mc.getCurrentServerData().serverIP) + 6.0f, y1 - 8.0f, color6);
                }
            }
            k1 += 25;
            for (int k4 = 0; k4 < l3; ++k4) {
                int l4 = k4 / i4;
                int i5 = k4 % i4;
                int j2 = j1 + l4 * i1 + l4 * 5 + 4;
                int k2 = k1 + i5 * 9;
                NetworkPlayerInfo networkplayerinfo1 = list.get(k4);
                GameProfile gameprofile = networkplayerinfo1.getGameProfile();
                EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                if (GuiIngame.tabAlpha > 16.0f && entityplayer == Minecraft.player) {
                    RenderUtils.drawAlphedRect(j2, k2, j2 + i1 - 4, k2 + 8, ColorUtils.getColor(160, 0, 255, (int)GuiIngame.tabAlpha));
                }
                for (Friend friend : Client.friendManager.getFriends()) {
                    if (friend == null || networkplayerinfo1 == null || networkplayerinfo1.getDisplayName() == null || networkplayerinfo1.getDisplayName().getUnformattedText().isEmpty() || !(GuiIngame.tabAlpha > 16.0f) || friend.getName() == Minecraft.player.getName() || networkplayerinfo1 == null || !networkplayerinfo1.getDisplayName().getUnformattedText().contains(friend.getName())) continue;
                    RenderUtils.drawAlphedRect(j2, k2, j2 + i1 - 4, k2 + 8, ColorUtils.getColor(20, 255, 120, (int)GuiIngame.tabAlpha));
                }
                if (entityplayer != Minecraft.player) {
                    // empty if block
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                if (k4 < list.size()) {
                    int k5;
                    int l5;
                    String s4;
                    boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
                    this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                    int l2 = 8 + (flag1 ? 8 : 0);
                    int i3 = 8 * (flag1 ? -1 : 1);
                    RenderUtils.setupColor(-1, (int)((double)GuiIngame.tabAlpha * 2.55));
                    GL11.glDisable(3008);
                    Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0f, l2, 8.0f, i3, 8.0f, 8.0f, 64.0f, 64.0f);
                    GL11.glEnable(3008);
                    GlStateManager.resetColor();
                    RenderUtils.fixShadows();
                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT) && GuiIngame.tabAlpha > 26.0f) {
                        int j3 = 8 + (flag1 ? 8 : 0);
                        int k3 = 8 * (flag1 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0f, j3, 8.0f, k3, 8.0f, 8.0f, 64.0f, 64.0f);
                    }
                    if ((s4 = this.getPlayerName(networkplayerinfo1)) != null && !s4.isEmpty() && s4 != "") {
                        String gamemode = networkplayerinfo1.getGameType() == GameType.SPECTATOR ? TextFormatting.AQUA + "Gm 3 | " : (networkplayerinfo1.getGameType() == GameType.CREATIVE ? TextFormatting.LIGHT_PURPLE + "Gm 1 | " : (networkplayerinfo1.getGameType() == GameType.SURVIVAL ? TextFormatting.GREEN + "Gm 0 | " : (networkplayerinfo1.getGameType() == GameType.ADVENTURE ? TextFormatting.YELLOW + "Gm 2 | " : (networkplayerinfo1.getGameType() == GameType.NOT_SET ? TextFormatting.LIGHT_PURPLE + "Gm -1 | " : "? | "))));
                        try {
                            gamemode = TextFormatting.GRAY + gamemode.replace("|", TextFormatting.WHITE + "|" + TextFormatting.RESET);
                            s4 = ReplaceStrUtils.fixString(s4);
                            String isMe = entityplayer == Minecraft.player ? " \u00a7r\u00a7f|\u00a7r \u00a7d\u042d\u0442\u043e \u044f\u00a7r" : "";
                            String isFriend = "";
                            for (Friend friend : Client.friendManager.getFriends()) {
                                if (entityplayer == null || !entityplayer.getName().contains(friend.getName())) continue;
                                isFriend = " \u00a7r\u00a7f|\u00a7r \u00a7a\u042d\u0442\u043e \u0434\u0440\u0443\u0433\u00a7r";
                            }
                            String neared = (String)(entityplayer != null ? TextFormatting.GREEN + "~ " : "") + TextFormatting.RESET;
                            if (GuiIngame.tabAlpha * 2.8333333f >= 5.0f) {
                                Fonts.mntsb_15.drawString(neared + gamemode + s4.trim() + isMe + isFriend, (float)j2 + 1.0f + 10.0f, (float)k2 + 2.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), GuiIngame.tabAlpha * 2.8333333f));
                            }
                        } catch (Exception e) {
                            System.out.println(gamemode + s4.trim());
                        }
                    }
                    if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != GameType.SPECTATOR && (l5 = (k5 = j2 + i + 1) + l) - k5 > 5) {
                        this.drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                    }
                    if ((double)GuiIngame.tabAlpha * 2.83333333333 >= 5.0) {
                        this.drawPing(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
                    }
                    this.drawPing2(i1, j2 - (flag ? 9 : 0) - 16, k2, networkplayerinfo1, (int)(GuiIngame.tabAlpha * 2.8333333f));
                }
                RenderUtils.resetBlender();
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void renderPlayerlist2(int width, Scoreboard scoreboardIn, @Nullable ScoreObjective scoreObjectiveIn) {
        void var19_27;
        boolean flag;
        int l3;
        NetHandlerPlayClient nethandlerplayclient = Minecraft.player.connection;
        List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        int i = 0;
        int j = 0;
        for (NetworkPlayerInfo networkplayerinfo : list) {
            int k = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkplayerinfo));
            i = Math.max(i, k);
            if (scoreObjectiveIn == null || scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) continue;
            k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getOrCreateScore(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
            j = Math.max(j, k);
        }
        list = list.subList(0, Math.min(list.size(), 80));
        int i4 = l3 = list.size();
        int j4 = 1;
        while (i4 > 20) {
            i4 = (l3 + ++j4 - 1) / j4;
        }
        boolean bl = flag = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
        int l = scoreObjectiveIn != null ? (scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS ? 90 : j) : 0;
        int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
        int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
        int k1 = 10;
        int l1 = i1 * j4 + (j4 - 1) * 5;
        List<String> list1 = null;
        if (this.header != null) {
            list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);
            for (String string : list1) {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(string));
            }
        }
        List<String> list2 = null;
        if (this.footer != null) {
            list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);
            for (String s1 : list2) {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s1));
            }
        }
        if (list1 != null) {
            GuiPlayerTabOverlay.drawRect(width / 2 - l1 / 2 - 1, (double)(k1 - 1), (double)(width / 2 + l1 / 2 + 1), (double)(k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT), Integer.MIN_VALUE);
            for (String s2 : list1) {
                int i2 = this.mc.fontRendererObj.getStringWidth(s2);
                this.mc.fontRendererObj.drawStringWithShadow(s2, width / 2 - i2 / 2, k1, -1);
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
            ++k1;
        }
        GuiPlayerTabOverlay.drawRect(width / 2 - l1 / 2 - 1, (double)(k1 - 1), (double)(width / 2 + l1 / 2 + 1), (double)(k1 + i4 * 9), Integer.MIN_VALUE);
        boolean bl2 = false;
        while (var19_27 < l3) {
            void l4 = var19_27 / i4;
            void i5 = var19_27 % i4;
            int j2 = j1 + l4 * i1 + l4 * 5;
            int k2 = k1 + i5 * 9;
            GuiPlayerTabOverlay.drawRect(j2, (double)k2, (double)(j2 + i1), (double)(k2 + 8), 0x20FFFFFF);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            if (var19_27 < list.size()) {
                int k5;
                int l5;
                NetworkPlayerInfo networkplayerinfo1 = list.get((int)var19_27);
                GameProfile gameprofile = networkplayerinfo1.getGameProfile();
                if (flag) {
                    EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                    boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
                    this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                    int l2 = 8 + (flag1 ? 8 : 0);
                    int i3 = 8 * (flag1 ? -1 : 1);
                    Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0f, l2, 8.0f, i3, 8.0f, 8.0f, 64.0f, 64.0f);
                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                        int j3 = 8 + (flag1 ? 8 : 0);
                        int k3 = 8 * (flag1 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0f, j3, 8.0f, k3, 8.0f, 8.0f, 64.0f, 64.0f);
                    }
                    j2 += 9;
                }
                String s4 = this.getPlayerName(networkplayerinfo1);
                if (networkplayerinfo1.getGameType() == GameType.SPECTATOR) {
                    this.mc.fontRendererObj.drawStringWithShadow(TextFormatting.ITALIC + s4, j2, k2, -1862270977);
                } else {
                    this.mc.fontRendererObj.drawStringWithShadow(s4, j2, k2, -1);
                }
                if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != GameType.SPECTATOR && (l5 = (k5 = j2 + i + 1) + l) - k5 > 5) {
                    this.drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                }
                this.drawPing2(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1, 255);
            }
            ++var19_27;
        }
        if (list2 != null) {
            k1 = k1 + i4 * 9 + 1;
            GuiPlayerTabOverlay.drawRect(width / 2 - l1 / 2 - 1, (double)(k1 - 1), (double)(width / 2 + l1 / 2 + 1), (double)(k1 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT), Integer.MIN_VALUE);
            for (String s3 : list2) {
                int j5 = this.mc.fontRendererObj.getStringWidth(s3);
                this.mc.fontRendererObj.drawStringWithShadow(s3, width / 2 - j5 / 2, k1, -1);
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public void renderPlayerlist3(int width, Scoreboard scoreboardIn, @Nullable ScoreObjective scoreObjectiveIn) {
        void var20_39;
        boolean flag;
        int l3;
        int alpher = (int)(GuiIngame.tabAlpha * 1.25f);
        NetHandlerPlayClient nethandlerplayclient = Minecraft.player.connection;
        List<NetworkPlayerInfo> list = ENTRY_ORDERING.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        int i = 0;
        int j = 0;
        for (NetworkPlayerInfo networkplayerinfo : list) {
            String s2 = this.getPlayerName(networkplayerinfo);
            Object nn = s2;
            if (s2 != null && !s2.isEmpty() && s2 != "") {
                String gamemode = networkplayerinfo.getGameType() == GameType.SPECTATOR ? TextFormatting.AQUA + "Gm 3 | " : (networkplayerinfo.getGameType() == GameType.CREATIVE ? TextFormatting.LIGHT_PURPLE + "Gm 1 | " : (networkplayerinfo.getGameType() == GameType.SURVIVAL ? TextFormatting.GREEN + "Gm 0 | " : (networkplayerinfo.getGameType() == GameType.ADVENTURE ? TextFormatting.YELLOW + "Gm 2 | " : (networkplayerinfo.getGameType() == GameType.NOT_SET ? TextFormatting.LIGHT_PURPLE + "Gm -1 | " : "? | "))));
                try {
                    gamemode = TextFormatting.GRAY + gamemode.replace("|", TextFormatting.WHITE + "|" + TextFormatting.RESET);
                    s2.replace(" ", "");
                    String text = s2;
                    text = text.replace("\uff21", "A");
                    text = text.replace("\uff22", "B");
                    text = text.replace("\uff23", "C");
                    text = text.replace("\uff24", "D");
                    text = text.replace("\uff25", "E");
                    text = text.replace("\uff26", "F");
                    text = text.replace("\uff27", "G");
                    text = text.replace("\uff28", "H");
                    text = text.replace("\uff29", "I");
                    text = text.replace("\uff2a", "J");
                    text = text.replace("\uff2b", "K");
                    text = text.replace("\uff2c", "L");
                    text = text.replace("\uff2d", "M");
                    text = text.replace("\uff2e", "N");
                    text = text.replace("\uff2f", "O");
                    text = text.replace("\uff30", "P");
                    text = text.replace("\uff31", "Q");
                    text = text.replace("\uff32", "R");
                    text = text.replace("\uff33", "S");
                    text = text.replace("\uff34", "T");
                    text = text.replace("\uff35", "U");
                    text = text.replace("\uff36", "V");
                    text = text.replace("\uff37", "W");
                    text = text.replace("\uff38", "X");
                    text = text.replace("\uff39", "Y");
                    text = text.replace("\uff3a", "Z");
                    text = text.replace("\u25b7", ">");
                    s2 = text = text.replace("\u25c1", "<");
                    GameProfile gameprofile = networkplayerinfo.getGameProfile();
                    String sss = this.getPlayerName(networkplayerinfo);
                    EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                    String isMe = entityplayer == Minecraft.player ? " \u00a7r\u00a7f|\u00a7r \u00a7d\u042d\u0442\u043e \u044f\u00a7r" : "";
                    Object isFriend = "";
                    for (Friend friend : Client.friendManager.getFriends()) {
                        if (friend == null || !sss.contains(friend.getName())) continue;
                        isFriend = " \u00a7r\u00a7f|\u00a7r \u00a7a\u042d\u0442\u043e \u0434\u0440\u0443\u0433\u00a7r";
                        break;
                    }
                    String string = (String)(entityplayer != null ? TextFormatting.GREEN + "~ " : "") + TextFormatting.RESET;
                    nn = s2 + isMe + (String)isFriend + gamemode + string + "00000";
                } catch (Exception e) {
                    System.out.println(gamemode + s2.trim());
                }
            }
            int k = Fonts.mntsb_12.getStringWidth((String)nn);
            i = Math.max(i, k);
            if (scoreObjectiveIn == null || scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) continue;
            k = this.mc.fontRendererObj.getStringWidth(" " + scoreboardIn.getOrCreateScore(networkplayerinfo.getGameProfile().getName(), scoreObjectiveIn).getScorePoints());
            j = Math.max(j, k);
        }
        list = list.subList(0, Math.min(list.size(), 600));
        int i4 = l3 = list.size();
        int j4 = 1;
        while (i4 > 50) {
            i4 = (l3 + ++j4 - 1) / j4;
        }
        boolean bl = flag = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted();
        int l = scoreObjectiveIn != null ? (scoreObjectiveIn.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS ? 90 : j) : 0;
        int i1 = Math.min(j4 * ((flag ? 9 : 0) + i + l + 13), width - 50) / j4;
        int j1 = width / 2 - (i1 * j4 + (j4 - 1) * 5) / 2;
        int k1 = 10;
        int l1 = i1 * j4 + (j4 - 1) * 5;
        List<String> list1 = null;
        if (this.header != null) {
            list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), width - 50);
            for (String string : list1) {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(string));
            }
        }
        List<String> list2 = null;
        if (this.footer != null) {
            list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), width - 50);
            for (String s1 : list2) {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s1));
            }
        }
        if (list1 != null) {
            float f = l1 + 61;
            if (f < 355.0f) {
                f = 355.0f;
            }
            float x1 = (float)(width / 2) - f / 2.0f - 1.0f;
            float y1 = k1 - 1;
            float x2 = (float)(width / 2) + f / 2.0f + 1.0f;
            float t = k1 + i4 * 9 + 1;
            float y2 = i4 * 9 + k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT;
            RenderUtils.customScaledObject2D(x1, y1, x2 - x1, y2 - y1, 1.0f + (1.0f - GuiIngame.tabScale));
            GL11.glTranslated(0.0, -(1.0f - GuiIngame.tabAlpha / 90.0f) * (y2 / 2.0f - y1 / 2.0f), 0.0);
            int c = ColorUtils.getColor(0, 0, 0, alpher);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x1 - 5.0f, y1 - 1.0f, x2 + 5.0f, y2 + 1.0f, 20.0f, 5.0f, c, c, c, c, false, true, true);
            RenderUtils.fixShadows();
            if ((int)((float)alpher * 2.25f) > 26) {
                Fonts.mntsb_20.drawStringWithShadow("Online: " + GuiPlayerTabOverlay.getPlayers().size(), x1 + 5.0f, y1 + 9.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), (int)((float)alpher * 2.25f)));
                Fonts.mntsb_12.drawStringWithShadow("ServerIp: " + this.mc.getCurrentServerData().serverIP, x1 + 5.0f, y1 + 20.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), (int)((float)alpher * 2.25f)));
            }
        }
        if (list1 != null) {
            for (String s2 : list1) {
                s2 = s2.replace("  ", " ").replace("\u00a7l", "").replace("[]", "").replace("\u00a7k", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "");
                s2.replace(" ", "");
                String text = s2;
                text = text.replace("\uff21", "A");
                text = text.replace("\uff22", "B");
                text = text.replace("\uff23", "C");
                text = text.replace("\uff24", "D");
                text = text.replace("\uff25", "E");
                text = text.replace("\uff26", "F");
                text = text.replace("\uff27", "G");
                text = text.replace("\uff28", "H");
                text = text.replace("\uff29", "I");
                text = text.replace("\uff2a", "J");
                text = text.replace("\uff2b", "K");
                text = text.replace("\uff2c", "L");
                text = text.replace("\uff2d", "M");
                text = text.replace("\uff2e", "N");
                text = text.replace("\uff2f", "O");
                text = text.replace("\uff30", "P");
                text = text.replace("\uff31", "Q");
                text = text.replace("\uff32", "R");
                text = text.replace("\uff33", "S");
                text = text.replace("\uff34", "T");
                text = text.replace("\uff35", "U");
                text = text.replace("\uff36", "V");
                text = text.replace("\uff37", "W");
                text = text.replace("\uff38", "X");
                text = text.replace("\uff39", "Y");
                text = text.replace("\uff3a", "Z");
                text = text.replace("\u25b7", ">");
                s2 = text = text.replace("\u25c1", "<");
                int i2 = Fonts.comfortaaRegular_17.getStringWidth(s2);
                if ((float)alpher * 2.25f >= 30.0f) {
                    Fonts.comfortaaRegular_17.drawString(s2, width / 2 - i2 / 2, k1, ColorUtils.swapAlpha(-1, (float)alpher * 2.25f));
                }
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
            ++k1;
        }
        boolean bl2 = false;
        while (var20_39 < l3) {
            GameProfile gameprofile;
            NetworkPlayerInfo networkplayerinfo1;
            void l4 = var20_39 / i4;
            void i5 = var20_39 % i4;
            int j2 = j1 + l4 * i1 + l4 * 5;
            int k2 = k1 + i5 * 9;
            boolean me = false;
            boolean fr = false;
            if (var20_39 < list.size()) {
                networkplayerinfo1 = list.get((int)var20_39);
                gameprofile = networkplayerinfo1.getGameProfile();
                String sss = this.getPlayerName(networkplayerinfo1);
                EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                if (entityplayer == Minecraft.player) {
                    me = true;
                }
                for (Friend friend : Client.friendManager.getFriends()) {
                    if (friend == null || !sss.contains(friend.getName())) continue;
                    fr = true;
                    break;
                }
            }
            if (me || fr) {
                RenderUtils.drawLightContureRect(j2, k2, j2 + i1, k2 + 8, ColorUtils.swapAlpha(me ? ColorUtils.getColor(255, 80, 255) : (fr ? ColorUtils.getColor(80, 255, 80) : 0x20FFFFFF), (float)alpher * 2.25f));
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            if (var20_39 < list.size()) {
                int k5;
                int l5;
                int k3;
                int j3;
                networkplayerinfo1 = list.get((int)var20_39);
                gameprofile = networkplayerinfo1.getGameProfile();
                EntityPlayer entityplayer = this.mc.world.getPlayerEntityByUUID(gameprofile.getId());
                boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && ("Dinnerbone".equals(gameprofile.getName()) || "Grumm".equals(gameprofile.getName()));
                this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                int l2 = 8 + (flag1 ? 8 : 0);
                int i3 = 8 * (flag1 ? -1 : 1);
                RenderUtils.setupColor(-1, (int)((double)GuiIngame.tabAlpha * 2.55));
                GL11.glDisable(3008);
                Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0f, l2, 8.0f, i3, 8.0f, 8.0f, 64.0f, 64.0f);
                GL11.glEnable(3008);
                GlStateManager.resetColor();
                RenderUtils.fixShadows();
                if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT) && GuiIngame.tabAlpha > 26.0f) {
                    j3 = 8 + (flag1 ? 8 : 0);
                    k3 = 8 * (flag1 ? -1 : 1);
                    Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0f, j3, 8.0f, k3, 8.0f, 8.0f, 64.0f, 64.0f);
                }
                if (flag) {
                    this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                    Gui.drawScaledCustomSizeModalRect(j2, k2, 8.0f, l2, 8.0f, i3, 8.0f, 8.0f, 64.0f, 64.0f);
                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT)) {
                        j3 = 8 + (flag1 ? 8 : 0);
                        k3 = 8 * (flag1 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(j2, k2, 40.0f, j3, 8.0f, k3, 8.0f, 8.0f, 64.0f, 64.0f);
                    }
                    j2 += 9;
                }
                String s4 = this.getPlayerName(networkplayerinfo1);
                if ((s4 = s4.replace("  ", " ").replace("\u00a7l", "").replace("[]", "").replace("\u00a7k", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "")) != null && !s4.isEmpty() && s4 != "") {
                    String gamemode = networkplayerinfo1.getGameType() == GameType.SPECTATOR ? TextFormatting.AQUA + "Gm 3 | " : (networkplayerinfo1.getGameType() == GameType.CREATIVE ? TextFormatting.LIGHT_PURPLE + "Gm 1 | " : (networkplayerinfo1.getGameType() == GameType.SURVIVAL ? TextFormatting.GREEN + "Gm 0 | " : (networkplayerinfo1.getGameType() == GameType.ADVENTURE ? TextFormatting.YELLOW + "Gm 2 | " : (networkplayerinfo1.getGameType() == GameType.NOT_SET ? TextFormatting.LIGHT_PURPLE + "Gm -1 | " : "? | "))));
                    try {
                        gamemode = TextFormatting.GRAY + gamemode.replace("|", TextFormatting.WHITE + "|" + TextFormatting.RESET);
                        s4.replace(" ", "");
                        String text = s4;
                        text = text.replace("\uff21", "A");
                        text = text.replace("\uff22", "B");
                        text = text.replace("\uff23", "C");
                        text = text.replace("\uff24", "D");
                        text = text.replace("\uff25", "E");
                        text = text.replace("\uff26", "F");
                        text = text.replace("\uff27", "G");
                        text = text.replace("\uff28", "H");
                        text = text.replace("\uff29", "I");
                        text = text.replace("\uff2a", "J");
                        text = text.replace("\uff2b", "K");
                        text = text.replace("\uff2c", "L");
                        text = text.replace("\uff2d", "M");
                        text = text.replace("\uff2e", "N");
                        text = text.replace("\uff2f", "O");
                        text = text.replace("\uff30", "P");
                        text = text.replace("\uff31", "Q");
                        text = text.replace("\uff32", "R");
                        text = text.replace("\uff33", "S");
                        text = text.replace("\uff34", "T");
                        text = text.replace("\uff35", "U");
                        text = text.replace("\uff36", "V");
                        text = text.replace("\uff37", "W");
                        text = text.replace("\uff38", "X");
                        text = text.replace("\uff39", "Y");
                        text = text.replace("\uff3a", "Z");
                        text = text.replace("\u25b7", ">");
                        s4 = text = text.replace("\u25c1", "<");
                        String isMe = entityplayer == Minecraft.player ? " \u00a7r\u00a7f|\u00a7r \u00a7d\u042d\u0442\u043e \u044f\u00a7r" : "";
                        String isFriend = "";
                        String sss = this.getPlayerName(networkplayerinfo1);
                        for (Friend friend : Client.friendManager.getFriends()) {
                            if (friend == null || !sss.contains(friend.getName())) continue;
                            isFriend = " \u00a7r\u00a7f|\u00a7r \u00a7a\u042d\u0442\u043e \u0434\u0440\u0443\u0433\u00a7r";
                            break;
                        }
                        String neared = (String)(entityplayer != null ? TextFormatting.GREEN + "~ " : "") + TextFormatting.RESET;
                        if (GuiIngame.tabAlpha * 2.8333333f >= 5.0f) {
                            Fonts.mntsb_12.drawString(neared + gamemode + s4.trim() + isMe + isFriend, (float)j2 + 1.0f + 10.0f, (float)k2 + 3.0f, ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), GuiIngame.tabAlpha * 2.8333333f));
                        }
                    } catch (Exception e) {
                        System.out.println(gamemode + s4.trim());
                    }
                }
                if (scoreObjectiveIn != null && networkplayerinfo1.getGameType() != GameType.SPECTATOR && (l5 = (k5 = j2 + i + 1) + l) - k5 > 5) {
                    this.drawScoreboardValues(scoreObjectiveIn, k2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                }
                this.drawPing2(i1, j2 - (flag ? 9 : 0), k2, networkplayerinfo1, (int)((double)alpher * 2.25));
                if ((int)((double)alpher * 2.25) >= 30) {
                    GlStateManager.disableDepth();
                    this.drawPing(i1 - 10, j2 - (flag ? 9 : 0), k2, networkplayerinfo1);
                    GlStateManager.enableDepth();
                }
            }
            ++var20_39;
        }
        if (list2 != null) {
            k1 = k1 + i4 * 9 + 1;
            for (String s3 : list2) {
                s3 = s3.replace("  ", " ").replace("\u00a7l", "").replace("[]", "").replace("\u00a7k", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "");
                s3.replace(" ", "");
                String text = s3;
                text = text.replace("\uff21", "A");
                text = text.replace("\uff22", "B");
                text = text.replace("\uff23", "C");
                text = text.replace("\uff24", "D");
                text = text.replace("\uff25", "E");
                text = text.replace("\uff26", "F");
                text = text.replace("\uff27", "G");
                text = text.replace("\uff28", "H");
                text = text.replace("\uff29", "I");
                text = text.replace("\uff2a", "J");
                text = text.replace("\uff2b", "K");
                text = text.replace("\uff2c", "L");
                text = text.replace("\uff2d", "M");
                text = text.replace("\uff2e", "N");
                text = text.replace("\uff2f", "O");
                text = text.replace("\uff30", "P");
                text = text.replace("\uff31", "Q");
                text = text.replace("\uff32", "R");
                text = text.replace("\uff33", "S");
                text = text.replace("\uff34", "T");
                text = text.replace("\uff35", "U");
                text = text.replace("\uff36", "V");
                text = text.replace("\uff37", "W");
                text = text.replace("\uff38", "X");
                text = text.replace("\uff39", "Y");
                text = text.replace("\uff3a", "Z");
                text = text.replace("\u25b7", ">");
                s3 = text = text.replace("\u25c1", "<");
                int j5 = Fonts.comfortaaRegular_17.getStringWidth(s3);
                if ((float)alpher * 2.25f >= 30.0f) {
                    Fonts.comfortaaRegular_17.drawString(s3, width / 2 - j5 / 2, k1, ColorUtils.swapAlpha(-1, (float)alpher * 2.25f));
                }
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
        RenderUtils.fixShadows();
    }

    protected void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn) {
        int color = -1;
        if (networkPlayerInfoIn.getResponseTime() < 50000) {
            color = ColorUtils.getColor(255, 40, 40);
        }
        if (networkPlayerInfoIn.getResponseTime() < 400) {
            color = ColorUtils.getColor(255, 255, 0);
        } else if (networkPlayerInfoIn.getResponseTime() < 300) {
            color = ColorUtils.getColor(255, 160, 0);
        }
        if (networkPlayerInfoIn.getResponseTime() < 200) {
            color = ColorUtils.getColor(225, 255, 25);
        }
        if (networkPlayerInfoIn.getResponseTime() < 100) {
            color = ColorUtils.getColor(40, 255, 40);
        }
        int p = networkPlayerInfoIn.getResponseTime();
        Object ping = "" + networkPlayerInfoIn.getResponseTime();
        if (p > 15000) {
            ping = "SPOOF";
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        Fonts.comfortaa_12.drawString((String)ping, (float)(p_175245_2_ + p_175245_1_) - (((String)ping).equalsIgnoreCase("SPOOF") ? 14.0f : 8.5f) - (float)(Fonts.comfortaa_12.getStringWidth("" + networkPlayerInfoIn.getResponseTime()) / 2), (float)p_175245_3_ + 2.5f, ColorUtils.swapAlpha(color, GuiIngame.tabAlpha * 2.8333333f));
        GL11.glPopMatrix();
    }

    protected void drawPing2(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn, int alpha) {
        if (networkPlayerInfoIn.getResponseTime() > 15000 && !Panic.stop) {
            return;
        }
        RenderUtils.setupColor(ColorUtils.getFixedWhiteColor(), alpha);
        this.mc.getTextureManager().bindTexture(ICONS);
        int j = networkPlayerInfoIn.getResponseTime() < 0 ? 5 : (networkPlayerInfoIn.getResponseTime() < 150 ? 0 : (networkPlayerInfoIn.getResponseTime() < 300 ? 1 : (networkPlayerInfoIn.getResponseTime() < 600 ? 2 : (networkPlayerInfoIn.getResponseTime() < 1000 ? 3 : 4))));
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        this.zLevel += 100.0f;
        this.drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0, 176 + j * 8, 10, 8);
        this.zLevel -= 100.0f;
        GL11.glEnable(3008);
        GlStateManager.resetColor();
    }

    private void drawScoreboardValues(ScoreObjective objective, int p_175247_2_, String name, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo info) {
        int i = objective.getScoreboard().getOrCreateScore(name, objective).getScorePoints();
        if (objective.getRenderType() == IScoreCriteria.EnumRenderType.HEARTS) {
            boolean flag;
            this.mc.getTextureManager().bindTexture(ICONS);
            if (this.lastTimeOpened == info.getRenderVisibilityId()) {
                if (i < info.getLastHealth()) {
                    info.setLastHealthTime(Minecraft.getSystemTime());
                    info.setHealthBlinkTime(this.guiIngame.getUpdateCounter() + 20);
                } else if (i > info.getLastHealth()) {
                    info.setLastHealthTime(Minecraft.getSystemTime());
                    info.setHealthBlinkTime(this.guiIngame.getUpdateCounter() + 10);
                }
            }
            if (Minecraft.getSystemTime() - info.getLastHealthTime() > 1000L || this.lastTimeOpened != info.getRenderVisibilityId()) {
                info.setLastHealth(i);
                info.setDisplayHealth(i);
                info.setLastHealthTime(Minecraft.getSystemTime());
            }
            info.setRenderVisibilityId(this.lastTimeOpened);
            info.setLastHealth(i);
            int j = MathHelper.ceil((float)Math.max(i, info.getDisplayHealth()) / 2.0f);
            int k = Math.max(MathHelper.ceil(i / 2), Math.max(MathHelper.ceil(info.getDisplayHealth() / 2), 10));
            boolean bl = flag = info.getHealthBlinkTime() > (long)this.guiIngame.getUpdateCounter() && (info.getHealthBlinkTime() - (long)this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;
            if (j > 0) {
                float f = Math.min((float)(p_175247_5_ - p_175247_4_ - 4) / (float)k, 9.0f);
                if (f > 3.0f) {
                    for (int l = j; l < k; ++l) {
                        this.drawTexturedModalRect((float)p_175247_4_ + (float)l * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                    }
                    for (int j1 = 0; j1 < j; ++j1) {
                        this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                        if (flag) {
                            if (j1 * 2 + 1 < info.getDisplayHealth()) {
                                this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 70, 0, 9, 9);
                            }
                            if (j1 * 2 + 1 == info.getDisplayHealth()) {
                                this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 79, 0, 9, 9);
                            }
                        }
                        if (j1 * 2 + 1 < i) {
                            this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 160 : 52, 0, 9, 9);
                        }
                        if (j1 * 2 + 1 != i) continue;
                        this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 169 : 61, 0, 9, 9);
                    }
                } else {
                    float f1 = MathHelper.clamp((float)i / 20.0f, 0.0f, 1.0f);
                    int i1 = (int)((1.0f - f1) * 255.0f) << 16 | (int)(f1 * 255.0f) << 8;
                    String s = "" + (float)i / 2.0f;
                    if (p_175247_5_ - this.mc.fontRendererObj.getStringWidth(s + "hp") >= p_175247_4_) {
                        s = s + "hp";
                    }
                    this.mc.fontRendererObj.drawStringWithShadow(s, (p_175247_5_ + p_175247_4_) / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, p_175247_2_, i1);
                }
            }
        } else {
            String s1 = "" + TextFormatting.YELLOW + i;
            this.mc.fontRendererObj.drawStringWithShadow(s1, p_175247_5_ - this.mc.fontRendererObj.getStringWidth(s1), p_175247_2_, 0xFFFFFF);
        }
    }

    public void setFooter(@Nullable ITextComponent footerIn) {
        this.footer = footerIn;
    }

    public void setHeader(@Nullable ITextComponent headerIn) {
        this.header = headerIn;
    }

    public void resetFooterHeader() {
        this.header = null;
        this.footer = null;
    }

    public static class PlayerComparator
    implements Comparator<NetworkPlayerInfo> {
        @Override
        public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
            ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
            ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != GameType.SPECTATOR, p_compare_2_.getGameType() != GameType.SPECTATOR).compare((Comparable<?>)((Object)(scoreplayerteam != null ? scoreplayerteam.getRegisteredName() : "")), (Comparable<?>)((Object)(scoreplayerteam1 != null ? scoreplayerteam1.getRegisteredName() : ""))).compare((Comparable<?>)((Object)p_compare_1_.getGameProfile().getName()), (Comparable<?>)((Object)p_compare_2_.getGameProfile().getName())).result();
        }
    }
}

