package com.ibrahimbayburtlu.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class ConditionalTest {

    @Test
    @Disabled("Don't run until JIRA #123 is resolved")
    void basicText(){
        // execute method perform assertions
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testForWindowsOnly(){
        // execute method and perform assertions
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void testForMacOnly(){
        // execute method and perform assertions
    }

    @Test
    @EnabledOnOs({OS.WINDOWS, OS.MAC})
    void testForWindowsAndMacOnly(){
        // execute method and perform assertions
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testFormLinuxOnly(){
        // execute method and perform assertions
    }

}
