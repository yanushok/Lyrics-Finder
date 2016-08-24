package by.asoi.course.factory;

import by.asoi.course.parser.AbstractParser;
import by.asoi.course.parser.LyricsParserMusixmatch;

/*
 * Фабрика парсеров
 * Реализация шаблона - Фабричный метод (фабрика, factory)
 */

public class ParserFactory {
	// Перечисление. Здесь перечисляются все используемые парсеры
	// В данном случае один, но никто не мешает создать ещё несколько парсеров,
	// наследующих класс AbstractParser, и возвращать ссылку на них этой фабрикой
	public enum ParserType {
		MUSIXMATCH
	}
	
	// Возвращаем экземпляр нужного парсера
	// В данном случае он всего один
	public AbstractParser createParser(String typeParser) {
		// получаем конкретную константу перечисления по строковому названию
		ParserType type = ParserType.valueOf(typeParser.toUpperCase());
		
		// в зависимости от названия возвращаем парсер
		switch (type) {
			case MUSIXMATCH:
				return LyricsParserMusixmatch.getInstance();
			default:
				throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
		}
	}
}
