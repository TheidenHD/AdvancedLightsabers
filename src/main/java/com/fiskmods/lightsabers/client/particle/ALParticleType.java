package com.fiskmods.lightsabers.client.particle;

import net.minecraft.client.particle.Particle;

public enum ALParticleType
{
    FORCE_HEAL(EntityALHealFX.class);

    public Class<? extends Particle> particleClass;

    private ALParticleType(Class<? extends Particle> clazz)
    {
        particleClass = clazz;
    }
}
