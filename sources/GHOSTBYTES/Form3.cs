using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using GHOSTBYTES.My;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x0200003C RID: 60
	[DesignerGenerated]
	public partial class Form3 : Form
	{
		// Token: 0x06000536 RID: 1334 RVA: 0x00005432 File Offset: 0x00003632
		public Form3()
		{
			base.Load += this.Form3_Load;
			this.InitializeComponent();
		}

		// Token: 0x170001E3 RID: 483
		// (get) Token: 0x06000539 RID: 1337 RVA: 0x00005452 File Offset: 0x00003652
		// (set) Token: 0x0600053A RID: 1338 RVA: 0x00023264 File Offset: 0x00021464
		internal virtual FormSkin FormSkin1
		{
			[CompilerGenerated]
			get
			{
				return this.FormSkin1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FormSkin1_Click);
				FormSkin formSkin = this.FormSkin1;
				if (formSkin != null)
				{
					formSkin.Click -= value2;
				}
				this.FormSkin1 = value;
				formSkin = this.FormSkin1;
				if (formSkin != null)
				{
					formSkin.Click += value2;
				}
			}
		}

		// Token: 0x170001E4 RID: 484
		// (get) Token: 0x0600053B RID: 1339 RVA: 0x0000545A File Offset: 0x0000365A
		// (set) Token: 0x0600053C RID: 1340 RVA: 0x00005462 File Offset: 0x00003662
		internal virtual FlatComboBox FlatComboBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001E5 RID: 485
		// (get) Token: 0x0600053D RID: 1341 RVA: 0x0000546B File Offset: 0x0000366B
		// (set) Token: 0x0600053E RID: 1342 RVA: 0x000232A8 File Offset: 0x000214A8
		internal virtual FlatButton FlatButton1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatButton1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatButton1_Click);
				FlatButton flatButton = this.FlatButton1;
				if (flatButton != null)
				{
					flatButton.Click -= value2;
				}
				this.FlatButton1 = value;
				flatButton = this.FlatButton1;
				if (flatButton != null)
				{
					flatButton.Click += value2;
				}
			}
		}

		// Token: 0x170001E6 RID: 486
		// (get) Token: 0x0600053F RID: 1343 RVA: 0x00005473 File Offset: 0x00003673
		// (set) Token: 0x06000540 RID: 1344 RVA: 0x000232EC File Offset: 0x000214EC
		internal virtual FlatClose FlatClose1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatClose1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatClose1_Click);
				FlatClose flatClose = this.FlatClose1;
				if (flatClose != null)
				{
					flatClose.Click -= value2;
				}
				this.FlatClose1 = value;
				flatClose = this.FlatClose1;
				if (flatClose != null)
				{
					flatClose.Click += value2;
				}
			}
		}

		// Token: 0x170001E7 RID: 487
		// (get) Token: 0x06000541 RID: 1345 RVA: 0x0000547B File Offset: 0x0000367B
		// (set) Token: 0x06000542 RID: 1346 RVA: 0x00023330 File Offset: 0x00021530
		internal virtual FlatProgressBar FlatProgressBar1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatProgressBar1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatProgressBar1_Click);
				EventHandler value3 = new EventHandler(this.FlatProgressBar1_VisibleChanged);
				FlatProgressBar flatProgressBar = this.FlatProgressBar1;
				if (flatProgressBar != null)
				{
					flatProgressBar.Click -= value2;
					flatProgressBar.VisibleChanged -= value3;
				}
				this.FlatProgressBar1 = value;
				flatProgressBar = this.FlatProgressBar1;
				if (flatProgressBar != null)
				{
					flatProgressBar.Click += value2;
					flatProgressBar.VisibleChanged += value3;
				}
			}
		}

		// Token: 0x170001E8 RID: 488
		// (get) Token: 0x06000543 RID: 1347 RVA: 0x00005483 File Offset: 0x00003683
		// (set) Token: 0x06000544 RID: 1348 RVA: 0x0000548B File Offset: 0x0000368B
		internal virtual FlatLabel injectionlabel { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170001E9 RID: 489
		// (get) Token: 0x06000545 RID: 1349 RVA: 0x00005494 File Offset: 0x00003694
		// (set) Token: 0x06000546 RID: 1350 RVA: 0x00023390 File Offset: 0x00021590
		internal virtual Timer Timer1
		{
			[CompilerGenerated]
			get
			{
				return this.Timer1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Timer1_Tick);
				Timer timer = this.Timer1;
				if (timer != null)
				{
					timer.Tick -= value2;
				}
				this.Timer1 = value;
				timer = this.Timer1;
				if (timer != null)
				{
					timer.Tick += value2;
				}
			}
		}

		// Token: 0x06000547 RID: 1351 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FormSkin1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x06000548 RID: 1352 RVA: 0x000233D4 File Offset: 0x000215D4
		private void Form3_Load(object sender, EventArgs e)
		{
			this.Timer1.Interval = 200;
			this.Text = "GHOSTBYTES";
			foreach (Process process in Process.GetProcesses())
			{
				this.FlatComboBox1.Items.Add(process.ProcessName);
			}
		}

		// Token: 0x06000549 RID: 1353 RVA: 0x00004FE6 File Offset: 0x000031E6
		[MethodImpl(MethodImplOptions.NoInlining | MethodImplOptions.NoOptimization)]
		private void FlatClose1_Click(object sender, EventArgs e)
		{
			ProjectData.EndApp();
		}

		// Token: 0x0600054A RID: 1354 RVA: 0x0002342C File Offset: 0x0002162C
		private void FlatButton1_Click(object sender, EventArgs e)
		{
			this.FlatComboBox1.Text = Conversions.ToString(this.FlatComboBox1.SelectedItem);
			if (this.FlatComboBox1.Text.Length > 1)
			{
				this.Timer1.Start();
				this.injectionlabel.Hide();
				this.FlatProgressBar1.Show();
				return;
			}
			Interaction.MsgBox("No Process Selected", MsgBoxStyle.Critical, "Error");
		}

		// Token: 0x0600054B RID: 1355 RVA: 0x0002349C File Offset: 0x0002169C
		private void Timer1_Tick(object sender, EventArgs e)
		{
			checked
			{
				if (this.FlatProgressBar1.Value <= this.FlatProgressBar1.Maximum - 1)
				{
					FlatProgressBar flatProgressBar;
					(flatProgressBar = this.FlatProgressBar1).Value = flatProgressBar.Value + 1;
				}
				if (this.FlatProgressBar1.Value == 100)
				{
					this.FlatProgressBar1.Hide();
				}
			}
		}

		// Token: 0x0600054C RID: 1356 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FlatProgressBar1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600054D RID: 1357 RVA: 0x0000549C File Offset: 0x0000369C
		private void FlatProgressBar1_VisibleChanged(object sender, EventArgs e)
		{
			if (this.FlatProgressBar1.Value == 100)
			{
				Interaction.MsgBox("Success!", MsgBoxStyle.Information, "Success");
				MyProject.Forms.Form2.Show();
				base.Hide();
			}
		}
	}
}
