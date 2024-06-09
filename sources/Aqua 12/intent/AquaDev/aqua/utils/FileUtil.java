// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import java.util.List;
import java.util.ArrayList;
import intent.AquaDev.aqua.gui.novoline.components.CategoryPaneNovoline;
import intent.AquaDev.aqua.gui.novoline.ClickguiScreenNovoline;
import gui.clickgui.ownClickgui.ClickguiScreen;
import gui.clickgui.ownClickgui.components.CategoryPaneOwn;
import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import intent.AquaDev.aqua.modules.Module;
import java.nio.file.Path;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.client.Minecraft;
import java.io.File;

public class FileUtil
{
    public static final File DIRECTORY;
    public static final File PIC;
    public static final File CONFIGS;
    
    public void createFolder() {
        if (!FileUtil.DIRECTORY.exists() || !FileUtil.DIRECTORY.isDirectory()) {
            FileUtil.DIRECTORY.mkdirs();
            FileUtil.DIRECTORY.mkdir();
        }
    }
    
    public void createPicFolder() {
        if (!FileUtil.PIC.exists() || !FileUtil.PIC.isDirectory()) {
            FileUtil.PIC.mkdir();
            FileUtil.PIC.mkdirs();
        }
    }
    
    public void createConfigFolder() {
        if (!FileUtil.CONFIGS.exists() || !FileUtil.CONFIGS.isDirectory()) {
            System.out.println("Created Folder");
            FileUtil.CONFIGS.mkdir();
            FileUtil.CONFIGS.mkdirs();
        }
        else {
            System.out.println("Folder exist");
        }
    }
    
    public void loadKeys() {
        try {
            final Path path1 = Paths.get(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name, new String[0]);
            if (!Files.exists(path1, new LinkOption[0])) {
                try {
                    Files.createDirectory(path1, (FileAttribute<?>[])new FileAttribute[0]);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            final Path path2 = Paths.get(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/keys.txt", new String[0]);
            if (!Files.exists(path2, new LinkOption[0])) {
                try {
                    Files.createFile(path2, (FileAttribute<?>[])new FileAttribute[0]);
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/keys.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            else if (file.exists()) {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString;
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split(":");
                    final Module mod = Aqua.moduleManager.getModuleByName(split[0]);
                    if (mod != null) {
                        final int key = Integer.parseInt(split[1]);
                        mod.setKeyBind(key);
                    }
                }
            }
        }
        catch (Exception e3) {
            e3.printStackTrace();
        }
    }
    
    public void saveKeys() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/keys.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (final Module module : Aqua.moduleManager.modules) {
                final String moduleName = module.getName();
                final int moduleKey = module.getKeyBind();
                final String endstring = moduleName + ":" + moduleKey + "\n";
                writer.write(endstring);
            }
            writer.close();
        }
        catch (Exception ex) {}
    }
    
    public void saveModules() {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/modules.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (final Module module : Aqua.moduleManager.modules) {
                final String modName = module.getName();
                final String string = modName + ":" + module.toggeld + "\n";
                writer.write(string);
            }
            writer.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void loadModules() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/modules.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString;
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split(":");
                    final Module mod = Aqua.moduleManager.getModuleByName(split[0]);
                    final boolean enabled = Boolean.parseBoolean(split[1]);
                    if (mod != null) {
                        mod.setState(enabled);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadClickGuiOwn(final CategoryPaneOwn categoryPanel) {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString;
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split(":");
                    if (split.length == 3 && categoryPanel.getCategory().toString().equals(split[0])) {
                        categoryPanel.setX(Integer.parseInt(split[1]));
                        categoryPanel.setY(Integer.parseInt(split[2]));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadClickGuiJello(final gui.jello.components.CategoryPaneOwn categoryPanel) {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString;
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split(":");
                    if (split.length == 3 && categoryPanel.getCategory().toString().equals(split[0])) {
                        categoryPanel.setX(Integer.parseInt(split[1]));
                        categoryPanel.setY(Integer.parseInt(split[2]));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveClickGuiOwn(final ClickguiScreen clickGui) {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (final CategoryPaneOwn categoryPanel : clickGui.getCategoryPanes()) {
                final String string = categoryPanel.getCategory() + ":" + categoryPanel.getX() + ":" + categoryPanel.getY() + "\n";
                writer.write(string);
            }
            writer.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void saveClickGuiJello(final gui.jello.ClickguiScreen clickGui) {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (final gui.jello.components.CategoryPaneOwn categoryPanel : clickGui.getCategoryPanes()) {
                final String string = categoryPanel.getCategory() + ":" + categoryPanel.getX() + ":" + categoryPanel.getY() + "\n";
                writer.write(string);
            }
            writer.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void saveClickGui(final ClickguiScreenNovoline clickGui) {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (final CategoryPaneNovoline categoryPanel : clickGui.getCategoryPanes()) {
                final String string = categoryPanel.getCategory() + ":" + categoryPanel.getX() + ":" + categoryPanel.getY() + "\n";
                writer.write(string);
            }
            writer.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void saveClickGuiTenacity(final intent.AquaDev.aqua.gui.tenacity.ClickguiScreenNovoline clickGui) {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (final intent.AquaDev.aqua.gui.tenacity.components.CategoryPaneNovoline categoryPanel : clickGui.getCategoryPanes()) {
                final String string = categoryPanel.getCategory() + ":" + categoryPanel.getX() + ":" + categoryPanel.getY() + "\n";
                writer.write(string);
            }
            writer.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void saveClickGuiSuicide(final intent.AquaDev.aqua.gui.suicideX.ClickguiScreenNovoline clickGui) {
        final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (final intent.AquaDev.aqua.gui.suicideX.components.CategoryPaneNovoline categoryPanel : clickGui.getCategoryPanes()) {
                final String string = categoryPanel.getCategory() + ":" + categoryPanel.getX() + ":" + categoryPanel.getY() + "\n";
                writer.write(string);
            }
            writer.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public void loadClickGui(final CategoryPaneNovoline categoryPanel) {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString;
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split(":");
                    if (split.length == 3 && categoryPanel.getCategory().toString().equals(split[0])) {
                        categoryPanel.setX(Integer.parseInt(split[1]));
                        categoryPanel.setY(Integer.parseInt(split[2]));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadClickGuiTenacity(final intent.AquaDev.aqua.gui.tenacity.components.CategoryPaneNovoline categoryPanel) {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString;
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split(":");
                    if (split.length == 3 && categoryPanel.getCategory().toString().equals(split[0])) {
                        categoryPanel.setX(Integer.parseInt(split[1]));
                        categoryPanel.setY(Integer.parseInt(split[2]));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadClickGuiSuicde(final intent.AquaDev.aqua.gui.suicideX.components.CategoryPaneNovoline categoryPanel) {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/gui.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            else {
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString;
                while ((readString = bufferedReader.readLine()) != null) {
                    final String[] split = readString.split(":");
                    if (split.length == 3 && categoryPanel.getCategory().toString().equals(split[0])) {
                        categoryPanel.setX(Integer.parseInt(split[1]));
                        categoryPanel.setY(Integer.parseInt(split[2]));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<String> readFile(final File file) {
        try {
            if (!file.exists()) {
                return null;
            }
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final ArrayList<String> strings = new ArrayList<String>();
            String curr;
            while ((curr = reader.readLine()) != null) {
                strings.add(curr);
            }
            reader.close();
            return strings;
        }
        catch (Exception ignored) {
            return null;
        }
    }
    
    public static boolean writeFile(final File file, final List<String> lines) {
        try {
            if (!file.exists()) {
                return false;
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (final String line : lines) {
                writer.write(line.endsWith("\n") ? line : (line + "\n"));
            }
            writer.close();
            writer.flush();
            return true;
        }
        catch (Exception ignored) {
            return false;
        }
    }
    
    static {
        DIRECTORY = new File(Minecraft.getMinecraft().mcDataDir, Aqua.name);
        PIC = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/pic");
        CONFIGS = new File(Minecraft.getMinecraft().mcDataDir + "/" + Aqua.name + "/configs");
    }
}
