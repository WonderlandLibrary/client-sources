package me.travis.wurstplus.module.modules.chat;

// import com.mojang.realmsclient.gui.ChatFormatting;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.function.Predicate;
// import me.zero.alpine.listener.EventHandler;
// import me.zero.alpine.listener.Listener;
// import me.travis.wurstplus.command.Command;
// import me.travis.wurstplus.event.events.PacketEvent;
import me.travis.wurstplus.module.Module;
// import me.travis.wurstplus.setting.Setting;
// import me.travis.wurstplus.setting.Settings;
// import net.minecraft.network.play.server.SPacketChat;
// import java.util.Random;

@Module.Info(name="ChatTimeStamps", category=Module.Category.CHAT)
public class ChatTimeStamps extends Module {

   /* private Setting<ColourType> colourType = this.register(Settings.e("Colour", ColourType.GOLD));
    private Setting<CharacterType> characterType = this.register(Settings.e("Character", CharacterType.STRAIGHTBRACKETS));
    private Setting<Boolean> showAMPM = this.register(Settings.b("Show AM/PM", true));
    private Setting<Boolean> rainbowGoods = this.register(Settings.b("Elementars Chat Mode", false));

    @EventHandler
    public Listener<PacketEvent.Receive> listener = new Listener<PacketEvent.Receive>(event -> {
        if (mc.player == null || this.isDisabled()) {
            return;
        }
        if (!(event.getPacket() instanceof SPacketChat)) {
            return;
        }
        SPacketChat sPacketChat = (SPacketChat) event.getPacket();
        String data = "";
        if (this.characterType.getValue().equals((Object) CharacterType.STRAIGHTBRACKETS)) {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + "[" + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + "]" + (Object) ChatFormatting.RESET + " ";
        } else if (this.characterType.getValue().equals((Object) CharacterType.CURLYBRACKETS)) {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + "(" + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + ")" + (Object) ChatFormatting.RESET + " ";
        } else if (this.characterType.getValue().equals((Object) CharacterType.FUCK)) {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + "\u00A7k(\u00A7r" + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + getTimestampColour() + "\u00A7k)\u00A7r" + (Object) ChatFormatting.RESET + " ";
        } else if (this.characterType.getValue().equals((Object) CharacterType.FANCYBRACKETS)) {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + "{" + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + "}" + (Object) ChatFormatting.RESET + " ";
        } else if (this.characterType.getValue().equals((Object) CharacterType.ARROWS)) {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + "<" + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + ">" + (Object) ChatFormatting.RESET + " ";
        } else if (this.characterType.getValue().equals((Object) CharacterType.PLUS)) {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + "+" + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + "+" + (Object) ChatFormatting.RESET + " ";
        } else if (this.characterType.getValue().equals((Object) CharacterType.MINUS)) {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + "-" + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + "-" + (Object) ChatFormatting.RESET + " ";
        } else if (this.characterType.getValue().equals((Object) CharacterType.EQUALS)) {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + "=" + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + "=" + (Object) ChatFormatting.RESET + " ";
        } else {
            data = (Object) ChatFormatting.GRAY + getTimestampColour() + (this.showAMPM.getValue() ? new SimpleDateFormat("k:mm a").format(new Date()) : new SimpleDateFormat("k:mm").format(new Date())) + (Object) ChatFormatting.RESET + " ";
        }

        String s = "";
        String str = sPacketChat.getChatComponent().getUnformattedText();

        if (this.rainbowGoods.getValue()) {
            s = data + getName(str) + getMessageRainbow(getChat(str));
        } else {
            if (isWhisper(sPacketChat.getChatComponent().getUnformattedText())) {
                s = data + "\u00A7d" + (sPacketChat.getChatComponent().getUnformattedText());
            } else if ((isLogin(sPacketChat.getChatComponent().getUnformattedText()))) {
                s = data + "\u00A77" + (sPacketChat.getChatComponent().getUnformattedText());
            } else if ((isLogout(sPacketChat.getChatComponent().getUnformattedText()))) {
                s = data + "\u00A77" + (sPacketChat.getChatComponent().getUnformattedText());
            } else {
                    s = data + (sPacketChat.getChatComponent().getUnformattedText());
                }
            }

        event.cancel();
        Command.sendRawChatMessage(s);
    }, new Predicate[0]);


    public String getName(String s) {
        String fin = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            fin += c;
            if (c == '>') {
                return fin;
            }
        }
        return "\u00A7r";
    }

    public Boolean isWhisper(String s) {
        String str = "whisper";
        if (s.toLowerCase().indexOf(str.toLowerCase()) != -1) {
            return true;
         }
        return false;
    }

    public Boolean isLogin(String s) {
        String str = "joined the game";
        if (s.toLowerCase().indexOf(str.toLowerCase()) != -1) {
            return true;
        }
        return false;
    }

    public Boolean isLogout(String s) {
        String str = "left the game";
        if (s.toLowerCase().indexOf(str.toLowerCase()) != -1) {
            return true;
        }
        return false;
    }


    public String getTimestampColour() {
        if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.BLACK)) {
            return "\u00A70";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.DARKBLUE)) {
            return "\u00A71";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.DARKGREEN)) {
                return "\u00A72";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.DARKCYAN)) {
            return "\u00A73";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.DARKRED)) {
            return "\u00A74";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.PURPLE)) {
            return "\u00A75";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.GOLD)) {
            return "\u00A76";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.LIGHTGREY)) {
            return "\u00A77";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.DARKGREY)) {
            return "\u00A78";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.LILAC)) {
            return "\u00A79";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.LIGHTGREEN)) {
            return "\u00A7a";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.LIGHTCYAN)) {
            return "\u00A7b";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.LIGHTRED)) {
            return "\u00A7c";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.PINK)) {
            return "\u00A7d";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.YELLOW)) {
            return "\u00A7e";
        } else if (this.colourType.getValue().equals((Object) ChatTimeStamps.ColourType.WHITE)) {
            return "\u00A7f";
        } else return "\u00A7f";
    }

    public String getChat(String s) {
        String fin = "";
        Boolean flag = true;
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            fin += c;
            if (c == '>' && flag) {
                flag = false;
                fin = "";
            }
        } if (getName(s) == "") {
            return "\u00A7r"+fin;
        }
        return fin;
    }

    public String getMessageRainbow(String s) {
        String fin = "";
        String add = "";
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            add = getColor() + c;
            fin += add;
        }
        return fin;
    }

    public String getColor() {
        Random rand = new Random();
        int n = rand.nextInt(16);
        if (n == 0) {
            return "\u00A74";
        }else if (n == 1) {
            return "\u00A7c";
        }else if (n == 2) {
            return "\u00A76";
        }else if (n == 3) {
            return "\u00A7e";
        }else if (n == 4) {
            return "\u00A72";
        }else if (n == 5) {
            return "\u00A7a";
        }else if (n == 6) {
            return "\u00A7b";
        }else if (n == 7) {
            return "\u00A73";
        }else if (n == 8) {
            return "\u00A71";
        }else if (n == 9) {
            return "\u00A79";
        }else if (n == 10) {
            return "\u00A7d";
        }else if (n == 11) {
            return "\u00A75";
        }else if (n == 12) {
            return "\u00A7f";
        }else if (n == 13) {
            return "\u00A77";
        }else if (n == 14) {
            return "\u00A78";
        }else {
            return "\u00A70";
        }
    }

    private static enum ColourType {
        BLACK,
        DARKBLUE,
        DARKGREEN,
        DARKCYAN,
        DARKRED,
        PURPLE,
        GOLD,
        LIGHTGREY,
        DARKGREY,
        LILAC,
        LIGHTGREEN,
        LIGHTCYAN,
        LIGHTRED,
        PINK,
        YELLOW,
        WHITE;
    }

    private static enum CharacterType {
        FUCK,
        STRAIGHTBRACKETS,
        CURLYBRACKETS,
        FANCYBRACKETS,
        ARROWS,
        PLUS,
        MINUS,
        EQUALS;
    }

    */

}
