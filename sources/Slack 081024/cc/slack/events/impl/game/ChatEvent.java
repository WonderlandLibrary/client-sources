package cc.slack.events.impl.game;

import cc.slack.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.PacketDirection;

@Getter
@Setter
@AllArgsConstructor
public class ChatEvent extends Event {
    private String message;
    private final PacketDirection direction;
}