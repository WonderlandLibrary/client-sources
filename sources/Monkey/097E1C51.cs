using System;
using System.Collections.Generic;
using System.Reflection;
using System.Reflection.Emit;
using System.Runtime.InteropServices;
using System.Threading;

// Token: 0x02000043 RID: 67
public class 097E1C51
{
	// Token: 0x060001D4 RID: 468 RVA: 0x002F0E7C File Offset: 0x0017127C
	public 097E1C51()
	{
		uint num;
		do
		{
			IL_00:
			this.0FEE0E1F = new Dictionary<uint, 097E1C51.1F7F0274>();
			num = 1066819877U;
			for (;;)
			{
				IL_11:
				Type typeFromHandle = typeof(097E1C51);
				num /= 1060982443U;
				this.45C00733 = typeFromHandle.Module;
				for (;;)
				{
					IL_2E:
					num = 1573736055U / num;
					Stack<097E1C51.4A781DBA> stack = new Stack<097E1C51.4A781DBA>();
					num = 977823351U % num;
					this.02C11BDF = stack;
					for (;;)
					{
						IL_49:
						num = 705759681U >> (int)num;
						this.53780069 = new List<097E1C51.0A706168>();
						num = 167605008U / num;
						if (568480801U != num)
						{
							for (;;)
							{
								IL_75:
								this.652B3F15 = new List<097E1C51.5C236423>();
								for (;;)
								{
									IL_80:
									num %= 386473958U;
									Stack<097E1C51.5C236423> stack2 = new Stack<097E1C51.5C236423>();
									num = 1675317390U % num;
									this.77FC1BC6 = stack2;
									num = (529689722U | num);
									do
									{
										this.1D9D066A = new Stack<int>();
										if (num > 976171882U)
										{
											goto IL_2E;
										}
										do
										{
											this.4FEE2A5A = new List<IntPtr>();
											num <<= 8;
											base..ctor();
											if (num << 1 == 0U)
											{
												goto IL_75;
											}
											num &= 1856332164U;
											num >>= 0;
											Module m = this.45C00733;
											num = 1763646242U - num;
											long num2 = Marshal.GetHINSTANCE(m).ToInt64();
											num >>= 11;
											this.4D045382 = num2;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary = this.0FEE0E1F;
											num = 830232778U * num;
											uint key = num ^ 1276256934U;
											num /= 1991731386U;
											IntPtr 012C2F = ldftn(011F0F15);
											num *= 2089181363U;
											dictionary[key] = new 097E1C51.1F7F0274(this, 012C2F);
											num /= 663251782U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary2 = this.0FEE0E1F;
											num -= 459368989U;
											uint key2 = num ^ 3835598306U;
											num = 1021143925U % num;
											IntPtr 012C2F2 = ldftn(31BD3698);
											num = (1108951283U ^ num);
											097E1C51.1F7F0274 value = new 097E1C51.1F7F0274(this, 012C2F2);
											num |= 607334892U;
											dictionary2[key2] = value;
											num = 1193366271U << (int)num;
											if (num > 1707869164U)
											{
												goto IL_00;
											}
											num |= 600059784U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary3 = this.0FEE0E1F;
											uint key3 = num - 2013261702U;
											num = 1022514033U % num;
											IntPtr 012C2F3 = ldftn(5A1954EC);
											num /= 654206448U;
											097E1C51.1F7F0274 value2 = new 097E1C51.1F7F0274(this, 012C2F3);
											num += 1845107959U;
											dictionary3[key3] = value2;
											if (num < 1076060590U)
											{
												goto IL_00;
											}
											num = 1289643510U >> (int)num;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary4 = this.0FEE0E1F;
											uint key4 = num + 4294967223U;
											num /= 1645887819U;
											097E1C51.1F7F0274 value3 = new 097E1C51.1F7F0274(this.51997215);
											num &= 1107568814U;
											dictionary4[key4] = value3;
											num %= 970857500U;
											num = (930618300U & num);
											Dictionary<uint, 097E1C51.1F7F0274> dictionary5 = this.0FEE0E1F;
											uint key5 = num ^ 4U;
											num |= 668869457U;
											dictionary5[key5] = new 097E1C51.1F7F0274(this.6B0F6D44);
											Dictionary<uint, 097E1C51.1F7F0274> dictionary6 = this.0FEE0E1F;
											num = 1709002059U >> (int)num;
											uint key6 = num - 13033U;
											IntPtr 012C2F4 = ldftn(27F7218F);
											num = 989620809U + num;
											097E1C51.1F7F0274 value4 = new 097E1C51.1F7F0274(this, 012C2F4);
											num /= 1347096310U;
											dictionary6[key6] = value4;
											if (252314996U <= num)
											{
												goto IL_00;
											}
											num <<= 25;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary7 = this.0FEE0E1F;
											num = (1084890387U & num);
											uint key7 = num ^ 6U;
											IntPtr 012C2F5 = ldftn(5AFD3DDB);
											num >>= 12;
											dictionary7[key7] = new 097E1C51.1F7F0274(this, 012C2F5);
											num &= 1680223004U;
											num = 487079151U << (int)num;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary8 = this.0FEE0E1F;
											uint key8 = num + 3807888152U;
											num = (1739142468U & num);
											097E1C51.1F7F0274 value5 = new 097E1C51.1F7F0274(this.4C135968);
											num = 1030358881U << (int)num;
											dictionary8[key8] = value5;
											if (1311917264U % num == 0U)
											{
												goto IL_2E;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary9 = this.0FEE0E1F;
											num <<= 3;
											uint key9 = num ^ 3036917896U;
											num <<= 7;
											dictionary9[key9] = new 097E1C51.1F7F0274(this.598E21C0);
											if (num <= 1867072275U)
											{
												goto IL_00;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary10 = this.0FEE0E1F;
											uint key10 = num + 2116534281U;
											num >>= 11;
											num += 447377421U;
											097E1C51.1F7F0274 value6 = new 097E1C51.1F7F0274(this.5393093A);
											num = 381571206U - num;
											dictionary10[key10] = value6;
											if (num < 987192755U)
											{
												goto IL_11;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary11 = this.0FEE0E1F;
											num = (982586935U & num);
											uint key11 = num ^ 939590715U;
											num *= 99172418U;
											num &= 1195790304U;
											IntPtr 012C2F6 = ldftn(18784820);
											num = (1572434527U | num);
											097E1C51.1F7F0274 value7 = new 097E1C51.1F7F0274(this, 012C2F6);
											num >>= 2;
											dictionary11[key11] = value7;
											if (num - 1621446882U == 0U)
											{
												goto IL_11;
											}
											num -= 9057814U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary12 = this.0FEE0E1F;
											num = 401107892U >> (int)num;
											uint key12 = num ^ 783422U;
											num = 826107695U - num;
											num = 1735939644U >> (int)num;
											IntPtr 012C2F7 = ldftn(2DD92D6C);
											num = 1655394087U - num;
											097E1C51.1F7F0274 value8 = new 097E1C51.1F7F0274(this, 012C2F7);
											num = 311042383U >> (int)num;
											dictionary12[key12] = value8;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary13 = this.0FEE0E1F;
											uint key13 = num ^ 18980U;
											num = 1060593662U - num;
											num = 2073633184U << (int)num;
											IntPtr 012C2F8 = ldftn(728D144D);
											num = 1132207347U * num;
											097E1C51.1F7F0274 value9 = new 097E1C51.1F7F0274(this, 012C2F8);
											num <<= 11;
											dictionary13[key13] = value9;
											num = 1927834436U + num;
											if (129109885U >> (int)num == 0U)
											{
												goto IL_00;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary14 = this.0FEE0E1F;
											num ^= 483878210U;
											uint key14 = num ^ 1849626123U;
											num = 1897559672U % num;
											IntPtr 012C2F9 = ldftn(446A7FC1);
											num *= 998072948U;
											097E1C51.1F7F0274 value10 = new 097E1C51.1F7F0274(this, 012C2F9);
											num <<= 23;
											dictionary14[key14] = value10;
											if (1713535049U > num)
											{
												goto IL_00;
											}
											num = 1354001336U / num;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary15 = this.0FEE0E1F;
											uint key15 = num + 14U;
											num = 526679767U - num;
											IntPtr 012C2F10 = ldftn(67D63995);
											num >>= 4;
											097E1C51.1F7F0274 value11 = new 097E1C51.1F7F0274(this, 012C2F10);
											num %= 4469613U;
											dictionary15[key15] = value11;
											num = 1641556306U % num;
											if (1598170173U >> (int)num == 0U)
											{
												goto IL_49;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary16 = this.0FEE0E1F;
											num %= 1673359580U;
											uint key16 = num - 1581127U;
											num &= 591537126U;
											IntPtr 012C2F11 = ldftn(4F7A0A45);
											num ^= 1805453421U;
											097E1C51.1F7F0274 value12 = new 097E1C51.1F7F0274(this, 012C2F11);
											num |= 756623720U;
											dictionary16[key16] = value12;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary17 = this.0FEE0E1F;
											num = 1557874150U + num;
											uint key17 = num + 864522431U;
											num = (1029507868U ^ num);
											IntPtr 012C2F12 = ldftn(5C162AE0);
											num = 2078678730U - num;
											097E1C51.1F7F0274 value13 = new 097E1C51.1F7F0274(this, 012C2F12);
											num |= 1526028776U;
											dictionary17[key17] = value13;
											num = 1715150824U << (int)num;
											num >>= 16;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary18 = this.0FEE0E1F;
											num >>= 23;
											uint key18 = num ^ 17U;
											num -= 1929920267U;
											097E1C51.1F7F0274 value14 = new 097E1C51.1F7F0274(this.265604A2);
											num >>= 5;
											dictionary18[key18] = value14;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary19 = this.0FEE0E1F;
											num *= 25439570U;
											uint key19 = num ^ 2822641964U;
											num = 1143620543U * num;
											097E1C51.1F7F0274 value15 = new 097E1C51.1F7F0274(this.71393749);
											num = 1560087234U >> (int)num;
											dictionary19[key19] = value15;
											num = 77791502U / num;
											num += 587883065U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary20 = this.0FEE0E1F;
											num = 1368415406U >> (int)num;
											uint key20 = num + 4294967275U;
											num >>= 11;
											097E1C51.1F7F0274 value16 = new 097E1C51.1F7F0274(this.5CB74094);
											num = 1249268304U * num;
											dictionary20[key20] = value16;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary21 = this.0FEE0E1F;
											uint key21 = num - 4294967276U;
											num = 274033073U - num;
											num |= 2102806884U;
											IntPtr 012C2F13 = ldftn(1A657FE9);
											num /= 1375093038U;
											097E1C51.1F7F0274 value17 = new 097E1C51.1F7F0274(this, 012C2F13);
											num = 691481739U + num;
											dictionary21[key21] = value17;
											num += 1324164219U;
											if (2017547780U * num == 0U)
											{
												goto IL_00;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary22 = this.0FEE0E1F;
											num <<= 27;
											uint key22 = num ^ 939524117U;
											097E1C51.1F7F0274 value18 = new 097E1C51.1F7F0274(this.130D57EB);
											num /= 623003798U;
											dictionary22[key22] = value18;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary23 = this.0FEE0E1F;
											uint key23 = num ^ 23U;
											num *= 2077780325U;
											dictionary23[key23] = new 097E1C51.1F7F0274(this.060F5510);
											num <<= 4;
											num ^= 100953539U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary24 = this.0FEE0E1F;
											uint key24 = num - 3145923452U;
											num *= 144658935U;
											IntPtr 012C2F14 = ldftn(155F5DB6);
											num += 719272906U;
											097E1C51.1F7F0274 value19 = new 097E1C51.1F7F0274(this, 012C2F14);
											num |= 917182221U;
											dictionary24[key24] = value19;
											num /= 1952074555U;
											if (num == 523581176U)
											{
												goto IL_00;
											}
											num *= 567414412U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary25 = this.0FEE0E1F;
											num = (1890138831U ^ num);
											uint key25 = num ^ 1367028827U;
											num = 713838302U + num;
											IntPtr 012C2F15 = ldftn(566A6686);
											num &= 1275138032U;
											dictionary25[key25] = new 097E1C51.1F7F0274(this, 012C2F15);
											num = (2058961629U | num);
											if ((num ^ 482225044U) == 0U)
											{
												goto IL_49;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary26 = this.0FEE0E1F;
											uint key26 = num ^ 2126070756U;
											IntPtr 012C2F16 = ldftn(777F2AAE);
											num = 986802345U - num;
											097E1C51.1F7F0274 value20 = new 097E1C51.1F7F0274(this, 012C2F16);
											num *= 490879506U;
											dictionary26[key26] = value20;
											if (219828425U + num == 0U)
											{
												goto IL_11;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary27 = this.0FEE0E1F;
											num = 1309093919U / num;
											uint key27 = num ^ 26U;
											num = 884371015U >> (int)num;
											097E1C51.1F7F0274 value21 = new 097E1C51.1F7F0274(this.0FB617A4);
											num |= 248518374U;
											dictionary27[key27] = value21;
											num = 1084963232U % num;
											if (num > 1401175857U)
											{
												goto IL_00;
											}
											num &= 1595880163U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary28 = this.0FEE0E1F;
											uint key28 = num + 4276866426U;
											num -= 182262423U;
											dictionary28[key28] = new 097E1C51.1F7F0274(this.09B23045);
											if (1763996388U / num != 0U)
											{
												goto IL_00;
											}
											num >>= 17;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary29 = this.0FEE0E1F;
											uint key29 = num ^ 31495U;
											num = (877483454U ^ num);
											num /= 1219588975U;
											dictionary29[key29] = new 097E1C51.1F7F0274(this.778E5B5E);
											num = (1690518811U | num);
											Dictionary<uint, 097E1C51.1F7F0274> dictionary30 = this.0FEE0E1F;
											num -= 2019518302U;
											uint key30 = num - 3965967776U;
											097E1C51.1F7F0274 value22 = new 097E1C51.1F7F0274(this.6075045E);
											num = (1327194562U ^ num);
											dictionary30[key30] = value22;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary31 = this.0FEE0E1F;
											num ^= 231219168U;
											uint key31 = num ^ 2930811777U;
											num = (768638532U & num);
											IntPtr 012C2F17 = ldftn(111A690C);
											num *= 138297514U;
											097E1C51.1F7F0274 value23 = new 097E1C51.1F7F0274(this, 012C2F17);
											num *= 2021617369U;
											dictionary31[key31] = value23;
											num = 220162543U << (int)num;
											num -= 2134125862U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary32 = this.0FEE0E1F;
											uint key32 = num + 1607089733U;
											num *= 801335372U;
											IntPtr 012C2F18 = ldftn(35F356F3);
											num <<= 3;
											dictionary32[key32] = new 097E1C51.1F7F0274(this, 012C2F18);
											num += 1010845325U;
											num <<= 18;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary33 = this.0FEE0E1F;
											num += 1090479481U;
											uint key33 = num - 4063454553U;
											num &= 701825844U;
											dictionary33[key33] = new 097E1C51.1F7F0274(this.5B3318A6);
											num = (338322400U & num);
											if (736191200U * num == 0U)
											{
												goto IL_00;
											}
											num /= 1947032530U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary34 = this.0FEE0E1F;
											num >>= 13;
											uint key34 = num - 4294967263U;
											num = 122444909U - num;
											IntPtr 012C2F19 = ldftn(7B835829);
											num |= 646188230U;
											097E1C51.1F7F0274 value24 = new 097E1C51.1F7F0274(this, 012C2F19);
											num <<= 4;
											dictionary34[key34] = value24;
											num *= 2082169851U;
											if (num <= 1242975105U)
											{
												goto IL_2E;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary35 = this.0FEE0E1F;
											num = 2081627761U - num;
											uint key35 = num ^ 2479515907U;
											num &= 2126914303U;
											IntPtr 012C2F20 = ldftn(649257A5);
											num -= 878405466U;
											dictionary35[key35] = new 097E1C51.1F7F0274(this, 012C2F20);
											num *= 1632830846U;
											if (num == 1877808632U)
											{
												goto IL_11;
											}
											num *= 1242122553U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary36 = this.0FEE0E1F;
											num &= 52510764U;
											uint key36 = num ^ 35733507U;
											num = 1517190037U / num;
											dictionary36[key36] = new 097E1C51.1F7F0274(this.44D61E98);
											num = 2040404074U >> (int)num;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary37 = this.0FEE0E1F;
											uint key37 = num ^ 1992610U;
											num /= 134023084U;
											dictionary37[key37] = new 097E1C51.1F7F0274(this.1C8B7660);
											num >>= 8;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary38 = this.0FEE0E1F;
											num ^= 1240825627U;
											uint key38 = num ^ 1240825662U;
											num = 160044964U * num;
											num ^= 1346660899U;
											IntPtr 012C2F21 = ldftn(377273A1);
											num = (200806293U | num);
											097E1C51.1F7F0274 value25 = new 097E1C51.1F7F0274(this, 012C2F21);
											num /= 1782804905U;
											dictionary38[key38] = value25;
											num = 235364134U % num;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary39 = this.0FEE0E1F;
											uint key39 = num - 4294967258U;
											num *= 1340540893U;
											IntPtr 012C2F22 = ldftn(7CEA02B4);
											num <<= 4;
											097E1C51.1F7F0274 value26 = new 097E1C51.1F7F0274(this, 012C2F22);
											num ^= 1692600767U;
											dictionary39[key39] = value26;
											if (num < 959592242U)
											{
												goto IL_49;
											}
											Dictionary<uint, 097E1C51.1F7F0274> dictionary40 = this.0FEE0E1F;
											num |= 1758021571U;
											uint key40 = num ^ 1827360728U;
											num ^= 895096584U;
											num = 1168582782U % num;
											097E1C51.1F7F0274 value27 = new 097E1C51.1F7F0274(this.17C253DE);
											num = (1812215859U & num);
											dictionary40[key40] = value27;
											num /= 1329742006U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary41 = this.0FEE0E1F;
											num %= 1072980723U;
											dictionary41[num + 40U] = new 097E1C51.1F7F0274(this.15BC1CEB);
											num = (822954178U ^ num);
											Dictionary<uint, 097E1C51.1F7F0274> dictionary42 = this.0FEE0E1F;
											num %= 496508553U;
											uint key41 = num ^ 326445584U;
											num /= 374555334U;
											dictionary42[key41] = new 097E1C51.1F7F0274(this.2A844995);
											num <<= 0;
											if (1452825164 << (int)num == 0)
											{
												goto IL_00;
											}
											num *= 889224362U;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary43 = this.0FEE0E1F;
											uint key42 = num ^ 42U;
											num <<= 7;
											dictionary43[key42] = new 097E1C51.1F7F0274(this.625228E8);
											num ^= 1917001089U;
											num <<= 4;
											Dictionary<uint, 097E1C51.1F7F0274> dictionary44 = this.0FEE0E1F;
											num %= 1436026598U;
											uint key43 = num ^ 607246395U;
											097E1C51.1F7F0274 value28 = new 097E1C51.1F7F0274(this.465700D8);
											num &= 1673995100U;
											dictionary44[key43] = value28;
											num >>= 0;
										}
										while (num <= 116416684U);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary45 = this.0FEE0E1F;
										num -= 327496798U;
										uint key44 = num ^ 209439646U;
										num <<= 4;
										dictionary45[key44] = new 097E1C51.1F7F0274(this.40BB10D3);
										if (num / 1282609988U == 0U)
										{
											goto IL_2E;
										}
										this.0FEE0E1F[num + 943932685U] = new 097E1C51.1F7F0274(this.69D76EFE);
										num = 141049298U << (int)num;
										if (num == 1888027018U)
										{
											goto IL_11;
										}
										num <<= 10;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary46 = this.0FEE0E1F;
										uint key45 = num - 2700560338U;
										097E1C51.1F7F0274 value29 = new 097E1C51.1F7F0274(this.56CF7F02);
										num = 1645498652U * num;
										dictionary46[key45] = value29;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary47 = this.0FEE0E1F;
										uint key46 = num ^ 2027151407U;
										num ^= 1577078641U;
										num = 1782255184U * num;
										IntPtr 012C2F23 = ldftn(6FFA1529);
										num = 823135059U >> (int)num;
										dictionary47[key46] = new 097E1C51.1F7F0274(this, 012C2F23);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary48 = this.0FEE0E1F;
										uint key47 = num - 12512U;
										num = (1463245462U | num);
										097E1C51.1F7F0274 value30 = new 097E1C51.1F7F0274(this.1A7E7361);
										num = 2091909371U - num;
										dictionary48[key47] = value30;
										num ^= 2065042368U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary49 = this.0FEE0E1F;
										num = 1666740005U >> (int)num;
										uint key48 = num ^ 52085576U;
										num &= 748375827U;
										num &= 1249392279U;
										IntPtr 012C2F24 = ldftn(75C147E9);
										num = 2109081052U >> (int)num;
										dictionary49[key48] = new 097E1C51.1F7F0274(this, 012C2F24);
										num = (14179449U ^ num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary50 = this.0FEE0E1F;
										uint key49 = num - 14180976U;
										IntPtr 012C2F25 = ldftn(54420E2A);
										num >>= 5;
										dictionary50[key49] = new 097E1C51.1F7F0274(this, 012C2F25);
										num = 2026381469U / num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary51 = this.0FEE0E1F;
										uint key50 = num ^ 4591U;
										num -= 1018894667U;
										IntPtr 012C2F26 = ldftn(4274788B);
										num ^= 414268306U;
										dictionary51[key50] = new 097E1C51.1F7F0274(this, 012C2F26);
										if (num - 1236235207U == 0U)
										{
											goto IL_2E;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary52 = this.0FEE0E1F;
										uint key51 = num ^ 3690316599U;
										IntPtr 012C2F27 = ldftn(7F19411B);
										num = 1356230748U - num;
										dictionary52[key51] = new 097E1C51.1F7F0274(this, 012C2F27);
										num %= 443351606U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary53 = this.0FEE0E1F;
										num = 339484871U >> (int)num;
										uint key52 = num + 4125224914U;
										num >>= 25;
										num = 1278887894U + num;
										097E1C51.1F7F0274 value31 = new 097E1C51.1F7F0274(this.0E797388);
										num *= 1174079237U;
										dictionary53[key52] = value31;
										num /= 781413216U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary54 = this.0FEE0E1F;
										num = (122713870U ^ num);
										uint key53 = num ^ 122713915U;
										IntPtr 012C2F28 = ldftn(21CC7EF0);
										num = 1369785116U << (int)num;
										dictionary54[key53] = new 097E1C51.1F7F0274(this, 012C2F28);
										if (num + 770968824U == 0U)
										{
											goto IL_11;
										}
										num = 529231417U - num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary55 = this.0FEE0E1F;
										num = 693189359U - num;
										uint key54 = num ^ 2989051009U;
										num |= 1214722751U;
										dictionary55[key54] = new 097E1C51.1F7F0274(this.4C0D7078);
										if (num + 1044252949U == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary56 = this.0FEE0E1F;
										uint key55 = num - 4201610887U;
										num /= 698307820U;
										num = (1288053714U & num);
										IntPtr 012C2F29 = ldftn(39971548);
										num = (877607079U | num);
										dictionary56[key55] = new 097E1C51.1F7F0274(this, 012C2F29);
										if (num / 1834877938U != 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary57 = this.0FEE0E1F;
										num <<= 10;
										uint key56 = num - 1021483975U;
										num |= 2000290360U;
										IntPtr 012C2F30 = ldftn(62A038EF);
										num *= 1576085710U;
										dictionary57[key56] = new 097E1C51.1F7F0274(this, 012C2F30);
										num = (2091405795U & num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary58 = this.0FEE0E1F;
										uint key57 = num ^ 1812468026U;
										num = 1594756714U + num;
										num = 433154239U % num;
										IntPtr 012C2F31 = ldftn(4A0B1810);
										num = 520294937U % num;
										097E1C51.1F7F0274 value32 = new 097E1C51.1F7F0274(this, 012C2F31);
										num = 1534422152U >> (int)num;
										dictionary58[key57] = value32;
										num *= 623603329U;
										num = 266085721U / num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary59 = this.0FEE0E1F;
										uint key58 = num - 4294967237U;
										num += 1989302165U;
										IntPtr 012C2F32 = ldftn(768C1A06);
										num &= 319824588U;
										097E1C51.1F7F0274 value33 = new 097E1C51.1F7F0274(this, 012C2F32);
										num /= 347799844U;
										dictionary59[key58] = value33;
										num = 1091204214U + num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary60 = this.0FEE0E1F;
										num = (1244933192U & num);
										uint key59 = num ^ 1073751164U;
										num += 1486170748U;
										IntPtr 012C2F33 = ldftn(58373290);
										num -= 481716675U;
										dictionary60[key59] = new 097E1C51.1F7F0274(this, 012C2F33);
										num = 1242192810U - num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary61 = this.0FEE0E1F;
										uint key60 = num ^ 3458954892U;
										num <<= 1;
										IntPtr 012C2F34 = ldftn(6B4F248F);
										num = 844913599U << (int)num;
										097E1C51.1F7F0274 value34 = new 097E1C51.1F7F0274(this, 012C2F34);
										num &= 286933133U;
										dictionary61[key60] = value34;
										if (27098831U * num == 0U)
										{
											goto IL_11;
										}
										num ^= 791815405U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary62 = this.0FEE0E1F;
										uint key61 = num + 3520961501U;
										num -= 1635589235U;
										num = (956848262U & num);
										097E1C51.1F7F0274 value35 = new 097E1C51.1F7F0274(this.5EE86E2F);
										num = (1160457692U ^ num);
										dictionary62[key61] = value35;
										if ((num ^ 1965446620U) == 0U)
										{
											goto IL_2E;
										}
										num = 1958215776U - num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary63 = this.0FEE0E1F;
										uint key62 = num ^ 663524153U;
										IntPtr 012C2F35 = ldftn(69F22BB5);
										num = (1848255096U | num);
										dictionary63[key62] = new 097E1C51.1F7F0274(this, 012C2F35);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary64 = this.0FEE0E1F;
										num ^= 913397104U;
										uint key63 = num ^ 1507845710U;
										num /= 1127426881U;
										num = (1654927560U ^ num);
										IntPtr 012C2F36 = ldftn(58773899);
										num = 1628602270U * num;
										097E1C51.1F7F0274 value36 = new 097E1C51.1F7F0274(this, 012C2F36);
										num *= 1408132588U;
										dictionary64[key63] = value36;
										if ((735927787U ^ num) == 0U)
										{
											goto IL_2E;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary65 = this.0FEE0E1F;
										num &= 516898130U;
										uint key64 = num ^ 412029953U;
										num ^= 293167060U;
										IntPtr 012C2F37 = ldftn(6EF13986);
										num <<= 7;
										097E1C51.1F7F0274 value37 = new 097E1C51.1F7F0274(this, 012C2F37);
										num = 284361168U - num;
										dictionary65[key64] = value37;
										if (num > 1826380152U)
										{
											goto IL_11;
										}
										num %= 2034578223U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary66 = this.0FEE0E1F;
										num -= 637081377U;
										uint key65 = num ^ 4023656685U;
										num <<= 15;
										num = 2100828922U * num;
										dictionary66[key65] = new 097E1C51.1F7F0274(this.690E2B29);
										num |= 11744818U;
										if (1762538322U > num)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary67 = this.0FEE0E1F;
										uint key66 = num ^ 3639817841U;
										097E1C51.1F7F0274 value38 = new 097E1C51.1F7F0274(this.55D55526);
										num &= 1284728735U;
										dictionary67[key66] = value38;
										num = (1950956394U | num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary68 = this.0FEE0E1F;
										num = (1593399437U | num);
										uint key67 = num ^ 2130411451U;
										IntPtr 012C2F38 = ldftn(73C37226);
										num = 2046581227U % num;
										097E1C51.1F7F0274 value39 = new 097E1C51.1F7F0274(this, 012C2F38);
										num = (194518319U ^ num);
										dictionary68[key67] = value39;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary69 = this.0FEE0E1F;
										uint key68 = num - 1919173759U;
										num = 1645443318U + num;
										IntPtr 012C2F39 = ldftn(1AC96223);
										num %= 1046875978U;
										dictionary69[key68] = new 097E1C51.1F7F0274(this, 012C2F39);
										num = 377367451U << (int)num;
										if (1792554704U >= num)
										{
											goto IL_2E;
										}
										num = (389968844U | num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary70 = this.0FEE0E1F;
										uint key69 = num + 1220643962U;
										num /= 1242589934U;
										num <<= 20;
										IntPtr 012C2F40 = ldftn(270E06CA);
										num ^= 2007714073U;
										097E1C51.1F7F0274 value40 = new 097E1C51.1F7F0274(this, 012C2F40);
										num += 1231228541U;
										dictionary70[key69] = value40;
										num = (1246372104U & num);
										if (num - 956592845U == 0U)
										{
											goto IL_11;
										}
										num /= 1944200662U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary71 = this.0FEE0E1F;
										uint key70 = num - 4294967225U;
										num *= 576740242U;
										num = (1544057131U | num);
										dictionary71[key70] = new 097E1C51.1F7F0274(this.209D7FC0);
										num += 630807392U;
										if ((num ^ 1476593369U) == 0U)
										{
											goto IL_11;
										}
										num = 1478451170U + num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary72 = this.0FEE0E1F;
										uint key71 = num - 3653315621U;
										num ^= 161546657U;
										num |= 1980444524U;
										dictionary72[key71] = new 097E1C51.1F7F0274(this.37ED550A);
										num %= 1593967915U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary73 = this.0FEE0E1F;
										uint key72 = num ^ 946283999U;
										num += 1889619418U;
										IntPtr 012C2F41 = ldftn(004273E4);
										num = 766784436U * num;
										097E1C51.1F7F0274 value41 = new 097E1C51.1F7F0274(this, 012C2F41);
										num >>= 5;
										dictionary73[key72] = value41;
										num += 1583624711U;
										if (291965136U >= num)
										{
											goto IL_49;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary74 = this.0FEE0E1F;
										num &= 727076329U;
										dictionary74[num ^ 553649539U] = new 097E1C51.1F7F0274(this.1D973A08);
										if (num == 1927839133U)
										{
											goto IL_00;
										}
										num ^= 1632707950U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary75 = this.0FEE0E1F;
										num <<= 17;
										uint key73 = num ^ 1095630923U;
										num = 1748920766U * num;
										num %= 2003634298U;
										097E1C51.1F7F0274 value42 = new 097E1C51.1F7F0274(this.60640AD4);
										num = 673152266U % num;
										dictionary75[key73] = value42;
										num &= 1065028744U;
										if (2029546821U + num == 0U)
										{
											goto IL_00;
										}
										num = 1420391556U / num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary76 = this.0FEE0E1F;
										num = 1775113807U * num;
										uint key74 = num ^ 3550227666U;
										num = 21516428U / num;
										IntPtr 012C2F42 = ldftn(68B23F03);
										num *= 18305358U;
										dictionary76[key74] = new 097E1C51.1F7F0274(this, 012C2F42);
										num = 604127078U + num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary77 = this.0FEE0E1F;
										num += 707551105U;
										uint key75 = num + 2983289190U;
										IntPtr 012C2F43 = ldftn(58373290);
										num *= 1004299312U;
										097E1C51.1F7F0274 value43 = new 097E1C51.1F7F0274(this, 012C2F43);
										num = 802060138U + num;
										dictionary77[key75] = value43;
										num *= 1805915977U;
										num = (790588684U & num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary78 = this.0FEE0E1F;
										uint key76 = num + 3590305606U;
										num = (1982538291U ^ num);
										IntPtr 012C2F44 = ldftn(56CF7F02);
										num ^= 361058421U;
										dictionary78[key76] = new 097E1C51.1F7F0274(this, 012C2F44);
										num &= 2017350695U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary79 = this.0FEE0E1F;
										num = 1092507448U * num;
										uint key77 = num + 667317503U;
										num %= 2140557121U;
										dictionary79[key77] = new 097E1C51.1F7F0274(this.768C1A06);
										num = 2118477963U << (int)num;
										if (1460477917U >= num)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary80 = this.0FEE0E1F;
										uint key78 = num ^ 3024453712U;
										num = 2062682292U - num;
										097E1C51.1F7F0274 value44 = new 097E1C51.1F7F0274(this.768C1A06);
										num = (4155195U | num);
										dictionary80[key78] = value44;
										if (num * 1953835548U == 0U)
										{
											goto IL_00;
										}
										num = (1190269311U | num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary81 = this.0FEE0E1F;
										num -= 1703632340U;
										uint key79 = num + 2659933734U;
										num ^= 1560758150U;
										num <<= 31;
										IntPtr 012C2F45 = ldftn(5CB74094);
										num <<= 27;
										dictionary81[key79] = new 097E1C51.1F7F0274(this, 012C2F45);
										if (num == 1200903366U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary82 = this.0FEE0E1F;
										num = (1567778856U & num);
										uint key80 = num ^ 82U;
										IntPtr 012C2F46 = ldftn(7B835829);
										num = 581128186U << (int)num;
										097E1C51.1F7F0274 value45 = new 097E1C51.1F7F0274(this, 012C2F46);
										num = (1349331909U & num);
										dictionary82[key80] = value45;
										if (num >= 2063535731U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary83 = this.0FEE0E1F;
										num <<= 7;
										uint key81 = num ^ 277209171U;
										num *= 2091132914U;
										IntPtr 012C2F47 = ldftn(768C1A06);
										num = 1659372327U - num;
										dictionary83[key81] = new 097E1C51.1F7F0274(this, 012C2F47);
										num += 1110203324U;
										if ((758861550U ^ num) == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary84 = this.0FEE0E1F;
										num = (1970303707U & num);
										uint key82 = num + 3735022993U;
										IntPtr 012C2F48 = ldftn(778E5B5E);
										num = (341517141U & num);
										097E1C51.1F7F0274 value46 = new 097E1C51.1F7F0274(this, 012C2F48);
										num ^= 435297683U;
										dictionary84[key82] = value46;
										num += 935356035U;
										if ((1606317436U ^ num) == 0U)
										{
											goto IL_11;
										}
										num = 862083601U / num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary85 = this.0FEE0E1F;
										uint key83 = num + 85U;
										num ^= 917973451U;
										097E1C51.1F7F0274 value47 = new 097E1C51.1F7F0274(this.465700D8);
										num += 24276118U;
										dictionary85[key83] = value47;
										num += 1548095088U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary86 = this.0FEE0E1F;
										num <<= 0;
										uint key84 = num + 1804622725U;
										num -= 1519276694U;
										dictionary86[key84] = new 097E1C51.1F7F0274(this.4A0B1810);
										if (505170037U > num)
										{
											goto IL_2E;
										}
										num = 1338193126U / num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary87 = this.0FEE0E1F;
										uint key85 = num - 4294967210U;
										num = 524501290U + num;
										dictionary87[key85] = new 097E1C51.1F7F0274(this.4C135968);
										if (num + 755505567U == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary88 = this.0FEE0E1F;
										num = (1661553222U ^ num);
										uint key86 = num + 2209740011U;
										097E1C51.1F7F0274 value48 = new 097E1C51.1F7F0274(this.69D76EFE);
										num <<= 1;
										dictionary88[key86] = value48;
										if (174343282U >= num)
										{
											goto IL_00;
										}
										num = (682633571U ^ num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary89 = this.0FEE0E1F;
										uint key87 = num + 802930848U;
										num = (2130135921U | num);
										dictionary89[key87] = new 097E1C51.1F7F0274(this.004273E4);
										num <<= 15;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary90 = this.0FEE0E1F;
										uint key88 = num ^ 3220996186U;
										num = 1385760086U >> (int)num;
										dictionary90[key88] = new 097E1C51.1F7F0274(this.598E21C0);
										if (num == 1022570045U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary91 = this.0FEE0E1F;
										uint key89 = num + 2909207301U;
										num = 527071194U >> (int)num;
										097E1C51.1F7F0274 value49 = new 097E1C51.1F7F0274(this.21CC7EF0);
										num = 311834047U + num;
										dictionary91[key89] = value49;
										num = (1017151233U ^ num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary92 = this.0FEE0E1F;
										num = 1318279064U * num;
										uint key90 = num ^ 2267428708U;
										IntPtr 012C2F49 = ldftn(270E06CA);
										num = 510421428U >> (int)num;
										dictionary92[key90] = new 097E1C51.1F7F0274(this, 012C2F49);
										if (num * 1414158706U == 0U)
										{
											goto IL_11;
										}
										num += 1802530040U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary93 = this.0FEE0E1F;
										uint key91 = num - 1802529977U;
										num = 758276976U + num;
										097E1C51.1F7F0274 value50 = new 097E1C51.1F7F0274(this.5A1954EC);
										num %= 2101376574U;
										dictionary93[key91] = value50;
										num = 1668357322U + num;
										if (num <= 813388287U)
										{
											goto IL_11;
										}
										num = 2086282875U % num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary94 = this.0FEE0E1F;
										uint key92 = num ^ 2086282789U;
										num %= 572279295U;
										num >>= 22;
										dictionary94[key92] = new 097E1C51.1F7F0274(this.39971548);
										num = (1613191487U | num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary95 = this.0FEE0E1F;
										uint key93 = num + 2681775840U;
										num %= 1229136342U;
										IntPtr 012C2F50 = ldftn(40BB10D3);
										num = 1081685224U << (int)num;
										097E1C51.1F7F0274 value51 = new 097E1C51.1F7F0274(this, 012C2F50);
										num = 1024811064U >> (int)num;
										dictionary95[key93] = value51;
										if (num / 1233615142U != 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary96 = this.0FEE0E1F;
										uint key94 = num ^ 1024811096U;
										num = 813594269U << (int)num;
										dictionary96[key94] = new 097E1C51.1F7F0274(this.625228E8);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary97 = this.0FEE0E1F;
										num = (1443916773U & num);
										uint key95 = num + 3959423073U;
										num = 879184345U / num;
										IntPtr 012C2F51 = ldftn(4A0B1810);
										num = 2108193347U * num;
										dictionary97[key95] = new 097E1C51.1F7F0274(this, 012C2F51);
										num |= 1558710781U;
										if (num <= 637078681U)
										{
											goto IL_11;
										}
										num /= 910583032U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary98 = this.0FEE0E1F;
										num = 807566865U % num;
										dictionary98[num ^ 99U] = new 097E1C51.1F7F0274(this.27F7218F);
										num = 1640048413U >> (int)num;
										if (1114907984U == num)
										{
											goto IL_49;
										}
										num ^= 1139887362U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary99 = this.0FEE0E1F;
										uint key96 = num + 2364416471U;
										num &= 2052472353U;
										dictionary99[key96] = new 097E1C51.1F7F0274(this.68B23F03);
										if (409690243U * num == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary100 = this.0FEE0E1F;
										num = 518665794U / num;
										uint key97 = num ^ 100U;
										num ^= 1863857209U;
										IntPtr 012C2F52 = ldftn(5AFD3DDB);
										num = (1287723309U & num);
										dictionary100[key97] = new 097E1C51.1F7F0274(this, 012C2F52);
										if (num == 2119704020U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary101 = this.0FEE0E1F;
										num -= 1062276193U;
										uint key98 = num ^ 212796333U;
										num *= 1313093236U;
										num <<= 18;
										IntPtr 012C2F53 = ldftn(4274788B);
										num = 321127668U << (int)num;
										dictionary101[key98] = new 097E1C51.1F7F0274(this, 012C2F53);
										num <<= 3;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary102 = this.0FEE0E1F;
										uint key99 = num - 2569021242U;
										num <<= 30;
										097E1C51.1F7F0274 value52 = new 097E1C51.1F7F0274(this.690E2B29);
										num = (1904359219U ^ num);
										dictionary102[key99] = value52;
										num = 1292331981U * num;
										if (525155897 << (int)num == 0)
										{
											goto IL_2E;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary103 = this.0FEE0E1F;
										num /= 965162397U;
										uint key100 = num ^ 103U;
										IntPtr 012C2F54 = ldftn(37ED550A);
										num = (1935413731U ^ num);
										dictionary103[key100] = new 097E1C51.1F7F0274(this, 012C2F54);
										num = 1156021857U - num;
										num %= 409238978U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary104 = this.0FEE0E1F;
										uint key101 = num ^ 241663494U;
										num = (34952606U & num);
										dictionary104[key101] = new 097E1C51.1F7F0274(this.690E2B29);
										if (1188723982U - num == 0U)
										{
											goto IL_11;
										}
										num ^= 1892232635U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary105 = this.0FEE0E1F;
										uint key102 = num ^ 1926003164U;
										num |= 830486220U;
										IntPtr 012C2F55 = ldftn(39971548);
										num /= 1120566491U;
										dictionary105[key102] = new 097E1C51.1F7F0274(this, 012C2F55);
										if (849412113U % num != 0U)
										{
											goto IL_2E;
										}
										num |= 1875715811U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary106 = this.0FEE0E1F;
										num = 1116740279U % num;
										uint key103 = num + 3178227123U;
										num += 306212791U;
										num &= 1460144616U;
										097E1C51.1F7F0274 value53 = new 097E1C51.1F7F0274(this.690E2B29);
										num &= 1833700218U;
										dictionary106[key103] = value53;
										num = 540226856U * num;
										num <<= 9;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary107 = this.0FEE0E1F;
										uint key104 = num ^ 3987767403U;
										num = 862606508U - num;
										IntPtr 012C2F56 = ldftn(5EE86E2F);
										num *= 1425945398U;
										dictionary107[key104] = new 097E1C51.1F7F0274(this, 012C2F56);
										num *= 1522672943U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary108 = this.0FEE0E1F;
										num &= 669847149U;
										uint key105 = num + 4200266820U;
										num = 699031244U - num;
										IntPtr 012C2F57 = ldftn(5AFD3DDB);
										num /= 945501773U;
										dictionary108[key105] = new 097E1C51.1F7F0274(this, 012C2F57);
										if ((num ^ 1282483512U) == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary109 = this.0FEE0E1F;
										uint key106 = num + 109U;
										num = 1844135058U >> (int)num;
										097E1C51.1F7F0274 value54 = new 097E1C51.1F7F0274(this.39971548);
										num = 2102855269U % num;
										dictionary109[key106] = value54;
										num &= 893271610U;
										if (842688484U % num == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary110 = this.0FEE0E1F;
										uint key107 = num - 86638500U;
										num %= 1505316437U;
										097E1C51.1F7F0274 value55 = new 097E1C51.1F7F0274(this.270E06CA);
										num += 1498437091U;
										dictionary110[key107] = value55;
										num += 1515213133U;
										if (156828364U > num)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary111 = this.0FEE0E1F;
										num %= 506878795U;
										uint key108 = num ^ 59016175U;
										num = 1462117231U >> (int)num;
										num |= 1601856586U;
										097E1C51.1F7F0274 value56 = new 097E1C51.1F7F0274(this.69F22BB5);
										num = (1346333412U & num);
										dictionary111[key108] = value56;
										if (1301761536 << (int)num == 0)
										{
											goto IL_11;
										}
										this.0FEE0E1F[num ^ 1346265620U] = new 097E1C51.1F7F0274(this.7F19411B);
										num /= 766773468U;
										num |= 567755929U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary112 = this.0FEE0E1F;
										num = 579996735U % num;
										uint key109 = num + 4282726603U;
										num *= 2019903107U;
										097E1C51.1F7F0274 value57 = new 097E1C51.1F7F0274(this.58773899);
										num /= 285814048U;
										dictionary112[key109] = value57;
										if (num > 86401243U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary113 = this.0FEE0E1F;
										uint key110 = num ^ 114U;
										num = (1275946869U | num);
										num &= 282991795U;
										dictionary113[key110] = new 097E1C51.1F7F0274(this.60640AD4);
										num = 1435766001U / num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary114 = this.0FEE0E1F;
										num = 82661474U * num;
										dictionary114[num + 3926955669U] = new 097E1C51.1F7F0274(this.465700D8);
										num += 1430849134U;
										num = (1454851202U & num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary115 = this.0FEE0E1F;
										uint key111 = num ^ 1110458484U;
										num &= 114373516U;
										dictionary115[key111] = new 097E1C51.1F7F0274(this.690E2B29);
										num /= 721823658U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary116 = this.0FEE0E1F;
										num &= 1736076493U;
										uint key112 = num - 4294967179U;
										097E1C51.1F7F0274 value58 = new 097E1C51.1F7F0274(this.598E21C0);
										num = (1146173810U & num);
										dictionary116[key112] = value58;
										num = 562955447U * num;
										if ((num ^ 1840401820U) == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary117 = this.0FEE0E1F;
										uint key113 = num - 4294967178U;
										num |= 823742626U;
										IntPtr 012C2F58 = ldftn(27F7218F);
										num = (1363154922U & num);
										dictionary117[key113] = new 097E1C51.1F7F0274(this, 012C2F58);
										num = 2116748495U * num;
										if (num * 1327917821U == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary118 = this.0FEE0E1F;
										uint key114 = num - 1183185543U;
										num >>= 8;
										IntPtr 012C2F59 = ldftn(5393093A);
										num |= 800289998U;
										097E1C51.1F7F0274 value59 = new 097E1C51.1F7F0274(this, 012C2F59);
										num = 1782335333U + num;
										dictionary118[key114] = value59;
										num ^= 458569806U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary119 = this.0FEE0E1F;
										uint key115 = num ^ 2170619221U;
										097E1C51.1F7F0274 value60 = new 097E1C51.1F7F0274(this.728D144D);
										num ^= 784486117U;
										dictionary119[key115] = value60;
										if ((1162698906U ^ num) == 0U)
										{
											goto IL_00;
										}
										num += 1249202610U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary120 = this.0FEE0E1F;
										num = (474891506U & num);
										uint key116 = num ^ 403178507U;
										IntPtr 012C2F60 = ldftn(21CC7EF0);
										num = (1962298277U | num);
										dictionary120[key116] = new 097E1C51.1F7F0274(this, 012C2F60);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary121 = this.0FEE0E1F;
										uint key117 = num - 2097041277U;
										num *= 663250775U;
										IntPtr 012C2F61 = ldftn(265604A2);
										num ^= 408827931U;
										097E1C51.1F7F0274 value61 = new 097E1C51.1F7F0274(this, 012C2F61);
										num *= 144588629U;
										dictionary121[key117] = value61;
										this.0FEE0E1F[num + 739771337U] = new 097E1C51.1F7F0274(this.5CB74094);
										num &= 796874737U;
										if (num >= 1654403160U)
										{
											goto IL_00;
										}
										num /= 1904942092U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary122 = this.0FEE0E1F;
										uint key118 = num + 124U;
										num *= 158926772U;
										IntPtr 012C2F62 = ldftn(27F7218F);
										num -= 1054540342U;
										dictionary122[key118] = new 097E1C51.1F7F0274(this, 012C2F62);
										num <<= 8;
										if ((638060174U ^ num) == 0U)
										{
											goto IL_2E;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary123 = this.0FEE0E1F;
										uint key119 = num ^ 620612221U;
										num = 1642476406U % num;
										IntPtr 012C2F63 = ldftn(598E21C0);
										num >>= 26;
										097E1C51.1F7F0274 value62 = new 097E1C51.1F7F0274(this, 012C2F63);
										num <<= 11;
										dictionary123[key119] = value62;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary124 = this.0FEE0E1F;
										num += 958748448U;
										uint key120 = num ^ 958758750U;
										num <<= 15;
										dictionary124[key120] = new 097E1C51.1F7F0274(this.51997215);
										num += 47002223U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary125 = this.0FEE0E1F;
										uint key121 = num + 1034079760U;
										IntPtr 012C2F64 = ldftn(6EF13986);
										num = (677929734U ^ num);
										dictionary125[key121] = new 097E1C51.1F7F0274(this, 012C2F64);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary126 = this.0FEE0E1F;
										num = 826632913U + num;
										uint key122 = num ^ 461028538U;
										num = (4540321U ^ num);
										num ^= 1841132910U;
										IntPtr 012C2F65 = ldftn(18784820);
										num = 617313627U / num;
										dictionary126[key122] = new 097E1C51.1F7F0274(this, 012C2F65);
										if (578366764U <= num)
										{
											goto IL_2E;
										}
										num |= 830040919U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary127 = this.0FEE0E1F;
										num -= 1999503948U;
										uint key123 = num - 3125504138U;
										num = 1120829U / num;
										IntPtr 012C2F66 = ldftn(58773899);
										num = (1595484885U ^ num);
										dictionary127[key123] = new 097E1C51.1F7F0274(this, 012C2F66);
										if (num / 476453719U == 0U)
										{
											goto IL_11;
										}
										num = 117571624U << (int)num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary128 = this.0FEE0E1F;
										uint key124 = num - 83885950U;
										num = (694498129U | num);
										dictionary128[key124] = new 097E1C51.1F7F0274(this.69D76EFE);
										num = 317095891U * num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary129 = this.0FEE0E1F;
										num &= 897796309U;
										uint key125 = num ^ 562055234U;
										num = (846010306U & num);
										dictionary129[key125] = new 097E1C51.1F7F0274(this.58773899);
										num = (789791277U | num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary130 = this.0FEE0E1F;
										uint key126 = num - 789791337U;
										IntPtr 012C2F67 = ldftn(1C8B7660);
										num += 260260492U;
										dictionary130[key126] = new 097E1C51.1F7F0274(this, 012C2F67);
										num = 1753437095U * num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary131 = this.0FEE0E1F;
										num /= 1374705990U;
										uint key127 = num + 133U;
										num &= 729293071U;
										097E1C51.1F7F0274 value63 = new 097E1C51.1F7F0274(this.728D144D);
										num = 2086079155U * num;
										dictionary131[key127] = value63;
										num |= 462431858U;
										num -= 1491163184U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary132 = this.0FEE0E1F;
										uint key128 = num ^ 3266236100U;
										097E1C51.1F7F0274 value64 = new 097E1C51.1F7F0274(this.27F7218F);
										num = 1876239417U + num;
										dictionary132[key128] = value64;
										num <<= 7;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary133 = this.0FEE0E1F;
										uint key129 = num + 3188114183U;
										IntPtr 012C2F68 = ldftn(598E21C0);
										num = (1675522787U & num);
										097E1C51.1F7F0274 value65 = new 097E1C51.1F7F0274(this, 012C2F68);
										num = 752822636U << (int)num;
										dictionary133[key129] = value65;
										num <<= 28;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary134 = this.0FEE0E1F;
										num = 1688339344U - num;
										uint key130 = num ^ 2762081048U;
										097E1C51.1F7F0274 value66 = new 097E1C51.1F7F0274(this.5AFD3DDB);
										num = 1061170873U * num;
										dictionary134[key130] = value66;
										if (27885287U > num)
										{
											goto IL_2E;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary135 = this.0FEE0E1F;
										uint key131 = num ^ 1773695897U;
										097E1C51.1F7F0274 value67 = new 097E1C51.1F7F0274(this.625228E8);
										num = 1914711955U << (int)num;
										dictionary135[key131] = value67;
										num = 1574916060U >> (int)num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary136 = this.0FEE0E1F;
										uint key132 = num ^ 1574915926U;
										IntPtr 012C2F69 = ldftn(73C37226);
										num <<= 29;
										097E1C51.1F7F0274 value68 = new 097E1C51.1F7F0274(this, 012C2F69);
										num = 1520266204U / num;
										dictionary136[key132] = value68;
										if (num == 1799896995U)
										{
											goto IL_00;
										}
										num &= 1120216570U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary137 = this.0FEE0E1F;
										num = (966001838U & num);
										uint key133 = num ^ 139U;
										num = 2030988852U + num;
										num = 1776836849U + num;
										097E1C51.1F7F0274 value69 = new 097E1C51.1F7F0274(this.0FB617A4);
										num = 1626669583U >> (int)num;
										dictionary137[key133] = value69;
										if ((num ^ 1708332979U) == 0U)
										{
											goto IL_00;
										}
										num %= 1338931275U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary138 = this.0FEE0E1F;
										uint key134 = num + 4244134012U;
										num |= 1353982613U;
										dictionary138[key134] = new 097E1C51.1F7F0274(this.4A0B1810);
										if (2105081994U % num == 0U)
										{
											goto IL_00;
										}
										num = 1108356500U % num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary139 = this.0FEE0E1F;
										num -= 1999325066U;
										uint key135 = num ^ 3403998855U;
										IntPtr 012C2F70 = ldftn(73C37226);
										num = 1684286176U + num;
										dictionary139[key135] = new 097E1C51.1F7F0274(this, 012C2F70);
										num /= 1770586788U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary140 = this.0FEE0E1F;
										uint key136 = num ^ 142U;
										num -= 861285914U;
										IntPtr 012C2F71 = ldftn(768C1A06);
										num |= 1749751120U;
										097E1C51.1F7F0274 value70 = new 097E1C51.1F7F0274(this, 012C2F71);
										num *= 1604454486U;
										dictionary140[key136] = value70;
										num = (621285012U & num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary141 = this.0FEE0E1F;
										num -= 1639342575U;
										uint key137 = num ^ 3276381722U;
										num &= 1114387694U;
										dictionary141[key137] = new 097E1C51.1F7F0274(this.5AFD3DDB);
										num = (1491210597U ^ num);
										num = 1055553072U % num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary142 = this.0FEE0E1F;
										uint key138 = num ^ 160852734U;
										num *= 954208506U;
										IntPtr 012C2F72 = ldftn(625228E8);
										num >>= 13;
										dictionary142[key138] = new 097E1C51.1F7F0274(this, 012C2F72);
										num *= 1284598074U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary143 = this.0FEE0E1F;
										uint key139 = num + 2812205845U;
										num = (43870543U | num);
										num = 2135318260U << (int)num;
										IntPtr 012C2F73 = ldftn(60640AD4);
										num = 556556309U - num;
										097E1C51.1F7F0274 value71 = new 097E1C51.1F7F0274(this, 012C2F73);
										num |= 1068202785U;
										dictionary143[key139] = value71;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary144 = this.0FEE0E1F;
										num = 1670456957U << (int)num;
										uint key140 = num - 3483369326U;
										num %= 1023495030U;
										num = (593322690U | num);
										IntPtr 012C2F74 = ldftn(2DD92D6C);
										num = (1928493046U | num);
										dictionary144[key140] = new 097E1C51.1F7F0274(this, 012C2F74);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary145 = this.0FEE0E1F;
										num = (2074108955U & num);
										uint key141 = num ^ 2074109065U;
										num *= 1375013517U;
										num = 1525765712U >> (int)num;
										dictionary145[key141] = new 097E1C51.1F7F0274(this.111A690C);
										num = 678049876U / num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary146 = this.0FEE0E1F;
										uint key142 = num - 116355U;
										num *= 1540510143U;
										dictionary146[key142] = new 097E1C51.1F7F0274(this.209D7FC0);
										num = (1100550360U ^ num);
										if (num % 1381985506U == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary147 = this.0FEE0E1F;
										uint key143 = num - 1320599900U;
										097E1C51.1F7F0274 value72 = new 097E1C51.1F7F0274(this.21CC7EF0);
										num = 927231438U << (int)num;
										dictionary147[key143] = value72;
										num &= 430069640U;
										if (676802336U == num)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary148 = this.0FEE0E1F;
										uint key144 = num + 3867148438U;
										num = 1131107769U * num;
										097E1C51.1F7F0274 value73 = new 097E1C51.1F7F0274(this.4C0D7078);
										num = 2143440951U + num;
										dictionary148[key144] = value73;
										num = (127089737U & num);
										num = 1560037909U - num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary149 = this.0FEE0E1F;
										uint key145 = num ^ 1476016771U;
										IntPtr 012C2F75 = ldftn(7CEA02B4);
										num -= 1285623262U;
										dictionary149[key145] = new 097E1C51.1F7F0274(this, 012C2F75);
										num -= 1248214240U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary150 = this.0FEE0E1F;
										num = (1568304068U ^ num);
										uint key146 = num ^ 2642969610U;
										num = 1874993634U % num;
										097E1C51.1F7F0274 value74 = new 097E1C51.1F7F0274(this.6EF13986);
										num <<= 28;
										dictionary150[key146] = value74;
										if (num * 991498012U == 0U)
										{
											goto IL_2E;
										}
										num <<= 26;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary151 = this.0FEE0E1F;
										num <<= 17;
										dictionary151[num ^ 153U] = new 097E1C51.1F7F0274(this.598E21C0);
										if (num >= 1765368089U)
										{
											goto IL_2E;
										}
										num %= 308507232U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary152 = this.0FEE0E1F;
										uint key147 = num + 154U;
										num |= 2096043927U;
										IntPtr 012C2F76 = ldftn(6EF13986);
										num += 1742279067U;
										dictionary152[key147] = new 097E1C51.1F7F0274(this, 012C2F76);
										if (num <= 192366197U)
										{
											goto IL_00;
										}
										num ^= 1403260369U;
										this.0FEE0E1F[num ^ 3077317752U] = new 097E1C51.1F7F0274(this.58773899);
										num = (1578444256U ^ num);
										if (num == 2104844913U)
										{
											goto IL_2E;
										}
										num = 1578783655U % num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary153 = this.0FEE0E1F;
										uint key148 = num - 1578783499U;
										num = 456206588U + num;
										num = (329866055U & num);
										IntPtr 012C2F77 = ldftn(265604A2);
										num -= 82011489U;
										dictionary153[key148] = new 097E1C51.1F7F0274(this, 012C2F77);
										if (1162875495 << (int)num == 0)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary154 = this.0FEE0E1F;
										num <<= 11;
										uint key149 = num + 3496669341U;
										num &= 635324500U;
										num = (1247480458U ^ num);
										097E1C51.1F7F0274 value75 = new 097E1C51.1F7F0274(this.5AFD3DDB);
										num = 231100884U << (int)num;
										dictionary154[key149] = value75;
										num = 355425876U % num;
										if (num >= 1689593383U)
										{
											goto IL_2E;
										}
										num = (1100559545U ^ num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary155 = this.0FEE0E1F;
										num = 2100506579U + num;
										uint key150 = num ^ 3521747550U;
										num = 2098426239U << (int)num;
										num = (1018063324U | num);
										IntPtr 012C2F78 = ldftn(5EE86E2F);
										num |= 457272760U;
										dictionary155[key150] = new 097E1C51.1F7F0274(this, 012C2F78);
										num /= 441736557U;
										if (num * 1690200454U == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary156 = this.0FEE0E1F;
										uint key151 = num ^ 155U;
										num = (366365584U ^ num);
										097E1C51.1F7F0274 value76 = new 097E1C51.1F7F0274(this.5AFD3DDB);
										num <<= 0;
										dictionary156[key151] = value76;
										if (1430600100U < num)
										{
											goto IL_00;
										}
										num = 244777663U - num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary157 = this.0FEE0E1F;
										uint key152 = num - 4173379211U;
										num = 1014186224U % num;
										dictionary157[key152] = new 097E1C51.1F7F0274(this.1A7E7361);
										num *= 1837574043U;
										num ^= 1126629657U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary158 = this.0FEE0E1F;
										num *= 1299451031U;
										uint key153 = num ^ 17348526U;
										num = 2004184957U << (int)num;
										num = 328608008U * num;
										097E1C51.1F7F0274 value77 = new 097E1C51.1F7F0274(this.598E21C0);
										num &= 1410476925U;
										dictionary158[key153] = value77;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary159 = this.0FEE0E1F;
										uint key154 = num ^ 1074790562U;
										num = 1661687069U + num;
										num *= 2052420397U;
										097E1C51.1F7F0274 value78 = new 097E1C51.1F7F0274(this.598E21C0);
										num >>= 15;
										dictionary159[key154] = value78;
										num -= 2110345793U;
										if (num - 1717510305U == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary160 = this.0FEE0E1F;
										uint key155 = num ^ 2184625591U;
										IntPtr 012C2F79 = ldftn(39971548);
										num = (1063387867U & num);
										097E1C51.1F7F0274 value79 = new 097E1C51.1F7F0274(this, 012C2F79);
										num = 340475902U << (int)num;
										dictionary160[key155] = value79;
										num %= 836187741U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary161 = this.0FEE0E1F;
										num |= 1439978866U;
										uint key156 = num - 1610014031U;
										IntPtr 012C2F80 = ldftn(31BD3698);
										num %= 2047746929U;
										097E1C51.1F7F0274 value80 = new 097E1C51.1F7F0274(this, 012C2F80);
										num /= 97321217U;
										dictionary161[key156] = value80;
										if (num == 1256207364U)
										{
											goto IL_11;
										}
										num = (341409048U & num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary162 = this.0FEE0E1F;
										num = 1782864406U / num;
										uint key157 = num + 4183538436U;
										num = 1760514945U + num;
										dictionary162[key157] = new 097E1C51.1F7F0274(this.4A0B1810);
										if (num * 1277183707U == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary163 = this.0FEE0E1F;
										uint key158 = num - 1871943804U;
										num = 1969384116U % num;
										num = (247160513U & num);
										097E1C51.1F7F0274 value81 = new 097E1C51.1F7F0274(this.7F19411B);
										num %= 220155238U;
										dictionary163[key158] = value81;
										num &= 1718304751U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary164 = this.0FEE0E1F;
										uint key159 = num - 67768281U;
										num = 1394360344U << (int)num;
										IntPtr 012C2F81 = ldftn(768C1A06);
										num >>= 12;
										dictionary164[key159] = new 097E1C51.1F7F0274(this, 012C2F81);
										num = 869935761U * num;
										if (num * 294402844U == 0U)
										{
											goto IL_11;
										}
										num -= 880901666U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary165 = this.0FEE0E1F;
										num = (1505262249U | num);
										uint key160 = num + 2722497725U;
										num /= 2099469554U;
										IntPtr 012C2F82 = ldftn(130D57EB);
										num = 1498107299U * num;
										dictionary165[key160] = new 097E1C51.1F7F0274(this, 012C2F82);
										num = (1583757315U ^ num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary166 = this.0FEE0E1F;
										num <<= 27;
										dictionary166[num ^ 402653353U] = new 097E1C51.1F7F0274(this.75C147E9);
										num = 1554783922U * num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary167 = this.0FEE0E1F;
										uint key161 = num ^ 2952790186U;
										num = 568674085U % num;
										num += 1179851683U;
										IntPtr 012C2F83 = ldftn(35F356F3);
										num *= 568148130U;
										097E1C51.1F7F0274 value82 = new 097E1C51.1F7F0274(this, 012C2F83);
										num = 1100965710U << (int)num;
										dictionary167[key161] = value82;
										num = 1339961057U >> (int)num;
										this.0FEE0E1F[num - 1339960886U] = new 097E1C51.1F7F0274(this.68B23F03);
										num = 1053821601U - num;
										num = 355676728U + num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary168 = this.0FEE0E1F;
										num &= 803416280U;
										uint key162 = num ^ 69273716U;
										num = 1017917861U + num;
										num = 1596554824U / num;
										dictionary168[key162] = new 097E1C51.1F7F0274(this.5A1954EC);
										num = 692551005U << (int)num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary169 = this.0FEE0E1F;
										num >>= 24;
										uint key163 = num + 91U;
										097E1C51.1F7F0274 value83 = new 097E1C51.1F7F0274(this.768C1A06);
										num = 880804470U >> (int)num;
										dictionary169[key163] = value83;
										num -= 1760963833U;
										if (num * 1617436313U == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary170 = this.0FEE0E1F;
										uint key164 = num - 2534006649U;
										IntPtr 012C2F84 = ldftn(625228E8);
										num <<= 6;
										097E1C51.1F7F0274 value84 = new 097E1C51.1F7F0274(this, 012C2F84);
										num = 1980588322U + num;
										dictionary170[key164] = value84;
										num -= 1428770894U;
										if (1794520977U == num)
										{
											goto IL_11;
										}
										num ^= 1150379092U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary171 = this.0FEE0E1F;
										uint key165 = num ^ 2815262319U;
										097E1C51.1F7F0274 value85 = new 097E1C51.1F7F0274(this.5CB74094);
										num = (1598314907U ^ num);
										dictionary171[key165] = value85;
										num /= 425141235U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary172 = this.0FEE0E1F;
										uint key166 = num ^ 185U;
										num -= 919345815U;
										num = 978152502U % num;
										IntPtr 012C2F85 = ldftn(777F2AAE);
										num >>= 24;
										dictionary172[key166] = new 097E1C51.1F7F0274(this, 012C2F85);
										if ((1099190199U & num) == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary173 = this.0FEE0E1F;
										num |= 1671693239U;
										uint key167 = num - 1671693070U;
										num = (1801868691U | num);
										num -= 304749600U;
										097E1C51.1F7F0274 value86 = new 097E1C51.1F7F0274(this.625228E8);
										num /= 323620634U;
										dictionary173[key167] = value86;
										num = 2024372027U - num;
										if (1956215562U > num)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary174 = this.0FEE0E1F;
										num |= 663436533U;
										uint key168 = num - 2141945669U;
										num = 1124948476U << (int)num;
										IntPtr 012C2F86 = ldftn(690E2B29);
										num += 942280634U;
										097E1C51.1F7F0274 value87 = new 097E1C51.1F7F0274(this, 012C2F86);
										num = 862275713U << (int)num;
										dictionary174[key168] = value87;
										if (num > 694887564U)
										{
											goto IL_11;
										}
										num = (330577239U | num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary175 = this.0FEE0E1F;
										num ^= 1503275996U;
										dictionary175[num ^ 1311645240U] = new 097E1C51.1F7F0274(this.690E2B29);
										num = 364402382U * num;
										num = 1238515083U - num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary176 = this.0FEE0E1F;
										uint key169 = num + 2020935939U;
										num += 1147423998U;
										num = 885664612U << (int)num;
										IntPtr 012C2F87 = ldftn(60640AD4);
										num = 1161191841U * num;
										dictionary176[key169] = new 097E1C51.1F7F0274(this, 012C2F87);
										if (1463682783U < num)
										{
											goto IL_00;
										}
										num = 1667908370U - num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary177 = this.0FEE0E1F;
										num >>= 25;
										uint key170 = num + 143U;
										num += 1893216136U;
										097E1C51.1F7F0274 value88 = new 097E1C51.1F7F0274(this.598E21C0);
										num = 262554223U >> (int)num;
										dictionary177[key170] = value88;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary178 = this.0FEE0E1F;
										uint key171 = num ^ 15919U;
										IntPtr 012C2F88 = ldftn(68B23F03);
										num = 1398827643U * num;
										dictionary178[key171] = new 097E1C51.1F7F0274(this, 012C2F88);
										num >>= 28;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary179 = this.0FEE0E1F;
										uint key172 = num + 181U;
										IntPtr 012C2F89 = ldftn(377273A1);
										num = 1548438888U % num;
										097E1C51.1F7F0274 value89 = new 097E1C51.1F7F0274(this, 012C2F89);
										num >>= 18;
										dictionary179[key172] = value89;
										if ((num ^ 200167664U) == 0U)
										{
											goto IL_11;
										}
										num |= 1119553753U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary180 = this.0FEE0E1F;
										num ^= 1373438387U;
										uint key173 = num + 3969513294U;
										num = (374306467U & num);
										097E1C51.1F7F0274 value90 = new 097E1C51.1F7F0274(this.27F7218F);
										num %= 191330318U;
										dictionary180[key173] = value90;
										num = (1064834287U | num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary181 = this.0FEE0E1F;
										num /= 1164272554U;
										uint key174 = num - 4294967111U;
										num = (77488373U | num);
										097E1C51.1F7F0274 value91 = new 097E1C51.1F7F0274(this.598E21C0);
										num = (963328075U & num);
										dictionary181[key174] = value91;
										num *= 139742105U;
										if ((755202922U ^ num) == 0U)
										{
											goto IL_00;
										}
										num = (1147560277U | num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary182 = this.0FEE0E1F;
										uint key175 = num - 3740692771U;
										num <<= 9;
										IntPtr 012C2F90 = ldftn(0FB617A4);
										num = 1159032119U >> (int)num;
										097E1C51.1F7F0274 value92 = new 097E1C51.1F7F0274(this, 012C2F90);
										num &= 1940009944U;
										dictionary182[key175] = value92;
										num %= 393899307U;
										if (num << 16 == 0U)
										{
											goto IL_00;
										}
										num += 951281203U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary183 = this.0FEE0E1F;
										uint key176 = num - 1254009906U;
										097E1C51.1F7F0274 value93 = new 097E1C51.1F7F0274(this.75C147E9);
										num += 2122739785U;
										dictionary183[key176] = value93;
										if (351169897U + num == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary184 = this.0FEE0E1F;
										uint key177 = num ^ 3376749962U;
										num &= 1930372165U;
										IntPtr 012C2F91 = ldftn(7CEA02B4);
										num /= 1841065804U;
										097E1C51.1F7F0274 value94 = new 097E1C51.1F7F0274(this, 012C2F91);
										num = 65668618U - num;
										dictionary184[key177] = value94;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary185 = this.0FEE0E1F;
										num /= 1266114246U;
										uint key178 = num ^ 189U;
										num -= 1401752744U;
										num = 1475032932U / num;
										dictionary185[key178] = new 097E1C51.1F7F0274(this.4274788B);
										num %= 585710870U;
										if ((num ^ 301608856U) == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary186 = this.0FEE0E1F;
										uint key179 = num - 4294967106U;
										num = (1907451216U & num);
										dictionary186[key179] = new 097E1C51.1F7F0274(this.75C147E9);
										if (num == 1695178913U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary187 = this.0FEE0E1F;
										uint key180 = num - 4294967105U;
										num = 328085895U + num;
										097E1C51.1F7F0274 value95 = new 097E1C51.1F7F0274(this.768C1A06);
										num = 589195745U % num;
										dictionary187[key180] = value95;
										num |= 1381067134U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary188 = this.0FEE0E1F;
										uint key181 = num ^ 1607563710U;
										num /= 1299777329U;
										IntPtr 012C2F92 = ldftn(44D61E98);
										num = 1929070589U << (int)num;
										097E1C51.1F7F0274 value96 = new 097E1C51.1F7F0274(this, 012C2F92);
										num = 1150766874U - num;
										dictionary188[key181] = value96;
										num = 81355847U >> (int)num;
										num &= 1641685948U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary189 = this.0FEE0E1F;
										num = (1796543880U | num);
										uint key182 = num ^ 1809659213U;
										num %= 870991371U;
										IntPtr 012C2F93 = ldftn(7F19411B);
										num <<= 13;
										dictionary189[key182] = new 097E1C51.1F7F0274(this, 012C2F93);
										num = (1638497176U ^ num);
										if ((num ^ 1625434848U) == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary190 = this.0FEE0E1F;
										num ^= 729511063U;
										uint key183 = num ^ 1610403789U;
										num -= 497115837U;
										num &= 904792435U;
										IntPtr 012C2F94 = ldftn(625228E8);
										num = (14813625U ^ num);
										dictionary190[key183] = new 097E1C51.1F7F0274(this, 012C2F94);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary191 = this.0FEE0E1F;
										num >>= 28;
										uint key184 = num - 4294967101U;
										IntPtr 012C2F95 = ldftn(690E2B29);
										num %= 800682326U;
										097E1C51.1F7F0274 value97 = new 097E1C51.1F7F0274(this, 012C2F95);
										num = (1333282865U ^ num);
										dictionary191[key184] = value97;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary192 = this.0FEE0E1F;
										uint key185 = num + 2961684627U;
										num &= 1303082930U;
										097E1C51.1F7F0274 value98 = new 097E1C51.1F7F0274(this.27F7218F);
										num -= 1677537786U;
										dictionary192[key185] = value98;
										num /= 1995074206U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary193 = this.0FEE0E1F;
										num = 2032417382U / num;
										uint key186 = num + 2262550111U;
										IntPtr 012C2F96 = ldftn(69D76EFE);
										num <<= 27;
										dictionary193[key186] = new 097E1C51.1F7F0274(this, 012C2F96);
										if (num > 1972115075U)
										{
											goto IL_49;
										}
										this.0FEE0E1F[num + 3489661126U] = new 097E1C51.1F7F0274(this.69D76EFE);
										num = 670907870U * num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary194 = this.0FEE0E1F;
										num ^= 1514619928U;
										uint key187 = num + 95993007U;
										num = 1161590488U + num;
										dictionary194[key187] = new 097E1C51.1F7F0274(this.690E2B29);
										if (num << 8 == 0U)
										{
											goto IL_11;
										}
										num = 1606050836U << (int)num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary195 = this.0FEE0E1F;
										num |= 759711665U;
										uint key188 = num - 1834764009U;
										IntPtr 012C2F97 = ldftn(2DD92D6C);
										num = 1401781088U * num;
										dictionary195[key188] = new 097E1C51.1F7F0274(this, 012C2F97);
										num = (1617131073U ^ num);
										if (num == 447947596U)
										{
											goto IL_00;
										}
										num -= 190787052U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary196 = this.0FEE0E1F;
										uint key189 = num - 1300982892U;
										num ^= 443561275U;
										IntPtr 012C2F98 = ldftn(69F22BB5);
										num <<= 30;
										dictionary196[key189] = new 097E1C51.1F7F0274(this, 012C2F98);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary197 = this.0FEE0E1F;
										uint key190 = num ^ 2147483850U;
										num -= 841227953U;
										num /= 1702850023U;
										IntPtr 012C2F99 = ldftn(270E06CA);
										num %= 1689852114U;
										097E1C51.1F7F0274 value99 = new 097E1C51.1F7F0274(this, 012C2F99);
										num = (554374218U & num);
										dictionary197[key190] = value99;
										if (num == 1275139500U)
										{
											goto IL_2E;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary198 = this.0FEE0E1F;
										num = (1353189405U ^ num);
										uint key191 = num ^ 1353189590U;
										IntPtr 012C2F100 = ldftn(625228E8);
										num = 1412398704U << (int)num;
										dictionary198[key191] = new 097E1C51.1F7F0274(this, 012C2F100);
										if (1130236621U + num == 0U)
										{
											goto IL_2E;
										}
										num *= 926815406U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary199 = this.0FEE0E1F;
										uint key192 = num + 204U;
										num = (349714299U ^ num);
										IntPtr 012C2F101 = ldftn(40BB10D3);
										num = 1868069425U << (int)num;
										097E1C51.1F7F0274 value100 = new 097E1C51.1F7F0274(this, 012C2F101);
										num = 1673606270U << (int)num;
										dictionary199[key192] = value100;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary200 = this.0FEE0E1F;
										num = (1541084867U & num);
										uint key193 = num ^ 1136727183U;
										num -= 2125007037U;
										num = 1415199682U - num;
										IntPtr 012C2F102 = ldftn(40BB10D3);
										num /= 1819092438U;
										dictionary200[key193] = new 097E1C51.1F7F0274(this, 012C2F102);
										num = (1624339453U & num);
										if (num == 335418999U)
										{
											goto IL_00;
										}
										num <<= 14;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary201 = this.0FEE0E1F;
										num &= 1269910489U;
										dictionary201[num ^ 16590U] = new 097E1C51.1F7F0274(this.2DD92D6C);
										num = 1772376969U >> (int)num;
										num /= 1971010238U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary202 = this.0FEE0E1F;
										num += 298338612U;
										uint key194 = num + 3996628891U;
										num -= 827472202U;
										IntPtr 012C2F103 = ldftn(625228E8);
										num |= 859380509U;
										097E1C51.1F7F0274 value101 = new 097E1C51.1F7F0274(this, 012C2F103);
										num = 1938838998U << (int)num;
										dictionary202[key194] = value101;
										num = 1499031786U + num;
										if (1899846393U == num)
										{
											goto IL_49;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary203 = this.0FEE0E1F;
										uint key195 = num ^ 1499031610U;
										IntPtr 012C2F104 = ldftn(39971548);
										num >>= 9;
										dictionary203[key195] = new 097E1C51.1F7F0274(this, 012C2F104);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary204 = this.0FEE0E1F;
										num = 1978552509U << (int)num;
										uint key196 = num - 198180655U;
										num = (1864637790U | num);
										num = 1368283768U + num;
										IntPtr 012C2F105 = ldftn(7F19411B);
										num -= 967979767U;
										dictionary204[key196] = new 097E1C51.1F7F0274(this, 012C2F105);
										num <<= 18;
										if (num + 226581251U == 0U)
										{
											goto IL_75;
										}
										num = (2142130118U ^ num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary205 = this.0FEE0E1F;
										uint key197 = num - 1825722100U;
										num = 1166540814U << (int)num;
										dictionary205[key197] = new 097E1C51.1F7F0274(this.5CB74094);
										num >>= 1;
										this.0FEE0E1F[num - 822083821U] = new 097E1C51.1F7F0274(this.37ED550A);
										num <<= 15;
										if (num >= 249193401U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary206 = this.0FEE0E1F;
										num = 1758284078U >> (int)num;
										uint key198 = num + 2536683430U;
										num = (1283458240U | num);
										IntPtr 012C2F106 = ldftn(27F7218F);
										num = (710363101U | num);
										dictionary206[key198] = new 097E1C51.1F7F0274(this, 012C2F106);
										num *= 315556480U;
										if (821365318U > num)
										{
											goto IL_00;
										}
										num *= 1740646959U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary207 = this.0FEE0E1F;
										num = 141043488U / num;
										uint key199 = num ^ 213U;
										num /= 845897360U;
										num -= 2002136159U;
										dictionary207[key199] = new 097E1C51.1F7F0274(this.768C1A06);
										num &= 691995526U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary208 = this.0FEE0E1F;
										num = 578387273U * num;
										uint key200 = num - 31948458U;
										num |= 167131618U;
										IntPtr 012C2F107 = ldftn(27F7218F);
										num = 726010721U % num;
										dictionary208[key200] = new 097E1C51.1F7F0274(this, 012C2F107);
										if (num << 8 == 0U)
										{
											goto IL_11;
										}
										num *= 1921017159U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary209 = this.0FEE0E1F;
										uint key201 = num ^ 2366105336U;
										IntPtr 012C2F108 = ldftn(265604A2);
										num >>= 28;
										097E1C51.1F7F0274 value102 = new 097E1C51.1F7F0274(this, 012C2F108);
										num = (1211770399U & num);
										dictionary209[key201] = value102;
										num = 1292319025U / num;
										if (num > 615845332U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary210 = this.0FEE0E1F;
										num = 2117036259U - num;
										uint key202 = num + 2339471131U;
										num ^= 799813161U;
										IntPtr 012C2F109 = ldftn(598E21C0);
										num *= 1076236941U;
										097E1C51.1F7F0274 value103 = new 097E1C51.1F7F0274(this, 012C2F109);
										num = 1435264159U * num;
										dictionary210[key202] = value103;
										if (1038289558U < num)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary211 = this.0FEE0E1F;
										num = 4855938U - num;
										uint key203 = num ^ 3966751327U;
										num = 1177842017U - num;
										num = 446314157U + num;
										dictionary211[key203] = new 097E1C51.1F7F0274(this.17C253DE);
										if (num * 1397043924U == 0U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary212 = this.0FEE0E1F;
										uint key204 = num + 2342595410U;
										num = 767105579U / num;
										num &= 1210319765U;
										IntPtr 012C2F110 = ldftn(2DD92D6C);
										num = 1971931429U * num;
										097E1C51.1F7F0274 value104 = new 097E1C51.1F7F0274(this, 012C2F110);
										num = 848762191U + num;
										dictionary212[key204] = value104;
										if ((614955136U ^ num) == 0U)
										{
											goto IL_80;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary213 = this.0FEE0E1F;
										num |= 287728070U;
										uint key205 = num - 867661044U;
										num = 413492192U * num;
										IntPtr 012C2F111 = ldftn(778E5B5E);
										num >>= 9;
										097E1C51.1F7F0274 value105 = new 097E1C51.1F7F0274(this, 012C2F111);
										num = 1497975860U % num;
										dictionary213[key205] = value105;
										if (364196822U + num == 0U)
										{
											goto IL_00;
										}
										num -= 1284720269U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary214 = this.0FEE0E1F;
										num = 2126136275U << (int)num;
										uint key206 = num ^ 2550137052U;
										num = 2019889170U + num;
										num |= 261652416U;
										dictionary214[key206] = new 097E1C51.1F7F0274(this.58773899);
										if (num >= 1556349108U)
										{
											goto IL_11;
										}
										num -= 249191135U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary215 = this.0FEE0E1F;
										uint key207 = num ^ 287515694U;
										num -= 1479682129U;
										num |= 1242702167U;
										IntPtr 012C2F112 = ldftn(265604A2);
										num &= 777335242U;
										dictionary215[key207] = new 097E1C51.1F7F0274(this, 012C2F112);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary216 = this.0FEE0E1F;
										num /= 1159678972U;
										uint key208 = num - 4294967074U;
										IntPtr 012C2F113 = ldftn(55D55526);
										num ^= 1978608592U;
										097E1C51.1F7F0274 value106 = new 097E1C51.1F7F0274(this, 012C2F113);
										num /= 909710824U;
										dictionary216[key208] = value106;
										if (2091260309U == num)
										{
											goto IL_2E;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary217 = this.0FEE0E1F;
										num = (241779377U ^ num);
										uint key209 = num ^ 241779308U;
										num = 1306135007U - num;
										IntPtr 012C2F114 = ldftn(18784820);
										num = 488597896U * num;
										dictionary217[key209] = new 097E1C51.1F7F0274(this, 012C2F114);
										if (1033974337U <= num)
										{
											goto IL_00;
										}
										num %= 1966112619U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary218 = this.0FEE0E1F;
										uint key210 = num ^ 267312000U;
										num %= 2081958721U;
										IntPtr 012C2F115 = ldftn(35F356F3);
										num *= 1361532869U;
										097E1C51.1F7F0274 value107 = new 097E1C51.1F7F0274(this, 012C2F115);
										num = 303716414U * num;
										dictionary218[key210] = value107;
										if (1419214207U == num)
										{
											goto IL_11;
										}
										num = (1247901465U & num);
										Dictionary<uint, 097E1C51.1F7F0274> dictionary219 = this.0FEE0E1F;
										num /= 1412122159U;
										dictionary219[num ^ 225U] = new 097E1C51.1F7F0274(this.5EE86E2F);
										num /= 1249213633U;
										if (127810510U < num)
										{
											goto IL_00;
										}
										this.0FEE0E1F[num ^ 226U] = new 097E1C51.1F7F0274(this.130D57EB);
										num = 1637221107U + num;
										num = 329412775U + num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary220 = this.0FEE0E1F;
										uint key211 = num - 1966633655U;
										num = (448149952U | num);
										num &= 1966544727U;
										097E1C51.1F7F0274 value108 = new 097E1C51.1F7F0274(this.37ED550A);
										num <<= 4;
										dictionary220[key211] = value108;
										if (520160257U > num)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary221 = this.0FEE0E1F;
										num = 1792020808U % num;
										uint key212 = num ^ 393141452U;
										num = 252457743U << (int)num;
										num = (1148805625U | num);
										IntPtr 012C2F116 = ldftn(270E06CA);
										num %= 988615424U;
										097E1C51.1F7F0274 value109 = new 097E1C51.1F7F0274(this, 012C2F116);
										num = 2127918433U - num;
										dictionary221[key212] = value109;
										num = 1236140321U / num;
										num = 579671430U * num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary222 = this.0FEE0E1F;
										num &= 1318528922U;
										uint key213 = num + 229U;
										num *= 294256812U;
										dictionary222[key213] = new 097E1C51.1F7F0274(this.27F7218F);
										if (num == 277613572U)
										{
											goto IL_00;
										}
										num = 269813919U - num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary223 = this.0FEE0E1F;
										uint key214 = num ^ 269813881U;
										num -= 1248485824U;
										IntPtr 012C2F117 = ldftn(625228E8);
										num = (2146980502U & num);
										097E1C51.1F7F0274 value110 = new 097E1C51.1F7F0274(this, 012C2F117);
										num <<= 2;
										dictionary223[key214] = value110;
										num >>= 18;
										if (831983714U <= num)
										{
											goto IL_11;
										}
										num /= 1775325151U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary224 = this.0FEE0E1F;
										uint key215 = num + 231U;
										num = 1338661027U * num;
										IntPtr 012C2F118 = ldftn(5C162AE0);
										num = 430318313U << (int)num;
										dictionary224[key215] = new 097E1C51.1F7F0274(this, 012C2F118);
										if (num > 618275099U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary225 = this.0FEE0E1F;
										uint key216 = num ^ 430318081U;
										IntPtr 012C2F119 = ldftn(7F19411B);
										num |= 1382895460U;
										dictionary225[key216] = new 097E1C51.1F7F0274(this, 012C2F119);
										num = 1366888043U << (int)num;
										if (num * 1366841903U == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary226 = this.0FEE0E1F;
										num = (1642139623U & num);
										dictionary226[num - 566296343U] = new 097E1C51.1F7F0274(this.27F7218F);
										num ^= 1914439611U;
										if (545066472U >= num)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary227 = this.0FEE0E1F;
										num %= 1078999184U;
										uint key217 = num + 3966972351U;
										num = (1415396866U | num);
										num -= 907942048U;
										097E1C51.1F7F0274 value111 = new 097E1C51.1F7F0274(this.21CC7EF0);
										num = (580071275U | num);
										dictionary227[key217] = value111;
										if (num > 1954160100U)
										{
											goto IL_00;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary228 = this.0FEE0E1F;
										num = 878787478U >> (int)num;
										uint key218 = num ^ 429260U;
										num = 546453773U / num;
										IntPtr 012C2F120 = ldftn(58773899);
										num <<= 0;
										097E1C51.1F7F0274 value112 = new 097E1C51.1F7F0274(this, 012C2F120);
										num = (1414020390U | num);
										dictionary228[key218] = value112;
										num %= 1861646862U;
										if (num - 721251523U == 0U)
										{
											goto IL_11;
										}
										Dictionary<uint, 097E1C51.1F7F0274> dictionary229 = this.0FEE0E1F;
										uint key219 = num - 1414020371U;
										num = 1148988274U << (int)num;
										097E1C51.1F7F0274 value113 = new 097E1C51.1F7F0274(this.5AFD3DDB);
										num <<= 8;
										dictionary229[key219] = value113;
										num = (1524911520U & num);
										if (782967011U * num != 0U)
										{
											goto IL_49;
										}
										num <<= 1;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary230 = this.0FEE0E1F;
										uint key220 = num ^ 237U;
										IntPtr 012C2F121 = ldftn(270E06CA);
										num %= 1295008728U;
										097E1C51.1F7F0274 value114 = new 097E1C51.1F7F0274(this, 012C2F121);
										num = 645546881U - num;
										dictionary230[key220] = value114;
										if (464929715U + num == 0U)
										{
											goto IL_00;
										}
										num = 95884788U * num;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary231 = this.0FEE0E1F;
										uint key221 = num ^ 2051599130U;
										num >>= 19;
										dictionary231[key221] = new 097E1C51.1F7F0274(this.5AFD3DDB);
										num %= 1431444426U;
										Dictionary<uint, 097E1C51.1F7F0274> dictionary232 = this.0FEE0E1F;
										uint key222 = num + 4294963622U;
										097E1C51.1F7F0274 value115 = new 097E1C51.1F7F0274(this.73C37226);
										num = 937899688U * num;
										dictionary232[key222] = value115;
									}
									while (1584740440U % num == 0U);
									Dictionary<uint, 097E1C51.1F7F0274> dictionary233 = this.0FEE0E1F;
									uint key223 = num - 2099408120U;
									num = 1924795008U * num;
									num ^= 879588808U;
									IntPtr 012C2F122 = ldftn(58773899);
									num = (24599498U & num);
									dictionary233[key223] = new 097E1C51.1F7F0274(this, 012C2F122);
									num |= 653163369U;
									if ((742022736U ^ num) == 0U)
									{
										goto IL_2E;
									}
									Dictionary<uint, 097E1C51.1F7F0274> dictionary234 = this.0FEE0E1F;
									uint key224 = num ^ 654277400U;
									IntPtr 012C2F123 = ldftn(60640AD4);
									num = 239545083U + num;
									097E1C51.1F7F0274 value116 = new 097E1C51.1F7F0274(this, 012C2F123);
									num >>= 10;
									dictionary234[key224] = value116;
									num %= 779225917U;
									Dictionary<uint, 097E1C51.1F7F0274> dictionary235 = this.0FEE0E1F;
									uint key225 = num - 872631U;
									num ^= 586679411U;
									dictionary235[key225] = new 097E1C51.1F7F0274(this.728D144D);
									if (num * 1494901681U == 0U)
									{
										goto IL_49;
									}
									num = 2071204327U >> (int)num;
									Dictionary<uint, 097E1C51.1F7F0274> dictionary236 = this.0FEE0E1F;
									num -= 2068869792U;
									uint key226 = num ^ 2226097549U;
									num ^= 1756503482U;
									097E1C51.1F7F0274 value117 = new 097E1C51.1F7F0274(this.73C37226);
									num += 743665024U;
									dictionary236[key226] = value117;
									num = (204936962U ^ num);
									Dictionary<uint, 097E1C51.1F7F0274> dictionary237 = this.0FEE0E1F;
									num = (1755272202U ^ num);
									uint key227 = num - 2094578776U;
									num %= 1384669542U;
									num = 616851224U >> (int)num;
									IntPtr 012C2F124 = ldftn(4274788B);
									num |= 1239297546U;
									dictionary237[key227] = new 097E1C51.1F7F0274(this, 012C2F124);
									num = (1700923562U ^ num);
									num &= 2011584246U;
									Dictionary<uint, 097E1C51.1F7F0274> dictionary238 = this.0FEE0E1F;
									uint key228 = num + 3680234177U;
									num = (1401488207U & num);
									097E1C51.1F7F0274 value118 = new 097E1C51.1F7F0274(this.778E5B5E);
									num >>= 28;
									dictionary238[key228] = value118;
									num = 1845910421U << (int)num;
									if ((1867734417U ^ num) == 0U)
									{
										goto IL_00;
									}
									num = 2109690907U << (int)num;
									Dictionary<uint, 097E1C51.1F7F0274> dictionary239 = this.0FEE0E1F;
									num = (817260273U ^ num);
									uint key229 = num ^ 3017172487U;
									IntPtr 012C2F125 = ldftn(58373290);
									num = (590098022U ^ num);
									dictionary239[key229] = new 097E1C51.1F7F0274(this, 012C2F125);
									num <<= 22;
									Dictionary<uint, 097E1C51.1F7F0274> dictionary240 = this.0FEE0E1F;
									num /= 3700739U;
									uint key230 = num ^ 92U;
									num = (1177882018U & num);
									dictionary240[key230] = new 097E1C51.1F7F0274(this.37ED550A);
									if (num == 1015308199U)
									{
										goto IL_00;
									}
									Dictionary<uint, 097E1C51.1F7F0274> dictionary241 = this.0FEE0E1F;
									num = (1539928007U ^ num);
									dictionary241[num - 1539927661U] = new 097E1C51.1F7F0274(this.7B835829);
									num = 1642156011U + num;
									if (num < 656883868U)
									{
										goto IL_00;
									}
									num = 256069715U * num;
									Dictionary<uint, 097E1C51.1F7F0274> dictionary242 = this.0FEE0E1F;
									uint key231 = num - 2618623479U;
									IntPtr 012C2F126 = ldftn(39971548);
									num /= 600208001U;
									dictionary242[key231] = new 097E1C51.1F7F0274(this, 012C2F126);
									if (num >= 588278542U)
									{
										goto IL_00;
									}
									num /= 1600275199U;
									Dictionary<uint, 097E1C51.1F7F0274> dictionary243 = this.0FEE0E1F;
									uint key232 = num - 4294967046U;
									num = 1329862996U + num;
									dictionary243[key232] = new 097E1C51.1F7F0274(this.1AC96223);
									if (num - 1338392306U == 0U)
									{
										goto IL_49;
									}
									Dictionary<uint, 097E1C51.1F7F0274> dictionary244 = this.0FEE0E1F;
									num /= 320765207U;
									uint key233 = num + 247U;
									num = 308049051U % num;
									dictionary244[key233] = new 097E1C51.1F7F0274(this.598E21C0);
									if ((num ^ 2030071359U) == 0U)
									{
										goto IL_00;
									}
									num = (2045588193U & num);
									Dictionary<uint, 097E1C51.1F7F0274> dictionary245 = this.0FEE0E1F;
									uint key234 = num ^ 253U;
									num <<= 16;
									097E1C51.1F7F0274 value119 = new 097E1C51.1F7F0274(this.37ED550A);
									num ^= 113473526U;
									dictionary245[key234] = value119;
									num *= 482366897U;
									Dictionary<uint, 097E1C51.1F7F0274> dictionary246 = this.0FEE0E1F;
									uint key235 = num - 516529689U;
									num -= 1754296640U;
									IntPtr 012C2F127 = ldftn(598E21C0);
									num -= 393505847U;
									dictionary246[key235] = new 097E1C51.1F7F0274(this, 012C2F127);
									if (num != 1149136025U)
									{
										goto Block_143;
									}
								}
							}
						}
					}
				}
			}
			Block_143:
			num >>= 2;
			Dictionary<uint, 097E1C51.1F7F0274> dictionary247 = this.0FEE0E1F;
			num /= 1291543788U;
			uint key236 = num ^ 254U;
			num = 1010791162U + num;
			num = (1928279097U & num);
			IntPtr 012C2F128 = ldftn(5AFD3DDB);
			num = (1267750297U ^ num);
			097E1C51.1F7F0274 value120 = new 097E1C51.1F7F0274(this, 012C2F128);
			num = 1744469519U * num;
			dictionary247[key236] = value120;
			num = (2044491028U & num);
			Dictionary<uint, 097E1C51.1F7F0274> dictionary248 = this.0FEE0E1F;
			num = (1075911755U ^ num);
			uint key237 = num ^ 947203248U;
			num |= 1121783727U;
			dictionary248[key237] = new 097E1C51.1F7F0274(this.37ED550A);
		}
		while (num == 335183737U);
	}

	// Token: 0x060001D5 RID: 469 RVA: 0x002F5100 File Offset: 0x00175500
	private void 1B04365B(097E1C51.0A706168 7A012B55)
	{
		uint num = 1328422066U;
		do
		{
			num -= 2025537570U;
			Stack<097E1C51.4A781DBA> stack = this.02C11BDF;
			097E1C51.4A781DBA item = 7A012B55.3D28B13E();
			num = 1739931795U >> (int)num;
			stack.Push(item);
		}
		while (1218532184U * num == 0U);
	}

	// Token: 0x060001D6 RID: 470 RVA: 0x002F5148 File Offset: 0x00175548
	private 097E1C51.0A706168 2734307D()
	{
		return this.02C11BDF.Pop();
	}

	// Token: 0x060001D7 RID: 471 RVA: 0x002F5168 File Offset: 0x00175568
	private 097E1C51.0A706168 53D3742F()
	{
		return this.02C11BDF.Peek();
	}

	// Token: 0x060001D8 RID: 472 RVA: 0x002F5180 File Offset: 0x00175580
	private byte 5C36503F()
	{
		uint num = 781273310U;
		num ^= 853348144U;
		long num2 = this.4D045382;
		num = 848438512U >> (int)num;
		long num3 = (long)this.14EA7BFD;
		num %= 1129473168U;
		long value = num2 + num3;
		num -= 1428584273U;
		byte result = Marshal.ReadByte(new IntPtr(value));
		num >>= 28;
		num -= 2033001953U;
		int num4 = this.14EA7BFD;
		num = 2013940098U << (int)num;
		this.14EA7BFD = num4 + (int)(num ^ 345179137U);
		return result;
	}

	// Token: 0x060001D9 RID: 473 RVA: 0x002F5204 File Offset: 0x00175604
	private short 197810C1()
	{
		long num = this.4D045382;
		uint num2 = 1071404679U;
		long num3 = (long)this.14EA7BFD;
		num2 += 1480467140U;
		long num4 = num3;
		num2 = 1757817657U >> (int)num2;
		long value = num + num4;
		num2 = (271266668U | num2);
		short result = Marshal.ReadInt16(new IntPtr(value));
		num2 = (424873290U & num2);
		int num5 = this.14EA7BFD;
		num2 += 459363918U;
		int num6 = (int)(num2 ^ 727998356U);
		num2 = (963320475U | num2);
		int num7 = num5 + num6;
		num2 = 1230572739U * num2;
		this.14EA7BFD = num7;
		return result;
	}

	// Token: 0x060001DA RID: 474 RVA: 0x002F5284 File Offset: 0x00175684
	private int 293B3F40()
	{
		uint num = 82668818U;
		long num2 = this.4D045382;
		num |= 564794142U;
		long num3 = (long)this.14EA7BFD;
		num <<= 13;
		long num4 = num3;
		num = (1009594943U | num);
		long value = num2 + num4;
		num &= 1639079644U;
		IntPtr ptr = new IntPtr(value);
		num = 1645624511U % num;
		int result = Marshal.ReadInt32(ptr);
		num <<= 21;
		this.14EA7BFD += (int)(num + 2879389700U);
		return result;
	}

	// Token: 0x060001DB RID: 475 RVA: 0x002F52F4 File Offset: 0x001756F4
	private long 0F8515AE()
	{
		long num = this.4D045382;
		uint num2 = 33252800U;
		num2 *= 645887894U;
		long num3 = (long)this.14EA7BFD;
		num2 = 1570257941U - num2;
		long num4 = num3;
		num2 = 1342981206U << (int)num2;
		long value = num + num4;
		num2 /= 1928792453U;
		long result = Marshal.ReadInt64(new IntPtr(value));
		num2 *= 1800170567U;
		int num5 = this.14EA7BFD;
		int num6 = (int)(num2 ^ 1800170575U);
		num2 = 1214189702U % num2;
		this.14EA7BFD = num5 + num6;
		return result;
	}

	// Token: 0x060001DC RID: 476 RVA: 0x002F536C File Offset: 0x0017576C
	private float 547B41AA()
	{
		byte[] bytes = BitConverter.GetBytes(this.293B3F40());
		uint num = 273238293U;
		int startIndex = (int)(num - 273238293U);
		num = 1553758956U - num;
		return BitConverter.ToSingle(bytes, startIndex);
	}

	// Token: 0x060001DD RID: 477 RVA: 0x002F53A0 File Offset: 0x001757A0
	private double 650B430D()
	{
		uint num = 1621262152U;
		return BitConverter.ToDouble(BitConverter.GetBytes(this.0F8515AE()), (int)(num ^ 1621262152U));
	}

	// Token: 0x060001DE RID: 478 RVA: 0x002F53CC File Offset: 0x001757CC
	private void 75C147E9()
	{
		for (;;)
		{
			IL_00:
			uint num = 2008571968U;
			byte b = this.5C36503F();
			num += 1277581308U;
			for (;;)
			{
				IL_19:
				int num2 = this.293B3F40();
				num ^= 2099863891U;
				int num3 = num2;
				for (;;)
				{
					IL_2B:
					num = 29647432U << (int)num;
					int num4 = this.293B3F40();
					for (;;)
					{
						int num5 = this.293B3F40();
						num = 1082197541U / num;
						int num6 = num5;
						num = 1277038115U - num;
						if (193796336U / num != 0U)
						{
							break;
						}
						for (;;)
						{
							num = 948914594U >> (int)num;
							int num7 = this.293B3F40();
							num |= 665716550U;
							int 50A937A = num7;
							num %= 461918492U;
							097E1C51.5C236423 5C = null;
							num *= 437854010U;
							int num8 = (int)(num + 868590444U);
							if (194579626U != num)
							{
								097E1C51.5C236423 5C3;
								for (;;)
								{
									num = (1130981073U | num);
									if (2144740549U > num)
									{
										goto IL_00;
									}
									int num9 = num8;
									num = (475995775U | num);
									List<097E1C51.5C236423> list = this.652B3F15;
									num = 392585914U >> (int)num;
									if (num9 >= list.Count)
									{
										goto Block_7;
									}
									num = 926710496U;
									num /= 2118605103U;
									List<097E1C51.5C236423> list2 = this.652B3F15;
									num += 1650867877U;
									097E1C51.5C236423 5C2 = list2[num8];
									num |= 1044186023U;
									5C3 = 5C2;
									num = 1884258291U % num;
									if (1148612062U * num == 0U)
									{
										goto IL_19;
									}
									097E1C51.5C236423 5C4 = 5C3;
									num = 548736212U % num;
									int num10 = 5C4.23AA1DEA();
									int num11 = num3;
									num = 569001950U * num;
									if (num10 == num11)
									{
										num &= 879900407U;
										int num12 = 5C3.065D025F();
										int num13 = num4;
										num = 934220572U >> (int)num;
										num ^= 2706889847U;
										if (num12 == num13)
										{
											goto Block_3;
										}
									}
									num8 += (int)(num - 2706893783U);
									num ^= 1835906892U;
								}
								IL_1E6:
								if (5C == null)
								{
									bool flag = (num ^ 3024536191U) != 0U;
									num = 1339754178U + num;
									bool flag2 = flag;
									num -= 594890533U;
									if (1490832098U <= num)
									{
										int 41125F = num3;
										num -= 794779466U;
										int 205111BB = num4;
										num += 1987576219U;
										097E1C51.5C236423 5C5 = new 097E1C51.5C236423(41125F, 205111BB);
										num <<= 2;
										5C = 5C5;
										int num14 = (int)(num ^ 2668917172U);
										for (;;)
										{
											num = (63507089U ^ num);
											if (1089618717U == num)
											{
												goto IL_2B;
											}
											int num15 = num14;
											List<097E1C51.5C236423> list3 = this.652B3F15;
											num /= 2007072428U;
											int count = list3.Count;
											num *= 402196062U;
											if (num15 >= count)
											{
												goto Block_12;
											}
											097E1C51.5C236423 495D4AC = this.652B3F15[num14];
											num = 1072902093U;
											uint num16 = (uint)5C.7D7A5133(495D4AC);
											num = 151522243U >> (int)num;
											if (num16 < num + 4294948800U)
											{
												goto Block_10;
											}
											int num17 = num14;
											int num18 = (int)(num + 4294948801U);
											num /= 2019043296U;
											int num19 = num17 + num18;
											num = 168120672U << (int)num;
											num14 = num19;
											num += 2500796500U;
										}
										IL_32B:
										if ((586162261U ^ num) == 0U)
										{
											goto IL_00;
										}
										bool flag3 = flag2;
										num += 3003343461U;
										if (!flag3)
										{
											num = 641011861U >> (int)num;
											num = 1517227574U + num;
											List<097E1C51.5C236423> list4 = this.652B3F15;
											097E1C51.5C236423 item = 5C;
											num >>= 26;
											list4.Add(item);
											num ^= 3024536169U;
											goto IL_383;
										}
										goto IL_383;
										Block_12:
										num ^= 381314628U;
										goto IL_32B;
										Block_10:
										num /= 786718521U;
										List<097E1C51.5C236423> list5 = this.652B3F15;
										int index = num14;
										097E1C51.5C236423 item2 = 5C;
										num += 21192730U;
										list5.Insert(index, item2);
										flag2 = (num - 21192729U != 0U);
										goto IL_32B;
									}
									break;
								}
								IL_383:
								097E1C51.5C236423 5C6 = 5C;
								num <<= 3;
								byte 29EB0DDF = b;
								num *= 2019833107U;
								int 1B584DDE = num6;
								num >>= 3;
								5C6.0F984DA8(29EB0DDF, 1B584DDE, 50A937A);
								if (num != 1511738962U)
								{
									return;
								}
								continue;
								Block_3:
								num = (353446311U ^ num);
								if (417011168U > num)
								{
									goto IL_00;
								}
								5C = 5C3;
								if (1127292468U < num)
								{
									goto IL_1E6;
								}
								goto IL_2B;
								Block_7:
								num += 3024536191U;
								goto IL_1E6;
							}
							goto IL_19;
						}
					}
				}
			}
		}
	}

	// Token: 0x060001DF RID: 479 RVA: 0x002F5794 File Offset: 0x00175B94
	private TypeCode 61563953(097E1C51.0A706168 5F945A15, 097E1C51.0A706168 1508543E)
	{
		uint num = 1806988446U;
		TypeCode typeCode;
		TypeCode typeCode3;
		for (;;)
		{
			num %= 2016215456U;
			typeCode = 5F945A15.6DFD59A4();
			num &= 564950337U;
			if (num * 1437100919U != 0U)
			{
				goto IL_29;
			}
			for (;;)
			{
				IL_56:
				TypeCode typeCode2 = typeCode;
				uint num2 = num - 1532894246U;
				num ^= 98700009U;
				if (typeCode2 == num2)
				{
					goto IL_AE;
				}
				num = (1755854828U ^ num);
				for (;;)
				{
					bool flag = typeCode3 != TypeCode.Empty;
					num -= 2121012633U;
					num ^= 3910409543U;
					if (!flag)
					{
						goto IL_AE;
					}
					num /= 2061253879U;
					uint num3 = (uint)typeCode3;
					num ^= 265497903U;
					if (num3 == (num ^ 265497902U))
					{
						goto Block_4;
					}
					TypeCode typeCode4 = typeCode;
					num = 652574460U * num;
					uint num4 = num ^ 3911752782U;
					num &= 303182671U;
					if (typeCode4 == num4)
					{
						goto Block_5;
					}
					num = 353263268U % num;
					if (num == 150090262U)
					{
						break;
					}
					TypeCode typeCode5 = typeCode3;
					uint num5 = num ^ 2010U;
					num >>= 1;
					if (typeCode5 == num5)
					{
						goto Block_9;
					}
					num = 1612526411U - num;
					if (num < 580801149U)
					{
						goto IL_40;
					}
					uint num6 = (uint)typeCode;
					num = (437726736U & num);
					if (num6 == num - 1388020U)
					{
						goto Block_12;
					}
					TypeCode typeCode6 = typeCode3;
					uint num7 = num ^ 1388044U;
					num = 1201216528U / num;
					if (typeCode6 != num7)
					{
						goto IL_25B;
					}
					num >>= 27;
					if (1662549016U * num == 0U)
					{
						goto Block_16;
					}
				}
			}
			Block_16:
			TypeCode typeCode7 = typeCode;
			uint num8 = num - 4294967287U;
			num <<= 29;
			if (typeCode7 != num8)
			{
				if (325522118U * num != 0U)
				{
					goto IL_29;
				}
				TypeCode typeCode8 = typeCode;
				uint num9 = num - 4294967285U;
				num += 0U;
				if (typeCode8 != num9)
				{
					goto Block_19;
				}
			}
			num &= 1273200321U;
			if ((2092069121U & num) == 0U)
			{
				return typeCode;
			}
			continue;
			IL_25B:
			num = (1716413533U | num);
			if (1244343036U >= num)
			{
				continue;
			}
			uint num10 = (uint)typeCode;
			num = 1229732706U / num;
			if (num10 == (num ^ 14U))
			{
				goto IL_2C1;
			}
			num = 464985662U >> (int)num;
			if (num > 1071319862U)
			{
				goto IL_29;
			}
			TypeCode typeCode9 = typeCode3;
			num = 1926856683U / num;
			uint num11 = num + 10U;
			num &= 678173948U;
			if (typeCode9 == num11)
			{
				goto Block_24;
			}
			num = (65409618U ^ num);
			if (num * 466692388U == 0U)
			{
				continue;
			}
			uint num12 = (uint)typeCode;
			num = 243085218U + num;
			if (num12 == (num ^ 308494837U))
			{
				goto IL_31F;
			}
			num = 1751153344U + num;
			TypeCode typeCode10 = typeCode3;
			uint num13 = num - 2059648171U;
			num = 2045643222U + num;
			if (typeCode10 == num13)
			{
				goto Block_27;
			}
			if (1938570896 << (int)num != 0)
			{
				goto Block_28;
			}
			continue;
			Block_5:
			num = (1787577855U ^ num);
			if (1060595135U < num)
			{
				goto Block_6;
			}
			goto IL_29;
			IL_40:
			bool flag2 = typeCode != TypeCode.Empty;
			num = 1295670668U - num;
			if (flag2)
			{
				num = 1532894247U % num;
				goto IL_56;
			}
			goto IL_AE;
			IL_29:
			num %= 1361381154U;
			typeCode3 = 1508543E.6DFD59A4();
			num -= 858139970U;
			goto IL_40;
		}
		Block_4:
		num += 1323887007U;
		IL_AE:
		return (TypeCode)(num - 1589384910U);
		Block_6:
		uint num14 = (uint)typeCode3;
		num = (557647057U ^ num);
		if (num14 != (num ^ 1269911907U))
		{
			return (TypeCode)(num - 1269911914U);
		}
		num = (1198074865U | num);
		return typeCode;
		Block_9:
		if (typeCode != (int)num + (TypeCode)(-991))
		{
			num = 1737430560U >> (int)num;
			return (int)num + (TypeCode)(-6786838);
		}
		num = 1259543107U - num;
		return typeCode3;
		Block_12:
		num -= 1912277183U;
		uint num15 = (uint)typeCode3;
		num >>= 10;
		if (num15 != num + 4292639104U)
		{
			TypeCode typeCode11 = typeCode3;
			uint num16 = num ^ 2328194U;
			num = 656437128U * num;
			num += 3587090113U;
			if (typeCode11 != num16)
			{
				num = 557790913U / num;
				return (TypeCode)(num ^ 239U);
			}
		}
		num &= 648442878U;
		return typeCode;
		Block_19:
		return (TypeCode)(num - 0U);
		Block_24:
		num += 4294967292U;
		IL_2C1:
		num /= 1636903263U;
		return (TypeCode)(num ^ 14U);
		Block_27:
		num += 498170730U;
		IL_31F:
		num = 181496481U >> (int)num;
		return (TypeCode)(num ^ 7U);
		Block_28:
		TypeCode typeCode12 = typeCode;
		num = (124519049U & num);
		uint num17 = num - 69206653U;
		num = (1124478189U ^ num);
		if (typeCode12 != num17)
		{
			TypeCode typeCode13 = typeCode3;
			num = 1797682606U + num;
			uint num18 = num - 2991367176U;
			num /= 2035040927U;
			if (typeCode13 != num18)
			{
				return (int)num + TypeCode.UInt16;
			}
			num += 1193684580U;
		}
		return (TypeCode)(num ^ 1193684590U);
	}

	// Token: 0x060001E0 RID: 480 RVA: 0x002F5B3C File Offset: 0x00175F3C
	private unsafe 097E1C51.0A706168 08C207B3(097E1C51.0A706168 381A12FC, 097E1C51.0A706168 6E255BAF, bool 0EAD58CE, bool 0F4D3299)
	{
		uint num = 1441938981U;
		097E1C51.277D4587 277D2;
		IntPtr intPtr;
		long num27;
		long num28;
		long num30;
		long value2;
		097E1C51.277D4587 277D4;
		float num42;
		float num43;
		int num44;
		int num45;
		int 339906C;
		double num50;
		double num52;
		for (;;)
		{
			IL_07:
			num = 952844964U + num;
			num &= 1528177769U;
			num -= 1326402678U;
			TypeCode typeCode = this.61563953(381A12FC, 6E255BAF);
			if (num != 1910205656U)
			{
				for (;;)
				{
					IL_3B:
					int num2 = typeCode - (TypeCode)(num ^ 3137647578U);
					num = 791753296U % num;
					switch (num2)
					{
					case 0:
						goto IL_72;
					case 1:
					{
						num -= 886600938U;
						if (num * 1941715812U == 0U)
						{
							continue;
						}
						int value;
						if (0F4D3299)
						{
							num = 1654546553U / num;
							num /= 240352944U;
							uint num3 = 381A12FC.2CD32589();
							num = (1879721846U ^ num);
							uint num4 = num3;
							if (894316830U == num)
							{
								goto IL_07;
							}
							num >>= 24;
							uint num5 = 6E255BAF.2CD32589();
							num *= 1820148202U;
							uint num6 = num5;
							if (238292874U > num)
							{
								goto IL_07;
							}
							int num8;
							if (!0EAD58CE)
							{
								num |= 660229230U;
								if (707158514U >= num)
								{
									goto IL_07;
								}
								int num7 = (int)num4;
								num *= 2106405025U;
								num8 = num7 + (int)num6;
							}
							else
							{
								if (num + 861291162U == 0U)
								{
									continue;
								}
								int num9 = (int)num4;
								int num10 = (int)num6;
								num = 1794255451U % num;
								num8 = checked(num9 + num10);
								num ^= 260483445U;
							}
							num = 492263856U * num;
							value = num8;
						}
						else
						{
							num = 757661991U << (int)num;
							if (num / 406019142U == 0U)
							{
								break;
							}
							num = (127298298U & num);
							int num11 = 381A12FC.B9179A8D();
							num = 208623512U % num;
							int num12 = num11;
							num >>= 16;
							num = 244012887U << (int)num;
							int num13 = 6E255BAF.B9179A8D();
							num = 1840514783U >> (int)num;
							int num16;
							if (!0EAD58CE)
							{
								num /= 1113416924U;
								int num14 = num12;
								num = 1036016196U * num;
								int num15 = num13;
								num = 1105594840U / num;
								num16 = num14 + num15;
							}
							else
							{
								num *= 976644206U;
								if ((1414280459U ^ num) == 0U)
								{
									goto IL_07;
								}
								int num17 = num12;
								num = (1039102344U & num);
								int num18 = num13;
								num += 1439397068U;
								num16 = checked(num17 + num18);
								num += 1829079477U;
							}
							value = num16;
							num ^= 207039905U;
						}
						097E1C51.277D4587 277D;
						if (381A12FC.6DFD59A4() != typeCode)
						{
							num = (160041432U & num);
							if (num >= 1173167108U)
							{
								continue;
							}
							num -= 777861007U;
							277D = (097E1C51.277D4587)6E255BAF;
						}
						else
						{
							if (1772244308U < num)
							{
								goto IL_07;
							}
							277D = (097E1C51.277D4587)381A12FC;
							num ^= 3589536849U;
						}
						277D2 = 277D;
						if (num < 1016603981U)
						{
							goto IL_07;
						}
						intPtr = new IntPtr(value);
						num += 862872702U;
						if (329187841U > num)
						{
							goto Block_51;
						}
						continue;
					}
					case 2:
						if (1486696358U >= num)
						{
							if (0F4D3299)
							{
								num /= 1518684738U;
								ulong num19 = 381A12FC.083A097E();
								num >>= 19;
								if (num * 878054208U == 0U)
								{
									ulong num20 = 6E255BAF.083A097E();
									num += 1708478014U;
									ulong num21 = num20;
									if (1206474207U >= num)
									{
										goto IL_07;
									}
									num = 1736064546U / num;
									long num24;
									if (!0EAD58CE)
									{
										if (num > 1712919775U)
										{
											goto IL_72;
										}
										long num22 = (long)num19;
										long num23 = (long)num21;
										num -= 1197935569U;
										num24 = num22 + num23;
									}
									else
									{
										num ^= 1199375369U;
										long num25 = (long)num19;
										long num26 = (long)num21;
										num /= 1434327214U;
										num24 = checked(num25 + num26);
										num ^= 3097031728U;
									}
									num27 = num24;
									if (96077403U <= num)
									{
										goto Block_17;
									}
								}
							}
							else
							{
								num += 157505767U;
								num28 = 381A12FC.8EC1A2BC();
								if (num * 2061328654U == 0U)
								{
									continue;
								}
								long num29 = 6E255BAF.8EC1A2BC();
								num = 1099525593U + num;
								num30 = num29;
								num /= 932871239U;
								if (num > 1674398175U)
								{
									goto IL_07;
								}
								if (0EAD58CE)
								{
									goto IL_313;
								}
								if ((num ^ 556404014U) != 0U)
								{
									goto Block_21;
								}
							}
						}
						break;
					case 3:
					{
						if (1496343604U < num)
						{
							goto IL_8F;
						}
						num = 50685465U >> (int)num;
						if (0F4D3299)
						{
							if (num > 21765654U)
							{
								goto IL_72;
							}
							ulong num31 = 381A12FC.083A097E();
							num *= 1721309822U;
							num = 575276991U / num;
							ulong num32 = 6E255BAF.083A097E();
							long num35;
							if (!0EAD58CE)
							{
								num /= 882193807U;
								if (399008839U <= num)
								{
									goto IL_07;
								}
								long num33 = (long)num31;
								num -= 769806566U;
								long num34 = (long)num32;
								num = 1443570009U >> (int)num;
								num35 = num33 + num34;
							}
							else
							{
								num35 = (long)(checked(num31 + num32));
								num ^= 21U;
							}
							num ^= 1062894997U;
							value2 = num35;
							if (2117162396U <= num)
							{
								continue;
							}
						}
						else
						{
							num -= 1326980120U;
							num <<= 30;
							long num36 = 381A12FC.8EC1A2BC();
							long num37 = 6E255BAF.8EC1A2BC();
							num >>= 11;
							long num40;
							if (!0EAD58CE)
							{
								num = 2092049346U << (int)num;
								if (num == 370872606U)
								{
									goto IL_07;
								}
								long num38 = num36;
								long num39 = num37;
								num = 2050384564U >> (int)num;
								num40 = num38 + num39;
							}
							else
							{
								num40 = checked(num36 + num37);
								num ^= 512071853U;
							}
							value2 = num40;
							num ^= 567797037U;
						}
						num = 1365969333U * num;
						num = (645489769U ^ num);
						TypeCode typeCode2 = 381A12FC.6DFD59A4();
						TypeCode typeCode3 = typeCode;
						num = 1068568243U + num;
						097E1C51.277D4587 277D3;
						if (typeCode2 != typeCode3)
						{
							num %= 1531406309U;
							if (1814977356U < num)
							{
								goto IL_07;
							}
							277D3 = (097E1C51.277D4587)6E255BAF;
						}
						else
						{
							277D3 = (097E1C51.277D4587)381A12FC;
							num ^= 0U;
						}
						num = (2070833683U | num);
						277D4 = 277D3;
						num = 1296452925U % num;
						if (num % 1870742501U != 0U)
						{
							goto Block_62;
						}
						continue;
					}
					case 4:
					{
						num ^= 167776568U;
						097E1C51.0A706168 0A;
						if (!0F4D3299)
						{
							if (595033771U == num)
							{
								break;
							}
							0A = 381A12FC;
						}
						else
						{
							0A = 381A12FC.58E29230();
							num += 0U;
						}
						num |= 1452756223U;
						float num41 = 0A.E1E85554();
						num *= 944188453U;
						num42 = num41;
						num *= 1013713866U;
						097E1C51.0A706168 0A2;
						if (!0F4D3299)
						{
							if ((266303268U & num) == 0U)
							{
								continue;
							}
							0A2 = 6E255BAF;
						}
						else
						{
							num -= 1490551605U;
							if (num > 1910775567U)
							{
								continue;
							}
							0A2 = 6E255BAF.58E29230();
							num += 1490551605U;
						}
						num43 = 0A2.E1E85554();
						if (num == 255729663U)
						{
							goto IL_07;
						}
						if (!0EAD58CE)
						{
							goto Block_28;
						}
						num -= 746326313U;
						if (num + 717183560U != 0U)
						{
							goto Block_29;
						}
						continue;
					}
					case 5:
						num ^= 391589758U;
						if (0F4D3299)
						{
							goto IL_485;
						}
						if (num >= 94396040U)
						{
							goto Block_31;
						}
						goto IL_8F;
					}
					goto IL_6D;
					for (;;)
					{
						IL_72:
						num = (1412503786U | num);
						if (0F4D3299)
						{
							break;
						}
						num = (857360812U & num);
						if (num / 1205538494U != 0U)
						{
							goto IL_3B;
						}
						num = 1483177031U - num;
						num44 = 381A12FC.B9179A8D();
						num |= 316554043U;
						if (426146246U > num)
						{
							goto IL_07;
						}
						num45 = 6E255BAF.B9179A8D();
						num ^= 1581404781U;
						if (!0EAD58CE)
						{
							goto Block_9;
						}
						num = 141756645U >> (int)num;
						if (num % 808138872U != 0U)
						{
							goto Block_10;
						}
					}
					if (991394308U < num)
					{
						goto IL_8F;
					}
					continue;
					IL_6D:
					num |= 2069510715U;
					if ((45815642U ^ num) != 0U)
					{
						goto Block_63;
					}
					continue;
					IL_8F:
					num <<= 29;
					uint num46 = 381A12FC.2CD32589();
					num *= 1863129899U;
					uint num47 = num46;
					num = 1925604152U * num;
					if (num * 1887906865U != 0U)
					{
						goto IL_07;
					}
					uint num48 = 6E255BAF.2CD32589();
					num = (815943940U ^ num);
					int num49;
					if (!0EAD58CE)
					{
						num -= 1845437866U;
						if (1708682544U + num == 0U)
						{
							goto IL_07;
						}
						num49 = (int)(num47 + num48);
					}
					else
					{
						if (1475898015U == num)
						{
							goto IL_07;
						}
						num49 = (int)(checked(num47 + num48));
						num ^= 4060183134U;
					}
					339906C = num49;
					if (num != 1069943457U)
					{
						goto Block_6;
					}
					goto IL_6D;
				}
				IL_4A9:
				097E1C51.0A706168 0A3;
				num50 = 0A3.D87DFA30();
				num |= 728398297U;
				if (687541775U + num == 0U)
				{
					continue;
				}
				num = (460355376U & num);
				097E1C51.0A706168 0A4;
				if (!0F4D3299)
				{
					0A4 = 6E255BAF;
				}
				else
				{
					num *= 535374263U;
					if (1004749275U > num)
					{
						continue;
					}
					num %= 791772379U;
					0A4 = 6E255BAF.58E29230();
					num += 4233820566U;
				}
				num = 804736568U >> (int)num;
				double num51 = 0A4.D87DFA30();
				num -= 1148349544U;
				num52 = num51;
				if (!0EAD58CE)
				{
					goto Block_35;
				}
				if (1493960509U - num != 0U)
				{
					goto Block_36;
				}
				continue;
				IL_485:
				num *= 2026242639U;
				num &= 90263319U;
				0A3 = 381A12FC.58E29230();
				num += 858050588U;
				goto IL_4A9;
				Block_31:
				0A3 = 381A12FC;
				goto IL_4A9;
			}
		}
		Block_6:
		goto IL_1E0;
		Block_9:
		num = (665341132U & num);
		int num53 = num44;
		int num54 = num45;
		num = (348066755U | num);
		int num55 = num53 + num54;
		goto IL_1D4;
		Block_10:
		int num56 = num44;
		int num57 = num45;
		num = 1088293891U * num;
		num55 = checked(num56 + num57);
		num += 1633535343U;
		IL_1D4:
		339906C = num55;
		num ^= 4145819801U;
		IL_1E0:
		num = 1805149015U * num;
		return new 097E1C51.14C26E2D(339906C);
		Block_17:
		goto IL_346;
		Block_21:
		long num58 = num28 + num30;
		goto IL_32B;
		IL_313:
		num = 372526562U + num;
		num58 = checked(num28 + num30);
		num ^= 372526566U;
		IL_32B:
		num = 478570204U >> (int)num;
		num27 = num58;
		num += 2977389177U;
		IL_346:
		long 6DB81B = num27;
		num = 1883444191U >> (int)num;
		return new 097E1C51.31EF1BAE(6DB81B);
		Block_28:
		float num59 = num42;
		num = 1884294367U % num;
		float 52F83C3E = num59 + num43;
		goto IL_452;
		Block_29:
		float num60 = num42;
		num += 951999603U;
		52F83C3E = num60 + num43;
		num += 2671432185U;
		IL_452:
		num = (1031559277U ^ num);
		return new 097E1C51.7B032626(52F83C3E);
		Block_35:
		num *= 1869218643U;
		double num61 = num50;
		double num62 = num52;
		num = 1873823914U / num;
		double 25B00FBF = num61 + num62;
		goto IL_576;
		Block_36:
		double num63 = num50;
		double num64 = num52;
		num %= 1973297248U;
		25B00FBF = num63 + num64;
		num ^= 1173332783U;
		IL_576:
		num %= 1960337359U;
		return new 097E1C51.4DA65608(25B00FBF);
		Block_51:
		void* ptr = intPtr.ToPointer();
		num -= 1953644401U;
		Type type = 277D2.686C3617();
		num /= 1228236168U;
		object 526508FB = Pointer.Box(ptr, type);
		num = 1072698670U + num;
		097E1C51.277D4587 277D5 = 277D2;
		num = 1949719561U - num;
		return new 097E1C51.277D4587(526508FB, 277D5.686C3617());
		Block_62:
		IntPtr intPtr2 = new IntPtr(value2);
		num *= 1852862958U;
		intPtr = intPtr2;
		num = (659561537U | num);
		void* ptr2 = intPtr.ToPointer();
		Type type2 = 277D4.686C3617();
		num = 1649550291U * num;
		object 526508FB2 = Pointer.Box(ptr2, type2);
		097E1C51.277D4587 277D6 = 277D4;
		num /= 627056752U;
		Type 30684F7F = 277D6.686C3617();
		num = 796622023U % num;
		return new 097E1C51.277D4587(526508FB2, 30684F7F);
		Block_63:
		throw new InvalidOperationException();
	}

	// Token: 0x060001E1 RID: 481 RVA: 0x002F6590 File Offset: 0x00176990
	private unsafe 097E1C51.0A706168 28D77A74(097E1C51.0A706168 240D6E99, 097E1C51.0A706168 5F6A540D, bool 379344A5, bool 42AD3445)
	{
		uint num;
		int num11;
		097E1C51.277D4587 277D2;
		long num19;
		long num21;
		long num28;
		ulong num39;
		ulong num41;
		double num43;
		double num45;
		097E1C51.277D4587 277D3;
		int 339906C;
		for (;;)
		{
			IL_00:
			num = 2135573021U;
			num = (1851591277U ^ num);
			TypeCode typeCode = this.61563953(240D6E99, 5F6A540D);
			num >>= 16;
			TypeCode typeCode2 = typeCode;
			if (num > 604326303U)
			{
				goto IL_83;
			}
			for (;;)
			{
				IL_30:
				uint num2 = (uint)typeCode2;
				num = 675290815U >> (int)num;
				uint num3 = num2 - (num ^ 89U);
				num = 1942505958U + num;
				switch (num3)
				{
				case 0:
					goto IL_83;
				case 1:
				{
					if (num == 1949053371U)
					{
						continue;
					}
					if (42AD3445)
					{
						if (1658918703U == num)
						{
							goto IL_00;
						}
						num = 1583109231U >> (int)num;
						uint num4 = 240D6E99.2CD32589();
						num = (1944674213U | num);
						uint num5 = num4;
						num &= 1016685720U;
						if (282143565U + num == 0U)
						{
							continue;
						}
						uint num6 = 5F6A540D.2CD32589();
						num %= 780230225U;
						if (1831275560U >> (int)num == 0U)
						{
							goto IL_7E;
						}
						num = (604728171U & num);
						int num8;
						if (!379344A5)
						{
							if (132479664U < num)
							{
								goto IL_00;
							}
							int num7 = (int)num5;
							num = 928804543U >> (int)num;
							num8 = num7 - (int)num6;
						}
						else
						{
							num %= 1972788884U;
							if (num + 1186954287U == 0U)
							{
								continue;
							}
							int num9 = (int)num5;
							int num10 = (int)num6;
							num *= 1528721205U;
							num8 = checked(num9 - num10);
							num ^= 3418614024U;
						}
						num = (134969606U | num);
						num11 = num8;
					}
					else
					{
						num = (1310284409U ^ num);
						num ^= 1986296591U;
						int num12 = 240D6E99.B9179A8D();
						num = 1262773902U >> (int)num;
						int num13 = num12;
						num = 284179147U + num;
						if (num == 2022774688U)
						{
							continue;
						}
						int num14 = 5F6A540D.B9179A8D();
						num |= 2098935029U;
						int num15;
						if (!379344A5)
						{
							num15 = num13 - num14;
						}
						else
						{
							num = (1125188228U | num);
							int num16 = num13;
							int num17 = num14;
							num %= 430721400U;
							num15 = checked(num16 - num17);
							num ^= 1706108896U;
						}
						num11 = num15;
						num += 2443986906U;
					}
					num ^= 82262914U;
					if (1760632628U % num == 0U)
					{
						continue;
					}
					num += 322777265U;
					TypeCode typeCode3 = 240D6E99.6DFD59A4();
					num = 1613200943U / num;
					TypeCode typeCode4 = typeCode2;
					num = 491027798U % num;
					097E1C51.277D4587 277D;
					if (typeCode3 != typeCode4)
					{
						num = 1634170929U % num;
						num *= 50596318U;
						277D = (097E1C51.277D4587)5F6A540D;
					}
					else
					{
						num = 808461153U << (int)num;
						277D = (097E1C51.277D4587)240D6E99;
						num ^= 3284436058U;
					}
					num %= 748431398U;
					277D2 = 277D;
					if ((1500975674U ^ num) != 0U)
					{
						goto Block_44;
					}
					continue;
				}
				case 2:
				{
					if (num - 1827216166U == 0U)
					{
						continue;
					}
					num |= 1322600106U;
					if (42AD3445)
					{
						goto Block_10;
					}
					num %= 1087395419U;
					num = 692594039U << (int)num;
					long num18 = 240D6E99.8EC1A2BC();
					num = 943682041U - num;
					num19 = num18;
					num = 1130892620U >> (int)num;
					long num20 = 5F6A540D.8EC1A2BC();
					num |= 291717739U;
					num21 = num20;
					num |= 2027946415U;
					if (!379344A5)
					{
						goto Block_16;
					}
					if (1807105363U < num)
					{
						goto Block_17;
					}
					continue;
				}
				case 3:
					num = 976171792U / num;
					num ^= 1093673758U;
					if (42AD3445)
					{
						num = 1978031514U << (int)num;
						ulong num22 = 240D6E99.083A097E();
						num = 1184713933U << (int)num;
						ulong num23 = 5F6A540D.083A097E();
						num = 2115526122U + num;
						if (num / 122495376U == 0U)
						{
							continue;
						}
						num = 2133011378U >> (int)num;
						long num26;
						if (!379344A5)
						{
							if (num == 757021940U)
							{
								goto IL_00;
							}
							long num24 = (long)num22;
							num = 1977768381U % num;
							long num25 = (long)num23;
							num >>= 2;
							num26 = num24 - num25;
						}
						else
						{
							num = 358707595U * num;
							long num27 = (long)num22;
							num >>= 20;
							num26 = checked(num27 - (long)num23);
							num += 4294966465U;
						}
						num |= 2124887242U;
						num28 = num26;
						if ((num ^ 1192325194U) == 0U)
						{
							goto IL_83;
						}
					}
					else
					{
						if (133068679U == num)
						{
							goto IL_00;
						}
						long num29 = 240D6E99.8EC1A2BC();
						num = 864124366U * num;
						if ((num ^ 462696368U) == 0U)
						{
							continue;
						}
						num = (951280138U ^ num);
						long num30 = 5F6A540D.8EC1A2BC();
						num = (150738765U | num);
						long num31 = num30;
						num += 1607416436U;
						num /= 110118357U;
						long num34;
						if (!379344A5)
						{
							num = (596127000U | num);
							long num32 = num29;
							num /= 207644091U;
							long num33 = num31;
							num = 682246030U / num;
							num34 = num32 - num33;
						}
						else
						{
							num34 = checked(num29 - num31);
							num += 341122977U;
						}
						num *= 609114564U;
						num28 = num34;
						num += 2865541519U;
					}
					if (1906320535U / num != 0U)
					{
						goto IL_00;
					}
					num = (146551969U & num);
					if (240D6E99.6DFD59A4() != typeCode2)
					{
						num += 322135145U;
						if (2142122413U >= num)
						{
							goto Block_55;
						}
						continue;
					}
					else
					{
						if (1937537557U != num)
						{
							goto Block_56;
						}
						continue;
					}
					break;
				case 4:
				{
					097E1C51.0A706168 0A;
					if (!42AD3445)
					{
						0A = 240D6E99;
					}
					else
					{
						num = 1644393616U >> (int)num;
						0A = 240D6E99.58E29230();
						num += 1942505646U;
					}
					float num35 = 0A.E1E85554();
					num = 132738531U % num;
					float num36 = num35;
					num += 1956717045U;
					if (133986108U + num == 0U)
					{
						goto IL_C0;
					}
					097E1C51.0A706168 0A2;
					if (!42AD3445)
					{
						0A2 = 5F6A540D;
					}
					else
					{
						num *= 929698380U;
						num -= 1919881624U;
						0A2 = 5F6A540D.58E29230();
						num += 1078574416U;
					}
					float num37 = 0A2.E1E85554();
					num -= 521543869U;
					if (1706364239U > num)
					{
						goto Block_21;
					}
					continue;
				}
				case 5:
					goto IL_4D3;
				}
				break;
			}
			if ((921322779U ^ num) != 0U)
			{
				break;
			}
			goto IL_C0;
			Block_10:
			if (846552476U >> (int)num != 0U)
			{
				break;
			}
			ulong num38 = 240D6E99.083A097E();
			num <<= 0;
			num39 = num38;
			if ((75710481U & num) == 0U)
			{
				continue;
			}
			num <<= 16;
			ulong num40 = 5F6A540D.083A097E();
			num = (1682769387U ^ num);
			num41 = num40;
			num = (1033122307U | num);
			if (!379344A5)
			{
				num = (183057856U | num);
				if (num >= 426001666U)
				{
					goto Block_14;
				}
				goto IL_D2;
			}
			else
			{
				num ^= 1147678880U;
				if ((331381517U ^ num) != 0U)
				{
					goto Block_15;
				}
				continue;
			}
			IL_4D3:
			num = 1438712327U / num;
			097E1C51.0A706168 0A3;
			if (!42AD3445)
			{
				num = (840447253U & num);
				if (1497777281U < num)
				{
					goto IL_83;
				}
				0A3 = 240D6E99;
			}
			else
			{
				num /= 1399335845U;
				if (595072136U * num != 0U)
				{
					break;
				}
				num &= 1208642931U;
				0A3 = 240D6E99.58E29230();
				num += 0U;
			}
			double num42 = 0A3.D87DFA30();
			num = 1919297223U * num;
			num43 = num42;
			num = 446365774U << (int)num;
			if (322784642U - num == 0U)
			{
				continue;
			}
			097E1C51.0A706168 0A4;
			if (!42AD3445)
			{
				num |= 605162393U;
				if (1697477955U < num)
				{
					continue;
				}
				0A4 = 5F6A540D;
			}
			else
			{
				if (767568943U <= num)
				{
					continue;
				}
				0A4 = 5F6A540D.58E29230();
				num ^= 603982737U;
			}
			double num44 = 0A4.D87DFA30();
			num = 685928167U / num;
			num45 = num44;
			num = (702569079U ^ num);
			num |= 731401852U;
			if (!379344A5)
			{
				goto Block_30;
			}
			if (num % 2091020943U != 0U)
			{
				goto Block_31;
			}
			goto IL_EE;
			IL_A93:
			097E1C51.277D4587 277D4;
			277D3 = 277D4;
			if (966016728U >= num)
			{
				goto Block_57;
			}
			continue;
			Block_56:
			num = (459936260U | num);
			277D4 = (097E1C51.277D4587)240D6E99;
			num += 4293485157U;
			goto IL_A93;
			Block_55:
			277D4 = (097E1C51.277D4587)5F6A540D;
			goto IL_A93;
			IL_144:
			num *= 958214119U;
			int num46;
			339906C = num46;
			if (num != 1128492060U)
			{
				goto IL_1FE;
			}
			continue;
			IL_114:
			uint num47;
			uint num48;
			num46 = (int)(num47 - num48);
			goto IL_144;
			IL_EE:
			num = 736244257U - num;
			if (!379344A5)
			{
				num = 161808773U / num;
				if (num != 1084172641U)
				{
					goto IL_114;
				}
				break;
			}
			else if (1583554121U / num != 0U)
			{
				int num49 = (int)num47;
				num = 2092771012U - num;
				num46 = checked(num49 - (int)num48);
				num ^= 1482125949U;
				goto IL_144;
			}
			IL_D2:
			num *= 1386767352U;
			uint num50 = 5F6A540D.2CD32589();
			num = 125599194U % num;
			num48 = num50;
			goto IL_EE;
			IL_C0:
			num47 = 240D6E99.2CD32589();
			num >>= 6;
			goto IL_D2;
			IL_1FE:
			if (num <= 754936614U)
			{
				goto Block_8;
			}
			goto IL_114;
			IL_83:
			num = 1264061573U / num;
			if (num > 448545611U)
			{
				break;
			}
			num = 2000250483U + num;
			if (42AD3445)
			{
				num >>= 23;
				if (num != 469191727U)
				{
					goto IL_C0;
				}
				break;
			}
			else
			{
				num = 1272329908U / num;
				num = 1740843672U + num;
				int num51 = 240D6E99.B9179A8D();
				if (num != 957054530U)
				{
					num = 1700147151U / num;
					int num52 = 5F6A540D.B9179A8D();
					num = 1179610403U - num;
					int num55;
					if (!379344A5)
					{
						int num53 = num51;
						num = 689636520U + num;
						int num54 = num52;
						num += 1871518526U;
						num55 = num53 - num54;
					}
					else
					{
						int num56 = num51;
						int num57 = num52;
						num = 1920106470U >> (int)num;
						num55 = checked(num56 - num57);
						num += 3500752141U;
					}
					num |= 1335570503U;
					339906C = num55;
					num ^= 3758079311U;
					goto IL_1FE;
				}
				goto IL_30;
			}
		}
		do
		{
			IL_7E:
			num &= 916854701U;
		}
		while (num >= 1851003093U);
		throw new InvalidOperationException();
		Block_8:
		return new 097E1C51.14C26E2D(339906C);
		Block_14:
		long num58 = (long)num39;
		num = 1708599502U + num;
		long num59 = num58 - (long)num41;
		goto IL_2E5;
		Block_15:
		long num60 = (long)num39;
		num = 1157431856U / num;
		num59 = checked(num60 - (long)num41);
		num ^= 2782292153U;
		IL_2E5:
		num = 1074822016U >> (int)num;
		long num61 = num59;
		goto IL_3BD;
		Block_16:
		num = 678509600U / num;
		long num62 = num19;
		long num63 = num21;
		num = (1602382598U & num);
		long num64 = num62 - num63;
		goto IL_3A7;
		Block_17:
		long num65 = num19;
		num /= 1829241197U;
		long num66 = num21;
		num = 1604353916U * num;
		num64 = checked(num65 - num66);
		num += 2690613380U;
		IL_3A7:
		num = (668759389U ^ num);
		num61 = num64;
		num += 3626207939U;
		IL_3BD:
		num = 1016466525U % num;
		long 6DB81B = num61;
		num /= 935802229U;
		return new 097E1C51.31EF1BAE(6DB81B);
		Block_21:
		num >>= 19;
		float 52F83C3E;
		if (!379344A5)
		{
			float num36;
			float num67 = num36;
			num = 1952388641U / num;
			float num37;
			52F83C3E = num67 - num37;
		}
		else
		{
			num *= 473252254U;
			float num36;
			float num68 = num36;
			num |= 26289717U;
			float num37;
			52F83C3E = num68 - num37;
			num += 2289340215U;
		}
		num = 1701849819U - num;
		return new 097E1C51.7B032626(52F83C3E);
		Block_30:
		num = 1557358876U + num;
		double num69 = num43;
		num = 626929009U >> (int)num;
		double num70 = num45;
		num %= 747204618U;
		double 25B00FBF = num69 - num70;
		goto IL_613;
		Block_31:
		25B00FBF = num43 - num45;
		num += 3557269893U;
		IL_613:
		num = (684729093U ^ num);
		return new 097E1C51.4DA65608(25B00FBF);
		Block_44:
		int value = num11;
		num <<= 24;
		IntPtr intPtr = new IntPtr(value);
		num = 1012074496U / num;
		void* ptr = intPtr.ToPointer();
		097E1C51.277D4587 277D5 = 277D2;
		num = (1398101350U & num);
		Type type = 277D5.686C3617();
		num += 273312711U;
		object 526508FB = Pointer.Box(ptr, type);
		097E1C51.277D4587 277D6 = 277D2;
		num *= 640028337U;
		return new 097E1C51.277D4587(526508FB, 277D6.686C3617());
		Block_57:
		long value2 = num28;
		num = 10571983U * num;
		intPtr = new IntPtr(value2);
		num = 1678863599U % num;
		void* ptr2 = intPtr.ToPointer();
		Type type2 = 277D3.686C3617();
		num = 2139895035U - num;
		object 526508FB2 = Pointer.Box(ptr2, type2);
		097E1C51.277D4587 277D7 = 277D3;
		num = 1546021842U - num;
		Type 30684F7F = 277D7.686C3617();
		num = 1014383202U - num;
		return new 097E1C51.277D4587(526508FB2, 30684F7F);
	}

	// Token: 0x060001E2 RID: 482 RVA: 0x002F70B4 File Offset: 0x001774B4
	private 097E1C51.0A706168 79AD2645(097E1C51.0A706168 4A1F7FA0, 097E1C51.0A706168 152620E8, bool 0D517AF7, bool 3392768A)
	{
		uint num = 113591632U;
		if ((num ^ 394294153U) != 0U)
		{
			goto IL_14;
		}
		goto IL_94;
		TypeCode typeCode2;
		long num3;
		long num4;
		double num8;
		double num9;
		for (;;)
		{
			IL_48:
			TypeCode typeCode = typeCode2;
			num ^= 98122416U;
			uint num2 = num ^ 1683564260U;
			num = 618491744U >> (int)num;
			switch (typeCode - num2)
			{
			case 0:
				goto IL_99;
			case 1:
			case 3:
				goto IL_5D4;
			case 2:
				if (num == 2054978060U)
				{
					continue;
				}
				num *= 387324547U;
				if (3392768A)
				{
					num = 1774538398U << (int)num;
					if ((596927413U ^ num) != 0U)
					{
						goto Block_12;
					}
					continue;
				}
				else
				{
					num3 = 4A1F7FA0.8EC1A2BC();
					num <<= 2;
					num4 = 152620E8.8EC1A2BC();
					if (680554596U == num)
					{
						continue;
					}
					num >>= 0;
					if (!0D517AF7)
					{
						if (num > 65741995U)
						{
							goto Block_16;
						}
						continue;
					}
					else
					{
						if (2054698921U != num)
						{
							goto Block_17;
						}
						continue;
					}
				}
				break;
			case 4:
			{
				num = (479750078U & num);
				if (num * 1109731749U == 0U)
				{
					goto IL_14;
				}
				num = 1046032064U >> (int)num;
				097E1C51.0A706168 0A;
				if (!3392768A)
				{
					num |= 1519205741U;
					if (1355183080U == num)
					{
						goto IL_D4;
					}
					0A = 4A1F7FA0;
				}
				else
				{
					if (num >= 433219699U)
					{
						goto IL_99;
					}
					num &= 542253430U;
					0A = 4A1F7FA0.58E29230();
					num += 1519238957U;
				}
				float num5 = 0A.E1E85554();
				num = 727731495U % num;
				float num6 = num5;
				num = 618531694U / num;
				097E1C51.0A706168 0A2;
				if (!3392768A)
				{
					if (num % 875773974U != 0U)
					{
						goto IL_14;
					}
					0A2 = 152620E8;
				}
				else
				{
					0A2 = 152620E8.58E29230();
					num ^= 0U;
				}
				float num7 = 0A2.E1E85554();
				num -= 496393413U;
				if (num << 7 != 0U)
				{
					goto Block_24;
				}
				continue;
			}
			case 5:
			{
				if (num >> 1 == 0U)
				{
					goto IL_14;
				}
				097E1C51.0A706168 0A3;
				if (!3392768A)
				{
					num = 73163791U % num;
					if (961546778U * num == 0U)
					{
						goto IL_94;
					}
					0A3 = 4A1F7FA0;
				}
				else
				{
					num |= 1770341292U;
					0A3 = 4A1F7FA0.58E29230();
					num ^= 1770353507U;
				}
				num8 = 0A3.D87DFA30();
				if (num * 1166490177U == 0U)
				{
					goto IL_14;
				}
				097E1C51.0A706168 0A4;
				if (!3392768A)
				{
					if (1163874044U == num)
					{
						goto IL_99;
					}
					0A4 = 152620E8;
				}
				else
				{
					num %= 1260742571U;
					if (178874633U < num)
					{
						goto IL_99;
					}
					0A4 = 152620E8.58E29230();
					num ^= 0U;
				}
				num = 1141072677U >> (int)num;
				num9 = 0A4.D87DFA30();
				num >>= 1;
				if (0D517AF7)
				{
					goto IL_5A5;
				}
				if (968640594U > num)
				{
					goto Block_35;
				}
				continue;
			}
			}
			break;
		}
		num ^= 0U;
		goto IL_94;
		Block_12:
		num = 14043624U << (int)num;
		ulong num10 = 4A1F7FA0.083A097E();
		ulong num11 = 152620E8.083A097E();
		num = (342118916U | num);
		ulong num12 = num11;
		num <<= 22;
		num >>= 14;
		long num13;
		if (!0D517AF7)
		{
			num <<= 24;
			num13 = (long)(num10 * num12);
		}
		else
		{
			num = 150485459U * num;
			long num14 = (long)num10;
			num -= 545533309U;
			long num15 = (long)num12;
			num &= 73039630U;
			num13 = checked(num14 * num15);
			num ^= 67640834U;
		}
		long 6DB81B = num13;
		goto IL_372;
		Block_16:
		long num16 = num3;
		long num17 = num4;
		num = 585134765U * num;
		long num18 = num16 * num17;
		goto IL_367;
		Block_17:
		long num19 = num3;
		long num20 = num4;
		num %= 130511537U;
		num18 = checked(num19 * num20);
		num ^= 195670446U;
		IL_367:
		6DB81B = num18;
		num += 4118734924U;
		IL_372:
		return new 097E1C51.31EF1BAE(6DB81B);
		Block_24:
		num = (735590353U | num);
		double num23;
		if (!0D517AF7)
		{
			num = 1814889914U / num;
			float num6;
			double num21 = (double)num6;
			float num7;
			double num22 = (double)num7;
			num *= 1955820793U;
			num23 = num21 * num22;
		}
		else
		{
			num <<= 18;
			if (num < 1912546747U)
			{
				goto IL_14;
			}
			float num6;
			float num7;
			num23 = (double)(num6 * num7);
			num ^= 3756785664U;
		}
		double 25B00FBF = num23;
		num |= 992757439U;
		return new 097E1C51.4DA65608(25B00FBF);
		Block_35:
		double num24 = num8;
		num = 1782060354U >> (int)num;
		double num25 = num9;
		num >>= 5;
		double 25B00FBF2 = num24 * num25;
		goto IL_5CE;
		IL_5A5:
		double num26 = num8;
		num >>= 16;
		double num27 = num9;
		num = 338364630U << (int)num;
		25B00FBF2 = num26 * num27;
		num ^= 1353458520U;
		IL_5CE:
		return new 097E1C51.4DA65608(25B00FBF2);
		uint num28;
		do
		{
			IL_D4:
			num = 1409889865U * num;
			num28 = 152620E8.2CD32589();
			num = 630457984U >> (int)num;
			if (945761711U == num)
			{
				goto IL_14;
			}
			num %= 1457811407U;
			if (0D517AF7)
			{
				goto IL_131;
			}
			num |= 797007601U;
		}
		while (341003582U % num == 0U);
		uint num30;
		int num29 = (int)(num30 * num28);
		goto IL_155;
		IL_131:
		if (294209062U < num)
		{
			goto IL_14;
		}
		int num31 = (int)num30;
		int num32 = (int)num28;
		num %= 1736518877U;
		num29 = checked(num31 * num32);
		num += 797007601U;
		IL_155:
		num |= 416681092U;
		int num33 = num29;
		goto IL_218;
		IL_14:
		num <<= 0;
		TypeCode typeCode3 = this.61563953(4A1F7FA0, 152620E8);
		num |= 2086754586U;
		typeCode2 = typeCode3;
		num = 1635785821U % num;
		if (num > 898973788U)
		{
			goto IL_48;
		}
		goto IL_99;
		IL_94:
		goto IL_5D4;
		IL_99:
		if (938297711U == num)
		{
			goto IL_48;
		}
		num &= 1596525544U;
		if (3392768A)
		{
			uint num34 = 4A1F7FA0.2CD32589();
			num = (313989126U | num);
			num30 = num34;
			if (131079570U <= num)
			{
				goto IL_D4;
			}
			goto IL_14;
		}
		else
		{
			num = 94008668U % num;
			num >>= 18;
			int num35 = 4A1F7FA0.B9179A8D();
			num = 18814803U - num;
			int num36 = num35;
			int num37 = 152620E8.B9179A8D();
			num -= 644897844U;
			int num38 = num37;
			int num41;
			if (!0D517AF7)
			{
				num *= 1651315780U;
				int num39 = num36;
				num <<= 8;
				int num40 = num38;
				num = 1104218588U / num;
				num41 = num39 * num40;
			}
			else
			{
				num = 2062429131U % num;
				if (num == 944791056U)
				{
					goto IL_14;
				}
				int num42 = num36;
				num %= 2067421850U;
				int num43 = num38;
				num = (369042885U ^ num);
				num41 = checked(num42 * num43);
				num ^= 1863388687U;
			}
			num = 1543535317U % num;
			num33 = num41;
			num ^= 1071079157U;
		}
		IL_218:
		int 339906C = num33;
		num &= 1338901228U;
		return new 097E1C51.14C26E2D(339906C);
		IL_5D4:
		num |= 467222681U;
		if (1268586128U >= num)
		{
			throw new InvalidOperationException();
		}
		goto IL_48;
	}

	// Token: 0x060001E3 RID: 483 RVA: 0x002F76B0 File Offset: 0x00177AB0
	private 097E1C51.0A706168 4E62064B(097E1C51.0A706168 52FC4A87, 097E1C51.0A706168 06744F72, bool 10D77F75)
	{
		uint num = 1748959677U;
		if (2063408877 << (int)num == 0)
		{
			goto IL_B6;
		}
		TypeCode typeCode2;
		do
		{
			IL_1A:
			num = (1554527834U ^ num);
			num = 1960258210U % num;
			TypeCode typeCode = this.61563953(52FC4A87, 06744F72);
			num = 1086417761U * num;
			typeCode2 = typeCode;
		}
		while ((num ^ 1217543519U) == 0U);
		for (;;)
		{
			IL_4F:
			uint num2 = (uint)typeCode2;
			num = (970025934U | num);
			uint num3 = num2 - (num ^ 4225331159U);
			num = 1854353843U % num;
			switch (num3)
			{
			case 0:
				goto IL_9A;
			case 1:
			case 3:
				goto IL_397;
			case 2:
				goto IL_15C;
			case 4:
				num = (1560226537U | num);
				if (!10D77F75)
				{
					goto Block_10;
				}
				num = 1248159284U * num;
				if (num - 831403316U != 0U)
				{
					goto Block_12;
				}
				continue;
			case 5:
				if (140073430U >= num)
				{
					goto IL_95;
				}
				num -= 981928858U;
				if (10D77F75)
				{
					goto IL_312;
				}
				if ((num ^ 1437540881U) != 0U)
				{
					goto Block_16;
				}
				continue;
			}
			break;
		}
		num ^= 0U;
		IL_95:
		goto IL_397;
		IL_9A:
		if (320107062U >= num)
		{
			goto IL_B6;
		}
		if (10D77F75)
		{
			num += 1294089743U;
			goto IL_B6;
		}
		int 339906C;
		if ((num & 412098053U) != 0U)
		{
			int num4 = 52FC4A87.B9179A8D();
			int num5 = 06744F72.B9179A8D();
			num = 1302870153U % num;
			int num6 = num4 / num5;
			num = 1390301230U / num;
			339906C = num6;
			num ^= 1961573412U;
			goto IL_13E;
		}
		goto IL_95;
		IL_15C:
		num -= 473249209U;
		long 6DB81B;
		if (10D77F75)
		{
			long num7 = (long)52FC4A87.083A097E();
			num = 852844525U - num;
			num = 330441935U >> (int)num;
			ulong num8 = 06744F72.083A097E();
			num |= 242574916U;
			ulong num9 = num8;
			num = (1798331032U & num);
			6DB81B = num7 / (long)num9;
			if (num == 461730536U)
			{
				goto IL_1A;
			}
		}
		else
		{
			long num10 = 52FC4A87.8EC1A2BC();
			num |= 1037057599U;
			num += 1780627373U;
			long num11 = 06744F72.8EC1A2BC();
			long num12 = num10 / num11;
			num /= 853608920U;
			6DB81B = num12;
			num ^= 170934804U;
		}
		num = 608991305U + num;
		if (464652955U <= num)
		{
			return new 097E1C51.31EF1BAE(6DB81B);
		}
		goto IL_95;
		Block_10:
		num %= 1828668525U;
		097E1C51.0A706168 0A;
		if (num / 682254101U == 0U)
		{
			0A = 52FC4A87;
			goto IL_268;
		}
		goto IL_1A;
		Block_12:
		0A = 52FC4A87.58E29230();
		num += 1547355794U;
		IL_268:
		num = 1575832542U << (int)num;
		float num13 = 0A.E1E85554();
		num = (1159987377U ^ num);
		097E1C51.0A706168 0A2;
		if (!10D77F75)
		{
			0A2 = 06744F72;
		}
		else
		{
			num = 87374822U * num;
			0A2 = 06744F72.58E29230();
			num ^= 1468066487U;
		}
		num = 7079800U * num;
		float num14 = 0A2.E1E85554();
		num = 1325732532U % num;
		float num15 = num14;
		num = 2085966525U - num;
		float num16 = num15;
		num <<= 31;
		return new 097E1C51.7B032626(num13 / num16);
		Block_16:
		097E1C51.0A706168 0A3 = 52FC4A87;
		goto IL_32C;
		IL_312:
		num /= 1442276758U;
		0A3 = 52FC4A87.58E29230();
		num ^= 872424985U;
		IL_32C:
		num = 2057139720U % num;
		double num17 = 0A3.D87DFA30();
		num = (1741961535U & num);
		097E1C51.0A706168 0A4;
		if (!10D77F75)
		{
			0A4 = 06744F72;
		}
		else
		{
			0A4 = 06744F72.58E29230();
			num ^= 0U;
		}
		double num18 = 0A4.D87DFA30();
		num = 371881510U - num;
		double num19 = num18;
		num = 1840582997U - num;
		double num20 = num19;
		num %= 925312611U;
		double 25B00FBF = num17 / num20;
		num |= 1118926138U;
		return new 097E1C51.4DA65608(25B00FBF);
		IL_397:
		num = 134685866U + num;
		throw new InvalidOperationException();
		IL_B6:
		num |= 1043166703U;
		int num21 = (int)52FC4A87.2CD32589();
		uint num22 = 06744F72.2CD32589();
		num <<= 0;
		uint num23 = num22;
		int num24 = (int)num23;
		num %= 685710655U;
		int num25 = num21 / num24;
		num = (1759131350U ^ num);
		339906C = num25;
		if (1258634280U == num)
		{
			goto IL_4F;
		}
		IL_13E:
		num += 1739394399U;
		if ((num & 929853914U) != 0U)
		{
			return new 097E1C51.14C26E2D(339906C);
		}
		goto IL_1A;
	}

	// Token: 0x060001E4 RID: 484 RVA: 0x002F7A64 File Offset: 0x00177E64
	private 097E1C51.0A706168 600357F0(097E1C51.0A706168 43670FA1, 097E1C51.0A706168 4C76345A, bool 58B27210)
	{
		uint num;
		TypeCode typeCode;
		do
		{
			IL_00:
			num = 975526453U;
			typeCode = 43670FA1.6DFD59A4();
			while (typeCode == (TypeCode)(num - 975526444U))
			{
				num = 1322463154U * num;
				if (num << 4 != 0U)
				{
					if (!58B27210)
					{
						goto IL_B7;
					}
					num /= 821494314U;
					if (436043365U != num)
					{
						goto Block_4;
					}
					goto IL_00;
				}
			}
			num &= 592346736U;
		}
		while (354111245U == num);
		for (;;)
		{
			TypeCode typeCode2 = typeCode;
			uint num2 = num ^ 570710587U;
			num = 1540509771U + num;
			if (typeCode2 != num2)
			{
				break;
			}
			num = 1035228534U << (int)num;
			if (58B27210)
			{
				goto Block_5;
			}
			num %= 1719414147U;
			if (num * 882266451U != 0U)
			{
				goto Block_6;
			}
		}
		num *= 337980412U;
		throw new InvalidOperationException();
		Block_5:
		long num3 = (long)43670FA1.083A097E();
		num = 1354117984U * num;
		num = 6899741U - num;
		ulong num4 = 4C76345A.083A097E();
		return new 097E1C51.31EF1BAE(num3 % (long)num4);
		Block_6:
		num ^= 669849591U;
		long num5 = 43670FA1.8EC1A2BC();
		num = 885860106U % num;
		long num6 = 4C76345A.8EC1A2BC();
		num = (354095786U & num);
		long num7 = num6;
		num = (407336647U | num);
		long num8 = num7;
		num &= 870191925U;
		return new 097E1C51.31EF1BAE(num5 % num8);
		Block_4:
		num += 531242359U;
		int num9 = (int)43670FA1.2CD32589();
		num <<= 2;
		uint num10 = 4C76345A.2CD32589();
		num <<= 17;
		uint num11 = num10;
		int num12 = (int)num11;
		num = 984942401U >> (int)num;
		int 339906C = num9 % num12;
		num = (686914520U | num);
		return new 097E1C51.14C26E2D(339906C);
		IL_B7:
		int num13 = 43670FA1.B9179A8D();
		int num14 = 4C76345A.B9179A8D();
		num >>= 31;
		int num15 = num14;
		num %= 1507659291U;
		return new 097E1C51.14C26E2D(num13 % num15);
	}

	// Token: 0x060001E5 RID: 485 RVA: 0x002F7BE8 File Offset: 0x00177FE8
	private 097E1C51.0A706168 049A163C(097E1C51.0A706168 037924A4, 097E1C51.0A706168 371D7B38)
	{
		uint num = 203128064U;
		if (num << 30 == 0U)
		{
			goto IL_12;
		}
		TypeCode typeCode2;
		for (;;)
		{
			IL_31:
			TypeCode typeCode = typeCode2;
			num ^= 413353867U;
			uint num2 = num - 995832411U;
			num &= 21514089U;
			switch (typeCode - num2)
			{
			case 0:
				goto IL_7F;
			case 1:
			case 3:
				break;
			case 2:
				goto IL_BA;
			case 4:
				goto IL_100;
			case 5:
				goto IL_161;
			default:
				if (num >= 137244290U)
				{
					continue;
				}
				num += 0U;
				break;
			}
			num += 1386106766U;
			if (num % 1638421574U != 0U)
			{
				goto Block_9;
			}
		}
		IL_7F:
		int num3 = 037924A4.B9179A8D();
		num >>= 31;
		num /= 2146382586U;
		int num4 = 371D7B38.B9179A8D();
		num = 743063419U << (int)num;
		int num5 = num4;
		int num6 = num5;
		num /= 1101531206U;
		return new 097E1C51.14C26E2D(num3 ^ num6);
		IL_BA:
		if (937720413U > num)
		{
			long num7 = 037924A4.8EC1A2BC();
			long num8 = 371D7B38.8EC1A2BC();
			num <<= 30;
			long num9 = num8;
			num = 1240077289U + num;
			long num10 = num9;
			num = 1264719997U << (int)num;
			long 6DB81B = num7 ^ num10;
			num = 1453013519U - num;
			return new 097E1C51.31EF1BAE(6DB81B);
		}
		goto IL_12;
		IL_100:
		num *= 34477904U;
		int size = IntPtr.Size;
		uint num11 = num - 3220659708U;
		num ^= 1844332449U;
		float 52F83C3E;
		if (size != num11)
		{
			num = (114969673U ^ num);
			52F83C3E = 0f;
		}
		else
		{
			num = 1682310122U * num;
			if (657531276U > num)
			{
				goto IL_12;
			}
			52F83C3E = float.NaN;
			num ^= 3901162946U;
		}
		num = (1261188925U & num);
		return new 097E1C51.7B032626(52F83C3E);
		IL_161:
		if (num != 1507267040U)
		{
			int size2 = IntPtr.Size;
			uint num12 = num - 21496412U;
			num /= 1250453171U;
			double 25B00FBF;
			if (size2 != num12)
			{
				num = (765884114U | num);
				25B00FBF = 0.0;
			}
			else
			{
				if (num == 1468810557U)
				{
					goto IL_12;
				}
				25B00FBF = double.NaN;
				num ^= 765884114U;
			}
			return new 097E1C51.4DA65608(25B00FBF);
		}
		goto IL_12;
		Block_9:
		throw new InvalidOperationException();
		IL_12:
		num = (1627147665U & num);
		TypeCode typeCode3 = this.61563953(037924A4, 371D7B38);
		num = 603485679U << (int)num;
		typeCode2 = typeCode3;
		goto IL_31;
	}

	// Token: 0x060001E6 RID: 486 RVA: 0x002F7DCC File Offset: 0x001781CC
	private 097E1C51.0A706168 5BA918D5(097E1C51.0A706168 64045339, 097E1C51.0A706168 6E0374D7)
	{
		uint num;
		for (;;)
		{
			TypeCode typeCode = this.61563953(64045339, 6E0374D7);
			num = 1133905402U;
			TypeCode typeCode2 = typeCode;
			for (;;)
			{
				switch (typeCode2 - (TypeCode)(num ^ 1133905395U))
				{
				case 0:
					goto IL_42;
				case 1:
				case 3:
					goto IL_1A8;
				case 2:
					goto IL_91;
				case 4:
					goto IL_C1;
				case 5:
				{
					if (num > 1707553981U)
					{
						continue;
					}
					int size = IntPtr.Size;
					uint num2 = num + 3161061898U;
					num += 1271079152U;
					if (size == num2)
					{
						goto IL_170;
					}
					num = 438840626U << (int)num;
					if (num > 20984603U)
					{
						goto Block_8;
					}
					continue;
				}
				}
				goto Block_0;
			}
			IL_42:
			num = 522213858U % num;
			if (num + 928392764U != 0U)
			{
				goto Block_1;
			}
			continue;
			IL_91:
			if (1591821755U > num)
			{
				goto Block_2;
			}
			continue;
			IL_C1:
			num = 1954219251U % num;
			if (1273758036U >> (int)num == 0U)
			{
				continue;
			}
			uint size2 = (uint)IntPtr.Size;
			num %= 1842873583U;
			if (size2 != (num ^ 820313853U))
			{
				goto Block_4;
			}
			if ((num ^ 1178479988U) != 0U)
			{
				goto Block_5;
			}
			goto IL_42;
			IL_170:
			num = (718473976U ^ num);
			if (326631571U <= num)
			{
				goto Block_9;
			}
		}
		Block_0:
		num ^= 0U;
		IL_3D:
		goto IL_1A8;
		Block_1:
		int num3 = 64045339.B9179A8D();
		num = 2060592235U << (int)num;
		int num4 = 6E0374D7.B9179A8D();
		num += 431955555U;
		int num5 = num4;
		int num6 = num5;
		num = 950425305U * num;
		int 339906C = num3 | num6;
		num = 1497389762U % num;
		return new 097E1C51.14C26E2D(339906C);
		Block_2:
		long num7 = 64045339.8EC1A2BC();
		num = 1033896967U - num;
		long num8 = 6E0374D7.8EC1A2BC();
		num -= 1462464022U;
		long num9 = num8;
		return new 097E1C51.31EF1BAE(num7 | num9);
		Block_4:
		num -= 123545984U;
		float 52F83C3E = 0f;
		goto IL_11F;
		Block_5:
		52F83C3E = float.NaN;
		num ^= 425929600U;
		IL_11F:
		return new 097E1C51.7B032626(52F83C3E);
		Block_8:
		double 25B00FBF = 0.0;
		goto IL_194;
		Block_9:
		25B00FBF = double.NaN;
		num += 4213873646U;
		IL_194:
		num = 1373051153U << (int)num;
		return new 097E1C51.4DA65608(25B00FBF);
		IL_1A8:
		num = (1240930558U & num);
		if (1734830027U >= num)
		{
			throw new InvalidOperationException();
		}
		goto IL_3D;
	}

	// Token: 0x060001E7 RID: 487 RVA: 0x002F7F9C File Offset: 0x0017839C
	private 097E1C51.0A706168 03A93C18(097E1C51.0A706168 696F045D, 097E1C51.0A706168 4DA956F2)
	{
		uint num = 2026667932U;
		for (;;)
		{
			num -= 1981299468U;
			TypeCode typeCode = this.61563953(696F045D, 4DA956F2);
			num |= 1220502319U;
			TypeCode typeCode2 = typeCode;
			for (;;)
			{
				object obj = typeCode2;
				num |= 1083075384U;
				object obj2 = num ^ 1254059958U;
				num = (208608804U ^ num);
				object obj3 = obj - obj2;
				num = (1220872006U | num);
				switch (obj3)
				{
				case 0:
					num = 705779415U % num;
					if (num % 969765077U != 0U)
					{
						goto Block_2;
					}
					continue;
				case 1:
				case 3:
					IL_1B7:
					num = 1784289971U - num;
					if (num << 27 != 0U)
					{
						goto Block_9;
					}
					continue;
				case 2:
					goto IL_AB;
				case 4:
					num = 376514489U / num;
					if (1049576720U >> (int)num != 0U)
					{
						goto Block_4;
					}
					break;
				case 5:
					goto IL_159;
				default:
					num ^= 0U;
					break;
				}
				goto IL_1B7;
			}
			IL_AB:
			if (59313453U <= num)
			{
				goto Block_3;
			}
			continue;
			Block_4:
			int size = IntPtr.Size;
			uint num2 = num ^ 4U;
			num &= 650406384U;
			if (size != num2)
			{
				num >>= 28;
				if (num <= 2045513228U)
				{
					goto Block_6;
				}
			}
			else if (268112897U > num)
			{
				goto Block_7;
			}
		}
		Block_2:
		num = 1135242953U * num;
		int num3 = 696F045D.B9179A8D();
		int num4 = 4DA956F2.B9179A8D();
		num = (113338156U | num);
		int num5 = num4;
		int num6 = num5;
		num %= 672822536U;
		return new 097E1C51.14C26E2D(num3 & num6);
		Block_3:
		long num7 = 696F045D.8EC1A2BC();
		num = (606082288U | num);
		num = (1532518446U ^ num);
		long num8 = 4DA956F2.8EC1A2BC();
		long 6DB81B = num7 & num8;
		num = (669984074U & num);
		return new 097E1C51.31EF1BAE(6DB81B);
		Block_6:
		float 52F83C3E = 0f;
		goto IL_14B;
		Block_7:
		52F83C3E = float.NaN;
		num += 0U;
		IL_14B:
		num &= 6048673U;
		return new 097E1C51.7B032626(52F83C3E);
		IL_159:
		num ^= 151345266U;
		int size2 = IntPtr.Size;
		num = 2094623348U / num;
		uint num9 = num ^ 5U;
		num *= 1144591813U;
		double 25B00FBF;
		if (size2 != num9)
		{
			num = (1638866761U & num);
			25B00FBF = 0.0;
		}
		else
		{
			num ^= 1037906300U;
			25B00FBF = double.NaN;
			num += 3326406792U;
		}
		return new 097E1C51.4DA65608(25B00FBF);
		Block_9:
		throw new InvalidOperationException();
	}

	// Token: 0x060001E8 RID: 488 RVA: 0x002F817C File Offset: 0x0017857C
	private int 483C62ED(097E1C51.0A706168 313E0ED5, 097E1C51.0A706168 03CE4216, bool 1C53484C, int 66836EE4)
	{
		uint num;
		for (;;)
		{
			IL_00:
			num = 681206713U;
			int num2 = 66836EE4;
			if (1603758108U > num)
			{
				goto IL_16;
			}
			TypeCode typeCode3;
			for (;;)
			{
				IL_28:
				TypeCode typeCode = 03CE4216.6DFD59A4();
				num = 2006910275U >> (int)num;
				for (;;)
				{
					TypeCode typeCode2 = typeCode3;
					num = 143092898U << (int)num;
					uint num3 = num ^ 3391094785U;
					num /= 1726243707U;
					if (typeCode2 == num3)
					{
						goto IL_AB;
					}
					num = 195041159U + num;
					if (643654246U + num == 0U)
					{
						goto IL_00;
					}
					TypeCode typeCode4 = typeCode;
					num = 434708150U * num;
					uint num4 = num ^ 3109726897U;
					num = (502954209U | num);
					if (typeCode4 == num4)
					{
						num += 1107625232U;
						goto IL_AB;
					}
					num = 49700074U / num;
					if (155990183U < num)
					{
						goto IL_16;
					}
					TypeCode typeCode5 = typeCode3;
					num = 1755261912U + num;
					uint num5 = num ^ 1755261910U;
					num -= 720839582U;
					if (typeCode5 != num5)
					{
						num = (98305969U | num);
						if (num > 1720064806U)
						{
							goto IL_16;
						}
						TypeCode typeCode6 = typeCode;
						uint num6 = num - 1039929261U;
						num -= 2138726333U;
						if (typeCode6 == num6)
						{
							num ^= 2200545220U;
						}
						else
						{
							num <<= 4;
							if (1460697142U == num)
							{
								goto IL_16;
							}
							TypeCode typeCode7 = typeCode3;
							num >>= 1;
							uint num7 = num - 1947041763U;
							num = (1725696669U | num);
							if (typeCode7 != num7)
							{
								num = (2129557045U | num);
								if (num < 1065964698U)
								{
									goto IL_00;
								}
								uint num8 = (uint)typeCode;
								num &= 469788722U;
								if (num8 == (num ^ 469788733U))
								{
									num += 1524438989U;
								}
								else
								{
									num >>= 1;
									if (num > 1143277134U)
									{
										break;
									}
									if (typeCode3 != (TypeCode)(num ^ 234894355U))
									{
										num -= 361000568U;
										if (typeCode == (TypeCode)(num ^ 4168861099U))
										{
											num += 361000568U;
										}
										else
										{
											num %= 1383234195U;
											if (1226658851U >= num)
											{
												uint num9 = (uint)typeCode3;
												num += 717554010U;
												if (num9 != num - 736712504U)
												{
													num &= 1992164608U;
													if (1372797337U / num == 0U)
													{
														goto IL_00;
													}
													TypeCode typeCode8 = typeCode;
													uint num10 = num - 581436663U;
													num = 463172763U * num;
													num ^= 620693248U;
													if (typeCode8 != num10)
													{
														goto IL_557;
													}
													num += 736712513U;
												}
												num &= 955143589U;
												num = (1615342201U & num);
												int num13;
												if (!1C53484C)
												{
													num <<= 3;
													int num11 = 313E0ED5.B9179A8D();
													num %= 1179661476U;
													int num12 = num11;
													num |= 453647151U;
													num13 = num12.CompareTo(03CE4216.B9179A8D());
												}
												else
												{
													num = (820079207U | num);
													num = 1785220816U >> (int)num;
													uint num14 = 313E0ED5.2CD32589();
													if (1466854697U <= num)
													{
														goto IL_16;
													}
													num <<= 9;
													num13 = num14.CompareTo(03CE4216.2CD32589());
													num ^= 3001753903U;
												}
												num2 = num13;
												num ^= 457841455U;
												goto IL_557;
											}
											goto IL_00;
										}
									}
									num = 1026116257U * num;
									if (1984319193U != num)
									{
										int num16;
										if (!1C53484C)
										{
											if (694761642U % num == 0U)
											{
												break;
											}
											long num15 = 313E0ED5.8EC1A2BC();
											if (num * 1612994465U == 0U)
											{
												goto IL_00;
											}
											num ^= 1429024582U;
											num /= 3671092U;
											num16 = num15.CompareTo(03CE4216.8EC1A2BC());
										}
										else
										{
											num = 495977538U >> (int)num;
											num = 15875016U + num;
											ulong num17 = 313E0ED5.083A097E();
											num += 577373331U;
											ulong num18 = num17;
											num /= 409612743U;
											num /= 273310175U;
											ulong value = 03CE4216.083A097E();
											num = 206326420U >> (int)num;
											num16 = num18.CompareTo(value);
											num ^= 206326237U;
										}
										num ^= 192679648U;
										num2 = num16;
										num ^= 192679337U;
										goto IL_557;
									}
									goto IL_00;
								}
							}
							if (num * 173738928U == 0U)
							{
								goto IL_00;
							}
							num = 1883193897U + num;
							float num19 = 313E0ED5.E1E85554();
							num &= 206596822U;
							float num20 = num19;
							if ((1041180432U ^ num) != 0U)
							{
								num = 1078402079U / num;
								float value2 = 03CE4216.E1E85554();
								num %= 1335233686U;
								num2 = num20.CompareTo(value2);
								num += 4294967281U;
								goto IL_557;
							}
							goto IL_00;
						}
					}
					if ((1702763958U ^ num) == 0U)
					{
						goto IL_00;
					}
					num = 361831342U / num;
					int num21 = 313E0ED5.D87DFA30().CompareTo(03CE4216.D87DFA30());
					num >>= 27;
					num2 = num21;
					if (268467048U * num != 0U)
					{
						break;
					}
					num ^= 0U;
					IL_557:
					num %= 1098662873U;
					if (num2 < (int)(num + 0U))
					{
						num2 = (int)(num + uint.MaxValue);
					}
					else
					{
						if (798428928U <= num)
						{
							break;
						}
						int num22 = num2;
						num = 1461403073U * num;
						uint num23 = num - 0U;
						num = (83833729U ^ num);
						num ^= 83833729U;
						if (num22 > num23)
						{
							num = (136149199U & num);
							num2 = (int)(num ^ 1U);
							num += 0U;
						}
					}
					if (1686860097U >> (int)num != 0U)
					{
						return num2;
					}
					continue;
					IL_AB:
					num = 2142571348U % num;
					object obj = 313E0ED5.04F0F15D();
					num *= 1591177595U;
					object obj2 = obj;
					num /= 696872908U;
					object obj3 = 03CE4216.04F0F15D();
					num = (1220237840U ^ num);
					if (num <= 31329293U)
					{
						goto IL_16;
					}
					object obj4 = obj2;
					num = 2134076612U >> (int)num;
					if (obj4 == obj3)
					{
						goto Block_4;
					}
					num <<= 24;
					bool flag = obj3 != null;
					num = 343869616U >> (int)num;
					if (!flag)
					{
						goto Block_5;
					}
					num = 1548036336U / num;
					bool flag2 = obj2 != null;
					num >>= 5;
					if (!flag2)
					{
						goto Block_6;
					}
					goto IL_557;
				}
			}
			IL_16:
			TypeCode typeCode9 = 313E0ED5.6DFD59A4();
			num ^= 1935343869U;
			typeCode3 = typeCode9;
			goto IL_28;
		}
		Block_4:
		return (int)(num + 4294934733U);
		Block_5:
		num /= 1938442053U;
		return (int)(num ^ 1U);
		Block_6:
		num /= 1302558293U;
		return (int)(num ^ uint.MaxValue);
	}

	// Token: 0x060001E9 RID: 489 RVA: 0x002F8770 File Offset: 0x00178B70
	private 097E1C51.0A706168 77346840(097E1C51.0A706168 106210F7)
	{
		uint num = 511990293U;
		if (850396670U / num == 0U)
		{
			goto IL_C4;
		}
		IL_12:
		num = 107163779U - num;
		TypeCode typeCode = 106210F7.6DFD59A4();
		num -= 1824481372U;
		TypeCode typeCode2 = typeCode;
		IL_29:
		TypeCode typeCode3 = typeCode2;
		uint num2 = num - 2065659401U;
		num *= 1070794464U;
		if (typeCode3 != num2)
		{
			num = 1837436109U >> (int)num;
			if (num == 1031238460U)
			{
				goto IL_12;
			}
			do
			{
				uint num3 = (uint)typeCode2;
				num = 1613956866U / num;
				if (num3 == num + 11U)
				{
					goto IL_AB;
				}
			}
			while (num >= 301039608U);
			goto IL_C4;
			IL_AB:
			if ((num ^ 1116036283U) != 0U)
			{
				return new 097E1C51.31EF1BAE(~106210F7.8EC1A2BC());
			}
		}
		else
		{
			num = 252264207U << (int)num;
			if (1655313034U % num != 0U)
			{
				int 339906C = ~106210F7.B9179A8D();
				num = 1361973487U + num;
				return new 097E1C51.14C26E2D(339906C);
			}
			goto IL_12;
		}
		IL_C4:
		if (num - 420676864U != 0U)
		{
			throw new InvalidOperationException();
		}
		goto IL_29;
	}

	// Token: 0x060001EA RID: 490 RVA: 0x002F8854 File Offset: 0x00178C54
	private 097E1C51.0A706168 3AA4054C(097E1C51.0A706168 613D36CD)
	{
		uint num;
		do
		{
			TypeCode typeCode = 613D36CD.6DFD59A4();
			num = 853683074U;
			TypeCode typeCode2 = typeCode;
			num >>= 4;
			for (;;)
			{
				TypeCode typeCode3 = typeCode2;
				uint num2 = num ^ 53355185U;
				num ^= 1533678435U;
				switch (typeCode3 - num2)
				{
				case 0:
					goto IL_5C;
				case 1:
				case 3:
					break;
				case 2:
					goto IL_84;
				case 4:
					goto IL_A9;
				case 5:
					goto IL_BE;
				default:
					if (num * 26882819U == 0U)
					{
						continue;
					}
					num ^= 0U;
					break;
				}
				if (num - 1737688303U != 0U)
				{
					goto Block_4;
				}
			}
			IL_5C:
			num *= 1073442336U;
		}
		while (7148133U > num);
		num %= 1951624116U;
		return new 097E1C51.14C26E2D(-613D36CD.B9179A8D());
		IL_84:
		num = 276654185U - num;
		long num3 = 613D36CD.8EC1A2BC();
		num -= 2088004870U;
		long 6DB81B = -num3;
		num %= 1486227598U;
		return new 097E1C51.31EF1BAE(6DB81B);
		IL_A9:
		num -= 1320579580U;
		return new 097E1C51.7B032626(-613D36CD.E1E85554());
		IL_BE:
		if ((num ^ 463886991U) != 0U)
		{
			double num4 = 613D36CD.D87DFA30();
			num = 614876250U - num;
			return new 097E1C51.4DA65608(-num4);
		}
		goto IL_84;
		Block_4:
		throw new InvalidOperationException();
	}

	// Token: 0x060001EB RID: 491 RVA: 0x002F8954 File Offset: 0x00178D54
	private 097E1C51.0A706168 088D1B0E(097E1C51.0A706168 6DA64275, 097E1C51.0A706168 38291A19, bool 0CD205F9)
	{
		uint num = 28903223U;
		for (;;)
		{
			IL_07:
			num = (491336570U | num);
			TypeCode typeCode = 6DA64275.6DFD59A4();
			num = 1059207889U << (int)num;
			TypeCode typeCode2 = typeCode;
			for (;;)
			{
				TypeCode typeCode3 = typeCode2;
				uint num2 = num + 2147483657U;
				num /= 1444361330U;
				if (typeCode3 != num2)
				{
					num = 1844128987U % num;
					for (;;)
					{
						TypeCode typeCode4 = typeCode2;
						uint num3 = num - 4294967285U;
						num = (1789988374U & num);
						if (typeCode4 != num3)
						{
							break;
						}
						if (num != 1905480393U)
						{
							goto Block_8;
						}
					}
					if (num != 1005001500U)
					{
						break;
					}
				}
				else
				{
					num ^= 1567182273U;
					if (num * 797930620U == 0U)
					{
						goto IL_07;
					}
					num = 235487854U / num;
					if (0CD205F9)
					{
						goto Block_5;
					}
					if (840724104U * num == 0U)
					{
						goto Block_7;
					}
				}
			}
			if (num <= 378231574U)
			{
				goto Block_11;
			}
			continue;
			Block_5:
			if (num % 1852536140U == 0U)
			{
				break;
			}
			continue;
			Block_8:
			if (0CD205F9)
			{
				goto Block_9;
			}
			if (num - 223682329U != 0U)
			{
				goto Block_10;
			}
		}
		uint num4 = 6DA64275.2CD32589();
		int num5 = 38291A19.B9179A8D();
		num = (731974978U ^ num);
		int num6 = num5;
		num = (2016031607U & num);
		int 339906C = num4 >> (num6 & (int)(num + 3621781213U));
		num = 538996204U * num;
		return new 097E1C51.14C26E2D(339906C);
		Block_7:
		num ^= 855076403U;
		int num7 = 6DA64275.B9179A8D();
		int num8 = 38291A19.B9179A8D();
		int 339906C2 = num7 >> (num8 & (int)(num ^ 855076396U));
		num = 932721182U / num;
		return new 097E1C51.14C26E2D(339906C2);
		Block_9:
		num >>= 26;
		ulong num9 = 6DA64275.083A097E();
		int num10 = 38291A19.B9179A8D();
		num /= 1391987936U;
		long 6DB81B = num9 >> (num10 & (int)(num - 4294967233U));
		num = (595601743U | num);
		return new 097E1C51.31EF1BAE(6DB81B);
		Block_10:
		long num11 = 6DA64275.8EC1A2BC();
		int num12 = 38291A19.B9179A8D();
		num >>= 16;
		int num13 = num12;
		num = 1563041424U << (int)num;
		return new 097E1C51.31EF1BAE(num11 >> (num13 & (int)(num ^ 1563041455U)));
		Block_11:
		throw new InvalidOperationException();
	}

	// Token: 0x060001EC RID: 492 RVA: 0x002F8B2C File Offset: 0x00178F2C
	private 097E1C51.0A706168 7CEC00A6(097E1C51.0A706168 15BF672E, 097E1C51.0A706168 594B55AA)
	{
		uint num;
		for (;;)
		{
			num = 1798911067U;
			TypeCode typeCode = 15BF672E.6DFD59A4();
			if (515203276U >> (int)num != 0U)
			{
				TypeCode typeCode2 = typeCode;
				num >>= 8;
				uint num2 = num ^ 7027005U;
				num += 1846939843U;
				if (typeCode2 == num2)
				{
					break;
				}
				num &= 321276788U;
				if (typeCode == (TypeCode)(num ^ 33573247U))
				{
					if (num / 1073565827U == 0U)
					{
						goto Block_3;
					}
				}
				num = 337120886U << (int)num;
				if (956107230U < num)
				{
					goto Block_4;
				}
			}
		}
		num ^= 1270173373U;
		int num3 = 15BF672E.B9179A8D();
		num = 1204620002U * num;
		int num4 = 594B55AA.B9179A8D();
		int 339906C = num3 << (num4 & (int)(num + 3280383179U));
		num = 678047525U % num;
		return new 097E1C51.14C26E2D(339906C);
		Block_3:
		num &= 1806779902U;
		long num5 = 15BF672E.8EC1A2BC();
		num = 1158966076U - num;
		int num6 = 594B55AA.B9179A8D();
		int num7 = num6;
		int num8 = (int)(num - 1125394825U);
		num %= 947416872U;
		return new 097E1C51.31EF1BAE(num5 << (num7 & num8));
		Block_4:
		throw new InvalidOperationException();
	}

	// Token: 0x060001ED RID: 493 RVA: 0x002F8C24 File Offset: 0x00179024
	private unsafe 097E1C51.0A706168 592B4570(object 169A670F, Type 5B942D4A)
	{
		uint num;
		097E1C51.0A706168 0A2;
		for (;;)
		{
			IL_00:
			object obj = 169A670F;
			num = 2086038606U;
			097E1C51.0A706168 0A = obj as 097E1C51.0A706168;
			num = (241258130U | num);
			0A2 = 0A;
			num %= 1686075016U;
			if (396241048U == num)
			{
				goto IL_3B;
			}
			for (;;)
			{
				IL_28:
				num <<= 29;
				if (5B942D4A.IsEnum)
				{
					goto IL_3B;
				}
				num = 296492605U << (int)num;
				if ((num & 1632195641U) == 0U)
				{
					goto IL_00;
				}
				num += 1078734846U;
				TypeCode typeCode = Type.GetTypeCode(5B942D4A);
				num = 2072342464U << (int)num;
				TypeCode typeCode2 = typeCode;
				num = (461053551U & num);
				if (879045223U != num)
				{
					int num2 = typeCode2 - (TypeCode)(num - 4294967293U);
					num <<= 23;
					switch (num2)
					{
					case 0:
						goto IL_1CC;
					case 1:
						goto IL_209;
					case 2:
						goto IL_258;
					case 3:
						goto IL_2AC;
					case 4:
						goto IL_306;
					case 5:
						goto IL_383;
					case 6:
						goto IL_3D0;
					case 7:
						goto IL_429;
					case 8:
						if (517816667U < num)
						{
							goto IL_00;
						}
						if (0A2 == null)
						{
							goto Block_28;
						}
						num *= 1145783183U;
						if (585651516U > num)
						{
							goto Block_29;
						}
						continue;
					case 9:
						num %= 1482236555U;
						if (num < 1741377727U)
						{
							goto Block_30;
						}
						continue;
					case 10:
						goto IL_520;
					case 11:
						if (num < 1493045204U)
						{
							goto Block_35;
						}
						continue;
					case 12:
					case 13:
					case 14:
						break;
					case 15:
						goto IL_5BE;
					default:
						num ^= 0U;
						break;
					}
					num += 611255190U;
					if (num >= 1336702949U)
					{
						goto IL_3B;
					}
					Type typeFromHandle = typeof(IntPtr);
					num *= 741676577U;
					if (5B942D4A == typeFromHandle)
					{
						bool flag = 0A2 != null;
						num *= 723592614U;
						if (!flag)
						{
							goto IL_683;
						}
						if (681521688U < num)
						{
							goto Block_44;
						}
					}
					else if (245255723U < num)
					{
						num = 298153531U << (int)num;
						if (5B942D4A == typeof(UIntPtr))
						{
							goto Block_49;
						}
						num = 1578989017U / num;
						bool isValueType = 5B942D4A.IsValueType;
						num += 1944193904U;
						if (isValueType)
						{
							bool flag2 = 0A2 != null;
							num = 679949826U / num;
							if (flag2)
							{
								if (2143100794U - num != 0U)
								{
									goto Block_56;
								}
							}
							else if (num <= 833506684U)
							{
								goto Block_57;
							}
						}
						else
						{
							if ((num & 145239715U) == 0U)
							{
								goto IL_7C;
							}
							bool isArray = 5B942D4A.IsArray;
							num = (1292531283U & num);
							if (isArray)
							{
								goto Block_60;
							}
							if (1001002653U == num)
							{
								goto IL_00;
							}
							num <<= 23;
							if (5B942D4A.IsPointer)
							{
								num %= 1438454248U;
								if (1183128534U != num)
								{
									bool flag3 = 0A2 != null;
									num += 309922809U;
									if (!flag3)
									{
										goto IL_911;
									}
									if ((655392766U & num) != 0U)
									{
										goto Block_67;
									}
								}
							}
							else
							{
								bool flag4 = 0A2 != null;
								num = (1779711200U & num);
								if (flag4)
								{
									goto IL_9A9;
								}
								num *= 519004932U;
								if (num >= 1837915242U)
								{
									goto Block_71;
								}
							}
						}
					}
				}
			}
			IL_306:
			num &= 1643868466U;
			if (670838090U == num)
			{
				goto IL_7C;
			}
			bool flag5 = 0A2 != null;
			num <<= 1;
			if (!flag5)
			{
				num |= 1606430229U;
				if (num << 20 != 0U)
				{
					goto Block_19;
				}
				continue;
			}
			else
			{
				num = 910494696U << (int)num;
				if (num >= 283144934U)
				{
					goto Block_20;
				}
				goto IL_3B;
			}
			IL_209:
			if (num + 253589192U != 0U)
			{
				goto Block_9;
			}
			goto IL_4C;
			IL_258:
			if (0A2 == null)
			{
				num = (1898388371U & num);
				if ((926026386U & num) == 0U)
				{
					goto Block_12;
				}
				continue;
			}
			else
			{
				if (num != 1518165533U)
				{
					goto Block_13;
				}
				continue;
			}
			IL_2AC:
			if (1253770075U == num)
			{
				continue;
			}
			if (0A2 != null)
			{
				goto IL_2DC;
			}
			num %= 2095068082U;
			if ((num ^ 573520186U) != 0U)
			{
				goto Block_16;
			}
			continue;
			IL_429:
			num = (400897519U & num);
			if (num + 410008500U == 0U)
			{
				continue;
			}
			if (0A2 == null)
			{
				if (num % 512771515U == 0U)
				{
					goto Block_25;
				}
				continue;
			}
			else
			{
				num %= 1056455608U;
				if (num * 2133420714U == 0U)
				{
					goto Block_26;
				}
				continue;
			}
			Block_30:
			bool flag6 = 0A2 != null;
			num = 220096505U * num;
			if (!flag6)
			{
				goto Block_31;
			}
			if (num <= 1384737733U)
			{
				goto Block_32;
			}
			continue;
			IL_520:
			num <<= 8;
			if (0A2 != null)
			{
				goto IL_54C;
			}
			if (num != 1672956927U)
			{
				goto Block_34;
			}
			continue;
			IL_5BE:
			num -= 461048108U;
			if (1531325599 << (int)num == 0)
			{
				continue;
			}
			bool flag7 = 0A2 != null;
			num = (647048518U & num);
			if (!flag7)
			{
				num ^= 1719762322U;
				if (num != 179963317U)
				{
					goto Block_39;
				}
				continue;
			}
			else
			{
				if (578500655U < num)
				{
					goto Block_40;
				}
				goto IL_3B;
			}
			IL_911:
			num &= 402944657U;
			bool flag8 = 169A670F != null;
			num >>= 4;
			if (!flag8)
			{
				goto Block_68;
			}
			num = (753940790U | num);
			if (num != 1301350941U)
			{
				goto Block_69;
			}
			goto IL_9A;
			IL_683:
			if (1969122039U <= num)
			{
				goto IL_3B;
			}
			if (169A670F != null)
			{
				goto IL_6B1;
			}
			num /= 1618106486U;
			if (1137190184U != num)
			{
				goto Block_47;
			}
			goto IL_3B;
			Block_60:
			num = 1575580725U << (int)num;
			if (1079467949U / num == 0U)
			{
				goto Block_61;
			}
			goto IL_3B;
			Block_49:
			num = 1312507445U % num;
			if ((332010693U ^ num) == 0U)
			{
				goto IL_4C;
			}
			bool flag9 = 0A2 != null;
			num += 1854152350U;
			if (flag9)
			{
				goto Block_51;
			}
			if (169A670F == null)
			{
				goto Block_52;
			}
			if (561264928U < num)
			{
				goto Block_53;
			}
			IL_3B:
			if (0A2 == null)
			{
				goto IL_5C;
			}
			if (1829794379U != num)
			{
				goto IL_4C;
			}
			continue;
			IL_7C:
			bool flag10 = 169A670F is Enum;
			num ^= 2437701819U;
			if (flag10)
			{
				goto IL_C9;
			}
			if (1292123065U <= num)
			{
				goto IL_9A;
			}
			continue;
			IL_5C:
			if (169A670F == null)
			{
				goto IL_C9;
			}
			num = 1363959995U << (int)num;
			if (num << 28 != 0U)
			{
				goto IL_7C;
			}
			goto IL_28;
			IL_4C:
			169A670F = 0A2.04F0F15D();
			num += 0U;
			goto IL_5C;
			IL_C9:
			num += 865884187U;
			bool flag11 = 169A670F != null;
			num = 136332422U % num;
			if (!flag11)
			{
				goto IL_FE;
			}
			if (1458318859U % num != 0U)
			{
				break;
			}
			goto IL_28;
			IL_9A:
			num = 243433393U << (int)num;
			object value = 169A670F;
			num = 741175544U - num;
			object obj2 = Enum.ToObject(5B942D4A, value);
			num |= 1608741553U;
			169A670F = obj2;
			num += 1611926537U;
			goto IL_C9;
		}
		object obj3 = 169A670F;
		num = (257381645U | num);
		Enum 141557E = (Enum)obj3;
		goto IL_119;
		IL_FE:
		num = 1659702060U * num;
		141557E = (Enum)Activator.CreateInstance(5B942D4A);
		num += 2330709127U;
		IL_119:
		return new 097E1C51.52644614(141557E);
		IL_1CC:
		bool 47466F;
		if (0A2 == null)
		{
			num += 1931494910U;
			47466F = Convert.ToBoolean(169A670F);
		}
		else
		{
			num += 458383412U;
			47466F = 0A2.C77753F7();
			num ^= 1752311242U;
		}
		num = 2103331827U * num;
		return new 097E1C51.1E233DE9(47466F);
		Block_9:
		char <<EMPTY_NAME>>;
		if (0A2 == null)
		{
			num = 286223422U >> (int)num;
			object value2 = 169A670F;
			num += 246485656U;
			<<EMPTY_NAME>> = Convert.ToChar(value2);
		}
		else
		{
			num &= 2042890258U;
			<<EMPTY_NAME>> = 0A2.A773FD03();
			num += 532709078U;
		}
		return new 097E1C51.0496456E(<<EMPTY_NAME>>);
		Block_12:
		sbyte 6DB62FEE = Convert.ToSByte(169A670F);
		goto IL_29E;
		Block_13:
		097E1C51.0A706168 0A3 = 0A2;
		num += 485964272U;
		6DB62FEE = 0A3.52B3F2EC();
		num += 3809003024U;
		IL_29E:
		num %= 51079272U;
		return new 097E1C51.63674386(6DB62FEE);
		Block_16:
		byte 3392633E = Convert.ToByte(169A670F);
		goto IL_2F2;
		IL_2DC:
		097E1C51.0A706168 0A4 = 0A2;
		num |= 290522250U;
		3392633E = 0A4.19201189();
		num += 4004445046U;
		IL_2F2:
		num = 586042075U >> (int)num;
		return new 097E1C51.226653A3(3392633E);
		Block_19:
		short 30E14BCB = Convert.ToInt16(169A670F);
		goto IL_375;
		Block_20:
		097E1C51.0A706168 0A5 = 0A2;
		num *= 699821896U;
		30E14BCB = 0A5.BDAC8EA4();
		num ^= 2370101077U;
		IL_375:
		num = (2024295864U & num);
		return new 097E1C51.77B017D1(30E14BCB);
		IL_383:
		bool flag12 = 0A2 != null;
		num ^= 927406020U;
		ushort 66DC3E;
		if (!flag12)
		{
			num /= 1895841413U;
			object value3 = 169A670F;
			num += 161818005U;
			66DC3E = Convert.ToUInt16(value3);
		}
		else
		{
			097E1C51.0A706168 0A6 = 0A2;
			num *= 1464368435U;
			66DC3E = 0A6.86B35C1E();
			num += 79738249U;
		}
		num += 1694655355U;
		return new 097E1C51.7EDB7F26(66DC3E);
		IL_3D0:
		bool flag13 = 0A2 != null;
		num = 695011255U << (int)num;
		int 339906C;
		if (!flag13)
		{
			num = 1023634305U >> (int)num;
			object value4 = 169A670F;
			num = 571955380U / num;
			339906C = Convert.ToInt32(value4);
		}
		else
		{
			num = 957304773U / num;
			339906C = 0A2.B9179A8D();
			num += 4688157U;
		}
		num &= 865284624U;
		return new 097E1C51.14C26E2D(339906C);
		Block_25:
		uint 5BCA48B = Convert.ToUInt32(169A670F);
		goto IL_47C;
		Block_26:
		5BCA48B = 0A2.2CD32589();
		num += 0U;
		IL_47C:
		return new 097E1C51.79942DAA(5BCA48B);
		Block_28:
		long 6DB81B = Convert.ToInt64(169A670F);
		goto IL_4C7;
		Block_29:
		097E1C51.0A706168 0A7 = 0A2;
		num -= 1081179161U;
		6DB81B = 0A7.8EC1A2BC();
		num ^= 3213788135U;
		IL_4C7:
		return new 097E1C51.31EF1BAE(6DB81B);
		Block_31:
		object value5 = 169A670F;
		num += 773996676U;
		ulong 6E5A0E = Convert.ToUInt64(value5);
		goto IL_51A;
		Block_32:
		6E5A0E = 0A2.083A097E();
		num ^= 773996676U;
		IL_51A:
		return new 097E1C51.378A58B6(6E5A0E);
		Block_34:
		object value6 = 169A670F;
		num %= 133057164U;
		float 52F83C3E = Convert.ToSingle(value6);
		goto IL_562;
		IL_54C:
		num <<= 20;
		52F83C3E = 0A2.E1E85554();
		num ^= 0U;
		IL_562:
		num = (1173699393U ^ num);
		return new 097E1C51.7B032626(52F83C3E);
		Block_35:
		bool flag14 = 0A2 != null;
		num = 611129525U << (int)num;
		double 25B00FBF;
		if (!flag14)
		{
			25B00FBF = Convert.ToDouble(169A670F);
		}
		else
		{
			num = 234778091U + num;
			25B00FBF = 0A2.D87DFA30();
			num += 4060189205U;
		}
		num = (704598495U ^ num);
		return new 097E1C51.4DA65608(25B00FBF);
		Block_39:
		string 609D036F = (string)169A670F;
		goto IL_625;
		Block_40:
		object obj4 = 0A2;
		num = (425347197U ^ num);
		609D036F = obj4.ToString();
		num ^= 2145072623U;
		IL_625:
		num = 304491388U - num;
		return new 097E1C51.703962A5(609D036F);
		Block_44:
		return new 097E1C51.6BB54CA8(0A2.E3F543C7());
		Block_47:
		IntPtr 3E = IntPtr.Zero;
		goto IL_6C7;
		IL_6B1:
		num = (841035064U ^ num);
		3E = (IntPtr)169A670F;
		num += 4130984708U;
		IL_6C7:
		num = 2138324072U - num;
		return new 097E1C51.6BB54CA8(3E);
		Block_51:
		num = 2100957164U >> (int)num;
		097E1C51.0A706168 0A8 = 0A2;
		num >>= 21;
		UIntPtr 2 = 0A8.7F934522();
		num -= 969349555U;
		return new 097E1C51.2B55285E(2);
		Block_52:
		num = (110042840U | num);
		UIntPtr 3 = UIntPtr.Zero;
		goto IL_783;
		Block_53:
		object obj5 = 169A670F;
		num = 2104568533U - num;
		3 = (UIntPtr)obj5;
		num += 4262312153U;
		IL_783:
		num = (1552760889U & num);
		return new 097E1C51.2B55285E(3);
		Block_56:
		097E1C51.0A706168 0A9 = 0A2;
		num = 788541416U + num;
		object 67EE67F = 0A9.04F0F15D();
		num ^= 1305149875U;
		return new 097E1C51.25883EE0(67EE67F);
		Block_57:
		bool flag15 = 169A670F != null;
		num = 1905618110U * num;
		object 67EE67F2;
		if (flag15)
		{
			67EE67F2 = 169A670F;
		}
		else
		{
			num &= 4997770U;
			67EE67F2 = Activator.CreateInstance(5B942D4A);
			num ^= 0U;
		}
		return new 097E1C51.25883EE0(67EE67F2);
		Block_61:
		Array 76A17C1B;
		if (0A2 == null)
		{
			num <<= 20;
			76A17C1B = (Array)169A670F;
		}
		else
		{
			76A17C1B = (Array)0A2.04F0F15D();
			num ^= 1949630464U;
		}
		return new 097E1C51.08046E73(76A17C1B);
		Block_67:
		097E1C51.0A706168 0A10 = 0A2;
		num <<= 27;
		object ptr = 0A10.04F0F15D();
		num >>= 8;
		object 526508FB = Pointer.Box(Pointer.Unbox(ptr), 5B942D4A);
		num = 1462314485U >> (int)num;
		num |= 38485320U;
		return new 097E1C51.277D4587(526508FB, 5B942D4A);
		Block_68:
		num = (1770129967U & num);
		void* ptr2 = num ^ 25165865U;
		num = 1061377715U % num;
		void* ptr3 = ptr2;
		goto IL_96D;
		Block_69:
		object ptr4 = 169A670F;
		num |= 169956566U;
		ptr3 = Pointer.Unbox(ptr4);
		num ^= 800207366U;
		IL_96D:
		object 526508FB2 = Pointer.Box(ptr3, 5B942D4A);
		num |= 1096898293U;
		return new 097E1C51.277D4587(526508FB2, 5B942D4A);
		Block_71:
		object 1A34651F = 169A670F;
		goto IL_9BF;
		IL_9A9:
		097E1C51.0A706168 0A11 = 0A2;
		num = 368260436U % num;
		1A34651F = 0A11.04F0F15D();
		num += 2316094124U;
		IL_9BF:
		return new 097E1C51.7B0B79E5(1A34651F);
	}

	// Token: 0x060001EE RID: 494 RVA: 0x002F95F8 File Offset: 0x001799F8
	private string 7C500D12(int 47873323)
	{
		uint num;
		Dictionary<int, object> dictionary2;
		do
		{
			Dictionary<int, object> dictionary = 097E1C51.6E740956;
			num = 1934046779U;
			dictionary2 = dictionary;
			num /= 227150265U;
		}
		while (num > 1909407999U);
		object obj = dictionary2;
		num |= 802881607U;
		Monitor.Enter(obj);
		string result;
		try
		{
			if ((num ^ 829979041U) == 0U)
			{
				goto IL_98;
			}
			Dictionary<int, object> dictionary3 = 097E1C51.6E740956;
			num &= 1284642823U;
			object obj2;
			string text2;
			if (dictionary3.TryGetValue(47873323, out obj2))
			{
				num /= 1221538379U;
				string text = (string)obj2;
				num = (2087075108U ^ num);
				result = text;
			}
			else
			{
				num = 1274544756U * num;
				num = (580524533U & num);
				text2 = this.45C00733.ResolveString(47873323);
				if (num <= 1952085079U)
				{
					goto IL_98;
				}
			}
			return result;
			IL_98:
			Dictionary<int, object> dictionary4 = 097E1C51.6E740956;
			object value = text2;
			num = 1509968309U * num;
			dictionary4.Add(47873323, value);
			num = (9320406U & num);
			if (1526549470U >= num)
			{
				result = text2;
			}
		}
		finally
		{
			Monitor.Exit(dictionary2);
		}
		return result;
	}

	// Token: 0x060001EF RID: 495 RVA: 0x002F96F4 File Offset: 0x00179AF4
	private Type 01581CD6(int 48092E16)
	{
		uint num;
		Dictionary<int, object> obj;
		do
		{
			Dictionary<int, object> dictionary = 097E1C51.6E740956;
			num = 186395666U;
			obj = dictionary;
		}
		while (2091670022U >> (int)num == 0U);
		Monitor.Enter(obj);
		Type result;
		try
		{
			object obj2;
			if ((num ^ 1421167749U) != 0U)
			{
				Dictionary<int, object> dictionary2 = 097E1C51.6E740956;
				num = 1407933102U * num;
				num = 1955733652U << (int)num;
				if (dictionary2.TryGetValue(48092E16, out obj2))
				{
					num |= 783042673U;
					if ((510948529U & num) == 0U)
					{
						goto IL_74;
					}
				}
				else
				{
					num = (1918126184U ^ num);
					num += 117316535U;
					Type type = this.45C00733.ResolveType(48092E16);
					num = 1048212813U % num;
					Type type2 = type;
					if (185602546U > num)
					{
						Dictionary<int, object> dictionary3 = 097E1C51.6E740956;
						num = (1099399062U ^ num);
						dictionary3.Add(48092E16, type2);
						result = type2;
						if (1347117710U > num)
						{
							return result;
						}
					}
				}
			}
			result = (Type)obj2;
			IL_74:;
		}
		finally
		{
			do
			{
				num = 291584000U;
				Monitor.Exit(obj);
			}
			while (num % 37309420U == 0U);
		}
		return result;
	}

	// Token: 0x060001F0 RID: 496 RVA: 0x002F9810 File Offset: 0x00179C10
	private MethodBase 5D982D0E(int 49DC1838)
	{
		uint num;
		Dictionary<int, object> obj;
		do
		{
			Dictionary<int, object> dictionary = 097E1C51.6E740956;
			num = 1889747778U;
			obj = dictionary;
			num ^= 925725797U;
		}
		while (num < 168184335U);
		MethodBase result;
		lock (obj)
		{
			if (num * 1446452483U == 0U)
			{
				goto IL_6F;
			}
			object obj2;
			for (;;)
			{
				IL_33:
				Dictionary<int, object> dictionary2 = 097E1C51.6E740956;
				num = 1651274981U << (int)num;
				num = 1415385442U * num;
				if (dictionary2.TryGetValue(49DC1838, out obj2))
				{
					num %= 569000690U;
					if (num + 959274207U != 0U)
					{
						goto IL_6F;
					}
				}
				Module module = this.45C00733;
				num |= 969948280U;
				num -= 1029656086U;
				MethodBase methodBase = module.ResolveMethod(49DC1838);
				num = 1506938397U >> (int)num;
				MethodBase methodBase2 = methodBase;
				num = 511012644U >> (int)num;
				if (546120166U > num)
				{
					Dictionary<int, object> dictionary3 = 097E1C51.6E740956;
					num *= 61557242U;
					dictionary3.Add(49DC1838, methodBase2);
					if (2078870623U - num == 0U)
					{
						goto IL_6F;
					}
					result = methodBase2;
					if (495127836U + num != 0U)
					{
						break;
					}
				}
			}
			return result;
			IL_6F:
			object obj3 = obj2;
			num >>= 5;
			MethodBase methodBase3 = (MethodBase)obj3;
			num = 1109594792U >> (int)num;
			result = methodBase3;
			if ((num ^ 1288981193U) == 0U)
			{
				goto IL_33;
			}
		}
		return result;
	}

	// Token: 0x060001F1 RID: 497 RVA: 0x002F995C File Offset: 0x00179D5C
	private FieldInfo 672A664F(int 65D139EE)
	{
		uint num = 1869818775U;
		Dictionary<int, object> dictionary2;
		if (num / 518719163U != 0U)
		{
			do
			{
				Dictionary<int, object> dictionary = 097E1C51.6E740956;
				num %= 1364813067U;
				dictionary2 = dictionary;
			}
			while (933518087U <= num);
		}
		object obj = dictionary2;
		num *= 2140407652U;
		Monitor.Enter(obj);
		FieldInfo result;
		try
		{
			object obj2;
			if (097E1C51.6E740956.TryGetValue(65D139EE, out obj2))
			{
				num = 1472419070U >> (int)num;
			}
			else
			{
				num %= 491674960U;
				Module module = this.45C00733;
				num = 1360855339U >> (int)num;
				num = 441529404U << (int)num;
				FieldInfo fieldInfo = module.ResolveField(65D139EE);
				num *= 247404416U;
				FieldInfo fieldInfo2 = fieldInfo;
				Dictionary<int, object> dictionary3 = 097E1C51.6E740956;
				num = 273506772U + num;
				object value = fieldInfo2;
				num = (1819422385U & num);
				dictionary3.Add(65D139EE, value);
				num -= 1388461453U;
				result = fieldInfo2;
				if (num != 2111860759U)
				{
					return result;
				}
			}
			do
			{
				object obj3 = obj2;
				num ^= 989685573U;
				FieldInfo fieldInfo3 = (FieldInfo)obj3;
				num = (1577595967U | num);
				result = fieldInfo3;
			}
			while (num - 16071249U == 0U);
		}
		finally
		{
			Monitor.Exit(dictionary2);
		}
		return result;
	}

	// Token: 0x060001F2 RID: 498 RVA: 0x002F9A80 File Offset: 0x00179E80
	private 097E1C51.0A706168 2BA571C3(MethodBase 120B2AC7)
	{
		uint num;
		Dictionary<int, 097E1C51.0A706168> dictionary;
		object[] array3;
		object obj2;
		do
		{
			num = 389105976U;
			ParameterInfo[] parameters = 120B2AC7.GetParameters();
			num = (1666217981U ^ num);
			ParameterInfo[] array = parameters;
			if (1670185836U == num)
			{
				goto IL_A2;
			}
			IL_25:
			dictionary = new Dictionary<int, 097E1C51.0A706168>();
			num ^= 1756051675U;
			if (num == 1642206477U)
			{
				continue;
			}
			ParameterInfo[] array2 = array;
			num = 2110996939U << (int)num;
			int num2 = array2.Length;
			num /= 1806899392U;
			array3 = new object[num2];
			num = (1803118047U | num);
			ParameterInfo[] array4 = array;
			num = 951220933U % num;
			int num3 = array4.Length;
			int num4 = (int)(num ^ 951220932U);
			num ^= 1580218349U;
			int num5 = num3 - num4;
			num = 2074617754U >> (int)num;
			int num6 = num5;
			IL_A2:
			for (;;)
			{
				num -= 1887335429U;
				uint num7 = (uint)num6;
				num ^= 1075056724U;
				if (num7 < num + 806804362U)
				{
					break;
				}
				num = 673332632U;
				097E1C51.0A706168 0A = this.2734307D();
				num += 1846482621U;
				097E1C51.0A706168 0A2 = 0A;
				if (num <= 913053747U)
				{
					goto IL_25;
				}
				bool flag = 0A2.3CF78257();
				num *= 785799570U;
				if (flag)
				{
					num |= 510855157U;
					dictionary[num6] = 0A2;
					num ^= 208798341U;
				}
				object[] array5 = array3;
				num = 2066287378U - num;
				int num8 = num6;
				num <<= 7;
				num -= 1278376794U;
				object 169A670F = 0A2;
				ParameterInfo[] array6 = array;
				int num9 = num6;
				num &= 424490162U;
				ParameterInfo parameterInfo = array6[num9];
				num ^= 1518482254U;
				097E1C51.0A706168 0A3 = this.592B4570(169A670F, parameterInfo.ParameterType);
				num += 963840781U;
				object obj = 0A3.04F0F15D();
				num |= 2050702895U;
				array5[num8] = obj;
				int num10 = num6 - (int)(num ^ 4278140670U);
				num = 332416557U >> (int)num;
				num6 = num10;
				num ^= 8103975U;
			}
			num = 246553634U + num;
			ConstructorInfo constructorInfo = (ConstructorInfo)120B2AC7;
			num >>= 27;
			object[] parameters2 = array3;
			num <<= 29;
			obj2 = constructorInfo.Invoke(parameters2);
			num ^= 1176830865U;
		}
		while (num >= 1513628667U);
		Dictionary<int, 097E1C51.0A706168> dictionary2 = dictionary;
		num = (149303199U | num);
		Dictionary<int, 097E1C51.0A706168>.Enumerator enumerator = dictionary2.GetEnumerator();
		try
		{
			if (727140699U < num)
			{
				for (;;)
				{
					num &= 1477332246U;
					num <<= 3;
					bool flag2 = enumerator.MoveNext();
					num = 1012488905U + num;
					KeyValuePair<int, 097E1C51.0A706168> keyValuePair2;
					if (!flag2)
					{
						if (971442285U < num)
						{
							break;
						}
					}
					else
					{
						KeyValuePair<int, 097E1C51.0A706168> keyValuePair = enumerator.Current;
						num = 1499032098U;
						keyValuePair2 = keyValuePair;
						num /= 1393913985U;
					}
					097E1C51.0A706168 value = keyValuePair2.Value;
					num >>= 3;
					object[] array7 = array3;
					num = 1278299038U * num;
					int key = keyValuePair2.Key;
					num %= 1720857417U;
					object 5076725B = array7[key];
					num = 378629343U << (int)num;
					value.360F4E2C(5076725B);
					num ^= 947274560U;
				}
			}
		}
		finally
		{
			do
			{
				num = 1785210529U;
				if (num == 1398897045U)
				{
					break;
				}
				((IDisposable)enumerator).Dispose();
			}
			while (num * 440604084U == 0U);
		}
		num = 550895721U;
		object 169A670F2 = obj2;
		num /= 393623032U;
		return this.592B4570(169A670F2, 120B2AC7.DeclaringType);
	}

	// Token: 0x060001F3 RID: 499 RVA: 0x002F9DA4 File Offset: 0x0017A1A4
	private bool 01B47CEE(MethodBase 620E49B6, object 74767079, ref object 635602FC, object[] 748542EB)
	{
		uint num = 1163145550U;
		for (;;)
		{
			IL_06:
			num %= 1895842942U;
			Type declaringType = 620E49B6.DeclaringType;
			for (;;)
			{
				bool flag = declaringType != null;
				num *= 1920615554U;
				if (!flag)
				{
					goto Block_1;
				}
				if (num <= 1618238111U)
				{
					goto IL_06;
				}
				bool isGenericType = declaringType.IsGenericType;
				num = 846603663U % num;
				if (!isGenericType)
				{
					goto IL_262;
				}
				num <<= 14;
				if ((num & 934696032U) == 0U)
				{
					goto IL_ED;
				}
				Type genericTypeDefinition = declaringType.GetGenericTypeDefinition();
				num ^= 1856330248U;
				RuntimeTypeHandle handle = typeof(Nullable<>).TypeHandle;
				num -= 958487697U;
				Type typeFromHandle = Type.GetTypeFromHandle(handle);
				num ^= 2631080184U;
				if (genericTypeDefinition != typeFromHandle)
				{
					goto IL_262;
				}
				num = 760874480U << (int)num;
				num = 1469739445U << (int)num;
				string name = 620E49B6.Name;
				num ^= 1571971419U;
				string b = "get_HasValue";
				num = 1052129821U * num;
				StringComparison comparisonType = (StringComparison)(num ^ 3885225714U);
				num %= 1099045356U;
				if (string.Equals(name, b, comparisonType))
				{
					break;
				}
				if (num - 407121448U != 0U)
				{
					string name2 = 620E49B6.Name;
					num = 584347444U >> (int)num;
					string b2 = "get_Value";
					StringComparison comparisonType2 = (StringComparison)(num ^ 2225U);
					num = 1598649464U % num;
					bool flag2 = string.Equals(name2, b2, comparisonType2);
					num %= 1622895262U;
					if (flag2)
					{
						goto Block_8;
					}
					num -= 1481075924U;
					string name3 = 620E49B6.Name;
					string value = "GetValueOrDefault";
					num &= 869552337U;
					StringComparison comparisonType3 = (StringComparison)(num ^ 596643844U);
					num = 475429664U / num;
					bool flag3 = name3.Equals(value, comparisonType3);
					num -= 1404594306U;
					num += 2778640766U;
					if (!flag3)
					{
						goto IL_25A;
					}
					num = 1059723322U - num;
					bool flag4 = 74767079 != null;
					num = 445537598U % num;
					if (!flag4)
					{
						num %= 2045279938U;
						if (num * 733297472U == 0U)
						{
							goto IL_113;
						}
						num = (266738795U & num);
						object obj = Activator.CreateInstance(Nullable.GetUnderlyingType(620E49B6.DeclaringType));
						num >>= 11;
						74767079 = obj;
						num ^= 445582845U;
					}
					num &= 1710258281U;
					if (num != 1509320990U)
					{
						goto Block_14;
					}
				}
			}
			num ^= 244413330U;
			if (num >= 1980770188U)
			{
				continue;
			}
			IL_ED:
			bool flag5 = 74767079 != null;
			num = 151681055U - num;
			object obj2 = flag5;
			num = 1834953988U * num;
			635602FC = obj2;
			if (1850739935U >= num)
			{
				goto IL_113;
			}
			continue;
			Block_8:
			bool flag6 = 74767079 != null;
			num >>= 5;
			if (!flag6)
			{
				goto Block_9;
			}
			object obj3 = 74767079;
			num &= 1722382431U;
			635602FC = obj3;
			if (710041348 << (int)num != 0)
			{
				goto Block_10;
			}
		}
		Block_1:
		return num - 3439706524U != 0U;
		IL_113:
		goto IL_25A;
		Block_9:
		throw new InvalidOperationException();
		Block_10:
		num += 1374046438U;
		goto IL_25A;
		Block_14:
		object obj4 = 74767079;
		num /= 1301564104U;
		635602FC = obj4;
		num ^= 1374046460U;
		IL_25A:
		return (num ^ 1374046461U) != 0U;
		IL_262:
		num = 501643670U % num;
		return (num ^ 501643670U) != 0U;
	}

	// Token: 0x060001F4 RID: 500 RVA: 0x002FA024 File Offset: 0x0017A424
	private 097E1C51.0A706168 544A548B(MethodBase 716750DB, bool 09F64FAE)
	{
		uint num;
		MethodInfo methodInfo;
		Dictionary<int, 097E1C51.0A706168> dictionary;
		object[] array4;
		object obj3;
		object obj5;
		object[] array9;
		for (;;)
		{
			IL_00:
			num = 811601103U;
			methodInfo = (716750DB as MethodInfo);
			if (1700166408U % num != 0U)
			{
				097E1C51.0A706168 0A;
				do
				{
					IL_1A:
					num |= 1735273918U;
					ParameterInfo[] parameters = 716750DB.GetParameters();
					num ^= 1628075207U;
					ParameterInfo[] array = parameters;
					if (num > 652548455U)
					{
						goto IL_00;
					}
					for (;;)
					{
						IL_3D:
						dictionary = new Dictionary<int, 097E1C51.0A706168>();
						object obj2;
						for (;;)
						{
							ParameterInfo[] array2 = array;
							num %= 726023611U;
							int num2 = array2.Length;
							num %= 109658151U;
							object[] array3 = new object[num2];
							num /= 1728532536U;
							array4 = array3;
							num = 1078410611U >> (int)num;
							int num3 = array.Length - (int)(num ^ 1078410610U);
							for (;;)
							{
								uint num4 = (uint)num3;
								num = 1584020660U + num;
								if (num4 < (num ^ 2662431271U))
								{
									break;
								}
								num = 1249650238U;
								num <<= 31;
								0A = this.2734307D();
								if (688665969U < num)
								{
									goto IL_00;
								}
								if (0A.3CF78257())
								{
									num /= 1465541331U;
									Dictionary<int, 097E1C51.0A706168> dictionary2 = dictionary;
									num -= 1741499936U;
									dictionary2[num3] = 0A;
									num += 1741499936U;
								}
								object[] array5 = array4;
								int num5 = num3;
								num &= 1154878278U;
								num = 923823836U + num;
								object 169A670F = 0A;
								ParameterInfo[] array6 = array;
								int num6 = num3;
								num -= 301020495U;
								Type parameterType = array6[num6].ParameterType;
								num %= 88807086U;
								097E1C51.0A706168 0A2 = this.592B4570(169A670F, parameterType);
								num -= 2112956797U;
								array5[num5] = 0A2.04F0F15D();
								if (2014607771U > num)
								{
									goto IL_00;
								}
								int num7 = num3;
								num = 2030710332U * num;
								num3 = num7 - (int)(num ^ 2667745865U);
								num += 2705632043U;
							}
							num ^= 652893231U;
							097E1C51.0A706168 0A3;
							if (!716750DB.IsStatic)
							{
								num ^= 1229671200U;
								0A3 = this.2734307D();
							}
							else
							{
								num = 880030537U >> (int)num;
								if (num >= 287201638U)
								{
									goto IL_00;
								}
								0A3 = null;
								num ^= 4045674779U;
							}
							0A = 0A3;
							if (num < 307459589U)
							{
								goto IL_1A;
							}
							object obj;
							if (0A == null)
							{
								if ((num ^ 1892946144U) == 0U)
								{
									goto IL_00;
								}
								obj = null;
							}
							else
							{
								num = 1225344703U << (int)num;
								if (num >= 1545999949U)
								{
									goto IL_00;
								}
								obj = 0A.04F0F15D();
								num += 3888754216U;
							}
							num /= 1395407096U;
							bool flag = (obj2 = obj) != null;
							num -= 1746555319U;
							if (flag)
							{
								break;
							}
							if (346882503U != num)
							{
								goto Block_11;
							}
						}
						IL_218:
						obj3 = obj2;
						num &= 121135947U;
						if ((num ^ 2025926514U) == 0U)
						{
							goto IL_1A;
						}
						if (09F64FAE)
						{
							bool flag2 = obj3 != null;
							num = 552013146U >> (int)num;
							num += 119276906U;
							if (!flag2)
							{
								goto Block_14;
							}
						}
						num += 1803768471U;
						if (num << 8 == 0U)
						{
							goto IL_1A;
						}
						object obj4 = null;
						num = (625563941U & num);
						obj5 = obj4;
						num = 372978491U + num;
						if (716750DB.IsConstructor)
						{
							num = 1166704582U % num;
							if (num - 942365642U == 0U)
							{
								goto IL_1A;
							}
							num ^= 709037551U;
							bool isValueType = 716750DB.DeclaringType.IsValueType;
							num |= 1431059004U;
							num ^= 1131575527U;
							if (isValueType)
							{
								break;
							}
						}
						if (num + 1333160100U == 0U)
						{
							goto IL_1A;
						}
						object <<EMPTY_NAME>> = obj3;
						num ^= 2049387750U;
						num = (752707023U ^ num);
						object[] 748542EB = array4;
						num &= 637014799U;
						bool flag3 = this.01B47CEE(716750DB, <<EMPTY_NAME>>, ref obj5, 748542EB);
						num += 2549049948U;
						if (flag3)
						{
							goto IL_A37;
						}
						num += 1104096530U;
						if (num <= 571228665U)
						{
							continue;
						}
						num = 845041982U / num;
						if (09F64FAE)
						{
							goto IL_9E9;
						}
						num <<= 24;
						num >>= 15;
						bool isVirtual = 716750DB.IsVirtual;
						num ^= 0U;
						if (!isVirtual)
						{
							goto IL_9E9;
						}
						num = (760036725U | num);
						bool isFinal = 716750DB.IsFinal;
						num ^= 760036725U;
						if (isFinal)
						{
							goto IL_9E9;
						}
						num %= 333464269U;
						ParameterInfo[] array7 = array;
						num = 1272930620U >> (int)num;
						int num8 = array7.Length;
						num = 844321954U >> (int)num;
						int num9 = num8;
						int num10 = (int)(num - 2U);
						num -= 739599213U;
						int num11 = num9 + num10;
						num &= 551431676U;
						object[] array8 = new object[num11];
						num = 967340842U * num;
						array9 = array8;
						if (643897514U + num != 0U)
						{
							object[] array10 = array9;
							int num12 = (int)(num + 3860034488U);
							num ^= 1134822410U;
							array10[num12] = obj3;
							int num13 = (int)(num ^ 1514702914U);
							num /= 666926071U;
							int num14 = num13;
							while ((932186355U ^ num) != 0U)
							{
								int num15 = num14;
								num = 1135964963U << (int)num;
								ParameterInfo[] array11 = array;
								num = 1777889854U * num;
								int num16 = array11.Length;
								num = 730007869U % num;
								if (num15 >= num16)
								{
									num = (113854546U & num);
									if (207497231U > num)
									{
										goto Block_32;
									}
									goto IL_3D;
								}
								else
								{
									object[] array12 = array9;
									int num17 = num14;
									int num18 = 1;
									num = 561076168U;
									int num19 = num17 + num18;
									num = 1628911523U % num;
									array12[num19] = array4[num14];
									int num20 = num14;
									int num21 = (int)(num ^ 506759186U);
									num = 65370503U * num;
									num14 = num20 + num21;
									num += 1274823421U;
								}
							}
							goto IL_1A;
						}
						continue;
						Block_11:
						obj2 = null;
						num ^= 0U;
						goto IL_218;
					}
				}
				while ((774596064U ^ num) == 0U);
				Type declaringType = 716750DB.DeclaringType;
				num = (1248620782U & num);
				obj3 = Activator.CreateInstance(declaringType, array4);
				num *= 811012803U;
				if (0A == null)
				{
					goto IL_A37;
				}
				bool flag4 = 0A.3CF78257();
				num += 309485215U;
				num ^= 1938728611U;
				if (!flag4)
				{
					goto IL_A37;
				}
				097E1C51.0A706168 0A4 = 0A;
				num = 1149202811U % num;
				object 169A670F2 = obj3;
				Type declaringType2 = 716750DB.DeclaringType;
				num = 1246431233U * num;
				097E1C51.0A706168 0A5 = this.592B4570(169A670F2, declaringType2);
				num *= 1920275676U;
				object 5076725B = 0A5.04F0F15D();
				num |= 8728536U;
				0A4.360F4E2C(5076725B);
				if (num != 1131641850U)
				{
					goto Block_22;
				}
			}
		}
		Block_14:
		throw new NullReferenceException();
		Block_22:
		num += 2311552098U;
		goto IL_A37;
		Block_32:
		Dictionary<MethodBase, DynamicMethod> obj6 = 097E1C51.7B842C70;
		DynamicMethod dynamicMethod;
		lock (obj6)
		{
			num = 801063713U * num;
			if (2046696618U <= num)
			{
				goto IL_594;
			}
			IL_526:
			Dictionary<MethodBase, DynamicMethod> dictionary3 = 097E1C51.7B842C70;
			num = 956916318U >> (int)num;
			num <<= 24;
			if (dictionary3.TryGetValue(716750DB, out dynamicMethod))
			{
				goto IL_8BD;
			}
			num <<= 18;
			if ((num ^ 1317208098U) == 0U)
			{
				goto IL_5BC;
			}
			IL_562:
			object[] array13 = array9;
			num = 1449133888U * num;
			int num22 = array13.Length;
			num *= 346228141U;
			int num23 = num22;
			num = 215293645U + num;
			Type[] array14 = new Type[num23];
			num = 800339693U * num;
			Type[] array15 = array14;
			num *= 2044665519U;
			IL_594:
			Type[] array16 = array15;
			int num24 = (int)(num ^ 2321614439U);
			num = 841643709U - num;
			num = 1949895397U / num;
			array16[num24] = 716750DB.DeclaringType;
			int num25 = (int)(num + 0U);
			IL_5BC:
			for (;;)
			{
				int num26 = num25;
				ParameterInfo[] array;
				ParameterInfo[] array17 = array;
				num += 1815311639U;
				int num27 = array17.Length;
				num >>= 7;
				int num28 = num27;
				num = 1141198241U / num;
				if (num26 >= num28)
				{
					break;
				}
				num = 1548316341U;
				if (num <= 464918012U)
				{
					goto IL_562;
				}
				Type[] array18 = array15;
				int num29 = num25;
				int num30 = (int)(num ^ 1548316340U);
				num = (1936009100U & num);
				int num31 = num29 + num30;
				num |= 188306797U;
				array18[num31] = array[num25].ParameterType;
				int num32 = num25;
				num = 931408268U % num;
				int num33 = (int)(num + 3363559029U);
				num -= 780928997U;
				int num34 = num32 + num33;
				num /= 1820793706U;
				num25 = num34;
				num ^= 0U;
			}
			if (num >= 1415598723U)
			{
				goto IL_562;
			}
			string name = "";
			Type returnType2;
			if (methodInfo != null)
			{
				num = 692015333U + num;
				MethodInfo methodInfo2 = methodInfo;
				num = 1006710680U + num;
				Type returnType = methodInfo2.ReturnType;
				num &= 440602529U;
				if (returnType != typeof(void))
				{
					MethodInfo methodInfo3 = methodInfo;
					num >>= 6;
					returnType2 = methodInfo3.ReturnType;
					num ^= 65539U;
					goto IL_6B9;
				}
				num += 4290772943U;
			}
			num >>= 6;
			returnType2 = null;
			IL_6B9:
			num /= 997860055U;
			Type[] parameterTypes = array15;
			RuntimeTypeHandle handle = typeof(097E1C51).TypeHandle;
			num |= 387016298U;
			Type typeFromHandle = Type.GetTypeFromHandle(handle);
			num /= 1719557052U;
			Module module = typeFromHandle.Module;
			num = (1064967219U ^ num);
			bool skipVisibility = num + 3230000078U != 0U;
			num = 1752395018U << (int)num;
			dynamicMethod = new DynamicMethod(name, returnType2, parameterTypes, module, skipVisibility);
			num += 1415991396U;
			ILGenerator ilgenerator = dynamicMethod.GetILGenerator();
			num ^= 394217008U;
			if (1635405451U >> (int)num == 0U)
			{
				goto IL_526;
			}
			ILGenerator ilgenerator2 = ilgenerator;
			097E1C51.0A706168 0A;
			097E1C51.0A706168 0A6 = 0A;
			num |= 431573041U;
			bool flag5 = 0A6.3CF78257();
			num = 1476269157U + num;
			OpCode opcode;
			if (!flag5)
			{
				num = (1492255485U ^ num);
				opcode = OpCodes.Ldarg;
			}
			else
			{
				opcode = OpCodes.Ldarga;
				num ^= 1492255485U;
			}
			ilgenerator2.Emit(opcode, (int)(num + 3036319705U));
			num = 85408220U / num;
			int num35 = (int)(num - uint.MaxValue);
			if (432174073U < num)
			{
				goto IL_562;
			}
			for (;;)
			{
				num = 911942772U - num;
				int num36 = num35;
				num = (1712862371U | num);
				Type[] array19 = array15;
				num = (170266228U ^ num);
				int num37 = array19.Length;
				num %= 1619986505U;
				int num38 = num37;
				num <<= 7;
				if (num36 >= num38)
				{
					break;
				}
				ILGenerator ilgenerator3 = ilgenerator;
				Dictionary<int, 097E1C51.0A706168> dictionary4 = dictionary;
				int num39 = num35;
				num = 950168967U;
				bool flag6 = dictionary4.ContainsKey(num39 - (int)(num + 3344798330U));
				num = 2084778763U % num;
				OpCode opcode2;
				if (!flag6)
				{
					num ^= 172785512U;
					opcode2 = OpCodes.Ldarg;
				}
				else
				{
					opcode2 = OpCodes.Ldarga;
					num += 4122202264U;
				}
				int arg = num35;
				num = 109540808U + num;
				ilgenerator3.Emit(opcode2, arg);
				num = 115282484U << (int)num;
				if (num <= 1991000243U)
				{
					goto IL_526;
				}
				int num40 = num35;
				int num41 = (int)(num ^ 2147483649U);
				num = 1487828461U / num;
				int num42 = num40 + num41;
				num &= 1855072216U;
				num35 = num42;
				num += 0U;
			}
			num = 241004601U - num;
			ILGenerator ilgenerator4 = ilgenerator;
			num %= 421229899U;
			OpCode call = OpCodes.Call;
			num = (635637072U | num);
			MethodInfo meth = methodInfo;
			num = 1694844893U + num;
			ilgenerator4.Emit(call, meth);
			num = (750981803U ^ num);
			ilgenerator.Emit(OpCodes.Ret);
			num = (1543445220U & num);
			Dictionary<MethodBase, DynamicMethod> dictionary5 = 097E1C51.7B842C70;
			DynamicMethod value = dynamicMethod;
			num /= 1889029049U;
			dictionary5[716750DB] = value;
			num ^= 150994944U;
			IL_8BD:;
		}
		do
		{
			obj5 = dynamicMethod.Invoke(null, array9);
			num = 576991302U;
		}
		while (1915754340U >> (int)num == 0U);
		Dictionary<int, 097E1C51.0A706168> dictionary6 = dictionary;
		num %= 935556486U;
		Dictionary<int, 097E1C51.0A706168>.Enumerator enumerator = dictionary6.GetEnumerator();
		try
		{
			if (num - 677594103U == 0U)
			{
				goto IL_915;
			}
			IL_910:
			goto IL_96A;
			IL_915:
			num = 1822123149U;
			num |= 566036009U;
			KeyValuePair<int, 097E1C51.0A706168> keyValuePair = enumerator.Current;
			097E1C51.0A706168 value2 = keyValuePair.Value;
			object[] array20 = array9;
			num = (1874592750U & num);
			int key = keyValuePair.Key;
			num = 1021781847U - num;
			int num43 = (int)(num ^ 3475711146U);
			num >>= 17;
			value2.360F4E2C(array20[key + num43]);
			num += 576964785U;
			IL_96A:
			num <<= 3;
			if (enumerator.MoveNext())
			{
				goto IL_915;
			}
			if (734868611U % num == 0U)
			{
				goto IL_910;
			}
		}
		finally
		{
			do
			{
				num = 842156926U;
				if (num >> 2 == 0U)
				{
					break;
				}
				((IDisposable)enumerator).Dispose();
			}
			while (413945013U > num);
		}
		IL_9BA:
		num = 80172447U;
		if (732458485U >= num)
		{
			dictionary.Clear();
			if (num < 1191657475U)
			{
				num ^= 3160947137U;
			}
		}
		goto IL_A37;
		IL_9E9:
		num /= 83972102U;
		if ((1798787027U ^ num) == 0U)
		{
			goto IL_9BA;
		}
		num = 344596236U + num;
		object obj7 = obj3;
		num <<= 2;
		object[] parameters2 = array4;
		num = 163320542U + num;
		object obj8 = 716750DB.Invoke(obj7, parameters2);
		num = 641411225U >> (int)num;
		obj5 = obj8;
		num += 3098465138U;
		IL_A37:
		if (num >= 232674580U)
		{
			Dictionary<int, 097E1C51.0A706168> dictionary7 = dictionary;
			num = 407251627U / num;
			Dictionary<int, 097E1C51.0A706168>.Enumerator enumerator2 = dictionary7.GetEnumerator();
			num %= 496203180U;
			using (Dictionary<int, 097E1C51.0A706168>.Enumerator enumerator = enumerator2)
			{
				for (;;)
				{
					num = (398204816U & num);
					KeyValuePair<int, 097E1C51.0A706168> keyValuePair2;
					if (!enumerator.MoveNext())
					{
						if (1650023787U - num != 0U)
						{
							break;
						}
					}
					else
					{
						do
						{
							num = 1072004899U;
							num &= 978023365U;
							keyValuePair2 = enumerator.Current;
						}
						while ((1019372833U ^ num) == 0U);
					}
					097E1C51.0A706168 value3 = keyValuePair2.Value;
					object[] array21 = array4;
					num = 464796895U * num;
					value3.360F4E2C(array21[keyValuePair2.Key]);
					num += 4065629729U;
				}
			}
			num = 1765092486U;
			if (methodInfo != null)
			{
				num = 1292639020U - num;
				if (num >> 21 != 0U)
				{
					MethodInfo methodInfo4 = methodInfo;
					num = 1379205969U % num;
					Type returnType3 = methodInfo4.ReturnType;
					num = (1150101841U | num);
					if (returnType3 == typeof(void))
					{
						num += 309854517U;
						goto IL_B2A;
					}
				}
				num = 278756387U - num;
				num -= 434374339U;
				object 169A670F3 = obj5;
				num = 1891319701U + num;
				MethodInfo methodInfo5 = methodInfo;
				num = 2068121266U - num;
				return this.592B4570(169A670F3, methodInfo5.ReturnType);
			}
			IL_B2A:
			return null;
		}
		goto IL_9BA;
	}

	// Token: 0x060001F5 RID: 501 RVA: 0x002FABD8 File Offset: 0x0017AFD8
	private 097E1C51.0A706168 5C7D1773(int 0516041F, bool 50755993)
	{
		uint num = 2121925313U;
		if (2075877493U * num != 0U)
		{
			goto IL_12;
		}
		Dictionary<int, 097E1C51.0A706168> dictionary2;
		int num11;
		object obj3;
		for (;;)
		{
			IL_22:
			this.14EA7BFD = 0516041F;
			if (num != 392380190U)
			{
				ushort num2 = (ushort)this.197810C1();
				object[] array;
				for (;;)
				{
					Dictionary<int, 097E1C51.0A706168> dictionary = new Dictionary<int, 097E1C51.0A706168>();
					num /= 1072720145U;
					dictionary2 = dictionary;
					for (;;)
					{
						array = null;
						if (478955724U - num == 0U)
						{
							goto IL_12;
						}
						uint num3 = (uint)num2;
						num <<= 21;
						if (num3 > num + 0U)
						{
							num = 1404853485U >> (int)num;
							object[] array2 = new object[(int)num2];
							num <<= 21;
							array = array2;
							int num4 = (int)num2;
							num *= 225602197U;
							int num5 = num4 - (int)(num + 31457281U);
							while (703428979U < num)
							{
								int num6 = num5;
								uint num7 = num ^ 4263510016U;
								num = 1042950225U << (int)num;
								if (num6 < num7)
								{
									num += 3252017071U;
									goto IL_1B6;
								}
								num = 1696220284U;
								if (num <= 1583616497U)
								{
									goto IL_12;
								}
								num = 1345404247U + num;
								097E1C51.0A706168 0A = this.2734307D();
								num = (552694443U ^ num);
								bool flag = 0A.3CF78257();
								num = 1241142087U / num;
								if (flag)
								{
									num = 1973958875U >> (int)num;
									Dictionary<int, 097E1C51.0A706168> dictionary3 = dictionary2;
									num *= 138038105U;
									int key = num5;
									097E1C51.0A706168 value = 0A;
									num = 2052664892U + num;
									dictionary3[key] = value;
									num += 2615469217U;
								}
								if (num + 1246038812U == 0U)
								{
									goto IL_12;
								}
								object[] array3 = array;
								int num8 = num5;
								object 169A670F = 0A;
								num += 1653758585U;
								num -= 2029138263U;
								097E1C51.0A706168 0A2 = this.592B4570(169A670F, this.01581CD6(this.293B3F40()));
								num &= 2006188774U;
								object obj = 0A2.04F0F15D();
								num = (1307459510U | num);
								array3[num8] = obj;
								num = 2089486050U << (int)num;
								int num9 = num5 - (int)(num + 1199570945U);
								num <<= 30;
								num5 = num9;
								num ^= 4263510016U;
							}
							goto IL_22;
						}
						IL_1B6:
						if (1735425928U + num == 0U)
						{
							goto IL_22;
						}
						int num10 = this.293B3F40();
						num = (1269004554U & num);
						if (970157329U >> (int)num == 0U)
						{
							goto IL_12;
						}
						0516041F = this.14EA7BFD;
						if (1304058624U <= num)
						{
							goto IL_22;
						}
						num = (1729105627U ^ num);
						this.14EA7BFD = num11;
						if (1356885746U + num == 0U)
						{
							break;
						}
						num = 1006383501U * num;
						if (!50755993)
						{
							goto IL_278;
						}
						num = 1255955703U + num;
						bool flag2 = array != null;
						num *= 204809532U;
						if (flag2)
						{
							object[] array4 = array;
							num += 8389642U;
							bool flag3 = array4[(int)(num - 3863838002U)];
							num ^= 259729640U;
							num += 101565381U;
							if (flag3)
							{
								goto IL_278;
							}
							num ^= 176775351U;
						}
						if (1602058368U != num)
						{
							goto Block_16;
						}
					}
				}
				IL_278:
				097E1C51 097E1C = new 097E1C51();
				object[] 781D0A = array;
				num &= 1153640348U;
				object obj2 = 097E1C.183C4865(781D0A, 0516041F);
				num %= 1043150680U;
				obj3 = obj2;
				if (num != 1079867397U)
				{
					goto Block_17;
				}
			}
		}
		Block_16:
		throw new NullReferenceException();
		Block_17:
		Dictionary<int, 097E1C51.0A706168> dictionary4 = dictionary2;
		num <<= 7;
		using (Dictionary<int, 097E1C51.0A706168>.Enumerator enumerator = dictionary4.GetEnumerator())
		{
			if (num < 999246633U)
			{
				for (;;)
				{
					for (;;)
					{
						num = 980568997U >> (int)num;
						if (num >> 4 == 0U)
						{
							goto IL_2C1;
						}
						bool flag4 = enumerator.MoveNext();
						num |= 1370767335U;
						if (flag4)
						{
							goto IL_2C1;
						}
						if (num > 1689089620U)
						{
							goto IL_363;
						}
						IL_2E4:
						num &= 854797854U;
						KeyValuePair<int, 097E1C51.0A706168> keyValuePair;
						097E1C51.0A706168 value2 = keyValuePair.Value;
						num %= 2134640233U;
						object[] array;
						object[] array5 = array;
						num %= 2006467229U;
						num &= 2030921260U;
						int key2 = keyValuePair.Key;
						num /= 1529025788U;
						value2.360F4E2C(array5[key2]);
						num ^= 157622784U;
						break;
						IL_2C1:
						KeyValuePair<int, 097E1C51.0A706168> keyValuePair2 = enumerator.Current;
						num = 2052347184U;
						keyValuePair = keyValuePair2;
						num = 1306358901U - num;
						if ((num & 1086587914U) != 0U)
						{
							goto IL_2E4;
						}
					}
				}
			}
			IL_363:;
		}
		Type type;
		for (;;)
		{
			num = 1299515400U;
			if (875835313U > num)
			{
				goto IL_40D;
			}
			int num10;
			bool flag5 = num10 != 0;
			num = 402530268U << (int)num;
			if (!flag5)
			{
				goto IL_40D;
			}
			for (;;)
			{
				int 48092E = num10;
				num <<= 5;
				type = this.01581CD6(48092E);
				if (num <= 1761684451U)
				{
					break;
				}
				Type type2 = type;
				Type typeFromHandle = typeof(void);
				num *= 610868197U;
				num += 2812697600U;
				if (type2 == typeFromHandle)
				{
					goto IL_40D;
				}
				if (num > 948636253U)
				{
					goto Block_22;
				}
			}
		}
		Block_22:
		num *= 234251040U;
		object 169A670F2 = obj3;
		num = 1056448435U + num;
		Type 5B942D4A = type;
		num *= 919090704U;
		return this.592B4570(169A670F2, 5B942D4A);
		IL_40D:
		num *= 448276962U;
		return null;
		IL_12:
		num11 = this.14EA7BFD;
		num = (8937453U & num);
		goto IL_22;
	}

	// Token: 0x060001F6 RID: 502 RVA: 0x002FB018 File Offset: 0x0017B418
	private bool 675D49B1(object 54893EA9, Type 556D1F25)
	{
		uint num = 1801134689U;
		if (812730038U % num != 0U)
		{
			num = (1539135080U | num);
			if (54893EA9 != null)
			{
				num = 1990469200U >> (int)num;
				num = 1855537286U % num;
				Type type = 54893EA9.GetType();
				num /= 966612803U;
				Type type2 = type;
				num = 609246834U >> (int)num;
				if (type2 != 556D1F25)
				{
					num |= 707879702U;
					if (!556D1F25.IsAssignableFrom(type))
					{
						return num + 3515777162U != 0U;
					}
					num ^= 169943300U;
				}
				return (num ^ 609246835U) != 0U;
			}
		}
		return (num ^ 2080339560U) != 0U;
	}

	// Token: 0x060001F7 RID: 503 RVA: 0x002FB0B4 File Offset: 0x0017B4B4
	private void 6F321127(Exception 2FA62E16)
	{
		uint num;
		for (;;)
		{
			IL_00:
			this.02C11BDF.Clear();
			num = 57287863U;
			097E1C51.54B80CE2 54B80CE2;
			for (;;)
			{
				IL_12:
				num |= 1262498813U;
				Stack<int> stack = this.1D9D066A;
				num /= 691625760U;
				stack.Clear();
				if ((num ^ 1822576369U) == 0U)
				{
					goto IL_00;
				}
				for (;;)
				{
					IL_3E:
					bool flag = this.29231AE3 != null;
					num = 890532535U / num;
					if (!flag)
					{
						num = 1804218505U - num;
						goto IL_5D;
					}
					for (;;)
					{
						IL_5D2:
						num <<= 0;
						Stack<097E1C51.5C236423> stack2 = this.77FC1BC6;
						num >>= 12;
						bool count = stack2.Count != 0;
						num = 2026902738U + num;
						if (!count)
						{
							goto Block_29;
						}
						num = 1976193946U;
						List<097E1C51.54B80CE2> list = this.77FC1BC6.Peek().75F02B24();
						num |= 1608269543U;
						bool flag2 = this.29231AE3 != null;
						num /= 112275912U;
						int num3;
						if (flag2)
						{
							if (num == 1074136858U)
							{
								goto IL_00;
							}
							int num2 = list.IndexOf(this.29231AE3);
							num *= 375004835U;
							num3 = num2 + (int)(num - 2830124568U);
						}
						else
						{
							num %= 1603295542U;
							num3 = (int)(num + 4294967277U);
							num += 2830124550U;
						}
						this.29231AE3 = null;
						int num4 = num3;
						if (638409733U + num == 0U)
						{
							goto IL_3E;
						}
						for (;;)
						{
							num |= 888999871U;
							int num5 = num4;
							num |= 2112952602U;
							List<097E1C51.54B80CE2> list2 = list;
							num = 1302821383U * num;
							if (num5 >= list2.Count)
							{
								break;
							}
							num = 823402968U;
							List<097E1C51.54B80CE2> list3 = list;
							num += 171644101U;
							097E1C51.54B80CE2 54B80CE = list3[num4];
							num -= 1005418474U;
							54B80CE2 = 54B80CE;
							num ^= 239602197U;
							if (249443280U == num)
							{
								goto IL_00;
							}
							097E1C51.54B80CE2 54B80CE3 = 54B80CE2;
							num += 1014631384U;
							byte b = 54B80CE3.35B577B7();
							num = 1261964084U / num;
							if (1924680533U <= num)
							{
								goto IL_00;
							}
							bool flag3 = b != 0;
							num &= 420550027U;
							if (flag3)
							{
								uint num6 = (uint)b;
								num -= 885071000U;
								if (num6 == (num ^ 3409896296U))
								{
									goto IL_308;
								}
							}
							else
							{
								num = 1950827136U + num;
								Type type = 2FA62E16.GetType();
								num = 2009623956U * num;
								Type type2 = type;
								num = 296550491U << (int)num;
								num *= 440075406U;
								int 48092E = 54B80CE2.19354B25();
								num = (925906858U | num);
								Type type3 = this.01581CD6(48092E);
								num ^= 1557017229U;
								Type type4 = type3;
								num += 1453550784U;
								Type type5 = type2;
								num &= 1803050600U;
								if (type5 == type4)
								{
									goto IL_257;
								}
								num = 881075945U + num;
								Type type6 = type2;
								Type c = type4;
								num <<= 16;
								bool flag4 = type6.IsSubclassOf(c);
								num %= 1571423001U;
								num += 2980046466U;
								if (flag4)
								{
									goto Block_9;
								}
							}
							if (1381717004U == num)
							{
								goto IL_00;
							}
							int num7 = num4;
							int num8 = (int)(num - 3409896296U);
							num = (1216500560U ^ num);
							num4 = num7 + num8;
							num += 619943392U;
						}
						num = 225579316U << (int)num;
						this.77FC1BC6.Pop();
						num *= 1539274108U;
						num = 367863385U >> (int)num;
						if ((1465876080U ^ num) == 0U)
						{
							goto IL_00;
						}
						int num9 = list.Count;
						for (;;)
						{
							num += 1847018587U;
							if (num >> 4 == 0U)
							{
								goto IL_00;
							}
							uint num10 = (uint)num9;
							num = (2124507907U ^ num);
							if (num10 <= num - 4205117879U)
							{
								break;
							}
							num = 2029523016U;
							if ((num ^ 857689942U) != 0U)
							{
								List<097E1C51.54B80CE2> list4 = list;
								int num11 = num9;
								num = 763429832U * num;
								int num12 = (int)(num ^ 1178964033U);
								num = 1770213029U + num;
								097E1C51.54B80CE2 54B80CE4 = list4[num11 - num12];
								num |= 459825056U;
								if ((uint)54B80CE4.35B577B7() == (num ^ 3219709927U))
								{
									goto IL_4D1;
								}
								if (41373674U >= num)
								{
									goto IL_5D;
								}
								097E1C51.54B80CE2 54B80CE5 = 54B80CE4;
								num ^= 2136755245U;
								if ((uint)54B80CE5.35B577B7() == num - 3233059780U)
								{
									num += 4281617437U;
									goto IL_4D1;
								}
								IL_50A:
								int num13 = num9;
								num = 861740573U << (int)num;
								num9 = num13 - (int)(num ^ 1562254593U);
								num += 3100576089U;
								continue;
								IL_4D1:
								num %= 1062548487U;
								Stack<int> stack3 = this.1D9D066A;
								097E1C51.54B80CE2 54B80CE6 = 54B80CE4;
								num = 1690575609U % num;
								int item = 54B80CE6.2AED5988();
								num %= 2070372186U;
								stack3.Push(item);
								num ^= 3252080497U;
								goto IL_50A;
							}
							goto IL_12;
						}
						if ((1014918935U ^ num) == 0U)
						{
							goto IL_12;
						}
						num &= 1377462038U;
						bool count2 = this.1D9D066A.Count != 0;
						num /= 1664156575U;
						num += 890532535U;
						if (count2)
						{
							goto Block_27;
						}
					}
					IL_257:
					num *= 705652652U;
					if (num == 683742454U)
					{
						goto IL_00;
					}
					this.77FC1BC6.Pop();
					num &= 134751199U;
					num *= 518204672U;
					if (334065873U == num)
					{
						goto IL_00;
					}
					Stack<097E1C51.4A781DBA> stack4 = this.02C11BDF;
					097E1C51.4A781DBA item2 = new 097E1C51.7B0B79E5(this.1F3D4082);
					num *= 1162296954U;
					stack4.Push(item2);
					num -= 964966470U;
					if ((516912653U & num) != 0U)
					{
						goto Block_12;
					}
					continue;
					Block_9:
					num ^= 3374232329U;
					goto IL_257;
					IL_5D:
					this.1F3D4082 = 2FA62E16;
					num ^= 56741733U;
					goto IL_5D2;
				}
				IL_308:
				if ((1429821781U ^ num) != 0U)
				{
					097E1C51.54B80CE2 54B80CE7 = 54B80CE2;
					num = (310859645U ^ num);
					this.29231AE3 = 54B80CE7;
					num = 1258750988U + num;
					if (num != 63926192U)
					{
						goto Block_15;
					}
				}
			}
			Block_12:
			num = (1809787186U & num);
			097E1C51.54B80CE2 54B80CE8 = 54B80CE2;
			num *= 1272609725U;
			int num14 = 54B80CE8.2AED5988();
			num = (138949445U | num);
			this.14EA7BFD = num14;
			if (714351422U * num != 0U)
			{
				break;
			}
			continue;
			Block_15:
			num = 598223644U + num;
			Stack<097E1C51.4A781DBA> stack5 = this.02C11BDF;
			num = (1199138273U | num);
			stack5.Push(new 097E1C51.7B0B79E5(this.1F3D4082));
			num <<= 25;
			097E1C51.54B80CE2 54B80CE9 = 54B80CE2;
			num %= 822484657U;
			int num15 = 54B80CE9.19354B25();
			num |= 2014521673U;
			this.14EA7BFD = num15;
			if ((num ^ 1111230791U) != 0U)
			{
				return;
			}
			continue;
			Block_27:
			if ((175390343U ^ num) != 0U)
			{
				goto Block_28;
			}
			continue;
			Block_29:
			if (num != 225781044U)
			{
				goto Block_30;
			}
		}
		return;
		Block_28:
		num = 165040767U >> (int)num;
		Stack<int> stack6 = this.1D9D066A;
		num >>= 14;
		this.14EA7BFD = stack6.Pop();
		return;
		Block_30:
		throw 2FA62E16;
	}

	// Token: 0x060001F8 RID: 504 RVA: 0x002FB6D0 File Offset: 0x0017BAD0
	private void 4C0D7078()
	{
		uint num = 2027422682U;
		097E1C51.0A706168 0A = this.2734307D();
		num *= 1247687934U;
		int 48092E = 0A.B9179A8D();
		num = 178286522U % num;
		Type 5B942D4A = this.01581CD6(48092E);
		num *= 1162484677U;
		097E1C51.0A706168 0A5;
		do
		{
			num = 123747885U << (int)num;
			097E1C51.0A706168 0A2 = this.2734307D();
			num &= 1480879668U;
			if (num % 101125494U != 0U)
			{
				num *= 1409950046U;
				097E1C51.0A706168 0A3 = this.2734307D();
				num = 1342000797U * num;
				object 169A670F = 0A3.04F0F15D();
				num = 1315663727U >> (int)num;
				097E1C51.0A706168 0A4 = this.592B4570(169A670F, 5B942D4A);
				num = 43722344U << (int)num;
				0A5 = 0A4;
			}
			097E1C51.0A706168 0A6 = 0A2;
			num /= 943267991U;
			if (0A6.3CF78257())
			{
				097E1C51.0A706168 58352FC = 0A5;
				097E1C51.0A706168 59482B = 0A2;
				num = 1586113253U * num;
				0A5 = new 097E1C51.6FD1772C(58352FC, 59482B);
				num ^= 463372460U;
			}
		}
		while (909141241 << (int)num == 0);
		num = 1918107805U % num;
		this.53780069.Add(0A5);
	}

	// Token: 0x060001F9 RID: 505 RVA: 0x002FB7C8 File Offset: 0x0017BBC8
	private void 1D973A08()
	{
		uint num = 1356012222U;
		int num3;
		do
		{
			097E1C51.0A706168 0A = this.2734307D();
			num = 2022198298U >> (int)num;
			int num2 = 0A.B9179A8D();
			num %= 578687494U;
			num3 = num2;
			num *= 1736209215U;
		}
		while ((1554068201U ^ num) == 0U);
		num = (1980051945U & num);
		List<097E1C51.5C236423>.Enumerator enumerator = this.652B3F15.GetEnumerator();
		try
		{
			if (2088790746U != num)
			{
			}
			IL_C0:
			while (75449103U == num || enumerator.MoveNext())
			{
				num = 1361133668U;
				097E1C51.5C236423 5C = enumerator.Current;
				if (1177908279U >> (int)num != 0U)
				{
					int num4 = 5C.23AA1DEA();
					int num5 = num3;
					num = (1917286993U & num);
					num += 369291497U;
					if (num4 == num5)
					{
						num >>= 3;
						num <<= 19;
						this.77FC1BC6.Push(5C);
						num ^= 1462513961U;
					}
				}
			}
			return;
			goto IL_C0;
		}
		finally
		{
			do
			{
				num = 997741255U;
				((IDisposable)enumerator).Dispose();
			}
			while (124216998U >> (int)num == 0U);
		}
	}

	// Token: 0x060001FA RID: 506 RVA: 0x002FB8F4 File Offset: 0x0017BCF4
	private void 6075045E()
	{
		uint num = 1795886308U;
		if (199432255U < num)
		{
			num *= 274100455U;
			097E1C51.0A706168 7A012B = new 097E1C51.14C26E2D(this.293B3F40());
			num += 552165524U;
			this.1B04365B(7A012B);
		}
	}

	// Token: 0x060001FB RID: 507 RVA: 0x002FB934 File Offset: 0x0017BD34
	private void 73C37226()
	{
		uint num = 2027699542U;
		num &= 699421672U;
		long 6DB81B = this.0F8515AE();
		num /= 1275399191U;
		097E1C51.0A706168 7A012B = new 097E1C51.31EF1BAE(6DB81B);
		num = (731018500U | num);
		this.1B04365B(7A012B);
	}

	// Token: 0x060001FC RID: 508 RVA: 0x002FB970 File Offset: 0x0017BD70
	private void 377273A1()
	{
		uint num;
		do
		{
			float 52F83C3E = this.547B41AA();
			num = 577076261U;
			097E1C51.0A706168 7A012B = new 097E1C51.7B032626(52F83C3E);
			num *= 1677004064U;
			this.1B04365B(7A012B);
		}
		while (1588029443U > num);
	}

	// Token: 0x060001FD RID: 509 RVA: 0x002FB9A8 File Offset: 0x0017BDA8
	private void 728D144D()
	{
		uint num = 79574733U;
		double 25B00FBF = this.650B430D();
		num -= 1460683687U;
		097E1C51.0A706168 7A012B = new 097E1C51.4DA65608(25B00FBF);
		num += 2043894636U;
		this.1B04365B(7A012B);
	}

	// Token: 0x060001FE RID: 510 RVA: 0x002FB9DC File Offset: 0x0017BDDC
	private void 68B23F03()
	{
		this.1B04365B(new 097E1C51.7B0B79E5(null));
	}

	// Token: 0x060001FF RID: 511 RVA: 0x002FB9F8 File Offset: 0x0017BDF8
	private void 004273E4()
	{
		uint num;
		do
		{
			num = 1614680170U;
			num *= 968831616U;
			this.1B04365B(new 097E1C51.703962A5(this.7C500D12(this.2734307D().B9179A8D())));
		}
		while (num > 543521307U);
	}

	// Token: 0x06000200 RID: 512 RVA: 0x002FBA3C File Offset: 0x0017BE3C
	private void 270E06CA()
	{
		uint num = 1221750736U;
		if (1654677288U >= num)
		{
			do
			{
				num |= 403653278U;
				097E1C51.0A706168 0A = this.53D3742F();
				num -= 1608810089U;
				097E1C51.0A706168 7A012B = 0A.7A52F208();
				num = 1602319432U * num;
				this.1B04365B(7A012B);
			}
			while (num == 1519478580U);
		}
	}

	// Token: 0x06000201 RID: 513 RVA: 0x002FBA90 File Offset: 0x0017BE90
	private void 51997215()
	{
		uint num = 4787724U;
		097E1C51.0A706168 0A;
		if (num < 847868112U)
		{
			do
			{
				num += 269881672U;
				0A = this.2734307D();
				num -= 1637305716U;
			}
			while (1685943313U > num);
		}
		num -= 666115170U;
		097E1C51.0A706168 0A2 = this.2734307D();
		num = 1415392677U % num;
		097E1C51.0A706168 0A3 = 0A2;
		do
		{
			num += 1049251895U;
			num &= 1267082339U;
			097E1C51.0A706168 381A12FC = 0A;
			097E1C51.0A706168 6E255BAF = 0A3;
			bool 0EAD58CE = num + 4252621760U != 0U;
			bool 0F4D = num + 4252621760U != 0U;
			num *= 1751129689U;
			this.1B04365B(this.08C207B3(381A12FC, 6E255BAF, 0EAD58CE, 0F4D));
		}
		while ((1875844334U & num) == 0U);
	}

	// Token: 0x06000202 RID: 514 RVA: 0x002FBB28 File Offset: 0x0017BF28
	private void 111A690C()
	{
		097E1C51.0A706168 0A;
		uint num;
		097E1C51.0A706168 0A3;
		do
		{
			0A = this.2734307D();
			num = 1586714260U;
			097E1C51.0A706168 0A2 = this.2734307D();
			num = 732451169U >> (int)num;
			0A3 = 0A2;
			num *= 2052064672U;
		}
		while (2110335603U - num == 0U);
		num %= 16517400U;
		097E1C51.0A706168 381A12FC = 0A;
		097E1C51.0A706168 6E255BAF = 0A3;
		num >>= 27;
		bool 0EAD58CE = (num ^ 1U) != 0U;
		bool 0F4D = num + 0U != 0U;
		num |= 1667965214U;
		097E1C51.0A706168 7A012B = this.08C207B3(381A12FC, 6E255BAF, 0EAD58CE, 0F4D);
		num = 1305554129U >> (int)num;
		this.1B04365B(7A012B);
	}

	// Token: 0x06000203 RID: 515 RVA: 0x002FBBB0 File Offset: 0x0017BFB0
	private void 5EE86E2F()
	{
		uint num = 818299963U;
		for (;;)
		{
			097E1C51.0A706168 0A = this.2734307D();
			num = (907035625U | num);
			097E1C51.0A706168 0A2 = 0A;
			num |= 231831956U;
			097E1C51.0A706168 0A3 = this.2734307D();
			if (894635391U <= num)
			{
				097E1C51.0A706168 381A12FC = 0A2;
				num -= 1934713285U;
				097E1C51.0A706168 6E255BAF = 0A3;
				bool 0EAD58CE = (num ^ 3431341627U) != 0U;
				bool 0F4D = num - 3431341625U != 0U;
				num /= 1423342071U;
				this.1B04365B(this.08C207B3(381A12FC, 6E255BAF, 0EAD58CE, 0F4D));
				if (num != 844841733U)
				{
					break;
				}
			}
		}
	}

	// Token: 0x06000204 RID: 516 RVA: 0x002FBC24 File Offset: 0x0017C024
	private void 6FFA1529()
	{
		uint num;
		do
		{
			num = 1376260088U;
			097E1C51.0A706168 0A = this.2734307D();
			097E1C51.0A706168 0A2;
			do
			{
				0A2 = this.2734307D();
			}
			while (1582709951U - num == 0U);
			num = 1963462582U << (int)num;
			097E1C51.0A706168 240D6E = 0A2;
			097E1C51.0A706168 5F6A540D = 0A;
			bool 379344A = (num ^ 3053453312U) != 0U;
			num *= 643439295U;
			097E1C51.0A706168 7A012B = this.28D77A74(240D6E, 5F6A540D, 379344A, (num ^ 3388997632U) != 0U);
			num = 127294532U / num;
			this.1B04365B(7A012B);
		}
		while (num << 17 != 0U);
	}

	// Token: 0x06000205 RID: 517 RVA: 0x002FBC98 File Offset: 0x0017C098
	private void 39971548()
	{
		uint num = 290024949U;
		for (;;)
		{
			num *= 44721630U;
			097E1C51.0A706168 0A = this.2734307D();
			num += 1316496778U;
			for (;;)
			{
				097E1C51.0A706168 0A2 = this.2734307D();
				num = 33242959U >> (int)num;
				097E1C51.0A706168 0A3 = 0A2;
				num /= 83065535U;
				if (num - 1900503120U == 0U)
				{
					break;
				}
				097E1C51.0A706168 240D6E = 0A3;
				num >>= 21;
				097E1C51.0A706168 5F6A540D = 0A;
				num *= 183370310U;
				bool 379344A = num + 1U != 0U;
				bool 42AD = num + 0U != 0U;
				num = (1749553370U | num);
				097E1C51.0A706168 7A012B = this.28D77A74(240D6E, 5F6A540D, 379344A, 42AD);
				num ^= 1830239976U;
				this.1B04365B(7A012B);
				if (num + 767061759U != 0U)
				{
					return;
				}
			}
		}
	}

	// Token: 0x06000206 RID: 518 RVA: 0x002FBD34 File Offset: 0x0017C134
	private void 58773899()
	{
		uint num = 1103192167U;
		if (447555328 << (int)num == 0)
		{
			goto IL_27;
		}
		IL_18:
		num = 1018318321U * num;
		097E1C51.0A706168 0A = this.2734307D();
		IL_27:
		097E1C51.0A706168 0A2 = this.2734307D();
		num *= 1845836582U;
		097E1C51.0A706168 0A3 = 0A2;
		num = 1757112974U << (int)num;
		if (num != 142369824U)
		{
			num %= 1146569017U;
			num &= 1842508734U;
			097E1C51.0A706168 240D6E = 0A3;
			num = (173352782U ^ num);
			097E1C51.0A706168 5F6A540D = 0A;
			num = 3474283U % num;
			this.1B04365B(this.28D77A74(240D6E, 5F6A540D, num - 3474282U != 0U, num + 4291493014U != 0U));
			if (num == 1807287547U)
			{
				goto IL_18;
			}
		}
	}

	// Token: 0x06000207 RID: 519 RVA: 0x002FBDD8 File Offset: 0x0017C1D8
	private void 55D55526()
	{
		uint num = 2113305891U;
		do
		{
			num = 796467839U + num;
			097E1C51.0A706168 0A = this.2734307D();
			num += 1255964592U;
			097E1C51.0A706168 0A2 = 0A;
			097E1C51.0A706168 0A3;
			do
			{
				num -= 204816411U;
				0A3 = this.2734307D();
				num = 1693986808U / num;
			}
			while (num * 1556566956U != 0U);
			097E1C51.0A706168 4A1F7FA = 0A3;
			num -= 167403388U;
			097E1C51.0A706168 152620E = 0A2;
			num = 1956408493U % num;
			bool 0D517AF = num + 2338558803U != 0U;
			num = 743840348U % num;
			bool 3392768A = num + 3551126948U != 0U;
			num = (645138209U ^ num);
			this.1B04365B(this.79AD2645(4A1F7FA, 152620E, 0D517AF, 3392768A));
		}
		while (2045461109U >> (int)num == 0U);
	}

	// Token: 0x06000208 RID: 520 RVA: 0x002FBE74 File Offset: 0x0017C274
	private void 625228E8()
	{
		uint num = 829841080U;
		097E1C51.0A706168 0A2;
		do
		{
			097E1C51.0A706168 0A = this.2734307D();
			num = 362877588U - num;
			0A2 = 0A;
			num /= 484520596U;
		}
		while (num >= 50070022U);
		097E1C51.0A706168 0A3 = this.2734307D();
		num = (691286351U ^ num);
		097E1C51.0A706168 0A4 = 0A3;
		num = (1446726869U | num);
		097E1C51.0A706168 4A1F7FA = 0A4;
		097E1C51.0A706168 152620E = 0A2;
		num = 1716917217U >> (int)num;
		097E1C51.0A706168 7A012B = this.79AD2645(4A1F7FA, 152620E, num + 4294967294U != 0U, (num ^ 3U) != 0U);
		num &= 1201620751U;
		this.1B04365B(7A012B);
	}

	// Token: 0x06000209 RID: 521 RVA: 0x002FBEF4 File Offset: 0x0017C2F4
	private void 1C8B7660()
	{
		uint num = 810578745U;
		if (num == 558174688U)
		{
			goto IL_42;
		}
		097E1C51.0A706168 0A2;
		do
		{
			IL_11:
			num = (1750663393U ^ num);
			097E1C51.0A706168 0A = this.2734307D();
			num = 2089042768U << (int)num;
			0A2 = 0A;
			num = (1416379118U | num);
		}
		while ((1129279612U ^ num) == 0U);
		IL_42:
		097E1C51.0A706168 0A3 = this.2734307D();
		num = 2029862229U - num;
		097E1C51.0A706168 4A1F7FA = 0A3;
		num %= 1413959306U;
		097E1C51.0A706168 152620E = 0A2;
		bool 0D517AF = num - 613483110U != 0U;
		bool 3392768A = (num ^ 613483110U) != 0U;
		num <<= 26;
		097E1C51.0A706168 7A012B = this.79AD2645(4A1F7FA, 152620E, 0D517AF, 3392768A);
		num = (141252394U | num);
		this.1B04365B(7A012B);
		if (num != 1131766317U)
		{
			return;
		}
		goto IL_11;
	}

	// Token: 0x0600020A RID: 522 RVA: 0x002FBF94 File Offset: 0x0017C394
	private void 69D76EFE()
	{
		097E1C51.0A706168 0A;
		uint num;
		097E1C51.0A706168 0A2;
		for (;;)
		{
			0A = this.2734307D();
			num = 1383366652U;
			if (num - 1601638241U != 0U)
			{
				num *= 328229613U;
				0A2 = this.2734307D();
				num = 2133667950U - num;
				if (num << 29 != 0U)
				{
					break;
				}
			}
		}
		do
		{
			num = (1039226356U ^ num);
			num += 369327853U;
			097E1C51.0A706168 52FC4A = 0A2;
			097E1C51.0A706168 06744F = 0A;
			bool 10D77F = (num ^ 4184402115U) != 0U;
			num = (355953485U & num);
			this.1B04365B(this.4E62064B(52FC4A, 06744F, 10D77F));
		}
		while (1801614534U < num);
	}

	// Token: 0x0600020B RID: 523 RVA: 0x002FC018 File Offset: 0x0017C418
	private void 598E21C0()
	{
		uint num = 74585091U;
		097E1C51.0A706168 0A;
		if (735194591U * num != 0U)
		{
			num |= 807740955U;
			0A = this.2734307D();
			num = (1280115633U ^ num);
		}
		num <<= 0;
		097E1C51.0A706168 0A2 = this.2734307D();
		num &= 1135110332U;
		097E1C51.0A706168 0A3 = 0A2;
		if (num != 481042266U)
		{
			num = 970487916U >> (int)num;
			num >>= 28;
			097E1C51.0A706168 52FC4A = 0A3;
			num ^= 1788826018U;
			097E1C51.0A706168 06744F = 0A;
			bool 10D77F = (num ^ 1788826019U) != 0U;
			num = 1689394060U * num;
			097E1C51.0A706168 7A012B = this.4E62064B(52FC4A, 06744F, 10D77F);
			num = 1531714350U >> (int)num;
			this.1B04365B(7A012B);
		}
	}

	// Token: 0x0600020C RID: 524 RVA: 0x002FC0BC File Offset: 0x0017C4BC
	private void 0FB617A4()
	{
		uint num = 1700542789U;
		097E1C51.0A706168 0A = this.2734307D();
		num >>= 21;
		num = (1771391796U & num);
		097E1C51.0A706168 0A2 = this.2734307D();
		num &= 2082277758U;
		097E1C51.0A706168 0A3 = 0A2;
		num >>= 16;
		097E1C51.0A706168 43670FA = 0A3;
		097E1C51.0A706168 4C76345A = 0A;
		num = (1955279936U | num);
		bool 58B = (num ^ 1955279936U) != 0U;
		num = 1764840058U * num;
		this.1B04365B(this.600357F0(43670FA, 4C76345A, 58B));
	}

	// Token: 0x0600020D RID: 525 RVA: 0x002FC124 File Offset: 0x0017C524
	private void 5A1954EC()
	{
		097E1C51.0A706168 0A;
		uint num;
		097E1C51.0A706168 0A3;
		do
		{
			0A = this.2734307D();
			097E1C51.0A706168 0A2 = this.2734307D();
			num = 705899913U;
			0A3 = 0A2;
			num <<= 10;
		}
		while (num < 811103611U);
		do
		{
			097E1C51.0A706168 43670FA = 0A3;
			097E1C51.0A706168 4C76345A = 0A;
			num = (718626529U ^ num);
			bool 58B = (num ^ 1717795552U) != 0U;
			num = (1890782903U & num);
			097E1C51.0A706168 7A012B = this.600357F0(43670FA, 4C76345A, 58B);
			num = (1171074554U ^ num);
			this.1B04365B(7A012B);
		}
		while (num == 120553130U);
	}

	// Token: 0x0600020E RID: 526 RVA: 0x002FC190 File Offset: 0x0017C590
	private void 6B0F6D44()
	{
		uint num = 815738321U;
		for (;;)
		{
			num <<= 26;
			097E1C51.0A706168 0A = this.2734307D();
			num = 1397761311U + num;
			097E1C51.0A706168 4DA956F = 0A;
			if (568215860U != num)
			{
				097E1C51.0A706168 696F045D = this.2734307D();
				num = 887293516U + num;
				if (1288646897U >> (int)num == 0U)
				{
					break;
				}
				num = 1805673148U >> (int)num;
				097E1C51.0A706168 7A012B = this.03A93C18(696F045D, 4DA956F);
				num = 1491882790U + num;
				this.1B04365B(7A012B);
				if (614089980U >> (int)num != 0U)
				{
					break;
				}
			}
		}
	}

	// Token: 0x0600020F RID: 527 RVA: 0x002FC21C File Offset: 0x0017C61C
	private void 4F7A0A45()
	{
		uint num = 1435709786U;
		if (1061951799U != num)
		{
			097E1C51.0A706168 6E0374D;
			097E1C51.0A706168 0A2;
			for (;;)
			{
				num = 968320411U >> (int)num;
				097E1C51.0A706168 0A = this.2734307D();
				num -= 1886003695U;
				6E0374D = 0A;
				if (num + 1561410933U != 0U)
				{
					num = 146547916U * num;
					0A2 = this.2734307D();
					if (num - 448294189U != 0U)
					{
						break;
					}
				}
			}
			num = (1702106540U | num);
			097E1C51.0A706168 <<EMPTY_NAME>> = 0A2;
			num |= 1275535096U;
			this.1B04365B(this.5BA918D5(<<EMPTY_NAME>>, 6E0374D));
		}
	}

	// Token: 0x06000210 RID: 528 RVA: 0x002FC29C File Offset: 0x0017C69C
	private void 21CC7EF0()
	{
		uint num = 660160792U;
		num %= 526538783U;
		097E1C51.0A706168 371D7B = this.2734307D();
		097E1C51.0A706168 037924A;
		if (num < 674642915U)
		{
			do
			{
				037924A = this.2734307D();
			}
			while (1677939439U <= num);
		}
		num *= 980253595U;
		this.1B04365B(this.049A163C(037924A, 371D7B));
	}

	// Token: 0x06000211 RID: 529 RVA: 0x002FC2F4 File Offset: 0x0017C6F4
	private void 4A0B1810()
	{
		uint num = 125130385U;
		do
		{
			097E1C51.0A706168 0A = this.2734307D();
			num |= 561413702U;
			if (num >= 1585670332U)
			{
				break;
			}
			num /= 12604650U;
			097E1C51.0A706168 106210F = 0A;
			num = (531067431U & num);
			this.1B04365B(this.77346840(106210F));
		}
		while (num > 1186423261U);
	}

	// Token: 0x06000212 RID: 530 RVA: 0x002FC34C File Offset: 0x0017C74C
	private void 5B3318A6()
	{
		uint num;
		do
		{
			num = 861808638U;
			097E1C51.0A706168 0A = this.2734307D();
			num = 1359178290U % num;
			097E1C51.0A706168 613D36CD = 0A;
			num = (451830391U | num);
			097E1C51.0A706168 7A012B = this.3AA4054C(613D36CD);
			num ^= 677194439U;
			this.1B04365B(7A012B);
		}
		while (1904043112U < num);
	}

	// Token: 0x06000213 RID: 531 RVA: 0x002FC398 File Offset: 0x0017C798
	private void 60640AD4()
	{
		097E1C51.0A706168 38291A = this.2734307D();
		uint num = 1237658709U;
		if (203125583U < num)
		{
			num = (1449724007U ^ num);
			097E1C51.0A706168 0A = this.2734307D();
			num |= 243497636U;
			097E1C51.0A706168 0A2 = 0A;
			num -= 1479170382U;
			if ((num ^ 278741442U) != 0U)
			{
				num = 1548515681U / num;
				num = 339768162U - num;
				097E1C51.0A706168 6DA = 0A2;
				num >>= 30;
				this.1B04365B(this.088D1B0E(6DA, 38291A, (num ^ 0U) != 0U));
			}
		}
	}

	// Token: 0x06000214 RID: 532 RVA: 0x002FC418 File Offset: 0x0017C818
	private void 40BB10D3()
	{
		097E1C51.0A706168 38291A = this.2734307D();
		uint num = 1681874326U;
		if (num > 73940969U)
		{
			097E1C51.0A706168 0A;
			do
			{
				0A = this.2734307D();
				num = 583869665U + num;
			}
			while (144210533U - num == 0U);
			do
			{
				num = 66354092U - num;
				097E1C51.0A706168 6DA = 0A;
				num ^= 768148596U;
				097E1C51.0A706168 7A012B = this.088D1B0E(6DA, 38291A, (num ^ 1362033984U) != 0U);
				num = (114451580U & num);
				this.1B04365B(7A012B);
			}
			while (1855531032U - num == 0U);
		}
	}

	// Token: 0x06000215 RID: 533 RVA: 0x002FC494 File Offset: 0x0017C894
	private void 56CF7F02()
	{
		097E1C51.0A706168 0A = this.2734307D();
		uint num = 905463535U;
		097E1C51.0A706168 0A2 = this.2734307D();
		num = 1325860932U + num;
		097E1C51.0A706168 0A3 = 0A2;
		if (num + 1165048372U != 0U)
		{
			num = 1238370292U >> (int)num;
			097E1C51.0A706168 15BF672E = 0A3;
			097E1C51.0A706168 594B55AA = 0A;
			num -= 977422667U;
			this.1B04365B(this.7CEC00A6(15BF672E, 594B55AA));
		}
	}

	// Token: 0x06000216 RID: 534 RVA: 0x002FC4F0 File Offset: 0x0017C8F0
	private void 011F0F15()
	{
		uint num;
		do
		{
			num = 1444236796U;
			num <<= 3;
			097E1C51.0A706168 0A = this.2734307D();
			num -= 2054642041U;
			int 48092E = 0A.B9179A8D();
			num = 516059380U << (int)num;
			Type type = this.01581CD6(48092E);
			num = 625085269U % num;
			Type 5B942D4A = type;
			num = 975249643U % num;
			num ^= 122228204U;
			object 169A670F = this.2734307D();
			num *= 1366181558U;
			this.1B04365B(this.592B4570(169A670F, 5B942D4A));
		}
		while (num > 591089443U);
	}

	// Token: 0x06000217 RID: 535 RVA: 0x002FC574 File Offset: 0x0017C974
	private void 5AFD3DDB()
	{
		uint num = 425019741U;
		097E1C51.0A706168 0A = this.2734307D();
		num &= 1964918492U;
		int 48092E = 0A.B9179A8D();
		num = (1439571349U & num);
		Type type = this.01581CD6(48092E);
		num = 2105433777U << (int)num;
		Type type2 = type;
		num += 1065710252U;
		num = 519914165U + num;
		num ^= 1296514353U;
		num /= 948120120U;
		097E1C51.0A706168 0A2 = this.2734307D();
		Type 1EB = type2;
		num %= 1443571111U;
		bool <<EMPTY_NAME>> = (num ^ 0U) != 0U;
		num = 589961832U * num;
		object 169A670F = 0A2.9E2F1085(1EB, <<EMPTY_NAME>>);
		num += 1656164308U;
		Type 5B942D4A = type2;
		num -= 2094668273U;
		this.1B04365B(this.592B4570(169A670F, 5B942D4A));
	}

	// Token: 0x06000218 RID: 536 RVA: 0x002FC618 File Offset: 0x0017CA18
	private void 09B23045()
	{
		uint num = 396762263U;
		num >>= 2;
		int 48092E = this.2734307D().B9179A8D();
		num = 1805726971U << (int)num;
		Type type = this.01581CD6(48092E);
		num >>= 31;
		num ^= 797204283U;
		num -= 1772892423U;
		num -= 105318282U;
		097E1C51.0A706168 0A = this.2734307D();
		num = (702379443U ^ num);
		097E1C51.0A706168 7A012B = this.592B4570(0A.9E2F1085(type, num + 1773382888U != 0U), type);
		num = (1386682066U & num);
		this.1B04365B(7A012B);
	}

	// Token: 0x06000219 RID: 537 RVA: 0x002FC6A4 File Offset: 0x0017CAA4
	private void 27F7218F()
	{
		uint num = 1819244221U;
		do
		{
			Type t = this.01581CD6(this.293B3F40());
			num = (777265654U & num);
			097E1C51.0A706168 7A012B = new 097E1C51.14C26E2D(Marshal.SizeOf(t));
			num /= 1574044780U;
			this.1B04365B(7A012B);
		}
		while ((543912154U ^ num) == 0U);
	}

	// Token: 0x0600021A RID: 538 RVA: 0x002FC6F0 File Offset: 0x0017CAF0
	private unsafe void 69F22BB5()
	{
		uint num = 119482126U;
		Type type;
		097E1C51.0A706168 0A2;
		do
		{
			IL_06:
			type = this.01581CD6(this.2734307D().B9179A8D());
			num *= 173103733U;
			for (;;)
			{
				num ^= 1819553277U;
				097E1C51.0A706168 0A = this.2734307D();
				num = (1618628461U ^ num);
				0A2 = 0A;
				num = 162923855U % num;
				if (num - 550664953U != 0U)
				{
					for (;;)
					{
						097E1C51.0A706168 0A3 = 0A2;
						num = 1240417245U % num;
						if (0A3.3CF78257())
						{
							break;
						}
						if (num == 2028537122U)
						{
							goto IL_06;
						}
						097E1C51.0A706168 0A4 = 0A2;
						num ^= 1891518043U;
						object obj = 0A4.04F0F15D();
						num = 1842035895U % num;
						if (!(obj is Pointer))
						{
							goto IL_E3;
						}
						if (num * 279782132U == 0U)
						{
							goto IL_06;
						}
						object ptr = 0A2.04F0F15D();
						num %= 516239147U;
						void* value = Pointer.Unbox(ptr);
						num /= 310598676U;
						IntPtr 7595406D = new IntPtr(value);
						Type 3C0F = type;
						num &= 1535473769U;
						0A2 = new 097E1C51.2D2F5095(7595406D, 3C0F);
						if (887362440U >> (int)num != 0U)
						{
							goto Block_5;
						}
					}
					IL_FD:
					if ((2130070074U & num) != 0U)
					{
						goto Block_7;
					}
					continue;
					Block_5:
					num ^= 99950260U;
					goto IL_FD;
				}
			}
			IL_E3:
			num %= 272831357U;
		}
		while (num - 50141911U == 0U);
		throw new ArgumentException();
		Block_7:
		num = (1981961508U ^ num);
		num >>= 26;
		object 169A670F = 0A2;
		num = (542260279U & num);
		Type 5B942D4A = type;
		num |= 183570100U;
		097E1C51.0A706168 7A012B = this.592B4570(169A670F, 5B942D4A);
		num = (1877767455U | num);
		this.1B04365B(7A012B);
	}

	// Token: 0x0600021B RID: 539 RVA: 0x002FC83C File Offset: 0x0017CC3C
	private void 265604A2()
	{
		uint num = 1657218444U;
		FieldInfo fieldInfo;
		object obj;
		for (;;)
		{
			num = (475811219U | num);
			fieldInfo = this.672A664F(this.2734307D().B9179A8D());
			if (num << 6 != 0U)
			{
				num |= 223101621U;
				obj = this.2734307D().04F0F15D();
				num = 1197952637U + num;
				if (fieldInfo.IsStatic)
				{
					goto IL_83;
				}
				IL_53:
				bool flag = obj != null;
				num += 1979869259U;
				num ^= 4195326139U;
				if (!flag)
				{
					num = 1005806404U - num;
					if (num / 866942442U != 0U)
					{
						break;
					}
					continue;
				}
				IL_83:
				if (num - 164063147U != 0U)
				{
					goto Block_4;
				}
				goto IL_53;
			}
		}
		throw new NullReferenceException();
		Block_4:
		num %= 1558007259U;
		FieldInfo fieldInfo2 = fieldInfo;
		num <<= 26;
		object obj2 = obj;
		num = 306541910U - num;
		097E1C51.0A706168 7A012B = this.592B4570(fieldInfo2.GetValue(obj2), fieldInfo.FieldType);
		num = 513157003U * num;
		this.1B04365B(7A012B);
	}

	// Token: 0x0600021C RID: 540 RVA: 0x002FC914 File Offset: 0x0017CD14
	private void 155F5DB6()
	{
		uint num = 746332556U;
		int 65D139EE = this.2734307D().B9179A8D();
		num = 1822502007U - num;
		FieldInfo fieldInfo = this.672A664F(65D139EE);
		num /= 823805687U;
		object obj = this.2734307D().04F0F15D();
		num += 306932606U;
		object obj2 = obj;
		num = 138634055U / num;
		if (236788759U >= num)
		{
			FieldInfo fieldInfo2 = fieldInfo;
			num /= 321268060U;
			if (!fieldInfo2.IsStatic)
			{
				bool flag = obj2 != null;
				num ^= 0U;
				if (!flag)
				{
					num = (1711107088U & num);
					goto IL_78;
				}
			}
			num = 949240082U + num;
			FieldInfo 65E63B = fieldInfo;
			num = 2101966895U * num;
			this.1B04365B(new 097E1C51.23FD3942(65E63B, obj2));
			return;
		}
		IL_78:
		throw new NullReferenceException();
	}

	// Token: 0x0600021D RID: 541 RVA: 0x002FC9BC File Offset: 0x0017CDBC
	private void 5CB74094()
	{
		uint num = 1758603320U;
		097E1C51.0A706168 0A = this.2734307D();
		num = (467695762U ^ num);
		int 65D139EE = 0A.B9179A8D();
		num %= 1762399640U;
		FieldInfo fieldInfo = this.672A664F(65D139EE);
		num = 1372995803U / num;
		FieldInfo fieldInfo2 = fieldInfo;
		if (num >= 1482897159U)
		{
			goto IL_72;
		}
		IL_3B:
		num = (1314722264U ^ num);
		097E1C51.0A706168 0A2 = this.2734307D();
		IL_4A:
		num = 604776643U * num;
		object obj = this.2734307D().04F0F15D();
		num += 1950486084U;
		if (num - 742727958U == 0U)
		{
			goto IL_3B;
		}
		IL_72:
		bool isStatic = fieldInfo2.IsStatic;
		num = 118846489U * num;
		if (!isStatic)
		{
			num |= 1088694707U;
			if (762598561U != num)
			{
				do
				{
					bool flag = obj != null;
					num /= 150560589U;
					num += 775661704U;
					if (flag)
					{
						goto IL_C8;
					}
					num = (477049226U ^ num);
				}
				while (num + 124992051U == 0U);
				throw new NullReferenceException();
			}
			goto IL_4A;
		}
		IL_C8:
		FieldInfo fieldInfo3 = fieldInfo2;
		object obj2 = obj;
		num = 392972015U / num;
		num = (602102820U & num);
		object 169A670F = 0A2;
		num = 1807812342U + num;
		FieldInfo fieldInfo4 = fieldInfo2;
		num |= 1953779616U;
		Type fieldType = fieldInfo4.FieldType;
		num = 1243249217U + num;
		097E1C51.0A706168 0A3 = this.592B4570(169A670F, fieldType);
		num = 1141455197U * num;
		fieldInfo3.SetValue(obj2, 0A3.04F0F15D());
		if (1559512340U <= num)
		{
			return;
		}
		goto IL_4A;
	}

	// Token: 0x0600021E RID: 542 RVA: 0x002FCAE8 File Offset: 0x0017CEE8
	private void 778E5B5E()
	{
		uint num = 961877912U;
		097E1C51.0A706168 0A = this.2734307D();
		num = 592604486U % num;
		FieldInfo fieldInfo = this.672A664F(0A.B9179A8D());
		num = 1365663169U / num;
		097E1C51.0A706168 0A2;
		if ((1263745717U ^ num) != 0U)
		{
			0A2 = this.2734307D();
			num >>= 26;
		}
		FieldInfo fieldInfo2 = fieldInfo;
		num = 1989419728U - num;
		object obj = null;
		num = 991319430U * num;
		num -= 274365287U;
		object 169A670F = 0A2;
		num *= 105259332U;
		FieldInfo fieldInfo3 = fieldInfo;
		num &= 806623731U;
		object value = this.592B4570(169A670F, fieldInfo3.FieldType).04F0F15D();
		num /= 837682360U;
		fieldInfo2.SetValue(obj, value);
	}

	// Token: 0x0600021F RID: 543 RVA: 0x002FCB84 File Offset: 0x0017CF84
	private unsafe void 446A7FC1()
	{
		uint num;
		for (;;)
		{
			num = 1676497258U;
			num = (254635782U ^ num);
			Type type = this.01581CD6(this.2734307D().B9179A8D());
			num = 1110528933U + num;
			if (1838836848U >> (int)num != 0U)
			{
				goto IL_3A;
			}
			IL_41:
			num <<= 16;
			097E1C51.0A706168 0A = this.2734307D();
			num <<= 5;
			097E1C51.0A706168 0A2 = 0A;
			if (1974161718U < num)
			{
				continue;
			}
			097E1C51.0A706168 0A3 = 0A2;
			num = 2137465415U - num;
			if (!0A3.3CF78257())
			{
				if ((num & 2055345520U) == 0U)
				{
					goto IL_3A;
				}
				097E1C51.0A706168 0A4 = 0A2;
				num ^= 692612186U;
				bool flag = 0A4.04F0F15D() is Pointer;
				num ^= 1712795220U;
				if (!flag)
				{
					break;
				}
				num /= 156964905U;
				if (1494224466U < num)
				{
					continue;
				}
				097E1C51.0A706168 0A5 = 0A2;
				num >>= 28;
				object ptr = 0A5.04F0F15D();
				num <<= 11;
				void* value = Pointer.Unbox(ptr);
				num = 1751475158U << (int)num;
				097E1C51.0A706168 0A6 = new 097E1C51.2D2F5095(new IntPtr(value), type);
				num = 1489638928U % num;
				0A2 = 0A6;
				if ((num ^ 367345619U) == 0U)
				{
					continue;
				}
				num ^= 1703752791U;
			}
			num >>= 9;
			097E1C51.0A706168 0A7 = 0A2;
			num = 457792998U >> (int)num;
			097E1C51.0A706168 0A8;
			object 169A670F = 0A8;
			num += 717232411U;
			Type 5B942D4A = type;
			num = 1143633083U % num;
			0A7.360F4E2C(this.592B4570(169A670F, 5B942D4A).04F0F15D());
			if (100617779U * num != 0U)
			{
				return;
			}
			IL_3A:
			0A8 = this.2734307D();
			goto IL_41;
		}
		num = (198856873U | num);
		throw new ArgumentException();
	}

	// Token: 0x06000220 RID: 544 RVA: 0x002FCCF0 File Offset: 0x0017D0F0
	private void 0E797388()
	{
		uint num = 1198929085U;
		do
		{
			num = (583760123U | num);
			List<097E1C51.0A706168> list = this.53780069;
			num &= 1788105556U;
			int index = (int)((ushort)this.197810C1());
			num = 261841953U << (int)num;
			this.1B04365B(list[index].7A52F208());
		}
		while (1219066749U + num == 0U);
	}

	// Token: 0x06000221 RID: 545 RVA: 0x002FCD4C File Offset: 0x0017D14C
	private void 566A6686()
	{
		uint num = 1590638224U;
		List<097E1C51.0A706168> list = this.53780069;
		num = 1230046366U / num;
		097E1C51.0A706168 7A012B = new 097E1C51.41091902(list[(int)((ushort)this.197810C1())]);
		num >>= 3;
		this.1B04365B(7A012B);
	}

	// Token: 0x06000222 RID: 546 RVA: 0x002FCD8C File Offset: 0x0017D18C
	private void 7F19411B()
	{
		uint num = 201800830U;
		097E1C51.0A706168 0A = this.2734307D();
		num = 1881172053U % num;
		097E1C51.0A706168 0A2 = 0A;
		do
		{
			List<097E1C51.0A706168> list = this.53780069;
			num &= 2072933341U;
			int index = (int)((ushort)this.197810C1());
			num = 1294760620U + num;
			097E1C51.0A706168 0A3 = list[index];
			097E1C51.0A706168 0A4 = 0A3;
			num = 1622875658U / num;
			object 169A670F = 0A2;
			097E1C51.0A706168 0A5 = 0A3;
			num = (99095621U ^ num);
			Type 5B942D4A = 0A5.5F93D4ED();
			num = 878266115U * num;
			097E1C51.0A706168 0A6 = this.592B4570(169A670F, 5B942D4A);
			num = 1070534023U + num;
			object 5076725B = 0A6.04F0F15D();
			num &= 1986950629U;
			0A4.360F4E2C(5076725B);
		}
		while (num > 962863455U);
	}

	// Token: 0x06000223 RID: 547 RVA: 0x002FCE1C File Offset: 0x0017D21C
	private void 649257A5()
	{
		uint num = 1567644644U;
		num = 2097512243U + num;
		num = 824792430U >> (int)num;
		097E1C51.0A706168 0A = this.2734307D();
		num = 1373466879U >> (int)num;
		int 48092E = 0A.B9179A8D();
		num = 309165191U >> (int)num;
		this.07131E52 = this.01581CD6(48092E);
	}

	// Token: 0x06000224 RID: 548 RVA: 0x002FCE78 File Offset: 0x0017D278
	private void 58373290()
	{
		uint num = 175386388U;
		do
		{
			097E1C51.0A706168 0A = this.2734307D();
			num = 1700413816U % num;
			MethodBase 716750DB = this.5D982D0E(0A.B9179A8D());
			097E1C51.0A706168 0A2 = this.544A548B(716750DB, num - 121936324U != 0U);
			num = (562331278U | num);
			097E1C51.0A706168 0A3 = 0A2;
			if (0A3 != null)
			{
				num %= 2146634493U;
				num <<= 23;
				097E1C51.0A706168 7A012B = 0A3;
				num = (988444521U | num);
				this.1B04365B(7A012B);
				num ^= 3626928295U;
			}
		}
		while (1650880275U < num);
	}

	// Token: 0x06000225 RID: 549 RVA: 0x002FCEF4 File Offset: 0x0017D2F4
	private void 130D57EB()
	{
		for (;;)
		{
			IL_00:
			uint num = 1575572320U;
			097E1C51.0A706168 0A = this.2734307D();
			num = 577375767U << (int)num;
			MethodBase methodBase = this.5D982D0E(0A.B9179A8D());
			num = 174014841U / num;
			for (;;)
			{
				num %= 1153700550U;
				Type[] array;
				int num5;
				ParameterInfo[] array2;
				if (this.07131E52 != null)
				{
					num = 1479153724U - num;
					ParameterInfo[] parameters = methodBase.GetParameters();
					num %= 1331897944U;
					int num2 = parameters.Length;
					num = 848914775U / num;
					int num3 = num2;
					num = 1239957936U / num;
					array = new Type[num3];
					num = (2040801954U & num);
					int num4 = (int)(num + 4152358878U);
					num >>= 27;
					num5 = num4;
					num >>= 8;
					array2 = parameters;
					goto IL_AA;
				}
				IL_273:
				num /= 1995052977U;
				if (623270875U >= num)
				{
					MethodBase 716750DB = methodBase;
					num &= 1913068447U;
					bool 09F64FAE = num + 1U != 0U;
					num <<= 13;
					097E1C51.0A706168 0A2 = this.544A548B(716750DB, 09F64FAE);
					bool flag = 0A2 != null;
					num += 1204099117U;
					if (flag)
					{
						num *= 733496455U;
						this.1B04365B(0A2);
						num ^= 1702275990U;
					}
					if (num % 1222709654U != 0U)
					{
						return;
					}
					continue;
				}
				IL_AA:
				int num6 = (int)(num + 0U);
				num |= 309552119U;
				int num7 = num6;
				if (num % 2012490657U != 0U)
				{
					MethodInfo methodInfo;
					do
					{
						for (;;)
						{
							num = 2010655712U / num;
							if (2018714768U / num == 0U)
							{
								goto IL_00;
							}
							int num8 = num7;
							ParameterInfo[] array3 = array2;
							num = 433062113U * num;
							int num9 = array3.Length;
							num = (1892624424U ^ num);
							if (num8 >= num9)
							{
								if (num != 1348938294U)
								{
									break;
								}
							}
							else
							{
								num = 1350243991U;
								ParameterInfo[] array4 = array2;
								num %= 1503870989U;
								int num10 = num7;
								num %= 59734386U;
								ParameterInfo parameterInfo = array4[num10];
								num >>= 13;
								if ((1290214302U & num) == 0U)
								{
									goto IL_00;
								}
								Type[] array5 = array;
								int num11 = num5;
								num <<= 30;
								int num12 = num11 + (int)(num - 1073741823U);
								num /= 123340658U;
								num5 = num12;
								ParameterInfo parameterInfo2 = parameterInfo;
								num += 1885733902U;
								Type parameterType = parameterInfo2.ParameterType;
								num = 1016928346U / num;
								array5[num11] = parameterType;
								if (1022057045U == num)
								{
									goto IL_00;
								}
								int num13 = num7;
								num *= 2036221132U;
								int num14 = (int)(num ^ 1U);
								num = 1405975166U * num;
								int num15 = num13 + num14;
								num |= 82201848U;
								num7 = num15;
								num ^= 378875663U;
							}
						}
						num = 190143774U >> (int)num;
						Type type = this.07131E52;
						MemberInfo memberInfo = methodBase;
						num = 532159217U / num;
						string name = memberInfo.Name;
						BindingFlags bindingAttr = (int)num + (BindingFlags)(-33260);
						Binder binder = null;
						Type[] types = array;
						num = (2059236421U | num);
						ParameterModifier[] modifiers = null;
						num = (31611230U ^ num);
						MethodInfo method = type.GetMethod(name, bindingAttr, binder, types, modifiers);
						num = 928860888U / num;
						methodInfo = method;
						num = 709262831U + num;
					}
					while (1192895116U % num == 0U);
					bool flag2 = methodInfo != null;
					num <<= 19;
					if (flag2)
					{
						MethodBase methodBase2 = methodInfo;
						num |= 70665440U;
						methodBase = methodBase2;
						num += 4294556448U;
					}
					num >>= 10;
					this.07131E52 = null;
					num += 4291043840U;
					goto IL_273;
				}
				break;
			}
		}
	}

	// Token: 0x06000226 RID: 550 RVA: 0x002FD1EC File Offset: 0x0017D5EC
	private void 777F2AAE()
	{
		uint num;
		for (;;)
		{
			num = 1148806512U;
			MethodBase methodBase = this.2734307D().04F0F15D() as MethodBase;
			if (methodBase == null)
			{
				break;
			}
			num >>= 17;
			MethodBase 716750DB = methodBase;
			num = 578902626U >> (int)num;
			097E1C51.0A706168 0A = this.544A548B(716750DB, (num ^ 2U) != 0U);
			num = 106499638U - num;
			097E1C51.0A706168 0A2 = 0A;
			bool flag = 0A2 != null;
			num = 561860335U - num;
			if (flag)
			{
				num ^= 20741959U;
				097E1C51.0A706168 7A012B = 0A2;
				num &= 490415673U;
				this.1B04365B(7A012B);
				num += 51124867U;
			}
			if ((966150706U ^ num) != 0U)
			{
				return;
			}
		}
		num *= 967997717U;
		throw new ArgumentException();
	}

	// Token: 0x06000227 RID: 551 RVA: 0x002FD28C File Offset: 0x0017D68C
	private void 060F5510()
	{
		uint num;
		097E1C51.0A706168 0A;
		do
		{
			num = 97795258U;
			num >>= 16;
			int 0516041F = this.2734307D().B9179A8D();
			bool <<EMPTY_NAME>> = (num ^ 1492U) != 0U;
			num = 913193312U - num;
			0A = this.5C7D1773(0516041F, <<EMPTY_NAME>>);
			num += 1801866184U;
		}
		while (691878167 << (int)num == 0);
		do
		{
			bool flag = 0A != null;
			num = (2002586339U & num);
			if (flag)
			{
				this.1B04365B(0A);
				num ^= 0U;
			}
		}
		while (311630283U == num);
	}

	// Token: 0x06000228 RID: 552 RVA: 0x002FD30C File Offset: 0x0017D70C
	private void 44D61E98()
	{
		uint num = 252013089U;
		097E1C51.0A706168 0A2;
		if (num * 1853057469U != 0U)
		{
			num <<= 15;
			097E1C51.0A706168 0A = this.2734307D();
			num *= 431121278U;
			int 0516041F = 0A.B9179A8D();
			num = (1345332389U & num);
			0A2 = this.5C7D1773(0516041F, num + 2951741441U != 0U);
			num = 2078220452U - num;
			if (0A2 == null)
			{
				return;
			}
		}
		this.1B04365B(0A2);
		num += 0U;
	}

	// Token: 0x06000229 RID: 553 RVA: 0x002FD37C File Offset: 0x0017D77C
	private void 17C253DE()
	{
		uint num = 8192691U;
		if ((num ^ 2074768769U) == 0U)
		{
			goto IL_50;
		}
		MethodBase methodBase2;
		do
		{
			IL_12:
			num -= 358825925U;
			int 49DC = this.2734307D().B9179A8D();
			num = (1436813405U & num);
			MethodBase methodBase = this.5D982D0E(49DC);
			num = 1280272277U / num;
			methodBase2 = methodBase;
			num = 1663307507U + num;
		}
		while (35401998U / num != 0U);
		do
		{
			IL_50:
			MethodBase 120B2AC = methodBase2;
			num = (460346482U | num);
			097E1C51.0A706168 0A = this.2BA571C3(120B2AC);
			num >>= 2;
			097E1C51.0A706168 0A2 = 0A;
			if (num >= 931596551U)
			{
				goto IL_12;
			}
			if (0A2 != null)
			{
				num = 621629920U * num;
				this.1B04365B(0A2);
				num += 2739701085U;
			}
		}
		while (num > 535520131U);
	}

	// Token: 0x0600022A RID: 554 RVA: 0x002FD424 File Offset: 0x0017D824
	private void 37ED550A()
	{
		for (;;)
		{
			IL_00:
			uint num = 1916289381U;
			int 48092E = this.2734307D().B9179A8D();
			num = 1457134517U << (int)num;
			Type type = this.01581CD6(48092E);
			097E1C51.0A706168 0A2;
			do
			{
				097E1C51.0A706168 0A = this.2734307D();
				num = 631254725U + num;
				0A2 = 0A;
				num = 1707095505U / num;
			}
			while (615007010U == num);
			for (;;)
			{
				bool isGenericType = type.IsGenericType;
				num = 1960714424U >> (int)num;
				if (isGenericType)
				{
					goto IL_6D;
				}
				IL_D5:
				num = 1741519026U / num;
				if (num / 1400382697U != 0U)
				{
					continue;
				}
				Type type2 = type;
				num = (67700127U | num);
				if (type2.IsValueType)
				{
					num = 1924465664U + num;
					if (num > 1882543459U)
					{
						goto Block_4;
					}
					continue;
				}
				else
				{
					if ((201207192U ^ num) == 0U)
					{
						goto IL_00;
					}
					097E1C51.0A706168 0A3 = 0A2;
					num &= 1766531437U;
					object 5076725B = null;
					num *= 1662598370U;
					0A3.360F4E2C(5076725B);
					if (num >> 18 != 0U)
					{
						return;
					}
				}
				IL_6D:
				Type genericTypeDefinition = type.GetGenericTypeDefinition();
				num = 1640052544U >> (int)num;
				RuntimeTypeHandle handle = typeof(Nullable<>).TypeHandle;
				num <<= 5;
				Type typeFromHandle = Type.GetTypeFromHandle(handle);
				num ^= 1367U;
				if (genericTypeDefinition == typeFromHandle)
				{
					break;
				}
				goto IL_D5;
			}
			num = 190202694U >> (int)num;
			097E1C51.0A706168 0A4 = 0A2;
			num = 336213467U % num;
			0A4.360F4E2C(null);
			if (num < 1152008898U)
			{
				break;
			}
			continue;
			Block_4:
			Type type3 = type;
			BindingFlags bindingAttr = (BindingFlags)(num - 1992331563U);
			num = 1536522834U - num;
			FieldInfo[] fields = type3.GetFields(bindingAttr);
			num = 1517833659U / num;
			FieldInfo[] array = fields;
			num = 207492637U >> (int)num;
			int num2 = (int)(num - 207492637U);
			while (num >> 19 != 0U)
			{
				int num3 = num2;
				num -= 1767273795U;
				if (num3 >= array.Length)
				{
					return;
				}
				FieldInfo[] array2 = array;
				int num4 = num2;
				num = 2026730360U;
				FieldInfo fieldInfo = array2[num4];
				num = (498797304U | num);
				FieldInfo fieldInfo2 = fieldInfo;
				num = (407665612U ^ num);
				if ((num ^ 1140133262U) == 0U)
				{
					break;
				}
				FieldInfo fieldInfo3 = fieldInfo2;
				num |= 1665488240U;
				object obj = 0A2.04F0F15D();
				Type fieldType = fieldInfo2.FieldType;
				num += 1882092159U;
				object value;
				if (!fieldType.IsValueType)
				{
					value = null;
				}
				else
				{
					num &= 1576038475U;
					Type fieldType2 = fieldInfo2.FieldType;
					num = 1094126631U >> (int)num;
					value = Activator.CreateInstance(fieldType2);
					num += 3489588847U;
				}
				fieldInfo3.SetValue(obj, value);
				int num5 = num2;
				int num6 = (int)(num ^ 3626354674U);
				num /= 1985873422U;
				int num7 = num5 + num6;
				num = 263675421U + num;
				num2 = num7;
				num ^= 65619971U;
			}
		}
	}

	// Token: 0x0600022B RID: 555 RVA: 0x002FD69C File Offset: 0x0017DA9C
	private void 2A844995()
	{
		uint num;
		int 66836EE;
		097E1C51.0A706168 0A2;
		097E1C51.0A706168 0A3;
		do
		{
			num = 82727091U;
			int num2 = this.2734307D().B9179A8D();
			num %= 450374577U;
			66836EE = num2;
			num = (1957255913U | num);
			097E1C51.0A706168 0A = this.2734307D();
			num = 2094084940U - num;
			0A2 = 0A;
			if ((num & 1820214531U) == 0U)
			{
				break;
			}
			0A3 = this.2734307D();
		}
		while (2126520005U <= num);
		num = (49685783U ^ num);
		097E1C51.0A706168 313E0ED = 0A3;
		097E1C51.0A706168 03CE = 0A2;
		num >>= 5;
		int 339906C = this.483C62ED(313E0ED, 03CE, (num ^ 2670570U) != 0U, 66836EE);
		num <<= 24;
		this.1B04365B(new 097E1C51.14C26E2D(339906C));
	}

	// Token: 0x0600022C RID: 556 RVA: 0x002FD72C File Offset: 0x0017DB2C
	private void 5C162AE0()
	{
		uint num = 1899645964U;
		if (num <= 1520697535U)
		{
			goto IL_48;
		}
		int 66836EE;
		097E1C51.0A706168 0A3;
		do
		{
			IL_11:
			097E1C51.0A706168 0A = this.2734307D();
			num = (1645811567U | num);
			66836EE = 0A.B9179A8D();
			num += 1391604551U;
			097E1C51.0A706168 0A2 = this.2734307D();
			num *= 540288988U;
			0A3 = 0A2;
		}
		while (num >> 15 == 0U);
		IL_48:
		num = 1955798171U * num;
		097E1C51.0A706168 0A4 = this.2734307D();
		num = 1442805128U - num;
		097E1C51.0A706168 0A5 = 0A4;
		num <<= 22;
		if (1272544950U >> (int)num != 0U)
		{
			num = 51196680U + num;
			097E1C51.0A706168 313E0ED = 0A5;
			num ^= 2028815892U;
			097E1C51.0A706168 03CE = 0A3;
			num = (617154016U | num);
			bool 1C53484C = num - 2146006523U != 0U;
			num <<= 22;
			this.1B04365B(new 097E1C51.14C26E2D(this.483C62ED(313E0ED, 03CE, 1C53484C, 66836EE)));
			if (138503318U > num)
			{
				goto IL_11;
			}
		}
	}

	// Token: 0x0600022D RID: 557 RVA: 0x002FD7F8 File Offset: 0x0017DBF8
	private void 6B4F248F()
	{
		uint num = 1104950921U;
		num &= 1021788945U;
		num = 415246247U * num;
		097E1C51.0A706168 0A = this.2734307D();
		num &= 792754064U;
		int 48092E = 0A.B9179A8D();
		num = 786388445U - num;
		Type type = this.01581CD6(48092E);
		num >>= 23;
		Type type2 = type;
		do
		{
			num = 1460481701U % num;
			Type elementType = type2;
			num = 434112766U + num;
			int length = this.2734307D().B9179A8D();
			num = 644036209U >> (int)num;
			097E1C51.0A706168 7A012B = new 097E1C51.08046E73(Array.CreateInstance(elementType, length));
			num = 371408501U >> (int)num;
			this.1B04365B(7A012B);
		}
		while (421856181U < num);
	}

	// Token: 0x0600022E RID: 558 RVA: 0x002FD898 File Offset: 0x0017DC98
	private void 465700D8()
	{
		uint num;
		Type type;
		097E1C51.0A706168 0A3;
		097E1C51.0A706168 0A4;
		Array array;
		for (;;)
		{
			IL_00:
			num = 849358558U;
			num = 471670494U / num;
			097E1C51.0A706168 0A = this.2734307D();
			num = 1403453542U + num;
			type = this.01581CD6(0A.B9179A8D());
			num = 880502469U - num;
			for (;;)
			{
				097E1C51.0A706168 0A2 = this.2734307D();
				num -= 2062433358U;
				0A3 = 0A2;
				num = 458185229U / num;
				do
				{
					0A4 = this.2734307D();
					if (397680832U == num)
					{
						goto IL_00;
					}
					097E1C51.0A706168 0A5 = this.2734307D();
					num = 1806516334U << (int)num;
					object obj = 0A5.04F0F15D();
					num >>= 19;
					array = (obj as Array);
					num = (26618113U | num);
				}
				while (1999729507U == num);
				bool flag = array != null;
				num &= 1772121390U;
				if (!flag)
				{
					goto Block_2;
				}
				if ((num ^ 855860816U) != 0U)
				{
					goto Block_3;
				}
			}
		}
		Block_2:
		throw new ArgumentException();
		Block_3:
		Array array2 = array;
		num = (1357739468U ^ num);
		num = 1646821090U - num;
		object 169A670F = 0A3;
		Type 5B942D4A = type;
		num = (8473623U ^ num);
		object 169A670F2 = this.592B4570(169A670F, 5B942D4A);
		num *= 1432115986U;
		097E1C51.0A706168 0A6 = this.592B4570(169A670F2, array.GetType().GetElementType());
		num = 908819426U * num;
		object value = 0A6.04F0F15D();
		num = 1377314245U % num;
		int index = 0A4.B9179A8D();
		num /= 1743397320U;
		array2.SetValue(value, index);
	}

	// Token: 0x0600022F RID: 559 RVA: 0x002FD9BC File Offset: 0x0017DDBC
	private void 18784820()
	{
		uint num = 566836336U;
		Type 5B942D4A;
		097E1C51.0A706168 0A2;
		Array array;
		for (;;)
		{
			num ^= 1422399060U;
			num ^= 116730015U;
			5B942D4A = this.01581CD6(this.2734307D().B9179A8D());
			num = 351042799U / num;
			if (132469106U < num)
			{
				return;
			}
			for (;;)
			{
				097E1C51.0A706168 0A = this.2734307D();
				num -= 2028235739U;
				0A2 = 0A;
				num |= 1481507333U;
				if (num % 1751198103U == 0U)
				{
					break;
				}
				097E1C51.0A706168 0A3 = this.2734307D();
				num ^= 1261269584U;
				object obj = 0A3.04F0F15D();
				num = (68574170U & num);
				array = (obj as Array);
				if (906896295U + num != 0U)
				{
					if (array != null)
					{
						goto IL_A3;
					}
					if ((num ^ 781019590U) != 0U)
					{
						goto Block_4;
					}
				}
			}
		}
		Block_4:
		throw new ArgumentException();
		IL_A3:
		num = 844319650U / num;
		Array array2 = array;
		num += 1469472371U;
		int index = 0A2.B9179A8D();
		num &= 1743864470U;
		object value = array2.GetValue(index);
		num += 1793155785U;
		097E1C51.0A706168 7A012B = this.592B4570(value, 5B942D4A);
		num = (1153464858U ^ num);
		this.1B04365B(7A012B);
	}

	// Token: 0x06000230 RID: 560 RVA: 0x002FDAB0 File Offset: 0x0017DEB0
	private void 31BD3698()
	{
		uint num = 590245172U;
		for (;;)
		{
			Array array = this.2734307D().04F0F15D() as Array;
			num &= 222836333U;
			bool flag = array != null;
			num = 637096915U + num;
			if (!flag)
			{
				break;
			}
			num *= 1341592173U;
			Array array2 = array;
			num -= 914646335U;
			int length = array2.Length;
			num |= 1825275878U;
			097E1C51.0A706168 7A012B = new 097E1C51.14C26E2D(length);
			num = (908877509U & num);
			this.1B04365B(7A012B);
			if (num != 371082591U)
			{
				return;
			}
		}
		num = 2052619969U / num;
		throw new ArgumentException();
	}

	// Token: 0x06000231 RID: 561 RVA: 0x002FDB34 File Offset: 0x0017DF34
	private void 209D7FC0()
	{
		for (;;)
		{
			097E1C51.0A706168 0A = this.2734307D();
			uint num = 2093246971U;
			num = 1944392800U << (int)num;
			object obj = this.2734307D().04F0F15D();
			num &= 28069552U;
			Array array = obj as Array;
			num = (968260779U ^ num);
			bool flag = array != null;
			num <<= 4;
			if (!flag)
			{
				if (num > 1814323806U)
				{
					break;
				}
			}
			else
			{
				num = 144707636U * num;
				if ((num ^ 90529748U) != 0U)
				{
					this.1B04365B(new 097E1C51.2013254A(array, 0A.B9179A8D()));
					if (num != 999387214U)
					{
						return;
					}
				}
			}
		}
		throw new ArgumentException();
	}

	// Token: 0x06000232 RID: 562 RVA: 0x002FDBD0 File Offset: 0x0017DFD0
	private void 35F356F3()
	{
		uint num = 638781390U;
		int 49DC = this.2734307D().B9179A8D();
		num <<= 9;
		MethodBase 33AC5A1B = this.5D982D0E(49DC);
		num = 747133562U << (int)num;
		this.1B04365B(new 097E1C51.110B654B(33AC5A1B));
	}

	// Token: 0x06000233 RID: 563 RVA: 0x002FDC18 File Offset: 0x0017E018
	private void 2DD92D6C()
	{
		uint num;
		MethodBase methodBase2;
		for (;;)
		{
			IL_00:
			num = 514720393U;
			num /= 1035215845U;
			097E1C51.0A706168 0A = this.2734307D();
			num = 647266110U + num;
			MethodBase methodBase = this.5D982D0E(0A.B9179A8D());
			num *= 1395468612U;
			methodBase2 = methodBase;
			num = (1247938434U | num);
			MethodInfo method;
			for (;;)
			{
				IL_42:
				097E1C51.0A706168 0A2 = this.2734307D();
				num |= 1559257912U;
				object obj = 0A2.04F0F15D();
				num >>= 16;
				Type type = obj.GetType();
				for (;;)
				{
					IL_68:
					Type declaringType = methodBase2.DeclaringType;
					num = 1166625368U - num;
					if ((num ^ 897005762U) == 0U)
					{
						break;
					}
					for (;;)
					{
						IL_87:
						MethodBase methodBase3 = methodBase2;
						num = 1982873552U * num;
						ParameterInfo[] parameters = methodBase3.GetParameters();
						num = (688204881U | num);
						Type[] array = new Type[parameters.Length];
						num = (2129477170U ^ num);
						Type[] array2 = array;
						int num2 = (int)(num + 4001853373U);
						num += 1103582500U;
						int num3 = num2;
						ParameterInfo[] array3 = parameters;
						num = (277834552U ^ num);
						int num4 = (int)(num - 1135644255U);
						if (num == 1655053640U)
						{
							goto IL_00;
						}
						for (;;)
						{
							for (;;)
							{
								num += 44189389U;
								if (num < 450970356U)
								{
									goto IL_68;
								}
								int num5 = num4;
								num += 1345277464U;
								int num6 = array3.Length;
								num = 1537878883U >> (int)num;
								int num7 = num6;
								num += 1945337531U;
								if (num5 >= num7)
								{
									break;
								}
								ParameterInfo parameterInfo = array3[num4];
								Type[] array4 = array2;
								int num8 = num3;
								num = 159914710U;
								num >>= 20;
								int num9 = num8 + (int)(num ^ 153U);
								num = (659455526U & num);
								num3 = num9;
								ParameterInfo parameterInfo2 = parameterInfo;
								num |= 560212938U;
								array4[num8] = parameterInfo2.ParameterType;
								num = 1798266568U + num;
								if ((811495620U ^ num) == 0U)
								{
									goto IL_42;
								}
								int num10 = num4 + (int)(num ^ 2358479507U);
								num = 1053710762U >> (int)num;
								num4 = num10;
								num ^= 1135641068U;
							}
							if (num - 2005149018U == 0U)
							{
								goto IL_42;
							}
							for (;;)
							{
								bool flag = type != null;
								num += 1735546891U;
								num ^= 3777001852U;
								if (!flag)
								{
									goto IL_31D;
								}
								num = 1608345321U - num;
								if (736640527U >> (int)num == 0U)
								{
									goto IL_87;
								}
								Type type2 = type;
								num -= 276587006U;
								if (type2 == declaringType)
								{
									goto Block_11;
								}
								num = 548758531U;
								Type type3 = type;
								string name = methodBase2.Name;
								BindingFlags bindingAttr = (BindingFlags)(num ^ 548688949U);
								num |= 1392914488U;
								Binder binder = null;
								num = (963779741U & num);
								CallingConventions callConvention = (CallingConventions)(num ^ 825365530U);
								num <<= 19;
								Type[] types = array2;
								num -= 1355954807U;
								ParameterModifier[] modifiers = null;
								num = (1274569305U ^ num);
								method = type3.GetMethod(name, bindingAttr, binder, callConvention, types, modifiers);
								num <<= 6;
								if (num >= 805137821U)
								{
									break;
								}
								if (method != null)
								{
									MethodInfo methodInfo = method;
									num = (1431459200U | num);
									MethodInfo baseDefinition = methodInfo.GetBaseDefinition();
									MethodBase methodBase4 = methodBase2;
									num = 13333363U + num;
									num += 2871167757U;
									if (baseDefinition == methodBase4)
									{
										goto Block_6;
									}
								}
								Type type4 = type;
								num <<= 2;
								Type baseType = type4.BaseType;
								num = 937845241U - num;
								type = baseType;
								num ^= 1401991304U;
							}
						}
					}
				}
			}
			Block_6:
			num = 309099829U << (int)num;
			if (num - 1209471656U != 0U)
			{
				MethodBase methodBase5 = method;
				num /= 1894017583U;
				methodBase2 = methodBase5;
				if (432103871U >= num)
				{
					break;
				}
			}
		}
		goto IL_31D;
		Block_11:
		num ^= 1331758315U;
		IL_31D:
		num &= 1577471822U;
		MethodBase 33AC5A1B = methodBase2;
		num = (2023111040U ^ num);
		097E1C51.0A706168 7A012B = new 097E1C51.110B654B(33AC5A1B);
		num -= 559226294U;
		this.1B04365B(7A012B);
	}

	// Token: 0x06000234 RID: 564 RVA: 0x002FDF70 File Offset: 0x0017E370
	private void 4C135968()
	{
		uint num = 1858612717U;
		num = 908534383U % num;
		num -= 1970484813U;
		097E1C51.0A706168 0A = this.2734307D();
		num = 867509768U >> (int)num;
		this.14EA7BFD = 0A.B9179A8D();
	}

	// Token: 0x06000235 RID: 565 RVA: 0x002FDFB4 File Offset: 0x0017E3B4
	private void 71393749()
	{
		this.2734307D();
	}

	// Token: 0x06000236 RID: 566 RVA: 0x002FDFC8 File Offset: 0x0017E3C8
	private void 15BC1CEB()
	{
		uint num = 2115711016U;
		for (;;)
		{
			IL_06:
			Stack<int> stack = this.1D9D066A;
			num = (1720208396U & num);
			num = 1639518527U / num;
			097E1C51.0A706168 0A = this.2734307D();
			num = 2053986849U - num;
			stack.Push(0A.B9179A8D());
			num = 2013731285U >> (int)num;
			if (num != 402878289U)
			{
				num -= 1901804443U;
				097E1C51.0A706168 0A2 = this.2734307D();
				num = 1950759159U + num;
				int num2 = 0A2.B9179A8D();
				num = 1209600893U + num;
				int num3 = num2;
				if (670059125U < num)
				{
					for (;;)
					{
						if (2024736220U <= num)
						{
							num = 1609393053U * num;
							if (this.77FC1BC6.Count == 0)
							{
								break;
							}
							num = (77925455U ^ num);
							int num4 = num3;
							num -= 1062081934U;
							Stack<097E1C51.5C236423> stack2 = this.77FC1BC6;
							num >>= 11;
							097E1C51.5C236423 5C = stack2.Peek();
							num = 368125779U * num;
							int num5 = 5C.065D025F();
							num <<= 4;
							if (num4 <= num5)
							{
								goto Block_10;
							}
						}
						for (;;)
						{
							IL_81:
							Stack<097E1C51.5C236423> stack3 = this.77FC1BC6;
							num = 932078623U;
							List<097E1C51.54B80CE2> list = stack3.Pop().75F02B24();
							num = 619395712U % num;
							List<097E1C51.54B80CE2> list2 = list;
							int count = list2.Count;
							num = 1204910400U % num;
							int i = count;
							while (i > (int)(num ^ 585514688U))
							{
								num = 819993230U;
								List<097E1C51.54B80CE2> list3 = list2;
								int num6 = i;
								num = 831279965U / num;
								int num7 = (int)(num ^ 0U);
								num = 189540637U % num;
								097E1C51.54B80CE2 54B80CE = list3[num6 - num7];
								num = 1943959283U + num;
								097E1C51.54B80CE2 54B80CE2 = 54B80CE;
								if (num < 995953297U)
								{
									goto IL_06;
								}
								097E1C51.54B80CE2 54B80CE3 = 54B80CE2;
								num >>= 19;
								byte b = 54B80CE3.35B577B7();
								num = 503127894U / num;
								uint num8 = num + 4294831575U;
								num ^= 130106626U;
								if (b == num8)
								{
									if (num << 18 == 0U)
									{
										goto IL_06;
									}
									Stack<int> stack4 = this.1D9D066A;
									num = 1063084552U + num;
									097E1C51.54B80CE2 54B80CE4 = 54B80CE2;
									num = (322731569U | num);
									int item = 54B80CE4.2AED5988();
									num ^= 1408270335U;
									stack4.Push(item);
									num ^= 51369959U;
								}
								num = 369508019U >> (int)num;
								if (num > 1828334861U)
								{
									goto IL_81;
								}
								int num9 = i;
								num = 827916720U - num;
								int num10 = num9 - (int)(num ^ 827195024U);
								num = 1559915773U * num;
								i = num10;
								num += 3419569523U;
							}
							break;
						}
						num ^= 2783030019U;
					}
					IL_210:
					if (num / 626547896U == 0U)
					{
						continue;
					}
					num = 495393970U >> (int)num;
					Exception ex = null;
					num = 549025782U - num;
					this.1F3D4082 = ex;
					num ^= 1613579647U;
					this.02C11BDF.Clear();
					num += 1775986788U;
					num ^= 1312041689U;
					Stack<int> stack5 = this.1D9D066A;
					num += 270611000U;
					int num11 = stack5.Pop();
					num <<= 0;
					this.14EA7BFD = num11;
					if (460784452U - num != 0U)
					{
						break;
					}
					continue;
					Block_10:
					num ^= 627535815U;
					goto IL_210;
				}
			}
		}
	}

	// Token: 0x06000237 RID: 567 RVA: 0x002FE260 File Offset: 0x0017E660
	private void 4274788B()
	{
		bool flag = this.1F3D4082 != null;
		uint num = 748029575U;
		if (flag && 1924480843 << (int)num != 0)
		{
			num = 1909075322U - num;
			Exception 2FA62E = this.1F3D4082;
			num = 1135558066U + num;
			this.6F321127(2FA62E);
			return;
		}
		num = (1690726825U & num);
		this.14EA7BFD = this.1D9D066A.Pop();
	}

	// Token: 0x06000238 RID: 568 RVA: 0x002FE2C8 File Offset: 0x0017E6C8
	private void 1A657FE9()
	{
		uint num = 1492676922U;
		for (;;)
		{
			if (this.2734307D().B9179A8D() != 0)
			{
				num >>= 13;
				if (2080589626U != num)
				{
					break;
				}
			}
			else
			{
				num += 569337644U;
				num -= 104562037U;
				num = 2105901015U << (int)num;
				Exception 2FA62E = this.1F3D4082;
				num *= 1794860360U;
				this.6F321127(2FA62E);
				if (1506244451U / num == 0U)
				{
					return;
				}
			}
		}
		do
		{
			num = (540501242U & num);
			this.77FC1BC6.Pop();
			num -= 585188210U;
			num = 1269961864U >> (int)num;
			Stack<097E1C51.4A781DBA> stack = this.02C11BDF;
			num |= 42362427U;
			num = 1260672946U * num;
			stack.Push(new 097E1C51.7B0B79E5(this.1F3D4082));
			097E1C51.54B80CE2 54B80CE = this.29231AE3;
			num ^= 1009918931U;
			int num2 = 54B80CE.2AED5988();
			num = 1854934852U * num;
			this.14EA7BFD = num2;
		}
		while (433537280 << (int)num == 0);
		this.29231AE3 = null;
	}

	// Token: 0x06000239 RID: 569 RVA: 0x002FE3C8 File Offset: 0x0017E7C8
	private void 67D63995()
	{
		for (;;)
		{
			uint num = 1382114391U;
			int 48092E = this.2734307D().B9179A8D();
			num = (523458446U & num);
			Type type = this.01581CD6(48092E);
			num = 2127780566U >> (int)num;
			Type 5B942D4A = type;
			if (479270043U >= num)
			{
				num >>= 18;
				num = 532964680U << (int)num;
				object 169A670F = this.2734307D();
				num *= 332283109U;
				097E1C51.0A706168 0A = this.592B4570(169A670F, 5B942D4A);
				num >>= 1;
				object 1A34651F = 0A.04F0F15D();
				num %= 819861792U;
				this.1B04365B(new 097E1C51.7B0B79E5(1A34651F));
				if (819416349U >= num)
				{
					break;
				}
			}
		}
	}

	// Token: 0x0600023A RID: 570 RVA: 0x002FE464 File Offset: 0x0017E864
	private void 6EF13986()
	{
		uint num;
		do
		{
			num = 1660248216U;
			097E1C51.0A706168 0A = this.2734307D();
			num = 1689075931U >> (int)num;
			Type type = this.01581CD6(0A.B9179A8D());
			num = 2039420167U * num;
			Type type2 = type;
			num /= 1872769588U;
			num /= 1966501286U;
			num = (1162235649U ^ num);
			097E1C51.0A706168 0A2 = this.2734307D();
			num = 134708651U + num;
			object 169A670F = 0A2.04F0F15D();
			num |= 424632742U;
			Type 5B942D4A = type2;
			num = (990016514U | num);
			097E1C51.0A706168 7A012B = this.592B4570(169A670F, 5B942D4A);
			num = 32587982U * num;
			this.1B04365B(7A012B);
		}
		while (num < 85549005U);
	}

	// Token: 0x0600023B RID: 571 RVA: 0x002FE4FC File Offset: 0x0017E8FC
	private void 1A7E7361()
	{
		uint num;
		097E1C51.0A706168 0A2;
		object obj2;
		for (;;)
		{
			num = 2083542279U;
			int 48092E = this.2734307D().B9179A8D();
			num = 1715950614U / num;
			Type type = this.01581CD6(48092E);
			num = (1099448914U | num);
			Type type2 = type;
			if (1625584669U % num != 0U)
			{
				goto IL_3A;
			}
			for (;;)
			{
				IL_6B:
				097E1C51.0A706168 0A = 0A2;
				num &= 386757653U;
				object obj = 0A.04F0F15D();
				num += 2015460256U;
				obj2 = obj;
				if (obj2 == null)
				{
					if (num >= 1011443952U)
					{
						goto Block_1;
					}
				}
				else
				{
					for (;;)
					{
						num = (472600320U & num);
						Type type3 = type2;
						num = 611520002U * num;
						bool isValueType = type3.IsValueType;
						num = 70677304U / num;
						if (isValueType)
						{
							goto IL_C7;
						}
						num = 933044455U * num;
						TypeCode typeCode = Type.GetTypeCode(type2);
						num &= 217515706U;
						TypeCode typeCode2 = typeCode;
						if (618659978U + num != 0U)
						{
							switch (typeCode2 - (TypeCode)(num ^ 3U))
							{
							case 0:
								goto IL_187;
							case 1:
								goto IL_1C4;
							case 2:
								goto IL_1F4;
							case 3:
								num = 1651513723U - num;
								if (num > 1404461745U)
								{
									goto Block_11;
								}
								continue;
							case 4:
								goto IL_279;
							case 5:
								goto IL_2C3;
							case 6:
								goto IL_323;
							case 7:
								goto IL_365;
							case 8:
								goto IL_38B;
							case 9:
								goto IL_3C8;
							case 10:
								goto IL_3F7;
							case 11:
								if (num != 212100404U)
								{
									goto Block_19;
								}
								continue;
							}
							goto Block_6;
						}
						goto IL_3A;
					}
					IL_3C8:
					if (num % 1591961868U == 0U)
					{
						goto Block_18;
					}
				}
			}
			IL_C7:
			if (type2 != obj2.GetType())
			{
				num = 791748373U - num;
				if (2072863923U * num != 0U)
				{
					goto Block_3;
				}
				goto IL_3A;
			}
			else
			{
				if (991969800U != num)
				{
					goto Block_4;
				}
				continue;
			}
			Block_6:
			if (num << 15 == 0U)
			{
				goto Block_7;
			}
			continue;
			IL_1F4:
			if (num == 2115781984U)
			{
				continue;
			}
			num &= 80494364U;
			object obj3 = obj2;
			num = 225582610U + num;
			sbyte 6DB62FEE = (sbyte)obj3;
			num = 151272150U / num;
			097E1C51.0A706168 7A012B = new 097E1C51.63674386(6DB62FEE);
			num |= 319640393U;
			this.1B04365B(7A012B);
			if (num >> 4 != 0U)
			{
				return;
			}
			continue;
			IL_279:
			if (num - 306267874U == 0U)
			{
				continue;
			}
			object obj4 = obj2;
			num &= 1160537513U;
			short 30E14BCB = (short)obj4;
			num %= 761488608U;
			097E1C51.0A706168 7A012B2 = new 097E1C51.77B017D1(30E14BCB);
			num = (1772774557U | num);
			this.1B04365B(7A012B2);
			if (num - 1587695439U != 0U)
			{
				return;
			}
			continue;
			IL_38B:
			num = (1674274502U ^ num);
			num = (2039550702U ^ num);
			long 6DB81B = (long)obj2;
			num *= 905845705U;
			this.1B04365B(new 097E1C51.31EF1BAE(6DB81B));
			if (num * 1830316476U != 0U)
			{
				return;
			}
			continue;
			IL_187:
			num = 1953387257U * num;
			if (1038746974U - num != 0U)
			{
				goto Block_8;
			}
			goto IL_3A;
			IL_2C3:
			num = 534184787U + num;
			if (1598977652U / num == 0U)
			{
				goto IL_3A;
			}
			num |= 1895846747U;
			ushort 66DC3E = (ushort)obj2;
			num = (1311640201U ^ num);
			097E1C51.0A706168 7A012B3 = new 097E1C51.7EDB7F26(66DC3E);
			num = 319976035U << (int)num;
			this.1B04365B(7A012B3);
			if (498344332 << (int)num != 0)
			{
				return;
			}
			goto IL_C7;
			IL_323:
			object obj5 = obj2;
			num ^= 368578079U;
			int 339906C = (int)obj5;
			num |= 2019370404U;
			097E1C51.0A706168 7A012B4 = new 097E1C51.14C26E2D(339906C);
			num = 507188432U << (int)num;
			this.1B04365B(7A012B4);
			if (271003962U >= num)
			{
				return;
			}
			IL_3A:
			num = (1228426959U | num);
			097E1C51.0A706168 0A3 = this.2734307D();
			num /= 1207325655U;
			0A2 = 0A3;
			num = (778974244U | num);
			if (340591920U <= num)
			{
				goto IL_6B;
			}
		}
		Block_1:
		throw new NullReferenceException();
		Block_3:
		throw new InvalidCastException();
		Block_4:
		num = (1517640758U & num);
		this.1B04365B(0A2);
		return;
		Block_7:
		throw new InvalidCastException();
		Block_8:
		object obj6 = obj2;
		num = (247791649U ^ num);
		bool 47466F = (bool)obj6;
		num |= 391451633U;
		this.1B04365B(new 097E1C51.1E233DE9(47466F));
		return;
		IL_1C4:
		num = 1431516225U - num;
		object obj7 = obj2;
		num |= 31926770U;
		097E1C51.0A706168 7A012B5 = new 097E1C51.0496456E((char)obj7);
		num <<= 25;
		this.1B04365B(7A012B5);
		return;
		Block_11:
		097E1C51.0A706168 7A012B6 = new 097E1C51.226653A3((byte)obj2);
		num += 691212970U;
		this.1B04365B(7A012B6);
		return;
		IL_365:
		num &= 1706708148U;
		uint 5BCA48B = (uint)obj2;
		num ^= 1818523898U;
		this.1B04365B(new 097E1C51.79942DAA(5BCA48B));
		return;
		Block_18:
		097E1C51.0A706168 7A012B7 = new 097E1C51.378A58B6((ulong)obj2);
		num = 643913263U << (int)num;
		this.1B04365B(7A012B7);
		return;
		IL_3F7:
		num *= 1179269563U;
		num |= 593837661U;
		float 52F83C3E = (float)obj2;
		num /= 370557346U;
		097E1C51.0A706168 7A012B8 = new 097E1C51.7B032626(52F83C3E);
		num <<= 7;
		this.1B04365B(7A012B8);
		return;
		Block_19:
		double 25B00FBF = (double)obj2;
		num = 425865973U * num;
		097E1C51.0A706168 7A012B9 = new 097E1C51.4DA65608(25B00FBF);
		num >>= 2;
		this.1B04365B(7A012B9);
	}

	// Token: 0x0600023C RID: 572 RVA: 0x002FE974 File Offset: 0x0017ED74
	private void 7B835829()
	{
		uint num = 1817452705U;
		if ((num & 1164738334U) != 0U)
		{
			do
			{
				num /= 541548576U;
				long num2 = this.4D045382;
				num &= 1958487868U;
				097E1C51.0A706168 0A = this.2734307D();
				num = (977405418U | num);
				ulong num3 = (ulong)0A.2CD32589();
				num <<= 6;
				this.1B04365B(new 097E1C51.14C26E2D(Marshal.ReadInt32(new IntPtr(num2 + (long)num3))));
			}
			while (1085626081U >= num);
		}
	}

	// Token: 0x0600023D RID: 573 RVA: 0x002FE9E8 File Offset: 0x0017EDE8
	private void 1AC96223()
	{
		uint num;
		int num3;
		for (;;)
		{
			IL_00:
			num = 764746094U;
			097E1C51.0A706168 0A = this.2734307D();
			num = (829697044U ^ num);
			int num2 = 0A.B9179A8D();
			num = (1869641379U ^ num);
			num3 = num2;
			int num7;
			for (;;)
			{
				int num4 = num3;
				int num5 = (int)(num ^ 1938899905U);
				num = 819164220U / num;
				int num6 = num4 >> num5;
				num = (1444111177U | num);
				num7 = num6;
				num /= 216028068U;
				if (num7 > (int)(num - 4294967292U))
				{
					goto IL_CE;
				}
				num = (579745864U | num);
				if (1237075893U >= num)
				{
					uint num8 = (uint)num7;
					num = (397161762U | num);
					switch (num8 - (num - 934165869U))
					{
					case 0U:
					case 1U:
						goto IL_166;
					case 2U:
					case 4U:
						goto IL_306;
					case 3U:
						if (num >> 10 != 0U)
						{
							goto Block_7;
						}
						continue;
					case 5U:
						goto IL_1AB;
					}
					goto Block_2;
				}
				goto IL_00;
			}
			IL_1AB:
			num *= 419976031U;
			if (num << 21 != 0U)
			{
				goto Block_8;
			}
			continue;
			IL_CE:
			int num9 = num7;
			num = 2073829188U - num;
			uint num10 = num + 2221138141U;
			num <<= 16;
			num += 410008942U;
			if (num9 == num10)
			{
				goto IL_166;
			}
			int num11 = num7;
			uint num12 = num + 3360801469U;
			num += 0U;
			if (num11 != num12)
			{
				goto Block_6;
			}
			goto IL_1AB;
			Block_2:
			if (1790407427U % num == 0U)
			{
				goto IL_C9;
			}
			int num13 = num7;
			num = 357923452U >> (int)num;
			uint num14 = num + 4294945461U;
			num = 1758267081U + num;
			if (num13 != num14)
			{
				break;
			}
			if (1718713062U < num)
			{
				goto Block_9;
			}
		}
		num += 3470844240U;
		IL_C9:
		goto IL_306;
		Block_6:
		num += 0U;
		goto IL_306;
		Block_7:
		Module module = this.45C00733;
		num |= 1803951778U;
		ModuleHandle moduleHandle = module.ModuleHandle;
		num = 886581801U - num;
		int fieldToken = num3;
		num ^= 1915512431U;
		object 67EE67F = moduleHandle.ResolveFieldHandle(fieldToken);
		num /= 405369804U;
		this.1B04365B(new 097E1C51.25883EE0(67EE67F));
		return;
		IL_166:
		num = (926307602U | num);
		ModuleHandle moduleHandle2 = this.45C00733.ModuleHandle;
		num = 1027474069U / num;
		moduleHandle = moduleHandle2;
		object 67EE67F2 = moduleHandle.ResolveTypeHandle(num3);
		num = 1020089110U - num;
		097E1C51.0A706168 7A012B = new 097E1C51.25883EE0(67EE67F2);
		num >>= 16;
		this.1B04365B(7A012B);
		return;
		Block_8:
		Module module2 = this.45C00733;
		num = 1233943076U - num;
		ModuleHandle moduleHandle3 = module2.ModuleHandle;
		num = (1447893912U | num);
		moduleHandle = moduleHandle3;
		num = 1792673760U << (int)num;
		num = 1726223456U % num;
		int methodToken = num3;
		num %= 1815355462U;
		RuntimeMethodHandle runtimeMethodHandle = moduleHandle.ResolveMethodHandle(methodToken);
		num = 522474238U * num;
		object 67EE67F3 = runtimeMethodHandle;
		num = 925067963U % num;
		this.1B04365B(new 097E1C51.25883EE0(67EE67F3));
		return;
		Block_9:
		try
		{
			if (num + 67896463U != 0U)
			{
				do
				{
					num = 1468749351U << (int)num;
					ModuleHandle moduleHandle4 = this.45C00733.ModuleHandle;
					num = 2081947906U + num;
					moduleHandle = moduleHandle4;
					num /= 1253055978U;
					RuntimeFieldHandle runtimeFieldHandle = moduleHandle.ResolveFieldHandle(num3);
					num = 732187355U * num;
					this.1B04365B(new 097E1C51.25883EE0(runtimeFieldHandle));
				}
				while (242682240U < num);
			}
			return;
		}
		catch
		{
			num = 2101105895U;
			if (num - 897256030U != 0U)
			{
				num -= 1179877101U;
				Module module3 = this.45C00733;
				num = 1733124023U + num;
				ModuleHandle moduleHandle5 = module3.ModuleHandle;
				num |= 481904539U;
				moduleHandle = moduleHandle5;
				RuntimeMethodHandle runtimeMethodHandle2 = moduleHandle.ResolveMethodHandle(num3);
				num = 1579223666U / num;
				object 67EE67F4 = runtimeMethodHandle2;
				num <<= 15;
				097E1C51.0A706168 7A012B2 = new 097E1C51.25883EE0(67EE67F4);
				num = 492905999U >> (int)num;
				this.1B04365B(7A012B2);
			}
			return;
		}
		IL_306:
		if (num != 939525885U)
		{
			throw new InvalidOperationException();
		}
	}

	// Token: 0x0600023E RID: 574 RVA: 0x002FED28 File Offset: 0x0017F128
	private void 5393093A()
	{
		Exception ex = this.2734307D().04F0F15D() as Exception;
		if (ex == null)
		{
			throw new ArgumentException();
		}
		throw ex;
	}

	// Token: 0x0600023F RID: 575 RVA: 0x002FED58 File Offset: 0x0017F158
	private void 690E2B29()
	{
		if (this.1F3D4082 != null)
		{
			uint num = 1306540575U;
			if (num <= 1854766689U)
			{
				num |= 647984161U;
				throw this.1F3D4082;
			}
		}
		throw new InvalidOperationException();
	}

	// Token: 0x06000240 RID: 576 RVA: 0x002FED98 File Offset: 0x0017F198
	private void 54420E2A()
	{
		uint num = 1402670579U;
		num <<= 5;
		097E1C51.0A706168 0A = this.2734307D();
		num &= 375720516U;
		Type type = this.01581CD6(0A.B9179A8D());
		num >>= 5;
		Type 556D1F = type;
		097E1C51.0A706168 0A2 = this.2734307D();
		num = 458976623U << (int)num;
		if (1351367470U % num != 0U)
		{
			num = 856176431U % num;
			097E1C51.0A706168 0A3 = 0A2;
			num = (1462059312U | num);
			bool flag = this.675D49B1(0A3.04F0F15D(), 556D1F);
			num = 986082813U << (int)num;
			if (flag)
			{
				num = 173693024U * num;
				if (num != 336557116U)
				{
					num *= 265296872U;
					097E1C51.0A706168 7A012B = 0A2;
					num = 1057635646U >> (int)num;
					this.1B04365B(7A012B);
				}
				return;
			}
			num ^= 2008108026U;
		}
		throw new InvalidCastException();
	}

	// Token: 0x06000241 RID: 577 RVA: 0x002FEE64 File Offset: 0x0017F264
	private void 768C1A06()
	{
		uint num;
		097E1C51.0A706168 0A2;
		for (;;)
		{
			num = 1199778013U;
			097E1C51.0A706168 0A = this.2734307D();
			num += 508898702U;
			int 48092E = 0A.B9179A8D();
			num ^= 1696034060U;
			Type type = this.01581CD6(48092E);
			0A2 = this.2734307D();
			if (214854161U >= num)
			{
				097E1C51.0A706168 0A3 = 0A2;
				num *= 1037464518U;
				object 54893EA = 0A3.04F0F15D();
				Type 556D1F = type;
				num = (1540492794U & num);
				bool flag = this.675D49B1(54893EA, 556D1F);
				num = (621746009U & num);
				if (!flag)
				{
					if (num == 392658634U)
					{
						continue;
					}
					object 1A34651F = null;
					num = 2040018487U * num;
					0A2 = new 097E1C51.7B0B79E5(1A34651F);
					num ^= 1704626608U;
				}
				if (867203670U != num)
				{
					break;
				}
			}
		}
		num *= 2062703804U;
		097E1C51.0A706168 7A012B = 0A2;
		num = 482820960U - num;
		this.1B04365B(7A012B);
	}

	// Token: 0x06000242 RID: 578 RVA: 0x002FEF1C File Offset: 0x0017F31C
	private void 7CEA02B4()
	{
		uint num = 798886390U;
		if (781922476U >> (int)num == 0U)
		{
			goto IL_2F;
		}
		IL_18:
		num = (1040321934U ^ num);
		097E1C51.0A706168 0A = this.2734307D();
		num += 775435559U;
		IL_2F:
		097E1C51.0A706168 0A2 = 0A;
		num = 314787498U / num;
		object obj = 0A2.04F0F15D();
		num = (1854955755U & num);
		bool flag = obj is IConvertible;
		num <<= 8;
		if (!flag)
		{
			if (1503752522U - num != 0U)
			{
				double naN = double.NaN;
				num *= 1631657390U;
				0A = new 097E1C51.4DA65608(naN);
				num += 156048221U;
				goto IL_F3;
			}
			goto IL_B7;
		}
		IL_57:
		097E1C51.0A706168 0A3 = 0A;
		num = (617501375U & num);
		double num2 = 0A3.D87DFA30();
		num = 1894474036U - num;
		double d = num2;
		num += 1661367203U;
		if (!double.IsNaN(d))
		{
			num = (208428619U & num);
			if (754129769U < num)
			{
				goto IL_18;
			}
			double d2 = num2;
			num /= 643121589U;
			bool flag2 = double.IsInfinity(d2);
			num ^= 156048221U;
			if (!flag2)
			{
				goto IL_F3;
			}
			num += 3399793018U;
		}
		IL_B7:
		if (num > 1175139538U)
		{
			throw new OverflowException();
		}
		goto IL_18;
		IL_F3:
		num = (425473279U ^ num);
		if (num != 971458230U)
		{
			this.1B04365B(0A);
			return;
		}
		goto IL_57;
	}

	// Token: 0x06000243 RID: 579 RVA: 0x002FF038 File Offset: 0x0017F438
	private unsafe void 62A038EF()
	{
		uint num = 1698976729U;
		IntPtr intPtr2;
		if (1935960166U >= num)
		{
			num = 1596938049U * num;
			IntPtr cb = this.2734307D().E3F543C7();
			num += 1552492525U;
			IntPtr intPtr = Marshal.AllocHGlobal(cb);
			num &= 482554611U;
			intPtr2 = intPtr;
			List<IntPtr> list = this.4FEE2A5A;
			IntPtr item = intPtr2;
			num = 598626331U / num;
			list.Add(item);
			if (num >= 1119239461U)
			{
				return;
			}
		}
		num = (677736548U & num);
		num = (686824836U | num);
		void* ptr = intPtr2.ToPointer();
		num ^= 468874677U;
		RuntimeTypeHandle handle = typeof(void*).TypeHandle;
		num >>= 17;
		Type typeFromHandle = Type.GetTypeFromHandle(handle);
		num = 1449161268U % num;
		this.1B04365B(new 097E1C51.7B0B79E5(Pointer.Box(ptr, typeFromHandle)));
	}

	// Token: 0x06000244 RID: 580 RVA: 0x002FF0E8 File Offset: 0x0017F4E8
	private void 35FA5863()
	{
		uint num = 134707413U;
		List<IntPtr>.Enumerator enumerator = this.4FEE2A5A.GetEnumerator();
		try
		{
			for (;;)
			{
				num &= 135792072U;
				if (!enumerator.MoveNext())
				{
					break;
				}
				num = 1557085121U;
				if (num >> 27 == 0U)
				{
					break;
				}
				num = (632360103U & num);
				IntPtr hglobal = enumerator.Current;
				num /= 1671242526U;
				Marshal.FreeHGlobal(hglobal);
				num ^= 134707413U;
			}
		}
		finally
		{
			do
			{
				num = 1973046429U;
				if (num <= 1156659156U)
				{
					break;
				}
				((IDisposable)enumerator).Dispose();
			}
			while (1527253459U == num);
		}
	}

	// Token: 0x06000245 RID: 581 RVA: 0x002FF1A4 File Offset: 0x0017F5A4
	public object 183C4865(object[] 781D0A76, int 00CB599B)
	{
		uint num = 976895333U;
		if (1459582295U > num)
		{
			num = (801712293U | num);
			this.14EA7BFD = 00CB599B;
		}
		num |= 1064139390U;
		num ^= 2034183793U;
		this.1B04365B(new 097E1C51.08046E73(781D0A76));
		object result;
		try
		{
			for (;;)
			{
				try
				{
					num = 944965446U;
					do
					{
						num <<= 10;
						Dictionary<uint, 097E1C51.1F7F0274> dictionary = this.0FEE0E1F;
						num -= 731282631U;
						num &= 800988880U;
						uint key = (uint)this.5C36503F();
						num = 1373831512U >> (int)num;
						097E1C51.1F7F0274 1F7F = dictionary[key];
						num = 1156605977U + num;
						1F7F();
					}
					while ((827465219U ^ num) == 0U);
					bool flag = this.14EA7BFD != 0;
					num *= 522680465U;
					if (flag)
					{
						continue;
					}
				}
				catch (Exception 2FA62E)
				{
					num = 1305169654U;
					if (num >> 2 != 0U)
					{
						num /= 1720848918U;
						this.6F321127(2FA62E);
					}
					continue;
				}
				break;
			}
			num = 130023485U;
			097E1C51.0A706168 0A = this.2734307D();
			num <<= 20;
			result = 0A.04F0F15D();
		}
		finally
		{
			num = 2128232657U;
			if (383745181U < num)
			{
				this.35FA5863();
			}
		}
		return result;
	}

	// Token: 0x06000246 RID: 582 RVA: 0x002FF2F4 File Offset: 0x0017F6F4
	// Note: this type is marked as 'beforefieldinit'.
	static 097E1C51()
	{
		uint num = 1182991149U;
		if ((num ^ 1832005378U) != 0U)
		{
			do
			{
				097E1C51.6E740956 = new Dictionary<int, object>();
				num %= 1546155963U;
			}
			while (854872975U / num != 0U);
		}
		097E1C51.7B842C70 = new Dictionary<MethodBase, DynamicMethod>();
	}

	// Token: 0x0400014D RID: 333
	private readonly Dictionary<uint, 097E1C51.1F7F0274> 0FEE0E1F;

	// Token: 0x0400014E RID: 334
	private readonly Module 45C00733;

	// Token: 0x0400014F RID: 335
	private readonly long 4D045382;

	// Token: 0x04000150 RID: 336
	private int 14EA7BFD;

	// Token: 0x04000151 RID: 337
	private Type 07131E52;

	// Token: 0x04000152 RID: 338
	private Stack<097E1C51.4A781DBA> 02C11BDF;

	// Token: 0x04000153 RID: 339
	private static readonly Dictionary<int, object> 6E740956;

	// Token: 0x04000154 RID: 340
	private static readonly Dictionary<MethodBase, DynamicMethod> 7B842C70;

	// Token: 0x04000155 RID: 341
	private List<097E1C51.0A706168> 53780069;

	// Token: 0x04000156 RID: 342
	private List<097E1C51.5C236423> 652B3F15;

	// Token: 0x04000157 RID: 343
	private Stack<097E1C51.5C236423> 77FC1BC6;

	// Token: 0x04000158 RID: 344
	private Stack<int> 1D9D066A;

	// Token: 0x04000159 RID: 345
	private Exception 1F3D4082;

	// Token: 0x0400015A RID: 346
	private 097E1C51.54B80CE2 29231AE3;

	// Token: 0x0400015B RID: 347
	private List<IntPtr> 4FEE2A5A;

	// Token: 0x0200004B RID: 75
	private abstract class 0A706168
	{
		// Token: 0x0600026E RID: 622
		public abstract 097E1C51.0A706168 7A52F208();

		// Token: 0x0600026F RID: 623
		public abstract object 04F0F15D();

		// Token: 0x06000270 RID: 624
		public abstract void 360F4E2C(object 5076725B);

		// Token: 0x06000271 RID: 625 RVA: 0x002F0E28 File Offset: 0x00171228
		public virtual bool 3CF78257()
		{
			return false;
		}

		// Token: 0x06000272 RID: 626 RVA: 0x002FFFD0 File Offset: 0x001803D0
		public virtual 097E1C51.4A781DBA 3D28B13E()
		{
			throw new InvalidOperationException();
		}

		// Token: 0x06000273 RID: 627 RVA: 0x002FFFE4 File Offset: 0x001803E4
		public virtual 097E1C51.0A706168 58E29230()
		{
			return this;
		}

		// Token: 0x06000274 RID: 628 RVA: 0x002FFFD0 File Offset: 0x001803D0
		public virtual Type 5F93D4ED()
		{
			throw new InvalidOperationException();
		}

		// Token: 0x06000275 RID: 629 RVA: 0x002FFFD0 File Offset: 0x001803D0
		public virtual TypeCode 6DFD59A4()
		{
			throw new InvalidOperationException();
		}

		// Token: 0x06000276 RID: 630 RVA: 0x002FFFF4 File Offset: 0x001803F4
		public virtual bool C77753F7()
		{
			return Convert.ToBoolean(this.04F0F15D());
		}

		// Token: 0x06000277 RID: 631 RVA: 0x00300014 File Offset: 0x00180414
		public virtual sbyte 52B3F2EC()
		{
			return Convert.ToSByte(this.04F0F15D());
		}

		// Token: 0x06000278 RID: 632 RVA: 0x00300034 File Offset: 0x00180434
		public virtual short BDAC8EA4()
		{
			return Convert.ToInt16(this.04F0F15D());
		}

		// Token: 0x06000279 RID: 633 RVA: 0x00300054 File Offset: 0x00180454
		public virtual int B9179A8D()
		{
			uint num = 1077556795U;
			num -= 1072513989U;
			return Convert.ToInt32(this.04F0F15D());
		}

		// Token: 0x0600027A RID: 634 RVA: 0x0030007C File Offset: 0x0018047C
		public virtual long 8EC1A2BC()
		{
			return Convert.ToInt64(this.04F0F15D());
		}

		// Token: 0x0600027B RID: 635 RVA: 0x0030009C File Offset: 0x0018049C
		public virtual char A773FD03()
		{
			return Convert.ToChar(this.04F0F15D());
		}

		// Token: 0x0600027C RID: 636 RVA: 0x003000B4 File Offset: 0x001804B4
		public virtual byte 19201189()
		{
			return Convert.ToByte(this.04F0F15D());
		}

		// Token: 0x0600027D RID: 637 RVA: 0x003000D4 File Offset: 0x001804D4
		public virtual ushort 86B35C1E()
		{
			return Convert.ToUInt16(this.04F0F15D());
		}

		// Token: 0x0600027E RID: 638 RVA: 0x003000F4 File Offset: 0x001804F4
		public virtual uint 2CD32589()
		{
			uint num = 1643979895U;
			num &= 1909330391U;
			return Convert.ToUInt32(this.04F0F15D());
		}

		// Token: 0x0600027F RID: 639 RVA: 0x0030011C File Offset: 0x0018051C
		public virtual ulong 083A097E()
		{
			uint num = 639507184U;
			num = (201873242U | num);
			object value = this.04F0F15D();
			num = (258951192U & num);
			return Convert.ToUInt64(value);
		}

		// Token: 0x06000280 RID: 640 RVA: 0x0030014C File Offset: 0x0018054C
		public virtual float E1E85554()
		{
			uint num = 1616259099U;
			object value = this.04F0F15D();
			num = 1480594291U * num;
			return Convert.ToSingle(value);
		}

		// Token: 0x06000281 RID: 641 RVA: 0x00300174 File Offset: 0x00180574
		public virtual double D87DFA30()
		{
			return Convert.ToDouble(this.04F0F15D());
		}

		// Token: 0x06000282 RID: 642 RVA: 0x00300194 File Offset: 0x00180594
		public override string ToString()
		{
			uint num;
			object obj2;
			do
			{
				num = 345843870U;
				object obj = this.04F0F15D();
				num >>= 2;
				obj2 = obj;
				num -= 183453806U;
				if (num < 387414680U)
				{
					break;
				}
				if (obj2 != null)
				{
					goto IL_44;
				}
				num >>= 18;
			}
			while (num % 852962382U == 0U);
			return null;
			IL_44:
			return Convert.ToString(obj2);
		}

		// Token: 0x06000283 RID: 643 RVA: 0x002FFFD0 File Offset: 0x001803D0
		public virtual IntPtr E3F543C7()
		{
			throw new InvalidOperationException();
		}

		// Token: 0x06000284 RID: 644 RVA: 0x002FFFD0 File Offset: 0x001803D0
		public virtual UIntPtr 7F934522()
		{
			throw new InvalidOperationException();
		}

		// Token: 0x06000285 RID: 645 RVA: 0x002FFFD0 File Offset: 0x001803D0
		public virtual object 9E2F1085(Type 1EB00610, bool 63854659)
		{
			throw new InvalidOperationException();
		}
	}

	// Token: 0x0200004C RID: 76
	private abstract class 4A781DBA : 097E1C51.0A706168
	{
		// Token: 0x06000287 RID: 647 RVA: 0x002FFFE4 File Offset: 0x001803E4
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			return this;
		}

		// Token: 0x06000288 RID: 648 RVA: 0x002F0E28 File Offset: 0x00171228
		public override TypeCode 6DFD59A4()
		{
			return TypeCode.Empty;
		}

		// Token: 0x06000289 RID: 649 RVA: 0x003001EC File Offset: 0x001805EC
		protected 4A781DBA()
		{
			uint num = 1853581343U;
			do
			{
				num = (736182134U ^ num);
				base..ctor();
			}
			while (2024285220U <= num);
		}
	}

	// Token: 0x0200004D RID: 77
	private sealed class 14C26E2D : 097E1C51.4A781DBA
	{
		// Token: 0x0600028A RID: 650 RVA: 0x00300218 File Offset: 0x00180618
		public 14C26E2D(int 339906C2)
		{
			this.34AB2CDC = 339906C2;
		}

		// Token: 0x0600028B RID: 651 RVA: 0x00300234 File Offset: 0x00180634
		public override Type 5F93D4ED()
		{
			uint num = 1926562835U;
			RuntimeTypeHandle handle = typeof(int).TypeHandle;
			num = 1729431346U << (int)num;
			return Type.GetTypeFromHandle(handle);
		}

		// Token: 0x0600028C RID: 652 RVA: 0x00300260 File Offset: 0x00180660
		public override TypeCode 6DFD59A4()
		{
			return TypeCode.Int32;
		}

		// Token: 0x0600028D RID: 653 RVA: 0x00300270 File Offset: 0x00180670
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.14C26E2D(this.34AB2CDC);
		}

		// Token: 0x0600028E RID: 654 RVA: 0x00300290 File Offset: 0x00180690
		public override object 04F0F15D()
		{
			uint num = 2139956030U;
			int num2 = this.34AB2CDC;
			num = 342827467U >> (int)num;
			return num2;
		}

		// Token: 0x0600028F RID: 655 RVA: 0x003002BC File Offset: 0x001806BC
		public override void 360F4E2C(object 19AD17A9)
		{
			this.34AB2CDC = Convert.ToInt32(19AD17A9);
		}

		// Token: 0x06000290 RID: 656 RVA: 0x003002DC File Offset: 0x001806DC
		public override bool C77753F7()
		{
			uint num = 377828758U;
			uint num2 = (uint)this.34AB2CDC;
			num = 547370601U << (int)num;
			return num2 > num - 2587885568U;
		}

		// Token: 0x06000291 RID: 657 RVA: 0x0030030C File Offset: 0x0018070C
		public override sbyte 52B3F2EC()
		{
			uint num = 510542281U;
			num = 627006916U << (int)num;
			return (sbyte)this.34AB2CDC;
		}

		// Token: 0x06000292 RID: 658 RVA: 0x00300334 File Offset: 0x00180734
		public override short BDAC8EA4()
		{
			return (short)this.34AB2CDC;
		}

		// Token: 0x06000293 RID: 659 RVA: 0x00300350 File Offset: 0x00180750
		public override int B9179A8D()
		{
			return this.34AB2CDC;
		}

		// Token: 0x06000294 RID: 660 RVA: 0x0030036C File Offset: 0x0018076C
		public override long 8EC1A2BC()
		{
			return (long)this.34AB2CDC;
		}

		// Token: 0x06000295 RID: 661 RVA: 0x00300388 File Offset: 0x00180788
		public override char A773FD03()
		{
			return (char)this.34AB2CDC;
		}

		// Token: 0x06000296 RID: 662 RVA: 0x003003A4 File Offset: 0x001807A4
		public override byte 19201189()
		{
			return (byte)this.34AB2CDC;
		}

		// Token: 0x06000297 RID: 663 RVA: 0x00300388 File Offset: 0x00180788
		public override ushort 86B35C1E()
		{
			return (ushort)this.34AB2CDC;
		}

		// Token: 0x06000298 RID: 664 RVA: 0x00300350 File Offset: 0x00180750
		public override uint 2CD32589()
		{
			return (uint)this.34AB2CDC;
		}

		// Token: 0x06000299 RID: 665 RVA: 0x003003C0 File Offset: 0x001807C0
		public override ulong 083A097E()
		{
			return (ulong)this.34AB2CDC;
		}

		// Token: 0x0600029A RID: 666 RVA: 0x003003DC File Offset: 0x001807DC
		public override float E1E85554()
		{
			return (float)this.34AB2CDC;
		}

		// Token: 0x0600029B RID: 667 RVA: 0x003003F8 File Offset: 0x001807F8
		public override double D87DFA30()
		{
			uint num = 757542694U;
			double num2 = (double)this.34AB2CDC;
			num = 597896217U % num;
			return num2;
		}

		// Token: 0x0600029C RID: 668 RVA: 0x0030041C File Offset: 0x0018081C
		public override IntPtr E3F543C7()
		{
			uint num = 1018259169U;
			int value = this.34AB2CDC;
			num = 2037860709U >> (int)num;
			return new IntPtr(value);
		}

		// Token: 0x0600029D RID: 669 RVA: 0x00300448 File Offset: 0x00180848
		public override UIntPtr 7F934522()
		{
			return new UIntPtr((uint)this.34AB2CDC);
		}

		// Token: 0x0600029E RID: 670 RVA: 0x00300468 File Offset: 0x00180868
		public override 097E1C51.0A706168 58E29230()
		{
			uint num = 980712126U;
			num /= 1887506460U;
			uint 5BCA48B = (uint)this.34AB2CDC;
			num -= 1607618590U;
			return new 097E1C51.79942DAA(5BCA48B);
		}

		// Token: 0x0600029F RID: 671 RVA: 0x00300498 File Offset: 0x00180898
		public override object 9E2F1085(Type 25AD1F29, bool 55CF5F81)
		{
			uint num;
			for (;;)
			{
				num = 740113483U;
				if (25AD1F29 != typeof(IntPtr))
				{
					num >>= 15;
					num &= 339877746U;
					Type typeFromHandle = typeof(UIntPtr);
					num = (916929994U & num);
					if (25AD1F29 == typeFromHandle)
					{
						num += 1018899502U;
						num *= 1115580068U;
						if (!55CF5F81)
						{
							if (num / 1179467580U != 0U)
							{
								goto Block_6;
							}
							continue;
						}
						else
						{
							if (1439594819U != num)
							{
								goto Block_7;
							}
							continue;
						}
					}
					else
					{
						num = (644969445U & num);
						num = (501116404U | num);
						TypeCode typeCode = Type.GetTypeCode(25AD1F29);
						num &= 237057748U;
						TypeCode typeCode2 = typeCode;
						if (1292526681U >= num)
						{
							uint num2 = (uint)typeCode2;
							num ^= 1578196805U;
							switch (num2 - (num + 2918093940U))
							{
							case 0U:
								num = (1341613836U | num);
								if (1330019647U > num)
								{
									continue;
								}
								if (!55CF5F81)
								{
									goto Block_11;
								}
								if ((1336083724U ^ num) != 0U)
								{
									goto Block_12;
								}
								goto IL_16;
							case 1U:
								num = 1746101431U % num;
								if (55CF5F81)
								{
									goto IL_3B6;
								}
								if (num >> 3 != 0U)
								{
									goto Block_21;
								}
								goto IL_16;
							case 2U:
								num = (1123175474U & num);
								if (!55CF5F81)
								{
									goto Block_13;
								}
								if (num > 804613459U)
								{
									goto Block_14;
								}
								continue;
							case 3U:
								num = (1493059825U | num);
								if (!55CF5F81)
								{
									num = 1735292074U + num;
									if (num / 1853694771U != 0U)
									{
										goto Block_23;
									}
									continue;
								}
								else
								{
									if (883380182U < num)
									{
										goto Block_24;
									}
									goto IL_16;
								}
								break;
							case 4U:
								if (55CF5F81)
								{
									goto IL_2EE;
								}
								if (num >= 911875166U)
								{
									goto Block_16;
								}
								continue;
							case 5U:
								num -= 410923315U;
								if (!55CF5F81)
								{
									if (1861093492U >= num)
									{
										goto Block_26;
									}
									continue;
								}
								else
								{
									if (2140628007U >= num)
									{
										goto Block_27;
									}
									continue;
								}
								break;
							case 6U:
								num = (1636834796U | num);
								if (821895589U >> (int)num == 0U)
								{
									continue;
								}
								if (55CF5F81)
								{
									goto IL_363;
								}
								num >>= 16;
								if (35194773U > num)
								{
									goto Block_19;
								}
								goto IL_42;
							case 7U:
								num = 337066768U << (int)num;
								if (55CF5F81)
								{
									goto IL_4DE;
								}
								num &= 1177705149U;
								if (num < 1879922378U)
								{
									goto Block_29;
								}
								goto IL_16;
							case 8U:
								goto IL_570;
							case 9U:
								if (!55CF5F81)
								{
									goto Block_30;
								}
								num = 401633593U + num;
								if ((num & 1685391002U) != 0U)
								{
									goto Block_31;
								}
								continue;
							}
							goto Block_9;
						}
						goto IL_B7;
					}
				}
				IL_16:
				int size = IntPtr.Size;
				uint num3 = num - 740113479U;
				num |= 262034069U;
				if (size != num3)
				{
					goto IL_A1;
				}
				num &= 1181958451U;
				if (2135584383U == num)
				{
					continue;
				}
				IL_42:
				if (!55CF5F81)
				{
					break;
				}
				num = 1433892318U - num;
				if (1991448494U >= num)
				{
					goto Block_2;
				}
				goto IL_16;
			}
			num *= 748046084U;
			int value = this.34AB2CDC;
			goto IL_8E;
			Block_2:
			num >>= 31;
			int num4 = (int)(checked((uint)this.34AB2CDC));
			num = (83454741U | num);
			value = num4;
			num += 960158263U;
			IL_8E:
			num += 1609901519U;
			return new IntPtr(value);
			IL_A1:
			num = (1378568409U ^ num);
			long value2;
			if (55CF5F81)
			{
				value2 = (long)((ulong)this.34AB2CDC);
				num += 4012612138U;
				goto IL_E2;
			}
			num %= 1803451760U;
			IL_B7:
			num = 1092581225U - num;
			long num5 = (long)this.34AB2CDC;
			num = (1110863587U ^ num);
			value2 = num5;
			IL_E2:
			num = 1569608711U << (int)num;
			return new IntPtr(value2);
			Block_6:
			uint num6 = (uint)this.34AB2CDC;
			num = 966419131U % num;
			uint value3 = num6;
			goto IL_172;
			Block_7:
			value3 = (uint)this.34AB2CDC;
			num += 3302462459U;
			IL_172:
			num = (785449943U & num);
			UIntPtr uintPtr = new UIntPtr(value3);
			num >>= 30;
			return uintPtr;
			Block_9:
			num += 0U;
			goto IL_570;
			Block_11:
			num <<= 11;
			num += 1004092300U;
			sbyte b = checked((sbyte)this.34AB2CDC);
			goto IL_269;
			Block_12:
			uint num7 = (uint)this.34AB2CDC;
			num = 185496789U >> (int)num;
			sbyte b2 = (sbyte)num7;
			num = 1930575394U * num;
			b = b2;
			num ^= 4149617548U;
			IL_269:
			num -= 944640238U;
			return b;
			Block_13:
			num = 1167226903U + num;
			short num8 = (short)this.34AB2CDC;
			num = (88095485U ^ num);
			short num9 = num8;
			goto IL_2C4;
			Block_14:
			short num10 = (short)(checked((uint)this.34AB2CDC));
			num *= 592327985U;
			num9 = num10;
			num += 1509547978U;
			IL_2C4:
			return num9;
			Block_16:
			num <<= 10;
			int num11 = this.34AB2CDC;
			goto IL_30E;
			IL_2EE:
			num = (53110448U & num);
			uint num12 = (uint)this.34AB2CDC;
			num += 1786909179U;
			num11 = checked((int)num12);
			num += 3643527029U;
			IL_30E:
			return num11;
			Block_19:
			num = 827133399U * num;
			long num13 = (long)this.34AB2CDC;
			num >>= 20;
			long num14 = num13;
			goto IL_382;
			IL_363:
			num /= 1087338694U;
			num = (1512386433U | num);
			num14 = (long)((ulong)this.34AB2CDC);
			num += 2782583068U;
			IL_382:
			return num14;
			Block_21:
			num = (1836864544U | num);
			byte b3;
			checked
			{
				b3 = (byte)this.34AB2CDC;
				goto IL_3DC;
				IL_3B6:
				num = (1560958267U ^ num);
				byte b4 = (byte)((uint)this.34AB2CDC);
				num = 1006985445U >> (int)num;
				b3 = b4;
			}
			num += 2138963237U;
			IL_3DC:
			return b3;
			Block_23:
			ushort num15 = (ushort)this.34AB2CDC;
			num %= 1138167808U;
			ushort num16 = num15;
			goto IL_43B;
			Block_24:
			uint num17 = (uint)this.34AB2CDC;
			num &= 1684539126U;
			uint num19;
			checked
			{
				num16 = (ushort)num17;
				num ^= 2060960363U;
				IL_43B:
				num /= 1993479961U;
				return num16;
				Block_26:
				num >>= 29;
				uint num18 = (uint)this.34AB2CDC;
				goto IL_49D;
				Block_27:
				num = 1892901241U >> (int)num;
				num18 = (uint)this.34AB2CDC;
				num ^= 0U;
				IL_49D:
				num = 1410163961U / num;
				return num18;
				Block_29:
				num19 = (uint)this.34AB2CDC;
				goto IL_4EC;
				IL_4DE:
				num19 = (uint)this.34AB2CDC;
			}
			num += 3489660928U;
			IL_4EC:
			return num19;
			Block_30:
			num = (456138374U | num);
			num = 943330531U >> (int)num;
			double num20 = (double)this.34AB2CDC;
			num = 703296608U << (int)num;
			double num21 = num20;
			goto IL_562;
			Block_31:
			num = (85344260U ^ num);
			int num22 = this.34AB2CDC;
			num = 693569825U >> (int)num;
			num21 = num22;
			num ^= 1952490844U;
			IL_562:
			num = (451149895U | num);
			return num21;
			IL_570:
			num = 526386766U - num;
			throw new ArgumentException();
		}

		// Token: 0x04000198 RID: 408
		private int 34AB2CDC;
	}

	// Token: 0x0200004E RID: 78
	private sealed class 31EF1BAE : 097E1C51.4A781DBA
	{
		// Token: 0x060002A0 RID: 672 RVA: 0x00300A24 File Offset: 0x00180E24
		public 31EF1BAE(long 6DB81B39)
		{
			uint num = 1109928690U;
			do
			{
				base..ctor();
				num <<= 30;
			}
			while (563166472U >> (int)num == 0U);
			this.59462865 = 6DB81B39;
		}

		// Token: 0x060002A1 RID: 673 RVA: 0x00300A60 File Offset: 0x00180E60
		public override Type 5F93D4ED()
		{
			return typeof(long);
		}

		// Token: 0x060002A2 RID: 674 RVA: 0x00300A78 File Offset: 0x00180E78
		public override TypeCode 6DFD59A4()
		{
			uint num = 1304065484U;
			return (TypeCode)(num ^ 1304065479U);
		}

		// Token: 0x060002A3 RID: 675 RVA: 0x00300A94 File Offset: 0x00180E94
		public override 097E1C51.0A706168 58E29230()
		{
			return new 097E1C51.378A58B6((ulong)this.59462865);
		}

		// Token: 0x060002A4 RID: 676 RVA: 0x00300AB4 File Offset: 0x00180EB4
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.31EF1BAE(this.59462865);
		}

		// Token: 0x060002A5 RID: 677 RVA: 0x00300AD4 File Offset: 0x00180ED4
		public override object 04F0F15D()
		{
			uint num = 157513860U;
			num = 642719870U - num;
			return this.59462865;
		}

		// Token: 0x060002A6 RID: 678 RVA: 0x00300AFC File Offset: 0x00180EFC
		public override void 360F4E2C(object 73892511)
		{
			uint num = 446641020U;
			if (1868717019U > num)
			{
				num = (1363941844U | num);
				this.59462865 = Convert.ToInt64(73892511);
			}
		}

		// Token: 0x060002A7 RID: 679 RVA: 0x00300B30 File Offset: 0x00180F30
		public override bool C77753F7()
		{
			long num = this.59462865;
			long num2 = 0;
			uint num3 = 1887005649U;
			long num4 = num2;
			num3 += 2138265415U;
			return num > num4;
		}

		// Token: 0x060002A8 RID: 680 RVA: 0x00300B58 File Offset: 0x00180F58
		public override char A773FD03()
		{
			uint num = 581331957U;
			num -= 1238571983U;
			return (char)this.59462865;
		}

		// Token: 0x060002A9 RID: 681 RVA: 0x00300B7C File Offset: 0x00180F7C
		public override byte 19201189()
		{
			return (byte)this.59462865;
		}

		// Token: 0x060002AA RID: 682 RVA: 0x00300B98 File Offset: 0x00180F98
		public override sbyte 52B3F2EC()
		{
			return (sbyte)this.59462865;
		}

		// Token: 0x060002AB RID: 683 RVA: 0x00300BAC File Offset: 0x00180FAC
		public override short BDAC8EA4()
		{
			uint num = 626278618U;
			short num2 = (short)this.59462865;
			num &= 360203732U;
			return num2;
		}

		// Token: 0x060002AC RID: 684 RVA: 0x00300BD0 File Offset: 0x00180FD0
		public override int B9179A8D()
		{
			return (int)this.59462865;
		}

		// Token: 0x060002AD RID: 685 RVA: 0x00300BEC File Offset: 0x00180FEC
		public override long 8EC1A2BC()
		{
			return this.59462865;
		}

		// Token: 0x060002AE RID: 686 RVA: 0x00300B58 File Offset: 0x00180F58
		public override ushort 86B35C1E()
		{
			uint num = 581331957U;
			num -= 1238571983U;
			return (ushort)this.59462865;
		}

		// Token: 0x060002AF RID: 687 RVA: 0x00300C08 File Offset: 0x00181008
		public override uint 2CD32589()
		{
			return (uint)this.59462865;
		}

		// Token: 0x060002B0 RID: 688 RVA: 0x00300BEC File Offset: 0x00180FEC
		public override ulong 083A097E()
		{
			return (ulong)this.59462865;
		}

		// Token: 0x060002B1 RID: 689 RVA: 0x00300C24 File Offset: 0x00181024
		public override float E1E85554()
		{
			uint num = 1516072205U;
			num += 1778283038U;
			float num2 = (float)this.59462865;
			num = 52914902U - num;
			return num2;
		}

		// Token: 0x060002B2 RID: 690 RVA: 0x00300C50 File Offset: 0x00181050
		public override double D87DFA30()
		{
			uint num = 677859088U;
			double num2 = (double)this.59462865;
			num -= 519464975U;
			return num2;
		}

		// Token: 0x060002B3 RID: 691 RVA: 0x00300C74 File Offset: 0x00181074
		public override IntPtr E3F543C7()
		{
			uint num = 649227385U;
			if (num == 905201831U)
			{
				goto IL_3D;
			}
			IL_11:
			long value;
			if (IntPtr.Size != (int)(num ^ 649227389U))
			{
				num = 1451843233U / num;
				num &= 78534119U;
				value = this.59462865;
				goto IL_60;
			}
			IL_3D:
			num = 1139097297U / num;
			if (661401366U < num)
			{
				goto IL_11;
			}
			value = (long)((int)this.59462865);
			num ^= 3U;
			IL_60:
			num = 921466324U / num;
			return new IntPtr(value);
		}

		// Token: 0x060002B4 RID: 692 RVA: 0x00300CF0 File Offset: 0x001810F0
		public override UIntPtr 7F934522()
		{
			uint num;
			do
			{
				uint size = (uint)UIntPtr.Size;
				num = 1322917934U;
				if (size == (num ^ 1322917930U))
				{
					goto IL_3E;
				}
				num >>= 12;
			}
			while (1446461677U + num == 0U);
			num >>= 21;
			ulong value = (ulong)this.59462865;
			goto IL_6C;
			IL_3E:
			num |= 2036623131U;
			uint num2 = (uint)this.59462865;
			num = 1008289917U >> (int)num;
			ulong num3 = (ulong)num2;
			num = 2124507964U * num;
			value = num3;
			num ^= 0U;
			IL_6C:
			return new UIntPtr(value);
		}

		// Token: 0x060002B5 RID: 693 RVA: 0x00300D70 File Offset: 0x00181170
		public override object 9E2F1085(Type 0A4031CF, bool 287C6459)
		{
			uint num = 860361865U;
			long value;
			if (1919565274U > num)
			{
				for (;;)
				{
					num /= 602813488U;
					if (0A4031CF == typeof(IntPtr))
					{
						if (num < 1709143868U)
						{
							goto IL_34;
						}
					}
					else
					{
						for (;;)
						{
							num = 1043610027U * num;
							Type typeFromHandle = typeof(UIntPtr);
							num &= 2124553473U;
							if (0A4031CF == typeFromHandle)
							{
								goto Block_4;
							}
							num %= 1963555653U;
							uint typeCode = (uint)Type.GetTypeCode(0A4031CF);
							num |= 1298151646U;
							switch (typeCode - (num - 2137012698U))
							{
							case 0U:
								goto IL_170;
							case 1U:
								goto IL_2D7;
							case 2U:
								goto IL_1D9;
							case 3U:
								goto IL_32B;
							case 4U:
								goto IL_225;
							case 5U:
								goto IL_38E;
							case 6U:
								if ((num ^ 1075058137U) != 0U)
								{
									goto Block_13;
								}
								continue;
							case 7U:
								goto IL_3EF;
							case 8U:
								goto IL_4C1;
							case 9U:
								goto IL_458;
							}
							goto Block_6;
						}
						IL_170:
						num = 2086239970U / num;
						num = 716008165U - num;
						if (287C6459)
						{
							goto IL_1A5;
						}
						if (813172504U >= num)
						{
							goto Block_8;
						}
						continue;
						IL_1D9:
						if (!287C6459)
						{
							goto Block_9;
						}
						num /= 335505936U;
						if ((1813273278U & num) != 0U)
						{
							goto Block_10;
						}
						continue;
						IL_2D7:
						num &= 527790523U;
						if (num - 824842546U != 0U)
						{
							goto Block_15;
						}
						continue;
						IL_32B:
						if (num * 1401559702U == 0U)
						{
							goto IL_34;
						}
						num = (2103267223U & num);
						if (!287C6459)
						{
							num %= 673215080U;
							if (num <= 2008500293U)
							{
								goto Block_19;
							}
							continue;
						}
						else
						{
							if (598227538U % num != 0U)
							{
								goto Block_20;
							}
							continue;
						}
						IL_3EF:
						num = (753690842U ^ num);
						num &= 1046546395U;
						if (!287C6459)
						{
							goto Block_23;
						}
						num = 1268934199U % num;
						if (175132120U >= num)
						{
							goto Block_24;
						}
						continue;
						IL_458:
						num = (616897841U ^ num);
						num = 668558949U >> (int)num;
						if (!287C6459)
						{
							goto Block_25;
						}
						if (num < 1461208842U)
						{
							goto Block_26;
						}
						continue;
						IL_38E:
						num = (2095254655U & num);
						num ^= 1970488918U;
						if (287C6459)
						{
							goto IL_3C3;
						}
						if (num <= 240986562U)
						{
							goto Block_22;
						}
						goto IL_34;
					}
					IL_55:
					if (32990121U > num)
					{
						goto Block_3;
					}
					continue;
					IL_34:
					if (!287C6459)
					{
						break;
					}
					goto IL_55;
				}
				num = 1699967875U % num;
				goto IL_42;
				Block_3:
				num = 1647913569U / num;
				ulong num2 = (ulong)this.59462865;
				num >>= 22;
				value = checked((long)num2);
				num += 4294966904U;
				goto IL_80;
				Block_4:
				ulong value2;
				if (!287C6459)
				{
					num /= 1182994150U;
					ulong num3 = (ulong)this.59462865;
					num = (1166755842U & num);
					value2 = num3;
				}
				else
				{
					num = 220726749U / num;
					num -= 865281526U;
					value2 = (ulong)this.59462865;
					num += 865281526U;
				}
				num |= 205727053U;
				UIntPtr uintPtr = new UIntPtr(value2);
				num *= 1468493407U;
				return uintPtr;
				Block_6:
				num += 0U;
				goto IL_4C1;
				Block_8:
				sbyte b = (sbyte)this.59462865;
				num = (1822112470U ^ num);
				sbyte b2 = b;
				goto IL_1CB;
				IL_1A5:
				num = 78539391U >> (int)num;
				sbyte b3 = (sbyte)(checked((ulong)this.59462865));
				num %= 71249402U;
				b2 = b3;
				num += 1175510240U;
				IL_1CB:
				num |= 504629635U;
				return b2;
				Block_9:
				short num4 = (short)this.59462865;
				num /= 923170308U;
				short num5 = num4;
				goto IL_21F;
				Block_10:
				short num6 = (short)(checked((ulong)this.59462865));
				num *= 700807516U;
				num5 = num6;
				num += 90122202U;
				IL_21F:
				return num5;
				IL_225:
				if (num != 779355733U)
				{
					int num7;
					if (!287C6459)
					{
						num += 1488812164U;
						num = 1377108417U % num;
						num7 = checked((int)this.59462865);
					}
					else
					{
						num = (2123115000U | num);
						num7 = checked((int)((ulong)this.59462865));
						num += 3525887938U;
					}
					num = 1960652448U << (int)num;
					return num7;
				}
				goto IL_42;
				Block_13:
				long num8;
				if (!287C6459)
				{
					num = 210436142U / num;
					num8 = this.59462865;
				}
				else
				{
					num ^= 2082633533U;
					ulong num9 = (ulong)this.59462865;
					num = 1581921819U << (int)num;
					num8 = checked((long)num9);
					num += 2262247316U;
				}
				num %= 1048056097U;
				return num8;
				Block_15:
				num = 308621409U * num;
				ushort num10;
				checked
				{
					byte b4;
					if (!287C6459)
					{
						b4 = (byte)this.59462865;
					}
					else
					{
						num = (612326822U | num);
						num = (162034808U ^ num);
						b4 = (byte)((ulong)this.59462865);
						num ^= 162623612U;
					}
					return b4;
					Block_19:
					num &= 300704268U;
					num10 = (ushort)this.59462865;
					goto IL_388;
					Block_20:
					num10 = (ushort)((uint)this.59462865);
				}
				num += 2206996597U;
				IL_388:
				return num10;
				Block_22:
				uint num11 = (uint)this.59462865;
				num = 874660188U + num;
				uint num12 = num11;
				goto IL_3E1;
				IL_3C3:
				ulong num13 = (ulong)this.59462865;
				num = 1729127465U >> (int)num;
				num12 = checked((uint)num13);
				num += 1023543091U;
				IL_3E1:
				num = 359862515U % num;
				return num12;
				Block_23:
				num = 1586049320U / num;
				num >>= 23;
				ulong num14 = checked((ulong)this.59462865);
				goto IL_44A;
				Block_24:
				num <<= 24;
				num14 = (ulong)this.59462865;
				num += 3439329280U;
				IL_44A:
				num = 71056407U + num;
				return num14;
				Block_25:
				num = (835865725U | num);
				double num15 = (double)this.59462865;
				num = 1872379938U % num;
				double num16 = num15;
				goto IL_4B3;
				Block_26:
				double num17 = this.59462865;
				num ^= 1641555167U;
				num16 = num17;
				num ^= 1781283474U;
				IL_4B3:
				num = 1110971661U / num;
				return num16;
				IL_4C1:
				num = (1466829437U | num);
				throw new ArgumentException();
			}
			IL_42:
			num = (2083198335U & num);
			value = this.59462865;
			IL_80:
			num = (1067713425U & num);
			IntPtr intPtr = new IntPtr(value);
			num = (1122727625U & num);
			return intPtr;
		}

		// Token: 0x04000199 RID: 409
		private long 59462865;
	}

	// Token: 0x0200004F RID: 79
	private sealed class 7B032626 : 097E1C51.4A781DBA
	{
		// Token: 0x060002B6 RID: 694 RVA: 0x0030124C File Offset: 0x0018164C
		public 7B032626(float 52F83C3E)
		{
			uint num = 617311679U;
			do
			{
				base..ctor();
				num = 1755653927U % num;
				num = 1006534039U - num;
				this.72353483 = 52F83C3E;
			}
			while (num / 1071259468U != 0U);
		}

		// Token: 0x060002B7 RID: 695 RVA: 0x00301288 File Offset: 0x00181688
		public override Type 5F93D4ED()
		{
			return typeof(float);
		}

		// Token: 0x060002B8 RID: 696 RVA: 0x003012A8 File Offset: 0x001816A8
		public override TypeCode 6DFD59A4()
		{
			uint num = 1755388192U;
			return (TypeCode)(num ^ 1755388205U);
		}

		// Token: 0x060002B9 RID: 697 RVA: 0x003012C4 File Offset: 0x001816C4
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.7B032626(this.72353483);
		}

		// Token: 0x060002BA RID: 698 RVA: 0x003012E4 File Offset: 0x001816E4
		public override object 04F0F15D()
		{
			return this.72353483;
		}

		// Token: 0x060002BB RID: 699 RVA: 0x00301304 File Offset: 0x00181704
		public override void 360F4E2C(object 01E67A6E)
		{
			uint num = 1981957883U;
			if (443685846U <= num)
			{
				do
				{
					float num2 = Convert.ToSingle(01E67A6E);
					num = 191583134U + num;
					this.72353483 = num2;
				}
				while (num == 1211651243U);
			}
		}

		// Token: 0x060002BC RID: 700 RVA: 0x00301344 File Offset: 0x00181744
		public override bool C77753F7()
		{
			return Convert.ToBoolean(this.72353483);
		}

		// Token: 0x060002BD RID: 701 RVA: 0x0030135C File Offset: 0x0018175C
		public override sbyte 52B3F2EC()
		{
			return (sbyte)this.72353483;
		}

		// Token: 0x060002BE RID: 702 RVA: 0x00301378 File Offset: 0x00181778
		public override short BDAC8EA4()
		{
			return (short)this.72353483;
		}

		// Token: 0x060002BF RID: 703 RVA: 0x00301394 File Offset: 0x00181794
		public override int B9179A8D()
		{
			return (int)this.72353483;
		}

		// Token: 0x060002C0 RID: 704 RVA: 0x003013B0 File Offset: 0x001817B0
		public override long 8EC1A2BC()
		{
			return (long)this.72353483;
		}

		// Token: 0x060002C1 RID: 705 RVA: 0x003013C4 File Offset: 0x001817C4
		public override char A773FD03()
		{
			return (char)this.72353483;
		}

		// Token: 0x060002C2 RID: 706 RVA: 0x003013E0 File Offset: 0x001817E0
		public override byte 19201189()
		{
			return (byte)this.72353483;
		}

		// Token: 0x060002C3 RID: 707 RVA: 0x003013C4 File Offset: 0x001817C4
		public override ushort 86B35C1E()
		{
			return (ushort)this.72353483;
		}

		// Token: 0x060002C4 RID: 708 RVA: 0x003013FC File Offset: 0x001817FC
		public override uint 2CD32589()
		{
			return (uint)this.72353483;
		}

		// Token: 0x060002C5 RID: 709 RVA: 0x00301418 File Offset: 0x00181818
		public override ulong 083A097E()
		{
			return (ulong)this.72353483;
		}

		// Token: 0x060002C6 RID: 710 RVA: 0x00301434 File Offset: 0x00181834
		public override float E1E85554()
		{
			return this.72353483;
		}

		// Token: 0x060002C7 RID: 711 RVA: 0x00301450 File Offset: 0x00181850
		public override double D87DFA30()
		{
			uint num = 1922190367U;
			num = (2044462636U | num);
			return (double)this.72353483;
		}

		// Token: 0x060002C8 RID: 712 RVA: 0x00301474 File Offset: 0x00181874
		public override IntPtr E3F543C7()
		{
			long value;
			if (IntPtr.Size != 4)
			{
				value = (long)this.72353483;
			}
			else
			{
				uint num = 1016748098U;
				int num2 = (int)this.72353483;
				num = 1396054222U + num;
				value = (long)num2;
				num ^= 2503557273U;
			}
			return new IntPtr(value);
		}

		// Token: 0x060002C9 RID: 713 RVA: 0x003014C4 File Offset: 0x001818C4
		public override UIntPtr 7F934522()
		{
			int size = IntPtr.Size;
			uint num = 1605517651U;
			uint num2 = num ^ 1605517655U;
			num = 1172590641U * num;
			if (size == num2)
			{
				goto IL_3F;
			}
			num >>= 18;
			if ((num ^ 1309889099U) == 0U)
			{
				goto IL_3F;
			}
			IL_33:
			ulong value = (ulong)this.72353483;
			goto IL_80;
			IL_3F:
			num |= 1341399662U;
			if (1459365618U > num)
			{
				goto IL_33;
			}
			num = 1478570650U >> (int)num;
			uint num3 = (uint)this.72353483;
			num = 1009389832U / num;
			ulong num4 = (ulong)num3;
			num = 554239061U * num;
			value = num4;
			num += 1242798355U;
			IL_80:
			num /= 297822745U;
			return new UIntPtr(value);
		}

		// Token: 0x060002CA RID: 714 RVA: 0x00301560 File Offset: 0x00181960
		public override object 9E2F1085(Type 0A872A9C, bool 60F22321)
		{
			uint num = 1762084144U;
			for (;;)
			{
				Type typeFromHandle = typeof(IntPtr);
				num >>= 8;
				if (0A872A9C == typeFromHandle)
				{
					if (num <= 266761897U)
					{
						break;
					}
				}
				else
				{
					for (;;)
					{
						IL_4B:
						num = (165033036U | num);
						if (0A872A9C == typeof(UIntPtr))
						{
							goto Block_2;
						}
						for (;;)
						{
							TypeCode typeCode2;
							if (num != 1617570571U)
							{
								num += 164834310U;
								TypeCode typeCode = Type.GetTypeCode(0A872A9C);
								num = 1432094011U >> (int)num;
								typeCode2 = typeCode;
								num >>= 7;
							}
							int num2 = typeCode2 - (TypeCode)(num ^ 16U);
							num = 847149207U + num;
							switch (num2)
							{
							case 0:
								if (1972258402U < num)
								{
									goto IL_4B;
								}
								if (60F22321)
								{
									goto IL_135;
								}
								num /= 837758141U;
								if (num >> 14 == 0U)
								{
									goto Block_8;
								}
								continue;
							case 1:
								goto IL_213;
							case 2:
								goto IL_177;
							case 3:
								goto IL_244;
							case 4:
								goto IL_1F2;
							case 5:
								goto IL_277;
							case 6:
								goto IL_2D4;
							case 7:
								goto IL_2AF;
							}
							goto Block_4;
						}
					}
					IL_135:
					num = 981231309U + num;
					if (208097272U + num != 0U)
					{
						goto Block_9;
					}
					continue;
					IL_177:
					if (num == 1288442455U)
					{
						goto IL_6B;
					}
					num = (1823873279U ^ num);
					if (!60F22321)
					{
						goto Block_11;
					}
					num = 387200627U * num;
					if (1767920242U / num == 0U)
					{
						goto Block_13;
					}
					continue;
					IL_1F2:
					if (num << 10 != 0U)
					{
						goto Block_14;
					}
					continue;
					IL_213:
					num *= 793723660U;
					if (num / 500046097U != 0U)
					{
						goto Block_15;
					}
					continue;
					IL_277:
					num = 720404110U % num;
					if (num <= 1997215590U)
					{
						goto Block_16;
					}
					continue;
					IL_2D4:
					num = 1470434668U % num;
					if (num != 551307350U)
					{
						goto Block_17;
					}
					continue;
					Block_4:
					if (671556350U + num != 0U)
					{
						num += 0U;
						goto IL_2D4;
					}
					break;
				}
			}
			IL_29:
			long num3 = (long)this.72353483;
			num = 794522356U % num;
			IntPtr intPtr = new IntPtr(num3);
			num = 1019699494U % num;
			return intPtr;
			Block_2:
			num = 1911498306U / num;
			IL_6B:
			return new UIntPtr(checked((ulong)this.72353483));
			Block_8:
			sbyte b = (sbyte)this.72353483;
			num = 815995023U / num;
			sbyte b2 = b;
			goto IL_169;
			Block_9:
			uint num4 = (uint)this.72353483;
			num = (1509838272U & num);
			sbyte b3 = (sbyte)num4;
			num = 260059515U - num;
			b2 = b3;
			num += 1780298836U;
			IL_169:
			num = (1404900419U | num);
			return b2;
			Block_11:
			num = (158889585U | num);
			short num6;
			if (1691421408U % num != 0U)
			{
				short num5 = (short)this.72353483;
				num %= 1111779807U;
				num6 = num5;
				goto IL_1EC;
			}
			goto IL_29;
			Block_13:
			uint num7 = (uint)this.72353483;
			num = 279337017U + num;
			short num8 = (short)num7;
			num = 1193094835U + num;
			num6 = num8;
			num += 4062166623U;
			IL_1EC:
			return num6;
			Block_14:
			int num9 = (int)this.72353483;
			num = (552162798U ^ num);
			return num9;
			Block_15:
			num = (1300766986U ^ num);
			byte b4 = checked((byte)this.72353483);
			num = 1711296142U * num;
			return b4;
			IL_244:
			num = (1186927133U & num);
			num = 1459820683U / num;
			ushort num10 = (ushort)this.72353483;
			num = 1909791541U >> (int)num;
			ushort num11 = num10;
			num = 518010265U * num;
			return num11;
			Block_16:
			num = 854990136U - num;
			uint num12 = (uint)this.72353483;
			num |= 1415335194U;
			uint num13 = num12;
			num += 743117893U;
			return num13;
			IL_2AF:
			num &= 558198913U;
			num *= 2135170498U;
			ulong num14 = (ulong)this.72353483;
			num *= 1391607673U;
			return num14;
			Block_17:
			throw new ArgumentException();
		}

		// Token: 0x0400019A RID: 410
		private float 72353483;
	}

	// Token: 0x02000050 RID: 80
	private sealed class 4DA65608 : 097E1C51.4A781DBA
	{
		// Token: 0x060002CB RID: 715 RVA: 0x0030185C File Offset: 0x00181C5C
		public 4DA65608(double 25B00FBF)
		{
			uint num = 1126840625U;
			num *= 1446468971U;
			base..ctor();
			num *= 663305096U;
			num = (356737663U & num);
			num = 758999374U << (int)num;
			this.77795C25 = 25B00FBF;
		}

		// Token: 0x060002CC RID: 716 RVA: 0x003018A4 File Offset: 0x00181CA4
		public override Type 5F93D4ED()
		{
			return typeof(double);
		}

		// Token: 0x060002CD RID: 717 RVA: 0x003018BC File Offset: 0x00181CBC
		public override TypeCode 6DFD59A4()
		{
			return TypeCode.Double;
		}

		// Token: 0x060002CE RID: 718 RVA: 0x003018CC File Offset: 0x00181CCC
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.4DA65608(this.77795C25);
		}

		// Token: 0x060002CF RID: 719 RVA: 0x003018E4 File Offset: 0x00181CE4
		public override object 04F0F15D()
		{
			uint num = 780611745U;
			num = 1440966011U * num;
			return this.77795C25;
		}

		// Token: 0x060002D0 RID: 720 RVA: 0x0030190C File Offset: 0x00181D0C
		public override void 360F4E2C(object 4C2F55F3)
		{
			uint num = 1658851305U;
			if (num >= 227375770U)
			{
				num = (405214757U & num);
				double num2 = (double)4C2F55F3;
				num ^= 2108257673U;
				this.77795C25 = num2;
			}
		}

		// Token: 0x060002D1 RID: 721 RVA: 0x00301948 File Offset: 0x00181D48
		public override bool C77753F7()
		{
			uint num = 1870100468U;
			double value = this.77795C25;
			num <<= 29;
			return Convert.ToBoolean(value);
		}

		// Token: 0x060002D2 RID: 722 RVA: 0x00301970 File Offset: 0x00181D70
		public override sbyte 52B3F2EC()
		{
			uint num = 547187552U;
			num += 568484332U;
			return (sbyte)this.77795C25;
		}

		// Token: 0x060002D3 RID: 723 RVA: 0x00301994 File Offset: 0x00181D94
		public override short BDAC8EA4()
		{
			uint num = 2123983742U;
			num = 794776536U / num;
			short num2 = (short)this.77795C25;
			num *= 193025239U;
			return num2;
		}

		// Token: 0x060002D4 RID: 724 RVA: 0x003019C0 File Offset: 0x00181DC0
		public override int B9179A8D()
		{
			uint num = 1632598204U;
			num /= 798185388U;
			int num2 = (int)this.77795C25;
			num |= 1460284394U;
			return num2;
		}

		// Token: 0x060002D5 RID: 725 RVA: 0x003019EC File Offset: 0x00181DEC
		public override long 8EC1A2BC()
		{
			return (long)this.77795C25;
		}

		// Token: 0x060002D6 RID: 726 RVA: 0x00301A08 File Offset: 0x00181E08
		public override char A773FD03()
		{
			return (char)this.77795C25;
		}

		// Token: 0x060002D7 RID: 727 RVA: 0x00301A24 File Offset: 0x00181E24
		public override byte 19201189()
		{
			uint num = 591015737U;
			byte b = (byte)this.77795C25;
			num = 1296439331U % num;
			return b;
		}

		// Token: 0x060002D8 RID: 728 RVA: 0x00301A08 File Offset: 0x00181E08
		public override ushort 86B35C1E()
		{
			return (ushort)this.77795C25;
		}

		// Token: 0x060002D9 RID: 729 RVA: 0x00301A48 File Offset: 0x00181E48
		public override uint 2CD32589()
		{
			return (uint)this.77795C25;
		}

		// Token: 0x060002DA RID: 730 RVA: 0x00301A64 File Offset: 0x00181E64
		public override ulong 083A097E()
		{
			return (ulong)this.77795C25;
		}

		// Token: 0x060002DB RID: 731 RVA: 0x00301A78 File Offset: 0x00181E78
		public override float E1E85554()
		{
			return (float)this.77795C25;
		}

		// Token: 0x060002DC RID: 732 RVA: 0x00301A94 File Offset: 0x00181E94
		public override double D87DFA30()
		{
			uint num = 1492460196U;
			num += 1593313674U;
			return this.77795C25;
		}

		// Token: 0x060002DD RID: 733 RVA: 0x00301AB8 File Offset: 0x00181EB8
		public override IntPtr E3F543C7()
		{
			int size = IntPtr.Size;
			int num = 4;
			uint num2 = 1826754605U;
			long value;
			if (size != num)
			{
				long num3 = (long)this.77795C25;
				num2 = (137503968U | num2);
				value = num3;
			}
			else
			{
				num2 = 1018173620U - num2;
				num2 *= 1964264081U;
				int num4 = (int)this.77795C25;
				num2 %= 615912586U;
				value = (long)num4;
				num2 += 1611967646U;
			}
			num2 &= 1261050079U;
			return new IntPtr(value);
		}

		// Token: 0x060002DE RID: 734 RVA: 0x00301B20 File Offset: 0x00181F20
		public override UIntPtr 7F934522()
		{
			uint num;
			ulong value;
			while (IntPtr.Size != 4)
			{
				num = 1497705608U;
				if (210766943U != num)
				{
					ulong num2 = (ulong)this.77795C25;
					num = 1126850156U % num;
					value = num2;
					IL_46:
					num *= 1734291044U;
					return new UIntPtr(value);
				}
			}
			uint num3 = (uint)this.77795C25;
			num = 832707163U;
			value = (ulong)num3;
			num ^= 1921532983U;
			goto IL_46;
		}

		// Token: 0x060002DF RID: 735 RVA: 0x00301B80 File Offset: 0x00181F80
		public override object 9E2F1085(Type 2E9E6929, bool 38972713)
		{
			uint num;
			for (;;)
			{
				num = 980824579U;
				RuntimeTypeHandle handle = typeof(IntPtr).TypeHandle;
				num &= 1205148267U;
				Type typeFromHandle = Type.GetTypeFromHandle(handle);
				num = 274728864U >> (int)num;
				if (2E9E6929 == typeFromHandle)
				{
					break;
				}
				if (1558054823U >= num)
				{
					Type typeFromHandle2 = typeof(UIntPtr);
					num &= 522607849U;
					TypeCode typeCode;
					if (2E9E6929 == typeFromHandle2)
					{
						num = 1979463448U * num;
						if (2023905118U - num != 0U)
						{
							goto Block_3;
						}
					}
					else
					{
						num = 1513185026U >> (int)num;
						if (num == 1883524185U)
						{
							continue;
						}
						typeCode = Type.GetTypeCode(2E9E6929);
						if (num == 1606419731U)
						{
							continue;
						}
					}
					for (;;)
					{
						object obj = typeCode;
						num = 599663821U >> (int)num;
						object obj2 = num - 149915950U;
						num = 686386265U / num;
						object obj3 = obj - obj2;
						num = 607729098U / num;
						switch (obj3)
						{
						case 0:
							goto IL_139;
						case 1:
							goto IL_223;
						case 2:
							num %= 759125353U;
							if ((1511921932U & num) != 0U)
							{
								goto Block_7;
							}
							continue;
						case 3:
							goto IL_24B;
						case 4:
							goto IL_1DD;
						case 5:
							goto IL_274;
						case 6:
							goto IL_1FA;
						case 7:
							goto IL_29C;
						case 8:
							goto IL_2BD;
						case 9:
							goto IL_2B1;
						}
						goto Block_5;
					}
					IL_1FA:
					if ((171451818U ^ num) != 0U)
					{
						goto Block_9;
					}
					continue;
					IL_223:
					num = 1863737115U - num;
					if (1132162577U != num)
					{
						goto Block_10;
					}
					continue;
					IL_24B:
					num -= 1230728454U;
					if ((1520523504U ^ num) != 0U)
					{
						goto Block_11;
					}
					continue;
					IL_274:
					num *= 241965683U;
					if (1687098058U < num)
					{
						goto Block_12;
					}
				}
			}
			num = (703409156U ^ num);
			checked
			{
				long value = (long)this.77795C25;
				num = (1476158822U & num);
				return new IntPtr(value);
				Block_3:
				ulong value2 = (ulong)this.77795C25;
				num <<= 3;
				UIntPtr uintPtr = new UIntPtr(value2);
				num = 740430993U << (int)num;
				return uintPtr;
				Block_5:;
			}
			num += 0U;
			goto IL_2BD;
			IL_139:
			num += 633954447U;
			num = 657850449U - num;
			checked
			{
				sbyte b;
				if (!38972713)
				{
					b = (sbyte)this.77795C25;
				}
				else
				{
					num /= 1714779401U;
					num = (1551830082U & num);
					sbyte b2 = (sbyte)((uint)this.77795C25);
					num %= 1040272031U;
					b = b2;
					num ^= 4166931026U;
				}
				return b;
				Block_7:
				short num2;
				if (!38972713)
				{
					num ^= 1946034513U;
					unchecked
					{
						num *= 2056017191U;
					}
					num2 = (short)this.77795C25;
				}
				else
				{
					uint num3 = (uint)this.77795C25;
					num = (856317798U | num);
					num2 = (short)num3;
					unchecked
					{
						num += 614316255U;
					}
				}
				return num2;
				IL_1DD:
				int num4 = (int)this.77795C25;
				num ^= 1504059720U;
				int num5 = num4;
				num /= 1350766592U;
				return num5;
				Block_9:
				num = 1050415555U % num;
				long num6 = (long)this.77795C25;
				num = (483097590U & num);
				return num6;
				Block_10:
				byte b3 = (byte)this.77795C25;
				num = unchecked(2108978785U * num);
				return b3;
				Block_11:
				num = (1732587854U | num);
				return (ushort)this.77795C25;
				Block_12:
				uint num7 = (uint)this.77795C25;
				num >>= 25;
				return num7;
				IL_29C:
				num = unchecked(1379425137U * num);
				return (ulong)this.77795C25;
				IL_2B1:
				return this.77795C25;
				IL_2BD:
				throw new ArgumentException();
			}
		}

		// Token: 0x0400019B RID: 411
		private double 77795C25;
	}

	// Token: 0x02000051 RID: 81
	private sealed class 703962A5 : 097E1C51.4A781DBA
	{
		// Token: 0x060002E0 RID: 736 RVA: 0x00301E50 File Offset: 0x00182250
		public 703962A5(string 609D036F)
		{
			uint num = 1510300225U;
			num = 1038568421U % num;
			num %= 21318074U;
			this.2B9F0718 = 609D036F;
		}

		// Token: 0x060002E1 RID: 737 RVA: 0x00301E80 File Offset: 0x00182280
		public override Type 5F93D4ED()
		{
			return typeof(string);
		}

		// Token: 0x060002E2 RID: 738 RVA: 0x002F0E0C File Offset: 0x0017120C
		public override TypeCode 6DFD59A4()
		{
			uint num = 2080718108U;
			return (TypeCode)(num ^ 2080718109U);
		}

		// Token: 0x060002E3 RID: 739 RVA: 0x00301E98 File Offset: 0x00182298
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.703962A5(this.2B9F0718);
		}

		// Token: 0x060002E4 RID: 740 RVA: 0x00301EB8 File Offset: 0x001822B8
		public override object 04F0F15D()
		{
			return this.2B9F0718;
		}

		// Token: 0x060002E5 RID: 741 RVA: 0x00301ED4 File Offset: 0x001822D4
		public override void 360F4E2C(object 2F786DD0)
		{
			uint num = 1666414676U;
			do
			{
				num /= 1985042065U;
				num = (1143496848U & num);
				string text;
				if (2F786DD0 == null)
				{
					num += 209079311U;
					text = null;
				}
				else
				{
					num += 1932077743U;
					text = Convert.ToString(2F786DD0);
					num += 2571968864U;
				}
				this.2B9F0718 = text;
			}
			while (num >> 1 == 0U);
		}

		// Token: 0x060002E6 RID: 742 RVA: 0x00301F34 File Offset: 0x00182334
		public override bool C77753F7()
		{
			return this.2B9F0718 != null;
		}

		// Token: 0x060002E7 RID: 743 RVA: 0x00301EB8 File Offset: 0x001822B8
		public override string ToString()
		{
			return this.2B9F0718;
		}

		// Token: 0x0400019C RID: 412
		private string 2B9F0718;
	}

	// Token: 0x02000052 RID: 82
	private sealed class 77B017D1 : 097E1C51.0A706168
	{
		// Token: 0x060002E8 RID: 744 RVA: 0x00301F4C File Offset: 0x0018234C
		public 77B017D1(short 30E14BCB)
		{
			uint num = 921705337U;
			base..ctor();
			num = 465665557U - num;
			do
			{
				this.13632755 = 30E14BCB;
			}
			while (num << 31 != 0U);
		}

		// Token: 0x060002E9 RID: 745 RVA: 0x00301F80 File Offset: 0x00182380
		public override Type 5F93D4ED()
		{
			return typeof(short);
		}

		// Token: 0x060002EA RID: 746 RVA: 0x00301F98 File Offset: 0x00182398
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.77B017D1(this.13632755);
		}

		// Token: 0x060002EB RID: 747 RVA: 0x00301FB8 File Offset: 0x001823B8
		public override object 04F0F15D()
		{
			return this.13632755;
		}

		// Token: 0x060002EC RID: 748 RVA: 0x00301FD8 File Offset: 0x001823D8
		public override void 360F4E2C(object 116D6355)
		{
			uint num;
			do
			{
				num = 1157696053U;
				this.13632755 = Convert.ToInt16(116D6355);
			}
			while (num / 674449759U == 0U);
		}

		// Token: 0x060002ED RID: 749 RVA: 0x00302004 File Offset: 0x00182404
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			return new 097E1C51.14C26E2D(this.B9179A8D());
		}

		// Token: 0x060002EE RID: 750 RVA: 0x00302024 File Offset: 0x00182424
		public override sbyte 52B3F2EC()
		{
			return (sbyte)this.13632755;
		}

		// Token: 0x060002EF RID: 751 RVA: 0x00302040 File Offset: 0x00182440
		public override byte 19201189()
		{
			uint num = 241184758U;
			byte b = (byte)this.13632755;
			num = 180972461U % num;
			return b;
		}

		// Token: 0x060002F0 RID: 752 RVA: 0x00302064 File Offset: 0x00182464
		public override short BDAC8EA4()
		{
			uint num = 1154678949U;
			num <<= 12;
			return this.13632755;
		}

		// Token: 0x060002F1 RID: 753 RVA: 0x00302088 File Offset: 0x00182488
		public override ushort 86B35C1E()
		{
			uint num = 454376855U;
			num |= 760887304U;
			return (ushort)this.13632755;
		}

		// Token: 0x060002F2 RID: 754 RVA: 0x00302064 File Offset: 0x00182464
		public override int B9179A8D()
		{
			uint num = 1154678949U;
			num <<= 12;
			return (int)this.13632755;
		}

		// Token: 0x060002F3 RID: 755 RVA: 0x00302064 File Offset: 0x00182464
		public override uint 2CD32589()
		{
			uint num = 1154678949U;
			num <<= 12;
			return (uint)this.13632755;
		}

		// Token: 0x0400019D RID: 413
		private short 13632755;
	}

	// Token: 0x02000053 RID: 83
	private sealed class 7EDB7F26 : 097E1C51.0A706168
	{
		// Token: 0x060002F4 RID: 756 RVA: 0x003020AC File Offset: 0x001824AC
		public 7EDB7F26(ushort 66DC3E64)
		{
			uint num;
			do
			{
				num = 1229740334U;
				this.7C0A6F8F = 66DC3E64;
			}
			while (1805328886U == num);
		}

		// Token: 0x060002F5 RID: 757 RVA: 0x003020D8 File Offset: 0x001824D8
		public override Type 5F93D4ED()
		{
			return typeof(ushort);
		}

		// Token: 0x060002F6 RID: 758 RVA: 0x003020F0 File Offset: 0x001824F0
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 2093186344U;
			num = 1384860989U >> (int)num;
			ushort 66DC3E = this.7C0A6F8F;
			num = 413082946U >> (int)num;
			return new 097E1C51.7EDB7F26(66DC3E);
		}

		// Token: 0x060002F7 RID: 759 RVA: 0x0030212C File Offset: 0x0018252C
		public override object 04F0F15D()
		{
			return this.7C0A6F8F;
		}

		// Token: 0x060002F8 RID: 760 RVA: 0x0030214C File Offset: 0x0018254C
		public override void 360F4E2C(object 727E320A)
		{
			this.7C0A6F8F = Convert.ToUInt16(727E320A);
		}

		// Token: 0x060002F9 RID: 761 RVA: 0x00302004 File Offset: 0x00182404
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			return new 097E1C51.14C26E2D(this.B9179A8D());
		}

		// Token: 0x060002FA RID: 762 RVA: 0x0030216C File Offset: 0x0018256C
		public override sbyte 52B3F2EC()
		{
			uint num = 315892481U;
			num = (1326580043U | num);
			return (sbyte)this.7C0A6F8F;
		}

		// Token: 0x060002FB RID: 763 RVA: 0x00302190 File Offset: 0x00182590
		public override byte 19201189()
		{
			uint num = 1305282840U;
			num = (1025202202U ^ num);
			return (byte)this.7C0A6F8F;
		}

		// Token: 0x060002FC RID: 764 RVA: 0x003021B4 File Offset: 0x001825B4
		public override short BDAC8EA4()
		{
			return (short)this.7C0A6F8F;
		}

		// Token: 0x060002FD RID: 765 RVA: 0x003021D0 File Offset: 0x001825D0
		public override ushort 86B35C1E()
		{
			return this.7C0A6F8F;
		}

		// Token: 0x060002FE RID: 766 RVA: 0x003021D0 File Offset: 0x001825D0
		public override int B9179A8D()
		{
			return (int)this.7C0A6F8F;
		}

		// Token: 0x060002FF RID: 767 RVA: 0x003021D0 File Offset: 0x001825D0
		public override uint 2CD32589()
		{
			return (uint)this.7C0A6F8F;
		}

		// Token: 0x0400019E RID: 414
		private ushort 7C0A6F8F;
	}

	// Token: 0x02000054 RID: 84
	private sealed class 1E233DE9 : 097E1C51.0A706168
	{
		// Token: 0x06000300 RID: 768 RVA: 0x003021E4 File Offset: 0x001825E4
		public 1E233DE9(bool 47466F97)
		{
			for (;;)
			{
				uint num = 1231515560U;
				base..ctor();
				if ((num & 1349141498U) != 0U)
				{
					num = 2073638547U - num;
					num %= 93287423U;
					this.155B7AED = 47466F97;
					if (num % 863653294U != 0U)
					{
						break;
					}
				}
			}
		}

		// Token: 0x06000301 RID: 769 RVA: 0x0030222C File Offset: 0x0018262C
		public override Type 5F93D4ED()
		{
			return typeof(bool);
		}

		// Token: 0x06000302 RID: 770 RVA: 0x0030224C File Offset: 0x0018264C
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 368998853U;
			num *= 1489375284U;
			bool 47466F = this.155B7AED;
			num %= 363203496U;
			return new 097E1C51.1E233DE9(47466F);
		}

		// Token: 0x06000303 RID: 771 RVA: 0x0030227C File Offset: 0x0018267C
		public override object 04F0F15D()
		{
			return this.155B7AED;
		}

		// Token: 0x06000304 RID: 772 RVA: 0x00302294 File Offset: 0x00182694
		public override void 360F4E2C(object 6E177E2D)
		{
			uint num = 2117295543U;
			do
			{
				num ^= 782193654U;
				num = 348201600U >> (int)num;
				this.155B7AED = Convert.ToBoolean(6E177E2D);
			}
			while ((1090335123U ^ num) == 0U);
		}

		// Token: 0x06000305 RID: 773 RVA: 0x00302004 File Offset: 0x00182404
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			return new 097E1C51.14C26E2D(this.B9179A8D());
		}

		// Token: 0x06000306 RID: 774 RVA: 0x003022D8 File Offset: 0x001826D8
		public override int B9179A8D()
		{
			uint num = 1809386036U;
			if (num >= 1448963521U)
			{
				num = 881991620U / num;
				bool flag = this.155B7AED;
				num *= 141110420U;
				if (flag)
				{
					num /= 586619093U;
					return (int)(num ^ 1U);
				}
			}
			num ^= 827749238U;
			return (int)(num ^ 827749238U);
		}

		// Token: 0x0400019F RID: 415
		private bool 155B7AED;
	}

	// Token: 0x02000055 RID: 85
	private sealed class 0496456E : 097E1C51.0A706168
	{
		// Token: 0x06000307 RID: 775 RVA: 0x00302330 File Offset: 0x00182730
		public 0496456E(char 02984074)
		{
			uint num = 566822076U;
			num = 2092828586U / num;
			base..ctor();
			do
			{
				this.68246987 = 02984074;
			}
			while (num > 1391163239U);
		}

		// Token: 0x06000308 RID: 776 RVA: 0x00302364 File Offset: 0x00182764
		public override Type 5F93D4ED()
		{
			return typeof(char);
		}

		// Token: 0x06000309 RID: 777 RVA: 0x0030237C File Offset: 0x0018277C
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 1816687780U;
			num <<= 9;
			return new 097E1C51.0496456E(this.68246987);
		}

		// Token: 0x0600030A RID: 778 RVA: 0x003023A4 File Offset: 0x001827A4
		public override object 04F0F15D()
		{
			uint num = 769877906U;
			char c = this.68246987;
			num &= 1592347236U;
			return c;
		}

		// Token: 0x0600030B RID: 779 RVA: 0x003023CC File Offset: 0x001827CC
		public override void 360F4E2C(object 09863E4F)
		{
			uint num;
			do
			{
				char c = Convert.ToChar(09863E4F);
				num = 1949305821U;
				this.68246987 = c;
			}
			while (num == 1751067767U);
		}

		// Token: 0x0600030C RID: 780 RVA: 0x00302004 File Offset: 0x00182404
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			return new 097E1C51.14C26E2D(this.B9179A8D());
		}

		// Token: 0x0600030D RID: 781 RVA: 0x003023F8 File Offset: 0x001827F8
		public override sbyte 52B3F2EC()
		{
			uint num = 2022468338U;
			num = (779837204U ^ num);
			sbyte b = (sbyte)this.68246987;
			num = (1739744049U | num);
			return b;
		}

		// Token: 0x0600030E RID: 782 RVA: 0x00302424 File Offset: 0x00182824
		public override byte 19201189()
		{
			uint num = 2058486543U;
			byte b = (byte)this.68246987;
			num &= 1660581069U;
			return b;
		}

		// Token: 0x0600030F RID: 783 RVA: 0x00302448 File Offset: 0x00182848
		public override short BDAC8EA4()
		{
			return (short)this.68246987;
		}

		// Token: 0x06000310 RID: 784 RVA: 0x00302464 File Offset: 0x00182864
		public override ushort 86B35C1E()
		{
			return (ushort)this.68246987;
		}

		// Token: 0x06000311 RID: 785 RVA: 0x00302464 File Offset: 0x00182864
		public override int B9179A8D()
		{
			return (int)this.68246987;
		}

		// Token: 0x06000312 RID: 786 RVA: 0x00302464 File Offset: 0x00182864
		public override uint 2CD32589()
		{
			return (uint)this.68246987;
		}

		// Token: 0x040001A0 RID: 416
		private char 68246987;
	}

	// Token: 0x02000056 RID: 86
	private sealed class 226653A3 : 097E1C51.0A706168
	{
		// Token: 0x06000313 RID: 787 RVA: 0x00302480 File Offset: 0x00182880
		public 226653A3(byte 3392633E)
		{
			uint num;
			do
			{
				num = 89986893U;
				this.5CB54CED = 3392633E;
			}
			while (num == 138887562U);
		}

		// Token: 0x06000314 RID: 788 RVA: 0x003024AC File Offset: 0x001828AC
		public override Type 5F93D4ED()
		{
			uint num = 1495361095U;
			RuntimeTypeHandle handle = typeof(byte).TypeHandle;
			num = 663579471U << (int)num;
			return Type.GetTypeFromHandle(handle);
		}

		// Token: 0x06000315 RID: 789 RVA: 0x003024D8 File Offset: 0x001828D8
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.226653A3(this.5CB54CED);
		}

		// Token: 0x06000316 RID: 790 RVA: 0x003024F8 File Offset: 0x001828F8
		public override object 04F0F15D()
		{
			uint num = 250688230U;
			byte b = this.5CB54CED;
			num = 1290744749U << (int)num;
			return b;
		}

		// Token: 0x06000317 RID: 791 RVA: 0x00302524 File Offset: 0x00182924
		public override void 360F4E2C(object 268163D5)
		{
			uint num;
			do
			{
				num = 203502279U;
				byte b = Convert.ToByte(268163D5);
				num = 183464425U / num;
				this.5CB54CED = b;
			}
			while (1620662003U * num != 0U);
		}

		// Token: 0x06000318 RID: 792 RVA: 0x00302004 File Offset: 0x00182404
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			return new 097E1C51.14C26E2D(this.B9179A8D());
		}

		// Token: 0x06000319 RID: 793 RVA: 0x00302558 File Offset: 0x00182958
		public override sbyte 52B3F2EC()
		{
			return (sbyte)this.5CB54CED;
		}

		// Token: 0x0600031A RID: 794 RVA: 0x00302574 File Offset: 0x00182974
		public override byte 19201189()
		{
			uint num = 1041499042U;
			num %= 1470643876U;
			return this.5CB54CED;
		}

		// Token: 0x0600031B RID: 795 RVA: 0x00302574 File Offset: 0x00182974
		public override short BDAC8EA4()
		{
			uint num = 1041499042U;
			num %= 1470643876U;
			return (short)this.5CB54CED;
		}

		// Token: 0x0600031C RID: 796 RVA: 0x00302574 File Offset: 0x00182974
		public override ushort 86B35C1E()
		{
			uint num = 1041499042U;
			num %= 1470643876U;
			return (ushort)this.5CB54CED;
		}

		// Token: 0x0600031D RID: 797 RVA: 0x00302574 File Offset: 0x00182974
		public override int B9179A8D()
		{
			uint num = 1041499042U;
			num %= 1470643876U;
			return (int)this.5CB54CED;
		}

		// Token: 0x0600031E RID: 798 RVA: 0x00302574 File Offset: 0x00182974
		public override uint 2CD32589()
		{
			uint num = 1041499042U;
			num %= 1470643876U;
			return (uint)this.5CB54CED;
		}

		// Token: 0x040001A1 RID: 417
		private byte 5CB54CED;
	}

	// Token: 0x02000057 RID: 87
	private sealed class 63674386 : 097E1C51.0A706168
	{
		// Token: 0x0600031F RID: 799 RVA: 0x00302598 File Offset: 0x00182998
		public 63674386(sbyte 6DB62FEE)
		{
			uint num = 1191862958U;
			base..ctor();
			num = 322244158U * num;
			do
			{
				this.516364A1 = 6DB62FEE;
			}
			while ((num ^ 541074034U) == 0U);
		}

		// Token: 0x06000320 RID: 800 RVA: 0x003025CC File Offset: 0x001829CC
		public override Type 5F93D4ED()
		{
			return typeof(sbyte);
		}

		// Token: 0x06000321 RID: 801 RVA: 0x003025EC File Offset: 0x001829EC
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 109789432U;
			num >>= 1;
			sbyte 6DB62FEE = this.516364A1;
			num = 1907494034U + num;
			return new 097E1C51.63674386(6DB62FEE);
		}

		// Token: 0x06000322 RID: 802 RVA: 0x0030261C File Offset: 0x00182A1C
		public override object 04F0F15D()
		{
			return this.516364A1;
		}

		// Token: 0x06000323 RID: 803 RVA: 0x0030263C File Offset: 0x00182A3C
		public override void 360F4E2C(object 059D5377)
		{
			uint num;
			do
			{
				num = 60624771U;
				this.516364A1 = Convert.ToSByte(059D5377);
			}
			while (1166034393U < num);
		}

		// Token: 0x06000324 RID: 804 RVA: 0x00302004 File Offset: 0x00182404
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			return new 097E1C51.14C26E2D(this.B9179A8D());
		}

		// Token: 0x06000325 RID: 805 RVA: 0x00302668 File Offset: 0x00182A68
		public override sbyte 52B3F2EC()
		{
			return this.516364A1;
		}

		// Token: 0x06000326 RID: 806 RVA: 0x00302684 File Offset: 0x00182A84
		public override byte 19201189()
		{
			return (byte)this.516364A1;
		}

		// Token: 0x06000327 RID: 807 RVA: 0x00302668 File Offset: 0x00182A68
		public override short BDAC8EA4()
		{
			return (short)this.516364A1;
		}

		// Token: 0x06000328 RID: 808 RVA: 0x00302698 File Offset: 0x00182A98
		public override ushort 86B35C1E()
		{
			uint num = 1055281346U;
			num = 1243092863U >> (int)num;
			return (ushort)this.516364A1;
		}

		// Token: 0x06000329 RID: 809 RVA: 0x00302668 File Offset: 0x00182A68
		public override int B9179A8D()
		{
			return (int)this.516364A1;
		}

		// Token: 0x0600032A RID: 810 RVA: 0x00302668 File Offset: 0x00182A68
		public override uint 2CD32589()
		{
			return (uint)this.516364A1;
		}

		// Token: 0x040001A2 RID: 418
		private sbyte 516364A1;
	}

	// Token: 0x02000058 RID: 88
	private sealed class 79942DAA : 097E1C51.0A706168
	{
		// Token: 0x0600032B RID: 811 RVA: 0x003026C0 File Offset: 0x00182AC0
		public 79942DAA(uint 5BCA48B0)
		{
			uint num = 741015788U;
			base..ctor();
			num /= 1187534006U;
			this.41072A98 = 5BCA48B0;
		}

		// Token: 0x0600032C RID: 812 RVA: 0x003026E8 File Offset: 0x00182AE8
		public override Type 5F93D4ED()
		{
			return typeof(uint);
		}

		// Token: 0x0600032D RID: 813 RVA: 0x00302708 File Offset: 0x00182B08
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 292382187U;
			uint 5BCA48B = this.41072A98;
			num <<= 21;
			return new 097E1C51.79942DAA(5BCA48B);
		}

		// Token: 0x0600032E RID: 814 RVA: 0x00302730 File Offset: 0x00182B30
		public override object 04F0F15D()
		{
			return this.41072A98;
		}

		// Token: 0x0600032F RID: 815 RVA: 0x00302748 File Offset: 0x00182B48
		public override void 360F4E2C(object 58D82DC4)
		{
			uint num;
			do
			{
				num = 1970036839U;
				this.41072A98 = Convert.ToUInt32(58D82DC4);
			}
			while (num <= 437535499U);
		}

		// Token: 0x06000330 RID: 816 RVA: 0x00302004 File Offset: 0x00182404
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			return new 097E1C51.14C26E2D(this.B9179A8D());
		}

		// Token: 0x06000331 RID: 817 RVA: 0x00302774 File Offset: 0x00182B74
		public override sbyte 52B3F2EC()
		{
			uint num = 634992830U;
			sbyte b = (sbyte)this.41072A98;
			num -= 555881008U;
			return b;
		}

		// Token: 0x06000332 RID: 818 RVA: 0x00302798 File Offset: 0x00182B98
		public override byte 19201189()
		{
			uint num = 1808942311U;
			byte b = (byte)this.41072A98;
			num = (177086777U & num);
			return b;
		}

		// Token: 0x06000333 RID: 819 RVA: 0x003027BC File Offset: 0x00182BBC
		public override short BDAC8EA4()
		{
			uint num = 812650463U;
			short num2 = (short)this.41072A98;
			num = 1154838693U >> (int)num;
			return num2;
		}

		// Token: 0x06000334 RID: 820 RVA: 0x003027E4 File Offset: 0x00182BE4
		public override ushort 86B35C1E()
		{
			return (ushort)this.41072A98;
		}

		// Token: 0x06000335 RID: 821 RVA: 0x003027F8 File Offset: 0x00182BF8
		public override int B9179A8D()
		{
			uint num = 623458052U;
			num |= 1116950784U;
			return (int)this.41072A98;
		}

		// Token: 0x06000336 RID: 822 RVA: 0x003027F8 File Offset: 0x00182BF8
		public override uint 2CD32589()
		{
			uint num = 623458052U;
			num |= 1116950784U;
			return this.41072A98;
		}

		// Token: 0x040001A3 RID: 419
		private uint 41072A98;
	}

	// Token: 0x02000059 RID: 89
	private sealed class 378A58B6 : 097E1C51.0A706168
	{
		// Token: 0x06000337 RID: 823 RVA: 0x0030281C File Offset: 0x00182C1C
		public 378A58B6(ulong 6E5A0E65)
		{
			uint num;
			do
			{
				base..ctor();
				num = 246035622U;
			}
			while (num / 890573746U != 0U);
			num += 497552323U;
			this.797D2FBC = 6E5A0E65;
		}

		// Token: 0x06000338 RID: 824 RVA: 0x00302850 File Offset: 0x00182C50
		public override Type 5F93D4ED()
		{
			return typeof(ulong);
		}

		// Token: 0x06000339 RID: 825 RVA: 0x00302870 File Offset: 0x00182C70
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.378A58B6(this.797D2FBC);
		}

		// Token: 0x0600033A RID: 826 RVA: 0x00302890 File Offset: 0x00182C90
		public override object 04F0F15D()
		{
			uint num = 60057765U;
			num = 1808929887U >> (int)num;
			return this.797D2FBC;
		}

		// Token: 0x0600033B RID: 827 RVA: 0x003028BC File Offset: 0x00182CBC
		public override void 360F4E2C(object 6610679C)
		{
			this.797D2FBC = Convert.ToUInt64(6610679C);
		}

		// Token: 0x0600033C RID: 828 RVA: 0x003028DC File Offset: 0x00182CDC
		public override 097E1C51.4A781DBA 3D28B13E()
		{
			uint num = 1496581035U;
			num = 2045648050U % num;
			return new 097E1C51.31EF1BAE(this.8EC1A2BC());
		}

		// Token: 0x0600033D RID: 829 RVA: 0x00302904 File Offset: 0x00182D04
		public override sbyte 52B3F2EC()
		{
			return (sbyte)this.797D2FBC;
		}

		// Token: 0x0600033E RID: 830 RVA: 0x00302920 File Offset: 0x00182D20
		public override byte 19201189()
		{
			uint num = 1118647655U;
			byte b = (byte)this.797D2FBC;
			num &= 1750210020U;
			return b;
		}

		// Token: 0x0600033F RID: 831 RVA: 0x00302944 File Offset: 0x00182D44
		public override short BDAC8EA4()
		{
			return (short)this.797D2FBC;
		}

		// Token: 0x06000340 RID: 832 RVA: 0x00302958 File Offset: 0x00182D58
		public override ushort 86B35C1E()
		{
			uint num = 309222836U;
			num <<= 25;
			ushort num2 = (ushort)this.797D2FBC;
			num = 760689043U >> (int)num;
			return num2;
		}

		// Token: 0x06000341 RID: 833 RVA: 0x00302988 File Offset: 0x00182D88
		public override int B9179A8D()
		{
			uint num = 102772940U;
			int num2 = (int)this.797D2FBC;
			num |= 582820354U;
			return num2;
		}

		// Token: 0x06000342 RID: 834 RVA: 0x003029AC File Offset: 0x00182DAC
		public override uint 2CD32589()
		{
			uint num = 744047512U;
			uint num2 = (uint)this.797D2FBC;
			num %= 1086419594U;
			return num2;
		}

		// Token: 0x06000343 RID: 835 RVA: 0x003029D0 File Offset: 0x00182DD0
		public override long 8EC1A2BC()
		{
			return (long)this.797D2FBC;
		}

		// Token: 0x06000344 RID: 836 RVA: 0x003029D0 File Offset: 0x00182DD0
		public override ulong 083A097E()
		{
			return this.797D2FBC;
		}

		// Token: 0x040001A4 RID: 420
		private ulong 797D2FBC;
	}

	// Token: 0x0200005A RID: 90
	private sealed class 7B0B79E5 : 097E1C51.4A781DBA
	{
		// Token: 0x06000345 RID: 837 RVA: 0x003029E4 File Offset: 0x00182DE4
		public 7B0B79E5(object 1A34651F)
		{
			uint num = 1147744420U;
			if ((209585197U & num) != 0U)
			{
				do
				{
					num |= 1012102567U;
					this.6225486D = 1A34651F;
				}
				while (1563386103U == num);
			}
		}

		// Token: 0x06000346 RID: 838 RVA: 0x00302A24 File Offset: 0x00182E24
		public override Type 5F93D4ED()
		{
			return typeof(object);
		}

		// Token: 0x06000347 RID: 839 RVA: 0x002F0E0C File Offset: 0x0017120C
		public override TypeCode 6DFD59A4()
		{
			uint num = 2080718108U;
			return (TypeCode)(num ^ 2080718109U);
		}

		// Token: 0x06000348 RID: 840 RVA: 0x00302A44 File Offset: 0x00182E44
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 618216650U;
			object 1A34651F = this.6225486D;
			num &= 2037806996U;
			return new 097E1C51.7B0B79E5(1A34651F);
		}

		// Token: 0x06000349 RID: 841 RVA: 0x00302A6C File Offset: 0x00182E6C
		public override object 04F0F15D()
		{
			uint num = 1970306790U;
			num -= 699074915U;
			return this.6225486D;
		}

		// Token: 0x0600034A RID: 842 RVA: 0x00302A90 File Offset: 0x00182E90
		public override void 360F4E2C(object 398932B5)
		{
			this.6225486D = 398932B5;
		}

		// Token: 0x0600034B RID: 843 RVA: 0x00302AAC File Offset: 0x00182EAC
		public override bool C77753F7()
		{
			return this.6225486D != null;
		}

		// Token: 0x040001A5 RID: 421
		private object 6225486D;
	}

	// Token: 0x0200005B RID: 91
	private sealed class 277D4587 : 097E1C51.4A781DBA
	{
		// Token: 0x0600034C RID: 844 RVA: 0x00302AC8 File Offset: 0x00182EC8
		public 277D4587(object 526508FB, Type 30684F7F)
		{
			uint num = 286067680U;
			if (num == 1791700966U)
			{
				goto IL_41;
			}
			do
			{
				IL_11:
				base..ctor();
				num = (691895553U | num);
			}
			while (410266468U * num == 0U);
			this.660B46CA = 526508FB;
			this.587D4F80 = 30684F7F;
			num = 727809329U - num;
			IL_41:
			num += 1062888516U;
			num >>= 12;
			097E1C51.0A706168 0A = 097E1C51.277D4587.26EA48A8(526508FB);
			num /= 934177410U;
			this.4E512DBF = 0A;
			if (1356951816 << (int)num != 0)
			{
				return;
			}
			goto IL_11;
		}

		// Token: 0x0600034D RID: 845 RVA: 0x00302B4C File Offset: 0x00182F4C
		private static 097E1C51.0A706168 26EA48A8(object 0D5330FC)
		{
			uint num = 1954622869U;
			if (num <= 50093355U)
			{
				goto IL_43;
			}
			IL_11:
			num *= 1693779527U;
			if (0D5330FC == null)
			{
				goto IL_43;
			}
			num = (2075550959U | num);
			if (1739985168U - num == 0U)
			{
				goto IL_43;
			}
			IL_33:
			IntPtr intPtr = new IntPtr(Pointer.Unbox(0D5330FC));
			goto IL_64;
			IL_43:
			num = (609948792U | num);
			if (1205474278U / num == 0U)
			{
				goto IL_87;
			}
			intPtr = IntPtr.Zero;
			num += 1300181124U;
			IL_64:
			num = 1524370647U + num;
			IntPtr intPtr2 = intPtr;
			num = 1698633525U * num;
			if (2108310012 << (int)num == 0)
			{
				goto IL_11;
			}
			IL_87:
			if (IntPtr.Size == (int)(num - 189543754U))
			{
				int 339906C = intPtr2.ToInt32();
				num = (1782844036U | num);
				return new 097E1C51.14C26E2D(339906C);
			}
			if (num >> 10 != 0U)
			{
				num *= 2000904435U;
				long 6DB81B = intPtr2.ToInt64();
				num *= 290275674U;
				return new 097E1C51.31EF1BAE(6DB81B);
			}
			goto IL_33;
		}

		// Token: 0x0600034E RID: 846 RVA: 0x00302A24 File Offset: 0x00182E24
		public override Type 5F93D4ED()
		{
			return typeof(object);
		}

		// Token: 0x0600034F RID: 847 RVA: 0x00302C30 File Offset: 0x00183030
		public Type 686C3617()
		{
			return this.587D4F80;
		}

		// Token: 0x06000350 RID: 848 RVA: 0x00302C4C File Offset: 0x0018304C
		public override TypeCode 6DFD59A4()
		{
			if (IntPtr.Size != 4)
			{
				return TypeCode.UInt64;
			}
			return TypeCode.UInt32;
		}

		// Token: 0x06000351 RID: 849 RVA: 0x00302C6C File Offset: 0x0018306C
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 686782945U;
			object 526508FB = this.660B46CA;
			num *= 1584405654U;
			return new 097E1C51.277D4587(526508FB, this.587D4F80);
		}

		// Token: 0x06000352 RID: 850 RVA: 0x00302C98 File Offset: 0x00183098
		public override object 04F0F15D()
		{
			return this.660B46CA;
		}

		// Token: 0x06000353 RID: 851 RVA: 0x00302CB4 File Offset: 0x001830B4
		public override void 360F4E2C(object 634216A0)
		{
			uint num = 895246261U;
			if (1710832147U >> (int)num != 0U)
			{
				num &= 734148871U;
				this.660B46CA = 634216A0;
				if ((124204951U & num) == 0U)
				{
					return;
				}
			}
			num |= 298713757U;
			num += 743788271U;
			097E1C51.0A706168 0A = 097E1C51.277D4587.26EA48A8(634216A0);
			num = 2000424306U % num;
			this.4E512DBF = 0A;
		}

		// Token: 0x06000354 RID: 852 RVA: 0x00302D18 File Offset: 0x00183118
		public override bool C77753F7()
		{
			uint num = 865155130U;
			num ^= 2143366737U;
			return this.660B46CA != null;
		}

		// Token: 0x06000355 RID: 853 RVA: 0x00302D3C File Offset: 0x0018313C
		public override sbyte 52B3F2EC()
		{
			uint num = 614880800U;
			097E1C51.0A706168 0A = this.4E512DBF;
			num = 641627796U << (int)num;
			return 0A.52B3F2EC();
		}

		// Token: 0x06000356 RID: 854 RVA: 0x00302D68 File Offset: 0x00183168
		public override short BDAC8EA4()
		{
			return this.4E512DBF.BDAC8EA4();
		}

		// Token: 0x06000357 RID: 855 RVA: 0x00302D88 File Offset: 0x00183188
		public override int B9179A8D()
		{
			uint num = 1714884833U;
			097E1C51.0A706168 0A = this.4E512DBF;
			num |= 658599472U;
			return 0A.B9179A8D();
		}

		// Token: 0x06000358 RID: 856 RVA: 0x00302DB0 File Offset: 0x001831B0
		public override long 8EC1A2BC()
		{
			uint num = 1652823382U;
			097E1C51.0A706168 0A = this.4E512DBF;
			num >>= 8;
			return 0A.8EC1A2BC();
		}

		// Token: 0x06000359 RID: 857 RVA: 0x00302DD8 File Offset: 0x001831D8
		public override byte 19201189()
		{
			return this.4E512DBF.19201189();
		}

		// Token: 0x0600035A RID: 858 RVA: 0x00302DF0 File Offset: 0x001831F0
		public override ushort 86B35C1E()
		{
			uint num = 1949596747U;
			num = (977630362U ^ num);
			return this.4E512DBF.86B35C1E();
		}

		// Token: 0x0600035B RID: 859 RVA: 0x00302E18 File Offset: 0x00183218
		public override uint 2CD32589()
		{
			uint num = 1767582040U;
			097E1C51.0A706168 0A = this.4E512DBF;
			num = (2031579821U | num);
			return 0A.2CD32589();
		}

		// Token: 0x0600035C RID: 860 RVA: 0x00302E40 File Offset: 0x00183240
		public override ulong 083A097E()
		{
			return this.4E512DBF.083A097E();
		}

		// Token: 0x0600035D RID: 861 RVA: 0x00302E58 File Offset: 0x00183258
		public override float E1E85554()
		{
			return this.4E512DBF.E1E85554();
		}

		// Token: 0x0600035E RID: 862 RVA: 0x00302E70 File Offset: 0x00183270
		public override double D87DFA30()
		{
			return this.4E512DBF.D87DFA30();
		}

		// Token: 0x0600035F RID: 863 RVA: 0x00302E88 File Offset: 0x00183288
		public override IntPtr E3F543C7()
		{
			return this.4E512DBF.E3F543C7();
		}

		// Token: 0x06000360 RID: 864 RVA: 0x00302EA8 File Offset: 0x001832A8
		public override UIntPtr 7F934522()
		{
			uint num = 1297962732U;
			num = 1166371820U << (int)num;
			097E1C51.0A706168 0A = this.4E512DBF;
			num -= 2141005900U;
			return 0A.7F934522();
		}

		// Token: 0x06000361 RID: 865 RVA: 0x00302EDC File Offset: 0x001832DC
		public override object 9E2F1085(Type 7824122E, bool 33FC3BC3)
		{
			uint num = 1444620795U;
			097E1C51.0A706168 0A = this.4E512DBF;
			num -= 1092580212U;
			num += 1370831013U;
			num ^= 2029994960U;
			return 0A.9E2F1085(7824122E, 33FC3BC3);
		}

		// Token: 0x040001A6 RID: 422
		private object 660B46CA;

		// Token: 0x040001A7 RID: 423
		private Type 587D4F80;

		// Token: 0x040001A8 RID: 424
		private 097E1C51.0A706168 4E512DBF;
	}

	// Token: 0x0200005C RID: 92
	private sealed class 25883EE0 : 097E1C51.4A781DBA
	{
		// Token: 0x06000362 RID: 866 RVA: 0x00302F14 File Offset: 0x00183314
		public 25883EE0(object 67EE67F7)
		{
			uint num = 991564531U;
			if ((num & 1677289278U) == 0U)
			{
				goto IL_5B;
			}
			num *= 17238309U;
			base..ctor();
			num = 631846074U / num;
			if (67EE67F7 == null)
			{
				goto IL_5B;
			}
			num = 1455706502U * num;
			if (num * 1086205887U != 0U)
			{
				goto IL_55;
			}
			IL_42:
			bool flag = 67EE67F7 is ValueType;
			num += 0U;
			if (flag)
			{
				goto IL_5B;
			}
			IL_55:
			throw new ArgumentException();
			IL_5B:
			if ((num ^ 2144758013U) != 0U)
			{
				num <<= 11;
				this.235A3ADC = 67EE67F7;
				return;
			}
			goto IL_42;
		}

		// Token: 0x06000363 RID: 867 RVA: 0x00302F98 File Offset: 0x00183398
		public override Type 5F93D4ED()
		{
			return typeof(ValueType);
		}

		// Token: 0x06000364 RID: 868 RVA: 0x00302FB0 File Offset: 0x001833B0
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.25883EE0(this.235A3ADC);
		}

		// Token: 0x06000365 RID: 869 RVA: 0x00302FD0 File Offset: 0x001833D0
		public override object 04F0F15D()
		{
			return this.235A3ADC;
		}

		// Token: 0x06000366 RID: 870 RVA: 0x00302FEC File Offset: 0x001833EC
		public override void 360F4E2C(object 26EF5ABE)
		{
			uint num = 279466143U;
			if (886386289U != num)
			{
				do
				{
					if (26EF5ABE != null)
					{
						num <<= 13;
						if (num >= 1150895344U)
						{
							continue;
						}
						num = (1773890408U | num);
						bool flag = 26EF5ABE is ValueType;
						num <<= 22;
						num ^= 3400028319U;
						if (!flag)
						{
							goto IL_4D;
						}
					}
					if (498734356U * num == 0U)
					{
						break;
					}
					num = 1805652930U * num;
					this.235A3ADC = 26EF5ABE;
				}
				while (num % 1762213519U == 0U);
				return;
			}
			IL_4D:
			throw new ArgumentException();
		}

		// Token: 0x040001A9 RID: 425
		private object 235A3ADC;
	}

	// Token: 0x0200005D RID: 93
	private sealed class 08046E73 : 097E1C51.4A781DBA
	{
		// Token: 0x06000367 RID: 871 RVA: 0x00303074 File Offset: 0x00183474
		public 08046E73(Array 76A17C1B)
		{
			uint num = 1782401767U;
			num &= 1706428461U;
			this.2FB66AA7 = 76A17C1B;
		}

		// Token: 0x06000368 RID: 872 RVA: 0x0030309C File Offset: 0x0018349C
		public override Type 5F93D4ED()
		{
			return typeof(Array);
		}

		// Token: 0x06000369 RID: 873 RVA: 0x003030B4 File Offset: 0x001834B4
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 1561747363U;
			num ^= 1918640557U;
			Array 76A17C1B = this.2FB66AA7;
			num += 270432922U;
			return new 097E1C51.08046E73(76A17C1B);
		}

		// Token: 0x0600036A RID: 874 RVA: 0x003030E4 File Offset: 0x001834E4
		public override object 04F0F15D()
		{
			uint num = 1265785270U;
			num = 1122437015U * num;
			return this.2FB66AA7;
		}

		// Token: 0x0600036B RID: 875 RVA: 0x00303108 File Offset: 0x00183508
		public override void 360F4E2C(object 605129B2)
		{
			this.2FB66AA7 = (Array)605129B2;
		}

		// Token: 0x0600036C RID: 876 RVA: 0x00303128 File Offset: 0x00183528
		public override bool C77753F7()
		{
			uint num = 495584357U;
			num %= 1207922060U;
			Array array = this.2FB66AA7;
			num >>= 12;
			object obj = null;
			num -= 616260012U;
			return array > obj;
		}

		// Token: 0x040001AA RID: 426
		private Array 2FB66AA7;
	}

	// Token: 0x0200005E RID: 94
	private abstract class 388961B0 : 097E1C51.4A781DBA
	{
		// Token: 0x0600036D RID: 877 RVA: 0x002F0E0C File Offset: 0x0017120C
		public override bool 3CF78257()
		{
			uint num = 2080718108U;
			return (num ^ 2080718109U) != 0U;
		}

		// Token: 0x0600036E RID: 878 RVA: 0x0030315C File Offset: 0x0018355C
		protected 388961B0()
		{
			uint num = 1474503748U;
			do
			{
				base..ctor();
			}
			while (num == 1764765626U);
		}
	}

	// Token: 0x0200005F RID: 95
	private sealed class 41091902 : 097E1C51.388961B0
	{
		// Token: 0x0600036F RID: 879 RVA: 0x00303180 File Offset: 0x00183580
		public 41091902(097E1C51.0A706168 5BBE6D88)
		{
			uint num = 186321571U;
			do
			{
				num |= 88746019U;
				base..ctor();
				num = 314919516U % num;
				if (1589514302U >> (int)num == 0U)
				{
					break;
				}
				num ^= 104866090U;
				num = 1717056071U * num;
				this.48D321DF = 5BBE6D88;
			}
			while (58349337U * num == 0U);
		}

		// Token: 0x06000370 RID: 880 RVA: 0x003031E0 File Offset: 0x001835E0
		public override Type 5F93D4ED()
		{
			return this.48D321DF.5F93D4ED();
		}

		// Token: 0x06000371 RID: 881 RVA: 0x00303200 File Offset: 0x00183600
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 2137204439U;
			num = 1674525085U + num;
			097E1C51.0A706168 5BBE6D = this.48D321DF;
			num -= 1170683615U;
			return new 097E1C51.41091902(5BBE6D);
		}

		// Token: 0x06000372 RID: 882 RVA: 0x00303230 File Offset: 0x00183630
		public override object 04F0F15D()
		{
			uint num = 866200306U;
			num *= 862003807U;
			097E1C51.0A706168 0A = this.48D321DF;
			num /= 246424215U;
			return 0A.04F0F15D();
		}

		// Token: 0x06000373 RID: 883 RVA: 0x00303260 File Offset: 0x00183660
		public override void 360F4E2C(object 3C5B4CB8)
		{
			uint num = 425746208U;
			if (num + 365329434U != 0U)
			{
				num /= 1911954722U;
				097E1C51.0A706168 0A = this.48D321DF;
				num <<= 5;
				num = 1534004755U - num;
				0A.360F4E2C(3C5B4CB8);
			}
		}

		// Token: 0x06000374 RID: 884 RVA: 0x003032A4 File Offset: 0x001836A4
		public override bool C77753F7()
		{
			uint num = 2125145544U;
			097E1C51.0A706168 0A = this.48D321DF;
			num = (499014534U | num);
			object obj = null;
			num += 453780014U;
			return 0A > obj;
		}

		// Token: 0x040001AB RID: 427
		private 097E1C51.0A706168 48D321DF;
	}

	// Token: 0x02000060 RID: 96
	private sealed class 6FD1772C : 097E1C51.388961B0
	{
		// Token: 0x06000375 RID: 885 RVA: 0x003032D0 File Offset: 0x001836D0
		public 6FD1772C(097E1C51.0A706168 58352FC3, 097E1C51.0A706168 59482B96)
		{
			uint num = 1887464284U;
			if (num >= 686037224U)
			{
				num = 2090226852U * num;
				base..ctor();
				num >>= 28;
				if ((num & 2036931371U) == 0U)
				{
					goto IL_52;
				}
				IL_33:
				num <<= 3;
				num ^= 2083474131U;
				this.63E9364D = 58352FC3;
				num = 704263606U % num;
				IL_52:
				num = 2106731099U >> (int)num;
				this.2F1A3AB8 = 59482B96;
				if (1773890795U + num == 0U)
				{
					goto IL_33;
				}
			}
		}

		// Token: 0x06000376 RID: 886 RVA: 0x00303350 File Offset: 0x00183750
		public override Type 5F93D4ED()
		{
			return this.63E9364D.5F93D4ED();
		}

		// Token: 0x06000377 RID: 887 RVA: 0x00303368 File Offset: 0x00183768
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 1307992924U;
			num = (604797507U | num);
			097E1C51.0A706168 58352FC = this.63E9364D;
			num -= 1577408991U;
			return new 097E1C51.6FD1772C(58352FC, this.2F1A3AB8);
		}

		// Token: 0x06000378 RID: 888 RVA: 0x0030339C File Offset: 0x0018379C
		public override object 04F0F15D()
		{
			return this.63E9364D.04F0F15D();
		}

		// Token: 0x06000379 RID: 889 RVA: 0x003033B4 File Offset: 0x001837B4
		public override void 360F4E2C(object 69C66AD9)
		{
			uint num;
			do
			{
				num = 1446643455U;
				097E1C51.0A706168 0A = this.63E9364D;
				num = 328273721U / num;
				0A.360F4E2C(69C66AD9);
			}
			while (320962521U < num);
			do
			{
				097E1C51.0A706168 0A2 = this.2F1A3AB8;
				num = (1150428462U & num);
				object 5076725B = this.63E9364D.04F0F15D();
				num = 1751859251U - num;
				0A2.360F4E2C(5076725B);
			}
			while (num <= 1578517092U);
		}

		// Token: 0x0600037A RID: 890 RVA: 0x00303418 File Offset: 0x00183818
		public override bool C77753F7()
		{
			uint num = 1886197699U;
			num /= 639067041U;
			return this.63E9364D != null;
		}

		// Token: 0x040001AC RID: 428
		private 097E1C51.0A706168 63E9364D;

		// Token: 0x040001AD RID: 429
		private 097E1C51.0A706168 2F1A3AB8;
	}

	// Token: 0x02000061 RID: 97
	private sealed class 23FD3942 : 097E1C51.388961B0
	{
		// Token: 0x0600037B RID: 891 RVA: 0x0030343C File Offset: 0x0018383C
		public 23FD3942(FieldInfo 65E63B90, object 050271CD)
		{
			uint num = 229534345U;
			if (1230975855U != num)
			{
				do
				{
					num = (1378451405U ^ num);
					base..ctor();
					num = 2106412916U >> (int)num;
				}
				while ((num ^ 277823878U) == 0U);
			}
			num ^= 127743807U;
			num <<= 14;
			this.1DFE0BAD = 65E63B90;
			do
			{
				num -= 1346588420U;
				num %= 1818892698U;
				this.2BDD3685 = 050271CD;
			}
			while (1681339169U == num);
		}

		// Token: 0x0600037C RID: 892 RVA: 0x003034BC File Offset: 0x001838BC
		public override Type 5F93D4ED()
		{
			return this.1DFE0BAD.FieldType;
		}

		// Token: 0x0600037D RID: 893 RVA: 0x003034DC File Offset: 0x001838DC
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 2098285535U;
			num = 45180425U + num;
			FieldInfo 65E63B = this.1DFE0BAD;
			num = 1130770007U % num;
			num &= 384445256U;
			return new 097E1C51.23FD3942(65E63B, this.2BDD3685);
		}

		// Token: 0x0600037E RID: 894 RVA: 0x00303518 File Offset: 0x00183918
		public override object 04F0F15D()
		{
			return this.1DFE0BAD.GetValue(this.2BDD3685);
		}

		// Token: 0x0600037F RID: 895 RVA: 0x00303538 File Offset: 0x00183938
		public override void 360F4E2C(object 16B5520F)
		{
			uint num;
			do
			{
				num = 711670629U;
				FieldInfo fieldInfo = this.1DFE0BAD;
				num = (1785338395U ^ num);
				object obj = this.2BDD3685;
				num = 595090042U << (int)num;
				num = 792227993U - num;
				fieldInfo.SetValue(obj, 16B5520F);
			}
			while (num <= 1309437811U);
		}

		// Token: 0x040001AE RID: 430
		private FieldInfo 1DFE0BAD;

		// Token: 0x040001AF RID: 431
		private object 2BDD3685;
	}

	// Token: 0x02000062 RID: 98
	private sealed class 2013254A : 097E1C51.388961B0
	{
		// Token: 0x06000380 RID: 896 RVA: 0x00303588 File Offset: 0x00183988
		public 2013254A(Array 74B23B46, int 1354693C)
		{
			uint num = 799561517U;
			base..ctor();
			do
			{
				num -= 1802842720U;
				this.55D76111 = 74B23B46;
				num <<= 5;
			}
			while (num <= 1244020221U);
			num = (624647268U ^ num);
			this.40A0162D = 1354693C;
		}

		// Token: 0x06000381 RID: 897 RVA: 0x003035D4 File Offset: 0x001839D4
		public override Type 5F93D4ED()
		{
			return this.55D76111.GetType().GetElementType();
		}

		// Token: 0x06000382 RID: 898 RVA: 0x003035F8 File Offset: 0x001839F8
		public override 097E1C51.0A706168 7A52F208()
		{
			Array 74B23B = this.55D76111;
			uint num = 630194534U;
			int 1354693C = this.40A0162D;
			num = 1843950488U << (int)num;
			return new 097E1C51.2013254A(74B23B, 1354693C);
		}

		// Token: 0x06000383 RID: 899 RVA: 0x0030362C File Offset: 0x00183A2C
		public override object 04F0F15D()
		{
			return this.55D76111.GetValue(this.40A0162D);
		}

		// Token: 0x06000384 RID: 900 RVA: 0x00303650 File Offset: 0x00183A50
		public override void 360F4E2C(object 68F95250)
		{
			uint num;
			do
			{
				num = 180095457U;
				Array array = this.55D76111;
				num = 1755863998U - num;
				int index = this.40A0162D;
				num = (230439630U ^ num);
				array.SetValue(68F95250, index);
			}
			while (1654285891U * num == 0U);
		}

		// Token: 0x040001B0 RID: 432
		private Array 55D76111;

		// Token: 0x040001B1 RID: 433
		private int 40A0162D;
	}

	// Token: 0x02000063 RID: 99
	private sealed class 110B654B : 097E1C51.4A781DBA
	{
		// Token: 0x06000385 RID: 901 RVA: 0x00303694 File Offset: 0x00183A94
		public 110B654B(MethodBase 33AC5A1B)
		{
			this.7672056B = 33AC5A1B;
		}

		// Token: 0x06000386 RID: 902 RVA: 0x003036B4 File Offset: 0x00183AB4
		public override Type 5F93D4ED()
		{
			return typeof(MethodBase);
		}

		// Token: 0x06000387 RID: 903 RVA: 0x003036D4 File Offset: 0x00183AD4
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 1283856404U;
			MethodBase 33AC5A1B = this.7672056B;
			num *= 1509317080U;
			return new 097E1C51.110B654B(33AC5A1B);
		}

		// Token: 0x06000388 RID: 904 RVA: 0x003036FC File Offset: 0x00183AFC
		public override object 04F0F15D()
		{
			uint num = 1286626353U;
			num = (565787600U | num);
			return this.7672056B;
		}

		// Token: 0x06000389 RID: 905 RVA: 0x00303720 File Offset: 0x00183B20
		public override void 360F4E2C(object 194C4EA9)
		{
			this.7672056B = (MethodBase)194C4EA9;
		}

		// Token: 0x0600038A RID: 906 RVA: 0x0030373C File Offset: 0x00183B3C
		public override bool C77753F7()
		{
			uint num = 699751420U;
			num = (694634266U ^ num);
			return this.7672056B != null;
		}

		// Token: 0x0600038B RID: 907 RVA: 0x00303760 File Offset: 0x00183B60
		public override IntPtr E3F543C7()
		{
			uint num = 2102488657U;
			RuntimeMethodHandle runtimeMethodHandle;
			if (1495016093U >> (int)num != 0U)
			{
				num = 1273527266U * num;
				RuntimeMethodHandle methodHandle = this.7672056B.MethodHandle;
				num *= 1565748081U;
				runtimeMethodHandle = methodHandle;
				num = 1055672454U * num;
			}
			return runtimeMethodHandle.GetFunctionPointer();
		}

		// Token: 0x040001B2 RID: 434
		private MethodBase 7672056B;
	}

	// Token: 0x02000064 RID: 100
	private sealed class 6BB54CA8 : 097E1C51.4A781DBA
	{
		// Token: 0x0600038C RID: 908 RVA: 0x003037B0 File Offset: 0x00183BB0
		public 6BB54CA8(IntPtr 3E360548)
		{
			uint num;
			do
			{
				num = 1073944669U;
				base..ctor();
				num = 503476704U << (int)num;
				this.44875CA0 = 3E360548;
			}
			while (2040742561U < num);
			num = 1283394020U + num;
			num = 2136739272U % num;
			097E1C51.0A706168 0A = 097E1C51.6BB54CA8.460A3E27(this.44875CA0);
			num -= 293107082U;
			this.06B66AC5 = 0A;
		}

		// Token: 0x0600038D RID: 909 RVA: 0x00303814 File Offset: 0x00183C14
		private static 097E1C51.0A706168 460A3E27(IntPtr 190126F7)
		{
			uint num2;
			do
			{
				int size = IntPtr.Size;
				int num = 4;
				num2 = 596315897U;
				if (size != num)
				{
					goto IL_31;
				}
			}
			while (num2 < 55730212U);
			int 339906C = 190126F7.ToInt32();
			num2 = 516578042U + num2;
			return new 097E1C51.14C26E2D(339906C);
			IL_31:
			return new 097E1C51.31EF1BAE(190126F7.ToInt64());
		}

		// Token: 0x0600038E RID: 910 RVA: 0x00303860 File Offset: 0x00183C60
		public override Type 5F93D4ED()
		{
			return typeof(IntPtr);
		}

		// Token: 0x0600038F RID: 911 RVA: 0x00303880 File Offset: 0x00183C80
		public override TypeCode 6DFD59A4()
		{
			uint num = 1722697040U;
			num = 1416311315U - num;
			return this.06B66AC5.6DFD59A4();
		}

		// Token: 0x06000390 RID: 912 RVA: 0x003038A8 File Offset: 0x00183CA8
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.6BB54CA8(this.44875CA0);
		}

		// Token: 0x06000391 RID: 913 RVA: 0x003038C8 File Offset: 0x00183CC8
		public override object 04F0F15D()
		{
			return this.44875CA0;
		}

		// Token: 0x06000392 RID: 914 RVA: 0x003038E0 File Offset: 0x00183CE0
		public override void 360F4E2C(object 55E66213)
		{
			uint num;
			do
			{
				num = 496634062U;
				this.44875CA0 = (IntPtr)55E66213;
			}
			while (1577339875U < num);
			do
			{
				this.06B66AC5 = 097E1C51.6BB54CA8.460A3E27(this.44875CA0);
			}
			while ((num ^ 85753842U) == 0U);
		}

		// Token: 0x06000393 RID: 915 RVA: 0x00303928 File Offset: 0x00183D28
		public override bool C77753F7()
		{
			return this.44875CA0 != IntPtr.Zero;
		}

		// Token: 0x06000394 RID: 916 RVA: 0x0030394C File Offset: 0x00183D4C
		public override sbyte 52B3F2EC()
		{
			return this.06B66AC5.52B3F2EC();
		}

		// Token: 0x06000395 RID: 917 RVA: 0x0030396C File Offset: 0x00183D6C
		public override short BDAC8EA4()
		{
			return this.06B66AC5.BDAC8EA4();
		}

		// Token: 0x06000396 RID: 918 RVA: 0x00303984 File Offset: 0x00183D84
		public override int B9179A8D()
		{
			return this.06B66AC5.B9179A8D();
		}

		// Token: 0x06000397 RID: 919 RVA: 0x003039A4 File Offset: 0x00183DA4
		public override long 8EC1A2BC()
		{
			uint num = 1055271321U;
			097E1C51.0A706168 0A = this.06B66AC5;
			num >>= 7;
			return 0A.8EC1A2BC();
		}

		// Token: 0x06000398 RID: 920 RVA: 0x003039CC File Offset: 0x00183DCC
		public override byte 19201189()
		{
			return this.06B66AC5.19201189();
		}

		// Token: 0x06000399 RID: 921 RVA: 0x003039EC File Offset: 0x00183DEC
		public override ushort 86B35C1E()
		{
			uint num = 2013152390U;
			097E1C51.0A706168 0A = this.06B66AC5;
			num <<= 25;
			return 0A.86B35C1E();
		}

		// Token: 0x0600039A RID: 922 RVA: 0x00303A14 File Offset: 0x00183E14
		public override uint 2CD32589()
		{
			return this.06B66AC5.2CD32589();
		}

		// Token: 0x0600039B RID: 923 RVA: 0x00303A34 File Offset: 0x00183E34
		public override ulong 083A097E()
		{
			return this.06B66AC5.083A097E();
		}

		// Token: 0x0600039C RID: 924 RVA: 0x00303A54 File Offset: 0x00183E54
		public override float E1E85554()
		{
			uint num = 500184912U;
			097E1C51.0A706168 0A = this.06B66AC5;
			num = (721970105U & num);
			return 0A.E1E85554();
		}

		// Token: 0x0600039D RID: 925 RVA: 0x00303A7C File Offset: 0x00183E7C
		public override double D87DFA30()
		{
			uint num = 1285955312U;
			097E1C51.0A706168 0A = this.06B66AC5;
			num -= 2124418415U;
			return 0A.D87DFA30();
		}

		// Token: 0x0600039E RID: 926 RVA: 0x00303AA4 File Offset: 0x00183EA4
		public override IntPtr E3F543C7()
		{
			return this.44875CA0;
		}

		// Token: 0x0600039F RID: 927 RVA: 0x00303AC0 File Offset: 0x00183EC0
		public override UIntPtr 7F934522()
		{
			uint num = 1875849497U;
			num *= 1740636539U;
			return this.06B66AC5.7F934522();
		}

		// Token: 0x060003A0 RID: 928 RVA: 0x00303AE8 File Offset: 0x00183EE8
		public override object 9E2F1085(Type 484E3445, bool 179A2E5C)
		{
			return this.06B66AC5.9E2F1085(484E3445, 179A2E5C);
		}

		// Token: 0x040001B3 RID: 435
		private IntPtr 44875CA0;

		// Token: 0x040001B4 RID: 436
		private 097E1C51.0A706168 06B66AC5;
	}

	// Token: 0x02000065 RID: 101
	private sealed class 2B55285E : 097E1C51.4A781DBA
	{
		// Token: 0x060003A1 RID: 929 RVA: 0x00303B08 File Offset: 0x00183F08
		public 2B55285E(UIntPtr 51266486)
		{
			uint num = 1987257504U;
			base..ctor();
			num *= 248541506U;
			this.062B7215 = 51266486;
			if (num > 503199576U)
			{
				UIntPtr 70420AE = this.062B7215;
				num = 1683954150U % num;
				this.51581B7F = 097E1C51.2B55285E.26437A04(70420AE);
			}
		}

		// Token: 0x060003A2 RID: 930 RVA: 0x00303B54 File Offset: 0x00183F54
		private static 097E1C51.0A706168 26437A04(UIntPtr 70420AE6)
		{
			uint num = 910308777U;
			if (637408263U >= num)
			{
				goto IL_4B;
			}
			do
			{
				IL_11:
				int size = IntPtr.Size;
				uint num2 = num - 910308773U;
				num = 328143306U * num;
				if (size != num2)
				{
					goto IL_4B;
				}
				num &= 2131381587U;
			}
			while ((num ^ 581763336U) == 0U);
			return new 097E1C51.14C26E2D((int)70420AE6.ToUInt32());
			IL_4B:
			num = 1866740907U + num;
			if (86259313 << (int)num != 0)
			{
				long 6DB81B = (long)70420AE6.ToUInt64();
				num = 1823033711U + num;
				return new 097E1C51.31EF1BAE(6DB81B);
			}
			goto IL_11;
		}

		// Token: 0x060003A3 RID: 931 RVA: 0x00303BDC File Offset: 0x00183FDC
		public override Type 5F93D4ED()
		{
			return typeof(UIntPtr);
		}

		// Token: 0x060003A4 RID: 932 RVA: 0x00303BF4 File Offset: 0x00183FF4
		public override TypeCode 6DFD59A4()
		{
			return this.51581B7F.6DFD59A4();
		}

		// Token: 0x060003A5 RID: 933 RVA: 0x00303C0C File Offset: 0x0018400C
		public override 097E1C51.0A706168 7A52F208()
		{
			return new 097E1C51.2B55285E(this.062B7215);
		}

		// Token: 0x060003A6 RID: 934 RVA: 0x00303C2C File Offset: 0x0018402C
		public override object 04F0F15D()
		{
			return this.062B7215;
		}

		// Token: 0x060003A7 RID: 935 RVA: 0x00303C4C File Offset: 0x0018404C
		public override void 360F4E2C(object 13217CED)
		{
			uint num = 1923891274U;
			num = (1019829047U ^ num);
			num = 924123145U * num;
			this.062B7215 = (UIntPtr)13217CED;
			num ^= 1822032540U;
			UIntPtr 70420AE = this.062B7215;
			num = 2018318767U << (int)num;
			this.51581B7F = 097E1C51.2B55285E.26437A04(70420AE);
		}

		// Token: 0x060003A8 RID: 936 RVA: 0x00303CA4 File Offset: 0x001840A4
		public override bool C77753F7()
		{
			uint num = 2092122560U;
			UIntPtr value = this.062B7215;
			num <<= 19;
			return value != UIntPtr.Zero;
		}

		// Token: 0x060003A9 RID: 937 RVA: 0x00303CD0 File Offset: 0x001840D0
		public override sbyte 52B3F2EC()
		{
			return this.51581B7F.52B3F2EC();
		}

		// Token: 0x060003AA RID: 938 RVA: 0x00303CF0 File Offset: 0x001840F0
		public override short BDAC8EA4()
		{
			uint num = 594611188U;
			num = 1728184333U % num;
			return this.51581B7F.BDAC8EA4();
		}

		// Token: 0x060003AB RID: 939 RVA: 0x00303D18 File Offset: 0x00184118
		public override int B9179A8D()
		{
			return this.51581B7F.B9179A8D();
		}

		// Token: 0x060003AC RID: 940 RVA: 0x00303D38 File Offset: 0x00184138
		public override long 8EC1A2BC()
		{
			uint num = 942631080U;
			097E1C51.0A706168 0A = this.51581B7F;
			num = (290674700U | num);
			return 0A.8EC1A2BC();
		}

		// Token: 0x060003AD RID: 941 RVA: 0x00303D60 File Offset: 0x00184160
		public override byte 19201189()
		{
			uint num = 2067090176U;
			097E1C51.0A706168 0A = this.51581B7F;
			num %= 1462770102U;
			return 0A.19201189();
		}

		// Token: 0x060003AE RID: 942 RVA: 0x00303D88 File Offset: 0x00184188
		public override ushort 86B35C1E()
		{
			return this.51581B7F.86B35C1E();
		}

		// Token: 0x060003AF RID: 943 RVA: 0x00303DA0 File Offset: 0x001841A0
		public override uint 2CD32589()
		{
			return this.51581B7F.2CD32589();
		}

		// Token: 0x060003B0 RID: 944 RVA: 0x00303DB8 File Offset: 0x001841B8
		public override ulong 083A097E()
		{
			uint num = 1949256386U;
			097E1C51.0A706168 0A = this.51581B7F;
			num -= 2002206716U;
			return 0A.083A097E();
		}

		// Token: 0x060003B1 RID: 945 RVA: 0x00303DE0 File Offset: 0x001841E0
		public override float E1E85554()
		{
			uint num = 1313550266U;
			097E1C51.0A706168 0A = this.51581B7F;
			num -= 919548133U;
			return 0A.E1E85554();
		}

		// Token: 0x060003B2 RID: 946 RVA: 0x00303E08 File Offset: 0x00184208
		public override double D87DFA30()
		{
			uint num = 877533274U;
			097E1C51.0A706168 0A = this.51581B7F;
			num = (1429176126U | num);
			return 0A.D87DFA30();
		}

		// Token: 0x060003B3 RID: 947 RVA: 0x00303E30 File Offset: 0x00184230
		public override IntPtr E3F543C7()
		{
			return this.51581B7F.E3F543C7();
		}

		// Token: 0x060003B4 RID: 948 RVA: 0x00303E50 File Offset: 0x00184250
		public override UIntPtr 7F934522()
		{
			return this.062B7215;
		}

		// Token: 0x060003B5 RID: 949 RVA: 0x00303E6C File Offset: 0x0018426C
		public override object 9E2F1085(Type 57406393, bool 055F419E)
		{
			uint num = 2139425945U;
			097E1C51.0A706168 0A = this.51581B7F;
			num *= 1054019570U;
			num -= 161373450U;
			return 0A.9E2F1085(57406393, 055F419E);
		}

		// Token: 0x040001B5 RID: 437
		private UIntPtr 062B7215;

		// Token: 0x040001B6 RID: 438
		private 097E1C51.0A706168 51581B7F;
	}

	// Token: 0x02000066 RID: 102
	private sealed class 52644614 : 097E1C51.4A781DBA
	{
		// Token: 0x060003B6 RID: 950 RVA: 0x00303E9C File Offset: 0x0018429C
		public 52644614(Enum 141557E3)
		{
			uint num = 1495611409U;
			if ((738360276U ^ num) != 0U)
			{
				for (;;)
				{
					num %= 1652913920U;
					base..ctor();
					num = 36965825U / num;
					if (num <= 1257726570U)
					{
						num *= 1360416329U;
						if (141557E3 == null)
						{
							goto IL_41;
						}
						if (34741762U > num)
						{
							break;
						}
					}
				}
				num = 1955731714U >> (int)num;
				this.4ACF1353 = 141557E3;
				if (1199180557U <= num)
				{
					Enum 6FC3553A = this.4ACF1353;
					num &= 1410752919U;
					this.333E6B8A = 097E1C51.52644614.589B7432(6FC3553A);
					return;
				}
			}
			IL_41:
			throw new ArgumentException();
		}

		// Token: 0x060003B7 RID: 951 RVA: 0x00303F34 File Offset: 0x00184334
		private static 097E1C51.0A706168 589B7432(Enum 6FC3553A)
		{
			uint num;
			for (;;)
			{
				num = 1555721843U;
				TypeCode typeCode = 6FC3553A.GetTypeCode();
				num %= 1823090180U;
				TypeCode typeCode2 = typeCode;
				uint num2 = (uint)typeCode2;
				num = 251925763U - num;
				switch (num2 - (num + 1303796085U))
				{
				case 0U:
				case 2U:
				case 4U:
					goto IL_78;
				case 1U:
				case 3U:
				case 5U:
					goto IL_50;
				case 6U:
					if (num > 533078032U)
					{
						goto Block_2;
					}
					continue;
				case 7U:
					goto IL_98;
				}
				break;
			}
			IL_4B:
			throw new InvalidOperationException();
			IL_50:
			if ((1202402761U ^ num) != 0U)
			{
				num = 895829164U * num;
				int 339906C = (int)Convert.ToUInt32(6FC3553A);
				num = (1123563886U ^ num);
				return new 097E1C51.14C26E2D(339906C);
			}
			goto IL_4B;
			IL_78:
			if (1250233252U - num != 0U)
			{
				num = 1871734942U * num;
				return new 097E1C51.14C26E2D(Convert.ToInt32(6FC3553A));
			}
			IL_98:
			long 6DB81B = (long)Convert.ToUInt64(6FC3553A);
			num = 89216762U << (int)num;
			return new 097E1C51.31EF1BAE(6DB81B);
			Block_2:
			num = (290003862U & num);
			return new 097E1C51.31EF1BAE(Convert.ToInt64(6FC3553A));
		}

		// Token: 0x060003B8 RID: 952 RVA: 0x00304018 File Offset: 0x00184418
		public override 097E1C51.0A706168 58E29230()
		{
			uint num = 705323621U;
			num = (1067410846U | num);
			097E1C51.0A706168 0A = this.333E6B8A;
			num &= 2049650320U;
			return 0A.58E29230();
		}

		// Token: 0x060003B9 RID: 953 RVA: 0x00304048 File Offset: 0x00184448
		public override Type 5F93D4ED()
		{
			return this.4ACF1353.GetType();
		}

		// Token: 0x060003BA RID: 954 RVA: 0x00304060 File Offset: 0x00184460
		public override TypeCode 6DFD59A4()
		{
			return this.333E6B8A.6DFD59A4();
		}

		// Token: 0x060003BB RID: 955 RVA: 0x00304080 File Offset: 0x00184480
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 1251696249U;
			Enum 141557E = this.4ACF1353;
			num -= 1027236661U;
			return new 097E1C51.52644614(141557E);
		}

		// Token: 0x060003BC RID: 956 RVA: 0x003040A8 File Offset: 0x001844A8
		public override object 04F0F15D()
		{
			uint num = 1345919346U;
			num /= 2105286618U;
			return this.4ACF1353;
		}

		// Token: 0x060003BD RID: 957 RVA: 0x003040CC File Offset: 0x001844CC
		public override void 360F4E2C(object 58C540C1)
		{
			uint num;
			for (;;)
			{
				num = 260047139U;
				if (58C540C1 == null)
				{
					break;
				}
				num = (478760723U & num);
				if (1169359715U + num != 0U)
				{
					goto Block_1;
				}
			}
			num = (1124030389U | num);
			if (num * 533346539U == 0U)
			{
				goto IL_4E;
			}
			IL_20:
			throw new ArgumentException();
			Block_1:
			num &= 1350320655U;
			this.4ACF1353 = (Enum)58C540C1;
			IL_4E:
			num &= 1234725022U;
			Enum 6FC3553A = this.4ACF1353;
			num = (304156799U | num);
			this.333E6B8A = 097E1C51.52644614.589B7432(6FC3553A);
			if (1121811933U / num != 0U)
			{
				return;
			}
			goto IL_20;
		}

		// Token: 0x060003BE RID: 958 RVA: 0x00304154 File Offset: 0x00184554
		public override byte 19201189()
		{
			uint num = 1202613934U;
			097E1C51.0A706168 0A = this.333E6B8A;
			num %= 1462122039U;
			return 0A.19201189();
		}

		// Token: 0x060003BF RID: 959 RVA: 0x0030417C File Offset: 0x0018457C
		public override sbyte 52B3F2EC()
		{
			return this.333E6B8A.52B3F2EC();
		}

		// Token: 0x060003C0 RID: 960 RVA: 0x0030419C File Offset: 0x0018459C
		public override short BDAC8EA4()
		{
			uint num = 614353157U;
			num |= 1348666485U;
			097E1C51.0A706168 0A = this.333E6B8A;
			num &= 1199184039U;
			return 0A.BDAC8EA4();
		}

		// Token: 0x060003C1 RID: 961 RVA: 0x003041CC File Offset: 0x001845CC
		public override ushort 86B35C1E()
		{
			return this.333E6B8A.86B35C1E();
		}

		// Token: 0x060003C2 RID: 962 RVA: 0x003041E4 File Offset: 0x001845E4
		public override int B9179A8D()
		{
			uint num = 186675025U;
			097E1C51.0A706168 0A = this.333E6B8A;
			num = 1110136799U % num;
			return 0A.B9179A8D();
		}

		// Token: 0x060003C3 RID: 963 RVA: 0x0030420C File Offset: 0x0018460C
		public override uint 2CD32589()
		{
			uint num = 310721545U;
			num = 1685852310U % num;
			097E1C51.0A706168 0A = this.333E6B8A;
			num = 199311308U / num;
			return 0A.2CD32589();
		}

		// Token: 0x060003C4 RID: 964 RVA: 0x0030423C File Offset: 0x0018463C
		public override long 8EC1A2BC()
		{
			return this.333E6B8A.8EC1A2BC();
		}

		// Token: 0x060003C5 RID: 965 RVA: 0x0030425C File Offset: 0x0018465C
		public override ulong 083A097E()
		{
			uint num = 79757712U;
			num = 1370191740U << (int)num;
			return this.333E6B8A.083A097E();
		}

		// Token: 0x060003C6 RID: 966 RVA: 0x00304288 File Offset: 0x00184688
		public override float E1E85554()
		{
			uint num = 1703937884U;
			num = 941515880U - num;
			return this.333E6B8A.E1E85554();
		}

		// Token: 0x060003C7 RID: 967 RVA: 0x003042B0 File Offset: 0x001846B0
		public override double D87DFA30()
		{
			return this.333E6B8A.D87DFA30();
		}

		// Token: 0x060003C8 RID: 968 RVA: 0x003042D0 File Offset: 0x001846D0
		public override IntPtr E3F543C7()
		{
			long value;
			while (IntPtr.Size == 4)
			{
				uint num = 168709882U;
				if (1861882680U * num != 0U)
				{
					long num2 = (long)this.B9179A8D();
					num = (334983745U | num);
					value = num2;
					num ^= 1778392433U;
					IL_45:
					return new IntPtr(value);
				}
			}
			value = this.8EC1A2BC();
			goto IL_45;
		}

		// Token: 0x060003C9 RID: 969 RVA: 0x00304328 File Offset: 0x00184728
		public override UIntPtr 7F934522()
		{
			uint num;
			do
			{
				int size = IntPtr.Size;
				num = 1342649979U;
				uint num2 = num ^ 1342649983U;
				num *= 1575580725U;
				if (size == num2)
				{
					goto IL_3D;
				}
				num &= 1729516190U;
			}
			while (num == 897854261U);
			ulong value = this.083A097E();
			goto IL_5C;
			IL_3D:
			num >>= 11;
			ulong num3 = (ulong)this.2CD32589();
			num <<= 8;
			value = num3;
			num += 4288406038U;
			IL_5C:
			num &= 921443763U;
			return new UIntPtr(value);
		}

		// Token: 0x060003CA RID: 970 RVA: 0x003043A0 File Offset: 0x001847A0
		public override object 9E2F1085(Type 45A6025F, bool 242A4858)
		{
			return this.333E6B8A.9E2F1085(45A6025F, 242A4858);
		}

		// Token: 0x040001B7 RID: 439
		private Enum 4ACF1353;

		// Token: 0x040001B8 RID: 440
		private 097E1C51.0A706168 333E6B8A;
	}

	// Token: 0x02000067 RID: 103
	private sealed class 2D2F5095 : 097E1C51.388961B0
	{
		// Token: 0x060003CB RID: 971 RVA: 0x003043C0 File Offset: 0x001847C0
		public 2D2F5095(IntPtr 7595406D, Type 3C0F5796)
		{
			uint num = 1091765351U;
			do
			{
				base..ctor();
				num = 435377560U % num;
			}
			while (num % 665072402U == 0U);
			this.48494A1B = 7595406D;
			num = 2125006938U - num;
			num /= 1646530834U;
			this.03071A91 = 3C0F5796;
		}

		// Token: 0x060003CC RID: 972 RVA: 0x0030440C File Offset: 0x0018480C
		public override Type 5F93D4ED()
		{
			return typeof(Pointer);
		}

		// Token: 0x060003CD RID: 973 RVA: 0x002F0E28 File Offset: 0x00171228
		public override TypeCode 6DFD59A4()
		{
			return TypeCode.Empty;
		}

		// Token: 0x060003CE RID: 974 RVA: 0x00304424 File Offset: 0x00184824
		public override 097E1C51.0A706168 7A52F208()
		{
			uint num = 1833706455U;
			num = 1243045821U / num;
			return new 097E1C51.2D2F5095(this.48494A1B, this.03071A91);
		}

		// Token: 0x060003CF RID: 975 RVA: 0x00304450 File Offset: 0x00184850
		public override object 04F0F15D()
		{
			uint num;
			do
			{
				num = 663174581U;
				bool isValueType = this.03071A91.IsValueType;
				num |= 475102756U;
				if (!isValueType)
				{
					goto IL_5B;
				}
				num = 463885787U * num;
			}
			while (586043755U <= num);
			IL_31:
			num += 1434732039U;
			IntPtr ptr = this.48494A1B;
			num = 1227302837U * num;
			Type structureType = this.03071A91;
			num = 801439914U * num;
			return Marshal.PtrToStructure(ptr, structureType);
			IL_5B:
			if ((1643738918U & num) != 0U)
			{
				throw new InvalidOperationException();
			}
			goto IL_31;
		}

		// Token: 0x060003D0 RID: 976 RVA: 0x003044CC File Offset: 0x001848CC
		public override void 360F4E2C(object 30ED5B94)
		{
			uint num = 662721883U;
			for (;;)
			{
				if (30ED5B94 != null)
				{
					goto IL_26;
				}
				num = 1884439750U * num;
				if (num << 25 != 0U)
				{
					break;
				}
				for (;;)
				{
					IL_7D:
					num = 902658093U << (int)num;
					Type type = 30ED5B94.GetType();
					num /= 23213242U;
					TypeCode typeCode = Type.GetTypeCode(type);
					if (num * 38426023U != 0U)
					{
						object obj = typeCode;
						num = (1289035113U ^ num);
						object obj2 = num ^ 1289035083U;
						num += 1777553006U;
						object obj3 = obj - obj2;
						num += 785145511U;
						switch (obj3)
						{
						case 0:
							goto IL_197;
						case 1:
							goto IL_10E;
						case 2:
							goto IL_14A;
						case 3:
							goto IL_1CC;
						case 4:
							num = 1049760039U / num;
							Marshal.WriteInt16(this.48494A1B, (short)Convert.ToUInt16(30ED5B94));
							if (num != 867318772U)
							{
								return;
							}
							continue;
						case 5:
							goto IL_218;
						case 6:
							goto IL_24D;
						case 7:
							goto IL_27F;
						case 8:
							goto IL_2A1;
						case 9:
							goto IL_2CE;
						case 10:
							goto IL_31C;
						}
						goto Block_5;
					}
					goto IL_26;
				}
				IL_14A:
				if (2137222246U >= num)
				{
					continue;
				}
				num = 1304910816U >> (int)num;
				IntPtr ptr = this.48494A1B;
				num = (1465410095U | num);
				byte val = Convert.ToByte(30ED5B94);
				num %= 1663309006U;
				Marshal.WriteByte(ptr, val);
				if (958737989U >> (int)num == 0U)
				{
					return;
				}
				goto IL_26;
				IL_2CE:
				num = 356871621U + num;
				if (1671050203U <= num)
				{
					goto Block_15;
				}
				continue;
				IL_31C:
				if (960124372U % num == 0U)
				{
					continue;
				}
				IntPtr ptr2 = this.48494A1B;
				num -= 1876565665U;
				byte[] bytes = BitConverter.GetBytes(Convert.ToDouble(30ED5B94));
				num %= 1047397549U;
				int startIndex = (int)(num ^ 927770390U);
				num <<= 18;
				Marshal.WriteInt64(ptr2, BitConverter.ToInt64(bytes, startIndex));
				if (num + 1689871626U != 0U)
				{
					return;
				}
				continue;
				IL_10E:
				if (num != 1872845203U)
				{
					goto Block_7;
				}
				goto IL_26;
				IL_197:
				if (num != 456201594U)
				{
					goto Block_10;
				}
				IL_26:
				num |= 1883644073U;
				Type type2 = this.03071A91;
				num += 434967045U;
				if (!type2.IsValueType)
				{
					goto IL_7D;
				}
				num &= 1154101471U;
				num = (1616279208U ^ num);
				IntPtr ptr3 = this.48494A1B;
				num |= 14231147U;
				Marshal.StructureToPtr(30ED5B94, ptr3, (num ^ 1625126635U) != 0U);
				if (num >= 524695318U)
				{
					return;
				}
			}
			IL_20:
			throw new ArgumentException();
			Block_5:
			if (num % 894851636U != 0U)
			{
				throw new ArgumentException();
			}
			goto IL_20;
			Block_7:
			num = 660020093U << (int)num;
			IntPtr ptr4 = this.48494A1B;
			num ^= 1099704917U;
			byte val2 = (byte)Convert.ToSByte(30ED5B94);
			num = 1394941381U + num;
			Marshal.WriteByte(ptr4, val2);
			return;
			Block_10:
			num ^= 1482974534U;
			IntPtr ptr5 = this.48494A1B;
			num *= 667826693U;
			num = (869937136U | num);
			Marshal.WriteInt16(ptr5, Convert.ToChar(30ED5B94));
			return;
			IL_1CC:
			IntPtr ptr6 = this.48494A1B;
			num |= 470120170U;
			Marshal.WriteInt16(ptr6, Convert.ToInt16(30ED5B94));
			if (14638341U + num != 0U)
			{
				return;
			}
			goto IL_20;
			IL_218:
			num = 981090961U * num;
			if (1980499252U < num)
			{
				IntPtr ptr7 = this.48494A1B;
				num = 2137396249U % num;
				num <<= 14;
				Marshal.WriteInt32(ptr7, Convert.ToInt32(30ED5B94));
				return;
			}
			goto IL_20;
			IL_24D:
			num = (560861635U & num);
			num = 172113320U % num;
			IntPtr ptr8 = this.48494A1B;
			num -= 44174061U;
			int val3 = (int)Convert.ToUInt32(30ED5B94);
			num = 267085098U + num;
			Marshal.WriteInt32(ptr8, val3);
			return;
			IL_27F:
			num |= 382476982U;
			IntPtr ptr9 = this.48494A1B;
			num |= 974213211U;
			Marshal.WriteInt64(ptr9, Convert.ToInt64(30ED5B94));
			return;
			IL_2A1:
			IntPtr ptr10 = this.48494A1B;
			num %= 1318404652U;
			long val4 = (long)Convert.ToUInt64(30ED5B94);
			num /= 1034626972U;
			Marshal.WriteInt64(ptr10, val4);
			if (944076705U > num)
			{
				return;
			}
			goto IL_20;
			Block_15:
			num -= 1706433955U;
			IntPtr ptr11 = this.48494A1B;
			num += 546988814U;
			float value = Convert.ToSingle(30ED5B94);
			num -= 1383422642U;
			Marshal.WriteInt32(ptr11, BitConverter.ToInt32(BitConverter.GetBytes(value), (int)(num ^ 1665737442U)));
		}

		// Token: 0x060003D1 RID: 977 RVA: 0x00304850 File Offset: 0x00184C50
		public override sbyte 52B3F2EC()
		{
			uint num = 87442768U;
			IntPtr ptr = this.48494A1B;
			num = 326900625U * num;
			sbyte b = (sbyte)Marshal.ReadByte(ptr);
			num &= 634916374U;
			return b;
		}

		// Token: 0x060003D2 RID: 978 RVA: 0x00304880 File Offset: 0x00184C80
		public override short BDAC8EA4()
		{
			return Marshal.ReadInt16(this.48494A1B);
		}

		// Token: 0x060003D3 RID: 979 RVA: 0x003048A0 File Offset: 0x00184CA0
		public override int B9179A8D()
		{
			uint num = 865816416U;
			num = 1500588375U + num;
			return Marshal.ReadInt32(this.48494A1B);
		}

		// Token: 0x060003D4 RID: 980 RVA: 0x003048C8 File Offset: 0x00184CC8
		public override long 8EC1A2BC()
		{
			return Marshal.ReadInt64(this.48494A1B);
		}

		// Token: 0x060003D5 RID: 981 RVA: 0x003048E8 File Offset: 0x00184CE8
		public override char A773FD03()
		{
			return (char)Marshal.ReadInt16(this.48494A1B);
		}

		// Token: 0x060003D6 RID: 982 RVA: 0x00304908 File Offset: 0x00184D08
		public override byte 19201189()
		{
			uint num = 2141026440U;
			num = 1694256292U / num;
			IntPtr ptr = this.48494A1B;
			num *= 559681232U;
			return Marshal.ReadByte(ptr);
		}

		// Token: 0x060003D7 RID: 983 RVA: 0x003048E8 File Offset: 0x00184CE8
		public override ushort 86B35C1E()
		{
			return (ushort)Marshal.ReadInt16(this.48494A1B);
		}

		// Token: 0x060003D8 RID: 984 RVA: 0x003048A0 File Offset: 0x00184CA0
		public override uint 2CD32589()
		{
			uint num = 865816416U;
			num = 1500588375U + num;
			return (uint)Marshal.ReadInt32(this.48494A1B);
		}

		// Token: 0x060003D9 RID: 985 RVA: 0x003048C8 File Offset: 0x00184CC8
		public override ulong 083A097E()
		{
			return (ulong)Marshal.ReadInt64(this.48494A1B);
		}

		// Token: 0x060003DA RID: 986 RVA: 0x00304938 File Offset: 0x00184D38
		public override float E1E85554()
		{
			int value = Marshal.ReadInt32(this.48494A1B);
			uint num = 767766991U;
			byte[] bytes = BitConverter.GetBytes(value);
			num |= 1770354122U;
			return BitConverter.ToSingle(bytes, (int)(num ^ 1841788367U));
		}

		// Token: 0x060003DB RID: 987 RVA: 0x00304970 File Offset: 0x00184D70
		public override double D87DFA30()
		{
			uint num = 1696336959U;
			IntPtr ptr = this.48494A1B;
			num = 85146828U >> (int)num;
			return BitConverter.ToDouble(BitConverter.GetBytes(Marshal.ReadInt64(ptr)), (int)(num - 0U));
		}

		// Token: 0x060003DC RID: 988 RVA: 0x003049B0 File Offset: 0x00184DB0
		public override IntPtr E3F543C7()
		{
			uint num = 1304369042U;
			long value;
			if (num >= 1218727594U)
			{
				do
				{
					int size = IntPtr.Size;
					num ^= 1805584351U;
					uint num2 = num ^ 639635529U;
					num <<= 2;
					if (size != num2)
					{
						goto IL_32;
					}
					num >>= 31;
				}
				while (num >= 555236131U);
				num = (1691118988U | num);
				value = (long)Marshal.ReadInt32(this.48494A1B);
				num ^= 1691118988U;
				goto IL_79;
			}
			IL_32:
			num /= 1844583135U;
			value = Marshal.ReadInt64(this.48494A1B);
			IL_79:
			return new IntPtr(value);
		}

		// Token: 0x060003DD RID: 989 RVA: 0x00304A3C File Offset: 0x00184E3C
		public override UIntPtr 7F934522()
		{
			uint num = 1187729446U;
			do
			{
				int size = IntPtr.Size;
				uint num2 = num ^ 1187729442U;
				num %= 246380524U;
				if (size == num2)
				{
					goto IL_4A;
				}
				num = (609778424U & num);
			}
			while (611599889U == num);
			IL_32:
			num ^= 1738215677U;
			ulong value = (ulong)Marshal.ReadInt64(this.48494A1B);
			goto IL_71;
			IL_4A:
			num = 1715431668U + num;
			if (num >= 2079875624U)
			{
				goto IL_32;
			}
			value = (ulong)Marshal.ReadInt32(this.48494A1B);
			num ^= 299874791U;
			IL_71:
			return new UIntPtr(value);
		}

		// Token: 0x040001B9 RID: 441
		private IntPtr 48494A1B;

		// Token: 0x040001BA RID: 442
		private Type 03071A91;
	}

	// Token: 0x02000068 RID: 104
	private sealed class 54B80CE2
	{
		// Token: 0x060003DE RID: 990 RVA: 0x00304AC0 File Offset: 0x00184EC0
		public 54B80CE2(byte 282327AA, int 58BD1D00, int 5675314B)
		{
			for (;;)
			{
				this.7D576CFF = 282327AA;
				this.264F1675 = 58BD1D00;
				uint num = 1651186669U;
				if (num * 1758407276U != 0U)
				{
					this.12EF132D = 5675314B;
					if (num != 1368458641U)
					{
						break;
					}
				}
			}
		}

		// Token: 0x060003DF RID: 991 RVA: 0x00304B08 File Offset: 0x00184F08
		public byte 35B577B7()
		{
			return this.7D576CFF;
		}

		// Token: 0x060003E0 RID: 992 RVA: 0x00304B24 File Offset: 0x00184F24
		public int 2AED5988()
		{
			return this.264F1675;
		}

		// Token: 0x060003E1 RID: 993 RVA: 0x00304B40 File Offset: 0x00184F40
		public int 19354B25()
		{
			return this.12EF132D;
		}

		// Token: 0x040001BB RID: 443
		private byte 7D576CFF;

		// Token: 0x040001BC RID: 444
		private int 264F1675;

		// Token: 0x040001BD RID: 445
		private int 12EF132D;
	}

	// Token: 0x02000069 RID: 105
	private sealed class 5C236423
	{
		// Token: 0x060003E2 RID: 994 RVA: 0x00304B54 File Offset: 0x00184F54
		public 5C236423(int 41125F96, int 205111BB)
		{
			uint num;
			do
			{
				num = 741764329U;
				this.40BD490E = new List<097E1C51.54B80CE2>();
				num -= 1541736177U;
				do
				{
					base..ctor();
				}
				while ((106779890U ^ num) == 0U);
				do
				{
					this.395E422B = 41125F96;
				}
				while (num % 251855387U == 0U);
				num = (877864956U & num);
				this.27E637C1 = 205111BB;
			}
			while (num == 758342244U);
		}

		// Token: 0x060003E3 RID: 995 RVA: 0x00304BBC File Offset: 0x00184FBC
		public int 23AA1DEA()
		{
			uint num = 1184312181U;
			num = 443750391U - num;
			return this.395E422B;
		}

		// Token: 0x060003E4 RID: 996 RVA: 0x00304BE0 File Offset: 0x00184FE0
		public int 065D025F()
		{
			return this.27E637C1;
		}

		// Token: 0x060003E5 RID: 997 RVA: 0x00304BFC File Offset: 0x00184FFC
		public int 7D7A5133(097E1C51.5C236423 495D4AC1)
		{
			uint num = 2011711305U;
			if (341978088U + num != 0U)
			{
				while (495D4AC1 != null)
				{
					if ((num & 1926185655U) != 0U)
					{
						num = 1673670750U / num;
						num = 286592902U + num;
						int value = 495D4AC1.23AA1DEA();
						num = 1141527880U % num;
						int num2 = this.395E422B.CompareTo(value);
						num >>= 31;
						int num3 = num2;
						num += 1989492755U;
						if (num3 == 0)
						{
							num ^= 70730843U;
							if (312441560U > num)
							{
								break;
							}
							int num4 = 495D4AC1.065D025F();
							if (num * 2053991246U == 0U)
							{
								break;
							}
							num /= 570757819U;
							int value2 = this.27E637C1;
							num = 1301110828U * num;
							num3 = num4.CompareTo(value2);
							num ^= 2654822551U;
						}
						num %= 806295375U;
						return num3;
					}
				}
			}
			return (int)(num + 2283255992U);
		}

		// Token: 0x060003E6 RID: 998 RVA: 0x00304CCC File Offset: 0x001850CC
		public void 0F984DA8(byte 29EB0DDF, int 1B584DDE, int 50A937A1)
		{
			uint num = 944441654U;
			if (1782346325U != num)
			{
				do
				{
					num = (1422082917U | num);
					List<097E1C51.54B80CE2> list = this.40BD490E;
					num += 1192059608U;
					097E1C51.54B80CE2 item = new 097E1C51.54B80CE2(29EB0DDF, 1B584DDE, 50A937A1);
					num = (1350461966U ^ num);
					list.Add(item);
				}
				while (1419998143U % num == 0U);
			}
		}

		// Token: 0x060003E7 RID: 999 RVA: 0x00304D24 File Offset: 0x00185124
		public List<097E1C51.54B80CE2> 75F02B24()
		{
			return this.40BD490E;
		}

		// Token: 0x040001BE RID: 446
		private int 395E422B;

		// Token: 0x040001BF RID: 447
		private int 27E637C1;

		// Token: 0x040001C0 RID: 448
		private List<097E1C51.54B80CE2> 40BD490E;
	}

	// Token: 0x0200006A RID: 106
	// (Invoke) Token: 0x060003E9 RID: 1001
	internal delegate void 1F7F0274();
}
