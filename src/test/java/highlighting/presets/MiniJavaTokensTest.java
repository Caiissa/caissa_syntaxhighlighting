package highlighting.presets;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import org.junit.jupiter.api.Test;

class MiniJavaTokensTest {

  // -------------------------------------------------------------------------
  // used BDD: "Given - When - Then"-Mantra
  // -------------------------------------------------------------------------

  private Matcher matcher;
  private String text;

  // -------------------------
  // CHARACTER
  // -------------------------

  @Test
  void character_matcher_true() {
    text = "'a'";
    matcher = MiniJavaTokens.defaultTokens().get(4).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void character_matcher_false() {
    text = "Hallo World";
    matcher = MiniJavaTokens.defaultTokens().get(4).pattern().matcher(text);
    assertFalse(matcher.matches());
  }

  @Test
  void character_inside_text_find() {
    text = "abc 'a' def";
    matcher = MiniJavaTokens.defaultTokens().get(4).pattern().matcher(text);
    assertTrue(matcher.find());
  }

  // -------------------------
  // STRING
  // -------------------------

  @Test
  void string_matcher_true() {
    text = "\"Hallo World\"";
    matcher = MiniJavaTokens.defaultTokens().get(3).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void string_matcher_false() {
    text = "Hallo World";
    matcher = MiniJavaTokens.defaultTokens().get(3).pattern().matcher(text);
    assertFalse(matcher.matches());
  }

  @Test
  void string_multiple_occurrences_find() {
    text = "\"a\" middle \"b\" end";
    matcher = MiniJavaTokens.defaultTokens().get(3).pattern().matcher(text);
    assertTrue(matcher.find());
  }

  @Test
  void string_with_comment_like_content() {
    text = "\"text // not comment\"";
    matcher = MiniJavaTokens.defaultTokens().get(3).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  // -------------------------
  // KEYWORD
  // -------------------------

  @Test
  void keyword_matcher_true() {
    text = "class";
    matcher = MiniJavaTokens.defaultTokens().get(6).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void keyword_matcher_false() {
    text = "classification";
    matcher = MiniJavaTokens.defaultTokens().get(6).pattern().matcher(text);
    assertFalse(matcher.matches());
  }

  @Test
  void keyword_in_middle_of_text_find() {
    text = "abc class def";
    matcher = MiniJavaTokens.defaultTokens().get(6).pattern().matcher(text);
    assertTrue(matcher.find());
  }

  @Test
  void keyword_multiple_occurrences_find() {
    text = "public class Test return";
    matcher = MiniJavaTokens.defaultTokens().get(6).pattern().matcher(text);
    assertTrue(matcher.find());
  }

  // -------------------------
  // ANNOTATION
  // -------------------------

  @Test
  void annotation_matcher_true() {
    text = "@Override";
    matcher = MiniJavaTokens.defaultTokens().get(5).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void annotation_matcher_false() {
    text = "@123";
    matcher = MiniJavaTokens.defaultTokens().get(5).pattern().matcher(text);
    assertFalse(matcher.matches());
  }

  @Test
  void annotation_with_leading_spaces_find() {
    text = "   @Override";
    matcher = MiniJavaTokens.defaultTokens().get(5).pattern().matcher(text);
    assertTrue(matcher.find());
  }

  // -------------------------
  // LINE COMMENT
  // -------------------------

  @Test
  void line_comment_matcher_true() {
    text = "// Dies ist ein Kommentar";
    matcher = MiniJavaTokens.defaultTokens().get(2).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void line_comment_matcher_false() {
    text = "/* Kein Zeilenkommentar */";
    matcher = MiniJavaTokens.defaultTokens().get(2).pattern().matcher(text);
    assertFalse(matcher.matches());
  }

  @Test
  void line_comment_contains_keyword_like_text() {
    text = "// class public return";
    matcher = MiniJavaTokens.defaultTokens().get(2).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void line_comment_in_middle_of_text_find() {
    text = "code // comment here code";
    matcher = MiniJavaTokens.defaultTokens().get(2).pattern().matcher(text);
    assertTrue(matcher.find());
  }

  // -------------------------
  // BLOCK COMMENT
  // -------------------------

  @Test
  void block_comment_matcher_true_space() {
    text = "/**/";
    matcher = MiniJavaTokens.defaultTokens().get(1).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void block_comment_matcher_true() {
    text = "/* text */";
    matcher = MiniJavaTokens.defaultTokens().get(1).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void block_comment_contains_keyword_like_text() {
    text = "/* class public return */";
    matcher = MiniJavaTokens.defaultTokens().get(1).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void block_comment_multiple_lines() {
    text = "/* first\n// second\n// third */";
    matcher = MiniJavaTokens.defaultTokens().get(1).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  // -------------------------
  // JAVADOC
  // -------------------------

  @Test
  void javadoc_comment_matcher_true_space() {
    text = "/***/";
    matcher = MiniJavaTokens.defaultTokens().get(0).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void javadoc_comment_matcher_true() {
    text = "/** text */";
    matcher = MiniJavaTokens.defaultTokens().get(0).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void javadoc_comment_contains_text_find() {
    text = "code /** comment */ code";
    matcher = MiniJavaTokens.defaultTokens().get(0).pattern().matcher(text);
    assertTrue(matcher.find());
  }

  @Test
  void javadoc_comment_multiple_lines() {
    text = "/** first\n// second\n// third */";
    matcher = MiniJavaTokens.defaultTokens().get(0).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  // -------------------------
  // NUMBER
  // -------------------------

  @Test
  void number_matcher_true() {
    text = "12345";
    matcher = MiniJavaTokens.defaultTokens().get(7).pattern().matcher(text);
    assertTrue(matcher.matches());
  }

  @Test
  void number_matcher_false() {
    text = "abc123";
    matcher = MiniJavaTokens.defaultTokens().get(7).pattern().matcher(text);
    assertFalse(matcher.matches());
  }

  @Test
  void number_inside_text_find() {
    text = "abc 123 def";
    matcher = MiniJavaTokens.defaultTokens().get(7).pattern().matcher(text);
    assertTrue(matcher.find());
  }
}
