/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Arrays;
import java.util.List;
import net.minecraft.util.text.TextFormatting;
import ru.govno.client.module.Module;
import ru.govno.client.ui.login.GuiAltLogin;
import ru.govno.client.utils.Command.impl.Panic;

public class NameSecurity
extends Module {
    public static Module get;

    public NameSecurity() {
        super("NameSecurity", 0, Module.Category.PLAYER);
        get = this;
    }

    public static Module me() {
        return get;
    }

    public static String replacedName() {
        String uni = "\u2661";
        return TextFormatting.LIGHT_PURPLE + uni + "LoveSex" + uni + TextFormatting.RESET;
    }

    private static List<String> namesToReplaceList() {
        List<String> namesToReplace = Arrays.asList(NameSecurity.mc.session.getUsername());
        return namesToReplace;
    }

    public static String replacedIfActive(String name) {
        if (get == null || Panic.stop || !NameSecurity.me().actived || NameSecurity.mc.currentScreen instanceof GuiAltLogin) {
            return name;
        }
        for (String Name2 : NameSecurity.namesToReplaceList()) {
            name = name.replace(Name2, NameSecurity.replacedName());
        }
        return name;
    }
}

