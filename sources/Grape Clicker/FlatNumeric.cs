using Microsoft.VisualBasic.CompilerServices;
using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	internal class FlatNumeric : Control
	{
		private int W;

		private int H;

		private MouseState State;

		private int x;

		private int y;

		private long _Value;

		private long _Min;

		private long _Max;

		private bool Bool;

		private Color _BaseColor;

		private Color _ButtonColor;

		[Category("Colors")]
		public Color BaseColor
		{
			get
			{
				return this._BaseColor;
			}
			set
			{
				this._BaseColor = value;
			}
		}

		[Category("Colors")]
		public Color ButtonColor
		{
			get
			{
				return this._ButtonColor;
			}
			set
			{
				this._ButtonColor = value;
			}
		}

		public long Maximum
		{
			get
			{
				return this._Max;
			}
			set
			{
				if (value > this._Min)
				{
					this._Max = value;
				}
				if (this._Value > this._Max)
				{
					this._Value = this._Max;
				}
				base.Invalidate();
			}
		}

		public long Minimum
		{
			get
			{
				return this._Min;
			}
			set
			{
				if (value < this._Max)
				{
					this._Min = value;
				}
				if (this._Value < this._Min)
				{
					this._Value = this.Minimum;
				}
				base.Invalidate();
			}
		}

		public long Value
		{
			get
			{
				return this._Value;
			}
			set
			{
				if (value <= this._Max & value >= this._Min)
				{
					this._Value = value;
				}
				base.Invalidate();
			}
		}

		public FlatNumeric()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._ButtonColor = Helpers._FlatColor;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.Font = new System.Drawing.Font("Segoe UI", 10f);
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.ForeColor = Color.White;
			this._Min = (long)0;
			this._Max = (long)9999999;
		}

		protected override void OnKeyDown(KeyEventArgs e)
		{
			base.OnKeyDown(e);
			if (e.KeyCode == Keys.Back)
			{
				this.Value = (long)0;
			}
		}

		protected override void OnKeyPress(KeyPressEventArgs e)
		{
			base.OnKeyPress(e);
			try
			{
				if (this.Bool)
				{
					string str = Conversions.ToString(this._Value);
					char keyChar = e.KeyChar;
					this._Value = Conversions.ToLong(string.Concat(str, keyChar.ToString()));
				}
				if (this._Value > this._Max)
				{
					this._Value = this._Max;
				}
				base.Invalidate();
			}
			catch (Exception exception)
			{
				ProjectData.SetProjectError(exception);
				ProjectData.ClearProjectError();
			}
		}

		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			if (this.x <= checked(base.Width - 21) || this.x >= checked(base.Width - 3))
			{
				this.Bool = !this.Bool;
				base.Focus();
			}
			else if (this.y < 15)
			{
				if (checked(this.Value + (long)1) <= this._Max)
				{
					ref long numPointer = ref this._Value;
					numPointer = checked(numPointer + (long)1);
				}
			}
			else if (checked(this.Value - (long)1) >= this._Min)
			{
				ref long numPointer1 = ref this._Value;
				numPointer1 = checked(numPointer1 - (long)1);
			}
			base.Invalidate();
		}

		protected override void OnMouseMove(MouseEventArgs e)
		{
			base.OnMouseMove(e);
			this.x = e.Location.X;
			this.y = e.Location.Y;
			base.Invalidate();
			if (e.X < checked(base.Width - 23))
			{
				this.Cursor = Cursors.IBeam;
				return;
			}
			this.Cursor = Cursors.Hand;
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = base.Width;
			this.H = base.Height;
			Rectangle rectangle = new Rectangle(0, 0, this.W, this.H);
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.PixelOffsetMode = PixelOffsetMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
			g.FillRectangle(new SolidBrush(this._ButtonColor), new Rectangle(checked(base.Width - 24), 0, 24, this.H));
			g.DrawString("+", new System.Drawing.Font("Segoe UI", 12f), Brushes.White, new Point(checked(base.Width - 12), 8), Helpers.CenterSF);
			g.DrawString("-", new System.Drawing.Font("Segoe UI", 10f, FontStyle.Bold), Brushes.White, new Point(checked(base.Width - 12), 22), Helpers.CenterSF);
			g.DrawString(Conversions.ToString(this.Value), this.Font, Brushes.White, new Rectangle(5, 1, this.W, this.H), new StringFormat()
			{
				LineAlignment = StringAlignment.Center
			});
			base.OnPaint(e);
			Helpers.G.Dispose();
			e.Graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			e.Graphics.DrawImageUnscaled(Helpers.B, 0, 0);
			Helpers.B.Dispose();
		}

		protected override void OnResize(EventArgs e)
		{
			base.OnResize(e);
			base.Height = 30;
		}
	}
}