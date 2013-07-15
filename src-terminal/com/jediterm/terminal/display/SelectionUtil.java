package com.jediterm.terminal.display;

import com.jediterm.terminal.SelectionTextAppender;
import org.apache.log4j.Logger;

import java.awt.*;

/**
 * @author traff
 */
public class SelectionUtil {
  private static final Logger LOG = Logger.getLogger(SelectionUtil.class);

  public static String getSelectionText(final Point selectionStart,
                                        final Point selectionEnd,
                                        final BackBuffer backBuffer) {
    Point top;
    Point bottom;
    int terminalWidth = backBuffer.getWidth();

    if (selectionStart.y == selectionEnd.y) {                        /* same line */
      top = selectionStart.x < selectionEnd.x ? selectionStart
                                              : selectionEnd;
      bottom = selectionStart.x >= selectionEnd.x ? selectionStart
                                                  : selectionEnd;
    }
    else {
      top = selectionStart.y < selectionEnd.y ? selectionStart
                                              : selectionEnd;
      bottom = selectionStart.y > selectionEnd.y ? selectionStart
                                                 : selectionEnd;
    }

    final StringBuilder selectionText = new StringBuilder();

    if (top.y < 0) {  //add lines from scroll buffer
      final Point scrollEnd = bottom.y >= 0 ? new Point(terminalWidth, -1) : bottom;
      SelectionTextAppender scrollText = new SelectionTextAppender(top, scrollEnd);
      backBuffer.getScrollBuffer().processLines(top.y, scrollEnd.y - top.y + 1,
                                                scrollText);
      selectionText.append(scrollText.getText());
    }

    if (bottom.y >= 0) {
      final Point backBegin = top.y < 0 ? new Point(0, 0) : top;
      SelectionTextAppender selectionTextAppender = new SelectionTextAppender(backBegin, bottom);
      for (int y = backBegin.y; y <= bottom.y; y++) {
        if (backBuffer.checkTextBufferIsValid(y)) {
          backBuffer.processTextBufferLines(y, 1, selectionTextAppender, 0);
        }
        else {
          LOG.error("Text buffer has invalid content");
          backBuffer.processBufferRow(y, selectionTextAppender);
        }
      }

      if (selectionText.length() > 0) {
        selectionText.append("\n");
      }
      selectionText.append(selectionTextAppender.getText());
    }


    return selectionText.toString();
  }
}
