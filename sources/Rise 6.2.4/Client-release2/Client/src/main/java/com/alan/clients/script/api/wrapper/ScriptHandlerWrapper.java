package com.alan.clients.script.api.wrapper;

import com.alan.clients.script.util.ScriptHandler;
import org.openjdk.nashorn.api.scripting.JSObject;

public abstract class ScriptHandlerWrapper<T> extends ScriptWrapper<T> {

    private final ScriptHandler handler = new ScriptHandler();

    public ScriptHandlerWrapper(final T wrapped) {
        super(wrapped);
    }

    public void handle(final String functionName, final JSObject function) {
        this.handler.handle(functionName, function);
    }

    public void unhandle(final String functionName) {
        this.handler.unhandle(functionName);
    }

    public void call(final String functionName, final Object... parameters) {
        this.handler.call(functionName, parameters);
    }
}
