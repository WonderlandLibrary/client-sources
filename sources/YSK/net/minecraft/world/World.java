package net.minecraft.world;

import net.minecraft.world.border.*;
import net.minecraft.tileentity.*;
import net.minecraft.village.*;
import net.minecraft.scoreboard.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.player.*;
import net.minecraft.profiler.*;
import com.google.common.base.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.server.*;
import net.minecraft.crash.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.chunk.*;
import java.util.concurrent.*;
import net.minecraft.world.gen.structure.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public abstract class World implements IBlockAccess
{
    private final WorldBorder worldBorder;
    protected float thunderingStrength;
    protected WorldInfo worldInfo;
    public final List<Entity> loadedEntityList;
    protected int updateLCG;
    public final List<TileEntity> loadedTileEntityList;
    protected boolean spawnPeacefulMobs;
    protected float prevThunderingStrength;
    protected VillageCollection villageCollectionObj;
    private final List<TileEntity> tileEntitiesToBeRemoved;
    private long cloudColour;
    protected Scoreboard worldScoreboard;
    public final List<Entity> weatherEffects;
    private int field_181546_a;
    protected float prevRainingStrength;
    protected final ISaveHandler saveHandler;
    public final WorldProvider provider;
    private boolean processingLoadedTiles;
    protected float rainingStrength;
    protected MapStorage mapStorage;
    protected boolean spawnHostileMobs;
    public final Random rand;
    public final List<TileEntity> tickableTileEntities;
    protected final int DIST_HASH_MAGIC = 1013904223;
    protected IChunkProvider chunkProvider;
    protected List<IWorldAccess> worldAccesses;
    private int lastLightningBolt;
    public final List<EntityPlayer> playerEntities;
    private final Calendar theCalendar;
    private final List<TileEntity> addedTileEntityList;
    private int skylightSubtracted;
    int[] lightUpdateBlockList;
    public final boolean isRemote;
    public final Profiler theProfiler;
    protected final IntHashMap<Entity> entitiesById;
    protected Set<ChunkCoordIntPair> activeChunkSet;
    private static final String[] I;
    private int ambientTickCountdown;
    protected final List<Entity> unloadedEntityList;
    protected boolean findingSpawnPoint;
    protected boolean scheduledUpdatesAreImmediate;
    
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
        if (this.isValid(blockPos) && this.isBlockLoaded(blockPos)) {
            this.getChunkFromBlockCoords(blockPos).setLightFor(enumSkyBlock, blockPos, n);
            this.notifyLightSet(blockPos);
        }
    }
    
    public void setRainStrength(final float n) {
        this.prevRainingStrength = n;
        this.rainingStrength = n;
    }
    
    public float getCelestialAngleRadians(final float n) {
        return this.getCelestialAngle(n) * 3.1415927f * 2.0f;
    }
    
    private boolean isValid(final BlockPos blockPos) {
        if (blockPos.getX() >= -(3261721 + 11985110 + 2821975 + 11931194) && blockPos.getZ() >= -(229340 + 19759895 - 15875644 + 25886409) && blockPos.getX() < 26523802 + 7188240 - 12568606 + 8856564 && blockPos.getZ() < 13251992 + 25466547 - 24497161 + 15778622 && blockPos.getY() >= 0 && blockPos.getY() < 217 + 209 - 348 + 178) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return "".length() != 0;
    }
    
    public <T extends Entity> List<T> getEntitiesWithinAABB(final Class<? extends T> clazz, final AxisAlignedBB axisAlignedBB, final Predicate<? super T> predicate) {
        final int floor_double = MathHelper.floor_double((axisAlignedBB.minX - 2.0) / 16.0);
        final int floor_double2 = MathHelper.floor_double((axisAlignedBB.maxX + 2.0) / 16.0);
        final int floor_double3 = MathHelper.floor_double((axisAlignedBB.minZ - 2.0) / 16.0);
        final int floor_double4 = MathHelper.floor_double((axisAlignedBB.maxZ + 2.0) / 16.0);
        final ArrayList arrayList = Lists.newArrayList();
        int i = floor_double;
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i <= floor_double2) {
            int j = floor_double3;
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j <= floor_double4) {
                if (this.isChunkLoaded(i, j, " ".length() != 0)) {
                    this.getChunkFromChunkCoords(i, j).getEntitiesOfTypeWithinAAAB(clazz, axisAlignedBB, (List<Entity>)arrayList, (com.google.common.base.Predicate<? super Entity>)predicate);
                }
                ++j;
            }
            ++i;
        }
        return (List<T>)arrayList;
    }
    
    public int countEntities(final Class<?> clazz) {
        int length = "".length();
        final Iterator<Entity> iterator = this.loadedEntityList.iterator();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Entity entity = iterator.next();
            if ((!(entity instanceof EntityLiving) || !((EntityLiving)entity).isNoDespawnRequired()) && clazz.isAssignableFrom(((EntityLiving)entity).getClass())) {
                ++length;
            }
        }
        return length;
    }
    
    public void playSoundToNearExcept(final EntityPlayer entityPlayer, final String s, final float n, final float n2) {
        int i = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).playSoundToNearExcept(entityPlayer, s, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, n, n2);
            ++i;
        }
    }
    
    public String getProviderName() {
        return this.chunkProvider.makeString();
    }
    
    public int getChunksLowestHorizon(final int n, final int n2) {
        if (n < -(28205330 + 29082317 - 46334882 + 19047235) || n2 < -(17278138 + 3819906 + 3317185 + 5584771) || n >= 24411018 + 24988343 - 26279188 + 6879827 || n2 >= 253371 + 14817089 + 4750597 + 10178943) {
            return this.func_181545_F() + " ".length();
        }
        if (!this.isChunkLoaded(n >> (0xB8 ^ 0xBC), n2 >> (0x20 ^ 0x24), " ".length() != 0)) {
            return "".length();
        }
        return this.getChunkFromChunkCoords(n >> (0x1A ^ 0x1E), n2 >> (0x6F ^ 0x6B)).getLowestHeight();
    }
    
    public boolean isMaterialInBB(final AxisAlignedBB axisAlignedBB, final Material material) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = floor_double;
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < floor_double2) {
            int j = floor_double3;
            "".length();
            if (false) {
                throw null;
            }
            while (j < floor_double4) {
                int k = floor_double5;
                "".length();
                if (false) {
                    throw null;
                }
                while (k < floor_double6) {
                    if (this.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock().getMaterial() == material) {
                        return " ".length() != 0;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public void initialize(final WorldSettings worldSettings) {
        this.worldInfo.setServerInitialized(" ".length() != 0);
    }
    
    public Calendar getCurrentDate() {
        if (this.getTotalWorldTime() % 600L == 0L) {
            this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
        }
        return this.theCalendar;
    }
    
    public float getSunBrightness(final float n) {
        return (float)((float)((1.0f - MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.2f), 0.0f, 1.0f)) * (1.0 - this.getRainStrength(n) * 5.0f / 16.0)) * (1.0 - this.getThunderStrength(n) * 5.0f / 16.0)) * 0.8f + 0.2f;
    }
    
    public void markBlockForUpdate(final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).markBlockForUpdate(blockPos);
            ++i;
        }
    }
    
    public void playAuxSFXAtEntity(final EntityPlayer entityPlayer, final int n, final BlockPos blockPos, final int n2) {
        try {
            int i = "".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (i < this.worldAccesses.size()) {
                this.worldAccesses.get(i).playAuxSFX(entityPlayer, n, blockPos, n2);
                ++i;
            }
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, World.I[0xB1 ^ 0x91]);
            final CrashReportCategory category = crashReport.makeCategory(World.I[0x22 ^ 0x3]);
            category.addCrashSection(World.I[0x41 ^ 0x63], CrashReportCategory.getCoordinateInfo(blockPos));
            category.addCrashSection(World.I[0xB ^ 0x28], entityPlayer);
            category.addCrashSection(World.I[0x2A ^ 0xE], n);
            category.addCrashSection(World.I[0x2F ^ 0xA], n2);
            throw new ReportedException(crashReport);
        }
    }
    
    public List<Entity> getEntitiesWithinAABBExcludingEntity(final Entity entity, final AxisAlignedBB axisAlignedBB) {
        return this.getEntitiesInAABBexcluding(entity, axisAlignedBB, EntitySelectors.NOT_SPECTATING);
    }
    
    @Override
    public boolean isAirBlock(final BlockPos blockPos) {
        if (this.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getLightFromNeighborsFor(final EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (this.provider.getHasNoSky() && enumSkyBlock == EnumSkyBlock.SKY) {
            return "".length();
        }
        if (blockPos.getY() < 0) {
            blockPos = new BlockPos(blockPos.getX(), "".length(), blockPos.getZ());
        }
        if (!this.isValid(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }
        if (!this.isBlockLoaded(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }
        if (this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int light = this.getLightFor(enumSkyBlock, blockPos.up());
            final int light2 = this.getLightFor(enumSkyBlock, blockPos.east());
            final int light3 = this.getLightFor(enumSkyBlock, blockPos.west());
            final int light4 = this.getLightFor(enumSkyBlock, blockPos.south());
            final int light5 = this.getLightFor(enumSkyBlock, blockPos.north());
            if (light2 > light) {
                light = light2;
            }
            if (light3 > light) {
                light = light3;
            }
            if (light4 > light) {
                light = light4;
            }
            if (light5 > light) {
                light = light5;
            }
            return light;
        }
        return this.getChunkFromBlockCoords(blockPos).getLightFor(enumSkyBlock, blockPos);
    }
    
    public void addBlockEvent(final BlockPos blockPos, final Block block, final int n, final int n2) {
        block.onBlockEventReceived(this, blockPos, this.getBlockState(blockPos), n, n2);
    }
    
    private static void I() {
        (I = new String[0x85 ^ 0xA9])["".length()] = I("!).99\b+z/9\t!?", "fLZMP");
        World.I[" ".length()] = I("&\r\u00006\n\f\f\u000e0\u000b\u0016B\u0000\"N\u0007\u000b\u0000)\u000bE\u0010\n5\u001b\u0000\u0011\u001b", "eboDn");
        World.I["  ".length()] = I("5\u000b\u000e\n\u0019\u0010\u000b\u0003", "ydmkm");
        World.I["   ".length()] = I(")$?0*\u0006%=;5", "JLZSA");
        World.I[0x60 ^ 0x64] = I("+5-\u000f\u0001\u001a$!\u0004Q\u0019%'\u0006\u0014N8>\u000e\u0010\u001a$ \rQ\u0000('\r\u0019\f\";\u0018\u0002", "nMNjq");
        World.I[0x26 ^ 0x23] = I("0\u001b.0\u001aR\u0015$:\u001f\u0015W4#\u0015\u0013\u0003$7", "rwASq");
        World.I[0x5 ^ 0x3] = I("\u0017\u0001\u0019#\u0012!N\u000e=\u001e'\u0005L%\b4\u000b", "DnlQq");
        World.I[0x28 ^ 0x2F] = I("\u0016\n\u0017<\u0017\u001a\u0001\u0010", "sdcUc");
        World.I[0xA8 ^ 0xA0] = I("3\b9\u000628", "TdVdS");
        World.I[0xA2 ^ 0xAB] = I("\u0004;!\u000e\r>5b\u0000\n$;6\u001c", "PRBed");
        World.I[0x77 ^ 0x7D] = I("&\u0000\u0004\u001f\u0007\u001aN\u0012\u0013\u001a\r\tP\u0002\u001a\u0000\u0005\u0015\u0012", "cnpvs");
        World.I[0x74 ^ 0x7F] = I("4\u000f=\u0018\u001b\b", "qaIqo");
        World.I[0x14 ^ 0x18] = I("85?\u001d)\n5\u000f", "FKqHe");
        World.I[0x29 ^ 0x24] = I("6\b98\u0005!", "DmTWs");
        World.I[0x2F ^ 0x21] = I("\u001b5\f\u0010/\b\"", "iPkeC");
        World.I[0x4C ^ 0x43] = I("\u0001\u0011\u0004\u0003", "uxghW");
        World.I[0x5D ^ 0x4D] = I("\u0000,7\r;:\"t\u0003< , \u001f", "TETfR");
        World.I[0x57 ^ 0x46] = I("\u000f\u001f\u001d\u001a\u00013Q\u000b\u0016\u001c$\u0016I\u0007\u001c)\u001a\f\u0017", "Jqisu");
        World.I[0xA4 ^ 0xB6] = I("8\u0000#<\u001d/", "JeNSk");
        World.I[0x49 ^ 0x5A] = I("\u00079&\u000b! ;=\u0001>\f0:", "eUIhJ");
        World.I[0x53 ^ 0x47] = I("8-)-/\u0002#j$*\u0003'!f#\u00020#2?", "lDJFF");
        World.I[0x3 ^ 0x16] = I("3\u000e,'9Q\u0007-0;\u0005\u001bc&7\u0018\f$d&\u0018\u0001(!6", "qbCDR");
        World.I[0x28 ^ 0x3E] = I(" 1)\u0016>>3\u0005\u001e83?\u0002\u001c#9 .\u0017$", "PTGrW");
        World.I[0x8E ^ 0x99] = I("6-\r\u001f\u0018\u0016-\u001d\u0012\u0018", "UExqs");
        World.I[0x3C ^ 0x24] = I("$*\u000fSp", "eFciP");
        World.I[0x5E ^ 0x47] = I("31&*\u0011\u001d-<2", "QDOFu");
        World.I[0x7D ^ 0x67] = I("\u0004#\",4\u0006\f+02\u001f\u0003*29\u0000", "tOCUQ");
        World.I[0x38 ^ 0x23] = I("\u0017\u0004>\u0016\u001b\u0015\u001e?\u0016", "zkQrH");
        World.I[0xA2 ^ 0xBE] = I("+'1\u000e\u000b$>}\u0004\u000f</}\u0004\u000f</", "JJSgn");
        World.I[0xDF ^ 0xC2] = I("4&.\u0000\"\u001b',\u000b=", "WNKcI");
        World.I[0xA0 ^ 0xBE] = I("1,?\u000b4?.#=(3:8", "VIKIF");
        World.I[0x4E ^ 0x51] = I("\u0010\u0001\u000f\u000b \u0016\r:\u00078\u001a\u001d\u0003\u0007%SUJ\u001c$0\u0001\u000f\u000b 0\u0006\u001f\u0006?", "sijhK");
        World.I[0x68 ^ 0x48] = I("\u0007\u0019\u0007\b\u00059\u0012F\u001d\t!\u0010\nQ\t!\u0010\b\u0005", "Wufql");
        World.I[0x35 ^ 0x14] = I("\u0018\u0006\u0006\u0017\u001ct\u0006\u0006\u0017\u001e C\u0012\u0017\u0019:\u0004P\u0002\u001c5\u001a\u0015\u0016", "Tcprp");
        World.I[0x40 ^ 0x62] = I("7\u0016;62U\u0019;:+\u0011\u0013:4-\u0010\t", "uzTUY");
        World.I[0x16 ^ 0x35] = I("\u0011=4\u0019;t8>\u0002=7.", "TKQwO");
        World.I[0xB1 ^ 0x95] = I("\f\u001d\u0007\t\u001ci\u001f\u001b\u0017\r", "Ikbgh");
        World.I[0x6B ^ 0x4E] = I("\b3<\u000b5m!8\u0011 ", "MEYeA");
        World.I[0x3D ^ 0x1B] = I("$+\u0000\u0012&\u0011(\u0002W)\u0000;\u0003\u001b", "eMfwE");
        World.I[0x5F ^ 0x78] = I("\u000e\u000f#\u0016/b\u00044\u001e&", "BjUsC");
        World.I[0xA5 ^ 0x8D] = I("gG\\v", "XxcIP");
        World.I[0x13 ^ 0x3A] = I("\u0005+\bc#(&\u001d&!7", "DGdCS");
        World.I[0x1F ^ 0x35] = I("\u000f/##/l4\",0?", "LGVMD");
        World.I[0x8F ^ 0xA4] = I("\u0018\u0013\u0002\u0017\u0002t2\u0015\u0006\u000ft#\u001a\u001d\f \u0017\u001d\u001c\u000f6\u001a\u0011", "Tvtrn");
    }
    
    private int getRawLight(final BlockPos blockPos, final EnumSkyBlock enumSkyBlock) {
        if (enumSkyBlock == EnumSkyBlock.SKY && this.canSeeSky(blockPos)) {
            return 0xAE ^ 0xA1;
        }
        final Block block = this.getBlockState(blockPos).getBlock();
        int n;
        if (enumSkyBlock == EnumSkyBlock.SKY) {
            n = "".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else {
            n = block.getLightValue();
        }
        int n2 = n;
        int n3 = block.getLightOpacity();
        if (n3 >= (0x6D ^ 0x62) && block.getLightValue() > 0) {
            n3 = " ".length();
        }
        if (n3 < " ".length()) {
            n3 = " ".length();
        }
        if (n3 >= (0xB4 ^ 0xBB)) {
            return "".length();
        }
        if (n2 >= (0x42 ^ 0x4C)) {
            return n2;
        }
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < length) {
            final int n4 = this.getLightFor(enumSkyBlock, blockPos.offset(values[i])) - n3;
            if (n4 > n2) {
                n2 = n4;
            }
            if (n2 >= (0x4C ^ 0x42)) {
                return n2;
            }
            ++i;
        }
        return n2;
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final int n, final boolean b) {
        return this.isAreaLoaded(blockPos.getX() - n, blockPos.getY() - n, blockPos.getZ() - n, blockPos.getX() + n, blockPos.getY() + n, blockPos.getZ() + n, b);
    }
    
    public boolean canBlockFreezeNoWater(final BlockPos blockPos) {
        return this.canBlockFreeze(blockPos, " ".length() != 0);
    }
    
    public boolean canSeeSky(final BlockPos blockPos) {
        return this.getChunkFromBlockCoords(blockPos).canSeeSky(blockPos);
    }
    
    public boolean canBlockFreeze(final BlockPos blockPos, final boolean b) {
        if (this.getBiomeGenForCoords(blockPos).getFloatTemperature(blockPos) > 0.15f) {
            return "".length() != 0;
        }
        if (blockPos.getY() >= 0 && blockPos.getY() < 194 + 195 - 256 + 123 && this.getLightFor(EnumSkyBlock.BLOCK, blockPos) < (0xAA ^ 0xA0)) {
            final IBlockState blockState = this.getBlockState(blockPos);
            final Block block = blockState.getBlock();
            if ((block == Blocks.water || block == Blocks.flowing_water) && blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL) == 0) {
                if (!b) {
                    return " ".length() != 0;
                }
                int n;
                if (this.isWater(blockPos.west()) && this.isWater(blockPos.east()) && this.isWater(blockPos.north()) && this.isWater(blockPos.south())) {
                    n = " ".length();
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                if (n == 0) {
                    return " ".length() != 0;
                }
            }
        }
        return "".length() != 0;
    }
    
    public float getLightBrightness(final BlockPos blockPos) {
        return this.provider.getLightBrightnessTable()[this.getLightFromNeighbors(blockPos)];
    }
    
    public float getThunderStrength(final float n) {
        return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * n) * this.getRainStrength(n);
    }
    
    public boolean isAnyLiquid(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = floor_double;
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i <= floor_double2) {
            int j = floor_double3;
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (j <= floor_double4) {
                int k = floor_double5;
                "".length();
                if (1 == 2) {
                    throw null;
                }
                while (k <= floor_double6) {
                    if (this.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock().getMaterial().isLiquid()) {
                        return " ".length() != 0;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public boolean addWeatherEffect(final Entity entity) {
        this.weatherEffects.add(entity);
        return " ".length() != 0;
    }
    
    @Override
    public int getStrongPower(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.getBlockState(blockPos);
        return blockState.getBlock().getStrongPower(this, blockPos, blockState, enumFacing);
    }
    
    public void removeTileEntity(final BlockPos blockPos) {
        final TileEntity tileEntity = this.getTileEntity(blockPos);
        if (tileEntity != null && this.processingLoadedTiles) {
            tileEntity.invalidate();
            this.addedTileEntityList.remove(tileEntity);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            if (tileEntity != null) {
                this.addedTileEntityList.remove(tileEntity);
                this.loadedTileEntityList.remove(tileEntity);
                this.tickableTileEntities.remove(tileEntity);
            }
            this.getChunkFromBlockCoords(blockPos).removeTileEntity(blockPos);
        }
    }
    
    public void playBroadcastSound(final int n, final BlockPos blockPos, final int n2) {
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).broadcastSound(n, blockPos, n2);
            ++i;
        }
    }
    
    public void markTileEntityForRemoval(final TileEntity tileEntity) {
        this.tileEntitiesToBeRemoved.add(tileEntity);
    }
    
    public void markChunkDirty(final BlockPos blockPos, final TileEntity tileEntity) {
        if (this.isBlockLoaded(blockPos)) {
            this.getChunkFromBlockCoords(blockPos).setChunkModified();
        }
    }
    
    public int getSkylightSubtracted() {
        return this.skylightSubtracted;
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final BlockPos blockPos2, final boolean b) {
        return this.isAreaLoaded(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ(), b);
    }
    
    public BlockPos getTopSolidOrLiquidBlock(final BlockPos blockPos) {
        final Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        BlockPos blockPos2 = new BlockPos(blockPos.getX(), chunkFromBlockCoords.getTopFilledSegment() + (0x44 ^ 0x54), blockPos.getZ());
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (blockPos2.getY() >= 0) {
            final BlockPos down = blockPos2.down();
            final Material material = chunkFromBlockCoords.getBlock(down).getMaterial();
            if (material.blocksMovement() && material != Material.leaves) {
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                break;
            }
            else {
                blockPos2 = down;
            }
        }
        return blockPos2;
    }
    
    public void markBlockRangeForRenderUpdate(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        int i = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).markBlockRangeForRenderUpdate(n, n2, n3, n4, n5, n6);
            ++i;
        }
    }
    
    public boolean addTileEntity(final TileEntity tileEntity) {
        final boolean add = this.loadedTileEntityList.add(tileEntity);
        if (add && tileEntity instanceof ITickable) {
            this.tickableTileEntities.add(tileEntity);
        }
        return add;
    }
    
    public int getLightFromNeighbors(final BlockPos blockPos) {
        return this.getLight(blockPos, " ".length() != 0);
    }
    
    public WorldBorder getWorldBorder() {
        return this.worldBorder;
    }
    
    public boolean isSidePowered(final BlockPos blockPos, final EnumFacing enumFacing) {
        if (this.getRedstonePower(blockPos, enumFacing) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean setBlockToAir(final BlockPos blockPos) {
        return this.setBlockState(blockPos, Blocks.air.getDefaultState(), "   ".length());
    }
    
    public Vec3 getSkyColor(final Entity entity, final float n) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        final BlockPos blockPos = new BlockPos(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
        final BiomeGenBase biomeGenForCoords = this.getBiomeGenForCoords(blockPos);
        final int skyColorByTemp = biomeGenForCoords.getSkyColorByTemp(biomeGenForCoords.getFloatTemperature(blockPos));
        final float n2 = (skyColorByTemp >> (0xBA ^ 0xAA) & 189 + 11 - 1 + 56) / 255.0f;
        final float n3 = (skyColorByTemp >> (0x5D ^ 0x55) & 133 + 16 - 16 + 122) / 255.0f;
        final float n4 = (skyColorByTemp & 235 + 146 - 358 + 232) / 255.0f;
        float n5 = n2 * clamp_float;
        float n6 = n3 * clamp_float;
        float n7 = n4 * clamp_float;
        final float rainStrength = this.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n8 = (n5 * 0.3f + n6 * 0.59f + n7 * 0.11f) * 0.6f;
            final float n9 = 1.0f - rainStrength * 0.75f;
            n5 = n5 * n9 + n8 * (1.0f - n9);
            n6 = n6 * n9 + n8 * (1.0f - n9);
            n7 = n7 * n9 + n8 * (1.0f - n9);
        }
        final float thunderStrength = this.getThunderStrength(n);
        if (thunderStrength > 0.0f) {
            final float n10 = (n5 * 0.3f + n6 * 0.59f + n7 * 0.11f) * 0.2f;
            final float n11 = 1.0f - thunderStrength * 0.75f;
            n5 = n5 * n11 + n10 * (1.0f - n11);
            n6 = n6 * n11 + n10 * (1.0f - n11);
            n7 = n7 * n11 + n10 * (1.0f - n11);
        }
        if (this.lastLightningBolt > 0) {
            float n12 = this.lastLightningBolt - n;
            if (n12 > 1.0f) {
                n12 = 1.0f;
            }
            final float n13 = n12 * 0.45f;
            n5 = n5 * (1.0f - n13) + 0.8f * n13;
            n6 = n6 * (1.0f - n13) + 0.8f * n13;
            n7 = n7 * (1.0f - n13) + 1.0f * n13;
        }
        return new Vec3(n5, n6, n7);
    }
    
    public void forceBlockUpdateTick(final Block block, final BlockPos blockPos, final Random random) {
        this.scheduledUpdatesAreImmediate = (" ".length() != 0);
        block.updateTick(this, blockPos, this.getBlockState(blockPos), random);
        this.scheduledUpdatesAreImmediate = ("".length() != 0);
    }
    
    public Vec3 getFogColor(final float n) {
        return this.provider.getFogColor(this.getCelestialAngle(n), n);
    }
    
    public Vec3 getCloudColour(final float n) {
        final float clamp_float = MathHelper.clamp_float(MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f, 0.0f, 1.0f);
        float n2 = (this.cloudColour >> (0x1 ^ 0x11) & 0xFFL) / 255.0f;
        float n3 = (this.cloudColour >> (0x73 ^ 0x7B) & 0xFFL) / 255.0f;
        float n4 = (this.cloudColour & 0xFFL) / 255.0f;
        final float rainStrength = this.getRainStrength(n);
        if (rainStrength > 0.0f) {
            final float n5 = (n2 * 0.3f + n3 * 0.59f + n4 * 0.11f) * 0.6f;
            final float n6 = 1.0f - rainStrength * 0.95f;
            n2 = n2 * n6 + n5 * (1.0f - n6);
            n3 = n3 * n6 + n5 * (1.0f - n6);
            n4 = n4 * n6 + n5 * (1.0f - n6);
        }
        float n7 = n2 * (clamp_float * 0.9f + 0.1f);
        float n8 = n3 * (clamp_float * 0.9f + 0.1f);
        float n9 = n4 * (clamp_float * 0.85f + 0.15f);
        final float thunderStrength = this.getThunderStrength(n);
        if (thunderStrength > 0.0f) {
            final float n10 = (n7 * 0.3f + n8 * 0.59f + n9 * 0.11f) * 0.2f;
            final float n11 = 1.0f - thunderStrength * 0.95f;
            n7 = n7 * n11 + n10 * (1.0f - n11);
            n8 = n8 * n11 + n10 * (1.0f - n11);
            n9 = n9 * n11 + n10 * (1.0f - n11);
        }
        return new Vec3(n7, n8, n9);
    }
    
    public Entity getEntityByID(final int n) {
        return this.entitiesById.lookup(n);
    }
    
    public float getCelestialAngle(final float n) {
        return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), n);
    }
    
    public EntityPlayer getClosestPlayerToEntity(final Entity entity, final double n) {
        return this.getClosestPlayer(entity.posX, entity.posY, entity.posZ, n);
    }
    
    public List<Entity> getEntitiesInAABBexcluding(final Entity entity, final AxisAlignedBB axisAlignedBB, final Predicate<? super Entity> predicate) {
        final ArrayList arrayList = Lists.newArrayList();
        final int floor_double = MathHelper.floor_double((axisAlignedBB.minX - 2.0) / 16.0);
        final int floor_double2 = MathHelper.floor_double((axisAlignedBB.maxX + 2.0) / 16.0);
        final int floor_double3 = MathHelper.floor_double((axisAlignedBB.minZ - 2.0) / 16.0);
        final int floor_double4 = MathHelper.floor_double((axisAlignedBB.maxZ + 2.0) / 16.0);
        int i = floor_double;
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i <= floor_double2) {
            int j = floor_double3;
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (j <= floor_double4) {
                if (this.isChunkLoaded(i, j, " ".length() != 0)) {
                    this.getChunkFromChunkCoords(i, j).getEntitiesWithinAABBForEntity(entity, axisAlignedBB, arrayList, predicate);
                }
                ++j;
            }
            ++i;
        }
        return (List<Entity>)arrayList;
    }
    
    public void updateEntityWithOptionalForce(final Entity entity, final boolean b) {
        final int floor_double = MathHelper.floor_double(entity.posX);
        final int floor_double2 = MathHelper.floor_double(entity.posZ);
        final int n = 0x1C ^ 0x3C;
        if (!b || this.isAreaLoaded(floor_double - n, "".length(), floor_double2 - n, floor_double + n, "".length(), floor_double2 + n, " ".length() != 0)) {
            entity.lastTickPosX = entity.posX;
            entity.lastTickPosY = entity.posY;
            entity.lastTickPosZ = entity.posZ;
            entity.prevRotationYaw = entity.rotationYaw;
            entity.prevRotationPitch = entity.rotationPitch;
            if (b && entity.addedToChunk) {
                entity.ticksExisted += " ".length();
                if (entity.ridingEntity != null) {
                    entity.updateRidden();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    entity.onUpdate();
                }
            }
            this.theProfiler.startSection(World.I[0x83 ^ 0x94]);
            if (Double.isNaN(entity.posX) || Double.isInfinite(entity.posX)) {
                entity.posX = entity.lastTickPosX;
            }
            if (Double.isNaN(entity.posY) || Double.isInfinite(entity.posY)) {
                entity.posY = entity.lastTickPosY;
            }
            if (Double.isNaN(entity.posZ) || Double.isInfinite(entity.posZ)) {
                entity.posZ = entity.lastTickPosZ;
            }
            if (Double.isNaN(entity.rotationPitch) || Double.isInfinite(entity.rotationPitch)) {
                entity.rotationPitch = entity.prevRotationPitch;
            }
            if (Double.isNaN(entity.rotationYaw) || Double.isInfinite(entity.rotationYaw)) {
                entity.rotationYaw = entity.prevRotationYaw;
            }
            final int floor_double3 = MathHelper.floor_double(entity.posX / 16.0);
            final int floor_double4 = MathHelper.floor_double(entity.posY / 16.0);
            final int floor_double5 = MathHelper.floor_double(entity.posZ / 16.0);
            if (!entity.addedToChunk || entity.chunkCoordX != floor_double3 || entity.chunkCoordY != floor_double4 || entity.chunkCoordZ != floor_double5) {
                if (entity.addedToChunk && this.isChunkLoaded(entity.chunkCoordX, entity.chunkCoordZ, " ".length() != 0)) {
                    this.getChunkFromChunkCoords(entity.chunkCoordX, entity.chunkCoordZ).removeEntityAtIndex(entity, entity.chunkCoordY);
                }
                if (this.isChunkLoaded(floor_double3, floor_double5, " ".length() != 0)) {
                    entity.addedToChunk = (" ".length() != 0);
                    this.getChunkFromChunkCoords(floor_double3, floor_double5).addEntity(entity);
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
                else {
                    entity.addedToChunk = ("".length() != 0);
                }
            }
            this.theProfiler.endSection();
            if (b && entity.addedToChunk && entity.riddenByEntity != null) {
                if (!entity.riddenByEntity.isDead && entity.riddenByEntity.ridingEntity == entity) {
                    this.updateEntity(entity.riddenByEntity);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    entity.riddenByEntity.ridingEntity = null;
                    entity.riddenByEntity = null;
                }
            }
        }
    }
    
    protected void updateWeather() {
        if (!this.provider.getHasNoSky() && !this.isRemote) {
            int cleanWeatherTime = this.worldInfo.getCleanWeatherTime();
            if (cleanWeatherTime > 0) {
                --cleanWeatherTime;
                this.worldInfo.setCleanWeatherTime(cleanWeatherTime);
                final WorldInfo worldInfo = this.worldInfo;
                int thunderTime;
                if (this.worldInfo.isThundering()) {
                    thunderTime = " ".length();
                    "".length();
                    if (4 == 3) {
                        throw null;
                    }
                }
                else {
                    thunderTime = "  ".length();
                }
                worldInfo.setThunderTime(thunderTime);
                final WorldInfo worldInfo2 = this.worldInfo;
                int rainTime;
                if (this.worldInfo.isRaining()) {
                    rainTime = " ".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else {
                    rainTime = "  ".length();
                }
                worldInfo2.setRainTime(rainTime);
            }
            int thunderTime2 = this.worldInfo.getThunderTime();
            if (thunderTime2 <= 0) {
                if (this.worldInfo.isThundering()) {
                    this.worldInfo.setThunderTime(this.rand.nextInt(6828 + 9441 - 14638 + 10369) + (54 + 1176 - 432 + 2802));
                    "".length();
                    if (0 < 0) {
                        throw null;
                    }
                }
                else {
                    this.worldInfo.setThunderTime(this.rand.nextInt(157288 + 28090 - 132241 + 114863) + (2630 + 5149 - 6492 + 10713));
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                }
            }
            else {
                --thunderTime2;
                this.worldInfo.setThunderTime(thunderTime2);
                if (thunderTime2 <= 0) {
                    final WorldInfo worldInfo3 = this.worldInfo;
                    int thundering;
                    if (this.worldInfo.isThundering()) {
                        thundering = "".length();
                        "".length();
                        if (4 < -1) {
                            throw null;
                        }
                    }
                    else {
                        thundering = " ".length();
                    }
                    worldInfo3.setThundering(thundering != 0);
                }
            }
            this.prevThunderingStrength = this.thunderingStrength;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength += 0.01;
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            else {
                this.thunderingStrength -= 0.01;
            }
            this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0f, 1.0f);
            int rainTime2 = this.worldInfo.getRainTime();
            if (rainTime2 <= 0) {
                if (this.worldInfo.isRaining()) {
                    this.worldInfo.setRainTime(this.rand.nextInt(9828 + 4575 - 12415 + 10012) + (11057 + 2394 - 2503 + 1052));
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
                else {
                    this.worldInfo.setRainTime(this.rand.nextInt(54847 + 71975 - 17660 + 58838) + (4308 + 3247 - 5045 + 9490));
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
            }
            else {
                --rainTime2;
                this.worldInfo.setRainTime(rainTime2);
                if (rainTime2 <= 0) {
                    final WorldInfo worldInfo4 = this.worldInfo;
                    int raining;
                    if (this.worldInfo.isRaining()) {
                        raining = "".length();
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                    }
                    else {
                        raining = " ".length();
                    }
                    worldInfo4.setRaining(raining != 0);
                }
            }
            this.prevRainingStrength = this.rainingStrength;
            if (this.worldInfo.isRaining()) {
                this.rainingStrength += 0.01;
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
            else {
                this.rainingStrength -= 0.01;
            }
            this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0f, 1.0f);
        }
    }
    
    public Block getGroundAboveSeaLevel(final BlockPos blockPos) {
        BlockPos up = new BlockPos(blockPos.getX(), this.func_181545_F(), blockPos.getZ());
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (!this.isAirBlock(up.up())) {
            up = up.up();
        }
        return this.getBlockState(up).getBlock();
    }
    
    public BlockPos getPrecipitationHeight(final BlockPos blockPos) {
        return this.getChunkFromBlockCoords(blockPos).getPrecipitationHeight(blockPos);
    }
    
    public void removePlayerEntityDangerously(final Entity entity) {
        entity.setDead();
        if (entity instanceof EntityPlayer) {
            this.playerEntities.remove(entity);
            this.updateAllPlayersSleepingFlag();
        }
        final int chunkCoordX = entity.chunkCoordX;
        final int chunkCoordZ = entity.chunkCoordZ;
        if (entity.addedToChunk && this.isChunkLoaded(chunkCoordX, chunkCoordZ, " ".length() != 0)) {
            this.getChunkFromChunkCoords(chunkCoordX, chunkCoordZ).removeEntity(entity);
        }
        this.loadedEntityList.remove(entity);
        this.onEntityRemoved(entity);
    }
    
    public boolean canBlockSeeSky(final BlockPos blockPos) {
        if (blockPos.getY() >= this.func_181545_F()) {
            return this.canSeeSky(blockPos);
        }
        final BlockPos blockPos2 = new BlockPos(blockPos.getX(), this.func_181545_F(), blockPos.getZ());
        if (!this.canSeeSky(blockPos2)) {
            return "".length() != 0;
        }
        BlockPos blockPos3 = blockPos2.down();
        "".length();
        if (true != true) {
            throw null;
        }
        while (blockPos3.getY() > blockPos.getY()) {
            final Block block = this.getBlockState(blockPos3).getBlock();
            if (block.getLightOpacity() > 0 && !block.getMaterial().isLiquid()) {
                return "".length() != 0;
            }
            blockPos3 = blockPos3.down();
        }
        return " ".length() != 0;
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final BlockPos blockPos2) {
        return this.isAreaLoaded(blockPos, blockPos2, " ".length() != 0);
    }
    
    public BlockPos getHeight(final BlockPos blockPos) {
        int n;
        if (blockPos.getX() >= -(16225633 + 13982427 - 5833355 + 5625295) && blockPos.getZ() >= -(26183337 + 21658932 - 32325956 + 14483687) && blockPos.getX() < 23180030 + 1897897 + 4461829 + 460244 && blockPos.getZ() < 17230605 + 6253391 - 17844481 + 24360485) {
            if (this.isChunkLoaded(blockPos.getX() >> (0x3E ^ 0x3A), blockPos.getZ() >> (0x28 ^ 0x2C), " ".length() != 0)) {
                n = this.getChunkFromChunkCoords(blockPos.getX() >> (0x45 ^ 0x41), blockPos.getZ() >> (0x8 ^ 0xC)).getHeightValue(blockPos.getX() & (0x9F ^ 0x90), blockPos.getZ() & (0x91 ^ 0x9E));
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
        }
        else {
            n = this.func_181545_F() + " ".length();
        }
        return new BlockPos(blockPos.getX(), n, blockPos.getZ());
    }
    
    public int getActualHeight() {
        int n;
        if (this.provider.getHasNoSky()) {
            n = 76 + 111 - 94 + 35;
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            n = 223 + 127 - 215 + 121;
        }
        return n;
    }
    
    public boolean isInsideBorder(final WorldBorder worldBorder, final Entity entity) {
        final double minX = worldBorder.minX();
        final double minZ = worldBorder.minZ();
        final double maxX = worldBorder.maxX();
        final double maxZ = worldBorder.maxZ();
        double n;
        double n2;
        double n3;
        double n4;
        if (entity.isOutsideBorder()) {
            n = minX + 1.0;
            n2 = minZ + 1.0;
            n3 = maxX - 1.0;
            n4 = maxZ - 1.0;
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            n = minX - 1.0;
            n2 = minZ - 1.0;
            n3 = maxX + 1.0;
            n4 = maxZ + 1.0;
        }
        if (entity.posX > n && entity.posX < n3 && entity.posZ > n2 && entity.posZ < n4) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void playSoundEffect(final double n, final double n2, final double n3, final String s, final float n4, final float n5) {
        int i = "".length();
        "".length();
        if (-1 == 2) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).playSound(s, n, n2, n3, n4, n5);
            ++i;
        }
    }
    
    public boolean canSnowAt(final BlockPos blockPos, final boolean b) {
        if (this.getBiomeGenForCoords(blockPos).getFloatTemperature(blockPos) > 0.15f) {
            return "".length() != 0;
        }
        if (!b) {
            return " ".length() != 0;
        }
        if (blockPos.getY() >= 0 && blockPos.getY() < 167 + 222 - 227 + 94 && this.getLightFor(EnumSkyBlock.BLOCK, blockPos) < (0x7D ^ 0x77) && this.getBlockState(blockPos).getBlock().getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this, blockPos)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public <T extends Entity> List<T> getEntitiesWithinAABB(final Class<? extends T> clazz, final AxisAlignedBB axisAlignedBB) {
        return this.getEntitiesWithinAABB(clazz, axisAlignedBB, (com.google.common.base.Predicate<? super T>)EntitySelectors.NOT_SPECTATING);
    }
    
    public <T extends Entity> T findNearestEntityWithinAABB(final Class<? extends T> clazz, final AxisAlignedBB axisAlignedBB, final T t) {
        final List<Entity> entitiesWithinAABB = this.getEntitiesWithinAABB((Class<? extends Entity>)clazz, axisAlignedBB);
        Entity entity = null;
        double n = Double.MAX_VALUE;
        int i = "".length();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (i < entitiesWithinAABB.size()) {
            final Entity entity2 = entitiesWithinAABB.get(i);
            if (entity2 != t && EntitySelectors.NOT_SPECTATING.apply((Object)entity2)) {
                final double distanceSqToEntity = t.getDistanceSqToEntity(entity2);
                if (distanceSqToEntity <= n) {
                    entity = entity2;
                    n = distanceSqToEntity;
                }
            }
            ++i;
        }
        return (T)entity;
    }
    
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport crashReport) {
        final CrashReportCategory categoryDepth;
        final CrashReportCategory crashReportCategory = categoryDepth = crashReport.makeCategoryDepth(World.I[0xE2 ^ 0xC4], " ".length());
        final String s = World.I[0x9 ^ 0x2E];
        String worldName;
        if (this.worldInfo == null) {
            worldName = World.I[0x66 ^ 0x4E];
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            worldName = this.worldInfo.getWorldName();
        }
        categoryDepth.addCrashSection(s, worldName);
        crashReportCategory.addCrashSectionCallable(World.I[0x9D ^ 0xB4], new Callable<String>(this) {
            private static final String[] I;
            final World this$0;
            
            static {
                I();
            }
            
            private static void I() {
                (I = new String[" ".length()])["".length()] = I("k\u0013.\u0013\u0000'\\a", "KgAga");
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() {
                return String.valueOf(this.this$0.playerEntities.size()) + World$3.I["".length()] + this.this$0.playerEntities.toString();
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
                    if (2 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        crashReportCategory.addCrashSectionCallable(World.I[0x89 ^ 0xA3], new Callable<String>(this) {
            final World this$0;
            
            @Override
            public String call() {
                return this.this$0.chunkProvider.makeString();
            }
            
            @Override
            public Object call() throws Exception {
                return this.call();
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
                    if (1 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        try {
            this.worldInfo.addToCrashReport(crashReportCategory);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        catch (Throwable t) {
            crashReportCategory.addCrashSectionThrowable(World.I[0x62 ^ 0x49], t);
        }
        return crashReportCategory;
    }
    
    public int getHeight() {
        return 227 + 225 - 405 + 209;
    }
    
    public boolean checkLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        if (!this.isAreaLoaded(blockPos, 0x8A ^ 0x9B, "".length() != 0)) {
            return "".length() != 0;
        }
        int i = "".length();
        int length = "".length();
        this.theProfiler.startSection(World.I[0x2A ^ 0x34]);
        final int light = this.getLightFor(enumSkyBlock, blockPos);
        final int rawLight = this.getRawLight(blockPos, enumSkyBlock);
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        if (rawLight > light) {
            this.lightUpdateBlockList[length++] = 16224 + 7631 + 101199 + 8098;
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (rawLight < light) {
            this.lightUpdateBlockList[length++] = (59902 + 24805 - 75867 + 124312 | light << (0xD ^ 0x1F));
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (i < length) {
                final int n = this.lightUpdateBlockList[i++];
                final int n2 = (n & (0x2D ^ 0x12)) - (0xE0 ^ 0xC0) + x;
                final int n3 = (n >> (0x8D ^ 0x8B) & (0x79 ^ 0x46)) - (0x31 ^ 0x11) + y;
                final int n4 = (n >> (0x35 ^ 0x39) & (0x14 ^ 0x2B)) - (0x2F ^ 0xF) + z;
                final int n5 = n >> (0x74 ^ 0x66) & (0x6F ^ 0x60);
                final BlockPos blockPos2 = new BlockPos(n2, n3, n4);
                if (this.getLightFor(enumSkyBlock, blockPos2) == n5) {
                    this.setLightFor(enumSkyBlock, blockPos2, "".length());
                    if (n5 <= 0 || MathHelper.abs_int(n2 - x) + MathHelper.abs_int(n3 - y) + MathHelper.abs_int(n4 - z) >= (0x48 ^ 0x59)) {
                        continue;
                    }
                    final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                    final EnumFacing[] values;
                    final int length2 = (values = EnumFacing.values()).length;
                    int j = "".length();
                    "".length();
                    if (2 == 1) {
                        throw null;
                    }
                    while (j < length2) {
                        final EnumFacing enumFacing = values[j];
                        final int n6 = n2 + enumFacing.getFrontOffsetX();
                        final int n7 = n3 + enumFacing.getFrontOffsetY();
                        final int n8 = n4 + enumFacing.getFrontOffsetZ();
                        mutableBlockPos.func_181079_c(n6, n7, n8);
                        final int max = Math.max(" ".length(), this.getBlockState(mutableBlockPos).getBlock().getLightOpacity());
                        if (this.getLightFor(enumSkyBlock, mutableBlockPos) == n5 - max && length < this.lightUpdateBlockList.length) {
                            this.lightUpdateBlockList[length++] = (n6 - x + (0x74 ^ 0x54) | n7 - y + (0xAC ^ 0x8C) << (0x20 ^ 0x26) | n8 - z + (0xA8 ^ 0x88) << (0x7B ^ 0x77) | n5 - max << (0x52 ^ 0x40));
                        }
                        ++j;
                    }
                }
            }
            i = "".length();
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection(World.I[0xA2 ^ 0xBD]);
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (i < length) {
            final int n9 = this.lightUpdateBlockList[i++];
            final int n10 = (n9 & (0x55 ^ 0x6A)) - (0xB5 ^ 0x95) + x;
            final int n11 = (n9 >> (0xC4 ^ 0xC2) & (0x42 ^ 0x7D)) - (0x38 ^ 0x18) + y;
            final int n12 = (n9 >> (0xBE ^ 0xB2) & (0x92 ^ 0xAD)) - (0x14 ^ 0x34) + z;
            final BlockPos blockPos3 = new BlockPos(n10, n11, n12);
            final int light2 = this.getLightFor(enumSkyBlock, blockPos3);
            final int rawLight2 = this.getRawLight(blockPos3, enumSkyBlock);
            if (rawLight2 != light2) {
                this.setLightFor(enumSkyBlock, blockPos3, rawLight2);
                if (rawLight2 <= light2) {
                    continue;
                }
                final int abs = Math.abs(n10 - x);
                final int abs2 = Math.abs(n11 - y);
                final int abs3 = Math.abs(n12 - z);
                int n13;
                if (length < this.lightUpdateBlockList.length - (0x49 ^ 0x4F)) {
                    n13 = " ".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n13 = "".length();
                }
                final int n14 = n13;
                if (abs + abs2 + abs3 >= (0x73 ^ 0x62) || n14 == 0) {
                    continue;
                }
                if (this.getLightFor(enumSkyBlock, blockPos3.west()) < rawLight2) {
                    this.lightUpdateBlockList[length++] = n10 - " ".length() - x + (0xAE ^ 0x8E) + (n11 - y + (0x71 ^ 0x51) << (0x58 ^ 0x5E)) + (n12 - z + (0x51 ^ 0x71) << (0x65 ^ 0x69));
                }
                if (this.getLightFor(enumSkyBlock, blockPos3.east()) < rawLight2) {
                    this.lightUpdateBlockList[length++] = n10 + " ".length() - x + (0x8E ^ 0xAE) + (n11 - y + (0x9C ^ 0xBC) << (0x67 ^ 0x61)) + (n12 - z + (0x1E ^ 0x3E) << (0x2B ^ 0x27));
                }
                if (this.getLightFor(enumSkyBlock, blockPos3.down()) < rawLight2) {
                    this.lightUpdateBlockList[length++] = n10 - x + (0x46 ^ 0x66) + (n11 - " ".length() - y + (0x56 ^ 0x76) << (0x4D ^ 0x4B)) + (n12 - z + (0xB3 ^ 0x93) << (0x8A ^ 0x86));
                }
                if (this.getLightFor(enumSkyBlock, blockPos3.up()) < rawLight2) {
                    this.lightUpdateBlockList[length++] = n10 - x + (0x57 ^ 0x77) + (n11 + " ".length() - y + (0xBB ^ 0x9B) << (0x10 ^ 0x16)) + (n12 - z + (0x47 ^ 0x67) << (0xBF ^ 0xB3));
                }
                if (this.getLightFor(enumSkyBlock, blockPos3.north()) < rawLight2) {
                    this.lightUpdateBlockList[length++] = n10 - x + (0x34 ^ 0x14) + (n11 - y + (0x70 ^ 0x50) << (0x9B ^ 0x9D)) + (n12 - " ".length() - z + (0x6C ^ 0x4C) << (0x72 ^ 0x7E));
                }
                if (this.getLightFor(enumSkyBlock, blockPos3.south()) >= rawLight2) {
                    continue;
                }
                this.lightUpdateBlockList[length++] = n10 - x + (0x76 ^ 0x56) + (n11 - y + (0x62 ^ 0x42) << (0x7B ^ 0x7D)) + (n12 + " ".length() - z + (0x60 ^ 0x40) << (0xA7 ^ 0xAB));
            }
        }
        this.theProfiler.endSection();
        return " ".length() != 0;
    }
    
    public int getMoonPhase() {
        return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
    }
    
    public int getStrongPower(final BlockPos blockPos) {
        final int max = Math.max("".length(), this.getStrongPower(blockPos.down(), EnumFacing.DOWN));
        if (max >= (0x85 ^ 0x8A)) {
            return max;
        }
        final int max2 = Math.max(max, this.getStrongPower(blockPos.up(), EnumFacing.UP));
        if (max2 >= (0x8B ^ 0x84)) {
            return max2;
        }
        final int max3 = Math.max(max2, this.getStrongPower(blockPos.north(), EnumFacing.NORTH));
        if (max3 >= (0xB3 ^ 0xBC)) {
            return max3;
        }
        final int max4 = Math.max(max3, this.getStrongPower(blockPos.south(), EnumFacing.SOUTH));
        if (max4 >= (0x15 ^ 0x1A)) {
            return max4;
        }
        final int max5 = Math.max(max4, this.getStrongPower(blockPos.west(), EnumFacing.WEST));
        if (max5 >= (0x1F ^ 0x10)) {
            return max5;
        }
        final int max6 = Math.max(max5, this.getStrongPower(blockPos.east(), EnumFacing.EAST));
        int n;
        if (max6 >= (0x93 ^ 0x9C)) {
            n = max6;
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n = max6;
        }
        return n;
    }
    
    public void notifyBlockOfStateChange(final BlockPos blockPos, final Block block) {
        if (!this.isRemote) {
            final IBlockState blockState = this.getBlockState(blockPos);
            try {
                blockState.getBlock().onNeighborBlockChange(this, blockPos, blockState, block);
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, World.I[0x3 ^ 0x7]);
                final CrashReportCategory category = crashReport.makeCategory(World.I[0x52 ^ 0x57]);
                category.addCrashSectionCallable(World.I[0x12 ^ 0x14], new Callable<String>(this, block) {
                    final World this$0;
                    private static final String[] I;
                    private final Block val$blockIn;
                    
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
                            if (2 >= 3) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    static {
                        I();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        try {
                            final String s = World$2.I["".length()];
                            final Object[] array = new Object["   ".length()];
                            array["".length()] = Block.getIdFromBlock(this.val$blockIn);
                            array[" ".length()] = this.val$blockIn.getUnlocalizedName();
                            array["  ".length()] = this.val$blockIn.getClass().getCanonicalName();
                            return String.format(s, array);
                        }
                        catch (Throwable t) {
                            return World$2.I[" ".length()] + Block.getIdFromBlock(this.val$blockIn);
                        }
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                    
                    private static void I() {
                        (I = new String["  ".length()])["".length()] = I("\u001a/QTI7KYR\u001fsD^WI B", "Skqwl");
                        World$2.I[" ".length()] = I("\u0018\u0013ns", "QWNPY");
                    }
                });
                CrashReportCategory.addBlockInfo(category, blockPos, blockState);
                throw new ReportedException(crashReport);
            }
        }
    }
    
    public void setSkylightSubtracted(final int skylightSubtracted) {
        this.skylightSubtracted = skylightSubtracted;
    }
    
    public EntityPlayer getClosestPlayer(final double n, final double n2, final double n3, final double n4) {
        double n5 = -1.0;
        EntityPlayer entityPlayer = null;
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < this.playerEntities.size()) {
            final EntityPlayer entityPlayer2 = this.playerEntities.get(i);
            if (EntitySelectors.NOT_SPECTATING.apply((Object)entityPlayer2)) {
                final double distanceSq = entityPlayer2.getDistanceSq(n, n2, n3);
                if ((n4 < 0.0 || distanceSq < n4 * n4) && (n5 == -1.0 || distanceSq < n5)) {
                    n5 = distanceSq;
                    entityPlayer = entityPlayer2;
                }
            }
            ++i;
        }
        return entityPlayer;
    }
    
    public long getSeed() {
        return this.worldInfo.getSeed();
    }
    
    public int func_181545_F() {
        return this.field_181546_a;
    }
    
    protected void setActivePlayerChunksAndCheckLight() {
        this.activeChunkSet.clear();
        this.theProfiler.startSection(World.I[0xB3 ^ 0xAA]);
        int i = "".length();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (i < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(i);
            final int floor_double = MathHelper.floor_double(entityPlayer.posX / 16.0);
            final int floor_double2 = MathHelper.floor_double(entityPlayer.posZ / 16.0);
            final int renderDistanceChunks = this.getRenderDistanceChunks();
            int j = -renderDistanceChunks;
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (j <= renderDistanceChunks) {
                int k = -renderDistanceChunks;
                "".length();
                if (2 == -1) {
                    throw null;
                }
                while (k <= renderDistanceChunks) {
                    this.activeChunkSet.add(new ChunkCoordIntPair(j + floor_double, k + floor_double2));
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        this.theProfiler.endSection();
        if (this.ambientTickCountdown > 0) {
            this.ambientTickCountdown -= " ".length();
        }
        this.theProfiler.startSection(World.I[0x25 ^ 0x3F]);
        if (!this.playerEntities.isEmpty()) {
            final EntityPlayer entityPlayer2 = this.playerEntities.get(this.rand.nextInt(this.playerEntities.size()));
            this.checkLight(new BlockPos(MathHelper.floor_double(entityPlayer2.posX) + this.rand.nextInt(0xBB ^ 0xB0) - (0x67 ^ 0x62), MathHelper.floor_double(entityPlayer2.posY) + this.rand.nextInt(0x65 ^ 0x6E) - (0x2C ^ 0x29), MathHelper.floor_double(entityPlayer2.posZ) + this.rand.nextInt(0xE ^ 0x5) - (0xC3 ^ 0xC6)));
        }
        this.theProfiler.endSection();
    }
    
    public void updateEntity(final Entity entity) {
        this.updateEntityWithOptionalForce(entity, " ".length() != 0);
    }
    
    public void notifyLightSet(final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).notifyLightSet(blockPos);
            ++i;
        }
    }
    
    public boolean setBlockState(final BlockPos blockPos, final IBlockState blockState, final int n) {
        if (!this.isValid(blockPos)) {
            return "".length() != 0;
        }
        if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return "".length() != 0;
        }
        final Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        final Block block = blockState.getBlock();
        final IBlockState setBlockState = chunkFromBlockCoords.setBlockState(blockPos, blockState);
        if (setBlockState == null) {
            return "".length() != 0;
        }
        final Block block2 = setBlockState.getBlock();
        if (block.getLightOpacity() != block2.getLightOpacity() || block.getLightValue() != block2.getLightValue()) {
            this.theProfiler.startSection(World.I["   ".length()]);
            this.checkLight(blockPos);
            this.theProfiler.endSection();
        }
        if ((n & "  ".length()) != 0x0 && (!this.isRemote || (n & (0x2B ^ 0x2F)) == 0x0) && chunkFromBlockCoords.isPopulated()) {
            this.markBlockForUpdate(blockPos);
        }
        if (!this.isRemote && (n & " ".length()) != 0x0) {
            this.notifyNeighborsRespectDebug(blockPos, setBlockState.getBlock());
            if (block.hasComparatorInputOverride()) {
                this.updateComparatorOutputLevel(blockPos, block);
            }
        }
        return " ".length() != 0;
    }
    
    protected abstract int getRenderDistanceChunks();
    
    public void setTileEntity(final BlockPos pos, final TileEntity tileEntity) {
        if (tileEntity != null && !tileEntity.isInvalid()) {
            if (this.processingLoadedTiles) {
                tileEntity.setPos(pos);
                final Iterator<TileEntity> iterator = this.addedTileEntityList.iterator();
                "".length();
                if (false) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final TileEntity tileEntity2 = iterator.next();
                    if (tileEntity2.getPos().equals(pos)) {
                        tileEntity2.invalidate();
                        iterator.remove();
                    }
                }
                this.addedTileEntityList.add(tileEntity);
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
            else {
                this.addTileEntity(tileEntity);
                this.getChunkFromBlockCoords(pos).addTileEntity(pos, tileEntity);
            }
        }
    }
    
    public boolean isBlockModifiable(final EntityPlayer entityPlayer, final BlockPos blockPos) {
        return " ".length() != 0;
    }
    
    public boolean isRaining() {
        if (this.getRainStrength(1.0f) > 0.2) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getLight(BlockPos blockPos, final boolean b) {
        if (blockPos.getX() < -(3578349 + 2726831 + 15218350 + 8476470) || blockPos.getZ() < -(22068621 + 2740714 - 15874592 + 21065257) || blockPos.getX() >= 10925897 + 29370233 - 29394054 + 19097924 || blockPos.getZ() >= 28106783 + 18347867 - 31791642 + 15336992) {
            return 0x99 ^ 0x96;
        }
        if (b && this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int light = this.getLight(blockPos.up(), "".length() != 0);
            final int light2 = this.getLight(blockPos.east(), "".length() != 0);
            final int light3 = this.getLight(blockPos.west(), "".length() != 0);
            final int light4 = this.getLight(blockPos.south(), "".length() != 0);
            final int light5 = this.getLight(blockPos.north(), "".length() != 0);
            if (light2 > light) {
                light = light2;
            }
            if (light3 > light) {
                light = light3;
            }
            if (light4 > light) {
                light = light4;
            }
            if (light5 > light) {
                light = light5;
            }
            return light;
        }
        if (blockPos.getY() < 0) {
            return "".length();
        }
        if (blockPos.getY() >= 206 + 125 - 145 + 70) {
            blockPos = new BlockPos(blockPos.getX(), 29 + 175 + 24 + 27, blockPos.getZ());
        }
        return this.getChunkFromBlockCoords(blockPos).getLightSubtracted(blockPos, this.skylightSubtracted);
    }
    
    public boolean canBlockFreezeWater(final BlockPos blockPos) {
        return this.canBlockFreeze(blockPos, "".length() != 0);
    }
    
    public List<AxisAlignedBB> getCollidingBoundingBoxes(final Entity entity, final AxisAlignedBB axisAlignedBB) {
        final ArrayList arrayList = Lists.newArrayList();
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final WorldBorder worldBorder = this.getWorldBorder();
        final boolean outsideBorder = entity.isOutsideBorder();
        final boolean insideBorder = this.isInsideBorder(worldBorder, entity);
        final IBlockState defaultState = Blocks.stone.getDefaultState();
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = floor_double;
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (i < floor_double2) {
            int j = floor_double5;
            "".length();
            if (3 == 2) {
                throw null;
            }
            while (j < floor_double6) {
                if (this.isBlockLoaded(mutableBlockPos.func_181079_c(i, 0x56 ^ 0x16, j))) {
                    int k = floor_double3 - " ".length();
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                    while (k < floor_double4) {
                        mutableBlockPos.func_181079_c(i, k, j);
                        if (outsideBorder && insideBorder) {
                            entity.setOutsideBorder("".length() != 0);
                            "".length();
                            if (2 < -1) {
                                throw null;
                            }
                        }
                        else if (!outsideBorder && !insideBorder) {
                            entity.setOutsideBorder(" ".length() != 0);
                        }
                        IBlockState blockState = defaultState;
                        if (worldBorder.contains(mutableBlockPos) || !insideBorder) {
                            blockState = this.getBlockState(mutableBlockPos);
                        }
                        blockState.getBlock().addCollisionBoxesToList(this, mutableBlockPos, blockState, axisAlignedBB, arrayList, entity);
                        ++k;
                    }
                }
                ++j;
            }
            ++i;
        }
        final double n = 0.25;
        final List<Entity> entitiesWithinAABBExcludingEntity = this.getEntitiesWithinAABBExcludingEntity(entity, axisAlignedBB.expand(n, n, n));
        int l = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (l < entitiesWithinAABBExcludingEntity.size()) {
            if (entity.riddenByEntity != entitiesWithinAABBExcludingEntity && entity.ridingEntity != entitiesWithinAABBExcludingEntity) {
                final AxisAlignedBB collisionBoundingBox = entitiesWithinAABBExcludingEntity.get(l).getCollisionBoundingBox();
                if (collisionBoundingBox != null && collisionBoundingBox.intersectsWith(axisAlignedBB)) {
                    arrayList.add(collisionBoundingBox);
                }
                final AxisAlignedBB collisionBox = entity.getCollisionBox(entitiesWithinAABBExcludingEntity.get(l));
                if (collisionBox != null && collisionBox.intersectsWith(axisAlignedBB)) {
                    arrayList.add(collisionBox);
                }
            }
            ++l;
        }
        return (List<AxisAlignedBB>)arrayList;
    }
    
    private boolean isWater(final BlockPos blockPos) {
        if (this.getBlockState(blockPos).getBlock().getMaterial() == Material.water) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Chunk getChunkFromChunkCoords(final int n, final int n2) {
        return this.chunkProvider.provideChunk(n, n2);
    }
    
    public void unloadEntities(final Collection<Entity> collection) {
        this.unloadedEntityList.addAll(collection);
    }
    
    public boolean isSpawnChunk(final int n, final int n2) {
        final BlockPos spawnPoint = this.getSpawnPoint();
        final int n3 = n * (0xD0 ^ 0xC0) + (0x70 ^ 0x78) - spawnPoint.getX();
        final int n4 = n2 * (0xBF ^ 0xAF) + (0x8C ^ 0x84) - spawnPoint.getZ();
        final int n5 = 26 + 41 + 55 + 6;
        if (n3 >= -n5 && n3 <= n5 && n4 >= -n5 && n4 <= n5) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void markBlocksDirtyVertical(final int n, final int n2, int n3, int n4) {
        if (n3 > n4) {
            final int n5 = n4;
            n4 = n3;
            n3 = n5;
        }
        if (!this.provider.getHasNoSky()) {
            int i = n3;
            "".length();
            if (1 == 3) {
                throw null;
            }
            while (i <= n4) {
                this.checkLightFor(EnumSkyBlock.SKY, new BlockPos(n, i, n2));
                ++i;
            }
        }
        this.markBlockRangeForRenderUpdate(n, n3, n2, n, n4, n2);
    }
    
    public boolean tickUpdates(final boolean b) {
        return "".length() != 0;
    }
    
    public void setLastLightningBolt(final int lastLightningBolt) {
        this.lastLightningBolt = lastLightningBolt;
    }
    
    public boolean isAreaLoaded(final StructureBoundingBox structureBoundingBox, final boolean b) {
        return this.isAreaLoaded(structureBoundingBox.minX, structureBoundingBox.minY, structureBoundingBox.minZ, structureBoundingBox.maxX, structureBoundingBox.maxY, structureBoundingBox.maxZ, b);
    }
    
    protected void updateBlocks() {
        this.setActivePlayerChunksAndCheckLight();
    }
    
    public void loadEntities(final Collection<Entity> collection) {
        this.loadedEntityList.addAll(collection);
        final Iterator<Entity> iterator = collection.iterator();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.onEntityAdded(iterator.next());
        }
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos blockPos) {
        if (!this.isValid(blockPos)) {
            return Blocks.air.getDefaultState();
        }
        return this.getChunkFromBlockCoords(blockPos).getBlockState(blockPos);
    }
    
    public void notifyNeighborsOfStateExcept(final BlockPos blockPos, final Block block, final EnumFacing enumFacing) {
        if (enumFacing != EnumFacing.WEST) {
            this.notifyBlockOfStateChange(blockPos.west(), block);
        }
        if (enumFacing != EnumFacing.EAST) {
            this.notifyBlockOfStateChange(blockPos.east(), block);
        }
        if (enumFacing != EnumFacing.DOWN) {
            this.notifyBlockOfStateChange(blockPos.down(), block);
        }
        if (enumFacing != EnumFacing.UP) {
            this.notifyBlockOfStateChange(blockPos.up(), block);
        }
        if (enumFacing != EnumFacing.NORTH) {
            this.notifyBlockOfStateChange(blockPos.north(), block);
        }
        if (enumFacing != EnumFacing.SOUTH) {
            this.notifyBlockOfStateChange(blockPos.south(), block);
        }
    }
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final BlockPos blockPos) {
        if (this.isBlockLoaded(blockPos)) {
            final Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
            try {
                return chunkFromBlockCoords.getBiome(blockPos, this.provider.getWorldChunkManager());
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, World.I["".length()]);
                crashReport.makeCategory(World.I[" ".length()]).addCrashSectionCallable(World.I["  ".length()], new Callable<String>(this, blockPos) {
                    private final BlockPos val$pos;
                    final World this$0;
                    
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
                            if (2 <= 0) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    @Override
                    public Object call() throws Exception {
                        return this.call();
                    }
                    
                    @Override
                    public String call() throws Exception {
                        return CrashReportCategory.getCoordinateInfo(this.val$pos);
                    }
                });
                throw new ReportedException(crashReport);
            }
        }
        return this.provider.getWorldChunkManager().getBiomeGenerator(blockPos, BiomeGenBase.plains);
    }
    
    public WorldInfo getWorldInfo() {
        return this.worldInfo;
    }
    
    public Explosion newExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2) {
        final Explosion explosion = new Explosion(this, entity, n, n2, n3, n4, b, b2);
        explosion.doExplosionA();
        explosion.doExplosionB(" ".length() != 0);
        return explosion;
    }
    
    public BlockPos getStrongholdPos(final String s, final BlockPos blockPos) {
        return this.getChunkProvider().getStrongholdGen(this, s, blockPos);
    }
    
    public boolean checkLight(final BlockPos blockPos) {
        int length = "".length();
        if (!this.provider.getHasNoSky()) {
            length |= (this.checkLightFor(EnumSkyBlock.SKY, blockPos) ? 1 : 0);
        }
        return (length | (this.checkLightFor(EnumSkyBlock.BLOCK, blockPos) ? 1 : 0)) != 0x0;
    }
    
    public void scheduleBlockUpdate(final BlockPos blockPos, final Block block, final int n, final int n2) {
    }
    
    public void playSound(final double n, final double n2, final double n3, final String s, final float n4, final float n5, final boolean b) {
    }
    
    public EntityPlayer getPlayerEntityByUUID(final UUID uuid) {
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(i);
            if (uuid.equals(entityPlayer.getUniqueID())) {
                return entityPlayer;
            }
            ++i;
        }
        return null;
    }
    
    public boolean spawnEntityInWorld(final Entity entity) {
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        int n = entity.forceSpawn ? 1 : 0;
        if (entity instanceof EntityPlayer) {
            n = " ".length();
        }
        if (n == 0 && !this.isChunkLoaded(floor_double, floor_double2, " ".length() != 0)) {
            return "".length() != 0;
        }
        if (entity instanceof EntityPlayer) {
            this.playerEntities.add((EntityPlayer)entity);
            this.updateAllPlayersSleepingFlag();
        }
        this.getChunkFromChunkCoords(floor_double, floor_double2).addEntity(entity);
        this.loadedEntityList.add(entity);
        this.onEntityAdded(entity);
        return " ".length() != 0;
    }
    
    public VillageCollection getVillageCollection() {
        return this.villageCollectionObj;
    }
    
    public void calculateInitialSkylight() {
        final int calculateSkylightSubtracted = this.calculateSkylightSubtracted(1.0f);
        if (calculateSkylightSubtracted != this.skylightSubtracted) {
            this.skylightSubtracted = calculateSkylightSubtracted;
        }
    }
    
    public void playSoundAtEntity(final Entity entity, final String s, final float n, final float n2) {
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).playSound(s, entity.posX, entity.posY, entity.posZ, n, n2);
            ++i;
        }
    }
    
    public int isBlockIndirectlyGettingPowered(final BlockPos blockPos) {
        int length = "".length();
        final EnumFacing[] values;
        final int length2 = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (i < length2) {
            final EnumFacing enumFacing = values[i];
            final int redstonePower = this.getRedstonePower(blockPos.offset(enumFacing), enumFacing);
            if (redstonePower >= (0x7D ^ 0x72)) {
                return 0x6C ^ 0x63;
            }
            if (redstonePower > length) {
                length = redstonePower;
            }
            ++i;
        }
        return length;
    }
    
    public IChunkProvider getChunkProvider() {
        return this.chunkProvider;
    }
    
    public boolean handleMaterialAcceleration(final AxisAlignedBB axisAlignedBB, final Material material, final Entity entity) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        if (!this.isAreaLoaded(floor_double, floor_double3, floor_double5, floor_double2, floor_double4, floor_double6, " ".length() != 0)) {
            return "".length() != 0;
        }
        int n = "".length();
        Vec3 modifyAcceleration = new Vec3(0.0, 0.0, 0.0);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = floor_double;
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (i < floor_double2) {
            int j = floor_double3;
            "".length();
            if (1 >= 4) {
                throw null;
            }
            while (j < floor_double4) {
                int k = floor_double5;
                "".length();
                if (0 == 2) {
                    throw null;
                }
                while (k < floor_double6) {
                    mutableBlockPos.func_181079_c(i, j, k);
                    final IBlockState blockState = this.getBlockState(mutableBlockPos);
                    final Block block = blockState.getBlock();
                    if (block.getMaterial() == material && floor_double4 >= j + " ".length() - BlockLiquid.getLiquidHeightPercent(blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL))) {
                        n = " ".length();
                        modifyAcceleration = block.modifyAcceleration(this, mutableBlockPos, entity, modifyAcceleration);
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        if (modifyAcceleration.lengthVector() > 0.0 && entity.isPushedByWater()) {
            final Vec3 normalize = modifyAcceleration.normalize();
            final double n2 = 0.014;
            entity.motionX += normalize.xCoord * n2;
            entity.motionY += normalize.yCoord * n2;
            entity.motionZ += normalize.zCoord * n2;
        }
        return n != 0;
    }
    
    public boolean destroyBlock(final BlockPos blockPos, final boolean b) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block.getMaterial() == Material.air) {
            return "".length() != 0;
        }
        this.playAuxSFX(1697 + 842 - 1889 + 1351, blockPos, Block.getStateId(blockState));
        if (b) {
            block.dropBlockAsItem(this, blockPos, blockState, "".length());
        }
        return this.setBlockState(blockPos, Blocks.air.getDefaultState(), "   ".length());
    }
    
    public boolean isFlammableWithin(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        if (this.isAreaLoaded(floor_double, floor_double3, floor_double5, floor_double2, floor_double4, floor_double6, " ".length() != 0)) {
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int i = floor_double;
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (i < floor_double2) {
                int j = floor_double3;
                "".length();
                if (false) {
                    throw null;
                }
                while (j < floor_double4) {
                    int k = floor_double5;
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    while (k < floor_double6) {
                        final Block block = this.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock();
                        if (block == Blocks.fire || block == Blocks.flowing_lava || block == Blocks.lava) {
                            return " ".length() != 0;
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
        return "".length() != 0;
    }
    
    public boolean isFindingSpawnPoint() {
        return this.findingSpawnPoint;
    }
    
    public Chunk getChunkFromBlockCoords(final BlockPos blockPos) {
        return this.getChunkFromChunkCoords(blockPos.getX() >> (0x62 ^ 0x66), blockPos.getZ() >> (0x8F ^ 0x8B));
    }
    
    public GameRules getGameRules() {
        return this.worldInfo.getGameRulesInstance();
    }
    
    protected void onEntityRemoved(final Entity entity) {
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).onEntityRemoved(entity);
            ++i;
        }
    }
    
    public String getDebugLoadedEntities() {
        return World.I[0x7C ^ 0x64] + this.loadedEntityList.size();
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 vec3, final Vec3 vec4, final boolean b) {
        return this.rayTraceBlocks(vec3, vec4, b, "".length() != 0, "".length() != 0);
    }
    
    public boolean checkNoEntityCollision(final AxisAlignedBB axisAlignedBB, final Entity entity) {
        final List<Entity> entitiesWithinAABBExcludingEntity = this.getEntitiesWithinAABBExcludingEntity(null, axisAlignedBB);
        int i = "".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (i < entitiesWithinAABBExcludingEntity.size()) {
            final Entity entity2 = entitiesWithinAABBExcludingEntity.get(i);
            if (!entity2.isDead && entity2.preventEntitySpawning && entity2 != entity && (entity == null || (entity.ridingEntity != entity2 && entity.riddenByEntity != entity2))) {
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    public static boolean doesBlockHaveSolidTopSurface(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        int n;
        if (block.getMaterial().isOpaque() && block.isFullCube()) {
            n = " ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (block instanceof BlockStairs) {
            if (blockState.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP) {
                n = " ".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
        }
        else if (block instanceof BlockSlab) {
            if (blockState.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP) {
                n = " ".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
        }
        else if (block instanceof BlockHopper) {
            n = " ".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (block instanceof BlockSnow) {
            if (blockState.getValue((IProperty<Integer>)BlockSnow.LAYERS) == (0x27 ^ 0x20)) {
                n = " ".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public double getHorizon() {
        double n;
        if (this.worldInfo.getTerrainType() == WorldType.FLAT) {
            n = 0.0;
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n = 63.0;
        }
        return n;
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.provider.getWorldChunkManager();
    }
    
    public void setItemData(final String s, final WorldSavedData worldSavedData) {
        this.mapStorage.setData(s, worldSavedData);
    }
    
    public void setInitialSpawnLocation() {
        this.setSpawnPoint(new BlockPos(0x91 ^ 0x99, 0x72 ^ 0x32, 0x63 ^ 0x6B));
    }
    
    public WorldSavedData loadItemData(final Class<? extends WorldSavedData> clazz, final String s) {
        return this.mapStorage.loadData(clazz, s);
    }
    
    public void scheduleUpdate(final BlockPos blockPos, final Block block, final int n) {
    }
    
    public List<Entity> getLoadedEntityList() {
        return this.loadedEntityList;
    }
    
    public boolean isBlockTickPending(final BlockPos blockPos, final Block block) {
        return "".length() != 0;
    }
    
    public void tick() {
        this.updateWeather();
    }
    
    public <T extends Entity> List<T> getPlayers(final Class<? extends T> clazz, final Predicate<? super T> predicate) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<EntityPlayer> iterator = this.playerEntities.iterator();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayer entityPlayer = iterator.next();
            if (clazz.isAssignableFrom(entityPlayer.getClass()) && predicate.apply((Object)entityPlayer)) {
                arrayList.add(entityPlayer);
            }
        }
        return (List<T>)arrayList;
    }
    
    public void updateBlockTick(final BlockPos blockPos, final Block block, final int n, final int n2) {
    }
    
    public void setWorldTime(final long worldTime) {
        this.worldInfo.setWorldTime(worldTime);
    }
    
    protected World(final ISaveHandler saveHandler, final WorldInfo worldInfo, final WorldProvider provider, final Profiler theProfiler, final boolean isRemote) {
        this.field_181546_a = (0xA3 ^ 0x9C);
        this.loadedEntityList = (List<Entity>)Lists.newArrayList();
        this.unloadedEntityList = (List<Entity>)Lists.newArrayList();
        this.loadedTileEntityList = (List<TileEntity>)Lists.newArrayList();
        this.tickableTileEntities = (List<TileEntity>)Lists.newArrayList();
        this.addedTileEntityList = (List<TileEntity>)Lists.newArrayList();
        this.tileEntitiesToBeRemoved = (List<TileEntity>)Lists.newArrayList();
        this.playerEntities = (List<EntityPlayer>)Lists.newArrayList();
        this.weatherEffects = (List<Entity>)Lists.newArrayList();
        this.entitiesById = new IntHashMap<Entity>();
        this.cloudColour = 16777215L;
        this.updateLCG = new Random().nextInt();
        this.rand = new Random();
        this.worldAccesses = (List<IWorldAccess>)Lists.newArrayList();
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.activeChunkSet = (Set<ChunkCoordIntPair>)Sets.newHashSet();
        this.ambientTickCountdown = this.rand.nextInt(472 + 7090 + 912 + 3526);
        this.spawnHostileMobs = (" ".length() != 0);
        this.spawnPeacefulMobs = (" ".length() != 0);
        this.lightUpdateBlockList = new int[10370 + 16314 - 21814 + 27898];
        this.saveHandler = saveHandler;
        this.theProfiler = theProfiler;
        this.worldInfo = worldInfo;
        this.provider = provider;
        this.isRemote = isRemote;
        this.worldBorder = provider.getWorldBorder();
    }
    
    public void addTileEntities(final Collection<TileEntity> collection) {
        if (this.processingLoadedTiles) {
            this.addedTileEntityList.addAll(collection);
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            final Iterator<TileEntity> iterator = collection.iterator();
            "".length();
            if (true != true) {
                throw null;
            }
            while (iterator.hasNext()) {
                final TileEntity tileEntity = iterator.next();
                this.loadedTileEntityList.add(tileEntity);
                if (tileEntity instanceof ITickable) {
                    this.tickableTileEntities.add(tileEntity);
                }
            }
        }
    }
    
    public void updateEntities() {
        this.theProfiler.startSection(World.I[0x63 ^ 0x64]);
        this.theProfiler.startSection(World.I[0xA7 ^ 0xAF]);
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < this.weatherEffects.size()) {
            final Entity entity = this.weatherEffects.get(i);
            try {
                final Entity entity2 = entity;
                entity2.ticksExisted += " ".length();
                entity.onUpdate();
                "".length();
                if (1 == 4) {
                    throw null;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, World.I[0xBE ^ 0xB7]);
                final CrashReportCategory category = crashReport.makeCategory(World.I[0x3A ^ 0x30]);
                if (entity == null) {
                    category.addCrashSection(World.I[0x54 ^ 0x5F], World.I[0x6F ^ 0x63]);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    entity.addEntityCrashInfo(category);
                }
                throw new ReportedException(crashReport);
            }
            if (entity.isDead) {
                this.weatherEffects.remove(i--);
            }
            ++i;
        }
        this.theProfiler.endStartSection(World.I[0xA1 ^ 0xAC]);
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        int j = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (j < this.unloadedEntityList.size()) {
            final Entity entity3 = this.unloadedEntityList.get(j);
            final int chunkCoordX = entity3.chunkCoordX;
            final int chunkCoordZ = entity3.chunkCoordZ;
            if (entity3.addedToChunk && this.isChunkLoaded(chunkCoordX, chunkCoordZ, " ".length() != 0)) {
                this.getChunkFromChunkCoords(chunkCoordX, chunkCoordZ).removeEntity(entity3);
            }
            ++j;
        }
        int k = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (k < this.unloadedEntityList.size()) {
            this.onEntityRemoved(this.unloadedEntityList.get(k));
            ++k;
        }
        this.unloadedEntityList.clear();
        this.theProfiler.endStartSection(World.I[0x31 ^ 0x3F]);
        int l = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (l < this.loadedEntityList.size()) {
            final Entity entity4 = this.loadedEntityList.get(l);
            Label_0705: {
                if (entity4.ridingEntity != null) {
                    if (!entity4.ridingEntity.isDead && entity4.ridingEntity.riddenByEntity == entity4) {
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                        break Label_0705;
                    }
                    else {
                        entity4.ridingEntity.riddenByEntity = null;
                        entity4.ridingEntity = null;
                    }
                }
                this.theProfiler.startSection(World.I[0x83 ^ 0x8C]);
                if (!entity4.isDead) {
                    try {
                        this.updateEntity(entity4);
                        "".length();
                        if (3 < 0) {
                            throw null;
                        }
                    }
                    catch (Throwable t2) {
                        final CrashReport crashReport2 = CrashReport.makeCrashReport(t2, World.I[0x30 ^ 0x20]);
                        entity4.addEntityCrashInfo(crashReport2.makeCategory(World.I[0x44 ^ 0x55]));
                        throw new ReportedException(crashReport2);
                    }
                }
                this.theProfiler.endSection();
                this.theProfiler.startSection(World.I[0x42 ^ 0x50]);
                if (entity4.isDead) {
                    final int chunkCoordX2 = entity4.chunkCoordX;
                    final int chunkCoordZ2 = entity4.chunkCoordZ;
                    if (entity4.addedToChunk && this.isChunkLoaded(chunkCoordX2, chunkCoordZ2, " ".length() != 0)) {
                        this.getChunkFromChunkCoords(chunkCoordX2, chunkCoordZ2).removeEntity(entity4);
                    }
                    this.loadedEntityList.remove(l--);
                    this.onEntityRemoved(entity4);
                }
                this.theProfiler.endSection();
            }
            ++l;
        }
        this.theProfiler.endStartSection(World.I[0x4B ^ 0x58]);
        this.processingLoadedTiles = (" ".length() != 0);
        final Iterator<TileEntity> iterator = this.tickableTileEntities.iterator();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final TileEntity tileEntity = iterator.next();
            if (!tileEntity.isInvalid() && tileEntity.hasWorldObj()) {
                final BlockPos pos = tileEntity.getPos();
                if (this.isBlockLoaded(pos) && this.worldBorder.contains(pos)) {
                    try {
                        ((ITickable)tileEntity).update();
                        "".length();
                        if (4 <= 1) {
                            throw null;
                        }
                    }
                    catch (Throwable t3) {
                        final CrashReport crashReport3 = CrashReport.makeCrashReport(t3, World.I[0xD3 ^ 0xC7]);
                        tileEntity.addInfoToCrashReport(crashReport3.makeCategory(World.I[0xF ^ 0x1A]));
                        throw new ReportedException(crashReport3);
                    }
                }
            }
            if (tileEntity.isInvalid()) {
                iterator.remove();
                this.loadedTileEntityList.remove(tileEntity);
                if (!this.isBlockLoaded(tileEntity.getPos())) {
                    continue;
                }
                this.getChunkFromBlockCoords(tileEntity.getPos()).removeTileEntity(tileEntity.getPos());
            }
        }
        this.processingLoadedTiles = ("".length() != 0);
        if (!this.tileEntitiesToBeRemoved.isEmpty()) {
            this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
            this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
            this.tileEntitiesToBeRemoved.clear();
        }
        this.theProfiler.endStartSection(World.I[0x8B ^ 0x9D]);
        if (!this.addedTileEntityList.isEmpty()) {
            int length = "".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
            while (length < this.addedTileEntityList.size()) {
                final TileEntity tileEntity2 = this.addedTileEntityList.get(length);
                if (!tileEntity2.isInvalid()) {
                    if (!this.loadedTileEntityList.contains(tileEntity2)) {
                        this.addTileEntity(tileEntity2);
                    }
                    if (this.isBlockLoaded(tileEntity2.getPos())) {
                        this.getChunkFromBlockCoords(tileEntity2.getPos()).addTileEntity(tileEntity2.getPos(), tileEntity2);
                    }
                    this.markBlockForUpdate(tileEntity2.getPos());
                }
                ++length;
            }
            this.addedTileEntityList.clear();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }
    
    public ISaveHandler getSaveHandler() {
        return this.saveHandler;
    }
    
    public void setTotalWorldTime(final long worldTotalTime) {
        this.worldInfo.setWorldTotalTime(worldTotalTime);
    }
    
    private boolean isAreaLoaded(int n, final int n2, int n3, int n4, final int n5, int n6, final boolean b) {
        if (n5 < 0 || n2 >= 11 + 200 - 41 + 86) {
            return "".length() != 0;
        }
        n >>= (0x12 ^ 0x16);
        n3 >>= (0xB1 ^ 0xB5);
        n4 >>= (0x5B ^ 0x5F);
        n6 >>= (0xA8 ^ 0xAC);
        int i = n;
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (i <= n4) {
            int j = n3;
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (j <= n6) {
                if (!this.isChunkLoaded(i, j, b)) {
                    return "".length() != 0;
                }
                ++j;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos) {
        if (!this.isValid(blockPos)) {
            return null;
        }
        TileEntity tileEntity = null;
        if (this.processingLoadedTiles) {
            int i = "".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
            while (i < this.addedTileEntityList.size()) {
                final TileEntity tileEntity2 = this.addedTileEntityList.get(i);
                if (!tileEntity2.isInvalid() && tileEntity2.getPos().equals(blockPos)) {
                    tileEntity = tileEntity2;
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        if (tileEntity == null) {
            tileEntity = this.getChunkFromBlockCoords(blockPos).getTileEntity(blockPos, Chunk.EnumCreateEntityType.IMMEDIATE);
        }
        if (tileEntity == null) {
            int j = "".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
            while (j < this.addedTileEntityList.size()) {
                final TileEntity tileEntity3 = this.addedTileEntityList.get(j);
                if (!tileEntity3.isInvalid() && tileEntity3.getPos().equals(blockPos)) {
                    tileEntity = tileEntity3;
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++j;
                }
            }
        }
        return tileEntity;
    }
    
    public void func_181544_b(final int field_181546_a) {
        this.field_181546_a = field_181546_a;
    }
    
    public List<AxisAlignedBB> func_147461_a(final AxisAlignedBB axisAlignedBB) {
        final ArrayList arrayList = Lists.newArrayList();
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = floor_double;
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < floor_double2) {
            int j = floor_double5;
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (j < floor_double6) {
                if (this.isBlockLoaded(mutableBlockPos.func_181079_c(i, 0x16 ^ 0x56, j))) {
                    int k = floor_double3 - " ".length();
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                    while (k < floor_double4) {
                        mutableBlockPos.func_181079_c(i, k, j);
                        IBlockState blockState;
                        if (i >= -(26713964 + 10312575 - 22506894 + 15480355) && i < 2946497 + 3884775 + 4682410 + 18486318 && j >= -(13264079 + 10094526 - 21576513 + 28217908) && j < 15428728 + 13627507 - 3115655 + 4059420) {
                            blockState = this.getBlockState(mutableBlockPos);
                            "".length();
                            if (2 == 0) {
                                throw null;
                            }
                        }
                        else {
                            blockState = Blocks.bedrock.getDefaultState();
                        }
                        blockState.getBlock().addCollisionBoxesToList(this, mutableBlockPos, blockState, axisAlignedBB, arrayList, null);
                        ++k;
                    }
                }
                ++j;
            }
            ++i;
        }
        return (List<AxisAlignedBB>)arrayList;
    }
    
    public boolean isBlockLoaded(final BlockPos blockPos, final boolean b) {
        int n;
        if (!this.isValid(blockPos)) {
            n = "".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            n = (this.isChunkLoaded(blockPos.getX() >> (0x0 ^ 0x4), blockPos.getZ() >> (0xC1 ^ 0xC5), b) ? 1 : 0);
        }
        return n != 0;
    }
    
    public float getStarBrightness(final float n) {
        final float clamp_float = MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.25f), 0.0f, 1.0f);
        return clamp_float * clamp_float * 0.5f;
    }
    
    public Explosion createExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b) {
        return this.newExplosion(entity, n, n2, n3, n4, "".length() != 0, b);
    }
    
    public boolean checkNoEntityCollision(final AxisAlignedBB axisAlignedBB) {
        return this.checkNoEntityCollision(axisAlignedBB, null);
    }
    
    public void setAllowedSpawnTypes(final boolean spawnHostileMobs, final boolean spawnPeacefulMobs) {
        this.spawnHostileMobs = spawnHostileMobs;
        this.spawnPeacefulMobs = spawnPeacefulMobs;
    }
    
    public Random setRandomSeed(final int n, final int n2, final int n3) {
        this.rand.setSeed(n * 341873128712L + n2 * 132897987541L + this.getWorldInfo().getSeed() + n3);
        return this.rand;
    }
    
    protected void calculateInitialWeather() {
        if (this.worldInfo.isRaining()) {
            this.rainingStrength = 1.0f;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength = 1.0f;
            }
        }
    }
    
    public List<NextTickListEntry> getPendingBlockUpdates(final Chunk chunk, final boolean b) {
        return null;
    }
    
    public int calculateSkylightSubtracted(final float n) {
        return (int)((1.0f - (float)((float)((1.0f - MathHelper.clamp_float(1.0f - (MathHelper.cos(this.getCelestialAngle(n) * 3.1415927f * 2.0f) * 2.0f + 0.5f), 0.0f, 1.0f)) * (1.0 - this.getRainStrength(n) * 5.0f / 16.0)) * (1.0 - this.getThunderStrength(n) * 5.0f / 16.0))) * 11.0f);
    }
    
    public void notifyNeighborsRespectDebug(final BlockPos blockPos, final Block block) {
        if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            this.notifyNeighborsOfStateChange(blockPos, block);
        }
    }
    
    public boolean isAnyPlayerWithinRangeAt(final double n, final double n2, final double n3, final double n4) {
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(i);
            if (EntitySelectors.NOT_SPECTATING.apply((Object)entityPlayer)) {
                final double distanceSq = entityPlayer.getDistanceSq(n, n2, n3);
                if (n4 < 0.0 || distanceSq < n4 * n4) {
                    return " ".length() != 0;
                }
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public Scoreboard getScoreboard() {
        return this.worldScoreboard;
    }
    
    public int getUniqueDataId(final String s) {
        return this.mapStorage.getUniqueDataId(s);
    }
    
    public void updateAllPlayersSleepingFlag() {
    }
    
    public boolean setBlockState(final BlockPos blockPos, final IBlockState blockState) {
        return this.setBlockState(blockPos, blockState, "   ".length());
    }
    
    public float getRainStrength(final float n) {
        return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * n;
    }
    
    public long getTotalWorldTime() {
        return this.worldInfo.getWorldTotalTime();
    }
    
    static {
        I();
    }
    
    public boolean isAreaLoaded(final BlockPos blockPos, final int n) {
        return this.isAreaLoaded(blockPos, n, " ".length() != 0);
    }
    
    public void updateComparatorOutputLevel(final BlockPos blockPos, final Block block) {
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing next = iterator.next();
            final BlockPos offset = blockPos.offset(next);
            if (this.isBlockLoaded(offset)) {
                final IBlockState blockState = this.getBlockState(offset);
                if (Blocks.unpowered_comparator.isAssociated(blockState.getBlock())) {
                    blockState.getBlock().onNeighborBlockChange(this, offset, blockState, block);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    continue;
                }
                else {
                    if (!blockState.getBlock().isNormalCube()) {
                        continue;
                    }
                    final BlockPos offset2 = offset.offset(next);
                    final IBlockState blockState2 = this.getBlockState(offset2);
                    if (!Blocks.unpowered_comparator.isAssociated(blockState2.getBlock())) {
                        continue;
                    }
                    blockState2.getBlock().onNeighborBlockChange(this, offset2, blockState2, block);
                }
            }
        }
    }
    
    public void markBlockRangeForRenderUpdate(final BlockPos blockPos, final BlockPos blockPos2) {
        this.markBlockRangeForRenderUpdate(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }
    
    private void spawnParticle(final int n, final boolean b, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).spawnParticle(n, b, n2, n3, n4, n5, n6, n7, array);
            ++i;
        }
    }
    
    public void makeFireworks(final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final NBTTagCompound nbtTagCompound) {
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 vec3, final Vec3 vec4) {
        return this.rayTraceBlocks(vec3, vec4, "".length() != 0, "".length() != 0, "".length() != 0);
    }
    
    public int getRedstonePower(final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        int n;
        if (block.isNormalCube()) {
            n = this.getStrongPower(blockPos);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            n = block.getWeakPower(this, blockPos, blockState, enumFacing);
        }
        return n;
    }
    
    public List<NextTickListEntry> func_175712_a(final StructureBoundingBox structureBoundingBox, final boolean b) {
        return null;
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.spawnParticle(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange(), n, n2, n3, n4, n5, n6, array);
    }
    
    public boolean isBlockinHighHumidity(final BlockPos blockPos) {
        return this.getBiomeGenForCoords(blockPos).isHighHumidity();
    }
    
    public void removeWorldAccess(final IWorldAccess worldAccess) {
        this.worldAccesses.remove(worldAccess);
    }
    
    public void sendBlockBreakProgress(final int n, final BlockPos blockPos, final int n2) {
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).sendBlockBreakProgress(n, blockPos, n2);
            ++i;
        }
    }
    
    public float getBlockDensity(final Vec3 vec3, final AxisAlignedBB axisAlignedBB) {
        final double n = 1.0 / ((axisAlignedBB.maxX - axisAlignedBB.minX) * 2.0 + 1.0);
        final double n2 = 1.0 / ((axisAlignedBB.maxY - axisAlignedBB.minY) * 2.0 + 1.0);
        final double n3 = 1.0 / ((axisAlignedBB.maxZ - axisAlignedBB.minZ) * 2.0 + 1.0);
        final double n4 = (1.0 - Math.floor(1.0 / n) * n) / 2.0;
        final double n5 = (1.0 - Math.floor(1.0 / n3) * n3) / 2.0;
        if (n < 0.0 || n2 < 0.0 || n3 < 0.0) {
            return 0.0f;
        }
        int length = "".length();
        int length2 = "".length();
        float n6 = 0.0f;
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (n6 <= 1.0f) {
            float n7 = 0.0f;
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (n7 <= 1.0f) {
                float n8 = 0.0f;
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                while (n8 <= 1.0f) {
                    if (this.rayTraceBlocks(new Vec3(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * n6 + n4, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * n7, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * n8 + n5), vec3) == null) {
                        ++length;
                    }
                    ++length2;
                    n8 += (float)n3;
                }
                n7 += (float)n2;
            }
            n6 += (float)n;
        }
        return length / length2;
    }
    
    public boolean isBlockNormalCube(final BlockPos blockPos, final boolean b) {
        if (!this.isValid(blockPos)) {
            return b;
        }
        if (this.chunkProvider.provideChunk(blockPos).isEmpty()) {
            return b;
        }
        final Block block = this.getBlockState(blockPos).getBlock();
        if (block.getMaterial().isOpaque() && block.isFullCube()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final boolean b, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final int... array) {
        this.spawnParticle(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange() | b, n, n2, n3, n4, n5, n6, array);
    }
    
    public boolean checkBlockCollision(final AxisAlignedBB axisAlignedBB) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = floor_double;
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i <= floor_double2) {
            int j = floor_double3;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            while (j <= floor_double4) {
                int k = floor_double5;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (k <= floor_double6) {
                    if (this.getBlockState(mutableBlockPos.func_181079_c(i, j, k)).getBlock().getMaterial() != Material.air) {
                        return " ".length() != 0;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public void playAuxSFX(final int n, final BlockPos blockPos, final int n2) {
        this.playAuxSFXAtEntity(null, n, blockPos, n2);
    }
    
    public DifficultyInstance getDifficultyForLocation(final BlockPos blockPos) {
        long inhabitedTime = 0L;
        float currentMoonPhaseFactor = 0.0f;
        if (this.isBlockLoaded(blockPos)) {
            currentMoonPhaseFactor = this.getCurrentMoonPhaseFactor();
            inhabitedTime = this.getChunkFromBlockCoords(blockPos).getInhabitedTime();
        }
        return new DifficultyInstance(this.getDifficulty(), this.getWorldTime(), inhabitedTime, currentMoonPhaseFactor);
    }
    
    public boolean extinguishFire(final EntityPlayer entityPlayer, BlockPos offset, final EnumFacing enumFacing) {
        offset = offset.offset(enumFacing);
        if (this.getBlockState(offset).getBlock() == Blocks.fire) {
            this.playAuxSFXAtEntity(entityPlayer, 195 + 778 - 271 + 302, offset, "".length());
            this.setBlockToAir(offset);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setEntityState(final Entity entity, final byte b) {
    }
    
    public boolean canLightningStrike(final BlockPos blockPos) {
        if (!this.isRaining()) {
            return "".length() != 0;
        }
        if (!this.canSeeSky(blockPos)) {
            return "".length() != 0;
        }
        if (this.getPrecipitationHeight(blockPos).getY() > blockPos.getY()) {
            return "".length() != 0;
        }
        final BiomeGenBase biomeGenForCoords = this.getBiomeGenForCoords(blockPos);
        int n;
        if (biomeGenForCoords.getEnableSnow()) {
            n = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (this.canSnowAt(blockPos, "".length() != 0)) {
            n = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = (biomeGenForCoords.canSpawnLightningBolt() ? 1 : 0);
        }
        return n != 0;
    }
    
    public int getLastLightningBolt() {
        return this.lastLightningBolt;
    }
    
    public boolean isDaytime() {
        if (this.skylightSubtracted < (0x70 ^ 0x74)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isThundering() {
        if (this.getThunderStrength(1.0f) > 0.9) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isBlockPowered(final BlockPos blockPos) {
        int n;
        if (this.getRedstonePower(blockPos.down(), EnumFacing.DOWN) > 0) {
            n = " ".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else if (this.getRedstonePower(blockPos.up(), EnumFacing.UP) > 0) {
            n = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (this.getRedstonePower(blockPos.north(), EnumFacing.NORTH) > 0) {
            n = " ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (this.getRedstonePower(blockPos.south(), EnumFacing.SOUTH) > 0) {
            n = " ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (this.getRedstonePower(blockPos.west(), EnumFacing.WEST) > 0) {
            n = " ".length();
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else if (this.getRedstonePower(blockPos.east(), EnumFacing.EAST) > 0) {
            n = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void checkSessionLock() throws MinecraftException {
        this.saveHandler.checkSessionLock();
    }
    
    public MovingObjectPosition rayTraceBlocks(Vec3 vec3, final Vec3 vec4, final boolean b, final boolean b2, final boolean b3) {
        if (Double.isNaN(vec3.xCoord) || Double.isNaN(vec3.yCoord) || Double.isNaN(vec3.zCoord)) {
            return null;
        }
        if (Double.isNaN(vec4.xCoord) || Double.isNaN(vec4.yCoord) || Double.isNaN(vec4.zCoord)) {
            return null;
        }
        final int floor_double = MathHelper.floor_double(vec4.xCoord);
        final int floor_double2 = MathHelper.floor_double(vec4.yCoord);
        final int floor_double3 = MathHelper.floor_double(vec4.zCoord);
        int floor_double4 = MathHelper.floor_double(vec3.xCoord);
        int floor_double5 = MathHelper.floor_double(vec3.yCoord);
        int floor_double6 = MathHelper.floor_double(vec3.zCoord);
        final BlockPos blockPos = new BlockPos(floor_double4, floor_double5, floor_double6);
        final IBlockState blockState = this.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if ((!b2 || block.getCollisionBoundingBox(this, blockPos, blockState) != null) && block.canCollideCheck(blockState, b)) {
            final MovingObjectPosition collisionRayTrace = block.collisionRayTrace(this, blockPos, vec3, vec4);
            if (collisionRayTrace != null) {
                return collisionRayTrace;
            }
        }
        MovingObjectPosition movingObjectPosition = null;
        int n = 57 + 131 - 19 + 31;
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (n-- >= 0) {
            if (Double.isNaN(vec3.xCoord) || Double.isNaN(vec3.yCoord) || Double.isNaN(vec3.zCoord)) {
                return null;
            }
            if (floor_double4 == floor_double && floor_double5 == floor_double2 && floor_double6 == floor_double3) {
                MovingObjectPosition movingObjectPosition2;
                if (b3) {
                    movingObjectPosition2 = movingObjectPosition;
                    "".length();
                    if (0 == 3) {
                        throw null;
                    }
                }
                else {
                    movingObjectPosition2 = null;
                }
                return movingObjectPosition2;
            }
            int n2 = " ".length();
            int n3 = " ".length();
            int n4 = " ".length();
            double n5 = 999.0;
            double n6 = 999.0;
            double n7 = 999.0;
            if (floor_double > floor_double4) {
                n5 = floor_double4 + 1.0;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else if (floor_double < floor_double4) {
                n5 = floor_double4 + 0.0;
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            if (floor_double2 > floor_double5) {
                n6 = floor_double5 + 1.0;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (floor_double2 < floor_double5) {
                n6 = floor_double5 + 0.0;
                "".length();
                if (!true) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            if (floor_double3 > floor_double6) {
                n7 = floor_double6 + 1.0;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (floor_double3 < floor_double6) {
                n7 = floor_double6 + 0.0;
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                n4 = "".length();
            }
            double n8 = 999.0;
            double n9 = 999.0;
            double n10 = 999.0;
            final double n11 = vec4.xCoord - vec3.xCoord;
            final double n12 = vec4.yCoord - vec3.yCoord;
            final double n13 = vec4.zCoord - vec3.zCoord;
            if (n2 != 0) {
                n8 = (n5 - vec3.xCoord) / n11;
            }
            if (n3 != 0) {
                n9 = (n6 - vec3.yCoord) / n12;
            }
            if (n4 != 0) {
                n10 = (n7 - vec3.zCoord) / n13;
            }
            if (n8 == -0.0) {
                n8 = -1.0E-4;
            }
            if (n9 == -0.0) {
                n9 = -1.0E-4;
            }
            if (n10 == -0.0) {
                n10 = -1.0E-4;
            }
            EnumFacing enumFacing2;
            if (n8 < n9 && n8 < n10) {
                EnumFacing enumFacing;
                if (floor_double > floor_double4) {
                    enumFacing = EnumFacing.WEST;
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    enumFacing = EnumFacing.EAST;
                }
                enumFacing2 = enumFacing;
                vec3 = new Vec3(n5, vec3.yCoord + n12 * n8, vec3.zCoord + n13 * n8);
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else if (n9 < n10) {
                EnumFacing enumFacing3;
                if (floor_double2 > floor_double5) {
                    enumFacing3 = EnumFacing.DOWN;
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    enumFacing3 = EnumFacing.UP;
                }
                enumFacing2 = enumFacing3;
                vec3 = new Vec3(vec3.xCoord + n11 * n9, n6, vec3.zCoord + n13 * n9);
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                EnumFacing enumFacing4;
                if (floor_double3 > floor_double6) {
                    enumFacing4 = EnumFacing.NORTH;
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    enumFacing4 = EnumFacing.SOUTH;
                }
                enumFacing2 = enumFacing4;
                vec3 = new Vec3(vec3.xCoord + n11 * n10, vec3.yCoord + n12 * n10, n7);
            }
            final int floor_double7 = MathHelper.floor_double(vec3.xCoord);
            int n14;
            if (enumFacing2 == EnumFacing.EAST) {
                n14 = " ".length();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                n14 = "".length();
            }
            floor_double4 = floor_double7 - n14;
            final int floor_double8 = MathHelper.floor_double(vec3.yCoord);
            int n15;
            if (enumFacing2 == EnumFacing.UP) {
                n15 = " ".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n15 = "".length();
            }
            floor_double5 = floor_double8 - n15;
            final int floor_double9 = MathHelper.floor_double(vec3.zCoord);
            int n16;
            if (enumFacing2 == EnumFacing.SOUTH) {
                n16 = " ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                n16 = "".length();
            }
            floor_double6 = floor_double9 - n16;
            final BlockPos blockPos2 = new BlockPos(floor_double4, floor_double5, floor_double6);
            final IBlockState blockState2 = this.getBlockState(blockPos2);
            final Block block2 = blockState2.getBlock();
            if (b2 && block2.getCollisionBoundingBox(this, blockPos2, blockState2) == null) {
                continue;
            }
            if (block2.canCollideCheck(blockState2, b)) {
                final MovingObjectPosition collisionRayTrace2 = block2.collisionRayTrace(this, blockPos2, vec3, vec4);
                if (collisionRayTrace2 != null) {
                    return collisionRayTrace2;
                }
                continue;
            }
            else {
                movingObjectPosition = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec3, enumFacing2, blockPos2);
            }
        }
        MovingObjectPosition movingObjectPosition3;
        if (b3) {
            movingObjectPosition3 = movingObjectPosition;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            movingObjectPosition3 = null;
        }
        return movingObjectPosition3;
    }
    
    public EnumDifficulty getDifficulty() {
        return this.getWorldInfo().getDifficulty();
    }
    
    public void setThunderStrength(final float n) {
        this.prevThunderingStrength = n;
        this.thunderingStrength = n;
    }
    
    public <T extends Entity> List<T> getEntities(final Class<? extends T> clazz, final Predicate<? super T> predicate) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Entity> iterator = this.loadedEntityList.iterator();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Entity entity = iterator.next();
            if (clazz.isAssignableFrom(entity.getClass()) && predicate.apply((Object)entity)) {
                arrayList.add(entity);
            }
        }
        return (List<T>)arrayList;
    }
    
    @Override
    public int getCombinedLight(final BlockPos blockPos, final int n) {
        final int lightFromNeighbors = this.getLightFromNeighborsFor(EnumSkyBlock.SKY, blockPos);
        int lightFromNeighbors2 = this.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, blockPos);
        if (lightFromNeighbors2 < n) {
            lightFromNeighbors2 = n;
        }
        return lightFromNeighbors << (0x10 ^ 0x4) | lightFromNeighbors2 << (0x26 ^ 0x22);
    }
    
    public EntityPlayer getPlayerEntityByName(final String s) {
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < this.playerEntities.size()) {
            final EntityPlayer entityPlayer = this.playerEntities.get(i);
            if (s.equals(entityPlayer.getName())) {
                return entityPlayer;
            }
            ++i;
        }
        return null;
    }
    
    public boolean isBlockLoaded(final BlockPos blockPos) {
        return this.isBlockLoaded(blockPos, " ".length() != 0);
    }
    
    @Override
    public WorldType getWorldType() {
        return this.worldInfo.getTerrainType();
    }
    
    public boolean canBlockBePlaced(final Block block, final BlockPos blockPos, final boolean b, final EnumFacing enumFacing, final Entity entity, final ItemStack itemStack) {
        final Block block2 = this.getBlockState(blockPos).getBlock();
        AxisAlignedBB collisionBoundingBox;
        if (b) {
            collisionBoundingBox = null;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            collisionBoundingBox = block.getCollisionBoundingBox(this, blockPos, block.getDefaultState());
        }
        final AxisAlignedBB axisAlignedBB = collisionBoundingBox;
        int n;
        if (axisAlignedBB != null && !this.checkNoEntityCollision(axisAlignedBB, entity)) {
            n = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else if (block2.getMaterial() == Material.circuits && block == Blocks.anvil) {
            n = " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else if (block2.getMaterial().isReplaceable() && block.canReplace(this, blockPos, enumFacing, itemStack)) {
            n = " ".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public void setSpawnPoint(final BlockPos spawn) {
        this.worldInfo.setSpawn(spawn);
    }
    
    public float getCurrentMoonPhaseFactor() {
        return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
    }
    
    public int getLightFor(final EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (blockPos.getY() < 0) {
            blockPos = new BlockPos(blockPos.getX(), "".length(), blockPos.getZ());
        }
        if (!this.isValid(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }
        if (!this.isBlockLoaded(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }
        return this.getChunkFromBlockCoords(blockPos).getLightFor(enumSkyBlock, blockPos);
    }
    
    protected void playMoodSoundAndCheckLight(final int n, final int n2, final Chunk chunk) {
        this.theProfiler.endStartSection(World.I[0x91 ^ 0x8A]);
        if (this.ambientTickCountdown == 0 && !this.isRemote) {
            this.updateLCG = this.updateLCG * "   ".length() + (476084389 + 511821354 - 170932715 + 196931195);
            final int n3 = this.updateLCG >> "  ".length();
            final int n4 = n3 & (0x19 ^ 0x16);
            final int n5 = n3 >> (0x31 ^ 0x39) & (0xAF ^ 0xA0);
            final int n6 = n3 >> (0x71 ^ 0x61) & 138 + 128 - 18 + 7;
            final BlockPos blockPos = new BlockPos(n4, n6, n5);
            final Block block = chunk.getBlock(blockPos);
            final int n7 = n4 + n;
            final int n8 = n5 + n2;
            if (block.getMaterial() == Material.air && this.getLight(blockPos) <= this.rand.nextInt(0xA5 ^ 0xAD) && this.getLightFor(EnumSkyBlock.SKY, blockPos) <= 0) {
                final EntityPlayer closestPlayer = this.getClosestPlayer(n7 + 0.5, n6 + 0.5, n8 + 0.5, 8.0);
                if (closestPlayer != null && closestPlayer.getDistanceSq(n7 + 0.5, n6 + 0.5, n8 + 0.5) > 4.0) {
                    this.playSoundEffect(n7 + 0.5, n6 + 0.5, n8 + 0.5, World.I[0x35 ^ 0x29], 0.7f, 0.8f + this.rand.nextFloat() * 0.2f);
                    this.ambientTickCountdown = this.rand.nextInt(7271 + 5367 - 11022 + 10384) + (3200 + 2043 - 2732 + 3489);
                }
            }
        }
        this.theProfiler.endStartSection(World.I[0xB0 ^ 0xAD]);
        chunk.enqueueRelightChecks();
    }
    
    public boolean isBlockFullCube(final BlockPos blockPos) {
        final IBlockState blockState = this.getBlockState(blockPos);
        final AxisAlignedBB collisionBoundingBox = blockState.getBlock().getCollisionBoundingBox(this, blockPos, blockState);
        if (collisionBoundingBox != null && collisionBoundingBox.getAverageEdgeLength() >= 1.0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isAreaLoaded(final StructureBoundingBox structureBoundingBox) {
        return this.isAreaLoaded(structureBoundingBox, " ".length() != 0);
    }
    
    public void removeEntity(final Entity entity) {
        if (entity.riddenByEntity != null) {
            entity.riddenByEntity.mountEntity(null);
        }
        if (entity.ridingEntity != null) {
            entity.mountEntity(null);
        }
        entity.setDead();
        if (entity instanceof EntityPlayer) {
            this.playerEntities.remove(entity);
            this.updateAllPlayersSleepingFlag();
            this.onEntityRemoved(entity);
        }
    }
    
    public void notifyNeighborsOfStateChange(final BlockPos blockPos, final Block block) {
        this.notifyBlockOfStateChange(blockPos.west(), block);
        this.notifyBlockOfStateChange(blockPos.east(), block);
        this.notifyBlockOfStateChange(blockPos.down(), block);
        this.notifyBlockOfStateChange(blockPos.up(), block);
        this.notifyBlockOfStateChange(blockPos.north(), block);
        this.notifyBlockOfStateChange(blockPos.south(), block);
    }
    
    public BlockPos getSpawnPoint() {
        BlockPos height = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
        if (!this.getWorldBorder().contains(height)) {
            height = this.getHeight(new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
        }
        return height;
    }
    
    public void joinEntityInSurroundings(final Entity entity) {
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        final int length = "  ".length();
        int i = floor_double - length;
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i <= floor_double + length) {
            int j = floor_double2 - length;
            "".length();
            if (-1 < -1) {
                throw null;
            }
            while (j <= floor_double2 + length) {
                this.getChunkFromChunkCoords(i, j);
                ++j;
            }
            ++i;
        }
        if (!this.loadedEntityList.contains(entity)) {
            this.loadedEntityList.add(entity);
        }
    }
    
    public World init() {
        return this;
    }
    
    public void sendQuittingDisconnectingPacket() {
    }
    
    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }
    
    protected void onEntityAdded(final Entity entity) {
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).onEntityAdded(entity);
            ++i;
        }
    }
    
    public void playRecord(final BlockPos blockPos, final String s) {
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < this.worldAccesses.size()) {
            this.worldAccesses.get(i).playRecord(s, blockPos);
            ++i;
        }
    }
    
    public int getLight(BlockPos blockPos) {
        if (blockPos.getY() < 0) {
            return "".length();
        }
        if (blockPos.getY() >= 112 + 72 + 8 + 64) {
            blockPos = new BlockPos(blockPos.getX(), 123 + 179 - 238 + 191, blockPos.getZ());
        }
        return this.getChunkFromBlockCoords(blockPos).getLightSubtracted(blockPos, "".length());
    }
    
    public boolean isAABBInMaterial(final AxisAlignedBB axisAlignedBB, final Material material) {
        final int floor_double = MathHelper.floor_double(axisAlignedBB.minX);
        final int floor_double2 = MathHelper.floor_double(axisAlignedBB.maxX + 1.0);
        final int floor_double3 = MathHelper.floor_double(axisAlignedBB.minY);
        final int floor_double4 = MathHelper.floor_double(axisAlignedBB.maxY + 1.0);
        final int floor_double5 = MathHelper.floor_double(axisAlignedBB.minZ);
        final int floor_double6 = MathHelper.floor_double(axisAlignedBB.maxZ + 1.0);
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = floor_double;
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (i < floor_double2) {
            int j = floor_double3;
            "".length();
            if (1 >= 4) {
                throw null;
            }
            while (j < floor_double4) {
                int k = floor_double5;
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (k < floor_double6) {
                    final IBlockState blockState = this.getBlockState(mutableBlockPos.func_181079_c(i, j, k));
                    if (blockState.getBlock().getMaterial() == material) {
                        final int intValue = blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL);
                        double n = j + " ".length();
                        if (intValue < (0x8A ^ 0x82)) {
                            n = j + " ".length() - intValue / 8.0;
                        }
                        if (n >= axisAlignedBB.minY) {
                            return " ".length() != 0;
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    protected boolean isChunkLoaded(final int n, final int n2, final boolean b) {
        if (this.chunkProvider.chunkExists(n, n2) && (b || !this.chunkProvider.provideChunk(n, n2).isEmpty())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void addWorldAccess(final IWorldAccess worldAccess) {
        this.worldAccesses.add(worldAccess);
    }
    
    protected abstract IChunkProvider createChunkProvider();
    
    public MapStorage getMapStorage() {
        return this.mapStorage;
    }
}
