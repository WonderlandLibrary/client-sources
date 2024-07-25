package club.bluezenith.module.modules.misc;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.TextRenderEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.StringValue;

@SuppressWarnings("unused")
public class StreamerMode extends Module {

    public StreamerMode(){
        super("Streamer", ModuleCategory.MISC);
    }
    private final BooleanValue hideName = new BooleanValue("Hide name", true).setIndex(1);
    private final StringValue fakeName = new StringValue("Fake name", "").showIf(this.hideName::get).setIndex(2);

    @Listener
    public void onTextEvent(TextRenderEvent e) {
        if(mc.thePlayer == null || mc.session.getUsername() == null) return;
        String username = mc.session.getUsername();

        if(hideName.get() && e.getText().contains(username)) {
            String text = e.getText().replace(username, fakeName.get().replace("&", "§").replace("%s", username));
            if(BlueZenith.getBlueZenith().getClientUser() != null) {
                text = text.replace("%u", BlueZenith.getBlueZenith().getClientUser().getUsername());
            }
            e.setText(text);
        }
     }
}
