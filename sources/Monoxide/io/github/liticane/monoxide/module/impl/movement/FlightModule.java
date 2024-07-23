package io.github.liticane.monoxide.module.impl.movement;

import io.github.liticane.monoxide.listener.event.minecraft.network.PacketEvent;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.minecraft.player.movement.UpdateMotionEvent;
import io.github.liticane.monoxide.listener.event.minecraft.world.CollisionBoxesEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.player.movement.MoveUtil;
import net.minecraft.util.BlockPos;

@ModuleData(name = "Flight", description = "Makes you fly", category = ModuleCategory.MOVEMENT)
public class FlightModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this, new String[] { "Vanilla", "Collision", "Verus", "Vulcan Timer" });

    private final NumberValue<Double> speed = new NumberValue<>("Speed", this, 1.5D, 0.05D, 5D, 2);
    private final NumberValue<Float> timer = new NumberValue<>("Timer", this, 5F, 0.05F, 5F, 2);

    public FlightModule() {
        if (mode.is("Vulcan Timer"))
            setEnabled(false);
    }

    @Override
    public String getSuffix() {
        return mode.getValue();
    }

    public double moveSpeed;

    public long flags;

    @Listen
    public void onCollisionBoxes(CollisionBoxesEvent event) {
        if (Methods.mc.thePlayer == null || Methods.mc.theWorld == null)
            return;

        switch (mode.getValue()) {
            case "Collision":
                if (!mc.gameSettings.keyBindSneak.pressed)
                    event.setBoundingBox(new AxisAlignedBB(-2, -1, -2, 2, 1, 2).offset(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ()));
                break;
            case "Vulcan Timer":
                event.setBoundingBox(AxisAlignedBB.fromBounds(-5, -1, -5, 5, 1, 5).offset(event.getBlockPos().getX(), event.getBlockPos().getY(), event.getBlockPos().getZ()));
        }
    }

    @Listen
    public void onUpdateMotion(UpdateMotionEvent motionEvent) {
        switch (mode.getValue()) {
            case "Vanilla":
                moveSpeed = speed.getValue();

                if (mc.gameSettings.keyBindJump.isKeyDown())
                    mc.thePlayer.motionY = moveSpeed / 2;

                else if (mc.gameSettings.keyBindSneak.isKeyDown())
                    mc.thePlayer.motionY = -moveSpeed / 2;
                else
                    mc.thePlayer.motionY = 0;

                MoveUtil.strafe(moveSpeed);
                break;
            case "Verus":
                if (motionEvent.getType() == UpdateMotionEvent.Type.PRE) {
                    BlockPos pos = mc.thePlayer.getPosition().add(0.0, -1.5, 0.0);
                    sendPacketUnlogged(
                            new C08PacketPlayerBlockPlacement(
                                    pos,
                                    1,
                                    new ItemStack(Blocks.stone.getItem(mc.theWorld, pos)),
                                    0.0F,
                                    0.5F + (float) Math.random() * 0.44F,
                                    0.0F
                            )
                    );
                    if (GameSettings.isKeyDown(mc.gameSettings.keyBindJump)) {
                        if (mc.thePlayer.ticksExisted % 2 == 0) {
                            mc.thePlayer.motionY = 0.75;
                        }
                    } else if (GameSettings.isKeyDown(mc.gameSettings.keyBindSneak)) {
                        if (mc.thePlayer.ticksExisted % 2 == 0) {
                            mc.thePlayer.motionY = -0.75;
                        }
                    } else {
                        mc.thePlayer.motionY = 0.0;
                    }
                    if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                        MoveUtil.strafe(0.45f);
                    } else {
                        MoveUtil.strafe(0.35f);
                    }
                }
                break;
            case "Vulcan Timer":
                if (flags >= 3) {
                    mc.timer.timerSpeed = timer.getValue();
                }
                break;
        }
    }

    @Listen
    public void onPacketEvent(PacketEvent event) {
        Packet <?> packet = event.getPacket();

        if (mode.is("Vulcan Timer") && packet instanceof S08PacketPlayerPosLook) {
            event.setCancelled(true);
            ++ flags;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        flags = 0;
        mc.timer.timerSpeed = 1.0F;
    }
}