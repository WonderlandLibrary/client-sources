/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import java.lang.ref.WeakReference;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaTable;
import mpp.venusfr.scripts.interpreter.LuaUserdata;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Metatable;

public class WeakTable
implements Metatable {
    private final boolean weakkeys;
    private final boolean weakvalues;
    private final LuaValue backing;

    public static LuaTable make(boolean bl, boolean bl2) {
        LuaString luaString;
        if (bl && bl2) {
            luaString = LuaString.valueOf("kv");
        } else if (bl) {
            luaString = LuaString.valueOf("k");
        } else if (bl2) {
            luaString = LuaString.valueOf("v");
        } else {
            return LuaTable.tableOf();
        }
        LuaTable luaTable = LuaTable.tableOf();
        LuaTable luaTable2 = LuaTable.tableOf(new LuaValue[]{LuaValue.MODE, luaString});
        luaTable.setmetatable(luaTable2);
        return luaTable;
    }

    public WeakTable(boolean bl, boolean bl2, LuaValue luaValue) {
        this.weakkeys = bl;
        this.weakvalues = bl2;
        this.backing = luaValue;
    }

    @Override
    public boolean useWeakKeys() {
        return this.weakkeys;
    }

    @Override
    public boolean useWeakValues() {
        return this.weakvalues;
    }

    @Override
    public LuaValue toLuaValue() {
        return this.backing;
    }

    @Override
    public LuaTable.Slot entry(LuaValue luaValue, LuaValue luaValue2) {
        if ((luaValue2 = luaValue2.strongvalue()) == null) {
            return null;
        }
        if (this.weakkeys && !luaValue.isnumber() && !luaValue.isstring() && !luaValue.isboolean()) {
            if (this.weakvalues && !luaValue2.isnumber() && !luaValue2.isstring() && !luaValue2.isboolean()) {
                return new WeakKeyAndValueSlot(luaValue, luaValue2, null);
            }
            return new WeakKeySlot(luaValue, luaValue2, null);
        }
        if (this.weakvalues && !luaValue2.isnumber() && !luaValue2.isstring() && !luaValue2.isboolean()) {
            return new WeakValueSlot(luaValue, luaValue2, null);
        }
        return LuaTable.defaultEntry(luaValue, luaValue2);
    }

    protected static LuaValue weaken(LuaValue luaValue) {
        switch (luaValue.type()) {
            case 5: 
            case 6: 
            case 8: {
                return new WeakValue(luaValue);
            }
            case 7: {
                return new WeakUserdata(luaValue);
            }
        }
        return luaValue;
    }

    protected static LuaValue strengthen(Object object) {
        if (object instanceof WeakReference) {
            object = ((WeakReference)object).get();
        }
        if (object instanceof WeakValue) {
            return ((WeakValue)object).strongvalue();
        }
        return (LuaValue)object;
    }

    @Override
    public LuaValue wrap(LuaValue luaValue) {
        return this.weakvalues ? WeakTable.weaken(luaValue) : luaValue;
    }

    @Override
    public LuaValue arrayget(LuaValue[] luaValueArray, int n) {
        LuaValue luaValue = luaValueArray[n];
        if (luaValue != null && (luaValue = WeakTable.strengthen(luaValue)) == null) {
            luaValueArray[n] = null;
        }
        return luaValue;
    }

    static class WeakKeyAndValueSlot
    extends WeakSlot {
        private final int keyhash;

        protected WeakKeyAndValueSlot(LuaValue luaValue, LuaValue luaValue2, LuaTable.Slot slot) {
            super(WeakTable.weaken(luaValue), WeakTable.weaken(luaValue2), slot);
            this.keyhash = luaValue.hashCode();
        }

        protected WeakKeyAndValueSlot(WeakKeyAndValueSlot weakKeyAndValueSlot, LuaTable.Slot slot) {
            super(weakKeyAndValueSlot.key, weakKeyAndValueSlot.value, slot);
            this.keyhash = weakKeyAndValueSlot.keyhash;
        }

        @Override
        public int keyindex(int n) {
            return LuaTable.hashmod(this.keyhash, n);
        }

        @Override
        public LuaTable.Slot set(LuaValue luaValue) {
            this.value = WeakTable.weaken(luaValue);
            return this;
        }

        @Override
        public LuaValue strongkey() {
            return WeakTable.strengthen(this.key);
        }

        @Override
        public LuaValue strongvalue() {
            return WeakTable.strengthen(this.value);
        }

        @Override
        protected WeakSlot copy(LuaTable.Slot slot) {
            return new WeakKeyAndValueSlot(this, slot);
        }
    }

    static class WeakKeySlot
    extends WeakSlot {
        private final int keyhash;

        protected WeakKeySlot(LuaValue luaValue, LuaValue luaValue2, LuaTable.Slot slot) {
            super(WeakTable.weaken(luaValue), luaValue2, slot);
            this.keyhash = luaValue.hashCode();
        }

        protected WeakKeySlot(WeakKeySlot weakKeySlot, LuaTable.Slot slot) {
            super(weakKeySlot.key, weakKeySlot.value, slot);
            this.keyhash = weakKeySlot.keyhash;
        }

        @Override
        public int keyindex(int n) {
            return LuaTable.hashmod(this.keyhash, n);
        }

        @Override
        public LuaTable.Slot set(LuaValue luaValue) {
            this.value = luaValue;
            return this;
        }

        @Override
        public LuaValue strongkey() {
            return WeakTable.strengthen(this.key);
        }

        @Override
        protected WeakSlot copy(LuaTable.Slot slot) {
            return new WeakKeySlot(this, slot);
        }
    }

    static class WeakValueSlot
    extends WeakSlot {
        protected WeakValueSlot(LuaValue luaValue, LuaValue luaValue2, LuaTable.Slot slot) {
            super(luaValue, WeakTable.weaken(luaValue2), slot);
        }

        protected WeakValueSlot(WeakValueSlot weakValueSlot, LuaTable.Slot slot) {
            super(weakValueSlot.key, weakValueSlot.value, slot);
        }

        @Override
        public int keyindex(int n) {
            return LuaTable.hashSlot(this.strongkey(), n);
        }

        @Override
        public LuaTable.Slot set(LuaValue luaValue) {
            this.value = WeakTable.weaken(luaValue);
            return this;
        }

        @Override
        public LuaValue strongvalue() {
            return WeakTable.strengthen(this.value);
        }

        @Override
        protected WeakSlot copy(LuaTable.Slot slot) {
            return new WeakValueSlot(this, slot);
        }
    }

    static class WeakValue
    extends LuaValue {
        WeakReference ref;

        protected WeakValue(LuaValue luaValue) {
            this.ref = new WeakReference<LuaValue>(luaValue);
        }

        @Override
        public int type() {
            this.illegal("type", "weak value");
            return 1;
        }

        @Override
        public String typename() {
            this.illegal("typename", "weak value");
            return null;
        }

        @Override
        public String toString() {
            return "weak<" + this.ref.get() + ">";
        }

        @Override
        public LuaValue strongvalue() {
            Object t = this.ref.get();
            return (LuaValue)t;
        }

        @Override
        public boolean raweq(LuaValue luaValue) {
            Object t = this.ref.get();
            return t != null && luaValue.raweq((LuaValue)t);
        }
    }

    static final class WeakUserdata
    extends WeakValue {
        private final WeakReference ob;
        private final LuaValue mt;

        private WeakUserdata(LuaValue luaValue) {
            super(luaValue);
            this.ob = new WeakReference<Object>(luaValue.touserdata());
            this.mt = luaValue.getmetatable();
        }

        @Override
        public LuaValue strongvalue() {
            Object t = this.ref.get();
            if (t != null) {
                return (LuaValue)t;
            }
            Object t2 = this.ob.get();
            if (t2 != null) {
                LuaUserdata luaUserdata = LuaValue.userdataOf(t2, this.mt);
                this.ref = new WeakReference<LuaUserdata>(luaUserdata);
                return luaUserdata;
            }
            return null;
        }
    }

    public static abstract class WeakSlot
    implements LuaTable.Slot {
        protected Object key;
        protected Object value;
        protected LuaTable.Slot next;

        protected WeakSlot(Object object, Object object2, LuaTable.Slot slot) {
            this.key = object;
            this.value = object2;
            this.next = slot;
        }

        @Override
        public abstract int keyindex(int var1);

        public abstract LuaTable.Slot set(LuaValue var1);

        @Override
        public LuaTable.StrongSlot first() {
            LuaValue luaValue = this.strongkey();
            LuaValue luaValue2 = this.strongvalue();
            if (luaValue != null && luaValue2 != null) {
                return new LuaTable.NormalEntry(luaValue, luaValue2);
            }
            this.key = null;
            this.value = null;
            return null;
        }

        @Override
        public LuaTable.StrongSlot find(LuaValue luaValue) {
            LuaTable.StrongSlot strongSlot = this.first();
            return strongSlot != null ? strongSlot.find(luaValue) : null;
        }

        @Override
        public boolean keyeq(LuaValue luaValue) {
            LuaTable.StrongSlot strongSlot = this.first();
            return strongSlot != null && strongSlot.keyeq(luaValue);
        }

        @Override
        public LuaTable.Slot rest() {
            return this.next;
        }

        @Override
        public int arraykey(int n) {
            return 1;
        }

        @Override
        public LuaTable.Slot set(LuaTable.StrongSlot strongSlot, LuaValue luaValue) {
            LuaValue luaValue2 = this.strongkey();
            if (luaValue2 != null && strongSlot.find(luaValue2) != null) {
                return this.set(luaValue);
            }
            if (luaValue2 != null) {
                this.next = this.next.set(strongSlot, luaValue);
                return this;
            }
            return this.next.set(strongSlot, luaValue);
        }

        @Override
        public LuaTable.Slot add(LuaTable.Slot slot) {
            LuaTable.Slot slot2 = this.next = this.next != null ? this.next.add(slot) : slot;
            if (this.strongkey() != null && this.strongvalue() != null) {
                return this;
            }
            return this.next;
        }

        @Override
        public LuaTable.Slot remove(LuaTable.StrongSlot strongSlot) {
            LuaValue luaValue = this.strongkey();
            if (luaValue == null) {
                return this.next.remove(strongSlot);
            }
            if (strongSlot.keyeq(luaValue)) {
                this.value = null;
                return this;
            }
            this.next = this.next.remove(strongSlot);
            return this;
        }

        @Override
        public LuaTable.Slot relink(LuaTable.Slot slot) {
            if (this.strongkey() != null && this.strongvalue() != null) {
                if (slot == null && this.next == null) {
                    return this;
                }
                return this.copy(slot);
            }
            return slot;
        }

        public LuaValue strongkey() {
            return (LuaValue)this.key;
        }

        public LuaValue strongvalue() {
            return (LuaValue)this.value;
        }

        protected abstract WeakSlot copy(LuaTable.Slot var1);
    }
}

