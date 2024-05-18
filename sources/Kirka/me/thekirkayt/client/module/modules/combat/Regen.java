/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@Module.Mod
public class Regen
extends Module {
    @Option.Op(min=0.0, max=6000.0, increment=100.0)
    private double packets = 3000.0;
    @Option.Op(name="Potion Packets", min=0.0, max=200.0, increment=10.0)
    private double potionPackets = 60.0;
    @Option.Op(min=0.0, max=10.0, increment=0.5)
    private double health = 8.0;
    @Option.Op
    private boolean potion;

    @Override
    public void enable() {
        super.enable();
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        block4 : {
            block3 : {
                event.setCancelled(true);
                if (!this.potion) break block3;
                if (ClientUtils.player().isUsingItem() || !ClientUtils.player().isPotionActive(Potion.regeneration) || ClientUtils.player().getActivePotionEffect(Potion.regeneration).getDuration() <= 0 || !((double)ClientUtils.player().getHealth() <= this.health * 2.0) || !ClientUtils.player().isCollidedVertically && !this.isInsideBlock() || !event.getState().equals((Object)Event.State.POST)) break block4;
                int i = 0;
                while ((double)i < this.potionPackets) {
                    ClientUtils.player().getActivePotionEffect(Potion.regeneration).deincrementDuration();
                    ClientUtils.packet(new C03PacketPlayer(true));
                    ++i;
                }
                break block4;
            }
            if ((double)ClientUtils.player().getHealth() <= this.health * 2.0 && (ClientUtils.player().isCollidedVertically || this.isInsideBlock()) && event.getState().equals((Object)Event.State.POST)) {
                int i = 0;
                while ((double)i < this.packets) {
                    ClientUtils.packet(new C03PacketPlayer(true));
                    ++i;
                }
            }
        }
    }

    private boolean isInsideBlock() {
        for (int x = MathHelper.floor_double((double)ClientUtils.player().boundingBox.minX); x < MathHelper.floor_double(ClientUtils.player().boundingBox.maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double((double)ClientUtils.player().boundingBox.minY); y < MathHelper.floor_double(ClientUtils.player().boundingBox.maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double((double)ClientUtils.player().boundingBox.minZ); z < MathHelper.floor_double(ClientUtils.player().boundingBox.maxZ) + 1; ++z) {
                    Block block = ClientUtils.world().getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == null || block instanceof BlockAir) continue;
                    AxisAlignedBB boundingBox = block.getCollisionBoundingBox(ClientUtils.world(), new BlockPos(x, y, z), ClientUtils.world().getBlockState(new BlockPos(x, y, z)));
                    if (block instanceof BlockHopper) {
                        boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                    }
                    if (boundingBox == null || !ClientUtils.player().boundingBox.intersectsWith(boundingBox)) continue;
                    return true;
                }
            }
        }
        return false;
    }
}

