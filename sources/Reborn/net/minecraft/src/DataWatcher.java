package net.minecraft.src;

import java.util.concurrent.locks.*;
import java.util.*;
import java.io.*;

public class DataWatcher
{
    private boolean isBlank;
    private static final HashMap dataTypes;
    private final Map watchedObjects;
    private boolean objectChanged;
    private ReadWriteLock lock;
    
    static {
        (dataTypes = new HashMap()).put(Byte.class, 0);
        DataWatcher.dataTypes.put(Short.class, 1);
        DataWatcher.dataTypes.put(Integer.class, 2);
        DataWatcher.dataTypes.put(Float.class, 3);
        DataWatcher.dataTypes.put(String.class, 4);
        DataWatcher.dataTypes.put(ItemStack.class, 5);
        DataWatcher.dataTypes.put(ChunkCoordinates.class, 6);
    }
    
    public DataWatcher() {
        this.isBlank = true;
        this.watchedObjects = new HashMap();
        this.lock = new ReentrantReadWriteLock();
    }
    
    public void addObject(final int par1, final Object par2Obj) {
        final Integer var3 = DataWatcher.dataTypes.get(par2Obj.getClass());
        if (var3 == null) {
            throw new IllegalArgumentException("Unknown data type: " + par2Obj.getClass());
        }
        if (par1 > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + par1 + "! (Max is " + 31 + ")");
        }
        if (this.watchedObjects.containsKey(par1)) {
            throw new IllegalArgumentException("Duplicate id value for " + par1 + "!");
        }
        final WatchableObject var4 = new WatchableObject(var3, par1, par2Obj);
        this.lock.writeLock().lock();
        this.watchedObjects.put(par1, var4);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }
    
    public void addObjectByDataType(final int par1, final int par2) {
        final WatchableObject var3 = new WatchableObject(par2, par1, null);
        this.lock.writeLock().lock();
        this.watchedObjects.put(par1, var3);
        this.lock.writeLock().unlock();
        this.isBlank = false;
    }
    
    public byte getWatchableObjectByte(final int par1) {
        return (byte)this.getWatchedObject(par1).getObject();
    }
    
    public short getWatchableObjectShort(final int par1) {
        return (short)this.getWatchedObject(par1).getObject();
    }
    
    public int getWatchableObjectInt(final int par1) {
        return (int)this.getWatchedObject(par1).getObject();
    }
    
    public String getWatchableObjectString(final int par1) {
        return (String)this.getWatchedObject(par1).getObject();
    }
    
    public ItemStack getWatchableObjectItemStack(final int par1) {
        return (ItemStack)this.getWatchedObject(par1).getObject();
    }
    
    private WatchableObject getWatchedObject(final int par1) {
        this.lock.readLock().lock();
        WatchableObject var2;
        try {
            var2 = this.watchedObjects.get(par1);
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.makeCrashReport(var4, "Getting synched entity data");
            final CrashReportCategory var5 = var3.makeCategory("Synched entity data");
            var5.addCrashSection("Data ID", par1);
            throw new ReportedException(var3);
        }
        this.lock.readLock().unlock();
        return var2;
    }
    
    public void updateObject(final int par1, final Object par2Obj) {
        final WatchableObject var3 = this.getWatchedObject(par1);
        if (!par2Obj.equals(var3.getObject())) {
            var3.setObject(par2Obj);
            var3.setWatched(true);
            this.objectChanged = true;
        }
    }
    
    public void setObjectWatched(final int par1) {
        WatchableObject.setWatchableObjectWatched(this.getWatchedObject(par1), true);
        this.objectChanged = true;
    }
    
    public boolean hasChanges() {
        return this.objectChanged;
    }
    
    public static void writeObjectsInListToStream(final List par0List, final DataOutputStream par1DataOutputStream) throws IOException {
        if (par0List != null) {
            for (final WatchableObject var3 : par0List) {
                writeWatchableObject(par1DataOutputStream, var3);
            }
        }
        par1DataOutputStream.writeByte(127);
    }
    
    public List unwatchAndReturnAllWatched() {
        ArrayList var1 = null;
        if (this.objectChanged) {
            this.lock.readLock().lock();
            for (final WatchableObject var3 : this.watchedObjects.values()) {
                if (var3.isWatched()) {
                    var3.setWatched(false);
                    if (var1 == null) {
                        var1 = new ArrayList();
                    }
                    var1.add(var3);
                }
            }
            this.lock.readLock().unlock();
        }
        this.objectChanged = false;
        return var1;
    }
    
    public void writeWatchableObjects(final DataOutputStream par1DataOutputStream) throws IOException {
        this.lock.readLock().lock();
        for (final WatchableObject var3 : this.watchedObjects.values()) {
            writeWatchableObject(par1DataOutputStream, var3);
        }
        this.lock.readLock().unlock();
        par1DataOutputStream.writeByte(127);
    }
    
    public List getAllWatched() {
        ArrayList var1 = null;
        this.lock.readLock().lock();
        for (final WatchableObject var3 : this.watchedObjects.values()) {
            if (var1 == null) {
                var1 = new ArrayList();
            }
            var1.add(var3);
        }
        this.lock.readLock().unlock();
        return var1;
    }
    
    private static void writeWatchableObject(final DataOutputStream par0DataOutputStream, final WatchableObject par1WatchableObject) throws IOException {
        final int var2 = (par1WatchableObject.getObjectType() << 5 | (par1WatchableObject.getDataValueId() & 0x1F)) & 0xFF;
        par0DataOutputStream.writeByte(var2);
        switch (par1WatchableObject.getObjectType()) {
            case 0: {
                par0DataOutputStream.writeByte((byte)par1WatchableObject.getObject());
                break;
            }
            case 1: {
                par0DataOutputStream.writeShort((short)par1WatchableObject.getObject());
                break;
            }
            case 2: {
                par0DataOutputStream.writeInt((int)par1WatchableObject.getObject());
                break;
            }
            case 3: {
                par0DataOutputStream.writeFloat((float)par1WatchableObject.getObject());
                break;
            }
            case 4: {
                Packet.writeString((String)par1WatchableObject.getObject(), par0DataOutputStream);
                break;
            }
            case 5: {
                final ItemStack var3 = (ItemStack)par1WatchableObject.getObject();
                Packet.writeItemStack(var3, par0DataOutputStream);
                break;
            }
            case 6: {
                final ChunkCoordinates var4 = (ChunkCoordinates)par1WatchableObject.getObject();
                par0DataOutputStream.writeInt(var4.posX);
                par0DataOutputStream.writeInt(var4.posY);
                par0DataOutputStream.writeInt(var4.posZ);
                break;
            }
        }
    }
    
    public static List readWatchableObjects(final DataInputStream par0DataInputStream) throws IOException {
        ArrayList var1 = null;
        for (byte var2 = par0DataInputStream.readByte(); var2 != 127; var2 = par0DataInputStream.readByte()) {
            if (var1 == null) {
                var1 = new ArrayList();
            }
            final int var3 = (var2 & 0xE0) >> 5;
            final int var4 = var2 & 0x1F;
            WatchableObject var5 = null;
            switch (var3) {
                case 0: {
                    var5 = new WatchableObject(var3, var4, par0DataInputStream.readByte());
                    break;
                }
                case 1: {
                    var5 = new WatchableObject(var3, var4, par0DataInputStream.readShort());
                    break;
                }
                case 2: {
                    var5 = new WatchableObject(var3, var4, par0DataInputStream.readInt());
                    break;
                }
                case 3: {
                    var5 = new WatchableObject(var3, var4, par0DataInputStream.readFloat());
                    break;
                }
                case 4: {
                    var5 = new WatchableObject(var3, var4, Packet.readString(par0DataInputStream, 64));
                    break;
                }
                case 5: {
                    var5 = new WatchableObject(var3, var4, Packet.readItemStack(par0DataInputStream));
                    break;
                }
                case 6: {
                    final int var6 = par0DataInputStream.readInt();
                    final int var7 = par0DataInputStream.readInt();
                    final int var8 = par0DataInputStream.readInt();
                    var5 = new WatchableObject(var3, var4, new ChunkCoordinates(var6, var7, var8));
                    break;
                }
            }
            var1.add(var5);
        }
        return var1;
    }
    
    public void updateWatchedObjectsFromList(final List par1List) {
        this.lock.writeLock().lock();
        for (final WatchableObject var3 : par1List) {
            final WatchableObject var4 = this.watchedObjects.get(var3.getDataValueId());
            if (var4 != null) {
                var4.setObject(var3.getObject());
            }
        }
        this.lock.writeLock().unlock();
    }
    
    public boolean getIsBlank() {
        return this.isBlank;
    }
}
