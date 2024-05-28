package dev.vertic.ui.click.dropdown;

import dev.vertic.Client;
import dev.vertic.Utils;
import dev.vertic.event.impl.input.SettingUpdateEvent;
import dev.vertic.module.impl.render.Blur;
import dev.vertic.util.animation.Direction;
import dev.vertic.util.animation.impl.EaseBackIn;
import dev.vertic.util.render.BlurUtil;
import lombok.Setter;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import dev.vertic.font.CustomFont;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.module.impl.render.ClickGUI;
import dev.vertic.setting.Setting;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.setting.impl.ModeSetting;
import dev.vertic.setting.impl.NumberSetting;
import dev.vertic.util.render.DrawUtil;
import dev.vertic.util.render.RenderUtil;

import java.awt.*;
import java.util.List;
import java.util.*;

public class CategoryFrame implements Utils {

    private static CustomFont bigFont = font26;
    private static CustomFont smolFont = font18;
    public static float entryWidth = 132;
    public static float entryHeight = 20;
    public static int maximumEntries = 15;
    public static float scale = 0.8F;
    private static List<String> configs;
    public final Category category;
    private final List<Module> modules;
    public float frameX, frameY;
    private float mouseX, mouseY, dragX, dragY;
    private float scrollVertical, lastScrollVertical;
    private float previousHeight;
    @Setter
    public boolean expanded = true;
    private boolean dragged;
    private boolean leftClick, rightClick, doBlur;
    private final List<Module> expandedModuleIndices;
    private int customHue;
    private int bgOP = 200;
    private int blurRadius = 10;
    private int blurTimes = 2;
    private Color background, backgroundDarker, backgroundDarkest, accent, accentDarker, accentDarkest, shadow;
    private final SettingUpdateEvent event = new SettingUpdateEvent();
    private EaseBackIn anim;
    private double yOff;

    public CategoryFrame(final Category category, final float frameX, final float frameY) {
        this.modules = Client.instance.getModuleManager().getModulesByCategory(category);
        this.expandedModuleIndices = new ArrayList<>();
        this.category = category;
        this.frameX = frameX;
        this.frameY = frameY;
    }

    public void init() {

    }

    public void draw(final DropUI parent, final int mouseX, final int mouseY) {
        doBlur = Client.instance.getModuleManager().getModule(Blur.class).isEnabled() && Client.instance.getModuleManager().getModule(Blur.class).clickgui.isEnabled();
        bgOP = Client.instance.getModuleManager().getModule(ClickGUI.class).bgOpacity.getInt();
        blurRadius = Client.instance.getModuleManager().getModule(Blur.class) != null ? Client.instance.getModuleManager().getModule(Blur.class).radius.getInt() : 10;
        entryWidth = 132;
        entryHeight = 20;

        accent = new Color(65, 68, 217);
        accentDarker = new Color(46, 48, 153);
        accentDarkest = new Color(34, 35, 115);
        background = new Color(38, 35, 41, bgOP);
        backgroundDarker = new Color(27, 24, 30, bgOP);
        backgroundDarkest = new Color(15, 12, 18, bgOP);

        shadow = new Color(backgroundDarker.getRed(), backgroundDarker.getGreen(), backgroundDarker.getBlue(), 100);

        /*
         * Scrolling
         */
        if (mouseX >= frameX + DropUI.scrollHorizontal && mouseX <= frameX + DropUI.scrollHorizontal + entryWidth && !GuiInventory.isCtrlKeyDown()) {
            final float partialTicks = mc == null || mc.timer == null ? 1.0F : mc.timer.renderPartialTicks;

            final float lastLastScrollHorizontal = lastScrollVertical;
            lastScrollVertical = scrollVertical;
            final float wheel = Mouse.getDWheel();
            scrollVertical += wheel / 10.0F;
            if (wheel == 0) scrollVertical -= (lastLastScrollHorizontal - scrollVertical) * 0.6 * partialTicks;

            final float minScroll = maximumEntries * entryHeight - previousHeight;
            if (scrollVertical < minScroll) scrollVertical = minScroll;
            if (scrollVertical > 0.0F) scrollVertical = 0.0F;
        }

        /*
         * Now the ClickGUI itself
         */
        final float textOffsetBig = bigFont.getHeight() / 2.0F;
        final float textOffsetNormal = smolFont.getHeight() / 2.0F;

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        if (dragged) {
            this.frameX += this.mouseX - this.dragX;
            this.frameY += this.mouseY - this.dragY;
            this.dragX = this.mouseX;
            this.dragY = this.mouseY;
        }

        final float frameX = this.frameX + DropUI.scrollHorizontal;
        final float frameY = this.frameY;

        // Draw the top bar
        DrawUtil.rect(frameX, frameY, entryWidth, entryHeight, true, backgroundDarker);
        bigFont.drawCenteredString(
                StringUtils.capitalize(category.name().toLowerCase(Locale.ENGLISH)),
                frameX + entryWidth / 2.0F, frameY + entryHeight / 2.0F - textOffsetBig, Color.WHITE.getRGB()
        );

        if (expanded) {
            float currentY = frameY;


            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtil.scissor(frameX, currentY + entryHeight, entryWidth, maximumEntries * entryHeight);
            for (final Module module : modules) {
                currentY += entryHeight;

                float moduleY = currentY + scrollVertical;

                final Color backgroundColor = module.isEnabled()
                        ? getAccentColor(frameX, moduleY, entryWidth, entryHeight)
                        : getBackgroundColor(frameX, moduleY, entryWidth, entryHeight, false);
                DrawUtil.rect(frameX, moduleY, entryWidth, entryHeight, true, backgroundColor);

                float titleY = moduleY + entryHeight / 2.0F - textOffsetNormal;
                smolFont.drawString(module.getName(), frameX + 4.0F, titleY, Color.WHITE.getRGB());

                // Draw the settings
                final ArrayList<Setting> settings = module.getSettings();
                if (!settings.isEmpty()) {
                    final boolean settingsExpanded = expandedModuleIndices.contains(module);
                    smolFont.drawString(settingsExpanded ? "-" : "+", frameX + entryWidth - 8.0F, titleY, Color.WHITE.getRGB());

                    if (settingsExpanded) {
                        boolean isFirst = true;
                        for (final Setting setting : settings) {
                            if (!setting.isShown()) {
                                continue;
                            }
                            moduleY += entryHeight;
                            titleY += entryHeight;
                            if (setting instanceof BooleanSetting) {
                                final Color settingColor = ((BooleanSetting) setting).isEnabled()
                                        ? getAccentColor(frameX, moduleY, entryWidth, entryHeight)
                                        : getBackgroundColor(frameX, moduleY, entryWidth, entryHeight, true);
                                DrawUtil.rect(frameX, moduleY, entryWidth, entryHeight, true, settingColor);

                                smolFont.drawString(setting.getName(), frameX + 8.0F, titleY, Color.WHITE.getRGB());
                            } else if (setting instanceof ModeSetting) {
                                DrawUtil.rect(frameX, moduleY, entryWidth, entryHeight, true, getBackgroundColor(frameX, moduleY, entryWidth, entryHeight, true));

                                final ModeSetting arraySetting = (ModeSetting) setting;
                                smolFont.drawString(arraySetting.getName() + ": " + arraySetting.getMode(), frameX + 8.0F, titleY, Color.WHITE.getRGB());
                            } else if (setting instanceof NumberSetting) {
                                final NumberSetting numberSetting = (NumberSetting) setting;

                                final double fromZeroValue = numberSetting.getDouble() - Math.abs(numberSetting.getMinimum());
                                final float normalizedValue = (float) (fromZeroValue / (float) (numberSetting.getMaximum() - numberSetting.getMinimum()));
                                final float screenSpaceValue = normalizedValue * entryWidth;

                                DrawUtil.rect(frameX, moduleY, screenSpaceValue, entryHeight, true, getAccentColor(frameX, moduleY, entryWidth, entryHeight));
                                final float backgroundWidth = entryWidth - screenSpaceValue;

                                if (backgroundWidth > 0.0F) {
                                    final Color color = getBackgroundColor(frameX, moduleY, entryWidth, entryHeight, true);
                                    DrawUtil.rect(frameX + screenSpaceValue, moduleY, backgroundWidth, entryHeight, true, color);
                                }

                                smolFont.drawString(
                                        numberSetting.getName() + ": " + getTruncatedDouble(numberSetting),
                                        frameX + 8.0F, titleY, Color.WHITE.getRGB()
                                );
                            }

                            if (isFirst) {
                                isFirst = false;
                                DrawUtil.gradient(frameX, moduleY, entryWidth, 10, true, shadow, new Color(0, 0, 0, 0));
                            }
                        }
                        moduleY += entryHeight;
                        titleY += entryHeight;
                        final Color settingColor = module == parent.focusedModule
                                ? getAccentColor(frameX, moduleY, entryWidth, entryHeight)
                                : getBackgroundColor(frameX, moduleY, entryWidth, entryHeight, true);
                        DrawUtil.rect(frameX, moduleY, entryWidth, entryHeight, true, settingColor);
                        smolFont.drawString("Keybind : " + (parent.focusedModule == module ? "..." : Keyboard.getKeyName(module.getKey())), frameX + 8.0F, titleY, Color.WHITE.getRGB());
                        DrawUtil.gradient(frameX, moduleY + entryHeight - 10, entryWidth, 10, true, new Color(0, 0, 0, 0), shadow);
                    }
                }
                currentY = moduleY - scrollVertical;
            }

            previousHeight = currentY - frameY;
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
    }

    public void drawDescriptions(final int mouseX, final int mouseY, final float partialTicks) {
        if (expanded) {
            final float frameX = this.frameX + DropUI.scrollHorizontal;
            float currentY = frameY;

            if (mouseY < currentY + entryHeight || mouseY > currentY + entryHeight + maximumEntries * entryHeight)
                return;

            for (final Module module : modules) {
                currentY += entryHeight;
                final float moduleY = currentY + scrollVertical;

                if (isHovering(frameX, moduleY, entryWidth, entryHeight)) {
                    final String description = module.getDescription();
                    final float descriptionWidth = smolFont.getStringWidth(description);

                    if (doBlur) {
                        BlurUtil.doBlur(
                                blurRadius,
                                () -> DrawUtil.rect(mouseX + 4, mouseY - 6 - entryHeight / 2.0F, descriptionWidth + 6.0F, entryHeight / 2.0F + 2.0F, true, backgroundDarker)
                        );
                    } else {
                        DrawUtil.rect(mouseX + 4, mouseY - 6 - entryHeight / 2.0F, descriptionWidth + 6.0F, entryHeight / 2.0F + 2.0F, true, backgroundDarker);
                        DrawUtil.rect(mouseX + 5, mouseY - 5 - entryHeight / 2.0F, descriptionWidth + 4.0F, entryHeight / 2.0F, true, background);
                    }

                    smolFont.drawString(description, mouseX + 7, mouseY - 5 - entryHeight / 2.0F + 2.0F, Color.WHITE.getRGB());
                }

                final List<Setting> settings = module.getSettings();
                if (!settings.isEmpty()) {
                    final boolean settingsExpanded = expandedModuleIndices.contains(module);
                    if (settingsExpanded) {
                        for (final Setting setting : settings) {
                            if (!setting.isShown()) {
                                continue;
                            }
                            currentY += entryHeight;
                        }
                        currentY += entryHeight;
                    }
                }
            }
        }
    }

    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        switch (mouseButton) {
            case 0: {
                leftClick = true;
                break;
            }
            case 1: {
                rightClick = true;
                break;
            }
        }

        final float frameX = this.frameX + DropUI.scrollHorizontal;

        if (isHovering(frameX, frameY, entryWidth, entryHeight)) {
            if (leftClick) {
                dragged = true;
                dragX = mouseX;
                dragY = mouseY;
            }
        }
    }

    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        final float frameX = this.frameX + DropUI.scrollHorizontal;

        if (leftClick) dragged = false;
        else if (isHovering(frameX, frameY, entryWidth, entryHeight)) expanded = !expanded;

        if (expanded) {
            float currentY = frameY;
            float moduleY = currentY + scrollVertical;

            for (final Module module : modules) {
                moduleY += entryHeight;

                if (isHovering(frameX, moduleY, entryWidth, entryHeight)) {
                    switch (mouseButton) {
                        case 0:
                            module.toggle();
                            break;
                        case 1: {
                            if (expandedModuleIndices.contains(module)) expandedModuleIndices.remove(module);
                            else expandedModuleIndices.add(module);
                            break;
                        }
                    }
                }

                if (expandedModuleIndices.contains(module)) {
                    for (final Setting setting : module.getSettings()) {
                        if (!setting.isShown()) {
                            continue;
                        }

                        moduleY += entryHeight;

                        if (isHovering(frameX, moduleY, entryWidth, entryHeight)) {
                            if (setting instanceof BooleanSetting) {
                                ((BooleanSetting) setting).toggle();
                                event.call();
                            } else if (setting instanceof ModeSetting) {
                                final ModeSetting modeSetting = (ModeSetting) setting;
                                switch (mouseButton) {
                                    case 0: {
                                        modeSetting.increment();
                                        break;
                                    }
                                    case 1: {
                                        modeSetting.decrement();
                                        break;
                                    }
                                }
                                event.call();
                            }
                        }
                    }
                    moduleY += entryHeight;
                    if (isHovering(frameX, moduleY, entryWidth, entryHeight)) {
                        Client.instance.getDropDownUI().focusedModule = Client.instance.getDropDownUI().focusedModule == null ? module : null;
                    }
                }

                currentY = moduleY - scrollVertical;
            }
        }

        switch (mouseButton) {
            case 0: {
                leftClick = false;
                break;
            }
            case 1: {
                rightClick = false;
                break;
            }
        }
    }

    public void mouseClickMove(final int mouseX, final int mouseY, final int mouseButton, final long timeSinceLastClick) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;

        if (expanded) {
            final float frameX = this.frameX + DropUI.scrollHorizontal;
            float currentY = frameY + scrollVertical;

            for (final Module module : modules) {
                currentY += entryHeight;

                if (expandedModuleIndices.contains(module)) {
                    for (final Setting setting : module.getSettings()) {
                        if (!setting.isShown()) {
                            continue;
                        }

                        currentY += entryHeight;

                        if (isHovering(frameX, currentY, entryWidth, entryHeight)) {
                            if (setting instanceof NumberSetting) {
                                final NumberSetting numberSetting = (NumberSetting) setting;

                                final float mouseRelative = mouseX - frameX;
                                final float mouseNormalized = mouseRelative / entryWidth;
                                final double range = numberSetting.getMaximum() - numberSetting.getMinimum();
                                final double value = (mouseNormalized * range) + numberSetting.getMinimum();

                                if (numberSetting.getIncrement() != 0)
                                    numberSetting.setValue(round(value, (float) numberSetting.getIncrement()));
                                else numberSetting.setValue(value);
                                event.call();
                            }
                        }
                    }
                    currentY += entryHeight;
                }
            }
        }
    }

    private boolean isHovering(final float x, final float y, final float width, final float height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private Color getBackgroundColor(final float x, final float y, final float width, final float height, final boolean setting) {
        Color color = isHovering(x, y, width, height) ? ((leftClick || rightClick) ? backgroundDarkest : backgroundDarker) : background;
        if (setting) color = color.darker();
        return color;
    }

    private Color getAccentColor(final float x, final float y, final float width, final float height) {
        return isHovering(x, y, width, height) ? ((leftClick || rightClick) ? accentDarkest : accentDarker) : accent;
    }

    private static double round(final double value, final float places) {
        if (places < 0) throw new IllegalArgumentException();

        final double precision = 1 / places;
        return Math.round(value * precision) / precision;
    }

    private static String getTruncatedDouble(final NumberSetting setting) {
        String str = String.valueOf((float) round(setting.getValue(), (float) setting.getIncrement()));

        if (setting.getIncrement() == 1) {
            str = str.replace(".0", "");
        }

        return str;
    }

}
