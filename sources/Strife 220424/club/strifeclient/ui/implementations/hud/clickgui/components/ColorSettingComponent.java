package club.strifeclient.ui.implementations.hud.clickgui.components;

import club.strifeclient.setting.implementations.ColorSetting;
import club.strifeclient.ui.implementations.hud.clickgui.Component;
import club.strifeclient.ui.implementations.hud.clickgui.ExtendableComponent;
import club.strifeclient.ui.implementations.hud.clickgui.Theme;
import club.strifeclient.util.rendering.DrawUtil;
import club.strifeclient.util.rendering.RenderUtil;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ColorSettingComponent extends SettingComponent<ColorSetting> {

    private boolean pickerSliding, hueSliding, opacitySliding;
    private float hue, saturation, brightness, opacity;
    private ResourceLocation opacityImage;
    private long start;
    private float[] hsb;

    public ColorSettingComponent(ColorSetting object, Theme theme, Component<?> parent, float x, float y, float width, float height) {
        super(object, theme, parent, x, y, width, height);
        if(parent instanceof ExtendableComponent<?>) {
            ExtendableComponent<?> extendableParent = (ExtendableComponent<?>) parent;
            extendableParent.addExtendCallback(extended -> {
                visible = extended;
                start = System.currentTimeMillis();
            });
        }
        object.addChangeCallback((old, value) -> setColors());
        if(hsb == null)
            setColors();
        opacityImage = new ResourceLocation("strife/gui/clickgui/opacity_slider.png");
    }

    private void setColors() {
        final Color color = object.getValue();
        hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hue = hsb[0];
        saturation = hsb[1];
        brightness = hsb[2];
        opacity = object.getOpacity();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float startX = x + 3;
        float startY = y + 2;
        float pickerHeight = height - 7;
        float pickerWidth = width - 28;
        float selectorWidth = 4;
        float selectorHeight = 4;
        float hueHeight = MathHelper.clamp_float(hsb[0] * pickerHeight, selectorWidth / 2, pickerHeight - selectorWidth / 2);
        float opacityHeight = MathHelper.clamp_float(opacity * pickerHeight, selectorWidth / 2, pickerHeight - selectorWidth / 2);
        float saturationWidth = MathHelper.clamp_float(hsb[1] * pickerWidth, -selectorWidth, pickerWidth - selectorWidth);
        float brightnessHeight = MathHelper.clamp_float(hsb[2] * pickerHeight, selectorHeight, pickerHeight + selectorHeight);
        if (pickerSliding) {
            saturation = MathHelper.clamp_float((mouseX - startX) / pickerWidth, 0, 1);
            brightness = MathHelper.clamp_float(1 - ((mouseY - startY) / pickerHeight), 0, 1);
            saturationWidth = MathHelper.clamp_float(saturation * pickerWidth, -selectorWidth, pickerWidth - selectorWidth);
            brightnessHeight = MathHelper.clamp_float(brightness * pickerHeight, selectorHeight, pickerHeight + selectorHeight);
            object.setValue(new Color(Color.HSBtoRGB(hue, MathHelper.clamp_float(saturation, 0.01f, 0.999f), MathHelper.clamp_float(brightness, 0.01f, 0.999f))));
        } else if(hueSliding) {
            hue = MathHelper.clamp_float((mouseY - startY) / pickerHeight, 0, 1);
            hueHeight = MathHelper.clamp_float(hue * pickerHeight, selectorWidth / 2, pickerHeight - selectorWidth / 2);
            object.setValue(new Color(Color.HSBtoRGB(MathHelper.clamp_float(hue, 0.01f, 0.999f), saturation, brightness)));
        } else if (opacitySliding) {
            opacity = MathHelper.clamp_float((mouseY - startY) / pickerHeight, 0, 1);
            opacityHeight = MathHelper.clamp_float(opacity * pickerHeight, selectorWidth / 2, pickerHeight - selectorWidth / 2);
            object.setValue(new Color(object.getValue().getRed(), object.getValue().getGreen(), object.getValue().getBlue(), 255 - (int)(opacity * 255)));
        }
        theme.drawColorSetting(object, startX, startY, pickerWidth, pickerHeight, width, origHeight, partialTicks);
        for(int i = 0; i < pickerHeight; i++) {
            float yPos = startY + i;
            DrawUtil.drawRect(x + width - 23, yPos, x + width - 14, yPos + 1, Color.getHSBColor(i / pickerHeight, 1, 1));
        }
        // opacity slider
//        DrawUtil.drawRect(x + width - 12, startY + hueHeight - selectorHeight / 2, x + width - 3, startY + hueHeight + selectorHeight / 2, -1);

        RenderUtil.drawImage(opacityImage, x + width - 12, startY, 9, pickerHeight);
        DrawUtil.drawGradientRect(x + width - 12, startY, x + width - 3, startY + pickerHeight, false, false, new Color(1, 1, 1, 0), new Color(1, 1, 1));
        // picker
        DrawUtil.drawCenteredPoint(startX + saturationWidth, startY + pickerHeight - brightnessHeight, 4,
                4, 10, Color.WHITE);
        // hue slider
//        DrawUtil.drawRect(x + width - 23, startY + hueHeight - selectorHeight / 2, x + width - 14, startY + hueHeight + selectorHeight / 2, -1);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int button) {
        pickerSliding = false;
        hueSliding = false;
        opacitySliding = false;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        float startX = x + 3;
        float startY = y + 2;
        float pickerHeight = height - 7;
        float pickerWidth = width - 28;
        if (!pickerSliding && button == 0 && RenderUtil.isHovered(startX, startY, pickerWidth, pickerHeight, mouseX, mouseY)) {
            pickerSliding = true;
            hueSliding = false;
            opacitySliding = false;
        }
        if (!hueSliding && button == 0 && RenderUtil.isHovered(x + width - 23, startY, 9, pickerHeight, mouseX, mouseY)) {
            hueSliding = true;
            pickerSliding = false;
            opacitySliding = false;
        }
        if (!opacitySliding && button == 0 && RenderUtil.isHovered(x + width - 12, startY, 8, pickerHeight, mouseX, mouseY)) {
            opacitySliding = true;
            hueSliding = false;
            pickerSliding = false;
        }
    }

    @Override
    public void onGuiClosed() {
        mouseReleased(0, 0, 0);
    }

    @Override
    public void initGui() {
        start = System.currentTimeMillis();
    }
}
