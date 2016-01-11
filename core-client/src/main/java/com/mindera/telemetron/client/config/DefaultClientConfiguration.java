package com.mindera.telemetron.client.config;

import com.mindera.telemetron.client.api.*;

import static com.mindera.telemetron.client.api.Aggregation.*;
import static com.mindera.telemetron.client.api.Aggregation.LAST;
import static com.mindera.telemetron.client.api.AggregationFreq.FREQ_10;

/**
 * This is thh default client configuration class.
 */
public class DefaultClientConfiguration implements ClientConfiguration {

    private static final int WORKER_POOL_SIZE = 1;

    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 2013;
    private static final int DEFAULT_SAMPLE_RATE = 100;
    private static final String DEFAULT_NAMESPACE = "application";
    private static final int DEFAULT_FLUSH_SIZE = 10;
    private static final AggregationFreq DEFAULT_AGGREGATION_FREQ = FREQ_10;

    private static final Tags DEFAULT_APP_TAGS = Tags.from("telemetron_client", "java");
    private static final Tags DEFAULT_TIMER_TAGS = Tags.from("unit", "ms");

    private static final Aggregation[] DEFAULT_TIMER_AGGREGATIONS = new Aggregation[] {AVG, P90, COUNT};
    private static final Aggregation[] DEFAULT_COUNTER_AGGREGATIONS = new Aggregation[] {AVG, P90};
    private static final Aggregation[] DEFAULT_GAUGE_AGGREGATIONS = new Aggregation[] {LAST};

    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private int sampleRate = DEFAULT_SAMPLE_RATE;
    private String namespace = DEFAULT_NAMESPACE;
    private int flushSize = DEFAULT_FLUSH_SIZE;

    private String prefix;
    private boolean isDryRun;
    private long flushIntervalMillis;
    private Transport transport;
    private String token;
    private String app;
    private int workersPoolSize = WORKER_POOL_SIZE;

    private Tags applicationTags = Tags.from(DEFAULT_APP_TAGS);
    private Tags timerTags = Tags.from(DEFAULT_TIMER_TAGS);
    private Tags counterTags;
    private Tags gaugeTags;

    private Aggregations timerAggregations = Aggregations.from(DEFAULT_TIMER_AGGREGATIONS);
    private Aggregations counterAggregations = Aggregations.from(DEFAULT_COUNTER_AGGREGATIONS);
    private Aggregations gaugeAggregations = Aggregations.from(DEFAULT_GAUGE_AGGREGATIONS);

    private AggregationFreq timerAggregationFreq = DEFAULT_AGGREGATION_FREQ;
    private AggregationFreq counterAggregationFreq = DEFAULT_AGGREGATION_FREQ;
    private AggregationFreq gaugeAggregationFreq = DEFAULT_AGGREGATION_FREQ;

    @Override
    public final boolean isValid() {
        return prefix != null && transport != null;
    }

    @Override
    public final Tags getApplicationTags() {
        return applicationTags;
    }

    @Override
    public final int getSampleRate() {
        return sampleRate;
    }

    @Override
    public final String getNamespace() {
        return namespace;
    }

    @Override
    public final String getPrefix() {
        return prefix;
    }

    @Override
    public final int getFlushSize() {
        return flushSize;
    }

    @Override
    public final long getFlushIntervalMillis() {
        return flushIntervalMillis;
    }

    @Override
    public final boolean isDryRun() {
        return isDryRun;
    }

    @Override
    public final String getHost() {
        return host;
    }

    @Override
    public final int getPort() {
        return port;
    }

    @Override
    public final Transport getTransport() {
        return transport;
    }

    @Override
    public final String getToken() {
        return token;
    }

    @Override
    public final String getApp() {
        return app;
    }

    @Override
    public final int getWorkersPoolSize() {
        return workersPoolSize;
    }

    @Override
    public final Tags getTimerTags() {
        return timerTags;
    }

    @Override
    public final Aggregations getTimerAggregations() {
        return timerAggregations;
    }

    @Override
    public final AggregationFreq getTimerAggregationFreq() {
        return timerAggregationFreq;
    }

    @Override
    public final Tags getCounterTags() {
        return counterTags;
    }

    @Override
    public final Aggregations getCounterAggregations() {
        return counterAggregations;
    }

    @Override
    public final AggregationFreq getCounterAggregationFreq() {
        return counterAggregationFreq;
    }

    @Override
    public final Tags getGaugeTags() {
        return gaugeTags;
    }

    @Override
    public final Aggregations getGaugeAggregations() {
        return gaugeAggregations;
    }

    @Override
    public final AggregationFreq getGaugeAggregationFreq() {
        return gaugeAggregationFreq;
    }

    /**
     * Setter for hostname.
     *
     * @param host The hostname
     */
    public final void setHost(final String host) {
        this.host = host;
    }

    /**
     * Setter for port.
     *
     * @param port The port
     */
    public final void setPort(final int port) {
        this.port = port;
    }

    /**
     * Setter for sample rate.
     *
     * @param sampleRate The sample rate
     */
    public final void setSampleRate(final int sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * Setter for namespace.
     *
     * @param namespace The namespace
     */
    public final void setNamespace(final String namespace) {
        this.namespace = namespace;
    }

    /**
     * Setter for flush size
     *
     * @param flushSize The flush size
     */
    public final void setFlushSize(final int flushSize) {
        this.flushSize = flushSize;
    }

    /**
     * Setter for prefix.
     *
     * @param prefix The prefix
     */
    public final void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    /**
     * Setter for dry run
     *
     * @param isDryRun Boolean value for dry run
     */
    public final void setDryRun(final boolean isDryRun) {
        this.isDryRun = isDryRun;
    }

    /**
     * Setter for flush interval in milliseconds.
     *
     * @param flushIntervalMillis The flush interval in milliseconds
     */
    public final void setFlushIntervalMillis(final long flushIntervalMillis) {
        this.flushIntervalMillis = flushIntervalMillis;
    }

    /**
     * Setter for transport.
     *
     * @param transport The {@link com.mindera.telemetron.client.api.Transport} to use
     */
    public final void setTransport(final Transport transport) {
        this.transport = transport;
    }

    /**
     * Setter for application token.
     *
     * @param token The token
     */
    public final void setToken(final String token) {
        this.token = token;
    }

    /**
     * Setter for app.
     * <p>
     * If set, it merges the app name with the application tags.
     *
     * @param app The app name
     */
    public final void setApp(final String app) {
        this.app = app;
        mergeTagIntoMethods("app", app);
    }

    /**
     * Setter for the worker pool size.
     *
     * @param workersPoolSize The poll size
     */
    public final void setWorkersPoolSize(final int workersPoolSize) {
        this.workersPoolSize = workersPoolSize;
    }

    /**
     * Setter for aggregation frequency for timer.
     *
     * @param timerAggregationFreq The {@link com.mindera.telemetron.client.api.AggregationFreq} to use
     */
    public final void setTimerAggregationFreq(final AggregationFreq timerAggregationFreq) {
        this.timerAggregationFreq = timerAggregationFreq;
    }

    /**
     * Merges the tag defined by <code>type</code> and <code>value</code> with the existent timer tags.
     *
     * @param type The type of the tag to merge
     * @param value The value of the tag to merge
     */
    public final void mergeTimerTag(final String type, final String value) {
        this.timerTags.putTag(type, value);
    }

    /**
     * Merges the tag defined by <code>aggregation</code> with the existent timer aggregations.
     *
     * @param aggregation The {@link com.mindera.telemetron.client.api.Aggregation} to merge
     */
    public final void mergeTimerAggregation(final Aggregation aggregation) {
        this.timerAggregations.put(aggregation);
    }

    /**
     * Setter for aggregation frequency for counter.
     *
     * @param counterAggregationFreq The {@link com.mindera.telemetron.client.api.AggregationFreq} to use
     */
    public final void setCounterAggregationFreq(final AggregationFreq counterAggregationFreq) {
        this.counterAggregationFreq = counterAggregationFreq;
    }

    /**
     * Merges the tag defined by <code>type</code> and <code>value</code> with the existent counter tags.
     *
     * @param type The type of the tag to merge
     * @param value The value of the tag to merge
     */
    public final void mergeCounterTag(final String type, final String value) {
        getSafeCounterTags().putTag(type, value);
    }

    /**
     * Merges the tag defined by <code>aggregation</code> with the existent counter aggregations.
     *
     * @param aggregation The {@link com.mindera.telemetron.client.api.Aggregation} to merge
     */
    public final void mergeCounterAggregation(final Aggregation aggregation) {
        this.counterAggregations.put(aggregation);
    }

    /**
     * Setter for aggregation frequency for gauge.
     *
     * @param gaugeAggregationFreq The {@link com.mindera.telemetron.client.api.AggregationFreq} to use
     */
    public final void setGaugeAggregationFreq(final AggregationFreq gaugeAggregationFreq) {
        this.gaugeAggregationFreq = gaugeAggregationFreq;
    }

    /**
     * Merges the tag defined by <code>type</code> and <code>value</code> with the existent gauge tags.
     *
     * @param type The type of the tag to merge
     * @param value The value of the tag to merge
     */
    public final void mergeGaugeTag(final String type, final String value) {
        getSafeGaugeTags().putTag(type, value);
    }

    /**
     * Merges the tag defined by <code>aggregation</code> with the existent gauge aggregations.
     *
     * @param aggregation The {@link com.mindera.telemetron.client.api.Aggregation} to merge
     */
    public final void mergeGaugeAggregation(final Aggregation aggregation) {
        this.gaugeAggregations.put(aggregation);
    }

    /**
     * Merges the tag defined by <code>type</code> and <code>value</code> with the existent application tags.
     *
     * @param type The type of the tag to merge
     * @param value The value of the tag to merge
     */
    public final void mergeApplicationTag(final String type, final String value) {
        applicationTags.putTag(type, value);
        mergeTagIntoMethods(type, value);
    }

    private void mergeTagIntoMethods(final String type, final String value) {
        timerTags.putTag(type, value);
        getSafeCounterTags().putTag(type, value);
        getSafeGaugeTags().putTag(type, value);
    }

    private Tags getSafeCounterTags() {
        if (counterTags == null) {
            counterTags = new Tags();
        }
        return counterTags;
    }

    private Tags getSafeGaugeTags() {
        if (gaugeTags == null) {
            gaugeTags = new Tags();
        }
        return gaugeTags;
    }
}
