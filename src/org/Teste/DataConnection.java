/*
 *
 * Projeto desenvolvido por Gleidson Neves da Silveira - Faculdade Nossa Senhora de Aparecida.
 * O uso deste projeto se destina aos participantes do grupo ao qual participa Gleidson Neves da Silveira.
 *
 *      http://www.fanap.br
 *
 */

package org.Teste;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * @author Gleidson Neves da Silveira
 * Email gleidisonmt@gmail.com | gleidisonmt@outlook.com
 * Created on : 23/12/2016, 20:13:29
 */
public class DataConnection {
    
    /*******************************************************
     *
     * Variáveis utilizadas para acesso ao banco
     *
     ******************************************************/
     
    private Connection connection;     // variável responsavel pela conexão com o banco
    private Statement  statement;      // responsavel por realizar a pesquisa no BD
    private ResultSet  result;       // armazena o resultado da pesquisa ** do Statement

    /******************************************************
     *
     * Configurações do banco
     *
     ******************************************************/
    private final String driver     = "com.mysql.jdbc.Driver";                           // informa o caminho do driver utilizado
    private final String user       = "root";                                           // nome do usuario no banco
    private final String password   = "";                                              // senha do usuario no banco
    private final int    port       = 3306;                                           // porta do banco                    
    private final String host       = "localhost:" + port;                           // host do banco
    private final String database   = "salao";                                      // nome da base da dados
    private final String url        = "jdbc:mysql://" + host + "/" + database;     // caminho para a base de dados

    /**
     * Construtor padrão da classe.
     */
    public DataConnection() {

    }
    
    /**
     * Construtor modificado da classe.
     * @param driver driver utilizado para conexão.
     * @param url caminho da base de dados.
     * @param user usúario da base de dados.
     * @param password senha da base de dados.
     */
    public DataConnection(String driver, String url, String user, String password) {

    }

    /**
     * Informa o nome do driver que está em uso.
     * @return o nome do driver utilizado.
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Informa onde se encontra o banco em uso
     *
     * @return o caminho do banco de dados
     */
    public String getUrl() {
        return url;
    }

    /**
     * Informa o nome do usúario do banco.
     * @return usuario do banco
     */
    public String getUser() {
        return user;
    }

    /**
     * Informa a senha utilizada no banco.
     * @return senha do banco
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retorna a conexão corrente com o banco.
     * @return a conexao com o banco
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Altera a conexão com o banco.
     * @param connection a conexao para configurar
     */
    public void setConnection (Connection connection) {
        this.connection = connection;
    }

    /**
     * Retorna a pesquisa corrente no banco.
     * @return a pesquisa realizada no banco
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Altera a pesquisa a ser realizada no banco.
     * @param statement a pesquisa para configurar
     */
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * Retorna o resultado da pesquisa no banco.
     * @return resultado da pesquisa no banco.
     */
    public ResultSet getResult() {
        return result;
    }

    /**
     * Altera o resultado da pesquisa.
     * @param result o resultado para configurar.
     */
    public void setResult(ResultSet result) {
        this.result = result;
    }

    /**************************************************************
     *
     * Incio da conexão
     *
     *************************************************************/
    
    /**
     * Conexão com o banco usando o driver jdbc do MySql.
     * @throws java.lang.ClassNotFoundException lançamento de exceções para classe sem fundo.
     * @throws java.lang.InstantiationException lançamento de exceções de instância.
     * @throws java.lang.IllegalAccessException lançamento de exceções para erro de acesso.
     */
    public boolean initConnection() {
        // Configura a classe a ser utilizada e cria uma instância com ela 
        try {
            Class.forName(driver).newInstance();
            // responsavel por configurar as propriedade do driver de conexão
            connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {

            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Executa um insert SQL.
     * @param sql sql para execução no banco.
     * @return boolean para.
     */
    public int insertSQL(String sql) {
        int status = 0;
        try {
            // Atribui a variável pesquisa uma nova de pesquisa
            this.setStatement(getConnection().createStatement());

            // Definda a pesquisa, executamos a query no banco de dados
            this.getStatement().executeUpdate(sql);

            // Consulta o útlimo id inserido
            this.setResult(this.getStatement().executeQuery("SELECT LAST_INSERT_ID();"));

            // Recupera o último id inserido
            while (this.result.next()) {
                status = this.result.getInt(1);
            }

            //retorna o ultimo id inserido
            //System.out.println(status);
            return status;

        } catch (SQLException ex) {
            System.out.println("erro" + ex);
            return status;
        }
    }

    /**
     * Executa consultas SQL.
     * @param SQL String sql para executar no banco.
     * @return true para não ocorrência de erros e e false para erro.
     */
    public boolean executeSQL(String SQL)  {
        try {
            //createStatement de con para criar o Statement
            this.setStatement(getConnection().createStatement());

            // Definido o Statement, executamos a query no banco de dados
            this.setResult(getStatement().executeQuery(SQL));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * *******************************************
     *
     * Fim da conexão
     *
     ********************************************
     */
    /**
     * Encerra a conexão corrente com o banco de dados.
     *
     * @return true se o fechamento ocorrer devidamente e false se ouver um erro
     * no fechamento .
     */
    public boolean closeConnection() {
        try {
            if ((this.getResult() != null) && (this.statement != null)) { // Verifica se o valor corrente de resultado e pesquisa são nulos
                this.getResult().close(); // Fecha a conexão com resultado
                this.statement.close(); // Fecha a conexão com a pesquisa
            }
            this.getConnection().close(); // Fecha a conexão com o banco
            return true;
        } catch (SQLException ex) {
            Alert dialogo = new Alert(Alert.AlertType.ERROR);  // Nova tela de diálogo
            dialogo.setTitle("Erro!"); // Titúlo do diálogo 
            dialogo.setHeaderText(null); // Retira o cabeçalho
            dialogo.setContentText("Ocorreu um erro ao desconectar o banco:\n" + ex.getMessage()); // Conteúdo do corpo do diálogo
            Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow(); // Busca a janela e a converte em um palco
            dialogo.showAndWait(); // Inicia o show/tela
            return false;
        }
    }
}
