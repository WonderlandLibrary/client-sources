using System;
using System.IO;

namespace New_CandyClient.ProtectionTools
{
	// Token: 0x0200002F RID: 47
	internal static class DnSpy
	{
		// Token: 0x0600016D RID: 365 RVA: 0x00012C93 File Offset: 0x00011093
		internal static bool ValueType()
		{
			return File.Exists(Environment.ExpandEnvironmentVariables("%appdata%") + "\\dnSpy\\dnSpy.xml");
		}
	}
}
