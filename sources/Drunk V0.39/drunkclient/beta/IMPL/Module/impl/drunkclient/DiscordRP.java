/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.drunkclient;

import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.UTILS.DiscordRPCUtils;
import drunkclient.beta.UTILS.helper.Helper;
import drunkclient.beta.UTILS.world.Timer;

public class DiscordRP
extends Module {
    DiscordRPCUtils util = new DiscordRPCUtils();
    Timer timer = new Timer();

    public DiscordRP() {
        super("DiscordRP", new String[0], Type.DrunkClient, "No");
    }

    @Override
    public void onEnable() {
        Helper.sendMessage("Starting DiscordRP...");
        try {
            this.util.start();
            if (this.timer.hasElapsed(5000L, false)) {
                Helper.sendMessage("Started DiscordRP");
            }
        }
        catch (Exception e) {
            Helper.sendMessage("Falied to start DiscordRP");
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        try {
            this.util.shutdown();
        }
        catch (Exception e) {
            Helper.sendMessage("Falied to shutdown DiscordRP");
        }
        this.timer.reset();
        super.onDisable();
    }
}

