package gq.vapu.czfclient.API.eventapi;

import gq.vapu.czfclient.API.eventapi.events.Event;

public class EventRender implements Event {
    int pass;
    float partialTicks;
    long finishTimeNano;

    public EventRender(final int pass, final float partialTicks, final long finishTimeNano) {
        this.pass = pass;
        this.partialTicks = partialTicks;
        this.finishTimeNano = finishTimeNano;
    }

    public int getPass() {
        return this.pass;
    }

    public void setPass(final int pass) {
        this.pass = pass;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public long getFinishTimeNano() {
        return this.finishTimeNano;
    }

    public void setFinishTimeNano(final long finishTimeNano) {
        this.finishTimeNano = finishTimeNano;
    }
}