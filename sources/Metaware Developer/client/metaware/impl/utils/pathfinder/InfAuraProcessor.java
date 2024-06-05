package client.metaware.impl.utils.pathfinder;

import client.metaware.Metaware;
import client.metaware.impl.module.combat.InfiniteAura;
import client.metaware.impl.utils.render.StringUtils;
import client.metaware.impl.utils.util.PacketUtil;
import client.metaware.impl.utils.util.other.PlayerUtil;
import com.google.common.collect.Iterables;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import java.util.ArrayList;

public class InfAuraProcessor extends PathProcessor
{
    private final boolean creativeFlying;
    private boolean stopped;
    public static ArrayList<BlockPos> tpPosList;
    private EntityLivingBase target;

    static {
        tpPosList = new ArrayList<BlockPos>();
    }

    public InfAuraProcessor(final ArrayList<PathPos> path, final boolean creativeFlying, final EntityLivingBase target) {
        super(path);
        this.creativeFlying = creativeFlying;
        this.target = target;
    }

    @Override
    public void process() {
        if (this.target == null) {
            return;
        }
        for (int o = 0; o < this.path.size(); ++o) {
            final BlockPos pos = new BlockPos(this.mc.thePlayer);
            final BlockPos nextPos = this.path.get(this.index);
            PacketUtil.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(nextPos.getX() + 0.5, nextPos.getY(), nextPos.getZ() + 0.5, true));
            tpPosList.add(nextPos);
            ++this.index;
            if (this.index < this.path.size()) {
                if (this.creativeFlying && this.index >= 2) {
                    final BlockPos prevPos = this.path.get(this.index - 1);
                    if (!this.path.get(this.index).subtract(prevPos).equals(prevPos.subtract(this.path.get(this.index - 2))) && !this.stopped) {
                        this.stopped = true;
                    }
                }
            }
            else {
                this.done = true;
            }
        }
        PlayerUtil.attackEntityAtPos(this.target, (int)this.target.posX + 0.5, StringUtils.removeDecimals(this.target.posY), (int)this.target.posZ + 0.5);
        if (!Metaware.INSTANCE.getModuleManager().getModuleByClass(InfiniteAura.class).tpBack.getValue()) {
            return;
        }
        for (int o = 0; o < tpPosList.size(); ++o) {
            final BlockPos tpPos = (BlockPos)Iterables.getLast((Iterable)tpPosList);
            PacketUtil.packetNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(tpPos.getX() + 0.5, tpPos.getY(), tpPos.getZ() + 0.5, true));
            tpPosList.remove(tpPosList.size() - 1);
        }
    }

    @Override
    public void lockControls() {
    }
}