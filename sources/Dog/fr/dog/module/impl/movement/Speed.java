package fr.dog.module.impl.movement;

import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.event.impl.player.PlayerTickEvent;
import fr.dog.event.impl.player.move.MoveInputEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.player.ChatUtil;
import fr.dog.util.player.MoveUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockSlab;
import net.minecraft.util.BlockPos;

@SuppressWarnings("unused")
public class Speed extends Module {

    private final ModeProperty mode = ModeProperty.newInstance("Mode", new String[]{"Legit", "Vanilla", "Watchdog", "Watchdog Low", "Watchdog Strafe"}, "Vanilla");
    private final NumberProperty sped = NumberProperty.newInstance("Speed", 0.2f, 0.29f, 0.5f, 0.01f, () -> !mode.is("Legit"));
    private int ticks = 0;

    public Speed() {
        super("Speed", ModuleCategory.MOVEMENT);
        this.registerProperties(mode, sped);
    }

    public void onEnable() {
        ticks = 0;
    }

    @SubscribeEvent
    private void onPlayerTick(PlayerTickEvent event) {
        this.setSuffix(mode.getValue());

        if (mc.thePlayer.isDead || mc.thePlayer.deathTime != 0 || mc.thePlayer.ticksExisted < 3 || mc.thePlayer.isSpectator())
            this.setEnabled(false);

        boolean moving = MoveUtil.moving();
        mc.thePlayer.jumpTicks = 0;

        switch (mode.getValue()) {
            case "Vanilla" -> {
                if (!moving)
                    return;

                MoveUtil.strafe(sped.getValue());
            }
            case "Watchdog", "Watchdog Low", "Watchdog Strafe" -> {
                if (moving && mc.thePlayer.onGround && mc.thePlayer.moveForward >= 0) {
                    MoveUtil.strafe(sped.getValue());
                }
            }
        }
    }

    @SubscribeEvent
    private void onPlayerNetworkTick(PlayerNetworkTickEvent event) {
        switch (mode.getValue()) {
            case "Watchdog Low" -> {
                if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() instanceof BlockSlab) {
                    return;
                }
                if (mc.thePlayer.onGround) {
                    event.setPosY(event.getPosY() + 1E-14);
                }
                if (ticks > 0 && mc.thePlayer.hurtTime == 0) {
                    if (mc.thePlayer.airTicks == 4) {
                        mc.thePlayer.motionY -= 0.03;
                    } else if (mc.thePlayer.airTicks == 6) {
                        mc.thePlayer.motionY -= 0.2;
                    }
                }
            }
            case "Watchdog Strafe" -> {
                if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).down()).getBlock() instanceof BlockSlab) {
                    return;
                }
                float dst = getDistanceToGround();
                ChatUtil.display(dst);

                if(dst == 0.5){
                    MoveUtil.strafe(MoveUtil.speed() * 1.1);
                }
            }
        }
    }

    @SubscribeEvent
    private void onMovementInputEvent(MoveInputEvent event) {
        if (!MoveUtil.moving()) {
            return;
        }
        event.setJumping(true);
        if (mc.thePlayer.airTicks == 8) {
            ticks++;
        }
    }


    private float getDistanceToGround(){
        float diff = 0;
        for(int i = (int) (mc.thePlayer.posY); i > -1; i--){
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if(!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)){
                return (float) (mc.thePlayer.posY - pos.getY()) - 1;
            }
        }
        return 0;
    }
}
