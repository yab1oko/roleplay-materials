package yblk.roleplay_materials.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yblk.roleplay_materials.Roleplay_materials;
import yblk.roleplay_materials.item.KeyItem;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Roleplay_materials.MODID);

    public static final RegistryObject<Item> SCRAP = item("scrap");
    public static final RegistryObject<Item> WOOD = item("wood");
    public static final RegistryObject<Item> PLANK = item("plank");
    public static final RegistryObject<Item> CLOTH = item("cloth");
    public static final RegistryObject<Item> METAL_PIPE = item("metal_pipe");
    public static final RegistryObject<Item> NAILBOX = item("nailbox");
    public static final RegistryObject<Item> GLUE = item("glue");
    public static final RegistryObject<Item> TAPE = item("tape");
    public static final RegistryObject<Item> SEWING_KIT = item("sewing_kit");
    public static final RegistryObject<Item> CHEMICAL = item("chemical");
    public static final RegistryObject<Item> PEBBLE = item("pebble");
    public static final RegistryObject<Item> OLD_MONEY = item("old_money");
    public static final RegistryObject<Item> TALON_TICKET = item("talon_ticket");
    public static final RegistryObject<Item> KEY = ITEMS.register("key",
            () -> new KeyItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FORGED_IRON_NUGGET = item("forged_iron_nugget");
    public static final RegistryObject<Item> FORGED_IRON_INGOT = item("forged_iron_ingot");
    public static final RegistryObject<Item> FORGED_IRON_PLATE = item("forged_iron_plate");
    public static final RegistryObject<Item> CIRCUIT = item("circuit");
    public static final RegistryObject<Item> MOTOR = item("motor");
    public static final RegistryObject<Item> MACE_PART = item("mace_part");
    public static final RegistryObject<Item> CIRCULAR_SAW = item("circular_saw");
    public static final RegistryObject<Item> SAW_SWORD_PART = item("saw_sword_part");
    public static final RegistryObject<Item> MACHETE_PART = item("machete_part");
    public static final RegistryObject<Item> SPEAR_STEEL_HEAD = item("spear_steel_head");
    public static final RegistryObject<Item> BATTERY = item("battery");
    public static final RegistryObject<Item> GAS_FILTER = item("gas_filter");

    private ModItems() {
    }

    private static RegistryObject<Item> item(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }
}
