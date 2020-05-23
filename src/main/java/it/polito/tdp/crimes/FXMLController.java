/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Month> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<DefaultWeightedEdge> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	DefaultWeightedEdge arco= this.boxArco.getValue();
    	if(arco==null) {
    		this.txtResult.appendText("SELEZIONA!");
    		return;
    	}
    	String partenza=grafo.getEdgeSource(arco);
    	String arrivo=grafo.getEdgeTarget(arco);
    	LinkedList<String> lista=model.getCamminoMassimo(partenza,arrivo);
    	System.out.println(lista);

    }
    Graph<String, DefaultWeightedEdge> grafo;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	String categoria=this.boxCategoria.getValue();
    	Month mese=this.boxMese.getValue();
    	if(categoria==null || mese==null) {
    		this.txtResult.setText("SELEZIONA!");
    		return ;
    	}
    	model.generaGrafo(categoria, mese);
    	ArrayList<DefaultWeightedEdge> risultato= model.getCombinazioniSupMedia();
    	if(risultato.isEmpty()) {
    		this.txtResult.setText("NESSUNA COMBINAZIONE");
    		return;
    	}
    	grafo=model.getGrafo();
    	this.txtResult.clear();
    	for(DefaultWeightedEdge arco: risultato) {
			this.txtResult.appendText(grafo.getEdgeSource(arco)+" "+grafo.getEdgeTarget(arco)+" "+grafo.getEdgeWeight(arco)+"\n");
    	}
    this.boxArco.getItems().addAll(risultato);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(model.getCategorie());
    	for(LocalDate m: model.getMesi()) {
    	this.boxMese.getItems().add(m.getMonth());
    }
    }
}
