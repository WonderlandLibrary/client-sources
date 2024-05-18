package net.minecraft.client.multiplayer;

import optfine.*;
import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.nbt.*;
import net.minecraft.client.particle.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.entity.*;
import net.minecraft.scoreboard.*;
import net.minecraft.profiler.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.item.*;
import net.minecraft.client.audio.*;
import net.minecraft.block.state.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class WorldClient extends World
{
    private BlockPosM randomTickPosM;
    private final Set entityList;
    private final Minecraft mc;
    private static final String __OBFID;
    private final Set entitySpawnQueue;
    private NetHandlerPlayClient sendQueue;
    private ChunkProviderClient clientChunkProvider;
    private final Set previousActiveChunkSet;
    private static final String[] I;
    
    @Override
    public void makeFireworks(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final NBTTagCompound nbtTagCompound) {
        this.mc.effectRenderer.addEffect(new EntityFirework.StarterFX(this, n, n2, n3, n4, n5, n6, this.mc.effectRenderer, nbtTagCompound));
    }
    
    static Set access$0(final WorldClient worldClient) {
        return worldClient.entityList;
    }
    
    @Override
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport crashReport) {
        final CrashReportCategory addWorldInfoToCrashReport = super.addWorldInfoToCrashReport(crashReport);
        addWorldInfoToCrashReport.addCrashSectionCallable(WorldClient.I[0x7D ^ 0x7A], new Callable(this) {
            final WorldClient this$0;
            private static final String[] I;
            private static final String __OBFID;
            
            static {
                I();
                __OBFID = WorldClient$1.I[" ".length()];
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
                    if (3 <= 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("N?\u001b6\u0011\u0002pT", "nKtBp");
                WorldClient$1.I[" ".length()] = I("\u0000\n1|Asv^tIp", "CFnLq");
            }
            
            @Override
            public String call() {
                return String.valueOf(WorldClient.access$0(this.this$0).size()) + WorldClient$1.I["".length()] + WorldClient.access$0(this.this$0).toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
        });
        addWorldInfoToCrashReport.addCrashSectionCallable(WorldClient.I[0x9D ^ 0x95], new Callable(this) {
            private static final String[] I;
            private static final String __OBFID;
            final WorldClient this$0;
            
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            static {
                I();
                __OBFID = WorldClient$2.I[" ".length()];
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() {
                return String.valueOf(WorldClient.access$1(this.this$0).size()) + WorldClient$2.I["".length()] + WorldClient.access$1(this.this$0).toString();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("u1\u00158\r9~Z", "UEzLl");
                WorldClient$2.I[" ".length()] = I("7:7s^DFX{V@", "tvhCn");
            }
        });
        addWorldInfoToCrashReport.addCrashSectionCallable(WorldClient.I[0x1C ^ 0x15], new Callable(this) {
            final WorldClient this$0;
            private static final String[] I;
            private static final String __OBFID;
            
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
                    if (1 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            static {
                I();
                __OBFID = WorldClient$3.I["".length()];
            }
            
            @Override
            public String call() throws Exception {
                return WorldClient.access$2(this.this$0).thePlayer.getClientBrand();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("\b\u00027]\\{~XUT~", "KNhml");
            }
        });
        addWorldInfoToCrashReport.addCrashSectionCallable(WorldClient.I[0xA8 ^ 0xA2], new Callable(this) {
            final WorldClient this$0;
            private static final String[] I;
            private static final String __OBFID;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            private static void I() {
                (I = new String["   ".length()])["".length()] = I("7\u001e B+\u0017\u0005+\b0\u0018\u0005+\u000bb\u0014\u0004\"\u001b+\t\u001d/\u0016'\u000bQ=\n0\u000f\u0014<", "yqNoB");
                WorldClient$4.I[" ".length()] = I("\u001a\u000f\u0017\n$!\u0000\u0017\n's\u0012\n\u0001$?\u0004\u0013\u0003\"*\u0004\u0011O06\u0013\u0015\n1", "SacoC");
                WorldClient$4.I["  ".length()] = I("\"\u0015\u000fQ]Qi`YUW", "aYPam");
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
                    if (1 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public String call() throws Exception {
                String s;
                if (WorldClient.access$2(this.this$0).getIntegratedServer() == null) {
                    s = WorldClient$4.I["".length()];
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
                else {
                    s = WorldClient$4.I[" ".length()];
                }
                return s;
            }
            
            static {
                I();
                __OBFID = WorldClient$4.I["  ".length()];
            }
        });
        return addWorldInfoToCrashReport;
    }
    
    @Override
    protected void onEntityRemoved(final Entity entity) {
        super.onEntityRemoved(entity);
        "".length();
        if (this.entityList.contains(entity)) {
            if (entity.isEntityAlive()) {
                this.entitySpawnQueue.add(entity);
                " ".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                this.entityList.remove(entity);
            }
        }
    }
    
    @Override
    public void playSound(final double n, final double n2, final double n3, final String s, final float n4, final float n5, final boolean b) {
        final double distanceSq = this.mc.getRenderViewEntity().getDistanceSq(n, n2, n3);
        final PositionedSoundRecord positionedSoundRecord = new PositionedSoundRecord(new ResourceLocation(s), n4, n5, (float)n, (float)n2, (float)n3);
        if (b && distanceSq > 100.0) {
            this.mc.getSoundHandler().playDelayedSound(positionedSoundRecord, (int)(Math.sqrt(distanceSq) / 40.0 * 20.0));
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            this.mc.getSoundHandler().playSound(positionedSoundRecord);
        }
    }
    
    static {
        I();
        __OBFID = WorldClient.I[0x8F ^ 0x80];
    }
    
    public void addEntityToWorld(final int entityId, final Entity entity) {
        final Entity entityByID = this.getEntityByID(entityId);
        if (entityByID != null) {
            this.removeEntity(entityByID);
        }
        this.entityList.add(entity);
        entity.setEntityId(entityId);
        if (!this.spawnEntityInWorld(entity)) {
            this.entitySpawnQueue.add(entity);
        }
        this.entitiesById.addKey(entityId, entity);
    }
    
    public Entity removeEntityFromWorld(final int n) {
        final Entity entity = this.entitiesById.removeObject(n);
        if (entity != null) {
            this.entityList.remove(entity);
            this.removeEntity(entity);
        }
        return entity;
    }
    
    @Override
    protected void updateBlocks() {
        super.updateBlocks();
        this.previousActiveChunkSet.retainAll(this.activeChunkSet);
        if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
            this.previousActiveChunkSet.clear();
        }
        int length = "".length();
        final Iterator<ChunkCoordIntPair> iterator = this.activeChunkSet.iterator();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final ChunkCoordIntPair chunkCoordIntPair = iterator.next();
            if (!this.previousActiveChunkSet.contains(chunkCoordIntPair)) {
                final int n = chunkCoordIntPair.chunkXPos * (0x99 ^ 0x89);
                final int n2 = chunkCoordIntPair.chunkZPos * (0x11 ^ 0x1);
                this.theProfiler.startSection(WorldClient.I[0xA8 ^ 0xAD]);
                this.playMoodSoundAndCheckLight(n, n2, this.getChunkFromChunkCoords(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos));
                this.theProfiler.endSection();
                this.previousActiveChunkSet.add(chunkCoordIntPair);
                if (++length >= (0xBD ^ 0xB7)) {
                    return;
                }
                continue;
            }
        }
    }
    
    @Override
    public Entity getEntityByID(final int n) {
        Entity entity;
        if (n == this.mc.thePlayer.getEntityId()) {
            entity = this.mc.thePlayer;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            entity = super.getEntityByID(n);
        }
        return entity;
    }
    
    public void setWorldScoreboard(final Scoreboard worldScoreboard) {
        this.worldScoreboard = worldScoreboard;
    }
    
    @Override
    protected void onEntityAdded(final Entity entity) {
        super.onEntityAdded(entity);
        if (this.entitySpawnQueue.contains(entity)) {
            this.entitySpawnQueue.remove(entity);
        }
    }
    
    public WorldClient(final NetHandlerPlayClient sendQueue, final WorldSettings worldSettings, final int n, final EnumDifficulty difficulty, final Profiler profiler) {
        super(new SaveHandlerMP(), new WorldInfo(worldSettings, WorldClient.I["".length()]), WorldProvider.getProviderForDimension(n), profiler, " ".length() != 0);
        this.entityList = Sets.newHashSet();
        this.entitySpawnQueue = Sets.newHashSet();
        this.mc = Minecraft.getMinecraft();
        this.previousActiveChunkSet = Sets.newHashSet();
        this.randomTickPosM = new BlockPosM("".length(), "".length(), "".length(), "   ".length());
        this.sendQueue = sendQueue;
        this.getWorldInfo().setDifficulty(difficulty);
        this.provider.registerWorld(this);
        this.setSpawnPoint(new BlockPos(0x9F ^ 0x97, 0x3 ^ 0x43, 0x93 ^ 0x9B));
        this.chunkProvider = this.createChunkProvider();
        this.mapStorage = new SaveDataMemoryStorage();
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
    }
    
    public void playSoundAtPos(final BlockPos blockPos, final String s, final float n, final float n2, final boolean b) {
        this.playSound(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, s, n, n2, b);
    }
    
    static Minecraft access$2(final WorldClient worldClient) {
        return worldClient.mc;
    }
    
    @Override
    public void setWorldTime(long worldTime) {
        if (worldTime < 0L) {
            worldTime = -worldTime;
            this.getGameRules().setOrCreateGameRule(WorldClient.I[0x59 ^ 0x52], WorldClient.I[0x45 ^ 0x49]);
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            this.getGameRules().setOrCreateGameRule(WorldClient.I[0xB7 ^ 0xBA], WorldClient.I[0x86 ^ 0x88]);
        }
        super.setWorldTime(worldTime);
    }
    
    private static void I() {
        (I = new String[0x3C ^ 0x2C])["".length()] = I("\"\u001a\u0001\u000b\u0007\u0019\u000f ", "ojRnu");
        WorldClient.I[" ".length()] = I("\u0005\u0016'\u0011\u001b\r\u0010\u0004\u0018\u0016\"\u0000\u0000\u001c\u0007", "aycpb");
        WorldClient.I["  ".length()] = I("( 5\u000b\u0003(< \u0017\u00189 \u0003\u0016\u001e4\"", "ZEpew");
        WorldClient.I["   ".length()] = I("\f!\u001d\u0017',(\u000b\u0011)", "oIhyL");
        WorldClient.I[0x67 ^ 0x63] = I(":\u0003-\u0019\u0018+", "XoBzs");
        WorldClient.I[0x2C ^ 0x29] = I("/(\u000e\u0002+=#\u0011", "HMzAC");
        WorldClient.I[0x22 ^ 0x24] = I("\b,\"%207,", "YYKQF");
        WorldClient.I[0xB5 ^ 0xB2] = I(".?\u001e-$\fp\t 5\u0001$\u0005+2", "hPlNA");
        WorldClient.I[0x7C ^ 0x74] = I(">=\u001f\u0003\u0018L=\u0005\u0005\b\u00181\u000e\u0002", "lXkqa");
        WorldClient.I[0x60 ^ 0x69] = I("\u0005*:>\u0013$o*:\u00178+", "VOHHv");
        WorldClient.I[0x9E ^ 0x94] = I("\u0018\n\u0007\u001a\u00159O\u0001\u0015\u0000.", "Koulp");
        WorldClient.I[0x8E ^ 0x85] = I("\t9)$\u0016\u0001?\n-\u001b./\u000e)\n", "mVmEo");
        WorldClient.I[0x3A ^ 0x36] = I("7\u00064\u000b\u001d", "QgXxx");
        WorldClient.I[0x65 ^ 0x68] = I("\u0006(&\t\u001a\u000e.\u0005\u0000\u0017!>\u0001\u0004\u0006", "bGbhc");
        WorldClient.I[0x5B ^ 0x55] = I("#'\u001b\b", "WUnmp");
        WorldClient.I[0x37 ^ 0x38] = I("9\u000f\u0013[tJs|S|H", "zCLkD");
    }
    
    @Override
    public boolean spawnEntityInWorld(final Entity entity) {
        final boolean spawnEntityInWorld = super.spawnEntityInWorld(entity);
        this.entityList.add(entity);
        if (!spawnEntityInWorld) {
            this.entitySpawnQueue.add(entity);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else if (entity instanceof EntityMinecart) {
            this.mc.getSoundHandler().playSound(new MovingSoundMinecart((EntityMinecart)entity));
        }
        return spawnEntityInWorld;
    }
    
    static Set access$1(final WorldClient worldClient) {
        return worldClient.entitySpawnQueue;
    }
    
    @Override
    public void sendQuittingDisconnectingPacket() {
        this.sendQueue.getNetworkManager().closeChannel(new ChatComponentText(WorldClient.I[0x1A ^ 0x1C]));
    }
    
    @Override
    protected int getRenderDistanceChunks() {
        return this.mc.gameSettings.renderDistanceChunks;
    }
    
    public boolean invalidateRegionAndSetBlock(final BlockPos blockPos, final IBlockState blockState) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        this.invalidateBlockReceiveRegion(x, y, z, x, y, z);
        return super.setBlockState(blockPos, blockState, "   ".length());
    }
    
    public void removeAllEntities() {
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        int i = "".length();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (i < this.unloadedEntityList.size()) {
            final Entity entity = this.unloadedEntityList.get(i);
            final int chunkCoordX = entity.chunkCoordX;
            final int chunkCoordZ = entity.chunkCoordZ;
            if (entity.addedToChunk && this.isChunkLoaded(chunkCoordX, chunkCoordZ, " ".length() != 0)) {
                this.getChunkFromChunkCoords(chunkCoordX, chunkCoordZ).removeEntity(entity);
            }
            ++i;
        }
        int j = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (j < this.unloadedEntityList.size()) {
            this.onEntityRemoved(this.unloadedEntityList.get(j));
            ++j;
        }
        this.unloadedEntityList.clear();
        int k = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (k < this.loadedEntityList.size()) {
            final Entity entity2 = this.loadedEntityList.get(k);
            Label_0325: {
                if (entity2.ridingEntity != null) {
                    if (!entity2.ridingEntity.isDead && entity2.ridingEntity.riddenByEntity == entity2) {
                        "".length();
                        if (3 == 1) {
                            throw null;
                        }
                        break Label_0325;
                    }
                    else {
                        entity2.ridingEntity.riddenByEntity = null;
                        entity2.ridingEntity = null;
                    }
                }
                if (entity2.isDead) {
                    final int chunkCoordX2 = entity2.chunkCoordX;
                    final int chunkCoordZ2 = entity2.chunkCoordZ;
                    if (entity2.addedToChunk && this.isChunkLoaded(chunkCoordX2, chunkCoordZ2, " ".length() != 0)) {
                        this.getChunkFromChunkCoords(chunkCoordX2, chunkCoordZ2).removeEntity(entity2);
                    }
                    this.loadedEntityList.remove(k--);
                    this.onEntityRemoved(entity2);
                }
            }
            ++k;
        }
    }
    
    @Override
    public void removeEntity(final Entity entity) {
        super.removeEntity(entity);
        this.entityList.remove(entity);
    }
    
    @Override
    protected void updateWeather() {
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        return this.clientChunkProvider = new ChunkProviderClient(this);
    }
    
    @Override
    public void tick() {
        super.tick();
        this.setTotalWorldTime(this.getTotalWorldTime() + 1L);
        if (this.getGameRules().getBoolean(WorldClient.I[" ".length()])) {
            this.setWorldTime(this.getWorldTime() + 1L);
        }
        this.theProfiler.startSection(WorldClient.I["  ".length()]);
        int length = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (length < (0x26 ^ 0x2C) && !this.entitySpawnQueue.isEmpty()) {
            final Entity entity = this.entitySpawnQueue.iterator().next();
            this.entitySpawnQueue.remove(entity);
            if (!this.loadedEntityList.contains(entity)) {
                this.spawnEntityInWorld(entity);
            }
            ++length;
        }
        this.theProfiler.endStartSection(WorldClient.I["   ".length()]);
        this.clientChunkProvider.unloadQueuedChunks();
        this.theProfiler.endStartSection(WorldClient.I[0xE ^ 0xA]);
        this.updateBlocks();
        this.theProfiler.endSection();
    }
    
    public void doVoidFogParticles(final int n, final int n2, final int n3) {
        final int n4 = 0x11 ^ 0x1;
        final Random random = new Random();
        final ItemStack heldItem = this.mc.thePlayer.getHeldItem();
        int n5;
        if (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE && heldItem != null && Block.getBlockFromItem(heldItem.getItem()) == Blocks.barrier) {
            n5 = " ".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        final int n6 = n5;
        final BlockPosM randomTickPosM = this.randomTickPosM;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < 401 + 6 - 371 + 964) {
            final int n7 = n + this.rand.nextInt(n4) - this.rand.nextInt(n4);
            final int n8 = n2 + this.rand.nextInt(n4) - this.rand.nextInt(n4);
            final int n9 = n3 + this.rand.nextInt(n4) - this.rand.nextInt(n4);
            randomTickPosM.setXyz(n7, n8, n9);
            final IBlockState blockState = this.getBlockState(randomTickPosM);
            blockState.getBlock().randomDisplayTick(this, randomTickPosM, blockState, random);
            if (n6 != 0 && blockState.getBlock() == Blocks.barrier) {
                this.spawnParticle(EnumParticleTypes.BARRIER, n7 + 0.5f, n8 + 0.5f, n9 + 0.5f, 0.0, 0.0, 0.0, new int["".length()]);
            }
            ++i;
        }
    }
    
    public void doPreChunk(final int n, final int n2, final boolean b) {
        if (b) {
            this.clientChunkProvider.loadChunk(n, n2);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            this.clientChunkProvider.unloadChunk(n, n2);
        }
        if (!b) {
            this.markBlockRangeForRenderUpdate(n * (0x12 ^ 0x2), "".length(), n2 * (0x6F ^ 0x7F), n * (0x3D ^ 0x2D) + (0x18 ^ 0x17), 55 + 197 - 206 + 210, n2 * (0x60 ^ 0x70) + (0x37 ^ 0x38));
        }
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void invalidateBlockReceiveRegion(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
}
