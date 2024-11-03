package xyz.cucumber.base.module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import xyz.cucumber.base.module.addons.Dragable;
import xyz.cucumber.base.module.feat.combat.AnnoyerModule;
import xyz.cucumber.base.module.feat.combat.AntiBotModule;
import xyz.cucumber.base.module.feat.combat.AuraModule;
import xyz.cucumber.base.module.feat.combat.AutoClickerModule;
import xyz.cucumber.base.module.feat.combat.AutoRodModule;
import xyz.cucumber.base.module.feat.combat.BackTrackModule;
import xyz.cucumber.base.module.feat.combat.BowAimBotModule;
import xyz.cucumber.base.module.feat.combat.CriticalsModule;
import xyz.cucumber.base.module.feat.combat.DelayFixModule;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.combat.LagRangeModule;
import xyz.cucumber.base.module.feat.combat.MoreKBModule;
import xyz.cucumber.base.module.feat.combat.NoHitSlowModule;
import xyz.cucumber.base.module.feat.combat.ReachModule;
import xyz.cucumber.base.module.feat.combat.STapModule;
import xyz.cucumber.base.module.feat.combat.TimerRangeModule;
import xyz.cucumber.base.module.feat.combat.VelocityModule;
import xyz.cucumber.base.module.feat.movement.AirStuckModule;
import xyz.cucumber.base.module.feat.movement.BlinkModule;
import xyz.cucumber.base.module.feat.movement.FlightModule;
import xyz.cucumber.base.module.feat.movement.IceSpeedModule;
import xyz.cucumber.base.module.feat.movement.InvMoveModule;
import xyz.cucumber.base.module.feat.movement.JesusModule;
import xyz.cucumber.base.module.feat.movement.LongJumpModule;
import xyz.cucumber.base.module.feat.movement.NoFallModule;
import xyz.cucumber.base.module.feat.movement.NoJumpDelayModule;
import xyz.cucumber.base.module.feat.movement.NoRotateModule;
import xyz.cucumber.base.module.feat.movement.NoSlowModule;
import xyz.cucumber.base.module.feat.movement.NoWebModule;
import xyz.cucumber.base.module.feat.movement.SpeedModule;
import xyz.cucumber.base.module.feat.movement.SprintModule;
import xyz.cucumber.base.module.feat.movement.TimerModule;
import xyz.cucumber.base.module.feat.other.ArrayListModule;
import xyz.cucumber.base.module.feat.other.BoosterModule;
import xyz.cucumber.base.module.feat.other.ClickGuiModule;
import xyz.cucumber.base.module.feat.other.DebugModule;
import xyz.cucumber.base.module.feat.other.FriendsModule;
import xyz.cucumber.base.module.feat.other.NotificationsModule;
import xyz.cucumber.base.module.feat.other.ReverseFriendsModule;
import xyz.cucumber.base.module.feat.other.ScoreboardModule;
import xyz.cucumber.base.module.feat.other.SessionModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.feat.other.TrackViewerModule;
import xyz.cucumber.base.module.feat.other.WatermarkModule;
import xyz.cucumber.base.module.feat.player.AntiFireModule;
import xyz.cucumber.base.module.feat.player.AutoArmorModule;
import xyz.cucumber.base.module.feat.player.AutoHealModule;
import xyz.cucumber.base.module.feat.player.AutoToolModule;
import xyz.cucumber.base.module.feat.player.AutoWeaponModule;
import xyz.cucumber.base.module.feat.player.BreakerModule;
import xyz.cucumber.base.module.feat.player.CrasherModule;
import xyz.cucumber.base.module.feat.player.CustomDisablerModule;
import xyz.cucumber.base.module.feat.player.DisablerModule;
import xyz.cucumber.base.module.feat.player.InvManagerModule;
import xyz.cucumber.base.module.feat.player.NameProtectModule;
import xyz.cucumber.base.module.feat.player.PingSpoofModule;
import xyz.cucumber.base.module.feat.player.RegenModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.feat.player.SmoothRotationModule;
import xyz.cucumber.base.module.feat.player.SpammerModule;
import xyz.cucumber.base.module.feat.player.StaffDetectorModule;
import xyz.cucumber.base.module.feat.player.StealerModule;
import xyz.cucumber.base.module.feat.visuals.AmbianceModule;
import xyz.cucumber.base.module.feat.visuals.AnimationsModule;
import xyz.cucumber.base.module.feat.visuals.BetterItemsModule;
import xyz.cucumber.base.module.feat.visuals.ChamsModule;
import xyz.cucumber.base.module.feat.visuals.ChinaHatModule;
import xyz.cucumber.base.module.feat.visuals.ESP3DModule;
import xyz.cucumber.base.module.feat.visuals.ESPModule;
import xyz.cucumber.base.module.feat.visuals.EffectStatusModule;
import xyz.cucumber.base.module.feat.visuals.FullBrightModule;
import xyz.cucumber.base.module.feat.visuals.HitMarkModule;
import xyz.cucumber.base.module.feat.visuals.HotbarModule;
import xyz.cucumber.base.module.feat.visuals.HurtCamModule;
import xyz.cucumber.base.module.feat.visuals.JumpCirclesModule;
import xyz.cucumber.base.module.feat.visuals.MurderFinderModule;
import xyz.cucumber.base.module.feat.visuals.NameTagsModule;
import xyz.cucumber.base.module.feat.visuals.RadarModule;
import xyz.cucumber.base.module.feat.visuals.ShaderESPModule;
import xyz.cucumber.base.module.feat.visuals.StorageESPModule;
import xyz.cucumber.base.module.feat.visuals.TargetESPModule;
import xyz.cucumber.base.module.feat.visuals.TargetHudModule;
import xyz.cucumber.base.module.feat.visuals.TracersModule;
import xyz.cucumber.base.module.feat.visuals.TrailModule;

public class ModuleManager {
   private ArrayList<Mod> modules = new ArrayList<>();
   private ArrayList<Mod> draggable = new ArrayList<>();

   public ModuleManager() {
      this.modules.add(new AntiBotModule());
      this.modules.add(new AutoClickerModule());
      this.modules.add(new AutoRodModule());
      this.modules.add(new BackTrackModule());
      this.modules.add(new BowAimBotModule());
      this.modules.add(new CriticalsModule());
      this.modules.add(new DelayFixModule());
      this.modules.add(new AnnoyerModule());
      this.modules.add(new AuraModule());
      this.modules.add(new KillAuraModule());
      this.modules.add(new MoreKBModule());
      this.modules.add(new NoHitSlowModule());
      this.modules.add(new ReachModule());
      this.modules.add(new STapModule());
      this.modules.add(new TimerRangeModule());
      this.modules.add(new VelocityModule());
      this.modules.add(new AirStuckModule());
      this.modules.add(new LagRangeModule());
      this.modules.add(new FlightModule());
      this.modules.add(new IceSpeedModule());
      this.modules.add(new InvMoveModule());
      this.modules.add(new JesusModule());
      this.modules.add(new LongJumpModule());
      this.modules.add(new NoFallModule());
      this.modules.add(new NoJumpDelayModule());
      this.modules.add(new NoRotateModule());
      this.modules.add(new NoSlowModule());
      this.modules.add(new NoWebModule());
      this.modules.add(new SpeedModule());
      this.modules.add(new SprintModule());
      this.modules.add(new TimerModule());
      this.modules.add(new ArrayListModule());
      this.modules.add(new BoosterModule());
      this.modules.add(new ClickGuiModule());
      this.modules.add(new FriendsModule());
      this.modules.add(new NotificationsModule());
      this.modules.add(new ScoreboardModule());
      this.modules.add(new SessionModule());
      this.modules.add(new TeamsModule());
      this.modules.add(new WatermarkModule());
      this.modules.add(new TrackViewerModule());
      this.modules.add(new AutoArmorModule());
      this.modules.add(new AutoHealModule());
      this.modules.add(new AutoToolModule());
      this.modules.add(new AutoWeaponModule());
      this.modules.add(new CrasherModule());
      this.modules.add(new DisablerModule());
      this.modules.add(new AntiFireModule());
      this.modules.add(new InvManagerModule());
      this.modules.add(new NameProtectModule());
      this.modules.add(new PingSpoofModule());
      this.modules.add(new ScaffoldModule());
      this.modules.add(new SmoothRotationModule());
      this.modules.add(new SpammerModule());
      this.modules.add(new StealerModule());
      this.modules.add(new StaffDetectorModule());
      this.modules.add(new AmbianceModule());
      this.modules.add(new AnimationsModule());
      this.modules.add(new BetterItemsModule());
      this.modules.add(new EffectStatusModule());
      this.modules.add(new ESP3DModule());
      this.modules.add(new ESPModule());
      this.modules.add(new FullBrightModule());
      this.modules.add(new HotbarModule());
      this.modules.add(new HurtCamModule());
      this.modules.add(new ChamsModule());
      this.modules.add(new ChinaHatModule());
      this.modules.add(new JumpCirclesModule());
      this.modules.add(new NameTagsModule());
      this.modules.add(new RadarModule());
      this.modules.add(new ShaderESPModule());
      this.modules.add(new StorageESPModule());
      this.modules.add(new TargetESPModule());
      this.modules.add(new TargetHudModule());
      this.modules.add(new TracersModule());
      this.modules.add(new TrailModule());
      this.modules.add(new MurderFinderModule());
      this.modules.add(new ReverseFriendsModule());
      this.modules.add(new RegenModule());
      this.modules.add(new HitMarkModule());
      this.modules.add(new BlinkModule());
      this.modules.add(new BreakerModule());
      this.modules.add(new DebugModule());
      this.modules.add(new CustomDisablerModule());

      for (Mod m : this.modules) {
         if (m instanceof Dragable) {
            this.draggable.add(m);
         }
      }
   }

   public ArrayList<Mod> getDraggable() {
      return this.draggable;
   }

   public ArrayList<Mod> getModules() {
      return this.modules;
   }

   public Mod getModule(Class modclass) {
      return this.modules.stream().filter(m -> m.getClass() == modclass).findFirst().orElse(null);
   }

   public Mod getModule(String name) {
      return this.modules.stream().filter(m -> m.getName().toLowerCase().replace(" ", "").equals(name.toLowerCase().replace(" ", ""))).findFirst().orElse(null);
   }

   public List<Mod> getModulesByCategory(Category c) {
      return this.modules.stream().filter(m -> m.getCategory() == c).collect(Collectors.toList());
   }

   public List<Mod> getEnabledModules() {
      List<Mod> modules = new ArrayList<>();

      for (Mod m : this.getModules()) {
         if (m.isEnabled()) {
            modules.add(m);
         }
      }

      return modules;
   }
}
