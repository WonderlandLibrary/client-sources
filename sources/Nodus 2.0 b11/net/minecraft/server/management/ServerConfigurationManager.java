/*    1:     */ package net.minecraft.server.management;
/*    2:     */ 
/*    3:     */ import com.google.common.base.Charsets;
/*    4:     */ import com.google.common.collect.Maps;
/*    5:     */ import com.mojang.authlib.GameProfile;
/*    6:     */ import java.io.File;
/*    7:     */ import java.net.SocketAddress;
/*    8:     */ import java.text.SimpleDateFormat;
/*    9:     */ import java.util.ArrayList;
/*   10:     */ import java.util.Collection;
/*   11:     */ import java.util.Collections;
/*   12:     */ import java.util.HashSet;
/*   13:     */ import java.util.Iterator;
/*   14:     */ import java.util.List;
/*   15:     */ import java.util.Map;
/*   16:     */ import java.util.Map.Entry;
/*   17:     */ import java.util.Set;
/*   18:     */ import net.minecraft.entity.Entity;
/*   19:     */ import net.minecraft.entity.EntityList;
/*   20:     */ import net.minecraft.entity.EntityTracker;
/*   21:     */ import net.minecraft.entity.player.EntityPlayer;
/*   22:     */ import net.minecraft.entity.player.EntityPlayerMP;
/*   23:     */ import net.minecraft.entity.player.InventoryPlayer;
/*   24:     */ import net.minecraft.nbt.NBTTagCompound;
/*   25:     */ import net.minecraft.network.NetHandlerPlayServer;
/*   26:     */ import net.minecraft.network.NetworkManager;
/*   27:     */ import net.minecraft.network.Packet;
/*   28:     */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*   29:     */ import net.minecraft.network.play.server.S02PacketChat;
/*   30:     */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*   31:     */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*   32:     */ import net.minecraft.network.play.server.S07PacketRespawn;
/*   33:     */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*   34:     */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*   35:     */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*   36:     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*   37:     */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*   38:     */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*   39:     */ import net.minecraft.network.play.server.S3EPacketTeams;
/*   40:     */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*   41:     */ import net.minecraft.potion.PotionEffect;
/*   42:     */ import net.minecraft.profiler.Profiler;
/*   43:     */ import net.minecraft.scoreboard.Score;
/*   44:     */ import net.minecraft.scoreboard.ScoreObjective;
/*   45:     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*   46:     */ import net.minecraft.scoreboard.Scoreboard;
/*   47:     */ import net.minecraft.scoreboard.ServerScoreboard;
/*   48:     */ import net.minecraft.scoreboard.Team;
/*   49:     */ import net.minecraft.server.MinecraftServer;
/*   50:     */ import net.minecraft.stats.StatList;
/*   51:     */ import net.minecraft.stats.StatisticsFile;
/*   52:     */ import net.minecraft.util.ChatComponentTranslation;
/*   53:     */ import net.minecraft.util.ChatStyle;
/*   54:     */ import net.minecraft.util.ChunkCoordinates;
/*   55:     */ import net.minecraft.util.EnumChatFormatting;
/*   56:     */ import net.minecraft.util.IChatComponent;
/*   57:     */ import net.minecraft.util.MathHelper;
/*   58:     */ import net.minecraft.world.GameRules;
/*   59:     */ import net.minecraft.world.Teleporter;
/*   60:     */ import net.minecraft.world.World;
/*   61:     */ import net.minecraft.world.WorldProvider;
/*   62:     */ import net.minecraft.world.WorldServer;
/*   63:     */ import net.minecraft.world.WorldSettings.GameType;
/*   64:     */ import net.minecraft.world.demo.DemoWorldManager;
/*   65:     */ import net.minecraft.world.gen.ChunkProviderServer;
/*   66:     */ import net.minecraft.world.storage.IPlayerFileData;
/*   67:     */ import net.minecraft.world.storage.ISaveHandler;
/*   68:     */ import net.minecraft.world.storage.WorldInfo;
/*   69:     */ import org.apache.logging.log4j.LogManager;
/*   70:     */ import org.apache.logging.log4j.Logger;
/*   71:     */ 
/*   72:     */ public abstract class ServerConfigurationManager
/*   73:     */ {
/*   74:  63 */   private static final Logger logger = ;
/*   75:  64 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
/*   76:     */   private final MinecraftServer mcServer;
/*   77:  70 */   public final List playerEntityList = new ArrayList();
/*   78:  71 */   private final BanList bannedPlayers = new BanList(new File("banned-players.txt"));
/*   79:  72 */   private final BanList bannedIPs = new BanList(new File("banned-ips.txt"));
/*   80:  75 */   private final Set ops = new HashSet();
/*   81:  78 */   private final Set whiteListedPlayers = new HashSet();
/*   82:  79 */   private final Map field_148547_k = Maps.newHashMap();
/*   83:     */   private IPlayerFileData playerNBTManagerObj;
/*   84:     */   private boolean whiteListEnforced;
/*   85:     */   protected int maxPlayers;
/*   86:     */   protected int viewDistance;
/*   87:     */   private WorldSettings.GameType gameType;
/*   88:     */   private boolean commandsAllowedForAll;
/*   89:     */   private int playerPingIndex;
/*   90:     */   private static final String __OBFID = "CL_00001423";
/*   91:     */   
/*   92:     */   public ServerConfigurationManager(MinecraftServer par1MinecraftServer)
/*   93:     */   {
/*   94: 105 */     this.mcServer = par1MinecraftServer;
/*   95: 106 */     this.bannedPlayers.setListActive(false);
/*   96: 107 */     this.bannedIPs.setListActive(false);
/*   97: 108 */     this.maxPlayers = 8;
/*   98:     */   }
/*   99:     */   
/*  100:     */   public void initializeConnectionToPlayer(NetworkManager par1INetworkManager, EntityPlayerMP par2EntityPlayerMP)
/*  101:     */   {
/*  102: 113 */     NBTTagCompound var3 = readPlayerDataFromFile(par2EntityPlayerMP);
/*  103: 114 */     par2EntityPlayerMP.setWorld(this.mcServer.worldServerForDimension(par2EntityPlayerMP.dimension));
/*  104: 115 */     par2EntityPlayerMP.theItemInWorldManager.setWorld((WorldServer)par2EntityPlayerMP.worldObj);
/*  105: 116 */     String var4 = "local";
/*  106: 118 */     if (par1INetworkManager.getSocketAddress() != null) {
/*  107: 120 */       var4 = par1INetworkManager.getSocketAddress().toString();
/*  108:     */     }
/*  109: 123 */     logger.info(par2EntityPlayerMP.getCommandSenderName() + "[" + var4 + "] logged in with entity id " + par2EntityPlayerMP.getEntityId() + " at (" + par2EntityPlayerMP.posX + ", " + par2EntityPlayerMP.posY + ", " + par2EntityPlayerMP.posZ + ")");
/*  110: 124 */     WorldServer var5 = this.mcServer.worldServerForDimension(par2EntityPlayerMP.dimension);
/*  111: 125 */     ChunkCoordinates var6 = var5.getSpawnPoint();
/*  112: 126 */     func_72381_a(par2EntityPlayerMP, null, var5);
/*  113: 127 */     NetHandlerPlayServer var7 = new NetHandlerPlayServer(this.mcServer, par1INetworkManager, par2EntityPlayerMP);
/*  114: 128 */     var7.sendPacket(new S01PacketJoinGame(par2EntityPlayerMP.getEntityId(), par2EntityPlayerMP.theItemInWorldManager.getGameType(), var5.getWorldInfo().isHardcoreModeEnabled(), var5.provider.dimensionId, var5.difficultySetting, getMaxPlayers(), var5.getWorldInfo().getTerrainType()));
/*  115: 129 */     var7.sendPacket(new S3FPacketCustomPayload("MC|Brand", getServerInstance().getServerModName().getBytes(Charsets.UTF_8)));
/*  116: 130 */     var7.sendPacket(new S05PacketSpawnPosition(var6.posX, var6.posY, var6.posZ));
/*  117: 131 */     var7.sendPacket(new S39PacketPlayerAbilities(par2EntityPlayerMP.capabilities));
/*  118: 132 */     var7.sendPacket(new S09PacketHeldItemChange(par2EntityPlayerMP.inventory.currentItem));
/*  119: 133 */     par2EntityPlayerMP.func_147099_x().func_150877_d();
/*  120: 134 */     par2EntityPlayerMP.func_147099_x().func_150884_b(par2EntityPlayerMP);
/*  121: 135 */     func_96456_a((ServerScoreboard)var5.getScoreboard(), par2EntityPlayerMP);
/*  122: 136 */     this.mcServer.func_147132_au();
/*  123: 137 */     ChatComponentTranslation var8 = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { par2EntityPlayerMP.func_145748_c_() });
/*  124: 138 */     var8.getChatStyle().setColor(EnumChatFormatting.YELLOW);
/*  125: 139 */     func_148539_a(var8);
/*  126: 140 */     playerLoggedIn(par2EntityPlayerMP);
/*  127: 141 */     var7.setPlayerLocation(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
/*  128: 142 */     updateTimeAndWeatherForPlayer(par2EntityPlayerMP, var5);
/*  129: 144 */     if (this.mcServer.func_147133_T().length() > 0) {
/*  130: 146 */       par2EntityPlayerMP.func_147095_a(this.mcServer.func_147133_T());
/*  131:     */     }
/*  132: 149 */     Iterator var9 = par2EntityPlayerMP.getActivePotionEffects().iterator();
/*  133: 151 */     while (var9.hasNext())
/*  134:     */     {
/*  135: 153 */       PotionEffect var10 = (PotionEffect)var9.next();
/*  136: 154 */       var7.sendPacket(new S1DPacketEntityEffect(par2EntityPlayerMP.getEntityId(), var10));
/*  137:     */     }
/*  138: 157 */     par2EntityPlayerMP.addSelfToInternalCraftingInventory();
/*  139: 159 */     if ((var3 != null) && (var3.func_150297_b("Riding", 10)))
/*  140:     */     {
/*  141: 161 */       Entity var11 = EntityList.createEntityFromNBT(var3.getCompoundTag("Riding"), var5);
/*  142: 163 */       if (var11 != null)
/*  143:     */       {
/*  144: 165 */         var11.forceSpawn = true;
/*  145: 166 */         var5.spawnEntityInWorld(var11);
/*  146: 167 */         par2EntityPlayerMP.mountEntity(var11);
/*  147: 168 */         var11.forceSpawn = false;
/*  148:     */       }
/*  149:     */     }
/*  150:     */   }
/*  151:     */   
/*  152:     */   protected void func_96456_a(ServerScoreboard par1ServerScoreboard, EntityPlayerMP par2EntityPlayerMP)
/*  153:     */   {
/*  154: 175 */     HashSet var3 = new HashSet();
/*  155: 176 */     Iterator var4 = par1ServerScoreboard.getTeams().iterator();
/*  156: 178 */     while (var4.hasNext())
/*  157:     */     {
/*  158: 180 */       ScorePlayerTeam var5 = (ScorePlayerTeam)var4.next();
/*  159: 181 */       par2EntityPlayerMP.playerNetServerHandler.sendPacket(new S3EPacketTeams(var5, 0));
/*  160:     */     }
/*  161: 184 */     for (int var9 = 0; var9 < 3; var9++)
/*  162:     */     {
/*  163: 186 */       ScoreObjective var10 = par1ServerScoreboard.func_96539_a(var9);
/*  164: 188 */       if ((var10 != null) && (!var3.contains(var10)))
/*  165:     */       {
/*  166: 190 */         List var6 = par1ServerScoreboard.func_96550_d(var10);
/*  167: 191 */         Iterator var7 = var6.iterator();
/*  168: 193 */         while (var7.hasNext())
/*  169:     */         {
/*  170: 195 */           Packet var8 = (Packet)var7.next();
/*  171: 196 */           par2EntityPlayerMP.playerNetServerHandler.sendPacket(var8);
/*  172:     */         }
/*  173: 199 */         var3.add(var10);
/*  174:     */       }
/*  175:     */     }
/*  176:     */   }
/*  177:     */   
/*  178:     */   public void setPlayerManager(WorldServer[] par1ArrayOfWorldServer)
/*  179:     */   {
/*  180: 209 */     this.playerNBTManagerObj = par1ArrayOfWorldServer[0].getSaveHandler().getSaveHandler();
/*  181:     */   }
/*  182:     */   
/*  183:     */   public void func_72375_a(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
/*  184:     */   {
/*  185: 214 */     WorldServer var3 = par1EntityPlayerMP.getServerForPlayer();
/*  186: 216 */     if (par2WorldServer != null) {
/*  187: 218 */       par2WorldServer.getPlayerManager().removePlayer(par1EntityPlayerMP);
/*  188:     */     }
/*  189: 221 */     var3.getPlayerManager().addPlayer(par1EntityPlayerMP);
/*  190: 222 */     var3.theChunkProviderServer.loadChunk((int)par1EntityPlayerMP.posX >> 4, (int)par1EntityPlayerMP.posZ >> 4);
/*  191:     */   }
/*  192:     */   
/*  193:     */   public int getEntityViewDistance()
/*  194:     */   {
/*  195: 227 */     return PlayerManager.getFurthestViewableBlock(getViewDistance());
/*  196:     */   }
/*  197:     */   
/*  198:     */   public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP par1EntityPlayerMP)
/*  199:     */   {
/*  200: 235 */     NBTTagCompound var2 = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
/*  201:     */     NBTTagCompound var3;
/*  202: 238 */     if ((par1EntityPlayerMP.getCommandSenderName().equals(this.mcServer.getServerOwner())) && (var2 != null))
/*  203:     */     {
/*  204: 240 */       par1EntityPlayerMP.readFromNBT(var2);
/*  205: 241 */       NBTTagCompound var3 = var2;
/*  206: 242 */       logger.debug("loading single player");
/*  207:     */     }
/*  208:     */     else
/*  209:     */     {
/*  210: 246 */       var3 = this.playerNBTManagerObj.readPlayerData(par1EntityPlayerMP);
/*  211:     */     }
/*  212: 249 */     return var3;
/*  213:     */   }
/*  214:     */   
/*  215:     */   protected void writePlayerData(EntityPlayerMP par1EntityPlayerMP)
/*  216:     */   {
/*  217: 257 */     this.playerNBTManagerObj.writePlayerData(par1EntityPlayerMP);
/*  218: 258 */     StatisticsFile var2 = (StatisticsFile)this.field_148547_k.get(par1EntityPlayerMP.getCommandSenderName());
/*  219: 260 */     if (var2 != null) {
/*  220: 262 */       var2.func_150883_b();
/*  221:     */     }
/*  222:     */   }
/*  223:     */   
/*  224:     */   public void playerLoggedIn(EntityPlayerMP par1EntityPlayerMP)
/*  225:     */   {
/*  226: 271 */     func_148540_a(new S38PacketPlayerListItem(par1EntityPlayerMP.getCommandSenderName(), true, 1000));
/*  227: 272 */     this.playerEntityList.add(par1EntityPlayerMP);
/*  228: 273 */     WorldServer var2 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
/*  229: 274 */     var2.spawnEntityInWorld(par1EntityPlayerMP);
/*  230: 275 */     func_72375_a(par1EntityPlayerMP, null);
/*  231: 277 */     for (int var3 = 0; var3 < this.playerEntityList.size(); var3++)
/*  232:     */     {
/*  233: 279 */       EntityPlayerMP var4 = (EntityPlayerMP)this.playerEntityList.get(var3);
/*  234: 280 */       par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S38PacketPlayerListItem(var4.getCommandSenderName(), true, var4.ping));
/*  235:     */     }
/*  236:     */   }
/*  237:     */   
/*  238:     */   public void serverUpdateMountedMovingPlayer(EntityPlayerMP par1EntityPlayerMP)
/*  239:     */   {
/*  240: 289 */     par1EntityPlayerMP.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(par1EntityPlayerMP);
/*  241:     */   }
/*  242:     */   
/*  243:     */   public void playerLoggedOut(EntityPlayerMP par1EntityPlayerMP)
/*  244:     */   {
/*  245: 297 */     par1EntityPlayerMP.triggerAchievement(StatList.leaveGameStat);
/*  246: 298 */     writePlayerData(par1EntityPlayerMP);
/*  247: 299 */     WorldServer var2 = par1EntityPlayerMP.getServerForPlayer();
/*  248: 301 */     if (par1EntityPlayerMP.ridingEntity != null)
/*  249:     */     {
/*  250: 303 */       var2.removePlayerEntityDangerously(par1EntityPlayerMP.ridingEntity);
/*  251: 304 */       logger.debug("removing player mount");
/*  252:     */     }
/*  253: 307 */     var2.removeEntity(par1EntityPlayerMP);
/*  254: 308 */     var2.getPlayerManager().removePlayer(par1EntityPlayerMP);
/*  255: 309 */     this.playerEntityList.remove(par1EntityPlayerMP);
/*  256: 310 */     this.field_148547_k.remove(par1EntityPlayerMP.getCommandSenderName());
/*  257: 311 */     func_148540_a(new S38PacketPlayerListItem(par1EntityPlayerMP.getCommandSenderName(), false, 9999));
/*  258:     */   }
/*  259:     */   
/*  260:     */   public String func_148542_a(SocketAddress p_148542_1_, GameProfile p_148542_2_)
/*  261:     */   {
/*  262: 316 */     if (this.bannedPlayers.isBanned(p_148542_2_.getName()))
/*  263:     */     {
/*  264: 318 */       BanEntry var6 = (BanEntry)this.bannedPlayers.getBannedList().get(p_148542_2_.getName());
/*  265: 319 */       String var7 = "You are banned from this server!\nReason: " + var6.getBanReason();
/*  266: 321 */       if (var6.getBanEndDate() != null) {
/*  267: 323 */         var7 = var7 + "\nYour ban will be removed on " + dateFormat.format(var6.getBanEndDate());
/*  268:     */       }
/*  269: 326 */       return var7;
/*  270:     */     }
/*  271: 328 */     if (!isAllowedToLogin(p_148542_2_.getName())) {
/*  272: 330 */       return "You are not white-listed on this server!";
/*  273:     */     }
/*  274: 334 */     String var3 = p_148542_1_.toString();
/*  275: 335 */     var3 = var3.substring(var3.indexOf("/") + 1);
/*  276: 336 */     var3 = var3.substring(0, var3.indexOf(":"));
/*  277: 338 */     if (this.bannedIPs.isBanned(var3))
/*  278:     */     {
/*  279: 340 */       BanEntry var4 = (BanEntry)this.bannedIPs.getBannedList().get(var3);
/*  280: 341 */       String var5 = "Your IP address is banned from this server!\nReason: " + var4.getBanReason();
/*  281: 343 */       if (var4.getBanEndDate() != null) {
/*  282: 345 */         var5 = var5 + "\nYour ban will be removed on " + dateFormat.format(var4.getBanEndDate());
/*  283:     */       }
/*  284: 348 */       return var5;
/*  285:     */     }
/*  286: 352 */     return this.playerEntityList.size() >= this.maxPlayers ? "The server is full!" : null;
/*  287:     */   }
/*  288:     */   
/*  289:     */   public EntityPlayerMP func_148545_a(GameProfile p_148545_1_)
/*  290:     */   {
/*  291: 359 */     ArrayList var2 = new ArrayList();
/*  292: 362 */     for (int var3 = 0; var3 < this.playerEntityList.size(); var3++)
/*  293:     */     {
/*  294: 364 */       EntityPlayerMP var4 = (EntityPlayerMP)this.playerEntityList.get(var3);
/*  295: 366 */       if (var4.getCommandSenderName().equalsIgnoreCase(p_148545_1_.getName())) {
/*  296: 368 */         var2.add(var4);
/*  297:     */       }
/*  298:     */     }
/*  299: 372 */     Iterator var5 = var2.iterator();
/*  300: 374 */     while (var5.hasNext())
/*  301:     */     {
/*  302: 376 */       EntityPlayerMP var4 = (EntityPlayerMP)var5.next();
/*  303: 377 */       var4.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
/*  304:     */     }
/*  305:     */     Object var6;
/*  306:     */     Object var6;
/*  307: 382 */     if (this.mcServer.isDemo()) {
/*  308: 384 */       var6 = new DemoWorldManager(this.mcServer.worldServerForDimension(0));
/*  309:     */     } else {
/*  310: 388 */       var6 = new ItemInWorldManager(this.mcServer.worldServerForDimension(0));
/*  311:     */     }
/*  312: 391 */     return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), p_148545_1_, (ItemInWorldManager)var6);
/*  313:     */   }
/*  314:     */   
/*  315:     */   public EntityPlayerMP respawnPlayer(EntityPlayerMP par1EntityPlayerMP, int par2, boolean par3)
/*  316:     */   {
/*  317: 401 */     par1EntityPlayerMP.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(par1EntityPlayerMP);
/*  318: 402 */     par1EntityPlayerMP.getServerForPlayer().getEntityTracker().removeEntityFromAllTrackingPlayers(par1EntityPlayerMP);
/*  319: 403 */     par1EntityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(par1EntityPlayerMP);
/*  320: 404 */     this.playerEntityList.remove(par1EntityPlayerMP);
/*  321: 405 */     this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension).removePlayerEntityDangerously(par1EntityPlayerMP);
/*  322: 406 */     ChunkCoordinates var4 = par1EntityPlayerMP.getBedLocation();
/*  323: 407 */     boolean var5 = par1EntityPlayerMP.isSpawnForced();
/*  324: 408 */     par1EntityPlayerMP.dimension = par2;
/*  325:     */     Object var6;
/*  326:     */     Object var6;
/*  327: 411 */     if (this.mcServer.isDemo()) {
/*  328: 413 */       var6 = new DemoWorldManager(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension));
/*  329:     */     } else {
/*  330: 417 */       var6 = new ItemInWorldManager(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension));
/*  331:     */     }
/*  332: 420 */     EntityPlayerMP var7 = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension), par1EntityPlayerMP.getGameProfile(), (ItemInWorldManager)var6);
/*  333: 421 */     var7.playerNetServerHandler = par1EntityPlayerMP.playerNetServerHandler;
/*  334: 422 */     var7.clonePlayer(par1EntityPlayerMP, par3);
/*  335: 423 */     var7.setEntityId(par1EntityPlayerMP.getEntityId());
/*  336: 424 */     WorldServer var8 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
/*  337: 425 */     func_72381_a(var7, par1EntityPlayerMP, var8);
/*  338: 428 */     if (var4 != null)
/*  339:     */     {
/*  340: 430 */       ChunkCoordinates var9 = EntityPlayer.verifyRespawnCoordinates(this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension), var4, var5);
/*  341: 432 */       if (var9 != null)
/*  342:     */       {
/*  343: 434 */         var7.setLocationAndAngles(var9.posX + 0.5F, var9.posY + 0.1F, var9.posZ + 0.5F, 0.0F, 0.0F);
/*  344: 435 */         var7.setSpawnChunk(var4, var5);
/*  345:     */       }
/*  346:     */       else
/*  347:     */       {
/*  348: 439 */         var7.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0, 0.0F));
/*  349:     */       }
/*  350:     */     }
/*  351: 443 */     var8.theChunkProviderServer.loadChunk((int)var7.posX >> 4, (int)var7.posZ >> 4);
/*  352: 445 */     while (!var8.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty()) {
/*  353: 447 */       var7.setPosition(var7.posX, var7.posY + 1.0D, var7.posZ);
/*  354:     */     }
/*  355: 450 */     var7.playerNetServerHandler.sendPacket(new S07PacketRespawn(var7.dimension, var7.worldObj.difficultySetting, var7.worldObj.getWorldInfo().getTerrainType(), var7.theItemInWorldManager.getGameType()));
/*  356: 451 */     ChunkCoordinates var9 = var8.getSpawnPoint();
/*  357: 452 */     var7.playerNetServerHandler.setPlayerLocation(var7.posX, var7.posY, var7.posZ, var7.rotationYaw, var7.rotationPitch);
/*  358: 453 */     var7.playerNetServerHandler.sendPacket(new S05PacketSpawnPosition(var9.posX, var9.posY, var9.posZ));
/*  359: 454 */     var7.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(var7.experience, var7.experienceTotal, var7.experienceLevel));
/*  360: 455 */     updateTimeAndWeatherForPlayer(var7, var8);
/*  361: 456 */     var8.getPlayerManager().addPlayer(var7);
/*  362: 457 */     var8.spawnEntityInWorld(var7);
/*  363: 458 */     this.playerEntityList.add(var7);
/*  364: 459 */     var7.addSelfToInternalCraftingInventory();
/*  365: 460 */     var7.setHealth(var7.getHealth());
/*  366: 461 */     return var7;
/*  367:     */   }
/*  368:     */   
/*  369:     */   public void transferPlayerToDimension(EntityPlayerMP par1EntityPlayerMP, int par2)
/*  370:     */   {
/*  371: 466 */     int var3 = par1EntityPlayerMP.dimension;
/*  372: 467 */     WorldServer var4 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
/*  373: 468 */     par1EntityPlayerMP.dimension = par2;
/*  374: 469 */     WorldServer var5 = this.mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
/*  375: 470 */     par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S07PacketRespawn(par1EntityPlayerMP.dimension, par1EntityPlayerMP.worldObj.difficultySetting, par1EntityPlayerMP.worldObj.getWorldInfo().getTerrainType(), par1EntityPlayerMP.theItemInWorldManager.getGameType()));
/*  376: 471 */     var4.removePlayerEntityDangerously(par1EntityPlayerMP);
/*  377: 472 */     par1EntityPlayerMP.isDead = false;
/*  378: 473 */     transferEntityToWorld(par1EntityPlayerMP, var3, var4, var5);
/*  379: 474 */     func_72375_a(par1EntityPlayerMP, var4);
/*  380: 475 */     par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
/*  381: 476 */     par1EntityPlayerMP.theItemInWorldManager.setWorld(var5);
/*  382: 477 */     updateTimeAndWeatherForPlayer(par1EntityPlayerMP, var5);
/*  383: 478 */     syncPlayerInventory(par1EntityPlayerMP);
/*  384: 479 */     Iterator var6 = par1EntityPlayerMP.getActivePotionEffects().iterator();
/*  385: 481 */     while (var6.hasNext())
/*  386:     */     {
/*  387: 483 */       PotionEffect var7 = (PotionEffect)var6.next();
/*  388: 484 */       par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(par1EntityPlayerMP.getEntityId(), var7));
/*  389:     */     }
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void transferEntityToWorld(Entity par1Entity, int par2, WorldServer par3WorldServer, WorldServer par4WorldServer)
/*  393:     */   {
/*  394: 493 */     double var5 = par1Entity.posX;
/*  395: 494 */     double var7 = par1Entity.posZ;
/*  396: 495 */     double var9 = 8.0D;
/*  397: 496 */     double var11 = par1Entity.posX;
/*  398: 497 */     double var13 = par1Entity.posY;
/*  399: 498 */     double var15 = par1Entity.posZ;
/*  400: 499 */     float var17 = par1Entity.rotationYaw;
/*  401: 500 */     par3WorldServer.theProfiler.startSection("moving");
/*  402: 502 */     if (par1Entity.dimension == -1)
/*  403:     */     {
/*  404: 504 */       var5 /= var9;
/*  405: 505 */       var7 /= var9;
/*  406: 506 */       par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, par1Entity.rotationYaw, par1Entity.rotationPitch);
/*  407: 508 */       if (par1Entity.isEntityAlive()) {
/*  408: 510 */         par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
/*  409:     */       }
/*  410:     */     }
/*  411: 513 */     else if (par1Entity.dimension == 0)
/*  412:     */     {
/*  413: 515 */       var5 *= var9;
/*  414: 516 */       var7 *= var9;
/*  415: 517 */       par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, par1Entity.rotationYaw, par1Entity.rotationPitch);
/*  416: 519 */       if (par1Entity.isEntityAlive()) {
/*  417: 521 */         par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
/*  418:     */       }
/*  419:     */     }
/*  420:     */     else
/*  421:     */     {
/*  422:     */       ChunkCoordinates var18;
/*  423:     */       ChunkCoordinates var18;
/*  424: 528 */       if (par2 == 1) {
/*  425: 530 */         var18 = par4WorldServer.getSpawnPoint();
/*  426:     */       } else {
/*  427: 534 */         var18 = par4WorldServer.getEntrancePortalLocation();
/*  428:     */       }
/*  429: 537 */       var5 = var18.posX;
/*  430: 538 */       par1Entity.posY = var18.posY;
/*  431: 539 */       var7 = var18.posZ;
/*  432: 540 */       par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, 90.0F, 0.0F);
/*  433: 542 */       if (par1Entity.isEntityAlive()) {
/*  434: 544 */         par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
/*  435:     */       }
/*  436:     */     }
/*  437: 548 */     par3WorldServer.theProfiler.endSection();
/*  438: 550 */     if (par2 != 1)
/*  439:     */     {
/*  440: 552 */       par3WorldServer.theProfiler.startSection("placing");
/*  441: 553 */       var5 = MathHelper.clamp_int((int)var5, -29999872, 29999872);
/*  442: 554 */       var7 = MathHelper.clamp_int((int)var7, -29999872, 29999872);
/*  443: 556 */       if (par1Entity.isEntityAlive())
/*  444:     */       {
/*  445: 558 */         par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, par1Entity.rotationYaw, par1Entity.rotationPitch);
/*  446: 559 */         par4WorldServer.getDefaultTeleporter().placeInPortal(par1Entity, var11, var13, var15, var17);
/*  447: 560 */         par4WorldServer.spawnEntityInWorld(par1Entity);
/*  448: 561 */         par4WorldServer.updateEntityWithOptionalForce(par1Entity, false);
/*  449:     */       }
/*  450: 564 */       par3WorldServer.theProfiler.endSection();
/*  451:     */     }
/*  452: 567 */     par1Entity.setWorld(par4WorldServer);
/*  453:     */   }
/*  454:     */   
/*  455:     */   public void sendPlayerInfoToAllPlayers()
/*  456:     */   {
/*  457: 575 */     if (++this.playerPingIndex > 600) {
/*  458: 577 */       this.playerPingIndex = 0;
/*  459:     */     }
/*  460: 580 */     if (this.playerPingIndex < this.playerEntityList.size())
/*  461:     */     {
/*  462: 582 */       EntityPlayerMP var1 = (EntityPlayerMP)this.playerEntityList.get(this.playerPingIndex);
/*  463: 583 */       func_148540_a(new S38PacketPlayerListItem(var1.getCommandSenderName(), true, var1.ping));
/*  464:     */     }
/*  465:     */   }
/*  466:     */   
/*  467:     */   public void func_148540_a(Packet p_148540_1_)
/*  468:     */   {
/*  469: 589 */     for (int var2 = 0; var2 < this.playerEntityList.size(); var2++) {
/*  470: 591 */       ((EntityPlayerMP)this.playerEntityList.get(var2)).playerNetServerHandler.sendPacket(p_148540_1_);
/*  471:     */     }
/*  472:     */   }
/*  473:     */   
/*  474:     */   public void func_148537_a(Packet p_148537_1_, int p_148537_2_)
/*  475:     */   {
/*  476: 597 */     for (int var3 = 0; var3 < this.playerEntityList.size(); var3++)
/*  477:     */     {
/*  478: 599 */       EntityPlayerMP var4 = (EntityPlayerMP)this.playerEntityList.get(var3);
/*  479: 601 */       if (var4.dimension == p_148537_2_) {
/*  480: 603 */         var4.playerNetServerHandler.sendPacket(p_148537_1_);
/*  481:     */       }
/*  482:     */     }
/*  483:     */   }
/*  484:     */   
/*  485:     */   public String getPlayerListAsString()
/*  486:     */   {
/*  487: 613 */     String var1 = "";
/*  488: 615 */     for (int var2 = 0; var2 < this.playerEntityList.size(); var2++)
/*  489:     */     {
/*  490: 617 */       if (var2 > 0) {
/*  491: 619 */         var1 = var1 + ", ";
/*  492:     */       }
/*  493: 622 */       var1 = var1 + ((EntityPlayerMP)this.playerEntityList.get(var2)).getCommandSenderName();
/*  494:     */     }
/*  495: 625 */     return var1;
/*  496:     */   }
/*  497:     */   
/*  498:     */   public String[] getAllUsernames()
/*  499:     */   {
/*  500: 633 */     String[] var1 = new String[this.playerEntityList.size()];
/*  501: 635 */     for (int var2 = 0; var2 < this.playerEntityList.size(); var2++) {
/*  502: 637 */       var1[var2] = ((EntityPlayerMP)this.playerEntityList.get(var2)).getCommandSenderName();
/*  503:     */     }
/*  504: 640 */     return var1;
/*  505:     */   }
/*  506:     */   
/*  507:     */   public BanList getBannedPlayers()
/*  508:     */   {
/*  509: 645 */     return this.bannedPlayers;
/*  510:     */   }
/*  511:     */   
/*  512:     */   public BanList getBannedIPs()
/*  513:     */   {
/*  514: 650 */     return this.bannedIPs;
/*  515:     */   }
/*  516:     */   
/*  517:     */   public void addOp(String par1Str)
/*  518:     */   {
/*  519: 658 */     this.ops.add(par1Str.toLowerCase());
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void removeOp(String par1Str)
/*  523:     */   {
/*  524: 666 */     this.ops.remove(par1Str.toLowerCase());
/*  525:     */   }
/*  526:     */   
/*  527:     */   public boolean isAllowedToLogin(String par1Str)
/*  528:     */   {
/*  529: 674 */     par1Str = par1Str.trim().toLowerCase();
/*  530: 675 */     return (!this.whiteListEnforced) || (this.ops.contains(par1Str)) || (this.whiteListedPlayers.contains(par1Str));
/*  531:     */   }
/*  532:     */   
/*  533:     */   public boolean isPlayerOpped(String par1Str)
/*  534:     */   {
/*  535: 683 */     return (this.ops.contains(par1Str.trim().toLowerCase())) || ((this.mcServer.isSinglePlayer()) && (this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed()) && (this.mcServer.getServerOwner().equalsIgnoreCase(par1Str))) || (this.commandsAllowedForAll);
/*  536:     */   }
/*  537:     */   
/*  538:     */   public EntityPlayerMP getPlayerForUsername(String par1Str)
/*  539:     */   {
/*  540: 688 */     Iterator var2 = this.playerEntityList.iterator();
/*  541:     */     EntityPlayerMP var3;
/*  542:     */     do
/*  543:     */     {
/*  544: 693 */       if (!var2.hasNext()) {
/*  545: 695 */         return null;
/*  546:     */       }
/*  547: 698 */       var3 = (EntityPlayerMP)var2.next();
/*  548: 700 */     } while (!var3.getCommandSenderName().equalsIgnoreCase(par1Str));
/*  549: 702 */     return var3;
/*  550:     */   }
/*  551:     */   
/*  552:     */   public List findPlayers(ChunkCoordinates par1ChunkCoordinates, int par2, int par3, int par4, int par5, int par6, int par7, Map par8Map, String par9Str, String par10Str, World par11World)
/*  553:     */   {
/*  554: 710 */     if (this.playerEntityList.isEmpty()) {
/*  555: 712 */       return null;
/*  556:     */     }
/*  557: 716 */     Object var12 = new ArrayList();
/*  558: 717 */     boolean var13 = par4 < 0;
/*  559: 718 */     boolean var14 = (par9Str != null) && (par9Str.startsWith("!"));
/*  560: 719 */     boolean var15 = (par10Str != null) && (par10Str.startsWith("!"));
/*  561: 720 */     int var16 = par2 * par2;
/*  562: 721 */     int var17 = par3 * par3;
/*  563: 722 */     par4 = MathHelper.abs_int(par4);
/*  564: 724 */     if (var14) {
/*  565: 726 */       par9Str = par9Str.substring(1);
/*  566:     */     }
/*  567: 729 */     if (var15) {
/*  568: 731 */       par10Str = par10Str.substring(1);
/*  569:     */     }
/*  570: 734 */     for (int var18 = 0; var18 < this.playerEntityList.size(); var18++)
/*  571:     */     {
/*  572: 736 */       EntityPlayerMP var19 = (EntityPlayerMP)this.playerEntityList.get(var18);
/*  573: 738 */       if (((par11World == null) || (var19.worldObj == par11World)) && ((par9Str == null) || (var14 != par9Str.equalsIgnoreCase(var19.getCommandSenderName())))) {
/*  574: 740 */         if (par10Str != null)
/*  575:     */         {
/*  576: 742 */           Team var20 = var19.getTeam();
/*  577: 743 */           String var21 = var20 == null ? "" : var20.getRegisteredName();
/*  578: 745 */           if (var15 == par10Str.equalsIgnoreCase(var21)) {}
/*  579:     */         }
/*  580: 751 */         else if ((par1ChunkCoordinates != null) && ((par2 > 0) || (par3 > 0)))
/*  581:     */         {
/*  582: 753 */           float var22 = par1ChunkCoordinates.getDistanceSquaredToChunkCoordinates(var19.getPlayerCoordinates());
/*  583: 755 */           if (((par2 > 0) && (var22 < var16)) || ((par3 > 0) && (var22 > var17))) {}
/*  584:     */         }
/*  585: 761 */         else if ((func_96457_a(var19, par8Map)) && ((par5 == WorldSettings.GameType.NOT_SET.getID()) || (par5 == var19.theItemInWorldManager.getGameType().getID())) && ((par6 <= 0) || (var19.experienceLevel >= par6)) && (var19.experienceLevel <= par7))
/*  586:     */         {
/*  587: 763 */           ((List)var12).add(var19);
/*  588:     */         }
/*  589:     */       }
/*  590:     */     }
/*  591: 768 */     if (par1ChunkCoordinates != null) {
/*  592: 770 */       Collections.sort((List)var12, new PlayerPositionComparator(par1ChunkCoordinates));
/*  593:     */     }
/*  594: 773 */     if (var13) {
/*  595: 775 */       Collections.reverse((List)var12);
/*  596:     */     }
/*  597: 778 */     if (par4 > 0) {
/*  598: 780 */       var12 = ((List)var12).subList(0, Math.min(par4, ((List)var12).size()));
/*  599:     */     }
/*  600: 783 */     return (List)var12;
/*  601:     */   }
/*  602:     */   
/*  603:     */   private boolean func_96457_a(EntityPlayer par1EntityPlayer, Map par2Map)
/*  604:     */   {
/*  605: 789 */     if ((par2Map != null) && (par2Map.size() != 0))
/*  606:     */     {
/*  607: 791 */       Iterator var3 = par2Map.entrySet().iterator();
/*  608:     */       Map.Entry var4;
/*  609:     */       boolean var6;
/*  610:     */       int var10;
/*  611:     */       do
/*  612:     */       {
/*  613: 798 */         if (!var3.hasNext()) {
/*  614: 800 */           return true;
/*  615:     */         }
/*  616: 803 */         var4 = (Map.Entry)var3.next();
/*  617: 804 */         String var5 = (String)var4.getKey();
/*  618: 805 */         var6 = false;
/*  619: 807 */         if ((var5.endsWith("_min")) && (var5.length() > 4))
/*  620:     */         {
/*  621: 809 */           var6 = true;
/*  622: 810 */           var5 = var5.substring(0, var5.length() - 4);
/*  623:     */         }
/*  624: 813 */         Scoreboard var7 = par1EntityPlayer.getWorldScoreboard();
/*  625: 814 */         ScoreObjective var8 = var7.getObjective(var5);
/*  626: 816 */         if (var8 == null) {
/*  627: 818 */           return false;
/*  628:     */         }
/*  629: 821 */         Score var9 = par1EntityPlayer.getWorldScoreboard().func_96529_a(par1EntityPlayer.getCommandSenderName(), var8);
/*  630: 822 */         var10 = var9.getScorePoints();
/*  631: 824 */         if ((var10 < ((Integer)var4.getValue()).intValue()) && (var6)) {
/*  632: 826 */           return false;
/*  633:     */         }
/*  634: 829 */       } while ((var10 <= ((Integer)var4.getValue()).intValue()) || (var6));
/*  635: 831 */       return false;
/*  636:     */     }
/*  637: 835 */     return true;
/*  638:     */   }
/*  639:     */   
/*  640:     */   public void func_148541_a(double p_148541_1_, double p_148541_3_, double p_148541_5_, double p_148541_7_, int p_148541_9_, Packet p_148541_10_)
/*  641:     */   {
/*  642: 841 */     func_148543_a(null, p_148541_1_, p_148541_3_, p_148541_5_, p_148541_7_, p_148541_9_, p_148541_10_);
/*  643:     */   }
/*  644:     */   
/*  645:     */   public void func_148543_a(EntityPlayer p_148543_1_, double p_148543_2_, double p_148543_4_, double p_148543_6_, double p_148543_8_, int p_148543_10_, Packet p_148543_11_)
/*  646:     */   {
/*  647: 846 */     for (int var12 = 0; var12 < this.playerEntityList.size(); var12++)
/*  648:     */     {
/*  649: 848 */       EntityPlayerMP var13 = (EntityPlayerMP)this.playerEntityList.get(var12);
/*  650: 850 */       if ((var13 != p_148543_1_) && (var13.dimension == p_148543_10_))
/*  651:     */       {
/*  652: 852 */         double var14 = p_148543_2_ - var13.posX;
/*  653: 853 */         double var16 = p_148543_4_ - var13.posY;
/*  654: 854 */         double var18 = p_148543_6_ - var13.posZ;
/*  655: 856 */         if (var14 * var14 + var16 * var16 + var18 * var18 < p_148543_8_ * p_148543_8_) {
/*  656: 858 */           var13.playerNetServerHandler.sendPacket(p_148543_11_);
/*  657:     */         }
/*  658:     */       }
/*  659:     */     }
/*  660:     */   }
/*  661:     */   
/*  662:     */   public void saveAllPlayerData()
/*  663:     */   {
/*  664: 869 */     for (int var1 = 0; var1 < this.playerEntityList.size(); var1++) {
/*  665: 871 */       writePlayerData((EntityPlayerMP)this.playerEntityList.get(var1));
/*  666:     */     }
/*  667:     */   }
/*  668:     */   
/*  669:     */   public void addToWhiteList(String par1Str)
/*  670:     */   {
/*  671: 880 */     this.whiteListedPlayers.add(par1Str);
/*  672:     */   }
/*  673:     */   
/*  674:     */   public void removeFromWhitelist(String par1Str)
/*  675:     */   {
/*  676: 888 */     this.whiteListedPlayers.remove(par1Str);
/*  677:     */   }
/*  678:     */   
/*  679:     */   public Set getWhiteListedPlayers()
/*  680:     */   {
/*  681: 896 */     return this.whiteListedPlayers;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public Set getOps()
/*  685:     */   {
/*  686: 901 */     return this.ops;
/*  687:     */   }
/*  688:     */   
/*  689:     */   public void loadWhiteList() {}
/*  690:     */   
/*  691:     */   public void updateTimeAndWeatherForPlayer(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
/*  692:     */   {
/*  693: 914 */     par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(par2WorldServer.getTotalWorldTime(), par2WorldServer.getWorldTime(), par2WorldServer.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
/*  694: 916 */     if (par2WorldServer.isRaining())
/*  695:     */     {
/*  696: 918 */       par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(1, 0.0F));
/*  697: 919 */       par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(7, par2WorldServer.getRainStrength(1.0F)));
/*  698: 920 */       par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(8, par2WorldServer.getWeightedThunderStrength(1.0F)));
/*  699:     */     }
/*  700:     */   }
/*  701:     */   
/*  702:     */   public void syncPlayerInventory(EntityPlayerMP par1EntityPlayerMP)
/*  703:     */   {
/*  704: 929 */     par1EntityPlayerMP.sendContainerToPlayer(par1EntityPlayerMP.inventoryContainer);
/*  705: 930 */     par1EntityPlayerMP.setPlayerHealthUpdated();
/*  706: 931 */     par1EntityPlayerMP.playerNetServerHandler.sendPacket(new S09PacketHeldItemChange(par1EntityPlayerMP.inventory.currentItem));
/*  707:     */   }
/*  708:     */   
/*  709:     */   public int getCurrentPlayerCount()
/*  710:     */   {
/*  711: 939 */     return this.playerEntityList.size();
/*  712:     */   }
/*  713:     */   
/*  714:     */   public int getMaxPlayers()
/*  715:     */   {
/*  716: 947 */     return this.maxPlayers;
/*  717:     */   }
/*  718:     */   
/*  719:     */   public String[] getAvailablePlayerDat()
/*  720:     */   {
/*  721: 955 */     return this.mcServer.worldServers[0].getSaveHandler().getSaveHandler().getAvailablePlayerDat();
/*  722:     */   }
/*  723:     */   
/*  724:     */   public void setWhiteListEnabled(boolean par1)
/*  725:     */   {
/*  726: 960 */     this.whiteListEnforced = par1;
/*  727:     */   }
/*  728:     */   
/*  729:     */   public List getPlayerList(String par1Str)
/*  730:     */   {
/*  731: 965 */     ArrayList var2 = new ArrayList();
/*  732: 966 */     Iterator var3 = this.playerEntityList.iterator();
/*  733: 968 */     while (var3.hasNext())
/*  734:     */     {
/*  735: 970 */       EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
/*  736: 972 */       if (var4.getPlayerIP().equals(par1Str)) {
/*  737: 974 */         var2.add(var4);
/*  738:     */       }
/*  739:     */     }
/*  740: 978 */     return var2;
/*  741:     */   }
/*  742:     */   
/*  743:     */   public int getViewDistance()
/*  744:     */   {
/*  745: 986 */     return this.viewDistance;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public MinecraftServer getServerInstance()
/*  749:     */   {
/*  750: 991 */     return this.mcServer;
/*  751:     */   }
/*  752:     */   
/*  753:     */   public NBTTagCompound getHostPlayerData()
/*  754:     */   {
/*  755: 999 */     return null;
/*  756:     */   }
/*  757:     */   
/*  758:     */   public void setGameType(WorldSettings.GameType par1EnumGameType)
/*  759:     */   {
/*  760:1004 */     this.gameType = par1EnumGameType;
/*  761:     */   }
/*  762:     */   
/*  763:     */   private void func_72381_a(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP, World par3World)
/*  764:     */   {
/*  765:1009 */     if (par2EntityPlayerMP != null) {
/*  766:1011 */       par1EntityPlayerMP.theItemInWorldManager.setGameType(par2EntityPlayerMP.theItemInWorldManager.getGameType());
/*  767:1013 */     } else if (this.gameType != null) {
/*  768:1015 */       par1EntityPlayerMP.theItemInWorldManager.setGameType(this.gameType);
/*  769:     */     }
/*  770:1018 */     par1EntityPlayerMP.theItemInWorldManager.initializeGameType(par3World.getWorldInfo().getGameType());
/*  771:     */   }
/*  772:     */   
/*  773:     */   public void setCommandsAllowedForAll(boolean par1)
/*  774:     */   {
/*  775:1026 */     this.commandsAllowedForAll = par1;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public void removeAllPlayers()
/*  779:     */   {
/*  780:1034 */     for (int var1 = 0; var1 < this.playerEntityList.size(); var1++) {
/*  781:1036 */       ((EntityPlayerMP)this.playerEntityList.get(var1)).playerNetServerHandler.kickPlayerFromServer("Server closed");
/*  782:     */     }
/*  783:     */   }
/*  784:     */   
/*  785:     */   public void func_148544_a(IChatComponent p_148544_1_, boolean p_148544_2_)
/*  786:     */   {
/*  787:1042 */     this.mcServer.addChatMessage(p_148544_1_);
/*  788:1043 */     func_148540_a(new S02PacketChat(p_148544_1_, p_148544_2_));
/*  789:     */   }
/*  790:     */   
/*  791:     */   public void func_148539_a(IChatComponent p_148539_1_)
/*  792:     */   {
/*  793:1048 */     func_148544_a(p_148539_1_, true);
/*  794:     */   }
/*  795:     */   
/*  796:     */   public StatisticsFile func_148538_i(String p_148538_1_)
/*  797:     */   {
/*  798:1053 */     StatisticsFile var2 = (StatisticsFile)this.field_148547_k.get(p_148538_1_);
/*  799:1055 */     if (var2 == null)
/*  800:     */     {
/*  801:1057 */       var2 = new StatisticsFile(this.mcServer, new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats/" + p_148538_1_ + ".json"));
/*  802:1058 */       var2.func_150882_a();
/*  803:1059 */       this.field_148547_k.put(p_148538_1_, var2);
/*  804:     */     }
/*  805:1062 */     return var2;
/*  806:     */   }
/*  807:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.server.management.ServerConfigurationManager
 * JD-Core Version:    0.7.0.1
 */