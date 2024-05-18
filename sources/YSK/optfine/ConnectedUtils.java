package optfine;

import java.util.zip.*;
import java.io.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.util.*;

public class ConnectedUtils
{
    private static final String[] I;
    
    public static int getAverage(final int[] array) {
        if (array.length <= 0) {
            return "".length();
        }
        int length = "".length();
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < array.length) {
            length += array[i];
            ++i;
        }
        return length / array.length;
    }
    
    private static String[] collectFilesZIP(final File file, final String s, final String s2) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s3 = ConnectedUtils.I["   ".length()];
        try {
            final ZipFile zipFile = new ZipFile(file);
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();
            "".length();
            if (3 == 0) {
                throw null;
            }
            while (entries.hasMoreElements()) {
                final String name = ((ZipEntry)entries.nextElement()).getName();
                if (name.startsWith(s3)) {
                    final String substring = name.substring(s3.length());
                    if (!substring.startsWith(s) || !substring.endsWith(s2)) {
                        continue;
                    }
                    list.add(substring);
                }
            }
            zipFile.close();
            return list.toArray(new String[list.size()]);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return new String["".length()];
        }
    }
    
    private static void I() {
        (I = new String[0xB3 ^ 0xB7])["".length()] = I("", "CAiXJ");
        ConnectedUtils.I[" ".length()] = I("\u0006\u0007\u0005=\u0019\u0014[\u001b1\u0003\u0002\u0017\u00049\u000b\u0013[", "gtvXm");
        ConnectedUtils.I["  ".length()] = I("y", "VwDxB");
        ConnectedUtils.I["   ".length()] = I("\u000e$2$'\u001cx,(=\n43 5\u001bx", "oWAAS");
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static String[] collectFiles(final IResourcePack resourcePack, final String s, final String s2, final String[] array) {
        if (resourcePack instanceof DefaultResourcePack) {
            return collectFilesFixed(resourcePack, array);
        }
        if (!(resourcePack instanceof AbstractResourcePack)) {
            return new String["".length()];
        }
        final File resourcePackFile = ResourceUtils.getResourcePackFile((AbstractResourcePack)resourcePack);
        String[] array2;
        if (resourcePackFile == null) {
            array2 = new String["".length()];
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (resourcePackFile.isDirectory()) {
            array2 = collectFilesFolder(resourcePackFile, ConnectedUtils.I["".length()], s, s2);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else if (resourcePackFile.isFile()) {
            array2 = collectFilesZIP(resourcePackFile, s, s2);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            array2 = new String["".length()];
        }
        return array2;
    }
    
    private static String[] collectFilesFolder(final File file, final String s, final String s2, final String s3) {
        final ArrayList<String> list = new ArrayList<String>();
        final String s4 = ConnectedUtils.I[" ".length()];
        final File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return new String["".length()];
        }
        int i = "".length();
        "".length();
        if (1 < -1) {
            throw null;
        }
        while (i < listFiles.length) {
            final File file2 = listFiles[i];
            if (file2.isFile()) {
                final String string = String.valueOf(s) + file2.getName();
                if (string.startsWith(s4)) {
                    final String substring = string.substring(s4.length());
                    if (substring.startsWith(s2) && substring.endsWith(s3)) {
                        list.add(substring);
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                    }
                }
            }
            else if (file2.isDirectory()) {
                final String[] collectFilesFolder = collectFilesFolder(file2, String.valueOf(s) + file2.getName() + ConnectedUtils.I["  ".length()], s2, s3);
                int j = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                while (j < collectFilesFolder.length) {
                    list.add(collectFilesFolder[j]);
                    ++j;
                }
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }
    
    private static String[] collectFilesFixed(final IResourcePack resourcePack, final String[] array) {
        if (array == null) {
            return new String["".length()];
        }
        final ArrayList<String> list = new ArrayList<String>();
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < array.length) {
            final String s = array[i];
            if (resourcePack.resourceExists(new ResourceLocation(s))) {
                list.add(s);
            }
            ++i;
        }
        return list.toArray(new String[list.size()]);
    }
    
    static {
        I();
    }
}
