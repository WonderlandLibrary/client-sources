package client.metaware.impl.utils.pathfinder;

import net.minecraft.client.entity.EntityPlayerSP;
import com.google.common.collect.Iterables;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import java.util.ArrayList;

public class TpPathProcessor extends PathProcessor
{
    private final boolean creativeFlying;
    private boolean stopped;
    public static boolean tpback;
    public static ArrayList<BlockPos> tpPosList;

    static {
        tpback = false;
        tpPosList = new ArrayList<BlockPos>();
    }

    public TpPathProcessor(final ArrayList<PathPos> path, final boolean creativeFlying) {
        super(path);
        this.creativeFlying = creativeFlying;
    }

    @Override
    public void process() {
        for (int o = 0; o < this.path.size(); ++o) {
            final BlockPos pos = new BlockPos(this.mc.thePlayer);
            final BlockPos nextPos = this.path.get(this.index);
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(nextPos.getX() + 0.5, nextPos.getY(), nextPos.getZ() + 0.5, true));
            this.mc.thePlayer.setPosition(nextPos.getX() + 0.5, nextPos.getY(), nextPos.getZ() + 0.5);
            tpPosList.add(nextPos);
            ++this.index;
            if (this.index < this.path.size()) {
                if (this.creativeFlying && this.index >= 2) {
                    final BlockPos prevPos = this.path.get(this.index - 1);
                    if (!this.path.get(this.index).subtract(prevPos).equals(prevPos.subtract(this.path.get(this.index - 2))) && !this.stopped) {
                        final EntityPlayerSP thePlayer = this.mc.thePlayer;
                        thePlayer.motionX /= Math.max(Math.abs(this.mc.thePlayer.motionX) * 50.0, 1.0);
                        final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                        thePlayer2.motionY /= Math.max(Math.abs(this.mc.thePlayer.motionY) * 50.0, 1.0);
                        final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                        thePlayer3.motionZ /= Math.max(Math.abs(this.mc.thePlayer.motionZ) * 50.0, 1.0);
                        this.stopped = true;
                    }
                }
            }
            else {
                this.done = true;
            }
        }
        if (!tpback) {
            return;
        }
        for (int o = 0; o < tpPosList.size(); ++o) {
            final BlockPos tpPos = (BlockPos)Iterables.getLast((Iterable)tpPosList);
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(tpPos.getX() + 0.5, tpPos.getY(), tpPos.getZ() + 0.5, true));
            tpPosList.remove(tpPosList.size() - 1);
        }
        tpPosList.clear();
    }

    @Override
    public void lockControls() {
        super.lockControls();
        this.mc.thePlayer.capabilities.isFlying = this.creativeFlying;
    }
}