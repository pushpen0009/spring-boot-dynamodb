package org.mytoshika.exception;

public class DuplicateTableException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateTableException(Exception e) {
        super(e);
    }
}
