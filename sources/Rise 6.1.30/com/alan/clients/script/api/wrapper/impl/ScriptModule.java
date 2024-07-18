package com.alan.clients.script.api.wrapper.impl;

import com.alan.clients.Client;
import com.alan.clients.event.Event;
import com.alan.clients.module.Module;
import com.alan.clients.script.api.RenderAPI;
import com.alan.clients.script.api.wrapper.ScriptHandlerWrapper;
import com.alan.clients.script.api.wrapper.impl.event.ScriptEvent;
import com.alan.clients.value.Value;
import com.alan.clients.value.impl.*;
import org.openjdk.nashorn.api.scripting.JSObject;
import org.openjdk.nashorn.internal.runtime.Undefined;

import java.awt.*;
import java.util.function.Function;

public final class ScriptModule extends ScriptHandlerWrapper<Module> {
    private final Function<Event, Boolean> eventListenerFunction = event -> {
        if (!Client.INSTANCE.getBindableManager().getBinds().contains(this.wrapped)) return true;

        passEvent:
        {
            if (!this.wrapped.isEnabled()) break passEvent;

            final ScriptEvent<? extends Event> scriptEvent = event.getScriptEvent();
            if (scriptEvent == null) break passEvent;

            this.call(scriptEvent.getHandlerName(), scriptEvent);
        }

        return false;
    };

    public ScriptModule(final Module wrapped, boolean register) {
        super(wrapped);
        if (register) Client.INSTANCE.getEventBus().registerCustom(eventListenerFunction);
    }

    public ScriptModule(final Module wrapped) {
        super(wrapped);
    }

    public void unregister() {
        // Disable the wrapped thus calling the onDisable function
        if (this.wrapped.isEnabled()) this.wrapped.setEnabled(false);

        this.wrapped.getValues().clear();
        Client.INSTANCE.getModuleManager().remove(this.wrapped);
        Client.INSTANCE.getClickGUI().rebuildModuleCache();
    }

    public String getName() {
        return this.wrapped.getName();
    }

    public String getCategory() {
        return this.wrapped.getModuleInfo().category().getName();
    }

    public String getDescription() {
        return this.wrapped.getModuleInfo().description();
    }

    public boolean isEnabled() {
        return this.wrapped.isEnabled();
    }

    public void toggle() {
        this.wrapped.toggle();
    }

    public void setEnabled(boolean enabled) {
        this.wrapped.setEnabled(enabled);
    }

    public String getTag() {
        return !this.wrapped.getValues().isEmpty() && this.wrapped.getValues().get(0) instanceof ModeValue ?
                ((ModeValue) this.wrapped.getValues().get(0)).getValue().getName() : null;
    }

    @Override
    public void handle(final String functionName, final JSObject function) {
        super.handle(functionName, function);
    }

    @Override
    public void call(final String functionName, final Object... parameters) {
        super.call(functionName, parameters);
    }

    public void registerSetting(String type, String name, Object defaultValue, Object... params) {
        switch (type.toLowerCase()) {
            case "string": {
                new StringValue(name, this.wrapped, (String) defaultValue);
                break;
            }
            case "number": {
                new NumberValue(name, this.wrapped, (Number) defaultValue, (Number) params[0], (Number) params[1], params.length >= 3 ? (Number) params[2] : 1);
                break;
            }
            case "boundsnumber": {
                new BoundsNumberValue(name, this.wrapped, (Number) defaultValue, (Number) params[0], (Number) params[1], (Number) params[2], (Number) params[3]);
                break;
            }
            case "boolean": {
                new BooleanValue(name, this.wrapped, (boolean) defaultValue);
                break;
            }
            case "mode": {
                ModeValue value = new ModeValue(name, this.wrapped);

                for (Object object : params) {
                    value.add(new SubMode((String) object));
                }

                value.setDefault((String) defaultValue);
                break;
            }
            case "color": {
                Color c = Color.WHITE;
                // The conversion below is needed since the Nashorn API passes a JSObject object.
                if (defaultValue instanceof JSObject) {
                    JSObject object = (JSObject) defaultValue;
                    if (object.isArray()) {
                        int[] values = object.values().stream().map(x -> x instanceof Number ? ((Number) x).intValue() : 255).mapToInt(Integer::intValue).toArray();
                        if (values.length >= 3) {
                            c = RenderAPI.intArrayToColor(values);
                        }
                    }
                }
                new ColorValue(name, this.wrapped, c);
                break;
            }
        }
    }

    public Object getSetting(String name) {
        try {
            Value<?> o = this.wrapped.getAllValues().stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst().get();
            if (o instanceof ColorValue) {
                int[] array = new int[4];
                Color c = ((ColorValue) o).getValue();
                array[0] = c.getRed();
                array[1] = c.getGreen();
                array[2] = c.getBlue();
                array[3] = c.getAlpha();
                return array;
            } else if (o instanceof NumberValue) {
                return ((NumberValue) o).getValue().doubleValue();
            } else if (o instanceof BoundsNumberValue) {
                double[] array = new double[2];
                array[0] = ((BoundsNumberValue) o).getValue().doubleValue();
                array[1] = ((BoundsNumberValue) o).getSecondValue().doubleValue();
                return array;
            } else if (o instanceof BooleanValue || o instanceof StringValue) {
                return o.getValue();
            } else if (o instanceof ModeValue) {
                return ((ModeValue) o).getValue().getName();
            }
        } catch (Exception ignored) {
        }
        return Undefined.getUndefined();
    }

    public void setSetting(String name, Object value) {
        try {
            Value<?> o = this.wrapped.getAllValues().stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst().get();
            if (o instanceof ColorValue) {
                int[] array = (int[]) value;
                ((ColorValue) o).setValue(new Color(array[0], array[1], array[2], array[3]));
            } else if (o instanceof NumberValue) {
                ((NumberValue) o).setValue((Number) value);
            } else if (o instanceof BoundsNumberValue) {
                Number[] array = (Number[]) value;
                ((BoundsNumberValue) o).setValue(array[0]);
                ((BoundsNumberValue) o).setSecondValue(array[1]);
            } else if (o instanceof BooleanValue) {
                ((BooleanValue) o).setValue((boolean) value);
            } else if (o instanceof ModeValue) {
                ((ModeValue) o).setValue((String) value);
            } else if (o instanceof StringValue) {
                ((StringValue) o).setValue((String) value);
            }
        } catch (Exception ignored) {
        }
    }

    public void setSettingVisibility(String name, boolean hidden) {
        try {
            Value<?> o = this.wrapped.getValues().stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst().get();
            o.setHideIf(() -> !hidden);
        } catch (Exception ignored) {
        }
    }
}
