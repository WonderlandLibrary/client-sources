/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.db.jdbc;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="ConnectionFactory", category="Core", elementType="connectionSource", printObject=true)
public final class FactoryMethodConnectionSource
implements ConnectionSource {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final DataSource dataSource;
    private final String description;

    private FactoryMethodConnectionSource(DataSource dataSource, String string, String string2, String string3) {
        this.dataSource = dataSource;
        this.description = "factory{ public static " + string3 + ' ' + string + '.' + string2 + "() }";
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    @Override
    public String toString() {
        return this.description;
    }

    @PluginFactory
    public static FactoryMethodConnectionSource createConnectionSource(@PluginAttribute(value="class") String string, @PluginAttribute(value="method") String string2) {
        DataSource dataSource;
        Method method;
        Class<?> clazz;
        if (Strings.isEmpty(string) || Strings.isEmpty(string2)) {
            LOGGER.error("No class name or method name specified for the connection factory method.");
            return null;
        }
        try {
            clazz = LoaderUtil.loadClass(string);
            method = clazz.getMethod(string2, new Class[0]);
        } catch (Exception exception) {
            LOGGER.error(exception.toString(), (Throwable)exception);
            return null;
        }
        clazz = method.getReturnType();
        String string3 = clazz.getName();
        if (clazz == DataSource.class) {
            try {
                dataSource = (DataSource)method.invoke(null, new Object[0]);
                string3 = string3 + "[" + dataSource + ']';
            } catch (Exception exception) {
                LOGGER.error(exception.toString(), (Throwable)exception);
                return null;
            }
        } else if (clazz == Connection.class) {
            dataSource = new DataSource(method){
                final Method val$method;
                {
                    this.val$method = method;
                }

                @Override
                public Connection getConnection() throws SQLException {
                    try {
                        return (Connection)this.val$method.invoke(null, new Object[0]);
                    } catch (Exception exception) {
                        throw new SQLException("Failed to obtain connection from factory method.", exception);
                    }
                }

                @Override
                public Connection getConnection(String string, String string2) throws SQLException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int getLoginTimeout() throws SQLException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public PrintWriter getLogWriter() throws SQLException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public java.util.logging.Logger getParentLogger() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public boolean isWrapperFor(Class<?> clazz) throws SQLException {
                    return true;
                }

                @Override
                public void setLoginTimeout(int n) throws SQLException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void setLogWriter(PrintWriter printWriter) throws SQLException {
                    throw new UnsupportedOperationException();
                }

                @Override
                public <T> T unwrap(Class<T> clazz) throws SQLException {
                    return null;
                }
            };
        } else {
            LOGGER.error("Method [{}.{}()] returns unsupported type [{}].", (Object)string, (Object)string2, (Object)clazz.getName());
            return null;
        }
        return new FactoryMethodConnectionSource(dataSource, string, string2, string3);
    }
}

