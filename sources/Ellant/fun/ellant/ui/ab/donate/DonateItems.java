/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.donate;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DonateItems {
    public static ArrayList<ItemStack> donitem = new ArrayList();

    public static void add() {
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZhYmVlZDQyNGIyNTJhODk0NWE2NDQyYjQ2MmQ1ZjMxNDcwMWE4MTZkYTJkMGE2OWNjZGZjZmQ3NDZlNTg4ZSJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0425\u0438\u043c\u0435\u0440\u044b"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgxMzYzNWJkODZiMTcxYmJlMTQzYWQ3MWUwOTAyMjkyNjQ5Y2IzYWI4NDQwZWQwMGY4NWNhNmNhMzgyOTkzNiJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u041e\u0441\u0438\u0440\u0438\u0441\u0430"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTAzN2JiYmViNjJlMTAyMGRmOWEwNmM0ZWRkNjAzMzBlNzA2MzBkMDkwZjA5NGQ4Nzc2YzJiZDEzNWRlYzIyIn19fQ==", "\u0421\u0444\u0435\u0440\u0430 \u0422\u0438\u0442\u0430\u043d\u0430"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQxMTdiNjAxOGZlZjBkNTE1NjcyMTczZTNiMjZlNjYwZDY1MWU1ODc2YmE2ZDAzZTUzNDIyNzBjNDliZWM4MCJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0410\u043f\u043e\u043b\u043b\u043e\u043d\u0430"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU1MWU2NWViNDA1Mjc3MjM4MmM5ZTUwN2E1NGJkZWQ0M2UzOWY3NTViNWRkZjU1YjNmMzk0NDNjZWQ0NjdmNCJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u041f\u0430\u043d\u0434\u043e\u0440\u044b"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRmZmUzZjM1OGYyMDliYWQ4ZmZmNGRjNDgyNDVkOWJhZjBhMDMxYjNjMWVlNmI3NTg0NjBhMzM5YjE1MTllMiJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0410\u043d\u0434\u0440\u043e\u043c\u0435\u0434\u044b"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE1YWFkZDUyYTVmYWI5NzA4ODE0NTFhZGY1NmZiYjQ5M2EzNTg1NmVhOTZmNTRlMzJlZWE2NjJkNzg3ZWQyMCJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0410\u0441\u0442\u0440\u0435\u044f"));
        ItemStack garmo = new ItemStack(Items.TOTEM_OF_UNDYING);
        garmo.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0413\u0430\u0440\u043c\u043e\u043d\u0438\u0438"));

        ItemStack grani = new ItemStack(Items.TOTEM_OF_UNDYING);
        grani.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0413\u0440\u0430\u043d\u0438"));
        ItemStack triton = new ItemStack(Items.TOTEM_OF_UNDYING);
        triton.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0422\u0440\u0438\u0442\u043e\u043d\u0430"));
        ItemStack exi = new ItemStack(Items.TOTEM_OF_UNDYING);
        exi.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0415\u0445\u0438\u0434\u043d\u044b"));
        ItemStack phoenix = new ItemStack(Items.TOTEM_OF_UNDYING);
        phoenix.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0424\u0435\u043d\u0438\u043a\u0441\u0430"));
        ItemStack dedala = new ItemStack(Items.TOTEM_OF_UNDYING);
        dedala.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0414\u0435\u0434\u0430\u043b\u0430"));
        ItemStack shulker_box = new ItemStack(Items.SHULKER_BOX);
        shulker_box.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0428\u0430\u043b\u043a\u0435\u0440\u043e\u0432\u044b\u0439 \u044f\u0449\u0438\u043a"));
        ItemStack gapple = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE);
        gapple.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0430\u0447\u0430\u0440\u043e\u0432\u0430\u043d\u043d\u043e\u0435 \u0437\u043e\u043b\u043e\u0442\u043e\u0435 \u044f\u0431\u043b\u043e\u043a\u043e"));
        ItemStack poroh = new ItemStack(Items.GUNPOWDER);
        poroh.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u043e\u0440\u043e\u0445"));
        ItemStack zombk = new ItemStack(Items.ZOMBIE_VILLAGER_SPAWN_EGG);
        zombk.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u042f\u0439\u0446\u043e \u043f\u0440\u0438\u0437\u044b\u0432\u0430 \u0437\u043e\u043c\u0431\u0438-\u043a\u0440\u0435\u0441\u0442\u044c\u044f\u043d\u0438\u043d\u0430"));
        ItemStack villager = new ItemStack(Items.VILLAGER_SPAWN_EGG);
        villager.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u042f\u0439\u0446\u043e \u043f\u0440\u0438\u0437\u044b\u0432\u0430 \u043a\u0440\u0435\u0441\u0442\u044c\u044f\u043d\u0438\u043d\u0430"));
        ItemStack elytra = new ItemStack(Items.ELYTRA);
        elytra.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u042d\u043b\u0438\u0442\u0440\u044b"));
        ItemStack netherobl = new ItemStack(Items.ANCIENT_DEBRIS);
        netherobl.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041d\u0435\u0437\u0435\u0440\u0438\u0442\u043e\u0432\u044b\u0439 \u043e\u0431\u043b\u043e\u043c\u043e\u043a"));
        ItemStack talkrush = new ItemStack(Items.TOTEM_OF_UNDYING);
        talkrush.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack talkaratel = new ItemStack(Items.TOTEM_OF_UNDYING);
        talkaratel.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0430\u0440\u0430\u0442\u0435\u043b\u044f"));
        ItemStack desorientationItem = new ItemStack(Items.ENDER_EYE);
        desorientationItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0414\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f"));
        ItemStack crusherSwordItem = new ItemStack(Items.NETHERITE_SWORD);
        crusherSwordItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041c\u0435\u0447 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack katanaItem = new ItemStack(Items.NETHERITE_SWORD);
        katanaItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041a\u0430\u0442\u0430\u043d\u0430"));
        ItemStack satansSwordItem = new ItemStack(Items.NETHERITE_SWORD);
        satansSwordItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041c\u0435\u0447 \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack plastItem = new ItemStack(Items.DRIED_KELP);
        plastItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u043b\u0430\u0441\u0442"));
        ItemStack obviousDustItem = new ItemStack(Items.SUGAR);
        obviousDustItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u042f\u0432\u043d\u0430\u044f \u043f\u044b\u043b\u044c"));
        ItemStack tridentCrusherItem = new ItemStack(Items.TRIDENT);
        tridentCrusherItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0440\u0435\u0437\u0443\u0431\u0435\u0446 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack crusherBowItem = new ItemStack(Items.BOW);
        crusherBowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041b\u0443\u043a \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack satansBowItem = new ItemStack(Items.BOW);
        satansBowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041b\u0443\u043a \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack phantomBowItem = new ItemStack(Items.BOW);
        phantomBowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041b\u0443\u043a \u0424\u0430\u043d\u0442\u043e\u043c\u0430"));
        ItemStack crossbowCrusherItem = new ItemStack(Items.CROSSBOW);
        crossbowCrusherItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0410\u0440\u0431\u0430\u043b\u0435\u0442 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack trapItem = new ItemStack(Items.NETHERITE_SCRAP);
        trapItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0440\u0430\u043f\u043a\u0430"));
        ItemStack diamond = new ItemStack(Items.DIAMOND);
        diamond.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0410\u043b\u043c\u0430\u0437"));
        ItemStack satansHelmetItem = new ItemStack(Items.NETHERITE_HELMET);
        satansHelmetItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0428\u043b\u0435\u043c \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack crusherHelmetItem = new ItemStack(Items.NETHERITE_HELMET);
        crusherHelmetItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0428\u043b\u0435\u043c \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack satansChestplateItem = new ItemStack(Items.NETHERITE_CHESTPLATE);
        satansChestplateItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack crusherChestplateItem = new ItemStack(Items.NETHERITE_CHESTPLATE);
        crusherChestplateItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack satansLeggingsItem = new ItemStack(Items.NETHERITE_LEGGINGS);
        satansLeggingsItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u043e\u043d\u043e\u0436\u0438 \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack crusherLeggingsItem = new ItemStack(Items.NETHERITE_LEGGINGS);
        crusherLeggingsItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u043e\u043d\u043e\u0436\u0438 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack satansBootsItem = new ItemStack(Items.NETHERITE_BOOTS);
        satansBootsItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0411\u043e\u0442\u0438\u043d\u043a\u0438 \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack crusherBootsItem = new ItemStack(Items.NETHERITE_BOOTS);
        crusherBootsItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0411\u043e\u0442\u0438\u043d\u043a\u0438 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack devilArrowItem = new ItemStack(Items.ARROW);
        devilArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0414\u044c\u044f\u0432\u043e\u043b\u044c\u0441\u043a\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack sharpArrowItem = new ItemStack(Items.ARROW);
        sharpArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u043e\u0447\u0435\u043d\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack paranoiaArrowItem = new ItemStack(Items.ARROW);
        paranoiaArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0421\u0442\u0440\u0435\u043b\u0430 \u043f\u0430\u0440\u0430\u043d\u043e\u0439\u0438"));
        ItemStack jesterArrowItem = new ItemStack(Items.ARROW);
        jesterArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0421\u0442\u0440\u0435\u043b\u0430 \u0414\u0436\u0430\u0441\u0442\u0435\u0440\u0430"));
        ItemStack icyArrowItem = new ItemStack(Items.ARROW);
        icyArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041b\u0435\u0434\u044f\u043d\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack poisonousArrowItem = new ItemStack(Items.ARROW);
        poisonousArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u042f\u0434\u043e\u0432\u0438\u0442\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack cursedArrowItem = new ItemStack(Items.ARROW);
        cursedArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u0440\u043e\u043a\u043b\u044f\u0442\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack potionOfStrengthItem = new ItemStack(Items.POTION);
        potionOfStrengthItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье силы"));
        ItemStack potionOfInvisibilityItem = new ItemStack(Items.POTION);
        potionOfInvisibilityItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье невидимости"));
        ItemStack potionOfSwiftnessItem = new ItemStack(Items.POTION);
        potionOfSwiftnessItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u0438"));
        ItemStack potionOfLeapingItem = new ItemStack(Items.POTION);
        potionOfLeapingItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u043f\u0440\u044b\u0433\u0443\u0447\u0435\u0441\u0442\u0438"));
        ItemStack potionOfRegenerationItem = new ItemStack(Items.POTION);
        potionOfRegenerationItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0440\u0435\u0433\u0435\u043d\u0435\u0440\u0430\u0446\u0438\u0438"));
        ItemStack nightVisionPotionItem = new ItemStack(Items.POTION);
        nightVisionPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u043d\u043e\u0447\u043d\u043e\u0433\u043e \u0437\u0440\u0435\u043d\u0438\u044f"));
        ItemStack fireResistancePotionItem = new ItemStack(Items.POTION);
        fireResistancePotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u043e\u0433\u043d\u0435\u0441\u0442\u043e\u0439\u043a\u043e\u0441\u0442\u0438"));
        ItemStack waterBreathingPotionItem = new ItemStack(Items.POTION);
        waterBreathingPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0432\u043e\u0434\u043d\u043e\u0433\u043e \u0434\u044b\u0445\u0430\u043d\u0438\u044f"));
        ItemStack flashPotionItem = new ItemStack(Items.SPLASH_POTION);
        flashPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041c\u043e\u0447\u0430 \u0444\u043b\u0435\u0448\u0430"));
        ItemStack medicPotionItem = new ItemStack(Items.SPLASH_POTION);
        medicPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u041c\u0435\u0434\u0438\u043a\u0430"));
        ItemStack agentPotionItem = new ItemStack(Items.SPLASH_POTION);
        agentPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0410\u0433\u0435\u043d\u0442\u0430"));
        ItemStack winnerPotionItem = new ItemStack(Items.SPLASH_POTION);
        winnerPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u041f\u043e\u0431\u0435\u0434\u0438\u0442\u0435\u043b\u044f"));
        ItemStack killerPotionItem = new ItemStack(Items.SPLASH_POTION);
        killerPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u041a\u0438\u043b\u043b\u0435\u0440\u0430"));
        ItemStack burpPotionItem = new ItemStack(Items.SPLASH_POTION);
        burpPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u041e\u0442\u0440\u044b\u0436\u043a\u0438"));
        ItemStack sulfuricAcidItem = new ItemStack(Items.SPLASH_POTION);
        sulfuricAcidItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0421\u0435\u0440\u043d\u0430\u044f \u043a\u0438\u0441\u043b\u043e\u0442\u0430"));
        ItemStack flashItem = new ItemStack(Items.SPLASH_POTION);
        flashItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0412\u0441\u043f\u044b\u0448\u043a\u0430"));
        ItemStack sphere = new ItemStack(Items.TRIPWIRE_HOOK);
        sphere.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041e\u0442\u043c\u044b\u0447\u043a\u0430 \u043a \u0441\u0444\u0435\u0440\u0430\u043c"));
        ItemStack armor = new ItemStack(Items.TRIPWIRE_HOOK);
        armor.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041e\u0442\u043c\u044b\u0447\u043a\u0430 \u043a \u0431\u0440\u043e\u043d\u0435"));
        ItemStack items = new ItemStack(Items.TRIPWIRE_HOOK);
        items.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к инструментам"));
        ItemStack stick = new ItemStack(Items.STICK);
        stick.setDisplayName(ITextComponent.getTextComponentOrEmpty("Палка"));
        ItemStack item = new ItemStack(Items.TRIPWIRE_HOOK);
        item.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к оружию"));
        ItemStack resourses = new ItemStack(Items.TRIPWIRE_HOOK);
        resourses.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к ресурсам"));
        ItemStack netherslit = new ItemStack(Items.NETHERITE_INGOT);
        netherslit.setDisplayName(ITextComponent.getTextComponentOrEmpty("Незеритовый слиток"));
        ItemStack expirience_botle = new ItemStack(Items.EXPERIENCE_BOTTLE);
        expirience_botle.setDisplayName(ITextComponent.getTextComponentOrEmpty("Пузырёк опыта"));
        ItemStack golden_block = new ItemStack(Items.GOLD_BLOCK);
        golden_block.setDisplayName(ITextComponent.getTextComponentOrEmpty("Золотой блок"));
        donitem.addAll(List.of(new ItemStack[]{desorientationItem, stick, crusherSwordItem, katanaItem, satansSwordItem, plastItem, obviousDustItem, tridentCrusherItem, crusherBowItem, satansBowItem, phantomBowItem, crossbowCrusherItem, trapItem, satansHelmetItem, resourses, items, item, armor, sphere, crusherHelmetItem, satansChestplateItem, crusherChestplateItem, satansLeggingsItem, crusherLeggingsItem, satansBootsItem, crusherBootsItem, devilArrowItem, sharpArrowItem, paranoiaArrowItem, jesterArrowItem, icyArrowItem, poisonousArrowItem, cursedArrowItem, potionOfStrengthItem, potionOfInvisibilityItem, potionOfSwiftnessItem, potionOfLeapingItem, potionOfRegenerationItem, nightVisionPotionItem, fireResistancePotionItem, waterBreathingPotionItem, flashPotionItem, medicPotionItem, agentPotionItem, winnerPotionItem, killerPotionItem, burpPotionItem, sulfuricAcidItem, flashItem, grani, garmo, talkaratel, dedala, triton, talkrush, phoenix, exi, shulker_box, netherslit, gapple, poroh, zombk, villager, elytra, netherobl, expirience_botle, diamond, golden_block}));
        HashMap<Enchantment, Integer> fake = new HashMap<Enchantment, Integer>();
        fake.put(Enchantments.UNBREAKING, 0);
    }

    public static ItemStack add(String texture, String name) {
        try {
            ItemStack magma = new ItemStack(Items.PLAYER_HEAD);
            magma.setTag(JsonToNBT.getTagFromJson(String.format("{SkullOwner:{Id:[I;-1949909288,1299464445,-1707774066,-249984712],Properties:{textures:[{Value:\"%s\"}]},Name:\"%s\"}}", texture, name)));
            magma.setDisplayName(new StringTextComponent(name));
            return magma;
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

