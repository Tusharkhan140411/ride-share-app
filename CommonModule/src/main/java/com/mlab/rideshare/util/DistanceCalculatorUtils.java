package com.mlab.rideshare.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DistanceCalculatorUtils {

    /** Distance of two point on earth in kilometer  **/
    public static double distance(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) {
        if ((fromLatitude == toLatitude) && (fromLongitude == toLongitude)) {
            return 0;
        }
        else {
            double theta = fromLongitude - toLongitude;
            double dist = Math.sin(Math.toRadians(fromLatitude))
                    * Math.sin(Math.toRadians(toLatitude))
                    + Math.cos(Math.toRadians(fromLatitude))
                    * Math.cos(Math.toRadians(toLatitude))
                    * Math.cos(Math.toRadians(theta));
            dist = Math.toDegrees(Math.acos(dist));
            dist = dist * 60 * 1.1515 * 1.609344;
            return dist;
        }
    }
}
