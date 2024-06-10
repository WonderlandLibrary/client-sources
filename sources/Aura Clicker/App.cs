using System;
using System.Collections.Generic;

namespace uhssvc
{
	// Token: 0x02000013 RID: 19
	internal class App
	{
		// Token: 0x06000039 RID: 57 RVA: 0x00003DB4 File Offset: 0x00001FB4
		public static string GrabVariable(string string_0)
		{
			string result;
			try
			{
				if (User.ID != null || User.HWID != null || User.IP != null || !Constants.Breached)
				{
					result = App.Variables[string_0];
				}
				else
				{
					Constants.Breached = true;
					result = "User is not logged in, possible breach detected!";
				}
			}
			catch
			{
				result = "N/A";
			}
			return result;
		}

		// Token: 0x04000269 RID: 617
		public static string Error = null;

		// Token: 0x0400026A RID: 618
		public static Dictionary<string, string> Variables = new Dictionary<string, string>();
	}
}
