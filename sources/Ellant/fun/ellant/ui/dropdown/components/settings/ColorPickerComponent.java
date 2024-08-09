package fun.ellant.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.functions.settings.impl.ColorSetting;
import fun.ellant.ui.dropdown.impl.Component;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;

import java.awt.*;

@SuppressWarnings("all")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColorPickerComponent extends Component {

    final ColorSetting colorSetting;

    float colorRectX, colorRectY, colorRectWidth, colorRectHeight;
    float pickerX, pickerY, pickerWidth, pickerHeight;
    float sliderX, sliderY, sliderWidth, sliderHeight;

    final float padding = 5;
    float textX, textY;
    final int textColor = ColorUtils.rgb(160, 163, 175);
    private float[] hsb = new float[2];

    boolean panelOpened;
    boolean draggingHue, draggingPicker;

    public ColorPickerComponent(ColorSetting colorSetting) {
        this.colorSetting = colorSetting;
        hsb = Color.RGBtoHSB(
                ColorUtils.IntColor.getRed(colorSetting.get()),
                ColorUtils.IntColor.getGreen(colorSetting.get()),
                ColorUtils.IntColor.getBlue(colorSetting.get()), null
        );
        setHeight(22);
    }


    @Override
    public void render(MatrixStack stack, float mouseX, float mouseY) {
        renderTextAndColorRect(stack);

        if (panelOpened) {
            this.colorSetting.set(Color.getHSBColor(hsb[0], hsb[1], hsb[2]).getRGB());
            renderSlider(mouseX, mouseY);
            renderPickerPanel(mouseX, mouseY);
            setHeight(26 + pickerHeight + padding);
        } else {
            setHeight(22);
        }

        super.render(stack, mouseX, mouseY);
    }

    private void renderTextAndColorRect(MatrixStack stack) {
        String settingName = colorSetting.getName();
        int colorValue = colorSetting.get();

        this.textX = this.getX() + padding;
        this.textY = this.getY() + 2;

        this.colorRectX = this.getX() + padding;
        this.colorRectY = this.getY() + 4 + (padding);
        this.colorRectWidth = this.getWidth() - (padding * 2);
        this.colorRectHeight = padding * 2;

        this.pickerX = this.getX() + padding;
        this.pickerY = this.getY() + 4 + (padding) + 16;
        this.pickerWidth = this.getWidth() - (padding * 4);
        this.pickerHeight = 60;

        this.sliderX = pickerX + pickerWidth + padding;
        this.sliderY = pickerY;
        this.sliderWidth = 3;
        this.sliderHeight = pickerHeight;


        Fonts.montserrat.drawText(stack, settingName, textX, textY, textColor, 5.5f, 0.05f);
        DisplayUtils.drawRoundedRect(this.colorRectX, this.colorRectY, this.colorRectWidth, this.colorRectHeight, 3.5f, colorValue);
    }

    private void renderPickerPanel(float mouseX, float mouseY) {
        Vector4i vector4i = new Vector4i(Color.WHITE.getRGB(),
                Color.BLACK.getRGB(),
                Color.getHSBColor(hsb[0], 1, 1).getRGB(),
                Color.BLACK.getRGB());

        float offset = 4;
        float xRange = pickerWidth - 8;
        float yRange = pickerHeight - 8;

        if (draggingPicker) {
            float saturation = MathHelper.clamp((mouseX - pickerX - offset), 0, xRange) / (xRange);
            float brightness = MathHelper.clamp((mouseY - pickerY - offset), 0, yRange) / (yRange);
            hsb[1] = saturation;
            hsb[2] = 1 - brightness;
        }

        DisplayUtils.drawRoundedRect(this.pickerX, this.pickerY, this.pickerWidth, this.pickerHeight, new Vector4f(6, 6, 6, 6), vector4i);

        float circleX = pickerX + offset + hsb[1] * (xRange);
        float circleY = pickerY + offset + (1 - hsb[2]) * (yRange);

        DisplayUtils.drawCircle(circleX, circleY, 8, Color.BLACK.getRGB());
        DisplayUtils.drawCircle(circleX, circleY, 6, Color.WHITE.getRGB());
    }


    private void renderSlider(float mouseX, float mouseY) {
        for (int i = 0; i < sliderHeight; i++) {
            float hue = i / sliderHeight;
            DisplayUtils.drawCircle(this.sliderX + 1f, sliderY + i, 3, Color.HSBtoRGB(hue, 1, 1));

        }
        DisplayUtils.drawCircle(this.sliderX + sliderWidth - 2F, this.sliderY + (hsb[0] * sliderHeight), 8, Color.BLACK.getRGB());
        DisplayUtils.drawCircle(this.sliderX + sliderWidth - 2F, this.sliderY + (hsb[0] * sliderHeight), 6, -1);
        if (draggingHue) {
            float hue = (mouseY - sliderY) / sliderHeight;
            hsb[0] = MathHelper.clamp(hue, 0,1);
        }
    }

    @Override
    public void mouseClick(float mouseX, float mouseY, int mouse) {
        if (MathUtil.isInRegion(mouseX, mouseY, colorRectX, colorRectY, colorRectWidth, colorRectHeight) && mouse == 1) {
            panelOpened = !panelOpened;
        }

        if (panelOpened) {
            if (MathUtil.isInRegion(mouseX, mouseY, sliderX - 2, sliderY, sliderWidth + 4, pickerHeight - 12)) {
                draggingHue = true;
            } else if (MathUtil.isInRegion(mouseX, mouseY, pickerX, pickerY, pickerWidth, pickerHeight)) {
                draggingPicker = true;
            }
        }

        super.mouseClick(mouseX, mouseY, mouse);
    }


    @Override
    public void mouseRelease(float mouseX, float mouseY, int mouse) {
        if (draggingHue) {
            draggingHue = false;
        }
        if (draggingPicker) {
            draggingPicker = false;
        }
        super.mouseRelease(mouseX, mouseY, mouse);
    }
}
