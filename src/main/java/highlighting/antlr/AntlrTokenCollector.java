package highlighting.antlr;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaColours;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.*;

public class AntlrTokenCollector extends SyntaxHighlighter {

  @Override
  public List<HighlightRegion> collectMatches(String text) {

    MiniJavaLexer lexer = new MiniJavaLexer(CharStreams.fromString(text));

    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    tokenStream.fill();

    List<Token> tokens = tokenStream.getTokens();
    List<HighlightRegion> highlightRegions = new ArrayList<>();

    for (Token token : tokens) {

      if (token.getType() == Token.EOF) {
        continue;
      }

      Color color = colorFor(token);

      if (color != null) {
        highlightRegions.add(
            new HighlightRegion(token.getStartIndex(), token.getStopIndex() + 1, color));
      }

      if (token.getType() == MiniJavaLexer.AT) {
        if (token.getTokenIndex() + 1 < tokens.size()) {
          Token next = tokens.get(token.getTokenIndex() + 1);
          if (next.getType() == MiniJavaLexer.IDENTIFIER) {
            highlightRegions.removeLast();
            highlightRegions.add(
                new HighlightRegion(
                    token.getStartIndex(),
                    next.getStopIndex() + 1,
                    MiniJavaColours.ANNOTATION_COLOUR));
          }
        }
      }
    }

    return highlightRegions;
  }

  private static Color colorFor(Token token) {
    return switch (token.getType()) {
      case MiniJavaLexer.STRING_LITERAL -> MiniJavaColours.STRING_LITERAL_COLOUR;
      case MiniJavaLexer.CHAR_LITERAL -> MiniJavaColours.CHAR_LITERAL_COLOUR;
      case MiniJavaLexer.LINE_COMMENT -> MiniJavaColours.LINE_COMMENT_COLOUR;
      case MiniJavaLexer.JAVADOC_COMMENT -> MiniJavaColours.JAVADOC_COMMENT_COLOUR;
      case MiniJavaLexer.BLOCK_COMMENT -> MiniJavaColours.BLOCK_COMMENT_COLOUR;
      case MiniJavaLexer.AT -> MiniJavaColours.ANNOTATION_COLOUR;
      case MiniJavaLexer.PACKAGE,
          MiniJavaLexer.IMPORT,
          MiniJavaLexer.CLASS,
          MiniJavaLexer.PUBLIC,
          MiniJavaLexer.PRIVATE,
          MiniJavaLexer.FINAL,
          MiniJavaLexer.RETURN,
          MiniJavaLexer.NULL,
          MiniJavaLexer.NEW,
          MiniJavaLexer.IF,
          MiniJavaLexer.ELSE,
          MiniJavaLexer.WHILE,
          MiniJavaLexer.EXTENDS,
          MiniJavaLexer.IMPLEMENTS ->
          MiniJavaColours.KEYWORD_COLOUR;
      default -> null;
    };
  }
}
