package fiskfille.utils.common.interaction;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import fiskfille.utils.common.network.FiskNetworkManager;
import fiskfille.utils.common.network.MessageInteraction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public enum InteractionHandler
{
    INSTANCE;
    
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
 //       Action action = event..action; //TODO
        int x = event.getPos().getX();
        int y = event.getPos().getY();
        int z = event.getPos().getZ();

//        if (action == Action.RIGHT_CLICK_AIR) //TODO
//        {
//            x = MathHelper.floor(player.posX);
//            y = MathHelper.floor(player.getEntityBoundingBox().minY);
//            z = MathHelper.floor(player.posZ);
//        }
//
//        if (interact(player, InteractionType.get(action), x, y, z))
//        {
//            event.setCanceled(true);
//            return;
//        }
    }
    
    public static boolean interact(EntityPlayer player, InteractionType type, int x, int y, int z)
    {
        if (player.getEntityWorld().isRemote)
        {
            for ( Object interaction_o : Interaction.REGISTRY)
            {
            	Interaction interaction = (Interaction)interaction_o;
                if (interaction.listen(player, player, type, Side.CLIENT, x, y, z))
                {
                    if (interaction.syncWithServer())
                    {
                        FiskNetworkManager.wrapper.sendToServer(new MessageInteraction(player, interaction, type, x, y, z));
                    }

                    return true;
                }
            }
        }
        
        return false;
    }
    
    public static void register()
    {
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }
    
    public static enum InteractionType
    {
        KEY_PRESS,
        KEY_HOLD,
        RIGHT_CLICK_AIR,
        RIGHT_CLICK_BLOCK,
        LEFT_CLICK_BLOCK;
        
//        public static InteractionType get(Action action) //TODO
//        {
//            switch (action)
//            {
//            case RIGHT_CLICK_AIR:
//                return RIGHT_CLICK_AIR;
//            case RIGHT_CLICK_BLOCK:
//                return RIGHT_CLICK_BLOCK;
//            case LEFT_CLICK_BLOCK:
//                return LEFT_CLICK_BLOCK;
//            }
//            
//            return KEY_PRESS;
//        }
    }
}
