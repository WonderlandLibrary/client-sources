/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.realms.RealmsScreen;

public class RealmsBridgeScreen
extends RealmsScreen {
    private Screen field_230718_a_;

    public void func_231394_a_(Screen screen) {
        this.field_230718_a_ = screen;
        Minecraft.getInstance().displayGuiScreen(new RealmsMainScreen(this));
    }

    @Nullable
    public RealmsScreen func_239555_b_(Screen screen) {
        this.field_230718_a_ = screen;
        return new RealmsNotificationsScreen();
    }

    @Override
    public void init() {
        Minecraft.getInstance().displayGuiScreen(this.field_230718_a_);
    }
}

