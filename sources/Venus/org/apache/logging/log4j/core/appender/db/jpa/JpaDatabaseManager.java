/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManager
 *  javax.persistence.EntityManagerFactory
 *  javax.persistence.EntityTransaction
 *  javax.persistence.Persistence
 */
package org.apache.logging.log4j.core.appender.db.jpa;

import java.lang.reflect.Constructor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
import org.apache.logging.log4j.core.appender.db.jpa.AbstractLogEventWrapperEntity;

public final class JpaDatabaseManager
extends AbstractDatabaseManager {
    private static final JPADatabaseManagerFactory FACTORY = new JPADatabaseManagerFactory(null);
    private final String entityClassName;
    private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
    private final String persistenceUnitName;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private JpaDatabaseManager(String string, int n, Class<? extends AbstractLogEventWrapperEntity> clazz, Constructor<? extends AbstractLogEventWrapperEntity> constructor, String string2) {
        super(string, n);
        this.entityClassName = clazz.getName();
        this.entityConstructor = constructor;
        this.persistenceUnitName = string2;
    }

    @Override
    protected void startupInternal() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory((String)this.persistenceUnitName);
    }

    @Override
    protected boolean shutdownInternal() {
        boolean bl = true;
        if (this.entityManager != null || this.transaction != null) {
            bl &= this.commitAndClose();
        }
        if (this.entityManagerFactory != null && this.entityManagerFactory.isOpen()) {
            this.entityManagerFactory.close();
        }
        return bl;
    }

    @Override
    protected void connectAndStart() {
        try {
            this.entityManager = this.entityManagerFactory.createEntityManager();
            this.transaction = this.entityManager.getTransaction();
            this.transaction.begin();
        } catch (Exception exception) {
            throw new AppenderLoggingException("Cannot write logging event or flush buffer; manager cannot create EntityManager or transaction.", exception);
        }
    }

    @Override
    protected void writeInternal(LogEvent logEvent) {
        AbstractLogEventWrapperEntity abstractLogEventWrapperEntity;
        if (!this.isRunning() || this.entityManagerFactory == null || this.entityManager == null || this.transaction == null) {
            throw new AppenderLoggingException("Cannot write logging event; JPA manager not connected to the database.");
        }
        try {
            abstractLogEventWrapperEntity = this.entityConstructor.newInstance(logEvent);
        } catch (Exception exception) {
            throw new AppenderLoggingException("Failed to instantiate entity class [" + this.entityClassName + "].", exception);
        }
        try {
            this.entityManager.persist((Object)abstractLogEventWrapperEntity);
        } catch (Exception exception) {
            if (this.transaction != null && this.transaction.isActive()) {
                this.transaction.rollback();
                this.transaction = null;
            }
            throw new AppenderLoggingException("Failed to insert record for log event in JPA manager: " + exception.getMessage(), exception);
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
            if (this.transaction == null) return bl;
            if (!this.transaction.isActive()) return bl;
            this.transaction.commit();
            return bl;
        } catch (Exception exception) {
            if (this.transaction == null) return bl;
            if (!this.transaction.isActive()) return bl;
            this.transaction.rollback();
            return bl;
        } finally {
            this.transaction = null;
            try {
                if (this.entityManager != null && this.entityManager.isOpen()) {
                    this.entityManager.close();
                }
            } catch (Exception exception) {
                this.logWarn("Failed to close entity manager while logging event or flushing buffer", exception);
                bl = false;
            } finally {
                this.entityManager = null;
            }
        }
    }

    public static JpaDatabaseManager getJPADatabaseManager(String string, int n, Class<? extends AbstractLogEventWrapperEntity> clazz, Constructor<? extends AbstractLogEventWrapperEntity> constructor, String string2) {
        return AbstractDatabaseManager.getManager(string, new FactoryData(n, clazz, constructor, string2), FACTORY);
    }

    JpaDatabaseManager(String string, int n, Class clazz, Constructor constructor, String string2, 1 var6_6) {
        this(string, n, clazz, constructor, string2);
    }

    static class 1 {
    }

    private static final class JPADatabaseManagerFactory
    implements ManagerFactory<JpaDatabaseManager, FactoryData> {
        private JPADatabaseManagerFactory() {
        }

        @Override
        public JpaDatabaseManager createManager(String string, FactoryData factoryData) {
            return new JpaDatabaseManager(string, factoryData.getBufferSize(), FactoryData.access$100(factoryData), FactoryData.access$200(factoryData), FactoryData.access$300(factoryData), null);
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        JPADatabaseManagerFactory(1 var1_1) {
            this();
        }
    }

    private static final class FactoryData
    extends AbstractDatabaseManager.AbstractFactoryData {
        private final Class<? extends AbstractLogEventWrapperEntity> entityClass;
        private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
        private final String persistenceUnitName;

        protected FactoryData(int n, Class<? extends AbstractLogEventWrapperEntity> clazz, Constructor<? extends AbstractLogEventWrapperEntity> constructor, String string) {
            super(n);
            this.entityClass = clazz;
            this.entityConstructor = constructor;
            this.persistenceUnitName = string;
        }

        static Class access$100(FactoryData factoryData) {
            return factoryData.entityClass;
        }

        static Constructor access$200(FactoryData factoryData) {
            return factoryData.entityConstructor;
        }

        static String access$300(FactoryData factoryData) {
            return factoryData.persistenceUnitName;
        }
    }
}

