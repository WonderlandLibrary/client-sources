/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.ssl;

import org.apache.logging.log4j.core.net.ssl.StoreConfigurationException;
import org.apache.logging.log4j.status.StatusLogger;

public class StoreConfiguration<T> {
    protected static final StatusLogger LOGGER = StatusLogger.getLogger();
    private String location;
    private String password;

    public StoreConfiguration(String string, String string2) {
        this.location = string;
        this.password = string2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String string) {
        this.location = string;
    }

    public String getPassword() {
        return this.password;
    }

    public char[] getPasswordAsCharArray() {
        return this.password == null ? null : this.password.toCharArray();
    }

    public void setPassword(String string) {
        this.password = string;
    }

    protected T load() throws StoreConfigurationException {
        return null;
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.location == null ? 0 : this.location.hashCode());
        n2 = 31 * n2 + (this.password == null ? 0 : this.password.hashCode());
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof StoreConfiguration)) {
            return true;
        }
        StoreConfiguration storeConfiguration = (StoreConfiguration)object;
        if (this.location == null ? storeConfiguration.location != null : !this.location.equals(storeConfiguration.location)) {
            return true;
        }
        return this.password == null ? storeConfiguration.password != null : !this.password.equals(storeConfiguration.password);
    }
}

