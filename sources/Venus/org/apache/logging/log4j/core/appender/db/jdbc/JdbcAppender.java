/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.db.jdbc;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
import org.apache.logging.log4j.core.appender.db.ColumnMapping;
import org.apache.logging.log4j.core.appender.db.jdbc.ColumnConfig;
import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.apache.logging.log4j.core.appender.db.jdbc.JdbcDatabaseManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.util.Assert;
import org.apache.logging.log4j.core.util.Booleans;

@Plugin(name="JDBC", category="Core", elementType="appender", printObject=true)
public final class JdbcAppender
extends AbstractDatabaseAppender<JdbcDatabaseManager> {
    private final String description = this.getName() + "{ manager=" + this.getManager() + " }";

    private JdbcAppender(String string, Filter filter, boolean bl, JdbcDatabaseManager jdbcDatabaseManager) {
        super(string, filter, bl, jdbcDatabaseManager);
    }

    @Override
    public String toString() {
        return this.description;
    }

    @Deprecated
    public static <B extends Builder<B>> JdbcAppender createAppender(String string, String string2, Filter filter, ConnectionSource connectionSource, String string3, String string4, ColumnConfig[] columnConfigArray) {
        Assert.requireNonEmpty(string, "Name cannot be empty");
        Objects.requireNonNull(connectionSource, "ConnectionSource cannot be null");
        Assert.requireNonEmpty(string4, "Table name cannot be empty");
        Assert.requireNonEmpty(columnConfigArray, "ColumnConfigs cannot be empty");
        int n = AbstractAppender.parseInt(string3, 0);
        boolean bl = Booleans.parseBoolean(string2, true);
        return ((Builder)((Builder)((Builder)((AbstractAppender.Builder)((Builder)((Builder)((Builder)((Builder)JdbcAppender.newBuilder()).setBufferSize(n)).setColumnConfigs(columnConfigArray)).setConnectionSource(connectionSource)).setTableName(string4)).withName(string)).withIgnoreExceptions(bl)).withFilter(filter)).build();
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    static Logger access$000() {
        return LOGGER;
    }

    JdbcAppender(String string, Filter filter, boolean bl, JdbcDatabaseManager jdbcDatabaseManager, 1 var5_5) {
        this(string, filter, bl, jdbcDatabaseManager);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<B extends Builder<B>>
    extends AbstractAppender.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<JdbcAppender> {
        @PluginElement(value="ConnectionSource")
        @Required(message="No ConnectionSource provided")
        private ConnectionSource connectionSource;
        @PluginBuilderAttribute
        private int bufferSize;
        @PluginBuilderAttribute
        @Required(message="No table name provided")
        private String tableName;
        @PluginElement(value="ColumnConfigs")
        private ColumnConfig[] columnConfigs;
        @PluginElement(value="ColumnMappings")
        private ColumnMapping[] columnMappings;

        public B setConnectionSource(ConnectionSource connectionSource) {
            this.connectionSource = connectionSource;
            return (B)((Builder)this.asBuilder());
        }

        public B setBufferSize(int n) {
            this.bufferSize = n;
            return (B)((Builder)this.asBuilder());
        }

        public B setTableName(String string) {
            this.tableName = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setColumnConfigs(ColumnConfig ... columnConfigArray) {
            this.columnConfigs = columnConfigArray;
            return (B)((Builder)this.asBuilder());
        }

        public B setColumnMappings(ColumnMapping ... columnMappingArray) {
            this.columnMappings = columnMappingArray;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public JdbcAppender build() {
            if (Assert.isEmpty(this.columnConfigs) && Assert.isEmpty(this.columnMappings)) {
                JdbcAppender.access$000().error("Cannot create JdbcAppender without any columns configured.");
                return null;
            }
            String string = "JdbcManager{name=" + this.getName() + ", bufferSize=" + this.bufferSize + ", tableName=" + this.tableName + ", columnConfigs=" + Arrays.toString(this.columnConfigs) + ", columnMappings=" + Arrays.toString(this.columnMappings) + '}';
            JdbcDatabaseManager jdbcDatabaseManager = JdbcDatabaseManager.getManager(string, this.bufferSize, this.connectionSource, this.tableName, this.columnConfigs, this.columnMappings);
            if (jdbcDatabaseManager == null) {
                return null;
            }
            return new JdbcAppender(this.getName(), this.getFilter(), this.isIgnoreExceptions(), jdbcDatabaseManager, null);
        }

        @Override
        @Deprecated
        public Layout<? extends Serializable> getLayout() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public B withLayout(Layout<? extends Serializable> layout) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Layout<? extends Serializable> getOrCreateLayout() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public Layout<? extends Serializable> getOrCreateLayout(Charset charset) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Deprecated
        public AbstractAppender.Builder withLayout(Layout layout) {
            return this.withLayout(layout);
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

