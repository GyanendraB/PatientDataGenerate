package com.testdata.util;

import java.util.Random;

public class SSNGenerator {

    public static String generate() {

        Random random = new Random();

        int part1 = 900 + random.nextInt(99);
        int part2 = 10 + random.nextInt(89);
        int part3 = 1000 + random.nextInt(8999);

        return part1 + "-" + part2 + "-" + part3;
    }
}
