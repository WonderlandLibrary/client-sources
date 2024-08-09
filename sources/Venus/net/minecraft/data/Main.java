/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import joptsimple.AbstractOptionSpec;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpecBuilder;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.BiomeProvider;
import net.minecraft.data.BlockListReport;
import net.minecraft.data.BlockStateProvider;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.CommandsReport;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.NBTToSNBTConverter;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.RegistryDumpReport;
import net.minecraft.data.SNBTToNBTConverter;
import net.minecraft.data.StructureUpdater;

public class Main {
    public static void main(String[] stringArray) throws IOException {
        OptionParser optionParser = new OptionParser();
        AbstractOptionSpec abstractOptionSpec = optionParser.accepts("help", "Show the help menu").forHelp();
        OptionSpecBuilder optionSpecBuilder = optionParser.accepts("server", "Include server generators");
        OptionSpecBuilder optionSpecBuilder2 = optionParser.accepts("client", "Include client generators");
        OptionSpecBuilder optionSpecBuilder3 = optionParser.accepts("dev", "Include development tools");
        OptionSpecBuilder optionSpecBuilder4 = optionParser.accepts("reports", "Include data reports");
        OptionSpecBuilder optionSpecBuilder5 = optionParser.accepts("validate", "Validate inputs");
        OptionSpecBuilder optionSpecBuilder6 = optionParser.accepts("all", "Include all generators");
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec = optionParser.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated", (String[])new String[0]);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec2 = optionParser.accepts("input", "Input folder").withRequiredArg();
        OptionSet optionSet = optionParser.parse(stringArray);
        if (!optionSet.has(abstractOptionSpec) && optionSet.hasOptions()) {
            Path path = Paths.get((String)argumentAcceptingOptionSpec.value(optionSet), new String[0]);
            boolean bl = optionSet.has(optionSpecBuilder6);
            boolean bl2 = bl || optionSet.has(optionSpecBuilder2);
            boolean bl3 = bl || optionSet.has(optionSpecBuilder);
            boolean bl4 = bl || optionSet.has(optionSpecBuilder3);
            boolean bl5 = bl || optionSet.has(optionSpecBuilder4);
            boolean bl6 = bl || optionSet.has(optionSpecBuilder5);
            DataGenerator dataGenerator = Main.makeGenerator(path, optionSet.valuesOf(argumentAcceptingOptionSpec2).stream().map(Main::lambda$main$0).collect(Collectors.toList()), bl2, bl3, bl4, bl5, bl6);
            dataGenerator.run();
        } else {
            optionParser.printHelpOn(System.out);
        }
    }

    public static DataGenerator makeGenerator(Path path, Collection<Path> collection, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        DataGenerator dataGenerator = new DataGenerator(path, collection);
        if (bl || bl2) {
            dataGenerator.addProvider(new SNBTToNBTConverter(dataGenerator).addTransformer(new StructureUpdater()));
        }
        if (bl) {
            dataGenerator.addProvider(new BlockStateProvider(dataGenerator));
        }
        if (bl2) {
            dataGenerator.addProvider(new FluidTagsProvider(dataGenerator));
            BlockTagsProvider blockTagsProvider = new BlockTagsProvider(dataGenerator);
            dataGenerator.addProvider(blockTagsProvider);
            dataGenerator.addProvider(new ItemTagsProvider(dataGenerator, blockTagsProvider));
            dataGenerator.addProvider(new EntityTypeTagsProvider(dataGenerator));
            dataGenerator.addProvider(new RecipeProvider(dataGenerator));
            dataGenerator.addProvider(new AdvancementProvider(dataGenerator));
            dataGenerator.addProvider(new LootTableProvider(dataGenerator));
        }
        if (bl3) {
            dataGenerator.addProvider(new NBTToSNBTConverter(dataGenerator));
        }
        if (bl4) {
            dataGenerator.addProvider(new BlockListReport(dataGenerator));
            dataGenerator.addProvider(new RegistryDumpReport(dataGenerator));
            dataGenerator.addProvider(new CommandsReport(dataGenerator));
            dataGenerator.addProvider(new BiomeProvider(dataGenerator));
        }
        return dataGenerator;
    }

    private static Path lambda$main$0(String string) {
        return Paths.get(string, new String[0]);
    }
}

