using System;
using Eternium_mcpe_client.DllImports;
using Eternium_mcpe_client.GUI;

namespace Eternium_mcpe_client.Data
{
	// Token: 0x02000016 RID: 22
	public static class CommunicationData
	{
		// Token: 0x02000061 RID: 97
		public static class GUI
		{
			// Token: 0x040005E1 RID: 1505
			public static double ColorRGBCounter = 0.0;
		}

		// Token: 0x02000062 RID: 98
		public static class Console
		{
			// Token: 0x040005E2 RID: 1506
			public static IntPtr Handle = DllImports.GetConsoleWindow();
		}

		// Token: 0x02000063 RID: 99
		public static class Overlay
		{
			// Token: 0x040005E3 RID: 1507
			public static Startscreen WindowObject;

			// Token: 0x040005E4 RID: 1508
			public static IntPtr TargetWindowHandle;
		}

		// Token: 0x02000064 RID: 100
		public static class MainWindow
		{
			// Token: 0x040005E5 RID: 1509
			public static Startscreen WindowObject;

			// Token: 0x040005E6 RID: 1510
			public static IntPtr WindowHandle;
		}
	}
}
