/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.property.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.property.Property;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class IntProperty extends Property {
    private int value;
    private final int min;
    private final int max;

    public IntProperty(String name, int value, int min, int max) {
        super(name);
        this.value = value;
        this.min = min;
        this.max = max;
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(value);
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element != null)
            value = element.getAsInt();
    }

    public void setValue(int value) {
        this.value = MathHelper.clamp(value, min, max);
        reportChanges();
    }

    public int getValue() {
        return value;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                        argument("value", IntegerArgumentType.integer(min, max))
                                .executes(ctx -> {
                                    setValue(IntegerArgumentType.getInteger(ctx, "value"));
                                    ChatUtil.sendI18n("command.property.set",getName(), getValue());
                                    return 1;
                                })
        ).executes(context -> {
            ChatUtil.sendI18n("command.property.equals",getName(), getValue());
            return 1;
        });
    }
}
