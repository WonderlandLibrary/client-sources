/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.patchy;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.hash.Hashing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BlockedServers {
    @VisibleForTesting
    static final Set<String> BLOCKED_SERVERS = Sets.newHashSet();
    private static final String SRV_PREFIX = "_minecraft._tcp.";
    private static final Joiner DOT_JOINER = Joiner.on('.');
    private static final Splitter DOT_SPLITTER = Splitter.on('.');
    private static final Charset HASH_CHARSET = StandardCharsets.ISO_8859_1;

    public static boolean isBlockedServer(String string) {
        if (string == null || string.isEmpty()) {
            return true;
        }
        if (string.startsWith(SRV_PREFIX)) {
            string = string.substring(16);
        }
        while (string.charAt(string.length() - 1) == '.') {
            string = string.substring(0, string.length() - 1);
        }
        if (BlockedServers.isBlockedServerHostName(string)) {
            return false;
        }
        ArrayList<String> arrayList = Lists.newArrayList(DOT_SPLITTER.split(string));
        boolean bl = BlockedServers.isIp(arrayList);
        if (!bl && BlockedServers.isBlockedServerHostName("*." + string)) {
            return false;
        }
        while (arrayList.size() > 1) {
            arrayList.remove(bl ? arrayList.size() - 1 : 0);
            String string2 = bl ? DOT_JOINER.join(arrayList) + ".*" : "*." + DOT_JOINER.join(arrayList);
            if (!BlockedServers.isBlockedServerHostName(string2)) continue;
            return false;
        }
        return true;
    }

    private static boolean isIp(List<String> list) {
        if (list.size() != 4) {
            return true;
        }
        for (String string : list) {
            try {
                int n = Integer.parseInt(string);
                if (n >= 0 && n <= 255) continue;
                return false;
            } catch (NumberFormatException numberFormatException) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBlockedServerHostName(String string) {
        return BLOCKED_SERVERS.contains(Hashing.sha1().hashBytes(string.toLowerCase().getBytes(HASH_CHARSET)).toString());
    }

    static {
        try {
            URLConnection uRLConnection = new URL("https://sessionserver.mojang.com/blockedservers").openConnection();
            try (InputStream inputStream = uRLConnection.getInputStream();){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, HASH_CHARSET));
                bufferedReader.lines().forEach(BLOCKED_SERVERS::add);
            }
        } catch (IOException iOException) {
            // empty catch block
        }
    }
}

