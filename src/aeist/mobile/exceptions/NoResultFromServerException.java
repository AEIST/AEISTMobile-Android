package aeist.mobile.exceptions;

/**
 * 
 * @author joaovasques
 *
 */
public class NoResultFromServerException extends Exception{


	private static final long serialVersionUID = 1L;
	
	private String request; // request type: image content | non-image content
	
	public NoResultFromServerException(String request) {
		super();
		this.request = request;
	}
	
	public String getRequest() {
		return request;
	}

}
