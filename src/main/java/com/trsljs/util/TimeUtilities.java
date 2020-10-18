package com.trsljs.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

/**
 * Utility Class providing static methods to handle operations on {@link LocalTime}
 *
 * @author <a href="mailto:timothyrocksmith@gmail.com">Tim Smith</a>
 */
public class TimeUtilities {
    public static final LocalTime START_TIME_MINIMUM = LocalTime.of(17,0);
    public static final LocalTime END_TIME_MAXIMUM = LocalTime.of(4, 0);
    private static final int TIME_ADJUSTMENT = START_TIME_MINIMUM.getHour();

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");
    public static final LocalTime ADJUSTED_MIDNIGHT = LocalTime.MIDNIGHT.minusHours(TIME_ADJUSTMENT);
    public static final LocalTime ADJUSTED_END_TIME_MAXIMUM = adjustTimePeriod(END_TIME_MAXIMUM);



    /**
     * Adjust the time period to handle comparisons after midnight.
     * Since {@link LocalTime} does not roll over to another day, and our shift is per day calculation,
     * we move the required minimum start time back to midnight giving us the time adjustment value that all times
     * will be adjusted to for comparing one time against the other.
     *
     * @param time the {@link LocalTime} to adjust
     * @return the adjusted {@link LocalTime}
     */
    public static LocalTime adjustTimePeriod(LocalTime time) {
        return time.minusHours(TIME_ADJUSTMENT);
    }

    /**
     * Generates a {@link Vector<String>} of time values starting with the minimum start time required in increments of
     * whole hours minus 1 hour from the maximum end time to populate the start time combobox.
     *
     * @return {@link Vector<String>} of time values.
     */
    public static Vector<String> getValidStartTimes() {
        Vector<String> times = new Vector<>();
        times.add("");
        LocalTime startTime = START_TIME_MINIMUM;
        for (int i = 0; i < ADJUSTED_END_TIME_MAXIMUM.getHour(); i++) {
            times.add(startTime.format(TIME_FORMATTER));
            startTime = startTime.plusHours(1L);
        }
        return times;
    }

    /**
     * Generates a {@link Vector<String>} of time values starting with the minimum start time required in
     * increments of whole hours to midnight to populate the bedtime combobox.
     *
     * @return {@link Vector<String>} of time values.
     */
    public static Vector<String> getValidBedtimes() {
        Vector<String> times = new Vector<>();
        times.add("");
        LocalTime startTime = START_TIME_MINIMUM;
        for (int i = 0; i <= 24 - START_TIME_MINIMUM.getHour(); i++) {
            times.add(startTime.format(TIME_FORMATTER));
            startTime = startTime.plusHours(1L);
        }
        return times;
    }

    /**
     * Generates a {@link Vector<String>} of time values starting with the minimum start time required plus 1 hour in
     * increments of whole hours to the maximum end time to populate the end time combobox.
     *
     * @return {@link Vector<String>} of time values.
     */
    public static Vector<String> getValidEndTimes() {
        Vector<String> times = new Vector<>();
        times.add("");
        LocalTime startTime = START_TIME_MINIMUM.plusHours(1L);
        for (int i = 0; i < ADJUSTED_END_TIME_MAXIMUM.getHour(); i++) {
            times.add(startTime.format(TIME_FORMATTER));
            startTime = startTime.plusHours(1L);
        }
        return times;
    }
}
