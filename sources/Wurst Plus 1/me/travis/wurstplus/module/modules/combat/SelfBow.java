// package me.travis.wurstplus.module.modules.combat;

// import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
// import net.minecraft.network.Packet;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.network.play.client.CPacketPlayerDigging;
// import net.minecraft.item.ItemBow;
// import net.minecraft.client.Minecraft;
// import me.travis.wurstplus.module.Module;
// import me.travis.wurstplus.command.Command;
// import net.minecraft.network.play.client.CPacketPlayer;
// import net.minecraft.util.math.MathHelper;
// import net.minecraft.util.EnumHand;
// import me.travis.wurstplus.setting.Setting;
// import me.travis.wurstplus.setting.Settings;

// @Module.Info(name = "Self Bow", category = Module.Category.COMBAT)
// public class SelfBow extends Module
// {
//     public boolean flag = false;
//     public Integer timeout = 0;
//     private Setting<Integer> timeoutTicks = this.register(Settings.i("TimeoutTicks", 20));

//     @Override
//     public void onUpdate() {
//         // if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) { // if you are holding a bow and it is active
//         //     mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
//         //     mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
//         // }
//         if (this.isEnabled()) {
//             mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(mc.player.rotationYaw, (float) -90.0, mc.player.onGround));
//             for (int i = 0; i < timeoutTicks.getValue()*10; i++) {
//                 mc.player.setActiveHand(EnumHand.MAIN_HAND);
//             }
//             this.disable();
//             // if (timeout > timeoutTicks.getValue()) {
//             //     flag = false;
//             //     this.disable();
//             // }
//             // timeout++;
//         }
//     }

//     protected void onEnable() {
//         if (mc.player == null) {
//             this.disable();
//             return;
//         }
//         timeout = 0;
//         flag = true;
//         Command.sendChatMessage("we bowing");
//         // mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(mc.player.rotationYaw, (float) -90.0, mc.player.onGround));
//         // mc.player.setActiveHand(EnumHand.MAIN_HAND);
//     }

//     protected void onDisable() {
//         mc.player.stopActiveHand();
//     }

// }