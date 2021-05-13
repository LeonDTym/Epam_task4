package by.epam.task04.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task04.entity.ComponentText;
import by.epam.task04.entity.CompositeText;
import by.epam.task04.entity.TypeComponent;
import by.epam.task04.exception.TextException;

public class TextAction {
	public static Logger logger = LogManager.getLogger();
	public static final int WORD_COUNT_ONE = 1;

	public ComponentText findSentenceWithLongestWord(ComponentText text) throws TextException {
		if (text == null || text.getType()!=TypeComponent.TEXT) {
			throw new TextException("component is invalid");
		}
		ComponentText sentenceWithLongestWord = text;
		ComponentText longestWord = text;
		int maxWordLenght = 0;
		for (ComponentText paragraphComponent : text.getComponents()) {
			for (ComponentText sentenceComponent : paragraphComponent.getComponents()) {
				for (ComponentText lexemeComponent : sentenceComponent.getComponents()) {
					for (ComponentText elementLexeme : lexemeComponent.getComponents()) {
						if (elementLexeme.getType() == TypeComponent.WORD
								&& elementLexeme.countSymbol() > maxWordLenght) {
							maxWordLenght = elementLexeme.countSymbol();
							longestWord = elementLexeme;
							sentenceWithLongestWord = sentenceComponent;
						}
					}
				}
			}
		}
		logger.log(Level.INFO,
				"the longest word " + longestWord.toString() + " in sentence :" + sentenceWithLongestWord.toString());
		return sentenceWithLongestWord;
	}

	public ComponentText removeSentencesWithFewerWords(ComponentText text, int numberWords) throws TextException {
		if (text == null || text.getType()!=TypeComponent.TEXT) {
			throw new TextException("component is invalid");
		}
		List<ComponentText> sentencesWithFewerWords = new ArrayList<>();
		int counterWords = 0;
		for (ComponentText paragraphComponent : text.getComponents()) {
			for (ComponentText sentenceComponent : paragraphComponent.getComponents()) {
				counterWords = ((CompositeText) sentenceComponent).countWord();
				if (counterWords < numberWords) {
					sentencesWithFewerWords.add(sentenceComponent);
				}
			}
			for (ComponentText sentence : sentencesWithFewerWords) {
				((CompositeText) paragraphComponent).removeComponent(sentence);
			}
			sentencesWithFewerWords.removeAll(sentencesWithFewerWords);
		}
		logger.log(Level.INFO,
				"text after removing sentences with fewer words " + numberWords + " :" + text.toString());
		return text;
	}

	public Map<String, Integer> findIdenticalWordsAndNumber(ComponentText text) throws TextException {
		if (text == null || text.getType()!=TypeComponent.TEXT) {
			throw new TextException("component is invalid");
		}
		Map<String, Integer> identicalWords = new HashMap<>();
		for (ComponentText paragraphComponent : text.getComponents()) {
			for (ComponentText sentenceComponent : paragraphComponent.getComponents()) {
				for (ComponentText lexemeComponent : sentenceComponent.getComponents()) {
					for (ComponentText elementLexeme : lexemeComponent.getComponents()) {
						if (elementLexeme.getType() == TypeComponent.WORD) {
							String word = elementLexeme.toString().toLowerCase();
							Integer previousValue = identicalWords.put(word, WORD_COUNT_ONE);
							if (previousValue != null) {
								identicalWords.put(word, ++previousValue);
							}
						}
					}
				}
			}
		}
		Iterator<Map.Entry<String, Integer>> iterator = identicalWords.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> wordAndNumber = iterator.next();
			Integer number = wordAndNumber.getValue();
			if (number == WORD_COUNT_ONE) {
				iterator.remove();
			}
		}
		logger.log(Level.INFO, "identical words in the text and their number: " + identicalWords.toString());
		return identicalWords;
	}
}
