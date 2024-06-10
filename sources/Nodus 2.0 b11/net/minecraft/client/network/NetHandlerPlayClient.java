/*    1:     */ package net.minecraft.client.network;
/*    2:     */ 
/*    3:     */ import com.google.common.base.Charsets;
/*    4:     */ import io.netty.buffer.ByteBuf;
/*    5:     */ import io.netty.buffer.Unpooled;
/*    6:     */ import io.netty.util.concurrent.GenericFutureListener;
/*    7:     */ import java.io.ByteArrayInputStream;
/*    8:     */ import java.io.DataInputStream;
/*    9:     */ import java.io.IOException;
/*   10:     */ import java.util.ArrayList;
/*   11:     */ import java.util.Collection;
/*   12:     */ import java.util.HashMap;
/*   13:     */ import java.util.Iterator;
/*   14:     */ import java.util.List;
/*   15:     */ import java.util.Map;
/*   16:     */ import java.util.Map.Entry;
/*   17:     */ import java.util.Random;
/*   18:     */ import java.util.Set;
/*   19:     */ import me.connorm.Nodus.Nodus;
/*   20:     */ import me.connorm.Nodus.module.NodusModuleManager;
/*   21:     */ import me.connorm.Nodus.module.modules.Unpushable;
/*   22:     */ import me.connorm.Nodus.ui.NodusGuiMainMenu;
/*   23:     */ import net.minecraft.block.Block;
/*   24:     */ import net.minecraft.client.ClientBrandRetriever;
/*   25:     */ import net.minecraft.client.Minecraft;
/*   26:     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   27:     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*   28:     */ import net.minecraft.client.gui.GuiChat;
/*   29:     */ import net.minecraft.client.gui.GuiDisconnected;
/*   30:     */ import net.minecraft.client.gui.GuiDownloadTerrain;
/*   31:     */ import net.minecraft.client.gui.GuiIngame;
/*   32:     */ import net.minecraft.client.gui.GuiMerchant;
/*   33:     */ import net.minecraft.client.gui.GuiMultiplayer;
/*   34:     */ import net.minecraft.client.gui.GuiNewChat;
/*   35:     */ import net.minecraft.client.gui.GuiPlayerInfo;
/*   36:     */ import net.minecraft.client.gui.GuiScreen;
/*   37:     */ import net.minecraft.client.gui.GuiScreenDemo;
/*   38:     */ import net.minecraft.client.gui.GuiScreenDisconnectedOnline;
/*   39:     */ import net.minecraft.client.gui.GuiWinGame;
/*   40:     */ import net.minecraft.client.gui.GuiYesNo;
/*   41:     */ import net.minecraft.client.gui.IProgressMeter;
/*   42:     */ import net.minecraft.client.gui.MapItemRenderer;
/*   43:     */ import net.minecraft.client.gui.achievement.GuiAchievement;
/*   44:     */ import net.minecraft.client.gui.inventory.GuiContainerCreative;
/*   45:     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*   46:     */ import net.minecraft.client.multiplayer.ServerData;
/*   47:     */ import net.minecraft.client.multiplayer.ServerList;
/*   48:     */ import net.minecraft.client.multiplayer.WorldClient;
/*   49:     */ import net.minecraft.client.particle.EffectRenderer;
/*   50:     */ import net.minecraft.client.particle.EntityCrit2FX;
/*   51:     */ import net.minecraft.client.particle.EntityPickupFX;
/*   52:     */ import net.minecraft.client.renderer.EntityRenderer;
/*   53:     */ import net.minecraft.client.resources.I18n;
/*   54:     */ import net.minecraft.client.resources.ResourcePackRepository;
/*   55:     */ import net.minecraft.client.settings.GameSettings;
/*   56:     */ import net.minecraft.client.settings.KeyBinding;
/*   57:     */ import net.minecraft.creativetab.CreativeTabs;
/*   58:     */ import net.minecraft.entity.DataWatcher;
/*   59:     */ import net.minecraft.entity.Entity;
/*   60:     */ import net.minecraft.entity.EntityLeashKnot;
/*   61:     */ import net.minecraft.entity.EntityList;
/*   62:     */ import net.minecraft.entity.EntityLiving;
/*   63:     */ import net.minecraft.entity.EntityLivingBase;
/*   64:     */ import net.minecraft.entity.IMerchant;
/*   65:     */ import net.minecraft.entity.NpcMerchant;
/*   66:     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*   67:     */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*   68:     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   69:     */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*   70:     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*   71:     */ import net.minecraft.entity.item.EntityBoat;
/*   72:     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*   73:     */ import net.minecraft.entity.item.EntityEnderEye;
/*   74:     */ import net.minecraft.entity.item.EntityEnderPearl;
/*   75:     */ import net.minecraft.entity.item.EntityExpBottle;
/*   76:     */ import net.minecraft.entity.item.EntityFallingBlock;
/*   77:     */ import net.minecraft.entity.item.EntityFireworkRocket;
/*   78:     */ import net.minecraft.entity.item.EntityItem;
/*   79:     */ import net.minecraft.entity.item.EntityItemFrame;
/*   80:     */ import net.minecraft.entity.item.EntityMinecart;
/*   81:     */ import net.minecraft.entity.item.EntityPainting;
/*   82:     */ import net.minecraft.entity.item.EntityTNTPrimed;
/*   83:     */ import net.minecraft.entity.item.EntityXPOrb;
/*   84:     */ import net.minecraft.entity.passive.EntityHorse;
/*   85:     */ import net.minecraft.entity.player.EntityPlayer;
/*   86:     */ import net.minecraft.entity.player.InventoryPlayer;
/*   87:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   88:     */ import net.minecraft.entity.projectile.EntityArrow;
/*   89:     */ import net.minecraft.entity.projectile.EntityEgg;
/*   90:     */ import net.minecraft.entity.projectile.EntityFishHook;
/*   91:     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*   92:     */ import net.minecraft.entity.projectile.EntityPotion;
/*   93:     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*   94:     */ import net.minecraft.entity.projectile.EntitySnowball;
/*   95:     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*   96:     */ import net.minecraft.inventory.AnimalChest;
/*   97:     */ import net.minecraft.inventory.Container;
/*   98:     */ import net.minecraft.inventory.InventoryBasic;
/*   99:     */ import net.minecraft.inventory.Slot;
/*  100:     */ import net.minecraft.item.Item;
/*  101:     */ import net.minecraft.item.ItemMap;
/*  102:     */ import net.minecraft.item.ItemStack;
/*  103:     */ import net.minecraft.network.EnumConnectionState;
/*  104:     */ import net.minecraft.network.NetworkManager;
/*  105:     */ import net.minecraft.network.Packet;
/*  106:     */ import net.minecraft.network.PacketBuffer;
/*  107:     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*  108:     */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*  109:     */ import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
/*  110:     */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*  111:     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*  112:     */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*  113:     */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*  114:     */ import net.minecraft.network.play.server.S02PacketChat;
/*  115:     */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*  116:     */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*  117:     */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*  118:     */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*  119:     */ import net.minecraft.network.play.server.S07PacketRespawn;
/*  120:     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*  121:     */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*  122:     */ import net.minecraft.network.play.server.S0APacketUseBed;
/*  123:     */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*  124:     */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*  125:     */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*  126:     */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*  127:     */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*  128:     */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*  129:     */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*  130:     */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*  131:     */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*  132:     */ import net.minecraft.network.play.server.S14PacketEntity;
/*  133:     */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*  134:     */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*  135:     */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*  136:     */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*  137:     */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*  138:     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*  139:     */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*  140:     */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*  141:     */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*  142:     */ import net.minecraft.network.play.server.S20PacketEntityProperties.Snapshot;
/*  143:     */ import net.minecraft.network.play.server.S21PacketChunkData;
/*  144:     */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*  145:     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*  146:     */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*  147:     */ import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
/*  148:     */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*  149:     */ import net.minecraft.network.play.server.S27PacketExplosion;
/*  150:     */ import net.minecraft.network.play.server.S28PacketEffect;
/*  151:     */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*  152:     */ import net.minecraft.network.play.server.S2APacketParticles;
/*  153:     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*  154:     */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*  155:     */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*  156:     */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*  157:     */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*  158:     */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*  159:     */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*  160:     */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*  161:     */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*  162:     */ import net.minecraft.network.play.server.S34PacketMaps;
/*  163:     */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  164:     */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*  165:     */ import net.minecraft.network.play.server.S37PacketStatistics;
/*  166:     */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*  167:     */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*  168:     */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*  169:     */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*  170:     */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*  171:     */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*  172:     */ import net.minecraft.network.play.server.S3EPacketTeams;
/*  173:     */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*  174:     */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*  175:     */ import net.minecraft.potion.PotionEffect;
/*  176:     */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*  177:     */ import net.minecraft.scoreboard.Score;
/*  178:     */ import net.minecraft.scoreboard.ScoreObjective;
/*  179:     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*  180:     */ import net.minecraft.scoreboard.Scoreboard;
/*  181:     */ import net.minecraft.stats.Achievement;
/*  182:     */ import net.minecraft.stats.AchievementList;
/*  183:     */ import net.minecraft.stats.StatBase;
/*  184:     */ import net.minecraft.stats.StatFileWriter;
/*  185:     */ import net.minecraft.tileentity.TileEntity;
/*  186:     */ import net.minecraft.tileentity.TileEntityBeacon;
/*  187:     */ import net.minecraft.tileentity.TileEntityBrewingStand;
/*  188:     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*  189:     */ import net.minecraft.tileentity.TileEntityDispenser;
/*  190:     */ import net.minecraft.tileentity.TileEntityDropper;
/*  191:     */ import net.minecraft.tileentity.TileEntityFlowerPot;
/*  192:     */ import net.minecraft.tileentity.TileEntityFurnace;
/*  193:     */ import net.minecraft.tileentity.TileEntityHopper;
/*  194:     */ import net.minecraft.tileentity.TileEntityMobSpawner;
/*  195:     */ import net.minecraft.tileentity.TileEntitySign;
/*  196:     */ import net.minecraft.tileentity.TileEntitySkull;
/*  197:     */ import net.minecraft.util.AxisAlignedBB;
/*  198:     */ import net.minecraft.util.ChatComponentText;
/*  199:     */ import net.minecraft.util.ChatComponentTranslation;
/*  200:     */ import net.minecraft.util.ChunkCoordinates;
/*  201:     */ import net.minecraft.util.FoodStats;
/*  202:     */ import net.minecraft.util.IChatComponent;
/*  203:     */ import net.minecraft.util.MathHelper;
/*  204:     */ import net.minecraft.village.MerchantRecipeList;
/*  205:     */ import net.minecraft.world.ChunkCoordIntPair;
/*  206:     */ import net.minecraft.world.Explosion;
/*  207:     */ import net.minecraft.world.WorldProviderSurface;
/*  208:     */ import net.minecraft.world.WorldSettings;
/*  209:     */ import net.minecraft.world.WorldSettings.GameType;
/*  210:     */ import net.minecraft.world.chunk.Chunk;
/*  211:     */ import net.minecraft.world.storage.MapData;
/*  212:     */ import net.minecraft.world.storage.MapStorage;
/*  213:     */ import net.minecraft.world.storage.WorldInfo;
/*  214:     */ import org.apache.logging.log4j.LogManager;
/*  215:     */ import org.apache.logging.log4j.Logger;
/*  216:     */ 
/*  217:     */ public class NetHandlerPlayClient
/*  218:     */   implements INetHandlerPlayClient
/*  219:     */ {
/*  220: 201 */   private static final Logger logger = ;
/*  221:     */   private final NetworkManager netManager;
/*  222:     */   private Minecraft gameController;
/*  223:     */   private WorldClient clientWorldController;
/*  224:     */   private boolean doneLoadingTerrain;
/*  225: 228 */   public MapStorage mapStorageOrigin = new MapStorage(null);
/*  226: 233 */   private Map playerInfoMap = new HashMap();
/*  227: 238 */   public List playerInfoList = new ArrayList();
/*  228: 239 */   public int currentServerMaxPlayers = 20;
/*  229:     */   private GuiScreen guiScreenServer;
/*  230: 246 */   private boolean field_147308_k = false;
/*  231: 252 */   private Random avRandomizer = new Random();
/*  232:     */   private static final String __OBFID = "CL_00000878";
/*  233:     */   
/*  234:     */   public NetHandlerPlayClient(Minecraft p_i45061_1_, GuiScreen p_i45061_2_, NetworkManager p_i45061_3_)
/*  235:     */   {
/*  236: 257 */     this.gameController = p_i45061_1_;
/*  237: 258 */     this.guiScreenServer = p_i45061_2_;
/*  238: 259 */     this.netManager = p_i45061_3_;
/*  239:     */   }
/*  240:     */   
/*  241:     */   public void cleanup()
/*  242:     */   {
/*  243: 267 */     this.clientWorldController = null;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public void onNetworkTick() {}
/*  247:     */   
/*  248:     */   public void handleJoinGame(S01PacketJoinGame p_147282_1_)
/*  249:     */   {
/*  250: 282 */     this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
/*  251: 283 */     this.clientWorldController = new WorldClient(this, new WorldSettings(0L, p_147282_1_.func_149198_e(), false, p_147282_1_.func_149195_d(), p_147282_1_.func_149196_i()), p_147282_1_.func_149194_f(), p_147282_1_.func_149192_g(), this.gameController.mcProfiler);
/*  252: 284 */     this.clientWorldController.isClient = true;
/*  253: 285 */     this.gameController.loadWorld(this.clientWorldController);
/*  254: 286 */     this.gameController.thePlayer.dimension = p_147282_1_.func_149194_f();
/*  255: 287 */     this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
/*  256: 288 */     this.gameController.thePlayer.setEntityId(p_147282_1_.func_149197_c());
/*  257: 289 */     this.currentServerMaxPlayers = p_147282_1_.func_149193_h();
/*  258: 290 */     this.gameController.playerController.setGameType(p_147282_1_.func_149198_e());
/*  259: 291 */     this.gameController.gameSettings.sendSettingsToServer();
/*  260: 292 */     this.netManager.scheduleOutboundPacket(new C17PacketCustomPayload("MC|Brand", ClientBrandRetriever.getClientModName().getBytes(Charsets.UTF_8)), new GenericFutureListener[0]);
/*  261:     */   }
/*  262:     */   
/*  263:     */   public void handleSpawnObject(S0EPacketSpawnObject p_147235_1_)
/*  264:     */   {
/*  265: 300 */     double var2 = p_147235_1_.func_148997_d() / 32.0D;
/*  266: 301 */     double var4 = p_147235_1_.func_148998_e() / 32.0D;
/*  267: 302 */     double var6 = p_147235_1_.func_148994_f() / 32.0D;
/*  268: 303 */     Object var8 = null;
/*  269: 305 */     if (p_147235_1_.func_148993_l() == 10)
/*  270:     */     {
/*  271: 307 */       var8 = EntityMinecart.createMinecart(this.clientWorldController, var2, var4, var6, p_147235_1_.func_149009_m());
/*  272:     */     }
/*  273: 309 */     else if (p_147235_1_.func_148993_l() == 90)
/*  274:     */     {
/*  275: 311 */       Entity var9 = this.clientWorldController.getEntityByID(p_147235_1_.func_149009_m());
/*  276: 313 */       if ((var9 instanceof EntityPlayer)) {
/*  277: 315 */         var8 = new EntityFishHook(this.clientWorldController, var2, var4, var6, (EntityPlayer)var9);
/*  278:     */       }
/*  279: 318 */       p_147235_1_.func_149002_g(0);
/*  280:     */     }
/*  281: 320 */     else if (p_147235_1_.func_148993_l() == 60)
/*  282:     */     {
/*  283: 322 */       var8 = new EntityArrow(this.clientWorldController, var2, var4, var6);
/*  284:     */     }
/*  285: 324 */     else if (p_147235_1_.func_148993_l() == 61)
/*  286:     */     {
/*  287: 326 */       var8 = new EntitySnowball(this.clientWorldController, var2, var4, var6);
/*  288:     */     }
/*  289: 328 */     else if (p_147235_1_.func_148993_l() == 71)
/*  290:     */     {
/*  291: 330 */       var8 = new EntityItemFrame(this.clientWorldController, (int)var2, (int)var4, (int)var6, p_147235_1_.func_149009_m());
/*  292: 331 */       p_147235_1_.func_149002_g(0);
/*  293:     */     }
/*  294: 333 */     else if (p_147235_1_.func_148993_l() == 77)
/*  295:     */     {
/*  296: 335 */       var8 = new EntityLeashKnot(this.clientWorldController, (int)var2, (int)var4, (int)var6);
/*  297: 336 */       p_147235_1_.func_149002_g(0);
/*  298:     */     }
/*  299: 338 */     else if (p_147235_1_.func_148993_l() == 65)
/*  300:     */     {
/*  301: 340 */       var8 = new EntityEnderPearl(this.clientWorldController, var2, var4, var6);
/*  302:     */     }
/*  303: 342 */     else if (p_147235_1_.func_148993_l() == 72)
/*  304:     */     {
/*  305: 344 */       var8 = new EntityEnderEye(this.clientWorldController, var2, var4, var6);
/*  306:     */     }
/*  307: 346 */     else if (p_147235_1_.func_148993_l() == 76)
/*  308:     */     {
/*  309: 348 */       var8 = new EntityFireworkRocket(this.clientWorldController, var2, var4, var6, null);
/*  310:     */     }
/*  311: 350 */     else if (p_147235_1_.func_148993_l() == 63)
/*  312:     */     {
/*  313: 352 */       var8 = new EntityLargeFireball(this.clientWorldController, var2, var4, var6, p_147235_1_.func_149010_g() / 8000.0D, p_147235_1_.func_149004_h() / 8000.0D, p_147235_1_.func_148999_i() / 8000.0D);
/*  314: 353 */       p_147235_1_.func_149002_g(0);
/*  315:     */     }
/*  316: 355 */     else if (p_147235_1_.func_148993_l() == 64)
/*  317:     */     {
/*  318: 357 */       var8 = new EntitySmallFireball(this.clientWorldController, var2, var4, var6, p_147235_1_.func_149010_g() / 8000.0D, p_147235_1_.func_149004_h() / 8000.0D, p_147235_1_.func_148999_i() / 8000.0D);
/*  319: 358 */       p_147235_1_.func_149002_g(0);
/*  320:     */     }
/*  321: 360 */     else if (p_147235_1_.func_148993_l() == 66)
/*  322:     */     {
/*  323: 362 */       var8 = new EntityWitherSkull(this.clientWorldController, var2, var4, var6, p_147235_1_.func_149010_g() / 8000.0D, p_147235_1_.func_149004_h() / 8000.0D, p_147235_1_.func_148999_i() / 8000.0D);
/*  324: 363 */       p_147235_1_.func_149002_g(0);
/*  325:     */     }
/*  326: 365 */     else if (p_147235_1_.func_148993_l() == 62)
/*  327:     */     {
/*  328: 367 */       var8 = new EntityEgg(this.clientWorldController, var2, var4, var6);
/*  329:     */     }
/*  330: 369 */     else if (p_147235_1_.func_148993_l() == 73)
/*  331:     */     {
/*  332: 371 */       var8 = new EntityPotion(this.clientWorldController, var2, var4, var6, p_147235_1_.func_149009_m());
/*  333: 372 */       p_147235_1_.func_149002_g(0);
/*  334:     */     }
/*  335: 374 */     else if (p_147235_1_.func_148993_l() == 75)
/*  336:     */     {
/*  337: 376 */       var8 = new EntityExpBottle(this.clientWorldController, var2, var4, var6);
/*  338: 377 */       p_147235_1_.func_149002_g(0);
/*  339:     */     }
/*  340: 379 */     else if (p_147235_1_.func_148993_l() == 1)
/*  341:     */     {
/*  342: 381 */       var8 = new EntityBoat(this.clientWorldController, var2, var4, var6);
/*  343:     */     }
/*  344: 383 */     else if (p_147235_1_.func_148993_l() == 50)
/*  345:     */     {
/*  346: 385 */       var8 = new EntityTNTPrimed(this.clientWorldController, var2, var4, var6, null);
/*  347:     */     }
/*  348: 387 */     else if (p_147235_1_.func_148993_l() == 51)
/*  349:     */     {
/*  350: 389 */       var8 = new EntityEnderCrystal(this.clientWorldController, var2, var4, var6);
/*  351:     */     }
/*  352: 391 */     else if (p_147235_1_.func_148993_l() == 2)
/*  353:     */     {
/*  354: 393 */       var8 = new EntityItem(this.clientWorldController, var2, var4, var6);
/*  355:     */     }
/*  356: 395 */     else if (p_147235_1_.func_148993_l() == 70)
/*  357:     */     {
/*  358: 397 */       var8 = new EntityFallingBlock(this.clientWorldController, var2, var4, var6, Block.getBlockById(p_147235_1_.func_149009_m() & 0xFFFF), p_147235_1_.func_149009_m() >> 16);
/*  359: 398 */       p_147235_1_.func_149002_g(0);
/*  360:     */     }
/*  361: 401 */     if (var8 != null)
/*  362:     */     {
/*  363: 403 */       ((Entity)var8).serverPosX = p_147235_1_.func_148997_d();
/*  364: 404 */       ((Entity)var8).serverPosY = p_147235_1_.func_148998_e();
/*  365: 405 */       ((Entity)var8).serverPosZ = p_147235_1_.func_148994_f();
/*  366: 406 */       ((Entity)var8).rotationPitch = (p_147235_1_.func_149008_j() * 360 / 256.0F);
/*  367: 407 */       ((Entity)var8).rotationYaw = (p_147235_1_.func_149006_k() * 360 / 256.0F);
/*  368: 408 */       Entity[] var12 = ((Entity)var8).getParts();
/*  369: 410 */       if (var12 != null)
/*  370:     */       {
/*  371: 412 */         int var10 = p_147235_1_.func_149001_c() - ((Entity)var8).getEntityId();
/*  372: 414 */         for (int var11 = 0; var11 < var12.length; var11++) {
/*  373: 416 */           var12[var11].setEntityId(var12[var11].getEntityId() + var10);
/*  374:     */         }
/*  375:     */       }
/*  376: 420 */       ((Entity)var8).setEntityId(p_147235_1_.func_149001_c());
/*  377: 421 */       this.clientWorldController.addEntityToWorld(p_147235_1_.func_149001_c(), (Entity)var8);
/*  378: 423 */       if (p_147235_1_.func_149009_m() > 0)
/*  379:     */       {
/*  380: 425 */         if (p_147235_1_.func_148993_l() == 60)
/*  381:     */         {
/*  382: 427 */           Entity var13 = this.clientWorldController.getEntityByID(p_147235_1_.func_149009_m());
/*  383: 429 */           if ((var13 instanceof EntityLivingBase))
/*  384:     */           {
/*  385: 431 */             EntityArrow var14 = (EntityArrow)var8;
/*  386: 432 */             var14.shootingEntity = var13;
/*  387:     */           }
/*  388:     */         }
/*  389: 436 */         ((Entity)var8).setVelocity(p_147235_1_.func_149010_g() / 8000.0D, p_147235_1_.func_149004_h() / 8000.0D, p_147235_1_.func_148999_i() / 8000.0D);
/*  390:     */       }
/*  391:     */     }
/*  392:     */   }
/*  393:     */   
/*  394:     */   public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb p_147286_1_)
/*  395:     */   {
/*  396: 446 */     EntityXPOrb var2 = new EntityXPOrb(this.clientWorldController, p_147286_1_.func_148984_d(), p_147286_1_.func_148983_e(), p_147286_1_.func_148982_f(), p_147286_1_.func_148986_g());
/*  397: 447 */     var2.serverPosX = p_147286_1_.func_148984_d();
/*  398: 448 */     var2.serverPosY = p_147286_1_.func_148983_e();
/*  399: 449 */     var2.serverPosZ = p_147286_1_.func_148982_f();
/*  400: 450 */     var2.rotationYaw = 0.0F;
/*  401: 451 */     var2.rotationPitch = 0.0F;
/*  402: 452 */     var2.setEntityId(p_147286_1_.func_148985_c());
/*  403: 453 */     this.clientWorldController.addEntityToWorld(p_147286_1_.func_148985_c(), var2);
/*  404:     */   }
/*  405:     */   
/*  406:     */   public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity p_147292_1_)
/*  407:     */   {
/*  408: 461 */     double var2 = p_147292_1_.func_149051_d() / 32.0D;
/*  409: 462 */     double var4 = p_147292_1_.func_149050_e() / 32.0D;
/*  410: 463 */     double var6 = p_147292_1_.func_149049_f() / 32.0D;
/*  411: 464 */     EntityLightningBolt var8 = null;
/*  412: 466 */     if (p_147292_1_.func_149053_g() == 1) {
/*  413: 468 */       var8 = new EntityLightningBolt(this.clientWorldController, var2, var4, var6);
/*  414:     */     }
/*  415: 471 */     if (var8 != null)
/*  416:     */     {
/*  417: 473 */       var8.serverPosX = p_147292_1_.func_149051_d();
/*  418: 474 */       var8.serverPosY = p_147292_1_.func_149050_e();
/*  419: 475 */       var8.serverPosZ = p_147292_1_.func_149049_f();
/*  420: 476 */       var8.rotationYaw = 0.0F;
/*  421: 477 */       var8.rotationPitch = 0.0F;
/*  422: 478 */       var8.setEntityId(p_147292_1_.func_149052_c());
/*  423: 479 */       this.clientWorldController.addWeatherEffect(var8);
/*  424:     */     }
/*  425:     */   }
/*  426:     */   
/*  427:     */   public void handleSpawnPainting(S10PacketSpawnPainting p_147288_1_)
/*  428:     */   {
/*  429: 488 */     EntityPainting var2 = new EntityPainting(this.clientWorldController, p_147288_1_.func_148964_d(), p_147288_1_.func_148963_e(), p_147288_1_.func_148962_f(), p_147288_1_.func_148966_g(), p_147288_1_.func_148961_h());
/*  430: 489 */     this.clientWorldController.addEntityToWorld(p_147288_1_.func_148965_c(), var2);
/*  431:     */   }
/*  432:     */   
/*  433:     */   public void handleEntityVelocity(S12PacketEntityVelocity p_147244_1_)
/*  434:     */   {
/*  435: 498 */     if (Nodus.theNodus.moduleManager.unpushableModule.isToggled()) {
/*  436: 499 */       return;
/*  437:     */     }
/*  438: 501 */     Entity var2 = this.clientWorldController.getEntityByID(p_147244_1_.func_149412_c());
/*  439: 503 */     if (var2 != null) {
/*  440: 505 */       var2.setVelocity(p_147244_1_.func_149411_d() / 8000.0D, p_147244_1_.func_149410_e() / 8000.0D, p_147244_1_.func_149409_f() / 8000.0D);
/*  441:     */     }
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void handleEntityMetadata(S1CPacketEntityMetadata p_147284_1_)
/*  445:     */   {
/*  446: 515 */     Entity var2 = this.clientWorldController.getEntityByID(p_147284_1_.func_149375_d());
/*  447: 517 */     if ((var2 != null) && (p_147284_1_.func_149376_c() != null)) {
/*  448: 519 */       var2.getDataWatcher().updateWatchedObjectsFromList(p_147284_1_.func_149376_c());
/*  449:     */     }
/*  450:     */   }
/*  451:     */   
/*  452:     */   public void handleSpawnPlayer(S0CPacketSpawnPlayer p_147237_1_)
/*  453:     */   {
/*  454: 528 */     double var2 = p_147237_1_.func_148942_f() / 32.0D;
/*  455: 529 */     double var4 = p_147237_1_.func_148949_g() / 32.0D;
/*  456: 530 */     double var6 = p_147237_1_.func_148946_h() / 32.0D;
/*  457: 531 */     float var8 = p_147237_1_.func_148941_i() * 360 / 256.0F;
/*  458: 532 */     float var9 = p_147237_1_.func_148945_j() * 360 / 256.0F;
/*  459: 533 */     EntityOtherPlayerMP var10 = new EntityOtherPlayerMP(this.gameController.theWorld, p_147237_1_.func_148948_e());
/*  460: 534 */     var10.prevPosX = (var10.lastTickPosX = var10.serverPosX = p_147237_1_.func_148942_f());
/*  461: 535 */     var10.prevPosY = (var10.lastTickPosY = var10.serverPosY = p_147237_1_.func_148949_g());
/*  462: 536 */     var10.prevPosZ = (var10.lastTickPosZ = var10.serverPosZ = p_147237_1_.func_148946_h());
/*  463: 537 */     int var11 = p_147237_1_.func_148947_k();
/*  464: 539 */     if (var11 == 0) {
/*  465: 541 */       var10.inventory.mainInventory[var10.inventory.currentItem] = null;
/*  466:     */     } else {
/*  467: 545 */       var10.inventory.mainInventory[var10.inventory.currentItem] = new ItemStack(Item.getItemById(var11), 1, 0);
/*  468:     */     }
/*  469: 548 */     var10.setPositionAndRotation(var2, var4, var6, var8, var9);
/*  470: 549 */     this.clientWorldController.addEntityToWorld(p_147237_1_.func_148943_d(), var10);
/*  471: 550 */     List var12 = p_147237_1_.func_148944_c();
/*  472: 552 */     if (var12 != null) {
/*  473: 554 */       var10.getDataWatcher().updateWatchedObjectsFromList(var12);
/*  474:     */     }
/*  475:     */   }
/*  476:     */   
/*  477:     */   public void handleEntityTeleport(S18PacketEntityTeleport p_147275_1_)
/*  478:     */   {
/*  479: 563 */     Entity var2 = this.clientWorldController.getEntityByID(p_147275_1_.func_149451_c());
/*  480: 565 */     if (var2 != null)
/*  481:     */     {
/*  482: 567 */       var2.serverPosX = p_147275_1_.func_149449_d();
/*  483: 568 */       var2.serverPosY = p_147275_1_.func_149448_e();
/*  484: 569 */       var2.serverPosZ = p_147275_1_.func_149446_f();
/*  485: 570 */       double var3 = var2.serverPosX / 32.0D;
/*  486: 571 */       double var5 = var2.serverPosY / 32.0D + 0.015625D;
/*  487: 572 */       double var7 = var2.serverPosZ / 32.0D;
/*  488: 573 */       float var9 = p_147275_1_.func_149450_g() * 360 / 256.0F;
/*  489: 574 */       float var10 = p_147275_1_.func_149447_h() * 360 / 256.0F;
/*  490: 575 */       var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
/*  491:     */     }
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void handleHeldItemChange(S09PacketHeldItemChange p_147257_1_)
/*  495:     */   {
/*  496: 584 */     if ((p_147257_1_.func_149385_c() >= 0) && (p_147257_1_.func_149385_c() < InventoryPlayer.getHotbarSize())) {
/*  497: 586 */       this.gameController.thePlayer.inventory.currentItem = p_147257_1_.func_149385_c();
/*  498:     */     }
/*  499:     */   }
/*  500:     */   
/*  501:     */   public void handleEntityMovement(S14PacketEntity p_147259_1_)
/*  502:     */   {
/*  503: 597 */     Entity var2 = p_147259_1_.func_149065_a(this.clientWorldController);
/*  504: 599 */     if (var2 != null)
/*  505:     */     {
/*  506: 601 */       var2.serverPosX += p_147259_1_.func_149062_c();
/*  507: 602 */       var2.serverPosY += p_147259_1_.func_149061_d();
/*  508: 603 */       var2.serverPosZ += p_147259_1_.func_149064_e();
/*  509: 604 */       double var3 = var2.serverPosX / 32.0D;
/*  510: 605 */       double var5 = var2.serverPosY / 32.0D;
/*  511: 606 */       double var7 = var2.serverPosZ / 32.0D;
/*  512: 607 */       float var9 = p_147259_1_.func_149060_h() ? p_147259_1_.func_149066_f() * 360 / 256.0F : var2.rotationYaw;
/*  513: 608 */       float var10 = p_147259_1_.func_149060_h() ? p_147259_1_.func_149063_g() * 360 / 256.0F : var2.rotationPitch;
/*  514: 609 */       var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
/*  515:     */     }
/*  516:     */   }
/*  517:     */   
/*  518:     */   public void handleEntityHeadLook(S19PacketEntityHeadLook p_147267_1_)
/*  519:     */   {
/*  520: 619 */     Entity var2 = p_147267_1_.func_149381_a(this.clientWorldController);
/*  521: 621 */     if (var2 != null)
/*  522:     */     {
/*  523: 623 */       float var3 = p_147267_1_.func_149380_c() * 360 / 256.0F;
/*  524: 624 */       var2.setRotationYawHead(var3);
/*  525:     */     }
/*  526:     */   }
/*  527:     */   
/*  528:     */   public void handleDestroyEntities(S13PacketDestroyEntities p_147238_1_)
/*  529:     */   {
/*  530: 635 */     for (int var2 = 0; var2 < p_147238_1_.func_149098_c().length; var2++) {
/*  531: 637 */       this.clientWorldController.removeEntityFromWorld(p_147238_1_.func_149098_c()[var2]);
/*  532:     */     }
/*  533:     */   }
/*  534:     */   
/*  535:     */   public void handlePlayerPosLook(S08PacketPlayerPosLook p_147258_1_)
/*  536:     */   {
/*  537: 648 */     EntityClientPlayerMP var2 = this.gameController.thePlayer;
/*  538: 649 */     double var3 = p_147258_1_.func_148932_c();
/*  539: 650 */     double var5 = p_147258_1_.func_148928_d();
/*  540: 651 */     double var7 = p_147258_1_.func_148933_e();
/*  541: 652 */     float var9 = p_147258_1_.func_148931_f();
/*  542: 653 */     float var10 = p_147258_1_.func_148930_g();
/*  543: 654 */     var2.ySize = 0.0F;
/*  544: 655 */     var2.motionX = (var2.motionY = var2.motionZ = 0.0D);
/*  545: 656 */     var2.setPositionAndRotation(var3, var5, var7, var9, var10);
/*  546: 657 */     this.netManager.scheduleOutboundPacket(new C03PacketPlayer.C06PacketPlayerPosLook(var2.posX, var2.boundingBox.minY, var2.posY, var2.posZ, p_147258_1_.func_148931_f(), p_147258_1_.func_148930_g(), p_147258_1_.func_148929_h()), new GenericFutureListener[0]);
/*  547: 659 */     if (!this.doneLoadingTerrain)
/*  548:     */     {
/*  549: 661 */       this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
/*  550: 662 */       this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
/*  551: 663 */       this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
/*  552: 664 */       this.doneLoadingTerrain = true;
/*  553: 665 */       this.gameController.displayGuiScreen(null);
/*  554:     */     }
/*  555:     */   }
/*  556:     */   
/*  557:     */   public void handleMultiBlockChange(S22PacketMultiBlockChange p_147287_1_)
/*  558:     */   {
/*  559: 676 */     int var2 = p_147287_1_.func_148920_c().chunkXPos * 16;
/*  560: 677 */     int var3 = p_147287_1_.func_148920_c().chunkZPos * 16;
/*  561: 679 */     if (p_147287_1_.func_148921_d() != null)
/*  562:     */     {
/*  563: 681 */       DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(p_147287_1_.func_148921_d()));
/*  564:     */       try
/*  565:     */       {
/*  566: 685 */         for (int var5 = 0; var5 < p_147287_1_.func_148922_e(); var5++)
/*  567:     */         {
/*  568: 687 */           short var6 = var4.readShort();
/*  569: 688 */           short var7 = var4.readShort();
/*  570: 689 */           int var8 = var7 >> 4 & 0xFFF;
/*  571: 690 */           int var9 = var7 & 0xF;
/*  572: 691 */           int var10 = var6 >> 12 & 0xF;
/*  573: 692 */           int var11 = var6 >> 8 & 0xF;
/*  574: 693 */           int var12 = var6 & 0xFF;
/*  575: 694 */           this.clientWorldController.func_147492_c(var10 + var2, var12, var11 + var3, Block.getBlockById(var8), var9);
/*  576:     */         }
/*  577:     */       }
/*  578:     */       catch (IOException localIOException) {}
/*  579:     */     }
/*  580:     */   }
/*  581:     */   
/*  582:     */   public void handleChunkData(S21PacketChunkData p_147263_1_)
/*  583:     */   {
/*  584: 709 */     if (p_147263_1_.func_149274_i())
/*  585:     */     {
/*  586: 711 */       if (p_147263_1_.func_149276_g() == 0)
/*  587:     */       {
/*  588: 713 */         this.clientWorldController.doPreChunk(p_147263_1_.func_149273_e(), p_147263_1_.func_149271_f(), false);
/*  589: 714 */         return;
/*  590:     */       }
/*  591: 717 */       this.clientWorldController.doPreChunk(p_147263_1_.func_149273_e(), p_147263_1_.func_149271_f(), true);
/*  592:     */     }
/*  593: 720 */     this.clientWorldController.invalidateBlockReceiveRegion(p_147263_1_.func_149273_e() << 4, 0, p_147263_1_.func_149271_f() << 4, (p_147263_1_.func_149273_e() << 4) + 15, 256, (p_147263_1_.func_149271_f() << 4) + 15);
/*  594: 721 */     Chunk var2 = this.clientWorldController.getChunkFromChunkCoords(p_147263_1_.func_149273_e(), p_147263_1_.func_149271_f());
/*  595: 722 */     var2.fillChunk(p_147263_1_.func_149272_d(), p_147263_1_.func_149276_g(), p_147263_1_.func_149270_h(), p_147263_1_.func_149274_i());
/*  596: 723 */     this.clientWorldController.markBlockRangeForRenderUpdate(p_147263_1_.func_149273_e() << 4, 0, p_147263_1_.func_149271_f() << 4, (p_147263_1_.func_149273_e() << 4) + 15, 256, (p_147263_1_.func_149271_f() << 4) + 15);
/*  597: 725 */     if ((!p_147263_1_.func_149274_i()) || (!(this.clientWorldController.provider instanceof WorldProviderSurface))) {
/*  598: 727 */       var2.resetRelightChecks();
/*  599:     */     }
/*  600:     */   }
/*  601:     */   
/*  602:     */   public void handleBlockChange(S23PacketBlockChange p_147234_1_)
/*  603:     */   {
/*  604: 736 */     this.clientWorldController.func_147492_c(p_147234_1_.func_148879_d(), p_147234_1_.func_148878_e(), p_147234_1_.func_148877_f(), p_147234_1_.func_148880_c(), p_147234_1_.func_148881_g());
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void handleDisconnect(S40PacketDisconnect p_147253_1_)
/*  608:     */   {
/*  609: 744 */     this.netManager.closeChannel(p_147253_1_.func_149165_c());
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void onDisconnect(IChatComponent p_147231_1_)
/*  613:     */   {
/*  614: 752 */     this.gameController.loadWorld(null);
/*  615: 754 */     if (this.guiScreenServer != null) {
/*  616: 756 */       this.gameController.displayGuiScreen(new GuiScreenDisconnectedOnline(this.guiScreenServer, "disconnect.lost", p_147231_1_));
/*  617:     */     } else {
/*  618: 760 */       this.gameController.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new NodusGuiMainMenu()), "disconnect.lost", p_147231_1_));
/*  619:     */     }
/*  620:     */   }
/*  621:     */   
/*  622:     */   public void addToSendQueue(Packet p_147297_1_)
/*  623:     */   {
/*  624: 766 */     this.netManager.scheduleOutboundPacket(p_147297_1_, new GenericFutureListener[0]);
/*  625:     */   }
/*  626:     */   
/*  627:     */   public void handleCollectItem(S0DPacketCollectItem p_147246_1_)
/*  628:     */   {
/*  629: 771 */     Entity var2 = this.clientWorldController.getEntityByID(p_147246_1_.func_149354_c());
/*  630: 772 */     Object var3 = (EntityLivingBase)this.clientWorldController.getEntityByID(p_147246_1_.func_149353_d());
/*  631: 774 */     if (var3 == null) {
/*  632: 776 */       var3 = this.gameController.thePlayer;
/*  633:     */     }
/*  634: 779 */     if (var2 != null)
/*  635:     */     {
/*  636: 781 */       if ((var2 instanceof EntityXPOrb)) {
/*  637: 783 */         this.clientWorldController.playSoundAtEntity(var2, "random.orb", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  638:     */       } else {
/*  639: 787 */         this.clientWorldController.playSoundAtEntity(var2, "random.pop", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  640:     */       }
/*  641: 790 */       this.gameController.effectRenderer.addEffect(new EntityPickupFX(this.gameController.theWorld, var2, (Entity)var3, -0.5F));
/*  642: 791 */       this.clientWorldController.removeEntityFromWorld(p_147246_1_.func_149354_c());
/*  643:     */     }
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void handleChat(S02PacketChat p_147251_1_)
/*  647:     */   {
/*  648: 800 */     this.gameController.ingameGUI.getChatGUI().func_146227_a(p_147251_1_.func_148915_c());
/*  649:     */   }
/*  650:     */   
/*  651:     */   public void handleAnimation(S0BPacketAnimation p_147279_1_)
/*  652:     */   {
/*  653: 809 */     Entity var2 = this.clientWorldController.getEntityByID(p_147279_1_.func_148978_c());
/*  654: 811 */     if (var2 != null) {
/*  655: 813 */       if (p_147279_1_.func_148977_d() == 0)
/*  656:     */       {
/*  657: 815 */         EntityLivingBase var3 = (EntityLivingBase)var2;
/*  658: 816 */         var3.swingItem();
/*  659:     */       }
/*  660: 818 */       else if (p_147279_1_.func_148977_d() == 1)
/*  661:     */       {
/*  662: 820 */         var2.performHurtAnimation();
/*  663:     */       }
/*  664: 822 */       else if (p_147279_1_.func_148977_d() == 2)
/*  665:     */       {
/*  666: 824 */         EntityPlayer var4 = (EntityPlayer)var2;
/*  667: 825 */         var4.wakeUpPlayer(false, false, false);
/*  668:     */       }
/*  669: 827 */       else if (p_147279_1_.func_148977_d() == 4)
/*  670:     */       {
/*  671: 829 */         this.gameController.effectRenderer.addEffect(new EntityCrit2FX(this.gameController.theWorld, var2));
/*  672:     */       }
/*  673: 831 */       else if (p_147279_1_.func_148977_d() == 5)
/*  674:     */       {
/*  675: 833 */         EntityCrit2FX var5 = new EntityCrit2FX(this.gameController.theWorld, var2, "magicCrit");
/*  676: 834 */         this.gameController.effectRenderer.addEffect(var5);
/*  677:     */       }
/*  678:     */     }
/*  679:     */   }
/*  680:     */   
/*  681:     */   public void handleUseBed(S0APacketUseBed p_147278_1_)
/*  682:     */   {
/*  683: 845 */     p_147278_1_.func_149091_a(this.clientWorldController).sleepInBedAt(p_147278_1_.func_149092_c(), p_147278_1_.func_149090_d(), p_147278_1_.func_149089_e());
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void handleSpawnMob(S0FPacketSpawnMob p_147281_1_)
/*  687:     */   {
/*  688: 854 */     double var2 = p_147281_1_.func_149023_f() / 32.0D;
/*  689: 855 */     double var4 = p_147281_1_.func_149034_g() / 32.0D;
/*  690: 856 */     double var6 = p_147281_1_.func_149029_h() / 32.0D;
/*  691: 857 */     float var8 = p_147281_1_.func_149028_l() * 360 / 256.0F;
/*  692: 858 */     float var9 = p_147281_1_.func_149030_m() * 360 / 256.0F;
/*  693: 859 */     EntityLivingBase var10 = (EntityLivingBase)EntityList.createEntityByID(p_147281_1_.func_149025_e(), this.gameController.theWorld);
/*  694: 860 */     var10.serverPosX = p_147281_1_.func_149023_f();
/*  695: 861 */     var10.serverPosY = p_147281_1_.func_149034_g();
/*  696: 862 */     var10.serverPosZ = p_147281_1_.func_149029_h();
/*  697: 863 */     var10.rotationYawHead = (p_147281_1_.func_149032_n() * 360 / 256.0F);
/*  698: 864 */     Entity[] var11 = var10.getParts();
/*  699: 866 */     if (var11 != null)
/*  700:     */     {
/*  701: 868 */       int var12 = p_147281_1_.func_149024_d() - var10.getEntityId();
/*  702: 870 */       for (int var13 = 0; var13 < var11.length; var13++) {
/*  703: 872 */         var11[var13].setEntityId(var11[var13].getEntityId() + var12);
/*  704:     */       }
/*  705:     */     }
/*  706: 876 */     var10.setEntityId(p_147281_1_.func_149024_d());
/*  707: 877 */     var10.setPositionAndRotation(var2, var4, var6, var8, var9);
/*  708: 878 */     var10.motionX = (p_147281_1_.func_149026_i() / 8000.0F);
/*  709: 879 */     var10.motionY = (p_147281_1_.func_149033_j() / 8000.0F);
/*  710: 880 */     var10.motionZ = (p_147281_1_.func_149031_k() / 8000.0F);
/*  711: 881 */     this.clientWorldController.addEntityToWorld(p_147281_1_.func_149024_d(), var10);
/*  712: 882 */     List var14 = p_147281_1_.func_149027_c();
/*  713: 884 */     if (var14 != null) {
/*  714: 886 */       var10.getDataWatcher().updateWatchedObjectsFromList(var14);
/*  715:     */     }
/*  716:     */   }
/*  717:     */   
/*  718:     */   public void handleTimeUpdate(S03PacketTimeUpdate p_147285_1_)
/*  719:     */   {
/*  720: 892 */     this.gameController.theWorld.func_82738_a(p_147285_1_.func_149366_c());
/*  721: 893 */     this.gameController.theWorld.setWorldTime(p_147285_1_.func_149365_d());
/*  722:     */   }
/*  723:     */   
/*  724:     */   public void handleSpawnPosition(S05PacketSpawnPosition p_147271_1_)
/*  725:     */   {
/*  726: 898 */     this.gameController.thePlayer.setSpawnChunk(new ChunkCoordinates(p_147271_1_.func_149360_c(), p_147271_1_.func_149359_d(), p_147271_1_.func_149358_e()), true);
/*  727: 899 */     this.gameController.theWorld.getWorldInfo().setSpawnPosition(p_147271_1_.func_149360_c(), p_147271_1_.func_149359_d(), p_147271_1_.func_149358_e());
/*  728:     */   }
/*  729:     */   
/*  730:     */   public void handleEntityAttach(S1BPacketEntityAttach p_147243_1_)
/*  731:     */   {
/*  732: 904 */     Object var2 = this.clientWorldController.getEntityByID(p_147243_1_.func_149403_d());
/*  733: 905 */     Entity var3 = this.clientWorldController.getEntityByID(p_147243_1_.func_149402_e());
/*  734: 907 */     if (p_147243_1_.func_149404_c() == 0)
/*  735:     */     {
/*  736: 909 */       boolean var4 = false;
/*  737: 911 */       if (p_147243_1_.func_149403_d() == this.gameController.thePlayer.getEntityId())
/*  738:     */       {
/*  739: 913 */         var2 = this.gameController.thePlayer;
/*  740: 915 */         if ((var3 instanceof EntityBoat)) {
/*  741: 917 */           ((EntityBoat)var3).setIsBoatEmpty(false);
/*  742:     */         }
/*  743: 920 */         var4 = (((Entity)var2).ridingEntity == null) && (var3 != null);
/*  744:     */       }
/*  745: 922 */       else if ((var3 instanceof EntityBoat))
/*  746:     */       {
/*  747: 924 */         ((EntityBoat)var3).setIsBoatEmpty(true);
/*  748:     */       }
/*  749: 927 */       if (var2 == null) {
/*  750: 929 */         return;
/*  751:     */       }
/*  752: 932 */       ((Entity)var2).mountEntity(var3);
/*  753: 934 */       if (var4)
/*  754:     */       {
/*  755: 936 */         GameSettings var5 = this.gameController.gameSettings;
/*  756: 937 */         this.gameController.ingameGUI.func_110326_a(I18n.format("mount.onboard", new Object[] { GameSettings.getKeyDisplayString(var5.keyBindSneak.getKeyCode()) }), false);
/*  757:     */       }
/*  758:     */     }
/*  759: 940 */     else if ((p_147243_1_.func_149404_c() == 1) && (var2 != null) && ((var2 instanceof EntityLiving)))
/*  760:     */     {
/*  761: 942 */       if (var3 != null) {
/*  762: 944 */         ((EntityLiving)var2).setLeashedToEntity(var3, false);
/*  763:     */       } else {
/*  764: 948 */         ((EntityLiving)var2).clearLeashed(false, false);
/*  765:     */       }
/*  766:     */     }
/*  767:     */   }
/*  768:     */   
/*  769:     */   public void handleEntityStatus(S19PacketEntityStatus p_147236_1_)
/*  770:     */   {
/*  771: 961 */     Entity var2 = p_147236_1_.func_149161_a(this.clientWorldController);
/*  772: 963 */     if (var2 != null) {
/*  773: 965 */       var2.handleHealthUpdate(p_147236_1_.func_149160_c());
/*  774:     */     }
/*  775:     */   }
/*  776:     */   
/*  777:     */   public void handleUpdateHealth(S06PacketUpdateHealth p_147249_1_)
/*  778:     */   {
/*  779: 971 */     this.gameController.thePlayer.setPlayerSPHealth(p_147249_1_.func_149332_c());
/*  780: 972 */     this.gameController.thePlayer.getFoodStats().setFoodLevel(p_147249_1_.func_149330_d());
/*  781: 973 */     this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(p_147249_1_.func_149331_e());
/*  782:     */   }
/*  783:     */   
/*  784:     */   public void handleSetExperience(S1FPacketSetExperience p_147295_1_)
/*  785:     */   {
/*  786: 978 */     this.gameController.thePlayer.setXPStats(p_147295_1_.func_149397_c(), p_147295_1_.func_149396_d(), p_147295_1_.func_149395_e());
/*  787:     */   }
/*  788:     */   
/*  789:     */   public void handleRespawn(S07PacketRespawn p_147280_1_)
/*  790:     */   {
/*  791: 983 */     if (p_147280_1_.func_149082_c() != this.gameController.thePlayer.dimension)
/*  792:     */     {
/*  793: 985 */       this.doneLoadingTerrain = false;
/*  794: 986 */       Scoreboard var2 = this.clientWorldController.getScoreboard();
/*  795: 987 */       this.clientWorldController = new WorldClient(this, new WorldSettings(0L, p_147280_1_.func_149083_e(), false, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), p_147280_1_.func_149080_f()), p_147280_1_.func_149082_c(), p_147280_1_.func_149081_d(), this.gameController.mcProfiler);
/*  796: 988 */       this.clientWorldController.setWorldScoreboard(var2);
/*  797: 989 */       this.clientWorldController.isClient = true;
/*  798: 990 */       this.gameController.loadWorld(this.clientWorldController);
/*  799: 991 */       this.gameController.thePlayer.dimension = p_147280_1_.func_149082_c();
/*  800: 992 */       this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
/*  801:     */     }
/*  802: 995 */     this.gameController.setDimensionAndSpawnPlayer(p_147280_1_.func_149082_c());
/*  803: 996 */     this.gameController.playerController.setGameType(p_147280_1_.func_149083_e());
/*  804:     */   }
/*  805:     */   
/*  806:     */   public void handleExplosion(S27PacketExplosion p_147283_1_)
/*  807:     */   {
/*  808:1004 */     Explosion var2 = new Explosion(this.gameController.theWorld, null, p_147283_1_.func_149148_f(), p_147283_1_.func_149143_g(), p_147283_1_.func_149145_h(), p_147283_1_.func_149146_i());
/*  809:1005 */     var2.affectedBlockPositions = p_147283_1_.func_149150_j();
/*  810:1006 */     var2.doExplosionB(true);
/*  811:1007 */     this.gameController.thePlayer.motionX += p_147283_1_.func_149149_c();
/*  812:1008 */     this.gameController.thePlayer.motionY += p_147283_1_.func_149144_d();
/*  813:1009 */     this.gameController.thePlayer.motionZ += p_147283_1_.func_149147_e();
/*  814:     */   }
/*  815:     */   
/*  816:     */   public void handleOpenWindow(S2DPacketOpenWindow p_147265_1_)
/*  817:     */   {
/*  818:1018 */     EntityClientPlayerMP var2 = this.gameController.thePlayer;
/*  819:1020 */     switch (p_147265_1_.func_148899_d())
/*  820:     */     {
/*  821:     */     case 0: 
/*  822:1023 */       var2.displayGUIChest(new InventoryBasic(p_147265_1_.func_148902_e(), p_147265_1_.func_148900_g(), p_147265_1_.func_148898_f()));
/*  823:1024 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  824:1025 */       break;
/*  825:     */     case 1: 
/*  826:1028 */       var2.displayGUIWorkbench(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
/*  827:1029 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  828:1030 */       break;
/*  829:     */     case 2: 
/*  830:1033 */       TileEntityFurnace var4 = new TileEntityFurnace();
/*  831:1035 */       if (p_147265_1_.func_148900_g()) {
/*  832:1037 */         var4.func_145951_a(p_147265_1_.func_148902_e());
/*  833:     */       }
/*  834:1040 */       var2.func_146101_a(var4);
/*  835:1041 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  836:1042 */       break;
/*  837:     */     case 3: 
/*  838:1045 */       TileEntityDispenser var7 = new TileEntityDispenser();
/*  839:1047 */       if (p_147265_1_.func_148900_g()) {
/*  840:1049 */         var7.func_146018_a(p_147265_1_.func_148902_e());
/*  841:     */       }
/*  842:1052 */       var2.func_146102_a(var7);
/*  843:1053 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  844:1054 */       break;
/*  845:     */     case 4: 
/*  846:1057 */       var2.displayGUIEnchantment(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ), p_147265_1_.func_148900_g() ? p_147265_1_.func_148902_e() : null);
/*  847:1058 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  848:1059 */       break;
/*  849:     */     case 5: 
/*  850:1062 */       TileEntityBrewingStand var5 = new TileEntityBrewingStand();
/*  851:1064 */       if (p_147265_1_.func_148900_g()) {
/*  852:1066 */         var5.func_145937_a(p_147265_1_.func_148902_e());
/*  853:     */       }
/*  854:1069 */       var2.func_146098_a(var5);
/*  855:1070 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  856:1071 */       break;
/*  857:     */     case 6: 
/*  858:1074 */       var2.displayGUIMerchant(new NpcMerchant(var2), p_147265_1_.func_148900_g() ? p_147265_1_.func_148902_e() : null);
/*  859:1075 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  860:1076 */       break;
/*  861:     */     case 7: 
/*  862:1079 */       TileEntityBeacon var8 = new TileEntityBeacon();
/*  863:1080 */       var2.func_146104_a(var8);
/*  864:1082 */       if (p_147265_1_.func_148900_g()) {
/*  865:1084 */         var8.func_145999_a(p_147265_1_.func_148902_e());
/*  866:     */       }
/*  867:1087 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  868:1088 */       break;
/*  869:     */     case 8: 
/*  870:1091 */       var2.displayGUIAnvil(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
/*  871:1092 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  872:1093 */       break;
/*  873:     */     case 9: 
/*  874:1096 */       TileEntityHopper var3 = new TileEntityHopper();
/*  875:1098 */       if (p_147265_1_.func_148900_g()) {
/*  876:1100 */         var3.func_145886_a(p_147265_1_.func_148902_e());
/*  877:     */       }
/*  878:1103 */       var2.func_146093_a(var3);
/*  879:1104 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  880:1105 */       break;
/*  881:     */     case 10: 
/*  882:1108 */       TileEntityDropper var6 = new TileEntityDropper();
/*  883:1110 */       if (p_147265_1_.func_148900_g()) {
/*  884:1112 */         var6.func_146018_a(p_147265_1_.func_148902_e());
/*  885:     */       }
/*  886:1115 */       var2.func_146102_a(var6);
/*  887:1116 */       var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  888:1117 */       break;
/*  889:     */     case 11: 
/*  890:1120 */       Entity var9 = this.clientWorldController.getEntityByID(p_147265_1_.func_148897_h());
/*  891:1122 */       if ((var9 != null) && ((var9 instanceof EntityHorse)))
/*  892:     */       {
/*  893:1124 */         var2.displayGUIHorse((EntityHorse)var9, new AnimalChest(p_147265_1_.func_148902_e(), p_147265_1_.func_148900_g(), p_147265_1_.func_148898_f()));
/*  894:1125 */         var2.openContainer.windowId = p_147265_1_.func_148901_c();
/*  895:     */       }
/*  896:     */       break;
/*  897:     */     }
/*  898:     */   }
/*  899:     */   
/*  900:     */   public void handleSetSlot(S2FPacketSetSlot p_147266_1_)
/*  901:     */   {
/*  902:1135 */     EntityClientPlayerMP var2 = this.gameController.thePlayer;
/*  903:1137 */     if (p_147266_1_.func_149175_c() == -1)
/*  904:     */     {
/*  905:1139 */       var2.inventory.setItemStack(p_147266_1_.func_149174_e());
/*  906:     */     }
/*  907:     */     else
/*  908:     */     {
/*  909:1143 */       boolean var3 = false;
/*  910:1145 */       if ((this.gameController.currentScreen instanceof GuiContainerCreative))
/*  911:     */       {
/*  912:1147 */         GuiContainerCreative var4 = (GuiContainerCreative)this.gameController.currentScreen;
/*  913:1148 */         var3 = var4.func_147056_g() != CreativeTabs.tabInventory.getTabIndex();
/*  914:     */       }
/*  915:1151 */       if ((p_147266_1_.func_149175_c() == 0) && (p_147266_1_.func_149173_d() >= 36) && (p_147266_1_.func_149173_d() < 45))
/*  916:     */       {
/*  917:1153 */         ItemStack var5 = var2.inventoryContainer.getSlot(p_147266_1_.func_149173_d()).getStack();
/*  918:1155 */         if ((p_147266_1_.func_149174_e() != null) && ((var5 == null) || (var5.stackSize < p_147266_1_.func_149174_e().stackSize))) {
/*  919:1157 */           p_147266_1_.func_149174_e().animationsToGo = 5;
/*  920:     */         }
/*  921:1160 */         var2.inventoryContainer.putStackInSlot(p_147266_1_.func_149173_d(), p_147266_1_.func_149174_e());
/*  922:     */       }
/*  923:1162 */       else if ((p_147266_1_.func_149175_c() == var2.openContainer.windowId) && ((p_147266_1_.func_149175_c() != 0) || (!var3)))
/*  924:     */       {
/*  925:1164 */         var2.openContainer.putStackInSlot(p_147266_1_.func_149173_d(), p_147266_1_.func_149174_e());
/*  926:     */       }
/*  927:     */     }
/*  928:     */   }
/*  929:     */   
/*  930:     */   public void handleConfirmTransaction(S32PacketConfirmTransaction p_147239_1_)
/*  931:     */   {
/*  932:1175 */     Container var2 = null;
/*  933:1176 */     EntityClientPlayerMP var3 = this.gameController.thePlayer;
/*  934:1178 */     if (p_147239_1_.func_148889_c() == 0) {
/*  935:1180 */       var2 = var3.inventoryContainer;
/*  936:1182 */     } else if (p_147239_1_.func_148889_c() == var3.openContainer.windowId) {
/*  937:1184 */       var2 = var3.openContainer;
/*  938:     */     }
/*  939:1187 */     if ((var2 != null) && (!p_147239_1_.func_148888_e())) {
/*  940:1189 */       addToSendQueue(new C0FPacketConfirmTransaction(p_147239_1_.func_148889_c(), p_147239_1_.func_148890_d(), true));
/*  941:     */     }
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void handleWindowItems(S30PacketWindowItems p_147241_1_)
/*  945:     */   {
/*  946:1198 */     EntityClientPlayerMP var2 = this.gameController.thePlayer;
/*  947:1200 */     if (p_147241_1_.func_148911_c() == 0) {
/*  948:1202 */       var2.inventoryContainer.putStacksInSlots(p_147241_1_.func_148910_d());
/*  949:1204 */     } else if (p_147241_1_.func_148911_c() == var2.openContainer.windowId) {
/*  950:1206 */       var2.openContainer.putStacksInSlots(p_147241_1_.func_148910_d());
/*  951:     */     }
/*  952:     */   }
/*  953:     */   
/*  954:     */   public void handleSignEditorOpen(S36PacketSignEditorOpen p_147268_1_)
/*  955:     */   {
/*  956:1215 */     Object var2 = this.clientWorldController.getTileEntity(p_147268_1_.func_149129_c(), p_147268_1_.func_149128_d(), p_147268_1_.func_149127_e());
/*  957:1217 */     if (var2 == null)
/*  958:     */     {
/*  959:1219 */       var2 = new TileEntitySign();
/*  960:1220 */       ((TileEntity)var2).setWorldObj(this.clientWorldController);
/*  961:1221 */       ((TileEntity)var2).field_145851_c = p_147268_1_.func_149129_c();
/*  962:1222 */       ((TileEntity)var2).field_145848_d = p_147268_1_.func_149128_d();
/*  963:1223 */       ((TileEntity)var2).field_145849_e = p_147268_1_.func_149127_e();
/*  964:     */     }
/*  965:1226 */     this.gameController.thePlayer.func_146100_a((TileEntity)var2);
/*  966:     */   }
/*  967:     */   
/*  968:     */   public void handleUpdateSign(S33PacketUpdateSign p_147248_1_)
/*  969:     */   {
/*  970:1234 */     boolean var2 = false;
/*  971:1236 */     if (this.gameController.theWorld.blockExists(p_147248_1_.func_149346_c(), p_147248_1_.func_149345_d(), p_147248_1_.func_149344_e()))
/*  972:     */     {
/*  973:1238 */       TileEntity var3 = this.gameController.theWorld.getTileEntity(p_147248_1_.func_149346_c(), p_147248_1_.func_149345_d(), p_147248_1_.func_149344_e());
/*  974:1240 */       if ((var3 instanceof TileEntitySign))
/*  975:     */       {
/*  976:1242 */         TileEntitySign var4 = (TileEntitySign)var3;
/*  977:1244 */         if (var4.func_145914_a())
/*  978:     */         {
/*  979:1246 */           for (int var5 = 0; var5 < 4; var5++) {
/*  980:1248 */             var4.field_145915_a[var5] = p_147248_1_.func_149347_f()[var5];
/*  981:     */           }
/*  982:1251 */           var4.onInventoryChanged();
/*  983:     */         }
/*  984:1254 */         var2 = true;
/*  985:     */       }
/*  986:     */     }
/*  987:1258 */     if ((!var2) && (this.gameController.thePlayer != null)) {
/*  988:1260 */       this.gameController.thePlayer.addChatMessage(new ChatComponentText("Unable to locate sign at " + p_147248_1_.func_149346_c() + ", " + p_147248_1_.func_149345_d() + ", " + p_147248_1_.func_149344_e()));
/*  989:     */     }
/*  990:     */   }
/*  991:     */   
/*  992:     */   public void handleUpdateTileEntity(S35PacketUpdateTileEntity p_147273_1_)
/*  993:     */   {
/*  994:1270 */     if (this.gameController.theWorld.blockExists(p_147273_1_.func_148856_c(), p_147273_1_.func_148855_d(), p_147273_1_.func_148854_e()))
/*  995:     */     {
/*  996:1272 */       TileEntity var2 = this.gameController.theWorld.getTileEntity(p_147273_1_.func_148856_c(), p_147273_1_.func_148855_d(), p_147273_1_.func_148854_e());
/*  997:1274 */       if (var2 != null) {
/*  998:1276 */         if ((p_147273_1_.func_148853_f() == 1) && ((var2 instanceof TileEntityMobSpawner))) {
/*  999:1278 */           var2.readFromNBT(p_147273_1_.func_148857_g());
/* 1000:1280 */         } else if ((p_147273_1_.func_148853_f() == 2) && ((var2 instanceof TileEntityCommandBlock))) {
/* 1001:1282 */           var2.readFromNBT(p_147273_1_.func_148857_g());
/* 1002:1284 */         } else if ((p_147273_1_.func_148853_f() == 3) && ((var2 instanceof TileEntityBeacon))) {
/* 1003:1286 */           var2.readFromNBT(p_147273_1_.func_148857_g());
/* 1004:1288 */         } else if ((p_147273_1_.func_148853_f() == 4) && ((var2 instanceof TileEntitySkull))) {
/* 1005:1290 */           var2.readFromNBT(p_147273_1_.func_148857_g());
/* 1006:1292 */         } else if ((p_147273_1_.func_148853_f() == 5) && ((var2 instanceof TileEntityFlowerPot))) {
/* 1007:1294 */           var2.readFromNBT(p_147273_1_.func_148857_g());
/* 1008:     */         }
/* 1009:     */       }
/* 1010:     */     }
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   public void handleWindowProperty(S31PacketWindowProperty p_147245_1_)
/* 1014:     */   {
/* 1015:1305 */     EntityClientPlayerMP var2 = this.gameController.thePlayer;
/* 1016:1307 */     if ((var2.openContainer != null) && (var2.openContainer.windowId == p_147245_1_.func_149182_c())) {
/* 1017:1309 */       var2.openContainer.updateProgressBar(p_147245_1_.func_149181_d(), p_147245_1_.func_149180_e());
/* 1018:     */     }
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public void handleEntityEquipment(S04PacketEntityEquipment p_147242_1_)
/* 1022:     */   {
/* 1023:1315 */     Entity var2 = this.clientWorldController.getEntityByID(p_147242_1_.func_149389_d());
/* 1024:1317 */     if (var2 != null) {
/* 1025:1319 */       var2.setCurrentItemOrArmor(p_147242_1_.func_149388_e(), p_147242_1_.func_149390_c());
/* 1026:     */     }
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public void handleCloseWindow(S2EPacketCloseWindow p_147276_1_)
/* 1030:     */   {
/* 1031:1328 */     this.gameController.thePlayer.closeScreenNoPacket();
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public void handleBlockAction(S24PacketBlockAction p_147261_1_)
/* 1035:     */   {
/* 1036:1338 */     this.gameController.theWorld.func_147452_c(p_147261_1_.func_148867_d(), p_147261_1_.func_148866_e(), p_147261_1_.func_148865_f(), p_147261_1_.func_148868_c(), p_147261_1_.func_148869_g(), p_147261_1_.func_148864_h());
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public void handleBlockBreakAnim(S25PacketBlockBreakAnim p_147294_1_)
/* 1040:     */   {
/* 1041:1346 */     this.gameController.theWorld.destroyBlockInWorldPartially(p_147294_1_.func_148845_c(), p_147294_1_.func_148844_d(), p_147294_1_.func_148843_e(), p_147294_1_.func_148842_f(), p_147294_1_.func_148846_g());
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public void handleMapChunkBulk(S26PacketMapChunkBulk p_147269_1_)
/* 1045:     */   {
/* 1046:1351 */     for (int var2 = 0; var2 < p_147269_1_.func_149254_d(); var2++)
/* 1047:     */     {
/* 1048:1353 */       int var3 = p_147269_1_.func_149255_a(var2);
/* 1049:1354 */       int var4 = p_147269_1_.func_149253_b(var2);
/* 1050:1355 */       this.clientWorldController.doPreChunk(var3, var4, true);
/* 1051:1356 */       this.clientWorldController.invalidateBlockReceiveRegion(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
/* 1052:1357 */       Chunk var5 = this.clientWorldController.getChunkFromChunkCoords(var3, var4);
/* 1053:1358 */       var5.fillChunk(p_147269_1_.func_149256_c(var2), p_147269_1_.func_149252_e()[var2], p_147269_1_.func_149257_f()[var2], true);
/* 1054:1359 */       this.clientWorldController.markBlockRangeForRenderUpdate(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
/* 1055:1361 */       if (!(this.clientWorldController.provider instanceof WorldProviderSurface)) {
/* 1056:1363 */         var5.resetRelightChecks();
/* 1057:     */       }
/* 1058:     */     }
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public void handleChangeGameState(S2BPacketChangeGameState p_147252_1_)
/* 1062:     */   {
/* 1063:1370 */     EntityClientPlayerMP var2 = this.gameController.thePlayer;
/* 1064:1371 */     int var3 = p_147252_1_.func_149138_c();
/* 1065:1372 */     float var4 = p_147252_1_.func_149137_d();
/* 1066:1373 */     int var5 = MathHelper.floor_float(var4 + 0.5F);
/* 1067:1375 */     if ((var3 >= 0) && (var3 < S2BPacketChangeGameState.field_149142_a.length) && (S2BPacketChangeGameState.field_149142_a[var3] != null)) {
/* 1068:1377 */       var2.addChatComponentMessage(new ChatComponentTranslation(S2BPacketChangeGameState.field_149142_a[var3], new Object[0]));
/* 1069:     */     }
/* 1070:1380 */     if (var3 == 1)
/* 1071:     */     {
/* 1072:1382 */       this.clientWorldController.getWorldInfo().setRaining(true);
/* 1073:1383 */       this.clientWorldController.setRainStrength(0.0F);
/* 1074:     */     }
/* 1075:1385 */     else if (var3 == 2)
/* 1076:     */     {
/* 1077:1387 */       this.clientWorldController.getWorldInfo().setRaining(false);
/* 1078:1388 */       this.clientWorldController.setRainStrength(1.0F);
/* 1079:     */     }
/* 1080:1390 */     else if (var3 == 3)
/* 1081:     */     {
/* 1082:1392 */       this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(var5));
/* 1083:     */     }
/* 1084:1394 */     else if (var3 == 4)
/* 1085:     */     {
/* 1086:1396 */       this.gameController.displayGuiScreen(new GuiWinGame());
/* 1087:     */     }
/* 1088:1398 */     else if (var3 == 5)
/* 1089:     */     {
/* 1090:1400 */       GameSettings var6 = this.gameController.gameSettings;
/* 1091:1402 */       if (var4 == 0.0F) {
/* 1092:1404 */         this.gameController.displayGuiScreen(new GuiScreenDemo());
/* 1093:1406 */       } else if (var4 == 101.0F) {
/* 1094:1408 */         this.gameController.ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("demo.help.movement", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindRight.getKeyCode()) }));
/* 1095:1410 */       } else if (var4 == 102.0F) {
/* 1096:1412 */         this.gameController.ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindJump.getKeyCode()) }));
/* 1097:1414 */       } else if (var4 == 103.0F) {
/* 1098:1416 */         this.gameController.ingameGUI.getChatGUI().func_146227_a(new ChatComponentTranslation("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindInventory.getKeyCode()) }));
/* 1099:     */       }
/* 1100:     */     }
/* 1101:1419 */     else if (var3 == 6)
/* 1102:     */     {
/* 1103:1421 */       this.clientWorldController.playSound(var2.posX, var2.posY + var2.getEyeHeight(), var2.posZ, "random.successful_hit", 0.18F, 0.45F, false);
/* 1104:     */     }
/* 1105:1423 */     else if (var3 == 7)
/* 1106:     */     {
/* 1107:1425 */       this.clientWorldController.setRainStrength(var4);
/* 1108:     */     }
/* 1109:1427 */     else if (var3 == 8)
/* 1110:     */     {
/* 1111:1429 */       this.clientWorldController.setThunderStrength(var4);
/* 1112:     */     }
/* 1113:     */   }
/* 1114:     */   
/* 1115:     */   public void handleMaps(S34PacketMaps p_147264_1_)
/* 1116:     */   {
/* 1117:1439 */     MapData var2 = ItemMap.func_150912_a(p_147264_1_.func_149188_c(), this.gameController.theWorld);
/* 1118:1440 */     var2.updateMPMapData(p_147264_1_.func_149187_d());
/* 1119:1441 */     this.gameController.entityRenderer.getMapItemRenderer().func_148246_a(var2);
/* 1120:     */   }
/* 1121:     */   
/* 1122:     */   public void handleEffect(S28PacketEffect p_147277_1_)
/* 1123:     */   {
/* 1124:1446 */     if (p_147277_1_.func_149244_c()) {
/* 1125:1448 */       this.gameController.theWorld.playBroadcastSound(p_147277_1_.func_149242_d(), p_147277_1_.func_149240_f(), p_147277_1_.func_149243_g(), p_147277_1_.func_149239_h(), p_147277_1_.func_149241_e());
/* 1126:     */     } else {
/* 1127:1452 */       this.gameController.theWorld.playAuxSFX(p_147277_1_.func_149242_d(), p_147277_1_.func_149240_f(), p_147277_1_.func_149243_g(), p_147277_1_.func_149239_h(), p_147277_1_.func_149241_e());
/* 1128:     */     }
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public void handleStatistics(S37PacketStatistics p_147293_1_)
/* 1132:     */   {
/* 1133:1461 */     boolean var2 = false;
/* 1134:     */     StatBase var5;
/* 1135:     */     int var6;
/* 1136:1465 */     for (Iterator var3 = p_147293_1_.func_148974_c().entrySet().iterator(); var3.hasNext(); this.gameController.thePlayer.func_146107_m().func_150873_a(this.gameController.thePlayer, var5, var6))
/* 1137:     */     {
/* 1138:1467 */       Map.Entry var4 = (Map.Entry)var3.next();
/* 1139:1468 */       var5 = (StatBase)var4.getKey();
/* 1140:1469 */       var6 = ((Integer)var4.getValue()).intValue();
/* 1141:1471 */       if ((var5.isAchievement()) && (var6 > 0))
/* 1142:     */       {
/* 1143:1473 */         if ((this.field_147308_k) && (this.gameController.thePlayer.func_146107_m().writeStat(var5) == 0))
/* 1144:     */         {
/* 1145:1475 */           this.gameController.guiAchievement.func_146256_a((Achievement)var5);
/* 1146:1477 */           if (var5 == AchievementList.openInventory)
/* 1147:     */           {
/* 1148:1479 */             this.gameController.gameSettings.showInventoryAchievementHint = false;
/* 1149:1480 */             this.gameController.gameSettings.saveOptions();
/* 1150:     */           }
/* 1151:     */         }
/* 1152:1484 */         var2 = true;
/* 1153:     */       }
/* 1154:     */     }
/* 1155:1488 */     if ((!this.field_147308_k) && (!var2) && (this.gameController.gameSettings.showInventoryAchievementHint)) {
/* 1156:1490 */       this.gameController.guiAchievement.func_146255_b(AchievementList.openInventory);
/* 1157:     */     }
/* 1158:1493 */     this.field_147308_k = true;
/* 1159:1495 */     if ((this.gameController.currentScreen instanceof IProgressMeter)) {
/* 1160:1497 */       ((IProgressMeter)this.gameController.currentScreen).func_146509_g();
/* 1161:     */     }
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public void handleEntityEffect(S1DPacketEntityEffect p_147260_1_)
/* 1165:     */   {
/* 1166:1503 */     Entity var2 = this.clientWorldController.getEntityByID(p_147260_1_.func_149426_d());
/* 1167:1505 */     if ((var2 instanceof EntityLivingBase))
/* 1168:     */     {
/* 1169:1507 */       PotionEffect var3 = new PotionEffect(p_147260_1_.func_149427_e(), p_147260_1_.func_149425_g(), p_147260_1_.func_149428_f());
/* 1170:1508 */       var3.setPotionDurationMax(p_147260_1_.func_149429_c());
/* 1171:1509 */       ((EntityLivingBase)var2).addPotionEffect(var3);
/* 1172:     */     }
/* 1173:     */   }
/* 1174:     */   
/* 1175:     */   public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect p_147262_1_)
/* 1176:     */   {
/* 1177:1515 */     Entity var2 = this.clientWorldController.getEntityByID(p_147262_1_.func_149076_c());
/* 1178:1517 */     if ((var2 instanceof EntityLivingBase)) {
/* 1179:1519 */       ((EntityLivingBase)var2).removePotionEffectClient(p_147262_1_.func_149075_d());
/* 1180:     */     }
/* 1181:     */   }
/* 1182:     */   
/* 1183:     */   public void handlePlayerListItem(S38PacketPlayerListItem p_147256_1_)
/* 1184:     */   {
/* 1185:1525 */     GuiPlayerInfo var2 = (GuiPlayerInfo)this.playerInfoMap.get(p_147256_1_.func_149122_c());
/* 1186:1527 */     if ((var2 == null) && (p_147256_1_.func_149121_d()))
/* 1187:     */     {
/* 1188:1529 */       var2 = new GuiPlayerInfo(p_147256_1_.func_149122_c());
/* 1189:1530 */       this.playerInfoMap.put(p_147256_1_.func_149122_c(), var2);
/* 1190:1531 */       this.playerInfoList.add(var2);
/* 1191:     */     }
/* 1192:1534 */     if ((var2 != null) && (!p_147256_1_.func_149121_d()))
/* 1193:     */     {
/* 1194:1536 */       this.playerInfoMap.remove(p_147256_1_.func_149122_c());
/* 1195:1537 */       this.playerInfoList.remove(var2);
/* 1196:     */     }
/* 1197:1540 */     if ((var2 != null) && (p_147256_1_.func_149121_d())) {
/* 1198:1542 */       var2.responseTime = p_147256_1_.func_149120_e();
/* 1199:     */     }
/* 1200:     */   }
/* 1201:     */   
/* 1202:     */   public void handleKeepAlive(S00PacketKeepAlive p_147272_1_)
/* 1203:     */   {
/* 1204:1548 */     addToSendQueue(new C00PacketKeepAlive(p_147272_1_.func_149134_c()));
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/* 1208:     */   {
/* 1209:1557 */     throw new IllegalStateException("Unexpected protocol change!");
/* 1210:     */   }
/* 1211:     */   
/* 1212:     */   public void handlePlayerAbilities(S39PacketPlayerAbilities p_147270_1_)
/* 1213:     */   {
/* 1214:1562 */     EntityClientPlayerMP var2 = this.gameController.thePlayer;
/* 1215:1563 */     var2.capabilities.isFlying = p_147270_1_.func_149106_d();
/* 1216:1564 */     var2.capabilities.isCreativeMode = p_147270_1_.func_149103_f();
/* 1217:1565 */     var2.capabilities.disableDamage = p_147270_1_.func_149112_c();
/* 1218:1566 */     var2.capabilities.allowFlying = p_147270_1_.func_149105_e();
/* 1219:1567 */     var2.capabilities.setFlySpeed(p_147270_1_.func_149101_g());
/* 1220:1568 */     var2.capabilities.setPlayerWalkSpeed(p_147270_1_.func_149107_h());
/* 1221:     */   }
/* 1222:     */   
/* 1223:     */   public void handleTabComplete(S3APacketTabComplete p_147274_1_)
/* 1224:     */   {
/* 1225:1576 */     String[] var2 = p_147274_1_.func_149630_c();
/* 1226:1578 */     if ((this.gameController.currentScreen instanceof GuiChat))
/* 1227:     */     {
/* 1228:1580 */       GuiChat var3 = (GuiChat)this.gameController.currentScreen;
/* 1229:1581 */       var3.func_146406_a(var2);
/* 1230:     */     }
/* 1231:     */   }
/* 1232:     */   
/* 1233:     */   public void handleSoundEffect(S29PacketSoundEffect p_147255_1_)
/* 1234:     */   {
/* 1235:1587 */     this.gameController.theWorld.playSound(p_147255_1_.func_149207_d(), p_147255_1_.func_149211_e(), p_147255_1_.func_149210_f(), p_147255_1_.func_149212_c(), p_147255_1_.func_149208_g(), p_147255_1_.func_149209_h(), false);
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   public void handleCustomPayload(S3FPacketCustomPayload p_147240_1_)
/* 1239:     */   {
/* 1240:1598 */     if ("MC|TrList".equals(p_147240_1_.func_149169_c()))
/* 1241:     */     {
/* 1242:1600 */       ByteBuf var2 = Unpooled.wrappedBuffer(p_147240_1_.func_149168_d());
/* 1243:     */       try
/* 1244:     */       {
/* 1245:1604 */         int var3 = var2.readInt();
/* 1246:1605 */         GuiScreen var4 = this.gameController.currentScreen;
/* 1247:1607 */         if ((var4 == null) || (!(var4 instanceof GuiMerchant)) || (var3 != this.gameController.thePlayer.openContainer.windowId)) {
/* 1248:     */           return;
/* 1249:     */         }
/* 1250:1609 */         IMerchant var5 = ((GuiMerchant)var4).func_147035_g();
/* 1251:1610 */         MerchantRecipeList var6 = MerchantRecipeList.func_151390_b(new PacketBuffer(var2));
/* 1252:1611 */         var5.setRecipes(var6);
/* 1253:     */       }
/* 1254:     */       catch (IOException var7)
/* 1255:     */       {
/* 1256:1616 */         logger.error("Couldn't load trade info", var7);
/* 1257:     */       }
/* 1258:     */     }
/* 1259:1619 */     else if ("MC|Brand".equals(p_147240_1_.func_149169_c()))
/* 1260:     */     {
/* 1261:1621 */       this.gameController.thePlayer.func_142020_c(new String(p_147240_1_.func_149168_d(), Charsets.UTF_8));
/* 1262:     */     }
/* 1263:1623 */     else if ("MC|RPack".equals(p_147240_1_.func_149169_c()))
/* 1264:     */     {
/* 1265:1625 */       final String var8 = new String(p_147240_1_.func_149168_d(), Charsets.UTF_8);
/* 1266:1627 */       if (this.gameController.gameSettings.serverTextures) {
/* 1267:1629 */         if ((this.gameController.func_147104_D() != null) && (this.gameController.func_147104_D().func_147408_b())) {
/* 1268:1631 */           this.gameController.getResourcePackRepository().func_148526_a(var8);
/* 1269:1633 */         } else if ((this.gameController.func_147104_D() == null) || (this.gameController.func_147104_D().func_147410_c())) {
/* 1270:1635 */           this.gameController.displayGuiScreen(new GuiYesNo(new GuiScreen()
/* 1271:     */           {
/* 1272:     */             private static final String __OBFID = "CL_00000879";
/* 1273:     */             
/* 1274:     */             public void confirmClicked(boolean par1, int par2)
/* 1275:     */             {
/* 1276:1640 */               this.mc = Minecraft.getMinecraft();
/* 1277:1642 */               if (this.mc.func_147104_D() != null)
/* 1278:     */               {
/* 1279:1644 */                 this.mc.func_147104_D().setAcceptsTextures(par1);
/* 1280:1645 */                 ServerList.func_147414_b(this.mc.func_147104_D());
/* 1281:     */               }
/* 1282:1648 */               if (par1) {
/* 1283:1650 */                 this.mc.getResourcePackRepository().func_148526_a(var8);
/* 1284:     */               }
/* 1285:1653 */               this.mc.displayGuiScreen(null);
/* 1286:     */             }
/* 1287:1655 */           }, I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
/* 1288:     */         }
/* 1289:     */       }
/* 1290:     */     }
/* 1291:     */   }
/* 1292:     */   
/* 1293:     */   public void handleScoreboardObjective(S3BPacketScoreboardObjective p_147291_1_)
/* 1294:     */   {
/* 1295:1666 */     Scoreboard var2 = this.clientWorldController.getScoreboard();
/* 1296:1669 */     if (p_147291_1_.func_149338_e() == 0)
/* 1297:     */     {
/* 1298:1671 */       ScoreObjective var3 = var2.addScoreObjective(p_147291_1_.func_149339_c(), IScoreObjectiveCriteria.field_96641_b);
/* 1299:1672 */       var3.setDisplayName(p_147291_1_.func_149337_d());
/* 1300:     */     }
/* 1301:     */     else
/* 1302:     */     {
/* 1303:1676 */       ScoreObjective var3 = var2.getObjective(p_147291_1_.func_149339_c());
/* 1304:1678 */       if (p_147291_1_.func_149338_e() == 1) {
/* 1305:1680 */         var2.func_96519_k(var3);
/* 1306:1682 */       } else if (p_147291_1_.func_149338_e() == 2) {
/* 1307:1684 */         var3.setDisplayName(p_147291_1_.func_149337_d());
/* 1308:     */       }
/* 1309:     */     }
/* 1310:     */   }
/* 1311:     */   
/* 1312:     */   public void handleUpdateScore(S3CPacketUpdateScore p_147250_1_)
/* 1313:     */   {
/* 1314:1694 */     Scoreboard var2 = this.clientWorldController.getScoreboard();
/* 1315:1695 */     ScoreObjective var3 = var2.getObjective(p_147250_1_.func_149321_d());
/* 1316:1697 */     if (p_147250_1_.func_149322_f() == 0)
/* 1317:     */     {
/* 1318:1699 */       Score var4 = var2.func_96529_a(p_147250_1_.func_149324_c(), var3);
/* 1319:1700 */       var4.func_96647_c(p_147250_1_.func_149323_e());
/* 1320:     */     }
/* 1321:1702 */     else if (p_147250_1_.func_149322_f() == 1)
/* 1322:     */     {
/* 1323:1704 */       var2.func_96515_c(p_147250_1_.func_149324_c());
/* 1324:     */     }
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public void handleDisplayScoreboard(S3DPacketDisplayScoreboard p_147254_1_)
/* 1328:     */   {
/* 1329:1714 */     Scoreboard var2 = this.clientWorldController.getScoreboard();
/* 1330:1716 */     if (p_147254_1_.func_149370_d().length() == 0)
/* 1331:     */     {
/* 1332:1718 */       var2.func_96530_a(p_147254_1_.func_149371_c(), null);
/* 1333:     */     }
/* 1334:     */     else
/* 1335:     */     {
/* 1336:1722 */       ScoreObjective var3 = var2.getObjective(p_147254_1_.func_149370_d());
/* 1337:1723 */       var2.func_96530_a(p_147254_1_.func_149371_c(), var3);
/* 1338:     */     }
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   public void handleTeams(S3EPacketTeams p_147247_1_)
/* 1342:     */   {
/* 1343:1733 */     Scoreboard var2 = this.clientWorldController.getScoreboard();
/* 1344:     */     ScorePlayerTeam var3;
/* 1345:     */     ScorePlayerTeam var3;
/* 1346:1736 */     if (p_147247_1_.func_149307_h() == 0) {
/* 1347:1738 */       var3 = var2.createTeam(p_147247_1_.func_149312_c());
/* 1348:     */     } else {
/* 1349:1742 */       var3 = var2.getTeam(p_147247_1_.func_149312_c());
/* 1350:     */     }
/* 1351:1745 */     if ((p_147247_1_.func_149307_h() == 0) || (p_147247_1_.func_149307_h() == 2))
/* 1352:     */     {
/* 1353:1747 */       var3.setTeamName(p_147247_1_.func_149306_d());
/* 1354:1748 */       var3.setNamePrefix(p_147247_1_.func_149311_e());
/* 1355:1749 */       var3.setNameSuffix(p_147247_1_.func_149309_f());
/* 1356:1750 */       var3.func_98298_a(p_147247_1_.func_149308_i());
/* 1357:     */     }
/* 1358:1756 */     if ((p_147247_1_.func_149307_h() == 0) || (p_147247_1_.func_149307_h() == 3))
/* 1359:     */     {
/* 1360:1758 */       Iterator var4 = p_147247_1_.func_149310_g().iterator();
/* 1361:1760 */       while (var4.hasNext())
/* 1362:     */       {
/* 1363:1762 */         String var5 = (String)var4.next();
/* 1364:1763 */         var2.func_151392_a(var5, p_147247_1_.func_149312_c());
/* 1365:     */       }
/* 1366:     */     }
/* 1367:1767 */     if (p_147247_1_.func_149307_h() == 4)
/* 1368:     */     {
/* 1369:1769 */       Iterator var4 = p_147247_1_.func_149310_g().iterator();
/* 1370:1771 */       while (var4.hasNext())
/* 1371:     */       {
/* 1372:1773 */         String var5 = (String)var4.next();
/* 1373:1774 */         var2.removePlayerFromTeam(var5, var3);
/* 1374:     */       }
/* 1375:     */     }
/* 1376:1778 */     if (p_147247_1_.func_149307_h() == 1) {
/* 1377:1780 */       var2.removeTeam(var3);
/* 1378:     */     }
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   public void handleParticles(S2APacketParticles p_147289_1_)
/* 1382:     */   {
/* 1383:1790 */     if (p_147289_1_.func_149222_k() == 0)
/* 1384:     */     {
/* 1385:1792 */       double var2 = p_147289_1_.func_149227_j() * p_147289_1_.func_149221_g();
/* 1386:1793 */       double var4 = p_147289_1_.func_149227_j() * p_147289_1_.func_149224_h();
/* 1387:1794 */       double var6 = p_147289_1_.func_149227_j() * p_147289_1_.func_149223_i();
/* 1388:1795 */       this.clientWorldController.spawnParticle(p_147289_1_.func_149228_c(), p_147289_1_.func_149220_d(), p_147289_1_.func_149226_e(), p_147289_1_.func_149225_f(), var2, var4, var6);
/* 1389:     */     }
/* 1390:     */     else
/* 1391:     */     {
/* 1392:1799 */       for (int var15 = 0; var15 < p_147289_1_.func_149222_k(); var15++)
/* 1393:     */       {
/* 1394:1801 */         double var3 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149221_g();
/* 1395:1802 */         double var5 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149224_h();
/* 1396:1803 */         double var7 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149223_i();
/* 1397:1804 */         double var9 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149227_j();
/* 1398:1805 */         double var11 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149227_j();
/* 1399:1806 */         double var13 = this.avRandomizer.nextGaussian() * p_147289_1_.func_149227_j();
/* 1400:1807 */         this.clientWorldController.spawnParticle(p_147289_1_.func_149228_c(), p_147289_1_.func_149220_d() + var3, p_147289_1_.func_149226_e() + var5, p_147289_1_.func_149225_f() + var7, var9, var11, var13);
/* 1401:     */       }
/* 1402:     */     }
/* 1403:     */   }
/* 1404:     */   
/* 1405:     */   public void handleEntityProperties(S20PacketEntityProperties p_147290_1_)
/* 1406:     */   {
/* 1407:1819 */     Entity var2 = this.clientWorldController.getEntityByID(p_147290_1_.func_149442_c());
/* 1408:1821 */     if (var2 != null)
/* 1409:     */     {
/* 1410:1823 */       if (!(var2 instanceof EntityLivingBase)) {
/* 1411:1825 */         throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + var2 + ")");
/* 1412:     */       }
/* 1413:1829 */       BaseAttributeMap var3 = ((EntityLivingBase)var2).getAttributeMap();
/* 1414:1830 */       Iterator var4 = p_147290_1_.func_149441_d().iterator();
/* 1415:     */       Iterator var7;
/* 1416:1832 */       for (; var4.hasNext(); var7.hasNext())
/* 1417:     */       {
/* 1418:1834 */         S20PacketEntityProperties.Snapshot var5 = (S20PacketEntityProperties.Snapshot)var4.next();
/* 1419:1835 */         IAttributeInstance var6 = var3.getAttributeInstanceByName(var5.func_151409_a());
/* 1420:1837 */         if (var6 == null) {
/* 1421:1839 */           var6 = var3.registerAttribute(new RangedAttribute(var5.func_151409_a(), 0.0D, 2.225073858507201E-308D, 1.7976931348623157E+308D));
/* 1422:     */         }
/* 1423:1842 */         var6.setBaseValue(var5.func_151410_b());
/* 1424:1843 */         var6.removeAllModifiers();
/* 1425:1844 */         var7 = var5.func_151408_c().iterator();
/* 1426:     */         
/* 1427:1846 */         continue;
/* 1428:     */         
/* 1429:1848 */         AttributeModifier var8 = (AttributeModifier)var7.next();
/* 1430:1849 */         var6.applyModifier(var8);
/* 1431:     */       }
/* 1432:     */     }
/* 1433:     */   }
/* 1434:     */   
/* 1435:     */   public NetworkManager getNetworkManager()
/* 1436:     */   {
/* 1437:1861 */     return this.netManager;
/* 1438:     */   }
/* 1439:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.network.NetHandlerPlayClient
 * JD-Core Version:    0.7.0.1
 */