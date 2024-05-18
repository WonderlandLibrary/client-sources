package net.minecraft.client.resources;

import com.google.common.base.*;
import java.util.zip.*;
import java.io.*;
import com.google.common.collect.*;
import java.util.*;

public class FileResourcePack extends AbstractResourcePack implements Closeable
{
    private ZipFile resourcePackZipFile;
    private static final String[] I;
    public static final Splitter entryNameSplitter;
    
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(",%'+\u001b>y", "MVTNo");
    }
    
    @Override
    protected InputStream getInputStreamByName(final String s) throws IOException {
        final ZipFile resourcePackZipFile = this.getResourcePackZipFile();
        final ZipEntry entry = resourcePackZipFile.getEntry(s);
        if (entry == null) {
            throw new ResourcePackFileNotFoundException(this.resourcePackFile, s);
        }
        return resourcePackZipFile.getInputStream(entry);
    }
    
    public FileResourcePack(final File file) {
        super(file);
    }
    
    @Override
    public Set<String> getResourceDomains() {
        ZipFile resourcePackZipFile;
        try {
            resourcePackZipFile = this.getResourcePackZipFile();
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        catch (IOException ex) {
            return Collections.emptySet();
        }
        final Enumeration<? extends ZipEntry> entries = resourcePackZipFile.entries();
        final HashSet hashSet = Sets.newHashSet();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (entries.hasMoreElements()) {
            final String name = ((ZipEntry)entries.nextElement()).getName();
            if (name.startsWith(FileResourcePack.I["".length()])) {
                final ArrayList arrayList = Lists.newArrayList(FileResourcePack.entryNameSplitter.split((CharSequence)name));
                if (arrayList.size() <= " ".length()) {
                    continue;
                }
                final String s = arrayList.get(" ".length());
                if (!s.equals(s.toLowerCase())) {
                    this.logNameNotLowercase(s);
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    continue;
                }
                else {
                    hashSet.add(s);
                }
            }
        }
        return (Set<String>)hashSet;
    }
    
    private ZipFile getResourcePackZipFile() throws IOException {
        if (this.resourcePackZipFile == null) {
            this.resourcePackZipFile = new ZipFile(this.resourcePackFile);
        }
        return this.resourcePackZipFile;
    }
    
    @Override
    public void close() throws IOException {
        if (this.resourcePackZipFile != null) {
            this.resourcePackZipFile.close();
            this.resourcePackZipFile = null;
        }
    }
    
    public boolean hasResourceName(final String s) {
        try {
            if (this.getResourcePackZipFile().getEntry(s) != null) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        catch (IOException ex) {
            return "".length() != 0;
        }
    }
    
    static {
        I();
        entryNameSplitter = Splitter.on((char)(0xBF ^ 0x90)).omitEmptyStrings().limit("   ".length());
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
