/*     */ package nightmare.module;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import nightmare.module.combat.AimAssist;
/*     */ import nightmare.module.combat.AntiBot;
/*     */ import nightmare.module.combat.AutoClicker;
/*     */ import nightmare.module.combat.BowAimAssist;
/*     */ import nightmare.module.combat.HitBox;
/*     */ import nightmare.module.combat.LegitAura;
/*     */ import nightmare.module.combat.Reach;
/*     */ import nightmare.module.combat.TriggerBot;
/*     */ import nightmare.module.combat.Velocity;
/*     */ import nightmare.module.misc.BlatantMode;
/*     */ import nightmare.module.misc.FPSBoost;
/*     */ import nightmare.module.misc.NameProtect;
/*     */ import nightmare.module.misc.NoPotionShift;
/*     */ import nightmare.module.misc.Particle;
/*     */ import nightmare.module.misc.Spin;
/*     */ import nightmare.module.movement.Blink;
/*     */ import nightmare.module.movement.FastBridge;
/*     */ import nightmare.module.movement.Flight;
/*     */ import nightmare.module.movement.GuiMove;
/*     */ import nightmare.module.movement.Sprint;
/*     */ import nightmare.module.movement.VClip;
/*     */ import nightmare.module.player.AutoArmor;
/*     */ import nightmare.module.player.AutoRod;
/*     */ import nightmare.module.player.AutoTool;
/*     */ import nightmare.module.player.FastPlace;
/*     */ import nightmare.module.player.Freecam;
/*     */ import nightmare.module.player.NoFall;
/*     */ import nightmare.module.render.Blur;
/*     */ import nightmare.module.render.Chams;
/*     */ import nightmare.module.render.Chat;
/*     */ import nightmare.module.render.ClickGUI;
/*     */ import nightmare.module.render.ESP;
/*     */ import nightmare.module.render.Fullbright;
/*     */ import nightmare.module.render.HUD;
/*     */ import nightmare.module.render.MotionBlur;
/*     */ import nightmare.module.world.AntiInvisible;
/*     */ import nightmare.module.world.AutoHypixel;
/*     */ import nightmare.module.world.ChestStealer;
/*     */ import nightmare.module.world.LightningTracker;
/*     */ import nightmare.module.world.StaffAnalyser;
/*     */ import nightmare.module.world.TimeChanger;
/*     */ import nightmare.module.world.Timer;
/*     */ 
/*     */ 
/*     */ public class ModuleManager
/*     */ {
/*  50 */   public ArrayList<Module> modules = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public ModuleManager() {
/*  55 */     this.modules.add(new AntiBot());
/*  56 */     this.modules.add(new AimAssist());
/*  57 */     this.modules.add(new AutoClicker());
/*  58 */     this.modules.add(new BowAimAssist());
/*  59 */     this.modules.add(new Reach());
/*  60 */     this.modules.add(new HitBox());
/*  61 */     this.modules.add(new LegitAura());
/*  62 */     this.modules.add(new TriggerBot());
/*  63 */     this.modules.add(new Velocity());
/*     */ 
/*     */     
/*  66 */     this.modules.add(new Blink());
/*  67 */     this.modules.add(new FastBridge());
/*  68 */     this.modules.add(new Flight());
/*  69 */     this.modules.add(new GuiMove());
/*  70 */     this.modules.add(new Sprint());
/*  71 */     this.modules.add(new VClip());
/*     */ 
/*     */     
/*  74 */     this.modules.add(new AutoArmor());
/*  75 */     this.modules.add(new AutoRod());
/*  76 */     this.modules.add(new AutoTool());
/*  77 */     this.modules.add(new FastPlace());
/*  78 */     this.modules.add(new Freecam());
/*  79 */     this.modules.add(new NoFall());
/*     */ 
/*     */     
/*  82 */     this.modules.add(new Blur());
/*  83 */     this.modules.add(new Chams());
/*  84 */     this.modules.add(new Chat());
/*  85 */     this.modules.add(new ClickGUI());
/*  86 */     this.modules.add(new ESP());
/*  87 */     this.modules.add(new Fullbright());
/*  88 */     this.modules.add(new HUD());
/*  89 */     this.modules.add(new MotionBlur());
/*     */ 
/*     */     
/*  92 */     this.modules.add(new AntiInvisible());
/*  93 */     this.modules.add(new AutoHypixel());
/*  94 */     this.modules.add(new ChestStealer());
/*  95 */     this.modules.add(new LightningTracker());
/*  96 */     this.modules.add(new StaffAnalyser());
/*  97 */     this.modules.add(new TimeChanger());
/*  98 */     this.modules.add(new Timer());
/*     */ 
/*     */     
/* 101 */     this.modules.add(new BlatantMode());
/* 102 */     this.modules.add(new FPSBoost());
/* 103 */     this.modules.add(new NameProtect());
/* 104 */     this.modules.add(new NoPotionShift());
/* 105 */     this.modules.add(new Particle());
/* 106 */     this.modules.add(new Spin());
/*     */   }
/*     */   
/*     */   public ArrayList<Module> getModules() {
/* 110 */     return this.modules;
/*     */   }
/*     */   
/*     */   public Module getModuleByName(String name) {
/* 114 */     return this.modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
/*     */   }
/*     */   
/*     */   public ArrayList<Module> getModulesInCategory(Category c) {
/* 118 */     ArrayList<Module> module = new ArrayList<>();
/* 119 */     for (Module m : this.modules) {
/* 120 */       if (m.getCategory() == c) {
/* 121 */         module.add(m);
/*     */       }
/*     */     } 
/* 124 */     return module;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\ModuleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */