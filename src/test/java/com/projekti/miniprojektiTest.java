package com.projekti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class miniprojektiTest {

    @Test
    public void testAddPositiveNumbers() {
        Miniprojekti calculator = new Miniprojekti();
        int result = calculator.add(2, 3);
        assertEquals(5, result);
    }

    @Test
    public void testSubtractPositiveNumbers() {
        Miniprojekti calculator = new Miniprojekti();
        int result = calculator.subtract(5, 2);
        assertEquals(3, result);
    }

    // ... more test cases for different scenarios
}