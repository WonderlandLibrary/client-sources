package wtf.shiyeno.modules.impl.render;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.TextSetting;
import wtf.shiyeno.util.ClientUtil;

@FunctionAnnotation(
        name = "NameProtect",
        type = Type.Render
)
public class NameProtect extends Function {
    public TextSetting name = new TextSetting("Ник", "shiyeno");
    public BooleanOption friends = new BooleanOption("Друзья", false);

    public NameProtect() {
        this.addSettings(new Setting[]{this.name, this.friends});
    }

    public void onEvent(Event event) {
    }

    public String patch(String text) {
        String out = text;
        if (this.state) {
            out = text.replaceAll(Minecraft.getInstance().session.getUsername(), this.name.text);
        }

        return out;
    }

    public ITextComponent patchFriendTextComponent(ITextComponent text, String name) {
        ITextComponent out = text;
        if (this.friends.get() && this.state) {
            out = ClientUtil.replace(text, name, this.name.text);
        }

        return out;
    }
}