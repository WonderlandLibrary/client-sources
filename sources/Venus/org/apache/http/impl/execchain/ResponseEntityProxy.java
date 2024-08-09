/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.execchain;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.conn.EofSensorWatcher;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.execchain.ConnectionHolder;

class ResponseEntityProxy
extends HttpEntityWrapper
implements EofSensorWatcher {
    private final ConnectionHolder connHolder;

    public static void enchance(HttpResponse httpResponse, ConnectionHolder connectionHolder) {
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null && httpEntity.isStreaming() && connectionHolder != null) {
            httpResponse.setEntity(new ResponseEntityProxy(httpEntity, connectionHolder));
        }
    }

    ResponseEntityProxy(HttpEntity httpEntity, ConnectionHolder connectionHolder) {
        super(httpEntity);
        this.connHolder = connectionHolder;
    }

    private void cleanup() throws IOException {
        if (this.connHolder != null) {
            this.connHolder.close();
        }
    }

    private void abortConnection() {
        if (this.connHolder != null) {
            this.connHolder.abortConnection();
        }
    }

    public void releaseConnection() {
        if (this.connHolder != null) {
            this.connHolder.releaseConnection();
        }
    }

    @Override
    public boolean isRepeatable() {
        return true;
    }

    @Override
    public InputStream getContent() throws IOException {
        return new EofSensorInputStream(this.wrappedEntity.getContent(), this);
    }

    @Override
    public void consumeContent() throws IOException {
        this.releaseConnection();
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        try {
            if (outputStream != null) {
                this.wrappedEntity.writeTo(outputStream);
            }
            this.releaseConnection();
        } catch (IOException iOException) {
            this.abortConnection();
            throw iOException;
        } catch (RuntimeException runtimeException) {
            this.abortConnection();
            throw runtimeException;
        } finally {
            this.cleanup();
        }
    }

    @Override
    public boolean eofDetected(InputStream inputStream) throws IOException {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            this.releaseConnection();
        } catch (IOException iOException) {
            this.abortConnection();
            throw iOException;
        } catch (RuntimeException runtimeException) {
            this.abortConnection();
            throw runtimeException;
        } finally {
            this.cleanup();
        }
        return true;
    }

    @Override
    public boolean streamClosed(InputStream inputStream) throws IOException {
        try {
            boolean bl = this.connHolder != null && !this.connHolder.isReleased();
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                this.releaseConnection();
            } catch (SocketException socketException) {
                if (bl) {
                    throw socketException;
                }
            }
        } catch (IOException iOException) {
            this.abortConnection();
            throw iOException;
        } catch (RuntimeException runtimeException) {
            this.abortConnection();
            throw runtimeException;
        } finally {
            this.cleanup();
        }
        return true;
    }

    @Override
    public boolean streamAbort(InputStream inputStream) throws IOException {
        this.cleanup();
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ResponseEntityProxy{");
        stringBuilder.append(this.wrappedEntity);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

