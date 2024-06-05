package pw.stamina.causam.scan.method.extract;

import java.lang.annotation.*;
import pw.stamina.causam.scan.method.model.*;

interface CollisionCheckingAnnotationExtractor<T extends Annotation> extends AnnotationExtractor<T>
{
    Class<T> getExtractionTarget();
    
    T extractFromSubscriber(final Subscriber p0);
    
    boolean checkCollision(final Subscriber p0);
}
