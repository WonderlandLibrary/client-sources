package im.expensive.command.impl;

import im.expensive.command.Parameters;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ParametersImpl implements Parameters {
    String[] parameters;

    public ParametersImpl(String[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public Optional<Integer> asInt(int index) {
        return Optional.ofNullable(getElementFromParametersOrNull(index, Integer::valueOf));
    }

    @Override
    public Optional<Float> asFloat(int index) {
        return Optional.ofNullable(getElementFromParametersOrNull(index, Float::valueOf));
    }

    @Override
    public Optional<Double> asDouble(int index) {
        return Optional.ofNullable(getElementFromParametersOrNull(index, Double::valueOf));
    }

    @Override
    public Optional<String> asString(int index) {
        return Optional.ofNullable(getElementFromParametersOrNull(index, String::valueOf));
    }

    @Override
    public String collectMessage(int startIndex) {
        return IntStream.range(startIndex, parameters.length)
                .mapToObj(i -> asString(i).orElse(""))
                .collect(Collectors.joining(" ")).trim();
    }



    private <T> T getElementFromParametersOrNull(int index, Function<String, T> mapper) {
        if (index >= parameters.length) {
            return null;
        }
        try {
            return mapper.apply(parameters[index]);
        } catch (Exception e) {
            return null;
        }
    }
}
