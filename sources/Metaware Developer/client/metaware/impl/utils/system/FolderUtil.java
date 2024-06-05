package client.metaware.impl.utils.system;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FolderUtil {

    public static boolean openFolder(File file) {
        String path = file.getAbsolutePath();
        try {
            switch (Util.getOSType()) {
                case OSX:
                    Runtime.getRuntime().exec(new String[] {"/usr/bin/open", path});
                    break;
                case WINDOWS:
                    Runtime.getRuntime().exec(String.format("cmd.exe /C start \"Open file\" \"%s\"", path));
                    break;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Couldn't open folder.\n" + e.getStackTrace());
        }
        return false;
    }

    public static List<String> chooseFileReturnLines(String windowName, String path) {
        List<String> lines = null;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFileChooser fileChooser = new JFileChooser(path);
            fileChooser.setDialogTitle(windowName);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int result = fileChooser.showOpenDialog(KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow());
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                lines = Files.readAllLines(selectedFile.toPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static List<File> files;
    public static List<File> chooseFiles(String windowName, String path, boolean multiple, int mode, String description, String... extensions) {
        files = new ArrayList<>();
        try {
            if(Minecraft.getMinecraft().isFullScreen()) Minecraft.getMinecraft().toggleFullscreen();
            SwingUtilities.invokeAndWait(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    JFileChooser fileChooser = new JFileChooser(path);
                    fileChooser.setDialogTitle(windowName);
                    fileChooser.setFileSelectionMode(mode);
                    fileChooser.setMultiSelectionEnabled(multiple);
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(description, extensions));
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        files = Arrays.asList(fileChooser.getSelectedFiles());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

}
