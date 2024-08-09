/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.turikhay.caf;

import com.turikhay.caf.Cert;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CAStore {
    private final Set<Cert> certs;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CAStore load(InputStream inputStream, String string, String string2) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(string);
        try {
            keyStore.load(inputStream, string2.toCharArray());
        } finally {
            inputStream.close();
        }
        return new CAStore(CAStore.keyStoreToCertSet(keyStore));
    }

    private static Set<Cert> keyStoreToCertSet(KeyStore keyStore) throws KeyStoreException {
        HashMap<Certificate, Set> hashMap = new HashMap<Certificate, Set>();
        Enumeration<String> enumeration = keyStore.aliases();
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            Certificate certificate = keyStore.getCertificate(string);
            hashMap.compute(certificate, (arg_0, arg_1) -> CAStore.lambda$keyStoreToCertSet$0(string, arg_0, arg_1));
        }
        return hashMap.entrySet().stream().map(CAStore::lambda$keyStoreToCertSet$1).collect(Collectors.toSet());
    }

    private CAStore(Set<Cert> set) {
        this.certs = set;
    }

    public boolean hasCert(Cert cert) {
        return this.certs.contains(cert);
    }

    public Set<Cert> getCerts() {
        return this.certs;
    }

    public CAStore merge(CAStore cAStore) {
        Set<Cert> set = CAStore.newCertSet();
        set.addAll(this.certs);
        set.addAll(cAStore.certs);
        return new CAStore(set);
    }

    public KeyStore toKeyStore() throws KeyStoreException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try {
            keyStore.load(null, new char[0]);
        } catch (IOException | NoSuchAlgorithmException | CertificateException exception) {
            throw new KeyStoreException("Couldn't init empty KeyStore", exception);
        }
        for (Cert cert : this.certs) {
            for (String string : cert.getAliases()) {
                keyStore.setCertificateEntry(string, cert.getCertificate());
            }
        }
        return keyStore;
    }

    private static Set<Cert> newCertSet() {
        return new HashSet<Cert>();
    }

    private static Cert lambda$keyStoreToCertSet$1(Map.Entry entry) {
        return new Cert((Certificate)entry.getKey(), (Set)entry.getValue());
    }

    private static Set lambda$keyStoreToCertSet$0(String string, Certificate certificate, Set hashSet) {
        if (hashSet == null) {
            hashSet = new HashSet<String>();
        }
        hashSet.add(string);
        return hashSet;
    }
}

