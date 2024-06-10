using System;
using System.Collections;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;
using GHOSTBYTES.My;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x02000031 RID: 49

	public partial class Form1 : Form
	{
		// Token: 0x06000248 RID: 584 RVA: 0x00003DEA File Offset: 0x00001FEA
		public Form1()
		{
			base.Load += this.Form1_Load;
			this.InitializeComponent();
		}

		// Token: 0x06000249 RID: 585 RVA: 0x0000B068 File Offset: 0x00009268
		private string CpuId()
		{
			string str = ".";
			object objectValue = RuntimeHelpers.GetObjectValue(NewLateBinding.LateGet(RuntimeHelpers.GetObjectValue(Interaction.GetObject("winmgmts:{impersonationLevel=impersonate}!\\\\" + str + "\\root\\cimv2", null)), null, "ExecQuery", new object[]
			{
				"Select * from Win32_Processor"
			}, null, null, null));
			string text = "";
			try
			{
				foreach (object obj in ((IEnumerable)objectValue))
				{
					object objectValue2 = RuntimeHelpers.GetObjectValue(obj);
					text = Conversions.ToString(Operators.ConcatenateObject(text + ", ", NewLateBinding.LateGet(objectValue2, null, "ProcessorId", new object[0], null, null, null)));
				}
			}
			finally
			{
				
			}
			if (text.Length > 0)
			{
				text = text.Substring(2);
			}
			return text;
		}

		// Token: 0x0600024A RID: 586 RVA: 0x00003E0A File Offset: 0x0000200A
		private void FlatButton1_Click(object sender, EventArgs e)
		{
			MyProject.Forms.Form3.Show();
			base.Hide();
		}

		// Token: 0x0600024B RID: 587 RVA: 0x00003E21 File Offset: 0x00002021
		private void FlatButton2_Click(object sender, EventArgs e)
		{
			this.FlatTextbox1.Text = Conversions.ToString(MyProject.Computer.Info.TotalVirtualMemory) + Conversions.ToString(MyProject.Computer.Info.TotalPhysicalMemory);
		}

		// Token: 0x0600024C RID: 588 RVA: 0x00003E5B File Offset: 0x0000205B
		private void Form1_Load(object sender, EventArgs e)
		{
			this.Text = "GHOSTBYTES";
			Thread.Sleep(500);
			this.FlatTextbox1.Text = "Log-In";
			this.FlatTextbox1.ReadOnly = false;
		}

		// Token: 0x0600024D RID: 589 RVA: 0x00003E8E File Offset: 0x0000208E
		private void FlatButton2_Click_1(object sender, EventArgs e)
		{
			MySettingsProperty.Settings.Save();
		}

		// Token: 0x0600024E RID: 590 RVA: 0x00003E9A File Offset: 0x0000209A
		private void FormSkin1_Click(object sender, EventArgs e)
		{
		}

		// Token: 0x0600024F RID: 591 RVA: 0x00003E9C File Offset: 0x0000209C
		private void Button21_Click(object sender, EventArgs e)
		{
			this.FlatTextbox1.ReadOnly = false;
			this.FlatTextbox1.PasswordChar = 'X';
			this.FlatTextbox1.Text = "";
		}

		// Token: 0x06000250 RID: 592 RVA: 0x00003EC7 File Offset: 0x000020C7
		

		// Token: 0x170000C6 RID: 198
		// (get) Token: 0x06000255 RID: 597 RVA: 0x00003EDD File Offset: 0x000020DD
		// (set) Token: 0x06000256 RID: 598 RVA: 0x00003EE5 File Offset: 0x000020E5
		internal virtual FlatClose FlatClose1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000C7 RID: 199
		// (get) Token: 0x06000257 RID: 599 RVA: 0x00003EEE File Offset: 0x000020EE
		// (set) Token: 0x06000258 RID: 600 RVA: 0x0000B754 File Offset: 0x00009954
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

		// Token: 0x170000C8 RID: 200
		// (get) Token: 0x06000259 RID: 601 RVA: 0x00003EF6 File Offset: 0x000020F6
		// (set) Token: 0x0600025A RID: 602 RVA: 0x0000B798 File Offset: 0x00009998
		internal virtual TextBox FlatTextbox1
		{
			[CompilerGenerated]
			get
			{
				return this.FlatTextbox1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.FlatTextbox1_MouseLeave);
				TextBox flatTextbox = this.FlatTextbox1;
				if (flatTextbox != null)
				{
					flatTextbox.MouseLeave -= value2;
				}
				this.FlatTextbox1 = value;
				flatTextbox = this.FlatTextbox1;
				if (flatTextbox != null)
				{
					flatTextbox.MouseLeave += value2;
				}
			}
		}

		// Token: 0x170000C9 RID: 201
		// (get) Token: 0x0600025B RID: 603 RVA: 0x00003EFE File Offset: 0x000020FE
		// (set) Token: 0x0600025C RID: 604 RVA: 0x0000B7DC File Offset: 0x000099DC
		internal virtual Button Button21
		{
			[CompilerGenerated]
			get
			{
				return this.Button21;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.Button21_Click);
				Button button = this.Button21;
				if (button != null)
				{
					button.Click -= value2;
				}
				this.Button21 = value;
				button = this.Button21;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}
	}
}
