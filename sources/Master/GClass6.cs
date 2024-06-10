using System;
using System.Collections.Generic;
using System.Reflection;
using System.Reflection.Emit;
using System.Runtime.InteropServices;
using System.Threading;

namespace Client
{
	// Token: 0x020002E1 RID: 737
	public class GClass6
	{
		// Token: 0x060035B9 RID: 13753 RVA: 0x000208E4 File Offset: 0x0001EAE4
		public GClass6()
		{
			this.long_0 = Marshal.GetHINSTANCE(this.module_0).ToInt64();
			this.dictionary_0[0U] = new GClass6.Delegate0(this.method_68);
			this.dictionary_0[1U] = new GClass6.Delegate0(this.method_81);
			this.dictionary_0[2U] = new GClass6.Delegate0(this.method_56);
			this.dictionary_0[3U] = new GClass6.Delegate0(this.method_59);
			this.dictionary_0[4U] = new GClass6.Delegate0(this.method_39);
			this.dictionary_0[5U] = new GClass6.Delegate0(this.method_75);
			this.dictionary_0[6U] = new GClass6.Delegate0(this.method_101);
			this.dictionary_0[7U] = new GClass6.Delegate0(this.method_72);
			this.dictionary_0[8U] = new GClass6.Delegate0(this.method_38);
			this.dictionary_0[9U] = new GClass6.Delegate0(this.method_82);
			this.dictionary_0[10U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[11U] = new GClass6.Delegate0(this.method_95);
			this.dictionary_0[12U] = new GClass6.Delegate0(this.method_91);
			this.dictionary_0[13U] = new GClass6.Delegate0(this.method_76);
			this.dictionary_0[14U] = new GClass6.Delegate0(this.method_65);
			this.dictionary_0[15U] = new GClass6.Delegate0(this.method_102);
			this.dictionary_0[16U] = new GClass6.Delegate0(this.method_87);
			this.dictionary_0[17U] = new GClass6.Delegate0(this.method_49);
			this.dictionary_0[18U] = new GClass6.Delegate0(this.method_86);
			this.dictionary_0[19U] = new GClass6.Delegate0(this.method_74);
			this.dictionary_0[20U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[21U] = new GClass6.Delegate0(this.method_93);
			this.dictionary_0[22U] = new GClass6.Delegate0(this.method_80);
			this.dictionary_0[23U] = new GClass6.Delegate0(this.method_36);
			this.dictionary_0[24U] = new GClass6.Delegate0(this.method_45);
			this.dictionary_0[25U] = new GClass6.Delegate0(this.method_99);
			this.dictionary_0[26U] = new GClass6.Delegate0(this.method_92);
			this.dictionary_0[27U] = new GClass6.Delegate0(this.method_67);
			this.dictionary_0[28U] = new GClass6.Delegate0(this.method_88);
			this.dictionary_0[29U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[30U] = new GClass6.Delegate0(this.method_73);
			this.dictionary_0[31U] = new GClass6.Delegate0(this.method_47);
			this.dictionary_0[32U] = new GClass6.Delegate0(this.method_37);
			this.dictionary_0[33U] = new GClass6.Delegate0(this.method_107);
			this.dictionary_0[34U] = new GClass6.Delegate0(this.method_78);
			this.dictionary_0[35U] = new GClass6.Delegate0(this.method_89);
			this.dictionary_0[36U] = new GClass6.Delegate0(this.method_50);
			this.dictionary_0[37U] = new GClass6.Delegate0(this.method_105);
			this.dictionary_0[38U] = new GClass6.Delegate0(this.method_58);
			this.dictionary_0[39U] = new GClass6.Delegate0(this.method_55);
			this.dictionary_0[40U] = new GClass6.Delegate0(this.method_96);
			this.dictionary_0[41U] = new GClass6.Delegate0(this.method_109);
			this.dictionary_0[42U] = new GClass6.Delegate0(this.method_69);
			this.dictionary_0[43U] = new GClass6.Delegate0(this.method_98);
			this.dictionary_0[44U] = new GClass6.Delegate0(this.method_41);
			this.dictionary_0[45U] = new GClass6.Delegate0(this.method_70);
			this.dictionary_0[46U] = new GClass6.Delegate0(this.method_94);
			this.dictionary_0[47U] = new GClass6.Delegate0(this.method_85);
			this.dictionary_0[48U] = new GClass6.Delegate0(this.method_62);
			this.dictionary_0[49U] = new GClass6.Delegate0(this.method_110);
			this.dictionary_0[50U] = new GClass6.Delegate0(this.method_103);
			this.dictionary_0[51U] = new GClass6.Delegate0(this.method_84);
			this.dictionary_0[52U] = new GClass6.Delegate0(this.method_90);
			this.dictionary_0[53U] = new GClass6.Delegate0(this.method_53);
			this.dictionary_0[54U] = new GClass6.Delegate0(this.method_60);
			this.dictionary_0[55U] = new GClass6.Delegate0(this.method_9);
			this.dictionary_0[56U] = new GClass6.Delegate0(this.method_40);
			this.dictionary_0[57U] = new GClass6.Delegate0(this.method_64);
			this.dictionary_0[58U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[59U] = new GClass6.Delegate0(this.method_77);
			this.dictionary_0[60U] = new GClass6.Delegate0(this.method_42);
			this.dictionary_0[61U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[62U] = new GClass6.Delegate0(this.method_44);
			this.dictionary_0[63U] = new GClass6.Delegate0(this.method_108);
			this.dictionary_0[64U] = new GClass6.Delegate0(this.method_61);
			this.dictionary_0[65U] = new GClass6.Delegate0(this.method_100);
			this.dictionary_0[66U] = new GClass6.Delegate0(this.method_83);
			this.dictionary_0[67U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[68U] = new GClass6.Delegate0(this.method_57);
			this.dictionary_0[69U] = new GClass6.Delegate0(this.method_106);
			this.dictionary_0[70U] = new GClass6.Delegate0(this.method_97);
			this.dictionary_0[71U] = new GClass6.Delegate0(this.method_79);
			this.dictionary_0[72U] = new GClass6.Delegate0(this.method_66);
			this.dictionary_0[73U] = new GClass6.Delegate0(this.method_104);
			this.dictionary_0[74U] = new GClass6.Delegate0(this.method_63);
			this.dictionary_0[75U] = new GClass6.Delegate0(this.method_48);
			this.dictionary_0[76U] = new GClass6.Delegate0(this.method_54);
			this.dictionary_0[77U] = new GClass6.Delegate0(this.method_64);
			this.dictionary_0[78U] = new GClass6.Delegate0(this.method_66);
			this.dictionary_0[79U] = new GClass6.Delegate0(this.method_9);
			this.dictionary_0[80U] = new GClass6.Delegate0(this.method_44);
			this.dictionary_0[81U] = new GClass6.Delegate0(this.method_109);
			this.dictionary_0[82U] = new GClass6.Delegate0(this.method_85);
			this.dictionary_0[83U] = new GClass6.Delegate0(this.method_72);
			this.dictionary_0[84U] = new GClass6.Delegate0(this.method_99);
			this.dictionary_0[85U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[86U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[87U] = new GClass6.Delegate0(this.method_64);
			this.dictionary_0[88U] = new GClass6.Delegate0(this.method_97);
			this.dictionary_0[89U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[90U] = new GClass6.Delegate0(this.method_45);
			this.dictionary_0[91U] = new GClass6.Delegate0(this.method_80);
			this.dictionary_0[92U] = new GClass6.Delegate0(this.method_102);
			this.dictionary_0[93U] = new GClass6.Delegate0(this.method_45);
			this.dictionary_0[94U] = new GClass6.Delegate0(this.method_74);
			this.dictionary_0[95U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[96U] = new GClass6.Delegate0(this.method_93);
			this.dictionary_0[97U] = new GClass6.Delegate0(this.method_39);
			this.dictionary_0[98U] = new GClass6.Delegate0(this.method_41);
			this.dictionary_0[99U] = new GClass6.Delegate0(this.method_53);
			this.dictionary_0[100U] = new GClass6.Delegate0(this.method_103);
			this.dictionary_0[101U] = new GClass6.Delegate0(this.method_104);
			this.dictionary_0[102U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[103U] = new GClass6.Delegate0(this.method_84);
			this.dictionary_0[104U] = new GClass6.Delegate0(this.method_108);
			this.dictionary_0[105U] = new GClass6.Delegate0(this.method_106);
			this.dictionary_0[106U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[107U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[108U] = new GClass6.Delegate0(this.method_77);
			this.dictionary_0[109U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[110U] = new GClass6.Delegate0(this.method_67);
			this.dictionary_0[111U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[112U] = new GClass6.Delegate0(this.method_77);
			this.dictionary_0[113U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[114U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[115U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[116U] = new GClass6.Delegate0(this.method_98);
			this.dictionary_0[117U] = new GClass6.Delegate0(this.method_93);
			this.dictionary_0[118U] = new GClass6.Delegate0(this.method_105);
			this.dictionary_0[119U] = new GClass6.Delegate0(this.method_59);
			this.dictionary_0[120U] = new GClass6.Delegate0(this.method_45);
			this.dictionary_0[121U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[122U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[123U] = new GClass6.Delegate0(this.method_69);
			this.dictionary_0[124U] = new GClass6.Delegate0(this.method_70);
			this.dictionary_0[125U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[126U] = new GClass6.Delegate0(this.method_69);
			this.dictionary_0[127U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[128U] = new GClass6.Delegate0(this.method_45);
			this.dictionary_0[129U] = new GClass6.Delegate0(this.method_108);
			this.dictionary_0[130U] = new GClass6.Delegate0(this.method_107);
			this.dictionary_0[131U] = new GClass6.Delegate0(this.method_102);
			this.dictionary_0[132U] = new GClass6.Delegate0(this.method_101);
			this.dictionary_0[133U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[134U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[135U] = new GClass6.Delegate0(this.method_99);
			this.dictionary_0[136U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[137U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[138U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[139U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[140U] = new GClass6.Delegate0(this.method_77);
			this.dictionary_0[141U] = new GClass6.Delegate0(this.method_99);
			this.dictionary_0[142U] = new GClass6.Delegate0(this.method_68);
			this.dictionary_0[143U] = new GClass6.Delegate0(this.method_44);
			this.dictionary_0[144U] = new GClass6.Delegate0(this.method_72);
			this.dictionary_0[145U] = new GClass6.Delegate0(this.method_64);
			this.dictionary_0[146U] = new GClass6.Delegate0(this.method_74);
			this.dictionary_0[147U] = new GClass6.Delegate0(this.method_103);
			this.dictionary_0[148U] = new GClass6.Delegate0(this.method_56);
			this.dictionary_0[149U] = new GClass6.Delegate0(this.method_38);
			this.dictionary_0[150U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[151U] = new GClass6.Delegate0(this.method_54);
			this.dictionary_0[152U] = new GClass6.Delegate0(this.method_58);
			this.dictionary_0[153U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[154U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[155U] = new GClass6.Delegate0(this.method_77);
			this.dictionary_0[156U] = new GClass6.Delegate0(this.method_99);
			this.dictionary_0[157U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[158U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[159U] = new GClass6.Delegate0(this.method_69);
			this.dictionary_0[160U] = new GClass6.Delegate0(this.method_38);
			this.dictionary_0[161U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[162U] = new GClass6.Delegate0(this.method_59);
			this.dictionary_0[163U] = new GClass6.Delegate0(this.method_85);
			this.dictionary_0[164U] = new GClass6.Delegate0(this.method_109);
			this.dictionary_0[165U] = new GClass6.Delegate0(this.method_105);
			this.dictionary_0[166U] = new GClass6.Delegate0(this.method_56);
			this.dictionary_0[167U] = new GClass6.Delegate0(this.method_82);
			this.dictionary_0[168U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[169U] = new GClass6.Delegate0(this.method_103);
			this.dictionary_0[170U] = new GClass6.Delegate0(this.method_103);
			this.dictionary_0[171U] = new GClass6.Delegate0(this.method_60);
			this.dictionary_0[172U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[173U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[174U] = new GClass6.Delegate0(this.method_45);
			this.dictionary_0[175U] = new GClass6.Delegate0(this.method_100);
			this.dictionary_0[176U] = new GClass6.Delegate0(this.method_80);
			this.dictionary_0[177U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[178U] = new GClass6.Delegate0(this.method_59);
			this.dictionary_0[179U] = new GClass6.Delegate0(this.method_104);
			this.dictionary_0[180U] = new GClass6.Delegate0(this.method_39);
			this.dictionary_0[181U] = new GClass6.Delegate0(this.method_103);
			this.dictionary_0[182U] = new GClass6.Delegate0(this.method_58);
			this.dictionary_0[183U] = new GClass6.Delegate0(this.method_54);
			this.dictionary_0[184U] = new GClass6.Delegate0(this.method_64);
			this.dictionary_0[185U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[186U] = new GClass6.Delegate0(this.method_88);
			this.dictionary_0[187U] = new GClass6.Delegate0(this.method_103);
			this.dictionary_0[188U] = new GClass6.Delegate0(this.method_67);
			this.dictionary_0[189U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[190U] = new GClass6.Delegate0(this.method_66);
			this.dictionary_0[191U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[192U] = new GClass6.Delegate0(this.method_69);
			this.dictionary_0[193U] = new GClass6.Delegate0(this.method_70);
			this.dictionary_0[194U] = new GClass6.Delegate0(this.method_75);
			this.dictionary_0[195U] = new GClass6.Delegate0(this.method_99);
			this.dictionary_0[196U] = new GClass6.Delegate0(this.method_53);
			this.dictionary_0[197U] = new GClass6.Delegate0(this.method_108);
			this.dictionary_0[198U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[199U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[200U] = new GClass6.Delegate0(this.method_59);
			this.dictionary_0[201U] = new GClass6.Delegate0(this.method_53);
			this.dictionary_0[202U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[203U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[204U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[205U] = new GClass6.Delegate0(this.method_78);
			this.dictionary_0[206U] = new GClass6.Delegate0(this.method_108);
			this.dictionary_0[207U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[208U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[209U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[210U] = new GClass6.Delegate0(this.method_9);
			this.dictionary_0[211U] = new GClass6.Delegate0(this.method_105);
			this.dictionary_0[212U] = new GClass6.Delegate0(this.method_89);
			this.dictionary_0[213U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[214U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[215U] = new GClass6.Delegate0(this.method_44);
			this.dictionary_0[216U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[217U] = new GClass6.Delegate0(this.method_106);
			this.dictionary_0[218U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[219U] = new GClass6.Delegate0(this.method_66);
			this.dictionary_0[220U] = new GClass6.Delegate0(this.method_66);
			this.dictionary_0[221U] = new GClass6.Delegate0(this.method_54);
			this.dictionary_0[222U] = new GClass6.Delegate0(this.method_59);
			this.dictionary_0[223U] = new GClass6.Delegate0(this.method_77);
			this.dictionary_0[224U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[225U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[226U] = new GClass6.Delegate0(this.method_46);
			this.dictionary_0[227U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[228U] = new GClass6.Delegate0(this.method_99);
			this.dictionary_0[229U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[230U] = new GClass6.Delegate0(this.method_85);
			this.dictionary_0[231U] = new GClass6.Delegate0(this.method_69);
			this.dictionary_0[232U] = new GClass6.Delegate0(this.method_87);
			this.dictionary_0[233U] = new GClass6.Delegate0(this.method_108);
			this.dictionary_0[234U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[235U] = new GClass6.Delegate0(this.method_45);
			this.dictionary_0[236U] = new GClass6.Delegate0(this.method_60);
			this.dictionary_0[237U] = new GClass6.Delegate0(this.method_85);
			this.dictionary_0[238U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[239U] = new GClass6.Delegate0(this.method_52);
			this.dictionary_0[240U] = new GClass6.Delegate0(this.method_59);
			this.dictionary_0[241U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[242U] = new GClass6.Delegate0(this.method_9);
			this.dictionary_0[243U] = new GClass6.Delegate0(this.method_110);
			this.dictionary_0[244U] = new GClass6.Delegate0(this.method_51);
			this.dictionary_0[245U] = new GClass6.Delegate0(this.method_58);
			this.dictionary_0[246U] = new GClass6.Delegate0(this.method_64);
			this.dictionary_0[247U] = new GClass6.Delegate0(this.method_40);
			this.dictionary_0[248U] = new GClass6.Delegate0(this.method_43);
			this.dictionary_0[249U] = new GClass6.Delegate0(this.method_35);
			this.dictionary_0[250U] = new GClass6.Delegate0(this.method_70);
			this.dictionary_0[251U] = new GClass6.Delegate0(this.method_71);
			this.dictionary_0[252U] = new GClass6.Delegate0(this.method_67);
			this.dictionary_0[253U] = new GClass6.Delegate0(this.method_80);
			this.dictionary_0[254U] = new GClass6.Delegate0(this.method_9);
			this.dictionary_0[255U] = new GClass6.Delegate0(this.method_109);
		}

		// Token: 0x060035BA RID: 13754 RVA: 0x000223EC File Offset: 0x000205EC
		private void method_0(GClass6.Class709 class709_0)
		{
			this.stack_0.Push(class709_0.vmethod_4());
		}

		// Token: 0x060035BB RID: 13755 RVA: 0x0002240C File Offset: 0x0002060C
		private GClass6.Class709 method_1()
		{
			return this.stack_0.Pop();
		}

		// Token: 0x060035BC RID: 13756 RVA: 0x00022424 File Offset: 0x00020624
		private GClass6.Class709 method_2()
		{
			return this.stack_0.Peek();
		}

		// Token: 0x060035BD RID: 13757 RVA: 0x0002243C File Offset: 0x0002063C
		private byte method_3()
		{
			byte result = Marshal.ReadByte(new IntPtr(this.long_0 + (long)this.int_0));
			this.int_0++;
			return result;
		}

		// Token: 0x060035BE RID: 13758 RVA: 0x00022470 File Offset: 0x00020670
		private short method_4()
		{
			short result = Marshal.ReadInt16(new IntPtr(this.long_0 + (long)this.int_0));
			this.int_0 += 2;
			return result;
		}

		// Token: 0x060035BF RID: 13759 RVA: 0x000224A4 File Offset: 0x000206A4
		private int method_5()
		{
			int result = Marshal.ReadInt32(new IntPtr(this.long_0 + (long)this.int_0));
			this.int_0 += 4;
			return result;
		}

		// Token: 0x060035C0 RID: 13760 RVA: 0x000224D8 File Offset: 0x000206D8
		private long method_6()
		{
			long result = Marshal.ReadInt64(new IntPtr(this.long_0 + (long)this.int_0));
			this.int_0 += 8;
			return result;
		}

		// Token: 0x060035C1 RID: 13761 RVA: 0x0002250C File Offset: 0x0002070C
		private float method_7()
		{
			return BitConverter.ToSingle(BitConverter.GetBytes(this.method_5()), 0);
		}

		// Token: 0x060035C2 RID: 13762 RVA: 0x0002252C File Offset: 0x0002072C
		private double method_8()
		{
			return BitConverter.ToDouble(BitConverter.GetBytes(this.method_6()), 0);
		}

		// Token: 0x060035C3 RID: 13763 RVA: 0x0002254C File Offset: 0x0002074C
		private void method_9()
		{
			for (;;)
			{
				byte b = this.method_3();
				uint num = 1973836070U;
				for (;;)
				{
					IL_302:
					int num2 = this.method_5();
					num >>= 12;
					if (num / 1583100256U != 0U)
					{
						break;
					}
					num -= 1092117689U;
					int num3 = this.method_5();
					if (1094714740U >> (int)num == 0U)
					{
						break;
					}
					int int_ = this.method_5();
					if (num >> 7 != 0U)
					{
						num -= 827740401U;
						int int_2 = this.method_5();
						num |= 1140537430U;
						GClass6.Class739 @class;
						int num4;
						if (num - 183766602U != 0U)
						{
							@class = null;
							num |= 1684813168U;
							num4 = (int)(num ^ 4026514943U);
						}
						GClass6.Class739 class2;
						for (;;)
						{
							num <<= 29;
							if (521363190U > num)
							{
								goto IL_302;
							}
							int num5 = num4;
							num = (473236357U & num);
							if (num5 >= this.list_1.Count)
							{
								goto IL_151;
							}
							class2 = this.list_1[num4];
							int num6 = class2.method_0();
							int num7 = num2;
							num = 3751747140U;
							if (num6 == num7)
							{
								if (num < 493780174U)
								{
									goto IL_302;
								}
								int num8 = class2.method_1();
								num -= 187059492U;
								int num9 = num3;
								num <<= 28;
								num += 3751747140U;
								if (num8 == num9)
								{
									goto Block_6;
								}
							}
							num -= 673141023U;
							int num10 = num4 + (int)(num + 1216361180U);
							num /= 1179795125U;
							num4 = num10;
							num += 4026514941U;
						}
						IL_15B:
						num %= 485043715U;
						if (@class == null)
						{
							bool flag = num - 356441135U != 0U;
							num = 540609140U << (int)num;
							int int_3 = num2;
							num &= 1023757392U;
							@class = new GClass6.Class739(int_3, num3);
							num |= 584661147U;
							int num11 = (int)(num ^ 668547227U);
							num = 519061658U % num;
							int num12 = num11;
							if (num <= 1720075426U)
							{
								for (;;)
								{
									num = (1133341432U & num);
									int num13 = num12;
									List<GClass6.Class739> list = this.list_1;
									num = (1995253050U & num);
									if (num13 >= list.Count)
									{
										goto Block_11;
									}
									GClass6.Class739 class739_ = this.list_1[num12];
									int num14 = @class.method_2(class739_);
									num = 9593U;
									if (num14 < 0)
									{
										goto IL_243;
									}
									int num15 = num12;
									int num16 = (int)(num ^ 9592U);
									num *= 794039912U;
									num12 = num15 + num16;
									num ^= 2601246642U;
								}
								IL_283:
								bool flag2 = flag;
								num += 4218804109U;
								if (!flag2)
								{
									num /= 1986882941U;
									num = (558572879U | num);
									List<GClass6.Class739> list2 = this.list_1;
									num >>= 15;
									GClass6.Class739 item = @class;
									num = 1756641416U >> (int)num;
									list2.Add(item);
									num ^= 356441485U;
									goto IL_2D5;
								}
								goto IL_2D5;
								IL_243:
								num -= 167575759U;
								List<GClass6.Class739> list3 = this.list_1;
								int index = num12;
								num += 1599283519U;
								GClass6.Class739 item2 = @class;
								num = 1685274305U + num;
								list3.Insert(index, item2);
								bool flag3 = (num ^ 3116991659U) != 0U;
								num &= 535377331U;
								flag = flag3;
								goto IL_283;
								Block_11:
								num += 390661258U;
								goto IL_283;
							}
							continue;
						}
						IL_2D5:
						if (869805194U < num)
						{
							continue;
						}
						GClass6.Class739 class3 = @class;
						byte byte_ = b;
						num = 363948105U << (int)num;
						class3.method_3(byte_, int_, int_2);
						if (num << 31 != 0U)
						{
							continue;
						}
						return;
						IL_151:
						num ^= 3751747140U;
						goto IL_15B;
						Block_6:
						@class = class2;
						goto IL_15B;
					}
				}
			}
		}

		// Token: 0x060035C4 RID: 13764 RVA: 0x00022880 File Offset: 0x00020A80
		private TypeCode method_10(GClass6.Class709 class709_0, GClass6.Class709 class709_1)
		{
			uint num = 869225266U;
			TypeCode typeCode2;
			TypeCode typeCode3;
			for (;;)
			{
				TypeCode typeCode = class709_0.vmethod_7();
				num <<= 16;
				typeCode2 = typeCode;
				num = 289481049U;
				for (;;)
				{
					IL_19A:
					num >>= 31;
					typeCode3 = class709_1.vmethod_7();
					num |= 887102713U;
					while (typeCode2 != TypeCode.Empty)
					{
						num >>= 18;
						TypeCode typeCode4 = typeCode2;
						uint num2 = num - 3383U;
						num = 616854544U + num;
						num += 270244785U;
						if (typeCode4 == num2)
						{
							break;
						}
						bool flag = typeCode3 != TypeCode.Empty;
						num |= 161303183U;
						num ^= 152912390U;
						if (!flag)
						{
							break;
						}
						TypeCode typeCode5 = typeCode3;
						num &= 1723936951U;
						uint num3 = num - 616566960U;
						num *= 1416170196U;
						if (typeCode5 == num3)
						{
							goto Block_3;
						}
						num *= 1503929163U;
						if (943729166U <= num)
						{
							TypeCode typeCode6 = typeCode2;
							num <<= 21;
							uint num4 = num ^ 1803550730U;
							num = 1051090259U;
							if (typeCode6 == num4)
							{
								goto Block_5;
							}
							TypeCode typeCode7 = typeCode3;
							uint num5 = num ^ 1051090265U;
							num = 652815610U % num;
							if (typeCode7 == num5)
							{
								if (num != 1504642079U)
								{
									TypeCode typeCode8 = typeCode2;
									uint num6 = num + 3642151695U;
									num = (458505624U ^ num);
									if (typeCode8 != num6)
									{
										goto Block_8;
									}
									num >>= 14;
									if (1941445317U >= num)
									{
										return typeCode3;
									}
								}
							}
							else
							{
								num = 2042039586U >> (int)num;
								if ((787642605U ^ num) == 0U)
								{
									goto IL_19A;
								}
								uint num7 = (uint)typeCode2;
								num = (865087097U & num);
								if (num7 == num + 4294967284U)
								{
									goto Block_11;
								}
								uint num8 = (uint)typeCode3;
								num <<= 4;
								if (num8 == (num ^ 396U))
								{
									goto Block_12;
								}
								if (typeCode2 == (TypeCode)(num - 370U))
								{
									goto IL_36B;
								}
								num = 846744509U * num;
								if ((1080716008U & num) != 0U)
								{
									TypeCode typeCode9 = typeCode3;
									num = 21318363U / num;
									uint num9 = num - 4294967282U;
									num |= 1117717926U;
									if (typeCode9 == num9)
									{
										goto Block_15;
									}
									num |= 2039035163U;
									uint num10 = (uint)typeCode2;
									num = 1586635166U % num;
									if (num10 == (num ^ 1586635155U))
									{
										goto IL_35B;
									}
									if (448334015U + num != 0U)
									{
										goto IL_26D;
									}
								}
							}
						}
					}
					goto IL_37B;
				}
				Block_11:
				num = 1790254038U % num;
				uint num11 = (uint)typeCode3;
				num |= 1964008837U;
				if (num11 != (num ^ 1964008846U))
				{
					num = (1453213621U & num);
					TypeCode typeCode10 = typeCode3;
					num += 1844933327U;
					uint num12 = num ^ 3255285855U;
					num &= 674186935U;
					num += 1963550067U;
					if (typeCode10 != num12)
					{
						goto Block_19;
					}
				}
				if (523910707U % num != 0U)
				{
					return typeCode2;
				}
				continue;
				Block_12:
				TypeCode typeCode11 = typeCode2;
				uint num13 = num - 375U;
				num <<= 14;
				if (typeCode11 != num13)
				{
					num = (1862424178U | num);
					if (1242593762U - num == 0U)
					{
						continue;
					}
					TypeCode typeCode12 = typeCode2;
					num &= 395542022U;
					uint num14 = num + 4177373705U;
					num /= 1559391455U;
					num += 6291456U;
					if (typeCode12 != num14)
					{
						goto Block_23;
					}
				}
				num /= 1474002423U;
				if ((num & 1834289469U) == 0U)
				{
					return typeCode2;
				}
				continue;
				IL_26D:
				if (typeCode3 == (TypeCode)(num ^ 1586635155U))
				{
					goto Block_25;
				}
				if ((450702889U & num) != 0U)
				{
					goto IL_315;
				}
			}
			Block_3:
			num ^= 3903692909U;
			goto IL_37B;
			Block_5:
			num = (1816352636U ^ num);
			uint num15 = (uint)typeCode3;
			num %= 1481590574U;
			if (num15 != (num ^ 1390742054U))
			{
				return (TypeCode)(num ^ 1390742063U);
			}
			num *= 699760417U;
			return typeCode2;
			Block_8:
			return (int)num + (TypeCode)(-1035800930);
			Block_15:
			num ^= 1117717542U;
			goto IL_36B;
			Block_19:
			return (TypeCode)(num ^ 1964008839U);
			Block_23:
			num -= 1162038566U;
			return (TypeCode)(num ^ 3139220186U);
			Block_25:
			num ^= 0U;
			goto IL_35B;
			IL_315:
			TypeCode typeCode13 = typeCode2;
			num = 341389951U - num;
			uint num16 = num ^ 3049722090U;
			num -= 470820133U;
			if (typeCode13 != num16)
			{
				uint num17 = (uint)typeCode3;
				num -= 349325210U;
				if (num17 != (num ^ 2229576745U))
				{
					return (TypeCode)(num ^ 2229576747U);
				}
				num += 349325210U;
			}
			return (int)num + (TypeCode)1716065359;
			IL_35B:
			num = 1707165458U - num;
			return (TypeCode)(num - 120530279U);
			IL_36B:
			num = (1680283371U & num);
			return (TypeCode)(num - 114U);
			IL_37B:
			num = 1656109610U >> (int)num;
			return (TypeCode)(num ^ 49U);
		}

		// Token: 0x060035C5 RID: 13765 RVA: 0x00022C20 File Offset: 0x00020E20
		private unsafe GClass6.Class709 method_11(GClass6.Class709 class709_0, GClass6.Class709 class709_1, bool bool_0, bool bool_1)
		{
			uint num = 795963458U;
			double num16;
			double num18;
			int int_;
			GClass6.Class717 class4;
			IntPtr intPtr2;
			long long_;
			long num48;
			for (;;)
			{
				IL_7CE:
				num = 1142755211U >> (int)num;
				num ^= 643648415U;
				TypeCode typeCode = this.method_10(class709_0, class709_1);
				num = 653856778U >> (int)num;
				uint num3;
				uint num4;
				int num6;
				int num8;
				long num14;
				long num15;
				for (;;)
				{
					uint num2 = (uint)typeCode;
					num *= 1944135190U;
					switch (num2 - (num ^ 1944135199U))
					{
					case 0U:
						goto IL_2BB;
					case 1U:
						num = (1743933013U ^ num);
						if (2143710914U < num)
						{
							continue;
						}
						if (bool_1)
						{
							if (num > 2023322918U)
							{
								goto IL_7CE;
							}
							num = 1775044225U / num;
							num3 = class709_0.vmethod_10();
							num -= 765920381U;
							num >>= 30;
							num4 = class709_1.vmethod_10();
							num = 1879525918U >> (int)num;
							if (964439731U == num)
							{
								goto IL_2BB;
							}
							num = 270079715U - num;
							if (bool_0)
							{
								goto IL_3FA;
							}
							if (num * 2110417324U != 0U)
							{
								goto Block_23;
							}
							continue;
						}
						else
						{
							if (num > 1691432776U)
							{
								goto IL_7CE;
							}
							int num5 = class709_0.C38FA951();
							num ^= 1228036751U;
							num6 = num5;
							if (388328309U % num == 0U)
							{
								goto IL_9C8;
							}
							num += 1898143769U;
							int num7 = class709_1.C38FA951();
							num *= 935594388U;
							num8 = num7;
							num = 502223156U * num;
							if (!bool_0)
							{
								goto IL_53A;
							}
							num = 1589317664U << (int)num;
							if ((655110035U & num) != 0U)
							{
								goto IL_364;
							}
							goto IL_519;
						}
						break;
					case 2U:
					{
						if (!bool_1)
						{
							goto IL_8DA;
						}
						num = (1988575485U ^ num);
						if ((1458139035U & num) == 0U)
						{
							continue;
						}
						num = 746735790U % num;
						ulong num9 = class709_0.vmethod_11();
						num = 81948180U - num;
						ulong num10 = num9;
						num = 751963980U - num;
						if (1311900471U - num != 0U)
						{
							goto Block_17;
						}
						continue;
					}
					case 3U:
						num = (2027242758U | num);
						if (bool_1)
						{
							if (2108244066U <= num)
							{
								goto IL_7CE;
							}
							num *= 1696923955U;
							ulong num11 = class709_0.vmethod_11();
							ulong num12 = class709_1.vmethod_11();
							num <<= 29;
							if (1083245887U / num != 0U)
							{
								goto Block_10;
							}
							continue;
						}
						else
						{
							num = (1715278940U & num);
							num = 1294860446U << (int)num;
							long num13 = class709_0.F4599160();
							num |= 1555317061U;
							num14 = num13;
							num15 = class709_1.F4599160();
							num = 1052510557U / num;
							if (867000090U < num)
							{
								goto IL_7CE;
							}
							num = 1999398010U >> (int)num;
							if (!bool_0)
							{
								num = 1042681488U << (int)num;
								if (1068714136U != num)
								{
									goto Block_13;
								}
								continue;
							}
							else
							{
								if (1351502331U <= num)
								{
									goto Block_14;
								}
								continue;
							}
						}
						break;
					case 4U:
						if (num >= 1205732849U)
						{
							goto Block_7;
						}
						continue;
					case 5U:
					{
						num -= 975461705U;
						num = (70854855U | num);
						GClass6.Class709 @class;
						if (!bool_1)
						{
							@class = class709_0;
						}
						else
						{
							num = 1615210178U << (int)num;
							if (399330998U / num == 0U)
							{
								continue;
							}
							num = (292756132U & num);
							@class = class709_0.vmethod_5();
							num ^= 752675023U;
						}
						num16 = @class.A2E5C9EC();
						GClass6.Class709 class2;
						if (!bool_1)
						{
							num = (1293630801U & num);
							if (877741989U < num)
							{
								goto IL_7CE;
							}
							class2 = class709_1;
						}
						else
						{
							class2 = class709_1.vmethod_5();
							num ^= 816103566U;
						}
						num = 2130850882U << (int)num;
						double num17 = class2.A2E5C9EC();
						num = (337529428U & num);
						num18 = num17;
						num |= 1927616997U;
						if (bool_0)
						{
							goto IL_A79;
						}
						num = 1431306941U << (int)num;
						if (num % 1013659555U != 0U)
						{
							goto Block_6;
						}
						continue;
					}
					}
					break;
					IL_364:
					num = 65996942U - num;
					uint num19 = class709_0.vmethod_10();
					num |= 939019980U;
					uint num20 = num19;
					if (1432421313U > num)
					{
						goto IL_9C8;
					}
					uint num21 = class709_1.vmethod_10();
					if (num <= 553874163U)
					{
						continue;
					}
					goto IL_4A0;
					IL_2BB:
					num >>= 23;
					if (num > 1727016501U)
					{
						goto IL_7CE;
					}
					num = 79234684U * num;
					if (!bool_1)
					{
						goto IL_442;
					}
					if (153631317U <= num)
					{
						goto IL_364;
					}
					goto IL_7CE;
				}
				if (num >= 1710171808U)
				{
					goto Block_32;
				}
				continue;
				IL_3FA:
				if (44198911U < num)
				{
					continue;
				}
				int num22 = (int)num3;
				num += 1905151726U;
				int num23 = checked(num22 + (int)num4);
				num += 4148145785U;
				IL_41F:
				num = 397352786U * num;
				int num24 = num23;
				if (1653960445U >> (int)num != 0U)
				{
					goto IL_55E;
				}
				continue;
				Block_23:
				int num25 = (int)num3;
				num = 1793469191U >> (int)num;
				num23 = num25 + (int)num4;
				goto IL_41F;
				IL_442:
				num = (1791640340U | num);
				num = (1500606994U ^ num);
				int num26 = class709_0.C38FA951();
				num <<= 10;
				int num27 = num26;
				num += 30617545U;
				int num28 = class709_1.C38FA951();
				num += 2037211649U;
				int num29 = num28;
				num &= 1770148398U;
				if (1000875532U % num != 0U)
				{
					goto Block_35;
				}
				continue;
				IL_4A0:
				num = (164965856U & num);
				int num30;
				if (!bool_0)
				{
					if (num >= 46149307U)
					{
						goto IL_9C8;
					}
					uint num20;
					uint num21;
					num30 = (int)(num20 + num21);
				}
				else
				{
					if (1940354872U / num == 0U)
					{
						continue;
					}
					uint num20;
					int num31 = (int)num20;
					num = 735804563U % num;
					uint num21;
					int num32 = (int)num21;
					num = 1437036921U << (int)num;
					num30 = checked(num31 + num32);
					num += 1980247264U;
				}
				num = 224530849U >> (int)num;
				int_ = num30;
				if (229649466U > num)
				{
					goto Block_39;
				}
				continue;
				IL_55E:
				TypeCode typeCode2 = class709_0.vmethod_7();
				num = 1891384025U >> (int)num;
				GClass6.Class717 class3;
				if (typeCode2 != typeCode)
				{
					num = 1825513347U / num;
					if (1114990478U >= num)
					{
						goto IL_9C8;
					}
					num = 1930626709U << (int)num;
					class3 = (GClass6.Class717)class709_1;
				}
				else
				{
					class3 = (GClass6.Class717)class709_0;
					num += 2560111783U;
				}
				class4 = class3;
				if (num == 26301860U)
				{
					goto IL_9C8;
				}
				int value = num24;
				num = 1740442434U * num;
				IntPtr intPtr = new IntPtr(value);
				num += 235745089U;
				intPtr2 = intPtr;
				if ((num ^ 719260876U) != 0U)
				{
					goto Block_43;
				}
				continue;
				IL_549:
				num = (778534537U | num);
				int num33;
				num24 = num33;
				num += 943268437U;
				goto IL_55E;
				IL_53A:
				int num34 = num6;
				int num35 = num8;
				num = 1114590702U * num;
				num33 = num34 + num35;
				goto IL_549;
				IL_519:
				int num36 = num6;
				int num37 = num8;
				num = 1323310842U << (int)num;
				num33 = checked(num36 + num37);
				num += 1918690150U;
				goto IL_549;
				Block_17:
				ulong num38 = class709_1.vmethod_11();
				num = (963650722U | num);
				long num41;
				if (!bool_0)
				{
					if (num == 1553869230U)
					{
						continue;
					}
					ulong num10;
					long num39 = (long)num10;
					num = 1205809951U << (int)num;
					long num40 = (long)num38;
					num <<= 28;
					num41 = num39 + num40;
				}
				else
				{
					num = 314967906U + num;
					ulong num10;
					long num42 = (long)num10;
					long num43 = (long)num38;
					num *= 349662503U;
					num41 = checked(num42 + num43);
					num += 1026177936U;
				}
				num %= 1958375469U;
				long_ = num41;
				if (num <= 366575154U)
				{
					goto Block_46;
				}
				continue;
				Block_10:
				num = 1997082735U << (int)num;
				long num46;
				if (!bool_0)
				{
					num = 1982793948U - num;
					ulong num11;
					long num44 = (long)num11;
					num = 1227428497U * num;
					ulong num12;
					long num45 = (long)num12;
					num = 1590525835U / num;
					num46 = num44 + num45;
				}
				else
				{
					num ^= 1795370495U;
					ulong num11;
					long num47 = (long)num11;
					num <<= 25;
					ulong num12;
					num46 = checked(num47 + (long)num12);
					num ^= 536870918U;
				}
				num %= 1608267199U;
				num48 = num46;
				if (num / 1409554915U != 0U)
				{
					continue;
				}
				IL_71C:
				TypeCode typeCode3 = class709_0.vmethod_7();
				num = 1745890827U - num;
				TypeCode typeCode4 = typeCode;
				num *= 1096434876U;
				GClass6.Class717 class5;
				if (typeCode3 != typeCode4)
				{
					class5 = (GClass6.Class717)class709_1;
				}
				else
				{
					class5 = (GClass6.Class717)class709_0;
					num += 0U;
				}
				GClass6.Class717 class6 = class5;
				num = 1996690723U >> (int)num;
				if (num != 1505259363U)
				{
					goto Block_50;
				}
				continue;
				IL_706:
				num <<= 20;
				long num49;
				num48 = num49;
				num += 851443718U;
				goto IL_71C;
				Block_14:
				num49 = checked(num14 + num15);
				num += 1338947674U;
				goto IL_706;
				Block_13:
				long num50 = num14;
				num = 117120212U - num;
				num49 = num50 + num15;
				goto IL_706;
				Block_7:
				GClass6.Class709 class7;
				if (!bool_1)
				{
					num %= 1560494353U;
					class7 = class709_0;
				}
				else
				{
					num %= 1101795984U;
					if (num < 112939484U)
					{
						continue;
					}
					num = 928728667U * num;
					class7 = class709_0.vmethod_5();
					num ^= 749991U;
				}
				float num51 = class7.vmethod_12();
				num /= 896543007U;
				if ((num ^ 1367697405U) != 0U)
				{
					goto IL_9D8;
				}
			}
			Block_6:
			double num52 = num16;
			num = 378810081U / num;
			double double_ = num52 + num18;
			goto IL_AAC;
			Block_32:
			goto IL_9C8;
			Block_35:
			num %= 459029888U;
			int num55;
			if (!bool_0)
			{
				num = 103296415U >> (int)num;
				int num27;
				int num53 = num27;
				num = 1393634986U << (int)num;
				int num29;
				int num54 = num29;
				num = 1508992918U / num;
				num55 = num53 + num54;
			}
			else
			{
				num = 1889035943U - num;
				int num27;
				int num56 = num27;
				int num29;
				int num57 = num29;
				num &= 1797921707U;
				num55 = checked(num56 + num57);
				num += 2648702967U;
			}
			num = (1620202399U & num);
			int_ = num55;
			num += 224530849U;
			Block_39:
			goto IL_87E;
			Block_43:
			num = 827788100U + num;
			void* ptr = intPtr2.ToPointer();
			GClass6.Class717 class8 = class4;
			num |= 925631343U;
			object object_ = Pointer.Box(ptr, class8.method_0());
			num = 1801068434U % num;
			Type type_ = class4.method_0();
			num |= 564216319U;
			return new GClass6.Class717(object_, type_);
			Block_46:
			goto IL_95F;
			Block_50:
			long value2 = num48;
			num = (709644994U & num);
			IntPtr intPtr3 = new IntPtr(value2);
			num = 878055918U << (int)num;
			intPtr2 = intPtr3;
			num = 1967462557U + num;
			if ((1803634902U ^ num) != 0U)
			{
				void* ptr2 = intPtr2.ToPointer();
				GClass6.Class717 class6;
				GClass6.Class717 class9 = class6;
				num = (739119502U | num);
				return new GClass6.Class717(Pointer.Box(ptr2, class9.method_0()), class6.method_0());
			}
			goto IL_9C8;
			IL_87E:
			if (num <= 300905852U)
			{
				return new GClass6.Class711(int_);
			}
			goto IL_9C8;
			IL_8DA:
			num = 1925394724U >> (int)num;
			long num58 = class709_0.F4599160();
			num = 945581180U / num;
			long num59 = num58;
			num = 840460549U / num;
			long num60 = class709_1.F4599160();
			if (374345931U < num)
			{
				goto IL_9C8;
			}
			long num61;
			if (!bool_0)
			{
				if (num == 1512115196U)
				{
					goto IL_9C8;
				}
				num61 = num59 + num60;
			}
			else
			{
				long num62 = num59;
				num ^= 615320821U;
				num61 = checked(num62 + num60);
				num ^= 615320821U;
			}
			num = (2068517651U ^ num);
			long_ = num61;
			num += 2226449788U;
			IL_95F:
			return new GClass6.Class712(long_);
			IL_9C8:
			num = (1274511052U & num);
			throw new InvalidOperationException();
			IL_9D8:
			num = 7416115U * num;
			GClass6.Class709 class10;
			if (!bool_1)
			{
				class10 = class709_1;
			}
			else
			{
				class10 = class709_1.vmethod_5();
				num ^= 0U;
			}
			num = 1459692989U - num;
			float num63 = class10.vmethod_12();
			num <<= 17;
			float num64 = num63;
			num >>= 1;
			float float_;
			if (!bool_0)
			{
				float num51;
				float num65 = num51;
				num = 885728159U >> (int)num;
				float num66 = num64;
				num = (1163208895U | num);
				float_ = num65 + num66;
			}
			else
			{
				float num51;
				float num67 = num51;
				num %= 294477229U;
				float_ = num67 + num64;
				num ^= 2004709145U;
			}
			num <<= 18;
			return new GClass6.Class713(float_);
			IL_A79:
			num = (1817389631U & num);
			double num68 = num16;
			num = 1660954254U >> (int)num;
			double num69 = num18;
			num <<= 14;
			double_ = num68 + num69;
			num ^= 5046272U;
			IL_AAC:
			num >>= 22;
			return new GClass6.Class714(double_);
		}

		// Token: 0x060035C6 RID: 13766 RVA: 0x000236E8 File Offset: 0x000218E8
		private unsafe GClass6.Class709 method_12(GClass6.Class709 class709_0, GClass6.Class709 class709_1, bool bool_0, bool bool_1)
		{
			uint num = 2009350063U;
			float num21;
			float num22;
			double num26;
			double num27;
			int value;
			GClass6.Class717 class6;
			long num39;
			long num41;
			GClass6.Class717 class7;
			IntPtr intPtr2;
			int num43;
			int num45;
			int num53;
			for (;;)
			{
				IL_822:
				TypeCode typeCode = this.method_10(class709_0, class709_1);
				num ^= 983374826U;
				TypeCode typeCode2 = typeCode;
				num += 1493706344U;
				if (1527400184U >> (int)num == 0U)
				{
					goto IL_57E;
				}
				int num3;
				int num4;
				long num13;
				for (;;)
				{
					IL_2D4:
					TypeCode typeCode3 = typeCode2;
					num &= 1220162574U;
					uint num2 = num ^ 2097157U;
					num = (364410126U | num);
					switch (typeCode3 - num2)
					{
					case 0:
						goto IL_57E;
					case 1:
						num += 253906183U;
						if (51987689U >= num)
						{
							continue;
						}
						num = 967468174U / num;
						if (bool_1)
						{
							goto IL_5ED;
						}
						num = (462057794U | num);
						num3 = class709_0.C38FA951();
						num4 = class709_1.C38FA951();
						if (!bool_0)
						{
							goto IL_5B9;
						}
						if (num + 46140705U == 0U)
						{
							continue;
						}
						goto IL_59F;
					case 2:
					{
						num /= 1220477044U;
						if ((1816922882U ^ num) == 0U)
						{
							goto IL_822;
						}
						if (!bool_1)
						{
							goto IL_725;
						}
						if ((num ^ 878599950U) == 0U)
						{
							continue;
						}
						num = (774124353U & num);
						ulong num5 = class709_0.vmethod_11();
						num %= 44117250U;
						ulong num6 = num5;
						if (num <= 1023544939U)
						{
							goto Block_20;
						}
						continue;
					}
					case 3:
						num = 775515230U >> (int)num;
						if (bool_1)
						{
							num <<= 1;
							num = 39667128U + num;
							ulong num7 = class709_0.vmethod_11();
							num = 354232986U >> (int)num;
							ulong num8 = class709_1.vmethod_11();
							num = (717509760U & num);
							ulong num9 = num8;
							num = (297022504U ^ num);
							long num12;
							if (!bool_0)
							{
								long num10 = (long)num7;
								long num11 = (long)num9;
								num *= 731273175U;
								num12 = num10 - num11;
							}
							else
							{
								num12 = (long)(checked(num7 - num9));
								num ^= 1257727408U;
							}
							num13 = num12;
							if (num < 515117418U)
							{
								goto IL_822;
							}
						}
						else
						{
							num = 793472597U >> (int)num;
							long num14 = class709_0.F4599160();
							num /= 2023898483U;
							long num15 = num14;
							if ((num ^ 2006603661U) == 0U)
							{
								goto IL_822;
							}
							long num16 = class709_1.F4599160();
							if (num > 1398744036U)
							{
								break;
							}
							long num19;
							if (!bool_0)
							{
								num = (623934084U & num);
								long num17 = num15;
								long num18 = num16;
								num = (1706440066U ^ num);
								num19 = num17 - num18;
							}
							else
							{
								num = 1051530006U - num;
								long num20 = num15;
								num >>= 20;
								num19 = checked(num20 - num16);
								num ^= 1706440296U;
							}
							num <<= 13;
							num13 = num19;
							num += 2488717592U;
						}
						if (num + 1162241751U == 0U)
						{
							goto IL_57E;
						}
						if (class709_0.vmethod_7() != typeCode2)
						{
							goto Block_15;
						}
						if (830014097U != num)
						{
							goto Block_16;
						}
						continue;
					case 4:
					{
						GClass6.Class709 @class;
						if (!bool_1)
						{
							@class = class709_0;
						}
						else
						{
							num <<= 0;
							num ^= 1704083276U;
							@class = class709_0.vmethod_5();
							num ^= 1704083276U;
						}
						num *= 35602828U;
						num21 = @class.vmethod_12();
						if (1302482371U - num == 0U)
						{
							goto IL_39A;
						}
						num %= 1330860439U;
						GClass6.Class709 class2;
						if (!bool_1)
						{
							if (num >> 20 == 0U)
							{
								goto IL_822;
							}
							class2 = class709_1;
						}
						else
						{
							num |= 2063469851U;
							class2 = class709_1.vmethod_5();
							num ^= 1789788440U;
						}
						num22 = class2.vmethod_12();
						num += 859710478U;
						if (num < 1921778005U)
						{
							goto Block_7;
						}
						continue;
					}
					case 5:
						goto IL_3C7;
					}
					goto Block_24;
				}
				Block_20:
				num >>= 23;
				ulong num23 = class709_1.vmethod_11();
				num |= 1941529033U;
				ulong num24 = num23;
				num = 1288196461U / num;
				if ((num & 1665934632U) == 0U)
				{
					goto Block_44;
				}
				continue;
				IL_3C7:
				num *= 1044715113U;
				if (num <= 261230540U)
				{
					break;
				}
				GClass6.Class709 class3;
				if (!bool_1)
				{
					num -= 1061562819U;
					class3 = class709_0;
				}
				else
				{
					num = 634934663U % num;
					class3 = class709_0.vmethod_5();
					num += 1314830196U;
				}
				double num25 = class3.A2E5C9EC();
				num = 1618430485U % num;
				num26 = num25;
				num |= 1173782059U;
				GClass6.Class709 class4;
				if (!bool_1)
				{
					num >>= 26;
					class4 = class709_1;
				}
				else
				{
					num /= 1190666782U;
					if ((num ^ 1466043117U) == 0U)
					{
						continue;
					}
					num &= 448093840U;
					class4 = class709_1.vmethod_5();
					num ^= 25U;
				}
				num = 749928654U + num;
				num27 = class4.A2E5C9EC();
				num -= 110570236U;
				if (757494818U - num == 0U)
				{
					goto IL_48D;
				}
				if (bool_0)
				{
					goto IL_9B2;
				}
				num *= 23724043U;
				if (num < 1529685957U)
				{
					goto Block_53;
				}
				continue;
				IL_5B9:
				num = (461518297U | num);
				int num28;
				if (634151368U > num)
				{
					num28 = num3 - num4;
					goto IL_5D3;
				}
				continue;
				IL_5ED:
				num += 1475040843U;
				if (1203985831U * num == 0U)
				{
					continue;
				}
				uint num29 = class709_0.vmethod_10();
				num = 372516695U + num;
				uint num30 = num29;
				num = 1664904508U % num;
				num = 1413108114U - num;
				uint num31 = class709_1.vmethod_10();
				num >>= 9;
				uint num32 = num31;
				num = 1208972722U * num;
				if (num < 401234935U)
				{
					break;
				}
				int num35;
				if (!bool_0)
				{
					num = (1837590000U ^ num);
					int num33 = (int)num30;
					int num34 = (int)num32;
					num = 345859657U + num;
					num35 = num33 - num34;
				}
				else
				{
					if (num < 252783023U)
					{
						continue;
					}
					int num36 = (int)num30;
					num &= 476927984U;
					num35 = checked(num36 - (int)num32);
					num += 2561854397U;
				}
				value = num35;
				IL_696:
				num ^= 258424600U;
				GClass6.Class717 class5;
				if (class709_0.vmethod_7() != typeCode2)
				{
					num = (1733518553U & num);
					class5 = (GClass6.Class717)class709_1;
				}
				else
				{
					if ((62873318U ^ num) == 0U)
					{
						continue;
					}
					class5 = (GClass6.Class717)class709_0;
					num += 1876157916U;
				}
				class6 = class5;
				if (num << 4 != 0U)
				{
					goto Block_43;
				}
				continue;
				IL_5D3:
				num >>= 3;
				value = num28;
				num += 2578043714U;
				goto IL_696;
				IL_59F:
				int num37 = num3;
				num |= 817449087U;
				num28 = checked(num37 - num4);
				num += 3754884188U;
				goto IL_5D3;
				IL_725:
				if (num << 7 != 0U)
				{
					continue;
				}
				long num38 = class709_0.F4599160();
				num = 1360137546U * num;
				num39 = num38;
				if (1078412369U * num != 0U)
				{
					break;
				}
				long num40 = class709_1.F4599160();
				num = (330851681U | num);
				num41 = num40;
				if (1064387684U - num == 0U)
				{
					continue;
				}
				num <<= 29;
				if (!bool_0)
				{
					goto IL_8DC;
				}
				num *= 659098016U;
				if (num != 1917352812U)
				{
					goto Block_49;
				}
				continue;
				Block_7:
				num = 1687429136U << (int)num;
				if (bool_0)
				{
					goto IL_978;
				}
				num = 1001916114U / num;
				if ((num & 2045192805U) == 0U)
				{
					goto Block_51;
				}
				continue;
				IL_4F0:
				num -= 728245792U;
				GClass6.Class717 class8;
				class7 = class8;
				num = 1892110422U - num;
				long value2 = num13;
				num |= 1263092024U;
				IntPtr intPtr = new IntPtr(value2);
				num = 654537988U + num;
				intPtr2 = intPtr;
				num = 2063603484U % num;
				if (1510694038U >> (int)num != 0U)
				{
					goto Block_34;
				}
				goto IL_57E;
				Block_16:
				num += 1636973961U;
				class8 = (GClass6.Class717)class709_0;
				num ^= 48449314U;
				goto IL_4F0;
				Block_15:
				num = 427891355U - num;
				class8 = (GClass6.Class717)class709_1;
				goto IL_4F0;
				for (;;)
				{
					IL_57E:
					num = 1431702928U >> (int)num;
					if (1484484001U <= num)
					{
						goto IL_A4A;
					}
					num = 448791977U - num;
					if (bool_1)
					{
						break;
					}
					num = 875250471U - num;
					num >>= 5;
					int num42 = class709_0.C38FA951();
					num = 1824330894U * num;
					num43 = num42;
					num ^= 106586963U;
					if (2090681583U / num == 0U)
					{
						goto IL_7E8;
					}
				}
				num /= 1167607632U;
				if (1761681736U >= num)
				{
					goto IL_48D;
				}
				continue;
				IL_7E8:
				int num44 = class709_1.C38FA951();
				num &= 1375355844U;
				num45 = num44;
				if (!bool_0)
				{
					if (1128673621U < num)
					{
						goto Block_55;
					}
					continue;
				}
				else
				{
					num %= 1720395513U;
					if (num % 2051762214U == 0U)
					{
						continue;
					}
					goto IL_A10;
				}
				IL_48D:
				num -= 895240577U;
				uint num46 = class709_0.vmethod_10();
				num ^= 95446341U;
				uint num47 = num46;
				num &= 974473017U;
				if (1796892721U < num)
				{
					continue;
				}
				IL_39A:
				num -= 785866831U;
				uint num48 = class709_1.vmethod_10();
				num ^= 1032012251U;
				if (num < 263079202U)
				{
					goto IL_2D4;
				}
				num = 1105143531U - num;
				int num50;
				if (!bool_0)
				{
					num /= 648759939U;
					if (num % 970870174U == 0U)
					{
						break;
					}
					int num49 = (int)num47;
					num >>= 21;
					num50 = num49 - (int)num48;
				}
				else
				{
					num += 2145129768U;
					int num51 = (int)num47;
					num *= 1222213159U;
					int num52 = (int)num48;
					num += 970555313U;
					num50 = checked(num51 - num52);
					num += 1058502664U;
				}
				num = 1173839182U >> (int)num;
				num53 = num50;
				if (num >> 21 != 0U)
				{
					goto Block_27;
				}
				goto IL_2D4;
			}
			Block_24:
			goto IL_A4A;
			Block_27:
			goto IL_A50;
			Block_34:
			num /= 778770720U;
			void* ptr = intPtr2.ToPointer();
			num &= 77166645U;
			Type type = class7.method_0();
			num = (903773351U | num);
			object object_ = Pointer.Box(ptr, type);
			num *= 1997084690U;
			return new GClass6.Class717(object_, class7.method_0());
			Block_43:
			intPtr2 = new IntPtr(value);
			num = 1948409711U + num;
			void* ptr2 = intPtr2.ToPointer();
			Type type2 = class6.method_0();
			num %= 1323842323U;
			object object_2 = Pointer.Box(ptr2, type2);
			Type type_ = class6.method_0();
			num |= 906763166U;
			return new GClass6.Class717(object_2, type_);
			Block_44:
			long num54;
			if (!bool_0)
			{
				ulong num6;
				ulong num24;
				num54 = (long)(num6 - num24);
			}
			else
			{
				ulong num6;
				ulong num24;
				num54 = (long)(checked(num6 - num24));
				num += 0U;
			}
			long long_ = num54;
			goto IL_8F7;
			Block_49:
			long num55 = num39;
			num = 1992586506U << (int)num;
			long num56 = checked(num55 - num41);
			num ^= 1532577102U;
			goto IL_8EB;
			Block_51:
			float num57 = num21;
			num &= 1414341673U;
			float num58 = num22;
			num = 1212618427U >> (int)num;
			float float_ = num57 - num58;
			goto IL_991;
			Block_53:
			double num59 = num26;
			num = (775160144U | num);
			double num60 = num27;
			num = (942345096U ^ num);
			double double_ = num59 - num60;
			goto IL_9E5;
			Block_55:
			int num61 = num43;
			num >>= 3;
			int num62 = num45;
			num += 1035158953U;
			int num63 = num61 - num62;
			goto IL_A33;
			IL_8DC:
			long num64 = num39;
			num |= 228405316U;
			num56 = num64 - num41;
			IL_8EB:
			long_ = num56;
			num += 3529691068U;
			IL_8F7:
			num = 852065039U >> (int)num;
			return new GClass6.Class712(long_);
			IL_978:
			float num65 = num21;
			num = 1431835783U - num;
			float_ = num65 - num22;
			num ^= 1568604732U;
			IL_991:
			return new GClass6.Class713(float_);
			IL_9B2:
			num &= 1990683127U;
			double num66 = num26;
			num = 1784166391U + num;
			double num67 = num27;
			num = 2012764524U >> (int)num;
			double_ = num66 - num67;
			num ^= 396311244U;
			IL_9E5:
			num %= 790256221U;
			return new GClass6.Class714(double_);
			IL_A10:
			int num68 = num43;
			num = 382021484U / num;
			int num69 = num45;
			num = (2063211903U & num);
			num63 = checked(num68 - num69);
			num ^= 1205423537U;
			IL_A33:
			num = 768031615U - num;
			num53 = num63;
			num += 1611231104U;
			goto IL_A50;
			IL_A4A:
			throw new InvalidOperationException();
			IL_A50:
			num = 1626284633U + num;
			int int_ = num53;
			num = 1532889645U >> (int)num;
			return new GClass6.Class711(int_);
		}

		// Token: 0x060035C7 RID: 13767 RVA: 0x00024168 File Offset: 0x00022368
		private GClass6.Class709 method_13(GClass6.Class709 class709_0, GClass6.Class709 class709_1, bool bool_0, bool bool_1)
		{
			uint num = 11887990U;
			float num6;
			float num7;
			double num8;
			uint num10;
			uint num12;
			int num13;
			int num15;
			long long_;
			double num27;
			for (;;)
			{
				IL_49E:
				num &= 50545152U;
				num = 2062881803U * num;
				TypeCode typeCode = this.method_10(class709_0, class709_1);
				num = 10059041U >> (int)num;
				TypeCode typeCode2 = typeCode;
				num &= 373183427U;
				ulong num3;
				ulong num4;
				for (;;)
				{
					int num2 = typeCode2 - (TypeCode)(num - 1593592U);
					num %= 1832268041U;
					switch (num2)
					{
					case 0:
						goto IL_239;
					case 1:
					case 3:
						break;
					case 2:
						num = (617881616U ^ num);
						if ((num ^ 697855987U) == 0U)
						{
							goto IL_49E;
						}
						num -= 497813174U;
						if (!bool_1)
						{
							goto IL_382;
						}
						num = (679888199U ^ num);
						if (num == 472333078U)
						{
							goto IL_49E;
						}
						num3 = class709_0.vmethod_11();
						num *= 203705882U;
						num = 748243086U * num;
						num4 = class709_1.vmethod_11();
						num &= 458518124U;
						num %= 1720140194U;
						if (!bool_0)
						{
							goto IL_36B;
						}
						num = 1231770376U << (int)num;
						if (1901537092U != num)
						{
							goto Block_17;
						}
						continue;
					case 4:
					{
						if (180646235U == num)
						{
							continue;
						}
						GClass6.Class709 @class;
						if (!bool_1)
						{
							num %= 1853500530U;
							@class = class709_0;
						}
						else
						{
							@class = class709_0.vmethod_5();
							num += 0U;
						}
						float num5 = @class.vmethod_12();
						num -= 1332044611U;
						num6 = num5;
						num = 1207774188U / num;
						num = 1663006458U - num;
						GClass6.Class709 class2;
						if (!bool_1)
						{
							num += 23349541U;
							if (107424631U > num)
							{
								break;
							}
							class2 = class709_1;
						}
						else
						{
							num += 1522994320U;
							class2 = class709_1.vmethod_5();
							num += 2795322517U;
						}
						num -= 1026451164U;
						num7 = class2.vmethod_12();
						if (num >= 1526603709U)
						{
							goto IL_49E;
						}
						if (!bool_0)
						{
							goto IL_428;
						}
						num /= 1116412353U;
						if (1181573873U > num)
						{
							goto IL_589;
						}
						break;
					}
					case 5:
					{
						num <<= 30;
						GClass6.Class709 class3;
						if (!bool_1)
						{
							class3 = class709_0;
						}
						else
						{
							if (num << 13 != 0U)
							{
								continue;
							}
							num *= 790433123U;
							class3 = class709_0.vmethod_5();
							num += 2147483648U;
						}
						num = 645874222U - num;
						num8 = class3.A2E5C9EC();
						num = 72433612U % num;
						if (num - 121204503U == 0U)
						{
							goto IL_49E;
						}
						if (!bool_1)
						{
							goto IL_452;
						}
						num %= 572806976U;
						if (num < 2124503347U)
						{
							goto Block_5;
						}
						continue;
					}
					default:
						num += 0U;
						break;
					}
					if (num + 1630305958U != 0U)
					{
						goto Block_12;
					}
				}
				IL_239:
				num = 660737761U / num;
				if (bool_1)
				{
					num = (1313540579U ^ num);
					uint num9 = class709_0.vmethod_10();
					num = (1932687743U ^ num);
					num10 = num9;
					num = (1189694545U ^ num);
					uint num11 = class709_1.vmethod_10();
					num ^= 451814132U;
					num12 = num11;
					num %= 158295795U;
					if (num + 2039508292U == 0U)
					{
						continue;
					}
				}
				else
				{
					num = 733941958U * num;
					if (num == 1148390081U)
					{
						continue;
					}
					num13 = class709_0.C38FA951();
					if ((num ^ 528158198U) == 0U)
					{
						continue;
					}
					num <<= 12;
					int num14 = class709_1.C38FA951();
					num &= 1292247772U;
					num15 = num14;
					num = (674452266U ^ num);
					if (num * 1950957452U != 0U)
					{
						if (!bool_0)
						{
							goto IL_4FF;
						}
						num += 2092717179U;
						if (1647055268U / num == 0U)
						{
							goto Block_25;
						}
						continue;
					}
				}
				num >>= 6;
				if (!bool_0)
				{
					goto IL_554;
				}
				num %= 1381381709U;
				if (num + 546312499U != 0U)
				{
					goto Block_27;
				}
				continue;
				IL_36F:
				long num16;
				long_ = num16;
				if (2140361740U >= num)
				{
					goto IL_409;
				}
				continue;
				IL_36B:
				num16 = (long)(num3 * num4);
				goto IL_36F;
				Block_17:
				long num17 = (long)num3;
				num %= 51469700U;
				long num18 = (long)num4;
				num = 287396833U >> (int)num;
				num16 = checked(num17 * num18);
				num += 290386987U;
				goto IL_36F;
				IL_409:
				num %= 19729575U;
				if (1237582250U >> (int)num != 0U)
				{
					goto Block_30;
				}
				continue;
				IL_382:
				num %= 1795050181U;
				long num19 = class709_0.F4599160();
				long num20 = class709_1.F4599160();
				num = 2097371135U << (int)num;
				num >>= 17;
				long num23;
				if (!bool_0)
				{
					long num21 = num19;
					num *= 1766921599U;
					long num22 = num20;
					num = 1270353551U * num;
					num23 = num21 * num22;
				}
				else
				{
					long num24 = num19;
					num = 947216787U >> (int)num;
					long num25 = num20;
					num /= 2010598910U;
					num23 = checked(num24 * num25);
					num ^= 4196973568U;
				}
				long_ = num23;
				num ^= 3950557760U;
				goto IL_409;
				IL_428:
				num = 1038188522U - num;
				if (897341061U != num)
				{
					goto Block_31;
				}
				continue;
				IL_452:
				if (num >> 21 == 0U)
				{
					continue;
				}
				GClass6.Class709 class4 = class709_1;
				IL_45D:
				double num26 = class4.A2E5C9EC();
				num %= 1616196271U;
				num27 = num26;
				num >>= 28;
				if (!bool_0)
				{
					if (1595823142 << (int)num != 0)
					{
						goto Block_34;
					}
					continue;
				}
				else
				{
					if (num != 914497434U)
					{
						goto Block_35;
					}
					continue;
				}
				Block_5:
				class4 = class709_1.vmethod_5();
				num += 0U;
				goto IL_45D;
			}
			Block_12:
			throw new InvalidOperationException();
			Block_25:
			int num28 = num13;
			num *= 864755586U;
			int num29 = num15;
			num = (344288441U ^ num);
			int num30 = checked(num28 * num29);
			num += 3177136779U;
			goto IL_518;
			Block_27:
			int num31 = (int)num10;
			num = 650989869U / num;
			int num32 = (int)num12;
			num = 434064151U * num;
			int num33 = checked(num31 * num32);
			num += 2575396572U;
			goto IL_573;
			Block_30:
			return new GClass6.Class712(long_);
			Block_31:
			double num34 = (double)num6;
			double num35 = (double)num7;
			num >>= 29;
			double num36 = num34 * num35;
			goto IL_5BD;
			Block_34:
			double num37 = num8;
			num &= 1109075537U;
			double double_ = num37 * num27;
			goto IL_5EC;
			Block_35:
			double_ = num8 * num27;
			num ^= 0U;
			goto IL_5EC;
			IL_4FF:
			int num38 = num13;
			num -= 1998875619U;
			int num39 = num15;
			num ^= 1199134137U;
			num30 = num38 * num39;
			IL_518:
			num = (1103626465U ^ num);
			int int_ = num30;
			num ^= 2267977161U;
			goto IL_574;
			IL_554:
			num /= 953176075U;
			int num40 = (int)num10;
			int num41 = (int)num12;
			num = 1888692182U >> (int)num;
			num33 = num40 * num41;
			IL_573:
			int_ = num33;
			IL_574:
			return new GClass6.Class711(int_);
			IL_589:
			double num42 = (double)num6;
			num /= 1448901660U;
			double num43 = (double)num7;
			num &= 545276123U;
			num36 = num42 * num43;
			num += 0U;
			IL_5BD:
			double double_2 = num36;
			num /= 1301677404U;
			return new GClass6.Class714(double_2);
			IL_5EC:
			return new GClass6.Class714(double_);
		}

		// Token: 0x060035C8 RID: 13768 RVA: 0x00024768 File Offset: 0x00022968
		private GClass6.Class709 method_14(GClass6.Class709 class709_0, GClass6.Class709 class709_1, bool bool_0)
		{
			uint num = 300835026U;
			int int_;
			for (;;)
			{
				num *= 1797400274U;
				num = 598480256U >> (int)num;
				TypeCode typeCode = this.method_10(class709_0, class709_1);
				num /= 1980521243U;
				object obj = typeCode;
				object obj2 = num ^ 9U;
				num = 243466800U >> (int)num;
				object obj3 = obj - obj2;
				num = 929237325U / num;
				switch (obj3)
				{
				case 0:
					if (1169849124U % num != 0U)
					{
						continue;
					}
					num = (600713773U & num);
					if (bool_0)
					{
						num = (1187341832U & num);
						int num2 = (int)class709_0.vmethod_10();
						num = 96429701U + num;
						num = 879822879U + num;
						uint num3 = class709_1.vmethod_10();
						num = 102131003U * num;
						int num4 = num2 / (int)num3;
						num >>= 9;
						int_ = num4;
						if (1109068418U - num == 0U)
						{
							continue;
						}
					}
					else
					{
						num = 1102006800U * num;
						int num5 = class709_0.C38FA951();
						num = 1484678441U + num;
						int num6 = class709_1.C38FA951();
						num = (88875788U | num);
						int num7 = num5 / num6;
						num /= 1028935131U;
						int_ = num7;
						num += 7304215U;
					}
					break;
				case 1:
				case 3:
					goto IL_58;
				case 2:
					num /= 1430275357U;
					if (bool_0)
					{
						goto IL_1F0;
					}
					if (916986396U > num)
					{
						goto Block_7;
					}
					goto IL_58;
				case 4:
					if (1945853287U != num)
					{
						goto Block_3;
					}
					break;
				case 5:
					num = 637871375U - num;
					if (bool_0)
					{
						goto IL_2E0;
					}
					num -= 1941665478U;
					if (num * 1249517747U != 0U)
					{
						goto Block_2;
					}
					continue;
				default:
					num += 0U;
					goto IL_58;
				}
				num = 421146919U + num;
				if (1970958392U % num != 0U)
				{
					goto Block_4;
				}
				continue;
				IL_58:
				num -= 1456152012U;
				if ((num & 849284075U) != 0U)
				{
					goto Block_5;
				}
			}
			Block_2:
			GClass6.Class709 @class = class709_0;
			goto IL_2EE;
			Block_3:
			GClass6.Class709 class2;
			if (!bool_0)
			{
				class2 = class709_0;
			}
			else
			{
				num &= 953878532U;
				num %= 359535668U;
				class2 = class709_0.vmethod_5();
				num ^= 3U;
			}
			num ^= 655124369U;
			float num8 = class2.vmethod_12();
			num = 429003113U + num;
			GClass6.Class709 class3;
			if (!bool_0)
			{
				num /= 1304389767U;
				class3 = class709_1;
			}
			else
			{
				num /= 172821265U;
				num *= 715994442U;
				class3 = class709_1.vmethod_5();
				num += 4293967940U;
			}
			num ^= 1919099893U;
			float num9 = class3.vmethod_12();
			num &= 49163812U;
			float num10 = num9;
			float float_ = num8 / num10;
			num = (1001087239U ^ num);
			return new GClass6.Class713(float_);
			Block_4:
			return new GClass6.Class711(int_);
			Block_5:
			throw new InvalidOperationException();
			Block_7:
			long num11 = class709_0.F4599160();
			num /= 818768383U;
			num |= 1046161011U;
			long num12 = class709_1.F4599160();
			num = 1282568114U - num;
			long num13 = num12;
			num += 1023435377U;
			long num14 = num13;
			num = (414660374U ^ num);
			long num15 = num11 / num14;
			num = 1838032448U << (int)num;
			long long_ = num15;
			num += 2232473631U;
			goto IL_230;
			IL_1F0:
			num = 1937787984U >> (int)num;
			num |= 626475083U;
			long num16 = (long)class709_0.vmethod_11();
			num ^= 1902079209U;
			ulong num17 = class709_1.vmethod_11();
			num >>= 13;
			ulong num18 = num17;
			num *= 1647707231U;
			long_ = num16 / (long)num18;
			IL_230:
			num = 490558693U << (int)num;
			return new GClass6.Class712(long_);
			IL_2E0:
			@class = class709_0.vmethod_5();
			num += 2353301818U;
			IL_2EE:
			num = 1976990506U / num;
			double num19 = @class.A2E5C9EC();
			num ^= 455414082U;
			GClass6.Class709 class4;
			if (!bool_0)
			{
				class4 = class709_1;
			}
			else
			{
				num %= 239423650U;
				num = 1033381481U % num;
				class4 = class709_1.vmethod_5();
				num ^= 289157803U;
			}
			num = 1090221002U % num;
			double num20 = class4.A2E5C9EC();
			double double_ = num19 / num20;
			num += 1156844766U;
			return new GClass6.Class714(double_);
		}

		// Token: 0x060035C9 RID: 13769 RVA: 0x00024ABC File Offset: 0x00022CBC
		private GClass6.Class709 method_15(GClass6.Class709 class709_0, GClass6.Class709 class709_1, bool bool_0)
		{
			uint num;
			for (;;)
			{
				num = 747132478U;
				TypeCode typeCode = class709_0.vmethod_7();
				for (;;)
				{
					TypeCode typeCode2 = typeCode;
					uint num2 = num + 3547834827U;
					num *= 1110914914U;
					if (typeCode2 == num2)
					{
						goto IL_23;
					}
					num -= 662719045U;
					IL_0F:
					if (typeCode != (TypeCode)(num - 2995391340U))
					{
						num = (716969150U | num);
						if (641956374U < num)
						{
							goto Block_2;
						}
						continue;
					}
					else if (279326556U <= num)
					{
						num += 113192126U;
						if (!bool_0)
						{
							goto IL_91;
						}
						if (num / 2083878938U != 0U)
						{
							goto Block_4;
						}
						continue;
					}
					IL_23:
					if (num != 755661411U)
					{
						break;
					}
					goto IL_0F;
				}
				if (!bool_0)
				{
					goto IL_E9;
				}
				num = 1737511309U % num;
				if (num != 1111179851U)
				{
					goto Block_7;
				}
				continue;
				IL_91:
				num = (935214286U | num);
				if (num - 394031107U != 0U)
				{
					goto Block_8;
				}
			}
			Block_2:
			throw new InvalidOperationException();
			Block_4:
			long num3 = (long)class709_0.vmethod_11();
			num *= 1727625336U;
			ulong num4 = class709_1.vmethod_11();
			long num5 = (long)num4;
			num <<= 27;
			long long_ = num3 % num5;
			num ^= 928459948U;
			return new GClass6.Class712(long_);
			Block_7:
			int num6 = (int)class709_0.vmethod_10();
			num = 102658335U * num;
			num = 661093745U * num;
			uint num7 = class709_1.vmethod_10();
			num |= 1779967038U;
			uint num8 = num7;
			num &= 1010327538U;
			int num9 = (int)num8;
			num &= 1986214714U;
			return new GClass6.Class711(num6 % num9);
			Block_8:
			num = (1807644274U & num);
			long num10 = class709_0.F4599160();
			num = (1799633040U | num);
			num = (1216312212U & num);
			long num11 = class709_1.F4599160();
			num <<= 23;
			long num12 = num11;
			return new GClass6.Class712(num10 % num12);
			IL_E9:
			num = 1294480280U * num;
			int num13 = class709_0.C38FA951();
			int num14 = class709_1.C38FA951();
			num = 695952412U * num;
			int num15 = num14;
			num -= 2044422190U;
			int num16 = num15;
			num = 563681339U / num;
			int int_ = num13 % num16;
			num = 346708194U << (int)num;
			return new GClass6.Class711(int_);
		}

		// Token: 0x060035CA RID: 13770 RVA: 0x00024C6C File Offset: 0x00022E6C
		private GClass6.Class709 method_16(GClass6.Class709 class709_0, GClass6.Class709 class709_1)
		{
			uint num = 1664184194U;
			for (;;)
			{
				num |= 2021746468U;
				num = (1340940607U & num);
				num -= 500452943U;
				TypeCode typeCode = this.method_10(class709_0, class709_1);
				num = 1566785352U - num;
				TypeCode typeCode2 = typeCode;
				num = (948848850U ^ num);
				for (;;)
				{
					TypeCode typeCode3 = typeCode2;
					num += 1720461258U;
					uint num2 = num ^ 2107919460U;
					num = 441857472U << (int)num;
					switch (typeCode3 - num2)
					{
					case 0:
						num = 895696641U / num;
						if (627640955U > num)
						{
							goto Block_1;
						}
						break;
					case 1:
					case 3:
						goto IL_149;
					case 2:
						goto IL_FE;
					case 4:
						goto IL_69;
					case 5:
						goto IL_7F;
					default:
						if (1059395247U <= num)
						{
							goto IL_C1;
						}
						break;
					}
				}
				IL_69:
				num = (173830802U | num);
				if (num % 1211238508U != 0U)
				{
					goto Block_3;
				}
				continue;
				IL_7F:
				if (929834561U % num != 0U)
				{
					goto IL_172;
				}
			}
			Block_1:
			num = 193083761U << (int)num;
			int num3 = class709_0.C38FA951();
			num = 112788009U + num;
			int num4 = class709_1.C38FA951();
			int int_ = num3 ^ num4;
			num += 362113199U;
			return new GClass6.Class711(int_);
			Block_3:
			float float_;
			if (IntPtr.Size != (int)(num ^ 3472650902U))
			{
				num &= 412710628U;
				if (174357729U * num == 0U)
				{
					goto IL_149;
				}
				float_ = 0f;
			}
			else
			{
				float_ = float.NaN;
				num += 966524910U;
			}
			num = (577766877U | num);
			return new GClass6.Class713(float_);
			IL_C1:
			num ^= 0U;
			goto IL_149;
			IL_FE:
			num |= 193817610U;
			long num5 = class709_0.F4599160();
			long num6 = class709_1.F4599160();
			long long_ = num5 ^ num6;
			num %= 2007647284U;
			return new GClass6.Class712(long_);
			IL_149:
			num -= 2088241297U;
			throw new InvalidOperationException();
			IL_172:
			double double_;
			if (IntPtr.Size != (int)(num + 961019908U))
			{
				num |= 1972193189U;
				double_ = 0.0;
			}
			else
			{
				double_ = double.NaN;
				num ^= 822429605U;
			}
			num = 1742937442U % num;
			return new GClass6.Class714(double_);
		}

		// Token: 0x060035CB RID: 13771 RVA: 0x00024E2C File Offset: 0x0002302C
		private GClass6.Class709 method_17(GClass6.Class709 class709_0, GClass6.Class709 class709_1)
		{
			uint num = 1358962145U;
			for (;;)
			{
				TypeCode typeCode = this.method_10(class709_0, class709_1);
				num = 1666855618U * num;
				for (;;)
				{
					object obj = typeCode;
					num = 1321949010U - num;
					object obj2 = num - 1657061575U;
					num = 1213284791U / num;
					object obj3 = obj - obj2;
					num += 303180798U;
					switch (obj3)
					{
					case 0:
						goto IL_15A;
					case 1:
					case 3:
						goto IL_197;
					case 2:
						goto IL_DF;
					case 4:
						goto IL_88;
					case 5:
					{
						uint size = (uint)IntPtr.Size;
						num ^= 2089681309U;
						if (size != num + 2439239073U)
						{
							goto Block_1;
						}
						num &= 2054633101U;
						if (num - 1857509705U != 0U)
						{
							goto Block_2;
						}
						break;
					}
					default:
						if (num * 1868369942U != 0U)
						{
							goto IL_D2;
						}
						break;
					}
				}
				IL_88:
				num = (727613293U | num);
				if (1275283133U * num == 0U)
				{
					goto IL_15A;
				}
				uint size2 = (uint)IntPtr.Size;
				num >>= 14;
				if (size2 == (num ^ 60797U))
				{
					goto IL_131;
				}
				if (1047092069U != num)
				{
					goto Block_6;
				}
			}
			Block_1:
			num += 1237849046U;
			double double_ = 0.0;
			goto IL_1C1;
			Block_2:
			double_ = double.NaN;
			num += 1313872952U;
			goto IL_1C1;
			Block_6:
			float float_ = 0f;
			goto IL_146;
			IL_D2:
			num ^= 0U;
			goto IL_197;
			IL_DF:
			num = 111025859U << (int)num;
			num += 906959172U;
			long num2 = class709_0.F4599160();
			num = 38163333U - num;
			long num3 = class709_1.F4599160();
			num >>= 28;
			long num4 = num3;
			num = 97865919U + num;
			long long_ = num2 | num4;
			num = 587816999U - num;
			return new GClass6.Class712(long_);
			IL_131:
			num = (1613503970U | num);
			float_ = float.NaN;
			num += 2681466750U;
			IL_146:
			num = 1854149344U >> (int)num;
			return new GClass6.Class713(float_);
			IL_15A:
			if (950741889U >= num)
			{
				int num5 = class709_0.C38FA951();
				num = 650982980U + num;
				int num6 = class709_1.C38FA951();
				num = 2068078225U / num;
				int num7 = num6;
				num = (1304627320U & num);
				int int_ = num5 | num7;
				num &= 1875013846U;
				return new GClass6.Class711(int_);
			}
			IL_197:
			throw new InvalidOperationException();
			IL_1C1:
			return new GClass6.Class714(double_);
		}

		// Token: 0x060035CC RID: 13772 RVA: 0x00025000 File Offset: 0x00023200
		private GClass6.Class709 method_18(GClass6.Class709 class709_0, GClass6.Class709 class709_1)
		{
			uint num2;
			for (;;)
			{
				TypeCode typeCode = this.method_10(class709_0, class709_1);
				int num = typeCode - TypeCode.Int32;
				num2 = 0U;
				switch (num)
				{
				case 0:
					goto IL_95;
				case 1:
				case 3:
					goto IL_FF;
				case 2:
					goto IL_C2;
				case 4:
				{
					int size = IntPtr.Size;
					num2 <<= 7;
					uint num3 = num2 + 4U;
					num2 = 80038052U;
					if (size == num3)
					{
						goto IL_10C;
					}
					num2 = 1042429964U * num2;
					if (1852455382U < num2)
					{
						goto Block_2;
					}
					break;
				}
				case 5:
					goto IL_127;
				default:
					if (486803411U != num2)
					{
						goto Block_3;
					}
					break;
				}
			}
			Block_2:
			float float_ = 0f;
			goto IL_121;
			Block_3:
			num2 += 0U;
			goto IL_FF;
			IL_95:
			int num4 = class709_0.C38FA951();
			num2 = (2077971022U ^ num2);
			int num5 = class709_1.C38FA951();
			num2 = 1977705807U % num2;
			int int_ = num4 & num5;
			num2 = 850539592U + num2;
			return new GClass6.Class711(int_);
			IL_C2:
			num2 = 241459168U - num2;
			num2 |= 320035356U;
			long num6 = class709_0.F4599160();
			num2 = (1034439930U ^ num2);
			long num7 = class709_1.F4599160();
			num2 %= 1297434213U;
			long long_ = num6 & num7;
			num2 >>= 10;
			return new GClass6.Class712(long_);
			IL_FF:
			throw new InvalidOperationException();
			IL_10C:
			num2 = 591011755U * num2;
			float_ = float.NaN;
			num2 ^= 1246766652U;
			IL_121:
			return new GClass6.Class713(float_);
			IL_127:
			int size2 = IntPtr.Size;
			num2 = (1139160504U | num2);
			uint num8 = num2 ^ 1139160508U;
			num2 = 1357059243U << (int)num2;
			double double_;
			if (size2 != num8)
			{
				num2 |= 1209815550U;
				double_ = 0.0;
			}
			else
			{
				double_ = double.NaN;
				num2 ^= 1075597822U;
			}
			return new GClass6.Class714(double_);
		}

		// Token: 0x060035CD RID: 13773 RVA: 0x00025184 File Offset: 0x00023384
		private int method_19(GClass6.Class709 class709_0, GClass6.Class709 class709_1, bool bool_0, int int_1)
		{
			uint num = 415452287U;
			int num2;
			for (;;)
			{
				IL_53C:
				num2 = int_1;
				num = 72310676U >> (int)num;
				for (;;)
				{
					num = (1148403711U ^ num);
					TypeCode typeCode = class709_0.vmethod_7();
					for (;;)
					{
						num = (174084223U & num);
						TypeCode typeCode2 = class709_1.vmethod_7();
						if (typeCode == (TypeCode)(num ^ 6295678U))
						{
							goto IL_0C;
						}
						if (num / 2126145897U == 0U)
						{
							for (;;)
							{
								uint num3 = (uint)typeCode2;
								num += 751578384U;
								if (num3 == num - 757874062U)
								{
									break;
								}
								num = 129655422U / num;
								TypeCode typeCode3 = typeCode;
								uint num4 = num ^ 14U;
								num *= 857806119U;
								if (typeCode3 == num4)
								{
									goto IL_498;
								}
								num &= 258374039U;
								TypeCode typeCode4 = typeCode2;
								num = 865626408U * num;
								uint num5 = num ^ 14U;
								num >>= 16;
								if (typeCode4 == num5)
								{
									goto Block_8;
								}
								num = 432160504U >> (int)num;
								if (983645091U != num)
								{
									goto Block_9;
								}
							}
							num += 3543388912U;
							goto IL_0C;
							Block_9:
							TypeCode typeCode5 = typeCode;
							uint num6 = num ^ 432160501U;
							num /= 1027814442U;
							if (typeCode5 != num6)
							{
								num += 2077496382U;
								uint num7 = (uint)typeCode2;
								num *= 1540182703U;
								if (num7 == num - 3558584917U)
								{
									num ^= 3558584930U;
								}
								else
								{
									num = 1684279440U / num;
									if (39787870U <= num)
									{
										break;
									}
									TypeCode typeCode6 = typeCode;
									uint num8 = num - 4294967285U;
									num = 587075669U >> (int)num;
									if (typeCode6 != num8)
									{
										num &= 1669413445U;
										TypeCode typeCode7 = typeCode2;
										uint num9 = num ^ 578818126U;
										num = 523113929U - num;
										if (typeCode7 == num9)
										{
											num += 642779857U;
										}
										else
										{
											num = (751511228U ^ num);
											if (num <= 739978042U)
											{
												break;
											}
											TypeCode typeCode8 = typeCode;
											uint num10 = num + 798678225U;
											num &= 1193836023U;
											if (typeCode8 != num10)
											{
												num = 747664972U * num;
												if (1036329844U + num == 0U)
												{
													break;
												}
												TypeCode typeCode9 = typeCode2;
												uint num11 = num ^ 3285580361U;
												num ^= 3285580392U;
												if (typeCode9 != num11)
												{
													goto IL_77;
												}
												num += 1075847432U;
											}
											num = (1941062540U | num);
											if (num < 2115205355U)
											{
												num = (1327391942U & num);
												int num13;
												if (!bool_0)
												{
													if ((1787847071U & num) == 0U)
													{
														break;
													}
													int num12 = class709_0.C38FA951();
													num >>= 21;
													num <<= 8;
													num13 = num12.CompareTo(class709_1.C38FA951());
												}
												else
												{
													num &= 2104241096U;
													if (1248143047U % num == 0U)
													{
														goto IL_53C;
													}
													uint num14 = class709_0.vmethod_10();
													num -= 1687975530U;
													if (num < 1632060248U)
													{
														break;
													}
													num >>= 18;
													num13 = num14.CompareTo(class709_1.vmethod_10());
													num += 123112U;
												}
												num = 1559629417U * num;
												num2 = num13;
												num += 3520342056U;
												goto IL_77;
											}
											break;
										}
									}
									num = 454762300U - num;
									if (num + 1174418429U == 0U)
									{
										break;
									}
									int num17;
									if (!bool_0)
									{
										num *= 1449734138U;
										if (799046589U >= num)
										{
											goto IL_53C;
										}
										num *= 406289955U;
										long num15 = class709_0.F4599160();
										num <<= 20;
										long num16 = num15;
										num >>= 17;
										long value = class709_1.F4599160();
										num |= 1987935926U;
										num17 = num16.CompareTo(value);
									}
									else
									{
										num /= 797851508U;
										if ((num ^ 365450714U) == 0U)
										{
											goto IL_53C;
										}
										num = 243031532U << (int)num;
										ulong num18 = class709_0.vmethod_11();
										num %= 752833445U;
										ulong num19 = num18;
										num = (1349062454U ^ num);
										num = (1162043517U & num);
										num17 = num19.CompareTo(class709_1.vmethod_11());
										num += 842669662U;
									}
									num = (1230130849U ^ num);
									num2 = num17;
									if (1256663482U > num)
									{
										num ^= 1060057151U;
										goto IL_77;
									}
									break;
								}
							}
							if (num < 17922388U)
							{
								float num20 = class709_0.vmethod_12();
								num /= 1232545159U;
								float num21 = num20;
								num >>= 10;
								num *= 638524464U;
								num2 = num21.CompareTo(class709_1.vmethod_12());
								num += 40U;
								goto IL_77;
							}
							break;
							IL_498:
							if (151718214U <= num)
							{
								break;
							}
							num *= 309798025U;
							double num22 = class709_0.A2E5C9EC();
							num = (1151216972U & num);
							double num23 = num22;
							if (1797551606U > num)
							{
								num = (1521159242U & num);
								num = (434206344U | num);
								double value2 = class709_1.A2E5C9EC();
								num /= 2105553810U;
								num2 = num23.CompareTo(value2);
								num += 40U;
								goto IL_77;
							}
							goto IL_53C;
							Block_8:
							num ^= 0U;
							goto IL_498;
						}
						continue;
						IL_77:
						num = 1967477790U >> (int)num;
						if ((1884517278U ^ num) == 0U)
						{
							break;
						}
						int num24 = num2;
						num = 167473229U + num;
						uint num25 = num ^ 175158689U;
						num += 294923077U;
						if (num24 < num25)
						{
							goto IL_59B;
						}
						if (num % 823283287U != 0U)
						{
							goto Block_5;
						}
						continue;
						IL_0C:
						num = 1746499239U >> (int)num;
						object obj = class709_0.vmethod_1();
						num %= 1388587694U;
						object obj2 = obj;
						num |= 671375356U;
						object obj3 = class709_1.vmethod_1();
						num ^= 1813014064U;
						object obj4 = obj2;
						num -= 1057255825U;
						object obj5 = obj3;
						num >>= 21;
						if (obj4 == obj5)
						{
							goto Block_1;
						}
						if (obj3 == null)
						{
							goto IL_5CF;
						}
						if (obj2 != null)
						{
							goto IL_77;
						}
						goto IL_5BC;
					}
				}
			}
			Block_1:
			num += 1234649212U;
			return (int)(num - 1234649252U);
			Block_5:
			int num26 = num2;
			uint num27 = num - 470081766U;
			num %= 601887645U;
			num += 4081103372U;
			if (num26 > num27)
			{
				num2 = (int)(num ^ 256217843U);
				num += 0U;
				goto IL_5AF;
			}
			goto IL_5AF;
			IL_59B:
			num = 1196381374U % num;
			num2 = (int)(num ^ 4038749453U);
			IL_5AF:
			num = (1153194177U ^ num);
			return num2;
			IL_5BC:
			num = 524894313U * num;
			return (int)(num ^ 479063959U);
			IL_5CF:
			return (int)(num + 4294967257U);
		}

		// Token: 0x060035CE RID: 13774 RVA: 0x00025768 File Offset: 0x00023968
		private GClass6.Class709 method_20(GClass6.Class709 class709_0)
		{
			uint num = 765871722U;
			for (;;)
			{
				num %= 707334585U;
				TypeCode typeCode = class709_0.vmethod_7();
				num <<= 1;
				TypeCode typeCode2 = typeCode;
				num |= 2118339001U;
				uint num2 = (uint)typeCode2;
				num *= 2027056177U;
				if (num2 == num + 155505406U)
				{
					num = 1262552733U - num;
					if (1573220474U > num)
					{
						break;
					}
					goto IL_3F;
				}
				IL_1A:
				TypeCode typeCode3 = typeCode2;
				num <<= 5;
				uint num3 = num ^ 3613761899U;
				num *= 793259308U;
				if (typeCode3 == num3)
				{
					goto IL_BB;
				}
				if (num < 1332692790U)
				{
					continue;
				}
				IL_3F:
				num = 1510299081U << (int)num;
				if (num != 1886535497U)
				{
					goto Block_3;
				}
				goto IL_1A;
			}
			num = 516423784U >> (int)num;
			int num4 = class709_0.C38FA951();
			num += 2086020688U;
			int int_ = ~num4;
			num += 91362421U;
			return new GClass6.Class711(int_);
			Block_3:
			throw new InvalidOperationException();
			IL_BB:
			num = (1627281562U & num);
			long long_ = ~class709_0.F4599160();
			num *= 1032082174U;
			return new GClass6.Class712(long_);
		}

		// Token: 0x060035CF RID: 13775 RVA: 0x0002584C File Offset: 0x00023A4C
		private GClass6.Class709 method_21(GClass6.Class709 class709_0)
		{
			TypeCode typeCode = class709_0.vmethod_7();
			TypeCode typeCode2 = typeCode;
			uint num = 3657433088U;
			switch (typeCode2)
			{
			case 9:
				goto IL_46;
			case 10:
			case 12:
				break;
			case 11:
			{
				num = 1928558867U + num;
				long long_ = -class709_0.F4599160();
				num = 537592091U / num;
				return new GClass6.Class712(long_);
			}
			case 13:
			{
				num <<= 27;
				num = (165493685U ^ num);
				float num2 = class709_0.vmethod_12();
				num &= 1795301267U;
				float float_ = -num2;
				num *= 1788301637U;
				return new GClass6.Class713(float_);
			}
			case 14:
			{
				num >>= 13;
				double num3 = class709_0.A2E5C9EC();
				num = 226712520U - num;
				double double_ = -num3;
				num &= 324082302U;
				return new GClass6.Class714(double_);
			}
			default:
				if (num <= 796262405U)
				{
					goto IL_46;
				}
				num ^= 0U;
				break;
			}
			do
			{
				IL_82:
				num = 608453879U / num;
			}
			while (1749502345U < num);
			throw new InvalidOperationException();
			IL_46:
			num /= 1335961007U;
			if (1693150400U % num == 0U)
			{
				return new GClass6.Class711(-class709_0.C38FA951());
			}
			goto IL_82;
		}

		// Token: 0x060035D0 RID: 13776 RVA: 0x00025944 File Offset: 0x00023B44
		private GClass6.Class709 method_22(GClass6.Class709 class709_0, GClass6.Class709 class709_1, bool bool_0)
		{
			uint num = 260124434U;
			for (;;)
			{
				num |= 649548640U;
				TypeCode typeCode = class709_0.vmethod_7();
				num /= 1410270730U;
				for (;;)
				{
					TypeCode typeCode2 = typeCode;
					num = 644176133U * num;
					uint num2 = num + 9U;
					num = (1883982966U & num);
					if (typeCode2 == num2)
					{
						goto IL_12F;
					}
					if (num % 1909984062U == 0U)
					{
						TypeCode typeCode3 = typeCode;
						num ^= 231146531U;
						uint num3 = num ^ 231146536U;
						num <<= 9;
						if (typeCode3 != num3)
						{
							if (648761211U <= num)
							{
								goto Block_3;
							}
						}
						else
						{
							if (bool_0)
							{
								break;
							}
							if (1342536656U - num != 0U)
							{
								goto IL_9D;
							}
						}
					}
				}
				num = 1469588005U << (int)num;
				if (798386890U <= num)
				{
					goto IL_E8;
				}
			}
			Block_3:
			num *= 1248682425U;
			throw new InvalidOperationException();
			IL_9D:
			long num4 = class709_0.F4599160();
			num %= 1415193571U;
			int num5 = class709_1.C38FA951();
			num *= 1432891266U;
			int num6 = num5;
			num = 1752375431U + num;
			int num7 = num6;
			num = 933970426U >> (int)num;
			long long_ = num4 >> (num7 & (int)(num ^ 466985154U));
			num >>= 22;
			return new GClass6.Class712(long_);
			IL_E8:
			ulong num8 = class709_0.vmethod_11();
			int num9 = class709_1.C38FA951();
			num = (421422779U | num);
			int num10 = num9;
			num = (1749292004U | num);
			int num11 = (int)(num + 2149681216U);
			num = 73074834U / num;
			int num12 = num10 & num11;
			num <<= 22;
			long long_2 = num8 >> num12;
			num /= 2143954210U;
			return new GClass6.Class712(long_2);
			IL_12F:
			num = (1610567145U & num);
			if (bool_0)
			{
				uint num13 = class709_0.vmethod_10();
				num += 108351303U;
				int num14 = class709_1.C38FA951();
				num %= 1532782385U;
				int num15 = num14;
				int num16 = num15;
				num ^= 1917655510U;
				int num17 = (int)(num - 1949849202U);
				num = (1126451817U ^ num);
				int num18 = num16 & num17;
				num = 529204884U / num;
				int int_ = num13 >> num18;
				num *= 2050098370U;
				return new GClass6.Class711(int_);
			}
			num = (591548729U ^ num);
			int num19 = class709_0.C38FA951();
			num = 1376409579U / num;
			num -= 661725747U;
			int num20 = class709_1.C38FA951();
			num -= 240876016U;
			int int_2 = num19 >> (num20 & (int)(num - 3392365504U));
			num &= 602024866U;
			return new GClass6.Class711(int_2);
		}

		// Token: 0x060035D1 RID: 13777 RVA: 0x00025B20 File Offset: 0x00023D20
		private GClass6.Class709 method_23(GClass6.Class709 class709_0, GClass6.Class709 class709_1)
		{
			uint num;
			for (;;)
			{
				TypeCode typeCode = class709_0.vmethod_7();
				num = 870519451U;
				TypeCode typeCode2 = typeCode;
				if (typeCode2 != TypeCode.Int32)
				{
					num >>= 15;
					if (169151514U >> (int)num == 0U)
					{
						goto IL_A9;
					}
					uint num2 = (uint)typeCode2;
					num <<= 27;
					if (num2 != (num ^ 805306379U))
					{
						goto Block_3;
					}
					num += 408645690U;
					if (num != 1005862462U)
					{
						goto Block_4;
					}
				}
				else if (1418672594U >= num)
				{
					break;
				}
			}
			int num3 = class709_0.C38FA951();
			num = (1205149707U | num);
			int num4 = class709_1.C38FA951();
			int num5 = num4;
			num = (721294050U ^ num);
			int num6 = num5 & (int)(num ^ 1560879206U);
			num %= 463634188U;
			return new GClass6.Class711(num3 << num6);
			Block_3:
			goto IL_A9;
			Block_4:
			long num7 = class709_0.F4599160();
			num = (388258284U | num);
			int num8 = class709_1.C38FA951();
			num = 2133346312U * num;
			int num9 = num8;
			num = 292358494U % num;
			int num10 = num9;
			int num11 = (int)(num ^ 292358497U);
			num |= 2142773810U;
			int num12 = num10 & num11;
			num = 697303231U - num;
			long long_ = num7 << num12;
			num = (858880723U & num);
			return new GClass6.Class712(long_);
			IL_A9:
			num %= 1180458622U;
			throw new InvalidOperationException();
		}

		// Token: 0x060035D2 RID: 13778 RVA: 0x00025C18 File Offset: 0x00023E18
		private unsafe GClass6.Class709 method_24(object object_0, Type type_1)
		{
			uint num;
			GClass6.Class709 @class;
			for (;;)
			{
				IL_00:
				object obj = object_0;
				num = 1578913608U;
				@class = (obj as GClass6.Class709);
				for (;;)
				{
					num >>= 22;
					if (type_1.IsEnum)
					{
						goto IL_232;
					}
					TypeCode typeCode = Type.GetTypeCode(type_1);
					TypeCode typeCode2 = typeCode;
					uint num2 = num + 4294966923U;
					num = 1251284464U - num;
					switch (typeCode2 - num2)
					{
					case 0:
						if (num * 866521758U != 0U)
						{
							goto Block_2;
						}
						goto IL_24A;
					case 1:
					{
						if (num == 319582606U)
						{
							continue;
						}
						bool flag = @class != null;
						num *= 1519133646U;
						if (flag)
						{
							goto IL_4FD;
						}
						if (num > 1442657401U)
						{
							goto Block_5;
						}
						continue;
					}
					case 2:
						goto IL_2C4;
					case 3:
						goto IL_2F7;
					case 4:
					{
						num = 534391519U << (int)num;
						bool flag2 = @class != null;
						num = 189027767U >> (int)num;
						if (!flag2)
						{
							goto IL_589;
						}
						if (num <= 237532772U)
						{
							goto Block_7;
						}
						continue;
					}
					case 5:
						if (2064732591U != num)
						{
							goto Block_8;
						}
						goto IL_235;
					case 6:
						goto IL_32B;
					case 7:
						goto IL_35A;
					case 8:
						if (@class != null)
						{
							goto IL_634;
						}
						num -= 602700965U;
						if ((1618826174U & num) != 0U)
						{
							goto Block_10;
						}
						goto IL_24A;
					case 9:
						if (num >> 0 != 0U)
						{
							goto Block_11;
						}
						goto IL_232;
					case 10:
						goto IL_695;
					case 11:
						goto IL_6CC;
					case 12:
					case 13:
					case 14:
						break;
					case 15:
						goto IL_480;
					default:
						if (1273918488U < num)
						{
							goto IL_24A;
						}
						num ^= 0U;
						break;
					}
					if (766344515U >= num)
					{
						continue;
					}
					num <<= 15;
					RuntimeTypeHandle handle = typeof(IntPtr).TypeHandle;
					num |= 881467761U;
					Type typeFromHandle = Type.GetTypeFromHandle(handle);
					num |= 171835796U;
					if (type_1 == typeFromHandle)
					{
						num -= 549848270U;
						if (num <= 1228483847U)
						{
							continue;
						}
						bool flag3 = @class != null;
						num = 1742808912U + num;
						if (flag3)
						{
							goto IL_741;
						}
						if (958464554U != num)
						{
							goto Block_16;
						}
						goto IL_24A;
					}
					else
					{
						num = 1915101781U * num;
						num >>= 0;
						Type typeFromHandle2 = typeof(UIntPtr);
						num = 1977966126U + num;
						if (type_1 == typeFromHandle2)
						{
							break;
						}
						if ((2014539674U ^ num) == 0U)
						{
							goto IL_24A;
						}
						num = (640483830U & num);
						if (type_1.IsValueType)
						{
							goto IL_448;
						}
						num -= 474512136U;
						bool isArray = type_1.IsArray;
						num = (1274425311U ^ num);
						if (!isArray)
						{
							goto IL_3EF;
						}
						num = 106961657U << (int)num;
						if (@class == null)
						{
							goto IL_3D8;
						}
						num = 358761228U * num;
						if ((2082552714U ^ num) != 0U)
						{
							goto Block_22;
						}
					}
					IL_235:
					bool flag4 = object_0 != null;
					num /= 2004816017U;
					if (flag4)
					{
						num <<= 3;
						if (1909851013U <= num)
						{
							goto IL_232;
						}
						bool flag5 = object_0 is Enum;
						num |= 1629031451U;
						num ^= 1629031451U;
						if (!flag5)
						{
							if (num >= 17787188U)
							{
								goto IL_00;
							}
							object_0 = Enum.ToObject(type_1, object_0);
							num += 0U;
						}
					}
					num ^= 1902837891U;
					if ((num & 682770436U) == 0U)
					{
						continue;
					}
					goto IL_8C5;
					IL_24A:
					object obj2 = @class.vmethod_1();
					num &= 760750324U;
					object_0 = obj2;
					num += 4276609240U;
					goto IL_235;
					IL_232:
					if (@class == null)
					{
						goto IL_235;
					}
					num *= 1530791308U;
					goto IL_24A;
				}
				if (num % 309470888U == 0U)
				{
					continue;
				}
				if (@class != null)
				{
					num >>= 31;
					if (628044454U > num)
					{
						goto Block_40;
					}
					continue;
				}
				else
				{
					bool flag6 = object_0 != null;
					num = 95558216U - num;
					if (flag6)
					{
						goto IL_778;
					}
					num = (1245147273U & num);
					if (num < 1784246377U)
					{
						goto Block_42;
					}
					continue;
				}
				IL_2C4:
				num = 1766993482U * num;
				if (@class == null)
				{
					if ((num ^ 859718785U) != 0U)
					{
						goto Block_28;
					}
					continue;
				}
				else
				{
					if (1219913260U >> (int)num != 0U)
					{
						goto Block_29;
					}
					continue;
				}
				IL_2F7:
				bool flag7 = @class != null;
				num &= 981745886U;
				if (!flag7)
				{
					if (1843331321U > num)
					{
						goto Block_31;
					}
					continue;
				}
				else
				{
					num = 1895330532U / num;
					if (num / 532875674U == 0U)
					{
						goto Block_32;
					}
					continue;
				}
				IL_32B:
				num = 1234059724U + num;
				bool flag8 = @class != null;
				num <<= 21;
				if (flag8)
				{
					goto IL_5CC;
				}
				num = 1176587063U + num;
				if (num % 1766546713U != 0U)
				{
					goto Block_34;
				}
				continue;
				IL_35A:
				num = 1458399509U % num;
				if (1071467930U < num)
				{
					continue;
				}
				bool flag9 = @class != null;
				num = 357447101U % num;
				if (!flag9)
				{
					goto IL_608;
				}
				if (num != 368791991U)
				{
					goto Block_37;
				}
				continue;
				IL_3D8:
				if (275980482 << (int)num != 0)
				{
					goto Block_43;
				}
				continue;
				IL_3EF:
				num >>= 23;
				if (1093296417U <= num)
				{
					continue;
				}
				bool isPointer = type_1.IsPointer;
				num /= 726144750U;
				if (isPointer)
				{
					bool flag10 = @class != null;
					num = 481318906U * num;
					if (!flag10)
					{
						goto IL_805;
					}
					if (num * 263129126U == 0U)
					{
						goto Block_47;
					}
					continue;
				}
				else
				{
					if (@class == null)
					{
						goto IL_873;
					}
					if ((1197961623U ^ num) != 0U)
					{
						goto Block_49;
					}
					continue;
				}
				IL_448:
				num *= 1755794339U;
				if (num < 1592268810U)
				{
					continue;
				}
				if (@class != null)
				{
					goto IL_8B1;
				}
				if (object_0 == null)
				{
					goto IL_885;
				}
				num *= 1908147447U;
				if (1031548850U * num != 0U)
				{
					goto Block_53;
				}
				continue;
				IL_480:
				if (@class != null)
				{
					goto IL_917;
				}
				num *= 1581792093U;
				if (1803184772U != num)
				{
					goto Block_55;
				}
			}
			Block_2:
			bool flag11 = @class != null;
			num = 1627788374U - num;
			bool bool_;
			if (!flag11)
			{
				num %= 2140948470U;
				object value = object_0;
				num = (19543538U | num);
				bool_ = Convert.ToBoolean(value);
			}
			else
			{
				num >>= 6;
				GClass6.Class709 class2 = @class;
				num = (342907152U ^ num);
				bool_ = class2.BDEF602B();
				num ^= 55797009U;
			}
			num ^= 385094744U;
			return new GClass6.Class732(bool_);
			Block_5:
			object value2 = object_0;
			num = 391394234U / num;
			char char_ = Convert.ToChar(value2);
			goto IL_51B;
			Block_7:
			short short_ = @class.C734BD78();
			num ^= 192034352U;
			goto IL_597;
			Block_8:
			ushort ushort_;
			if (@class == null)
			{
				ushort_ = Convert.ToUInt16(object_0);
			}
			else
			{
				num += 973417399U;
				ushort_ = @class.FC2B6FC2();
				num += 3321549897U;
			}
			return new GClass6.Class731(ushort_);
			Block_10:
			object value3 = object_0;
			num = 1476539853U + num;
			long long_ = Convert.ToInt64(value3);
			goto IL_64A;
			Block_11:
			bool flag12 = @class != null;
			num >>= 0;
			ulong ulong_;
			if (!flag12)
			{
				object value4 = object_0;
				num = 1793601769U >> (int)num;
				ulong_ = Convert.ToUInt64(value4);
			}
			else
			{
				GClass6.Class709 class3 = @class;
				num = 141966749U * num;
				ulong_ = class3.vmethod_11();
				num ^= 1922967026U;
			}
			return new GClass6.Class737(ulong_);
			Block_16:
			IntPtr intptr_;
			if (object_0 == null)
			{
				intptr_ = IntPtr.Zero;
			}
			else
			{
				object obj3 = object_0;
				num = (1057096333U ^ num);
				intptr_ = (IntPtr)obj3;
				num += 3405643133U;
			}
			return new GClass6.Class727(intptr_);
			Block_22:
			GClass6.Class709 class4 = @class;
			num = (494629325U & num);
			Array array_ = (Array)class4.vmethod_1();
			num += 125822122U;
			goto IL_7BF;
			Block_28:
			object value5 = object_0;
			num -= 317402290U;
			sbyte sbyte_ = Convert.ToSByte(value5);
			goto IL_547;
			Block_29:
			GClass6.Class709 class5 = @class;
			num = (1866673097U & num);
			sbyte_ = class5.vmethod_8();
			num += 4249428862U;
			goto IL_547;
			Block_31:
			byte byte_ = Convert.ToByte(object_0);
			goto IL_573;
			Block_32:
			GClass6.Class709 class6 = @class;
			num = 633671437U + num;
			byte_ = class6.vmethod_9();
			num ^= 792792911U;
			goto IL_573;
			Block_34:
			int int_ = Convert.ToInt32(object_0);
			goto IL_5EA;
			Block_37:
			GClass6.Class709 class7 = @class;
			num *= 1391224483U;
			uint uint_ = class7.vmethod_10();
			num += 2319378592U;
			goto IL_616;
			Block_40:
			GClass6.Class709 class8 = @class;
			num %= 153885585U;
			return new GClass6.Class728(class8.vmethod_14());
			Block_42:
			UIntPtr uintptr_ = UIntPtr.Zero;
			goto IL_796;
			Block_43:
			array_ = (Array)object_0;
			goto IL_7BF;
			Block_47:
			GClass6.Class709 class9 = @class;
			num *= 1959943995U;
			object ptr = class9.vmethod_1();
			num ^= 1417238432U;
			void* ptr2 = Pointer.Unbox(ptr);
			num /= 1079388983U;
			object object_ = Pointer.Box(ptr2, type_1);
			num = 205016101U / num;
			return new GClass6.Class717(object_, type_1);
			Block_49:
			GClass6.Class709 class10 = @class;
			num = 778916150U << (int)num;
			object object_2 = class10.vmethod_1();
			num += 3516051146U;
			goto IL_87C;
			Block_53:
			object object_3 = object_0;
			goto IL_8A3;
			Block_55:
			string string_ = (string)object_0;
			goto IL_925;
			IL_4FD:
			num %= 353195486U;
			GClass6.Class709 class11 = @class;
			num >>= 30;
			char_ = class11.DDEBCEDF();
			num ^= 0U;
			IL_51B:
			return new GClass6.Class733(char_);
			IL_547:
			num ^= 769333140U;
			return new GClass6.Class735(sbyte_);
			IL_573:
			return new GClass6.Class734(byte_);
			IL_589:
			object value6 = object_0;
			num = 1704814326U % num;
			short_ = Convert.ToInt16(value6);
			IL_597:
			return new GClass6.Class730(short_);
			IL_5CC:
			num = 63527792U * num;
			GClass6.Class709 class12 = @class;
			num = (1567193718U & num);
			int_ = class12.C38FA951();
			num += 4137765687U;
			IL_5EA:
			return new GClass6.Class711(int_);
			IL_608:
			object value7 = object_0;
			num /= 1508969912U;
			uint_ = Convert.ToUInt32(value7);
			IL_616:
			num = 1241672610U * num;
			return new GClass6.Class736(uint_);
			IL_634:
			GClass6.Class709 class13 = @class;
			num = 1803844652U * num;
			long_ = class13.F4599160();
			num ^= 3284363520U;
			IL_64A:
			num = 1774264674U * num;
			return new GClass6.Class712(long_);
			IL_695:
			float float_;
			if (@class == null)
			{
				num = (1352036468U & num);
				float_ = Convert.ToSingle(object_0);
			}
			else
			{
				num = (578305584U | num);
				GClass6.Class709 class14 = @class;
				num ^= 527781126U;
				float_ = class14.vmethod_12();
				num += 3406535922U;
			}
			return new GClass6.Class713(float_);
			IL_6CC:
			num = 1194210886U - num;
			bool flag13 = @class != null;
			num *= 1486579962U;
			double double_;
			if (!flag13)
			{
				object value8 = object_0;
				num = (1778414842U ^ num);
				double_ = Convert.ToDouble(value8);
			}
			else
			{
				num = (1149179137U | num);
				GClass6.Class709 class15 = @class;
				num = 536557440U % num;
				double_ = class15.A2E5C9EC();
				num ^= 1593569366U;
			}
			num >>= 14;
			return new GClass6.Class714(double_);
			IL_741:
			GClass6.Class709 class16 = @class;
			num = 2019773715U % num;
			IntPtr intptr_2 = class16.vmethod_13();
			num >>= 29;
			return new GClass6.Class727(intptr_2);
			IL_778:
			num = (433396528U ^ num);
			object obj4 = object_0;
			num %= 247216938U;
			uintptr_ = (UIntPtr)obj4;
			num ^= 1284673358U;
			IL_796:
			return new GClass6.Class728(uintptr_);
			IL_7BF:
			num = 225589597U * num;
			return new GClass6.Class719(array_);
			IL_805:
			num = 1961645811U * num;
			void* ptr4;
			if (object_0 == null)
			{
				void* ptr3 = num - 0U;
				num &= 578441905U;
				ptr4 = ptr3;
			}
			else
			{
				num ^= 315389765U;
				object ptr5 = object_0;
				num <<= 2;
				ptr4 = Pointer.Unbox(ptr5);
				num ^= 1261559060U;
			}
			object object_4 = Pointer.Box(ptr4, type_1);
			num -= 1012995081U;
			return new GClass6.Class717(object_4, type_1);
			IL_873:
			num <<= 3;
			object_2 = object_0;
			IL_87C:
			return new GClass6.Class716(object_2);
			IL_885:
			num = (752297649U & num);
			num = 491401722U * num;
			object_3 = Activator.CreateInstance(type_1);
			num += 708332670U;
			IL_8A3:
			num <<= 25;
			return new GClass6.Class718(object_3);
			IL_8B1:
			num <<= 12;
			return new GClass6.Class718(@class.vmethod_1());
			IL_8C5:
			Enum enum_;
			if (object_0 != null)
			{
				num /= 350490095U;
				enum_ = (Enum)object_0;
			}
			else
			{
				num = 829555818U >> (int)num;
				object obj5 = Activator.CreateInstance(type_1);
				num &= 1877308002U;
				enum_ = (Enum)obj5;
				num += 4191928325U;
			}
			num += 1254839426U;
			return new GClass6.Class729(enum_);
			IL_917:
			string_ = @class.ToString();
			num ^= 255132640U;
			IL_925:
			return new GClass6.Class715(string_);
		}

		// Token: 0x060035D3 RID: 13779 RVA: 0x00026550 File Offset: 0x00024750
		private string method_25(int int_1)
		{
			Dictionary<int, object> dictionary = GClass6.dictionary_1;
			object obj = dictionary;
			uint num = 651629011U;
			Monitor.Enter(obj);
			string result;
			try
			{
				num = (1330347523U | num);
				object obj2;
				string text2;
				for (;;)
				{
					Dictionary<int, object> dictionary2 = GClass6.dictionary_1;
					num = 1604877808U >> (int)num;
					if (dictionary2.TryGetValue(int_1, out obj2))
					{
						break;
					}
					num += 35929625U;
					if ((num ^ 717358487U) != 0U)
					{
						num *= 100475472U;
						Module module = this.module_0;
						num *= 186020297U;
						num >>= 12;
						string text = module.ResolveString(int_1);
						num /= 2146725345U;
						text2 = text;
						num = 1625521155U - num;
						if (num >= 14617954U)
						{
							goto IL_AD;
						}
					}
				}
				num = (1734245913U & num);
				result = (string)obj2;
				goto IL_E1;
				IL_AD:
				Dictionary<int, object> dictionary3 = GClass6.dictionary_1;
				num ^= 1890264568U;
				dictionary3.Add(int_1, text2);
				num = 1052257959U >> (int)num;
				result = text2;
				if (num % 1245737445U != 0U)
				{
				}
				IL_E1:;
			}
			finally
			{
				Monitor.Exit(dictionary);
			}
			return result;
		}

		// Token: 0x060035D4 RID: 13780 RVA: 0x00026670 File Offset: 0x00024870
		private Type method_26(int int_1)
		{
			Dictionary<int, object> obj = GClass6.dictionary_1;
			Type result;
			lock (obj)
			{
				uint num = 17529672U;
				object obj2;
				for (;;)
				{
					Dictionary<int, object> dictionary = GClass6.dictionary_1;
					num = 1808878933U << (int)num;
					if (dictionary.TryGetValue(int_1, out obj2))
					{
						break;
					}
					Module module = this.module_0;
					num = 1872039691U / num;
					Type type = module.ResolveType(int_1);
					num ^= 437911948U;
					Dictionary<int, object> dictionary2 = GClass6.dictionary_1;
					num = 448007918U - num;
					object value = type;
					num -= 1387803449U;
					dictionary2.Add(int_1, value);
					Type type2 = type;
					num ^= 2140957236U;
					result = type2;
					if (num + 270206729U != 0U)
					{
						goto IL_BB;
					}
				}
				num += 1929399348U;
				if (num != 1052712452U)
				{
					object obj3 = obj2;
					num += 991189121U;
					Type type3 = (Type)obj3;
					num &= 1052195149U;
					result = type3;
				}
				IL_BB:;
			}
			return result;
		}

		// Token: 0x060035D5 RID: 13781 RVA: 0x0002675C File Offset: 0x0002495C
		private MethodBase method_27(int int_1)
		{
			uint num = 1976073397U;
			Dictionary<int, object> obj;
			do
			{
				Dictionary<int, object> dictionary = GClass6.dictionary_1;
				num = 552302040U >> (int)num;
				obj = dictionary;
			}
			while (2111583408U <= num);
			MethodBase result2;
			lock (obj)
			{
				object obj2;
				if ((num & 1647863246U) != 0U)
				{
					MethodBase methodBase2;
					for (;;)
					{
						Dictionary<int, object> dictionary2 = GClass6.dictionary_1;
						num = (1621324779U | num);
						bool flag = dictionary2.TryGetValue(int_1, out obj2);
						num %= 284983051U;
						if (flag)
						{
							break;
						}
						num |= 1293762886U;
						Module module = this.module_0;
						num = (897542131U ^ num);
						num = 331943699U << (int)num;
						MethodBase methodBase = module.ResolveMethod(int_1);
						num = 587490339U * num;
						methodBase2 = methodBase;
						GClass6.dictionary_1.Add(int_1, methodBase2);
						if ((1492602668U ^ num) != 0U)
						{
							goto IL_B1;
						}
					}
					goto IL_BF;
					IL_B1:
					MethodBase result = methodBase2;
					num = 1124669134U * num;
					return result;
				}
				do
				{
					IL_BF:
					result2 = (MethodBase)obj2;
				}
				while (160704412U >= num);
			}
			return result2;
		}

		// Token: 0x060035D6 RID: 13782 RVA: 0x00026854 File Offset: 0x00024A54
		private FieldInfo method_28(int int_1)
		{
			Dictionary<int, object> dictionary = GClass6.dictionary_1;
			object obj = dictionary;
			uint num = 1742176255U;
			Monitor.Enter(obj);
			FieldInfo result;
			try
			{
				FieldInfo fieldInfo2;
				for (;;)
				{
					Dictionary<int, object> dictionary2 = GClass6.dictionary_1;
					num %= 419593189U;
					object obj2;
					bool flag = dictionary2.TryGetValue(int_1, out obj2);
					num = 330529370U + num;
					if (flag)
					{
						goto IL_7E;
					}
					if (3022203U <= num)
					{
						FieldInfo fieldInfo = this.module_0.ResolveField(int_1);
						num -= 208678664U;
						fieldInfo2 = fieldInfo;
						num = (123020333U ^ num);
						Dictionary<int, object> dictionary3 = GClass6.dictionary_1;
						num = 414413713U * num;
						dictionary3.Add(int_1, fieldInfo2);
						if ((782576508U ^ num) != 0U)
						{
							break;
						}
					}
				}
				return fieldInfo2;
				IL_7E:
				if (num % 366153027U != 0U)
				{
					object obj2;
					FieldInfo fieldInfo3 = (FieldInfo)obj2;
					num = 1079924461U / num;
					result = fieldInfo3;
				}
			}
			finally
			{
				Monitor.Exit(dictionary);
			}
			return result;
		}

		// Token: 0x060035D7 RID: 13783 RVA: 0x0002691C File Offset: 0x00024B1C
		private GClass6.Class709 method_29(MethodBase methodBase_0)
		{
			uint num;
			Dictionary<int, GClass6.Class709> dictionary2;
			object[] array2;
			object object_2;
			do
			{
				ParameterInfo[] parameters = methodBase_0.GetParameters();
				num = 1819557218U;
				do
				{
					IL_14F:
					Dictionary<int, GClass6.Class709> dictionary = new Dictionary<int, GClass6.Class709>();
					num = 801075234U % num;
					dictionary2 = dictionary;
					if (1790142318U >> (int)num != 0U)
					{
						int num2 = parameters.Length;
						num &= 1410025666U;
						object[] array = new object[num2];
						num /= 2048863228U;
						array2 = array;
					}
					for (;;)
					{
						ParameterInfo[] array3 = parameters;
						num &= 293498628U;
						int num3 = array3.Length;
						num = 298011213U - num;
						int num4 = num3 - (int)(num - 298011212U);
						num = (1631993180U ^ num);
						int num5 = num4;
						if (1451710646U == num)
						{
							goto IL_14F;
						}
						while (633760989U * num != 0U)
						{
							if (num5 < (int)(num ^ 1887794961U))
							{
								goto IL_119;
							}
							GClass6.Class709 @class = this.method_1();
							bool flag = @class.vmethod_3();
							num = 1646619297U;
							if (flag)
							{
								if (num > 2002341018U)
								{
									break;
								}
								dictionary2[num5] = @class;
								num ^= 0U;
							}
							object[] array4 = array2;
							int num6 = num5;
							object object_ = @class;
							num *= 573593158U;
							ParameterInfo[] array5 = parameters;
							num -= 1626941710U;
							int num7 = num5;
							num = (1591898970U ^ num);
							ParameterInfo parameterInfo = array5[num7];
							num = (792612275U & num);
							Type parameterType = parameterInfo.ParameterType;
							num = (782435540U | num);
							GClass6.Class709 class2 = this.method_24(object_, parameterType);
							num = (1902857774U | num);
							array4[num6] = class2.vmethod_1();
							num5 -= (int)(num ^ 2146390015U);
							num += 4036372243U;
						}
					}
					IL_119:
					num = (751242792U | num);
				}
				while (2126273364U + num == 0U);
				ConstructorInfo constructorInfo = (ConstructorInfo)methodBase_0;
				object[] parameters2 = array2;
				num = 1025706715U - num;
				object_2 = constructorInfo.Invoke(parameters2);
			}
			while (162754934U == num);
			Dictionary<int, GClass6.Class709>.Enumerator enumerator = dictionary2.GetEnumerator();
			num += 1352533867U;
			using (Dictionary<int, GClass6.Class709>.Enumerator enumerator2 = enumerator)
			{
				if (1457022743U == num)
				{
					goto IL_1D5;
				}
				IL_1B3:
				bool flag2 = enumerator2.MoveNext();
				num -= 350691274U;
				if (!flag2)
				{
					goto IL_24F;
				}
				num = 317008534U;
				KeyValuePair<int, GClass6.Class709> keyValuePair = enumerator2.Current;
				IL_1D5:
				num += 1617971045U;
				GClass6.Class709 value = keyValuePair.Value;
				num = 1524326236U - num;
				object[] array6 = array2;
				num <<= 2;
				num = 23668029U << (int)num;
				int key = keyValuePair.Key;
				num = 101122744U << (int)num;
				value.vmethod_2(array6[key]);
				num += 239178509U;
				goto IL_1B3;
			}
			IL_24F:
			return this.method_24(object_2, methodBase_0.DeclaringType);
		}

		// Token: 0x060035D8 RID: 13784 RVA: 0x00026BAC File Offset: 0x00024DAC
		private bool method_30(MethodBase methodBase_0, object object_0, ref object object_1, object[] object_2)
		{
			uint num = 98334530U;
			for (;;)
			{
				num *= 937776520U;
				Type declaringType = methodBase_0.DeclaringType;
				num = 1872324478U * num;
				Type type = declaringType;
				num = (180967889U | num);
				for (;;)
				{
					bool flag = type != null;
					num = 1987838022U + num;
					if (!flag)
					{
						goto Block_6;
					}
					num >>= 27;
					bool isGenericType = type.IsGenericType;
					num += 2033068885U;
					if (!isGenericType)
					{
						goto IL_210;
					}
					Type type2 = type;
					num %= 1561933873U;
					Type genericTypeDefinition = type2.GetGenericTypeDefinition();
					RuntimeTypeHandle handle = typeof(Nullable<>).TypeHandle;
					num = (1516774972U & num);
					Type typeFromHandle = Type.GetTypeFromHandle(handle);
					num += 1630407025U;
					if (genericTypeDefinition != typeFromHandle)
					{
						goto IL_210;
					}
					num /= 1500989448U;
					num -= 2143231022U;
					string name = methodBase_0.Name;
					num = 1970567492U * num;
					string b = "get_HasValue";
					num = 1716547736U << (int)num;
					StringComparison comparisonType = (StringComparison)(num ^ 118063108U);
					num = 220411452U / num;
					if (string.Equals(name, b, comparisonType))
					{
						goto IL_1DD;
					}
					string name2 = methodBase_0.Name;
					string b2 = "get_Value";
					StringComparison comparisonType2 = (StringComparison)(num - 4294967293U);
					num -= 162733941U;
					bool flag2 = string.Equals(name2, b2, comparisonType2);
					num &= 1085014903U;
					if (!flag2)
					{
						goto IL_113;
					}
					bool flag3 = object_0 != null;
					num ^= 1764577379U;
					if (flag3)
					{
						break;
					}
					num = 1473599938U - num;
					if (num * 671746913U != 0U)
					{
						goto IL_1C0;
					}
				}
				num = 180373758U - num;
				object_1 = object_0;
				if (num >= 2129080757U)
				{
					goto Block_7;
				}
				continue;
				IL_113:
				num <<= 24;
				if (num * 2004114108U != 0U)
				{
					num |= 957043642U;
					string name3 = methodBase_0.Name;
					string value = "GetValueOrDefault";
					num = 1459884132U / num;
					bool flag4 = name3.Equals(value, (StringComparison)(num - 4294967293U));
					num ^= 1338258170U;
					if (!flag4)
					{
						goto IL_208;
					}
					bool flag5 = object_0 != null;
					num &= 1312580267U;
					if (!flag5)
					{
						Type declaringType2 = methodBase_0.DeclaringType;
						num = 1497450226U - num;
						object obj = Activator.CreateInstance(Nullable.GetUnderlyingType(declaringType2));
						num = 513620923U << (int)num;
						object_0 = obj;
						num += 4284892459U;
					}
					if (num / 999632652U != 0U)
					{
						goto IL_1D0;
					}
				}
			}
			Block_6:
			goto IL_200;
			Block_7:
			num ^= 2925185644U;
			goto IL_208;
			IL_1C0:
			throw new InvalidOperationException();
			IL_1D0:
			object_1 = object_0;
			num ^= 29364304U;
			goto IL_208;
			IL_1DD:
			num = 1342664053U + num;
			object obj2 = object_0;
			object obj3 = null;
			num = (532897677U ^ num);
			object_1 = (obj2 > obj3);
			if (num > 816987999U)
			{
				goto IL_208;
			}
			IL_200:
			return num - 3866852919U != 0U;
			IL_208:
			return (num ^ 1338258170U) != 0U;
			IL_210:
			num *= 1127887842U;
			return (num ^ 2550389954U) != 0U;
		}

		// Token: 0x060035D9 RID: 13785 RVA: 0x00026DD8 File Offset: 0x00024FD8
		private GClass6.Class709 method_31(MethodBase methodBase_0, bool bool_0)
		{
			uint num = 1819178867U;
			MethodInfo methodInfo;
			Dictionary<int, GClass6.Class709> dictionary2;
			object[] array3;
			object obj5;
			object[] array8;
			Dictionary<MethodBase, DynamicMethod> dictionary4;
			for (;;)
			{
				IL_594:
				num = (1253129043U | num);
				methodInfo = (methodBase_0 as MethodInfo);
				num *= 1905462507U;
				if (832578321U <= num)
				{
					ParameterInfo[] array;
					int num14;
					for (;;)
					{
						IL_4D3:
						num ^= 1109332626U;
						ParameterInfo[] parameters = methodBase_0.GetParameters();
						num = 2065505577U % num;
						array = parameters;
						num = 10108997U >> (int)num;
						if ((2035232319U ^ num) == 0U)
						{
							goto IL_594;
						}
						GClass6.Class709 @class;
						object obj3;
						for (;;)
						{
							Dictionary<int, GClass6.Class709> dictionary = new Dictionary<int, GClass6.Class709>();
							num <<= 28;
							dictionary2 = dictionary;
							for (;;)
							{
								ParameterInfo[] array2 = array;
								num = (735398011U & num);
								array3 = new object[array2.Length];
								num = 2126727461U >> (int)num;
								for (;;)
								{
									int num2 = array.Length;
									num += 1534681896U;
									int num3 = (int)(num ^ 3661409356U);
									num += 1452752772U;
									int num4 = num2 - num3;
									for (;;)
									{
										num += 1367432022U;
										if (74919211U / num != 0U)
										{
											goto IL_4D3;
										}
										uint num5 = (uint)num4;
										num ^= 1850941575U;
										if (num5 < (num ^ 3959842720U))
										{
											break;
										}
										@class = this.method_1();
										bool flag = @class.vmethod_3();
										num = 670193225U;
										if (flag)
										{
											num >>= 30;
											Dictionary<int, GClass6.Class709> dictionary3 = dictionary2;
											int key = num4;
											num /= 1315076916U;
											dictionary3[key] = @class;
											num += 670193225U;
										}
										object[] array4 = array3;
										int num6 = num4;
										num += 844367316U;
										object object_ = @class;
										num = (1647146217U & num);
										ParameterInfo[] array5 = array;
										int num7 = num4;
										num |= 1442584193U;
										array4[num6] = this.method_24(object_, array5[num7].ParameterType).vmethod_1();
										int num8 = num4;
										num |= 1325945373U;
										int num9 = num8 - (int)(num ^ 1610372764U);
										num *= 44451362U;
										num4 = num9;
										num += 231963895U;
									}
									bool isStatic = methodBase_0.IsStatic;
									num = 315180203U - num;
									GClass6.Class709 class2;
									if (!isStatic)
									{
										class2 = this.method_1();
									}
									else
									{
										if (num % 498093646U == 0U)
										{
											goto IL_594;
										}
										class2 = null;
										num += 0U;
									}
									num |= 440154373U;
									@class = class2;
									num = (885619195U ^ num);
									bool flag2 = @class != null;
									num ^= 1183125620U;
									object obj;
									if (!flag2)
									{
										num = 449341737U * num;
										if (1821327427U == num)
										{
											goto IL_4D3;
										}
										obj = null;
									}
									else
									{
										num = (1850826412U & num);
										GClass6.Class709 class3 = @class;
										num = (2106339388U & num);
										obj = class3.vmethod_1();
										num += 1065498752U;
									}
									object obj2;
									bool flag3 = (obj2 = obj) != null;
									num >>= 25;
									if (!flag3)
									{
										obj2 = null;
										num += 0U;
									}
									num %= 207696564U;
									obj3 = obj2;
									if (!bool_0)
									{
										break;
									}
									num = (1263226320U | num);
									if (392828968 << (int)num != 0)
									{
										goto IL_1F9;
									}
								}
								IL_214:
								if (936843726U <= num)
								{
									goto IL_4D3;
								}
								object obj4 = null;
								num = 2084726566U - num;
								obj5 = obj4;
								if (350559184U - num == 0U)
								{
									break;
								}
								num <<= 5;
								bool isConstructor = methodBase_0.IsConstructor;
								num |= 107885274U;
								if (isConstructor)
								{
									if (749675809U == num)
									{
										continue;
									}
									bool isValueType = methodBase_0.DeclaringType.IsValueType;
									num ^= 0U;
									if (isValueType)
									{
										num *= 1019554968U;
										num += 900024250U;
										Type declaringType = methodBase_0.DeclaringType;
										object[] args = array3;
										num -= 164189493U;
										object obj6 = Activator.CreateInstance(declaringType, args);
										num = (969031021U & num);
										obj3 = obj6;
										num = 997012831U % num;
										if (@class == null)
										{
											goto IL_A3E;
										}
										num >>= 24;
										if (556824705U > num)
										{
											goto Block_16;
										}
										continue;
									}
								}
								num = (2122281987U | num);
								if (554108336U + num == 0U)
								{
									goto IL_4D3;
								}
								object object_2 = obj3;
								object[] object_3 = array3;
								num = (1128667311U | num);
								bool flag4 = this.method_30(methodBase_0, object_2, ref obj5, object_3);
								num %= 1823082046U;
								num ^= 566685206U;
								if (flag4)
								{
									goto IL_A3E;
								}
								num = 1532974215U * num;
								if (bool_0)
								{
									goto IL_9FE;
								}
								num = (132676807U ^ num);
								if (num == 496976723U)
								{
									goto IL_594;
								}
								bool isVirtual = methodBase_0.IsVirtual;
								num += 56126527U;
								if (!isVirtual)
								{
									goto IL_9FE;
								}
								num ^= 1747141678U;
								if ((num & 1493328632U) == 0U)
								{
									goto IL_594;
								}
								num = 624429768U / num;
								bool isFinal = methodBase_0.IsFinal;
								num -= 1333537630U;
								num ^= 1026957873U;
								if (isFinal)
								{
									goto IL_9FE;
								}
								if (101268356U % num == 0U)
								{
									goto IL_594;
								}
								ParameterInfo[] array6 = array;
								num = 888551262U + num;
								int num10 = array6.Length;
								int num11 = (int)(num + 1028903440U);
								num *= 1917065716U;
								int num12 = num10 + num11;
								num += 1009071335U;
								object[] array7 = new object[num12];
								num %= 1567049690U;
								array8 = array7;
								object[] array9 = array8;
								num *= 994458618U;
								int num13 = (int)(num ^ 1889609310U);
								num = 1923377589U + num;
								object obj7 = obj3;
								num /= 1046612483U;
								array9[num13] = obj7;
								num *= 1307603735U;
								num14 = (int)(num ^ 3922811205U);
								if (num + 1754935597U == 0U)
								{
									continue;
								}
								goto IL_564;
								IL_1F9:
								bool flag5 = obj3 != null;
								num *= 1038630758U;
								num ^= 3526389147U;
								if (flag5)
								{
									goto IL_214;
								}
								goto IL_5BD;
							}
						}
						Block_16:
						bool flag6 = @class.vmethod_3();
						num ^= 132979858U;
						if (!flag6)
						{
							goto IL_A3E;
						}
						num = 430642974U - num;
						GClass6.Class709 class4 = @class;
						object object_4 = obj3;
						num = (528574888U ^ num);
						GClass6.Class709 class5 = this.method_24(object_4, methodBase_0.DeclaringType);
						num = 1070561809U % num;
						object object_5 = class5.vmethod_1();
						num = 1761286419U / num;
						class4.vmethod_2(object_5);
						if ((num & 1660509490U) != 0U)
						{
							goto IL_5CD;
						}
					}
					IL_564:
					while ((933914023U ^ num) != 0U)
					{
						int num15 = num14;
						int num16 = array.Length;
						num = (1800037743U ^ num);
						int num17 = num16;
						num = 454506898U + num;
						if (num15 < num17)
						{
							array8[num14 + 1] = array3[num14];
							num14++;
							num = 3922811205U;
						}
						else
						{
							if (453524069U >= num)
							{
								break;
							}
							dictionary4 = GClass6.dictionary_2;
							num = 1996913763U % num;
							if (num % 2077892301U == 0U)
							{
								break;
							}
							goto IL_5DC;
						}
					}
				}
			}
			IL_5BD:
			num &= 1633771384U;
			throw new NullReferenceException();
			IL_5CD:
			num += 132979846U;
			goto IL_A3E;
			IL_5DC:
			object obj8 = dictionary4;
			num = 822550893U >> (int)num;
			Monitor.Enter(obj8);
			DynamicMethod dynamicMethod;
			try
			{
				while (!GClass6.dictionary_2.TryGetValue(methodBase_0, out dynamicMethod))
				{
					num = 712707280U % num;
					if (num == 816977169U)
					{
						goto IL_7C2;
					}
					IL_7A1:
					Type[] array10 = new Type[array8.Length];
					num &= 682891967U;
					Type[] array11 = array10;
					if (1598444128U - num == 0U)
					{
						continue;
					}
					IL_7C2:
					Type[] array12 = array11;
					num %= 1390544743U;
					int num18 = (int)(num - 11801218U);
					num = 417035705U - num;
					Type declaringType2 = methodBase_0.DeclaringType;
					num = 1102402796U - num;
					array12[num18] = declaringType2;
					int num19 = (int)(num ^ 697168309U);
					num %= 288167963U;
					int num20 = num19;
					if (275603313U >= num)
					{
						for (;;)
						{
							num >>= 8;
							ParameterInfo[] array;
							if (num20 >= array.Length)
							{
								break;
							}
							array11[num20 + 1] = array[num20].ParameterType;
							num20++;
							num = 120832383U;
						}
						string name = "";
						if (methodInfo == null)
						{
							goto IL_6D7;
						}
						num /= 247162014U;
						MethodInfo methodInfo2 = methodInfo;
						num *= 1841446368U;
						Type returnType = methodInfo2.ReturnType;
						RuntimeTypeHandle handle = typeof(void).TypeHandle;
						num = 54133719U * num;
						if (returnType == Type.GetTypeFromHandle(handle))
						{
							num ^= 472001U;
							goto IL_6D7;
						}
						Type returnType2 = methodInfo.ReturnType;
						num ^= 1561U;
						IL_6E2:
						Type[] parameterTypes = array11;
						RuntimeTypeHandle handle2 = typeof(GClass6).TypeHandle;
						num = (1446868048U | num);
						DynamicMethod dynamicMethod2 = new DynamicMethod(name, returnType2, parameterTypes, Type.GetTypeFromHandle(handle2).Module, (num ^ 1446868568U) != 0U);
						num = 432625320U % num;
						dynamicMethod = dynamicMethod2;
						if (num % 225848919U == 0U)
						{
							continue;
						}
						ILGenerator ilgenerator = dynamicMethod.GetILGenerator();
						ILGenerator ilgenerator2 = ilgenerator;
						GClass6.Class709 @class;
						GClass6.Class709 class6 = @class;
						num = (1527925556U | num);
						bool flag7 = class6.vmethod_3();
						num &= 491485378U;
						OpCode opcode;
						if (!flag7)
						{
							num = 1907978745U - num;
							opcode = OpCodes.Ldarg;
						}
						else
						{
							num = 891254447U + num;
							opcode = OpCodes.Ldarga;
							num += 167987786U;
						}
						num &= 324097355U;
						ilgenerator2.Emit(opcode, (int)(num + 4022333111U));
						int num21 = (int)(num ^ 272634184U);
						if (num - 1495549816U == 0U)
						{
							goto IL_7A1;
						}
						for (;;)
						{
							int num22 = num21;
							num = 9066894U << (int)num;
							if (num22 >= array11.Length)
							{
								break;
							}
							ILGenerator ilgenerator3 = ilgenerator;
							Dictionary<int, GClass6.Class709> dictionary5 = dictionary2;
							int num23 = num21;
							int num24 = 1;
							num = 2359230464U;
							OpCode opcode2;
							if (!dictionary5.ContainsKey(num23 - num24))
							{
								num *= 865272632U;
								opcode2 = OpCodes.Ldarg;
							}
							else
							{
								opcode2 = OpCodes.Ldarga;
								num += 321454080U;
							}
							int arg = num21;
							num /= 330523866U;
							ilgenerator3.Emit(opcode2, arg);
							num = (2143179007U | num);
							int num25 = num21;
							int num26 = (int)(num - 2143179006U);
							num *= 95582857U;
							num21 = num25 + num26;
							num ^= 1266732862U;
						}
						num = (2056665429U | num);
						ILGenerator ilgenerator4 = ilgenerator;
						OpCode call = OpCodes.Call;
						num |= 1860241331U;
						ilgenerator4.Emit(call, methodInfo);
						num = (462310349U ^ num);
						if (287785700 << (int)num != 0)
						{
							ILGenerator ilgenerator5 = ilgenerator;
							num |= 2132695632U;
							ilgenerator5.Emit(OpCodes.Ret);
							num &= 1459038668U;
							GClass6.dictionary_2[methodBase_0] = dynamicMethod;
							num ^= 1347931237U;
							break;
						}
						continue;
						IL_6D7:
						num = 736836649U / num;
						returnType2 = null;
						goto IL_6E2;
					}
				}
			}
			finally
			{
				Monitor.Exit(dictionary4);
			}
			obj5 = dynamicMethod.Invoke(null, array8);
			Dictionary<int, GClass6.Class709> dictionary6 = dictionary2;
			num = 2U;
			using (Dictionary<int, GClass6.Class709>.Enumerator enumerator = dictionary6.GetEnumerator())
			{
				if (757230501 << (int)num == 0)
				{
					goto IL_999;
				}
				IL_960:
				num %= 738527282U;
				num <<= 27;
				KeyValuePair<int, GClass6.Class709> keyValuePair;
				if (!enumerator.MoveNext())
				{
					if ((num ^ 696015932U) != 0U)
					{
						goto IL_A30;
					}
				}
				else
				{
					num = 737872432U;
					keyValuePair = enumerator.Current;
				}
				IL_999:
				num = 51529540U / num;
				GClass6.Class709 value = keyValuePair.Value;
				object[] array13 = array8;
				num = (1823294337U ^ num);
				num >>= 30;
				int key2 = keyValuePair.Key;
				int num27 = (int)(num ^ 0U);
				num <<= 6;
				value.vmethod_2(array13[key2 + num27]);
				num ^= 66U;
				goto IL_960;
			}
			IL_9FE:
			if (1031885445U >> (int)num != 0U)
			{
				object obj3;
				object obj9 = obj3;
				num = 1375428609U * num;
				obj5 = methodBase_0.Invoke(obj9, array3);
				num ^= 144902662U;
				goto IL_A3E;
			}
			IL_A30:
			dictionary2.Clear();
			num = 132979861U;
			IL_A3E:
			if (1561922876U % num != 0U)
			{
				Dictionary<int, GClass6.Class709> dictionary7 = dictionary2;
				num %= 1138187861U;
				using (Dictionary<int, GClass6.Class709>.Enumerator enumerator = dictionary7.GetEnumerator())
				{
					for (;;)
					{
						num = 1680350548U << (int)num;
						if (267613128U != num)
						{
							num %= 1160580128U;
							if (!enumerator.MoveNext())
							{
								break;
							}
						}
						KeyValuePair<int, GClass6.Class709> keyValuePair2 = enumerator.Current;
						keyValuePair2.Value.vmethod_2(array3[keyValuePair2.Key]);
						num = 132979861U;
					}
					goto IL_B1C;
				}
				IL_AE9:
				Type returnType3 = methodInfo.ReturnType;
				Type typeFromHandle = typeof(void);
				num = 849835427U;
				if (returnType3 == typeFromHandle)
				{
					goto IL_B44;
				}
				if (num >> 24 != 0U)
				{
					num -= 1846427304U;
					object object_6 = obj5;
					num <<= 9;
					return this.method_24(object_6, methodInfo.ReturnType);
				}
				IL_B1C:
				if (methodInfo != null)
				{
					goto IL_AE9;
				}
				IL_B44:
				return null;
			}
			goto IL_A30;
		}

		// Token: 0x060035DA RID: 13786 RVA: 0x00027980 File Offset: 0x00025B80
		private GClass6.Class709 method_32(int int_1, bool bool_0)
		{
			uint num = 1375274056U;
			Dictionary<int, GClass6.Class709> dictionary2;
			object[] array2;
			int num16;
			for (;;)
			{
				IL_31D:
				num /= 2097503722U;
				int num2 = this.int_0;
				num %= 180423180U;
				int num3 = num2;
				if (num - 877861288U != 0U)
				{
					goto IL_2D5;
				}
				for (;;)
				{
					IL_2F2:
					ushort num4 = (ushort)this.method_4();
					num = 1216689979U * num;
					ushort num5 = num4;
					num = 717376236U * num;
					if (2049064948U >= num)
					{
						Dictionary<int, GClass6.Class709> dictionary = new Dictionary<int, GClass6.Class709>();
						num = 203431446U >> (int)num;
						dictionary2 = dictionary;
						if (37433415U == num)
						{
							goto IL_31D;
						}
						for (;;)
						{
							object[] array = null;
							num = 965282758U >> (int)num;
							array2 = array;
							ushort num6 = num5;
							uint num7 = num - 965282758U;
							num >>= 23;
							if (num6 > num7)
							{
								num += 1313684075U;
								if (num < 1020283320U)
								{
									goto IL_2D5;
								}
								int num8 = (int)num5;
								num = 1670020581U / num;
								array2 = new object[num8];
								num &= 1380978568U;
								int num9 = (int)num5;
								num >>= 0;
								int num10 = num9 - (int)(num + 1U);
								num *= 1035337959U;
								int num11 = num10;
								for (;;)
								{
									num = 1825782950U - num;
									if (num <= 1664953660U)
									{
										goto IL_2D5;
									}
									uint num12 = (uint)num11;
									num = 318861724U >> (int)num;
									if (num12 < num - 4982214U)
									{
										break;
									}
									GClass6.Class709 @class = this.method_1();
									GClass6.Class709 class2 = @class;
									num = 0U;
									if (class2.vmethod_3())
									{
										num = 1314158606U * num;
										if ((num ^ 298471213U) == 0U)
										{
											goto IL_2D5;
										}
										Dictionary<int, GClass6.Class709> dictionary3 = dictionary2;
										num -= 19364986U;
										int key = num11;
										num = 407056116U << (int)num;
										GClass6.Class709 value = @class;
										num = 184439921U << (int)num;
										dictionary3[key] = value;
										num += 4110527375U;
									}
									object[] array3 = array2;
									num >>= 20;
									int num13 = num11;
									num = (1138820111U | num);
									object object_ = @class;
									num |= 1894213054U;
									num ^= 162485081U;
									num &= 364137201U;
									array3[num13] = this.method_24(object_, this.method_26(this.method_5())).vmethod_1();
									if (num >= 459806001U)
									{
										goto IL_31D;
									}
									int num14 = num11;
									num = 635193600U >> (int)num;
									int num15 = num14 - (int)(num - 635193599U);
									num = (599142465U & num);
									num11 = num15;
									num += 3731619840U;
								}
								num ^= 4982197U;
							}
							num = 1071411232U + num;
							num16 = this.method_5();
							num = (893744257U ^ num);
							int num17 = this.int_0;
							num = 357960425U + num;
							int_1 = num17;
							num = 830411178U * num;
							num %= 505034299U;
							this.int_0 = num3;
							num = 132988522U % num;
							num = 1884246110U * num;
							if (!bool_0)
							{
								break;
							}
							num /= 1903451329U;
							if (array2 != null)
							{
								if (1175925100U - num == 0U)
								{
									goto IL_31D;
								}
								bool flag = array2[(int)(num - 1U)] != null;
								num ^= 2941520621U;
								if (flag)
								{
									break;
								}
								num ^= 2941520621U;
							}
							num = 721251984U << (int)num;
							if (1709143874 << (int)num != 0)
							{
								goto Block_6;
							}
						}
						if (num << 17 != 0U)
						{
							goto IL_34A;
						}
					}
				}
				IL_2D5:
				int num18 = int_1;
				num = (882444863U | num);
				this.int_0 = num18;
				goto IL_2F2;
			}
			Block_6:
			throw new NullReferenceException();
			IL_34A:
			GClass6 gclass = new GClass6();
			object[] object_2 = array2;
			num = (1304123501U ^ num);
			object obj = gclass.method_112(object_2, int_1);
			num -= 1990147138U;
			object obj2 = obj;
			Dictionary<int, GClass6.Class709> dictionary4 = dictionary2;
			num = (1426730977U ^ num);
			using (Dictionary<int, GClass6.Class709>.Enumerator enumerator = dictionary4.GetEnumerator())
			{
				do
				{
					for (;;)
					{
						num >>= 18;
						if (2100117997U % num != 0U)
						{
							num = (48042936U & num);
							bool flag2 = enumerator.MoveNext();
							num &= 2002460941U;
							if (!flag2)
							{
								break;
							}
							KeyValuePair<int, GClass6.Class709> keyValuePair = enumerator.Current;
							keyValuePair.Value.vmethod_2(array2[keyValuePair.Key]);
							num = 962214366U;
						}
					}
				}
				while (639770713U == num);
			}
			bool flag3 = num16 != 0;
			num = 29U;
			if (!flag3)
			{
				goto IL_48D;
			}
			num = (239890323U | num);
			if (num - 2044349837U == 0U)
			{
				goto IL_459;
			}
			IL_43C:
			int int_2 = num16;
			num |= 592398323U;
			Type type = this.method_26(int_2);
			num += 1935879084U;
			Type type2 = type;
			IL_459:
			Type type3 = type2;
			num %= 1798068482U;
			Type typeFromHandle = typeof(void);
			num = 1098844526U << (int)num;
			num += 32318493U;
			if (type3 != typeFromHandle)
			{
				num <<= 22;
				object object_3 = obj2;
				num = 1692087295U * num;
				Type type_ = type2;
				num ^= 1931024830U;
				return this.method_24(object_3, type_);
			}
			IL_48D:
			num >>= 18;
			if (num < 1845693721U)
			{
				return null;
			}
			goto IL_43C;
		}

		// Token: 0x060035DB RID: 13787 RVA: 0x00027E68 File Offset: 0x00026068
		private bool method_33(object object_0, Type type_1)
		{
			uint num;
			Type type;
			for (;;)
			{
				num = 2130468522U;
				if (object_0 == null)
				{
					goto IL_67;
				}
				num = 675751255U >> (int)num;
				type = object_0.GetType();
				if (num / 24847400U == 0U)
				{
					if (type == type_1)
					{
						goto IL_5F;
					}
					num = (707205581U | num);
					if (930946226U - num != 0U)
					{
						break;
					}
				}
			}
			bool flag = type_1.IsAssignableFrom(type);
			num = 258304847U % num;
			if (!flag)
			{
				return num - 258304847U != 0U;
			}
			num ^= 258964102U;
			IL_5F:
			return num - 659912U != 0U;
			IL_67:
			return num - 2130468521U != 0U;
		}

		// Token: 0x060035DC RID: 13788 RVA: 0x00027EE4 File Offset: 0x000260E4
		private void method_34(Exception exception_1)
		{
			uint num;
			GClass6.Class738 @class;
			for (;;)
			{
				IL_00:
				this.stack_0.Clear();
				for (;;)
				{
					this.stack_2.Clear();
					num = 1641174373U;
					for (;;)
					{
						IL_4CC:
						num |= 285370925U;
						if (this.class738_0 == null)
						{
							num = 395386768U - num;
							if (num + 1724980021U != 0U)
							{
								goto IL_3DD;
							}
							break;
						}
						for (;;)
						{
							IL_40E:
							Stack<GClass6.Class739> stack = this.stack_1;
							num ^= 479594967U;
							bool count = stack.Count != 0;
							num |= 785342003U;
							if (!count)
							{
								goto Block_19;
							}
							List<GClass6.Class738> list = this.stack_1.Peek().method_4();
							bool flag = this.class738_0 != null;
							num = 1478000779U;
							int num3;
							if (flag)
							{
								num = 233716121U >> (int)num;
								int num2 = list.IndexOf(this.class738_0);
								num = (1221154150U | num);
								num3 = num2 + (int)(num ^ 1221197286U);
							}
							else
							{
								num3 = (int)(num ^ 1478000779U);
								num ^= 282164588U;
							}
							num = (1071259466U & num);
							this.class738_0 = null;
							num = 693062963U / num;
							int num4 = num3;
							for (;;)
							{
								int num5 = num4;
								int count2 = list.Count;
								num -= 27011979U;
								if (num5 >= count2)
								{
									break;
								}
								@class = list[num4];
								byte b = @class.method_0();
								num = 0U;
								byte b2 = b;
								if (b2 != 0)
								{
									byte b3 = b2;
									uint num6 = num ^ 1U;
									num += 1370109803U;
									if (b3 == num6)
									{
										goto IL_437;
									}
								}
								else
								{
									num -= 1514742506U;
									if (558327340U >= num)
									{
										goto IL_00;
									}
									num = (2062171116U | num);
									Type type = exception_1.GetType();
									num ^= 359156675U;
									Type type2 = type;
									num = 552563956U % num;
									if (597492819U <= num)
									{
										goto IL_3DD;
									}
									num = 395067528U + num;
									int int_ = @class.method_2();
									num = (1457417671U ^ num);
									Type type3 = this.method_26(int_);
									num ^= 247404962U;
									Type type4 = type3;
									if ((1426800675U ^ num) == 0U)
									{
										goto IL_00;
									}
									Type type5 = type2;
									Type type6 = type4;
									num /= 1391855611U;
									if (type5 == type6)
									{
										goto IL_525;
									}
									if (num / 1106656203U != 0U)
									{
										goto IL_4CC;
									}
									bool flag2 = type2.IsSubclassOf(type4);
									num = 1114574493U + num;
									num ^= 331692533U;
									if (flag2)
									{
										goto IL_51B;
									}
								}
								num >>= 11;
								if (1457659322U % num == 0U)
								{
									goto IL_3DD;
								}
								int num7 = num4;
								num >>= 23;
								num4 = num7 + (int)(num ^ 1U);
								num += 4U;
							}
							num = 674787808U - num;
							num ^= 1270362159U;
							Stack<GClass6.Class739> stack2 = this.stack_1;
							num *= 1434670270U;
							stack2.Pop();
							num = 738682567U - num;
							List<GClass6.Class738> list2 = list;
							num = 1166814258U << (int)num;
							int num8 = list2.Count;
							if (num >> 17 != 0U)
							{
								while (num != 1759261623U)
								{
									int num9 = num8;
									num = 493236334U - num;
									uint num10 = num ^ 73805934U;
									num = 1937073189U * num;
									if (num9 > num10)
									{
										GClass6.Class738 class2 = list[num8 - 1];
										byte b4 = class2.method_0();
										int num11 = 2;
										num = 1157055595U;
										if (b4 == num11)
										{
											goto IL_31C;
										}
										num = (1284128285U ^ num);
										if (num <= 101478981U)
										{
											break;
										}
										GClass6.Class738 class3 = class2;
										num = 993998362U >> (int)num;
										uint num12 = (uint)class3.method_0();
										num = 1205608622U >> (int)num;
										if (num12 == (num ^ 294342U))
										{
											num ^= 1156790697U;
											goto IL_31C;
										}
										IL_35C:
										num = (227311474U & num);
										int num13 = num8;
										num = 910639446U % num;
										num8 = num13 - (int)(num ^ 59497U);
										num ^= 419489896U;
										continue;
										IL_31C:
										num /= 1865041533U;
										num %= 740649281U;
										Stack<int> stack3 = this.stack_2;
										int item = class2.method_1();
										num = 585892177U << (int)num;
										stack3.Push(item);
										num += 3709369457U;
										goto IL_35C;
									}
									num = 1725122980U % num;
									if (377366637U + num == 0U)
									{
										break;
									}
									num ^= 616643539U;
									Stack<int> stack4 = this.stack_2;
									num = (1001999046U | num);
									bool count3 = stack4.Count != 0;
									num += 840508550U;
									if (count3)
									{
										goto Block_17;
									}
									goto IL_40E;
								}
								goto IL_00;
							}
							goto IL_00;
						}
						IL_437:
						if ((num ^ 1978211249U) == 0U)
						{
							continue;
						}
						GClass6.Class738 class4 = @class;
						num += 1626168839U;
						this.class738_0 = class4;
						num *= 2124558198U;
						if (33112355U > num)
						{
							goto IL_00;
						}
						num = 758739439U / num;
						Stack<GClass6.Class710> stack5 = this.stack_0;
						num = 1730163214U >> (int)num;
						GClass6.Class710 item2 = new GClass6.Class716(this.exception_0);
						num ^= 1205277802U;
						stack5.Push(item2);
						if (1706247001U != num)
						{
							goto Block_22;
						}
						continue;
						IL_3DD:
						num /= 2134598715U;
						num *= 191461637U;
						this.exception_0 = exception_1;
						if (264439432U != num)
						{
							num ^= 2059077224U;
							goto IL_40E;
						}
					}
				}
				Block_17:
				if (num << 16 != 0U)
				{
					goto Block_26;
				}
				continue;
				Block_19:
				if ((934704192U ^ num) != 0U)
				{
					goto Block_25;
				}
				continue;
				IL_525:
				Stack<GClass6.Class739> stack6 = this.stack_1;
				num = (1202796013U & num);
				stack6.Pop();
				num /= 337131838U;
				Stack<GClass6.Class710> stack7 = this.stack_0;
				num = (1304968999U ^ num);
				num = (1346898128U & num);
				object object_ = this.exception_0;
				num = 1726032688U * num;
				GClass6.Class710 item3 = new GClass6.Class716(object_);
				num %= 337913113U;
				stack7.Push(item3);
				this.int_0 = @class.method_1();
				if (228473097U >= num)
				{
					return;
				}
				continue;
				IL_51B:
				num += 2924857494U;
				goto IL_525;
			}
			Block_22:
			GClass6.Class738 class5 = @class;
			num = 1538737567U >> (int)num;
			this.int_0 = class5.method_2();
			return;
			Block_25:
			throw exception_1;
			Block_26:
			this.int_0 = this.stack_2.Pop();
		}

		// Token: 0x060035DD RID: 13789 RVA: 0x000284C0 File Offset: 0x000266C0
		private void method_35()
		{
			for (;;)
			{
				Type type = this.method_26(this.method_1().C38FA951());
				uint num = 537275781U;
				Type type2 = type;
				GClass6.Class709 @class;
				do
				{
					@class = this.method_1();
					num %= 81471662U;
				}
				while (339552340U % num == 0U);
				object object_ = this.method_1().vmethod_1();
				Type type_ = type2;
				num -= 1671258945U;
				GClass6.Class709 class2 = this.method_24(object_, type_);
				num = 1462251710U % num;
				if (num != 1087833990U)
				{
					bool flag = @class.vmethod_3();
					num |= 168825265U;
					if (flag)
					{
						num = 636291613U - num;
						if (961682553U < num)
						{
							GClass6.Class709 class709_ = class2;
							num <<= 4;
							class2 = new GClass6.Class722(class709_, @class);
							num ^= 880129119U;
						}
					}
					List<GClass6.Class709> list = this.list_0;
					GClass6.Class709 item = class2;
					num = 1607690323U >> (int)num;
					list.Add(item);
					if (1420042873U * num == 0U)
					{
						break;
					}
				}
			}
		}

		// Token: 0x060035DE RID: 13790 RVA: 0x00028594 File Offset: 0x00026794
		private void method_36()
		{
			int num = this.method_1().C38FA951();
			List<GClass6.Class739>.Enumerator enumerator = this.list_1.GetEnumerator();
			uint num2 = 115081U;
			using (List<GClass6.Class739>.Enumerator enumerator2 = enumerator)
			{
				for (;;)
				{
					IL_77:
					bool flag = enumerator2.MoveNext();
					num2 %= 327364269U;
					if (!flag)
					{
						break;
					}
					GClass6.Class739 @class;
					do
					{
						@class = enumerator2.Current;
						int num3 = @class.method_0();
						int num4 = num;
						num2 = 115081U;
						if (num3 != num4)
						{
							goto IL_77;
						}
					}
					while (961488425U / num2 == 0U);
					Stack<GClass6.Class739> stack = this.stack_1;
					num2 %= 494746543U;
					stack.Push(@class);
					num2 ^= 0U;
				}
			}
		}

		// Token: 0x060035DF RID: 13791 RVA: 0x0002864C File Offset: 0x0002684C
		private void method_37()
		{
			uint num = 763254171U;
			do
			{
				num *= 1169371280U;
				num <<= 27;
				GClass6.Class709 class709_ = new GClass6.Class711(this.method_5());
				num /= 467565896U;
				this.method_0(class709_);
			}
			while ((num & 2075685959U) == 0U);
		}

		// Token: 0x060035E0 RID: 13792 RVA: 0x00028694 File Offset: 0x00026894
		private void method_38()
		{
			this.method_0(new GClass6.Class712(this.method_6()));
		}

		// Token: 0x060035E1 RID: 13793 RVA: 0x000286B4 File Offset: 0x000268B4
		private void method_39()
		{
			this.method_0(new GClass6.Class713(this.method_7()));
		}

		// Token: 0x060035E2 RID: 13794 RVA: 0x000286D4 File Offset: 0x000268D4
		private void method_40()
		{
			this.method_0(new GClass6.Class714(this.method_8()));
		}

		// Token: 0x060035E3 RID: 13795 RVA: 0x000286F4 File Offset: 0x000268F4
		private void method_41()
		{
			uint num = 1873895527U;
			do
			{
				num -= 878906836U;
				object object_ = null;
				num = 858472340U / num;
				this.method_0(new GClass6.Class716(object_));
			}
			while (num == 1428779734U);
		}

		// Token: 0x060035E4 RID: 13796 RVA: 0x0002872C File Offset: 0x0002692C
		private void method_42()
		{
			uint num = 506490133U;
			do
			{
				num = 154411313U << (int)num;
				int int_ = this.method_1().C38FA951();
				num &= 1179536314U;
				this.method_0(new GClass6.Class715(this.method_25(int_)));
			}
			while ((num & 1739213812U) == 0U);
		}

		// Token: 0x060035E5 RID: 13797 RVA: 0x0002877C File Offset: 0x0002697C
		private void method_43()
		{
			uint num = 1044059322U;
			do
			{
				num /= 303323106U;
				GClass6.Class709 @class = this.method_2();
				num ^= 528231668U;
				GClass6.Class709 class709_ = @class.vmethod_0();
				num *= 1500193349U;
				this.method_0(class709_);
			}
			while (665277655U + num == 0U);
		}

		// Token: 0x060035E6 RID: 13798 RVA: 0x000287C4 File Offset: 0x000269C4
		private void method_44()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_11(class709_, class709_2, false, false));
		}

		// Token: 0x060035E7 RID: 13799 RVA: 0x000287F0 File Offset: 0x000269F0
		private void method_45()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_11(class709_, class709_2, true, false));
		}

		// Token: 0x060035E8 RID: 13800 RVA: 0x0002881C File Offset: 0x00026A1C
		private void method_46()
		{
			GClass6.Class709 @class = this.method_1();
			uint num = 3276800U;
			GClass6.Class709 class2 = this.method_1();
			do
			{
				num &= 544620684U;
				GClass6.Class709 class709_ = @class;
				GClass6.Class709 class709_2 = class2;
				num = 1852651283U << (int)num;
				bool bool_ = (num ^ 1852651282U) != 0U;
				num >>= 4;
				bool bool_2 = num - 115790704U != 0U;
				num /= 1919843412U;
				GClass6.Class709 class709_3 = this.method_11(class709_, class709_2, bool_, bool_2);
				num %= 1578976439U;
				this.method_0(class709_3);
			}
			while (1196061280 << (int)num == 0);
		}

		// Token: 0x060035E9 RID: 13801 RVA: 0x000288A8 File Offset: 0x00026AA8
		private void method_47()
		{
			uint num = 2135835215U;
			GClass6.Class709 @class;
			GClass6.Class709 class3;
			for (;;)
			{
				@class = this.method_1();
				num %= 1217144303U;
				if (1564227706U > num)
				{
					num |= 101416544U;
					GClass6.Class709 class2 = this.method_1();
					num += 1899716993U;
					class3 = class2;
					num ^= 1102265664U;
					if ((num ^ 1225734469U) != 0U)
					{
						break;
					}
				}
			}
			num %= 187002025U;
			num += 141572993U;
			GClass6.Class709 class709_ = class3;
			num = 1547698846U * num;
			GClass6.Class709 class709_2 = @class;
			bool bool_ = num + 3953971484U != 0U;
			bool bool_2 = (num ^ 340995812U) != 0U;
			num = (583144182U ^ num);
			this.method_0(this.method_12(class709_, class709_2, bool_, bool_2));
		}

		// Token: 0x060035EA RID: 13802 RVA: 0x0002893C File Offset: 0x00026B3C
		private void method_48()
		{
			uint num = 519646597U;
			GClass6.Class709 @class;
			GClass6.Class709 class2;
			do
			{
				@class = this.method_1();
				num >>= 6;
				if (num > 1460427079U)
				{
					break;
				}
				class2 = this.method_1();
			}
			while (2017355077U == num);
			num = 1332026175U + num;
			GClass6.Class709 class709_ = class2;
			num -= 1027307366U;
			GClass6.Class709 class709_2 = @class;
			bool bool_ = (num ^ 312838286U) != 0U;
			num %= 907033835U;
			bool bool_2 = (num ^ 312838287U) != 0U;
			num >>= 17;
			GClass6.Class709 class709_3 = this.method_12(class709_, class709_2, bool_, bool_2);
			num %= 291442565U;
			this.method_0(class709_3);
		}

		// Token: 0x060035EB RID: 13803 RVA: 0x000289C0 File Offset: 0x00026BC0
		private void method_49()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_12(class709_2, class709_, true, true));
		}

		// Token: 0x060035EC RID: 13804 RVA: 0x000289EC File Offset: 0x00026BEC
		private void method_50()
		{
			GClass6.Class709 @class = this.method_1();
			GClass6.Class709 class2 = this.method_1();
			uint num = 0U;
			GClass6.Class709 class3 = class2;
			do
			{
				num = 1004882581U >> (int)num;
				num = 2036890475U * num;
				GClass6.Class709 class709_ = class3;
				GClass6.Class709 class709_2 = @class;
				bool bool_ = (num ^ 3979592519U) != 0U;
				num = 1108037413U + num;
				bool bool_2 = num + 3502304660U != 0U;
				num |= 1158815373U;
				GClass6.Class709 class709_3 = this.method_13(class709_, class709_2, bool_, bool_2);
				num |= 1851358183U;
				this.method_0(class709_3);
			}
			while (1858940254U / num != 0U);
		}

		// Token: 0x060035ED RID: 13805 RVA: 0x00028A6C File Offset: 0x00026C6C
		private void method_51()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_13(class709_2, class709_, true, false));
		}

		// Token: 0x060035EE RID: 13806 RVA: 0x00028A98 File Offset: 0x00026C98
		private void method_52()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_13(class709_2, class709_, true, true));
		}

		// Token: 0x060035EF RID: 13807 RVA: 0x00028AC4 File Offset: 0x00026CC4
		private void method_53()
		{
			uint num = 745479010U;
			for (;;)
			{
				num &= 410407156U;
				GClass6.Class709 @class = this.method_1();
				num <<= 5;
				GClass6.Class709 class2 = @class;
				num += 1818781299U;
				if ((1681401338U ^ num) != 0U)
				{
					GClass6.Class709 class4;
					do
					{
						GClass6.Class709 class3 = this.method_1();
						num = 530527133U + num;
						class4 = class3;
					}
					while (231426821U > num);
					num = (7678705U ^ num);
					num = 1360542572U / num;
					GClass6.Class709 class709_ = class4;
					GClass6.Class709 class709_2 = class2;
					bool bool_ = num + 0U != 0U;
					num ^= 1402961125U;
					this.method_0(this.method_14(class709_, class709_2, bool_));
					if (num % 1885300566U != 0U)
					{
						break;
					}
				}
			}
		}

		// Token: 0x060035F0 RID: 13808 RVA: 0x00028B50 File Offset: 0x00026D50
		private void method_54()
		{
			uint num = 1937192070U;
			for (;;)
			{
				GClass6.Class709 @class = this.method_1();
				num = (364801402U & num);
				if (1682577154U < num)
				{
					break;
				}
				for (;;)
				{
					num %= 1824357761U;
					GClass6.Class709 class2 = this.method_1();
					num = (587168266U ^ num);
					GClass6.Class709 class3 = class2;
					if (1817139234U < num)
					{
						break;
					}
					num &= 682380820U;
					num = 55004545U >> (int)num;
					GClass6.Class709 class709_ = class3;
					num *= 70219240U;
					GClass6.Class709 class709_2 = @class;
					num <<= 11;
					GClass6.Class709 class709_3 = this.method_14(class709_, class709_2, (num ^ 3381608449U) != 0U);
					num = (234583275U ^ num);
					this.method_0(class709_3);
					if (58615099U < num)
					{
						return;
					}
				}
			}
		}

		// Token: 0x060035F1 RID: 13809 RVA: 0x00028BE8 File Offset: 0x00026DE8
		private void method_55()
		{
			uint num = 2143355340U;
			for (;;)
			{
				GClass6.Class709 @class = this.method_1();
				num = 992951160U + num;
				GClass6.Class709 class2 = @class;
				num &= 1937396949U;
				GClass6.Class709 class3 = this.method_1();
				num ^= 337532713U;
				GClass6.Class709 class4 = class3;
				if (num / 1948784915U == 0U)
				{
					GClass6.Class709 class709_ = class4;
					num |= 933329463U;
					GClass6.Class709 class709_2 = class2;
					num += 1025914936U;
					bool bool_ = num - 1964356535U != 0U;
					num = 713195496U - num;
					GClass6.Class709 class709_3 = this.method_15(class709_, class709_2, bool_);
					num = 1686140775U % num;
					this.method_0(class709_3);
					if (num > 1399472590U)
					{
						break;
					}
				}
			}
		}

		// Token: 0x060035F2 RID: 13810 RVA: 0x00028C6C File Offset: 0x00026E6C
		private void method_56()
		{
			uint num = 1601270943U;
			for (;;)
			{
				num = (251095774U | num);
				GClass6.Class709 @class = this.method_1();
				num = (1071058446U | num);
				GClass6.Class709 class2 = @class;
				GClass6.Class709 class3 = this.method_1();
				if (1282631526U < num)
				{
					num *= 1966094952U;
					GClass6.Class709 class709_ = class3;
					GClass6.Class709 class709_2 = class2;
					bool bool_ = num - 3290042519U != 0U;
					num ^= 1592943557U;
					this.method_0(this.method_15(class709_, class709_2, bool_));
					if (1213668824U < num)
					{
						break;
					}
				}
			}
		}

		// Token: 0x060035F3 RID: 13811 RVA: 0x00028CD8 File Offset: 0x00026ED8
		private void method_57()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_18(class709_2, class709_));
		}

		// Token: 0x060035F4 RID: 13812 RVA: 0x00028D04 File Offset: 0x00026F04
		private void method_58()
		{
			uint num = 24450943U;
			GClass6.Class709 @class = this.method_1();
			GClass6.Class709 class2;
			do
			{
				class2 = this.method_1();
			}
			while (54410221 << (int)num == 0);
			num -= 1032130453U;
			GClass6.Class709 class709_ = class2;
			GClass6.Class709 class709_2 = @class;
			num ^= 1234841537U;
			this.method_0(this.method_17(class709_, class709_2));
		}

		// Token: 0x060035F5 RID: 13813 RVA: 0x00028D54 File Offset: 0x00026F54
		private void method_59()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_16(class709_2, class709_));
		}

		// Token: 0x060035F6 RID: 13814 RVA: 0x00028D80 File Offset: 0x00026F80
		private void method_60()
		{
			GClass6.Class709 class709_ = this.method_1();
			this.method_0(this.method_20(class709_));
		}

		// Token: 0x060035F7 RID: 13815 RVA: 0x00028DA4 File Offset: 0x00026FA4
		private void method_61()
		{
			uint num = 552166746U;
			GClass6.Class709 class709_;
			do
			{
				num = 429588904U / num;
				GClass6.Class709 @class = this.method_1();
				num %= 894447671U;
				class709_ = @class;
			}
			while (656155759 << (int)num == 0);
			num = 578359301U * num;
			this.method_0(this.method_21(class709_));
		}

		// Token: 0x060035F8 RID: 13816 RVA: 0x00028DF4 File Offset: 0x00026FF4
		private void method_62()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_22(class709_2, class709_, false));
		}

		// Token: 0x060035F9 RID: 13817 RVA: 0x00028E20 File Offset: 0x00027020
		private void method_63()
		{
			GClass6.Class709 @class = this.method_1();
			uint num = 2403585000U;
			do
			{
				num = 86143869U - num;
				GClass6.Class709 class2 = this.method_1();
				num |= 1750360728U;
				GClass6.Class709 class3 = class2;
				num = 1667069460U >> (int)num;
				num = 1943486926U * num;
				GClass6.Class709 class709_ = class3;
				GClass6.Class709 class709_2 = @class;
				num <<= 0;
				GClass6.Class709 class709_3 = this.method_22(class709_, class709_2, (num ^ 1535493483U) != 0U);
				num /= 403178638U;
				this.method_0(class709_3);
			}
			while (num << 23 == 0U);
		}

		// Token: 0x060035FA RID: 13818 RVA: 0x00028EA4 File Offset: 0x000270A4
		private void method_64()
		{
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(this.method_23(class709_2, class709_));
		}

		// Token: 0x060035FB RID: 13819 RVA: 0x00028ED0 File Offset: 0x000270D0
		private void method_65()
		{
			Type type_ = this.method_26(this.method_1().C38FA951());
			this.method_0(this.method_24(this.method_1(), type_));
		}

		// Token: 0x060035FC RID: 13820 RVA: 0x00028F04 File Offset: 0x00027104
		private void method_66()
		{
			uint num = 408831752U;
			do
			{
				num = (1087727062U & num);
				num %= 487983820U;
				GClass6.Class709 @class = this.method_1();
				num = 1254242947U - num;
				int int_ = @class.C38FA951();
				num = 1048272282U % num;
				Type type = this.method_26(int_);
				num = (1840710614U ^ num);
				num = 1095531373U >> (int)num;
				GClass6.Class709 class2 = this.method_1();
				Type type2 = type;
				num = 447550735U - num;
				bool bool_ = num - 447283272U != 0U;
				num = 102652547U % num;
				this.method_0(this.method_24(class2.E4B72359(type2, bool_), type));
			}
			while (num << 25 == 0U);
		}

		// Token: 0x060035FD RID: 13821 RVA: 0x00028F9C File Offset: 0x0002719C
		private void method_67()
		{
			Type type = this.method_26(this.method_1().C38FA951());
			uint num = 7891U;
			do
			{
				num = 584915383U << (int)num;
				num = 1944396941U - num;
				num = 1713402509U - num;
				GClass6.Class709 @class = this.method_1();
				num = (234104073U & num);
				Type type2 = type;
				num >>= 1;
				bool bool_ = (num ^ 116916737U) != 0U;
				num = (1529899791U & num);
				object object_ = @class.E4B72359(type2, bool_);
				num = (337072412U | num);
				Type type_ = type;
				num += 244650409U;
				this.method_0(this.method_24(object_, type_));
			}
			while (num >= 748824459U);
		}

		// Token: 0x060035FE RID: 13822 RVA: 0x0002904C File Offset: 0x0002724C
		private void method_68()
		{
			this.method_0(new GClass6.Class711(Marshal.SizeOf(this.method_26(this.method_5()))));
		}

		// Token: 0x060035FF RID: 13823 RVA: 0x00029078 File Offset: 0x00027278
		private void method_69()
		{
			uint num = 973436926U;
			for (;;)
			{
				num = 1986420287U << (int)num;
				num = 339626418U << (int)num;
				GClass6.Class709 @class = this.method_1();
				num = (1478437426U ^ num);
				Type type = this.method_26(@class.C38FA951());
				num *= 518599573U;
				Type type_ = type;
				num = (752178605U ^ num);
				if (num <= 822088889U)
				{
					goto IL_0B;
				}
				GClass6.Class709 class3;
				for (;;)
				{
					num = (237467383U ^ num);
					GClass6.Class709 class2 = this.method_1();
					num = 1317150717U - num;
					class3 = class2;
					if (num >= 119346242U)
					{
						do
						{
							bool flag = class3.vmethod_3();
							num = 289418890U >> (int)num;
							if (flag)
							{
								goto IL_42;
							}
							num = 600325125U >> (int)num;
						}
						while (num > 400239940U);
					}
					GClass6.Class709 class4 = class3;
					num = 481308094U / num;
					if (class4.vmethod_1() is Pointer)
					{
						goto IL_0B;
					}
					num += 184563600U;
					if (26050080 << (int)num != 0)
					{
						goto Block_3;
					}
				}
				IL_42:
				num = 1908802918U % num;
				num &= 1932413838U;
				object object_ = class3;
				num %= 1665932320U;
				this.method_0(this.method_24(object_, type_));
				if (num < 659716740U)
				{
					break;
				}
				continue;
				IL_0B:
				GClass6.Class709 class5 = class3;
				num = 2144031655U * num;
				object ptr = class5.vmethod_1();
				num |= 1759061450U;
				IntPtr intptr_ = new IntPtr(Pointer.Unbox(ptr));
				num |= 1430069458U;
				class3 = new GClass6.Class725(intptr_, type_);
				num ^= 2144843822U;
				goto IL_42;
			}
			return;
			Block_3:
			throw new ArgumentException();
		}

		// Token: 0x06003600 RID: 13824 RVA: 0x000291E0 File Offset: 0x000273E0
		private void method_70()
		{
			int int_ = this.method_1().C38FA951();
			uint num = 1133297508U;
			FieldInfo fieldInfo = this.method_28(int_);
			object obj;
			for (;;)
			{
				GClass6.Class709 @class = this.method_1();
				num = 53616859U / num;
				obj = @class.vmethod_1();
				num = 1562656146U * num;
				if (253040707U - num != 0U)
				{
					if (fieldInfo.IsStatic)
					{
						goto IL_87;
					}
					num %= 457320390U;
					bool flag = obj != null;
					num += 0U;
					if (flag)
					{
						goto IL_87;
					}
					num = 1616477491U * num;
					if ((566833930U & num) == 0U)
					{
						break;
					}
				}
			}
			throw new NullReferenceException();
			IL_87:
			num = (1451768750U & num);
			num = (24585463U ^ num);
			num = 1932464533U >> (int)num;
			FieldInfo fieldInfo2 = fieldInfo;
			object obj2 = obj;
			num -= 104215794U;
			this.method_0(this.method_24(fieldInfo2.GetValue(obj2), fieldInfo.FieldType));
		}

		// Token: 0x06003601 RID: 13825 RVA: 0x000292B4 File Offset: 0x000274B4
		private void method_71()
		{
			FieldInfo fieldInfo;
			object obj;
			uint num;
			do
			{
				fieldInfo = this.method_28(this.method_1().C38FA951());
				obj = this.method_1().vmethod_1();
				bool isStatic = fieldInfo.IsStatic;
				num = 289409040U;
				if (isStatic)
				{
					goto IL_63;
				}
			}
			while (827473114U == num);
			bool flag = obj != null;
			num ^= 0U;
			if (!flag)
			{
				throw new NullReferenceException();
			}
			IL_63:
			num = 1326259362U % num;
			FieldInfo fieldInfo_ = fieldInfo;
			object object_ = obj;
			num = (993265877U ^ num);
			GClass6.Class709 class709_ = new GClass6.Class723(fieldInfo_, object_);
			num = 1333487395U - num;
			this.method_0(class709_);
		}

		// Token: 0x06003602 RID: 13826 RVA: 0x0002934C File Offset: 0x0002754C
		private void method_72()
		{
			uint num = 159741742U;
			FieldInfo fieldInfo;
			GClass6.Class709 class2;
			object obj;
			for (;;)
			{
				num = 1094459847U >> (int)num;
				num |= 147282149U;
				GClass6.Class709 @class = this.method_1();
				num = 446439513U + num;
				fieldInfo = this.method_28(@class.C38FA951());
				num = (1870232443U | num);
				if (num - 597363664U == 0U)
				{
					goto IL_77;
				}
				IL_0B:
				class2 = this.method_1();
				num >>= 27;
				if (num > 714342519U)
				{
					continue;
				}
				GClass6.Class709 class3 = this.method_1();
				num = 330513095U % num;
				obj = class3.vmethod_1();
				num /= 1369011390U;
				if ((444539317U ^ num) == 0U)
				{
					continue;
				}
				FieldInfo fieldInfo2 = fieldInfo;
				num /= 1361664097U;
				if (!fieldInfo2.IsStatic)
				{
					num = 1618218037U >> (int)num;
					goto IL_77;
				}
				IL_57:
				num = 783686958U * num;
				if (2041726255U > num)
				{
					break;
				}
				goto IL_0B;
				IL_77:
				bool flag = obj != null;
				num = 2024298015U >> (int)num;
				num ^= 965U;
				if (!flag)
				{
					goto Block_4;
				}
				goto IL_57;
			}
			FieldInfo fieldInfo3 = fieldInfo;
			num = 394160810U << (int)num;
			object obj2 = obj;
			num = (212536726U ^ num);
			object object_ = class2;
			num = (615543117U ^ num);
			FieldInfo fieldInfo4 = fieldInfo;
			num = (1949049943U ^ num);
			Type fieldType = fieldInfo4.FieldType;
			num = 1051023150U + num;
			GClass6.Class709 class4 = this.method_24(object_, fieldType);
			num /= 227609583U;
			fieldInfo3.SetValue(obj2, class4.vmethod_1());
			return;
			Block_4:
			num = (1272473266U ^ num);
			throw new NullReferenceException();
		}

		// Token: 0x06003603 RID: 13827 RVA: 0x00029490 File Offset: 0x00027690
		private void method_73()
		{
			FieldInfo fieldInfo = this.method_28(this.method_1().C38FA951());
			GClass6.Class709 object_ = this.method_1();
			fieldInfo.SetValue(null, this.method_24(object_, fieldInfo.FieldType).vmethod_1());
		}

		// Token: 0x06003604 RID: 13828 RVA: 0x000294D0 File Offset: 0x000276D0
		private unsafe void method_74()
		{
			uint num = 1175864614U;
			Type type_;
			GClass6.Class709 object_;
			GClass6.Class709 class2;
			for (;;)
			{
				int int_ = this.method_1().C38FA951();
				num /= 1197498220U;
				Type type = this.method_26(int_);
				num *= 1207502079U;
				type_ = type;
				num <<= 8;
				if (741495659U > num)
				{
					for (;;)
					{
						GClass6.Class709 @class = this.method_1();
						num = 793869955U * num;
						object_ = @class;
						if (1578122684U != num)
						{
							num &= 505022116U;
							class2 = this.method_1();
							GClass6.Class709 class3 = class2;
							num = 1216360869U >> (int)num;
							if (class3.vmethod_3())
							{
								goto IL_EE;
							}
							num = 521305605U % num;
							if ((num & 76835868U) == 0U)
							{
								break;
							}
							object obj = class2.vmethod_1();
							num = (2065568660U | num);
							if (!(obj is Pointer))
							{
								goto IL_E8;
							}
							num = 457574105U + num;
							if ((1655180395U ^ num) != 0U)
							{
								goto IL_B7;
							}
						}
					}
				}
			}
			IL_B7:
			void* value = Pointer.Unbox(class2.vmethod_1());
			num = 1432818657U * num;
			GClass6.Class709 class4 = new GClass6.Class725(new IntPtr(value), type_);
			num = 1614832548U / num;
			class2 = class4;
			num += 1216360869U;
			goto IL_EE;
			IL_E8:
			throw new ArgumentException();
			IL_EE:
			num <<= 7;
			class2.vmethod_2(this.method_24(object_, type_).vmethod_1());
		}

		// Token: 0x06003605 RID: 13829 RVA: 0x00029600 File Offset: 0x00027800
		private void method_75()
		{
			this.method_0(this.list_0[(int)((ushort)this.method_4())].vmethod_0());
		}

		// Token: 0x06003606 RID: 13830 RVA: 0x0002962C File Offset: 0x0002782C
		private void method_76()
		{
			this.method_0(new GClass6.Class721(this.list_0[(int)((ushort)this.method_4())]));
		}

		// Token: 0x06003607 RID: 13831 RVA: 0x00029658 File Offset: 0x00027858
		private void method_77()
		{
			GClass6.Class709 object_ = this.method_1();
			GClass6.Class709 @class = this.list_0[(int)((ushort)this.method_4())];
			@class.vmethod_2(this.method_24(object_, @class.vmethod_6()).vmethod_1());
		}

		// Token: 0x06003608 RID: 13832 RVA: 0x00029698 File Offset: 0x00027898
		private void method_78()
		{
			this.type_0 = this.method_26(this.method_1().C38FA951());
		}

		// Token: 0x06003609 RID: 13833 RVA: 0x000296BC File Offset: 0x000278BC
		private void method_79()
		{
			uint num;
			do
			{
				MethodBase methodBase_ = this.method_27(this.method_1().C38FA951());
				GClass6.Class709 @class = this.method_31(methodBase_, false);
				num = 1136660544U;
				if (@class != null)
				{
					num = 1941005656U + num;
					GClass6.Class709 class709_ = @class;
					num = 1398800914U / num;
					this.method_0(class709_);
					num += 1136660544U;
				}
			}
			while (797642306U > num);
		}

		// Token: 0x0600360A RID: 13834 RVA: 0x00029740 File Offset: 0x00027940
		private void method_80()
		{
			uint num;
			GClass6.Class709 @class;
			for (;;)
			{
				IL_00:
				MethodBase methodBase = this.method_27(this.method_1().C38FA951());
				num = 2688286720U;
				if (this.type_0 == null)
				{
					goto IL_244;
				}
				for (;;)
				{
					IL_1A3:
					MethodBase methodBase2 = methodBase;
					num = 434969175U / num;
					ParameterInfo[] parameters = methodBase2.GetParameters();
					num /= 373500160U;
					num = 591857521U << (int)num;
					int num2 = parameters.Length;
					num = 83182135U % num;
					int num3 = num2;
					num = 805184448U << (int)num;
					Type[] array = new Type[num3];
					num = (916398957U ^ num);
					int num4 = (int)(num ^ 3600753517U);
					ParameterInfo[] array2 = parameters;
					num %= 237909399U;
					int num5 = (int)(num + 4262854764U);
					while (1799430613U / num != 0U)
					{
						int num6 = num5;
						int num7 = array2.Length;
						num = 1494512652U + num;
						int num8 = num7;
						num = 956380241U - num;
						if (num6 < num8)
						{
							ParameterInfo parameterInfo = array2[num5];
							array[num4++] = parameterInfo.ParameterType;
							num5++;
							num = 32112532U;
						}
						else
						{
							num &= 839066615U;
							Type type = this.type_0;
							num = 1405511477U / num;
							MemberInfo memberInfo = methodBase;
							num = 910910909U << (int)num;
							string name = memberInfo.Name;
							BindingFlags bindingAttr = (BindingFlags)(num ^ 1689676516U);
							Binder binder = null;
							num += 580847262U;
							Type[] types = array;
							num *= 980751733U;
							ParameterModifier[] modifiers = null;
							num |= 1474193067U;
							MethodInfo method = type.GetMethod(name, bindingAttr, binder, types, modifiers);
							num >>= 6;
							MethodInfo methodInfo = method;
							num = 1322262511U * num;
							bool flag = methodInfo != null;
							num = 309492403U % num;
							if (flag)
							{
								num /= 416694853U;
								if (num == 1135309338U)
								{
									goto IL_00;
								}
								MethodBase methodBase3 = methodInfo;
								num /= 2017951705U;
								methodBase = methodBase3;
								num += 309492403U;
							}
							num >>= 2;
							if (1826580865U * num == 0U)
							{
								break;
							}
							goto IL_21F;
						}
					}
				}
				IL_21F:
				num ^= 1539053528U;
				Type type2 = null;
				num += 1297701870U;
				this.type_0 = type2;
				num ^= 205907298U;
				IL_244:
				num = 901130888U << (int)num;
				MethodBase methodBase_ = methodBase;
				num = (33168949U & num);
				@class = this.method_31(methodBase_, num - 28445183U != 0U);
				num = (1053447109U | num);
				if (@class == null)
				{
					return;
				}
				num -= 1789423099U;
				if (num > 44374652U)
				{
					break;
				}
				goto IL_1A3;
			}
			GClass6.Class709 class709_ = @class;
			num &= 1134701458U;
			this.method_0(class709_);
			num += 4277809731U;
		}

		// Token: 0x0600360B RID: 13835 RVA: 0x000299EC File Offset: 0x00027BEC
		private void method_81()
		{
			MethodBase methodBase = this.method_1().vmethod_1() as MethodBase;
			uint num = 1331002780U;
			for (;;)
			{
				bool flag = methodBase != null;
				num = 1136343366U >> (int)num;
				if (!flag)
				{
					break;
				}
				num += 1208615101U;
				if (num * 1855981092U == 0U)
				{
					break;
				}
				num = (1872430732U | num);
				MethodBase methodBase_ = methodBase;
				num = 1613710517U >> (int)num;
				bool bool_ = (num ^ 196986U) != 0U;
				num += 1261907271U;
				GClass6.Class709 @class = this.method_31(methodBase_, bool_);
				num = (1388715923U ^ num);
				if (num * 1996247909U != 0U)
				{
					if (@class != null)
					{
						num = 664882094U >> (int)num;
						num = 430708199U >> (int)num;
						GClass6.Class709 class709_ = @class;
						num = 1974958996U << (int)num;
						this.method_0(class709_);
						num += 2809934162U;
					}
					if ((997462277U & num) != 0U)
					{
						return;
					}
				}
			}
			throw new ArgumentException();
		}

		// Token: 0x0600360C RID: 13836 RVA: 0x00029AD8 File Offset: 0x00027CD8
		private void method_82()
		{
			uint num = 282461990U;
			GClass6.Class709 class2;
			do
			{
				num = 2054319965U - num;
				GClass6.Class709 @class = this.method_1();
				num = 1334121018U % num;
				class2 = this.method_32(@class.C38FA951(), num - 1334121018U != 0U);
				num += 1119755251U;
			}
			while ((875064205U ^ num) == 0U);
			while (class2 != null)
			{
				if (num >= 686709572U)
				{
					num = 941043959U + num;
					GClass6.Class709 class709_ = class2;
					num %= 1197301224U;
					this.method_0(class709_);
					num += 1453558489U;
					break;
				}
			}
		}

		// Token: 0x0600360D RID: 13837 RVA: 0x00029B54 File Offset: 0x00027D54
		private void method_83()
		{
			uint num = 1247368369U;
			GClass6.Class709 class3;
			do
			{
				num = (1956737202U | num);
				num = (651128256U | num);
				GClass6.Class709 @class = this.method_1();
				num %= 984639276U;
				int int_ = @class.C38FA951();
				num &= 210240679U;
				GClass6.Class709 class2 = this.method_32(int_, (num ^ 143131778U) != 0U);
				num = 649418877U * num;
				class3 = class2;
				num >>= 31;
			}
			while (805112839U <= num);
			if (class3 == null)
			{
				goto IL_87;
			}
			num = 1977897678U << (int)num;
			IL_68:
			num = 2096841148U + num;
			GClass6.Class709 class709_ = class3;
			num <<= 15;
			this.method_0(class709_);
			num ^= 4088135681U;
			IL_87:
			if (1905883391U - num != 0U)
			{
				return;
			}
			goto IL_68;
		}

		// Token: 0x0600360E RID: 13838 RVA: 0x00029BF4 File Offset: 0x00027DF4
		private void method_84()
		{
			MethodBase methodBase_ = this.method_27(this.method_1().C38FA951());
			uint num = 3622348431U;
			GClass6.Class709 class2;
			do
			{
				GClass6.Class709 @class = this.method_29(methodBase_);
				num &= 1499159381U;
				class2 = @class;
			}
			while (193286872 << (int)num == 0);
			bool flag = class2 != null;
			num = 1208709990U >> (int)num;
			if (flag)
			{
				num -= 657342360U;
				this.method_0(class2);
				num ^= 3646048152U;
			}
		}

		// Token: 0x0600360F RID: 13839 RVA: 0x00029C74 File Offset: 0x00027E74
		private void method_85()
		{
			for (;;)
			{
				IL_00:
				Type type = this.method_26(this.method_1().C38FA951());
				uint num = 1073872964U;
				Type type2 = type;
				GClass6.Class709 class2;
				for (;;)
				{
					IL_1C8:
					GClass6.Class709 @class = this.method_1();
					num = 1321099651U / num;
					class2 = @class;
					num += 1804156812U;
					for (;;)
					{
						Type type3 = type2;
						num = (300956307U & num);
						if (type3.IsGenericType)
						{
							if ((68297773U & num) != 0U)
							{
								goto IL_114;
							}
							goto IL_00;
						}
						IL_148:
						num = (910765128U | num);
						if (!type2.IsValueType)
						{
							break;
						}
						FieldInfo[] fields = type2.GetFields((BindingFlags)(num ^ 935935677U));
						int num2 = (int)(num + 3359031607U);
						if (1591623079U / num == 0U)
						{
							goto IL_1C8;
						}
						for (;;)
						{
							num >>= 16;
							int num3 = num2;
							num %= 1596263657U;
							FieldInfo[] array = fields;
							num %= 1026709606U;
							int num4 = array.Length;
							num = 1128493244U % num;
							int num5 = num4;
							num = (1462400754U ^ num);
							if (num3 >= num5)
							{
								break;
							}
							FieldInfo fieldInfo = fields[num2];
							FieldInfo fieldInfo2 = fieldInfo;
							object obj = class2.vmethod_1();
							num = 5U;
							object value;
							if (!fieldInfo.FieldType.IsValueType)
							{
								num = (1108114455U ^ num);
								value = null;
							}
							else
							{
								FieldInfo fieldInfo3 = fieldInfo;
								num = (1816480312U & num);
								Type fieldType = fieldInfo3.FieldType;
								num ^= 276714213U;
								value = Activator.CreateInstance(fieldType);
								num += 831400237U;
							}
							fieldInfo2.SetValue(obj, value);
							num = 53955183U * num;
							if (num > 1408843232U)
							{
								goto IL_114;
							}
							int num6 = num2;
							int num7 = (int)(num - 488964045U);
							num = 1333011800U - num;
							int num8 = num6 + num7;
							num = 142220065U * num;
							num2 = num8;
							num += 786160127U;
						}
						if (num << 8 != 0U)
						{
							return;
						}
						continue;
						IL_114:
						Type type4 = type2;
						num = 257111978U % num;
						Type genericTypeDefinition = type4.GetGenericTypeDefinition();
						num = (1539773664U ^ num);
						RuntimeTypeHandle handle = typeof(Nullable<>).TypeHandle;
						num |= 1662529429U;
						Type typeFromHandle = Type.GetTypeFromHandle(handle);
						num ^= 2048902484U;
						if (genericTypeDefinition != typeFromHandle)
						{
							goto IL_148;
						}
						if (num <= 1519735505U)
						{
							goto Block_7;
						}
					}
					num = 1314136533U >> (int)num;
					if (num << 12 == 0U)
					{
						goto IL_00;
					}
					GClass6.Class709 class3 = class2;
					object object_ = null;
					num %= 1285254636U;
					class3.vmethod_2(object_);
					if (2001537324U + num != 0U)
					{
						return;
					}
				}
				Block_7:
				class2.vmethod_2(null);
				if (1604350718U % num != 0U)
				{
					return;
				}
			}
		}

		// Token: 0x06003610 RID: 13840 RVA: 0x00029E7C File Offset: 0x0002807C
		private void method_86()
		{
			int int_ = this.method_1().C38FA951();
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(new GClass6.Class711(this.method_19(class709_2, class709_, false, int_)));
		}

		// Token: 0x06003611 RID: 13841 RVA: 0x00029EB8 File Offset: 0x000280B8
		private void method_87()
		{
			int int_ = this.method_1().C38FA951();
			GClass6.Class709 class709_ = this.method_1();
			GClass6.Class709 class709_2 = this.method_1();
			this.method_0(new GClass6.Class711(this.method_19(class709_2, class709_, true, int_)));
		}

		// Token: 0x06003612 RID: 13842 RVA: 0x00029EF4 File Offset: 0x000280F4
		private void method_88()
		{
			Type type = this.method_26(this.method_1().C38FA951());
			uint num = 2U;
			Type type2 = type;
			do
			{
				Type elementType = type2;
				num |= 239225606U;
				GClass6.Class709 @class = this.method_1();
				num = 63207803U >> (int)num;
				int length = @class.C38FA951();
				num = 57350710U % num;
				GClass6.Class709 class709_ = new GClass6.Class719(Array.CreateInstance(elementType, length));
				num = 1222865298U % num;
				this.method_0(class709_);
			}
			while ((num ^ 2093763204U) == 0U);
		}

		// Token: 0x06003613 RID: 13843 RVA: 0x00029F68 File Offset: 0x00028168
		private void method_89()
		{
			uint num = 529743623U;
			for (;;)
			{
				num = (1396444530U ^ num);
				int int_ = this.method_1().C38FA951();
				num <<= 26;
				Type type = this.method_26(int_);
				num = 556804461U - num;
				if ((1265204879U ^ num) != 0U)
				{
					GClass6.Class709 class2;
					do
					{
						GClass6.Class709 @class = this.method_1();
						num = 1139160144U * num;
						class2 = @class;
					}
					while (num <= 1872647897U);
					num *= 1827538791U;
					GClass6.Class709 class3 = this.method_1();
					num -= 887364182U;
					GClass6.Class709 class4 = class3;
					num = 1881164986U + num;
					if ((1951075347U & num) != 0U)
					{
						GClass6.Class709 class5 = this.method_1();
						num >>= 13;
						object obj = class5.vmethod_1();
						num *= 1822120772U;
						Array array = obj as Array;
						num = (1060329352U ^ num);
						Array array2 = array;
						num = 1922530333U % num;
						if (array2 == null)
						{
							if (658769342U <= num)
							{
								break;
							}
						}
						else
						{
							Array array3 = array2;
							num = 630027057U * num;
							num = (2092643854U | num);
							object object_ = class2;
							num += 130687983U;
							Type type_ = type;
							num = (1751133998U & num);
							object object_2 = this.method_24(object_, type_);
							num &= 1679168386U;
							object obj2 = array2;
							num *= 1750689058U;
							Type elementType = obj2.GetType().GetElementType();
							num <<= 8;
							GClass6.Class709 class6 = this.method_24(object_2, elementType);
							num = 1506293068U + num;
							object value = class6.vmethod_1();
							num = (465726329U & num);
							int index = class4.C38FA951();
							num /= 949289278U;
							array3.SetValue(value, index);
							if (num <= 2109278596U)
							{
								return;
							}
						}
					}
				}
			}
			throw new ArgumentException();
		}

		// Token: 0x06003614 RID: 13844 RVA: 0x0002A0C8 File Offset: 0x000282C8
		private void method_90()
		{
			uint num;
			for (;;)
			{
				Type type = this.method_26(this.method_1().C38FA951());
				GClass6.Class709 @class = this.method_1();
				Array array = this.method_1().vmethod_1() as Array;
				num = 2449473536U;
				if (array == null)
				{
					goto IL_92;
				}
				num <<= 13;
				if (259751035U >= num)
				{
					num = (2106425000U ^ num);
					object value = array.GetValue(@class.C38FA951());
					Type type_ = type;
					num /= 164768478U;
					this.method_0(this.method_24(value, type_));
					if ((num ^ 1678185505U) != 0U)
					{
						break;
					}
				}
			}
			return;
			IL_92:
			num = (81597886U ^ num);
			throw new ArgumentException();
		}

		// Token: 0x06003615 RID: 13845 RVA: 0x0002A178 File Offset: 0x00028378
		private void method_91()
		{
			uint num = 892095510U;
			Array array;
			for (;;)
			{
				num <<= 2;
				object obj = this.method_1().vmethod_1();
				num = 1937145466U / num;
				array = (obj as Array);
				if (598886936U * num == 0U)
				{
					if (array != null)
					{
						goto IL_4E;
					}
					num /= 1132623633U;
					if ((684022825U & num) == 0U)
					{
						break;
					}
				}
			}
			throw new ArgumentException();
			IL_4E:
			num = 1905223050U + num;
			Array array2 = array;
			num = (498416767U & num);
			GClass6.Class709 class709_ = new GClass6.Class711(array2.Length);
			num = 1626685427U % num;
			this.method_0(class709_);
		}

		// Token: 0x06003616 RID: 13846 RVA: 0x0002A1FC File Offset: 0x000283FC
		private void method_92()
		{
			GClass6.Class709 @class = this.method_1();
			uint num = 1214136265U;
			Array array;
			for (;;)
			{
				num %= 1321623399U;
				array = (this.method_1().vmethod_1() as Array);
				num *= 796348438U;
				if (array != null)
				{
					break;
				}
				num *= 256978782U;
				if ((num & 439446552U) != 0U)
				{
					goto IL_46;
				}
			}
			do
			{
				this.method_0(new GClass6.Class724(array, @class.C38FA951()));
			}
			while (num * 126573609U == 0U);
			return;
			IL_46:
			throw new ArgumentException();
		}

		// Token: 0x06003617 RID: 13847 RVA: 0x0002A270 File Offset: 0x00028470
		private void method_93()
		{
			this.method_0(new GClass6.Class726(this.method_27(this.method_1().C38FA951())));
		}

		// Token: 0x06003618 RID: 13848 RVA: 0x0002A29C File Offset: 0x0002849C
		private void method_94()
		{
			uint num = 1298616921U;
			MethodBase methodBase2;
			MethodInfo method;
			for (;;)
			{
				num = 1112632275U << (int)num;
				num = 1619864568U * num;
				GClass6.Class709 @class = this.method_1();
				num &= 1359506321U;
				int int_ = @class.C38FA951();
				num = 160906661U << (int)num;
				MethodBase methodBase = this.method_27(int_);
				num = (908092159U & num);
				methodBase2 = methodBase;
				num >>= 12;
				for (;;)
				{
					num = 2082543232U / num;
					Type type = this.method_1().vmethod_1().GetType();
					num = 1361995343U + num;
					Type type2 = type;
					MemberInfo memberInfo = methodBase2;
					num += 1628985618U;
					Type declaringType = memberInfo.DeclaringType;
					num = 1888556325U / num;
					Type type3 = declaringType;
					if (num > 1804883066U)
					{
						break;
					}
					MethodBase methodBase3 = methodBase2;
					num *= 1652163642U;
					ParameterInfo[] parameters = methodBase3.GetParameters();
					num += 1181951898U;
					Type[] array = new Type[parameters.Length];
					num %= 1612152253U;
					int num2 = (int)(num ^ 1181951898U);
					num = 1454009890U * num;
					int num3 = num2;
					ParameterInfo[] array2 = parameters;
					int num4 = (int)(num - 3734204020U);
					num = 670960597U << (int)num;
					int num5 = num4;
					for (;;)
					{
						int num6 = num5;
						ParameterInfo[] array3 = array2;
						num ^= 1777602512U;
						if (num6 >= array3.Length)
						{
							break;
						}
						ParameterInfo parameterInfo = array2[num5];
						array[num3++] = parameterInfo.ParameterType;
						num5++;
						num = 3176136704U;
					}
					if (num == 1032063665U)
					{
						break;
					}
					for (;;)
					{
						num += 498878535U;
						bool flag = type2 != null;
						num = (1331322913U ^ num);
						num += 460407768U;
						if (!flag)
						{
							goto IL_241;
						}
						num ^= 246882020U;
						Type type4 = type2;
						num %= 485127580U;
						Type type5 = type3;
						num = 61963209U % num;
						if (type4 == type5)
						{
							goto Block_3;
						}
						method = type2.GetMethod(methodBase2.Name, BindingFlags.DeclaredOnly | BindingFlags.Instance | BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.GetProperty | BindingFlags.SetProperty | BindingFlags.ExactBinding, null, CallingConventions.Any, array, null);
						bool flag2 = method != null;
						num = 2078027619U;
						if (flag2)
						{
							MethodInfo baseDefinition = method.GetBaseDefinition();
							MethodBase methodBase4 = methodBase2;
							num ^= 0U;
							if (baseDefinition == methodBase4)
							{
								goto Block_5;
							}
						}
						if (1046689288U > num)
						{
							break;
						}
						type2 = type2.BaseType;
						num += 1489494125U;
					}
				}
			}
			Block_3:
			num ^= 3675916231U;
			goto IL_241;
			Block_5:
			MethodBase methodBase5 = method;
			num = 1557089451U + num;
			methodBase2 = methodBase5;
			IL_241:
			num <<= 8;
			num = (1120149785U & num);
			GClass6.Class709 class709_ = new GClass6.Class726(methodBase2);
			num ^= 799630595U;
			this.method_0(class709_);
		}

		// Token: 0x06003619 RID: 13849 RVA: 0x0002A510 File Offset: 0x00028710
		private void method_95()
		{
			uint num = 1767984638U;
			do
			{
				num |= 1601376381U;
				GClass6.Class709 @class = this.method_1();
				num <<= 15;
				this.int_0 = @class.C38FA951();
			}
			while (929639982U + num == 0U);
		}

		// Token: 0x0600361A RID: 13850 RVA: 0x0002A550 File Offset: 0x00028750
		private void method_96()
		{
			uint num = 2049647313U;
			do
			{
				this.method_1();
			}
			while (1541823458U >= num);
		}

		// Token: 0x0600361B RID: 13851 RVA: 0x0002A574 File Offset: 0x00028774
		private void method_97()
		{
			uint num;
			for (;;)
			{
				Stack<int> stack = this.stack_2;
				int item = this.method_1().C38FA951();
				num = 157891291U;
				stack.Push(item);
				int num2;
				do
				{
					num -= 205476067U;
					num2 = this.method_1().C38FA951();
				}
				while (2049460934U >= num);
				while (1815752580U != num)
				{
					num = 422975868U - num;
					Stack<GClass6.Class739> stack2 = this.stack_1;
					num >>= 0;
					if (stack2.Count != 0)
					{
						num = (888302376U | num);
						int num3 = num2;
						num = 1796897770U - num;
						num = 668940266U * num;
						GClass6.Class739 @class = this.stack_1.Peek();
						num = (1814125414U & num);
						if (num3 > @class.method_1())
						{
							List<GClass6.Class738> list = this.stack_1.Pop().method_4();
							int count = list.Count;
							num = 978269946U;
							int num4 = count;
							for (;;)
							{
								num <<= 13;
								uint num5 = (uint)num4;
								num <<= 16;
								if (num5 <= (num ^ 1073741824U))
								{
									break;
								}
								GClass6.Class738 class2 = list[num4 - 1];
								int num6 = (int)class2.method_0();
								num = 224291424U;
								if (num6 == 2)
								{
									num %= 1192440702U;
									Stack<int> stack3 = this.stack_2;
									num = 2099277887U / num;
									GClass6.Class738 class3 = class2;
									num -= 1237518695U;
									int item2 = class3.method_1();
									num = 267729496U - num;
									stack3.Push(item2);
									num ^= 1424370134U;
								}
								num /= 949570761U;
								int num7 = num4;
								num = 1790460194U << (int)num;
								int num8 = (int)(num ^ 1790460195U);
								num = 1858417108U * num;
								int num9 = num7 - num8;
								num = 819279138U % num;
								num4 = num9;
								num ^= 177865688U;
							}
							num += 3173640696U;
							continue;
						}
						num ^= 1410084256U;
					}
					this.exception_0 = null;
					if (871314650U >= num)
					{
						num &= 42821177U;
						Stack<GClass6.Class710> stack4 = this.stack_0;
						num = 223247094U % num;
						stack4.Clear();
						if (827877577U * num != 0U)
						{
							goto IL_1F8;
						}
					}
				}
			}
			IL_1F8:
			num = 236260374U / num;
			int num10 = this.stack_2.Pop();
			num -= 1976248419U;
			this.int_0 = num10;
		}

		// Token: 0x0600361C RID: 13852 RVA: 0x0002A79C File Offset: 0x0002899C
		private void method_98()
		{
			uint num;
			do
			{
				bool flag = this.exception_0 != null;
				num = 289674632U;
				if (!flag)
				{
					goto IL_3D;
				}
				num %= 1406613489U;
				if ((num & 1605708910U) == 0U)
				{
					return;
				}
				num *= 2011201295U;
				this.method_34(this.exception_0);
			}
			while (num < 1827746498U);
			return;
			IL_3D:
			num = 964176701U + num;
			num *= 992622941U;
			num = 323969815U % num;
			this.int_0 = this.stack_2.Pop();
			if (num == 1350596113U)
			{
				return;
			}
		}

		// Token: 0x0600361D RID: 13853 RVA: 0x0002A818 File Offset: 0x00028A18
		private void method_99()
		{
			uint num;
			for (;;)
			{
				GClass6.Class709 @class = this.method_1();
				num = 1565945526U;
				if (@class.C38FA951() == 0)
				{
					goto IL_DF;
				}
				if (num + 810885926U != 0U)
				{
					do
					{
						Stack<GClass6.Class739> stack = this.stack_1;
						num = 2043766516U >> (int)num;
						stack.Pop();
						num = (968363599U & num);
						do
						{
							Stack<GClass6.Class710> stack2 = this.stack_0;
							num = 382599763U >> (int)num;
							object object_ = this.exception_0;
							num = (1667726184U | num);
							GClass6.Class710 item = new GClass6.Class716(object_);
							num >>= 25;
							stack2.Push(item);
							num %= 1049496625U;
						}
						while (244063518U < num);
						num -= 2029653819U;
						GClass6.Class738 class2 = this.class738_0;
						num = 1135623553U * num;
						this.int_0 = class2.method_1();
					}
					while (1797071611U == num);
					num = 1158942648U >> (int)num;
					this.class738_0 = null;
					if (2019827237U >> (int)num != 0U)
					{
						break;
					}
				}
			}
			return;
			IL_DF:
			num %= 567230086U;
			num ^= 2013222651U;
			Exception exception_ = this.exception_0;
			num = 1726298954U / num;
			this.method_34(exception_);
		}

		// Token: 0x0600361E RID: 13854 RVA: 0x0002A928 File Offset: 0x00028B28
		private void method_100()
		{
			Type type_ = this.method_26(this.method_1().C38FA951());
			this.method_0(new GClass6.Class716(this.method_24(this.method_1(), type_).vmethod_1()));
		}

		// Token: 0x0600361F RID: 13855 RVA: 0x0002A964 File Offset: 0x00028B64
		private void method_101()
		{
			Type type_ = this.method_26(this.method_1().C38FA951());
			this.method_0(this.method_24(this.method_1().vmethod_1(), type_));
		}

		// Token: 0x06003620 RID: 13856 RVA: 0x0002A99C File Offset: 0x00028B9C
		private void method_102()
		{
			uint num = 1712421503U;
			object obj2;
			for (;;)
			{
				IL_29E:
				Type type = this.method_26(this.method_1().C38FA951());
				num = 541870664U + num;
				GClass6.Class709 class2;
				for (;;)
				{
					IL_178:
					GClass6.Class709 @class = this.method_1();
					num = (1231622566U ^ num);
					class2 = @class;
					num &= 1538523217U;
					for (;;)
					{
						object obj = class2.vmethod_1();
						num = 1514670503U / num;
						obj2 = obj;
						num = (6687554U & num);
						if (obj2 == null)
						{
							goto Block_5;
						}
						if (num <= 819007620U)
						{
							Type type2 = type;
							num *= 1531918740U;
							if (type2.IsValueType)
							{
								goto IL_265;
							}
							num = 1291741797U >> (int)num;
							if (num < 1982423642U)
							{
								Type type3 = type;
								num &= 1731675584U;
								TypeCode typeCode = Type.GetTypeCode(type3);
								num += 1888886155U;
								TypeCode typeCode2 = typeCode;
								num /= 1827627789U;
								TypeCode typeCode3 = typeCode2;
								num = (2037016910U ^ num);
								uint num2 = num - 2037016908U;
								num -= 1211388999U;
								switch (typeCode3 - num2)
								{
								case 0:
									if (1547786954U <= num)
									{
										continue;
									}
									goto IL_1A4;
								case 1:
									goto IL_2CF;
								case 2:
									goto IL_E3;
								case 3:
									goto IL_1D5;
								case 4:
									goto IL_2EB;
								case 5:
									goto IL_315;
								case 6:
									goto IL_34C;
								case 7:
									goto IL_212;
								case 8:
									goto IL_222;
								case 9:
									goto IL_3B2;
								case 10:
									goto IL_237;
								case 11:
									goto IL_12C;
								}
								goto Block_4;
							}
							goto IL_178;
						}
					}
					IL_E3:
					if (num + 102908694U == 0U)
					{
						goto IL_29E;
					}
					num = 291647419U >> (int)num;
					sbyte sbyte_ = (sbyte)obj2;
					num %= 1662932554U;
					GClass6.Class709 class709_ = new GClass6.Class735(sbyte_);
					num = 1602047017U * num;
					this.method_0(class709_);
					if ((num & 1932855849U) != 0U)
					{
						return;
					}
					continue;
					IL_12C:
					num = 1958442938U * num;
					if (1629816350U / num == 0U)
					{
						num %= 1026573338U;
						object obj3 = obj2;
						num *= 263194043U;
						double double_ = (double)obj3;
						num = 420494035U << (int)num;
						this.method_0(new GClass6.Class714(double_));
						if (num << 28 == 0U)
						{
							return;
						}
					}
				}
				Block_4:
				if (num < 1732195242U)
				{
					goto Block_9;
				}
				continue;
				IL_1A4:
				object obj4 = obj2;
				num &= 1696558987U;
				GClass6.Class709 class709_2 = new GClass6.Class732((bool)obj4);
				num += 2138515717U;
				this.method_0(class709_2);
				if (num != 1763393031U)
				{
					return;
				}
				continue;
				IL_1D5:
				if (num >= 1766215919U)
				{
					continue;
				}
				num = (701921454U | num);
				GClass6.Class709 class709_3 = new GClass6.Class734((byte)obj2);
				num = 241830719U * num;
				this.method_0(class709_3);
				if (num % 1484524092U != 0U)
				{
					return;
				}
				continue;
				IL_212:
				if (608587962U < num)
				{
					goto Block_13;
				}
				continue;
				IL_222:
				num = 1518234819U * num;
				if (num > 1868571653U)
				{
					goto Block_14;
				}
				continue;
				IL_237:
				num /= 208880446U;
				float float_ = (float)obj2;
				num = 484324442U % num;
				this.method_0(new GClass6.Class713(float_));
				if (num >= 1190872747U)
				{
					continue;
				}
				return;
				IL_265:
				Type type4 = type;
				num = 1863725847U - num;
				if (type4 != obj2.GetType())
				{
					goto Block_16;
				}
				num >>= 10;
				GClass6.Class709 class709_4 = class2;
				num -= 1218924146U;
				this.method_0(class709_4);
				if (291899150U <= num)
				{
					return;
				}
			}
			Block_5:
			num = (335157760U & num);
			goto IL_346;
			Block_9:
			throw new InvalidCastException();
			Block_13:
			num >>= 19;
			uint uint_ = (uint)obj2;
			num = (219229550U ^ num);
			GClass6.Class709 class709_5 = new GClass6.Class736(uint_);
			num += 760832218U;
			this.method_0(class709_5);
			return;
			Block_14:
			object obj5 = obj2;
			num = (366282883U & num);
			GClass6.Class709 class709_6 = new GClass6.Class712((long)obj5);
			num <<= 11;
			this.method_0(class709_6);
			return;
			Block_16:
			num |= 2011170156U;
			throw new InvalidCastException();
			IL_2CF:
			this.method_0(new GClass6.Class733((char)obj2));
			if (209746467U != num)
			{
				return;
			}
			goto IL_346;
			IL_2EB:
			num = (890115474U & num);
			short short_ = (short)obj2;
			num = 975647753U + num;
			GClass6.Class709 class709_7 = new GClass6.Class730(short_);
			num ^= 574966355U;
			this.method_0(class709_7);
			return;
			IL_315:
			if (num << 18 != 0U)
			{
				object obj6 = obj2;
				num = 1439181312U + num;
				GClass6.Class709 class709_8 = new GClass6.Class731((ushort)obj6);
				num = 1665473673U >> (int)num;
				this.method_0(class709_8);
				return;
			}
			IL_346:
			throw new NullReferenceException();
			IL_34C:
			num = 553582613U % num;
			this.method_0(new GClass6.Class711((int)obj2));
			return;
			IL_3B2:
			num += 1867007703U;
			num &= 637954462U;
			object obj7 = obj2;
			num = 199170424U / num;
			ulong ulong_ = (ulong)obj7;
			num = 1503751799U << (int)num;
			this.method_0(new GClass6.Class737(ulong_));
		}

		// Token: 0x06003621 RID: 13857 RVA: 0x0002ADA4 File Offset: 0x00028FA4
		private void method_103()
		{
			this.method_0(new GClass6.Class711(Marshal.ReadInt32(new IntPtr(this.long_0 + (long)((ulong)this.method_1().vmethod_10())))));
		}

		// Token: 0x06003622 RID: 13858 RVA: 0x0002ADDC File Offset: 0x00028FDC
		private void method_104()
		{
			uint num2;
			for (;;)
			{
				IL_00:
				int num = this.method_1().C38FA951();
				num2 = 39307965U;
				ModuleHandle moduleHandle;
				for (;;)
				{
					int num3 = num >> (int)(num2 ^ 39307941U);
					num2 /= 561202222U;
					int num4 = num3;
					if (num2 != 1421373113U)
					{
						int num5 = num4;
						uint num6 = num2 ^ 10U;
						num2 = (990991664U | num2);
						if (num5 <= num6)
						{
							num2 = 1192107194U + num2;
							if (num2 % 893789429U == 0U)
							{
								goto IL_330;
							}
							for (;;)
							{
								int num7 = num4 - (int)(num2 ^ 2183098859U);
								num2 = (790511457U | num2);
								switch (num7)
								{
								case 0:
								case 1:
									goto IL_182;
								case 2:
								case 4:
									goto IL_330;
								case 3:
								{
									if (num2 == 1142120443U)
									{
										continue;
									}
									num2 >>= 18;
									num2 = 544151162U * num2;
									Module module = this.module_0;
									num2 = 44840403U / num2;
									moduleHandle = module.ModuleHandle;
									int fieldToken = num;
									num2 = (1929868826U | num2);
									object object_ = moduleHandle.ResolveFieldHandle(fieldToken);
									num2 -= 864243560U;
									GClass6.Class709 class709_ = new GClass6.Class718(object_);
									num2 = 128604381U - num2;
									this.method_0(class709_);
									if (69034015U >> (int)num2 == 0U)
									{
										continue;
									}
									return;
								}
								case 5:
									goto IL_219;
								}
								break;
							}
							num2 = 1866425100U / num2;
							if (496390941U * num2 != 0U)
							{
								continue;
							}
							uint num8 = (uint)num4;
							num2 = 1960400998U * num2;
							if (num8 == num2 - 4294967286U)
							{
								break;
							}
							if (num2 != 1915109201U)
							{
								goto Block_6;
							}
							continue;
						}
						else
						{
							int num9 = num4;
							num2 = 726687074U << (int)num2;
							uint num10 = num2 + 2728263707U;
							num2 &= 832900074U;
							num2 ^= 3191832555U;
							if (num9 != num10)
							{
								if (2124314614U >= num2)
								{
									goto IL_330;
								}
								int num11 = num4;
								num2 *= 509639626U;
								uint num12 = num2 ^ 3547387973U;
								num2 |= 202789666U;
								num2 ^= 1885971589U;
								if (num11 == num12)
								{
									goto IL_219;
								}
								if (num2 >= 1708732879U)
								{
									goto Block_10;
								}
								continue;
							}
						}
						IL_182:
						if (477832994U > num2)
						{
							goto IL_00;
						}
						num2 ^= 653622984U;
						num2 = (101401706U ^ num2);
						ModuleHandle moduleHandle2 = this.module_0.ModuleHandle;
						num2 = 1417751581U + num2;
						moduleHandle = moduleHandle2;
						int typeToken = num;
						num2 = (960130154U & num2);
						GClass6.Class709 class709_2 = new GClass6.Class718(moduleHandle.ResolveTypeHandle(typeToken));
						num2 += 1165365868U;
						this.method_0(class709_2);
						if (1685138426U / num2 == 0U)
						{
							return;
						}
					}
				}
				num2 = 1740116529U * num2;
				if (961878601U != num2)
				{
					goto Block_13;
				}
				continue;
				IL_219:
				num2 = (1104309050U & num2);
				num2 %= 437661694U;
				moduleHandle = this.module_0.ModuleHandle;
				int methodToken = num;
				num2 = 1726745680U * num2;
				this.method_0(new GClass6.Class718(moduleHandle.ResolveMethodHandle(methodToken)));
				if (num2 > 495288153U)
				{
					return;
				}
			}
			Block_6:
			num2 += 2938077163U;
			goto IL_330;
			Block_10:
			num2 ^= 0U;
			goto IL_330;
			Block_13:
			try
			{
				num2 = 742748230U + num2;
				if (714808614U <= num2)
				{
					do
					{
						num2 *= 557450592U;
						Module module2 = this.module_0;
						num2 /= 565904839U;
						ModuleHandle moduleHandle3 = module2.ModuleHandle;
						num2 -= 747054992U;
						ModuleHandle moduleHandle = moduleHandle3;
						int num;
						int fieldToken2 = num;
						num2 %= 917244187U;
						GClass6.Class709 class709_3 = new GClass6.Class718(moduleHandle.ResolveFieldHandle(fieldToken2));
						num2 = 545862140U / num2;
						this.method_0(class709_3);
					}
					while (num2 >= 800551311U);
				}
				return;
			}
			catch
			{
				int num;
				this.method_0(new GClass6.Class718(this.module_0.ModuleHandle.ResolveMethodHandle(num)));
				return;
			}
			return;
			IL_330:
			if ((num2 ^ 141037244U) != 0U)
			{
				throw new InvalidOperationException();
			}
		}

		// Token: 0x06003623 RID: 13859 RVA: 0x0002B13C File Offset: 0x0002933C
		private void method_105()
		{
			Exception ex = this.method_1().vmethod_1() as Exception;
			if (ex == null)
			{
				throw new ArgumentException();
			}
			throw ex;
		}

		// Token: 0x06003624 RID: 13860 RVA: 0x0002B164 File Offset: 0x00029364
		private void method_106()
		{
			if (this.exception_0 == null)
			{
				throw new InvalidOperationException();
			}
			throw this.exception_0;
		}

		// Token: 0x06003625 RID: 13861 RVA: 0x0002B188 File Offset: 0x00029388
		private void method_107()
		{
			uint num = 1326928505U;
			for (;;)
			{
				GClass6.Class709 @class = this.method_1();
				num = 2011841631U - num;
				Type type = this.method_26(@class.C38FA951());
				num = 1640761943U - num;
				if (num <= 2053771997U)
				{
					GClass6.Class709 class3;
					for (;;)
					{
						GClass6.Class709 class2 = this.method_1();
						num = (1200309120U | num);
						class3 = class2;
						num >>= 29;
						if (1350586187U > num)
						{
							object object_ = class3.vmethod_1();
							Type type_ = type;
							num += 384699033U;
							bool flag = this.method_33(object_, type_);
							num >>= 1;
							if (flag)
							{
								break;
							}
							num /= 207112596U;
							if ((num ^ 272446949U) != 0U)
							{
								goto IL_A5;
							}
						}
					}
					if ((221723974U & num) != 0U)
					{
						num = (1625643714U & num);
						this.method_0(class3);
						if (num < 639833663U)
						{
							return;
						}
					}
				}
			}
			IL_A5:
			throw new InvalidCastException();
		}

		// Token: 0x06003626 RID: 13862 RVA: 0x0002B240 File Offset: 0x00029440
		private void method_108()
		{
			GClass6.Class709 @class;
			uint num;
			for (;;)
			{
				Type type = this.method_26(this.method_1().C38FA951());
				@class = this.method_1();
				object object_ = @class.vmethod_1();
				Type type_ = type;
				num = 968128140U;
				if (this.method_33(object_, type_))
				{
					break;
				}
				if (1501185063U >> (int)num != 0U)
				{
					goto Block_1;
				}
			}
			do
			{
				IL_82:
				num += 1550857472U;
				this.method_0(@class);
			}
			while (num <= 1423145955U);
			return;
			Block_1:
			@class = new GClass6.Class716(null);
			num += 0U;
			goto IL_82;
		}

		// Token: 0x06003627 RID: 13863 RVA: 0x0002B2E8 File Offset: 0x000294E8
		private void method_109()
		{
			uint num;
			GClass6.Class709 class2;
			for (;;)
			{
				GClass6.Class709 @class = this.method_1();
				num = 2953835540U;
				class2 = @class;
				for (;;)
				{
					GClass6.Class709 class3 = class2;
					num &= 2090948162U;
					object obj = class3.vmethod_1();
					num = (487133665U & num);
					if (obj is IConvertible)
					{
						if (590164063U < num)
						{
							continue;
						}
						GClass6.Class709 class4 = class2;
						num = (644299408U | num);
						double num2 = class4.A2E5C9EC();
						num *= 301990320U;
						double num3 = num2;
						num *= 1002833965U;
						if (double.IsNaN(num3))
						{
							goto IL_C8;
						}
						double d = num3;
						num &= 674188584U;
						bool flag = double.IsInfinity(d);
						num = 1504857518U - num;
						if (flag)
						{
							goto Block_4;
						}
					}
					else
					{
						if (2031366796U <= num)
						{
							break;
						}
						double naN = double.NaN;
						num /= 2145877114U;
						class2 = new GClass6.Class714(naN);
						num ^= 967526574U;
					}
					num <<= 22;
					if ((num & 1420059963U) != 0U)
					{
						goto Block_1;
					}
				}
			}
			Block_1:
			num -= 1989673936U;
			this.method_0(class2);
			return;
			Block_4:
			num ^= 2653708206U;
			IL_C8:
			num = (965219332U | num);
			throw new OverflowException();
		}

		// Token: 0x06003628 RID: 13864 RVA: 0x0002B3DC File Offset: 0x000295DC
		private unsafe void method_110()
		{
			IntPtr item = Marshal.AllocHGlobal(this.method_1().vmethod_13());
			this.list_2.Add(item);
			this.method_0(new GClass6.Class716(Pointer.Box(item.ToPointer(), typeof(void*))));
		}

		// Token: 0x06003629 RID: 13865 RVA: 0x0002B428 File Offset: 0x00029628
		private void method_111()
		{
			uint num = 3293995072U;
			using (List<IntPtr>.Enumerator enumerator = this.list_2.GetEnumerator())
			{
				if (num << 7 != 0U)
				{
				}
				for (;;)
				{
					bool flag = enumerator.MoveNext();
					num = 1603614525U % num;
					if (!flag)
					{
						if (1721521843U + num != 0U)
						{
							break;
						}
					}
					else
					{
						Marshal.FreeHGlobal(enumerator.Current);
						num = 3293995072U;
					}
				}
			}
		}

		// Token: 0x0600362A RID: 13866 RVA: 0x0002B4BC File Offset: 0x000296BC
		public object method_112(object[] object_0, int int_1)
		{
			uint num = 1148137350U;
			do
			{
				num = 677516138U % num;
				this.int_0 = int_1;
			}
			while (num <= 146100805U);
			num |= 2016684452U;
			num /= 445867197U;
			this.method_0(new GClass6.Class719(object_0));
			object result;
			try
			{
				for (;;)
				{
					try
					{
						do
						{
							this.dictionary_0[(uint)this.method_3()]();
							num = 356744512U;
							while (this.int_0 != 0)
							{
								if (1394098966U + num != 0U)
								{
									goto IL_91;
								}
							}
						}
						while (917047005U == num);
						break;
						IL_91:
						continue;
					}
					catch (Exception exception_)
					{
						this.method_34(exception_);
						continue;
					}
					break;
				}
				result = this.method_1().vmethod_1();
			}
			finally
			{
				this.method_111();
			}
			return result;
		}

		// Token: 0x0600362B RID: 13867 RVA: 0x0002B5C4 File Offset: 0x000297C4
		// Note: this type is marked as 'beforefieldinit'.
		static GClass6()
		{
			uint num = 430384357U;
			for (;;)
			{
				Dictionary<int, object> dictionary = new Dictionary<int, object>();
				num /= 505118460U;
				GClass6.dictionary_1 = dictionary;
				num -= 136267642U;
				if (num >= 1822239533U)
				{
					GClass6.dictionary_2 = new Dictionary<MethodBase, DynamicMethod>();
					if (325065596U != num)
					{
						break;
					}
				}
			}
		}

		// Token: 0x040000B3 RID: 179
		private readonly Dictionary<uint, GClass6.Delegate0> dictionary_0 = new Dictionary<uint, GClass6.Delegate0>();

		// Token: 0x040000B4 RID: 180
		private readonly Module module_0 = typeof(GClass6).Module;

		// Token: 0x040000B5 RID: 181
		private readonly long long_0;

		// Token: 0x040000B6 RID: 182
		private int int_0;

		// Token: 0x040000B7 RID: 183
		private Type type_0;

		// Token: 0x040000B8 RID: 184
		private Stack<GClass6.Class710> stack_0 = new Stack<GClass6.Class710>();

		// Token: 0x040000B9 RID: 185
		private static readonly Dictionary<int, object> dictionary_1;

		// Token: 0x040000BA RID: 186
		private static readonly Dictionary<MethodBase, DynamicMethod> dictionary_2;

		// Token: 0x040000BB RID: 187
		private List<GClass6.Class709> list_0 = new List<GClass6.Class709>();

		// Token: 0x040000BC RID: 188
		private List<GClass6.Class739> list_1 = new List<GClass6.Class739>();

		// Token: 0x040000BD RID: 189
		private Stack<GClass6.Class739> stack_1 = new Stack<GClass6.Class739>();

		// Token: 0x040000BE RID: 190
		private Stack<int> stack_2 = new Stack<int>();

		// Token: 0x040000BF RID: 191
		private Exception exception_0;

		// Token: 0x040000C0 RID: 192
		private GClass6.Class738 class738_0;

		// Token: 0x040000C1 RID: 193
		private List<IntPtr> list_2 = new List<IntPtr>();

		// Token: 0x020002E2 RID: 738
		private abstract class Class709
		{
			// Token: 0x0600362C RID: 13868
			public abstract GClass6.Class709 vmethod_0();

			// Token: 0x0600362D RID: 13869
			public abstract object vmethod_1();

			// Token: 0x0600362E RID: 13870
			public abstract void vmethod_2(object object_0);

			// Token: 0x0600362F RID: 13871 RVA: 0x0002B614 File Offset: 0x00029814
			public virtual bool vmethod_3()
			{
				return false;
			}

			// Token: 0x06003630 RID: 13872 RVA: 0x0002B624 File Offset: 0x00029824
			public virtual GClass6.Class710 vmethod_4()
			{
				throw new InvalidOperationException();
			}

			// Token: 0x06003631 RID: 13873 RVA: 0x0002B638 File Offset: 0x00029838
			public virtual GClass6.Class709 vmethod_5()
			{
				return this;
			}

			// Token: 0x06003632 RID: 13874 RVA: 0x0002B624 File Offset: 0x00029824
			public virtual Type vmethod_6()
			{
				throw new InvalidOperationException();
			}

			// Token: 0x06003633 RID: 13875 RVA: 0x0002B624 File Offset: 0x00029824
			public virtual TypeCode vmethod_7()
			{
				throw new InvalidOperationException();
			}

			// Token: 0x06003634 RID: 13876 RVA: 0x0002B648 File Offset: 0x00029848
			public virtual bool BDEF602B()
			{
				return Convert.ToBoolean(this.vmethod_1());
			}

			// Token: 0x06003635 RID: 13877 RVA: 0x0002B660 File Offset: 0x00029860
			public virtual sbyte vmethod_8()
			{
				return Convert.ToSByte(this.vmethod_1());
			}

			// Token: 0x06003636 RID: 13878 RVA: 0x0002B678 File Offset: 0x00029878
			public virtual short C734BD78()
			{
				return Convert.ToInt16(this.vmethod_1());
			}

			// Token: 0x06003637 RID: 13879 RVA: 0x0002B690 File Offset: 0x00029890
			public virtual int C38FA951()
			{
				return Convert.ToInt32(this.vmethod_1());
			}

			// Token: 0x06003638 RID: 13880 RVA: 0x0002B6A8 File Offset: 0x000298A8
			public virtual long F4599160()
			{
				return Convert.ToInt64(this.vmethod_1());
			}

			// Token: 0x06003639 RID: 13881 RVA: 0x0002B6C0 File Offset: 0x000298C0
			public virtual char DDEBCEDF()
			{
				return Convert.ToChar(this.vmethod_1());
			}

			// Token: 0x0600363A RID: 13882 RVA: 0x0002B6D8 File Offset: 0x000298D8
			public virtual byte vmethod_9()
			{
				return Convert.ToByte(this.vmethod_1());
			}

			// Token: 0x0600363B RID: 13883 RVA: 0x0002B6F0 File Offset: 0x000298F0
			public virtual ushort FC2B6FC2()
			{
				return Convert.ToUInt16(this.vmethod_1());
			}

			// Token: 0x0600363C RID: 13884 RVA: 0x0002B708 File Offset: 0x00029908
			public virtual uint vmethod_10()
			{
				return Convert.ToUInt32(this.vmethod_1());
			}

			// Token: 0x0600363D RID: 13885 RVA: 0x0002B720 File Offset: 0x00029920
			public virtual ulong vmethod_11()
			{
				return Convert.ToUInt64(this.vmethod_1());
			}

			// Token: 0x0600363E RID: 13886 RVA: 0x0002B738 File Offset: 0x00029938
			public virtual float vmethod_12()
			{
				return Convert.ToSingle(this.vmethod_1());
			}

			// Token: 0x0600363F RID: 13887 RVA: 0x0002B750 File Offset: 0x00029950
			public virtual double A2E5C9EC()
			{
				return Convert.ToDouble(this.vmethod_1());
			}

			// Token: 0x06003640 RID: 13888 RVA: 0x0002B768 File Offset: 0x00029968
			public override string ToString()
			{
				object obj = this.vmethod_1();
				uint num = 1766411068U;
				if (obj != null && num + 1407547928U != 0U)
				{
					return Convert.ToString(obj);
				}
				return null;
			}

			// Token: 0x06003641 RID: 13889 RVA: 0x0002B624 File Offset: 0x00029824
			public virtual IntPtr vmethod_13()
			{
				throw new InvalidOperationException();
			}

			// Token: 0x06003642 RID: 13890 RVA: 0x0002B624 File Offset: 0x00029824
			public virtual UIntPtr vmethod_14()
			{
				throw new InvalidOperationException();
			}

			// Token: 0x06003643 RID: 13891 RVA: 0x0002B624 File Offset: 0x00029824
			public virtual object E4B72359(Type type_0, bool bool_0)
			{
				throw new InvalidOperationException();
			}

			// Token: 0x06003644 RID: 13892 RVA: 0x000208C0 File Offset: 0x0001EAC0
			protected Class709()
			{
				uint num = 1951662569U;
				do
				{
					base..ctor();
				}
				while (1075202719U >= num);
			}
		}

		// Token: 0x020002E3 RID: 739
		private abstract class Class710 : GClass6.Class709
		{
			// Token: 0x06003645 RID: 13893 RVA: 0x0002B638 File Offset: 0x00029838
			public override GClass6.Class710 vmethod_4()
			{
				return this;
			}

			// Token: 0x06003646 RID: 13894 RVA: 0x0002B614 File Offset: 0x00029814
			public override TypeCode vmethod_7()
			{
				return TypeCode.Empty;
			}

			// Token: 0x06003647 RID: 13895 RVA: 0x0002B7A4 File Offset: 0x000299A4
			protected Class710()
			{
				uint num = 640843366U;
				do
				{
					num -= 1745619812U;
					base..ctor();
				}
				while (1637235562U == num);
			}
		}

		// Token: 0x020002E4 RID: 740
		private sealed class Class711 : GClass6.Class710
		{
			// Token: 0x06003648 RID: 13896 RVA: 0x0002B7D0 File Offset: 0x000299D0
			public Class711(int int_1)
			{
				uint num = 1903119584U;
				do
				{
					base..ctor();
				}
				while ((num & 669260911U) == 0U);
				num /= 864362496U;
				this.int_0 = int_1;
			}

			// Token: 0x06003649 RID: 13897 RVA: 0x0002B804 File Offset: 0x00029A04
			public override Type vmethod_6()
			{
				return typeof(int);
			}

			// Token: 0x0600364A RID: 13898 RVA: 0x0002B81C File Offset: 0x00029A1C
			public override TypeCode vmethod_7()
			{
				return TypeCode.Int32;
			}

			// Token: 0x0600364B RID: 13899 RVA: 0x0002B82C File Offset: 0x00029A2C
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class711(this.int_0);
			}

			// Token: 0x0600364C RID: 13900 RVA: 0x0002B844 File Offset: 0x00029A44
			public override object vmethod_1()
			{
				return this.int_0;
			}

			// Token: 0x0600364D RID: 13901 RVA: 0x0002B85C File Offset: 0x00029A5C
			public override void vmethod_2(object object_0)
			{
				uint num = 234762786U;
				do
				{
					int num2 = Convert.ToInt32(object_0);
					num |= 1160012298U;
					this.int_0 = num2;
				}
				while (num <= 890525176U);
			}

			// Token: 0x0600364E RID: 13902 RVA: 0x0002B88C File Offset: 0x00029A8C
			public override bool BDEF602B()
			{
				return this.int_0 != 0;
			}

			// Token: 0x0600364F RID: 13903 RVA: 0x0002B8A4 File Offset: 0x00029AA4
			public override sbyte vmethod_8()
			{
				return (sbyte)this.int_0;
			}

			// Token: 0x06003650 RID: 13904 RVA: 0x0002B8B8 File Offset: 0x00029AB8
			public override short C734BD78()
			{
				return (short)this.int_0;
			}

			// Token: 0x06003651 RID: 13905 RVA: 0x0002B8CC File Offset: 0x00029ACC
			public override int C38FA951()
			{
				return this.int_0;
			}

			// Token: 0x06003652 RID: 13906 RVA: 0x0002B8E0 File Offset: 0x00029AE0
			public override long F4599160()
			{
				return (long)this.int_0;
			}

			// Token: 0x06003653 RID: 13907 RVA: 0x0002B8F4 File Offset: 0x00029AF4
			public override char DDEBCEDF()
			{
				return (char)this.int_0;
			}

			// Token: 0x06003654 RID: 13908 RVA: 0x0002B908 File Offset: 0x00029B08
			public override byte vmethod_9()
			{
				return (byte)this.int_0;
			}

			// Token: 0x06003655 RID: 13909 RVA: 0x0002B8F4 File Offset: 0x00029AF4
			public override ushort FC2B6FC2()
			{
				return (ushort)this.int_0;
			}

			// Token: 0x06003656 RID: 13910 RVA: 0x0002B8CC File Offset: 0x00029ACC
			public override uint vmethod_10()
			{
				return (uint)this.int_0;
			}

			// Token: 0x06003657 RID: 13911 RVA: 0x0002B91C File Offset: 0x00029B1C
			public override ulong vmethod_11()
			{
				return (ulong)this.int_0;
			}

			// Token: 0x06003658 RID: 13912 RVA: 0x0002B930 File Offset: 0x00029B30
			public override float vmethod_12()
			{
				return (float)this.int_0;
			}

			// Token: 0x06003659 RID: 13913 RVA: 0x0002B944 File Offset: 0x00029B44
			public override double A2E5C9EC()
			{
				return (double)this.int_0;
			}

			// Token: 0x0600365A RID: 13914 RVA: 0x0002B958 File Offset: 0x00029B58
			public override IntPtr vmethod_13()
			{
				return new IntPtr(this.int_0);
			}

			// Token: 0x0600365B RID: 13915 RVA: 0x0002B970 File Offset: 0x00029B70
			public override UIntPtr vmethod_14()
			{
				return new UIntPtr((uint)this.int_0);
			}

			// Token: 0x0600365C RID: 13916 RVA: 0x0002B988 File Offset: 0x00029B88
			public override GClass6.Class709 vmethod_5()
			{
				return new GClass6.Class736((uint)this.int_0);
			}

			// Token: 0x0600365D RID: 13917 RVA: 0x0002B9A0 File Offset: 0x00029BA0
			public override object E4B72359(Type type_0, bool bool_0)
			{
				uint num = 1161910659U;
				for (;;)
				{
					num &= 2104711768U;
					if (type_0 == typeof(IntPtr))
					{
						num >>= 0;
						if (num - 2133858075U == 0U)
						{
							continue;
						}
					}
					else
					{
						num /= 1260461751U;
						if (type_0 == typeof(UIntPtr))
						{
							break;
						}
						if (num / 794827223U != 0U)
						{
							continue;
						}
						TypeCode typeCode = Type.GetTypeCode(type_0);
						num /= 1284865401U;
						TypeCode typeCode2 = typeCode;
						num = 1105007416U << (int)num;
						if (num + 638741908U != 0U)
						{
							uint num2 = (uint)typeCode2;
							num = 936469879U >> (int)num;
							switch (num2 - (num - 50U))
							{
							case 0U:
								if (392243336U == num)
								{
									goto IL_182;
								}
								if (!bool_0)
								{
									num = 358297796U + num;
									if (num % 1220611813U != 0U)
									{
										goto Block_7;
									}
									continue;
								}
								else
								{
									if (356792812U > num)
									{
										goto Block_8;
									}
									continue;
								}
								break;
							case 1U:
								if (num == 632717074U)
								{
									continue;
								}
								if (!bool_0)
								{
									num = 1487883520U % num;
									if ((num ^ 1295856608U) != 0U)
									{
										goto Block_11;
									}
									goto IL_182;
								}
								else
								{
									num /= 270143391U;
									if (num < 2000764776U)
									{
										goto Block_12;
									}
									goto IL_182;
								}
								break;
							case 2U:
								goto IL_2FE;
							case 3U:
								goto IL_338;
							case 4U:
								num ^= 339376173U;
								if (249986551U == num)
								{
									goto IL_58B;
								}
								if (!bool_0)
								{
									if (num + 140986128U != 0U)
									{
										goto Block_15;
									}
									continue;
								}
								else
								{
									num %= 354615871U;
									if (36470109U < num)
									{
										goto Block_16;
									}
									continue;
								}
								break;
							case 5U:
								num = 730744972U << (int)num;
								if (350576049U - num == 0U)
								{
									goto IL_182;
								}
								if (bool_0)
								{
									goto IL_404;
								}
								if (num < 1996389400U)
								{
									goto Block_19;
								}
								continue;
							case 6U:
								num = 927743244U / num;
								if (bool_0)
								{
									goto IL_4BA;
								}
								if (696003550U > num)
								{
									goto Block_23;
								}
								continue;
							case 7U:
								num = (31551614U | num);
								if (num > 1081369790U)
								{
									continue;
								}
								num = (1361785440U | num);
								if (bool_0)
								{
									goto IL_4F8;
								}
								num %= 1147539556U;
								if (788938123U != num)
								{
									goto Block_26;
								}
								continue;
							case 8U:
								goto IL_51C;
							case 9U:
								goto IL_52A;
							}
							goto Block_4;
						}
						continue;
					}
					IL_182:
					int size = IntPtr.Size;
					num >>= 23;
					uint num3 = num ^ 142U;
					num <<= 27;
					if (size == num3)
					{
						goto Block_20;
					}
					if (num != 2023826166U)
					{
						goto Block_21;
					}
				}
				num = (84175581U | num);
				num = 453072339U % num;
				uint value;
				if (!bool_0)
				{
					num -= 1400374096U;
					uint num4 = (uint)this.int_0;
					num &= 27947269U;
					value = num4;
				}
				else
				{
					num ^= 1008228047U;
					value = (uint)this.int_0;
					num += 3257845427U;
				}
				return new UIntPtr(value);
				Block_4:
				num ^= 0U;
				goto IL_51C;
				Block_7:
				sbyte b;
				sbyte b2;
				checked
				{
					b = (sbyte)this.int_0;
					goto IL_2B1;
					Block_8:
					num = (2089382006U ^ num);
					b2 = (sbyte)((uint)this.int_0);
				}
				num *= 1268015105U;
				b = b2;
				num ^= 3798917306U;
				goto IL_2B1;
				Block_11:
				num <<= 3;
				byte b3 = (byte)this.int_0;
				num = 1696299237U / num;
				byte b4 = b3;
				goto IL_2F0;
				Block_12:
				num *= 1852381110U;
				b4 = checked((byte)((uint)this.int_0));
				num ^= 6058211U;
				goto IL_2F0;
				Block_15:
				num = 2110919626U << (int)num;
				int num5 = this.int_0;
				goto IL_3E5;
				Block_16:
				num = 2025670810U + num;
				uint num6 = (uint)this.int_0;
				num %= 1575944617U;
				int num7 = (int)num6;
				num += 1893488232U;
				num5 = num7;
				num += 2283465357U;
				goto IL_3E5;
				Block_19:
				num *= 1662147468U;
				uint num8 = (uint)this.int_0;
				num >>= 3;
				uint num9 = num8;
				goto IL_412;
				Block_20:
				num -= 1454129745U;
				if (188567638U != num)
				{
					goto IL_428;
				}
				goto IL_436;
				Block_21:
				num %= 167970184U;
				long value2;
				if (!bool_0)
				{
					num = 1530472369U - num;
					num = (310661314U ^ num);
					long num10 = (long)this.int_0;
					num = 2068863222U / num;
					value2 = num10;
				}
				else
				{
					value2 = (long)((ulong)this.int_0);
					num ^= 166385993U;
				}
				num += 1685412401U;
				IntPtr intPtr = new IntPtr(value2);
				num = 1251151579U * num;
				return intPtr;
				Block_23:
				long num11 = (long)this.int_0;
				goto IL_4D9;
				Block_26:
				num = 404584775U / num;
				uint num12 = (uint)this.int_0;
				num &= 1052787727U;
				uint num13 = num12;
				goto IL_50E;
				IL_2B1:
				num <<= 12;
				return b;
				IL_2F0:
				num = (421691212U & num);
				return b4;
				IL_2FE:
				short num14;
				if (!bool_0)
				{
					num = (1358048708U | num);
					num14 = checked((short)this.int_0);
				}
				else
				{
					num -= 2081783575U;
					num += 1725243714U;
					num14 = checked((short)((uint)this.int_0));
					num ^= 3125645205U;
				}
				return num14;
				IL_338:
				num %= 863706582U;
				if (num / 1435963292U == 0U)
				{
					ushort num16;
					if (!bool_0)
					{
						num = 843659786U << (int)num;
						num &= 377242354U;
						ushort num15 = (ushort)this.int_0;
						num *= 1840386165U;
						num16 = num15;
					}
					else
					{
						if (num > 1998062895U)
						{
							goto IL_428;
						}
						num &= 1870034072U;
						num16 = checked((ushort)((uint)this.int_0));
						num ^= 3556769808U;
					}
					num *= 2091869533U;
					return num16;
				}
				goto IL_58B;
				IL_3E5:
				return num5;
				IL_404:
				num9 = (uint)this.int_0;
				num += 3271557120U;
				IL_412:
				return num9;
				IL_428:
				num -= 55914208U;
				if (!bool_0)
				{
					goto IL_58B;
				}
				IL_436:
				num ^= 1650084121U;
				uint num17 = (uint)this.int_0;
				num = (1917601896U & num);
				int value3 = checked((int)num17);
				num += 3824832143U;
				goto IL_591;
				IL_4BA:
				num /= 772039693U;
				ulong num18 = (ulong)this.int_0;
				num = (1082273103U | num);
				num11 = (long)num18;
				num += 3229562251U;
				IL_4D9:
				return num11;
				IL_4F8:
				num += 931748968U;
				num13 = (uint)this.int_0;
				num += 1988833562U;
				IL_50E:
				num = 1555768014U + num;
				return num13;
				IL_51C:
				num = 166355913U - num;
				throw new ArgumentException();
				IL_52A:
				num <<= 28;
				double num19;
				if (!bool_0)
				{
					num = 441479021U >> (int)num;
					num += 24930670U;
					num19 = (double)this.int_0;
				}
				else
				{
					if ((num & 135687442U) != 0U)
					{
						goto IL_58B;
					}
					num /= 500383146U;
					double num20 = this.int_0;
					num >>= 16;
					num19 = num20;
					num += 466409691U;
				}
				num = 208558112U + num;
				return num19;
				IL_58B:
				value3 = this.int_0;
				IL_591:
				return new IntPtr(value3);
			}

			// Token: 0x040000C2 RID: 194
			private int int_0;
		}

		// Token: 0x020002E5 RID: 741
		private sealed class Class712 : GClass6.Class710
		{
			// Token: 0x0600365E RID: 13918 RVA: 0x0002BF48 File Offset: 0x0002A148
			public Class712(long long_1)
			{
				uint num = 640168958U;
				do
				{
					this.long_0 = long_1;
				}
				while (679756888U < num);
			}

			// Token: 0x0600365F RID: 13919 RVA: 0x0002BF78 File Offset: 0x0002A178
			public override Type vmethod_6()
			{
				return typeof(long);
			}

			// Token: 0x06003660 RID: 13920 RVA: 0x0002BF90 File Offset: 0x0002A190
			public override TypeCode vmethod_7()
			{
				return TypeCode.Int64;
			}

			// Token: 0x06003661 RID: 13921 RVA: 0x0002BFA0 File Offset: 0x0002A1A0
			public override GClass6.Class709 vmethod_5()
			{
				return new GClass6.Class737((ulong)this.long_0);
			}

			// Token: 0x06003662 RID: 13922 RVA: 0x0002BFB8 File Offset: 0x0002A1B8
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class712(this.long_0);
			}

			// Token: 0x06003663 RID: 13923 RVA: 0x0002BFD0 File Offset: 0x0002A1D0
			public override object vmethod_1()
			{
				return this.long_0;
			}

			// Token: 0x06003664 RID: 13924 RVA: 0x0002BFE8 File Offset: 0x0002A1E8
			public override void vmethod_2(object object_0)
			{
				uint num = 1657669194U;
				do
				{
					num = 687104003U * num;
					long num2 = Convert.ToInt64(object_0);
					num = 947996312U * num;
					this.long_0 = num2;
				}
				while (num > 1599480294U);
			}

			// Token: 0x06003665 RID: 13925 RVA: 0x0002C020 File Offset: 0x0002A220
			public override bool BDEF602B()
			{
				return this.long_0 != 0L;
			}

			// Token: 0x06003666 RID: 13926 RVA: 0x0002C040 File Offset: 0x0002A240
			public override char DDEBCEDF()
			{
				return (char)this.long_0;
			}

			// Token: 0x06003667 RID: 13927 RVA: 0x0002C054 File Offset: 0x0002A254
			public override byte vmethod_9()
			{
				return (byte)this.long_0;
			}

			// Token: 0x06003668 RID: 13928 RVA: 0x0002C068 File Offset: 0x0002A268
			public override sbyte vmethod_8()
			{
				return (sbyte)this.long_0;
			}

			// Token: 0x06003669 RID: 13929 RVA: 0x0002C07C File Offset: 0x0002A27C
			public override short C734BD78()
			{
				return (short)this.long_0;
			}

			// Token: 0x0600366A RID: 13930 RVA: 0x0002C090 File Offset: 0x0002A290
			public override int C38FA951()
			{
				return (int)this.long_0;
			}

			// Token: 0x0600366B RID: 13931 RVA: 0x0002C0A4 File Offset: 0x0002A2A4
			public override long F4599160()
			{
				return this.long_0;
			}

			// Token: 0x0600366C RID: 13932 RVA: 0x0002C040 File Offset: 0x0002A240
			public override ushort FC2B6FC2()
			{
				return (ushort)this.long_0;
			}

			// Token: 0x0600366D RID: 13933 RVA: 0x0002C0B8 File Offset: 0x0002A2B8
			public override uint vmethod_10()
			{
				return (uint)this.long_0;
			}

			// Token: 0x0600366E RID: 13934 RVA: 0x0002C0A4 File Offset: 0x0002A2A4
			public override ulong vmethod_11()
			{
				return (ulong)this.long_0;
			}

			// Token: 0x0600366F RID: 13935 RVA: 0x0002C0CC File Offset: 0x0002A2CC
			public override float vmethod_12()
			{
				return (float)this.long_0;
			}

			// Token: 0x06003670 RID: 13936 RVA: 0x0002C0E0 File Offset: 0x0002A2E0
			public override double A2E5C9EC()
			{
				return (double)this.long_0;
			}

			// Token: 0x06003671 RID: 13937 RVA: 0x0002C0F4 File Offset: 0x0002A2F4
			public override IntPtr vmethod_13()
			{
				int size = IntPtr.Size;
				int num = 4;
				uint num2 = 470812314U;
				long value;
				if (size == num)
				{
					num2 >>= 8;
					if (1023019257U % num2 != 0U)
					{
						value = (long)((int)this.long_0);
						num2 += 1035756462U;
						goto IL_4D;
					}
				}
				num2 = 566783258U + num2;
				value = this.long_0;
				IL_4D:
				return new IntPtr(value);
			}

			// Token: 0x06003672 RID: 13938 RVA: 0x0002C154 File Offset: 0x0002A354
			public override UIntPtr vmethod_14()
			{
				uint num;
				ulong value;
				if (UIntPtr.Size != 4)
				{
					num = 1447324045U;
					value = (ulong)this.long_0;
				}
				else
				{
					value = (ulong)((uint)this.long_0);
					num = 1447324045U;
				}
				num = 1931047871U << (int)num;
				return new UIntPtr(value);
			}

			// Token: 0x06003673 RID: 13939 RVA: 0x0002C1AC File Offset: 0x0002A3AC
			public override object E4B72359(Type type_0, bool bool_0)
			{
				uint num = 409033873U;
				while (type_0 != typeof(IntPtr))
				{
					if (type_0 != typeof(UIntPtr))
					{
						TypeCode typeCode = Type.GetTypeCode(type_0);
						if (num <= 837048846U)
						{
							switch (typeCode - (TypeCode)(num ^ 409033876U))
							{
							case 0:
							{
								num = 1743857554U >> (int)num;
								num -= 1807031935U;
								sbyte b;
								if (!bool_0)
								{
									if (73951873U * num == 0U)
									{
										continue;
									}
									b = checked((sbyte)this.long_0);
								}
								else
								{
									num ^= 1419851746U;
									if ((935939406U ^ num) == 0U)
									{
										continue;
									}
									num /= 67141157U;
									ulong num2 = (ulong)this.long_0;
									num = 873859028U << (int)num;
									b = checked((sbyte)num2);
									num += 2356614521U;
								}
								return b;
							}
							case 1:
								num = 107631440U / num;
								if ((440343732U ^ num) != 0U)
								{
									num -= 1250236763U;
									byte b3;
									if (bool_0)
									{
										num += 1955489717U;
										if (num - 1315653634U == 0U)
										{
											continue;
										}
										ulong num3 = (ulong)this.long_0;
										num += 500641614U;
										byte b2 = (byte)num3;
										num = (1219909070U ^ num);
										b3 = b2;
										num += 3894726279U;
									}
									else
									{
										num = (1192260684U | num);
										b3 = checked((byte)this.long_0);
									}
									return b3;
								}
								continue;
							case 2:
							{
								num -= 1279721892U;
								short num4;
								if (!bool_0)
								{
									num = (519777989U | num);
									if (num < 1865495175U)
									{
										continue;
									}
									num &= 1067479160U;
									num4 = checked((short)this.long_0);
								}
								else
								{
									num = 1182100925U + num;
									num4 = checked((short)((ulong)this.long_0));
									num ^= 204454082U;
								}
								num /= 2001862752U;
								return num4;
							}
							case 3:
								if (num / 1385189173U == 0U)
								{
									ushort num6;
									if (bool_0)
									{
										if (2115048613U * num == 0U)
										{
											continue;
										}
										num = (344673535U & num);
										uint num5 = (uint)this.long_0;
										num <<= 18;
										num6 = checked((ushort)num5);
										num ^= 369360896U;
									}
									else
									{
										num <<= 22;
										num6 = checked((ushort)this.long_0);
									}
									num = 1708161337U / num;
									return num6;
								}
								goto IL_2B4;
							case 4:
								if (num / 521487374U == 0U)
								{
									int num7;
									if (!bool_0)
									{
										if ((1223566340U & num) == 0U)
										{
											continue;
										}
										num7 = checked((int)this.long_0);
									}
									else
									{
										num %= 466290259U;
										num = 679745140U * num;
										ulong num8 = (ulong)this.long_0;
										num = 2080466988U / num;
										int num9 = (int)num8;
										num |= 578636109U;
										num7 = num9;
										num ^= 974919132U;
									}
									return num7;
								}
								continue;
							case 5:
							{
								num = 853756769U - num;
								uint num11;
								if (!bool_0)
								{
									if (num + 718557708U == 0U)
									{
										continue;
									}
									uint num10 = (uint)this.long_0;
									num = 888830752U - num;
									num11 = num10;
								}
								else
								{
									num = 557776962U * num;
									num11 = checked((uint)((ulong)this.long_0));
									num += 2949184176U;
								}
								num = 1012543980U % num;
								return num11;
							}
							case 6:
								if (num / 1938298557U == 0U)
								{
									long num12;
									if (!bool_0)
									{
										if ((1589664952U ^ num) == 0U)
										{
											goto IL_467;
										}
										num = 1825257787U + num;
										num12 = this.long_0;
									}
									else
									{
										num = 93665197U << (int)num;
										num = 146932598U << (int)num;
										ulong num13 = (ulong)this.long_0;
										num = 877802104U >> (int)num;
										num12 = checked((long)num13);
										num += 2234291451U;
									}
									return num12;
								}
								continue;
							case 7:
							{
								num /= 97992876U;
								ulong num14;
								if (!bool_0)
								{
									num14 = checked((ulong)this.long_0);
								}
								else
								{
									if (1458784553U + num == 0U)
									{
										goto IL_3EB;
									}
									num14 = (ulong)this.long_0;
									num ^= 0U;
								}
								return num14;
							}
							case 8:
								break;
							case 9:
							{
								num %= 696193271U;
								double num16;
								if (bool_0)
								{
									if ((num ^ 443749727U) == 0U)
									{
										continue;
									}
									long num15 = this.long_0;
									num /= 1861239798U;
									num16 = num15;
									num += 409033873U;
								}
								else
								{
									if ((263346675U ^ num) == 0U)
									{
										goto IL_457;
									}
									num16 = (double)this.long_0;
								}
								return num16;
							}
							default:
								num += 0U;
								break;
							}
							num = (1196784483U ^ num);
							if ((num ^ 187044233U) != 0U)
							{
								throw new ArgumentException();
							}
						}
						IL_454:
						if (bool_0)
						{
							goto IL_467;
						}
						IL_457:
						num = 1634211570U - num;
						long value = this.long_0;
						goto IL_48F;
						IL_467:
						num %= 1027737072U;
						num = (678720532U | num);
						ulong num17 = (ulong)this.long_0;
						num = 457604508U + num;
						value = checked((long)num17);
						num ^= 982805794U;
						IL_48F:
						IntPtr intPtr = new IntPtr(value);
						num = (1778327732U & num);
						return intPtr;
					}
					num <<= 26;
					if (1528973847U < num)
					{
						continue;
					}
					IL_2B4:
					num = 207319205U >> (int)num;
					ulong value2;
					if (bool_0)
					{
						value2 = (ulong)this.long_0;
						num ^= 207319205U;
						goto IL_3F2;
					}
					num /= 2116119236U;
					IL_3EB:
					value2 = checked((ulong)this.long_0);
					IL_3F2:
					num /= 1629245453U;
					UIntPtr uintPtr = new UIntPtr(value2);
					num ^= 2073893066U;
					return uintPtr;
				}
				num = 408252959U << (int)num;
				goto IL_454;
			}

			// Token: 0x040000C3 RID: 195
			private long long_0;
		}

		// Token: 0x020002E6 RID: 742
		private sealed class Class713 : GClass6.Class710
		{
			// Token: 0x06003674 RID: 13940 RVA: 0x0002C65C File Offset: 0x0002A85C
			public Class713(float float_1)
			{
				this.float_0 = float_1;
			}

			// Token: 0x06003675 RID: 13941 RVA: 0x0002C678 File Offset: 0x0002A878
			public override Type vmethod_6()
			{
				return typeof(float);
			}

			// Token: 0x06003676 RID: 13942 RVA: 0x0002C690 File Offset: 0x0002A890
			public override TypeCode vmethod_7()
			{
				return TypeCode.Single;
			}

			// Token: 0x06003677 RID: 13943 RVA: 0x0002C6A0 File Offset: 0x0002A8A0
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class713(this.float_0);
			}

			// Token: 0x06003678 RID: 13944 RVA: 0x0002C6B8 File Offset: 0x0002A8B8
			public override object vmethod_1()
			{
				return this.float_0;
			}

			// Token: 0x06003679 RID: 13945 RVA: 0x0002C6D0 File Offset: 0x0002A8D0
			public override void vmethod_2(object object_0)
			{
				uint num = 1677686840U;
				do
				{
					num = 2069126931U << (int)num;
					num = 301022829U * num;
					this.float_0 = Convert.ToSingle(object_0);
				}
				while ((num ^ 1759130433U) == 0U);
			}

			// Token: 0x0600367A RID: 13946 RVA: 0x0002C710 File Offset: 0x0002A910
			public override bool BDEF602B()
			{
				return Convert.ToBoolean(this.float_0);
			}

			// Token: 0x0600367B RID: 13947 RVA: 0x0002C728 File Offset: 0x0002A928
			public override sbyte vmethod_8()
			{
				return (sbyte)this.float_0;
			}

			// Token: 0x0600367C RID: 13948 RVA: 0x0002C73C File Offset: 0x0002A93C
			public override short C734BD78()
			{
				return (short)this.float_0;
			}

			// Token: 0x0600367D RID: 13949 RVA: 0x0002C750 File Offset: 0x0002A950
			public override int C38FA951()
			{
				return (int)this.float_0;
			}

			// Token: 0x0600367E RID: 13950 RVA: 0x0002C764 File Offset: 0x0002A964
			public override long F4599160()
			{
				return (long)this.float_0;
			}

			// Token: 0x0600367F RID: 13951 RVA: 0x0002C778 File Offset: 0x0002A978
			public override char DDEBCEDF()
			{
				return (char)this.float_0;
			}

			// Token: 0x06003680 RID: 13952 RVA: 0x0002C78C File Offset: 0x0002A98C
			public override byte vmethod_9()
			{
				return (byte)this.float_0;
			}

			// Token: 0x06003681 RID: 13953 RVA: 0x0002C778 File Offset: 0x0002A978
			public override ushort FC2B6FC2()
			{
				return (ushort)this.float_0;
			}

			// Token: 0x06003682 RID: 13954 RVA: 0x0002C7A0 File Offset: 0x0002A9A0
			public override uint vmethod_10()
			{
				return (uint)this.float_0;
			}

			// Token: 0x06003683 RID: 13955 RVA: 0x0002C7B4 File Offset: 0x0002A9B4
			public override ulong vmethod_11()
			{
				return (ulong)this.float_0;
			}

			// Token: 0x06003684 RID: 13956 RVA: 0x0002C7C8 File Offset: 0x0002A9C8
			public override float vmethod_12()
			{
				return this.float_0;
			}

			// Token: 0x06003685 RID: 13957 RVA: 0x0002C7DC File Offset: 0x0002A9DC
			public override double A2E5C9EC()
			{
				return (double)this.float_0;
			}

			// Token: 0x06003686 RID: 13958 RVA: 0x0002C7F0 File Offset: 0x0002A9F0
			public override IntPtr vmethod_13()
			{
				int size = IntPtr.Size;
				uint num = 0U;
				long value;
				if (size != 4)
				{
					num = 980947436U - num;
					num = 841356013U + num;
					value = (long)this.float_0;
				}
				else
				{
					num = 555704083U - num;
					long num2 = (long)((int)this.float_0);
					num %= 777748356U;
					value = num2;
					num += 1266599366U;
				}
				return new IntPtr(value);
			}

			// Token: 0x06003687 RID: 13959 RVA: 0x0002C84C File Offset: 0x0002AA4C
			public override UIntPtr vmethod_14()
			{
				uint num2;
				do
				{
					int size = IntPtr.Size;
					int num = 4;
					num2 = 917133955U;
					if (size != num)
					{
						num2 -= 280834618U;
						if ((1132333022U & num2) != 0U)
						{
							goto Block_2;
						}
					}
				}
				while (964825607U / num2 == 0U);
				ulong num3 = (ulong)((uint)this.float_0);
				num2 /= 1130709400U;
				ulong value = num3;
				num2 ^= 636299337U;
				goto IL_4D;
				Block_2:
				value = (ulong)this.float_0;
				IL_4D:
				num2 = (1382887760U | num2);
				return new UIntPtr(value);
			}

			// Token: 0x06003688 RID: 13960 RVA: 0x0002C8B4 File Offset: 0x0002AAB4
			public override object E4B72359(Type type_0, bool bool_0)
			{
				uint num = 132395920U;
				for (;;)
				{
					num /= 262022090U;
					RuntimeTypeHandle handle = typeof(IntPtr).TypeHandle;
					num = (552824670U & num);
					Type typeFromHandle = Type.GetTypeFromHandle(handle);
					num = 772738898U + num;
					if (type_0 == typeFromHandle)
					{
						goto Block_8;
					}
					num ^= 491208690U;
					if (1701326525U < num)
					{
						break;
					}
					num %= 1893078207U;
					RuntimeTypeHandle handle2 = typeof(UIntPtr).TypeHandle;
					num += 870983391U;
					Type typeFromHandle2 = Type.GetTypeFromHandle(handle2);
					num >>= 5;
					if (type_0 == typeFromHandle2)
					{
						break;
					}
					TypeCode typeCode = Type.GetTypeCode(type_0);
					num = 58359736U >> (int)num;
					if (num <= 1246765460U)
					{
						uint num2 = (uint)typeCode;
						num = 1392071985U << (int)num;
						switch (num2 - (num + 2902895316U))
						{
						case 0U:
							if (num < 54339752U)
							{
								continue;
							}
							if (!bool_0)
							{
								num = 90508191U >> (int)num;
								if (num / 281238055U == 0U)
								{
									goto Block_7;
								}
								continue;
							}
							else
							{
								if (num >= 1589070383U)
								{
									continue;
								}
								goto IL_11A;
							}
							break;
						case 1U:
							goto IL_148;
						case 2U:
							goto IL_155;
						case 3U:
							goto IL_1A5;
						case 4U:
							goto IL_1D8;
						case 5U:
							goto IL_1ED;
						case 6U:
							goto IL_212;
						case 7U:
							goto IL_218;
						}
						goto Block_4;
					}
				}
				goto IL_260;
				Block_4:
				num ^= 0U;
				goto IL_212;
				Block_7:
				sbyte b = (sbyte)this.float_0;
				num = 444429716U * num;
				sbyte b2 = b;
				goto IL_13A;
				Block_8:
				goto IL_246;
				IL_11A:
				uint num3 = (uint)this.float_0;
				num = 958424346U + num;
				sbyte b3 = (sbyte)num3;
				num /= 1158765240U;
				b2 = b3;
				num += 1713826022U;
				IL_13A:
				num /= 1025968666U;
				return b2;
				IL_148:
				return checked((byte)this.float_0);
				IL_155:
				short num5;
				if (!bool_0)
				{
					num &= 51781537U;
					num |= 949575952U;
					short num4 = (short)this.float_0;
					num = 1341196085U << (int)num;
					num5 = num4;
				}
				else
				{
					num |= 1821600583U;
					num5 = checked((short)((uint)this.float_0));
					num += 2406383753U;
				}
				num = 776930023U * num;
				return num5;
				IL_1A5:
				num = (1004613875U | num);
				num = 692214717U >> (int)num;
				ushort num6 = (ushort)this.float_0;
				num = 1860125850U % num;
				ushort num7 = num6;
				num = (1310459354U & num);
				return num7;
				IL_1D8:
				num = (1974814114U | num);
				return checked((int)this.float_0);
				IL_1ED:
				num <<= 24;
				uint num8 = (uint)this.float_0;
				num >>= 31;
				uint num9 = num8;
				num <<= 24;
				return num9;
				IL_212:
				throw new ArgumentException();
				IL_218:
				num |= 587141489U;
				if (782776466U - num != 0U)
				{
					num &= 1903522753U;
					ulong num10 = (ulong)this.float_0;
					num /= 838417918U;
					return num10;
				}
				IL_246:
				num = (507409575U & num);
				return new IntPtr(checked((long)this.float_0));
				IL_260:
				num = 1761231987U + num;
				return new UIntPtr(checked((ulong)this.float_0));
			}

			// Token: 0x040000C4 RID: 196
			private float float_0;
		}

		// Token: 0x020002E7 RID: 743
		private sealed class Class714 : GClass6.Class710
		{
			// Token: 0x06003689 RID: 13961 RVA: 0x0002CB3C File Offset: 0x0002AD3C
			public Class714(double double_1)
			{
				uint num = 942422979U;
				do
				{
					num = 934558784U * num;
					this.double_0 = double_1;
				}
				while (num == 1186927671U);
			}

			// Token: 0x0600368A RID: 13962 RVA: 0x0002CB6C File Offset: 0x0002AD6C
			public override Type vmethod_6()
			{
				return typeof(double);
			}

			// Token: 0x0600368B RID: 13963 RVA: 0x0002CB84 File Offset: 0x0002AD84
			public override TypeCode vmethod_7()
			{
				return TypeCode.Double;
			}

			// Token: 0x0600368C RID: 13964 RVA: 0x0002CB94 File Offset: 0x0002AD94
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class714(this.double_0);
			}

			// Token: 0x0600368D RID: 13965 RVA: 0x0002CBAC File Offset: 0x0002ADAC
			public override object vmethod_1()
			{
				return this.double_0;
			}

			// Token: 0x0600368E RID: 13966 RVA: 0x0002CBC4 File Offset: 0x0002ADC4
			public override void vmethod_2(object object_0)
			{
				this.double_0 = (double)object_0;
			}

			// Token: 0x0600368F RID: 13967 RVA: 0x0002CBE0 File Offset: 0x0002ADE0
			public override bool BDEF602B()
			{
				return Convert.ToBoolean(this.double_0);
			}

			// Token: 0x06003690 RID: 13968 RVA: 0x0002CBF8 File Offset: 0x0002ADF8
			public override sbyte vmethod_8()
			{
				return (sbyte)this.double_0;
			}

			// Token: 0x06003691 RID: 13969 RVA: 0x0002CC0C File Offset: 0x0002AE0C
			public override short C734BD78()
			{
				return (short)this.double_0;
			}

			// Token: 0x06003692 RID: 13970 RVA: 0x0002CC20 File Offset: 0x0002AE20
			public override int C38FA951()
			{
				return (int)this.double_0;
			}

			// Token: 0x06003693 RID: 13971 RVA: 0x0002CC34 File Offset: 0x0002AE34
			public override long F4599160()
			{
				return (long)this.double_0;
			}

			// Token: 0x06003694 RID: 13972 RVA: 0x0002CC48 File Offset: 0x0002AE48
			public override char DDEBCEDF()
			{
				return (char)this.double_0;
			}

			// Token: 0x06003695 RID: 13973 RVA: 0x0002CC5C File Offset: 0x0002AE5C
			public override byte vmethod_9()
			{
				return (byte)this.double_0;
			}

			// Token: 0x06003696 RID: 13974 RVA: 0x0002CC48 File Offset: 0x0002AE48
			public override ushort FC2B6FC2()
			{
				return (ushort)this.double_0;
			}

			// Token: 0x06003697 RID: 13975 RVA: 0x0002CC70 File Offset: 0x0002AE70
			public override uint vmethod_10()
			{
				return (uint)this.double_0;
			}

			// Token: 0x06003698 RID: 13976 RVA: 0x0002CC84 File Offset: 0x0002AE84
			public override ulong vmethod_11()
			{
				return (ulong)this.double_0;
			}

			// Token: 0x06003699 RID: 13977 RVA: 0x0002CC98 File Offset: 0x0002AE98
			public override float vmethod_12()
			{
				return (float)this.double_0;
			}

			// Token: 0x0600369A RID: 13978 RVA: 0x0002CCAC File Offset: 0x0002AEAC
			public override double A2E5C9EC()
			{
				return this.double_0;
			}

			// Token: 0x0600369B RID: 13979 RVA: 0x0002CCC0 File Offset: 0x0002AEC0
			public override IntPtr vmethod_13()
			{
				uint num = 669677302U;
				long value;
				if (IntPtr.Size == 4)
				{
					num = 604716666U >> (int)num;
					if (num >> 21 == 0U)
					{
						int num2 = (int)this.double_0;
						num = 1328444975U << (int)num;
						long num3 = (long)num2;
						num = 1067458613U + num;
						value = num3;
						num += 3222292359U;
						goto IL_5E;
					}
				}
				long num4 = (long)this.double_0;
				num ^= 1379209546U;
				value = num4;
				IL_5E:
				return new IntPtr(value);
			}

			// Token: 0x0600369C RID: 13980 RVA: 0x0002CD30 File Offset: 0x0002AF30
			public override UIntPtr vmethod_14()
			{
				int size = IntPtr.Size;
				uint num = 176621678U;
				ulong value;
				if (size != 4)
				{
					num = 2093762814U >> (int)num;
					num = (222765241U ^ num);
					value = (ulong)this.double_0;
				}
				else
				{
					value = (ulong)((uint)this.double_0);
					num ^= 130145254U;
				}
				num *= 2076014415U;
				return new UIntPtr(value);
			}

			// Token: 0x0600369D RID: 13981 RVA: 0x0002CD88 File Offset: 0x0002AF88
			public override object E4B72359(Type type_0, bool bool_0)
			{
				uint num;
				for (;;)
				{
					Type typeFromHandle = typeof(IntPtr);
					num = 66076123U;
					TypeCode typeCode2;
					if (type_0 == typeFromHandle)
					{
						num *= 291981921U;
						if (119935643 << (int)num != 0)
						{
							goto Block_7;
						}
					}
					else
					{
						num = (15561460U & num);
						if (type_0 == typeof(UIntPtr))
						{
							break;
						}
						num = 209408756U << (int)num;
						num += 1461671186U;
						TypeCode typeCode = Type.GetTypeCode(type_0);
						num = 632713096U * num;
						typeCode2 = typeCode;
						if ((1010790774U ^ num) == 0U)
						{
							continue;
						}
					}
					TypeCode typeCode3 = typeCode2;
					num ^= 287375948U;
					uint num2 = num + 1043137065U;
					num %= 704394930U;
					switch (typeCode3 - num2)
					{
					case 0:
						goto IL_143;
					case 1:
						goto IL_198;
					case 2:
						goto IL_1AD;
					case 3:
						if (num / 226317657U != 0U)
						{
							goto Block_4;
						}
						break;
					case 4:
						num *= 1691370376U;
						if ((num ^ 1943880549U) != 0U)
						{
							goto Block_5;
						}
						break;
					case 5:
						goto IL_26A;
					case 6:
						goto IL_28D;
					case 7:
						goto IL_2C0;
					case 8:
						goto IL_2DD;
					case 9:
						if (num >> 4 != 0U)
						{
							goto Block_6;
						}
						break;
					default:
						if (num != 506735141U)
						{
							goto Block_3;
						}
						break;
					}
				}
				if ((665460690U ^ num) != 0U)
				{
					ulong value = checked((ulong)this.double_0);
					num /= 534478058U;
					return new UIntPtr(value);
				}
				goto IL_200;
				Block_3:
				num ^= 0U;
				goto IL_2DD;
				Block_4:
				num *= 703741904U;
				ushort num3 = (ushort)this.double_0;
				num = 1349714866U >> (int)num;
				ushort num4 = num3;
				num ^= 1714117936U;
				return num4;
				Block_5:
				int num5 = (int)this.double_0;
				num >>= 15;
				return num5;
				Block_6:
				return this.double_0;
				Block_7:
				goto IL_200;
				IL_143:
				num = 638934080U + num;
				sbyte b2;
				if (!bool_0)
				{
					if (num > 1541628630U)
					{
						goto IL_200;
					}
					num |= 565137027U;
					sbyte b = (sbyte)this.double_0;
					num >>= 17;
					b2 = b;
				}
				else
				{
					uint num6 = (uint)this.double_0;
					num >>= 4;
					sbyte b3 = (sbyte)num6;
					num <<= 19;
					b2 = b3;
					num ^= 3215466495U;
				}
				return b2;
				IL_198:
				byte b4 = (byte)this.double_0;
				num = 250938997U / num;
				return b4;
				IL_1AD:
				short num7;
				if (!bool_0)
				{
					num = (1437492042U & num);
					num7 = checked((short)this.double_0);
				}
				else
				{
					num = 925907357U / num;
					if (num % 588325481U == 0U)
					{
						goto IL_200;
					}
					num %= 768238627U;
					uint num8 = (uint)this.double_0;
					num = 1776178304U / num;
					num7 = checked((short)num8);
					num += 3702716608U;
				}
				num = (1045524327U & num);
				return num7;
				IL_200:
				num >>= 19;
				long num9 = (long)this.double_0;
				num = 1697585199U + num;
				long value2 = num9;
				num += 479544812U;
				return new IntPtr(value2);
				IL_26A:
				num = 1355745545U >> (int)num;
				uint num10 = checked((uint)this.double_0);
				num = 2107136885U % num;
				return num10;
				IL_28D:
				num -= 2072935947U;
				num *= 693004066U;
				long num11 = (long)this.double_0;
				num |= 1808423787U;
				long num12 = num11;
				num = 1902604851U >> (int)num;
				return num12;
				IL_2C0:
				num = (581520174U ^ num);
				ulong num13 = checked((ulong)this.double_0);
				num += 562527126U;
				return num13;
				IL_2DD:
				num |= 1210523163U;
				throw new ArgumentException();
			}

			// Token: 0x040000C5 RID: 197
			private double double_0;
		}

		// Token: 0x020002E8 RID: 744
		private sealed class Class715 : GClass6.Class710
		{
			// Token: 0x0600369E RID: 13982 RVA: 0x0002D08C File Offset: 0x0002B28C
			public Class715(string string_1)
			{
				this.string_0 = string_1;
			}

			// Token: 0x0600369F RID: 13983 RVA: 0x0002D0A8 File Offset: 0x0002B2A8
			public override Type vmethod_6()
			{
				return typeof(string);
			}

			// Token: 0x060036A0 RID: 13984 RVA: 0x0002D0C0 File Offset: 0x0002B2C0
			public override TypeCode vmethod_7()
			{
				return TypeCode.Object;
			}

			// Token: 0x060036A1 RID: 13985 RVA: 0x0002D0D0 File Offset: 0x0002B2D0
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class715(this.string_0);
			}

			// Token: 0x060036A2 RID: 13986 RVA: 0x0002D0E8 File Offset: 0x0002B2E8
			public override object vmethod_1()
			{
				return this.string_0;
			}

			// Token: 0x060036A3 RID: 13987 RVA: 0x0002D0FC File Offset: 0x0002B2FC
			public override void vmethod_2(object object_0)
			{
				uint num;
				do
				{
					string text;
					if (object_0 == null)
					{
						num = 737424109U;
						text = null;
					}
					else
					{
						text = Convert.ToString(object_0);
						num = 737424109U;
					}
					num /= 63908348U;
					this.string_0 = text;
				}
				while (num > 1200881686U);
			}

			// Token: 0x060036A4 RID: 13988 RVA: 0x0002D13C File Offset: 0x0002B33C
			public override bool BDEF602B()
			{
				return this.string_0 != null;
			}

			// Token: 0x060036A5 RID: 13989 RVA: 0x0002D0E8 File Offset: 0x0002B2E8
			public override string ToString()
			{
				return this.string_0;
			}

			// Token: 0x040000C6 RID: 198
			private string string_0;
		}

		// Token: 0x020002E9 RID: 745
		private sealed class Class730 : GClass6.Class709
		{
			// Token: 0x060036A6 RID: 13990 RVA: 0x0002D154 File Offset: 0x0002B354
			public Class730(short short_1)
			{
				this.short_0 = short_1;
			}

			// Token: 0x060036A7 RID: 13991 RVA: 0x0002D170 File Offset: 0x0002B370
			public override Type vmethod_6()
			{
				return typeof(short);
			}

			// Token: 0x060036A8 RID: 13992 RVA: 0x0002D188 File Offset: 0x0002B388
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class730(this.short_0);
			}

			// Token: 0x060036A9 RID: 13993 RVA: 0x0002D1A0 File Offset: 0x0002B3A0
			public override object vmethod_1()
			{
				return this.short_0;
			}

			// Token: 0x060036AA RID: 13994 RVA: 0x0002D1B8 File Offset: 0x0002B3B8
			public override void vmethod_2(object object_0)
			{
				this.short_0 = Convert.ToInt16(object_0);
			}

			// Token: 0x060036AB RID: 13995 RVA: 0x0002D1D4 File Offset: 0x0002B3D4
			public override GClass6.Class710 vmethod_4()
			{
				return new GClass6.Class711(this.C38FA951());
			}

			// Token: 0x060036AC RID: 13996 RVA: 0x0002D1EC File Offset: 0x0002B3EC
			public override sbyte vmethod_8()
			{
				return (sbyte)this.short_0;
			}

			// Token: 0x060036AD RID: 13997 RVA: 0x0002D200 File Offset: 0x0002B400
			public override byte vmethod_9()
			{
				return (byte)this.short_0;
			}

			// Token: 0x060036AE RID: 13998 RVA: 0x0002D214 File Offset: 0x0002B414
			public override short C734BD78()
			{
				return this.short_0;
			}

			// Token: 0x060036AF RID: 13999 RVA: 0x0002D228 File Offset: 0x0002B428
			public override ushort FC2B6FC2()
			{
				return (ushort)this.short_0;
			}

			// Token: 0x060036B0 RID: 14000 RVA: 0x0002D214 File Offset: 0x0002B414
			public override int C38FA951()
			{
				return (int)this.short_0;
			}

			// Token: 0x060036B1 RID: 14001 RVA: 0x0002D214 File Offset: 0x0002B414
			public override uint vmethod_10()
			{
				return (uint)this.short_0;
			}

			// Token: 0x040000C7 RID: 199
			private short short_0;
		}

		// Token: 0x020002EA RID: 746
		private sealed class Class731 : GClass6.Class709
		{
			// Token: 0x060036B2 RID: 14002 RVA: 0x0002D23C File Offset: 0x0002B43C
			public Class731(ushort ushort_1)
			{
				this.ushort_0 = ushort_1;
			}

			// Token: 0x060036B3 RID: 14003 RVA: 0x0002D258 File Offset: 0x0002B458
			public override Type vmethod_6()
			{
				return typeof(ushort);
			}

			// Token: 0x060036B4 RID: 14004 RVA: 0x0002D270 File Offset: 0x0002B470
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class731(this.ushort_0);
			}

			// Token: 0x060036B5 RID: 14005 RVA: 0x0002D288 File Offset: 0x0002B488
			public override object vmethod_1()
			{
				return this.ushort_0;
			}

			// Token: 0x060036B6 RID: 14006 RVA: 0x0002D2A0 File Offset: 0x0002B4A0
			public override void vmethod_2(object object_0)
			{
				this.ushort_0 = Convert.ToUInt16(object_0);
			}

			// Token: 0x060036B7 RID: 14007 RVA: 0x0002D1D4 File Offset: 0x0002B3D4
			public override GClass6.Class710 vmethod_4()
			{
				return new GClass6.Class711(this.C38FA951());
			}

			// Token: 0x060036B8 RID: 14008 RVA: 0x0002D2BC File Offset: 0x0002B4BC
			public override sbyte vmethod_8()
			{
				return (sbyte)this.ushort_0;
			}

			// Token: 0x060036B9 RID: 14009 RVA: 0x0002D2D0 File Offset: 0x0002B4D0
			public override byte vmethod_9()
			{
				return (byte)this.ushort_0;
			}

			// Token: 0x060036BA RID: 14010 RVA: 0x0002D2E4 File Offset: 0x0002B4E4
			public override short C734BD78()
			{
				return (short)this.ushort_0;
			}

			// Token: 0x060036BB RID: 14011 RVA: 0x0002D2F8 File Offset: 0x0002B4F8
			public override ushort FC2B6FC2()
			{
				return this.ushort_0;
			}

			// Token: 0x060036BC RID: 14012 RVA: 0x0002D2F8 File Offset: 0x0002B4F8
			public override int C38FA951()
			{
				return (int)this.ushort_0;
			}

			// Token: 0x060036BD RID: 14013 RVA: 0x0002D2F8 File Offset: 0x0002B4F8
			public override uint vmethod_10()
			{
				return (uint)this.ushort_0;
			}

			// Token: 0x040000C8 RID: 200
			private ushort ushort_0;
		}

		// Token: 0x020002EB RID: 747
		private sealed class Class732 : GClass6.Class709
		{
			// Token: 0x060036BE RID: 14014 RVA: 0x0002D30C File Offset: 0x0002B50C
			public Class732(bool bool_1)
			{
				this.bool_0 = bool_1;
			}

			// Token: 0x060036BF RID: 14015 RVA: 0x0002D328 File Offset: 0x0002B528
			public override Type vmethod_6()
			{
				return typeof(bool);
			}

			// Token: 0x060036C0 RID: 14016 RVA: 0x0002D340 File Offset: 0x0002B540
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class732(this.bool_0);
			}

			// Token: 0x060036C1 RID: 14017 RVA: 0x0002D358 File Offset: 0x0002B558
			public override object vmethod_1()
			{
				return this.bool_0;
			}

			// Token: 0x060036C2 RID: 14018 RVA: 0x0002D370 File Offset: 0x0002B570
			public override void vmethod_2(object object_0)
			{
				this.bool_0 = Convert.ToBoolean(object_0);
			}

			// Token: 0x060036C3 RID: 14019 RVA: 0x0002D1D4 File Offset: 0x0002B3D4
			public override GClass6.Class710 vmethod_4()
			{
				return new GClass6.Class711(this.C38FA951());
			}

			// Token: 0x060036C4 RID: 14020 RVA: 0x0002D38C File Offset: 0x0002B58C
			public override int C38FA951()
			{
				uint num = 1408375743U;
				if (!this.bool_0)
				{
					return (int)(num - 1408375743U);
				}
				return (int)(num - 1408375742U);
			}

			// Token: 0x040000C9 RID: 201
			private bool bool_0;
		}

		// Token: 0x020002EC RID: 748
		private sealed class Class733 : GClass6.Class709
		{
			// Token: 0x060036C5 RID: 14021 RVA: 0x0002D3BC File Offset: 0x0002B5BC
			public Class733(char char_1)
			{
				this.char_0 = char_1;
			}

			// Token: 0x060036C6 RID: 14022 RVA: 0x0002D3D8 File Offset: 0x0002B5D8
			public override Type vmethod_6()
			{
				return typeof(char);
			}

			// Token: 0x060036C7 RID: 14023 RVA: 0x0002D3F0 File Offset: 0x0002B5F0
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class733(this.char_0);
			}

			// Token: 0x060036C8 RID: 14024 RVA: 0x0002D408 File Offset: 0x0002B608
			public override object vmethod_1()
			{
				return this.char_0;
			}

			// Token: 0x060036C9 RID: 14025 RVA: 0x0002D420 File Offset: 0x0002B620
			public override void vmethod_2(object object_0)
			{
				this.char_0 = Convert.ToChar(object_0);
			}

			// Token: 0x060036CA RID: 14026 RVA: 0x0002D1D4 File Offset: 0x0002B3D4
			public override GClass6.Class710 vmethod_4()
			{
				return new GClass6.Class711(this.C38FA951());
			}

			// Token: 0x060036CB RID: 14027 RVA: 0x0002D43C File Offset: 0x0002B63C
			public override sbyte vmethod_8()
			{
				return (sbyte)this.char_0;
			}

			// Token: 0x060036CC RID: 14028 RVA: 0x0002D450 File Offset: 0x0002B650
			public override byte vmethod_9()
			{
				return (byte)this.char_0;
			}

			// Token: 0x060036CD RID: 14029 RVA: 0x0002D464 File Offset: 0x0002B664
			public override short C734BD78()
			{
				return (short)this.char_0;
			}

			// Token: 0x060036CE RID: 14030 RVA: 0x0002D478 File Offset: 0x0002B678
			public override ushort FC2B6FC2()
			{
				return (ushort)this.char_0;
			}

			// Token: 0x060036CF RID: 14031 RVA: 0x0002D478 File Offset: 0x0002B678
			public override int C38FA951()
			{
				return (int)this.char_0;
			}

			// Token: 0x060036D0 RID: 14032 RVA: 0x0002D478 File Offset: 0x0002B678
			public override uint vmethod_10()
			{
				return (uint)this.char_0;
			}

			// Token: 0x040000CA RID: 202
			private char char_0;
		}

		// Token: 0x020002ED RID: 749
		private sealed class Class734 : GClass6.Class709
		{
			// Token: 0x060036D1 RID: 14033 RVA: 0x0002D48C File Offset: 0x0002B68C
			public Class734(byte byte_1)
			{
				this.byte_0 = byte_1;
			}

			// Token: 0x060036D2 RID: 14034 RVA: 0x0002D4A8 File Offset: 0x0002B6A8
			public override Type vmethod_6()
			{
				return typeof(byte);
			}

			// Token: 0x060036D3 RID: 14035 RVA: 0x0002D4C0 File Offset: 0x0002B6C0
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class734(this.byte_0);
			}

			// Token: 0x060036D4 RID: 14036 RVA: 0x0002D4D8 File Offset: 0x0002B6D8
			public override object vmethod_1()
			{
				return this.byte_0;
			}

			// Token: 0x060036D5 RID: 14037 RVA: 0x0002D4F0 File Offset: 0x0002B6F0
			public override void vmethod_2(object object_0)
			{
				this.byte_0 = Convert.ToByte(object_0);
			}

			// Token: 0x060036D6 RID: 14038 RVA: 0x0002D1D4 File Offset: 0x0002B3D4
			public override GClass6.Class710 vmethod_4()
			{
				return new GClass6.Class711(this.C38FA951());
			}

			// Token: 0x060036D7 RID: 14039 RVA: 0x0002D50C File Offset: 0x0002B70C
			public override sbyte vmethod_8()
			{
				return (sbyte)this.byte_0;
			}

			// Token: 0x060036D8 RID: 14040 RVA: 0x0002D520 File Offset: 0x0002B720
			public override byte vmethod_9()
			{
				return this.byte_0;
			}

			// Token: 0x060036D9 RID: 14041 RVA: 0x0002D520 File Offset: 0x0002B720
			public override short C734BD78()
			{
				return (short)this.byte_0;
			}

			// Token: 0x060036DA RID: 14042 RVA: 0x0002D520 File Offset: 0x0002B720
			public override ushort FC2B6FC2()
			{
				return (ushort)this.byte_0;
			}

			// Token: 0x060036DB RID: 14043 RVA: 0x0002D520 File Offset: 0x0002B720
			public override int C38FA951()
			{
				return (int)this.byte_0;
			}

			// Token: 0x060036DC RID: 14044 RVA: 0x0002D520 File Offset: 0x0002B720
			public override uint vmethod_10()
			{
				return (uint)this.byte_0;
			}

			// Token: 0x040000CB RID: 203
			private byte byte_0;
		}

		// Token: 0x020002EE RID: 750
		private sealed class Class735 : GClass6.Class709
		{
			// Token: 0x060036DD RID: 14045 RVA: 0x0002D534 File Offset: 0x0002B734
			public Class735(sbyte sbyte_1)
			{
				uint num = 2052931185U;
				do
				{
					num = 2105167210U * num;
					base..ctor();
				}
				while (1721514849U + num == 0U);
				do
				{
					this.sbyte_0 = sbyte_1;
				}
				while (2066687719U > num);
			}

			// Token: 0x060036DE RID: 14046 RVA: 0x0002D570 File Offset: 0x0002B770
			public override Type vmethod_6()
			{
				return typeof(sbyte);
			}

			// Token: 0x060036DF RID: 14047 RVA: 0x0002D588 File Offset: 0x0002B788
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class735(this.sbyte_0);
			}

			// Token: 0x060036E0 RID: 14048 RVA: 0x0002D5A0 File Offset: 0x0002B7A0
			public override object vmethod_1()
			{
				return this.sbyte_0;
			}

			// Token: 0x060036E1 RID: 14049 RVA: 0x0002D5B8 File Offset: 0x0002B7B8
			public override void vmethod_2(object object_0)
			{
				uint num = 686313911U;
				do
				{
					num = (929783508U | num);
					num = (690889793U & num);
					sbyte b = Convert.ToSByte(object_0);
					num = 928974471U << (int)num;
					this.sbyte_0 = b;
				}
				while (num <= 867829565U);
			}

			// Token: 0x060036E2 RID: 14050 RVA: 0x0002D1D4 File Offset: 0x0002B3D4
			public override GClass6.Class710 vmethod_4()
			{
				return new GClass6.Class711(this.C38FA951());
			}

			// Token: 0x060036E3 RID: 14051 RVA: 0x0002D600 File Offset: 0x0002B800
			public override sbyte vmethod_8()
			{
				return this.sbyte_0;
			}

			// Token: 0x060036E4 RID: 14052 RVA: 0x0002D614 File Offset: 0x0002B814
			public override byte vmethod_9()
			{
				return (byte)this.sbyte_0;
			}

			// Token: 0x060036E5 RID: 14053 RVA: 0x0002D600 File Offset: 0x0002B800
			public override short C734BD78()
			{
				return (short)this.sbyte_0;
			}

			// Token: 0x060036E6 RID: 14054 RVA: 0x0002D628 File Offset: 0x0002B828
			public override ushort FC2B6FC2()
			{
				return (ushort)this.sbyte_0;
			}

			// Token: 0x060036E7 RID: 14055 RVA: 0x0002D600 File Offset: 0x0002B800
			public override int C38FA951()
			{
				return (int)this.sbyte_0;
			}

			// Token: 0x060036E8 RID: 14056 RVA: 0x0002D600 File Offset: 0x0002B800
			public override uint vmethod_10()
			{
				return (uint)this.sbyte_0;
			}

			// Token: 0x040000CC RID: 204
			private sbyte sbyte_0;
		}

		// Token: 0x020002EF RID: 751
		private sealed class Class736 : GClass6.Class709
		{
			// Token: 0x060036E9 RID: 14057 RVA: 0x0002D63C File Offset: 0x0002B83C
			public Class736(uint uint_1)
			{
				uint num = 1758076179U;
				base..ctor();
				do
				{
					num = 237721647U / num;
					this.uint_0 = uint_1;
				}
				while (num == 1688540674U);
			}

			// Token: 0x060036EA RID: 14058 RVA: 0x0002D66C File Offset: 0x0002B86C
			public override Type vmethod_6()
			{
				return typeof(uint);
			}

			// Token: 0x060036EB RID: 14059 RVA: 0x0002D684 File Offset: 0x0002B884
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class736(this.uint_0);
			}

			// Token: 0x060036EC RID: 14060 RVA: 0x0002D69C File Offset: 0x0002B89C
			public override object vmethod_1()
			{
				return this.uint_0;
			}

			// Token: 0x060036ED RID: 14061 RVA: 0x0002D6B4 File Offset: 0x0002B8B4
			public override void vmethod_2(object object_0)
			{
				this.uint_0 = Convert.ToUInt32(object_0);
			}

			// Token: 0x060036EE RID: 14062 RVA: 0x0002D1D4 File Offset: 0x0002B3D4
			public override GClass6.Class710 vmethod_4()
			{
				return new GClass6.Class711(this.C38FA951());
			}

			// Token: 0x060036EF RID: 14063 RVA: 0x0002D6D0 File Offset: 0x0002B8D0
			public override sbyte vmethod_8()
			{
				return (sbyte)this.uint_0;
			}

			// Token: 0x060036F0 RID: 14064 RVA: 0x0002D6E4 File Offset: 0x0002B8E4
			public override byte vmethod_9()
			{
				return (byte)this.uint_0;
			}

			// Token: 0x060036F1 RID: 14065 RVA: 0x0002D6F8 File Offset: 0x0002B8F8
			public override short C734BD78()
			{
				return (short)this.uint_0;
			}

			// Token: 0x060036F2 RID: 14066 RVA: 0x0002D70C File Offset: 0x0002B90C
			public override ushort FC2B6FC2()
			{
				return (ushort)this.uint_0;
			}

			// Token: 0x060036F3 RID: 14067 RVA: 0x0002D720 File Offset: 0x0002B920
			public override int C38FA951()
			{
				return (int)this.uint_0;
			}

			// Token: 0x060036F4 RID: 14068 RVA: 0x0002D720 File Offset: 0x0002B920
			public override uint vmethod_10()
			{
				return this.uint_0;
			}

			// Token: 0x040000CD RID: 205
			private uint uint_0;
		}

		// Token: 0x020002F0 RID: 752
		private sealed class Class737 : GClass6.Class709
		{
			// Token: 0x060036F5 RID: 14069 RVA: 0x0002D734 File Offset: 0x0002B934
			public Class737(ulong ulong_1)
			{
				uint num = 729166074U;
				for (;;)
				{
					base..ctor();
					if (num <= 2106598235U)
					{
						num |= 1640453582U;
						num = 1646160475U - num;
						this.ulong_0 = ulong_1;
						if (1407725115U + num != 0U)
						{
							break;
						}
					}
				}
			}

			// Token: 0x060036F6 RID: 14070 RVA: 0x0002D77C File Offset: 0x0002B97C
			public override Type vmethod_6()
			{
				return typeof(ulong);
			}

			// Token: 0x060036F7 RID: 14071 RVA: 0x0002D794 File Offset: 0x0002B994
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class737(this.ulong_0);
			}

			// Token: 0x060036F8 RID: 14072 RVA: 0x0002D7AC File Offset: 0x0002B9AC
			public override object vmethod_1()
			{
				return this.ulong_0;
			}

			// Token: 0x060036F9 RID: 14073 RVA: 0x0002D7C4 File Offset: 0x0002B9C4
			public override void vmethod_2(object object_0)
			{
				this.ulong_0 = Convert.ToUInt64(object_0);
			}

			// Token: 0x060036FA RID: 14074 RVA: 0x0002D7E0 File Offset: 0x0002B9E0
			public override GClass6.Class710 vmethod_4()
			{
				return new GClass6.Class712(this.F4599160());
			}

			// Token: 0x060036FB RID: 14075 RVA: 0x0002D7F8 File Offset: 0x0002B9F8
			public override sbyte vmethod_8()
			{
				return (sbyte)this.ulong_0;
			}

			// Token: 0x060036FC RID: 14076 RVA: 0x0002D80C File Offset: 0x0002BA0C
			public override byte vmethod_9()
			{
				return (byte)this.ulong_0;
			}

			// Token: 0x060036FD RID: 14077 RVA: 0x0002D820 File Offset: 0x0002BA20
			public override short C734BD78()
			{
				return (short)this.ulong_0;
			}

			// Token: 0x060036FE RID: 14078 RVA: 0x0002D834 File Offset: 0x0002BA34
			public override ushort FC2B6FC2()
			{
				return (ushort)this.ulong_0;
			}

			// Token: 0x060036FF RID: 14079 RVA: 0x0002D848 File Offset: 0x0002BA48
			public override int C38FA951()
			{
				return (int)this.ulong_0;
			}

			// Token: 0x06003700 RID: 14080 RVA: 0x0002D85C File Offset: 0x0002BA5C
			public override uint vmethod_10()
			{
				return (uint)this.ulong_0;
			}

			// Token: 0x06003701 RID: 14081 RVA: 0x0002D870 File Offset: 0x0002BA70
			public override long F4599160()
			{
				return (long)this.ulong_0;
			}

			// Token: 0x06003702 RID: 14082 RVA: 0x0002D870 File Offset: 0x0002BA70
			public override ulong vmethod_11()
			{
				return this.ulong_0;
			}

			// Token: 0x040000CE RID: 206
			private ulong ulong_0;
		}

		// Token: 0x020002F1 RID: 753
		private sealed class Class716 : GClass6.Class710
		{
			// Token: 0x06003703 RID: 14083 RVA: 0x0002D884 File Offset: 0x0002BA84
			public Class716(object object_1)
			{
				this.object_0 = object_1;
			}

			// Token: 0x06003704 RID: 14084 RVA: 0x0002D8A0 File Offset: 0x0002BAA0
			public override Type vmethod_6()
			{
				return typeof(object);
			}

			// Token: 0x06003705 RID: 14085 RVA: 0x0002D0C0 File Offset: 0x0002B2C0
			public override TypeCode vmethod_7()
			{
				return TypeCode.Object;
			}

			// Token: 0x06003706 RID: 14086 RVA: 0x0002D8B8 File Offset: 0x0002BAB8
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class716(this.object_0);
			}

			// Token: 0x06003707 RID: 14087 RVA: 0x0002D8D0 File Offset: 0x0002BAD0
			public override object vmethod_1()
			{
				return this.object_0;
			}

			// Token: 0x06003708 RID: 14088 RVA: 0x0002D8E4 File Offset: 0x0002BAE4
			public override void vmethod_2(object object_1)
			{
				this.object_0 = object_1;
			}

			// Token: 0x06003709 RID: 14089 RVA: 0x0002D8F8 File Offset: 0x0002BAF8
			public override bool BDEF602B()
			{
				return this.object_0 != null;
			}

			// Token: 0x040000CF RID: 207
			private object object_0;
		}

		// Token: 0x020002F2 RID: 754
		private sealed class Class717 : GClass6.Class710
		{
			// Token: 0x0600370A RID: 14090 RVA: 0x0002D910 File Offset: 0x0002BB10
			public Class717(object object_1, Type type_1)
			{
				uint num = 2172943232U;
				this.object_0 = object_1;
				do
				{
					this.type_0 = type_1;
					num -= 1274758815U;
				}
				while (1288715566U < num);
				do
				{
					num >>= 12;
					GClass6.Class709 @class = GClass6.Class717.smethod_0(object_1);
					num /= 1498809851U;
					this.class709_0 = @class;
				}
				while (num - 731598123U == 0U);
			}

			// Token: 0x0600370B RID: 14091 RVA: 0x0002D978 File Offset: 0x0002BB78
			private unsafe static GClass6.Class709 smethod_0(object object_1)
			{
				uint num = 189150850U;
				IntPtr intPtr2;
				for (;;)
				{
					IntPtr intPtr;
					if (object_1 == null)
					{
						num /= 635834505U;
						intPtr = IntPtr.Zero;
						num += 986658706U;
						goto IL_56;
					}
					num &= 371478167U;
					if (1451305161U >= num)
					{
						goto IL_22;
					}
					IL_37:
					if (IntPtr.Size == (int)(num + 2166392909U))
					{
						if (2448596 << (int)num != 0)
						{
							break;
						}
						goto IL_22;
					}
					else
					{
						num = 1271691842U >> (int)num;
						if (240941917U != num)
						{
							goto Block_4;
						}
						continue;
					}
					IL_56:
					intPtr2 = intPtr;
					num = (1284728613U | num);
					if (1951096464U - num != 0U)
					{
						goto IL_37;
					}
					IL_22:
					void* value = Pointer.Unbox(object_1);
					num |= 986658704U;
					intPtr = new IntPtr(value);
					goto IL_56;
				}
				return new GClass6.Class711(intPtr2.ToInt32());
				Block_4:
				num *= 773407836U;
				long long_ = intPtr2.ToInt64();
				num = 515537060U % num;
				return new GClass6.Class712(long_);
			}

			// Token: 0x0600370C RID: 14092 RVA: 0x0002D8A0 File Offset: 0x0002BAA0
			public override Type vmethod_6()
			{
				return typeof(object);
			}

			// Token: 0x0600370D RID: 14093 RVA: 0x0002DA4C File Offset: 0x0002BC4C
			public Type method_0()
			{
				return this.type_0;
			}

			// Token: 0x0600370E RID: 14094 RVA: 0x0002DA60 File Offset: 0x0002BC60
			public override TypeCode vmethod_7()
			{
				int size = IntPtr.Size;
				int num = 4;
				uint num2 = 108361U;
				if (size != num)
				{
					return (int)num2 + (TypeCode)(-108349);
				}
				return (TypeCode)(num2 ^ 108355U);
			}

			// Token: 0x0600370F RID: 14095 RVA: 0x0002DA98 File Offset: 0x0002BC98
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class717(this.object_0, this.type_0);
			}

			// Token: 0x06003710 RID: 14096 RVA: 0x0002DAB8 File Offset: 0x0002BCB8
			public override object vmethod_1()
			{
				return this.object_0;
			}

			// Token: 0x06003711 RID: 14097 RVA: 0x0002DACC File Offset: 0x0002BCCC
			public override void vmethod_2(object object_1)
			{
				this.object_0 = object_1;
				this.class709_0 = GClass6.Class717.smethod_0(object_1);
			}

			// Token: 0x06003712 RID: 14098 RVA: 0x0002DAEC File Offset: 0x0002BCEC
			public override bool BDEF602B()
			{
				return this.object_0 != null;
			}

			// Token: 0x06003713 RID: 14099 RVA: 0x0002DB04 File Offset: 0x0002BD04
			public override sbyte vmethod_8()
			{
				return this.class709_0.vmethod_8();
			}

			// Token: 0x06003714 RID: 14100 RVA: 0x0002DB1C File Offset: 0x0002BD1C
			public override short C734BD78()
			{
				return this.class709_0.C734BD78();
			}

			// Token: 0x06003715 RID: 14101 RVA: 0x0002DB34 File Offset: 0x0002BD34
			public override int C38FA951()
			{
				return this.class709_0.C38FA951();
			}

			// Token: 0x06003716 RID: 14102 RVA: 0x0002DB4C File Offset: 0x0002BD4C
			public override long F4599160()
			{
				return this.class709_0.F4599160();
			}

			// Token: 0x06003717 RID: 14103 RVA: 0x0002DB64 File Offset: 0x0002BD64
			public override byte vmethod_9()
			{
				return this.class709_0.vmethod_9();
			}

			// Token: 0x06003718 RID: 14104 RVA: 0x0002DB7C File Offset: 0x0002BD7C
			public override ushort FC2B6FC2()
			{
				return this.class709_0.FC2B6FC2();
			}

			// Token: 0x06003719 RID: 14105 RVA: 0x0002DB94 File Offset: 0x0002BD94
			public override uint vmethod_10()
			{
				return this.class709_0.vmethod_10();
			}

			// Token: 0x0600371A RID: 14106 RVA: 0x0002DBAC File Offset: 0x0002BDAC
			public override ulong vmethod_11()
			{
				return this.class709_0.vmethod_11();
			}

			// Token: 0x0600371B RID: 14107 RVA: 0x0002DBC4 File Offset: 0x0002BDC4
			public override float vmethod_12()
			{
				return this.class709_0.vmethod_12();
			}

			// Token: 0x0600371C RID: 14108 RVA: 0x0002DBDC File Offset: 0x0002BDDC
			public override double A2E5C9EC()
			{
				return this.class709_0.A2E5C9EC();
			}

			// Token: 0x0600371D RID: 14109 RVA: 0x0002DBF4 File Offset: 0x0002BDF4
			public override IntPtr vmethod_13()
			{
				return this.class709_0.vmethod_13();
			}

			// Token: 0x0600371E RID: 14110 RVA: 0x0002DC0C File Offset: 0x0002BE0C
			public override UIntPtr vmethod_14()
			{
				return this.class709_0.vmethod_14();
			}

			// Token: 0x0600371F RID: 14111 RVA: 0x0002DC24 File Offset: 0x0002BE24
			public override object E4B72359(Type type_1, bool bool_0)
			{
				return this.class709_0.E4B72359(type_1, bool_0);
			}

			// Token: 0x040000D0 RID: 208
			private object object_0;

			// Token: 0x040000D1 RID: 209
			private Type type_0;

			// Token: 0x040000D2 RID: 210
			private GClass6.Class709 class709_0;
		}

		// Token: 0x020002F3 RID: 755
		private sealed class Class718 : GClass6.Class710
		{
			// Token: 0x06003720 RID: 14112 RVA: 0x0002DC40 File Offset: 0x0002BE40
			public Class718(object object_1)
			{
				uint num = 406024278U;
				base..ctor();
				if (object_1 != null)
				{
					if ((num & 827072120U) != 0U)
					{
						bool flag = object_1 is ValueType;
						num = 1448817354U - num;
						num ^= 638890530U;
						if (flag)
						{
							goto IL_36;
						}
					}
					throw new ArgumentException();
				}
				IL_36:
				num = (1166834885U | num);
				num <<= 9;
				this.object_0 = object_1;
			}

			// Token: 0x06003721 RID: 14113 RVA: 0x0002DC9C File Offset: 0x0002BE9C
			public override Type vmethod_6()
			{
				return typeof(ValueType);
			}

			// Token: 0x06003722 RID: 14114 RVA: 0x0002DCB4 File Offset: 0x0002BEB4
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class718(this.object_0);
			}

			// Token: 0x06003723 RID: 14115 RVA: 0x0002DCCC File Offset: 0x0002BECC
			public override object vmethod_1()
			{
				return this.object_0;
			}

			// Token: 0x06003724 RID: 14116 RVA: 0x0002DCE0 File Offset: 0x0002BEE0
			public override void vmethod_2(object object_1)
			{
				uint num;
				do
				{
					num = 177884908U;
					if (object_1 == null)
					{
						goto IL_38;
					}
					num ^= 785854997U;
				}
				while (num + 327099821U == 0U);
				num |= 440944620U;
				bool flag = object_1 is ValueType;
				num += 3427585775U;
				if (!flag)
				{
					throw new ArgumentException();
				}
				IL_38:
				num >>= 27;
				this.object_0 = object_1;
			}

			// Token: 0x040000D3 RID: 211
			private object object_0;
		}

		// Token: 0x020002F4 RID: 756
		private sealed class Class719 : GClass6.Class710
		{
			// Token: 0x06003725 RID: 14117 RVA: 0x0002DD34 File Offset: 0x0002BF34
			public Class719(Array array_1)
			{
				this.array_0 = array_1;
			}

			// Token: 0x06003726 RID: 14118 RVA: 0x0002DD50 File Offset: 0x0002BF50
			public override Type vmethod_6()
			{
				return typeof(Array);
			}

			// Token: 0x06003727 RID: 14119 RVA: 0x0002DD68 File Offset: 0x0002BF68
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class719(this.array_0);
			}

			// Token: 0x06003728 RID: 14120 RVA: 0x0002DD80 File Offset: 0x0002BF80
			public override object vmethod_1()
			{
				return this.array_0;
			}

			// Token: 0x06003729 RID: 14121 RVA: 0x0002DD94 File Offset: 0x0002BF94
			public override void vmethod_2(object object_0)
			{
				this.array_0 = (Array)object_0;
			}

			// Token: 0x0600372A RID: 14122 RVA: 0x0002DDB0 File Offset: 0x0002BFB0
			public override bool BDEF602B()
			{
				return this.array_0 != null;
			}

			// Token: 0x040000D4 RID: 212
			private Array array_0;
		}

		// Token: 0x020002F5 RID: 757
		private abstract class Class720 : GClass6.Class710
		{
			// Token: 0x0600372B RID: 14123 RVA: 0x0002D0C0 File Offset: 0x0002B2C0
			public override bool vmethod_3()
			{
				return true;
			}
		}

		// Token: 0x020002F6 RID: 758
		private sealed class Class721 : GClass6.Class720
		{
			// Token: 0x0600372D RID: 14125 RVA: 0x0002DDDC File Offset: 0x0002BFDC
			public Class721(GClass6.Class709 class709_1)
			{
				this.class709_0 = class709_1;
			}

			// Token: 0x0600372E RID: 14126 RVA: 0x0002DDF8 File Offset: 0x0002BFF8
			public override Type vmethod_6()
			{
				return this.class709_0.vmethod_6();
			}

			// Token: 0x0600372F RID: 14127 RVA: 0x0002DE10 File Offset: 0x0002C010
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class721(this.class709_0);
			}

			// Token: 0x06003730 RID: 14128 RVA: 0x0002DE28 File Offset: 0x0002C028
			public override object vmethod_1()
			{
				return this.class709_0.vmethod_1();
			}

			// Token: 0x06003731 RID: 14129 RVA: 0x0002DE40 File Offset: 0x0002C040
			public override void vmethod_2(object object_0)
			{
				this.class709_0.vmethod_2(object_0);
			}

			// Token: 0x06003732 RID: 14130 RVA: 0x0002DE5C File Offset: 0x0002C05C
			public override bool BDEF602B()
			{
				return this.class709_0 != null;
			}

			// Token: 0x040000D5 RID: 213
			private GClass6.Class709 class709_0;
		}

		// Token: 0x020002F7 RID: 759
		private sealed class Class722 : GClass6.Class720
		{
			// Token: 0x06003733 RID: 14131 RVA: 0x0002DE74 File Offset: 0x0002C074
			public Class722(GClass6.Class709 class709_2, GClass6.Class709 class709_3)
			{
				uint num = 2049992634U;
				this.class709_0 = class709_2;
				do
				{
					this.class709_1 = class709_3;
				}
				while (num == 860192383U);
			}

			// Token: 0x06003734 RID: 14132 RVA: 0x0002DEA4 File Offset: 0x0002C0A4
			public override Type vmethod_6()
			{
				return this.class709_0.vmethod_6();
			}

			// Token: 0x06003735 RID: 14133 RVA: 0x0002DEBC File Offset: 0x0002C0BC
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class722(this.class709_0, this.class709_1);
			}

			// Token: 0x06003736 RID: 14134 RVA: 0x0002DEDC File Offset: 0x0002C0DC
			public override object vmethod_1()
			{
				return this.class709_0.vmethod_1();
			}

			// Token: 0x06003737 RID: 14135 RVA: 0x0002DEF4 File Offset: 0x0002C0F4
			public override void vmethod_2(object object_0)
			{
				uint num = 995185399U;
				do
				{
					this.class709_0.vmethod_2(object_0);
					num /= 1167160260U;
				}
				while (505288914U - num == 0U);
				do
				{
					GClass6.Class709 @class = this.class709_1;
					num = 947734254U + num;
					GClass6.Class709 class2 = this.class709_0;
					num %= 1082992402U;
					@class.vmethod_2(class2.vmethod_1());
				}
				while (1207505870U >> (int)num == 0U);
			}

			// Token: 0x06003738 RID: 14136 RVA: 0x0002DF5C File Offset: 0x0002C15C
			public override bool BDEF602B()
			{
				return this.class709_0 != null;
			}

			// Token: 0x040000D6 RID: 214
			private GClass6.Class709 class709_0;

			// Token: 0x040000D7 RID: 215
			private GClass6.Class709 class709_1;
		}

		// Token: 0x020002F8 RID: 760
		private sealed class Class723 : GClass6.Class720
		{
			// Token: 0x06003739 RID: 14137 RVA: 0x0002DF74 File Offset: 0x0002C174
			public Class723(FieldInfo fieldInfo_1, object object_1)
			{
				uint num = 1599034723U;
				for (;;)
				{
					num *= 679355775U;
					base..ctor();
					num &= 1556481834U;
					if (num / 1020228425U == 0U)
					{
						num ^= 1528436272U;
						this.fieldInfo_0 = fieldInfo_1;
						if (num * 1796040565U != 0U)
						{
							break;
						}
					}
				}
				this.object_0 = object_1;
			}

			// Token: 0x0600373A RID: 14138 RVA: 0x0002DFCC File Offset: 0x0002C1CC
			public override Type vmethod_6()
			{
				return this.fieldInfo_0.FieldType;
			}

			// Token: 0x0600373B RID: 14139 RVA: 0x0002DFE4 File Offset: 0x0002C1E4
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class723(this.fieldInfo_0, this.object_0);
			}

			// Token: 0x0600373C RID: 14140 RVA: 0x0002E004 File Offset: 0x0002C204
			public override object vmethod_1()
			{
				return this.fieldInfo_0.GetValue(this.object_0);
			}

			// Token: 0x0600373D RID: 14141 RVA: 0x0002E024 File Offset: 0x0002C224
			public override void vmethod_2(object object_1)
			{
				this.fieldInfo_0.SetValue(this.object_0, object_1);
			}

			// Token: 0x040000D8 RID: 216
			private FieldInfo fieldInfo_0;

			// Token: 0x040000D9 RID: 217
			private object object_0;
		}

		// Token: 0x020002F9 RID: 761
		private sealed class Class724 : GClass6.Class720
		{
			// Token: 0x0600373E RID: 14142 RVA: 0x0002E044 File Offset: 0x0002C244
			public Class724(Array array_1, int int_1)
			{
				uint num = 272701363U;
				for (;;)
				{
					base..ctor();
					num = 2141547294U - num;
					if (num << 25 == 0U)
					{
						goto IL_26;
					}
					IL_08:
					this.array_0 = array_1;
					num = 1874279122U << (int)num;
					if ((num ^ 1549475736U) == 0U)
					{
						continue;
					}
					IL_26:
					num = 106300364U * num;
					this.int_0 = int_1;
					if (741106744U + num != 0U)
					{
						break;
					}
					goto IL_08;
				}
			}

			// Token: 0x0600373F RID: 14143 RVA: 0x0002E0AC File Offset: 0x0002C2AC
			public override Type vmethod_6()
			{
				return this.array_0.GetType().GetElementType();
			}

			// Token: 0x06003740 RID: 14144 RVA: 0x0002E0CC File Offset: 0x0002C2CC
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class724(this.array_0, this.int_0);
			}

			// Token: 0x06003741 RID: 14145 RVA: 0x0002E0EC File Offset: 0x0002C2EC
			public override object vmethod_1()
			{
				return this.array_0.GetValue(this.int_0);
			}

			// Token: 0x06003742 RID: 14146 RVA: 0x0002E10C File Offset: 0x0002C30C
			public override void vmethod_2(object object_0)
			{
				uint num = 783511946U;
				do
				{
					Array array = this.array_0;
					num = (592265457U ^ num);
					num >>= 31;
					int index = this.int_0;
					num *= 301999611U;
					array.SetValue(object_0, index);
				}
				while (2133864853U >> (int)num == 0U);
			}

			// Token: 0x040000DA RID: 218
			private Array array_0;

			// Token: 0x040000DB RID: 219
			private int int_0;
		}

		// Token: 0x020002FA RID: 762
		private sealed class Class726 : GClass6.Class710
		{
			// Token: 0x06003743 RID: 14147 RVA: 0x0002E158 File Offset: 0x0002C358
			public Class726(MethodBase methodBase_1)
			{
				this.methodBase_0 = methodBase_1;
			}

			// Token: 0x06003744 RID: 14148 RVA: 0x0002E174 File Offset: 0x0002C374
			public override Type vmethod_6()
			{
				return typeof(MethodBase);
			}

			// Token: 0x06003745 RID: 14149 RVA: 0x0002E18C File Offset: 0x0002C38C
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class726(this.methodBase_0);
			}

			// Token: 0x06003746 RID: 14150 RVA: 0x0002E1A4 File Offset: 0x0002C3A4
			public override object vmethod_1()
			{
				return this.methodBase_0;
			}

			// Token: 0x06003747 RID: 14151 RVA: 0x0002E1B8 File Offset: 0x0002C3B8
			public override void vmethod_2(object object_0)
			{
				this.methodBase_0 = (MethodBase)object_0;
			}

			// Token: 0x06003748 RID: 14152 RVA: 0x0002E1D4 File Offset: 0x0002C3D4
			public override bool BDEF602B()
			{
				return this.methodBase_0 != null;
			}

			// Token: 0x06003749 RID: 14153 RVA: 0x0002E1EC File Offset: 0x0002C3EC
			public override IntPtr vmethod_13()
			{
				return this.methodBase_0.MethodHandle.GetFunctionPointer();
			}

			// Token: 0x040000DC RID: 220
			private MethodBase methodBase_0;
		}

		// Token: 0x020002FB RID: 763
		private sealed class Class727 : GClass6.Class710
		{
			// Token: 0x0600374A RID: 14154 RVA: 0x0002E20C File Offset: 0x0002C40C
			public Class727(IntPtr intptr_1)
			{
				this.intptr_0 = intptr_1;
				this.class709_0 = GClass6.Class727.smethod_0(this.intptr_0);
			}

			// Token: 0x0600374B RID: 14155 RVA: 0x0002E238 File Offset: 0x0002C438
			private static GClass6.Class709 smethod_0(IntPtr intptr_1)
			{
				int size = IntPtr.Size;
				int num = 4;
				uint num2 = 3U;
				if (size == num && (1352491605U ^ num2) != 0U)
				{
					int int_ = intptr_1.ToInt32();
					num2 = (452490868U & num2);
					return new GClass6.Class711(int_);
				}
				num2 -= 1181297357U;
				long long_ = intptr_1.ToInt64();
				num2 = 1247892008U % num2;
				return new GClass6.Class712(long_);
			}

			// Token: 0x0600374C RID: 14156 RVA: 0x0002E290 File Offset: 0x0002C490
			public override Type vmethod_6()
			{
				return typeof(IntPtr);
			}

			// Token: 0x0600374D RID: 14157 RVA: 0x0002E2A8 File Offset: 0x0002C4A8
			public override TypeCode vmethod_7()
			{
				return this.class709_0.vmethod_7();
			}

			// Token: 0x0600374E RID: 14158 RVA: 0x0002E2C0 File Offset: 0x0002C4C0
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class727(this.intptr_0);
			}

			// Token: 0x0600374F RID: 14159 RVA: 0x0002E2D8 File Offset: 0x0002C4D8
			public override object vmethod_1()
			{
				return this.intptr_0;
			}

			// Token: 0x06003750 RID: 14160 RVA: 0x0002E2F0 File Offset: 0x0002C4F0
			public override void vmethod_2(object object_0)
			{
				this.intptr_0 = (IntPtr)object_0;
				this.class709_0 = GClass6.Class727.smethod_0(this.intptr_0);
			}

			// Token: 0x06003751 RID: 14161 RVA: 0x0002E31C File Offset: 0x0002C51C
			public override bool BDEF602B()
			{
				return this.intptr_0 != IntPtr.Zero;
			}

			// Token: 0x06003752 RID: 14162 RVA: 0x0002E33C File Offset: 0x0002C53C
			public override sbyte vmethod_8()
			{
				return this.class709_0.vmethod_8();
			}

			// Token: 0x06003753 RID: 14163 RVA: 0x0002E354 File Offset: 0x0002C554
			public override short C734BD78()
			{
				return this.class709_0.C734BD78();
			}

			// Token: 0x06003754 RID: 14164 RVA: 0x0002E36C File Offset: 0x0002C56C
			public override int C38FA951()
			{
				return this.class709_0.C38FA951();
			}

			// Token: 0x06003755 RID: 14165 RVA: 0x0002E384 File Offset: 0x0002C584
			public override long F4599160()
			{
				return this.class709_0.F4599160();
			}

			// Token: 0x06003756 RID: 14166 RVA: 0x0002E39C File Offset: 0x0002C59C
			public override byte vmethod_9()
			{
				return this.class709_0.vmethod_9();
			}

			// Token: 0x06003757 RID: 14167 RVA: 0x0002E3B4 File Offset: 0x0002C5B4
			public override ushort FC2B6FC2()
			{
				return this.class709_0.FC2B6FC2();
			}

			// Token: 0x06003758 RID: 14168 RVA: 0x0002E3CC File Offset: 0x0002C5CC
			public override uint vmethod_10()
			{
				return this.class709_0.vmethod_10();
			}

			// Token: 0x06003759 RID: 14169 RVA: 0x0002E3E4 File Offset: 0x0002C5E4
			public override ulong vmethod_11()
			{
				return this.class709_0.vmethod_11();
			}

			// Token: 0x0600375A RID: 14170 RVA: 0x0002E3FC File Offset: 0x0002C5FC
			public override float vmethod_12()
			{
				return this.class709_0.vmethod_12();
			}

			// Token: 0x0600375B RID: 14171 RVA: 0x0002E414 File Offset: 0x0002C614
			public override double A2E5C9EC()
			{
				return this.class709_0.A2E5C9EC();
			}

			// Token: 0x0600375C RID: 14172 RVA: 0x0002E42C File Offset: 0x0002C62C
			public override IntPtr vmethod_13()
			{
				return this.intptr_0;
			}

			// Token: 0x0600375D RID: 14173 RVA: 0x0002E440 File Offset: 0x0002C640
			public override UIntPtr vmethod_14()
			{
				return this.class709_0.vmethod_14();
			}

			// Token: 0x0600375E RID: 14174 RVA: 0x0002E458 File Offset: 0x0002C658
			public override object E4B72359(Type type_0, bool bool_0)
			{
				return this.class709_0.E4B72359(type_0, bool_0);
			}

			// Token: 0x040000DD RID: 221
			private IntPtr intptr_0;

			// Token: 0x040000DE RID: 222
			private GClass6.Class709 class709_0;
		}

		// Token: 0x020002FC RID: 764
		private sealed class Class728 : GClass6.Class710
		{
			// Token: 0x0600375F RID: 14175 RVA: 0x0002E474 File Offset: 0x0002C674
			public Class728(UIntPtr uintptr_1)
			{
				this.uintptr_0 = uintptr_1;
				this.class709_0 = GClass6.Class728.smethod_0(this.uintptr_0);
			}

			// Token: 0x06003760 RID: 14176 RVA: 0x0002E4A0 File Offset: 0x0002C6A0
			private static GClass6.Class709 smethod_0(UIntPtr uintptr_1)
			{
				uint num = 361704416U;
				do
				{
					int size = IntPtr.Size;
					num -= 219890156U;
					uint num2 = num ^ 141814256U;
					num -= 1017970697U;
					if (size == num2 && 1907038138U <= num)
					{
						goto Block_2;
					}
					num += 1979588630U;
				}
				while (num <= 133908878U);
				long long_ = (long)uintptr_1.ToUInt64();
				num = (484588929U | num);
				return new GClass6.Class712(long_);
				Block_2:
				int int_ = (int)uintptr_1.ToUInt32();
				num *= 101669708U;
				return new GClass6.Class711(int_);
			}

			// Token: 0x06003761 RID: 14177 RVA: 0x0002E51C File Offset: 0x0002C71C
			public override Type vmethod_6()
			{
				return typeof(UIntPtr);
			}

			// Token: 0x06003762 RID: 14178 RVA: 0x0002E534 File Offset: 0x0002C734
			public override TypeCode vmethod_7()
			{
				return this.class709_0.vmethod_7();
			}

			// Token: 0x06003763 RID: 14179 RVA: 0x0002E54C File Offset: 0x0002C74C
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class728(this.uintptr_0);
			}

			// Token: 0x06003764 RID: 14180 RVA: 0x0002E564 File Offset: 0x0002C764
			public override object vmethod_1()
			{
				return this.uintptr_0;
			}

			// Token: 0x06003765 RID: 14181 RVA: 0x0002E57C File Offset: 0x0002C77C
			public override void vmethod_2(object object_0)
			{
				uint num = 241387192U;
				do
				{
					num ^= 1066021328U;
					this.uintptr_0 = (UIntPtr)object_0;
					num |= 359535001U;
				}
				while (598634238U % num == 0U);
				this.class709_0 = GClass6.Class728.smethod_0(this.uintptr_0);
			}

			// Token: 0x06003766 RID: 14182 RVA: 0x0002E5C8 File Offset: 0x0002C7C8
			public override bool BDEF602B()
			{
				return this.uintptr_0 != UIntPtr.Zero;
			}

			// Token: 0x06003767 RID: 14183 RVA: 0x0002E5E8 File Offset: 0x0002C7E8
			public override sbyte vmethod_8()
			{
				return this.class709_0.vmethod_8();
			}

			// Token: 0x06003768 RID: 14184 RVA: 0x0002E600 File Offset: 0x0002C800
			public override short C734BD78()
			{
				return this.class709_0.C734BD78();
			}

			// Token: 0x06003769 RID: 14185 RVA: 0x0002E618 File Offset: 0x0002C818
			public override int C38FA951()
			{
				return this.class709_0.C38FA951();
			}

			// Token: 0x0600376A RID: 14186 RVA: 0x0002E630 File Offset: 0x0002C830
			public override long F4599160()
			{
				return this.class709_0.F4599160();
			}

			// Token: 0x0600376B RID: 14187 RVA: 0x0002E648 File Offset: 0x0002C848
			public override byte vmethod_9()
			{
				return this.class709_0.vmethod_9();
			}

			// Token: 0x0600376C RID: 14188 RVA: 0x0002E660 File Offset: 0x0002C860
			public override ushort FC2B6FC2()
			{
				return this.class709_0.FC2B6FC2();
			}

			// Token: 0x0600376D RID: 14189 RVA: 0x0002E678 File Offset: 0x0002C878
			public override uint vmethod_10()
			{
				return this.class709_0.vmethod_10();
			}

			// Token: 0x0600376E RID: 14190 RVA: 0x0002E690 File Offset: 0x0002C890
			public override ulong vmethod_11()
			{
				return this.class709_0.vmethod_11();
			}

			// Token: 0x0600376F RID: 14191 RVA: 0x0002E6A8 File Offset: 0x0002C8A8
			public override float vmethod_12()
			{
				return this.class709_0.vmethod_12();
			}

			// Token: 0x06003770 RID: 14192 RVA: 0x0002E6C0 File Offset: 0x0002C8C0
			public override double A2E5C9EC()
			{
				return this.class709_0.A2E5C9EC();
			}

			// Token: 0x06003771 RID: 14193 RVA: 0x0002E6D8 File Offset: 0x0002C8D8
			public override IntPtr vmethod_13()
			{
				return this.class709_0.vmethod_13();
			}

			// Token: 0x06003772 RID: 14194 RVA: 0x0002E6F0 File Offset: 0x0002C8F0
			public override UIntPtr vmethod_14()
			{
				return this.uintptr_0;
			}

			// Token: 0x06003773 RID: 14195 RVA: 0x0002E704 File Offset: 0x0002C904
			public override object E4B72359(Type type_0, bool bool_0)
			{
				return this.class709_0.E4B72359(type_0, bool_0);
			}

			// Token: 0x040000DF RID: 223
			private UIntPtr uintptr_0;

			// Token: 0x040000E0 RID: 224
			private GClass6.Class709 class709_0;
		}

		// Token: 0x020002FD RID: 765
		private sealed class Class729 : GClass6.Class710
		{
			// Token: 0x06003774 RID: 14196 RVA: 0x0002E720 File Offset: 0x0002C920
			public Class729(Enum enum_1)
			{
				uint num = 2056535789U;
				base..ctor();
				for (;;)
				{
					if (enum_1 == null)
					{
						num = (78659200U & num);
						if (632514638U * num != 0U)
						{
							goto IL_74;
						}
					}
					else
					{
						num |= 171211548U;
						num = 2059356509U >> (int)num;
						this.enum_0 = enum_1;
						num *= 955130841U;
						num = (1890453448U & num);
						Enum enum_2 = this.enum_0;
						num /= 2123447103U;
						this.class709_0 = GClass6.Class729.smethod_0(enum_2);
						if (num != 2029002970U)
						{
							break;
						}
					}
				}
				return;
				IL_74:
				throw new ArgumentException();
			}

			// Token: 0x06003775 RID: 14197 RVA: 0x0002E7A8 File Offset: 0x0002C9A8
			private static GClass6.Class709 smethod_0(Enum enum_1)
			{
				uint num = 1193696715U;
				for (;;)
				{
					TypeCode typeCode = enum_1.GetTypeCode();
					num = 1370372275U + num;
					TypeCode typeCode2 = typeCode;
					if ((2073895761U ^ num) != 0U)
					{
						TypeCode typeCode3 = typeCode2;
						num = (1611860079U ^ num);
						uint num2 = num + 121135604U;
						num = 486346890U + num;
						switch (typeCode3 - num2)
						{
						case 0:
						case 2:
						case 4:
							goto IL_88;
						case 1:
						case 3:
						case 5:
							num %= 810026088U;
							if (1495693385U - num != 0U)
							{
								goto Block_2;
							}
							continue;
						case 6:
							num ^= 1123966141U;
							if (num < 124608819U)
							{
								continue;
							}
							goto IL_CE;
						case 7:
							goto IL_FE;
						}
						break;
					}
					break;
				}
				goto IL_EA;
				Block_2:
				num = (1745487668U ^ num);
				int int_ = (int)Convert.ToUInt32(enum_1);
				num = 878511567U << (int)num;
				return new GClass6.Class711(int_);
				IL_88:
				if (565987814U >= num)
				{
					num -= 957359911U;
					int int_2 = Convert.ToInt32(enum_1);
					num = (629879212U & num);
					return new GClass6.Class711(int_2);
				}
				goto IL_EA;
				IL_CE:
				num = (771830525U ^ num);
				long long_ = Convert.ToInt64(enum_1);
				num = 1609057048U / num;
				return new GClass6.Class712(long_);
				IL_EA:
				num = 778009284U >> (int)num;
				throw new InvalidOperationException();
				IL_FE:
				long long_2 = (long)Convert.ToUInt64(enum_1);
				num = 992312187U * num;
				return new GClass6.Class712(long_2);
			}

			// Token: 0x06003776 RID: 14198 RVA: 0x0002E8C8 File Offset: 0x0002CAC8
			public override GClass6.Class709 vmethod_5()
			{
				return this.class709_0.vmethod_5();
			}

			// Token: 0x06003777 RID: 14199 RVA: 0x0002E8E0 File Offset: 0x0002CAE0
			public override Type vmethod_6()
			{
				return this.enum_0.GetType();
			}

			// Token: 0x06003778 RID: 14200 RVA: 0x0002E8F8 File Offset: 0x0002CAF8
			public override TypeCode vmethod_7()
			{
				return this.class709_0.vmethod_7();
			}

			// Token: 0x06003779 RID: 14201 RVA: 0x0002E910 File Offset: 0x0002CB10
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class729(this.enum_0);
			}

			// Token: 0x0600377A RID: 14202 RVA: 0x0002E928 File Offset: 0x0002CB28
			public override object vmethod_1()
			{
				return this.enum_0;
			}

			// Token: 0x0600377B RID: 14203 RVA: 0x0002E93C File Offset: 0x0002CB3C
			public override void vmethod_2(object object_0)
			{
				uint num = 25824728U;
				if (object_0 == null)
				{
					throw new ArgumentException();
				}
				num |= 70852880U;
				num = 1267169427U - num;
				Enum @enum = (Enum)object_0;
				num -= 445138036U;
				this.enum_0 = @enum;
				num = (885463432U & num);
				num = 1026973486U - num;
				this.class709_0 = GClass6.Class729.smethod_0(this.enum_0);
			}

			// Token: 0x0600377C RID: 14204 RVA: 0x0002E9A0 File Offset: 0x0002CBA0
			public override byte vmethod_9()
			{
				return this.class709_0.vmethod_9();
			}

			// Token: 0x0600377D RID: 14205 RVA: 0x0002E9B8 File Offset: 0x0002CBB8
			public override sbyte vmethod_8()
			{
				return this.class709_0.vmethod_8();
			}

			// Token: 0x0600377E RID: 14206 RVA: 0x0002E9D0 File Offset: 0x0002CBD0
			public override short C734BD78()
			{
				return this.class709_0.C734BD78();
			}

			// Token: 0x0600377F RID: 14207 RVA: 0x0002E9E8 File Offset: 0x0002CBE8
			public override ushort FC2B6FC2()
			{
				return this.class709_0.FC2B6FC2();
			}

			// Token: 0x06003780 RID: 14208 RVA: 0x0002EA00 File Offset: 0x0002CC00
			public override int C38FA951()
			{
				return this.class709_0.C38FA951();
			}

			// Token: 0x06003781 RID: 14209 RVA: 0x0002EA18 File Offset: 0x0002CC18
			public override uint vmethod_10()
			{
				return this.class709_0.vmethod_10();
			}

			// Token: 0x06003782 RID: 14210 RVA: 0x0002EA30 File Offset: 0x0002CC30
			public override long F4599160()
			{
				return this.class709_0.F4599160();
			}

			// Token: 0x06003783 RID: 14211 RVA: 0x0002EA48 File Offset: 0x0002CC48
			public override ulong vmethod_11()
			{
				return this.class709_0.vmethod_11();
			}

			// Token: 0x06003784 RID: 14212 RVA: 0x0002EA60 File Offset: 0x0002CC60
			public override float vmethod_12()
			{
				return this.class709_0.vmethod_12();
			}

			// Token: 0x06003785 RID: 14213 RVA: 0x0002EA78 File Offset: 0x0002CC78
			public override double A2E5C9EC()
			{
				return this.class709_0.A2E5C9EC();
			}

			// Token: 0x06003786 RID: 14214 RVA: 0x0002EA90 File Offset: 0x0002CC90
			public override IntPtr vmethod_13()
			{
				int size = IntPtr.Size;
				int num = 4;
				uint num2 = 1288861134U;
				long value;
				if (size != num)
				{
					num2 = 474552955U / num2;
					value = this.F4599160();
				}
				else
				{
					num2 *= 3878634U;
					long num3 = (long)this.C38FA951();
					num2 ^= 2010479159U;
					value = num3;
					num2 += 3090036613U;
				}
				num2 = (127467954U | num2);
				return new IntPtr(value);
			}

			// Token: 0x06003787 RID: 14215 RVA: 0x0002EAF0 File Offset: 0x0002CCF0
			public override UIntPtr vmethod_14()
			{
				int size = IntPtr.Size;
				uint num = 1147470187U;
				ulong value;
				if (size != 4)
				{
					num %= 271128052U;
					if (num < 1207265252U)
					{
						value = this.vmethod_11();
						goto IL_45;
					}
				}
				num = (1061912237U & num);
				ulong num2 = (ulong)this.vmethod_10();
				num = 1454274014U / num;
				value = num2;
				num += 62957959U;
				IL_45:
				num = 476925411U + num;
				return new UIntPtr(value);
			}

			// Token: 0x06003788 RID: 14216 RVA: 0x0002EB50 File Offset: 0x0002CD50
			public override object E4B72359(Type type_0, bool bool_0)
			{
				return this.class709_0.E4B72359(type_0, bool_0);
			}

			// Token: 0x040000E1 RID: 225
			private Enum enum_0;

			// Token: 0x040000E2 RID: 226
			private GClass6.Class709 class709_0;
		}

		// Token: 0x020002FE RID: 766
		private sealed class Class725 : GClass6.Class720
		{
			// Token: 0x06003789 RID: 14217 RVA: 0x0002EB6C File Offset: 0x0002CD6C
			public Class725(IntPtr intptr_1, Type type_1)
			{
				uint num = 567031430U;
				do
				{
					num = 1674196367U << (int)num;
					base..ctor();
					num = 1353146983U + num;
					num %= 1746541535U;
					this.intptr_0 = intptr_1;
				}
				while ((num ^ 1293646802U) == 0U);
				num += 1517383250U;
				this.type_0 = type_1;
			}

			// Token: 0x0600378A RID: 14218 RVA: 0x0002EBC4 File Offset: 0x0002CDC4
			public override Type vmethod_6()
			{
				return typeof(Pointer);
			}

			// Token: 0x0600378B RID: 14219 RVA: 0x0002B614 File Offset: 0x00029814
			public override TypeCode vmethod_7()
			{
				return TypeCode.Empty;
			}

			// Token: 0x0600378C RID: 14220 RVA: 0x0002EBDC File Offset: 0x0002CDDC
			public override GClass6.Class709 vmethod_0()
			{
				return new GClass6.Class725(this.intptr_0, this.type_0);
			}

			// Token: 0x0600378D RID: 14221 RVA: 0x0002EBFC File Offset: 0x0002CDFC
			public override object vmethod_1()
			{
				uint num = 1138194806U;
				for (;;)
				{
					Type type = this.type_0;
					num = 1530022656U * num;
					bool isValueType = type.IsValueType;
					num = 1746225055U + num;
					if (!isValueType)
					{
						break;
					}
					num >>= 18;
					if (num <= 1402930351U)
					{
						goto IL_3B;
					}
				}
				throw new InvalidOperationException();
				IL_3B:
				num = 1701593541U % num;
				IntPtr ptr = this.intptr_0;
				num *= 86967196U;
				Type structureType = this.type_0;
				num %= 109120104U;
				return Marshal.PtrToStructure(ptr, structureType);
			}

			// Token: 0x0600378E RID: 14222 RVA: 0x0002EC70 File Offset: 0x0002CE70
			public override void vmethod_2(object object_0)
			{
				uint num;
				for (;;)
				{
					num = 422063574U;
					if (object_0 == null)
					{
						num /= 2105232401U;
						if (num < 838928630U)
						{
							goto Block_12;
						}
					}
					else
					{
						for (;;)
						{
							Type type = this.type_0;
							num = (1764310076U | num);
							bool isValueType = type.IsValueType;
							num %= 592648533U;
							if (isValueType)
							{
								goto Block_3;
							}
							num ^= 1217607719U;
							num /= 1332098625U;
							Type type2 = object_0.GetType();
							num = (703005751U | num);
							TypeCode typeCode = Type.GetTypeCode(type2);
							object obj = typeCode;
							object obj2 = num - 703005747U;
							num = 2093564459U % num;
							object obj3 = obj - obj2;
							num = 2012438468U + num;
							switch (obj3)
							{
							case 0:
								goto IL_257;
							case 1:
								if (num / 1699746685U != 0U)
								{
									goto IL_BA;
								}
								break;
							case 2:
								goto IL_EC;
							case 3:
								goto IL_12E;
							case 4:
								goto IL_292;
							case 5:
								goto IL_2D1;
							case 6:
								goto IL_2F3;
							case 7:
								goto IL_315;
							case 8:
								goto IL_183;
							case 9:
								goto IL_37D;
							case 10:
								goto IL_1CE;
							default:
								if (722810882U != num)
								{
									goto Block_2;
								}
								break;
							}
						}
						IL_BA:
						IntPtr ptr = this.intptr_0;
						num = (1783375073U | num);
						byte b = (byte)Convert.ToSByte(object_0);
						num |= 1278762777U;
						Marshal.WriteByte(ptr, b);
						if (num > 483163133U)
						{
							return;
						}
						continue;
						IL_EC:
						num |= 193214651U;
						IntPtr ptr2 = this.intptr_0;
						num &= 1085288092U;
						num >>= 30;
						byte val = Convert.ToByte(object_0);
						num += 1534542621U;
						Marshal.WriteByte(ptr2, val);
						if ((num ^ 1279212744U) != 0U)
						{
							return;
						}
						continue;
						IL_12E:
						num = 949949300U % num;
						if ((num ^ 900403872U) == 0U)
						{
							continue;
						}
						num /= 1711417297U;
						IntPtr ptr3 = this.intptr_0;
						num <<= 12;
						num = 232784455U * num;
						short val2 = Convert.ToInt16(object_0);
						num -= 796737369U;
						Marshal.WriteInt16(ptr3, val2);
						if (num >= 1193357787U)
						{
							return;
						}
						continue;
						IL_183:
						if (num <= 17708175U)
						{
							goto IL_348;
						}
						IntPtr ptr4 = this.intptr_0;
						num = 1105622761U * num;
						num *= 1239683314U;
						long val3 = (long)Convert.ToUInt64(object_0);
						num = (1038579645U | num);
						Marshal.WriteInt64(ptr4, val3);
						if (1863272297U >> (int)num == 0U)
						{
							return;
						}
						continue;
						IL_1CE:
						num &= 1446521835U;
						if (1218783881U < num)
						{
							goto IL_3D8;
						}
						num += 2053188324U;
						IntPtr ptr5 = this.intptr_0;
						num &= 637340884U;
						double value = Convert.ToDouble(object_0);
						num = (172849761U & num);
						Marshal.WriteInt64(ptr5, BitConverter.ToInt64(BitConverter.GetBytes(value), (int)(num ^ 589888U)));
						if ((num & 102586309U) != 0U)
						{
							return;
						}
					}
				}
				Block_2:
				throw new ArgumentException();
				Block_3:
				num = (1964379590U & num);
				goto IL_348;
				Block_12:
				goto IL_3D8;
				IL_257:
				num <<= 21;
				IntPtr ptr6 = this.intptr_0;
				num = 1370650545U * num;
				num = 1506349381U << (int)num;
				char val4 = Convert.ToChar(object_0);
				num += 1910472181U;
				Marshal.WriteInt16(ptr6, val4);
				return;
				IL_292:
				num = 1383943780U - num;
				if ((1875012619U ^ num) != 0U)
				{
					IntPtr ptr7 = this.intptr_0;
					num -= 1677335166U;
					short num2 = (short)Convert.ToUInt16(object_0);
					num = (130951090U & num);
					short val5 = num2;
					num /= 404160908U;
					Marshal.WriteInt16(ptr7, val5);
					return;
				}
				goto IL_3D8;
				IL_2D1:
				num = (1636709036U & num);
				IntPtr ptr8 = this.intptr_0;
				num = (635577471U & num);
				Marshal.WriteInt32(ptr8, Convert.ToInt32(object_0));
				return;
				IL_2F3:
				num = (1341750971U & num);
				IntPtr ptr9 = this.intptr_0;
				num = (357699442U | num);
				Marshal.WriteInt32(ptr9, (int)Convert.ToUInt32(object_0));
				return;
				IL_315:
				num &= 989680550U;
				num += 710433379U;
				IntPtr ptr10 = this.intptr_0;
				num = 1221788502U / num;
				num *= 564026731U;
				Marshal.WriteInt64(ptr10, Convert.ToInt64(object_0));
				return;
				IL_348:
				num = 1751537201U - num;
				num -= 129842605U;
				IntPtr ptr11 = this.intptr_0;
				num /= 345537092U;
				Marshal.StructureToPtr(object_0, ptr11, num + 4294967292U != 0U);
				if (819618142U - num != 0U)
				{
					return;
				}
				goto IL_3D8;
				IL_37D:
				if (num > 1868789413U)
				{
					num = (764374700U | num);
					IntPtr ptr12 = this.intptr_0;
					num = (380269598U ^ num);
					float value2 = Convert.ToSingle(object_0);
					num ^= 874470727U;
					byte[] bytes = BitConverter.GetBytes(value2);
					int startIndex = (int)(num ^ 2405095156U);
					num &= 2094944220U;
					int val6 = BitConverter.ToInt32(bytes, startIndex);
					num <<= 6;
					Marshal.WriteInt32(ptr12, val6);
					if (num <= 1135497010U)
					{
						return;
					}
				}
				IL_3D8:
				throw new ArgumentException();
			}

			// Token: 0x0600378F RID: 14223 RVA: 0x0002F05C File Offset: 0x0002D25C
			public override sbyte vmethod_8()
			{
				return (sbyte)Marshal.ReadByte(this.intptr_0);
			}

			// Token: 0x06003790 RID: 14224 RVA: 0x0002F078 File Offset: 0x0002D278
			public override short C734BD78()
			{
				return Marshal.ReadInt16(this.intptr_0);
			}

			// Token: 0x06003791 RID: 14225 RVA: 0x0002F090 File Offset: 0x0002D290
			public override int C38FA951()
			{
				return Marshal.ReadInt32(this.intptr_0);
			}

			// Token: 0x06003792 RID: 14226 RVA: 0x0002F0A8 File Offset: 0x0002D2A8
			public override long F4599160()
			{
				return Marshal.ReadInt64(this.intptr_0);
			}

			// Token: 0x06003793 RID: 14227 RVA: 0x0002F0C0 File Offset: 0x0002D2C0
			public override char DDEBCEDF()
			{
				return (char)Marshal.ReadInt16(this.intptr_0);
			}

			// Token: 0x06003794 RID: 14228 RVA: 0x0002F0DC File Offset: 0x0002D2DC
			public override byte vmethod_9()
			{
				return Marshal.ReadByte(this.intptr_0);
			}

			// Token: 0x06003795 RID: 14229 RVA: 0x0002F0C0 File Offset: 0x0002D2C0
			public override ushort FC2B6FC2()
			{
				return (ushort)Marshal.ReadInt16(this.intptr_0);
			}

			// Token: 0x06003796 RID: 14230 RVA: 0x0002F090 File Offset: 0x0002D290
			public override uint vmethod_10()
			{
				return (uint)Marshal.ReadInt32(this.intptr_0);
			}

			// Token: 0x06003797 RID: 14231 RVA: 0x0002F0A8 File Offset: 0x0002D2A8
			public override ulong vmethod_11()
			{
				return (ulong)Marshal.ReadInt64(this.intptr_0);
			}

			// Token: 0x06003798 RID: 14232 RVA: 0x0002F0F4 File Offset: 0x0002D2F4
			public override float vmethod_12()
			{
				return BitConverter.ToSingle(BitConverter.GetBytes(Marshal.ReadInt32(this.intptr_0)), 0);
			}

			// Token: 0x06003799 RID: 14233 RVA: 0x0002F118 File Offset: 0x0002D318
			public override double A2E5C9EC()
			{
				return BitConverter.ToDouble(BitConverter.GetBytes(Marshal.ReadInt64(this.intptr_0)), 0);
			}

			// Token: 0x0600379A RID: 14234 RVA: 0x0002F13C File Offset: 0x0002D33C
			public override IntPtr vmethod_13()
			{
				int size = IntPtr.Size;
				int num = 4;
				uint num2 = 6U;
				if (size != num)
				{
					num2 <<= 5;
					if (num2 < 926707339U)
					{
						goto IL_5D;
					}
				}
				num2 /= 526919295U;
				long value;
				if ((1911387782U & num2) == 0U)
				{
					num2 %= 1036020352U;
					long num3 = (long)Marshal.ReadInt32(this.intptr_0);
					num2 = 2132225979U * num2;
					value = num3;
					num2 ^= 745091813U;
					goto IL_76;
				}
				IL_5D:
				IntPtr ptr = this.intptr_0;
				num2 = 745091813U << (int)num2;
				value = Marshal.ReadInt64(ptr);
				IL_76:
				return new IntPtr(value);
			}

			// Token: 0x0600379B RID: 14235 RVA: 0x0002F1C4 File Offset: 0x0002D3C4
			public override UIntPtr vmethod_14()
			{
				uint num = 1568567180U;
				do
				{
					uint size = (uint)IntPtr.Size;
					num = (2007123995U & num);
					if (size == num - 1428310020U)
					{
						goto IL_3F;
					}
				}
				while (1038295397U == num);
				num = (25172289U ^ num);
				ulong value = (ulong)Marshal.ReadInt64(this.intptr_0);
				goto IL_6B;
				IL_3F:
				num -= 49284661U;
				IntPtr ptr = this.intptr_0;
				num = 826616201U - num;
				ulong num2 = (ulong)Marshal.ReadInt32(ptr);
				num /= 814621491U;
				value = num2;
				num += 1419923781U;
				IL_6B:
				num = 253054885U - num;
				return new UIntPtr(value);
			}

			// Token: 0x040000E3 RID: 227
			private IntPtr intptr_0;

			// Token: 0x040000E4 RID: 228
			private Type type_0;
		}

		// Token: 0x020002FF RID: 767
		private sealed class Class738
		{
			// Token: 0x0600379C RID: 14236 RVA: 0x0002F24C File Offset: 0x0002D44C
			public Class738(byte byte_1, int int_2, int int_3)
			{
				uint num = 41243600U;
				base..ctor();
				do
				{
					num = (1882995406U & num);
					this.byte_0 = byte_1;
					num = 808915505U % num;
					num ^= 1172652328U;
					this.int_0 = int_2;
					this.int_1 = int_3;
				}
				while (1461921762U < num);
			}

			// Token: 0x0600379D RID: 14237 RVA: 0x0002F2A0 File Offset: 0x0002D4A0
			public byte method_0()
			{
				return this.byte_0;
			}

			// Token: 0x0600379E RID: 14238 RVA: 0x0002F2B4 File Offset: 0x0002D4B4
			public int method_1()
			{
				return this.int_0;
			}

			// Token: 0x0600379F RID: 14239 RVA: 0x0002F2C8 File Offset: 0x0002D4C8
			public int method_2()
			{
				return this.int_1;
			}

			// Token: 0x040000E5 RID: 229
			private byte byte_0;

			// Token: 0x040000E6 RID: 230
			private int int_0;

			// Token: 0x040000E7 RID: 231
			private int int_1;
		}

		// Token: 0x02000300 RID: 768
		private sealed class Class739
		{
			// Token: 0x060037A0 RID: 14240 RVA: 0x0002F2DC File Offset: 0x0002D4DC
			public Class739(int int_2, int int_3)
			{
				this.int_0 = int_2;
				this.int_1 = int_3;
			}

			// Token: 0x060037A1 RID: 14241 RVA: 0x0002F308 File Offset: 0x0002D508
			public int method_0()
			{
				return this.int_0;
			}

			// Token: 0x060037A2 RID: 14242 RVA: 0x0002F31C File Offset: 0x0002D51C
			public int method_1()
			{
				return this.int_1;
			}

			// Token: 0x060037A3 RID: 14243 RVA: 0x0002F330 File Offset: 0x0002D530
			public int method_2(GClass6.Class739 class739_0)
			{
				uint num = 1U;
				if (class739_0 == null)
				{
					return (int)(num ^ 0U);
				}
				int num2;
				do
				{
					num += 2072118959U;
					num2 = this.int_0.CompareTo(class739_0.method_0());
					num %= 1664960379U;
				}
				while (num >= 1700613293U);
				bool flag = num2 != 0;
				num %= 748178652U;
				if (!flag)
				{
					num = 694842903U - num;
					int num3 = class739_0.method_1();
					num = 540236570U % num;
					num = (1699425939U ^ num);
					num = 1509586365U >> (int)num;
					int value = this.int_1;
					num ^= 2070680978U;
					int num4 = num3.CompareTo(value);
					num <<= 14;
					num2 = num4;
					num ^= 3539189557U;
				}
				num = 481377925U / num;
				return num2;
			}

			// Token: 0x060037A4 RID: 14244 RVA: 0x0002F3E4 File Offset: 0x0002D5E4
			public void method_3(byte byte_0, int int_2, int int_3)
			{
				uint num = 1287330379U;
				do
				{
					List<GClass6.Class738> list = this.list_0;
					num = 409432120U * num;
					num = 2097313349U % num;
					num = (1692741595U | num);
					GClass6.Class738 item = new GClass6.Class738(byte_0, int_2, int_3);
					num *= 1483540710U;
					list.Add(item);
				}
				while (num >= 1372539055U);
			}

			// Token: 0x060037A5 RID: 14245 RVA: 0x0002F434 File Offset: 0x0002D634
			public List<GClass6.Class738> method_4()
			{
				return this.list_0;
			}

			// Token: 0x040000E8 RID: 232
			private int int_0;

			// Token: 0x040000E9 RID: 233
			private int int_1;

			// Token: 0x040000EA RID: 234
			private List<GClass6.Class738> list_0 = new List<GClass6.Class738>();
		}

		// Token: 0x02000301 RID: 769
		// (Invoke) Token: 0x060037A7 RID: 14247
		internal delegate void Delegate0();
	}
}
