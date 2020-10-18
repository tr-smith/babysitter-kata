package com.trsljs.service;


import org.junit.Before;
import org.junit.Test;

import java.text.NumberFormat;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class CalculatorServiceTest {
    private CalculatorService testService;
    private NumberFormat format;

    @Before
    public void createInstance() {
        testService = new CalculatorService();
        format = NumberFormat.getCurrencyInstance();
    }

    @Test
    public void testCalculateJobTotalThreeTimePeriods() {
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime bedtime = LocalTime.of(21, 0);
        LocalTime endTime = LocalTime.of(2, 0);
        int expectedTotal = 104;
        assertEquals(format.format(expectedTotal), testService.calculateJobTotal(startTime, bedtime, endTime));
    }

    @Test
    public void testCalculateJobTotalStartAfterBedtime() {
        LocalTime startTime = LocalTime.of(22, 0);
        LocalTime bedtime = LocalTime.of(21, 0);
        LocalTime endTime = LocalTime.of(2, 0);
        int expectedTotal = 48;
        assertEquals(format.format(expectedTotal), testService.calculateJobTotal(startTime, bedtime, endTime));
    }

    @Test
    public void testCalculateJobTotalStartAfterMidnight() {
        LocalTime startTime = LocalTime.of(1, 0);
        LocalTime bedtime = LocalTime.of(21, 0);
        LocalTime endTime = LocalTime.of(3, 0);
        int expectedTotal = 32;
        assertEquals(format.format(expectedTotal), testService.calculateJobTotal(startTime, bedtime, endTime));
    }

    @Test
    public void testCalculateJobEndTimeBeforeMidnight() {
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime bedtime = LocalTime.of(21, 0);
        LocalTime endTime = LocalTime.of(23, 0);
        int expectedTotal = 64;
        assertEquals(format.format(expectedTotal), testService.calculateJobTotal(startTime, bedtime, endTime));
    }

    @Test
    public void testCalculateJobTotalEndTimeBeforeBedtime() {
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime bedtime = LocalTime.of(21, 0);
        LocalTime endTime = LocalTime.of(20, 0);
        int expectedTotal = 36;
        assertEquals(format.format(expectedTotal), testService.calculateJobTotal(startTime, bedtime, endTime));
    }

    @Test
    public void testIsStartTimeAfterEndTimeTrue() {
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        assertTrue(testService.isStartTimeAfterEndTime(startTime, endTime));
    }

    @Test
    public void testIsStartTimeAfterEndTimeFalse() {
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime endTime = LocalTime.of(18, 0);

        assertFalse(testService.isStartTimeAfterEndTime(startTime, endTime));
    }
}
