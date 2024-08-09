package net.minecraft.realms;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.mojang.realmsclient.RealmsMainScreen;
import net.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;

import javax.annotation.Nullable;

public class RealmsBridgeScreen extends RealmsScreen
{
    private Screen field_230718_a_;

    public void func_231394_a_(Screen p_231394_1_)
    {
        this.field_230718_a_ = p_231394_1_;
        Minecraft.getInstance().displayScreen(new RealmsMainScreen(this));
    }

    @Nullable
    public RealmsScreen func_239555_b_(Screen p_239555_1_)
    {
        this.field_230718_a_ = p_239555_1_;
        return new RealmsNotificationsScreen();
    }

    public void init()
    {
        Minecraft.getInstance().displayScreen(this.field_230718_a_);
    }
}
