package vestige.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import vestige.module.impl.client.NotificationManager;
import vestige.module.impl.combat.AimAssist;
import vestige.module.impl.combat.Antibot;
import vestige.module.impl.combat.Autoclicker;
import vestige.module.impl.combat.Criticals;
import vestige.module.impl.combat.ItemBot;
import vestige.module.impl.combat.Killaura;
import vestige.module.impl.combat.PingSpoofer;
import vestige.module.impl.combat.Reach;
import vestige.module.impl.combat.TargetStrafe;
import vestige.module.impl.combat.Teams;
import vestige.module.impl.combat.Tickbase;
import vestige.module.impl.combat.Velocity;
import vestige.module.impl.combat.WTap;
import vestige.module.impl.exploit.AutoRespawn;
import vestige.module.impl.exploit.ClientSpoofer;
import vestige.module.impl.exploit.Disabler;
import vestige.module.impl.exploit.FlagDetector;
import vestige.module.impl.exploit.Regen;
import vestige.module.impl.exploit.UnFlag;
import vestige.module.impl.misc.AnticheatModule;
import vestige.module.impl.misc.Autoplay;
import vestige.module.impl.misc.KillSult;
import vestige.module.impl.misc.SelfDestruct;
import vestige.module.impl.misc.StaffDetector;
import vestige.module.impl.misc.Targets;
import vestige.module.impl.movement.Fly;
import vestige.module.impl.movement.InventoryMove;
import vestige.module.impl.movement.Longjump;
import vestige.module.impl.movement.Noslow;
import vestige.module.impl.movement.Parkour;
import vestige.module.impl.movement.Safewalk;
import vestige.module.impl.movement.Speed;
import vestige.module.impl.movement.Sprint;
import vestige.module.impl.movement.Step;
import vestige.module.impl.player.Antivoid;
import vestige.module.impl.player.AutoPot;
import vestige.module.impl.player.Autoplace;
import vestige.module.impl.player.Blink;
import vestige.module.impl.player.ChestStealer;
import vestige.module.impl.player.FastPlace;
import vestige.module.impl.player.Freecam;
import vestige.module.impl.player.InventoryManager;
import vestige.module.impl.player.Nofall;
import vestige.module.impl.player.Timer;
import vestige.module.impl.visual.Animations;
import vestige.module.impl.visual.AntiInvis;
import vestige.module.impl.visual.BedESP;
import vestige.module.impl.visual.BlockOrvelay;
import vestige.module.impl.visual.Breadcrumbs;
import vestige.module.impl.visual.Capes;
import vestige.module.impl.visual.Chams;
import vestige.module.impl.visual.ChestESP;
import vestige.module.impl.visual.ClickGuiModule;
import vestige.module.impl.visual.ClientTheme;
import vestige.module.impl.visual.Crosshair;
import vestige.module.impl.visual.CustomUI;
import vestige.module.impl.visual.ESP;
import vestige.module.impl.visual.FPSCounter;
import vestige.module.impl.visual.FovChanger;
import vestige.module.impl.visual.Freelook;
import vestige.module.impl.visual.Fullbright;
import vestige.module.impl.visual.IngameInfo;
import vestige.module.impl.visual.ModuleList;
import vestige.module.impl.visual.NameProtect;
import vestige.module.impl.visual.Nametags;
import vestige.module.impl.visual.NoHurtcam;
import vestige.module.impl.visual.Rotations;
import vestige.module.impl.visual.TargetHUD;
import vestige.module.impl.visual.TimeChanger;
import vestige.module.impl.visual.Tracers;
import vestige.module.impl.visual.Watermark;
import vestige.module.impl.visual.Xray;
import vestige.module.impl.world.AutoBridge;
import vestige.module.impl.world.AutoSoup;
import vestige.module.impl.world.AutoTool;
import vestige.module.impl.world.Breaker;
import vestige.module.impl.world.Eagle;
import vestige.module.impl.world.FastUse;
import vestige.module.impl.world.Phase;
import vestige.module.impl.world.ScaffoldV2;

public class ModuleManager {
   public final List<Module> modules = new ArrayList();
   public List<HUDModule> hudModules;

   public ModuleManager() {
      this.modules.add(new Killaura());
      this.modules.add(new Velocity());
      this.modules.add(new TargetStrafe());
      this.modules.add(new Teams());
      this.modules.add(new AutoSoup());
      this.modules.add(new AutoTool());
      this.modules.add(new Capes());
      this.modules.add(new Tracers());
      this.modules.add(new Tickbase());
      this.modules.add(new FovChanger());
      this.modules.add(new UnFlag());
      this.modules.add(new Regen());
      this.modules.add(new AntiInvis());
      this.modules.add(new Eagle());
      this.modules.add(new Reach());
      this.modules.add(new ChestESP());
      this.modules.add(new Autoclicker());
      this.modules.add(new AimAssist());
      this.modules.add(new AutoRespawn());
      this.modules.add(new FlagDetector());
      this.modules.add(new FastUse());
      this.modules.add(new WTap());
      this.modules.add(new Criticals());
      this.modules.add(new Antibot());
      this.modules.add(new CustomUI());
      this.modules.add(new Sprint());
      this.modules.add(new Fly());
      this.modules.add(new ClientSpoofer());
      this.modules.add(new KillSult());
      this.modules.add(new Speed());
      this.modules.add(new Longjump());
      this.modules.add(new InventoryMove());
      this.modules.add(new Noslow());
      this.modules.add(new Safewalk());
      this.modules.add(new Step());
      this.modules.add(new FPSCounter());
      this.modules.add(new StaffDetector());
      this.modules.add(new ChestStealer());
      this.modules.add(new InventoryManager());
      this.modules.add(new Nofall());
      this.modules.add(new Antivoid());
      this.modules.add(new Breadcrumbs());
      this.modules.add(new BlockOrvelay());
      this.modules.add(new ItemBot());
      this.modules.add(new Targets());
      this.modules.add(new Timer());
      this.modules.add(new FastPlace());
      this.modules.add(new Phase());
      this.modules.add(new NotificationManager());
      this.modules.add(new Autoplace());
      this.modules.add(new ScaffoldV2());
      this.modules.add(new AutoPot());
      this.modules.add(new AutoBridge());
      this.modules.add(new Breaker());
      this.modules.add(new Crosshair());
      this.modules.add(new Watermark());
      this.modules.add(new ModuleList());
      this.modules.add(new IngameInfo());
      this.modules.add(new ClientTheme());
      this.modules.add(new ClickGuiModule());
      this.modules.add(new PingSpoofer());
      this.modules.add(new Freecam());
      this.modules.add(new Parkour());
      this.modules.add(new Nametags());
      this.modules.add(new ESP());
      this.modules.add(new Blink());
      this.modules.add(new BedESP());
      this.modules.add(new Chams());
      this.modules.add(new Animations());
      this.modules.add(new Rotations());
      this.modules.add(new TargetHUD());
      this.modules.add(new Freelook());
      this.modules.add(new TimeChanger());
      this.modules.add(new Fullbright());
      this.modules.add(new NameProtect());
      this.modules.add(new Xray());
      this.modules.add(new NoHurtcam());
      this.modules.add(new Disabler());
      this.modules.add(new AnticheatModule());
      this.modules.add(new Autoplay());
      this.modules.add(new SelfDestruct());
      Stream var10001 = this.modules.stream();
      Objects.requireNonNull(HUDModule.class);
      var10001 = var10001.filter(HUDModule.class::isInstance);
      Objects.requireNonNull(HUDModule.class);
      this.hudModules = (List) var10001.map(HUDModule.class::cast).collect(Collectors.toList());
   }

   public <T extends Module> T getModule(Class<T> clazz) {
      Optional<Module> module = modules.stream().filter(m -> m.getClass().equals(clazz)).findFirst();

      if (module.isPresent()) {
         return (T) module.get();
      } else {
         return null;
      }
   }

   public <T extends Module> T getModuleByName(String name) {
      Optional<Module> module = modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst();

      if (module.isPresent()) {
         return (T) module.get();
      }

      return null;
   }

   public <T extends Module> T getModuleByNameNoSpace(String name) {
      Optional<Module> module = modules.stream().filter(m -> m.getName().replace(" ", "").equalsIgnoreCase(name)).findFirst();

      if (module.isPresent()) {
         return (T) module.get();
      }

      return null;
   }
}
