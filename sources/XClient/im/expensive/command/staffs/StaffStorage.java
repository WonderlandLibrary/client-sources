package im.expensive.command.staffs;

import im.expensive.utils.client.IMinecraft;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import static java.io.File.separator;

@UtilityClass
public class StaffStorage implements IMinecraft {

    @Getter
    private final Set<String> staffs = new HashSet<>();
    private final File file = new File(mc.gameDir + separator + "expensive" + separator + "staffs.cfg");

    @SneakyThrows
    public void load() {
        if (file.exists()) {
            staffs.addAll(Files.readAllLines(file.toPath()));
        } else {
            file.createNewFile();
        }
    }

    public void add(String name) {
        staffs.add(name);
        save();
    }

    public void remove(String name) {
        staffs.remove(name);
        save();
    }

    public void clear() {
        staffs.clear();
        save();
    }

    public boolean isStaff(String name) {
        return staffs.contains(name);
    }

    @SneakyThrows
    private void save() {
        Files.write(file.toPath(), staffs);
    }
}
