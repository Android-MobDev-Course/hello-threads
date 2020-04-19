package com.mobdev.hellothreads.utils;

import com.mobdev.hellothreads.model.LogDescriptor;

import java.util.Random;

/**
 * Created by Marco Picone picone.m@gmail.com on 19,April,2020
 * Mobile System Development - University Course
 */
public class LogFactory {

    public static LogDescriptor createRandomLogDescriptor() {
        Random random = new Random();

        double x0 = 44.766992;
        double y0 = 10.310035;
        int radius = 1000;

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(y0);

        double foundLongitude = new_x + x0;
        double foundLatitude = y + y0;

        int number = random.nextInt((1000 - 0) + 1);

        return new LogDescriptor(foundLatitude, foundLongitude, "RANDOM_LOG", Double.valueOf(number).toString());
    }

}
