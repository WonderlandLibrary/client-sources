package dev.excellent.api.event.impl.other;

import com.mojang.datafixers.util.Pair;
import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.scoreboard.Score;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScoreBoardEvent extends Event {
    List<Pair<Score, ITextComponent>> list;
}
