package wtf.shiyeno.ui.clickgui.objects;

import com.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector4i;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.*;
import wtf.shiyeno.ui.clickgui.Window;
import wtf.shiyeno.ui.clickgui.binds.BindWindow;
import wtf.shiyeno.ui.clickgui.objects.sets.*;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.*;
import wtf.shiyeno.util.render.animation.AnimationMath;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.ui.clickgui.objects.ModuleObject;
import wtf.shiyeno.ui.clickgui.objects.Object;
import wtf.shiyeno.ui.clickgui.objects.sets.BindObject;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.ColorUtil;
import wtf.shiyeno.util.render.RenderUtil;
import wtf.shiyeno.util.render.animation.AnimationMath;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ModuleObject extends Object {

    public Function function;
    public ArrayList<Object> object = new ArrayList<>();
    public float animation;
    public BindWindow bindWindow;
    public boolean bindingState = false;
    boolean binding;

    @Override
    public void exit() {
        super.exit();
        for (Object object1 : object) {
            object1.exit();
        }
    }

    public ModuleObject(Function function) {
        this.function = function;
        for (Setting setting : function.settingList) {
            if (setting instanceof BooleanOption option) {
                object.add(new BooleanObject(this, option));
            }
            if (setting instanceof SliderSetting option) {
                object.add(new SliderObject(this, option));
            }
            if (setting instanceof ModeSetting option) {
                object.add(new ModeObject(this, option));
            }
            if (setting instanceof MultiBoxSetting option) {
                object.add(new MultiObject(this, option));
            }
            if (setting instanceof BindSetting option) {
                object.add(new BindObject(this, option));
            }
            if (setting instanceof ButtonSetting option) {
                object.add(new ButtonObject(this, option));
            }
        }
        bindWindow = new BindWindow(this);
        bindWindow.x = 10 + ThreadLocalRandom.current().nextFloat(0, 200);
        bindWindow.y = 10 + ThreadLocalRandom.current().nextFloat(0, 200);
        bindWindow.width = 178 / 2f;
        bindWindow.height = 73 / 2f;
    }

    public boolean isBinding = false;

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        for (Object object1 : object) {
            object1.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (isHovered(mouseX,mouseY, 23)) {
            if (mouseButton == 0)
                function.toggle();
            if (mouseButton == 2)
                isBinding = !isBinding;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        for (Object object1 : object) {
            object1.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        if (isBinding) {

            if (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == GLFW.GLFW_KEY_DELETE ||
                    keyCode == GLFW.GLFW_KEY_ESCAPE) {
                function.bind = 0;
            } else {
                function.bind = keyCode;
                Managment.NOTIFICATION_MANAGER.add("Module " + TextFormatting.GRAY + function.name + TextFormatting.WHITE + " was keyed " + ClientUtil.getKey(keyCode).toUpperCase(), "Module Info", 2);
            }
            isBinding = false;
        }

        for (Object obj : object) {
            if (obj instanceof BindObject m) {
                if (m.isBinding) {
                    if (keyCode == GLFW.GLFW_KEY_BACKSPACE || keyCode == GLFW.GLFW_KEY_DELETE ||
                            keyCode == GLFW.GLFW_KEY_ESCAPE) {
                        m.set.setKey(0);
                        m.isBinding = false;
                        continue;
                    }
                    m.set.setKey(keyCode);
                    Managment.NOTIFICATION_MANAGER.add("Module " + TextFormatting.GRAY + m.object.function.name + TextFormatting.WHITE + " was binned on the button " + ClientUtil.getKey(keyCode).toUpperCase(), "Module Info", 2);
                    m.isBinding = false;
                }
            }
            obj.keyTyped(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
    }

    @Override
    public void draw(MatrixStack stack, int mouseX, int mouseY) {
        super.draw(stack, mouseX, mouseY);
        RenderUtil.Render2D.drawRoundOutline(x, y, width, height, 3, 0f, ColorUtil.rgba(30, 30, 30, 0), new Vector4i(
                ColorUtil.rgba(30, 30, 30, 255),
                ColorUtil.rgba(30, 30, 30, 255),
                ColorUtil.rgba(30, 30, 30, 255),
                ColorUtil.rgba(30, 30, 30, 255)
        ));
        animation = AnimationMath.fast(animation, function.state ? 1 : 0, 5);
        RenderUtil.Render2D.drawRoundOutline(x, y, width, height, 3, 0f, ColorUtil.rgba(20, 20, 20, 255), new Vector4i(
                ColorUtil.rgba(20, 20, 20, 0),
                ColorUtil.rgba(20, 20, 20, 0),
                ColorUtil.rgba(20, 20, 20, 0),
                ColorUtil.rgba(20, 20, 20, 0)
        ));

        Fonts.msBold[15].drawString(stack, function.name, x + 10, y + 10, ColorUtil.interpolateColor(ColorUtil.rgba(100, 100, 100, 255), -1, animation));
        if (!function.settingList.isEmpty()) {
            RenderUtil.Render2D.drawRect(x + 10, y + 20, width - 20, 1f, ColorUtil.rgba(30, 30, 30, 255));
        }
        drawObjects(stack, mouseX, mouseY);
    }

    public void drawObjects(MatrixStack stack, int mouseX, int mouseY) {
        float offset = 3;
        for (Object object : object) {
            if (object.setting.visible()) {
                object.x = x;
                object.y = y + 22 + offset;
                object.width = 160;
                object.height = 16;
                object.draw(stack, mouseX, mouseY);
                offset += object.height;
            }
        }
    }
}