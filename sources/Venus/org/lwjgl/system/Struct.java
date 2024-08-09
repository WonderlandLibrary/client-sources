/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;
import org.lwjgl.system.Pointer;

public abstract class Struct
extends Pointer.Default {
    protected static final int DEFAULT_PACK_ALIGNMENT = Platform.get() == Platform.WINDOWS ? 8 : 0x40000000;
    protected static final int DEFAULT_ALIGN_AS = 0;
    private static final long CONTAINER;
    @Nullable
    private ByteBuffer container;

    protected Struct(long l, @Nullable ByteBuffer byteBuffer) {
        super(l);
        this.container = byteBuffer;
    }

    public abstract int sizeof();

    public void clear() {
        MemoryUtil.memSet(this.address(), 0, this.sizeof());
    }

    public void free() {
        MemoryUtil.nmemFree(this.address());
    }

    public boolean isNull(int n) {
        if (Checks.DEBUG) {
            this.checkMemberOffset(n);
        }
        return MemoryUtil.memGetAddress(this.address() + (long)n) == 0L;
    }

    protected static <T extends Struct> T wrap(Class<T> clazz, long l) {
        Struct struct;
        try {
            struct = (Struct)UNSAFE.allocateInstance(clazz);
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)struct, ADDRESS, l);
        return (T)struct;
    }

    protected static <T extends Struct> T wrap(Class<T> clazz, long l, ByteBuffer byteBuffer) {
        Struct struct;
        try {
            struct = (Struct)UNSAFE.allocateInstance(clazz);
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)struct, ADDRESS, l);
        UNSAFE.putObject((Object)struct, CONTAINER, (Object)byteBuffer);
        return (T)struct;
    }

    <T extends Struct> T wrap(long l, int n, @Nullable ByteBuffer byteBuffer) {
        Struct struct;
        try {
            struct = (Struct)UNSAFE.allocateInstance(this.getClass());
        } catch (InstantiationException instantiationException) {
            throw new UnsupportedOperationException(instantiationException);
        }
        UNSAFE.putLong((Object)struct, ADDRESS, l + Integer.toUnsignedLong(n) * (long)this.sizeof());
        UNSAFE.putObject((Object)struct, CONTAINER, (Object)byteBuffer);
        return (T)struct;
    }

    private void checkMemberOffset(int n) {
        if (n < 0 || this.sizeof() - n < POINTER_SIZE) {
            throw new IllegalArgumentException("Invalid member offset.");
        }
    }

    protected static ByteBuffer __checkContainer(ByteBuffer byteBuffer, int n) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, n);
        }
        return byteBuffer;
    }

    private static long getBytes(int n, int n2) {
        return ((long)n & 0xFFFFFFFFL) * (long)n2;
    }

    protected static long __checkMalloc(int n, int n2) {
        long l = ((long)n & 0xFFFFFFFFL) * (long)n2;
        if (Checks.DEBUG) {
            if (n < 0) {
                throw new IllegalArgumentException("Invalid number of elements");
            }
            if (BITS32 && 0xFFFFFFFFL < l) {
                throw new IllegalArgumentException("The request allocation is too large");
            }
        }
        return l;
    }

    protected static ByteBuffer __create(int n, int n2) {
        APIUtil.apiCheckAllocation(n, Struct.getBytes(n, n2), Integer.MAX_VALUE);
        return ByteBuffer.allocateDirect(n * n2).order(ByteOrder.nativeOrder());
    }

    protected static Member __padding(int n, boolean bl) {
        return Struct.__member(bl ? n : 0, 1);
    }

    protected static Member __member(int n) {
        return Struct.__member(n, n);
    }

    protected static Member __member(int n, int n2) {
        return Struct.__member(n, n2, false);
    }

    protected static Member __member(int n, int n2, boolean bl) {
        return new Member(n, n2, bl);
    }

    protected static Member __array(int n, int n2) {
        return Struct.__array(n, n, n2);
    }

    protected static Member __array(int n, int n2, int n3) {
        return new Member(n * n3, n2, false);
    }

    protected static Member __array(int n, int n2, boolean bl, int n3) {
        return new Member(n * n3, n2, bl);
    }

    protected static Layout __union(Member ... memberArray) {
        return Struct.__union(DEFAULT_PACK_ALIGNMENT, 0, memberArray);
    }

    protected static Layout __union(int n, int n2, Member ... memberArray) {
        ArrayList<Member> arrayList = new ArrayList<Member>(memberArray.length);
        int n3 = 0;
        int n4 = n2;
        for (Member member : memberArray) {
            n3 = Math.max(n3, member.size);
            n4 = Math.max(n4, member.getAlignment(n));
            member.offset = 0;
            arrayList.add(member);
            if (!(member instanceof Layout)) continue;
            Struct.addNestedMembers(member, arrayList, 0);
        }
        return new Layout(n3, n4, n2 != 0, arrayList.toArray(new Member[0]));
    }

    protected static Layout __struct(Member ... memberArray) {
        return Struct.__struct(DEFAULT_PACK_ALIGNMENT, 0, memberArray);
    }

    protected static Layout __struct(int n, int n2, Member ... memberArray) {
        ArrayList<Member> arrayList = new ArrayList<Member>(memberArray.length);
        int n3 = 0;
        int n4 = n2;
        for (Member member : memberArray) {
            int n5 = member.getAlignment(n);
            member.offset = Struct.align(n3, n5);
            n3 = member.offset + member.size;
            n4 = Math.max(n4, n5);
            arrayList.add(member);
            if (!(member instanceof Layout)) continue;
            Struct.addNestedMembers(member, arrayList, member.offset);
        }
        n3 = Struct.align(n3, n4);
        return new Layout(n3, n4, n2 != 0, arrayList.toArray(new Member[0]));
    }

    private static void addNestedMembers(Member member, List<Member> list, int n) {
        Layout layout = (Layout)member;
        for (Member member2 : layout.members) {
            member2.offset += n;
            list.add(member2);
        }
    }

    private static int align(int n, int n2) {
        return (n - 1 | n2 - 1) + 1;
    }

    static {
        Library.initialize();
        try {
            CONTAINER = UNSAFE.objectFieldOffset(Struct.class.getDeclaredField("container"));
        } catch (Throwable throwable) {
            throw new UnsupportedOperationException(throwable);
        }
    }

    protected static class Layout
    extends Member {
        final Member[] members;

        Layout(int n, int n2, boolean bl, Member[] memberArray) {
            super(n, n2, bl);
            this.members = memberArray;
        }

        public int offsetof(int n) {
            return this.members[n].offset;
        }
    }

    protected static class Member {
        final int size;
        final int alignment;
        final boolean forcedAlignment;
        int offset;

        Member(int n, int n2, boolean bl) {
            this.size = n;
            this.alignment = n2;
            this.forcedAlignment = bl;
        }

        public int getSize() {
            return this.size;
        }

        public int getAlignment() {
            return this.alignment;
        }

        public int getAlignment(int n) {
            return this.forcedAlignment ? this.alignment : Math.min(this.alignment, n);
        }
    }
}

