package xyz.mathax.mathaxclient.systems.commands.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import xyz.mathax.mathaxclient.systems.commands.Command;
import xyz.mathax.mathaxclient.systems.commands.arguments.FakePlayerArgumentType;
import xyz.mathax.mathaxclient.utils.entity.fakeplayer.FakePlayerEntity;
import xyz.mathax.mathaxclient.utils.entity.fakeplayer.FakePlayerManager;
import xyz.mathax.mathaxclient.utils.entity.fakeplayer.FakePlayerSettings;
import xyz.mathax.mathaxclient.utils.text.ChatUtils;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class FakePlayerCommand extends Command {
    public FakePlayerCommand() {
        super("Fake Player", "Manages fake players that you can use for testing.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("add").executes(context -> {
                    FakePlayerManager.add(FakePlayerSettings.nameSetting.get(), FakePlayerSettings.healthSetting.get(), FakePlayerSettings.copyInventorySetting.get());

                    return SINGLE_SUCCESS;
                }).then(argument("name", StringArgumentType.word()).executes(context -> {
                            FakePlayerManager.add(StringArgumentType.getString(context, "name"), FakePlayerSettings.healthSetting.get(), FakePlayerSettings.copyInventorySetting.get());

                            return SINGLE_SUCCESS;
                        })
                )
        );

        builder.then(literal("remove").then(argument("fakeplayer", FakePlayerArgumentType.create()).executes(context -> {
            FakePlayerEntity fakePlayerEntity = FakePlayerArgumentType.get(context);
            if (fakePlayerEntity == null || !FakePlayerManager.contains(fakePlayerEntity)) {
                error("Couldn't find a (highlight)Fake Player(default) with that name.");

                return SINGLE_SUCCESS;
            }

            FakePlayerManager.remove(fakePlayerEntity);

            info("Removed Fake Player %s.".formatted(fakePlayerEntity.getEntityName()));

            return SINGLE_SUCCESS;
        })));

        builder.then(literal("clear").executes(context -> {
            FakePlayerManager.clear();
            return SINGLE_SUCCESS;
        }));

        builder.then(literal("list").executes(context -> {
            info("--- Fake Players ((highlight)%s(default)) ---", FakePlayerManager.count());
            FakePlayerManager.forEach(fakePlayer -> ChatUtils.info("(highlight)%s".formatted(fakePlayer.getEntityName())));
            return SINGLE_SUCCESS;
        }));
    }
}