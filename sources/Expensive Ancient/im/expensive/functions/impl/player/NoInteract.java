package im.expensive.functions.impl.player;

import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@FunctionRegister(name = "NoInteract", type = Category.Player)
public class NoInteract extends Function {

    public BooleanSetting allBlocks = new BooleanSetting("Все блоки", false);
    public ModeListSetting ignoreInteract = new ModeListSetting("Обьекты",
            new BooleanSetting("Стойки", true),
            new BooleanSetting("Сундуки", true),
            new BooleanSetting("Двери", true),
            new BooleanSetting("Кнопки", true),
            new BooleanSetting("Воронки", true),
            new BooleanSetting("Раздатчики", true),
            new BooleanSetting("Нотные блоки", true),
            new BooleanSetting("Верстаки", true),
            new BooleanSetting("Люки", true),
            new BooleanSetting("Печки", true),
            new BooleanSetting("Калитки", true),
            new BooleanSetting("Наковальни", true),
            new BooleanSetting("Рычаги", true)).setVisible(() -> !allBlocks.get());

    public NoInteract() {
        addSettings(ignoreInteract, allBlocks);
    }
    public Set<Integer> getBlocks() {
        Set<Integer> blocks = new HashSet<>();
        addBlocksForInteractionType(blocks, 1, 147, 329, 270);
        addBlocksForInteractionType(blocks, 2, 173, 161, 485, 486, 487, 488, 489, 720, 721);
        addBlocksForInteractionType(blocks, 3, 183, 308, 309, 310, 311, 312, 313, 718, 719, 758);
        addBlocksForInteractionType(blocks, 4, 336);
        addBlocksForInteractionType(blocks, 5, 70, 342, 508);
        addBlocksForInteractionType(blocks, 6, 74);
        addBlocksForInteractionType(blocks, 7, 151);
        addBlocksForInteractionType(blocks, 8, 222, 223, 224, 225, 226, 227, 712, 713, 379);
        addBlocksForInteractionType(blocks, 9, 154, 670);
        addBlocksForInteractionType(blocks, 10, 250, 475, 476, 477, 478, 479, 714, 715);
        addBlocksForInteractionType(blocks, 11, 328, 327, 326);
        addBlocksForInteractionType(blocks, 12, 171);
        return blocks;
    }

    private void addBlocksForInteractionType(Set<Integer> blocks, int interactionType, Integer... blockIds) {
        if (ignoreInteract.get(interactionType).get()) {
            blocks.addAll(Arrays.asList(blockIds));
        }
    }
}
