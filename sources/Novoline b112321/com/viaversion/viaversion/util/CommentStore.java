package com.viaversion.viaversion.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommentStore {
   private final Map headers = Maps.newConcurrentMap();
   private final char pathSeperator;
   private final int indents;
   private List mainHeader = Lists.newArrayList();

   public CommentStore(char var1, int var2) {
      this.pathSeperator = var1;
      this.indents = var2;
   }

   public void mainHeader(String... var1) {
      this.mainHeader = Arrays.asList(var1);
   }

   public List mainHeader() {
      return this.mainHeader;
   }

   public void header(String var1, String... var2) {
      this.headers.put(var1, Arrays.asList(var2));
   }

   public List header(String var1) {
      return (List)this.headers.get(var1);
   }

   public void storeComments(InputStream var1) throws IOException {
      Config.c();
      InputStreamReader var3 = new InputStreamReader(var1);
      InputStreamReader var10000 = var3;

      String var4;
      try {
         var4 = CharStreams.toString(var10000);
      } finally {
         var3.close();
      }

      StringBuilder var5 = new StringBuilder();
      String var6 = Character.toString(this.pathSeperator);
      byte var7 = 0;
      String var8 = "";
      ArrayList var9 = Lists.newArrayList();
      String[] var10 = var4.split("\n");
      int var11 = var10.length;
      int var12 = 0;
      if(var12 < var11) {
         String var13 = var10[var12];
         if(!var13.isEmpty()) {
            int var14 = this.getSuccessiveCharCount(var13, ' ');
            String var15 = var13.substring(var14);
            if(var15.startsWith("#")) {
               if(var15.startsWith("#>")) {
                  String var16 = var15.startsWith("#> ")?var15.substring(3):var15.substring(2);
                  this.mainHeader.add(var16);
               }

               String var24 = var15.startsWith("# ")?var15.substring(2):var15.substring(1);
               var9.add(var24);
            }

            int var25 = var14 / this.indents;
            if(var25 <= var7) {
               String[] var17 = var8.split(Pattern.quote(var6));
               int var18 = var7 - var25 + 1;
               var8 = this.join(var17, this.pathSeperator, 0, var17.length - var18);
            }

            String var26 = var8.length() > 0?var6:"";
            String var27 = var13.contains(":")?var13.split(Pattern.quote(":"))[0]:var13;
            var8 = var8 + var26 + var27.substring(var14);
            var5.append(var13).append('\n');
            if(!var9.isEmpty()) {
               this.headers.put(var8, var9);
               var9 = Lists.newArrayList();
            }
         }

         ++var12;
      }

      if(PacketRemapper.b() == null) {
         Config.b("ZzBHO");
      }

   }

   public void writeComments(String var1, File var2) throws IOException {
      int var4 = this.indents;
      Config.c();
      String var5 = Character.toString(this.pathSeperator);
      StringBuilder var6 = new StringBuilder();
      byte var7 = 0;
      String var8 = "";
      Iterator var9 = this.mainHeader.iterator();
      if(var9.hasNext()) {
         String var10 = (String)var9.next();
         var6.append("#> ").append(var10).append('\n');
      }

      String[] var28 = var1.split("\n");
      int var30 = var28.length;
      int var11 = 0;
      if(var11 < var30) {
         String var12 = var28[var11];
         if(!var12.isEmpty()) {
            int var13 = this.getSuccessiveCharCount(var12, ' ');
            int var14 = var13 / var4;
            String var15 = var12.substring(0, var13);
            if(var14 <= var7) {
               String[] var16 = var8.split(Pattern.quote(var5));
               int var17 = var7 - var14 + 1;
               var8 = this.join(var16, this.pathSeperator, 0, var16.length - var17);
            }

            String var32 = var8.length() > 0?var5:"";
            String var33 = var12.contains(":")?var12.split(Pattern.quote(":"))[0]:var12;
            var8 = var8 + var32 + var33.substring(var13);
            List var18 = (List)this.headers.get(var8);
            String var19 = this.addHeaderTags(var18, var15);
            var6.append(var19).append(var12).append('\n');
         }

         ++var11;
      }

      FileWriter var29 = null;

      try {
         var29 = new FileWriter(var2);
         var29.write(var6.toString());
         var29.flush();
      } finally {
         if(var29 != null) {
            FileWriter var10000 = var29;

            try {
               var10000.close();
            } catch (IOException var25) {
               ;
            }
         }

      }

   }

   private String addHeaderTags(List var1, String var2) {
      StringBuilder var4 = new StringBuilder();
      Config.c();
      Iterator var5 = var1.iterator();
      if(var5.hasNext()) {
         String var6 = (String)var5.next();
         var4.append(var2).append("# ").append(var6).append('\n');
      }

      return var4.toString();
   }

   private String join(String[] var1, char var2, int var3, int var4) {
      String[] var5 = new String[var4 - var3];
      System.arraycopy(var1, var3, var5, 0, var4 - var3);
      return Joiner.on(var2).join(var5);
   }

   private int getSuccessiveCharCount(String var1, char var2) {
      Config.c();
      int var4 = 0;
      int var5 = 0;
      if(var5 < var1.length() && var1.charAt(var5) == var2) {
         ++var4;
         ++var5;
      }

      return var4;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
