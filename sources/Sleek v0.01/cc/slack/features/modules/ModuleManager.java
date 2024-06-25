package cc.slack.features.modules;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.impl.combat.AntiFireball;
import cc.slack.features.modules.impl.combat.Criticals;
import cc.slack.features.modules.impl.combat.FastBow;
import cc.slack.features.modules.impl.combat.Hitbox;
import cc.slack.features.modules.impl.combat.KillAura;
import cc.slack.features.modules.impl.combat.Velocity;
import cc.slack.features.modules.impl.exploit.ChatBypass;
import cc.slack.features.modules.impl.exploit.Disabler;
import cc.slack.features.modules.impl.exploit.Kick;
import cc.slack.features.modules.impl.exploit.MultiAction;
import cc.slack.features.modules.impl.exploit.Phase;
import cc.slack.features.modules.impl.exploit.Regen;
import cc.slack.features.modules.impl.ghost.AimAssist;
import cc.slack.features.modules.impl.ghost.AimBot;
import cc.slack.features.modules.impl.ghost.AutoTool;
import cc.slack.features.modules.impl.ghost.Autoclicker;
import cc.slack.features.modules.impl.ghost.Backtrack;
import cc.slack.features.modules.impl.ghost.JumpReset;
import cc.slack.features.modules.impl.ghost.KeepSprint;
import cc.slack.features.modules.impl.ghost.LegitNofall;
import cc.slack.features.modules.impl.ghost.LegitScaffold;
import cc.slack.features.modules.impl.ghost.PearlAntiVoid;
import cc.slack.features.modules.impl.ghost.Reach;
import cc.slack.features.modules.impl.ghost.Stap;
import cc.slack.features.modules.impl.ghost.Wtap;
import cc.slack.features.modules.impl.movement.Flight;
import cc.slack.features.modules.impl.movement.InvMove;
import cc.slack.features.modules.impl.movement.Jesus;
import cc.slack.features.modules.impl.movement.NoSlow;
import cc.slack.features.modules.impl.movement.NoWeb;
import cc.slack.features.modules.impl.movement.SafeWalk;
import cc.slack.features.modules.impl.movement.Speed;
import cc.slack.features.modules.impl.movement.Sprint;
import cc.slack.features.modules.impl.movement.Step;
import cc.slack.features.modules.impl.other.AntiBot;
import cc.slack.features.modules.impl.other.AutoRespawn;
import cc.slack.features.modules.impl.other.Killsults;
import cc.slack.features.modules.impl.other.Performance;
import cc.slack.features.modules.impl.other.RemoveEffect;
import cc.slack.features.modules.impl.other.Targets;
import cc.slack.features.modules.impl.other.Tweaks;
import cc.slack.features.modules.impl.player.AntiVoid;
import cc.slack.features.modules.impl.player.Blink;
import cc.slack.features.modules.impl.player.FastEat;
import cc.slack.features.modules.impl.player.FreeLook;
import cc.slack.features.modules.impl.player.NoFall;
import cc.slack.features.modules.impl.player.SpeedMine;
import cc.slack.features.modules.impl.player.TestBlink;
import cc.slack.features.modules.impl.player.TimerModule;
import cc.slack.features.modules.impl.render.Ambience;
import cc.slack.features.modules.impl.render.Animations;
import cc.slack.features.modules.impl.render.Bobbing;
import cc.slack.features.modules.impl.render.Cape;
import cc.slack.features.modules.impl.render.ChestESP;
import cc.slack.features.modules.impl.render.ClickGUI;
import cc.slack.features.modules.impl.render.ESP;
import cc.slack.features.modules.impl.render.HUD;
import cc.slack.features.modules.impl.render.Projectiles;
import cc.slack.features.modules.impl.render.ScoreboardModule;
import cc.slack.features.modules.impl.render.TargetHUD;
import cc.slack.features.modules.impl.render.XRay;
import cc.slack.features.modules.impl.utilties.AutoLogin;
import cc.slack.features.modules.impl.utilties.AutoPlay;
import cc.slack.features.modules.impl.utilties.LegitMode;
import cc.slack.features.modules.impl.utilties.PacketDebugger;
import cc.slack.features.modules.impl.world.Breaker;
import cc.slack.features.modules.impl.world.FastPlace;
import cc.slack.features.modules.impl.world.InvManager;
import cc.slack.features.modules.impl.world.Scaffold;
import cc.slack.features.modules.impl.world.Stealer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
   private final Map<Class<? extends Module>, Module> modules = new LinkedHashMap();

   public void initialize() {
      try {
         this.addModules(new AntiFireball(), new Criticals(), new Hitbox(), new KillAura(), new FastBow(), new Velocity(), new Flight(), new InvMove(), new Jesus(), new NoSlow(), new NoWeb(), new SafeWalk(), new Speed(), new Sprint(), new Step(), new AntiBot(), new AutoPlay(), new AutoRespawn(), new Performance(), new RemoveEffect(), new Killsults(), new Targets(), new Tweaks(), new AntiVoid(), new Blink(), new FastEat(), new FreeLook(), new SpeedMine(), new NoFall(), new TestBlink(), new TimerModule(), new Breaker(), new FastPlace(), new InvManager(), new Scaffold(), new Stealer(), new ChatBypass(), new Disabler(), new Kick(), new Regen(), new MultiAction(), new Phase(), new Animations(), new Ambience(), new Bobbing(), new Cape(), new ChestESP(), new ClickGUI(), new ESP(), new HUD(), new Projectiles(), new ScoreboardModule(), new TargetHUD(), new XRay(), new AimBot(), new AimAssist(), new Autoclicker(), new AutoTool(), new Backtrack(), new JumpReset(), new KeepSprint(), new LegitNofall(), new LegitScaffold(), new PearlAntiVoid(), new Reach(), new Stap(), new Wtap(), new AutoPlay(), new AutoLogin(), new LegitMode(), new PacketDebugger());
      } catch (Exception var2) {
      }

   }

   public List<Module> getModules() {
      return new ArrayList(this.modules.values());
   }

   public <T extends Module> T getInstance(Class<T> clazz) {
      return (Module)this.modules.get(clazz);
   }

   public Module getModuleByName(String n) {
      Iterator var2 = this.modules.values().iterator();

      Module m;
      do {
         if (!var2.hasNext()) {
            throw new IllegalArgumentException("Module not found.");
         }

         m = (Module)var2.next();
      } while(!m.getName().equals(n));

      return m;
   }

   private void addModules(Module... mod) {
      Module[] var2 = mod;
      int var3 = mod.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module m = var2[var4];
         this.modules.put(m.getClass(), m);
      }

   }

   public Module[] getModulesByCategory(Category c) {
      List<Module> category = new ArrayList();
      this.modules.forEach((aClass, module) -> {
         if (module.getCategory() == c) {
            category.add(module);
         }

      });
      return (Module[])category.toArray(new Module[0]);
   }

   public Module[] getModulesByCategoryABC(Category c) {
      Module[] modulesByCategory = this.getModulesByCategory(c);
      Arrays.sort(modulesByCategory, Comparator.comparing(Module::getName));
      return modulesByCategory;
   }
}
