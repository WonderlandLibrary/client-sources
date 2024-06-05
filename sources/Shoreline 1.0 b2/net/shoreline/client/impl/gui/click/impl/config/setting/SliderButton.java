package net.shoreline.client.impl.gui.click.impl.config.setting;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.render.RenderManager;
import net.shoreline.client.impl.gui.click.ClickGuiScreen;
import net.shoreline.client.impl.gui.click.impl.config.CategoryFrame;
import net.shoreline.client.init.Modules;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @param <T>
 */
public class SliderButton<T extends Number> extends ConfigButton<T>
{
    // Slider rounding scale
    private final int scale;

    /**
     *
     *
     * @param frame
     * @param config
     */
    public SliderButton(CategoryFrame frame, Config<T> config, float x, float y)
    {
        super(frame, config, x, y);
        //
        final String sval = String.valueOf(config.getValue());
        scale = sval.substring(sval.indexOf(".") + 1).length();
    }

    /**
     *
     *
     * @param matrices
     * @param ix
     * @param iy
     * @param mouseX
     * @param mouseY
     * @param delta
     */
    @Override
    public void render(MatrixStack matrices, float ix, float iy, float mouseX,
                       float mouseY, float delta)
    {
        x = ix;
        y = iy;
        //
        Number min = ((NumberConfig<T>) config).getMin();
        Number max = ((NumberConfig<T>) config).getMax();
        if (isWithin(mouseX, mouseY) && ClickGuiScreen.MOUSE_LEFT_HOLD)
        {
            float fillv = (mouseX - ix) / width;
            if (config.getValue() instanceof Integer)
            {
                float val = min.floatValue() + fillv * (max.intValue() - min.intValue());
                int bval = (int) MathHelper.clamp(val, min.intValue(), max.intValue());
                ((NumberConfig<Integer>) config).setValue(bval);
            }
            else if (config.getValue() instanceof Float)
            {
                float val = min.floatValue() + fillv * (max.floatValue() - min.floatValue());
                float bval = MathHelper.clamp(val, min.floatValue(),
                        max.floatValue());
                BigDecimal bigDecimal = new BigDecimal(bval);
                bval = bigDecimal.setScale(scale, RoundingMode.HALF_UP).floatValue();
                ((NumberConfig<Float>) config).setValue(bval);
            }
            else if (config.getValue() instanceof Double)
            {
                double val = min.doubleValue() + fillv * (max.doubleValue() - min.doubleValue());
                double bval = MathHelper.clamp(val, min.doubleValue(),
                        max.doubleValue());
                BigDecimal bigDecimal = new BigDecimal(bval);
                bval = bigDecimal.setScale(scale, RoundingMode.HALF_UP).doubleValue();
                ((NumberConfig<Double>) config).setValue(bval);
            }
            float lower = ix + 1.0f;
            float upper = ix + width - 1.0f;
            // out of bounds
            if (mouseX < lower)
            {
                config.setValue((T) min);
            }
            else if (mouseX > upper)
            {
                config.setValue((T) max);
            }
        }
        // slider fill
        float fill = (config.getValue().floatValue() - min.floatValue())
                / (max.floatValue() - min.floatValue());
        fill(matrices, ix, iy, (fill * width), height, Modules.COLORS.getRGB());
        RenderManager.renderText(matrices, config.getName() + Formatting.GRAY
                        + " " + config.getValue(), ix + 2.0f, iy + 4.0f, -1);
    }

    /**
     * @param mouseX
     * @param mouseY
     * @param button
     */
    @Override
    public void mouseClicked(double mouseX, double mouseY, int button)
    {

    }

    /**
     * @param mouseX
     * @param mouseY
     * @param button
     */
    @Override
    public void mouseReleased(double mouseX, double mouseY, int button)
    {

    }

    /**
     * @param keyCode
     * @param scanCode
     * @param modifiers
     */
    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers)
    {

    }
}
