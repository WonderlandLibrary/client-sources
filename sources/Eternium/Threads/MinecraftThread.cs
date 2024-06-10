using System;
using System.Threading;
using System.Windows.Threading;
using Eternium_mcpe_client.Data;
using Eternium_mcpe_client.GUI;
using Eternium_mcpe_client.Input;

namespace Eternium_mcpe_client.Threads
{
	// Token: 0x0200000A RID: 10
	public static class MinecraftThread
	{
		// Token: 0x06000019 RID: 25 RVA: 0x00002343 File Offset: 0x00000543
		public static void StartAllThreads()
		{
			MinecraftThread.ColorRGBManagment.StartColorRGBCounter();
			MinecraftThread.CInputManagment.StartThread();
			MinecraftThread.OverlayManagment.StartOverlay();
			MinecraftThread.MemoryConnecting.StartMemoryConnectingLoop();
		}

		// Token: 0x04000007 RID: 7
		public static string[] args = Environment.GetCommandLineArgs();

		// Token: 0x02000018 RID: 24
		public static class CInputManagment
		{
			// Token: 0x06000114 RID: 276 RVA: 0x00006074 File Offset: 0x00004274
			public static void StartThread()
			{
				CInput.StartThread();
			}
		}

		// Token: 0x02000019 RID: 25
		public static class Application
		{
			// Token: 0x06000115 RID: 277 RVA: 0x0000607D File Offset: 0x0000427D
			public static void InitializeThread()
			{
				MinecraftThread.Application.ConsoleThread = new Thread(new ThreadStart(MinecraftThread.Application.ConsoleMain));
				MinecraftThread.Application.ConsoleThread.SetApartmentState(ApartmentState.STA);
				MinecraftThread.Application.ConsoleThread.Start();
			}

			// Token: 0x06000116 RID: 278 RVA: 0x000060AD File Offset: 0x000042AD
			public static void ConsoleMain()
			{
			}

			// Token: 0x04000079 RID: 121
			public static Thread ConsoleThread;

			// Token: 0x0400007A RID: 122
			public static string[] args = Environment.GetCommandLineArgs();
		}

		// Token: 0x0200001A RID: 26
		public static class OldOverlayManagment
		{
			// Token: 0x06000118 RID: 280 RVA: 0x000060BC File Offset: 0x000042BC
			public static void StartOverlay()
			{
				MinecraftThread.OldOverlayManagment.OverlayThread = new Thread(new ThreadStart(MinecraftThread.OldOverlayManagment.OverlayLoop));
				MinecraftThread.OldOverlayManagment.OverlayThread.SetApartmentState(ApartmentState.STA);
				MinecraftThread.OldOverlayManagment.OverlayThread.Start();
			}

			// Token: 0x06000119 RID: 281 RVA: 0x000060EC File Offset: 0x000042EC
			private static void OverlayLoop()
			{
			}

			// Token: 0x0400007B RID: 123
			public static Thread OverlayThread;
		}

		// Token: 0x0200001B RID: 27
		public static class OverlayManagment
		{
			// Token: 0x0600011A RID: 282 RVA: 0x000060EF File Offset: 0x000042EF
			public static void StartOverlay()
			{
				MinecraftThread.OverlayManagment.OverlayThread = new Thread(new ThreadStart(MinecraftThread.OverlayManagment.OverlayStart));
				MinecraftThread.OverlayManagment.OverlayThread.SetApartmentState(ApartmentState.STA);
				MinecraftThread.OverlayManagment.OverlayThread.Priority = ThreadPriority.AboveNormal;
				MinecraftThread.OverlayManagment.OverlayThread.Start();
			}

			// Token: 0x0600011B RID: 283 RVA: 0x0000612B File Offset: 0x0000432B
			private static void OverlayStart()
			{
				MinecraftThread.OverlayManagment.overlay = new GUI();
				MinecraftThread.OverlayManagment.overlay.InitializeComponent();
				MinecraftThread.OverlayManagment.overlay.Show();
				Dispatcher.Run();
			}

			// Token: 0x0400007C RID: 124
			public static GUI overlay;

			// Token: 0x0400007D RID: 125
			public static Thread OverlayThread;
		}

		// Token: 0x0200001C RID: 28
		public static class ColorRGBManagment
		{
			// Token: 0x0600011C RID: 284 RVA: 0x00006154 File Offset: 0x00004354
			public static void StartColorRGBCounter()
			{
				MinecraftThread.ColorRGBManagment.ColorRGBCounterThread = new Thread(new ThreadStart(MinecraftThread.ColorRGBManagment.ColorRGBCounterLoop));
				MinecraftThread.ColorRGBManagment.ColorRGBCounterThread.Priority = ThreadPriority.BelowNormal;
				MinecraftThread.ColorRGBManagment.ColorRGBCounterThread.Start();
			}

			// Token: 0x0600011D RID: 285 RVA: 0x00006184 File Offset: 0x00004384
			private static void ColorRGBCounterLoop()
			{
				for (;;)
				{
					try
					{
						CommunicationData.GUI.ColorRGBCounter += Data.GUI_Data.ColorRGBCounterSpeed;
						CommunicationData.GUI.ColorRGBCounter = Math.Max(CommunicationData.GUI.ColorRGBCounter, 0.0);
						CommunicationData.GUI.ColorRGBCounter %= 1536.0;
					}
					catch
					{
						CommunicationData.GUI.ColorRGBCounter = 0.0;
					}
					Thread.Sleep(10);
				}
			}

			// Token: 0x0400007E RID: 126
			public static Thread ColorRGBCounterThread;
		}

		// Token: 0x0200001D RID: 29
		public static class MemoryConnecting
		{
			// Token: 0x0600011E RID: 286 RVA: 0x00006204 File Offset: 0x00004404
			public static void StartMemoryConnectingLoop()
			{
				MinecraftThread.MemoryConnecting.MemoryConnectingThread = new Thread(new ThreadStart(MinecraftThread.MemoryConnecting.ReconnectMemoryLoop));
				MinecraftThread.MemoryConnecting.MemoryConnectingThread.Priority = ThreadPriority.Lowest;
				MinecraftThread.MemoryConnecting.MemoryConnectingThread.Start();
			}

			// Token: 0x0600011F RID: 287 RVA: 0x00006234 File Offset: 0x00004434
			private static void ReconnectMemoryLoop()
			{
				for (;;)
				{
					Memory0.mem.ConnectToProcess();
					Thread.Sleep(600);
				}
			}

			// Token: 0x0400007F RID: 127
			public static Thread MemoryConnectingThread;
		}
	}
}
