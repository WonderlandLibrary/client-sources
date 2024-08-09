/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.StreamingNotSupportedException;
import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.commons.compress.archivers.ar.ArArchiveOutputStream;
import org.apache.commons.compress.archivers.arj.ArjArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveOutputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ArchiveStreamFactory {
    public static final String AR = "ar";
    public static final String ARJ = "arj";
    public static final String CPIO = "cpio";
    public static final String DUMP = "dump";
    public static final String JAR = "jar";
    public static final String TAR = "tar";
    public static final String ZIP = "zip";
    public static final String SEVEN_Z = "7z";
    private String entryEncoding = null;

    public String getEntryEncoding() {
        return this.entryEncoding;
    }

    public void setEntryEncoding(String string) {
        this.entryEncoding = string;
    }

    public ArchiveInputStream createArchiveInputStream(String string, InputStream inputStream) throws ArchiveException {
        if (string == null) {
            throw new IllegalArgumentException("Archivername must not be null.");
        }
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream must not be null.");
        }
        if (AR.equalsIgnoreCase(string)) {
            return new ArArchiveInputStream(inputStream);
        }
        if (ARJ.equalsIgnoreCase(string)) {
            if (this.entryEncoding != null) {
                return new ArjArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new ArjArchiveInputStream(inputStream);
        }
        if (ZIP.equalsIgnoreCase(string)) {
            if (this.entryEncoding != null) {
                return new ZipArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new ZipArchiveInputStream(inputStream);
        }
        if (TAR.equalsIgnoreCase(string)) {
            if (this.entryEncoding != null) {
                return new TarArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new TarArchiveInputStream(inputStream);
        }
        if (JAR.equalsIgnoreCase(string)) {
            return new JarArchiveInputStream(inputStream);
        }
        if (CPIO.equalsIgnoreCase(string)) {
            if (this.entryEncoding != null) {
                return new CpioArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new CpioArchiveInputStream(inputStream);
        }
        if (DUMP.equalsIgnoreCase(string)) {
            if (this.entryEncoding != null) {
                return new DumpArchiveInputStream(inputStream, this.entryEncoding);
            }
            return new DumpArchiveInputStream(inputStream);
        }
        if (SEVEN_Z.equalsIgnoreCase(string)) {
            throw new StreamingNotSupportedException(SEVEN_Z);
        }
        throw new ArchiveException("Archiver: " + string + " not found.");
    }

    public ArchiveOutputStream createArchiveOutputStream(String string, OutputStream outputStream) throws ArchiveException {
        if (string == null) {
            throw new IllegalArgumentException("Archivername must not be null.");
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("OutputStream must not be null.");
        }
        if (AR.equalsIgnoreCase(string)) {
            return new ArArchiveOutputStream(outputStream);
        }
        if (ZIP.equalsIgnoreCase(string)) {
            ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputStream);
            if (this.entryEncoding != null) {
                zipArchiveOutputStream.setEncoding(this.entryEncoding);
            }
            return zipArchiveOutputStream;
        }
        if (TAR.equalsIgnoreCase(string)) {
            if (this.entryEncoding != null) {
                return new TarArchiveOutputStream(outputStream, this.entryEncoding);
            }
            return new TarArchiveOutputStream(outputStream);
        }
        if (JAR.equalsIgnoreCase(string)) {
            return new JarArchiveOutputStream(outputStream);
        }
        if (CPIO.equalsIgnoreCase(string)) {
            if (this.entryEncoding != null) {
                return new CpioArchiveOutputStream(outputStream, this.entryEncoding);
            }
            return new CpioArchiveOutputStream(outputStream);
        }
        if (SEVEN_Z.equalsIgnoreCase(string)) {
            throw new StreamingNotSupportedException(SEVEN_Z);
        }
        throw new ArchiveException("Archiver: " + string + " not found.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public ArchiveInputStream createArchiveInputStream(InputStream inputStream) throws ArchiveException {
        block19: {
            TarArchiveInputStream tarArchiveInputStream;
            block20: {
                if (inputStream == null) {
                    throw new IllegalArgumentException("Stream must not be null.");
                }
                if (!inputStream.markSupported()) {
                    throw new IllegalArgumentException("Mark is not supported.");
                }
                byte[] byArray = new byte[12];
                inputStream.mark(byArray.length);
                int n = IOUtils.readFully(inputStream, byArray);
                inputStream.reset();
                if (ZipArchiveInputStream.matches(byArray, n)) {
                    if (this.entryEncoding != null) {
                        return new ZipArchiveInputStream(inputStream, this.entryEncoding);
                    }
                    return new ZipArchiveInputStream(inputStream);
                }
                if (JarArchiveInputStream.matches(byArray, n)) {
                    return new JarArchiveInputStream(inputStream);
                }
                if (ArArchiveInputStream.matches(byArray, n)) {
                    return new ArArchiveInputStream(inputStream);
                }
                if (CpioArchiveInputStream.matches(byArray, n)) {
                    return new CpioArchiveInputStream(inputStream);
                }
                if (ArjArchiveInputStream.matches(byArray, n)) {
                    return new ArjArchiveInputStream(inputStream);
                }
                if (SevenZFile.matches(byArray, n)) {
                    throw new StreamingNotSupportedException(SEVEN_Z);
                }
                byte[] byArray2 = new byte[32];
                inputStream.mark(byArray2.length);
                n = IOUtils.readFully(inputStream, byArray2);
                inputStream.reset();
                if (DumpArchiveInputStream.matches(byArray2, n)) {
                    return new DumpArchiveInputStream(inputStream);
                }
                byte[] byArray3 = new byte[512];
                inputStream.mark(byArray3.length);
                n = IOUtils.readFully(inputStream, byArray3);
                inputStream.reset();
                if (TarArchiveInputStream.matches(byArray3, n)) {
                    if (this.entryEncoding != null) {
                        return new TarArchiveInputStream(inputStream, this.entryEncoding);
                    }
                    return new TarArchiveInputStream(inputStream);
                }
                if (n < 512) break block19;
                tarArchiveInputStream = null;
                tarArchiveInputStream = new TarArchiveInputStream(new ByteArrayInputStream(byArray3));
                if (!tarArchiveInputStream.getNextTarEntry().isCheckSumOK()) break block20;
                TarArchiveInputStream tarArchiveInputStream2 = new TarArchiveInputStream(inputStream);
                IOUtils.closeQuietly(tarArchiveInputStream);
                return tarArchiveInputStream2;
            }
            try {
                IOUtils.closeQuietly(tarArchiveInputStream);
                break block19;
                {
                    catch (Exception exception) {
                        IOUtils.closeQuietly(tarArchiveInputStream);
                        break block19;
                        catch (Throwable throwable) {
                            IOUtils.closeQuietly(tarArchiveInputStream);
                            throw throwable;
                        }
                    }
                }
            } catch (IOException iOException) {
                throw new ArchiveException("Could not use reset and mark operations.", iOException);
            }
        }
        throw new ArchiveException("No Archiver found for the stream signature");
    }
}

