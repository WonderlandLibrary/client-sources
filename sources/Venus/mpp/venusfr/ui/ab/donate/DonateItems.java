/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.donate;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static void add() {
        donitem.add(DonateItems.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWRiNWNlMGQ0NGMzZTgxMzhkYzJlN2U1MmMyODk3YmI4NzhlMWRiYzIyMGQ3MDY4OWM3YjZiMThkMzE3NWUwZiJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u041c\u0430\u0433\u043c\u044b"));
        donitem.add(DonateItems.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIwMWFlMWE4YTA0ZGY1MjY1NmY1ZTQ4MTNlMWZiY2Y5Nzg3N2RiYmZiYzQyNjhkMDQzMTZkNmY5Zjc1MyJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0422\u0435\u0443\u0440\u0433\u0435\u044f"));
        donitem.add(DonateItems.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmZDViZjFmZjA1NDMxNDdjOWQ2NGU2ODc2MWRiNmU0YjcxMzJhYzY1OGYwYjhmNzk4MzFmYWQ5YzI4OWVjYSJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u041f\u0430\u043d\u0430\u043a\u0435\u044f"));
        donitem.add(DonateItems.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY2MzZiYTY5ODhjZTliNDBkZGM3NDlhMDljZTBmYjkzOWFmNTI2MDA1OTk1YzE4ZDMyM2FjOTY2MjVmMGQ2ZCJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0424\u0438\u043b\u043e\u043d\u0430"));
        donitem.add(DonateItems.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNmOWVlZGEzYmEyM2ZlMTQyM2M0MDM2ZTdkZDBhNzQ0NjFkZmY5NmJhZGM1YjJmMmI5ZmFhN2NjMTZmMzgyZiJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0410\u0444\u0438\u043d\u0430"));
        donitem.add(DonateItems.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY3OTliZmFhM2EyYzYzYWQ4NWRkMzc4ZTY2ZDU3ZDlhOTdhM2Y4NmQwZDlmNjgzYzQ5ODYzMmY0ZjVjIn19fQ=", "\u0421\u0444\u0435\u0440\u0430 \u0421\u043e\u0440\u0430\u043d\u0430"));
        donitem.add(DonateItems.add("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjgyMjAyODJmMmVlNTk5NTExYjRmYzc0NjExMWM5NzM2ZDdiNDkxZThiY2ZiNjQ4YThhMTU2MjkyODFlZTUifX19=", "\u0421\u0444\u0435\u0440\u0430 \u042d\u043f\u0438\u043e\u043d\u0430"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmFkYzRhMDI0NzE4ZDQwMWVlYWU5ZTk1YjNjOTI3NjdmOTE2ZjMyM2M5ZTgzNjQ5YWQxNWM5MjY1ZWU1MDkyZiJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0418\u0430\u0441\u043e"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQxNDQ5MDk3YjRiNzlhOWY2Y2FmNjM0NDQxOGYyMDM0ZGU0YmI5NzFmZWI3YThlNGFhY2JmYjkwNWFjZGNlZiJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0410\u0431\u0430\u043d\u0442\u044b"));
        donitem.add(DonateItems.add("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNkMTQ1NjFiYmQwNjNmNzA0MjRhOGFmY2MzN2JmZTljNzQ1NjJlYTM2ZjdiZmEzZjIzMjA2ODMwYzY0ZmFmMSJ9fX0=", "\u0421\u0444\u0435\u0440\u0430 \u0421\u043a\u0438\u0444\u0430"));
        Object object = new ItemStack(Items.TOTEM_OF_UNDYING);
        ((ItemStack)object).setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0424\u0443\u0433\u0443"));
        ItemStack itemStack = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u042d\u0433\u0438\u0434\u0430"));
        ItemStack itemStack210 = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack210.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0440\u0430\u0439\u0442\u0430"));
        ItemStack itemStack3 = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack3.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041b\u0435\u043a\u0430\u0440\u044f"));
        ItemStack itemStack4 = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack4.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041c\u0430\u043d\u0435\u0441\u0430"));
        ItemStack itemStack5 = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack5.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u043e\u0431\u0440\u044b"));
        ItemStack itemStack6 = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack6.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0414\u0438\u043e\u043d\u0438\u0441\u0430"));
        ItemStack itemStack7 = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack7.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0413\u0435\u0444\u0435\u0441\u0442\u0430"));
        ItemStack itemStack8 = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack8.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u0425\u0430\u0443\u0431\u0435\u0440\u043a\u0430"));
        ItemStack itemStack9 = new ItemStack(Items.TOTEM_OF_UNDYING);
        itemStack9.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack10 = new ItemStack(Items.ENDER_EYE);
        itemStack10.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0414\u0435\u0437\u043e\u0440\u0438\u0435\u043d\u0442\u0430\u0446\u0438\u044f"));
        ItemStack itemStack11 = new ItemStack(Items.NETHERITE_SWORD);
        itemStack11.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041c\u0435\u0447 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack12 = new ItemStack(Items.NETHERITE_SWORD);
        itemStack12.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041a\u0430\u0442\u0430\u043d\u0430"));
        ItemStack itemStack13 = new ItemStack(Items.NETHERITE_SWORD);
        itemStack13.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041c\u0435\u0447 \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack itemStack14 = new ItemStack(Items.DRIED_KELP);
        itemStack14.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u043b\u0430\u0441\u0442"));
        ItemStack itemStack15 = new ItemStack(Items.SUGAR);
        itemStack15.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u042f\u0432\u043d\u0430\u044f \u043f\u044b\u043b\u044c"));
        ItemStack itemStack16 = new ItemStack(Items.TRIDENT);
        itemStack16.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0440\u0435\u0437\u0443\u0431\u0435\u0446 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack17 = new ItemStack(Items.BOW);
        itemStack17.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041b\u0443\u043a \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack18 = new ItemStack(Items.BOW);
        itemStack18.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041b\u0443\u043a \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack itemStack19 = new ItemStack(Items.BOW);
        itemStack19.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041b\u0443\u043a \u0424\u0430\u043d\u0442\u043e\u043c\u0430"));
        ItemStack itemStack20 = new ItemStack(Items.CROSSBOW);
        itemStack20.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0410\u0440\u0431\u0430\u043b\u0435\u0442 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack21 = new ItemStack(Items.NETHERITE_SCRAP);
        itemStack21.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u0440\u0430\u043f\u043a\u0430"));
        ItemStack itemStack22 = new ItemStack(Items.DRIED_KELP);
        itemStack22.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041d\u043e\u0432\u043e\u0433\u043e\u0434\u043d\u0438\u0439 \u041f\u043b\u0430\u0441\u0442"));
        ItemStack itemStack23 = new ItemStack(Items.SNOWBALL);
        itemStack23.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0421\u043d\u0435\u0436\u043e\u043a \u0437\u0430\u043c\u043e\u0440\u043e\u0437\u043a\u0430"));
        ItemStack itemStack24 = new ItemStack(Items.NETHERITE_SCRAP);
        itemStack24.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041d\u043e\u0432\u043e\u0433\u043e\u0434\u043d\u044f\u044f \u0422\u0440\u0430\u043f\u043a\u0430"));
        ItemStack itemStack25 = new ItemStack(Items.NETHERITE_HELMET);
        itemStack25.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0428\u043b\u0435\u043c \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack itemStack26 = new ItemStack(Items.NETHERITE_HELMET);
        itemStack26.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0428\u043b\u0435\u043c \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack27 = new ItemStack(Items.NETHERITE_CHESTPLATE);
        itemStack27.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack itemStack28 = new ItemStack(Items.NETHERITE_CHESTPLATE);
        itemStack28.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041d\u0430\u0433\u0440\u0443\u0434\u043d\u0438\u043a \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack29 = new ItemStack(Items.NETHERITE_LEGGINGS);
        itemStack29.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u043e\u043d\u043e\u0436\u0438 \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack itemStack30 = new ItemStack(Items.NETHERITE_LEGGINGS);
        itemStack30.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u043e\u043d\u043e\u0436\u0438 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack31 = new ItemStack(Items.NETHERITE_BOOTS);
        itemStack31.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0411\u043e\u0442\u0438\u043d\u043a\u0438 \u0421\u0430\u0442\u0430\u043d\u044b"));
        ItemStack itemStack32 = new ItemStack(Items.NETHERITE_BOOTS);
        itemStack32.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0411\u043e\u0442\u0438\u043d\u043a\u0438 \u041a\u0440\u0443\u0448\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack33 = new ItemStack(Items.ARROW);
        itemStack33.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0414\u044c\u044f\u0432\u043e\u043b\u044c\u0441\u043a\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack itemStack34 = new ItemStack(Items.ARROW);
        itemStack34.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0422\u043e\u0447\u0435\u043d\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack itemStack35 = new ItemStack(Items.ARROW);
        itemStack35.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0421\u0442\u0440\u0435\u043b\u0430 \u043f\u0430\u0440\u0430\u043d\u043e\u0439\u0438"));
        ItemStack itemStack36 = new ItemStack(Items.ARROW);
        itemStack36.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0421\u0442\u0440\u0435\u043b\u0430 \u0414\u0436\u0430\u0441\u0442\u0435\u0440\u0430"));
        ItemStack itemStack37 = new ItemStack(Items.ARROW);
        itemStack37.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041b\u0435\u0434\u044f\u043d\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack itemStack38 = new ItemStack(Items.ARROW);
        itemStack38.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u042f\u0434\u043e\u0432\u0438\u0442\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack itemStack39 = new ItemStack(Items.ARROW);
        itemStack39.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041f\u0440\u043e\u043a\u043b\u044f\u0442\u0430\u044f \u0441\u0442\u0440\u0435\u043b\u0430"));
        ItemStack itemStack40 = new ItemStack(Items.POTION);
        itemStack40.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0441\u0438\u043b\u044b"));
        ItemStack itemStack41 = new ItemStack(Items.POTION);
        itemStack41.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u0438"));
        ItemStack itemStack42 = new ItemStack(Items.POTION);
        itemStack42.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u0438"));
        ItemStack itemStack43 = new ItemStack(Items.POTION);
        itemStack43.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u043f\u0440\u044b\u0433\u0443\u0447\u0435\u0441\u0442\u0438"));
        ItemStack itemStack44 = new ItemStack(Items.POTION);
        itemStack44.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0440\u0435\u0433\u0435\u043d\u0435\u0440\u0430\u0446\u0438\u0438"));
        ItemStack itemStack45 = new ItemStack(Items.POTION);
        itemStack45.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u043d\u043e\u0447\u043d\u043e\u0433\u043e \u0437\u0440\u0435\u043d\u0438\u044f"));
        ItemStack itemStack46 = new ItemStack(Items.POTION);
        itemStack46.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u043e\u0433\u043d\u0435\u0441\u0442\u043e\u0439\u043a\u043e\u0441\u0442\u0438"));
        ItemStack itemStack47 = new ItemStack(Items.POTION);
        itemStack47.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0432\u043e\u0434\u043d\u043e\u0433\u043e \u0434\u044b\u0445\u0430\u043d\u0438\u044f"));
        ItemStack itemStack48 = new ItemStack(Items.SPLASH_POTION);
        itemStack48.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u041c\u043e\u0447\u0430 \u0444\u043b\u0435\u0448\u0430"));
        ItemStack itemStack49 = new ItemStack(Items.SPLASH_POTION);
        itemStack49.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u041c\u0435\u0434\u0438\u043a\u0430"));
        ItemStack itemStack50 = new ItemStack(Items.SPLASH_POTION);
        itemStack50.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u0410\u0433\u0435\u043d\u0442\u0430"));
        ItemStack itemStack51 = new ItemStack(Items.SPLASH_POTION);
        itemStack51.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u041f\u043e\u0431\u0435\u0434\u0438\u0442\u0435\u043b\u044f"));
        ItemStack itemStack52 = new ItemStack(Items.SPLASH_POTION);
        itemStack52.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u041a\u0438\u043b\u043b\u0435\u0440\u0430"));
        ItemStack itemStack53 = new ItemStack(Items.SPLASH_POTION);
        itemStack53.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0417\u0435\u043b\u044c\u0435 \u041e\u0442\u0440\u044b\u0436\u043a\u0438"));
        ItemStack itemStack54 = new ItemStack(Items.SPLASH_POTION);
        itemStack54.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0421\u0435\u0440\u043d\u0430\u044f \u043a\u0438\u0441\u043b\u043e\u0442\u0430"));
        ItemStack itemStack55 = new ItemStack(Items.SPLASH_POTION);
        itemStack55.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u0412\u0441\u043f\u044b\u0448\u043a\u0430"));
        donitem.addAll(List.of((Object[])new ItemStack[]{itemStack10, itemStack11, itemStack12, itemStack13, itemStack14, itemStack15, itemStack16, itemStack17, itemStack18, itemStack19, itemStack20, itemStack21, itemStack22, itemStack23, itemStack24, itemStack25, itemStack26, itemStack27, itemStack28, itemStack29, itemStack30, itemStack31, itemStack32, itemStack33, itemStack34, itemStack35, itemStack36, itemStack37, itemStack38, itemStack39, itemStack40, itemStack41, itemStack42, itemStack43, itemStack44, itemStack45, itemStack46, itemStack47, itemStack48, itemStack49, itemStack50, itemStack51, itemStack52, itemStack53, itemStack54, itemStack55, itemStack6, itemStack, object, itemStack7, itemStack8, itemStack5, itemStack210, itemStack9, itemStack4, itemStack3}));
        object = new HashMap();
        ((HashMap)object).put(Enchantments.UNBREAKING, 0);
        for (ItemStack itemStack210 : donitem) {
            EnchantmentHelper.setEnchantments((Map<Enchantment, Integer>)object, itemStack210);
        }
    }

    public static ItemStack add(String string, String string2) {
        try {
            ItemStack itemStack = new ItemStack(Items.PLAYER_HEAD);
            itemStack.setTag(JsonToNBT.getTagFromJson(String.format("{SkullOwner:{Id:[I;-1949909288,1299464445,-1707774066,-249984712],Properties:{textures:[{Value:\"%s\"}]},Name:\"%s\"}}", string, string2)));
            itemStack.setDisplayName(new StringTextComponent(string2));
            return itemStack;
        } catch (CommandSyntaxException commandSyntaxException) {
            throw new RuntimeException(commandSyntaxException);
        }
    }
}

