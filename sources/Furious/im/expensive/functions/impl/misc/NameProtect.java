package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import im.expensive.Furious;
import im.expensive.command.friends.FriendStorage;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.Setting;
import im.expensive.functions.settings.impl.StringSetting;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@FunctionRegister(
        name = "NameProtect",
        type = Category.Miscellaneous
)
public class NameProtect extends Function {
    public static String fakeName = "";
    private String friendReplacementName = "";
    private Map<String, String> originalNames = new HashMap();
    public StringSetting name = new StringSetting("Ваше фейк имя", "Furious", "Укажите фейковый никнейм");
    public StringSetting friendName = new StringSetting("Фейк имя ваших друзей", "Furious", "Укажитей фейковый никнейм");

    public NameProtect() {
        this.addSettings(new Setting[]{this.name});
        this.addSettings(new Setting[]{this.friendName});
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        fakeName = (String)this.name.get();
        this.friendReplacementName = (String)this.friendName.get();
    }

    public static String getReplaced(String input) {
        if (Furious.getInstance() != null && Furious.getInstance().getFunctionRegistry().getNameProtect().isState()) {
            NameProtect np = Furious.getInstance().getFunctionRegistry().getNameProtect();
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
