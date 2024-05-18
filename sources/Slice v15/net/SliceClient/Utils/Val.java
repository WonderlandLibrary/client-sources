package net.SliceClient.Utils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Val
{
  double min() default 1.0D;
  
  double max() default 10.0D;
  
  double increment() default 1.0D;
}
