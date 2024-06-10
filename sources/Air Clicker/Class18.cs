using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Text;
using System.Windows.Forms;

// Token: 0x02000053 RID: 83
internal class Class18 : ContextMenuStrip
{
	// Token: 0x06000468 RID: 1128 RVA: 0x00015A00 File Offset: 0x00013C00
	protected virtual void OnTextChanged(EventArgs e)
	{
		base.OnTextChanged(e);
		base.Invalidate();
	}

	// Token: 0x06000469 RID: 1129 RVA: 0x00015A10 File Offset: 0x00013C10
	public Class18()
	{
		base.Renderer = new ToolStripProfessionalRenderer(new Class18.Class19());
		base.ShowImageMargin = false;
		base.ForeColor = Color.White;
		this.Font = new Font("Segoe UI", 8f);
	}

	// Token: 0x0600046A RID: 1130 RVA: 0x00015A50 File Offset: 0x00013C50
	protected virtual void OnPaint(PaintEventArgs e)
	{
		base.OnPaint(e);
		e.Graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
	}

	// Token: 0x02000054 RID: 84
	public class Class19 : ProfessionalColorTable
	{
		// Token: 0x0600046B RID: 1131 RVA: 0x00015A68 File Offset: 0x00013C68
		public Class19()
		{
			this.color_0 = Color.FromArgb(45, 47, 49);
			this.color_1 = Class16.color_0;
			this.color_2 = Color.FromArgb(53, 58, 60);
		}

		// Token: 0x17000154 RID: 340
		// (get) Token: 0x0600046C RID: 1132 RVA: 0x00015AA0 File Offset: 0x00013CA0
		// (set) Token: 0x0600046D RID: 1133 RVA: 0x00015AB8 File Offset: 0x00013CB8
		[Category("Colors")]
		public Color Color_0
		{
			get
			{
				return this.color_0;
			}
			set
			{
				this.color_0 = value;
			}
		}

		// Token: 0x17000155 RID: 341
		// (get) Token: 0x0600046E RID: 1134 RVA: 0x00015AC4 File Offset: 0x00013CC4
		// (set) Token: 0x0600046F RID: 1135 RVA: 0x00015ADC File Offset: 0x00013CDC
		[Category("Colors")]
		public Color Color_1
		{
			get
			{
				return this.color_1;
			}
			set
			{
				this.color_1 = value;
			}
		}

		// Token: 0x17000156 RID: 342
		// (get) Token: 0x06000470 RID: 1136 RVA: 0x00015AE8 File Offset: 0x00013CE8
		// (set) Token: 0x06000471 RID: 1137 RVA: 0x00015B00 File Offset: 0x00013D00
		[Category("Colors")]
		public Color Color_2
		{
			get
			{
				return this.color_2;
			}
			set
			{
				this.color_2 = value;
			}
		}

		// Token: 0x17000157 RID: 343
		// (get) Token: 0x06000472 RID: 1138 RVA: 0x00015B0C File Offset: 0x00013D0C
		public virtual Color ButtonSelectedBorder
		{
			get
			{
				return this.color_0;
			}
		}

		// Token: 0x17000158 RID: 344
		// (get) Token: 0x06000473 RID: 1139 RVA: 0x00015B24 File Offset: 0x00013D24
		public virtual Color CheckBackground
		{
			get
			{
				return this.color_1;
			}
		}

		// Token: 0x17000159 RID: 345
		// (get) Token: 0x06000474 RID: 1140 RVA: 0x00015B3C File Offset: 0x00013D3C
		public virtual Color CheckPressedBackground
		{
			get
			{
				return this.color_1;
			}
		}

		// Token: 0x1700015A RID: 346
		// (get) Token: 0x06000475 RID: 1141 RVA: 0x00015B54 File Offset: 0x00013D54
		public virtual Color CheckSelectedBackground
		{
			get
			{
				return this.color_1;
			}
		}

		// Token: 0x1700015B RID: 347
		// (get) Token: 0x06000476 RID: 1142 RVA: 0x00015B6C File Offset: 0x00013D6C
		public virtual Color ImageMarginGradientBegin
		{
			get
			{
				return this.color_1;
			}
		}

		// Token: 0x1700015C RID: 348
		// (get) Token: 0x06000477 RID: 1143 RVA: 0x00015B84 File Offset: 0x00013D84
		public virtual Color ImageMarginGradientEnd
		{
			get
			{
				return this.color_1;
			}
		}

		// Token: 0x1700015D RID: 349
		// (get) Token: 0x06000478 RID: 1144 RVA: 0x00015B9C File Offset: 0x00013D9C
		public virtual Color ImageMarginGradientMiddle
		{
			get
			{
				return this.color_1;
			}
		}

		// Token: 0x1700015E RID: 350
		// (get) Token: 0x06000479 RID: 1145 RVA: 0x00015BB4 File Offset: 0x00013DB4
		public virtual Color MenuBorder
		{
			get
			{
				return this.color_2;
			}
		}

		// Token: 0x1700015F RID: 351
		// (get) Token: 0x0600047A RID: 1146 RVA: 0x00015BCC File Offset: 0x00013DCC
		public virtual Color MenuItemBorder
		{
			get
			{
				return this.color_2;
			}
		}

		// Token: 0x17000160 RID: 352
		// (get) Token: 0x0600047B RID: 1147 RVA: 0x00015BE4 File Offset: 0x00013DE4
		public virtual Color MenuItemSelected
		{
			get
			{
				return this.color_1;
			}
		}

		// Token: 0x17000161 RID: 353
		// (get) Token: 0x0600047C RID: 1148 RVA: 0x00015BFC File Offset: 0x00013DFC
		public virtual Color SeparatorDark
		{
			get
			{
				return this.color_2;
			}
		}

		// Token: 0x17000162 RID: 354
		// (get) Token: 0x0600047D RID: 1149 RVA: 0x00015C14 File Offset: 0x00013E14
		public virtual Color ToolStripDropDownBackground
		{
			get
			{
				return this.color_0;
			}
		}

		// Token: 0x04000257 RID: 599
		private Color color_0;

		// Token: 0x04000258 RID: 600
		private Color color_1;

		// Token: 0x04000259 RID: 601
		private Color color_2;
	}
}
