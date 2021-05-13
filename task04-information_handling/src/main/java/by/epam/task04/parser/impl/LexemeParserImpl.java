package by.epam.task04.parser.impl;

import by.epam.task04.entity.ComponentText;
import by.epam.task04.entity.CompositeText;
import by.epam.task04.entity.Symbol;
import by.epam.task04.entity.TypeComponent;
import by.epam.task04.exception.TextException;
import by.epam.task04.parser.DataParser;

public class LexemeParserImpl implements DataParser {
	public static final String WORD_CODE_REGEX = "\\w+\\.\\w+\\(.*\\)";
	public static final String PUNCTUATION_MARK_REGEX = "(\\p{Punct})";
	public static final String MARK_WORD_MARK_REGEX = "(\\p{Punct}.+\\p{Punct})";
	public static final String MARK_WORD_REGEX = "(\\p{Punct}.+)";
	public static final String CODE_MARK_REGEX = "\\w+\\.\\w+\\(.*\\)\\p{Punct}";
	public static final String WORD_MARK_REGEX = ".+\\p{Punct}";
	public static final String WORD_MARK_SPLIT = "(?=[,.!?)])";
	private DataParser parser = new WordParserImpl();

	@Override
	public ComponentText parseText(String lexeme) throws TextException {
		if (lexeme == null || lexeme.isEmpty()) {
			throw new TextException("lexeme is null or empty");
		}
		CompositeText lexemeComponent = new CompositeText(TypeComponent.LEXEME);
		String word;
		Symbol symbol;
		if (lexeme.matches(WORD_CODE_REGEX)) {
			ComponentText wordComponent = parser.parseText(lexeme);
			lexemeComponent.addComponent(wordComponent);
		} else if (lexeme.matches(PUNCTUATION_MARK_REGEX)) {
			symbol = new Symbol(TypeComponent.PUNCTUATION_MARK, lexeme.charAt(0));
			lexemeComponent.addComponent(symbol);
		} else if (lexeme.matches(MARK_WORD_MARK_REGEX)) {
			Character firstSymbol = lexeme.charAt(0);
			symbol = new Symbol(TypeComponent.PUNCTUATION_MARK, firstSymbol);
			lexemeComponent.addComponent(symbol);
			word = lexeme.substring(1, lexeme.length() - 1);
			ComponentText wordComponent = parser.parseText(word);
			lexemeComponent.addComponent(wordComponent);
			Character lastSymbol = lexeme.charAt(lexeme.length() - 1);
			symbol = new Symbol(TypeComponent.PUNCTUATION_MARK, lastSymbol);
			lexemeComponent.addComponent(symbol);
		} else if (lexeme.matches(MARK_WORD_REGEX)) {
			Character firstSymbol = lexeme.charAt(0);
			symbol = new Symbol(TypeComponent.PUNCTUATION_MARK, firstSymbol);
			lexemeComponent.addComponent(symbol);
			word = lexeme.substring(1, lexeme.length());
			ComponentText wordComponent = parser.parseText(word);
			lexemeComponent.addComponent(wordComponent);
		} else if (lexeme.matches(CODE_MARK_REGEX)) {
			word = lexeme.substring(0, lexeme.length() - 1);
			ComponentText wordComponent = parser.parseText(word);
			lexemeComponent.addComponent(wordComponent);
			Character lastSymbol = lexeme.charAt(lexeme.length() - 1);
			symbol = new Symbol(TypeComponent.PUNCTUATION_MARK, lastSymbol);
			lexemeComponent.addComponent(symbol);
		} else if (lexeme.matches(WORD_MARK_REGEX)) {
			String[] lexemeEelements = lexeme.split(WORD_MARK_SPLIT);
			for (String element : lexemeEelements) {
				if (element.matches(PUNCTUATION_MARK_REGEX)) {
					Character markSymbol = element.charAt(0);
					symbol = new Symbol(TypeComponent.PUNCTUATION_MARK, markSymbol);
					lexemeComponent.addComponent(symbol);
				} else {
					ComponentText wordComponent = parser.parseText(element);
					lexemeComponent.addComponent(wordComponent);
				}
			}
		} else {
			ComponentText wordComponent = parser.parseText(lexeme);
			lexemeComponent.addComponent(wordComponent);
		}
		return lexemeComponent;
	}
}