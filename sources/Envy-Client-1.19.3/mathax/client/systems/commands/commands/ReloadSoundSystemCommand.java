package mathax.client.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.command.CommandSource;

import java.lang.reflect.Field;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ReloadSoundSystemCommand extends Command {
    public ReloadSoundSystemCommand() {
        super("reload-sound-system", "Reloads Minecraft's sound system.", "rss", "rmss", "rmcss");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            final SoundSystem soundSystem = getPrivateValue(SoundManager.class, mc.getSoundManager(), "soundSystem", "soundSystem");
            soundSystem.reloadSounds();
            info("Reloaded Minecraft sound system.");
            return SINGLE_SUCCESS;
        });
    }

    public static Field getField(final Class clazz, final String... names) {
        Field field = null;
        for (final String name : names) {
            if (field != null) break;
            try {
                field = clazz.getDeclaredField(name);
            } catch (NoSuchFieldException ignored) {}
        }

        return field;
    }

    public static <T> T getPrivateValue(final Class clazz, final Object object, final String... names) {
        final Field field = getField(clazz, names);
        field.setAccessible(true);
        try {
            return (T)field.get(object);
        } catch (IllegalAccessException ex) {
            return null;
        }
    }
}
