package com.leafclient.leaf.command.parser

import com.leafclient.commando.parser.ArgumentParser
import com.leafclient.leaf.utils.client.keyboard.Key

class KeyParser: ArgumentParser<Key> {

    override fun parseFrom(objectClass: Class<Key>, textArguments: MutableList<String>): Key {
        val text = textArguments[0]
            .toLowerCase()

        textArguments.removeAt(0)

        return Key(text)
    }

}