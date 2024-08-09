/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.tar;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.apache.commons.compress.archivers.tar.TarUtils;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.utils.ArchiveUtils;

public class TarArchiveEntry
implements TarConstants,
ArchiveEntry {
    private String name = "";
    private int mode;
    private int userId = 0;
    private int groupId = 0;
    private long size = 0L;
    private long modTime;
    private boolean checkSumOK;
    private byte linkFlag;
    private String linkName = "";
    private String magic = "ustar\u0000";
    private String version = "00";
    private String userName;
    private String groupName = "";
    private int devMajor = 0;
    private int devMinor = 0;
    private boolean isExtended;
    private long realSize;
    private final File file;
    public static final int MAX_NAMELEN = 31;
    public static final int DEFAULT_DIR_MODE = 16877;
    public static final int DEFAULT_FILE_MODE = 33188;
    public static final int MILLIS_PER_SECOND = 1000;

    private TarArchiveEntry() {
        String string = System.getProperty("user.name", "");
        if (string.length() > 31) {
            string = string.substring(0, 31);
        }
        this.userName = string;
        this.file = null;
    }

    public TarArchiveEntry(String string) {
        this(string, false);
    }

    public TarArchiveEntry(String string, boolean bl) {
        this();
        string = TarArchiveEntry.normalizeFileName(string, bl);
        boolean bl2 = string.endsWith("/");
        this.name = string;
        this.mode = bl2 ? 16877 : 33188;
        this.linkFlag = (byte)(bl2 ? 53 : 48);
        this.modTime = new Date().getTime() / 1000L;
        this.userName = "";
    }

    public TarArchiveEntry(String string, byte by) {
        this(string, by, false);
    }

    public TarArchiveEntry(String string, byte by, boolean bl) {
        this(string, bl);
        this.linkFlag = by;
        if (by == 76) {
            this.magic = "ustar ";
            this.version = " \u0000";
        }
    }

    public TarArchiveEntry(File file) {
        this(file, TarArchiveEntry.normalizeFileName(file.getPath(), false));
    }

    public TarArchiveEntry(File file, String string) {
        this.file = file;
        if (file.isDirectory()) {
            this.mode = 16877;
            this.linkFlag = (byte)53;
            int n = string.length();
            this.name = n == 0 || string.charAt(n - 1) != '/' ? string + "/" : string;
        } else {
            this.mode = 33188;
            this.linkFlag = (byte)48;
            this.size = file.length();
            this.name = string;
        }
        this.modTime = file.lastModified() / 1000L;
        this.userName = "";
    }

    public TarArchiveEntry(byte[] byArray) {
        this();
        this.parseTarHeader(byArray);
    }

    public TarArchiveEntry(byte[] byArray, ZipEncoding zipEncoding) throws IOException {
        this();
        this.parseTarHeader(byArray, zipEncoding);
    }

    public boolean equals(TarArchiveEntry tarArchiveEntry) {
        return this.getName().equals(tarArchiveEntry.getName());
    }

    public boolean equals(Object object) {
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        return this.equals((TarArchiveEntry)object);
    }

    public int hashCode() {
        return this.getName().hashCode();
    }

    public boolean isDescendent(TarArchiveEntry tarArchiveEntry) {
        return tarArchiveEntry.getName().startsWith(this.getName());
    }

    public String getName() {
        return this.name.toString();
    }

    public void setName(String string) {
        this.name = TarArchiveEntry.normalizeFileName(string, false);
    }

    public void setMode(int n) {
        this.mode = n;
    }

    public String getLinkName() {
        return this.linkName.toString();
    }

    public void setLinkName(String string) {
        this.linkName = string;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int n) {
        this.userId = n;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public void setGroupId(int n) {
        this.groupId = n;
    }

    public String getUserName() {
        return this.userName.toString();
    }

    public void setUserName(String string) {
        this.userName = string;
    }

    public String getGroupName() {
        return this.groupName.toString();
    }

    public void setGroupName(String string) {
        this.groupName = string;
    }

    public void setIds(int n, int n2) {
        this.setUserId(n);
        this.setGroupId(n2);
    }

    public void setNames(String string, String string2) {
        this.setUserName(string);
        this.setGroupName(string2);
    }

    public void setModTime(long l) {
        this.modTime = l / 1000L;
    }

    public void setModTime(Date date) {
        this.modTime = date.getTime() / 1000L;
    }

    public Date getModTime() {
        return new Date(this.modTime * 1000L);
    }

    public Date getLastModifiedDate() {
        return this.getModTime();
    }

    public boolean isCheckSumOK() {
        return this.checkSumOK;
    }

    public File getFile() {
        return this.file;
    }

    public int getMode() {
        return this.mode;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Size is out of range: " + l);
        }
        this.size = l;
    }

    public int getDevMajor() {
        return this.devMajor;
    }

    public void setDevMajor(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Major device number is out of range: " + n);
        }
        this.devMajor = n;
    }

    public int getDevMinor() {
        return this.devMinor;
    }

    public void setDevMinor(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Minor device number is out of range: " + n);
        }
        this.devMinor = n;
    }

    public boolean isExtended() {
        return this.isExtended;
    }

    public long getRealSize() {
        return this.realSize;
    }

    public boolean isGNUSparse() {
        return this.linkFlag == 83;
    }

    public boolean isGNULongLinkEntry() {
        return this.linkFlag == 75 && this.name.equals("././@LongLink");
    }

    public boolean isGNULongNameEntry() {
        return this.linkFlag == 76 && this.name.equals("././@LongLink");
    }

    public boolean isPaxHeader() {
        return this.linkFlag == 120 || this.linkFlag == 88;
    }

    public boolean isGlobalPaxHeader() {
        return this.linkFlag == 103;
    }

    public boolean isDirectory() {
        if (this.file != null) {
            return this.file.isDirectory();
        }
        if (this.linkFlag == 53) {
            return false;
        }
        return !this.getName().endsWith("/");
    }

    public boolean isFile() {
        if (this.file != null) {
            return this.file.isFile();
        }
        if (this.linkFlag == 0 || this.linkFlag == 48) {
            return false;
        }
        return !this.getName().endsWith("/");
    }

    public boolean isSymbolicLink() {
        return this.linkFlag == 50;
    }

    public boolean isLink() {
        return this.linkFlag == 49;
    }

    public boolean isCharacterDevice() {
        return this.linkFlag == 51;
    }

    public boolean isBlockDevice() {
        return this.linkFlag == 52;
    }

    public boolean isFIFO() {
        return this.linkFlag == 54;
    }

    public TarArchiveEntry[] getDirectoryEntries() {
        if (this.file == null || !this.file.isDirectory()) {
            return new TarArchiveEntry[0];
        }
        String[] stringArray = this.file.list();
        TarArchiveEntry[] tarArchiveEntryArray = new TarArchiveEntry[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            tarArchiveEntryArray[i] = new TarArchiveEntry(new File(this.file, stringArray[i]));
        }
        return tarArchiveEntryArray;
    }

    public void writeEntryHeader(byte[] byArray) {
        try {
            this.writeEntryHeader(byArray, TarUtils.DEFAULT_ENCODING, true);
        } catch (IOException iOException) {
            try {
                this.writeEntryHeader(byArray, TarUtils.FALLBACK_ENCODING, true);
            } catch (IOException iOException2) {
                throw new RuntimeException(iOException2);
            }
        }
    }

    public void writeEntryHeader(byte[] byArray, ZipEncoding zipEncoding, boolean bl) throws IOException {
        int n = 0;
        n = TarUtils.formatNameBytes(this.name, byArray, n, 100, zipEncoding);
        n = this.writeEntryHeaderField(this.mode, byArray, n, 8, bl);
        n = this.writeEntryHeaderField(this.userId, byArray, n, 8, bl);
        n = this.writeEntryHeaderField(this.groupId, byArray, n, 8, bl);
        n = this.writeEntryHeaderField(this.size, byArray, n, 12, bl);
        int n2 = n = this.writeEntryHeaderField(this.modTime, byArray, n, 12, bl);
        for (int i = 0; i < 8; ++i) {
            byArray[n++] = 32;
        }
        byArray[n++] = this.linkFlag;
        n = TarUtils.formatNameBytes(this.linkName, byArray, n, 100, zipEncoding);
        n = TarUtils.formatNameBytes(this.magic, byArray, n, 6);
        n = TarUtils.formatNameBytes(this.version, byArray, n, 2);
        n = TarUtils.formatNameBytes(this.userName, byArray, n, 32, zipEncoding);
        n = TarUtils.formatNameBytes(this.groupName, byArray, n, 32, zipEncoding);
        n = this.writeEntryHeaderField(this.devMajor, byArray, n, 8, bl);
        n = this.writeEntryHeaderField(this.devMinor, byArray, n, 8, bl);
        while (n < byArray.length) {
            byArray[n++] = 0;
        }
        long l = TarUtils.computeCheckSum(byArray);
        TarUtils.formatCheckSumOctalBytes(l, byArray, n2, 8);
    }

    private int writeEntryHeaderField(long l, byte[] byArray, int n, int n2, boolean bl) {
        if (!(bl || l >= 0L && l < 1L << 3 * (n2 - 1))) {
            return TarUtils.formatLongOctalBytes(0L, byArray, n, n2);
        }
        return TarUtils.formatLongOctalOrBinaryBytes(l, byArray, n, n2);
    }

    public void parseTarHeader(byte[] byArray) {
        try {
            this.parseTarHeader(byArray, TarUtils.DEFAULT_ENCODING);
        } catch (IOException iOException) {
            try {
                this.parseTarHeader(byArray, TarUtils.DEFAULT_ENCODING, true);
            } catch (IOException iOException2) {
                throw new RuntimeException(iOException2);
            }
        }
    }

    public void parseTarHeader(byte[] byArray, ZipEncoding zipEncoding) throws IOException {
        this.parseTarHeader(byArray, zipEncoding, false);
    }

    private void parseTarHeader(byte[] byArray, ZipEncoding zipEncoding, boolean bl) throws IOException {
        int n = 0;
        this.name = bl ? TarUtils.parseName(byArray, n, 100) : TarUtils.parseName(byArray, n, 100, zipEncoding);
        this.mode = (int)TarUtils.parseOctalOrBinary(byArray, n += 100, 8);
        this.userId = (int)TarUtils.parseOctalOrBinary(byArray, n += 8, 8);
        this.groupId = (int)TarUtils.parseOctalOrBinary(byArray, n += 8, 8);
        this.size = TarUtils.parseOctalOrBinary(byArray, n += 8, 12);
        this.modTime = TarUtils.parseOctalOrBinary(byArray, n += 12, 12);
        n += 12;
        this.checkSumOK = TarUtils.verifyCheckSum(byArray);
        n += 8;
        this.linkFlag = byArray[n++];
        this.linkName = bl ? TarUtils.parseName(byArray, n, 100) : TarUtils.parseName(byArray, n, 100, zipEncoding);
        this.magic = TarUtils.parseName(byArray, n += 100, 6);
        this.version = TarUtils.parseName(byArray, n += 6, 2);
        this.userName = bl ? TarUtils.parseName(byArray, n, 32) : TarUtils.parseName(byArray, n += 2, 32, zipEncoding);
        this.groupName = bl ? TarUtils.parseName(byArray, n, 32) : TarUtils.parseName(byArray, n += 32, 32, zipEncoding);
        this.devMajor = (int)TarUtils.parseOctalOrBinary(byArray, n += 32, 8);
        this.devMinor = (int)TarUtils.parseOctalOrBinary(byArray, n += 8, 8);
        n += 8;
        int n2 = this.evaluateType(byArray);
        switch (n2) {
            case 2: {
                n += 12;
                n += 12;
                n += 12;
                n += 4;
                ++n;
                this.isExtended = TarUtils.parseBoolean(byArray, n += 96);
                this.realSize = TarUtils.parseOctal(byArray, ++n, 12);
                n += 12;
                break;
            }
            default: {
                String string;
                String string2 = string = bl ? TarUtils.parseName(byArray, n, 155) : TarUtils.parseName(byArray, n, 155, zipEncoding);
                if (this.isDirectory() && !this.name.endsWith("/")) {
                    this.name = this.name + "/";
                }
                if (string.length() <= 0) break;
                this.name = string + "/" + this.name;
            }
        }
    }

    private static String normalizeFileName(String string, boolean bl) {
        String string2 = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (string2 != null) {
            int n;
            if (string2.startsWith("windows")) {
                if (string.length() > 2) {
                    n = string.charAt(0);
                    char c = string.charAt(1);
                    if (c == ':' && (n >= 97 && n <= 122 || n >= 65 && n <= 90)) {
                        string = string.substring(2);
                    }
                }
            } else if (string2.indexOf("netware") > -1 && (n = string.indexOf(58)) != -1) {
                string = string.substring(n + 1);
            }
        }
        string = string.replace(File.separatorChar, '/');
        while (!bl && string.startsWith("/")) {
            string = string.substring(1);
        }
        return string;
    }

    private int evaluateType(byte[] byArray) {
        if (ArchiveUtils.matchAsciiBuffer("ustar ", byArray, 257, 6)) {
            return 1;
        }
        if (ArchiveUtils.matchAsciiBuffer("ustar\u0000", byArray, 257, 6)) {
            return 0;
        }
        return 1;
    }
}

