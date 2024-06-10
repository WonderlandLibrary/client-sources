using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

namespace GHOSTBYTES
{
	// Token: 0x02000025 RID: 37
	internal class FlatContextMenuStrip : ContextMenuStrip
	{
		// Token: 0x060001E8 RID: 488 RVA: 0x00003271 File Offset: 0x00001471
		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		// Token: 0x060001E9 RID: 489 RVA: 0x00003B3B File Offset: 0x00001D3B
		public FlatContextMenuStrip()
		{
			base.Renderer = new ToolStripProfessionalRenderer(new FlatContextMenuStrip.TColorTable());
			base.ShowImageMargin = false;
			base.ForeColor = Color.White;
			this.Font = new Font("Segoe UI", 8f);
		}

		// Token: 0x060001EA RID: 490 RVA: 0x00003B7A File Offset: 0x00001D7A
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
		}

		// Token: 0x02000026 RID: 38
		public class TColorTable : ProfessionalColorTable
		{
			// Token: 0x060001EB RID: 491 RVA: 0x00009EB8 File Offset: 0x000080B8
			public TColorTable()
			{
				this.BackColor = Color.FromArgb(60, 60, 60);
				this.CheckedColor = Color.FromArgb(0, 170, 220);
				this.BorderColor = Color.FromArgb(0, 170, 220);
			}

			// Token: 0x170000A2 RID: 162
			// (get) Token: 0x060001EC RID: 492 RVA: 0x00003B8F File Offset: 0x00001D8F
			// (set) Token: 0x060001ED RID: 493 RVA: 0x00003B97 File Offset: 0x00001D97
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

			// Token: 0x170000A3 RID: 163
			// (get) Token: 0x060001EE RID: 494 RVA: 0x00003BA0 File Offset: 0x00001DA0
			// (set) Token: 0x060001EF RID: 495 RVA: 0x00003BA8 File Offset: 0x00001DA8
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

			// Token: 0x170000A4 RID: 164
			// (get) Token: 0x060001F0 RID: 496 RVA: 0x00003BB1 File Offset: 0x00001DB1
			// (set) Token: 0x060001F1 RID: 497 RVA: 0x00003BB9 File Offset: 0x00001DB9
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

			// Token: 0x170000A5 RID: 165
			// (get) Token: 0x060001F2 RID: 498 RVA: 0x00003B8F File Offset: 0x00001D8F
			public override Color ButtonSelectedBorder
			{
				get
				{
					return this.BackColor;
				}
			}

			// Token: 0x170000A6 RID: 166
			// (get) Token: 0x060001F3 RID: 499 RVA: 0x00003BA0 File Offset: 0x00001DA0
			public override Color CheckBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000A7 RID: 167
			// (get) Token: 0x060001F4 RID: 500 RVA: 0x00003BA0 File Offset: 0x00001DA0
			public override Color CheckPressedBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000A8 RID: 168
			// (get) Token: 0x060001F5 RID: 501 RVA: 0x00003BA0 File Offset: 0x00001DA0
			public override Color CheckSelectedBackground
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000A9 RID: 169
			// (get) Token: 0x060001F6 RID: 502 RVA: 0x00003BA0 File Offset: 0x00001DA0
			public override Color ImageMarginGradientBegin
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000AA RID: 170
			// (get) Token: 0x060001F7 RID: 503 RVA: 0x00003BA0 File Offset: 0x00001DA0
			public override Color ImageMarginGradientEnd
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000AB RID: 171
			// (get) Token: 0x060001F8 RID: 504 RVA: 0x00003BA0 File Offset: 0x00001DA0
			public override Color ImageMarginGradientMiddle
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000AC RID: 172
			// (get) Token: 0x060001F9 RID: 505 RVA: 0x00003BB1 File Offset: 0x00001DB1
			public override Color MenuBorder
			{
				get
				{
					return this.BorderColor;
				}
			}

			// Token: 0x170000AD RID: 173
			// (get) Token: 0x060001FA RID: 506 RVA: 0x00003BB1 File Offset: 0x00001DB1
			public override Color MenuItemBorder
			{
				get
				{
					return this.BorderColor;
				}
			}

			// Token: 0x170000AE RID: 174
			// (get) Token: 0x060001FB RID: 507 RVA: 0x00003BA0 File Offset: 0x00001DA0
			public override Color MenuItemSelected
			{
				get
				{
					return this.CheckedColor;
				}
			}

			// Token: 0x170000AF RID: 175
			// (get) Token: 0x060001FC RID: 508 RVA: 0x00003BB1 File Offset: 0x00001DB1
			public override Color SeparatorDark
			{
				get
				{
					return this.BorderColor;
				}
			}

			// Token: 0x170000B0 RID: 176
			// (get) Token: 0x060001FD RID: 509 RVA: 0x00003B8F File Offset: 0x00001D8F
			public override Color ToolStripDropDownBackground
			{
				get
				{
					return this.BackColor;
				}
			}

			// Token: 0x040000B0 RID: 176
			private Color BackColor;

			// Token: 0x040000B1 RID: 177
			private Color CheckedColor;

			// Token: 0x040000B2 RID: 178
			private Color BorderColor;
		}
	}
}
