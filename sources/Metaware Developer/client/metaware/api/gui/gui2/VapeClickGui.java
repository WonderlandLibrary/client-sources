package client.metaware.api.gui.gui2;

import client.metaware.Metaware;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.gui.notis.NotificationType;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.api.shader.Shader;
import client.metaware.api.shader.implementations.BlurShader;
import client.metaware.impl.utils.BlurUtil;
import client.metaware.impl.utils.render.RenderUtil;
import client.metaware.impl.utils.system.TimerUtil;
import client.metaware.impl.utils.util.Stencil;
import client.metaware.impl.utils.util.StencilUtil;
import client.metaware.impl.utils.util.other.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class VapeClickGui extends GuiScreen {

    private final boolean close = false;
    private boolean closed;
    float sY;

    private final BlurShader shader = new BlurShader(25);

    class GetConfigs extends Thread {
        @Override
        public void run() {
            for (client.metaware.api.config.Config s : Metaware.INSTANCE.getConfigManager().configs()) {
                configs.add(new Config(s.name(), "Local-Drive", true));
            }

        }
    }

    private float dragX, dragY;
    private boolean drag = false;
    private int valuemodx = 0;
    private static float modsRole, modsRoleNow;
    private static float valueRoleNow, valueRole;

    public static ArrayList<Config> configs = new ArrayList<Config>();
    public static EmptyInputBox configInputBox;


    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public float outro;
    public float lastOutro;

    @Override
    public void initGui() {
        super.initGui();
        percent = 1.33f;
        lastPercent = 1f;
        percent2 = 1.33f;
        lastPercent2 = 1f;
        outro = 1;
        lastOutro = 1;
        valuetimer.reset();
        configs.clear();

        Metaware.INSTANCE.getConfigManager().init();
        new GetConfigs().start();
        configInputBox = new EmptyInputBox(2, mc.fontRendererObj, 0, 0, 85, 10);
    }



    static float windowX = 200, windowY = 200;
    static float width = 500, height = 310;

    static ClickType selectType = ClickType.Home;
    static Category modCategory = Category.COMBAT;
    static Module selectMod;

    float[] typeXAnim = new float[]{windowX + 10, windowX + 10, windowX + 10, windowX + 10};

    float hy = windowY + 40;

    TimerUtil valuetimer = new TimerUtil();

    public float smoothTrans(double current, double last){
        return (float) (current + (last - current) / (Minecraft.getDebugFPS() / 10));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ScaledResolution sResolution = new ScaledResolution(mc);
        ScaledResolution sr = new ScaledResolution(mc);
        CustomFontRenderer fr = Metaware.INSTANCE.getFontManager().currentFont().size(20);
        CustomFontRenderer fr1 = Metaware.INSTANCE.getFontManager().currentFont().size(18);
        CustomFontRenderer fr2 = Metaware.INSTANCE.getFontManager().currentFont().size(16);
        CustomFontRenderer fr3 = Metaware.INSTANCE.getFontManager().currentFont().size(14);


        float outro = smoothTrans(this.outro, lastOutro);
        if (mc.currentScreen == null) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(outro, outro, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        }


        //animation
        percent = smoothTrans(this.percent, lastPercent);
        percent2 = smoothTrans(this.percent2, lastPercent2);


        if (percent > 0.98) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(percent, percent, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        } else {
            if (percent2 <= 1) {
                GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
                GlStateManager.scale(percent2, percent2, 0);
                GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
            }
        }

        if(percent <= 1.5 && close) {
            percent = smoothTrans(this.percent, 2);
            percent2 = smoothTrans(this.percent2, 2);
        }

        if(percent >= 1.4  &&  close){
            percent = 1.5f;
            closed = true;
            mc.currentScreen = null;
        }



        RenderUtil.drawGradientRect(0, 0, sResolution.getScaledWidth(), sResolution.getScaledHeight(), new Color(107, 147, 255, 100).getRGB(), new Color(0, 0, 0, 30).getRGB());

        if (isHovered(windowX, windowY, windowX + width, windowY + 20, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (dragX == 0 && dragY == 0) {
                dragX = mouseX - windowX;
                dragY = mouseY - windowY;
            } else {
                windowX = mouseX - dragX;
                windowY = mouseY - dragY;
            }
            drag = true;
        } else if (dragX != 0 || dragY != 0) {
            dragX = 0;
            dragY = 0;
        }


        Gui.drawRect(windowX, windowY, windowX + width, windowY + height, new Color(21, 22, 25).getRGB());
        if (selectMod == null) {
            fr.drawString("Vape gui", windowX + 20, windowY + height - 20, new Color(77, 78, 84).getRGB());
        }

        float typeX = windowX + 20;
        int i = 0;
        for (ClickType e : ClickType.values()) {
            if (!isHovered(windowX, windowY, windowX + width, windowY + 20, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                if (typeXAnim[i] != typeX) {
                    typeXAnim[i] += (typeX - typeXAnim[i]) / 20;
                }
            } else {
                if (typeXAnim[i] != typeX) {
                    typeXAnim[i] = typeX;
                }
            }
            if (e != ClickType.Settings) {
                if (e == selectType) {
                    RenderUtil.drawImage(typeXAnim[i], windowY + 10, 16, 16, new ResourceLocation("whiz/vapeclickgui/" + selectType.name() + ".png"), new Color(255, 255, 255));
                    fr1.drawString(e.name(), typeXAnim[i] + 20, windowY + 15, new Color(255, 255, 255).getRGB());
                    typeX += (32 + fr1.getWidth(e.name() + " "));
                } else {
                    RenderUtil.drawImage(typeXAnim[i], windowY + 10, 16, 16, new ResourceLocation("whiz/vapeclickgui/" + e.name() + ".png"), new Color(79, 80, 86));
                    typeX += (32);
                }
            } else {
                RenderUtil.drawImage(windowX + width - 20, windowY + 10, 16, 16, new ResourceLocation("whiz/vapeclickgui/" + e.name() + ".png"), e == selectType ? new Color(255, 255, 255) : new Color(79, 80, 86));
            }
            i++;
        }


        if (selectType == ClickType.Home) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(0, 2 * ((int) (sr.getScaledHeight_double() - (windowY + height))) + 40, (int) (sr.getScaledWidth_double() * 2), (int) ((height) * 2) - 160);
            if (selectMod == null) {
                float cateY = windowY + 65;
                GL11.glColor4f(1, 1, 1, 1);
                for (Category m : Category.values()) {
                    if (m == modCategory) {
                        fr1.drawString(m.name(), windowX + 20, cateY, new Color(255, 255, 255, 255).getRGB());
                        GL11.glColor4f(1, 1, 1, 1);
                        RenderUtil.drawRoundRect(windowX + 20, hy + fr1.getHeight(m.name()) + 2, windowX + 20 + fr1.getWidth(m.name()), hy + fr1.getHeight(m.name()) + 4, new Color(51, 112, 255).getRGB());
                        GL11.glColor4f(1, 1, 1, 1);
                        if (isHovered(windowX, windowY, windowX + width, windowY + 20, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                            hy = cateY;
                        } else {
                            if (hy != cateY) {
                                hy += (cateY - hy) / 20;
                            }
                        }
                    } else {
                        fr1.drawString(m.name(), windowX + 20, cateY, -1);
                    }


                    cateY += 25;
                }
            }
            if (selectMod != null) {
                if (valuemodx > -80) {
                    valuemodx -= 5;
                }
            } else {
                if (valuemodx < 0) {
                    valuemodx += 5;
                }
            }

            if (selectMod != null) {
                RenderUtil.drawRoundRect(windowX + 430 + valuemodx, windowY + 60, windowX + width, windowY + height - 20, new Color(32, 31, 35).getRGB());
                RenderUtil.drawRoundRect(windowX + 430 + valuemodx, windowY + 60, windowX + width, windowY + 85, new Color(39, 38, 42).getRGB());
                RenderUtil.drawImage(windowX + 435 + valuemodx, windowY + 65, 16, 16, new ResourceLocation("whiz/vapeclickgui/back.png"), new Color(82, 82, 85));
                if (isHovered(windowX + 435 + valuemodx, windowY + 65, windowX + 435 + valuemodx + 16, windowY + 65 + 16, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                    selectMod = null;
                    valuetimer.reset();
                }


                int dWheel = Mouse.getDWheel();
                if (isHovered(windowX + 430 + valuemodx, windowY + 60, windowX + width, windowY + height - 20, mouseX, mouseY)) {
                    if (dWheel < 0 && Math.abs(valueRole) + 170 < (Module.getPropertyRepository().propertiesBy(selectMod.getClass()).size() * 25)) {
                        valueRole -= 32;
                    }
                    if (dWheel > 0 && valueRole < 0) {
                        valueRole += 32;
                    }
                }

                if (valueRoleNow != valueRole) {
                    valueRoleNow += (valueRole - valueRoleNow) / 20;
                    valueRoleNow = (int) valueRoleNow;
                }

                float valuey = windowY + 100 + valueRoleNow;

                if(selectMod == null) {
                	return;
                }
                for (Property v : Module.getPropertyRepository().propertiesBy(selectMod.getClass())) {
                    if (v instanceof EnumProperty && v.isAvailable()) {
                        EnumProperty prop = (EnumProperty) v;
                        int modesSize = prop.values().length;
                        int index = Arrays.asList(prop.values()).indexOf(prop.getValue());
                        if (valuey + 4 > windowY + 100 & valuey < (windowY + height)) {
                            RenderUtil.drawRoundedRect5(windowX + 445 + valuemodx, valuey + 2, windowX + width - 5, valuey + 22, 2, new Color(46, 45, 48).getRGB());
                            RenderUtil.drawRoundedRect5(windowX + 446 + valuemodx, valuey + 3, windowX + width - 6, valuey + 21, 2, new Color(32, 31, 35).getRGB());
                            fr2.drawString(prop.label() + " : " + prop.getValue(), windowX + 455 + valuemodx, valuey + 10, new Color(230, 230, 230).getRGB());
                            fr1.drawString(">", windowX + width - 15, valuey + 9, new Color(73, 72, 76).getRGB());
                            if (isHovered(windowX + 445 + valuemodx, valuey + 2, windowX + width - 5, valuey + 22, mouseX, mouseY) && Mouse.isButtonDown(0) && valuetimer.delay(300)) {
                                if(Mouse.isButtonDown(0))
                                    if(index + 1 < prop.values().length) {
                                        prop.setValue(index++);
                                    } else index = 0;
                                else
                                if(index - 1 > 0) {
                                    prop.setValue(index--);
                                } else index = 0;
                                prop.setValue(index);
                                valuetimer.reset();
                            }
                        }
                        valuey += 25;
                    }
                }

                for (Property v : Module.getPropertyRepository().propertiesBy(selectMod.getClass())) {
                    if (v instanceof DoubleProperty && v.isAvailable()) {
                        DoubleProperty property = (DoubleProperty) v;
                        if (valuey + 4 > windowY + 100) {

                            double present = (((windowX + width - 11) - (windowX + 450 + valuemodx))
                                    * (property.getValue() - (property).getMin())
                                    / ((property).getMax() - (property).getMin()));

                            fr2.drawString(property.label(), windowX + 445 + valuemodx, valuey + 5, new Color(73, 72, 76).getRGB());
                            String rep = "";
                            switch (property.representation()) {
                                case INT:
                                    rep = "";
                                case DOUBLE:
                                    rep = "";
                                    break;
                                case DISTANCE:
                                    rep = "m/s";
                                    break;
                                case PERCENTAGE:
                                    rep = "%";
                                    break;
                                case MILLISECONDS:
                                    rep = "ms";
                                    break;
                            }
                            fr2.drawCenteredString("" + MathUtils.round(property.getValue(), 2) + rep, windowX + width - 20, valuey + 5, new Color(255, 255, 255).getRGB());
                            Gui.drawRect(windowX + 450 + valuemodx, valuey + 20, windowX + width - 11, valuey + 21.5f, new Color(77, 76, 79).getRGB());
                            Gui.drawRect(windowX + 450 + valuemodx, valuey + 20, windowX + 450 + valuemodx + present, valuey + 21.5f, new Color(43, 116, 226).getRGB());
                            RenderUtil.drawCircle2(windowX + 450 + valuemodx + present, valuey + 21f, 5, new Color(32, 31, 35).getRGB());
                            RenderUtil.drawCircle2(windowX + 450 + valuemodx + present, valuey + 21f, 5, new Color(32, 31, 35).getRGB());
                            RenderUtil.drawCircle2(windowX + 450 + valuemodx + present, valuey + 21f, 4, new Color(44, 115, 224).getRGB());
                            RenderUtil.drawCircle2(windowX + 450 + valuemodx + present, valuey + 21f, 4, new Color(44, 115, 224).getRGB());

                            if (isHovered(windowX + 450 + valuemodx, valuey + 18, windowX + width - 11, valuey + 23.5f, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                                float render2 = (float) (property).getMin();
                                double max = (property).getMax();
                                double inc = property.increment();
                                double valAbs = (double) mouseX - ((double) (windowX + 450 + valuemodx));
                                double perc = valAbs / (((windowX + width - 11) - (windowX + 450 + valuemodx)));
                                perc = Math.min(Math.max(0.0D, perc), 1.0D);
                                double valRel = (max - render2) * perc;
                                double val = render2 + valRel;
                                val = (double) Math.round(val * (1.0D / inc)) / (1.0D / inc);
                                (property).setValue(val);
                            }
                        }
                        valuey += 25;
                    }
                }


                for (Property v : Module.getPropertyRepository().propertiesBy(selectMod.getClass())) {
                    if (v.getValue() instanceof Boolean && v.isAvailable()) {
                        Property property = (Property) v;
                        if (valuey + 4 > windowY + 100) {
                            if ((Boolean)property.getValue()) {
                                fr2.drawString(property.label(), windowX + 445 + valuemodx, valuey + 4, -1);
                                property.optionAnim = 100;
                                RenderUtil.drawRoundedRect5(windowX + width - 30, valuey + 2, windowX + width - 10, valuey + 12, 4, new Color(33, 94, 181, 255).getRGB());
                                RenderUtil.drawRoundedRect5(windowX + width - 30, valuey + 2, windowX + width - 10, valuey + 12, 4, new Color(59, 60, 65).getRGB());
                                RenderUtil.drawRoundedRect5(windowX + width - 29, valuey + 3, windowX + width - 11, valuey + 11, 3, new Color(42, 115, 222).getRGB());
                                RenderUtil.drawCircle2(windowX + width - 25 + 10 * (property.optionAnimNow / 100f), valuey + 7, 3.5f, new Color(255, 255, 255).getRGB());
                            } else {
                                fr3.drawString(property.label(), windowX + 445 + valuemodx, valuey + 4, new Color(73, 72, 76).getRGB());
                                property.optionAnim = 0;
                                RenderUtil.drawRoundedRect5(windowX + width - 30, valuey + 2, windowX + width - 10, valuey + 12, 4, new Color(59, 60, 65).getRGB());
                                RenderUtil.drawRoundedRect5(windowX + width - 29, valuey + 3, windowX + width - 11, valuey + 11, 3, new Color(32, 31, 35).getRGB());
                                RenderUtil.drawCircle2(windowX + width - 25 + 10 * (v.optionAnimNow / 100f), valuey + 7, 3.5f, new Color(59, 60, 65).getRGB());
                            }
                            if (isHovered(windowX + width - 30, valuey + 2, windowX + width - 10, valuey + 12, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                                if (valuetimer.delay(300)) {
                                    property.setValue(!(Boolean) property.getValue());
                                    valuetimer.reset();
                                }
                            }
                        }

                        if (v.optionAnimNow != v.optionAnim) {
                            v.optionAnimNow += (v.optionAnim - v.optionAnimNow) / 20;
                        }
                        valuey += 25;
                    }
                }
            }

            float modY = windowY + 70 + modsRoleNow;
            for (Module m : Metaware.INSTANCE.getModuleManager().getModules()) {
                if (m.getCategory() != modCategory)
                    continue;

                if (isHovered(windowX + 100 + valuemodx, modY - 10, windowX + 425 + valuemodx, modY + 25, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                    if (valuetimer.delay(300) && modY + 40 > (windowY + 70) && modY < (windowY + height)) {
                        m.toggle();
                        valuetimer.reset();
                    }
                } else if (isHovered(windowX + 100 + valuemodx, modY - 10, windowX + 425 + valuemodx, modY + 25, mouseX, mouseY) && Mouse.isButtonDown(1)) {
                    if (valuetimer.delay(300)) {
                        if (selectMod != m) {
                            valueRole = 0;
                            selectMod = m;
                        } else if (selectMod == m) {
                            selectMod = null;
                        }
                        valuetimer.reset();
                    }
                }

                if (isHovered(windowX + 100 + valuemodx, modY - 10, windowX + 425 + valuemodx, modY + 25, mouseX, mouseY)) {
                    if (m.isToggled()) {
                        RenderUtil.drawRoundRect(windowX + 100 + valuemodx, modY - 10, windowX + 425 + valuemodx, modY + 25, new Color(43, 41, 45).getRGB());
                    } else {
                        RenderUtil.drawRoundRect(windowX + 100 + valuemodx, modY - 10, windowX + 425 + valuemodx, modY + 25, new Color(35, 35, 35).getRGB());
                    }
                } else {
                    if (m.isToggled()) {
                        RenderUtil.drawRoundRect(windowX + 100 + valuemodx, modY - 10, windowX + 425 + valuemodx, modY + 25, new Color(36, 34, 38).getRGB());
                    } else {
                        RenderUtil.drawRoundRect(windowX + 100 + valuemodx, modY - 10, windowX + 425 + valuemodx, modY + 25, new Color(32, 31, 33).getRGB());
                    }
                }
                RenderUtil.drawRoundRect(windowX + 100 + valuemodx, modY - 10, windowX + 125 + valuemodx, modY + 25, new Color(37, 35, 39).getRGB());
                RenderUtil.drawRoundRect(windowX + 410 + valuemodx, modY - 10, windowX + 425 + valuemodx, modY + 25, new Color(39, 38, 42).getRGB());
                fr.drawString(".", windowX + 416 + valuemodx, modY - 5, new Color(66, 64, 70).getRGB());
                fr.drawString(".", windowX + 416 + valuemodx, modY - 1, new Color(66, 64, 70).getRGB());
                fr.drawString(".", windowX + 416 + valuemodx, modY + 3, new Color(66, 64, 70).getRGB());

                if (m.isToggled()) {
                    fr1.drawString(m.getName(), windowX + 140 + valuemodx, modY + 5, new Color(220, 220, 220).getRGB());
                    RenderUtil.drawRoundRect(windowX + 100 + valuemodx, modY - 10, windowX + 125 + valuemodx, modY + 25, new Color(41, 117, 221, (int) (m.optionAnimNow / 100f * 255)).getRGB());
                    RenderUtil.drawImage(windowX + 105 + valuemodx, modY, 16, 16, new ResourceLocation("whiz/vapeclickgui/module.png"), new Color(220, 220, 220));
                    m.optionAnim = 100;

                    RenderUtil.drawRoundedRect5(windowX + 380 + valuemodx, modY + 2, windowX + 400 + valuemodx, modY + 12, 4, new Color(33, 94, 181, (int) (m.optionAnimNow / 100f * 255)).getRGB());
                    RenderUtil.drawCircle2(windowX + 385 + 10 * m.optionAnimNow / 100 + valuemodx, modY + 7, 3.5f, new Color(255, 255, 255).getRGB());
                } else {
                    fr1.drawString(m.getName(), windowX + 140 + valuemodx, modY + 5, new Color(108, 109, 113).getRGB());
                    RenderUtil.drawImage(windowX + 105 + valuemodx, modY, 16, 16, new ResourceLocation("whiz/vapeclickgui/module.png"), new Color(92, 90, 94));
                    m.optionAnim = 0;

                    RenderUtil.drawRoundedRect5(windowX + 380 + valuemodx, modY + 2, windowX + 400 + valuemodx, modY + 12, 4, new Color(59, 60, 65).getRGB());
                    RenderUtil.drawRoundedRect5(windowX + 381 + valuemodx, modY + 3, windowX + 399 + valuemodx, modY + 11, 3, new Color(29, 27, 31).getRGB());
                    RenderUtil.drawCircle2(windowX + 385 + 10 * m.optionAnimNow / 100 + valuemodx, modY + 7, 3.5f, new Color(59, 60, 65).getRGB());
                }

                if (m.optionAnimNow != m.optionAnim) {
                    m.optionAnimNow += (m.optionAnim - m.optionAnimNow) / 20;
                }


                modY += 40;
            }

            int dWheel2 = Mouse.getDWheel();
            if (isHovered(windowX + 100 + valuemodx, windowY + 60, windowX + 425 + valuemodx, windowY + height, mouseX, mouseY)) {
                if (dWheel2 < 0 && Math.abs(modsRole) + 220 < (Metaware.INSTANCE.getModuleManager().getModulesInCategory(modCategory).size() * 40)) {
                    modsRole -= 32;
                }
                if (dWheel2 > 0 && modsRole < 0) {
                    modsRole += 32;
                }
            }

            if (modsRoleNow != modsRole) {
                modsRoleNow += (modsRole - modsRoleNow) / 20;
                modsRoleNow = (int) modsRoleNow;
            }


            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        if (selectType == ClickType.Config) {
            fr1.drawString("Your Profiles", windowX + 20, windowY + 60, new Color(169, 170, 173).getRGB());
            RenderUtil.drawRoundRect(windowX + 20, windowY + fr1.getHeight("Your Profiles") + 60, windowX + 20 + fr1.getWidth("Your Profiles"), windowY + fr1.getHeight("Your Profiles") + 62, new Color(51, 112, 203).getRGB());


            configInputBox.xPosition = (int) (windowX + 20);
            configInputBox.yPosition = (int) (windowY + 100);
            configInputBox.drawTextBox();
            RenderUtil.drawRoundRect(windowX + 20, windowY + 110, windowX + 105, windowY + 111, new Color(36, 36, 40).getRGB());
            if (configInputBox.getText().equals("")) {
                fr2.drawString("Add new profile", windowX + 20, windowY + 97, new Color(63, 64, 67).getRGB());
            }
            RenderUtil.drawCircle2(windowX + 100, windowY + 100, 5, new Color(42, 115, 222).getRGB());
            RenderUtil.drawCircle2(windowX + 100, windowY + 100, 5, new Color(42, 115, 222).getRGB());
            fr.drawString("+", windowX + 97f, windowY + 95.5f, new Color(255, 255, 255, 255).getRGB());


            int dWheel2 = Mouse.getDWheel();
            if (isHovered(windowX + 100 + valuemodx, windowY + 60, windowX + 425 + valuemodx, windowY + height, mouseX, mouseY)) {
                if (dWheel2 < 0 && Math.abs(sY) + 220 < (configs.size() * 40)) {
                    sY -= 16;
                }
                if (dWheel2 > 0 && sY < 0) {
                    sY += 16;
                }
            }
            float cx = windowX + 140, cy = windowY + 60 + sY;
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(0, 2 * ((int) (sr.getScaledHeight_double() - (windowY + height))) + 40, (int) (sr.getScaledWidth_double() * 2), (int) ((height) * 2) - 160);

            for (Config c : configs) {
                RenderUtil.drawRoundRect(cx, cy, cx + 100, cy + 100, new Color(32, 31, 35).getRGB());
                fr2.drawString(c.getName(), cx + 10, cy + 10, -1);
                fr2.drawString("X", cx + 85, cy + 10, new Color(86, 85, 88).getRGB());
                RenderUtil.drawImage(cx + 10, cy + 80, 10, 8, new ResourceLocation("whiz/Cloud.png"), new Color(91, 92, 98));
                fr3.drawString(c.description, cx + 25, cy + 82, new Color(86, 85, 88).getRGB());


                if (isHovered(cx, cy, cx + 100, cy + 100, mouseX, mouseY) && Mouse.isButtonDown(0) && valuetimer.delay(500)) {
                    Metaware.INSTANCE.getConfigManager().load(c.name);
                    Metaware.INSTANCE.getNotificationManager().pop("Loaded Config!", "Seccesfully loaded " + c.name + " config!", 3000, NotificationType.SUCCESS);
                    valuetimer.reset();
                }

                cx += 105;
                if ((cx +
                        100) >= windowX + width) {
                    cx = windowX + 140;
                    cy += 110;
                }
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        int dWheel2 = Mouse.getDWheel();
        if (isHovered(windowX + 100 + valuemodx, windowY + 60, windowX + 425 + valuemodx, windowY + height, mouseX, mouseY)) {
            if (dWheel2 < 0 && Math.abs(modsRole) + 220 < (Metaware.INSTANCE.getModuleManager().getModulesInCategory(modCategory).size() * 40)) {
                modsRole -= 16;
            }
            if (dWheel2 > 0 && modsRole < 0) {
                modsRole += 16;
            }
        }

        if (modsRoleNow != modsRole) {
            modsRoleNow += (modsRole - modsRoleNow) / 20;
            modsRoleNow = (int) modsRoleNow;
        }

        if (selectType == ClickType.Config) {
            if (isHovered(windowX + 95, windowY + 95, windowX + 105, windowY + 105, mouseX, mouseY) && Mouse.isButtonDown(0) && valuetimer.delay(500)) {
                if (!configInputBox.getText().contains(" ")) {
                    configs.add(new Config(configInputBox.getText(), "Local-Drive", false));
                    Metaware.INSTANCE.getConfigManager().save(configInputBox.getText());
                    Metaware.INSTANCE.getNotificationManager().pop("Added Config!", "Successfully saved " + configInputBox.getText() + " config!", 3000, NotificationType.SUCCESS);
                }else {
                    Metaware.INSTANCE.getNotificationManager().pop("Error Occurred!", "Wrong config text format" + EnumChatFormatting.RED + " \"" + configInputBox.getText() + "\"" + EnumChatFormatting.WHITE, 3000, NotificationType.ERROR);
                }
                valuetimer.reset();
            }


            if (isHovered(configInputBox.xPosition, configInputBox.yPosition, configInputBox.xPosition + configInputBox.getWidth(), configInputBox.yPosition + 10, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                configInputBox.mouseClicked(mouseX, mouseY, Mouse.getButtonCount());
            } else if (Mouse.isButtonDown(0) && !isHovered(configInputBox.xPosition, configInputBox.yPosition, configInputBox.xPosition + configInputBox.getWidth(), configInputBox.yPosition + 10, mouseX, mouseY)) {
                configInputBox.setFocused(false);
            }
        }
        //Gui.drawRect(width + 10, height - 8, (windowX + width), (windowY + height), new Color(0, 255, 0, 90).getRGB());
    }

    public int findArray(float[] a, float b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float typeX = windowX + 20;
        CustomFontRenderer fr1 = Metaware.INSTANCE.getFontManager().currentFont().size(18);
        for (ClickType e : ClickType.values()) {
            if (e != ClickType.Settings) {
                if (e == selectType) {
                    if (isHovered(typeX, windowY + 10, typeX + 16 + fr1.getWidth(e.name() + " "), windowY + 10 + 16, mouseX, mouseY)) {
                        selectType = e;
                    }
                    typeX += (32 + fr1.getWidth(e.name() + " "));
                } else {
                    if (isHovered(typeX, windowY + 10, typeX + 16, windowY + 10 + 16, mouseX, mouseY)) {
                        selectType = e;
                    }
                    typeX += (32);
                }
            } else {
                if (isHovered(windowX + width - 32, windowY + 10, windowX + width, windowY + 10 + 16, mouseX, mouseY)) {
                    selectType = e;
                }
            }
        }

        if (selectType == ClickType.Home) {
            float cateY = windowY + 65;
            for (Category m : Category.values()) {

                if (isHovered(windowX, cateY - 8, windowX + 50, cateY + fr1.getHeight("") + 8, mouseX, mouseY)) {
                    if (modCategory != m) {
                        modsRole = 0;
                    }

                    modCategory = m;
                    for (Module mod : Metaware.INSTANCE.getModuleManager().getModules()){
                        mod.optionAnim = 0;
                        mod.optionAnimNow = 0;

                    }
                }

                cateY += 25;
            }

        }

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE){
            this.mc.displayGuiScreen(null);
        }
        if (configInputBox.isFocused()) {
            configInputBox.textboxKeyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);

    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}