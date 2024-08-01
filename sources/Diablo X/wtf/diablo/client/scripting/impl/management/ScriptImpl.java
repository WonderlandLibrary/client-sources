package wtf.diablo.client.scripting.impl.management;

import jdk.nashorn.api.scripting.JSObject;
import wtf.diablo.client.event.api.AbstractEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.scripting.api.Script;
import wtf.diablo.client.scripting.impl.data.EventScripting;
import wtf.diablo.client.scripting.impl.data.functions.packet.PacketScriptingFunction;
import wtf.diablo.client.scripting.impl.data.functions.rendering.RenderScriptingFunction;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.util.Scanner;

public final class ScriptImpl implements Script {
    private final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("LUA");
    private final File directory;
    private final EventScripting eventScripting = new EventScripting();
    private AbstractModule abstractModule;


    public ScriptImpl(final File directory) {
        this.directory = directory;
    }

    public void registerEvent(final String event, final AbstractEvent abstractEvent) {
        if (this.abstractModule == null)
            return;

        final JSObject jsObject = this.eventScripting.getEventMap().get(event);
        jsObject.call(jsObject, abstractEvent);
    }

    @Override
    public AbstractModule setAbstractModule(AbstractModule abstractModule) {
        return null;
    }

    @Override
    public void begin() {
        this.addFunctions();

        final StringBuilder scriptingContext = new StringBuilder();

        try {
            final Scanner reader = new Scanner(this.directory);
            while (reader.hasNextLine()) {
                scriptingContext.append(reader.nextLine());
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        try {
            this.scriptEngine.eval(String.valueOf(scriptingContext));
        } catch (final ScriptException scriptException) {
            scriptException.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (this.abstractModule != null) {
            this.abstractModule.toggle(false);
        }
    }

    @Override
    public void addFunctions() {
        this.scriptEngine.put("event", this.eventScripting);
        this.scriptEngine.put("packet", new PacketScriptingFunction());
        this.scriptEngine.put("render", new RenderScriptingFunction());
    }

    @Override
    public AbstractModule getAbstractModule() {
        return this.abstractModule;
    }
}