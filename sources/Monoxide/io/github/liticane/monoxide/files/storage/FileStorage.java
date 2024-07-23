package io.github.liticane.monoxide.files.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.florianmichael.rclasses.storage.Storage;
import io.github.liticane.monoxide.files.LocalFile;
import io.github.liticane.monoxide.files.impl.HUDFile;
import io.github.liticane.monoxide.files.impl.ModulesFile;
import io.github.liticane.monoxide.util.interfaces.Methods;

import java.io.File;

public class FileStorage extends Storage<LocalFile> implements Methods {

    private final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private File root;

    private static FileStorage instance;

    @Override
    public void init() {
        this.setupRoot(CLIENT_NAME);
        this.add(new ModulesFile());
        this.add(new HUDFile());
        this.load();
    }

    public void add(LocalFile item) {
        item.setFile(root);
        super.add(item);
    }

    public void saveFile(Class<? extends LocalFile> iFile) {
        LocalFile file = getByClass(iFile);
        if (file != null) {
            file.save(GSON);
        }
    }

    public void loadFile(Class<? extends LocalFile> iFile) {
        LocalFile file = getByClass(iFile);
        if (file != null) {
            file.load(GSON);
        }
    }

    public void save() {
        this.getList().forEach(file -> file.save(GSON));
    }

    public void load() {
        this.getList().forEach(file -> file.load(GSON));
    }

    public void setupRoot(String name) {
        // make the root directory
        root = new File(mc.mcDataDir, name);

        // check if it exists if not make it
        if (!root.exists()) {
            if (!root.mkdirs()) {
                mc.logger.warn("Failed to create the root folder \"" + root.getPath() + "\".");
            }
        }
    }

    public static FileStorage getInstance() {
        return instance;
    }

    public static void setInstance(FileStorage instance) {
        FileStorage.instance = instance;
    }
}
