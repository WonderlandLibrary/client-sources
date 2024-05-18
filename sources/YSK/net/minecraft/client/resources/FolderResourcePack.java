package net.minecraft.client.resources;

import com.google.common.collect.*;
import org.apache.commons.io.filefilter.*;
import java.util.*;
import java.io.*;

public class FolderResourcePack extends AbstractResourcePack
{
    private static final String[] I;
    
    @Override
    protected boolean hasResourceName(final String s) {
        return new File(this.resourcePackFile, s).isFile();
    }
    
    public FolderResourcePack(final File file) {
        super(file);
    }
    
    @Override
    public Set<String> getResourceDomains() {
        final HashSet hashSet = Sets.newHashSet();
        final File file = new File(this.resourcePackFile, FolderResourcePack.I["".length()]);
        if (file.isDirectory()) {
            final File[] listFiles;
            final int length = (listFiles = file.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)).length;
            int i = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (i < length) {
                final String relativeName = AbstractResourcePack.getRelativeName(file, listFiles[i]);
                if (!relativeName.equals(relativeName.toLowerCase())) {
                    this.logNameNotLowercase(relativeName);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    hashSet.add(relativeName.substring("".length(), relativeName.length() - " ".length()));
                }
                ++i;
            }
        }
        return (Set<String>)hashSet;
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("*%42=8y", "KVGWI");
    }
    
    @Override
    protected InputStream getInputStreamByName(final String s) throws IOException {
        return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, s)));
    }
}
