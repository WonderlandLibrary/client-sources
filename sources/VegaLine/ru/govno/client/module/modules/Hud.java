/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.Vec2f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventRender2D;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.BloomUtil;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;
import ru.govno.client.utils.Render.Vec2fColored;
import ru.govno.client.utils.TPSDetect;

public class Hud
extends Module {
    private float expand;
    private float width1;
    private float width2;
    private float width3;
    public static Hud get;
    Settings WX;
    Settings WY;
    Settings Information;
    Settings Info;
    Settings Watermark;
    Settings MarkMode;
    Settings Potions;
    Settings PX;
    Settings PY;
    Settings CustomHotbar;
    Settings HotbarStyle;
    Settings ArmorHUD;
    Settings AX;
    Settings AY;
    Settings StaffList;
    Settings SX;
    Settings SY;
    Settings ArrayList;
    Settings LX;
    Settings LY;
    Settings KeyBinds;
    Settings KX;
    Settings KY;
    Settings PickupsList;
    Settings PCX;
    Settings PCY;
    Settings LagDetect;
    Settings HudRectMode;
    private final Pattern validUserPattern = Pattern.compile("^\\w{3,16}$");
    TimerHelper laggCheck = new TimerHelper();
    private final AnimationUtils laggAlphaPC = new AnimationUtils(0.0f, 0.0f, 0.1f);
    private static final AnimationUtils alphaPC;
    public static List<Module> enabledModules;
    private static final TimerHelper arrayListLastUpdateTime;
    public static float listPosX;
    public static float listPosY;
    private boolean side;
    private int segment;
    private int nolo;
    private int secconds;
    private int mins;
    private int hours;
    private float yIndent;
    private float xPos;
    private float yPos;
    private float cullingX;
    TimerHelper texter = new TimerHelper();
    int ticks = 0;
    ArrayList<StaffPlayer> staffPlayers = new ArrayList();
    List<Module> bindsList = new ArrayList<Module>();
    private final List<PickupItem> notifysList = new ArrayList<PickupItem>();
    private final AnimationUtils potionsHeight = new AnimationUtils(Hud.getPotionHudHeight(), Hud.getPotionHudHeight(), 0.075f);
    private final AnimationUtils staffsHeight = new AnimationUtils(this.getStaffHudHeight(), this.getStaffHudHeight(), 0.075f);
    private final AnimationUtils keybindsHeight = new AnimationUtils(this.getKeyBindsHudHeight(), this.getKeyBindsHudHeight(), 0.075f);
    private final AnimationUtils pickupsHeight = new AnimationUtils(this.getPickupsHudHeight(), this.getPickupsHudHeight(), 0.125f);
    public static float wmPosX;
    public static float wmPosY;
    public static float wmWidth;
    public static float wmHeight;
    public static float potPosX;
    public static float potPosY;
    public static float potWidth;
    public static float potHeight;
    public static float armPosX;
    public static float armPosY;
    public static float armWidth;
    public static float armHeight;
    public static float stPosX;
    public static float stPosY;
    public static float stWidth;
    public static float stHeight;
    public static float kbPosX;
    public static float kbPosY;
    public static float kbWidth;
    public static float kbHeight;
    public static float pcPosX;
    public static float pcPosY;
    public static float pcWidth;
    public static float pcHeight;
    private final List<PotionWithString> potionsWithString = new ArrayList<PotionWithString>();

    public Hud() {
        super("Hud", 0, Module.Category.RENDER);
        this.WX = new Settings("WX", 0.01f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.WX);
        this.WY = new Settings("WY", 0.01f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.WY);
        this.Information = new Settings("Information", true, (Module)this);
        this.settings.add(this.Information);
        this.Info = new Settings("Info", "Sleek", this, new String[]{"Sleek", "Akrien", "Plastic", "Modern"}, () -> this.Information.bValue);
        this.settings.add(this.Info);
        this.Watermark = new Settings("Watermark", true, (Module)this);
        this.settings.add(this.Watermark);
        this.MarkMode = new Settings("MarkMode", "Default", (Module)this, new String[]{"Default", "Chess", "Sweet", "Bloom", "Clock", "Wonderful", "Plate"});
        this.settings.add(this.MarkMode);
        this.Potions = new Settings("Potions", true, (Module)this);
        this.settings.add(this.Potions);
        this.PX = new Settings("PX", 0.01f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.PX);
        this.PY = new Settings("PY", 0.35f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.PY);
        this.CustomHotbar = new Settings("CustomHotbar", true, (Module)this);
        this.settings.add(this.CustomHotbar);
        this.HotbarStyle = new Settings("HotbarStyle", "Sleek", this, new String[]{"Sleek", "Modern"}, () -> this.CustomHotbar.bValue);
        this.settings.add(this.HotbarStyle);
        this.ArmorHUD = new Settings("ArmorHUD", true, (Module)this);
        this.settings.add(this.ArmorHUD);
        this.AX = new Settings("AX", 0.005f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.AX);
        this.AY = new Settings("AY", 0.7f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.AY);
        this.StaffList = new Settings("StaffList", false, (Module)this);
        this.settings.add(this.StaffList);
        this.SX = new Settings("SX", 0.005f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.SX);
        this.SY = new Settings("SY", 0.4f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.SY);
        this.ArrayList = new Settings("ArrayList", true, (Module)this);
        this.settings.add(this.ArrayList);
        this.LX = new Settings("LX", 0.999f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.LX);
        this.LY = new Settings("LY", 0.004f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.LY);
        this.KeyBinds = new Settings("KeyBinds", false, (Module)this);
        this.settings.add(this.KeyBinds);
        this.KX = new Settings("KX", 0.005f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.KX);
        this.KY = new Settings("KY", 0.6f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.KY);
        this.PickupsList = new Settings("PickupsList", true, (Module)this);
        this.settings.add(this.PickupsList);
        this.PCX = new Settings("PCX", 0.005f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.PCX);
        this.PCY = new Settings("PCY", 0.8f, 1.0f, 0.0f, this, () -> false);
        this.settings.add(this.PCY);
        this.LagDetect = new Settings("LagDetect", false, (Module)this);
        this.settings.add(this.LagDetect);
        this.HudRectMode = new Settings("HudRectMode", "Glow", this, new String[]{"Glow", "Window", "Plain", "Stipple"}, () -> this.Potions.bValue || this.StaffList.bValue || this.KeyBinds.bValue || this.PickupsList.bValue);
        this.settings.add(this.HudRectMode);
        get = this;
    }

    private void clientRect(float x, float y, float x2, float y2, String element) {
        if (get == null || this.HudRectMode.currentMode == null) {
            return;
        }
        RenderUtils.hudRectWithString(x, y, x2, y2, element, this.HudRectMode.currentMode);
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (this.actived && this.LagDetect.bValue) {
            this.laggCheck.reset();
            this.laggAlphaPC.to = 0.0f;
        }
    }

    public static final String getModName(Module mod, boolean titles) {
        return titles ? mod.getDisplayName() : mod.getName();
    }

    public static final List<Module> getMods() {
        return enabledModules;
    }

    public static final CFontRenderer font() {
        return Fonts.comfortaaBold_13;
    }

    public static void onTriger(Module mod) {
        if ((double)mod.stateAnim.getAnim() < 0.1 && mod.stateAnim.to == 0.0f) {
            mod.stateAnim.setAnim(0.0f);
        }
        float f = mod.stateAnim.to = mod.actived ? 1.0f : 0.0f;
        if (MathUtils.getDifferenceOf(mod.stateAnim.to, mod.stateAnim.anim) > 0.1) {
            arrayListLastUpdateTime.reset();
        }
    }

    public boolean isTitles() {
        return true;
    }

    public boolean isOnlyBound() {
        return false;
    }

    public void setupArrayListCoords(ScaledResolution sr) {
        float curX = this.LX.fValue * (float)sr.getScaledWidth();
        float curY = this.LY.fValue * (float)sr.getScaledHeight();
        float extY = 0.0f;
        if (!(Minecraft.player == null || Minecraft.player != null && Minecraft.player.getActivePotionEffects().size() == 0 || this.isPotsCustom())) {
            boolean a = false;
            boolean b = false;
            for (PotionEffect apf : Minecraft.player.getActivePotionEffects()) {
                a = true;
                if (!apf.getPotion().isBadEffect()) continue;
                b = true;
            }
            extY = a || b ? (float)(26 * (b ? 2 : 1) * (curX > (float)(sr.getScaledWidth() - (Minecraft.player == null ? 0 : Minecraft.player.getActivePotionEffects().size()) * 26) ? 1 : 0)) : 0.0f;
        }
        curY += extY;
        float speedAnim = (float)Minecraft.frameTime * 0.0175f;
        if (listPosX == 0.0f) {
            listPosX = curX;
        }
        if (listPosY == 0.0f) {
            listPosY = curY;
        }
        listPosX = MathUtils.harp(listPosX, curX, speedAnim);
        listPosY = MathUtils.harp(listPosY, curY, speedAnim);
    }

    public void trigerSort(boolean titles) {
        if (this.isKeyBindsHud()) {
            this.setupBindsList();
        }
        if (get == null || !Hud.get.actived || !this.ArrayList.bValue) {
            return;
        }
        List<String> matches = Arrays.asList("nointeract", "procontainer", "respawn", "namesecurity", "srpspoofer", "middleclick", "onfalling");
        ScaledResolution sr = new ScaledResolution(mc);
        CFontRenderer font = Hud.font();
        boolean reverseY = this.isReverseY(sr);
        enabledModules = Client.moduleManager.getModuleList().stream().filter(Module::showToArrayList).filter(m -> !m.getName().toLowerCase().contains("helper") && !matches.stream().anyMatch(mat -> mat.contains(m.getName().toLowerCase()))).sorted(Comparator.comparingDouble(e -> {
            Hud.onTriger(e);
            return font.getStringWidth(Hud.getModName(e, titles)) * (reverseY ? 1 : -1);
        })).collect(Collectors.toList());
    }

    private void setupAlphaPC() {
        boolean cur;
        boolean bl = cur = this.actived && this.ArrayList.bValue;
        if (Hud.alphaPC.to != (float)cur) {
            Hud.alphaPC.to = (float)cur;
        }
        if (MathUtils.getDifferenceOf(alphaPC.getAnim(), (float)cur) < (double)0.01f) {
            alphaPC.setAnim((float)cur);
        }
    }

    private float getAlphaPC() {
        return alphaPC.getAnim();
    }

    private boolean canRenderList() {
        return enabledModules != null && enabledModules.size() != 0 && this.getAlphaPC() != 0.0f;
    }

    public boolean isReverseY(ScaledResolution sr) {
        return listPosY + this.getArrayHeight() / 2.0f > (float)sr.getScaledHeight() / 2.0f;
    }

    public boolean isReverseX(ScaledResolution sr) {
        return listPosX - this.getArrayWidth(sr) / 2.0f < (float)sr.getScaledWidth() / 2.0f;
    }

    public float getArrayWidth(ScaledResolution sr) {
        if (enabledModules == null || enabledModules != null && enabledModules.size() == 0) {
            return 0.0f;
        }
        Module toCheck = this.isReverseY(sr) ? enabledModules.get(enabledModules.size() - 1) : enabledModules.get(0);
        return toCheck == null ? 0.0f : (float)Hud.font().getStringWidth(Hud.getModName(toCheck, this.isTitles())) + 3.5f + 1.0f + 1.5f;
    }

    public float getArrayHeight() {
        if (enabledModules == null || enabledModules != null && enabledModules.size() == 0) {
            return 0.0f;
        }
        float h = 0.0f;
        for (Module mod : enabledModules) {
            h += this.getArrayStep() * mod.stateAnim.getAnim();
        }
        return h + 0.5f;
    }

    private float getArrayStep() {
        return 10.001f;
    }

    @Override
    public void alwaysRender2D(ScaledResolution sr) {
        this.setupAlphaPC();
        if (!this.canRenderList()) {
            return;
        }
        if (System.currentTimeMillis() % 300L < 50L || Hud.mc.currentScreen instanceof GuiChat) {
            this.trigerSort(this.isTitles());
        }
        this.setupArrayListCoords(sr);
        boolean extY = false;
        float x = listPosX;
        float y = listPosY;
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        float size = enabledModules.size();
        float aPC = this.getAlphaPC();
        this.drawAList(x, y, false, aPC, sr);
        GlStateManager.enableDepth();
        RenderUtils.fixShadows();
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        String coords;
        CFontRenderer font;
        ScaledResolution sr = event.getResolution();
        if (this.Information.bValue && this.Info.currentMode.equalsIgnoreCase("Sleek") && this.actived) {
            font = Fonts.roboto_13;
            coords = "\u00a77XYZ:\u00a7r " + (int)Minecraft.player.posX + "," + (Serializable)(Minecraft.player.posY == (double)((int)Minecraft.player.posY) ? Integer.valueOf((int)Minecraft.player.posY) : String.format("%.1f", Minecraft.player.posY)) + "," + (int)Minecraft.player.posZ;
            double posX = Minecraft.player.posX;
            double prevX = posX - Minecraft.player.prevPosX;
            double posZ = Minecraft.player.posZ;
            double prevZ = posZ - Minecraft.player.prevPosZ;
            int bgColorForShadow = ColorUtils.getColor(10, 10, 10, 100);
            double bps = Math.sqrt(prevX * prevX + prevZ * prevZ) * 15.3571428571;
            String speed = "\u00a77BPS:\u00a7r " + String.format("%.2f", bps);
            String myName = "\u00a77NAME:\u00a7r " + Minecraft.player.getName();
            this.expand = Hud.mc.currentScreen instanceof GuiChat ? MathUtils.harp(this.expand, 15.0f, 0.1f) : MathUtils.harp(this.expand, 0.0f, 0.05f);
            this.width1 = MathUtils.harp(this.width1, font.getStringWidth(coords), 0.05f);
            this.width2 = MathUtils.harp(this.width2, font.getStringWidth(speed), 0.05f);
            this.width3 = MathUtils.harp(this.width3, font.getStringWidth(myName), 0.05f);
            GL11.glPushMatrix();
            GL11.glTranslated(1.0, -this.expand, 0.0);
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(3.5f, (float)sr.getScaledHeight() - 11.5f, 6.5f + this.width1, sr.getScaledHeight() - 4, 0.0f, 2.0f, bgColorForShadow, bgColorForShadow, bgColorForShadow, bgColorForShadow, false, true, true);
            font.drawString(coords, 5.0, (float)sr.getScaledHeight() - 8.5f, ColorUtils.getColor(155, 155, 155, 155));
            font.drawString(coords, 5.0, sr.getScaledHeight() - 9, ColorUtils.getColor(255, 255, 255));
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(3.5f + this.width1 + 7.0f, (float)sr.getScaledHeight() - 11.5f, 6.5f + this.width1 + this.width2 + 7.0f, sr.getScaledHeight() - 4, 0.0f, 2.0f, bgColorForShadow, bgColorForShadow, bgColorForShadow, bgColorForShadow, false, true, true);
            font.drawString(speed, 5.0f + this.width1 + 7.0f, (float)sr.getScaledHeight() - 8.5f, ColorUtils.getColor(155, 155, 155, 155));
            font.drawString(speed, 5.0f + this.width1 + 7.0f, sr.getScaledHeight() - 9, ColorUtils.getColor(255, 255, 255));
            RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(3.5f + this.width1 + 7.0f + this.width2 + 7.0f, (float)sr.getScaledHeight() - 11.5f, 6.5f + this.width1 + this.width2 + 7.0f + this.width3 + 7.0f, sr.getScaledHeight() - 4, 0.0f, 2.0f, bgColorForShadow, bgColorForShadow, bgColorForShadow, bgColorForShadow, false, true, true);
            font.drawString(myName, 5.0f + this.width1 + 7.0f + this.width2 + 7.0f, (float)sr.getScaledHeight() - 8.5f, ColorUtils.getColor(155, 155, 155, 155));
            font.drawString(myName, 5.0f + this.width1 + 7.0f + this.width2 + 7.0f, sr.getScaledHeight() - 9, ColorUtils.getColor(255, 255, 255));
            GL11.glPopMatrix();
        }
        if (this.Information.bValue && this.Info.currentMode.equalsIgnoreCase("Plastic") && this.actived) {
            font = Fonts.mntsb_12;
            coords = "\u00a77XYZ:\u00a7r " + (int)Minecraft.player.posX + ", " + (Serializable)(Minecraft.player.posY == (double)((int)Minecraft.player.posY) ? Integer.valueOf((int)Minecraft.player.posY) : String.format("%.1f", Minecraft.player.posY)) + ", " + (int)Minecraft.player.posZ;
            double prevX = Minecraft.player.posX - Minecraft.player.prevPosX;
            double prevZ = Minecraft.player.posZ - Minecraft.player.prevPosZ;
            int bgColorForShadow = ColorUtils.getColor(10, 10, 10, 100);
            double bps = Math.sqrt(prevX * prevX + prevZ * prevZ) * 15.3571428571;
            if (Speed.get.actived && Speed.get.currentMode("AntiCheat").equalsIgnoreCase("Strict")) {
                bps *= (double)(1.0f + (Minecraft.player.ticksExisted % 6 > 2 ? 0.4f : 0.0f));
            }
            String speed = "\u00a77BPS:\u00a7r " + ((bps *= Hud.mc.timer.speed) == 0.0 ? "0.0" : String.format("%.2f", bps));
            String myName = "\u00a77NAME:\u00a7r " + Minecraft.player.getName();
            String ping = "\u00a77PING:\u00a7r " + (Serializable)(Minecraft.player.connection.getPlayerInfo(Minecraft.player.getUniqueID()) != null ? Integer.valueOf(Minecraft.player.connection.getPlayerInfo(Minecraft.player.getUniqueID()).getResponseTime()) : "0");
            String tps = "\u00a77TPS\u00a7r " + TPSDetect.getTpsString();
            String fps = "\u00a77FPS:\u00a7r " + Minecraft.getDebugFPS();
            if (Hud.mc.currentScreen instanceof GuiChat) {
                if (MathUtils.getDifferenceOf(this.expand, 16.0f) != 0.0) {
                    this.expand = MathUtils.harp(this.expand, 16.0f, (float)Minecraft.frameTime * 0.04f);
                }
            } else if (MathUtils.getDifferenceOf(this.expand, 0.0f) != 0.0) {
                this.expand = MathUtils.harp(this.expand, 0.0f, (float)Minecraft.frameTime * 0.04f);
            }
            int y = sr.getScaledHeight() - 19 - (int)this.expand;
            font.drawStringWithShadow(speed, 3.5, y, -1);
            font.drawStringWithShadow(coords, 3.5, y += 6, -1);
            font.drawStringWithShadow(myName, 3.5, y += 6, -1);
            font.drawStringWithShadow(tps, sr.getScaledWidth() - 3 - Fonts.mntsb_12.getStringWidth(tps), y -= 7, -1);
            font.drawStringWithShadow(ping, (float)sr.getScaledWidth() - 14.5f - (float)Fonts.mntsb_12.getStringWidth(ping), y += 7, -1);
            RenderUtils.drawPlayerPing(sr.getScaledWidth() - 13, y -= 4, Minecraft.player, 255.0f);
        }
        if (this.Information.bValue && this.Info.currentMode.equalsIgnoreCase("Akrien") && this.actived) {
            this.expand = Hud.mc.currentScreen instanceof GuiChat ? MathUtils.harp(this.expand, 16.0f, 0.1f) : MathUtils.harp(this.expand, 0.0f, 0.05f);
            GL11.glPushMatrix();
            GL11.glTranslated(1.0, -this.expand, 0.0);
            String coords2 = TextFormatting.WHITE + "Coords: " + TextFormatting.GREEN + (int)Minecraft.player.posX + "," + (int)Minecraft.player.posY + "," + (int)Minecraft.player.posZ + " " + TextFormatting.RED + "(" + (int)Minecraft.player.posX / 8 + "," + (int)Minecraft.player.posY + "," + (int)Minecraft.player.posZ / 8 + ")";
            String coords22 = "Coords: " + (int)Minecraft.player.posX + "," + (int)Minecraft.player.posY + "," + (int)Minecraft.player.posZ + " (" + (int)Minecraft.player.posX / 8 + "," + (int)Minecraft.player.posY + "," + (int)Minecraft.player.posZ / 8 + ")";
            if (Hud.mc.world.getBiome(new BlockPos(Minecraft.player)).getBiomeName().equalsIgnoreCase("Hell")) {
                coords2 = TextFormatting.WHITE + "Coords: " + TextFormatting.GREEN + (int)(Minecraft.player.posX * 8.0) + "," + (int)Minecraft.player.posY + "," + (int)(Minecraft.player.posZ * 8.0) + " " + TextFormatting.RED + "(" + (int)Minecraft.player.posX + "," + (int)Minecraft.player.posY + "," + (int)Minecraft.player.posZ + ")";
                coords22 = "Coords: " + (int)(Minecraft.player.posX * 8.0) + "," + (int)Minecraft.player.posY + "," + (int)(Minecraft.player.posZ * 8.0) + " (" + (int)Minecraft.player.posX + "," + (int)Minecraft.player.posY + "," + (int)Minecraft.player.posZ + ")";
            }
            int colorOutline = ColorUtils.getColor(20, 20, 20, 100);
            Fonts.roboto_13.drawString(coords22, 2.5, sr.getScaledHeight() - 7, colorOutline);
            Fonts.roboto_13.drawString(coords22, 1.5, sr.getScaledHeight() - 7, colorOutline);
            Fonts.roboto_13.drawString(coords22, 2.0, (float)sr.getScaledHeight() - 6.5f, colorOutline);
            Fonts.roboto_13.drawString(coords22, 2.0, (float)sr.getScaledHeight() - 7.5f, colorOutline);
            Fonts.roboto_13.drawString(coords2, 2.0, sr.getScaledHeight() - 7, -1);
            double posX = Minecraft.player.posX;
            double prevX = posX - Minecraft.player.prevPosX;
            double posZ = Minecraft.player.posZ;
            double prevZ = posZ - Minecraft.player.prevPosZ;
            float bps = (int)(Math.sqrt(prevX * prevX + prevZ * prevZ) * 15.3571428571 * 2.0);
            String speed = "Blocks/s: " + TextFormatting.GRAY + String.format("%.1f", Float.valueOf(bps / 2.0f));
            String speed2 = "Blocks/s: " + String.format("%.1f", Float.valueOf(bps / 2.0f));
            Fonts.roboto_13.drawString(speed2, 2.5, sr.getScaledHeight() - 15, colorOutline);
            Fonts.roboto_13.drawString(speed2, 1.5, sr.getScaledHeight() - 15, colorOutline);
            Fonts.roboto_13.drawString(speed2, 2.0, (float)sr.getScaledHeight() - 14.5f, colorOutline);
            Fonts.roboto_13.drawString(speed2, 2.0, (float)sr.getScaledHeight() - 15.5f, colorOutline);
            Fonts.roboto_13.drawString(speed, 2.0, sr.getScaledHeight() - 15, -1);
            GlStateManager.resetColor();
            GL11.glPopMatrix();
        }
        if (this.Information.bValue && this.Info.currentMode.equalsIgnoreCase("Modern") && this.actived) {
            font = Fonts.noise_14;
            float xl = 7.0f;
            float xOld = 7.0f;
            this.expand = Hud.mc.currentScreen instanceof GuiChat ? MathUtils.harp(this.expand, 16.0f, (float)Minecraft.frameTime * 0.015f) : MathUtils.harp(this.expand, 0.0f, (float)Minecraft.frameTime * 0.015f);
            float y = (float)(sr.getScaledHeight() - 6 - font.getHeight()) - this.expand;
            String coords3 = (int)Minecraft.player.posX + "," + (int)Minecraft.player.posY + "," + (int)Minecraft.player.posZ;
            double posX = Minecraft.player.posX;
            double prevX = posX - Minecraft.player.prevPosX;
            double posZ = Minecraft.player.posZ;
            double prevZ = posZ - Minecraft.player.prevPosZ;
            int bgColorForShadow = ColorUtils.getColor(10, 10, 10, 100);
            double bps = Math.sqrt(prevX * prevX + prevZ * prevZ) * 15.3571428571;
            String speed = String.format("%.2f", bps);
            String myName = "" + Minecraft.getDebugFPS();
            int colorN = ColorUtils.getColor(0, 0, 50, 65);
            int colorP = ColorUtils.getColor(0, 0, 45, 45);
            RenderUtils.resetBlender();
            RenderUtils.drawAlphedRect(xl - 1.5f, y - 3.5f, xl + (float)font.getStringWidth("XYZ: ") - 1.0f, (double)(y + (float)font.getHeight()) + 1.5, colorN);
            font.drawVGradientString("XYZ: ", xl, y, ColorUtils.getColor(100, 100, 255), ColorUtils.getColor(255, 100, 255));
            RenderUtils.drawAlphedRect((xl += (float)font.getStringWidth("XYZ: ")) - 1.0f, y - 3.5f, xl + (float)font.getStringWidth(coords3) + 0.5f, (double)(y + (float)font.getHeight()) + 1.5, colorP);
            font.drawVHGradientString(coords3, xl, y, ColorUtils.getColor(100, 100, 255), ColorUtils.getColor(255, 255, 90), ColorUtils.getColor(255, 20, 0), ColorUtils.getColor(255, 100, 255));
            xl += (float)font.getStringWidth(coords3);
            RenderUtils.drawAlphedRect((xl += 1.5f) - 1.0f, y - 3.5f, xl + (float)font.getStringWidth("BPS: ") - 1.0f, (double)(y + (float)font.getHeight()) + 1.5, colorN);
            font.drawVGradientString("BPS: ", xl, y, ColorUtils.getColor(255, 255, 90), ColorUtils.getColor(255, 0, 0));
            RenderUtils.drawAlphedRect((xl += (float)font.getStringWidth("BPS: ")) - 1.0f, y - 3.5f, xl + (float)font.getStringWidth(speed) + 0.5f, (double)(y + (float)font.getHeight()) + 1.5, colorP);
            font.drawVHGradientString(speed, xl, y, ColorUtils.getColor(255, 255, 90), ColorUtils.getColor(90, 255, 90), ColorUtils.getColor(0, 55, 255), ColorUtils.getColor(255, 0, 0));
            xl += (float)font.getStringWidth(speed);
            RenderUtils.drawAlphedRect((xl += 1.5f) - 1.0f, y - 3.5f, xl + (float)font.getStringWidth("FPS: ") - 1.0f, (double)(y + (float)font.getHeight()) + 1.5, colorN);
            font.drawVGradientString("FPS: ", xl, y, ColorUtils.getColor(90, 255, 90), ColorUtils.getColor(0, 55, 255));
            RenderUtils.drawAlphedRect((xl += (float)font.getStringWidth("FPS: ")) - 1.0f, y - 3.5f, xl + (float)font.getStringWidth(myName) + 2.0f, (double)(y + (float)font.getHeight()) + 1.5, colorP);
            font.drawVHGradientString(myName, xl, y, ColorUtils.getColor(90, 255, 90), ColorUtils.getColor(100, 155, 255), ColorUtils.getColor(155, 255, 255), ColorUtils.getColor(0, 55, 255));
            RenderUtils.drawBloomedFullShadowFullGradientRectBool(xOld - 1.5f, y - 3.5f, (xl += (float)font.getStringWidth(myName)) + 2.0f, y + (float)font.getHeight() + 1.5f, 2.5f, 0, 0, 0, 0, 0, 40, false, false, true);
            RenderUtils.resetBlender();
        }
        if (this.Watermark.bValue) {
            float speedAnim = (float)Minecraft.frameTime * 0.0175f;
            float curX = this.WX.fValue * (float)sr.getScaledWidth();
            float curY = this.WY.fValue * (float)sr.getScaledHeight();
            float curW = 0.0f;
            float curH = 0.0f;
            float x = wmPosX;
            float y = wmPosY;
            float x2 = x + wmWidth;
            float y2 = y + wmHeight;
            String mode = this.MarkMode.currentMode;
            if (mode.equalsIgnoreCase("Default")) {
                CFontRenderer font2 = Fonts.neverlose500_13;
                String wm = "\u0412\u0435\u0433\u0443\u043b\u044f :3 | " + Minecraft.getDebugFPS() + "fps";
                curW = (float)font2.getStringWidth(wm) + 13.5f;
                curH = 9.5f;
                int c1 = ColorUtils.getColor(0, 0, 0, 140);
                int c2 = ColorUtils.getColor(255, 255, 255);
                ArrayList<Vec2f> vecs = new ArrayList<Vec2f>();
                vecs.add(new Vec2f(x + curH / 2.0f + 0.5f, y));
                vecs.add(new Vec2f(x + curH - 1.5f, y));
                vecs.add(new Vec2f(x + curH / 2.0f - 1.5f, y2));
                vecs.add(new Vec2f(x + 0.5f, y2));
                RenderUtils.drawSome(vecs, c2);
                vecs.clear();
                vecs.add(new Vec2f(x + curH / 2.0f + 2.0f, y));
                vecs.add(new Vec2f(x2, y));
                vecs.add(new Vec2f(x2 - curH / 2.0f, y2));
                vecs.add(new Vec2f(x + 2.0f, y2));
                RenderUtils.drawSome(vecs, c1);
                vecs.clear();
                font2.drawStringWithShadow(wm, x + 8.0f, y + 3.5f, -1);
            } else if (mode.equalsIgnoreCase("Chess")) {
                CFontRenderer font3 = Fonts.roadrage_36;
                String name = "VEGALINE";
                float index = 0.0f;
                int col1 = ColorUtils.getColor(130, 130, 130);
                int col2 = ColorUtils.getColor(255, 255, 255);
                curW = font3.getStringWidth("VEGALINE") + 2;
                curH = 23.0f;
                for (char lolik : "VEGALINE".toCharArray()) {
                    int c = ColorUtils.fadeColor(col2, col1, 0.3f, 1000 - (int)(index * 5.0f));
                    font3.drawStringWithShadow("" + lolik, x + index, (double)y + 0.5, c);
                    index += (float)font3.getStringWidth(String.valueOf(lolik));
                }
            } else if (mode.equalsIgnoreCase("Sweet")) {
                float w2;
                float w;
                CFontRenderer font4 = Fonts.noise_18;
                CFontRenderer font2 = Fonts.comfortaa_12;
                int c = ColorUtils.getOverallColorFrom(ClientColors.getColor1(), ColorUtils.getColor(0), 0.8f);
                int c2 = ColorUtils.getOverallColorFrom(ClientColors.getColor2(), ColorUtils.getColor(0, 100), 0.8f);
                int c5 = ClientColors.getColor1();
                int c6 = ClientColors.getColor2();
                float w1 = (float)font4.getStringWidth("\u0412\u0435\u0433\u0443\u043b\u044f [" + Minecraft.getDebugFPS() + "fps]") + 2.5f;
                curW = w = w1 > (w2 = (float)font2.getStringWidth("\u043a\u0430\u043a \u0432\u0441\u0435\u0433\u0434\u0430 \u043d\u0430 \u0432\u044b\u0441\u043e\u0442\u0435!") + 2.5f) ? w1 : w2;
                curH = 17.0f;
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, 3.0f, 0.5f, c, c2, c2, c, false, true, true);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + 1.5f, y + 2.0f, x + 2.0f, y2 - 2.0f, 0.0f, 1.0f, c5, c5, c6, c6, true, true, true);
                font4.drawClientColoredString("\u0412\u0435\u0433\u0443\u043b\u044f [" + Minecraft.getDebugFPS() + "fps]", x + 4.5f, y + 1.5f, 1.0f, false);
                font2.drawClientColoredString("\u043a\u0430\u043a \u0432\u0441\u0435\u0433\u0434\u0430 \u043d\u0430 \u0432\u044b\u0441\u043e\u0442\u0435!", x + 5.0f, y + 11.0f, 1.0f, false);
                GlStateManager.enableDepth();
                GL11.glDepthMask(true);
            } else if (mode.equalsIgnoreCase("Bloom")) {
                CFontRenderer font5 = Fonts.comfortaaRegular_22;
                CFontRenderer font2 = Fonts.comfortaaRegular_12;
                String str = "VEGALINE";
                String str2 = "b0.95 : " + Minecraft.getDebugFPS() + "fps";
                float w = font5.getStringWidth("VEGALINE");
                float w2 = font2.getStringWidth(str2);
                curH = 21.5f;
                curW = w + 3.0f;
                float e = x + w / 2.0f > (float)(sr.getScaledWidth() / 2) ? w - w2 - 3.0f : 0.0f;
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                int col = ClientColors.getColor1((int)(x * 4.0f + w / 2.0f));
                int col2 = ClientColors.getColor2((int)(x * 4.0f + w / 2.0f));
                int color = ColorUtils.getOverallColorFrom(col, col2, 0.5f);
                BloomUtil.renderShadow(() -> {
                    font5.drawString("VEGALINE", x, y + 3.0f, -1);
                    font2.drawString(str2, x + e + 2.5f, y + 15.0f, -1);
                }, color, 5, 0, 2.8f, false);
                font5.drawString("VEGALINE", x, y + 3.0f, color);
                font2.drawString(str2, x + e + 2.5f, y + 15.0f, color);
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.enableDepth();
            } else if (mode.equalsIgnoreCase("Clock")) {
                Object sec;
                Object mins;
                Calendar celendar = Calendar.getInstance();
                Object hour = String.valueOf(celendar.getTime().getHours());
                if (Integer.parseInt((String)hour) < 10) {
                    hour = "0" + (String)hour;
                }
                if (Integer.parseInt((String)(mins = String.valueOf(celendar.getTime().getMinutes()))) < 10) {
                    mins = "0" + (String)mins;
                }
                if (Integer.parseInt((String)(sec = String.valueOf(celendar.getTime().getSeconds()))) < 10) {
                    sec = "0" + (String)sec;
                }
                String time1 = (String)hour + ":" + (String)mins;
                Object time2 = sec;
                String ampm = Integer.parseInt((String)hour) < 12 ? "AM" : "PM";
                CFontRenderer font6 = Fonts.time_30;
                CFontRenderer font2 = Fonts.time_17;
                CFontRenderer font3 = Fonts.time_14;
                float w = font6.getStringWidth(time1);
                float w2 = font2.getStringWidth((String)time2);
                if ((float)font3.getStringWidth(ampm) > w2) {
                    w2 = font3.getStringWidth(ampm);
                }
                int c = ClientColors.getColor1();
                font6.drawClientColoredString(time1, x + 1.0f, y + 7.0f, 1.0f, false);
                font2.drawClientColoredString((String)time2, x + w + 2.0f, y + 12.0f, 1.0f, false, (int)(w * 5.0f), true);
                font3.drawClientColoredString(ampm, x + w + 2.0f, y + 6.5f, 1.0f, false, (int)(w * 5.0f), true);
                curH = 17.5f;
                curW = w + w2 + 3.0f;
            } else if (mode.equalsIgnoreCase("Wonderful")) {
                float w = wmWidth;
                float h = wmHeight;
                float[] smoothTime = this.getSmoothTimeValues();
                float cRange = 11.0f;
                float cx = x + cRange;
                float cy = y + cRange;
                float apc = 0.15f;
                int bgCol1 = ColorUtils.getOverallColorFrom(ClientColors.getColor1(0, apc), ColorUtils.getColor(0, (int)(255.0f * apc)), 0.35f);
                int bgCol2 = ColorUtils.getOverallColorFrom(ClientColors.getColor2(0, apc), ColorUtils.getColor(0, (int)(255.0f * apc)), 0.35f);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x, y, x2, y2, cRange, 1.0f, bgCol1, bgCol2, bgCol2, bgCol1, false, true, true);
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glEnable(3042);
                GL11.glDisable(3008);
                GL11.glEnable(2832);
                GL11.glPointSize(2.0f);
                int radiansMax = 320;
                GL11.glBegin(0);
                for (int radian360 = 220; radian360 < radiansMax; radian360 += 2) {
                    float pcAlpha = 1.0f - (float)MathUtils.getDifferenceOf(270, radian360) / 40.0f;
                    RenderUtils.setupColor(-1, 225.0f * pcAlpha + 30.0f);
                    double calcX = -Math.sin(Math.toRadians(radian360)) * (double)(cRange + 2.0f);
                    double calcY = Math.cos(Math.toRadians(radian360)) * (double)(cRange + 2.0f);
                    GL11.glVertex2d((double)cx + calcX, (double)cy + calcY);
                }
                GL11.glEnd();
                GL11.glPointSize(1.0f);
                GlStateManager.resetColor();
                GL11.glEnable(3008);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
                this.drawClockPoints(cx, cy, 2.5f, ColorUtils.getColor(255, 195), cRange - 1.5f);
                this.drawClockArrow(cx, cy, 2.5f, ColorUtils.getColor(155, 190, 255), ColorUtils.getColor(155, 190, 255, 125), cRange - 4.0f, smoothTime[0] * 30.0f + 180.0f);
                this.drawClockArrow(cx, cy, 1.75f, ColorUtils.getColor(255, 255, 0), ColorUtils.getColor(255, 255, 0, 125), cRange - 3.0f, smoothTime[1] * 6.0f + 180.0f);
                this.drawClockArrow(cx, cy, 1.75f, ColorUtils.getColor(255, 10, 10), ColorUtils.getColor(255, 50, 50, 125), cRange - 2.0f, smoothTime[2] * 6.0f + 180.0f);
                RenderUtils.drawSmoothCircle(cx, cy, 1.0f, ColorUtils.getColor(185, 125));
                CFontRenderer font7 = Fonts.comfortaaBold_18;
                String str = "\u0412\u0435\u0433\u0443\u043b\u044f / \t" + Minecraft.getDebugFPS() + "fps";
                font7.drawStringWithShadow(str, x + cRange * 2.0f + 6.0f, y + 8.0f, -1);
                curH = 22.0f;
                curW = cRange * 2.0f + 11.0f + (float)font7.getStringWidth(str);
            } else if (mode.equalsIgnoreCase("Plate")) {
                CFontRenderer font8 = Fonts.smallestpixel_24;
                CFontRenderer font2 = Fonts.smallestpixel_16;
                String name = "\u0412\u0415\u0413\u0423\u041b\u042f " + Minecraft.getDebugFPS() + "FPS";
                String addTop = "\u041b\u0423\u0427\u0428\u0418\u0419 \u0418\u0417 \u041b\u0423\u0427\u0428\u0418\u0425";
                float w = font8.getStringWidth(name);
                float w2 = font2.getStringWidth(addTop);
                if (w2 > w) {
                    w = w2;
                }
                w += 5.0f;
                w2 += 5.0f;
                int cli1 = ClientColors.getColor1(0);
                int cli2 = ClientColors.getColor2(-324);
                int cli3 = ClientColors.getColor2(0);
                int cli4 = ClientColors.getColor1(972);
                RenderUtils.resetBlender();
                StencilUtil.initStencilToWrite();
                font8.drawString(name, x + 4.0f, y + 2.0f, -1);
                font2.drawString(addTop, x + 4.0f, y + 12.5f, -1);
                StencilUtil.readStencilBuffer(0);
                RenderUtils.drawVGradientRect(x, y + 2.0f, x + 2.0f, y2 - 2.0f, cli1, cli4);
                RenderUtils.drawAlphedSideways(x, y2 - 2.0f, x2 - 2.0f, y2, cli4, cli3);
                RenderUtils.drawLightContureRectSmooth(x, y + 2.0f, x2 - 2.0f, y2, ColorUtils.getColor(0, 0, 0, 90));
                cli1 = ColorUtils.getOverallColorFrom(cli1, -1, 0.7f);
                cli2 = ColorUtils.getOverallColorFrom(cli2, -1, 0.7f);
                cli3 = ColorUtils.getOverallColorFrom(cli3, -1, 0.8f);
                cli4 = ColorUtils.getOverallColorFrom(cli4, -1, 0.8f);
                RenderUtils.drawFullGradientRectPro(x + 2.0f, y, x2, y2 - 2.0f, cli2, cli1, cli3, cli4, false);
                RenderUtils.drawLightContureRectSmooth(x + 2.0f, y, x2, y2 - 2.0f, ColorUtils.getColor(0, 0, 0, 90));
                StencilUtil.uninitStencilBuffer();
                curH = 19.5f;
                curW = w;
            }
            wmPosX = MathUtils.harp(wmPosX, curX, speedAnim);
            wmPosY = MathUtils.harp(wmPosY, curY, speedAnim);
            wmWidth = MathUtils.harp(wmWidth, curW, speedAnim);
            wmHeight = MathUtils.harp(wmHeight, curH, speedAnim);
        }
        if (this.Potions.bValue) {
            float w;
            this.updatePotionsList();
            float curX = this.PX.fValue * (float)sr.getScaledWidth();
            float curY = this.PY.fValue * (float)sr.getScaledHeight();
            this.potionsHeight.to = Hud.getPotionHudHeight();
            float x = potPosX;
            float y = potPosY;
            float wd = w = Hud.getPotionHudWidth();
            CFontRenderer fontName = Fonts.mntsb_16;
            CFontRenderer fontPot = Fonts.mntsb_12;
            int icoOffset = 9;
            int icoSize = 9;
            int durrCircleSize = 6;
            for (PotionWithString pot : this.potionsWithString) {
                float offX = (float)durrCircleSize * (0.5f + pot.alphaPC.getAnim() * 0.5f);
                if (this.onDoDrawPotionEffectIcon(true, x + 4.0f, 0.0f, icoSize, icoSize, pot.getPotion().getPotion(), true)) {
                    offX += (float)icoOffset * (0.5f + pot.alphaPC.getAnim() * 0.5f);
                }
                if ((float)fontPot.getStringWidth(pot.getName()) + offX < wd) continue;
                wd = (float)fontPot.getStringWidth(pot.getName()) + offX;
            }
            if (wd + 8.0f > w) {
                w = wd + 8.0f;
            }
            this.potionsHeight.speed = 0.2f;
            float h = this.potionsHeight.getAnim();
            int bgC = ColorUtils.getColor(7, 7, 7, 140);
            int gradC1 = ClientColors.getColor1();
            int gradC2 = ClientColors.getColor2(50);
            this.clientRect(x, y, x + w, y + h, "Potions");
            float yp = y + 25.0f - 10.0f;
            if (this.potionsWithString.size() == 0) {
                String lots = TextFormatting.DARK_GRAY + "Potions is empty";
                fontPot.drawStringWithShadow(lots, x + 3.0f, yp + 2.5f, -1);
            } else {
                GL11.glPushMatrix();
                for (PotionWithString pot : this.potionsWithString) {
                    if (pot == null) continue;
                    float alphaPC = MathUtils.clamp(pot.alphaPC.anim * 1.3333f, 0.0f, 1.0f);
                    int textCol = ColorUtils.getColor(255, (int)(255.0f * alphaPC));
                    float icoSize2 = (float)icoSize * alphaPC;
                    float durrCircleScale = (float)durrCircleSize * (0.5f + alphaPC * 0.5f);
                    float offX = 3.0f;
                    if (this.onDoDrawPotionEffectIcon(true, x + 4.0f + (float)icoSize / 4.0f, yp + 0.5f + (float)icoSize / 4.0f, icoSize2, (float)icoSize * 2.0f, pot.getPotion().getPotion(), false)) {
                        offX += (float)icoOffset * (0.5f + alphaPC * 0.5f);
                    }
                    String name = MathUtils.getStringPercent(pot.getName(), alphaPC * 1.1f);
                    float cx = x + w - 3.0f - (float)durrCircleSize / 2.0f;
                    float cy = yp + (durrCircleScale / 2.0f + 0.5f) * alphaPC;
                    RenderUtils.drawSmoothCircle(cx, cy, (float)durrCircleSize / 2.0f, ColorUtils.swapAlpha(0, 60.0f * alphaPC));
                    RenderUtils.drawClientCircleWithOverallToColor(cx, cy, ((float)durrCircleSize / 2.0f - 1.0f) * alphaPC, 360.0f * pot.getDurrationPC(), 1.0E-4f + 2.0f * alphaPC, alphaPC / 2.3f, ColorUtils.getOverallColorFrom(ColorUtils.getProgressColor(pot.getDurrationPC()).getRGB(), -1), 1.0f);
                    if (pot.getPotion().getPotion().isBadEffect()) {
                        RenderUtils.drawSmoothCircle(cx, cy, (float)durrCircleSize / 2.0f - 2.0f, ColorUtils.getColor(255, 60, 60, 255.0f * alphaPC));
                    }
                    if (255.0f * alphaPC >= 33.0f) {
                        RenderUtils.customScaledObject2DPro(x + offX + (float)fontPot.getStringWidth(name), yp, fontPot.getStringWidth(name), 6.0f, 1.0f, alphaPC);
                        fontPot.drawStringWithShadow(name, x + offX, yp + 2.5f, textCol);
                        RenderUtils.customScaledObject2DPro(x + offX + (float)fontPot.getStringWidth(name), yp, fontPot.getStringWidth(name), 6.0f, 1.0f, 1.0f / alphaPC);
                    }
                    yp += 9.0f * alphaPC;
                }
                GL11.glPopMatrix();
            }
            GlStateManager.enableDepth();
            GL11.glDepthMask(true);
            float speedAnim = (float)Minecraft.frameTime * 0.0175f;
            potPosX = MathUtils.harp(potPosX, curX, speedAnim);
            potPosY = MathUtils.harp(potPosY, curY, speedAnim);
            potWidth = MathUtils.harp(potWidth, w, speedAnim);
            potHeight = MathUtils.harp(potHeight, h, speedAnim);
        }
        if (this.ArmorHUD.bValue && Minecraft.player != null) {
            float curX = this.AX.fValue * (float)sr.getScaledWidth();
            float curY = this.AY.fValue * (float)sr.getScaledHeight();
            int itemScalePix = 16;
            CopyOnWriteArrayList<ItemStack> stacks = new CopyOnWriteArrayList<ItemStack>();
            boolean isVertical = MathUtils.getDifferenceOf(curX, 0.0f) < (double)itemScalePix && curY > 12.0f;
            for (int i = 0; i < 4; ++i) {
                stacks.add(Minecraft.player.inventory.armorItemInSlot(isVertical ? 3 - i : i));
            }
            if (MathUtils.getDifferenceOf(curX + 2.0f, (float)sr.getScaledWidth()) < (double)(stacks.size() * itemScalePix) && curY > 12.0f) {
                isVertical = true;
                Collections.reverse(stacks);
            }
            float xPos = armPosX;
            float yPos = armPosY;
            GlStateManager.disableDepth();
            float indexColor = 0.5f;
            if (!(isVertical || !(yPos > 6.0f) || yPos > (float)(sr.getScaledHeight() - 58) && MathUtils.getDifferenceOf(curX, (float)sr.getScaledWidth() / 2.0f) < 94.0)) {
                float texW = Fonts.minecraftia_16.getStringWidth("Armor");
                GlStateManager.pushMatrix();
                GL11.glScaled(0.5, 0.5, 0.5);
                GL11.glTranslated((double)xPos * 2.0 + (double)itemScalePix * 4.0, (double)yPos * 2.0, 0.0);
                int bg = ColorUtils.getColor(0, 80);
                int c1 = ClientColors.getColor1(0, 0.6f);
                int c2 = ClientColors.getColor2(0, 0.6f);
                int c3 = ClientColors.getColor1(0, 0.1f);
                int c4 = ClientColors.getColor2(0, 0.1f);
                int col1 = ColorUtils.getOverallColorFrom(c1, c2, indexColor - 0.275f);
                int col2 = ColorUtils.getOverallColorFrom(c1, c2, indexColor + 0.275f);
                int col3 = ColorUtils.getOverallColorFrom(c3, c4, indexColor + 0.275f);
                int col4 = ColorUtils.getOverallColorFrom(c3, c4, indexColor - 0.275f);
                List<Vec2fColored> vertexes = Arrays.asList(new Vec2fColored(-texW / 2.0f - 4.0f, -13.0f, col1), new Vec2fColored(texW / 2.0f + 4.0f, -13.0f, col2), new Vec2fColored(texW / 2.0f + 10.0f, 0.0f, col3), new Vec2fColored(-texW / 2.0f - 10.0f, 0.0f, col4));
                RenderUtils.drawVec2Colored(vertexes);
                vertexes = Arrays.asList(new Vec2fColored(-texW / 2.0f - 3.0f, -12.0f, bg), new Vec2fColored(texW / 2.0f + 3.0f, -12.0f, bg), new Vec2fColored(texW / 2.0f + 9.0f, 0.0f, bg), new Vec2fColored(-texW / 2.0f - 9.0f, 0.0f, bg));
                RenderUtils.drawVec2Colored(vertexes);
                Fonts.minecraftia_16.drawClientColoredString("Armor", -texW / 2.0f, -10.0, 1.0f, true, (int)(armWidth / 2.0f - texW / 2.0f));
                GlStateManager.popMatrix();
            }
            GlStateManager.pushMatrix();
            RenderItem itemRender = mc.getRenderItem();
            float zlevel = itemRender.zLevel;
            RenderUtils.enableGUIStandardItemLighting();
            GlStateManager.enableDepth();
            if (stacks.size() != 0 && isVertical) {
                yPos -= (float)itemScalePix;
            } else if (stacks.size() != 0) {
                xPos -= (float)itemScalePix;
            }
            int indexTex = isVertical ? 4 : 9;
            int index = 0;
            for (ItemStack stack : stacks) {
                String iconName;
                indexTex += isVertical ? 1 : -1;
                indexColor = ((float)index + 0.5f) / (float)stacks.size();
                ++index;
                if (stack == null) continue;
                if (isVertical) {
                    yPos += (float)itemScalePix;
                } else {
                    xPos += (float)itemScalePix;
                }
                String count = stack.stackSize > 1 ? "" + stack.stackSize : "";
                itemRender.zLevel = 200.0f;
                GlStateManager.translate(xPos, yPos, 0.0f);
                RenderUtils.disableStandardItemLighting();
                int c1 = ClientColors.getColor1(0);
                int c2 = ClientColors.getColor2(0);
                int c3 = ClientColors.getColor1(0, 0.4f);
                int c4 = ClientColors.getColor2(0, 0.4f);
                int col1 = ColorUtils.getOverallColorFrom(c1, c2, indexColor);
                int col2 = ColorUtils.getOverallColorFrom(c3, c4, indexColor);
                RenderUtils.drawLightContureRectSmooth(1.0, 1.0, 15.0, 15.0, col1);
                RenderUtils.drawLightContureRectSmooth(0.5, 0.5, 15.5, 15.5, ColorUtils.getColor(0, 140));
                RenderUtils.drawLightContureRectSmooth(1.5, 1.5, 14.5, 14.5, ColorUtils.getColor(0, 140));
                RenderUtils.drawAlphedRect(1.5, 1.5, 14.5, 14.5, ColorUtils.toDark(col2, 0.6f));
                if (stack.getItem() != Items.air) {
                    RenderUtils.enableStandardItemLighting();
                    itemRender.renderItemAndEffectIntoGUI(stack, 0, 0);
                    itemRender.renderItemOverlayIntoGUI(Hud.mc.fontRendererObj, stack, 0, 0, "");
                    RenderUtils.disableStandardItemLighting();
                    GlStateManager.scale(0.5, 0.5, 0.5);
                    if (stack != null && stack.isItemDamaged()) {
                        float armDamage = ((float)stack.getMaxDamage() - (float)stack.getItemDamage()) / (float)stack.getMaxDamage();
                        String armInfo = (int)(armDamage * 100.0f) + "%";
                        int colorStatus = ColorUtils.getProgressColor(armDamage * (0.75f + armDamage / 4.0f)).getRGB();
                        if (armDamage * 100.0f <= 5.0f) {
                            colorStatus = ColorUtils.fadeColor(ColorUtils.getColor(255, 20, 20), -1, 2.0f);
                        }
                        GlStateManager.disableDepth();
                        Fonts.minecraftia_16.drawStringWithOutline(armInfo, 16.0f - (float)(Fonts.minecraftia_16.getStringWidth(armInfo) / 2), 12.0, colorStatus);
                        GlStateManager.enableDepth();
                    }
                    if (stack.stackSize > 1) {
                        GlStateManager.disableDepth();
                        Fonts.minecraftia_16.drawStringWithShadow(count, 18.0, 18.0, -1);
                        GlStateManager.enableDepth();
                    }
                    int reserveArmCounter = 0;
                    if (stack.getItem() != Items.air) {
                        for (int i = 0; i < 45; ++i) {
                            ItemStack checkStack = Minecraft.player.inventory.getStackInSlot(i);
                            if (checkStack == null || checkStack == stack || checkStack.getItem() != stack.getItem()) continue;
                            reserveArmCounter += checkStack.stackSize;
                        }
                    }
                    if (reserveArmCounter != 0) {
                        int speedMS = reserveArmCounter > 1 ? 1500 : 500;
                        float colTimePC = ((float)System.currentTimeMillis() + indexColor * armWidth) % (float)speedMS / (float)speedMS;
                        int countC = ColorUtils.getOverallColorFrom(col1, -1, ((double)colTimePC > 0.5 ? 1.0f - colTimePC : colTimePC) * 2.0f);
                        GlStateManager.disableDepth();
                        RenderUtils.drawSmoothCircle(5.5, 5.5, 3.25f, countC);
                        RenderUtils.drawSmoothCircle(5.5, 5.5, 2.75f, ColorUtils.getColor(0, 190));
                        Fonts.minecraftia_16.drawStringWithShadow("" + reserveArmCounter, 6.0f - (float)Fonts.minecraftia_16.getStringWidth("" + reserveArmCounter) / 2.0f, 2.0, countC);
                        GlStateManager.enableDepth();
                    }
                    GlStateManager.scale(2.0f, 2.0f, 2.0f);
                }
                if (stack.func_190926_b() && (iconName = Minecraft.player.inventoryContainer.inventorySlots.get(indexTex).getSlotTexture()) != null) {
                    mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    GL11.glBlendFunc(770, 1);
                    GL11.glShadeModel(7425);
                    Tessellator tessellator = Tessellator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuffer();
                    TextureAtlasSprite textureSprite = mc.getTextureMapBlocks().getAtlasSprite(iconName);
                    bufferbuilder.begin(9, DefaultVertexFormats.POSITION_TEX_COLOR);
                    bufferbuilder.pos(4.0, 12.0).tex(textureSprite.getMinU(), textureSprite.getMaxV()).color(col1).endVertex();
                    bufferbuilder.pos(12.0, 12.0).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).color(col1).endVertex();
                    bufferbuilder.pos(12.0, 4.0).tex(textureSprite.getMaxU(), textureSprite.getMinV()).color(col2).endVertex();
                    bufferbuilder.pos(4.0, 4.0).tex(textureSprite.getMinU(), textureSprite.getMinV()).color(col2).endVertex();
                    tessellator.draw(6);
                    GL11.glShadeModel(7424);
                    GL11.glBlendFunc(770, 771);
                }
                RenderUtils.drawItemWarnIfLowDur(stack, 0.0f, 0.0f, 1.0f, 1.0f);
                GlStateManager.translate(-xPos, -yPos, 0.0f);
            }
            itemRender.zLevel = zlevel;
            if (stacks.size() != 0 && isVertical) {
                yPos -= (float)(itemScalePix * stacks.size());
            } else if (stacks.size() != 0) {
                xPos -= (float)(itemScalePix * stacks.size());
            }
            GlStateManager.enableAlpha();
            RenderUtils.resetBlender();
            GL11.glEnable(3042);
            GlStateManager.popMatrix();
            float curW = itemScalePix * (!isVertical ? stacks.size() : 1);
            float curH = itemScalePix * (isVertical ? stacks.size() : 1);
            stacks.clear();
            float speedAnim = (float)Minecraft.frameTime * 0.0175f;
            armPosX = MathUtils.harp(armPosX, curX, speedAnim);
            armPosY = MathUtils.harp(armPosY, curY, speedAnim);
            armWidth = MathUtils.harp(armWidth, curW, speedAnim);
            armHeight = MathUtils.harp(armHeight, curH, speedAnim);
        }
        if (this.StaffList.bValue) {
            float w;
            float curX = this.SX.fValue * (float)sr.getScaledWidth();
            float curY = this.SY.fValue * (float)sr.getScaledHeight();
            this.staffsHeight.to = this.getStaffHudHeight();
            float x = stPosX;
            float y = stPosY;
            float wd = w = this.getStaffHudWidth();
            CFontRenderer fontStaff = Fonts.mntsb_12;
            for (StaffPlayer staff : this.getStaffList()) {
                String sName = this.staffDisplay(staff);
                if ((float)fontStaff.getStringWidth(sName = sName.replace("  ", " ").replace("\u00a7l", "").replace("[]", "").replace("\u00a7k", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "")) < wd) continue;
                wd = fontStaff.getStringWidth(sName);
            }
            if (wd + 8.0f > w) {
                w = wd + 8.0f;
            }
            this.staffsHeight.speed = 0.4f;
            float h = this.staffsHeight.getAnim();
            this.clientRect(x, y, x + w, y + h, "Staff list");
            float yp = y + 25.0f - 10.0f;
            GlStateManager.disableDepth();
            if (this.getStaffList().size() == 0) {
                String lots = TextFormatting.DARK_GRAY + "Staffs is empty";
                fontStaff.drawStringWithShadow(lots, x + 3.0f, yp + 2.5f, -1);
            } else {
                GL11.glPushMatrix();
                for (StaffPlayer staff : this.getStaffList()) {
                    String sName = this.staffDisplay(staff);
                    sName = sName.replace("  ", " ").replace("\u00a7l", "").replace("[]", "").replace("\u00a7k", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "");
                    float ext = 1.5f * (1.0f - staff.alphaPC.getAnim()) + 0.5f;
                    int ALP = (int)(255.0f * staff.alphaPC.getAnim());
                    if (staff.toRemove) {
                        ALP /= 2;
                    }
                    if (ALP >= 33) {
                        RenderUtils.customScaledObject2DPro(x, yp, 0.0f, 9.0f, 1.0f, staff.alphaPC.getAnim());
                        fontStaff.drawStringWithShadow(MathUtils.getStringPercent(sName, staff.alphaPC.getAnim() * 1.1f), x + 4.0f, yp + 3.0f - ext, ColorUtils.getColor(255, 255, 255, ALP));
                        RenderUtils.customScaledObject2DPro(x, yp, 0.0f, 9.0f, 1.0f, 1.0f / staff.alphaPC.getAnim());
                    }
                    yp += 9.0f * staff.alphaPC.getAnim();
                }
                GL11.glPopMatrix();
            }
            GlStateManager.enableDepth();
            GL11.glDepthMask(true);
            float speedAnim = (float)Minecraft.frameTime * 0.0175f;
            stPosX = MathUtils.harp(stPosX, curX, speedAnim);
            stPosY = MathUtils.harp(stPosY, curY, speedAnim);
            stWidth = MathUtils.harp(stWidth, w, speedAnim);
            stHeight = MathUtils.harp(stHeight, h, speedAnim);
        }
        if (this.KeyBinds.bValue) {
            float w;
            if (System.currentTimeMillis() % 250L <= 50L) {
                this.setupBindsList();
            }
            float curX = this.KX.fValue * (float)sr.getScaledWidth();
            float curY = this.KY.fValue * (float)sr.getScaledHeight();
            this.keybindsHeight.to = this.getKeyBindsHudHeight();
            float x = kbPosX;
            float y = kbPosY;
            float wd = w = this.getKeyBindsHudWidth();
            CFontRenderer fontName = Fonts.mntsb_16;
            CFontRenderer fontKB = Fonts.mntsb_12;
            for (Module mod : this.bindsList) {
                if ((float)fontKB.getStringWidth(this.getKeyBingModName(mod)) < wd) continue;
                wd = fontKB.getStringWidth(this.getKeyBingModName(mod));
            }
            if (wd + 8.0f > w) {
                w = wd + 8.0f;
            }
            this.keybindsHeight.speed = 0.35f;
            float h = this.keybindsHeight.getAnim();
            int bgC = ColorUtils.getColor(7, 7, 7, 140);
            int gradC1 = ClientColors.getColor1();
            int gradC2 = ClientColors.getColor2(50);
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            this.clientRect(x, y, x + w, y + h, "Keybinds");
            float yp = y + 25.0f - 10.0f;
            if (this.bindsList.size() == 0 || this.bindsList.size() == 1 && this.bindsList.get((int)0).stateAnim.getAnim() < 0.15f) {
                String lots = TextFormatting.DARK_GRAY + "Binds not enabled";
                fontKB.drawStringWithShadow(lots, x + 3.0f, yp + 2.5f, -1);
            } else {
                int i = 0;
                int rd = 200;
                for (Module mod : this.bindsList) {
                    float addSO = MathUtils.clamp(mod.stateAnim.getAnim() * 1.05f, 0.0f, 1.0f);
                    ++i;
                    if ((float)rd * addSO >= 33.0f) {
                        float ext = 4.5f * (1.0f - mod.stateAnim.anim);
                        if ((float)rd * addSO >= 33.0f) {
                            fontKB.drawStringWithShadow(MathUtils.getStringPercent(mod.getName(), addSO), x + 4.0f, yp + 3.0f - ext, ColorUtils.getColor(255, 255, 255, (float)rd * addSO));
                        }
                        RenderUtils.customScaledObject2D(x + w - 4.0f - (float)fontKB.getStringWidth("{" + Keyboard.getKeyName(mod.getBind()) + "}"), yp - ext, fontKB.getStringWidth("{" + Keyboard.getKeyName(mod.getBind()) + "}"), 8.0f, addSO);
                        if ((float)rd * addSO >= 33.0f) {
                            fontKB.drawStringWithShadow("{" + Keyboard.getKeyName(mod.getBind()) + "}", x + w - 4.0f - (float)fontKB.getStringWidth("{" + Keyboard.getKeyName(mod.getBind()) + "}"), yp + 2.5f - ext, ColorUtils.getColor(255, 255, 255, (float)rd * addSO));
                        }
                        RenderUtils.customScaledObject2D(x + w - 4.0f - (float)fontKB.getStringWidth("{" + Keyboard.getKeyName(mod.getBind()) + "}"), yp - ext, fontKB.getStringWidth("{" + Keyboard.getKeyName(mod.getBind()) + "}"), 8.0f, 1.0f / addSO);
                    }
                    yp += 9.0f * addSO;
                }
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glPopMatrix();
            float speedAnim = (float)Minecraft.frameTime * 0.0175f;
            kbPosX = MathUtils.harp(kbPosX, curX, speedAnim);
            kbPosY = MathUtils.harp(kbPosY, curY, speedAnim);
            kbWidth = MathUtils.harp(kbWidth, w, speedAnim);
            kbHeight = MathUtils.harp(kbHeight, h, speedAnim);
        }
        if (this.PickupsList.bValue) {
            float w;
            float curX = this.PCX.fValue * (float)sr.getScaledWidth();
            float curY = this.PCY.fValue * (float)sr.getScaledHeight();
            this.pickupsHeight.to = this.getPickupsHudHeight();
            float x = pcPosX;
            float y = pcPosY;
            float wd = w = this.getPickupsHudWidth();
            CFontRenderer fontKB = Fonts.mntsb_12;
            float h = this.pickupsHeight.getAnim();
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            this.clientRect(x, y, x + w, y + h, "Pickups list");
            float yp = y + 25.0f - 10.0f;
            if (this.notifysList.size() == 0 || this.notifysList.size() == 1 && this.notifysList.get((int)0).alphaPC.getAnim() < 0.15f) {
                String lots = TextFormatting.DARK_GRAY + "Picks is empty";
                fontKB.drawStringWithShadow(lots, x + 3.0f, yp + 2.5f, -1);
            } else {
                float textureSizeFinal = 8.0f;
                int counter = 0;
                float prevX = x;
                RenderItem itemRender = mc.getRenderItem();
                y = yp;
                for (PickupItem pick : this.notifysList) {
                    if (y <= (float)sr.getScaledHeight() && x <= (float)sr.getScaledWidth()) {
                        this.drawPickStack(pick.stack, pick.alphaPC.getAnim(), x + 1.0f, y, itemRender);
                    }
                    if (counter % 10 == 0) {
                        yp += textureSizeFinal;
                    }
                    if (counter % 10 != 9) {
                        x += textureSizeFinal * (pick.alphaPC.to == 1.0f ? 1.0f : pick.alphaPC.getAnim());
                    } else {
                        y += 8.0f * pick.alphaPC.getAnim();
                        x = prevX;
                    }
                    ++counter;
                }
                yp += textureSizeFinal * 2.0f;
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glPopMatrix();
            float speedAnim = (float)Minecraft.frameTime * 0.0175f;
            pcPosX = MathUtils.harp(pcPosX, curX, speedAnim);
            pcPosY = MathUtils.harp(pcPosY, curY, speedAnim);
            pcWidth = MathUtils.harp(pcWidth, w, speedAnim);
            pcHeight = MathUtils.harp(pcHeight, h, speedAnim);
        }
        if (this.LagDetect.bValue && this.laggAlphaPC.to == 0.0f && this.laggCheck.hasReached(1000.0) && !mc.isGamePaused()) {
            this.laggAlphaPC.to = 1.0f;
        }
        if (this.LagDetect.bValue && this.laggAlphaPC.getAnim() * 255.0f > 31.0f) {
            String str = (mc.isSingleplayer() ? "Loading client" : "Server lagged") + " " + (int)(this.laggCheck.getTime() / 1000L) + "s";
            CFontRenderer font9 = Fonts.mntsb_36;
            float w = font9.getStringWidth(str);
            float h = font9.getStringHeight(str);
            float x = (float)(sr.getScaledWidth() / 2) - w / 2.0f;
            float y = sr.getScaledHeight() / 8;
            int c = ColorUtils.swapAlpha(ColorUtils.getFixedWhiteColor(), 255.0f * this.laggAlphaPC.getAnim() * this.laggAlphaPC.getAnim());
            float APC = (float)ColorUtils.getAlphaFromColor(c) / 255.0f;
            if (APC * 255.0f > 31.0f) {
                GL11.glPushMatrix();
                GL11.glDepthMask(false);
                RenderUtils.customScaledObject2DPro(x, y, w, h, 1.2f - 0.2f * APC, APC);
                font9.drawString(str, x, y, c);
                RenderUtils.customScaledObject2DPro(x, y, w, h, 1.0f / (1.2f - 0.2f * APC), 1.0f / APC);
                RenderUtils.customScaledObject2DPro(x, y, w, h, 1.1f - 0.1f * APC, APC);
                BloomUtil.renderShadow(() -> font9.drawString(str, x, y, c), c, 4 + (int)(6.0f * APC), 0, 1.75f * APC * APC, false);
                GL11.glDepthMask(true);
                GL11.glPopMatrix();
            }
        }
    }

    private void drawAList(float x, float y, boolean silent, float alphaPC, ScaledResolution sr) {
        boolean resersedX = this.isReverseX(sr);
        float arrayWidth = this.getArrayWidth(sr);
        int index = 0;
        for (Module mod : enabledModules) {
            this.drawModule(x, y, mod, this.getArrayStep(), index, silent, alphaPC, sr, resersedX, arrayWidth);
            y += this.getArrayStep() * mod.stateAnim.getAnim();
            ++index;
        }
    }

    public boolean isArraylist() {
        return get != null && this.ArrayList.bValue && enabledModules != null && enabledModules.size() != 0;
    }

    public boolean isHoverToArrayList(int mouseX, int mouseY, ScaledResolution sr) {
        float x1 = listPosX - this.getArrayWidth(sr);
        float x2 = listPosX;
        float y1 = listPosY;
        float y2 = listPosY + this.getArrayHeight();
        return this.isArraylist() && RenderUtils.isHovered(mouseX, mouseY, x1, y1, x2 - x1, y2 - y1);
    }

    private void drawModule(float x, float y, Module mod, float shag, int index, boolean silent, float alphaPC, ScaledResolution sr, boolean reversedX, float arrayWidth) {
        float gd = MathUtils.clamp(Math.abs((float)index / (float)enabledModules.size()), 0.0f, 1.0f);
        float extX = 2.0f;
        String modName = Hud.getModName(mod, this.isTitles());
        float w = (float)Hud.font().getStringWidth(modName) + extX * 2.0f;
        int colTex = ColorUtils.getOverallColorFrom(ClientColors.getColor1((int)((float)index * shag), alphaPC *= mod.stateAnim.getAnim() / 2.0f + 0.5f), ClientColors.getColor2((int)((float)index * shag), alphaPC), gd);
        int col1 = ColorUtils.getOverallColorFrom(colTex, ColorUtils.getColor(0, 0, 0, 100.0f * alphaPC), 0.4f);
        int col2 = ColorUtils.getOverallColorFrom(colTex, ColorUtils.getColor(0, 0, 0, 100.0f * alphaPC), 0.7f);
        int c1 = ColorUtils.swapAlpha(col1, 70.0f * alphaPC);
        int c2 = ColorUtils.swapAlpha(col2, 155.0f * alphaPC);
        x -= reversedX ? arrayWidth : w + 1.0f;
        float ext = shag * mod.stateAnim.getAnim();
        if (silent) {
            RenderUtils.drawRect(x, y, x + w, y + ext, -1);
        } else {
            RenderUtils.drawAlphedSideways(x, y, x + w, y + ext, reversedX ? c2 : c1, reversedX ? c1 : c2);
            RenderUtils.drawAlphedRect(x + (reversedX ? -0.5f : w), y, x + (reversedX ? -0.5f : w) + 1.0f, y + ext, colTex);
        }
        if (!silent && 255.0f * alphaPC > 26.0f) {
            float scaleText = MathUtils.clamp(mod.stateAnim.getAnim() * 1.005f, 0.1f, 1.0f);
            if (scaleText == 0.1f) {
                return;
            }
            RenderUtils.customScaledObject2D(x, y, w, shag, scaleText);
            if (255.0f * alphaPC >= 33.0f) {
                Hud.font().drawStringWithShadow(modName, x + extX, y + shag / 2.0f - 2.0f - (1.0f - mod.stateAnim.getAnim()) * shag / 3.0f, ColorUtils.swapAlpha(colTex, MathUtils.clamp(255.0f * alphaPC, 33.0f, 255.0f)));
            }
            RenderUtils.customScaledObject2D(x, y, w, shag, 1.0f / scaleText);
        }
    }

    @Override
    public void onUpdate() {
        if (this.PickupsList.bValue) {
            this.picksRemoveAuto();
        }
        if (this.StaffList.bValue && Minecraft.player != null) {
            this.getStaffList().forEach(StaffPlayer::update);
            this.updateStaffsGetedList(this.updatedStaffsToGetList());
        }
    }

    boolean isIsNotNulledString(String stringIn) {
        return stringIn != null && !stringIn.isEmpty();
    }

    boolean lowerContains(String first, String second) {
        return first.toLowerCase().contains(second.toLowerCase());
    }

    boolean stringOverlapHasResult(List<String> strings, String string, boolean checkToLower) {
        String opString = string = this.isIsNotNulledString(string) && checkToLower ? string.toLowerCase() : string;
        return strings.size() != 0 && (strings.size() == 1 ? this.isIsNotNulledString(strings.get(0)) && this.lowerContains(strings.get(0), opString) : strings.stream().filter(inList -> this.isIsNotNulledString((String)inList) && this.lowerContains((String)inList, opString)).collect(Collectors.toList()).size() != 0);
    }

    private String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }

    private boolean hasPrefix(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getPlayerTeam() != null && !networkPlayerInfoIn.getPlayerTeam().getColorPrefix().isEmpty();
    }

    private boolean hasPrefix(ScorePlayerTeam playerTeam) {
        return playerTeam.getColorPrefix() != null && !playerTeam.getColorPrefix().isEmpty();
    }

    String getDecoloredPrefix(String prefix) {
        return prefix.replace("\u00a70", "").replace("\u00a71", "").replace("\u00a72", "").replace("\u00a73", "").replace("\u00a74", "").replace("\u00a75", "").replace("\u00a76", "").replace("\u00a77", "").replace("\u00a78", "").replace("\u00a79", "").replace("\u00a7a", "").replace("\u00a7b", "").replace("\u00a7c", "").replace("\u00a7d", "").replace("\u00a7e", "").replace("\u00a7f", "").replace("\u00a7k", "").replace("\u00a7l", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "").replace("\u00a7r", "");
    }

    boolean isStaffInPlayerInfo(NetworkPlayerInfo info, List<String> staffEqualsPrefix, List<String> staffEqualsName) {
        if (info == null) {
            return false;
        }
        if (info.getPlayerTeam() == null || info.getPlayerTeam().getTeamName().length() < 2) {
            return false;
        }
        if (info.getDisplayName() == null || !this.isIsNotNulledString(info.getDisplayName().getUnformattedText())) {
            return false;
        }
        String name = this.getPlayerName(info).toLowerCase();
        String prefix = this.getDecoloredPrefix(info.getPlayerTeam().getColorPrefix()).toLowerCase();
        if (!this.isIsNotNulledString(prefix)) {
            return false;
        }
        boolean prefixHandle = staffEqualsPrefix.stream().anyMatch(pref -> prefix.contains(pref.toLowerCase()));
        boolean nameHandle = staffEqualsName.stream().anyMatch(named -> name.contains(named.toLowerCase()));
        return name != null && (this.hasPrefix(info) && prefixHandle || nameHandle);
    }

    boolean isStaffInScoreboard(ScorePlayerTeam playerTeam, List<String> staffEqualsPrefix, List<String> staffEqualsName, List<String> onlinePlayersNames) {
        if (playerTeam == null || !this.isIsNotNulledString(playerTeam.getTeamName())) {
            return false;
        }
        String name = playerTeam.getRegisteredName();
        String prefix = this.getDecoloredPrefix(playerTeam.getColorPrefix()).toLowerCase();
        if (!this.isIsNotNulledString(prefix)) {
            return false;
        }
        boolean prefixHandle = staffEqualsPrefix.stream().anyMatch(pref -> prefix.contains(pref.toLowerCase()));
        boolean nameHandle = staffEqualsName.stream().anyMatch(named -> name.contains(named.toLowerCase()));
        return name != null && (this.hasPrefix(playerTeam) && prefixHandle || nameHandle);
    }

    String fixedString(String string) {
        return string.replace("  ", " ").replace("\u00a7l", "").replace("[]", "").replace("\u00a7k", "").replace("\u00a7m", "").replace("\u00a7n", "").replace("\u00a7o", "");
    }

    void addStaffList(ArrayList<StaffPlayer> staffsList, NetworkPlayerInfo infoNet) {
        if (infoNet == null || infoNet.getDisplayName() == null) {
            return;
        }
        staffsList.add(new StaffPlayer(this.fixedString(infoNet.getDisplayName().getUnformattedText()), this.fixedString(infoNet.getGameProfile().getName()), false, infoNet.getGameType() == null ? GameType.SURVIVAL : infoNet.getGameType()));
    }

    void addStaffList(ArrayList<StaffPlayer> staffsList, ScorePlayerTeam playerTeam) {
        if (playerTeam == null || !this.isIsNotNulledString(playerTeam.getColorPrefix())) {
            return;
        }
        String name = this.fixedString(Arrays.asList(playerTeam.getMembershipCollection().stream().toArray()).toString());
        staffsList.add(new StaffPlayer(this.fixedString(playerTeam.getColorPrefix() + name + playerTeam.getColorSuffix()), name, true, GameType.SURVIVAL));
    }

    void addStaffList(StaffPlayer staff) {
        this.getStaffList().add(staff);
    }

    List<String> getStaffEqualPrefixes() {
        return Arrays.asList("helper", "moder", "admin", "yt", "\u043c\u043e\u0434\u0435\u0440", "\u0430\u0434\u043c\u0438\u043d", "\u0445\u0435\u043b\u043f\u0435\u0440", "\u0441\u0442\u0430\u0436\u0451", "\u0432\u043b\u0430\u0441\u0442\u0435\u043b\u0438\u043d");
    }

    List<String> getStaffWarnEqualPrefixes() {
        return Arrays.asList("st.", "moder", "admin", "yt");
    }

    List<String> getStaffNameEqualsList() {
        return Arrays.asList("MrZenyYT", "soniloon", "Blood_yoshkin", "MrDomer", "local_hunk");
    }

    ArrayList<StaffPlayer> updatedStaffsToGetList() {
        ArrayList<StaffPlayer> staffsSpawned = new ArrayList<StaffPlayer>();
        Collection<ScorePlayerTeam> teamsPlayers = Hud.mc.world.getScoreboard().getTeams();
        List<String> staffEqualPrefixes = this.getStaffEqualPrefixes();
        List<String> nameEqualsList = this.getStaffNameEqualsList();
        List onlinePlayersNames = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator()).sortedCopy(Minecraft.player.connection.getPlayerInfoMap()).stream().map(NetworkPlayerInfo::getGameProfile).map(GameProfile::getName).filter(profileName -> this.validUserPattern.matcher((CharSequence)profileName).matches()).collect(Collectors.toList());
        List onlinePlayers = Minecraft.player.connection.getPlayerInfoMap().stream().filter(profileName -> this.validUserPattern.matcher(profileName.getGameProfile().getName()).matches()).collect(Collectors.toList());
        onlinePlayers.stream().filter(player -> this.isStaffInPlayerInfo((NetworkPlayerInfo)player, staffEqualPrefixes, nameEqualsList)).forEach(staff -> this.addStaffList(staffsSpawned, (NetworkPlayerInfo)staff));
        List notSpec = staffsSpawned.stream().filter(staff -> !staff.vanished).collect(Collectors.toList());
        teamsPlayers.stream().filter(team -> this.isStaffInScoreboard((ScorePlayerTeam)team, staffEqualPrefixes, nameEqualsList, onlinePlayersNames)).forEach(staff -> {
            if (!notSpec.stream().anyMatch(testStaff -> Arrays.asList(staff.getMembershipCollection().stream().toArray()).toString().replace("[", "").replace("]", "").equalsIgnoreCase(testStaff.name))) {
                this.addStaffList(staffsSpawned, (ScorePlayerTeam)staff);
            }
        });
        return staffsSpawned;
    }

    void updateStaffsGetedList(ArrayList<StaffPlayer> getedStaffs) {
        int maxListSise = 20;
        if (this.staffPlayers.size() < 20) {
            List staffPlayerNamesGeted = this.staffPlayers.stream().map(StaffPlayer::getName).collect(Collectors.toList());
            getedStaffs.stream().filter(staffToGet -> !this.stringOverlapHasResult(staffPlayerNamesGeted, staffToGet.name, false)).forEach(staffToGet -> this.addStaffList((StaffPlayer)staffToGet));
        }
        if (this.staffPlayers.size() == 0) {
            return;
        }
        this.staffPlayers.removeIf(staff -> staff.remove);
        List onlinePlayers = Minecraft.player.connection.getPlayerInfoMap().stream().filter(profileName -> this.validUserPattern.matcher(profileName.getGameProfile().getName()).matches()).collect(Collectors.toList());
        for (StaffPlayer staffGeted : this.staffPlayers) {
            getedStaffs.stream().filter(toGet -> staffGeted.name.equalsIgnoreCase(toGet.name)).forEach(toGet -> {
                staffGeted.vanished = toGet.vanished && !onlinePlayers.stream().anyMatch(player -> player.getGameProfile() != null && player.getGameProfile().getName().equalsIgnoreCase(staffGeted.getName()));
                staffGeted.gamemode = toGet.gamemode;
                GameType gmPass = onlinePlayers.stream().filter(player -> player.getGameProfile() != null && player.getGameProfile().getName().equalsIgnoreCase(staffGeted.getName())).map(player -> player.getGameType()).findAny().orElse(null);
                if (gmPass != null) {
                    staffGeted.gamemode = gmPass;
                }
            });
            staffGeted.toRemove = !this.stringOverlapHasResult(getedStaffs.stream().map(staff -> staff.name).collect(Collectors.toList()), staffGeted.name, false);
        }
    }

    private ArrayList<StaffPlayer> getStaffList() {
        return this.staffPlayers;
    }

    private String staffDisplay(StaffPlayer staff) {
        return staff.renderString();
    }

    private List<String> staffDisplays(List<StaffPlayer> staffs) {
        return staffs.stream().map(StaffPlayer::renderString).collect(Collectors.toList());
    }

    private void drawPickStack(ItemStack stack, float alphaPC, float x, float y, RenderItem renderer) {
        if ((double)alphaPC < 0.05) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glScaled(0.5, 0.5, 1.0);
        GL11.glTranslated(x * 2.0f, y * 2.0f, 1.0);
        GL11.glDepthMask(true);
        RenderUtils.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();
        if ((double)alphaPC > 0.95) {
            alphaPC = 1.0f;
        }
        if (alphaPC != 1.0f) {
            RenderUtils.customScaledObject2D(0.0f, 0.0f, 16.0f, 16.0f, alphaPC);
        }
        renderer.zLevel = 209.0f;
        renderer.renderItemAndEffectIntoGUI(stack, 0, 0);
        GL11.glDepthMask(false);
        GlStateManager.disableDepth();
        Fonts.minecraftia_16.drawString(stack.getCount(), 11.0f - (float)Fonts.minecraftia_16.getStringWidth(stack.getCount()) / 2.0f, 5.0, ColorUtils.getColor(0, 0, 0, 255.0f * alphaPC));
        Fonts.minecraftia_16.drawString(stack.getCount(), 9.0f - (float)Fonts.minecraftia_16.getStringWidth(stack.getCount()) / 2.0f, 5.0, ColorUtils.getColor(0, 0, 0, 255.0f * alphaPC));
        Fonts.minecraftia_16.drawString(stack.getCount(), 10.0f - (float)Fonts.minecraftia_16.getStringWidth(stack.getCount()) / 2.0f, 5.0, ColorUtils.getColor(0, 0, 0, 255.0f * alphaPC));
        Fonts.minecraftia_16.drawString(stack.getCount(), 10.0f - (float)Fonts.minecraftia_16.getStringWidth(stack.getCount()) / 2.0f, 7.0, ColorUtils.getColor(0, 0, 0, 255.0f * alphaPC));
        Fonts.minecraftia_16.drawString(stack.getCount(), 10.0f - (float)Fonts.minecraftia_16.getStringWidth(stack.getCount()) / 2.0f, 6.0, ColorUtils.getColor(255, 255, 255, 255.0f * alphaPC));
        renderer.zLevel = 200.0f;
        GL11.glDepthMask(true);
        GL11.glDisable(3008);
        RenderUtils.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GL11.glPopMatrix();
    }

    private float[] getSmoothTimeValues() {
        Calendar clock = Calendar.getInstance();
        Date date = clock.getTime();
        float sSex = (float)date.getSeconds() + (float)(System.currentTimeMillis() % 1000L) / 1000.0f;
        float sMins = (float)date.getMinutes() + sSex / 60.0f;
        float sHours = (float)date.getHours() + sMins / 60.0f;
        return new float[]{sHours, sMins, sSex};
    }

    private void drawClockArrow(float x, float y, float lineW, int color, int color2, float rangeAtMiddle, float radian360) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glShadeModel(7425);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glLineWidth(lineW);
        RenderUtils.glColor(color);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glBegin(3);
        GL11.glVertex2d(x, y);
        RenderUtils.glColor(color2);
        double calcX = -Math.sin(Math.toRadians(radian360)) * (double)rangeAtMiddle;
        double calcY = Math.cos(Math.toRadians(radian360)) * (double)rangeAtMiddle;
        GL11.glVertex2d((double)x + calcX, (double)y + calcY);
        GL11.glEnd();
        GL11.glHint(3154, 4352);
        GL11.glDisable(2848);
        GlStateManager.resetColor();
        GL11.glLineWidth(1.0f);
        GL11.glEnable(3008);
        GL11.glShadeModel(7424);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    private void drawClockPoints(float x, float y, float pointSize, int color, float rangeAtMiddle) {
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glEnable(2832);
        GL11.glPointSize(pointSize);
        GL11.glDisable(3553);
        int pointsCount = 12;
        int radiansMax = 360;
        RenderUtils.glColor(color);
        GL11.glBegin(0);
        for (int radian360 = 0; radian360 < radiansMax; radian360 += radiansMax / pointsCount) {
            double calcX = -Math.sin(Math.toRadians(radian360)) * (double)rangeAtMiddle;
            double calcY = Math.cos(Math.toRadians(radian360)) * (double)rangeAtMiddle;
            GL11.glVertex2d((double)x + calcX, (double)y + calcY);
        }
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glPointSize(1.0f);
        GlStateManager.resetColor();
        GL11.glEnable(3008);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }

    public boolean isPotsCustom() {
        return this.actived && this.Potions != null && this.Potions.bValue;
    }

    public boolean isCustomHotbar() {
        return this.actived && this.CustomHotbar != null && this.CustomHotbar.bValue;
    }

    public boolean isSleekHotbar() {
        return this.settings.get(10) != null && ((Settings)this.settings.get((int)10)).currentMode.equalsIgnoreCase("Sleek");
    }

    public boolean isArmorHud() {
        return this.actived && this.ArmorHUD != null && this.ArmorHUD.bValue;
    }

    public boolean isStaffListHud() {
        return this.actived && this.StaffList != null && this.StaffList.bValue;
    }

    public boolean isKeyBindsHud() {
        return this.actived && this.KeyBinds != null && this.KeyBinds.bValue;
    }

    public boolean isPickupHud() {
        return this.actived && this.PickupsList != null && this.PickupsList.bValue;
    }

    public boolean isHoveredToPotionsHUD(int mouseX, int mouseY) {
        return this.isPotsCustom() && RenderUtils.isHovered(mouseX, mouseY, potPosX, potPosY, potWidth, potHeight);
    }

    public boolean isHoveredToArmorHUD(int mouseX, int mouseY) {
        return this.isArmorHud() && RenderUtils.isHovered(mouseX, mouseY, armPosX, armPosY, armWidth, armHeight);
    }

    public boolean isHoveredStaffListHUD(int mouseX, int mouseY) {
        return this.isArmorHud() && RenderUtils.isHovered(mouseX, mouseY, stPosX, stPosY, stWidth, stHeight);
    }

    public boolean isHoveredKeyBindsHUD(int mouseX, int mouseY) {
        return this.isKeyBindsHud() && RenderUtils.isHovered(mouseX, mouseY, kbPosX, kbPosY, kbWidth, kbHeight);
    }

    public boolean isHoveredPickupsHUD(int mouseX, int mouseY) {
        return this.isPickupHud() && RenderUtils.isHovered(mouseX, mouseY, pcPosX, pcPosY, pcWidth, pcHeight);
    }

    private static float getPotionHudHeight() {
        float h = 16.0f;
        if (Minecraft.player != null && get != null && Hud.get.potionsWithString != null) {
            float height = 0.0f;
            for (PotionWithString potion : Hud.get.potionsWithString) {
                float aPC = MathUtils.clamp(potion.alphaPC.getAnim() * 1.1f, 0.0f, 1.0f);
                height += potion.alphaPC.to == 0.0f ? ((double)aPC > 0.3 ? 4.5f + 4.5f * aPC : 9.0f * aPC) : 6.0f + 3.0f * aPC;
            }
            height = height < 9.0f ? 9.0f : height;
            h += height;
        }
        return h;
    }

    private static float getPotionHudWidth() {
        return 75.0f;
    }

    private float getStaffHudHeight() {
        float h = 16.0f;
        float r = 0.0f;
        if (Minecraft.player != null) {
            if (this.getStaffList().size() == 0) {
                r += 9.0f;
            }
            for (StaffPlayer staff : this.getStaffList()) {
                r += 9.0f * staff.alphaPC.getAnim();
            }
        }
        r = r < 9.0f ? 9.0f : r;
        return h += r;
    }

    private float getStaffHudWidth() {
        return 75.0f;
    }

    String getKeyBingModName(Module mod) {
        return mod.getName() + " [" + Keyboard.getKeyName(mod.getBind()) + "]";
    }

    List<Module> keyBindsMods() {
        return Client.moduleManager.getModuleList().stream().filter(m -> (m.actived || m.stateAnim.getAnim() > 0.02f) && m.getBind() != 0).sorted(Comparator.comparingLong(e -> -e.lastEnableTime)).toList();
    }

    void setupBindsList() {
        this.bindsList = this.keyBindsMods();
        this.bindsList.forEach(module -> {
            if (module.stateAnim.anim < 0.02f && module.stateAnim.to == 0.0f) {
                module.stateAnim.setAnim(0.0f);
            }
            if (module.stateAnim.anim > 0.98f && module.stateAnim.to == 1.0f) {
                module.stateAnim.setAnim(1.0f);
            }
            module.stateAnim.to = module.actived ? 1.0f : 0.0f;
        });
    }

    private float getKeyBindsHudWidth() {
        float w = 75.0f;
        for (Module mod : this.bindsList) {
            if (!mod.actived && !(mod.stateAnim.getAnim() > 0.02f) || !((float)Fonts.mntsb_12.getStringWidth(this.getKeyBingModName(mod)) > w)) continue;
            w = Fonts.mntsb_12.getStringWidth(this.getKeyBingModName(mod));
        }
        return MathUtils.clamp(w, 75.0f, 175.0f);
    }

    private float getKeyBindsHudHeight() {
        float h = 16.0f;
        float h2 = 0.0f;
        if (this.bindsList.size() == 0) {
            h2 += 9.0f;
        }
        for (Module mod : this.bindsList) {
            h2 += 9.0f * mod.stateAnim.getAnim();
        }
        if (h2 < 9.0f) {
            h2 = 9.0f;
        }
        return h + h2;
    }

    private float getPickupsHudHeight() {
        float h = 16.0f;
        float h2 = 0.0f;
        int counter = 0;
        for (PickupItem pick : this.notifysList) {
            if (counter % 10 == 0) {
                h2 += 8.0f * (pick.alphaPC.to == 1.0f ? 1.0f : pick.alphaPC.getAnim());
            }
            ++counter;
        }
        if (h2 < 9.0f) {
            h2 = 9.0f;
        }
        return h + h2;
    }

    private float getPickupsHudWidth() {
        return 83.0f;
    }

    private int maxNotifyTime() {
        return 20000;
    }

    public void onCollect(EntityItem entityItem, EntityLivingBase whoPicked) {
        if (whoPicked != null && whoPicked instanceof EntityPlayerSP) {
            EntityPlayerSP player = (EntityPlayerSP)whoPicked;
            if (this.isPickupHud()) {
                ItemStack stack = entityItem.getItem();
                if (stack.func_190926_b()) {
                    return;
                }
                this.notifysList.add(0, new PickupItem(stack, this.maxNotifyTime()));
            }
        }
    }

    private void picksRemoveAuto() {
        this.notifysList.forEach(pickup -> {
            if (pickup.timePC() == 1.0f) {
                pickup.alphaPC.to = 0.0f;
                pickup.toRemove = (double)pickup.alphaPC.getAnim() < 0.01 && pickup.alphaPC.to == 0.0f;
            }
        });
        for (int i = 0; i < this.notifysList.size(); ++i) {
            if (this.notifysList.get(i) == null) {
                return;
            }
            PickupItem pickup2 = this.notifysList.get(i);
            if (pickup2 == null || !pickup2.toRemove) continue;
            this.notifysList.remove(i);
        }
    }

    private boolean onDoDrawPotionEffectIcon(boolean bindTex, float x, float y, float size, float extXY, Potion potion, boolean silent) {
        if (potion == null) {
            return false;
        }
        if (potion.hasStatusIcon()) {
            if (!silent) {
                if (bindTex) {
                    mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
                }
                int indexTex = potion.getStatusIconIndex();
                GL11.glPushMatrix();
                GlStateManager.disableLighting();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glTranslated(x, y, 0.0);
                GL11.glScaled(0.05555555555555555, 0.05555555555555555, 1.0);
                GL11.glTranslated((double)extXY / 2.0, (double)extXY / 2.0, 0.0);
                GL11.glScaled(size, size, 1.0);
                GL11.glTranslated((double)(-extXY) / 2.0, (double)(-extXY) / 2.0, 0.0);
                new Gui().drawTexturedModalRect(0, 0, indexTex % 8 * 18, 198 + indexTex / 8 * 18, 18, 18);
                GL11.glPopMatrix();
            }
            return true;
        }
        return false;
    }

    public void updatePotionsList() {
        Collection<PotionEffect> activeEffectsCollect = Minecraft.player.getActivePotionEffects();
        List activeEffectsList = Arrays.asList(activeEffectsCollect.toArray()).stream().map(obj -> (PotionEffect)obj).filter(Objects::nonNull).toList();
        for (PotionEffect effect2 : activeEffectsList) {
            boolean isAdded = this.potionsWithString.stream().anyMatch(added -> added.equals(effect2));
            if (this.potionsWithString.stream().anyMatch(added -> added.getPotion().equals(effect2))) continue;
            this.potionsWithString.add(new PotionWithString(effect2));
        }
        for (PotionWithString added2 : this.potionsWithString) {
            PotionEffect searched = activeEffectsList.stream().filter(effect -> added2 != null && effect.equals(added2.getPotion())).findFirst().orElse(null);
            boolean isSerched = searched != null;
            added2.setToRemove(!isSerched);
            if (!isSerched) continue;
            added2.updateDurration(searched);
        }
        this.potionsWithString.stream().forEach(PotionWithString::updateRemoveStatus);
        this.potionsWithString.removeIf(PotionWithString::isWantToRemove);
    }

    static {
        alphaPC = new AnimationUtils(0.0f, 0.0f, 0.05f);
        arrayListLastUpdateTime = new TimerHelper();
        wmPosX = 0.0f;
        wmPosY = 0.0f;
        wmWidth = 0.0f;
        wmHeight = 0.0f;
        potPosX = 100.0f;
        potPosY = 8.0f;
        potWidth = Hud.getPotionHudWidth();
        potHeight = Hud.getPotionHudHeight();
        armPosX = 5.0f;
        armPosY = 5.0f;
        armWidth = 16.0f;
        armHeight = 16.0f;
        stPosX = 5.0f;
        stPosY = 55.0f;
        stWidth = 16.0f;
        stHeight = 16.0f;
        kbPosX = 5.0f;
        kbPosY = 55.0f;
        kbWidth = 16.0f;
        kbHeight = 16.0f;
        pcPosX = 5.0f;
        pcPosY = 195.0f;
        pcWidth = 16.0f;
        pcHeight = 16.0f;
    }

    private class PotionWithString {
        AnimationUtils alphaPC = new AnimationUtils(0.0f, 1.0f, 0.04f);
        String name;
        PotionEffect potion;
        boolean toRemove;
        boolean remove;
        int maxTicksDurration;
        int durration;

        public PotionWithString(PotionEffect potion) {
            this.potion = potion;
            Object name = potion.getEffectName();
            if (((String)name).contains("luck")) {
                name = "\u00a7aLuck\u00a7r";
            }
            if (((String)name).contains("moveSpeed")) {
                name = "\u00a7bSpeed\u00a7r";
            }
            if (((String)name).contains("moveSlowdown")) {
                name = "\u00a77Slowness\u00a7r";
            }
            if (((String)name).contains("jump")) {
                name = "\u00a7aJumpBoost\u00a7r";
            }
            if (((String)name).contains("digSpeed")) {
                name = "\u00a76Haste\u00a7r ";
            }
            if (((String)name).contains("digSlowDown")) {
                name = "\u00a77SlowHand\u00a7r";
            }
            if (((String)name).contains("damageBoost")) {
                name = "\u00a74Strength\u00a7r";
            }
            if (((String)name).contains("heal")) {
                name = "\u00a7cInstantHeal\u00a7r";
            }
            if (((String)name).contains("harm")) {
                name = "\u00a74InstantDamage\u00a7r";
            }
            if (((String)name).contains("confusion")) {
                name = "\u00a77Nausea\u00a7r";
            }
            if (((String)name).contains("regeneration")) {
                name = "\u00a7dRegeneration\u00a7r";
            }
            if (((String)name).contains("resistance")) {
                name = "\u00a7eResistance\u00a7r";
            }
            if (((String)name).contains("absorption")) {
                name = "\u00a7eAbsorption\u00a7r";
            }
            if (((String)name).contains("fireResistance")) {
                name = "\u00a76FireResistance\u00a7r";
            }
            if (((String)name).contains("waterBreathing")) {
                name = "\u00a73Breathing\u00a7r";
            }
            if (((String)name).contains("invisibility")) {
                name = "\u00a7fInvisibility\u00a7r";
            }
            if (((String)name).contains("blindness")) {
                name = "\u00a77Blindness\u00a7r";
            }
            if (((String)name).contains("nightVision")) {
                name = "\u00a79NightVision\u00a7r";
            }
            if (((String)name).contains("weakness")) {
                name = "\u00a77Weakness\u00a7r";
            }
            if (((String)name).contains("poison")) {
                name = "\u00a72Poison\u00a7r";
            }
            if (potion.getAmplifier() != 0) {
                name = (String)name + " ";
            }
            name = (String)name + TextFormatting.GRAY + I18n.format("enchantment.level." + (potion.getAmplifier() == 0 ? 0 : potion.getAmplifier() + 1), new Object[0]).replace("enchantment.level.0", "").replace("enchantment.level.", "");
            name = ((String)name).replace("256", "").replace("  ", " ");
            Object ampf = "";
            ampf = potion.getDuration() < 50 ? (String)ampf + TextFormatting.RED : (String)ampf + TextFormatting.GRAY;
            ampf = (String)ampf + Potion.getPotionDurationString(potion, 1.0f);
            name = (String)name + " ";
            name = (String)name + (String)ampf;
            this.name = name = ((String)name).replace("  ", " ");
            this.maxTicksDurration = potion.getDuration();
        }

        public void updateDurration(PotionEffect updatedPotionEffect) {
            if (updatedPotionEffect != null) {
                Object name;
                if (updatedPotionEffect.getDuration() > this.potion.getDuration()) {
                    this.potion = updatedPotionEffect;
                    this.maxTicksDurration = this.potion.getDuration();
                }
                if (((String)(name = this.potion.getEffectName())).contains("luck")) {
                    name = "\u00a7aLuck\u00a7r";
                }
                if (((String)name).contains("moveSpeed")) {
                    name = "\u00a7bSpeed\u00a7r";
                }
                if (((String)name).contains("moveSlowdown")) {
                    name = "\u00a77Slowness\u00a7r";
                }
                if (((String)name).contains("jump")) {
                    name = "\u00a7aJumpBoost\u00a7r";
                }
                if (((String)name).contains("digSpeed")) {
                    name = "\u00a76Haste\u00a7r ";
                }
                if (((String)name).contains("digSlowDown")) {
                    name = "\u00a77SlowHand\u00a7r";
                }
                if (((String)name).contains("damageBoost")) {
                    name = "\u00a74Strength\u00a7r";
                }
                if (((String)name).contains("heal")) {
                    name = "\u00a7cInstantHeal\u00a7r";
                }
                if (((String)name).contains("harm")) {
                    name = "\u00a74InstantDamage\u00a7r";
                }
                if (((String)name).contains("confusion")) {
                    name = "\u00a77Nausea\u00a7r";
                }
                if (((String)name).contains("regeneration")) {
                    name = "\u00a7dRegeneration\u00a7r";
                }
                if (((String)name).contains("resistance")) {
                    name = "\u00a7eResistance\u00a7r";
                }
                if (((String)name).contains("absorption")) {
                    name = "\u00a7eAbsorption\u00a7r";
                }
                if (((String)name).contains("fireResistance")) {
                    name = "\u00a76FireResistance\u00a7r";
                }
                if (((String)name).contains("waterBreathing")) {
                    name = "\u00a73Breathing\u00a7r";
                }
                if (((String)name).contains("invisibility")) {
                    name = "\u00a7fInvisibility\u00a7r";
                }
                if (((String)name).contains("blindness")) {
                    name = "\u00a77Blindness\u00a7r";
                }
                if (((String)name).contains("nightVision")) {
                    name = "\u00a79NightVision\u00a7r";
                }
                if (((String)name).contains("weakness")) {
                    name = "\u00a77Weakness\u00a7r";
                }
                if (((String)name).contains("poison")) {
                    name = "\u00a72Poison\u00a7r";
                }
                if (this.potion.getAmplifier() != 0) {
                    name = (String)name + " ";
                }
                name = (String)name + TextFormatting.GRAY + I18n.format("enchantment.level." + (this.potion.getAmplifier() == 0 ? 0 : this.potion.getAmplifier() + 1), new Object[0]).replace("enchantment.level.0", "").replace("enchantment.level.", "");
                name = ((String)name).replace("256", "").replace("  ", " ");
                Object ampf = "";
                ampf = this.potion.getDuration() < 50 ? (String)ampf + TextFormatting.RED : (String)ampf + TextFormatting.GRAY;
                ampf = (String)ampf + Potion.getPotionDurationString(this.potion, 1.0f);
                name = (String)name + " ";
                name = (String)name + (String)ampf;
                this.name = name = ((String)name).replace("  ", " ");
            }
            this.durration = this.potion.getDuration();
        }

        public float getDurrationPC() {
            return MathUtils.clamp((float)this.durration / (float)this.maxTicksDurration, 0.0f, 1.0f);
        }

        public void setToRemove(boolean doRemove) {
            if (!doRemove) {
                if (this.alphaPC.to == 0.0f || this.toRemove) {
                    this.toRemove = false;
                    this.alphaPC.to = 1.0f;
                }
                return;
            }
            this.toRemove = true;
            this.alphaPC.to = 0.0f;
        }

        public void updateRemoveStatus() {
            if (this.alphaPC.to == 0.0f && (double)this.alphaPC.getAnim() < 0.1) {
                this.remove = true;
            }
        }

        public boolean isWantToRemove() {
            return this.remove;
        }

        public PotionEffect getPotion() {
            return this.potion;
        }

        public String getName() {
            return this.name;
        }
    }

    private class StaffPlayer {
        String displayName;
        String name;
        boolean vanished;
        GameType gamemode = GameType.SURVIVAL;
        long startTime = System.currentTimeMillis();
        long startSpecTime = System.currentTimeMillis();
        long startTimeQuit = 0L;
        AnimationUtils alphaPC = new AnimationUtils(0.0f, 1.0f, 0.05f);
        boolean toRemove;
        boolean remove;

        public StaffPlayer(String displayName, String name, boolean vanished, GameType gamemode) {
            this.displayName = displayName;
            this.name = name;
            this.vanished = vanished;
            if (gamemode != null) {
                this.gamemode = gamemode;
            }
            this.toRemove = this.displayName.length() == 0;
            this.alphaPC.setAnim(0.0f);
        }

        String getName() {
            return this.name;
        }

        String getDisplayName() {
            return this.displayName;
        }

        long getSpecTime() {
            if (!this.vanished) {
                this.startSpecTime = System.currentTimeMillis();
            }
            return System.currentTimeMillis() - this.startSpecTime;
        }

        String getSpecTimeString() {
            int sec = (int)(this.getSpecTime() / 1000L);
            int mins = sec / 60;
            int hors = mins / 60;
            sec -= mins * 60;
            String timeString = this.getSpecTime() >= 500L ? " " + TextFormatting.RED + (String)(hors > 0 ? hors + "h" : "") + (String)(mins > 0 ? (mins -= hors * 60) + "m" : "") + (String)(sec > 0 ? sec + "s" : "") + TextFormatting.RESET : "";
            return timeString;
        }

        long getTime() {
            return System.currentTimeMillis() - this.startTime;
        }

        String getTimeString() {
            int sec = (int)(this.getTime() / 1000L);
            int mins = sec / 60;
            int hors = mins / 60;
            sec -= mins * 60;
            String timeString = " " + TextFormatting.WHITE + (String)(hors > 0 ? hors + "h" : "") + (String)(mins > 0 ? (mins -= hors * 60) + "m" : "") + (String)(sec > 0 ? sec + "s" : "") + TextFormatting.RESET;
            return timeString;
        }

        long getQuitTime() {
            if (this.toRemove) {
                return System.currentTimeMillis() - this.startTimeQuit;
            }
            return 0L;
        }

        String getQuitString() {
            if (this.toRemove) {
                return " " + TextFormatting.GRAY + "Quit " + TextFormatting.DARK_GRAY + (String)(this.getQuitTime() > 5000L ? "" : (int)(5L - this.getQuitTime() / 1000L) + "s") + TextFormatting.RESET;
            }
            return "";
        }

        void update() {
            if (this.toRemove) {
                if (this.startTimeQuit == 0L) {
                    this.startTimeQuit = System.currentTimeMillis();
                }
                this.startTime = System.currentTimeMillis();
                this.startSpecTime = System.currentTimeMillis();
            } else if (this.startTimeQuit != 0L) {
                this.startTimeQuit = 0L;
            }
            if (this.toRemove && this.alphaPC.to == 1.0f && this.getQuitTime() >= 5000L) {
                this.alphaPC.to = 0.0f;
            }
            if ((double)this.alphaPC.getAnim() < 0.03) {
                this.remove = true;
            }
        }

        String renderString() {
            int gm = this.gamemode == GameType.SURVIVAL ? 0 : (this.gamemode == GameType.CREATIVE ? 1 : (this.gamemode == GameType.ADVENTURE ? 2 : (this.gamemode == GameType.SPECTATOR ? 3 : -1)));
            Object pref = "";
            boolean spec = this.vanished;
            if (!this.toRemove) {
                boolean warn = Hud.this.getStaffWarnEqualPrefixes().stream().anyMatch(prefix -> Hud.this.lowerContains(this.getDisplayName(), (String)prefix));
                if (warn) {
                    pref = (String)pref + TextFormatting.AQUA + "[!]" + TextFormatting.RESET;
                }
                if (!spec) {
                    List neared = Module.mc.world.getLoadedEntityList().stream().filter(Objects::nonNull).map(Entity::getLivingBaseOf).filter(Objects::nonNull).filter(e -> e instanceof EntityOtherPlayerMP).toList();
                    boolean hasNear = neared.stream().map(Entity::getName).anyMatch(match -> Hud.this.lowerContains((String)match, this.name));
                    if (hasNear) {
                        int dst = neared.stream().filter(entity -> Hud.this.lowerContains(entity.getName(), this.name)).map(entity -> Float.valueOf(Minecraft.player.getDistanceToEntity((Entity)entity))).findFirst().get().intValue();
                        boolean seen = neared.stream().filter(entity -> Hud.this.lowerContains(entity.getName(), this.name)).map(entity -> RenderUtils.isInView(entity)).findFirst().get();
                        pref = (String)pref + TextFormatting.YELLOW + "[N] " + (String)(dst != 0 ? "- " + dst + "m " : " ") + (Serializable)(seen ? TextFormatting.BLUE + "[Seen] " + TextFormatting.RESET : TextFormatting.RESET);
                    } else if (Hud.this.lowerContains(Minecraft.player.getName(), this.name)) {
                        pref = (String)pref + TextFormatting.GREEN + "[ME]" + TextFormatting.RESET;
                    }
                }
                if (gm != 0) {
                    pref = (String)pref + (gm == 1 ? TextFormatting.LIGHT_PURPLE : (gm == 2 ? TextFormatting.GOLD : (gm == 3 ? TextFormatting.AQUA : TextFormatting.GRAY)));
                    pref = (String)pref + "[G" + gm + "] " + TextFormatting.RESET;
                }
            }
            if (spec) {
                pref = (String)pref + TextFormatting.RED + "[S] " + TextFormatting.RESET;
            }
            String time = this.getSpecTime() >= 500L && MathUtils.getDifferenceOf(this.getSpecTime(), this.getTime()) < 1000.0 ? this.getSpecTimeString() : this.getTimeString() + this.getSpecTimeString();
            Object title = "";
            title = this.toRemove ? this.getQuitString() : ((double)((float)this.getTime() / 1000.0f) > 0.5 ? time : "");
            return ((String)pref + this.displayName + (String)title).trim().replace("  ", " ");
        }
    }

    private class PickupItem {
        ItemStack stack;
        boolean toRemove = false;
        AnimationUtils alphaPC = new AnimationUtils(0.0f, 1.0f, 0.1f);
        long startTime = System.currentTimeMillis();
        int maxTime = 8000;

        public PickupItem(ItemStack stack, int maxTime) {
            this.maxTime = maxTime;
            this.stack = stack;
        }

        float timePC() {
            return MathUtils.clamp((float)(System.currentTimeMillis() - this.startTime) / (float)this.maxTime, 0.0f, 1.0f);
        }
    }
}

