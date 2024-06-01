package best.actinium.module.impl.visual;

import best.actinium.Actinium;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.render.BloomEvent;
import best.actinium.event.impl.render.BlurEvent;
import best.actinium.event.impl.render.Render2DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.module.impl.movement.SprintModule;
import best.actinium.property.impl.*;
import best.actinium.util.IAccess;
import best.actinium.util.packet.NetworkUtil;
import best.actinium.util.render.ChatUtil;
import best.actinium.util.render.animations.Animation;
import best.actinium.util.render.animations.Direction;
import best.actinium.util.render.ColorUtil;
import best.actinium.util.render.drag.DragManager;
import best.actinium.util.render.drag.DragUtil;
import best.actinium.util.render.drag.Draggable;
import best.actinium.util.render.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Formatting;
import org.lwjglx.util.vector.Vector2f;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

@ModuleInfo(
        name = "HUD",
        description = "Hud",
        category = ModuleCategory.VISUAL,
        autoEnabled = true
)
public class HudModule extends Module {
    private InputProperty input = new InputProperty("Client Name", this, Actinium.NAME);
    private ModeProperty watermarkMode = new ModeProperty("Watermark Mode", this, new String[]{"None", "Basic", "Best","Modern", "Old Reality"}, "Basic");
    private BooleanProperty sprintStatus = new BooleanProperty("Show Sprint Status", this, false);
    private BooleanProperty ping = new BooleanProperty("Show Ping", this, false).setHidden(() -> !watermarkMode.is("Best")),
            time = new BooleanProperty("Time Ping", this, false).setHidden(() -> !watermarkMode.is("Best")),
            fps = new BooleanProperty("Show Fps", this, false).setHidden(() -> !watermarkMode.is("Best")),
            protocol = new BooleanProperty("Show Protocol", this, false).setHidden(() -> !watermarkMode.is("Best"));
    private NumberProperty offsetY = new NumberProperty("Y Offset", this, 0f, 4f, 10f, 0.1f);
    private BooleanProperty space = new BooleanProperty("No Space", this, false);
    private BooleanProperty sideBar = new BooleanProperty("Side Bar", this, false);
    private BooleanProperty backGround = new BooleanProperty("Back Ground", this, true);
    private NumberProperty backGroundAlpha = new NumberProperty("Back Ground Alpha", this, 0, 100, 255, 1);
    public ModeProperty color = new ModeProperty("Arraylist Color", this, new String[]{"Blend", "Fade", "Old"}, "Blend");
    public ColorProperty color1 = new ColorProperty("First Color", this, Color.white).setHidden(() -> !color.is("Blend") && !color.is("Fade")),
            color2 = new ColorProperty("Second Color", this, Color.white).setHidden(() -> !color.is("Blend"));
    private BooleanProperty suffix = new BooleanProperty("Suffix", this, true);
    public ModeProperty sufiixColor = new ModeProperty("Suffix Color", this, new String[]{"Gray", "White", "None"}, "Gray")
            .setHidden(() -> !suffix.isEnabled());
    public ModeProperty sufiixSign = new ModeProperty("Suffix Sign", this, new String[]{"Space", "Dash"}, "Space")
            .setHidden(() -> !suffix.isEnabled());
    private FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
    private int textColor;

    @Callback
    public void onRender2d(Render2DEvent event) {
        if (sprintStatus.isEnabled()) {
            mc.fontRendererObj.drawStringWithShadow(Actinium.INSTANCE.getModuleManager().get(SprintModule.class).isEnabled() ? "[Sprinting (Toggled)]" : "", 3, 3, -1);
        }

        drawWatermark();
        drawArraylist(false);
    }

    @Callback
    public void onBlur(BlurEvent event) {
        ScaledResolution resolution = new ScaledResolution(IAccess.mc);
        int index = 0;

        List<Module> sortedModules = getSortedVisibleModules();

        for (Module module : sortedModules) {
            if (!module.isVisible()) continue;

            String moduleName = module.getDisplayName(space.isEnabled());
            int moduleWidth = font.getStringWidth(moduleName) + 4;
            float bgX = resolution.getScaledWidth() - moduleWidth - 2;
            float bgY = 2 + index * (font.FONT_HEIGHT + offsetY.getValue().floatValue());

            if (backGround.isEnabled()) {
                Gui.drawRect(bgX, bgY, bgX + moduleWidth, bgY + font.FONT_HEIGHT + offsetY.getValue().floatValue(), Color.white.getRGB());
            }

            index++;
        }
    }

    @Callback
    public void onBloom(BloomEvent event) {
        drawArraylist(true);
        drawWatermark();

        if (sprintStatus.isEnabled()) {
            mc.fontRendererObj.drawStringWithShadow(Actinium.INSTANCE.getModuleManager().get(SprintModule.class).isEnabled() ? "[Sprinting (Toggled)]" : "", 3, 3, -1);
        }
    }

    /* functions */
    private int getColor(int index, Module module) {
        return color.is("Blend") ? ColorUtil.interpolateColorsBackAndForth(15, 3 + (index * 20), color1.getColor(),
                color2.getColor(), false).getRGB() : color.is("Old") ? module.getCategoryColor() :
                ColorUtil.interpolateColorsBackAndForth(15, 3 + (index * 20), color1.getColor(),
                        color1.getColor().darker().darker().darker(), false).getRGB();
    }

    private List<Module> getSortedVisibleModules() {
        return Actinium.INSTANCE.getModuleManager().stream()
                .filter(Module::isEnabled)
                .filter(Module::isVisible)
                .sorted(Comparator.comparingDouble(module -> -font.getStringWidth(module.getDisplayName(space.isEnabled()))))
                .toList();
    }

    private void drawArraylist(boolean bloom) {
        ScaledResolution resolution = new ScaledResolution(IAccess.mc);
        int index = 0;
        //maybe fix render order and todo: 3 of the same variables but me lazy

        List<Module> sortedModules = getSortedVisibleModules();

        for (Module module : sortedModules) {
            final Animation moduleAnimation = module.getAnimation();

            moduleAnimation.setDirection(module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);

            if (!module.isEnabled() && moduleAnimation.finished(Direction.BACKWARDS)) continue;
            textColor = getColor(index, module);

            if (!module.isVisible()) continue;

            double yOffset = 0;

            String moduleName = module.getDisplayName(space.isEnabled());
            int moduleWidth = font.getStringWidth(moduleName) + 4;
            float bgX = resolution.getScaledWidth() - moduleWidth - 2;
            float bgY = 2 + index * (font.FONT_HEIGHT + offsetY.getValue().floatValue());
            float x = bgX + 2;
            float y = (float) (yOffset + bgY + 1);

            double xValue = resolution.getScaledWidth() - (moduleAnimation.getOutput().floatValue() * moduleWidth);
            boolean flip = xValue <= resolution.getScaledWidth() / 2f;
            if (flip) {
                x -= Math.abs((moduleAnimation.getOutput().floatValue() - 1) * (resolution.getScaledWidth() - moduleWidth - xValue));
            } else {
                x += Math.abs((moduleAnimation.getOutput().floatValue() - 1) * (x - moduleWidth));
            }

            if (sideBar.isEnabled()) {
                Gui.drawRect(958, bgY - 1, bgX + moduleWidth + 2, bgY + font.FONT_HEIGHT + offsetY.getValue().floatValue() , textColor);
            }

            //todo: fix the weird ass shadow
            if (backGround.isEnabled()) {
                Gui.drawRect(bgX, bgY, bgX + moduleWidth, bgY + font.FONT_HEIGHT + offsetY.getValue().floatValue(), bloom ? Color.BLACK.getRGB() : new Color(0,0,0, backGroundAlpha.getValue().intValue()).getRGB());
            }

            //if (!backGround.isEnabled()) {
                font.drawStringWithShadow(moduleName, x, y, textColor);
         //   }

            index++;
        }
    }

    private void drawWatermark() {
        float charX = 6.0F;

        switch (watermarkMode.getMode()) {
            case "Basic":
                for (char i : (input.getInput()).toCharArray()) {
                    String string = String.valueOf(i);
                    font.drawStringWithShadow(
                            string,
                            charX, 5,
                            ColorUtil.getAccentColor(new Vector2f(charX * 32, 6), color1.getColor(), color2.getColor()).getRGB()
                    );
                    charX += this.font.getStringWidth(string) + 0.25F;
                }
                break;
            case "Best":
                StringBuilder builder = new StringBuilder();

                builder.append(!input.getInput().isEmpty() ? input.getInput().substring(0, 1) : "")
                        .append(Formatting.WHITE)
                        .append(input.getInput().length() > 1 ? input.getInput().substring(1) : "")
                        .append(fps.isEnabled() ? Formatting.GRAY + " [" + Formatting.WHITE + Minecraft.getDebugFPS() + Formatting.GRAY + "]" : "")
                        .append(ping.isEnabled() ? Formatting.GRAY + " [" + Formatting.WHITE + NetworkUtil.getPing() + " ms" + Formatting.GRAY + "]" : "")
                        .append(time.isEnabled() ? Formatting.GRAY + " [" + Formatting.WHITE + "5 am" + Formatting.GRAY + "]" : "")
                        .append(protocol.isEnabled() ? Formatting.GRAY + " [" + Formatting.WHITE + (Minecraft.closeTimer.getTime() <= 1000 ? "Connect" : Minecraft.closeTimer.getTime() / 1000 + " Attempting To Reconnect") + Formatting.GRAY + "]" : "");

                font.drawStringWithShadow(builder.toString(), 3, 3, ColorUtil.interpolateColorsBackAndForth(15, 3 + (0 * 20), color1.getColor(), color2.getColor(), false).getRGB());
                break;
            case "Modern":
                for (char i : (input.getInput()).toCharArray()) {
                    String string = String.valueOf(i);
                    FontUtil.productWatermark.drawString(
                            string,
                            charX, 3,
                            ColorUtil.getAccentColor(new Vector2f(charX * 32, 6), color1.getColor(), color2.getColor()).getRGB()
                    );
                    charX += FontUtil.productWatermark.getStringWidth(string) + 0.25F;
                }
                break;
            case "Old Reality":
                Gui.drawRect(2, 5, 100, 23, new Color(0, 0, 0, 100).getRGB());
                font.drawStringWithShadow(Actinium.NAME, 5, 5, -1);
                break;
        }
    }
}
