package fiskfille.utils.registry;

import java.util.Locale;

import com.google.common.reflect.TypeToken;

import net.minecraftforge.fml.common.FMLContainer;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.InjectedModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraft.util.ResourceLocation;

public class FiskRegistryEntry<T>
{
    public final FiskDelegate<T> delegate = new FiskDelegate(this, getClass());

    private TypeToken<T> token = new TypeToken<T>(getClass())
    {
    };

    private ResourceLocation registryName = null;

    public final T setRegistryName(ResourceLocation name)
    {
        if (getRegistryName() != null)
        {
            throw new IllegalStateException("Attempted to set registry name with existing registry name! New: " + name + " Old: " + getRegistryName());
        }

        delegate.setName(name);

        int index = name.toString().lastIndexOf(':');
        String oldPrefix = index == -1 ? "" : name.toString().substring(0, index);
        String name2 = index == -1 ? name.toString() : name.toString().substring(index + 1);

        ModContainer mc = Loader.instance().activeModContainer();
        String prefix = mc == null || (mc instanceof InjectedModContainer && ((InjectedModContainer) mc).wrappedContainer instanceof FMLContainer) ? "minecraft" : mc.getModId().toLowerCase(Locale.ROOT);

        if (!oldPrefix.equals(prefix) && oldPrefix.length() > 0)
        {
            FMLLog.bigWarning("Dangerous alternative prefix '%s' for name '%s', expected '%s' invalid registry invocation/invalid name?", oldPrefix, name, prefix);
            prefix = oldPrefix;
        }

        registryName = new ResourceLocation(prefix, name2);

        return (T) this;
    }

    public final T setRegistryName(String domain, String name)
    {
        return setRegistryName(new ResourceLocation(domain + ":" + name));
    }

    public final ResourceLocation getRegistryName()
    {
        return registryName;
    }

    public final String getDomain()
    {
        return registryName.getPath(); //TODO check
    }

    public final Class<T> getRegistryType()
    {
        return (Class<T>) token.getRawType();
    }

    @Override
    public String toString()
    {
        return delegate.name().toString();
    }
}
