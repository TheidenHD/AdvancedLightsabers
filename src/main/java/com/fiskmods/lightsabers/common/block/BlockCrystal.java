package com.fiskmods.lightsabers.common.block;

import java.util.Random;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystal extends BlockDirectional implements ITileEntityProvider
{
    protected static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0.3125D, 0.875D, 0.375D, 0.6875D, 1.0D, 0.625D);
    protected static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.125D, 0.625D);
    protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.3125D, 0.375D, 0.875D, 0.6875D, 0.625D, 1.0D);
    protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.625D, 0.125D);
    protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.875D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
    protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.125D, 0.625D, 0.6875D);

    private Random rand = new Random();

    public BlockCrystal(String name)
    {
        super(Material.GLASS);
        setLightLevel(0.25F);
        setHardness(2.0F);
        setResistance(10.0F);
        setSoundType(SoundType.GLASS);
        setTranslationKey(name);
        setRegistryName(Lightsabers.MODID,name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        ModBlocks.BLOCKS.add(this);
        ModItems.crystal = new ItemCrystal(this);
        hasTileEntity = true;
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)//TODO seting in reg?
    {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED; //-1
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    private boolean canPlaceAt(World world, BlockPos pos)
    {
        if (world.isSideSolid(pos, EnumFacing.UP))
        {
            return true;
        }
        else
        {
            Block block = world.getBlockState(pos).getBlock();
            return block.canPlaceTorchOnTop(world.getBlockState(pos), world, pos);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return world.isSideSolid(pos.west(), EnumFacing.EAST, true) || world.isSideSolid(pos.east(), EnumFacing.WEST, true) || world.isSideSolid(pos.north(), EnumFacing.SOUTH, true) || world.isSideSolid(pos.south(), EnumFacing.NORTH, true) || canPlaceAt(world, pos.down()) || canPlaceAt(world, pos.up());
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos1, Block block, BlockPos pos2) {
        if (this.checkForDrop(world, pos1, state) && !canPlaceBlock(world, pos1, (EnumFacing)state.getValue(FACING))) {
            this.dropBlockAsItem(world, pos1, state, 0);
            world.setBlockToAir(pos1);
        }

    }

    private boolean checkForDrop(World world, BlockPos pos, IBlockState state) {
        if (this.canPlaceBlockAt(world, pos)) {
            return true;
        } else {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
            return false;
        }
    }


    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
    	//TODO l== state
        float f = 0.0625F;
        float width = f * 6;
        float height = f * 6;

//        if (l == 1) //TODO
//        {
//            setBlockBounds(0, 0.5F - width / 2, 0.5F - width / 2, height, 0.5F + width / 2, 0.5F + width / 2);
//        }
//        else if (l == 2)
//        {
//            setBlockBounds(1 - height, 0.5F - width / 2, 0.5F - width / 2, 1, 0.5F + width / 2, 0.5F + width / 2);
//        }
//        else if (l == 3)
//        {
//            setBlockBounds(0.5F - width / 2, 0.5F - width / 2, 0, 0.5F + width / 2, 0.5F + width / 2, height);
//        }
//        else if (l == 4)
//        {
//            setBlockBounds(0.5F - width / 2, 0.5F - width / 2, 1 - height, 0.5F + width / 2, 0.5F + width / 2, 1);
//        }
//        else if (l == 5)
//        {
//            setBlockBounds(0.5F - width / 2, 0, 0.5F - width / 2, 0.5F + width / 2, height, 0.5F + width / 2);
//        }
//        else
//        {
//            setBlockBounds(0.5F - width / 2, 1 - height, 0.5F - width / 2, 0.5F + width / 2, 1, 0.5F + width / 2);
//        }

        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        switch(enumfacing) {
            case EAST:
                return AABB_EAST;
            case WEST:
                return AABB_WEST;
            case SOUTH:
                return AABB_SOUTH;
            case NORTH:
            default:
                return AABB_NORTH;
            case UP:
                return AABB_UP;
            case DOWN:
                return AABB_DOWN;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack itemstack)
    {
        super.onBlockPlacedBy(world, pos, state, entity, itemstack);
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityCrystal)
        {
            ((TileEntityCrystal) tile).setColor(ItemCrystal.get(itemstack));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityCrystal)
        {
            return ItemCrystal.create(((TileEntityCrystal) tile).getColor());
        }

        return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(pos);

        if (tile instanceof TileEntityCrystal)
        {
            dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);

            return true;
        }

        return false;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        return 0;
    }

    @Override
    public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        super.getDrops(drops, world, pos, state, fortune);
        TileEntity tile = world.getTileEntity(pos);
        drops.add(ItemCrystal.create(((TileEntityCrystal) tile).getColor()));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityCrystal();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    public int getMetaFromState(IBlockState p_getMetaFromState_1_) {
        int i;
        switch((EnumFacing)p_getMetaFromState_1_.getValue(FACING)) {
            case EAST:
                i = 1;
                break;
            case WEST:
                i = 2;
                break;
            case SOUTH:
                i = 3;
                break;
            case NORTH:
                i = 4;
                break;
            case UP:
            default:
                i = 5;
                break;
            case DOWN:
                i = 0;
        }

        return i;
    }

    public IBlockState getStateFromMeta(int p_getStateFromMeta_1_) {
        IBlockState iblockstate = this.getDefaultState();
        switch(p_getStateFromMeta_1_) {
            case 0:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.DOWN);
                break;
            case 1:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
                break;
            case 5:
            default:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
        }

        return iblockstate;
    }
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float p_getStateForPlacement_4_, float p_getStateForPlacement_5_, float p_getStateForPlacement_6_, int p_getStateForPlacement_7_, EntityLivingBase entity) {
        return canPlaceBlock(world, pos, facing) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
    }

    protected static boolean canPlaceBlock(World p_canPlaceBlock_0_, BlockPos p_canPlaceBlock_1_, EnumFacing p_canPlaceBlock_2_) {
        BlockPos blockpos = p_canPlaceBlock_1_.offset(p_canPlaceBlock_2_.getOpposite());
        IBlockState iblockstate = p_canPlaceBlock_0_.getBlockState(blockpos);
        boolean flag = iblockstate.getBlockFaceShape(p_canPlaceBlock_0_, blockpos, p_canPlaceBlock_2_) == BlockFaceShape.SOLID;
        Block block = iblockstate.getBlock();
        if (p_canPlaceBlock_2_ == EnumFacing.UP) {
            return iblockstate.isTopSolid() || !isExceptionBlockForAttaching(block) && flag;
        } else {
            return !isExceptBlockForAttachWithPiston(block) && flag;
        }
    }

//    @Override
//    public int getRenderColor(IBlockState state) {
//        return new Color(0, 0, 255).getRGB();
//    }
//
//    @Override
//    public int getBlockColor() {
//        return new Color(0, 0, 255).getRGB();
//    }
//
//    @Override
//    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
//        return new Color(0, 0, 255).getRGB();
//    }

}
