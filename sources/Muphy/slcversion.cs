using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;
using Muphy.My;
using Muphy.My.Resources;
using Siticone.UI.WinForms;

namespace Muphy
{
	// Token: 0x02000014 RID: 20
	[DesignerGenerated]
	public partial class slcversion : Form
	{
		// Token: 0x060001A9 RID: 425 RVA: 0x00002DD7 File Offset: 0x00000FD7
		public slcversion()
		{
			base.Load += this.slcversion_Load;
			this.InitializeComponent();
		}

		// Token: 0x170000AC RID: 172
		// (get) Token: 0x060001AC RID: 428 RVA: 0x00002DF7 File Offset: 0x00000FF7
		// (set) Token: 0x060001AD RID: 429 RVA: 0x0000AF28 File Offset: 0x00009128
		internal virtual SiticoneButton SiticoneButton1
		{
			[CompilerGenerated]
			get
			{
				return this._SiticoneButton1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.SiticoneButton1_Click);
				SiticoneButton siticoneButton = this._SiticoneButton1;
				if (siticoneButton != null)
				{
					siticoneButton.Click -= value2;
				}
				this._SiticoneButton1 = value;
				siticoneButton = this._SiticoneButton1;
				if (siticoneButton != null)
				{
					siticoneButton.Click += value2;
				}
			}
		}

		// Token: 0x170000AD RID: 173
		// (get) Token: 0x060001AE RID: 430 RVA: 0x00002DFF File Offset: 0x00000FFF
		// (set) Token: 0x060001AF RID: 431 RVA: 0x00002E07 File Offset: 0x00001007
		internal virtual PictureBox PictureBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000AE RID: 174
		// (get) Token: 0x060001B0 RID: 432 RVA: 0x00002E10 File Offset: 0x00001010
		// (set) Token: 0x060001B1 RID: 433 RVA: 0x0000AF6C File Offset: 0x0000916C
		internal virtual Timer Timer1
		{
			[CompilerGenerated]
			get
			{
				return this._Timer1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer1_Tick);
				Timer timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this._Timer1 = value;
				timer = this._Timer1;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x060001B2 RID: 434
		[DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
		public static extern int FindWindow(int IpClassname, [MarshalAs(UnmanagedType.VBByRefStr)] ref string IpWindownName);

		// Token: 0x060001B3 RID: 435 RVA: 0x00002521 File Offset: 0x00000721
		private void slcversion_Load(object sender, EventArgs e)
		{
		}

		// Token: 0x060001B4 RID: 436 RVA: 0x0000AFB0 File Offset: 0x000091B0
		private void SiticoneButton1_Click(object sender, EventArgs e)
		{
			int ipClassname = 0;
			string text = Conversions.ToString(Conversions.ToLong("V Client") | Conversions.ToLong("Lunar Client") | Conversions.ToLong("CheatBreaker") | Conversions.ToLong("Cheat Breaker") | Conversions.ToLong("Minecraft 1.7.10") | Conversions.ToLong("Minecraft 1.8.9") | Conversions.ToLong("Sosa Client") | Conversions.ToLong("SosaClient"));
			slcversion.FindWindow(ipClassname, ref text);
		}

		// Token: 0x060001B5 RID: 437 RVA: 0x00002E18 File Offset: 0x00001018
		private void Timer1_Tick(object sender, EventArgs e)
		{
			base.Hide();
			MyProject.Forms.client.Show();
			this.Timer1.Stop();
		}
	}
}
