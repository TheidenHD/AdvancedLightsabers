package com.fiskmods.lightsabers.common.force;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.util.text.TextFormatting;

public enum ForceSide
{
    NONE(TextFormatting.WHITE),
    LIGHT(TextFormatting.AQUA),
    DARK(TextFormatting.RED),
    NEUTRAL(TextFormatting.YELLOW);

    public final TextFormatting theme;
    public final Set<Power> powers = Sets.newHashSet();

    private ForceSide(TextFormatting formatting)
    {
        theme = formatting;
    }
    
    public Power getPower()
    {
        switch (this)
        {
        case LIGHT:
            return Power.LIGHT_SIDE;
        case DARK:
            return Power.DARK_SIDE;
        case NEUTRAL:
            return Power.NEUTRAL;
        default:
            return null;
        }
    }

    public ForceSide getOpposite()
    {
        switch (this)
        {
        case LIGHT:
            return DARK;
        case DARK:
            return LIGHT;
        default:
            return this;
        }
    }

    public boolean isPolar()
    {
        return this == LIGHT || this == DARK;
    }
}
