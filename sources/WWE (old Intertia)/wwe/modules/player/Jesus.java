package wwe.modules.player;

import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import wwe.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;

public class Jesus extends qMIe
{
    private boolean VpUa;
    private int pcWm;
    public static AxisAlignedBB RYee;
    public BooleanValue oldPackets;
    public BooleanValue test;
    public ModeValue mode;
    
    public Jesus() {
        super("Jesus", "Allows you to walk on water", 0, Category.PLAYER);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Solid");
        list.add("Bouncy");
        list.add("Mini Jump");
        this.oldPackets = new BooleanValue("Old Packets", this, "Doesent send packet", false);
        this.test = new BooleanValue("Test", this, "Just a test,you'll have to wonder what it does(;", false);
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Solid", list);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Solid")) {
            this.owgW("Jesus" + TextFormatting.DARK_GRAY + " Solid");
            if (yPGD((Entity)Wrapper.mc.player) && !Wrapper.mc.player.isSneaking() && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump)) {
                Wrapper.mc.player.motionY = 0.1;
                if (Wrapper.mc.player.getRidingEntity() != null && !(Wrapper.mc.player.getRidingEntity() instanceof EntityBoat)) {
                    Wrapper.mc.player.getRidingEntity().motionY = 0.3;
                }
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Bouncy")) {
            this.owgW("Jesus" + TextFormatting.DARK_GRAY + " Bouncy");
            if (Wrapper.mc.player.isSneaking()) {
                return;
            }
            if (yPGD((Entity)Wrapper.mc.player) && !Wrapper.mc.player.isSneaking() && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump)) {
                Wrapper.mc.player.motionY = 0.1;
                return;
            }
            if (Wrapper.mc.player.onGround || Wrapper.mc.player.isOnLadder()) {
                this.VpUa = false;
            }
            if (Wrapper.mc.player.motionY > 0.0) {
                if (Wrapper.mc.player.motionY < 0.03 && this.VpUa) {
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.motionY += 0.06713;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                else if (Wrapper.mc.player.motionY <= 0.05 && this.VpUa) {
                    final EntityPlayerSP player2 = Wrapper.mc.player;
                    player2.motionY *= 1.20000000999;
                    final EntityPlayerSP player3 = Wrapper.mc.player;
                    player3.motionY += 0.06;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                else if (Wrapper.mc.player.motionY <= 0.08 && this.VpUa) {
                    final EntityPlayerSP player4 = Wrapper.mc.player;
                    player4.motionY *= 1.20000003;
                    final EntityPlayerSP player5 = Wrapper.mc.player;
                    player5.motionY += 0.055;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                else if (Wrapper.mc.player.motionY <= 0.112 && this.VpUa) {
                    final EntityPlayerSP player6 = Wrapper.mc.player;
                    player6.motionY += 0.0535;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                else if (this.VpUa) {
                    final EntityPlayerSP player7 = Wrapper.mc.player;
                    player7.motionY *= 1.000000000002;
                    final EntityPlayerSP player8 = Wrapper.mc.player;
                    player8.motionY += 0.0517;
                }
            }
            if (this.VpUa && Wrapper.mc.player.motionY < 0.0 && Wrapper.mc.player.motionY > -0.3) {
                final EntityPlayerSP player9 = Wrapper.mc.player;
                player9.motionY += 0.045835;
            }
            if (!this.test.OnGi()) {
                Wrapper.mc.player.fallDistance = 0.0f;
            }
            if (!FoCK((Entity)Wrapper.mc.player, true)) {
                return;
            }
            if (FoCK((Entity)Wrapper.mc.player, true)) {
                Wrapper.mc.player.motionY = 0.5;
            }
            this.VpUa = true;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Mini Jump")) {
            this.owgW("Jesus" + TextFormatting.DARK_GRAY + " Mini Jump");
            if (Wrapper.mc.player.isSneaking()) {
                return;
            }
            if (yPGD((Entity)Wrapper.mc.player) && !Wrapper.mc.player.isSneaking() && !yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump)) {
                Wrapper.mc.player.motionY = 0.1;
                return;
            }
            if (Wrapper.mc.player.onGround || Wrapper.mc.player.isOnLadder()) {
                this.VpUa = false;
            }
            if (Wrapper.mc.player.motionY > 0.0) {
                if (Wrapper.mc.player.motionY < 0.03 && this.VpUa) {
                    final EntityPlayerSP player10 = Wrapper.mc.player;
                    player10.motionY += 0.06713;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                else if (Wrapper.mc.player.motionY <= 0.05 && this.VpUa) {
                    final EntityPlayerSP player11 = Wrapper.mc.player;
                    player11.motionY += 0.06;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                else if (Wrapper.mc.player.motionY <= 0.08 && this.VpUa) {
                    final EntityPlayerSP player12 = Wrapper.mc.player;
                    player12.motionY += 0.055;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                else if (Wrapper.mc.player.motionY <= 0.112 && this.VpUa) {
                    final EntityPlayerSP player13 = Wrapper.mc.player;
                    player13.motionY += 0.0535;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                else if (this.VpUa) {
                    final EntityPlayerSP player14 = Wrapper.mc.player;
                    player14.motionY *= 0.500000000002;
                    final EntityPlayerSP player15 = Wrapper.mc.player;
                    player15.motionY += 0.0517;
                }
            }
            if (this.VpUa && Wrapper.mc.player.motionY < 0.0 && Wrapper.mc.player.motionY > -0.3) {
                final EntityPlayerSP player16 = Wrapper.mc.player;
                player16.motionY += 0.045835;
            }
            Wrapper.mc.player.fallDistance = 0.0f;
            if (!FoCK((Entity)Wrapper.mc.player, true)) {
                return;
            }
            if (FoCK((Entity)Wrapper.mc.player, true)) {
                Wrapper.mc.player.motionY = 0.2;
            }
            this.VpUa = true;
        }
    }
    
    @oqrr
    public void ffoK(final sFUx sfUx) {
    }
    
    @oqrr
    public void gbjy(final uqzS uqzS) {
        if (!this.mode.OnGi().equalsIgnoreCase("Solid")) {
            return;
        }
        if (!Wrapper.mc.player.isRiding() && uqzS.MYlK() instanceof CPacketPlayer) {
            if (Wrapper.mc.player.fallDistance >= 4.0f) {
                return;
            }
            if (FoCK((Entity)Wrapper.mc.player, true) && !yPGD((Entity)Wrapper.mc.player) && !heyv((Entity)Wrapper.mc.player)) {
                final int n = Wrapper.mc.player.ticksExisted % 2;
                yYGD.NXja((CPacketPlayer)uqzS.MYlK(), yYGD.Oixl((CPacketPlayer)uqzS.MYlK()) + 0.02);
                if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                    throw null;
                }
            }
        }
        else if (Wrapper.mc.player.isRiding() && uqzS.MYlK() instanceof CPacketVehicleMove) {
            if (this.oldPackets.OnGi()) {
                return;
            }
            if (Wrapper.mc.player.fallDistance >= 4.0f) {
                return;
            }
            if (FoCK(Wrapper.mc.player.getRidingEntity(), true) && !yPGD(Wrapper.mc.player.getRidingEntity()) && !heyv(Wrapper.mc.player.getRidingEntity())) {
                final int n2 = Wrapper.mc.player.getRidingEntity().ticksExisted % 2;
                yYGD.sXuu((CPacketVehicleMove)uqzS.MYlK(), 0.0);
            }
        }
    }
    
    @oqrr
    public void DwMG(final LqJY lqJY) {
        if (lqJY.MYlK() instanceof SPacketMoveVehicle) {
            lqJY.Ggez(true);
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
    
    private static boolean heyv(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double n = entity.posY - 0.01;
        int i = MathHelper.floor(entity.posX);
        while (i < MathHelper.ceil(entity.posX)) {
            int j = MathHelper.floor(entity.posZ);
            while (j < MathHelper.ceil(entity.posZ)) {
                final BlockPos blockPos = new BlockPos(i, MathHelper.floor(n), j);
                if (Wrapper.mc.world.getBlockState(blockPos).getBlock().isFullBlock(Wrapper.mc.world.getBlockState(blockPos))) {
                    return true;
                }
                ++j;
                if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                    throw null;
                }
            }
            ++i;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        return false;
    }
    
    public static boolean hVXg(final Entity entity, final BlockPos blockPos) {
        boolean b;
        if (entity.posY >= blockPos.getY()) {
            b = true;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public static boolean FoCK(final Entity entity, final boolean b) {
        if (entity == null) {
            return false;
        }
        final double posY = entity.posY;
        double n;
        if (b) {
            n = 0.03;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        else if (aite(entity)) {
            n = 0.2;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        else {
            n = 0.5;
        }
        final double n2 = posY - n;
        int i = MathHelper.floor(entity.posX);
        while (i < MathHelper.ceil(entity.posX)) {
            int j = MathHelper.floor(entity.posZ);
            while (j < MathHelper.ceil(entity.posZ)) {
                if (Wrapper.mc.world.getBlockState(new BlockPos(i, MathHelper.floor(n2), j)).getBlock() instanceof BlockLiquid) {
                    return true;
                }
                ++j;
                if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                    throw null;
                }
            }
            ++i;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        return false;
    }
    
    public static boolean aite(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public static boolean yPGD(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double n = entity.posY + 0.01;
        int i = MathHelper.floor(entity.posX);
        while (i < MathHelper.ceil(entity.posX)) {
            int j = MathHelper.floor(entity.posZ);
            while (j < MathHelper.ceil(entity.posZ)) {
                if (Wrapper.mc.world.getBlockState(new BlockPos(i, (int)n, j)).getBlock() instanceof BlockLiquid) {
                    return true;
                }
                ++j;
                if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                    throw null;
                }
            }
            ++i;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        return false;
    }
    
    public static boolean jthT(final int n) {
        int n2 = n;
        int n3 = n;
        int n4 = n;
        int n5 = n;
        if (Wrapper.mc.player.motionX > 0.009999999776482582) {
            n2 = 0;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        else if (Wrapper.mc.player.motionX < -0.009999999776482582) {
            n4 = 0;
        }
        if (Wrapper.mc.player.motionZ > 0.009999999776482582) {
            n3 = 0;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        else if (Wrapper.mc.player.motionZ < -0.009999999776482582) {
            n5 = 0;
        }
        final int floor = MathHelper.floor(Wrapper.mc.player.getEntityBoundingBox().minX - n2);
        final int floor2 = MathHelper.floor(Wrapper.mc.player.getEntityBoundingBox().minY - 1.0);
        final int floor3 = MathHelper.floor(Wrapper.mc.player.getEntityBoundingBox().minZ - n3);
        final int floor4 = MathHelper.floor(Wrapper.mc.player.getEntityBoundingBox().minX + n4);
        final int floor5 = MathHelper.floor(Wrapper.mc.player.getEntityBoundingBox().minY + 1.0);
        final int floor6 = MathHelper.floor(Wrapper.mc.player.getEntityBoundingBox().minZ + n5);
        int i = floor;
        while (i <= floor4) {
            int j = floor2;
            while (j <= floor5) {
                int k = floor3;
                while (k <= floor6) {
                    if (Wrapper.mc.world.getBlockState(new BlockPos(i, j, k)).getBlock() instanceof BlockLiquid && !Wrapper.mc.player.isInsideOfMaterial(Material.LAVA) && !Wrapper.mc.player.isInsideOfMaterial(Material.WATER)) {
                        return true;
                    }
                    ++k;
                    if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                        throw null;
                    }
                }
                ++j;
                if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                    throw null;
                }
            }
            ++i;
            if (Jesus.\u200d\u2008\u2001\u200f\u2007\u200a) {
                throw null;
            }
        }
        return false;
    }
    
    static {
        Jesus.RYee = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.9999, 1.0);
    }
}
