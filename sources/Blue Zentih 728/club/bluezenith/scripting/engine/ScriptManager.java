package club.bluezenith.scripting.engine;

import club.bluezenith.BlueZenith;
import club.bluezenith.scripting.bindings.BindingsFactory;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import javax.script.Bindings;
import javax.script.ScriptEngine;

import static javax.script.ScriptContext.ENGINE_SCOPE;

public class ScriptManager {
    private final ScriptEngine scriptEngine = new NashornScriptEngineFactory().getScriptEngine();
    private final BindingsFactory bindingsFactory = new BindingsFactory();

    public void updateBindings() {
        final Bindings bindings = scriptEngine.createBindings();

        bindings.put("mc", bindingsFactory.createObjectFromClass("mc", Minecraft.class, Minecraft.getMinecraft()));
        bindings.put("player", bindingsFactory.createObjectFromClass("player", EntityPlayerSP.class, Minecraft.getMinecraft().thePlayer));
        bindings.put("client", bindingsFactory.createObjectFromClass("client", BlueZenith.class, BlueZenith.getBlueZenith()));

        scriptEngine.setBindings(bindings, ENGINE_SCOPE);
    }

    public void init() {
        try {
            updateBindings();
            scriptEngine.eval("client.getModuleManager().anal");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
