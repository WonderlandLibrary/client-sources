package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventDamageBlock;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;

public class NameProtect extends Module {
	public String nameString = "";
	private static final String PROTECTED_NAME_STRING = "FakeName";
	   
   public NameProtect(ModuleData data) {
      super(data);
      this.settings.put(PROTECTED_NAME_STRING, new Setting("Fake-Name", ".", "Command prefix."));
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
	   if(event instanceof EventMotionUpdate) {
		   if(nameString == "") {
			   nameString = "NameProtected";
		   }
		   nameString = (String)((Setting)this.settings.get(PROTECTED_NAME_STRING)).getValue();
	   }
   }
}
