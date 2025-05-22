package com.maratbakasov.javamodule1.models;

public class FileInfo {
	private int id;
	private String path;
	private byte[] contents;

	public FileInfo() {
	}

	public FileInfo(int id, String path, byte[] contents) {
		this.id = id;
		this.path = path;
		this.contents = contents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}
}
