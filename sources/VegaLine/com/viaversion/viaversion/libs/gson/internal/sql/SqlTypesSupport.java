/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson.internal.sql;

import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.DefaultDateTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.sql.SqlDateTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.sql.SqlTimeTypeAdapter;
import com.viaversion.viaversion.libs.gson.internal.sql.SqlTimestampTypeAdapter;
import java.sql.Timestamp;
import java.util.Date;

public final class SqlTypesSupport {
    public static final boolean SUPPORTS_SQL_TYPES;
    public static final DefaultDateTypeAdapter.DateType<? extends Date> DATE_DATE_TYPE;
    public static final DefaultDateTypeAdapter.DateType<? extends Date> TIMESTAMP_DATE_TYPE;
    public static final TypeAdapterFactory DATE_FACTORY;
    public static final TypeAdapterFactory TIME_FACTORY;
    public static final TypeAdapterFactory TIMESTAMP_FACTORY;

    private SqlTypesSupport() {
    }

    static {
        boolean sqlTypesSupport;
        try {
            Class.forName("java.sql.Date");
            sqlTypesSupport = true;
        } catch (ClassNotFoundException classNotFoundException) {
            sqlTypesSupport = false;
        }
        SUPPORTS_SQL_TYPES = sqlTypesSupport;
        if (SUPPORTS_SQL_TYPES) {
            DATE_DATE_TYPE = new DefaultDateTypeAdapter.DateType<java.sql.Date>(java.sql.Date.class){

                @Override
                protected java.sql.Date deserialize(Date date) {
                    return new java.sql.Date(date.getTime());
                }
            };
            TIMESTAMP_DATE_TYPE = new DefaultDateTypeAdapter.DateType<Timestamp>(Timestamp.class){

                @Override
                protected Timestamp deserialize(Date date) {
                    return new Timestamp(date.getTime());
                }
            };
            DATE_FACTORY = SqlDateTypeAdapter.FACTORY;
            TIME_FACTORY = SqlTimeTypeAdapter.FACTORY;
            TIMESTAMP_FACTORY = SqlTimestampTypeAdapter.FACTORY;
        } else {
            DATE_DATE_TYPE = null;
            TIMESTAMP_DATE_TYPE = null;
            DATE_FACTORY = null;
            TIME_FACTORY = null;
            TIMESTAMP_FACTORY = null;
        }
    }
}

