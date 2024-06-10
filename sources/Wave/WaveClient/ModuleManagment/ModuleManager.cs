using System;
using System.Threading;
using WaveClient.Module;

namespace WaveClient.ModuleManagment
{
	// Token: 0x02000012 RID: 18
	public static class ModuleManager
	{
		// Token: 0x02000029 RID: 41
		public static class MemoryUpdate
		{
			// Token: 0x06000118 RID: 280 RVA: 0x00004368 File Offset: 0x00002568
			public static void Tick10()
			{
				while (!ModuleManager.MemoryUpdate.ExitTickThread)
				{
					if (AirJump.ToggleState)
					{
						AirJump.Tick10();
					}
					if (NoFall.ToggleState)
					{
						NoFall.Tick10();
					}
					if (Instabreak.ToggleState)
					{
						Instabreak.Tick10();
					}
					if (Reach.ToggleState)
					{
						Reach.Tick10();
					}
					if (NoSwing.ToggleState)
					{
						NoSwing.Tick10();
					}
					if (Coords.ToggleState)
					{
						Coords.Tick10();
					}
					if (Speed.ToggleState)
					{
						Speed.Tick100();
					}
					if (AutoSprint.ToggleState)
					{
						AutoSprint.Tick10();
					}
					bool toggleState = AirJump.ToggleState;
					bool toggleState2 = NoFall.ToggleState;
					if (!Instabreak.ToggleState)
					{
						Instabreak.Disable();
					}
					if (!Reach.ToggleState)
					{
						Reach.Disable();
					}
					if (!NoSwing.ToggleState)
					{
						NoSwing.Disable();
					}
					if (!Coords.ToggleState)
					{
						Coords.Disable();
					}
					bool toggleState3 = Speed.ToggleState;
					bool toggleState4 = AutoSprint.ToggleState;
				}
			}

			// Token: 0x06000119 RID: 281 RVA: 0x0000442C File Offset: 0x0000262C
			public static void Tick100()
			{
				while (!ModuleManager.MemoryUpdate.ExitTickThread)
				{
					Thread.Sleep(100);
				}
			}

			// Token: 0x0600011A RID: 282 RVA: 0x0000443E File Offset: 0x0000263E
			public static void Tick1000()
			{
				while (!ModuleManager.MemoryUpdate.ExitTickThread)
				{
					Thread.Sleep(1000);
				}
			}

			// Token: 0x0600011B RID: 283 RVA: 0x00004454 File Offset: 0x00002654
			public static void StartTickThread()
			{
				ModuleManager.MemoryUpdate.ThreadTimerTick10 = new Thread(new ThreadStart(ModuleManager.MemoryUpdate.Tick10));
				ModuleManager.MemoryUpdate.ThreadTimerTick100 = new Thread(new ThreadStart(ModuleManager.MemoryUpdate.Tick100));
				ModuleManager.MemoryUpdate.ThreadTimerTick1000 = new Thread(new ThreadStart(ModuleManager.MemoryUpdate.Tick1000));
				ModuleManager.MemoryUpdate.ThreadTimerTick10.Priority = ThreadPriority.Normal;
				ModuleManager.MemoryUpdate.ThreadTimerTick100.Priority = ThreadPriority.Normal;
				ModuleManager.MemoryUpdate.ThreadTimerTick1000.Priority = ThreadPriority.BelowNormal;
				ModuleManager.MemoryUpdate.ThreadTimerTick10.Start();
				ModuleManager.MemoryUpdate.ThreadTimerTick100.Start();
				ModuleManager.MemoryUpdate.ThreadTimerTick1000.Start();
			}

			// Token: 0x0600011C RID: 284 RVA: 0x000044E2 File Offset: 0x000026E2
			public static void RestartTickThread()
			{
				ModuleManager.MemoryUpdate.ThreadTimerTick10.Abort();
				ModuleManager.MemoryUpdate.ThreadTimerTick100.Abort();
				ModuleManager.MemoryUpdate.ThreadTimerTick1000.Abort();
				ModuleManager.MemoryUpdate.StartTickThread();
			}

			// Token: 0x04000072 RID: 114
			public static bool ExitTickThread;

			// Token: 0x04000073 RID: 115
			private static Thread ThreadTimerTick10;

			// Token: 0x04000074 RID: 116
			private static Thread ThreadTimerTick100;

			// Token: 0x04000075 RID: 117
			private static Thread ThreadTimerTick1000;
		}
	}
}
