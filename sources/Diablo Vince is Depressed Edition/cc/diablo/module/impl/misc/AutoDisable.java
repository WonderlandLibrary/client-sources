/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.event.impl.WorldLoadEvent;
import cc.diablo.helpers.render.ChatColor;
import cc.diablo.helpers.render.ChatHelper;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.combat.KillAura;
import cc.diablo.module.impl.movement.Fly;
import cc.diablo.module.impl.movement.LongJump;
import cc.diablo.module.impl.movement.Speed;
import cc.diablo.module.impl.player.Scaffold;
import cc.diablo.setting.impl.BooleanSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class AutoDisable
extends Module {
    public BooleanSetting flag = new BooleanSetting("Flag", true);
    public BooleanSetting worldChange = new BooleanSetting("World Change", true);
    public BooleanSetting killaura = new BooleanSetting("KillAura", true);
    public BooleanSetting fly = new BooleanSetting("Fly", true);
    public BooleanSetting speed = new BooleanSetting("Speed", true);
    public BooleanSetting longjump = new BooleanSetting("LongJump", true);

    public AutoDisable() {
        super("AutoDisable", "Automatically disables modules", 0, Category.Misc);
        this.addSettings(this.flag, this.worldChange, this.killaura, this.fly, this.speed, this.longjump);
    }

    @Subscribe
    public void onWorldUpdate(WorldLoadEvent e) {
        new Thread(){

            @Override
            public void run() {
                try {
                    ModuleManager.getModule(AutoDisable.class).setToggled(false);
                    ChatHelper.addChat("AutoDisable disabled temporarily");
                    Thread.sleep(5000L);
                    ModuleManager.getModule(AutoDisable.class).setToggled(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        try {
            if (this.worldChange.isChecked()) {
                ChatHelper.addChat((Object)((Object)ChatColor.DARK_RED) + "Disabled " + (Object)((Object)ChatColor.RESET) + "some modules due to world change");
                if (this.killaura.isChecked()) {
                    ModuleManager.getModule(KillAura.class).setToggled(false);
                }
                if (this.speed.isChecked()) {
                    ModuleManager.getModule(KillAura.class).setToggled(false);
                }
                if (this.fly.isChecked()) {
                    ModuleManager.getModule(Fly.class).setToggled(false);
                }
                if (this.longjump.isChecked()) {
                    ModuleManager.getModule(KillAura.class).setToggled(false);
                }
                ModuleManager.getModule(Scaffold.class).setToggled(false);
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        if (Minecraft.theWorld != null) {
            try {
                if (this.flag.isChecked() && e.getPacket() instanceof S08PacketPlayerPosLook) {
                    ChatHelper.addChat((Object)((Object)ChatColor.DARK_RED) + "Disabled " + (Object)((Object)ChatColor.RESET) + "some modules due to flag");
                    if (this.speed.isChecked()) {
                        ModuleManager.getModule(Speed.class).setToggled(false);
                    }
                    if (this.killaura.isChecked()) {
                        ModuleManager.getModule(Fly.class).setToggled(false);
                    }
                    if (this.longjump.isChecked()) {
                        ModuleManager.getModule(LongJump.class).setToggled(false);
                    }
                }
            }
            catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}

