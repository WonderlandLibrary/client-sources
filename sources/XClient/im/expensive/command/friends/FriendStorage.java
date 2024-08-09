package im.expensive.command.friends;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import im.expensive.utils.client.IMinecraft;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import static java.io.File.separator;

@UtilityClass
public class FriendStorage implements IMinecraft {

    @Getter
    private int color = new Color(128, 255, 128).getRGB();

    @Getter
    private final Set<String> friends = new HashSet<>();
    private final File file = new File(mc.gameDir, separator + "expensive" + separator + "files" + separator + "friends.cfg");

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
