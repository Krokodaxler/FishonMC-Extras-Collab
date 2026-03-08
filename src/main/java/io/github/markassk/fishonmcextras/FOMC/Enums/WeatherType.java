package io.github.markassk.fishonmcextras.FOMC.Enums;

import net.minecraft.text.Text;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum WeatherType implements EnumConstant {
    RAIN("☂", Text.literal("☂"), 0x5555FF),
    SUN("☀", Text.literal("☀"), 0xFFFF55),
    THUNDERSTORM("⚡", Text.literal("⚡"), 0xFFFF55),
    BLOOMINGOASIS("♣", Text.literal("♣"), 0xFC54FC),
    FABLEDWEATHER("⭐", Text.literal("⭐"), 0xF7453E),
    GOLDRUSH("⚠", Text.literal("⚠"), 0xF7EA3E),
    MOON("○", Text.literal("○"), 0x5FC0E6),
    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static final EnumLookup<WeatherType> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    WeatherType(String id, Text tag, int color) {
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
