/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.GUI;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import us.amerikan.amerikan;
import us.amerikan.events.EventRender2D;
import us.amerikan.events.EventUpdate;
import us.amerikan.events.PacketEvent;
import us.amerikan.gui.FontUtils;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.modules.ModuleManager;
import us.amerikan.utils.GLUtil;
import us.amerikan.utils.RenderUtils;

public class HUD
extends Module {
    private List<Integer> fps = new ArrayList<Integer>();
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private List<Vec3> lastLocations = new ArrayList<Vec3>();
    private List<Long> lastSetBacks = new ArrayList<Long>();
    public static int flags = 0;
    static FontUtils verynicecountryside = new FontUtils("AmazDooMLeft", 0, 82);
    FontUtils font = new FontUtils("Comfortaa", 0, 23);
    FontRenderer fontRenderer;

    public HUD() {
        super("HUD", "HUD", 0, Category.GUI);
        this.fontRenderer = HUD.mc.fontRendererObj;
        ArrayList<String> options = new ArrayList<String>();
        options.add("Classic");
        options.add("Simple");
        amerikan.setmgr.rSetting(new Setting("Design Mode", this, "Classic", options));
        amerikan.setmgr.rSetting(new Setting("LSD", this, true));
        amerikan.setmgr.rSetting(new Setting("Watermark", this, true));
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onEnable();
    }

    private static int rainbow(int delay) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), 0.8f, 0.7f).brighter().getRGB();
    }

    public static void drawHotbar() {
        if (!ModuleManager.getModuleByName("HUD").isEnabled()) {
            return;
        }
        if (amerikan.setmgr.getSettingByName("Design Mode").getValString().equalsIgnoreCase("Classic")) {
            FontRenderer fontRenderer = HUD.mc.fontRendererObj;
            int i2 = 8;
            ScaledResolution res = new ScaledResolution(mc, HUD.mc.displayWidth, HUD.mc.displayHeight);
            int blackBarHeight = fontRenderer.FONT_HEIGHT * 2 + 4;
            Gui.drawRect(0, res.getScaledHeight() - blackBarHeight, res.getScaledWidth(), res.getScaledHeight(), GLUtil.toRGBA(new Color(0, 0, 0, 150)));
            Gui.drawRect(0, res.getScaledHeight() - (blackBarHeight + 1), res.getScaledWidth(), res.getScaledHeight() - blackBarHeight, ColorUtil.getClickGUIColor().getRGB());
            if (Minecraft.thePlayer.inventory.currentItem == 0) {
                Gui.drawGradientRectPublic(res.getScaledWidth() / 2 - 91 + Minecraft.thePlayer.inventory.currentItem * 20, res.getScaledHeight() - blackBarHeight, res.getScaledWidth() / 2 + 91 - 20 * i2, res.getScaledHeight(), GLUtil.toRGBA(new Color(0, 0, 0, 0)), ColorUtil.getClickGUIColor().getRGB());
            } else {
                Gui.drawGradientRectPublic(res.getScaledWidth() / 2 - 91 + Minecraft.thePlayer.inventory.currentItem * 20, res.getScaledHeight() - blackBarHeight, res.getScaledWidth() / 2 + 91 - 20 * (8 - Minecraft.thePlayer.inventory.currentItem), res.getScaledHeight(), GLUtil.toRGBA(new Color(0, 0, 0, 0)), ColorUtil.getClickGUIColor().getRGB());
            }
        } else {
            FontRenderer fontRenderer = HUD.mc.fontRendererObj;
            int i3 = 8;
            ScaledResolution res = new ScaledResolution(mc, HUD.mc.displayWidth, HUD.mc.displayHeight);
            int blackBarHeight = fontRenderer.FONT_HEIGHT * 2 + 4;
            Gui.drawRect(res.getScaledWidth() / 2 - 93, res.getScaledHeight() - blackBarHeight, res.getScaledWidth() / 2 + 93, res.getScaledHeight(), GLUtil.toRGBA(new Color(34, 34, 34, 255)));
            Gui.drawRect(res.getScaledWidth() / 2 - 93, res.getScaledHeight() - blackBarHeight, res.getScaledWidth() / 2 - 91, res.getScaledHeight(), Color.WHITE.getRGB());
            Gui.drawRect(res.getScaledWidth() / 2 + 93, res.getScaledHeight() - blackBarHeight, res.getScaledWidth() / 2 + 91, res.getScaledHeight(), Color.WHITE.getRGB());
            if (Minecraft.thePlayer.inventory.currentItem == 0) {
                Gui.drawRect(res.getScaledWidth() / 2 - 91 + Minecraft.thePlayer.inventory.currentItem * 20, res.getScaledHeight() - blackBarHeight + 20, res.getScaledWidth() / 2 + 91 - 20 * i3, res.getScaledHeight(), Color.WHITE.getRGB());
            } else {
                Gui.drawRect(res.getScaledWidth() / 2 - 91 + Minecraft.thePlayer.inventory.currentItem * 20, res.getScaledHeight() - blackBarHeight + 20, res.getScaledWidth() / 2 + 91 - 20 * (8 - Minecraft.thePlayer.inventory.currentItem), res.getScaledHeight(), Color.WHITE.getRGB());
            }
        }
        if (amerikan.setmgr.getSettingByName("Watermark").getValBoolean()) {
            HUD.WaterMarkGlitch();
        }
    }

    protected static void WaterMarkGlitch() {
        if (amerikan.setmgr.getSettingByName("Design Mode").getValString().equalsIgnoreCase("Classic")) {
            FontRenderer fontRenderer = HUD.mc.fontRendererObj;
            ScaledResolution res = new ScaledResolution(mc, HUD.mc.displayWidth, HUD.mc.displayHeight);
            verynicecountryside.drawString("Mercury", 2.0f, 2.0f, new Color(7, 250, 255, 255).getRGB());
            verynicecountryside.drawString("Mercury", 4.0f, 2.0f, new Color(247, 13, 240, 255).getRGB());
            verynicecountryside.drawString("Mercury", 3.0f, 2.0f, Color.white.getRGB());
        } else {
            FontRenderer fontRenderer = HUD.mc.fontRendererObj;
            fontRenderer.drawStringWithShadow("ercury", 1 + fontRenderer.getStringWidth("M"), 1.0f, Color.WHITE.getRGB());
            fontRenderer.drawStringWithShadow("M", 1.0f, 1.0f, new Color(107, 108, 109, 255).getRGB());
        }
    }

    @EventTarget
    public void MajesticEstroE(EventRender2D e2) {
        if (!amerikan.setmgr.getSettingByName("Design Mode").getValString().equalsIgnoreCase("Simple")) {
            return;
        }
        AtomicInteger offset = new AtomicInteger(3);
        AtomicInteger index = new AtomicInteger();
        ScaledResolution res = new ScaledResolution(mc, HUD.mc.displayWidth, HUD.mc.displayHeight);
        LocalDateTime now = LocalDateTime.now();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);
        this.fontRenderer.drawStringWithShadow(date, res.getScaledWidth() / 2 + 94, res.getScaledHeight() - this.fontRenderer.FONT_HEIGHT - 2, -1);
        this.fontRenderer.drawStringWithShadow(time, res.getScaledWidth() / 2 + 94, res.getScaledHeight() - this.fontRenderer.FONT_HEIGHT * 2 - 2, -1);
        this.fontRenderer.drawStringWithShadow("FPS: " + Minecraft.debugFPS, res.getScaledWidth() / 2 - 94 - this.fontRenderer.getStringWidth("FPS: " + Minecraft.debugFPS), res.getScaledHeight() - this.fontRenderer.FONT_HEIGHT - 2, -1);
        amerikan.modulemanager.getModules().stream().filter(mod -> mod.isToggled() && !mod.isCategory(Category.GUI)).sorted(Comparator.comparingInt(mod -> -this.fontRenderer.getStringWidth(mod.getAddon() != "ignore" ? String.valueOf(mod.getName()) + "[" + mod.getAddon().toUpperCase() + "]" + 2 : mod.getName()))).forEach(mod -> {
            String addon = "[" + mod.getAddon().toUpperCase() + "]";
            this.fontRenderer.drawStringWithShadow(mod.getName(), res.getScaledWidth() - this.fontRenderer.getStringWidth(mod.getName()) - 3 - (mod.getAddon() != "ignore" ? this.fontRenderer.getStringWidth(addon) + 2 : 0), offset.get(), Color.WHITE.getRGB());
            if (mod.getAddon() != "ignore") {
                this.fontRenderer.drawStringWithShadow("\u00a77" + addon, res.getScaledWidth() - this.fontRenderer.getStringWidth(addon) - 3, offset.get(), Color.WHITE.getRGB());
            }
            offset.addAndGet(this.fontRenderer.FONT_HEIGHT + 2);
            index.getAndIncrement();
        });
    }

    @EventTarget
    public void MajesticEstro(EventRender2D e2) {
        if (!amerikan.setmgr.getSettingByName("Design Mode").getValString().equalsIgnoreCase("Classic")) {
            return;
        }
        ScaledResolution res = new ScaledResolution(mc, HUD.mc.displayWidth, HUD.mc.displayHeight);
        int radius = 20;
        this.font.drawString("FPS:", 1.0f, (float)res.getScaledHeight() - this.font.getHeight("1") - 2.0f, ColorUtil.getClickGUIColor().getRGB());
        this.font.drawString("" + Minecraft.debugFPS, 2 + this.fontRenderer.getStringWidth("FPS: "), (float)res.getScaledHeight() - this.font.getHeight("1") - 2.0f, new Color(255, 255, 255, 255).getRGB());
        int ping = mc.isSingleplayer() ? 0 : (int)HUD.mc.getCurrentServerData().pingToServer;
        this.font.drawString("Ping:", 1.0f, (float)res.getScaledHeight() - this.font.getHeight("1") * 2.0f - 2.0f, ColorUtil.getClickGUIColor().getRGB());
        this.font.drawString("" + ping, 1.0f + this.font.getWidth("Ping: "), (float)res.getScaledHeight() - this.font.getHeight("1") * 2.0f - 2.0f, new Color(255, 255, 255, 255).getRGB());
        this.font.drawString("Flags:", this.font.getWidth("FPS: ") + (float)(String.valueOf(Minecraft.debugFPS).length() * 9), (float)res.getScaledHeight() - this.font.getHeight("1") - 3.0f, ColorUtil.getClickGUIColor().getRGB());
        this.font.drawString("" + flags, this.font.getWidth("Flags: ") + this.font.getWidth("FPS: ") + (float)String.valueOf(Minecraft.debugFPS).length() * 8.6f, (float)res.getScaledHeight() - this.font.getHeight("1") - 3.0f, new Color(255, 255, 255, 255).getRGB());
        LocalDateTime now = LocalDateTime.now();
        String date = dateFormat.format(now);
        String time = timeFormat.format(now);
        this.font.drawString(date, (float)res.getScaledWidth() - this.font.getWidth(date), (float)res.getScaledHeight() - this.font.getHeight("1") - 2.0f, -1);
        this.font.drawString(time, (float)res.getScaledWidth() - this.font.getWidth(time), (float)res.getScaledHeight() - this.font.getHeight("1") * 2.0f - 2.0f, -1);
        AtomicInteger offset = new AtomicInteger(3);
        AtomicInteger offsetback = new AtomicInteger(3);
        AtomicInteger index = new AtomicInteger();
        amerikan.modulemanager.getModules().stream().filter(mod -> mod.isToggled() && !mod.isCategory(Category.GUI)).sorted(Comparator.comparingInt(mod -> -((int)this.font.getWidth(mod.getAddon() != "ignore" ? String.valueOf(mod.getName()) + mod.getAddon() + 2 : mod.getName())))).forEach(mod -> {
            RenderUtils.drawRect((float)res.getScaledWidth() - (mod.getAddon() != "ignore" ? this.font.getWidth(mod.getName()) + this.font.getWidth(mod.getAddon()) + 10.0f : this.font.getWidth(mod.getName()) + 8.0f), offset.get() + 1, res.getScaledWidth(), (float)offset.get() + this.font.getHeight("1") + 2.5f, GLUtil.toRGBA(new Color(0, 0, 0, 150)));
            RenderUtils.drawRect((float)res.getScaledWidth() - (mod.getAddon() != "ignore" ? this.font.getWidth(mod.getName()) + this.font.getWidth(mod.getAddon()) + 10.0f : this.font.getWidth(mod.getName()) + 8.0f), offset.get() + 1, (float)res.getScaledWidth() - (mod.getAddon() != "ignore" ? this.font.getWidth(mod.getName()) + this.font.getWidth(mod.getAddon()) + 6.0f : this.font.getWidth(mod.getName()) + 4.0f), (float)offset.get() + this.font.getHeight("1") + 2.5f, ColorUtil.getClickGUIColor().getRGB());
            this.font.drawString(mod.getName(), (float)res.getScaledWidth() - this.font.getWidth(mod.getName()) - 3.0f - (mod.getAddon() != "ignore" ? this.font.getWidth(mod.getAddon()) + 2.0f : 0.0f), offset.get(), !amerikan.setmgr.getSettingByName("LSD").getValBoolean() ? ColorUtil.getClickGUIColor().getRGB() : HUD.rainbow(index.get() * 100));
            if (mod.getAddon() != "ignore") {
                this.font.drawString(mod.getAddon(), (float)res.getScaledWidth() - this.font.getWidth(mod.getAddon()) - 3.0f, offset.get(), new Color(230, 230, 230, 255).getRGB());
            }
            offset.addAndGet((int)this.font.getHeight("1") + 2);
            index.getAndIncrement();
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        });
    }

    @EventTarget
    private void onTickTrickTrack(EventUpdate event) {
        if (HUD.mc.theWorld == null) {
            flags = 0;
        }
    }

    @EventTarget
    private void onMove(EventUpdate event) {
        if (this.getState()) {
            return;
        }
        ArrayList<Long> remove = new ArrayList<Long>();
        for (Long lastSetBack : this.lastSetBacks) {
            if (System.currentTimeMillis() - lastSetBack <= 5000L) continue;
            remove.add(lastSetBack);
        }
        for (Long aLong : remove) {
            this.lastSetBacks.remove(aLong);
        }
        this.lastLocations.add(new Vec3(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ));
        while (this.lastLocations.size() > 30) {
            this.lastLocations.remove(0);
        }
    }

    @EventTarget
    private void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook p2 = (S08PacketPlayerPosLook)event.getPacket();
            boolean setback = this.lastLocations.stream().anyMatch(loc -> p2.func_148932_c() == loc.xCoord && p2.func_148928_d() == loc.yCoord && p2.func_148933_e() == loc.zCoord);
            if (setback) {
                this.lastSetBacks.add(System.currentTimeMillis());
                ++flags;
            }
        }
    }
}

