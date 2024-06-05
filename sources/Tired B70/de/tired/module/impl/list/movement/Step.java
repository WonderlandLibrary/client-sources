package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.ModeSetting;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.api.guis.clickgui.setting.impl.BooleanSetting;
import de.tired.api.util.math.TimerUtil;
import de.tired.event.EventTarget;
import de.tired.event.events.EventStep;
import de.tired.event.events.StepPostEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.tired.other.ClientHelper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.stats.StatList;

@ModuleAnnotation(name = "Step", category = ModuleCategory.MOVEMENT)
public class Step extends Module {

    public NumberSetting stepHeight = new NumberSetting("stepHeight", this, 1, 1, 22, 1);
    public BooleanSetting boostValue = new BooleanSetting("SpeedBoost", this, true);
    public BooleanSetting fakeJump = new BooleanSetting("fakeJump", this, true);
    public NumberSetting boost = new NumberSetting("boost", this, 4, .01, 12, .01, () -> boostValue.getValue());
    public ModeSetting packetType = new ModeSetting("packetType", this, new String[]{"Silent", "Logged", "Jump"});
    public static double moveSpeed;
    public TimerUtil timer = new TimerUtil();
    public boolean enabled;
    private double stepY = 0, stepX = 0, stepZ = 0;

    int ticks = 2;

    @EventTarget
    public void onStep(EventStep e) {

        MC.thePlayer.stepHeight = 1;
        e.setStepHeight(stepHeight.getValueInt());
        if (ticks == 1) {
            MC.timer.timerSpeed = 1.0f;
            ticks = 2;
        }
        if (e.getStepHeight() > 0.625) {
            stepY = MC.thePlayer.posY;
            stepX = MC.thePlayer.posX;
            stepZ = MC.thePlayer.posZ;
        }

    }

    @EventTarget
    public void onConfirm(StepPostEvent e) {
        if (MC.thePlayer.getEntityBoundingBox().minY - stepY > 0.625) {
            if (fakeJump.getValue()) {
                fakeJump();
            }

            switch (packetType.getValue()) {
                case "Silent":

                    sendPacketUnlogged(new C03PacketPlayer.C04PacketPlayerPosition(stepX, stepY + 0.41999998688698, stepZ, false));
                    sendPacketUnlogged(new C03PacketPlayer.C04PacketPlayerPosition(stepX, stepY + 0.7531999805212, stepZ, false));

                    break;
                case "Logged": {
                    sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(stepX, stepY + 0.41999998688698, stepZ, false));
                    sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(stepX, stepY + 0.7531999805212, stepZ, false));
                    break;
                }

                case "Jump": {
                    MC.thePlayer.jump();
                }
                break;

            }


            if (boostValue.getValue()) {
                ClientHelper.INSTANCE.doSpeedup(boost.getValue());
            }
            MC.timer.timerSpeed = .1F;
            ticks = 1;
        }
    }

    public void fakeJump() {
        MC.thePlayer.isAirBorne = true;
        MC.thePlayer.triggerAchievement(StatList.jumpStat);

    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {
        MC.thePlayer.stepHeight = 0.625f;

        MC.timer.timerSpeed = 1F;
    }


}
