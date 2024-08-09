package wtf.shiyeno.modules.impl.combat;

import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventMotion;
import wtf.shiyeno.events.impl.player.EventPlaceAnchorByPlayer;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.math.MathUtil;
import wtf.shiyeno.util.math.RayTraceUtil;
import wtf.shiyeno.util.misc.TimerUtil;
import wtf.shiyeno.util.world.InventoryUtil;

@FunctionAnnotation(
        name = "AutoAnchor",
        type = Type.Combat
)
public class AutoAncherFunction extends Function {
    private int oldSlot = -1;
    private BlockPos position = null;
    private final TimerUtil t = new TimerUtil();

    public AutoAncherFunction() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventPlaceAnchorByPlayer e) {
            this.handleEventPlaceAnchorByPlayer(e);
        } else if (event instanceof EventMotion e) {
            this.handleEventMotion(e);
        }
    }

    private void handleEventPlaceAnchorByPlayer(EventPlaceAnchorByPlayer e) {
        this.position = e.getPos();
    }

    private void handleEventMotion(EventMotion e) {
        if (this.position != null) {
            if (!(mc.player.getPositionVec().distanceTo(new Vector3d((double)this.position.getX(), (double)this.position.getY(), (double)this.position.getZ())) > (double)mc.playerController.getBlockReachDistance())) {
                if (this.oldSlot == -1) {
                    this.oldSlot = mc.player.inventory.currentItem;
                }

                int slot = InventoryUtil.getSlotInHotBar(Items.GLOWSTONE);
                if (slot != -1 && !mc.player.isSneaking()) {
                    mc.player.inventory.currentItem = slot;
                    double x = (double)((float)this.position.getX() + 0.5F);
                    double y = (double)(this.position.getY() - 1);
                    double z = (double)((float)this.position.getZ() + 0.5F);
                    Vector2f rots = MathUtil.rotationToVec(new Vector3d(x, y, z));
                    e.setYaw(rots.x);
                    e.setPitch(rots.y);
                    mc.player.rotationYawHead = rots.x;
                    mc.player.renderYawOffset = rots.x;
                    mc.player.rotationPitchHead = rots.y;
                    BlockState state = mc.world.getBlockState(this.position);
                    if (!(state.getBlock() instanceof RespawnAnchorBlock) || state.getBlock() instanceof RespawnAnchorBlock && (Integer)state.get(RespawnAnchorBlock.CHARGES) >= 1) {
                        this.resetOnFull();
                    }

                    if (!(state.getBlock() instanceof RespawnAnchorBlock) || state.getBlock() instanceof RespawnAnchorBlock && (Integer)state.get(RespawnAnchorBlock.CHARGES) >= 2) {
                        this.position = null;
                    }

                    if (this.position != null && mc.player.getPositionVec().distanceTo(new Vector3d((double)this.position.getX(), (double)this.position.getY(), (double)this.position.getZ())) <= (double)mc.playerController.getBlockReachDistance()) {
                        this.setFuelToAncher(rots);
                    }
                }

            }
        }
    }

    private void resetOnFull() {
        mc.player.inventory.currentItem = this.oldSlot;
        this.oldSlot = -1;
    }

    private void setFuelToAncher(Vector2f rots) {
        if (this.t.hasTimeElapsed(150L)) {
            ActionResultType result = mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, (BlockRayTraceResult)RayTraceUtil.rayTrace(6.0, rots.x, rots.y, mc.player));
            if (result == ActionResultType.SUCCESS) {
                mc.player.swingArm(Hand.MAIN_HAND);
            }

            this.t.reset();
        }
    }

    protected void onDisable() {
        this.oldSlot = -1;
        super.onDisable();
    }
}