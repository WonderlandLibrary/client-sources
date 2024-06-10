/*    1:     */ package net.minecraft.entity.player;
/*    2:     */ 
/*    3:     */ import com.google.common.collect.Sets;
/*    4:     */ import com.mojang.authlib.GameProfile;
/*    5:     */ import io.netty.buffer.Unpooled;
/*    6:     */ import java.io.IOException;
/*    7:     */ import java.util.ArrayList;
/*    8:     */ import java.util.Arrays;
/*    9:     */ import java.util.Collection;
/*   10:     */ import java.util.HashMap;
/*   11:     */ import java.util.HashSet;
/*   12:     */ import java.util.Iterator;
/*   13:     */ import java.util.LinkedList;
/*   14:     */ import java.util.List;
/*   15:     */ import java.util.Random;
/*   16:     */ import java.util.Set;
/*   17:     */ import net.minecraft.crash.CrashReport;
/*   18:     */ import net.minecraft.crash.CrashReportCategory;
/*   19:     */ import net.minecraft.entity.Entity;
/*   20:     */ import net.minecraft.entity.EntityList;
/*   21:     */ import net.minecraft.entity.EntityList.EntityEggInfo;
/*   22:     */ import net.minecraft.entity.EntityLivingBase;
/*   23:     */ import net.minecraft.entity.EntityTracker;
/*   24:     */ import net.minecraft.entity.IMerchant;
/*   25:     */ import net.minecraft.entity.item.EntityMinecartHopper;
/*   26:     */ import net.minecraft.entity.passive.EntityHorse;
/*   27:     */ import net.minecraft.entity.projectile.EntityArrow;
/*   28:     */ import net.minecraft.inventory.Container;
/*   29:     */ import net.minecraft.inventory.ContainerBeacon;
/*   30:     */ import net.minecraft.inventory.ContainerBrewingStand;
/*   31:     */ import net.minecraft.inventory.ContainerChest;
/*   32:     */ import net.minecraft.inventory.ContainerDispenser;
/*   33:     */ import net.minecraft.inventory.ContainerEnchantment;
/*   34:     */ import net.minecraft.inventory.ContainerFurnace;
/*   35:     */ import net.minecraft.inventory.ContainerHopper;
/*   36:     */ import net.minecraft.inventory.ContainerHorseInventory;
/*   37:     */ import net.minecraft.inventory.ContainerMerchant;
/*   38:     */ import net.minecraft.inventory.ContainerRepair;
/*   39:     */ import net.minecraft.inventory.ContainerWorkbench;
/*   40:     */ import net.minecraft.inventory.ICrafting;
/*   41:     */ import net.minecraft.inventory.IInventory;
/*   42:     */ import net.minecraft.inventory.InventoryMerchant;
/*   43:     */ import net.minecraft.inventory.SlotCrafting;
/*   44:     */ import net.minecraft.item.EnumAction;
/*   45:     */ import net.minecraft.item.Item;
/*   46:     */ import net.minecraft.item.ItemMapBase;
/*   47:     */ import net.minecraft.item.ItemStack;
/*   48:     */ import net.minecraft.nbt.NBTTagCompound;
/*   49:     */ import net.minecraft.network.NetHandlerPlayServer;
/*   50:     */ import net.minecraft.network.NetworkManager;
/*   51:     */ import net.minecraft.network.Packet;
/*   52:     */ import net.minecraft.network.PacketBuffer;
/*   53:     */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*   54:     */ import net.minecraft.network.play.server.S02PacketChat;
/*   55:     */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*   56:     */ import net.minecraft.network.play.server.S0APacketUseBed;
/*   57:     */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*   58:     */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*   59:     */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*   60:     */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*   61:     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*   62:     */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*   63:     */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*   64:     */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*   65:     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*   66:     */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*   67:     */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*   68:     */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*   69:     */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*   70:     */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*   71:     */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*   72:     */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*   73:     */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*   74:     */ import net.minecraft.potion.PotionEffect;
/*   75:     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*   76:     */ import net.minecraft.scoreboard.Score;
/*   77:     */ import net.minecraft.scoreboard.ScoreObjective;
/*   78:     */ import net.minecraft.scoreboard.Scoreboard;
/*   79:     */ import net.minecraft.server.MinecraftServer;
/*   80:     */ import net.minecraft.server.management.ItemInWorldManager;
/*   81:     */ import net.minecraft.server.management.ServerConfigurationManager;
/*   82:     */ import net.minecraft.stats.AchievementList;
/*   83:     */ import net.minecraft.stats.StatBase;
/*   84:     */ import net.minecraft.stats.StatList;
/*   85:     */ import net.minecraft.stats.StatisticsFile;
/*   86:     */ import net.minecraft.tileentity.TileEntity;
/*   87:     */ import net.minecraft.tileentity.TileEntityBeacon;
/*   88:     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*   89:     */ import net.minecraft.tileentity.TileEntityDispenser;
/*   90:     */ import net.minecraft.tileentity.TileEntityDropper;
/*   91:     */ import net.minecraft.tileentity.TileEntityFurnace;
/*   92:     */ import net.minecraft.tileentity.TileEntityHopper;
/*   93:     */ import net.minecraft.tileentity.TileEntitySign;
/*   94:     */ import net.minecraft.util.ChunkCoordinates;
/*   95:     */ import net.minecraft.util.CombatTracker;
/*   96:     */ import net.minecraft.util.DamageSource;
/*   97:     */ import net.minecraft.util.EntityDamageSource;
/*   98:     */ import net.minecraft.util.FoodStats;
/*   99:     */ import net.minecraft.util.IChatComponent;
/*  100:     */ import net.minecraft.util.JsonSerializableSet;
/*  101:     */ import net.minecraft.util.MathHelper;
/*  102:     */ import net.minecraft.util.ReportedException;
/*  103:     */ import net.minecraft.village.MerchantRecipeList;
/*  104:     */ import net.minecraft.world.ChunkCoordIntPair;
/*  105:     */ import net.minecraft.world.GameRules;
/*  106:     */ import net.minecraft.world.World;
/*  107:     */ import net.minecraft.world.WorldProvider;
/*  108:     */ import net.minecraft.world.WorldServer;
/*  109:     */ import net.minecraft.world.WorldSettings.GameType;
/*  110:     */ import net.minecraft.world.biome.BiomeGenBase;
/*  111:     */ import net.minecraft.world.chunk.Chunk;
/*  112:     */ import net.minecraft.world.storage.WorldInfo;
/*  113:     */ import org.apache.commons.io.Charsets;
/*  114:     */ import org.apache.logging.log4j.LogManager;
/*  115:     */ import org.apache.logging.log4j.Logger;
/*  116:     */ 
/*  117:     */ public class EntityPlayerMP
/*  118:     */   extends EntityPlayer
/*  119:     */   implements ICrafting
/*  120:     */ {
/*  121: 104 */   private static final Logger logger = ;
/*  122: 105 */   private String translator = "en_US";
/*  123:     */   public NetHandlerPlayServer playerNetServerHandler;
/*  124:     */   public final MinecraftServer mcServer;
/*  125:     */   public final ItemInWorldManager theItemInWorldManager;
/*  126:     */   public double managedPosX;
/*  127:     */   public double managedPosZ;
/*  128: 125 */   public final List loadedChunks = new LinkedList();
/*  129: 128 */   public final List destroyedItemsNetCache = new LinkedList();
/*  130:     */   private final StatisticsFile field_147103_bO;
/*  131: 130 */   private float field_130068_bO = 1.4E-45F;
/*  132: 133 */   private float lastHealth = -1.0E+008F;
/*  133: 136 */   private int lastFoodLevel = -99999999;
/*  134: 139 */   private boolean wasHungry = true;
/*  135: 142 */   private int lastExperience = -99999999;
/*  136: 143 */   private int field_147101_bU = 60;
/*  137:     */   private int renderDistance;
/*  138:     */   private EntityPlayer.EnumChatVisibility chatVisibility;
/*  139: 148 */   private boolean chatColours = true;
/*  140: 149 */   private long field_143005_bX = 0L;
/*  141:     */   private int currentWindowId;
/*  142:     */   public boolean isChangingQuantityOnly;
/*  143:     */   public int ping;
/*  144:     */   public boolean playerConqueredTheEnd;
/*  145:     */   private static final String __OBFID = "CL_00001440";
/*  146:     */   
/*  147:     */   public EntityPlayerMP(MinecraftServer p_i45285_1_, WorldServer p_i45285_2_, GameProfile p_i45285_3_, ItemInWorldManager p_i45285_4_)
/*  148:     */   {
/*  149: 172 */     super(p_i45285_2_, p_i45285_3_);
/*  150: 173 */     p_i45285_4_.thisPlayerMP = this;
/*  151: 174 */     this.theItemInWorldManager = p_i45285_4_;
/*  152: 175 */     this.renderDistance = p_i45285_1_.getConfigurationManager().getViewDistance();
/*  153: 176 */     ChunkCoordinates var5 = p_i45285_2_.getSpawnPoint();
/*  154: 177 */     int var6 = var5.posX;
/*  155: 178 */     int var7 = var5.posZ;
/*  156: 179 */     int var8 = var5.posY;
/*  157: 181 */     if ((!p_i45285_2_.provider.hasNoSky) && (p_i45285_2_.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE))
/*  158:     */     {
/*  159: 183 */       int var9 = Math.max(5, p_i45285_1_.getSpawnProtectionSize() - 6);
/*  160: 184 */       var6 += this.rand.nextInt(var9 * 2) - var9;
/*  161: 185 */       var7 += this.rand.nextInt(var9 * 2) - var9;
/*  162: 186 */       var8 = p_i45285_2_.getTopSolidOrLiquidBlock(var6, var7);
/*  163:     */     }
/*  164: 189 */     this.mcServer = p_i45285_1_;
/*  165: 190 */     this.field_147103_bO = p_i45285_1_.getConfigurationManager().func_148538_i(getCommandSenderName());
/*  166: 191 */     this.stepHeight = 0.0F;
/*  167: 192 */     this.yOffset = 0.0F;
/*  168: 193 */     setLocationAndAngles(var6 + 0.5D, var8, var7 + 0.5D, 0.0F, 0.0F);
/*  169: 195 */     while (!p_i45285_2_.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
/*  170: 197 */       setPosition(this.posX, this.posY + 1.0D, this.posZ);
/*  171:     */     }
/*  172:     */   }
/*  173:     */   
/*  174:     */   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
/*  175:     */   {
/*  176: 206 */     super.readEntityFromNBT(par1NBTTagCompound);
/*  177: 208 */     if (par1NBTTagCompound.func_150297_b("playerGameType", 99)) {
/*  178: 210 */       if (MinecraftServer.getServer().getForceGamemode()) {
/*  179: 212 */         this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
/*  180:     */       } else {
/*  181: 216 */         this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(par1NBTTagCompound.getInteger("playerGameType")));
/*  182:     */       }
/*  183:     */     }
/*  184:     */   }
/*  185:     */   
/*  186:     */   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
/*  187:     */   {
/*  188: 226 */     super.writeEntityToNBT(par1NBTTagCompound);
/*  189: 227 */     par1NBTTagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
/*  190:     */   }
/*  191:     */   
/*  192:     */   public void addExperienceLevel(int par1)
/*  193:     */   {
/*  194: 235 */     super.addExperienceLevel(par1);
/*  195: 236 */     this.lastExperience = -1;
/*  196:     */   }
/*  197:     */   
/*  198:     */   public void addSelfToInternalCraftingInventory()
/*  199:     */   {
/*  200: 241 */     this.openContainer.addCraftingToCrafters(this);
/*  201:     */   }
/*  202:     */   
/*  203:     */   protected void resetHeight()
/*  204:     */   {
/*  205: 249 */     this.yOffset = 0.0F;
/*  206:     */   }
/*  207:     */   
/*  208:     */   public float getEyeHeight()
/*  209:     */   {
/*  210: 254 */     return 1.62F;
/*  211:     */   }
/*  212:     */   
/*  213:     */   public void onUpdate()
/*  214:     */   {
/*  215: 262 */     this.theItemInWorldManager.updateBlockRemoving();
/*  216: 263 */     this.field_147101_bU -= 1;
/*  217: 265 */     if (this.hurtResistantTime > 0) {
/*  218: 267 */       this.hurtResistantTime -= 1;
/*  219:     */     }
/*  220: 270 */     this.openContainer.detectAndSendChanges();
/*  221: 272 */     if ((!this.worldObj.isClient) && (!this.openContainer.canInteractWith(this)))
/*  222:     */     {
/*  223: 274 */       closeScreen();
/*  224: 275 */       this.openContainer = this.inventoryContainer;
/*  225:     */     }
/*  226: 278 */     while (!this.destroyedItemsNetCache.isEmpty())
/*  227:     */     {
/*  228: 280 */       int var1 = Math.min(this.destroyedItemsNetCache.size(), 127);
/*  229: 281 */       int[] var2 = new int[var1];
/*  230: 282 */       Iterator var3 = this.destroyedItemsNetCache.iterator();
/*  231: 283 */       int var4 = 0;
/*  232: 285 */       while ((var3.hasNext()) && (var4 < var1))
/*  233:     */       {
/*  234: 287 */         var2[(var4++)] = ((Integer)var3.next()).intValue();
/*  235: 288 */         var3.remove();
/*  236:     */       }
/*  237: 291 */       this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(var2));
/*  238:     */     }
/*  239: 294 */     if (!this.loadedChunks.isEmpty())
/*  240:     */     {
/*  241: 296 */       ArrayList var6 = new ArrayList();
/*  242: 297 */       Iterator var7 = this.loadedChunks.iterator();
/*  243: 298 */       ArrayList var8 = new ArrayList();
/*  244: 301 */       while ((var7.hasNext()) && (var6.size() < S26PacketMapChunkBulk.func_149258_c()))
/*  245:     */       {
/*  246: 303 */         ChunkCoordIntPair var9 = (ChunkCoordIntPair)var7.next();
/*  247: 305 */         if (var9 != null)
/*  248:     */         {
/*  249: 307 */           if (this.worldObj.blockExists(var9.chunkXPos << 4, 0, var9.chunkZPos << 4))
/*  250:     */           {
/*  251: 309 */             Chunk var5 = this.worldObj.getChunkFromChunkCoords(var9.chunkXPos, var9.chunkZPos);
/*  252: 311 */             if (var5.func_150802_k())
/*  253:     */             {
/*  254: 313 */               var6.add(var5);
/*  255: 314 */               var8.addAll(((WorldServer)this.worldObj).func_147486_a(var9.chunkXPos * 16, 0, var9.chunkZPos * 16, var9.chunkXPos * 16 + 16, 256, var9.chunkZPos * 16 + 16));
/*  256: 315 */               var7.remove();
/*  257:     */             }
/*  258:     */           }
/*  259:     */         }
/*  260:     */         else {
/*  261: 321 */           var7.remove();
/*  262:     */         }
/*  263:     */       }
/*  264: 325 */       if (!var6.isEmpty())
/*  265:     */       {
/*  266: 327 */         this.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(var6));
/*  267: 328 */         Iterator var11 = var8.iterator();
/*  268: 330 */         while (var11.hasNext())
/*  269:     */         {
/*  270: 332 */           TileEntity var10 = (TileEntity)var11.next();
/*  271: 333 */           func_147097_b(var10);
/*  272:     */         }
/*  273: 336 */         var11 = var6.iterator();
/*  274: 338 */         while (var11.hasNext())
/*  275:     */         {
/*  276: 340 */           Chunk var5 = (Chunk)var11.next();
/*  277: 341 */           getServerForPlayer().getEntityTracker().func_85172_a(this, var5);
/*  278:     */         }
/*  279:     */       }
/*  280:     */     }
/*  281: 346 */     if ((this.field_143005_bX > 0L) && (this.mcServer.func_143007_ar() > 0) && (MinecraftServer.getSystemTimeMillis() - this.field_143005_bX > this.mcServer.func_143007_ar() * 1000 * 60)) {
/*  282: 348 */       this.playerNetServerHandler.kickPlayerFromServer("You have been idle for too long!");
/*  283:     */     }
/*  284:     */   }
/*  285:     */   
/*  286:     */   public void onUpdateEntity()
/*  287:     */   {
/*  288:     */     try
/*  289:     */     {
/*  290: 356 */       super.onUpdate();
/*  291: 358 */       for (int var1 = 0; var1 < this.inventory.getSizeInventory(); var1++)
/*  292:     */       {
/*  293: 360 */         ItemStack var6 = this.inventory.getStackInSlot(var1);
/*  294: 362 */         if ((var6 != null) && (var6.getItem().isMap()))
/*  295:     */         {
/*  296: 364 */           Packet var8 = ((ItemMapBase)var6.getItem()).func_150911_c(var6, this.worldObj, this);
/*  297: 366 */           if (var8 != null) {
/*  298: 368 */             this.playerNetServerHandler.sendPacket(var8);
/*  299:     */           }
/*  300:     */         }
/*  301:     */       }
/*  302: 373 */       if ((getHealth() == this.lastHealth) && (this.lastFoodLevel == this.foodStats.getFoodLevel()))
/*  303:     */       {
/*  304: 373 */         if ((this.foodStats.getSaturationLevel() == 0.0F) == this.wasHungry) {}
/*  305:     */       }
/*  306:     */       else
/*  307:     */       {
/*  308: 375 */         this.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
/*  309: 376 */         this.lastHealth = getHealth();
/*  310: 377 */         this.lastFoodLevel = this.foodStats.getFoodLevel();
/*  311: 378 */         this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0F);
/*  312:     */       }
/*  313: 381 */       if (getHealth() + getAbsorptionAmount() != this.field_130068_bO)
/*  314:     */       {
/*  315: 383 */         this.field_130068_bO = (getHealth() + getAbsorptionAmount());
/*  316: 384 */         Collection var5 = getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.health);
/*  317: 385 */         Iterator var7 = var5.iterator();
/*  318: 387 */         while (var7.hasNext())
/*  319:     */         {
/*  320: 389 */           ScoreObjective var9 = (ScoreObjective)var7.next();
/*  321: 390 */           getWorldScoreboard().func_96529_a(getCommandSenderName(), var9).func_96651_a(Arrays.asList(new EntityPlayer[] { this }));
/*  322:     */         }
/*  323:     */       }
/*  324: 394 */       if (this.experienceTotal != this.lastExperience)
/*  325:     */       {
/*  326: 396 */         this.lastExperience = this.experienceTotal;
/*  327: 397 */         this.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
/*  328:     */       }
/*  329: 400 */       if ((this.ticksExisted % 20 * 5 == 0) && (!func_147099_x().hasAchievementUnlocked(AchievementList.field_150961_L))) {
/*  330: 402 */         func_147098_j();
/*  331:     */       }
/*  332:     */     }
/*  333:     */     catch (Throwable var4)
/*  334:     */     {
/*  335: 407 */       CrashReport var2 = CrashReport.makeCrashReport(var4, "Ticking player");
/*  336: 408 */       CrashReportCategory var3 = var2.makeCategory("Player being ticked");
/*  337: 409 */       addEntityCrashInfo(var3);
/*  338: 410 */       throw new ReportedException(var2);
/*  339:     */     }
/*  340:     */   }
/*  341:     */   
/*  342:     */   protected void func_147098_j()
/*  343:     */   {
/*  344: 416 */     BiomeGenBase var1 = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
/*  345: 418 */     if (var1 != null)
/*  346:     */     {
/*  347: 420 */       String var2 = var1.biomeName;
/*  348: 421 */       JsonSerializableSet var3 = (JsonSerializableSet)func_147099_x().func_150870_b(AchievementList.field_150961_L);
/*  349: 423 */       if (var3 == null) {
/*  350: 425 */         var3 = (JsonSerializableSet)func_147099_x().func_150872_a(AchievementList.field_150961_L, new JsonSerializableSet());
/*  351:     */       }
/*  352: 428 */       var3.add(var2);
/*  353: 430 */       if ((func_147099_x().canUnlockAchievement(AchievementList.field_150961_L)) && (var3.size() == BiomeGenBase.field_150597_n.size()))
/*  354:     */       {
/*  355: 432 */         HashSet var4 = Sets.newHashSet(BiomeGenBase.field_150597_n);
/*  356: 433 */         Iterator var5 = var3.iterator();
/*  357: 435 */         while (var5.hasNext())
/*  358:     */         {
/*  359: 437 */           String var6 = (String)var5.next();
/*  360: 438 */           Iterator var7 = var4.iterator();
/*  361: 440 */           while (var7.hasNext())
/*  362:     */           {
/*  363: 442 */             BiomeGenBase var8 = (BiomeGenBase)var7.next();
/*  364: 444 */             if (var8.biomeName.equals(var6)) {
/*  365: 446 */               var7.remove();
/*  366:     */             }
/*  367:     */           }
/*  368: 450 */           if (var4.isEmpty()) {
/*  369:     */             break;
/*  370:     */           }
/*  371:     */         }
/*  372: 456 */         if (var4.isEmpty()) {
/*  373: 458 */           triggerAchievement(AchievementList.field_150961_L);
/*  374:     */         }
/*  375:     */       }
/*  376:     */     }
/*  377:     */   }
/*  378:     */   
/*  379:     */   public void onDeath(DamageSource par1DamageSource)
/*  380:     */   {
/*  381: 469 */     this.mcServer.getConfigurationManager().func_148539_a(func_110142_aN().func_151521_b());
/*  382: 471 */     if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
/*  383: 473 */       this.inventory.dropAllItems();
/*  384:     */     }
/*  385: 476 */     Collection var2 = this.worldObj.getScoreboard().func_96520_a(IScoreObjectiveCriteria.deathCount);
/*  386: 477 */     Iterator var3 = var2.iterator();
/*  387: 479 */     while (var3.hasNext())
/*  388:     */     {
/*  389: 481 */       ScoreObjective var4 = (ScoreObjective)var3.next();
/*  390: 482 */       Score var5 = getWorldScoreboard().func_96529_a(getCommandSenderName(), var4);
/*  391: 483 */       var5.func_96648_a();
/*  392:     */     }
/*  393: 486 */     EntityLivingBase var6 = func_94060_bK();
/*  394: 488 */     if (var6 != null)
/*  395:     */     {
/*  396: 490 */       int var7 = EntityList.getEntityID(var6);
/*  397: 491 */       EntityList.EntityEggInfo var8 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(var7));
/*  398: 493 */       if (var8 != null) {
/*  399: 495 */         addStat(var8.field_151513_e, 1);
/*  400:     */       }
/*  401: 498 */       var6.addToPlayerScore(this, this.scoreValue);
/*  402:     */     }
/*  403: 501 */     addStat(StatList.deathsStat, 1);
/*  404:     */   }
/*  405:     */   
/*  406:     */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  407:     */   {
/*  408: 509 */     if (isEntityInvulnerable()) {
/*  409: 511 */       return false;
/*  410:     */     }
/*  411: 515 */     boolean var3 = (this.mcServer.isDedicatedServer()) && (this.mcServer.isPVPEnabled()) && ("fall".equals(par1DamageSource.damageType));
/*  412: 517 */     if ((!var3) && (this.field_147101_bU > 0) && (par1DamageSource != DamageSource.outOfWorld)) {
/*  413: 519 */       return false;
/*  414:     */     }
/*  415: 523 */     if ((par1DamageSource instanceof EntityDamageSource))
/*  416:     */     {
/*  417: 525 */       Entity var4 = par1DamageSource.getEntity();
/*  418: 527 */       if (((var4 instanceof EntityPlayer)) && (!canAttackPlayer((EntityPlayer)var4))) {
/*  419: 529 */         return false;
/*  420:     */       }
/*  421: 532 */       if ((var4 instanceof EntityArrow))
/*  422:     */       {
/*  423: 534 */         EntityArrow var5 = (EntityArrow)var4;
/*  424: 536 */         if (((var5.shootingEntity instanceof EntityPlayer)) && (!canAttackPlayer((EntityPlayer)var5.shootingEntity))) {
/*  425: 538 */           return false;
/*  426:     */         }
/*  427:     */       }
/*  428:     */     }
/*  429: 543 */     return super.attackEntityFrom(par1DamageSource, par2);
/*  430:     */   }
/*  431:     */   
/*  432:     */   public boolean canAttackPlayer(EntityPlayer par1EntityPlayer)
/*  433:     */   {
/*  434: 550 */     return !this.mcServer.isPVPEnabled() ? false : super.canAttackPlayer(par1EntityPlayer);
/*  435:     */   }
/*  436:     */   
/*  437:     */   public void travelToDimension(int par1)
/*  438:     */   {
/*  439: 558 */     if ((this.dimension == 1) && (par1 == 1))
/*  440:     */     {
/*  441: 560 */       triggerAchievement(AchievementList.theEnd2);
/*  442: 561 */       this.worldObj.removeEntity(this);
/*  443: 562 */       this.playerConqueredTheEnd = true;
/*  444: 563 */       this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0F));
/*  445:     */     }
/*  446:     */     else
/*  447:     */     {
/*  448: 567 */       if ((this.dimension == 0) && (par1 == 1))
/*  449:     */       {
/*  450: 569 */         triggerAchievement(AchievementList.theEnd);
/*  451: 570 */         ChunkCoordinates var2 = this.mcServer.worldServerForDimension(par1).getEntrancePortalLocation();
/*  452: 572 */         if (var2 != null) {
/*  453: 574 */           this.playerNetServerHandler.setPlayerLocation(var2.posX, var2.posY, var2.posZ, 0.0F, 0.0F);
/*  454:     */         }
/*  455: 577 */         par1 = 1;
/*  456:     */       }
/*  457:     */       else
/*  458:     */       {
/*  459: 581 */         triggerAchievement(AchievementList.portal);
/*  460:     */       }
/*  461: 584 */       this.mcServer.getConfigurationManager().transferPlayerToDimension(this, par1);
/*  462: 585 */       this.lastExperience = -1;
/*  463: 586 */       this.lastHealth = -1.0F;
/*  464: 587 */       this.lastFoodLevel = -1;
/*  465:     */     }
/*  466:     */   }
/*  467:     */   
/*  468:     */   private void func_147097_b(TileEntity p_147097_1_)
/*  469:     */   {
/*  470: 593 */     if (p_147097_1_ != null)
/*  471:     */     {
/*  472: 595 */       Packet var2 = p_147097_1_.getDescriptionPacket();
/*  473: 597 */       if (var2 != null) {
/*  474: 599 */         this.playerNetServerHandler.sendPacket(var2);
/*  475:     */       }
/*  476:     */     }
/*  477:     */   }
/*  478:     */   
/*  479:     */   public void onItemPickup(Entity par1Entity, int par2)
/*  480:     */   {
/*  481: 609 */     super.onItemPickup(par1Entity, par2);
/*  482: 610 */     this.openContainer.detectAndSendChanges();
/*  483:     */   }
/*  484:     */   
/*  485:     */   public EntityPlayer.EnumStatus sleepInBedAt(int par1, int par2, int par3)
/*  486:     */   {
/*  487: 618 */     EntityPlayer.EnumStatus var4 = super.sleepInBedAt(par1, par2, par3);
/*  488: 620 */     if (var4 == EntityPlayer.EnumStatus.OK)
/*  489:     */     {
/*  490: 622 */       S0APacketUseBed var5 = new S0APacketUseBed(this, par1, par2, par3);
/*  491: 623 */       getServerForPlayer().getEntityTracker().func_151247_a(this, var5);
/*  492: 624 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  493: 625 */       this.playerNetServerHandler.sendPacket(var5);
/*  494:     */     }
/*  495: 628 */     return var4;
/*  496:     */   }
/*  497:     */   
/*  498:     */   public void wakeUpPlayer(boolean par1, boolean par2, boolean par3)
/*  499:     */   {
/*  500: 636 */     if (isPlayerSleeping()) {
/*  501: 638 */       getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
/*  502:     */     }
/*  503: 641 */     super.wakeUpPlayer(par1, par2, par3);
/*  504: 643 */     if (this.playerNetServerHandler != null) {
/*  505: 645 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  506:     */     }
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void mountEntity(Entity par1Entity)
/*  510:     */   {
/*  511: 654 */     super.mountEntity(par1Entity);
/*  512: 655 */     this.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, this.ridingEntity));
/*  513: 656 */     this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  514:     */   }
/*  515:     */   
/*  516:     */   protected void updateFallState(double par1, boolean par3) {}
/*  517:     */   
/*  518:     */   public void handleFalling(double par1, boolean par3)
/*  519:     */   {
/*  520: 670 */     super.updateFallState(par1, par3);
/*  521:     */   }
/*  522:     */   
/*  523:     */   public void func_146100_a(TileEntity p_146100_1_)
/*  524:     */   {
/*  525: 675 */     if ((p_146100_1_ instanceof TileEntitySign))
/*  526:     */     {
/*  527: 677 */       ((TileEntitySign)p_146100_1_).func_145912_a(this);
/*  528: 678 */       this.playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(p_146100_1_.field_145851_c, p_146100_1_.field_145848_d, p_146100_1_.field_145849_e));
/*  529:     */     }
/*  530:     */   }
/*  531:     */   
/*  532:     */   private void getNextWindowId()
/*  533:     */   {
/*  534: 687 */     this.currentWindowId = (this.currentWindowId % 100 + 1);
/*  535:     */   }
/*  536:     */   
/*  537:     */   public void displayGUIWorkbench(int par1, int par2, int par3)
/*  538:     */   {
/*  539: 695 */     getNextWindowId();
/*  540: 696 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 1, "Crafting", 9, true));
/*  541: 697 */     this.openContainer = new ContainerWorkbench(this.inventory, this.worldObj, par1, par2, par3);
/*  542: 698 */     this.openContainer.windowId = this.currentWindowId;
/*  543: 699 */     this.openContainer.addCraftingToCrafters(this);
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str)
/*  547:     */   {
/*  548: 704 */     getNextWindowId();
/*  549: 705 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 4, par4Str == null ? "" : par4Str, 9, par4Str != null));
/*  550: 706 */     this.openContainer = new ContainerEnchantment(this.inventory, this.worldObj, par1, par2, par3);
/*  551: 707 */     this.openContainer.windowId = this.currentWindowId;
/*  552: 708 */     this.openContainer.addCraftingToCrafters(this);
/*  553:     */   }
/*  554:     */   
/*  555:     */   public void displayGUIAnvil(int par1, int par2, int par3)
/*  556:     */   {
/*  557: 716 */     getNextWindowId();
/*  558: 717 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 8, "Repairing", 9, true));
/*  559: 718 */     this.openContainer = new ContainerRepair(this.inventory, this.worldObj, par1, par2, par3, this);
/*  560: 719 */     this.openContainer.windowId = this.currentWindowId;
/*  561: 720 */     this.openContainer.addCraftingToCrafters(this);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public void displayGUIChest(IInventory par1IInventory)
/*  565:     */   {
/*  566: 728 */     if (this.openContainer != this.inventoryContainer) {
/*  567: 730 */       closeScreen();
/*  568:     */     }
/*  569: 733 */     getNextWindowId();
/*  570: 734 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 0, par1IInventory.getInventoryName(), par1IInventory.getSizeInventory(), par1IInventory.isInventoryNameLocalized()));
/*  571: 735 */     this.openContainer = new ContainerChest(this.inventory, par1IInventory);
/*  572: 736 */     this.openContainer.windowId = this.currentWindowId;
/*  573: 737 */     this.openContainer.addCraftingToCrafters(this);
/*  574:     */   }
/*  575:     */   
/*  576:     */   public void func_146093_a(TileEntityHopper p_146093_1_)
/*  577:     */   {
/*  578: 742 */     getNextWindowId();
/*  579: 743 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 9, p_146093_1_.getInventoryName(), p_146093_1_.getSizeInventory(), p_146093_1_.isInventoryNameLocalized()));
/*  580: 744 */     this.openContainer = new ContainerHopper(this.inventory, p_146093_1_);
/*  581: 745 */     this.openContainer.windowId = this.currentWindowId;
/*  582: 746 */     this.openContainer.addCraftingToCrafters(this);
/*  583:     */   }
/*  584:     */   
/*  585:     */   public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper)
/*  586:     */   {
/*  587: 751 */     getNextWindowId();
/*  588: 752 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 9, par1EntityMinecartHopper.getInventoryName(), par1EntityMinecartHopper.getSizeInventory(), par1EntityMinecartHopper.isInventoryNameLocalized()));
/*  589: 753 */     this.openContainer = new ContainerHopper(this.inventory, par1EntityMinecartHopper);
/*  590: 754 */     this.openContainer.windowId = this.currentWindowId;
/*  591: 755 */     this.openContainer.addCraftingToCrafters(this);
/*  592:     */   }
/*  593:     */   
/*  594:     */   public void func_146101_a(TileEntityFurnace p_146101_1_)
/*  595:     */   {
/*  596: 760 */     getNextWindowId();
/*  597: 761 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 2, p_146101_1_.getInventoryName(), p_146101_1_.getSizeInventory(), p_146101_1_.isInventoryNameLocalized()));
/*  598: 762 */     this.openContainer = new ContainerFurnace(this.inventory, p_146101_1_);
/*  599: 763 */     this.openContainer.windowId = this.currentWindowId;
/*  600: 764 */     this.openContainer.addCraftingToCrafters(this);
/*  601:     */   }
/*  602:     */   
/*  603:     */   public void func_146102_a(TileEntityDispenser p_146102_1_)
/*  604:     */   {
/*  605: 769 */     getNextWindowId();
/*  606: 770 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, (p_146102_1_ instanceof TileEntityDropper) ? 10 : 3, p_146102_1_.getInventoryName(), p_146102_1_.getSizeInventory(), p_146102_1_.isInventoryNameLocalized()));
/*  607: 771 */     this.openContainer = new ContainerDispenser(this.inventory, p_146102_1_);
/*  608: 772 */     this.openContainer.windowId = this.currentWindowId;
/*  609: 773 */     this.openContainer.addCraftingToCrafters(this);
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void func_146098_a(TileEntityBrewingStand p_146098_1_)
/*  613:     */   {
/*  614: 778 */     getNextWindowId();
/*  615: 779 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 5, p_146098_1_.getInventoryName(), p_146098_1_.getSizeInventory(), p_146098_1_.isInventoryNameLocalized()));
/*  616: 780 */     this.openContainer = new ContainerBrewingStand(this.inventory, p_146098_1_);
/*  617: 781 */     this.openContainer.windowId = this.currentWindowId;
/*  618: 782 */     this.openContainer.addCraftingToCrafters(this);
/*  619:     */   }
/*  620:     */   
/*  621:     */   public void func_146104_a(TileEntityBeacon p_146104_1_)
/*  622:     */   {
/*  623: 787 */     getNextWindowId();
/*  624: 788 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 7, p_146104_1_.getInventoryName(), p_146104_1_.getSizeInventory(), p_146104_1_.isInventoryNameLocalized()));
/*  625: 789 */     this.openContainer = new ContainerBeacon(this.inventory, p_146104_1_);
/*  626: 790 */     this.openContainer.windowId = this.currentWindowId;
/*  627: 791 */     this.openContainer.addCraftingToCrafters(this);
/*  628:     */   }
/*  629:     */   
/*  630:     */   public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str)
/*  631:     */   {
/*  632: 796 */     getNextWindowId();
/*  633: 797 */     this.openContainer = new ContainerMerchant(this.inventory, par1IMerchant, this.worldObj);
/*  634: 798 */     this.openContainer.windowId = this.currentWindowId;
/*  635: 799 */     this.openContainer.addCraftingToCrafters(this);
/*  636: 800 */     InventoryMerchant var3 = ((ContainerMerchant)this.openContainer).getMerchantInventory();
/*  637: 801 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 6, par2Str == null ? "" : par2Str, var3.getSizeInventory(), par2Str != null));
/*  638: 802 */     MerchantRecipeList var4 = par1IMerchant.getRecipes(this);
/*  639: 804 */     if (var4 != null) {
/*  640:     */       try
/*  641:     */       {
/*  642: 808 */         PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
/*  643: 809 */         var5.writeInt(this.currentWindowId);
/*  644: 810 */         var4.func_151391_a(var5);
/*  645: 811 */         this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", var5));
/*  646:     */       }
/*  647:     */       catch (IOException var6)
/*  648:     */       {
/*  649: 815 */         logger.error("Couldn't send trade list", var6);
/*  650:     */       }
/*  651:     */     }
/*  652:     */   }
/*  653:     */   
/*  654:     */   public void displayGUIHorse(EntityHorse par1EntityHorse, IInventory par2IInventory)
/*  655:     */   {
/*  656: 822 */     if (this.openContainer != this.inventoryContainer) {
/*  657: 824 */       closeScreen();
/*  658:     */     }
/*  659: 827 */     getNextWindowId();
/*  660: 828 */     this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, 11, par2IInventory.getInventoryName(), par2IInventory.getSizeInventory(), par2IInventory.isInventoryNameLocalized(), par1EntityHorse.getEntityId()));
/*  661: 829 */     this.openContainer = new ContainerHorseInventory(this.inventory, par2IInventory, par1EntityHorse);
/*  662: 830 */     this.openContainer.windowId = this.currentWindowId;
/*  663: 831 */     this.openContainer.addCraftingToCrafters(this);
/*  664:     */   }
/*  665:     */   
/*  666:     */   public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack)
/*  667:     */   {
/*  668: 840 */     if (!(par1Container.getSlot(par2) instanceof SlotCrafting)) {
/*  669: 842 */       if (!this.isChangingQuantityOnly) {
/*  670: 844 */         this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(par1Container.windowId, par2, par3ItemStack));
/*  671:     */       }
/*  672:     */     }
/*  673:     */   }
/*  674:     */   
/*  675:     */   public void sendContainerToPlayer(Container par1Container)
/*  676:     */   {
/*  677: 851 */     sendContainerAndContentsToPlayer(par1Container, par1Container.getInventory());
/*  678:     */   }
/*  679:     */   
/*  680:     */   public void sendContainerAndContentsToPlayer(Container par1Container, List par2List)
/*  681:     */   {
/*  682: 856 */     this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(par1Container.windowId, par2List));
/*  683: 857 */     this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void sendProgressBarUpdate(Container par1Container, int par2, int par3)
/*  687:     */   {
/*  688: 867 */     this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(par1Container.windowId, par2, par3));
/*  689:     */   }
/*  690:     */   
/*  691:     */   public void closeScreen()
/*  692:     */   {
/*  693: 875 */     this.playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(this.openContainer.windowId));
/*  694: 876 */     closeContainer();
/*  695:     */   }
/*  696:     */   
/*  697:     */   public void updateHeldItem()
/*  698:     */   {
/*  699: 884 */     if (!this.isChangingQuantityOnly) {
/*  700: 886 */       this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*  701:     */     }
/*  702:     */   }
/*  703:     */   
/*  704:     */   public void closeContainer()
/*  705:     */   {
/*  706: 895 */     this.openContainer.onContainerClosed(this);
/*  707: 896 */     this.openContainer = this.inventoryContainer;
/*  708:     */   }
/*  709:     */   
/*  710:     */   public void setEntityActionState(float par1, float par2, boolean par3, boolean par4)
/*  711:     */   {
/*  712: 901 */     if (this.ridingEntity != null)
/*  713:     */     {
/*  714: 903 */       if ((par1 >= -1.0F) && (par1 <= 1.0F)) {
/*  715: 905 */         this.moveStrafing = par1;
/*  716:     */       }
/*  717: 908 */       if ((par2 >= -1.0F) && (par2 <= 1.0F)) {
/*  718: 910 */         this.moveForward = par2;
/*  719:     */       }
/*  720: 913 */       this.isJumping = par3;
/*  721: 914 */       setSneaking(par4);
/*  722:     */     }
/*  723:     */   }
/*  724:     */   
/*  725:     */   public void addStat(StatBase par1StatBase, int par2)
/*  726:     */   {
/*  727: 923 */     if (par1StatBase != null)
/*  728:     */     {
/*  729: 925 */       this.field_147103_bO.func_150871_b(this, par1StatBase, par2);
/*  730: 926 */       Iterator var3 = getWorldScoreboard().func_96520_a(par1StatBase.func_150952_k()).iterator();
/*  731: 928 */       while (var3.hasNext())
/*  732:     */       {
/*  733: 930 */         ScoreObjective var4 = (ScoreObjective)var3.next();
/*  734: 931 */         getWorldScoreboard().func_96529_a(getCommandSenderName(), var4).func_96648_a();
/*  735:     */       }
/*  736: 934 */       if (this.field_147103_bO.func_150879_e()) {
/*  737: 936 */         this.field_147103_bO.func_150876_a(this);
/*  738:     */       }
/*  739:     */     }
/*  740:     */   }
/*  741:     */   
/*  742:     */   public void mountEntityAndWakeUp()
/*  743:     */   {
/*  744: 943 */     if (this.riddenByEntity != null) {
/*  745: 945 */       this.riddenByEntity.mountEntity(this);
/*  746:     */     }
/*  747: 948 */     if (this.sleeping) {
/*  748: 950 */       wakeUpPlayer(true, false, false);
/*  749:     */     }
/*  750:     */   }
/*  751:     */   
/*  752:     */   public void setPlayerHealthUpdated()
/*  753:     */   {
/*  754: 960 */     this.lastHealth = -1.0E+008F;
/*  755:     */   }
/*  756:     */   
/*  757:     */   public void addChatComponentMessage(IChatComponent p_146105_1_)
/*  758:     */   {
/*  759: 965 */     this.playerNetServerHandler.sendPacket(new S02PacketChat(p_146105_1_));
/*  760:     */   }
/*  761:     */   
/*  762:     */   protected void onItemUseFinish()
/*  763:     */   {
/*  764: 973 */     this.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, (byte)9));
/*  765: 974 */     super.onItemUseFinish();
/*  766:     */   }
/*  767:     */   
/*  768:     */   public void setItemInUse(ItemStack par1ItemStack, int par2)
/*  769:     */   {
/*  770: 982 */     super.setItemInUse(par1ItemStack, par2);
/*  771: 984 */     if ((par1ItemStack != null) && (par1ItemStack.getItem() != null) && (par1ItemStack.getItem().getItemUseAction(par1ItemStack) == EnumAction.eat)) {
/*  772: 986 */       getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
/*  773:     */     }
/*  774:     */   }
/*  775:     */   
/*  776:     */   public void clonePlayer(EntityPlayer par1EntityPlayer, boolean par2)
/*  777:     */   {
/*  778: 996 */     super.clonePlayer(par1EntityPlayer, par2);
/*  779: 997 */     this.lastExperience = -1;
/*  780: 998 */     this.lastHealth = -1.0F;
/*  781: 999 */     this.lastFoodLevel = -1;
/*  782:1000 */     this.destroyedItemsNetCache.addAll(((EntityPlayerMP)par1EntityPlayer).destroyedItemsNetCache);
/*  783:     */   }
/*  784:     */   
/*  785:     */   protected void onNewPotionEffect(PotionEffect par1PotionEffect)
/*  786:     */   {
/*  787:1005 */     super.onNewPotionEffect(par1PotionEffect);
/*  788:1006 */     this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(getEntityId(), par1PotionEffect));
/*  789:     */   }
/*  790:     */   
/*  791:     */   protected void onChangedPotionEffect(PotionEffect par1PotionEffect, boolean par2)
/*  792:     */   {
/*  793:1011 */     super.onChangedPotionEffect(par1PotionEffect, par2);
/*  794:1012 */     this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(getEntityId(), par1PotionEffect));
/*  795:     */   }
/*  796:     */   
/*  797:     */   protected void onFinishedPotionEffect(PotionEffect par1PotionEffect)
/*  798:     */   {
/*  799:1017 */     super.onFinishedPotionEffect(par1PotionEffect);
/*  800:1018 */     this.playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(getEntityId(), par1PotionEffect));
/*  801:     */   }
/*  802:     */   
/*  803:     */   public void setPositionAndUpdate(double par1, double par3, double par5)
/*  804:     */   {
/*  805:1026 */     this.playerNetServerHandler.setPlayerLocation(par1, par3, par5, this.rotationYaw, this.rotationPitch);
/*  806:     */   }
/*  807:     */   
/*  808:     */   public void onCriticalHit(Entity par1Entity)
/*  809:     */   {
/*  810:1034 */     getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(par1Entity, 4));
/*  811:     */   }
/*  812:     */   
/*  813:     */   public void onEnchantmentCritical(Entity par1Entity)
/*  814:     */   {
/*  815:1039 */     getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(par1Entity, 5));
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void sendPlayerAbilities()
/*  819:     */   {
/*  820:1047 */     if (this.playerNetServerHandler != null) {
/*  821:1049 */       this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
/*  822:     */     }
/*  823:     */   }
/*  824:     */   
/*  825:     */   public WorldServer getServerForPlayer()
/*  826:     */   {
/*  827:1055 */     return (WorldServer)this.worldObj;
/*  828:     */   }
/*  829:     */   
/*  830:     */   public void setGameType(WorldSettings.GameType par1EnumGameType)
/*  831:     */   {
/*  832:1063 */     this.theItemInWorldManager.setGameType(par1EnumGameType);
/*  833:1064 */     this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, par1EnumGameType.getID()));
/*  834:     */   }
/*  835:     */   
/*  836:     */   public void addChatMessage(IChatComponent p_145747_1_)
/*  837:     */   {
/*  838:1075 */     this.playerNetServerHandler.sendPacket(new S02PacketChat(p_145747_1_));
/*  839:     */   }
/*  840:     */   
/*  841:     */   public boolean canCommandSenderUseCommand(int par1, String par2Str)
/*  842:     */   {
/*  843:1083 */     return ("seed".equals(par2Str)) && (!this.mcServer.isDedicatedServer());
/*  844:     */   }
/*  845:     */   
/*  846:     */   public String getPlayerIP()
/*  847:     */   {
/*  848:1091 */     String var1 = this.playerNetServerHandler.netManager.getSocketAddress().toString();
/*  849:1092 */     var1 = var1.substring(var1.indexOf("/") + 1);
/*  850:1093 */     var1 = var1.substring(0, var1.indexOf(":"));
/*  851:1094 */     return var1;
/*  852:     */   }
/*  853:     */   
/*  854:     */   public void func_147100_a(C15PacketClientSettings p_147100_1_)
/*  855:     */   {
/*  856:1099 */     this.translator = p_147100_1_.func_149524_c();
/*  857:1100 */     int var2 = 256 >> p_147100_1_.func_149521_d();
/*  858:1102 */     if ((var2 > 3) && (var2 < 15)) {
/*  859:1104 */       this.renderDistance = var2;
/*  860:     */     }
/*  861:1107 */     this.chatVisibility = p_147100_1_.func_149523_e();
/*  862:1108 */     this.chatColours = p_147100_1_.func_149520_f();
/*  863:1110 */     if ((this.mcServer.isSinglePlayer()) && (this.mcServer.getServerOwner().equals(getCommandSenderName()))) {
/*  864:1112 */       this.mcServer.func_147139_a(p_147100_1_.func_149518_g());
/*  865:     */     }
/*  866:1115 */     setHideCape(1, !p_147100_1_.func_149519_h());
/*  867:     */   }
/*  868:     */   
/*  869:     */   public EntityPlayer.EnumChatVisibility func_147096_v()
/*  870:     */   {
/*  871:1120 */     return this.chatVisibility;
/*  872:     */   }
/*  873:     */   
/*  874:     */   public void func_147095_a(String p_147095_1_)
/*  875:     */   {
/*  876:1125 */     this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|RPack", p_147095_1_.getBytes(Charsets.UTF_8)));
/*  877:     */   }
/*  878:     */   
/*  879:     */   public ChunkCoordinates getPlayerCoordinates()
/*  880:     */   {
/*  881:1133 */     return new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 0.5D), MathHelper.floor_double(this.posZ));
/*  882:     */   }
/*  883:     */   
/*  884:     */   public void func_143004_u()
/*  885:     */   {
/*  886:1138 */     this.field_143005_bX = MinecraftServer.getSystemTimeMillis();
/*  887:     */   }
/*  888:     */   
/*  889:     */   public StatisticsFile func_147099_x()
/*  890:     */   {
/*  891:1143 */     return this.field_147103_bO;
/*  892:     */   }
/*  893:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.player.EntityPlayerMP
 * JD-Core Version:    0.7.0.1
 */