package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 00:09
 */
public class MouseOverEvent extends Event {

    public double reach;
    public boolean flag;

    public MouseOverEvent(double reach , boolean flag) {
        this.reach = reach;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }
}
