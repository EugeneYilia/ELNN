package com.jstarcraft.module.math.structure.message;

import com.jstarcraft.module.math.structure.MathMessage;

public class VarianceMessage implements AccumulationMessage<Float>{

    float value;

    public VarianceMessage(float value){
        this.value = value;
    }

    @Override
    public synchronized void attach(MathMessage message) {
        VarianceMessage that = VarianceMessage.class.cast(message);
        this.value += that.value;
    }

    @Override
    public synchronized MathMessage detach() {
        VarianceMessage varianceMessage = new VarianceMessage(0);
        return varianceMessage;
    }

    @Override
    public void accumulateValue(float value) {
        //doesn't need this method
    }

    @Override
    public Float getValue() {
        return value;
    }
}
