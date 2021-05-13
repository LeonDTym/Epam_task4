package by.epam.task04.main;

import by.epam.task04.action.TextAction;
import by.epam.task04.comparator.ParagraphComporator;
import by.epam.task04.entity.ComponentText;
import by.epam.task04.entity.CompositeText;
import by.epam.task04.exception.TextException;
import by.epam.task04.parser.DataParser;
import by.epam.task04.parser.impl.TextParserImpl;
import by.epam.task04.reader.CommonReader;
import by.epam.task04.reader.impl.TextReaderImpl;

public class Main {

	public static void main(String[] args) throws TextException {
		CommonReader reader = new TextReaderImpl();
		String text = reader.readAll("resources/data/text.txt");

		DataParser parser = new TextParserImpl();
		ComponentText textComponent = parser.parseText(text);
		
		((CompositeText) textComponent).sortComponents(new ParagraphComporator());		
		TextAction action = new TextAction();
		action.findSentenceWithLongestWord(textComponent);
		action.findIdenticalWordsAndNumber(textComponent);
		action.removeSentencesWithFewerWords(textComponent, 19);
	}
}
