package com.client.glowclient;

import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class AB
{
    private static final BlockQuartz$EnumType[][] d;
    private static final BlockLog$EnumAxis[][] L;
    private static final EnumFacing[][] A;
    public static final AB B;
    private static final EnumFacing$Axis[][] b;
    
    public AB() {
        super();
    }
    
    private static EnumFacing M(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
        return AB.A[enumFacing.ordinal()][enumFacing2.ordinal()];
    }
    
    public Id M(final J j, final EnumFacing enumFacing, final boolean b) throws Lc {
        final Vec3i m = this.M(enumFacing, j.D(), j.M(), j.A());
        final Id id = new Id(j.M(), m.getX(), m.getY(), m.getZ(), j.M());
        final wc wc = new wc();
        final int n = 0;
        final Iterator<wc> iterator2;
        Iterator<wc> iterator = iterator2 = LB.A(n, n, n, j.D() - 1, j.M() - 1, j.A() - 1).iterator();
        while (iterator.hasNext()) {
            final wc next = iterator2.next();
            id.M(this.M(next, enumFacing, m, wc), this.M(j.M((BlockPos)next), enumFacing, b));
            iterator = iterator2;
        }
        final Iterator<TileEntity> iterator4;
        Iterator<TileEntity> iterator3 = iterator4 = j.M().iterator();
        while (iterator3.hasNext()) {
            final TileEntity tileEntity;
            tileEntity.setPos(new BlockPos((Vec3i)this.M((tileEntity = iterator4.next()).getPos(), enumFacing, m, wc)));
            iterator3 = iterator4;
            id.M(tileEntity.getPos(), tileEntity);
        }
        return id;
    }
    
    private void M(final XA xa, final EnumFacing enumFacing) {
        switch (Mc.b[enumFacing.ordinal()]) {
            case 1:
            case 2: {
                while (false) {}
                final int n = (xa.getWidth() - xa.getLength()) / 2;
                final wc l = xa.L;
                l.b += n;
                final wc i = xa.L;
                i.B -= n;
            }
            case 3:
            case 4: {
                final int n2 = (xa.getWidth() - xa.getHeight()) / 2;
                final wc j = xa.L;
                j.b += n2;
                final wc k = xa.L;
                k.A -= n2;
            }
            case 5:
            case 6: {
                final int n3 = (xa.getHeight() - xa.getLength()) / 2;
                final wc m = xa.L;
                m.A += n3;
                final wc l2 = xa.L;
                l2.B -= n3;
            }
            default: {}
        }
    }
    
    private IBlockState M(final IBlockState blockState, final EnumFacing enumFacing, final boolean b) throws Lc {
        final net.minecraft.block.properties.IProperty<Comparable> m;
        if ((m = Ib.M(blockState, "facing")) instanceof PropertyDirection) {
            final Comparable value;
            if ((value = blockState.getValue((IProperty)m)) instanceof EnumFacing) {
                final EnumFacing i = M(enumFacing, (EnumFacing)value);
                if (m.getAllowedValues().contains(i)) {
                    return blockState.withProperty((IProperty)m, (Comparable)i);
                }
            }
        }
        else if (m instanceof PropertyEnum) {
            if (BlockLever$EnumOrientation.class.isAssignableFrom(m.getValueClass())) {
                final BlockLever$EnumOrientation j = M(enumFacing, (BlockLever$EnumOrientation)blockState.getValue((IProperty)m));
                if (m.getAllowedValues().contains(j)) {
                    return blockState.withProperty((IProperty)m, (Comparable)j);
                }
            }
        }
        else if (m != null) {}
        final net.minecraft.block.properties.IProperty<Comparable> k;
        if ((k = Ib.M(blockState, "axis")) instanceof PropertyEnum) {
            if (EnumFacing$Axis.class.isAssignableFrom(k.getValueClass())) {
                return blockState.withProperty((IProperty)k, (Comparable)M(enumFacing, (EnumFacing$Axis)blockState.getValue((IProperty)k)));
            }
            if (BlockLog$EnumAxis.class.isAssignableFrom(k.getValueClass())) {
                return blockState.withProperty((IProperty)k, (Comparable)M(enumFacing, (BlockLog$EnumAxis)blockState.getValue((IProperty)k)));
            }
        }
        else if (k != null) {}
        final net.minecraft.block.properties.IProperty<Comparable> l;
        if ((l = Ib.M(blockState, "variant")) instanceof PropertyEnum && BlockQuartz$EnumType.class.isAssignableFrom(l.getValueClass())) {
            return blockState.withProperty((IProperty)l, (Comparable)M(enumFacing, (BlockQuartz$EnumType)blockState.getValue((IProperty)l)));
        }
        if (!b && (m != null || k != null)) {
            throw new Lc("'%s' cannot be rotated around '%s'", new Object[] { Block.REGISTRY.getNameForObject((Object)blockState.getBlock()), enumFacing });
        }
        return blockState;
    }
    
    private static BlockQuartz$EnumType M(final EnumFacing enumFacing, final BlockQuartz$EnumType blockQuartz$EnumType) {
        return AB.d[enumFacing.getAxis().ordinal()][blockQuartz$EnumType.ordinal()];
    }
    
    private static BlockLog$EnumAxis M(final EnumFacing enumFacing, final BlockLog$EnumAxis blockLog$EnumAxis) {
        return AB.L[enumFacing.getAxis().ordinal()][blockLog$EnumAxis.ordinal()];
    }
    
    private Vec3i M(final EnumFacing enumFacing, final int n, final int n2, final int n3) throws Lc {
        switch (Mc.b[enumFacing.ordinal()]) {
            case 1:
            case 2: {
                return new Vec3i(n3, n2, n);
            }
            case 3:
            case 4: {
                return new Vec3i(n2, n, n3);
            }
            case 5:
            case 6: {
                return new Vec3i(n, n3, n2);
            }
            default: {
                throw new Lc("'%s' is not a valid axis!", new Object[] { enumFacing.getName() });
            }
        }
    }
    
    private static BlockLever$EnumOrientation M(final EnumFacing enumFacing, final BlockLever$EnumOrientation blockLever$EnumOrientation) {
        return BlockLever$EnumOrientation.forFacings(M(enumFacing, blockLever$EnumOrientation.getFacing()), (enumFacing.getAxis().isVertical() && blockLever$EnumOrientation.getFacing().getAxis().isVertical()) ? ((blockLever$EnumOrientation == BlockLever$EnumOrientation.UP_X || blockLever$EnumOrientation == BlockLever$EnumOrientation.DOWN_X) ? EnumFacing.NORTH : EnumFacing.WEST) : blockLever$EnumOrientation.getFacing());
    }
    
    private static EnumFacing$Axis M(final EnumFacing enumFacing, final EnumFacing$Axis enumFacing$Axis) {
        return AB.b[enumFacing.getAxis().ordinal()][enumFacing$Axis.ordinal()];
    }
    
    public boolean M(final XA xa, final EnumFacing enumFacing, final boolean b) {
        if (xa == null) {
            return false;
        }
        try {
            final Id m = this.M(xa.getSchematic(), enumFacing, b);
            this.M(xa, enumFacing);
            xa.setSchematic(m);
            final Iterator<TileEntity> iterator2;
            Iterator<TileEntity> iterator = iterator2 = xa.getTileEntities().iterator();
            while (iterator.hasNext()) {
                final TileEntity tileEntity = iterator2.next();
                iterator = iterator2;
                xa.initializeTileEntity(tileEntity);
            }
            return true;
        }
        catch (Lc lc) {
            ld.H.error(lc.getMessage());
        }
        catch (Exception ex) {
            ld.H.fatal("Something went wrong!", (Throwable)ex);
        }
        return false;
    }
    
    static {
        B = new AB();
        A = new EnumFacing[EnumFacing.VALUES.length][];
        b = new EnumFacing$Axis[EnumFacing$Axis.values().length][];
        L = new BlockLog$EnumAxis[EnumFacing$Axis.values().length][];
        d = new BlockQuartz$EnumType[EnumFacing$Axis.values().length][];
        AB.A[EnumFacing.DOWN.ordinal()] = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.WEST, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.NORTH };
        AB.A[EnumFacing.UP.ordinal()] = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.UP, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH };
        AB.A[EnumFacing.NORTH.ordinal()] = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.DOWN, EnumFacing.UP };
        AB.A[EnumFacing.SOUTH.ordinal()] = new EnumFacing[] { EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.DOWN };
        AB.A[EnumFacing.WEST.ordinal()] = new EnumFacing[] { EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.DOWN, EnumFacing.WEST, EnumFacing.EAST };
        AB.A[EnumFacing.EAST.ordinal()] = new EnumFacing[] { EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.DOWN, EnumFacing.UP, EnumFacing.WEST, EnumFacing.EAST };
        AB.b[EnumFacing$Axis.X.ordinal()] = new EnumFacing$Axis[] { EnumFacing$Axis.X, EnumFacing$Axis.Z, EnumFacing$Axis.Y };
        AB.b[EnumFacing$Axis.Y.ordinal()] = new EnumFacing$Axis[] { EnumFacing$Axis.Z, EnumFacing$Axis.Y, EnumFacing$Axis.X };
        AB.b[EnumFacing$Axis.Z.ordinal()] = new EnumFacing$Axis[] { EnumFacing$Axis.Y, EnumFacing$Axis.X, EnumFacing$Axis.Z };
        AB.L[EnumFacing$Axis.X.ordinal()] = new BlockLog$EnumAxis[] { BlockLog$EnumAxis.X, BlockLog$EnumAxis.Z, BlockLog$EnumAxis.Y, BlockLog$EnumAxis.NONE };
        AB.L[EnumFacing$Axis.Y.ordinal()] = new BlockLog$EnumAxis[] { BlockLog$EnumAxis.Z, BlockLog$EnumAxis.Y, BlockLog$EnumAxis.X, BlockLog$EnumAxis.NONE };
        AB.L[EnumFacing$Axis.Z.ordinal()] = new BlockLog$EnumAxis[] { BlockLog$EnumAxis.Y, BlockLog$EnumAxis.X, BlockLog$EnumAxis.Z, BlockLog$EnumAxis.NONE };
        AB.d[EnumFacing$Axis.X.ordinal()] = new BlockQuartz$EnumType[] { BlockQuartz$EnumType.DEFAULT, BlockQuartz$EnumType.CHISELED, BlockQuartz$EnumType.LINES_Z, BlockQuartz$EnumType.LINES_X, BlockQuartz$EnumType.LINES_Y };
        AB.d[EnumFacing$Axis.Y.ordinal()] = new BlockQuartz$EnumType[] { BlockQuartz$EnumType.DEFAULT, BlockQuartz$EnumType.CHISELED, BlockQuartz$EnumType.LINES_Y, BlockQuartz$EnumType.LINES_Z, BlockQuartz$EnumType.LINES_X };
        AB.d[EnumFacing$Axis.Z.ordinal()] = new BlockQuartz$EnumType[] { BlockQuartz$EnumType.DEFAULT, BlockQuartz$EnumType.CHISELED, BlockQuartz$EnumType.LINES_X, BlockQuartz$EnumType.LINES_Y, BlockQuartz$EnumType.LINES_Z };
    }
    
    private BlockPos M(final BlockPos blockPos, final EnumFacing enumFacing, final Vec3i vec3i, final wc wc) throws Lc {
        switch (Mc.b[enumFacing.ordinal()]) {
            case 1: {
                while (false) {}
                return wc.set(blockPos.getZ(), blockPos.getY(), vec3i.getZ() - 1 - blockPos.getX());
            }
            case 2: {
                return wc.set(vec3i.getX() - 1 - blockPos.getZ(), blockPos.getY(), blockPos.getX());
            }
            case 3: {
                return wc.set(vec3i.getX() - 1 - blockPos.getY(), blockPos.getX(), blockPos.getZ());
            }
            case 4: {
                return wc.set(blockPos.getY(), vec3i.getY() - 1 - blockPos.getX(), blockPos.getZ());
            }
            case 5: {
                return wc.set(blockPos.getX(), vec3i.getY() - 1 - blockPos.getZ(), blockPos.getY());
            }
            case 6: {
                return wc.set(blockPos.getX(), blockPos.getZ(), vec3i.getZ() - 1 - blockPos.getY());
            }
            default: {
                throw new Lc("'%s' is not a valid axis!", new Object[] { enumFacing.getName() });
            }
        }
    }
}
