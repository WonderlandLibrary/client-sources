package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.DataOutputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInputStream;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import java.util.Set;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class AnvilChunkLoader implements IThreadedFileIO, IChunkLoader
{
    private static final Logger HorizonCode_Horizon_È;
    private List Â;
    private Set Ý;
    private Object Ø­áŒŠá;
    private final File Âµá€;
    private static final String Ó = "CL_00000384";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public AnvilChunkLoader(final File p_i2003_1_) {
        this.Â = Lists.newArrayList();
        this.Ý = Sets.newHashSet();
        this.Ø­áŒŠá = new Object();
        this.Âµá€ = p_i2003_1_;
    }
    
    @Override
    public Chunk HorizonCode_Horizon_È(final World worldIn, final int x, final int z) throws IOException {
        NBTTagCompound var4 = null;
        final ChunkCoordIntPair var5 = new ChunkCoordIntPair(x, z);
        final Object var6 = this.Ø­áŒŠá;
        synchronized (this.Ø­áŒŠá) {
            if (this.Ý.contains(var5)) {
                for (int var7 = 0; var7 < this.Â.size(); ++var7) {
                    if (this.Â.get(var7).HorizonCode_Horizon_È.equals(var5)) {
                        var4 = this.Â.get(var7).Â;
                        break;
                    }
                }
            }
        }
        // monitorexit(this.\u00d8\u00ad\u00e1\u0152\u0160\u00e1)
        if (var4 == null) {
            final DataInputStream var8 = RegionFileCache.Â(this.Âµá€, x, z);
            if (var8 == null) {
                return null;
            }
            var4 = CompressedStreamTools.HorizonCode_Horizon_È(var8);
        }
        return this.HorizonCode_Horizon_È(worldIn, x, z, var4);
    }
    
    protected Chunk HorizonCode_Horizon_È(final World worldIn, final int p_75822_2_, final int p_75822_3_, final NBTTagCompound p_75822_4_) {
        if (!p_75822_4_.Â("Level", 10)) {
            AnvilChunkLoader.HorizonCode_Horizon_È.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is missing level data, skipping");
            return null;
        }
        if (!p_75822_4_.ˆÏ­("Level").Â("Sections", 9)) {
            AnvilChunkLoader.HorizonCode_Horizon_È.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is missing block data, skipping");
            return null;
        }
        Chunk var5 = this.HorizonCode_Horizon_È(worldIn, p_75822_4_.ˆÏ­("Level"));
        if (!var5.HorizonCode_Horizon_È(p_75822_2_, p_75822_3_)) {
            AnvilChunkLoader.HorizonCode_Horizon_È.error("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is in the wrong location; relocating. (Expected " + p_75822_2_ + ", " + p_75822_3_ + ", got " + var5.HorizonCode_Horizon_È + ", " + var5.Â + ")");
            p_75822_4_.HorizonCode_Horizon_È("xPos", p_75822_2_);
            p_75822_4_.HorizonCode_Horizon_È("zPos", p_75822_3_);
            var5 = this.HorizonCode_Horizon_È(worldIn, p_75822_4_.ˆÏ­("Level"));
        }
        return var5;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Chunk chunkIn) throws MinecraftException, IOException {
        worldIn.µÕ();
        try {
            final NBTTagCompound var3 = new NBTTagCompound();
            final NBTTagCompound var4 = new NBTTagCompound();
            var3.HorizonCode_Horizon_È("Level", var4);
            this.HorizonCode_Horizon_È(chunkIn, worldIn, var4);
            this.HorizonCode_Horizon_È(chunkIn.áˆºÑ¢Õ(), var3);
        }
        catch (Exception var5) {
            var5.printStackTrace();
        }
    }
    
    protected void HorizonCode_Horizon_È(final ChunkCoordIntPair p_75824_1_, final NBTTagCompound p_75824_2_) {
        final Object var3 = this.Ø­áŒŠá;
        synchronized (this.Ø­áŒŠá) {
            if (this.Ý.contains(p_75824_1_)) {
                for (int var4 = 0; var4 < this.Â.size(); ++var4) {
                    if (this.Â.get(var4).HorizonCode_Horizon_È.equals(p_75824_1_)) {
                        this.Â.set(var4, new HorizonCode_Horizon_È(p_75824_1_, p_75824_2_));
                        // monitorexit(this.\u00d8\u00ad\u00e1\u0152\u0160\u00e1)
                        return;
                    }
                }
            }
            this.Â.add(new HorizonCode_Horizon_È(p_75824_1_, p_75824_2_));
            this.Ý.add(p_75824_1_);
            ThreadedFileIOBase.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this);
        }
        // monitorexit(this.\u00d8\u00ad\u00e1\u0152\u0160\u00e1)
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        HorizonCode_Horizon_È var1 = null;
        final Object var2 = this.Ø­áŒŠá;
        synchronized (this.Ø­áŒŠá) {
            if (this.Â.isEmpty()) {
                // monitorexit(this.\u00d8\u00ad\u00e1\u0152\u0160\u00e1)
                return false;
            }
            var1 = this.Â.remove(0);
            this.Ý.remove(var1.HorizonCode_Horizon_È);
        }
        // monitorexit(this.\u00d8\u00ad\u00e1\u0152\u0160\u00e1)
        if (var1 != null) {
            try {
                this.HorizonCode_Horizon_È(var1);
            }
            catch (Exception var3) {
                var3.printStackTrace();
            }
        }
        return true;
    }
    
    private void HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_75821_1_) throws IOException {
        final DataOutputStream var2 = RegionFileCache.Ý(this.Âµá€, p_75821_1_.HorizonCode_Horizon_È.HorizonCode_Horizon_È, p_75821_1_.HorizonCode_Horizon_È.Â);
        CompressedStreamTools.HorizonCode_Horizon_È(p_75821_1_.Â, (DataOutput)var2);
        var2.close();
    }
    
    @Override
    public void Â(final World worldIn, final Chunk chunkIn) {
    }
    
    @Override
    public void Â() {
    }
    
    @Override
    public void Ý() {
        while (this.HorizonCode_Horizon_È()) {}
    }
    
    private void HorizonCode_Horizon_È(final Chunk p_75820_1_, final World worldIn, final NBTTagCompound p_75820_3_) {
        p_75820_3_.HorizonCode_Horizon_È("V", (byte)1);
        p_75820_3_.HorizonCode_Horizon_È("xPos", p_75820_1_.HorizonCode_Horizon_È);
        p_75820_3_.HorizonCode_Horizon_È("zPos", p_75820_1_.Â);
        p_75820_3_.HorizonCode_Horizon_È("LastUpdate", worldIn.Šáƒ());
        p_75820_3_.HorizonCode_Horizon_È("HeightMap", p_75820_1_.µà());
        p_75820_3_.HorizonCode_Horizon_È("TerrainPopulated", p_75820_1_.Ø­à());
        p_75820_3_.HorizonCode_Horizon_È("LightPopulated", p_75820_1_.µÕ());
        p_75820_3_.HorizonCode_Horizon_È("InhabitedTime", p_75820_1_.Šáƒ());
        final ExtendedBlockStorage[] var4 = p_75820_1_.Â();
        final NBTTagList var5 = new NBTTagList();
        final boolean var6 = !worldIn.£à.Å();
        final ExtendedBlockStorage[] var7 = var4;
        for (int var8 = var4.length, var9 = 0; var9 < var8; ++var9) {
            final ExtendedBlockStorage var10 = var7[var9];
            if (var10 != null) {
                final NBTTagCompound var11 = new NBTTagCompound();
                var11.HorizonCode_Horizon_È("Y", (byte)(var10.Ý() >> 4 & 0xFF));
                final byte[] var12 = new byte[var10.Âµá€().length];
                final NibbleArray var13 = new NibbleArray();
                NibbleArray var14 = null;
                for (int var15 = 0; var15 < var10.Âµá€().length; ++var15) {
                    final char var16 = var10.Âµá€()[var15];
                    final int var17 = var15 & 0xF;
                    final int var18 = var15 >> 8 & 0xF;
                    final int var19 = var15 >> 4 & 0xF;
                    if (var16 >> 12 != '\0') {
                        if (var14 == null) {
                            var14 = new NibbleArray();
                        }
                        var14.HorizonCode_Horizon_È(var17, var18, var19, var16 >> 12);
                    }
                    var12[var15] = (byte)(var16 >> 4 & 'ÿ');
                    var13.HorizonCode_Horizon_È(var17, var18, var19, var16 & '\u000f');
                }
                var11.HorizonCode_Horizon_È("Blocks", var12);
                var11.HorizonCode_Horizon_È("Data", var13.HorizonCode_Horizon_È());
                if (var14 != null) {
                    var11.HorizonCode_Horizon_È("Add", var14.HorizonCode_Horizon_È());
                }
                var11.HorizonCode_Horizon_È("BlockLight", var10.Ó().HorizonCode_Horizon_È());
                if (var6) {
                    var11.HorizonCode_Horizon_È("SkyLight", var10.à().HorizonCode_Horizon_È());
                }
                else {
                    var11.HorizonCode_Horizon_È("SkyLight", new byte[var10.Ó().HorizonCode_Horizon_È().length]);
                }
                var5.HorizonCode_Horizon_È(var11);
            }
        }
        p_75820_3_.HorizonCode_Horizon_È("Sections", var5);
        p_75820_3_.HorizonCode_Horizon_È("Biomes", p_75820_1_.ÂµÈ());
        p_75820_1_.à(false);
        final NBTTagList var20 = new NBTTagList();
        for (int var8 = 0; var8 < p_75820_1_.¥Æ().length; ++var8) {
            for (final Entity var22 : p_75820_1_.¥Æ()[var8]) {
                final NBTTagCompound var11 = new NBTTagCompound();
                if (var22.Ø­áŒŠá(var11)) {
                    p_75820_1_.à(true);
                    var20.HorizonCode_Horizon_È(var11);
                }
            }
        }
        p_75820_3_.HorizonCode_Horizon_È("Entities", var20);
        final NBTTagList var23 = new NBTTagList();
        for (final TileEntity var24 : p_75820_1_.ˆà().values()) {
            final NBTTagCompound var11 = new NBTTagCompound();
            var24.Â(var11);
            var23.HorizonCode_Horizon_È(var11);
        }
        p_75820_3_.HorizonCode_Horizon_È("TileEntities", var23);
        final List var25 = worldIn.HorizonCode_Horizon_È(p_75820_1_, false);
        if (var25 != null) {
            final long var26 = worldIn.Šáƒ();
            final NBTTagList var27 = new NBTTagList();
            for (final NextTickListEntry var29 : var25) {
                final NBTTagCompound var30 = new NBTTagCompound();
                final ResourceLocation_1975012498 var31 = (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(var29.HorizonCode_Horizon_È());
                var30.HorizonCode_Horizon_È("i", (var31 == null) ? "" : var31.toString());
                var30.HorizonCode_Horizon_È("x", var29.HorizonCode_Horizon_È.HorizonCode_Horizon_È());
                var30.HorizonCode_Horizon_È("y", var29.HorizonCode_Horizon_È.Â());
                var30.HorizonCode_Horizon_È("z", var29.HorizonCode_Horizon_È.Ý());
                var30.HorizonCode_Horizon_È("t", (int)(var29.Â - var26));
                var30.HorizonCode_Horizon_È("p", var29.Ý);
                var27.HorizonCode_Horizon_È(var30);
            }
            p_75820_3_.HorizonCode_Horizon_È("TileTicks", var27);
        }
    }
    
    private Chunk HorizonCode_Horizon_È(final World worldIn, final NBTTagCompound p_75823_2_) {
        final int var3 = p_75823_2_.Ó("xPos");
        final int var4 = p_75823_2_.Ó("zPos");
        final Chunk var5 = new Chunk(worldIn, var3, var4);
        var5.HorizonCode_Horizon_È(p_75823_2_.á("HeightMap"));
        var5.Ø­áŒŠá(p_75823_2_.£á("TerrainPopulated"));
        var5.Âµá€(p_75823_2_.£á("LightPopulated"));
        var5.Ý(p_75823_2_.à("InhabitedTime"));
        final NBTTagList var6 = p_75823_2_.Ý("Sections", 10);
        final byte var7 = 16;
        final ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
        final boolean var9 = !worldIn.£à.Å();
        for (int var10 = 0; var10 < var6.Âµá€(); ++var10) {
            final NBTTagCompound var11 = var6.Â(var10);
            final byte var12 = var11.Ø­áŒŠá("Y");
            final ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
            final byte[] var14 = var11.ÂµÈ("Blocks");
            final NibbleArray var15 = new NibbleArray(var11.ÂµÈ("Data"));
            final NibbleArray var16 = var11.Â("Add", 7) ? new NibbleArray(var11.ÂµÈ("Add")) : null;
            final char[] var17 = new char[var14.length];
            for (int var18 = 0; var18 < var17.length; ++var18) {
                final int var19 = var18 & 0xF;
                final int var20 = var18 >> 8 & 0xF;
                final int var21 = var18 >> 4 & 0xF;
                final int var22 = (var16 != null) ? var16.HorizonCode_Horizon_È(var19, var20, var21) : 0;
                var17[var18] = (char)(var22 << 12 | (var14[var18] & 0xFF) << 4 | var15.HorizonCode_Horizon_È(var19, var20, var21));
            }
            var13.HorizonCode_Horizon_È(var17);
            var13.HorizonCode_Horizon_È(new NibbleArray(var11.ÂµÈ("BlockLight")));
            if (var9) {
                var13.Â(new NibbleArray(var11.ÂµÈ("SkyLight")));
            }
            var13.Ø­áŒŠá();
            var8[var12] = var13;
        }
        var5.HorizonCode_Horizon_È(var8);
        if (p_75823_2_.Â("Biomes", 7)) {
            var5.HorizonCode_Horizon_È(p_75823_2_.ÂµÈ("Biomes"));
        }
        final NBTTagList var23 = p_75823_2_.Ý("Entities", 10);
        if (var23 != null) {
            for (int var24 = 0; var24 < var23.Âµá€(); ++var24) {
                final NBTTagCompound var25 = var23.Â(var24);
                final Entity var26 = EntityList.HorizonCode_Horizon_È(var25, worldIn);
                var5.à(true);
                if (var26 != null) {
                    var5.HorizonCode_Horizon_È(var26);
                    Entity var27 = var26;
                    for (NBTTagCompound var28 = var25; var28.Â("Riding", 10); var28 = var28.ˆÏ­("Riding")) {
                        final Entity var29 = EntityList.HorizonCode_Horizon_È(var28.ˆÏ­("Riding"), worldIn);
                        if (var29 != null) {
                            var5.HorizonCode_Horizon_È(var29);
                            var27.HorizonCode_Horizon_È(var29);
                        }
                        var27 = var29;
                    }
                }
            }
        }
        final NBTTagList var30 = p_75823_2_.Ý("TileEntities", 10);
        if (var30 != null) {
            for (int var31 = 0; var31 < var30.Âµá€(); ++var31) {
                final NBTTagCompound var32 = var30.Â(var31);
                final TileEntity var33 = TileEntity.Ý(var32);
                if (var33 != null) {
                    var5.HorizonCode_Horizon_È(var33);
                }
            }
        }
        if (p_75823_2_.Â("TileTicks", 9)) {
            final NBTTagList var34 = p_75823_2_.Ý("TileTicks", 10);
            if (var34 != null) {
                for (int var35 = 0; var35 < var34.Âµá€(); ++var35) {
                    final NBTTagCompound var36 = var34.Â(var35);
                    Block var37;
                    if (var36.Â("i", 8)) {
                        var37 = Block.HorizonCode_Horizon_È(var36.áˆºÑ¢Õ("i"));
                    }
                    else {
                        var37 = Block.HorizonCode_Horizon_È(var36.Ó("i"));
                    }
                    worldIn.Â(new BlockPos(var36.Ó("x"), var36.Ó("y"), var36.Ó("z")), var37, var36.Ó("t"), var36.Ó("p"));
                }
            }
        }
        return var5;
    }
    
    static class HorizonCode_Horizon_È
    {
        public final ChunkCoordIntPair HorizonCode_Horizon_È;
        public final NBTTagCompound Â;
        private static final String Ý = "CL_00000385";
        
        public HorizonCode_Horizon_È(final ChunkCoordIntPair p_i2002_1_, final NBTTagCompound p_i2002_2_) {
            this.HorizonCode_Horizon_È = p_i2002_1_;
            this.Â = p_i2002_2_;
        }
    }
}
