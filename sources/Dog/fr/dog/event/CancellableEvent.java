package fr.dog.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class CancellableEvent extends Event {
    private boolean cancelled;
}
