package com.ibrahimbayburtlu.junitdemo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemoUtilsTest {

    // every test annotation just one testing
    @Test
    void testEqualsAndNotEquals(){

        DemoUtils demoUtils = new DemoUtils();

        assertEquals(6,demoUtils.add(2,4),"2 + 4 must be 6");
        assertNotEquals(9,demoUtils.add(2,4),"2 + 4 must not be 9");
    }

    // assertNull -> expected null result
    @Test
    void testNullAndNotNull(){
        DemoUtils demoUtils = new DemoUtils();

        String strOne = null;
        String strTwo = "ibo";

        assertNull(demoUtils.checkNull(strOne),"Object should be null");
    }

}
