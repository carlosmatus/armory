package com.demo.cm;

import org.junit.Test;
import rx.observers.TestSubscriber;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class LogHandlerTest {
    @Test
    public void displayLogSuccessful() throws IOException {
        final  String path = "/Users/carlosma/dev/paso";

       TestSubscriber<File> subscriber = new TestSubscriber<>();
        LogHandler.processLogs(path).subscribe(subscriber);
        System.out.println(subscriber.getOnNextEvents());
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        assertNotNull("No content",subscriber.getOnNextEvents());

    }
}
