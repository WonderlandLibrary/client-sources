package io.github.liticane.monoxide.files.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.files.LocalFile;
import io.github.liticane.monoxide.files.data.FileData;
import io.github.liticane.monoxide.module.api.Module;

@FileData(fileName = "modules")
public class ModulesFile extends LocalFile {

    @Override
    public void save(Gson gson) {
        JsonObject object = new JsonObject();

        JsonObject modulesObject = new JsonObject();

        for (Module module : ModuleManager.getInstance())
            modulesObject.add(module.getIdentifier(), module.save());

        object.add("Modules", modulesObject);

        writeFile(gson.toJson(object), file);
    }

    public void save(Gson gson, String fileName) {
        JsonObject object = new JsonObject();

        JsonObject modulesObject = new JsonObject();

        for (Module module : ModuleManager.getInstance())
            modulesObject.add(module.getIdentifier(), module.save());

        object.add("Modules", modulesObject);

        writeFile(gson.toJson(object), file);
    }

    @Override
    public void load(Gson gson) {
        if (!file.exists()) {
            return;
        }

        JsonObject object = gson.fromJson(readFile(file), JsonObject.class);
        if (object.has("Modules")){
            JsonObject modulesObject = object.getAsJsonObject("Modules");

            for (Module module : ModuleManager.getInstance()) {
                if (modulesObject.has(module.getIdentifier()))
                    module.load(modulesObject.getAsJsonObject(module.getIdentifier()));
            }
        }
    }

    public void load(Gson gson, String fileName) {
        if (!file.exists()) {
            return;
        }

        JsonObject object = gson.fromJson(readFile(file), JsonObject.class);
        if (object.has("Modules")){
            JsonObject modulesObject = object.getAsJsonObject(fileName);

            for (Module module : ModuleManager.getInstance()) {
                if (modulesObject.has(module.getIdentifier()))
                    module.load(modulesObject.getAsJsonObject(module.getIdentifier()));
            }
        }
    }

}