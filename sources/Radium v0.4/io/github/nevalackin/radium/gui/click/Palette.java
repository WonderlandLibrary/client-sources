package io.github.nevalackin.radium.gui.click;

import io.github.nevalackin.radium.utils.render.Colors;

import java.awt.*;

public enum Palette {
    DEFAULT(new Color(0xFF303030),
            new Color(0xFF303030).darker(),
            new Color(0xFF303030),
            new Color(0xFF232323).darker(),
            new Color(0xFF292929),
            new Color(Colors.BLUE),
            new Color(0xFF303030),
            new Color(0xFFFFFF)),
    PINK(new Color(0x80000000, true),
            new Color(0x80808080, true),
            new Color(0x80000000, true),
            new Color(0x80808080, true),
            new Color(Colors.PINK),
            new Color(Colors.PINK),
            new Color(0xFFFFFFFF),
            new Color(0xFFFFFFFF));

    private final Color panelBackgroundColor;
    private final Color hoveredBackgroundColor;
    private final Color secondaryBackgroundColor;
    private final Color hoveredSecondaryBackgroundColor;
    private final Color panelHeaderColor;
    private final Color enabledModuleColor;
    private final Color disabledModuleColor;
    private final Color panelHeaderTextColor;
    Palette(Color panelBackgroundColor,
            Color hoveredBackgroundColor,
            Color secondaryBackgroundColor,
            Color hoveredSecondaryBackgroundColor,
            Color panelHeaderColor,
            Color enabledModuleColor,
            Color disabledModuleColor,
            Color panelHeaderTextColor) {
        this.panelBackgroundColor = panelBackgroundColor;
        this.hoveredBackgroundColor = hoveredBackgroundColor;
        this.secondaryBackgroundColor = secondaryBackgroundColor;
        this.hoveredSecondaryBackgroundColor = hoveredSecondaryBackgroundColor;
        this.panelHeaderColor = panelHeaderColor;
        this.enabledModuleColor = enabledModuleColor;
        this.disabledModuleColor = disabledModuleColor;
        this.panelHeaderTextColor = panelHeaderTextColor;
    }

    public Color getHoveredSecondaryBackgroundColor() {
        return hoveredSecondaryBackgroundColor;
    }

    public Color getHoveredBackgroundColor() {
        return hoveredBackgroundColor;
    }

    public Color getSecondaryBackgroundColor() {
        return secondaryBackgroundColor;
    }

    public Color getPanelHeaderTextColor() {
        return panelHeaderTextColor;
    }

    public Color getPanelBackgroundColor() {
        return panelBackgroundColor;
    }

    public Color getPanelHeaderColor() {
        return panelHeaderColor;
    }

    public Color getEnabledModuleColor() {
        return enabledModuleColor;
    }

    public Color getDisabledModuleColor() {
        return disabledModuleColor;
    }
}
