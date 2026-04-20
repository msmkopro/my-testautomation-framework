package com.demo.tests.DummyClass;

import com.demo.base.BaseClass;
import org.testng.annotations.Test;

public class DummyTest2 extends BaseClass {

    @Test
    public void dummyTest2(){

        String title = driver.getTitle();

        assert title.equals("Automation Exercise"): "Test Failed - No Matching Title";
        System.out.println("Test Passed - Matching Title");
    }
}
