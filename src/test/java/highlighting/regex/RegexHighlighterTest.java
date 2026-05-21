package highlighting.regex;

import static org.junit.jupiter.api.Assertions.*;

import highlighting.core.HighlightRegion;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegexHighlighterTest {

  private RegexHighlighter regexHighlighter;

  @BeforeEach
  void setup() {
    regexHighlighter = new RegexHighlighter();
  }

  private List<HighlightRegion> compute(String text) {
    return regexHighlighter.computeRegions(text);
  }

  @Test
  void collect_matches_finds_matches() {
    // given
    String text = "public class Test { private String name = \"hello\"; }";

    // when
    var collected = regexHighlighter.collectMatches(text);

    // then
    assertFalse(collected.isEmpty());
  }

  @Test
  void collect_matches_finds_matches_public() {
    // given
    String text = "public class Test { private String name = \"hello\"; }";

    // when
    var collected = regexHighlighter.collectMatches(text);

    // then
    assertTrue(collected.stream().anyMatch(r -> r.start() == 0 && r.end() == 6));
  }

  @Test
  void collect_matches_finds_matches_class() {
    // given
    String text = "public class Test { private String name = \"hello\"; }";

    // when
    var collected = regexHighlighter.collectMatches(text);

    // then
    assertTrue(collected.stream().anyMatch(r -> r.start() == 7 && r.end() == 12));
  }

  @Test
  void collect_matches_finds_matches_hallo() {
    // given
    String text = "public class Test { private String name = \"hello\"; }";

    // when
    var collected = regexHighlighter.collectMatches(text);

    // then
    assertTrue(
        collected.stream().anyMatch(r -> text.substring(r.start(), r.end()).equals("\"hello\"")));
  }

  @Test
  void resolve_conflicts_non_overlapping() {
    // given
    String text = "\"hello\"}";

    // when
    var result = compute(text);

    // then
    assertEquals(1, result.size());
  }

  @Test
  void resolve_conflicts_overlapping() {
    // given
    String text = "//\"hello\" Welt}";

    // when
    var result = compute(text);

    // then
    assertEquals(1, result.size());
  }

  @Test
  void resolve_conflicts_overlapping_keywords() {
    // given
    String text = "//class \"hello\" Welt}";

    // when
    var result = compute(text);

    // then
    assertEquals(1, result.size());
  }

  @Test
  void resolve_conflicts_overlapping_javadoc() {
    // given
    String text =
        """
        /**
         * "ApplicationListener" that delegates to the "MainGameController".
         Just some setup. */;\
        """;

    // when
    var result = compute(text);

    // then
    assertEquals(1, result.size());
  }

  @Test
  void resolve_conflicts_accepts_touching_regions() {
    // given
    String text = "class// Hallo World";

    // when
    var result = compute(text);

    // then
    assertEquals(2, result.size());
  }

  @Test
  void resolve_conflicts_empty_string() {
    // given
    String text = "";

    // when
    var result = compute(text);

    // then
    assertEquals(0, result.size());
  }

  @Test
  void resolve_conflicts_handles_multiple_separate_comments() {
    // given
    String text = "// first\nclass\n// second";

    // when
    var result = compute(text);

    // then
    assertEquals(3, result.size());
  }

  @Test
  void resolve_conflicts_handles_only_overlapping_regions() {
    // given
    String text = "/* public class Test */";

    // when
    var result = compute(text);

    // then
    assertEquals(1, result.size());

    assertEquals(
        "/* public class Test */",
        text.substring(result.getFirst().start(), result.getFirst().end()));
  }
}
