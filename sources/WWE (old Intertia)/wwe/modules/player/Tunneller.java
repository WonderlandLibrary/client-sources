package wwe.modules.player;

import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.function.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import wwe.modules.exploits.*;
import java.awt.*;
import wwe.*;
import net.minecraft.client.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;

public class Tunneller extends qMIe
{
    public BlockPos hEIf;
    public BooleanValue rotations;
    public BooleanValue legitRotations;
    public BooleanValue bypass;
    public BooleanValue fill;
    public BooleanValue fillLava;
    public BooleanValue fillFlowingLava;
    public BooleanValue esp;
    public NumberValue delay;
    public sbfc Xijd;
    public boolean keWR;
    public int AUfX;
    
    public Tunneller() {
        super("Tunneller", "Automatically mines a tunnel", 0, Category.PLAYER);
        this.Xijd = new sbfc();
        this.AUfX = -1;
    }
    
    @Override
    public void HrDr() {
        this.rotations = new BooleanValue("Rotations", this, "Look at the block, server side", true);
        this.legitRotations = new BooleanValue("Legit Rotations", this, "Look at the block", false);
        this.bypass = new BooleanValue("Bypass", this, "Trys to bypass a servers anticheat", false);
        this.fill = new BooleanValue("Fill", this, "Place blocks behind you", true);
        this.fillLava = new BooleanValue("Fill Lava", this, "Blocks lava Sources", true);
        this.fillFlowingLava = new BooleanValue("Fill Flowing Lava", this, "Blocks flowing lava/all lava", true);
        this.esp = new BooleanValue("ESP", this, "Draws a box around targeted blocks", false);
        this.delay = new NumberValue("Delay", this, "Delay before placing next block", 0.1, 0.0, 1.0);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        this.hEIf = null;
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        final ArrayList<BlockPos> list2 = new ArrayList<BlockPos>();
        final ArrayList<BlockPos> list3 = new ArrayList<BlockPos>();
        for (final BlockPos blockPos : BlockPos.getAllInBox(this.TWkw(new BlockPos((Entity)Wrapper.mc.player).offset(Wrapper.mc.player.getHorizontalFacing(), 3), new Vec3i(0, 1, 0)), this.TWkw(new BlockPos((Entity)Wrapper.mc.player).offset(Wrapper.mc.player.getHorizontalFacing(), 1), new Vec3i(0, 0, 0)))) {
            if (!(Wrapper.mc.world.getBlockState(blockPos).getBlock() instanceof BlockAir)) {
                list.add(blockPos);
            }
            if (Tunneller.\u200c\u200d\u2007) {
                throw null;
            }
        }
        for (final BlockPos blockPos2 : BlockPos.getAllInBox(this.TWkw(new BlockPos((Entity)Wrapper.mc.player).offset(Wrapper.mc.player.getHorizontalFacing(), 3).offset(EnumFacing.fromAngle((double)(Wrapper.mc.player.rotationYaw + 90.0f)), 1), new Vec3i(0, 1, 0)), this.TWkw(new BlockPos((Entity)Wrapper.mc.player).offset(Wrapper.mc.player.getHorizontalFacing(), 1).offset(EnumFacing.fromAngle((double)(Wrapper.mc.player.rotationYaw - 180.0f)), 4).offset(EnumFacing.fromAngle((double)(Wrapper.mc.player.rotationYaw - 90.0f)), 1), new Vec3i(0, 0, 0)))) {
            if (Wrapper.mc.world.getBlockState(blockPos2).getBlock() instanceof BlockLiquid) {
                final BlockLiquid blockLiquid = (BlockLiquid)Wrapper.mc.world.getBlockState(blockPos2).getBlock();
                if ((int)Wrapper.mc.world.getBlockState(blockPos2).getValue((IProperty)BlockLiquid.LEVEL) == 0 || this.fillFlowingLava.OnGi()) {
                    list3.add(blockPos2);
                }
            }
            if (Tunneller.\u200c\u200d\u2007) {
                throw null;
            }
        }
        for (final BlockPos blockPos3 : BlockPos.getAllInBox(this.TWkw(new BlockPos((Entity)Wrapper.mc.player).offset(EnumFacing.fromAngle(Wrapper.mc.player.rotationYaw - 180.0), 3), new Vec3i(0, 1, 0)), this.TWkw(new BlockPos((Entity)Wrapper.mc.player).offset(EnumFacing.fromAngle(Wrapper.mc.player.rotationYaw - 180.0), 1), new Vec3i(0, 0, 0)))) {
            if (Wrapper.mc.world.getBlockState(blockPos3).getBlock() instanceof BlockAir && FIwW.ecXb(blockPos3)) {
                list2.add(blockPos3);
            }
            if (Tunneller.\u200c\u200d\u2007) {
                throw null;
            }
        }
        int n = -1;
        int i = 0;
        while (i < 9) {
            final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
            if (!jjZz.cKMx(stackInSlot) && stackInSlot.getItem() instanceof ItemBlock && Block.getBlockFromItem(stackInSlot.getItem()).getDefaultState().isFullCube()) {
                n = i;
                if (Tunneller.\u200c\u200d\u2007) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
                if (Tunneller.\u200c\u200d\u2007) {
                    throw null;
                }
                continue;
            }
        }
        if ((this.fillLava.OnGi() || this.fillFlowingLava.OnGi()) && list3.size() > 0 && n != -1) {
            this.AUfX = 2;
            if (Tunneller.\u200c\u200d\u2007) {
                throw null;
            }
        }
        else if (this.fill.OnGi() && list2.size() > 0 && n != -1) {
            this.AUfX = 1;
            if (Tunneller.\u200c\u200d\u2007) {
                throw null;
            }
        }
        else {
            this.AUfX = 0;
        }
        if (this.AUfX == 2) {
            final Iterator<BlockPos> iterator4 = list3.iterator();
            if (iterator4.hasNext()) {
                this.hEIf = iterator4.next().add(0, -1, 0);
                if (Tunneller.\u200c\u200d\u2007) {
                    throw null;
                }
            }
            omqH.prFd = WFux.Goyn(this.hEIf)[0];
            omqH.OjyD = WFux.Goyn(this.hEIf)[1];
        }
        if (this.AUfX == 0) {
            BlockPos heIf = null;
            double n2 = 0.0;
            for (final BlockPos blockPos4 : list) {
                if (Wrapper.mc.world.getBlockState(blockPos4).getBlock() != Blocks.AIR && Wrapper.mc.world.getBlockState(blockPos4).getBlock() != Blocks.BEDROCK && !(Wrapper.mc.world.getBlockState(blockPos4).getBlock() instanceof BlockLiquid)) {
                    final double iTtI = ITtI(blockPos4.getX(), blockPos4.getY(), blockPos4.getZ());
                    if (heIf != null) {
                        if (iTtI < n2) {
                            heIf = blockPos4;
                            n2 = iTtI;
                            if (Tunneller.\u200c\u200d\u2007) {
                                throw null;
                            }
                        }
                    }
                    else {
                        heIf = blockPos4;
                        n2 = iTtI;
                    }
                    this.hEIf = heIf;
                }
                if (Tunneller.\u200c\u200d\u2007) {
                    throw null;
                }
            }
            if (this.hEIf == null) {
                Wrapper.mc.playerController.resetBlockRemoving();
                if (Tunneller.\u200c\u200d\u2007) {
                    throw null;
                }
            }
            else {
                if (this.rotations.OnGi()) {
                    final EntityPlayerSP player = Wrapper.mc.player;
                    player.rotationPitch += 9.0E-4f;
                    omqH.prFd = WFux.Goyn(this.hEIf)[0];
                    omqH.OjyD = WFux.Goyn(this.hEIf)[1];
                }
                if (this.legitRotations.OnGi()) {
                    WFux.oAdl(this.hEIf);
                    final EntityPlayerSP player2 = Wrapper.mc.player;
                    player2.rotationPitch += 9.0E-4f;
                }
                if (this.bypass.OnGi()) {
                    final EntityPlayerSP player3 = Wrapper.mc.player;
                    player3.motionX *= 0.3;
                    final EntityPlayerSP player4 = Wrapper.mc.player;
                    player4.motionZ *= 0.3;
                }
            }
        }
        if (this.AUfX == 1 && n != -1 && this.fill.OnGi()) {
            this.Xijd.gCgh();
            this.hEIf = null;
            BlockPos heIf2 = null;
            double n3 = 3.0;
            for (final BlockPos blockPos5 : list2) {
                final List entitiesWithinAABB = Wrapper.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(blockPos5));
                entitiesWithinAABB.removeIf(XJPO::qLRg);
                if (Wrapper.mc.world.getBlockState(blockPos5).getBlock() == Blocks.AIR && !(Wrapper.mc.world.getBlockState(blockPos5.add(0, -1, 0)).getBlock() instanceof BlockAir) && entitiesWithinAABB.isEmpty() && FIwW.ecXb(blockPos5)) {
                    final double iTtI2 = ITtI(blockPos5.getX(), blockPos5.getY(), blockPos5.getZ());
                    if (heIf2 != null) {
                        if (iTtI2 > n3) {
                            heIf2 = blockPos5;
                            n3 = iTtI2;
                            if (Tunneller.\u200c\u200d\u2007) {
                                throw null;
                            }
                        }
                    }
                    else {
                        heIf2 = blockPos5;
                        n3 = iTtI2;
                    }
                    this.hEIf = heIf2;
                }
                if (Tunneller.\u200c\u200d\u2007) {
                    throw null;
                }
            }
            this.hEIf = this.hEIf.add(0, -1, 0);
            if (Wrapper.mc.world.getBlockState(this.hEIf).getBlock() instanceof BlockLiquid && !(Wrapper.mc.world.getBlockState(this.hEIf.add(0, -1, 0)).getBlock() instanceof BlockAir)) {
                this.hEIf = this.hEIf.add(0, -1, 0);
            }
            if (this.bypass.OnGi()) {
                final EntityPlayerSP player5 = Wrapper.mc.player;
                player5.motionX *= 0.3;
                final EntityPlayerSP player6 = Wrapper.mc.player;
                player6.motionZ *= 0.3;
            }
            if (this.hEIf != null && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
                omqH.prFd = WFux.Goyn(this.hEIf)[0];
                omqH.OjyD = WFux.Goyn(this.hEIf)[1];
            }
        }
    }
    
    @oqrr
    public void ooHs(final ILBH ilbh) {
        if (this.AUfX == 2 && this.hEIf != null) {
            final int currentItem = Wrapper.mc.player.inventory.currentItem;
            int currentItem2 = -1;
            int i = 0;
            while (i < 9) {
                final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
                if (!jjZz.cKMx(stackInSlot) && stackInSlot.getItem() instanceof ItemBlock && Block.getBlockFromItem(stackInSlot.getItem()).getDefaultState().isFullCube()) {
                    currentItem2 = i;
                    if (Tunneller.\u200c\u200d\u2007) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                    if (Tunneller.\u200c\u200d\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            if (currentItem2 == -1) {
                return;
            }
            if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem2));
                Wrapper.mc.player.inventory.currentItem = currentItem2;
                Wrapper.mc.playerController.updateController();
            }
            Wrapper.mc.playerController.processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, this.hEIf, EnumFacing.UP, new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
            OffHand.LInL();
            this.Xijd.BBwj();
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem));
            Wrapper.mc.player.inventory.currentItem = currentItem;
            Wrapper.mc.playerController.updateController();
        }
        if (this.AUfX == 0 && this.hEIf != null) {
            Wrapper.mc.playerController.onPlayerDamageBlock(new BlockPos(this.hEIf.getX(), this.hEIf.getY(), this.hEIf.getZ()), EnumFacing.DOWN);
            OffHand.LInL();
        }
        if (this.AUfX == 1 && this.hEIf != null && this.Xijd.QOXG(this.delay.OnGi() * 1000.0)) {
            final int currentItem3 = Wrapper.mc.player.inventory.currentItem;
            int currentItem4 = -1;
            int j = 0;
            while (j < 9) {
                final ItemStack stackInSlot2 = Wrapper.mc.player.inventory.getStackInSlot(j);
                if (!jjZz.cKMx(stackInSlot2) && stackInSlot2.getItem() instanceof ItemBlock && Block.getBlockFromItem(stackInSlot2.getItem()).getDefaultState().isFullCube()) {
                    currentItem4 = j;
                    if (Tunneller.\u200c\u200d\u2007) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++j;
                    if (Tunneller.\u200c\u200d\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            if (currentItem4 == -1) {
                return;
            }
            if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
                Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem4));
                Wrapper.mc.player.inventory.currentItem = currentItem4;
                Wrapper.mc.playerController.updateController();
            }
            Wrapper.mc.playerController.processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, this.hEIf, EnumFacing.UP, new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
            OffHand.LInL();
            this.Xijd.BBwj();
            Wrapper.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(currentItem3));
            Wrapper.mc.player.inventory.currentItem = currentItem3;
            Wrapper.mc.playerController.updateController();
        }
    }
    
    @oqrr
    public void efgw(final RTIo rtIo) {
        if (!this.esp.OnGi()) {
            return;
        }
        if (this.hEIf != null) {
            BIIV.TGTO(this.hEIf, Color.RED, 1.0, 1.0);
        }
    }
    
    public static boolean vKFe(final BlockPos blockPos) {
        return Wrapper.mc.world.getBlockState(blockPos).getBlock().canCollideCheck(Wrapper.mc.world.getBlockState(blockPos), false);
    }
    
    private BlockPos TWkw(final BlockPos blockPos, final Vec3i vec3i) {
        return blockPos.offset(Wrapper.mc.player.getHorizontalFacing(), vec3i.getX()).up(vec3i.getY());
    }
    
    @Override
    public void LPxx() {
        this.keWR = Wrapper.mc.gameSettings.keyBindForward.isPressed();
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
    }
    
    public static double ITtI(final double n, final double n2, final double n3) {
        final double n4 = Minecraft.getMinecraft().getRenderManager().viewerPosX - n;
        final double n5 = Minecraft.getMinecraft().getRenderManager().viewerPosY - n2;
        final double n6 = Minecraft.getMinecraft().getRenderManager().viewerPosZ - n3;
        return MathHelper.sqrt(n4 * n4 + n5 * n5 + n6 * n6);
    }
    
    private static boolean qLRg(final Entity entity) {
        return entity instanceof EntityItem;
    }
}
