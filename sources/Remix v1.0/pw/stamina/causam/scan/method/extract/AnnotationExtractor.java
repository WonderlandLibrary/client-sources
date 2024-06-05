package pw.stamina.causam.scan.method.extract;

import java.lang.annotation.*;
import java.lang.reflect.*;

public interface AnnotationExtractor<T extends Annotation>
{
    T extract(final Method p0);
}
