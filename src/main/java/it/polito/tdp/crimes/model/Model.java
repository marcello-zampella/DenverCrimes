package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

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
	private ArrayList<String> tipi;
	public Model () {
		campione= new LinkedList<String>();
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
						if(peso!=-1)
							Graphs.addEdge(grafo, primo, secondo, peso);
				}
			}
		}
		
		
	}

	public ArrayList<DefaultWeightedEdge> getCombinazioniSupMedia() {
		int numero= grafo.edgeSet().size();
		double totale=0;
			for(DefaultWeightedEdge arco: grafo.edgeSet()) {
				totale+=grafo.getEdgeWeight(arco);
			}
			double media= totale/numero;
			ArrayList<DefaultWeightedEdge> archi= new ArrayList<DefaultWeightedEdge>();
			for(DefaultWeightedEdge arco: grafo.edgeSet()) {
				if(grafo.getEdgeWeight(arco)>media) {
					archi.add(arco);
				}
			}
			System.out.println(grafo);
		return archi;
	}

	public Graph<String, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	int max;
	LinkedList<String> campione;

	public LinkedList<String> getCamminoMassimo(String partenza, String arrivo) {
		max=0;
		LinkedList<String> inseriti=new LinkedList<String>();
		
		inseriti.add(partenza);
		espandi(partenza, inseriti,arrivo);
		return campione;
	}
	
	private void espandi(String p, LinkedList<String> inseriti, String arrivo) {
		ArrayList<String> lista=new ArrayList<String>(Graphs.neighborListOf(grafo, p));
		lista.removeAll(inseriti);
		if(p.equals(arrivo)) {
			if(inseriti.size()>max) {
				max=inseriti.size();
				campione=(LinkedList<String>) inseriti.clone();
			}
			return;
		}
		for(String s: lista) {
			inseriti.add(s);
			espandi(s, inseriti,arrivo);
			inseriti.removeLast();
		}
	}
}
		
	
	
	
	
	

