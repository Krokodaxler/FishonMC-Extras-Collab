package io.github.markassk.fishonmcextras.FOMC.Enums;

import net.minecraft.text.Text;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

public enum FishSize implements EnumConstant {
    BABY("baby", Text.literal("ʙᴀʙʏ").withColor(0x468CE7), 0x468CE7),
    JUVENILE("juvenile", Text.literal("ᴊᴜᴠᴇɴɪʟᴇ").withColor(0x22EA08), 0x22EA08),
    ADULT("adult", Text.literal("ᴀᴅᴜʟᴛ").withColor(0x1C7DA0), 0x1C7DA0),
    LARGE("large", Text.literal("ʟᴀʀɢᴇ").withColor(0xFF9000), 0xFF9000),
    GIGANTIC("gigantic", Text.literal("ɢɪɢᴀɴᴛɪᴄ").withColor(0xAF3333), 0xAF3333),
    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static final EnumLookup<FishSize> LOOKUP = new EnumLookup<>(values());

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    FishSize(String id, Text tag, int color) {
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
