package gq.vapu.czfclient.Manager;

import gq.vapu.czfclient.API.EventBus;
import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Misc.EventKey;
import gq.vapu.czfclient.API.Events.Render.EventRender2D;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.API.Value.Numbers;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.API.Value.Value;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.*;
import gq.vapu.czfclient.Module.Modules.Combat.*;
import gq.vapu.czfclient.IRC.IRC;
import gq.vapu.czfclient.Module.Modules.Misc.*;
import gq.vapu.czfclient.Module.Modules.Misc.domcer.DomcerFucker;
import gq.vapu.czfclient.Module.Modules.Movement.*;
import gq.vapu.czfclient.Module.Modules.Render.*;
import gq.vapu.czfclient.Util.Render.gl.GLUtils;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager implements Manager {
    public static List<Module> modules = new ArrayList<Module>();
    public boolean nicetry = true;
    private boolean enabledNeededMod = true;

    public static List<Module> getModules() {
        return modules;
    }

    public static Module getModuleByClass(Class<? extends Module> cls) {
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

    @Override
    public void init() {
        // Combat
        modules.add(new AntiBot());
        modules.add(new AutoClicker());
        modules.add(new AutoHeal());
        modules.add(new LegitAura());
        modules.add(new BowAimBot());
//		modules.add(new Blocker());
        modules.add(new Criticals());
        modules.add(new FastBow());
        modules.add(new Killaura());
        modules.add(new Velocity());
        modules.add(new Ragen());
        modules.add(new AutoSword());
        modules.add(new Reach());
//		modules.add(new IQBooster());

        // Render
        modules.add(new Animations());
        modules.add(new BlockOverlay());
        modules.add(new Chams());
        modules.add(new ChestESP());
        modules.add(new FakeFPS());
        modules.add(new ClickGui());
        modules.add(new ESP());
        modules.add(new ItemESP());
        modules.add(new EveryThingBlock());
        modules.add(new FullBright());
        modules.add(new Health());
        modules.add(new HUD());
        modules.add(new NameTag());
        modules.add(new TargetHUD());
        modules.add(new Tracers());
        modules.add(new Xray());
        modules.add(new ViewClip());
        modules.add(new Projectiles());
        modules.add(new NoRender());
        modules.add(new ChineseHat());

        // Move
        modules.add(new AntiFall());
        modules.add(new Blink());
        modules.add(new Fly());
        modules.add(new InvMove());
        modules.add(new Jesus());
        modules.add(new HypixelLongJump());
        modules.add(new NoSlow());
        modules.add(new SafeWalk());
        modules.add(new Scaffold());
        modules.add(new Speed());
//		modules.add(new Spider());
        modules.add(new Sprint());
        modules.add(new Strafe());
        //modules.add(new SkidSense());
        modules.add(new AirJump());
        modules.add(new Teleport());
        modules.add(new VanillaZoomFly());
        modules.add(new KillAuraP());
        modules.add(new AirWalk());
//		modules.add(new LiquidBounce());

        // Misc
        modules.add(new AntiSpam());
        modules.add(new AntiFire());
        modules.add(new DomcerFucker());
        modules.add(new AutoArmor());
        modules.add(new AutoGG());
        modules.add(new FastReport());
        modules.add(new AutoTool());
        modules.add(new ChestSteal());
        modules.add(new FastPlace());
        modules.add(new FastUse());
        modules.add(new RayAura());
        modules.add(new InvCleaner());
        modules.add(new MCF());
        modules.add(new IRC());
        //modules.add(new Debug());
        modules.add(new MemoryFix());
        modules.add(new ModCheck());
        modules.add(new NoFall());
        modules.add(new NoJumpDelay());
        modules.add(new PacketMotior());
        modules.add(new NoRotate());
        modules.add(new PingSpoof());
        modules.add(new SpeedMine());
        modules.add(new TimerDisabler());
        modules.add(new Teams());
        modules.add(new WatchdogReporter());
        //modules.add(new IRC());
        //modules.add(new TargetStrafe());
        modules.add(new AutoSpam());
        modules.add(new LightningCheck());

        //czf
//		modules.add(new CzfQuotations());
//		modules.add(new SBTimer());
//		modules.add(new ServerCrasher());
//		modules.add(new DeepDrak());
//		modules.add(new DangerWalk());
//		modules.add(new SbBounce());
//		modules.add(new AntiPenzi());
        //modules.add(new LSD());

        this.readSettings();
        for (Module m : modules) {
            m.makeCommand();
        }
        EventBus.getInstance().register(this);
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

    public List<Module> getModulesInType(ModuleType t) {
        ArrayList<Module> output = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.getType() != t)
                continue;
            output.add(m);
        }
        return output;
    }

    @EventHandler
    private void onKeyPress(EventKey e) throws InterruptedException {
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
    private void on2DRender(EventRender2D e) throws InterruptedException {
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
            m.setKey(Keyboard.getKeyIndex(bind.toUpperCase()));
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
