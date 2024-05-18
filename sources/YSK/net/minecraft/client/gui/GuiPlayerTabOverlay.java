package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public class GuiPlayerTabOverlay extends Gui
{
    private long lastTimeOpened;
    private final Minecraft mc;
    private IChatComponent header;
    private static final String[] I;
    private final GuiIngame guiIngame;
    private boolean isBeingRendered;
    private IChatComponent footer;
    private static final Ordering<NetworkPlayerInfo> field_175252_a;
    
    public void setHeader(final IChatComponent header) {
        this.header = header;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void renderPlayerlist(final int n, final Scoreboard scoreboard, final ScoreObjective scoreObjective) {
        final List sortedCopy = GuiPlayerTabOverlay.field_175252_a.sortedCopy((Iterable)this.mc.thePlayer.sendQueue.getPlayerInfoMap());
        int n2 = "".length();
        int n3 = "".length();
        final Iterator<NetworkPlayerInfo> iterator = sortedCopy.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final NetworkPlayerInfo networkPlayerInfo = iterator.next();
            n2 = Math.max(n2, this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkPlayerInfo)));
            if (scoreObjective != null && scoreObjective.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                n3 = Math.max(n3, this.mc.fontRendererObj.getStringWidth(GuiPlayerTabOverlay.I["".length()] + scoreboard.getValueFromObjective(networkPlayerInfo.getGameProfile().getName(), scoreObjective).getScorePoints()));
            }
        }
        final List<NetworkPlayerInfo> subList = sortedCopy.subList("".length(), Math.min(sortedCopy.size(), 0x7B ^ 0x2B));
        int i;
        final int n4 = i = subList.size();
        int length = " ".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i > (0x8E ^ 0x9A)) {
            ++length;
            i = (n4 + length - " ".length()) / length;
        }
        int n5;
        if (!this.mc.isIntegratedServerRunning() && !this.mc.getNetHandler().getNetworkManager().getIsencrypted()) {
            n5 = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            n5 = " ".length();
        }
        final int n6 = n5;
        int length2;
        if (scoreObjective != null) {
            if (scoreObjective.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                length2 = (0xD3 ^ 0x89);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else {
                length2 = n3;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else {
            length2 = "".length();
        }
        final int n7 = length;
        int length3;
        if (n6 != 0) {
            length3 = (0x57 ^ 0x5E);
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            length3 = "".length();
        }
        final int n8 = Math.min(n7 * (length3 + n2 + length2 + (0x52 ^ 0x5F)), n - (0x9E ^ 0xAC)) / length;
        final int n9 = n / "  ".length() - (n8 * length + (length - " ".length()) * (0x4F ^ 0x4A)) / "  ".length();
        int n10 = 0x67 ^ 0x6D;
        int n11 = n8 * length + (length - " ".length()) * (0x61 ^ 0x64);
        List<String> listFormattedStringToWidth = null;
        List<String> listFormattedStringToWidth2 = null;
        if (this.header != null) {
            listFormattedStringToWidth = (List<String>)this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), n - (0x71 ^ 0x43));
            final Iterator<String> iterator2 = listFormattedStringToWidth.iterator();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                n11 = Math.max(n11, this.mc.fontRendererObj.getStringWidth(iterator2.next()));
            }
        }
        if (this.footer != null) {
            listFormattedStringToWidth2 = (List<String>)this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), n - (0x80 ^ 0xB2));
            final Iterator<String> iterator3 = listFormattedStringToWidth2.iterator();
            "".length();
            if (4 < -1) {
                throw null;
            }
            while (iterator3.hasNext()) {
                n11 = Math.max(n11, this.mc.fontRendererObj.getStringWidth(iterator3.next()));
            }
        }
        if (listFormattedStringToWidth != null) {
            Gui.drawRect(n / "  ".length() - n11 / "  ".length() - " ".length(), n10 - " ".length(), n / "  ".length() + n11 / "  ".length() + " ".length(), n10 + listFormattedStringToWidth.size() * this.mc.fontRendererObj.FONT_HEIGHT, -"".length());
            final Iterator<String> iterator4 = listFormattedStringToWidth.iterator();
            "".length();
            if (4 == 1) {
                throw null;
            }
            while (iterator4.hasNext()) {
                final String s = iterator4.next();
                this.mc.fontRendererObj.drawStringWithShadow(s, n / "  ".length() - this.mc.fontRendererObj.getStringWidth(s) / "  ".length(), n10, -" ".length());
                n10 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
            ++n10;
        }
        Gui.drawRect(n / "  ".length() - n11 / "  ".length() - " ".length(), n10 - " ".length(), n / "  ".length() + n11 / "  ".length() + " ".length(), n10 + i * (0x18 ^ 0x11), -"".length());
        int j = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (j < n4) {
            final int n12 = j / i;
            final int n13 = j % i;
            int n14 = n9 + n12 * n8 + n12 * (0xBB ^ 0xBE);
            final int n15 = n10 + n13 * (0x91 ^ 0x98);
            Gui.drawRect(n14, n15, n14 + n8, n15 + (0x7D ^ 0x75), 146895623 + 346687809 - 475364636 + 535429331);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(141 + 735 - 443 + 337, 512 + 494 - 297 + 62, " ".length(), "".length());
            if (j < subList.size()) {
                final NetworkPlayerInfo networkPlayerInfo2 = subList.get(j);
                final String playerName = this.getPlayerName(networkPlayerInfo2);
                final GameProfile gameProfile = networkPlayerInfo2.getGameProfile();
                if (n6 != 0) {
                    final EntityPlayer playerEntityByUUID = this.mc.theWorld.getPlayerEntityByUUID(gameProfile.getId());
                    int n16;
                    if (playerEntityByUUID != null && playerEntityByUUID.isWearing(EnumPlayerModelParts.CAPE) && (gameProfile.getName().equals(GuiPlayerTabOverlay.I[" ".length()]) || gameProfile.getName().equals(GuiPlayerTabOverlay.I["  ".length()]))) {
                        n16 = " ".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else {
                        n16 = "".length();
                    }
                    final int n17 = n16;
                    this.mc.getTextureManager().bindTexture(networkPlayerInfo2.getLocationSkin());
                    final int n18 = 0x64 ^ 0x6C;
                    int length4;
                    if (n17 != 0) {
                        length4 = (0x9C ^ 0x94);
                        "".length();
                        if (4 < 2) {
                            throw null;
                        }
                    }
                    else {
                        length4 = "".length();
                    }
                    final int n19 = n18 + length4;
                    final int n20 = 0x51 ^ 0x59;
                    int length5;
                    if (n17 != 0) {
                        length5 = -" ".length();
                        "".length();
                        if (-1 == 3) {
                            throw null;
                        }
                    }
                    else {
                        length5 = " ".length();
                    }
                    Gui.drawScaledCustomSizeModalRect(n14, n15, 8.0f, n19, 0x52 ^ 0x5A, n20 * length5, 0x57 ^ 0x5F, 0x15 ^ 0x1D, 64.0f, 64.0f);
                    if (playerEntityByUUID != null && playerEntityByUUID.isWearing(EnumPlayerModelParts.HAT)) {
                        final int n21 = 0x8D ^ 0x85;
                        int length6;
                        if (n17 != 0) {
                            length6 = (0x9C ^ 0x94);
                            "".length();
                            if (4 < 4) {
                                throw null;
                            }
                        }
                        else {
                            length6 = "".length();
                        }
                        final int n22 = n21 + length6;
                        final int n23 = 0xA6 ^ 0xAE;
                        int length7;
                        if (n17 != 0) {
                            length7 = -" ".length();
                            "".length();
                            if (true != true) {
                                throw null;
                            }
                        }
                        else {
                            length7 = " ".length();
                        }
                        Gui.drawScaledCustomSizeModalRect(n14, n15, 40.0f, n22, 0xC ^ 0x4, n23 * length7, 0x75 ^ 0x7D, 0xCD ^ 0xC5, 64.0f, 64.0f);
                    }
                    n14 += 9;
                }
                if (networkPlayerInfo2.getGameType() == WorldSettings.GameType.SPECTATOR) {
                    this.mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.ITALIC + playerName, n14, n15, -(591834976 + 7709391 + 629999595 + 632727015));
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else {
                    this.mc.fontRendererObj.drawStringWithShadow(playerName, n14, n15, -" ".length());
                }
                if (scoreObjective != null && networkPlayerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR) {
                    final int n24 = n14 + n2 + " ".length();
                    final int n25 = n24 + length2;
                    if (n25 - n24 > (0x89 ^ 0x8C)) {
                        this.drawScoreboardValues(scoreObjective, n15, gameProfile.getName(), n24, n25, networkPlayerInfo2);
                    }
                }
                final int n26 = n8;
                final int n27 = n14;
                int length8;
                if (n6 != 0) {
                    length8 = (0x20 ^ 0x29);
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                }
                else {
                    length8 = "".length();
                }
                this.drawPing(n26, n27 - length8, n15, networkPlayerInfo2);
            }
            ++j;
        }
        if (listFormattedStringToWidth2 != null) {
            int n28 = n10 + i * (0x1 ^ 0x8) + " ".length();
            Gui.drawRect(n / "  ".length() - n11 / "  ".length() - " ".length(), n28 - " ".length(), n / "  ".length() + n11 / "  ".length() + " ".length(), n28 + listFormattedStringToWidth2.size() * this.mc.fontRendererObj.FONT_HEIGHT, -"".length());
            final Iterator<String> iterator5 = listFormattedStringToWidth2.iterator();
            "".length();
            if (false) {
                throw null;
            }
            while (iterator5.hasNext()) {
                final String s2 = iterator5.next();
                this.mc.fontRendererObj.drawStringWithShadow(s2, n / "  ".length() - this.mc.fontRendererObj.getStringWidth(s2) / "  ".length(), n28, -" ".length());
                n28 += this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
    }
    
    public GuiPlayerTabOverlay(final Minecraft mc, final GuiIngame guiIngame) {
        this.mc = mc;
        this.guiIngame = guiIngame;
    }
    
    public String getPlayerName(final NetworkPlayerInfo networkPlayerInfo) {
        String s;
        if (networkPlayerInfo.getDisplayName() != null) {
            s = networkPlayerInfo.getDisplayName().getFormattedText();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            s = ScorePlayerTeam.formatPlayerName(networkPlayerInfo.getPlayerTeam(), networkPlayerInfo.getGameProfile().getName());
        }
        return s;
    }
    
    protected void drawPing(final int n, final int n2, final int n3, final NetworkPlayerInfo networkPlayerInfo) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiPlayerTabOverlay.icons);
        final int length = "".length();
        "".length();
        int n4;
        if (networkPlayerInfo.getResponseTime() < 0) {
            n4 = (0xAE ^ 0xAB);
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else if (networkPlayerInfo.getResponseTime() < 136 + 62 - 196 + 148) {
            n4 = "".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (networkPlayerInfo.getResponseTime() < 298 + 59 - 135 + 78) {
            n4 = " ".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else if (networkPlayerInfo.getResponseTime() < 448 + 498 - 820 + 474) {
            n4 = "  ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (networkPlayerInfo.getResponseTime() < 491 + 426 - 403 + 486) {
            n4 = "   ".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n4 = (0x29 ^ 0x2D);
        }
        this.zLevel += 100.0f;
        this.drawTexturedModalRect(n2 + n - (0x7C ^ 0x77), n3, "".length() + length * (0x3A ^ 0x30), 103 + 38 - 8 + 43 + n4 * (0x7 ^ 0xF), 0x69 ^ 0x63, 0x32 ^ 0x3A);
        this.zLevel -= 100.0f;
    }
    
    static {
        I();
        field_175252_a = Ordering.from((Comparator)new PlayerComparator(null));
    }
    
    public void func_181030_a() {
        this.header = null;
        this.footer = null;
    }
    
    private static void I() {
        (I = new String[0x9F ^ 0x9A])["".length()] = I("q", "QsQYL");
        GuiPlayerTabOverlay.I[" ".length()] = I("\u001d1\u0007;\u000e+:\u0006;\u000e", "YXiUk");
        GuiPlayerTabOverlay.I["  ".length()] = I("\u00119=\u001f\u0005", "VKHrh");
        GuiPlayerTabOverlay.I["   ".length()] = I("1>", "YNCHB");
        GuiPlayerTabOverlay.I[0x42 ^ 0x46] = I("0\u0004", "XtauU");
    }
    
    private void drawScoreboardValues(final ScoreObjective scoreObjective, final int n, final String s, final int n2, final int n3, final NetworkPlayerInfo networkPlayerInfo) {
        final int scorePoints = scoreObjective.getScoreboard().getValueFromObjective(s, scoreObjective).getScorePoints();
        if (scoreObjective.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            this.mc.getTextureManager().bindTexture(GuiPlayerTabOverlay.icons);
            if (this.lastTimeOpened == networkPlayerInfo.func_178855_p()) {
                if (scorePoints < networkPlayerInfo.func_178835_l()) {
                    networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
                    networkPlayerInfo.func_178844_b(this.guiIngame.getUpdateCounter() + (0x15 ^ 0x1));
                    "".length();
                    if (2 == 4) {
                        throw null;
                    }
                }
                else if (scorePoints > networkPlayerInfo.func_178835_l()) {
                    networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
                    networkPlayerInfo.func_178844_b(this.guiIngame.getUpdateCounter() + (0x71 ^ 0x7B));
                }
            }
            if (Minecraft.getSystemTime() - networkPlayerInfo.func_178847_n() > 1000L || this.lastTimeOpened != networkPlayerInfo.func_178855_p()) {
                networkPlayerInfo.func_178836_b(scorePoints);
                networkPlayerInfo.func_178857_c(scorePoints);
                networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
            }
            networkPlayerInfo.func_178843_c(this.lastTimeOpened);
            networkPlayerInfo.func_178836_b(scorePoints);
            final int ceiling_float_int = MathHelper.ceiling_float_int(Math.max(scorePoints, networkPlayerInfo.func_178860_m()) / 2.0f);
            final int max = Math.max(MathHelper.ceiling_float_int(scorePoints / "  ".length()), Math.max(MathHelper.ceiling_float_int(networkPlayerInfo.func_178860_m() / "  ".length()), 0x47 ^ 0x4D));
            int n4;
            if (networkPlayerInfo.func_178858_o() > this.guiIngame.getUpdateCounter() && (networkPlayerInfo.func_178858_o() - this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L) {
                n4 = " ".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                n4 = "".length();
            }
            final int n5 = n4;
            if (ceiling_float_int > 0) {
                final float min = Math.min((n3 - n2 - (0x51 ^ 0x55)) / max, 9.0f);
                if (min > 3.0f) {
                    int i = ceiling_float_int;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    while (i < max) {
                        final float n6 = n2 + i * min;
                        final float n7 = n;
                        int n8;
                        if (n5 != 0) {
                            n8 = (0x7 ^ 0x1E);
                            "".length();
                            if (1 >= 2) {
                                throw null;
                            }
                        }
                        else {
                            n8 = (0xB3 ^ 0xA3);
                        }
                        this.drawTexturedModalRect(n6, n7, n8, "".length(), 0xCB ^ 0xC2, 0x98 ^ 0x91);
                        ++i;
                    }
                    int j = "".length();
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    while (j < ceiling_float_int) {
                        final float n9 = n2 + j * min;
                        final float n10 = n;
                        int n11;
                        if (n5 != 0) {
                            n11 = (0x5D ^ 0x44);
                            "".length();
                            if (0 >= 2) {
                                throw null;
                            }
                        }
                        else {
                            n11 = (0xA1 ^ 0xB1);
                        }
                        this.drawTexturedModalRect(n9, n10, n11, "".length(), 0x86 ^ 0x8F, 0xB0 ^ 0xB9);
                        if (n5 != 0) {
                            if (j * "  ".length() + " ".length() < networkPlayerInfo.func_178860_m()) {
                                this.drawTexturedModalRect(n2 + j * min, n, 0xF7 ^ 0xB1, "".length(), 0x6C ^ 0x65, 0xB4 ^ 0xBD);
                            }
                            if (j * "  ".length() + " ".length() == networkPlayerInfo.func_178860_m()) {
                                this.drawTexturedModalRect(n2 + j * min, n, 0x7C ^ 0x33, "".length(), 0xA6 ^ 0xAF, 0x8 ^ 0x1);
                            }
                        }
                        if (j * "  ".length() + " ".length() < scorePoints) {
                            final float n12 = n2 + j * min;
                            final float n13 = n;
                            int n14;
                            if (j >= (0xC8 ^ 0xC2)) {
                                n14 = 4 + 49 + 20 + 87;
                                "".length();
                                if (3 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                n14 = (0x41 ^ 0x75);
                            }
                            this.drawTexturedModalRect(n12, n13, n14, "".length(), 0x9F ^ 0x96, 0x67 ^ 0x6E);
                        }
                        if (j * "  ".length() + " ".length() == scorePoints) {
                            final float n15 = n2 + j * min;
                            final float n16 = n;
                            int n17;
                            if (j >= (0x85 ^ 0x8F)) {
                                n17 = 16 + 162 - 135 + 126;
                                "".length();
                                if (3 == -1) {
                                    throw null;
                                }
                            }
                            else {
                                n17 = (0x48 ^ 0x75);
                            }
                            this.drawTexturedModalRect(n15, n16, n17, "".length(), 0x1 ^ 0x8, 0x6 ^ 0xF);
                        }
                        ++j;
                    }
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                }
                else {
                    final float clamp_float = MathHelper.clamp_float(scorePoints / 20.0f, 0.0f, 1.0f);
                    final int n18 = (int)((1.0f - clamp_float) * 255.0f) << (0x56 ^ 0x46) | (int)(clamp_float * 255.0f) << (0x47 ^ 0x4F);
                    String s2 = new StringBuilder().append(scorePoints / 2.0f).toString();
                    if (n3 - this.mc.fontRendererObj.getStringWidth(String.valueOf(s2) + GuiPlayerTabOverlay.I["   ".length()]) >= n2) {
                        s2 = String.valueOf(s2) + GuiPlayerTabOverlay.I[0x28 ^ 0x2C];
                    }
                    this.mc.fontRendererObj.drawStringWithShadow(s2, (n3 + n2) / "  ".length() - this.mc.fontRendererObj.getStringWidth(s2) / "  ".length(), n, n18);
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
            }
        }
        else {
            final String string = new StringBuilder().append(EnumChatFormatting.YELLOW).append(scorePoints).toString();
            this.mc.fontRendererObj.drawStringWithShadow(string, n3 - this.mc.fontRendererObj.getStringWidth(string), n, 11979861 + 5872775 - 15128483 + 14053062);
        }
    }
    
    public void updatePlayerList(final boolean isBeingRendered) {
        if (isBeingRendered && !this.isBeingRendered) {
            this.lastTimeOpened = Minecraft.getSystemTime();
        }
        this.isBeingRendered = isBeingRendered;
    }
    
    public void setFooter(final IChatComponent footer) {
        this.footer = footer;
    }
    
    static class PlayerComparator implements Comparator<NetworkPlayerInfo>
    {
        private static final String[] I;
        
        static {
            I();
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (1 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private PlayerComparator() {
        }
        
        @Override
        public int compare(final NetworkPlayerInfo networkPlayerInfo, final NetworkPlayerInfo networkPlayerInfo2) {
            final ScorePlayerTeam playerTeam = networkPlayerInfo.getPlayerTeam();
            final ScorePlayerTeam playerTeam2 = networkPlayerInfo2.getPlayerTeam();
            final ComparisonChain start = ComparisonChain.start();
            int n;
            if (networkPlayerInfo.getGameType() != WorldSettings.GameType.SPECTATOR) {
                n = " ".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            int n2;
            if (networkPlayerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR) {
                n2 = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            final ComparisonChain compareTrueFirst = start.compareTrueFirst((boolean)(n != 0), (boolean)(n2 != 0));
            String registeredName;
            if (playerTeam != null) {
                registeredName = playerTeam.getRegisteredName();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                registeredName = PlayerComparator.I["".length()];
            }
            String registeredName2;
            if (playerTeam2 != null) {
                registeredName2 = playerTeam2.getRegisteredName();
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                registeredName2 = PlayerComparator.I[" ".length()];
            }
            return compareTrueFirst.compare((Comparable)registeredName, (Comparable)registeredName2).compare((Comparable)networkPlayerInfo.getGameProfile().getName(), (Comparable)networkPlayerInfo2.getGameProfile().getName()).result();
        }
        
        PlayerComparator(final PlayerComparator playerComparator) {
            this();
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("", "QrHyj");
            PlayerComparator.I[" ".length()] = I("", "xiNyF");
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((NetworkPlayerInfo)o, (NetworkPlayerInfo)o2);
        }
    }
}
