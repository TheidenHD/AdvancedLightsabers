package fiskfille.utils;

import com.fiskmods.lightsabers.common.data.ALData.DataFactory;
import com.google.common.base.MoreObjects;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos ;

public class DimensionalCoords extends BlockPos 
{
	private final int dimension;

    public DimensionalCoords(int x, int y, int z, int dim)
    {
        super(x, y, z);
        dimension = dim;
    }

    public DimensionalCoords(BlockPos coords, int dim)
    {
        super(coords);
        dimension = dim;
    }

    public DimensionalCoords(TileEntity tile)
    {
        super(tile.getPos());
        dimension = tile.getWorld().provider.getDimension();
    }

    public int getDimension()
    {
        return this.dimension;
    }
    
    public BlockPos getBlockPos()
    {
        return (BlockPos) this;
    }
    
    public static DimensionalCoords copy(DimensionalCoords coords)
    {
        if (coords != null)
        {
            return new DimensionalCoords(coords.getX(),coords.getY(),coords.getZ(),coords.getDimension());
        }

        return null;
    }

    public int[] toArray()
    {
        return new int[] {this.getX(), this.getY(), this.getZ(), dimension};
    }

    public static DimensionalCoords fromArray(int[] aint)
    {
        int[] aint1 = new int[4];

        for (int i = 0; i < Math.min(aint.length, aint1.length); ++i)
        {
            aint1[i] = aint[i];
        }

        return new DimensionalCoords(aint1[0], aint1[1], aint1[2], aint1[3]);
    }

//    public static DataFactory<DimensionalCoords> factory() //TODO loeschen?
//    {
//        return new DataFactory<DimensionalCoords>()
//        {
//            @Override
//            public DimensionalCoords construct()
//            {
//                return new DimensionalCoords();
//            }
//        };
//    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof DimensionalCoords))
        {
            return false;
        }
        else
        {
            DimensionalCoords coords = (DimensionalCoords) obj;
            return this.getX() == coords.getX() && this.getY() == coords.getY() && this.getZ() == coords.getZ() && dimension == coords.dimension;
        }
    }

    @Override
    public int hashCode()
    {
        return this.getX() + this.getZ() << 8 + this.getY() << 16 + dimension << 32;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).add("dimension", dimension).toString();
    }

    public int compareTo(DimensionalCoords coords)
    {
        return dimension == coords.dimension ? this.getY() == coords.getY() ? this.getZ() == coords.getZ() ? this.getX() - coords.getX() : this.getZ() - coords.getZ() : this.getY() - coords.getY() : dimension - coords.dimension;
    }
}
