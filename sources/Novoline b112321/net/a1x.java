package net;

import org.apache.commons.lang3.StringUtils;

public enum a1x {
   CRACKED,
   PREMIUM;

   private final String a = StringUtils.capitalize(this.name().toLowerCase());

   public String a() {
      return this.a;
   }
}
