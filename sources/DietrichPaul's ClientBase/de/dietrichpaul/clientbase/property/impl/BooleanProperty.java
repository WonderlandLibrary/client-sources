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
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.property.Property;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class BooleanProperty extends Property {

    private boolean state;

    public BooleanProperty(String name, boolean state) {
        super(name);
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        reportChanges();
    }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(state);
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element != null)
            state = element.getAsBoolean();
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        root.then(
                argument("value", BoolArgumentType.bool())
                        .executes(ctx -> {
                            setState(BoolArgumentType.getBool(ctx, "value"));
                            ChatUtil.sendI18n("command.property.set",getName(), getState());
                            return 1;
                        })
        ).executes(context -> {
            ChatUtil.sendI18n("command.property.equals",getName(), getState());
            return 1;
        });
    }
}
