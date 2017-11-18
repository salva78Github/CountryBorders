package it.polito.tdp.country.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private List<Country> countries;
	Map<Country, Country> alberoDiVisita = new HashMap<Country, Country>();

	public Model() {
	}

	public List<Country> retrieveCountries() throws CountryBordersException {
		if (this.countries == null) {
			// ottimizzazione: visto che la lista delle nazioni mi serve più
			// volte,
			// cerco di andare su db una volta sola
			countries = dao.retrieveListCountries();
		}
		return this.countries;
	}

	public List<Country> getRaggiungibili(Country partenza) throws CountryBordersException {
		UndirectedGraph<Country, DefaultEdge> g = getGrafo();

		BreadthFirstIterator<Country, DefaultEdge> bfi = new BreadthFirstIterator<Country, DefaultEdge>(g, partenza);
		List<Country> list = new ArrayList<Country>();


		// albero di raggiungibilità che mi serve per la funzione cerca percorso
		Map<Country, Country> albero = new HashMap<Country, Country>();
		albero.put(partenza, null);
		bfi.addTraversalListener(new CountryTraversalListener(g, albero));
		while (bfi.hasNext()) {
			list.add(bfi.next());
		}

		this.alberoDiVisita = albero;
		// System.out.println(albero.toString());

		return list;

	}

	private UndirectedGraph<Country, DefaultEdge> getGrafo() throws CountryBordersException {
		// in questo caso il grafo è sempre lo stesso.
		// non cambia in base a certe condizioni
		// Per esempio l'anno variabile
		if (this.graph == null) {
			this.graph = creaGrafo();
		}

		return this.graph;
	}

	public UndirectedGraph<Country, DefaultEdge> creaGrafo() throws CountryBordersException {

		this.graph = new SimpleGraph<>(DefaultEdge.class);
		List<Country> vertexList = this.retrieveCountries();
		System.out.println("<creaGrafo> numero vertici/paesi: " + vertexList.size());
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, vertexList);

		// crea gli archi del grafo --versione 1
		/*
		 * for(Country c1 : graph.vertexSet()){ for(Country c2 :
		 * graph.vertexSet()){ if(!c2.equals(c1)){ //svantaggio: faccio n^2 di
		 * questa query if(dao.confinanti(c1,c2)){ graph.addEdge(c1, c2); } } }
		 * 
		 * }
		 */

		// crea gli archi del grafo --versione 2
		// mi faccio dare da db la lista di tutti i vertici confinanti
		// sposto complessità dal model al dao
		// complessità: numero di vertici * grado medio grafo
		/*
		 * for(Country c1 : graph.vertexSet()){ //questa query (più complicata
		 * di quella sopra) la faccio n volte List<Country> adiacenti =
		 * dao.retrieveListAdiacenti(c1); for(Country c2 : adiacenti){
		 * graph.addEdge(c1, c2); }
		 * 
		 * }
		 */

		// crea gli archi del grafo --versione 3
		// faccio fare tutto il lavoro al dao
		// che mi dà la lista della coppia dei vertici
		List<CountryPair> listaCountryPairAdiacenti = dao.retrieveListaCountryPairAdiacenti();
		for (CountryPair cp : listaCountryPairAdiacenti) {
			graph.addEdge(cp.getC1(), cp.getC2());
		}

		return this.graph;

		/*
		 * Country c1 = new Country("USA", 2, "United States of America");
		 * //Esempio visita in ampiezza del grafo da un certo nodo
		 * BreadthFirstIterator<Country, DefaultEdge> bfv = new
		 * BreadthFirstIterator<>(graph, c1); List<Country> visited = new
		 * ArrayList<>(); while(bfv.hasNext()){ System.out.println(bfv.next());
		 * visited.add(bfv.next()); }
		 */

	}

	public List<Country> getPercorso(Country destinazione) {
		List<Country> percorso = new ArrayList<Country>();
		Country c = destinazione;
		
		//vado all'indietro perché è più semplice
		//un nodo di un albero ha solo un papà. 
		//andando in avanti potrei avere più figli
		while (c != null) {
			percorso.add(c);
			c = this.alberoDiVisita.get(c);
		}

		return percorso;
	}
}
