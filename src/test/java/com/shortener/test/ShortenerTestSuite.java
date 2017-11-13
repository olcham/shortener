package com.shortener.test;

import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(value={Test1_OpenAccount.class, Test2_OpenAccountNegative.class, 
                           Test3_RegisterUrl.class, Test4_Redirect.class,
                           Test5_Statistic.class})
public class ShortenerTestSuite {    

    public static TestSuite suite() {
        TestSuite suite = new TestSuite("ShortenerTestSuite");
        suite.addTest(new TestSuite(com.shortener.test.Test1_OpenAccount.class));
        suite.addTest(new TestSuite(com.shortener.test.Test2_OpenAccountNegative.class));
        suite.addTest(new TestSuite(com.shortener.test.Test3_RegisterUrl.class));
        suite.addTest(new TestSuite(com.shortener.test.Test4_Redirect.class));
        suite.addTest(new TestSuite(com.shortener.test.Test5_Statistic.class));
        return suite;
    }
    
}
