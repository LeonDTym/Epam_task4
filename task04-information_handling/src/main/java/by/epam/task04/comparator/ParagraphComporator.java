package by.epam.task04.comparator;

import java.util.Comparator;

import by.epam.task04.entity.ComponentText;

public class ParagraphComporator implements Comparator<ComponentText> {

	@Override
	public int compare(ComponentText paragraph1, ComponentText paragraph2) {
		int numberSentences1 = paragraph1.getComponents().size();
		int numberSentences2 = paragraph2.getComponents().size();
		return numberSentences1 - numberSentences2;
	}
}
