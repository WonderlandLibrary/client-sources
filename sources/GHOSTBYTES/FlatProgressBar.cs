using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace GHOSTBYTES
{
	// Token: 0x02000020 RID: 32
	internal class FlatProgressBar : Control
	{
		// Token: 0x1700008E RID: 142
		// (get) Token: 0x0600019D RID: 413 RVA: 0x0000375B File Offset: 0x0000195B
		// (set) Token: 0x0600019E RID: 414 RVA: 0x00003763 File Offset: 0x00001963
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

		// Token: 0x1700008F RID: 143
		// (get) Token: 0x0600019F RID: 415 RVA: 0x00008A18 File Offset: 0x00006C18
		// (set) Token: 0x060001A0 RID: 416 RVA: 0x00003782 File Offset: 0x00001982
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

		// Token: 0x17000090 RID: 144
		// (get) Token: 0x060001A1 RID: 417 RVA: 0x000037A8 File Offset: 0x000019A8
		// (set) Token: 0x060001A2 RID: 418 RVA: 0x000037B0 File Offset: 0x000019B0
		[Category("Colors")]
		public Color ProgressColor
		{
			get
			{
				return this._ProgressColor;
			}
			set
			{
				this._ProgressColor = value;
			}
		}

		// Token: 0x17000091 RID: 145
		// (get) Token: 0x060001A3 RID: 419 RVA: 0x000037B9 File Offset: 0x000019B9
		// (set) Token: 0x060001A4 RID: 420 RVA: 0x000037C1 File Offset: 0x000019C1
		[Category("Colors")]
		public Color DarkerProgress
		{
			get
			{
				return this._DarkerProgress;
			}
			set
			{
				this._DarkerProgress = value;
			}
		}

		// Token: 0x060001A5 RID: 421 RVA: 0x0000366D File Offset: 0x0000186D
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 42;
		}

		// Token: 0x060001A6 RID: 422 RVA: 0x000037CA File Offset: 0x000019CA
		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Height = 42;
		}

		// Token: 0x060001A7 RID: 423 RVA: 0x000037DA File Offset: 0x000019DA
		public void Increment(int Amount)
		{
			checked
			{
				this.Value += Amount;
			}
		}

		// Token: 0x060001A8 RID: 424 RVA: 0x00008A3C File Offset: 0x00006C3C
		public FlatProgressBar()
		{
			this._Value = 0;
			this._Maximum = 100;
			this._BaseColor = Color.FromArgb(60, 60, 60);
			this._ProgressColor = Color.FromArgb(0, 170, 220);
			this._DarkerProgress = Color.FromArgb(0, 170, 220);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(50, 50, 50);
			base.Height = 30;
		}

		// Token: 0x060001A9 RID: 425 RVA: 0x00008AC8 File Offset: 0x00006CC8
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			checked
			{
				this.W = base.Width - 1;
				this.H = base.Height - 1;
				Rectangle rect = new Rectangle(0, 24, this.W, this.H);
				GraphicsPath graphicsPath = new GraphicsPath();
				GraphicsPath path = new GraphicsPath();
				GraphicsPath path2 = new GraphicsPath();
				Graphics g = Helpers.G;
				g.SmoothingMode = SmoothingMode.HighQuality;
				g.PixelOffsetMode = PixelOffsetMode.HighQuality;
				g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
				g.Clear(this.BackColor);
				int num = (int)Math.Round(unchecked((double)this._Value / (double)this._Maximum * (double)base.Width));
				int value = this.Value;
				if (value != 0)
				{
					if (value != 100)
					{
						g.FillRectangle(new SolidBrush(this._BaseColor), rect);
						graphicsPath.AddRectangle(new Rectangle(0, 24, num - 1, this.H - 1));
						g.FillPath(new SolidBrush(this._ProgressColor), graphicsPath);
						HatchBrush brush = new HatchBrush(HatchStyle.Plaid, this._DarkerProgress, this._ProgressColor);
						g.FillRectangle(brush, new Rectangle(0, 24, num - 1, this.H - 1));
						path = Helpers.RoundRec(new Rectangle(num - 18, 0, 34, 16), 4);
						g.FillPath(new SolidBrush(this._BaseColor), path);
						path2 = Helpers.DrawArrow(num - 9, 16, true);
						g.FillPath(new SolidBrush(this._BaseColor), path2);
						g.DrawString(Conversions.ToString(this.Value), new Font("Segoe UI", 10f), new SolidBrush(this._ProgressColor), new Rectangle(num - 11, -2, this.W, this.H), Helpers.NearSF);
					}
					else
					{
						g.FillRectangle(new SolidBrush(this._BaseColor), rect);
						g.FillRectangle(new SolidBrush(this._ProgressColor), new Rectangle(0, 24, num - 1, this.H - 1));
					}
				}
				else
				{
					g.FillRectangle(new SolidBrush(this._BaseColor), rect);
					g.FillRectangle(new SolidBrush(this._ProgressColor), new Rectangle(0, 24, num - 1, this.H - 1));
				}
				base.OnPaint(e);
				Helpers.G.Dispose();
				e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
				e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
				Helpers.B.Dispose();
			}
		}

		// Token: 0x0400008B RID: 139
		private int W;

		// Token: 0x0400008C RID: 140
		private int H;

		// Token: 0x0400008D RID: 141
		private int _Value;

		// Token: 0x0400008E RID: 142
		private int _Maximum;

		// Token: 0x0400008F RID: 143
		private Color _BaseColor;

		// Token: 0x04000090 RID: 144
		private Color _ProgressColor;

		// Token: 0x04000091 RID: 145
		private Color _DarkerProgress;
	}
}
