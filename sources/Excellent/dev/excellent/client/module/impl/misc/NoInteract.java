package dev.excellent.client.module.impl.misc;

import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import dev.excellent.impl.value.mode.SubMode;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@ModuleInfo(name = "No Interact", description = "Убирает взаимодействие с блоками", category = Category.MISC)
public class NoInteract extends Module {
    @Getter
    private static final Singleton<NoInteract> singleton = Singleton.create(() -> Module.link(NoInteract.class));
    public ModeValue mode = new ModeValue("Игнорировать", this) {{
        add(SubMode.of("Все Блоки", "Выбранные"));
    }};

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    public MultiBooleanValue ignoreInteract = new MultiBooleanValue("Обьекты", this, () -> !mode.is("Выбранные")) {{
        add(
                new BooleanValue("Сундуки", true),
                new BooleanValue("Двери", true),
                new BooleanValue("Кнопки", true),
                new BooleanValue("Воронки", true),
                new BooleanValue("Раздатчики", true),
                new BooleanValue("Нотные блоки", true),
                new BooleanValue("Верстаки", true),
                new BooleanValue("Люки", true),
                new BooleanValue("Печки", true),
                new BooleanValue("Калитки", true),
                new BooleanValue("Наковальни", true),
                new BooleanValue("Рычаги", true)
        );
    }};

    public Set<Integer> getBlocks() {
        Set<Integer> blocks = new HashSet<>();
        addBlocksForInteractionType(blocks, 0, 147, 329, 270);
        addBlocksForInteractionType(blocks, 1, 173, 161, 485, 486, 487, 488, 489, 720, 721);
        addBlocksForInteractionType(blocks, 2, 183, 308, 309, 310, 311, 312, 313, 718, 719, 758);
        addBlocksForInteractionType(blocks, 3, 336);
        addBlocksForInteractionType(blocks, 4, 70, 342, 508);
        addBlocksForInteractionType(blocks, 5, 74);
        addBlocksForInteractionType(blocks, 6, 151);
        addBlocksForInteractionType(blocks, 7, 222, 223, 224, 225, 226, 227, 712, 713, 379);
        addBlocksForInteractionType(blocks, 8, 154, 670);
        addBlocksForInteractionType(blocks, 9, 250, 475, 476, 477, 478, 479, 714, 715);
        addBlocksForInteractionType(blocks, 10, 328, 327, 326);
        addBlocksForInteractionType(blocks, 11, 171);
        return blocks;
    }

    private void addBlocksForInteractionType(Set<Integer> blocks, int interactionType, Integer... blockIds) {
        if (ignoreInteract.getValues().stream().toList().get(interactionType).getValue()) {
            blocks.addAll(Arrays.asList(blockIds));
        }
    }
}
