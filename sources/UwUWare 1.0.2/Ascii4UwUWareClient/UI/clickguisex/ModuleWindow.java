package Ascii4UwUWareClient.UI.clickguisex;

import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.API.Value.Value;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import Ascii4UwUWareClient.UI.Hanabi.RenderUtil;
import Ascii4UwUWareClient.UI.clickguisex.values.ValueRender;
import Ascii4UwUWareClient.Util.TimerUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

public class ModuleWindow {
    public float x, y, height, height2;
    public Color color;
    public Module mod;
    public TimerUtil timerUtil = new TimerUtil();
    public ArrayList<ValueRender> vrs = new ArrayList<>();
    public static Color mainColor = new Color(55, 171, 255);

    public ModuleWindow(Module mod, float x, float y, Color c) {
        this.x = x;
        this.y = y;
        this.color = c;
        this.mod = mod;

        for (Value v : mod.values) {
            vrs.add(new ValueRender(v, x, y));
            if (!(v instanceof Mode)) {
                this.height2 += 20;
            } else {
                this.height2 += ((Mode<?>) v).getModes().length * 20;
            }
        }
    }

    public void drawModule(int mouseX, int mouseY) {

        if (height != height2) {
            height += (height2 - height) / 60;
        }

        RenderUtil.drawRect(x, y + (mod.values.size() == 0 ? 25 : 20), x + 100, y + height + (mod.values.size() == 0 ? 25 : 25), new Color(36, 41, 46).getRGB());

        if (mod.isEnabled()) {
            RenderUtil.drawRect(x, y, x + 100, y + (mod.values.size() == 0 ? 25 : 20), new Color(color.getRed(), color.getGreen(), color.getBlue()).getRGB());
//            RenderUtil.drawCustomImage(x + 74, y + (mod.values.size() == 0 ? 6 : 5), 20, 10, new ResourceLocation("fIux/ui/clickgui/toggle_24x10.png"),-1);
        } else {
            RenderUtil.drawRect(x, y, x + 100, y + (mod.values.size() == 0 ? 25 : 20), new Color(113, 113, 113, 170).getRGB());
//            RenderUtil.drawCustomImage(x + 74, y + (mod.values.size() == 0 ? 6 : 5), 20, 10, new ResourceLocation("fIux/ui/clickgui/toggle2_24x10.png"));
        }

        FontLoaders.arial18.drawStringWithShadow(mod.getName(), x + 10, y + (mod.values.size() == 0 ? 10 : 8), -1);

        if (isHovered(x, y, x + 100, y + (mod.values.size() == 0 ? 25 : 20), mouseX, mouseY) && Mouse.isButtonDown(0) && timerUtil.delay(200)) {
            mod.setEnabled(!mod.isEnabled());
            timerUtil.reset();
        }

        float valueY = y + (mod.values.size() == 0 ? 35 : 30);
        for (Value v : mod.values) {
            if (v instanceof Mode) {
                for (Enum m : ((Mode<?>) v).getModes()) {
                    RenderUtil.circle(x + 8, valueY + 2, 4, mainColor);
                    if (((Mode<?>) v).getValue().toString() == (m.name())) {
                        RenderUtil.circle(x + 8f, valueY + 2f, 2.8f, new Color(255, 255, 255));
                    }
//                    RenderUtil.drawRect(x + 6, valueY + 2, x + 11, valueY + 7, new Color(255, 255, 255));
                    FontLoaders.arial18.drawStringWithShadow(m.name(), x + 16, valueY, -1);
                    if (isHovered(x + 5, valueY, x + 95, valueY + FontLoaders.arial18.getHeight(), mouseX, mouseY) && Mouse.isButtonDown(0) && timerUtil.delay(100)) {
                        ((Mode<?>) v).setMode(m.name());
                        timerUtil.reset();
                    }
                    valueY += 20;
                }
            }
        }

        for (Value v : mod.values) {
            if (v instanceof Option) {
                if (((Boolean) v.getValue())) {
                    RenderUtil.drawRect(x + 5, valueY, x + 12, valueY + 7, mainColor.getRGB());
                    if (isHovered(x + 5, valueY, x + 95, valueY + 7, mouseX, mouseY)) {
                        RenderUtil.drawRect(x + 6, valueY + 1, x + 11, valueY + 6, new Color(200, 200, 200).getRGB());
                    } else {
                        RenderUtil.drawRect(x + 6, valueY + 1, x + 11, valueY + 6, new Color(255, 255, 255).getRGB());
                    }
                    FontLoaders.arial18.drawStringWithShadow(v.getDisplayName(), x + 16, valueY + 1, -1);
                } else {
                    RenderUtil.drawRect(x + 5, valueY, x + 12, valueY + 7, mainColor.getRGB());
//                    RenderUtil.drawRect(x + 6, valueY + 2, x + 11, valueY + 7, new Color(255, 255, 255));
                    FontLoaders.arial18.drawStringWithShadow(v.getDisplayName(), x + 16, valueY + 1, -1);
                }


                if (isHovered(x + 5, valueY, x + 95, valueY + 7, mouseX, mouseY) && Mouse.isButtonDown(0) && timerUtil.delay(300)) {
                    v.setValue(!(boolean) v.getValue());
                    timerUtil.reset();
                }
                valueY += 20;
            }
        }

        for (Value v : mod.values) {
            if (v instanceof Numbers) {

                float present = 90
                        * (((Number) v.getValue()).floatValue() - ((Numbers) v).getMinimum().floatValue())
                        / (((Numbers) v).getMaximum().floatValue() - ((Numbers) v).getMinimum().floatValue());
//                    RenderUtil.drawRect(x + 5, valueY, x + 12, valueY + 7, new Color(171, 71, 188));
//                    RenderUtil.drawRect(x + 6, valueY + 2, x + 11, valueY + 7, new Color(255, 255, 255));
                FontLoaders.arial16.drawStringWithShadow(v.getDisplayName(), x + 5, valueY - 5, new Color(123, 123, 123).getRGB());
                FontLoaders.arial16.drawStringWithShadow(v.getValue().toString(), x + 95 - FontLoaders.arial16.getStringWidth(v.getValue().toString()), valueY - 5, new Color(123, 123, 123).getRGB());
                v.animX1 = present;

                if (v.animX != v.animX1) {
                    v.animX += (v.animX1 - v.animX) / 30;
                }

                RenderUtil.drawRect(x + 5, valueY + 3, x + 95, valueY + 4.5f, new Color(255, 255, 255).getRGB());
                RenderUtil.drawRect(x + 5, valueY + 3, x + 5 + v.animX, valueY + 4.5f, mainColor.getRGB());

                if ((isHovered(x + 5, valueY + 0.5f, x + 95, valueY + 6.5f, mouseX, mouseY) || v.drag) && Mouse.isButtonDown(0)) {
                    RenderUtil.circle(x + 5 + v.animX, valueY + 3.5f, 3, new Color(200, 200, 200).getRGB());
                    RenderUtil.circle(x + 5 + v.animX, valueY + 3.5f, 3, new Color(200, 200, 200).getRGB());
                    RenderUtil.circle(x + 5 + v.animX, valueY + 3.5f, 3, new Color(200, 200, 200).getRGB());
                } else {
                    RenderUtil.circle(x + 5 + v.animX, valueY + 3.5f, 3, -1);
                    RenderUtil.circle(x + 5 + v.animX, valueY + 3.5f, 3, -1);
                    RenderUtil.circle(x + 5 + v.animX, valueY + 3.5f, 3, -1);
                }


                if ((isHovered(x + 5, valueY + 0.5f, x + 95, valueY + 6.5f, mouseX, mouseY) || v.drag) && Mouse.isButtonDown(0)) {
                    v.drag = true;
                    float render2 = ((Numbers) v).getMinimum().floatValue();
                    double max = ((Numbers) v).getMaximum().doubleValue();
                    float inc = (((Numbers<?>) v).getIncrement()).floatValue();
                    double valAbs = (double) mouseX - x - 5;
                    double perc = valAbs / (((x + 95) - (x + 5)));
                    perc = Math.min(Math.max(0.0D, perc), 1.0D);
                    double valRel = (max - render2) * perc;
                    double val = render2 + valRel;
                    val = (double) Math.round(val * (1.0D / inc)) / (1.0D / inc);
                    ((Numbers) v).setValue(Double.valueOf(val));
                }
                if (v.drag && !Mouse.isButtonDown(0)) {
                    v.drag = false;
                }

                valueY += 20;
            }
        }

    }

    public static boolean isHovered(float x, float y, float x1, float y1, float mouseX, float mouseY) {
        if (mouseX > x && mouseY > y && mouseX < x1 && mouseY < y1) {
            return true;
        }
        return false;
    }
}
