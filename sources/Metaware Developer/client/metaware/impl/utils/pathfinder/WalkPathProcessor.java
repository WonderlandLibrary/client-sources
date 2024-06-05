package client.metaware.impl.utils.pathfinder;

import client.metaware.impl.utils.util.ScaffoldUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.BlockLadder;
import net.minecraft.util.BlockPos;
import java.util.ArrayList;

public class WalkPathProcessor extends PathProcessor
{
    public WalkPathProcessor(final ArrayList<PathPos> path) {
        super(path);
    }

    @Override
    public void process() {
        final BlockPos pos = new BlockPos(this.mc.thePlayer);
        final BlockPos nextPos = this.path.get(this.index);
        if (pos.equals(nextPos)) {
            ++this.index;
            if (this.index >= this.path.size()) {
                this.done = true;
            }
            return;
        }
        this.lockControls();
        ScaffoldUtils.faceBlockClientHorizontally(nextPos);
        if (pos.getX() != nextPos.getX() || pos.getZ() != nextPos.getZ()) {
            this.mc.gameSettings.keyBindForward.pressed = true;
            if (this.mc.thePlayer.isInWater() && this.mc.thePlayer.posY < nextPos.getY()) {
                this.mc.gameSettings.keyBindJump.pressed = true;
            }
        }
        else if (pos.getY() != nextPos.getY()) {
            if (pos.getY() < nextPos.getY()) {
                if (ScaffoldUtils.getBlock(pos) instanceof BlockLadder) {
                    ScaffoldUtils.faceBlockClientHorizontally(pos.offset(((EnumFacing)ScaffoldUtils.getState(pos).getValue(BlockLadder.FACING)).getOpposite()));
                    this.mc.gameSettings.keyBindForward.pressed = true;
                }
                else {
                    this.mc.gameSettings.keyBindJump.pressed = true;
                    if (this.index < this.path.size() - 1) {
                        ScaffoldUtils.faceBlockClientHorizontally(this.path.get(this.index + 1));
                        this.mc.gameSettings.keyBindForward.pressed = true;
                    }
                }
            }
            else {
                while (this.index < this.path.size() - 1 && this.path.get(this.index).down().equals(this.path.get(this.index + 1))) {
                    ++this.index;
                }
                if (this.mc.thePlayer.onGround) {
                    this.mc.gameSettings.keyBindForward.pressed = true;
                }
            }
        }
    }

    @Override
    public void lockControls() {
        super.lockControls();
        this.mc.thePlayer.capabilities.isFlying = false;
    }
}