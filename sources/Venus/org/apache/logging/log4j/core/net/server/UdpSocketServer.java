/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.server;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.net.server.AbstractSocketServer;
import org.apache.logging.log4j.core.net.server.JsonInputStreamLogEventBridge;
import org.apache.logging.log4j.core.net.server.LogEventBridge;
import org.apache.logging.log4j.core.net.server.ObjectInputStreamLogEventBridge;
import org.apache.logging.log4j.core.net.server.XmlInputStreamLogEventBridge;
import org.apache.logging.log4j.core.util.BasicCommandLineArguments;

public class UdpSocketServer<T extends InputStream>
extends AbstractSocketServer<T> {
    private final DatagramSocket datagramSocket;
    private final int maxBufferSize = 67584;

    public static UdpSocketServer<InputStream> createJsonSocketServer(int n) throws IOException {
        return new UdpSocketServer<InputStream>(n, new JsonInputStreamLogEventBridge());
    }

    public static UdpSocketServer<ObjectInputStream> createSerializedSocketServer(int n) throws IOException {
        return new UdpSocketServer<ObjectInputStream>(n, new ObjectInputStreamLogEventBridge());
    }

    public static UdpSocketServer<InputStream> createXmlSocketServer(int n) throws IOException {
        return new UdpSocketServer<InputStream>(n, new XmlInputStreamLogEventBridge());
    }

    public static void main(String[] stringArray) throws Exception {
        AbstractSocketServer.CommandLineArguments commandLineArguments = BasicCommandLineArguments.parseCommandLine(stringArray, UdpSocketServer.class, new AbstractSocketServer.CommandLineArguments());
        if (commandLineArguments.isHelp()) {
            return;
        }
        if (commandLineArguments.getConfigLocation() != null) {
            ConfigurationFactory.setConfigurationFactory(new AbstractSocketServer.ServerConfigurationFactory(commandLineArguments.getConfigLocation()));
        }
        UdpSocketServer<ObjectInputStream> udpSocketServer = UdpSocketServer.createSerializedSocketServer(commandLineArguments.getPort());
        Thread thread2 = udpSocketServer.startNewThread();
        if (commandLineArguments.isInteractive()) {
            udpSocketServer.awaitTermination(thread2);
        }
    }

    public UdpSocketServer(int n, LogEventBridge<T> logEventBridge) throws IOException {
        super(n, logEventBridge);
        this.datagramSocket = new DatagramSocket(n);
    }

    @Override
    public void run() {
        while (this.isActive()) {
            if (this.datagramSocket.isClosed()) {
                return;
            }
            try {
                byte[] byArray = new byte[67584];
                DatagramPacket datagramPacket = new DatagramPacket(byArray, byArray.length);
                this.datagramSocket.receive(datagramPacket);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
                this.logEventInput.logEvents(this.logEventInput.wrapStream(byteArrayInputStream), this);
            } catch (OptionalDataException optionalDataException) {
                if (this.datagramSocket.isClosed()) {
                    return;
                }
                this.logger.error("OptionalDataException eof=" + optionalDataException.eof + " length=" + optionalDataException.length, (Throwable)optionalDataException);
            } catch (EOFException eOFException) {
                if (this.datagramSocket.isClosed()) {
                    return;
                }
                this.logger.info("EOF encountered");
            } catch (IOException iOException) {
                if (this.datagramSocket.isClosed()) {
                    return;
                }
                this.logger.error("Exception encountered on accept. Ignoring. Stack Trace :", (Throwable)iOException);
            }
        }
    }

    @Override
    public void shutdown() {
        this.setActive(true);
        Thread.currentThread().interrupt();
        this.datagramSocket.close();
    }
}

