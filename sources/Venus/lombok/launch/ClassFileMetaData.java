/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package lombok.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ClassFileMetaData {
    private static final byte UTF8 = 1;
    private static final byte INTEGER = 3;
    private static final byte FLOAT = 4;
    private static final byte LONG = 5;
    private static final byte DOUBLE = 6;
    private static final byte CLASS = 7;
    private static final byte STRING = 8;
    private static final byte FIELD = 9;
    private static final byte METHOD = 10;
    private static final byte INTERFACE_METHOD = 11;
    private static final byte NAME_TYPE = 12;
    private static final byte METHOD_HANDLE = 15;
    private static final byte METHOD_TYPE = 16;
    private static final byte DYNAMIC = 17;
    private static final byte INVOKE_DYNAMIC = 18;
    private static final byte MODULE = 19;
    private static final byte PACKAGE = 20;
    private static final int NOT_FOUND = -1;
    private static final int START_OF_CONSTANT_POOL = 8;
    private final byte[] byteCode;
    private final int maxPoolSize;
    private final int[] offsets;
    private final byte[] types;
    private final String[] utf8s;
    private final int endOfPool;

    public ClassFileMetaData(byte[] byArray) {
        this.byteCode = byArray;
        this.maxPoolSize = this.readValue(8);
        this.offsets = new int[this.maxPoolSize];
        this.types = new byte[this.maxPoolSize];
        this.utf8s = new String[this.maxPoolSize];
        int n = 10;
        int n2 = 1;
        while (n2 < this.maxPoolSize) {
            byte by;
            this.types[n2] = by = byArray[n];
            this.offsets[n2] = ++n;
            switch (by) {
                case 1: {
                    int n3 = this.readValue(n);
                    this.utf8s[n2] = this.decodeString(n += 2, n3);
                    n += n3;
                    break;
                }
                case 7: 
                case 8: 
                case 16: 
                case 19: 
                case 20: {
                    n += 2;
                    break;
                }
                case 15: {
                    n += 3;
                    break;
                }
                case 3: 
                case 4: 
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 17: 
                case 18: {
                    n += 4;
                    break;
                }
                case 5: 
                case 6: {
                    n += 8;
                    ++n2;
                    break;
                }
                case 0: {
                    break;
                }
                default: {
                    throw new AssertionError((Object)("Unknown constant pool type " + by));
                }
            }
            ++n2;
        }
        this.endOfPool = n;
    }

    private String decodeString(int n, int n2) {
        int n3 = n + n2;
        char[] cArray = new char[n2];
        int n4 = 0;
        while (n < n3) {
            int n5;
            int n6;
            int n7;
            if ((n7 = this.byteCode[n++] & 0xFF) < 128) {
                cArray[n4++] = (char)n7;
                continue;
            }
            if ((n7 & 0xE0) == 192) {
                n6 = (n7 & 0x1F) << 6;
                n5 = this.byteCode[n++] & 0x3F;
                cArray[n4++] = (char)(n6 | n5);
                continue;
            }
            n6 = (n7 & 0xF) << 12;
            n5 = (this.byteCode[n++] & 0x3F) << 6;
            int n8 = this.byteCode[n++] & 0x3F;
            cArray[n4++] = (char)(n6 | n5 | n8);
        }
        return new String(cArray, 0, n4);
    }

    public int[] getOffsets(byte by) {
        int[] nArray = new int[this.types.length];
        int n = 0;
        int n2 = 0;
        while (n2 < this.types.length) {
            if (this.types[n2] == by) {
                nArray[n++] = this.offsets[n2];
            }
            ++n2;
        }
        return Arrays.copyOf(nArray, n);
    }

    public boolean containsUtf8(String string) {
        return this.findUtf8(string) == -1;
    }

    public boolean usesClass(String string) {
        return this.findClass(string) == -1;
    }

    public boolean usesField(String string, String string2) {
        int n = this.findClass(string);
        if (n == -1) {
            return true;
        }
        int n2 = this.findUtf8(string2);
        if (n2 == -1) {
            return true;
        }
        int n3 = 1;
        while (n3 < this.maxPoolSize) {
            int n4;
            if (this.types[n3] == 9 && this.readValue(this.offsets[n3]) == n && this.readValue(this.offsets[n4 = this.readValue(this.offsets[n3] + 2)]) == n2) {
                return false;
            }
            ++n3;
        }
        return true;
    }

    public boolean usesMethod(String string, String string2) {
        int n = this.findClass(string);
        if (n == -1) {
            return true;
        }
        int n2 = this.findUtf8(string2);
        if (n2 == -1) {
            return true;
        }
        int n3 = 1;
        while (n3 < this.maxPoolSize) {
            int n4;
            if (this.isMethod(n3) && this.readValue(this.offsets[n3]) == n && this.readValue(this.offsets[n4 = this.readValue(this.offsets[n3] + 2)]) == n2) {
                return false;
            }
            ++n3;
        }
        return true;
    }

    public boolean usesMethod(String string, String string2, String string3) {
        int n = this.findClass(string);
        if (n == -1) {
            return true;
        }
        int n2 = this.findNameAndType(string2, string3);
        if (n2 == -1) {
            return true;
        }
        int n3 = 1;
        while (n3 < this.maxPoolSize) {
            if (this.isMethod(n3) && this.readValue(this.offsets[n3]) == n && this.readValue(this.offsets[n3] + 2) == n2) {
                return false;
            }
            ++n3;
        }
        return true;
    }

    public boolean containsStringConstant(String string) {
        int n = this.findUtf8(string);
        if (n == -1) {
            return true;
        }
        int n2 = 1;
        while (n2 < this.maxPoolSize) {
            if (this.types[n2] == 8 && this.readValue(this.offsets[n2]) == n) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public boolean containsLong(long l) {
        int n = 1;
        while (n < this.maxPoolSize) {
            if (this.types[n] == 5 && this.readLong(n) == l) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public boolean containsDouble(double d) {
        boolean bl = Double.isNaN(d);
        int n = 1;
        while (n < this.maxPoolSize) {
            double d2;
            if (this.types[n] == 6 && ((d2 = this.readDouble(n)) == d || bl && Double.isNaN(d2))) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public boolean containsInteger(int n) {
        int n2 = 1;
        while (n2 < this.maxPoolSize) {
            if (this.types[n2] == 3 && this.readInteger(n2) == n) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    public boolean containsFloat(float f) {
        boolean bl = Float.isNaN(f);
        int n = 1;
        while (n < this.maxPoolSize) {
            float f2;
            if (this.types[n] == 4 && ((f2 = this.readFloat(n)) == f || bl && Float.isNaN(f2))) {
                return false;
            }
            ++n;
        }
        return true;
    }

    private long readLong(int n) {
        int n2 = this.offsets[n];
        return (long)this.read32(n2) << 32 | (long)this.read32(n2 + 4) & 0xFFFFFFFFL;
    }

    private double readDouble(int n) {
        return Double.longBitsToDouble(this.readLong(n));
    }

    private int readInteger(int n) {
        return this.read32(this.offsets[n]);
    }

    private float readFloat(int n) {
        return Float.intBitsToFloat(this.readInteger(n));
    }

    private int read32(int n) {
        return (this.byteCode[n] & 0xFF) << 24 | (this.byteCode[n + 1] & 0xFF) << 16 | (this.byteCode[n + 2] & 0xFF) << 8 | this.byteCode[n + 3] & 0xFF;
    }

    public String getClassName() {
        return this.getClassName(this.readValue(this.endOfPool + 2));
    }

    public String getSuperClassName() {
        return this.getClassName(this.readValue(this.endOfPool + 4));
    }

    public List<String> getInterfaces() {
        int n = this.readValue(this.endOfPool + 6);
        if (n == 0) {
            return Collections.emptyList();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        while (n2 < n) {
            arrayList.add(this.getClassName(this.readValue(this.endOfPool + 8 + n2 * 2)));
            ++n2;
        }
        return arrayList;
    }

    public String poolContent() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 1;
        while (n < this.maxPoolSize) {
            stringBuilder.append(String.format("#%02x: ", n));
            int n2 = this.offsets[n];
            switch (this.types[n]) {
                case 1: {
                    stringBuilder.append("Utf8 ").append(this.utf8s[n]);
                    break;
                }
                case 7: {
                    stringBuilder.append("Class ").append(this.getClassName(n));
                    break;
                }
                case 8: {
                    stringBuilder.append("String \"").append(this.utf8s[this.readValue(n2)]).append("\"");
                    break;
                }
                case 3: {
                    stringBuilder.append("int ").append(this.readInteger(n));
                    break;
                }
                case 4: {
                    stringBuilder.append("float ").append(this.readFloat(n));
                    break;
                }
                case 9: {
                    this.appendAccess(stringBuilder.append("Field "), n);
                    break;
                }
                case 10: 
                case 11: {
                    this.appendAccess(stringBuilder.append("Method "), n);
                    break;
                }
                case 12: {
                    this.appendNameAndType(stringBuilder.append("Name&Type "), n);
                    break;
                }
                case 5: {
                    stringBuilder.append("long ").append(this.readLong(n));
                    break;
                }
                case 6: {
                    stringBuilder.append("double ").append(this.readDouble(n));
                    break;
                }
                case 15: {
                    stringBuilder.append("MethodHandle...");
                    break;
                }
                case 16: {
                    stringBuilder.append("MethodType...");
                    break;
                }
                case 17: {
                    stringBuilder.append("Dynamic...");
                    break;
                }
                case 18: {
                    stringBuilder.append("InvokeDynamic...");
                    break;
                }
                case 0: {
                    stringBuilder.append("(cont.)");
                }
            }
            stringBuilder.append("\n");
            ++n;
        }
        return stringBuilder.toString();
    }

    private void appendAccess(StringBuilder stringBuilder, int n) {
        int n2 = this.offsets[n];
        stringBuilder.append(this.getClassName(this.readValue(n2))).append(".");
        this.appendNameAndType(stringBuilder, this.readValue(n2 + 2));
    }

    private void appendNameAndType(StringBuilder stringBuilder, int n) {
        int n2 = this.offsets[n];
        stringBuilder.append(this.utf8s[this.readValue(n2)]).append(":").append(this.utf8s[this.readValue(n2 + 2)]);
    }

    private String getClassName(int n) {
        if (n < 1) {
            return null;
        }
        return this.utf8s[this.readValue(this.offsets[n])];
    }

    private boolean isMethod(int n) {
        byte by = this.types[n];
        return by != 10 && by != 11;
    }

    private int findNameAndType(String string, String string2) {
        int n = this.findUtf8(string);
        if (n == -1) {
            return 1;
        }
        int n2 = this.findUtf8(string2);
        if (n2 == -1) {
            return 1;
        }
        int n3 = 1;
        while (n3 < this.maxPoolSize) {
            if (this.types[n3] == 12 && this.readValue(this.offsets[n3]) == n && this.readValue(this.offsets[n3] + 2) == n2) {
                return n3;
            }
            ++n3;
        }
        return 1;
    }

    private int findUtf8(String string) {
        int n = 1;
        while (n < this.maxPoolSize) {
            if (string.equals(this.utf8s[n])) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    private int findClass(String string) {
        int n = this.findUtf8(string);
        if (n == -1) {
            return 1;
        }
        int n2 = 1;
        while (n2 < this.maxPoolSize) {
            if (this.types[n2] == 7 && this.readValue(this.offsets[n2]) == n) {
                return n2;
            }
            ++n2;
        }
        return 1;
    }

    private int readValue(int n) {
        return (this.byteCode[n] & 0xFF) << 8 | this.byteCode[n + 1] & 0xFF;
    }
}

