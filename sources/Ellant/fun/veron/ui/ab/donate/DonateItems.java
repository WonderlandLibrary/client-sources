/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.veron.ui.ab.donate;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DonateItems {
    public static ArrayList<ItemStack> donitem = new ArrayList();

    public void registerSphereItems() {
    }

    public static void add() {
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZhYmVlZDQyNGIyNTJhODk0NWE2NDQyYjQ2MmQ1ZjMxNDcwMWE4MTZkYTJkMGE2OWNjZGZjZmQ3NDZlNTg4ZSJ9fX0=", "Сфера Химеры"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgxMzYzNWJkODZiMTcxYmJlMTQzYWQ3MWUwOTAyMjkyNjQ5Y2IzYWI4NDQwZWQwMGY4NWNhNmNhMzgyOTkzNiJ9fX0=", "Сфера Осириса"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTAzN2JiYmViNjJlMTAyMGRmOWEwNmM0ZWRkNjAzMzBlNzA2MzBkMDkwZjA5NGQ4Nzc2YzJiZDEzNWRlYzIyIn19fQ==", "Сфера Титана"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQxMTdiNjAxOGZlZjBkNTE1NjcyMTczZTNiMjZlNjYwZDY1MWU1ODc2YmE2ZDAzZTUzNDIyNzBjNDliZWM4MCJ9fX0=", "Сфера Аполлона"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU1MWU2NWViNDA1Mjc3MjM4MmM5ZTUwN2E1NGJkZWQ0M2UzOWY3NTViNWRkZjU1YjNmMzk0NDNjZWQ0NjdmNCJ9fX0=", "Сфера Пандоры"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRmZmUzZjM1OGYyMDliYWQ4ZmZmNGRjNDgyNDVkOWJhZjBhMDMxYjNjMWVlNmI3NTg0NjBhMzM5YjE1MTllMiJ9fX0=", "Сфера Андромеды"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE1YWFkZDUyYTVmYWI5NzA4ODE0NTFhZGY1NmZiYjQ5M2EzNTg1NmVhOTZmNTRlMzJlZWE2NjJkNzg3ZWQyMCJ9fX0=", "Сфера Астрея"));
        ItemStack grani = new ItemStack(Items.TOTEM_OF_UNDYING);
        grani.setDisplayName(ITextComponent.getTextComponentOrEmpty("Талисман Грани"));

        ItemStack garmo = new ItemStack(Items.TOTEM_OF_UNDYING);
        garmo.setDisplayName(ITextComponent.getTextComponentOrEmpty("Талисман гармонии"));

        ItemStack triton = new ItemStack(Items.TOTEM_OF_UNDYING);
        triton.setDisplayName(ITextComponent.getTextComponentOrEmpty("Талисман Тритона"));

        ItemStack exi = new ItemStack(Items.TOTEM_OF_UNDYING);
        exi.setDisplayName(ITextComponent.getTextComponentOrEmpty("Талисман Ехидны"));

        ItemStack phoenix = new ItemStack(Items.TOTEM_OF_UNDYING);
        phoenix.setDisplayName(ITextComponent.getTextComponentOrEmpty("Талисман Феникса"));

        ItemStack dedala = new ItemStack(Items.TOTEM_OF_UNDYING);
        dedala.setDisplayName(ITextComponent.getTextComponentOrEmpty("Талисман Дедала"));

        ItemStack talkrush = new ItemStack(Items.TOTEM_OF_UNDYING);
        talkrush.setDisplayName(ITextComponent.getTextComponentOrEmpty("Талисман Крушителя"));

        ItemStack talkaratel = new ItemStack(Items.TOTEM_OF_UNDYING);
        talkaratel.setDisplayName(ITextComponent.getTextComponentOrEmpty("Талисман Карателя"));

        ItemStack desorientationItem = new ItemStack(Items.ENDER_EYE);
        desorientationItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Дезориентация"));

        ItemStack crusherSwordItem = new ItemStack(Items.NETHERITE_SWORD);
        crusherSwordItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Меч Крушителя"));

        ItemStack katanaItem = new ItemStack(Items.NETHERITE_SWORD);
        katanaItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Катана"));

        ItemStack satansSwordItem = new ItemStack(Items.NETHERITE_SWORD);
        satansSwordItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Меч Сатаны"));

        ItemStack plastItem = new ItemStack(Items.DRIED_KELP);
        plastItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Пласт"));

        ItemStack obviousDustItem = new ItemStack(Items.SUGAR);
        obviousDustItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Явная пыль"));

        ItemStack tridentCrusherItem = new ItemStack(Items.TRIDENT);
        tridentCrusherItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Трезубец Крушителя"));

        ItemStack crusherBowItem = new ItemStack(Items.BOW);
        crusherBowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Лук Крушителя"));

        ItemStack satansBowItem = new ItemStack(Items.BOW);
        satansBowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Лук Сатаны"));

        ItemStack phantomBowItem = new ItemStack(Items.BOW);
        phantomBowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Лук Фантома"));

        ItemStack crossbowCrusherItem = new ItemStack(Items.CROSSBOW);
        crossbowCrusherItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Арбалет Крушителя"));

        ItemStack trapItem = new ItemStack(Items.NETHERITE_SCRAP);
        trapItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Трапка"));

        ItemStack satansHelmetItem = new ItemStack(Items.NETHERITE_HELMET);
        satansHelmetItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Шлем Сатаны"));

        ItemStack crusherHelmetItem = new ItemStack(Items.NETHERITE_HELMET);
        crusherHelmetItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Шлем Крушителя"));

        ItemStack satansChestplateItem = new ItemStack(Items.NETHERITE_CHESTPLATE);
        satansChestplateItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Нагрудник Сатаны"));

        ItemStack crusherChestplateItem = new ItemStack(Items.NETHERITE_CHESTPLATE);
        crusherChestplateItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Нагрудник Крушителя"));

        ItemStack satansLeggingsItem = new ItemStack(Items.NETHERITE_LEGGINGS);
        satansLeggingsItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Поножи Сатаны"));

        ItemStack crusherLeggingsItem = new ItemStack(Items.NETHERITE_LEGGINGS);
        crusherLeggingsItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Поножи Крушителя"));

        ItemStack satansBootsItem = new ItemStack(Items.NETHERITE_BOOTS);
        satansBootsItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Ботинки Сатаны"));

        ItemStack crusherBootsItem = new ItemStack(Items.NETHERITE_BOOTS);
        crusherBootsItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Ботинки Крушителя"));

        ItemStack devilArrowItem = new ItemStack(Items.ARROW);
        devilArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Дьявольская стрела"));

        ItemStack sharpArrowItem = new ItemStack(Items.ARROW);
        sharpArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Точеная стрела"));

        ItemStack paranoiaArrowItem = new ItemStack(Items.ARROW);
        paranoiaArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Стрела паранойи"));

        ItemStack jesterArrowItem = new ItemStack(Items.ARROW);
        jesterArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Стрела Джастера"));

        ItemStack icyArrowItem = new ItemStack(Items.ARROW);
        icyArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Ледяная стрела"));

        ItemStack poisonousArrowItem = new ItemStack(Items.ARROW);
        poisonousArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Ядовитая стрела"));

        ItemStack cursedArrowItem = new ItemStack(Items.ARROW);
        cursedArrowItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Проклятая стрела"));

        ItemStack potionOfStrengthItem = new ItemStack(Items.POTION);
        potionOfStrengthItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье силы"));

        ItemStack potionOfInvisibilityItem = new ItemStack(Items.POTION);
        potionOfInvisibilityItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье невидимости"));

        ItemStack potionOfSwiftnessItem = new ItemStack(Items.POTION);
        potionOfSwiftnessItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье скорости"));

        ItemStack potionOfLeapingItem = new ItemStack(Items.POTION);
        potionOfLeapingItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье прыгучести"));

        ItemStack potionOfRegenerationItem = new ItemStack(Items.POTION);
        potionOfRegenerationItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье регенерации"));

        ItemStack nightVisionPotionItem = new ItemStack(Items.POTION);
        nightVisionPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье ночного зрения"));

        ItemStack fireResistancePotionItem = new ItemStack(Items.POTION);
        fireResistancePotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье огнестойкости"));

        ItemStack waterBreathingPotionItem = new ItemStack(Items.POTION);
        waterBreathingPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье водного дыхания"));

        ItemStack flashPotionItem = new ItemStack(Items.SPLASH_POTION);
        flashPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Моча Флеша"));

        ItemStack medicPotionItem = new ItemStack(Items.SPLASH_POTION);
        medicPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье медика"));

        ItemStack agentPotionItem = new ItemStack(Items.SPLASH_POTION);
        agentPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье агента"));

        ItemStack winnerPotionItem = new ItemStack(Items.SPLASH_POTION);
        winnerPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье победителя"));

        ItemStack killerPotionItem = new ItemStack(Items.SPLASH_POTION);
        killerPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье киллера"));

        ItemStack burpPotionItem = new ItemStack(Items.SPLASH_POTION);
        burpPotionItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Зелье отрыжки"));

        ItemStack sulfuricAcidItem = new ItemStack(Items.SPLASH_POTION);
        sulfuricAcidItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Серная кислота"));

        ItemStack flashItem = new ItemStack(Items.SPLASH_POTION);
        flashItem.setDisplayName(ITextComponent.getTextComponentOrEmpty("Вспышка"));

        ItemStack sphere = new ItemStack(Items.TRIPWIRE_HOOK);
        sphere.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к сферам"));

        ItemStack armor = new ItemStack(Items.TRIPWIRE_HOOK);
        armor.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к броне"));
        ItemStack stick = new ItemStack(Items.STICK);
        stick.setDisplayName(ITextComponent.getTextComponentOrEmpty("Палка"));
        ItemStack items = new ItemStack(Items.TRIPWIRE_HOOK);
        items.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к предметам"));

        items.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к инструментам"));
        ItemStack item = new ItemStack(Items.TRIPWIRE_HOOK);

        item.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к оружию"));
        ItemStack resourses = new ItemStack(Items.TRIPWIRE_HOOK);

        resourses.setDisplayName(ITextComponent.getTextComponentOrEmpty("Отмычка к ресурсам"));


        donitem.addAll(List.of((new ItemStack[]{desorientationItem, crusherSwordItem, katanaItem, satansSwordItem, plastItem, obviousDustItem, tridentCrusherItem, crusherBowItem, satansBowItem, phantomBowItem, crossbowCrusherItem, trapItem, satansHelmetItem, resourses, items, item, armor, sphere, crusherHelmetItem, satansChestplateItem, crusherChestplateItem, satansLeggingsItem, crusherLeggingsItem, satansBootsItem, crusherBootsItem, devilArrowItem, sharpArrowItem, paranoiaArrowItem, jesterArrowItem, icyArrowItem, poisonousArrowItem, cursedArrowItem, potionOfStrengthItem, potionOfInvisibilityItem, potionOfSwiftnessItem, potionOfLeapingItem, potionOfRegenerationItem, nightVisionPotionItem, fireResistancePotionItem, waterBreathingPotionItem, flashPotionItem, medicPotionItem, agentPotionItem, winnerPotionItem, killerPotionItem, burpPotionItem, sulfuricAcidItem, flashItem, grani, garmo, talkaratel, dedala, triton, talkrush, phoenix, stick, exi})));
        HashMap<Enchantment, Integer> fake = new HashMap<Enchantment, Integer>();
        fake.put(Enchantments.UNBREAKING, 0);
        for (ItemStack s : donitem) {
            EnchantmentHelper.setEnchantments(fake, s);
        }
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

