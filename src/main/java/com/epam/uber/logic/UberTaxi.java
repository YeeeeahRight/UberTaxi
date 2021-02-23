package com.epam.uber.logic;

import org.apache.log4j.Logger;

public class UberTaxi {
    private static final Logger LOGGER = Logger.getLogger(UberTaxi.class);
    private int locationX;
    private int locationY;
    private final int taxiNumber;
    private final double radius;

    public UberTaxi(int locationX, int locationY,
                    int taxiNumber, double radius) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.taxiNumber = taxiNumber;
        this.radius = radius;
    }

    public void transfer(Human human) {
        LOGGER.info(String.format("Taxi #%d goes to the %s, who is in [%d, %d] point",
                taxiNumber, human.getName(), human.getLocationCoordinateX(), human.getLocationCoordinateY()));
        LOGGER.info(String.format("Taxi #%d took %s and goes to the [%d, %d] destination point",
                taxiNumber, human.getName(), human.getDestinationPointX(), human.getDestinationPointY()));
        human.setIsArrived(true);
        LOGGER.info(String.format("Taxi #%d delivered %s to the destination point!", taxiNumber, human.getName()));
        locationX = human.getDestinationPointX();
        locationY = human.getDestinationPointY();
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public int getTaxiNumber() {
        return taxiNumber;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "UberTaxi{" +
                "locationX=" + locationX +
                ", locationY=" + locationY +
                ", taxiNumber=" + taxiNumber +
                ", radius=" + radius + '}';
    }
}
