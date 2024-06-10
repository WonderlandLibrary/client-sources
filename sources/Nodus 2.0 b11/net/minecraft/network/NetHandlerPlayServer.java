/*    1:     */ package net.minecraft.network;
/*    2:     */ 
/*    3:     */ import com.google.common.base.Charsets;
/*    4:     */ import com.google.common.collect.Lists;
/*    5:     */ import io.netty.buffer.Unpooled;
/*    6:     */ import io.netty.util.concurrent.Future;
/*    7:     */ import io.netty.util.concurrent.GenericFutureListener;
/*    8:     */ import java.io.ByteArrayInputStream;
/*    9:     */ import java.io.DataInputStream;
/*   10:     */ import java.io.IOException;
/*   11:     */ import java.util.ArrayList;
/*   12:     */ import java.util.Iterator;
/*   13:     */ import java.util.List;
/*   14:     */ import java.util.Random;
/*   15:     */ import java.util.concurrent.Callable;
/*   16:     */ import net.minecraft.block.Block;
/*   17:     */ import net.minecraft.block.material.Material;
/*   18:     */ import net.minecraft.command.ICommandManager;
/*   19:     */ import net.minecraft.command.server.CommandBlockLogic;
/*   20:     */ import net.minecraft.crash.CrashReport;
/*   21:     */ import net.minecraft.crash.CrashReportCategory;
/*   22:     */ import net.minecraft.entity.Entity;
/*   23:     */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*   24:     */ import net.minecraft.entity.item.EntityItem;
/*   25:     */ import net.minecraft.entity.item.EntityXPOrb;
/*   26:     */ import net.minecraft.entity.passive.EntityHorse;
/*   27:     */ import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
/*   28:     */ import net.minecraft.entity.player.EntityPlayerMP;
/*   29:     */ import net.minecraft.entity.player.InventoryPlayer;
/*   30:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   31:     */ import net.minecraft.entity.projectile.EntityArrow;
/*   32:     */ import net.minecraft.init.Items;
/*   33:     */ import net.minecraft.inventory.Container;
/*   34:     */ import net.minecraft.inventory.ContainerBeacon;
/*   35:     */ import net.minecraft.inventory.ContainerMerchant;
/*   36:     */ import net.minecraft.inventory.ContainerRepair;
/*   37:     */ import net.minecraft.inventory.Slot;
/*   38:     */ import net.minecraft.item.ItemEditableBook;
/*   39:     */ import net.minecraft.item.ItemStack;
/*   40:     */ import net.minecraft.item.ItemWritableBook;
/*   41:     */ import net.minecraft.nbt.NBTTagCompound;
/*   42:     */ import net.minecraft.nbt.NBTTagString;
/*   43:     */ import net.minecraft.network.play.INetHandlerPlayServer;
/*   44:     */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*   45:     */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*   46:     */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*   47:     */ import net.minecraft.network.play.client.C02PacketUseEntity.Action;
/*   48:     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*   49:     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*   50:     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*   51:     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*   52:     */ import net.minecraft.network.play.client.C0APacketAnimation;
/*   53:     */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*   54:     */ import net.minecraft.network.play.client.C0CPacketInput;
/*   55:     */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*   56:     */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*   57:     */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*   58:     */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*   59:     */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*   60:     */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*   61:     */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*   62:     */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*   63:     */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*   64:     */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*   65:     */ import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
/*   66:     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*   67:     */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*   68:     */ import net.minecraft.network.play.server.S02PacketChat;
/*   69:     */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*   70:     */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*   71:     */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*   72:     */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*   73:     */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*   74:     */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*   75:     */ import net.minecraft.profiler.Profiler;
/*   76:     */ import net.minecraft.server.MinecraftServer;
/*   77:     */ import net.minecraft.server.management.BanEntry;
/*   78:     */ import net.minecraft.server.management.BanList;
/*   79:     */ import net.minecraft.server.management.ItemInWorldManager;
/*   80:     */ import net.minecraft.server.management.ServerConfigurationManager;
/*   81:     */ import net.minecraft.stats.AchievementList;
/*   82:     */ import net.minecraft.stats.StatisticsFile;
/*   83:     */ import net.minecraft.tileentity.TileEntity;
/*   84:     */ import net.minecraft.tileentity.TileEntityBeacon;
/*   85:     */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*   86:     */ import net.minecraft.tileentity.TileEntitySign;
/*   87:     */ import net.minecraft.util.AxisAlignedBB;
/*   88:     */ import net.minecraft.util.ChatAllowedCharacters;
/*   89:     */ import net.minecraft.util.ChatComponentText;
/*   90:     */ import net.minecraft.util.ChatComponentTranslation;
/*   91:     */ import net.minecraft.util.ChatStyle;
/*   92:     */ import net.minecraft.util.EnumChatFormatting;
/*   93:     */ import net.minecraft.util.IChatComponent;
/*   94:     */ import net.minecraft.util.IntHashMap;
/*   95:     */ import net.minecraft.util.ReportedException;
/*   96:     */ import net.minecraft.world.World;
/*   97:     */ import net.minecraft.world.WorldServer;
/*   98:     */ import net.minecraft.world.storage.WorldInfo;
/*   99:     */ import org.apache.commons.lang3.StringUtils;
/*  100:     */ import org.apache.logging.log4j.LogManager;
/*  101:     */ import org.apache.logging.log4j.Logger;
/*  102:     */ 
/*  103:     */ public class NetHandlerPlayServer
/*  104:     */   implements INetHandlerPlayServer
/*  105:     */ {
/*  106:  90 */   private static final Logger logger = ;
/*  107:     */   public final NetworkManager netManager;
/*  108:     */   private final MinecraftServer serverController;
/*  109:     */   public EntityPlayerMP playerEntity;
/*  110:     */   private int networkTickCount;
/*  111:     */   private int floatingTickCount;
/*  112:     */   private boolean field_147366_g;
/*  113:     */   private int field_147378_h;
/*  114:     */   private long field_147379_i;
/*  115: 104 */   private static Random field_147376_j = new Random();
/*  116:     */   private long field_147377_k;
/*  117:     */   private int chatSpamThresholdCount;
/*  118:     */   private int field_147375_m;
/*  119: 113 */   private IntHashMap field_147372_n = new IntHashMap();
/*  120:     */   private double lastPosX;
/*  121:     */   private double lastPosY;
/*  122:     */   private double lastPosZ;
/*  123: 117 */   private boolean hasMoved = true;
/*  124:     */   private static final String __OBFID = "CL_00001452";
/*  125:     */   
/*  126:     */   public NetHandlerPlayServer(MinecraftServer par1MinecraftServer, NetworkManager par2INetworkManager, EntityPlayerMP par3EntityPlayerMP)
/*  127:     */   {
/*  128: 122 */     this.serverController = par1MinecraftServer;
/*  129: 123 */     this.netManager = par2INetworkManager;
/*  130: 124 */     par2INetworkManager.setNetHandler(this);
/*  131: 125 */     this.playerEntity = par3EntityPlayerMP;
/*  132: 126 */     par3EntityPlayerMP.playerNetServerHandler = this;
/*  133:     */   }
/*  134:     */   
/*  135:     */   public void onNetworkTick()
/*  136:     */   {
/*  137: 135 */     this.field_147366_g = false;
/*  138: 136 */     this.networkTickCount += 1;
/*  139: 137 */     this.serverController.theProfiler.startSection("keepAlive");
/*  140: 139 */     if (this.networkTickCount - this.field_147377_k > 40L)
/*  141:     */     {
/*  142: 141 */       this.field_147377_k = this.networkTickCount;
/*  143: 142 */       this.field_147379_i = func_147363_d();
/*  144: 143 */       this.field_147378_h = ((int)this.field_147379_i);
/*  145: 144 */       sendPacket(new S00PacketKeepAlive(this.field_147378_h));
/*  146:     */     }
/*  147: 147 */     if (this.chatSpamThresholdCount > 0) {
/*  148: 149 */       this.chatSpamThresholdCount -= 1;
/*  149:     */     }
/*  150: 152 */     if (this.field_147375_m > 0) {
/*  151: 154 */       this.field_147375_m -= 1;
/*  152:     */     }
/*  153: 157 */     this.serverController.theProfiler.endStartSection("playerTick");
/*  154: 158 */     this.serverController.theProfiler.endSection();
/*  155:     */   }
/*  156:     */   
/*  157:     */   public NetworkManager func_147362_b()
/*  158:     */   {
/*  159: 163 */     return this.netManager;
/*  160:     */   }
/*  161:     */   
/*  162:     */   public void kickPlayerFromServer(String p_147360_1_)
/*  163:     */   {
/*  164: 171 */     final ChatComponentText var2 = new ChatComponentText(p_147360_1_);
/*  165: 172 */     this.netManager.scheduleOutboundPacket(new S40PacketDisconnect(var2), new GenericFutureListener[] { new GenericFutureListener()
/*  166:     */     {
/*  167:     */       private static final String __OBFID = "CL_00001453";
/*  168:     */       
/*  169:     */       public void operationComplete(Future p_operationComplete_1_)
/*  170:     */       {
/*  171: 177 */         NetHandlerPlayServer.this.netManager.closeChannel(var2);
/*  172:     */       }
/*  173: 180 */     } });
/*  174: 181 */     this.netManager.disableAutoRead();
/*  175:     */   }
/*  176:     */   
/*  177:     */   public void processInput(C0CPacketInput p_147358_1_)
/*  178:     */   {
/*  179: 190 */     this.playerEntity.setEntityActionState(p_147358_1_.func_149620_c(), p_147358_1_.func_149616_d(), p_147358_1_.func_149618_e(), p_147358_1_.func_149617_f());
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void processPlayer(C03PacketPlayer p_147347_1_)
/*  183:     */   {
/*  184: 198 */     WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  185: 199 */     this.field_147366_g = true;
/*  186: 201 */     if (!this.playerEntity.playerConqueredTheEnd)
/*  187:     */     {
/*  188: 205 */       if (!this.hasMoved)
/*  189:     */       {
/*  190: 207 */         double var3 = p_147347_1_.func_149467_d() - this.lastPosY;
/*  191: 209 */         if ((p_147347_1_.func_149464_c() == this.lastPosX) && (var3 * var3 < 0.01D) && (p_147347_1_.func_149472_e() == this.lastPosZ)) {
/*  192: 211 */           this.hasMoved = true;
/*  193:     */         }
/*  194:     */       }
/*  195: 215 */       if (this.hasMoved)
/*  196:     */       {
/*  197: 221 */         if (this.playerEntity.ridingEntity != null)
/*  198:     */         {
/*  199: 223 */           float var34 = this.playerEntity.rotationYaw;
/*  200: 224 */           float var4 = this.playerEntity.rotationPitch;
/*  201: 225 */           this.playerEntity.ridingEntity.updateRiderPosition();
/*  202: 226 */           double var5 = this.playerEntity.posX;
/*  203: 227 */           double var7 = this.playerEntity.posY;
/*  204: 228 */           double var9 = this.playerEntity.posZ;
/*  205: 230 */           if (p_147347_1_.func_149463_k())
/*  206:     */           {
/*  207: 232 */             var34 = p_147347_1_.func_149462_g();
/*  208: 233 */             var4 = p_147347_1_.func_149470_h();
/*  209:     */           }
/*  210: 236 */           this.playerEntity.onGround = p_147347_1_.func_149465_i();
/*  211: 237 */           this.playerEntity.onUpdateEntity();
/*  212: 238 */           this.playerEntity.ySize = 0.0F;
/*  213: 239 */           this.playerEntity.setPositionAndRotation(var5, var7, var9, var34, var4);
/*  214: 241 */           if (this.playerEntity.ridingEntity != null) {
/*  215: 243 */             this.playerEntity.ridingEntity.updateRiderPosition();
/*  216:     */           }
/*  217: 246 */           this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*  218: 248 */           if (this.hasMoved)
/*  219:     */           {
/*  220: 250 */             this.lastPosX = this.playerEntity.posX;
/*  221: 251 */             this.lastPosY = this.playerEntity.posY;
/*  222: 252 */             this.lastPosZ = this.playerEntity.posZ;
/*  223:     */           }
/*  224: 255 */           var2.updateEntity(this.playerEntity);
/*  225: 256 */           return;
/*  226:     */         }
/*  227: 259 */         if (this.playerEntity.isPlayerSleeping())
/*  228:     */         {
/*  229: 261 */           this.playerEntity.onUpdateEntity();
/*  230: 262 */           this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*  231: 263 */           var2.updateEntity(this.playerEntity);
/*  232: 264 */           return;
/*  233:     */         }
/*  234: 267 */         double var3 = this.playerEntity.posY;
/*  235: 268 */         this.lastPosX = this.playerEntity.posX;
/*  236: 269 */         this.lastPosY = this.playerEntity.posY;
/*  237: 270 */         this.lastPosZ = this.playerEntity.posZ;
/*  238: 271 */         double var5 = this.playerEntity.posX;
/*  239: 272 */         double var7 = this.playerEntity.posY;
/*  240: 273 */         double var9 = this.playerEntity.posZ;
/*  241: 274 */         float var11 = this.playerEntity.rotationYaw;
/*  242: 275 */         float var12 = this.playerEntity.rotationPitch;
/*  243: 277 */         if ((p_147347_1_.func_149466_j()) && (p_147347_1_.func_149467_d() == -999.0D) && (p_147347_1_.func_149471_f() == -999.0D)) {
/*  244: 279 */           p_147347_1_.func_149469_a(false);
/*  245:     */         }
/*  246: 284 */         if (p_147347_1_.func_149466_j())
/*  247:     */         {
/*  248: 286 */           var5 = p_147347_1_.func_149464_c();
/*  249: 287 */           var7 = p_147347_1_.func_149467_d();
/*  250: 288 */           var9 = p_147347_1_.func_149472_e();
/*  251: 289 */           double var13 = p_147347_1_.func_149471_f() - p_147347_1_.func_149467_d();
/*  252: 291 */           if ((!this.playerEntity.isPlayerSleeping()) && ((var13 > 1.65D) || (var13 < 0.1D)))
/*  253:     */           {
/*  254: 293 */             kickPlayerFromServer("Illegal stance");
/*  255: 294 */             logger.warn(this.playerEntity.getCommandSenderName() + " had an illegal stance: " + var13);
/*  256: 295 */             return;
/*  257:     */           }
/*  258: 298 */           if ((Math.abs(p_147347_1_.func_149464_c()) > 32000000.0D) || (Math.abs(p_147347_1_.func_149472_e()) > 32000000.0D))
/*  259:     */           {
/*  260: 300 */             kickPlayerFromServer("Illegal position");
/*  261: 301 */             return;
/*  262:     */           }
/*  263:     */         }
/*  264: 305 */         if (p_147347_1_.func_149463_k())
/*  265:     */         {
/*  266: 307 */           var11 = p_147347_1_.func_149462_g();
/*  267: 308 */           var12 = p_147347_1_.func_149470_h();
/*  268:     */         }
/*  269: 311 */         this.playerEntity.onUpdateEntity();
/*  270: 312 */         this.playerEntity.ySize = 0.0F;
/*  271: 313 */         this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var11, var12);
/*  272: 315 */         if (!this.hasMoved) {
/*  273: 317 */           return;
/*  274:     */         }
/*  275: 320 */         double var13 = var5 - this.playerEntity.posX;
/*  276: 321 */         double var15 = var7 - this.playerEntity.posY;
/*  277: 322 */         double var17 = var9 - this.playerEntity.posZ;
/*  278: 323 */         double var19 = Math.min(Math.abs(var13), Math.abs(this.playerEntity.motionX));
/*  279: 324 */         double var21 = Math.min(Math.abs(var15), Math.abs(this.playerEntity.motionY));
/*  280: 325 */         double var23 = Math.min(Math.abs(var17), Math.abs(this.playerEntity.motionZ));
/*  281: 326 */         double var25 = var19 * var19 + var21 * var21 + var23 * var23;
/*  282: 328 */         if ((var25 > 100.0D) && ((!this.serverController.isSinglePlayer()) || (!this.serverController.getServerOwner().equals(this.playerEntity.getCommandSenderName()))))
/*  283:     */         {
/*  284: 330 */           logger.warn(this.playerEntity.getCommandSenderName() + " moved too quickly! " + var13 + "," + var15 + "," + var17 + " (" + var19 + ", " + var21 + ", " + var23 + ")");
/*  285: 331 */           setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*  286: 332 */           return;
/*  287:     */         }
/*  288: 335 */         float var27 = 0.0625F;
/*  289: 336 */         boolean var28 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().contract(var27, var27, var27)).isEmpty();
/*  290: 338 */         if ((this.playerEntity.onGround) && (!p_147347_1_.func_149465_i()) && (var15 > 0.0D)) {
/*  291: 340 */           this.playerEntity.jump();
/*  292:     */         }
/*  293: 343 */         this.playerEntity.moveEntity(var13, var15, var17);
/*  294: 344 */         this.playerEntity.onGround = p_147347_1_.func_149465_i();
/*  295: 345 */         this.playerEntity.addMovementStat(var13, var15, var17);
/*  296: 346 */         double var29 = var15;
/*  297: 347 */         var13 = var5 - this.playerEntity.posX;
/*  298: 348 */         var15 = var7 - this.playerEntity.posY;
/*  299: 350 */         if ((var15 > -0.5D) || (var15 < 0.5D)) {
/*  300: 352 */           var15 = 0.0D;
/*  301:     */         }
/*  302: 355 */         var17 = var9 - this.playerEntity.posZ;
/*  303: 356 */         var25 = var13 * var13 + var15 * var15 + var17 * var17;
/*  304: 357 */         boolean var31 = false;
/*  305: 359 */         if ((var25 > 0.0625D) && (!this.playerEntity.isPlayerSleeping()) && (!this.playerEntity.theItemInWorldManager.isCreative()))
/*  306:     */         {
/*  307: 361 */           var31 = true;
/*  308: 362 */           logger.warn(this.playerEntity.getCommandSenderName() + " moved wrongly!");
/*  309:     */         }
/*  310: 365 */         this.playerEntity.setPositionAndRotation(var5, var7, var9, var11, var12);
/*  311: 366 */         boolean var32 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.boundingBox.copy().contract(var27, var27, var27)).isEmpty();
/*  312: 368 */         if ((var28) && ((var31) || (!var32)) && (!this.playerEntity.isPlayerSleeping()))
/*  313:     */         {
/*  314: 370 */           setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, var11, var12);
/*  315: 371 */           return;
/*  316:     */         }
/*  317: 374 */         AxisAlignedBB var33 = this.playerEntity.boundingBox.copy().expand(var27, var27, var27).addCoord(0.0D, -0.55D, 0.0D);
/*  318: 376 */         if ((!this.serverController.isFlightAllowed()) && (!this.playerEntity.theItemInWorldManager.isCreative()) && (!var2.checkBlockCollision(var33)))
/*  319:     */         {
/*  320: 378 */           if (var29 >= -0.03125D)
/*  321:     */           {
/*  322: 380 */             this.floatingTickCount += 1;
/*  323: 382 */             if (this.floatingTickCount > 80)
/*  324:     */             {
/*  325: 384 */               logger.warn(this.playerEntity.getCommandSenderName() + " was kicked for floating too long!");
/*  326: 385 */               kickPlayerFromServer("Flying is not enabled on this server");
/*  327:     */             }
/*  328:     */           }
/*  329:     */         }
/*  330:     */         else {
/*  331: 392 */           this.floatingTickCount = 0;
/*  332:     */         }
/*  333: 395 */         this.playerEntity.onGround = p_147347_1_.func_149465_i();
/*  334: 396 */         this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*  335: 397 */         this.playerEntity.handleFalling(this.playerEntity.posY - var3, p_147347_1_.func_149465_i());
/*  336:     */       }
/*  337: 399 */       else if (this.networkTickCount % 20 == 0)
/*  338:     */       {
/*  339: 401 */         setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*  340:     */       }
/*  341:     */     }
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void setPlayerLocation(double p_147364_1_, double p_147364_3_, double p_147364_5_, float p_147364_7_, float p_147364_8_)
/*  345:     */   {
/*  346: 408 */     this.hasMoved = false;
/*  347: 409 */     this.lastPosX = p_147364_1_;
/*  348: 410 */     this.lastPosY = p_147364_3_;
/*  349: 411 */     this.lastPosZ = p_147364_5_;
/*  350: 412 */     this.playerEntity.setPositionAndRotation(p_147364_1_, p_147364_3_, p_147364_5_, p_147364_7_, p_147364_8_);
/*  351: 413 */     this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(p_147364_1_, p_147364_3_ + 1.620000004768372D, p_147364_5_, p_147364_7_, p_147364_8_, false));
/*  352:     */   }
/*  353:     */   
/*  354:     */   public void processPlayerDigging(C07PacketPlayerDigging p_147345_1_)
/*  355:     */   {
/*  356: 423 */     WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  357: 424 */     this.playerEntity.func_143004_u();
/*  358: 426 */     if (p_147345_1_.func_149506_g() == 4)
/*  359:     */     {
/*  360: 428 */       this.playerEntity.dropOneItem(false);
/*  361:     */     }
/*  362: 430 */     else if (p_147345_1_.func_149506_g() == 3)
/*  363:     */     {
/*  364: 432 */       this.playerEntity.dropOneItem(true);
/*  365:     */     }
/*  366: 434 */     else if (p_147345_1_.func_149506_g() == 5)
/*  367:     */     {
/*  368: 436 */       this.playerEntity.stopUsingItem();
/*  369:     */     }
/*  370:     */     else
/*  371:     */     {
/*  372: 440 */       boolean var3 = false;
/*  373: 442 */       if (p_147345_1_.func_149506_g() == 0) {
/*  374: 444 */         var3 = true;
/*  375:     */       }
/*  376: 447 */       if (p_147345_1_.func_149506_g() == 1) {
/*  377: 449 */         var3 = true;
/*  378:     */       }
/*  379: 452 */       if (p_147345_1_.func_149506_g() == 2) {
/*  380: 454 */         var3 = true;
/*  381:     */       }
/*  382: 457 */       int var4 = p_147345_1_.func_149505_c();
/*  383: 458 */       int var5 = p_147345_1_.func_149503_d();
/*  384: 459 */       int var6 = p_147345_1_.func_149502_e();
/*  385: 461 */       if (var3)
/*  386:     */       {
/*  387: 463 */         double var7 = this.playerEntity.posX - (var4 + 0.5D);
/*  388: 464 */         double var9 = this.playerEntity.posY - (var5 + 0.5D) + 1.5D;
/*  389: 465 */         double var11 = this.playerEntity.posZ - (var6 + 0.5D);
/*  390: 466 */         double var13 = var7 * var7 + var9 * var9 + var11 * var11;
/*  391: 468 */         if (var13 > 36.0D) {
/*  392: 470 */           return;
/*  393:     */         }
/*  394: 473 */         if (var5 >= this.serverController.getBuildLimit()) {
/*  395: 475 */           return;
/*  396:     */         }
/*  397:     */       }
/*  398: 479 */       if (p_147345_1_.func_149506_g() == 0)
/*  399:     */       {
/*  400: 481 */         if (!this.serverController.isBlockProtected(var2, var4, var5, var6, this.playerEntity)) {
/*  401: 483 */           this.playerEntity.theItemInWorldManager.onBlockClicked(var4, var5, var6, p_147345_1_.func_149501_f());
/*  402:     */         } else {
/*  403: 487 */           this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
/*  404:     */         }
/*  405:     */       }
/*  406: 490 */       else if (p_147345_1_.func_149506_g() == 2)
/*  407:     */       {
/*  408: 492 */         this.playerEntity.theItemInWorldManager.uncheckedTryHarvestBlock(var4, var5, var6);
/*  409: 494 */         if (var2.getBlock(var4, var5, var6).getMaterial() != Material.air) {
/*  410: 496 */           this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
/*  411:     */         }
/*  412:     */       }
/*  413: 499 */       else if (p_147345_1_.func_149506_g() == 1)
/*  414:     */       {
/*  415: 501 */         this.playerEntity.theItemInWorldManager.cancelDestroyingBlock(var4, var5, var6);
/*  416: 503 */         if (var2.getBlock(var4, var5, var6).getMaterial() != Material.air) {
/*  417: 505 */           this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var4, var5, var6, var2));
/*  418:     */         }
/*  419:     */       }
/*  420:     */     }
/*  421:     */   }
/*  422:     */   
/*  423:     */   public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement p_147346_1_)
/*  424:     */   {
/*  425: 516 */     WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  426: 517 */     ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
/*  427: 518 */     boolean var4 = false;
/*  428: 519 */     int var5 = p_147346_1_.func_149576_c();
/*  429: 520 */     int var6 = p_147346_1_.func_149571_d();
/*  430: 521 */     int var7 = p_147346_1_.func_149570_e();
/*  431: 522 */     int var8 = p_147346_1_.func_149568_f();
/*  432: 523 */     this.playerEntity.func_143004_u();
/*  433: 525 */     if (p_147346_1_.func_149568_f() == 255)
/*  434:     */     {
/*  435: 527 */       if (var3 == null) {
/*  436: 529 */         return;
/*  437:     */       }
/*  438: 532 */       this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, var2, var3);
/*  439:     */     }
/*  440: 534 */     else if ((p_147346_1_.func_149571_d() >= this.serverController.getBuildLimit() - 1) && ((p_147346_1_.func_149568_f() == 1) || (p_147346_1_.func_149571_d() >= this.serverController.getBuildLimit())))
/*  441:     */     {
/*  442: 536 */       ChatComponentTranslation var9 = new ChatComponentTranslation("build.tooHigh", new Object[] { Integer.valueOf(this.serverController.getBuildLimit()) });
/*  443: 537 */       var9.getChatStyle().setColor(EnumChatFormatting.RED);
/*  444: 538 */       this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(var9));
/*  445: 539 */       var4 = true;
/*  446:     */     }
/*  447:     */     else
/*  448:     */     {
/*  449: 543 */       if ((this.hasMoved) && (this.playerEntity.getDistanceSq(var5 + 0.5D, var6 + 0.5D, var7 + 0.5D) < 64.0D) && (!this.serverController.isBlockProtected(var2, var5, var6, var7, this.playerEntity))) {
/*  450: 545 */         this.playerEntity.theItemInWorldManager.activateBlockOrUseItem(this.playerEntity, var2, var3, var5, var6, var7, var8, p_147346_1_.func_149573_h(), p_147346_1_.func_149569_i(), p_147346_1_.func_149575_j());
/*  451:     */       }
/*  452: 548 */       var4 = true;
/*  453:     */     }
/*  454: 551 */     if (var4)
/*  455:     */     {
/*  456: 553 */       this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var6, var7, var2));
/*  457: 555 */       if (var8 == 0) {
/*  458: 557 */         var6--;
/*  459:     */       }
/*  460: 560 */       if (var8 == 1) {
/*  461: 562 */         var6++;
/*  462:     */       }
/*  463: 565 */       if (var8 == 2) {
/*  464: 567 */         var7--;
/*  465:     */       }
/*  466: 570 */       if (var8 == 3) {
/*  467: 572 */         var7++;
/*  468:     */       }
/*  469: 575 */       if (var8 == 4) {
/*  470: 577 */         var5--;
/*  471:     */       }
/*  472: 580 */       if (var8 == 5) {
/*  473: 582 */         var5++;
/*  474:     */       }
/*  475: 585 */       this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var5, var6, var7, var2));
/*  476:     */     }
/*  477: 588 */     var3 = this.playerEntity.inventory.getCurrentItem();
/*  478: 590 */     if ((var3 != null) && (var3.stackSize == 0))
/*  479:     */     {
/*  480: 592 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
/*  481: 593 */       var3 = null;
/*  482:     */     }
/*  483: 596 */     if ((var3 == null) || (var3.getMaxItemUseDuration() == 0))
/*  484:     */     {
/*  485: 598 */       this.playerEntity.isChangingQuantityOnly = true;
/*  486: 599 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
/*  487: 600 */       Slot var10 = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
/*  488: 601 */       this.playerEntity.openContainer.detectAndSendChanges();
/*  489: 602 */       this.playerEntity.isChangingQuantityOnly = false;
/*  490: 604 */       if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), p_147346_1_.func_149574_g())) {
/*  491: 606 */         sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, var10.slotNumber, this.playerEntity.inventory.getCurrentItem()));
/*  492:     */       }
/*  493:     */     }
/*  494:     */   }
/*  495:     */   
/*  496:     */   public void onDisconnect(IChatComponent p_147231_1_)
/*  497:     */   {
/*  498: 616 */     logger.info(this.playerEntity.getCommandSenderName() + " lost connection: " + p_147231_1_);
/*  499: 617 */     this.serverController.func_147132_au();
/*  500: 618 */     ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.func_145748_c_() });
/*  501: 619 */     var2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
/*  502: 620 */     this.serverController.getConfigurationManager().func_148539_a(var2);
/*  503: 621 */     this.playerEntity.mountEntityAndWakeUp();
/*  504: 622 */     this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
/*  505: 624 */     if ((this.serverController.isSinglePlayer()) && (this.playerEntity.getCommandSenderName().equals(this.serverController.getServerOwner())))
/*  506:     */     {
/*  507: 626 */       logger.info("Stopping singleplayer server as player logged out");
/*  508: 627 */       this.serverController.initiateShutdown();
/*  509:     */     }
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void sendPacket(final Packet p_147359_1_)
/*  513:     */   {
/*  514: 633 */     if ((p_147359_1_ instanceof S02PacketChat))
/*  515:     */     {
/*  516: 635 */       S02PacketChat var2 = (S02PacketChat)p_147359_1_;
/*  517: 636 */       EntityPlayer.EnumChatVisibility var3 = this.playerEntity.func_147096_v();
/*  518: 638 */       if (var3 == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*  519: 640 */         return;
/*  520:     */       }
/*  521: 643 */       if ((var3 == EntityPlayer.EnumChatVisibility.SYSTEM) && (!var2.func_148916_d())) {
/*  522: 645 */         return;
/*  523:     */       }
/*  524:     */     }
/*  525:     */     try
/*  526:     */     {
/*  527: 651 */       this.netManager.scheduleOutboundPacket(p_147359_1_, new GenericFutureListener[0]);
/*  528:     */     }
/*  529:     */     catch (Throwable var5)
/*  530:     */     {
/*  531: 655 */       CrashReport var6 = CrashReport.makeCrashReport(var5, "Sending packet");
/*  532: 656 */       CrashReportCategory var4 = var6.makeCategory("Packet being sent");
/*  533: 657 */       var4.addCrashSectionCallable("Packet class", new Callable()
/*  534:     */       {
/*  535:     */         private static final String __OBFID = "CL_00001454";
/*  536:     */         
/*  537:     */         public String call()
/*  538:     */         {
/*  539: 662 */           return p_147359_1_.getClass().getCanonicalName();
/*  540:     */         }
/*  541: 664 */       });
/*  542: 665 */       throw new ReportedException(var6);
/*  543:     */     }
/*  544:     */   }
/*  545:     */   
/*  546:     */   public void processHeldItemChange(C09PacketHeldItemChange p_147355_1_)
/*  547:     */   {
/*  548: 674 */     if ((p_147355_1_.func_149614_c() >= 0) && (p_147355_1_.func_149614_c() < InventoryPlayer.getHotbarSize()))
/*  549:     */     {
/*  550: 676 */       this.playerEntity.inventory.currentItem = p_147355_1_.func_149614_c();
/*  551: 677 */       this.playerEntity.func_143004_u();
/*  552:     */     }
/*  553:     */     else
/*  554:     */     {
/*  555: 681 */       logger.warn(this.playerEntity.getCommandSenderName() + " tried to set an invalid carried item");
/*  556:     */     }
/*  557:     */   }
/*  558:     */   
/*  559:     */   public void processChatMessage(C01PacketChatMessage p_147354_1_)
/*  560:     */   {
/*  561: 690 */     if (this.playerEntity.func_147096_v() == EntityPlayer.EnumChatVisibility.HIDDEN)
/*  562:     */     {
/*  563: 692 */       ChatComponentTranslation var4 = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
/*  564: 693 */       var4.getChatStyle().setColor(EnumChatFormatting.RED);
/*  565: 694 */       sendPacket(new S02PacketChat(var4));
/*  566:     */     }
/*  567:     */     else
/*  568:     */     {
/*  569: 698 */       this.playerEntity.func_143004_u();
/*  570: 699 */       String var2 = p_147354_1_.func_149439_c();
/*  571: 700 */       var2 = StringUtils.normalizeSpace(var2);
/*  572: 702 */       for (int var3 = 0; var3 < var2.length(); var3++) {
/*  573: 704 */         if (!ChatAllowedCharacters.isAllowedCharacter(var2.charAt(var3)))
/*  574:     */         {
/*  575: 706 */           kickPlayerFromServer("Illegal characters in chat");
/*  576: 707 */           return;
/*  577:     */         }
/*  578:     */       }
/*  579: 711 */       if (var2.startsWith("/"))
/*  580:     */       {
/*  581: 713 */         handleSlashCommand(var2);
/*  582:     */       }
/*  583:     */       else
/*  584:     */       {
/*  585: 717 */         ChatComponentTranslation var5 = new ChatComponentTranslation("chat.type.text", new Object[] { this.playerEntity.func_145748_c_(), var2 });
/*  586: 718 */         this.serverController.getConfigurationManager().func_148544_a(var5, false);
/*  587:     */       }
/*  588: 721 */       this.chatSpamThresholdCount += 20;
/*  589: 723 */       if ((this.chatSpamThresholdCount > 200) && (!this.serverController.getConfigurationManager().isPlayerOpped(this.playerEntity.getCommandSenderName()))) {
/*  590: 725 */         kickPlayerFromServer("disconnect.spam");
/*  591:     */       }
/*  592:     */     }
/*  593:     */   }
/*  594:     */   
/*  595:     */   private void handleSlashCommand(String p_147361_1_)
/*  596:     */   {
/*  597: 735 */     this.serverController.getCommandManager().executeCommand(this.playerEntity, p_147361_1_);
/*  598:     */   }
/*  599:     */   
/*  600:     */   public void processAnimation(C0APacketAnimation p_147350_1_)
/*  601:     */   {
/*  602: 743 */     this.playerEntity.func_143004_u();
/*  603: 745 */     if (p_147350_1_.func_149421_d() == 1) {
/*  604: 747 */       this.playerEntity.swingItem();
/*  605:     */     }
/*  606:     */   }
/*  607:     */   
/*  608:     */   public void processEntityAction(C0BPacketEntityAction p_147357_1_)
/*  609:     */   {
/*  610: 757 */     this.playerEntity.func_143004_u();
/*  611: 759 */     if (p_147357_1_.func_149513_d() == 1)
/*  612:     */     {
/*  613: 761 */       this.playerEntity.setSneaking(true);
/*  614:     */     }
/*  615: 763 */     else if (p_147357_1_.func_149513_d() == 2)
/*  616:     */     {
/*  617: 765 */       this.playerEntity.setSneaking(false);
/*  618:     */     }
/*  619: 767 */     else if (p_147357_1_.func_149513_d() == 4)
/*  620:     */     {
/*  621: 769 */       this.playerEntity.setSprinting(true);
/*  622:     */     }
/*  623: 771 */     else if (p_147357_1_.func_149513_d() == 5)
/*  624:     */     {
/*  625: 773 */       this.playerEntity.setSprinting(false);
/*  626:     */     }
/*  627: 775 */     else if (p_147357_1_.func_149513_d() == 3)
/*  628:     */     {
/*  629: 777 */       this.playerEntity.wakeUpPlayer(false, true, true);
/*  630: 778 */       this.hasMoved = false;
/*  631:     */     }
/*  632: 780 */     else if (p_147357_1_.func_149513_d() == 6)
/*  633:     */     {
/*  634: 782 */       if ((this.playerEntity.ridingEntity != null) && ((this.playerEntity.ridingEntity instanceof EntityHorse))) {
/*  635: 784 */         ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(p_147357_1_.func_149512_e());
/*  636:     */       }
/*  637:     */     }
/*  638: 787 */     else if ((p_147357_1_.func_149513_d() == 7) && (this.playerEntity.ridingEntity != null) && ((this.playerEntity.ridingEntity instanceof EntityHorse)))
/*  639:     */     {
/*  640: 789 */       ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
/*  641:     */     }
/*  642:     */   }
/*  643:     */   
/*  644:     */   public void processUseEntity(C02PacketUseEntity p_147340_1_)
/*  645:     */   {
/*  646: 799 */     WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  647: 800 */     Entity var3 = p_147340_1_.func_149564_a(var2);
/*  648: 801 */     this.playerEntity.func_143004_u();
/*  649: 803 */     if (var3 != null)
/*  650:     */     {
/*  651: 805 */       boolean var4 = this.playerEntity.canEntityBeSeen(var3);
/*  652: 806 */       double var5 = 36.0D;
/*  653: 808 */       if (!var4) {
/*  654: 810 */         var5 = 9.0D;
/*  655:     */       }
/*  656: 813 */       if (this.playerEntity.getDistanceSqToEntity(var3) < var5) {
/*  657: 815 */         if (p_147340_1_.func_149565_c() == C02PacketUseEntity.Action.INTERACT)
/*  658:     */         {
/*  659: 817 */           this.playerEntity.interactWith(var3);
/*  660:     */         }
/*  661: 819 */         else if (p_147340_1_.func_149565_c() == C02PacketUseEntity.Action.ATTACK)
/*  662:     */         {
/*  663: 821 */           if (((var3 instanceof EntityItem)) || ((var3 instanceof EntityXPOrb)) || ((var3 instanceof EntityArrow)) || (var3 == this.playerEntity))
/*  664:     */           {
/*  665: 823 */             kickPlayerFromServer("Attempting to attack an invalid entity");
/*  666: 824 */             this.serverController.logWarning("Player " + this.playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
/*  667: 825 */             return;
/*  668:     */           }
/*  669: 828 */           this.playerEntity.attackTargetEntityWithCurrentItem(var3);
/*  670:     */         }
/*  671:     */       }
/*  672:     */     }
/*  673:     */   }
/*  674:     */   
/*  675:     */   public void processClientStatus(C16PacketClientStatus p_147342_1_)
/*  676:     */   {
/*  677: 840 */     this.playerEntity.func_143004_u();
/*  678: 841 */     C16PacketClientStatus.EnumState var2 = p_147342_1_.func_149435_c();
/*  679: 843 */     switch (SwitchEnumState.field_151290_a[var2.ordinal()])
/*  680:     */     {
/*  681:     */     case 1: 
/*  682: 846 */       if (this.playerEntity.playerConqueredTheEnd)
/*  683:     */       {
/*  684: 848 */         this.playerEntity = this.serverController.getConfigurationManager().respawnPlayer(this.playerEntity, 0, true);
/*  685:     */       }
/*  686: 850 */       else if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled())
/*  687:     */       {
/*  688: 852 */         if ((this.serverController.isSinglePlayer()) && (this.playerEntity.getCommandSenderName().equals(this.serverController.getServerOwner())))
/*  689:     */         {
/*  690: 854 */           this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*  691: 855 */           this.serverController.deleteWorldAndStopServer();
/*  692:     */         }
/*  693:     */         else
/*  694:     */         {
/*  695: 859 */           BanEntry var3 = new BanEntry(this.playerEntity.getCommandSenderName());
/*  696: 860 */           var3.setBanReason("Death in Hardcore");
/*  697: 861 */           this.serverController.getConfigurationManager().getBannedPlayers().put(var3);
/*  698: 862 */           this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*  699:     */         }
/*  700:     */       }
/*  701:     */       else
/*  702:     */       {
/*  703: 867 */         if (this.playerEntity.getHealth() > 0.0F) {
/*  704: 869 */           return;
/*  705:     */         }
/*  706: 872 */         this.playerEntity = this.serverController.getConfigurationManager().respawnPlayer(this.playerEntity, 0, false);
/*  707:     */       }
/*  708: 875 */       break;
/*  709:     */     case 2: 
/*  710: 878 */       this.playerEntity.func_147099_x().func_150876_a(this.playerEntity);
/*  711: 879 */       break;
/*  712:     */     case 3: 
/*  713: 882 */       this.playerEntity.triggerAchievement(AchievementList.openInventory);
/*  714:     */     }
/*  715:     */   }
/*  716:     */   
/*  717:     */   public void processCloseWindow(C0DPacketCloseWindow p_147356_1_)
/*  718:     */   {
/*  719: 891 */     this.playerEntity.closeContainer();
/*  720:     */   }
/*  721:     */   
/*  722:     */   public void processClickWindow(C0EPacketClickWindow p_147351_1_)
/*  723:     */   {
/*  724: 901 */     this.playerEntity.func_143004_u();
/*  725: 903 */     if ((this.playerEntity.openContainer.windowId == p_147351_1_.func_149548_c()) && (this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)))
/*  726:     */     {
/*  727: 905 */       ItemStack var2 = this.playerEntity.openContainer.slotClick(p_147351_1_.func_149544_d(), p_147351_1_.func_149543_e(), p_147351_1_.func_149542_h(), this.playerEntity);
/*  728: 907 */       if (ItemStack.areItemStacksEqual(p_147351_1_.func_149546_g(), var2))
/*  729:     */       {
/*  730: 909 */         this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), true));
/*  731: 910 */         this.playerEntity.isChangingQuantityOnly = true;
/*  732: 911 */         this.playerEntity.openContainer.detectAndSendChanges();
/*  733: 912 */         this.playerEntity.updateHeldItem();
/*  734: 913 */         this.playerEntity.isChangingQuantityOnly = false;
/*  735:     */       }
/*  736:     */       else
/*  737:     */       {
/*  738: 917 */         this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, Short.valueOf(p_147351_1_.func_149547_f()));
/*  739: 918 */         this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(p_147351_1_.func_149548_c(), p_147351_1_.func_149547_f(), false));
/*  740: 919 */         this.playerEntity.openContainer.setPlayerIsPresent(this.playerEntity, false);
/*  741: 920 */         ArrayList var3 = new ArrayList();
/*  742: 922 */         for (int var4 = 0; var4 < this.playerEntity.openContainer.inventorySlots.size(); var4++) {
/*  743: 924 */           var3.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(var4)).getStack());
/*  744:     */         }
/*  745: 927 */         this.playerEntity.sendContainerAndContentsToPlayer(this.playerEntity.openContainer, var3);
/*  746:     */       }
/*  747:     */     }
/*  748:     */   }
/*  749:     */   
/*  750:     */   public void processEnchantItem(C11PacketEnchantItem p_147338_1_)
/*  751:     */   {
/*  752: 938 */     this.playerEntity.func_143004_u();
/*  753: 940 */     if ((this.playerEntity.openContainer.windowId == p_147338_1_.func_149539_c()) && (this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity)))
/*  754:     */     {
/*  755: 942 */       this.playerEntity.openContainer.enchantItem(this.playerEntity, p_147338_1_.func_149537_d());
/*  756: 943 */       this.playerEntity.openContainer.detectAndSendChanges();
/*  757:     */     }
/*  758:     */   }
/*  759:     */   
/*  760:     */   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction p_147344_1_)
/*  761:     */   {
/*  762: 952 */     if (this.playerEntity.theItemInWorldManager.isCreative())
/*  763:     */     {
/*  764: 954 */       boolean var2 = p_147344_1_.func_149627_c() < 0;
/*  765: 955 */       ItemStack var3 = p_147344_1_.func_149625_d();
/*  766: 956 */       boolean var4 = (p_147344_1_.func_149627_c() >= 1) && (p_147344_1_.func_149627_c() < 36 + InventoryPlayer.getHotbarSize());
/*  767: 957 */       boolean var5 = (var3 == null) || (var3.getItem() != null);
/*  768: 958 */       boolean var6 = (var3 == null) || ((var3.getItemDamage() >= 0) && (var3.stackSize <= 64) && (var3.stackSize > 0));
/*  769: 960 */       if ((var4) && (var5) && (var6))
/*  770:     */       {
/*  771: 962 */         if (var3 == null) {
/*  772: 964 */           this.playerEntity.inventoryContainer.putStackInSlot(p_147344_1_.func_149627_c(), null);
/*  773:     */         } else {
/*  774: 968 */           this.playerEntity.inventoryContainer.putStackInSlot(p_147344_1_.func_149627_c(), var3);
/*  775:     */         }
/*  776: 971 */         this.playerEntity.inventoryContainer.setPlayerIsPresent(this.playerEntity, true);
/*  777:     */       }
/*  778: 973 */       else if ((var2) && (var5) && (var6) && (this.field_147375_m < 200))
/*  779:     */       {
/*  780: 975 */         this.field_147375_m += 20;
/*  781: 976 */         EntityItem var7 = this.playerEntity.dropPlayerItemWithRandomChoice(var3, true);
/*  782: 978 */         if (var7 != null) {
/*  783: 980 */           var7.setAgeToCreativeDespawnTime();
/*  784:     */         }
/*  785:     */       }
/*  786:     */     }
/*  787:     */   }
/*  788:     */   
/*  789:     */   public void processConfirmTransaction(C0FPacketConfirmTransaction p_147339_1_)
/*  790:     */   {
/*  791: 993 */     Short var2 = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
/*  792: 995 */     if ((var2 != null) && (p_147339_1_.func_149533_d() == var2.shortValue()) && (this.playerEntity.openContainer.windowId == p_147339_1_.func_149532_c()) && (!this.playerEntity.openContainer.isPlayerNotUsingContainer(this.playerEntity))) {
/*  793: 997 */       this.playerEntity.openContainer.setPlayerIsPresent(this.playerEntity, true);
/*  794:     */     }
/*  795:     */   }
/*  796:     */   
/*  797:     */   public void processUpdateSign(C12PacketUpdateSign p_147343_1_)
/*  798:     */   {
/*  799:1003 */     this.playerEntity.func_143004_u();
/*  800:1004 */     WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  801:1006 */     if (var2.blockExists(p_147343_1_.func_149588_c(), p_147343_1_.func_149586_d(), p_147343_1_.func_149585_e()))
/*  802:     */     {
/*  803:1008 */       TileEntity var3 = var2.getTileEntity(p_147343_1_.func_149588_c(), p_147343_1_.func_149586_d(), p_147343_1_.func_149585_e());
/*  804:1010 */       if ((var3 instanceof TileEntitySign))
/*  805:     */       {
/*  806:1012 */         TileEntitySign var4 = (TileEntitySign)var3;
/*  807:1014 */         if ((!var4.func_145914_a()) || (var4.func_145911_b() != this.playerEntity))
/*  808:     */         {
/*  809:1016 */           this.serverController.logWarning("Player " + this.playerEntity.getCommandSenderName() + " just tried to change non-editable sign");
/*  810:1017 */           return;
/*  811:     */         }
/*  812:     */       }
/*  813:1024 */       for (int var8 = 0; var8 < 4; var8++)
/*  814:     */       {
/*  815:1026 */         boolean var5 = true;
/*  816:1028 */         if (p_147343_1_.func_149589_f()[var8].length() > 15) {
/*  817:1030 */           var5 = false;
/*  818:     */         } else {
/*  819:1034 */           for (int var6 = 0; var6 < p_147343_1_.func_149589_f()[var8].length(); var6++) {
/*  820:1036 */             if (!ChatAllowedCharacters.isAllowedCharacter(p_147343_1_.func_149589_f()[var8].charAt(var6))) {
/*  821:1038 */               var5 = false;
/*  822:     */             }
/*  823:     */           }
/*  824:     */         }
/*  825:1043 */         if (!var5) {
/*  826:1045 */           p_147343_1_.func_149589_f()[var8] = "!?";
/*  827:     */         }
/*  828:     */       }
/*  829:1049 */       if ((var3 instanceof TileEntitySign))
/*  830:     */       {
/*  831:1051 */         var8 = p_147343_1_.func_149588_c();
/*  832:1052 */         int var9 = p_147343_1_.func_149586_d();
/*  833:1053 */         int var6 = p_147343_1_.func_149585_e();
/*  834:1054 */         TileEntitySign var7 = (TileEntitySign)var3;
/*  835:1055 */         System.arraycopy(p_147343_1_.func_149589_f(), 0, var7.field_145915_a, 0, 4);
/*  836:1056 */         var7.onInventoryChanged();
/*  837:1057 */         var2.func_147471_g(var8, var9, var6);
/*  838:     */       }
/*  839:     */     }
/*  840:     */   }
/*  841:     */   
/*  842:     */   public void processKeepAlive(C00PacketKeepAlive p_147353_1_)
/*  843:     */   {
/*  844:1067 */     if (p_147353_1_.func_149460_c() == this.field_147378_h)
/*  845:     */     {
/*  846:1069 */       int var2 = (int)(func_147363_d() - this.field_147379_i);
/*  847:1070 */       this.playerEntity.ping = ((this.playerEntity.ping * 3 + var2) / 4);
/*  848:     */     }
/*  849:     */   }
/*  850:     */   
/*  851:     */   private long func_147363_d()
/*  852:     */   {
/*  853:1076 */     return System.nanoTime() / 1000000L;
/*  854:     */   }
/*  855:     */   
/*  856:     */   public void processPlayerAbilities(C13PacketPlayerAbilities p_147348_1_)
/*  857:     */   {
/*  858:1084 */     this.playerEntity.capabilities.isFlying = ((p_147348_1_.func_149488_d()) && (this.playerEntity.capabilities.allowFlying));
/*  859:     */   }
/*  860:     */   
/*  861:     */   public void processTabComplete(C14PacketTabComplete p_147341_1_)
/*  862:     */   {
/*  863:1092 */     ArrayList var2 = Lists.newArrayList();
/*  864:1093 */     Iterator var3 = this.serverController.getPossibleCompletions(this.playerEntity, p_147341_1_.func_149419_c()).iterator();
/*  865:1095 */     while (var3.hasNext())
/*  866:     */     {
/*  867:1097 */       String var4 = (String)var3.next();
/*  868:1098 */       var2.add(var4);
/*  869:     */     }
/*  870:1101 */     this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete((String[])var2.toArray(new String[var2.size()])));
/*  871:     */   }
/*  872:     */   
/*  873:     */   public void processClientSettings(C15PacketClientSettings p_147352_1_)
/*  874:     */   {
/*  875:1110 */     this.playerEntity.func_147100_a(p_147352_1_);
/*  876:     */   }
/*  877:     */   
/*  878:     */   public void processVanilla250Packet(C17PacketCustomPayload p_147349_1_)
/*  879:     */   {
/*  880:1121 */     if ("MC|BEdit".equals(p_147349_1_.func_149559_c()))
/*  881:     */     {
/*  882:     */       try
/*  883:     */       {
/*  884:1125 */         ItemStack var2 = new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e())).readItemStackFromBuffer();
/*  885:1127 */         if (!ItemWritableBook.func_150930_a(var2.getTagCompound())) {
/*  886:1129 */           throw new IOException("Invalid book tag!");
/*  887:     */         }
/*  888:1132 */         ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
/*  889:1134 */         if ((var2.getItem() != Items.writable_book) || (var2.getItem() != var3.getItem())) {
/*  890:     */           return;
/*  891:     */         }
/*  892:1136 */         var3.setTagInfo("pages", var2.getTagCompound().getTagList("pages", 8));
/*  893:     */       }
/*  894:     */       catch (Exception var12)
/*  895:     */       {
/*  896:1141 */         logger.error("Couldn't handle book info", var12);
/*  897:     */       }
/*  898:     */     }
/*  899:1144 */     else if ("MC|BSign".equals(p_147349_1_.func_149559_c()))
/*  900:     */     {
/*  901:     */       try
/*  902:     */       {
/*  903:1148 */         ItemStack var2 = new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e())).readItemStackFromBuffer();
/*  904:1150 */         if (!ItemEditableBook.validBookTagContents(var2.getTagCompound())) {
/*  905:1152 */           throw new IOException("Invalid book tag!");
/*  906:     */         }
/*  907:1155 */         ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
/*  908:1157 */         if ((var2.getItem() != Items.written_book) || (var3.getItem() != Items.writable_book)) {
/*  909:     */           return;
/*  910:     */         }
/*  911:1159 */         var3.setTagInfo("author", new NBTTagString(this.playerEntity.getCommandSenderName()));
/*  912:1160 */         var3.setTagInfo("title", new NBTTagString(var2.getTagCompound().getString("title")));
/*  913:1161 */         var3.setTagInfo("pages", var2.getTagCompound().getTagList("pages", 8));
/*  914:1162 */         var3.func_150996_a(Items.written_book);
/*  915:     */       }
/*  916:     */       catch (Exception var11)
/*  917:     */       {
/*  918:1167 */         logger.error("Couldn't sign book", var11);
/*  919:     */       }
/*  920:     */     }
/*  921:1175 */     else if ("MC|TrSel".equals(p_147349_1_.func_149559_c()))
/*  922:     */     {
/*  923:     */       try
/*  924:     */       {
/*  925:1179 */         DataInputStream var13 = new DataInputStream(new ByteArrayInputStream(p_147349_1_.func_149558_e()));
/*  926:1180 */         int var16 = var13.readInt();
/*  927:1181 */         Container var4 = this.playerEntity.openContainer;
/*  928:1183 */         if (!(var4 instanceof ContainerMerchant)) {
/*  929:     */           return;
/*  930:     */         }
/*  931:1185 */         ((ContainerMerchant)var4).setCurrentRecipeIndex(var16);
/*  932:     */       }
/*  933:     */       catch (Exception var10)
/*  934:     */       {
/*  935:1190 */         logger.error("Couldn't select trade", var10);
/*  936:     */       }
/*  937:     */     }
/*  938:1193 */     else if ("MC|AdvCdm".equals(p_147349_1_.func_149559_c()))
/*  939:     */     {
/*  940:1195 */       if (!this.serverController.isCommandBlockEnabled()) {
/*  941:1197 */         this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
/*  942:1199 */       } else if ((this.playerEntity.canCommandSenderUseCommand(2, "")) && (this.playerEntity.capabilities.isCreativeMode)) {
/*  943:     */         try
/*  944:     */         {
/*  945:1203 */           PacketBuffer var14 = new PacketBuffer(Unpooled.wrappedBuffer(p_147349_1_.func_149558_e()));
/*  946:1204 */           byte var17 = var14.readByte();
/*  947:1205 */           CommandBlockLogic var18 = null;
/*  948:1207 */           if (var17 == 0)
/*  949:     */           {
/*  950:1209 */             TileEntity var5 = this.playerEntity.worldObj.getTileEntity(var14.readInt(), var14.readInt(), var14.readInt());
/*  951:1211 */             if ((var5 instanceof TileEntityCommandBlock)) {
/*  952:1213 */               var18 = ((TileEntityCommandBlock)var5).func_145993_a();
/*  953:     */             }
/*  954:     */           }
/*  955:1216 */           else if (var17 == 1)
/*  956:     */           {
/*  957:1218 */             Entity var20 = this.playerEntity.worldObj.getEntityByID(var14.readInt());
/*  958:1220 */             if ((var20 instanceof EntityMinecartCommandBlock)) {
/*  959:1222 */               var18 = ((EntityMinecartCommandBlock)var20).func_145822_e();
/*  960:     */             }
/*  961:     */           }
/*  962:1226 */           String var23 = var14.readStringFromBuffer(var14.readableBytes());
/*  963:1228 */           if (var18 == null) {
/*  964:     */             return;
/*  965:     */           }
/*  966:1230 */           var18.func_145752_a(var23);
/*  967:1231 */           var18.func_145756_e();
/*  968:1232 */           this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.setCommand.success", new Object[] { var23 }));
/*  969:     */         }
/*  970:     */         catch (Exception var9)
/*  971:     */         {
/*  972:1237 */           logger.error("Couldn't set command block", var9);
/*  973:     */         }
/*  974:     */       } else {
/*  975:1242 */         this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
/*  976:     */       }
/*  977:     */     }
/*  978:1245 */     else if ("MC|Beacon".equals(p_147349_1_.func_149559_c()))
/*  979:     */     {
/*  980:1247 */       if ((this.playerEntity.openContainer instanceof ContainerBeacon)) {
/*  981:     */         try
/*  982:     */         {
/*  983:1251 */           DataInputStream var13 = new DataInputStream(new ByteArrayInputStream(p_147349_1_.func_149558_e()));
/*  984:1252 */           int var16 = var13.readInt();
/*  985:1253 */           int var22 = var13.readInt();
/*  986:1254 */           ContainerBeacon var21 = (ContainerBeacon)this.playerEntity.openContainer;
/*  987:1255 */           Slot var6 = var21.getSlot(0);
/*  988:1257 */           if (!var6.getHasStack()) {
/*  989:     */             return;
/*  990:     */           }
/*  991:1259 */           var6.decrStackSize(1);
/*  992:1260 */           TileEntityBeacon var7 = var21.func_148327_e();
/*  993:1261 */           var7.func_146001_d(var16);
/*  994:1262 */           var7.func_146004_e(var22);
/*  995:1263 */           var7.onInventoryChanged();
/*  996:     */         }
/*  997:     */         catch (Exception var8)
/*  998:     */         {
/*  999:1268 */           logger.error("Couldn't set beacon", var8);
/* 1000:     */         }
/* 1001:     */       }
/* 1002:     */     }
/* 1003:1272 */     else if (("MC|ItemName".equals(p_147349_1_.func_149559_c())) && ((this.playerEntity.openContainer instanceof ContainerRepair)))
/* 1004:     */     {
/* 1005:1274 */       ContainerRepair var15 = (ContainerRepair)this.playerEntity.openContainer;
/* 1006:1276 */       if ((p_147349_1_.func_149558_e() != null) && (p_147349_1_.func_149558_e().length >= 1))
/* 1007:     */       {
/* 1008:1278 */         String var19 = ChatAllowedCharacters.filerAllowedCharacters(new String(p_147349_1_.func_149558_e(), Charsets.UTF_8));
/* 1009:1280 */         if (var19.length() <= 30) {
/* 1010:1282 */           var15.updateItemName(var19);
/* 1011:     */         }
/* 1012:     */       }
/* 1013:     */       else
/* 1014:     */       {
/* 1015:1287 */         var15.updateItemName("");
/* 1016:     */       }
/* 1017:     */     }
/* 1018:     */   }
/* 1019:     */   
/* 1020:     */   public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_)
/* 1021:     */   {
/* 1022:1299 */     if (p_147232_2_ != EnumConnectionState.PLAY) {
/* 1023:1301 */       throw new IllegalStateException("Unexpected change in protocol!");
/* 1024:     */     }
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   static final class SwitchEnumState
/* 1028:     */   {
/* 1029:1307 */     static final int[] field_151290_a = new int[C16PacketClientStatus.EnumState.values().length];
/* 1030:     */     private static final String __OBFID = "CL_00001455";
/* 1031:     */     
/* 1032:     */     static
/* 1033:     */     {
/* 1034:     */       try
/* 1035:     */       {
/* 1036:1314 */         field_151290_a[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = 1;
/* 1037:     */       }
/* 1038:     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/* 1039:     */       try
/* 1040:     */       {
/* 1041:1323 */         field_151290_a[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = 2;
/* 1042:     */       }
/* 1043:     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/* 1044:     */       try
/* 1045:     */       {
/* 1046:1332 */         field_151290_a[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
/* 1047:     */       }
/* 1048:     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/* 1049:     */     }
/* 1050:     */   }
/* 1051:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.NetHandlerPlayServer
 * JD-Core Version:    0.7.0.1
 */