package HORIZON-6-0-SKIDPROTECTION;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;

public class ModuleManager
{
    public static ArrayList<Mod> HorizonCode_Horizon_È;
    public static Map<String, Mod> Â;
    
    static {
        ModuleManager.HorizonCode_Horizon_È = new ArrayList<Mod>();
        ModuleManager.Â = new LinkedHashMap<String, Mod>();
    }
    
    public ModuleManager() {
        ModuleManager.HorizonCode_Horizon_È.add(new Gui());
        ModuleManager.HorizonCode_Horizon_È.add(new Sprint());
        ModuleManager.HorizonCode_Horizon_È.add(new Sneak());
        ModuleManager.HorizonCode_Horizon_È.add(new SafeWalk());
        ModuleManager.HorizonCode_Horizon_È.add(new ESP());
        ModuleManager.HorizonCode_Horizon_È.add(new FullBright());
        ModuleManager.HorizonCode_Horizon_È.add(new Stealer());
        ModuleManager.HorizonCode_Horizon_È.add(new AutoWalk());
        ModuleManager.HorizonCode_Horizon_È.add(new AutoArmor());
        ModuleManager.HorizonCode_Horizon_È.add(new Step());
        ModuleManager.HorizonCode_Horizon_È.add(new Speed());
        ModuleManager.HorizonCode_Horizon_È.add(new Inventory());
        ModuleManager.HorizonCode_Horizon_È.add(new BedFucker());
        ModuleManager.HorizonCode_Horizon_È.add(new NoSlow());
        ModuleManager.HorizonCode_Horizon_È.add(new NoVelocity());
        ModuleManager.HorizonCode_Horizon_È.add(new Glide());
        ModuleManager.HorizonCode_Horizon_È.add(new Jesus());
        ModuleManager.HorizonCode_Horizon_È.add(new InstantUse());
        ModuleManager.HorizonCode_Horizon_È.add(new RageAura());
        ModuleManager.HorizonCode_Horizon_È.add(new AutoSoup());
        ModuleManager.HorizonCode_Horizon_È.add(new PlayerESP());
        ModuleManager.HorizonCode_Horizon_È.add(new NameTags());
        ModuleManager.HorizonCode_Horizon_È.add(new Blink());
        ModuleManager.HorizonCode_Horizon_È.add(new Phase());
        ModuleManager.HorizonCode_Horizon_È.add(new Kektus());
        ModuleManager.HorizonCode_Horizon_È.add(new ScaffoldWalk());
        ModuleManager.HorizonCode_Horizon_È.add(new Criticals());
        ModuleManager.HorizonCode_Horizon_È.add(new NoFall());
        ModuleManager.HorizonCode_Horizon_È.add(new Fly());
        ModuleManager.HorizonCode_Horizon_È.add(new MultiJump());
        ModuleManager.HorizonCode_Horizon_È.add(new Freecam());
        ModuleManager.HorizonCode_Horizon_È.add(new AutoPot());
        ModuleManager.HorizonCode_Horizon_È.add(new Zoot());
        ModuleManager.HorizonCode_Horizon_È.add(new Tower());
        ModuleManager.HorizonCode_Horizon_È.add(new KeepSprint());
        ModuleManager.HorizonCode_Horizon_È.add(new Trajectories());
        ModuleManager.HorizonCode_Horizon_È.add(new Ladders());
        ModuleManager.HorizonCode_Horizon_È.add(new NoPitchLimit());
        ModuleManager.HorizonCode_Horizon_È.add(new Paralyze());
        ModuleManager.HorizonCode_Horizon_È.add(new Tracers());
        ModuleManager.HorizonCode_Horizon_È.add(new SpookySkin());
        ModuleManager.HorizonCode_Horizon_È.add(new RcPigRacing());
        ModuleManager.HorizonCode_Horizon_È.add(new ChestAura());
        ModuleManager.HorizonCode_Horizon_È.add(new IDNuker());
        ModuleManager.HorizonCode_Horizon_È.add(new Derp());
        ModuleManager.HorizonCode_Horizon_È.add(new Notifier());
        ModuleManager.HorizonCode_Horizon_È.add(new SCHLITZ());
        ModuleManager.HorizonCode_Horizon_È.add(new TriggerBot());
        ModuleManager.HorizonCode_Horizon_È.add(new BreakingBlocks());
        ModuleManager.HorizonCode_Horizon_È.add(new TnTBlocker());
        ModuleManager.HorizonCode_Horizon_È.add(new MLG());
        ModuleManager.HorizonCode_Horizon_È.add(new BowAimbot());
        ModuleManager.HorizonCode_Horizon_È.add(new Regen());
        ModuleManager.HorizonCode_Horizon_È.add(new NoBob());
        ModuleManager.HorizonCode_Horizon_È.add(new FileSpammer());
        ModuleManager.HorizonCode_Horizon_È.add(new AntiInvisible());
        ModuleManager.HorizonCode_Horizon_È.add(new Teleport());
        ModuleManager.HorizonCode_Horizon_È.add(new NoWeb());
        ModuleManager.HorizonCode_Horizon_È.add(new HitboxExtend());
        ModuleManager.HorizonCode_Horizon_È.add(new SlowMotion());
        ModuleManager.HorizonCode_Horizon_È.add(new ReverseSpeak());
        ModuleManager.HorizonCode_Horizon_È.add(new MiddleClickFriends());
        ModuleManager.HorizonCode_Horizon_È.add(new Aids());
        ModuleManager.HorizonCode_Horizon_È.add(new NoDownGlide());
        ModuleManager.HorizonCode_Horizon_È.add(new KillAura());
    }
    
    public static Mod HorizonCode_Horizon_È(final Class<? extends Mod> clazz) {
        for (final Mod mod : ModuleManager.HorizonCode_Horizon_È) {
            if (mod.getClass() == clazz) {
                return mod;
            }
        }
        return null;
    }
    
    public ArrayList<Mod> HorizonCode_Horizon_È() {
        return ModuleManager.HorizonCode_Horizon_È;
    }
    
    public void Â() {
        new GuiArrayList().HorizonCode_Horizon_È();
    }
    
    public static Mod HorizonCode_Horizon_È(final String s) {
        for (final Mod mod : ModuleManager.HorizonCode_Horizon_È) {
            if (mod.ÂµÈ().HorizonCode_Horizon_È().trim().equalsIgnoreCase(s.trim()) || mod.toString().trim().equalsIgnoreCase(s.trim())) {
                return mod;
            }
        }
        return null;
    }
    
    public Mod Â(final String s) {
        for (final Mod mod : ModuleManager.HorizonCode_Horizon_È) {
            if (mod.Ý().equalsIgnoreCase(s)) {
                return mod;
            }
        }
        return null;
    }
    
    public void Ý() {
        try {
            final File file = new File(Horizon.à¢.áŒŠ, "modules.ini");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            final PrintWriter printWriter = new PrintWriter(new FileWriter(file, true));
            final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
            for (final Mod mod : ModuleManager.HorizonCode_Horizon_È) {
                if (mod.áˆºÑ¢Õ()) {
                    printWriter.println(mod.ÂµÈ().HorizonCode_Horizon_È());
                }
            }
            printWriter.close();
        }
        catch (Exception ex) {}
    }
    
    public void Ø­áŒŠá() {
        try {
            new Thread("Module Toggle Thread (StartUp)") {
                @Override
                public void run() {
                    final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
                    for (final Mod m : ModuleManager.HorizonCode_Horizon_È) {
                        String[] âµá€;
                        for (int length = (âµá€ = ModuleManager.this.Âµá€()).length, i = 0; i < length; ++i) {
                            final String module = âµá€[i];
                            if (module.equalsIgnoreCase(m.ÂµÈ().HorizonCode_Horizon_È())) {
                                try {
                                    Thread.sleep(200L);
                                }
                                catch (InterruptedException ex) {}
                                m.ˆÏ­();
                            }
                        }
                    }
                }
            }.start();
        }
        catch (Exception ex) {}
    }
    
    public String[] Âµá€() {
        try {
            final File file = new File(Horizon.à¢.áŒŠ, "modules.ini");
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            final ArrayList<String> list = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
            bufferedReader.close();
            return list.toArray(new String[list.size()]);
        }
        catch (Exception ex) {
            return new String[] { "kevin" };
        }
    }
}
