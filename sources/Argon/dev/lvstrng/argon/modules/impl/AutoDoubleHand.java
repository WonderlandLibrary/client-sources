package dev.lvstrng.argon.modules.impl;

// so did we just disappear with all the methods or what
public final class AutoDoubleHand { /*extends Module implements Render2DListener {
    private final BooleanSetting checkShield;
    private final BooleanSetting onPopSwitch;
    private final BooleanSetting lowHealthSwitch;
    private final IntSetting healthThreshold;
    private final BooleanSetting checkGround;
    private final BooleanSetting checkNearbyPlayers;
    private final IntSetting playerDistance;
    private final BooleanSetting predictCrystals;
    private final BooleanSetting checkAim;
    private final BooleanSetting checkHeldItems;
    private final IntSetting heightTrigger;
    private boolean isTotemActive;
    private boolean isHealthLow;

    public AutoDoubleHand() {
        super("Auto Double Hand", "Automatically switches to your totem when you're about to pop", 0, Category.field90);
        this.checkShield = new BooleanSetting("Check Shield", false).setDescription("Checks if you're blocking with a shield");
        this.onPopSwitch = new BooleanSetting("On Pop", false).setDescription("Switches to a totem if you pop");
        this.lowHealthSwitch = new BooleanSetting("On Health", false).setDescription("Switches to totem if low on health");
        this.healthThreshold = new IntSetting("Health", 1.0, 20.0, 2.0, 1.0).setDescription("Health to trigger at");
        this.checkGround = new BooleanSetting("On Ground", true).setDescription("Whether crystal damage is checked on ground or not");
        this.checkNearbyPlayers = new BooleanSetting("Check Players", true).setDescription("Checks for nearby players");
        this.playerDistance = new IntSetting("Distance", 1.0, 10.0, 5.0, 0.1).setDescription("Player distance");
        this.predictCrystals = new BooleanSetting("Predict Crystals", false);
        this.checkAim = new BooleanSetting("Check Aim", false).setDescription("Checks if the opponent is aiming at obsidian");
        this.checkHeldItems = new BooleanSetting("Check Items", false).setDescription("Checks if the opponent is holding crystals");
        this.heightTrigger = new IntSetting("Activates Above", 0.0, 4.0, 0.2, 0.1).setDescription("Height to trigger at");
        this.addSettings(new Setting[]{
                this.checkShield,
                this.onPopSwitch,
                this.lowHealthSwitch,
                this.healthThreshold,
                this.checkGround,
                this.checkNearbyPlayers,
                this.playerDistance,
                this.predictCrystals,
                this.checkAim,
                this.checkHeldItems,
                this.heightTrigger
        });
        this.isTotemActive = false;
        this.isHealthLow = false;
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(Render2DListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(Render2DListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender2D(final Render2DEvent event) {
        if (this.mc.player == null) {
            return;
        }

        final double playerDistanceSquared = this.playerDistance.getValue() * this.playerDistance.getValue();
        final PlayerInventory inventory = this.mc.player.getInventory();

        if (this.checkShield.getValue() && this.mc.player.isBlocking()) {
            return;
        }

        if (inventory.offHand.get(0).getItem() != Items.TOTEM_OF_UNDYING && this.onPopSwitch.getValue() && !this.isHealthLow) {
            this.isHealthLow = true;
            InventoryUtil.method309(Items.TOTEM_OF_UNDYING);
        }

        if (inventory.offHand.get(0).getItem() == Items.TOTEM_OF_UNDYING) {
            this.isHealthLow = false;
        }

        if (this.mc.player.getHealth() <= this.healthThreshold.getValue() && this.lowHealthSwitch.getValue() && !this.isTotemActive) {
            this.isTotemActive = true;
            InventoryUtil.method309(Items.TOTEM_OF_UNDYING);
        }

        if (this.mc.player.getHealth() > this.healthThreshold.getValue()) {
            this.isTotemActive = false;
        }

        if (this.mc.player.getHealth() > 19.0f) {
            return;
        }

        if (!this.checkGround.getValue() && this.mc.player.isOnGround()) {
            return;
        }

        if (this.checkNearbyPlayers.getValue() && this.mc.world.getPlayers().parallelStream().filter(this::isPlayerNearby).noneMatch(this::isPlayerInRange)) {
            return;
        }

        final double heightTriggerValue = this.heightTrigger.getValue();
        for (int heightCheck = (int) Math.floor(heightTriggerValue), i = 1; i <= heightCheck; ++i) {
            if (!this.mc.world.getBlockState(this.mc.player.getBlockPos().add(0, -i, 0)).isAir()) {
                return;
            }
        }

        final Vec3d playerPosition = this.mc.player.getPos();
        if (!this.mc.world.getBlockState(new BlockPos(new BlockPos((int) playerPosition.x, (int) playerPosition.y - (int) heightTriggerValue, (int) playerPosition.z))).isAir()) {
            return;
        }

        final List<Vec3d> crystalPositions = this.getCrystalPositions();
        final ArrayList<Vec3d> nearbyCrystals = new ArrayList<>();
        crystalPositions.forEach(Class63::addCrystalPosition);

        if (this.predictCrystals.getValue()) {
            Stream<Vec3d> crystalStream = BlockUtil.method260(this.mc.player.getBlockPos().add(-6, -8, -6), this.mc.player.getBlockPos().add(6, 2, 6))
                    .filter(this::isObsidianBlock).filter(CrystalUtil::isValidCrystal);

            if (this.checkAim.getValue()) {
                if (this.checkHeldItems.getValue()) {
                    crystalStream = crystalStream.filter(this::isAimingAtObsidian);
                } else {
                    crystalStream = crystalStream.filter(this::isAimingAtObsidianWithoutItems);
                }
            }
            crystalStream.forEachOrdered(Class63::recordCrystalPosition);
        }

        for (Vec3d crystalPos : nearbyCrystals) {
            if (DamageUtil.calculateDamage((PlayerEntity) this.mc.player, crystalPos, true, null, false) >= this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) {
                InventoryUtil.method309(Items.TOTEM_OF_UNDYING);
                break;
            }
        }
    }

    private List<Vec3d> getCrystalPositions() {
        final Vec3d playerPosition = this.mc.player.getPos();
        return this.mc.world.getEntitiesByClass(EndCrystalEntity.class, new Box(playerPosition.add(-6.0, -6.0, -6.0), playerPosition.add(6.0, 6.0, 6.0)), (Predicate<EndCrystalEntity>) Class63::isValidCrystalEntity);
    }

    private boolean isObsidianBlock(final BlockPos blockPos) {
        return this.mc.world.getPlayers().parallelStream().filter(this::isNearbyBlock).anyMatch(this::isObsidianOrBedrock);
    }

    private boolean isAimingAtObsidian(final BlockPos blockPos) {
        return this.mc.world.getPlayers().parallelStream().filter(this::isDifferentPlayer).filter(Class63::isPlayerHoldingCrystal).anyMatch(this::isAimingAtBlock);
    }

    private boolean isAimingAtBlock(final Vec3d[] position, final BlockHitResult[] hitResults, final BlockPos blockPos, final AbstractClientPlayerEntity playerEntity) {
        position[0] = RotationUtil.getPlayerEyes((PlayerEntity) playerEntity);
        hitResults[0] = this.mc.world.raycast(new RaycastContext(position[0], position[0].add(RotationUtil.getDirection((PlayerEntity) playerEntity).multiply(4.5)), RaycastContext$ShapeType.COLLIDER, RaycastContext$FluidHandling.NONE, (Entity) playerEntity));
        final BlockHitResult hitResult = hitResults[0];
        return hitResult != null && hitResult.getBlockPos().equals(blockPos);
    }*/
}
