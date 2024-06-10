using System;

namespace New_CandyClient.Memory
{
	// Token: 0x02000043 RID: 67
	internal class Enums
	{
		// Token: 0x02000044 RID: 68
		internal enum ProcessAccessFlags : uint
		{
			// Token: 0x040003E2 RID: 994
			All = 2035711U,
			// Token: 0x040003E3 RID: 995
			Terminate = 1U,
			// Token: 0x040003E4 RID: 996
			CreateThread,
			// Token: 0x040003E5 RID: 997
			VMOperation = 8U,
			// Token: 0x040003E6 RID: 998
			VMRead = 16U,
			// Token: 0x040003E7 RID: 999
			VMWrite = 32U,
			// Token: 0x040003E8 RID: 1000
			DupHandle = 64U,
			// Token: 0x040003E9 RID: 1001
			SetInformation = 512U,
			// Token: 0x040003EA RID: 1002
			QueryInformation = 1024U,
			// Token: 0x040003EB RID: 1003
			Synchronize = 1048576U
		}

		// Token: 0x02000045 RID: 69
		internal enum EncodingType
		{
			// Token: 0x040003ED RID: 1005
			ASCII,
			// Token: 0x040003EE RID: 1006
			Unicode,
			// Token: 0x040003EF RID: 1007
			UTF7,
			// Token: 0x040003F0 RID: 1008
			UTF8
		}

		// Token: 0x02000046 RID: 70
		[Flags]
		internal enum FreeType
		{
			// Token: 0x040003F2 RID: 1010
			Decommit = 16384,
			// Token: 0x040003F3 RID: 1011
			MEM_COMMIT = 4096,
			// Token: 0x040003F4 RID: 1012
			MEM_RELEASE = 32768,
			// Token: 0x040003F5 RID: 1013
			MEM_RESERVE = 8192
		}
	}
}
