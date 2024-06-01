package best.actinium.module.impl.combat;

import best.actinium.Actinium;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.game.UpdateEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.event.impl.network.PacketEvent;
import best.actinium.event.impl.render.Render2DEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.util.IAccess;
import best.actinium.util.player.PlayerUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.block.BlockFurnace;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;

@ModuleInfo(name = "Auto Block",description = "Idk",category = ModuleCategory.COMBAT)
public class AutoBlockModule extends Module {
    //fuck jd shit code btw
    private static boolean blocking = false;
    private static boolean placingBlock = false;
    private static boolean stopKaingBlocks = false;
    private static boolean stopKaingGaps = false;
    private static boolean ateGap = false;
    private static boolean cancelUnblock = false;
    private static boolean sendBlock = false;
    private static boolean setDelay = false;
    private static int blockTicks = 0;
    private static int checkBlock = 0;
    private static int gapTicks = 0;
    private static int slotChange = 0;
    private static int waitDelay = 0;
    private static boolean blocked = false;
    private static String name = "";
    //broken asf


    public static void disableKa() {
        Actinium.INSTANCE.getModuleManager().get(KillauraModule.class).setEnabled(true);
    }

    public static void enableKa() {
        Actinium.INSTANCE.getModuleManager().get(KillauraModule.class).setEnabled(false);
    }

    public static boolean isHoldingGap() {
        return mc.thePlayer.getHeldItem() != null &&
                mc.thePlayer.getHeldItem().getDisplayName().contains("Apple");
    }

    public static void block() {
        //IAccess.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(IAccess.mc.thePlayer.inventory.getCurrentItem()));
        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0), 0, mc.thePlayer.getHeldItem(), 0, 0, 0));
        ChatUtil.display("Block");
    }

    public static void swap() {
        //held slot
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        ChatUtil.display("swap");
    }

    private static boolean lookingAtBlock;

    @Callback
    public void onPacket(PacketEvent packet) {

        if (packet.getPacket() instanceof C08PacketPlayerBlockPlacement && KillauraModule.target != null) {
            packet.setCancelled(true);
            blocked = true;
        } else if (packet.getPacket() instanceof C07PacketPlayerDigging && blocked && KillauraModule.target != null) {
            packet.setCancelled(true);
            blocked = false;
        }

        if(mc.objectMouseOver != null) {
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            if (packet.getPacket() instanceof C08PacketPlayerBlockPlacement && pos != null &&
                    (lookingAtBlock) &&
                    !(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockFurnace)) {
                placingBlock = true;
                checkBlock = 0;
            }
        }

        if (packet.getPacket() instanceof C02PacketUseEntity && slotChange >= 2 && blocking) {
            if (!setDelay) {
                sendBlock = true;
                swap();
            }

            if (setDelay) {
                packet.setCancelled(true);
                ChatUtil.display("cancelled hit");
            }
        }
    }

    @Callback
    public void onRender(MotionEvent event) {
        if (sendBlock && event.getType() == EventType.PRE) {
            block();
         //   setDelay = true;
            sendBlock = true;
        }
    }

    @Callback
    public void onMotion(MotionEvent event) {
        if(event.getType() == EventType.POST) {
            if (setDelay) {
                waitDelay += 0.9;
            }
            if (waitDelay >= 1.8) {
                setDelay = false;
                waitDelay = 0;
            }
        }

        if(event.getType() == EventType.PRE) {
            if (PlayerUtil.isHoldingSword()) slotChange++;
            if (!PlayerUtil.isHoldingSword()) slotChange = 0;
            if (PlayerUtil.isHoldingSword() && KillauraModule.target != null) {
                blocking = true;
            }

            if (blocking && KillauraModule.target == null) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                blocking = false;
                sendBlock = false;
                setDelay = false;
                waitDelay = 0;
            }
        }
    }

    @Callback
    public void onUpdate(UpdateEvent event) {
        if (placingBlock && PlayerUtil.isHoldingSword()) {
         //   disableKa();
            stopKaingBlocks = true;
            placingBlock = false;
        }

        if (stopKaingBlocks) {
            blockTicks += 0.8;
        }
        if (blockTicks == 2) {
            enableKa();
            stopKaingBlocks = false;
            blockTicks = 0;
        }
        if (checkBlock == 4) {
            placingBlock = false;
            stopKaingBlocks = false;
            blockTicks = 0;
            checkBlock = 0;
        }
        if (isHoldingGap() && mc.thePlayer.isUsingItem()) {
            ateGap = true;
        }
        if (ateGap && (!isHoldingGap() || (isHoldingGap() && !mc.thePlayer.isUsingItem()))) {
            disableKa();
            stopKaingGaps = true;
            ateGap = false;
        }
        if (stopKaingGaps) {
            gapTicks++;
        }
        if (gapTicks == 3) {
            enableKa();
            stopKaingGaps = false;
            gapTicks = 0;
        }
        if (placingBlock && !blocking) {
            checkBlock++;
        }

        MovingObjectPosition result = mc.objectMouseOver;
        if (result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            lookingAtBlock = true;
        } else {
            lookingAtBlock = false;
        }

    }
}
