package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.google.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import net.aYj;
import net.aq_;
import net.bgR;
import net.tp;

public class CommandBlockHandler implements aq_ {
   public int a(bgR var1, CompoundTag var2) {
      aYj.a();
      Tag var4 = var2.get("CustomName");
      if(var4 instanceof StringTag) {
         ((StringTag)var4).setValue(ChatRewriter.b(((StringTag)var4).getValue()));
      }

      Tag var5 = var2.get("LastOutput");
      if(var5 instanceof StringTag) {
         JsonElement var6 = tp.b().parse(((StringTag)var5).getValue());
         ChatRewriter.a(var6);
         ((StringTag)var5).setValue(var6.toString());
      }

      return -1;
   }
}
