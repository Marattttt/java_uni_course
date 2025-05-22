package com.maratbakasov.javamodule1.db;

import java.io.FileNotFoundException;

public class FileNotFound extends FileNotFoundException {
	public FileNotFound(String message) {
		super(message);
	}
}
