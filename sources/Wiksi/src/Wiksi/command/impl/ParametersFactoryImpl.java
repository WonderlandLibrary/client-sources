package src.Wiksi.command.impl;

import src.Wiksi.command.Parameters;
import src.Wiksi.command.ParametersFactory;

public class ParametersFactoryImpl implements ParametersFactory {

    @Override
    public Parameters createParameters(String message, String delimiter) {
        return new ParametersImpl(message.split(delimiter));
    }
}
