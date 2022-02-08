package com.fiskmods.lightsabers.common.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.fiskmods.lightsabers.common.generator.WorldGeneratorStructures;
import com.fiskmods.lightsabers.common.generator.structure.EnumStructure;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Lists;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class StructureLocator implements Runnable
{
    public static WeakHashMap<World, Map<EnumStructure, List<ChunkPos>>> cachedStructurePos = new WeakHashMap();
    public static WeakHashMap<World, List<ChunkPos>> chunksSearched = new WeakHashMap();

    private final CommandBase command;
    private final ICommandSender sender;
    private final EnumStructure structure;
    private final boolean targetAll;
    private final int maxRange;
    private final int startX;
    private final int startZ;

    public StructureLocator(CommandBase commandBase, ICommandSender commandSender, EnumStructure enumStructure, boolean all, int range, int x, int z)
    {
        command = commandBase;
        sender = commandSender;
        structure = enumStructure;
        targetAll = all;
        maxRange = range;
        startX = x;
        startZ = z;
    }

    @Override
    public void run()
    {
        World world = sender.getEntityWorld();

        List<ChunkPos> searched = getChunksSearched(world);
        boolean flag = structure == null || getStructures(world, structure).isEmpty();
        int range = 0;

        searched.clear();

        while (++range < maxRange && flag)
        {
            for (int i = -range; i <= range; ++i)
            {
                for (int j = -range; j <= range; ++j)
                {
                    int chunkX = (startX >> 4) + i;
                    int chunkZ = (startZ >> 4) + j;
                    ChunkPos chunk = new ChunkPos(chunkX, chunkZ);

                    if (searched.contains(chunk))
                    {
                        continue;
                    }

                    for (EnumStructure structure1 : EnumStructure.values())
                    {
                        if (WorldGeneratorStructures.canSpawnStructureAtCoords(world, chunk.getXStart() + 8, chunk.getZStart() + 8, structure1))
                        {
                            getStructures(world, structure1).add(chunk);

                            if (structure == structure1 || targetAll)
                            {
                                flag = false;
                            }
                        }
                    }

                    searched.add(chunk);
                }
            }
        }

        final Vec3d vec = new Vec3d(startX >> 4, 0, startZ >> 4);
        Comparator<ChunkPos> coordComparator = new Comparator<ChunkPos>()
        {
            @Override
            public int compare(ChunkPos arg0, ChunkPos arg1)
            {
                return Double.valueOf(new Vec3d(arg0.x, 0, arg0.z).distanceTo(vec)).compareTo(new Vec3d(arg1.x, 0, arg1.z).distanceTo(vec));
            }
        };

        for (EnumStructure structure1 : EnumStructure.values())
        {
            Collections.sort(getStructures(world, structure1), coordComparator);
        }

        if (targetAll)
        {
            List<ChunkPos> structures = Lists.newArrayList();

            for (EnumStructure structure1 : EnumStructure.values())
            {
                if (!getStructures(world, structure1).isEmpty())
                {
                    structures.add(getStructures(world, structure1).get(0));
                }
            }

            if (structures.isEmpty())
            {
            	TextComponentTranslation message = new TextComponentTranslation("commands.force.structure.locate.failure");
                message.getStyle().setColor(TextFormatting.RED);

                sender.sendMessage(message);
            }
            else
            {
                Collections.sort(structures, coordComparator);

                for (EnumStructure structure1 : EnumStructure.values())
                {
                    if (!getStructures(world, structure1).isEmpty() && structures.get(0).equals(getStructures(world, structure1).get(0)))
                    {
                    	ChunkPos chunk = structures.get(0);
                        CommandBase.notifyCommandListener(sender, command, "commands.force.structure.locate.success", ALHelper.getUnconventionalName(structure1.name()), chunk.getXStart() + 8, chunk.getZStart() + 8);
                        break;
                    }
                }
            }
        }
        else
        {
            List<ChunkPos> structures = getStructures(world, structure);

            if (structures.isEmpty())
            {
            	TextComponentTranslation message = new TextComponentTranslation("commands.force.structure.locate.failure");
                message.getStyle().setColor(TextFormatting.RED);

                sender.sendMessage(message);
            }
            else
            {
            	ChunkPos chunk = structures.get(0);
                CommandBase.notifyCommandListener(sender, command, "commands.force.structure.locate.success", ALHelper.getUnconventionalName(structure.name()), chunk.getXStart() + 8, chunk.getZStart() + 8);
            }
        }
    }

    public static List<ChunkPos> getChunksSearched(World world)
    {
        if (chunksSearched.get(world) == null)
        {
            chunksSearched.put(world, new ArrayList<ChunkPos>());
        }

        return chunksSearched.get(world);
    }

    public static Map<EnumStructure, List<ChunkPos>> getStructures(World world)
    {
        if (cachedStructurePos.get(world) == null)
        {
            cachedStructurePos.put(world, new HashMap<EnumStructure, List<ChunkPos>>());
        }

        return cachedStructurePos.get(world);
    }

    public static List<ChunkPos> getStructures(World world, EnumStructure structure)
    {
        Map<EnumStructure, List<ChunkPos>> map = getStructures(world);

        if (map.get(structure) == null)
        {
            map.put(structure, new ArrayList<ChunkPos>());
        }

        return map.get(structure);
    }
}
