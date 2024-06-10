using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.Drawing.Text;
using System.Windows.Forms;
using Microsoft.VisualBasic.CompilerServices;

namespace svchost
{
	// Token: 0x0200003B RID: 59
	public class LogInListBoxWBuiltInScrollBar : Control
	{
		// Token: 0x17000149 RID: 329
		// (get) Token: 0x060003B1 RID: 945 RVA: 0x00013C0C File Offset: 0x00011E0C
		// (set) Token: 0x060003B2 RID: 946 RVA: 0x000037B6 File Offset: 0x000019B6
		[Category("Colours")]
		public Color TextColour
		{
			get
			{
				return this._TextColour;
			}
			set
			{
				this._TextColour = value;
			}
		}

		// Token: 0x1700014A RID: 330
		// (get) Token: 0x060003B3 RID: 947 RVA: 0x00013C24 File Offset: 0x00011E24
		// (set) Token: 0x060003B4 RID: 948 RVA: 0x00013C3C File Offset: 0x00011E3C
		[Category("Control")]
		public int SelectedHeight
		{
			get
			{
				return this._SelectedHeight;
			}
			set
			{
				bool flag = value < 1;
				if (flag)
				{
					this._SelectedHeight = base.Height;
				}
				else
				{
					this._SelectedHeight = value;
				}
				this.InvalidateScroll();
			}
		}

		// Token: 0x1700014B RID: 331
		// (get) Token: 0x060003B5 RID: 949 RVA: 0x00013C74 File Offset: 0x00011E74
		// (set) Token: 0x060003B6 RID: 950 RVA: 0x000037C0 File Offset: 0x000019C0
		[Category("Colours")]
		public Color BaseColour
		{
			get
			{
				return this._BaseColour;
			}
			set
			{
				this._BaseColour = value;
			}
		}

		// Token: 0x1700014C RID: 332
		// (get) Token: 0x060003B7 RID: 951 RVA: 0x00013C8C File Offset: 0x00011E8C
		// (set) Token: 0x060003B8 RID: 952 RVA: 0x000037CA File Offset: 0x000019CA
		[Category("Colours")]
		public Color SelectedItemColour
		{
			get
			{
				return this._SelectedItemColour;
			}
			set
			{
				this._SelectedItemColour = value;
			}
		}

		// Token: 0x1700014D RID: 333
		// (get) Token: 0x060003B9 RID: 953 RVA: 0x00013CA4 File Offset: 0x00011EA4
		// (set) Token: 0x060003BA RID: 954 RVA: 0x000037D4 File Offset: 0x000019D4
		[Category("Colours")]
		public Color NonSelectedItemColour
		{
			get
			{
				return this._NonSelectedItemColour;
			}
			set
			{
				this._NonSelectedItemColour = value;
			}
		}

		// Token: 0x1700014E RID: 334
		// (get) Token: 0x060003BB RID: 955 RVA: 0x00013CBC File Offset: 0x00011EBC
		// (set) Token: 0x060003BC RID: 956 RVA: 0x000037DE File Offset: 0x000019DE
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

		// Token: 0x060003BD RID: 957 RVA: 0x00003711 File Offset: 0x00001911
		private void HandleScroll(object sender)
		{
			base.Invalidate();
		}

		// Token: 0x060003BE RID: 958 RVA: 0x00013CD4 File Offset: 0x00011ED4
		private void InvalidateScroll()
		{
			Debug.Print(Conversions.ToString(base.Height));
			checked
			{
				bool flag = (double)((int)Math.Round((double)(this._Items.Count * this.ItemHeight) / (double)this._SelectedHeight)) < (double)(this._Items.Count * this.ItemHeight) / (double)this._SelectedHeight;
				if (flag)
				{
					this.VerticalScrollbar._Maximum = (int)Math.Ceiling((double)(this._Items.Count * this.ItemHeight) / (double)this._SelectedHeight);
				}
				else
				{
					bool flag2 = (int)Math.Round((double)(this._Items.Count * this.ItemHeight) / (double)this._SelectedHeight) == 0;
					if (flag2)
					{
						this.VerticalScrollbar._Maximum = 1;
					}
					else
					{
						this.VerticalScrollbar._Maximum = (int)Math.Round((double)(this._Items.Count * this.ItemHeight) / (double)this._SelectedHeight);
					}
				}
				base.Invalidate();
			}
		}

		// Token: 0x060003BF RID: 959 RVA: 0x00013DD4 File Offset: 0x00011FD4
		private void InvalidateLayout()
		{
			checked
			{
				this.VerticalScrollbar.Location = new Point(base.Width - this.VerticalScrollbar.Width - 2, 2);
				this.VerticalScrollbar.Size = new Size(18, base.Height - 4);
				base.Invalidate();
			}
		}

		// Token: 0x1700014F RID: 335
		// (get) Token: 0x060003C0 RID: 960 RVA: 0x00013E2C File Offset: 0x0001202C
		// (set) Token: 0x060003C1 RID: 961 RVA: 0x000037E8 File Offset: 0x000019E8
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
		public LogInListBoxWBuiltInScrollBar.LogInListBoxItem[] Items
		{
			get
			{
				return this._Items.ToArray();
			}
			set
			{
				this._Items = new List<LogInListBoxWBuiltInScrollBar.LogInListBoxItem>(value);
				base.Invalidate();
				this.InvalidateScroll();
			}
		}

		// Token: 0x17000150 RID: 336
		// (get) Token: 0x060003C2 RID: 962 RVA: 0x00013E4C File Offset: 0x0001204C
		public LogInListBoxWBuiltInScrollBar.LogInListBoxItem[] SelectedItems
		{
			get
			{
				return this._SelectedItems.ToArray();
			}
		}

		// Token: 0x17000151 RID: 337
		// (get) Token: 0x060003C3 RID: 963 RVA: 0x00013E6C File Offset: 0x0001206C
		// (set) Token: 0x060003C4 RID: 964 RVA: 0x00013E84 File Offset: 0x00012084
		public bool MultiSelect
		{
			get
			{
				return this._MultiSelect;
			}
			set
			{
				this._MultiSelect = value;
				bool flag = this._SelectedItems.Count > 1;
				if (flag)
				{
					this._SelectedItems.RemoveRange(1, checked(this._SelectedItems.Count - 1));
				}
				base.Invalidate();
			}
		}

		// Token: 0x17000152 RID: 338
		// (get) Token: 0x060003C5 RID: 965 RVA: 0x0000ACB4 File Offset: 0x00008EB4
		// (set) Token: 0x060003C6 RID: 966 RVA: 0x00013ED0 File Offset: 0x000120D0
		public override Font Font
		{
			get
			{
				return base.Font;
			}
			set
			{
				this.ItemHeight = checked((int)Math.Round((double)Graphics.FromHwnd(base.Handle).MeasureString("@", this.Font).Height));
				bool flag = this.VerticalScrollbar != null;
				if (flag)
				{
					this.VerticalScrollbar._SmallChange = 1;
					this.VerticalScrollbar._LargeChange = 1;
				}
				base.Font = value;
				this.InvalidateLayout();
			}
		}

		// Token: 0x060003C7 RID: 967 RVA: 0x00013F44 File Offset: 0x00012144
		public void AddItem(string Items)
		{
			LogInListBoxWBuiltInScrollBar.LogInListBoxItem Item = new LogInListBoxWBuiltInScrollBar.LogInListBoxItem();
			Item.Text = Items;
			this._Items.Add(Item);
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003C8 RID: 968 RVA: 0x00013F7C File Offset: 0x0001217C
		public void AddItems(string[] Items)
		{
			foreach (string I in Items)
			{
				LogInListBoxWBuiltInScrollBar.LogInListBoxItem Item = new LogInListBoxWBuiltInScrollBar.LogInListBoxItem();
				Item.Text = I;
				this._Items.Add(Item);
			}
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003C9 RID: 969 RVA: 0x00003805 File Offset: 0x00001A05
		public void RemoveItemAt(int index)
		{
			this._Items.RemoveAt(index);
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003CA RID: 970 RVA: 0x00003823 File Offset: 0x00001A23
		public void RemoveItem(LogInListBoxWBuiltInScrollBar.LogInListBoxItem item)
		{
			this._Items.Remove(item);
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003CB RID: 971 RVA: 0x00013FD0 File Offset: 0x000121D0
		public void RemoveItems(LogInListBoxWBuiltInScrollBar.LogInListBoxItem[] items)
		{
			foreach (LogInListBoxWBuiltInScrollBar.LogInListBoxItem I in items)
			{
				this._Items.Remove(I);
			}
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003CC RID: 972 RVA: 0x00003841 File Offset: 0x00001A41
		protected override void OnSizeChanged(EventArgs e)
		{
			this._SelectedHeight = base.Height;
			this.InvalidateScroll();
			this.InvalidateLayout();
			base.OnSizeChanged(e);
		}

		// Token: 0x060003CD RID: 973 RVA: 0x00003799 File Offset: 0x00001999
		private void Vertical_MouseDown(object sender, MouseEventArgs e)
		{
			base.Focus();
		}

		// Token: 0x060003CE RID: 974 RVA: 0x00014014 File Offset: 0x00012214
		protected override void OnMouseDown(MouseEventArgs e)
		{
			base.Focus();
			bool flag = e.Button == MouseButtons.Left;
			checked
			{
				if (flag)
				{
					int Offset = this.VerticalScrollbar.Value * (this.VerticalScrollbar.Maximum + (base.Height - this.ItemHeight));
					int Index = (e.Y + Offset) / this.ItemHeight;
					bool flag2 = Index > this._Items.Count - 1;
					if (flag2)
					{
						Index = -1;
					}
					bool flag3 = Index != -1;
					if (flag3)
					{
						bool flag4 = Control.ModifierKeys == Keys.Control && this._MultiSelect;
						if (flag4)
						{
							bool flag5 = this._SelectedItems.Contains(this._Items[Index]);
							if (flag5)
							{
								this._SelectedItems.Remove(this._Items[Index]);
							}
							else
							{
								this._SelectedItems.Add(this._Items[Index]);
							}
						}
						else
						{
							this._SelectedItems.Clear();
							this._SelectedItems.Add(this._Items[Index]);
						}
						Debug.Print(this._SelectedItems[0].Text);
					}
					base.Invalidate();
				}
				base.OnMouseDown(e);
			}
		}

		// Token: 0x060003CF RID: 975 RVA: 0x0001415C File Offset: 0x0001235C
		protected override void OnMouseWheel(MouseEventArgs e)
		{
			checked
			{
				int Move = 0 - e.Delta * SystemInformation.MouseWheelScrollLines / 120 * 1;
				int Value = Math.Max(Math.Min(this.VerticalScrollbar.Value + Move, this.VerticalScrollbar.Maximum), this.VerticalScrollbar.Minimum);
				this.VerticalScrollbar.Value = Value;
				base.OnMouseWheel(e);
			}
		}

		// Token: 0x060003D0 RID: 976 RVA: 0x000141C4 File Offset: 0x000123C4
		public LogInListBoxWBuiltInScrollBar()
		{
			this._Items = new List<LogInListBoxWBuiltInScrollBar.LogInListBoxItem>();
			this._SelectedItems = new List<LogInListBoxWBuiltInScrollBar.LogInListBoxItem>();
			this._MultiSelect = true;
			this.ItemHeight = 24;
			this._BaseColour = Color.FromArgb(55, 55, 55);
			this._SelectedItemColour = Color.FromArgb(50, 50, 50);
			this._NonSelectedItemColour = Color.FromArgb(47, 47, 47);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._SelectedHeight = 1;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.VerticalScrollbar = new LogInVerticalScrollBar();
			this.VerticalScrollbar._SmallChange = 1;
			this.VerticalScrollbar._LargeChange = 1;
			this.VerticalScrollbar.Scroll += this.HandleScroll;
			this.VerticalScrollbar.MouseDown += this.Vertical_MouseDown;
			base.Controls.Add(this.VerticalScrollbar);
			this.InvalidateLayout();
		}

		// Token: 0x060003D1 RID: 977 RVA: 0x000142E4 File Offset: 0x000124E4
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			Graphics graphics = g;
			graphics.TextRenderingHint = TextRenderingHint.ClearTypeGridFit;
			graphics.SmoothingMode = SmoothingMode.HighQuality;
			graphics.PixelOffsetMode = PixelOffsetMode.HighQuality;
			graphics.Clear(this._BaseColour);
			checked
			{
				int Offset = this.VerticalScrollbar.Value * (this.VerticalScrollbar.Maximum + (base.Height - this.ItemHeight));
				bool flag = Offset == 0;
				int StartIndex;
				if (flag)
				{
					StartIndex = 0;
				}
				else
				{
					StartIndex = Offset / this.ItemHeight / this.VerticalScrollbar.Maximum;
				}
				int EndIndex = Math.Min(StartIndex + base.Height / this.ItemHeight, this._Items.Count - 1);
				graphics.DrawLine(new Pen(this._BorderColour, 2f), this.VerticalScrollbar.Location.X - 1, 0, this.VerticalScrollbar.Location.X - 1, base.Height);
				int num = StartIndex;
				int num2 = this._Items.Count - 1;
				for (int I = num; I <= num2; I++)
				{
					LogInListBoxWBuiltInScrollBar.LogInListBoxItem AllItems = this.Items[I];
					int Y = I * this.ItemHeight + 1 - Offset + (int)Math.Round(unchecked((double)this.ItemHeight / 2.0 - 8.0));
					bool flag2 = this._SelectedItems.Contains(AllItems);
					if (flag2)
					{
						graphics.FillRectangle(new SolidBrush(this._SelectedItemColour), new Rectangle(0, I * this.ItemHeight + 1 - Offset, base.Width - 19, this.ItemHeight - 1));
					}
					else
					{
						graphics.FillRectangle(new SolidBrush(this._NonSelectedItemColour), new Rectangle(0, I * this.ItemHeight + 1 - Offset, base.Width - 19, this.ItemHeight - 1));
					}
					graphics.DrawLine(new Pen(this._BorderColour), 0, I * this.ItemHeight + 1 - Offset + this.ItemHeight - 1, base.Width - 18, I * this.ItemHeight + 1 - Offset + this.ItemHeight - 1);
					graphics.DrawString(AllItems.Text, new Font("Segoe UI", 8f), new SolidBrush(this._TextColour), 9f, (float)Y);
					graphics.ResetClip();
				}
				graphics.DrawRectangle(new Pen(Color.FromArgb(35, 35, 35), 2f), 1, 1, base.Width - 2, base.Height - 2);
				graphics.DrawLine(new Pen(this._BorderColour, 2f), this.VerticalScrollbar.Location.X - 1, 0, this.VerticalScrollbar.Location.X - 1, base.Height);
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000185 RID: 389
		private List<LogInListBoxWBuiltInScrollBar.LogInListBoxItem> _Items;

		// Token: 0x04000186 RID: 390
		private readonly List<LogInListBoxWBuiltInScrollBar.LogInListBoxItem> _SelectedItems;

		// Token: 0x04000187 RID: 391
		private bool _MultiSelect;

		// Token: 0x04000188 RID: 392
		private int ItemHeight;

		// Token: 0x04000189 RID: 393
		private readonly LogInVerticalScrollBar VerticalScrollbar;

		// Token: 0x0400018A RID: 394
		private Color _BaseColour;

		// Token: 0x0400018B RID: 395
		private Color _SelectedItemColour;

		// Token: 0x0400018C RID: 396
		private Color _NonSelectedItemColour;

		// Token: 0x0400018D RID: 397
		private Color _BorderColour;

		// Token: 0x0400018E RID: 398
		private Color _TextColour;

		// Token: 0x0400018F RID: 399
		private int _SelectedHeight;

		// Token: 0x0200003C RID: 60
		public class LogInListBoxItem
		{
			// Token: 0x17000153 RID: 339
			// (get) Token: 0x060003D3 RID: 979 RVA: 0x00003866 File Offset: 0x00001A66
			// (set) Token: 0x060003D4 RID: 980 RVA: 0x00003870 File Offset: 0x00001A70
			public string Text { get; set; }

			// Token: 0x060003D5 RID: 981 RVA: 0x000145C8 File Offset: 0x000127C8
			public override string ToString()
			{
				return this.Text;
			}
		}
	}
}
