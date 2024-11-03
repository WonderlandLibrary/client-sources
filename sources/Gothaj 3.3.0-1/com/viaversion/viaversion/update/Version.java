package com.viaversion.viaversion.update;

import com.google.common.base.Joiner;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Comparable<Version> {
   private static final Pattern semVer = Pattern.compile("(?<a>0|[1-9]\\d*)\\.(?<b>0|[1-9]\\d*)(?:\\.(?<c>0|[1-9]\\d*))?(?:-(?<tag>[A-z0-9.-]*))?");
   private final int[] parts = new int[3];
   private final String tag;

   public Version(String value) {
      if (value == null) {
         throw new IllegalArgumentException("Version can not be null");
      } else {
         Matcher matcher = semVer.matcher(value);
         if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format");
         } else {
            this.parts[0] = Integer.parseInt(matcher.group("a"));
            this.parts[1] = Integer.parseInt(matcher.group("b"));
            this.parts[2] = matcher.group("c") == null ? 0 : Integer.parseInt(matcher.group("c"));
            this.tag = matcher.group("tag") == null ? "" : matcher.group("tag");
         }
      }
   }

   public static int compare(Version verA, Version verB) {
      if (verA == verB) {
         return 0;
      } else if (verA == null) {
         return -1;
      } else if (verB == null) {
         return 1;
      } else {
         int max = Math.max(verA.parts.length, verB.parts.length);

         for (int i = 0; i < max; i++) {
            int partA = i < verA.parts.length ? verA.parts[i] : 0;
            int partB = i < verB.parts.length ? verB.parts[i] : 0;
            if (partA < partB) {
               return -1;
            }

            if (partA > partB) {
               return 1;
            }
         }

         if (verA.tag.isEmpty() && !verB.tag.isEmpty()) {
            return 1;
         } else {
            return !verA.tag.isEmpty() && verB.tag.isEmpty() ? -1 : 0;
         }
      }
   }

   public static boolean equals(Version verA, Version verB) {
      return verA == verB || verA != null && verB != null && compare(verA, verB) == 0;
   }

   @Override
   public String toString() {
      String[] split = new String[this.parts.length];

      for (int i = 0; i < this.parts.length; i++) {
         split[i] = String.valueOf(this.parts[i]);
      }

      return Joiner.on(".").join(split) + (!this.tag.isEmpty() ? "-" + this.tag : "");
   }

   public int compareTo(Version that) {
      return compare(this, that);
   }

   @Override
   public boolean equals(Object that) {
      return that instanceof Version && equals(this, (Version)that);
   }

   @Override
   public int hashCode() {
      int result = Objects.hash(this.tag);
      return 31 * result + Arrays.hashCode(this.parts);
   }

   public String getTag() {
      return this.tag;
   }
}
