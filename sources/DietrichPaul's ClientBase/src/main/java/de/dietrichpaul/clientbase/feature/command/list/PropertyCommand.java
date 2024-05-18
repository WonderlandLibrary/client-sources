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
package de.dietrichpaul.clientbase.feature.command.list;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.command.Command;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.property.Property;
import de.dietrichpaul.clientbase.property.PropertyGroup;
import net.minecraft.command.CommandSource;

import java.util.Map;

public class PropertyCommand extends Command {

    public PropertyCommand() {
        super("property");
    }

    public void buildGroup(PropertyGroup group, LiteralArgumentBuilder<CommandSource> base, String name) {
        if (!group.hasProperties())
            return;
        LiteralArgumentBuilder<CommandSource> literal = literal(name);

        for (Map.Entry<String, PropertyGroup> propertyGroup : group.getPropertyGroups().entrySet()) {
            buildGroup(propertyGroup.getValue(), literal, propertyGroup.getKey());
        }

        for (Property property : group.getProperties()) {
            LiteralArgumentBuilder<CommandSource> propertyLiteral = literal(property.getName().replace(' ', '-'));
            property.buildCommand(propertyLiteral);
            literal.then(propertyLiteral);
        }

        base.then(literal);
    }

    @Override
    public void buildCommand(LiteralArgumentBuilder<CommandSource> root) {
        for (Hack hack : ClientBase.INSTANCE.getHackList().getHacks()) {
            buildGroup(hack, root, hack.getName());
        }
    }
}
