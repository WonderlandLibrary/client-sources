package dev.excellent.client.staff;

import dev.excellent.Excellent;
import dev.excellent.impl.util.file.FileManager;
import i.gishreloaded.protection.annotation.Native;

import java.io.File;
import java.util.ArrayList;

public class StaffManager extends ArrayList<String> {
    public static File STAFF_DIRECTORY;

    @Native
    public void init() {
        STAFF_DIRECTORY = new File(FileManager.DIRECTORY, "staff");
        if (!STAFF_DIRECTORY.exists()) {
            if (STAFF_DIRECTORY.mkdir()) {
                System.out.println("Папка с списком стаффов успешно создана.");
            } else {
                System.out.println("Произошла ошибка при создании папки с списком стаффов.");
            }
        }

        Excellent.getInst().getEventBus().register(this);

    }

    public StaffFile get() {
        final File file = new File(STAFF_DIRECTORY, "staffs." + Excellent.getInst().getInfo().getNamespace());
        return new StaffFile(file);
    }

    public void set() {
        final File file = new File(STAFF_DIRECTORY, "staffs." + Excellent.getInst().getInfo().getNamespace());
        StaffFile StaffFile = get();
        if (StaffFile == null) {
            StaffFile = new StaffFile(file);
        }
        StaffFile.write();
    }


    public void addStaff(String name) {
        this.add(name);
        set();
    }

    public boolean isStaff(String name) {
        return this.contains(name);
    }

    public void removeStaff(String name) {
        this.removeIf(x -> x.equalsIgnoreCase(name));
        set();
    }

    public void clearStaffs() {
        this.clear();
        set();
    }
}
