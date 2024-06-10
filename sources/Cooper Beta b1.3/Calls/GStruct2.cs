using System.Runtime.CompilerServices;

namespace ns0;

public struct GStruct2
{
	[CompilerGenerated]
	private readonly long long_0;

	[CompilerGenerated]
	private readonly byte[] byte_0;

	public long _base
	{
		[CompilerGenerated]
		get
		{
			return long_0;
		}
	}

	public byte[] buf
	{
		[CompilerGenerated]
		get
		{
			return byte_0;
		}
	}

	public GStruct2(long long_1, byte[] byte_1)
	{
		long_0 = long_1;
		byte_0 = byte_1;
	}
}
