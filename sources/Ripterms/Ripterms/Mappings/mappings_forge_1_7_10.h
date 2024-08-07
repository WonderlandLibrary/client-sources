#pragma once
namespace Mappings
{
	using namespace nlohmann::literals;
	static const nlohmann::json* const mappings_forge_1_7_10 = new const nlohmann::json(R"(
{
	"java/lang/Object": {
		"obfuscated": "java/lang/Object",
		"fields": [],
		"methods": [
			{
				"name": "equals",
				"obfuscated": "equals",
				"signature": "(Ljava/lang/Object;)Z",
				"static": false
			}
		]
	},
	"java/util/Collection": {
		"obfuscated": "java/util/Collection",
		"fields": [],
		"methods": [
			{
				"name": "toArray",
				"obfuscated": "toArray",
				"signature": "()[Ljava/lang/Object;",
				"static": false
			},
			{
				"name": "clear",
				"obfuscated": "clear",
				"signature": "()V",
				"static": false
			},
			{
				"name": "add",
				"obfuscated": "add",
				"signature": "(Ljava/lang/Object;)Z",
				"static": false
			}
		]
	},
	"java/util/List": {
		"obfuscated": "java/util/List",
		"fields": [],
		"methods": []
	},
	"java/util/Map": {
		"obfuscated": "java/util/Map",
		"fields": [],
		"methods": [
			{
				"name": "put",
				"obfuscated": "put",
				"signature": "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
				"static": false
			},
			{
				"name": "get",
				"obfuscated": "get",
				"signature": "(Ljava/lang/Object;)Ljava/lang/Object;",
				"static": false
			}
		]
	},
	"java/lang/String": {
		"obfuscated": "java/lang/String",
		"fields": [],
		"methods": []
	},
	"java/lang/System": {
		"obfuscated": "java/lang/System",
		"fields": [],
		"methods": [
			{
				"name": "gc",
				"obfuscated": "gc",
				"signature": "()V",
				"static": true
			}
		]
	},
	"io/github/lefraudeur/ClassPatcher": {
		"obfuscated": "io/github/lefraudeur/ClassPatcher",
		"fields": [],
		"methods": [
			{
				"name": "patchEntityRenderer",
				"obfuscated": "patchEntityRenderer",
				"signature": "([BLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)[B",
				"static": true
			},
			{
				"name": "patchClientBrandRetriever",
				"obfuscated": "patchClientBrandRetriever",
				"signature": "([BLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)[B",
				"static": true
			},
			{
				"name": "patchNetworkManager",
				"obfuscated": "patchNetworkManager1_7_10",
				"signature": "([BLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)[B",
				"static": true
			},
			{
				"name": "patchBlock",
				"obfuscated": "patchBlock",
				"signature": "([BLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)[B",
				"static": true
			},
			{
				"name": "patchGL11",
				"obfuscated": "patchGL11",
				"signature": "([B)[B",
				"static": true
			},
			{
				"name": "patchMethod",
				"obfuscated": "patchMethod",
				"signature": "([BLjava/lang/String;Ljava/lang/String;Ljava/lang/String;II)[B",
				"static": true
			}
		]
	},
	"org/apache/logging/log4j/ThreadContext": {
		"obfuscated": "org/apache/logging/log4j/ThreadContext",
		"fields": [
			{
				"name": "EMPTY_MAP",
				"obfuscated": "EMPTY_MAP",
				"signature": "Ljava/util/Map;",
				"static": true
			}
		],
		"methods": []
	},
	"net/minecraft/client/Minecraft": {
		"obfuscated": "net/minecraft/client/Minecraft",
		"fields": [
			{
				"name": "theMinecraft",
				"obfuscated": "field_71432_P",
				"signature": "Lnet/minecraft/client/Minecraft;",
				"static": true
			},
			{
				"name": "thePlayer",
				"obfuscated": "field_71439_g",
				"signature": "Lnet/minecraft/client/entity/EntityClientPlayerMP;",
				"static": false
			},
			{
				"name": "theWorld",
				"obfuscated": "field_71441_e",
				"signature": "Lnet/minecraft/client/multiplayer/WorldClient;",
				"static": false
			},
			{
				"name": "gameSettings",
				"obfuscated": "field_71474_y",
				"signature": "Lnet/minecraft/client/settings/GameSettings;",
				"static": false
			},
			{
				"name": "objectMouseOver",
				"obfuscated": "field_71476_x",
				"signature": "Lnet/minecraft/util/MovingObjectPosition;",
				"static": false
			},
			{
				"name": "rightClickDelayTimer",
				"obfuscated": "field_71467_ac",
				"signature": "I",
				"static": false
			},
			{
				"name": "pointedEntity",
				"obfuscated": "field_147125_j",
				"signature": "Lnet/minecraft/entity/Entity;",
				"static": false
			},
			{
				"name": "timer",
				"obfuscated": "field_71428_T",
				"signature": "Lnet/minecraft/util/Timer;",
				"static": false
			}
		],
		"methods": [
		]
	},
	"net/minecraft/client/entity/EntityClientPlayerMP": {
		"obfuscated": "net/minecraft/client/entity/EntityClientPlayerMP",
		"fields": [],
		"methods": [
			{
				"name": "onUpdateWalkingPlayer",
				"obfuscated": "func_71166_b",
				"signature": "()V",
				"static": false
			}
		]
	},
	"net/minecraft/client/entity/EntityPlayerSP": {
		"obfuscated": "net/minecraft/client/entity/EntityPlayerSP",
		"fields": [],
		"methods": []
	},
	"net/minecraft/client/entity/AbstractClientPlayer": {
		"obfuscated": "net/minecraft/client/entity/AbstractClientPlayer",
		"fields": [],
		"methods": []
	},
	"net/minecraft/entity/player/EntityPlayer": {
		"obfuscated": "net/minecraft/entity/player/EntityPlayer",
		"fields": [],
		"methods": [
			{
				"name": "attackTargetEntityWithCurrentItem",
				"obfuscated": "func_71059_n",
				"signature": "(Lnet/minecraft/entity/Entity;)V",
				"static": false
			}
		]
	},
	"net/minecraft/entity/EntityLivingBase": {
		"obfuscated": "net/minecraft/entity/EntityLivingBase",
		"fields": [],
		"methods": []
	},
	"net/minecraft/entity/Entity": {
		"obfuscated": "net/minecraft/entity/Entity",
		"fields": [
			{
				"name": "posX",
				"obfuscated": "field_70165_t",
				"signature": "D",
				"static": false
			},
			{
				"name": "posY",
				"obfuscated": "field_70163_u",
				"signature": "D",
				"static": false
			},
			{
				"name": "posZ",
				"obfuscated": "field_70161_v",
				"signature": "D",
				"static": false
			},
			{
				"name": "rotationYaw",
				"obfuscated": "field_70177_z",
				"signature": "F",
				"static": false
			},
			{
				"name": "rotationPitch",
				"obfuscated": "field_70125_A",
				"signature": "F",
				"static": false
			},
			{
				"name": "motionX",
				"obfuscated": "field_70159_w",
				"signature": "D",
				"static": false
			},
			{
				"name": "motionY",
				"obfuscated": "field_70181_x",
				"signature": "D",
				"static": false
			},
			{
				"name": "motionZ",
				"obfuscated": "field_70179_y",
				"signature": "D",
				"static": false
			},
			{
				"name": "hurtResistantTime",
				"obfuscated": "field_70172_ad",
				"signature": "I",
				"static": false
			},
			{
				"name": "ticksExisted",
				"obfuscated": "field_70173_aa",
				"signature": "I",
				"static": false
			},
			{
				"name": "boundingBox",
				"obfuscated": "field_70121_D",
				"signature": "Lnet/minecraft/util/AxisAlignedBB;",
				"static": false
			},
			{
				"name": "lastTickPosX",
				"obfuscated": "field_70142_S",
				"signature": "D",
				"static": false
			},
			{
				"name": "lastTickPosY",
				"obfuscated": "field_70137_T",
				"signature": "D",
				"static": false
			},
			{
				"name": "lastTickPosZ",
				"obfuscated": "field_70136_U",
				"signature": "D",
				"static": false
			},
			{
				"name": "onGround",
				"obfuscated": "field_70122_E",
				"signature": "Z",
				"static": false
			}
		],
		"methods": [
			{
				"name": "getEyeHeight",
				"obfuscated": "func_70047_e",
				"signature": "()F",
				"static": false
			},
			{
				"name": "isSprinting",
				"obfuscated": "func_70051_ag",
				"signature": "()Z",
				"static": false
			},
			{
				"name": "setSprinting",
				"obfuscated": "func_70031_b",
				"signature": "(Z)V",
				"static": false
			}
		]
	},
	"net/minecraft/client/multiplayer/WorldClient": {
		"obfuscated": "net/minecraft/client/multiplayer/WorldClient",
		"fields": [],
		"methods": []
	},
	"net/minecraft/world/World": {
		"obfuscated": "net/minecraft/world/World",
		"fields": [
			{
				"name": "playerEntities",
				"obfuscated": "field_73010_i",
				"signature": "Ljava/util/List;",
				"static": false
			}
		],
		"methods": []
	},
	"net/minecraft/util/Vec3": {
		"obfuscated": "net/minecraft/util/Vec3",
		"fields": [
			{
				"name": "xCoord",
				"obfuscated": "field_72450_a",
				"signature": "D",
				"static": false
			},
			{
				"name": "yCoord",
				"obfuscated": "field_72448_b",
				"signature": "D",
				"static": false
			},
			{
				"name": "zCoord",
				"obfuscated": "field_72449_c",
				"signature": "D",
				"static": false
			}
		],
		"methods": []
	},
	"net/minecraft/client/renderer/EntityRenderer": {
		"obfuscated": "net/minecraft/client/renderer/EntityRenderer",
		"fields": [],
		"methods": [
			{
				"name": "getMouseOver",
				"obfuscated": "func_78473_a",
				"signature": "(F)V",
				"static": false
			}
		]
	},
	"net/minecraft/client/settings/GameSettings": {
		"obfuscated": "net/minecraft/client/settings/GameSettings",
		"fields": [
			{
				"name": "gammaSetting",
				"obfuscated": "field_74333_Y",
				"signature": "F",
				"static": false
			},
			{
				"name": "keyBindSneak",
				"obfuscated": "field_74311_E",
				"signature": "Lnet/minecraft/client/settings/KeyBinding;",
				"static": false
			},
			{
				"name": "keyBindSprint",
				"obfuscated": "field_151444_V",
				"signature": "Lnet/minecraft/client/settings/KeyBinding;",
				"static": false
			}
		],
		"methods": []
	},
	"net/minecraft/client/ClientBrandRetriever": {
		"obfuscated": "net/minecraft/client/ClientBrandRetriever",
		"fields": [],
		"methods": [
			{
				"name": "getClientModName",
				"obfuscated": "getClientModName",
				"signature": "()Ljava/lang/String;",
				"static": true
			}
		]
	},
	"org/apache/logging/log4j/ThreadContext": {
		"obfuscated": "org/apache/logging/log4j/ThreadContext",
		"fields": [
			{
				"name": "EMPTY_MAP",
				"obfuscated": "EMPTY_MAP",
				"signature": "Ljava/util/Map;",
				"static": true
			}
		],
		"methods": []
	},
	"net/minecraft/network/NetworkManager": {
		"obfuscated": "net/minecraft/network/NetworkManager",
		"fields": [],
		"methods": [
			{
				"name": "sendPacket",
				"obfuscated": "func_150732_b",
				"signature": "(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V",
				"static": false
			}
		]
	},
	"net/minecraft/network/Packet": {
		"obfuscated": "net/minecraft/network/Packet",
		"fields": [],
		"methods": []
	},
	"net/minecraft/util/MovingObjectPosition": {
		"obfuscated": "net/minecraft/util/MovingObjectPosition",
		"fields": [
			{
				"name": "typeOfHit",
				"obfuscated": "field_72313_a",
				"signature": "Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;",
				"static": false
			}
		],
		"methods": []
	},
	"net/minecraft/util/MovingObjectPosition$MovingObjectType": {
		"obfuscated": "net/minecraft/util/MovingObjectPosition$MovingObjectType",
		"fields": [
			{
				"name": "BLOCK",
				"obfuscated": "BLOCK",
				"signature": "Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;",
				"static": true
			}
		],
		"methods": []
	},
	"net/minecraft/block/Block": {
		"obfuscated": "net/minecraft/block/Block",
		"fields": [],
		"methods": [
			{
				"name": "shouldSideBeRendered",
				"obfuscated": "func_149646_a",
				"signature": "(Lnet/minecraft/world/IBlockAccess;IIII)Z",
				"static": false
			}
		]
	},
	"net/minecraft/util/registry/Registry": {
		"obfuscated": "net/minecraft/block/Block",
		"fields": [
			{
				"name": "blockRegistry",
				"obfuscated": "field_149771_c",
				"signature": "Lnet/minecraft/util/RegistryNamespaced;",
				"static": true
			}
		],
		"methods": []
	},
	"net/minecraft/util/RegistryNamespaced": {
		"obfuscated": "net/minecraft/util/RegistryNamespaced",
		"fields": [],
		"methods": [
			{
				"name": "getNameForObject",
				"obfuscated": "func_148750_c",
				"signature": "(Ljava/lang/Object;)Ljava/lang/String;",
				"static": false
			}
		]
	},
	"net/minecraft/util/AxisAlignedBB": {
		"obfuscated": "net/minecraft/util/AxisAlignedBB",
		"fields": [
			{
				"name": "minX",
				"obfuscated": "field_72340_a",
				"signature": "D",
				"static": false
			},
			{
				"name": "minY",
				"obfuscated": "field_72338_b",
				"signature": "D",
				"static": false
			},
			{
				"name": "minZ",
				"obfuscated": "field_72339_c",
				"signature": "D",
				"static": false
			},
			{
				"name": "maxX",
				"obfuscated": "field_72336_d",
				"signature": "D",
				"static": false
			},
			{
				"name": "maxY",
				"obfuscated": "field_72337_e",
				"signature": "D",
				"static": false
			},
			{
				"name": "maxZ",
				"obfuscated": "field_72334_f",
				"signature": "D",
				"static": false
			}
		],
		"methods": []
	},
	"net/minecraft/util/Timer": {
		"obfuscated": "net/minecraft/util/Timer",
		"fields": [
			{
				"name": "renderPartialTicks",
				"obfuscated": "field_74281_c",
				"signature": "F",
				"static": false
			}
		],
		"methods": []
	},
	"net/minecraft/block/BlockAir": {
		"obfuscated": "net/minecraft/block/BlockAir",
		"fields": [],
		"methods": []
	},
	"net/minecraft/client/settings/KeyBinding": {
		"obfuscated": "net/minecraft/client/settings/KeyBinding",
		"fields": [
			{
				"name": "pressed",
				"obfuscated": "field_74513_e",
				"signature": "Z",
				"static": false
			},
			{
				"name": "pressTime",
				"obfuscated": "field_151474_i",
				"signature": "I",
				"static": false
			}
		],
		"methods": []
	},
	"net/minecraft/world/IBlockAccess": {
		"obfuscated": "net/minecraft/world/IBlockAccess",
		"fields": [],
		"methods": [
			{
				"name": "getBlock",
				"obfuscated": "func_147439_a",
				"signature": "(III)Lnet/minecraft/block/Block;",
				"static": false
			}
		]
	}
}
)"_json);
}