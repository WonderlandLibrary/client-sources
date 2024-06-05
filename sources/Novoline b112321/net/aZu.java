package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage$Property;
import de.gerrygames.viarewind.utils.ChatUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.aMz;
import net.cf;
import net.md_5.bungee.api.ChatColor;

public class aZu {
   public String e;
   public String c;
   public int b;
   public UUID f;
   public List d = new ArrayList();
   public int a = 0;

   public aZu(UUID var1, String var2) {
      this.e = var2;
      this.f = var1;
   }

   public aMz a() {
      CompoundTag var2 = new CompoundTag("");
      cf.b();
      CompoundTag var3 = new CompoundTag("SkullOwner");
      var2.put(var3);
      var3.put(new StringTag("Id", this.f.toString()));
      CompoundTag var4 = new CompoundTag("Properties");
      var3.put(var4);
      ListTag var5 = new ListTag("textures", CompoundTag.class);
      var4.put(var5);
      Iterator var6 = this.d.iterator();
      if(var6.hasNext()) {
         GameProfileStorage$Property var7 = (GameProfileStorage$Property)var6.next();
         if(var7.name.equals("textures")) {
            CompoundTag var8 = new CompoundTag("");
            var8.put(new StringTag("Value", var7.value));
            if(var7.signature != null) {
               var8.put(new StringTag("Signature", var7.signature));
            }

            var5.add(var8);
         }
      }

      return new aMz(397, (byte)1, (short)3, var2);
   }

   public String b() {
      String var1 = cf.b();
      String var2 = this.c == null?this.e:this.c;
      if(var2.length() > 16) {
         var2 = ChatUtil.removeUnusedColor(var2, 'f');
      }

      if(var2.length() > 16) {
         var2 = ChatColor.stripColor(var2);
      }

      if(var2.length() > 16) {
         var2 = var2.substring(0, 16);
      }

      return var2;
   }

   public void a(String var1) {
      this.c = var1;
   }
}
