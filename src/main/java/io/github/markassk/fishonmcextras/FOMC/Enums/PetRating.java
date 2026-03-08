package io.github.markassk.fishonmcextras.FOMC.Enums;

import net.minecraft.text.Text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.TreeMap;

import static io.github.markassk.fishonmcextras.FOMC.Types.Defaults.DEFAULT_COLOR;

// todo: method to get rating by percent value

public enum PetRating implements EnumConstant {
    SICKLY("sickly", Text.literal("sɪᴄᴋʟʏ").withColor(0xFF74403B), 0xFF74403B),
    BAD("bad", Text.literal("ʙᴀᴅ").withColor(0xFFFF5555), 0xFFFF5555),
    BELOW_AVERAGE("below_average", Text.literal("ʙᴇʟᴏᴡ ᴀᴠᴇʀᴀɢᴇ").withColor(0xFFFCFC54), 0xFFFCFC54),
    AVERAGE("average", Text.literal("ᴀᴠᴇʀᴀɢᴇ").withColor(0xFFFCA800), 0xFFFCA800),
    GOOD("good", Text.literal("ɢᴏᴏᴅ").withColor(0xFF54FC54), 0xFF54FC54),
    GREAT("great", Text.literal("ɢʀᴇᴀᴛ").withColor(0xFF00A800), 0xFF00A800),
    EXCELLENT("excellent", Text.literal("ᴇxᴄᴇʟʟᴇɴᴛ").withColor(0xFF54FCFC), 0xFF54FCFC),
    AMAZING("amazing", Text.literal("ᴀᴍᴀᴢɪɴɢ").withColor(0xFFFC54FC), 0xFFFC54FC),
    PERFECT("perfect", Text.literal("ᴘᴇʀꜰᴇᴄᴛ").withColor(0xFFA800A8), 0xFFA800A8),
    UNKNOWN(ID_UNKNOWN, Text.empty(), DEFAULT_COLOR);

    public static class PetRatingLookup extends EnumLookup<PetRating> {
        public PetRatingLookup() {
            super(values());
        }

        public PetRating valueOfPercent(float value) {
            BigDecimal percent = BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(100));

            if (percent.compareTo(BigDecimal.valueOf(20)) <= 0)
                return PetRating.SICKLY;
            if (percent.compareTo(BigDecimal.valueOf(30)) < 0)
                return PetRating.BAD;
            if (percent.compareTo(BigDecimal.valueOf(40)) < 0)
                return PetRating.BELOW_AVERAGE;
            if (percent.compareTo(BigDecimal.valueOf(50)) < 0)
                return PetRating.AVERAGE;
            if (percent.compareTo(BigDecimal.valueOf(60)) < 0)
                return PetRating.GOOD;
            if (percent.compareTo(BigDecimal.valueOf(80)) < 0)
                return PetRating.GREAT;
            if (percent.compareTo(BigDecimal.valueOf(90)) < 0)
                return PetRating.EXCELLENT;
            if (percent.compareTo(BigDecimal.valueOf(100)) < 0)
                return PetRating.AMAZING;
            return PetRating.PERFECT;
        }
    }

    public static final PetRatingLookup LOOKUP = new PetRatingLookup();

    public final String ID;
    public final Text TAG;
    public final int COLOR;

    PetRating(String id, Text tag, int color) {
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
