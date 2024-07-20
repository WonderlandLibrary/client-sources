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
    private static final JdbcDatabaseManagerFactory INSTANCE = new JdbcDatabaseManagerFactory();
    private final List<ColumnMapping> columnMappings;
    private final List<ColumnConfig> columnConfigs;
    private final ConnectionSource connectionSource;
    private final String sqlStatement;
    private Connection connection;
    private PreparedStatement statement;
    private boolean isBatchSupported;

    private JdbcDatabaseManager(String name, int bufferSize, ConnectionSource connectionSource, String sqlStatement, List<ColumnConfig> columnConfigs, List<ColumnMapping> columnMappings) {
        super(name, bufferSize);
        this.connectionSource = connectionSource;
        this.sqlStatement = sqlStatement;
        this.columnConfigs = columnConfigs;
        this.columnMappings = columnMappings;
    }

    @Override
    protected void startupInternal() throws Exception {
        this.connection = this.connectionSource.getConnection();
        DatabaseMetaData metaData = this.connection.getMetaData();
        this.isBatchSupported = metaData.supportsBatchUpdates();
        Closer.closeSilently(this.connection);
    }

    @Override
    protected boolean shutdownInternal() {
        if (this.connection != null || this.statement != null) {
            return this.commitAndClose();
        }
        return true;
    }

    @Override
    protected void connectAndStart() {
        try {
            this.connection = this.connectionSource.getConnection();
            this.connection.setAutoCommit(false);
            this.statement = this.connection.prepareStatement(this.sqlStatement);
        } catch (SQLException e) {
            throw new AppenderLoggingException("Cannot write logging event or flush buffer; JDBC manager cannot connect to the database.", e);
        }
    }

    @Override
    protected void writeInternal(LogEvent event) {
        StringReader reader = null;
        try {
            if (!this.isRunning() || this.connection == null || this.connection.isClosed() || this.statement == null || this.statement.isClosed()) {
                throw new AppenderLoggingException("Cannot write logging event; JDBC manager not connected to the database.");
            }
            int i = 1;
            for (ColumnMapping mapping : this.columnMappings) {
                if (ThreadContextMap.class.isAssignableFrom(mapping.getType()) || ReadOnlyStringMap.class.isAssignableFrom(mapping.getType())) {
                    this.statement.setObject(i++, event.getContextData().toMap());
                    continue;
                }
                if (ThreadContextStack.class.isAssignableFrom(mapping.getType())) {
                    this.statement.setObject(i++, event.getContextStack().asList());
                    continue;
                }
                if (Date.class.isAssignableFrom(mapping.getType())) {
                    this.statement.setObject(i++, DateTypeConverter.fromMillis(event.getTimeMillis(), mapping.getType().asSubclass(Date.class)));
                    continue;
                }
                if (Clob.class.isAssignableFrom(mapping.getType())) {
                    this.statement.setClob(i++, new StringReader((String)mapping.getLayout().toSerializable(event)));
                    continue;
                }
                if (NClob.class.isAssignableFrom(mapping.getType())) {
                    this.statement.setNClob(i++, new StringReader((String)mapping.getLayout().toSerializable(event)));
                    continue;
                }
                Object value = TypeConverters.convert((String)mapping.getLayout().toSerializable(event), mapping.getType(), null);
                if (value == null) {
                    this.statement.setNull(i++, 0);
                    continue;
                }
                this.statement.setObject(i++, value);
            }
            for (ColumnConfig column : this.columnConfigs) {
                if (column.isEventTimestamp()) {
                    this.statement.setTimestamp(i++, new Timestamp(event.getTimeMillis()));
                    continue;
                }
                if (column.isClob()) {
                    reader = new StringReader(column.getLayout().toSerializable(event));
                    if (column.isUnicode()) {
                        this.statement.setNClob(i++, reader);
                        continue;
                    }
                    this.statement.setClob(i++, reader);
                    continue;
                }
                if (column.isUnicode()) {
                    this.statement.setNString(i++, column.getLayout().toSerializable(event));
                    continue;
                }
                this.statement.setString(i++, column.getLayout().toSerializable(event));
            }
            if (this.isBatchSupported) {
                this.statement.addBatch();
            } else if (this.statement.executeUpdate() == 0) {
                throw new AppenderLoggingException("No records inserted in database table for log event in JDBC manager.");
            }
        } catch (SQLException e) {
            throw new AppenderLoggingException("Failed to insert record for log event in JDBC manager: " + e.getMessage(), e);
        } finally {
            Closer.closeSilently(reader);
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
        boolean closed = true;
        try {
            if (this.connection == null) return closed;
            if (this.connection.isClosed()) return closed;
            if (this.isBatchSupported) {
                this.statement.executeBatch();
            }
            this.connection.commit();
            return closed;
        } catch (SQLException e) {
            throw new AppenderLoggingException("Failed to commit transaction logging event or flushing buffer.", e);
        } finally {
            try {
                Closer.close(this.statement);
            } catch (Exception e) {
                this.logWarn("Failed to close SQL statement logging event or flushing buffer", e);
                closed = false;
            } finally {
                this.statement = null;
            }
            try {
                Closer.close(this.connection);
            } catch (Exception e) {
                this.logWarn("Failed to close database connection logging event or flushing buffer", e);
                closed = false;
            } finally {
                this.connection = null;
            }
        }
    }

    @Deprecated
    public static JdbcDatabaseManager getJDBCDatabaseManager(String name, int bufferSize, ConnectionSource connectionSource, String tableName, ColumnConfig[] columnConfigs) {
        return JdbcDatabaseManager.getManager(name, new FactoryData(bufferSize, connectionSource, tableName, columnConfigs, new ColumnMapping[0]), JdbcDatabaseManager.getFactory());
    }

    public static JdbcDatabaseManager getManager(String name, int bufferSize, ConnectionSource connectionSource, String tableName, ColumnConfig[] columnConfigs, ColumnMapping[] columnMappings) {
        return JdbcDatabaseManager.getManager(name, new FactoryData(bufferSize, connectionSource, tableName, columnConfigs, columnMappings), JdbcDatabaseManager.getFactory());
    }

    private static JdbcDatabaseManagerFactory getFactory() {
        return INSTANCE;
    }

    private static final class JdbcDatabaseManagerFactory
    implements ManagerFactory<JdbcDatabaseManager, FactoryData> {
        private JdbcDatabaseManagerFactory() {
        }

        @Override
        public JdbcDatabaseManager createManager(String name, FactoryData data) {
            StringBuilder sb = new StringBuilder("INSERT INTO ").append(data.tableName).append(" (");
            for (ColumnMapping mapping : data.columnMappings) {
                sb.append(mapping.getName()).append(',');
            }
            for (ColumnConfig config : data.columnConfigs) {
                sb.append(config.getColumnName()).append(',');
            }
            sb.setCharAt(sb.length() - 1, ')');
            sb.append(" VALUES (");
            ArrayList<ColumnMapping> columnMappings = new ArrayList<ColumnMapping>(data.columnMappings.length);
            for (ColumnMapping mapping : data.columnMappings) {
                if (Strings.isNotEmpty(mapping.getLiteralValue())) {
                    sb.append(mapping.getLiteralValue());
                } else {
                    sb.append('?');
                    columnMappings.add(mapping);
                }
                sb.append(',');
            }
            ArrayList<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>(data.columnConfigs.length);
            for (ColumnConfig config : data.columnConfigs) {
                if (Strings.isNotEmpty(config.getLiteralValue())) {
                    sb.append(config.getLiteralValue());
                } else {
                    sb.append('?');
                    columnConfigs.add(config);
                }
                sb.append(',');
            }
            sb.setCharAt(sb.length() - 1, ')');
            String sqlStatement = sb.toString();
            return new JdbcDatabaseManager(name, data.getBufferSize(), data.connectionSource, sqlStatement, columnConfigs, columnMappings);
        }
    }

    private static final class FactoryData
    extends AbstractDatabaseManager.AbstractFactoryData {
        private final ConnectionSource connectionSource;
        private final String tableName;
        private final ColumnConfig[] columnConfigs;
        private final ColumnMapping[] columnMappings;

        protected FactoryData(int bufferSize, ConnectionSource connectionSource, String tableName, ColumnConfig[] columnConfigs, ColumnMapping[] columnMappings) {
            super(bufferSize);
            this.connectionSource = connectionSource;
            this.tableName = tableName;
            this.columnConfigs = columnConfigs;
            this.columnMappings = columnMappings;
        }
    }
}

