package net.minecraft.client.triton.management.enemies;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.triton.utils.FileUtils;
import net.minecraft.util.StringUtils;

public class EnemyManager
{
    private static final File ENEMY_DIR;
    public static ArrayList<Enemy> EnemysList;
    
    static {
        ENEMY_DIR = FileUtils.getConfigFile("Enemies");
        EnemyManager.EnemysList = new ArrayList<Enemy>();
    }
    
    public static void start() {
        load();
        save();
    }
    
    public static void addEnemy(final String name, final String alias) {
        EnemyManager.EnemysList.add(new Enemy(name, alias));
        save();
    }
    
    public static String getAliasName(final String name) {
        String alias = "";
        for (final Enemy Enemy : EnemyManager.EnemysList) {
            if (Enemy.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
                alias = Enemy.alias;
                break;
            }
        }
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            return name;
        }
        return alias;
    }
    
    public static void removeEnemy(final String name) {
        for (final Enemy Enemy : EnemyManager.EnemysList) {
            if (Enemy.name.equalsIgnoreCase(name)) {
                EnemyManager.EnemysList.remove(Enemy);
                break;
            }
        }
        save();
    }
    
    public static String replaceText(String text) {
        for (final Enemy Enemy : EnemyManager.EnemysList) {
            if (text.contains(Enemy.name)) {
                text = Enemy.alias;
            }
        }
        return text;
    }
    
    public static boolean isEnemy(final String name) {
        boolean isEnemy = false;
        for (final Enemy Enemy : EnemyManager.EnemysList) {
            if (Enemy.name.equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
                isEnemy = true;
                break;
            }
        }
        if (Minecraft.getMinecraft().thePlayer.getGameProfile().getName() == name) {
            isEnemy = true;
        }
        return isEnemy;
    }
    
    public static void load() {
        EnemyManager.EnemysList.clear();
        final List<String> fileContent = FileUtils.read(EnemyManager.ENEMY_DIR);
        for (final String line : fileContent) {
            try {
                final String[] split = line.split(":");
                final String name = split[0];
                final String alias = split[1];
                addEnemy(name, alias);
            }
            catch (Exception ex) {}
        }
    }
    
    public static void save() {
        final List<String> fileContent = new ArrayList<String>();
        for (final Enemy Enemy : EnemyManager.EnemysList) {
            final String alias = getAliasName(Enemy.name);
            fileContent.add(String.format("%s:%s", Enemy.name, alias));
        }
        FileUtils.write(EnemyManager.ENEMY_DIR, fileContent, true);
    }
}

