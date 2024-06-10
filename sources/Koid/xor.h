

#pragma once

//-------------------------------------------------------------//
// "Malware related compile-time hacks with C++11" by LeFF   //
// You can use this code however you like, I just don't really //
// give a shit, but if you feel some respect for me, please //
// don't cut off this comment when copy-pasting... ;-)       //
//-------------------------------------------------------------//

////////////////////////////////////////////////////////////////////
template <int X> struct EnsureCompileTime {
	enum : int {
		Value = X
	};
};
////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////
//Use Compile-Time as seed
#define r_seed ((__TIME__[7] - '0') * 1  + (__TIME__[6] - '0') * 10  + \
              (__TIME__[4] - '0') * 60   + (__TIME__[3] - '0') * 600 + \
              (__TIME__[1] - '0') * 3600 + (__TIME__[0] - '0') * 36000)
////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////
constexpr int LinearCongruentGenerator(int Rounds) {
	return 1013904223 + 1664525 * ((Rounds > 0) ? LinearCongruentGenerator(Rounds - 1) : r_seed & 0xFFFFFFFF);
}
#define random() EnsureCompileTime<LinearCongruentGenerator(10)>::Value //10 Rounds
#define random_number(Min, Max) (Min + (random() % (Max - Min + 1)))
////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////
template <int... Pack> struct IndexList {};
////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////
template <typename IndexList, int Right> struct Append;
template <int... Left, int Right> struct Append<IndexList<Left...>, Right> {
	typedef IndexList<Left..., Right> Result;
};
////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////
template <int N> struct ConstructIndexList {
	typedef typename Append<typename ConstructIndexList<N - 1>::Result, N - 1>::Result Result;
};
template <> struct ConstructIndexList<0> {
	typedef IndexList<> Result;
};
////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////
const char XORKEY = static_cast<char>(random_number(0, 0xFF));
constexpr char EncryptCharacter(const char Character, int Index) {
	return Character ^ (XORKEY + Index);
}

template <typename IndexList> class Cxor_string;
template <int... Index> class Cxor_string<IndexList<Index...> > {
private:
	char Value[sizeof...(Index) + 1];
public:
	constexpr Cxor_string(const char* const String)
		: Value{ EncryptCharacter(String[Index], Index)... } {}

	char* decrypt() {
		for (int t = 0; t < sizeof...(Index); t++) {
			Value[t] = Value[t] ^ (XORKEY + t);
		}
		Value[sizeof...(Index)] = '\0';
		return Value;
	}

	char* get() {
		return Value;
	}
};
#define xor_s(X, String) Cxor_string<ConstructIndexList<sizeof(String)-1>::Result> X(String)
#define xor_string( String ) ( Cxor_string<ConstructIndexList<sizeof( String ) - 1>::Result>( String ).decrypt() )
////////////////////////////////////////////////////////////////////