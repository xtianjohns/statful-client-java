package com.mindera.telemetron.client.api;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TagsTest {

    @Test
    public void shouldGetFromTypeAndValue() throws Exception {
        Tags tags = Tags.from("unit", "ms");

        assertEquals("ms", tags.getTagValue("unit"));
    }

    @Test
    public void shouldGetFromOtherTag() throws Exception {
        Tags tags = Tags.from(Tags.from("unit", "ms"));

        assertEquals("ms", tags.getTagValue("unit"));
    }

    @Test
    public void shouldPutTag() throws Exception {
        Tags tags = new Tags();
        tags.putTag("unit", "ms");

        assertEquals("ms", tags.getTagValue("unit"));
    }

    @Test
    public void shouldGetTags() throws Exception {
        Tags tags = Tags.from("unit", "ms");

        assertEquals("ms", tags.getTags().get("unit"));
    }

    @Test
    public void shouldMergeTags() throws Exception {
        Tags tags = Tags.from("unit", "ms");

        tags.merge(Tags.from("cluster", "production"));

        assertEquals("ms", tags.getTags().get("unit"));
        assertEquals("production", tags.getTags().get("cluster"));
    }

    @Test
    public void shouldNotMergeTagsIfArgumentIsNull() throws Exception {
        Tags tags = Tags.from("unit", "ms");

        tags.merge(null);

        assertEquals("ms", tags.getTags().get("unit"));
        assertEquals(1, tags.getTags().size());
    }

    @Test
    public void shouldCheckIfTagTypeIsEmpty() throws Exception {
        assertTrue(Tags.isEmpty("", "ms"));
    }

    @Test
    public void shouldCheckIfTagTypeIsNull() throws Exception {
        assertTrue(Tags.isEmpty(null, "ms"));
    }

    @Test
    public void shouldCheckIfTagValueIsEmpty() throws Exception {
        assertTrue(Tags.isEmpty("unit", ""));
    }

    @Test
    public void shouldCheckIfTagValueIsNull() throws Exception {
        assertTrue(Tags.isEmpty("unit", null));
    }

    @Test
    public void shouldCheckIfTypeAndValueAreEmpty() throws Exception {
        assertTrue(Tags.isEmpty("", ""));
    }

    @Test
    public void shouldCheckIfTypeAndValueAreNull() throws Exception {
        assertTrue(Tags.isEmpty(null, null));
    }

    @Test
    public void shouldCheckIfTagIsNotEmpty() throws Exception {
        assertFalse(Tags.isEmpty("unit", "ms"));
    }
}