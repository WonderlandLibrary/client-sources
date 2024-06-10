#pragma once
#include <string>
#include <ctype.h>

static char Cipher(char ch, int key)
{
	if (!isalpha(ch))
		return ch;

	char offset = isupper(ch) ? 'A' : 'a';
	return (char)((((ch + key) - offset) % 26) + offset);
}

static std::string Encipher(std::string input, int key)
{
	std::string output = "";

	for (int i = 0; i < input.size(); i++)
	{
		output += Cipher(input[i], key);
	}

	return output;
}

static std::string Decipher(std::string input, int key)
{
	return Encipher(input, 26 - key);
}