/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mpp.venusfr.ui.mainmenu.Alt;
import mpp.venusfr.ui.mainmenu.AltConfig;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.Scissor;
import mpp.venusfr.utils.render.font.Fonts;
import net.minecraft.util.Session;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;

public class AltWidget
implements IMinecraft {
    public final List<Alt> alts = new ArrayList<Alt>();
    private float x;
    private final float y;
    public boolean open;
    private String altName = "";
    private boolean typing;
    private float scrollPre;
    private float scroll;

    public AltWidget() {
        this.y = 10.0f;
    }

    public void updateScroll(int n, int n2, float f) {
        if (MathUtil.isHovered(n, n2, this.x, this.y, 145.0f, 100.0f) && this.open) {
            this.scrollPre += f * 10.0f;
        }
    }

    public void render(MatrixStack matrixStack, int n, int n2) {
        this.scroll = MathUtil.fast(this.scroll, this.scrollPre, 10.0f);
        this.x = mc.getMainWindow().getScaledWidth() - 110 - 45;
        float f = 145.0f;
        float f2 = Math.min(20 + (this.open ? 10 + (this.alts.size() + 1) * 17 : 0), 100);
        DisplayUtils.drawShadow(this.x, this.y, f, f2, 10, ColorUtils.rgba(0, 0, 10, 30));
        DisplayUtils.drawRoundedRect(this.x, this.y, f, f2, new Vector4f(4.0f, 4.0f, 4.0f, 4.0f), ColorUtils.rgba(0, 0, 10, 30));
        Scissor.push();
        Scissor.setFromComponentCoordinates(this.x, this.y, f - 16.0f, f2);
        Fonts.montserrat.drawText(matrixStack, AltWidget.mc.session.getUsername(), this.x + 6.0f, this.y + 6.0f, -1, 7.0f);
        Scissor.unset();
        Scissor.pop();
        Fonts.icons.drawText(matrixStack, this.open ? "C" : "B", this.x + f - 6.0f - Fonts.icons.getWidth(this.open ? "C" : "B", 7.0f), this.y + 6.5f, -1, 7.0f);
        if (this.open) {
            DisplayUtils.drawRectHorizontalW(this.x, this.y + 20.0f, f, 5.0, ColorUtils.rgba(64, 64, 64, 0), ColorUtils.rgba(64, 64, 64, 64));
            DisplayUtils.drawRectW(this.x, this.y + 20.0f, f, 0.5, ColorUtils.rgba(64, 64, 64, 255));
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x, this.y + 20.0f, f, 80.0);
            float f3 = 0.0f;
            for (Alt alt : this.alts) {
                DisplayUtils.drawRoundedRect(this.x + 5.0f, this.y + 26.0f + f3 * 17.0f + this.scroll, f - 10.0f, 15.0f, 3.0f, AltWidget.mc.session.getUsername().equals(alt.name) ? ColorUtils.rgba(30, 30, 36, 128) : ColorUtils.rgba(30, 30, 36, 64));
                Fonts.montserrat.drawText(matrixStack, alt.name, this.x + 10.0f, this.y + 26.0f + f3 * 17.0f + 4.0f + this.scroll, -1, 6.0f);
                f3 += 1.0f;
            }
            this.scrollPre = !this.alts.isEmpty() && 20 + (this.open ? 10 + (this.alts.size() + 1) * 17 : 0) > 100 ? MathHelper.clamp(this.scrollPre, -f3 * 17.0f + 50.0f, 0.0f) : 0.0f;
            Object object = this.altName;
            if (!this.typing && this.altName.isEmpty()) {
                object = "nickname";
            }
            DisplayUtils.drawRoundedRect(this.x + 5.0f, this.y + 26.0f + f3 * 17.0f + this.scroll, f - 10.0f, 15.0f, 3.0f, ColorUtils.rgba(30, 30, 36, 64));
            Fonts.montserrat.drawText(matrixStack, (String)object + (this.typing ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), this.x + 10.0f, this.y + 26.0f + f3 * 17.0f + 4.0f + this.scroll, ColorUtils.rgba(255, 255, 255, 64), 6.0f);
            DisplayUtils.drawRoundedRect(this.x + 5.0f + 2.0f, this.y + 26.0f + f3 * 17.0f + 2.0f + this.scroll, Fonts.montserrat.getWidth((String)object + (this.typing ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), 6.0f) + 7.0f, 11.0f, 2.0f, ColorUtils.rgba(30, 30, 36, 64));
            Fonts.montserrat.drawText(matrixStack, "+", this.x + f - 18.0f, this.y + 26.0f + f3 * 17.0f + 2.0f + this.scroll, -1, 10.0f);
            Scissor.unset();
            Scissor.pop();
        }
    }

    public void onChar(char c) {
        if (this.typing && Fonts.montserrat.getWidth(this.altName, 6.0f) < 95.0f) {
            this.altName = this.altName + c;
        }
    }

    public void onKey(int n) {
        boolean bl;
        boolean bl2 = bl = GLFW.glfwGetKey(mc.getMainWindow().getHandle(), 341) == 1 || GLFW.glfwGetKey(mc.getMainWindow().getHandle(), 345) == 1;
        if (this.typing) {
            if (bl && n == 86) {
                try {
                    this.altName = this.altName + GLFW.glfwGetClipboardString(mc.getMainWindow().getHandle());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (n == 259 && !this.altName.isEmpty()) {
                this.altName = this.altName.substring(0, this.altName.length() - 1);
            }
            if (n == 257) {
                if (this.altName.length() >= 3) {
                    this.alts.add(new Alt(this.altName));
                    AltConfig.updateFile();
                }
                this.typing = false;
            }
        }
    }

    public void click(int n, int n2, int n3) {
        float f = 145.0f;
        if (MathUtil.isHovered(n, n2, this.x, this.y, f, 20.0f)) {
            boolean bl = this.open = !this.open;
            if (!this.open) {
                this.typing = false;
            }
        }
        if (!MathUtil.isHovered(n, n2, this.x, this.y, f, 50.0f)) {
            this.typing = false;
        }
        ArrayList<Alt> arrayList = new ArrayList<Alt>();
        if (this.open) {
            float f2 = 0.0f;
            for (Alt alt : this.alts) {
                if (MathUtil.isHovered(n, n2, this.x + 5.0f, this.y + 26.0f + f2 * 17.0f + this.scroll, f - 10.0f, 15.0f)) {
                    if (n3 == 0) {
                        AltConfig.updateFile();
                        AltWidget.mc.session = new Session(alt.name, UUID.randomUUID().toString(), "", "mojang");
                    } else {
                        arrayList.add(alt);
                        AltConfig.updateFile();
                    }
                }
                f2 += 1.0f;
            }
            this.alts.removeAll(arrayList);
        }
        if (MathUtil.isHovered(n, n2, this.x + 82.0f, this.y + 26.0f + (float)(this.alts.size() * 17) + 2.0f + this.scroll, 10.0f, 10.0f)) {
            if (this.altName.length() >= 3) {
                this.alts.add(new Alt(this.altName));
                AltConfig.updateFile();
            }
            this.typing = false;
        }
        if (MathUtil.isHovered(n, n2, this.x + 5.0f, this.y + 26.0f + (float)(this.alts.size() * 17) + this.scroll, f - 10.0f, 15.0f)) {
            this.typing = !this.typing;
        }
    }
}

