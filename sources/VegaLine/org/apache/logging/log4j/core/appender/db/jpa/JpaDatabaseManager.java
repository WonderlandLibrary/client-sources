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
    private static final JPADatabaseManagerFactory FACTORY = new JPADatabaseManagerFactory();
    private final String entityClassName;
    private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
    private final String persistenceUnitName;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    private JpaDatabaseManager(String name, int bufferSize, Class<? extends AbstractLogEventWrapperEntity> entityClass, Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor, String persistenceUnitName) {
        super(name, bufferSize);
        this.entityClassName = entityClass.getName();
        this.entityConstructor = entityConstructor;
        this.persistenceUnitName = persistenceUnitName;
    }

    @Override
    protected void startupInternal() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory((String)this.persistenceUnitName);
    }

    @Override
    protected boolean shutdownInternal() {
        boolean closed = true;
        if (this.entityManager != null || this.transaction != null) {
            closed &= this.commitAndClose();
        }
        if (this.entityManagerFactory != null && this.entityManagerFactory.isOpen()) {
            this.entityManagerFactory.close();
        }
        return closed;
    }

    @Override
    protected void connectAndStart() {
        try {
            this.entityManager = this.entityManagerFactory.createEntityManager();
            this.transaction = this.entityManager.getTransaction();
            this.transaction.begin();
        } catch (Exception e) {
            throw new AppenderLoggingException("Cannot write logging event or flush buffer; manager cannot create EntityManager or transaction.", e);
        }
    }

    @Override
    protected void writeInternal(LogEvent event) {
        AbstractLogEventWrapperEntity entity;
        if (!this.isRunning() || this.entityManagerFactory == null || this.entityManager == null || this.transaction == null) {
            throw new AppenderLoggingException("Cannot write logging event; JPA manager not connected to the database.");
        }
        try {
            entity = this.entityConstructor.newInstance(event);
        } catch (Exception e) {
            throw new AppenderLoggingException("Failed to instantiate entity class [" + this.entityClassName + "].", e);
        }
        try {
            this.entityManager.persist((Object)entity);
        } catch (Exception e) {
            if (this.transaction != null && this.transaction.isActive()) {
                this.transaction.rollback();
                this.transaction = null;
            }
            throw new AppenderLoggingException("Failed to insert record for log event in JPA manager: " + e.getMessage(), e);
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
            if (this.transaction == null) return closed;
            if (!this.transaction.isActive()) return closed;
            this.transaction.commit();
            return closed;
        } catch (Exception e) {
            if (this.transaction == null) return closed;
            if (!this.transaction.isActive()) return closed;
            this.transaction.rollback();
            return closed;
        } finally {
            this.transaction = null;
            try {
                if (this.entityManager != null && this.entityManager.isOpen()) {
                    this.entityManager.close();
                }
            } catch (Exception e) {
                this.logWarn("Failed to close entity manager while logging event or flushing buffer", e);
                closed = false;
            } finally {
                this.entityManager = null;
            }
        }
    }

    public static JpaDatabaseManager getJPADatabaseManager(String name, int bufferSize, Class<? extends AbstractLogEventWrapperEntity> entityClass, Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor, String persistenceUnitName) {
        return AbstractDatabaseManager.getManager(name, new FactoryData(bufferSize, entityClass, entityConstructor, persistenceUnitName), FACTORY);
    }

    private static final class JPADatabaseManagerFactory
    implements ManagerFactory<JpaDatabaseManager, FactoryData> {
        private JPADatabaseManagerFactory() {
        }

        @Override
        public JpaDatabaseManager createManager(String name, FactoryData data) {
            return new JpaDatabaseManager(name, data.getBufferSize(), data.entityClass, data.entityConstructor, data.persistenceUnitName);
        }
    }

    private static final class FactoryData
    extends AbstractDatabaseManager.AbstractFactoryData {
        private final Class<? extends AbstractLogEventWrapperEntity> entityClass;
        private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
        private final String persistenceUnitName;

        protected FactoryData(int bufferSize, Class<? extends AbstractLogEventWrapperEntity> entityClass, Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor, String persistenceUnitName) {
            super(bufferSize);
            this.entityClass = entityClass;
            this.entityConstructor = entityConstructor;
            this.persistenceUnitName = persistenceUnitName;
        }
    }
}

