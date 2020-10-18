package com.trsljs.service;

import com.trsljs.util.TimeUtilities;

import java.text.NumberFormat;
import java.time.LocalTime;

/**
 * Service for the Babysitting Calculator
 *
 * @author <a href="mailto:timothyrocksmith@gmail.com">Tim Smith</a>
 */
public class CalculatorService {
    private static final int START_TO_BEDTIME_RATE = 12;
    private static final int BEDTIME_TO_MIDNIGHT_RATE = 8;
    private static final int MIDNIGHT_TO_END_RATE = 16;

    /**
     * Calculates the total charge for a single day of babysitting from the given times.
     *
     * @param startTime the {@link LocalTime} start time chosen.
     * @param bedtime the {@link LocalTime} bedtime chosen.
     * @param endTime the {@link LocalTime} end time chosen.
     * @return {@link String} formatted to currency of the calculation.
     */
    public String calculateJobTotal(LocalTime startTime, LocalTime bedtime, LocalTime endTime) {
        // Adjust the times to start at midnight to simplify calculations.
        LocalTime adjustedStartTime = TimeUtilities.adjustTimePeriod(startTime);
        LocalTime adjustedBedTime = TimeUtilities.adjustTimePeriod(bedtime);
        LocalTime adjustedEndTime = TimeUtilities.adjustTimePeriod(endTime);
        LocalTime adjustedMidnight = TimeUtilities.ADJUSTED_MIDNIGHT;

        /*
         * If the start time is later than the bed time, we are in the bedtime to midnight rate, so we
         * adjust the bedtime to the same as start time so the start time to bedtime hours is 0 for the calculations.
         */
        if (adjustedStartTime.isAfter(adjustedBedTime)) {
            adjustedBedTime = adjustedStartTime;
        }
        /*
         * If the start time is midnight or after, we are in the midnight to end rate, but we don't want to calculate
         * from midnight, so adjust midnight to start time for the calculation
        */
        if (adjustedStartTime.isAfter(adjustedMidnight)) {
            adjustedMidnight = adjustedStartTime;
        }
        /*
         * In cases where the shift ends before midnight, we adjust midnight to be the end time so the calculations
         * do not record hours until midnight of after.
         */
        if (adjustedEndTime.isBefore(adjustedMidnight)) {
            adjustedMidnight = adjustedEndTime;
        }
        if (adjustedEndTime.isBefore(adjustedBedTime)) {
            adjustedBedTime = adjustedEndTime;
        }

        /*
         * Calculate the number of hours for each time period of pay.
         */
        int hoursUntilBedtime = adjustedBedTime.getHour() - adjustedStartTime.getHour();
        int hoursBedtimeUntilMidnight = adjustedMidnight.getHour() - adjustedBedTime.getHour();
        int hoursMidnightUntilEnd = adjustedEndTime.getHour() - adjustedMidnight.getHour();

        /*
         * Calculate the total charge for each time period and add them to the total;
         */
        int pay = hoursUntilBedtime * START_TO_BEDTIME_RATE;
        pay += hoursBedtimeUntilMidnight * BEDTIME_TO_MIDNIGHT_RATE;
        pay += hoursMidnightUntilEnd * MIDNIGHT_TO_END_RATE;

        /*
         * Format the result
         */
        NumberFormat format = NumberFormat.getCurrencyInstance();

        return format.format(pay);
    }

    /**
     * Checks if start time is later than end time
     * @param startTime the {@link LocalTime} start time
     * @param endTime the {@link LocalTime} end time
     * @return true iff start time is later than end time.
     */
    public boolean isStartTimeAfterEndTime(LocalTime startTime, LocalTime endTime) {
        return TimeUtilities.adjustTimePeriod(startTime).isAfter(TimeUtilities.adjustTimePeriod(endTime));
    }
}
