/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package lombok.launch;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import lombok.launch.ClassFileMetaData;

class PackageShader {
    private final byte[][] froms;
    private final byte[][] tos;
    private static final byte CONSTANTPOOLTYPE_UTF8 = 1;

    public PackageShader(String ... stringArray) {
        if (stringArray.length % 2 != 0) {
            throw new IllegalArgumentException("Provide pairs: real package name to shaded package name (you provided an odd number of strings; even number required)");
        }
        Charset charset = Charset.forName("US-ASCII");
        int n = stringArray.length / 2;
        this.froms = new byte[n][];
        this.tos = new byte[n][];
        int n2 = 0;
        while (n2 < n) {
            String string = stringArray[n2 << 1];
            String string2 = stringArray[n2 << 1 | 1];
            if (string.contains(".") || string2.contains(".")) {
                throw new IllegalArgumentException("Binary name prefixes are required (use slashes and dollars instead of dots to separate type name elements); they look like e.g. 'java/util/'. Violating entry: " + string + " -> " + string2);
            }
            this.froms[n2] = string.getBytes(charset);
            this.tos[n2] = string2.getBytes(charset);
            if (this.froms[n2].length != this.tos[n2].length) {
                throw new IllegalArgumentException("Pair [" + string + " -> " + string2 + "] is invalid: Both strings must be the same length");
            }
            ++n2;
        }
    }

    public boolean apply(byte[] byArray) {
        ClassFileMetaData classFileMetaData = new ClassFileMetaData(byArray);
        boolean bl = false;
        int[] nArray = new int[260];
        int[] nArray2 = classFileMetaData.getOffsets((byte)1);
        int n = nArray2.length;
        int n2 = 0;
        while (n2 < n) {
            int n3 = nArray2[n2];
            int n4 = PackageShader.readValue(byArray, n3);
            nArray[0] = n3 += 2;
            int n5 = 1;
            int n6 = n3;
            int n7 = n3 + n4;
            while (n6 < n7) {
                if (byArray[n6] == 76) {
                    nArray[n5++] = n6 + 1;
                }
                ++n6;
            }
            n6 = 0;
            while (n6 < this.froms.length) {
                n7 = 0;
                while (n7 < n5) {
                    block8: {
                        int n8 = nArray[n7];
                        if (n4 - (n8 - n3) >= this.froms[n6].length) {
                            int n9 = 0;
                            while (n9 < this.froms[n6].length) {
                                if (byArray[n8 + n9] == this.froms[n6][n9]) {
                                    ++n9;
                                    continue;
                                }
                                break block8;
                            }
                            System.arraycopy(this.tos[n6], 0, byArray, n8, this.tos[n6].length);
                            bl = true;
                        }
                    }
                    ++n7;
                }
                ++n6;
            }
            ++n2;
        }
        return bl;
    }

    private static int readValue(byte[] byArray, int n) {
        return (byArray[n] & 0xFF) << 8 | byArray[n + 1] & 0xFF;
    }

    public String reverseResourceName(String string) {
        int n = 0;
        while (n < this.tos.length) {
            block6: {
                int n2 = this.tos[n].length;
                if (string.length() >= n2) {
                    int n3 = 0;
                    while (n3 < n2) {
                        if (string.charAt(n3) == this.tos[n][n3]) {
                            ++n3;
                            continue;
                        }
                        break block6;
                    }
                    try {
                        String string2 = String.valueOf(new String(this.froms[n], 0, this.froms[n].length, "US-ASCII")) + string.substring(n2);
                        return string2;
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        return string;
                    }
                }
            }
            ++n;
        }
        return string;
    }
}

