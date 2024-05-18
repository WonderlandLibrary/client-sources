package net.minecraft.client.main;

import java.io.*;
import net.minecraft.client.*;
import java.net.*;
import com.mojang.authlib.properties.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import joptsimple.*;
import java.util.*;
import com.google.gson.*;

public class Main
{
    private static final String[] I;
    
    static {
        I();
    }
    
    private static boolean isNullOrEmpty(final String s) {
        if (s != null && !s.isEmpty()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static void main(final String[] array) {
        System.setProperty(Main.I["".length()], Main.I[" ".length()]);
        final OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts(Main.I["  ".length()]);
        optionParser.accepts(Main.I["   ".length()]);
        optionParser.accepts(Main.I[0x97 ^ 0x93]);
        final ArgumentAcceptingOptionSpec withRequiredArg = optionParser.accepts(Main.I[0x2E ^ 0x2B]).withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo = optionParser.accepts(Main.I[0xAC ^ 0xAA]).withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)(10808 + 19722 - 25589 + 20624), (Object[])new Integer["".length()]);
        final ArgumentAcceptingOptionSpec defaultsTo2 = optionParser.accepts(Main.I[0x9A ^ 0x9D]).withRequiredArg().ofType((Class)File.class).defaultsTo((Object)new File(Main.I[0x1B ^ 0x13]), (Object[])new File["".length()]);
        final ArgumentAcceptingOptionSpec ofType = optionParser.accepts(Main.I[0x88 ^ 0x81]).withRequiredArg().ofType((Class)File.class);
        final ArgumentAcceptingOptionSpec ofType2 = optionParser.accepts(Main.I[0x84 ^ 0x8E]).withRequiredArg().ofType((Class)File.class);
        final ArgumentAcceptingOptionSpec withRequiredArg2 = optionParser.accepts(Main.I[0xBE ^ 0xB5]).withRequiredArg();
        final ArgumentAcceptingOptionSpec ofType3 = optionParser.accepts(Main.I[0x35 ^ 0x39]).withRequiredArg().defaultsTo((Object)Main.I[0xB0 ^ 0xBD], (Object[])new String["".length()]).ofType((Class)Integer.class);
        final ArgumentAcceptingOptionSpec withRequiredArg3 = optionParser.accepts(Main.I[0x3B ^ 0x35]).withRequiredArg();
        final ArgumentAcceptingOptionSpec withRequiredArg4 = optionParser.accepts(Main.I[0xBB ^ 0xB4]).withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo3 = optionParser.accepts(Main.I[0x2D ^ 0x3D]).withRequiredArg().defaultsTo((Object)(Main.I[0x4E ^ 0x5F] + Minecraft.getSystemTime() % 1000L), (Object[])new String["".length()]);
        final ArgumentAcceptingOptionSpec withRequiredArg5 = optionParser.accepts(Main.I[0x44 ^ 0x56]).withRequiredArg();
        final ArgumentAcceptingOptionSpec required = optionParser.accepts(Main.I[0x88 ^ 0x9B]).withRequiredArg().required();
        final ArgumentAcceptingOptionSpec required2 = optionParser.accepts(Main.I[0xB6 ^ 0xA2]).withRequiredArg().required();
        final ArgumentAcceptingOptionSpec defaultsTo4 = optionParser.accepts(Main.I[0x54 ^ 0x41]).withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)(607 + 325 - 337 + 259), (Object[])new Integer["".length()]);
        final ArgumentAcceptingOptionSpec defaultsTo5 = optionParser.accepts(Main.I[0x86 ^ 0x90]).withRequiredArg().ofType((Class)Integer.class).defaultsTo((Object)(316 + 370 - 616 + 410), (Object[])new Integer["".length()]);
        final ArgumentAcceptingOptionSpec defaultsTo6 = optionParser.accepts(Main.I[0x9F ^ 0x88]).withRequiredArg().defaultsTo((Object)Main.I[0xD ^ 0x15], (Object[])new String["".length()]);
        final ArgumentAcceptingOptionSpec defaultsTo7 = optionParser.accepts(Main.I[0x7 ^ 0x1E]).withRequiredArg().defaultsTo((Object)Main.I[0x8A ^ 0x90], (Object[])new String["".length()]);
        final ArgumentAcceptingOptionSpec withRequiredArg6 = optionParser.accepts(Main.I[0x58 ^ 0x43]).withRequiredArg();
        final ArgumentAcceptingOptionSpec defaultsTo8 = optionParser.accepts(Main.I[0x5 ^ 0x19]).withRequiredArg().defaultsTo((Object)Main.I[0xA1 ^ 0xBC], (Object[])new String["".length()]);
        final NonOptionArgumentSpec nonOptions = optionParser.nonOptions();
        final OptionSet parse = optionParser.parse(array);
        final List values = parse.valuesOf((OptionSpec)nonOptions);
        if (!values.isEmpty()) {
            System.out.println(Main.I[0x8D ^ 0x93] + values);
        }
        final String s = (String)parse.valueOf((OptionSpec)withRequiredArg2);
        Proxy no_PROXY = Proxy.NO_PROXY;
        if (s != null) {
            try {
                no_PROXY = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(s, (int)parse.valueOf((OptionSpec)ofType3)));
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            catch (Exception ex) {}
        }
        final String s2 = (String)parse.valueOf((OptionSpec)withRequiredArg3);
        final String s3 = (String)parse.valueOf((OptionSpec)withRequiredArg4);
        if (!no_PROXY.equals(Proxy.NO_PROXY) && isNullOrEmpty(s2) && isNullOrEmpty(s3)) {
            Authenticator.setDefault(new Authenticator(s2, s3) {
                private final String val$s2;
                private final String val$s1;
                
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(this.val$s1, this.val$s2.toCharArray());
                }
                
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
        }
        final int intValue = (int)parse.valueOf((OptionSpec)defaultsTo4);
        final int intValue2 = (int)parse.valueOf((OptionSpec)defaultsTo5);
        final boolean has = parse.has(Main.I[0xF ^ 0x10]);
        final boolean has2 = parse.has(Main.I[0xE3 ^ 0xC3]);
        final boolean has3 = parse.has(Main.I[0x62 ^ 0x43]);
        final String s4 = (String)parse.valueOf((OptionSpec)required2);
        final Gson create = new GsonBuilder().registerTypeAdapter((Type)PropertyMap.class, (Object)new PropertyMap.Serializer()).create();
        final PropertyMap propertyMap = (PropertyMap)create.fromJson((String)parse.valueOf((OptionSpec)defaultsTo6), (Class)PropertyMap.class);
        final PropertyMap propertyMap2 = (PropertyMap)create.fromJson((String)parse.valueOf((OptionSpec)defaultsTo7), (Class)PropertyMap.class);
        final File file = (File)parse.valueOf((OptionSpec)defaultsTo2);
        File file2;
        if (parse.has((OptionSpec)ofType)) {
            file2 = (File)parse.valueOf((OptionSpec)ofType);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            file2 = new File(file, Main.I[0x2B ^ 0x9]);
        }
        final File file3 = file2;
        File file4;
        if (parse.has((OptionSpec)ofType2)) {
            file4 = (File)parse.valueOf((OptionSpec)ofType2);
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            file4 = new File(file, Main.I[0x3B ^ 0x18]);
        }
        final File file5 = file4;
        String s5;
        if (parse.has((OptionSpec)withRequiredArg5)) {
            s5 = (String)((OptionSpec)withRequiredArg5).value(parse);
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            s5 = (String)((OptionSpec)defaultsTo3).value(parse);
        }
        final String s6 = s5;
        String s7;
        if (parse.has((OptionSpec)withRequiredArg6)) {
            s7 = (String)((OptionSpec)withRequiredArg6).value(parse);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            s7 = null;
        }
        final GameConfiguration gameConfiguration = new GameConfiguration(new GameConfiguration.UserInformation(new Session((String)((OptionSpec)defaultsTo3).value(parse), s6, (String)((OptionSpec)required).value(parse), (String)((OptionSpec)defaultsTo8).value(parse)), propertyMap, propertyMap2, no_PROXY), new GameConfiguration.DisplayInformation(intValue, intValue2, has, has2), new GameConfiguration.FolderInformation(file, file5, file3, s7), new GameConfiguration.GameInformation(has3, s4), new GameConfiguration.ServerInformation((String)parse.valueOf((OptionSpec)withRequiredArg), (int)parse.valueOf((OptionSpec)defaultsTo)));
        Runtime.getRuntime().addShutdownHook(new Thread(Main.I[0xB9 ^ 0x9D]) {
            @Override
            public void run() {
                Minecraft.stopIntegratedServer();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        Thread.currentThread().setName(Main.I[0x9 ^ 0x2C]);
        new Minecraft(gameConfiguration).run();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x4B ^ 0x6D])["".length()] = I("\u001f\u00144.b\u001b\u00106a<\u0007\u0010$*><%4{\u001f\u0001\u0014!$", "uuBOL");
        Main.I[" ".length()] = I("\u0002\u0014\"\u0011", "vfWtz");
        Main.I["  ".length()] = I("\u0003<\u001a&", "gYwIg");
        Main.I["   ".length()] = I("?'\u001f\u001d\u0001: \u0016\u0014\u001c", "YRsqr");
        Main.I[0x79 ^ 0x7D] = I("0\u00120\b\u001b\u0014\u0016\u0010\u0019\u0002<\b&", "SzUkp");
        Main.I[0x5D ^ 0x58] = I("!\u0012\u001e\u001d\u0016 ", "Rwlks");
        Main.I[0x47 ^ 0x41] = I("\u001b\u001b\u0007\u001f", "ktukO");
        Main.I[0x93 ^ 0x94] = I("\u001438\u00172\u001a ", "sRUrv");
        Main.I[0x33 ^ 0x3B] = I("I", "gBurH");
        Main.I[0xA7 ^ 0xAE] = I("9>!\u00011+\t;\u0016", "XMRdE");
        Main.I[0x64 ^ 0x6E] = I("0\"<\u001a\u00100$*%\u0004!,\u000b\u001c\u0017", "BGOue");
        Main.I[0xCD ^ 0xC6] = I("\u0014\u0010\u0017,2,\r\u000b ", "dbxTK");
        Main.I[0x94 ^ 0x98] = I("=!\u001f1<\u001d<\u0002=", "MSpIE");
        Main.I[0x4A ^ 0x47] = I("BsVu", "zCnEq");
        Main.I[0x8E ^ 0x80] = I("\u001f9\u0006\u0019=:8\f\u0013", "oKiaD");
        Main.I[0xB1 ^ 0xBE] = I("\n+-\u0015?*81\u001e", "zYBmF");
        Main.I[0x0 ^ 0x10] = I("\u001c\"\b\n8\b<\b", "iQmxV");
        Main.I[0xBF ^ 0xAE] = I("<\n\u00042\u0003\u001e", "lfeKf");
        Main.I[0x4B ^ 0x59] = I("\u0012%=*", "gPTNC");
        Main.I[0x63 ^ 0x70] = I("\u0010\u001b2\u0011\u0016\u0002,>\u001f\u0000\u001f", "qxQte");
        Main.I[0x14 ^ 0x0] = I("\u0015\u0017\u0018\u0016\u001f\f\u001c", "crjev");
        Main.I[0x9A ^ 0x8F] = I("\u001f?\u0000\u0006\r", "hVdre");
        Main.I[0xB8 ^ 0xAE] = I("85(\u0015-$", "PPArE");
        Main.I[0x29 ^ 0x3E] = I("2\u0019\u000e\u0001\u00045\u0005\u001b\u0016&3\u0003\u000e\u0000", "GjksT");
        Main.I[0x6C ^ 0x74] = I("78", "LELfL");
        Main.I[0xAE ^ 0xB7] = I("%\u001d\u000b\u001e\u00049\n4\n\u0002%\n\u0016\f\u00040\u001c", "Uodxm");
        Main.I[0x42 ^ 0x58] = I(",\u0010", "WmheO");
        Main.I[0x73 ^ 0x68] = I("$\u0003\u0016*\f\f\u001e\u0001*\u0000", "EpeOx");
        Main.I[0xD9 ^ 0xC5] = I(";%6\u0001%7&6", "NVSsq");
        Main.I[0x6D ^ 0x70] = I("\t\u00000 7\u001c", "eeWAT");
        Main.I[0xA8 ^ 0xB6] = I("4\u001d*\u001d%\u0012\u0006\"\u00010W\u001b \u0003&\u0005\u0017#M(\u0005\u00152\u0000,\u0019\u00064Wi", "wrGmI");
        Main.I[0x79 ^ 0x66] = I("'%6\u0015\u0010\"\"?\u001c\r", "APZyc");
        Main.I[0x12 ^ 0x32] = I("\u0019?!\u0015;=;\u0001\u0004\"\u0015%7", "zWDvP");
        Main.I[0x49 ^ 0x68] = I("\u001d \u0007\u001e", "yEjqe");
        Main.I[0x96 ^ 0xB4] = I("\u0012\u0001*\u0016\u0003\u0000]", "srYsw");
        Main.I[0x19 ^ 0x3A] = I("\u0010\r&\u00063\u0010\u000b0\u0019'\u0001\u0003&F", "bhUiF");
        Main.I[0x4B ^ 0x6F] = I("\u001a8\n\u0011\u0007-t0\u001c\u001c-0\f\u0003\u0007y\u0000\u000b\u0006\f80", "YTcti");
        Main.I[0x70 ^ 0x55] = I("+\u001a<\u0017\u001c\u001cV!\u001a\u0000\r\u00171", "hvUrr");
    }
}
