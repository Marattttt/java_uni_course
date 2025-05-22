package com.maratbakasov.javamodule1.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.maratbakasov.javamodule1.models.FileInfo;

@Repository
public class FileRepository {
	private final JdbcTemplate jdbcTemplate;

	public FileRepository(JdbcTemplate t) {
		this.jdbcTemplate = t;
	}

	private final RowMapper<FileInfo> fileInfoMapper = (rs, rowNum) -> new FileInfo(rs.getInt("id"),
			rs.getString("path"), rs.getBytes("contents"));

	public List<FileInfo> findAll() throws DataAccessException {
		return jdbcTemplate.query("SELECT * FROM files", fileInfoMapper);
	}

	public FileInfo findById(int id) throws DataAccessException {
		return jdbcTemplate.queryForObject("SELECT * FROM files WHERE id = ?", fileInfoMapper, id);
	}

	public int save(FileInfo file) throws IOException, DataAccessException {
		byte[] contents = file.getContents();
		if (contents == null) {
			contents = readFile(file.getPath());
		}

		return jdbcTemplate.update("INSERT INTO files (path, contents) VALUES(?, ?)", file.getPath(),
				contents);
	}

	public int deleteById(int id) throws DataAccessException {
		return jdbcTemplate.update("DELETE FROM files WHERE id = ?", id);
	}

	private byte[] readFile(String path) throws IOException {
		return Files.readAllBytes(Paths.get(path));
	}

}
