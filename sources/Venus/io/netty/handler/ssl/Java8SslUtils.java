/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLParameters;

final class Java8SslUtils {
    private Java8SslUtils() {
    }

    static List<String> getSniHostNames(SSLParameters sSLParameters) {
        List<SNIServerName> list = sSLParameters.getServerNames();
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> arrayList = new ArrayList<String>(list.size());
        for (SNIServerName sNIServerName : list) {
            if (sNIServerName instanceof SNIHostName) {
                arrayList.add(((SNIHostName)sNIServerName).getAsciiName());
                continue;
            }
            throw new IllegalArgumentException("Only " + SNIHostName.class.getName() + " instances are supported, but found: " + sNIServerName);
        }
        return arrayList;
    }

    static void setSniHostNames(SSLParameters sSLParameters, List<String> list) {
        ArrayList<SNIServerName> arrayList = new ArrayList<SNIServerName>(list.size());
        for (String string : list) {
            arrayList.add(new SNIHostName(string));
        }
        sSLParameters.setServerNames(arrayList);
    }

    static boolean getUseCipherSuitesOrder(SSLParameters sSLParameters) {
        return sSLParameters.getUseCipherSuitesOrder();
    }

    static void setUseCipherSuitesOrder(SSLParameters sSLParameters, boolean bl) {
        sSLParameters.setUseCipherSuitesOrder(bl);
    }

    static void setSNIMatchers(SSLParameters sSLParameters, Collection<?> collection) {
        sSLParameters.setSNIMatchers(collection);
    }

    static boolean checkSniHostnameMatch(Collection<?> collection, String string) {
        if (collection != null && !collection.isEmpty()) {
            SNIHostName sNIHostName = new SNIHostName(string);
            for (SNIMatcher sNIMatcher : collection) {
                if (sNIMatcher.getType() != 0 || !sNIMatcher.matches(sNIHostName)) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}

