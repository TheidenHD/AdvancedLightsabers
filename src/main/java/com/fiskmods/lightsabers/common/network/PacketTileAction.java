package com.fiskmods.lightsabers.common.network;

import java.util.Random;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.common.block.ModBlocks;
import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class PacketTileAction implements IMessage
{
    private int id;
    private int x;
    private int y;
    private int z;
    private int action;

    public PacketTileAction()
    {

    }

    public PacketTileAction(Entity entity, int x, int y, int z, int action)
    {
        id = entity != null ? entity.getEntityId() : 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.action = action;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        action = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(action);
    }

    public static class Handler implements IMessageHandler<PacketTileAction, IMessage>
    {
        @Override
        public IMessage onMessage(PacketTileAction message, MessageContext ctx)
        {
            EntityPlayer clientPlayer = ctx.side.isClient() ? Lightsabers.proxy.getPlayer() : ctx.getServerHandler().player;
            EntityPlayer player = null;
            World world = clientPlayer.getEntityWorld();
            int action = message.action;
            int x = message.x;
            int y = message.y;
            int z = message.z;

            if (world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntitySithCoffin)
            {
                TileEntitySithCoffin tile = (TileEntitySithCoffin) world.getTileEntity(new BlockPos(x, y, z));

                if (tile != null)
                {
                    int metadata = tile.getBlockMetadata();

                    if (world.getEntityByID(message.id) instanceof EntityPlayer)
                    {
                        player = (EntityPlayer) world.getEntityByID(message.id);

                        if (action == 0)
                        {
                            if (tile.lidOpenTimer == 0)
                            {
                                tile.isLidOpen = true;
                                player.playSound(new SoundEvent(ALSounds.BLOCK_SITH_SARCOPHAGUS_OPEN.getSoundName()), 1.0F, 1.0F);//TODO fix?
                            }
                            else if (tile.lidOpenTimer == TileEntitySithCoffin.LID_OPEN_MAX)
                            {
                                tile.isLidOpen = false;
                                player.playSound(new SoundEvent(ALSounds.BLOCK_SITH_SARCOPHAGUS_CLOSE.getSoundName()), 1.0F, 1.0F);//TODO fix?
                            }
                        }
                    }

                    if (ctx.side.isServer())
                    {
                        ALNetworkManager.wrapper.sendToAll(new PacketTileAction(player, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), action));
                    }
                }
            }
            else if (world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntitySithStoneCoffin)
            {
                TileEntitySithStoneCoffin tile = (TileEntitySithStoneCoffin) world.getTileEntity(new BlockPos(x, y, z));

                if (tile != null)
                {
                    int metadata = tile.getBlockMetadata();

                    if (action == 0 || action == 1)
                    {
                        tile.baseplateOnly = action > 0;

                        if (tile.baseplateOnly)
                        {
                            world.setBlockToAir(new BlockPos(x, y + 1, z));
                        }
                        else
                        {
                            //world.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.sithStoneCoffin, tile.getBlockMetadata() + 4, 2);//TODO
                        }

                        Entity entity = world.getEntityByID(message.id);

                        if (entity != null)
                        {
                            Random rand = new Random();

                            for (int i = 0; i < 128; ++i)
                            {
                                double d0 = (double) (rand.nextFloat() * 2 - 1) * 1.2F;
                                double d1 = rand.nextFloat() * 2.4F - 1;
                                double d2 = (double) (rand.nextFloat() * 2 - 1) * 1.2F;
                                double d3 = entity.posX + d0 * entity.width;
                                double d4 = entity.getCollisionBoundingBox().minY + d1 * entity.height;
                                double d5 = entity.posZ + d2 * entity.width;
                                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d4, d5, 0, 0, 0);
                            }
                        }
                    }

                    if (ctx.side.isServer())
                    {
                        ALNetworkManager.wrapper.sendToAll(new PacketTileAction(player, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), action));
                    }
                }
            }
            else if (world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityHolocron)
            {
                TileEntityHolocron tile = (TileEntityHolocron) world.getTileEntity(new BlockPos(x, y, z));

                if (tile != null)
                {
                    int metadata = tile.getBlockMetadata();

                    if (world.getEntityByID(message.id) instanceof EntityPlayer)
                    {
                        player = (EntityPlayer) world.getEntityByID(message.id);

                        if (action == 0)
                        {
                            ++tile.playersUsing;
                        }
                        else if (action == 1)
                        {
                            --tile.playersUsing;
                        }
                    }

                    if (ctx.side.isServer())
                    {
                        ALNetworkManager.wrapper.sendToAll(new PacketTileAction(player, tile.getPos().getX(), tile.getPos().getY(), tile.getPos().getZ(), action));
                    }
                }
            }

            return null;
        }
    }
}
