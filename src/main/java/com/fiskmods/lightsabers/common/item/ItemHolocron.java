package com.fiskmods.lightsabers.common.item;

import com.fiskmods.lightsabers.Lightsabers;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ItemHolocron extends ItemBlockWithMetadata
{
    //private IIcon[] icons;

    public ItemHolocron(Block block)
    {
        super(block);
        setCreativeTab(Lightsabers.CREATIVE_TAB);

        ModItems.ITEMS.add(this);
    }

//    @Override //TODO
//    public String getUnlocalizedName(ItemStack itemstack)
//    {
//        return "tile." + (itemstack.getItemDamage() == 0 ? "jedi" : "sith") + "_holocron";
//    }

//    @Override //TODO
//    public IIcon getIconFromDamage(int damage)
//    {
//        return icons[MathHelper.clamp(damage, 0, 1)];
//    }
//
//    @Override
//    @SideOnly(Side.CLIENT)
//    public int getSpriteNumber()
//    {
//        return 1;
//    }
//
//    @Override
//    public void registerIcons(IIconRegister par1IIconRegister)
//    {
//        icons = new IIcon[2];
//        icons[0] = par1IIconRegister.registerIcon(Lightsabers.MODID + ":jedi_holocron");
//        icons[1] = par1IIconRegister.registerIcon(Lightsabers.MODID + ":sith_holocron");
//    }
}
