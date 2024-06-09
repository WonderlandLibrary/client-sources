package com.client.glowclient;

import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.datafix.*;
import net.minecraft.world.storage.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.storage.*;
import com.client.glowclient.utils.*;
import net.minecraft.world.*;
import java.io.*;
import net.minecraft.entity.player.*;

public class Dd extends AnvilChunkLoader
{
    private static final Logger A;
    private final Map<ChunkPos, NBTTagCompound> B;
    private final File b;
    
    public NBTTagList getEntityList(final Chunk chunk) {
        final NBTTagList list = new NBTTagList();
        if (!WA.D(chunk)) {
            return list;
        }
        final ArrayList<Entity> list2 = new ArrayList<Entity>();
        final ClassInheritanceMultiMap[] entityLists;
        final int length = (entityLists = chunk.getEntityLists()).length;
        int n;
        int i = n = 0;
        while (i < length) {
            final ClassInheritanceMultiMap classInheritanceMultiMap = entityLists[n];
            ++n;
            list2.addAll((Collection<?>)classInheritanceMultiMap);
            i = n;
        }
        final Iterator<Entity> iterator2;
        Iterator<Entity> iterator = iterator2 = uc.e.get((Object)chunk.getPos()).iterator();
        while (iterator.hasNext()) {
            final Entity entity = iterator2.next();
            iterator = iterator2;
            final ArrayList<Entity> list3 = list2;
            final Entity entity2 = entity;
            entity2.isDead = false;
            list3.add(entity2);
        }
        final Iterator<Object> iterator3 = list2.iterator();
    Label_0135:
        while (true) {
            Iterator<Object> iterator4 = iterator3;
            while (iterator4.hasNext()) {
                final Entity entity3;
                if ((entity3 = iterator3.next()) == null) {
                    Dd.A.warn(new StringBuilder().insert(0, "[WDL] Null entity in chunk at ").append(chunk.getPos()).toString());
                    iterator4 = iterator3;
                }
                else {
                    if (M(entity3)) {
                        for (final ra<H> ra : Oa.M(H.class)) {
                            try {
                                if (ra.A.M(entity3)) {
                                    ra.A.M(entity3);
                                    continue;
                                }
                                continue;
                            }
                            catch (Exception ex) {
                                throw new RuntimeException(new StringBuilder().insert(0, "Failed to edit entity ").append(entity3).append(" for chunk at ").append(chunk.getPos()).append(" with extension ").append(ra).toString(), ex);
                            }
                            break;
                        }
                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                        try {
                            if (entity3.writeToNBTOptional(nbtTagCompound)) {
                                final NBTTagList list4 = list;
                                final NBTTagCompound nbtTagCompound2 = nbtTagCompound;
                                chunk.setHasEntities(true);
                                list4.appendTag((NBTBase)nbtTagCompound2);
                                continue Label_0135;
                            }
                            continue Label_0135;
                        }
                        catch (Exception ex2) {
                            Qd.M(ub.d, "com.client.glowclient.utils.mod.imports.wdl.messages.generalError.failedToSaveEntity", entity3, chunk.x, chunk.z, ex2);
                            Dd.A.warn(new StringBuilder().insert(0, "Compound: ").append(nbtTagCompound).toString());
                            Dd.A.warn("Entity metadata dump:");
                            try {
                                final List all;
                                if ((all = entity3.getDataManager().getAll()) == null) {
                                    Dd.A.warn("No entries (getAllWatched() returned null)");
                                }
                                else {
                                    Dd.A.warn((Object)all);
                                    final Iterator<EntityDataManager$DataEntry> iterator6 = all.iterator();
                                    while (iterator6.hasNext()) {
                                        final EntityDataManager$DataEntry entityDataManager$DataEntry;
                                        if ((entityDataManager$DataEntry = iterator6.next()) != null) {
                                            Dd.A.warn(new StringBuilder().insert(0, "DataEntry [getValue()=").append(entityDataManager$DataEntry.getValue()).append(", isDirty()=").append(entityDataManager$DataEntry.isDirty()).append(", getKey()=DataParameter [getId()=").append(entityDataManager$DataEntry.getKey().getId()).append(", getSerializer()=").append(entityDataManager$DataEntry.getKey().getSerializer()).append("]]").toString());
                                        }
                                    }
                                }
                            }
                            catch (Exception ex3) {
                                Dd.A.warn("Failed to complete dump: ", (Throwable)ex2);
                            }
                            Dd.A.warn("End entity metadata dump");
                            iterator4 = iterator3;
                            continue;
                        }
                        break;
                    }
                    iterator4 = iterator3;
                }
            }
            break;
        }
        return list;
    }
    
    public boolean shouldImportTileEntity(final String s, final BlockPos blockPos, final Block block, final NBTTagCompound nbtTagCompound, final Chunk chunk) {
        if (block instanceof BlockChest && s.equals(nb.M((Class<? extends TileEntity>)TileEntityChest.class))) {
            return true;
        }
        if (block instanceof BlockDispenser && s.equals(nb.M((Class<? extends TileEntity>)TileEntityDispenser.class))) {
            return true;
        }
        if (block instanceof BlockDropper && s.equals(nb.M((Class<? extends TileEntity>)TileEntityDropper.class))) {
            return true;
        }
        if (block instanceof BlockFurnace && s.equals(nb.M((Class<? extends TileEntity>)TileEntityFurnace.class))) {
            return true;
        }
        if (block instanceof BlockNote && s.equals(nb.M((Class<? extends TileEntity>)TileEntityNote.class))) {
            return true;
        }
        if (block instanceof BlockBrewingStand && s.equals(nb.M((Class<? extends TileEntity>)TileEntityBrewingStand.class))) {
            return true;
        }
        if (block instanceof BlockHopper && s.equals(nb.M((Class<? extends TileEntity>)TileEntityHopper.class))) {
            return true;
        }
        if (block instanceof BlockBeacon && s.equals(nb.M((Class<? extends TileEntity>)TileEntityBeacon.class))) {
            return true;
        }
        final Iterator<ra<l>> iterator = Oa.M(l.class).iterator();
        while (iterator.hasNext()) {
            if (iterator.next().A.M(s, blockPos, block, nbtTagCompound, chunk)) {
                return true;
            }
        }
        return false;
    }
    
    public Dd(final File b) {
        final Class<AnvilChunkLoader> clazz = AnvilChunkLoader.class;
        super(b, (DataFixer)null);
        this.B = Hd.D(this, (Class<Dd>)clazz, (Class<Map<ChunkPos, NBTTagCompound>>)Map.class);
        this.b = b;
    }
    
    public synchronized int getNumPendingChunks() {
        return this.B.size();
    }
    
    private static File M(final SaveHandler saveHandler, final WorldProvider worldProvider) {
        final File worldDirectory = saveHandler.getWorldDirectory();
        try {
            final String s;
            if ((s = (String)worldProvider.getClass().getMethod("getSaveFolder", (Class<?>[])new Class[0]).invoke(worldProvider, new Object[0])) != null) {
                final File file;
                (file = new File(worldDirectory, s)).mkdirs();
                return file;
            }
            return worldDirectory;
        }
        catch (Exception ex) {
            if (worldProvider instanceof WorldProviderHell) {
                final File file2 = new File(worldDirectory, "DIM-1");
                file2.mkdirs();
                return file2;
            }
            if (worldProvider instanceof WorldProviderEnd) {
                final File file3 = new File(worldDirectory, "DIM1");
                file3.mkdirs();
                return file3;
            }
            return worldDirectory;
        }
    }
    
    public Map<BlockPos, NBTTagCompound> getOldTileEntities(final Chunk p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/HashMap.<init>:()V
        //     7: astore_2       
        //     8: aload_0        
        //     9: getfield        com/client/glowclient/Dd.B:Ljava/util/Map;
        //    12: aload_1        
        //    13: invokevirtual   net/minecraft/world/chunk/Chunk.getPos:()Lnet/minecraft/util/math/ChunkPos;
        //    16: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    21: checkcast       Lnet/minecraft/nbt/NBTTagCompound;
        //    24: dup            
        //    25: astore_3       
        //    26: ifnull          56
        //    29: getstatic       com/client/glowclient/Dd.A:Lorg/apache/logging/log4j/Logger;
        //    32: ldc_w           "getOldTileEntities (and thus saveChunk) was called while a chunk was already in chunksToSave!  (location: {})"
        //    35: aload_1        
        //    36: invokevirtual   net/minecraft/world/chunk/Chunk.getPos:()Lnet/minecraft/util/math/ChunkPos;
        //    39: new             Ljava/lang/Exception;
        //    42: dup            
        //    43: invokespecial   java/lang/Exception.<init>:()V
        //    46: invokeinterface org/apache/logging/log4j/Logger.warn:(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
        //    51: aload_3        
        //    52: goto            224
        //    55: athrow         
        //    56: aload_0        
        //    57: getfield        com/client/glowclient/Dd.b:Ljava/io/File;
        //    60: aload_1        
        //    61: dup            
        //    62: getfield        net/minecraft/world/chunk/Chunk.x:I
        //    65: swap           
        //    66: getfield        net/minecraft/world/chunk/Chunk.z:I
        //    69: invokestatic    net/minecraft/world/chunk/storage/RegionFileCache.getChunkInputStream:(Ljava/io/File;II)Ljava/io/DataInputStream;
        //    72: astore          4
        //    74: aconst_null    
        //    75: astore          5
        //    77: aload           4
        //    79: ifnonnull       124
        //    82: aload_2        
        //    83: astore          6
        //    85: aload           4
        //    87: ifnull          121
        //    90: aload           5
        //    92: ifnull          116
        //    95: aload           4
        //    97: invokevirtual   java/io/DataInputStream.close:()V
        //   100: aload           6
        //   102: areturn        
        //   103: athrow         
        //   104: astore          7
        //   106: aload           6
        //   108: aload           5
        //   110: aload           7
        //   112: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   115: areturn        
        //   116: aload           4
        //   118: invokevirtual   java/io/DataInputStream.close:()V
        //   121: aload           6
        //   123: areturn        
        //   124: aload           4
        //   126: invokestatic    net/minecraft/nbt/CompressedStreamTools.read:(Ljava/io/DataInputStream;)Lnet/minecraft/nbt/NBTTagCompound;
        //   129: astore_3       
        //   130: aload           4
        //   132: ifnull          223
        //   135: aload           5
        //   137: ifnull          162
        //   140: aload           4
        //   142: invokevirtual   java/io/DataInputStream.close:()V
        //   145: aload_3        
        //   146: goto            224
        //   149: astore          6
        //   151: aload_3        
        //   152: aload           5
        //   154: aload           6
        //   156: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   159: goto            224
        //   162: aload           4
        //   164: invokevirtual   java/io/DataInputStream.close:()V
        //   167: aload_3        
        //   168: goto            224
        //   171: astore          6
        //   173: aload           6
        //   175: dup            
        //   176: astore          5
        //   178: athrow         
        //   179: astore          8
        //   181: aload           4
        //   183: ifnull          220
        //   186: aload           5
        //   188: ifnull          215
        //   191: aload           4
        //   193: invokevirtual   java/io/DataInputStream.close:()V
        //   196: aload           8
        //   198: goto            222
        //   201: astore          9
        //   203: aload           8
        //   205: aload           5
        //   207: aload           9
        //   209: invokevirtual   java/lang/Throwable.addSuppressed:(Ljava/lang/Throwable;)V
        //   212: goto            222
        //   215: aload           4
        //   217: invokevirtual   java/io/DataInputStream.close:()V
        //   220: aload           8
        //   222: athrow         
        //   223: aload_3        
        //   224: ldc_w           "Level"
        //   227: invokevirtual   net/minecraft/nbt/NBTTagCompound.getCompoundTag:(Ljava/lang/String;)Lnet/minecraft/nbt/NBTTagCompound;
        //   230: dup            
        //   231: astore          4
        //   233: ldc_w           "TileEntities"
        //   236: bipush          10
        //   238: invokevirtual   net/minecraft/nbt/NBTTagCompound.getTagList:(Ljava/lang/String;I)Lnet/minecraft/nbt/NBTTagList;
        //   241: dup            
        //   242: astore          5
        //   244: ifnull          430
        //   247: iconst_0       
        //   248: istore          6
        //   250: iload           6
        //   252: aload           5
        //   254: invokevirtual   net/minecraft/nbt/NBTTagList.tagCount:()I
        //   257: if_icmpge       430
        //   260: aload           5
        //   262: iload           6
        //   264: invokevirtual   net/minecraft/nbt/NBTTagList.getCompoundTagAt:(I)Lnet/minecraft/nbt/NBTTagCompound;
        //   267: dup            
        //   268: astore          7
        //   270: ldc_w           "id"
        //   273: invokevirtual   net/minecraft/nbt/NBTTagCompound.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   276: astore          8
        //   278: new             Lnet/minecraft/util/math/BlockPos;
        //   281: dup            
        //   282: aload           7
        //   284: ldc_w           "x"
        //   287: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   290: aload           7
        //   292: ldc_w           "y"
        //   295: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   298: aload           7
        //   300: ldc_w           "z"
        //   303: invokevirtual   net/minecraft/nbt/NBTTagCompound.getInteger:(Ljava/lang/String;)I
        //   306: invokespecial   net/minecraft/util/math/BlockPos.<init>:(III)V
        //   309: astore          9
        //   311: aload_1        
        //   312: aload           9
        //   314: invokevirtual   net/minecraft/world/chunk/Chunk.getBlockState:(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   317: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   322: astore          4
        //   324: aload_0        
        //   325: aload           8
        //   327: aload           9
        //   329: aload           4
        //   331: aload           7
        //   333: aload_1        
        //   334: invokevirtual   com/client/glowclient/Dd.shouldImportTileEntity:(Ljava/lang/String;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/world/chunk/Chunk;)Z
        //   337: ifeq            354
        //   340: aload_2        
        //   341: aload           9
        //   343: aload           7
        //   345: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   350: pop            
        //   351: goto            380
        //   354: getstatic       com/client/glowclient/ub.j:Lcom/client/glowclient/ub;
        //   357: ldc_w           "com.client.glowclient.utils.mod.imports.wdl.messages.tileEntity.notImporting"
        //   360: iconst_2       
        //   361: anewarray       Ljava/lang/Object;
        //   364: iconst_1       
        //   365: dup            
        //   366: pop2           
        //   367: dup            
        //   368: iconst_0       
        //   369: aload           8
        //   371: aastore        
        //   372: dup            
        //   373: iconst_1       
        //   374: aload           9
        //   376: aastore        
        //   377: invokestatic    com/client/glowclient/Qd.M:(Lcom/client/glowclient/a;Ljava/lang/String;[Ljava/lang/Object;)V
        //   380: iinc            6, 1
        //   383: goto            250
        //   386: nop            
        //   387: nop            
        //   388: athrow         
        //   389: astore_3       
        //   390: getstatic       com/client/glowclient/ub.d:Lcom/client/glowclient/ub;
        //   393: ldc_w           "com.client.glowclient.utils.mod.imports.wdl.messages.generalError.failedToImportTE"
        //   396: iconst_3       
        //   397: anewarray       Ljava/lang/Object;
        //   400: iconst_1       
        //   401: dup            
        //   402: pop2           
        //   403: dup            
        //   404: iconst_0       
        //   405: aload_1        
        //   406: getfield        net/minecraft/world/chunk/Chunk.x:I
        //   409: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   412: aastore        
        //   413: dup            
        //   414: iconst_1       
        //   415: aload_1        
        //   416: getfield        net/minecraft/world/chunk/Chunk.z:I
        //   419: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   422: aastore        
        //   423: dup            
        //   424: iconst_2       
        //   425: aload_3        
        //   426: aastore        
        //   427: invokestatic    com/client/glowclient/Qd.M:(Lcom/client/glowclient/a;Ljava/lang/String;[Ljava/lang/Object;)V
        //   430: aload_2        
        //   431: areturn        
        //    Signature:
        //  (Lnet/minecraft/world/chunk/Chunk;)Ljava/util/Map<Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/nbt/NBTTagCompound;>;
        //    StackMapTable: 00 17 FF 00 37 00 00 00 01 07 00 1E FF 00 00 00 04 07 00 02 07 00 20 07 01 76 07 00 99 00 00 FF 00 2E 00 00 00 01 07 00 1E FF 00 00 00 07 07 00 02 07 00 20 07 01 76 07 00 99 07 01 88 05 07 01 76 00 01 07 00 1E 0B 04 FA 00 02 58 07 00 1E 0C 48 07 00 1E FF 00 07 00 06 07 00 02 07 00 20 07 01 76 07 00 99 07 01 88 07 00 1E 00 01 07 00 1E FF 00 15 00 09 07 00 02 07 00 20 07 01 76 07 00 99 07 01 88 07 00 1E 00 00 07 00 1E 00 01 07 00 1E 0D 04 41 07 00 1E FF 00 00 00 06 07 00 02 07 00 20 07 01 76 07 00 99 07 01 88 05 00 00 FF 00 00 00 04 07 00 02 07 00 20 07 01 76 07 00 99 00 01 07 00 99 FE 00 19 07 00 5C 07 00 12 01 FF 00 67 00 0A 07 00 02 07 00 20 07 01 76 07 00 99 07 01 13 07 00 12 01 07 00 99 07 01 0C 07 01 11 00 00 19 FF 00 05 00 00 00 01 07 00 1E FF 00 02 00 03 07 00 02 07 00 20 07 01 76 00 01 07 00 10 FC 00 28 07 00 5C
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  95     100    104    116    Ljava/lang/Throwable;
        //  140    145    149    162    Ljava/lang/Throwable;
        //  77     85     171    179    Ljava/lang/Throwable;
        //  124    130    171    179    Ljava/lang/Throwable;
        //  77     85     179    223    Any
        //  124    130    179    223    Any
        //  191    196    201    215    Ljava/lang/Throwable;
        //  171    181    179    223    Any
        //  8      55     389    430    Ljava/lang/Exception;
        //  56     103    389    430    Ljava/lang/Exception;
        //  104    121    389    430    Ljava/lang/Exception;
        //  124    386    389    430    Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        A = LogManager.getLogger();
    }
    
    public NBTTagList getTileEntityList(final Chunk chunk) {
        final NBTTagList list = new NBTTagList();
        if (!WA.M(chunk)) {
            return list;
        }
        final Map tileEntityMap = chunk.getTileEntityMap();
        final Map<BlockPos, NBTTagCompound> oldTileEntities = this.getOldTileEntities(chunk);
        Map<BlockPos, TileEntity> map;
        if ((map = uc.f.get(chunk.getPos())) == null) {
            map = new HashMap<BlockPos, TileEntity>();
        }
        final HashSet<Object> set;
        (set = new HashSet<Object>()).addAll(tileEntityMap.keySet());
        set.addAll(oldTileEntities.keySet());
        set.addAll(map.keySet());
        final Iterator<BlockPos> iterator = set.iterator();
    Label_0118:
        while (true) {
            Iterator<BlockPos> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final BlockPos blockPos = iterator.next();
                if (map.containsKey(blockPos)) {
                    final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                    final TileEntity tileEntity = map.get(blockPos);
                    try {
                        tileEntity.writeToNBT(nbtTagCompound);
                    }
                    catch (Exception ex) {
                        Qd.M(ub.d, "com.client.glowclient.utils.mod.imports.wdl.messages.generalError.failedToSaveTE", tileEntity, blockPos, chunk.x, chunk.z, ex);
                        Dd.A.warn(new StringBuilder().insert(0, "Compound: ").append(nbtTagCompound).toString());
                        iterator2 = iterator;
                        continue;
                    }
                    Qd.M(ub.j, "com.client.glowclient.utils.mod.imports.wdl.messages.tileEntity.usingNew", new StringBuilder().insert(0, nbtTagCompound.getString("id")).append(" (").append(tileEntity.getClass().getCanonicalName()).append(")").toString(), blockPos);
                    final NBTTagList list2 = list;
                    final BlockPos blockPos2 = blockPos;
                    final NBTTagCompound nbtTagCompound2 = nbtTagCompound;
                    M(blockPos2, nbtTagCompound2, sa.b);
                    list2.appendTag((NBTBase)nbtTagCompound2);
                    continue Label_0118;
                }
                if (oldTileEntities.containsKey(blockPos)) {
                    final NBTTagCompound nbtTagCompound3;
                    Qd.M(ub.j, "com.client.glowclient.utils.mod.imports.wdl.messages.tileEntity.usingOld", (nbtTagCompound3 = oldTileEntities.get(blockPos)).getString("id"), blockPos);
                    final NBTTagList list3 = list;
                    final BlockPos blockPos3 = blockPos;
                    final NBTTagCompound nbtTagCompound4 = nbtTagCompound3;
                    M(blockPos3, nbtTagCompound4, sa.A);
                    list3.appendTag((NBTBase)nbtTagCompound4);
                    continue Label_0118;
                }
                if (tileEntityMap.containsKey(blockPos)) {
                    final TileEntity tileEntity2 = tileEntityMap.get(blockPos);
                    final NBTTagCompound nbtTagCompound5 = new NBTTagCompound();
                    BlockPos blockPos4;
                    try {
                        tileEntity2.writeToNBT(nbtTagCompound5);
                        blockPos4 = blockPos;
                    }
                    catch (Exception ex2) {
                        Qd.M(ub.d, "com.client.glowclient.utils.mod.imports.wdl.messages.generalError.failedToSaveTE", tileEntity2, blockPos, chunk.x, chunk.z, ex2);
                        Dd.A.warn(new StringBuilder().insert(0, "Compound: ").append(nbtTagCompound5).toString());
                        iterator2 = iterator;
                        continue;
                    }
                    M(blockPos4, nbtTagCompound5, sa.L);
                    list.appendTag((NBTBase)nbtTagCompound5);
                    continue Label_0118;
                }
                continue Label_0118;
            }
            break;
        }
        return list;
    }
    
    private NBTTagCompound M(final Chunk chunk, final World world) {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final String s = "U\u000bt\u0004~\fh\u0000x1u\by";
        final String s2 = "~LUMFu]UGISQWA";
        final NBTTagCompound nbtTagCompound2 = nbtTagCompound;
        final String s3 = "1y\u0017n\u0004u\u000bL\nl\u0010p\u0004h\u0000x";
        final String s4 = "mWLUMFhSU";
        final NBTTagCompound nbtTagCompound3 = nbtTagCompound;
        final String s5 = ")}\u0016h0l\u0001}\u0011y";
        final String s6 = "Hu]V";
        final NBTTagCompound nbtTagCompound4 = nbtTagCompound;
        final String s7 = "\u001dL\no";
        nbtTagCompound.setByte("V", (byte)1);
        nbtTagCompound4.setInteger(PC.M(s7), chunk.x);
        nbtTagCompound4.setInteger(Ga.M(s6), chunk.z);
        nbtTagCompound3.setLong(PC.M(s5), world.getTotalWorldTime());
        nbtTagCompound3.setIntArray(Ga.M(s4), chunk.getHeightMap());
        nbtTagCompound2.setBoolean(PC.M(s3), true);
        nbtTagCompound2.setBoolean(Ga.M(s2), chunk.isLightPopulated());
        nbtTagCompound2.setLong(PC.M(s), chunk.getInhabitedTime());
        final ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
        final NBTTagList list = new NBTTagList();
        final ExtendedBlockStorage[] array = blockStorageArray;
        final boolean m = nb.M(world);
        final ExtendedBlockStorage[] array2 = array;
        final int length = array.length;
        int n;
        int i = n = 0;
        while (i < length) {
            final ExtendedBlockStorage extendedBlockStorage;
            if ((extendedBlockStorage = array2[n]) != null) {
                final NBTTagCompound nbtTagCompound5 = new NBTTagCompound();
                final int n2 = 4096;
                final NBTTagCompound nbtTagCompound6 = nbtTagCompound5;
                nbtTagCompound6.setByte("Y", (byte)(extendedBlockStorage.getYLocation() >> 4 & 0xFF));
                final byte[] array3 = new byte[n2];
                final NibbleArray nibbleArray = new NibbleArray();
                final NibbleArray dataForNBT = extendedBlockStorage.getData().getDataForNBT(array3, nibbleArray);
                nbtTagCompound6.setByteArray("Blocks", array3);
                nbtTagCompound6.setByteArray("Data", nibbleArray.getData());
                if (dataForNBT != null) {
                    nbtTagCompound5.setByteArray("Add", dataForNBT.getData());
                }
                final NibbleArray blockLight;
                final int length2 = (blockLight = extendedBlockStorage.getBlockLight()).getData().length;
                final boolean b = m;
                nbtTagCompound5.setByteArray("BlockLight", blockLight.getData());
                if (b) {
                    final NibbleArray skyLight;
                    if ((skyLight = extendedBlockStorage.getSkyLight()) != null) {
                        nbtTagCompound5.setByteArray("SkyLight", skyLight.getData());
                    }
                    else {
                        Dd.A.error(new StringBuilder().insert(0, "[WDL] Skylight array for chunk at ").append(chunk.x).append(", ").append(chunk.z).append(" is null despite VersionedProperties saying it shouldn't be!").toString());
                        nbtTagCompound5.setByteArray("SkyLight", new byte[length2]);
                    }
                }
                else {
                    nbtTagCompound5.setByteArray("SkyLight", new byte[length2]);
                }
                list.appendTag((NBTBase)nbtTagCompound5);
            }
            i = ++n;
        }
        final NBTTagCompound nbtTagCompound7 = nbtTagCompound;
        final boolean hasEntities = false;
        final String s8 = "'u\nq\u0000o";
        final NBTTagCompound nbtTagCompound8 = nbtTagCompound;
        nbtTagCompound8.setTag("Sections", (NBTBase)list);
        nbtTagCompound8.setByteArray(PC.M(s8), chunk.getBiomeArray());
        chunk.setHasEntities(hasEntities);
        nbtTagCompound7.setTag("Entities", (NBTBase)this.getEntityList(chunk));
        nbtTagCompound7.setTag("TileEntities", (NBTBase)this.getTileEntityList(chunk));
        final List pendingBlockUpdates;
        if ((pendingBlockUpdates = world.getPendingBlockUpdates(chunk, false)) != null) {
            final long totalWorldTime = world.getTotalWorldTime();
            final NBTTagList list2 = new NBTTagList();
            final Iterator<NextTickListEntry> iterator2;
            Iterator<NextTickListEntry> iterator = iterator2 = pendingBlockUpdates.iterator();
            while (iterator.hasNext()) {
                final NextTickListEntry nextTickListEntry = iterator2.next();
                final NBTTagCompound nbtTagCompound9 = new NBTTagCompound();
                final ResourceLocation resourceLocation = (ResourceLocation)Block.REGISTRY.getNameForObject((Object)nextTickListEntry.getBlock());
                nbtTagCompound9.setString("i", (resourceLocation == null) ? "" : resourceLocation.toString());
                iterator = iterator2;
                final NBTTagList list3 = list2;
                final String s9 = "l";
                final NBTTagCompound nbtTagCompound10 = nbtTagCompound9;
                final String s10 = "Q";
                final String s11 = "f";
                final NBTTagCompound nbtTagCompound11 = nbtTagCompound9;
                final String s12 = "\\";
                nbtTagCompound9.setInteger("x", nextTickListEntry.position.getX());
                nbtTagCompound11.setInteger(Ga.M(s12), nextTickListEntry.position.getY());
                nbtTagCompound11.setInteger(PC.M(s11), nextTickListEntry.position.getZ());
                nbtTagCompound10.setInteger(Ga.M(s10), (int)(nextTickListEntry.scheduledTime - totalWorldTime));
                nbtTagCompound10.setInteger(PC.M(s9), nextTickListEntry.priority);
                list3.appendTag((NBTBase)nbtTagCompound10);
            }
            nbtTagCompound.setTag("TileTicks", (NBTBase)list2);
        }
        return nbtTagCompound;
    }
    
    public static void M(final BlockPos blockPos, final NBTTagCompound nbtTagCompound, final sa sa) {
        for (final ra<d> ra : Oa.M(d.class)) {
            try {
                if (ra.A.M(blockPos, nbtTagCompound, sa)) {
                    ra.A.M(blockPos, nbtTagCompound, sa);
                    Qd.M(ub.j, "com.client.glowclient.utils.mod.imports.wdl.messages.tileEntity.edited", blockPos, ra.D());
                    continue;
                }
                continue;
            }
            catch (Exception ex) {
                throw new RuntimeException(new StringBuilder().insert(0, "Failed to edit tile entity at ").append(blockPos).append(" with extension ").append(ra).append("; NBT is now ").append(nbtTagCompound).append(" (this may be the initial value, an edited value, or a partially edited value)").toString(), ex);
            }
            break;
        }
    }
    
    public static Dd M(final SaveHandler saveHandler, final WorldProvider worldProvider) {
        return new Dd(M(saveHandler, worldProvider));
    }
    
    public void saveChunk(final World world, final Chunk chunk) throws MinecraftException, IOException {
        world.checkSessionLock();
        final NBTTagCompound m = this.M(chunk, world);
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final String s = "X\u0004h\u0004J\u0000n\u0016u\nr";
        final NBTTagCompound nbtTagCompound2 = nbtTagCompound;
        nbtTagCompound2.setTag("Level", (NBTBase)m);
        nbtTagCompound2.setInteger(PC.M(s), VersionUtils.D());
        Dd.A.info("Calling addChunkToPending for the chunk at {} at time {}", (Object)chunk.getPos(), (Object)System.nanoTime());
        this.addChunkToPending(chunk.getPos(), nbtTagCompound);
    }
    
    public static boolean M(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            return false;
        }
        if (!iC.M(entity)) {
            Qd.M(ub.g, "com.client.glowclient.utils.mod.imports.wdl.messages.removeEntity.notSavingUserPreference", entity);
            return false;
        }
        return true;
    }
}
