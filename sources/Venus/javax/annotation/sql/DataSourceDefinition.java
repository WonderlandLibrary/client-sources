/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package javax.annotation.sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.sql.DataSourceDefinitions;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Repeatable(value=DataSourceDefinitions.class)
public @interface DataSourceDefinition {
    public String name();

    public String className();

    public String description() default "";

    public String url() default "";

    public String user() default "";

    public String password() default "";

    public String databaseName() default "";

    public int portNumber() default -1;

    public String serverName() default "localhost";

    public int isolationLevel() default -1;

    public boolean transactional() default true;

    public int initialPoolSize() default -1;

    public int maxPoolSize() default -1;

    public int minPoolSize() default -1;

    public int maxIdleTime() default -1;

    public int maxStatements() default -1;

    public String[] properties() default {};

    public int loginTimeout() default 0;
}

