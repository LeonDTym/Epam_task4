package by.epam.task04.reader.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task04.exception.TextException;
import by.epam.task04.reader.CommonReader;

public class TextReaderImpl implements CommonReader {
	public static Logger logger = LogManager.getLogger();

	@Override
	public String readAll(String filePath) throws TextException {
		if (filePath == null) {
			throw new TextException("filePath is null");
		}
		Path path = Paths.get(filePath);
		String text;
		try (Stream<String> streamLines = Files.lines(path)) {
			text = streamLines.collect(Collectors.joining());
		} catch (IOException e) {
			logger.log(Level.ERROR, "file " + filePath + " not found", e);
			throw new TextException("file " + filePath + " not found", e);
		}
		logger.log(Level.INFO, "read data from file : " + text);
		return text;
	}
}
