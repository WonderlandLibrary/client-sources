using Microsoft.VisualBasic.CompilerServices;
using System;
using System.Drawing;
using System.Windows.Forms;
using Xh0kO1ZCmA.My;

namespace Xh0kO1ZCmA
{
	public class cSettings
	{
		public static cConfig cfg;

		private Color randomlul4;

		public string Theme;

		public bool ColorCycle;

		public int MinCPS;

		public int MaxCPS;

		public bool Click;

		public string ClickToggle;

		public bool Jitter;

		public int JitterVertical;

		public int JitterHorizontal;

		public int JitterSmoothness;

		public bool Randomize;

		public bool MCOnly;

		public bool BreakBlocks;

		public bool ClickSounds;

		public string ClickSoundsType;

		public bool CPSDrops;

		public int CPSDropsValue;

		public bool SmartCPSDrops;

		public bool BlockHit;

		public bool IgnoreBindsInChat;

		public string ChatKey;

		public string HideFromTaskbarKey;

		public bool WTap;

		public int DBW;

		public int DBWW;

		public bool ThrowPot;

		public string ThrowPotToggleKey;

		public string ThrowPotInvKey;

		public int ThrowPotFirstPot;

		public int ThrowPotLastPot;

		public int ThrowPotSwordSlot;

		public bool DropBowls;

		public string DropBowlsDropKey;

		public bool RodTrick;

		public int DBSS;

		public string RodTrickToggleKey;

		public int RodTrickRodSlot;

		public int RodTrickSwordSlot;

		public bool SimulateHumanizedClicks;

		public int MaxDelay;

		public int MinRDelay;

		public int MaxRDelay;

		public string AverageCPS;

		public string MinimumCPS;

		public string MaximumCPS;

		static cSettings()
		{
			cSettings.cfg = new cConfig(string.Concat("C:\\Users\\", Environment.UserName, "\\AppData\\Local\\Temp\\EYrY9.ini"));
		}

		public cSettings()
		{
			this.randomlul4 = Color.FromArgb(255, 42, 42, 42);
		}

		public object Load()
		{
			object obj = null;
			try
			{
				this.Theme = Conversions.ToString(cSettings.cfg.read("MXFN7a6aJX", "Theme"));
				this.ColorCycle = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "ColorCycle"));
				this.MinCPS = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "MinCPS"));
				this.MaxCPS = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "MaxCPS"));
				this.Click = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "Click"));
				this.Jitter = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "Jitter"));
				this.Randomize = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "Randomize"));
				this.MCOnly = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "MCOnly"));
				this.BreakBlocks = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "BreakBlocks"));
				this.ClickToggle = Conversions.ToString(cSettings.cfg.read("MXFN7a6aJX", "ClickToggle"));
				this.JitterVertical = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "JitterVertical"));
				this.JitterHorizontal = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "JitterHorizontal"));
				this.JitterSmoothness = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "JitterSmoothness"));
				this.ClickSounds = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "ClickSounds"));
				this.ClickSoundsType = Conversions.ToString(cSettings.cfg.read("MXFN7a6aJX", "ClickSoundsType"));
				this.CPSDrops = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "CPSDrops"));
				this.CPSDropsValue = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "CPSDropsValue"));
				this.SmartCPSDrops = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "SmartCPSDrops"));
				this.BlockHit = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "BlockHit"));
				this.SimulateHumanizedClicks = Conversions.ToBoolean(cSettings.cfg.read("MXFN7a6aJX", "SimulateHumanizedClicks"));
				this.MaxDelay = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "MaxDelay"));
				this.MinRDelay = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "MinRDelay"));
				this.MaxRDelay = Conversions.ToInteger(cSettings.cfg.read("MXFN7a6aJX", "MaxRDelay"));
				this.AverageCPS = Conversions.ToString(cSettings.cfg.read("MXFN7a6aJX", "AverageCPS"));
				this.MinimumCPS = Conversions.ToString(cSettings.cfg.read("MXFN7a6aJX", "MinimumCPS"));
				this.MaximumCPS = Conversions.ToString(cSettings.cfg.read("MXFN7a6aJX", "MaximumCPS"));
				this.IgnoreBindsInChat = Conversions.ToBoolean(cSettings.cfg.read("SZXth5rJub", "IgnoreBindsInChat"));
				this.ChatKey = Conversions.ToString(cSettings.cfg.read("SZXth5rJub", "ChatKey"));
				this.HideFromTaskbarKey = Conversions.ToString(cSettings.cfg.read("SZXth5rJub", "HideFromTaskbarKey"));
				this.WTap = Conversions.ToBoolean(cSettings.cfg.read("0FkDE4QwnJ", "WTap"));
				this.DBW = Conversions.ToInteger(cSettings.cfg.read("0FkDE4QwnJ", "DBW"));
				this.DBWW = Conversions.ToInteger(cSettings.cfg.read("0FkDE4QwnJ", "DBWW"));
				this.ThrowPot = Conversions.ToBoolean(cSettings.cfg.read("nkNyMDyrbT", "ThrowPot"));
				this.ThrowPotToggleKey = Conversions.ToString(cSettings.cfg.read("nkNyMDyrbT", "ThrowPotToggleKey"));
				this.ThrowPotInvKey = Conversions.ToString(cSettings.cfg.read("nkNyMDyrbT", "ThrowPotInvKey"));
				this.ThrowPotFirstPot = Conversions.ToInteger(cSettings.cfg.read("nkNyMDyrbT", "ThrowPotFirstPot"));
				this.ThrowPotLastPot = Conversions.ToInteger(cSettings.cfg.read("nkNyMDyrbT", "ThrowPotLastPot"));
				this.ThrowPotSwordSlot = Conversions.ToInteger(cSettings.cfg.read("nkNyMDyrbT", "ThrowPotSwordSlot"));
				this.DropBowls = Conversions.ToBoolean(cSettings.cfg.read("nkNyMDyrbT", "DropBowls"));
				this.DropBowlsDropKey = Conversions.ToString(cSettings.cfg.read("nkNyMDyrbT", "DropBowlsDropKey"));
				this.RodTrick = Conversions.ToBoolean(cSettings.cfg.read("pYjxdrjerq", "RodTrick"));
				this.DBSS = Conversions.ToInteger(cSettings.cfg.read("pYjxdrjerq", "DBSS"));
				this.RodTrickToggleKey = Conversions.ToString(cSettings.cfg.read("pYjxdrjerq", "RodTrickToggleKey"));
				this.RodTrickRodSlot = Conversions.ToInteger(cSettings.cfg.read("pYjxdrjerq", "RodTrickRodSlot"));
				this.RodTrickSwordSlot = Conversions.ToInteger(cSettings.cfg.read("pYjxdrjerq", "RodTrickSwordSlot"));
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				ProjectData.ClearProjectError();
			}
			return obj;
		}

		public object Save()
		{
			object obj = null;
			if (MyProject.Forms.Form1.BackColor != this.randomlul4)
			{
				cSettings.cfg.write("MXFN7a6aJX", "Theme", "Light");
			}
			else
			{
				cSettings.cfg.write("MXFN7a6aJX", "Theme", "Dark");
			}
			if (MyProject.Forms.Form1.ExitLoop)
			{
				cSettings.cfg.write("MXFN7a6aJX", "ColorCycle", "False");
			}
			else
			{
				cSettings.cfg.write("MXFN7a6aJX", "ColorCycle", "True");
			}
			cSettings.cfg.write("MXFN7a6aJX", "MinCPS", Conversions.ToString(MyProject.Forms.Form1.TrackBar1.Value));
			cSettings.cfg.write("MXFN7a6aJX", "MaxCPS", Conversions.ToString(MyProject.Forms.Form1.TrackBar2.Value));
			cSettings.cfg.write("MXFN7a6aJX", "Click", Conversions.ToString(MyProject.Forms.Form1.FlatToggle1.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "Jitter", Conversions.ToString(MyProject.Forms.Form1.FlatToggle2.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "Randomize", Conversions.ToString(MyProject.Forms.Form1.FlatToggle3.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "MCOnly", Conversions.ToString(MyProject.Forms.Form1.FlatToggle4.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "BreakBlocks", Conversions.ToString(MyProject.Forms.Form1.FlatToggle5.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "ClickToggle", MyProject.Forms.Form1.ComboBox8.Text);
			cSettings.cfg.write("MXFN7a6aJX", "JitterVertical", MyProject.Forms.Form1.ComboBox2.Text);
			cSettings.cfg.write("MXFN7a6aJX", "JitterHorizontal", MyProject.Forms.Form1.ComboBox12.Text);
			cSettings.cfg.write("MXFN7a6aJX", "JitterSmoothness", MyProject.Forms.Form1.ComboBox13.Text);
			cSettings.cfg.write("MXFN7a6aJX", "ClickSounds", Conversions.ToString(MyProject.Forms.Form1.FlatToggle6.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "ClickSoundsType", MyProject.Forms.Form1.ComboBox3.Text);
			cSettings.cfg.write("MXFN7a6aJX", "CPSDrops", Conversions.ToString(MyProject.Forms.Form1.FlatToggle9.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "CPSDropsValue", MyProject.Forms.Form1.ComboBox1.Text);
			cSettings.cfg.write("MXFN7a6aJX", "SmartCPSDrops", Conversions.ToString(MyProject.Forms.Form1.FlatToggle8.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "BlockHit", Conversions.ToString(MyProject.Forms.Form1.FlatToggle7.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "SimulateHumanizedClicks", Conversions.ToString(MyProject.Forms.Form1.FlatToggle31.Checked));
			cSettings.cfg.write("MXFN7a6aJX", "MaxDelay", Conversions.ToString(MyProject.Forms.Form1.FlatTrackBar4.Value));
			cSettings.cfg.write("MXFN7a6aJX", "MinRDelay", Conversions.ToString(MyProject.Forms.Form1.ClicksMin));
			cSettings.cfg.write("MXFN7a6aJX", "MaxRDelay", Conversions.ToString(MyProject.Forms.Form1.ClicksMax));
			cSettings.cfg.write("MXFN7a6aJX", "AverageCPS", MyProject.Forms.Form1.NsLabel30.Value2);
			cSettings.cfg.write("MXFN7a6aJX", "MinimumCPS", MyProject.Forms.Form1.NsLabel33.Value2);
			cSettings.cfg.write("MXFN7a6aJX", "MaximumCPS", MyProject.Forms.Form1.NsLabel32.Value2);
			cSettings.cfg.write("SZXth5rJub", "IgnoreBindsInChat", Conversions.ToString(MyProject.Forms.Form1.FlatToggle22.Checked));
			cSettings.cfg.write("SZXth5rJub", "ChatKey", MyProject.Forms.Form1.ComboBox14.Text);
			cSettings.cfg.write("SZXth5rJub", "HideFromTaskbarKey", MyProject.Forms.Form1.ComboBox16.Text);
			cSettings.cfg.write("0FkDE4QwnJ", "WTap", Conversions.ToString(MyProject.Forms.Form1.FlatToggle10.Checked));
			cSettings.cfg.write("0FkDE4QwnJ", "DBW", Conversions.ToString(MyProject.Forms.Form1.FlatTrackBar1.Value));
			cSettings.cfg.write("0FkDE4QwnJ", "DBWW", Conversions.ToString(MyProject.Forms.Form1.FlatTrackBar2.Value));
			cSettings.cfg.write("nkNyMDyrbT", "ThrowPot", Conversions.ToString(MyProject.Forms.Form1.FlatToggle11.Checked));
			cSettings.cfg.write("nkNyMDyrbT", "ThrowPotToggleKey", MyProject.Forms.Form1.ComboBox6.Text);
			cSettings.cfg.write("nkNyMDyrbT", "ThrowPotInvKey", MyProject.Forms.Form1.ComboBox7.Text);
			cSettings.cfg.write("nkNyMDyrbT", "ThrowPotFirstPot", MyProject.Forms.Form1.ComboBox4.Text);
			cSettings.cfg.write("nkNyMDyrbT", "ThrowPotLastPot", MyProject.Forms.Form1.ComboBox5.Text);
			cSettings.cfg.write("nkNyMDyrbT", "ThrowPotSwordSlot", MyProject.Forms.Form1.ComboBox11.Text);
			cSettings.cfg.write("nkNyMDyrbT", "DropBowls", Conversions.ToString(MyProject.Forms.Form1.FlatToggle13.Checked));
			cSettings.cfg.write("nkNyMDyrbT", "DropBowlsDropKey", MyProject.Forms.Form1.ComboBox17.Text);
			cSettings.cfg.write("pYjxdrjerq", "RodTrick", Conversions.ToString(MyProject.Forms.Form1.FlatToggle12.Checked));
			cSettings.cfg.write("pYjxdrjerq", "DBSS", Conversions.ToString(MyProject.Forms.Form1.FlatTrackBar1.Value));
			cSettings.cfg.write("pYjxdrjerq", "RodTrickToggleKey", MyProject.Forms.Form1.ComboBox9.Text);
			cSettings.cfg.write("pYjxdrjerq", "RodTrickRodSlot", MyProject.Forms.Form1.ComboBox15.Text);
			cSettings.cfg.write("pYjxdrjerq", "RodTrickSwordSlot", MyProject.Forms.Form1.ComboBox10.Text);
			return obj;
		}
	}
}