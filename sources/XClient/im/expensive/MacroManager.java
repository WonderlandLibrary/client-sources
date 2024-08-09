package im.expensive;

import im.expensive.utils.client.IMinecraft;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MacroManager implements IMinecraft {
    public List<Macro> macroList = new ArrayList<>();
    public File macroFile = new File(mc.gameDir, "\\expensive\\files\\macro.cfg");


    public void init() throws IOException {
        if (!macroFile.exists()) {
            macroFile.createNewFile();
        } else {
            readFile();
        }
    }

    public boolean isEmpty() {
        return macroList.isEmpty();
    }

    public void addMacro(String name, String message, int key) {
        macroList.add(new Macro(name, message, key));
        writeFile();
    }

    public boolean hasMacro(String macroName) {
        for (Macro macro : macroList) {
            if (macro.getName().equalsIgnoreCase(macroName)) {
                return true;
            }
        }
        return false;
    }

    public void deleteMacro(String name) {
        if (macroList.stream()
                .anyMatch(macro -> macro.getName().equals(name))) {
            macroList.removeIf(macro -> macro.getName().equalsIgnoreCase(name));
            writeFile();
        }
    }

    public void clearList() {
        if (!macroList.isEmpty()) {
            macroList.clear();
        }
        writeFile();
    }

    public void onKeyPressed(int key) {
        if (mc.player == null) {
            return;
        }

        macroList.stream()
                .filter(macro -> macro.getKey() == key)
                .findFirst()
                .ifPresent(macro -> {
                    try {
                        mc.player.sendChatMessage(macro.getMessage());
                    } catch (Exception e) {
                        print("Ошибка при отправки команды " + e);
                    }
                });
    }

    @SneakyThrows
    public void writeFile() {
        StringBuilder builder = new StringBuilder();
        macroList.forEach(macro -> builder.append(macro.getName())
                .append(":").append(macro.getMessage())
                .append(":").append(String.valueOf(macro.getKey()).toUpperCase())
                .append("\n"));
        Files.write(macroFile.toPath(), builder.toString().getBytes());
    }

    @SneakyThrows
    private void readFile() {
        FileInputStream fileInputStream = new FileInputStream(macroFile.getAbsolutePath());
        @Cleanup
        BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
        String line;
        while ((line = reader.readLine()) != null) {
            String curLine = line.trim();
            String name = curLine.split(":")[0];
            String command = curLine.split(":")[1];
            String key = curLine.split(":")[2];
            macroList.add(new Macro(name, command, Integer.parseInt(key)));
        }
    }

    @Value
    public static class Macro {
        String name;
        String message;
        int key;
    }
}
