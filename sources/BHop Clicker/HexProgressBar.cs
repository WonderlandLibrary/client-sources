using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace shit_temple
{
	// Token: 0x02000014 RID: 20
	internal class HexProgressBar : Control
	{
		// Token: 0x17000014 RID: 20
		// (get) Token: 0x0600006C RID: 108 RVA: 0x000025E4 File Offset: 0x000007E4
		// (set) Token: 0x0600006D RID: 109 RVA: 0x000025EC File Offset: 0x000007EC
		[Category("Control")]
		public int Maximum
		{
			get
			{
				return this._Maximum;
			}
			set
			{
				if (value < this._Value)
				{
					this._Value = value;
				}
				this._Maximum = value;
				base.Invalidate();
			}
		}

		// Token: 0x17000015 RID: 21
		// (get) Token: 0x0600006E RID: 110 RVA: 0x000035F0 File Offset: 0x000017F0
		// (set) Token: 0x0600006F RID: 111 RVA: 0x0000260B File Offset: 0x0000080B
		[Category("Control")]
		public int Value
		{
			get
			{
				int result;
				if (this._Value == 0)
				{
					result = 0;
				}
				else
				{
					result = this._Value;
				}
				return result;
			}
			set
			{
				if (value > this._Maximum)
				{
					value = this._Maximum;
					base.Invalidate();
				}
				this._Value = value;
				base.Invalidate();
			}
		}

		// Token: 0x06000070 RID: 112 RVA: 0x00002631 File Offset: 0x00000831
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 25;
		}

		// Token: 0x06000071 RID: 113 RVA: 0x00002642 File Offset: 0x00000842
		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Height = 25;
		}

		// Token: 0x06000072 RID: 114 RVA: 0x00002652 File Offset: 0x00000852
		public void Increment(int Amount)
		{
			checked
			{
				this.Value += Amount;
			}
		}

		// Token: 0x06000073 RID: 115 RVA: 0x00002662 File Offset: 0x00000862
		public HexProgressBar()
		{
			this._Value = 0;
			this._Maximum = 100;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
		}

		// Token: 0x06000074 RID: 116 RVA: 0x00003614 File Offset: 0x00001814
		protected override void OnPaint(PaintEventArgs e)
		{
			base.OnPaint(e);
			Graphics graphics = e.Graphics;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(Color.FromArgb(47, 51, 60));
			checked
			{
				int num = (int)Math.Round(unchecked((double)this._Value / (double)this._Maximum * (double)base.Width));
				graphics.FillRectangle(new SolidBrush(Color.FromArgb(236, 95, 75)), new Rectangle(0, 0, num - 1, base.Height));
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x0400002A RID: 42
		private int _Value;

		// Token: 0x0400002B RID: 43
		private int _Maximum;
	}
}
