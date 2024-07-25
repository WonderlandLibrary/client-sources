package club.bluezenith.core.data.preferences;

import club.bluezenith.BlueZenith;
import com.google.gson.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.stream;

public class PreferencesSerializer implements DataHandler{

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void deserialize() throws JsonParseException {
        JsonElement jsonElement;

        final String content = BlueZenith.getBlueZenith().getResourceRepository().readFromFile("preferences.json");
        if(content == null) {
            BlueZenith.getBlueZenith().getResourceRepository().createFileInDirectory("preferences.json", false);
            return;
        }

        jsonElement = new JsonParser().parse(BlueZenith.getBlueZenith().getResourceRepository().readFromFile("preferences.json"));

        if(jsonElement == null) return;
        if(!jsonElement.isJsonObject()) throw new IllegalStateException("PreferencesSerializer data must be wrapped in a JSON object!");

        final JsonObject object = jsonElement.getAsJsonObject();
        Map<String, Field> fieldMap = new HashMap<>();

        stream(Preferences.class.getDeclaredFields()).forEach(field -> {
            try {
                final Class<Preference> annotation = Preference.class;

                if (field.isAnnotationPresent(annotation) && isStatic(field.getModifiers())) {
                    field.setAccessible(false);
                    fieldMap.put(field.getAnnotation(annotation).value(), field);
                }

            } catch (Exception exception) {
                System.err.println("Something went terribly wrong while loading preferences! [Scan]");
                exception.printStackTrace();
            }
        });

        object.entrySet().forEach(entry -> {
            try {
                final Field linkedField = fieldMap.get(entry.getKey());
                if (linkedField == null) return;

                linkedField.set(null, gson.fromJson(entry.getValue(), linkedField.getGenericType()));
            } catch (Exception exception) {
                System.err.println("Something went terribly wrong while loading preferences! [Access]");
                exception.printStackTrace();
            }
        });
    }

    @Override
    public void serialize() {
        final JsonObject object = new JsonObject();
        stream(Preferences.class.getDeclaredFields()).forEach(field -> {
            try {
                final Class<Preference> annotation = Preference.class;

                if (field.isAnnotationPresent(annotation) && isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    if(!field.getType().isPrimitive()) {
                        System.err.println("Non-primitive field type in the preference manager. Not saving! (I: " + field.getAnnotation(annotation).value() + ")");
                        return;
                    }
                    object.add(field.getAnnotation(annotation).value(), gson.toJsonTree(field.get(null), field.getGenericType()));
                    BlueZenith.getBlueZenith().getResourceRepository().writeToFile("preferences.json", gson.toJson(object));
                }

            } catch (Exception exception) {
                System.err.println("Something went terribly wrong while saving preferences! [Reflection]");
                exception.printStackTrace();
            }
        });
    }
}
