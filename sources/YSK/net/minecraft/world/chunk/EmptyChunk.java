package net.minecraft.world.chunk;

import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

public class EmptyChunk extends Chunk
{
    @Override
    public int getLightSubtracted(final BlockPos blockPos, final int n) {
        return "".length();
    }
    
    @Override
    public boolean canSeeSky(final BlockPos blockPos) {
        return "".length() != 0;
    }
    
    @Override
    public void onChunkUnload() {
    }
    
    @Override
    public int getHeightValue(final int n, final int n2) {
        return "".length();
    }
    
    @Override
    public int getBlockMetadata(final BlockPos blockPos) {
        return "".length();
    }
    
    @Override
    public int getLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        return enumSkyBlock.defaultLightValue;
    }
    
    @Override
    public void removeEntity(final Entity entity) {
    }
    
    @Override
    public void setChunkModified() {
    }
    
    @Override
    public Random getRandomWithSeed(final long n) {
        return new Random(this.getWorld().getSeed() + this.xPosition * this.xPosition * (4221268 + 1023942 - 3460170 + 3202102) + this.xPosition * (1299184 + 5241252 - 5317316 + 4724491) + this.zPosition * this.zPosition * 4392871L + this.zPosition * (51516 + 302284 - 326052 + 361963) ^ n);
    }
    
    @Override
    public void addEntity(final Entity entity) {
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos, final EnumCreateEntityType enumCreateEntityType) {
        return null;
    }
    
    @Override
    public void generateSkylightMap() {
    }
    
    @Override
    public void removeEntityAtIndex(final Entity entity, final int n) {
    }
    
    @Override
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
    }
    
    @Override
    public boolean needsSaving(final boolean b) {
        return "".length() != 0;
    }
    
    public void generateHeightMap() {
    }
    
    @Override
    public <T extends Entity> void getEntitiesOfTypeWithinAAAB(final Class<? extends T> clazz, final AxisAlignedBB axisAlignedBB, final List<T> list, final Predicate<? super T> predicate) {
    }
    
    @Override
    public int getBlockLightOpacity(final BlockPos blockPos) {
        return 212 + 174 - 154 + 23;
    }
    
    @Override
    public boolean isEmpty() {
        return " ".length() != 0;
    }
    
    @Override
    public boolean isAtLocation(final int n, final int n2) {
        if (n == this.xPosition && n2 == this.zPosition) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean getAreLevelsEmpty(final int n, final int n2) {
        return " ".length() != 0;
    }
    
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void getEntitiesWithinAABBForEntity(final Entity entity, final AxisAlignedBB axisAlignedBB, final List<Entity> list, final Predicate<? super Entity> predicate) {
    }
    
    public EmptyChunk(final World world, final int n, final int n2) {
        super(world, n, n2);
    }
    
    @Override
    public Block getBlock(final BlockPos blockPos) {
        return Blocks.air;
    }
    
    @Override
    public void addTileEntity(final TileEntity tileEntity) {
    }
    
    @Override
    public void removeTileEntity(final BlockPos blockPos) {
    }
    
    @Override
    public void onChunkLoad() {
    }
    
    @Override
    public void addTileEntity(final BlockPos blockPos, final TileEntity tileEntity) {
    }
}
