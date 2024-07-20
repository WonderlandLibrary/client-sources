/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLParameters;

final class Java8SslParametersUtils {
    private Java8SslParametersUtils() {
    }

    static List<String> getSniHostNames(SSLParameters sslParameters) {
        List<SNIServerName> names = sslParameters.getServerNames();
        if (names == null || names.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> strings = new ArrayList<String>(names.size());
        for (SNIServerName serverName : names) {
            if (serverName instanceof SNIHostName) {
                strings.add(((SNIHostName)serverName).getAsciiName());
                continue;
            }
            throw new IllegalArgumentException("Only " + SNIHostName.class.getName() + " instances are supported, but found: " + serverName);
        }
        return strings;
    }

    static void setSniHostNames(SSLParameters sslParameters, List<String> names) {
        ArrayList<SNIServerName> sniServerNames = new ArrayList<SNIServerName>(names.size());
        for (String name : names) {
            sniServerNames.add(new SNIHostName(name));
        }
        sslParameters.setServerNames(sniServerNames);
    }

    static boolean getUseCipherSuitesOrder(SSLParameters sslParameters) {
        return sslParameters.getUseCipherSuitesOrder();
    }

    static void setUseCipherSuitesOrder(SSLParameters sslParameters, boolean useOrder) {
        sslParameters.setUseCipherSuitesOrder(useOrder);
    }
}

