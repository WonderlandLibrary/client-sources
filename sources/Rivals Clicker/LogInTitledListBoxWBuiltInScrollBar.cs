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
	// Token: 0x02000039 RID: 57
	public class LogInTitledListBoxWBuiltInScrollBar : Control
	{
		// Token: 0x1700013D RID: 317
		// (get) Token: 0x0600038A RID: 906 RVA: 0x0001316C File Offset: 0x0001136C
		// (set) Token: 0x0600038B RID: 907 RVA: 0x000036D5 File Offset: 0x000018D5
		[Category("Colours")]
		public Color TitleAreaColour
		{
			get
			{
				return this._TitleAreaColour;
			}
			set
			{
				this._TitleAreaColour = value;
			}
		}

		// Token: 0x1700013E RID: 318
		// (get) Token: 0x0600038C RID: 908 RVA: 0x00013184 File Offset: 0x00011384
		// (set) Token: 0x0600038D RID: 909 RVA: 0x000036DF File Offset: 0x000018DF
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

		// Token: 0x1700013F RID: 319
		// (get) Token: 0x0600038E RID: 910 RVA: 0x0001319C File Offset: 0x0001139C
		// (set) Token: 0x0600038F RID: 911 RVA: 0x000131B4 File Offset: 0x000113B4
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

		// Token: 0x17000140 RID: 320
		// (get) Token: 0x06000390 RID: 912 RVA: 0x000131EC File Offset: 0x000113EC
		// (set) Token: 0x06000391 RID: 913 RVA: 0x000036E9 File Offset: 0x000018E9
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

		// Token: 0x17000141 RID: 321
		// (get) Token: 0x06000392 RID: 914 RVA: 0x00013204 File Offset: 0x00011404
		// (set) Token: 0x06000393 RID: 915 RVA: 0x000036F3 File Offset: 0x000018F3
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

		// Token: 0x17000142 RID: 322
		// (get) Token: 0x06000394 RID: 916 RVA: 0x0001321C File Offset: 0x0001141C
		// (set) Token: 0x06000395 RID: 917 RVA: 0x000036FD File Offset: 0x000018FD
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

		// Token: 0x17000143 RID: 323
		// (get) Token: 0x06000396 RID: 918 RVA: 0x00013234 File Offset: 0x00011434
		// (set) Token: 0x06000397 RID: 919 RVA: 0x00003707 File Offset: 0x00001907
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

		// Token: 0x06000398 RID: 920 RVA: 0x00003711 File Offset: 0x00001911
		private void HandleScroll(object sender)
		{
			base.Invalidate();
		}

		// Token: 0x06000399 RID: 921 RVA: 0x0001324C File Offset: 0x0001144C
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

		// Token: 0x0600039A RID: 922 RVA: 0x0001334C File Offset: 0x0001154C
		private void InvalidateLayout()
		{
			checked
			{
				this.VerticalScrollbar.Location = new Point(base.Width - this.VerticalScrollbar.Width - 2, 2);
				this.VerticalScrollbar.Size = new Size(18, base.Height - 4);
				base.Invalidate();
			}
		}

		// Token: 0x17000144 RID: 324
		// (get) Token: 0x0600039B RID: 923 RVA: 0x000133A4 File Offset: 0x000115A4
		// (set) Token: 0x0600039C RID: 924 RVA: 0x0000371B File Offset: 0x0000191B
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
		public LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem[] Items
		{
			get
			{
				return this._Items.ToArray();
			}
			set
			{
				this._Items = new List<LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem>(value);
				base.Invalidate();
				this.InvalidateScroll();
			}
		}

		// Token: 0x17000145 RID: 325
		// (get) Token: 0x0600039D RID: 925 RVA: 0x000133C4 File Offset: 0x000115C4
		public LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem[] SelectedItems
		{
			get
			{
				return this._SelectedItems.ToArray();
			}
		}

		// Token: 0x17000146 RID: 326
		// (get) Token: 0x0600039E RID: 926 RVA: 0x000133E4 File Offset: 0x000115E4
		// (set) Token: 0x0600039F RID: 927 RVA: 0x000133FC File Offset: 0x000115FC
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

		// Token: 0x17000147 RID: 327
		// (get) Token: 0x060003A0 RID: 928 RVA: 0x0000ACB4 File Offset: 0x00008EB4
		// (set) Token: 0x060003A1 RID: 929 RVA: 0x00013448 File Offset: 0x00011648
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

		// Token: 0x060003A2 RID: 930 RVA: 0x000134BC File Offset: 0x000116BC
		public void AddItem(string Items)
		{
			LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem Item = new LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem();
			Item.Text = Items;
			this._Items.Add(Item);
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003A3 RID: 931 RVA: 0x000134F4 File Offset: 0x000116F4
		public void AddItems(string[] Items)
		{
			foreach (string I in Items)
			{
				LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem Item = new LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem();
				Item.Text = I;
				this._Items.Add(Item);
			}
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003A4 RID: 932 RVA: 0x00003738 File Offset: 0x00001938
		public void RemoveItemAt(int index)
		{
			this._Items.RemoveAt(index);
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003A5 RID: 933 RVA: 0x00003756 File Offset: 0x00001956
		public void RemoveItem(LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem item)
		{
			this._Items.Remove(item);
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003A6 RID: 934 RVA: 0x00013548 File Offset: 0x00011748
		public void RemoveItems(LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem[] items)
		{
			foreach (LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem I in items)
			{
				this._Items.Remove(I);
			}
			base.Invalidate();
			this.InvalidateScroll();
		}

		// Token: 0x060003A7 RID: 935 RVA: 0x00003774 File Offset: 0x00001974
		protected override void OnSizeChanged(EventArgs e)
		{
			this._SelectedHeight = base.Height;
			this.InvalidateScroll();
			this.InvalidateLayout();
			base.OnSizeChanged(e);
		}

		// Token: 0x060003A8 RID: 936 RVA: 0x00003799 File Offset: 0x00001999
		private void Vertical_MouseDown(object sender, MouseEventArgs e)
		{
			base.Focus();
		}

		// Token: 0x060003A9 RID: 937 RVA: 0x0001358C File Offset: 0x0001178C
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

		// Token: 0x060003AA RID: 938 RVA: 0x000136D4 File Offset: 0x000118D4
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

		// Token: 0x060003AB RID: 939 RVA: 0x0001373C File Offset: 0x0001193C
		public LogInTitledListBoxWBuiltInScrollBar()
		{
			this._Items = new List<LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem>();
			this._SelectedItems = new List<LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem>();
			this._MultiSelect = true;
			this.ItemHeight = 24;
			this._BaseColour = Color.FromArgb(55, 55, 55);
			this._SelectedItemColour = Color.FromArgb(50, 50, 50);
			this._NonSelectedItemColour = Color.FromArgb(47, 47, 47);
			this._TitleAreaColour = Color.FromArgb(42, 42, 42);
			this._BorderColour = Color.FromArgb(35, 35, 35);
			this._TextColour = Color.FromArgb(255, 255, 255);
			this._SelectedHeight = 1;
			base.SetStyle(ControlStyles.UserPaint | ControlStyles.ResizeRedraw | ControlStyles.Selectable | ControlStyles.SupportsTransparentBackColor | ControlStyles.AllPaintingInWmPaint | ControlStyles.OptimizedDoubleBuffer, true);
			this.DoubleBuffered = true;
			this.VerticalScrollbar = new LogInVerticalScrollBar();
			this.VerticalScrollbar.SmallChange = 1;
			this.VerticalScrollbar.LargeChange = 1;
			this.VerticalScrollbar.Scroll += this.HandleScroll;
			this.VerticalScrollbar.MouseDown += this.Vertical_MouseDown;
			base.Controls.Add(this.VerticalScrollbar);
			this.InvalidateLayout();
		}

		// Token: 0x060003AC RID: 940 RVA: 0x00013870 File Offset: 0x00011A70
		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics G = e.Graphics;
			Graphics graphics = G;
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
				int num = StartIndex;
				int num2 = this._Items.Count - 1;
				for (int I = num; I <= num2; I++)
				{
					LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem AllItems = this.Items[I];
					int Y = this.ItemHeight + I * this.ItemHeight + 1 - Offset + (int)Math.Round(unchecked((double)this.ItemHeight / 2.0 - 8.0));
					bool flag2 = this._SelectedItems.Contains(AllItems);
					if (flag2)
					{
						graphics.FillRectangle(new SolidBrush(this._SelectedItemColour), new Rectangle(0, this.ItemHeight + I * this.ItemHeight + 1 - Offset, base.Width - 19, this.ItemHeight - 1));
					}
					else
					{
						graphics.FillRectangle(new SolidBrush(this._NonSelectedItemColour), new Rectangle(0, this.ItemHeight + I * this.ItemHeight + 1 - Offset, base.Width - 19, this.ItemHeight - 1));
					}
					graphics.DrawLine(new Pen(this._BorderColour), 0, this.ItemHeight + I * this.ItemHeight + 1 - Offset + this.ItemHeight - 1, base.Width - 18, this.ItemHeight + I * this.ItemHeight + 1 - Offset + this.ItemHeight - 1);
					graphics.DrawString(AllItems.Text, new Font("Segoe UI", 8f), new SolidBrush(this._TextColour), 9f, (float)Y);
					graphics.ResetClip();
				}
				graphics.FillRectangle(new SolidBrush(this._TitleAreaColour), new Rectangle(0, 0, base.Width, this.ItemHeight));
				graphics.DrawRectangle(new Pen(Color.FromArgb(35, 35, 35)), 1, 1, base.Width - 3, this.ItemHeight - 2);
				graphics.DrawString(this.Text, new Font("Segoe UI", 10f, FontStyle.Bold), new SolidBrush(this._TextColour), new Rectangle(0, 0, base.Width, this.ItemHeight + 2), new StringFormat
				{
					Alignment = StringAlignment.Center,
					LineAlignment = StringAlignment.Center
				});
				graphics.DrawRectangle(new Pen(Color.FromArgb(35, 35, 35), 2f), 1, 0, base.Width - 2, base.Height - 1);
				graphics.DrawLine(new Pen(this._BorderColour), 0, this.ItemHeight, base.Width, this.ItemHeight);
				graphics.DrawLine(new Pen(this._BorderColour, 2f), this.VerticalScrollbar.Location.X - 1, 0, this.VerticalScrollbar.Location.X - 1, base.Height);
				graphics.InterpolationMode = InterpolationMode.HighQualityBicubic;
			}
		}

		// Token: 0x04000178 RID: 376
		private List<LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem> _Items;

		// Token: 0x04000179 RID: 377
		private readonly List<LogInTitledListBoxWBuiltInScrollBar.LogInListBoxItem> _SelectedItems;

		// Token: 0x0400017A RID: 378
		private bool _MultiSelect;

		// Token: 0x0400017B RID: 379
		private int ItemHeight;

		// Token: 0x0400017C RID: 380
		private readonly LogInVerticalScrollBar VerticalScrollbar;

		// Token: 0x0400017D RID: 381
		private Color _BaseColour;

		// Token: 0x0400017E RID: 382
		private Color _SelectedItemColour;

		// Token: 0x0400017F RID: 383
		private Color _NonSelectedItemColour;

		// Token: 0x04000180 RID: 384
		private Color _TitleAreaColour;

		// Token: 0x04000181 RID: 385
		private Color _BorderColour;

		// Token: 0x04000182 RID: 386
		private Color _TextColour;

		// Token: 0x04000183 RID: 387
		private int _SelectedHeight;

		// Token: 0x0200003A RID: 58
		public class LogInListBoxItem
		{
			// Token: 0x17000148 RID: 328
			// (get) Token: 0x060003AE RID: 942 RVA: 0x000037A3 File Offset: 0x000019A3
			// (set) Token: 0x060003AF RID: 943 RVA: 0x000037AD File Offset: 0x000019AD
			public string Text { get; set; }

			// Token: 0x060003B0 RID: 944 RVA: 0x00013BF4 File Offset: 0x00011DF4
			public override string ToString()
			{
				return this.Text;
			}
		}
	}
}
