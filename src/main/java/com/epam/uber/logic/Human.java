package com.epam.uber.logic;

import com.epam.uber.exceptions.UberException;
import org.apache.log4j.Logger;

import java.util.Objects;

public class Human implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Human.class);

    private int locationCoordinateX;
    private int locationCoordinateY;
    private boolean isArrived;
    private final int destinationPointX;
    private final int destinationPointY;
    private final String name;
    private final UberCabCompany taxiPark;

    public Human(int locationCoordinateX, int locationCoordinateY,
                 int destinationPointX, int destinationPointY, String name, UberCabCompany taxiPark) {
        this.locationCoordinateX = locationCoordinateX;
        this.locationCoordinateY = locationCoordinateY;
        this.destinationPointX = destinationPointX;
        this.destinationPointY = destinationPointY;
        this.name = name;
        this.taxiPark = taxiPark;
    }

    public int getLocationCoordinateX() {
        return locationCoordinateX;
    }

    public int getLocationCoordinateY() {
        return locationCoordinateY;
    }

    public int getDestinationPointX() {
        return destinationPointX;
    }

    public int getDestinationPointY() {
        return destinationPointY;
    }

    public void setIsArrived(boolean arrived) {
        isArrived = arrived;
    }

    public boolean isArrived() {
        return isArrived;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            LOGGER.info(String.format("%s: is calling to Uber cab company...", name));
            UberTaxi taxi = taxiPark.acquireTaxi(this);
            if (taxi == null) {
                LOGGER.info(String.format("%s: is getting Uber notification of rejecting call " +
                        "and starts finding other taxi companies :(", name));
            } else {
                LOGGER.info(String.format("%s: is getting Uber notification of accepting call " +
                        "and starts waiting uber taxi #%d...", name, taxi.getTaxiNumber()));
                taxi.transfer(this);
                if (isArrived) {
                    locationCoordinateX = destinationPointX;
                    locationCoordinateY = destinationPointY;
                    LOGGER.info(String.format("%s: is arrived to destination point!", name));
                } else {
                    //in this program version it impossible, but in the future versions can be
                    LOGGER.info(String.format("%s: is not arrived to destination point!", name));
                }
                taxiPark.releaseTaxi(taxi);
            }
        } catch (UberException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "Human{" +
                "locationCoordinateX=" + locationCoordinateX +
                ", locationCoordinateY=" + locationCoordinateY +
                ", destinationPointX=" + destinationPointX +
                ", destinationPointY=" + destinationPointY +
                ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Human)) {
            return false;
        }

        Human human = (Human) o;

        if (getLocationCoordinateX() != human.getLocationCoordinateX()) {
            return false;
        }
        if (getLocationCoordinateY() != human.getLocationCoordinateY()) {
            return false;
        }
        if (getDestinationPointX() != human.getDestinationPointX()) {
            return false;
        }
        if (getDestinationPointY() != human.getDestinationPointY()) {
            return false;
        }
        if (isArrived() != human.isArrived()) {
            return false;
        }
        if (getName() != null ? !getName().equals(human.getName()) : human.getName() != null) {
            return false;
        }
        return Objects.equals(taxiPark, human.taxiPark);
    }

    @Override
    public int hashCode() {
        int result = getLocationCoordinateX();
        result = 31 * result + getLocationCoordinateY();
        result = 31 * result + getDestinationPointX();
        result = 31 * result + getDestinationPointY();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (taxiPark != null ? taxiPark.hashCode() : 0);
        result = 31 * result + (isArrived() ? 1 : 0);
        return result;
    }
}
