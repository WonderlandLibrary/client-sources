package fr.dog.module.impl.movement;

import fr.dog.component.impl.packet.BlinkComponent;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.module.impl.combat.KillAuraModule;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.util.player.MoveUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;

public class FireballFlight extends Module {
    private final BooleanProperty auto = BooleanProperty.newInstance("Automatic", true);
    private final ModeProperty modeProperty = ModeProperty.newInstance("Mode", new String[]{"Legit"}, "Legit");
    private int ticksSinceEnabled = 0;
    private int oldSlot = -1;
    private boolean done = false;

    boolean start, jump;
    int ticks;
    double boostticks, mode;

    public FireballFlight() {
        super("FireBallFlight", ModuleCategory.MOVEMENT);
        this.registerProperties(auto, modeProperty);
    }

    public void onEnable() {
        oldSlot = mc.thePlayer.inventory.currentItem;
        ticksSinceEnabled = 0;
        ticks = 0;
        start = false;
        done = false;
    }

    public void onDisable() {
        mc.thePlayer.inventory.currentItem = oldSlot;
        start = false;
        if(KillAuraModule.target == null){
            BlinkComponent.onDisable();
        }
    }

    @SubscribeEvent
    private void onPre(PlayerNetworkTickEvent e) {
        ticksSinceEnabled++;
        if (auto.getValue()) {
            if (ticksSinceEnabled < 4) {
                int temp = getFireballSlot();
                if (temp != -1) {
                    mc.thePlayer.inventory.currentItem = temp;
                } else {
                    this.setEnabled(false);
                    return;
                }

                e.setPitch(89);
                e.setYaw(MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - 180));

                mc.thePlayer.rotationYawHead = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - 180);
                mc.thePlayer.renderPitchHead = 89;

                if (ticksSinceEnabled == 3) {
                    mc.rightClickMouse();
                }
            }
        }


        if (mc.thePlayer.hurtTime >= 3) {
            start = true;
        }
        if (start) {
            ticks++;
        }

        switch (modeProperty.getValue()){
            case "Legit" -> {
                if(ticks == 1){
                    MoveUtil.strafe(1f);
                }
            }
            case "Opal" -> {
                if(ticks == 1){
                    MoveUtil.strafe(1.7f);
                }
                if(ticks < 4 && ticks > 0){
                    mc.thePlayer.motionY *= 1.2;
                }

            }
        }
        if (mc.thePlayer.onGround && ticks > 5) {
            this.setEnabled(false);
        }
    }

    private int getFireballSlot() {
        for (int i = 0; i < 9; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i) == null) {
                continue;
            }
            if (Minecraft.getMinecraft().thePlayer.inventory.getStackInSlot(i).getItem() == Items.fire_charge) {
                return i;
            }
        }
        return -1;
    }
}
