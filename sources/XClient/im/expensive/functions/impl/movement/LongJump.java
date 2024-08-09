package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.*;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.InventoryUtil;
import im.expensive.utils.player.MouseUtil;
import im.expensive.utils.player.MoveUtils;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Pose;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "LongJump", type = Category.MOVEMENT)
public class LongJump extends Function {

    boolean placed;
    int counter;

    public ModeSetting mod = new ModeSetting("Режим", "Boost", "Boost");

    public LongJump() {
        addSettings(mod);
    }
    StopWatch stopWatch = new StopWatch();
    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mod.is("Boost") && !mc.player.isInWater()) {

            int slot = InventoryUtil.getSlotInInventoryOrHotbar();
            if (slot == -1) {
                print("Для работы LongJump требуются полублоки в хотбаре!");
                toggle();
                return;
            }
            int old = mc.player.inventory.currentItem;

            if (MouseUtil.rayTraceResult(2, mc.player.rotationYaw, 800, mc.player) instanceof BlockRayTraceResult result) {
                if (MoveUtils.isMoving()) {
                    if (mc.player.fallDistance >= 4 && mc.world.getBlockState(mc.player.getPosition()).isAir() && !mc.world.getBlockState(result.getPos()).isAir() && mc.world.getBlockState(result.getPos()).isSolid() && !(mc.world.getBlockState(result.getPos()).getBlock() instanceof SlabBlock) && !(mc.world.getBlockState(result.getPos()).getBlock() instanceof StairsBlock)) {

                        mc.player.inventory.currentItem = slot;
                        placed = true;
                        mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, result);
                        mc.player.inventory.currentItem = old;
                        mc.player.fallDistance = 0;
                    }
                    mc.gameSettings.keyBindJump.pressed = false;


                    if ((mc.player.isOnGround() && !mc.gameSettings.keyBindJump.pressed)
                            && placed
                            && mc.world.getBlockState(mc.player.getPosition()).isAir()
                            && !mc.world.getBlockState(result.getPos()).isAir()
                            && mc.world.getBlockState(result.getPos()).isSolid()
                            && !(mc.world.getBlockState(result.getPos()).getBlock() instanceof SlabBlock) && stopWatch.isReached(750)) {

                        mc.player.setPose(Pose.STANDING);



                        stopWatch.reset();
                        placed = false;
                    } else if ((mc.player.isOnGround() && !mc.gameSettings.keyBindJump.pressed)) {
                        mc.player.jump();
                        placed = false;
                    }
                }
            } else {
                if ((mc.player.isOnGround() && !mc.gameSettings.keyBindJump.pressed)) {
                    mc.player.jump();
                    placed = false;
                }
            }
        }
    }
    @Subscribe
    public void onMoving(MovingEvent e) {
    }
    @Subscribe
    public void onFlag(EventPacket e) {
        if (e.getPacket() instanceof SPlayerPositionLookPacket p) {
            placed = false;
            counter = 3;
            mc.player.setPose(Pose.STANDING);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        counter = 3;
        placed = false;
    }


}
