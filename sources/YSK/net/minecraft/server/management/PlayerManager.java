package net.minecraft.server.management;

import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;

public class PlayerManager
{
    private final List<PlayerInstance> playerInstancesToUpdate;
    private int playerViewRadius;
    private final LongHashMap playerInstances;
    private long previousTotalWorldTime;
    private final int[][] xzDirectionsConst;
    private final List<PlayerInstance> playerInstanceList;
    private static final Logger pmLogger;
    private final List<EntityPlayerMP> players;
    private final WorldServer theWorldServer;
    
    public void setPlayerViewRadius(int clamp_int) {
        clamp_int = MathHelper.clamp_int(clamp_int, "   ".length(), 0x4D ^ 0x6D);
        if (clamp_int != this.playerViewRadius) {
            final int n = clamp_int - this.playerViewRadius;
            final Iterator<EntityPlayerMP> iterator = (Iterator<EntityPlayerMP>)Lists.newArrayList((Iterable)this.players).iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityPlayerMP entityPlayerMP = iterator.next();
                final int n2 = (int)entityPlayerMP.posX >> (0x78 ^ 0x7C);
                final int n3 = (int)entityPlayerMP.posZ >> (0xAE ^ 0xAA);
                if (n > 0) {
                    int i = n2 - clamp_int;
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                    while (i <= n2 + clamp_int) {
                        int j = n3 - clamp_int;
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                        while (j <= n3 + clamp_int) {
                            final PlayerInstance playerInstance = this.getPlayerInstance(i, j, " ".length() != 0);
                            if (!PlayerInstance.access$1(playerInstance).contains(entityPlayerMP)) {
                                playerInstance.addPlayer(entityPlayerMP);
                            }
                            ++j;
                        }
                        ++i;
                    }
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    continue;
                }
                else {
                    int k = n2 - this.playerViewRadius;
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                    while (k <= n2 + this.playerViewRadius) {
                        int l = n3 - this.playerViewRadius;
                        "".length();
                        if (0 < 0) {
                            throw null;
                        }
                        while (l <= n3 + this.playerViewRadius) {
                            if (!this.overlaps(k, l, n2, n3, clamp_int)) {
                                this.getPlayerInstance(k, l, " ".length() != 0).removePlayer(entityPlayerMP);
                            }
                            ++l;
                        }
                        ++k;
                    }
                }
            }
            this.playerViewRadius = clamp_int;
        }
    }
    
    public WorldServer getWorldServer() {
        return this.theWorldServer;
    }
    
    public void removePlayer(final EntityPlayerMP entityPlayerMP) {
        final int n = (int)entityPlayerMP.managedPosX >> (0x58 ^ 0x5C);
        final int n2 = (int)entityPlayerMP.managedPosZ >> (0xBC ^ 0xB8);
        int i = n - this.playerViewRadius;
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i <= n + this.playerViewRadius) {
            int j = n2 - this.playerViewRadius;
            "".length();
            if (1 >= 2) {
                throw null;
            }
            while (j <= n2 + this.playerViewRadius) {
                final PlayerInstance playerInstance = this.getPlayerInstance(i, j, "".length() != 0);
                if (playerInstance != null) {
                    playerInstance.removePlayer(entityPlayerMP);
                }
                ++j;
            }
            ++i;
        }
        this.players.remove(entityPlayerMP);
    }
    
    public boolean hasPlayerInstance(final int n, final int n2) {
        if (this.playerInstances.getValueByKey(n + 2147483647L | n2 + 2147483647L << (0x2F ^ 0xF)) != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean overlaps(final int n, final int n2, final int n3, final int n4, final int n5) {
        final int n6 = n - n3;
        final int n7 = n2 - n4;
        int n8;
        if (n6 >= -n5 && n6 <= n5) {
            if (n7 >= -n5 && n7 <= n5) {
                n8 = " ".length();
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
            else {
                n8 = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
        }
        else {
            n8 = "".length();
        }
        return n8 != 0;
    }
    
    static Logger access$0() {
        return PlayerManager.pmLogger;
    }
    
    public void updateMountedMovingPlayer(final EntityPlayerMP entityPlayerMP) {
        final int n = (int)entityPlayerMP.posX >> (0x24 ^ 0x20);
        final int n2 = (int)entityPlayerMP.posZ >> (0x91 ^ 0x95);
        final double n3 = entityPlayerMP.managedPosX - entityPlayerMP.posX;
        final double n4 = entityPlayerMP.managedPosZ - entityPlayerMP.posZ;
        if (n3 * n3 + n4 * n4 >= 64.0) {
            final int n5 = (int)entityPlayerMP.managedPosX >> (0x1D ^ 0x19);
            final int n6 = (int)entityPlayerMP.managedPosZ >> (0x82 ^ 0x86);
            final int playerViewRadius = this.playerViewRadius;
            final int n7 = n - n5;
            final int n8 = n2 - n6;
            if (n7 != 0 || n8 != 0) {
                int i = n - playerViewRadius;
                "".length();
                if (2 < 1) {
                    throw null;
                }
                while (i <= n + playerViewRadius) {
                    int j = n2 - playerViewRadius;
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                    while (j <= n2 + playerViewRadius) {
                        if (!this.overlaps(i, j, n5, n6, playerViewRadius)) {
                            this.getPlayerInstance(i, j, " ".length() != 0).addPlayer(entityPlayerMP);
                        }
                        if (!this.overlaps(i - n7, j - n8, n, n2, playerViewRadius)) {
                            final PlayerInstance playerInstance = this.getPlayerInstance(i - n7, j - n8, "".length() != 0);
                            if (playerInstance != null) {
                                playerInstance.removePlayer(entityPlayerMP);
                            }
                        }
                        ++j;
                    }
                    ++i;
                }
                this.filterChunkLoadQueue(entityPlayerMP);
                entityPlayerMP.managedPosX = entityPlayerMP.posX;
                entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
            }
        }
    }
    
    public void addPlayer(final EntityPlayerMP entityPlayerMP) {
        final int n = (int)entityPlayerMP.posX >> (0x39 ^ 0x3D);
        final int n2 = (int)entityPlayerMP.posZ >> (0x86 ^ 0x82);
        entityPlayerMP.managedPosX = entityPlayerMP.posX;
        entityPlayerMP.managedPosZ = entityPlayerMP.posZ;
        int i = n - this.playerViewRadius;
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (i <= n + this.playerViewRadius) {
            int j = n2 - this.playerViewRadius;
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (j <= n2 + this.playerViewRadius) {
                this.getPlayerInstance(i, j, " ".length() != 0).addPlayer(entityPlayerMP);
                ++j;
            }
            ++i;
        }
        this.players.add(entityPlayerMP);
        this.filterChunkLoadQueue(entityPlayerMP);
    }
    
    public static int getFurthestViewableBlock(final int n) {
        return n * (0x74 ^ 0x64) - (0x75 ^ 0x65);
    }
    
    public void updatePlayerInstances() {
        final long totalWorldTime = this.theWorldServer.getTotalWorldTime();
        if (totalWorldTime - this.previousTotalWorldTime > 8000L) {
            this.previousTotalWorldTime = totalWorldTime;
            int i = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (i < this.playerInstanceList.size()) {
                final PlayerInstance playerInstance = this.playerInstanceList.get(i);
                playerInstance.onUpdate();
                playerInstance.processChunk();
                ++i;
            }
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            int j = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (j < this.playerInstancesToUpdate.size()) {
                this.playerInstancesToUpdate.get(j).onUpdate();
                ++j;
            }
        }
        this.playerInstancesToUpdate.clear();
        if (this.players.isEmpty() && !this.theWorldServer.provider.canRespawnHere()) {
            this.theWorldServer.theChunkProviderServer.unloadAllChunks();
        }
    }
    
    public void markBlockForUpdate(final BlockPos blockPos) {
        final PlayerInstance playerInstance = this.getPlayerInstance(blockPos.getX() >> (0x91 ^ 0x95), blockPos.getZ() >> (0x31 ^ 0x35), "".length() != 0);
        if (playerInstance != null) {
            playerInstance.flagChunkForUpdate(blockPos.getX() & (0x47 ^ 0x48), blockPos.getY(), blockPos.getZ() & (0x9C ^ 0x93));
        }
    }
    
    static LongHashMap access$2(final PlayerManager playerManager) {
        return playerManager.playerInstances;
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
            if (4 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private PlayerInstance getPlayerInstance(final int n, final int n2, final boolean b) {
        final long n3 = n + 2147483647L | n2 + 2147483647L << (0x4 ^ 0x24);
        PlayerInstance playerInstance = (PlayerInstance)this.playerInstances.getValueByKey(n3);
        if (playerInstance == null && b) {
            playerInstance = new PlayerInstance(n, n2);
            this.playerInstances.add(n3, playerInstance);
            this.playerInstanceList.add(playerInstance);
        }
        return playerInstance;
    }
    
    public void filterChunkLoadQueue(final EntityPlayerMP entityPlayerMP) {
        final ArrayList arrayList = Lists.newArrayList((Iterable)entityPlayerMP.loadedChunks);
        int length = "".length();
        final int playerViewRadius = this.playerViewRadius;
        final int n = (int)entityPlayerMP.posX >> (0x35 ^ 0x31);
        final int n2 = (int)entityPlayerMP.posZ >> (0x4C ^ 0x48);
        int length2 = "".length();
        int length3 = "".length();
        final ChunkCoordIntPair access$0 = PlayerInstance.access$0(this.getPlayerInstance(n, n2, (boolean)(" ".length() != 0)));
        entityPlayerMP.loadedChunks.clear();
        if (arrayList.contains(access$0)) {
            entityPlayerMP.loadedChunks.add(access$0);
        }
        int i = " ".length();
        "".length();
        if (3 == 0) {
            throw null;
        }
        while (i <= playerViewRadius * "  ".length()) {
            int j = "".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (j < "  ".length()) {
                final int[] array = this.xzDirectionsConst[length++ % (0x22 ^ 0x26)];
                int k = "".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
                while (k < i) {
                    length2 += array["".length()];
                    length3 += array[" ".length()];
                    final ChunkCoordIntPair access$2 = PlayerInstance.access$0(this.getPlayerInstance(n + length2, n2 + length3, (boolean)(" ".length() != 0)));
                    if (arrayList.contains(access$2)) {
                        entityPlayerMP.loadedChunks.add(access$2);
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        final int n3 = length % (0x1B ^ 0x1F);
        int l = "".length();
        "".length();
        if (-1 == 4) {
            throw null;
        }
        while (l < playerViewRadius * "  ".length()) {
            length2 += this.xzDirectionsConst[n3]["".length()];
            length3 += this.xzDirectionsConst[n3][" ".length()];
            final ChunkCoordIntPair access$3 = PlayerInstance.access$0(this.getPlayerInstance(n + length2, n2 + length3, (boolean)(" ".length() != 0)));
            if (arrayList.contains(access$3)) {
                entityPlayerMP.loadedChunks.add(access$3);
            }
            ++l;
        }
    }
    
    static {
        pmLogger = LogManager.getLogger();
    }
    
    static List access$4(final PlayerManager playerManager) {
        return playerManager.playerInstancesToUpdate;
    }
    
    static List access$3(final PlayerManager playerManager) {
        return playerManager.playerInstanceList;
    }
    
    static WorldServer access$1(final PlayerManager playerManager) {
        return playerManager.theWorldServer;
    }
    
    public boolean isPlayerWatchingChunk(final EntityPlayerMP entityPlayerMP, final int n, final int n2) {
        final PlayerInstance playerInstance = this.getPlayerInstance(n, n2, "".length() != 0);
        if (playerInstance != null && PlayerInstance.access$1(playerInstance).contains(entityPlayerMP) && !entityPlayerMP.loadedChunks.contains(PlayerInstance.access$0(playerInstance))) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public PlayerManager(final WorldServer theWorldServer) {
        this.players = (List<EntityPlayerMP>)Lists.newArrayList();
        this.playerInstances = new LongHashMap();
        this.playerInstancesToUpdate = (List<PlayerInstance>)Lists.newArrayList();
        this.playerInstanceList = (List<PlayerInstance>)Lists.newArrayList();
        final int[][] xzDirectionsConst = new int[0xC0 ^ 0xC4][];
        final int length = "".length();
        final int[] array = new int["  ".length()];
        array["".length()] = " ".length();
        xzDirectionsConst[length] = array;
        final int length2 = " ".length();
        final int[] array2 = new int["  ".length()];
        array2[" ".length()] = " ".length();
        xzDirectionsConst[length2] = array2;
        final int length3 = "  ".length();
        final int[] array3 = new int["  ".length()];
        array3["".length()] = -" ".length();
        xzDirectionsConst[length3] = array3;
        final int length4 = "   ".length();
        final int[] array4 = new int["  ".length()];
        array4[" ".length()] = -" ".length();
        xzDirectionsConst[length4] = array4;
        this.xzDirectionsConst = xzDirectionsConst;
        this.theWorldServer = theWorldServer;
        this.setPlayerViewRadius(theWorldServer.getMinecraftServer().getConfigurationManager().getViewDistance());
    }
    
    class PlayerInstance
    {
        private static final String[] I;
        private short[] locationOfBlockChange;
        private long previousWorldTime;
        private final ChunkCoordIntPair chunkCoords;
        private int flagsYAreasToUpdate;
        final PlayerManager this$0;
        private int numBlocksToUpdate;
        private final List<EntityPlayerMP> playersWatchingChunk;
        
        public void removePlayer(final EntityPlayerMP entityPlayerMP) {
            if (this.playersWatchingChunk.contains(entityPlayerMP)) {
                final Chunk chunkFromChunkCoords = PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
                if (chunkFromChunkCoords.isPopulated()) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(new S21PacketChunkData(chunkFromChunkCoords, (boolean)(" ".length() != 0), "".length()));
                }
                this.playersWatchingChunk.remove(entityPlayerMP);
                entityPlayerMP.loadedChunks.remove(this.chunkCoords);
                if (this.playersWatchingChunk.isEmpty()) {
                    final long n = this.chunkCoords.chunkXPos + 2147483647L | this.chunkCoords.chunkZPos + 2147483647L << (0x58 ^ 0x78);
                    this.increaseInhabitedTime(chunkFromChunkCoords);
                    PlayerManager.access$2(this.this$0).remove(n);
                    PlayerManager.access$3(this.this$0).remove(this);
                    if (this.numBlocksToUpdate > 0) {
                        PlayerManager.access$4(this.this$0).remove(this);
                    }
                    this.this$0.getWorldServer().theChunkProviderServer.dropChunk(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos);
                }
            }
        }
        
        public void sendToAllPlayersWatchingChunk(final Packet packet) {
            int i = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (i < this.playersWatchingChunk.size()) {
                final EntityPlayerMP entityPlayerMP = this.playersWatchingChunk.get(i);
                if (!entityPlayerMP.loadedChunks.contains(this.chunkCoords)) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(packet);
                }
                ++i;
            }
        }
        
        private void increaseInhabitedTime(final Chunk chunk) {
            chunk.setInhabitedTime(chunk.getInhabitedTime() + PlayerManager.access$1(this.this$0).getTotalWorldTime() - this.previousWorldTime);
            this.previousWorldTime = PlayerManager.access$1(this.this$0).getTotalWorldTime();
        }
        
        private void sendTileToAllPlayersWatchingChunk(final TileEntity tileEntity) {
            if (tileEntity != null) {
                final Packet descriptionPacket = tileEntity.getDescriptionPacket();
                if (descriptionPacket != null) {
                    this.sendToAllPlayersWatchingChunk(descriptionPacket);
                }
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
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public void addPlayer(final EntityPlayerMP entityPlayerMP) {
            if (this.playersWatchingChunk.contains(entityPlayerMP)) {
                final Logger access$0 = PlayerManager.access$0();
                final String s = PlayerInstance.I["".length()];
                final Object[] array = new Object["   ".length()];
                array["".length()] = entityPlayerMP;
                array[" ".length()] = this.chunkCoords.chunkXPos;
                array["  ".length()] = this.chunkCoords.chunkZPos;
                access$0.debug(s, array);
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
            else {
                if (this.playersWatchingChunk.isEmpty()) {
                    this.previousWorldTime = PlayerManager.access$1(this.this$0).getTotalWorldTime();
                }
                this.playersWatchingChunk.add(entityPlayerMP);
                entityPlayerMP.loadedChunks.add(this.chunkCoords);
            }
        }
        
        static ChunkCoordIntPair access$0(final PlayerInstance playerInstance) {
            return playerInstance.chunkCoords;
        }
        
        public void flagChunkForUpdate(final int n, final int n2, final int n3) {
            if (this.numBlocksToUpdate == 0) {
                PlayerManager.access$4(this.this$0).add(this);
            }
            this.flagsYAreasToUpdate |= " ".length() << (n2 >> (0x6D ^ 0x69));
            if (this.numBlocksToUpdate < (0xEB ^ 0xAB)) {
                final short n4 = (short)(n << (0x37 ^ 0x3B) | n3 << (0x4B ^ 0x43) | n2);
                int i = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (i < this.numBlocksToUpdate) {
                    if (this.locationOfBlockChange[i] == n4) {
                        return;
                    }
                    ++i;
                }
                final short[] locationOfBlockChange = this.locationOfBlockChange;
                final int numBlocksToUpdate = this.numBlocksToUpdate;
                this.numBlocksToUpdate = numBlocksToUpdate + " ".length();
                locationOfBlockChange[numBlocksToUpdate] = n4;
            }
        }
        
        static List access$1(final PlayerInstance playerInstance) {
            return playerInstance.playersWatchingChunk;
        }
        
        public void onUpdate() {
            if (this.numBlocksToUpdate != 0) {
                if (this.numBlocksToUpdate == " ".length()) {
                    final BlockPos blockPos = new BlockPos((this.locationOfBlockChange["".length()] >> (0x75 ^ 0x79) & (0x65 ^ 0x6A)) + this.chunkCoords.chunkXPos * (0x40 ^ 0x50), this.locationOfBlockChange["".length()] & 131 + 185 - 121 + 60, (this.locationOfBlockChange["".length()] >> (0x19 ^ 0x11) & (0xB5 ^ 0xBA)) + this.chunkCoords.chunkZPos * (0x87 ^ 0x97));
                    this.sendToAllPlayersWatchingChunk(new S23PacketBlockChange(PlayerManager.access$1(this.this$0), blockPos));
                    if (PlayerManager.access$1(this.this$0).getBlockState(blockPos).getBlock().hasTileEntity()) {
                        this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$1(this.this$0).getTileEntity(blockPos));
                        "".length();
                        if (3 < 0) {
                            throw null;
                        }
                    }
                }
                else if (this.numBlocksToUpdate == (0x3A ^ 0x7A)) {
                    final int n = this.chunkCoords.chunkXPos * (0xA0 ^ 0xB0);
                    final int n2 = this.chunkCoords.chunkZPos * (0x57 ^ 0x47);
                    this.sendToAllPlayersWatchingChunk(new S21PacketChunkData(PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos), (boolean)("".length() != 0), this.flagsYAreasToUpdate));
                    int i = "".length();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    while (i < (0x6A ^ 0x7A)) {
                        if ((this.flagsYAreasToUpdate & " ".length() << i) != 0x0) {
                            final int n3 = i << (0x39 ^ 0x3D);
                            final List<TileEntity> tileEntitiesIn = PlayerManager.access$1(this.this$0).getTileEntitiesIn(n, n3, n2, n + (0x20 ^ 0x30), n3 + (0xB9 ^ 0xA9), n2 + (0x8B ^ 0x9B));
                            int j = "".length();
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                            while (j < tileEntitiesIn.size()) {
                                this.sendTileToAllPlayersWatchingChunk(tileEntitiesIn.get(j));
                                ++j;
                            }
                        }
                        ++i;
                    }
                    "".length();
                    if (1 == -1) {
                        throw null;
                    }
                }
                else {
                    this.sendToAllPlayersWatchingChunk(new S22PacketMultiBlockChange(this.numBlocksToUpdate, this.locationOfBlockChange, PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos)));
                    int k = "".length();
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    while (k < this.numBlocksToUpdate) {
                        final BlockPos blockPos2 = new BlockPos((this.locationOfBlockChange[k] >> (0x7C ^ 0x70) & (0x14 ^ 0x1B)) + this.chunkCoords.chunkXPos * (0x9 ^ 0x19), this.locationOfBlockChange[k] & 253 + 213 - 324 + 113, (this.locationOfBlockChange[k] >> (0x8B ^ 0x83) & (0x9F ^ 0x90)) + this.chunkCoords.chunkZPos * (0x5B ^ 0x4B));
                        if (PlayerManager.access$1(this.this$0).getBlockState(blockPos2).getBlock().hasTileEntity()) {
                            this.sendTileToAllPlayersWatchingChunk(PlayerManager.access$1(this.this$0).getTileEntity(blockPos2));
                        }
                        ++k;
                    }
                }
                this.numBlocksToUpdate = "".length();
                this.flagsYAreasToUpdate = "".length();
            }
        }
        
        public PlayerInstance(final PlayerManager this$0, final int n, final int n2) {
            this.this$0 = this$0;
            this.playersWatchingChunk = (List<EntityPlayerMP>)Lists.newArrayList();
            this.locationOfBlockChange = new short[0x68 ^ 0x28];
            this.chunkCoords = new ChunkCoordIntPair(n, n2);
            this$0.getWorldServer().theChunkProviderServer.loadChunk(n, n2);
        }
        
        public void processChunk() {
            this.increaseInhabitedTime(PlayerManager.access$1(this.this$0).getChunkFromChunkCoords(this.chunkCoords.chunkXPos, this.chunkCoords.chunkZPos));
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("2*\u0010\u0015,\u0010k\r\u0016i\u0015/\u001dY9\u0018*\u0000\u001c;Zk\u0002\u0004i\u0015'\u000b\u001c(\u00102Y\u0010:T\"\u0017Y*\u001c>\u0017\u0012i\u000f6UY2\t", "tKyyI");
        }
        
        static {
            I();
        }
    }
}
