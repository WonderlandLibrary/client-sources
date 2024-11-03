package dev.star.gui.clickguis.dropdown;

import dev.star.utils.tuples.Pair;
import dev.star.Client;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.impl.render.ClickGUIMod;
import dev.star.module.impl.display.HUDMod;
import dev.star.module.settings.Setting;
import dev.star.gui.Screen;
import dev.star.gui.clickguis.dropdown.components.ModuleRect;
import dev.star.utils.animations.Animation;
import dev.star.utils.misc.HoveringUtil;
import dev.star.utils.misc.MathUtils;
import dev.star.utils.render.*;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static dev.star.utils.misc.HoveringUtil.isHovering;

public class CategoryPanel implements Screen {

    private final Category category;

    private final float rectWidth = 105;
    private final float categoryRectHeight = 15;
    @Getter
    private boolean typing;

    public final Pair<Animation, Animation> openingAnimations;
    private List<ModuleRect> moduleRects;

    public CategoryPanel(Category category, Pair<Animation, Animation> openingAnimations) {
        this.category = category;
        this.openingAnimations = openingAnimations;
    }

    @Override
    public void initGui() {
        if (moduleRects == null) {
            moduleRects = new ArrayList<>();
            for (Module module : Client.INSTANCE.getModuleCollection().getModulesInCategory(category).stream().sorted(Comparator.comparing(Module::getName)).collect(Collectors.toList())) {
                moduleRects.add(new ModuleRect(module));
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

    public void onDrag(int mouseX, int mouseY) {
        category.getDrag().onDraw(mouseX, mouseY);
    }


    float actualHeight = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if (moduleRects == null) {
            return;
        }

        if (openingAnimations == null) return;


        float alpha = Math.min(1, openingAnimations.getFirst().getOutput().floatValue());

        Theme theme = Theme.getCurrentTheme();
        Pair<Color, Color> clientColors = HUDMod.getClientColors();

        //Multiply it by the alpha again so that it eases faster
        float alphaValue = alpha * alpha;
        if (ClickGUIMod.transparent.isEnabled()) {
            alphaValue *= .75f;
        }
        Color clientFirst = ColorUtil.applyOpacity(clientColors.getFirst(), alphaValue);
        Color clientSecond = ColorUtil.applyOpacity(clientColors.getSecond(), alphaValue);
        int textColor = ColorUtil.applyOpacity(-1, alpha);


        float x = category.getDrag().getX(), y = category.getDrag().getY();

        if (ClickGUIMod.scrollMode.getMode().equals("Value")) {
            Module.allowedClickGuiHeight = ClickGUIMod.clickHeight.getValue().floatValue();
        } else {
            ScaledResolution sr = new ScaledResolution(mc);
            Module.allowedClickGuiHeight = 2 * sr.getScaledHeight() / 3f;
        }

        float allowedHeight = Module.allowedClickGuiHeight;


        boolean hoveringMods = isHovering(x, y + categoryRectHeight, rectWidth, allowedHeight, mouseX, mouseY);

        RenderUtil.resetColor();
        float realHeight = Math.min(actualHeight, Module.allowedClickGuiHeight);


        if (ClickGUIMod.outlineAccent.isEnabled()) {
            if (theme.equals(Theme.RED_COFFEE)) {
                Color temp = clientFirst;
                clientFirst = clientSecond;
                clientSecond = temp;
            }
            if (DropdownClickGUI.gradient) {
                RoundedUtil.drawGradientVertical(x - .75f, y - .5f, rectWidth + 1.5f, realHeight + categoryRectHeight + 1.5f, 5, clientFirst, clientSecond);
            } else {
                RoundedUtil.drawRound(x - .75f, y - .5f, rectWidth + 1.5f, realHeight + categoryRectHeight + 1.5f, 0, clientFirst);
            }
        } else {
            RoundedUtil.drawRound(x, y, rectWidth, categoryRectHeight, 0, new Color(15, 15, 15, (int) (255)));
            // RoundedUtil.drawRound(x - .75f, y + categoryRectHeight, rectWidth + 1.5f, realHeight + 1, 0,  clientFirst);
//            if (!ClickGUIMod.transparent.isEnabled()) {
//                Gui.drawRect2(x, y + categoryRectHeight, rectWidth, 3, clientFirst.getRGB());
//            }
//            if (DropdownClickGUI.gradient) {
//                RoundedUtil.drawGradientVertical(x + 1, y + categoryRectHeight + 1, rectWidth - 2, realHeight - 2, 4, clientFirst, clientSecond);
//            } else {
//                RoundedUtil.drawRound(x + .8f, y + categoryRectHeight + .8f, rectWidth - 1.6f, realHeight - 1.6f, 3.5f, clientFirst);
//            }

        }


        // StencilUtil.initStencilToWrite();
        RoundedUtil.drawRound(x + 1, y + categoryRectHeight, rectWidth - 2, realHeight, 0, new Color(20, 20, 20, 10));
        // Gui.drawRect2(x, y + categoryRectHeight, rectWidth, 0, Color.BLACK.getRGB());
        //  StencilUtil.readStencilBuffer(1);


        double scroll = category.getScroll().getScroll();
        double count = 0;

        float rectHeight = 15F;


        for (ModuleRect moduleRect : getModuleRects()) {
            moduleRect.alpha = alpha;
            moduleRect.x = x - .5f;
            moduleRect.height = rectHeight;
            moduleRect.panelLimitY = y + categoryRectHeight - 2;
            moduleRect.y = (float) (y + categoryRectHeight + (count * rectHeight) + MathUtils.roundToHalf(scroll));
            moduleRect.width = rectWidth + 1;
            moduleRect.drawScreen(mouseX, mouseY);

            // count ups by one but then accounts for setting animation opening
            count += 1 + (moduleRect.getSettingSize() * (16 / 15f));
        }

        typing = getModuleRects().stream().anyMatch(ModuleRect::isTyping);


        actualHeight = (float) (count * rectHeight);

        if (hoveringMods) {
            category.getScroll().onScroll(25);
            float hiddenHeight = (float) ((count * rectHeight) - allowedHeight);
            category.getScroll().setMaxScroll(Math.max(0, hiddenHeight));
        }

        StencilUtil.uninitStencilBuffer();
        RenderUtil.resetColor();


        float yMovement;
        switch (category.name) {
            case "Movement":
            case "Player":
            case "Misc":
                yMovement = .5f;
                break;
            case "Render":
                yMovement = 1f;
                break;
            case "Exploit":
            default:
                yMovement = 0;
                break;

        }

        RenderUtil.resetColor();
        float textWidth = BoldFont22.getStringWidth(category.name + " ") / 2f;
//        iconFont20.drawCenteredString(category.icon, x + rectWidth / 2f + textWidth,
//                y + iconFont20.getMiddleOfBox(categoryRectHeight) + yMovement, textColor);

        RenderUtil.resetColor();
        BoldFont22.drawString(category.name, x + (rectWidth / 2f - textWidth), y + BoldFont22.getMiddleOfBox(categoryRectHeight), textColor);
    }

    public void renderEffects() {
        float x = category.getDrag().getX(), y = category.getDrag().getY();

        float alpha = Math.min(1, openingAnimations.getFirst().getOutput().floatValue());
        alpha *= alpha;


        Theme theme = Theme.getCurrentTheme();
        Pair<Color, Color> clientColors = theme.getColors();
        Color clientFirst = ColorUtil.applyOpacity(clientColors.getFirst(), alpha);
        Color clientSecond = ColorUtil.applyOpacity(clientColors.getSecond(), alpha);

        float allowedHeight = Math.min(actualHeight, Module.allowedClickGuiHeight);

        if (!ClickGUIMod.outlineAccent.isEnabled()) {
            RoundedUtil.drawRound(x , y , rectWidth, allowedHeight + categoryRectHeight, 0, Color.BLACK);
            return;
        }

        if (DropdownClickGUI.gradient && ClickGUIMod.outlineAccent.isEnabled()) {
            if (theme.equals(Theme.RED_COFFEE)) {
                Color temp = clientFirst;
                clientFirst = clientSecond;
                clientSecond = temp;
            }

            RoundedUtil.drawGradientVertical(x - .75f, y - .5f, rectWidth + 1.5f, allowedHeight + categoryRectHeight + 1.5f, 5,
                    clientFirst, clientSecond);

        } else {
            RoundedUtil.drawRound(x - .75f, y - .5f, rectWidth + 1.5f, allowedHeight + categoryRectHeight + 1.5f, 5,
                    ColorUtil.applyOpacity(Color.BLACK, alpha));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        boolean canDrag = HoveringUtil.isHovering(category.getDrag().getX(), category.getDrag().getY(), rectWidth, categoryRectHeight, mouseX, mouseY);
        category.getDrag().onClick(mouseX, mouseY, button, canDrag);
        getModuleRects().forEach(moduleRect -> moduleRect.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        category.getDrag().onRelease(state);
        getModuleRects().forEach(moduleRect -> moduleRect.mouseReleased(mouseX, mouseY, state));
    }

    private final List<String> searchTerms = new ArrayList<>();
    private final List<ModuleRect> moduleRectFilter = new ArrayList<>();

    public List<ModuleRect> getModuleRects() {
        for (ModuleRect moduleRect : moduleRects) {
            searchTerms.clear();
            Module module = moduleRect.module;

            searchTerms.add(module.getName());
            searchTerms.add(module.getCategory().name);
            if (!module.getAuthor().isEmpty()) {
                searchTerms.add(module.getAuthor());
            }
            for (Setting setting : module.getSettingsList()) {
                searchTerms.add(setting.name);
            }
            return moduleRects;
        }
        return moduleRectFilter;
    }
}
