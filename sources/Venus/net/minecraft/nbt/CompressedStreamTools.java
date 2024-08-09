/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.EndNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTypes;

public class CompressedStreamTools {
    public static CompoundNBT readCompressed(File file) throws IOException {
        CompoundNBT compoundNBT;
        try (FileInputStream fileInputStream = new FileInputStream(file);){
            compoundNBT = CompressedStreamTools.readCompressed(fileInputStream);
        }
        return compoundNBT;
    }

    public static CompoundNBT readCompressed(InputStream inputStream) throws IOException {
        CompoundNBT compoundNBT;
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));){
            compoundNBT = CompressedStreamTools.read(dataInputStream, NBTSizeTracker.INFINITE);
        }
        return compoundNBT;
    }

    public static void writeCompressed(CompoundNBT compoundNBT, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
            CompressedStreamTools.writeCompressed(compoundNBT, fileOutputStream);
        }
    }

    public static void writeCompressed(CompoundNBT compoundNBT, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));){
            CompressedStreamTools.write(compoundNBT, dataOutputStream);
        }
    }

    public static void write(CompoundNBT compoundNBT, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);){
            CompressedStreamTools.write(compoundNBT, dataOutputStream);
        }
    }

    @Nullable
    public static CompoundNBT read(File file) throws IOException {
        CompoundNBT compoundNBT;
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);
             DataInputStream dataInputStream = new DataInputStream(fileInputStream);){
            compoundNBT = CompressedStreamTools.read(dataInputStream, NBTSizeTracker.INFINITE);
        }
        return compoundNBT;
    }

    public static CompoundNBT read(DataInput dataInput) throws IOException {
        return CompressedStreamTools.read(dataInput, NBTSizeTracker.INFINITE);
    }

    public static CompoundNBT read(DataInput dataInput, NBTSizeTracker nBTSizeTracker) throws IOException {
        INBT iNBT = CompressedStreamTools.read(dataInput, 0, nBTSizeTracker);
        if (iNBT instanceof CompoundNBT) {
            return (CompoundNBT)iNBT;
        }
        throw new IOException("Root tag must be a named compound tag");
    }

    public static void write(CompoundNBT compoundNBT, DataOutput dataOutput) throws IOException {
        CompressedStreamTools.writeTag(compoundNBT, dataOutput);
    }

    private static void writeTag(INBT iNBT, DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(iNBT.getId());
        if (iNBT.getId() != 0) {
            dataOutput.writeUTF("");
            iNBT.write(dataOutput);
        }
    }

    private static INBT read(DataInput dataInput, int n, NBTSizeTracker nBTSizeTracker) throws IOException {
        byte by = dataInput.readByte();
        if (by == 0) {
            return EndNBT.INSTANCE;
        }
        dataInput.readUTF();
        try {
            return NBTTypes.getGetTypeByID(by).readNBT(dataInput, n, nBTSizeTracker);
        } catch (IOException iOException) {
            CrashReport crashReport = CrashReport.makeCrashReport(iOException, "Loading NBT data");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("NBT Tag");
            crashReportCategory.addDetail("Tag type", by);
            throw new ReportedException(crashReport);
        }
    }
}

