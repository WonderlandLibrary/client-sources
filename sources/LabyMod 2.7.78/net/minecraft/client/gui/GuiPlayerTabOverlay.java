package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Allowed;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.FriendsLoader;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;
import org.lwjgl.opengl.GL11;

public class GuiPlayerTabOverlay extends Gui
{
    private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator());
    private final Minecraft mc;
    private final GuiIngame guiIngame;
    private IChatComponent footer;
    private IChatComponent header;

    /**
     * The last time the playerlist was opened (went from not being renderd, to being rendered)
     */
    private long lastTimeOpened;

    /** Weither or not the playerlist is currently being rendered */
    private boolean isBeingRendered;

    public GuiPlayerTabOverlay(Minecraft mcIn, GuiIngame guiIngameIn)
    {
        this.mc = mcIn;
        this.guiIngame = guiIngameIn;
    }

    /**
     * Returns the name that should be renderd for the player supplied
     */
    public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn)
    {
        return networkPlayerInfoIn.getDisplayName() != null ? FriendsLoader.getNick(networkPlayerInfoIn.getDisplayName().getFormattedText(), networkPlayerInfoIn.getGameProfile().getName()) : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), FriendsLoader.getNick(networkPlayerInfoIn.getGameProfile().getName(), networkPlayerInfoIn.getGameProfile().getName()));
    }

    /**
     * Called by GuiIngame to update the information stored in the playerlist, does not actually render the list,
     * however.
     */
    public void updatePlayerList(boolean willBeRendered)
    {
        if (willBeRendered && !this.isBeingRendered)
        {
            this.lastTimeOpened = Minecraft.getSystemTime();
        }

        this.isBeingRendered = willBeRendered;
    }

    /**
     * Renders the playerlist, its background, headers and footers.
     */
    public void renderPlayerlist(int width, Scoreboard scoreboardIn, ScoreObjective scoreObjectiveIn)
    {
        if (ConfigManager.settings.oldTablist && Allowed.animations())
        {
            this.oldTabOverlay(width, scoreboardIn, scoreObjectiveIn);
        }
        else
        {
            this.newTabOverlay(width, scoreboardIn, scoreObjectiveIn);
        }
    }

    public void newTabOverlay(int p_newTabOverlay_1_, Scoreboard p_newTabOverlay_2_, ScoreObjective p_newTabOverlay_3_)
    {
        NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> list = field_175252_a.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());
        int i = 0;
        int j = 0;

        for (NetworkPlayerInfo networkplayerinfo : list)
        {
            int k = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkplayerinfo));
            i = Math.max(i, k);

            if (p_newTabOverlay_3_ != null && p_newTabOverlay_3_.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS)
            {
                k = this.mc.fontRendererObj.getStringWidth(" " + p_newTabOverlay_2_.getValueFromObjective(networkplayerinfo.getGameProfile().getName(), p_newTabOverlay_3_).getScorePoints());
                j = Math.max(j, k);
            }
        }

        list = list.subList(0, Math.min(list.size(), 80));
        int k3 = list.size();
        int l3 = k3;
        int i4;

        for (i4 = 1; l3 > 20; l3 = (k3 + i4 - 1) / i4)
        {
            ++i4;
        }

        boolean flag = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
        int l;

        if (p_newTabOverlay_3_ != null)
        {
            if (p_newTabOverlay_3_.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS)
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

        int i1 = Math.min(i4 * ((flag ? 9 : 0) + i + l + 13), p_newTabOverlay_1_ - 50) / i4;
        int j1 = p_newTabOverlay_1_ / 2 - (i1 * i4 + (i4 - 1) * 5) / 2;
        int k1 = 10;
        int l1 = i1 * i4 + (i4 - 1) * 5;
        List<String> list1 = null;
        List<String> list2 = null;

        if (this.header != null)
        {
            list1 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), p_newTabOverlay_1_ - 50);

            for (String s : list1)
            {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s));
            }
        }

        if (this.footer != null)
        {
            list2 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), p_newTabOverlay_1_ - 50);

            for (String s3 : list2)
            {
                l1 = Math.max(l1, this.mc.fontRendererObj.getStringWidth(s3));
            }
        }

        if (list1 != null)
        {
            drawRect(p_newTabOverlay_1_ / 2 - l1 / 2 - 1, k1 - 1, p_newTabOverlay_1_ / 2 + l1 / 2 + 1, k1 + list1.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

            for (String s1 : list1)
            {
                int i2 = this.mc.fontRendererObj.getStringWidth(s1);
                this.mc.fontRendererObj.drawStringWithShadow(s1, (float)(p_newTabOverlay_1_ / 2 - i2 / 2), (float)k1, -1);
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }

            ++k1;
        }

        drawRect(p_newTabOverlay_1_ / 2 - l1 / 2 - 1, k1 - 1, p_newTabOverlay_1_ / 2 + l1 / 2 + 1, k1 + l3 * 9, Integer.MIN_VALUE);

        for (int k4 = 0; k4 < k3; ++k4)
        {
            int l4 = k4 / l3;
            int j4 = k4 % l3;
            int i5 = j1 + l4 * i1 + l4 * 5;
            int j2 = k1 + j4 * 9;
            drawRect(i5, j2, i5 + i1, j2 + 8, 553648127);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

            if (k4 < list.size())
            {
                NetworkPlayerInfo networkplayerinfo1 = (NetworkPlayerInfo)list.get(k4);
                String s2 = this.getPlayerName(networkplayerinfo1);
                GameProfile gameprofile = networkplayerinfo1.getGameProfile();

                if (flag)
                {
                    EntityPlayer entityplayer = this.mc.theWorld.getPlayerEntityByUUID(gameprofile.getId());
                    boolean flag1 = entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.CAPE) && (gameprofile.getName().equals("Dinnerbone") || gameprofile.getName().equals("Grumm"));
                    this.mc.getTextureManager().bindTexture(networkplayerinfo1.getLocationSkin());
                    int k2 = 8 + (flag1 ? 8 : 0);
                    int l2 = 8 * (flag1 ? -1 : 1);
                    Gui.drawScaledCustomSizeModalRect(i5, j2, 8.0F, (float)k2, 8, l2, 8, 8, 64.0F, 64.0F);

                    if (entityplayer != null && entityplayer.isWearing(EnumPlayerModelParts.HAT))
                    {
                        int i3 = 8 + (flag1 ? 8 : 0);
                        int j3 = 8 * (flag1 ? -1 : 1);
                        Gui.drawScaledCustomSizeModalRect(i5, j2, 40.0F, (float)i3, 8, j3, 8, 8, 64.0F, 64.0F);
                    }

                    i5 += 9;
                }

                if (networkplayerinfo1.getGameType() == WorldSettings.GameType.SPECTATOR)
                {
                    s2 = EnumChatFormatting.ITALIC + s2;
                    this.mc.fontRendererObj.drawStringWithShadow(s2, (float)i5, (float)j2, -1862270977);
                }
                else
                {
                    this.mc.fontRendererObj.drawStringWithShadow(s2, (float)i5, (float)j2, -1);
                }

                if (p_newTabOverlay_3_ != null && networkplayerinfo1.getGameType() != WorldSettings.GameType.SPECTATOR)
                {
                    int k5 = i5 + i + 1;
                    int l5 = k5 + l;

                    if (l5 - k5 > 5)
                    {
                        this.drawScoreboardValues(p_newTabOverlay_3_, j2, gameprofile.getName(), k5, l5, networkplayerinfo1);
                    }
                }

                this.drawPing(i1, i5 - (flag ? 9 : 0), j2, networkplayerinfo1);
            }
        }

        if (list2 != null)
        {
            k1 = k1 + l3 * 9 + 1;
            drawRect(p_newTabOverlay_1_ / 2 - l1 / 2 - 1, k1 - 1, p_newTabOverlay_1_ / 2 + l1 / 2 + 1, k1 + list2.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);

            for (String s4 : list2)
            {
                int j5 = this.mc.fontRendererObj.getStringWidth(s4);
                this.mc.fontRendererObj.drawStringWithShadow(s4, (float)(p_newTabOverlay_1_ / 2 - j5 / 2), (float)k1, -1);
                k1 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
    }

    public void oldTabOverlay(int p_oldTabOverlay_1_, Scoreboard p_oldTabOverlay_2_, ScoreObjective p_oldTabOverlay_3_)
    {
        try
        {
            NetHandlerPlayClient nethandlerplayclient = this.mc.thePlayer.sendQueue;
            List list = field_175252_a.sortedCopy(nethandlerplayclient.getPlayerInfoMap());
            int i = LabyMod.getInstance().mc.thePlayer.sendQueue.currentServerMaxPlayers;
            int j = i;
            ScaledResolution scaledresolution = new ScaledResolution(LabyMod.getInstance().mc);
            int k = 0;
            int l = scaledresolution.getScaledWidth();
            int i1 = 0;
            int j1 = 0;
            int k1 = 0;

            for (k = 1; j > 20; j = (i + k - 1) / k)
            {
                ++k;
            }

            int l1 = 300 / k;

            if (l1 > 150)
            {
                l1 = 150;
            }

            int i2 = (l - k * l1) / 2;
            byte b0 = 10;
            drawRect(i2 - 1, b0 - 1, i2 + l1 * k, b0 + 9 * j, Integer.MIN_VALUE);

            for (i1 = 0; i1 < i; ++i1)
            {
                j1 = i2 + i1 % k * l1;
                k1 = b0 + i1 / k * 9;
                drawRect(j1, k1, j1 + l1 - 1, k1 + 8, 553648127);
                GlStateManager.enableAlpha();

                if (i1 < list.size())
                {
                    NetworkPlayerInfo networkplayerinfo = (NetworkPlayerInfo)list.get(i1);
                    String s = networkplayerinfo.getGameProfile().getName();
                    ScorePlayerTeam scoreplayerteam = LabyMod.getInstance().mc.theWorld.getScoreboard().getPlayersTeam(s);
                    String s1 = this.getPlayerName(networkplayerinfo);
                    LabyMod.getInstance().draw.drawString(s1, (double)j1, (double)k1);

                    if (p_oldTabOverlay_3_ != null)
                    {
                        int j2 = j1 + LabyMod.getInstance().draw.getStringWidth(s1) + 5;
                        int k2 = j1 + l1 - 12 - 5;

                        if (k2 - j2 > 5)
                        {
                            Score score = p_oldTabOverlay_2_.getValueFromObjective(s, p_oldTabOverlay_3_);
                            String s2 = EnumChatFormatting.YELLOW + "" + score.getScorePoints();
                            LabyMod.getInstance().draw.drawString(s2, (double)(k2 - LabyMod.getInstance().draw.getStringWidth(s2)), (double)k1, 1.6777215E7D);
                        }
                    }

                    this.drawPing(50, j1 + l1 - 52, k1, networkplayerinfo);
                }
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.enableAlpha();
        }
        catch (Exception var26)
        {
            ;
        }
    }

    protected void drawPing(int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn)
    {
        if (ConfigManager.settings.tabPing.booleanValue() && Allowed.unfairExtra())
        {
            this.zLevel += 100.0F;
        }
        else
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(icons);
            byte b0 = 0;
            boolean flag = false;
            byte b1;

            if (networkPlayerInfoIn.getResponseTime() < 0)
            {
                b1 = 5;
            }
            else if (networkPlayerInfoIn.getResponseTime() < 150)
            {
                b1 = 0;
            }
            else if (networkPlayerInfoIn.getResponseTime() < 300)
            {
                b1 = 1;
            }
            else if (networkPlayerInfoIn.getResponseTime() < 600)
            {
                b1 = 2;
            }
            else if (networkPlayerInfoIn.getResponseTime() < 1000)
            {
                b1 = 3;
            }
            else
            {
                b1 = 4;
            }

            this.zLevel += 100.0F;
            this.drawTexturedModalRect(p_175245_2_ + p_175245_1_ - 11, p_175245_3_, 0 + b0 * 10, 176 + b1 * 8, 10, 8);
        }

        DrawUtils drawutils = LabyMod.getInstance().draw;
        GL11.glPushMatrix();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        int i = networkPlayerInfoIn.getResponseTime();

        if (i >= 1000)
        {
            i = 999;
        }

        if (i < 0)
        {
            i = 0;
        }

        if (networkPlayerInfoIn.getGameProfile() != null && networkPlayerInfoIn.getGameProfile().getName() != null && LabyMod.getInstance().isInGame() && networkPlayerInfoIn.getGameProfile().getName().equals(Minecraft.getMinecraft().thePlayer.getName()))
        {
            LabyMod.getInstance().playerPing = i;
        }

        String s1 = "a";

        if (i > 150)
        {
            s1 = "2";
        }

        if (i > 300)
        {
            s1 = "c";
        }

        if (i > 600)
        {
            s1 = "4";
        }

        String s = "" + i;

        if (i == 0)
        {
            s = "?";
        }

        if (ConfigManager.settings.tabPing.booleanValue())
        {
            drawutils.drawCenteredString(Color.cl(s1) + s + "", (p_175245_2_ + p_175245_1_) * 2 - 12, p_175245_3_ * 2 + 5);
        }

        GL11.glPopMatrix();
        this.zLevel -= 100.0F;
    }

    private void drawScoreboardValues(ScoreObjective p_175247_1_, int p_175247_2_, String p_175247_3_, int p_175247_4_, int p_175247_5_, NetworkPlayerInfo p_175247_6_)
    {
        int i = p_175247_1_.getScoreboard().getValueFromObjective(p_175247_3_, p_175247_1_).getScorePoints();

        if (p_175247_1_.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS)
        {
            this.mc.getTextureManager().bindTexture(icons);

            if (this.lastTimeOpened == p_175247_6_.func_178855_p())
            {
                if (i < p_175247_6_.func_178835_l())
                {
                    p_175247_6_.func_178846_a(Minecraft.getSystemTime());
                    p_175247_6_.func_178844_b((long)(this.guiIngame.getUpdateCounter() + 20));
                }
                else if (i > p_175247_6_.func_178835_l())
                {
                    p_175247_6_.func_178846_a(Minecraft.getSystemTime());
                    p_175247_6_.func_178844_b((long)(this.guiIngame.getUpdateCounter() + 10));
                }
            }

            if (Minecraft.getSystemTime() - p_175247_6_.func_178847_n() > 1000L || this.lastTimeOpened != p_175247_6_.func_178855_p())
            {
                p_175247_6_.func_178836_b(i);
                p_175247_6_.func_178857_c(i);
                p_175247_6_.func_178846_a(Minecraft.getSystemTime());
            }

            p_175247_6_.func_178843_c(this.lastTimeOpened);
            p_175247_6_.func_178836_b(i);
            int j = MathHelper.ceiling_float_int((float)Math.max(i, p_175247_6_.func_178860_m()) / 2.0F);
            int k = Math.max(MathHelper.ceiling_float_int((float)(i / 2)), Math.max(MathHelper.ceiling_float_int((float)(p_175247_6_.func_178860_m() / 2)), 10));
            boolean flag = p_175247_6_.func_178858_o() > (long)this.guiIngame.getUpdateCounter() && (p_175247_6_.func_178858_o() - (long)this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;

            if (j > 0)
            {
                float f = Math.min((float)(p_175247_5_ - p_175247_4_ - 4) / (float)k, 9.0F);

                if (f > 3.0F)
                {
                    for (int l = j; l < k; ++l)
                    {
                        this.drawTexturedModalRect((float)p_175247_4_ + (float)l * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);
                    }

                    for (int j1 = 0; j1 < j; ++j1)
                    {
                        this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, flag ? 25 : 16, 0, 9, 9);

                        if (flag)
                        {
                            if (j1 * 2 + 1 < p_175247_6_.func_178860_m())
                            {
                                this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 70, 0, 9, 9);
                            }

                            if (j1 * 2 + 1 == p_175247_6_.func_178860_m())
                            {
                                this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, 79, 0, 9, 9);
                            }
                        }

                        if (j1 * 2 + 1 < i)
                        {
                            this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 160 : 52, 0, 9, 9);
                        }

                        if (j1 * 2 + 1 == i)
                        {
                            this.drawTexturedModalRect((float)p_175247_4_ + (float)j1 * f, (float)p_175247_2_, j1 >= 10 ? 169 : 61, 0, 9, 9);
                        }
                    }
                }
                else
                {
                    float f1 = MathHelper.clamp_float((float)i / 20.0F, 0.0F, 1.0F);
                    int i1 = (int)((1.0F - f1) * 255.0F) << 16 | (int)(f1 * 255.0F) << 8;
                    String s = "" + (float)i / 2.0F;

                    if (p_175247_5_ - this.mc.fontRendererObj.getStringWidth(s + "hp") >= p_175247_4_)
                    {
                        s = s + "hp";
                    }

                    this.mc.fontRendererObj.drawStringWithShadow(s, (float)((p_175247_5_ + p_175247_4_) / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2), (float)p_175247_2_, i1);
                }
            }
        }
        else
        {
            String s1 = EnumChatFormatting.YELLOW + "" + i;
            this.mc.fontRendererObj.drawStringWithShadow(s1, (float)(p_175247_5_ - this.mc.fontRendererObj.getStringWidth(s1)), (float)p_175247_2_, 16777215);
        }
    }

    public void setFooter(IChatComponent footerIn)
    {
        this.footer = footerIn;
        LabyMod.getInstance().footer = this.footer;
    }

    public void setHeader(IChatComponent headerIn)
    {
        this.header = headerIn;
        LabyMod.getInstance().header = this.header;
    }

    public void func_181030_a()
    {
        this.header = null;
        this.footer = null;
    }

    static class PlayerComparator implements Comparator<NetworkPlayerInfo>
    {
        private PlayerComparator()
        {
        }

        public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_)
        {
            ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
            ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR, p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.getRegisteredName() : "", scoreplayerteam1 != null ? scoreplayerteam1.getRegisteredName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
        }
    }
}
