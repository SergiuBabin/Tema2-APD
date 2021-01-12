package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class SimpleIntersection implements Intersection {
    // Define your variables here.
    public static Semaphore sem;
    public static Semaphore[] vecSem;
    public static CyclicBarrier barrier;
    public static CyclicBarrier barrierAll;
    public static int timeInIntersection;
}
