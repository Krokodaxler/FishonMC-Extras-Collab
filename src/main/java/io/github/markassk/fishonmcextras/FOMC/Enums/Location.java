package io.github.markassk.fishonmcextras.FOMC.Enums;

import io.github.markassk.fishonmcextras.util.TextHelper;
import net.minecraft.text.Text;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum Location implements EnumConstant {
    CYPRESS_LAKE("spawn",
            Text.literal("Cypress Lake").withColor(0x5CAE65),
            0x5CAE65,
            Climate.SUBTROPICAL, WaterType.FRESHWATER),

    KENAI_RIVER("kenai",
            Text.literal("Kenai River").withColor(0x68D499),
            0x68D499,
            Climate.SUBARCTIC, WaterType.FRESHWATER),

    LAKE_BIWA("biwa",
            Text.literal("Lake Biwa").withColor(0xFBC0FA),
            0xFBC0FA,
            Climate.SUBTROPICAL, WaterType.FRESHWATER),

    MURRAY_RIVER("murray",
            Text.literal("Murray River").withColor(0xCD5916),
            0xCD5916,
            Climate.SEMI_ARID, WaterType.FRESHWATER),

    EVERGLADES("everglades",
            Text.literal("Everglades").withColor(0x2EBB8D),
            0x2EBB8D,
            Climate.SAVANNA, WaterType.FRESHWATER),

    KEY_WEST("keywest",
            Text.literal("Key West").withColor(0xFBF17C),
            0xFBF17C,
            Climate.SAVANNA, WaterType.SALTWATER),

    TOLEDO_BEND("toledobend",
            Text.literal("Toledo Bend Reservoir").withColor(0x99A7D0),
            0x99A7D0,
            Climate.SUBTROPICAL, WaterType.FRESHWATER),

    GREAT_LAKES("greatlakes",
            Text.literal("Great Lakes").withColor(0x3CABF3),
            0x3CABF3,
            Climate.CONTINENTAL, WaterType.FRESHWATER),

    DANUBE_RIVER("danube",
            Text.literal("Danube River").withColor(0xFBC598),
            0xFBC598,
            Climate.CONTINENTAL, WaterType.FRESHWATER),

    OIL_RIG("oilrig",
            Text.literal("Oil Rig").withColor(0xFCEB47),
            0xFCEB47,
            Climate.SAVANNA, WaterType.SALTWATER),

    AMAZON_RIVER("amazon",
            Text.literal("Amazon River").withColor(0x3EA729),
            0x3EA729,
            Climate.RAINFOREST, WaterType.FRESHWATER),

    MEDITERRANEAN_SEA("mediterranean",
            Text.literal("Mediterranean Sea").withColor(0xF0FB37),
            0xF0FB37,
            Climate.MEDITERRANEAN, WaterType.SALTWATER),

    CAPE_COD("capecod",
            Text.literal("Cape Cod").withColor(0xBBF5FB),
            0xBBF5FB,
            Climate.OCEANIC, WaterType.SALTWATER),

    HAWAII("hawaii", TextHelper.concat(
            Text.literal("H").withColor(0xFB933B),
            Text.literal("a").withColor(0xFCB140),
            Text.literal("w").withColor(0xEACD4D),
            Text.literal("a").withColor(0xB2E66C),
            Text.literal("i").withColor(0x75F0A6),
            Text.literal("i").withColor(0x35F4EF)),
            0x35F4EF,
            Climate.SAVANNA, WaterType.SALTWATER),

    LOFOTEN_ISLANDS("lofoten", TextHelper.concat(
            Text.literal("L").withColor(0xCDDAD7),
            Text.literal("o").withColor(0xCCDFD2),
            Text.literal("f").withColor(0xCCE3CC),
            Text.literal("o").withColor(0xCBE8C7),
            Text.literal("t").withColor(0xCBECC1),
            Text.literal("e").withColor(0xCAF1BC),
            Text.literal("n ").withColor(0xCFF2C3),
            Text.literal("I").withColor(0xD9F3D0),
            Text.literal("s").withColor(0xDEF4D7),
            Text.literal("l").withColor(0xE4F5DD),
            Text.literal("a").withColor(0xE9F6E4),
            Text.literal("n").withColor(0xEEF6EB),
            Text.literal("d").withColor(0xF3F7F1),
            Text.literal("s").withColor(0xF8F8F8)),
            0xF8F8F8,
            Climate.SUBARCTIC, WaterType.SALTWATER),

    CAIRNS("cairns",
            Text.literal("Cairns").withColor(0xA1C2FB),
            0xA1C2FB,
            Climate.MONSOON, WaterType.SALTWATER),

    SPAWNHUB(INTERNAL_PREFIX + "spawnhub",
            CYPRESS_LAKE.TAG,
            CYPRESS_LAKE.COLOR,
            CYPRESS_LAKE.CLIMATE, CYPRESS_LAKE.WATER),

    CREW_ISLAND(INTERNAL_PREFIX + "crewisland",
            Text.literal("Crew Island"),
            DEFAULT_COLOR,
            Climate.UNKNOWN, WaterType.UNKNOWN),

    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR, Climate.UNKNOWN, WaterType.UNKNOWN);

    public static final EnumLookup<Location> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;
    public final Climate CLIMATE;
    public final WaterType WATER;

    Location(String id, Text tag, int color, Climate climate, WaterType water) {
        this.ID = id;
        this.TAG = tag;
        this.COLOR = color;
        this.CLIMATE = climate;
        this.WATER = water;
    }

    @Override
    public String id() {
        return this.ID;
    }

    @Override
    public Text tag() {
        return this.TAG;
    }

    @Override
    public int color() {
        return this.COLOR;
    }

    @Override
    public String toString() {
        return this.ID;
    }
}
