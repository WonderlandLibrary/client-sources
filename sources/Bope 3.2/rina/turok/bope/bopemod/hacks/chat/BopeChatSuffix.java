package rina.turok.bope.bopemod.hacks.chat;

import java.util.Random;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventPacket;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeChatSuffix extends BopeModule {
   BopeSetting ignore = this.create("Ignore", "ChatSuffixIgnore", true);
   BopeSetting type = this.create("Type", "ChatSuffixType", "Default", this.combobox(new String[]{"Default", "Random", "Custom"}));
   BopeSetting custom = this.create("Custom", "ChatSuffixCustom", "bope");
   boolean accept_suffix;
   boolean suffix_default;
   boolean suffix_random;
   boolean suffix_custom;
   StringBuilder suffix;
   String[] random_suffix_pre = new String[]{"bope", "turok", "mercury", "salhack", "phbobos", "waohack", "nutgod", "nutlord", "nutfail", "orkut", "osiris", "seppuku", "aurora", "wwe", "rusherhack", "hephaestus", "impact", "team", "rama", "oct", "future", "huzuni", "kami", "optifine", "wrust", "root", "holocaust", "future", "aristois", "axiom", "fit", "rina", "zbob", "trump", "ares", "oldturok", "kotlin", "keemy", "based", "rgba", "rgb", "obama", "google", "facebook", "hack"};
   String[] random_suffix_mid = new String[]{".eu", ".ez", ".gg", ".cc", ".ff", ".kt", ".gay", ".java", ".god", " "};
   String[] random_suffix_end = new String[]{" ", " \u262d", " \u0fc9", " \u2620", " \u2623", " \u2654", " \u2764", " \u267f", " \u262f"};
   @EventHandler
   private Listener<BopeEventPacket.SendPacket> listener = new Listener<>(event -> {
      if (event.get_packet() instanceof CPacketChatMessage) {
         this.accept_suffix = true;
         boolean ignore_prefix = this.ignore.get_value(true);
         String message = ((CPacketChatMessage)event.get_packet()).getMessage();
         if (message.startsWith("/") || message.startsWith("\\") || message.startsWith("!") || message.startsWith(":") || message.startsWith(";") || message.startsWith(".") || message.startsWith(",") || message.startsWith("@") || message.startsWith("&") || message.startsWith("*") || message.startsWith("$") || message.startsWith("#") || message.startsWith("(") || message.startsWith(")") && ignore_prefix) {
            this.accept_suffix = false;
         }

         if (this.type.in("Default")) {
            this.suffix_default = true;
            this.suffix_random = false;
            this.suffix_custom = false;
         }

         if (this.type.in("Random")) {
            this.suffix_default = false;
            this.suffix_random = true;
            this.suffix_custom = false;
         }

         if (this.type.in("Custom")) {
            this.suffix_default = false;
            this.suffix_random = false;
            this.suffix_custom = true;
         }

         if (this.accept_suffix) {
            if (this.suffix_default) {
               message = message + " \u23d0 \u2620 " + this.convert_base("bope") + " \u2620";
            }

            if (this.suffix_random) {
               StringBuilder suffix_with_randoms = new StringBuilder();
               suffix_with_randoms.append(this.convert_base(this.random_string(this.random_suffix_pre)));
               if (this.r(3, 1)) {
                  suffix_with_randoms.append(this.convert_base(this.random_string(this.random_suffix_mid)));
               }

               if (this.r(5, 4)) {
                  suffix_with_randoms.append(this.convert_base(this.random_string(this.random_suffix_end)));
               }

               message = message + " \u23d0 " + suffix_with_randoms.toString();
            }

            if (this.suffix_custom) {
               message = message + " \u23d0 " + this.convert_base(this.custom.get_value(""));
            }

            if (message.length() >= 256) {
               message.substring(0, 256);
            }
         }

         ((CPacketChatMessage)event.get_packet()).message = message;
      }
   });

   public BopeChatSuffix() {
      super(BopeCategory.BOPE_CHAT, false);
      this.name = "Chat Suffix";
      this.tag = "ChatSuffix";
      this.description = "Sentence end with a pretty suffix.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public String random_string(String[] list) {
      return list[(new Random()).nextInt(list.length)];
   }

   public boolean r(int max, int equals) {
      return (new Random()).nextInt(max) == equals;
   }

   public String convert_base(String base) {
      return Bope.smooth(base);
   }

   public String detail_option() {
      return this.type.get_current_value();
   }
}
