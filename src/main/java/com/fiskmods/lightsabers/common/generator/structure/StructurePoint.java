package com.fiskmods.lightsabers.common.generator.structure;

import net.minecraft.util.math.BlockPos;

public class StructurePoint extends BlockPos
{
    public StructurePoint(int x, int y, int z)
    {
        super(x, y, z);
    }

    public StructurePoint(BlockPos p)
    {
        super(p);
    }
    
    public StructurePoint(StructurePoint p)
    {
        super(p);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof StructurePoint))
        {
            return false;
        }
        else
        {
            StructurePoint chunkcoordinates = (StructurePoint) obj;
            return this.getX() == chunkcoordinates.getX() && this.getZ() == chunkcoordinates.getZ();
        }
    }
}
