/*   1:    */ package me.connorm.Nodus.module;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import me.connorm.Nodus.event.other.EventKeyPress;
/*   6:    */ import me.connorm.Nodus.module.core.NodusModule;
/*   7:    */ import me.connorm.Nodus.module.modules.Aimbot;
/*   8:    */ import me.connorm.Nodus.module.modules.AutoArmor;
/*   9:    */ import me.connorm.Nodus.module.modules.AutoEat;
/*  10:    */ import me.connorm.Nodus.module.modules.AutoMine;
/*  11:    */ import me.connorm.Nodus.module.modules.AutoSoup;
/*  12:    */ import me.connorm.Nodus.module.modules.AutoSword;
/*  13:    */ import me.connorm.Nodus.module.modules.AutoTool;
/*  14:    */ import me.connorm.Nodus.module.modules.AutoWalk;
/*  15:    */ import me.connorm.Nodus.module.modules.BowAimbot;
/*  16:    */ import me.connorm.Nodus.module.modules.Breadcrumbs;
/*  17:    */ import me.connorm.Nodus.module.modules.Brightness;
/*  18:    */ import me.connorm.Nodus.module.modules.Build;
/*  19:    */ import me.connorm.Nodus.module.modules.ChestESP;
/*  20:    */ import me.connorm.Nodus.module.modules.ClickAimbot;
/*  21:    */ import me.connorm.Nodus.module.modules.ClickGui;
/*  22:    */ import me.connorm.Nodus.module.modules.Console;
/*  23:    */ import me.connorm.Nodus.module.modules.Criticals;
/*  24:    */ import me.connorm.Nodus.module.modules.Day;
/*  25:    */ import me.connorm.Nodus.module.modules.FastPlace;
/*  26:    */ import me.connorm.Nodus.module.modules.Flight;
/*  27:    */ import me.connorm.Nodus.module.modules.ForceField;
/*  28:    */ import me.connorm.Nodus.module.modules.Freecam;
/*  29:    */ import me.connorm.Nodus.module.modules.HighJump;
/*  30:    */ import me.connorm.Nodus.module.modules.MobESP;
/*  31:    */ import me.connorm.Nodus.module.modules.NameTags;
/*  32:    */ import me.connorm.Nodus.module.modules.NoFall;
/*  33:    */ import me.connorm.Nodus.module.modules.Nuker;
/*  34:    */ import me.connorm.Nodus.module.modules.Paralyze;
/*  35:    */ import me.connorm.Nodus.module.modules.Phase;
/*  36:    */ import me.connorm.Nodus.module.modules.PlayerESP;
/*  37:    */ import me.connorm.Nodus.module.modules.ProphuntESP;
/*  38:    */ import me.connorm.Nodus.module.modules.Regen;
/*  39:    */ import me.connorm.Nodus.module.modules.SafeWalk;
/*  40:    */ import me.connorm.Nodus.module.modules.Search;
/*  41:    */ import me.connorm.Nodus.module.modules.Sneak;
/*  42:    */ import me.connorm.Nodus.module.modules.Spider;
/*  43:    */ import me.connorm.Nodus.module.modules.Sprint;
/*  44:    */ import me.connorm.Nodus.module.modules.Step;
/*  45:    */ import me.connorm.Nodus.module.modules.Timer;
/*  46:    */ import me.connorm.Nodus.module.modules.Tracers;
/*  47:    */ import me.connorm.Nodus.module.modules.Unpushable;
/*  48:    */ import me.connorm.Nodus.module.modules.Weather;
/*  49:    */ import me.connorm.Nodus.module.modules.Xray;
/*  50:    */ import me.connorm.lib.EventManager;
/*  51:    */ import me.connorm.lib.EventTarget;
/*  52:    */ 
/*  53:    */ public class NodusModuleManager
/*  54:    */ {
/*  55: 56 */   private List<NodusModule> theModules = new ArrayList();
/*  56:    */   public AutoSword autoSwordModule;
/*  57:    */   public Breadcrumbs breadcrumbsModule;
/*  58:    */   public Build buildModule;
/*  59:    */   public ChestESP chestESPModule;
/*  60:    */   public NameTags nameTagsModule;
/*  61:    */   public SafeWalk safeWalkModule;
/*  62:    */   public Search searchModule;
/*  63:    */   public Unpushable unpushableModule;
/*  64:    */   public Xray xrayModule;
/*  65:    */   
/*  66:    */   public NodusModuleManager()
/*  67:    */   {
/*  68: 70 */     EventManager.register(this);
/*  69: 71 */     this.theModules.add(new Aimbot());
/*  70: 72 */     this.theModules.add(new AutoArmor());
/*  71: 73 */     this.theModules.add(new AutoEat());
/*  72: 74 */     this.theModules.add(new AutoMine());
/*  73: 75 */     this.theModules.add(new AutoSoup());
/*  74: 76 */     this.theModules.add(this.autoSwordModule = new AutoSword());
/*  75: 77 */     this.theModules.add(new AutoTool());
/*  76: 78 */     this.theModules.add(new AutoWalk());
/*  77: 79 */     this.theModules.add(new BowAimbot());
/*  78: 80 */     this.theModules.add(this.breadcrumbsModule = new Breadcrumbs());
/*  79: 81 */     this.theModules.add(new Brightness());
/*  80: 82 */     this.theModules.add(this.buildModule = new Build());
/*  81: 83 */     this.theModules.add(this.chestESPModule = new ChestESP());
/*  82: 84 */     this.theModules.add(new ClickAimbot());
/*  83: 85 */     this.theModules.add(new ClickGui());
/*  84: 86 */     this.theModules.add(new Console());
/*  85: 87 */     this.theModules.add(new Criticals());
/*  86: 88 */     this.theModules.add(new Day());
/*  87: 89 */     this.theModules.add(new FastPlace());
/*  88: 90 */     this.theModules.add(new Flight());
/*  89: 91 */     this.theModules.add(new ForceField());
/*  90: 92 */     this.theModules.add(new Freecam());
/*  91: 93 */     this.theModules.add(new HighJump());
/*  92: 94 */     this.theModules.add(new MobESP());
/*  93: 95 */     this.theModules.add(this.nameTagsModule = new NameTags());
/*  94: 96 */     this.theModules.add(new NoFall());
/*  95: 97 */     this.theModules.add(new Nuker());
/*  96: 98 */     this.theModules.add(new Paralyze());
/*  97: 99 */     this.theModules.add(new Phase());
/*  98:100 */     this.theModules.add(new PlayerESP());
/*  99:101 */     this.theModules.add(new ProphuntESP());
/* 100:102 */     this.theModules.add(new Regen());
/* 101:103 */     this.theModules.add(this.safeWalkModule = new SafeWalk());
/* 102:104 */     this.theModules.add(this.searchModule = new Search());
/* 103:105 */     this.theModules.add(new Sneak());
/* 104:106 */     this.theModules.add(new Spider());
/* 105:107 */     this.theModules.add(new Sprint());
/* 106:108 */     this.theModules.add(new Step());
/* 107:109 */     this.theModules.add(new Timer());
/* 108:110 */     this.theModules.add(new Tracers());
/* 109:111 */     this.theModules.add(this.unpushableModule = new Unpushable());
/* 110:112 */     this.theModules.add(new Weather());
/* 111:113 */     this.theModules.add(this.xrayModule = new Xray());
/* 112:114 */     initSerialIDs();
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void initSerialIDs()
/* 116:    */   {
/* 117:119 */     byte idAssigner = 1;
/* 118:120 */     for (NodusModule nodusModule : this.theModules)
/* 119:    */     {
/* 120:122 */       nodusModule.setSerialID(idAssigner);
/* 121:123 */       idAssigner = (byte)(idAssigner + 1);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   @EventTarget
/* 126:    */   public void pressKey(EventKeyPress theEvent)
/* 127:    */   {
/* 128:130 */     int keyCode = theEvent.getKeyCode();
/* 129:131 */     for (NodusModule theModule : this.theModules) {
/* 130:133 */       if (keyCode == theModule.getKeyBind()) {
/* 131:135 */         theModule.toggleModule();
/* 132:    */       }
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public NodusModule getModuleByID(byte serialID)
/* 137:    */   {
/* 138:142 */     for (NodusModule nodusModule : this.theModules) {
/* 139:144 */       if (serialID == nodusModule.getSerialID()) {
/* 140:145 */         return nodusModule;
/* 141:    */       }
/* 142:    */     }
/* 143:147 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public NodusModule[] getModules()
/* 147:    */   {
/* 148:152 */     return (NodusModule[])this.theModules.toArray(new NodusModule[this.theModules.size()]);
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.module.NodusModuleManager
 * JD-Core Version:    0.7.0.1
 */