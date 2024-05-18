/*    */ package me.eagler.module;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import me.eagler.module.modules.fun.BigItems;
/*    */ import me.eagler.module.modules.gui.ClickGUI;
/*    */ import me.eagler.module.modules.movement.NoSlowdown;
/*    */ import me.eagler.module.modules.player.BedDestroyer;
/*    */ import me.eagler.module.modules.player.ChestStealer;
/*    */ import me.eagler.module.modules.player.Knockback;
/*    */ import me.eagler.module.modules.render.AuraESP;
/*    */ import me.eagler.module.modules.render.BedESP;
/*    */ import me.eagler.module.modules.render.Bobbing;
/*    */ import me.eagler.module.modules.render.DamageIndicator;
/*    */ import me.eagler.module.modules.render.Hurtcam;
/*    */ import me.eagler.module.modules.render.ItemRenderer;
/*    */ import me.eagler.module.modules.render.PlayerESP;
/*    */ 
/*    */ public class ModuleManager {
/*    */   public void load() {
/* 20 */     this.modules = new CopyOnWriteArrayList<Module>();
/*    */     
/* 22 */     loadModules();
/*    */   }
/*    */   
/*    */   private List<Module> modules;
/*    */   
/*    */   public void loadModules() {
/* 28 */     addModule((Module)new Brightness());
/* 29 */     addModule((Module)new Speed());
/* 30 */     addModule((Module)new ClickGUI());
/* 31 */     addModule((Module)new Killaura());
/* 32 */     addModule((Module)new ScaffoldWalk());
/* 33 */     addModule((Module)new Knockback());
/* 34 */     addModule((Module)new ChestStealer());
/* 35 */     addModule((Module)new AutoArmor());
/* 36 */     addModule((Module)new PlayerESP());
/* 37 */     addModule((Module)new DamageIndicator());
/* 38 */     addModule((Module)new InvMove());
/* 39 */     addModule((Module)new InvCleaner());
/* 40 */     addModule((Module)new ChestESP());
/* 41 */     addModule((Module)new NoSlowdown());
/* 42 */     addModule((Module)new Glide());
/* 43 */     addModule((Module)new Sprint());
/* 44 */     addModule((Module)new NameChange());
/* 45 */     addModule((Module)new NoScoreboard());
/* 46 */     addModule((Module)new AutoConfig());
/* 47 */     addModule((Module)new AuraESP());
/* 48 */     addModule((Module)new BedDestroyer());
/* 49 */     addModule((Module)new BedESP());
/* 50 */     addModule((Module)new Teams());
/* 51 */     addModule((Module)new Zoom());
/* 52 */     addModule((Module)new Hurtcam());
/* 53 */     addModule((Module)new Flight());
/* 54 */     addModule((Module)new ItemRenderer());
/* 55 */     addModule((Module)new Bobbing());
/* 56 */     addModule((Module)new Theme());
/* 57 */     addModule((Module)new TestModule());
/* 58 */     addModule((Module)new BigItems());
/* 59 */     addModule((Module)new Tracers());
/* 60 */     addModule((Module)new LongJump());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addModule(Module module) {
/* 67 */     this.modules.add(module);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Module> getModules() {
/* 73 */     return this.modules;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Module getModuleByName(String name) {
/* 79 */     for (Module module : getModules()) {
/*    */       
/* 81 */       if (module.getName().equalsIgnoreCase(name))
/*    */       {
/* 83 */         return module;
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 89 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\ModuleManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */