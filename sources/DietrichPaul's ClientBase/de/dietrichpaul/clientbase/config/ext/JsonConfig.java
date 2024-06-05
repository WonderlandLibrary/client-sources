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
package de.dietrichpaul.clientbase.config.ext;

import com.google.gson.JsonElement;
import de.dietrichpaul.clientbase.config.AbstractConfig;
import de.dietrichpaul.clientbase.config.ConfigType;
import de.dietrichpaul.clientbase.util.jvm.IOUtil;

public abstract class JsonConfig<T extends JsonElement> extends AbstractConfig {

    public JsonConfig(String name, ConfigType type) {
        super(name + ".json", type);
    }

    @Override
    protected void read() {
        this.read(IOUtil.readGsonOr(getFile(), make()));
        this.markAsLoaded();
    }

    @Override
    protected void write() {
        T elem = make();
        write(elem);
        IOUtil.writePrettyGson(getFile(), elem);
    }

    protected abstract T make();

    protected abstract void read(T element);
    protected abstract void write(T element);
}
