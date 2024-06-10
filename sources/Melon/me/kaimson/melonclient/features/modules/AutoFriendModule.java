package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.config.*;
import java.util.regex.*;

public class AutoFriendModule extends Module
{
    public static AutoFriendModule INSTANCE;
    private final Pattern pattern;
    
    public AutoFriendModule() {
        super("Auto Friend");
        this.pattern = Pattern.compile("§m----------------------------------------------------Friend request from (?<name>.+)\\[ACCEPT\\] - \\[DENY\\] - \\[IGNORE\\].*");
        AutoFriendModule.INSTANCE = this;
    }
    
    public void onChat(final eu chatComponent) {
        if (ModuleConfig.INSTANCE.isEnabled(this) && ave.A().D() != null && ave.A().D().b != null) {
            final Matcher matcher = this.pattern.matcher(chatComponent.c().replace("\n", ""));
            if (matcher.matches()) {
                String name = matcher.group("name");
                if (name.startsWith("[")) {
                    name = name.substring(name.indexOf("] ") + 2);
                }
                ave.A().h.e("/friend accept " + name);
            }
        }
    }
}
