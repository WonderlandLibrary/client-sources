package im.expensive.command.impl;

import im.expensive.command.Parameters;
import im.expensive.command.ParametersFactory;

import java.util.Arrays;

public class ParametersFactoryImpl implements ParametersFactory {

    @Override
    public Parameters createParameters(String message, String delimiter) {
        return new ParametersImpl(message.split(delimiter));
    }
}
