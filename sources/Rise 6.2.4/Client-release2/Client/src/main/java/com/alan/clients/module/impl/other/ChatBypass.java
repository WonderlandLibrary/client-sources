package com.alan.clients.module.impl.other;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.lang.reflect.Field;

@ModuleInfo(aliases = {"module.other.chatbypass.name"}, description = "module.other.chatbypass.description", category = Category.PLAYER)

public class ChatBypass extends Module {
    private final char[] chars = new char[]{
            '͸', '͹', 'Ϳ', '΀', '΁', '΂', '΃', '΋', '΍', '΢', 'Ԥ', 'ԥ', 'Ԧ', 'ԧ', 'Ԩ', 'ԩ', 'Ԫ', 'ԫ', 'Ԭ', 'ԭ', 'Ԯ', 'ԯ', '԰', '՗', '՘', 'ՠ', 'ֈ', '֋', '֌', '֍', '֎', '֏', '֐', '׈', '׉', '׊', '׋', '׌', '׍', '׎', '׏', '׫', '׬', '׭', '׮', 'ׯ', '׵', '׶', '׷', '׸', '׹', '׺', '׻', '׼', '׽', '׾', '׿', '؄', '؅', '؜', '؝', 'ؠ', 'ٟ', '܎', '݋', '݌', '޲', '޳', '޴', '޵', '޶', '޷', '޸', '޹', '޺', '޻', '޼', '޽', '޾', '޿', '߻', '߼', '߽', '߾', '߿', 'ࠀ', 'ࠁ', 'ࠂ', 'ࠃ', 'ࠄ', 'ࠅ', 'ࠆ', 'ࠇ', 'ࠈ', 'ࠉ', 'ࠊ', 'ࠋ', 'ࠌ', 'ࠍ', 'ࠎ', 'ࠏ', 'ࠐ', 'ࠑ', 'ࠒ', 'ࠓ', 'ࠔ', 'ࠕ', 'ࠖ', 'ࠗ', '࠘', '࠙', 'ࠚ', 'ࠛ', 'ࠜ', 'ࠝ', 'ࠞ', 'ࠟ', 'ࠠ', 'ࠡ', 'ࠢ', 'ࠣ', 'ࠤ', 'ࠥ', 'ࠦ', 'ࠧ', 'ࠨ', 'ࠩ', 'ࠪ', 'ࠫ', 'ࠬ', '࠭', '࠮', '࠯', '࠰', '࠱', '࠲', '࠳', '࠴', '࠵', '࠶', '࠷', '࠸', '࠹', '࠺', '࠻', '࠼', '࠽', '࠾', '࠿', 'ࡀ', 'ࡁ', 'ࡂ', 'ࡃ', 'ࡄ', 'ࡅ', 'ࡆ', 'ࡇ', 'ࡈ', 'ࡉ', 'ࡊ', 'ࡋ', 'ࡌ', 'ࡍ', 'ࡎ', 'ࡏ', 'ࡐ', 'ࡑ', 'ࡒ', 'ࡓ', 'ࡔ', 'ࡕ', 'ࡖ', 'ࡗ', 'ࡘ', '࡙', '࡚', '࡛', '࡜', '࡝', '࡞', '࡟', 'ࡠ', 'ࡡ', 'ࡢ', 'ࡣ', 'ࡤ', 'ࡥ', 'ࡦ', 'ࡧ', 'ࡨ', 'ࡩ', 'ࡪ', '࡫', '࡬', '࡭', '࡮', '࡯', 'ࡰ', 'ࡱ', 'ࡲ', 'ࡳ', 'ࡴ', 'ࡵ', 'ࡶ', 'ࡷ', 'ࡸ', 'ࡹ', 'ࡺ', 'ࡻ', 'ࡼ', 'ࡽ', 'ࡾ', 'ࡿ', 'ࢀ', 'ࢁ', 'ࢂ', 'ࢃ', 'ࢄ', 'ࢅ', 'ࢆ', 'ࢇ', '࢈', 'ࢉ', 'ࢊ', 'ࢋ', 'ࢌ', 'ࢍ', 'ࢎ', '࢏', '࢐', '࢑', '࢒', '࢓', '࢔', '࢕', '࢖', 'ࢗ', '࢘', '࢙', '࢚', '࢛', '࢜', '࢝', '࢞', '࢟', 'ࢠ', 'ࢡ', 'ࢢ', 'ࢣ', 'ࢤ', 'ࢥ', 'ࢦ', 'ࢧ', 'ࢨ', 'ࢩ', 'ࢪ', 'ࢫ', 'ࢬ', 'ࢭ', 'ࢮ', 'ࢯ', 'ࢰ', 'ࢱ', 'ࢲ', 'ࢳ', 'ࢴ', 'ࢵ', 'ࢶ', 'ࢷ', 'ࢸ', 'ࢹ', 'ࢺ', 'ࢻ', 'ࢼ', 'ࢽ', 'ࢾ', 'ࢿ', 'ࣀ', 'ࣁ', 'ࣂ', 'ࣃ', 'ࣄ', 'ࣅ', 'ࣆ', 'ࣇ', 'ࣈ', 'ࣉ', '࣊', '࣋', '࣌', '࣍', '࣎', '࣏', '࣐', '࣑', '࣒', '࣓', 'ࣔ', 'ࣕ', 'ࣖ', 'ࣗ', 'ࣘ', 'ࣙ', 'ࣚ', 'ࣛ', 'ࣜ', 'ࣝ', 'ࣞ', 'ࣟ', '࣠', '࣡', '࣢', 'ࣣ', 'ࣤ', 'ࣥ', 'ࣦ', 'ࣧ', 'ࣨ', 'ࣩ', '࣪', '࣫', '࣬', '࣭', '࣮', '࣯', 'ࣰ', 'ࣱ', 'ࣲ', 'ࣳ', 'ࣴ', 'ࣵ', 'ࣶ', 'ࣷ', 'ࣸ', 'ࣹ', 'ࣺ', 'ࣻ', 'ࣼ', 'ࣽ', 'ࣾ', 'ࣿ', 'ऀ', 'ऺ', 'ऻ', 'ॎ', 'ॏ', 'ॕ', 'ॖ', 'ॗ', 'ॳ', 'ॴ', 'ॵ', 'ॶ', 'ॷ', 'ॸ', 'ॹ', 'ॺ', 'ঀ', '঄', '঍', '঎', '঑', '঒', '঩', '঱', '঳', '঴', '঵', '঺', '঻', '৅', '৆', '৉', '৊', '৏', '৐', '৑', '৒', '৓', '৔', '৕', '৖', '৘', '৙', '৚', '৛', '৞', '৤', '৥', '৻', 'ৼ', '৽', '৾', '৿', '਀', '਄', '਋', '਌', '਍', '਎', '਑', '਒', '਩', '਱', '਴', '਷', '਺', '਻', '਽', '੃', '੄', '੅', '੆', '੉', '੊', '੎', '੏'
    };

    @EventLink
    private final Listener<PacketSendEvent> PacketSend = event -> {


        if (event.getPacket() instanceof C01PacketChatMessage) {
            final C01PacketChatMessage packet = (C01PacketChatMessage) event.getPacket();
            final String message = packet.getMessage();


            if (!message.startsWith("/")) {
                final StringBuilder bypass = new StringBuilder(message.length() * 1);


                for (int i = 0; i < message.length(); i++) {
                    final char character = message.charAt(i);


                    bypass.append(character);


                    if (i % 2 == 0) {
                        char randomChar = 'ˌ';
                        bypass.append(randomChar);
                    }
                }

                try {

                    Field messageField = C01PacketChatMessage.class.getDeclaredField("message");
                    messageField.setAccessible(true);
                    messageField.set(packet, bypass.toString());

                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                event.setPacket(packet);
            }
        }

    };
}
