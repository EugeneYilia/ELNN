package com.jstarcraft.module.math.structure.message;

import com.jstarcraft.module.math.structure.MathMessage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SumMessage implements AccumulationMessage<Float>{

    float sum = 0;
    boolean willReturnAbs;

    boolean calcParallel;
    Lock lock = new ReentrantLock(false);

    public SumMessage(boolean calcParallel){
        this.calcParallel = calcParallel;
    }

    @Override
    public void attach(MathMessage message) {
        SumMessage that = SumMessage.class.cast(message);
        this.sum += that.sum;
    }

    @Override
    public MathMessage detach() {
        MathMessage mathMessage = new SumMessage(false);
        return mathMessage;
    }

    @Override
    public void accumulateValue(float value) {
        if(calcParallel){
            try {
                lock.lock();
                sum += value;
            } finally {
                lock.unlock();
            }
        } else {
            sum += value;
        }
    }

    @Override
    public Float getValue() {
        if(calcParallel){
            try{
                lock.lock();
                return sum;
            } finally {
                lock.unlock();
            }
        } else {
            return sum;
        }
    }
}
