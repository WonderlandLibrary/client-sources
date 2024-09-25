/*
 * Decompiled with CFR 0.150.
 */
package skizzle.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

public abstract class Command {
    public String syntax;
    public List<String> aliases = new ArrayList<String>();
    public String description;
    public Minecraft mc = Minecraft.getMinecraft();
    public String name;

    public abstract void onCommand(String[] var1, String var2);

    public String getDescription() {
        Command Nigga;
        return Nigga.description;
    }

    public void setDescription(String Nigga) {
        Nigga.description = Nigga;
    }

    public List<String> getAliases() {
        Command Nigga;
        return Nigga.aliases;
    }

    public String getName() {
        Command Nigga;
        return Nigga.name;
    }

    public String getSyntax() {
        Command Nigga;
        return Nigga.syntax;
    }

    public Command(String Nigga, String Nigga2, String Nigga3, String ... Nigga4) {
        Command Nigga5;
        Nigga5.name = Nigga;
        Nigga5.description = Nigga2;
        Nigga5.syntax = Nigga3;
        Nigga5.aliases = Arrays.asList(Nigga4);
    }

    public void setAliases(List<String> Nigga) {
        Nigga.aliases = Nigga;
    }

    public void setSyntax(String Nigga) {
        Nigga.syntax = Nigga;
    }

    public static {
        throw throwable;
    }

    public void setName(String Nigga) {
        Nigga.name = Nigga;
    }
}

