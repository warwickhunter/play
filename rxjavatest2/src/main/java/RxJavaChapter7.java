import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

/**
 * Experiments with RxJava from "Reactive Programming with RxJava"
 *
 * @author Warwick Hunter
 * @since  2018-02-19
 */
public class RxJavaChapter7 {
    static final MetricRegistry metrics = new MetricRegistry();
    static final Counter counter = metrics.counter("counter");

    public static void main(String[] args) throws InterruptedException {
        startReport();
        Meter requests = metrics.meter("requests");
        requests.mark();
        Timer timer = metrics.timer("timer");
        Timer.Context ctx = timer.time();
        wait5Seconds();
        ctx.stop();
    }

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .formattedFor(Locale.UK)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void wait5Seconds() {
        try {
            Thread.sleep(5000);
            counter.inc();
        }
        catch(InterruptedException e) {}
    }
}