package um2.websemantique.ontoligie.factory;

import java.util.ArrayList;

import um2.websemantique.entities.base.Author;
import um2.websemantique.entities.base.Book;
import um2.websemantique.entities.utils.GetterBookAuthor;
import um2.websemantique.entities.utils.SearchType;
import um2.websemantique.ontoligie.utils.Response;

public class GetterRDFAuthorBook implements Runnable {
	private String key;
	private SearchType typeS;
	private GetterBookAuthor getweb = new GetterBookAuthor();
	private int progress = 0;
	//ArrayList<Thread> thrds = new ArrayList<Thread>();

	public int getProgress() {
		return progress;
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SearchType getTypeS() {
		return typeS;
	}

	public void setTypeS(SearchType typeS) {
		this.typeS = typeS;
	}

	public GetterBookAuthor getGetweb() {
		return getweb;
	}

	/**
	 * find a query with type given from database or from web
	 * 
	 * @param query
	 *            that the client want to get
	 * @param type
	 *            {@link SearchType}
	 * @return result of query
	 */
	public String find(String query, SearchType type) {
		key = query;
		typeS = type;

		Response res = findSPRQL(query, type);

		String resultat = "";
		if (res.isOK()) {
			resultat = res.getContaints();
		} else {
			Thread x = new Thread(this);
			x.start();
		}

		return resultat;
	}

	/**
	 * find a query in data base
	 * 
	 * @param query
	 *            that the client want to get
	 * @param type
	 *            {@link SearchType}
	 * @return {@link Response}
	 */
	public Response findSPRQL(String query, SearchType type) {
		Response ret = new Response();
		ret.setContaints("OK");
		ret.setOK(false);
		return ret;
	}

	/**
	 * get the information into data base
	 */
	public void run() {
		findFromWeb(key, typeS);
	}
	
	/**
	 * find from Web and pill the DATABASE
	 * @param query 
	 * @param type
	 */
	public synchronized void findFromWeb(String query, SearchType type){
		progress = 25;
		getweb.find(key, typeS);
		progress = 50;

		int taille = getweb.getAuthors().size() + getweb.getBooks().size();
		int i = 0;

		for (Author author : getweb.getAuthors()) {
			progress = 50 + i * 50 / taille;
			RDFFactory.generateRDFAuthorInstance(author);
			i++;
		}

		for (Book book : getweb.getBooks()) {
			progress = 50 + i * 50 / taille;
			RDFFactory.generateRDFBookInstance(book);
			i++;
		}
		
		progress = 100;
	}
}
