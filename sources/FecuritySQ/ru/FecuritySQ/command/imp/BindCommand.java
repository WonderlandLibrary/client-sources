package ru.FecuritySQ.command.imp;



import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.command.Command;
import ru.FecuritySQ.command.CommandAbstract;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.utils.RenderUtil;


import java.util.ArrayList;
import java.util.Comparator;

@Command(name = "bind", description = "Позволяет забиндить модули")
public class BindCommand extends CommandAbstract {

    @Override
    public void execute(String[] args) {
        try {
            if (args.length >= 2) {
                if (args[0].equals("bind")) {
                    if (args[1].equals("clear")) {
                        int i = 0;
                        for (Module feature2 : FecuritySQ.get().getModuleList()) {
                            if (feature2 == null || feature2.getKey() == 0 || feature2.getName().equals("ClickGui"))
                                continue;
                            feature2.setKey(0);
                            i++;
                        }
                        sendMessage(TextFormatting.GREEN + "" + i + " модулей " + TextFormatting.WHITE + " разбинжено");
                        //NotificationRenderer.queue("Bind Manager", TextFormatting.GREEN + "All modules successfully" + TextFormatting.WHITE + " unbinded", 4, NotificationMode.SUCCESS);
                    }
                    if (args[1].equals("list")) {
                        ArrayList<Module> boxes = new ArrayList();

                        for (Module feature1 : FecuritySQ.get().getModuleList()) {
                            if (feature1.getKey() == GLFW.GLFW_KEY_0) continue;
                            boxes.add(feature1);
                        }
                        RenderUtil.addChatMessage(boxes.isEmpty() ? "Очистить" : "Все бинды :");

                        boxes.sort(Comparator.comparing(m -> m.getName()));
                        for (Module box : boxes) {
                            sendMessage(TextFormatting.GRAY + box.getName() + TextFormatting.WHITE + " [" + TextFormatting.GRAY + GLFW.glfwGetKeyName(box.getKey(), 0)
                                    + TextFormatting.WHITE + "]");
                        }
                    }

                    if (args[1].equals("add")) {
                        int keyBind = getKeyCodeFromKey(args[3].toUpperCase());

                        Module module = FecuritySQ.get().getModule(args[2]);
                        if (module != null) {
                            module.setKey(keyBind);
                            sendMessage("§fКлавиша " + TextFormatting.GRAY + args[3].toUpperCase() + TextFormatting.RESET + " была привязана к модулю " + TextFormatting.GRAY + module.getName());
                        } else {
                            sendMessage("Модуль " + TextFormatting.GRAY + args[2] + TextFormatting.RESET + " не был найден");
                        }
                    }
                    if (args[1].equals("remove")) {
                        for (Module f : FecuritySQ.get().getModuleList()) {
                            if (f.getName().equalsIgnoreCase(args[2])) {
                                f.setKey(0);
                                sendMessage("§fКлавиша " + TextFormatting.GRAY + args[3].toUpperCase() + TextFormatting.RESET + " была отвязана от модуля " + TextFormatting.GRAY + f.getName());
                            }
                        }
                    }
                }
            } else {
                error();
            }
        } catch (
                Exception e) {
            // empty catch block
        }

    }
    public int getKeyCodeFromKey(String key) {
        for(int i = 0; i < 255; i++) {
            if (key.equalsIgnoreCase(GLFW.glfwGetKeyName(i, i)))
                return i;
        }
        return 0;
    }
    @Override
    public void error() {
        sendMessage(TextFormatting.GRAY + "Ошибка в использовании" + TextFormatting.WHITE + ":");
        sendMessage(TextFormatting.WHITE + ".bind add " + TextFormatting.DARK_GRAY + "<name> <key>");
        sendMessage(TextFormatting.WHITE + ".bind remove " + TextFormatting.DARK_GRAY + "<name> <key>");
        sendMessage(TextFormatting.WHITE + ".bind list");
        sendMessage(TextFormatting.WHITE + ".bind clear");
    }
}