package alphentus.file;

import alphentus.file.files.Component;
import alphentus.file.files.create.*;
import alphentus.file.files.load.*;
import alphentus.init.Init;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 07/08/2020.
 */
public class FileSystem {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final Init init = Init.getInstance();

    private final File fileDirectory = new File(mc.mcDataDir, init.CLIENT_NAME);

    private final ArrayList<Component> components = new ArrayList<>();

    public FileSystem() {

        components.add(new ModulesToggleCreate());
        components.add(new ModulesToggleLoad());

        components.add(new ModuleVisibleCreate());
        components.add(new ModuleVisibleLoad());

        components.add(new ModuleBindsCreate());
        components.add(new ModuleBindsLoad());

        components.add(new SettingCreate());
        components.add(new SettingLoad());

        components.add(new SettingsCustomHUDCreate());
        components.add(new SettingsLoadCustomHUD());

        components.add(new AltsLoad());
        components.add(new AltsCreate());


        try {
            if (!fileDirectory.exists())
                fileDirectory.mkdirs();
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void load() {
        for (Component component : this.components) {
            component.load();
        }
    }

    public void create() {
        for (Component component : this.components) {
            component.create();
        }
    }
}