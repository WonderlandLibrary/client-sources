/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.zip;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ExtraFieldUtils;
import org.apache.commons.compress.archivers.zip.GeneralPurposeBit;
import org.apache.commons.compress.archivers.zip.UnparseableExtraFieldData;
import org.apache.commons.compress.archivers.zip.ZipExtraField;
import org.apache.commons.compress.archivers.zip.ZipShort;

public class ZipArchiveEntry
extends ZipEntry
implements ArchiveEntry {
    public static final int PLATFORM_UNIX = 3;
    public static final int PLATFORM_FAT = 0;
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_SHIFT = 16;
    private static final byte[] EMPTY = new byte[0];
    private int method = -1;
    private long size = -1L;
    private int internalAttributes = 0;
    private int platform = 0;
    private long externalAttributes = 0L;
    private LinkedHashMap<ZipShort, ZipExtraField> extraFields = null;
    private UnparseableExtraFieldData unparseableExtra = null;
    private String name = null;
    private byte[] rawName = null;
    private GeneralPurposeBit gpb = new GeneralPurposeBit();

    public ZipArchiveEntry(String string) {
        super(string);
        this.setName(string);
    }

    public ZipArchiveEntry(ZipEntry zipEntry) throws ZipException {
        super(zipEntry);
        this.setName(zipEntry.getName());
        byte[] byArray = zipEntry.getExtra();
        if (byArray != null) {
            this.setExtraFields(ExtraFieldUtils.parse(byArray, true, ExtraFieldUtils.UnparseableExtraField.READ));
        } else {
            this.setExtra();
        }
        this.setMethod(zipEntry.getMethod());
        this.size = zipEntry.getSize();
    }

    public ZipArchiveEntry(ZipArchiveEntry zipArchiveEntry) throws ZipException {
        this((ZipEntry)zipArchiveEntry);
        this.setInternalAttributes(zipArchiveEntry.getInternalAttributes());
        this.setExternalAttributes(zipArchiveEntry.getExternalAttributes());
        this.setExtraFields(zipArchiveEntry.getExtraFields(false));
    }

    protected ZipArchiveEntry() {
        this("");
    }

    public ZipArchiveEntry(File file, String string) {
        this(file.isDirectory() && !string.endsWith("/") ? string + "/" : string);
        if (file.isFile()) {
            this.setSize(file.length());
        }
        this.setTime(file.lastModified());
    }

    public Object clone() {
        ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)super.clone();
        zipArchiveEntry.setInternalAttributes(this.getInternalAttributes());
        zipArchiveEntry.setExternalAttributes(this.getExternalAttributes());
        zipArchiveEntry.setExtraFields(this.getExtraFields(false));
        return zipArchiveEntry;
    }

    public int getMethod() {
        return this.method;
    }

    public void setMethod(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("ZIP compression method can not be negative: " + n);
        }
        this.method = n;
    }

    public int getInternalAttributes() {
        return this.internalAttributes;
    }

    public void setInternalAttributes(int n) {
        this.internalAttributes = n;
    }

    public long getExternalAttributes() {
        return this.externalAttributes;
    }

    public void setExternalAttributes(long l) {
        this.externalAttributes = l;
    }

    public void setUnixMode(int n) {
        this.setExternalAttributes(n << 16 | ((n & 0x80) == 0 ? 1 : 0) | (this.isDirectory() ? 16 : 0));
        this.platform = 3;
    }

    public int getUnixMode() {
        return this.platform != 3 ? 0 : (int)(this.getExternalAttributes() >> 16 & 0xFFFFL);
    }

    public boolean isUnixSymlink() {
        return (this.getUnixMode() & 0xA000) == 40960;
    }

    public int getPlatform() {
        return this.platform;
    }

    protected void setPlatform(int n) {
        this.platform = n;
    }

    public void setExtraFields(ZipExtraField[] zipExtraFieldArray) {
        this.extraFields = new LinkedHashMap();
        for (ZipExtraField zipExtraField : zipExtraFieldArray) {
            if (zipExtraField instanceof UnparseableExtraFieldData) {
                this.unparseableExtra = (UnparseableExtraFieldData)zipExtraField;
                continue;
            }
            this.extraFields.put(zipExtraField.getHeaderId(), zipExtraField);
        }
        this.setExtra();
    }

    public ZipExtraField[] getExtraFields() {
        return this.getExtraFields(true);
    }

    public ZipExtraField[] getExtraFields(boolean bl) {
        if (this.extraFields == null) {
            ZipExtraField[] zipExtraFieldArray;
            if (!bl || this.unparseableExtra == null) {
                zipExtraFieldArray = new ZipExtraField[]{};
            } else {
                ZipExtraField[] zipExtraFieldArray2 = new ZipExtraField[1];
                zipExtraFieldArray = zipExtraFieldArray2;
                zipExtraFieldArray2[0] = this.unparseableExtra;
            }
            return zipExtraFieldArray;
        }
        ArrayList<ZipExtraField> arrayList = new ArrayList<ZipExtraField>(this.extraFields.values());
        if (bl && this.unparseableExtra != null) {
            arrayList.add(this.unparseableExtra);
        }
        return arrayList.toArray(new ZipExtraField[0]);
    }

    public void addExtraField(ZipExtraField zipExtraField) {
        if (zipExtraField instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData)zipExtraField;
        } else {
            if (this.extraFields == null) {
                this.extraFields = new LinkedHashMap();
            }
            this.extraFields.put(zipExtraField.getHeaderId(), zipExtraField);
        }
        this.setExtra();
    }

    public void addAsFirstExtraField(ZipExtraField zipExtraField) {
        if (zipExtraField instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData)zipExtraField;
        } else {
            LinkedHashMap<ZipShort, ZipExtraField> linkedHashMap = this.extraFields;
            this.extraFields = new LinkedHashMap();
            this.extraFields.put(zipExtraField.getHeaderId(), zipExtraField);
            if (linkedHashMap != null) {
                linkedHashMap.remove(zipExtraField.getHeaderId());
                this.extraFields.putAll(linkedHashMap);
            }
        }
        this.setExtra();
    }

    public void removeExtraField(ZipShort zipShort) {
        if (this.extraFields == null) {
            throw new NoSuchElementException();
        }
        if (this.extraFields.remove(zipShort) == null) {
            throw new NoSuchElementException();
        }
        this.setExtra();
    }

    public void removeUnparseableExtraFieldData() {
        if (this.unparseableExtra == null) {
            throw new NoSuchElementException();
        }
        this.unparseableExtra = null;
        this.setExtra();
    }

    public ZipExtraField getExtraField(ZipShort zipShort) {
        if (this.extraFields != null) {
            return this.extraFields.get(zipShort);
        }
        return null;
    }

    public UnparseableExtraFieldData getUnparseableExtraFieldData() {
        return this.unparseableExtra;
    }

    public void setExtra(byte[] byArray) throws RuntimeException {
        try {
            ZipExtraField[] zipExtraFieldArray = ExtraFieldUtils.parse(byArray, true, ExtraFieldUtils.UnparseableExtraField.READ);
            this.mergeExtraFields(zipExtraFieldArray, true);
        } catch (ZipException zipException) {
            throw new RuntimeException("Error parsing extra fields for entry: " + this.getName() + " - " + zipException.getMessage(), zipException);
        }
    }

    protected void setExtra() {
        super.setExtra(ExtraFieldUtils.mergeLocalFileDataData(this.getExtraFields(false)));
    }

    public void setCentralDirectoryExtra(byte[] byArray) {
        try {
            ZipExtraField[] zipExtraFieldArray = ExtraFieldUtils.parse(byArray, false, ExtraFieldUtils.UnparseableExtraField.READ);
            this.mergeExtraFields(zipExtraFieldArray, false);
        } catch (ZipException zipException) {
            throw new RuntimeException(zipException.getMessage(), zipException);
        }
    }

    public byte[] getLocalFileDataExtra() {
        byte[] byArray = this.getExtra();
        return byArray != null ? byArray : EMPTY;
    }

    public byte[] getCentralDirectoryExtra() {
        return ExtraFieldUtils.mergeCentralDirectoryData(this.getExtraFields(false));
    }

    public String getName() {
        return this.name == null ? super.getName() : this.name;
    }

    public boolean isDirectory() {
        return this.getName().endsWith("/");
    }

    protected void setName(String string) {
        if (string != null && this.getPlatform() == 0 && string.indexOf("/") == -1) {
            string = string.replace('\\', '/');
        }
        this.name = string;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("invalid entry size");
        }
        this.size = l;
    }

    protected void setName(String string, byte[] byArray) {
        this.setName(string);
        this.rawName = byArray;
    }

    public byte[] getRawName() {
        if (this.rawName != null) {
            byte[] byArray = new byte[this.rawName.length];
            System.arraycopy(this.rawName, 0, byArray, 0, this.rawName.length);
            return byArray;
        }
        return null;
    }

    public int hashCode() {
        return this.getName().hashCode();
    }

    public GeneralPurposeBit getGeneralPurposeBit() {
        return this.gpb;
    }

    public void setGeneralPurposeBit(GeneralPurposeBit generalPurposeBit) {
        this.gpb = generalPurposeBit;
    }

    private void mergeExtraFields(ZipExtraField[] zipExtraFieldArray, boolean bl) throws ZipException {
        if (this.extraFields == null) {
            this.setExtraFields(zipExtraFieldArray);
        } else {
            for (ZipExtraField zipExtraField : zipExtraFieldArray) {
                byte[] byArray;
                ZipExtraField zipExtraField2 = zipExtraField instanceof UnparseableExtraFieldData ? this.unparseableExtra : this.getExtraField(zipExtraField.getHeaderId());
                if (zipExtraField2 == null) {
                    this.addExtraField(zipExtraField);
                    continue;
                }
                if (bl) {
                    byArray = zipExtraField.getLocalFileDataData();
                    zipExtraField2.parseFromLocalFileData(byArray, 0, byArray.length);
                    continue;
                }
                byArray = zipExtraField.getCentralDirectoryData();
                zipExtraField2.parseFromCentralDirectoryData(byArray, 0, byArray.length);
            }
            this.setExtra();
        }
    }

    public Date getLastModifiedDate() {
        return new Date(this.getTime());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry)object;
        String string = this.getName();
        String string2 = zipArchiveEntry.getName();
        if (string == null ? string2 != null : !string.equals(string2)) {
            return true;
        }
        String string3 = this.getComment();
        String string4 = zipArchiveEntry.getComment();
        if (string3 == null) {
            string3 = "";
        }
        if (string4 == null) {
            string4 = "";
        }
        return this.getTime() == zipArchiveEntry.getTime() && string3.equals(string4) && this.getInternalAttributes() == zipArchiveEntry.getInternalAttributes() && this.getPlatform() == zipArchiveEntry.getPlatform() && this.getExternalAttributes() == zipArchiveEntry.getExternalAttributes() && this.getMethod() == zipArchiveEntry.getMethod() && this.getSize() == zipArchiveEntry.getSize() && this.getCrc() == zipArchiveEntry.getCrc() && this.getCompressedSize() == zipArchiveEntry.getCompressedSize() && Arrays.equals(this.getCentralDirectoryExtra(), zipArchiveEntry.getCentralDirectoryExtra()) && Arrays.equals(this.getLocalFileDataExtra(), zipArchiveEntry.getLocalFileDataExtra()) && this.gpb.equals(zipArchiveEntry.gpb);
    }
}

