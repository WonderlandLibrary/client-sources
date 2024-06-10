using System;
using System.Threading;
using Wave.Cmr;
using Wave.Cmr.Font;
using WaveClient.ModuleManagment;

namespace WaveClient
{
	// Token: 0x02000005 RID: 5
	public static class WaveClientConsole
	{
		// Token: 0x06000021 RID: 33 RVA: 0x000024D4 File Offset: 0x000006D4
		public static void Start(string[] args)
		{
			cmr.MaximizeConsole();
			cmr_font.SetConsoleFont("Cascadia Code", 12, 24, 0);
			Console.WriteLine(cmr.cf(89, 122, 255) + "Welcome to WaveClient!");
			Console.WriteLine(cmr.cf(200, 200, 200) + "Thanks for using it :D");
			Thread.Sleep(1000);
			cmr.MinimizeConsole();
			cmr.clogl(cmr.cf(100, 108, 143) + "WaveClient", "Loading Modules");
			Thread.Sleep(100);
			ModuleManager.MemoryUpdate.StartTickThread();
			cmr.clogl(cmr.cf(100, 108, 143) + "WaveClient", "Being cool");
		}

		// Token: 0x02000028 RID: 40
		public static class Application
		{
			// Token: 0x06000115 RID: 277 RVA: 0x00004323 File Offset: 0x00002523
			public static void InitializeThread()
			{
				WaveClientConsole.Application.ConsoleThread = new Thread(new ThreadStart(WaveClientConsole.Application.ConsoleMain));
				WaveClientConsole.Application.ConsoleThread.SetApartmentState(ApartmentState.STA);
				WaveClientConsole.Application.ConsoleThread.Start();
			}

			// Token: 0x06000116 RID: 278 RVA: 0x00004350 File Offset: 0x00002550
			public static void ConsoleMain()
			{
				WaveClientConsole.Start(WaveClientConsole.Application.args);
			}

			// Token: 0x04000070 RID: 112
			public static Thread ConsoleThread;

			// Token: 0x04000071 RID: 113
			public static string[] args = Environment.GetCommandLineArgs();
		}
	}
}
