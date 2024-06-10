using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Meth
{
	// Token: 0x02000029 RID: 41
	internal class FlatContextMenuStrip : ContextMenuStrip
	{
		// Token: 0x060002F0 RID: 752 RVA: 0x0000D7C2 File Offset: 0x0000B9C2
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x060002F1 RID: 753 RVA: 0x00014A15 File Offset: 0x00012C15
		public FlatContextMenuStrip()
		{
			base.Renderer = new ToolStripProfessionalRenderer(new FlatContextMenuStrip.TColorTable());
			base.ShowImageMargin = false;
			base.ForeColor = Color.White;
			this.Font = new Font("Segoe UI", 8f);
		}

		// Token: 0x060002F2 RID: 754 RVA: 0x00014A54 File Offset: 0x00012C54
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		}

		// Token: 0x02000060 RID: 96
		public class TColorTable : ProfessionalColorTable
		{
			// Token: 0x0600039C RID: 924 RVA: 0x00015C8F File Offset: 0x00013E8F
			public TColorTable()
			{
				this.BackColor = Color.FromArgb(45, 47, 49);
				this.CheckedColor = Helpers._FlatColor;
				this.BorderColor = Color.FromArgb(53, 58, 60);
			}

			// Token: 0x170000E6 RID: 230
			// (get) Token: 0x0600039D RID: 925 RVA: 0x00015CC4 File Offset: 0x00013EC4
			// (set) Token: 0x0600039E RID: 926 RVA: 0x00015CCC File Offset: 0x00013ECC
			[Category("Colors")]
			public Color _BackColor
			{
				get
				{
					return this.BackColor;
				}
				set
				{
					this.BackColor = value;
				}
			}

			// Token: 0x170000E7 RID: 231
			// (get) Token: 0x0600039F RID: 927 RVA: 0x00015CD5 File Offset: 0x00013ED5
			// (set) Token: 0x060003A0 RID: 928 RVA: 0x00015CDD File Offset: 0x00013EDD
			[Category("Colors")]
			public Color _CheckedColor
			{
				get
				{
					return this.CheckedColor;
				}
				set
				{
					this.CheckedColor = value;
				}
			}

			// Token: 0x170000E8 RID: 232
			// (get) Token: 0x060003A1 RID: 929 RVA: 0x00015CE6 File Offset: 0x00013EE6
			// (set) Token: 0x060003A2 RID: 930 RVA: 0x00015CEE File Offset: 0x00013EEE
			[Category("Colors")]
			public Color _BorderColor
			{
				get
				{
					return this.BorderColor;
				}
				set
				{
					this.BorderColor = value;
				}
			}

			// Token: 0x170000E9 RID: 233
			// (get) Token: 0x060003A3 RID: 931 RVA: 0x00015CC4 File Offset: 0x00013EC4
			public override Color ButtonSelectedBorder
			{
				get
				{
					return this.BackColor;
				}
			}

			// Token: 0x170000EA RID: 234
			// (get) Token: 0x060003A4 RID: 932 RVA: 0x00015CD5 File Offset: 0x00013ED5
			public override Color CheckBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000EB RID: 235
			// (get) Token: 0x060003A5 RID: 933 RVA: 0x00015CD5 File Offset: 0x00013ED5
			public override Color CheckPressedBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000EC RID: 236
			// (get) Token: 0x060003A6 RID: 934 RVA: 0x00015CD5 File Offset: 0x00013ED5
			public override Color CheckSelectedBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000ED RID: 237
			// (get) Token: 0x060003A7 RID: 935 RVA: 0x00015CD5 File Offset: 0x00013ED5
			public override Color ImageMarginGradientBegin
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000EE RID: 238
			// (get) Token: 0x060003A8 RID: 936 RVA: 0x00015CD5 File Offset: 0x00013ED5
			public override Color ImageMarginGradientEnd
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000EF RID: 239
			// (get) Token: 0x060003A9 RID: 937 RVA: 0x00015CD5 File Offset: 0x00013ED5
			public override Color ImageMarginGradientMiddle
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000F0 RID: 240
			// (get) Token: 0x060003AA RID: 938 RVA: 0x00015CE6 File Offset: 0x00013EE6
			public override Color MenuBorder
			{
				get
				{
					return this.BorderColor;
				}
			}

			// Token: 0x170000F1 RID: 241
			// (get) Token: 0x060003AB RID: 939 RVA: 0x00015CE6 File Offset: 0x00013EE6
			public override Color MenuItemBorder
			{
				get
				{
					return this.BorderColor;
				}
			}

			// Token: 0x170000F2 RID: 242
			// (get) Token: 0x060003AC RID: 940 RVA: 0x00015CD5 File Offset: 0x00013ED5
			public override Color MenuItemSelected
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000F3 RID: 243
			// (get) Token: 0x060003AD RID: 941 RVA: 0x00015CE6 File Offset: 0x00013EE6
			public override Color SeparatorDark
			{
				get
				{
					return this.BorderColor;
				}
			}

			// Token: 0x170000F4 RID: 244
			// (get) Token: 0x060003AE RID: 942 RVA: 0x00015CC4 File Offset: 0x00013EC4
			public override Color ToolStripDropDownBackground
			{
				get
				{
					return this.BackColor;
				}
			}

			// Token: 0x040001E7 RID: 487
			private Color BackColor;

			// Token: 0x040001E8 RID: 488
			private Color CheckedColor;

			// Token: 0x040001E9 RID: 489
			private Color BorderColor;
		}
	}
}
