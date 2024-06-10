package me.sleepyfish.smok.rats.impl.visual;

import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.Rat;
import me.sleepyfish.smok.utils.entities.capes.Cape;
import me.sleepyfish.smok.utils.font.FontUtils;
import me.sleepyfish.smok.utils.misc.MouseUtils;
import me.sleepyfish.smok.utils.misc.ClientUtils;
import me.sleepyfish.smok.utils.render.RenderUtils;
import me.sleepyfish.smok.utils.render.RoundedUtils;
import me.sleepyfish.smok.utils.render.color.ColorUtils;
import net.minecraft.client.renderer.GlStateManager;

// Class from SMok Client by SleepyFish
public class Cosmetics_Gui extends Rat {

    public static float guiX =  0;
    public static float guiY =  0;
    public static float guiX2 = 0;
    public static float guiY2 = 0;

    private static int height = 0;

    public Cosmetics_Gui() {
        super("Cosmetics", Category.Visuals, "Draws a cosmetics gui");
    }

    @Override
    public void onEnableEvent() {
        mc.displayGuiScreen(Smok.inst.guiManager.getCapeGui());
        this.toggle();
    }

    public static void render() {
        if (ClientUtils.inClickGui()) {
            int count = 0;

            if (Gui.renderShadows.isEnabled()) {
                RenderUtils.drawShadow(guiX, guiY, 200, 120, 1F);
            }

            RoundedUtils.drawRoundOutline(guiX, guiY, 200, 120, 1F, 2.62F, ColorUtils.getBackgroundColor(4), ColorUtils.getBackgroundColor(4).darker());

            FontUtils.i20.drawString("7", guiX + 2, guiY + 4, ColorUtils.getFontColor(1));
            FontUtils.r20.drawStringWithClientColor("Cosmetics", guiX + 18, guiY + 3, true);

            for (Cape cape : Smok.inst.capeManager.getCapes()) {
                FontUtils.r20.drawString(cape.getName(), guiX + 44 - (FontUtils.r20.getStringWidth(cape.getName()) / 2F), guiY + (count * 16F) + 18, ColorUtils.getFontColor(1));
                count++;
            }

            if (Smok.inst.capeManager.getCurrentCape() != null) {
                Smok.inst.mc.getTextureManager().bindTexture(Smok.inst.capeManager.getCurrentCape().getPreview());
                GlStateManager.enableBlend();
                RenderUtils.drawRoundTextured(guiX + 150, guiY + 45, 44, 70, 4, 1.0F);
                GlStateManager.disableBlend();
            }
        }
    }

    public static void clickMouse(int x, int y, int b) {
        int count = 0;

        if (ClientUtils.inClickGui() && b == 0) {
            for (Cape cape : Smok.inst.capeManager.getCapes()) {
                if (cape != null) {
                    if (MouseUtils.isInside(x, y, guiX + 5, guiY + (count * 16F) + 18, FontUtils.r20.getStringWidth(cape.getName()), 10)) {
                        Smok.inst.capeManager.setCurrentCape(cape);
                    }

                    count++;
                }
            }
        }
    }

    public static void movementOnTop(int x, int y) {
        if (Smok.inst.guiManager.getClickGui().isCosmeticsGuiMoving() && ClientUtils.inClickGui()) {
            guiX = x - guiX2;
            guiY = y - guiY2;
        }
    }

}