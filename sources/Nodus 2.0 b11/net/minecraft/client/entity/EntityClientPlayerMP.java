/*   1:    */ package net.minecraft.client.entity;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.event.chat.EventChatSend;
/*   4:    */ import me.connorm.Nodus.event.player.EventPlayerMotionUpdate;
/*   5:    */ import me.connorm.Nodus.event.player.EventPlayerPostMotionUpdate;
/*   6:    */ import me.connorm.Nodus.event.player.EventPlayerUpdate;
/*   7:    */ import me.connorm.lib.EventManager;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.audio.MovingSoundMinecartRiding;
/*  10:    */ import net.minecraft.client.audio.SoundHandler;
/*  11:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  12:    */ import net.minecraft.entity.Entity;
/*  13:    */ import net.minecraft.entity.item.EntityItem;
/*  14:    */ import net.minecraft.entity.item.EntityMinecart;
/*  15:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  16:    */ import net.minecraft.inventory.Container;
/*  17:    */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*  18:    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*  19:    */ import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
/*  20:    */ import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
/*  21:    */ import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
/*  22:    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*  23:    */ import net.minecraft.network.play.client.C0APacketAnimation;
/*  24:    */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*  25:    */ import net.minecraft.network.play.client.C0CPacketInput;
/*  26:    */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*  27:    */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*  28:    */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*  29:    */ import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
/*  30:    */ import net.minecraft.stats.StatBase;
/*  31:    */ import net.minecraft.stats.StatFileWriter;
/*  32:    */ import net.minecraft.util.AxisAlignedBB;
/*  33:    */ import net.minecraft.util.DamageSource;
/*  34:    */ import net.minecraft.util.MathHelper;
/*  35:    */ import net.minecraft.util.MovementInput;
/*  36:    */ import net.minecraft.util.Session;
/*  37:    */ import net.minecraft.world.World;
/*  38:    */ 
/*  39:    */ public class EntityClientPlayerMP
/*  40:    */   extends EntityPlayerSP
/*  41:    */ {
/*  42:    */   public final NetHandlerPlayClient sendQueue;
/*  43:    */   private final StatFileWriter field_146108_bO;
/*  44:    */   private double oldPosX;
/*  45:    */   private double oldMinY;
/*  46:    */   private double oldPosY;
/*  47:    */   private double oldPosZ;
/*  48:    */   private float oldRotationYaw;
/*  49:    */   private float oldRotationPitch;
/*  50:    */   private boolean wasOnGround;
/*  51:    */   private boolean shouldStopSneaking;
/*  52:    */   private boolean wasSneaking;
/*  53:    */   private int ticksSinceMovePacket;
/*  54:    */   private boolean hasSetHealth;
/*  55:    */   private String field_142022_ce;
/*  56:    */   private static final String __OBFID = "CL_00000887";
/*  57:    */   
/*  58:    */   public EntityClientPlayerMP(Minecraft p_i45064_1_, World p_i45064_2_, Session p_i45064_3_, NetHandlerPlayClient p_i45064_4_, StatFileWriter p_i45064_5_)
/*  59:    */   {
/*  60: 65 */     super(p_i45064_1_, p_i45064_2_, p_i45064_3_, 0);
/*  61: 66 */     this.sendQueue = p_i45064_4_;
/*  62: 67 */     this.field_146108_bO = p_i45064_5_;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
/*  66:    */   {
/*  67: 75 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void heal(float par1) {}
/*  71:    */   
/*  72:    */   public void mountEntity(Entity par1Entity)
/*  73:    */   {
/*  74: 88 */     super.mountEntity(par1Entity);
/*  75: 90 */     if ((par1Entity instanceof EntityMinecart)) {
/*  76: 92 */       this.mc.getSoundHandler().playSound(new MovingSoundMinecartRiding(this, (EntityMinecart)par1Entity));
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void onUpdate()
/*  81:    */   {
/*  82:101 */     if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)))
/*  83:    */     {
/*  84:104 */       EventManager.call(new EventPlayerUpdate(this));
/*  85:    */       
/*  86:106 */       super.onUpdate();
/*  87:108 */       if (isRiding())
/*  88:    */       {
/*  89:110 */         this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/*  90:111 */         this.sendQueue.addToSendQueue(new C0CPacketInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
/*  91:    */       }
/*  92:    */       else
/*  93:    */       {
/*  94:115 */         sendMotionUpdates();
/*  95:    */       }
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void sendMotionUpdates()
/* 100:    */   {
/* 101:126 */     EventManager.call(new EventPlayerMotionUpdate(this));
/* 102:    */     
/* 103:128 */     boolean var1 = isSprinting();
/* 104:130 */     if (var1 != this.wasSneaking)
/* 105:    */     {
/* 106:132 */       if (var1) {
/* 107:134 */         this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 4));
/* 108:    */       } else {
/* 109:138 */         this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 5));
/* 110:    */       }
/* 111:141 */       this.wasSneaking = var1;
/* 112:    */     }
/* 113:144 */     boolean var2 = isSneaking();
/* 114:146 */     if (var2 != this.shouldStopSneaking)
/* 115:    */     {
/* 116:148 */       if (var2) {
/* 117:150 */         this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 1));
/* 118:    */       } else {
/* 119:154 */         this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 2));
/* 120:    */       }
/* 121:157 */       this.shouldStopSneaking = var2;
/* 122:    */     }
/* 123:160 */     double var3 = this.posX - this.oldPosX;
/* 124:161 */     double var5 = this.boundingBox.minY - this.oldMinY;
/* 125:162 */     double var7 = this.posZ - this.oldPosZ;
/* 126:163 */     double var9 = this.rotationYaw - this.oldRotationYaw;
/* 127:164 */     double var11 = this.rotationPitch - this.oldRotationPitch;
/* 128:165 */     boolean var13 = (var3 * var3 + var5 * var5 + var7 * var7 > 0.0009D) || (this.ticksSinceMovePacket >= 20);
/* 129:166 */     boolean var14 = (var9 != 0.0D) || (var11 != 0.0D);
/* 130:168 */     if (this.ridingEntity != null)
/* 131:    */     {
/* 132:170 */       this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
/* 133:171 */       var13 = false;
/* 134:    */     }
/* 135:173 */     else if ((var13) && (var14))
/* 136:    */     {
/* 137:175 */       this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround));
/* 138:    */     }
/* 139:177 */     else if (var13)
/* 140:    */     {
/* 141:179 */       this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, this.onGround));
/* 142:    */     }
/* 143:181 */     else if (var14)
/* 144:    */     {
/* 145:183 */       this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
/* 146:    */     }
/* 147:    */     else
/* 148:    */     {
/* 149:187 */       this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
/* 150:    */     }
/* 151:190 */     this.ticksSinceMovePacket += 1;
/* 152:191 */     this.wasOnGround = this.onGround;
/* 153:193 */     if (var13)
/* 154:    */     {
/* 155:195 */       this.oldPosX = this.posX;
/* 156:196 */       this.oldMinY = this.boundingBox.minY;
/* 157:197 */       this.oldPosY = this.posY;
/* 158:198 */       this.oldPosZ = this.posZ;
/* 159:199 */       this.ticksSinceMovePacket = 0;
/* 160:    */     }
/* 161:202 */     if (var14)
/* 162:    */     {
/* 163:204 */       this.oldRotationYaw = this.rotationYaw;
/* 164:205 */       this.oldRotationPitch = this.rotationPitch;
/* 165:    */     }
/* 166:209 */     EventManager.call(new EventPlayerPostMotionUpdate(this));
/* 167:    */   }
/* 168:    */   
/* 169:    */   public EntityItem dropOneItem(boolean par1)
/* 170:    */   {
/* 171:217 */     int var2 = par1 ? 3 : 4;
/* 172:218 */     this.sendQueue.addToSendQueue(new C07PacketPlayerDigging(var2, 0, 0, 0, 0));
/* 173:219 */     return null;
/* 174:    */   }
/* 175:    */   
/* 176:    */   protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {}
/* 177:    */   
/* 178:    */   public void sendChatMessage(String par1Str)
/* 179:    */   {
/* 180:    */     EventChatSend eventChatSend;
/* 181:234 */     EventManager.call(eventChatSend = new EventChatSend(par1Str));
/* 182:235 */     if (eventChatSend.isCancelled()) {
/* 183:236 */       return;
/* 184:    */     }
/* 185:237 */     this.sendQueue.addToSendQueue(new C01PacketChatMessage(par1Str));
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void swingItem()
/* 189:    */   {
/* 190:245 */     super.swingItem();
/* 191:246 */     this.sendQueue.addToSendQueue(new C0APacketAnimation(this, 1));
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void respawnPlayer()
/* 195:    */   {
/* 196:251 */     this.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.PERFORM_RESPAWN));
/* 197:    */   }
/* 198:    */   
/* 199:    */   protected void damageEntity(DamageSource par1DamageSource, float par2)
/* 200:    */   {
/* 201:260 */     if (!isEntityInvulnerable()) {
/* 202:262 */       setHealth(getHealth() - par2);
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void closeScreen()
/* 207:    */   {
/* 208:271 */     this.sendQueue.addToSendQueue(new C0DPacketCloseWindow(this.openContainer.windowId));
/* 209:272 */     closeScreenNoPacket();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void closeScreenNoPacket()
/* 213:    */   {
/* 214:280 */     this.inventory.setItemStack(null);
/* 215:281 */     super.closeScreen();
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setPlayerSPHealth(float par1)
/* 219:    */   {
/* 220:289 */     if (this.hasSetHealth)
/* 221:    */     {
/* 222:291 */       super.setPlayerSPHealth(par1);
/* 223:    */     }
/* 224:    */     else
/* 225:    */     {
/* 226:295 */       setHealth(par1);
/* 227:296 */       this.hasSetHealth = true;
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void addStat(StatBase par1StatBase, int par2)
/* 232:    */   {
/* 233:305 */     if (par1StatBase != null) {
/* 234:307 */       if (par1StatBase.isIndependent) {
/* 235:309 */         super.addStat(par1StatBase, par2);
/* 236:    */       }
/* 237:    */     }
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void sendPlayerAbilities()
/* 241:    */   {
/* 242:319 */     this.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(this.capabilities));
/* 243:    */   }
/* 244:    */   
/* 245:    */   protected void func_110318_g()
/* 246:    */   {
/* 247:324 */     this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 6, (int)(getHorseJumpPower() * 100.0F)));
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void func_110322_i()
/* 251:    */   {
/* 252:329 */     this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, 7));
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void func_142020_c(String par1Str)
/* 256:    */   {
/* 257:334 */     this.field_142022_ce = par1Str;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String func_142021_k()
/* 261:    */   {
/* 262:339 */     return this.field_142022_ce;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public StatFileWriter func_146107_m()
/* 266:    */   {
/* 267:344 */     return this.field_146108_bO;
/* 268:    */   }
/* 269:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.entity.EntityClientPlayerMP
 * JD-Core Version:    0.7.0.1
 */