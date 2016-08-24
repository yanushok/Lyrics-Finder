package by.asoi.course.factory;

import by.asoi.course.parser.AbstractParser;
import by.asoi.course.parser.LyricsParserMusixmatch;

/*
 * ������� ��������
 * ���������� ������� - ��������� ����� (�������, factory)
 */

public class ParserFactory {
	// ������������. ����� ������������� ��� ������������ �������
	// � ������ ������ ����, �� ����� �� ������ ������� ��� ��������� ��������,
	// ����������� ����� AbstractParser, � ���������� ������ �� ��� ���� ��������
	public enum ParserType {
		MUSIXMATCH
	}
	
	// ���������� ��������� ������� �������
	// � ������ ������ �� ����� ����
	public AbstractParser createParser(String typeParser) {
		// �������� ���������� ��������� ������������ �� ���������� ��������
		ParserType type = ParserType.valueOf(typeParser.toUpperCase());
		
		// � ����������� �� �������� ���������� ������
		switch (type) {
			case MUSIXMATCH:
				return LyricsParserMusixmatch.getInstance();
			default:
				throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
		}
	}
}
