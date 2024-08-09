/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.db.jpa;

import java.lang.reflect.Constructor;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
import org.apache.logging.log4j.core.appender.db.jpa.AbstractLogEventWrapperEntity;
import org.apache.logging.log4j.core.appender.db.jpa.JpaDatabaseManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="JPA", category="Core", elementType="appender", printObject=true)
public final class JpaAppender
extends AbstractDatabaseAppender<JpaDatabaseManager> {
    private final String description = this.getName() + "{ manager=" + this.getManager() + " }";

    private JpaAppender(String string, Filter filter, boolean bl, JpaDatabaseManager jpaDatabaseManager) {
        super(string, filter, bl, jpaDatabaseManager);
    }

    @Override
    public String toString() {
        return this.description;
    }

    @PluginFactory
    public static JpaAppender createAppender(@PluginAttribute(value="name") String string, @PluginAttribute(value="ignoreExceptions") String string2, @PluginElement(value="Filter") Filter filter, @PluginAttribute(value="bufferSize") String string3, @PluginAttribute(value="entityClassName") String string4, @PluginAttribute(value="persistenceUnitName") String string5) {
        if (Strings.isEmpty(string4) || Strings.isEmpty(string5)) {
            LOGGER.error("Attributes entityClassName and persistenceUnitName are required for JPA Appender.");
            return null;
        }
        int n = AbstractAppender.parseInt(string3, 0);
        boolean bl = Booleans.parseBoolean(string2, true);
        try {
            Class<AbstractLogEventWrapperEntity> clazz = LoaderUtil.loadClass(string4).asSubclass(AbstractLogEventWrapperEntity.class);
            try {
                clazz.getConstructor(new Class[0]);
            } catch (NoSuchMethodException noSuchMethodException) {
                LOGGER.error("Entity class [{}] does not have a no-arg constructor. The JPA provider will reject it.", (Object)string4);
                return null;
            }
            Constructor<AbstractLogEventWrapperEntity> constructor = clazz.getConstructor(LogEvent.class);
            String string6 = "jpaManager{ description=" + string + ", bufferSize=" + n + ", persistenceUnitName=" + string5 + ", entityClass=" + clazz.getName() + '}';
            JpaDatabaseManager jpaDatabaseManager = JpaDatabaseManager.getJPADatabaseManager(string6, n, clazz, constructor, string5);
            if (jpaDatabaseManager == null) {
                return null;
            }
            return new JpaAppender(string, filter, bl, jpaDatabaseManager);
        } catch (ClassNotFoundException classNotFoundException) {
            LOGGER.error("Could not load entity class [{}].", (Object)string4, (Object)classNotFoundException);
            return null;
        } catch (NoSuchMethodException noSuchMethodException) {
            LOGGER.error("Entity class [{}] does not have a constructor with a single argument of type LogEvent.", (Object)string4);
            return null;
        } catch (ClassCastException classCastException) {
            LOGGER.error("Entity class [{}] does not extend AbstractLogEventWrapperEntity.", (Object)string4);
            return null;
        }
    }
}

