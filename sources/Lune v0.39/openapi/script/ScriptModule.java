package me.superskidder.lune.openapi.script;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.*;
import me.superskidder.lune.manager.EventManager;
import me.superskidder.lune.manager.event.EventTarget;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.lang.reflect.Method;

/**
 * @description: ...
 * @author: QianXia
 * @create: 2020/11/4 19:05
 **/
public class ScriptModule extends Mod {
    private String moduleName;
    private ModCategory category;
    private Invocable invoke;

    public ScriptModule(String moduleName, ModCategory category, Invocable invocable) {
        super(moduleName, category,"Script Module");
        this.moduleName = moduleName;
        this.category = category;
        this.invoke = invocable;
    }

    @EventTarget
    public void EventAttack(EventAttack event) {
        try {
            invoke.invokeFunction("onAttack", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventChat(EventChat event) {
        try {
            invoke.invokeFunction("onChat", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventKey(EventKey event) {
        try {
            invoke.invokeFunction("onKey", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventPacketSend(EventPacketSend event) {
        try {
            invoke.invokeFunction("onPacketSend", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventPacketReceive(EventPacketReceive event) {
        try {
            invoke.invokeFunction("onPacketReceive", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventUpdate(EventUpdate event) {
        try {
            invoke.invokeFunction("onUpdate", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventPreUpdate(EventPreUpdate event) {
        try {
            invoke.invokeFunction("onPreUpdate", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventPostUpdate(EventPostUpdate event) {
        try {
            invoke.invokeFunction("onPostUpdate", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventRender3D(EventRender3D event) {
        try {
            invoke.invokeFunction("onRender3D", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventRender2D(EventRender2D event) {
        try {
            invoke.invokeFunction("onRender2D", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventStep(EventStep event) {
        try {
            invoke.invokeFunction("onStep", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventTick(EventTick event) {
        try {
            invoke.invokeFunction("onTick", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @EventTarget
    public void EventChangeValue(EventChangeValue event){
        try {
            invoke.invokeFunction("onValueChange", event);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @Override
    public void onEnabled() {
        try {
            invoke.invokeFunction("onEnabled");
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    @Override
    public void onDisable() {
        try {
            invoke.invokeFunction("onDisable");
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            this.unregisterMe();
        }
    }

    private void unregisterMe(){
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String invokedMethodName = stackTrace[2].getMethodName();
            Class<?> eventClazz = null;
            try {
                eventClazz = Class.forName(invokedMethodName);
            }catch (ClassNotFoundException ig){
                // 一般来说 找不到的Method就是onDisable和onEnabled
                // 而这两个Method不在EventManager中被注册
                // 所以直接return掉
                return;
            }
            Method invokedMethod = getClass().getDeclaredMethod(invokedMethodName, eventClazz);
            EventManager.unregister(this, invokedMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
