package pw.stamina.causam.scan.method.extract;

import java.lang.annotation.*;
import pw.stamina.causam.scan.method.model.*;
import java.util.function.*;
import java.lang.reflect.*;

class SimpleCollisionCheckingAnnotationExtractor<T extends Annotation> implements CollisionCheckingAnnotationExtractor<T>
{
    private final Class<T> extractionTarget;
    private final Function<Subscriber, T> subscriberExtractor;
    private final Predicate<Subscriber> collisionChecker;
    
    SimpleCollisionCheckingAnnotationExtractor(final Class<T> extractionTarget, final Function<Subscriber, T> subscriberExtractor, final Predicate<Subscriber> collisionChecker) {
        this.extractionTarget = extractionTarget;
        this.subscriberExtractor = subscriberExtractor;
        this.collisionChecker = collisionChecker;
    }
    
    @Override
    public final T extract(final Method method) {
        final Subscriber subscriber = method.getAnnotation(Subscriber.class);
        final T extracted = method.getAnnotation(this.getExtractionTarget());
        if (extracted != null) {
            if (this.checkCollision(subscriber)) {}
            return extracted;
        }
        return this.extractFromSubscriber(subscriber);
    }
    
    @Override
    public final Class<T> getExtractionTarget() {
        return this.extractionTarget;
    }
    
    @Override
    public final T extractFromSubscriber(final Subscriber subscriber) {
        return this.subscriberExtractor.apply(subscriber);
    }
    
    @Override
    public final boolean checkCollision(final Subscriber subscriber) {
        return this.collisionChecker.test(subscriber);
    }
}
