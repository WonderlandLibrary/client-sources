package net.minecraft.entity.player;

import net.minecraft.server.*;
import net.minecraft.potion.*;
import net.minecraft.server.management.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.projectile.*;
import com.mojang.authlib.*;
import com.google.common.collect.*;
import net.minecraft.entity.passive.*;
import net.minecraft.tileentity.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.village.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.world.chunk.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.crash.*;
import net.minecraft.scoreboard.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.inventory.*;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private static final String[] I;
    public NetHandlerPlayServer playerNetServerHandler;
    private float combinedHealth;
    private int respawnInvulnerabilityTicks;
    public boolean isChangingQuantityOnly;
    private Entity spectatingEntity;
    private long playerLastActiveTime;
    public final MinecraftServer mcServer;
    private String translator;
    public double managedPosX;
    private int lastFoodLevel;
    public double managedPosZ;
    private final StatisticsFile statsFile;
    private final List<Integer> destroyedItemsNetCache;
    private boolean chatColours;
    private boolean wasHungry;
    public int ping;
    private EnumChatVisibility chatVisibility;
    private static final Logger logger;
    public final ItemInWorldManager theItemInWorldManager;
    public boolean playerConqueredTheEnd;
    private int lastExperience;
    public final List<ChunkCoordIntPair> loadedChunks;
    private float lastHealth;
    private int currentWindowId;
    
    public void addSelfToInternalCraftingInventory() {
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    protected void onNewPotionEffect(final PotionEffect potionEffect) {
        super.onNewPotionEffect(potionEffect);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
    }
    
    @Override
    public void sendProgressBarUpdate(final Container container, final int n, final int n2) {
        this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(container.windowId, n, n2));
    }
    
    public void mountEntityAndWakeUp() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(this);
        }
        if (this.sleeping) {
            this.wakeUpPlayer(" ".length() != 0, "".length() != 0, "".length() != 0);
        }
    }
    
    @Override
    public void sendEnterCombat() {
        super.sendEnterCombat();
        this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
    }
    
    public void removeEntity(final Entity entity) {
        if (entity instanceof EntityPlayer) {
            final NetHandlerPlayServer playerNetServerHandler = this.playerNetServerHandler;
            final int[] array = new int[" ".length()];
            array["".length()] = entity.getEntityId();
            playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(array));
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            this.destroyedItemsNetCache.add(entity.getEntityId());
        }
    }
    
    @Override
    protected void onItemUseFinish() {
        this.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, (byte)(0x12 ^ 0x1B)));
        super.onItemUseFinish();
    }
    
    @Override
    public void updateCraftingInventory(final Container container, final List<ItemStack> list) {
        this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(container.windowId, list));
        this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-" ".length(), -" ".length(), this.inventory.getItemStack()));
    }
    
    @Override
    protected void updateFallState(final double n, final boolean b, final Block block, final BlockPos blockPos) {
    }
    
    public StatisticsFile getStatFile() {
        return this.statsFile;
    }
    
    public void updateHeldItem() {
        if (!this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-" ".length(), -" ".length(), this.inventory.getItemStack()));
        }
    }
    
    public void markPlayerActive() {
        this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
    }
    
    @Override
    public boolean isSpectatedByPlayer(final EntityPlayerMP entityPlayerMP) {
        int n;
        if (entityPlayerMP.isSpectator()) {
            if (this.getSpectatingEntity() == this) {
                n = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else if (this.isSpectator()) {
            n = "".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n = (super.isSpectatedByPlayer(entityPlayerMP) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int n, final String s) {
        if (EntityPlayerMP.I[0x48 ^ 0x58].equals(s) && !this.mcServer.isDedicatedServer()) {
            return " ".length() != 0;
        }
        if (EntityPlayerMP.I[0x20 ^ 0x31].equals(s) || EntityPlayerMP.I[0x41 ^ 0x53].equals(s) || EntityPlayerMP.I[0xB ^ 0x18].equals(s) || EntityPlayerMP.I[0x70 ^ 0x64].equals(s)) {
            return " ".length() != 0;
        }
        if (this.mcServer.getConfigurationManager().canSendCommands(this.getGameProfile())) {
            final UserListOpsEntry userListOpsEntry = this.mcServer.getConfigurationManager().getOppedPlayers().getEntry(this.getGameProfile());
            int n2;
            if (userListOpsEntry != null) {
                if (userListOpsEntry.getPermissionLevel() >= n) {
                    n2 = " ".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
            }
            else if (this.mcServer.getOpPermissionLevel() >= n) {
                n2 = " ".length();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            return n2 != 0;
        }
        return "".length() != 0;
    }
    
    public void handleFalling(final double n, final boolean b) {
        BlockPos down = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023224), MathHelper.floor_double(this.posZ));
        Block block = this.worldObj.getBlockState(down).getBlock();
        if (block.getMaterial() == Material.air) {
            final Block block2 = this.worldObj.getBlockState(down.down()).getBlock();
            if (block2 instanceof BlockFence || block2 instanceof BlockWall || block2 instanceof BlockFenceGate) {
                down = down.down();
                block = this.worldObj.getBlockState(down).getBlock();
            }
        }
        super.updateFallState(n, b, block, down);
    }
    
    @Override
    public void addStat(final StatBase statBase, final int n) {
        if (statBase != null) {
            this.statsFile.increaseStat(this, statBase, n);
            final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().getObjectivesFromCriteria(statBase.func_150952_k()).iterator();
            "".length();
            if (false == true) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).increseScore(n);
            }
            if (this.statsFile.func_150879_e()) {
                this.statsFile.func_150876_a(this);
            }
        }
    }
    
    @Override
    public void addChatComponentMessage(final IChatComponent chatComponent) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponent));
    }
    
    @Override
    public boolean canAttackPlayer(final EntityPlayer entityPlayer) {
        int n;
        if (!this.canPlayersAttack()) {
            n = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            n = (super.canAttackPlayer(entityPlayer) ? 1 : 0);
        }
        return n != 0;
    }
    
    protected void updateBiomesExplored() {
        final String biomeName = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), "".length(), MathHelper.floor_double(this.posZ))).biomeName;
        JsonSerializableSet set = this.getStatFile().func_150870_b(AchievementList.exploreAllBiomes);
        if (set == null) {
            set = this.getStatFile().func_150872_a(AchievementList.exploreAllBiomes, new JsonSerializableSet());
        }
        set.add((Object)biomeName);
        if (this.getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes) && set.size() >= BiomeGenBase.explorationBiomesList.size()) {
            final HashSet hashSet = Sets.newHashSet((Iterable)BiomeGenBase.explorationBiomesList);
            final Iterator iterator = set.iterator();
            "".length();
            if (3 <= 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s = iterator.next();
                final Iterator<BiomeGenBase> iterator2 = hashSet.iterator();
                "".length();
                if (4 < 3) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    if (iterator2.next().biomeName.equals(s)) {
                        iterator2.remove();
                    }
                }
                if (!hashSet.isEmpty()) {
                    continue;
                }
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                break;
            }
            if (hashSet.isEmpty()) {
                this.triggerAchievement(AchievementList.exploreAllBiomes);
            }
        }
    }
    
    public String getPlayerIP() {
        final String string = this.playerNetServerHandler.netManager.getRemoteAddress().toString();
        final String substring = string.substring(string.indexOf(EntityPlayerMP.I[0x3F ^ 0x2A]) + " ".length());
        return substring.substring("".length(), substring.indexOf(EntityPlayerMP.I[0xD3 ^ 0xC5]));
    }
    
    @Override
    public void displayGui(final IInteractionObject interactionObject) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, interactionObject.getGuiID(), interactionObject.getDisplayName()));
        this.openContainer = interactionObject.createContainer(this.inventory, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    public void sendSlotContents(final Container container, final int n, final ItemStack itemStack) {
        if (!(container.getSlot(n) instanceof SlotCrafting) && !this.isChangingQuantityOnly) {
            this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(container.windowId, n, itemStack));
        }
    }
    
    @Override
    protected void updatePotionMetadata() {
        if (this.isSpectator()) {
            this.resetPotionEffectMetadata();
            this.setInvisible(" ".length() != 0);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            super.updatePotionMetadata();
        }
        this.getServerForPlayer().getEntityTracker().func_180245_a(this);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        int n2;
        if (this.mcServer.isDedicatedServer() && this.canPlayersAttack() && EntityPlayerMP.I[0xA3 ^ 0xAB].equals(damageSource.damageType)) {
            n2 = " ".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        if (n2 == 0 && this.respawnInvulnerabilityTicks > 0 && damageSource != DamageSource.outOfWorld) {
            return "".length() != 0;
        }
        if (damageSource instanceof EntityDamageSource) {
            final Entity entity = damageSource.getEntity();
            if (entity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)entity)) {
                return "".length() != 0;
            }
            if (entity instanceof EntityArrow) {
                final EntityArrow entityArrow = (EntityArrow)entity;
                if (entityArrow.shootingEntity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)entityArrow.shootingEntity)) {
                    return "".length() != 0;
                }
            }
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    public EntityPlayerMP(final MinecraftServer mcServer, final WorldServer worldServer, final GameProfile gameProfile, final ItemInWorldManager theItemInWorldManager) {
        super(worldServer, gameProfile);
        this.translator = EntityPlayerMP.I["".length()];
        this.loadedChunks = (List<ChunkCoordIntPair>)Lists.newLinkedList();
        this.destroyedItemsNetCache = (List<Integer>)Lists.newLinkedList();
        this.combinedHealth = Float.MIN_VALUE;
        this.lastHealth = -1.0E8f;
        this.lastFoodLevel = -(69011804 + 35181010 - 57153782 + 52960967);
        this.wasHungry = (" ".length() != 0);
        this.lastExperience = -(86118464 + 3860741 - 4642802 + 14663596);
        this.respawnInvulnerabilityTicks = (0x56 ^ 0x6A);
        this.chatColours = (" ".length() != 0);
        this.playerLastActiveTime = System.currentTimeMillis();
        this.spectatingEntity = null;
        theItemInWorldManager.thisPlayerMP = this;
        this.theItemInWorldManager = theItemInWorldManager;
        BlockPos blockPos = worldServer.getSpawnPoint();
        if (!worldServer.provider.getHasNoSky() && worldServer.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
            int n = Math.max(0x67 ^ 0x62, mcServer.getSpawnProtectionSize() - (0x80 ^ 0x86));
            final int floor_double = MathHelper.floor_double(worldServer.getWorldBorder().getClosestDistance(blockPos.getX(), blockPos.getZ()));
            if (floor_double < n) {
                n = floor_double;
            }
            if (floor_double <= " ".length()) {
                n = " ".length();
            }
            blockPos = worldServer.getTopSolidOrLiquidBlock(blockPos.add(this.rand.nextInt(n * "  ".length()) - n, "".length(), this.rand.nextInt(n * "  ".length()) - n));
        }
        this.mcServer = mcServer;
        this.statsFile = mcServer.getConfigurationManager().getPlayerStatsFile(this);
        this.moveToBlockPosAndAngles(blockPos, this.stepHeight = 0.0f, 0.0f);
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (!worldServer.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && this.posY < 255.0) {
            this.setPosition(this.posX, this.posY + 1.0, this.posZ);
        }
    }
    
    @Override
    public void setItemInUse(final ItemStack itemStack, final int n) {
        super.setItemInUse(itemStack, n);
        if (itemStack != null && itemStack.getItem() != null && itemStack.getItem().getItemUseAction(itemStack) == EnumAction.EAT) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, "   ".length()));
        }
    }
    
    @Override
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, EntityPlayerMP.I[0x12 ^ 0x1C], inventory.getDisplayName(), inventory.getSizeInventory(), entityHorse.getEntityId()));
        this.openContainer = new ContainerHorseInventory(this.inventory, inventory, entityHorse, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void sendTileEntityUpdate(final TileEntity tileEntity) {
        if (tileEntity != null) {
            final Packet descriptionPacket = tileEntity.getDescriptionPacket();
            if (descriptionPacket != null) {
                this.playerNetServerHandler.sendPacket(descriptionPacket);
            }
        }
    }
    
    @Override
    public void onCriticalHit(final Entity entity) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(entity, 0x1E ^ 0x1A));
    }
    
    @Override
    public BlockPos getPosition() {
        return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
    }
    
    @Override
    public void travelToDimension(int length) {
        if (this.dimension == " ".length() && length == " ".length()) {
            this.triggerAchievement(AchievementList.theEnd2);
            this.worldObj.removeEntity(this);
            this.playerConqueredTheEnd = (" ".length() != 0);
            this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x5B ^ 0x5F, 0.0f));
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            if (this.dimension == 0 && length == " ".length()) {
                this.triggerAchievement(AchievementList.theEnd);
                final BlockPos spawnCoordinate = this.mcServer.worldServerForDimension(length).getSpawnCoordinate();
                if (spawnCoordinate != null) {
                    this.playerNetServerHandler.setPlayerLocation(spawnCoordinate.getX(), spawnCoordinate.getY(), spawnCoordinate.getZ(), 0.0f, 0.0f);
                }
                length = " ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                this.triggerAchievement(AchievementList.portal);
            }
            this.mcServer.getConfigurationManager().transferPlayerToDimension(this, length);
            this.lastExperience = -" ".length();
            this.lastHealth = -1.0f;
            this.lastFoodLevel = -" ".length();
        }
    }
    
    public void setPlayerHealthUpdated() {
        this.lastHealth = -1.0E8f;
    }
    
    @Override
    public void onEnchantmentCritical(final Entity entity) {
        this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(entity, 0x99 ^ 0x9C));
    }
    
    @Override
    public void openEditSign(final TileEntitySign tileEntitySign) {
        tileEntitySign.setPlayer(this);
        this.playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(tileEntitySign.getPos()));
    }
    
    @Override
    public void onItemPickup(final Entity entity, final int n) {
        super.onItemPickup(entity, n);
        this.openContainer.detectAndSendChanges();
    }
    
    @Override
    public void removeExperienceLevel(final int n) {
        super.removeExperienceLevel(n);
        this.lastExperience = -" ".length();
    }
    
    @Override
    public void displayVillagerTradeGui(final IMerchant merchant) {
        this.getNextWindowId();
        this.openContainer = new ContainerMerchant(this.inventory, merchant, this.worldObj);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
        this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, EntityPlayerMP.I[0x23 ^ 0x2F], merchant.getDisplayName(), ((ContainerMerchant)this.openContainer).getMerchantInventory().getSizeInventory()));
        final MerchantRecipeList recipes = merchant.getRecipes(this);
        if (recipes != null) {
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(this.currentWindowId);
            recipes.writeToBuf(packetBuffer);
            this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload(EntityPlayerMP.I[0x45 ^ 0x48], packetBuffer));
        }
    }
    
    @Override
    public void displayGUIBook(final ItemStack itemStack) {
        if (itemStack.getItem() == Items.written_book) {
            this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload(EntityPlayerMP.I[0x6B ^ 0x64], new PacketBuffer(Unpooled.buffer())));
        }
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        final Entity ridingEntity = this.ridingEntity;
        super.mountEntity(entity);
        if (entity != ridingEntity) {
            this.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach("".length(), this, this.ridingEntity));
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    @Override
    public void setGameType(final WorldSettings.GameType gameType) {
        this.theItemInWorldManager.setGameType(gameType);
        this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState("   ".length(), gameType.getID()));
        if (gameType == WorldSettings.GameType.SPECTATOR) {
            this.mountEntity(null);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            this.setSpectatingEntity(this);
        }
        this.sendPlayerAbilities();
        this.markPotionsDirty();
    }
    
    public EnumChatVisibility getChatVisibility() {
        return this.chatVisibility;
    }
    
    @Override
    public void func_175173_a(final Container container, final IInventory inventory) {
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < inventory.getFieldCount()) {
            this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(container.windowId, i, inventory.getField(i)));
            ++i;
        }
    }
    
    public void closeContainer() {
        this.openContainer.onContainerClosed(this);
        this.openContainer = this.inventoryContainer;
    }
    
    public WorldServer getServerForPlayer() {
        return (WorldServer)this.worldObj;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntityPlayerMP.I[" ".length()], 0x1E ^ 0x7D)) {
            if (MinecraftServer.getServer().getForceGamemode()) {
                this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else {
                this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(nbtTagCompound.getInteger(EntityPlayerMP.I["  ".length()])));
            }
        }
    }
    
    @Override
    public void onUpdate() {
        this.theItemInWorldManager.updateBlockRemoving();
        this.respawnInvulnerabilityTicks -= " ".length();
        if (this.hurtResistantTime > 0) {
            this.hurtResistantTime -= " ".length();
        }
        this.openContainer.detectAndSendChanges();
        if (!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        while (!this.destroyedItemsNetCache.isEmpty()) {
            final int min = Math.min(this.destroyedItemsNetCache.size(), 111526272 + 660965580 + 683111695 + 691880100);
            final int[] array = new int[min];
            final Iterator<Integer> iterator = this.destroyedItemsNetCache.iterator();
            int length = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext() && length < min) {
                array[length++] = iterator.next();
                iterator.remove();
            }
            this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(array));
        }
        if (!this.loadedChunks.isEmpty()) {
            final ArrayList arrayList = Lists.newArrayList();
            final Iterator<ChunkCoordIntPair> iterator2 = this.loadedChunks.iterator();
            final ArrayList arrayList2 = Lists.newArrayList();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (iterator2.hasNext() && arrayList.size() < (0x31 ^ 0x3B)) {
                final ChunkCoordIntPair chunkCoordIntPair = iterator2.next();
                if (chunkCoordIntPair != null) {
                    if (!this.worldObj.isBlockLoaded(new BlockPos(chunkCoordIntPair.chunkXPos << (0x22 ^ 0x26), "".length(), chunkCoordIntPair.chunkZPos << (0x55 ^ 0x51)))) {
                        continue;
                    }
                    final Chunk chunkFromChunkCoords = this.worldObj.getChunkFromChunkCoords(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);
                    if (!chunkFromChunkCoords.isPopulated()) {
                        continue;
                    }
                    arrayList.add(chunkFromChunkCoords);
                    arrayList2.addAll(((WorldServer)this.worldObj).getTileEntitiesIn(chunkCoordIntPair.chunkXPos * (0x48 ^ 0x58), "".length(), chunkCoordIntPair.chunkZPos * (0x11 ^ 0x1), chunkCoordIntPair.chunkXPos * (0x71 ^ 0x61) + (0x97 ^ 0x87), 128 + 187 - 106 + 47, chunkCoordIntPair.chunkZPos * (0x2B ^ 0x3B) + (0x6B ^ 0x7B)));
                    iterator2.remove();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                    continue;
                }
                else {
                    iterator2.remove();
                }
            }
            if (!arrayList.isEmpty()) {
                if (arrayList.size() == " ".length()) {
                    this.playerNetServerHandler.sendPacket(new S21PacketChunkData((Chunk)arrayList.get("".length()), (boolean)(" ".length() != 0), 18675 + 15868 - 8961 + 39953));
                    "".length();
                    if (1 == 2) {
                        throw null;
                    }
                }
                else {
                    this.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(arrayList));
                }
                final Iterator<TileEntity> iterator3 = arrayList2.iterator();
                "".length();
                if (3 < 0) {
                    throw null;
                }
                while (iterator3.hasNext()) {
                    this.sendTileEntityUpdate(iterator3.next());
                }
                final Iterator<Chunk> iterator4 = arrayList.iterator();
                "".length();
                if (4 < -1) {
                    throw null;
                }
                while (iterator4.hasNext()) {
                    this.getServerForPlayer().getEntityTracker().func_85172_a(this, iterator4.next());
                }
            }
        }
        final Entity spectatingEntity = this.getSpectatingEntity();
        if (spectatingEntity != this) {
            if (!spectatingEntity.isEntityAlive()) {
                this.setSpectatingEntity(this);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                this.setPositionAndRotation(spectatingEntity.posX, spectatingEntity.posY, spectatingEntity.posZ, spectatingEntity.rotationYaw, spectatingEntity.rotationPitch);
                this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
                if (this.isSneaking()) {
                    this.setSpectatingEntity(this);
                }
            }
        }
    }
    
    @Override
    public void sendEndCombat() {
        super.sendEndCombat();
        this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
    }
    
    @Override
    public boolean isSpectator() {
        if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void addExperienceLevel(final int n) {
        super.addExperienceLevel(n);
        this.lastExperience = -" ".length();
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (this.worldObj.getGameRules().getBoolean(EntityPlayerMP.I[0x9E ^ 0x98])) {
            final Team team = this.getTeam();
            if (team != null && team.getDeathMessageVisibility() != Team.EnumVisible.ALWAYS) {
                if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
                    this.mcServer.getConfigurationManager().sendMessageToAllTeamMembers(this, this.getCombatTracker().getDeathMessage());
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
                    this.mcServer.getConfigurationManager().sendMessageToTeamOrEvryPlayer(this, this.getCombatTracker().getDeathMessage());
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
            }
            else {
                this.mcServer.getConfigurationManager().sendChatMsg(this.getCombatTracker().getDeathMessage());
            }
        }
        if (!this.worldObj.getGameRules().getBoolean(EntityPlayerMP.I[0xC6 ^ 0xC1])) {
            this.inventory.dropAllItems();
        }
        final Iterator<ScoreObjective> iterator = this.worldObj.getScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.deathCount).iterator();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).func_96648_a();
        }
        final EntityLivingBase func_94060_bK = this.func_94060_bK();
        if (func_94060_bK != null) {
            final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(EntityList.getEntityID(func_94060_bK));
            if (entityEggInfo != null) {
                this.triggerAchievement(entityEggInfo.field_151513_e);
            }
            func_94060_bK.addToPlayerScore(this, this.scoreValue);
        }
        this.triggerAchievement(StatList.deathsStat);
        this.func_175145_a(StatList.timeSinceDeathStat);
        this.getCombatTracker().reset();
    }
    
    private boolean canPlayersAttack() {
        return this.mcServer.isPVPEnabled();
    }
    
    public void loadResourcePack(final String s, final String s2) {
        this.playerNetServerHandler.sendPacket(new S48PacketResourcePackSend(s, s2));
    }
    
    @Override
    public void clonePlayer(final EntityPlayer entityPlayer, final boolean b) {
        super.clonePlayer(entityPlayer, b);
        this.lastExperience = -" ".length();
        this.lastHealth = -1.0f;
        this.lastFoodLevel = -" ".length();
        this.destroyedItemsNetCache.addAll(((EntityPlayerMP)entityPlayer).destroyedItemsNetCache);
    }
    
    @Override
    protected void onFinishedPotionEffect(final PotionEffect potionEffect) {
        super.onFinishedPotionEffect(potionEffect);
        this.playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(this.getEntityId(), potionEffect));
    }
    
    public void handleClientSettings(final C15PacketClientSettings c15PacketClientSettings) {
        this.translator = c15PacketClientSettings.getLang();
        this.chatVisibility = c15PacketClientSettings.getChatVisibility();
        this.chatColours = c15PacketClientSettings.isColorsEnabled();
        this.getDataWatcher().updateObject(0x65 ^ 0x6F, (byte)c15PacketClientSettings.getModelPartFlags());
    }
    
    public void sendContainerToPlayer(final Container container) {
        this.updateCraftingInventory(container, container.getInventory());
    }
    
    public IChatComponent getTabListDisplayName() {
        return null;
    }
    
    public Entity getSpectatingEntity() {
        Entity spectatingEntity;
        if (this.spectatingEntity == null) {
            spectatingEntity = this;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            spectatingEntity = this.spectatingEntity;
        }
        return spectatingEntity;
    }
    
    @Override
    public void sendPlayerAbilities() {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
            this.updatePotionMetadata();
        }
    }
    
    @Override
    public void addChatMessage(final IChatComponent chatComponent) {
        this.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponent));
    }
    
    public void onUpdateEntity() {
        try {
            super.onUpdate();
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < this.inventory.getSizeInventory()) {
                final ItemStack stackInSlot = this.inventory.getStackInSlot(i);
                if (stackInSlot != null && stackInSlot.getItem().isMap()) {
                    final Packet mapDataPacket = ((ItemMapBase)stackInSlot.getItem()).createMapDataPacket(stackInSlot, this.worldObj, this);
                    if (mapDataPacket != null) {
                        this.playerNetServerHandler.sendPacket(mapDataPacket);
                    }
                }
                ++i;
            }
            Label_0247: {
                if (this.getHealth() == this.lastHealth && this.lastFoodLevel == this.foodStats.getFoodLevel()) {
                    int n;
                    if (this.foodStats.getSaturationLevel() == 0.0f) {
                        n = " ".length();
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    if (n == (this.wasHungry ? 1 : 0)) {
                        break Label_0247;
                    }
                }
                this.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
                this.lastHealth = this.getHealth();
                this.lastFoodLevel = this.foodStats.getFoodLevel();
                int wasHungry;
                if (this.foodStats.getSaturationLevel() == 0.0f) {
                    wasHungry = " ".length();
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                }
                else {
                    wasHungry = "".length();
                }
                this.wasHungry = (wasHungry != 0);
            }
            if (this.getHealth() + this.getAbsorptionAmount() != this.combinedHealth) {
                this.combinedHealth = this.getHealth() + this.getAbsorptionAmount();
                final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.health).iterator();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final Score valueFromObjective = this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next());
                    final EntityPlayer[] array = new EntityPlayer[" ".length()];
                    array["".length()] = this;
                    valueFromObjective.func_96651_a(Arrays.asList(array));
                }
            }
            if (this.experienceTotal != this.lastExperience) {
                this.lastExperience = this.experienceTotal;
                this.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
            }
            if (this.ticksExisted % (0x18 ^ 0xC) * (0x2F ^ 0x2A) == 0 && !this.getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes)) {
                this.updateBiomesExplored();
                "".length();
                if (-1 == 3) {
                    throw null;
                }
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, EntityPlayerMP.I[0xB0 ^ 0xB4]);
            this.addEntityCrashInfo(crashReport.makeCategory(EntityPlayerMP.I[0xBE ^ 0xBB]));
            throw new ReportedException(crashReport);
        }
    }
    
    public void setEntityActionState(final float moveStrafing, final float moveForward, final boolean isJumping, final boolean sneaking) {
        if (this.ridingEntity != null) {
            if (moveStrafing >= -1.0f && moveStrafing <= 1.0f) {
                this.moveStrafing = moveStrafing;
            }
            if (moveForward >= -1.0f && moveForward <= 1.0f) {
                this.moveForward = moveForward;
            }
            this.isJumping = isJumping;
            this.setSneaking(sneaking);
        }
    }
    
    public void closeScreen() {
        this.playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(this.openContainer.windowId));
        this.closeContainer();
    }
    
    @Override
    protected void onChangedPotionEffect(final PotionEffect potionEffect, final boolean b) {
        super.onChangedPotionEffect(potionEffect, b);
        this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), potionEffect));
    }
    
    public void setSpectatingEntity(final Entity entity) {
        final Entity spectatingEntity = this.getSpectatingEntity();
        Entity spectatingEntity2;
        if (entity == null) {
            spectatingEntity2 = this;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            spectatingEntity2 = entity;
        }
        this.spectatingEntity = spectatingEntity2;
        if (spectatingEntity != this.spectatingEntity) {
            this.playerNetServerHandler.sendPacket(new S43PacketCamera(this.spectatingEntity));
            this.setPositionAndUpdate(this.spectatingEntity.posX, this.spectatingEntity.posY, this.spectatingEntity.posZ);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityPlayerMP.I["   ".length()], this.theItemInWorldManager.getGameType().getID());
    }
    
    @Override
    public EnumStatus trySleep(final BlockPos blockPos) {
        final EnumStatus trySleep = super.trySleep(blockPos);
        if (trySleep == EnumStatus.OK) {
            final S0APacketUseBed s0APacketUseBed = new S0APacketUseBed(this, blockPos);
            this.getServerForPlayer().getEntityTracker().sendToAllTrackingEntity(this, s0APacketUseBed);
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playerNetServerHandler.sendPacket(s0APacketUseBed);
        }
        return trySleep;
    }
    
    private static void I() {
        (I = new String[0x3C ^ 0x2B])["".length()] = I("1\u0000:\f\u0005", "TneYV");
        EntityPlayerMP.I[" ".length()] = I("8'6\u0010\u0016:\f6\u0004\u0016\u001c2'\f", "HKWis");
        EntityPlayerMP.I["  ".length()] = I("5\u0001\n\u001e\u00117*\n\n\u0011\u0011\u0014\u001b\u0002", "Emkgt");
        EntityPlayerMP.I["   ".length()] = I("\u0006\u001e\u0007-\u0006\u00045\u00079\u0006\"\u000b\u00161", "vrfTc");
        EntityPlayerMP.I[0x8D ^ 0x89] = I("\u0018\b\u0007\u0007\f\"\u0006D\u001c\t-\u0018\u0001\u001e", "Ladle");
        EntityPlayerMP.I[0x7B ^ 0x7E] = I("\u0018\u0002$,\u0015:N'0\u0019&\te!\u0019+\u0005 1", "HnEUp");
        EntityPlayerMP.I[0xA1 ^ 0xA7] = I("#8\u00178051\f'95#\u000b.\u00135#", "PPxOt");
        EntityPlayerMP.I[0x4D ^ 0x4A] = I("\u0013*\u0016*.\u00169\u00164\u0013\u0017=\n", "xOsZg");
        EntityPlayerMP.I[0xC8 ^ 0xC0] = I("!,\u001d(", "GMqDQ");
        EntityPlayerMP.I[0x2C ^ 0x25] = I(",\t9\u001c/&\b2\u001a`&\u0015\u001b\u0007-$\u00033", "OfWhN");
        EntityPlayerMP.I[0x37 ^ 0x3D] = I("*5/\r75z%\u00067*\u000b\"\u00057+1", "XTAiX");
        EntityPlayerMP.I[0x14 ^ 0x1F] = I("\u001f\u000f,)\u000e\u0000\u0007$8W\u0011\t,8\f\u001b\b'>", "rfBLm");
        EntityPlayerMP.I[0xBF ^ 0xB3] = I("\u001d<8\u00131\u000240\u0002h\u0006<:\u001a3\u00170$", "pUVvR");
        EntityPlayerMP.I[0x3B ^ 0x36] = I("\u00067=\u001f\u001c\u0007\u001d2?", "KtAKn");
        EntityPlayerMP.I[0x1C ^ 0x12] = I("<&:/=\u0000\u0000!4:\u001c", "yHNFI");
        EntityPlayerMP.I[0x59 ^ 0x56] = I("\u00054$*\b8\u00126", "HwXhG");
        EntityPlayerMP.I[0x87 ^ 0x97] = I("\u000b.'\u0002", "xKBfL");
        EntityPlayerMP.I[0x51 ^ 0x40] = I("?\u00014\u001c", "KdXpW");
        EntityPlayerMP.I[0x1C ^ 0xE] = I("8\t\u0014\"", "PlxRg");
        EntityPlayerMP.I[0x8 ^ 0x1B] = I("\u000e/", "cJxwu");
        EntityPlayerMP.I[0x62 ^ 0x76] = I("10:\u0012? 0", "EBSuX");
        EntityPlayerMP.I[0x5A ^ 0x4F] = I("c", "Loagw");
        EntityPlayerMP.I[0x3D ^ 0x2B] = I("[", "akdwQ");
    }
    
    @Override
    public void setPositionAndUpdate(final double n, final double n2, final double n3) {
        this.playerNetServerHandler.setPlayerLocation(n, n2, n3, this.rotationYaw, this.rotationPitch);
    }
    
    @Override
    public void func_175145_a(final StatBase statBase) {
        if (statBase != null) {
            this.statsFile.unlockAchievement(this, statBase, "".length());
            final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().getObjectivesFromCriteria(statBase.func_150952_k()).iterator();
            "".length();
            if (4 == 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).setScorePoints("".length());
            }
            if (this.statsFile.func_150879_e()) {
                this.statsFile.func_150876_a(this);
            }
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public void displayGUIChest(final IInventory inventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        if (inventory instanceof ILockableContainer) {
            final ILockableContainer lockableContainer = (ILockableContainer)inventory;
            if (lockableContainer.isLocked() && !this.canOpen(lockableContainer.getLockCode()) && !this.isSpectator()) {
                final NetHandlerPlayServer playerNetServerHandler = this.playerNetServerHandler;
                final String s = EntityPlayerMP.I[0xCB ^ 0xC2];
                final Object[] array = new Object[" ".length()];
                array["".length()] = inventory.getDisplayName();
                playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentTranslation(s, array), (byte)"  ".length()));
                this.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(EntityPlayerMP.I[0x88 ^ 0x82], this.posX, this.posY, this.posZ, 1.0f, 1.0f));
                return;
            }
        }
        this.getNextWindowId();
        if (inventory instanceof IInteractionObject) {
            this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, ((IInteractionObject)inventory).getGuiID(), inventory.getDisplayName(), inventory.getSizeInventory()));
            this.openContainer = ((IInteractionObject)inventory).createContainer(this.inventory, this);
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, EntityPlayerMP.I[0x2D ^ 0x26], inventory.getDisplayName(), inventory.getSizeInventory()));
            this.openContainer = new ContainerChest(this.inventory, inventory, this);
        }
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.onCraftGuiOpened(this);
    }
    
    @Override
    public void attackTargetEntityWithCurrentItem(final Entity spectatingEntity) {
        if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
            this.setSpectatingEntity(spectatingEntity);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            super.attackTargetEntityWithCurrentItem(spectatingEntity);
        }
    }
    
    public long getLastActiveTime() {
        return this.playerLastActiveTime;
    }
    
    @Override
    public void wakeUpPlayer(final boolean b, final boolean b2, final boolean b3) {
        if (this.isPlayerSleeping()) {
            this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, "  ".length()));
        }
        super.wakeUpPlayer(b, b2, b3);
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    private void getNextWindowId() {
        this.currentWindowId = this.currentWindowId % (0x77 ^ 0x13) + " ".length();
    }
}
