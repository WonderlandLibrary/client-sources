/*     */ package me.eagler;
/*     */ 
/*     */ import me.eagler.clickgui.ClickGUI;
/*     */ import me.eagler.command.CommandManager;
/*     */ import me.eagler.file.ConfigSaver;
/*     */ import me.eagler.file.FileManager;
/*     */ import me.eagler.font.FontHelper;
/*     */ import me.eagler.module.ModuleManager;
/*     */ import me.eagler.premium.HWIDCheck;
/*     */ import me.eagler.setting.SettingManager;
/*     */ import me.eagler.utils.RenderHelper;
/*     */ import me.eagler.utils.Utils;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Client
/*     */ {
/*     */   public static Client instance;
/*  24 */   public double VERSION = 2.0D;
/*     */   
/*  26 */   private String prefix = "§c§lHera >> §f";
/*     */   
/*     */   private ModuleManager moduleManager;
/*     */   
/*     */   private CommandManager commandManager;
/*     */   
/*     */   private SettingManager settingManager;
/*     */   
/*     */   private FileManager fileManager;
/*     */   
/*     */   private ClickGUI clickGui;
/*     */   
/*     */   private RenderHelper renderHelper;
/*     */   
/*  40 */   private Minecraft mc = Minecraft.getMinecraft();
/*     */   
/*     */   private HWIDCheck hwidCheck;
/*     */   
/*     */   private Utils utils;
/*     */ 
/*     */   
/*     */   public void start() {
/*  48 */     instance = this;
/*     */     
/*  50 */     Display.setTitle("Minecraft 1.8.8 | Hera v" + this.VERSION);
/*     */     
/*  52 */     FontHelper.loadFonts();
/*     */     
/*  54 */     this.settingManager = new SettingManager();
/*     */     
/*  56 */     this.settingManager.load();
/*     */     
/*  58 */     this.moduleManager = new ModuleManager();
/*     */     
/*  60 */     this.moduleManager.load();
/*     */     
/*  62 */     this.commandManager = new CommandManager();
/*     */     
/*  64 */     this.commandManager.load();
/*     */     
/*  66 */     this.fileManager = new FileManager();
/*     */     
/*  68 */     this.fileManager.load();
/*     */     
/*  70 */     this.fileManager.loadName();
/*     */     
/*  72 */     ConfigSaver.loadConfig("latest");
/*     */     
/*  74 */     this.renderHelper = new RenderHelper();
/*     */     
/*  76 */     this.utils = new Utils();
/*     */     
/*  78 */     this.clickGui = new ClickGUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModuleManager getModuleManager() {
/*  94 */     return this.moduleManager;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandManager getCommandManager() {
/* 100 */     return this.commandManager;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 106 */     return this.prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileManager getFileManager() {
/* 112 */     return this.fileManager;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SettingManager getSettingManager() {
/* 118 */     return this.settingManager;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClickGUI getClickGui() {
/* 124 */     return this.clickGui;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Minecraft getMc() {
/* 130 */     return this.mc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RenderHelper getRenderHelper() {
/* 136 */     return this.renderHelper;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Utils getUtils() {
/* 142 */     return this.utils;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\Client.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */