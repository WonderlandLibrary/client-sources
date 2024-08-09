package wtf.resolute.command.impl;

import wtf.resolute.command.Parameters;
import wtf.resolute.command.ParametersFactory;

public class ParametersFactoryImpl implements ParametersFactory {

    @Override
    public Parameters createParameters(String message, String delimiter) {
        return new ParametersImpl(message.split(delimiter));
    }
}
