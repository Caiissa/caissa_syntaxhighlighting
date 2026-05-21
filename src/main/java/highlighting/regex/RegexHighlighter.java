package highlighting.regex;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaTokens;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

// TODO: Implement a simple regex-based highlighting strategy. Unlike the scanning approach, this
// strategy applies each token independently to the entire input text and collects all resulting
// {@code HighlightRegion}s, even if they overlap. Conflicts are resolved in a separate step.

// TODO: Make this class extend {@code SyntaxHighlighter}, implement the abstract method {@code
// collectMatches}, and override {@code resolveConflicts} to handle overlapping regions produced by
// the naive regex-based strategy.
public class RegexHighlighter extends SyntaxHighlighter {

  // TODO: For each token, find all matches of its pattern in the input text, convert them into
  // {@code HighlightRegion}s, and combine all of these regions into a single list.
  @Override
  public List<HighlightRegion> collectMatches(String text) {
    List<HighlightRegion> highlight = new ArrayList<>();

    for (int i = 0; i < MiniJavaTokens.defaultTokens().size(); i++) {
      Matcher matcher = MiniJavaTokens.defaultTokens().get(i).pattern().matcher(text);
      Color colour = MiniJavaTokens.defaultTokens().get(i).colour();

      while (matcher.find()) {
        highlight.add(new HighlightRegion(matcher.start(), matcher.end(), colour));
      }
    }
    return highlight;
  }

  // TODO: Resolve overlapping regions. Assume that {@code regions} has been normalised and sorted.
  // For any overlapping regions, keep the one that appears first in this list (which reflects the
  // token order) and discard all later overlapping regions. Longer regions that start at the same
  // position are preferred because of the sorting in {@code normalize}.
  @Override
  public List<HighlightRegion> resolveConflicts(List<HighlightRegion> regions) {
    List<HighlightRegion> highlight = new ArrayList<>();

    if (regions.isEmpty()) {
      return highlight;
    }

    highlight.add(
        new HighlightRegion(
            regions.getFirst().start(), regions.getFirst().end(), regions.getFirst().colour()));

    for (int i = 1; i < regions.size(); i++) {
      HighlightRegion current = regions.get(i);
      HighlightRegion lastAccepted = highlight.getLast();

      if (lastAccepted.end() > current.start()) {
        continue;
      }

      highlight.add(
          new HighlightRegion(
              regions.get(i).start(), regions.get(i).end(), regions.get(i).colour()));
    }
    return highlight;
  }
}
