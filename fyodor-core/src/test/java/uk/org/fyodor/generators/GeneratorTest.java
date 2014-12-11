package uk.org.fyodor.generators;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static uk.org.fyodor.FyodorAssertions.assertThat;


public class GeneratorTest {
    @Test
    public void shouldCreateAStreamForTheGenerator() {
        // Given
        final Iterator<String> expectedElements = newArrayList("1", "2", "3", "4").iterator();
        final Generator<String> stubGenerator = expectedElements::next;

        // When
        final List<String> actualElements = stubGenerator.stream().limit(4).collect(toList());

        // Then
        assertThat(actualElements).containsExactly("1", "2", "3", "4");
    }

}