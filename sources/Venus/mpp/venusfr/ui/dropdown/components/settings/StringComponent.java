/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown.components.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.List;
import mpp.venusfr.functions.settings.impl.StringSetting;
import mpp.venusfr.ui.dropdown.impl.Component;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.Cursors;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.glfw.GLFW;

public class StringComponent
extends Component {
    private final StringSetting setting;
    private boolean typing;
    private String text = "";
    private static final int X_OFFSET = 5;
    private static final int Y_OFFSET = 10;
    private static final int WIDTH_OFFSET = -9;
    private static final int TEXT_Y_OFFSET = -7;
    private boolean hovered = false;

    public StringComponent(StringSetting stringSetting) {
        this.setting = stringSetting;
        this.setHeight(24.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, float f, float f2) {
        super.render(matrixStack, f, f2);
        this.text = (String)this.setting.get();
        if (this.setting.isOnlyNumber() && !NumberUtils.isNumber(this.text)) {
            this.text = this.text.replaceAll("[a-zA-Z]", "");
        }
        float f3 = this.calculateX();
        float f4 = this.calculateY();
        float f5 = this.calculateWidth();
        String string = this.setting.getName();
        String string2 = this.setting.getDescription();
        String string3 = (String)this.setting.get();
        if (!this.typing && ((String)this.setting.get()).isEmpty()) {
            string3 = string2;
        }
        if (this.setting.isOnlyNumber() && !NumberUtils.isNumber(string3)) {
            string3 = string3.replaceAll("[a-zA-Z]", "");
        }
        float f6 = this.calculateHeight(string3, f5 - 1.0f);
        this.drawSettingName(matrixStack, string, f3, f4);
        this.drawBackground(f3, f4, f5, f6);
        this.drawTextWithLineBreaks(matrixStack, string3 + (this.typing && this.text.length() < 59 && System.currentTimeMillis() % 1000L > 500L ? "_" : ""), f3 + 1.0f, f4 + Fonts.montserrat.getHeight(6.0f) / 2.0f, f5 - 1.0f);
        if (this.isHovered(f, f2)) {
            if (MathUtil.isHovered(f, f2, f3, f4, f5, f6)) {
                if (!this.hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.IBEAM);
                    this.hovered = true;
                }
            } else if (this.hovered) {
                GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                this.hovered = false;
            }
        }
        this.setHeight(f6 + 12.0f);
    }

    private void drawTextWithLineBreaks(MatrixStack matrixStack, String string, float f, float f2, float f3) {
        String[] stringArray = string.split("\n");
        float f4 = f2;
        for (String string2 : stringArray) {
            List<String> list = this.wrapText(string2, 6.0f, f3);
            for (String string3 : list) {
                Fonts.montserrat.drawText(matrixStack, string3, f, f4, ColorUtils.rgba(255, 255, 255, 255), 6.0f);
                f4 += Fonts.montserrat.getHeight(6.0f);
            }
        }
    }

    private List<String> wrapText(String string, float f, float f2) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] stringArray = string.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray) {
            if (Fonts.montserrat.getWidth(string2, f) <= f2) {
                if (Fonts.montserrat.getWidth(stringBuilder.toString() + string2, f) <= f2) {
                    stringBuilder.append(string2).append(" ");
                    continue;
                }
                arrayList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder(string2).append(" ");
                continue;
            }
            if (!stringBuilder.toString().isEmpty()) {
                arrayList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
            stringBuilder = this.breakAndAddWord(string2, stringBuilder, f, f2, arrayList);
        }
        if (!stringBuilder.toString().isEmpty()) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    private StringBuilder breakAndAddWord(String string, StringBuilder stringBuilder, float f, float f2, List<String> list) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            String string2 = stringBuilder.toString() + c;
            if (Fonts.montserrat.getWidth(string2, f) <= f2) {
                stringBuilder.append(c);
                continue;
            }
            list.add(stringBuilder.toString());
            stringBuilder = new StringBuilder(String.valueOf(c));
        }
        return stringBuilder;
    }

    private float calculateX() {
        return this.getX() + 5.0f;
    }

    private float calculateY() {
        return this.getY() + 10.0f;
    }

    private float calculateWidth() {
        return this.getWidth() + -9.0f;
    }

    private float calculateHeight(String string, float f) {
        List<String> list = this.wrapText(string, 6.0f, f);
        int n = list.size();
        float f2 = Fonts.montserrat.getHeight(6.0f);
        float f3 = 1.5f;
        float f4 = 5.0f;
        return f4 + (float)n * f2 + (float)(n - 1);
    }

    private void drawSettingName(MatrixStack matrixStack, String string, float f, float f2) {
        Fonts.montserrat.drawText(matrixStack, string, f, f2 + -7.0f, ColorUtils.rgba(255, 255, 255, 255), 6.0f);
    }

    private void drawBackground(float f, float f2, float f3, float f4) {
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, 4.0f, ColorUtils.rgba(25, 26, 40, 165));
    }

    @Override
    public void charTyped(char c, int n) {
        if (this.setting.isOnlyNumber() && !NumberUtils.isNumber(String.valueOf(c))) {
            return;
        }
        if (this.typing && this.text.length() < 60) {
            this.text = this.text + c;
            this.setting.set(this.text);
        }
        super.charTyped(c, n);
    }

    @Override
    public void keyPressed(int n, int n2, int n3) {
        if (this.typing) {
            if (Screen.isPaste(n)) {
                this.pasteFromClipboard();
            }
            if (n == 259) {
                this.deleteLastCharacter();
            }
            if (n == 257) {
                this.typing = false;
            }
        }
        super.keyPressed(n, n2, n3);
    }

    @Override
    public void mouseClick(float f, float f2, int n) {
        this.typing = this.isHovered(f, f2) ? !this.typing : false;
        super.mouseClick(f, f2, n);
    }

    private boolean isControlDown() {
        return GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), 341) == 1 || GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), 345) == 1;
    }

    private void pasteFromClipboard() {
        try {
            this.text = this.text + GLFW.glfwGetClipboardString(Minecraft.getInstance().getMainWindow().getHandle());
            this.setting.set(this.text);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void deleteLastCharacter() {
        if (!this.text.isEmpty()) {
            this.text = this.text.substring(0, this.text.length() - 1);
            this.setting.set(this.text);
        }
    }

    @Override
    public boolean isVisible() {
        return (Boolean)this.setting.visible.get();
    }
}

