package Ascii4UwUWareClient.Manager;

import Ascii4UwUWareClient.API.EventBus;
import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.Misc.EventKey;
import Ascii4UwUWareClient.API.Events.Render.EventRender2D;
import Ascii4UwUWareClient.API.Events.Render.EventRender3D;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.API.Value.Option;
import Ascii4UwUWareClient.API.Value.Value;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.*;
import Ascii4UwUWareClient.Module.Modules.Exploit.Disabler;
import Ascii4UwUWareClient.Module.Modules.Exploit.Pingspoof;
import Ascii4UwUWareClient.Module.Modules.Exploit.Teleport;
import Ascii4UwUWareClient.Module.Modules.Misc.*;
import Ascii4UwUWareClient.Module.Modules.Move.*;
import Ascii4UwUWareClient.Module.Modules.Render.*;
import Ascii4UwUWareClient.Util.Render.gl.GLUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager implements Manager {
    public static List<Module> modules = new ArrayList<Module>();
    private boolean enabledNeededMod = true;
    public boolean nicetry = true;

    @Override
    public void init() {

        /**
         * Registered Combat Modules
         */
        modules.add(new Velocity());
        modules.add(new AntiBot());
        modules.add(new Killaura());
        modules.add(new Criticals () );
        modules.add ( new TPaura () );



        //Exploit Modules
        modules.add (new Disabler());
        modules.add(new Teleport());
        modules.add ( new Pingspoof () );

        /**
         * Registered Visuals
         */
        modules.add(new HUD());
        modules.add(new NameTags());
        modules.add(new ClickGui());
        modules.add(new TargetHUD());
        modules.add(new ESP());
        modules.add(new FullBright());
        modules.add(new Chams());
        modules.add(new Xray());
        modules.add(new ChestESP());
        modules.add(new Animation());
        modules.add(new Radar());
        modules.add(new ESP2D());
        modules.add(new Crosshair());

        /**
         * Registered Movement Modules
         */
        modules.add(new NoSlow());
        modules.add(new Nofall ());
        modules.add(new InvMove());
        modules.add(new AntiFall());
        modules.add(new Speed());
        modules.add (new Scaffold());
        modules.add(new TargetStrafe());
        modules.add(new Sprint ());
        modules.add ( new Fly () );
        modules.add ( new Longjump () );
        modules.add(new FastBridge());
        modules.add ( new Phase () );

        /**
         * Registered Misc Modules
         */
        modules.add(new MCF());
        modules.add(new Fucker());
        modules.add(new Teams());
        modules.add ( new Cheststealer () );
        modules.add(new AutoPotion () );
        modules.add(new InvManager());
        modules.add ( new Fasteat () );
        modules.add ( new Autotool () );
        modules.add ( new PlayerList () );
        modules.add(new KillSults());

        /**
         * Registered Cubecraft Module
         */
        this.readSettings();
        for (Module m : modules) {
            m.makeCommand();
        }
        EventBus.getInstance().register(this);
    }

    public static List<Module> getModules() {
        return modules;
    }

    public Module getModuleByClass(Class<? extends Module> cls) {
        for (Module m : modules) {
            if (m.getClass() != cls)
                continue;
            return m;
        }
        return null;
    }

    public static Module getModuleByName(String name) {
        for (Module m : modules) {
            if (!m.getName().equalsIgnoreCase(name))
                continue;
            return m;
        }
        return null;
    }

    public Module getAlias(String name) {
        for (Module f : modules) {
            if (f.getName().equalsIgnoreCase(name)) {
                return f;
            }
            String[] alias = f.getAlias();
            int length = alias.length;
            int i = 0;
            while (i < length) {
                String s = alias[i];
                if (s.equalsIgnoreCase(name)) {
                    return f;
                }
                ++i;
            }
        }
        return null;
    }

    public static List<Module> getModulesInType(ModuleType t) {
        ArrayList<Module> output = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.getType() != t)
                continue;
            output.add(m);
        }
        return output;
    }

    @EventHandler
    private void onKeyPress(EventKey e) {
        for (Module m : modules) {
            if (m.getKey() != e.getKey())
                continue;
            m.setEnabled(!m.isEnabled());
        }
    }

    @EventHandler
    private void onGLHack(EventRender3D e) {
        GlStateManager.getFloat(2982, (FloatBuffer) GLUtils.MODELVIEW.clear());
        GlStateManager.getFloat(2983, (FloatBuffer) GLUtils.PROJECTION.clear());
        GlStateManager.glGetInteger(2978, (IntBuffer) GLUtils.VIEWPORT.clear());
    }

    @EventHandler
    private void on2DRender(EventRender2D e) {
        if (this.enabledNeededMod) {
            this.enabledNeededMod = false;
            for (Module m : modules) {
                if (!m.enabledOnStartup)
                    continue;
                m.setEnabled(true);
            }
        }
    }

    private void readSettings() {
        List<String> binds = FileManager.read("Binds.txt");
        for (String v : binds) {
            String name = v.split(":")[0];
            String bind = v.split(":")[1];
            Module m = ModuleManager.getModuleByName(name);
            if (m == null)
                continue;
            m.setKey(Keyboard.getKeyIndex((String) bind.toUpperCase()));
        }
        List<String> enabled = FileManager.read("Enabled.txt");
        for (String v : enabled) {
            Module m = ModuleManager.getModuleByName(v);
            if (m == null)
                continue;
            m.enabledOnStartup = true;
        }
        List<String> vals = FileManager.read("Values.txt");
        for (String v : vals) {
            String name = v.split(":")[0];
            String values = v.split(":")[1];
            Module m = ModuleManager.getModuleByName(name);
            if (m == null)
                continue;
            for (Value value : m.getValues()) {
                if (!value.getName().equalsIgnoreCase(values))
                    continue;
                if (value instanceof Option) {
                    value.setValue(Boolean.parseBoolean(v.split(":")[2]));
                    continue;
                }
                if (value instanceof Numbers) {
                    value.setValue(Double.parseDouble(v.split(":")[2]));
                    continue;
                }
                ((Mode) value).setMode(v.split(":")[2]);
            }
        }
    }
}
