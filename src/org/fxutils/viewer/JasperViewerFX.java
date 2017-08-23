/*
 * Copyright (C) 2017 Gustavo Fragoso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fxutils.viewer;



import Decoration.GNDecoration;
import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;

/**
 * Created by gleidson on 28/06/17.
 */
public class JasperViewerFX {

    private final Stage dialog = new Stage();
    GNDecoration decoration = new GNDecoration();
    private JFXButton print, save, back, firstPage, next, lastPage, zoomIn, zoomOut;
    private ImageView report;
    private TextField txtPage;
    private int reportPages;
    private int currentPage = 0;

    // JasperReports variables
    private JasperReport jreport;
    private JasperPrint jprint;

    // Zoom
    private int imageHeight = 0, imageWidth = 0;

    public JasperViewerFX(Stage owner, String title, String jasper, HashMap params, Connection con) {

        // Initializing window
//        decoration = new Stage();
//        decoration.initModality(Modality.WINDOW_MODAL);
//        decoration.initOwner(owner);
//        decoration.setScene(createScene());
//        decoration.setTitle(title);


//        try {
//            GNDecoration decoration = new GNDecoration();
//            decoration.initTheme(Theme.WHITE);
            decoration.setContent(createContent());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            URL arquivo = getClass().getResource(jasper);
            jreport = (JasperReport) JRLoader.loadObject(arquivo);
            jprint = JasperFillManager.fillReport(jreport, params, con);

            imageHeight = jprint.getPageHeight() + 284;
            imageWidth = jprint.getPageWidth() + 201;
            reportPages = jprint.getPages().size();

        } catch (JRException ex) {

        }
    }

    public JasperViewerFX(String title, String jasper, HashMap params, Connection con) {

        // Initializing window
//        decoration = new Stage();
//        decoration.initModality(Modality.WINDOW_MODAL);
//        decoration.initOwner(owner);
//        decoration.setScene(createScene());
//        decoration.setTitle(title);


//        try {
//            GNDecoration decoration = new GNDecoration();
//            decoration.initTheme(Theme.WHITE);
            decoration.setTitle(title);
            decoration.setContent(createContent());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            URL arquivo = getClass().getResource(jasper);
            jreport = (JasperReport) JRLoader.loadObject(arquivo);
            jprint = JasperFillManager.fillReport(jreport, params, con);

            imageHeight = jprint.getPageHeight() + 284;
            imageWidth = jprint.getPageWidth() + 201;
            reportPages = jprint.getPages().size();

        } catch (JRException ex) {

        }
    }

    public JasperViewerFX(Stage owner, String title, String jasper, HashMap params, JRBeanCollectionDataSource source) {


        // Initializing window
//        decoration = new Stage();
//        decoration.initModality(Modality.WINDOW_MODAL);
//        decoration.initOwner(owner);
//        decoration.setScene(createScene());
//        decoration.setTitle(title);


//        try {

//            decoration.initTheme(Theme.WHITE);
            decoration.setContent(createContent());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            URL arquivo = getClass().getResource(jasper);
            jreport = (JasperReport) JRLoader.loadObject(arquivo);
            jprint = JasperFillManager.fillReport(jreport, params, source);
            imageHeight = jprint.getPageHeight() + 284;
            imageWidth = jprint.getPageWidth() + 201;
            reportPages = jprint.getPages().size();

        } catch (JRException ex) {
        }
    }

    private Parent createContent() {
        HBox menu = new HBox(5);
        menu.setPrefHeight(60.0D);
        menu.setAlignment(Pos.CENTER);
        menu.setId("menu");
        
        ImageView b1 = new ImageView("org/fxutils/icons/printer.png");
        ImageView b2 = new ImageView("org/fxutils/icons/save.png");
        ImageView b3 = new ImageView("org/fxutils/icons/navigate-before.png");
        ImageView b4 = new ImageView("org/fxutils/icons/skip-previous.png");
        ImageView b5 = new ImageView("org/fxutils/icons/navigate-next.png");
        ImageView b6 = new ImageView("org/fxutils/icons/skip-next.png");
        ImageView b7 = new ImageView("org/fxutils/icons/zoom-in.png");
        ImageView b8 = new ImageView("org/fxutils/icons/zoom-out.png");


        print = new JFXButton(null, b1);
        save = new JFXButton(null, b2);
        back = new JFXButton(null, b3);
        firstPage = new JFXButton(null, b4);
        next = new JFXButton(null, b5);
        lastPage = new JFXButton(null, b6);
        zoomIn = new JFXButton(null, b7);
        zoomOut = new JFXButton(null, b8);
        txtPage = new TextField("1");
        txtPage.setAlignment(Pos.CENTER);
        txtPage.setPrefSize(75, 30);

        menu.getChildren().addAll(print, save, firstPage, back, txtPage, next, lastPage, zoomIn, zoomOut);

        // This imageview will preview the pdf inside scrollpane
        report = new ImageView();
        report.setFitHeight(imageHeight);
        report.setFitWidth(imageWidth);
        DropShadow shadow = new DropShadow(4, Color.GRAY);
        report.setEffect(shadow);

        
        Group contentGroup = new Group();
        contentGroup.getChildren().add(report);

        StackPane stack = new StackPane(contentGroup);
        stack.setPadding(new Insets(10));
        stack.setAlignment(Pos.CENTER);
        stack.setStyle("-fx-background-color: #cecece;");

        ScrollPane scroll = new ScrollPane(stack);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        BorderPane root = new BorderPane();

        root.setCenter(scroll);
        root.setTop(menu);

        root.setPrefSize(1024, 768);

        return root;
    }

    private void start() {
        viewPage(0);

        print.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                printAction();
            }
        });
        save.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                saveAction();
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                backAction();
            }
        });
        next.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                nextAction();
            }
        });
        firstPage.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                firstPageAction();
            }
        });
        lastPage.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                lastPageAction();
            }
        });
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                zoomInAction();
            }
        });
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                zoomOutAction();
            }
        });
        txtPage.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        int p = Integer.parseInt(txtPage.getText());
                        if (p > reportPages) {
                            txtPage.setText(Integer.toString(reportPages));
                            viewPage(reportPages - 1);
                        } else {
                            viewPage(p - 1);
                        }
                    } catch (Exception e) {
                        Alert dialog = new Alert(Alert.AlertType.WARNING, "Invalid number", ButtonType.OK);
                        dialog.show();
                    }

                }
            }
        });
    }

    private void backAction() {
        if (currentPage - 1 > -1) {
            currentPage--;
            txtPage.setText(Integer.toString(currentPage + 1));
            viewPage(currentPage);
        }
    }

    private void firstPageAction() {
        txtPage.setText("1");
        currentPage = 0;
        viewPage(0);
    }

    private void lastPageAction() {
        txtPage.setText(Integer.toString(reportPages));
        currentPage = reportPages - 1;
        viewPage(reportPages - 1);
    }

    private void nextAction() {
        if (currentPage + 1 < reportPages) {
            currentPage++;
            txtPage.setText(Integer.toString(currentPage + 1));
            viewPage(currentPage);
        }
    }

    private void printAction() {
        try {
            JasperPrintManager.printReport(jprint, true);
        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void saveAction() {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter pdf = new FileChooser.ExtensionFilter("Portable Document Format (*.pdf)", "*.pdf");
        FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("HyperText Markup Language", "*.html");
        FileChooser.ExtensionFilter xml = new FileChooser.ExtensionFilter("eXtensible Markup Language", "*.xml");
        FileChooser.ExtensionFilter xls = new FileChooser.ExtensionFilter("Microsoft Excel 2007", "*.xls");
        FileChooser.ExtensionFilter xlsx = new FileChooser.ExtensionFilter("Microsoft Excel 2016", "*.xlsx");
        FileChooser.ExtensionFilter csv = new FileChooser.ExtensionFilter("Comma-separeted Values", "*.csv");
        chooser.getExtensionFilters().addAll(pdf, html, xml, xls, xlsx, csv);

        chooser.setTitle("Salvar");
        chooser.setSelectedExtensionFilter(pdf);
        File file = chooser.showSaveDialog(dialog);

        if (file != null) {
            List<String> box = chooser.getSelectedExtensionFilter().getExtensions();
            switch (box.get(0)) {
                case "*.pdf":
                    try {
                        JasperExportManager.exportReportToPdfFile(jprint, file.getPath());
                    } catch (JRException ex) {
                    }
                    break;
                case "*.html":
                    try {
                        JasperExportManager.exportReportToHtmlFile(jprint, file.getPath());
                    } catch (JRException ex) {
                    }
                    break;
                case "*.xml":
                    try {
                        JasperExportManager.exportReportToXmlFile(jprint, file.getPath(), false);
                    } catch (JRException ex) {
                    }
                    break;
                case "*.xls":
                    try {
                        JRXlsExporter exporter = new JRXlsExporter();
                        exporter.setExporterInput(new SimpleExporterInput(jprint));
                        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                        exporter.exportReport();
                    } catch (JRException ex) {
                    }
                    break;
                case "*.xlsx":
                    try {
                        JRXlsxExporter exporter = new JRXlsxExporter();
                        exporter.setExporterInput(new SimpleExporterInput(jprint));
                        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                        exporter.exportReport();
                    } catch (JRException ex) {
                    }
                    break;
                case "*.csv":
                    try {
                        JRCsvExporter exporter = new JRCsvExporter();
                        exporter.setExporterInput(new SimpleExporterInput(jprint));
                        exporter.setExporterOutput(new SimpleWriterExporterOutput(file));
                        exporter.exportReport();
                    } catch (JRException ex) {
                    }
                    break;
            }
        }
    }

    public void show() {
        if (reportPages > 0) {
            start();
            decoration.getScene().getStylesheets().add(getClass().getResource("JasperView.css").toExternalForm());
            decoration.show();
        } else {
            Alert aviso = new Alert(Alert.AlertType.INFORMATION, "We found 0 entries for this report", ButtonType.CLOSE);
            aviso.setHeaderText("Sorry");
            aviso.show();
        }
    }

    private void viewPage(int page) {
        try {
            float zoom = (float) 1.33;
            BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jprint, page, zoom);
            WritableImage fxImage = new WritableImage(imageHeight, imageWidth);
            report.setImage(SwingFXUtils.toFXImage(image, fxImage));
        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void zoomInAction() {
        report.setScaleX(report.getScaleX() + 0.15);
        report.setScaleY(report.getScaleY() + 0.15);
        report.setFitHeight(imageHeight + 0.15);
        report.setFitWidth(imageWidth + 0.15);
    }

    private void zoomOutAction() {
        report.setScaleX(report.getScaleX() - 0.15);
        report.setScaleY(report.getScaleY() - 0.15);
        report.setFitHeight(imageHeight - 0.15);
        report.setFitWidth(imageWidth - 0.15);
    }

}
