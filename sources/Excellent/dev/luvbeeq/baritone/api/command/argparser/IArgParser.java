package dev.luvbeeq.baritone.api.command.argparser;

import dev.luvbeeq.baritone.api.command.argument.ICommandArgument;

public interface IArgParser<T> {

    /**
     * @return the class of this parser.
     */
    Class<T> getTarget();

    /**
     * A stateless argument parser is just that. It takes a {@link ICommandArgument} and outputs its type.
     */
    interface Stateless<T> extends IArgParser<T> {

        /**
         * @param arg The argument to parse.
         * @return What it was parsed into.
         * @throws RuntimeException if you want the parsing to fail. The exception will be caught and turned into an
         *                          appropriate error.
         */
        T parseArg(ICommandArgument arg) throws Exception;
    }

    /**
     * A stated argument parser is similar to a stateless one. It also takes a {@link ICommandArgument}, but it also
     * takes a second argument that can be any type, referred to as the state.
     */
    interface Stated<T, S> extends IArgParser<T> {

        Class<S> getStateType();

        /**
         * @param arg   The argument to parse.
         * @param state Can be anything.
         * @return What it was parsed into.
         * @throws RuntimeException if you want the parsing to fail. The exception will be caught and turned into an
         *                          appropriate error.
         */
        T parseArg(ICommandArgument arg, S state) throws Exception;
    }
}
