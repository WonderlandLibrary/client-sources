/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.impl.LocateCommand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biome;

public class LocateBiomeCommand {
    public static final DynamicCommandExceptionType field_241044_a_ = new DynamicCommandExceptionType(LocateBiomeCommand::lambda$static$0);
    private static final DynamicCommandExceptionType field_241045_b_ = new DynamicCommandExceptionType(LocateBiomeCommand::lambda$static$1);

    public static void register(CommandDispatcher<CommandSource> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("locatebiome").requires(LocateBiomeCommand::lambda$register$2)).then(Commands.argument("biome", ResourceLocationArgument.resourceLocation()).suggests(SuggestionProviders.field_239574_d_).executes(LocateBiomeCommand::lambda$register$3)));
    }

    private static int func_241049_a_(CommandSource commandSource, ResourceLocation resourceLocation) throws CommandSyntaxException {
        Biome biome = (Biome)commandSource.getServer().func_244267_aX().getRegistry(Registry.BIOME_KEY).getOptional(resourceLocation).orElseThrow(() -> LocateBiomeCommand.lambda$func_241049_a_$4(resourceLocation));
        BlockPos blockPos = new BlockPos(commandSource.getPos());
        BlockPos blockPos2 = commandSource.getWorld().func_241116_a_(biome, blockPos, 6400, 8);
        String string = resourceLocation.toString();
        if (blockPos2 == null) {
            throw field_241045_b_.create(string);
        }
        return LocateCommand.func_241054_a_(commandSource, string, blockPos, blockPos2, "commands.locatebiome.success");
    }

    private static CommandSyntaxException lambda$func_241049_a_$4(ResourceLocation resourceLocation) {
        return field_241044_a_.create(resourceLocation);
    }

    private static int lambda$register$3(CommandContext commandContext) throws CommandSyntaxException {
        return LocateBiomeCommand.func_241049_a_((CommandSource)commandContext.getSource(), commandContext.getArgument("biome", ResourceLocation.class));
    }

    private static boolean lambda$register$2(CommandSource commandSource) {
        return commandSource.hasPermissionLevel(1);
    }

    private static Message lambda$static$1(Object object) {
        return new TranslationTextComponent("commands.locatebiome.notFound", object);
    }

    private static Message lambda$static$0(Object object) {
        return new TranslationTextComponent("commands.locatebiome.invalid", object);
    }
}

