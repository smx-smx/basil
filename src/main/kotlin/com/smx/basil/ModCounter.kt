package com.smx.basil

class ModCounter(value: Int, private val modMask: Int) {
    var value: Int = value
        set(value){
            field = value.and(modMask)
        }

    operator fun minus(count: Int): ModCounter {
        return ModCounter(value.minus(count).and(modMask), modMask)
    }

    operator fun plus(count: Int): ModCounter {
        return ModCounter(value.plus(count).and(modMask), modMask)
    }

    operator fun inc(): ModCounter {
        return plus(1)
    }
}

class UModCounter(value: UInt, private val modMask: UInt) {
    var value: UInt = value
        set(value){
            field = value.and(modMask)
        }

    operator fun minus(count: UInt): UModCounter {
        return UModCounter(value.minus(count).and(modMask), modMask)
    }

    operator fun plus(count: UInt): UModCounter {
        return UModCounter(value.plus(count).and(modMask), modMask)
    }

    operator fun inc(): UModCounter {
        return plus(1u)
    }
}