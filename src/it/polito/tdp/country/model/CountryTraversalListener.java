package it.polito.tdp.country.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;

public class CountryTraversalListener implements TraversalListener<Country, DefaultEdge> {
	private Graph<Country, DefaultEdge> graph;
	private Map<Country, Country> map;

	public CountryTraversalListener(Graph<Country, DefaultEdge> g, Map<Country, Country> map) {
		this.graph = g;
		this.map = map;
	}

	/**
	 * @return the graph
	 */
	public Graph<Country, DefaultEdge> getGraph() {
		return graph;
	}

	/**
	 * @param graph
	 *            the graph to set
	 */
	public void setGraph(Graph<Country, DefaultEdge> graph) {
		this.graph = graph;
	}

	/**
	 * @return the map
	 */
	public Map<Country, Country> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<Country, Country> map) {
		this.map = map;
	}

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> evento) {
		/*
		 * evento.getEdge() è l'arco appena attraversato arco:
		 * graph.edgeSource/Dest(evento.getEdge() )
		 
		 *la mappa deve sempre puntare dal nuovo verso il vecchio (la sorgente)
		 *il vecchio riesco ad individuarlo perché ce l'ho già
		 */
		
		Country c1 = this.graph.getEdgeSource(evento.getEdge());
		Country c2 = this.graph.getEdgeTarget(evento.getEdge());
		
		//se c1 e c2 sono contenuti nella mappa, non devo fare nulla
		//se non faccio così, rischio di creare un ciclo 
		if(map.containsKey(c1) && map.containsKey(c2)){
			return;
		}
		
		//se c1 non è contenuto nella mappa, allora è lui il nuovo ed è stato scoperto da c2
		if(!map.containsKey(c1)){
			map.put(c1, c2);
		}
		//altrimenti il nuovo è c2 è da stato scoperto da c1
		else {
			map.put(c2, c1);
		}
		
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Country> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<Country> arg0) {
		// TODO Auto-generated method stub

	}

}
