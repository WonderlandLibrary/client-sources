/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.compress.archivers.sevenz.Coder;
import org.apache.commons.compress.archivers.sevenz.CoderBase;

class AES256SHA256Decoder
extends CoderBase {
    AES256SHA256Decoder() {
        super(new Class[0]);
    }

    InputStream decode(InputStream inputStream, Coder coder, byte[] byArray) throws IOException {
        return new InputStream(this, coder, byArray, inputStream){
            private boolean isInitialized;
            private CipherInputStream cipherInputStream;
            final Coder val$coder;
            final byte[] val$passwordBytes;
            final InputStream val$in;
            final AES256SHA256Decoder this$0;
            {
                this.this$0 = aES256SHA256Decoder;
                this.val$coder = coder;
                this.val$passwordBytes = byArray;
                this.val$in = inputStream;
                this.isInitialized = false;
                this.cipherInputStream = null;
            }

            private CipherInputStream init() throws IOException {
                Object object;
                Object object2;
                byte[] byArray;
                if (this.isInitialized) {
                    return this.cipherInputStream;
                }
                int n = 0xFF & this.val$coder.properties[0];
                int n2 = n & 0x3F;
                int n3 = 0xFF & this.val$coder.properties[1];
                int n4 = (n >> 7 & 1) + (n3 >> 4);
                int n5 = (n >> 6 & 1) + (n3 & 0xF);
                if (2 + n4 + n5 > this.val$coder.properties.length) {
                    throw new IOException("Salt size + IV size too long");
                }
                byte[] byArray2 = new byte[n4];
                System.arraycopy(this.val$coder.properties, 2, byArray2, 0, n4);
                byte[] byArray3 = new byte[16];
                System.arraycopy(this.val$coder.properties, 2 + n4, byArray3, 0, n5);
                if (this.val$passwordBytes == null) {
                    throw new IOException("Cannot read encrypted files without a password");
                }
                if (n2 == 63) {
                    byArray = new byte[32];
                    System.arraycopy(byArray2, 0, byArray, 0, n4);
                    System.arraycopy(this.val$passwordBytes, 0, byArray, n4, Math.min(this.val$passwordBytes.length, byArray.length - n4));
                } else {
                    try {
                        object2 = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        IOException iOException = new IOException("SHA-256 is unsupported by your Java implementation");
                        iOException.initCause(noSuchAlgorithmException);
                        throw iOException;
                    }
                    object = new byte[8];
                    block4: for (long i = 0L; i < 1L << n2; ++i) {
                        ((MessageDigest)object2).update(byArray2);
                        ((MessageDigest)object2).update(this.val$passwordBytes);
                        ((MessageDigest)object2).update((byte[])object);
                        for (int j = 0; j < ((byte[])object).length; ++j) {
                            Object object3 = object;
                            int n6 = j;
                            object3[n6] = (byte)(object3[n6] + 1);
                            if (object[j] != false) continue block4;
                        }
                    }
                    byArray = ((MessageDigest)object2).digest();
                }
                object2 = new SecretKeySpec(byArray, "AES");
                try {
                    object = Cipher.getInstance("AES/CBC/NoPadding");
                    ((Cipher)object).init(2, (Key)object2, new IvParameterSpec(byArray3));
                    this.cipherInputStream = new CipherInputStream(this.val$in, (Cipher)object);
                    this.isInitialized = true;
                    return this.cipherInputStream;
                } catch (GeneralSecurityException generalSecurityException) {
                    IOException iOException = new IOException("Decryption error (do you have the JCE Unlimited Strength Jurisdiction Policy Files installed?)");
                    iOException.initCause(generalSecurityException);
                    throw iOException;
                }
            }

            public int read() throws IOException {
                return this.init().read();
            }

            public int read(byte[] byArray, int n, int n2) throws IOException {
                return this.init().read(byArray, n, n2);
            }

            public void close() {
            }
        };
    }
}

