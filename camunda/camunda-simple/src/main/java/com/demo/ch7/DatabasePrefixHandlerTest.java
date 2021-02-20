package com.demo.ch7;

import org.camunda.bpm.engine.impl.digest.DatabasePrefixHandler;
import org.junit.Test;

/**
 * @author zhoupeng
 */
public class DatabasePrefixHandlerTest {


    @Test
    public void testGeneratePrefix() {
        DatabasePrefixHandler databasePrefixHandler = new DatabasePrefixHandler();
        String peng = databasePrefixHandler.generatePrefix("peng");
        System.out.println(peng);
    }


    @Test
    public void testRetrieveAlgorithmName() {
        String psd="{SHA-512}uImcok93uhHbK7/uyVZjoUwK3ctZuFAfdQooKX0iqGIN3j/bYP3FakdCwHizdmhpBOS6SDlod98ODfGkO7FQPA==";
        DatabasePrefixHandler databasePrefixHandler = new DatabasePrefixHandler();
        String text = databasePrefixHandler.retrieveAlgorithmName(psd);
        System.out.println(text);
    }
}
