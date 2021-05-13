package by.epam.task04.parser.impl;

import by.epam.task04.entity.ComponentText;
import by.epam.task04.entity.CompositeText;
import by.epam.task04.entity.TypeComponent;
import by.epam.task04.exception.TextException;
import by.epam.task04.parser.DataParser;

public class SentenceParserImpl implements DataParser {
	public static final String LEXEME_SPLIT = "\s";
	private DataParser parser = new LexemeParserImpl();

	@Override
	public ComponentText parseText(String sentence) throws TextException {
		if (sentence == null || sentence.isEmpty()) {
			throw new TextException("sentence is null or empty");
		}
		CompositeText sentenceComponent = new CompositeText(TypeComponent.SENTENCE);
		String[] lexemeData = sentence.split(LEXEME_SPLIT);
		for (String lexeme : lexemeData) {
			ComponentText lexemeComponent = parser.parseText(lexeme);
			sentenceComponent.addComponent(lexemeComponent);
		}
		return sentenceComponent;
	}
}
