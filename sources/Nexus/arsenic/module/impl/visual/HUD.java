package arsenic.module.impl.visual;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventRender2D;
import arsenic.injection.accessor.IMixinMinecraft;
import arsenic.injection.accessor.IMixinTimer;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.impl.BooleanProperty;
import arsenic.module.property.impl.EnumProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.font.FontRendererExtension;
import arsenic.utils.java.ColorUtils;
import arsenic.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@ModuleInfo(name = "HUD", category = ModuleCategory.Visual, keybind = Keyboard.KEY_U)
public class HUD extends Module {

    public final EnumProperty<hMode> colorMode = new EnumProperty<>("Color Mode: ", hMode.RAINBOW);
    public final BooleanProperty watermark = new BooleanProperty("Watermark", true);
    public final DoubleProperty opacity = new DoubleProperty("Opacity", new DoubleValue(0, 255, 100, 1));
    public final BooleanProperty backbar = new BooleanProperty("BackBar", true);
    public final BooleanProperty frontbar = new BooleanProperty("FrontBar", true);
    public final BooleanProperty info = new BooleanProperty("Info", true);
    DecimalFormat decimalFormat = new DecimalFormat("0.0");
    int offsetY = 6;
    @EventLink
    public final Listener<EventRender2D> onRender2D = event -> {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            return;
        }

        ScaledResolution sr = new ScaledResolution(mc);
        FontRendererExtension<?> fr = Nexus.getNexus().getClickGuiScreen().getFontRenderer();

        if (fr == null) {
            return;
        }


        int noDelayColor = colorMode.getValue().getColor(4, 0);
        if (watermark.getValue()) {
            fr.drawStringWithShadow("N" + EnumChatFormatting.WHITE + "exus", 4, 4, noDelayColor);
        }

        if (info.getValue()) {
            //this should actually be at the bottom also the tps is broken
            double y = sr.getScaledHeight();
            double tps = ((IMixinTimer) ((IMixinMinecraft) mc).getTimer()).getTicksPerSecond();
            String bps = String.valueOf(decimalFormat.format(Math.hypot(mc.thePlayer.posX - mc.thePlayer.lastTickPosX, mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * 20.0F * ((IMixinMinecraft) mc).getTimer().timerSpeed));
            fr.drawStringWithShadow("FPS " + EnumChatFormatting.WHITE + Minecraft.getDebugFPS(), 1, (float) y - fr.getHeight("FPS") * 2, noDelayColor);
            fr.drawStringWithShadow("BPS " + EnumChatFormatting.WHITE + (bps), 1, (float) y - fr.getHeight("BPS") * 1, noDelayColor);
        }

        float x = sr.getScaledWidth();

        //sorts it in order of length
        List<ModuleRenderInfo> nameList =
                Nexus.getNexus().getModuleManager().getEnabledModules()
                        .stream().filter(module -> !isHidden())
                        .map(module -> new ModuleRenderInfo(fr.getWidth(module.getName()), module.getName()))
                        .sorted(Comparator.comparingDouble(ri -> -ri.length)).collect(Collectors.toList());

        int i = 0;
        for (ModuleRenderInfo m : nameList) {
            offsetY += 11;
            float mX = x - m.length;
            RenderUtils.resetColorText();
            int color = colorMode.getValue().getColor(4, i * 20);
            Gui.drawRect((int) x, i, (int) mX - 6, 10 + i, new Color(0, 0, 0, (int) opacity.getValue().getInput()).getRGB());
            fr.drawStringWithShadow(m.name, mX - 3, i + 1, color);

            if (backbar.getValue()) {
                Gui.drawRect((int) x, i, (int) x - 1, 10 + i, color);
            }

            if (frontbar.getValue()) {
                Gui.drawRect((int) mX - 6, i, (int) mX - 7, 10 + i, color);
            }

            GlStateManager.resetColor();

            i += 10;
        }
    };

    private static class ModuleRenderInfo {

        public final float length;
        public final String name;

        public ModuleRenderInfo(float length, String name) {
            this.length = length;
            this.name = name;
        }
    }

    public enum hMode {
        THEME(ColorUtils::getThemeRainbowColor),
        ASTOLFO(ColorUtils::getAstolfo),
        RAINBOW(ColorUtils::getRainbow);

        private final BinaryOperator<Integer> f;

        hMode(BinaryOperator<Integer> f) {
            this.f = f;
        }

        public int getColor(int speed, int delay) {
            return f.apply(speed, delay);
        }
    }
}