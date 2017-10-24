/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fxutils.viewer;

import java.sql.SQLException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.Teste.DataConnection;

/**
 *
 * @author Gleidson
 */
public class PrintNode  extends Application{
//    / Create the TextArea
private final Label jobStatus = new Label();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        // Create the Text Label
        Label textLabel = new Label("Please insert your Text:");

        // Create the TextArea
        final TextArea textArea = new TextArea();
        
        DataConnection connection = new DataConnection();
        
        connection.initConnection();
        connection.executeSQL("select * from cliente");
        ObservableList<Cliente> list = FXCollections.observableArrayList();
    try {
        while (connection.getResult().next()){
            Cliente cli = new Cliente();
            cli.setCodigo(connection.getResult().getInt("id"));
            cli.setNome(connection.getResult().getString("nome_completo"));
            list.add(cli);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PrintNode.class.getName()).log(Level.SEVERE, null, ex);
    }
        TableView<Cliente> table = new TableView<>();
        TableColumn<Cliente, String> nome = new TableColumn<>();
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        table.getColumns().addAll(nome);

        table.setItems(list);
        // Create the Buttons
        Button pageSetupButton = new Button("Page Setup and Print");

        // Create the Event-Handlers for the Button
        pageSetupButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                pageSetup(table, stage);
            }
        });

        // Create the Status Box
        HBox jobStatusBox = new HBox(5, new Label("Job Status: "), jobStatus);
        // Create the Button Box
        HBox buttonBox = new HBox(pageSetupButton);

        // Create the VBox
        VBox root = new VBox(5);

        // Add the Children to the VBox		
        root.getChildren().addAll(textLabel, table, buttonBox, jobStatusBox);
        // Set the Size of the VBox
        root.setPrefSize(400, 300);

        // Set the Style-properties of the VBox
        root.setStyle("-fx-padding: 10;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the scene to the Stage
        stage.setScene(scene);
        // Set the title of the Stage
        stage.setTitle("A Printing Dialog Example");
        // Display the Stage
        stage.show();
    }

    private void pageSetup(Node node, Stage owner) {
        // Create the PrinterJob
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job == null) {
            return;
        }

        // Show the page setup dialog
        boolean proceed = job.showPageSetupDialog(owner);

        if (proceed) {
            print(job, node);
        }
    }

    private void print(PrinterJob job, Node node) {
        // Set the Job Status Message
        jobStatus.textProperty().bind(job.jobStatusProperty().asString());

        // Print the node
        boolean printed = job.printPage(node);

        if (printed) {
            job.endJob();
        }
    }
}

