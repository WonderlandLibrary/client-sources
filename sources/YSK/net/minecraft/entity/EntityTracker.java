package net.minecraft.entity;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;
import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.network.*;
import net.minecraft.world.chunk.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public class EntityTracker
{
    private int maxTrackingDistanceThreshold;
    private final WorldServer theWorld;
    private static final String[] I;
    private static final Logger logger;
    private IntHashMap<EntityTrackerEntry> trackedEntityHashTable;
    private Set<EntityTrackerEntry> trackedEntities;
    
    public void trackEntity(final Entity entity, final int n, final int n2) {
        this.addEntityToTracker(entity, n, n2, "".length() != 0);
    }
    
    public void removePlayerFromTrackers(final EntityPlayerMP entityPlayerMP) {
        final Iterator<EntityTrackerEntry> iterator = this.trackedEntities.iterator();
        "".length();
        if (true != true) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().removeTrackedPlayerSymmetric(entityPlayerMP);
        }
    }
    
    public EntityTracker(final WorldServer theWorld) {
        this.trackedEntities = (Set<EntityTrackerEntry>)Sets.newHashSet();
        this.trackedEntityHashTable = new IntHashMap<EntityTrackerEntry>();
        this.theWorld = theWorld;
        this.maxTrackingDistanceThreshold = theWorld.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
    }
    
    public void trackEntity(final Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            this.trackEntity(entity, 413 + 60 - 89 + 128, "  ".length());
            final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
            final Iterator<EntityTrackerEntry> iterator = this.trackedEntities.iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityTrackerEntry entityTrackerEntry = iterator.next();
                if (entityTrackerEntry.trackedEntity != entityPlayerMP) {
                    entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
                }
            }
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityFishHook) {
            this.addEntityToTracker(entity, 0xD3 ^ 0x93, 0xBB ^ 0xBE, " ".length() != 0);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityArrow) {
            this.addEntityToTracker(entity, 0x54 ^ 0x14, 0x95 ^ 0x81, "".length() != 0);
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else if (entity instanceof EntitySmallFireball) {
            this.addEntityToTracker(entity, 0x48 ^ 0x8, 0xE ^ 0x4, "".length() != 0);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (entity instanceof EntityFireball) {
            this.addEntityToTracker(entity, 0x72 ^ 0x32, 0x5A ^ 0x50, "".length() != 0);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (entity instanceof EntitySnowball) {
            this.addEntityToTracker(entity, 0x41 ^ 0x1, 0x3 ^ 0x9, " ".length() != 0);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (entity instanceof EntityEnderPearl) {
            this.addEntityToTracker(entity, 0xF7 ^ 0xB7, 0x18 ^ 0x12, " ".length() != 0);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else if (entity instanceof EntityEnderEye) {
            this.addEntityToTracker(entity, 0x6F ^ 0x2F, 0x86 ^ 0x82, " ".length() != 0);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (entity instanceof EntityEgg) {
            this.addEntityToTracker(entity, 0xEF ^ 0xAF, 0x83 ^ 0x89, " ".length() != 0);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (entity instanceof EntityPotion) {
            this.addEntityToTracker(entity, 0x4D ^ 0xD, 0x76 ^ 0x7C, " ".length() != 0);
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityExpBottle) {
            this.addEntityToTracker(entity, 0xDC ^ 0x9C, 0xBB ^ 0xB1, " ".length() != 0);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityFireworkRocket) {
            this.addEntityToTracker(entity, 0xEE ^ 0xAE, 0xA7 ^ 0xAD, " ".length() != 0);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityItem) {
            this.addEntityToTracker(entity, 0xD0 ^ 0x90, 0xBE ^ 0xAA, " ".length() != 0);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (entity instanceof EntityMinecart) {
            this.addEntityToTracker(entity, 0xCB ^ 0x9B, "   ".length(), " ".length() != 0);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else if (entity instanceof EntityBoat) {
            this.addEntityToTracker(entity, 0x7A ^ 0x2A, "   ".length(), " ".length() != 0);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else if (entity instanceof EntitySquid) {
            this.addEntityToTracker(entity, 0x4 ^ 0x44, "   ".length(), " ".length() != 0);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (entity instanceof EntityWither) {
            this.addEntityToTracker(entity, 0x27 ^ 0x77, "   ".length(), "".length() != 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (entity instanceof EntityBat) {
            this.addEntityToTracker(entity, 0x2D ^ 0x7D, "   ".length(), "".length() != 0);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (entity instanceof EntityDragon) {
            this.addEntityToTracker(entity, 147 + 2 - 56 + 67, "   ".length(), " ".length() != 0);
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else if (entity instanceof IAnimals) {
            this.addEntityToTracker(entity, 0xF3 ^ 0xA3, "   ".length(), " ".length() != 0);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (entity instanceof EntityTNTPrimed) {
            this.addEntityToTracker(entity, 123 + 36 - 74 + 75, 0x73 ^ 0x79, " ".length() != 0);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityFallingBlock) {
            this.addEntityToTracker(entity, 156 + 64 - 162 + 102, 0xD1 ^ 0xC5, " ".length() != 0);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityHanging) {
            this.addEntityToTracker(entity, 15 + 89 - 65 + 121, 613187743 + 2146887817 - 772135291 + 159543378, "".length() != 0);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else if (entity instanceof EntityArmorStand) {
            this.addEntityToTracker(entity, 128 + 124 - 245 + 153, "   ".length(), " ".length() != 0);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (entity instanceof EntityXPOrb) {
            this.addEntityToTracker(entity, 137 + 159 - 191 + 55, 0x99 ^ 0x8D, " ".length() != 0);
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else if (entity instanceof EntityEnderCrystal) {
            this.addEntityToTracker(entity, 111 + 253 - 144 + 36, 1297081236 + 273266014 - 614395686 + 1191532083, "".length() != 0);
        }
    }
    
    public void untrackEntity(final Entity entity) {
        if (entity instanceof EntityPlayerMP) {
            final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)entity;
            final Iterator<EntityTrackerEntry> iterator = this.trackedEntities.iterator();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().removeFromTrackedPlayers(entityPlayerMP);
            }
        }
        final EntityTrackerEntry entityTrackerEntry = this.trackedEntityHashTable.removeObject(entity.getEntityId());
        if (entityTrackerEntry != null) {
            this.trackedEntities.remove(entityTrackerEntry);
            entityTrackerEntry.sendDestroyEntityPacketToTrackedPlayers();
        }
    }
    
    private static void I() {
        (I = new String[0x8E ^ 0x86])["".length()] = I("$&<\u0018#\u0018h!\u0002w\u0000$:\u00146\u00051h\u0005%\u0000+#\u00143@", "aHHqW");
        EntityTracker.I[" ".length()] = I("%\u0013\"\b*\u0003W#\u000f0\r\u0003?A0\u000bW2\u0013%\u0007\u001c", "dwFaD");
        EntityTracker.I["  ".length()] = I("1;\u0015!%\ru5'q '\u0000+:", "tUaHQ");
        EntityTracker.I["   ".length()] = I("\u00198,;\u001c$$*x\u0005,$*=", "MJMXw");
        EntityTracker.I[0xB5 ^ 0xB1] = I("a\u0015\u000b,:*\u0004", "AwgCY");
        EntityTracker.I[0x46 ^ 0x43] = I("\u0004&\u0000):4v\r&:4$\u0012)\"", "QVdHN");
        EntityTracker.I[0xB0 ^ 0xB6] = I("\t4\u00018=5z!9(8z<\"i\r6\u00074((#U\u0005;-9\u001e4-", "LZuQI");
        EntityTracker.I[0x88 ^ 0x8F] = I("W\u0001\r\r\u001f\u001b&\b\u0018XU1\u0005\u0015\u0019\u001d;\n\u0006Z\u0010<\u0010\b\u000e\fr\u0010\u0013\u001b\u00169\r\u000f\u001dU7\u0016\u0013\u0015\u0007|", "uRdaz");
    }
    
    public void updateTrackedEntities() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<EntityTrackerEntry> iterator = this.trackedEntities.iterator();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityTrackerEntry entityTrackerEntry = iterator.next();
            entityTrackerEntry.updatePlayerList(this.theWorld.playerEntities);
            if (entityTrackerEntry.playerEntitiesUpdated && entityTrackerEntry.trackedEntity instanceof EntityPlayerMP) {
                arrayList.add(entityTrackerEntry.trackedEntity);
            }
        }
        int i = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < arrayList.size()) {
            final EntityPlayerMP entityPlayerMP = arrayList.get(i);
            final Iterator<EntityTrackerEntry> iterator2 = this.trackedEntities.iterator();
            "".length();
            if (false) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final EntityTrackerEntry entityTrackerEntry2 = iterator2.next();
                if (entityTrackerEntry2.trackedEntity != entityPlayerMP) {
                    entityTrackerEntry2.updatePlayerEntity(entityPlayerMP);
                }
            }
            ++i;
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public void sendToAllTrackingEntity(final Entity entity, final Packet packet) {
        final EntityTrackerEntry entityTrackerEntry = this.trackedEntityHashTable.lookup(entity.getEntityId());
        if (entityTrackerEntry != null) {
            entityTrackerEntry.sendPacketToTrackedPlayers(packet);
        }
    }
    
    public void func_85172_a(final EntityPlayerMP entityPlayerMP, final Chunk chunk) {
        final Iterator<EntityTrackerEntry> iterator = this.trackedEntities.iterator();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityTrackerEntry entityTrackerEntry = iterator.next();
            if (entityTrackerEntry.trackedEntity != entityPlayerMP && entityTrackerEntry.trackedEntity.chunkCoordX == chunk.xPosition && entityTrackerEntry.trackedEntity.chunkCoordZ == chunk.zPosition) {
                entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
            }
        }
    }
    
    public void func_180245_a(final EntityPlayerMP entityPlayerMP) {
        final Iterator<EntityTrackerEntry> iterator = this.trackedEntities.iterator();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityTrackerEntry entityTrackerEntry = iterator.next();
            if (entityTrackerEntry.trackedEntity == entityPlayerMP) {
                entityTrackerEntry.updatePlayerEntities(this.theWorld.playerEntities);
                "".length();
                if (3 == 1) {
                    throw null;
                }
                continue;
            }
            else {
                entityTrackerEntry.updatePlayerEntity(entityPlayerMP);
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void addEntityToTracker(final Entity entity, int maxTrackingDistanceThreshold, final int n, final boolean b) {
        if (maxTrackingDistanceThreshold > this.maxTrackingDistanceThreshold) {
            maxTrackingDistanceThreshold = this.maxTrackingDistanceThreshold;
        }
        try {
            if (this.trackedEntityHashTable.containsItem(entity.getEntityId())) {
                throw new IllegalStateException(EntityTracker.I["".length()]);
            }
            final EntityTrackerEntry entityTrackerEntry = new EntityTrackerEntry(entity, maxTrackingDistanceThreshold, n, b);
            this.trackedEntities.add(entityTrackerEntry);
            this.trackedEntityHashTable.addKey(entity.getEntityId(), entityTrackerEntry);
            entityTrackerEntry.updatePlayerEntities(this.theWorld.playerEntities);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, EntityTracker.I[" ".length()]);
            final CrashReportCategory category = crashReport.makeCategory(EntityTracker.I["  ".length()]);
            category.addCrashSection(EntityTracker.I["   ".length()], String.valueOf(maxTrackingDistanceThreshold) + EntityTracker.I[0x7F ^ 0x7B]);
            category.addCrashSectionCallable(EntityTracker.I[0x32 ^ 0x37], new Callable<String>(this, n) {
                private static final String[] I;
                final EntityTracker this$0;
                private final int val$updateFrequency;
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
                
                static {
                    I();
                }
                
                @Override
                public String call() throws Exception {
                    String s = EntityTracker$1.I["".length()] + this.val$updateFrequency + EntityTracker$1.I[" ".length()];
                    if (this.val$updateFrequency == 1315547264 + 2045003093 + 1643851176 + 1438049410) {
                        s = EntityTracker$1.I["  ".length()] + s + EntityTracker$1.I["   ".length()];
                    }
                    return s;
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
                        if (1 <= -1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                private static void I() {
                    (I = new String[0x9B ^ 0x9F])["".length()] = I(")*!+m\u0016!0n", "fDBNM");
                    EntityTracker$1.I[" ".length()] = I("s\r;\u00011 ", "SyRbZ");
                    EntityTracker$1.I["  ".length()] = I("\f\b\u000f\u001d\b4\u0004W\\", "Aiwte");
                    EntityTracker$1.I["   ".length()] = I("c", "JlsOp");
                }
            });
            entity.addEntityCrashInfo(category);
            this.trackedEntityHashTable.lookup(entity.getEntityId()).trackedEntity.addEntityCrashInfo(crashReport.makeCategory(EntityTracker.I[0x56 ^ 0x50]));
            try {
                throw new ReportedException(crashReport);
            }
            catch (ReportedException ex) {
                EntityTracker.logger.error(EntityTracker.I[0x5E ^ 0x59], (Throwable)ex);
            }
        }
    }
    
    public void func_151248_b(final Entity entity, final Packet packet) {
        final EntityTrackerEntry entityTrackerEntry = this.trackedEntityHashTable.lookup(entity.getEntityId());
        if (entityTrackerEntry != null) {
            entityTrackerEntry.func_151261_b(packet);
        }
    }
}
