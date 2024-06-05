/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.module.impl.render;

import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;

public class CustomChat
extends Module {
    public static BooleanSetting clearChat = new BooleanSetting("Clear Chat", true);
    public static BooleanSetting customFont = new BooleanSetting("Custom Font", false);

    public CustomChat() {
        super("CustomChat", "Customize the visuals of the chat", 0, Category.Render);
        this.addSettings(clearChat, customFont);
    }
}

