package com.epam.uber.logic;

import com.epam.uber.exceptions.UberException;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class UberCabCompany {
    private static final Logger LOGGER = Logger.getLogger(UberCabCompany.class);

    private static final int TAXI_AMOUNT = 3;
    private static final int MIN_TAXI_LOCATION = -250;
    private static final int MAX_TAXI_LOCATION = 250;
    private static final int MIN_TAXI_RADIUS = 1000;
    private static final int MAX_TAXI_RADIUS = 3000;

    private static final ReentrantLock INSTANCE_LOCK = new ReentrantLock();
    private static final ReentrantLock TAXI_LOCK = new ReentrantLock();
    private static final AtomicBoolean IS_INSTANCE_CREATED = new AtomicBoolean(false);
    private final Semaphore semaphore = new Semaphore(TAXI_AMOUNT, true);
    private static UberCabCompany cabCompanyInstance;

    private final List<UberTaxi> allTaxi;

    private UberCabCompany() {
        LOGGER.info("Starting creating taxi...");
        allTaxi = new ArrayList<>();
        for (int i = 0; i < TAXI_AMOUNT; i++) {
            int taxiLocationX = (int) ((Math.random() * MAX_TAXI_LOCATION - MIN_TAXI_LOCATION + 1))
                    + MIN_TAXI_LOCATION;
            int taxiLocationY = (int) ((Math.random() * MAX_TAXI_LOCATION - MIN_TAXI_LOCATION + 1))
                    + MIN_TAXI_LOCATION;
            double taxiRadius = (Math.random() * MAX_TAXI_RADIUS - MIN_TAXI_RADIUS) + MIN_TAXI_RADIUS;
            UberTaxi taxi = new UberTaxi(taxiLocationX, taxiLocationY, i + 1, taxiRadius);
            LOGGER.info("Created taxi: " + taxi);
            allTaxi.add(taxi);
        }
        LOGGER.info("Taxi was created!");
    }

    public static UberCabCompany getInstance() {
        if (!IS_INSTANCE_CREATED.get()) {
            INSTANCE_LOCK.lock();
            try {
                if (!IS_INSTANCE_CREATED.get()) {
                    cabCompanyInstance = new UberCabCompany();
                    LOGGER.info("Uber cab company is created!");
                    IS_INSTANCE_CREATED.set(true);
                }
            } finally {
                INSTANCE_LOCK.unlock();
            }
        }
        return cabCompanyInstance;
    }

    public UberTaxi acquireTaxi(Human human) throws UberException {
        try {
            semaphore.acquire();
            TAXI_LOCK.lock();
            UberTaxi taxi = null;
            LOGGER.info(String.format("Cab company: %s is phoned the Uber!", human.getName()));
            int indexTaxi = findNearestTaxi(human);
            if (indexTaxi == -1) {
                LOGGER.info(String.format("Cab company: %s, sorry. There are no taxi for you :(", human.getName()));
            } else {
                taxi = allTaxi.remove(indexTaxi);
                LOGGER.info(String.format("Cab company: %s, your taxi is %d! Taxi will arrive any minute!",
                        human.getName(), taxi.getTaxiNumber()));
            }
            return taxi;
        } catch (InterruptedException e) {
            throw new UberException(e.getMessage(), e);
        } finally {
            TAXI_LOCK.unlock();
        }
    }

    public void releaseTaxi(UberTaxi taxi) {
        TAXI_LOCK.lock();
        try {
            allTaxi.add(taxi);
            semaphore.release();
            LOGGER.info(String.format("Cab company: Taxi #%d is free! His location is [%d, %d]",
                    taxi.getTaxiNumber(), taxi.getLocationX(), taxi.getLocationY()));
        } finally {
            TAXI_LOCK.unlock();
        }
    }

    private int findNearestTaxi(Human human) {
        LOGGER.info("Cab company: Choosing the right taxi for " + human);
        int numberTaxi = -1;
        int humanLocationX = human.getLocationCoordinateX();
        int humanLocationY = human.getLocationCoordinateY();
        double nearestDistance = Integer.MAX_VALUE;
        LOGGER.info("Cab company: Showing all available taxi...");
        for (int i = 0; i < allTaxi.size(); i++) {
            int currentTaxiLocationX = allTaxi.get(i).getLocationX();
            int currentTaxiLocationY = allTaxi.get(i).getLocationY();
            double currentHumanWay = calculateDistance(currentTaxiLocationX, currentTaxiLocationY,
                    humanLocationX, humanLocationY);
            double currentTaxiRadius = allTaxi.get(i).getRadius();
            if (currentHumanWay < currentTaxiRadius
                    && currentHumanWay < nearestDistance) {
                numberTaxi = i;
                nearestDistance = currentHumanWay;
            }
            LOGGER.info("Cab company: " + allTaxi.get(i) +
                    ". And his way to human =" + currentHumanWay);
        }
        return numberTaxi;
    }

    private double calculateDistance(int locationTaxiX, int locationTaxiY, int locationHumanX, int locationHumanY) {
        double squareDistanceX = Math.pow(locationHumanX - locationTaxiX, 2);
        double squareDistanceY = Math.pow(locationHumanY - locationTaxiY, 2);
        return Math.sqrt(squareDistanceX + squareDistanceY);
    }
}
