package Hydro.config;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Hydro.util.Container;
import net.minecraft.client.Minecraft;

public class FileFactory extends Container<IFile> {

    private final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    private File root;

    @Override
    public void add(IFile item) {
        item.setFile(root);
        super.add(item);
    }

    public void saveFile(Class<? extends IFile> iFile) {
        IFile file = findByClass(iFile);
        if (file != null) {
            file.save(GSON);
        }
    }

    public void loadFile(Class<? extends IFile> iFile) {
        IFile file = findByClass(iFile);
        if (file != null) {
            file.load(GSON);
        }
    }

    public void save() {
    	System.out.println("Saving Modules");
        forEach(file -> file.save(GSON));
    }

    public void load() {
    	System.out.println("Loading Modules");
        forEach(file -> file.load(GSON));
    }

    public void setupRoot() {
        // make the root directory
        root = new File(Minecraft.getMinecraft().mcDataDir, "Hydro");

        System.out.println("Minecraft dir: " + Minecraft.getMinecraft().mcDataDir);
        
        // check if it exists if not make it
        if (!root.exists()) {
            if (!root.mkdirs()) {
                Minecraft.logger.warn("Failed to create the root folder \"" + root.getPath() + "\".");
            }
        }
    }
}