package com.ibrahimbayburtlu.junitdemo;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setUpBeforeEach(){
        // set up
        demoUtils = new DemoUtils();
        System.out.println("@BeforeEach executes before the execution of each test method.");
    }

    @AfterEach
    void testDownAfterEach(){
        System.out.println("Running @AfterEach");
    }

    @BeforeAll
    static void setupBeforeEachClass(){
        System.out.println("@BeforeAll executes only once before all test methods execution is the class.\n");
    }

    @AfterAll
    static void tearDownAfterAll(){
        System.out.println("@AfterAll executes only once after all test methods execution in the class.\n");
    }

    // every test annotation just one testing
    @Test
    void testEqualsAndNotEquals(){

        System.out.println("Running Test: testEqualsAndNotEquals");
        // execute and assert
        assertEquals(6,demoUtils.add(2,4),"2 + 4 must be 6");
        assertNotEquals(9,demoUtils.add(2,4),"2 + 4 must not be 9");
    }

    // assertNull -> expected null result
    @Test
    void testNullAndNotNull(){

        System.out.println("Running Test: testNullAndNotNull");

        String strOne = null;
        assertNull(demoUtils.checkNull(strOne),"Object should be null");
    }

}
