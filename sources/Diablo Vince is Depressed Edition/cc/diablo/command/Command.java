/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.command;

import cc.diablo.Main;
import net.minecraft.client.Minecraft;

public class Command {
    public static Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public String displayName;
    public String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        Main.getInstance().getEventBus().register((Object)this);
    }

    public String getDisplayName() {
        return this.displayName == null ? this.name : this.displayName;
    }

    public String getDescription() {
        return this.description;
    }
}

