package wwe.modules.world;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import wwe.modules.exploits.*;
import wwe.*;

public class Surround extends qMIe
{
    public sbfc Xijd;
    public ArrayList<BlockPos> dzbi;
    public BlockPos hEIf;
    public float nfJX;
    public float fJqi;
    public NumberValue delay;
    public BooleanValue legit;
    
    public Surround() {
        super("Surround", "Places blocks around you, good for explosions", 0, Category.WORLD);
        this.Xijd = new sbfc();
        this.dzbi = new ArrayList<BlockPos>();
    }
    
    @Override
    public void HrDr() {
        this.delay = new NumberValue("Delay", this, "Delay till next block is placed", 0.1, 0.0, 1.0);
        this.legit = new BooleanValue("Legit", this, "Legit rotations", false);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if ((Wrapper.mc.world.getBlockState(new BlockPos(this.dzbi.get(0).getX(), this.dzbi.get(0).getY() + 1, this.dzbi.get(0).getZ())).getBlock() != Blocks.AIR || !Wrapper.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(this.dzbi.get(0).add(0, 1, 0))).isEmpty()) && (Wrapper.mc.world.getBlockState(new BlockPos(this.dzbi.get(1).getX(), this.dzbi.get(1).getY() + 1, this.dzbi.get(1).getZ())).getBlock() != Blocks.AIR || !Wrapper.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(this.dzbi.get(1).add(0, 1, 0))).isEmpty()) && (Wrapper.mc.world.getBlockState(new BlockPos(this.dzbi.get(2).getX(), this.dzbi.get(2).getY() + 1, this.dzbi.get(2).getZ())).getBlock() != Blocks.AIR || !Wrapper.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(this.dzbi.get(2).add(0, 1, 0))).isEmpty()) && (Wrapper.mc.world.getBlockState(new BlockPos(this.dzbi.get(3).getX(), this.dzbi.get(3).getY() + 1, this.dzbi.get(3).getZ())).getBlock() != Blocks.AIR || !Wrapper.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(this.dzbi.get(3).add(0, 1, 0))).isEmpty())) {
            this.GDVp();
        }
        final int currentItem = Wrapper.mc.player.inventory.currentItem;
        int n = -1;
        int i = 0;
        while (i < 9) {
            final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
            if (!jjZz.cKMx(stackInSlot) && stackInSlot.getItem() instanceof ItemBlock && Block.getBlockFromItem(stackInSlot.getItem()).getDefaultState().isFullBlock()) {
                n = i;
                if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
                if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                    throw null;
                }
                continue;
            }
        }
        if (n == -1) {
            this.GDVp();
            return;
        }
        this.hEIf = null;
        for (final BlockPos heIf : this.dzbi) {
            if (Wrapper.mc.world.getBlockState(heIf).getBlock() != null && Wrapper.mc.world.getBlockState(new BlockPos(heIf.getX(), heIf.getY() + 1, heIf.getZ())).getBlock() instanceof BlockAir && Wrapper.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(heIf.add(0, 1, 0))).isEmpty()) {
                if (Wrapper.mc.world.getBlockState(heIf.down()).getBlock() instanceof BlockAir) {
                    this.hEIf = heIf.down().down();
                    if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                        throw null;
                    }
                    break;
                }
                else if (Wrapper.mc.world.getBlockState(heIf).getBlock() instanceof BlockAir) {
                    this.hEIf = heIf.down();
                    if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                        throw null;
                    }
                    break;
                }
                else {
                    this.hEIf = heIf;
                    if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                        throw null;
                    }
                    break;
                }
            }
            else {
                if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                    throw null;
                }
                continue;
            }
        }
        if (this.hEIf == null) {
            return;
        }
        this.Xijd.gCgh();
        omqH.prFd = this.YiAl(this.hEIf.up().getX(), this.hEIf.up().getY(), this.hEIf.up().getZ())[0];
        omqH.OjyD = this.YiAl(this.hEIf.up().getX(), this.hEIf.up().getY(), this.hEIf.up().getZ())[1];
    }
    
    public float[] YiAl(final double n, final double n2, final double n3) {
        final double n4 = n - Wrapper.mc.player.posX;
        final double n5 = n2 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        final double n6 = n3 - Wrapper.mc.player.posZ;
        final double n7 = MathHelper.sqrt(n4 * n4 + n6 * n6);
        final float n8 = (float)Math.toDegrees(Math.atan2(n6, n4)) - 90.3f;
        final float n9 = (float)(-Math.toDegrees(Math.atan2(n5, n7)));
        if (this.legit.OnGi()) {
            final EntityPlayerSP player = Wrapper.mc.player;
            player.rotationYaw += MathHelper.wrapDegrees(n8 - Wrapper.mc.player.rotationYaw);
            final EntityPlayerSP player2 = Wrapper.mc.player;
            player2.rotationPitch += MathHelper.wrapDegrees(n9 - Wrapper.mc.player.rotationPitch);
        }
        return new float[] { Wrapper.mc.player.rotationYaw + MathHelper.wrapDegrees(n8 - Wrapper.mc.player.rotationYaw), Wrapper.mc.player.rotationPitch + MathHelper.wrapDegrees(n9 - Wrapper.mc.player.rotationPitch) };
    }
    
    @oqrr
    public void ooHs(final ILBH ilbh) {
        if (this.hEIf == null) {
            return;
        }
        final int currentItem = Wrapper.mc.player.inventory.currentItem;
        int currentItem2 = -1;
        int i = 0;
        while (i < 9) {
            final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
            if (!jjZz.cKMx(stackInSlot) && stackInSlot.getItem() instanceof ItemBlock && Block.getBlockFromItem(stackInSlot.getItem()).getDefaultState().isFullBlock()) {
                currentItem2 = i;
                if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
                if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                    throw null;
                }
                continue;
            }
        }
        if (currentItem2 == -1) {
            return;
        }
        if (Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
            if (Surround.\u200d\u2003\u2001\u2008\u2002\u200b\u200a\u2003) {
                throw null;
            }
        }
        else {
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem2));
            Wrapper.mc.player.inventory.currentItem = currentItem2;
            Wrapper.mc.playerController.updateController();
        }
        if (this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
            Wrapper.mc.playerController.processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, this.hEIf, EnumFacing.UP, new Vec3d((double)this.hEIf.getX(), (double)this.hEIf.getY(), (double)this.hEIf.getZ()), EnumHand.MAIN_HAND);
            OffHand.LInL();
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem));
            Wrapper.mc.player.inventory.currentItem = currentItem;
            Wrapper.mc.playerController.updateController();
            this.Xijd.BBwj();
        }
        Wrapper.mc.player.inventory.currentItem = currentItem;
    }
    
    @Override
    public void LPxx() {
        this.Xijd.BBwj();
        final double cos = Math.cos(Math.toRadians(0.0));
        final double sin = Math.sin(Math.toRadians(0.0));
        this.dzbi.add(new BlockPos(Wrapper.mc.player.posX + (1.0 * cos + 0.0 * sin), Wrapper.mc.player.posY - 1.0, Wrapper.mc.player.posZ + (1.0 * sin - 0.0 * cos)));
        this.dzbi.add(new BlockPos(Wrapper.mc.player.posX + (0.0 * cos + 1.0 * sin), Wrapper.mc.player.posY - 1.0, Wrapper.mc.player.posZ + (0.0 * sin - 1.0 * cos)));
        this.dzbi.add(new BlockPos(Wrapper.mc.player.posX + (-1.0 * cos + 0.0 * sin), Wrapper.mc.player.posY - 1.0, Wrapper.mc.player.posZ + (-1.0 * sin - 0.0 * cos)));
        this.dzbi.add(new BlockPos(Wrapper.mc.player.posX + (0.0 * cos + -1.0 * sin), Wrapper.mc.player.posY - 1.0, Wrapper.mc.player.posZ + (0.0 * sin - -1.0 * cos)));
        hSNE.lyvR(this);
        this.nfJX = Wrapper.mc.player.rotationYaw;
        this.fJqi = Wrapper.mc.player.rotationPitch;
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        this.Xijd.BBwj();
        this.dzbi.clear();
        hSNE.kduK(this);
        if (this.legit.OnGi()) {
            Wrapper.mc.player.rotationYaw = this.nfJX;
            Wrapper.mc.player.rotationPitch = this.fJqi;
        }
        super.qBmy();
    }
}
