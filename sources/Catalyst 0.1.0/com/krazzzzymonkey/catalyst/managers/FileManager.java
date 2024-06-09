// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;
import com.krazzzzymonkey.catalyst.xray.XRayData;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import com.google.gson.GsonBuilder;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Map;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.krazzzzymonkey.catalyst.value.Mode;
import java.util.Iterator;
import java.io.IOException;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import com.google.gson.JsonElement;
import com.krazzzzymonkey.catalyst.value.ModeValue;
import com.krazzzzymonkey.catalyst.value.NumberValue;
import com.krazzzzymonkey.catalyst.value.BooleanValue;
import com.krazzzzymonkey.catalyst.value.Value;
import com.krazzzzymonkey.catalyst.module.Modules;
import com.google.gson.JsonObject;
import java.util.List;
import com.google.gson.Gson;
import java.io.File;
import com.google.gson.JsonParser;

public class FileManager
{
    private static /* synthetic */ JsonParser jsonParser;
    private static final /* synthetic */ int[] lIIIIIlI;
    private static final /* synthetic */ String[] llllIll;
    private static final /* synthetic */ File HACKS;
    public static final /* synthetic */ File GISHCODE_DIR;
    private static final /* synthetic */ File XRAYDATA;
    private static /* synthetic */ Gson gsonPretty;
    private static final /* synthetic */ File FRIENDS;
    private static final /* synthetic */ File ENEMYS;
    
    public static void saveEnemys() {
        write(FileManager.ENEMYS, EnemyManager.enemysList, (boolean)(FileManager.lIIIIIlI[1] != 0), (boolean)(FileManager.lIIIIIlI[1] != 0));
    }
    
    private static boolean llIlIlIII(final int llIIlllIIlIllll, final int llIIlllIIlIlllI) {
        return llIIlllIIlIllll < llIIlllIIlIlllI;
    }
    
    public static void saveHacks() {
        try {
            final JsonObject llIIllllIIlIlIl = new JsonObject();
            final Iterator<Modules> iterator = HackManager.getHacks().iterator();
            while (llIlIIllI(iterator.hasNext() ? 1 : 0)) {
                final Modules llIIllllIIlIllI = iterator.next();
                final JsonObject llIIllllIIlIlll = new JsonObject();
                llIIllllIIlIlll.addProperty(FileManager.llllIll[FileManager.lIIIIIlI[11]], Boolean.valueOf(llIIllllIIlIllI.isToggled()));
                llIIllllIIlIlll.addProperty(FileManager.llllIll[FileManager.lIIIIIlI[12]], (Number)llIIllllIIlIllI.getKey());
                if (llIlIIlII(llIIllllIIlIllI.getValues().isEmpty() ? 1 : 0)) {
                    final long llIIllllIIIlllI = (long)llIIllllIIlIllI.getValues().iterator();
                    while (llIlIIllI(((Iterator)llIIllllIIIlllI).hasNext() ? 1 : 0)) {
                        final Value llIIllllIIllIII = ((Iterator<Value>)llIIllllIIIlllI).next();
                        if (llIlIIllI((llIIllllIIllIII instanceof BooleanValue) ? 1 : 0)) {
                            llIIllllIIlIlll.addProperty(llIIllllIIllIII.getName(), Boolean.valueOf(llIIllllIIllIII.getValue()));
                        }
                        if (llIlIIllI((llIIllllIIllIII instanceof NumberValue) ? 1 : 0)) {
                            llIIllllIIlIlll.addProperty(llIIllllIIllIII.getName(), (Number)llIIllllIIllIII.getValue());
                        }
                        if (llIlIIllI((llIIllllIIllIII instanceof ModeValue) ? 1 : 0)) {
                            final ModeValue llIIllllIIllIIl = (ModeValue)llIIllllIIllIII;
                            final long llIIllllIIIlIll = (Object)llIIllllIIllIIl.getModes();
                            final int llIIllllIIIlIlI = llIIllllIIIlIll.length;
                            long llIIllllIIIlIIl = FileManager.lIIIIIlI[0];
                            while (llIlIlIII((int)llIIllllIIIlIIl, llIIllllIIIlIlI)) {
                                final Mode llIIllllIIllIlI = llIIllllIIIlIll[llIIllllIIIlIIl];
                                llIIllllIIlIlll.addProperty(llIIllllIIllIlI.getName(), Boolean.valueOf(llIIllllIIllIlI.isToggled()));
                                ++llIIllllIIIlIIl;
                                "".length();
                                if (((0x5D ^ 0x3) & ~(0x4 ^ 0x5A)) != 0x0) {
                                    return;
                                }
                            }
                        }
                        "".length();
                        if (" ".length() == -" ".length()) {
                            return;
                        }
                    }
                }
                llIIllllIIlIlIl.add(llIIllllIIlIllI.getName(), (JsonElement)llIIllllIIlIlll);
                "".length();
                if ((151 + 40 - 121 + 83 ^ 155 + 63 - 198 + 137) <= "   ".length()) {
                    return;
                }
            }
            final PrintWriter llIIllllIIlIlII = new PrintWriter(new FileWriter(FileManager.HACKS));
            llIIllllIIlIlII.println(FileManager.gsonPretty.toJson((JsonElement)llIIllllIIlIlIl));
            llIIllllIIlIlII.close();
            "".length();
            if ("   ".length() > "   ".length()) {
                return;
            }
        }
        catch (IOException llIIllllIIlIIll) {
            llIIllllIIlIIll.printStackTrace();
        }
    }
    
    private static String llIIIllIl(String llIIlllIlIIIlll, final String llIIlllIlIIlIll) {
        llIIlllIlIIIlll = new String(Base64.getDecoder().decode(llIIlllIlIIIlll.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        final StringBuilder llIIlllIlIIlIlI = new StringBuilder();
        final char[] llIIlllIlIIlIIl = llIIlllIlIIlIll.toCharArray();
        int llIIlllIlIIlIII = FileManager.lIIIIIlI[0];
        final byte llIIlllIlIIIIlI = (Object)llIIlllIlIIIlll.toCharArray();
        final Exception llIIlllIlIIIIIl = (Exception)llIIlllIlIIIIlI.length;
        Exception llIIlllIlIIIIII = (Exception)FileManager.lIIIIIlI[0];
        while (llIlIlIII((int)llIIlllIlIIIIII, (int)llIIlllIlIIIIIl)) {
            final char llIIlllIlIIllIl = llIIlllIlIIIIlI[llIIlllIlIIIIII];
            llIIlllIlIIlIlI.append((char)(llIIlllIlIIllIl ^ llIIlllIlIIlIIl[llIIlllIlIIlIII % llIIlllIlIIlIIl.length]));
            "".length();
            ++llIIlllIlIIlIII;
            ++llIIlllIlIIIIII;
            "".length();
            if ("  ".length() >= (0x68 ^ 0x6C)) {
                return null;
            }
        }
        return String.valueOf(llIIlllIlIIlIlI);
    }
    
    public static void loadHacks() {
        try {
            final BufferedReader llIIlllllllIlIl = new BufferedReader(new FileReader(FileManager.HACKS));
            final JsonObject llIIlllllllIlII = (JsonObject)FileManager.jsonParser.parse((Reader)llIIlllllllIlIl);
            llIIlllllllIlIl.close();
            final byte llIIlllllllIIII = (byte)llIIlllllllIlII.entrySet().iterator();
            while (llIlIIllI(((Iterator)llIIlllllllIIII).hasNext() ? 1 : 0)) {
                final Map.Entry<String, JsonElement> llIIlllllllIllI = ((Iterator<Map.Entry<String, JsonElement>>)llIIlllllllIIII).next();
                final Modules llIIlllllllIlll = HackManager.getHack(llIIlllllllIllI.getKey());
                if (llIlIIlll(llIIlllllllIlll)) {
                    final JsonObject llIIllllllllIIl = (JsonObject)llIIlllllllIllI.getValue();
                    final boolean llIIllllllllIII = llIIllllllllIIl.get(FileManager.llllIll[FileManager.lIIIIIlI[0]]).getAsBoolean();
                    if (llIlIIllI(llIIllllllllIII ? 1 : 0)) {
                        llIIlllllllIlll.setToggled((boolean)(FileManager.lIIIIIlI[1] != 0));
                    }
                    if (llIlIIlII(llIIlllllllIlll.getValues().isEmpty() ? 1 : 0)) {
                        final double llIIllllllIlIll = (double)llIIlllllllIlll.getValues().iterator();
                        while (llIlIIllI(((Iterator)llIIllllllIlIll).hasNext() ? 1 : 0)) {
                            final Value llIIllllllllIlI = ((Iterator<Value>)llIIllllllIlIll).next();
                            if (llIlIIllI((llIIllllllllIlI instanceof BooleanValue) ? 1 : 0)) {
                                final boolean llIIlllllllllll = llIIllllllllIIl.get(llIIllllllllIlI.getName()).getAsBoolean();
                                llIIllllllllIlI.setValue(llIIlllllllllll);
                            }
                            if (llIlIIllI((llIIllllllllIlI instanceof NumberValue) ? 1 : 0)) {
                                final double llIIllllllllllI = llIIllllllllIIl.get(llIIllllllllIlI.getName()).getAsDouble();
                                llIIllllllllIlI.setValue(llIIllllllllllI);
                            }
                            if (llIlIIllI((llIIllllllllIlI instanceof ModeValue) ? 1 : 0)) {
                                final ModeValue llIIllllllllIll = (ModeValue)llIIllllllllIlI;
                                final float llIIllllllIlIII = (Object)llIIllllllllIll.getModes();
                                final boolean llIIllllllIIlll = llIIllllllIlIII.length != 0;
                                long llIIllllllIIllI = FileManager.lIIIIIlI[0];
                                while (llIlIlIII((int)llIIllllllIIllI, llIIllllllIIlll ? 1 : 0)) {
                                    final Mode llIIlllllllllII = llIIllllllIlIII[llIIllllllIIllI];
                                    final boolean llIIlllllllllIl = llIIllllllllIIl.get(llIIlllllllllII.getName()).getAsBoolean();
                                    llIIlllllllllII.setToggled(llIIlllllllllIl);
                                    ++llIIllllllIIllI;
                                    "".length();
                                    if ("  ".length() < 0) {
                                        return;
                                    }
                                }
                            }
                            "".length();
                            if ((0x84 ^ 0xBB ^ (0x70 ^ 0x4B)) != (0x71 ^ 0x5A ^ (0xBD ^ 0x92))) {
                                return;
                            }
                        }
                    }
                    llIIlllllllIlll.setKey(llIIllllllllIIl.get(FileManager.llllIll[FileManager.lIIIIIlI[1]]).getAsInt());
                }
                "".length();
                if ("   ".length() <= " ".length()) {
                    return;
                }
            }
            "".length();
            if ("  ".length() >= "   ".length()) {
                return;
            }
        }
        catch (IOException llIIlllllllIIll) {
            llIIlllllllIIll.printStackTrace();
        }
    }
    
    public static void saveFriends() {
        write(FileManager.FRIENDS, FriendManager.friendsList, (boolean)(FileManager.lIIIIIlI[1] != 0), (boolean)(FileManager.lIIIIIlI[1] != 0));
    }
    
    public static void loadFriends() {
        final List<String> llIIlllllIlllll = read(FileManager.FRIENDS);
        final short llIIlllllIlllIl = (short)llIIlllllIlllll.iterator();
        while (llIlIIllI(((Iterator)llIIlllllIlllIl).hasNext() ? 1 : 0)) {
            final String llIIllllllIIIII = ((Iterator<String>)llIIlllllIlllIl).next();
            FriendManager.addFriend(llIIllllllIIIII);
            "".length();
            if (("  ".length() & ~"  ".length()) != 0x0) {
                return;
            }
        }
    }
    
    private static String llIIIllll(final String llIIlllIIllIlll, final String llIIlllIIllIlII) {
        try {
            final SecretKeySpec llIIlllIIlllIlI = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(llIIlllIIllIlII.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            final Cipher llIIlllIIlllIIl = Cipher.getInstance("Blowfish");
            llIIlllIIlllIIl.init(FileManager.lIIIIIlI[2], llIIlllIIlllIlI);
            return new String(llIIlllIIlllIIl.doFinal(Base64.getDecoder().decode(llIIlllIIllIlll.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIlllIIlllIII) {
            llIIlllIIlllIII.printStackTrace();
            return null;
        }
    }
    
    static {
        llIlIIIll();
        llIIllIll();
        FileManager.gsonPretty = new GsonBuilder().setPrettyPrinting().create();
        FileManager.jsonParser = new JsonParser();
        final String s = FileManager.llllIll[FileManager.lIIIIIlI[13]];
        final Object[] array = new Object[FileManager.lIIIIIlI[6]];
        array[FileManager.lIIIIIlI[0]] = Wrapper.INSTANCE.mc().gameDir;
        array[FileManager.lIIIIIlI[1]] = File.separator;
        array[FileManager.lIIIIIlI[2]] = FileManager.llllIll[FileManager.lIIIIIlI[14]];
        array[FileManager.lIIIIIlI[3]] = FileManager.llllIll[FileManager.lIIIIIlI[15]];
        array[FileManager.lIIIIIlI[4]] = FileManager.llllIll[FileManager.lIIIIIlI[16]];
        array[FileManager.lIIIIIlI[5]] = File.separator;
        GISHCODE_DIR = new File(String.format(s, array));
        HACKS = new File(FileManager.GISHCODE_DIR, FileManager.llllIll[FileManager.lIIIIIlI[17]]);
        XRAYDATA = new File(FileManager.GISHCODE_DIR, FileManager.llllIll[FileManager.lIIIIIlI[18]]);
        FRIENDS = new File(FileManager.GISHCODE_DIR, FileManager.llllIll[FileManager.lIIIIIlI[19]]);
        ENEMYS = new File(FileManager.GISHCODE_DIR, FileManager.llllIll[FileManager.lIIIIIlI[20]]);
    }
    
    public static void loadEnemys() {
        final List<String> llIIlllllIlIlll = read(FileManager.ENEMYS);
        final long llIIlllllIlIlIl = (long)llIIlllllIlIlll.iterator();
        while (llIlIIllI(((Iterator)llIIlllllIlIlIl).hasNext() ? 1 : 0)) {
            final String llIIlllllIllIII = ((Iterator<String>)llIIlllllIlIlIl).next();
            EnemyManager.addEnemy(llIIlllllIllIII);
            "".length();
            if ("   ".length() == ((0xA8 ^ 0x87 ^ (0x79 ^ 0x9)) & (2 + 123 + 14 + 88 ^ 94 + 8 - 91 + 177 ^ -" ".length()))) {
                return;
            }
        }
    }
    
    public FileManager() {
        if (llIlIIlII(FileManager.GISHCODE_DIR.exists() ? 1 : 0)) {
            FileManager.GISHCODE_DIR.mkdir();
            "".length();
        }
        if (llIlIIlII(FileManager.HACKS.exists() ? 1 : 0)) {
            saveHacks();
            "".length();
            if ((28 + 11 - 8 + 99 ^ 95 + 67 - 110 + 82) > (130 + 59 - 156 + 131 ^ 32 + 58 - 69 + 139)) {
                throw null;
            }
        }
        else {
            loadHacks();
        }
        if (llIlIIlII(FileManager.XRAYDATA.exists() ? 1 : 0)) {
            saveXRayData();
            "".length();
            if (-"   ".length() >= 0) {
                throw null;
            }
        }
        else {
            loadXRayData();
        }
        if (llIlIIlII(FileManager.FRIENDS.exists() ? 1 : 0)) {
            saveFriends();
            "".length();
            if (" ".length() == ((0x8B ^ 0x9D ^ (0x8F ^ 0xC3)) & (187 + 133 - 213 + 98 ^ 134 + 33 - 73 + 57 ^ -" ".length()))) {
                throw null;
            }
        }
        else {
            loadFriends();
        }
        if (llIlIIlII(FileManager.ENEMYS.exists() ? 1 : 0)) {
            saveEnemys();
            "".length();
            if (null != null) {
                throw null;
            }
        }
        else {
            loadEnemys();
        }
    }
    
    public static void loadXRayData() {
        try {
            final BufferedReader llIIlllllIIIIII = new BufferedReader(new FileReader(FileManager.XRAYDATA));
            final JsonObject llIIllllIllllll = (JsonObject)FileManager.jsonParser.parse((Reader)llIIlllllIIIIII);
            llIIlllllIIIIII.close();
            final byte llIIllllIlllIll = (byte)llIIllllIllllll.entrySet().iterator();
            while (llIlIIllI(((Iterator)llIIllllIlllIll).hasNext() ? 1 : 0)) {
                final Map.Entry<String, JsonElement> llIIlllllIIIIIl = ((Iterator<Map.Entry<String, JsonElement>>)llIIllllIlllIll).next();
                final JsonObject llIIlllllIIlIII = (JsonObject)llIIlllllIIIIIl.getValue();
                final String[] llIIlllllIIIlll = llIIlllllIIIIIl.getKey().split(FileManager.llllIll[FileManager.lIIIIIlI[2]]);
                final int llIIlllllIIIllI = Integer.parseInt(llIIlllllIIIlll[FileManager.lIIIIIlI[0]]);
                final int llIIlllllIIIlIl = Integer.parseInt(llIIlllllIIIlll[FileManager.lIIIIIlI[1]]);
                final int llIIlllllIIIlII = llIIlllllIIlIII.get(FileManager.llllIll[FileManager.lIIIIIlI[3]]).getAsInt();
                final int llIIlllllIIIIll = llIIlllllIIlIII.get(FileManager.llllIll[FileManager.lIIIIIlI[4]]).getAsInt();
                final int llIIlllllIIIIlI = llIIlllllIIlIII.get(FileManager.llllIll[FileManager.lIIIIIlI[5]]).getAsInt();
                XRayManager.addData(new XRayData(llIIlllllIIIllI, llIIlllllIIIlIl, llIIlllllIIIlII, llIIlllllIIIIll, llIIlllllIIIIlI));
                "".length();
                if (null != null) {
                    return;
                }
            }
            "".length();
            if (((0xCD ^ 0xC0) & ~(0x95 ^ 0x98)) != 0x0) {
                return;
            }
        }
        catch (IOException llIIllllIlllllI) {
            llIIllllIlllllI.printStackTrace();
        }
    }
    
    private static void llIlIIIll() {
        (lIIIIIlI = new int[22])[0] = ((0xF2 ^ 0xB2) & ~(0x61 ^ 0x21));
        FileManager.lIIIIIlI[1] = " ".length();
        FileManager.lIIIIIlI[2] = "  ".length();
        FileManager.lIIIIIlI[3] = "   ".length();
        FileManager.lIIIIIlI[4] = (0xB4 ^ 0xB0);
        FileManager.lIIIIIlI[5] = (0x6D ^ 0x54 ^ (0x3C ^ 0x0));
        FileManager.lIIIIIlI[6] = (0x1A ^ 0x11 ^ (0x3E ^ 0x33));
        FileManager.lIIIIIlI[7] = (0xC2 ^ 0xC5);
        FileManager.lIIIIIlI[8] = (91 + 34 - 9 + 47 ^ 119 + 95 - 186 + 143);
        FileManager.lIIIIIlI[9] = (0xCD ^ 0xC4);
        FileManager.lIIIIIlI[10] = (0x6 ^ 0x3C ^ (0xA1 ^ 0x91));
        FileManager.lIIIIIlI[11] = (0x29 ^ 0x22);
        FileManager.lIIIIIlI[12] = (0xF7 ^ 0xBC ^ (0x7 ^ 0x40));
        FileManager.lIIIIIlI[13] = (0x7F ^ 0x72);
        FileManager.lIIIIIlI[14] = (0xA7 ^ 0xA9);
        FileManager.lIIIIIlI[15] = (33 + 160 - 118 + 93 ^ 157 + 79 - 202 + 133);
        FileManager.lIIIIIlI[16] = (0xBD ^ 0xAD);
        FileManager.lIIIIIlI[17] = (0x18 ^ 0x9);
        FileManager.lIIIIIlI[18] = (0xF ^ 0x64 ^ (0x16 ^ 0x6F));
        FileManager.lIIIIIlI[19] = (0x38 ^ 0x5C ^ (0x11 ^ 0x66));
        FileManager.lIIIIIlI[20] = (0xD5 ^ 0xC1);
        FileManager.lIIIIIlI[21] = (126 + 154 - 245 + 142 ^ 93 + 51 - 127 + 147);
    }
    
    private static void llIIllIll() {
        (llllIll = new String[FileManager.lIIIIIlI[21]])[FileManager.lIIIIIlI[0]] = llIIIllIl("MBwNAQEhFw==", "Dsjfm");
        FileManager.llllIll[FileManager.lIIIIIlI[1]] = llIIIlllI("iUQY5aKES24=", "oiVuM");
        FileManager.llllIll[FileManager.lIIIIIlI[2]] = llIIIlllI("/Yq37RyYa3M=", "MAbND");
        FileManager.llllIll[FileManager.lIIIIIlI[3]] = llIIIllIl("GQEe", "kdzVu");
        FileManager.llllIll[FileManager.lIIIIIlI[4]] = llIIIllll("XxWUF7oUVLo=", "jOgwm");
        FileManager.llllIll[FileManager.lIIIIIlI[5]] = llIIIllll("9RGmQ4hGAFM=", "UVeXr");
        FileManager.llllIll[FileManager.lIIIIIlI[6]] = llIIIlllI("xs6O9MYgcSw=", "hMRZK");
        FileManager.llllIll[FileManager.lIIIIIlI[7]] = llIIIllll("6a8q431ZpvI=", "oRULC");
        FileManager.llllIll[FileManager.lIIIIIlI[8]] = llIIIllIl("ByMBJw==", "eOtBY");
        FileManager.llllIll[FileManager.lIIIIIlI[9]] = llIIIllll("dPIeW99No0U=", "Skrnm");
        FileManager.llllIll[FileManager.lIIIIIlI[10]] = llIIIlllI("vpb5hht/4Xk=", "dPoPO");
        FileManager.llllIll[FileManager.lIIIIIlI[11]] = llIIIlllI("8DxdRMIR4oQ=", "zRYAM");
        FileManager.llllIll[FileManager.lIIIIIlI[12]] = llIIIllll("ascGEjUOOKc=", "XgcFt");
        FileManager.llllIll[FileManager.lIIIIIlI[13]] = llIIIlllI("bJhdBRb0xcvsN3yzFdH1Gw==", "EZFAO");
        FileManager.llllIll[FileManager.lIIIIIlI[14]] = llIIIlllI("dPiY1LSIrEoAc+sJIUC/jw==", "AoiRr");
        FileManager.llllIll[FileManager.lIIIIIlI[15]] = llIIIllll("Ej2axTKwZGc=", "SsxPh");
        FileManager.llllIll[FileManager.lIIIIIlI[16]] = llIIIllIl("W1xARlo=", "krqhj");
        FileManager.llllIll[FileManager.lIIIIIlI[17]] = llIIIllll("yg3CFYAAjhLPEeaTWFKwrg==", "CkkLy");
        FileManager.llllIll[FileManager.lIIIIIlI[18]] = llIIIllIl("CBoNMAERHA1nDwMHAg==", "phlIe");
        FileManager.llllIll[FileManager.lIIIIIlI[19]] = llIIIllIl("AyECHRcBIEUSCgo9", "eSkxy");
        FileManager.llllIll[FileManager.lIIIIIlI[20]] = llIIIllll("pSTQDlp2/OC1QgiP96vFZA==", "LEyhX");
    }
    
    public static void write(final File llIIlllIllllIIl, final List<String> llIIlllIlllllIl, final boolean llIIlllIlllIlll, final boolean llIIlllIllllIll) {
        BufferedWriter llIIlllIllllIlI = null;
        try {
            int n;
            if (llIlIIlII(llIIlllIllllIll ? 1 : 0)) {
                n = FileManager.lIIIIIlI[1];
                "".length();
                if ("  ".length() < "  ".length()) {
                    return;
                }
            }
            else {
                n = FileManager.lIIIIIlI[0];
            }
            llIIlllIllllIlI = new BufferedWriter(new FileWriter(llIIlllIllllIIl, (boolean)(n != 0)));
            final Iterator<String> iterator = llIIlllIlllllIl.iterator();
            while (llIlIIllI(iterator.hasNext() ? 1 : 0)) {
                final String llIIllllIIIIIII = iterator.next();
                llIIlllIllllIlI.write(llIIllllIIIIIII);
                llIIlllIllllIlI.flush();
                if (llIlIIllI(llIIlllIlllIlll ? 1 : 0)) {
                    llIIlllIllllIlI.newLine();
                }
                "".length();
                if (((157 + 55 - 108 + 113 ^ 146 + 78 - 65 + 39) & (108 + 59 - 151 + 144 ^ 30 + 106 - 9 + 64 ^ -" ".length())) != ((145 + 43 - 145 + 116 ^ 175 + 100 - 150 + 57) & (0x2E ^ 0x6F ^ (0x4B ^ 0x23) ^ -" ".length()))) {
                    return;
                }
            }
            "".length();
            if ((49 + 123 - 82 + 44 ^ 70 + 116 - 85 + 30) <= 0) {
                return;
            }
        }
        catch (Exception llIIlllIlllllll) {
            try {
                if (llIlIIlll(llIIlllIllllIlI)) {
                    llIIlllIllllIlI.close();
                }
                "".length();
                if (((0x7B ^ 0x64) & ~(0x6C ^ 0x73)) != 0x0) {
                    return;
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private static String llIIIlllI(final String llIIlllIlIlllII, final String llIIlllIlIllIIl) {
        try {
            final SecretKeySpec llIIlllIlIlllll = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(llIIlllIlIllIIl.getBytes(StandardCharsets.UTF_8)), FileManager.lIIIIIlI[8]), "DES");
            final Cipher llIIlllIlIllllI = Cipher.getInstance("DES");
            llIIlllIlIllllI.init(FileManager.lIIIIIlI[2], llIIlllIlIlllll);
            return new String(llIIlllIlIllllI.doFinal(Base64.getDecoder().decode(llIIlllIlIlllII.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        }
        catch (Exception llIIlllIlIlllIl) {
            llIIlllIlIlllIl.printStackTrace();
            return null;
        }
    }
    
    private static boolean llIlIIllI(final int llIIlllIIlIlIlI) {
        return llIIlllIIlIlIlI != 0;
    }
    
    private static boolean llIlIIlII(final int llIIlllIIlIlIII) {
        return llIIlllIIlIlIII == 0;
    }
    
    public static List<String> read(final File llIIlllIllIlIll) {
        final ArrayList<String> llIIlllIllIlIlI = new ArrayList<String>();
        BufferedReader llIIlllIllIlIIl = null;
        try {
            llIIlllIllIlIIl = new BufferedReader(new FileReader(llIIlllIllIlIll));
            String llIIlllIllIllIl;
            while (llIlIIlll(llIIlllIllIllIl = llIIlllIllIlIIl.readLine())) {
                llIIlllIllIlIlI.add(llIIlllIllIllIl);
                "".length();
                "".length();
                if ("  ".length() == -" ".length()) {
                    return null;
                }
            }
            "".length();
            if (" ".length() >= "   ".length()) {
                return null;
            }
        }
        catch (Exception llIIlllIllIllII) {
            try {
                if (llIlIIlll(llIIlllIllIlIIl)) {
                    llIIlllIllIlIIl.close();
                }
                "".length();
                if (((0x66 ^ 0x46) & ~(0x79 ^ 0x59)) != 0x0) {
                    return null;
                }
                return llIIlllIllIlIlI;
            }
            catch (Exception ex) {}
        }
        return llIIlllIllIlIlI;
    }
    
    private static boolean llIlIIlll(final Object llIIlllIIlIllII) {
        return llIIlllIIlIllII != null;
    }
    
    public static void saveXRayData() {
        try {
            final JsonObject llIIllllIlIllII = new JsonObject();
            final byte llIIllllIlIlIII = (byte)XRayManager.xrayList.iterator();
            while (llIlIIllI(((Iterator)llIIllllIlIlIII).hasNext() ? 1 : 0)) {
                final XRayData llIIllllIlIllIl = ((Iterator<XRayData>)llIIllllIlIlIII).next();
                final JsonObject llIIllllIlIlllI = new JsonObject();
                llIIllllIlIlllI.addProperty(FileManager.llllIll[FileManager.lIIIIIlI[6]], (Number)llIIllllIlIllIl.getRed());
                llIIllllIlIlllI.addProperty(FileManager.llllIll[FileManager.lIIIIIlI[7]], (Number)llIIllllIlIllIl.getGreen());
                llIIllllIlIlllI.addProperty(FileManager.llllIll[FileManager.lIIIIIlI[8]], (Number)llIIllllIlIllIl.getBlue());
                llIIllllIlIllII.add(String.valueOf(new StringBuilder().append(FileManager.llllIll[FileManager.lIIIIIlI[9]]).append(llIIllllIlIllIl.getId()).append(FileManager.llllIll[FileManager.lIIIIIlI[10]]).append(llIIllllIlIllIl.getMeta())), (JsonElement)llIIllllIlIlllI);
                "".length();
                if ((54 + 36 - 34 + 98 ^ 153 + 71 - 191 + 125) == ((70 + 82 - 103 + 126 ^ 20 + 47 + 36 + 32) & (0x30 ^ 0x41 ^ (0x13 ^ 0x4A) ^ -" ".length()))) {
                    return;
                }
            }
            final PrintWriter llIIllllIlIlIll = new PrintWriter(new FileWriter(FileManager.XRAYDATA));
            llIIllllIlIlIll.println(FileManager.gsonPretty.toJson((JsonElement)llIIllllIlIllII));
            llIIllllIlIlIll.close();
            "".length();
            if (null != null) {
                return;
            }
        }
        catch (IOException llIIllllIlIlIlI) {
            llIIllllIlIlIlI.printStackTrace();
        }
    }
}
