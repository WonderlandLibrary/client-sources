/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.dropdown;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector4f;

public class SearchField {
    Minecraft mc = Minecraft.getInstance();
    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private boolean isFocused;
    private boolean typing;
    private final String placeholder;

    public SearchField(int n, int n2, int n3, int n4, String string) {
        this.x = n;
        this.y = n2;
        this.width = n3;
        this.height = n4;
        this.placeholder = string;
        this.text = "";
        this.isFocused = false;
        this.typing = false;
    }

    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.mc.gameRenderer.setupOverlayRendering(2);
        DisplayUtils.drawShadow(this.x, this.y, this.width, this.height, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect((float)this.x, (float)this.y, (float)this.width, (float)this.height, new Vector4f(5.0f, 5.0f, 5.0f, 5.0f), ColorUtils.rgba(25, 26, 40, 165));
        String string = this.text.isEmpty() && !this.typing ? this.placeholder : this.text;
        String string2 = this.typing && System.currentTimeMillis() % 1000L > 500L ? "_" : "";
        Fonts.montserrat.drawText(matrixStack, string + string2, (float)(this.x + 5), (float)(this.y + (this.height - 8) / 2 + 1), ColorUtils.rgb(255, 255, 255), 6.0f);
    }

    public boolean charTyped(char c, int n) {
        if (this.isFocused) {
            this.text = this.text + c;
            return false;
        }
        return true;
    }

    public boolean keyPressed(int n, int n2, int n3) {
        if (this.isFocused && n == 259 && !this.text.isEmpty()) {
            this.text = this.text.substring(0, this.text.length() - 1);
            return false;
        }
        if (n == 257 || n == 256) {
            this.typing = false;
        }
        return true;
    }

    public boolean mouseClicked(double d, double d2, int n) {
        if (!MathUtil.isHovered((float)d, (float)d2, this.x, this.y, this.width, this.height)) {
            this.isFocused = false;
        }
        this.typing = this.isFocused = MathUtil.isHovered((float)d, (float)d2, this.x, this.y, this.width, this.height);
        return this.isFocused;
    }

    public boolean isEmpty() {
        return this.text.isEmpty();
    }

    public void setFocused(boolean bl) {
        this.isFocused = bl;
    }

    public void setMc(Minecraft minecraft) {
        this.mc = minecraft;
    }

    public void setX(int n) {
        this.x = n;
    }

    public void setY(int n) {
        this.y = n;
    }

    public void setWidth(int n) {
        this.width = n;
    }

    public void setHeight(int n) {
        this.height = n;
    }

    public void setText(String string) {
        this.text = string;
    }

    public void setTyping(boolean bl) {
        this.typing = bl;
    }

    public Minecraft getMc() {
        return this.mc;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getText() {
        return this.text;
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public boolean isTyping() {
        return this.typing;
    }

    public String getPlaceholder() {
        return this.placeholder;
    }
}

