
package org.Teste;

import Genesis.Controls.Decoration.Control.GNDecoration;
import Genesis.Controls.Decoration.Control.Theme;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.fxutils.viewer.JasperViewerFX;

/**
 *
 * @author          Gleidson Neves da Silveira <br>
 * Email            gleidisonmt@gmail.com <br> 
 * Date             16/08/2017 <br>
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        GNDecoration decoration = new GNDecoration();
        VBox root = new VBox();
        decoration.setBody(root);
        root.setAlignment(Pos.CENTER);
        Button btn = new Button("open report");
        root.getChildren().add(btn);
        DataConnection connection = new DataConnection();
        HashMap has = new HashMap();
        has.put("Nome", "Goku");
        System.out.println(connection.initConnection());
        btn.setOnMouseClicked((e) -> {
            JasperViewerFX jvfx = new JasperViewerFX("Clientes", "lista_clientes.jasper", has, connection.getConnection(), Color.web("white"));
            jvfx.show();
            connection.closeConnection();
            primaryStage.close();
        });

        decoration.initTheme(Theme.LIGHT);
        decoration.setColor(Color.WHITE);
        decoration.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
