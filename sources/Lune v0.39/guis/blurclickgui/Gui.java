package me.superskidder.lune.guis.blurclickgui;

import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.utils.render.RenderUtil;
import me.superskidder.lune.utils.render.RenderUtils;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class Gui extends GuiScreen {

    public static float windowX = 0, windowY = 0;
    public static float width = 500;
    public static float height = 300;
    public static Mod currentMod;
    public static ModCategory modCategory = ModCategory.Combat;
    public static float mRole, vRole;
    public TimerUtil timer = new TimerUtil();
    public float dragX, dragY;


    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (isHovered(windowX, windowY, windowX + width, windowY + 40, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (dragX == 0 && dragY == 0) {
                dragX = mouseX - windowX;
                dragY = mouseY - windowY;
            } else {
                windowX = mouseX - dragX;
                windowY = mouseY - dragY;
            }
        } else if (dragX != 0 || dragY != 0) {
            dragX = 0;
            dragY = 0;
        }

        //滚动
        int dWheel = Mouse.getDWheel();
        if (isHovered(windowX + 200, windowY + 40, windowX + width, windowY + height, mouseX, mouseY)) {
            if (dWheel < 0 && Math.abs(vRole) + 170 < (currentMod.values.size() * 25)) {
                vRole -= 16;
            }
            if (dWheel > 0 && vRole < 0) {
                vRole += 16;
            }
        }

        if (isHovered(windowX, windowY + 40, windowX + 200, windowY + height - 20, mouseX, mouseY)) {
            if (dWheel < 0 && (Math.abs(mRole) + 200) < (ModuleManager.getModsByCategory(modCategory).size() * 30)) {
                mRole -= 16;
            }
            if (dWheel > 0 && mRole < 0) {
                mRole += 16;
            }
        }


        //主窗体
        RenderUtil.drawRect(windowX, windowY, windowX + width, windowY + height, new Color(0, 0, 0, 10).getRGB());
        RenderUtil.drawRect(windowX, windowY, windowX + width,windowY+ height, new Color(0,0,0,100).getRGB());
        RenderUtil.drawRect(windowX, windowY, windowX + width, windowY + 40, new Color(0, 0, 0, 120).getRGB());
        //Categories
        float cx = windowX + 50;
        for (ModCategory mc : ModCategory.values()) {
            if (mc != ModCategory.Cloud) {
                RenderUtils.drawImage(cx, windowY + 10, 16, 16, new ResourceLocation("client/blurlogo/" + mc.name() + ".png"), modCategory == mc ? new Color(255, 255, 255) : new Color(100, 100, 100));
                if (isHovered(cx, windowY + 10, cx + 16, windowY + 26, mouseX, mouseY) && Mouse.isButtonDown(0) && timer.delay(300)) {
                    modCategory = mc;
                    mRole = 0;
                    vRole = 0;
                    timer.reset();
                }
                cx += (width - 116) / (ModCategory.values().length - 2);
            }
        }
        //分割线
        RenderUtil.drawRect(windowX + 200, windowY + 50, windowX + 201, windowY + height - 10, new Color(150, 150, 150, 100).getRGB());

        float vY = windowY + 50 + vRole;
        if (currentMod != null) {
            for (Value v : currentMod.values) {
                if (((vY + 30) < (windowY + 70))) {
                    vY += 25;
                }
                if (!((vY + 30) < (windowY + 70)) && !((vY + 30) > (windowY + height))) {
                    if (v instanceof Bool) {
                        FontLoaders.F16.drawString(v.getName(), windowX + 210, vY, -1);
                        RenderUtils.drawCircle(windowX + width - 10, vY + 4, 4, ((Boolean) v.getValue()) ? -1 : new Color(100, 100, 100).getRGB());
                        if (isHovered(windowX + width - 14, vY, windowX + width - 6, vY + 8, mouseX, mouseY) && Mouse.isButtonDown(0) && timer.delay(300)) {
                            v.setValue(!((Boolean) v.getValue()));
                            timer.reset();
                        }
                    }

                    if (v instanceof Mode) {
                        FontLoaders.F16.drawString(v.getName(), windowX + 210, vY, -1);
                        RenderUtils.drawRoundRect(windowX + width - 80, vY - 5, windowX + width - 10, vY + 10, new Color(100, 100, 100, 200).getRGB());
                        FontLoaders.F16.drawCenteredStringWithShadow(((Mode<?>) v).getModeAsString(), windowX + width - 45, vY, new Color(255, 255, 255).getRGB());
                        if (isHovered(windowX + width - 80, vY - 5, windowX + width - 10, vY + 10, mouseX, mouseY) && Mouse.isButtonDown(0) && timer.delay(300)) {
                            if (Arrays.binarySearch(((Mode<?>) v).getModes(), (v.getValue()))
                                    + 1 < ((Mode<?>) v).getModes().length) {
                                v.setValue(((Mode<?>) v)
                                        .getModes()[Arrays.binarySearch(((Mode<?>) v).getModes(), (v.getValue())) + 1]);
                            } else {
                                v.setValue(((Mode<?>) v).getModes()[0]);
                            }
                            timer.reset();
                        }

                    }

                    if (v instanceof Num) {
                        FontLoaders.F16.drawString(v.getName(), windowX + 210, vY, -1);
                        FontLoaders.F16.drawString(v.getValue().toString(), windowX + width - FontLoaders.F16.getStringWidth(v.getValue().toString()) - 5, vY, -1);

                        RenderUtils.drawRect(windowX + 210, vY + 9, windowX + width - 10, vY + 10, new Color(120, 120, 120).getRGB());
                        RenderUtils.drawRect(windowX + 210,
                                vY + 9,
                                windowX + 210 + (windowX + width - 10 - windowX - 210) * ((((Number) v.getValue()).floatValue() - ((Num<?>) v).getMin().floatValue()) / (((Num<?>) v).getMax().floatValue() - ((Num<?>) v).getMin().floatValue())), vY + 10, new Color(255, 255, 255).getRGB());


                        if (isHovered(windowX + 210, vY + 7, windowX + width - 10, vY + 12, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                            float render2 = ((Num) v).getMin().floatValue();
                            double max = ((Num) v).getMax().doubleValue();
                            double inc = 0.1;
                            double valAbs = (double) mouseX - windowX - 210;
                            double perc = valAbs / (((windowX + width - 10) - (windowX + 210)));
                            perc = Math.min(Math.max(0.0D, perc), 1.0D);
                            double valRel = (max - render2) * perc;
                            double val = render2 + valRel;
                            val = (double) Math.round(val * (1.0D / inc)) / (1.0D / inc);
                            ((Num) v).setValue(Double.valueOf(val));
                        }


                    }

                    vY += 25;
                }
            }
        }


        float mY = windowY + 50 + mRole;
        for (Mod m : ModuleManager.getModsByCategory(modCategory)) {
            if (((mY + 30) < (windowY + 70))) {
                mY += 30;
            }
            if (!((mY + 30) < (windowY + 70)) && !((mY + 30) > (windowY + height))) {
                if (m.getState()) {
                    RenderUtils.drawRect(windowX + 10, mY, windowX + 190, mY + 25, new Color(255, 255, 255, 100).getRGB());
                } else {
                    RenderUtils.drawRect(windowX + 10, mY, windowX + 190, mY + 25, new Color(0, 0, 0, 50).getRGB());
                }

                if (isHovered(windowX + 10, mY, windowX + 190, mY + 25, mouseX, mouseY) && Mouse.isButtonDown(0) && timer.delay(300)) {
                    m.setStage(!m.getState());
                    timer.reset();
                } else if (isHovered(windowX + 10, mY, windowX + 190, mY + 25, mouseX, mouseY) && Mouse.isButtonDown(1) && timer.delay(300)) {
                    currentMod = m;
                    timer.reset();
                }
                //RenderUtils.drawGradientRect(windowX + 10, mY + 25, windowX + 190, mY + 27, new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 100).getRGB());
                FontLoaders.F18.drawString(m.getName(), windowX + 15, mY + 5, -1);
                FontLoaders.F14.drawString(m.getDescription(), windowX + 20, mY + 15, new Color(230, 230, 230).getRGB());
                mY += 30;
            }
        }

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
