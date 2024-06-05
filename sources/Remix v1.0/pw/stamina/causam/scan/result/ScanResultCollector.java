package pw.stamina.causam.scan.result;

import java.util.stream.*;
import pw.stamina.causam.subscribe.*;
import java.util.function.*;
import java.util.*;

public final class ScanResultCollector implements Collector<Subscription<?>, ScanResultBuilder, ScanResult>
{
    @Override
    public Supplier<ScanResultBuilder> supplier() {
        return ScanResultBuilder::new;
    }
    
    @Override
    public BiConsumer<ScanResultBuilder, Subscription<?>> accumulator() {
        return ScanResultBuilder::addSubscription;
    }
    
    @Override
    public BinaryOperator<ScanResultBuilder> combiner() {
        final ScanResult rightResult;
        return (BinaryOperator<ScanResultBuilder>)((left, right) -> {
            rightResult = right.build();
            rightResult.getSubscriptions().forEach(left::addSubscription);
            return left;
        });
    }
    
    @Override
    public Function<ScanResultBuilder, ScanResult> finisher() {
        return ScanResultBuilder::build;
    }
    
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
