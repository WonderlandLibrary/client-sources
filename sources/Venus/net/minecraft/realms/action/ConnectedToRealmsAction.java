/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms.action;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.gui.LongRunningTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.realms.RealmsConnect;
import net.minecraft.realms.RealmsServerAddress;
import net.minecraft.util.text.TranslationTextComponent;

public class ConnectedToRealmsAction
extends LongRunningTask {
    private final RealmsConnect field_238109_c_;
    private final RealmsServer field_244784_d;
    private final com.mojang.realmsclient.dto.RealmsServerAddress field_238110_d_;

    public ConnectedToRealmsAction(Screen screen, RealmsServer realmsServer, com.mojang.realmsclient.dto.RealmsServerAddress realmsServerAddress) {
        this.field_244784_d = realmsServer;
        this.field_238110_d_ = realmsServerAddress;
        this.field_238109_c_ = new RealmsConnect(screen);
    }

    @Override
    public void run() {
        this.func_224989_b(new TranslationTextComponent("mco.connect.connecting"));
        RealmsServerAddress realmsServerAddress = RealmsServerAddress.func_231413_a_(this.field_238110_d_.field_230601_a_);
        this.field_238109_c_.func_244798_a(this.field_244784_d, realmsServerAddress.func_231412_a_(), realmsServerAddress.func_231414_b_());
    }

    @Override
    public void func_224992_d() {
        this.field_238109_c_.func_231396_a_();
        Minecraft.getInstance().getPackFinder().clearResourcePack();
    }

    @Override
    public void func_224990_b() {
        this.field_238109_c_.func_231398_b_();
    }
}

