package com.mindera.telemetron.client;

import com.mindera.telemetron.client.api.*;
import com.mindera.telemetron.client.config.ClientConfiguration;
import com.mindera.telemetron.client.sender.BufferedMetricsSender;
import com.mindera.telemetron.client.sender.MetricsSender;
import com.mindera.telemetron.client.transport.TransportSender;
import com.mindera.telemetron.client.transport.UDPSender;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

import static com.mindera.telemetron.client.api.Transport.UDP;

/**
 * This class is the Telemetron client, which allows to send metrics (timer, counter, gauge or raw metric) to Telemetron.
 */
public class TelemetronClient implements MetricsSender {

    private static final Logger LOGGER = Logger.getLogger(TelemetronClient.class.getName());

    private final MetricsSender metricsSender;
    private final ClientConfiguration configuration;

    TelemetronClient(final MetricsSender metricsSender, final ClientConfiguration configuration) {
        this.metricsSender = metricsSender;
        this.configuration = configuration;
    }

    /**
     * Creates a new Timer metrics builder.
     *
     * @param metricName The timer name to create
     * @param timestamp The timer timestamp value to send to Telemetron
     * @return A Counter metric builder, ready to be sent or configure
     */
    public final TelemetronClientDelegate timer(final String metricName, final long timestamp) {
        APIBuilder apiBuilder = new APIBuilder(this).with()
                .configuration(configuration)
                .aggregations(configuration.getTimerAggregations())
                .aggFreq(configuration.getTimerAggregationFreq())
                .tags(configuration.getTimerTags())
                .metricName("timer." + metricName)
                .value(Long.toString(timestamp));

        return new TelemetronClientDelegate(apiBuilder);
    }

    /**
     * Creates a new Counter metrics builder, which increments by one by default.
     *
     * @param metricName The counter name to create
     * @return A Counter metric builder, ready to be sent or configure
     */
    public final TelemetronClientDelegate counter(final String metricName) {
        return counter(metricName, 0);
    }

    /**
     * Creates a new Counter metrics builder.
     *
     * @param metricName The counter name to create
     * @param value The counter increment value to send to Telemetron
     * @return A Counter metric builder, ready to be sent or configure
     */
    public final TelemetronClientDelegate counter(final String metricName, final int value) {
        APIBuilder apiBuilder = new APIBuilder(this).with()
                .configuration(configuration)
                .aggregations(configuration.getCounterAggregations())
                .aggFreq(configuration.getCounterAggregationFreq())
                .tags(configuration.getCounterTags())
                .metricName("counter." + metricName)
                .value(Integer.toString(value));

        return new TelemetronClientDelegate(apiBuilder);
    }

    /**
     * Creates a new Gauge metrics builder.
     *
     * @param metricName The gauge name to create
     * @param value The gauge value to send to Telemetron
     * @return A Gauge metric builder, ready to be sent or configure
     */
    public final TelemetronClientDelegate gauge(final String metricName, final String value) {
        APIBuilder apiBuilder = new APIBuilder(this).with()
                .configuration(configuration)
                .aggregations(configuration.getGaugeAggregations())
                .aggFreq(configuration.getGaugeAggregationFreq())
                .tags(configuration.getGaugeTags())
                .metricName("gauge." + metricName)
                .value(value);

        return new TelemetronClientDelegate(apiBuilder);
    }

    @Override
    public final void put(
            final String name, final String value, final Tags tags, final Aggregations aggregations,
            final AggregationFreq aggregationFreq, final Integer sampleRate, final String namespace,
            final long timestamp
    ) {
        try {
            metricsSender.put(name, value, tags, aggregations, aggregationFreq, sampleRate, namespace, timestamp);
        } catch (Exception e) {
            LOGGER.warning("Unable to send metric: " + e.toString());
        }
    }

    @Override
    public final void shutdown() {
        metricsSender.shutdown();
    }

    /**
     * Instantiates a new Telemetron client builder with UDP protocol.
     *
     * @param prefix The metric prefix
     * @return A Telemetron client builder, ready for configure or bootstrap
     */
    public static TelemetronClientBuilder buildUDPClient(final String prefix) {
        ConfigurationBuilder<TelemetronClient> configurationBuilder = ConfigurationBuilder
                .newBuilder(builderChain).transport(UDP).prefix(prefix);

        return new TelemetronClientBuilder(configurationBuilder);
    }

    private static ConfigurationBuilderChain<TelemetronClient> builderChain =
            new ConfigurationBuilderChain<TelemetronClient>() {
        @Override
        public TelemetronClient build(final ClientConfiguration configuration) {
            int poolSize = configuration.getWorkersPoolSize();
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(poolSize);

            TransportSender transportSender = new UDPSender(configuration.getHost(), configuration.getPort());
            MetricsSender bufferedMetricsSender = new BufferedMetricsSender(transportSender, configuration, executorService);
            return new TelemetronClient(bufferedMetricsSender, configuration);
        }
    };
}
