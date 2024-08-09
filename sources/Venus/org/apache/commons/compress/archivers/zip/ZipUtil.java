/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.CRC32;
import org.apache.commons.compress.archivers.zip.AbstractUnicodeExtraField;
import org.apache.commons.compress.archivers.zip.UnicodeCommentExtraField;
import org.apache.commons.compress.archivers.zip.UnicodePathExtraField;
import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.compress.archivers.zip.ZipLong;
import org.apache.commons.compress.archivers.zip.ZipMethod;

public abstract class ZipUtil {
    private static final byte[] DOS_TIME_MIN = ZipLong.getBytes(8448L);

    public static ZipLong toDosTime(Date date) {
        return new ZipLong(ZipUtil.toDosTime(date.getTime()));
    }

    public static byte[] toDosTime(long l) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        int n = calendar.get(1);
        if (n < 1980) {
            return ZipUtil.copy(DOS_TIME_MIN);
        }
        int n2 = calendar.get(2) + 1;
        long l2 = n - 1980 << 25 | n2 << 21 | calendar.get(5) << 16 | calendar.get(11) << 11 | calendar.get(12) << 5 | calendar.get(13) >> 1;
        return ZipLong.getBytes(l2);
    }

    public static long adjustToLong(int n) {
        if (n < 0) {
            return 0x100000000L + (long)n;
        }
        return n;
    }

    public static byte[] reverse(byte[] byArray) {
        int n = byArray.length - 1;
        for (int i = 0; i < byArray.length / 2; ++i) {
            byte by = byArray[i];
            byArray[i] = byArray[n - i];
            byArray[n - i] = by;
        }
        return byArray;
    }

    static long bigToLong(BigInteger bigInteger) {
        if (bigInteger.bitLength() <= 63) {
            return bigInteger.longValue();
        }
        throw new NumberFormatException("The BigInteger cannot fit inside a 64 bit java long: [" + bigInteger + "]");
    }

    static BigInteger longToBig(long l) {
        if (l < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Negative longs < -2^31 not permitted: [" + l + "]");
        }
        if (l < 0L && l >= Integer.MIN_VALUE) {
            l = ZipUtil.adjustToLong((int)l);
        }
        return BigInteger.valueOf(l);
    }

    public static int signedByteToUnsignedInt(byte by) {
        if (by >= 0) {
            return by;
        }
        return 256 + by;
    }

    public static byte unsignedIntToSignedByte(int n) {
        if (n > 255 || n < 0) {
            throw new IllegalArgumentException("Can only convert non-negative integers between [0,255] to byte: [" + n + "]");
        }
        if (n < 128) {
            return (byte)n;
        }
        return (byte)(n - 256);
    }

    public static Date fromDosTime(ZipLong zipLong) {
        long l = zipLong.getValue();
        return new Date(ZipUtil.dosToJavaTime(l));
    }

    public static long dosToJavaTime(long l) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, (int)(l >> 25 & 0x7FL) + 1980);
        calendar.set(2, (int)(l >> 21 & 0xFL) - 1);
        calendar.set(5, (int)(l >> 16) & 0x1F);
        calendar.set(11, (int)(l >> 11) & 0x1F);
        calendar.set(12, (int)(l >> 5) & 0x3F);
        calendar.set(13, (int)(l << 1) & 0x3E);
        calendar.set(14, 0);
        return calendar.getTime().getTime();
    }

    static void setNameAndCommentFromExtraFields(ZipArchiveEntry zipArchiveEntry, byte[] byArray, byte[] byArray2) {
        UnicodeCommentExtraField unicodeCommentExtraField;
        String string;
        UnicodePathExtraField unicodePathExtraField = (UnicodePathExtraField)zipArchiveEntry.getExtraField(UnicodePathExtraField.UPATH_ID);
        String string2 = zipArchiveEntry.getName();
        String string3 = ZipUtil.getUnicodeStringIfOriginalMatches(unicodePathExtraField, byArray);
        if (string3 != null && !string2.equals(string3)) {
            zipArchiveEntry.setName(string3);
        }
        if (byArray2 != null && byArray2.length > 0 && (string = ZipUtil.getUnicodeStringIfOriginalMatches(unicodeCommentExtraField = (UnicodeCommentExtraField)zipArchiveEntry.getExtraField(UnicodeCommentExtraField.UCOM_ID), byArray2)) != null) {
            zipArchiveEntry.setComment(string);
        }
    }

    private static String getUnicodeStringIfOriginalMatches(AbstractUnicodeExtraField abstractUnicodeExtraField, byte[] byArray) {
        if (abstractUnicodeExtraField != null) {
            CRC32 cRC32 = new CRC32();
            cRC32.update(byArray);
            long l = cRC32.getValue();
            if (l == abstractUnicodeExtraField.getNameCRC32()) {
                try {
                    return ZipEncodingHelper.UTF8_ZIP_ENCODING.decode(abstractUnicodeExtraField.getUnicodeName());
                } catch (IOException iOException) {
                    return null;
                }
            }
        }
        return null;
    }

    static byte[] copy(byte[] byArray) {
        if (byArray != null) {
            byte[] byArray2 = new byte[byArray.length];
            System.arraycopy(byArray, 0, byArray2, 0, byArray2.length);
            return byArray2;
        }
        return null;
    }

    static boolean canHandleEntryData(ZipArchiveEntry zipArchiveEntry) {
        return ZipUtil.supportsEncryptionOf(zipArchiveEntry) && ZipUtil.supportsMethodOf(zipArchiveEntry);
    }

    private static boolean supportsEncryptionOf(ZipArchiveEntry zipArchiveEntry) {
        return !zipArchiveEntry.getGeneralPurposeBit().usesEncryption();
    }

    private static boolean supportsMethodOf(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getMethod() == 0 || zipArchiveEntry.getMethod() == ZipMethod.UNSHRINKING.getCode() || zipArchiveEntry.getMethod() == ZipMethod.IMPLODING.getCode() || zipArchiveEntry.getMethod() == 8;
    }

    static void checkRequestedFeatures(ZipArchiveEntry zipArchiveEntry) throws UnsupportedZipFeatureException {
        if (!ZipUtil.supportsEncryptionOf(zipArchiveEntry)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.ENCRYPTION, zipArchiveEntry);
        }
        if (!ZipUtil.supportsMethodOf(zipArchiveEntry)) {
            ZipMethod zipMethod = ZipMethod.getMethodByCode(zipArchiveEntry.getMethod());
            if (zipMethod == null) {
                throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.METHOD, zipArchiveEntry);
            }
            throw new UnsupportedZipFeatureException(zipMethod, zipArchiveEntry);
        }
    }
}

