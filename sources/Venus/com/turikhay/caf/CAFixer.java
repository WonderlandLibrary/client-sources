/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.turikhay.caf;

import com.turikhay.caf.CAStore;
import com.turikhay.caf.Cert;
import com.turikhay.caf.KeyStoreManager;
import com.turikhay.caf.util.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.lang.instrument.Instrumentation;
import java.security.KeyStore;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

public class CAFixer {
    private final Logger logger;
    private static final String LOG_PREFIX = "[CertJavaAgent] ";

    public static void premain(String string, Instrumentation instrumentation) {
        CAFixer.fix();
    }

    public static void main(String[] stringArray) {
        CAFixer.fix();
    }

    public static void fix(Logger logger) {
        new CAFixer(logger == null ? Logger.PrintLogger.ofSystem() : logger).fixCA();
    }

    public static void fix() {
        CAFixer.fix(null);
    }

    private CAFixer(Logger logger) {
        this.logger = logger;
    }

    private void fixCA() {
        try {
            this.updateJreCAStoreIfNecessary();
        } catch (Exception exception) {
            this.logger.logError("[CertJavaAgent] Failed", exception);
        }
    }

    private void updateJreCAStoreIfNecessary() throws Exception {
        CAStore cAStore;
        CAStore cAStore2 = CAFixer.loadJreCAStore();
        if (this.doesContainAllCerts(cAStore2, cAStore = CAFixer.loadEmbeddedCAStore())) {
            return;
        }
        CAStore cAStore3 = cAStore2.merge(cAStore);
        KeyStore keyStore = cAStore3.toKeyStore();
        this.log("Will use updated KeyStore that includes missing certificates");
        KeyStoreManager.useNewKeyStore(keyStore);
    }

    private boolean doesContainAllCerts(CAStore cAStore, CAStore cAStore2) {
        boolean bl = true;
        for (Cert cert : cAStore2.getCerts()) {
            boolean bl2 = cert.asX509().map(arg_0 -> this.lambda$doesContainAllCerts$0(cert, arg_0)).filter(CAFixer::lambda$doesContainAllCerts$1).isPresent();
            if (bl2) {
                this.log("Skipping " + cert);
                continue;
            }
            if (cAStore.hasCert(cert)) continue;
            this.log("JRE trust store doesn't contain " + cert);
            bl = false;
        }
        return bl;
    }

    private void log(String string) {
        this.logger.logMessage(LOG_PREFIX + string);
    }

    private static CAStore loadJreCAStore() throws Exception {
        File file = new File(System.getProperty("java.home"), "lib/security/cacerts");
        return CAStore.load(new FileInputStream(file), KeyStore.getDefaultType(), "changeit");
    }

    private static CAStore loadEmbeddedCAStore() throws Exception {
        return CAStore.load(CAFixer.class.getResourceAsStream("ca.jks"), "jks", "supersecretpassword");
    }

    private static boolean lambda$doesContainAllCerts$1(Boolean bl) {
        return bl == Boolean.TRUE;
    }

    private Boolean lambda$doesContainAllCerts$0(Cert cert, X509Certificate x509Certificate) {
        try {
            x509Certificate.checkValidity();
        } catch (CertificateExpiredException certificateExpiredException) {
            this.log("Embedded certificate has expired " + cert);
            return Boolean.TRUE;
        } catch (CertificateNotYetValidException certificateNotYetValidException) {
            this.log("Embedded certificate is not yet valid..? " + cert);
        }
        return Boolean.FALSE;
    }
}

