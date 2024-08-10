package cc.slack.features.modules.impl.world;


import cc.slack.start.Slack;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.WorldEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.other.BlockUtils;
import cc.slack.utils.other.TimeUtil;
import cc.slack.utils.player.AttackUtil;
import cc.slack.utils.render.RenderUtil;
import cc.slack.utils.rotations.RotationUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.ArrayList;


@ModuleInfo(
        name = "ChestAura",
        category = Category.WORLD
)
public class ChestAura extends Module {

    public final NumberValue<Double> radiusDist = new NumberValue<>("Radius", 4.5, 1.0, 7.0, 0.5);
    public final NumberValue<Integer> switchDelay = new NumberValue<>("Switch Delay", 50, 0, 500, 10);

    public final BooleanValue onlyGround = new BooleanValue("Only Ground", false);

    public final BooleanValue render = new BooleanValue("Render Chests", true);

    public ChestAura() {
        addSettings(radiusDist, switchDelay, onlyGround, render);
    }

    ArrayList<BlockPos> openedChests = new ArrayList<>();
    TimeUtil switchTimer = new TimeUtil();
    BlockPos currentBlock;

    boolean waitToClose = false;

    @Override
    public void onEnable() {
        openedChests.clear();
        switchTimer.reset();
        waitToClose = false;
    }

    @Listen
    public void onWorld(WorldEvent event) {
        openedChests.clear(); // its fucing 2 am bro
        switchTimer.reset();
    }

    @Listen
    public void onRender(RenderEvent event) {
        if (event.getState() != RenderEvent.State.RENDER_3D) return;

        int radius = (int) Math.ceil(radiusDist.getValue());

        for(int x = radius; x >=-radius +1; x--) {
            for (int y = radius; y >= -radius + 1; y--) {
                for (int z = radius; z >= -radius + 1; z--) {
                    BlockPos blockPos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
                    Block block = BlockUtils.getBlock(blockPos);
                    if (block != null) {
                        if (block instanceof BlockChest) {
                            if (openedChests.contains(blockPos)) {
                                RenderUtil.drawBlock(blockPos, new Color(120, 120, 120, 255), 4f);
                            } else {
                                RenderUtil.drawBlock(blockPos, new Color(20, 255, 20, 255), 4f);
                            }
                        }
                    }
                }
            }
        }
    }

    @Listen
    public void onMotion(MotionEvent event) {
        if (onlyGround.getValue() && !mc.thePlayer.onGround) return;
        if (Slack.getInstance().getModuleManager().getInstance(Scaffold.class).isToggle()) return;
        if (AttackUtil.inCombat) return;

        if (currentBlock == null && switchTimer.hasReached(switchDelay.getValue()) && !(mc.currentScreen instanceof GuiChest)) {
            currentBlock = findNextBlock();
            waitToClose = false;
        } else if (!(mc.currentScreen instanceof GuiChest) && waitToClose) {
            currentBlock = null;
            waitToClose = false;
            switchTimer.reset();

        }

        if (currentBlock != null) {
            if (openedChests.contains(currentBlock) && !waitToClose) {
                if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), currentBlock, EnumFacing.UP, new Vec3(0.5, 1, 0.5))) {
                    mc.thePlayer.swingItem();
                    waitToClose = true;
                }
            } else if (!openedChests.contains(currentBlock)) {
                openedChests.add(currentBlock);
                RotationUtil.setClientRotation(BlockUtils.getFaceRotation(EnumFacing.UP, currentBlock), 2);
            }
        }
    }

    private BlockPos findNextBlock(){
        int radius = (int) Math.ceil(radiusDist.getValue());

        for(int x = radius; x >=-radius +1; x--) {
            for (int y = radius; y >= -radius + 1; y--) {
                for (int z = radius; z >= -radius + 1; z--) {
                    BlockPos blockPos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
                    Block block = BlockUtils.getBlock(blockPos);
                    if (block != null) {
                        if (block instanceof BlockChest) {
                            if (!openedChests.contains(blockPos)) {
                                return blockPos;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
