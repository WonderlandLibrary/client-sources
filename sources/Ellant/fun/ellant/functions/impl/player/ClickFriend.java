package fun.ellant.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventKey;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.settings.impl.BindSetting;
import fun.ellant.utils.player.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import fun.ellant.command.friends.FriendStorage;
import fun.ellant.functions.api.FunctionRegister;

@FunctionRegister(name = "ClickFriend", type = Category.PLAYER, desc = "Добавляет друзей по нажатию кнопки")
public class ClickFriend extends Function {
    final BindSetting throwKey = new BindSetting("Кнопка", -98);
    public ClickFriend() {
        addSettings(throwKey);
    }
    @Subscribe
    public void onKey(EventKey e) {
        if (e.getKey() == throwKey.get() && mc.pointedEntity instanceof PlayerEntity) {

            if (mc.player == null || mc.pointedEntity == null) {
                return;
            }

            String playerName = mc.pointedEntity.getName().getString();

            if (!PlayerUtils.isNameValid(playerName)) {
                print("Невозможно добавить бота в друзья, увы, как бы вам не хотелось это сделать");
                return;
            }

            if (FriendStorage.isFriend(playerName)) {
                FriendStorage.remove(playerName);
                printStatus(playerName, true);
            } else {
                FriendStorage.add(playerName);
                printStatus(playerName, false);
            }
        }
    }

    void printStatus(String name, boolean remove) {
        if (remove) print(name + " удалён из друзей");
        else print(name + " добавлен в друзья");
    }
}
