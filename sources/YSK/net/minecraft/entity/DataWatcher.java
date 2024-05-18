package net.minecraft.entity;

import net.minecraft.crash.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.network.*;
import java.io.*;
import org.apache.commons.lang3.*;
import java.util.concurrent.locks.*;

public class DataWatcher
{
    private static final String[] I;
    private ReadWriteLock lock;
    private boolean isBlank;
    private boolean objectChanged;
    private final Map<Integer, WatchableObject> watchedObjects;
    private static final Map<Class<?>, Integer> dataTypes;
    private final Entity owner;
    
    public void addObjectByDataType(final int n, final int n2) {
        final WatchableObject watchableObject = new WatchableObject(n2, n, null);
        this.lock.writeLock().lock();
        this.watchedObjects.put(n, watchableObject);
        this.lock.writeLock().unlock();
        this.isBlank = ("".length() != 0);
    }
    
    private WatchableObject getWatchedObject(final int n) {
        this.lock.readLock().lock();
        WatchableObject watchableObject;
        try {
            watchableObject = this.watchedObjects.get(n);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, DataWatcher.I[0x76 ^ 0x70]);
            crashReport.makeCategory(DataWatcher.I[0x8B ^ 0x8C]).addCrashSection(DataWatcher.I[0x80 ^ 0x88], n);
            throw new ReportedException(crashReport);
        }
        this.lock.readLock().unlock();
        return watchableObject;
    }
    
    public float getWatchableObjectFloat(final int n) {
        return (float)this.getWatchedObject(n).getObject();
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
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getWatchableObjectInt(final int n) {
        return (int)this.getWatchedObject(n).getObject();
    }
    
    public void setObjectWatched(final int n) {
        WatchableObject.access$0(this.getWatchedObject(n), " ".length() != 0);
        this.objectChanged = (" ".length() != 0);
    }
    
    public String getWatchableObjectString(final int n) {
        return (String)this.getWatchedObject(n).getObject();
    }
    
    static {
        I();
        (dataTypes = Maps.newHashMap()).put(Byte.class, "".length());
        DataWatcher.dataTypes.put(Short.class, " ".length());
        DataWatcher.dataTypes.put(Integer.class, "  ".length());
        DataWatcher.dataTypes.put(Float.class, "   ".length());
        DataWatcher.dataTypes.put(String.class, 0x3B ^ 0x3F);
        DataWatcher.dataTypes.put(ItemStack.class, 0x76 ^ 0x73);
        DataWatcher.dataTypes.put(BlockPos.class, 0x5D ^ 0x5B);
        DataWatcher.dataTypes.put(Rotations.class, 0x83 ^ 0x84);
    }
    
    public List<WatchableObject> getChanged() {
        List<WatchableObject> arrayList = null;
        if (this.objectChanged) {
            this.lock.readLock().lock();
            final Iterator<WatchableObject> iterator = this.watchedObjects.values().iterator();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final WatchableObject watchableObject = iterator.next();
                if (watchableObject.isWatched()) {
                    watchableObject.setWatched("".length() != 0);
                    if (arrayList == null) {
                        arrayList = (List<WatchableObject>)Lists.newArrayList();
                    }
                    arrayList.add(watchableObject);
                }
            }
            this.lock.readLock().unlock();
        }
        this.objectChanged = ("".length() != 0);
        return arrayList;
    }
    
    private static void I() {
        (I = new String[0x92 ^ 0x9B])["".length()] = I("\"\u001b)'\u0006\u0000\u001bb-\b\u0003\u0014b=\u0010\u0007\u0010xi", "wuBIi");
        DataWatcher.I[" ".length()] = I(">'\u00065y\f'\u001e!<Z/\u0016t0\tf\u0006;6Z$\u001b3y\r/\u0006<y", "zFrTY");
        DataWatcher.I["  ".length()] = I("OH^#\u0007\u0016H\u001f\u001dF", "nhvnf");
        DataWatcher.I["   ".length()] = I("c", "JzaEB");
        DataWatcher.I[0x10 ^ 0x14] = I("/\u001e4\n.\b\n0\u0003g\u0002\u000fd\u0010&\u0007\u001e!F!\u0004\u0019d", "kkDfG");
        DataWatcher.I[0x73 ^ 0x76] = I("n", "OongI");
        DataWatcher.I[0x50 ^ 0x56] = I("6\"- \n\u001f y'\u001a\u001f$11\u0007Q\"7 \n\u0005>y0\u0002\u0005&", "qGYTc");
        DataWatcher.I[0x87 ^ 0x80] = I("\t-\u000b1\u000e?0E7\b.=\u0011+F>5\u00113", "ZTeRf");
        DataWatcher.I[0x7E ^ 0x76] = I("=\u0015!&E00", "ytUGe");
    }
    
    public void func_111144_e() {
        this.objectChanged = ("".length() != 0);
    }
    
    public boolean getIsBlank() {
        return this.isBlank;
    }
    
    public ItemStack getWatchableObjectItemStack(final int n) {
        return (ItemStack)this.getWatchedObject(n).getObject();
    }
    
    private static void writeWatchableObjectToPacketBuffer(final PacketBuffer packetBuffer, final WatchableObject watchableObject) throws IOException {
        packetBuffer.writeByte((watchableObject.getObjectType() << (0xB9 ^ 0xBC) | (watchableObject.getDataValueId() & (0x14 ^ 0xB))) & 253 + 253 - 374 + 123);
        switch (watchableObject.getObjectType()) {
            case 0: {
                packetBuffer.writeByte((byte)watchableObject.getObject());
                "".length();
                if (3 <= -1) {
                    throw null;
                }
                break;
            }
            case 1: {
                packetBuffer.writeShort((short)watchableObject.getObject());
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                packetBuffer.writeInt((int)watchableObject.getObject());
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                break;
            }
            case 3: {
                packetBuffer.writeFloat((float)watchableObject.getObject());
                "".length();
                if (4 <= 1) {
                    throw null;
                }
                break;
            }
            case 4: {
                packetBuffer.writeString((String)watchableObject.getObject());
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            case 5: {
                packetBuffer.writeItemStackToBuffer((ItemStack)watchableObject.getObject());
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 6: {
                final BlockPos blockPos = (BlockPos)watchableObject.getObject();
                packetBuffer.writeInt(blockPos.getX());
                packetBuffer.writeInt(blockPos.getY());
                packetBuffer.writeInt(blockPos.getZ());
                "".length();
                if (3 <= 2) {
                    throw null;
                }
                break;
            }
            case 7: {
                final Rotations rotations = (Rotations)watchableObject.getObject();
                packetBuffer.writeFloat(rotations.getX());
                packetBuffer.writeFloat(rotations.getY());
                packetBuffer.writeFloat(rotations.getZ());
                break;
            }
        }
    }
    
    public <T> void updateObject(final int n, final T object) {
        final WatchableObject watchedObject = this.getWatchedObject(n);
        if (ObjectUtils.notEqual((Object)object, watchedObject.getObject())) {
            watchedObject.setObject(object);
            this.owner.onDataWatcherUpdate(n);
            watchedObject.setWatched(" ".length() != 0);
            this.objectChanged = (" ".length() != 0);
        }
    }
    
    public <T> void addObject(final int n, final T t) {
        final Integer n2 = DataWatcher.dataTypes.get(t.getClass());
        if (n2 == null) {
            throw new IllegalArgumentException(DataWatcher.I["".length()] + t.getClass());
        }
        if (n > (0x89 ^ 0x96)) {
            throw new IllegalArgumentException(DataWatcher.I[" ".length()] + n + DataWatcher.I["  ".length()] + (0x14 ^ 0xB) + DataWatcher.I["   ".length()]);
        }
        if (this.watchedObjects.containsKey(n)) {
            throw new IllegalArgumentException(DataWatcher.I[0x45 ^ 0x41] + n + DataWatcher.I[0x83 ^ 0x86]);
        }
        final WatchableObject watchableObject = new WatchableObject(n2, n, t);
        this.lock.writeLock().lock();
        this.watchedObjects.put(n, watchableObject);
        this.lock.writeLock().unlock();
        this.isBlank = ("".length() != 0);
    }
    
    public byte getWatchableObjectByte(final int n) {
        return (byte)this.getWatchedObject(n).getObject();
    }
    
    public List<WatchableObject> getAllWatched() {
        List<WatchableObject> arrayList = null;
        this.lock.readLock().lock();
        final Iterator<WatchableObject> iterator = this.watchedObjects.values().iterator();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final WatchableObject watchableObject = iterator.next();
            if (arrayList == null) {
                arrayList = (List<WatchableObject>)Lists.newArrayList();
            }
            arrayList.add(watchableObject);
        }
        this.lock.readLock().unlock();
        return arrayList;
    }
    
    public void writeTo(final PacketBuffer packetBuffer) throws IOException {
        this.lock.readLock().lock();
        final Iterator<WatchableObject> iterator = this.watchedObjects.values().iterator();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            writeWatchableObjectToPacketBuffer(packetBuffer, iterator.next());
        }
        this.lock.readLock().unlock();
        packetBuffer.writeByte(34 + 75 - 104 + 122);
    }
    
    public static List<WatchableObject> readWatchedListFromPacketBuffer(final PacketBuffer packetBuffer) throws IOException {
        List<WatchableObject> arrayList = null;
        byte b = packetBuffer.readByte();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (b != 112 + 3 - 102 + 114) {
            if (arrayList == null) {
                arrayList = (List<WatchableObject>)Lists.newArrayList();
            }
            final int n = (b & 104 + 203 - 151 + 68) >> (0x0 ^ 0x5);
            final int n2 = b & (0xBB ^ 0xA4);
            WatchableObject watchableObject = null;
            switch (n) {
                case 0: {
                    watchableObject = new WatchableObject(n, n2, packetBuffer.readByte());
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    watchableObject = new WatchableObject(n, n2, packetBuffer.readShort());
                    "".length();
                    if (4 <= 2) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    watchableObject = new WatchableObject(n, n2, packetBuffer.readInt());
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    watchableObject = new WatchableObject(n, n2, packetBuffer.readFloat());
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    watchableObject = new WatchableObject(n, n2, packetBuffer.readStringFromBuffer(21014 + 32062 - 22456 + 2147));
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    watchableObject = new WatchableObject(n, n2, packetBuffer.readItemStackFromBuffer());
                    "".length();
                    if (1 >= 4) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    watchableObject = new WatchableObject(n, n2, new BlockPos(packetBuffer.readInt(), packetBuffer.readInt(), packetBuffer.readInt()));
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    break;
                }
                case 7: {
                    watchableObject = new WatchableObject(n, n2, new Rotations(packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readFloat()));
                    break;
                }
            }
            arrayList.add(watchableObject);
            b = packetBuffer.readByte();
        }
        return arrayList;
    }
    
    public Rotations getWatchableObjectRotations(final int n) {
        return (Rotations)this.getWatchedObject(n).getObject();
    }
    
    public DataWatcher(final Entity owner) {
        this.isBlank = (" ".length() != 0);
        this.watchedObjects = (Map<Integer, WatchableObject>)Maps.newHashMap();
        this.lock = new ReentrantReadWriteLock();
        this.owner = owner;
    }
    
    public short getWatchableObjectShort(final int n) {
        return (short)this.getWatchedObject(n).getObject();
    }
    
    public static void writeWatchedListToPacketBuffer(final List<WatchableObject> list, final PacketBuffer packetBuffer) throws IOException {
        if (list != null) {
            final Iterator<WatchableObject> iterator = list.iterator();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                writeWatchableObjectToPacketBuffer(packetBuffer, iterator.next());
            }
        }
        packetBuffer.writeByte(71 + 27 - 1 + 30);
    }
    
    public void updateWatchedObjectsFromList(final List<WatchableObject> list) {
        this.lock.writeLock().lock();
        final Iterator<WatchableObject> iterator = list.iterator();
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final WatchableObject watchableObject = iterator.next();
            final WatchableObject watchableObject2 = this.watchedObjects.get(watchableObject.getDataValueId());
            if (watchableObject2 != null) {
                watchableObject2.setObject(watchableObject.getObject());
                this.owner.onDataWatcherUpdate(watchableObject.getDataValueId());
            }
        }
        this.lock.writeLock().unlock();
        this.objectChanged = (" ".length() != 0);
    }
    
    public boolean hasObjectChanged() {
        return this.objectChanged;
    }
    
    public static class WatchableObject
    {
        private final int dataValueId;
        private final int objectType;
        private boolean watched;
        private Object watchedObject;
        
        public int getDataValueId() {
            return this.dataValueId;
        }
        
        public WatchableObject(final int objectType, final int dataValueId, final Object watchedObject) {
            this.dataValueId = dataValueId;
            this.watchedObject = watchedObject;
            this.objectType = objectType;
            this.watched = (" ".length() != 0);
        }
        
        public Object getObject() {
            return this.watchedObject;
        }
        
        public void setWatched(final boolean watched) {
            this.watched = watched;
        }
        
        public void setObject(final Object watchedObject) {
            this.watchedObject = watchedObject;
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
                if (-1 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static void access$0(final WatchableObject watchableObject, final boolean watched) {
            watchableObject.watched = watched;
        }
        
        public boolean isWatched() {
            return this.watched;
        }
        
        public int getObjectType() {
            return this.objectType;
        }
    }
}
