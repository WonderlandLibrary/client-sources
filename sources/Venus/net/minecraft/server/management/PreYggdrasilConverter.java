/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.management.BanEntry;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.server.management.IPBanList;
import net.minecraft.server.management.OpEntry;
import net.minecraft.server.management.OpList;
import net.minecraft.server.management.PlayerList;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.server.management.WhiteList;
import net.minecraft.server.management.WhitelistEntry;
import net.minecraft.util.StringUtils;
import net.minecraft.world.storage.FolderName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PreYggdrasilConverter {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final File OLD_IPBAN_FILE = new File("banned-ips.txt");
    public static final File OLD_PLAYERBAN_FILE = new File("banned-players.txt");
    public static final File OLD_OPS_FILE = new File("ops.txt");
    public static final File OLD_WHITELIST_FILE = new File("white-list.txt");

    static List<String> readFile(File file, Map<String, String[]> map) throws IOException {
        List<String> list = Files.readLines(file, StandardCharsets.UTF_8);
        for (String string : list) {
            if ((string = string.trim()).startsWith("#") || string.length() < 1) continue;
            String[] stringArray = string.split("\\|");
            map.put(stringArray[0].toLowerCase(Locale.ROOT), stringArray);
        }
        return list;
    }

    private static void lookupNames(MinecraftServer minecraftServer, Collection<String> collection, ProfileLookupCallback profileLookupCallback) {
        String[] stringArray = (String[])collection.stream().filter(PreYggdrasilConverter::lambda$lookupNames$0).toArray(PreYggdrasilConverter::lambda$lookupNames$1);
        if (minecraftServer.isServerInOnlineMode()) {
            minecraftServer.getGameProfileRepository().findProfilesByNames(stringArray, Agent.MINECRAFT, profileLookupCallback);
        } else {
            for (String string : stringArray) {
                UUID uUID = PlayerEntity.getUUID(new GameProfile(null, string));
                GameProfile gameProfile = new GameProfile(uUID, string);
                profileLookupCallback.onProfileLookupSucceeded(gameProfile);
            }
        }
    }

    public static boolean convertUserBanlist(MinecraftServer minecraftServer) {
        BanList banList = new BanList(PlayerList.FILE_PLAYERBANS);
        if (OLD_PLAYERBAN_FILE.exists() && OLD_PLAYERBAN_FILE.isFile()) {
            if (banList.getSaveFile().exists()) {
                try {
                    banList.readSavedFile();
                } catch (IOException iOException) {
                    LOGGER.warn("Could not load existing file {}", (Object)banList.getSaveFile().getName(), (Object)iOException);
                }
            }
            try {
                HashMap<String, String[]> hashMap = Maps.newHashMap();
                PreYggdrasilConverter.readFile(OLD_PLAYERBAN_FILE, hashMap);
                ProfileLookupCallback profileLookupCallback = new ProfileLookupCallback(){
                    final MinecraftServer val$server;
                    final Map val$map;
                    final BanList val$banlist;
                    {
                        this.val$server = minecraftServer;
                        this.val$map = map;
                        this.val$banlist = banList;
                    }

                    @Override
                    public void onProfileLookupSucceeded(GameProfile gameProfile) {
                        this.val$server.getPlayerProfileCache().addEntry(gameProfile);
                        String[] stringArray = (String[])this.val$map.get(gameProfile.getName().toLowerCase(Locale.ROOT));
                        if (stringArray == null) {
                            LOGGER.warn("Could not convert user banlist entry for {}", (Object)gameProfile.getName());
                            throw new ConversionError("Profile not in the conversionlist");
                        }
                        Date date = stringArray.length > 1 ? PreYggdrasilConverter.parseDate(stringArray[0], null) : null;
                        String string = stringArray.length > 2 ? stringArray[5] : null;
                        Date date2 = stringArray.length > 3 ? PreYggdrasilConverter.parseDate(stringArray[5], null) : null;
                        String string2 = stringArray.length > 4 ? stringArray[5] : null;
                        this.val$banlist.addEntry(new ProfileBanEntry(gameProfile, date, string, date2, string2));
                    }

                    @Override
                    public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
                        LOGGER.warn("Could not lookup user banlist entry for {}", (Object)gameProfile.getName(), (Object)exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new ConversionError("Could not request user " + gameProfile.getName() + " from backend systems", exception);
                        }
                    }
                };
                PreYggdrasilConverter.lookupNames(minecraftServer, hashMap.keySet(), profileLookupCallback);
                banList.writeChanges();
                PreYggdrasilConverter.backupConverted(OLD_PLAYERBAN_FILE);
                return true;
            } catch (IOException iOException) {
                LOGGER.warn("Could not read old user banlist to convert it!", (Throwable)iOException);
                return true;
            } catch (ConversionError conversionError) {
                LOGGER.error("Conversion failed, please try again later", (Throwable)conversionError);
                return true;
            }
        }
        return false;
    }

    public static boolean convertIpBanlist(MinecraftServer minecraftServer) {
        IPBanList iPBanList = new IPBanList(PlayerList.FILE_IPBANS);
        if (OLD_IPBAN_FILE.exists() && OLD_IPBAN_FILE.isFile()) {
            if (iPBanList.getSaveFile().exists()) {
                try {
                    iPBanList.readSavedFile();
                } catch (IOException iOException) {
                    LOGGER.warn("Could not load existing file {}", (Object)iPBanList.getSaveFile().getName(), (Object)iOException);
                }
            }
            try {
                HashMap<String, String[]> hashMap = Maps.newHashMap();
                PreYggdrasilConverter.readFile(OLD_IPBAN_FILE, hashMap);
                for (String string : hashMap.keySet()) {
                    String[] stringArray = (String[])hashMap.get(string);
                    Date date = stringArray.length > 1 ? PreYggdrasilConverter.parseDate(stringArray[5], null) : null;
                    String string2 = stringArray.length > 2 ? stringArray[5] : null;
                    Date date2 = stringArray.length > 3 ? PreYggdrasilConverter.parseDate(stringArray[5], null) : null;
                    String string3 = stringArray.length > 4 ? stringArray[5] : null;
                    iPBanList.addEntry(new IPBanEntry(string, date, string2, date2, string3));
                }
                iPBanList.writeChanges();
                PreYggdrasilConverter.backupConverted(OLD_IPBAN_FILE);
                return true;
            } catch (IOException iOException) {
                LOGGER.warn("Could not parse old ip banlist to convert it!", (Throwable)iOException);
                return true;
            }
        }
        return false;
    }

    public static boolean convertOplist(MinecraftServer minecraftServer) {
        OpList opList = new OpList(PlayerList.FILE_OPS);
        if (OLD_OPS_FILE.exists() && OLD_OPS_FILE.isFile()) {
            if (opList.getSaveFile().exists()) {
                try {
                    opList.readSavedFile();
                } catch (IOException iOException) {
                    LOGGER.warn("Could not load existing file {}", (Object)opList.getSaveFile().getName(), (Object)iOException);
                }
            }
            try {
                List<String> list = Files.readLines(OLD_OPS_FILE, StandardCharsets.UTF_8);
                ProfileLookupCallback profileLookupCallback = new ProfileLookupCallback(){
                    final MinecraftServer val$server;
                    final OpList val$oplist;
                    {
                        this.val$server = minecraftServer;
                        this.val$oplist = opList;
                    }

                    @Override
                    public void onProfileLookupSucceeded(GameProfile gameProfile) {
                        this.val$server.getPlayerProfileCache().addEntry(gameProfile);
                        this.val$oplist.addEntry(new OpEntry(gameProfile, this.val$server.getOpPermissionLevel(), false));
                    }

                    @Override
                    public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
                        LOGGER.warn("Could not lookup oplist entry for {}", (Object)gameProfile.getName(), (Object)exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new ConversionError("Could not request user " + gameProfile.getName() + " from backend systems", exception);
                        }
                    }
                };
                PreYggdrasilConverter.lookupNames(minecraftServer, list, profileLookupCallback);
                opList.writeChanges();
                PreYggdrasilConverter.backupConverted(OLD_OPS_FILE);
                return true;
            } catch (IOException iOException) {
                LOGGER.warn("Could not read old oplist to convert it!", (Throwable)iOException);
                return true;
            } catch (ConversionError conversionError) {
                LOGGER.error("Conversion failed, please try again later", (Throwable)conversionError);
                return true;
            }
        }
        return false;
    }

    public static boolean convertWhitelist(MinecraftServer minecraftServer) {
        WhiteList whiteList = new WhiteList(PlayerList.FILE_WHITELIST);
        if (OLD_WHITELIST_FILE.exists() && OLD_WHITELIST_FILE.isFile()) {
            if (whiteList.getSaveFile().exists()) {
                try {
                    whiteList.readSavedFile();
                } catch (IOException iOException) {
                    LOGGER.warn("Could not load existing file {}", (Object)whiteList.getSaveFile().getName(), (Object)iOException);
                }
            }
            try {
                List<String> list = Files.readLines(OLD_WHITELIST_FILE, StandardCharsets.UTF_8);
                ProfileLookupCallback profileLookupCallback = new ProfileLookupCallback(){
                    final MinecraftServer val$server;
                    final WhiteList val$whitelist;
                    {
                        this.val$server = minecraftServer;
                        this.val$whitelist = whiteList;
                    }

                    @Override
                    public void onProfileLookupSucceeded(GameProfile gameProfile) {
                        this.val$server.getPlayerProfileCache().addEntry(gameProfile);
                        this.val$whitelist.addEntry(new WhitelistEntry(gameProfile));
                    }

                    @Override
                    public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
                        LOGGER.warn("Could not lookup user whitelist entry for {}", (Object)gameProfile.getName(), (Object)exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new ConversionError("Could not request user " + gameProfile.getName() + " from backend systems", exception);
                        }
                    }
                };
                PreYggdrasilConverter.lookupNames(minecraftServer, list, profileLookupCallback);
                whiteList.writeChanges();
                PreYggdrasilConverter.backupConverted(OLD_WHITELIST_FILE);
                return true;
            } catch (IOException iOException) {
                LOGGER.warn("Could not read old whitelist to convert it!", (Throwable)iOException);
                return true;
            } catch (ConversionError conversionError) {
                LOGGER.error("Conversion failed, please try again later", (Throwable)conversionError);
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static UUID convertMobOwnerIfNeeded(MinecraftServer minecraftServer, String string) {
        if (!StringUtils.isNullOrEmpty(string) && string.length() <= 16) {
            GameProfile gameProfile = minecraftServer.getPlayerProfileCache().getGameProfileForUsername(string);
            if (gameProfile != null && gameProfile.getId() != null) {
                return gameProfile.getId();
            }
            if (!minecraftServer.isSinglePlayer() && minecraftServer.isServerInOnlineMode()) {
                ArrayList arrayList = Lists.newArrayList();
                ProfileLookupCallback profileLookupCallback = new ProfileLookupCallback(){
                    final MinecraftServer val$server;
                    final List val$list;
                    {
                        this.val$server = minecraftServer;
                        this.val$list = list;
                    }

                    @Override
                    public void onProfileLookupSucceeded(GameProfile gameProfile) {
                        this.val$server.getPlayerProfileCache().addEntry(gameProfile);
                        this.val$list.add(gameProfile);
                    }

                    @Override
                    public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
                        LOGGER.warn("Could not lookup user whitelist entry for {}", (Object)gameProfile.getName(), (Object)exception);
                    }
                };
                PreYggdrasilConverter.lookupNames(minecraftServer, Lists.newArrayList(string), profileLookupCallback);
                return !arrayList.isEmpty() && ((GameProfile)arrayList.get(0)).getId() != null ? ((GameProfile)arrayList.get(0)).getId() : null;
            }
            return PlayerEntity.getUUID(new GameProfile(null, string));
        }
        try {
            return UUID.fromString(string);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    public static boolean convertSaveFiles(DedicatedServer dedicatedServer) {
        File file = PreYggdrasilConverter.func_219585_g(dedicatedServer);
        File file2 = new File(file.getParentFile(), "playerdata");
        File file3 = new File(file.getParentFile(), "unknownplayers");
        if (file.exists() && file.isDirectory()) {
            File[] fileArray = file.listFiles();
            ArrayList<String> arrayList = Lists.newArrayList();
            for (File file4 : fileArray) {
                String string;
                String string2 = file4.getName();
                if (!string2.toLowerCase(Locale.ROOT).endsWith(".dat") || (string = string2.substring(0, string2.length() - 4)).isEmpty()) continue;
                arrayList.add(string);
            }
            try {
                Object[] objectArray = arrayList.toArray(new String[arrayList.size()]);
                ProfileLookupCallback profileLookupCallback = new ProfileLookupCallback(){
                    final DedicatedServer val$server;
                    final File val$file2;
                    final File val$file3;
                    final File val$file1;
                    final String[] val$astring;
                    {
                        this.val$server = dedicatedServer;
                        this.val$file2 = file;
                        this.val$file3 = file2;
                        this.val$file1 = file3;
                        this.val$astring = stringArray;
                    }

                    @Override
                    public void onProfileLookupSucceeded(GameProfile gameProfile) {
                        this.val$server.getPlayerProfileCache().addEntry(gameProfile);
                        UUID uUID = gameProfile.getId();
                        if (uUID == null) {
                            throw new ConversionError("Missing UUID for user profile " + gameProfile.getName());
                        }
                        this.renamePlayerFile(this.val$file2, this.getFileNameForProfile(gameProfile), uUID.toString());
                    }

                    @Override
                    public void onProfileLookupFailed(GameProfile gameProfile, Exception exception) {
                        LOGGER.warn("Could not lookup user uuid for {}", (Object)gameProfile.getName(), (Object)exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new ConversionError("Could not request user " + gameProfile.getName() + " from backend systems", exception);
                        }
                        String string = this.getFileNameForProfile(gameProfile);
                        this.renamePlayerFile(this.val$file3, string, string);
                    }

                    private void renamePlayerFile(File file, String string, String string2) {
                        File file2 = new File(this.val$file1, string + ".dat");
                        File file3 = new File(file, string2 + ".dat");
                        PreYggdrasilConverter.mkdir(file);
                        if (!file2.renameTo(file3)) {
                            throw new ConversionError("Could not convert file for " + string);
                        }
                    }

                    private String getFileNameForProfile(GameProfile gameProfile) {
                        String string = null;
                        for (String string2 : this.val$astring) {
                            if (string2 == null || !string2.equalsIgnoreCase(gameProfile.getName())) continue;
                            string = string2;
                            break;
                        }
                        if (string == null) {
                            throw new ConversionError("Could not find the filename for " + gameProfile.getName() + " anymore");
                        }
                        return string;
                    }
                };
                PreYggdrasilConverter.lookupNames(dedicatedServer, Lists.newArrayList(objectArray), profileLookupCallback);
                return true;
            } catch (ConversionError conversionError) {
                LOGGER.error("Conversion failed, please try again later", (Throwable)conversionError);
                return true;
            }
        }
        return false;
    }

    private static void mkdir(File file) {
        if (file.exists() ? !file.isDirectory() : !file.mkdirs()) {
            throw new ConversionError("Can't create directory " + file.getName() + " in world save directory.");
        }
    }

    public static boolean func_219587_e(MinecraftServer minecraftServer) {
        boolean bl = PreYggdrasilConverter.hasUnconvertableFiles();
        return bl && PreYggdrasilConverter.func_219589_f(minecraftServer);
    }

    private static boolean hasUnconvertableFiles() {
        boolean bl = false;
        if (OLD_PLAYERBAN_FILE.exists() && OLD_PLAYERBAN_FILE.isFile()) {
            bl = true;
        }
        boolean bl2 = false;
        if (OLD_IPBAN_FILE.exists() && OLD_IPBAN_FILE.isFile()) {
            bl2 = true;
        }
        boolean bl3 = false;
        if (OLD_OPS_FILE.exists() && OLD_OPS_FILE.isFile()) {
            bl3 = true;
        }
        boolean bl4 = false;
        if (OLD_WHITELIST_FILE.exists() && OLD_WHITELIST_FILE.isFile()) {
            bl4 = true;
        }
        if (!(bl || bl2 || bl3 || bl4)) {
            return false;
        }
        LOGGER.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
        LOGGER.warn("** please remove the following files and restart the server:");
        if (bl) {
            LOGGER.warn("* {}", (Object)OLD_PLAYERBAN_FILE.getName());
        }
        if (bl2) {
            LOGGER.warn("* {}", (Object)OLD_IPBAN_FILE.getName());
        }
        if (bl3) {
            LOGGER.warn("* {}", (Object)OLD_OPS_FILE.getName());
        }
        if (bl4) {
            LOGGER.warn("* {}", (Object)OLD_WHITELIST_FILE.getName());
        }
        return true;
    }

    private static boolean func_219589_f(MinecraftServer minecraftServer) {
        File file = PreYggdrasilConverter.func_219585_g(minecraftServer);
        if (!file.exists() || !file.isDirectory() || file.list().length <= 0 && file.delete()) {
            return false;
        }
        LOGGER.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
        LOGGER.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
        LOGGER.warn("** please restart the server and if the problem persists, remove the directory '{}'", (Object)file.getPath());
        return true;
    }

    private static File func_219585_g(MinecraftServer minecraftServer) {
        return minecraftServer.func_240776_a_(FolderName.PLAYERS).toFile();
    }

    private static void backupConverted(File file) {
        File file2 = new File(file.getName() + ".converted");
        file.renameTo(file2);
    }

    private static Date parseDate(String string, Date date) {
        Date date2;
        try {
            date2 = BanEntry.DATE_FORMAT.parse(string);
        } catch (ParseException parseException) {
            date2 = date;
        }
        return date2;
    }

    private static String[] lambda$lookupNames$1(int n) {
        return new String[n];
    }

    private static boolean lambda$lookupNames$0(String string) {
        return !StringUtils.isNullOrEmpty(string);
    }

    static class ConversionError
    extends RuntimeException {
        private ConversionError(String string, Throwable throwable) {
            super(string, throwable);
        }

        private ConversionError(String string) {
            super(string);
        }
    }
}

