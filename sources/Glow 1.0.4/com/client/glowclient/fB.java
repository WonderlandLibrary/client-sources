package com.client.glowclient;

import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class FB
{
    private final Map<Item, PB> L;
    public static final FB A;
    private final Map<Block, PB> B;
    private final Map<Class<? extends Block>, PB> b;
    
    private static boolean d(final IBlockState blockState, final EntityPlayer entityPlayer, final BlockPos blockPos, final World world) {
        return (int)blockState.getValue((IProperty)BlockStandingSign.ROTATION) == (MathHelper.floor((entityPlayer.rotationYaw + 180.0) * 16.0 / 360.0 + 0.5) & 0xF);
    }
    
    private static boolean B(final IBlockState blockState, final EntityPlayer entityPlayer, final BlockPos blockPos, final World world) {
        return Ib.M(blockState, "facing") == EnumFacing.getDirectionFromEntityLiving(blockPos, (EntityLivingBase)entityPlayer).getOpposite();
    }
    
    public FB() {
        super();
        this.b = new LinkedHashMap<Class<? extends Block>, PB>();
        this.B = new HashMap<Block, PB>();
        this.L = new HashMap<Item, PB>();
    }
    
    private void M() {
        this.b.clear();
        this.B.clear();
        this.L.clear();
        final S s = FB::E;
        final S s2 = FB::M;
        final S s3 = FB::k;
        final S s4 = FB::B;
        final S s5 = FB::D;
        final S s6 = FB::e;
        final S s7 = FB::d;
        final S s8 = FB::A;
        final q q = FB::A;
        final q q2 = FB::M;
        final q q3 = FB::D;
        final W w = FB::k;
        final W w2 = FB::D;
        final W w3 = FB::M;
        final W w4 = FB::E;
        final W w5 = FB::A;
        final W w6 = FB::B;
        final W w7 = FB::e;
        final w w8 = FB::M;
        this.M((Class<? extends Block>)BlockLog.class, new PB(w));
        this.M((Class<? extends Block>)BlockButton.class, new PB(w3));
        this.M((Class<? extends Block>)BlockChest.class, new PB(s2));
        this.M((Class<? extends Block>)BlockDispenser.class, new PB(s3));
        this.M((Class<? extends Block>)BlockDoor.class, new PB(s));
        this.M((Class<? extends Block>)BlockEnderChest.class, new PB(s2));
        this.M((Class<? extends Block>)BlockEndRod.class, new PB(w3));
        this.M((Class<? extends Block>)BlockFenceGate.class, new PB(s));
        this.M((Class<? extends Block>)BlockFurnace.class, new PB(s2));
        this.M((Class<? extends Block>)BlockHopper.class, new PB(w5));
        this.M((Class<? extends Block>)BlockObserver.class, new PB(s4));
        this.M((Class<? extends Block>)BlockPistonBase.class, new PB(s3));
        this.M((Class<? extends Block>)BlockPumpkin.class, new PB(s2));
        this.M((Class<? extends Block>)BlockRotatedPillar.class, new PB(w2));
        this.M((Class<? extends Block>)BlockSlab.class, new PB().D(q).M(w8));
        this.M((Class<? extends Block>)BlockStairs.class, new PB(s).D(q2));
        this.M((Class<? extends Block>)BlockTorch.class, new PB(w3));
        this.M((Class<? extends Block>)BlockTrapDoor.class, new PB(w3).D(q3));
        this.M(Blocks.ANVIL, new PB(s5));
        this.M(Blocks.CHAIN_COMMAND_BLOCK, new PB(s2));
        this.M(Blocks.COCOA, new PB(w4));
        this.M(Blocks.END_PORTAL_FRAME, new PB(s2));
        this.M(Blocks.LADDER, new PB(w3));
        this.M(Blocks.LEVER, new PB(s6, w6));
        this.M(Blocks.QUARTZ_BLOCK, new PB(w7));
        this.M(Blocks.REPEATING_COMMAND_BLOCK, new PB(s2));
        this.M(Blocks.STANDING_SIGN, new PB(s7));
        this.M((Block)Blocks.TRIPWIRE_HOOK, new PB(w3));
        this.M(Blocks.WALL_SIGN, new PB(w3));
        this.M(Items.COMPARATOR, new PB(s2));
        this.M(Items.REPEATER, new PB(s2));
        this.M(Blocks.BED, new PB(s8));
        this.M(Blocks.END_PORTAL, new PB(s8));
        this.M((Block)Blocks.PISTON_EXTENSION, new PB(s8));
        this.M((Block)Blocks.PISTON_HEAD, new PB(s8));
        this.M((Block)Blocks.PORTAL, new PB(s8));
        this.M((Block)Blocks.SKULL, new PB(s8));
        this.M(Blocks.STANDING_BANNER, new PB(s8));
        this.M(Blocks.WALL_BANNER, new PB(s8));
    }
    
    private static boolean E(final IBlockState blockState, final EntityPlayer entityPlayer, final BlockPos blockPos, final World world) {
        return Ib.M(blockState, "facing") == entityPlayer.getHorizontalFacing();
    }
    
    private static boolean e(final IBlockState blockState, final EntityPlayer entityPlayer, final BlockPos blockPos, final World world) {
        final BlockLever$EnumOrientation blockLever$EnumOrientation;
        return !(blockLever$EnumOrientation = (BlockLever$EnumOrientation)blockState.getValue((IProperty)BlockLever.FACING)).getFacing().getAxis().isVertical() || BlockLever$EnumOrientation.forFacings(blockLever$EnumOrientation.getFacing(), entityPlayer.getHorizontalFacing()) == blockLever$EnumOrientation;
    }
    
    private PB M(final Class<? extends Block> clazz, final PB pb) {
        if (clazz == null || pb == null) {
            return null;
        }
        return this.b.put(clazz, pb);
    }
    
    private static float A(final IBlockState blockState) {
        if (((BlockSlab)blockState.getBlock()).isDouble()) {
            return 0.0f;
        }
        if ((BlockSlab$EnumBlockHalf)blockState.getValue((IProperty)BlockSlab.HALF) == BlockSlab$EnumBlockHalf.TOP) {
            return 1.0f;
        }
        return 0.0f;
    }
    
    private static List B(final List list, final IBlockState blockState) {
        final ArrayList<EnumFacing> list2 = new ArrayList<EnumFacing>();
        final BlockLever$EnumOrientation blockLever$EnumOrientation = (BlockLever$EnumOrientation)blockState.getValue((IProperty)BlockLever.FACING);
        final Iterator<EnumFacing> iterator2;
        Iterator<EnumFacing> iterator = iterator2 = list.iterator();
        while (iterator.hasNext()) {
            final EnumFacing enumFacing = iterator2.next();
            if (blockLever$EnumOrientation.getFacing().getOpposite() != enumFacing) {
                iterator = iterator2;
            }
            else {
                list2.add(enumFacing);
                iterator = iterator2;
            }
        }
        return list2;
    }
    
    private static boolean k(final IBlockState blockState, final EntityPlayer entityPlayer, final BlockPos blockPos, final World world) {
        return Ib.M(blockState, "facing") == EnumFacing.getDirectionFromEntityLiving(blockPos, (EntityLivingBase)entityPlayer);
    }
    
    private static float D(final IBlockState blockState) {
        if ((BlockTrapDoor$DoorHalf)blockState.getValue((IProperty)BlockTrapDoor.HALF) == BlockTrapDoor$DoorHalf.TOP) {
            return 1.0f;
        }
        return 0.0f;
    }
    
    private PB M(final Block block, final PB pb) {
        if (block == null || pb == null) {
            return null;
        }
        return this.B.put(block, pb);
    }
    
    private static List E(final List list, final IBlockState blockState) {
        final ArrayList<EnumFacing> list2 = new ArrayList<EnumFacing>();
        final net.minecraft.block.properties.IProperty<Comparable> m;
        if ((m = Ib.M(blockState, "facing")) != null && m.getValueClass().equals(EnumFacing.class)) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue((IProperty)m);
            final Iterator<EnumFacing> iterator2;
            Iterator<EnumFacing> iterator = iterator2 = list.iterator();
            while (iterator.hasNext()) {
                final EnumFacing enumFacing2 = iterator2.next();
                if (enumFacing != enumFacing2) {
                    iterator = iterator2;
                }
                else {
                    list2.add(enumFacing2);
                    iterator = iterator2;
                }
            }
        }
        return list2;
    }
    
    private static boolean A(final IBlockState blockState, final EntityPlayer entityPlayer, final BlockPos blockPos, final World world) {
        return false;
    }
    
    private static List e(final List list, final IBlockState blockState) {
        final ArrayList<EnumFacing> list2 = new ArrayList<EnumFacing>();
        final BlockQuartz$EnumType blockQuartz$EnumType = (BlockQuartz$EnumType)blockState.getValue((IProperty)BlockQuartz.VARIANT);
        final Iterator<EnumFacing> iterator = list.iterator();
    Label_0028:
        while (true) {
            Iterator<EnumFacing> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final EnumFacing enumFacing = iterator.next();
                if ((blockQuartz$EnumType == BlockQuartz$EnumType.LINES_X && enumFacing.getAxis() != EnumFacing$Axis.X) || (blockQuartz$EnumType == BlockQuartz$EnumType.LINES_Y && enumFacing.getAxis() != EnumFacing$Axis.Y)) {
                    continue Label_0028;
                }
                if (blockQuartz$EnumType == BlockQuartz$EnumType.LINES_Z && enumFacing.getAxis() != EnumFacing$Axis.Z) {
                    iterator2 = iterator;
                }
                else {
                    list2.add(enumFacing);
                    iterator2 = iterator;
                }
            }
            break;
        }
        return list2;
    }
    
    private PB M(final Item item, final PB pb) {
        if (item == null || pb == null) {
            return null;
        }
        return this.L.put(item, pb);
    }
    
    private static int M(final IBlockState blockState) {
        if (((BlockSlab)blockState.getBlock()).isDouble()) {
            return 1;
        }
        return 0;
    }
    
    private static float M(final IBlockState blockState) {
        if ((BlockStairs$EnumHalf)blockState.getValue((IProperty)BlockStairs.HALF) == BlockStairs$EnumHalf.TOP) {
            return 1.0f;
        }
        return 0.0f;
    }
    
    static {
        (A = new FB()).M();
    }
    
    private static boolean D(final IBlockState blockState, final EntityPlayer entityPlayer, final BlockPos blockPos, final World world) {
        return Ib.M(blockState, "facing") == entityPlayer.getHorizontalFacing().rotateY();
    }
    
    public PB M(final IBlockState blockState, final ItemStack itemStack) {
        final PB pb;
        if ((pb = this.L.get(itemStack.getItem())) != null) {
            return pb;
        }
        final Block block = blockState.getBlock();
        final PB pb2;
        if ((pb2 = this.B.get(block)) != null) {
            return pb2;
        }
        final Iterator<Class<? extends Block>> iterator2;
        Iterator<Class<? extends Block>> iterator = iterator2 = this.b.keySet().iterator();
        while (iterator.hasNext()) {
            final Class<? extends Block> clazz;
            if ((clazz = iterator2.next()).isInstance(block)) {
                return this.b.get(clazz);
            }
            iterator = iterator2;
        }
        return null;
    }
    
    private static List k(final List list, final IBlockState blockState) {
        final ArrayList<EnumFacing> list2 = new ArrayList<EnumFacing>();
        final BlockLog$EnumAxis blockLog$EnumAxis = (BlockLog$EnumAxis)blockState.getValue((IProperty)BlockLog.LOG_AXIS);
        final Iterator<EnumFacing> iterator2;
        Iterator<EnumFacing> iterator = iterator2 = list.iterator();
        while (iterator.hasNext()) {
            final EnumFacing enumFacing = iterator2.next();
            if (blockLog$EnumAxis != BlockLog$EnumAxis.fromFacingAxis(enumFacing.getAxis())) {
                iterator = iterator2;
            }
            else {
                list2.add(enumFacing);
                iterator = iterator2;
            }
        }
        return list2;
    }
    
    private static List A(final List list, final IBlockState blockState) {
        final ArrayList<EnumFacing> list2 = new ArrayList<EnumFacing>();
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue((IProperty)BlockHopper.FACING);
        final Iterator<EnumFacing> iterator2;
        Iterator<EnumFacing> iterator = iterator2 = list.iterator();
        while (iterator.hasNext()) {
            final EnumFacing enumFacing2 = iterator2.next();
            if (enumFacing != enumFacing2) {
                iterator = iterator2;
            }
            else {
                list2.add(enumFacing2);
                iterator = iterator2;
            }
        }
        return list2;
    }
    
    private static boolean M(final IBlockState blockState, final EntityPlayer entityPlayer, final BlockPos blockPos, final World world) {
        return Ib.M(blockState, "facing") == entityPlayer.getHorizontalFacing().getOpposite();
    }
    
    private static List D(final List list, final IBlockState blockState) {
        final ArrayList<EnumFacing> list2 = new ArrayList<EnumFacing>();
        final EnumFacing$Axis enumFacing$Axis = (EnumFacing$Axis)blockState.getValue((IProperty)BlockRotatedPillar.AXIS);
        final Iterator<EnumFacing> iterator2;
        Iterator<EnumFacing> iterator = iterator2 = list.iterator();
        while (iterator.hasNext()) {
            final EnumFacing enumFacing = iterator2.next();
            if (enumFacing$Axis != enumFacing.getAxis()) {
                iterator = iterator2;
            }
            else {
                list2.add(enumFacing);
                iterator = iterator2;
            }
        }
        return list2;
    }
    
    private static List M(final List list, final IBlockState blockState) {
        final ArrayList<EnumFacing> list2 = new ArrayList<EnumFacing>();
        final net.minecraft.block.properties.IProperty<Comparable> m;
        if ((m = Ib.M(blockState, "facing")) != null && m.getValueClass().equals(EnumFacing.class)) {
            final EnumFacing enumFacing = (EnumFacing)blockState.getValue((IProperty)m);
            final Iterator<EnumFacing> iterator2;
            Iterator<EnumFacing> iterator = iterator2 = list.iterator();
            while (iterator.hasNext()) {
                final EnumFacing enumFacing2 = iterator2.next();
                if (enumFacing.getOpposite() != enumFacing2) {
                    iterator = iterator2;
                }
                else {
                    list2.add(enumFacing2);
                    iterator = iterator2;
                }
            }
        }
        return list2;
    }
}
