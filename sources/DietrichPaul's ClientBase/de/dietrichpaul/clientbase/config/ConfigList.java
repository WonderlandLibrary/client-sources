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
package de.dietrichpaul.clientbase.config;

import de.dietrichpaul.clientbase.config.list.BindConfig;
import de.dietrichpaul.clientbase.config.list.FriendConfig;
import de.dietrichpaul.clientbase.config.list.HackConfig;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.ClientBase;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;

public class ConfigList implements UpdateListener {
    private final Set<AbstractConfig> configs = new LinkedHashSet<>();

    public BindConfig bind = new BindConfig();
    public FriendConfig friend = new FriendConfig();
    public HackConfig hack = new HackConfig();

    public ConfigList() {
        for (Field field : getClass().getFields()) {
            if (AbstractConfig.class.isAssignableFrom(field.getType())) {
                try {
                    addConfig((AbstractConfig) field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addConfig(AbstractConfig config) {
        this.configs.add(config);
    }

    public void start() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(UpdateListener.class, this);

        for (AbstractConfig config : this.configs) {
            if (config.getType() == ConfigType.PRE) {
                try {
                    config.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onUpdate() {
        for (AbstractConfig config : this.configs) {
            try {
                if (config.getType() == ConfigType.IN_GAME)
                    config.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(UpdateListener.class, this);
    }
}
