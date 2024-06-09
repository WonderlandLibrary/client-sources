package us.dev.direkt.file.internal.files;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import us.dev.api.property.Property;
import us.dev.api.property.multi.MultiProperty;
import us.dev.direkt.Direkt;
import us.dev.direkt.file.internal.AbstractClientFile;
import us.dev.direkt.file.internal.FileData;
import us.dev.direkt.file.internal.util.RuntimeTypeAdapterFactory;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.traits.TransientProperties;
import us.dev.direkt.module.annotations.traits.TransientStatus;
import us.dev.direkt.module.property.ModProperty;
import us.dev.direkt.module.property.Propertied;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Foundry
 */
@FileData(fileName = "mods")
public final class ModulesFile extends AbstractClientFile {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Map.class, new StringObjectMapDeserializer())
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(SerializedModuleData.class, "modType")
                .registerSubtype(SerializedToggleableModuleData.class, "TOGGLEABLE")
                .registerSubtype(SerializedInternalModuleData.class, "INTERNAL"))
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setPrettyPrinting()
            .create();

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void load() {
        try (Reader reader = new BufferedReader(new FileReader(this.getFile()))){
            final Type serializedObject = new TypeToken<HashMap<String, SerializedModuleData>>(){}.getType();
            final Map<String, SerializedModuleData> serializedData = gson.fromJson(reader, serializedObject);

            if (serializedData != null) {
                for (Module module : Direkt.getInstance().getModuleManager().getModules()) {
                    final SerializedModuleData moduleDataLookup = serializedData.get(module.getLabel());
                    if (moduleDataLookup != null) {
                        if (moduleDataLookup instanceof SerializedToggleableModuleData) {
                            ((ToggleableModule) module).getKeybind().setKey(((SerializedToggleableModuleData) moduleDataLookup).getKeybind());
                            if (((SerializedToggleableModuleData) moduleDataLookup).isEnabled()) {
                                ((ToggleableModule) module).setRunning(true, false);
                            }
                        }
                        if (module instanceof Propertied) {
                            final Propertied propertiedModule = (Propertied) module;
                            for (ModProperty property : propertiedModule.getProperties()) {
                                final Object propertyValue = moduleDataLookup.properties.get(property.getLabel());
                                if (propertyValue != null) {
                                    try {
                                        if (Enum.class.isAssignableFrom(property.getType())) {
                                            final Enum enumProperty = (Enum) property.getValue();
                                            property.setValue(Enum.valueOf(enumProperty.getDeclaringClass(), (String) propertyValue));

                                        } else if (property.getProperty() instanceof MultiProperty) {
                                            for (Map.Entry<String, Map<String, ?>> mapEntry : ((Map<String, Map<String, ?>>) propertyValue).entrySet()) {
                                                final Property targetProperty = ((MultiProperty) property.getProperty()).getValue(mapEntry.getKey());
                                                if (targetProperty != null) {
                                                    targetProperty.setValue(mapEntry.getValue().get("value"));
                                                }
                                            }
                                        } else {
                                            property.setValue(propertyValue);
                                        }
                                    } catch (Exception e) {
                                        System.err.println("Unable to resolve data from saved property " + property.getLabel());
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Unable to read file " + this.getFile().getName());
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        final Map<String, SerializedModuleData> serializedData = Maps.newHashMap();
        Direkt.getInstance().getModuleManager().getModules().stream()
                .filter(Propertied.class::isInstance)
                .map(mod -> (Module & Propertied) mod)
                .forEach(mod -> {
                    final TransientProperties propertiesAnnotation = mod.getClass().getAnnotation(TransientProperties.class);
                    List<Property> modProperties = mod.getProperties().stream().map(ModProperty::getProperty).collect(Collectors.toList());
                    if (propertiesAnnotation != null) {
                        if (propertiesAnnotation.value().length > 0) {
                            final List<String> backingList = Arrays.asList(propertiesAnnotation.value());
                            modProperties.removeIf(property -> backingList.contains(property.getLabel()));
                        } else {
                            modProperties = Collections.emptyList();
                        }
                    }
                    serializedData.put(mod.getLabel(), mod instanceof ToggleableModule
                            ? new SerializedToggleableModuleData(((ToggleableModule) mod).getKeybind().getKey(), !mod.getClass().isAnnotationPresent(TransientStatus.class) && ((ToggleableModule) mod).isRunning(), modProperties)
                            : new SerializedInternalModuleData(modProperties));
                });
        try {
            Files.write(gson.toJson(serializedData).getBytes("UTF-8"), this.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static abstract class SerializedModuleData {
        protected final Map<String, Object> properties = Maps.newHashMap();
        protected String modType;

        SerializedModuleData(List<Property> properties, String modType) {
            this.modType = modType;
            properties.forEach(property -> this.properties.put(property.getLabel(), property.getValue()));
        }
    }

    private static class SerializedInternalModuleData extends SerializedModuleData {
        SerializedInternalModuleData(List<Property> properties) {
            super(properties, "INTERNAL");
        }
    }

    private static class SerializedToggleableModuleData extends SerializedModuleData {
        private final int keybind;
        private final boolean enabled;

        SerializedToggleableModuleData(int keybind, boolean enabled, List<Property> properties) {
            super(properties, "TOGGLEABLE");
            this.keybind = keybind;
            this.enabled = enabled;
        }

        public int getKeybind() {
            return keybind;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }

    //    Gson sucks at differentiating between a float and an integer
    private class StringObjectMapDeserializer implements JsonDeserializer<Map<String, Object>> {
        @Override
        public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final Map<String, Object> m = new LinkedHashMap<>();
            final JsonObject jo = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> mx : jo.entrySet()) {
                final String key = mx.getKey();
                final JsonElement v = mx.getValue();

                if (v.isJsonArray()) {
                    m.put(key, gson.fromJson(v, List.class));
                }
                else if (v.isJsonPrimitive()) {
                    Number num = null;
                    final ParsePosition position = new ParsePosition(0);
                    final String vString = v.getAsString();

                    try {
                        num = NumberFormat.getInstance(Locale.ROOT).parse(vString, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (num != null && (vString.contains(".") || vString.contains(",")))
                        num = num.doubleValue();

                    if (num instanceof Long)
                        num = num.intValue();

                    //Check if the position corresponds to the length of the string
                    if (position.getErrorIndex() < 0
                            && vString.length() == position.getIndex()) {
                        if (num != null) {
                            m.put(key, num);
                            continue;
                        }
                    }

                    final JsonPrimitive prim = v.getAsJsonPrimitive();
                    if (prim.isBoolean()) {
                        m.put(key, prim.getAsBoolean());
                    }
                    else if (prim.isString()) {
                        m.put(key, prim.getAsString());
                    }
                    else {
                        m.put(key, null);
                    }

                }
                else if (v.isJsonObject()) {
                    m.put(key, gson.fromJson(v, Map.class));
                }

            }
            return m;
        }
    }
}