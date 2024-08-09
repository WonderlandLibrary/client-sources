/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.gson.internal.sql;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.DefaultDateTypeAdapter;
import com.google.gson.internal.sql.SqlDateTypeAdapter;
import com.google.gson.internal.sql.SqlTimeTypeAdapter;
import com.google.gson.internal.sql.SqlTimestampTypeAdapter;
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
        boolean bl;
        try {
            Class.forName("java.sql.Date");
            bl = true;
        } catch (ClassNotFoundException classNotFoundException) {
            bl = false;
        }
        SUPPORTS_SQL_TYPES = bl;
        if (SUPPORTS_SQL_TYPES) {
            DATE_DATE_TYPE = new DefaultDateTypeAdapter.DateType<java.sql.Date>(java.sql.Date.class){

                @Override
                protected java.sql.Date deserialize(Date date) {
                    return new java.sql.Date(date.getTime());
                }

                @Override
                protected Date deserialize(Date date) {
                    return this.deserialize(date);
                }
            };
            TIMESTAMP_DATE_TYPE = new DefaultDateTypeAdapter.DateType<Timestamp>(Timestamp.class){

                @Override
                protected Timestamp deserialize(Date date) {
                    return new Timestamp(date.getTime());
                }

                @Override
                protected Date deserialize(Date date) {
                    return this.deserialize(date);
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

