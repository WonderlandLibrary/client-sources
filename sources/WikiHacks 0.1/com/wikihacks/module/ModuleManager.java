package com.wikihacks.module;

import com.wikihacks.module.Module;
import com.wikihacks.module.modules.ExampleModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class ModuleManager {

    private static ArrayList<Module> modules = new ArrayList<>();

    public static void init() {
        //modules.add(new Module());
    }

    public static Module getModuleByName(String name) {

        for (Module m : modules) {

            if(m.getName().equalsIgnoreCase(name)) {
                return m;
            }

        }

        return null;

    }

}