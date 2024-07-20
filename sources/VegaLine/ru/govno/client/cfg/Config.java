/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.cfg;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import ru.govno.client.Client;
import ru.govno.client.cfg.ConfigManager;
import ru.govno.client.cfg.ConfigUpdater;
import ru.govno.client.friendsystem.Friend;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ClickGui;
import ru.govno.client.module.modules.PointTrace;
import ru.govno.client.utils.MacroMngr.Macros;
import ru.govno.client.utils.MacroMngr.MacrosManager;

public final class Config
implements ConfigUpdater {
    private final String name;
    private final File file;

    public Config(String name) {
        this.name = name;
        this.file = new File(ConfigManager.configDirectory, name + ".vls");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    public JsonObject getJsonFromMacros(Macros macros) {
        JsonObject object = new JsonObject();
        object.addProperty("Name", macros.getName());
        object.addProperty("KeyBound", macros.getKey());
        object.addProperty("Massage", macros.getMassage());
        return object;
    }

    public void loadAllMacrosesFromJson(JsonObject object) {
        Client.macrosManager.clear();
        String defaultName = "Macros-\u2116";
        for (int index = 0; index < Integer.MAX_VALUE; ++index) {
            String groupName = defaultName + index;
            String groupNameNext = defaultName + (index + 1);
            if (object.has(groupName)) {
                JsonObject propertiesObject = object.getAsJsonObject(groupName);
                if (propertiesObject == null) continue;
                JsonElement elementName = propertiesObject.get("Name");
                JsonElement elementKeyBound = propertiesObject.get("KeyBound");
                JsonElement elementMassage = propertiesObject.get("Massage");
                Client.macrosManager.add(new Macros(elementName.getAsString(), elementKeyBound.getAsInt(), elementMassage.getAsString()));
            }
            if (!object.has(groupNameNext)) break;
        }
    }

    public JsonObject getJsonFromPointTrace(PointTrace pointTrace) {
        JsonObject object = new JsonObject();
        object.addProperty("Name", pointTrace.getName());
        object.addProperty("ServerName", pointTrace.getServerName());
        object.addProperty("XPosition", PointTrace.getX(pointTrace));
        object.addProperty("YPosition", PointTrace.getY(pointTrace));
        object.addProperty("ZPosition", PointTrace.getZ(pointTrace));
        object.addProperty("Dimension", PointTrace.getDemension(pointTrace));
        object.addProperty("Index", pointTrace.getIndex());
        return object;
    }

    public void loadAllPointTracesFromJson(JsonObject object) {
        PointTrace.getPointList().clear();
        String defaultName = "PointTrace-\u2116";
        for (int index = 0; index < Integer.MAX_VALUE; ++index) {
            String groupName = defaultName + index;
            String groupNameNext = defaultName + (index + 1);
            if (object.has(groupName)) {
                JsonObject propertiesObject = object.getAsJsonObject(groupName);
                if (propertiesObject == null) continue;
                JsonElement elementName = propertiesObject.get("Name");
                JsonElement elementServerName = propertiesObject.get("ServerName");
                JsonElement elementXPosition = propertiesObject.get("XPosition");
                JsonElement elementYPosition = propertiesObject.get("YPosition");
                JsonElement elementZPosition = propertiesObject.get("ZPosition");
                JsonElement elementDimension = propertiesObject.get("Dimension");
                JsonElement elementIndex = propertiesObject.get("Index");
                PointTrace.getPointList().add(new PointTrace(elementName.getAsString(), elementServerName.getAsString(), elementXPosition.getAsDouble(), elementYPosition.getAsDouble(), elementZPosition.getAsDouble(), elementDimension.getAsInt(), elementIndex.getAsInt()));
            }
            if (!object.has(groupNameNext)) break;
        }
    }

    public JsonObject getJsonFromFriend(Friend friend) {
        JsonObject object = new JsonObject();
        object.addProperty("Name", friend.getName());
        return object;
    }

    public void loadAllFriendsFromJson(JsonObject object) {
        Client.friendManager.clearFriends();
        String defaultName = "Friend-\u2116";
        for (int index = 0; index < Integer.MAX_VALUE; ++index) {
            String groupName = defaultName + index;
            String groupNameNext = defaultName + (index + 1);
            if (object.has(groupName)) {
                JsonObject propertiesObject = object.getAsJsonObject(groupName);
                if (propertiesObject == null) continue;
                JsonElement elementName = propertiesObject.get("Name");
                Client.friendManager.addFriend(elementName.getAsString());
            }
            if (!object.has(groupNameNext)) break;
        }
    }

    public JsonObject getJsonFromSettings(Module module) {
        JsonObject object = new JsonObject();
        object.addProperty("EnabledState", module.isActived());
        object.addProperty("KeyBound", module.getBind());
        JsonObject propertiesObject = new JsonObject();
        module.getSettings().forEach(set -> {
            switch (set.getCategory()) {
                case Boolean: {
                    propertiesObject.addProperty(set.getName(), set.getBool());
                    break;
                }
                case Float: {
                    propertiesObject.addProperty(set.getName(), Float.valueOf(set.getFloat()));
                    break;
                }
                case String_Massive: {
                    propertiesObject.addProperty(set.getName(), set.getMode());
                    break;
                }
                case Color: {
                    propertiesObject.addProperty(set.getName(), set.getCol());
                }
            }
            object.add("AllSets", propertiesObject);
        });
        return object;
    }

    public void loadSettingsFromJson(JsonObject object, Module module) {
        JsonObject propertiesObject;
        if (object == null) {
            return;
        }
        if (object.has("EnabledState") && !(module instanceof ClickGui)) {
            module.toggleSilent(object.get("EnabledState").getAsBoolean());
        }
        if (object.has("KeyBound")) {
            module.setBind(object.get("KeyBound").getAsInt());
        }
        if ((propertiesObject = object.getAsJsonObject("AllSets")) == null) {
            return;
        }
        module.getSettings().stream().filter(set -> propertiesObject.has(set.getName())).forEach(set -> {
            JsonElement value = propertiesObject.get(set.getName());
            switch (set.getCategory()) {
                case Boolean: {
                    set.setBool(value.getAsBoolean());
                    break;
                }
                case Float: {
                    set.setFloat(value.getAsFloat());
                    break;
                }
                case String_Massive: {
                    set.setMode(value.getAsString());
                    break;
                }
                case Color: {
                    set.setCol(value.getAsInt());
                }
            }
        });
    }

    @Override
    public JsonObject save() {
        JsonObject jsonObject = new JsonObject();
        JsonObject modulesObject = new JsonObject();
        JsonObject macrosesObject = new JsonObject();
        JsonObject pointsObject = new JsonObject();
        JsonObject friendsObject = new JsonObject();
        Client.moduleManager.getModuleList().forEach(mod -> modulesObject.add(mod.getName(), this.getJsonFromSettings((Module)mod)));
        jsonObject.add("AllMods", modulesObject);
        if (!MacrosManager.macroses.isEmpty()) {
            int macrosIndex = 0;
            for (Macros macros : MacrosManager.macroses) {
                macrosesObject.add("Macros-\u2116" + macrosIndex, this.getJsonFromMacros(macros));
                ++macrosIndex;
            }
            jsonObject.add("AllMacroses", macrosesObject);
        }
        if (!PointTrace.getPointList().isEmpty()) {
            int pointIndex = 0;
            for (PointTrace pointTrace : PointTrace.getPointList()) {
                pointsObject.add("PointTrace-\u2116" + pointIndex, this.getJsonFromPointTrace(pointTrace));
                ++pointIndex;
            }
            jsonObject.add("AllPointTraces", pointsObject);
        }
        if (!Client.friendManager.getFriends().isEmpty()) {
            int friendIndex = 0;
            for (Friend friend : Client.friendManager.getFriends()) {
                friendsObject.add("Friend-\u2116" + friendIndex, this.getJsonFromFriend(friend));
                ++friendIndex;
            }
            jsonObject.add("AllFriends", friendsObject);
        }
        return jsonObject;
    }

    @Override
    public void load(JsonObject object) {
        JsonObject pointTracesObject;
        Client.moduleManager.getModuleList().forEach(Module::disableSilent);
        if (object.has("AllMods")) {
            JsonObject modulesObject = object.getAsJsonObject("AllMods");
            Client.moduleManager.getModuleList().forEach(mod -> this.loadSettingsFromJson(modulesObject.getAsJsonObject(mod.getName()), (Module)mod));
        }
        if (object.has("AllMacroses")) {
            JsonObject macrosesObject = object.getAsJsonObject("AllMacroses");
            this.loadAllMacrosesFromJson(macrosesObject);
            System.out.println("mac-sucess >><");
        }
        if (object.has("AllPointTraces")) {
            pointTracesObject = object.getAsJsonObject("AllPointTraces");
            this.loadAllPointTracesFromJson(pointTracesObject);
            System.out.println("point-sucess >><");
        }
        if (object.has("AllFriends")) {
            pointTracesObject = object.getAsJsonObject("AllFriends");
            this.loadAllFriendsFromJson(pointTracesObject);
            System.out.println("friend-sucess >><");
        }
    }
}

