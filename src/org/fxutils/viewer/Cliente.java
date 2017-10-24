/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fxutils.viewer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Gleidson
 */
public class Cliente {

    IntegerProperty codigo = new SimpleIntegerProperty();
    StringProperty nome = new SimpleStringProperty();

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getNome() {
        return nome.get();
    }

    public void setCodigo(int id) {
        this.codigo.set(id);
    }

}
