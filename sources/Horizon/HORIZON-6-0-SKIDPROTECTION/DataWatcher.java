package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import com.google.common.collect.Maps;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.Map;

public class DataWatcher
{
    private final Entity HorizonCode_Horizon_È;
    private boolean Â;
    private static final Map Ý;
    private final Map Ø­áŒŠá;
    private boolean Âµá€;
    private ReadWriteLock Ó;
    private static final String à = "CL_00001559";
    
    static {
        (Ý = Maps.newHashMap()).put(Byte.class, 0);
        DataWatcher.Ý.put(Short.class, 1);
        DataWatcher.Ý.put(Integer.class, 2);
        DataWatcher.Ý.put(Float.class, 3);
        DataWatcher.Ý.put(String.class, 4);
        DataWatcher.Ý.put(ItemStack.class, 5);
        DataWatcher.Ý.put(BlockPos.class, 6);
        DataWatcher.Ý.put(Rotations.class, 7);
    }
    
    public DataWatcher(final Entity owner) {
        this.Â = true;
        this.Ø­áŒŠá = Maps.newHashMap();
        this.Ó = new ReentrantReadWriteLock();
        this.HorizonCode_Horizon_È = owner;
    }
    
    public void HorizonCode_Horizon_È(final int id, final Object object) {
        final Integer var3 = DataWatcher.Ý.get(object.getClass());
        if (var3 == null) {
            throw new IllegalArgumentException("Unknown data type: " + object.getClass());
        }
        if (id > 31) {
            throw new IllegalArgumentException("Data value id is too big with " + id + "! (Max is " + 31 + ")");
        }
        if (this.Ø­áŒŠá.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate id value for " + id + "!");
        }
        final HorizonCode_Horizon_È var4 = new HorizonCode_Horizon_È(var3, id, object);
        this.Ó.writeLock().lock();
        this.Ø­áŒŠá.put(id, var4);
        this.Ó.writeLock().unlock();
        this.Â = false;
    }
    
    public void HorizonCode_Horizon_È(final int id, final int type) {
        final HorizonCode_Horizon_È var3 = new HorizonCode_Horizon_È(type, id, null);
        this.Ó.writeLock().lock();
        this.Ø­áŒŠá.put(id, var3);
        this.Ó.writeLock().unlock();
        this.Â = false;
    }
    
    public byte HorizonCode_Horizon_È(final int id) {
        return (byte)this.áŒŠÆ(id).Â();
    }
    
    public short Â(final int id) {
        return (short)this.áŒŠÆ(id).Â();
    }
    
    public int Ý(final int id) {
        return (int)this.áŒŠÆ(id).Â();
    }
    
    public float Ø­áŒŠá(final int id) {
        return (float)this.áŒŠÆ(id).Â();
    }
    
    public String Âµá€(final int id) {
        return (String)this.áŒŠÆ(id).Â();
    }
    
    public ItemStack Ó(final int id) {
        return (ItemStack)this.áŒŠÆ(id).Â();
    }
    
    private HorizonCode_Horizon_È áŒŠÆ(final int id) {
        this.Ó.readLock().lock();
        HorizonCode_Horizon_È var2;
        try {
            var2 = this.Ø­áŒŠá.get(id);
        }
        catch (Throwable var4) {
            final CrashReport var3 = CrashReport.HorizonCode_Horizon_È(var4, "Getting synched entity data");
            final CrashReportCategory var5 = var3.HorizonCode_Horizon_È("Synched entity data");
            var5.HorizonCode_Horizon_È("Data ID", id);
            throw new ReportedException(var3);
        }
        this.Ó.readLock().unlock();
        return var2;
    }
    
    public Rotations à(final int id) {
        return (Rotations)this.áŒŠÆ(id).Â();
    }
    
    public void Â(final int id, final Object newData) {
        final HorizonCode_Horizon_È var3 = this.áŒŠÆ(id);
        if (ObjectUtils.notEqual(newData, var3.Â())) {
            var3.HorizonCode_Horizon_È(newData);
            this.HorizonCode_Horizon_È.áˆºÑ¢Õ(id);
            var3.HorizonCode_Horizon_È(true);
            this.Âµá€ = true;
        }
    }
    
    public void Ø(final int id) {
        DataWatcher.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.áŒŠÆ(id), true);
        this.Âµá€ = true;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public static void HorizonCode_Horizon_È(final List objectsList, final PacketBuffer buffer) throws IOException {
        if (objectsList != null) {
            for (final HorizonCode_Horizon_È var3 : objectsList) {
                HorizonCode_Horizon_È(buffer, var3);
            }
        }
        buffer.writeByte(127);
    }
    
    public List Â() {
        ArrayList var1 = null;
        if (this.Âµá€) {
            this.Ó.readLock().lock();
            for (final HorizonCode_Horizon_È var3 : this.Ø­áŒŠá.values()) {
                if (var3.Ø­áŒŠá()) {
                    var3.HorizonCode_Horizon_È(false);
                    if (var1 == null) {
                        var1 = Lists.newArrayList();
                    }
                    var1.add(var3);
                }
            }
            this.Ó.readLock().unlock();
        }
        this.Âµá€ = false;
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final PacketBuffer buffer) throws IOException {
        this.Ó.readLock().lock();
        for (final HorizonCode_Horizon_È var3 : this.Ø­áŒŠá.values()) {
            HorizonCode_Horizon_È(buffer, var3);
        }
        this.Ó.readLock().unlock();
        buffer.writeByte(127);
    }
    
    public List Ý() {
        ArrayList var1 = null;
        this.Ó.readLock().lock();
        for (final HorizonCode_Horizon_È var3 : this.Ø­áŒŠá.values()) {
            if (var1 == null) {
                var1 = Lists.newArrayList();
            }
            var1.add(var3);
        }
        this.Ó.readLock().unlock();
        return var1;
    }
    
    private static void HorizonCode_Horizon_È(final PacketBuffer buffer, final HorizonCode_Horizon_È object) throws IOException {
        final int var2 = (object.Ý() << 5 | (object.HorizonCode_Horizon_È() & 0x1F)) & 0xFF;
        buffer.writeByte(var2);
        switch (object.Ý()) {
            case 0: {
                buffer.writeByte((byte)object.Â());
                break;
            }
            case 1: {
                buffer.writeShort((short)object.Â());
                break;
            }
            case 2: {
                buffer.writeInt((int)object.Â());
                break;
            }
            case 3: {
                buffer.writeFloat((float)object.Â());
                break;
            }
            case 4: {
                buffer.HorizonCode_Horizon_È((String)object.Â());
                break;
            }
            case 5: {
                final ItemStack var3 = (ItemStack)object.Â();
                buffer.HorizonCode_Horizon_È(var3);
                break;
            }
            case 6: {
                final BlockPos var4 = (BlockPos)object.Â();
                buffer.writeInt(var4.HorizonCode_Horizon_È());
                buffer.writeInt(var4.Â());
                buffer.writeInt(var4.Ý());
                break;
            }
            case 7: {
                final Rotations var5 = (Rotations)object.Â();
                buffer.writeFloat(var5.Â());
                buffer.writeFloat(var5.Ý());
                buffer.writeFloat(var5.Ø­áŒŠá());
                break;
            }
        }
    }
    
    public static List Â(final PacketBuffer buffer) throws IOException {
        ArrayList var1 = null;
        for (byte var2 = buffer.readByte(); var2 != 127; var2 = buffer.readByte()) {
            if (var1 == null) {
                var1 = Lists.newArrayList();
            }
            final int var3 = (var2 & 0xE0) >> 5;
            final int var4 = var2 & 0x1F;
            HorizonCode_Horizon_È var5 = null;
            switch (var3) {
                case 0: {
                    var5 = new HorizonCode_Horizon_È(var3, var4, buffer.readByte());
                    break;
                }
                case 1: {
                    var5 = new HorizonCode_Horizon_È(var3, var4, buffer.readShort());
                    break;
                }
                case 2: {
                    var5 = new HorizonCode_Horizon_È(var3, var4, buffer.readInt());
                    break;
                }
                case 3: {
                    var5 = new HorizonCode_Horizon_È(var3, var4, buffer.readFloat());
                    break;
                }
                case 4: {
                    var5 = new HorizonCode_Horizon_È(var3, var4, buffer.Ý(32767));
                    break;
                }
                case 5: {
                    var5 = new HorizonCode_Horizon_È(var3, var4, buffer.Ø());
                    break;
                }
                case 6: {
                    final int var6 = buffer.readInt();
                    final int var7 = buffer.readInt();
                    final int var8 = buffer.readInt();
                    var5 = new HorizonCode_Horizon_È(var3, var4, new BlockPos(var6, var7, var8));
                    break;
                }
                case 7: {
                    final float var9 = buffer.readFloat();
                    final float var10 = buffer.readFloat();
                    final float var11 = buffer.readFloat();
                    var5 = new HorizonCode_Horizon_È(var3, var4, new Rotations(var9, var10, var11));
                    break;
                }
            }
            var1.add(var5);
        }
        return var1;
    }
    
    public void HorizonCode_Horizon_È(final List p_75687_1_) {
        this.Ó.writeLock().lock();
        for (final HorizonCode_Horizon_È var3 : p_75687_1_) {
            final HorizonCode_Horizon_È var4 = this.Ø­áŒŠá.get(var3.HorizonCode_Horizon_È());
            if (var4 != null) {
                var4.HorizonCode_Horizon_È(var3.Â());
                this.HorizonCode_Horizon_È.áˆºÑ¢Õ(var3.HorizonCode_Horizon_È());
            }
        }
        this.Ó.writeLock().unlock();
        this.Âµá€ = true;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Â;
    }
    
    public void Âµá€() {
        this.Âµá€ = false;
    }
    
    public static class HorizonCode_Horizon_È
    {
        private final int HorizonCode_Horizon_È;
        private final int Â;
        private Object Ý;
        private boolean Ø­áŒŠá;
        private static final String Âµá€ = "CL_00001560";
        
        public HorizonCode_Horizon_È(final int type, final int id, final Object object) {
            this.Â = id;
            this.Ý = object;
            this.HorizonCode_Horizon_È = type;
            this.Ø­áŒŠá = true;
        }
        
        public int HorizonCode_Horizon_È() {
            return this.Â;
        }
        
        public void HorizonCode_Horizon_È(final Object object) {
            this.Ý = object;
        }
        
        public Object Â() {
            return this.Ý;
        }
        
        public int Ý() {
            return this.HorizonCode_Horizon_È;
        }
        
        public boolean Ø­áŒŠá() {
            return this.Ø­áŒŠá;
        }
        
        public void HorizonCode_Horizon_È(final boolean watched) {
            this.Ø­áŒŠá = watched;
        }
        
        static /* synthetic */ void HorizonCode_Horizon_È(final HorizonCode_Horizon_È horizonCode_Horizon_È, final boolean ø­áŒŠá) {
            horizonCode_Horizon_È.Ø­áŒŠá = ø­áŒŠá;
        }
    }
}
