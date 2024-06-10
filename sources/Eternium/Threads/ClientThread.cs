using System;
using Eternium_mcpe_client.Threads.GUI;

namespace Eternium_mcpe_client.Threads
{
	// Token: 0x02000009 RID: 9
	public static class ClientThread
	{
		// Token: 0x06000017 RID: 23 RVA: 0x00002322 File Offset: 0x00000522
		public static void Thread()
		{
			ClientThread.Launchclient();
		}

		// Token: 0x06000018 RID: 24 RVA: 0x0000232B File Offset: 0x0000052B
		public static void Launchclient()
		{
			ClientThread.eternium = new Eternium();
			ClientThread.eternium.Show();
		}

		// Token: 0x04000006 RID: 6
		public static Eternium eternium;
	}
}
