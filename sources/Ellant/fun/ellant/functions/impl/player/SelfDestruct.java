package fun.ellant.functions.impl.player;

import fun.ellant.Ellant;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.utils.math.StopWatch;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.optifine.shaders.Shaders;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.ArrayList;
import java.util.List;

@FunctionRegister(name = "SelfDestruct", type = Category.PLAYER, desc = "Пон?")
public class SelfDestruct extends Function {

    public boolean unhooked = false;
    public String secret = RandomStringUtils.randomAlphabetic(2);
    public StopWatch stopWatch = new StopWatch();

    @Override
    public boolean onEnable() {
        super.onEnable();
        process();
        print("Что бы вернуть чит напишите в чат " + TextFormatting.DARK_RED + secret);
        print("Все сообщения удалятся через 5 секунд");
        stopWatch.reset();

        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            mc.ingameGUI.getChatGUI().clearChatMessages(false);
            toggle();
        }).start();

        unhooked = true;

        return false;
    }

    public List<Function> saved = new ArrayList<>();

    public void process() {
        for (Function function : Ellant.getInstance().getFunctionRegistry().getFunctions()) {
            if (function == this) continue;
            if (function.isState()) {
                saved.add(function);
                function.setState(false, false);
            }
        }
        mc.fileResourcepacks = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\resourcepacks");
        Shaders.shaderPacksDir = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\shaderpacks");

        File folder = new File("C://Ellant//screenshots");
        hiddenFolder(folder, true);

        File File = new File("C://Ellant//EllantStarter.jar");
        hideFile(File, true);

        File folder2 = new File("C://Ellant//ellant");
        hiddenFolder(folder2, true);

        File folder3 = new File("C://Ellant//natives");
        hiddenFolder(folder3, true);

        File folder4 = new File("C://Ellant//assets");
        hiddenFolder(folder4, true);

        File folder5 = new File("C://Ellant//jdk");
        hiddenFolder(folder5, true);

        File folder6 = new File("C://Ellant//files");
        hiddenFolder(folder6, true);

        File folder7 = new File("C://Ellant");
        hiddenFolder(folder7, true);
    }

    public void hook() {
        for (Function function : saved) {
            if (function == this) continue;
            if (!function.isState()) {
                function.setState(true, false);
            }
        }

        mc.fileResourcepacks = GameConfiguration.instance.folderInfo.resourcePacksDir;
        Shaders.shaderPacksDir = new File(Minecraft.getInstance().gameDir, "Ellant");
        unhooked = false;
    }

    private void hiddenFolder(File folder, boolean hide) {
        if (folder.exists()) {
            try {
                Path folderPathObj = folder.toPath();
                DosFileAttributeView attributes = Files.getFileAttributeView(folderPathObj, DosFileAttributeView.class);
                attributes.setHidden(hide);
            } catch (IOException e) {
                System.out.println("Не удалось скрыть папку: " + e.getMessage());
            }
        }
    }

    private void hideFile(File file, boolean hide) {
        if (file.exists()) {
            try {
                Path filePathObj = file.toPath();
                DosFileAttributeView attributes = Files.getFileAttributeView(filePathObj, DosFileAttributeView.class);
                attributes.setHidden(hide);
            } catch (IOException e) {
                System.out.println("Не удалось скрыть файл: " + e.getMessage());
            }
        }
    }
}
