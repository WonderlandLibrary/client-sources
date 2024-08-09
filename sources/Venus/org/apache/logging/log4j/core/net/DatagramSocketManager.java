/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.net.AbstractSocketManager;
import org.apache.logging.log4j.core.net.DatagramOutputStream;
import org.apache.logging.log4j.util.Strings;

public class DatagramSocketManager
extends AbstractSocketManager {
    private static final DatagramSocketManagerFactory FACTORY = new DatagramSocketManagerFactory(null);

    protected DatagramSocketManager(String string, OutputStream outputStream, InetAddress inetAddress, String string2, int n, Layout<? extends Serializable> layout, int n2) {
        super(string, outputStream, inetAddress, string2, n, layout, true, n2);
    }

    public static DatagramSocketManager getSocketManager(String string, int n, Layout<? extends Serializable> layout, int n2) {
        if (Strings.isEmpty(string)) {
            throw new IllegalArgumentException("A host name is required");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("A port value is required");
        }
        return (DatagramSocketManager)DatagramSocketManager.getManager("UDP:" + string + ':' + n, new FactoryData(string, n, layout, n2), FACTORY);
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>(super.getContentFormat());
        hashMap.put("protocol", "udp");
        hashMap.put("direction", "out");
        return hashMap;
    }

    static Logger access$200() {
        return LOGGER;
    }

    static class 1 {
    }

    private static class DatagramSocketManagerFactory
    implements ManagerFactory<DatagramSocketManager, FactoryData> {
        private DatagramSocketManagerFactory() {
        }

        @Override
        public DatagramSocketManager createManager(String string, FactoryData factoryData) {
            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(FactoryData.access$100(factoryData));
            } catch (UnknownHostException unknownHostException) {
                DatagramSocketManager.access$200().error("Could not find address of " + FactoryData.access$100(factoryData), (Throwable)unknownHostException);
                return null;
            }
            DatagramOutputStream datagramOutputStream = new DatagramOutputStream(FactoryData.access$100(factoryData), FactoryData.access$300(factoryData), FactoryData.access$400(factoryData).getHeader(), FactoryData.access$400(factoryData).getFooter());
            return new DatagramSocketManager(string, (OutputStream)datagramOutputStream, inetAddress, FactoryData.access$100(factoryData), FactoryData.access$300(factoryData), FactoryData.access$400(factoryData), FactoryData.access$500(factoryData));
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        DatagramSocketManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData {
        private final String host;
        private final int port;
        private final Layout<? extends Serializable> layout;
        private final int bufferSize;

        public FactoryData(String string, int n, Layout<? extends Serializable> layout, int n2) {
            this.host = string;
            this.port = n;
            this.layout = layout;
            this.bufferSize = n2;
        }

        static String access$100(FactoryData factoryData) {
            return factoryData.host;
        }

        static int access$300(FactoryData factoryData) {
            return factoryData.port;
        }

        static Layout access$400(FactoryData factoryData) {
            return factoryData.layout;
        }

        static int access$500(FactoryData factoryData) {
            return factoryData.bufferSize;
        }
    }
}

