#pragma once
#include <string>
#include <algorithm>
#include <array>

namespace rnd
{
	constexpr auto time = __TIME__;
	constexpr auto seed = static_cast<unsigned>(time[7]) + static_cast<unsigned>(time[6]) * 10 + static_cast<unsigned>(time[4]) * 60 + static_cast<unsigned>(time[3]) * 600 + static_cast<unsigned>(time[1]) * 3600 + static_cast<unsigned>(time[0]) * 36000;

	template <int n>
	struct gen
	{
	private:
		static constexpr unsigned a = 16807;
		static constexpr unsigned m = 2147483647;

		static constexpr unsigned s = gen<n - 1>::value;
		static constexpr unsigned lo = a * (s & 0xFFFFu);
		static constexpr unsigned hi = a * (s >> 16u);
		static constexpr unsigned lo2 = lo + ((hi & 0x7FFFu) << 16u);
		static constexpr unsigned hi2 = hi >> 15u;
		static constexpr unsigned lo3 = lo2 + hi;

	public:
		static constexpr unsigned max = m;
		static constexpr unsigned value = lo3 > m ? lo3 - m : lo3;
	};

	template <>
	struct gen<0>
	{
		static constexpr unsigned value = seed;
	};

	template <int n, int m>
	struct _int
	{
		static constexpr auto value = gen<n + 1>::value % m;
	};

	template <int n>
	struct _char
	{
		static const char value = static_cast<char>(1 + _int<n, 0x7F - 1>::value);
	};
}

template <size_t n, char k>
struct xorstr
{
private:
	static constexpr char enc(const char c)
	{
		return c ^ k;
	}

public:
	template <size_t... s>
	constexpr __forceinline xorstr(const char* str, std::index_sequence<s...>)
		: encrypted{ enc(str[s])... } { }

	__forceinline std::string dec(bool wipe = false)
	{
		std::string ret;
		ret.resize(n);

		for (size_t i = 0; i < n; i++)
		{
			ret[i] = encrypted[i] ^ k;

			if (wipe)
				encrypted[i] = 0;
		}

		return ret;
	}

	std::array<char, n> encrypted{};
};

// decrypts on stack
#define xors(s) xorstr<sizeof(s), rnd::_char<__COUNTER__>::value>(s, std::make_index_sequence<sizeof(s)>()).dec().c_str()

// raw, call dec on it urself
#define xors_raw(s) xorstr<sizeof(s), rnd::_char<__COUNTER__>::value>(s, std::make_index_sequence<sizeof(s)>())

// for one time use, it nulls out the data
#define xors_ot(s) xorstr<sizeof(s), rnd::_char<__COUNTER__>::value>(s, std::make_index_sequence<sizeof(s)>()).dec( true ).c_str()
