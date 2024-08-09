/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.registry;

import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.EntityOptions;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.server.DebugLoggingPrintStream;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.LoggingPrintStream;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bootstrap {
    public static final PrintStream SYSOUT = System.out;
    private static boolean alreadyRegistered;
    private static final Logger LOGGER;

    public static void register() {
        if (!alreadyRegistered) {
            alreadyRegistered = true;
            if (Registry.REGISTRY.keySet().isEmpty()) {
                throw new IllegalStateException("Unable to load registries");
            }
            FireBlock.init();
            ComposterBlock.init();
            if (EntityType.getKey(EntityType.PLAYER) == null) {
                throw new IllegalStateException("Failed loading EntityTypes");
            }
            PotionBrewing.init();
            EntityOptions.registerOptions();
            IDispenseItemBehavior.init();
            ArgumentTypes.registerArgumentTypes();
            TagRegistryManager.checkHelperRegistrations();
            Bootstrap.redirectOutputToLog();
        }
    }

    private static <T> void addTranslationStrings(Iterable<T> iterable, Function<T, String> function, Set<String> set) {
        LanguageMap languageMap = LanguageMap.getInstance();
        iterable.forEach(arg_0 -> Bootstrap.lambda$addTranslationStrings$0(function, languageMap, set, arg_0));
    }

    private static void addGameRuleTranslationStrings(Set<String> set) {
        LanguageMap languageMap = LanguageMap.getInstance();
        GameRules.visitAll(new GameRules.IRuleEntryVisitor(){
            final LanguageMap val$languagemap;
            final Set val$translations;
            {
                this.val$languagemap = languageMap;
                this.val$translations = set;
            }

            @Override
            public <T extends GameRules.RuleValue<T>> void visit(GameRules.RuleKey<T> ruleKey, GameRules.RuleType<T> ruleType) {
                if (!this.val$languagemap.func_230506_b_(ruleKey.getLocaleString())) {
                    this.val$translations.add(ruleKey.getName());
                }
            }
        });
    }

    public static Set<String> getTranslationStrings() {
        TreeSet<String> treeSet = new TreeSet<String>();
        Bootstrap.addTranslationStrings(Registry.ATTRIBUTE, Attribute::getAttributeName, treeSet);
        Bootstrap.addTranslationStrings(Registry.ENTITY_TYPE, EntityType::getTranslationKey, treeSet);
        Bootstrap.addTranslationStrings(Registry.EFFECTS, Effect::getName, treeSet);
        Bootstrap.addTranslationStrings(Registry.ITEM, Item::getTranslationKey, treeSet);
        Bootstrap.addTranslationStrings(Registry.ENCHANTMENT, Enchantment::getName, treeSet);
        Bootstrap.addTranslationStrings(Registry.BLOCK, Block::getTranslationKey, treeSet);
        Bootstrap.addTranslationStrings(Registry.CUSTOM_STAT, Bootstrap::lambda$getTranslationStrings$1, treeSet);
        Bootstrap.addGameRuleTranslationStrings(treeSet);
        return treeSet;
    }

    public static void checkTranslations() {
        if (!alreadyRegistered) {
            throw new IllegalArgumentException("Not bootstrapped");
        }
        if (SharedConstants.developmentMode) {
            Bootstrap.getTranslationStrings().forEach(Bootstrap::lambda$checkTranslations$2);
            Commands.func_242986_b();
        }
        GlobalEntityTypeAttributes.validateEntityAttributes();
    }

    private static void redirectOutputToLog() {
        if (LOGGER.isDebugEnabled()) {
            System.setErr(new DebugLoggingPrintStream("STDERR", System.err));
            System.setOut(new DebugLoggingPrintStream("STDOUT", SYSOUT));
        } else {
            System.setErr(new LoggingPrintStream("STDERR", System.err));
            System.setOut(new LoggingPrintStream("STDOUT", SYSOUT));
        }
    }

    public static void printToSYSOUT(String string) {
        SYSOUT.println(string);
    }

    private static void lambda$checkTranslations$2(String string) {
        LOGGER.error("Missing translations: " + string);
    }

    private static String lambda$getTranslationStrings$1(ResourceLocation resourceLocation) {
        return "stat." + resourceLocation.toString().replace(':', '.');
    }

    private static void lambda$addTranslationStrings$0(Function function, LanguageMap languageMap, Set set, Object object) {
        String string = (String)function.apply(object);
        if (!languageMap.func_230506_b_(string)) {
            set.add(string);
        }
    }

    static {
        LOGGER = LogManager.getLogger();
    }
}

