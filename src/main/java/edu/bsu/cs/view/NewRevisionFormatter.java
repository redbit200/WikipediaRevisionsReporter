package edu.bsu.cs.view;

import edu.bsu.cs.model.Revision;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class NewRevisionFormatter implements RevisionFormatterInterface {

    public String format(Revision revision) {
        return String.format("A change was made on %s by %s",
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault()).format(revision.timestamp),
                revision.name);
    }

}
