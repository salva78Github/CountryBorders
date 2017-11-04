package it.polito.tdp.country.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.country.dao.CountryDAO;
import it.polito.tdp.country.exception.CountryBordersException;

public class Model {

	private UndirectedGraph<Country, DefaultEdge> graph;
	private CountryDAO dao = new CountryDAO();

	public Model(){
		graph = new SimpleGraph<>(DefaultEdge.class);
	}
	
	public void creaGrafo() throws CountryBordersException{
		
		List<Country> vertexList = dao.retrieveListCountries();
		System.out.println("<creaGrafo> numero vertici/paesi: " + vertexList.size());
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, vertexList );
		
		// crea gli archi del grafo --versione 1
		/*
		for(Country c1 : graph.vertexSet()){
			for(Country c2 : graph.vertexSet()){
				if(!c2.equals(c1)){
					//svantaggio: faccio n^2 di questa query
					if(dao.confinanti(c1,c2)){
						graph.addEdge(c1, c2);					}
				}
			}
			
		}
		*/
		
		// crea gli archi del grafo --versione 2
		//mi faccio dare da db la lista di tutti i vertici confinanti
		//sposto complessità dal model al dao
		//complessità: numero di vertici * grado medio grafo
		/*
		for(Country c1 : graph.vertexSet()){
			//questa query (più complicata di quella sopra) la faccio n volte
			List<Country> adiacenti = dao.retrieveListAdiacenti(c1);
			for(Country c2 : adiacenti){
				graph.addEdge(c1, c2);
			}
			
		}
		*/
		
		// crea gli archi del grafo --versione 3
		//faccio fare tutto il lavoro al dao
		//che mi dà la lista della coppia dei vertici
		for(CountryPair cp : dao.retrieveListaCountryPairAdiacenti()){
			graph.addEdge(cp.getC1(), cp.getC2());
		}
		
		
		Country c1 = new Country("USA", 2, "United States of America");
		//Esempio visita in ampiezza del grafo da un certo nodo
		BreadthFirstIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(graph, c1);
		List<Country> visited = new ArrayList<>();
		while(bfv.hasNext()){
			System.out.println(bfv.next());
			visited.add(bfv.next());
		}
		
		
	}
}
