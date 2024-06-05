package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventChat;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Client;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.UI.Font.CFontRenderer;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.ClientUtil;
import gq.vapu.czfclient.Util.CompassUtil;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Render.Colors;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HUD extends Module {

    public static boolean shouldMove;
    public static int ping;
    public static float red;
    public static float green;
    public static float blue;
    public static boolean UI;
    private static float x;
    private static int y;
    public Numbers<Double> r = new Numbers<Double>("Red", "Red", 47.0, 0.0, 255.0, 1.0);
    public Numbers<Double> g = new Numbers<Double>("Green", "Green", 116.0, 0.0, 255.0, 1.0);
    public Numbers<Double> b = new Numbers<Double>("Blue", "Blue", 253.0, 0.0, 255.0, 1.0);
    public Numbers<Double> alpha = new Numbers<Double>("Alpha", "Alpha", 0.0, 0.0, 255.0, 1.0);
    public Mode mode1 = new Mode("TabUI", "UI", UIMode.values(), UIMode.ETB);
    private final Mode<Enum> mode = new Mode("Mode", "Mode", Mode1.values(), Mode1.None);
    private final Option<Boolean> rainbow = new Option<Boolean>("Rainbow", "Rainbow", true);
    private final Option<Boolean> Sense = new Option<Boolean>("CzfSense", "Sense", true);

    public HUD() {
        super("HUD", new String[]{"gui"}, ModuleType.Render);
        this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
        this.setEnabled(true);
        this.setRemoved(true);
        this.addValues(this.r, this.g, this.b, this.rainbow, this.Sense);
    }

    @EventHandler
    public void onChat(EventChat e) {
        if (e.getMessage().contains("jndi")) {
            Helper.sendMessage("[AntiJNDI]JNDI Injection Message has been Removed!");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void renderHud(EventRender2D event) {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        if (this.Sense.getValue()) {
            Sense sb = new Sense();
            sb.init();
        }


        CFontRenderer font = FontLoaders.GoogleSans26;
        CFontRenderer font1 = FontLoaders.GoogleSans18;
        FontLoaders.NovICON44.drawString("M", 44, 20, new Color(47, 154, 241).getRGB());
        String name;
        String direction;
        ArrayList<Module> sorted = new ArrayList<Module>();
        Client.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled() || m.wasRemoved())
                continue;
            sorted.add(m);
        }
        sorted.sort((o1,
                     o2) -> font1.getStringWidth(
                o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix()))
                - font1.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName()
                : String.format("%s %s", o1.getName(), o1.getSuffix())));
        y = 0;
        float rainbowRGB = 0;
        for (int i = 0; i < sorted.size(); i++) {
            Color rainbow = new Color(Color.HSBtoRGB(((float) (System.nanoTime()) / 10000000000F) + (rainbowRGB += 0.05) % 1, 1F, 1F));
            Module m = sorted.get(i);
            name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s", m.getName(), m.getSuffix());
            x = RenderUtil.width() - font1.getStringWidth(name);
            ping = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
            if (this.rainbow.getValue()) {
                red = rainbow.getRed() / 255.0f;
                green = rainbow.getGreen() / 255.0f;
                blue = rainbow.getBlue() / 255.0f;
            } else {
                red = getRed() / 255.0f;
                green = getGreen() / 255.0f;
                blue = getBlue() / 255.0f;
            }
            float a = 255.0f;
            float al = getAlpha() / 255.0f;
            float x1 = 0;
            float x2 = 0;
            float x3 = 0;
            float x4 = 0;
            float x5 = 0;
            if (mode.getValue() == Mode1.Left) {
                x1 = x - 5;
                x2 = RenderUtil.width();
                x3 = x - 8;
                x4 = x - 5;
                x5 = x - 2;
            } else if (mode.getValue() == Mode1.Right) {
                x1 = x - 8;
                x2 = RenderUtil.width() - 3;
                x3 = RenderUtil.width() - 3;
                x4 = RenderUtil.width();
                x5 = x - 5;
            } else if (mode.getValue() == Mode1.None) {
                x1 = x - 5;
                x2 = RenderUtil.width();
                x5 = x - 2;
                a = 0.0f;
            }
//			TabUI tui = new TabUI();
//			tui.init();
            Gui.drawRect(x1, y, x2, y + 10, new Color(0, 0, 0, al).getRGB());
            if (!(mode.getValue() == Mode1.None)) {
                Gui.drawRect(x3, y, x4, y + 10, new Color(red, green, blue).getRGB());
            }
            font1.drawString(name, x5, y + 3, new Color(red, green, blue).getRGB());

            y += 10;
        }
        this.drawPotionStatus(new ScaledResolution(mc));
        CompassUtil cpass = new CompassUtil(325, 325, 1, 2, true);
        ScaledResolution sc = new ScaledResolution(mc);
        cpass.draw(sc); //字体渲染问题必须修复才能打开指南针，不然太丑了
        //this.renderPotionStatus();
    }

    private void drawPotionStatus(ScaledResolution sr) {
        int y = 0;
        for (PotionEffect effect : mc.thePlayer.getActivePotionEffects()) {
            int ychat;
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName());
            switch (effect.getAmplifier()) {
                case 1: {
                    PType = PType + " II";
                    break;
                }
                case 2: {
                    PType = PType + " III";
                    break;
                }
                case 3: {
                    PType = PType + " IV";
                    break;
                }
            }
            if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                PType = PType + "\u00a77:\u00a76 " + Potion.getDurationString(effect);
            } else if (effect.getDuration() < 300) {
                PType = PType + "\u00a77:\u00a7c " + Potion.getDurationString(effect);
            } else if (effect.getDuration() > 600) {
                PType = PType + "\u00a77:\u00a77 " + Potion.getDurationString(effect);
            }
            int n = ychat = -10;
            mc.fontRendererObj.drawStringWithShadow(PType,
                    sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(PType) - 2,
                    sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT + y - 12 - ychat - 20,
                    potion.getLiquidColor());
            y -= 10;
        }
    }

    public void renderPotionStatus(int width, int height) {
        {

            Map<Potion, Double> timerMap = new HashMap<Potion, Double>();

            x = 0;

            for (PotionEffect effect : mc.thePlayer.getActivePotionEffects()) {
                Potion potion = Potion.potionTypes[effect.getPotionID()];
                String PType = I18n.format(potion.getName());
                int minutes = -1;
                int seconds = -2;

                try {
                    minutes = Integer.parseInt(Potion.getDurationString(effect).split(":")[0]);
                    seconds = Integer.parseInt(Potion.getDurationString(effect).split(":")[1]);
                } catch (Exception ex) {
                    minutes = 0;
                    seconds = 0;
                }

                double total = (minutes * 60) + seconds;

                if (!timerMap.containsKey(potion)) {
                    timerMap.put(potion, total);
                }

                if (timerMap.get(potion) == 0 || total > timerMap.get(potion)) {
                    timerMap.replace(potion, total);
                }

                switch (effect.getAmplifier()) {
                    case 1:
                        PType = PType + " II";
                        break;
                    case 2:
                        PType = PType + " III";
                        break;
                    case 3:
                        PType = PType + " IV";
                        break;
                    default:
                        break;
                }

                int color = Colors.WHITE.c;

                if (effect.getDuration() < 600 && effect.getDuration() > 300) {
                    color = Colors.YELLOW.c;
                } else if (effect.getDuration() < 300) {
                    color = Colors.RED.c;
                } else if (effect.getDuration() > 600) {
                    color = Colors.WHITE.c;
                }

                int x1 = (int) ((width - 6) * 1.33f);
                int y1 = (int) ((height - 52 - mc.fontRendererObj.FONT_HEIGHT + x + 5) * 1.33F);

                RenderUtil.drawRect(width - 120, height - 60 + x, width - 10, height - 30 + x,
                        ClientUtil.reAlpha(Colors.BLACK.c, 0.41f));

                if (potion.hasStatusIcon()) {
                    GlStateManager.pushMatrix();

                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDepthMask(false);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    GL11.glColor4f(1, 1, 1, 1);
                    int index = potion.getStatusIconIndex();
                    ResourceLocation location = new ResourceLocation("textures/gui/container/inventory.png");
                    mc.getTextureManager().bindTexture(location);
                    GlStateManager.scale(0.75, 0.75, 0.75);
                    mc.ingameGUI.drawTexturedModalRect(x1 - 138, y1 + 8, 0 + index % 8 * 18, 198 + index / 8 * 18, 18, 18);

                    GL11.glDepthMask(true);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GlStateManager.popMatrix();
                }

                int y = (int) ((height - mc.fontRendererObj.FONT_HEIGHT + x) - 38);
                CFontRenderer font = FontLoaders.Comfortaa18;
                font.drawString(PType.replaceAll("\247.", ""), (float) width - 91f,
                        y - mc.fontRendererObj.FONT_HEIGHT + 1, new Color(47, 116, 253).getRGB());

                font.drawString(Potion.getDurationString(effect).replaceAll("\247.", ""),
                        width - 91f, y + 4, ClientUtil.reAlpha(-1, 0.8f));

                x -= 35;

            }
        }
    }

    public int getRed() {
        return this.r.getValue().intValue();
    }

    public int getGreen() {
        return this.g.getValue().intValue();
    }

    public int getBlue() {
        return this.b.getValue().intValue();
    }

    public int getAlpha() {
        return this.alpha.getValue().intValue();
    }

    public enum Mode1 {
        Left, Right, None
    }

    public enum UIMode {
        Skeet, ETB
    }
}
