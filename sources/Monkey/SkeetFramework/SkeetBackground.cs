using System;
using System.Drawing;
using System.Windows.Forms;

namespace SkeetFramework
{
	// Token: 0x02000002 RID: 2
	public class SkeetBackground : ContainerControl
	{
		// Token: 0x06000003 RID: 3 RVA: 0x00002058 File Offset: 0x00000058
		protected override void OnPaint(PaintEventArgs e)
		{
		}

		// Token: 0x06000004 RID: 4 RVA: 0x000021F0 File Offset: 0x000001F0
		protected override void OnHandleCreated(EventArgs e)
		{
		}

		// Token: 0x04000001 RID: 1
		public Color ColorRight;

		// Token: 0x04000002 RID: 2
		public Color ColorLeft;

		// Token: 0x04000003 RID: 3
		public Color ColorMiddle;

		// Token: 0x04000004 RID: 4
		public Form Form;

		// Token: 0x04000005 RID: 5
		private bool mouseDown;

		// Token: 0x04000006 RID: 6
		private Point lastLocation;
	}
}
