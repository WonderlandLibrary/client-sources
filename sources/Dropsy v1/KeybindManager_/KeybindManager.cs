using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using Hotkeys;

namespace KeybindManager_
{
	// Token: 0x0200000A RID: 10
	public class KeybindManager
	{
		// Token: 0x06000019 RID: 25 RVA: 0x0000297D File Offset: 0x00000D7D
		public KeybindManager()
		{
			this.dictionary_0 = new Dictionary<Keys, bool>();
			this.list_0 = new List<KeybindManager.keyStatusCallBack>();
			this.list_1 = new List<KeybindManager.keyStatusCallBack>();
			this.timer_0 = new Timer();
			this.method_3();
			this.method_0();
		}

		// Token: 0x0600001A RID: 26 RVA: 0x000029BD File Offset: 0x00000DBD
		private void method_0()
		{
			this.timer_0.Tick += this.timer_0_Tick;
			this.timer_0.Interval = 10;
		}

		// Token: 0x0600001B RID: 27 RVA: 0x000029E4 File Offset: 0x00000DE4
		private void timer_0_Tick(object sender, EventArgs e)
		{
			Dictionary<Keys, bool> dictionary = new Dictionary<Keys, bool>();
			foreach (KeyValuePair<Keys, bool> keyValuePair in this.dictionary_0)
			{
				bool keyState;
				if ((keyState = WinApi.getKeyState(keyValuePair.Key)) != keyValuePair.Value)
				{
					dictionary[keyValuePair.Key] = keyState;
					if (keyState)
					{
						this.method_1(keyValuePair.Key);
					}
					else
					{
						this.method_2(keyValuePair.Key);
					}
				}
			}
			if (dictionary.Count > 0)
			{
				foreach (KeyValuePair<Keys, bool> keyValuePair2 in dictionary)
				{
					this.dictionary_0[keyValuePair2.Key] = keyValuePair2.Value;
				}
			}
		}

		// Token: 0x0600001C RID: 28 RVA: 0x00002AD4 File Offset: 0x00000ED4
		public bool getKeyStatus(Keys key)
		{
			return this.dictionary_0.ContainsKey(key) && this.dictionary_0[key];
		}

		// Token: 0x0600001D RID: 29 RVA: 0x00002AF2 File Offset: 0x00000EF2
		public void start()
		{
			this.timer_0.Enabled = true;
		}

		// Token: 0x0600001E RID: 30 RVA: 0x00002B00 File Offset: 0x00000F00
		public void stop()
		{
			this.timer_0.Enabled = false;
			foreach (object obj in Enum.GetValues(typeof(Keys)))
			{
				Keys key = (Keys)obj;
				this.dictionary_0[key] = false;
			}
		}

		// Token: 0x0600001F RID: 31 RVA: 0x00002B74 File Offset: 0x00000F74
		public void registerKeyToggleCallback(KeybindManager.keyStatusCallBack callback)
		{
			this.list_0.Add(callback);
		}

		// Token: 0x06000020 RID: 32 RVA: 0x00002B82 File Offset: 0x00000F82
		public void registerKeyUnToggleCallback(KeybindManager.keyStatusCallBack callback)
		{
			this.list_1.Add(callback);
		}

		// Token: 0x06000021 RID: 33 RVA: 0x00002B90 File Offset: 0x00000F90
		public void clearKeyToggleCallbacks()
		{
			this.list_0.Clear();
		}

		// Token: 0x06000022 RID: 34 RVA: 0x00002B9D File Offset: 0x00000F9D
		public void clearKeyUnToggleCallbacks()
		{
			this.list_1.Clear();
		}

		// Token: 0x06000023 RID: 35 RVA: 0x00002BAC File Offset: 0x00000FAC
		private void method_1(Keys keys_0)
		{
			if (this.list_0.Count > 0)
			{
				foreach (KeybindManager.keyStatusCallBack keyStatusCallBack in this.list_0.ToList<KeybindManager.keyStatusCallBack>())
				{
					keyStatusCallBack(keys_0);
				}
			}
		}

		// Token: 0x06000024 RID: 36 RVA: 0x00002C10 File Offset: 0x00001010
		private void method_2(Keys keys_0)
		{
			if (this.list_1.Count > 0)
			{
				foreach (KeybindManager.keyStatusCallBack keyStatusCallBack in this.list_1.ToList<KeybindManager.keyStatusCallBack>())
				{
					keyStatusCallBack(keys_0);
				}
			}
		}

		// Token: 0x06000025 RID: 37 RVA: 0x00002C74 File Offset: 0x00001074
		private void method_3()
		{
			foreach (object obj in Enum.GetValues(typeof(Keys)))
			{
				Keys key = (Keys)obj;
				this.dictionary_0[key] = false;
			}
		}

		// Token: 0x06000026 RID: 38 RVA: 0x00002CDC File Offset: 0x000010DC
		public static string getKeyName(Keys key, int maxChar = 0)
		{
			string text = Enum.GetName(typeof(Keys), key);
			if (maxChar > 0 && maxChar <= text.Length)
			{
				text = text.Substring(0, maxChar);
			}
			return text;
		}

		// Token: 0x04000040 RID: 64
		private Dictionary<Keys, bool> dictionary_0;

		// Token: 0x04000041 RID: 65
		private List<KeybindManager.keyStatusCallBack> list_0;

		// Token: 0x04000042 RID: 66
		private List<KeybindManager.keyStatusCallBack> list_1;

		// Token: 0x04000043 RID: 67
		private Timer timer_0;

		// Token: 0x0200000B RID: 11
		// (Invoke) Token: 0x06000028 RID: 40
		public delegate void keyStatusCallBack(Keys key);
	}
}
