package by.epam.task04.parser.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task04.entity.ComponentText;
import by.epam.task04.entity.CompositeText;
import by.epam.task04.entity.TypeComponent;
import by.epam.task04.exception.TextException;
import by.epam.task04.parser.DataParser;

public class TextParserImpl implements DataParser {
	public static Logger logger = LogManager.getLogger();
	public static final String PARAGRAPH_SPLIT = "\s{4}";
	private DataParser parser = new ParagraphParserImpl();

	@Override
	public ComponentText parseText(String text) throws TextException {
		if (text == null || text.isEmpty()) {
			throw new TextException("text is null or empty");
		}
		CompositeText textComponent = new CompositeText(TypeComponent.TEXT);
		String[] paragraphData = text.split(PARAGRAPH_SPLIT);
		for (String paragraph : paragraphData) {
			if (!paragraph.isEmpty()) {
				ComponentText paragraphComponent = parser.parseText(paragraph);
				textComponent.addComponent(paragraphComponent);
			}
		}
		logger.log(Level.INFO, "after parsing the component text is created: " + textComponent.toString());
		return textComponent;
	}
}
