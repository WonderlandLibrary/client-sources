/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.primitives.UnsignedLong;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.command.ITimerCallback;
import net.minecraft.command.TimerCallbackSerializers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerCallbackManager<T> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final TimerCallbackSerializers<T> field_216334_b;
    private final Queue<Entry<T>> entries = new PriorityQueue<Entry<T>>(TimerCallbackManager.sorter());
    private UnsignedLong nextUniqueId = UnsignedLong.ZERO;
    private final Table<String, Long, Entry<T>> byName = HashBasedTable.create();

    private static <T> Comparator<Entry<T>> sorter() {
        return Comparator.comparingLong(TimerCallbackManager::lambda$sorter$0).thenComparing(TimerCallbackManager::lambda$sorter$1);
    }

    public TimerCallbackManager(TimerCallbackSerializers<T> timerCallbackSerializers, Stream<Dynamic<INBT>> stream) {
        this(timerCallbackSerializers);
        this.entries.clear();
        this.byName.clear();
        this.nextUniqueId = UnsignedLong.ZERO;
        stream.forEach(this::lambda$new$2);
    }

    public TimerCallbackManager(TimerCallbackSerializers<T> timerCallbackSerializers) {
        this.field_216334_b = timerCallbackSerializers;
    }

    public void run(T t, long l) {
        Entry<T> entry;
        while ((entry = this.entries.peek()) != null && entry.triggerTime <= l) {
            this.entries.remove();
            this.byName.remove(entry.name, l);
            entry.callback.run(t, this, l);
        }
        return;
    }

    public void func_227576_a_(String string, long l, ITimerCallback<T> iTimerCallback) {
        if (!this.byName.contains(string, l)) {
            this.nextUniqueId = this.nextUniqueId.plus(UnsignedLong.ONE);
            Entry<T> entry = new Entry<T>(l, this.nextUniqueId, string, iTimerCallback);
            this.byName.put(string, l, entry);
            this.entries.add(entry);
        }
    }

    public int func_227575_a_(String string) {
        Collection<Entry<Entry>> collection = this.byName.row(string).values();
        collection.forEach(this.entries::remove);
        int n = collection.size();
        collection.clear();
        return n;
    }

    public Set<String> func_227574_a_() {
        return Collections.unmodifiableSet(this.byName.rowKeySet());
    }

    private void readEntry(CompoundNBT compoundNBT) {
        CompoundNBT compoundNBT2 = compoundNBT.getCompound("Callback");
        ITimerCallback<T> iTimerCallback = this.field_216334_b.func_216341_a(compoundNBT2);
        if (iTimerCallback != null) {
            String string = compoundNBT.getString("Name");
            long l = compoundNBT.getLong("TriggerTime");
            this.func_227576_a_(string, l, iTimerCallback);
        }
    }

    private CompoundNBT writeEntry(Entry<T> entry) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Name", entry.name);
        compoundNBT.putLong("TriggerTime", entry.triggerTime);
        compoundNBT.put("Callback", this.field_216334_b.func_216339_a(entry.callback));
        return compoundNBT;
    }

    public ListNBT write() {
        ListNBT listNBT = new ListNBT();
        this.entries.stream().sorted(TimerCallbackManager.sorter()).map(this::writeEntry).forEach(listNBT::add);
        return listNBT;
    }

    private void lambda$new$2(Dynamic dynamic) {
        if (!(dynamic.getValue() instanceof CompoundNBT)) {
            LOGGER.warn("Invalid format of events: {}", (Object)dynamic);
        } else {
            this.readEntry((CompoundNBT)dynamic.getValue());
        }
    }

    private static UnsignedLong lambda$sorter$1(Entry entry) {
        return entry.uniqueId;
    }

    private static long lambda$sorter$0(Entry entry) {
        return entry.triggerTime;
    }

    public static class Entry<T> {
        public final long triggerTime;
        public final UnsignedLong uniqueId;
        public final String name;
        public final ITimerCallback<T> callback;

        private Entry(long l, UnsignedLong unsignedLong, String string, ITimerCallback<T> iTimerCallback) {
            this.triggerTime = l;
            this.uniqueId = unsignedLong;
            this.name = string;
            this.callback = iTimerCallback;
        }
    }
}

