package im.expensive.functions.impl.misc;

import im.expensive.Expensive;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.utils.math.StopWatch;
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

@FunctionRegister(name = "SelfDestruct", type = Category.PLAYER)
public class SelfDestruct extends Function {

    public boolean unhooked = false;
    public String secret = RandomStringUtils.randomAlphabetic(2);
    public StopWatch stopWatch = new StopWatch();

    @Override
    public void onEnable() {
        super.onEnable();
        process();
        print("Что бы вернуть чит напишите в чат " + TextFormatting.RED + secret);
        print("Все сообщения удалятся через 10 секунд");
        stopWatch.reset();

        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            mc.ingameGUI.getChatGUI().clearChatMessages(false);
            toggle();
        }).start();


        unhooked = true;

    }

    public List<Function> saved = new ArrayList<>();

    public void process() {
        for (Function function : Expensive.getInstance().getFunctionRegistry().getFunctions()) {
            if (function == this) continue;
            if (function.isState()) {
                saved.add(function);
                function.setState(false, false);
            }
        }
        mc.fileResourcepacks = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\resourcepacks");
        Shaders.shaderPacksDir = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\shaderpacks");

        File folder = new File("C:\\Expensive");
        hiddenFolder(folder, true);
    }

    public void hook() {
        for (Function function : saved) {
            if (function == this) continue;
            if (!function.isState()) {
                function.setState(true, false);
            }
        }
        File folder = new File("C:\\Expensive");
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
