package im.expensive.functions.impl.misc;

import im.expensive.Furious;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.StringSetting;
import im.expensive.utils.math.StopWatch;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.optifine.shaders.Shaders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.ArrayList;
import java.util.List;

@FunctionRegister(name = "Panic", type = Category.Miscellaneous)
public class SelfDestruct extends Function {
    private final StringSetting code = new StringSetting("Код", "Furious", "Код");
    public boolean unhooked = false;
    public String secret = code.get();
    public StopWatch stopWatch = new StopWatch();

    public SelfDestruct() {
        addSettings(code);
    }
    @Override
    public boolean onEnable() {
        secret = code.get();
        super.onEnable();
        process();
        stopWatch.reset();

        new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            toggle();
        }).start();


        unhooked = true;

        return false;
    }

    public List<Function> saved = new ArrayList<>();

    public void process() {
        for (Function function : Furious.getInstance().getFunctionRegistry().getFunctions()) {
            if (function == this) continue;
            if (function.isState()) {
                saved.add(function);
                function.setState(false, false);
            }
        }
        mc.fileResourcepacks = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\resourcepacks");
        Shaders.shaderPacksDir = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\shaderpacks");

        File folder = new File("C:\\Furious");
        hiddenFolder(folder, true);
    }

    public void hook() {
        for (Function function : saved) {
            if (function == this) continue;
            if (!function.isState()) {
                function.setState(true, false);
            }
        }
        File folder = new File("C:\\Furious");
        hiddenFolder(folder, false);
        mc.fileResourcepacks = GameConfiguration.instance.folderInfo.resourcePacksDir;
        Shaders.shaderPacksDir = new File(Minecraft.getInstance().gameDir, "shaderpacks");
        unhooked = false;
    }

    private void hiddenFolder(File folder, boolean hide) {
        if (folder.exists()) {
            try {
                Path folderPathObj = folder.toPath();
                DosFileAttributeView attributes = Files.getFileAttributeView(folderPathObj, DosFileAttributeView.class);
                attributes.setHidden(false);
            } catch (IOException e) {
                System.out.println("Не удалось скрыть папку: " + e.getMessage());
            }
        }
    }
}
