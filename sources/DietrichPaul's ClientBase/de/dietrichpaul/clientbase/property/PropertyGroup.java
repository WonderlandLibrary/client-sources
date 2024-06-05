/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.property;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PropertyGroup {
    private final Map<String, PropertyGroup> propertyGroups = new HashMap<>();
    private final List<Property> properties = new LinkedList<>();

    private PropertyGroup parent;

    public boolean hasProperties() {
        boolean hasPropertyGroupProperties = propertyGroups.values().stream().anyMatch(PropertyGroup::hasProperties);
        return hasPropertyGroupProperties || !properties.isEmpty();
    }

    public void reportChanges() {
        if (parent != null)
            parent.reportChanges();
    }

    public void addProperty(Property property) {
        property.parent = this;
        properties.add(property);
    }

    public PropertyGroup addPropertyGroup(String name) {
        PropertyGroup group = new PropertyGroup();
        group.parent = this;
        propertyGroups.put(name, group);
        return group;
    }

    public PropertyGroup getParent() {
        return parent;
    }

    public PropertyGroup getPropertyGroup(String name) {
        return propertyGroups.get(name);
    }

    public Map<String, PropertyGroup> getPropertyGroups() {
        return propertyGroups;
    }

    public void deserializeProperties(JsonObject object) {
        if (object == null)
            return;
        if (object.has("groups")) {
            JsonObject groups = object.getAsJsonObject("groups");
            propertyGroups.forEach((key, value) -> {
                value.deserializeProperties(groups.getAsJsonObject(key));
            });
        }
        for (Property property : properties) {
            property.deserialize(object.get(property.getName()));
        }
    }

    public JsonObject serializeProperties() {
        JsonObject object = new JsonObject();
        JsonObject groups = new JsonObject();
        propertyGroups.forEach((key, value) -> {
            groups.add(key, value.serializeProperties());
        });
        object.add("groups", groups);
        for (Property property : properties) {
            object.add(property.getName(), property.serialize());
        }
        return object;
    }

    public List<Property> getProperties() {
        return properties;
    }
}
