/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.palette;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BitArray;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.palette.ArrayPalette;
import net.minecraft.util.palette.HashMapPalette;
import net.minecraft.util.palette.IPalette;
import net.minecraft.util.palette.IResizeCallback;

public class PalettedContainer<T>
implements IResizeCallback<T> {
    private final IPalette<T> registryPalette;
    private final IResizeCallback<T> dummyPaletteResize = PalettedContainer::lambda$new$0;
    private final ObjectIntIdentityMap<T> registry;
    private final Function<CompoundNBT, T> deserializer;
    private final Function<T, CompoundNBT> serializer;
    private final T defaultState;
    protected BitArray storage;
    private IPalette<T> palette;
    private int bits;
    private final ReentrantLock lock = new ReentrantLock();

    public void lock() {
        if (this.lock.isLocked() && !this.lock.isHeldByCurrentThread()) {
            String string = Thread.getAllStackTraces().keySet().stream().filter(Objects::nonNull).map(PalettedContainer::lambda$lock$1).collect(Collectors.joining("\n"));
            CrashReport crashReport = new CrashReport("Writing into PalettedContainer from multiple threads", new IllegalStateException());
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Thread dumps");
            crashReportCategory.addDetail("Thread dumps", string);
            throw new ReportedException(crashReport);
        }
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public PalettedContainer(IPalette<T> iPalette, ObjectIntIdentityMap<T> objectIntIdentityMap, Function<CompoundNBT, T> function, Function<T, CompoundNBT> function2, T t) {
        this.registryPalette = iPalette;
        this.registry = objectIntIdentityMap;
        this.deserializer = function;
        this.serializer = function2;
        this.defaultState = t;
        this.setBits(4);
    }

    private static int getIndex(int n, int n2, int n3) {
        return n2 << 8 | n3 << 4 | n;
    }

    private void setBits(int n) {
        if (n != this.bits) {
            this.bits = n;
            if (this.bits <= 4) {
                this.bits = 4;
                this.palette = new ArrayPalette<T>(this.registry, this.bits, this, this.deserializer);
            } else if (this.bits < 9) {
                this.palette = new HashMapPalette<T>(this.registry, this.bits, this, this.deserializer, this.serializer);
            } else {
                this.palette = this.registryPalette;
                this.bits = MathHelper.log2DeBruijn(this.registry.size());
            }
            this.palette.idFor(this.defaultState);
            this.storage = new BitArray(this.bits, 4096);
        }
    }

    @Override
    public int onResize(int n, T t) {
        int n2;
        this.lock();
        BitArray bitArray = this.storage;
        IPalette<T> iPalette = this.palette;
        this.setBits(n);
        for (n2 = 0; n2 < bitArray.size(); ++n2) {
            T t2 = iPalette.get(bitArray.getAt(n2));
            if (t2 == null) continue;
            this.set(n2, t2);
        }
        n2 = this.palette.idFor(t);
        this.unlock();
        return n2;
    }

    public T lockedSwap(int n, int n2, int n3, T t) {
        this.lock();
        T t2 = this.doSwap(PalettedContainer.getIndex(n, n2, n3), t);
        this.unlock();
        return t2;
    }

    public T swap(int n, int n2, int n3, T t) {
        return this.doSwap(PalettedContainer.getIndex(n, n2, n3), t);
    }

    protected T doSwap(int n, T t) {
        int n2 = this.palette.idFor(t);
        int n3 = this.storage.swapAt(n, n2);
        T t2 = this.palette.get(n3);
        return t2 == null ? this.defaultState : t2;
    }

    protected void set(int n, T t) {
        int n2 = this.palette.idFor(t);
        this.storage.setAt(n, n2);
    }

    public T get(int n, int n2, int n3) {
        return this.get(PalettedContainer.getIndex(n, n2, n3));
    }

    protected T get(int n) {
        T t = this.palette.get(this.storage.getAt(n));
        return t == null ? this.defaultState : t;
    }

    public void read(PacketBuffer packetBuffer) {
        this.lock();
        byte by = packetBuffer.readByte();
        if (this.bits != by) {
            this.setBits(by);
        }
        this.palette.read(packetBuffer);
        packetBuffer.readLongArray(this.storage.getBackingLongArray());
        this.unlock();
    }

    public void write(PacketBuffer packetBuffer) {
        this.lock();
        packetBuffer.writeByte(this.bits);
        this.palette.write(packetBuffer);
        packetBuffer.writeLongArray(this.storage.getBackingLongArray());
        this.unlock();
    }

    public void readChunkPalette(ListNBT listNBT, long[] lArray) {
        this.lock();
        int n = Math.max(4, MathHelper.log2DeBruijn(listNBT.size()));
        if (n != this.bits) {
            this.setBits(n);
        }
        this.palette.read(listNBT);
        int n2 = lArray.length * 64 / 4096;
        if (this.palette == this.registryPalette) {
            HashMapPalette<T> hashMapPalette = new HashMapPalette<T>(this.registry, n, this.dummyPaletteResize, this.deserializer, this.serializer);
            hashMapPalette.read(listNBT);
            BitArray bitArray = new BitArray(n, 4096, lArray);
            for (int i = 0; i < 4096; ++i) {
                this.storage.setAt(i, this.registryPalette.idFor(hashMapPalette.get(bitArray.getAt(i))));
            }
        } else if (n2 == this.bits) {
            System.arraycopy(lArray, 0, this.storage.getBackingLongArray(), 0, lArray.length);
        } else {
            BitArray bitArray = new BitArray(n2, 4096, lArray);
            for (int i = 0; i < 4096; ++i) {
                this.storage.setAt(i, bitArray.getAt(i));
            }
        }
        this.unlock();
    }

    public void writeChunkPalette(CompoundNBT compoundNBT, String string, String string2) {
        this.lock();
        HashMapPalette<T> hashMapPalette = new HashMapPalette<T>(this.registry, this.bits, this.dummyPaletteResize, this.deserializer, this.serializer);
        T t = this.defaultState;
        int n = hashMapPalette.idFor(this.defaultState);
        int[] nArray = new int[4096];
        for (int i = 0; i < 4096; ++i) {
            T t2 = this.get(i);
            if (t2 != t) {
                t = t2;
                n = hashMapPalette.idFor(t2);
            }
            nArray[i] = n;
        }
        ListNBT listNBT = new ListNBT();
        hashMapPalette.writePaletteToList(listNBT);
        compoundNBT.put(string, listNBT);
        int n2 = Math.max(4, MathHelper.log2DeBruijn(listNBT.size()));
        BitArray bitArray = new BitArray(n2, 4096);
        for (int i = 0; i < nArray.length; ++i) {
            bitArray.setAt(i, nArray[i]);
        }
        compoundNBT.putLongArray(string2, bitArray.getBackingLongArray());
        this.unlock();
    }

    public int getSerializedSize() {
        return 1 + this.palette.getSerializedSize() + PacketBuffer.getVarIntSize(this.storage.size()) + this.storage.getBackingLongArray().length * 8;
    }

    public boolean func_235963_a_(Predicate<T> predicate) {
        return this.palette.func_230341_a_(predicate);
    }

    public void count(ICountConsumer<T> iCountConsumer) {
        Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap();
        this.storage.getAll(arg_0 -> PalettedContainer.lambda$count$2(int2IntOpenHashMap, arg_0));
        int2IntOpenHashMap.int2IntEntrySet().forEach(arg_0 -> this.lambda$count$3(iCountConsumer, arg_0));
    }

    private void lambda$count$3(ICountConsumer iCountConsumer, Int2IntMap.Entry entry) {
        iCountConsumer.accept(this.palette.get(entry.getIntKey()), entry.getIntValue());
    }

    private static void lambda$count$2(Int2IntMap int2IntMap, int n) {
        int2IntMap.put(n, int2IntMap.get(n) + 1);
    }

    private static String lambda$lock$1(Thread thread2) {
        return thread2.getName() + ": \n\tat " + Arrays.stream(thread2.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat "));
    }

    private static int lambda$new$0(int n, Object object) {
        return 1;
    }

    @FunctionalInterface
    public static interface ICountConsumer<T> {
        public void accept(T var1, int var2);
    }
}

