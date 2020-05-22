package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.time.*;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private Graph<String, DefaultWeightedEdge> grafo;
	ArrayList<String> tipi;
	public Model () {
		
	}
	EventsDao dao= new EventsDao();
	
	public ArrayList<String> getCategorie() {
		ArrayList<String> categorie=dao.getCategorieEventi();
		Collections.sort(categorie);
		return categorie;
	}
	
	public ArrayList<LocalDate> getMesi() {
		return dao.getDate();
	}

	public void generaGrafo(String categoria, Month month) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//ArrayList<Reato> eventi=dao.getEventi(categoria, month);
		//int contatore;
		tipi= dao.getTipoReatoPerCategoria(categoria, month);
		Graphs.addAllVertices(grafo, tipi);
		for(String primo: tipi) {
		//	contatore=0;
			for(String secondo: tipi) {
				if(!primo.equals(secondo)) {
					int peso=dao.getContatoreCrimini(primo,secondo, month);
					Graphs.addEdge(grafo, primo, secondo, peso);
				}
			}
		}
		
		
	}

	public ArrayList<String> getCombinazioniSupMedia() {
		int numero= grafo.vertexSet().size();
		double totale=0;
			for(DefaultWeightedEdge arco: grafo.edgeSet()) {
				totale+=grafo.getEdgeWeight(arco);
			}
			double media= totale/numero;
			ArrayList<String> archi= new ArrayList<String>();
			for(DefaultWeightedEdge arco: grafo.edgeSet()) {
				if(grafo.getEdgeWeight(arco)>media) {
					archi.add(grafo.getEdgeSource(arco)+" "+grafo.getEdgeTarget(arco)+" "+grafo.getEdgeWeight(arco));
				}
			}
		return archi;
	}
	
	
}
