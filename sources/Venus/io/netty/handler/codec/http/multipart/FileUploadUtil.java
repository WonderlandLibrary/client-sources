/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.handler.codec.http.multipart.FileUpload;

final class FileUploadUtil {
    private FileUploadUtil() {
    }

    static int hashCode(FileUpload fileUpload) {
        return fileUpload.getName().hashCode();
    }

    static boolean equals(FileUpload fileUpload, FileUpload fileUpload2) {
        return fileUpload.getName().equalsIgnoreCase(fileUpload2.getName());
    }

    static int compareTo(FileUpload fileUpload, FileUpload fileUpload2) {
        return fileUpload.getName().compareToIgnoreCase(fileUpload2.getName());
    }
}

