package Focus.Beta.IMPL.managers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import Focus.Beta.IMPL.Module.impl.combat.*;
import Focus.Beta.IMPL.Module.impl.exploit.*;
import Focus.Beta.IMPL.Module.impl.focus.Cape;
import Focus.Beta.IMPL.Module.impl.focus.DiscordRP;
import Focus.Beta.IMPL.Module.impl.focus.FocusBot;
import Focus.Beta.IMPL.Module.impl.misc.*;
import Focus.Beta.IMPL.Module.impl.move.*;
import Focus.Beta.IMPL.Module.impl.render.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import Focus.Beta.API.EventBus;
import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.misc.EventKey;
import Focus.Beta.API.events.render.EventRender2D;
import Focus.Beta.API.events.render.EventRender3D;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.IMPL.set.Value;
import Focus.Beta.UTILS.render.GLUtils;
import net.minecraft.client.renderer.GlStateManager;

public class ModuleManager implements Manager {
    public static List<Module> modules = new ArrayList<Module>();
    

    private boolean enabledNeededMod = true;
    @Override
    public void init() {
    	modules.add(new HUD());
        modules.add(new ChinaHat());
        modules.add(new NoFall());
        modules.add(new AutoRegister());
        modules.add(new FastEat());
    	modules.add(new FocusBot());
        modules.add(new Phase());
        modules.add(new Cape());
        modules.add(new AutoHypixel());
        modules.add(new HackerDetector());
    	modules.add(new PingSpoof());
        modules.add(new LightningTracker());
    	modules.add(new ClickGui());
    	modules.add(new FastPlace());
    	modules.add(new KillSluts());
    	modules.add(new NoSlow());
    	modules.add(new FullBright());
    	modules.add(new TeleportAura());
        modules.add(new ChestESP());
    	modules.add(new TargetHUD());
    	modules.add(new Disabler());
    	modules.add(new InvMove());
    	modules.add(new Scaffold69());
    	modules.add(new NameTags());
        modules.add(new DiscordRP());
    	modules.add(new AutoArmor());
    	modules.add(new Cheststealer());
    	modules.add(new Fly());
    	modules.add(new InvManager());
    	modules.add(new Speed());
        modules.add(new HighJump());
    	modules.add(new Animation());
    	modules.add(new Autotool());
    	modules.add(new Velocity());
    	modules.add(new Killaura());
    	modules.add(new MCF());
        modules.add(new LongJump());
    	modules.add(new Criticals());
    	modules.add(new AntiBot());
    	this.readSettings();
    	for (Module m : modules) {
    		m.makeCommand();
    	}
    	EventBus.getInstance().register(this);
    }

    public List<Module> getModules() {
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

    public static List<Module> getModulesInType(Type t) {
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
        GL11.glGetInteger(2978, (IntBuffer) GLUtils.VIEWPORT.clear());
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