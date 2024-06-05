package pw.stamina.causam.scan.method.extract;

import pw.stamina.causam.scan.method.model.*;

public final class PausableAnnotationExtractor extends SimpleCollisionCheckingAnnotationExtractor<Pausable>
{
    public PausableAnnotationExtractor() {
        super(Pausable.class, Subscriber::pausable, PausableAnnotationExtractor::subscriberHasCollidingPausable);
    }
    
    private static boolean subscriberHasCollidingPausable(final Subscriber subscriber) {
        return subscriber.pausable().value() != Pausable.PausableType.NONE;
    }
}
