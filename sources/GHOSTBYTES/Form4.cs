using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Runtime.CompilerServices;
using System.Windows.Forms;
using GHOSTBYTES.My.Resources;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x02000030 RID: 48

	public partial class Form4 : Form
	{
		// Token: 0x06000241 RID: 577 RVA: 0x00003DBA File Offset: 0x00001FBA
		public Form4()
		{
			this.InitializeComponent();
		}

		// Token: 0x170000C3 RID: 195
		// (get) Token: 0x06000244 RID: 580 RVA: 0x00003DC8 File Offset: 0x00001FC8
		// (set) Token: 0x06000245 RID: 581 RVA: 0x00003DD0 File Offset: 0x00001FD0
		internal virtual FormSkin FormSkin1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }

		// Token: 0x170000C4 RID: 196
		// (get) Token: 0x06000246 RID: 582 RVA: 0x00003DD9 File Offset: 0x00001FD9
		// (set) Token: 0x06000247 RID: 583 RVA: 0x00003DE1 File Offset: 0x00001FE1
		internal virtual PictureBox PictureBox1 { get; [MethodImpl(MethodImplOptions.Synchronized)] set; }
	}
}
