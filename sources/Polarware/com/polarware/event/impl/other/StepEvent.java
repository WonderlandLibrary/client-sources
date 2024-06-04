package com.polarware.event.impl.other;

import com.polarware.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class StepEvent implements Event {

    private double height;
}
