package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import net.aqu;
import net.ayd;
import net.ln;
import org.jetbrains.annotations.Nullable;
import viaversion.viabackwards.api.rewriters.ItemRewriterBase;
import viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import viaversion.viaversion.api.minecraft.item.Item;

public abstract class aqq extends ItemRewriterBase {
   private final TranslatableRewriter g;
   private static String h;

   protected aqq(ayd var1, @Nullable TranslatableRewriter var2) {
      super(var1, true);
      this.g = var2;
   }

   @Nullable
   public Item a(Item var1) {
      int var2 = aqu.e();
      if(var1 == null) {
         return null;
      } else {
         CompoundTag var3 = var1.getTag() != null?(CompoundTag)var1.getTag().get("display"):null;
         if(this.g != null && var3 != null) {
            StringTag var4 = (StringTag)var3.get("Name");
            if(var4 != null) {
               String var5 = this.g.a(var4.getValue()).toString();
               if(!var5.equals(var4.getValue())) {
                  this.saveNameTag(var3, var4);
               }

               var4.setValue(var5);
            }

            ListTag var13 = (ListTag)var3.get("Lore");
            ListTag var6 = null;
            boolean var7 = false;

            for(Tag var9 : var13) {
               if(var9 instanceof StringTag) {
                  StringTag var10 = (StringTag)var9;
                  String var11 = this.g.a(var10.getValue()).toString();
                  if(!var11.equals(var10.getValue())) {
                     var7 = true;
                     var6 = var13.clone();
                  }

                  var10.setValue(var11);
                  break;
               }
            }

            if(var7) {
               this.saveLoreTag(var3, var6);
            }
         }

         ln var12 = this.c.getMappingData().a(var1.getIdentifier());
         return super.a(var1);
      }
   }

   @Nullable
   public Item c(Item var1) {
      int var2 = aqu.e();
      if(var1 == null) {
         return null;
      } else {
         super.c(var1);
         if(var1.getTag() != null) {
            IntTag var3 = (IntTag)var1.getTag().remove(this.nbtTagName + "|id");
            var1.setIdentifier(var3.getValue().intValue());
         }

         return var1;
      }
   }

   public static void b(String var0) {
      h = var0;
   }

   public static String a() {
      return h;
   }

   static {
      b("We8cqc");
   }
}
