package com.viaversion.viaversion.libs.gson.internal.sql;

import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.bind.DefaultDateTypeAdapter;
import java.sql.Date;
import java.sql.Timestamp;

public final class SqlTypesSupport {
   public static final boolean SUPPORTS_SQL_TYPES;
   public static final DefaultDateTypeAdapter.DateType<? extends java.util.Date> DATE_DATE_TYPE;
   public static final DefaultDateTypeAdapter.DateType<? extends java.util.Date> TIMESTAMP_DATE_TYPE;
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
      } catch (ClassNotFoundException var2) {
         sqlTypesSupport = false;
      }

      SUPPORTS_SQL_TYPES = sqlTypesSupport;
      if (SUPPORTS_SQL_TYPES) {
         DATE_DATE_TYPE = new DefaultDateTypeAdapter.DateType<Date>(Date.class) {
            protected Date deserialize(java.util.Date date) {
               return new Date(date.getTime());
            }
         };
         TIMESTAMP_DATE_TYPE = new DefaultDateTypeAdapter.DateType<Timestamp>(Timestamp.class) {
            protected Timestamp deserialize(java.util.Date date) {
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
