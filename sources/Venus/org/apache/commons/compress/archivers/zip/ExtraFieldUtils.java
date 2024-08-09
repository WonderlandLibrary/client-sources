/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.AsiExtraField;
import org.apache.commons.compress.archivers.zip.JarMarker;
import org.apache.commons.compress.archivers.zip.UnicodeCommentExtraField;
import org.apache.commons.compress.archivers.zip.UnicodePathExtraField;
import org.apache.commons.compress.archivers.zip.UnparseableExtraFieldData;
import org.apache.commons.compress.archivers.zip.UnrecognizedExtraField;
import org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp;
import org.apache.commons.compress.archivers.zip.X7875_NewUnix;
import org.apache.commons.compress.archivers.zip.Zip64ExtendedInformationExtraField;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipShort;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ExtraFieldUtils {
    private static final int WORD = 4;
    private static final Map<ZipShort, Class<?>> implementations = new ConcurrentHashMap();

    public static void register(Class<?> clazz) {
        try {
            ZipExtraField zipExtraField = (ZipExtraField)clazz.newInstance();
            implementations.put(zipExtraField.getHeaderId(), clazz);
        } catch (ClassCastException classCastException) {
            throw new RuntimeException(clazz + " doesn't implement ZipExtraField");
        } catch (InstantiationException instantiationException) {
            throw new RuntimeException(clazz + " is not a concrete class");
        } catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(clazz + "'s no-arg constructor is not public");
        }
    }

    public static ZipExtraField createExtraField(ZipShort zipShort) throws InstantiationException, IllegalAccessException {
        Class<?> clazz = implementations.get(zipShort);
        if (clazz != null) {
            return (ZipExtraField)clazz.newInstance();
        }
        UnrecognizedExtraField unrecognizedExtraField = new UnrecognizedExtraField();
        unrecognizedExtraField.setHeaderId(zipShort);
        return unrecognizedExtraField;
    }

    public static ZipExtraField[] parse(byte[] byArray) throws ZipException {
        return ExtraFieldUtils.parse(byArray, true, UnparseableExtraField.THROW);
    }

    public static ZipExtraField[] parse(byte[] byArray, boolean bl) throws ZipException {
        return ExtraFieldUtils.parse(byArray, bl, UnparseableExtraField.THROW);
    }

    public static ZipExtraField[] parse(byte[] byArray, boolean bl, UnparseableExtraField unparseableExtraField) throws ZipException {
        Object object;
        int n;
        ArrayList<ZipExtraField> arrayList = new ArrayList<ZipExtraField>();
        block8: for (int i = 0; i <= byArray.length - 4; i += n + 4) {
            ZipExtraField zipExtraField;
            object = new ZipShort(byArray, i);
            n = new ZipShort(byArray, i + 2).getValue();
            if (i + 4 + n > byArray.length) {
                switch (unparseableExtraField.getKey()) {
                    case 0: {
                        throw new ZipException("bad extra field starting at " + i + ".  Block length of " + n + " bytes exceeds remaining" + " data of " + (byArray.length - i - 4) + " bytes.");
                    }
                    case 2: {
                        zipExtraField = new UnparseableExtraFieldData();
                        if (bl) {
                            ((UnparseableExtraFieldData)zipExtraField).parseFromLocalFileData(byArray, i, byArray.length - i);
                        } else {
                            ((UnparseableExtraFieldData)zipExtraField).parseFromCentralDirectoryData(byArray, i, byArray.length - i);
                        }
                        arrayList.add(zipExtraField);
                    }
                    case 1: {
                        break block8;
                    }
                    default: {
                        throw new ZipException("unknown UnparseableExtraField key: " + unparseableExtraField.getKey());
                    }
                }
            }
            try {
                zipExtraField = ExtraFieldUtils.createExtraField((ZipShort)object);
                if (bl) {
                    zipExtraField.parseFromLocalFileData(byArray, i + 4, n);
                } else {
                    zipExtraField.parseFromCentralDirectoryData(byArray, i + 4, n);
                }
                arrayList.add(zipExtraField);
                continue;
            } catch (InstantiationException instantiationException) {
                throw (ZipException)new ZipException(instantiationException.getMessage()).initCause(instantiationException);
            } catch (IllegalAccessException illegalAccessException) {
                throw (ZipException)new ZipException(illegalAccessException.getMessage()).initCause(illegalAccessException);
            }
        }
        object = new ZipExtraField[arrayList.size()];
        return arrayList.toArray((T[])object);
    }

    public static byte[] mergeLocalFileDataData(ZipExtraField[] zipExtraFieldArray) {
        byte[] byArray;
        boolean bl = zipExtraFieldArray.length > 0 && zipExtraFieldArray[zipExtraFieldArray.length - 1] instanceof UnparseableExtraFieldData;
        int n = bl ? zipExtraFieldArray.length - 1 : zipExtraFieldArray.length;
        int n2 = 4 * n;
        for (ZipExtraField zipExtraField : zipExtraFieldArray) {
            n2 += zipExtraField.getLocalFileDataLength().getValue();
        }
        byte[] byArray2 = new byte[n2];
        int n3 = 0;
        for (int byArray3 = 0; byArray3 < n; ++byArray3) {
            System.arraycopy(zipExtraFieldArray[byArray3].getHeaderId().getBytes(), 0, byArray2, n3, 2);
            System.arraycopy(zipExtraFieldArray[byArray3].getLocalFileDataLength().getBytes(), 0, byArray2, n3 + 2, 2);
            n3 += 4;
            byte[] byArray4 = zipExtraFieldArray[byArray3].getLocalFileDataData();
            if (byArray4 == null) continue;
            System.arraycopy(byArray4, 0, byArray2, n3, byArray4.length);
            n3 += byArray4.length;
        }
        if (bl && (byArray = zipExtraFieldArray[zipExtraFieldArray.length - 1].getLocalFileDataData()) != null) {
            System.arraycopy(byArray, 0, byArray2, n3, byArray.length);
        }
        return byArray2;
    }

    public static byte[] mergeCentralDirectoryData(ZipExtraField[] zipExtraFieldArray) {
        byte[] byArray;
        boolean bl = zipExtraFieldArray.length > 0 && zipExtraFieldArray[zipExtraFieldArray.length - 1] instanceof UnparseableExtraFieldData;
        int n = bl ? zipExtraFieldArray.length - 1 : zipExtraFieldArray.length;
        int n2 = 4 * n;
        for (ZipExtraField zipExtraField : zipExtraFieldArray) {
            n2 += zipExtraField.getCentralDirectoryLength().getValue();
        }
        byte[] byArray2 = new byte[n2];
        int n3 = 0;
        for (int byArray3 = 0; byArray3 < n; ++byArray3) {
            System.arraycopy(zipExtraFieldArray[byArray3].getHeaderId().getBytes(), 0, byArray2, n3, 2);
            System.arraycopy(zipExtraFieldArray[byArray3].getCentralDirectoryLength().getBytes(), 0, byArray2, n3 + 2, 2);
            n3 += 4;
            byte[] byArray4 = zipExtraFieldArray[byArray3].getCentralDirectoryData();
            if (byArray4 == null) continue;
            System.arraycopy(byArray4, 0, byArray2, n3, byArray4.length);
            n3 += byArray4.length;
        }
        if (bl && (byArray = zipExtraFieldArray[zipExtraFieldArray.length - 1].getCentralDirectoryData()) != null) {
            System.arraycopy(byArray, 0, byArray2, n3, byArray.length);
        }
        return byArray2;
    }

    static {
        ExtraFieldUtils.register(AsiExtraField.class);
        ExtraFieldUtils.register(X5455_ExtendedTimestamp.class);
        ExtraFieldUtils.register(X7875_NewUnix.class);
        ExtraFieldUtils.register(JarMarker.class);
        ExtraFieldUtils.register(UnicodePathExtraField.class);
        ExtraFieldUtils.register(UnicodeCommentExtraField.class);
        ExtraFieldUtils.register(Zip64ExtendedInformationExtraField.class);
    }

    public static final class UnparseableExtraField {
        public static final int THROW_KEY = 0;
        public static final int SKIP_KEY = 1;
        public static final int READ_KEY = 2;
        public static final UnparseableExtraField THROW = new UnparseableExtraField(0);
        public static final UnparseableExtraField SKIP = new UnparseableExtraField(1);
        public static final UnparseableExtraField READ = new UnparseableExtraField(2);
        private final int key;

        private UnparseableExtraField(int n) {
            this.key = n;
        }

        public int getKey() {
            return this.key;
        }
    }
}

