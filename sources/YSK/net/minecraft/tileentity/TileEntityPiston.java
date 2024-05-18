package net.minecraft.tileentity;

import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import com.google.common.collect.*;

public class TileEntityPiston extends TileEntity implements ITickable
{
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis;
    private float progress;
    private IBlockState pistonState;
    private static final String[] I;
    private boolean extending;
    private float lastProgress;
    private EnumFacing pistonFacing;
    private boolean shouldHeadBeRendered;
    private List<Entity> field_174933_k;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private void launchWithSlimeBlock(float n, final float n2) {
        if (this.extending) {
            n = 1.0f - n;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            --n;
        }
        final AxisAlignedBB boundingBox = Blocks.piston_extension.getBoundingBox(this.worldObj, this.pos, this.pistonState, n, this.pistonFacing);
        if (boundingBox != null) {
            final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, boundingBox);
            if (!entitiesWithinAABBExcludingEntity.isEmpty()) {
                this.field_174933_k.addAll(entitiesWithinAABBExcludingEntity);
                final Iterator<Entity> iterator = this.field_174933_k.iterator();
                "".length();
                if (-1 == 0) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final Entity entity = iterator.next();
                    if (this.pistonState.getBlock() == Blocks.slime_block && this.extending) {
                        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis()[this.pistonFacing.getAxis().ordinal()]) {
                            case 1: {
                                entity.motionX = this.pistonFacing.getFrontOffsetX();
                                "".length();
                                if (-1 != -1) {
                                    throw null;
                                }
                                continue;
                            }
                            case 2: {
                                entity.motionY = this.pistonFacing.getFrontOffsetY();
                                "".length();
                                if (4 == 3) {
                                    throw null;
                                }
                                continue;
                            }
                            case 3: {
                                entity.motionZ = this.pistonFacing.getFrontOffsetZ();
                                break;
                            }
                        }
                        "".length();
                        if (2 >= 3) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        entity.moveEntity(n2 * this.pistonFacing.getFrontOffsetX(), n2 * this.pistonFacing.getFrontOffsetY(), n2 * this.pistonFacing.getFrontOffsetZ());
                    }
                }
                this.field_174933_k.clear();
            }
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing$Axis = TileEntityPiston.$SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis;
        if ($switch_TABLE$net$minecraft$util$EnumFacing$Axis != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing$Axis;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing$Axis2 = new int[EnumFacing.Axis.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.X.ordinal()] = " ".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.Y.ordinal()] = "  ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.Z.ordinal()] = "   ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return TileEntityPiston.$SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis = $switch_TABLE$net$minecraft$util$EnumFacing$Axis2;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(TileEntityPiston.I[0xA1 ^ 0xA4], Block.getIdFromBlock(this.pistonState.getBlock()));
        nbtTagCompound.setInteger(TileEntityPiston.I[0x5E ^ 0x58], this.pistonState.getBlock().getMetaFromState(this.pistonState));
        nbtTagCompound.setInteger(TileEntityPiston.I[0x32 ^ 0x35], this.pistonFacing.getIndex());
        nbtTagCompound.setFloat(TileEntityPiston.I[0x5C ^ 0x54], this.lastProgress);
        nbtTagCompound.setBoolean(TileEntityPiston.I[0x96 ^ 0x9F], this.extending);
    }
    
    public float getOffsetZ(final float n) {
        float n2;
        if (this.extending) {
            n2 = (this.getProgress(n) - 1.0f) * this.pistonFacing.getFrontOffsetZ();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n2 = (1.0f - this.getProgress(n)) * this.pistonFacing.getFrontOffsetZ();
        }
        return n2;
    }
    
    public float getProgress(float n) {
        if (n > 1.0f) {
            n = 1.0f;
        }
        return this.lastProgress + (this.progress - this.lastProgress) * n;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.pistonState = Block.getBlockById(nbtTagCompound.getInteger(TileEntityPiston.I["".length()])).getStateFromMeta(nbtTagCompound.getInteger(TileEntityPiston.I[" ".length()]));
        this.pistonFacing = EnumFacing.getFront(nbtTagCompound.getInteger(TileEntityPiston.I["  ".length()]));
        final float float1 = nbtTagCompound.getFloat(TileEntityPiston.I["   ".length()]);
        this.progress = float1;
        this.lastProgress = float1;
        this.extending = nbtTagCompound.getBoolean(TileEntityPiston.I[0x3 ^ 0x7]);
    }
    
    public TileEntityPiston() {
        this.field_174933_k = (List<Entity>)Lists.newArrayList();
    }
    
    public void clearPistonTileEntity() {
        if (this.lastProgress < 1.0f && this.worldObj != null) {
            final float n = 1.0f;
            this.progress = n;
            this.lastProgress = n;
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();
            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
                this.worldObj.setBlockState(this.pos, this.pistonState, "   ".length());
                this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
            }
        }
    }
    
    public EnumFacing getFacing() {
        return this.pistonFacing;
    }
    
    public float getOffsetX(final float n) {
        float n2;
        if (this.extending) {
            n2 = (this.getProgress(n) - 1.0f) * this.pistonFacing.getFrontOffsetX();
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            n2 = (1.0f - this.getProgress(n)) * this.pistonFacing.getFrontOffsetX();
        }
        return n2;
    }
    
    @Override
    public int getBlockMetadata() {
        return "".length();
    }
    
    public boolean isExtending() {
        return this.extending;
    }
    
    public float getOffsetY(final float n) {
        float n2;
        if (this.extending) {
            n2 = (this.getProgress(n) - 1.0f) * this.pistonFacing.getFrontOffsetY();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n2 = (1.0f - this.getProgress(n)) * this.pistonFacing.getFrontOffsetY();
        }
        return n2;
    }
    
    public boolean shouldPistonHeadBeRendered() {
        return this.shouldHeadBeRendered;
    }
    
    public IBlockState getPistonState() {
        return this.pistonState;
    }
    
    @Override
    public void update() {
        this.lastProgress = this.progress;
        if (this.lastProgress >= 1.0f) {
            this.launchWithSlimeBlock(1.0f, 0.25f);
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();
            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
                this.worldObj.setBlockState(this.pos, this.pistonState, "   ".length());
                this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
        }
        else {
            this.progress += 0.5f;
            if (this.progress >= 1.0f) {
                this.progress = 1.0f;
            }
            if (this.extending) {
                this.launchWithSlimeBlock(this.progress, this.progress - this.lastProgress + 0.0625f);
            }
        }
    }
    
    private static void I() {
        (I = new String[0xBA ^ 0xB0])["".length()] = I(":\r\u0018\u0000\u000e\u0011\u0005", "Xawce");
        TileEntityPiston.I[" ".length()] = I("85\u001f\u0016\u0013\u001e8\u0004\u0014", "ZYpux");
        TileEntityPiston.I["  ".length()] = I("\u000e)(\u0001\u0007\u000f", "hHKhi");
        TileEntityPiston.I["   ".length()] = I("\u00056\u001d\r>\u00107\u0001", "uDrjL");
        TileEntityPiston.I[0x4B ^ 0x4F] = I("\b?5\u001f?\t./\u001d", "mGAzQ");
        TileEntityPiston.I[0x9E ^ 0x9B] = I("\u0004<)\u0014\"/4", "fPFwI");
        TileEntityPiston.I[0x9A ^ 0x9C] = I("&\u0002\"\u001b)\u0000\u000f9\u0019", "DnMxB");
        TileEntityPiston.I[0x83 ^ 0x84] = I("\u0011\u0006\u0005\u000f\u0005\u0010", "wgffk");
        TileEntityPiston.I[0x82 ^ 0x8A] = I("='\u0018\u0003<(&\u0004", "MUwdN");
        TileEntityPiston.I[0x3F ^ 0x36] = I("(;\f&!)*\u0016$", "MCxCO");
    }
    
    public TileEntityPiston(final IBlockState pistonState, final EnumFacing pistonFacing, final boolean extending, final boolean shouldHeadBeRendered) {
        this.field_174933_k = (List<Entity>)Lists.newArrayList();
        this.pistonState = pistonState;
        this.pistonFacing = pistonFacing;
        this.extending = extending;
        this.shouldHeadBeRendered = shouldHeadBeRendered;
    }
}
