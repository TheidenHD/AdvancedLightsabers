package fiskfille.utils.registry;

import com.google.common.base.Objects;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IRegistryDelegate;

public class FiskDelegate<T> implements IRegistryDelegate<T>
{
    private T referant;
    private ResourceLocation name;
    private final Class<T> type;

    public FiskDelegate(T referant, Class<T> type)
    {
        this.referant = referant;
        this.type = type;
    }

    @Override
    public T get()
    {
        return referant;
    }

    @Override
    public ResourceLocation name()
    {
        return name;
    }

    @Override
    public Class<T> type()
    {
        return type;
    }

    void changeReference(T newTarget)
    {
        this.referant = newTarget;
    }

    void setName(ResourceLocation name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof IRegistryDelegate)
        {
        	IRegistryDelegate<?> other = (IRegistryDelegate<?>) obj;

            return Objects.equal(other.name(), name);
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }
}
