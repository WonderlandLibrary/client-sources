package HORIZON-6-0-SKIDPROTECTION;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.util.zip.GZIPInputStream;
import java.io.InputStream;

public class CompressedStreamTools
{
    private static final String Â = "CL_00001226";
    public static boolean HorizonCode_Horizon_È;
    
    static {
        CompressedStreamTools.HorizonCode_Horizon_È = true;
    }
    
    public static NBTTagCompound HorizonCode_Horizon_È(final InputStream inputStream) throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));
        NBTTagCompound horizonCode_Horizon_È;
        try {
            horizonCode_Horizon_È = HorizonCode_Horizon_È(dataInputStream, NBTSizeTracker.HorizonCode_Horizon_È);
        }
        finally {
            dataInputStream.close();
        }
        dataInputStream.close();
        return horizonCode_Horizon_È;
    }
    
    public static void HorizonCode_Horizon_È(final NBTTagCompound nbtTagCompound, final OutputStream outputStream) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));
        try {
            HorizonCode_Horizon_È(nbtTagCompound, (DataOutput)dataOutputStream);
        }
        finally {
            dataOutputStream.close();
        }
        dataOutputStream.close();
    }
    
    public static void HorizonCode_Horizon_È(final NBTTagCompound nbtTagCompound, final File file) throws IOException {
        final File file2 = new File(String.valueOf(file.getAbsolutePath()) + "_tmp");
        if (file2.exists()) {
            file2.delete();
        }
        Â(nbtTagCompound, file2);
        if (file.exists()) {
            file.delete();
        }
        if (file.exists()) {
            throw new IOException("Failed to delete " + file);
        }
        file2.renameTo(file);
    }
    
    public static void Â(final NBTTagCompound nbtTagCompound, final File file) throws IOException {
        final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        try {
            HorizonCode_Horizon_È(nbtTagCompound, (DataOutput)dataOutputStream);
        }
        finally {
            dataOutputStream.close();
        }
        dataOutputStream.close();
    }
    
    public static NBTTagCompound HorizonCode_Horizon_È(final File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        final DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        NBTTagCompound horizonCode_Horizon_È;
        try {
            horizonCode_Horizon_È = HorizonCode_Horizon_È(dataInputStream, NBTSizeTracker.HorizonCode_Horizon_È);
        }
        finally {
            dataInputStream.close();
        }
        dataInputStream.close();
        return horizonCode_Horizon_È;
    }
    
    public static NBTTagCompound HorizonCode_Horizon_È(final DataInputStream dataInputStream) throws IOException {
        return HorizonCode_Horizon_È(dataInputStream, NBTSizeTracker.HorizonCode_Horizon_È);
    }
    
    public static NBTTagCompound HorizonCode_Horizon_È(final DataInput dataInput, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final NBTBase horizonCode_Horizon_È = HorizonCode_Horizon_È(dataInput, 0, nbtSizeTracker);
        if (horizonCode_Horizon_È instanceof NBTTagCompound) {
            return (NBTTagCompound)horizonCode_Horizon_È;
        }
        throw new IOException("Root tag must be a named compound tag");
    }
    
    public static void HorizonCode_Horizon_È(final NBTTagCompound nbtTagCompound, final DataOutput dataOutput) throws IOException {
        HorizonCode_Horizon_È((NBTBase)nbtTagCompound, dataOutput);
    }
    
    private static void HorizonCode_Horizon_È(final NBTBase nbtBase, final DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(nbtBase.HorizonCode_Horizon_È());
        if (nbtBase.HorizonCode_Horizon_È() != 0) {
            dataOutput.writeUTF("");
            nbtBase.HorizonCode_Horizon_È(dataOutput);
        }
    }
    
    private static NBTBase HorizonCode_Horizon_È(final DataInput dataInput, final int n, final NBTSizeTracker nbtSizeTracker) throws IOException {
        final byte byte1 = dataInput.readByte();
        if (byte1 == 0) {
            return new NBTTagEnd();
        }
        dataInput.readUTF();
        final NBTBase horizonCode_Horizon_È = NBTBase.HorizonCode_Horizon_È(byte1);
        try {
            horizonCode_Horizon_È.HorizonCode_Horizon_È(dataInput, n, nbtSizeTracker);
            return horizonCode_Horizon_È;
        }
        catch (IOException causeIn) {
            final CrashReport horizonCode_Horizon_È2 = CrashReport.HorizonCode_Horizon_È(causeIn, "Loading NBT data");
            final CrashReportCategory horizonCode_Horizon_È3 = horizonCode_Horizon_È2.HorizonCode_Horizon_È("NBT Tag");
            horizonCode_Horizon_È3.HorizonCode_Horizon_È("Tag name", "[UNNAMED TAG]");
            horizonCode_Horizon_È3.HorizonCode_Horizon_È("Tag type", byte1);
            throw new ReportedException(horizonCode_Horizon_È2);
        }
    }
}
