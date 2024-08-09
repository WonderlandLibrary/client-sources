/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.db.jdbc;

import java.io.StringReader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
import org.apache.logging.log4j.core.appender.db.ColumnMapping;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.config.plugins.convert.DateTypeConverter;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import org.apache.logging.log4j.core.util.Closer;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextStack;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.Strings;

public final class JdbcDatabaseManager
extends AbstractDatabaseManager {
    private static final JdbcDatabaseManagerFactory INSTANCE = new JdbcDatabaseManagerFactory(null);
    private final List<ColumnMapping> columnMappings;
    private final List<ColumnConfig> columnConfigs;
    private final ConnectionSource connectionSource;
    private final String sqlStatement;
    private Connection connection;
    private PreparedStatement statement;
    private boolean isBatchSupported;

    private JdbcDatabaseManager(String string, int n, ConnectionSource connectionSource, String string2, List<ColumnConfig> list, List<ColumnMapping> list2) {
        super(string, n);
        this.connectionSource = connectionSource;
        this.sqlStatement = string2;
        this.columnConfigs = list;
        this.columnMappings = list2;
    }

    @Override
    protected void startupInternal() throws Exception {
        this.connection = this.connectionSource.getConnection();
        DatabaseMetaData databaseMetaData = this.connection.getMetaData();
        this.isBatchSupported = databaseMetaData.supportsBatchUpdates();
        Closer.closeSilently(this.connection);
    }

    @Override
    protected boolean shutdownInternal() {
        if (this.connection != null || this.statement != null) {
            return this.commitAndClose();
        }
        return false;
    }

    @Override
    protected void connectAndStart() {
        try {
            this.connection = this.connectionSource.getConnection();
            this.connection.setAutoCommit(false);
            this.statement = this.connection.prepareStatement(this.sqlStatement);
        } catch (SQLException sQLException) {
            throw new AppenderLoggingException("Cannot write logging event or flush buffer; JDBC manager cannot connect to the database.", sQLException);
        }
    }

    @Override
    protected void writeInternal(LogEvent logEvent) {
        StringReader stringReader = null;
        try {
            if (!this.isRunning() || this.connection == null || this.connection.isClosed() || this.statement == null || this.statement.isClosed()) {
                throw new AppenderLoggingException("Cannot write logging event; JDBC manager not connected to the database.");
            }
            int n = 1;
            for (ColumnMapping object : this.columnMappings) {
                if (ThreadContextMap.class.isAssignableFrom(object.getType()) || ReadOnlyStringMap.class.isAssignableFrom(object.getType())) {
                    this.statement.setObject(n++, logEvent.getContextData().toMap());
                    continue;
                }
                if (ThreadContextStack.class.isAssignableFrom(object.getType())) {
                    this.statement.setObject(n++, logEvent.getContextStack().asList());
                    continue;
                }
                if (Date.class.isAssignableFrom(object.getType())) {
                    this.statement.setObject(n++, DateTypeConverter.fromMillis(logEvent.getTimeMillis(), object.getType().asSubclass(Date.class)));
                    continue;
                }
                if (Clob.class.isAssignableFrom(object.getType())) {
                    this.statement.setClob(n++, new StringReader((String)object.getLayout().toSerializable(logEvent)));
                    continue;
                }
                if (NClob.class.isAssignableFrom(object.getType())) {
                    this.statement.setNClob(n++, new StringReader((String)object.getLayout().toSerializable(logEvent)));
                    continue;
                }
                Object obj = TypeConverters.convert((String)object.getLayout().toSerializable(logEvent), object.getType(), null);
                if (obj == null) {
                    this.statement.setNull(n++, 0);
                    continue;
                }
                this.statement.setObject(n++, obj);
            }
            for (ColumnConfig columnConfig : this.columnConfigs) {
                if (columnConfig.isEventTimestamp()) {
                    this.statement.setTimestamp(n++, new Timestamp(logEvent.getTimeMillis()));
                    continue;
                }
                if (columnConfig.isClob()) {
                    stringReader = new StringReader(columnConfig.getLayout().toSerializable(logEvent));
                    if (columnConfig.isUnicode()) {
                        this.statement.setNClob(n++, stringReader);
                        continue;
                    }
                    this.statement.setClob(n++, stringReader);
                    continue;
                }
                if (columnConfig.isUnicode()) {
                    this.statement.setNString(n++, columnConfig.getLayout().toSerializable(logEvent));
                    continue;
                }
                this.statement.setString(n++, columnConfig.getLayout().toSerializable(logEvent));
            }
            if (this.isBatchSupported) {
                this.statement.addBatch();
            } else if (this.statement.executeUpdate() == 0) {
                throw new AppenderLoggingException("No records inserted in database table for log event in JDBC manager.");
            }
        } catch (SQLException sQLException) {
            throw new AppenderLoggingException("Failed to insert record for log event in JDBC manager: " + sQLException.getMessage(), sQLException);
        } finally {
            Closer.closeSilently(stringReader);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean commitAndClose() {
        boolean bl = true;
        try {
            if (this.connection == null) return bl;
            if (this.connection.isClosed()) return bl;
            if (this.isBatchSupported) {
                this.statement.executeBatch();
            }
            this.connection.commit();
            return bl;
        } catch (SQLException sQLException) {
            throw new AppenderLoggingException("Failed to commit transaction logging event or flushing buffer.", sQLException);
        } finally {
            try {
                Closer.close(this.statement);
            } catch (Exception exception) {
                this.logWarn("Failed to close SQL statement logging event or flushing buffer", exception);
                bl = false;
            } finally {
                this.statement = null;
            }
            try {
                Closer.close(this.connection);
            } catch (Exception exception) {
                this.logWarn("Failed to close database connection logging event or flushing buffer", exception);
                bl = false;
            } finally {
                this.connection = null;
            }
        }
    }

    @Deprecated
    public static JdbcDatabaseManager getJDBCDatabaseManager(String string, int n, ConnectionSource connectionSource, String string2, ColumnConfig[] columnConfigArray) {
        return JdbcDatabaseManager.getManager(string, new FactoryData(n, connectionSource, string2, columnConfigArray, new ColumnMapping[0]), JdbcDatabaseManager.getFactory());
    }

    public static JdbcDatabaseManager getManager(String string, int n, ConnectionSource connectionSource, String string2, ColumnConfig[] columnConfigArray, ColumnMapping[] columnMappingArray) {
        return JdbcDatabaseManager.getManager(string, new FactoryData(n, connectionSource, string2, columnConfigArray, columnMappingArray), JdbcDatabaseManager.getFactory());
    }

    private static JdbcDatabaseManagerFactory getFactory() {
        return INSTANCE;
    }

    JdbcDatabaseManager(String string, int n, ConnectionSource connectionSource, String string2, List list, List list2, 1 var7_7) {
        this(string, n, connectionSource, string2, list, list2);
    }

    static class 1 {
    }

    private static final class JdbcDatabaseManagerFactory
    implements ManagerFactory<JdbcDatabaseManager, FactoryData> {
        private JdbcDatabaseManagerFactory() {
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public JdbcDatabaseManager createManager(String string, FactoryData factoryData) {
            void var7_18;
            void var6_10;
            void object;
            StringBuilder stringBuilder = new StringBuilder("INSERT INTO ").append(FactoryData.access$100(factoryData)).append(" (");
            Object arrayList = FactoryData.access$200(factoryData);
            int n = ((ColumnMapping[])arrayList).length;
            boolean bl = false;
            while (object < n) {
                ColumnMapping columnMapping = arrayList[object];
                stringBuilder.append(columnMapping.getName()).append(',');
                ++object;
            }
            arrayList = FactoryData.access$300(factoryData);
            n = ((Object[])arrayList).length;
            boolean bl2 = false;
            while (var6_10 < n) {
                Object object2 = arrayList[var6_10];
                stringBuilder.append(((ColumnConfig)object2).getColumnName()).append(',');
                ++var6_10;
            }
            stringBuilder.setCharAt(stringBuilder.length() - 1, ')');
            stringBuilder.append(" VALUES (");
            arrayList = new ArrayList(FactoryData.access$200(factoryData).length);
            Object arrayList2 = FactoryData.access$200(factoryData);
            int n2 = ((ColumnMapping[])arrayList2).length;
            boolean bl3 = false;
            while (var7_18 < n2) {
                ColumnMapping columnMapping = arrayList2[var7_18];
                if (Strings.isNotEmpty(columnMapping.getLiteralValue())) {
                    stringBuilder.append(columnMapping.getLiteralValue());
                } else {
                    stringBuilder.append('?');
                    arrayList.add(columnMapping);
                }
                stringBuilder.append(',');
                ++var7_18;
            }
            arrayList2 = new ArrayList(FactoryData.access$300(factoryData).length);
            for (ColumnConfig columnConfig : FactoryData.access$300(factoryData)) {
                if (Strings.isNotEmpty(columnConfig.getLiteralValue())) {
                    stringBuilder.append(columnConfig.getLiteralValue());
                } else {
                    stringBuilder.append('?');
                    arrayList2.add(columnConfig);
                }
                stringBuilder.append(',');
            }
            stringBuilder.setCharAt(stringBuilder.length() - 1, ')');
            String string2 = stringBuilder.toString();
            return new JdbcDatabaseManager(string, factoryData.getBufferSize(), FactoryData.access$400(factoryData), string2, (List)arrayList2, (List)arrayList, null);
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        JdbcDatabaseManagerFactory(1 var1_1) {
            this();
        }
    }

    private static final class FactoryData
    extends AbstractDatabaseManager.AbstractFactoryData {
        private final ConnectionSource connectionSource;
        private final String tableName;
        private final ColumnConfig[] columnConfigs;
        private final ColumnMapping[] columnMappings;

        protected FactoryData(int n, ConnectionSource connectionSource, String string, ColumnConfig[] columnConfigArray, ColumnMapping[] columnMappingArray) {
            super(n);
            this.connectionSource = connectionSource;
            this.tableName = string;
            this.columnConfigs = columnConfigArray;
            this.columnMappings = columnMappingArray;
        }

        static String access$100(FactoryData factoryData) {
            return factoryData.tableName;
        }

        static ColumnMapping[] access$200(FactoryData factoryData) {
            return factoryData.columnMappings;
        }

        static ColumnConfig[] access$300(FactoryData factoryData) {
            return factoryData.columnConfigs;
        }

        static ConnectionSource access$400(FactoryData factoryData) {
            return factoryData.connectionSource;
        }
    }
}

