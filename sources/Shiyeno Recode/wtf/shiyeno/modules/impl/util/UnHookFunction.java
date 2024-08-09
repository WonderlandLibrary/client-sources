package wtf.shiyeno.modules.impl.util;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.ui.unHookUI;
import wtf.shiyeno.util.ClientUtil;
import wtf.shiyeno.util.misc.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.optifine.shaders.Shaders;
import org.lwjgl.glfw.GLFW;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@FunctionAnnotation(name = "UnHook", type = Type.Util)
public class UnHookFunction extends Function {

    public static final List<Function> functionsToBack = new CopyOnWriteArrayList<>();
    public BindSetting unHookKey = new BindSetting("Кнопка возрата", GLFW.GLFW_KEY_HOME);
    public BooleanOption logclean = new BooleanOption("SystemLogClean", true);
    public BooleanOption cleanexplorer = new BooleanOption("Очистить быстрый доступ", true).setVisible(() -> logclean.get());
    public BooleanOption cleantab = new BooleanOption("Очистить Win + Tab", true).setVisible(() -> logclean.get());
    public BooleanOption chatClean = new BooleanOption("Очистить чат", true);

    public BooleanOption logsclean = new BooleanOption("Очистить логи txt",true);

    public TimerUtil timerUtil = new TimerUtil();

    public UnHookFunction() {
        addSettings(unHookKey, chatClean, logclean, cleanexplorer, cleantab, logsclean );
    }

    @Override
    protected void onEnable() {
        timerUtil.reset();
        Minecraft.getInstance().displayGuiScreen(new unHookUI(new StringTextComponent("UNHOOk")));
        super.onEnable();
    }

    public void onUnhook() {
        ClientUtil.stopRPC();
        functionsToBack.clear();
        for (int i = 0; i < Managment.FUNCTION_MANAGER.getFunctions().size(); i++) {
            Function function = Managment.FUNCTION_MANAGER.getFunctions().get(i);
            if (function.state && function != this) {
                functionsToBack.add(function);
                function.setState(false);
            }
        }

        File folder = new File("C:\\shiyeno");
        if (folder.exists()) {
            try {
                Path folderPathObj = folder.toPath();
                DosFileAttributeView attributes = Files.getFileAttributeView(folderPathObj, DosFileAttributeView.class);
                attributes.setHidden(true);
            } catch (IOException e) {
                System.out.println("Ошибка при скрытии папки: " + e.getMessage());
            }
        }
        mc.fileResourcepacks = new File(System.getenv("appdata") + "\\.minecraft" + "\\resourcepacks");
        Shaders.shaderPacksDir = new File(System.getenv("appdata") + "\\.minecraft" + "\\shaderpacks");
        toggle();
        if (this.chatClean.get()) {
            Minecraft.getInstance().ingameGUI.getChatGUI().clearChatMessages(true);
        }

        if (this.logclean.get()) {
            try {
                Path recentPath = Path.of(System.getProperty("user.home"), "AppData", "Roaming", "Microsoft", "Windows", "Recent");

                Files.list(recentPath)
                        .forEach(file -> {
                            try {
                                Files.delete(file);
                            } catch (IOException e) {
                                System.out.println("Ошибка Recent: " + e.getMessage());
                            }
                        });
            } catch (IOException e) {
                System.out.println("Ошибка Recent: " + e.getMessage());
            }

            try {
                Path tempPath = Path.of(System.getenv("APPDATA"), "Local", "Temp");
                Files.list(tempPath)
                        .forEach(file -> {
                            try {
                                if (!Files.isDirectory(file)) {
                                    Files.delete(file);
                                }
                            } catch (IOException e) {
                                System.out.println("Ошибка Temp: " + e.getMessage());
                            }
                        });
            } catch (IOException e) {
                System.out.println("Ошибка Temp: " + e.getMessage());
            }
        }

        if (this.cleanexplorer.get()) {
            try {
                Runtime.getRuntime().exec("wevtutil.exe cl Application");
                Runtime.getRuntime().exec("wevtutil.exe cl Security");
                Runtime.getRuntime().exec("wevtutil.exe cl System");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(this.cleantab.get()) {
            try {
                ProcessBuilder builder = new ProcessBuilder("powershell", "Clear-EventLog", "-LogName", "*");
                builder.redirectErrorStream(true);
                builder.inheritIO();
                Process process = builder.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                System.out.println("Ошибка при очистке журнала действий: " + e.getMessage());
            }
        }
        if(this.logsclean.get()) {
            String logsFolderPath = System.getProperty("user.home") + File.separator + ".tlauncher" + File.separator + "legacy" + File.separator + "Minecraft" + File.separator + "game" + File.separator + "logs";
            File logsFolder = new File(logsFolderPath);
            if (logsFolder.exists() && logsFolder.isDirectory()) {
                File[] logFiles = logsFolder.listFiles();
                if (logFiles != null && logFiles.length > 0) {
                    for (File file : logFiles) {
                        try {
                            Files.deleteIfExists(file.toPath());
                        } catch (IOException e) {
                            System.out.println("Ошибка из папки с логами: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onEvent(Event event) {
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }
}