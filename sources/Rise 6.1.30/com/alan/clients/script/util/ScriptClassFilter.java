package com.alan.clients.script.util;

import org.openjdk.nashorn.api.scripting.ClassFilter;

public final class ScriptClassFilter implements ClassFilter {

    @Override
    public boolean exposeToScripts(final String className) {
        return className.startsWith("com.alan.clients.script.api");
    }
}
