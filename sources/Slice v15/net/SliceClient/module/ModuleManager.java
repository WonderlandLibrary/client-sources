package net.SliceClient.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import net.SliceClient.Slice;
import net.SliceClient.modules.AACNoFall;
import net.SliceClient.modules.AirWalk;
import net.SliceClient.modules.AntiFire;
import net.SliceClient.modules.AutoArmor;
import net.SliceClient.modules.AutoJump;
import net.SliceClient.modules.AutoPotion;
import net.SliceClient.modules.AutoRespawn;
import net.SliceClient.modules.AutoWalk;
import net.SliceClient.modules.BedFucker;
import net.SliceClient.modules.BetterBobbing;
import net.SliceClient.modules.BetterBow;
import net.SliceClient.modules.Bhop;
import net.SliceClient.modules.BlockJump;
import net.SliceClient.modules.BoatFly;
import net.SliceClient.modules.CCSpeed;
import net.SliceClient.modules.Casper;
import net.SliceClient.modules.ChestESP;
import net.SliceClient.modules.ChestSteal;
import net.SliceClient.modules.ClickTeleport;
import net.SliceClient.modules.Criticals;
import net.SliceClient.modules.Damage;
import net.SliceClient.modules.Drugs;
import net.SliceClient.modules.ESP;
import net.SliceClient.modules.Eagle;
import net.SliceClient.modules.FastFall;
import net.SliceClient.modules.FastLadders;
import net.SliceClient.modules.FastPlace;
import net.SliceClient.modules.FastUse;
import net.SliceClient.modules.Fly;
import net.SliceClient.modules.Frames;
import net.SliceClient.modules.Freecam;
import net.SliceClient.modules.FullBright;
import net.SliceClient.modules.Glide;
import net.SliceClient.modules.Gui;
import net.SliceClient.modules.IceSpeed;
import net.SliceClient.modules.InvMove;
import net.SliceClient.modules.ItemESP;
import net.SliceClient.modules.Jesus;
import net.SliceClient.modules.Jetpack;
import net.SliceClient.modules.Jitter;
import net.SliceClient.modules.KillAura;
import net.SliceClient.modules.MaxRotations;
import net.SliceClient.modules.Motion;
import net.SliceClient.modules.MultiJump;
import net.SliceClient.modules.NameProtect;
import net.SliceClient.modules.Nametags;
import net.SliceClient.modules.New;
import net.SliceClient.modules.NoBob;
import net.SliceClient.modules.NoFall;
import net.SliceClient.modules.NoHurtcam;
import net.SliceClient.modules.NoSlowDown;
import net.SliceClient.modules.NoSwing;
import net.SliceClient.modules.NoVelocity;
import net.SliceClient.modules.NoWeb;
import net.SliceClient.modules.Old;
import net.SliceClient.modules.OnGround;
import net.SliceClient.modules.OtherTower;
import net.SliceClient.modules.Phase;
import net.SliceClient.modules.RewiVelocity;
import net.SliceClient.modules.SafeWalk;
import net.SliceClient.modules.ScaffoldWalk;
import net.SliceClient.modules.SlimeFly;
import net.SliceClient.modules.SlimeSpeed;
import net.SliceClient.modules.Spammer;
import net.SliceClient.modules.Spider;
import net.SliceClient.modules.Sprint;
import net.SliceClient.modules.Step;
import net.SliceClient.modules.Strafe;
import net.SliceClient.modules.Tower;
import net.SliceClient.modules.Tracers;
import net.SliceClient.modules.yee;


public class ModuleManager
{
  public static ArrayList<Module> activeModules = new ArrayList();
  
  public ModuleManager() {
    activeModules.add(new Gui());
    activeModules.add(new NoVelocity());
    activeModules.add(new Sprint());
    activeModules.add(new SafeWalk());
    activeModules.add(new ChestSteal());
    activeModules.add(new AutoArmor());
    activeModules.add(new AutoJump());
    activeModules.add(new FastPlace());
    activeModules.add(new FullBright());
    activeModules.add(new AutoWalk());
    activeModules.add(new Spider());
    activeModules.add(new Fly());
    activeModules.add(new NoSlowDown());
    activeModules.add(new Damage());
    activeModules.add(new NoFall());
    activeModules.add(new BoatFly());
    activeModules.add(new AutoRespawn());
    activeModules.add(new Eagle());
    activeModules.add(new FastLadders());
    activeModules.add(new InvMove());
    activeModules.add(new NoWeb());
    activeModules.add(new BlockJump());
    activeModules.add(new NoHurtcam());
    activeModules.add(new NoBob());
    activeModules.add(new BedFucker());
    activeModules.add(new Tracers());
    activeModules.add(new Nametags());
    activeModules.add(new MultiJump());
    activeModules.add(new Jetpack());
    activeModules.add(new IceSpeed());
    activeModules.add(new Step());
    activeModules.add(new ScaffoldWalk());
    activeModules.add(new NameProtect());
    activeModules.add(new Strafe());
    activeModules.add(new Bhop());
    activeModules.add(new Casper());
    activeModules.add(new Frames());
    activeModules.add(new Jitter());
    activeModules.add(new Motion());
    activeModules.add(new Drugs());
    activeModules.add(new AntiFire());
    activeModules.add(new AutoPotion());
    activeModules.add(new KillAura());
    activeModules.add(new Glide());
    activeModules.add(new New());
    activeModules.add(new ItemESP());
    activeModules.add(new AACNoFall());
    activeModules.add(new CCSpeed());
    activeModules.add(new Criticals());
    activeModules.add(new Tower());
    activeModules.add(new Freecam());
    activeModules.add(new MaxRotations());
    activeModules.add(new yee());
    activeModules.add(new NoSwing());
    activeModules.add(new ESP());
    activeModules.add(new FastUse());
    activeModules.add(new ChestESP());
    activeModules.add(new AirWalk());
    activeModules.add(new RewiVelocity());
    activeModules.add(new OtherTower());
    activeModules.add(new SlimeSpeed());
    activeModules.add(new SlimeFly());
    activeModules.add(new Old());
    activeModules.add(new ClickTeleport());
    activeModules.add(new BetterBobbing());
    activeModules.add(new BetterBow());
    activeModules.add(new Jesus());
    activeModules.add(new FastFall());
    activeModules.add(new Spammer());
    activeModules.add(new Phase());
    activeModules.add(new OnGround());
  }
  
  public static ArrayList<Module> getModules() {
    return activeModules;
  }
  
  public static Module getModuleByName(String name) {
    for (Module modul : ) {
      if (modul.getName().equalsIgnoreCase(name)) {
        return modul;
      }
    }
    return null;
  }
  
  public static Module getModule(Class<? extends Module> clazz) {
    for (Module mod : ) {
      if (mod.getClass() == clazz) {
        return mod;
      }
    }
    return null;
  }
  















  public void setModules()
  {
    try
    {
      new Thread("Module Toggle Thread (StartUp)")
      {
        public void run()
        {
          int j;
          int i;
          for (Iterator localIterator = ModuleManager.activeModules.iterator(); localIterator.hasNext(); 
              i < j)
          {
            Module m = (Module)localIterator.next();
            String[] arrayOfString; j = (arrayOfString = readModules()).length;i = 0; continue;String module = arrayOfString[i];
            if (module.equalsIgnoreCase(m.getName())) {
              try {
                sleep(200L);
              }
              catch (InterruptedException localInterruptedException) {}
              m.toggleModule();
            }
            i++;
          }
        }
      }.start();
    }
    catch (Exception localException) {}
  }
  








  public String[] readModules()
  {
    try
    {
      File f = new File(Slice.directory, "modules.cfg");
      if (!f.exists()) {
        f.createNewFile();
      }
      FileReader fileReader = new FileReader(f);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      ArrayList<String> lines = new ArrayList();
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        lines.add(line);
      }
      bufferedReader.close();
      return (String[])lines.toArray(new String[lines.size()]);
    }
    catch (Exception localException) {}
    return tmp103_100;
  }
  

  public static Module getModByName(String string)
  {
    return null;
  }
}
