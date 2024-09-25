/*
 * Decompiled with CFR 0.150.
 */
package skizzle.scripts;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import net.minecraft.client.Minecraft;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventAttack;
import skizzle.events.listeners.EventChat;
import skizzle.events.listeners.EventMotion;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventRender3D;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.newFont.FontUtil;
import skizzle.util.RenderUtil;

public class Script
extends Module {
    public Object script;
    public ScriptEngine engine;
    public Invocable caller;

    @Override
    public void onDisable() {
        Script Nigga;
        Nigga.callFunction(Qprot0.0("\u6b2a\u71c5\u507d\u8196\ua370\uee9e\u8c2d\u076c\u717c"));
    }

    @Override
    public void onEvent(Event Nigga) {
        Script Nigga2;
        if (Nigga instanceof EventAttack) {
            Nigga2.callFunction(Qprot0.0("\u6b2a\u71c5\u5078\ue279\u8f79\uee9e\u8c2c\u076b"));
        }
        if (Nigga instanceof EventPacket) {
            try {
                Nigga2.caller.invokeFunction(Qprot0.0("\u6b2a\u71c5\u5069\ue26c\u8f6e\uee94\u8c2a\u0774"), (EventPacket)Nigga);
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (Exception Nigga3) {
                Nigga3.printStackTrace();
            }
        }
        if (Nigga instanceof EventUpdate) {
            Nigga2.callFunction(Qprot0.0("\u6b2a\u71c5\u506c\ue27d\u8f69\uee9e\u8c3b\u0765"));
        }
        if (Nigga instanceof EventMotion) {
            try {
                Nigga2.caller.invokeFunction(Qprot0.0("\u6b2a\u71c5\u5074\ue262\u8f7b\uee9a"), (EventMotion)Nigga);
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (Exception Nigga4) {
                Nigga4.printStackTrace();
            }
        }
        if (Nigga instanceof EventRender3D) {
            Nigga2.callFunction(Qprot0.0("\u6b2a\u71c5\u506b\ue268\u8f63\uee9b\u8c2a\u0772\u12d8\u2403"), (EventRender3D)Nigga);
        }
        if (Nigga instanceof EventRenderGUI) {
            Nigga2.callFunction(Qprot0.0("\u6b2a\u71c5\u506b\ue268\u8f63\uee9b\u8c2a\u0772\u12ac\u2412\u649b"));
        }
        if (Nigga instanceof EventChat) {
            try {
                Nigga2.caller.invokeFunction(Qprot0.0("\u6b2a\u71c5\u507a\ue265\u8f6c\uee8b"), (EventChat)Nigga);
            }
            catch (NoSuchMethodException noSuchMethodException) {
            }
            catch (Exception Nigga5) {
                Nigga5.printStackTrace();
            }
        }
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEnable() {
        Script Nigga;
        Nigga.callFunction(Qprot0.0("\u6b2a\u71c5\u507c\u59da\u7b2b\uee9d\u8c23\u0765"));
    }

    public boolean callFunction(String Nigga, Object ... Nigga2) {
        try {
            Script Nigga3;
            Nigga3.caller.invokeFunction(Nigga, new Object[]{Nigga2});
            return true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
        }
        catch (Exception Nigga4) {
            Nigga4.printStackTrace();
        }
        return false;
    }

    public boolean callFunction(String Nigga) {
        try {
            Script Nigga2;
            Nigga2.caller.invokeFunction(Nigga, new Object[0]);
            return true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
        }
        catch (Exception Nigga3) {
            Nigga3.printStackTrace();
        }
        return false;
    }

    public Script(String Nigga, Object Nigga2, ScriptEngine Nigga3) throws ScriptException {
        super(Nigga, 0, Module.Category.SCRIPTS);
        Script Nigga4;
        Bindings Nigga5 = Nigga3.getBindings(100);
        Nigga5.put(Qprot0.0("\u6b16\u71c0\u5050\ua7fe\u42e0\uee93\u8c2a"), (Object)Client.class);
        Nigga5.put(Qprot0.0("\u6b28\u71c8"), (Object)Minecraft.getMinecraft());
        Nigga5.put(Qprot0.0("\u6b35\u71c7\u5058\ua7fd\u42ff\uee8d"), (Object)Minecraft.getMinecraft().thePlayer);
        Nigga5.put(Qprot0.0("\u6b37\u71ce\u5057\ua7e0\u42ff\uee8d"), (Object)RenderUtil.class);
        Nigga5.put(Qprot0.0("\u6b28\u71ce"), (Object)Nigga4);
        Nigga5.put(Qprot0.0("\u6b31\u71c2\u5054\ua7e1\u42e8"), (Object)Float.valueOf(Minecraft.getMinecraft().timer.timerSpeed));
        Nigga5.put(Qprot0.0("\u6b23\u71d9"), (Object)FontUtil.cleanmedium);
        Nigga5.put(Qprot0.0("\u6b28\u71c2\u5057\ua7e1\u42f9\uee8d\u8c2e\u0766\u5716\ue980\u64b3\uaf0f\ue9b0\u7248\u36d7\uf733"), (Object)Qprot0.0("\u6b2b\u71ce\u504d\ua7aa\u42f7\uee96\u8c21\u0765\u5701\ue9a2\u64b3\uaf0a\ue9af\u7203\u36cd\uf725\u42fd\udd55\u170f\u5da9\u92e8\u0184\u7b60\ue860\u855c\ud63b\u2f50"));
        Nigga3.setBindings(Nigga5, 100);
        Nigga4.script = Nigga2;
        Nigga4.engine = Nigga3;
        Nigga4.caller = (Invocable)((Object)Nigga3);
        Nigga4.callFunction(Qprot0.0("\u6b2a\u71c5\u5075\ua7eb\u42fb\uee9b"));
    }
}

