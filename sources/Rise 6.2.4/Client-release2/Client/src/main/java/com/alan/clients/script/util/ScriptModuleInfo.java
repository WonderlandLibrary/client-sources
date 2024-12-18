package com.alan.clients.script.util;

import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.Annotation;

public final class ScriptModuleInfo implements ModuleInfo {

    private final String name, description;

    public ScriptModuleInfo(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public String[] aliases() {
        return new String[]{name};
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public Category category() {
        return Category.SCRIPT;
    }

    @Override
    public int keyBind() {
        return Keyboard.KEY_NONE;
    }

    @Override
    public boolean autoEnabled() {
        return false;
    }

    @Override
    public boolean allowDisable() {
        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return ScriptModuleInfo.class;
    }
}
