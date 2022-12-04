package wily.betterfurnaces.init;

import com.mojang.datafixers.types.Type;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import wily.betterfurnaces.BetterFurnacesReforged;
import wily.betterfurnaces.blockentity.*;
import wily.betterfurnaces.blocks.*;
import wily.betterfurnaces.inventory.*;
import wily.betterfurnaces.items.*;
import wily.betterfurnaces.recipes.CobblestoneGeneratorRecipes;
import wily.factoryapi.FactoryAPIPlatform;

public class Registration {

    public static final DeferredRegister<Block> BLOCK_ITEMS = DeferredRegister.create(BetterFurnacesReforged.MOD_ID, Registry.BLOCK_REGISTRY);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BetterFurnacesReforged.MOD_ID, Registry.ITEM_REGISTRY);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BetterFurnacesReforged.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BetterFurnacesReforged.MOD_ID, Registry.MENU_REGISTRY);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPES_SEREALIZERS = DeferredRegister.create( BetterFurnacesReforged.MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY);
    private static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create( BetterFurnacesReforged.MOD_ID,Registry.RECIPE_TYPE_REGISTRY);

    public static void init() {
        BLOCK_ITEMS.register();
        BLOCK_ITEMS.forEach((b)-> ITEMS.getRegistrar().register( b.getId(),() -> new BlockItem(b.get(), defaultItemProperties())));
        ITEMS.register();
        BLOCK_ENTITIES.register();
        CONTAINERS.register();
        RECIPES_SEREALIZERS.register();
        RECIPES.register();


    }


    private static Item.Properties defaultItemProperties(){ return  new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP);}
    private static Type<?> blockEntityType(String name){
        return Util.fetchChoiceType(References.BLOCK_ENTITY, name);
    }

    public static final RegistrySupplier<RecipeSerializer<CobblestoneGeneratorRecipes>> COB_GENERATION_SERIALIZER = RECIPES_SEREALIZERS.register("rock_generating", () -> CobblestoneGeneratorRecipes.SERIALIZER);

    public static final RegistrySupplier<RecipeType<CobblestoneGeneratorRecipes>> ROCK_GENERATING_RECIPE = RECIPES.register("rock_generating", () -> new RecipeType<>() {});

    public static final RegistrySupplier<IronFurnaceBlock> IRON_FURNACE = BLOCK_ITEMS.register(IronFurnaceBlock.IRON_FURNACE, () -> new IronFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<BlockEntityType<IronFurnaceBlockEntity>> IRON_FURNACE_TILE = BLOCK_ENTITIES.register(IronFurnaceBlock.IRON_FURNACE, () -> BlockEntityType.Builder.of(IronFurnaceBlockEntity::new, IRON_FURNACE.get()).build(blockEntityType(IronFurnaceBlock.IRON_FURNACE)));

    public static final RegistrySupplier<MenuType<IronFurnaceMenu>> IRON_FURNACE_CONTAINER = CONTAINERS.register(IronFurnaceBlock.IRON_FURNACE, () -> MenuRegistry.ofExtended((windowId, inv, data) -> new IronFurnaceMenu(windowId, inv.player.level, data.readBlockPos(), inv, inv.player)));

    public static final RegistrySupplier<GoldFurnaceBlock> GOLD_FURNACE = BLOCK_ITEMS.register(GoldFurnaceBlock.GOLD_FURNACE, () -> new GoldFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistrySupplier<BlockEntityType<GoldFurnaceBlockEntity>> GOLD_FURNACE_TILE = BLOCK_ENTITIES.register(GoldFurnaceBlock.GOLD_FURNACE, () -> BlockEntityType.Builder.of(GoldFurnaceBlockEntity::new, GOLD_FURNACE.get()).build(blockEntityType(GoldFurnaceBlock.GOLD_FURNACE)));

    public static final RegistrySupplier<MenuType<GoldFurnaceMenu>> GOLD_FURNACE_CONTAINER = CONTAINERS.register(GoldFurnaceBlock.GOLD_FURNACE, () -> MenuRegistry.ofExtended((windowId, inv, data) -> new GoldFurnaceMenu(windowId, inv.player.level, data.readBlockPos(), inv, inv.player)));

    public static final RegistrySupplier<MenuType<ColorUpgradeItem.ContainerColorUpgrade>> COLOR_UPGRADE_CONTAINER = CONTAINERS.register("color_upgrade", () -> MenuRegistry.ofExtended((windowId, inv, data) -> new ColorUpgradeItem.ContainerColorUpgrade(windowId, inv, inv.player.getMainHandItem())));

    public static final RegistrySupplier<DiamondFurnaceBlock> DIAMOND_FURNACE = BLOCK_ITEMS.register(DiamondFurnaceBlock.DIAMOND_FURNACE, () -> new DiamondFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistrySupplier<BlockEntityType<DiamondFurnaceBlockEntity>> DIAMOND_FURNACE_TILE = BLOCK_ENTITIES.register(DiamondFurnaceBlock.DIAMOND_FURNACE, () -> BlockEntityType.Builder.of(DiamondFurnaceBlockEntity::new, DIAMOND_FURNACE.get()).build(blockEntityType(DiamondFurnaceBlock.DIAMOND_FURNACE)));

    public static final RegistrySupplier<MenuType<DiamondFurnaceMenu>> DIAMOND_FURNACE_CONTAINER = CONTAINERS.register(DiamondFurnaceBlock.DIAMOND_FURNACE, () -> MenuRegistry.ofExtended((windowId, inv, data) -> new DiamondFurnaceMenu(windowId, inv.player.level, data.readBlockPos(), inv, inv.player)));

    public static final RegistrySupplier<NetherhotFurnaceBlock> NETHERHOT_FURNACE = BLOCK_ITEMS.register(NetherhotFurnaceBlock.NETHERHOT_FURNACE, () -> new NetherhotFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_BLOCK)));
    public static final RegistrySupplier<BlockEntityType<NetherhotFurnaceBlockEntity>> NETHERHOT_FURNACE_TILE = BLOCK_ENTITIES.register(NetherhotFurnaceBlock.NETHERHOT_FURNACE, () -> BlockEntityType.Builder.of(NetherhotFurnaceBlockEntity::new, NETHERHOT_FURNACE.get()).build(blockEntityType(NetherhotFurnaceBlock.NETHERHOT_FURNACE)));

    public static final RegistrySupplier<MenuType<NetherhotFurnaceMenu>> NETHERHOT_FURNACE_CONTAINER = CONTAINERS.register(NetherhotFurnaceBlock.NETHERHOT_FURNACE, () -> MenuRegistry.ofExtended((windowId, inv, data) -> new NetherhotFurnaceMenu(windowId, inv.player.level, data.readBlockPos(), inv, inv.player)));

    public static final RegistrySupplier<ExtremeFurnaceBlock> EXTREME_FURNACE = BLOCK_ITEMS.register(ExtremeFurnaceBlock.EXTREME_FURNACE, () -> new ExtremeFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).strength(20.0F, 3000.0F)));
    public static final RegistrySupplier<BlockEntityType<ExtremeFurnaceBlockEntity>> EXTREME_FURNACE_TILE = BLOCK_ENTITIES.register(ExtremeFurnaceBlock.EXTREME_FURNACE, () -> BlockEntityType.Builder.of(ExtremeFurnaceBlockEntity::new, EXTREME_FURNACE.get()).build(blockEntityType(ExtremeForgeBlock.EXTREME_FORGE)));

    public static final RegistrySupplier<MenuType<ExtremeFurnaceMenu>> EXTREME_FURNACE_CONTAINER = CONTAINERS.register(ExtremeFurnaceBlock.EXTREME_FURNACE, () -> MenuRegistry.ofExtended((windowId, inv, data) -> new ExtremeFurnaceMenu(windowId, inv.player.level, data.readBlockPos(), inv, inv.player)));

    public static final RegistrySupplier<ExtremeForgeBlock> EXTREME_FORGE = BLOCK_ITEMS.register(ExtremeForgeBlock.EXTREME_FORGE, () -> new ExtremeForgeBlock(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).strength(30.0F, 6000.0F)));
    public static final RegistrySupplier<BlockEntityType<ExtremeForgeBlockEntity>> EXTREME_FORGE_TILE = BLOCK_ENTITIES.register(ExtremeForgeBlock.EXTREME_FORGE, () -> BlockEntityType.Builder.of(ExtremeForgeBlockEntity::new, EXTREME_FORGE.get()).build(blockEntityType(IronFurnaceBlock.IRON_FURNACE)));

    public static final RegistrySupplier<MenuType<ExtremeForgeMenu>> EXTREME_FORGE_CONTAINER = CONTAINERS.register(ExtremeForgeBlock.EXTREME_FORGE, () -> MenuRegistry.ofExtended((windowId, inv, data) -> new ExtremeForgeMenu(windowId, inv.player.level, data.readBlockPos(), inv, inv.player)));

    public static final RegistrySupplier<CobblestoneGeneratorBlock> COBBLESTONE_GENERATOR = BLOCK_ITEMS.register(CobblestoneGeneratorBlock.COBBLESTONE_GENERATOR, () -> new CobblestoneGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE)));
    public static final RegistrySupplier<BlockEntityType<AbstractCobblestoneGeneratorBlockEntity.CobblestoneGeneratorBlockEntity>> COB_GENERATOR_TILE = BLOCK_ENTITIES.register(CobblestoneGeneratorBlock.COBBLESTONE_GENERATOR, () -> BlockEntityType.Builder.of(AbstractCobblestoneGeneratorBlockEntity.CobblestoneGeneratorBlockEntity::new, COBBLESTONE_GENERATOR.get()).build(blockEntityType(CobblestoneGeneratorBlock.COBBLESTONE_GENERATOR)));

    public static final RegistrySupplier<MenuType<AbstractCobblestoneGeneratorMenu.CobblestoneGeneratorMenu>> COB_GENERATOR_CONTAINER = CONTAINERS.register(CobblestoneGeneratorBlock.COBBLESTONE_GENERATOR, () -> MenuRegistry.ofExtended((windowId, inv, data) -> new AbstractCobblestoneGeneratorMenu.CobblestoneGeneratorMenu(windowId, inv.player.level, data.readBlockPos(), inv, inv.player)));

    public static final RegistrySupplier<FuelVerifierBlock> FUEL_VERIFIER = BLOCK_ITEMS.register(FuelVerifierBlock.FUEL_VERIFIER, () -> new FuelVerifierBlock(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistrySupplier<BlockEntityType<AbstractFuelVerifierBlockEntity.FuelVerifierBlockEntity>> FUEL_VERIFIER_TILE = BLOCK_ENTITIES.register(FuelVerifierBlock.FUEL_VERIFIER, () -> BlockEntityType.Builder.of(AbstractFuelVerifierBlockEntity.FuelVerifierBlockEntity::new, FUEL_VERIFIER.get()).build(blockEntityType(FuelVerifierBlock.FUEL_VERIFIER)));

    public static final RegistrySupplier<MenuType<AbstractFuelVerifierMenu.FuelVerifierMenu>> FUEL_VERIFIER_CONTAINER = CONTAINERS.register(FuelVerifierBlock.FUEL_VERIFIER, () -> MenuRegistry.ofExtended((windowId, inv, data) -> new AbstractFuelVerifierMenu.FuelVerifierMenu(windowId, inv.player.level,data.readBlockPos(), inv, inv.player) {
    }));


    public static final RegistrySupplier<ConductorBlock> IRON_CONDUCTOR_BLOCK = BLOCK_ITEMS.register("iron_conductor_block", () -> new ConductorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(8.0F, 20.0F)));

    public static final RegistrySupplier<ConductorBlock> GOLD_CONDUCTOR_BLOCK = BLOCK_ITEMS.register("gold_conductor_block", () -> new ConductorBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).strength(8.0F, 20.0F)));

    public static final RegistrySupplier<ConductorBlock> NETHERHOT_CONDUCTOR_BLOCK = BLOCK_ITEMS.register("netherhot_conductor_block", () -> new ConductorBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_BLOCK).strength(10.0F, 40.0F)));

    public static final RegistrySupplier<IronUpgradeItem> IRON_UPGRADE = ITEMS.register("iron_upgrade", () -> new IronUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP)));
    public static final RegistrySupplier<GoldUpgradeItem> GOLD_UPGRADE = ITEMS.register("gold_upgrade", () -> new GoldUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP)));
    public static final RegistrySupplier<DiamondUpgradeItem> DIAMOND_UPGRADE = ITEMS.register("diamond_upgrade", () -> new DiamondUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP)));
    public static final RegistrySupplier<NetherhotUpgradeItem> NETHERHOT_UPGRADE = ITEMS.register("netherhot_upgrade", () -> new NetherhotUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP)));
    public static final RegistrySupplier<ExtremeUpgradeItem> EXTREME_UPGRADE = ITEMS.register("extreme_upgrade", () -> new ExtremeUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP)));

    public static final RegistrySupplier<FuelEfficiencyUpgradeItem> FUEL = ITEMS.register("fuel_efficiency_upgrade", () -> new FuelEfficiencyUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1).durability(256),2));
    public static final RegistrySupplier<OreProcessingUpgradeItem> ORE_PROCESSING = ITEMS.register("ore_processing_upgrade", () -> new OreProcessingUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1).durability(128),2,true,false));
    public static final RegistrySupplier<OreProcessingUpgradeItem> RAWORE_PROCESSING = ITEMS.register("raw_ore_processing_upgrade", () -> new OreProcessingUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1),2,false,true));
    public static final RegistrySupplier<FuelEfficiencyUpgradeItem> ADVFUEL = ITEMS.register("advanced_fuel_efficiency_upgrade", () -> new FuelEfficiencyUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1),2));
    public static final RegistrySupplier<OreProcessingUpgradeItem> ADVORE_PROCESSING = ITEMS.register("advanced_ore_processing_upgrade", () -> new OreProcessingUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1),2,true,false));
    public static final RegistrySupplier<FactoryUpgradeItem> FACTORY = ITEMS.register("factory_upgrade", () -> new FactoryUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1), "factory", true,true,true,true));
    public static final RegistrySupplier<FactoryUpgradeItem> PIPING = ITEMS.register("piping_upgrade", () -> new FactoryUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1), "piping", false,false,false,true));
    public static final RegistrySupplier<FactoryUpgradeItem> OUTPUT = ITEMS.register("autooutput_upgrade", () -> new FactoryUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1), "output", true,false,true,false));
    public static final RegistrySupplier<FactoryUpgradeItem> INPUT = ITEMS.register("autoinput_upgrade", () -> new FactoryUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1), "input", false,true,true,false));
    public static final RegistrySupplier<FactoryUpgradeItem> REDSTONE = ITEMS.register("redstone_signal_upgrade", () -> new FactoryUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1), "redstone", false,false,false,true));
    public static final RegistrySupplier<ColorUpgradeItem> COLOR = ITEMS.register("color_upgrade", () -> new ColorUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1),"color"));
    public static final RegistrySupplier<LiquidFuelUpgradeItem> LIQUID = ITEMS.register("liquid_fuel_upgrade", () -> new LiquidFuelUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1),"liquid"));
    public static final RegistrySupplier<EnergyFuelUpgradeItem> ENERGY = ITEMS.register("energy_upgrade", () -> new EnergyFuelUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1), Component.translatable("tooltip." + BetterFurnacesReforged.MOD_ID + ".upgrade.energy", FactoryAPIPlatform.getPlatformEnergyComponent().getString()).setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY)))));
    public static final RegistrySupplier<XpTankUpgradeItem> XP = ITEMS.register("xp_tank_upgrade", () -> new XpTankUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1),"xp"));
    public static final RegistrySupplier<TypeUpgradeItem> BLAST = ITEMS.register("blasting_upgrade", () -> new TypeUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1),"blasting"));
    public static final RegistrySupplier<TypeUpgradeItem> SMOKE = ITEMS.register("smoking_upgrade", () -> new TypeUpgradeItem(new Item.Properties().tab(BetterFurnacesReforged.ITEM_GROUP).stacksTo(1),"smoking"));




}
