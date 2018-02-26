package ggd.auth;

public class AuthException extends Exception {

	private static final long serialVersionUID = 6353681541619730921L;

	public AuthException() {
		super();
	}
	
	public AuthException(String msg) {
		super(msg);
	}
	
	public AuthException(Throwable e) {
		super(e);
	}
	
	public AuthException(String msg, Throwable e) {
		super(msg, e);
	}
}
