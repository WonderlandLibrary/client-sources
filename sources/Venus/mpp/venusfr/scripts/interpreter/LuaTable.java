/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.scripts.interpreter;

import java.lang.ref.WeakReference;
import java.util.Vector;
import mpp.venusfr.scripts.interpreter.Buffer;
import mpp.venusfr.scripts.interpreter.LuaError;
import mpp.venusfr.scripts.interpreter.LuaInteger;
import mpp.venusfr.scripts.interpreter.LuaString;
import mpp.venusfr.scripts.interpreter.LuaValue;
import mpp.venusfr.scripts.interpreter.Metatable;
import mpp.venusfr.scripts.interpreter.Varargs;

public class LuaTable
extends LuaValue
implements Metatable {
    private static final int MIN_HASH_CAPACITY = 2;
    private static final LuaString N = LuaTable.valueOf("n");
    protected LuaValue[] array;
    protected Slot[] hash;
    protected int hashEntries;
    protected Metatable m_metatable;
    private static final Slot[] NOBUCKETS = new Slot[0];

    public LuaTable() {
        this.array = NOVALS;
        this.hash = NOBUCKETS;
    }

    public LuaTable(int n, int n2) {
        this.presize(n, n2);
    }

    public LuaTable(LuaValue[] luaValueArray, LuaValue[] luaValueArray2, Varargs varargs) {
        int n;
        int n2 = luaValueArray != null ? luaValueArray.length : 0;
        int n3 = luaValueArray2 != null ? luaValueArray2.length : 0;
        int n4 = varargs != null ? varargs.narg() : 0;
        this.presize(n3 + n4, n2 >> 1);
        for (n = 0; n < n3; ++n) {
            this.rawset(n + 1, luaValueArray2[n]);
        }
        if (varargs != null) {
            int n5 = varargs.narg();
            for (n = 1; n <= n5; ++n) {
                this.rawset(n3 + n, varargs.arg(n));
            }
        }
        for (n = 0; n < n2; n += 2) {
            if (luaValueArray[n + 1].isnil()) continue;
            this.rawset(luaValueArray[n], luaValueArray[n + 1]);
        }
    }

    public LuaTable(Varargs varargs) {
        this(varargs, 1);
    }

    public LuaTable(Varargs varargs, int n) {
        int n2 = n - 1;
        int n3 = Math.max(varargs.narg() - n2, 0);
        this.presize(n3, 1);
        this.set(N, (LuaValue)LuaTable.valueOf(n3));
        for (int i = 1; i <= n3; ++i) {
            this.set(i, varargs.arg(i + n2));
        }
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public String typename() {
        return "table";
    }

    @Override
    public boolean istable() {
        return false;
    }

    @Override
    public LuaTable checktable() {
        return this;
    }

    @Override
    public LuaTable opttable(LuaTable luaTable) {
        return this;
    }

    @Override
    public void presize(int n) {
        if (n > this.array.length) {
            this.array = LuaTable.resize(this.array, 1 << LuaTable.log2(n));
        }
    }

    public void presize(int n, int n2) {
        if (n2 > 0 && n2 < 2) {
            n2 = 2;
        }
        this.array = n > 0 ? new LuaValue[1 << LuaTable.log2(n)] : NOVALS;
        this.hash = n2 > 0 ? new Slot[1 << LuaTable.log2(n2)] : NOBUCKETS;
        this.hashEntries = 0;
    }

    private static LuaValue[] resize(LuaValue[] luaValueArray, int n) {
        LuaValue[] luaValueArray2 = new LuaValue[n];
        System.arraycopy(luaValueArray, 0, luaValueArray2, 0, luaValueArray.length);
        return luaValueArray2;
    }

    protected int getArrayLength() {
        return this.array.length;
    }

    protected int getHashLength() {
        return this.hash.length;
    }

    @Override
    public LuaValue getmetatable() {
        return this.m_metatable != null ? this.m_metatable.toLuaValue() : null;
    }

    @Override
    public LuaValue setmetatable(LuaValue luaValue) {
        boolean bl = this.m_metatable != null && this.m_metatable.useWeakKeys();
        boolean bl2 = this.m_metatable != null && this.m_metatable.useWeakValues();
        this.m_metatable = LuaTable.metatableOf(luaValue);
        if (bl != (this.m_metatable != null && this.m_metatable.useWeakKeys()) || bl2 != (this.m_metatable != null && this.m_metatable.useWeakValues())) {
            this.rehash(0);
        }
        return this;
    }

    @Override
    public LuaValue get(int n) {
        LuaValue luaValue = this.rawget(n);
        return luaValue.isnil() && this.m_metatable != null ? LuaTable.gettable(this, LuaTable.valueOf(n)) : luaValue;
    }

    @Override
    public LuaValue get(LuaValue luaValue) {
        LuaValue luaValue2 = this.rawget(luaValue);
        return luaValue2.isnil() && this.m_metatable != null ? LuaTable.gettable(this, luaValue) : luaValue2;
    }

    @Override
    public LuaValue rawget(int n) {
        if (n > 0 && n <= this.array.length) {
            LuaValue luaValue = this.m_metatable == null ? this.array[n - 1] : this.m_metatable.arrayget(this.array, n - 1);
            return luaValue != null ? luaValue : NIL;
        }
        return this.hashget(LuaInteger.valueOf(n));
    }

    @Override
    public LuaValue rawget(LuaValue luaValue) {
        int n;
        if (luaValue.isinttype() && (n = luaValue.toint()) > 0 && n <= this.array.length) {
            LuaValue luaValue2 = this.m_metatable == null ? this.array[n - 1] : this.m_metatable.arrayget(this.array, n - 1);
            return luaValue2 != null ? luaValue2 : NIL;
        }
        return this.hashget(luaValue);
    }

    protected LuaValue hashget(LuaValue luaValue) {
        if (this.hashEntries > 0) {
            for (Slot slot = this.hash[this.hashSlot(luaValue)]; slot != null; slot = slot.rest()) {
                StrongSlot strongSlot = slot.find(luaValue);
                if (strongSlot == null) continue;
                return strongSlot.value();
            }
        }
        return NIL;
    }

    @Override
    public void set(int n, LuaValue luaValue) {
        if (this.m_metatable == null || !this.rawget(n).isnil() || !LuaTable.settable(this, LuaInteger.valueOf(n), luaValue)) {
            this.rawset(n, luaValue);
        }
    }

    @Override
    public void set(LuaValue luaValue, LuaValue luaValue2) {
        if (luaValue == null || !luaValue.isvalidkey() && !this.metatag(NEWINDEX).isfunction()) {
            throw new LuaError("value ('" + luaValue + "') can not be used as a table index");
        }
        if (this.m_metatable == null || !this.rawget(luaValue).isnil() || !LuaTable.settable(this, luaValue, luaValue2)) {
            this.rawset(luaValue, luaValue2);
        }
    }

    @Override
    public void rawset(int n, LuaValue luaValue) {
        if (!this.arrayset(n, luaValue)) {
            this.hashset(LuaInteger.valueOf(n), luaValue);
        }
    }

    @Override
    public void rawset(LuaValue luaValue, LuaValue luaValue2) {
        if (!luaValue.isinttype() || !this.arrayset(luaValue.toint(), luaValue2)) {
            this.hashset(luaValue, luaValue2);
        }
    }

    private boolean arrayset(int n, LuaValue luaValue) {
        if (n > 0 && n <= this.array.length) {
            this.array[n - 1] = luaValue.isnil() ? null : (this.m_metatable != null ? this.m_metatable.wrap(luaValue) : luaValue);
            return false;
        }
        return true;
    }

    public LuaValue remove(int n) {
        LuaValue luaValue;
        int n2 = this.length();
        if (n == 0) {
            n = n2;
        } else if (n > n2) {
            return NONE;
        }
        LuaValue luaValue2 = luaValue = this.get(n);
        while (!luaValue2.isnil()) {
            luaValue2 = this.get(n + 1);
            this.set(n++, luaValue2);
        }
        return luaValue.isnil() ? NONE : luaValue;
    }

    public void insert(int n, LuaValue luaValue) {
        if (n == 0) {
            n = this.length() + 1;
        }
        while (!luaValue.isnil()) {
            LuaValue luaValue2 = this.get(n);
            this.set(n++, luaValue);
            luaValue = luaValue2;
        }
    }

    public LuaValue concat(LuaString luaString, int n, int n2) {
        Buffer buffer = new Buffer();
        if (n <= n2) {
            buffer.append(this.get(n).checkstring());
            while (++n <= n2) {
                buffer.append(luaString);
                buffer.append(this.get(n).checkstring());
            }
        }
        return buffer.tostring();
    }

    @Override
    public int length() {
        if (this.m_metatable != null) {
            LuaValue luaValue = this.len();
            if (!luaValue.isint()) {
                throw new LuaError("table length is not an integer: " + luaValue);
            }
            return luaValue.toint();
        }
        return this.rawlen();
    }

    @Override
    public LuaValue len() {
        LuaValue luaValue = this.metatag(LEN);
        if (luaValue.toboolean()) {
            return luaValue.call(this);
        }
        return LuaInteger.valueOf(this.rawlen());
    }

    @Override
    public int rawlen() {
        int n = this.getArrayLength();
        int n2 = n + 1;
        int n3 = 0;
        while (!this.rawget(n2).isnil()) {
            n3 = n2;
            n2 += n + this.getHashLength() + 1;
        }
        while (n2 > n3 + 1) {
            int n4 = (n2 + n3) / 2;
            if (!this.rawget(n4).isnil()) {
                n3 = n4;
                continue;
            }
            n2 = n4;
        }
        return n3;
    }

    @Override
    public Varargs next(LuaValue luaValue) {
        Slot slot;
        int n = 0;
        if (!(luaValue.isnil() || luaValue.isinttype() && (n = luaValue.toint()) > 0 && n <= this.array.length)) {
            if (this.hash.length == 0) {
                LuaTable.error("invalid key to 'next' 1: " + luaValue);
            }
            n = this.hashSlot(luaValue);
            boolean bl = false;
            for (slot = this.hash[n]; slot != null; slot = slot.rest()) {
                if (bl) {
                    StrongSlot strongSlot = slot.first();
                    if (strongSlot == null) continue;
                    return strongSlot.toVarargs();
                }
                if (!slot.keyeq(luaValue)) continue;
                bl = true;
            }
            if (!bl) {
                LuaTable.error("invalid key to 'next' 2: " + luaValue);
            }
            n += 1 + this.array.length;
        }
        while (n < this.array.length) {
            if (this.array[n] != null) {
                LuaValue luaValue2;
                LuaValue luaValue3 = luaValue2 = this.m_metatable == null ? this.array[n] : this.m_metatable.arrayget(this.array, n);
                if (luaValue2 != null) {
                    return LuaTable.varargsOf(LuaInteger.valueOf(n + 1), (Varargs)luaValue2);
                }
            }
            ++n;
        }
        n -= this.array.length;
        while (n < this.hash.length) {
            for (Slot slot2 = this.hash[n]; slot2 != null; slot2 = slot2.rest()) {
                slot = slot2.first();
                if (slot == null) continue;
                return slot.toVarargs();
            }
            ++n;
        }
        return NIL;
    }

    @Override
    public Varargs inext(LuaValue luaValue) {
        int n = luaValue.checkint() + 1;
        LuaValue luaValue2 = this.rawget(n);
        return luaValue2.isnil() ? NONE : LuaTable.varargsOf(LuaInteger.valueOf(n), (Varargs)luaValue2);
    }

    public void hashset(LuaValue luaValue, LuaValue luaValue2) {
        if (luaValue2.isnil()) {
            this.hashRemove(luaValue);
        } else {
            Slot slot;
            int n = 0;
            if (this.hash.length > 0) {
                n = this.hashSlot(luaValue);
                for (slot = this.hash[n]; slot != null; slot = slot.rest()) {
                    StrongSlot strongSlot = slot.find(luaValue);
                    if (strongSlot == null) continue;
                    this.hash[n] = this.hash[n].set(strongSlot, luaValue2);
                    return;
                }
            }
            if (this.checkLoadFactor()) {
                if ((this.m_metatable == null || !this.m_metatable.useWeakValues()) && luaValue.isinttype() && luaValue.toint() > 0) {
                    this.rehash(luaValue.toint());
                    if (this.arrayset(luaValue.toint(), luaValue2)) {
                        return;
                    }
                } else {
                    this.rehash(-1);
                }
                n = this.hashSlot(luaValue);
            }
            slot = this.m_metatable != null ? this.m_metatable.entry(luaValue, luaValue2) : LuaTable.defaultEntry(luaValue, luaValue2);
            this.hash[n] = this.hash[n] != null ? this.hash[n].add(slot) : slot;
            ++this.hashEntries;
        }
    }

    public static int hashpow2(int n, int n2) {
        return n & n2;
    }

    public static int hashmod(int n, int n2) {
        return (n & Integer.MAX_VALUE) % n2;
    }

    public static int hashSlot(LuaValue luaValue, int n) {
        switch (luaValue.type()) {
            case 2: 
            case 3: 
            case 5: 
            case 7: 
            case 8: {
                return LuaTable.hashmod(luaValue.hashCode(), n);
            }
        }
        return LuaTable.hashpow2(luaValue.hashCode(), n);
    }

    private int hashSlot(LuaValue luaValue) {
        return LuaTable.hashSlot(luaValue, this.hash.length - 1);
    }

    private void hashRemove(LuaValue luaValue) {
        if (this.hash.length > 0) {
            int n = this.hashSlot(luaValue);
            for (Slot slot = this.hash[n]; slot != null; slot = slot.rest()) {
                StrongSlot strongSlot = slot.find(luaValue);
                if (strongSlot == null) continue;
                this.hash[n] = this.hash[n].remove(strongSlot);
                --this.hashEntries;
                return;
            }
        }
    }

    private boolean checkLoadFactor() {
        return this.hashEntries >= this.hash.length;
    }

    private int countHashKeys() {
        int n = 0;
        for (int i = 0; i < this.hash.length; ++i) {
            for (Slot slot = this.hash[i]; slot != null; slot = slot.rest()) {
                if (slot.first() == null) continue;
                ++n;
            }
        }
        return n;
    }

    private void dropWeakArrayValues() {
        for (int i = 0; i < this.array.length; ++i) {
            this.m_metatable.arrayget(this.array, i);
        }
    }

    private int countIntKeys(int[] nArray) {
        int n;
        int n2 = 0;
        int n3 = 1;
        for (int i = 0; i < 31 && n3 <= this.array.length; ++i) {
            n = Math.min(this.array.length, 1 << i);
            int n4 = 0;
            while (n3 <= n) {
                if (this.array[n3++ - 1] == null) continue;
                ++n4;
            }
            nArray[i] = n4;
            n2 += n4;
        }
        for (n3 = 0; n3 < this.hash.length; ++n3) {
            for (Slot slot = this.hash[n3]; slot != null; slot = slot.rest()) {
                n = slot.arraykey(Integer.MAX_VALUE);
                if (n <= 0) continue;
                int n5 = LuaTable.log2(n);
                nArray[n5] = nArray[n5] + 1;
                ++n2;
            }
        }
        return n2;
    }

    static int log2(int n) {
        int n2 = 0;
        if (--n < 0) {
            return 1;
        }
        if ((n & 0xFFFF0000) != 0) {
            n2 = 16;
            n >>>= 16;
        }
        if ((n & 0xFF00) != 0) {
            n2 += 8;
            n >>>= 8;
        }
        if ((n & 0xF0) != 0) {
            n2 += 4;
            n >>>= 4;
        }
        switch (n) {
            case 0: {
                return 1;
            }
            case 1: {
                ++n2;
                break;
            }
            case 2: {
                n2 += 2;
                break;
            }
            case 3: {
                n2 += 2;
                break;
            }
            case 4: {
                n2 += 3;
                break;
            }
            case 5: {
                n2 += 3;
                break;
            }
            case 6: {
                n2 += 3;
                break;
            }
            case 7: {
                n2 += 3;
                break;
            }
            case 8: {
                n2 += 4;
                break;
            }
            case 9: {
                n2 += 4;
                break;
            }
            case 10: {
                n2 += 4;
                break;
            }
            case 11: {
                n2 += 4;
                break;
            }
            case 12: {
                n2 += 4;
                break;
            }
            case 13: {
                n2 += 4;
                break;
            }
            case 14: {
                n2 += 4;
                break;
            }
            case 15: {
                n2 += 4;
            }
        }
        return n2;
    }

    private void rehash(int n) {
        Slot slot;
        int n2;
        Object object;
        int n3;
        Slot[] slotArray;
        int n4;
        int n5;
        int n6;
        LuaValue[] luaValueArray;
        if (this.m_metatable != null && (this.m_metatable.useWeakKeys() || this.m_metatable.useWeakValues())) {
            this.hashEntries = this.countHashKeys();
            if (this.m_metatable.useWeakValues()) {
                this.dropWeakArrayValues();
            }
        }
        int[] nArray = new int[32];
        int n7 = this.countIntKeys(nArray);
        if (n > 0) {
            ++n7;
            int n8 = LuaTable.log2(n);
            nArray[n8] = nArray[n8] + 1;
        }
        int n9 = nArray[0];
        int n10 = 0;
        for (int i = 1; i < 32; ++i) {
            n9 += nArray[i];
            if (n7 * 2 < 1 << i) break;
            if (n9 < 1 << i - 1) continue;
            n10 = 1 << i;
        }
        LuaValue[] luaValueArray2 = this.array;
        Slot[] slotArray2 = this.hash;
        int n11 = 0;
        if (n > 0 && n <= n10) {
            --n11;
        }
        if (n10 != luaValueArray2.length) {
            luaValueArray = new LuaValue[n10];
            if (n10 > luaValueArray2.length) {
                n6 = LuaTable.log2(n10) + 1;
                for (n5 = LuaTable.log2(luaValueArray2.length + 1); n5 < n6; ++n5) {
                    n11 += nArray[n5];
                }
            } else if (luaValueArray2.length > n10) {
                n6 = LuaTable.log2(luaValueArray2.length) + 1;
                for (n5 = LuaTable.log2(n10 + 1); n5 < n6; ++n5) {
                    n11 -= nArray[n5];
                }
            }
            System.arraycopy(luaValueArray2, 0, luaValueArray, 0, Math.min(luaValueArray2.length, n10));
        } else {
            luaValueArray = this.array;
        }
        n5 = this.hashEntries - n11 + (n < 0 || n > n10 ? 1 : 0);
        n6 = slotArray2.length;
        if (n5 > 0) {
            var13_13 = n5 < 2 ? 2 : 1 << LuaTable.log2(n5);
            n4 = var13_13 - 1;
            slotArray = new Slot[var13_13];
        } else {
            var13_13 = 0;
            n4 = 0;
            slotArray = NOBUCKETS;
        }
        for (n3 = 0; n3 < n6; ++n3) {
            for (object = slotArray2[n3]; object != null; object = object.rest()) {
                n2 = object.arraykey(n10);
                if (n2 > 0) {
                    slot = object.first();
                    if (slot == null) continue;
                    luaValueArray[n2 - 1] = slot.value();
                    continue;
                }
                if (object instanceof DeadSlot) continue;
                int n12 = object.keyindex(n4);
                slotArray[n12] = object.relink(slotArray[n12]);
            }
        }
        n3 = n10;
        while (n3 < luaValueArray2.length) {
            if ((object = luaValueArray2[n3++]) == null) continue;
            n2 = LuaTable.hashmod(LuaInteger.hashCode(n3), n4);
            if (this.m_metatable != null) {
                slot = this.m_metatable.entry(LuaTable.valueOf(n3), (LuaValue)object);
                if (slot == null) {
                    continue;
                }
            } else {
                slot = LuaTable.defaultEntry(LuaTable.valueOf(n3), (LuaValue)object);
            }
            slotArray[n2] = slotArray[n2] != null ? slotArray[n2].add(slot) : slot;
        }
        this.hash = slotArray;
        this.array = luaValueArray;
        this.hashEntries -= n11;
    }

    @Override
    public Slot entry(LuaValue luaValue, LuaValue luaValue2) {
        return LuaTable.defaultEntry(luaValue, luaValue2);
    }

    protected static boolean isLargeKey(LuaValue luaValue) {
        switch (luaValue.type()) {
            case 4: {
                return luaValue.rawlen() > 32;
            }
            case 1: 
            case 3: {
                return true;
            }
        }
        return false;
    }

    protected static Entry defaultEntry(LuaValue luaValue, LuaValue luaValue2) {
        if (luaValue.isinttype()) {
            return new IntKeyEntry(luaValue.toint(), luaValue2);
        }
        if (luaValue2.type() == 3) {
            return new NumberValueEntry(luaValue, luaValue2.todouble());
        }
        return new NormalEntry(luaValue, luaValue2);
    }

    public void sort(LuaValue luaValue) {
        int n;
        if (this.len().tolong() >= Integer.MAX_VALUE) {
            throw new LuaError("array too big: " + this.len().tolong());
        }
        if (this.m_metatable != null && this.m_metatable.useWeakValues()) {
            this.dropWeakArrayValues();
        }
        if ((n = this.length()) > 1) {
            this.heapSort(n, luaValue.isnil() ? null : luaValue);
        }
    }

    private void heapSort(int n, LuaValue luaValue) {
        this.heapify(n, luaValue);
        int n2 = n;
        while (n2 > 1) {
            LuaValue luaValue2 = this.get(n2);
            this.set(n2, this.get(1));
            this.set(1, luaValue2);
            this.siftDown(1, --n2, luaValue);
        }
    }

    private void heapify(int n, LuaValue luaValue) {
        for (int i = n / 2; i > 0; --i) {
            this.siftDown(i, n, luaValue);
        }
    }

    private void siftDown(int n, int n2, LuaValue luaValue) {
        int n3 = n;
        while (n3 * 2 <= n2) {
            int n4 = n3 * 2;
            if (n4 < n2 && this.compare(n4, n4 + 1, luaValue)) {
                ++n4;
            }
            if (this.compare(n3, n4, luaValue)) {
                LuaValue luaValue2 = this.get(n3);
                this.set(n3, this.get(n4));
                this.set(n4, luaValue2);
                n3 = n4;
                continue;
            }
            return;
        }
    }

    private boolean compare(int n, int n2, LuaValue luaValue) {
        LuaValue luaValue2 = this.get(n);
        LuaValue luaValue3 = this.get(n2);
        if (luaValue2 == null || luaValue3 == null) {
            return true;
        }
        if (luaValue != null) {
            return luaValue.call(luaValue2, luaValue3).toboolean();
        }
        return luaValue2.lt_b(luaValue3);
    }

    public int keyCount() {
        LuaValue luaValue = LuaValue.NIL;
        int n = 0;
        Varargs varargs;
        while (!(luaValue = (varargs = this.next(luaValue)).arg1()).isnil()) {
            ++n;
        }
        return n;
    }

    public LuaValue[] keys() {
        Object[] objectArray;
        Vector<LuaValue> vector = new Vector<LuaValue>();
        LuaValue luaValue = LuaValue.NIL;
        while (!(luaValue = (objectArray = this.next(luaValue)).arg1()).isnil()) {
            vector.addElement(luaValue);
        }
        objectArray = new LuaValue[vector.size()];
        vector.copyInto(objectArray);
        return objectArray;
    }

    @Override
    public LuaValue eq(LuaValue luaValue) {
        return this.eq_b(luaValue) ? TRUE : FALSE;
    }

    @Override
    public boolean eq_b(LuaValue luaValue) {
        if (this == luaValue) {
            return false;
        }
        if (this.m_metatable == null || !luaValue.istable()) {
            return true;
        }
        LuaValue luaValue2 = luaValue.getmetatable();
        return luaValue2 != null && LuaValue.eqmtcall(this, this.m_metatable.toLuaValue(), luaValue, luaValue2);
    }

    public Varargs unpack() {
        return this.unpack(1, this.rawlen());
    }

    public Varargs unpack(int n) {
        return this.unpack(n, this.rawlen());
    }

    public Varargs unpack(int n, int n2) {
        if (n2 < n) {
            return NONE;
        }
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new LuaError("too many results to unpack: greater 2147483647");
        }
        int n4 = 0xFFFFFF;
        if (n3 >= n4) {
            throw new LuaError("too many results to unpack: " + n3 + " (max is " + n4 + ")");
        }
        int n5 = n2 + 1 - n;
        switch (n5) {
            case 0: {
                return NONE;
            }
            case 1: {
                return this.get(n);
            }
            case 2: {
                return LuaTable.varargsOf(this.get(n), (Varargs)this.get(n + 1));
            }
        }
        if (n5 < 0) {
            return NONE;
        }
        try {
            LuaValue[] luaValueArray = new LuaValue[n5];
            while (--n5 >= 0) {
                luaValueArray[n5] = this.get(n + n5);
            }
            return LuaTable.varargsOf(luaValueArray);
        } catch (OutOfMemoryError outOfMemoryError) {
            throw new LuaError("too many results to unpack [out of memory]: " + n5);
        }
    }

    @Override
    public boolean useWeakKeys() {
        return true;
    }

    @Override
    public boolean useWeakValues() {
        return true;
    }

    @Override
    public LuaValue toLuaValue() {
        return this;
    }

    @Override
    public LuaValue wrap(LuaValue luaValue) {
        return luaValue;
    }

    @Override
    public LuaValue arrayget(LuaValue[] luaValueArray, int n) {
        return luaValueArray[n];
    }

    static interface Slot {
        public int keyindex(int var1);

        public StrongSlot first();

        public StrongSlot find(LuaValue var1);

        public boolean keyeq(LuaValue var1);

        public Slot rest();

        public int arraykey(int var1);

        public Slot set(StrongSlot var1, LuaValue var2);

        public Slot add(Slot var1);

        public Slot remove(StrongSlot var1);

        public Slot relink(Slot var1);
    }

    static interface StrongSlot
    extends Slot {
        public LuaValue key();

        public LuaValue value();

        public Varargs toVarargs();
    }

    static abstract class Entry
    extends Varargs
    implements StrongSlot {
        Entry() {
        }

        @Override
        public abstract LuaValue key();

        @Override
        public abstract LuaValue value();

        abstract Entry set(LuaValue var1);

        @Override
        public abstract boolean keyeq(LuaValue var1);

        @Override
        public abstract int keyindex(int var1);

        @Override
        public int arraykey(int n) {
            return 1;
        }

        @Override
        public LuaValue arg(int n) {
            switch (n) {
                case 1: {
                    return this.key();
                }
                case 2: {
                    return this.value();
                }
            }
            return LuaValue.NIL;
        }

        @Override
        public int narg() {
            return 1;
        }

        @Override
        public Varargs toVarargs() {
            return LuaValue.varargsOf(this.key(), (Varargs)this.value());
        }

        @Override
        public LuaValue arg1() {
            return this.key();
        }

        @Override
        public Varargs subargs(int n) {
            switch (n) {
                case 1: {
                    return this;
                }
                case 2: {
                    return this.value();
                }
            }
            return LuaValue.NONE;
        }

        @Override
        public StrongSlot first() {
            return this;
        }

        @Override
        public Slot rest() {
            return null;
        }

        @Override
        public StrongSlot find(LuaValue luaValue) {
            return this.keyeq(luaValue) ? this : null;
        }

        @Override
        public Slot set(StrongSlot strongSlot, LuaValue luaValue) {
            return this.set(luaValue);
        }

        @Override
        public Slot add(Slot slot) {
            return new LinkSlot(this, slot);
        }

        @Override
        public Slot remove(StrongSlot strongSlot) {
            return new DeadSlot(this.key(), null);
        }

        @Override
        public Slot relink(Slot slot) {
            return slot != null ? new LinkSlot(this, slot) : this;
        }
    }

    private static class DeadSlot
    implements Slot {
        private final Object key;
        private Slot next;

        private DeadSlot(LuaValue luaValue, Slot slot) {
            this.key = LuaTable.isLargeKey(luaValue) ? new WeakReference<LuaValue>(luaValue) : luaValue;
            this.next = slot;
        }

        private LuaValue key() {
            return (LuaValue)(this.key instanceof WeakReference ? ((WeakReference)this.key).get() : this.key);
        }

        @Override
        public int keyindex(int n) {
            return 1;
        }

        @Override
        public StrongSlot first() {
            return null;
        }

        @Override
        public StrongSlot find(LuaValue luaValue) {
            return null;
        }

        @Override
        public boolean keyeq(LuaValue luaValue) {
            LuaValue luaValue2 = this.key();
            return luaValue2 != null && luaValue.raweq(luaValue2);
        }

        @Override
        public Slot rest() {
            return this.next;
        }

        @Override
        public int arraykey(int n) {
            return 1;
        }

        @Override
        public Slot set(StrongSlot strongSlot, LuaValue luaValue) {
            Slot slot;
            Slot slot2 = slot = this.next != null ? this.next.set(strongSlot, luaValue) : null;
            if (this.key() != null) {
                this.next = slot;
                return this;
            }
            return slot;
        }

        @Override
        public Slot add(Slot slot) {
            return this.next != null ? this.next.add(slot) : slot;
        }

        @Override
        public Slot remove(StrongSlot strongSlot) {
            if (this.key() != null) {
                this.next = this.next.remove(strongSlot);
                return this;
            }
            return this.next;
        }

        @Override
        public Slot relink(Slot slot) {
            return slot;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("<dead");
            LuaValue luaValue = this.key();
            if (luaValue != null) {
                stringBuffer.append(": ");
                stringBuffer.append(luaValue);
            }
            stringBuffer.append('>');
            if (this.next != null) {
                stringBuffer.append("; ");
                stringBuffer.append(this.next);
            }
            return stringBuffer.toString();
        }
    }

    private static class IntKeyEntry
    extends Entry {
        private final int key;
        private LuaValue value;

        IntKeyEntry(int n, LuaValue luaValue) {
            this.key = n;
            this.value = luaValue;
        }

        @Override
        public LuaValue key() {
            return LuaValue.valueOf(this.key);
        }

        @Override
        public int arraykey(int n) {
            return this.key >= 1 && this.key <= n ? this.key : 0;
        }

        @Override
        public LuaValue value() {
            return this.value;
        }

        @Override
        public Entry set(LuaValue luaValue) {
            this.value = luaValue;
            return this;
        }

        @Override
        public int keyindex(int n) {
            return LuaTable.hashmod(LuaInteger.hashCode(this.key), n);
        }

        @Override
        public boolean keyeq(LuaValue luaValue) {
            return luaValue.raweq(this.key);
        }
    }

    private static class NumberValueEntry
    extends Entry {
        private double value;
        private final LuaValue key;

        NumberValueEntry(LuaValue luaValue, double d) {
            this.key = luaValue;
            this.value = d;
        }

        @Override
        public LuaValue key() {
            return this.key;
        }

        @Override
        public LuaValue value() {
            return LuaValue.valueOf(this.value);
        }

        @Override
        public Entry set(LuaValue luaValue) {
            LuaValue luaValue2;
            if (luaValue.type() == 3 && !(luaValue2 = luaValue.tonumber()).isnil()) {
                this.value = luaValue2.todouble();
                return this;
            }
            return new NormalEntry(this.key, luaValue);
        }

        @Override
        public int keyindex(int n) {
            return LuaTable.hashSlot(this.key, n);
        }

        @Override
        public boolean keyeq(LuaValue luaValue) {
            return luaValue.raweq(this.key);
        }
    }

    static class NormalEntry
    extends Entry {
        private final LuaValue key;
        private LuaValue value;

        NormalEntry(LuaValue luaValue, LuaValue luaValue2) {
            this.key = luaValue;
            this.value = luaValue2;
        }

        @Override
        public LuaValue key() {
            return this.key;
        }

        @Override
        public LuaValue value() {
            return this.value;
        }

        @Override
        public Entry set(LuaValue luaValue) {
            this.value = luaValue;
            return this;
        }

        @Override
        public Varargs toVarargs() {
            return this;
        }

        @Override
        public int keyindex(int n) {
            return LuaTable.hashSlot(this.key, n);
        }

        @Override
        public boolean keyeq(LuaValue luaValue) {
            return luaValue.raweq(this.key);
        }
    }

    private static class LinkSlot
    implements StrongSlot {
        private Entry entry;
        private Slot next;

        LinkSlot(Entry entry, Slot slot) {
            this.entry = entry;
            this.next = slot;
        }

        @Override
        public LuaValue key() {
            return this.entry.key();
        }

        @Override
        public int keyindex(int n) {
            return this.entry.keyindex(n);
        }

        @Override
        public LuaValue value() {
            return this.entry.value();
        }

        @Override
        public Varargs toVarargs() {
            return this.entry.toVarargs();
        }

        @Override
        public StrongSlot first() {
            return this.entry;
        }

        @Override
        public StrongSlot find(LuaValue luaValue) {
            return this.entry.keyeq(luaValue) ? this : null;
        }

        @Override
        public boolean keyeq(LuaValue luaValue) {
            return this.entry.keyeq(luaValue);
        }

        @Override
        public Slot rest() {
            return this.next;
        }

        @Override
        public int arraykey(int n) {
            return this.entry.arraykey(n);
        }

        @Override
        public Slot set(StrongSlot strongSlot, LuaValue luaValue) {
            if (strongSlot == this) {
                this.entry = this.entry.set(luaValue);
                return this;
            }
            return this.setnext(this.next.set(strongSlot, luaValue));
        }

        @Override
        public Slot add(Slot slot) {
            return this.setnext(this.next.add(slot));
        }

        @Override
        public Slot remove(StrongSlot strongSlot) {
            if (this == strongSlot) {
                return new DeadSlot(this.key(), this.next);
            }
            this.next = this.next.remove(strongSlot);
            return this;
        }

        @Override
        public Slot relink(Slot slot) {
            return slot != null ? new LinkSlot(this.entry, slot) : this.entry;
        }

        private Slot setnext(Slot slot) {
            if (slot != null) {
                this.next = slot;
                return this;
            }
            return this.entry;
        }

        public String toString() {
            return this.entry + "; " + this.next;
        }
    }
}

