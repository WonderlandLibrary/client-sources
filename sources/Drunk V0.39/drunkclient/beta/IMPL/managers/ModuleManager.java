/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.IMPL.managers;

import drunkclient.beta.API.EventBus;
import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.misc.EventKey;
import drunkclient.beta.API.events.render.EventRender2D;
import drunkclient.beta.API.events.render.EventRender3D;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.Module.impl.combat.AntiBot;
import drunkclient.beta.IMPL.Module.impl.combat.Criticals;
import drunkclient.beta.IMPL.Module.impl.combat.Killaura;
import drunkclient.beta.IMPL.Module.impl.combat.TeleportAura;
import drunkclient.beta.IMPL.Module.impl.combat.Velocity;
import drunkclient.beta.IMPL.Module.impl.drunkclient.Cape;
import drunkclient.beta.IMPL.Module.impl.drunkclient.DiscordRP;
import drunkclient.beta.IMPL.Module.impl.exploit.AutoHypixel;
import drunkclient.beta.IMPL.Module.impl.exploit.Disabler;
import drunkclient.beta.IMPL.Module.impl.exploit.FastEat;
import drunkclient.beta.IMPL.Module.impl.exploit.PingSpoof;
import drunkclient.beta.IMPL.Module.impl.misc.AutoArmor;
import drunkclient.beta.IMPL.Module.impl.misc.AutoRegister;
import drunkclient.beta.IMPL.Module.impl.misc.Autotool;
import drunkclient.beta.IMPL.Module.impl.misc.Breaker;
import drunkclient.beta.IMPL.Module.impl.misc.Cheststealer;
import drunkclient.beta.IMPL.Module.impl.misc.FastPlace;
import drunkclient.beta.IMPL.Module.impl.misc.InvManager;
import drunkclient.beta.IMPL.Module.impl.misc.KillSluts;
import drunkclient.beta.IMPL.Module.impl.misc.LightningTracker;
import drunkclient.beta.IMPL.Module.impl.misc.MCF;
import drunkclient.beta.IMPL.Module.impl.misc.NoSlow;
import drunkclient.beta.IMPL.Module.impl.misc.Scaffold69;
import drunkclient.beta.IMPL.Module.impl.misc.Teams;
import drunkclient.beta.IMPL.Module.impl.move.Fly;
import drunkclient.beta.IMPL.Module.impl.move.HighJump;
import drunkclient.beta.IMPL.Module.impl.move.InvMove;
import drunkclient.beta.IMPL.Module.impl.move.LongJump;
import drunkclient.beta.IMPL.Module.impl.move.NoFall;
import drunkclient.beta.IMPL.Module.impl.move.Phase;
import drunkclient.beta.IMPL.Module.impl.move.Speed;
import drunkclient.beta.IMPL.Module.impl.move.Sprint;
import drunkclient.beta.IMPL.Module.impl.render.Animation;
import drunkclient.beta.IMPL.Module.impl.render.Chams;
import drunkclient.beta.IMPL.Module.impl.render.ChestESP;
import drunkclient.beta.IMPL.Module.impl.render.ChinaHat;
import drunkclient.beta.IMPL.Module.impl.render.ClickGui;
import drunkclient.beta.IMPL.Module.impl.render.FullBright;
import drunkclient.beta.IMPL.Module.impl.render.HUD;
import drunkclient.beta.IMPL.Module.impl.render.NameTags;
import drunkclient.beta.IMPL.Module.impl.render.TargetHUD;
import drunkclient.beta.IMPL.Module.impl.render.Xray;
import drunkclient.beta.IMPL.managers.FileManager;
import drunkclient.beta.IMPL.managers.Manager;
import drunkclient.beta.IMPL.set.Mode;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.render.GLUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ModuleManager
implements Manager {
    public static List<Module> modules = new ArrayList<Module>();
    private boolean enabledNeededMod = true;

    @Override
    public void init() {
        modules.add(new HUD());
        modules.add(new ChinaHat());
        modules.add(new NoFall());
        modules.add(new AutoRegister());
        modules.add(new FastEat());
        modules.add(new Phase());
        modules.add(new Cape());
        modules.add(new AutoHypixel());
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
        modules.add(new Breaker());
        modules.add(new Chams());
        modules.add(new Sprint());
        modules.add(new DiscordRP());
        modules.add(new Xray());
        modules.add(new Teams());
        this.readSettings();
        Iterator<Module> iterator = modules.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                EventBus.getInstance().register(this);
                return;
            }
            Module m = iterator.next();
            m.makeCommand();
        }
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModuleByClass(Class<? extends Module> cls) {
        Iterator<Module> iterator = modules.iterator();
        while (iterator.hasNext()) {
            Module m = iterator.next();
            if (m.getClass() == cls) return m;
        }
        return null;
    }

    public static Module getModuleByName(String name) {
        Iterator<Module> iterator = modules.iterator();
        while (iterator.hasNext()) {
            Module m = iterator.next();
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public Module getAlias(String name) {
        Iterator<Module> iterator = modules.iterator();
        block0: while (iterator.hasNext()) {
            Module f = iterator.next();
            if (f.getName().equalsIgnoreCase(name)) {
                return f;
            }
            String[] alias = f.getAlias();
            int length = alias.length;
            int i = 0;
            while (true) {
                if (i >= length) continue block0;
                String s = alias[i];
                if (s.equalsIgnoreCase(name)) {
                    return f;
                }
                ++i;
            }
            break;
        }
        return null;
    }

    public static List<Module> getModulesInType(Type t) {
        ArrayList<Module> output = new ArrayList<Module>();
        Iterator<Module> iterator = modules.iterator();
        while (iterator.hasNext()) {
            Module m = iterator.next();
            if (m.getType() != t) continue;
            output.add(m);
        }
        return output;
    }

    @EventHandler
    private void onKeyPress(EventKey e) {
        Iterator<Module> iterator = modules.iterator();
        while (iterator.hasNext()) {
            Module m = iterator.next();
            if (m.getKey() != e.getKey()) continue;
            m.setEnabled(!m.isEnabled());
        }
    }

    @EventHandler
    private void onGLHack(EventRender3D e) {
        GlStateManager.getFloat(2982, (FloatBuffer)GLUtils.MODELVIEW.clear());
        GlStateManager.getFloat(2983, (FloatBuffer)GLUtils.PROJECTION.clear());
        GL11.glGetInteger((int)2978, (IntBuffer)((IntBuffer)GLUtils.VIEWPORT.clear()));
    }

    @EventHandler
    private void on2DRender(EventRender2D e) {
        if (!this.enabledNeededMod) return;
        this.enabledNeededMod = false;
        Iterator<Module> iterator = modules.iterator();
        while (iterator.hasNext()) {
            Module m = iterator.next();
            if (!m.enabledOnStartup) continue;
            m.setEnabled(true);
        }
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private void readSettings() {
        binds = FileManager.read("Binds.txt");
        for (Object v : binds) {
            name = v.split(":")[0];
            bind = v.split(":")[1];
            m = ModuleManager.getModuleByName(name);
            if (m == null) continue;
            m.setKey(Keyboard.getKeyIndex((String)bind.toUpperCase()));
        }
        enabled = FileManager.read("Enabled.txt");
        for (String v : enabled) {
            m = ModuleManager.getModuleByName(v);
            if (m == null) continue;
            m.enabledOnStartup = true;
        }
        vals = FileManager.read("Values.txt");
        var4_4 = vals.iterator();
        block2: while (true) {
            if (var4_4.hasNext() == false) return;
            v = var4_4.next();
            name = v.split(":")[0];
            values = v.split(":")[1];
            m = ModuleManager.getModuleByName(name);
            if (m == null) continue;
            var9_9 = m.getValues().iterator();
            while (true) {
                if (var9_9.hasNext()) ** break;
                continue block2;
                value = var9_9.next();
                if (!value.getName().equalsIgnoreCase(values)) continue;
                if (value instanceof Option) {
                    value.setValue(Boolean.parseBoolean(v.split(":")[2]));
                    continue;
                }
                if (value instanceof Numbers) {
                    value.setValue(Double.parseDouble(v.split(":")[2]));
                    continue;
                }
                ((Mode)value).setMode(v.split(":")[2]);
            }
            break;
        }
    }
}

