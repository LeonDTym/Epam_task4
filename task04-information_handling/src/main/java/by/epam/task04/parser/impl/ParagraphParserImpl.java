package by.epam.task04.parser.impl;

import by.epam.task04.entity.ComponentText;
import by.epam.task04.entity.CompositeText;
import by.epam.task04.entity.TypeComponent;
import by.epam.task04.exception.TextException;
import by.epam.task04.parser.DataParser;

public class ParagraphParserImpl implements DataParser {
	public static final String SENTENCE_SPLIT = "(?<=([.!?.{3}]\s))";
	private DataParser parser = new SentenceParserImpl();

	@Override
	public ComponentText parseText(String paragraph) throws TextException {
		if (paragraph == null || paragraph.isEmpty()) {
			throw new TextException("paragraph is null or empty");
		}
		CompositeText paragraphComponent = new CompositeText(TypeComponent.PARAGRAPH);
		String[] sentenceData = paragraph.split(SENTENCE_SPLIT);
		for (String sentence : sentenceData) {
			ComponentText sentenceComponent = parser.parseText(sentence);
			paragraphComponent.addComponent(sentenceComponent);
		}
		return paragraphComponent;
	}
}
