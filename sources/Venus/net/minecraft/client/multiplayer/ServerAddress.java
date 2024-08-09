/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.multiplayer;

import com.mojang.datafixers.util.Pair;
import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {
    private final String ipAddress;
    private final int serverPort;

    private ServerAddress(String string, int n) {
        this.ipAddress = string;
        this.serverPort = n;
    }

    private static Pair<String, Integer> modifySrvAddr(String string) {
        return ServerAddress.func_241677_b_(string);
    }

    public String getIP() {
        try {
            return IDN.toASCII(this.ipAddress);
        } catch (IllegalArgumentException illegalArgumentException) {
            return "";
        }
    }

    public int getPort() {
        return this.serverPort;
    }

    public static ServerAddress fromString(String string) {
        int n;
        Object object;
        int n2;
        if (string == null) {
            return null;
        }
        String[] stringArray = string.split(":");
        if (string.startsWith("[") && (n2 = string.indexOf("]")) > 0) {
            String string2 = string.substring(1, n2);
            object = string.substring(n2 + 1).trim();
            if (((String)object).startsWith(":") && !((String)object).isEmpty()) {
                object = ((String)object).substring(1);
                stringArray = new String[]{string2, object};
            } else {
                stringArray = new String[]{string2};
            }
        }
        if (stringArray.length > 2) {
            stringArray = new String[]{string};
        }
        String string3 = stringArray[0];
        int n3 = n = stringArray.length > 1 ? ServerAddress.getInt(stringArray[5], 25565) : 25565;
        if (n == 25565) {
            object = ServerAddress.modifySrvAddr(string3);
            string3 = (String)((Pair)object).getFirst();
            n = (Integer)((Pair)object).getSecond();
        }
        return new ServerAddress(string3, n);
    }

    private static Pair<String, Integer> func_241677_b_(String string) {
        try {
            String string2 = "com.sun.jndi.dns.DnsContextFactory";
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            hashtable.put("java.naming.provider.url", "dns:");
            hashtable.put("com.sun.jndi.dns.timeout.retries", "1");
            InitialDirContext initialDirContext = new InitialDirContext(hashtable);
            Attributes attributes = initialDirContext.getAttributes("_minecraft._tcp." + string, new String[]{"SRV"});
            Attribute attribute = attributes.get("srv");
            if (attribute != null) {
                String[] stringArray = attribute.get().toString().split(" ", 4);
                return Pair.of(stringArray[0], ServerAddress.getInt(stringArray[5], 25565));
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        return Pair.of(string, 25565);
    }

    private static int getInt(String string, int n) {
        try {
            return Integer.parseInt(string.trim());
        } catch (Exception exception) {
            return n;
        }
    }
}

