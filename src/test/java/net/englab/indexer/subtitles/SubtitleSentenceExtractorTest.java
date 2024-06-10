package net.englab.indexer.subtitles;

import net.englab.common.search.models.subtitles.SubtitleSentence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SubtitleSentenceExtractorTest {
    private final SubtitleSentenceExtractor sentenceExtractor = new SubtitleSentenceExtractor();

    @Test
    void testLongSentence() {
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

        List<SubtitleSentence> sentences = sentenceExtractor.extract(new SrtSubtitles(text));

        assertEquals(1, sentences.size());
        SubtitleSentence sentence = sentences.get(0);
        String expectedText = "The most common words that languages borrow from each other are nouns, words for things, " +
                "probably since it's fairly straightforward to point at an object, say the word for it, and have the other " +
                "person understand that’s what you’re talking about.";
        assertEquals(expectedText, sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(59));
        assertEquals(1, sentence.rangeMap().get(60));
        assertEquals(1, sentence.rangeMap().get(131));
        assertEquals(2, sentence.rangeMap().get(132));
        assertEquals(2, sentence.rangeMap().get(175));
        assertEquals(3, sentence.rangeMap().get(176));
        assertEquals(3, sentence.rangeMap().get(245));
        assertNull(sentence.rangeMap().get(246));
    }

    @Test
    void testShortSentences() {
        String text = """
                1
                00:00:00,000 --> 00:00:03,000
                Hello world! How are you? I'm fine.
                This is a test
                                
                2
                00:00:03,000 --> 00:00:06,600
                that will catch bugs!
                """;

        List<SubtitleSentence> sentences = sentenceExtractor.extract(new SrtSubtitles(text));

        assertEquals(4, sentences.size());

        SubtitleSentence sentence = sentences.get(0);
        assertEquals("Hello world!", sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(11));
        assertNull(sentence.rangeMap().get(12));

        sentence = sentences.get(1);
        assertEquals("How are you?", sentence.text());
        assertEquals(13, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(11));
        assertNull(sentence.rangeMap().get(12));

        sentence = sentences.get(2);
        assertEquals("I'm fine.", sentence.text());
        assertEquals(26, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(8));
        assertNull(sentence.rangeMap().get(9));

        sentence = sentences.get(3);
        assertEquals("This is a test that will catch bugs!", sentence.text());
        assertEquals(36, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(14));
        assertEquals(1, sentence.rangeMap().get(15));
        assertEquals(1, sentence.rangeMap().get(35));
        assertNull(sentence.rangeMap().get(36));
    }

    @Test
    void testShortSentences2() {
        String text = """
                1
                00:01:06,960 --> 00:01:10,860
                The Arabic word /sˤaħ raːʔ/ for "deserts"
                became the English word
                                
                2
                00:01:10,860 --> 00:01:14,460
                specifically for the Sahara Desert.
                So the Desert Desert.
                                
                3
                00:01:14,460 --> 00:01:17,520
                Which keeps happening!
                Matcha tea is "powdered tea tea".
                """;

        List<SubtitleSentence> sentences = sentenceExtractor.extract(new SrtSubtitles(text));

        assertEquals(4, sentences.size());

        SubtitleSentence sentence = sentences.get(0);
        String expectedText = "The Arabic word /sˤaħ raːʔ/ for \"deserts\" became " +
                "the English word specifically for the Sahara Desert.";
        assertEquals(expectedText, sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(65));
        assertEquals(1, sentence.rangeMap().get(66));
        assertEquals(1, sentence.rangeMap().get(100));
        assertNull(sentence.rangeMap().get(101));

        sentence = sentences.get(1);
        assertEquals(36, sentence.position());
        assertEquals("So the Desert Desert.", sentence.text());
        assertEquals(1, sentence.rangeMap().get(0));
        assertEquals(1, sentence.rangeMap().get(20));
        assertNull(sentence.rangeMap().get(21));

        sentence = sentences.get(2);
        assertEquals("Which keeps happening!", sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(2, sentence.rangeMap().get(0));
        assertEquals(2, sentence.rangeMap().get(21));
        assertNull(sentence.rangeMap().get(22));

        sentence = sentences.get(3);
        assertEquals("Matcha tea is \"powdered tea tea\".", sentence.text());
        assertEquals(23, sentence.position());
        assertEquals(2, sentence.rangeMap().get(0));
        assertEquals(2, sentence.rangeMap().get(32));
        assertNull(sentence.rangeMap().get(33));
    }

    @Test
    void testSpecialChars() {
        String text = """
                1
                00:49:36,920 --> 00:49:37,960
                "Oooh! And me!"
                                
                2
                00:49:39,200 --> 00:49:41,700
                "What are you doing there?"
                                
                3
                00:49:42,700 --> 00:49:45,520
                ...this wasn’t really the Doctor Who I personally loved.
                                
                4
                00:49:45,520 --> 00:49:48,750
                It was an obvious success,
                and I appreciated that a new generation were
                
                5
                00:49:48,750 --> 00:49:52,660
                experiencing the Doctor’s adventures, but
                I was unsure whether it was really for me.
                """;

        List<SubtitleSentence> sentences = sentenceExtractor.extract(new SrtSubtitles(text));

        assertEquals(5, sentences.size());

        SubtitleSentence sentence = sentences.get(0);
        assertEquals("\"Oooh!", sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(5));
        assertNull(sentence.rangeMap().get(6));

        sentence = sentences.get(1);
        assertEquals("And me!\"", sentence.text());
        assertEquals(7, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(7));
        assertNull(sentence.rangeMap().get(8));

        sentence = sentences.get(2);
        assertEquals("\"What are you doing there?\"", sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(1, sentence.rangeMap().get(0));
        assertEquals(1, sentence.rangeMap().get(26));
        assertNull(sentence.rangeMap().get(27));

        sentence = sentences.get(3);
        assertEquals("...this wasn’t really the Doctor Who I personally loved.", sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(2, sentence.rangeMap().get(0));
        assertEquals(2, sentence.rangeMap().get(55));
        assertNull(sentence.rangeMap().get(56));

        sentence = sentences.get(4);
        String expectedText = "It was an obvious success, and I appreciated that a new generation were experiencing " +
                "the Doctor’s adventures, but I was unsure whether it was really for me.";
        assertEquals(expectedText, sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(3, sentence.rangeMap().get(0));
        assertEquals(3, sentence.rangeMap().get(71));
        assertEquals(4, sentence.rangeMap().get(72));
        assertEquals(4, sentence.rangeMap().get(155));
        assertNull(sentence.rangeMap().get(156));
    }

    @Test
    void testEmptyEntry() {
        String text = """
                1
                01:13:09,723 --> 01:13:12,138
                when Doctor Who returns in 2010..."
                                
                2
                01:13:12,138 --> 01:13:12,819
                                
                                
                3
                01:13:12,819 --> 01:13:13,912
                "...it won't be with me."
                """;

        List<SubtitleSentence> sentences = sentenceExtractor.extract(new SrtSubtitles(text));

        assertEquals(1, sentences.size());
        SubtitleSentence sentence = sentences.get(0);
        String expectedText = "when Doctor Who returns in 2010...\" \"...it won't be with me.\"";
        assertEquals(expectedText, sentence.text());
        assertEquals(0, sentence.position());
        assertEquals(0, sentence.rangeMap().get(0));
        assertEquals(0, sentence.rangeMap().get(35));
        assertEquals(2, sentence.rangeMap().get(36));
        assertEquals(2, sentence.rangeMap().get(60));
        assertNull(sentence.rangeMap().get(61));
    }
}
