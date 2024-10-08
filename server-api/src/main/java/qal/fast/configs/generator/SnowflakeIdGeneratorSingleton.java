package qal.fast.configs.generator;


import qal.fast.utils.common.IpUtils;

public class SnowflakeIdGeneratorSingleton {

    private static volatile SnowFlake idWorker;

    private SnowflakeIdGeneratorSingleton() {
    }

    public static SnowFlake getInstance() {
        if (idWorker == null) {
            synchronized (SnowflakeIdGeneratorSingleton.class) {
                if (idWorker == null) {
                    idWorker = new SnowFlake(IpUtils.getSnowFlakeMachineId());
                }
            }
        }
        return idWorker;
    }
}

