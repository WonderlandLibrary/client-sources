// package me.travis.wurstplus.module.modules.chat;

// import java.util.function.Predicate;

// import me.travis.wurstplus.event.events.PacketEvent;
// import me.travis.wurstplus.module.Module;
// import me.travis.wurstplus.setting.Setting;
// import me.travis.wurstplus.setting.Settings;
// import me.zero.alpine.listener.EventHandler;
// import me.zero.alpine.listener.Listener;
// import net.minecraft.network.play.server.SPacketChat;

// @Module.Info(name = "ChatBot", category = Module.Category.CHAT)
// public class ChatBot extends Module {

//     private Setting<String> receiveChat = this.register(Settings.s("Listen For:", null));
//     private Setting<String> sendChat = this.register(Settings.s("Send:", null));

//     @EventHandler
//     public Listener<PacketEvent.Receive> listener = new Listener<PacketEvent.Receive>(event -> {
//         if (mc.player == null || this.isDisabled()) {
//             return;
//         }
//         if (!(event.getPacket() instanceof SPacketChat)) {
//             return;
//         }
//         SPacketChat sPacketChat = (SPacketChat) event.getPacket();

//         String s = "";
//         String str = sPacketChat.getChatComponent().getUnformattedText();
//     }, new Predicate[0]);

//     public Boolean listenForMsg(String s) {
//         String str = this.receiveChat.getValue();
//         if (s.toLowerCase().indexOf(str.toLowerCase()) != -1) {
//             return true;
//         }
//         return false;
//     }
// }
