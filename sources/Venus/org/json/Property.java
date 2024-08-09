/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.json;

import java.util.Enumeration;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

public class Property {
    public static JSONObject toJSONObject(Properties properties) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (properties != null && !properties.isEmpty()) {
            Enumeration<?> enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String string = (String)enumeration.nextElement();
                jSONObject.put(string, properties.getProperty(string));
            }
        }
        return jSONObject;
    }

    public static Properties toProperties(JSONObject jSONObject) throws JSONException {
        Properties properties = new Properties();
        if (jSONObject != null) {
            for (String string : jSONObject.keySet()) {
                Object object = jSONObject.opt(string);
                if (JSONObject.NULL.equals(object)) continue;
                properties.put(string, object.toString());
            }
        }
        return properties;
    }
}

