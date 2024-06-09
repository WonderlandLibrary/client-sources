using Microsoft.VisualBasic.CompilerServices;
using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatProgressBar : Control
	{
		private int W;

		private int H;

		private int _Value;

		private int _Maximum;

		private Color _BaseColor;

		private Color _ProgressColor;

		private Color _DarkerProgress;

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

		[Category("Control")]
		public int Value
		{
			get
			{
				return (this._Value != 0 ? this._Value : 0);
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

		protected override void CreateHandle()
		{
			base.CreateHandle();
			base.Height = 42;
		}

		public void Increment(int Amount)
		{
			this.Value = checked(this.Value + Amount);
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = checked(base.Width - 1);
			this.H = checked(base.Height - 1);
			Rectangle rectangle = new Rectangle(0, 24, this.W, this.H);
			GraphicsPath graphicsPath = new GraphicsPath();
			GraphicsPath graphicsPath1 = new GraphicsPath();
			GraphicsPath graphicsPath2 = new GraphicsPath();
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			int num = checked((int)Math.Round((double)this._Value / (double)this._Maximum * (double)base.Width));
			int value = this.Value;
			if (value == 0)
			{
				g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
				g.FillRectangle(new SolidBrush(this._ProgressColor), new Rectangle(0, 24, checked(num - 1), checked(this.H - 1)));
			}
			else if (value == 100)
			{
				g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
				g.FillRectangle(new SolidBrush(this._ProgressColor), new Rectangle(0, 24, checked(num - 1), checked(this.H - 1)));
			}
			else
			{
				g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
				graphicsPath.AddRectangle(new Rectangle(0, 24, checked(num - 1), checked(this.H - 1)));
				g.FillPath(new SolidBrush(this._ProgressColor), graphicsPath);
				HatchBrush hatchBrush = new HatchBrush(HatchStyle.Plaid, this._DarkerProgress, this._ProgressColor);
				g.FillRectangle(hatchBrush, new Rectangle(0, 24, checked(num - 1), checked(this.H - 1)));
				graphicsPath1 = Helpers.RoundRec(new Rectangle(checked(num - 18), 0, 34, 16), 4);
				g.FillPath(new SolidBrush(this._BaseColor), graphicsPath1);
				graphicsPath2 = Helpers.DrawArrow(checked(num - 9), 16, true);
				g.FillPath(new SolidBrush(this._BaseColor), graphicsPath2);
				g.DrawString(Conversions.ToString(this.Value), new System.Drawing.Font("Segoe UI", 10f), new SolidBrush(this._ProgressColor), new Rectangle(checked(num - 11), -2, this.W, this.H), Helpers.NearSF);
			}
			g = null;
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 42;
		}
	}
}