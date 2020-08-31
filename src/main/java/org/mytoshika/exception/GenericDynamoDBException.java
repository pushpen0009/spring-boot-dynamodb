package org.mytoshika.exception;

public class GenericDynamoDBException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenericDynamoDBException(Exception e) {
        super(e);
    }
}
