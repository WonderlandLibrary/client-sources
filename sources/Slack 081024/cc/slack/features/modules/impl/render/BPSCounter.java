package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.start.Slack;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;

import static java.lang.Math.round;

@ModuleInfo(
        name = "BPSCounter",
        category = Category.RENDER
)
public class BPSCounter extends Module {

    private final ModeValue<String> backgroundMode = new ModeValue<>("Background", new String[]{"Smart", "Custom"});
    private final BooleanValue rounded = new BooleanValue("Rounded", true);
    public final BooleanValue resetPos = new BooleanValue("Reset Position", false);
    private final NumberValue<Integer> widthValue = new NumberValue<>("Custom Background Width", 50, 10, 100, 1);
    private final NumberValue<Integer> heightValue = new NumberValue<>("Custom Background Height", 18, 5, 100, 1);
    private final NumberValue<Integer> radiusValue = new NumberValue<>("Custom Background Rounded Radius", 6, 0 , 20, 1);

    private double posX = -1D;
    private double posY = -1D;
    private int x = 0;
    private int y = 0;

    private boolean dragging = false;
    private double dragX = 0, dragY = 0;

    public BPSCounter() {
        addSettings(backgroundMode, rounded, resetPos, widthValue, heightValue, radiusValue);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onRender(RenderEvent event) {
        if (resetPos.getValue()) {
            posX = -1D;
            posY = -1D;
            Slack.getInstance().getModuleManager().getInstance(BPSCounter.class).resetPos.setValue(false);
        }

        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (posX == -1 || posY == -1) {
            posX = sr.getScaledWidth() / 2.0 + 90;
            posY = sr.getScaledHeight() / 2.0 - 20;
        }

        int mouseX = Mouse.getX() * sr.getScaledWidth() / mc.displayWidth;
        int mouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / mc.displayHeight - 1;

        if (dragging) {
            posX = mouseX - dragX;
            posY = mouseY - dragY;
        }

        x = (int) posX;
        y = (int) posY;

        String fpsText = "BPS: ";
        String fpsValue = "" + getBPS();

        int fpsTextWidth = Fonts.sfRoundedBold20.getStringWidth(fpsText);
        int fpsValueWidth = Fonts.sfRoundedRegular20.getStringWidth(fpsValue);
        int totalTextWidth = fpsTextWidth + fpsValueWidth;
        int textHeight = Fonts.sfRoundedBold20.getHeight();

        int rectWidth, rectHeight;
        int textX, textY;

        if (backgroundMode.getValue().equals("Smart")) {
            rectWidth = totalTextWidth + 10;
            rectHeight = textHeight + 12;
        } else {
            rectWidth = widthValue.getValue();
            rectHeight = heightValue.getValue();
        }

        int rectX = x + 170;
        int rectY = y + 58;

        textX = rectX + (rectWidth - totalTextWidth) / 2;
        textY = rectY + (rectHeight - textHeight) / 2;

        int cornerRadius = radiusValue.getValue();

        if (rounded.getValue()) {
            drawRoundedRect(rectX, rectY, rectWidth, rectHeight, cornerRadius, new Color(0, 0, 0, 150).getRGB());
        } else {
            drawRect(rectX, rectY, rectWidth, rectHeight, new Color(0, 0, 0, 150).getRGB());
        }

        Fonts.sfRoundedBold18.drawStringWithShadow(fpsText, textX, textY, ColorUtil.getColor(Slack.getInstance().getModuleManager().getInstance(Interface.class).theme.getValue(), 0.15).getRGB());
        Fonts.sfRoundedRegular20.drawStringWithShadow(fpsValue, textX + fpsTextWidth, textY, -1);

        handleMouseInput(mouseX, mouseY, rectX, rectY, rectWidth, rectHeight);
    }

    private void handleMouseInput(int mouseX, int mouseY, int rectX, int rectY, int rectWidth, int rectHeight) {
        if (Mouse.isButtonDown(0) && mc.currentScreen instanceof GuiChat) {
            if (!dragging) {
                if (mouseX >= rectX && mouseX <= rectX + rectWidth &&
                        mouseY >= rectY && mouseY <= rectY + rectHeight) {
                    dragging = true;
                    dragX = mouseX - posX;
                    dragY = mouseY - posY;
                }
            }
        } else {
            dragging = false;
        }
    }

    private void drawRoundedRect(float x, float y, float width, float height, float radius, int color) {
        RenderUtil.drawRoundedRect(x, y, x + width, y + height, radius, color);
    }

    private void drawRect(int x, int y, int width, int height, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }

    private String getBPS() {
        double currentBPS = ((double) round((MovementUtil.getSpeed() * 20) * 100)) / 100;
        return String.format("%.2f", currentBPS);
    }
}
