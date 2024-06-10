#include "aimassist.hpp"

#include "../sdk/minecraft/entity/entity.hpp"

bool aimassist::c_aimassist::attach()
{
	HMODULE jvm = GetModuleHandleA("jvm.dll");

	using t_createdvms = jint(__stdcall*)(JavaVM**, jsize, jsize*);

	FARPROC processAddress = GetProcAddress(reinterpret_cast<HMODULE>(jvm), "JNI_GetCreatedJavaVMs");
	t_createdvms created_java_vms = reinterpret_cast<t_createdvms>(processAddress);

	auto ret = created_java_vms(&vm, 1, nullptr);

	if (ret != JNI_OK)
		return false;

	ret = vm->AttachCurrentThread(reinterpret_cast<void**>(&env), nullptr);

	if (ret != JNI_OK)
		return false;

	vm->GetEnv(reinterpret_cast<void**>(&jvmtiEnv), JVMTI_VERSION_1_1);

	if (!jvmtiEnv)
		return false;

	getKlasses();

	hook();

	return true;
}

void aimassist::c_aimassist::getKlasses()
{
	jint numberOfClasses;
	jclass* classes;

	jvmtiEnv->GetLoadedClasses(&numberOfClasses, &classes);

	for (int i = 0; i < numberOfClasses; i++)
	{
		jclass klass = env->FindClass("java/lang/Class");

		jmethodID getNameMid = env->GetMethodID(klass, "getName", "()Ljava/lang/String;");
		jstring name = (jstring)env->CallObjectMethod(classes[i], getNameMid);

		const char* str = env->GetStringUTFChars(name, 0);
		std::string klassName(str);
		env->ReleaseStringUTFChars(name, str);
		
		if (klassName._Equal("net.minecraft.client.Minecraft"))
		{
			klasses[klassName] = classes[i];
		}
		else
		{
			env->DeleteLocalRef(classes[i]);
		}

		// CODE FROM BEFORE AFTER IMPROVEMENTS MIGHT HAVE TO KEEP IT
		/*
		if (klassName.find("net.minecraft") != std::string::npos)
		{
			std::cout << klassName << "->" << classes[i] << std::endl;
			
			klasses[klassName] = classes[i];
		}
		else
		{
			env->DeleteLocalRef(classes[i]);
		}*/
	}
}

void aimassist::c_aimassist::hook()
{
	MH_Initialize();

	auto swapBuffersPointer = GetProcAddress(GetModuleHandleA("Gdi32.dll"), "SwapBuffers");

	MH_CreateHook(swapBuffersPointer, hooks::swapBuffersHook, reinterpret_cast<void**>(&hooks::oswapBuffers));
	MH_EnableHook(MH_ALL_HOOKS);

	hooks::originalWndproc = reinterpret_cast<WNDPROC>(SetWindowLongPtrA(FindWindow(L"LWJGL", nullptr), GWLP_WNDPROC, reinterpret_cast<LONG_PTR>(hooks::wndproc)));
}

float wrapTo180(float value)
{
	while (value >= 180.f)
		value -= 360.f;
	while (value < -180.f)
		value += 360.f;
	return value;
}

std::pair<float, float> getAngles(double posX1, double posY1, double posZ1, double posX2, double posY2, double posZ2)
{
	double x = posX2 - posX1;
	double y = posY2 - posY1;
	double z = posZ2 - posZ1;

	double hypothenuse = sqrt(x * x + z * z);
	float yaw = ((atan2(z, x)) * 180.f / M_PI) - 90.f;
	float pitch = (-atan2(y, hypothenuse)) * 180.f / M_PI;

	return std::make_pair(yaw, pitch);
}

void aimassist::c_aimassist::run()
{
	std::cout << "(" << klasses.size() << ") klasses found" << std::endl;

	std::cout << "Everything went as planed" << std::endl;

	sdk::instance = std::make_unique<sdk::c_minecraft>(env, klasses);

	while (loop)
	{

		if (GetAsyncKeyState(VK_INSERT) & 1)
			menuOpen = !menuOpen;

		if (GetAsyncKeyState(VK_DELETE) & 1)
			loop = false;

		auto localPlayer = std::make_shared<c_entity_player>(env, sdk::instance->getPlayer());
		auto world = std::make_shared<c_world>(env, sdk::instance->getWorld());

		if (localPlayer->getEntity() && world->getWorld())
		{
			double targetDistance = aimassist::instance->distance;
			std::shared_ptr<c_entity_player> target = nullptr;

			for (auto entity : world->getPlayers())
			{
				if (!env->IsSameObject(entity->getEntity(), localPlayer->getEntity()))
				{
					if (!aimassist::instance->teams || !localPlayer->isSameTeam(entity->getTeam()))
					{
						if (!localPlayer->isInvisible() || !aimassist::instance->checkInvisibles)
						{
							double distance = sqrt(pow(localPlayer->getPosY() - entity->getPosY(), 2) + pow(sqrt(pow(localPlayer->getPosX() - entity->getPosX(), 2) + pow(localPlayer->getPosZ() - entity->getPosZ(), 2)), 2));

							if (distance < targetDistance)
							{
								target = entity;
								targetDistance = distance;
							}
						}
					}

				}
			}


			std::string itemName = localPlayer->getCurrentItem();

			if (!aimassist::instance->itemWhitelist || itemName._Equal("item.swordIron") || itemName._Equal("item.swordWood") || itemName._Equal("item.swordStone") || itemName._Equal("item.swordDiamond") || itemName._Equal("item.swordGold") || itemName._Equal("item.fishingRod"))
			{
				if (aimassist::instance->enabled)
				{
					if (!aimassist::instance->requireClick || (GetKeyState(VK_LBUTTON) & 0x100) != 0)
					{
						if (target)
						{
							float my_yaw = localPlayer->getRotationYaw();
							float my_pitch = localPlayer->getRotationPitch();


							auto angles = getAngles(localPlayer->getPosX(), localPlayer->getPosY(), localPlayer->getPosZ(), target->getPosX(), target->getPosY(), target->getPosZ());
							auto fovyaw = wrapTo180(-(my_yaw - angles.first));
							auto fovpitch = wrapTo180(-(my_pitch - angles.second));

							if (abs(wrapTo180(-(my_yaw - angles.first))) <= aimassist::instance->fov)
							{

								// ANTICHEAT BYPASS METHOD 1 A BIT WEIRD MOUSE WIGGLING BUT WORKS

								auto device = std::random_device();
								auto engine = std::mt19937(device());
								auto gen = std::uniform_real_distribution<float>(fovyaw - aimassist::instance->randomAmount, fovpitch + aimassist::instance->randomAmount);

								float yaw = gen(engine);

								my_yaw += (yaw / (100 / aimassist::instance->speed)) * 0.25;


								/*auto device = std::random_device();
								auto engine = std::mt19937(device());
								auto gen = std::uniform_real_distribution<float>(aimassist::instance->speed - aimassist::instance->randomAmount, aimassist::instance->speed + aimassist::instance->randomAmount);
								float speed = gen(engine);

								//my_yaw += ((speed - 1) * 0.25);

								my_yaw += (fovyaw / (100 / speed)) * 0.25; //* aimassist::_rand_val(2, 3.2); /// aimassist::_rand_val(0.5,1.2);*/

								//my_yaw = yaw;

								std::cout << my_yaw << std::endl;

								localPlayer->setRotationYaw(my_yaw);
								Sleep(1);
								localPlayer->setPrevRotationYaw(my_yaw);
							}
						}
					}
				}
			}

			std::this_thread::sleep_for(std::chrono::milliseconds(5));
		}
	}
}

void aimassist::c_aimassist::unload()
{
	for (auto klass : klasses)
	{
		env->DeleteLocalRef(klass.second);
	}

	vm->DetachCurrentThread();

	ImGui_ImplOpenGL2_Shutdown();
	ImGui_ImplWin32_Shutdown();

	MH_DisableHook(MH_ALL_HOOKS);
	MH_Uninitialize();

	SetWindowLongPtrA(FindWindow(L"LWJGL", nullptr), GWLP_WNDPROC, (LONG_PTR)hooks::originalWndproc);

	env = nullptr;
	jvmtiEnv = nullptr;
	vm = nullptr;
}

std::unique_ptr<aimassist::c_aimassist> aimassist::instance;