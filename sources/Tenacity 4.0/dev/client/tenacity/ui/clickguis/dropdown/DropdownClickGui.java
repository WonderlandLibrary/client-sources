package dev.client.tenacity.ui.clickguis.dropdown;

import dev.client.tenacity.Tenacity;
import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.ModuleCollection;
import dev.client.tenacity.module.impl.movement.InventoryMove;
import dev.client.tenacity.module.impl.render.ClickGuiMod;
import dev.client.tenacity.ui.clickguis.dropdown.impl.SettingComponents;
import dev.utils.animations.Animation;
import dev.utils.animations.Direction;
import dev.utils.animations.impl.DecelerateAnimation;
import dev.utils.animations.impl.EaseBackIn;
import dev.client.tenacity.utils.misc.HoveringUtil;
import dev.client.tenacity.utils.render.RenderUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.ArrayList;
import java.util.List;

public class DropdownClickGui extends GuiScreen {
    private Animation openingAnimation;
    private EaseBackIn fadeAnimation;
    private DecelerateAnimation configHover;

    private List<MainScreen> categoryPanels;


    @Override
    public void initGui() {
        if (categoryPanels == null || ModuleCollection.reloadModules) {
            categoryPanels = new ArrayList() {{
                for (Category category : Category.values()) {
                    add(new MainScreen(category));
                }
            }};
            ModuleCollection.reloadModules = false;
        }
        Tenacity.INSTANCE.getSideGui().initGui();
        fadeAnimation = new EaseBackIn(400, 1, 2f);
        openingAnimation = new EaseBackIn(400, .4f, 2f);
        configHover = new DecelerateAnimation(250, 1);

        for (MainScreen catPanels : categoryPanels) {
            catPanels.animation = fadeAnimation;
            catPanels.openingAnimation = openingAnimation;
            catPanels.initGui();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1) {
            openingAnimation.setDirection(Direction.BACKWARDS);
            Tenacity.INSTANCE.getSideGui().focused = false;
            fadeAnimation.setDirection(openingAnimation.getDirection());
        }
        Tenacity.INSTANCE.getSideGui().keyTyped(typedChar, keyCode);
        categoryPanels.forEach(categoryPanel -> categoryPanel.keyTyped(typedChar, keyCode));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (ModuleCollection.reloadModules) {
            initGui();
        }
        if (ClickGuiMod.walk.isEnabled()) {
            InventoryMove.updateStates();
        }

        //If the closing animation finished then change the gui screen to null
        if (openingAnimation.isDone() && openingAnimation.getDirection().equals(Direction.BACKWARDS)) {
            mc.displayGuiScreen(null);
            return;
        }

        boolean focusedConfigGui = Tenacity.INSTANCE.getSideGui().focused;
        int fakeMouseX = focusedConfigGui ? 0 : mouseX, fakeMouseY = focusedConfigGui ? 0 : mouseY;
        ScaledResolution sr = new ScaledResolution(mc);

        boolean hoveringConfig = HoveringUtil.isHovering(width - 120, height - 65, 75, 25, fakeMouseX, fakeMouseY);

        configHover.setDirection(hoveringConfig ? Direction.FORWARDS : Direction.BACKWARDS);
        int alphaAnimation = Math.max(0, Math.min(255, (int) (255 * fadeAnimation.getOutput())));

        GlStateManager.color(1, 1, 1, 1);

        SettingComponents.scale = (float) (openingAnimation.getOutput() + .6f);
        RenderUtil.scale(sr.getScaledWidth() / 2f, sr.getScaledHeight() / 2f, (float) openingAnimation.getOutput() + .6f, () -> {
            for (MainScreen catPanels : categoryPanels) {
                catPanels.drawScreen(fakeMouseX, fakeMouseY);
            }

            Tenacity.INSTANCE.getSideGui().drawScreen(mouseX, mouseY, partialTicks, alphaAnimation);
        });


    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean focused = Tenacity.INSTANCE.getSideGui().focused;
        Tenacity.INSTANCE.getSideGui().mouseClicked(mouseX, mouseY, mouseButton);
        if (!focused) {
            categoryPanels.forEach(cat -> cat.mouseClicked(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        boolean focused = Tenacity.INSTANCE.getSideGui().focused;
        Tenacity.INSTANCE.getSideGui().mouseReleased(mouseX, mouseY, state);
        if (!focused) {
            categoryPanels.forEach(cat -> cat.mouseReleased(mouseX, mouseY, state));
        }
    }

}
