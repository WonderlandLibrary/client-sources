package net.minecraft.src;

import java.util.*;

public class BlockBaseRailLogic
{
    private World logicWorld;
    private int railX;
    private int railY;
    private int railZ;
    private final boolean isStraightRail;
    private List railChunkPosition;
    final BlockRailBase theRail;
    
    public BlockBaseRailLogic(final BlockRailBase par1, final World par2, final int par3, final int par4, final int par5) {
        this.theRail = par1;
        this.railChunkPosition = new ArrayList();
        this.logicWorld = par2;
        this.railX = par3;
        this.railY = par4;
        this.railZ = par5;
        final int var6 = par2.getBlockId(par3, par4, par5);
        int var7 = par2.getBlockMetadata(par3, par4, par5);
        if (((BlockRailBase)Block.blocksList[var6]).isPowered) {
            this.isStraightRail = true;
            var7 &= 0xFFFFFFF7;
        }
        else {
            this.isStraightRail = false;
        }
        this.setBasicRail(var7);
    }
    
    private void setBasicRail(final int par1) {
        this.railChunkPosition.clear();
        if (par1 == 0) {
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY, this.railZ - 1));
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY, this.railZ + 1));
        }
        else if (par1 == 1) {
            this.railChunkPosition.add(new ChunkPosition(this.railX - 1, this.railY, this.railZ));
            this.railChunkPosition.add(new ChunkPosition(this.railX + 1, this.railY, this.railZ));
        }
        else if (par1 == 2) {
            this.railChunkPosition.add(new ChunkPosition(this.railX - 1, this.railY, this.railZ));
            this.railChunkPosition.add(new ChunkPosition(this.railX + 1, this.railY + 1, this.railZ));
        }
        else if (par1 == 3) {
            this.railChunkPosition.add(new ChunkPosition(this.railX - 1, this.railY + 1, this.railZ));
            this.railChunkPosition.add(new ChunkPosition(this.railX + 1, this.railY, this.railZ));
        }
        else if (par1 == 4) {
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY + 1, this.railZ - 1));
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY, this.railZ + 1));
        }
        else if (par1 == 5) {
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY, this.railZ - 1));
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY + 1, this.railZ + 1));
        }
        else if (par1 == 6) {
            this.railChunkPosition.add(new ChunkPosition(this.railX + 1, this.railY, this.railZ));
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY, this.railZ + 1));
        }
        else if (par1 == 7) {
            this.railChunkPosition.add(new ChunkPosition(this.railX - 1, this.railY, this.railZ));
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY, this.railZ + 1));
        }
        else if (par1 == 8) {
            this.railChunkPosition.add(new ChunkPosition(this.railX - 1, this.railY, this.railZ));
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY, this.railZ - 1));
        }
        else if (par1 == 9) {
            this.railChunkPosition.add(new ChunkPosition(this.railX + 1, this.railY, this.railZ));
            this.railChunkPosition.add(new ChunkPosition(this.railX, this.railY, this.railZ - 1));
        }
    }
    
    private void refreshConnectedTracks() {
        for (int var1 = 0; var1 < this.railChunkPosition.size(); ++var1) {
            final BlockBaseRailLogic var2 = this.getRailLogic(this.railChunkPosition.get(var1));
            if (var2 != null && var2.isRailChunkPositionCorrect(this)) {
                this.railChunkPosition.set(var1, new ChunkPosition(var2.railX, var2.railY, var2.railZ));
            }
            else {
                this.railChunkPosition.remove(var1--);
            }
        }
    }
    
    private boolean isMinecartTrack(final int par1, final int par2, final int par3) {
        return BlockRailBase.isRailBlockAt(this.logicWorld, par1, par2, par3) || BlockRailBase.isRailBlockAt(this.logicWorld, par1, par2 + 1, par3) || BlockRailBase.isRailBlockAt(this.logicWorld, par1, par2 - 1, par3);
    }
    
    private BlockBaseRailLogic getRailLogic(final ChunkPosition par1ChunkPosition) {
        return BlockRailBase.isRailBlockAt(this.logicWorld, par1ChunkPosition.x, par1ChunkPosition.y, par1ChunkPosition.z) ? new BlockBaseRailLogic(this.theRail, this.logicWorld, par1ChunkPosition.x, par1ChunkPosition.y, par1ChunkPosition.z) : (BlockRailBase.isRailBlockAt(this.logicWorld, par1ChunkPosition.x, par1ChunkPosition.y + 1, par1ChunkPosition.z) ? new BlockBaseRailLogic(this.theRail, this.logicWorld, par1ChunkPosition.x, par1ChunkPosition.y + 1, par1ChunkPosition.z) : (BlockRailBase.isRailBlockAt(this.logicWorld, par1ChunkPosition.x, par1ChunkPosition.y - 1, par1ChunkPosition.z) ? new BlockBaseRailLogic(this.theRail, this.logicWorld, par1ChunkPosition.x, par1ChunkPosition.y - 1, par1ChunkPosition.z) : null));
    }
    
    private boolean isRailChunkPositionCorrect(final BlockBaseRailLogic par1BlockBaseRailLogic) {
        for (int var2 = 0; var2 < this.railChunkPosition.size(); ++var2) {
            final ChunkPosition var3 = this.railChunkPosition.get(var2);
            if (var3.x == par1BlockBaseRailLogic.railX && var3.z == par1BlockBaseRailLogic.railZ) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isPartOfTrack(final int par1, final int par2, final int par3) {
        for (int var4 = 0; var4 < this.railChunkPosition.size(); ++var4) {
            final ChunkPosition var5 = this.railChunkPosition.get(var4);
            if (var5.x == par1 && var5.z == par3) {
                return true;
            }
        }
        return false;
    }
    
    protected int getNumberOfAdjacentTracks() {
        int var1 = 0;
        if (this.isMinecartTrack(this.railX, this.railY, this.railZ - 1)) {
            ++var1;
        }
        if (this.isMinecartTrack(this.railX, this.railY, this.railZ + 1)) {
            ++var1;
        }
        if (this.isMinecartTrack(this.railX - 1, this.railY, this.railZ)) {
            ++var1;
        }
        if (this.isMinecartTrack(this.railX + 1, this.railY, this.railZ)) {
            ++var1;
        }
        return var1;
    }
    
    private boolean canConnectTo(final BlockBaseRailLogic par1BlockBaseRailLogic) {
        return this.isRailChunkPositionCorrect(par1BlockBaseRailLogic) || (this.railChunkPosition.size() != 2 && (!this.railChunkPosition.isEmpty() || true));
    }
    
    private void connectToNeighbor(final BlockBaseRailLogic par1BlockBaseRailLogic) {
        this.railChunkPosition.add(new ChunkPosition(par1BlockBaseRailLogic.railX, par1BlockBaseRailLogic.railY, par1BlockBaseRailLogic.railZ));
        final boolean var2 = this.isPartOfTrack(this.railX, this.railY, this.railZ - 1);
        final boolean var3 = this.isPartOfTrack(this.railX, this.railY, this.railZ + 1);
        final boolean var4 = this.isPartOfTrack(this.railX - 1, this.railY, this.railZ);
        final boolean var5 = this.isPartOfTrack(this.railX + 1, this.railY, this.railZ);
        byte var6 = -1;
        if (var2 || var3) {
            var6 = 0;
        }
        if (var4 || var5) {
            var6 = 1;
        }
        if (!this.isStraightRail) {
            if (var3 && var5 && !var2 && !var4) {
                var6 = 6;
            }
            if (var3 && var4 && !var2 && !var5) {
                var6 = 7;
            }
            if (var2 && var4 && !var3 && !var5) {
                var6 = 8;
            }
            if (var2 && var5 && !var3 && !var4) {
                var6 = 9;
            }
        }
        if (var6 == 0) {
            if (BlockRailBase.isRailBlockAt(this.logicWorld, this.railX, this.railY + 1, this.railZ - 1)) {
                var6 = 4;
            }
            if (BlockRailBase.isRailBlockAt(this.logicWorld, this.railX, this.railY + 1, this.railZ + 1)) {
                var6 = 5;
            }
        }
        if (var6 == 1) {
            if (BlockRailBase.isRailBlockAt(this.logicWorld, this.railX + 1, this.railY + 1, this.railZ)) {
                var6 = 2;
            }
            if (BlockRailBase.isRailBlockAt(this.logicWorld, this.railX - 1, this.railY + 1, this.railZ)) {
                var6 = 3;
            }
        }
        if (var6 < 0) {
            var6 = 0;
        }
        int var7 = var6;
        if (this.isStraightRail) {
            var7 = ((this.logicWorld.getBlockMetadata(this.railX, this.railY, this.railZ) & 0x8) | var6);
        }
        this.logicWorld.setBlockMetadataWithNotify(this.railX, this.railY, this.railZ, var7, 3);
    }
    
    private boolean canConnectFrom(final int par1, final int par2, final int par3) {
        final BlockBaseRailLogic var4 = this.getRailLogic(new ChunkPosition(par1, par2, par3));
        if (var4 == null) {
            return false;
        }
        var4.refreshConnectedTracks();
        return var4.canConnectTo(this);
    }
    
    public void func_94511_a(final boolean par1, final boolean par2) {
        final boolean var3 = this.canConnectFrom(this.railX, this.railY, this.railZ - 1);
        final boolean var4 = this.canConnectFrom(this.railX, this.railY, this.railZ + 1);
        final boolean var5 = this.canConnectFrom(this.railX - 1, this.railY, this.railZ);
        final boolean var6 = this.canConnectFrom(this.railX + 1, this.railY, this.railZ);
        byte var7 = -1;
        if ((var3 || var4) && !var5 && !var6) {
            var7 = 0;
        }
        if ((var5 || var6) && !var3 && !var4) {
            var7 = 1;
        }
        if (!this.isStraightRail) {
            if (var4 && var6 && !var3 && !var5) {
                var7 = 6;
            }
            if (var4 && var5 && !var3 && !var6) {
                var7 = 7;
            }
            if (var3 && var5 && !var4 && !var6) {
                var7 = 8;
            }
            if (var3 && var6 && !var4 && !var5) {
                var7 = 9;
            }
        }
        if (var7 == -1) {
            if (var3 || var4) {
                var7 = 0;
            }
            if (var5 || var6) {
                var7 = 1;
            }
            if (!this.isStraightRail) {
                if (par1) {
                    if (var4 && var6) {
                        var7 = 6;
                    }
                    if (var5 && var4) {
                        var7 = 7;
                    }
                    if (var6 && var3) {
                        var7 = 9;
                    }
                    if (var3 && var5) {
                        var7 = 8;
                    }
                }
                else {
                    if (var3 && var5) {
                        var7 = 8;
                    }
                    if (var6 && var3) {
                        var7 = 9;
                    }
                    if (var5 && var4) {
                        var7 = 7;
                    }
                    if (var4 && var6) {
                        var7 = 6;
                    }
                }
            }
        }
        if (var7 == 0) {
            if (BlockRailBase.isRailBlockAt(this.logicWorld, this.railX, this.railY + 1, this.railZ - 1)) {
                var7 = 4;
            }
            if (BlockRailBase.isRailBlockAt(this.logicWorld, this.railX, this.railY + 1, this.railZ + 1)) {
                var7 = 5;
            }
        }
        if (var7 == 1) {
            if (BlockRailBase.isRailBlockAt(this.logicWorld, this.railX + 1, this.railY + 1, this.railZ)) {
                var7 = 2;
            }
            if (BlockRailBase.isRailBlockAt(this.logicWorld, this.railX - 1, this.railY + 1, this.railZ)) {
                var7 = 3;
            }
        }
        if (var7 < 0) {
            var7 = 0;
        }
        this.setBasicRail(var7);
        int var8 = var7;
        if (this.isStraightRail) {
            var8 = ((this.logicWorld.getBlockMetadata(this.railX, this.railY, this.railZ) & 0x8) | var7);
        }
        if (par2 || this.logicWorld.getBlockMetadata(this.railX, this.railY, this.railZ) != var8) {
            this.logicWorld.setBlockMetadataWithNotify(this.railX, this.railY, this.railZ, var8, 3);
            for (int var9 = 0; var9 < this.railChunkPosition.size(); ++var9) {
                final BlockBaseRailLogic var10 = this.getRailLogic(this.railChunkPosition.get(var9));
                if (var10 != null) {
                    var10.refreshConnectedTracks();
                    if (var10.canConnectTo(this)) {
                        var10.connectToNeighbor(this);
                    }
                }
            }
        }
    }
}
