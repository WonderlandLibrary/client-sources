// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.other.ServerUtils;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.network.play.client.C01PacketChatMessage;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "ChatBypass", description = "Bypasses some chats with filter", cat = Category.MISC)
public class ChatBypass extends Module
{
    private final EnumProperty<Mode> mode;
    private final char[] chars;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public ChatBypass() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Normal);
        this.chars = new char[] { '\u0378', '\u0379', '\u037f', '\u0380', '\u0381', '\u0382', '\u0383', '\u038b', '\u038d', '\u03a2', '\u0524', '\u0525', '\u0526', '\u0527', '\u0528', '\u0529', '\u052a', '\u052b', '\u052c', '\u052d', '\u052e', '\u052f', '\u0530', '\u0557', '\u0558', '\u0560', '\u0588', '\u058b', '\u058c', '\u058d', '\u058e', '\u058f', '\u0590', '\u05c8', '\u05c9', '\u05ca', '\u05cb', '\u05cc', '\u05cd', '\u05ce', '\u05cf', '\u05eb', '\u05ec', '\u05ed', '\u05ee', '\u05ef', '\u05f5', '\u05f6', '\u05f7', '\u05f8', '\u05f9', '\u05fa', '\u05fb', '\u05fc', '\u05fd', '\u05fe', '\u05ff', '\u0604', '\u0605', '\u061c', '\u061d', '\u0620', '\u065f', '\u070e', '\u074b', '\u074c', '\u07b2', '\u07b3', '\u07b4', '\u07b5', '\u07b6', '\u07b7', '\u07b8', '\u07b9', '\u07ba', '\u07bb', '\u07bc', '\u07bd', '\u07be', '\u07bf', '\u07fb', '\u07fc', '\u07fd', '\u07fe', '\u07ff', '\u0800', '\u0801', '\u0802', '\u0803', '\u0804', '\u0805', '\u0806', '\u0807', '\u0808', '\u0809', '\u080a', '\u080b', '\u080c', '\u080d', '\u080e', '\u080f', '\u0810', '\u0811', '\u0812', '\u0813', '\u0814', '\u0815', '\u0816', '\u0817', '\u0818', '\u0819', '\u081a', '\u081b', '\u081c', '\u081d', '\u081e', '\u081f', '\u0820', '\u0821', '\u0822', '\u0823', '\u0824', '\u0825', '\u0826', '\u0827', '\u0828', '\u0829', '\u082a', '\u082b', '\u082c', '\u082d', '\u082e', '\u082f', '\u0830', '\u0831', '\u0832', '\u0833', '\u0834', '\u0835', '\u0836', '\u0837', '\u0838', '\u0839', '\u083a', '\u083b', '\u083c', '\u083d', '\u083e', '\u083f', '\u0840', '\u0841', '\u0842', '\u0843', '\u0844', '\u0845', '\u0846', '\u0847', '\u0848', '\u0849', '\u084a', '\u084b', '\u084c', '\u084d', '\u084e', '\u084f', '\u0851', '\u0852', '\u0853', '\u0854', '\u0855', '\u0856', '\u0857', '\u0858', '\u0859', '\u085a', '\u085b', '\u085c', '\u085d', '\u085e', '\u085f', '\u0860', '\u0861', '\u0862', '\u0863', '\u0864', '\u0865', '\u0866', '\u0867', '\u0868', '\u0869', '\u086a', '\u086b', '\u086c', '\u086d', '\u086e', '\u086f', '\u0870', '\u0871', '\u0872', '\u0873', '\u0874', '\u0875', '\u0876', '\u0877', '\u0878', '\u0879', '\u087a', '\u087b', '\u087c', '\u087d', '\u087e', '\u087f', '\u0880', '\u0881', '\u0882', '\u0883', '\u0884', '\u0885', '\u0886', '\u0887', '\u0888', '\u0889', '\u088a', '\u088b', '\u088c', '\u088d', '\u088e', '\u088f', '\u0890', '\u0891', '\u0892', '\u0893', '\u0894', '\u0895', '\u0896', '\u0897', '\u0898', '\u0899', '\u089a', '\u089b', '\u089c', '\u089d', '\u089e', '\u089f', '\u08a0', '\u08a1', '\u08a2', '\u08a3', '\u08a4', '\u08a5', '\u08a6', '\u08a7', '\u08a8', '\u08a9', '\u08aa', '\u08ab', '\u08ac', '\u08ad', '\u08ae', '\u08af', '\u08b0', '\u08b1', '\u08b2', '\u08b3', '\u08b4', '\u08b5', '\u08b6', '\u08b7', '\u08b8', '\u08b9', '\u08ba', '\u08bb', '\u08bc', '\u08bd', '\u08be', '\u08bf', '\u08c0', '\u08c1', '\u08c2', '\u08c3', '\u08c4', '\u08c5', '\u08c6', '\u08c7', '\u08c8', '\u08c9', '\u08ca', '\u08cb', '\u08cc', '\u08cd', '\u08ce', '\u08cf', '\u08d0', '\u08d1', '\u08d2', '\u08d3', '\u08d4', '\u08d5', '\u08d6', '\u08d7', '\u08d8', '\u08d9', '\u08da', '\u08db', '\u08dc', '\u08dd', '\u08de', '\u08df', '\u08e0', '\u08e1', '\u08e2', '\u08e3', '\u08e4', '\u08e5', '\u08e6', '\u08e7', '\u08e8', '\u08e9', '\u08ea', '\u08eb', '\u08ec', '\u08ed', '\u08ee', '\u08ef', '\u08f0', '\u08f1', '\u08f2', '\u08f3', '\u08f4', '\u08f5', '\u08f6', '\u08f7', '\u08f8', '\u08f9', '\u08fa', '\u08fb', '\u08fc', '\u08fd', '\u08fe', '\u08ff', '\u0900', '\u093a', '\u093b', '\u094e', '\u094f', '\u0955', '\u0956', '\u0957', '\u0973', '\u0974', '\u0975', '\u0976', '\u0977', '\u0978', '\u0979', '\u097a', '\u0980', '\u0984', '\u098d', '\u098e', '\u0991', '\u0992', '\u09a9', '\u09b1', '\u09b3', '\u09b4', '\u09b5', '\u09ba', '\u09bb', '\u09c5', '\u09c6', '\u09c9', '\u09ca', '\u09cf', '\u09d0', '\u09d1', '\u09d2', '\u09d3', '\u09d4', '\u09d5', '\u09d6', '\u09d8', '\u09d9', '\u09da', '\u09db', '\u09de', '\u09e4', '\u09e5', '\u09fb', '\u09fc', '\u09fd', '\u09fe', '\u09ff', '\u0a00', '\u0a04', '\u0a0b', '\u0a0c', '\u0a0d', '\u0a0e', '\u0a11', '\u0a12', '\u0a29', '\u0a31', '\u0a34', '\u0a37', '\u0a3a', '\u0a3b', '\u0a3d', '\u0a43', '\u0a44', '\u0a45', '\u0a46', '\u0a49', '\u0a4a', '\u0a4e', '\u0a4f' };
        C01PacketChatMessage c01;
        String message;
        StringBuilder bypass;
        int i;
        char character;
        char randomChar;
        this.packetEventListener = (e -> {
            if (e.getPacket() instanceof C01PacketChatMessage) {
                c01 = (C01PacketChatMessage)e.getPacket();
                message = c01.getMessage();
                if (!message.startsWith("/")) {
                    bypass = new StringBuilder(message.length() * 2);
                    for (i = 0; i < message.length(); ++i) {
                        character = message.charAt(i);
                        randomChar = this.chars[ThreadLocalRandom.current().nextInt(0, this.chars.length)];
                        if (ServerUtils.isOnHypixel() && this.mode.getValue() == Mode.Watchdog) {
                            randomChar = '\u02cc';
                        }
                        bypass.append(character).append(randomChar);
                    }
                    c01.setMessage(bypass.toString());
                    e.setPacket(c01);
                }
            }
        });
    }
    
    public enum Mode
    {
        Normal, 
        Watchdog;
    }
}
