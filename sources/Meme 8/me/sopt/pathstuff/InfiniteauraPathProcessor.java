// 
// Decompiled by Procyon v0.5.30
// 

package me.sopt.pathstuff;

import java.util.ArrayList;

import com.google.common.collect.Iterables;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class InfiniteauraPathProcessor extends PathProcessor
{
    private final boolean creativeFlying;
    private boolean stopped;
    public static ArrayList<BlockPos> tpPosList;
    private EntityLivingBase target;
    
    static {
        InfiniteauraPathProcessor.tpPosList = new ArrayList<BlockPos>();
    }
    
    public InfiniteauraPathProcessor(final ArrayList<PathPos> path, final boolean creativeFlying, final EntityLivingBase target) {
        super(path);
        this.creativeFlying = creativeFlying;
        this.target = target;
    }
    
    @Override
    public void process() {
        if (this.target == null) {
            return;
        }
		if (true) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 16.384, mc.thePlayer.posZ, true));
		}for (int o = 0; o < this.path.size(); ++o) {
            final BlockPos pos = new BlockPos(this.mc.thePlayer);
            final BlockPos nextPos = this.path.get(this.index);
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(nextPos.getX() + 0.5, nextPos.getY(), nextPos.getZ() + 0.5, true));
            InfiniteauraPathProcessor.tpPosList.add(nextPos);
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
        mc.thePlayer.swingItem();
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, Action.ATTACK));
        for (int o = 0; o < InfiniteauraPathProcessor.tpPosList.size(); ++o) {
            final BlockPos tpPos = (BlockPos)Iterables.getLast((Iterable)InfiniteauraPathProcessor.tpPosList);
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(tpPos.getX() + 0.5, tpPos.getY(), tpPos.getZ() + 0.5, true));
            InfiniteauraPathProcessor.tpPosList.remove(InfiniteauraPathProcessor.tpPosList.size() - 1);
        }
		if (true) {
		}
    }
    
    @Override
    public void lockControls() {
    }
}
