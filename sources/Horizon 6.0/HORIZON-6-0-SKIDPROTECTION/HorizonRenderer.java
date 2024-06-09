package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;

public class HorizonRenderer
{
    public static int HorizonCode_Horizon_È;
    public static int Â;
    private static final Potion[] Ý;
    private static final Potion[] Ø­áŒŠá;
    
    static {
        HorizonRenderer.HorizonCode_Horizon_È = 0;
        HorizonRenderer.Â = 0;
        Ý = new Potion[] { Potion.µÕ, Potion.µà, Potion.Ø­áŒŠá, Potion.¥Æ, Potion.Ø­à };
        Ø­áŒŠá = new Potion[] { Potion.á, Potion.Ý, Potion.Å, Potion.à, Potion.Âµá€, Potion.Ï­Ðƒà, Potion.£á, Potion.£à };
    }
    
    public static void HorizonCode_Horizon_È(final long mouseX, final long mouseY) {
        if (Minecraft.áŒŠà().¥Æ instanceof GuiIRCChat) {
            return;
        }
        Horizon.à¢.Ô.HorizonCode_Horizon_È();
        Â();
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GuiInventory.HorizonCode_Horizon_È(18, 109, 30, -30.0f, Minecraft.áŒŠà().á.áƒ / 10.0f, Minecraft.áŒŠà().á);
    }
    
    public static void HorizonCode_Horizon_È() {
        if (Minecraft.áŒŠà().¥Æ instanceof GuiIRCChat) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(Minecraft.áŒŠà(), Minecraft.áŒŠà().Ó, Minecraft.áŒŠà().à);
        final int width = sr.HorizonCode_Horizon_È();
        final int height = sr.Â();
        final FontRenderer fr = Minecraft.áŒŠà().µà;
        if (Minecraft.áŒŠà().Âµá€.à()) {
            int x = 15;
            GL11.glPushMatrix();
            RenderHelper.Ý();
            for (int index = 3; index >= 0; --index) {
                final ItemStack stack = Minecraft.áŒŠà().á.Ø­Ñ¢Ï­Ø­áˆº.Â[index];
                final ItemStack iteminhand = Minecraft.áŒŠà().á.áŒŠá();
                if (iteminhand != null) {
                    Minecraft.áŒŠà().áˆºÏ().Â(iteminhand, sr.HorizonCode_Horizon_È() / 2 + 92, sr.Â() - 19);
                    Minecraft.áŒŠà().áˆºÏ().HorizonCode_Horizon_È(Minecraft.áŒŠà().µà, iteminhand, sr.HorizonCode_Horizon_È() / 2 + 92, sr.Â() - 19);
                }
                if (stack != null) {
                    Minecraft.áŒŠà().áˆºÏ().Â(stack, sr.HorizonCode_Horizon_È() / 2 + x, sr.Â() - (Minecraft.áŒŠà().á.HorizonCode_Horizon_È(Material.Ø) ? 65 : 55));
                    Minecraft.áŒŠà().áˆºÏ().HorizonCode_Horizon_È(Minecraft.áŒŠà().µà, stack, sr.HorizonCode_Horizon_È() / 2 + x, sr.Â() - (Minecraft.áŒŠà().á.HorizonCode_Horizon_È(Material.Ø) ? 65 : 55));
                    x += 18;
                }
                final ItemStack compass = new ItemStack(Items.£ÇªÓ);
                Minecraft.áŒŠà().áˆºÏ().Â(compass, sr.HorizonCode_Horizon_È() - 20, sr.Â() - 19);
                final ItemStack watch = new ItemStack(Items.Š);
                Minecraft.áŒŠà().áˆºÏ().Â(watch, sr.HorizonCode_Horizon_È() - 40, sr.Â() - 19);
            }
            RenderHelper.HorizonCode_Horizon_È();
            GL11.glPopMatrix();
        }
    }
    
    public static void Â() {
        if (Horizon.Ó.equalsIgnoreCase("metro")) {
            if (Horizon.Âµá€.equals("red")) {
                UIFonts.Ø.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Ø.HorizonCode_Horizon_È("H", 2, 2, -756593604);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 16, 2, -756593604);
                UIFonts.Ø.HorizonCode_Horizon_È("r", 30, 2, -756593604);
                UIFonts.Ø.HorizonCode_Horizon_È("i", 40, 2, -756593604);
                UIFonts.Ø.HorizonCode_Horizon_È("z", 45, 2, -756593604);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 57, 2, -756593604);
                UIFonts.Ø.HorizonCode_Horizon_È("n", 71, 2, -756593604);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -756593604);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -756593604);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -756593604);
            }
            if (Horizon.Âµá€.equals("green")) {
                UIFonts.Ø.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Ø.HorizonCode_Horizon_È("H", 2, 2, -769151392);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 16, 2, -769151392);
                UIFonts.Ø.HorizonCode_Horizon_È("r", 30, 2, -769151392);
                UIFonts.Ø.HorizonCode_Horizon_È("i", 40, 2, -769151392);
                UIFonts.Ø.HorizonCode_Horizon_È("z", 45, 2, -769151392);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 57, 2, -769151392);
                UIFonts.Ø.HorizonCode_Horizon_È("n", 71, 2, -769151392);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -769151392);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -769151392);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -769151392);
            }
            if (Horizon.Âµá€.equals("blue")) {
                UIFonts.Ø.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Ø.HorizonCode_Horizon_È("H", 2, 2, -770269051);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 16, 2, -770269051);
                UIFonts.Ø.HorizonCode_Horizon_È("r", 30, 2, -770269051);
                UIFonts.Ø.HorizonCode_Horizon_È("i", 40, 2, -770269051);
                UIFonts.Ø.HorizonCode_Horizon_È("z", 45, 2, -770269051);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 57, 2, -770269051);
                UIFonts.Ø.HorizonCode_Horizon_È("n", 71, 2, -770269051);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -770269051);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -770269051);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -770269051);
            }
            if (Horizon.Âµá€.equals("magenta")) {
                UIFonts.Ø.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Ø.HorizonCode_Horizon_È("H", 2, 2, -758369281);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 16, 2, -758369281);
                UIFonts.Ø.HorizonCode_Horizon_È("r", 30, 2, -758369281);
                UIFonts.Ø.HorizonCode_Horizon_È("i", 40, 2, -758369281);
                UIFonts.Ø.HorizonCode_Horizon_È("z", 45, 2, -758369281);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 57, 2, -758369281);
                UIFonts.Ø.HorizonCode_Horizon_È("n", 71, 2, -758369281);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -758369281);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -758369281);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -758369281);
            }
            if (Horizon.Âµá€.equals("orange")) {
                UIFonts.Ø.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Ø.HorizonCode_Horizon_È("H", 2, 2, -754996464);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 16, 2, -754996464);
                UIFonts.Ø.HorizonCode_Horizon_È("r", 30, 2, -754996464);
                UIFonts.Ø.HorizonCode_Horizon_È("i", 40, 2, -754996464);
                UIFonts.Ø.HorizonCode_Horizon_È("z", 45, 2, -754996464);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 57, 2, -754996464);
                UIFonts.Ø.HorizonCode_Horizon_È("n", 71, 2, -754996464);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -754996464);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -754996464);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -754996464);
            }
            if (Horizon.Âµá€.equals("rainbow")) {
                final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                final int color2 = ColorUtil.HorizonCode_Horizon_È(400000000L, 1.0f).getRGB();
                final int color3 = ColorUtil.HorizonCode_Horizon_È(600000000L, 1.0f).getRGB();
                final int color4 = ColorUtil.HorizonCode_Horizon_È(800000000L, 1.0f).getRGB();
                final int color5 = ColorUtil.HorizonCode_Horizon_È(1000000000L, 1.0f).getRGB();
                final int color6 = ColorUtil.HorizonCode_Horizon_È(1200000000L, 1.0f).getRGB();
                final int color7 = ColorUtil.HorizonCode_Horizon_È(1400000000L, 1.0f).getRGB();
                UIFonts.Ø.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Ø.HorizonCode_Horizon_È("H", 2, 2, color7);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 16, 2, color6);
                UIFonts.Ø.HorizonCode_Horizon_È("r", 30, 2, color5);
                UIFonts.Ø.HorizonCode_Horizon_È("i", 40, 2, color4);
                UIFonts.Ø.HorizonCode_Horizon_È("z", 45, 2, color3);
                UIFonts.Ø.HorizonCode_Horizon_È("o", 57, 2, color2);
                UIFonts.Ø.HorizonCode_Horizon_È("n", 71, 2, color);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.áŒŠÆ.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, color);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, color7);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áˆºÑ¢Õ.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, color7);
            }
        }
        else if (Horizon.Ó.equalsIgnoreCase("classic")) {
            if (Horizon.Âµá€.equals("red")) {
                GL11.glScaled(2.5, 2.5, 2.5);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "Horizon", 2, 4, -723039172);
                GL11.glScaled(0.4, 0.4, 0.4);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "v" + Horizon.Ý, 72, 3, -723039172);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, new StringBuilder().append(Utils.HorizonCode_Horizon_È()).toString(), 4, 35, -723039172);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "FPS: " + Minecraft.Çªà¢, 4, 45, -723039172);
                GL11.glScaled(1.0, 1.0, 1.0);
            }
            if (Horizon.Âµá€.equals("green")) {
                GL11.glScaled(2.5, 2.5, 2.5);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "Horizon", 2, 4, -735596960);
                GL11.glScaled(0.4, 0.4, 0.4);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "v" + Horizon.Ý, 72, 3, -769151392);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, new StringBuilder().append(Utils.HorizonCode_Horizon_È()).toString(), 4, 35, -769151392);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "FPS: " + Minecraft.Çªà¢, 4, 45, -769151392);
                GL11.glScaled(1.0, 1.0, 1.0);
            }
            if (Horizon.Âµá€.equals("blue")) {
                GL11.glScaled(2.5, 2.5, 2.5);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "Horizon", 2, 4, -736714619);
                GL11.glScaled(0.4, 0.4, 0.4);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "v" + Horizon.Ý, 72, 3, -736714619);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, new StringBuilder().append(Utils.HorizonCode_Horizon_È()).toString(), 4, 35, -736714619);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "FPS: " + Minecraft.Çªà¢, 4, 45, -736714619);
                GL11.glScaled(1.0, 1.0, 1.0);
            }
            if (Horizon.Âµá€.equals("orange")) {
                GL11.glScaled(2.5, 2.5, 2.5);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "Horizon", 2, 4, -721442032);
                GL11.glScaled(0.4, 0.4, 0.4);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "v" + Horizon.Ý, 72, 3, -721442032);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, new StringBuilder().append(Utils.HorizonCode_Horizon_È()).toString(), 4, 35, -721442032);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "FPS: " + Minecraft.Çªà¢, 4, 45, -721442032);
                GL11.glScaled(1.0, 1.0, 1.0);
            }
            if (Horizon.Âµá€.equals("magenta")) {
                GL11.glScaled(2.5, 2.5, 2.5);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "Horizon", 2, 4, -724814849);
                GL11.glScaled(0.4, 0.4, 0.4);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "v" + Horizon.Ý, 72, 3, -724814849);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, new StringBuilder().append(Utils.HorizonCode_Horizon_È()).toString(), 4, 35, -724814849);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "FPS: " + Minecraft.Çªà¢, 4, 45, -724814849);
                GL11.glScaled(1.0, 1.0, 1.0);
            }
            if (Horizon.Âµá€.equals("rainbow")) {
                final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                GL11.glScaled(2.5, 2.5, 2.5);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "Horizon", 2, 4, color);
                GL11.glScaled(0.4, 0.4, 0.4);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "v" + Horizon.Ý, 72, 3, color);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, Utils.HorizonCode_Horizon_È(), 4, 35, color);
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "FPS: " + Minecraft.Çªà¢, 4, 45, color);
                GL11.glScaled(1.0, 1.0, 1.0);
            }
        }
        else if (Horizon.Ó.equalsIgnoreCase("pixel")) {
            if (Horizon.Âµá€.equals("red")) {
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 2, 2, -756593604);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -756593604);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -756593604);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -756593604);
            }
            if (Horizon.Âµá€.equals("green")) {
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 2, 2, -769151392);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -769151392);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -769151392);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -769151392);
            }
            if (Horizon.Âµá€.equals("blue")) {
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 2, 2, -770269051);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -770269051);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -770269051);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -770269051);
            }
            if (Horizon.Âµá€.equals("magenta")) {
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 2, 2, -758369281);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -758369281);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -758369281);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -758369281);
            }
            if (Horizon.Âµá€.equals("orange")) {
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 2, 2, -754996464);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, -754996464);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, -754996464);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, -754996464);
            }
            if (Horizon.Âµá€.equals("rainbow")) {
                final int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 3, 3, 1882474846);
                UIFonts.Šáƒ.HorizonCode_Horizon_È("Horizon", 2, 2, color);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 85, 3, 1882474846);
                UIFonts.Ï­Ðƒà.HorizonCode_Horizon_È("v" + Horizon.Ý, 84, 2, color);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 3, 25, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È(Utils.HorizonCode_Horizon_È(), 2, 24, color);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 3, 38, 1882474846);
                UIFonts.áŒŠà.HorizonCode_Horizon_È("FPS: " + Minecraft.Çªà¢, 2, 37, color);
            }
        }
    }
    
    public static void Ý() {
        int y = 0;
        for (final Object e : Minecraft.áŒŠà().á.ÇŽÈ()) {
            final PotionEffect po = (PotionEffect)e;
            final String pot = String.valueOf(po.Ó()) + ", " + po.Â() / 20;
            final String poti = pot.replaceAll("potion.", "");
            final String goodpotio = poti.replaceAll(", ", " §2-§a ");
            final String badpotio = poti.replaceAll(", ", " §4-§c ");
            if (po.HorizonCode_Horizon_È() == 1 || po.HorizonCode_Horizon_È() == 3 || po.HorizonCode_Horizon_È() == 5 || po.HorizonCode_Horizon_È() == 6 || po.HorizonCode_Horizon_È() == 8 || po.HorizonCode_Horizon_È() == 10 || po.HorizonCode_Horizon_È() == 11 || po.HorizonCode_Horizon_È() == 12 || po.HorizonCode_Horizon_È() == 13 || po.HorizonCode_Horizon_È() == 14 || po.HorizonCode_Horizon_È() == 16 || po.HorizonCode_Horizon_È() == 21 || po.HorizonCode_Horizon_È() == 22 || po.HorizonCode_Horizon_È() == 23) {
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "§a" + goodpotio, 5, 70 + y, -1);
            }
            else {
                Gui_1808253012.Â(Minecraft.áŒŠà().µà, "§c" + badpotio, 5, 70 + y, -1);
            }
            y += 10;
        }
    }
}
