package io.github.markassk.fishonmcextras.FOMC.Enums;

import net.minecraft.text.Text;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

// separate for rod parts and location/bait?
public enum WaterType implements EnumConstant {
    FRESHWATER("freshwater", Text.literal("Freshwater").withColor(0x3F87EF), 0x3F87EF),
    SALTWATER("saltwater", Text.literal("Saltwater").withColor(0x86D9E6), 0x86D9E6),
    ANY("any", Text.literal("Any"), DEFAULT_COLOR),
    GLOBAL("global", Text.literal("Anywhere"), DEFAULT_COLOR),
    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static final EnumLookup<WaterType> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    WaterType(String id, Text tag, int color) {
        this.ID = id;
        this.TAG = tag;
        this.COLOR = color;
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
