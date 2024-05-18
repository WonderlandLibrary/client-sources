package rina.turok.bope.bopemod.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventPacket;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeChatStyle extends BopeModule {
   public List colors_combobox = this.combobox(new String[]{"Disabled", "Black", "Red", "Aqua", "Blue", "Gold", "Gray", "White", "Green", "Yello", "Dark_Red", "Dark_Aqua", "Dark_Blue", "Dark_Gray", "Dark_Green", "Dark_Purple", "Light_Purple"});
   BopeSetting color_time;
   BopeSetting color_name;
   BopeSetting color_fname;
   BopeSetting type_mode;
   public static HashMap color = new HashMap();
   boolean event_color_time;
   boolean event_color_name;
   @EventHandler
   private Listener<ClientChatReceivedEvent> listener;

   public BopeChatStyle() {
      super(BopeCategory.BOPE_CHAT, false);
      this.color_time = this.create("Time", "ChatStyleColorTime", (String)this.colors_combobox.get(0), this.colors_combobox);
      this.color_name = this.create("Name", "ChatStyleColorMessage", (String)this.colors_combobox.get(0), this.colors_combobox);
      this.color_fname = this.create("Friend", "ChatStyleColorFriend", (String)this.colors_combobox.get(14), this.colors_combobox);
      this.type_mode = this.create("Separator", "ChatStyleSeparator", "<>", this.combobox(new String[]{"[]", "<>", "None"}));
      this.event_color_time = true;
      this.event_color_name = true;
      this.listener = new Listener<>(event -> {

         TextComponentString message = new TextComponentString("");
         String original_message = event.getMessage().getUnformattedText();
         boolean cancel = false;
         boolean is_name = false;
         boolean is_friend = false;
         this.event_color_time = true;
         this.event_color_name = true;
         String name = original_message.trim().split("\\s+")[0];
         if (name.contains("<") && name.contains(">")) {
            is_name = true;
         }

         name = name.replaceAll("<", "");
         name = name.replaceAll(">", "");
         if (Bope.get_friend_manager().is_friend(name) || this.mc.player.getName().equalsIgnoreCase(name)) {
            is_friend = true;
         }

         String pre = "";
         String end = "";
         boolean none = true;
         if (!this.type_mode.in("None")) {
            pre = this.type_mode.in("[]") ? "[" : "<";
            end = this.type_mode.in("[]") ? "]" : ">";
            none = false;
         }

         if (this.color_time.in("Disabled")) {
            this.event_color_time = false;
         }

         if (this.color_fname.in("Disabled") && this.color_name.in("Disabled")) {
            this.event_color_name = false;
         }

         if (this.color_name.in("Disabled")) {
            this.event_color_name = false;
         }

         if (!this.color_fname.in("Disabled") && this.color_name.in("Disabled")) {
            this.event_color_name = true;
         }

         ChatFormatting c;
         if (this.event_color_time) {
            c = (ChatFormatting)color.get(this.color_time.get_current_value());
            message.appendText(Bope.r + pre + c + (new SimpleDateFormat("k:mm:a")).format(new Date()) + Bope.r + end + (is_name ? (none ? " " : "") : " "));
            cancel = false;
         }

         if (this.event_color_name && is_name) {
            if (is_friend && !this.color_fname.in("Disabled")) {
               c = (ChatFormatting)color.get(this.color_fname.get_current_value());
            } else {
               c = (ChatFormatting)color.get(this.color_name.get_current_value());
            }

            String[] separates = original_message.trim().split("\\s+");
            String base_1 = separates[0];
            base_1 = base_1.replaceAll("<", Bope.r + "<" + c);
            base_1 = base_1.replaceAll(">", Bope.r + ">" + Bope.r);
            String message_of_player = original_message.substring(separates[0].length());
            message.appendText(base_1 + message_of_player);
            cancel = true;
         }

         if (!cancel) {
            message.appendSibling(event.getMessage());
         }

         event.setMessage(message);
      });
      this.name = "Chat Style";
      this.tag = "ChatStyle";
      this.description = "Chat style settings.";
      this.release("B.O.P.E - Module - B.O.P.E");
      color.put(this.colors_combobox.get(1), ChatFormatting.BLACK);
      color.put(this.colors_combobox.get(2), ChatFormatting.RED);
      color.put(this.colors_combobox.get(3), ChatFormatting.AQUA);
      color.put(this.colors_combobox.get(4), ChatFormatting.BLUE);
      color.put(this.colors_combobox.get(5), ChatFormatting.GOLD);
      color.put(this.colors_combobox.get(6), ChatFormatting.GRAY);
      color.put(this.colors_combobox.get(7), ChatFormatting.WHITE);
      color.put(this.colors_combobox.get(8), ChatFormatting.GREEN);
      color.put(this.colors_combobox.get(9), ChatFormatting.YELLOW);
      color.put(this.colors_combobox.get(10), ChatFormatting.DARK_RED);
      color.put(this.colors_combobox.get(11), ChatFormatting.DARK_AQUA);
      color.put(this.colors_combobox.get(12), ChatFormatting.DARK_BLUE);
      color.put(this.colors_combobox.get(13), ChatFormatting.DARK_GRAY);
      color.put(this.colors_combobox.get(14), ChatFormatting.DARK_GREEN);
      color.put(this.colors_combobox.get(15), ChatFormatting.DARK_PURPLE);
      color.put(this.colors_combobox.get(16), ChatFormatting.LIGHT_PURPLE);
   }
}
