package wtf.shiyeno.modules.impl.player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.MultiBoxSetting;

@FunctionAnnotation(
        name = "NoInteract",
        type = Type.Player
)
public class NoInteractFunction extends Function {
    public BooleanOption allBlocks = new BooleanOption("Все блоки", false);
    public MultiBoxSetting ignoreInteract = (new MultiBoxSetting("Обьекты", new BooleanOption[]{new BooleanOption("Стойки", true), new BooleanOption("Сундуки", true), new BooleanOption("Двери", true), new BooleanOption("Кнопки", true), new BooleanOption("Воронки", true), new BooleanOption("Раздатчики", true), new BooleanOption("Нотные блоки", true), new BooleanOption("Верстаки", true), new BooleanOption("Люки", true), new BooleanOption("Печки", true), new BooleanOption("Калитки", true), new BooleanOption("Наковальни", true), new BooleanOption("Рычаги", true)})).setVisible(() -> {
        return !this.allBlocks.get();
    });

    public NoInteractFunction() {
        this.addSettings(new Setting[]{this.allBlocks, this.ignoreInteract});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
        }

    }

    public Set<Integer> getBlocks() {
        Set<Integer> blocks = new HashSet();
        this.addBlocksForInteractionType(blocks, 1, 147, 329, 270);
        this.addBlocksForInteractionType(blocks, 2, 173, 161, 485, 486, 487, 488, 489, 720, 721);
        this.addBlocksForInteractionType(blocks, 3, 183, 308, 309, 310, 311, 312, 313, 718, 719, 758);
        this.addBlocksForInteractionType(blocks, 4, 336);
        this.addBlocksForInteractionType(blocks, 5, 70, 342, 508);
        this.addBlocksForInteractionType(blocks, 6, 74);
        this.addBlocksForInteractionType(blocks, 7, 151);
        this.addBlocksForInteractionType(blocks, 8, 222, 223, 224, 225, 226, 227, 712, 713, 379);
        this.addBlocksForInteractionType(blocks, 9, 154, 670);
        this.addBlocksForInteractionType(blocks, 10, 250, 475, 476, 477, 478, 479, 714, 715);
        this.addBlocksForInteractionType(blocks, 11, 328, 327, 326);
        this.addBlocksForInteractionType(blocks, 12, 171);
        return blocks;
    }

    private void addBlocksForInteractionType(Set<Integer> blocks, int interactionType, Integer... blockIds) {
        if (this.ignoreInteract.get(interactionType)) {
            blocks.addAll(Arrays.asList(blockIds));
        }

    }
}