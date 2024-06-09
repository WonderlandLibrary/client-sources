/*     */ package net.minecraft.realms;
/*     */ 
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.net.Proxy;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.util.Session;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ 
/*     */ public class Realms
/*     */ {
/*     */   public static boolean isTouchScreen() {
/*  15 */     return (Minecraft.getMinecraft()).gameSettings.touchscreen;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Proxy getProxy() {
/*  20 */     return Minecraft.getMinecraft().getProxy();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String sessionId() {
/*  25 */     Session session = Minecraft.getMinecraft().getSession();
/*  26 */     return (session == null) ? null : session.getSessionID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String userName() {
/*  31 */     Session session = Minecraft.getMinecraft().getSession();
/*  32 */     return (session == null) ? null : session.getUsername();
/*     */   }
/*     */ 
/*     */   
/*     */   public static long currentTimeMillis() {
/*  37 */     return Minecraft.getSystemTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getSessionId() {
/*  42 */     return Minecraft.getMinecraft().getSession().getSessionID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getUUID() {
/*  47 */     return Minecraft.getMinecraft().getSession().getPlayerID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getName() {
/*  52 */     return Minecraft.getMinecraft().getSession().getUsername();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String uuidToName(String p_uuidToName_0_) {
/*  57 */     return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(p_uuidToName_0_), null), false).getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setScreen(RealmsScreen p_setScreen_0_) {
/*  62 */     Minecraft.getMinecraft().displayGuiScreen((GuiScreen)p_setScreen_0_.getProxy());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getGameDirectoryPath() {
/*  67 */     return (Minecraft.getMinecraft()).mcDataDir.getAbsolutePath();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int survivalId() {
/*  72 */     return WorldSettings.GameType.SURVIVAL.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int creativeId() {
/*  77 */     return WorldSettings.GameType.CREATIVE.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int adventureId() {
/*  82 */     return WorldSettings.GameType.ADVENTURE.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int spectatorId() {
/*  87 */     return WorldSettings.GameType.SPECTATOR.getID();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setConnectedToRealms(boolean p_setConnectedToRealms_0_) {
/*  92 */     Minecraft.getMinecraft().func_181537_a(p_setConnectedToRealms_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ListenableFuture<Object> downloadResourcePack(String p_downloadResourcePack_0_, String p_downloadResourcePack_1_) {
/*  97 */     ListenableFuture<Object> listenablefuture = Minecraft.getMinecraft().getResourcePackRepository().downloadResourcePack(p_downloadResourcePack_0_, p_downloadResourcePack_1_);
/*  98 */     return listenablefuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearResourcePack() {
/* 103 */     Minecraft.getMinecraft().getResourcePackRepository().func_148529_f();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\realms\Realms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */