package src.Wiksi.ui.mainmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.utils.client.IMinecraft;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import net.minecraft.util.Session;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AltWidget implements IMinecraft {
    public final List<Alt> alts = new ArrayList<>();

    private float x;
    private final float y;

    public AltWidget() {

        y = 10;

    }


    public boolean open;

    private String altName = "";
    private boolean typing;
    private float scrollPre;
    private float scroll;

    public void updateScroll(int mouseX, int mouseY, float delta) {

        if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, 145, 100) && open) {
            scrollPre += delta * 10;
        }
    }

    public void render(MatrixStack stack, int x, int y) {
        scroll = MathUtil.fast(scroll, scrollPre, 10);

        this.x = mc.getMainWindow().getScaledWidth() - 110 - 45;
        float width = 145;

        float height = Math.min(20 + (open ? 10 + (alts.size() + 1) * (17) : 0), 100);

        DisplayUtils.drawShadow(this.x, this.y, width, height, 10, ColorUtils.rgba(0, 0, 10, 128));

        DisplayUtils.drawRoundedRect(this.x, this.y, width, height, new Vector4f(4, 4, 4, 4), ColorUtils.rgba(0, 0, 10, 128));
        Scissor.push();
        Scissor.setFromComponentCoordinates(this.x, this.y, width - 16, height);
        Fonts.montserrat.drawText(stack, mc.session.getUsername(), this.x + 6, this.y + 6, -1, 7);
        Scissor.unset();
        Scissor.pop();
        Fonts.icons.drawText(stack, open ? "C" : "B", this.x + width - 6 - Fonts.icons.getWidth(open ? "C" : "B", 7), this.y + 6.5f, -1, 7);
        if (open) {
            DisplayUtils.drawRectHorizontalW(this.x, this.y + 20, width, 5f, ColorUtils.rgba(64, 64, 64, 0), ColorUtils.rgba(64, 64, 64, 64));
            DisplayUtils.drawRectW(this.x, this.y + 20, width, 0.5f, ColorUtils.rgba(64, 64, 64, 255));
            Scissor.push();
            Scissor.setFromComponentCoordinates(this.x, this.y + 20, width, 100f - 20);
            float i = 0;
            for (Alt alt : alts) {
                DisplayUtils.drawRoundedRect(this.x + 5, this.y + 26 + i * 17 + scroll, width - 10, 15, 3, mc.session.getUsername().equals(alt.name) ? ColorUtils.rgba(30, 30, 36, 128) : ColorUtils.rgba(30, 30, 36, 64));
                Fonts.montserrat.drawText(stack, alt.name, this.x + 10, this.y + 26 + i * 17 + 4 + scroll, -1, 6);
                i++;
            }
            if (!alts.isEmpty() && 20 + (open ? 10 + (alts.size() + 1) * (17) : 0) > 100)
                scrollPre = MathHelper.clamp(scrollPre, -i * 17 + 50, 0);
            else {
                scrollPre = 0;
            }
            String textToDraw = altName;

            if (!typing && altName.isEmpty()) {
                textToDraw = "Введите ник";
            }

            DisplayUtils.drawRoundedRect(this.x + 5, this.y + 26 + i * 17 + scroll, width - 10,
                    15, 3, ColorUtils.rgba(30, 30, 36, 64));
            Fonts.montserrat.drawText(stack, textToDraw + (typing ? (System.currentTimeMillis() % 1000 > 500 ? "_" : "") : ""),
                    this.x + 10, this.y + 26 + i * 17 + 4 + scroll, ColorUtils.rgba(255, 255, 255, 64), 6);
            DisplayUtils.drawRoundedRect(this.x + 5 + 2, this.y + 26 + i * 17 + 2 + scroll,
                    Fonts.montserrat.getWidth(textToDraw + (typing ? (System.currentTimeMillis() % 1000 > 500 ? "_" : "") : ""),
                            6) + 7, 15 - 4, 2, ColorUtils.rgba(30, 30, 36, 64));
            Fonts.montserrat.drawText(stack, "+", this.x + width - 18,
                    this.y + 26 + i * 17 + 2 + scroll, -1, 10);
            Scissor.unset();
            Scissor.pop();
        }
    }

    public void onChar(char typed) {
        if (typing) {
            if (Fonts.montserrat.getWidth(altName, 6f) < 145 - 50) {
                altName += typed;
            }
        }
    }

    public void onKey(int key) {
        boolean ctrlDown = GLFW.glfwGetKey(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS ||
                GLFW.glfwGetKey(mc.getMainWindow().getHandle(), GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
        if (typing) {
            if (ctrlDown && key == GLFW.GLFW_KEY_V) {
                try {
                    altName += GLFW.glfwGetClipboardString(mc.getMainWindow().getHandle());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (key == GLFW.GLFW_KEY_BACKSPACE) {
                if (!altName.isEmpty()) {
                    altName = altName.substring(0, altName.length() - 1);
                }
            }
            if (key == GLFW.GLFW_KEY_ENTER) {
                if (altName.length() >= 3) {
                    alts.add(new Alt(altName));
                    AltConfig.updateFile();
                }
                typing = false;
            }
        }
    }

    public void click(int mouseX, int mouseY, int button) {
        float width = 145;

        if (MathUtil.isHovered(mouseX, mouseY, this.x, this.y, width, 20)) {
            open = !open;

            if (!open) {
                typing = false;
            }
        }
        if (!MathUtil.isHovered(mouseX, mouseY, this.x, this.y, width, 50)) {
            typing = false;
        }
        List<Alt> toRemove = new ArrayList<>();
        if (open) {
            float i = 0;
            for (Alt alt : alts) {

                if (MathUtil.isHovered(mouseX, mouseY, this.x + 5, this.y + 26 + i * 17 + scroll, width - 10, 15)) {
                    if (button == 0) {
                        AltConfig.updateFile();
                        mc.session = new Session(alt.name, UUID.randomUUID().toString(), "", "mojang");
                    } else {
                        toRemove.add(alt);
                        AltConfig.updateFile();
                    }
                }
                i++;
            }
            alts.removeAll(toRemove);
        }

        if (MathUtil.isHovered(mouseX, mouseY, this.x + 82, this.y + 26 + alts.size() * 17 + 2 + scroll, 10, 10)) {
            if (altName.length() >= 3) {
                alts.add(new Alt(altName));
                AltConfig.updateFile();
            }
            typing = false;
        }
        if (MathUtil.isHovered(mouseX, mouseY, this.x + 5, this.y + 26 + alts.size() * 17 + scroll, width - 10, 15)) {
            typing = !typing;
        }
    }

}
