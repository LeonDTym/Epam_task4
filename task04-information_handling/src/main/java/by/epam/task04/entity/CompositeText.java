package by.epam.task04.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task04.exception.TextException;

public class CompositeText implements ComponentText {
	public static Logger logger = LogManager.getLogger();
	public final static String SPACE = " ";
	public final static String TAB_CHARACTER = "\t";
	private TypeComponent type;
	private final List<ComponentText> components = new ArrayList<>();

	public CompositeText() {
	}

	public CompositeText(TypeComponent type) {
		this.type = type;
	}

	public TypeComponent getType() {
		return type;
	}

	public void setType(TypeComponent type) {
		this.type = type;
	}

	public List<ComponentText> getComponents() {
		return Collections.unmodifiableList(components);
	}
	
	public boolean addComponent(ComponentText componentText) {
		return components.add(componentText);
	}

	public boolean removeComponent(ComponentText componentText) {
		return components.remove(componentText);
	}

	public List<ComponentText> sortComponents(Comparator<ComponentText> comparator) throws TextException {
		if (comparator == null) {
			throw new TextException("sort conditions are not specified");
		}
		components.sort(comparator);
		logger.log(Level.INFO, "components after sort: " + components.toString());
		return Collections.unmodifiableList(components);
	}

	public int countWord() {
		int counter = 0;
		for (ComponentText component : this.components) {
			if (component.getType() == TypeComponent.WORD) {
				counter++;
			} else if (component.getType() != TypeComponent.PUNCTUATION_MARK) {
				counter += ((CompositeText) component).countWord();
			}
		}
		return counter;
	}

	@Override
	public int countSymbol() {
		int counter = 0;
		for (ComponentText component : this.components) {
			counter += component.countSymbol();
		}
		return counter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((components == null) ? 0 : components.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeText other = (CompositeText) obj;
		if (components == null) {
			if (other.components != null)
				return false;
		} else if (!components.equals(other.components))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		for (ComponentText component : this.components) {
			text.append(component.toString());
			if (component.getType() == TypeComponent.LEXEME) {
				text.append(SPACE);
			}
			if (component.getType() == TypeComponent.PARAGRAPH) {
				text.append(TAB_CHARACTER);
			}
		}
		return text.toString();
	}
}
