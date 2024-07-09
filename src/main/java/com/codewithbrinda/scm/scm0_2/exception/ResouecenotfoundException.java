package com.codewithbrinda.scm.scm0_2.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResouecenotfoundException extends RuntimeException {

	String recourcename;
	String fildname;
	String id;
	int fildvale;

	public ResouecenotfoundException(String recourcename, String fildname, int fildvale) {
		super(String.format("%s is not found whith %s : %s", recourcename, fildname, fildvale));
		this.recourcename = recourcename;
		this.fildname = fildname;
		this.fildvale = fildvale;
	}

	public ResouecenotfoundException(String recourcename, String fildname, String id) {
		super(String.format("%s is not found whith %s : %s", recourcename, fildname, id));
		this.recourcename = recourcename;
		this.fildname = fildname;
		this.id = id;
	}

}
