package dev.darkmoon.client.module.impl.util;

import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

@ModuleAnnotation(name = "NameProtect", category = Category.UTIL)
public class NameProtect extends Module {
    public static String protectedNick = "darkmoon";
}
