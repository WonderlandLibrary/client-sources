using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace FlatUI
{
	// Token: 0x0200000E RID: 14
	public class checkBoxes : ContainerControl
	{
		// Token: 0x1700002E RID: 46
		// (get) Token: 0x060000A0 RID: 160 RVA: 0x00004DEC File Offset: 0x00002DEC
		// (set) Token: 0x060000A1 RID: 161 RVA: 0x00004E04 File Offset: 0x00002E04
		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x1700002F RID: 47
		// (get) Token: 0x060000A2 RID: 162 RVA: 0x00004E10 File Offset: 0x00002E10
		// (set) Token: 0x060000A3 RID: 163 RVA: 0x00004E28 File Offset: 0x00002E28
		public bool ShowText
		{
			get
			{
			}
			set
			{
			}
		}

		// Token: 0x060000A5 RID: 165 RVA: 0x00004EBC File Offset: 0x00002EBC
		protected override void OnPaint(PaintEventArgs e)
		{
		}

		// Token: 0x060000A6 RID: 166 RVA: 0x00004FAC File Offset: 0x00002FAC
		private void UpdateColors()
		{
		}

		// Token: 0x04000051 RID: 81
		private int W;

		// Token: 0x04000052 RID: 82
		private int H;

		// Token: 0x04000053 RID: 83
		private bool _ShowText;

		// Token: 0x04000054 RID: 84
		private Color _BaseColor;

		// Token: 0x04000055 RID: 85
		private Color _TextColor;
	}
}
