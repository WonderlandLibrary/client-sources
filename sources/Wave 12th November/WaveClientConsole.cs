using System;
using System.Threading;
using Wave.Cmr;
using Wave.Cmr.Font;
using WaveClient.ModuleManagment;

namespace WaveClient
{
	// Token: 0x02000002 RID: 2
	public static class WaveClientConsole
	{
		// Token: 0x06000001 RID: 1 RVA: 0x00002048 File Offset: 0x00000248
		public static void Start(string[] args)
		{
			cmr.MaximizeConsole();
			cmr_font.SetConsoleFont("Cascadia Code", 12, 24, 0);
			Console.WriteLine(cmr.cf(89, 122, 255) + "Welcome to WaveClient!");
			Console.WriteLine(cmr.cf(200, 200, 200) + "Thanks for using it :D");
			Thread.Sleep(1000);
			cmr.MinimizeConsole();
			cmr.clogl(cmr.cf(100, 108, 143) + "WaveClient", "Loading Modules");
			Console.WriteLine(cmr.cf(100, 108, 143) + "Made by Milo and the Wave Development Team!");
			Thread.Sleep(100);
			ModuleManager.MemoryUpdate.StartTickThread();
		}

		// Token: 0x02000029 RID: 41
		public static class Application
		{
			// Token: 0x06000136 RID: 310 RVA: 0x00004C2F File Offset: 0x00002E2F
			public static void InitializeThread()
			{
				WaveClientConsole.Application.ConsoleThread = new Thread(new ThreadStart(WaveClientConsole.Application.ConsoleMain));
				WaveClientConsole.Application.ConsoleThread.SetApartmentState(ApartmentState.STA);
				WaveClientConsole.Application.ConsoleThread.Start();
			}

			// Token: 0x06000137 RID: 311 RVA: 0x00004C5C File Offset: 0x00002E5C
			public static void ConsoleMain()
			{
				WaveClientConsole.Start(WaveClientConsole.Application.args);
			}

			// Token: 0x040000A2 RID: 162
			public static Thread ConsoleThread;

			// Token: 0x040000A3 RID: 163
			public static string[] args = Environment.GetCommandLineArgs();
		}
	}
}
