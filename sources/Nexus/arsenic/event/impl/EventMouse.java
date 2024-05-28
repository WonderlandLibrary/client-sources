package arsenic.event.impl;

import arsenic.event.types.Event;

public class EventMouse implements Event {

    public final int button;

    private EventMouse(int button) {
        this.button = button;
    }

    //pressed
    public static class Down extends EventMouse {
        public Down(int button) {super(button);}
    }

    //released
    public static class Up extends EventMouse {
        public Up(int button) {super(button);}
    }


}
