// package me.travis.wurstplus.module.modules.misc;

// import me.travis.wurstplus.command.Command;
// import me.travis.wurstplus.module.Module;
// import me.zero.alpine.listener.EventHandler;
// import me.zero.alpine.listener.EventHook;
// import me.zero.alpine.listener.Listener;
// import net.minecraft.network.play.client.CPacketPlayer;
// import net.minecraft.util.MovementInput;
// import me.travis.wurstplus.setting.Setting;
// import me.travis.wurstplus.setting.Settings;
// import me.travis.wurstplus.event.events.EventMove;
// import net.minecraft.client.Minecraft;
// import net.minecraft.entity.MoverType;

// @Module.Info(name = "Strafe", category = Module.Category.MISC)
// public class Strafe extends Module {

//     public Integer timeout = 0;
//     private Setting<Integer> timeoutTicks = this.register(Settings.i("TimeoutTicks", 2));

//     @Override
//     public void onUpdate() {
//         if (mc.player.onGround && this.isEnabled() && isMoving() && !mc.player.collidedHorizontally) {
//             if (mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInLava() || mc.player.isInWater() || mc.player.capabilities.isFlying) {
//                 return;
//             }
//             mc.player.setSprinting(true);
//             if (timeoutTicks.getValue() < timeout) {
//                 mc.player.jump();
//                 timeout = 0;
//             }
//             timeout++;
//         }
//         else {
//             mc.player.setSprinting(false);
//         }
//     }

//     public boolean isMoving() {
//         if (mc.player.movementInput.forwardKeyDown || mc.player.movementInput.backKeyDown || mc.player.movementInput.leftKeyDown || mc.player.movementInput.rightKeyDown) {
//             return true;
//         }
//         return false;
//     }

//     // @EventHandler
//     // public Listener<MoverType> listener = new Listener<MoverType>(event -> {
//     //     if (mc.player == null) {
//     //         return;
//     //     }
//     //     if (mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInLava() || mc.player.isInWater() || mc.player.capabilities.isFlying) {
//     //         return;
//     //     }
//     //     if (mc.player.onGround) {
//     //         return;
//     //     }
//     //     float playerSpeed = 0.2873f;
//     //     float moveForward = mc.player.movementInput.moveForward;
//     //     float moveStrafe = mc.player.movementInput.moveStrafe;
//     //     final float rotationPitch = mc.player.rotationPitch;
//     //     float rotationYaw = mc.player.rotationYaw;
//     //     if (moveForward == 0.0f && moveStrafe == 0.0f) {
//     //         event.setX(0.0);
//     //         event.setZ(0.0);
//     //     }
//     //     else {
//     //         if (moveForward != 0.0f) {
//     //             if (moveStrafe > 0.0f) {
//     //                 rotationYaw += ((moveForward > 0.0f) ? -45 : 45);
//     //             }
//     //             else if (moveStrafe < 0.0f) {
//     //                 rotationYaw += ((moveForward > 0.0f) ? 45 : -45);
//     //             }
//     //             moveStrafe = 0.0f;
//     //             if (moveForward > 0.0f) {
//     //                 moveForward = 1.0f;
//     //             }
//     //             else if (moveForward < 0.0f) {
//     //                 moveForward = -1.0f;
//     //             }
//     //         }
//     //         event.setX(moveForward * playerSpeed * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * playerSpeed * Math.sin(Math.toRadians(rotationYaw + 90.0f)));
//     //         event.setZ(moveForward * playerSpeed * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * playerSpeed * Math.cos(Math.toRadians(rotationYaw + 90.0f)));
//     //     }
//     // });

//     protected void onEnable() {
//         if (mc.player == null) {
//             this.disable();
//             return;
//         }
//         timeout = 0;
//         Command.sendChatMessage("we strafing");
//     }

//     protected void onDisable() {
//         Command.sendChatMessage("we aint strafing no more");
//     }

// }