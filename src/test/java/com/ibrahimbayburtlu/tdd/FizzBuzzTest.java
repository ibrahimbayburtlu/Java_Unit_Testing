package com.ibrahimbayburtlu.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {

    // If number is divisible by 3, print Fizz
    // If number is divisible by 5, print Buzz
    // If number is divisible by 3 and 5, print Fizz
    // If number is NOT  divisible by 3  or 5, then print the number

    FizzBuzz fizzBuzz;

    @BeforeEach
    void setUpBeforeEach(){
        fizzBuzz = new FizzBuzz();
    }

    @DisplayName("Divisible by Three")
    @Test
    @Order(1)
    void testForDivisibleThree(){
        String expected = "Fizz";
        assertEquals(expected,fizzBuzz.computer(3),"Should return Fizz");
    }

    @DisplayName("Divisible by Five")
    @Test
    @Order(2)
    void testForDivisibleFive(){
        String expected = "Buzz";
        assertEquals(expected,fizzBuzz.computer(5),"Should return Buzz");
    }

    @DisplayName("Divisible by Three and Five ")
    @Test
    @Order(3)
    void testForDivisibleThreeAndFive(){
        String expected = "FizzBuzz";
        assertEquals(expected,fizzBuzz.computer(15),"Should return FizzBuzz");
    }

    @DisplayName("Divisible by Not Three and Five ")
    @Test
    @Order(4)
    void testForDivisibleNotThreeAndFive(){
        String expected = "1";
        assertEquals(expected,fizzBuzz.computer(1),"Should return number");
    }
    @DisplayName("Loop over array")
    @Test
    @Order(5)
    void testLoopOverArray(){

        String[][] data = {{"1","1"},
                {"2","2"},
                {"3","Fizz"},
                {"4","4"},
                {"5","Buzz"},
                {"6","Fizz"},
                {"7","7"}
        };

        for (String[] datum : data) {
            String value = datum[0];
            String expected = datum[1];
            assertEquals(expected, fizzBuzz.computer(Integer.parseInt(value)));
        }
    }

    @DisplayName("Testing with csv Java")
    @ParameterizedTest
    @CsvSource({"1,1",
                "2,2",
                "3,Fizz",
                "4,4",
                "5,Buzz",
                "6,Fizz",
                "7,7"})
    @Order(6)
    void testCsvData(int value,String expected){
        assertEquals(expected,fizzBuzz.computer(value));
    }

}
