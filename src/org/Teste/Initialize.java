/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.Teste;

import java.util.HashMap;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxutils.viewer.JasperViewerFX;

/**
 *
 * @author          Gleidson Neves da Silveira <br>
 * Email            gleidisonmt@gmail.com <br> 
 * Date             16/08/2017 <br>
 */
public class Initialize extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Button btn = new Button("open report");
        root.getChildren().add(btn);
        Global.Conexoes.DataConnection connection = new Global.Conexoes.DataConnection();
        HashMap has = new HashMap();
        has.put("Nome", "Joao1");
        connection.initConnection();
        btn.setOnMouseClicked((e) -> {
            JasperViewerFX jvfx = new JasperViewerFX(primaryStage, "Clientes", "clientes.jasper", has, connection.getConnection());
            jvfx.show();
            connection.closeConnection();
        });
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
       
    }

    public static void main(String[] args) {
        launch(args);
    }
}
