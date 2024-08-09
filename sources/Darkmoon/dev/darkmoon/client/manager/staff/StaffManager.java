package dev.darkmoon.client.manager.staff;

import lombok.Getter;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class StaffManager {
    @Getter
    public final List<String> staff = new ArrayList<>();
    private static final File staffFile = new File(System.getenv("SystemDrive") + "\\DarkMoon\\staff.dm");

    public void init() throws IOException {
        if (!staffFile.exists()) {
            staffFile.createNewFile();
        } else {
            readStaff();
        }
    }

    public void addStaff(String name) {
        staff.add(name);
        updateFile();
    }

    public boolean isStaff(String name) {
        return staff.stream().anyMatch(isStaff -> isStaff.equals(name));
    }

    public void removeStaff(String name) {
        staff.removeIf(staff1 -> staff1.equalsIgnoreCase(name));
    }

    public void clearStaff() {
        staff.clear();
        updateFile();
    }

    public void updateFile() {
        try {
            StringBuilder builder = new StringBuilder();
            staff.forEach(staff1 -> builder.append(staff1).append("\n"));
            Files.write(staffFile.toPath(), builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readStaff() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(staffFile.getAbsolutePath()))));
            String line;
            while ((line = reader.readLine()) != null) {
                staff.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
