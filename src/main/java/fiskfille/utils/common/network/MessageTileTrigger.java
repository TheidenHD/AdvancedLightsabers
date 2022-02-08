package fiskfille.utils.common.network;

import com.fiskmods.lightsabers.common.network.ALNetworkManager;

import fiskfille.utils.DimensionalCoords;
import fiskfille.utils.helper.NBTHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class MessageTileTrigger extends AbstractMessage<MessageTileTrigger>
{
    private DimensionalCoords coords;
    private int id;
    private int action;
    private int playerDim;

    public MessageTileTrigger()
    {
    }

    public MessageTileTrigger(DimensionalCoords coords, EntityPlayer player, int action)
    {
        this.coords = coords;
        this.action = action;

        if (player != null)
        {
            id = player.getEntityId();
            playerDim = player.getEntityWorld().provider.getDimension();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        id = buf.readInt();
        action = buf.readInt();
        playerDim = buf.readInt();
        coords = NBTHelper.fromBytes(buf, DimensionalCoords.class);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(id);
        buf.writeInt(action);
        buf.writeInt(playerDim);
        NBTHelper.toBytes(buf, coords);
    }

    @Override
    public void receive() throws MessageException
    {
        EntityPlayer player = getSender(getWorld(playerDim), id);
        TileEntity tile = getWorld(coords.getDimension()).getTileEntity(coords.getBlockPos());

        if (tile instanceof ITileDataCallback)
        {
            ((ITileDataCallback) tile).receive(player, action);

            if (context.side.isServer())
            {
                ALNetworkManager.wrapper.sendToDimension(new MessageTileTrigger(coords, player, action), coords.getDimension());
            }
        }
    }

    public static interface ITileDataCallback
    {
        /**
         * Called when a tile gets triggered
         *
         * @param player The player who triggered it, or null if none exists
         * @param action The trigger type
         */
        void receive(EntityPlayer player, int action);
    }
}
