package mathax.client.systems;

import mathax.client.utils.files.StreamUtils;
import mathax.client.utils.misc.ISerializable;
import mathax.client.MatHax;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;

public abstract class System<T> implements ISerializable<T> {
    private final String name;
    private File file;

    public System(String name) {
        this.name = name;

        if (name != null) this.file = new File(MatHax.VERSION_FOLDER, name + ".nbt");
    }

    public void init() {}

    public void save(File folder) {
        File file = getFile();
        if (file == null) return;

        NbtCompound tag = toTag();
        if (tag == null) return;

        try {
            File tempFile = File.createTempFile("mathax", file.getName());
            NbtIo.write(tag, tempFile);

            if (folder != null) file = new File(folder, file.getName());

            file.getParentFile().mkdirs();
            StreamUtils.copy(tempFile, file);
            tempFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        save(null);
    }

    public void load(File folder) {
        File file = getFile();
        if (file == null) return;

        try {
            if (folder != null) file = new File(folder, file.getName());

            if (file.exists()) fromTag(NbtIo.read(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        load(null);
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    @Override
    public NbtCompound toTag() {
        return null;
    }

    @Override
    public T fromTag(NbtCompound tag) {
        return null;
    }
}
