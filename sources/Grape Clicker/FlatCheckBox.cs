using System;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Runtime.CompilerServices;
using System.Threading;
using System.Windows.Forms;

namespace Xh0kO1ZCmA
{
	[DefaultEvent("CheckedChanged")]
	internal class FlatCheckBox : Control
	{
		private int W;

		private int H;

		private MouseState State;

		private FlatCheckBox._Options O;

		private bool _Checked;

		private Color _BaseColor;

		private Color _BorderColor;

		private Color _TextColor;

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
		public Color BorderColor
		{
			get
			{
				return this._BorderColor;
			}
			set
			{
				this._BorderColor = value;
			}
		}

		public bool Checked
		{
			get
			{
				return this._Checked;
			}
			set
			{
				this._Checked = value;
				base.Invalidate();
			}
		}

		[Category("Options")]
		public FlatCheckBox._Options Options
		{
			get
			{
				return this.O;
			}
			set
			{
				this.O = value;
			}
		}

		public FlatCheckBox()
		{
			this.State = MouseState.None;
			this._BaseColor = Color.FromArgb(45, 47, 49);
			this._BorderColor = Helpers._FlatColor;
			this._TextColor = Color.FromArgb(243, 243, 243);
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.BackColor = Color.FromArgb(60, 70, 73);
			this.Cursor = Cursors.Hand;
			this.Font = new System.Drawing.Font("Segoe UI", 10f);
			base.Size = new System.Drawing.Size(112, 22);
		}

		protected override void OnClick(EventArgs e)
		{
			this._Checked = !this._Checked;
			FlatCheckBox.CheckedChangedEventHandler checkedChangedEventHandler = this.CheckedChanged;
			if (checkedChangedEventHandler != null)
			{
				checkedChangedEventHandler(this);
			}
			base.OnClick(e);
		}

		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.OnMouseDown(e);
			this.State = MouseState.Down;
			base.Invalidate();
		}

		protected override void OnMouseEnter(EventArgs e)
		{
			base.OnMouseEnter(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		protected override void OnMouseLeave(EventArgs e)
		{
			base.OnMouseLeave(e);
			this.State = MouseState.None;
			base.Invalidate();
		}

		protected override void OnMouseUp(MouseEventArgs e)
		{
			base.OnMouseUp(e);
			this.State = MouseState.Over;
			base.Invalidate();
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Helpers.B = new Bitmap(base.Width, base.Height);
			Helpers.G = Graphics.FromImage(Helpers.B);
			this.W = checked(base.Width - 1);
			this.H = checked(base.Height - 1);
			Rectangle rectangle = new Rectangle(0, 2, checked(base.Height - 5), checked(base.Height - 5));
			Graphics g = Helpers.G;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			g.Clear(this.BackColor);
			FlatCheckBox._Options o = this.O;
			if (o == FlatCheckBox._Options.Style1)
			{
				g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
				MouseState state = this.State;
				if (state == MouseState.Over)
				{
					g.DrawRectangle(new Pen(this._BorderColor), rectangle);
				}
				else if (state == MouseState.Down)
				{
					g.DrawRectangle(new Pen(this._BorderColor), rectangle);
				}
				if (this.Checked)
				{
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 18f), new SolidBrush(this._BorderColor), new Rectangle(5, 7, checked(this.H - 9), checked(this.H - 9)), Helpers.CenterSF);
				}
				if (!base.Enabled)
				{
					g.FillRectangle(new SolidBrush(Color.FromArgb(54, 58, 61)), rectangle);
					g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(140, 142, 143)), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
				}
				g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
			}
			else if (o == FlatCheckBox._Options.Style2)
			{
				g.FillRectangle(new SolidBrush(this._BaseColor), rectangle);
				MouseState mouseState = this.State;
				if (mouseState == MouseState.Over)
				{
					g.DrawRectangle(new Pen(this._BorderColor), rectangle);
					g.FillRectangle(new SolidBrush(Color.FromArgb(118, 213, 170)), rectangle);
				}
				else if (mouseState == MouseState.Down)
				{
					g.DrawRectangle(new Pen(this._BorderColor), rectangle);
					g.FillRectangle(new SolidBrush(Color.FromArgb(118, 213, 170)), rectangle);
				}
				if (this.Checked)
				{
					g.DrawString("ü", new System.Drawing.Font("Wingdings", 18f), new SolidBrush(this._BorderColor), new Rectangle(5, 7, checked(this.H - 9), checked(this.H - 9)), Helpers.CenterSF);
				}
				if (!base.Enabled)
				{
					g.FillRectangle(new SolidBrush(Color.FromArgb(54, 58, 61)), rectangle);
					g.DrawString(this.Text, this.Font, new SolidBrush(Color.FromArgb(48, 119, 91)), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
				}
				g.DrawString(this.Text, this.Font, new SolidBrush(this._TextColor), new Rectangle(20, 2, this.W, this.H), Helpers.NearSF);
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
			base.Height = 22;
		}

		protected override void OnTextChanged(EventArgs e)
		{
			base.OnTextChanged(e);
			base.Invalidate();
		}

		public event FlatCheckBox.CheckedChangedEventHandler CheckedChanged;

		[Flags]
		public enum _Options
		{
			Style1,
			Style2
		}

		public delegate void CheckedChangedEventHandler(object sender);
	}
}