package org.yaml.snakeyaml.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.Tag;

public class Resolver {
   public static final Pattern BOOL = Pattern.compile("^(?:yes|Yes|YES|no|No|NO|true|True|TRUE|false|False|FALSE|on|On|ON|off|Off|OFF)$");
   public static final Pattern FLOAT = Pattern.compile("^([-+]?(\\.[0-9]+|[0-9_]+(\\.[0-9_]*)?)([eE][-+]?[0-9]+)?|[-+]?[0-9][0-9_]*(?::[0-5]?[0-9])+\\.[0-9_]*|[-+]?\\.(?:inf|Inf|INF)|\\.(?:nan|NaN|NAN))$");
   public static final Pattern INT = Pattern.compile("^(?:[-+]?0b[0-1_]+|[-+]?0[0-7_]+|[-+]?(?:0|[1-9][0-9_]*)|[-+]?0x[0-9a-fA-F_]+|[-+]?[1-9][0-9_]*(?::[0-5]?[0-9])+)$");
   public static final Pattern MERGE = Pattern.compile("^(?:<<)$");
   public static final Pattern NULL = Pattern.compile("^(?:~|null|Null|NULL| )$");
   public static final Pattern EMPTY = Pattern.compile("^$");
   public static final Pattern TIMESTAMP = Pattern.compile("^(?:[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]|[0-9][0-9][0-9][0-9]-[0-9][0-9]?-[0-9][0-9]?(?:[Tt]|[ \t]+)[0-9][0-9]?:[0-9][0-9]:[0-9][0-9](?:\\.[0-9]*)?(?:[ \t]*(?:Z|[-+][0-9][0-9]?(?::[0-9][0-9])?))?)$");
   public static final Pattern VALUE = Pattern.compile("^(?:=)$");
   public static final Pattern YAML = Pattern.compile("^(?:!|&|\\*)$");
   protected Map yamlImplicitResolvers = new HashMap();

   protected void addImplicitResolvers() {
      this.addImplicitResolver(Tag.BOOL, BOOL, "yYnNtTfFoO");
      this.addImplicitResolver(Tag.INT, INT, "-+0123456789");
      this.addImplicitResolver(Tag.FLOAT, FLOAT, "-+0123456789.");
      this.addImplicitResolver(Tag.MERGE, MERGE, "<");
      this.addImplicitResolver(Tag.NULL, NULL, "~nN\u0000");
      this.addImplicitResolver(Tag.NULL, EMPTY, (String)null);
      this.addImplicitResolver(Tag.TIMESTAMP, TIMESTAMP, "0123456789");
      this.addImplicitResolver(Tag.YAML, YAML, "!&*");
   }

   public Resolver() {
      this.addImplicitResolvers();
   }

   public void addImplicitResolver(Tag var1, Pattern var2, String var3) {
      if (var3 == null) {
         Object var4 = (List)this.yamlImplicitResolvers.get((Object)null);
         if (var4 == null) {
            var4 = new ArrayList();
            this.yamlImplicitResolvers.put((Object)null, var4);
         }

         ((List)var4).add(new ResolverTuple(var1, var2));
      } else {
         char[] var9 = var3.toCharArray();
         int var5 = 0;

         for(int var6 = var9.length; var5 < var6; ++var5) {
            Character var7 = var9[var5];
            if (var7 == 0) {
               var7 = null;
            }

            Object var8 = (List)this.yamlImplicitResolvers.get(var7);
            if (var8 == null) {
               var8 = new ArrayList();
               this.yamlImplicitResolvers.put(var7, var8);
            }

            ((List)var8).add(new ResolverTuple(var1, var2));
         }
      }

   }

   public Tag resolve(NodeId var1, String var2, boolean var3) {
      if (var1 == NodeId.scalar && var3) {
         List var4 = null;
         if (var2.length() == 0) {
            var4 = (List)this.yamlImplicitResolvers.get('\u0000');
         } else {
            var4 = (List)this.yamlImplicitResolvers.get(var2.charAt(0));
         }

         Iterator var5;
         ResolverTuple var6;
         Tag var7;
         Pattern var8;
         if (var4 != null) {
            var5 = var4.iterator();

            while(var5.hasNext()) {
               var6 = (ResolverTuple)var5.next();
               var7 = var6.getTag();
               var8 = var6.getRegexp();
               if (var8.matcher(var2).matches()) {
                  return var7;
               }
            }
         }

         if (this.yamlImplicitResolvers.containsKey((Object)null)) {
            var5 = ((List)this.yamlImplicitResolvers.get((Object)null)).iterator();

            while(var5.hasNext()) {
               var6 = (ResolverTuple)var5.next();
               var7 = var6.getTag();
               var8 = var6.getRegexp();
               if (var8.matcher(var2).matches()) {
                  return var7;
               }
            }
         }
      }

      switch(var1) {
      case scalar:
         return Tag.STR;
      case sequence:
         return Tag.SEQ;
      default:
         return Tag.MAP;
      }
   }
}
