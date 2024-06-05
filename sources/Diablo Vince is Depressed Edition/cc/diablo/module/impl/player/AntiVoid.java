/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.player;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.module.impl.movement.Fly;
import cc.diablo.module.impl.movement.Highjump;
import cc.diablo.module.impl.player.Scaffold;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;

public class AntiVoid
extends Module {
    public static BooleanSetting disableTemp = new BooleanSetting("Disable Temporarly", true);
    public static BooleanSetting enableScaffold = new BooleanSetting("Enable Scaffold", true);
    public static NumberSetting distance = new NumberSetting("Distance", 3.0, 1.0, 10.0, 1.0);

    public AntiVoid() {
        super("AntiVoid", "Prevents falling in the void", 0, Category.Player);
        this.addSettings(disableTemp, enableScaffold, distance);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        this.setDisplayName(this.getName() + "\u00a77 " + distance.getVal());
        if (!this.isBlockUnder()) {
            if (!(!((double)AntiVoid.mc.thePlayer.fallDistance >= distance.getVal()) || ModuleManager.getModule(Highjump.class).isToggled() || ModuleManager.getModule(Fly.class).isToggled() || AntiVoid.mc.thePlayer.capabilities.isFlying || AntiVoid.mc.thePlayer.capabilities.isCreativeMode)) {
                Timer cfr_ignored_0 = AntiVoid.mc.timer;
                Timer.timerSpeed = 1.0f;
                AntiVoid.mc.thePlayer.motionY = 0.5213123;
                AntiVoid.mc.thePlayer.motionX = 0.4163213123;
                AntiVoid.mc.thePlayer.fallDistance = 0.0f;
                if (disableTemp.isChecked()) {
                    new Thread(){

                        @Override
                        public void run() {
                            try {
                                ModuleManager.getModule(AntiVoid.class).setToggled(false);
                                Thread.sleep(5000L);
                                ModuleManager.getModule(AntiVoid.class).setToggled(true);
                                ModuleManager.getModule(AntiVoid.class).onEnable();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                if (enableScaffold.isChecked()) {
                    ModuleManager.getModule(Scaffold.class).setToggled(true);
                }
            }
        }
    }

    private boolean isBlockUnder() {
        if (AntiVoid.mc.thePlayer.posY < 0.0) {
            return false;
        }
        for (int offset = 0; offset < (int)AntiVoid.mc.thePlayer.posY + 2; offset += 2) {
            AxisAlignedBB bb = AntiVoid.mc.thePlayer.getEntityBoundingBox().offset(0.0, -offset, 0.0);
            if (Minecraft.theWorld.getCollidingBoundingBoxes(AntiVoid.mc.thePlayer, bb).isEmpty()) continue;
            return true;
        }
        return false;
    }
}

