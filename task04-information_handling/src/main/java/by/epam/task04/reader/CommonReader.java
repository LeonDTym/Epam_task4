package by.epam.task04.reader;

import by.epam.task04.exception.TextException;

public interface CommonReader {
	String readAll(String path) throws TextException;
}
