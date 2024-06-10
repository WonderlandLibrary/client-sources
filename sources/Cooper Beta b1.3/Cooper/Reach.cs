using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Windows.Forms;
using Cooper;

namespace ns0;

public static class Reach
{
	public static bool bool_0 = false;

	public static bool bool_1;

	public static List<long> list_0 = new List<long>();

	public static List<long> list_1 = new List<long>();

	public static List<long> list_2 = new List<long>();

	public static List<long> list_3 = new List<long>();

	public static void Reach0()
	{
		Memory gClass = new Memory(Form1.uint_0);
		while (true)
		{
			double num = new Random().Next(306, 309) / 100.0;
			double num2 = 4.5 + num - 3.0;
            Random random = new Random();
            bool result = random.NextDouble() < 0.75;
            if (!bool_1)
			{
				foreach (long item in list_2.ToList())
				{
					foreach (long item2 in list_1.ToList())
					{
						double num3 = gClass.method_1<double>(item);
						float num4 = gClass.method_1<float>(item2);
						string text = item.ToString("X").Substring(0, item.ToString().Length - 6);
						string text2 = item2.ToString("X").Substring(0, item2.ToString().Length - 6);
						if (!(text == text2))
						{
							continue;
						}
						if (num3 <= 7.5 && num3 >= 4.5)
						{
							if ((double)num4 <= 6.0 && (double)num4 >= 3.0)
							{
								if ((result && Form1.MainF.coophitcb.Checked && !Form1.MainF.cbMoving.Checked) || (Form1.MainF.coophitcb.Checked && MovingCheck()))
								{
									gClass.method_4(item2, (float)num);
									gClass.method_5(item, num2);
								}
								else
								{
									gClass.method_4(item2, 3f);
									gClass.method_5(item, 4.5);
								}
							}
							else
							{
								list_1.Remove(item2);
							}
						}
						else
						{
							list_2.Remove(item);
						}
					}
				}
			}
			if (!bool_1)
			{
				foreach (long item3 in list_2.ToList())
				{
					foreach (long item4 in list_0.ToList())
					{
						double num5 = gClass.method_1<double>(item3);
						double num6 = gClass.method_1<double>(item4);
						string text3 = item3.ToString("X").Substring(0, item3.ToString().Length - 6);
						string text4 = item4.ToString("X").Substring(0, item4.ToString().Length - 6);
						if (!(text3 == text4))
						{
							continue;
						}
						if (num5 <= 7.5 && num5 >= 4.5)
						{
							if (num6 <= 6.0 && num6 >= 3.0)
							{
								if ((result && Form1.MainF.coophitcb.Checked && !Form1.MainF.cbMoving.Checked) || (Form1.MainF.coophitcb.Checked && MovingCheck()))
								{
									gClass.method_5(item4, num);
									gClass.method_5(item3, num2);
								}
								else
								{
									gClass.method_5(item4, 3.0);
									gClass.method_5(item3, 4.5);
								}
							}
							else
							{
								list_0.Remove(item4);
							}
						}
						else
						{
							list_2.Remove(item3);
						}
					}
				}
			}
			if (!bool_1)
			{
				foreach (long item5 in list_3.ToList())
				{
					foreach (long item6 in list_0.ToList())
					{
						float num7 = gClass.method_1<float>(item5);
						double num8 = gClass.method_1<double>(item6);
						string text5 = item5.ToString("X").Substring(0, item5.ToString().Length - 6);
						string text6 = item6.ToString("X").Substring(0, item6.ToString().Length - 6);
						if (!(text5 == text6))
						{
							continue;
						}
						if ((double)num7 <= 7.5 && (double)num7 >= 4.5)
						{
							if (num8 <= 6.0 && num8 >= 3.0)
							{
								if ((result && Form1.MainF.coophitcb.Checked && !Form1.MainF.cbMoving.Checked) || (Form1.MainF.coophitcb.Checked && MovingCheck()))
								{
									gClass.method_5(item6, num);
									gClass.method_4(item5, (float)num2);
								}
								else
								{
									gClass.method_5(item6, 3.0);
									gClass.method_4(item5, 4.5f);
								}
							}
							else
							{
								list_0.Remove(item6);
							}
						}
						else
						{
							list_3.Remove(item5);
						}
					}
				}
			}
			Thread.Sleep(500);
		}
	}

	public static void Reach1()
	{
		Memory gClass = new Memory(Form1.uint_0);
		while (true)
		{
			if (!bool_0)
			{
				bool_1 = true;
				foreach (long item in gClass.method_2(3f))
				{
					if (!list_1.Contains(item))
					{
						list_1.Add(item);
					}
				}
				foreach (long item2 in gClass.method_3(3.0))
				{
					if (!list_0.Contains(item2))
					{
						list_0.Add(item2);
					}
				}
				foreach (long item3 in gClass.method_3(4.5))
				{
					if (!list_2.Contains(item3))
					{
						list_2.Add(item3);
					}
				}
				foreach (long item4 in gClass.method_2(4.5f))
				{
					if (!list_3.Contains(item4))
					{
						list_3.Add(item4);
					}
				}
				bool_1 = false;
			}
			Thread.Sleep(5000);
		}
	}

	public static bool MovingCheck()
	{
		if (Locator.GetAsyncKeyState_1(Keys.W) >= 0 && Locator.GetAsyncKeyState_1(Keys.A) >= 0 && Locator.GetAsyncKeyState_1(Keys.S) >= 0)
		{
			return Locator.GetAsyncKeyState_1(Keys.D) < 0;
		}
		return true;
	}
}
