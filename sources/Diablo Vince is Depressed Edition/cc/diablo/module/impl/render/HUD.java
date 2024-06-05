/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.render;

import cc.diablo.Main;
import cc.diablo.event.impl.ServerJoinEvent;
import cc.diablo.font.TTFFontRenderer;
import cc.diablo.helpers.Stopwatch;
import cc.diablo.helpers.render.ChatColor;
import cc.diablo.helpers.render.ColorHelper;
import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.player.Stealer;
import cc.diablo.module.impl.render.Crosshair;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Timer;

public class HUD
extends Module {
    public static ModeSetting arraylistMode = new ModeSetting("Arraylist", "Boxed", "Simple", "Boxed", "Boxed2", "Infamous", "Diablo", "Diablo2", "Astolfo");
    public static ModeSetting colorMode = new ModeSetting("Color Mode", "Astolfo", "Solid", "Rainbow", "Rainbow Light", "Wave", "Astolfo");
    public static ModeSetting watermarkMode = new ModeSetting("Watermark Mode", "Simple", "Simple", "Skeet", "Diablo");
    public static BooleanSetting minecraftFont = new BooleanSetting("Minecraft Font", true);
    public static BooleanSetting hideVisual = new BooleanSetting("Hide Visuals", false);
    public static BooleanSetting sessionTime = new BooleanSetting("Session Time", true);
    public static NumberSetting delay = new NumberSetting("Delay", 3000.0, 100.0, 10000.0, 50.0);
    public static NumberSetting red = new NumberSetting("Red", 45.0, 1.0, 255.0, 1.0);
    public static NumberSetting green = new NumberSetting("Green", 24.0, 1.0, 255.0, 1.0);
    public static NumberSetting blue = new NumberSetting("Blue", 158.0, 1.0, 255.0, 1.0);
    public static NumberSetting offset = new NumberSetting("Offset", 0.0, 0.0, 6.0, 1.0);
    public static Stopwatch watch = new Stopwatch();

    public HUD() {
        super("HUD", "Displays the Client's Hud!", 0, Category.Render);
        this.addSettings(arraylistMode, colorMode, minecraftFont, hideVisual, sessionTime, delay, red, green, blue, offset);
    }

    public void drawHud(ScaledResolution sr) {
        TTFFontRenderer fr = Main.getInstance().getSFUI(20);
        TTFFontRenderer frSmall = Main.getInstance().getSFUI(20);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String bps = "BPS\u00a7s: " + String.format("%.2f", Math.hypot(HUD.mc.thePlayer.posX - HUD.mc.thePlayer.prevPosX, HUD.mc.thePlayer.posZ - HUD.mc.thePlayer.prevPosZ) * (double)Timer.timerSpeed * 20.0);
        String coords = "XYZ\u00a7s: " + HUD.mc.thePlayer.getPosition().getX() + "," + HUD.mc.thePlayer.getPosition().getY() + "," + HUD.mc.thePlayer.getPosition().getZ();
        String userInfo = (Object)((Object)ChatColor.DARK_GRAY) + Main.buildType + (Object)((Object)ChatColor.WHITE) + " (" + (Object)((Object)ChatColor.WHITE) + Main.version + (Object)((Object)ChatColor.WHITE) + ") - " + dtf.format(now);
        int count = 0;
        int lastX = -1;
        double yOff = 0.0;
        double xOff = 0.0;
        ArrayList<Module> toggledModule = new ArrayList<Module>();
        Module lastModule = null;
        Module firstModule = null;
        switch ("Simple") {
            case "Diablo": {
                break;
            }
            case "Simple": {
                this.drawString(Main.customName.charAt(0) + "\u00a77" + Main.customName.substring(1) + " " + Main.version, 2.5f, 2.5f, ColorHelper.getColor(0));
            }
        }
        if (sessionTime.isChecked()) {
            String text = HUD.GetElapsed(Main.timestamp, watch.getCurrentMS(), true);
            HUD.mc.fontRendererObj.drawStringWithShadow(text, sr.getScaledWidth() / 2 - HUD.mc.fontRendererObj.getStringWidth(text) / 2, sr.getScaledHeight() / 7, -1);
        }
        ModuleManager.getModules().sort(Comparator.comparing(module -> Float.valueOf(this.getFontWidth(((Module)module).getDisplayName()))).reversed());
        switch (arraylistMode.getMode()) {
            case "Boxed": {
                for (Module module2 : ModuleManager.getModules()) {
                    if (!module2.isToggled() || !this.shouldShow(module2)) continue;
                    yOff = offset.getVal() / 2.0;
                    int color = ColorHelper.getColor(count * 150);
                    toggledModule.add(module2);
                    RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(module2.getDisplayName()) - 1.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(module2.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)sr.getScaledWidth(), (double)((float)count * this.getFontHeight(module2.getDisplayName()) + this.getFontHeight(module2.getDisplayName())) + offset.getVal() / 2.0 - yOff, RenderUtils.transparency(new Color(18, 18, 18).getRGB(), 0.6f));
                    RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(module2.getDisplayName()) - 2.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(module2.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)((float)sr.getScaledWidth() - this.getFontWidth(module2.getDisplayName()) - 1.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(module2.getDisplayName()) + this.getFontHeight(module2.getDisplayName())) + offset.getVal() / 2.0 - yOff, color);
                    if (lastX != -1) {
                        RenderUtils.drawRect((double)(lastX - 1) - offset.getVal(), (double)((float)count * this.getFontHeight(module2.getDisplayName())) + offset.getVal() / 2.0 - 1.0 - yOff, (double)((float)sr.getScaledWidth() - this.getFontWidth(module2.getDisplayName()) - 1.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(module2.getDisplayName())) + offset.getVal() / 2.0 - yOff, color);
                    }
                    this.drawString(module2.getDisplayName(), (float)((double)((float)sr.getScaledWidth() - this.getFontWidth(module2.getDisplayName())) - offset.getVal() / 2.0), (float)((double)((float)((double)((float)count * this.getFontHeight(module2.getDisplayName())) + offset.getVal() * 0.5)) - yOff), color);
                    lastX = (int)((float)sr.getScaledWidth() - this.getFontWidth(module2.getDisplayName()));
                    lastModule = module2;
                    ++count;
                }
                if (lastModule == null) break;
                RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(lastModule.getDisplayName()) - 2.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(lastModule.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)sr.getScaledWidth(), (double)((float)count * this.getFontHeight(lastModule.getDisplayName())) + offset.getVal() / 2.0 + 1.0 - yOff, ColorHelper.getColor(count * 150));
                break;
            }
            case "Boxed2": {
                for (Module module3 : ModuleManager.getModules()) {
                    if (!module3.isToggled() || !this.shouldShow(module3)) continue;
                    yOff = offset.getVal() / 2.0;
                    int color = ColorHelper.getColor(count * 150);
                    toggledModule.add(module3);
                    RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(module3.getDisplayName()) - 1.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(module3.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)sr.getScaledWidth(), (double)((float)count * this.getFontHeight(module3.getDisplayName()) + this.getFontHeight(module3.getDisplayName())) + offset.getVal() / 2.0 - yOff, RenderUtils.transparency(new Color(229, 229, 229).getRGB(), 0.125));
                    RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(module3.getDisplayName()) - 2.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(module3.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)((float)sr.getScaledWidth() - this.getFontWidth(module3.getDisplayName()) - 1.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(module3.getDisplayName()) + this.getFontHeight(module3.getDisplayName())) + offset.getVal() / 2.0 - yOff, color);
                    if (lastX != -1) {
                        RenderUtils.drawRect((double)(lastX - 1) - offset.getVal(), (double)((float)count * this.getFontHeight(module3.getDisplayName())) + offset.getVal() / 2.0 - 1.0 - yOff, (double)((float)sr.getScaledWidth() - this.getFontWidth(module3.getDisplayName()) - 1.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(module3.getDisplayName())) + offset.getVal() / 2.0 - yOff, color);
                    }
                    this.drawString(module3.getDisplayName(), (float)((double)((float)sr.getScaledWidth() - this.getFontWidth(module3.getDisplayName())) - offset.getVal() / 2.0), (float)((double)((float)((double)((float)count * this.getFontHeight(module3.getDisplayName())) + offset.getVal() * 0.5)) - yOff), color);
                    lastX = (int)((float)sr.getScaledWidth() - this.getFontWidth(module3.getDisplayName()));
                    lastModule = module3;
                    ++count;
                }
                if (lastModule == null) break;
                RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(lastModule.getDisplayName()) - 2.0f) - offset.getVal(), (double)((float)count * this.getFontHeight(lastModule.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)sr.getScaledWidth(), (double)((float)count * this.getFontHeight(lastModule.getDisplayName())) + offset.getVal() / 2.0 + 1.0 - yOff, ColorHelper.getColor(count * 150));
                break;
            }
            case "Diablo": {
                for (Module module4 : ModuleManager.getModules()) {
                    if (!module4.isToggled() || !this.shouldShow(module4)) continue;
                    if (firstModule == null) {
                        firstModule = module4;
                    }
                    int color = ColorHelper.getColor(count * 150);
                    yOff = -6.0;
                    xOff = 5.0;
                    toggledModule.add(module4);
                    RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(module4.getDisplayName()) - 1.0f) - offset.getVal() - xOff, (double)((float)count * this.getFontHeight(module4.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)sr.getScaledWidth() - xOff, (double)((float)count * this.getFontHeight(module4.getDisplayName()) + this.getFontHeight(module4.getDisplayName())) + offset.getVal() / 2.0 - yOff, RenderUtils.transparency(new Color(57, 57, 57).getRGB(), 0.6f));
                    if (lastX != -1) {
                        // empty if block
                    }
                    this.drawString(module4.getDisplayName(), (float)((double)((float)sr.getScaledWidth() - this.getFontWidth(module4.getDisplayName())) - offset.getVal() / 2.0 - xOff), (float)((double)((float)((double)((float)count * this.getFontHeight(module4.getDisplayName())) + offset.getVal() * 0.5)) - yOff), color);
                    lastX = (int)((float)sr.getScaledWidth() - this.getFontWidth(module4.getDisplayName()));
                    lastModule = module4;
                    ++count;
                }
                if (firstModule == null) break;
                RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(firstModule.getDisplayName()) - 1.0f) - offset.getVal() - xOff, offset.getVal() / 2.0 - yOff - 2.0, (double)sr.getScaledWidth() - xOff, offset.getVal() / 2.0 - yOff, RenderUtils.transparency(new Color(57, 57, 57).getRGB(), 0.6f));
                RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(firstModule.getDisplayName()) - 1.0f) - offset.getVal() - xOff, offset.getVal() / 2.0 - yOff - 2.0, (double)sr.getScaledWidth() - xOff, offset.getVal() / 2.0 - yOff - 1.5, ColorHelper.getColor(150));
                break;
            }
            case "Diablo2": {
                for (Module module5 : ModuleManager.getModules()) {
                    if (!module5.isToggled() || !this.shouldShow(module5)) continue;
                    if (firstModule == null) {
                        firstModule = module5;
                    }
                    int color = ColorHelper.getColor(count * 150);
                    yOff = -6.0;
                    xOff = 5.0;
                    toggledModule.add(module5);
                    RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(module5.getDisplayName()) - 1.0f) - offset.getVal() - xOff, (double)((float)count * this.getFontHeight(module5.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)sr.getScaledWidth() - xOff, (double)((float)count * this.getFontHeight(module5.getDisplayName()) + this.getFontHeight(module5.getDisplayName())) + offset.getVal() / 2.0 - yOff, RenderUtils.transparency(new Color(57, 57, 57).getRGB(), 0.6f));
                    RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(module5.getDisplayName())) - 1.7 - offset.getVal() - xOff, (double)((float)count * this.getFontHeight(module5.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)((float)sr.getScaledWidth() - this.getFontWidth(module5.getDisplayName()) - 1.0f) - offset.getVal() - xOff, (double)((float)count * this.getFontHeight(module5.getDisplayName()) + this.getFontHeight(module5.getDisplayName())) + offset.getVal() / 2.0 - yOff, color);
                    if (lastX != -1) {
                        // empty if block
                    }
                    this.drawString(module5.getDisplayName(), (float)((double)((float)sr.getScaledWidth() - this.getFontWidth(module5.getDisplayName())) - offset.getVal() / 2.0 - xOff), (float)((double)((float)((double)((float)count * this.getFontHeight(module5.getDisplayName())) + offset.getVal() * 0.5)) - yOff), color);
                    lastX = (int)((float)sr.getScaledWidth() - this.getFontWidth(module5.getDisplayName()));
                    lastModule = module5;
                    ++count;
                }
                if (firstModule == null) break;
                RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(firstModule.getDisplayName())) - 1.5 - offset.getVal() - xOff, offset.getVal() / 2.0 - yOff - 2.0, (double)sr.getScaledWidth() - xOff, offset.getVal() / 2.0 - yOff, RenderUtils.transparency(new Color(57, 57, 57).getRGB(), 0.6f));
                RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(firstModule.getDisplayName())) - 1.5 - offset.getVal() - xOff, offset.getVal() / 2.0 - yOff - 2.0, (double)sr.getScaledWidth() - xOff, offset.getVal() / 2.0 - yOff - 1.5, ColorHelper.getColor(150));
                break;
            }
            case "Simple": {
                for (Module module6 : ModuleManager.getModules()) {
                    if (!module6.isToggled() || !this.shouldShow(module6)) continue;
                    toggledModule.add(module6);
                    int color = ColorHelper.getColor(count * 150);
                    this.drawString(module6.getDisplayName(), (float)sr.getScaledWidth() - this.getFontWidth(module6.getDisplayName()) - 3.0f, (float)count * this.getFontHeight(module6.getDisplayName()) + 3.0f, color);
                    ++count;
                }
                break;
            }
            case "Infamous": {
                minecraftFont.setChecked(true);
                int y2 = (int)offset.getVal();
                for (Module m : ModuleManager.getModules()) {
                    if (!m.isToggled() || !this.shouldShow(m)) continue;
                    HUD.mc.fontRendererObj.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - HUD.mc.fontRendererObj.getStringWidth(m.getDisplayName()), y2, ColorHelper.getColor(0));
                    y2 += 14;
                }
                break;
            }
            case "Astolfo": {
                for (Module module7 : ModuleManager.getModules()) {
                    if (!module7.isToggled() || !this.shouldShow(module7)) continue;
                    yOff = offset.getVal() / 2.0;
                    int color = ColorHelper.getColor(count * 150);
                    double offBlackBox = minecraftFont.isChecked() ? 4.0 : 3.5;
                    toggledModule.add(module7);
                    RenderUtils.drawRect((double)((float)sr.getScaledWidth() - this.getFontWidth(module7.getDisplayName())) - offBlackBox, (double)((float)count * this.getFontHeight(module7.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)sr.getScaledWidth(), (double)((float)count * this.getFontHeight(module7.getDisplayName()) + this.getFontHeight(module7.getDisplayName())) + offset.getVal() / 2.0 - yOff, RenderUtils.transparency(new Color(18, 18, 18).getRGB(), 0.6f));
                    RenderUtils.drawRect((double)sr.getScaledWidth() - 0.75, (double)((float)count * this.getFontHeight(module7.getDisplayName())) + offset.getVal() / 2.0 - yOff, (double)sr.getScaledWidth(), (double)((float)count * this.getFontHeight(module7.getDisplayName()) + this.getFontHeight(module7.getDisplayName())) + offset.getVal() / 2.0 - yOff, color);
                    this.drawString(module7.getDisplayName(), (float)sr.getScaledWidth() - this.getFontWidth(module7.getDisplayName()) - 2.0f, (float)((double)((float)((double)((float)count * this.getFontHeight(module7.getDisplayName())) + offset.getVal() * 0.5)) - yOff), color);
                    lastX = (int)((float)sr.getScaledWidth() - this.getFontWidth(module7.getDisplayName()));
                    lastModule = module7;
                    ++count;
                }
                break;
            }
        }
        this.drawString(bps, 1.0f, (float)sr.getScaledHeight() - this.getFontHeight(bps) - this.getFontHeight(coords), ColorHelper.getColor(0));
        this.drawString(coords, 1.0f, (float)sr.getScaledHeight() - this.getFontHeight(coords), ColorHelper.getColor(0));
        this.drawString(userInfo, (float)sr.getScaledWidth() - this.getFontWidth(userInfo) - 1.0f, (float)sr.getScaledHeight() - this.getFontHeight(userInfo) - 1.0f, ColorHelper.getColor(0));
        if (Stealer.isStealing) {
            this.drawString("Stealing...", (float)(sr.getScaledWidth() / 2) - this.getFontWidth("Stealing...") / 2.0f, (float)(sr.getScaledHeight() / 2) + this.getFontHeight("Stealing") + 3.0f, -1);
        }
        if (ModuleManager.getModule(Crosshair.class).isToggled()) {
            int n = new Color((int)Crosshair.red.getVal(), (int)Crosshair.green.getVal(), (int)Crosshair.blue.getVal()).getRGB();
        }
    }

    private boolean shouldShow(Module module) {
        if (hideVisual.isChecked()) {
            return module.category != Category.Render;
        }
        return true;
    }

    @Override
    public void onEnable() {
        Main.timestamp = watch.getCurrentMS();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.toggle();
        super.onDisable();
    }

    @Subscribe
    public void onServerJoin(ServerJoinEvent e) {
        this.setToggled(false);
        this.setToggled(true);
        watch.reset();
        Main.timestamp = watch.getCurrentMS();
    }

    public float getFontWidth(String text) {
        TTFFontRenderer fr = Main.getInstance().getSFUI(20);
        return minecraftFont.isChecked() ? (float)HUD.mc.fontRendererObj.getStringWidth(text) : fr.getWidth(text);
    }

    public float getFontHeight(String text) {
        TTFFontRenderer fr = Main.getInstance().getSFUI(20);
        return minecraftFont.isChecked() ? (float)HUD.mc.fontRendererObj.FONT_HEIGHT : fr.getHeight(text);
    }

    public void drawString(String text, float x, float y, int color) {
        TTFFontRenderer fr = Main.getInstance().getSFUI(20);
        if (minecraftFont.isChecked()) {
            HUD.mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
        } else {
            fr.drawStringWithShadow(text, x, y, color);
        }
    }

    public static String GetElapsed(long aInitialTime, long aEndTime, boolean aIncludeMillis) {
        StringBuffer elapsed = new StringBuffer();
        HashMap<String, Long> units = new HashMap<String, Long>();
        long milliseconds = aEndTime - aInitialTime;
        long seconds = milliseconds / 1000L;
        long minutes = milliseconds / 60000L;
        long hours = milliseconds / 3600000L;
        long days = milliseconds / 86400000L;
        units.put("milliseconds", milliseconds);
        units.put("seconds", seconds);
        units.put("minutes", minutes);
        units.put("hours", hours);
        units.put("days", days);
        if (days > 0L) {
            long leftoverHours = hours % 24L;
            units.put("hours", leftoverHours);
        }
        if (hours > 0L) {
            long leftoeverMinutes = minutes % 60L;
            units.put("minutes", leftoeverMinutes);
        }
        if (minutes > 0L) {
            long leftoverSeconds = seconds % 60L;
            units.put("seconds", leftoverSeconds);
        }
        if (seconds > 0L) {
            long leftoverMilliseconds = milliseconds % 1000L;
            units.put("milliseconds", leftoverMilliseconds);
        }
        if ((Long)units.get("days") != 0L) {
            elapsed.append(units.get("days")).append("d ");
        }
        if ((Long)units.get("hours") != 0L) {
            elapsed.append(units.get("hours")).append("h ");
        }
        if ((Long)units.get("minutes") != 0L) {
            elapsed.append(units.get("minutes")).append("m ");
        }
        elapsed.append(units.get("seconds")).append("s ");
        return elapsed.toString();
    }

    private static String PrependZeroIfNeeded(long aValue) {
        return aValue < 10L ? "0" + aValue : Long.toString(aValue);
    }
}

