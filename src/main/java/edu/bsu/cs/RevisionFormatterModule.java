package edu.bsu.cs;

import com.google.inject.AbstractModule;
import edu.bsu.cs.view.NewRevisionFormatter;
import edu.bsu.cs.view.RevisionFormatterInterface;

public class RevisionFormatterModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RevisionFormatterInterface.class).to(NewRevisionFormatter.class);
    }

}
