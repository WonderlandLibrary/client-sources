/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.utils.player.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;

@FunctionRegister(name="ClickFriend", type=Category.Player)
public class ClickFriend
extends Function {
    final BindSetting throwKey = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430", -98);

    public ClickFriend() {
        this.addSettings(this.throwKey);
    }

    @Subscribe
    public void onKey(EventKey eventKey) {
        if (eventKey.getKey() == ((Integer)this.throwKey.get()).intValue() && ClickFriend.mc.pointedEntity instanceof PlayerEntity) {
            if (ClickFriend.mc.player == null || ClickFriend.mc.pointedEntity == null) {
                return;
            }
            String string = ClickFriend.mc.pointedEntity.getName().getString();
            if (!PlayerUtils.isNameValid(string)) {
                this.print("\u041d\u0435\u0432\u043e\u0437\u043c\u043e\u0436\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0431\u043e\u0442\u0430 \u0432 \u0434\u0440\u0443\u0437\u044c\u044f, \u0443\u0432\u044b, \u043a\u0430\u043a \u0431\u044b \u0432\u0430\u043c \u043d\u0435 \u0445\u043e\u0442\u0435\u043b\u043e\u0441\u044c \u044d\u0442\u043e \u0441\u0434\u0435\u043b\u0430\u0442\u044c");
                return;
            }
            if (FriendStorage.isFriend(string)) {
                FriendStorage.remove(string);
                this.printStatus(string, false);
            } else {
                FriendStorage.add(string);
                this.printStatus(string, true);
            }
        }
    }

    void printStatus(String string, boolean bl) {
        if (bl) {
            this.print(string + " \u0443\u0434\u0430\u043b\u0451\u043d \u0438\u0437 \u0434\u0440\u0443\u0437\u0435\u0439");
        } else {
            this.print(string + " \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0432 \u0434\u0440\u0443\u0437\u044c\u044f");
        }
    }
}

