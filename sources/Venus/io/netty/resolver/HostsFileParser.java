/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver;

import io.netty.resolver.HostsFileEntries;
import io.netty.util.NetUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

public final class HostsFileParser {
    private static final String WINDOWS_DEFAULT_SYSTEM_ROOT = "C:\\Windows";
    private static final String WINDOWS_HOSTS_FILE_RELATIVE_PATH = "\\system32\\drivers\\etc\\hosts";
    private static final String X_PLATFORMS_HOSTS_FILE_PATH = "/etc/hosts";
    private static final Pattern WHITESPACES = Pattern.compile("[ \t]+");
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(HostsFileParser.class);

    private static File locateHostsFile() {
        File file;
        if (PlatformDependent.isWindows()) {
            file = new File(System.getenv("SystemRoot") + WINDOWS_HOSTS_FILE_RELATIVE_PATH);
            if (!file.exists()) {
                file = new File("C:\\Windows\\system32\\drivers\\etc\\hosts");
            }
        } else {
            file = new File(X_PLATFORMS_HOSTS_FILE_PATH);
        }
        return file;
    }

    public static HostsFileEntries parseSilently() {
        File file = HostsFileParser.locateHostsFile();
        try {
            return HostsFileParser.parse(file);
        } catch (IOException iOException) {
            logger.warn("Failed to load and parse hosts file at " + file.getPath(), iOException);
            return HostsFileEntries.EMPTY;
        }
    }

    public static HostsFileEntries parse() throws IOException {
        return HostsFileParser.parse(HostsFileParser.locateHostsFile());
    }

    public static HostsFileEntries parse(File file) throws IOException {
        ObjectUtil.checkNotNull(file, "file");
        if (file.exists() && file.isFile()) {
            return HostsFileParser.parse(new BufferedReader(new FileReader(file)));
        }
        return HostsFileEntries.EMPTY;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static HostsFileEntries parse(Reader reader) throws IOException {
        ObjectUtil.checkNotNull(reader, "reader");
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String string;
            HashMap<String, Inet4Address> hashMap = new HashMap<String, Inet4Address>();
            HashMap<String, Inet6Address> hashMap2 = new HashMap<String, Inet6Address>();
            while ((string = bufferedReader.readLine()) != null) {
                String string2;
                int n = string.indexOf(35);
                if (n != -1) {
                    string = string.substring(0, n);
                }
                if ((string = string.trim()).isEmpty()) continue;
                ArrayList<String> arrayList = new ArrayList<String>();
                Object[] objectArray = WHITESPACES.split(string);
                int n2 = objectArray.length;
                for (int i = 0; i < n2; ++i) {
                    string2 = objectArray[i];
                    if (string2.isEmpty()) continue;
                    arrayList.add(string2);
                }
                if (arrayList.size() < 2 || (objectArray = (Object[])NetUtil.createByteArrayFromIpAddressString((String)arrayList.get(0))) == null) continue;
                for (n2 = 1; n2 < arrayList.size(); ++n2) {
                    InetAddress inetAddress;
                    String string3 = (String)arrayList.get(n2);
                    string2 = string3.toLowerCase(Locale.ENGLISH);
                    InetAddress inetAddress2 = InetAddress.getByAddress(string3, (byte[])objectArray);
                    if (inetAddress2 instanceof Inet4Address) {
                        inetAddress = hashMap.put(string2, (Inet4Address)inetAddress2);
                        if (inetAddress == null) continue;
                        hashMap.put(string2, (Inet4Address)inetAddress);
                        continue;
                    }
                    inetAddress = hashMap2.put(string2, (Inet6Address)inetAddress2);
                    if (inetAddress == null) continue;
                    hashMap2.put(string2, (Inet6Address)inetAddress);
                }
            }
            HostsFileEntries hostsFileEntries = hashMap.isEmpty() && hashMap2.isEmpty() ? HostsFileEntries.EMPTY : new HostsFileEntries(hashMap, hashMap2);
            return hostsFileEntries;
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException iOException) {
                logger.warn("Failed to close a reader", iOException);
            }
        }
    }

    private HostsFileParser() {
    }
}

