using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace svchost
{
	// Token: 0x02000024 RID: 36
	public class LogInColourTable : ProfessionalColorTable
	{
		// Token: 0x06000251 RID: 593 RVA: 0x00003015 File Offset: 0x00001215
		public LogInColourTable()
		{
			this._BackColour = Color.FromArgb(42, 42, 42);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._SelectedColour = Color.FromArgb(47, 47, 47);
		}

		// Token: 0x170000CD RID: 205
		// (get) Token: 0x06000252 RID: 594 RVA: 0x0000E850 File Offset: 0x0000CA50
		// (set) Token: 0x06000253 RID: 595 RVA: 0x00003051 File Offset: 0x00001251
		[Category("Colours")]
		public Color SelectedColour
		{
			get
			{
				return this._SelectedColour;
			}
			set
			{
				this._SelectedColour = value;
			}
		}

		// Token: 0x170000CE RID: 206
		// (get) Token: 0x06000254 RID: 596 RVA: 0x0000E868 File Offset: 0x0000CA68
		// (set) Token: 0x06000255 RID: 597 RVA: 0x0000305B File Offset: 0x0000125B
		[Category("Colours")]
		public Color BorderColour
		{
			get
			{
				return this._BorderColour;
			}
			set
			{
				this._BorderColour = value;
			}
		}

		// Token: 0x170000CF RID: 207
		// (get) Token: 0x06000256 RID: 598 RVA: 0x0000E880 File Offset: 0x0000CA80
		// (set) Token: 0x06000257 RID: 599 RVA: 0x00003065 File Offset: 0x00001265
		[Category("Colours")]
		public Color BackColour
		{
			get
			{
				return this._BackColour;
			}
			set
			{
				this._BackColour = value;
			}
		}

		// Token: 0x170000D0 RID: 208
		// (get) Token: 0x06000258 RID: 600 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color ButtonSelectedBorder
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x170000D1 RID: 209
		// (get) Token: 0x06000259 RID: 601 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color CheckBackground
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x170000D2 RID: 210
		// (get) Token: 0x0600025A RID: 602 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color CheckPressedBackground
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x170000D3 RID: 211
		// (get) Token: 0x0600025B RID: 603 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color CheckSelectedBackground
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x170000D4 RID: 212
		// (get) Token: 0x0600025C RID: 604 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color ImageMarginGradientBegin
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x170000D5 RID: 213
		// (get) Token: 0x0600025D RID: 605 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color ImageMarginGradientEnd
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x170000D6 RID: 214
		// (get) Token: 0x0600025E RID: 606 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color ImageMarginGradientMiddle
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x170000D7 RID: 215
		// (get) Token: 0x0600025F RID: 607 RVA: 0x0000E868 File Offset: 0x0000CA68
		public override Color MenuBorder
		{
			get
			{
				return this._BorderColour;
			}
		}

		// Token: 0x170000D8 RID: 216
		// (get) Token: 0x06000260 RID: 608 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color MenuItemBorder
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x170000D9 RID: 217
		// (get) Token: 0x06000261 RID: 609 RVA: 0x0000E850 File Offset: 0x0000CA50
		public override Color MenuItemSelected
		{
			get
			{
				return this._SelectedColour;
			}
		}

		// Token: 0x170000DA RID: 218
		// (get) Token: 0x06000262 RID: 610 RVA: 0x0000E868 File Offset: 0x0000CA68
		public override Color SeparatorDark
		{
			get
			{
				return this._BorderColour;
			}
		}

		// Token: 0x170000DB RID: 219
		// (get) Token: 0x06000263 RID: 611 RVA: 0x0000E880 File Offset: 0x0000CA80
		public override Color ToolStripDropDownBackground
		{
			get
			{
				return this._BackColour;
			}
		}

		// Token: 0x040000F2 RID: 242
		private Color _BackColour;

		// Token: 0x040000F3 RID: 243
		private Color _BorderColour;

		// Token: 0x040000F4 RID: 244
		private Color _SelectedColour;
	}
}
