package dev.darkmoon.client.event.misc;

import com.darkmagician6.eventapi.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.block.BlockObsidian;
import net.minecraft.util.math.BlockPos;

@Getter
@AllArgsConstructor
public class EventObsidianPlaced implements Event {
    private final BlockObsidian block;
    private final BlockPos pos;
}
