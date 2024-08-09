/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.ab.donate;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.StringTextComponent;

public class DonatItems {
    private final int DEFAULT_PRICE = 10;
    public static final ArrayList<CustomItem> donitems = new ArrayList();
    static String input = "";

    public void add() {
        donitems.add(new CustomItem("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZhYmVlZDQyNGIyNTJhODk0NWE2NDQyYjQ2MmQ1ZjMxNDcwMWE4MTZkYTJkMGE2OWNjZGZjZmQ3NDZlNTg4ZSJ9fX0=", "Сфера Химеры", "ximera", "Уровень MAX"));
        donitems.add(new CustomItem("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgxMzYzNWJkODZiMTcxYmJlMTQzYWQ3MWUwOTAyMjkyNjQ5Y2IzYWI4NDQwZWQwMGY4NWNhNmNhMzgyOTkzNiJ9fX0=", "Сфера Осириса", "osiris", "Уровень MAX"));
        donitems.add(new CustomItem("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTAzN2JiYmViNjJlMTAyMGRmOWEwNmM0ZWRkNjAzMzBlNzA2MzBkMDkwZjA5NGQ4Nzc2YzJiZDEzNWRlYzIyIn19fQ==", "Сфера Титана", "titan", "Уровень MAX"));
        donitems.add(new CustomItem("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQxMTdiNjAxOGZlZjBkNTE1NjcyMTczZTNiMjZlNjYwZDY1MWU1ODc2YmE2ZDAzZTUzNDIyNzBjNDliZWM4MCJ9fX0=", "Сфера Аполлона", "apollona", "Уровень MAX"));
        donitems.add(new CustomItem("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU1MWU2NWViNDA1Mjc3MjM4MmM5ZTUwN2E1NGJkZWQ0M2UzOWY3NTViNWRkZjU1YjNmMzk0NDNjZWQ0NjdmNCJ9fX0=", "Сфера Пандоры", "pandora", "Уровень MAX"));
        donitems.add(new CustomItem("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDRmZmUzZjM1OGYyMDliYWQ4ZmZmNGRjNDgyNDVkOWJhZjBhMDMxYjNjMWVlNmI3NTg0NjBhMzM5YjE1MTllMiJ9fX0=", "Сфера Андромеды", "andromeda", "Уровень MAX"));
        donitems.add(new CustomItem("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE1YWFkZDUyYTVmYWI5NzA4ODE0NTFhZGY1NmZiYjQ5M2EzNTg1NmVhOTZmNTRlMzJlZWE2NjJkNzg3ZWQyMCJ9fX0=", "Сфера Астрея", "astrey", "Уровень MAX"));
        DonatItems.addTotems();
        DonatItems.addWeaponsAndItems();
        DonatItems.addArmor();
        DonatItems.addArrows();
        DonatItems.addPotions();
        HashMap<Enchantment, Integer> fake = new HashMap<Enchantment, Integer>();
        fake.put(Enchantments.UNBREAKING, 0);
        for (CustomItem customItem : donitems) {
            ItemStack stack = DonatItems.createItemStackFromCustomItem(customItem);
            EnchantmentHelper.setEnchantments(fake, stack);
        }
    }

    private static void addTotems() {
        donitems.add(new CustomItem("", "Талисман Гармонии", "harmony_talisman", "Уровень MAX"));
        donitems.add(new CustomItem("", "Талисман Грани", "edge_talisman", "Уровень MAX"));
    }

    private static void addWeaponsAndItems() {
        donitems.add(new CustomItem("", "Дезориентация", "disorientation", ""));
        donitems.add(new CustomItem("", "Меч Крушителя", "crusher_sword", ""));
        donitems.add(new CustomItem("", "Шалкеровый ящик", "shulker_box", ""));
        donitems.add(new CustomItem("", "Зачарованное золотое яблоко", "enchanted_golden_apple", ""));
    }

    private static void addArmor() {
        donitems.add(new CustomItem("", "Шлем Сатаны", "satan_helmet", ""));
        donitems.add(new CustomItem("", "Шлем Крушителя", "crusher_helmet", ""));
    }

    private static void addArrows() {
        donitems.add(new CustomItem("", "Стрела слабости", "weakness_arrow", ""));
        donitems.add(new CustomItem("", "Стрела медлительности", "slowness_arrow", ""));
    }

    private static void addPotions() {
        donitems.add(new CustomItem("", "Зелье исцеления", "healing_potion", ""));
        donitems.add(new CustomItem("", "Зелье силы", "strength_potion", ""));
    }


    private static ItemStack createItemStackFromCustomItem(CustomItem customItem) {
        ItemStack stack = new ItemStack(Items.PLAYER_HEAD);
        CompoundNBT tag = new CompoundNBT();
        CompoundNBT skullOwner = new CompoundNBT();
        skullOwner.putString("Id", customItem.getId());
        skullOwner.putString("Name", customItem.getName());
        CompoundNBT properties = new CompoundNBT();
        ListNBT textures = new ListNBT();
        CompoundNBT texture = new CompoundNBT();
        texture.putString("Value", customItem.getTextureUrl());
        textures.add(texture);
        properties.put("textures", textures);
        skullOwner.put("Properties", properties);
        tag.put("SkullOwner", skullOwner);
        ListNBT lore = new ListNBT();
        lore.add(StringNBT.valueOf(customItem.getDescription()));
        CompoundNBT display = new CompoundNBT();
        display.put("Lore", lore);
        tag.put("display", display);
        stack.setTag(tag);
        stack.setDisplayName(new StringTextComponent(customItem.getName()));
        return stack;
    }

    public static class CustomItem {
        private final String textureUrl;
        private final String name;
        private final String id;
        private final String description;

        public CustomItem(String textureUrl, String name, String id, String description) {
            this.textureUrl = textureUrl;
            this.name = name;
            this.id = id;
            this.description = description;
        }

        public String get() {
            return input;
        }

        public String getTextureUrl() {
            return this.textureUrl;
        }

        public String getName() {
            return this.name;
        }

        public String getId() {
            return this.id;
        }

        public String getDescription() {
            return this.description;
        }
    }
}

