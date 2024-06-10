using System;

namespace AnyDesk
{
	// Token: 0x02000011 RID: 17
	internal class Enums
	{
		// Token: 0x02000012 RID: 18
		internal enum ProcessAccessFlags : uint
		{
			// Token: 0x0400007E RID: 126
			All = 2035711U,
			// Token: 0x0400007F RID: 127
			Terminate = 1U,
			// Token: 0x04000080 RID: 128
			CreateThread,
			// Token: 0x04000081 RID: 129
			VMOperation = 8U,
			// Token: 0x04000082 RID: 130
			VMRead = 16U,
			// Token: 0x04000083 RID: 131
			VMWrite = 32U,
			// Token: 0x04000084 RID: 132
			DupHandle = 64U,
			// Token: 0x04000085 RID: 133
			SetInformation = 512U,
			// Token: 0x04000086 RID: 134
			QueryInformation = 1024U,
			// Token: 0x04000087 RID: 135
			Synchronize = 1048576U
		}

		// Token: 0x02000013 RID: 19
		internal enum EncodingType
		{
			// Token: 0x04000089 RID: 137
			ASCII,
			// Token: 0x0400008A RID: 138
			Unicode,
			// Token: 0x0400008B RID: 139
			UTF7,
			// Token: 0x0400008C RID: 140
			UTF8
		}

		// Token: 0x02000014 RID: 20
		[Flags]
		internal enum FreeType
		{
			// Token: 0x0400008E RID: 142
			Decommit = 16384,
			// Token: 0x0400008F RID: 143
			MEM_COMMIT = 4096,
			// Token: 0x04000090 RID: 144
			MEM_RELEASE = 32768,
			// Token: 0x04000091 RID: 145
			MEM_RESERVE = 8192
		}
	}
}
