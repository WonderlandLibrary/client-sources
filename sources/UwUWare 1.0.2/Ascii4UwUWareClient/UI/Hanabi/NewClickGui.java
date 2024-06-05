package Ascii4UwUWareClient.UI.Hanabi;

import java.awt.Color;

import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.API.Value.Value;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Manager.ModuleManager;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.UI.Font.FontLoaders;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.util.ResourceLocation;


import org.lwjgl.input.Mouse;

public class NewClickGui extends GuiScreen implements GuiYesNoCallback {
    public static ModuleType currentModuleType;
    public static Module currentModule;
    public static float startX;
    public static float startY;
    public int moduleStart = 0;
    public int valueStart = 0;
    boolean previousmouse = true;
    boolean mouse;
    public Opacity opacity = new Opacity(0);
    public int opacityx = 255;
    public float moveX = 0.0F;
    public float moveY = 0.0F;

    static {
        currentModuleType = ModuleType.Combat;
        currentModule = ModuleManager.getModulesInType(currentModuleType).size() != 0 ? (Module) ModuleManager.getModulesInType(currentModuleType).get(0) : null;
        startX = 100.0F;
        startY = 100.0F;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.isHovered(startX, startY - 25.0F, startX + 400.0F, startY + 25.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
            if (this.moveX == 0.0F && this.moveY == 0.0F) {
                this.moveX = (float) mouseX - startX;
                this.moveY = (float) mouseY - startY;
            } else {
                startX = (float) mouseX - this.moveX;
                startY = (float) mouseY - this.moveY;
            }

            this.previousmouse = true;
        } else if (this.moveX != 0.0F || this.moveY != 0.0F) {
            this.moveX = 0.0F;
            this.moveY = 0.0F;
        }

        this.opacity.interpolate((float) this.opacityx);
        RenderUtil.drawRect(startX, startY, startX + 60.0F, startY + 320.0F, (new Color(40, 40, 40, (int) this.opacity.getOpacity())).getRGB());
        RenderUtil.drawRect(startX + 60.0F, startY, startX + 200.0F, startY + 320.0F, (new Color(31, 31, 31, (int) this.opacity.getOpacity())).getRGB());
        RenderUtil.drawRect(startX + 200.0F, startY, startX + 420.0F, startY + 320.0F, (new Color(40, 40, 40, (int) this.opacity.getOpacity())).getRGB());

        int m;
        for (m = 0; m < ModuleType.values().length; ++m) {
            ModuleType[] mY = ModuleType.values();
            if (mY[m] != currentModuleType) {
                RenderUtil.drawFilledCircle((double) (startX + 30.0F), (double) (startY + 30.0F + (float) (m * 40)), 15.0D, (new Color(56, 56, 56, (int) this.opacity.getOpacity())).getRGB(), 5);
                if (mY[m].toString() == "Combat") {
                    Client.instance.getClass();
                    RenderUtil.drawImage(new ResourceLocation("ClientAssets" + "/Clickui/combat.png"), (int) startX + 20, (int) startY + 20 + m * 40, 20, 20);
                }

                if (mY[m].toString() == "Move") {
                    Client.instance.getClass();
                    RenderUtil.drawImage(new ResourceLocation("ClientAssets" + "/Clickui/movement.png"), (int) startX + 20, (int) startY + 20 + m * 40, 20, 20);
                }

                if (mY[m].toString() == "Misc") {
                    Client.instance.getClass();
                    RenderUtil.drawImage(new ResourceLocation("ClientAssets" + "/Clickui/player.png"), (int) startX + 20, (int) startY + 20 + m * 40, 20, 20);
                }

                if (mY[m].toString() == "Render") {
                    Client.instance.getClass();
                    RenderUtil.drawImage(new ResourceLocation("ClientAssets" + "/Clickui/render.png"), (int) startX + 20, (int) startY + 20 + m * 40, 20, 20);
                }
            } else {
                RenderUtil.drawFilledCircle((double) (startX + 30.0F), (double) (startY + 30.0F + (float) (m * 40)), 15.0D, (new Color(101, 81, 255, (int) this.opacity.getOpacity())).getRGB(), 5);
                if (mY[m].toString() == "Combat") {
                    Client.instance.getClass();
                    RenderUtil.drawImage(new ResourceLocation("ClientAssets" + "/Clickui/combat2.png"), (int) startX + 20, (int) startY + 20 + m * 40, 20, 20);
                }

                if (mY[m].toString() == "Move") {
                    Client.instance.getClass();
                    RenderUtil.drawImage(new ResourceLocation("ClientAssets" + "/Clickui/movement2.png"), (int) startX + 20, (int) startY + 20 + m * 40, 20, 20);
                }

                if (mY[m].toString() == "Misc") {
                    Client.instance.getClass();
                    RenderUtil.drawImage(new ResourceLocation("ClientAssets" + "/Clickui/player2.png"), (int) startX + 20, (int) startY + 20 + m * 40, 20, 20);
                }

                if (mY[m].toString() == "Render") {
                    Client.instance.getClass();
                    RenderUtil.drawImage(new ResourceLocation("ClientAssets" + "/Clickui/render2.png"), (int) startX + 20, (int) startY + 20 + m * 40, 20, 20);
                }


            }

            try {
                if (this.isCategoryHovered(startX + 15.0F, startY + 15.0F + (float) (m * 40), startX + 45.0F, startY + 45.0F + (float) (m * 40), mouseX, mouseY) && Mouse.isButtonDown(0)) {
                    currentModuleType = mY[m];
                    currentModule = ModuleManager.getModulesInType(currentModuleType).size() != 0 ? (Module) ModuleManager.getModulesInType(currentModuleType).get(0) : null;
                    this.moduleStart = 0;
                }
            } catch (Exception var23) {
                System.err.println(var23);
            }
        }

        m = Mouse.getDWheel();
        if (this.isCategoryHovered(startX + 60.0F, startY, startX + 200.0F, startY + 320.0F, mouseX, mouseY)) {
            if (m < 0 && this.moduleStart < ModuleManager.getModulesInType(currentModuleType).size() - 1) {
                ++this.moduleStart;
            }

            if (m > 0 && this.moduleStart > 0) {
                --this.moduleStart;
            }
        }

        if (this.isCategoryHovered(startX + 200.0F, startY, startX + 420.0F, startY + 320.0F, mouseX, mouseY)) {
            if (m < 0 && this.valueStart < currentModule.getValues().size() - 1) {
                ++this.valueStart;
            }

            if (m > 0 && this.valueStart > 0) {
                --this.valueStart;
            }
        }

        FontLoaders.Comfortaa16.drawString(currentModule == null ? currentModuleType.toString() : currentModuleType.toString() + "/" + currentModule.getName(), startX + 70.0F, startY + 15.0F, (new Color(248, 248, 248)).getRGB());
        if (currentModule != null) {
            float var24 = startY + 30.0F;

            int i;
            for (i = 0; i < ModuleManager.getModulesInType(currentModuleType).size(); ++i) {
                Module value = (Module) ModuleManager.getModulesInType(currentModuleType).get(i);
                if (var24 > startY + 300.0F) {
                    break;
                }

                if (i >= this.moduleStart) {
                    RenderUtil.drawRect(startX + 75.0F, var24, startX + 185.0F, var24 + 2.0F, (new Color(40, 40, 40, (int) this.opacity.getOpacity())).getRGB());
                    FontLoaders.Comfortaa16.drawString(value.getName(), startX + 90.0F, var24 + 8.0F, (new Color(248, 248, 248, (int) this.opacity.getOpacity())).getRGB());
                    if (!value.isEnabled()) {
                        RenderUtil.drawFilledCircle((double) (startX + 75.0F), (double) (var24 + 13.0F), 2.0D, (new Color(255, 0, 0, (int) this.opacity.getOpacity())).getRGB(), 5);
                    } else {
                        RenderUtil.drawFilledCircle((double) (startX + 75.0F), (double) (var24 + 13.0F), 2.0D, (new Color(0, 255, 0, (int) this.opacity.getOpacity())).getRGB(), 5);
                    }

                    if (this.isSettingsButtonHovered(startX + 90.0F, var24, startX + 100.0F + (float) FontLoaders.Comfortaa20.getStringWidth(value.getName()), var24 + 8.0F + (float) FontLoaders.Comfortaa20.getHeight(), mouseX, mouseY)) {
                        if (!this.previousmouse && Mouse.isButtonDown(0)) {
                            if (value.isEnabled()) {
                                value.setEnabled(false);
                            } else {
                                value.setEnabled(true);
                            }

                            this.previousmouse = true;
                        }

                        if (!this.previousmouse && Mouse.isButtonDown(1)) {
                            this.previousmouse = true;
                        }
                    }

                    if (!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                    }

                    if (this.isSettingsButtonHovered(startX + 90.0F, var24, startX + 100.0F + (float) FontLoaders.Comfortaa20.getStringWidth(value.getName()), var24 + 8.0F + (float) FontLoaders.Comfortaa20.getHeight(), mouseX, mouseY) && Mouse.isButtonDown(1)) {
                        currentModule = value;
                        this.valueStart = 0;
                    }

                    var24 += 25.0F;
                }
            }

            var24 = startY + 30.0F;

            for (i = 0; i < currentModule.getValues().size() && var24 <= startY + 300.0F; ++i) {
                if (i >= this.valueStart) {
                    Value var25 = (Value) currentModule.getValues().get(i);
                    float x;
                    if (var25 instanceof Numbers) {
                        x = startX + 300.0F;
                        double current = (double) (68.0F * (((Number) var25.getValue()).floatValue() - ((Numbers) var25).getMinimum().floatValue()) / (((Numbers) var25).getMaximum().floatValue() - ((Numbers) var25).getMinimum().floatValue()));
                        RenderUtil.drawRect(x - 6.0F, var24 + 2.0F, (float) ((double) x + 75.0D), var24 + 3.0F, (new Color(50, 50, 50, (int) this.opacity.getOpacity())).getRGB());
                        RenderUtil.drawRect(x - 6.0F, var24 + 2.0F, (float) ((double) x + current + 6.5D), var24 + 3.0F, (new Color(61, 141, 255, (int) this.opacity.getOpacity())).getRGB());
                        RenderUtil.drawRect((float) ((double) x + current + 2.0D), var24, (float) ((double) x + current + 7.0D), var24 + 5.0F, (new Color(61, 141, 255, (int) this.opacity.getOpacity())).getRGB());
                        FontLoaders.Comfortaa18.drawStringWithShadow(var25.getName() + ": " + var25.getValue(), startX + 210.0F, var24, -1);
                        if (!Mouse.isButtonDown(0)) {
                            this.previousmouse = false;
                        }

                        if (this.isButtonHovered(x, var24 - 2.0F, x + 100.0F, var24 + 7.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                            if (!this.previousmouse && Mouse.isButtonDown(0)) {
                                current = ((Numbers) var25).getMinimum().doubleValue();
                                double max = ((Numbers) var25).getMaximum().doubleValue();
                                double inc = ((Numbers) var25).getIncrement().doubleValue();
                                double valAbs = (double) mouseX - ((double) x + 1.0D);
                                double perc = valAbs / 68.0D;
                                perc = Math.min(Math.max(0.0D, perc), 1.0D);
                                double valRel = (max - current) * perc;
                                double val = current + valRel;
                                val = (double) Math.round(val * (1.0D / inc)) / (1.0D / inc);
                                ((Numbers) var25).setValue(Double.valueOf(val));
                            }

                            if (!Mouse.isButtonDown(0)) {
                                this.previousmouse = false;
                            }
                        }

                        var24 += 20.0F;
                    }

                    if (var25 instanceof Option) {
                        x = startX + 300.0F;
                        FontLoaders.Comfortaa18.drawStringWithShadow(var25.getName(), startX + 210.0F, var24, -1);
                        RenderUtil.drawRect(x + 56.0F, var24, x + 76.0F, var24 + 1.0F, (new Color(255, 255, 255, (int) this.opacity.getOpacity())).getRGB());
                        RenderUtil.drawRect(x + 56.0F, var24 + 8.0F, x + 76.0F, var24 + 9.0F, (new Color(255, 255, 255, (int) this.opacity.getOpacity())).getRGB());
                        RenderUtil.drawRect(x + 56.0F, var24, x + 57.0F, var24 + 9.0F, (new Color(255, 255, 255, (int) this.opacity.getOpacity())).getRGB());
                        RenderUtil.drawRect(x + 77.0F, var24, x + 76.0F, var24 + 9.0F, (new Color(255, 255, 255, (int) this.opacity.getOpacity())).getRGB());
                        if (((Boolean) var25.getValue()).booleanValue()) {
                            RenderUtil.drawRect(x + 67.0F, var24 + 2.0F, x + 75.0F, var24 + 7.0F, (new Color(61, 141, 255, (int) this.opacity.getOpacity())).getRGB());
                        } else {
                            RenderUtil.drawRect(x + 58.0F, var24 + 2.0F, x + 65.0F, var24 + 7.0F, (new Color(150, 150, 150, (int) this.opacity.getOpacity())).getRGB());
                        }

                        if (this.isCheckBoxHovered(x + 56.0F, var24, x + 76.0F, var24 + 9.0F, mouseX, mouseY)) {
                            if (!this.previousmouse && Mouse.isButtonDown(0)) {
                                this.previousmouse = true;
                                this.mouse = true;
                            }

                            if (this.mouse) {
                                var25.setValue(Boolean.valueOf(!((Boolean) var25.getValue()).booleanValue()));
                                this.mouse = false;
                            }
                        }

                        if (!Mouse.isButtonDown(0)) {
                            this.previousmouse = false;
                        }

                        var24 += 20.0F;
                    }

                    if (var25 instanceof Mode) {
                        x = startX + 300.0F;
                        FontLoaders.Comfortaa18.drawStringWithShadow(var25.getName(), startX + 210.0F, var24, -1);
                        RenderUtil.drawRect(x - 5.0F, var24 - 5.0F, x + 90.0F, var24 + 15.0F, (new Color(56, 56, 56, (int) this.opacity.getOpacity())).getRGB());
                        RenderUtil.drawBorderRect((double) (x - 5.0F), (double) (var24 - 5.0F), (double) (x + 90.0F), (double) (var24 + 15.0F), (new Color(101, 81, 255, (int) this.opacity.getOpacity())).getRGB(), 2.0D);
                        FontLoaders.Comfortaa18.drawStringWithShadow(((Mode) var25).getModeAsString(), x + 40.0F - (float) (FontLoaders.Comfortaa18.getStringWidth(((Mode) var25).getModeAsString()) / 2), var24, -1);
                        if (this.isStringHovered(x, var24 - 5.0F, x + 100.0F, var24 + 15.0F, mouseX, mouseY)) {
                            if (Mouse.isButtonDown(0) && !this.previousmouse) {
                                Enum var26 = (Enum) ((Mode) var25).getValue();
                                int next = var26.ordinal() + 1 >= ((Mode) var25).getModes().length ? 0 : var26.ordinal() + 1;
                                var25.setValue(((Mode) var25).getModes()[next]);
                                this.previousmouse = true;
                            }

                            if (!Mouse.isButtonDown(0)) {
                                this.previousmouse = false;
                            }
                        }

                        var24 += 25.0F;
                    }
                }
            }
        }

    }

    public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= f && (float) mouseX <= g && (float) mouseY >= y && (float) mouseY <= y2;
    }

    public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
    }

    public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= f && (float) mouseX <= g && (float) mouseY >= y && (float) mouseY <= y2;
    }

    public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= f && (float) mouseX <= g && (float) mouseY >= y && (float) mouseY <= y2;
    }

    public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
    }

    public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return (float) mouseX >= x && (float) mouseX <= x2 && (float) mouseY >= y && (float) mouseY <= y2;
    }

    public void onGuiClosed() {
        this.opacity.setOpacity(0.0F);
    }
}
