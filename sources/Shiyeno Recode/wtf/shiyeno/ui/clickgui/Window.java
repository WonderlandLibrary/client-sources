package wtf.shiyeno.ui.clickgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import wtf.shiyeno.util.RoleHelper;
import wtf.shiyeno.util.animations.Animation;
import wtf.shiyeno.util.animations.Direction;
import wtf.shiyeno.util.animations.impl.DecelerateAnimation;
import wtf.shiyeno.util.animations.impl.EaseBackIn;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.ui.clickgui.configs.ConfigDrawing;
import wtf.shiyeno.ui.clickgui.objects.ModuleObject;
import wtf.shiyeno.ui.clickgui.objects.Object;
import wtf.shiyeno.ui.clickgui.objects.sets.BindObject;
import wtf.shiyeno.ui.clickgui.theme.ThemeDrawing;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.render.*;
import wtf.shiyeno.util.render.animation.AnimationMath;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static wtf.shiyeno.util.IMinecraft.mc;

public class Window extends Screen {

    private Vector2f position = new Vector2f(0, 0);

    public static Vector2f size = new Vector2f(500, 400);

    public static int dark = new Color(18, 19, 25).getRGB();
    public static int foreground = new Color(142, 142, 142).getRGB();
    public static int light = new Color(17, 24, 39).getRGB();

    private Type currentCategory = Type.Combat;

    private ThemeDrawing themeDrawing = new ThemeDrawing();
    private ConfigDrawing configDrawing = new ConfigDrawing();

    private ArrayList<ModuleObject> objects = new ArrayList<>();

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        scrolling += (float) (delta * 30);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    public Window(ITextComponent titleIn) {
        super(titleIn);
        scrolling = 0;
        for (Function function : Managment.FUNCTION_MANAGER.getFunctions()) {
            objects.add(new ModuleObject(function));
        }

    }

    @Override
    protected void init() {
        super.init();
        size = new Vector2f(450, 250);
        position = new Vector2f(mc.getMainWindow().scaledWidth() / 2f - size.x / 2f, mc.getMainWindow().scaledHeight() / 2f - size.y / 2f);
    }

    public static float scrolling;
    public static float scrollingOut;

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(
                position.x - 2,
                position.y - 2,
                size.x + 4,
                size.y + 4);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        var window = mc.getMainWindow();

        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();

        float bar = 100;
        mc.gameRenderer.setupOverlayRendering(2);

        RenderUtil.Render2D.drawGradientRound(0, 0, window.getScaledWidth(), window.scaledHeight(),
                0,
                RenderUtil.reAlphaInt(ColorUtil.getColorStyle(0), 30),
                RenderUtil.reAlphaInt(ColorUtil.getColorStyle(90), 30),
                RenderUtil.reAlphaInt(ColorUtil.getColorStyle(180), 30),
                RenderUtil.reAlphaInt(ColorUtil.getColorStyle(270), 30)
        );

        GlStateManager.pushMatrix();

        RenderUtil.Render2D.drawRoundedCorner(position.x, position.y, size.x, size.y, new Vector4f(0, 0, 0, 0), dark);
        RenderUtil.Render2D.drawGradientRound(position.x - 2, position.y - 2, size.x + 4, size.y + 4, 0,
                ColorUtil.getColorStyle(0),
                ColorUtil.getColorStyle(90),
                ColorUtil.getColorStyle(180),
                ColorUtil.getColorStyle(270)
        );
        RenderUtil.Render2D.drawRect(position.x + bar, position.y, size.x - bar, size.y, ColorUtil.rgba(20, 20, 20, 255));
        RenderUtil.Render2D.drawRect(position.x, position.y, bar, size.y, ColorUtil.rgba(20, 20, 20, 255));
        int darkerGray = new Color(20, 20, 20).getRGB();
        RenderUtil.Render2D.drawRect(position.x, position.y, bar, size.y, darkerGray);

        int grayColor = new Color(100, 100, 100).getRGB();
        int whiteColor = new Color(255, 255, 255).getRGB();

        Fonts.logo[23].drawString(matrixStack, "A", position.x + bar / 2 - Fonts.msBold[18].getWidth("SHIYENO") / 2f - 15, position.y + 10.5, -1);
        Fonts.msBold[23].drawCenteredString(matrixStack, "SHIYENO", position.x + bar / 2 + 8, position.y + 10, -1);
        RenderUtil.Render2D.drawRect(position.x + 5, position.y + size.y - 220, bar - 8, 1f, RenderUtil.reAlphaInt(foreground, 255));
        float len = 0;
        for (Type type : Type.values()) {
            if (type != Type.Scripts && type != Type.Configs) {

                type.anim = AnimationMath.fast((float) type.anim, type == currentCategory ? 1 : 0, 10);

                RenderUtil.Render2D.drawShadow(position.x + 5f, position.y + 35 + len, (float) bar - 8, 20F, (int) (15),
                        RenderUtil.reAlphaInt(ColorUtil.getColorStyle(0), (int) (100 * type.anim)),
                        RenderUtil.reAlphaInt(ColorUtil.getColorStyle(90), (int) (100 * type.anim)),
                        RenderUtil.reAlphaInt(ColorUtil.getColorStyle(180), (int) (100 * type.anim)),
                        RenderUtil.reAlphaInt(ColorUtil.getColorStyle(270), (int) (100 * type.anim)));

                RenderUtil.Render2D.drawGradientRound(position.x + 5f, position.y + 35 + len, (float) bar - 8, 20, 3,
                        RenderUtil.reAlphaInt(ColorUtil.getColorStyle(0), (int) (100 * type.anim)),
                        RenderUtil.reAlphaInt(ColorUtil.getColorStyle(90), (int) (100 * type.anim)),
                        RenderUtil.reAlphaInt(ColorUtil.getColorStyle(180), (int) (100 * type.anim)),
                        RenderUtil.reAlphaInt(ColorUtil.getColorStyle(270), (int) (100 * type.anim)));

                int textColor = (type == currentCategory) ? whiteColor : grayColor;
                Fonts.icons2[23].drawString(matrixStack, type.image, position.x + 9f, position.y + 41 + len, textColor);
                Fonts.msBold[20].drawString(matrixStack, type.name(), position.x + 27f, position.y + 40 + len, textColor);

                len += 30;
            }
        }

        RenderUtil.Render2D.drawRect(position.x + 5, position.y + size.y - 35, bar - 8, 1f, RenderUtil.reAlphaInt(foreground, 255));
        RenderUtil.Render2D.drawShadow(position.x + 5, position.y + size.y - 30, 25, 25, 15, ColorUtil.rgba(0, 0, 0, 64));
        if (ClientUtil.me != null) {
            GlStateManager.color4f(0, 0, 0, 0);
            GlStateManager.bindTexture(RenderUtil.Render2D.downloadImage(ClientUtil.me.getAvatarUrl()));
            RenderUtil.Render2D.drawTexture(position.x + 5, position.y + size.y - 30, 25, 25, 12.5f, 1);
        }

        var roleHelper = new RoleHelper(Managment.USER_PROFILE.getRole());
        int roleColor = roleHelper.getRoleColor();

        Fonts.gilroyBold[15].drawString(matrixStack, Managment.USER_PROFILE.getName(), position.x + 35, position.y + size.y - 28, roleColor);

        Fonts.gilroy[13].drawString(matrixStack, "UID: " + Managment.USER_PROFILE.getUid(), position.x + 35, position.y + size.y - 19, foreground);
        Fonts.gilroy[13].drawString(matrixStack, "TILL: " + Managment.USER_PROFILE.getExpiration(), position.x + 35, position.y + size.y - 12, foreground);
        if (currentCategory == Type.Theme) {
            themeDrawing.draw(matrixStack, mouseX, mouseY, position.x + 100, position.y, size.x - bar, size.y);
            BloomHelper.draw(25, 1.5f, false);
        }
        if (currentCategory == Type.Configs) {
            configDrawing.draw(matrixStack, mouseX, mouseY, position.x + 100, position.y, size.x - bar, size.y);
            BloomHelper.draw(10, 1f, false);
        }
        if (currentCategory != Type.Theme && currentCategory != Type.Configs)
            drawObjects(matrixStack, mouseX, mouseY);

        scrollingOut = AnimationMath.fast(scrollingOut, scrolling, 15);

        StencilUtil.initStencilToWrite();
        RenderUtil.Render2D.drawRoundedCorner(position.x, position.y, size.x, size.y, new Vector4f(0, 0, 0, 0f), -1);
        StencilUtil.readStencilBuffer(0);
        StencilUtil.uninitStencilBuffer();

        GlStateManager.popMatrix();

        mc.gameRenderer.setupOverlayRendering();
        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (currentCategory == Type.Configs)
            configDrawing.charTyped(codePoint);
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (ModuleObject m : objects) {
            if (m.function.category == currentCategory)
                m.keyTyped(keyCode, scanCode, modifiers);
        }
        if (currentCategory == Type.Configs)
            configDrawing.keyTyped(keyCode);

        if(keyCode == 256){
            mc.displayGuiScreen(null);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();
        for (ModuleObject m : objects) {
            if (m.function.category == currentCategory)
                m.mouseReleased((int) mouseX, (int) mouseY, button);
        }
        for (Object object : objects) {
            if (object instanceof ModuleObject m) {
                m.bindWindow.mouseUn();
                for (Object object1 : m.object) {
                    if (object1 instanceof BindObject no) {
                        no.bindWindow.mouseUn();
                    }
                }
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        super.onClose();
        for (ModuleObject m : objects) {
            m.exit();
        }
    }

    public void drawObjects(MatrixStack stack, int mouseX, int mouseY) {
        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(
                position.x + 2,
                position.y + 2,
                size.x + 4,
                size.y - 3);
        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();
        List<ModuleObject> first = objects.stream()
                .filter(moduleObject -> moduleObject.function.category == currentCategory)
                .filter(moduleObject -> objects.indexOf(moduleObject) % 2 == 0)
                .toList();

        List<ModuleObject> second = objects.stream()
                .filter(moduleObject -> moduleObject.function.category == currentCategory)
                .filter(moduleObject -> objects.indexOf(moduleObject) % 2 != 0)
                .toList();

        float offset = scrollingOut;
        float sizePanel = 0;
        for (ModuleObject object : first) {
            object.x = position.x + 110;
            object.y = position.y + 10 + offset;
            object.width = 160;
            object.height = 22;
            for (Object object1 : object.object) {
                if (object1.setting.visible()) {
                    object.height += object1.height;
                }
            }
            object.height += 3;
                object.draw(stack, mouseX, mouseY);
            offset += object.height += 5;
            sizePanel += object.height += 5;
        }
        float sizePanel1 = 0;
        offset = scrollingOut;
        for (ModuleObject object : second) {
            object.x = position.x + 280;
            object.y = position.y + 10 + offset;
            object.width = 160;
            object.height = 22;
            for (Object object1 : object.object) {
                if (object1.setting.visible()) {
                    object.height += object1.height;
                }
            }
            object.height += 3;
                object.draw(stack, mouseX, mouseY);
            offset += object.height += 5;
            sizePanel1 += object.height += 5;
        }
        float max = Math.max(sizePanel, sizePanel1);
        if (max < size.y) {
            scrolling = 0;
        } else {
            scrolling = MathHelper.clamp(scrolling, -(max - size.y), 0);
        }
        BloomHelper.draw(6, 1.5f, false);
        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();
        if (currentCategory == Type.Theme) {
            themeDrawing.click((int) mouseX, (int) mouseY, button);
        }
        if (currentCategory == Type.Configs) {
            configDrawing.click((int) mouseX, (int) mouseY, button);
        }
        float bar = 100;

        float len = 0;
        for (Type type : Type.values()) {
            if (type != Type.Scripts && type != Type.Configs) {

                if (RenderUtil.isInRegion(mouseX, mouseY, position.x + 5f, position.y + 40 + len, bar - 10, 15)) {
                    currentCategory = type;
                }

                len += 30;
            }
        }

        for (ModuleObject m : objects) {
            if (m.function.category == currentCategory)
                m.mouseClicked((int) mouseX, (int) mouseY, button);
        }

        var hasBindingModules = objects.stream().filter(mod -> mod.bindingState == true).count() > 0;

        for (Object object : objects) {
            if (object instanceof ModuleObject m) {
                if (button == 2 && !hasBindingModules) {
                    m.bindingState = true;
                    hasBindingModules = true;
                }

                m.bindWindow.mouseClick((int) mouseX, (int) mouseY, button);
                for (Object object1 : m.object) {
                    if (object1 instanceof BindObject no) {
                        no.bindWindow.mouseClick((int) mouseX, (int) mouseY, button);
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}