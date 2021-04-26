package edu.bsu.cs.view;

import com.google.inject.Inject;
import edu.bsu.cs.model.QueryEngine;
import edu.bsu.cs.model.QueryResponse;
import edu.bsu.cs.model.Revision;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

public final class WikipediaAnalyzer extends VBox {

    private final TextField titleField = new TextField();
    private final TextArea outputArea = new TextArea() {
        {
            setEditable(false);
        }
    };
    private final Button queryButton = new Button("Search");
    private final List<Control> inputControls = List.of(titleField, queryButton);

    // Suppress the warning that this field is not initialized.
    // Unfortunately, it is the same warning as if it were never used, which means
    // this is "over-suppressing," but this is preferable to keeping the warning around.
    @SuppressWarnings("unused")
    @Inject
    private QueryEngine engine;

    // See note above
    @SuppressWarnings("unused")
    @Inject
    private ExecutorService executor;

    @SuppressWarnings("unused")
    @Inject
    private RevisionFormatterInterface formatter;

    public WikipediaAnalyzer() {
        queryButton.setOnAction(e -> attemptQuery());
        titleField.setOnAction(e -> attemptQuery());

        getChildren().addAll(
                new HBox(new Label("Article Title: "),
                        titleField,
                        queryButton),
                outputArea);
    }

    private void attemptQuery() {
        String articleTitle = titleField.getText().trim();
        if (!articleTitle.isEmpty()) {
            outputArea.clear();
            inputControls.forEach(control -> control.setDisable(true));
            executor.execute(() -> runQuery(articleTitle));
        }
    }

    private void runQuery(String articleTitle) {
        try {
            QueryResponse response = engine.queryRevisions(articleTitle);
            StringBuilder stringBuilder = new StringBuilder();
            for (Revision revision : response.revisions()) {
                String message = formatter.format(revision);
                stringBuilder.append(message);
                stringBuilder.append("\n");
            }
            outputArea.setText(stringBuilder.toString());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Connection Problem");
            alert.setContentText("There was a problem connecting to Wikipedia. Check your network connection or try again later.");
        } finally {
            inputControls.forEach(control -> control.setDisable(false));
        }
    }

}
