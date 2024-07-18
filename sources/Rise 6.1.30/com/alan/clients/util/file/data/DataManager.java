package com.alan.clients.util.file.data;

import com.alan.clients.Client;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.util.file.FileManager;
import com.alan.clients.util.file.FileType;

import java.io.File;
import java.util.ArrayList;
public class DataManager extends ArrayList<DataFile> {

    public static final File DATA_DIRECTORY = new File(FileManager.DIRECTORY, "data");

    public void init() {
        Client.INSTANCE.setClickGUI(new RiseClickGUI());
//        long now = System.currentTimeMillis();
//
//        try {
//            Socket socket = new Socket();
//            long time = System.currentTimeMillis();
//            socket.connect(new InetSocketAddress("199.247.6.233", 443));
//            socket.close();
//
//            if (System.currentTimeMillis() - time != 0) {
//                Client.INSTANCE.setClickGUI(new RiseClickGUI());
//            }
//        } catch (Exception ignored) {
//            for (; ; ) {
//                Client.INSTANCE.getModuleManager().get("Test");
//            }
//        }
//
//        if (now == System.currentTimeMillis()) {
//            while (true) ;
//        }

        if (!DATA_DIRECTORY.exists()) {
            DATA_DIRECTORY.mkdir();
        }

        this.update();
    }

    public DataFile get(final String config, final boolean allowKey) {
        final File file = new File(DataManager.DATA_DIRECTORY, config + ".json");

        final DataFile dataFile = new DataFile(file, FileType.CONFIG);
        if (allowKey) dataFile.allowKeyCodeLoading();

        return dataFile;
    }

    public DataFile get(final String config) {
        final File file = new File(DataManager.DATA_DIRECTORY, config + ".json");

        final DataFile dataFile = new DataFile(file, FileType.CONFIG);
        dataFile.allowKeyCodeLoading();

        return dataFile;
    }

    public void set(final String config) {
        final File file = new File(DATA_DIRECTORY, config + ".json");
        DataFile dataFile = get(config);

        if (dataFile == null) {
            dataFile = new DataFile(file, FileType.CONFIG);
            add(dataFile);

            System.out.println("Creating new config...");
        } else {
            System.out.println("Overwriting existing config...");
        }

        dataFile.write();

        System.out.println("Config saved to files.");
    }

    public boolean update() {
        clear();

        final File[] files = DATA_DIRECTORY.listFiles();

        if (files == null)
            return false;

        for (final File file : files) {
            if (file.getName().endsWith(".json")) {
                add(new DataFile(file, FileType.CONFIG));
            }
        }

        return true;
    }

    public boolean delete(final String config) {
        final DataFile dataFile = get(config);

        if (dataFile == null)
            return false;

        remove(dataFile);
        return dataFile.getFile().delete();
    }
}