package wtf.resolute.manage.friends;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import wtf.resolute.utiled.client.IMinecraft;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class FriendStorage implements IMinecraft {

    @Getter
    private int color = new Color(128, 255, 128).getRGB();

    @Getter
    private final Set<String> friends = new HashSet<>();
    private final File file = new File(Minecraft.getInstance().gameDir, "\\resolute\\friends.cfg");

    @SneakyThrows
    public void load() {
        if (file.exists()) {
            friends.addAll(Files.readAllLines(file.toPath()));
        } else {
            file.createNewFile();
        }
    }

    public void add(String name) {
        friends.add(name);
        save();
    }

    public void remove(String name) {
        friends.remove(name);
        save();
    }

    public void clear() {
        friends.clear();
        save();
    }

    public boolean isFriend(String name) {
        return friends.contains(name);
    }

    @SneakyThrows
    private void save() {
        Files.write(file.toPath(), friends);
    }
}
