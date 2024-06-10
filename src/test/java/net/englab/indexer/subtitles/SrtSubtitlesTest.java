package net.englab.indexer.subtitles;

import net.englab.common.search.models.common.TimeFrame;
import net.englab.common.search.models.subtitles.SrtEntry;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SrtSubtitlesTest {

    @Test
    void test() {
        String text = """
                1
                00:00:00,000 --> 00:00:03,000
                The most common words that languages
                borrow from each other
                
                2
                00:00:03,000 --> 00:00:06,600
                are nouns, words for things, probably
                since it's fairly straightforward
                
                3
                00:00:06,600 --> 00:00:08,880
                to point at an object,
                say the word for it,
                
                4
                00:00:08,880 --> 00:00:11,760
                and have the other person understand
                that’s what you’re talking about.
                """;

        SrtSubtitles srtSubtitles = new SrtSubtitles(text);

        List<SrtEntry> expectedResult = List.of(
                new SrtEntry(1, new TimeFrame(0, 3),
                        List.of("The most common words that languages",
                                "borrow from each other")),
                new SrtEntry(2, new TimeFrame(3, 6.6),
                        List.of("are nouns, words for things, probably",
                                "since it's fairly straightforward")),
                new SrtEntry(3, new TimeFrame(6.6, 8.88),
                        List.of("to point at an object,",
                                "say the word for it,")),
                new SrtEntry(4, new TimeFrame(8.88, 11.76),
                        List.of("and have the other person understand",
                                "that’s what you’re talking about."))
        );

        srtSubtitles.forEach(srtBlock ->
                assertEquals(expectedResult.get(srtBlock.id() - 1), srtBlock)
        );
    }

}
