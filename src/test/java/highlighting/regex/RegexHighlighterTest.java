package highlighting.regex;

import static org.junit.jupiter.api.Assertions.*;

import highlighting.core.HighlightRegion;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegexHighlighterTest {

  private RegexHighlighter regexHighlighter;

  // -------------------------------------------------------------------------
  // used BDD: "Given - When - Then"-Mantra
  // -------------------------------------------------------------------------

  @BeforeEach
  void setup() {
    regexHighlighter = new RegexHighlighter();
  }

  private List<HighlightRegion> compute(String text) {
    return regexHighlighter.computeRegions(text);
  }

  // -------------------------------------------------------------------------
  // collectMatches
  // -------------------------------------------------------------------------

  @Test
  void collect_matches_finds_matches() {
    String text = "public class Test { private String name = \"hello\"; }";
    var collected = regexHighlighter.collectMatches(text);
    assertFalse(collected.isEmpty());
  }

  @Test
  void collect_matches_finds_matches_public() {
    String text = "public class Test { private String name = \"hello\"; }";
    var collected = regexHighlighter.collectMatches(text);
    assertTrue(collected.stream().anyMatch(r -> r.start() == 0 && r.end() == 6));
  }

  @Test
  void collect_matches_finds_matches_class() {
    String text = "public class Test { private String name = \"hello\"; }";
    var collected = regexHighlighter.collectMatches(text);
    assertTrue(collected.stream().anyMatch(r -> r.start() == 7 && r.end() == 12));
  }

  @Test
  void collect_matches_finds_matches_hallo() {
    String text = "public class Test { private String name = \"hello\"; }";
    var collected = regexHighlighter.collectMatches(text);
    assertTrue(
        collected.stream().anyMatch(r -> text.substring(r.start(), r.end()).equals("\"hello\"")));
  }

  // -------------------------------------------------------------------------
  // resolveConflicts
  // -------------------------------------------------------------------------

  @Test
  void resolve_conflicts_non_overlapping() {
    String text = "\"hello\"}";
    var result = compute(text);
    assertEquals(1, result.size());
  }

  @Test
  void resolve_conflicts_overlapping() {
    String text = "//\"hello\" Welt}";
    var result = compute(text);
    assertEquals(1, result.size());
  }

  @Test
  void resolve_conflicts_overlapping_keywords() {
    String text = "//class \"hello\" Welt}";
    var result = compute(text);
    assertEquals(1, result.size());
  }

  @Test
  void resolve_conflicts_overlapping_javadoc() {
    String text =
        """
        /**
         * "ApplicationListener" that delegates to the "MainGameController".
         Just some setup. */;\
        """;
    var result = compute(text);
    assertEquals(1, result.size());
  }

  @Test
  void resolve_conflicts_accepts_touching_regions() {
    String text = "class// Hallo World";
    var result = compute(text);
    assertEquals(2, result.size());
  }

  @Test
  void resolve_conflicts_empty_string() {
    String text = "";
    var result = compute(text);
    assertEquals(0, result.size());
  }

  @Test
  void resolve_conflicts_handles_multiple_separate_comments() {
    String text = "// first\nclass\n// second";
    var result = compute(text);
    assertEquals(3, result.size());
  }

  @Test
  void resolve_conflicts_handles_only_overlapping_regions() {
    String text = "/* public class Test */";
    var result = compute(text);
    assertEquals(1, result.size());
    assertEquals(text, text.substring(result.getFirst().start(), result.getFirst().end()));
  }
}
