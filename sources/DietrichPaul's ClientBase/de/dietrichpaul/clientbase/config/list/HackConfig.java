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
package de.dietrichpaul.clientbase.config.list;

import com.google.gson.JsonObject;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.config.ConfigType;
import de.dietrichpaul.clientbase.config.ext.json.JsonObjectConfig;
import de.dietrichpaul.clientbase.feature.hack.Hack;

public class HackConfig extends JsonObjectConfig {

    public HackConfig() {
        super("hack", ConfigType.IN_GAME);
    }

    @Override
    protected void read(JsonObject element) {
        for (Hack hack : ClientBase.INSTANCE.getHackList().getHacks()) {
            if (element.has(hack.getName()))
                hack.deserializeHack(element.getAsJsonObject(hack.getName()));
        }
    }

    @Override
    protected void write(JsonObject element) {
        for (Hack hack : ClientBase.INSTANCE.getHackList().getHacks()) {
            element.add(hack.getName(), hack.serializeHack());
        }
    }
}
