package dev.excellent.impl.util.file;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.game.IMinecraft;

import java.io.File;

public class FileManager implements IMinecraft {

    public static File DIRECTORY;

    public void init() {
        DIRECTORY = new File(mc.gameDir, Excellent.getInst().getInfo().getNamespace());
        if (!DIRECTORY.exists()) {
            if (DIRECTORY.mkdir()) {
                System.out.println("Папка клиента успешно создана.");
            } else {
                System.out.println("Произошла ошибка при создании папки клиента");
            }
        }

    }
}