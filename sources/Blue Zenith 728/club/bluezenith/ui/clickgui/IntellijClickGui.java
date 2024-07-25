package club.bluezenith.ui.clickgui;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.*;
import club.bluezenith.util.MinecraftInstance;
import club.bluezenith.util.anim.Scroll;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class IntellijClickGui extends GuiScreen {
    private final Color background = new Color(40, 44, 52);
    private final Color background2 = new Color(49, 51, 53);
    private final Color background3 = new Color(79, 87, 95);
    private final Color background4 = new Color(74, 136, 199);
    private final Color background5 = new Color(61, 66, 75);
    private final Color background6 = new Color(33, 37, 43);
    private final Color background7 = new Color(50, 50, 50);
    private final Color color1 = new Color(213, 95, 222);
    private final Color color2 = new Color(239, 89, 111);
    private final Color color3 = new Color(229, 192, 123);
    private final Color color4 = new Color(225, 157, 94);
    private final Color color5 = new Color(133, 195, 117);
    private final Color color6 = new Color(72, 80, 96);
    //private final Color color7 = new Color(112, 118, 129);

    public IntellijClickGui() {
    }

    private final FontRenderer f = FontUtil.jetBrainsLight36;
    private final FontRenderer f2 = FontUtil.inter28;
    private final FontRenderer f3 = FontUtil.ICON_testFont;
    private final FontRenderer f4 = FontUtil.ICON_testFont2;
    private final ResourceLocation classImage = new ResourceLocation("club/bluezenith/ui/class.png");
    private final ResourceLocation classImage2 = new ResourceLocation("club/bluezenith/ui/class2.png");
    private final ResourceLocation classImage4 = new ResourceLocation("club/bluezenith/ui/class4.png");
    private final ResourceLocation interfacepng = new ResourceLocation("club/bluezenith/ui/interface.png");
    private final ResourceLocation ijlogo = new ResourceLocation("club/bluezenith/ui/ijlogo.png");
    private final ResourceLocation buttonz = new ResourceLocation("club/bluezenith/ui/buttonz.png");
    private final ResourceLocation closeX = new ResourceLocation("club/bluezenith/ui/x.png");
    private final ResourceLocation closeX2 = new ResourceLocation("club/bluezenith/ui/x2.png");
    private final ResourceLocation closeX3 = new ResourceLocation("club/bluezenith/ui/x3.png");
    private final ResourceLocation xbuttonred = new ResourceLocation("club/bluezenith/ui/xbuttonred.png");

    private final float yIncrement = f.FONT_HEIGHT + 4;
    private final float yIncrement2 = f.FONT_HEIGHT + 4;
    private final float sussyRectHeight = f.FONT_HEIGHT + 6;
    private final float moduleTreeWidth = 150;
    private final float lol = (yIncrement / 2f - getHalfHeight());
    private final float xOffset = 45;
    private final float yOffset = 45;
    private float prevX = 0;
    private float prevY = 0;
    private Module selectedModule = null;
    private List<Value<?>> l = Collections.emptyList();
    private boolean mousePressed = false;
    private float panelHeight = 0;
    private final Scroll scroll = new Scroll(0);
    private final ResourceLocation res = new ResourceLocation("club/bluezenith/ui/folder.png");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float width = 800;
        float x = xOffset;
        float y = yOffset;
        y = drawSussyRect(x, y, mouseX, mouseY, width);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.crop(x, y, x + moduleTreeWidth, y + panelHeight);
        x = drawModulesTree(x, y, mouseX, mouseY, width);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        y = drawValues(x, y, mouseX, mouseY, width);
        super.drawScreen(mouseX, mouseY, partialTicks);
        mousePressed = Mouse.isButtonDown(0) || Mouse.isButtonDown(1);
        panelHeight = Math.max(400, sussyRectHeight + 4 + (yIncrement * l.size()) + sussyRectHeight);
    }

    private float drawModulesTree(float x, float y, int mouseX, int mouseY, float width) {
        float amountScrolled = scroll.getAmountScrolled();
        RenderUtil.rect(x, y, x + moduleTreeWidth, y + panelHeight, background6);
        float tempY = y;
        final float middle = yIncrement2 / 2f - f3.FONT_HEIGHT / 2f;
        final float middle2 = yIncrement2 / 2f - f2.FONT_HEIGHT / 2f;
        f3.drawString("B", x + 5, tempY + middle - amountScrolled, new Color(171, 178, 191).getRGB());
        f4.drawString(" A", x + 6, tempY + middle - amountScrolled, new Color(171, 178, 191).getRGB());
        f2.drawString("Modules", x + f3.getStringWidth("A") + f4.getStringWidth(" A") + 5, tempY + middle2 - amountScrolled, new Color(171, 178, 191).getRGB());
        tempY += yIncrement2;
        for (int i = 0; i < ModuleCategory.values().length; i++) {
            ModuleCategory cat = ModuleCategory.values()[i];
            final String str = (cat.showContent ? "B" : "C");
            final float strWidth = f3.getStringWidthF(str);
            if (tempY - amountScrolled > y - yIncrement2 && tempY - amountScrolled < y + panelHeight) {
                f3.drawString(str, x + strWidth + 5, tempY + middle - amountScrolled, new Color(171, 178, 191).getRGB());
                RenderUtil.drawImage(res, x + strWidth + 3 + f3.getStringWidth("A "), tempY + middle - amountScrolled - 3, 10, 8.5f, 1);
                f2.drawString(cat.displayName, x + f3.getStringWidth("A ") + f4.getStringWidth(" A") + 5, tempY + middle2 - amountScrolled, new Color(171, 178, 191).getRGB());
                if (i(mouseX, mouseY, x, tempY - amountScrolled, x + moduleTreeWidth, tempY - amountScrolled + yIncrement2) && Mouse.isButtonDown(1) && !mousePressed) {
                    cat.showContent = !cat.showContent;
                    toggleSound();
                }
            }
            tempY += yIncrement2;
            if (cat.showContent) {
                for (Module m : BlueZenith.getBlueZenith().getModuleManager().getModules()) {
                    if (m.getCategory() == cat) {
                        if (tempY - amountScrolled > y - yIncrement2 && tempY - amountScrolled < y + panelHeight) {
                            if (i(mouseX, mouseY, x, tempY - amountScrolled, x + moduleTreeWidth, tempY - amountScrolled + yIncrement2) && !mousePressed) {
                                if (Mouse.isButtonDown(0)) {
                                    m.toggle();
                                    toggleSound();
                                } else if (Mouse.isButtonDown(1) && !m.getValues().isEmpty()) {
                                    selectedModule = m;
                                    toggleSound();
                                }
                            }
                            if (!m.getValues().isEmpty()) {
                                RenderUtil.drawImage(classImage, x + (strWidth * 2) + 5 - 11 + 15, tempY + middle2 - amountScrolled - 2.5f, 10, 10, 1);
                            }
                            else {
                                RenderUtil.drawImage(interfacepng, x + (strWidth * 2) + 5 - 11 + 15, tempY + middle2 - amountScrolled - 2.5f, 10, 10, 1);
                            }
                            f2.drawString(m.getName(), x + (strWidth * 2) + 5 + 15, tempY + middle2 - amountScrolled, m.getState() ? new Color(171, 178, 191).getRGB() : new Color(171, 178, 191).darker().getRGB());
                        }
                        tempY += yIncrement2;
                    }
                }
            }
        }
        scroll.update(tempY - y, y, y + panelHeight);
        return x + moduleTreeWidth;
    }

    private float drawSussyRect(float x, float y, int mouseX, int mouseY, float width) {
        width -= moduleTreeWidth;
        RenderUtil.rect(x, y - 15.5f, x + width, y, background6.getRGB());
        RenderUtil.drawImage(ijlogo, x + 3.5f, y - 12, 9, 9, 1);

        float offset = 0;
        RenderUtil.rect(x + 20f, y - 7f + (((float)f2.FONT_HEIGHT) / 2), x + 19.5f + f2.getStringWidth("F"), y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("File", x + 19.5f, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("File") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("E") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Edit", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Edit") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("V") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("View", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("View") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("N") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Navigate", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Navigate") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("C") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Code", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Code") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("R") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Refactor", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Refactor") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("B") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Build", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Build") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("R") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Run", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Run") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("T") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Tools", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Tools") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("G") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Git", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Git") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("W") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Window", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Window") + 7;
        RenderUtil.rect(x + 20f + offset, y - 7 + (((float)f2.FONT_HEIGHT) / 2), x + 20f + f2.getStringWidth("H") + offset, y - 7.5f + (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        f2.drawString("Help", x + 19.5f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(171, 178, 191).getRGB());
        offset += f2.getStringWidth("Help") + 7;

        RenderUtil.drawImage(buttonz, x + width - 65.5f, y - 15.5f, 65.5f, 15.5f, 1);
        if (i(mouseX, mouseY, x + width - 23f, y - 15.5f, x + width, y)) {
            if (Mouse.isButtonDown(0) && !mousePressed) {
                MinecraftInstance.mc.displayGuiScreen(null);

                if (MinecraftInstance.mc.currentScreen == null) {
                    MinecraftInstance.mc.setIngameFocus();
                }
            }
            RenderUtil.drawImage(xbuttonred, x + width - 23.5f, y - 15.5f, 23.5f, 15.5f, 1);
        }

        f2.drawString("blue-zenith-727" + (selectedModule == null ? "" : (" - " + selectedModule.getName() + ".java")), x + 25f + offset, y - 7.5f - (((float)f2.FONT_HEIGHT) / 2), new Color(120, 126, 129).getRGB());
        RenderUtil.rect(x, y, x + width, y + sussyRectHeight + 2, new Color(51, 56, 65).getRGB());
        RenderUtil.rect(x, y + 0.5f, x + width - 0.5f, y + sussyRectHeight + 1.5f, background6.getRGB());

        String displayString = selectedModule == null ? "" : selectedModule.getName() + ".java";
        float moduleNameWidth = f.getStringWidthF(displayString);
        float rectWidth = (18 + moduleNameWidth) * 1.05F;
        float classIcon = 9;

        if (selectedModule != null && i(mouseX, mouseY, x + rectWidth - 11, y + (sussyRectHeight / 2f - 6f / 2f), x + rectWidth - 11 + 6, y + (sussyRectHeight / 2f - 6f / 2f) + 6) && Mouse.isButtonDown(0) && !mousePressed) {
            selectedModule = null;
        }
        if (selectedModule != null) {
            if (i(mouseX, mouseY, x + 1, y + 1, x + rectWidth - 1, y + sussyRectHeight - 1)){
                RenderUtil.rect(x + 1, y + 1, x + rectWidth - 1, y + sussyRectHeight - 1, new Color(50, 56, 68).getRGB());
                if (i(mouseX, mouseY, x + rectWidth - 11, y + (sussyRectHeight / 2f - 6f / 2f), x + rectWidth - 11 + 6, y + (sussyRectHeight / 2f - 6f / 2f) + 6)) {
                    RenderUtil.drawImage(closeX3, x + rectWidth - 12.5f, y + (sussyRectHeight / 2f - 9f / 2f), 9f, 9f, 1);
                }
                else {
                    RenderUtil.drawImage(closeX2, x + rectWidth - 11, y + (sussyRectHeight / 2f - 6f / 2f), 6f, 6f, 1);
                }
                RenderUtil.drawImage(classImage4, x + 4, y + (sussyRectHeight / 2f - classIcon / 2f) - 0.5f, 10, 10, 1);
            }
            else {
                RenderUtil.rect(x + 1, y + 1, x + rectWidth - 1, y + sussyRectHeight - 1, background5.getRGB());
                RenderUtil.drawImage(closeX, x + rectWidth - 11, y + (sussyRectHeight / 2f - 6f / 2f), 6f, 6f, 1);
                RenderUtil.drawImage(classImage2, x + 4, y + (sussyRectHeight / 2f - classIcon / 2f) - 0.5f, 10, 10, 1);
            }
            RenderUtil.rect(x + 1, y + sussyRectHeight - 1, x + rectWidth - 1, y + sussyRectHeight + 1, background4.getRGB());

            f2.drawString(displayString, x + classIcon + 6, y + (sussyRectHeight / 2f - f2.FONT_HEIGHT / 2f), Color.WHITE.getRGB());
        }
        return y + sussyRectHeight + 2;
    }

    private float drawValues(float x, float aY, int mouseX, int mouseY, float width) {
        HashMap<String, Boolean> Support = new HashMap<>(); {
            Support.put("All", false);
            Support.put("Speed", true);
            Support.put("Flight", false);
        }

        width -= moduleTreeWidth * 2f;
        final float rectWidth = 29;
        RenderUtil.rect(x, aY, x + width, aY + panelHeight, background.getRGB());
        RenderUtil.rect(x - 5, aY, x + rectWidth, aY + panelHeight, background.getRGB());
        RenderUtil.rect(x - 5, aY, x - 5 + 0.5f, aY + panelHeight, new Color(51, 56, 65).getRGB());
        RenderUtil.rect(x + rectWidth, aY, x + rectWidth + 0.5f, aY + panelHeight, background3.getRGB());
        float y = aY + f.FONT_HEIGHT + 4;
        float indent = f.getStringWidthF("  ");
        if (selectedModule != null) {
            l = selectedModule.getValues().stream().filter(Value::isVisible).collect(Collectors.toList());
            String prefix = "private";
            f.drawString(ColorUtil.toString(color1) + "public class " + ColorUtil.toString(color3) + selectedModule.getName() + ColorUtil.toString(color1) + " extends " + ColorUtil.toString(color3) + "Module " + ColorUtil.toString(new Color(176, 176, 177)) + "{", x + rectWidth + 5, y - f.FONT_HEIGHT - 1, color1.getRGB());
            for (int i = 0; i < 30; i++) {
                f.drawString((i + 1) + "", x, y - f.FONT_HEIGHT - 1 + ((f.FONT_HEIGHT + 4) * i), color6.getRGB());
            }
            for (int i = 0; i < l.size(); i++) {
                Value<?> v = l.get(i);
                // God this is a mess...

                String valueName = v.name.replaceAll(" ", "_");
                String text = ColorUtil.toString(color3) + "Object " + ColorUtil.toString(color2) + "" + valueName + " " + ColorUtil.toString(new Color(176, 176, 177)) + "= " + ColorUtil.toString(color3) + "Settings." + ColorUtil.toString(color2) + "unsupported";
                if (v instanceof IntegerValue) {
                    text = "int " + ColorUtil.toString(color2) + valueName + ColorUtil.toString(new Color(176, 176, 177)) + " = " + ColorUtil.toString(color4) + v.get();

                } else if (v instanceof FloatValue) {
                    text = "float " + ColorUtil.toString(color2) + valueName + ColorUtil.toString(new Color(176, 176, 177)) + " = " + ColorUtil.toString(color4) + v.get() + "F";

                } else if (v instanceof StringValue) {
                    text = ColorUtil.toString(color3) + "String " + ColorUtil.toString(color2) + valueName + ColorUtil.toString(new Color(176, 176, 177)) + " = " + ColorUtil.toString(color5) + "\"" + v.get() + "\"";

                } else if (v instanceof ModeValue) {
                    text = ColorUtil.toString(color3) + "String" + ColorUtil.toString(new Color(176, 176, 177)) + "[] " + ColorUtil.toString(color2) + valueName + ColorUtil.toString(new Color(176, 176, 177)) + " =" + ColorUtil.toString(color1) + " new " + ColorUtil.toString(color3) + "String" + ColorUtil.toString(new Color(176, 176, 177)) + "[]{" + ColorUtil.toString(color5) + "\"" + v.get() + "\"" + ColorUtil.toString(new Color(176, 176, 177)) + "}";
                    ModeValue l = (ModeValue) v;
                    if (i(mouseX, mouseY, x, y, x + width, y + yIncrement) && Mouse.isButtonDown(0) && !mousePressed) {
                        l.next();
                        toggleSound();
                    }

                } else if (v instanceof BooleanValue) {
                    text = "boolean " + ColorUtil.toString(color2) + valueName + ColorUtil.toString(new Color(176, 176, 177)) + " = " + ColorUtil.toString(color1) + v.get();
                    BooleanValue f = (BooleanValue) v;
                    if (i(mouseX, mouseY, x, y, x + width, y + yIncrement) && Mouse.isButtonDown(0) && !mousePressed) {
                        f.set(!f.get());
                        toggleSound();
                    }

                } else if (v instanceof ActionValue) {
                    text = ColorUtil.toString(color3) + "Runnable " + ColorUtil.toString(color2) + valueName + ColorUtil.toString(new Color(176, 176, 177)) + " = () -> {}";
                    ActionValue f = (ActionValue) v;
                    if (i(mouseX, mouseY, x, y, x + width, y + yIncrement) && Mouse.isButtonDown(0) && !mousePressed) {
                        f.next();
                        toggleSound();
                    }
                }

                f.drawString(prefix + " " + text + ColorUtil.toString(new Color(176, 176, 177)) + ";", x + rectWidth + 5 + indent, y + lol, color1.getRGB());
                y += yIncrement;
            }
            f.drawString("}", x + rectWidth + 5, y - f.FONT_HEIGHT - 1 + yIncrement, new Color(176, 176, 177).getRGB());
        }
        return y;
    }

    public boolean i(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        return mouseX >= x && mouseY >= y && mouseX <= x2 && mouseY <= y2;
    }

    public final void toggleSound() {
        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    private float getHalfHeight() {
        return f.FONT_HEIGHT / 2f;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        BlueZenith.getBlueZenith().getModuleManager().getAndCast(ClickGUI.class).setState(false);
    }
}
