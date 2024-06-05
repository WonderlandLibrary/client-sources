package net.minecraft.src;

import java.util.zip.*;
import java.io.*;

public class CompressedStreamTools
{
    public static NBTTagCompound readCompressed(final InputStream par0InputStream) throws IOException {
        final DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(par0InputStream)));
        NBTTagCompound var2;
        try {
            var2 = read(var1);
        }
        finally {
            var1.close();
        }
        var1.close();
        return var2;
    }
    
    public static void writeCompressed(final NBTTagCompound par0NBTTagCompound, final OutputStream par1OutputStream) throws IOException {
        final DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(par1OutputStream));
        try {
            write(par0NBTTagCompound, var2);
        }
        finally {
            var2.close();
        }
        var2.close();
    }
    
    public static NBTTagCompound decompress(final byte[] par0ArrayOfByte) throws IOException {
        final DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(par0ArrayOfByte))));
        NBTTagCompound var2;
        try {
            var2 = read(var1);
        }
        finally {
            var1.close();
        }
        var1.close();
        return var2;
    }
    
    public static byte[] compress(final NBTTagCompound par0NBTTagCompound) throws IOException {
        final ByteArrayOutputStream var1 = new ByteArrayOutputStream();
        final DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));
        try {
            write(par0NBTTagCompound, var2);
        }
        finally {
            var2.close();
        }
        var2.close();
        return var1.toByteArray();
    }
    
    public static void safeWrite(final NBTTagCompound par0NBTTagCompound, final File par1File) throws IOException {
        final File var2 = new File(String.valueOf(par1File.getAbsolutePath()) + "_tmp");
        if (var2.exists()) {
            var2.delete();
        }
        write(par0NBTTagCompound, var2);
        if (par1File.exists()) {
            par1File.delete();
        }
        if (par1File.exists()) {
            throw new IOException("Failed to delete " + par1File);
        }
        var2.renameTo(par1File);
    }
    
    public static void write(final NBTTagCompound par0NBTTagCompound, final File par1File) throws IOException {
        final DataOutputStream var2 = new DataOutputStream(new FileOutputStream(par1File));
        try {
            write(par0NBTTagCompound, var2);
        }
        finally {
            var2.close();
        }
        var2.close();
    }
    
    public static NBTTagCompound read(final File par0File) throws IOException {
        if (!par0File.exists()) {
            return null;
        }
        final DataInputStream var1 = new DataInputStream(new FileInputStream(par0File));
        NBTTagCompound var2;
        try {
            var2 = read(var1);
        }
        finally {
            var1.close();
        }
        var1.close();
        return var2;
    }
    
    public static NBTTagCompound read(final DataInput par0DataInput) throws IOException {
        final NBTBase var1 = NBTBase.readNamedTag(par0DataInput);
        if (var1 instanceof NBTTagCompound) {
            return (NBTTagCompound)var1;
        }
        throw new IOException("Root tag must be a named compound tag");
    }
    
    public static void write(final NBTTagCompound par0NBTTagCompound, final DataOutput par1DataOutput) throws IOException {
        NBTBase.writeNamedTag(par0NBTTagCompound, par1DataOutput);
    }
}
