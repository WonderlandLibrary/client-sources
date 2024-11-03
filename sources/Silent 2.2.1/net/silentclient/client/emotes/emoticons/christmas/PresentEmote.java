package net.silentclient.client.emotes.emoticons.christmas;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.emoticons.PropEmote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;

public class PresentEmote extends PropEmote {
   public int item = -1;

   public PresentEmote(String s, String s1) {
      super(s, s1);
      this.props("prop_present");
   }

   public PresentEmote(String s, String s1, int i) {
      this(s, s1);
      this.item = i;
   }

   @Override
   public Emote getDynamicEmote() {
      return this.getDynamicEmote(String.valueOf(this.rand.nextInt(6) + 1));
   }

   @Override
   public Emote getDynamicEmote(String s) {
      int i = -1;

      try {
         i = Integer.parseInt(s);
      } catch (Exception var4) {
      }

      return new PresentEmote(this.id, this.title, i).looping(this.looping);
   }

   @Override
   public Emote set(String s) {
      return s.contains(":") ? this.getDynamicEmote(s.split(":")[1]) : this;
   }

   @Override
   public String getKey() {
      return this.id + (this.item == -1 ? "" : ":" + this.item);
   }

   @Override
   public String getActionName() {
      return super.getActionName() + (this.item <= 3 ? ":bad" : ":good");
   }

   @Override
   public void startAnimation(IEmoteAccessor iemoteaccessor) {
      super.startAnimation(iemoteaccessor);
      Item o = Items.coal;
      if (this.item == 1) {
         o = Items.fish;
      } else if (this.item == 2) {
         o = Items.stick;
      } else if (this.item == 4) {
         o = Items.diamond;
      } else if (this.item == 5) {
         ItemEnchantedBook m = Items.enchanted_book;
      } else if (this.item == 6) {
         o = Items.nether_star;
      }

      ItemStack m = new ItemStack(o);
      if (this.item == 1) {
      }

      iemoteaccessor.setItem(m);
      iemoteaccessor.setItemScale(0.0F);
   }

   @Override
   public void stopAnimation(IEmoteAccessor iemoteaccessor) {
      super.stopAnimation(iemoteaccessor);
      iemoteaccessor.setItem(null);
      iemoteaccessor.setItemScale(0.0F);
   }

   @Override
   public void progressAnimation(IEmoteAccessor iemoteaccessor, BOBJArmature var2, int i, float f) {
   }
}
