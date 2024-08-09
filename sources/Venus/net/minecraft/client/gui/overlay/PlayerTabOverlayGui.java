/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.overlay;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.misc.BetterMinecraft;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

public class PlayerTabOverlayGui
extends AbstractGui {
    private static final Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new PlayerComparator());
    private final Minecraft mc;
    private final IngameGui guiIngame;
    private ITextComponent footer;
    public ITextComponent header;
    private long lastTimeOpened;
    private boolean visible;
    private final Pattern namePattern = Pattern.compile("^\\w{3,16}$");

    public PlayerTabOverlayGui(Minecraft minecraft, IngameGui ingameGui) {
        this.mc = minecraft;
        this.guiIngame = ingameGui;
    }

    public ITextComponent getDisplayName(NetworkPlayerInfo networkPlayerInfo) {
        return networkPlayerInfo.getDisplayName() != null ? this.func_238524_a_(networkPlayerInfo, networkPlayerInfo.getDisplayName().deepCopy()) : this.func_238524_a_(networkPlayerInfo, ScorePlayerTeam.formatPlayerName(networkPlayerInfo.getPlayerTeam(), new StringTextComponent(networkPlayerInfo.getGameProfile().getName())));
    }

    private ITextComponent func_238524_a_(NetworkPlayerInfo networkPlayerInfo, IFormattableTextComponent iFormattableTextComponent) {
        return networkPlayerInfo.getGameType() == GameType.SPECTATOR ? iFormattableTextComponent.mergeStyle(TextFormatting.ITALIC) : iFormattableTextComponent;
    }

    public void setVisible(boolean bl) {
        if (bl && !this.visible) {
            this.lastTimeOpened = Util.milliTime();
        }
        this.visible = bl;
    }

    public void func_238523_a_(MatrixStack matrixStack, int n, Scoreboard scoreboard, @Nullable ScoreObjective scoreObjective) {
        int n2;
        int n3;
        boolean bl;
        int n4;
        boolean bl2;
        Object object;
        ClientPlayNetHandler clientPlayNetHandler = this.mc.player.connection;
        List<NetworkPlayerInfo> list = new ArrayList<NetworkPlayerInfo>();
        for (ScorePlayerTeam scorePlayerTeam : this.mc.world.getScoreboard().getTeams().stream().sorted(Comparator.comparing(Team::getName)).toList()) {
            object = scorePlayerTeam.getMembershipCollection().toString();
            if (!this.namePattern.matcher((CharSequence)(object = ((String)object).substring(1, ((String)object).length() - 1))).matches() || scorePlayerTeam.getPrefix().getString().isEmpty()) continue;
            boolean bl3 = true;
            for (NetworkPlayerInfo networkPlayerInfo : this.mc.getConnection().getPlayerInfoMap()) {
                if (!networkPlayerInfo.getGameProfile().getName().equals(object)) continue;
                bl3 = false;
            }
            if (!bl3) continue;
            IFormattableTextComponent iFormattableTextComponent = (IFormattableTextComponent)ITextComponent.getTextComponentOrEmpty(TextFormatting.GRAY + "[" + TextFormatting.RED + "V" + TextFormatting.GRAY + "] ");
            iFormattableTextComponent.append(scorePlayerTeam.getPrefix());
            iFormattableTextComponent.appendString(TextFormatting.GRAY + (String)object);
            list.add(new NetworkPlayerInfo(new SPlayerListItemPacket.AddPlayerData(new GameProfile(UUID.randomUUID(), scorePlayerTeam.getName()), 0, GameType.SURVIVAL, iFormattableTextComponent)));
        }
        list.addAll(ENTRY_ORDERING.sortedCopy(clientPlayNetHandler.getPlayerInfoMap()));
        int n5 = 0;
        int n6 = 0;
        for (NetworkPlayerInfo networkPlayerInfo : list) {
            int n7 = this.mc.fontRenderer.getStringPropertyWidth(this.getDisplayName(networkPlayerInfo));
            n5 = Math.max(n5, n7);
            if (scoreObjective == null || scoreObjective.getRenderType() == ScoreCriteria.RenderType.HEARTS) continue;
            n7 = this.mc.fontRenderer.getStringWidth(" " + scoreboard.getOrCreateScore(networkPlayerInfo.getGameProfile().getName(), scoreObjective).getScorePoints());
            n6 = Math.max(n6, n7);
        }
        object = venusfr.getInstance().getFunctionRegistry();
        BetterMinecraft betterMinecraft = ((FunctionRegistry)object).getBetterMinecraft();
        boolean bl3 = bl2 = betterMinecraft.isState() && (Boolean)betterMinecraft.betterTab.get() != false;
        if (!bl2) {
            list = list.subList(0, Math.min(list.size(), 80));
        }
        int n8 = n4 = list.size();
        int n9 = n4 > 120 && bl2 ? 40 : 20;
        int n10 = 1;
        while (n8 > n9) {
            n8 = (n4 + ++n10 - 1) / n10;
        }
        boolean bl4 = bl = this.mc.isIntegratedServerRunning() || this.mc.getConnection().getNetworkManager().isEncrypted() || bl2;
        int n11 = scoreObjective != null ? (scoreObjective.getRenderType() == ScoreCriteria.RenderType.HEARTS ? 90 : n6) : 0;
        int n12 = Math.min(n10 * ((bl ? 9 : 0) + n5 + n11 + 13), n - 50) / n10;
        int n13 = n / 2 - (n12 * n10 + (n10 - 1) * 5) / 2;
        int n14 = 10;
        int n15 = n12 * n10 + (n10 - 1) * 5;
        List<IReorderingProcessor> list2 = null;
        if (this.header != null) {
            list2 = this.mc.fontRenderer.trimStringToWidth(this.header, n - 50);
            for (IReorderingProcessor iterator2 : list2) {
                n15 = Math.max(n15, this.mc.fontRenderer.func_243245_a(iterator2));
            }
        }
        Object object2 = null;
        if (this.footer != null) {
            object2 = this.mc.fontRenderer.trimStringToWidth(this.footer, n - 50);
            Iterator n16 = object2.iterator();
            while (n16.hasNext()) {
                IReorderingProcessor iReorderingProcessor = (IReorderingProcessor)n16.next();
                n15 = Math.max(n15, this.mc.fontRenderer.func_243245_a(iReorderingProcessor));
            }
        }
        if (list2 != null) {
            PlayerTabOverlayGui.fill(matrixStack, n / 2 - n15 / 2 - 1, n14 - 1, n / 2 + n15 / 2 + 1, n14 + list2.size() * 9, Integer.MIN_VALUE);
            for (IReorderingProcessor iReorderingProcessor : list2) {
                n3 = this.mc.fontRenderer.func_243245_a(iReorderingProcessor);
                this.mc.fontRenderer.func_238407_a_(matrixStack, iReorderingProcessor, n / 2 - n3 / 2, n14, -1);
                n14 += 9;
            }
            ++n14;
        }
        PlayerTabOverlayGui.fill(matrixStack, n / 2 - n15 / 2 - 1, n14 - 1, n / 2 + n15 / 2 + 1, n14 + n8 * 9, Integer.MIN_VALUE);
        int n7 = this.mc.gameSettings.getChatBackgroundColor(0x20FFFFFF);
        for (int i = 0; i < n4; ++i) {
            int n16;
            int n17;
            int n18;
            Object object3;
            n3 = i / n8;
            n2 = i % n8;
            int n19 = n13 + n3 * n12 + n3 * 5;
            int n20 = n14 + n2 * 9;
            PlayerTabOverlayGui.fill(matrixStack, n19, n20, n19 + n12, n20 + 8, n7);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            if (i >= list.size()) continue;
            NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)list.get(i);
            GameProfile gameProfile = networkPlayerInfo.getGameProfile();
            if (bl) {
                object3 = this.mc.world.getPlayerByUuid(gameProfile.getId());
                n18 = object3 != null && ((PlayerEntity)object3).isWearing(PlayerModelPart.CAPE) && ("Dinnerbone".equals(gameProfile.getName()) || "Grumm".equals(gameProfile.getName())) ? 1 : 0;
                this.mc.getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                n17 = 8 + (n18 != 0 ? 8 : 0);
                n16 = 8 * (n18 != 0 ? -1 : 1);
                AbstractGui.blit(matrixStack, n19, n20, 8, 8, 8.0f, n17, 8, n16, 64, 64);
                if (object3 != null && ((PlayerEntity)object3).isWearing(PlayerModelPart.HAT)) {
                    int n21 = 8 + (n18 != 0 ? 8 : 0);
                    int n22 = 8 * (n18 != 0 ? -1 : 1);
                    AbstractGui.blit(matrixStack, n19, n20, 8, 8, 40.0f, n21, 8, n22, 64, 64);
                }
                n19 += 9;
            }
            object3 = this.getDisplayName(networkPlayerInfo);
            this.mc.fontRenderer.func_243246_a(matrixStack, (ITextComponent)object3, n19, n20, networkPlayerInfo.getGameType() == GameType.SPECTATOR ? -1862270977 : -1);
            if (scoreObjective != null && networkPlayerInfo.getGameType() != GameType.SPECTATOR && (n17 = (n18 = n19 + n5 + 1) + n11) - n18 > 5) {
                this.func_175247_a_(scoreObjective, n20, gameProfile.getName(), n18, n17, networkPlayerInfo, matrixStack);
            }
            if (bl2) {
                n18 = MathHelper.clamp(networkPlayerInfo.getResponseTime(), 0, 999);
                float f = Fonts.sfui.getWidth(String.valueOf(n18), 6.0f);
                n16 = ColorUtils.red;
                if (n18 < 150) {
                    n16 = ColorUtils.green;
                } else if (n18 < 300) {
                    n16 = ColorUtils.yellow;
                } else if (n18 < 600) {
                    n16 = ColorUtils.orange;
                }
                Fonts.sfui.drawTextWithOutline(matrixStack, String.valueOf(n18), (float)(n12 + n19 - (bl ? 9 : 0)) - f - 2.0f, (float)n20 + 1.5f, n16, 6.0f, 0.05f);
                continue;
            }
            this.func_238522_a_(matrixStack, n12, n19 - (bl ? 9 : 0), n20, networkPlayerInfo);
        }
        if (object2 != null) {
            n14 = n14 + n8 * 9 + 1;
            PlayerTabOverlayGui.fill(matrixStack, n / 2 - n15 / 2 - 1, n14 - 1, n / 2 + n15 / 2 + 1, n14 + object2.size() * 9, Integer.MIN_VALUE);
            Iterator iterator2 = object2.iterator();
            while (iterator2.hasNext()) {
                IReorderingProcessor iReorderingProcessor = (IReorderingProcessor)iterator2.next();
                n2 = this.mc.fontRenderer.func_243245_a(iReorderingProcessor);
                this.mc.fontRenderer.func_238407_a_(matrixStack, iReorderingProcessor, n / 2 - n2 / 2, n14, -1);
                n14 += 9;
            }
        }
    }

    protected void func_238522_a_(MatrixStack matrixStack, int n, int n2, int n3, NetworkPlayerInfo networkPlayerInfo) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GUI_ICONS_LOCATION);
        boolean bl = false;
        int n4 = networkPlayerInfo.getResponseTime() < 0 ? 5 : (networkPlayerInfo.getResponseTime() < 150 ? 0 : (networkPlayerInfo.getResponseTime() < 300 ? 1 : (networkPlayerInfo.getResponseTime() < 600 ? 2 : (networkPlayerInfo.getResponseTime() < 1000 ? 3 : 4))));
        this.setBlitOffset(this.getBlitOffset() + 100);
        this.blit(matrixStack, n2 + n - 11, n3, 0, 176 + n4 * 8, 10, 8);
        this.setBlitOffset(this.getBlitOffset() - 100);
    }

    private void func_175247_a_(ScoreObjective scoreObjective, int n, String string, int n2, int n3, NetworkPlayerInfo networkPlayerInfo, MatrixStack matrixStack) {
        int n4 = scoreObjective.getScoreboard().getOrCreateScore(string, scoreObjective).getScorePoints();
        if (scoreObjective.getRenderType() == ScoreCriteria.RenderType.HEARTS) {
            boolean bl;
            this.mc.getTextureManager().bindTexture(GUI_ICONS_LOCATION);
            long l = Util.milliTime();
            if (this.lastTimeOpened == networkPlayerInfo.getRenderVisibilityId()) {
                if (n4 < networkPlayerInfo.getLastHealth()) {
                    networkPlayerInfo.setLastHealthTime(l);
                    networkPlayerInfo.setHealthBlinkTime(this.guiIngame.getTicks() + 20);
                } else if (n4 > networkPlayerInfo.getLastHealth()) {
                    networkPlayerInfo.setLastHealthTime(l);
                    networkPlayerInfo.setHealthBlinkTime(this.guiIngame.getTicks() + 10);
                }
            }
            if (l - networkPlayerInfo.getLastHealthTime() > 1000L || this.lastTimeOpened != networkPlayerInfo.getRenderVisibilityId()) {
                networkPlayerInfo.setLastHealth(n4);
                networkPlayerInfo.setDisplayHealth(n4);
                networkPlayerInfo.setLastHealthTime(l);
            }
            networkPlayerInfo.setRenderVisibilityId(this.lastTimeOpened);
            networkPlayerInfo.setLastHealth(n4);
            int n5 = MathHelper.ceil((float)Math.max(n4, networkPlayerInfo.getDisplayHealth()) / 2.0f);
            int n6 = Math.max(MathHelper.ceil(n4 / 2), Math.max(MathHelper.ceil(networkPlayerInfo.getDisplayHealth() / 2), 10));
            boolean bl2 = bl = networkPlayerInfo.getHealthBlinkTime() > (long)this.guiIngame.getTicks() && (networkPlayerInfo.getHealthBlinkTime() - (long)this.guiIngame.getTicks()) / 3L % 2L == 1L;
            if (n5 > 0) {
                int n7 = MathHelper.floor(Math.min((float)(n3 - n2 - 4) / (float)n6, 9.0f));
                if (n7 > 3) {
                    int n8;
                    for (n8 = n5; n8 < n6; ++n8) {
                        this.blit(matrixStack, n2 + n8 * n7, n, bl ? 25 : 16, 0, 9, 9);
                    }
                    for (n8 = 0; n8 < n5; ++n8) {
                        this.blit(matrixStack, n2 + n8 * n7, n, bl ? 25 : 16, 0, 9, 9);
                        if (bl) {
                            if (n8 * 2 + 1 < networkPlayerInfo.getDisplayHealth()) {
                                this.blit(matrixStack, n2 + n8 * n7, n, 70, 0, 9, 9);
                            }
                            if (n8 * 2 + 1 == networkPlayerInfo.getDisplayHealth()) {
                                this.blit(matrixStack, n2 + n8 * n7, n, 79, 0, 9, 9);
                            }
                        }
                        if (n8 * 2 + 1 < n4) {
                            this.blit(matrixStack, n2 + n8 * n7, n, n8 >= 10 ? 160 : 52, 0, 9, 9);
                        }
                        if (n8 * 2 + 1 != n4) continue;
                        this.blit(matrixStack, n2 + n8 * n7, n, n8 >= 10 ? 169 : 61, 0, 9, 9);
                    }
                } else {
                    float f = MathHelper.clamp((float)n4 / 20.0f, 0.0f, 1.0f);
                    int n9 = (int)((1.0f - f) * 255.0f) << 16 | (int)(f * 255.0f) << 8;
                    String string2 = "" + (float)n4 / 2.0f;
                    if (n3 - this.mc.fontRenderer.getStringWidth(string2 + "hp") >= n2) {
                        string2 = string2 + "hp";
                    }
                    this.mc.fontRenderer.drawStringWithShadow(matrixStack, string2, (n3 + n2) / 2 - this.mc.fontRenderer.getStringWidth(string2) / 2, n, n9);
                }
            }
        } else {
            String string3 = "" + TextFormatting.YELLOW + n4;
            this.mc.fontRenderer.drawStringWithShadow(matrixStack, string3, n3 - this.mc.fontRenderer.getStringWidth(string3), n, 0xFFFFFF);
        }
    }

    public void setFooter(@Nullable ITextComponent iTextComponent) {
        this.footer = iTextComponent;
    }

    public void setHeader(@Nullable ITextComponent iTextComponent) {
        this.header = iTextComponent;
    }

    public void resetFooterHeader() {
        this.header = null;
        this.footer = null;
    }

    public static class PlayerComparator
    implements Comparator<NetworkPlayerInfo> {
        private PlayerComparator() {
        }

        @Override
        public int compare(NetworkPlayerInfo networkPlayerInfo, NetworkPlayerInfo networkPlayerInfo2) {
            ScorePlayerTeam scorePlayerTeam = networkPlayerInfo.getPlayerTeam();
            ScorePlayerTeam scorePlayerTeam2 = networkPlayerInfo2.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst(networkPlayerInfo.getGameType() != GameType.SPECTATOR, networkPlayerInfo2.getGameType() != GameType.SPECTATOR).compare((Comparable<?>)((Object)(scorePlayerTeam != null ? scorePlayerTeam.getName() : "")), (Comparable<?>)((Object)(scorePlayerTeam2 != null ? scorePlayerTeam2.getName() : ""))).compare(networkPlayerInfo.getGameProfile().getName(), networkPlayerInfo2.getGameProfile().getName(), String::compareToIgnoreCase).result();
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((NetworkPlayerInfo)object, (NetworkPlayerInfo)object2);
        }
    }
}

