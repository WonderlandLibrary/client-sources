package rip.vantage.commons.util.vantage;

import java.awt.*;

public enum VantageProduct {
    RISE("Rise", "63d0f9bc46ca6bf7ad9572b7", "\247b", new Color(71, 148, 253)),
    WEED("Weed", "63d32b8e3012b50e9686dd39", "\2472", new Color(70, 200, 70)),
    PRESTIGE("Prestige", "", "\2471", new Color(79, 70, 229)),
    MONSOON("Monsoon", "63d106727842de723ada3bf0", "\2473", new Color(32, 117, 171));

    private final String displayName;
    private final String productId;
    private final String chatColor;
    private final Color color;

    VantageProduct(String displayName, String productId, String chatColor, Color color) {
        this.displayName = displayName;
        this.productId = productId;
        this.chatColor = chatColor;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProductId() {
        return productId;
    }

    public String getChatColor() {
        return chatColor;
    }

    public Color getColor() {
        return color;
    }
}
