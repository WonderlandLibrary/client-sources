package com.alan.clients.util.file;

import com.alan.clients.Client;
import com.alan.clients.util.Accessor;

import java.io.File;
public class FileManager {

    public static final File DIRECTORY = new File(Accessor.mc.mcDataDir, Client.NAME);

    public void init() {
        if (!DIRECTORY.exists()) {
            DIRECTORY.mkdir();
        }
    }
}