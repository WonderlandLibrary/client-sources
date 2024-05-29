package dev.client.tenacity.ui.clickguis.dropdown;

import dev.client.tenacity.Tenacity;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;

import dev.client.tenacity.module.impl.render.ClickGuiMod;

import static dev.client.tenacity.utils.misc.HoveringUtil.*;

import dev.client.tenacity.ui.Screen;
import dev.client.tenacity.ui.clickguis.dropdown.impl.ModuleRect;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.font.FontUtil;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import dev.utils.misc.MathUtils;
import dev.utils.render.StencilUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MainScreen implements Screen {

    private final Category category;
    private final float rectWidth = 110;
    private final float categoryRectHeight = 18;
    public Animation animation;
    public HashMap<ModuleRect, Animation> moduleAnimMap = new HashMap<>();
    public Animation openingAnimation;
    private List<ModuleRect> moduleRects;

    public MainScreen(Category category) {
        this.category = category;
    }

    @Override
    public void initGui() {
        if (moduleRects == null) {
            moduleRects = new ArrayList<>();
            for (Module module : Tenacity.INSTANCE.getModuleCollection().getModulesInCategory(category).stream().sorted(Comparator.comparing(Module::getName)).collect(Collectors.toList())) {
                ModuleRect moduleRect = new ModuleRect(module);
                moduleRects.add(moduleRect);
                moduleAnimMap.put(moduleRect, new DecelerateAnimation(250, 1));
            }
        }

        if (moduleRects != null) {
            moduleRects.forEach(ModuleRect::initGui);
        }

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (moduleRects != null) {
            moduleRects.forEach(moduleRect -> moduleRect.keyTyped(typedChar, keyCode));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        float animClamp = (float) Math.max(0, Math.min(255, 255 * animation.getOutput()));
        int alphaAnimation = (int) animClamp;
        int categoryRectColor = new Color(29, 29, 29, alphaAnimation).getRGB();
        int textColor = new Color(255, 255, 255, alphaAnimation).getRGB();

        category.getDrag().onDraw(mouseX, mouseY);
        float x = category.getDrag().getX(), y = category.getDrag().getY();
        Gui.drawRect2(x, y, rectWidth, categoryRectHeight, categoryRectColor);
        RenderUtil.setAlphaLimit(0);
        FontUtil.tenacityBoldFont26.drawString(category.name, x + 5, y + FontUtil.tenacityBoldFont26.getMiddleOfBox(categoryRectHeight), textColor);

        String icon = category.icon;
        RenderUtil.setAlphaLimit(0);
        RenderUtil.resetColor();
        FontUtil.iconFont20.drawString(icon, x + rectWidth - (FontUtil.iconFont20.getStringWidth(icon) + 5),
                y + FontUtil.iconFont20.getMiddleOfBox(categoryRectHeight), textColor);


        ClickGuiMod clickGUIMod = (ClickGuiMod) Tenacity.INSTANCE.getModuleCollection().get(ClickGuiMod.class);

        if (clickGUIMod.scrollMode.getMode().equals("Value")) {
            Module.allowedClickGuiHeight =  clickGUIMod.clickHeight.getValue().floatValue();
        } else {
            ScaledResolution sr = new ScaledResolution(mc);
            Module.allowedClickGuiHeight = 2 * sr.getScaledHeight() / 3f;
        }

        float allowedHeight = Module.allowedClickGuiHeight;


        boolean hoveringMods = isHovering(x, y + categoryRectHeight, rectWidth, allowedHeight, mouseX, mouseY);


        float scaleAnim = Math.max(1, (float) openingAnimation.getOutput() + .7f);
        float width = rectWidth;

        StencilUtil.initStencilToWrite();
        Gui.drawRect2(x - 100, y + categoryRectHeight, rectWidth + 150, allowedHeight, -1);
        StencilUtil.readStencilBuffer(1);

        double scroll = category.getScroll().getScroll();
        double count = 0;
        for (ModuleRect moduleRect : moduleRects) {
            Animation animation = moduleAnimMap.get(moduleRect);
            animation.setDirection(moduleRect.module.expanded ? Direction.FORWARDS : Direction.BACKWARDS);

            moduleRect.settingAnimation = animation;
            moduleRect.alphaAnimation = alphaAnimation;
            moduleRect.x = x;
            moduleRect.height = 17;
            moduleRect.panelLimitY = y;
            moduleRect.openingAnimation = openingAnimation;
            moduleRect.y = (float) (y + categoryRectHeight + (count * 17) + MathUtils.roundToHalf(scroll));
            moduleRect.width = rectWidth;
            moduleRect.drawScreen(mouseX, mouseY);

            // count ups by one but then accounts for setting animation opening
            count += 1 + (moduleRect.getSettingSize());
        }

        if (hoveringMods) {
            category.getScroll().onScroll(30);
            float hiddenHeight = (float) ((count * 17) - allowedHeight);
            category.getScroll().setMaxScroll(Math.max(0, hiddenHeight));
        }

        StencilUtil.uninitStencilBuffer();

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean canDrag = HoveringUtil.isHovering(category.getDrag().getX(), category.getDrag().getY(), rectWidth, categoryRectHeight, mouseX, mouseY);
        category.getDrag().onClick(mouseX, mouseY, button, canDrag);
        moduleRects.forEach(moduleRect -> moduleRect.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        category.getDrag().onRelease(state);
        moduleRects.forEach(moduleRect -> moduleRect.mouseReleased(mouseX, mouseY, state));
    }
}
