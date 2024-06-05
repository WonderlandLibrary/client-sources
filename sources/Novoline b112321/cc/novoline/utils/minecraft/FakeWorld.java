package cc.novoline.utils.minecraft;

import cc.novoline.utils.minecraft.FakeWorld$FakeChunkProvider;
import cc.novoline.utils.minecraft.FakeWorld$FakeSaveHandler;
import cc.novoline.utils.minecraft.FakeWorld$FakeWorldProvider;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;

public final class FakeWorld extends World {
   private static String[] O;

   public FakeWorld() {
      super(new FakeWorld$FakeSaveHandler(), new WorldInfo(new NBTTagCompound()), new FakeWorld$FakeWorldProvider(), (Profiler)null, false);
   }

   public void markBlocksDirtyVertical(int var1, int var2, int var3, int var4) {
   }

   public void markBlockRangeForRenderUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   protected boolean isChunkLoaded(int var1, int var2, boolean var3) {
      return true;
   }

   public float getLightBrightness(BlockPos var1) {
      return 14.0F;
   }

   public boolean isDaytime() {
      return true;
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 var1, Vec3 var2) {
      return null;
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 var1, Vec3 var2, boolean var3) {
      return null;
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 var1, Vec3 var2, boolean var3, boolean var4, boolean var5) {
      return null;
   }

   public void playSoundAtEntity(Entity var1, String var2, float var3, float var4) {
   }

   public void playSoundToNearExcept(EntityPlayer var1, String var2, float var3, float var4) {
   }

   public void playSoundEffect(double var1, double var3, double var5, String var7, float var8, float var9) {
   }

   public void playSound(double var1, double var3, double var5, String var7, float var8, float var9, boolean var10) {
   }

   public boolean addWeatherEffect(Entity var1) {
      return false;
   }

   public boolean spawnEntityInWorld(Entity var1) {
      return false;
   }

   public void onEntityAdded(Entity var1) {
   }

   public void onEntityRemoved(Entity var1) {
   }

   public void removeEntity(Entity var1) {
   }

   public void removePlayerEntityDangerously(Entity var1) {
   }

   public int calculateSkylightSubtracted(float var1) {
      return 6;
   }

   public void removeWorldAccess(IWorldAccess var1) {
   }

   public void updateEntities() {
   }

   public void updateEntity(Entity var1) {
   }

   public void updateEntityWithOptionalForce(Entity var1, boolean var2) {
   }

   public boolean checkNoEntityCollision(AxisAlignedBB var1) {
      return true;
   }

   public boolean checkNoEntityCollision(AxisAlignedBB var1, Entity var2) {
      return true;
   }

   public boolean checkBlockCollision(AxisAlignedBB var1) {
      return false;
   }

   public boolean isAnyLiquid(AxisAlignedBB var1) {
      return false;
   }

   public boolean handleMaterialAcceleration(AxisAlignedBB var1, Material var2, Entity var3) {
      return false;
   }

   public boolean isMaterialInBB(AxisAlignedBB var1, Material var2) {
      return false;
   }

   public boolean isAABBInMaterial(AxisAlignedBB var1, Material var2) {
      return false;
   }

   public String getDebugLoadedEntities() {
      return "";
   }

   public String getProviderName() {
      return "";
   }

   public void markTileEntityForRemoval(TileEntity var1) {
   }

   public void tick() {
   }

   protected void updateWeather() {
   }

   protected int getRenderDistanceChunks() {
      return 0;
   }

   public void tickUpdates(boolean var1) {
   }

   public List getPendingBlockUpdates(Chunk var1, boolean var2) {
      return null;
   }

   public Entity findNearestEntityWithinAABB(Class var1, AxisAlignedBB var2, Entity var3) {
      return null;
   }

   public int countEntities(Class var1) {
      return 0;
   }

   public void checkSessionLock() {
   }

   public long getSeed() {
      return 1L;
   }

   public long getTotalWorldTime() {
      return 1L;
   }

   public long getWorldTime() {
      return 1L;
   }

   public void setWorldTime(long var1) {
   }

   public BlockPos getSpawnPoint() {
      return new BlockPos(0, 64, 0);
   }

   public void joinEntityInSurroundings(Entity var1) {
   }

   public void setEntityState(Entity var1, byte var2) {
   }

   public void updateAllPlayersSleepingFlag() {
   }

   public void setThunderStrength(float var1) {
   }

   public float getRainStrength(float var1) {
      return 0.0F;
   }

   public void setRainStrength(float var1) {
   }

   public boolean isThundering() {
      return false;
   }

   public boolean isRaining() {
      return false;
   }

   public void setItemData(String var1, WorldSavedData var2) {
   }

   public int getHeight() {
      return 256;
   }

   public int getActualHeight() {
      return 256;
   }

   public boolean extendedLevelsInChunkCache() {
      return false;
   }

   public void makeFireworks(double var1, double var3, double var5, double var7, double var9, double var11, NBTTagCompound var13) {
   }

   public void addTileEntity(TileEntity var1) {
   }

   protected IChunkProvider createChunkProvider() {
      return new FakeWorld$FakeChunkProvider();
   }

   public Entity getEntityByID(int var1) {
      return EntityList.createEntityByID(var1, this);
   }

   public Chunk getChunkFromChunkCoords(int var1, int var2) {
      return null;
   }

   public Block getBlock(int var1, int var2, int var3) {
      return (Block)(var2 > 63?Blocks.air:Blocks.grass);
   }

   public static void b(String[] var0) {
      O = var0;
   }

   public static String[] b() {
      return O;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}
