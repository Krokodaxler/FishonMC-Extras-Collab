package io.github.markassk.fishonmcextras.FOMC.Enums;

import io.github.markassk.fishonmcextras.FOMC.Types.Defaults;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum FishVariant implements EnumConstant {
    NORMAL("normal", Text.empty(), Defaults.DEFAULT_COLOR),
    ALBINO("albino", Text.literal("\uF041").formatted(Formatting.WHITE), 0xc4c19f),
    MELANISTIC("melanistic", Text.literal("\uF042").formatted(Formatting.WHITE), 0x1c1c1c),
    TROPHY("trophy", Text.literal("\uF043").formatted(Formatting.WHITE), 0xd5bf3b),
    FABLED("fabled", Text.literal("\uF044").formatted(Formatting.WHITE), 0x82171e),

    // Event Variants
    ALTERNATE("alternate", Text.literal("\uF098").formatted(Formatting.WHITE), 0x9cb4fc),
    SPOOKY("spooky", Text.literal("\uF102").formatted(Formatting.WHITE), 0x2e2e8f),
    FROZEN("frozen", Text.literal("\uF179").formatted(Formatting.WHITE), 0x7fb0e7),

    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static final EnumLookup<FishVariant> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    FishVariant(String id, Text tag, int color) {
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
