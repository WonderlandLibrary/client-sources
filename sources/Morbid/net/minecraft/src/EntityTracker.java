package net.minecraft.src;

import java.util.concurrent.*;
import java.util.*;

public class EntityTracker
{
    private final WorldServer theWorld;
    private Set trackedEntities;
    private IntHashMap trackedEntityIDs;
    private int entityViewDistance;
    
    public EntityTracker(final WorldServer par1WorldServer) {
        this.trackedEntities = new HashSet();
        this.trackedEntityIDs = new IntHashMap();
        this.theWorld = par1WorldServer;
        this.entityViewDistance = par1WorldServer.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
    }
    
    public void addEntityToTracker(final Entity par1Entity) {
        if (par1Entity instanceof EntityPlayerMP) {
            this.addEntityToTracker(par1Entity, 512, 2);
            final EntityPlayerMP var2 = (EntityPlayerMP)par1Entity;
            for (final EntityTrackerEntry var4 : this.trackedEntities) {
                if (var4.myEntity != var2) {
                    var4.tryStartWachingThis(var2);
                }
            }
        }
        else if (par1Entity instanceof EntityFishHook) {
            this.addEntityToTracker(par1Entity, 64, 5, true);
        }
        else if (par1Entity instanceof EntityArrow) {
            this.addEntityToTracker(par1Entity, 64, 20, false);
        }
        else if (par1Entity instanceof EntitySmallFireball) {
            this.addEntityToTracker(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntityFireball) {
            this.addEntityToTracker(par1Entity, 64, 10, false);
        }
        else if (par1Entity instanceof EntitySnowball) {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderPearl) {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityEnderEye) {
            this.addEntityToTracker(par1Entity, 64, 4, true);
        }
        else if (par1Entity instanceof EntityEgg) {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityPotion) {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityExpBottle) {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityFireworkRocket) {
            this.addEntityToTracker(par1Entity, 64, 10, true);
        }
        else if (par1Entity instanceof EntityItem) {
            this.addEntityToTracker(par1Entity, 64, 20, true);
        }
        else if (par1Entity instanceof EntityMinecart) {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityBoat) {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntitySquid) {
            this.addEntityToTracker(par1Entity, 64, 3, true);
        }
        else if (par1Entity instanceof EntityWither) {
            this.addEntityToTracker(par1Entity, 80, 3, false);
        }
        else if (par1Entity instanceof EntityBat) {
            this.addEntityToTracker(par1Entity, 80, 3, false);
        }
        else if (par1Entity instanceof IAnimals) {
            this.addEntityToTracker(par1Entity, 80, 3, true);
        }
        else if (par1Entity instanceof EntityDragon) {
            this.addEntityToTracker(par1Entity, 160, 3, true);
        }
        else if (par1Entity instanceof EntityTNTPrimed) {
            this.addEntityToTracker(par1Entity, 160, 10, true);
        }
        else if (par1Entity instanceof EntityFallingSand) {
            this.addEntityToTracker(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityPainting) {
            this.addEntityToTracker(par1Entity, 160, Integer.MAX_VALUE, false);
        }
        else if (par1Entity instanceof EntityXPOrb) {
            this.addEntityToTracker(par1Entity, 160, 20, true);
        }
        else if (par1Entity instanceof EntityEnderCrystal) {
            this.addEntityToTracker(par1Entity, 256, Integer.MAX_VALUE, false);
        }
        else if (par1Entity instanceof EntityItemFrame) {
            this.addEntityToTracker(par1Entity, 160, Integer.MAX_VALUE, false);
        }
    }
    
    public void addEntityToTracker(final Entity par1Entity, final int par2, final int par3) {
        this.addEntityToTracker(par1Entity, par2, par3, false);
    }
    
    public void addEntityToTracker(final Entity par1Entity, int par2, final int par3, final boolean par4) {
        if (par2 > this.entityViewDistance) {
            par2 = this.entityViewDistance;
        }
        try {
            if (this.trackedEntityIDs.containsItem(par1Entity.entityId)) {
                throw new IllegalStateException("Entity is already tracked!");
            }
            final EntityTrackerEntry var5 = new EntityTrackerEntry(par1Entity, par2, par3, par4);
            this.trackedEntities.add(var5);
            this.trackedEntityIDs.addKey(par1Entity.entityId, var5);
            var5.sendEventsToPlayers(this.theWorld.playerEntities);
        }
        catch (Throwable var7) {
            final CrashReport var6 = CrashReport.makeCrashReport(var7, "Adding entity to track");
            final CrashReportCategory var8 = var6.makeCategory("Entity To Track");
            var8.addCrashSection("Tracking range", String.valueOf(par2) + " blocks");
            var8.addCrashSectionCallable("Update interval", new CallableEntityTracker(this, par3));
            par1Entity.func_85029_a(var8);
            final CrashReportCategory var9 = var6.makeCategory("Entity That Is Already Tracked");
            ((EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.entityId)).myEntity.func_85029_a(var9);
            try {
                throw new ReportedException(var6);
            }
            catch (ReportedException var10) {
                System.err.println("\"Silently\" catching entity tracking error.");
                var10.printStackTrace();
            }
        }
    }
    
    public void removeEntityFromAllTrackingPlayers(final Entity par1Entity) {
        if (par1Entity instanceof EntityPlayerMP) {
            final EntityPlayerMP var2 = (EntityPlayerMP)par1Entity;
            for (final EntityTrackerEntry var4 : this.trackedEntities) {
                var4.removeFromWatchingList(var2);
            }
        }
        final EntityTrackerEntry var5 = (EntityTrackerEntry)this.trackedEntityIDs.removeObject(par1Entity.entityId);
        if (var5 != null) {
            this.trackedEntities.remove(var5);
            var5.informAllAssociatedPlayersOfItemDestruction();
        }
    }
    
    public void updateTrackedEntities() {
        final ArrayList var1 = new ArrayList();
        for (final EntityTrackerEntry var3 : this.trackedEntities) {
            var3.sendLocationToAllClients(this.theWorld.playerEntities);
            if (var3.playerEntitiesUpdated && var3.myEntity instanceof EntityPlayerMP) {
                var1.add(var3.myEntity);
            }
        }
        for (int var4 = 0; var4 < var1.size(); ++var4) {
            final EntityPlayerMP var5 = var1.get(var4);
            for (final EntityTrackerEntry var7 : this.trackedEntities) {
                if (var7.myEntity != var5) {
                    var7.tryStartWachingThis(var5);
                }
            }
        }
    }
    
    public void sendPacketToAllPlayersTrackingEntity(final Entity par1Entity, final Packet par2Packet) {
        final EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.entityId);
        if (var3 != null) {
            var3.sendPacketToAllTrackingPlayers(par2Packet);
        }
    }
    
    public void sendPacketToAllAssociatedPlayers(final Entity par1Entity, final Packet par2Packet) {
        final EntityTrackerEntry var3 = (EntityTrackerEntry)this.trackedEntityIDs.lookup(par1Entity.entityId);
        if (var3 != null) {
            var3.sendPacketToAllAssociatedPlayers(par2Packet);
        }
    }
    
    public void removePlayerFromTrackers(final EntityPlayerMP par1EntityPlayerMP) {
        for (final EntityTrackerEntry var3 : this.trackedEntities) {
            var3.removePlayerFromTracker(par1EntityPlayerMP);
        }
    }
    
    public void func_85172_a(final EntityPlayerMP par1EntityPlayerMP, final Chunk par2Chunk) {
        for (final EntityTrackerEntry var4 : this.trackedEntities) {
            if (var4.myEntity != par1EntityPlayerMP && var4.myEntity.chunkCoordX == par2Chunk.xPosition && var4.myEntity.chunkCoordZ == par2Chunk.zPosition) {
                var4.tryStartWachingThis(par1EntityPlayerMP);
            }
        }
    }
}
