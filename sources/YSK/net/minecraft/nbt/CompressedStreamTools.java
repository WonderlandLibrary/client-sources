package net.minecraft.nbt;

import net.minecraft.util.*;
import net.minecraft.crash.*;
import java.util.zip.*;
import java.io.*;

public class CompressedStreamTools
{
    private static final String[] I;
    
    private static void I() {
        (I = new String[0xB2 ^ 0xBB])["".length()] = I("9 \u000e<", "fTcLd");
        CompressedStreamTools.I[" ".length()] = I("\b \u0010\n\n*a\r\tO*$\u0015\u0003\u001b+a", "NAyfo");
        CompressedStreamTools.I["  ".length()] = I("?\u001e.\u0007S\u0019\u0010&S\u001e\u0018\u00025S\u0011\bQ S\u001d\f\u001c$\u0017S\u000e\u001e,\u0003\u001c\u0018\u001f%S\u0007\f\u0016", "mqAss");
        CompressedStreamTools.I["   ".length()] = I("", "PNhbY");
        CompressedStreamTools.I[0x70 ^ 0x74] = I("\u0006\u001b9\u000e\f$\u0013x$'\u001eT<\u000b\u0011+", "JtXje");
        CompressedStreamTools.I[0x66 ^ 0x63] = I("\"\u001a\u0006w\u0011\r?", "lXRWE");
        CompressedStreamTools.I[0x33 ^ 0x35] = I("7'\bp?\u0002+\n", "cFoPQ");
        CompressedStreamTools.I[0xC ^ 0xB] = I("\u001e!*\u001c+\b1 r>\u000439", "EtdRj");
        CompressedStreamTools.I[0x40 ^ 0x48] = I("\u0001\u000f\u000eS\u0015,\u001e\f", "Unisa");
    }
    
    public static void safeWrite(final NBTTagCompound nbtTagCompound, final File file) throws IOException {
        final File file2 = new File(String.valueOf(file.getAbsolutePath()) + CompressedStreamTools.I["".length()]);
        if (file2.exists()) {
            file2.delete();
        }
        write(nbtTagCompound, file2);
        if (file.exists()) {
            file.delete();
        }
        if (file.exists()) {
            throw new IOException(CompressedStreamTools.I[" ".length()] + file);
        }
        file2.renameTo(file);
    }
    
    public static void write(final NBTTagCompound nbtTagCompound, final DataOutput dataOutput) throws IOException {
        writeTag(nbtTagCompound, dataOutput);
    }
    
    static {
        I();
    }
    
    public static NBTTagCompound read(final File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        NBTTagCompound read;
        try {
            read = read(dataInputStream, NBTSizeTracker.INFINITE);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        finally {
            dataInputStream.close();
        }
        dataInputStream.close();
        return read;
    }
    
    public static NBTTagCompound readCompressed(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));
        NBTTagCompound read;
        try {
            read = read(dataInputStream, NBTSizeTracker.INFINITE);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        finally {
            dataInputStream.close();
        }
        dataInputStream.close();
        return read;
    }
    
    public static void write(final NBTTagCompound nbtTagCompound, final File file) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        try {
            write(nbtTagCompound, dataOutputStream);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        finally {
            dataOutputStream.close();
        }
        dataOutputStream.close();
    }
    
    public static NBTTagCompound read(final DataInputStream dataInputStream) throws IOException {
        return read(dataInputStream, NBTSizeTracker.INFINITE);
    }
    
    private static NBTBase func_152455_a(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final byte byte1 = dataInput.readByte();
        if (byte1 == 0) {
            return new NBTTagEnd();
        }
        dataInput.readUTF();
        final NBTBase newByType = NBTBase.createNewByType(byte1);
        try {
            newByType.read(dataInput, n, nbtSizeTracker);
            return newByType;
        }
        catch (IOException ex) {
            final CrashReport crashReport = CrashReport.makeCrashReport(ex, CompressedStreamTools.I[0x9D ^ 0x99]);
            final CrashReportCategory category = crashReport.makeCategory(CompressedStreamTools.I[0x5F ^ 0x5A]);
            category.addCrashSection(CompressedStreamTools.I[0xA2 ^ 0xA4], CompressedStreamTools.I[0x7B ^ 0x7C]);
            category.addCrashSection(CompressedStreamTools.I[0x9D ^ 0x95], byte1);
            throw new ReportedException(crashReport);
        }
    }
    
    private static void writeTag(final NBTBase nbtBase, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(nbtBase.getId());
        if (nbtBase.getId() != 0) {
            dataOutput.writeUTF(CompressedStreamTools.I["   ".length()]);
            nbtBase.write(dataOutput);
        }
    }
    
    public static void writeCompressed(final NBTTagCompound nbtTagCompound, final OutputStream outputStream) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));
        try {
            write(nbtTagCompound, dataOutputStream);
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        finally {
            dataOutputStream.close();
        }
        dataOutputStream.close();
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
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static NBTTagCompound read(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final NBTBase func_152455_a = func_152455_a(dataInput, "".length(), nbtSizeTracker);
        if (func_152455_a instanceof NBTTagCompound) {
            return (NBTTagCompound)func_152455_a;
        }
        throw new IOException(CompressedStreamTools.I["  ".length()]);
    }
}
