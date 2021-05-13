package by.epam.task04.parser;

import by.epam.task04.entity.ComponentText;
import by.epam.task04.exception.TextException;

public interface DataParser {
	ComponentText parseText(String text) throws TextException;
}
