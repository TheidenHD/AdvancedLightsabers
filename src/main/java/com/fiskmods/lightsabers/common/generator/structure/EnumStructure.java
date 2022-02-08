package com.fiskmods.lightsabers.common.generator.structure;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.init.Biomes;

public enum EnumStructure
{
    SITH_TOMB(new Constructor()
    {
        @Override
        public Structure construct(World world, int x, int y, int z, Random rand)
        {
            return null; // new StructureSithTomb(world, x, y, z); //TODO
        }
    }, 8, 32, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.MESA, Biomes.MESA_ROCK, Biomes.MESA_CLEAR_ROCK),
    JEDI_TEMPLE(new Constructor()
    {
        @Override
        public Structure construct(World world, int x, int y, int z, Random rand)
        {
            return null; //new StructureJediTemple(0, world, x, y, z); //TODO
        }
    }, 24, 64, new TemplePredicate());
    
    public final Predicate<Biome> biomePredicate;
    private final Constructor constructor;
    public int minDistance;
    public int maxDistance;

    private EnumStructure(Constructor c, int min, int max, Predicate<Biome> predicate)
    {
        biomePredicate = predicate;
        minDistance = min;
        maxDistance = max;
        constructor = c;
    }
    
    private EnumStructure(Constructor c, int min, int max, final Biome... biomes)
    {
        this(c, min, max, new Predicate<Biome>()
        {
            List<Biome> biomeList = Lists.newArrayList(biomes);

            @Override
            public boolean apply(Biome biome)
            {
                return biomeList.contains(biome);
            }
        });
    }
    
    public Structure construct(World world, int x, int y, int z, Random rand)
    {
        return constructor.construct(world, x, y, z, rand);
    }
    
    private interface Constructor
    {
        Structure construct(World world, int x, int y, int z, Random rand);
    }
    
    private static class TemplePredicate implements Predicate<Biome>
    {
        @Override
        public boolean apply(Biome biome)
        {
            return biome.getBaseHeight() > 0 && biome.getDefaultTemperature() < 1.5F;
        }
    }
}
