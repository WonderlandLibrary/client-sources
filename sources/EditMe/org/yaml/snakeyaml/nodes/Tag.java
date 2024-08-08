package org.yaml.snakeyaml.nodes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.util.UriEncoder;

public final class Tag implements Comparable {
   public static final String PREFIX = "tag:yaml.org,2002:";
   public static final Tag YAML = new Tag("tag:yaml.org,2002:yaml");
   public static final Tag MERGE = new Tag("tag:yaml.org,2002:merge");
   public static final Tag SET = new Tag("tag:yaml.org,2002:set");
   public static final Tag PAIRS = new Tag("tag:yaml.org,2002:pairs");
   public static final Tag OMAP = new Tag("tag:yaml.org,2002:omap");
   public static final Tag BINARY = new Tag("tag:yaml.org,2002:binary");
   public static final Tag INT = new Tag("tag:yaml.org,2002:int");
   public static final Tag FLOAT = new Tag("tag:yaml.org,2002:float");
   public static final Tag TIMESTAMP = new Tag("tag:yaml.org,2002:timestamp");
   public static final Tag BOOL = new Tag("tag:yaml.org,2002:bool");
   public static final Tag NULL = new Tag("tag:yaml.org,2002:null");
   public static final Tag STR = new Tag("tag:yaml.org,2002:str");
   public static final Tag SEQ = new Tag("tag:yaml.org,2002:seq");
   public static final Tag MAP = new Tag("tag:yaml.org,2002:map");
   public static final Map COMPATIBILITY_MAP = new HashMap();
   private final String value;
   private boolean secondary = false;

   public Tag(String var1) {
      if (var1 == null) {
         throw new NullPointerException("Tag must be provided.");
      } else if (var1.length() == 0) {
         throw new IllegalArgumentException("Tag must not be empty.");
      } else if (var1.trim().length() != var1.length()) {
         throw new IllegalArgumentException("Tag must not contain leading or trailing spaces.");
      } else {
         this.value = UriEncoder.encode(var1);
         this.secondary = !var1.startsWith("tag:yaml.org,2002:");
      }
   }

   public Tag(Class var1) {
      if (var1 == null) {
         throw new NullPointerException("Class for tag must be provided.");
      } else {
         this.value = "tag:yaml.org,2002:" + UriEncoder.encode(var1.getName());
      }
   }

   public Tag(URI var1) {
      if (var1 == null) {
         throw new NullPointerException("URI for tag must be provided.");
      } else {
         this.value = var1.toASCIIString();
      }
   }

   public boolean isSecondary() {
      return this.secondary;
   }

   public String getValue() {
      return this.value;
   }

   public boolean startsWith(String var1) {
      return this.value.startsWith(var1);
   }

   public String getClassName() {
      if (!this.value.startsWith("tag:yaml.org,2002:")) {
         throw new YAMLException("Invalid tag: " + this.value);
      } else {
         return UriEncoder.decode(this.value.substring("tag:yaml.org,2002:".length()));
      }
   }

   public int getLength() {
      return this.value.length();
   }

   public String toString() {
      return this.value;
   }

   public boolean equals(Object var1) {
      return var1 instanceof Tag ? this.value.equals(((Tag)var1).getValue()) : false;
   }

   public int hashCode() {
      return this.value.hashCode();
   }

   public boolean isCompatible(Class var1) {
      Set var2 = (Set)COMPATIBILITY_MAP.get(this);
      return var2 != null ? var2.contains(var1) : false;
   }

   public boolean matches(Class var1) {
      return this.value.equals("tag:yaml.org,2002:" + var1.getName());
   }

   public int compareTo(Tag var1) {
      return this.value.compareTo(var1.getValue());
   }

   public int compareTo(Object var1) {
      return this.compareTo((Tag)var1);
   }

   static {
      HashSet var0 = new HashSet();
      var0.add(Double.class);
      var0.add(Float.class);
      var0.add(BigDecimal.class);
      COMPATIBILITY_MAP.put(FLOAT, var0);
      HashSet var1 = new HashSet();
      var1.add(Integer.class);
      var1.add(Long.class);
      var1.add(BigInteger.class);
      COMPATIBILITY_MAP.put(INT, var1);
      HashSet var2 = new HashSet();
      var2.add(Date.class);
      var2.add(java.sql.Date.class);
      var2.add(Timestamp.class);
      COMPATIBILITY_MAP.put(TIMESTAMP, var2);
   }
}
