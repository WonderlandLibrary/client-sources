package net.minecraft.world.storage;

import java.io.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class SaveFormatOld implements ISaveFormat
{
    protected final File savesDirectory;
    private static final String[] I;
    private static final Logger logger;
    
    @Override
    public void renameWorld(final String s, final String s2) {
        final File file = new File(this.savesDirectory, s);
        if (file.exists()) {
            final File file2 = new File(file, SaveFormatOld.I[0x58 ^ 0x51]);
            if (file2.exists()) {
                try {
                    final NBTTagCompound compressed = CompressedStreamTools.readCompressed(new FileInputStream(file2));
                    compressed.getCompoundTag(SaveFormatOld.I[0x33 ^ 0x39]).setString(SaveFormatOld.I[0x26 ^ 0x2D], s2);
                    CompressedStreamTools.writeCompressed(compressed, new FileOutputStream(file2));
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public boolean func_154334_a(final String s) {
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0xB4 ^ 0xA0])["".length()] = I("\u0006/0D-&19\u0005\u001f", "ICTdk");
        SaveFormatOld.I[" ".length()] = I("\u001c;\u0002(\u000e", "KTpDj");
        SaveFormatOld.I["  ".length()] = I("", "obufL");
        SaveFormatOld.I["   ".length()] = I("\u0018 <\u00154Z!+\u0004", "tEJpX");
        SaveFormatOld.I[0x3D ^ 0x39] = I("\"%\u001d8", "fDiYE");
        SaveFormatOld.I[0x45 ^ 0x40] = I("3:\u000e\u0011:\u0002+\u0002\u001aj\u0004'\f\u0010#\u0018%M", "vBmtJ");
        SaveFormatOld.I[0xC1 ^ 0xC7] = I("\u0006!\u00172#D \u0000#\u0010\u0005(\u0005", "jDaWO");
        SaveFormatOld.I[0x2A ^ 0x2D] = I("2\u0014\u001f'", "vukFZ");
        SaveFormatOld.I[0xC ^ 0x4] = I(".\t\u0019\u0015;\u001f\u0018\u0015\u001ek\u0019\u0014\u001b\u0014\"\u0005\u0016Z", "kqzpK");
        SaveFormatOld.I[0x44 ^ 0x4D] = I("\u000e,,+\u001eL-;:", "bIZNr");
        SaveFormatOld.I[0x47 ^ 0x4D] = I("\f\u000b,#", "HjXBO");
        SaveFormatOld.I[0x71 ^ 0x7A] = I("+2$\u0006<)6?\u0006", "gWRcP");
        SaveFormatOld.I[0x44 ^ 0x48] = I("\"\u001f\u0018 \u001e\u000fW\u0019l\u0017\u0000\u001b\bl\u0014\u0004\u0007M \u001f\u0017\u0015\u0001", "apmLz");
        SaveFormatOld.I[0x43 ^ 0x4E] = I("\u001d\u001c\u0003\u001500\u0017\bP(<\u000f\n\u001cd", "YyopD");
        SaveFormatOld.I[0x4B ^ 0x45] = I("5\u0007.\u0016(\u0004\u0007z", "tsZsE");
        SaveFormatOld.I[0x45 ^ 0x4A] = I("~xV", "PVxCh");
        SaveFormatOld.I[0x64 ^ 0x74] = I(";\u001e\u001d8$\r\u0015\u001d>!\u001b\u001cN$)N\u0014\u000b!\"\u001a\u0019\u0000*g\r\u001f\u00009\"\u0000\u0004\u001dc", "npnMG");
        SaveFormatOld.I[0x3A ^ 0x2B] = I("!$\n!\u0006\f/\u0001d", "eAfDr");
        SaveFormatOld.I[0x40 ^ 0x52] = I("\n)$\t\u000f'a%E\u000f,*4\u0011\u000ei\"8\u0017\u000e*2>\u0017\u0012i", "IFQek");
        SaveFormatOld.I[0x64 ^ 0x77] = I("'\t\u001b=\u001d\nA\u001aq\u001d\u0001\n\u000b%\u001cD\u0000\u0007=\u001cD", "dfnQy");
    }
    
    @Override
    public String getName() {
        return SaveFormatOld.I["".length()];
    }
    
    @Override
    public WorldInfo getWorldInfo(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (!file.exists()) {
            return null;
        }
        final File file2 = new File(file, SaveFormatOld.I["   ".length()]);
        if (file2.exists()) {
            try {
                return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file2)).getCompoundTag(SaveFormatOld.I[0x4B ^ 0x4F]));
            }
            catch (Exception ex) {
                SaveFormatOld.logger.error(SaveFormatOld.I[0x6E ^ 0x6B] + file2, (Throwable)ex);
            }
        }
        final File file3 = new File(file, SaveFormatOld.I[0x92 ^ 0x94]);
        if (file3.exists()) {
            try {
                return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file3)).getCompoundTag(SaveFormatOld.I[0x92 ^ 0x95]));
            }
            catch (Exception ex2) {
                SaveFormatOld.logger.error(SaveFormatOld.I[0x94 ^ 0x9C] + file3, (Throwable)ex2);
            }
        }
        return null;
    }
    
    public SaveFormatOld(final File savesDirectory) {
        if (!savesDirectory.exists()) {
            savesDirectory.mkdirs();
        }
        this.savesDirectory = savesDirectory;
    }
    
    @Override
    public ISaveHandler getSaveLoader(final String s, final boolean b) {
        return new SaveHandler(this.savesDirectory, s, b);
    }
    
    @Override
    public boolean canLoadWorld(final String s) {
        return new File(this.savesDirectory, s).isDirectory();
    }
    
    @Override
    public boolean deleteWorldDirectory(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (!file.exists()) {
            return " ".length() != 0;
        }
        SaveFormatOld.logger.info(SaveFormatOld.I[0x8B ^ 0x86] + s);
        int i = " ".length();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (i <= (0x84 ^ 0x81)) {
            SaveFormatOld.logger.info(SaveFormatOld.I[0x1 ^ 0xF] + i + SaveFormatOld.I[0xC9 ^ 0xC6]);
            if (deleteFiles(file.listFiles())) {
                "".length();
                if (3 < 1) {
                    throw null;
                }
                break;
            }
            else {
                SaveFormatOld.logger.warn(SaveFormatOld.I[0x1F ^ 0xF]);
                if (i < (0xB ^ 0xE)) {
                    try {
                        Thread.sleep(500L);
                        "".length();
                        if (2 >= 4) {
                            throw null;
                        }
                    }
                    catch (InterruptedException ex) {}
                }
                ++i;
            }
        }
        return file.delete();
    }
    
    protected static boolean deleteFiles(final File[] array) {
        int i = "".length();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i < array.length) {
            final File file = array[i];
            SaveFormatOld.logger.debug(SaveFormatOld.I[0xB ^ 0x1A] + file);
            if (file.isDirectory() && !deleteFiles(file.listFiles())) {
                SaveFormatOld.logger.warn(SaveFormatOld.I[0x8B ^ 0x99] + file);
                return "".length() != 0;
            }
            if (!file.delete()) {
                SaveFormatOld.logger.warn(SaveFormatOld.I[0x78 ^ 0x6B] + file);
                return "".length() != 0;
            }
            ++i;
        }
        return " ".length() != 0;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void flushCache() {
    }
    
    @Override
    public boolean func_154335_d(final String s) {
        final File file = new File(this.savesDirectory, s);
        if (file.exists()) {
            return "".length() != 0;
        }
        try {
            file.mkdir();
            file.delete();
            return " ".length() != 0;
        }
        catch (Throwable t) {
            SaveFormatOld.logger.warn(SaveFormatOld.I[0x92 ^ 0x9E], t);
            return "".length() != 0;
        }
    }
    
    @Override
    public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i < (0x5 ^ 0x0)) {
            final String string = SaveFormatOld.I[" ".length()] + (i + " ".length());
            final WorldInfo worldInfo = this.getWorldInfo(string);
            if (worldInfo != null) {
                arrayList.add(new SaveFormatComparator(string, SaveFormatOld.I["  ".length()], worldInfo.getLastTimePlayed(), worldInfo.getSizeOnDisk(), worldInfo.getGameType(), (boolean)("".length() != 0), worldInfo.isHardcoreModeEnabled(), worldInfo.areCommandsAllowed()));
            }
            ++i;
        }
        return (List<SaveFormatComparator>)arrayList;
    }
    
    @Override
    public boolean isOldMapFormat(final String s) {
        return "".length() != 0;
    }
    
    @Override
    public boolean convertMapFormat(final String s, final IProgressUpdate progressUpdate) {
        return "".length() != 0;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
}
