package wtf.resolute.moduled.impl.misc;

import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.utiled.math.StopWatch;
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

@ModuleAnontion(name = "PANIC", type = Categories.Misc,server = "")
public class PANIC extends Module {

    private final BooleanSetting saveEnchanted = new BooleanSetting("Удалять папку безвозвратно", false);
    public boolean unhooked = false;
    public String secret = RandomStringUtils.randomAlphabetic(3);
    public StopWatch stopWatch = new StopWatch();

    @Override
    public void onEnable() {
        super.onEnable();
        process();
        print("Что бы вернуть чит напишите в чат " + TextFormatting.RED + secret);
        print("Все сообщения удалятся через 10 секунд");
        print("Все Папки  чита удалятся через 10 секунд");
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

    public List<Module> saved = new ArrayList<>();

    public void process() {
        for (Module function : ResoluteInfo.getInstance().getFunctionRegistry().getFunctions()) {
            if (function == this) continue;
            if (function.isState()) {
                saved.add(function);
                function.setState(false, false);
            }
        }
        mc.fileResourcepacks = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\resourcepacks");
        Shaders.shaderPacksDir = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\shaderpacks");
        String folderPath = System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\resolute";
        File folder2 = new File(folderPath);
        deleteFolder(folder2);
        String folderPath2 = System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game\\versions" + "\\Resolute 1.16.5";
        File folder3 = new File(folderPath2);
        deleteFolder(folder3);
        File folder = new File("C:\\resolute");
        deleteFolder(folder);
        File folder4 = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game\\" + "\\logs");
        deleteFolder(folder4);
    }

    public void hook() {
        for (Module function : saved) {
            if (function == this) continue;
            if (!function.isState()) {
                function.setState(true, false);
            }
        }
        File folder = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game" + "\\resolute");
        hiddenFolder(folder, false);
        File folder2 = new File(System.getenv("appdata") + "\\.tlauncher\\legacy\\Minecraft\\game\\versions" + "\\Resolute 1.16.5");
        hiddenFolder(folder2, false);
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
    public static boolean deleteFolder(File foldered) {
        if (foldered.isDirectory()) {
            File[] files = foldered.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFolder(file);
                }
            }
        }
        return foldered.delete();
    }
}
