package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.*;
import com.apd.tema2.intersections.*;
import com.apd.tema2.utils.Constants;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Clasa Factory ce returneaza implementari ale InterfaceHandler sub forma unor
 * clase anonime.
 */
public class IntersectionHandlerFactory {

    public static IntersectionHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of
        // them)
        // road in maintenance - 2 ways 1 lane each, X cars at a time
        // road in maintenance - 1 way, M out of N lanes are blocked, X cars at a time
        // railroad blockage for s seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    try {
                        System.out.println("Car " + car.getId() + " has reached the semaphore, now waiting...");
                        sleep(car.getWaitingTime()); // Oprire thread-uri un numar de sec.

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() + " has waited enough, now driving...");
                }
            };
            case "simple_n_roundabout" -> new IntersectionHandler() {

                @Override
                public void handle(Car car) {
                    System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");
                    try {
                        ((SimpleIntersection)Main.intersection).sem.acquire(); // intrate in sens giratoriu
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car "+ car.getId() + " has entered the roundabout");
                    synchronized (this) {
                        try {
                            sleep(((SimpleIntersection)Main.intersection).timeInIntersection); // timp aflare in sens giratoriu
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Car " + car.getId() + " has exited the roundabout after "
                                + ((SimpleIntersection)Main.intersection).timeInIntersection + " seconds");
                        ((SimpleIntersection) Main.intersection).sem.release(); // iesire din sens giratoriu
                    }
                }
            };
            case "simple_strict_1_car_roundabout" -> new IntersectionHandler() {

                @Override
                public void handle(Car car) {
                    System.out.println("Car " + car.getId() + " has reached the roundabout");
                    try {
                        ((SimpleIntersection)Main.intersection).
                                vecSem[car.getStartDirection()].acquire(); // intrare masina in sens giratoriu pe directia
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car "+ car.getId() + " has entered the roundabout from lane "
                            + car.getStartDirection());
                    try {
                        sleep(((SimpleIntersection)Main.intersection).timeInIntersection); // timp aflare in sens giratoriu
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Car " + car.getId() + " has exited the roundabout after "
                            + ((SimpleIntersection)Main.intersection).timeInIntersection + " seconds");
                    ((SimpleIntersection) Main.intersection).
                            vecSem[car.getStartDirection()].release(); // iesire masina in sens giratoriu pe directia

                }
            };

            case "simple_strict_x_car_roundabout" -> new IntersectionHandler() {

                @Override
                public void handle(Car car) {
                    System.out.println("Car " + car.getId() + " has reached the roundabout, now waiting...");

                    try {
                        ((SimpleIntersection) Main.intersection).
                                barrierAll.await(); // asteapta toate masinile sa ajunga (reached)

                        ((SimpleIntersection) Main.intersection).
                                vecSem[car.getStartDirection()].acquire(); // intrare masini in sens giratoriu

                        ((SimpleIntersection) Main.intersection).
                                barrier.await(); // asteapta ca toate masinile sa ajunga in sens giratoriu
                        System.out.println("Car " + car.getId() + " was selected to enter the roundabout from lane "
                                + car.getStartDirection());
                        ((SimpleIntersection) Main.intersection).barrier.await();
                        System.out.println("Car " + car.getId() + " has entered the roundabout from lane "
                                + car.getStartDirection());
                        sleep(((SimpleIntersection) Main.intersection).timeInIntersection);
                        System.out.println("Car " + car.getId() + " has exited the roundabout after "
                                + ((SimpleIntersection) Main.intersection).timeInIntersection + " seconds");
                        ((SimpleIntersection) Main.intersection).barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }



                    ((SimpleIntersection) Main.intersection).vecSem[car.getStartDirection()].release();
                }

            };
            case "simple_max_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance

                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici
                    System.out.println("Car " + car.getId() + " has reached the roundabout from lane "
                            + car.getStartDirection());
                    try {
                        ((SimpleIntersection) Main.intersection).vecSem[car.getStartDirection()].acquire();
                        System.out.println("Car " + car.getId() + " has entered the roundabout from lane "
                                + car.getStartDirection());
                        sleep(((SimpleIntersection) Main.intersection).timeInIntersection);
                        System.out.println("Car " + car.getId() + " has exited the roundabout after "
                                + ((SimpleIntersection) Main.intersection).timeInIntersection + " seconds");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ((SimpleIntersection) Main.intersection).vecSem[car.getStartDirection()].release();
                }
            };

            case "priority_intersection" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance

                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici
                }
            };

            case "crosswalk" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            case "simple_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            case "complex_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            case "railroad" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            default -> null;
        };
    }
}
