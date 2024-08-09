/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.mojang.bridge.game.GameVersion;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.util.ResourceLeakDetector;
import java.time.Duration;
import net.minecraft.command.TranslatableExceptionProvider;
import net.minecraft.util.MinecraftVersion;

public class SharedConstants {
    public static final ResourceLeakDetector.Level NETTY_LEAK_DETECTION = ResourceLeakDetector.Level.DISABLED;
    public static final long field_240855_b_ = Duration.ofMillis(300L).toNanos();
    public static boolean useDatafixers = true;
    public static boolean developmentMode;
    public static final char[] ILLEGAL_FILE_CHARACTERS;
    private static GameVersion version;

    public static boolean isAllowedCharacter(char c) {
        return c != '\u00a7' && c >= ' ' && c != '\u007f';
    }

    public static String filterAllowedCharacters(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (!SharedConstants.isAllowedCharacter(c)) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static GameVersion getVersion() {
        if (version == null) {
            version = MinecraftVersion.load();
        }
        return version;
    }

    public static int func_244709_b() {
        return 1;
    }

    static {
        ILLEGAL_FILE_CHARACTERS = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};
        ResourceLeakDetector.setLevel(NETTY_LEAK_DETECTION);
        CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = false;
        CommandSyntaxException.BUILT_IN_EXCEPTIONS = new TranslatableExceptionProvider();
    }
}

