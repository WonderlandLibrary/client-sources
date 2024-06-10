using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Management;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using Muphy.My;
using Muphy.My.Resources;
using Siticone.UI.WinForms;

namespace Muphy
{
	// Token: 0x02000010 RID: 16
	[DesignerGenerated]
	public partial class hwidacess : Form
	{
		// Token: 0x0600013B RID: 315 RVA: 0x00002A88 File Offset: 0x00000C88
		public hwidacess()
		{
			base.Load += this.Form1_Load;
			this.InitializeComponent();
		}

		// Token: 0x17000084 RID: 132
		// (get) Token: 0x0600013E RID: 318 RVA: 0x00002AA8 File Offset: 0x00000CA8
		// (set) Token: 0x0600013F RID: 319 RVA: 0x00008D9C File Offset: 0x00006F9C
		internal virtual SiticoneButton Button1
		{
			[CompilerGenerated]
			get
			{
				return this._Button1;
			}
			[CompilerGenerated]
			[MethodImpl(MethodImplOptions.Synchronized)]
			set
			{
				EventHandler value2 = new EventHandler(this.button1_Click);
				SiticoneButton button = this._Button1;
				if (button != null)
				{
					button.Click -= value2;
				}
				this._Button1 = value;
				button = this._Button1;
				if (button != null)
				{
					button.Click += value2;
				}
			}
		}

		// Token: 0x17000085 RID: 133
		// (get) Token: 0x06000140 RID: 320 RVA: 0x00002AB0 File Offset: 0x00000CB0
		// (set) Token: 0x06000141 RID: 321 RVA: 0x00002AB8 File Offset: 0x00000CB8
		internal virtual TextBox TextBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000086 RID: 134
		// (get) Token: 0x06000142 RID: 322 RVA: 0x00002AC1 File Offset: 0x00000CC1
		// (set) Token: 0x06000143 RID: 323 RVA: 0x00002AC9 File Offset: 0x00000CC9
		internal virtual SiticoneDragControl SiticoneDragControl1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000087 RID: 135
		// (get) Token: 0x06000144 RID: 324 RVA: 0x00002AD2 File Offset: 0x00000CD2
		// (set) Token: 0x06000145 RID: 325 RVA: 0x00002ADA File Offset: 0x00000CDA
		internal virtual PictureBox PictureBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x17000088 RID: 136
		// (get) Token: 0x06000146 RID: 326 RVA: 0x00002AE3 File Offset: 0x00000CE3
		// (set) Token: 0x06000147 RID: 327 RVA: 0x00002AEB File Offset: 0x00000CEB
		internal virtual SiticoneDragControl SiticoneDragControl2 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x06000148 RID: 328 RVA: 0x00002AF4 File Offset: 0x00000CF4
		private void Form1_Load(object sender, EventArgs e)
		{
			bool isAvailable = MyProject.Computer.Network.IsAvailable;
			base.AcceptButton = this.Button1;
		}

		// Token: 0x06000149 RID: 329 RVA: 0x00008DE0 File Offset: 0x00006FE0
		public object GetHWID()
		{
			ManagementClass managementClass = new ManagementClass("win32_processor");
			ManagementObjectCollection instances = managementClass.GetInstances();
			try
			{
				foreach (ManagementBaseObject managementBaseObject in instances)
				{
					ManagementObject managementObject = (ManagementObject)managementBaseObject;
					if (Operators.CompareString(this.cpuInfo, "", false) == 0)
					{
						this.cpuInfo = managementObject.Properties["processorID"].Value.ToString();
						break;
					}
				}
			}
			finally
			{
				ManagementObjectCollection.ManagementObjectEnumerator enumerator;
				if (enumerator != null)
				{
					((IDisposable)enumerator).Dispose();
				}
			}
			return this.cpuInfo;
		}

		// Token: 0x0600014A RID: 330 RVA: 0x00002B12 File Offset: 0x00000D12
		private void button1_Click(object sender, EventArgs e)
		{
			Interaction.MsgBox("Cracked by your boy vroom#0002", MsgBoxStyle.OkOnly, null);
			MyProject.Forms.client.Show();
			base.Hide();
		}

		// Token: 0x04000086 RID: 134
		private string cpuInfo;
	}
}
