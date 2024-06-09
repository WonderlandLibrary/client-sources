package exhibition.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import exhibition.Client;
import exhibition.event.EventSystem;
import exhibition.management.AbstractManager;
import exhibition.management.SubFolder;
import exhibition.management.keybinding.KeyMask;
import exhibition.management.keybinding.Keybind;
import exhibition.module.data.ModuleData;
import exhibition.module.data.MultiBool;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.impl.combat.AimAssist;
import exhibition.module.impl.combat.AntiBot;
import exhibition.module.impl.combat.AntiVelocity;
import exhibition.module.impl.combat.AutoArmor;
import exhibition.module.impl.combat.AutoClicker;
import exhibition.module.impl.combat.AutoPot;
import exhibition.module.impl.combat.AutoSoup;
import exhibition.module.impl.combat.AutoSword;
import exhibition.module.impl.combat.BowAimbot;
import exhibition.module.impl.combat.Criticals;
import exhibition.module.impl.combat.Killaura;
import exhibition.module.impl.gta.Aimbot;
import exhibition.module.impl.gta.AntiAim;
import exhibition.module.impl.gta.AutoBandage;
import exhibition.module.impl.gta.AutoIon;
import exhibition.module.impl.hud.ArmorStatus;
import exhibition.module.impl.hud.Enabled;
import exhibition.module.impl.hud.Radar;
import exhibition.module.impl.hud.TabGUI;
import exhibition.module.impl.hud.TargetHUD;
import exhibition.module.impl.movement.AntiFall;
import exhibition.module.impl.movement.BorderHop;
import exhibition.module.impl.movement.Debug;
import exhibition.module.impl.movement.DepthStrider;
import exhibition.module.impl.movement.Fly;
import exhibition.module.impl.movement.Jesus;
import exhibition.module.impl.movement.LongJump;
import exhibition.module.impl.movement.NoSlowdown;
import exhibition.module.impl.movement.Phase;
import exhibition.module.impl.movement.Speed;
import exhibition.module.impl.movement.Sprint;
import exhibition.module.impl.movement.Step;
import exhibition.module.impl.other.Animations;
import exhibition.module.impl.other.AntiDesync;
import exhibition.module.impl.other.AntiVanish;
import exhibition.module.impl.other.AutoGG;
import exhibition.module.impl.other.AutoSay;
import exhibition.module.impl.other.AutoTPA;
import exhibition.module.impl.other.BedFucker;
import exhibition.module.impl.other.ChatCommands;
import exhibition.module.impl.other.ChestStealer;
import exhibition.module.impl.other.DeathClip;
import exhibition.module.impl.other.FriendAlert;
import exhibition.module.impl.other.KopyKatt;
import exhibition.module.impl.other.MCF;
import exhibition.module.impl.other.PingSpoof;
import exhibition.module.impl.other.StreamerMode;
import exhibition.module.impl.other.Timer;
import exhibition.module.impl.player.AutoEat;
import exhibition.module.impl.player.AutoRespawn;
import exhibition.module.impl.player.AutoTool;
import exhibition.module.impl.player.FastPlace;
import exhibition.module.impl.player.FastUse;
import exhibition.module.impl.player.InventoryCleaner;
import exhibition.module.impl.player.InventoryWalk;
import exhibition.module.impl.player.ItemSpoof;
import exhibition.module.impl.player.NoFall;
import exhibition.module.impl.player.NoRotate;
import exhibition.module.impl.player.PotionSaver;
import exhibition.module.impl.player.Scaffold;
import exhibition.module.impl.player.SpeedMine;
import exhibition.module.impl.player.Teleport;
import exhibition.module.impl.render.Box;
import exhibition.module.impl.render.Brightness;
import exhibition.module.impl.render.Chams;
import exhibition.module.impl.render.ChestESP;
import exhibition.module.impl.render.Crosshair;
import exhibition.module.impl.render.DONOTFUCKINGDIEYOURETARD;
import exhibition.module.impl.render.ESP2D;
import exhibition.module.impl.render.Freecam;
import exhibition.module.impl.render.Lines;
import exhibition.module.impl.render.Nametags;
import exhibition.module.impl.render.Outline;
import exhibition.module.impl.render.SpookySkeltal;
import exhibition.module.impl.render.Tags;
import exhibition.module.impl.render.TargetESP;
import exhibition.module.impl.render.ViewClip;
import exhibition.module.impl.render.Waypoints;
import exhibition.module.impl.render.Weather;
import exhibition.module.impl.render.Xray;
import exhibition.util.FileUtils;
import exhibition.util.StringConversions;

public class ModuleManager extends AbstractManager {
   private static final File MODULE_DIR = FileUtils.getConfigFile("Mods");
   private static final File SETTINGS_DIR = FileUtils.getConfigFile("Sets");
   private boolean setup;

   public ModuleManager(Class clazz) {
      super(clazz, 0);
   }

   public void setup() {
      this.loadLocalPlugins();
      this.add(new Animations(new ModuleData(ModuleData.Type.Other, "Animations", "\"AdD mORe AnImAtIoNS\" Happy now? Commit die.")));
      this.add(new AutoGG(new ModuleData(ModuleData.Type.Other, "AutoGG", "Make those fucking kids chargeback MVP++")));
      this.add(new Waypoints(new ModuleData(ModuleData.Type.Visuals, "Waypoints", "Renders waypoints on screen.")));
      this.add(new Timer(new ModuleData(ModuleData.Type.Other, "GameSpeed", "Modifies game speed. Can be used to 'bow fly'.")));
      this.add(new Step(new ModuleData(ModuleData.Type.Movement, "Step", "Moves you up one block.")));
      this.add(new Teleport(new ModuleData(ModuleData.Type.Player, "ClickBlink", "Teleports you to the selected block.")));
      this.add(new Tags(new ModuleData(ModuleData.Type.Visuals, "3DTags", "Nametags but 3D.")));
      this.add(new Outline(new ModuleData(ModuleData.Type.Visuals, "Glow", "Makes players glow.")));
      this.add(new PingSpoof(new ModuleData(ModuleData.Type.Other, "PingSpoof", "Chokes KeepAlive packets to fake high ping.")));
      this.add(new Speed(new ModuleData(ModuleData.Type.Movement, "Speed", "ZOOOOOOOOOOOOM.")));
      this.add(new Criticals(new ModuleData(ModuleData.Type.Combat, "Criticals", "Critical attack each hit.")));
      this.add(new SpeedMine(new ModuleData(ModuleData.Type.Player, "SpeedMine", "Mine blocks faster")));
      this.add(new Brightness(new ModuleData(ModuleData.Type.Visuals, "Brightness", "Applies night vision")));
      this.add(new BowAimbot(new ModuleData(ModuleData.Type.Combat, "BowAimbot", "Aims at players & predicts movement.")));
      this.add(new FriendAlert(new ModuleData(ModuleData.Type.Other, "FriendAlert", "Event specific alerts for friends. (Joining/Dying)")));
      this.add(new ItemSpoof(new ModuleData(ModuleData.Type.Player, "ItemSpoof", "Spoofs your item with the top left inv item.")));
      this.add(new Lines(new ModuleData(ModuleData.Type.Visuals, "Lines", "Draws lines at entities.")));
      this.add(new DeathClip(new ModuleData(ModuleData.Type.Other, "DeathClip", "Teleports you on death.")));
      this.add(new AutoClicker(new ModuleData(ModuleData.Type.Combat, "AutoClicker", "Clicks automatically for you.")));
      this.add(new AimAssist(new ModuleData(ModuleData.Type.Combat, "AimAssist", "Aims for you.")));
      this.add(new DONOTFUCKINGDIEYOURETARD(new ModuleData(ModuleData.Type.Visuals, "Health", "Shows your health in the middle of the screen.")));
      this.add(new AutoTool(new ModuleData(ModuleData.Type.Player, "AutoTool", "Switches to best tool.")));
      this.add(new Scaffold(new ModuleData(ModuleData.Type.Movement, "Scaffold", "Silently places blocks.")));
      this.add(new AntiVanish(new ModuleData(ModuleData.Type.Other, "AntiVanish", "Alerts you of vanished staff members.")));
      this.add(new NoFall(new ModuleData(ModuleData.Type.Player, "NoFall", "Take no fall damage.")));
      this.add(new Freecam(new ModuleData(ModuleData.Type.Visuals, "FreeCam", "Allows you to view around in noclip.")));
      this.add(new LongJump(new ModuleData(ModuleData.Type.Movement, "LongJump", "Jump, but longly.")));
      this.add(new AntiBot(new ModuleData(ModuleData.Type.Combat, "AntiBot", "Ignores/Removes bots.")));
      this.add(new DepthStrider(new ModuleData(ModuleData.Type.Movement, "DepthStrider", "Swim faster in water.")));
      this.add(new Jesus(new ModuleData(ModuleData.Type.Movement, "Jesus", "Walk on water.")));
      this.add(new FastUse(new ModuleData(ModuleData.Type.Player, "FastUse", "Consume items faster.")));
      this.add(new ChestStealer(new ModuleData(ModuleData.Type.Player, "ChestStealer", "Steal items from chests.")));
      this.add(new Fly(new ModuleData(ModuleData.Type.Movement, "Fly", "Become a bird.")));
      this.add(new AntiAim(new ModuleData(ModuleData.Type.MSGO, "AntiAim", "Derp, essentially.")));
      this.add(new AutoSoup(new ModuleData(ModuleData.Type.Combat, "AutoSoup", "Consumes soups to heal for you.")));
      this.add(new ChestESP(new ModuleData(ModuleData.Type.Visuals, "ChestESP", "Draws a box around chests.")));
      this.add(new Sprint(new ModuleData(ModuleData.Type.Movement, "Sprint", "Automatically sprints for you.")));
      this.add(new Xray(new ModuleData(ModuleData.Type.Visuals, "Xray", "Sends brain waves to blocks.", 45, KeyMask.None)));
      this.add(new AntiVelocity(new ModuleData(ModuleData.Type.Combat, "AntiVelocity", "Reduce/Remove velocity.")));
      this.add(new FastPlace(new ModuleData(ModuleData.Type.Player, "FastPlace", "Place blocks, but fast.")));
      this.add(new ChatCommands(new ModuleData(ModuleData.Type.Other, "Commands", "Commands, but for chat.")));
      this.add(new Enabled(new ModuleData(ModuleData.Type.Visuals, "HUD", "Watermark + Arraylist.")));
      this.add(new AutoEat(new ModuleData(ModuleData.Type.Player, "AutoEat", "Does /eat for you.")));
      this.add(new Nametags(new ModuleData(ModuleData.Type.Visuals, "2DTags", "Nametags that are rendered in the 2D view.")));
      this.add(new ArmorStatus(new ModuleData(ModuleData.Type.Visuals, "ArmorHUD", "Shows you your armor stats.")));
      this.add(new AutoArmor(new ModuleData(ModuleData.Type.Player, "AutoArmor", "Switches out current armor for best armor.")));
      this.add(new InventoryWalk(new ModuleData(ModuleData.Type.Player, "Inventory", "Walk in inventory + carry extra items.")));
      this.add(new Crosshair(new ModuleData(ModuleData.Type.Visuals, "Crosshair", "Draws a custom crosshair.")));
      this.add(new Killaura(new ModuleData(ModuleData.Type.Combat, "KillAura", "Attacks entities for you.")));
      this.add(new NoRotate(new ModuleData(ModuleData.Type.Player, "NoRotate", "Prevents the server from forcing head rotations.")));
      this.add(new NoSlowdown(new ModuleData(ModuleData.Type.Movement, "NoSlowdown", "Movement isn't reduced when using an item.")));
      this.add(new AutoPot(new ModuleData(ModuleData.Type.Combat, "AutoPot", "Throws potions to heal for you.")));
      this.add(new ESP2D(new ModuleData(ModuleData.Type.Visuals, "2DESP", "Outlined box ESP that is rendered in the 2D view.")));
      this.add(new Radar(new ModuleData(ModuleData.Type.Visuals, "Radar", "Shows you all the players around you.")));
      this.add(new AutoSay(new ModuleData(ModuleData.Type.Other, "AutoSay", "Says what ever you set the string to for you.")));
      this.add(new AutoRespawn(new ModuleData(ModuleData.Type.Player, "Respawn", "Respawns you after you've died.")));
      this.add(new KopyKatt(new ModuleData(ModuleData.Type.Other, "KopyKatt", "Copies what the selected target says. Use '.cc' to select the target.")));
      this.add(new Chams(new ModuleData(ModuleData.Type.Visuals, "Chams", "Doesn't work.")));
      this.add(new TabGUI(new ModuleData(ModuleData.Type.Visuals, "TabGUI", "TabGUI.")));
      this.add(new Phase(new ModuleData(ModuleData.Type.Movement, "Phase", "Clip through blocks.")));
      this.add(new BedFucker(new ModuleData(ModuleData.Type.Other, "BedFucker", "Breaks beds around you.")));
      this.add(new AutoTPA(new ModuleData(ModuleData.Type.Other, "AutoTPA", "Auto tp accepts for you.")));
      this.add(new Aimbot(new ModuleData(ModuleData.Type.MSGO, "Ragebot", "Minestrike/Cops & Crims ragebot.")));
      this.add(new AutoSword(new ModuleData(ModuleData.Type.Combat, "AutoSword", "Swords automatically woah how cool.")));
      this.add(new AntiFall(new ModuleData(ModuleData.Type.Movement, "AntiFall", "For bots who fall off maps 24/7.")));
      this.add(new Weather(new ModuleData(ModuleData.Type.Visuals, "Weather", "Sets the time for your fat ass.")));
      this.add(new InventoryCleaner(new ModuleData(ModuleData.Type.Player, "InventoryCleaner", "Cleans your inventory for your lazy ass.")));
      this.add(new TargetHUD(new ModuleData(ModuleData.Type.Visuals, "TargetHUD", "Shows information about the target on the HUD.")));
      this.add(new Box(new ModuleData(ModuleData.Type.Visuals, "BoxESP", "A 3D ESP that's so simple it shouldn't even exist smh.")));
      this.add(new AutoBandage(new ModuleData(ModuleData.Type.MSGO, "AutoBandage", "OK.")));
      this.add(new AutoIon(new ModuleData(ModuleData.Type.MSGO, "AutoIon", "OK.")));
      this.add(new SpookySkeltal(new ModuleData(ModuleData.Type.Visuals, "Skeletal", "OMG BRO NO WAY")));
      this.add(new Debug(new ModuleData(ModuleData.Type.Other, "Debug", "Enables debugging features (Console & Dev Notifs).")));
      this.add(new MCF(new ModuleData(ModuleData.Type.Other, "MCF", "For those who are lazy.")));
      this.add(new ViewClip(new ModuleData(ModuleData.Type.Visuals, "ViewClip", "Removes camera view collision.")));
      this.add(new AntiDesync(new ModuleData(ModuleData.Type.Other, "AntiDesync", "Prevents block desynchronization when breaking/placing.")));
      this.add(new BorderHop(new ModuleData(ModuleData.Type.Movement, "BorderHop", "Hop the border like a snow mexican.")));
      this.add(new StreamerMode(new ModuleData(ModuleData.Type.Other, "Streamer", "Fuck you hypixel niggers.")));
      this.add(new PotionSaver(new ModuleData(ModuleData.Type.Player, "PotionSaver", "Saves effects when standing.")));
      this.add(new TargetESP(new ModuleData(ModuleData.Type.Visuals, "TargetESP", "Highlights important/priority targets.")));
      this.setup = true;
      loadStatus();
      if (!((Module)this.get(ChatCommands.class)).isEnabled()) {
         ((Module)this.get(ChatCommands.class)).toggle();
      }

   }

   public static void saveStatus() {
      if (Client.getModuleManager().isSetup()) {
         List fileContent = new ArrayList();
         Module[] var1 = (Module[])Client.getModuleManager().getArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Module module = var1[var3];
            String displayName = module.getName();
            String enabled = Boolean.toString(module.isEnabled());
            int bind = module.data.getKey();
            String hidden = Boolean.toString(module.isHidden());
            fileContent.add(String.format("%s:%s:%s:%s", displayName, enabled, bind, hidden));
         }

         FileUtils.write(MODULE_DIR, fileContent, true);
      }
   }

   public static void saveSettings() {
      List fileContent = new ArrayList();
      Module[] var1 = (Module[])Client.getModuleManager().getArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Module module = var1[var3];
         Iterator var5 = module.getSettings().values().iterator();

         while(var5.hasNext()) {
            Setting setting = (Setting)var5.next();
            String displayName;
            String settingName;
            String settingValue;
            if (setting.getValue() instanceof Options) {
               displayName = module.getName();
               settingName = setting.getName();
               settingValue = ((Options)setting.getValue()).getSelected();
               fileContent.add(String.format("%s:%s:%s", displayName, settingName, settingValue));
            } else if (setting.getValue() instanceof MultiBool) {
               displayName = module.getName();
               settingName = setting.getName();
               List enabled = new ArrayList();
               ((MultiBool)setting.getValue()).getBooleans().forEach((set) -> {
                  enabled.add(((Setting) set).getName() + "=" + ((Setting) set).getValue());
               });
               settingValue = enabled.toString();
               fileContent.add(String.format("%s:%s:%s", displayName, settingName, settingValue));
            } else {
               displayName = module.getName();
               settingName = setting.getName();
               settingValue = setting.getValue().toString();
               fileContent.add(String.format("%s:%s:%s", displayName, settingName, settingValue));
            }
         }
      }

      FileUtils.write(SETTINGS_DIR, fileContent, true);
   }

   public static void loadStatus() {
      try {
         List fileContent = FileUtils.read(MODULE_DIR);
         Iterator var1 = fileContent.iterator();

         while(var1.hasNext()) {
            String line = (String)var1.next();
            String[] split = line.split(":");
            String displayName = split[0];
            Module[] var5 = (Module[])Client.getModuleManager().getArray();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Module module = var5[var7];
               if (module.getName().equalsIgnoreCase(displayName)) {
                  String strEnabled = split[1];
                  boolean enabled = Boolean.parseBoolean(strEnabled);
                  String key = split[2];
                  module.setKeybind(new Keybind(module, Integer.parseInt(key)));
                  if (enabled && !module.isEnabled()) {
                     module.setEnabled(true);
                     EventSystem.register(module);
                     module.onEnable();
                     if (split.length > 3) {
                        module.setHidden(Boolean.parseBoolean(split[3]));
                     }
                  }
               }
            }
         }
      } catch (Exception var12) {
         var12.printStackTrace();
      }

   }

   public static void loadSettings() {
      try {
         List fileContent = FileUtils.read(SETTINGS_DIR);
         Iterator var1 = fileContent.iterator();

         while(var1.hasNext()) {
            String line = (String)var1.next();
            String[] split = line.split(":");
            Module[] var4 = (Module[])Client.getModuleManager().getArray();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Module module = var4[var6];
               if (module.getName().equalsIgnoreCase(split[0])) {
                  Setting setting = Module.getSetting(module.getSettings(), split[1]);
                  String settingValue = split[2];
                  if (setting != null) {
                     if (setting.getValue() instanceof Number) {
                        if (!settingValue.equalsIgnoreCase("true") && !settingValue.equalsIgnoreCase("false")) {
                           Object newValue = StringConversions.castNumber(settingValue, setting.getValue());
                           if (newValue != null) {
                              setting.setValue(newValue);
                           }
                        }
                     } else if (setting.getValue().getClass().equals(String.class)) {
                        String parsed = settingValue.toString().replaceAll("_", " ");
                        setting.setValue(parsed);
                     } else if (setting.getValue().getClass().equals(Boolean.class)) {
                        setting.setValue(Boolean.parseBoolean(settingValue));
                     } else if (setting.getValue().getClass().equals(Options.class)) {
                        ((Options)setting.getValue()).setSelected(settingValue);
                     } else if (setting.getValue().getClass().equals(MultiBool.class)) {
                        MultiBool multiBool = (MultiBool)setting.getValue();
                        List items = Arrays.asList(settingValue.replace("[", "").replace("]", "").split("\\s*,\\s*"));
                        items.forEach((o) -> {
                           multiBool.getBooleans().forEach((bool) -> {
                              if (((String) o).contains(((Setting) bool).getName())) {
                                 ((Setting) bool).setValue(((String) o).split("=")[1].equalsIgnoreCase("true"));
                              }

                           });
                        });
                     }
                  }
               }
            }
         }
      } catch (Exception var12) {
         var12.printStackTrace();
      }

   }

   private void loadLocalPlugins() {
      String basePath = Client.getDataDir().getAbsolutePath();
      String newPath = basePath + (basePath.endsWith(File.separator) ? SubFolder.ModuleJars.getFolderName() : File.separator + SubFolder.ModuleJars.getFolderName());
      File test = new File(newPath);
      if (!test.exists()) {
         test.mkdirs();
      }

      File[] var4 = test.listFiles();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File file = var4[var6];
         if (file.getAbsolutePath().endsWith(".jar")) {
            try {
               this.loadJar(file);
               System.out.println(file.getAbsoluteFile().getName() + " has been successfully loaded!");
            } catch (IOException var9) {
               System.out.println("IOException thrown! -- Error loading Plugin.");
               var9.printStackTrace();
            }
         }
      }

   }

   private void loadJar(File file) throws IOException {
      JarInputStream jis = new JarInputStream(new FileInputStream(file));
      URLClassLoader urlLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});

      for(JarEntry jarEntry = jis.getNextJarEntry(); jarEntry != null; jarEntry = jis.getNextJarEntry()) {
         if (!jarEntry.isDirectory() && jarEntry.getName().endsWith(".class")) {
            String className = jarEntry.getName().replace('/', '.').substring(0, jarEntry.getName().length() - ".class".length());
            if (!className.contains("$")) {
               try {
                  Class classs = urlLoader.loadClass(className);
                  if (Module.class.isAssignableFrom(classs)) {
                     this.add((Module)classs.newInstance());
                  }
               } catch (ReflectiveOperationException var7) {
                  var7.printStackTrace();
               }
            }
         }
      }

      jis.close();
      urlLoader.close();
   }

   public boolean isSetup() {
      return this.setup;
   }

   public boolean isEnabled(Class clazz) {
      Module module = (Module)this.get(clazz);
      return module != null && module.isEnabled();
   }

   public Module get(String name) {
      Module[] var2 = (Module[])this.getArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         if (module.getName().toLowerCase().equals(name.toLowerCase())) {
            return module;
         }
      }

      return null;
   }
}
