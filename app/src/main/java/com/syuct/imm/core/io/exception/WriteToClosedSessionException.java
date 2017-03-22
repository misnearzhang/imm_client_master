 
package com.syuct.imm.core.io.exception;

import java.io.Serializable;


public class WriteToClosedSessionException extends Exception implements Serializable {
	 
	private static final long serialVersionUID = 1L;

	public WriteToClosedSessionException() {
		super();
	}
 
	public WriteToClosedSessionException(String s) {
		super(s);
	}
}