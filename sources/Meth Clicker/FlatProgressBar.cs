using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace Meth
{
	// Token: 0x02000024 RID: 36
	internal class FlatProgressBar : Control
	{
		// Token: 0x170000C5 RID: 197
		// (get) Token: 0x060002A3 RID: 675 RVA: 0x00013168 File Offset: 0x00011368
		// (set) Token: 0x060002A4 RID: 676 RVA: 0x00013170 File Offset: 0x00011370
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

		// Token: 0x170000C6 RID: 198
		// (get) Token: 0x060002A5 RID: 677 RVA: 0x00013190 File Offset: 0x00011390
		// (set) Token: 0x060002A6 RID: 678 RVA: 0x000131B3 File Offset: 0x000113B3
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

		// Token: 0x170000C7 RID: 199
		// (get) Token: 0x060002A7 RID: 679 RVA: 0x000131D9 File Offset: 0x000113D9
		// (set) Token: 0x060002A8 RID: 680 RVA: 0x000131E1 File Offset: 0x000113E1
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

		// Token: 0x170000C8 RID: 200
		// (get) Token: 0x060002A9 RID: 681 RVA: 0x000131EA File Offset: 0x000113EA
		// (set) Token: 0x060002AA RID: 682 RVA: 0x000131F2 File Offset: 0x000113F2
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

		// Token: 0x060002AB RID: 683 RVA: 0x000129A4 File Offset: 0x00010BA4
		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 42;
		}

		// Token: 0x060002AC RID: 684 RVA: 0x000131FB File Offset: 0x000113FB
		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Height = 42;
		}

		// Token: 0x060002AD RID: 685 RVA: 0x0001320B File Offset: 0x0001140B
		public void Increment(int Amount)
		{
			this.Value += Amount;
		}

		// Token: 0x060002AE RID: 686 RVA: 0x0001321C File Offset: 0x0001141C
		public FlatProgressBar()
		{
			this._Value = 0;
			this._Maximum = 100;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._ProgressColor = Helpers._FlatColor;
			this._DarkerProgress = Color.FromArgb(23, 148, 92);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(60, 70, 73);
			base.Height = 42;
		}

		// Token: 0x060002AF RID: 687 RVA: 0x0001329C File Offset: 0x0001149C
		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
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
			int num = (int)Math.Round((double)this._Value / (double)this._Maximum * (double)base.Width);
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

		// Token: 0x0400015D RID: 349
		private int W;

		// Token: 0x0400015E RID: 350
		private int H;

		// Token: 0x0400015F RID: 351
		private int _Value;

		// Token: 0x04000160 RID: 352
		private int _Maximum;

		// Token: 0x04000161 RID: 353
		private Color _BaseColor;

		// Token: 0x04000162 RID: 354
		private Color _ProgressColor;

		// Token: 0x04000163 RID: 355
		private Color _DarkerProgress;
	}
}
