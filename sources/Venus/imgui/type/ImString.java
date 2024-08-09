/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package imgui.type;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ImString
implements Cloneable,
Comparable<ImString> {
    public static final short DEFAULT_LENGTH = 100;
    public static final short CARET_LEN = 1;
    public final InputData inputData = new InputData(null);
    private byte[] data;
    private String text = "";

    public ImString() {
        this(100);
    }

    public ImString(ImString imString) {
        this(imString.text, imString.data.length);
        this.inputData.allowedChars = imString.inputData.allowedChars;
        this.inputData.isResizable = imString.inputData.isResizable;
        this.inputData.resizeFactor = imString.inputData.resizeFactor;
        this.inputData.size = imString.inputData.size;
        this.inputData.isDirty = imString.inputData.isDirty;
        this.inputData.isResized = imString.inputData.isResized;
    }

    public ImString(int n) {
        this.data = new byte[n + 1];
    }

    public ImString(String string) {
        this.set(string, true, 1);
    }

    public ImString(String string, int n) {
        this(n);
        this.set(string);
    }

    public String get() {
        if (this.inputData.isDirty) {
            this.inputData.isDirty = false;
            this.text = new String(this.data, 0, this.inputData.size, StandardCharsets.UTF_8);
        }
        return this.text;
    }

    public byte[] getData() {
        return this.data;
    }

    public void set(Object object) {
        this.set(String.valueOf(object));
    }

    public void set(ImString imString) {
        this.set(imString.get(), false);
    }

    public void set(ImString imString, boolean bl) {
        this.set(imString.get(), bl);
    }

    public void set(String string) {
        this.set(string, this.inputData.isResizable, this.inputData.resizeFactor);
    }

    public void set(String string, boolean bl) {
        this.set(string, bl, this.inputData.resizeFactor);
    }

    public void set(String string, boolean bl, int n) {
        byte[] byArray = (string == null ? "null" : string).getBytes(StandardCharsets.UTF_8);
        int n2 = this.data == null ? 0 : this.data.length;
        byte[] byArray2 = null;
        if (bl && n2 - 1 < byArray.length) {
            byArray2 = new byte[byArray.length + n + 1];
            this.inputData.size = byArray.length;
        }
        if (byArray2 == null) {
            byArray2 = new byte[n2];
            this.inputData.size = Math.max(0, Math.min(byArray.length, n2 - 1));
        }
        System.arraycopy(byArray, 0, byArray2, 0, Math.min(byArray.length, byArray2.length - 1));
        this.data = byArray2;
        this.inputData.isDirty = true;
    }

    public void resize(int n) {
        if (n < this.data.length) {
            throw new IllegalArgumentException("New size should be greater than current size of the buffer");
        }
        int n2 = n + 1;
        byte[] byArray = new byte[n2];
        System.arraycopy(this.data, 0, byArray, 0, this.data.length);
        this.data = byArray;
    }

    byte[] resizeInternal(int n) {
        this.resize(n + this.inputData.resizeFactor);
        return this.data;
    }

    public int getLength() {
        return this.get().length();
    }

    public int getBufferSize() {
        return this.data.length;
    }

    public boolean isEmpty() {
        return this.getLength() == 0;
    }

    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    public void clear() {
        this.set("");
    }

    public String toString() {
        return this.get();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ImString imString = (ImString)object;
        return Objects.equals(this.text, imString.text);
    }

    public int hashCode() {
        return this.text.hashCode();
    }

    public ImString clone() {
        return new ImString(this);
    }

    @Override
    public int compareTo(ImString imString) {
        return this.get().compareTo(imString.get());
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((ImString)object);
    }

    public static final class InputData {
        private static final short DEFAULT_RESIZE_FACTOR = 10;
        public String allowedChars = "";
        public boolean isResizable;
        public int resizeFactor = 10;
        int size;
        boolean isDirty;
        boolean isResized = false;

        private InputData() {
        }

        InputData(1 var1_1) {
            this();
        }
    }
}

