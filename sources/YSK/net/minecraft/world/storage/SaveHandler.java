package net.minecraft.world.storage;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import org.apache.logging.log4j.*;
import net.minecraft.server.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.storage.*;

public class SaveHandler implements ISaveHandler, IPlayerFileData
{
    private final File playersDirectory;
    private final File worldDirectory;
    private final String saveDirectoryName;
    private final File mapDataDir;
    private final long initializationTime;
    private static final Logger logger;
    private static final String[] I;
    
    @Override
    public NBTTagCompound readPlayerData(final EntityPlayer entityPlayer) {
        NBTTagCompound compressed = null;
        try {
            final File file = new File(this.playersDirectory, String.valueOf(entityPlayer.getUniqueID().toString()) + SaveHandler.I[0x48 ^ 0x5F]);
            if (file.exists() && file.isFile()) {
                compressed = CompressedStreamTools.readCompressed(new FileInputStream(file));
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            SaveHandler.logger.warn(SaveHandler.I[0xB1 ^ 0xA9] + entityPlayer.getName());
        }
        if (compressed != null) {
            entityPlayer.readFromNBT(compressed);
        }
        return compressed;
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo worldInfo, final NBTTagCompound nbtTagCompound) {
        final NBTTagCompound cloneNBTCompound = worldInfo.cloneNBTCompound(nbtTagCompound);
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setTag(SaveHandler.I[0x78 ^ 0x74], cloneNBTCompound);
        try {
            final File file = new File(this.worldDirectory, SaveHandler.I[0x9A ^ 0x97]);
            final File file2 = new File(this.worldDirectory, SaveHandler.I[0xB6 ^ 0xB8]);
            final File file3 = new File(this.worldDirectory, SaveHandler.I[0xA2 ^ 0xAD]);
            CompressedStreamTools.writeCompressed(nbtTagCompound2, new FileOutputStream(file));
            if (file2.exists()) {
                file2.delete();
            }
            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }
            file.renameTo(file3);
            if (file.exists()) {
                file.delete();
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public File getWorldDirectory() {
        return this.worldDirectory;
    }
    
    @Override
    public void saveWorldInfo(final WorldInfo worldInfo) {
        final NBTTagCompound nbtTagCompound = worldInfo.getNBTTagCompound();
        final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
        nbtTagCompound2.setTag(SaveHandler.I[0x2C ^ 0x3C], nbtTagCompound);
        try {
            final File file = new File(this.worldDirectory, SaveHandler.I[0x21 ^ 0x30]);
            final File file2 = new File(this.worldDirectory, SaveHandler.I[0xD7 ^ 0xC5]);
            final File file3 = new File(this.worldDirectory, SaveHandler.I[0x9F ^ 0x8C]);
            CompressedStreamTools.writeCompressed(nbtTagCompound2, new FileOutputStream(file));
            if (file2.exists()) {
                file2.delete();
            }
            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }
            file.renameTo(file3);
            if (file.exists()) {
                file.delete();
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public String[] getAvailablePlayerDat() {
        String[] list = this.playersDirectory.list();
        if (list == null) {
            list = new String["".length()];
        }
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < list.length) {
            if (list[i].endsWith(SaveHandler.I[0xB ^ 0x12])) {
                list[i] = list[i].substring("".length(), list[i].length() - (0x73 ^ 0x77));
            }
            ++i;
        }
        return list;
    }
    
    @Override
    public IPlayerFileData getPlayerNBTManager() {
        return this;
    }
    
    @Override
    public File getMapFileFromName(final String s) {
        return new File(this.mapDataDir, String.valueOf(s) + SaveHandler.I[0xA6 ^ 0xBC]);
    }
    
    private void setSessionLock() {
        try {
            final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(this.worldDirectory, SaveHandler.I["  ".length()])));
            try {
                dataOutputStream.writeLong(this.initializationTime);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            finally {
                dataOutputStream.close();
            }
            dataOutputStream.close();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(SaveHandler.I["   ".length()]);
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public WorldInfo loadWorldInfo() {
        final File file = new File(this.worldDirectory, SaveHandler.I[0x50 ^ 0x58]);
        if (file.exists()) {
            try {
                return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file)).getCompoundTag(SaveHandler.I[0x6 ^ 0xF]));
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        final File file2 = new File(this.worldDirectory, SaveHandler.I[0x77 ^ 0x7D]);
        if (file2.exists()) {
            try {
                return new WorldInfo(CompressedStreamTools.readCompressed(new FileInputStream(file2)).getCompoundTag(SaveHandler.I[0x4 ^ 0xF]));
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
        return null;
    }
    
    public SaveHandler(final File file, final String saveDirectoryName, final boolean b) {
        this.initializationTime = MinecraftServer.getCurrentTimeMillis();
        (this.worldDirectory = new File(file, saveDirectoryName)).mkdirs();
        this.playersDirectory = new File(this.worldDirectory, SaveHandler.I["".length()]);
        (this.mapDataDir = new File(this.worldDirectory, SaveHandler.I[" ".length()])).mkdirs();
        this.saveDirectoryName = saveDirectoryName;
        if (b) {
            this.playersDirectory.mkdirs();
        }
        this.setSessionLock();
    }
    
    @Override
    public void writePlayerData(final EntityPlayer entityPlayer) {
        try {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            entityPlayer.writeToNBT(nbtTagCompound);
            final File file = new File(this.playersDirectory, String.valueOf(entityPlayer.getUniqueID().toString()) + SaveHandler.I[0x8F ^ 0x9B]);
            final File file2 = new File(this.playersDirectory, String.valueOf(entityPlayer.getUniqueID().toString()) + SaveHandler.I[0x99 ^ 0x8C]);
            CompressedStreamTools.writeCompressed(nbtTagCompound, new FileOutputStream(file));
            if (file2.exists()) {
                file2.delete();
            }
            file.renameTo(file2);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (Exception ex) {
            SaveHandler.logger.warn(SaveHandler.I[0xD1 ^ 0xC7] + entityPlayer.getName());
        }
    }
    
    @Override
    public void checkSessionLock() throws MinecraftException {
        try {
            final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(new File(this.worldDirectory, SaveHandler.I[0x28 ^ 0x2C])));
            try {
                if (dataInputStream.readLong() != this.initializationTime) {
                    throw new MinecraftException(SaveHandler.I[0x2D ^ 0x28]);
                }
            }
            finally {
                dataInputStream.close();
            }
            dataInputStream.close();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        catch (IOException ex) {
            throw new MinecraftException(SaveHandler.I[0x1D ^ 0x1B]);
        }
    }
    
    @Override
    public void flush() {
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x37 ^ 0x2C])["".length()] = I("7\"8\u000055*8\r1", "GNYyP");
        SaveHandler.I[" ".length()] = I("*\t%\u000f", "NhQnC");
        SaveHandler.I["  ".length()] = I("5\b\u0017\u001a\u0019)\u0003J\u0005\u001f%\u0006", "Fmdip");
        SaveHandler.I["   ".length()] = I("0)\u0010\u0014\u0016\u0012h\r\u0017S\u0015 \u001c\u001b\u0018V;\u001c\u000b\u0000\u001f'\u0017X\u001f\u0019+\u0012TS\u0017*\u0016\n\u0007\u001f&\u001e", "vHyxs");
        SaveHandler.I[0x56 ^ 0x52] = I("\u001850$#\u0004>m;%\b;", "kPCWJ");
        SaveHandler.I[0xF ^ 0xA] = I("\u000e\u0006\u0012P\t;\u0018\u0012P\u0013)N\u0015\u0015\u00134\tW\u0011\u00199\u000b\u0004\u0003\u001f>N\u0011\u0002\u00157N\u0016\u001e\u0015.\u0006\u0012\u0002Z6\u0001\u0014\u0011\u000e3\u0001\u0019\\Z;\f\u0018\u0002\u000e3\u0000\u0010", "Znwpz");
        SaveHandler.I[0x8 ^ 0xE] = I("\u001e\u0015#4\u0004<T>7A;\u001c/;\nx\u0007/+\u00121\u001b$x\r7\u0017!tA9\u0016%*\u00151\u001a-", "XtJXa");
        SaveHandler.I[0xA9 ^ 0xAE] = I(":\u0014\u0016r\u0017\u001d\r\u001c9t&\f\u001d 5\u0012\u001dR;'U\u0016\u001dr8\u001a\u0016\u00157&U\u000b\u0007\"$\u001a\n\u000670[", "uxrRT");
        SaveHandler.I[0x2A ^ 0x22] = I("\u00193'\u001d/[20\f", "uVQxC");
        SaveHandler.I[0x4E ^ 0x47] = I("\u0000\u0013%1", "DrQPs");
        SaveHandler.I[0x6B ^ 0x61] = I("\u0015\u001c9 \u0001W\u001d.12\u0016\u0015+", "yyOEm");
        SaveHandler.I[0x6F ^ 0x64] = I("77\u001e\u000e", "sVjov");
        SaveHandler.I[0xBF ^ 0xB3] = I("\u0010&6\u0017", "TGBvj");
        SaveHandler.I[0x80 ^ 0x8D] = I(".*\u000f\r\u0018l+\u0018\u001c+,*\u000e", "BOyht");
        SaveHandler.I[0x58 ^ 0x56] = I("$\u0001/\u0000.f\u00008\u0011\u001d'\b=", "HdYeB");
        SaveHandler.I[0x1 ^ 0xE] = I("\u0000\u000f\u000e\u0017\u000fB\u000e\u0019\u0006", "ljxrc");
        SaveHandler.I[0x4B ^ 0x5B] = I(">\u0015\u0011/", "zteNt");
        SaveHandler.I[0x93 ^ 0x82] = I("%.\u0013.\u001cg/\u0004?/'.\u0012", "IKeKp");
        SaveHandler.I[0x21 ^ 0x33] = I("\u001a#\f\u0016\"X\"\u001b\u0007\u0011\u0019*\u001e", "vFzsN");
        SaveHandler.I[0x99 ^ 0x8A] = I("(\u0007,\b:j\u0006;\u0019", "DbZmV");
        SaveHandler.I[0xB3 ^ 0xA7] = I("k\u0006\b\fV1\u000f\u0019", "Ebixx");
        SaveHandler.I[0x63 ^ 0x76] = I("t*\u0011\u0016", "ZNpbs");
        SaveHandler.I[0xBB ^ 0xAD] = I("3)\u0006\u0018\u0016\u0011h\u001b\u001bS\u0006)\u0019\u0011S\u0005$\u000e\r\u0016\u0007h\u000b\u0015\u0007\u0014h\t\u001b\u0001U", "uHots");
        SaveHandler.I[0x24 ^ 0x33] = I("W\f3\u0019", "yhRmY");
        SaveHandler.I[0x75 ^ 0x6D] = I("\u0017-\r*<5l\u0010)y=#\u0005\"y! \u0005?<#l\u0000'-0l\u0002)+q", "QLdFY");
        SaveHandler.I[0x1C ^ 0x5] = I("h*\u0003:", "FNbNn");
        SaveHandler.I[0x56 ^ 0x4C] = I("g\u0010\u00059", "ItdMD");
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider worldProvider) {
        throw new RuntimeException(SaveHandler.I[0x31 ^ 0x36]);
    }
    
    @Override
    public String getWorldDirectoryName() {
        return this.saveDirectoryName;
    }
}
