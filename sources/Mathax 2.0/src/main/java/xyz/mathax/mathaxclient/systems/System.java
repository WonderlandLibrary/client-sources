package xyz.mathax.mathaxclient.systems;

import xyz.mathax.mathaxclient.utils.files.StreamUtils;
import xyz.mathax.mathaxclient.utils.misc.ISerializable;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public abstract class System<T> implements ISerializable<T> {
    private final String name;

    private File file;

    protected boolean isFirstInit;

    public System(String name, File folder) {
        this.name = name;

        if (folder != null) {
            this.file = new File(folder, name + ".json");
            this.isFirstInit = !file.exists();
        }
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public void init() {}

    public void save(File folder) {
        File file = getFile();
        if (file == null) {
            return;
        }

        JSONObject json = toJson();
        if (json == null) {
            return;
        }

        try {
            File tempFile = File.createTempFile("Temp", file.getName());
            FileUtils.write(tempFile, json.toString(4));

            if (folder != null) {
                file = new File(folder, file.getName());
            }

            file.getParentFile().mkdirs();
            StreamUtils.copy(tempFile, file);
            tempFile.delete();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        save(null);
    }

    public void load(File folder) {
        File file = getFile();
        if (file == null) {
            return;
        }

        try {
            if (folder != null) {
                file = new File(folder, file.getName());
            }

            if (file.exists()) {
                String fileString = FileUtils.readFileToString(file);
                if (fileString.startsWith("{") && fileString.endsWith("}")) {
                    JSONObject json = new JSONObject(fileString);
                    fromJson(json);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void load() {
        load(null);
    }

    public JSONObject toJson() {
        return null;
    }

    public T fromJson(JSONObject json) {
        return null;
    }
}
