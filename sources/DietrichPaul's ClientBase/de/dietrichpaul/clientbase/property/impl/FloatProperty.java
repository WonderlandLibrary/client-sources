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
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.property.Property;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class FloatProperty extends Property {

    private float value;
    private float min;
    private float max;

    public FloatProperty(String name, float value, float min, float max) {
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
            value = element.getAsFloat();
    }

    public void setValue(float value) {
        this.value = MathHelper.clamp((Math.round(value * 10)) / 10F, min, max);
        reportChanges();
    }

    public float getValue() {
        return value;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                argument("value", FloatArgumentType.floatArg(min, max))
                        .executes(ctx -> {
                            setValue(FloatArgumentType.getFloat(ctx, "value"));
                            ChatUtil.sendI18n("command.property.set",getName(), getValue());
                            return 1;
                        })
        ).executes(context -> {
            ChatUtil.sendI18n("command.property.equals",getName(), getValue());
            return 1;
        });
    }
}
