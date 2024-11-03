package net.silentclient.client.emotes.emoticons.emoticons;

import net.silentclient.client.emotes.bobj.BOBJArmature;
import net.silentclient.client.emotes.emoticons.Emote;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RockPaperScissorsEmote extends Emote {
   public String suffix = "";

   public RockPaperScissorsEmote(String s, String s1, int var3) {
      super(s, s1);
   }

   public RockPaperScissorsEmote(String s, String s1, int i, String s2) {
      this(s, s1, i);
      this.suffix = s2;
   }

   @Override
   public Emote getDynamicEmote() {
      String s = "";
      int i = this.rand.nextInt(30);
      if (i <= 10) {
         s = "rock";
      } else if (i <= 20) {
         s = "paper";
      } else if (i <= 30) {
         s = "scissors";
      }

      return this.getDynamicEmote(s);
   }

   @Override
   public Emote getDynamicEmote(String s) {
      return new RockPaperScissorsEmote(this.id, this.title, this.looping, s);
   }

   @Override
   public Emote set(String s) {
      return s.contains(":") ? this.getDynamicEmote(s.split(":")[1]) : this;
   }

   @Override
   public String getKey() {
      return this.id + (this.suffix.isEmpty() ? "" : ":" + this.suffix);
   }

   @Override
   public void startAnimation(IEmoteAccessor iemoteaccessor) {
      super.startAnimation(iemoteaccessor);
      ItemStack m;
      if (this.suffix.equals("rock")) {
         m = new ItemStack(Blocks.stone);
      } else if (this.suffix.equals("paper")) {
         m = new ItemStack(Items.paper);
      } else {
         m = new ItemStack(Items.shears);
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
