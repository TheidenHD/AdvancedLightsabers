package fiskfille.utils.common.keybinds;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import fiskfille.utils.common.interaction.InteractionHandler;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public enum FiskKeyHandler
{
    INSTANCE;
    
    public static final List<FiskKeyBinding> KEYS = new ArrayList();
    private Minecraft mc = Minecraft.getMinecraft();

    private int pressed;
    private int[] timePressed;

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event)
    {
        EntityPlayer player = mc.player;

        if (mc.currentScreen == null)
        {
            int prevPressed = pressed;
            pressed = 0;
            
            for (int i = 0; i < KEYS.size(); ++i)
            {
                if (KEYS.get(i).isKeyDown())
                {
                    pressed |= 1 << i;
                }
            }
            
            if (pressed != prevPressed)
            {
                InteractionHandler.interact(mc.player, InteractionType.KEY_PRESS, MathHelper.floor(player.posX), MathHelper.floor(player.getEntityBoundingBox().minY), MathHelper.floor(player.posZ));
            }
        }
    }
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event)
    {
        EntityPlayer player = mc.player;

        if (mc.currentScreen == null && event.phase == Phase.END)
        {
            if (timePressed == null)
            {
                timePressed = new int[KEYS.size()];
            }
            
            for (int i = 0; i < KEYS.size(); ++i)
            {
                if (KEYS.get(i).isKeyDown())
                {
                    if (++timePressed[i] == 5)
                    {
                        InteractionHandler.interact(mc.player, InteractionType.KEY_HOLD, MathHelper.floor(player.posX), MathHelper.floor(player.getCollisionBoundingBox().minY), MathHelper.floor(player.posZ));
                    }
                }
                else
                {
                    timePressed[i] = 0;
                }
            }
        }
    }
    
    public static void register()
    {
        FMLCommonHandler.instance().bus().register(INSTANCE);
    }
}
