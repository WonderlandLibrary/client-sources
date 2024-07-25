package club.bluezenith.module.modules.render;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.UpdateEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.ui.draggables.Draggable;
import club.bluezenith.ui.notifications.NotificationType;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.GuiChat;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.module.value.builders.AbstractBuilder.createFloat;
import static club.bluezenith.util.font.FontUtil.newIconsSmall;
import static club.bluezenith.util.render.RenderUtil.*;
import static java.util.stream.Collectors.toList;
import static net.minecraft.client.renderer.GlStateManager.translate;
import static org.lwjgl.input.Keyboard.getKeyName;
import static org.lwjgl.opengl.GL11.*;

public class NewKeybinds extends Module implements Draggable {

    private static final int RED = new Color(255, 40, 27).getRGB(),
                             GREEN = new Color(76, 220, 80).getRGB();

    private static final TFontRenderer TITLE = FontUtil.createFont("Product Sans Regular", 42),
            KEY = FontUtil.createFont("Product Sans Regular", 35),
            MOD_NAME = FontUtil.createFont("Product Sans Bold", 35);

    private final FloatValue posX = createFloat("X")
            .index(-1)
            .increment(0.5F).max(500F).min(0F).defaultOf(0F)
            .build();

    private final FloatValue posY = createFloat("Y")
            .index(-2)
            .increment(0.5F).max(500F).min(0F).defaultOf(0F)
            .build();

    private final BooleanValue gradientLine = new BooleanValue("Gradient line", false).setIndex(-4);
    private final BooleanValue applyBlur = new BooleanValue("Blur", false).setIndex(-5);

    private final FloatValue widthSetting = createFloat("Width").index(-3).increment(0.5F).max(150F).min(125F).defaultOf(140F).build();

    private final BooleanValue onlyEnabled = new BooleanValue("Only Toggled", false).setIndex(3);
    private final BooleanValue hideWhenEmpty = new BooleanValue("Hide when empty", false).setIndex(4);
    private final BooleanValue showBinds = new BooleanValue("Show binds", false).setIndex(5);

    private final ModeValue indicatorSetting = AbstractBuilder.createMode("Indicator").index(-6).range("Icons", "Normal", "Color", "None").build();

    private float x, y, width, height;
    private boolean shouldRender;

    private List<Module> enabledModuleList = new ArrayList<>();

    public NewKeybinds() {
        super("NewKeybinds", ModuleCategory.RENDER);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        enabledModuleList = getBlueZenith()
                .getModuleManager()
                .getModules()
                .stream()
                .filter(module -> (module.getState() || !onlyEnabled.get()) && module.keyBind > 0)
                .collect(toList());
    }

    @Override
    public void draw(Render2DEvent event) {
        final boolean hasNoModules = enabledModuleList.isEmpty();

        width = widthSetting.get();
        height = hasNoModules ? 20 : gradientLine.get() ? 26F : 25;

        if(gradientLine.get()) {
            height += 2F;
        } else height -= 1F;

        height += (enabledModuleList.size() - 1) * 10.25F;

        translate(x, y, 0);

        final float cornerRadius = .8f;

        if(!shouldRender) {
            height += 15;
        }

        if(applyBlur.get())
            blur(x, y - 1, x + width, y + height + 1);


        rect(0, 0, width, height, new Color(0, 0, 0, applyBlur.get() ? 100 : 190));

        TITLE.drawString("Keybinds", width/2F - TITLE.getStringWidthF("Keybinds")/2F, 0, -1);

        float moduleY = TITLE.getHeight("Keybinds") + (gradientLine.get() ? 0 : 1);

        if(gradientLine.get()) {
            drawGradient(moduleY);

            moduleY += 4F;
        }

        if(!enabledModuleList.isEmpty()) {
            for (Module module : enabledModuleList) {
                final int color = module.getState() ? GREEN : RED;

                KEY.drawString(module.getName(), 2, moduleY, indicatorSetting.is("Color") ? color : -1);

                if(showBinds.get()) {
                    KEY.drawString(
                            "[" + getKeyName(module.keyBind) + "]",
                            4F + KEY.getStringWidthF(module.getName()),
                            moduleY,
                            0xD1D1D1E1
                    );
                }

                final boolean useIcons = indicatorSetting.is("Icons");

                if(!indicatorSetting.is("None") && !indicatorSetting.is("Color")) {
                    final String indicator = module.getState() ?
                            useIcons ? NotificationType.SUCCESS.getIconCode() : "On"
                            : useIcons ? NotificationType.ERROR.getIconCode() : "Off";

                    (useIcons ? newIconsSmall : KEY).drawString(
                            indicator,
                            width - 2 - (useIcons ? newIconsSmall : KEY).getStringWidthF(indicator),
                            moduleY + (useIcons ? 2 : 0),
                            color
                    );
                }

                moduleY += MOD_NAME.getHeight(module.getName()) + 1.5F;
            }
        } else {
            final String text = "Keybinds will be shown here.";

            KEY.drawString(text, width/2F - KEY.getStringWidthF(text)/2F, moduleY, -1);
        }

        translate(-x, -y, 0);
    }

    private void drawGradient(float startY) {
        glEnable(GL_SCISSOR_TEST);
        crop(x, y, x + width, y + height);

        int lastX = -1;
        final int parts = HUD.module.colorMode.is("Pulse") ? 8 : 4;

        float width = this.width;

        if(width % parts != 0) {
            width += parts - width % parts;
        }

        for (int x = 0; x <= width; x += (width / parts)) {
            drawGradientRectHorizontal(lastX, startY + .7F, x, startY + 1.5F,  HUD.module.getColor(lastX), HUD.module.getColor(x));
            lastX = x;
        }

        glDisable(GL_SCISSOR_TEST);
    }

    @Override
    public boolean shouldBeRendered() {
        shouldRender = !enabledModuleList.isEmpty() || !hideWhenEmpty.get();
        return getState() && (shouldRender || mc.currentScreen instanceof GuiChat);
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, x, y, x + width, y + height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void moveTo(float x, float y) {
        posX.set(this.x = x);
        posY.set(this.y = y);
    }
}
