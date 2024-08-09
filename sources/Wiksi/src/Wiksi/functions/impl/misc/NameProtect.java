//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.Wiksi;
import src.Wiksi.command.friends.EventFriendRemove;
import src.Wiksi.command.friends.EventManager;
import src.Wiksi.command.friends.FriendStorage;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.StringSetting;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;

@FunctionRegister(
        name = "NameProtect",
        type = Category.Misc
)
public class NameProtect extends Function {
    public static String fakeName = "";
    private String friendReplacementName = "";
    private Map<String, String> originalNames = new HashMap();
    public StringSetting name = new StringSetting("Ваш никнейм", "WIKSI SRC | BY KNOWQE FURIOUS CLIENT", "Укажите текст для замены вашего игрового ника");
    public StringSetting friendName = new StringSetting("Никнейм напарника", "WIKSI SRC | BY KNOWQE FURIOUS CLIENT", "Введите текст для замены игрового ника напарника");

    public NameProtect() {
        this.addSettings(new Setting[]{this.name});
        this.addSettings(new Setting[]{this.friendName});
        EventManager.register(this);
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        fakeName = (String)this.name.get();
        this.friendReplacementName = (String)this.friendName.get();
    }

    @Subscribe
    private void onFriendRemove(EventFriendRemove e) {
        String friend = e.getFriendName();
        if (this.originalNames.containsKey(friend)) {
            this.originalNames.remove(friend);
        }

    }

    public static String getReplaced(String input) {
        if (Wiksi.getInstance() != null && Wiksi.getInstance().getFunctionRegistry().getNameProtect().isState()) {
            NameProtect np = Wiksi.getInstance().getFunctionRegistry().getNameProtect();
            input = input.replace(Minecraft.getInstance().session.getUsername(), fakeName);

            Iterator var2;
            String originalName;
            for(var2 = FriendStorage.getFriends().iterator(); var2.hasNext(); input = input.replace(originalName, np.friendReplacementName)) {
                originalName = (String)var2.next();
                if (!np.originalNames.containsKey(originalName)) {
                    np.originalNames.put(originalName, originalName);
                }
            }

            var2 = np.originalNames.keySet().iterator();

            while(var2.hasNext()) {
                originalName = (String)var2.next();
                if (!FriendStorage.getFriends().contains(originalName)) {
                    input = input.replace(np.friendReplacementName, originalName);
                }
            }
        }

        return input;
    }
}
