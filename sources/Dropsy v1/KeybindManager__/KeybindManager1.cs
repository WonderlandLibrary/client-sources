using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using Hotkeys;

namespace KeybindManager__
{
	// Token: 0x02000021 RID: 33
	public class KeybindManager1
	{
		// Token: 0x060000FE RID: 254 RVA: 0x00011881 File Offset: 0x0000FC81
		public KeybindManager1()
		{
			this.dictionary_0 = new Dictionary<Keys, bool>();
			this.list_0 = new List<KeybindManager1.keyStatusCallBack>();
			this.list_1 = new List<KeybindManager1.keyStatusCallBack>();
			this.timer_0 = new Timer();
			this.method_3();
			this.method_0();
		}

		// Token: 0x060000FF RID: 255 RVA: 0x000118C1 File Offset: 0x0000FCC1
		private void method_0()
		{
			this.timer_0.Tick += this.timer_0_Tick;
			this.timer_0.Interval = 10;
		}

		// Token: 0x06000100 RID: 256 RVA: 0x000118E8 File Offset: 0x0000FCE8
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

		// Token: 0x06000101 RID: 257 RVA: 0x000119D8 File Offset: 0x0000FDD8
		public bool getKeyStatus(Keys key)
		{
			return this.dictionary_0.ContainsKey(key) && this.dictionary_0[key];
		}

		// Token: 0x06000102 RID: 258 RVA: 0x000119F6 File Offset: 0x0000FDF6
		public void start()
		{
			this.timer_0.Enabled = true;
		}

		// Token: 0x06000103 RID: 259 RVA: 0x00011A04 File Offset: 0x0000FE04
		public void stop()
		{
			this.timer_0.Enabled = false;
			foreach (object obj in Enum.GetValues(typeof(Keys)))
			{
				Keys key = (Keys)obj;
				this.dictionary_0[key] = false;
			}
		}

		// Token: 0x06000104 RID: 260 RVA: 0x00011A78 File Offset: 0x0000FE78
		public void registerKeyToggleCallback(KeybindManager1.keyStatusCallBack callback)
		{
			this.list_0.Add(callback);
		}

		// Token: 0x06000105 RID: 261 RVA: 0x00011A86 File Offset: 0x0000FE86
		public void registerKeyUnToggleCallback(KeybindManager1.keyStatusCallBack callback)
		{
			this.list_1.Add(callback);
		}

		// Token: 0x06000106 RID: 262 RVA: 0x00011A94 File Offset: 0x0000FE94
		public void clearKeyToggleCallbacks()
		{
			this.list_0.Clear();
		}

		// Token: 0x06000107 RID: 263 RVA: 0x00011AA1 File Offset: 0x0000FEA1
		public void clearKeyUnToggleCallbacks()
		{
			this.list_1.Clear();
		}

		// Token: 0x06000108 RID: 264 RVA: 0x00011AB0 File Offset: 0x0000FEB0
		private void method_1(Keys keys_0)
		{
			if (this.list_0.Count > 0)
			{
				foreach (KeybindManager1.keyStatusCallBack keyStatusCallBack in this.list_0.ToList<KeybindManager1.keyStatusCallBack>())
				{
					keyStatusCallBack(keys_0);
				}
			}
		}

		// Token: 0x06000109 RID: 265 RVA: 0x00011B14 File Offset: 0x0000FF14
		private void method_2(Keys keys_0)
		{
			if (this.list_1.Count > 0)
			{
				foreach (KeybindManager1.keyStatusCallBack keyStatusCallBack in this.list_1.ToList<KeybindManager1.keyStatusCallBack>())
				{
					keyStatusCallBack(keys_0);
				}
			}
		}

		// Token: 0x0600010A RID: 266 RVA: 0x00011B78 File Offset: 0x0000FF78
		private void method_3()
		{
			foreach (object obj in Enum.GetValues(typeof(Keys)))
			{
				Keys key = (Keys)obj;
				this.dictionary_0[key] = false;
			}
		}

		// Token: 0x0600010B RID: 267 RVA: 0x00011BE0 File Offset: 0x0000FFE0
		public static string getKeyName(Keys key, int maxChar = 0)
		{
			string text = Enum.GetName(typeof(Keys), key);
			if (maxChar > 0 && maxChar <= text.Length)
			{
				text = text.Substring(0, maxChar);
			}
			return text;
		}

		// Token: 0x0400013E RID: 318
		private Dictionary<Keys, bool> dictionary_0;

		// Token: 0x0400013F RID: 319
		private List<KeybindManager1.keyStatusCallBack> list_0;

		// Token: 0x04000140 RID: 320
		private List<KeybindManager1.keyStatusCallBack> list_1;

		// Token: 0x04000141 RID: 321
		private Timer timer_0;

		// Token: 0x02000022 RID: 34
		// (Invoke) Token: 0x0600010D RID: 269
		public delegate void keyStatusCallBack(Keys key);
	}
}
