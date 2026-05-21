package highlighting.presets;

import static org.junit.jupiter.api.Assertions.*;

import java.util.regex.Matcher;
import org.junit.jupiter.api.Test;

class MiniJavaTokensTest {

  private Matcher matcher;
  private String text;

  // -------------------------
  // CHARACTER
  // -------------------------

  @Test
  void character_matcher_true() {
    // given
    text = "'a'";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(4).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void character_matcher_false() {
    // given
    text = "Hallo World";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(4).pattern().matcher(text);

    // then
    assertFalse(matcher.matches());
  }

  @Test
  void character_inside_text_find() {
    // given
    text = "abc 'a' def";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(4).pattern().matcher(text);

    // then
    assertTrue(matcher.find());
  }

  // -------------------------
  // STRING
  // -------------------------

  @Test
  void string_matcher_true() {
    // given
    text = "\"Hallo World\"";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(3).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void string_matcher_false() {
    // given
    text = "Hallo World";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(3).pattern().matcher(text);

    // then
    assertFalse(matcher.matches());
  }

  @Test
  void string_multiple_occurrences_find() {
    // given
    text = "\"a\" middle \"b\" end";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(3).pattern().matcher(text);

    // then
    assertTrue(matcher.find());
  }

  @Test
  void string_with_comment_like_content() {
    // given
    text = "\"text // not comment\"";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(3).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  // -------------------------
  // KEYWORD
  // -------------------------

  @Test
  void keyword_matcher_true() {
    // given
    text = "class";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(6).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void keyword_matcher_false() {
    // given
    text = "classification";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(6).pattern().matcher(text);

    // then
    assertFalse(matcher.matches());
  }

  @Test
  void keyword_in_middle_of_text_find() {
    // given
    text = "abc class def";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(6).pattern().matcher(text);

    // then
    assertTrue(matcher.find());
  }

  @Test
  void keyword_multiple_occurrences_find() {
    // given
    text = "public class Test return";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(6).pattern().matcher(text);

    // then
    assertTrue(matcher.find());
  }

  // -------------------------
  // ANNOTATION
  // -------------------------

  @Test
  void annotation_matcher_true() {
    // given
    text = "@Override";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(5).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void annotation_matcher_false() {
    // given
    text = "@123";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(5).pattern().matcher(text);

    // then
    assertFalse(matcher.matches());
  }

  @Test
  void annotation_with_leading_spaces_find() {
    // given
    text = "   @Override";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(5).pattern().matcher(text);

    // then
    assertTrue(matcher.find());
  }

  // -------------------------
  // LINE COMMENT
  // -------------------------

  @Test
  void line_comment_matcher_true() {
    // given
    text = "// Dies ist ein Kommentar";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(2).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void line_comment_matcher_false() {
    // given
    text = "/* Kein Zeilenkommentar */";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(2).pattern().matcher(text);

    // then
    assertFalse(matcher.matches());
  }

  @Test
  void line_comment_contains_keyword_like_text() {
    // given
    text = "// class public return";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(2).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void line_comment_in_middle_of_text_find() {
    // given
    text = "code // comment here code";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(2).pattern().matcher(text);

    // then
    assertTrue(matcher.find());
  }

  // -------------------------
  // BLOCK COMMENT
  // -------------------------

  @Test
  void block_comment_matcher_true_space() {
    // given
    text = "/**/";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(1).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void block_comment_matcher_true() {
    // given
    text = "/* text */";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(1).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void block_comment_contains_keyword_like_text() {
    // given
    text = "/* class public return */";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(1).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void block_comment_multiple_lines() {
    // given
    text = "/* first\n// second\n// third */";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(1).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  // -------------------------
  // JAVADOC
  // -------------------------

  @Test
  void javadoc_comment_matcher_true_space() {
    // given
    text = "/***/";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(0).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void javadoc_comment_matcher_true() {
    // given
    text = "/** text */";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(0).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void javadoc_comment_contains_text_find() {
    // given
    text = "code /** comment */ code";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(0).pattern().matcher(text);

    // then
    assertTrue(matcher.find());
  }

  @Test
  void javadoc_comment_multiple_lines() {
    // given
    text = "/** first\n// second\n// third */";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(0).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  // -------------------------
  // NUMBER
  // -------------------------

  @Test
  void number_matcher_true() {
    // given
    text = "12345";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(7).pattern().matcher(text);

    // then
    assertTrue(matcher.matches());
  }

  @Test
  void number_matcher_false() {
    // given
    text = "abc123";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(7).pattern().matcher(text);

    // then
    assertFalse(matcher.matches());
  }

  @Test
  void number_inside_text_find() {
    // given
    text = "abc 123 def";

    // when
    matcher = MiniJavaTokens.defaultTokens().get(7).pattern().matcher(text);

    // then
    assertTrue(matcher.find());
  }
}
