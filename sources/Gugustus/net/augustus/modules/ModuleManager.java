package net.augustus.modules;

import java.util.ArrayList;
import java.util.List;

import net.augustus.modules.combat.AntiBot;
import net.augustus.modules.combat.AntiFireBall;
import net.augustus.modules.combat.AutoClicker;
import net.augustus.modules.combat.AutoProjectile;
import net.augustus.modules.combat.AutoRod;
import net.augustus.modules.combat.AutoSoup;
import net.augustus.modules.combat.BackTrack;
import net.augustus.modules.combat.Criticals;
import net.augustus.modules.combat.FastBow;
import net.augustus.modules.combat.KillAura;
import net.augustus.modules.combat.MoreKB;
import net.augustus.modules.combat.NewTimerRange;
import net.augustus.modules.combat.OldTimerRange;
import net.augustus.modules.combat.Teams;
import net.augustus.modules.combat.TimerRange;
import net.augustus.modules.combat.Velocity;
import net.augustus.modules.misc.*;
import net.augustus.modules.movement.Blink;
import net.augustus.modules.movement.BugUp;
import net.augustus.modules.movement.FastFall;
import net.augustus.modules.movement.FastLadder;
import net.augustus.modules.movement.Fly;
import net.augustus.modules.movement.Jesus;
import net.augustus.modules.movement.LongJump;
import net.augustus.modules.movement.NoSlow;
import net.augustus.modules.movement.NoSlow2;
import net.augustus.modules.movement.NoWeb;
import net.augustus.modules.movement.SafeWalk;
import net.augustus.modules.movement.Speed;
import net.augustus.modules.movement.Spider;
import net.augustus.modules.movement.Sprint;
import net.augustus.modules.movement.Step;
import net.augustus.modules.movement.Strafe;
import net.augustus.modules.movement.TargetStrafe;
import net.augustus.modules.movement.Timer;
import net.augustus.modules.movement.VClip;
import net.augustus.modules.player.AntiVoid;
import net.augustus.modules.player.AutoArmor;
import net.augustus.modules.player.AutoExtinguish;
import net.augustus.modules.player.AutoTool;
import net.augustus.modules.player.ChestStealer;
import net.augustus.modules.player.FakeLag;
import net.augustus.modules.player.Inventory;
import net.augustus.modules.player.InventoryCleaner;
import net.augustus.modules.player.NoFall;
import net.augustus.modules.player.Phase;
import net.augustus.modules.player.Regen;
import net.augustus.modules.player.Teleport;
import net.augustus.modules.render.Ambiance;
import net.augustus.modules.render.AttackEffects;
import net.augustus.modules.render.Barriers;
import net.augustus.modules.render.BlockAnimation;
import net.augustus.modules.render.BlockESP;
import net.augustus.modules.render.Cape;
import net.augustus.modules.render.Chat;
import net.augustus.modules.render.ChinaHat;
import net.augustus.modules.render.ClickGUI;
import net.augustus.modules.render.CrossHair;
import net.augustus.modules.render.CustomGlint;
import net.augustus.modules.render.CustomItemPos;
import net.augustus.modules.render.ESP;
import net.augustus.modules.render.FogModifier;
import net.augustus.modules.render.FullBright;
import net.augustus.modules.render.HUD;
import net.augustus.modules.render.HurtCam;
import net.augustus.modules.render.ItemEsp;
import net.augustus.modules.render.Line;
import net.augustus.modules.render.MotionGraph;
import net.augustus.modules.render.NameTags;
import net.augustus.modules.render.NoOverlay;
import net.augustus.modules.render.Notifications;
import net.augustus.modules.render.OldArmor;
import net.augustus.modules.render.Projectiles;
import net.augustus.modules.render.Protector;
import net.augustus.modules.render.ResourcesDisplay;
import net.augustus.modules.render.Scoreboard;
import net.augustus.modules.render.Shaders;
import net.augustus.modules.render.StorageESP;
import net.augustus.modules.render.Tracers;
import net.augustus.modules.render.Trajectories;
import net.augustus.modules.world.BlockFly;
import net.augustus.modules.world.FastBreak;
import net.augustus.modules.world.FastPlace;
import net.augustus.modules.world.Fucker;
import net.augustus.utils.interfaces.MA;

public class ModuleManager implements MA {
   public final Velocity velocity = new Velocity();
   public final KillAura killAura = new KillAura();
   public final AntiBot antiBot = new AntiBot();
   public final Teams teams = new Teams();
   public final BackTrack backTrack = new BackTrack();
   public final AutoSoup autoSoup = new AutoSoup();
   public final AutoClicker autoClicker = new AutoClicker();
   public final AntiFireBall antiFireBall = new AntiFireBall();
   public final TimerRange timerRange = new TimerRange();
   public final Criticals criticals = new Criticals();
   public final MotionGraph motionGraph = new MotionGraph();
   public final MoreKB moreKB = new MoreKB();
   public final MurderThingyIdk murderThingyIdk = new MurderThingyIdk();
   public final net.augustus.modules.render.ArrayList arrayList = new net.augustus.modules.render.ArrayList();
   public final ClickGUI clickGUI = new ClickGUI();
   public final FastBow fastBow = new FastBow();
   public final AttackEffects attackEffects = new AttackEffects();
   public final ESP esp = new ESP();
   public final StorageESP storageESP = new StorageESP();
   public final BlockAnimation blockAnimation = new BlockAnimation();
   public final FullBright fullBright = new FullBright();
   public final Protector protector = new Protector();
   public final ChinaHat chinaHat = new ChinaHat();
   public final NameTags nameTags = new NameTags();
   public final HUD hud = new HUD();
   public final Tracers tracers = new Tracers();
   public final ItemEsp itemEsp = new ItemEsp();
   public final Scoreboard scoreboard = new Scoreboard();
   public final BlockESP blockESP = new BlockESP();
   public final Barriers barriers = new Barriers();
   public final Line line = new Line();
   public final Ambiance ambiance = new Ambiance();
   public final CrossHair crossHair = new CrossHair();
   public final Trajectories trajectories = new Trajectories();
   public final Projectiles projectiles = new Projectiles();
   public final CustomGlint customGlint = new CustomGlint();
   public final CustomItemPos customItemPos = new CustomItemPos();
   public final Sprint sprint = new Sprint();
   public final Jesus jesus = new Jesus();
   public final NoSlow noSlow = new NoSlow();
   public final Speed speed = new Speed();
   public final Timer timer = new Timer();
   public final Strafe strafe = new Strafe();
   public final Blink blink = new Blink();
   public final TestModule testModule = new TestModule();
   public final Fly fly = new Fly();
   public final BugUp bugUp = new BugUp();
   public final TargetStrafe targetStrafe = new TargetStrafe();
   public final FastLadder fastLadder = new FastLadder();
   public final Spider spider = new Spider();
   public final NoWeb noWeb = new NoWeb();
   public final Step step = new Step();
   public final LongJump longJump = new LongJump();
   public final VClip vclip = new VClip();
   public final SafeWalk safeWalk = new SafeWalk();
   public final NoFall noFall = new NoFall();
   public final AutoArmor autoArmor = new AutoArmor();
   public final InventoryCleaner inventoryCleaner = new InventoryCleaner();
   public final ChestStealer chestStealer = new ChestStealer();
   public static boolean cracked = true;
   public final Inventory inventory = new Inventory();
   public final Notifications notifications = new Notifications();

   public final AutoTool autoTool = new AutoTool();
   public final FakeLag fakeLag = new FakeLag();
   public final Teleport teleport = new Teleport();
   public final Phase phase = new Phase();
   public final Regen regen = new Regen();
   //public final ScaffoldWalk scaffoldWalk = new ScaffoldWalk();
   //public final NewScaffold newScaffold = new NewScaffold();
   public final FastBreak fastBreak = new FastBreak();
   public final Fucker fucker = new Fucker();
   public final FastPlace fastPlace = new FastPlace();
   public final BlockFly blockFly = new BlockFly();
   public final MidClick midClick = new MidClick();
   public final Disabler disabler = new Disabler();
   public final AutoPlay autoPlay = new AutoPlay();
   public final AutoRegister autoRegister = new AutoRegister();
   public final SpinBot spinBot = new SpinBot();
   public final Fixes fixes = new Fixes();
   public final AutoWalk autoWalk = new AutoWalk();
   public final StaffDetector staffDetector = new StaffDetector();
   public final Shaders shaders = new Shaders();
   
   public final OldTimerRange timerrange = new OldTimerRange();
   public final AutoRod autorod = new AutoRod();
   public final OldArmor oldarmor = new OldArmor();
   public final HurtCam hurtcam = new HurtCam();
   public final Cape cape = new Cape();
   public final HackerDetector hackerDetector = new HackerDetector();
   public final FogModifier fogModifier = new FogModifier();
   public final Chat chat = new Chat();
   public final NoOverlay nooverlay = new NoOverlay();
   public final AutoPartyChat autopartychat = new AutoPartyChat();
   public final IRC irc = new IRC();
   public final ResourcesDisplay resourcesdisplay = new ResourcesDisplay();
   public final AntiVoid antivoid = new AntiVoid();
//   public final AutoCrit autocrit = new AutoCrit();
//   public final AutoPearl autopearl = new AutoPearl();
   public final FastFall fastfall = new FastFall();
   public final ScriptingAPI scriptingapi = new ScriptingAPI();
   public final NoSlow2 noslow2 = new NoSlow2();
   public final PostDisabler postDisabler = new PostDisabler();
   public final FlagDetector mitigationDetection = new FlagDetector();
   public final NewTimerRange newtimerrange = new NewTimerRange();
   public final AutoProjectile autoprojectile = new AutoProjectile();
   public final AutoExtinguish autoextinguish = new AutoExtinguish();

    public List<Module> getModules() {
      return ma.getModules();
   }

   public List<Module> getModules(Categorys cat) {
      List<Module> modules = new ArrayList<>();
      for(Module mod : getModules()) {
         if(mod.getCategory().equals(cat)) {
            modules.add(mod);
         }
      }
      return modules;
   }


   public void setModules(List<Module> modules) {
      ma.setModules(modules);
   }

   public ArrayList<Module> getActiveModules() {
      return ma.getActiveModules();
   }

   public void setActiveModules(ArrayList<Module> activeModules) {
      ma.setActiveModules(activeModules);
   }
}
