/*   1:    */ package net.minecraft.network;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.BiMap;
/*   4:    */ import com.google.common.collect.HashBiMap;
/*   5:    */ import com.google.common.collect.Iterables;
/*   6:    */ import com.google.common.collect.Maps;
/*   7:    */ import gnu.trove.map.TIntObjectMap;
/*   8:    */ import gnu.trove.map.hash.TIntObjectHashMap;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.Map;
/*  11:    */ import net.minecraft.network.handshake.client.C00Handshake;
/*  12:    */ import net.minecraft.network.login.client.C00PacketLoginStart;
/*  13:    */ import net.minecraft.network.login.client.C01PacketEncryptionResponse;
/*  14:    */ import net.minecraft.network.login.server.S00PacketDisconnect;
/*  15:    */ import net.minecraft.network.login.server.S01PacketEncryptionRequest;
/*  16:    */ import net.minecraft.network.login.server.S02PacketLoginSuccess;
/*  17:    */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*  18:    */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*  19:    */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*  20:    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*  21:    */ import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
/*  22:    */ import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
/*  23:    */ import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
/*  24:    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*  25:    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*  26:    */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*  27:    */ import net.minecraft.network.play.client.C0APacketAnimation;
/*  28:    */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*  29:    */ import net.minecraft.network.play.client.C0CPacketInput;
/*  30:    */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*  31:    */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*  32:    */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*  33:    */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*  34:    */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*  35:    */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*  36:    */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*  37:    */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*  38:    */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*  39:    */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*  40:    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*  41:    */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*  42:    */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*  43:    */ import net.minecraft.network.play.server.S02PacketChat;
/*  44:    */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*  45:    */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*  46:    */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*  47:    */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*  48:    */ import net.minecraft.network.play.server.S07PacketRespawn;
/*  49:    */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*  50:    */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*  51:    */ import net.minecraft.network.play.server.S0APacketUseBed;
/*  52:    */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*  53:    */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*  54:    */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*  55:    */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*  56:    */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*  57:    */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*  58:    */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*  59:    */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*  60:    */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*  61:    */ import net.minecraft.network.play.server.S14PacketEntity;
/*  62:    */ import net.minecraft.network.play.server.S14PacketEntity.S15PacketEntityRelMove;
/*  63:    */ import net.minecraft.network.play.server.S14PacketEntity.S16PacketEntityLook;
/*  64:    */ import net.minecraft.network.play.server.S14PacketEntity.S17PacketEntityLookMove;
/*  65:    */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*  66:    */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*  67:    */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*  68:    */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*  69:    */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*  70:    */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*  71:    */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*  72:    */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*  73:    */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*  74:    */ import net.minecraft.network.play.server.S21PacketChunkData;
/*  75:    */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*  76:    */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*  77:    */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*  78:    */ import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
/*  79:    */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*  80:    */ import net.minecraft.network.play.server.S27PacketExplosion;
/*  81:    */ import net.minecraft.network.play.server.S28PacketEffect;
/*  82:    */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*  83:    */ import net.minecraft.network.play.server.S2APacketParticles;
/*  84:    */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*  85:    */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*  86:    */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*  87:    */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*  88:    */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*  89:    */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*  90:    */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*  91:    */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*  92:    */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*  93:    */ import net.minecraft.network.play.server.S34PacketMaps;
/*  94:    */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*  95:    */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*  96:    */ import net.minecraft.network.play.server.S37PacketStatistics;
/*  97:    */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*  98:    */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*  99:    */ import net.minecraft.network.play.server.S3APacketTabComplete;
/* 100:    */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/* 101:    */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/* 102:    */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/* 103:    */ import net.minecraft.network.play.server.S3EPacketTeams;
/* 104:    */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/* 105:    */ import net.minecraft.network.play.server.S40PacketDisconnect;
/* 106:    */ import net.minecraft.network.status.client.C00PacketServerQuery;
/* 107:    */ import net.minecraft.network.status.client.C01PacketPing;
/* 108:    */ import net.minecraft.network.status.server.S00PacketServerInfo;
/* 109:    */ import net.minecraft.network.status.server.S01PacketPong;
/* 110:    */ import org.apache.logging.log4j.LogManager;
/* 111:    */ import org.apache.logging.log4j.Logger;
/* 112:    */ 
/* 113:    */ public enum EnumConnectionState
/* 114:    */ {
/* 115:108 */   HANDSHAKING("HANDSHAKING", 0, -1, null),  PLAY("PLAY", 1, 0, null),  STATUS("STATUS", 2, 1, null),  LOGIN("LOGIN", 3, 2, null);
/* 116:    */   
/* 117:    */   private static final TIntObjectMap field_150764_e;
/* 118:    */   private static final Map field_150761_f;
/* 119:    */   private final int field_150762_g;
/* 120:    */   private final BiMap field_150769_h;
/* 121:    */   private final BiMap field_150770_i;
/* 122:    */   private static final String __OBFID = "CL_00001245";
/* 123:    */   
/* 124:    */   private EnumConnectionState(int p_i45152_3_)
/* 125:    */   {
/* 126:240 */     this.field_150769_h = HashBiMap.create();
/* 127:241 */     this.field_150770_i = HashBiMap.create();
/* 128:242 */     this.field_150762_g = p_i45152_3_;
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected EnumConnectionState func_150751_a(int p_150751_1_, Class p_150751_2_)
/* 132:    */   {
/* 133:249 */     if (this.field_150769_h.containsKey(Integer.valueOf(p_150751_1_)))
/* 134:    */     {
/* 135:251 */       String var3 = "Serverbound packet ID " + p_150751_1_ + " is already assigned to " + this.field_150769_h.get(Integer.valueOf(p_150751_1_)) + "; cannot re-assign to " + p_150751_2_;
/* 136:252 */       LogManager.getLogger().fatal(var3);
/* 137:253 */       throw new IllegalArgumentException(var3);
/* 138:    */     }
/* 139:255 */     if (this.field_150769_h.containsValue(p_150751_2_))
/* 140:    */     {
/* 141:257 */       String var3 = "Serverbound packet " + p_150751_2_ + " is already assigned to ID " + this.field_150769_h.inverse().get(p_150751_2_) + "; cannot re-assign to " + p_150751_1_;
/* 142:258 */       LogManager.getLogger().fatal(var3);
/* 143:259 */       throw new IllegalArgumentException(var3);
/* 144:    */     }
/* 145:263 */     this.field_150769_h.put(Integer.valueOf(p_150751_1_), p_150751_2_);
/* 146:264 */     return this;
/* 147:    */   }
/* 148:    */   
/* 149:    */   protected EnumConnectionState func_150756_b(int p_150756_1_, Class p_150756_2_)
/* 150:    */   {
/* 151:272 */     if (this.field_150770_i.containsKey(Integer.valueOf(p_150756_1_)))
/* 152:    */     {
/* 153:274 */       String var3 = "Clientbound packet ID " + p_150756_1_ + " is already assigned to " + this.field_150770_i.get(Integer.valueOf(p_150756_1_)) + "; cannot re-assign to " + p_150756_2_;
/* 154:275 */       LogManager.getLogger().fatal(var3);
/* 155:276 */       throw new IllegalArgumentException(var3);
/* 156:    */     }
/* 157:278 */     if (this.field_150770_i.containsValue(p_150756_2_))
/* 158:    */     {
/* 159:280 */       String var3 = "Clientbound packet " + p_150756_2_ + " is already assigned to ID " + this.field_150770_i.inverse().get(p_150756_2_) + "; cannot re-assign to " + p_150756_1_;
/* 160:281 */       LogManager.getLogger().fatal(var3);
/* 161:282 */       throw new IllegalArgumentException(var3);
/* 162:    */     }
/* 163:286 */     this.field_150770_i.put(Integer.valueOf(p_150756_1_), p_150756_2_);
/* 164:287 */     return this;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public BiMap func_150753_a()
/* 168:    */   {
/* 169:293 */     return this.field_150769_h;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public BiMap func_150755_b()
/* 173:    */   {
/* 174:298 */     return this.field_150770_i;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public BiMap func_150757_a(boolean p_150757_1_)
/* 178:    */   {
/* 179:303 */     return p_150757_1_ ? func_150755_b() : func_150753_a();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public BiMap func_150754_b(boolean p_150754_1_)
/* 183:    */   {
/* 184:308 */     return p_150754_1_ ? func_150753_a() : func_150755_b();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int func_150759_c()
/* 188:    */   {
/* 189:313 */     return this.field_150762_g;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static EnumConnectionState func_150760_a(int p_150760_0_)
/* 193:    */   {
/* 194:318 */     return (EnumConnectionState)field_150764_e.get(p_150760_0_);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static EnumConnectionState func_150752_a(Packet p_150752_0_)
/* 198:    */   {
/* 199:323 */     return (EnumConnectionState)field_150761_f.get(p_150752_0_.getClass());
/* 200:    */   }
/* 201:    */   
/* 202:    */   private EnumConnectionState(String ignore1, int ignore2, int var3, Object var4)
/* 203:    */   {
/* 204:328 */     this(var3);
/* 205:    */   }
/* 206:    */   
/* 207:    */   static
/* 208:    */   {
/* 209:231 */     field_150764_e = new TIntObjectHashMap();
/* 210:232 */     field_150761_f = Maps.newHashMap();
/* 211:    */     
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:    */ 
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:    */ 
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:    */ 
/* 257:    */ 
/* 258:    */ 
/* 259:    */ 
/* 260:    */ 
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:    */ 
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:    */ 
/* 274:    */ 
/* 275:    */ 
/* 276:    */ 
/* 277:    */ 
/* 278:    */ 
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:    */ 
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:    */ 
/* 288:    */ 
/* 289:    */ 
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:    */ 
/* 296:    */ 
/* 297:    */ 
/* 298:    */ 
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:    */ 
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:332 */     EnumConnectionState[] var0 = values();
/* 311:333 */     int var1 = var0.length;
/* 312:335 */     for (int var2 = 0; var2 < var1; var2++)
/* 313:    */     {
/* 314:337 */       EnumConnectionState var3 = var0[var2];
/* 315:338 */       field_150764_e.put(var3.func_150759_c(), var3);
/* 316:339 */       Iterator var4 = Iterables.concat(var3.func_150755_b().values(), var3.func_150753_a().values()).iterator();
/* 317:341 */       while (var4.hasNext())
/* 318:    */       {
/* 319:343 */         Class var5 = (Class)var4.next();
/* 320:345 */         if ((field_150761_f.containsKey(var5)) && (field_150761_f.get(var5) != var3)) {
/* 321:347 */           throw new Error("Packet " + var5 + " is already assigned to protocol " + field_150761_f.get(var5) + " - can't reassign to " + var3);
/* 322:    */         }
/* 323:350 */         field_150761_f.put(var5, var3);
/* 324:    */       }
/* 325:    */     }
/* 326:    */   }
/* 327:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.EnumConnectionState
 * JD-Core Version:    0.7.0.1
 */