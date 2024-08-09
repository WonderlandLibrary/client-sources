package dev.excellent.client.module.impl.misc.autobuy.manager;

import dev.excellent.Excellent;
import dev.excellent.client.module.impl.misc.autobuy.entity.AutoBuyItem;
import dev.excellent.client.module.impl.misc.autobuy.entity.DonateItem;
import dev.excellent.client.module.impl.misc.autobuy.entity.SphereItem;
import dev.excellent.impl.util.file.FileManager;
import i.gishreloaded.protection.annotation.Native;
import lombok.Getter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class AutoBuyManager extends CopyOnWriteArrayList<AutoBuyItem> {
    public static File AUTOBUY_DIRECTORY;

    private final List<AutoBuyItem> selectedItems = new CopyOnWriteArrayList<>();
    private final List<String> funtimeDonateItems = new CopyOnWriteArrayList<>();
    private final int DEFAULT_PRICE = 10;

    @Override
    public boolean add(AutoBuyItem autoBuyItem) {
        boolean isDuplicate = this.stream()
                .anyMatch(existingItem -> existingItem.getItemStack().equals(autoBuyItem.getItemStack()));
        if (!isDuplicate) {
            return super.add(autoBuyItem);
        }
        return false;
    }

    @Native
    public void init() {
        AUTOBUY_DIRECTORY = new File(FileManager.DIRECTORY, "autobuy");
        if (!AUTOBUY_DIRECTORY.exists()) {
            if (AUTOBUY_DIRECTORY.mkdir()) {
                System.out.println("Папка autobuy успешно создана.");
            } else {
                System.out.println("Произошла ошибка при создании папки autobuy.");
            }
        }
        this.clear();
        addFuntimeDonateItems();
        registerSphereItems();
        registerTalismans();
        registerKrushItems();
        registerStashItems();
        registerConsumableItems();
        registerAllItems();

    }

    private void registerAllItems() {
        for (Item item : Registry.ITEM) {
            boolean isValidItem = !item.equals(Items.AIR)
                    && !item.equals(Items.BEDROCK)
                    && !item.equals(Items.DEBUG_STICK)
                    && !item.equals(Items.BARRIER);
            if (isValidItem) {
                ItemStack itemStack = new ItemStack(item);
                List<Enchantment> enchantments = Registry.ENCHANTMENT.stream()
                        .filter(enchantment -> enchantment.canApply(itemStack))
                        .toList();

                HashMap<Enchantment, Boolean> enchantMap = new HashMap<>();
                enchantments.forEach(enchantment -> enchantMap.putIfAbsent(enchantment, false));

                this.add(new AutoBuyItem(itemStack, enchantMap, DEFAULT_PRICE));
            }
        }
    }

    private void registerSphereItems() {
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Иасо",
                "sphere-iaso",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFkYzRhMDI0NzE4ZDQwMWVlYWU5ZTk1YjNjOTI3NjdmOTE2ZjMyM2M5ZTgzNjQ5YWQxNWM5MjY1ZWU1MDkyZiJ9fX0=")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Скифа",
                "sphere-skifa",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNkMTQ1NjFiYmQwNjNmNzA0MjRhOGFmY2MzN2JmZTljNzQ1NjJlYTM2ZjdiZmEzZjIzMjA2ODMwYzY0ZmFmMSJ9fX0=")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Абанты",
                "sphere-abanti",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQxNDQ5MDk3YjRiNzlhOWY2Y2FmNjM0NDQxOGYyMDM0ZGU0YmI5NzFmZWI3YThlNGFhY2JmYjkwNWFjZGNlZiJ9fX0=")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Филона",
                "sphere-filona",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY2MzZiYTY5ODhjZTliNDBkZGM3NDlhMDljZTBmYjkzOWFmNTI2MDA1OTk1YzE4ZDMyM2FjOTY2MjVmMGQ2ZCJ9fX0=")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Сорана",
                "sphere-sorana",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTgwNTU3ZmU2M2YwNmI0YzcwZTJjNWViMmVmNmMwZDhkNWI1NWZkMDljYzVkZTNiY2I2NWY4MzJlMDVkNGMyZSJ9fX0=")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Эпиона",
                "sphere-epiona",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjgyMjAyODJmMmVlNTk5NTExYjRmYzc0NjExMWM5NzM2ZDdiNDkxZThiY2ZiNjQ4YThhMTU2MjkyODFlZTUifX19")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Афина",
                "sphere-afina",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNmOWVlZGEzYmEyM2ZlMTQyM2M0MDM2ZTdkZDBhNzQ0NjFkZmY5NmJhZGM1YjJmMmI5ZmFhN2NjMTZmMzgyZiJ9fX0=")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Панакея",
                "sphere-panakeya",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmZDViZjFmZjA1NDMxNDdjOWQ2NGU2ODc2MWRiNmU0YjcxMzJhYzY1OGYwYjhmNzk4MzFmYWQ5YzI4OWVjYSJ9fX0=")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Магмы",
                "sphere-magma",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWRiNWNlMGQ0NGMzZTgxMzhkYzJlN2U1MmMyODk3YmI4NzhlMWRiYzIyMGQ3MDY4OWM3YjZiMThkMzE3NWUwZiJ9fX0=")
        );
        this.add(new SphereItem(new ItemStack(Items.PLAYER_HEAD),
                DEFAULT_PRICE,
                TextFormatting.RED + "Сфера Теургия",
                "sphere-teyrgiya",
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIwMWFlMWE4YTA0ZGY1MjY1NmY1ZTQ4MTNlMWZiY2Y5Nzg3N2RiYmZiYzQyNjhkMDQzMTZkNmY5Zjc1MyJ9fX0==")
        );
    }

    private void registerTalismans() {
        List<ItemStack> totems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING);

            totem.addEnchantment(Enchantments.MENDING, 0);
            totems.add(totem);
        }

        this.add(new DonateItem(totems.get(0),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Фугу",
                "tal-fugu")
        );
        this.add(new DonateItem(totems.get(1),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Эгида",
                "tal-egida")
        );
        this.add(new DonateItem(totems.get(2),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Крайта",
                "tal-kraita")
        );
        this.add(new DonateItem(totems.get(3),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Лекаря",
                "tal-medic")
        );
        this.add(new DonateItem(totems.get(4),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Манеса",
                "tal-manesa")
        );
        this.add(new DonateItem(totems.get(5),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Кобры",
                "tal-kobra")
        );
        this.add(new DonateItem(totems.get(6),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Диониса",
                "tal-dionisa")
        );
        this.add(new DonateItem(totems.get(7),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Гефеста",
                "tal-gefesta")
        );
        this.add(new DonateItem(totems.get(8),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Хауберка",
                "tal-hauberka")
        );
        this.add(new DonateItem(totems.get(9),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Талисман Крушителя",
                "tal-krush")
        );
    }

    private void registerKrushItems() {
        ItemStack helmet = new ItemStack(Items.NETHERITE_HELMET);
        ItemStack chestplate = new ItemStack(Items.NETHERITE_CHESTPLATE);
        ItemStack leggings = new ItemStack(Items.NETHERITE_LEGGINGS);
        ItemStack boots = new ItemStack(Items.NETHERITE_BOOTS);

        ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
        ItemStack pickaxe = new ItemStack(Items.NETHERITE_PICKAXE);
        ItemStack trident = new ItemStack(Items.TRIDENT);
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);

        helmet.addEnchantment(Enchantments.MENDING, 0);
        chestplate.addEnchantment(Enchantments.MENDING, 0);
        leggings.addEnchantment(Enchantments.MENDING, 0);
        boots.addEnchantment(Enchantments.MENDING, 0);

        sword.addEnchantment(Enchantments.MENDING, 0);
        pickaxe.addEnchantment(Enchantments.MENDING, 0);
        trident.addEnchantment(Enchantments.MENDING, 0);
        crossbow.addEnchantment(Enchantments.MENDING, 0);

        this.add(new DonateItem(helmet,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Шлем Крушителя",
                "krush-helmet")
        );
        this.add(new DonateItem(chestplate,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Нагрудник Крушителя",
                "krush-chestplate")
        );
        this.add(new DonateItem(leggings,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Поножи Крушителя",
                "krush-leggings")
        );
        this.add(new DonateItem(boots,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Ботинки Крушителя",
                "krush-boots")
        );

        this.add(new DonateItem(sword,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Меч Крушителя",
                "krush-sword")
        );
        this.add(new DonateItem(pickaxe,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Кирка Крушителя",
                "krush-pickaxe")
        );
        this.add(new DonateItem(trident,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Трезубец Крушителя",
                "krush-trident")
        );
        this.add(new DonateItem(crossbow,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Арбалет Крушителя",
                "krush-crossbow")
        );
    }

    private void registerStashItems() {
        ItemStack armour = new ItemStack(Items.TRIPWIRE_HOOK);
        ItemStack global = new ItemStack(Items.TRIPWIRE_HOOK);
        ItemStack spheres = new ItemStack(Items.TRIPWIRE_HOOK);
        ItemStack tools = new ItemStack(Items.TRIPWIRE_HOOK);
        ItemStack weapons = new ItemStack(Items.TRIPWIRE_HOOK);

        armour.addEnchantment(Enchantments.MENDING, 0);
        global.addEnchantment(Enchantments.MENDING, 0);
        spheres.addEnchantment(Enchantments.MENDING, 0);
        tools.addEnchantment(Enchantments.MENDING, 0);
        weapons.addEnchantment(Enchantments.MENDING, 0);

        this.add(new DonateItem(armour,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Отмычка к броне",
                "stash-armour")
        );
        this.add(new DonateItem(global,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Отмычка к ресурсам",
                "stash-global")
        );
        this.add(new DonateItem(spheres,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Отмычка к сферам",
                "stash-spheres")
        );
        this.add(new DonateItem(tools,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Отмычка к инструментам",
                "stash-tools")
        );
        this.add(new DonateItem(weapons,
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Отмычка к оружию",
                "stash-weapons")
        );

    }

    private void registerConsumableItems() {
        this.add(new DonateItem(new ItemStack(Items.NETHERITE_SCRAP),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Трапка",
                "trap")
        );
        this.add(new DonateItem(new ItemStack(Items.DRIED_KELP),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Пласт",
                "stratum")
        );
        this.add(new DonateItem(new ItemStack(Items.TNT),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Динамит TIER WHITE",
                "twhite")
        );
        this.add(new DonateItem(new ItemStack(Items.TNT),
                new HashMap<>(),
                DEFAULT_PRICE,
                TextFormatting.RED + "Динамит TIER BLACK",
                "tblack")
        );
    }

    private void addFuntimeDonateItems() {
        funtimeDonateItems.clear();
        funtimeDonateItems.addAll(Arrays.asList(
                "arrow-chiseled",
                "arrow-damn",
                "arrow-devil",
                "arrow-ice",
                "arrow-jaster",
                "arrow-paranoia",

                "fierytornado",

                "krush-boots",
                "krush-chestplate",
                "krush-crossbow",
                "krush-helmet",
                "krush-leggings",
                "krush-pickaxe",
                "krush-sword",
                "krush-trident",

                "potion-acid",
                "potion-agent",
                "potion-burp",
                "potion-flash",
                "potion-flare",
                "potion-medic",
                "potion-killer",
                "potion-winner",

                "processor",
                "serebro",

                "sphere-abanti",
                "sphere-afina",
                "sphere-filona",
                "sphere-iaso",
                "sphere-magma",
                "sphere-panakeya",
                "sphere-sorana",
                "sphere-teyrgiya",
                "sphere-iaso",
                "sphere-skifa",

                "stash-armour",
                "stash-global",
                "stash-spheres",
                "stash-tools",
                "stash-weapons",

                "stratum",

                "tal-dionisa",
                "tal-egida",
                "tal-fugu",
                "tal-gefesta",
                "tal-hauberka",
                "tal-kobra",
                "tal-manesa",
                "tal-medic",
                "tal-egida",
                "tal-krush",
                "tal-kraita",

                "tblack",
                "twhite"
        ));
    }

    public AutoBuyFile get() {
        final File file = new File(AUTOBUY_DIRECTORY, "autobuy." + Excellent.getInst().getInfo().getNamespace());
        return new AutoBuyFile(file);
    }

    public void set() {
        final File file = new File(AUTOBUY_DIRECTORY, "autobuy." + Excellent.getInst().getInfo().getNamespace());
        AutoBuyFile autoBuyFile = get();
        if (autoBuyFile == null) {
            autoBuyFile = new AutoBuyFile(file);
        }
        autoBuyFile.write();
    }

}