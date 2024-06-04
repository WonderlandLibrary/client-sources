package net.java.games.input;

import java.io.IOException;










































final class DIControllers
{
  private static final DIDeviceObjectData di_event = new DIDeviceObjectData();
  
  DIControllers() {}
  
  public static final synchronized boolean getNextDeviceEvent(Event event, IDirectInputDevice device) throws IOException { if (!device.getNextEvent(di_event))
      return false;
    DIDeviceObject object = device.mapEvent(di_event);
    DIComponent component = device.mapObject(object);
    if (component == null)
      return false;
    int event_value;
    int event_value; if (object.isRelative()) {
      event_value = object.getRelativeEventValue(di_event.getData());
    } else {
      event_value = di_event.getData();
    }
    event.set(component, component.getDeviceObject().convertValue(event_value), di_event.getNanos());
    return true;
  }
  
  public static final float poll(Component component, DIDeviceObject object) throws IOException {
    int poll_data = object.getDevice().getPollData(object);
    float result;
    float result; if (object.isRelative()) {
      result = object.getRelativePollValue(poll_data);
    } else {
      result = poll_data;
    }
    return object.convertValue(result);
  }
}
