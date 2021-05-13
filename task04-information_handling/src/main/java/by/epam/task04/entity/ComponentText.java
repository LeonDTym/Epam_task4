package by.epam.task04.entity;

import java.util.List;

public interface ComponentText{

	TypeComponent getType();

	List<ComponentText> getComponents();
	
	int countSymbol();

	public String toString();
}
