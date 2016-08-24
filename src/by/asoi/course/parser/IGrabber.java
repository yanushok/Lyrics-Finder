package by.asoi.course.parser;

import java.io.IOException;
import java.io.InputStream;
/*
 * ��������� Grabber
 * ��������� ��������� �����, ������� ������ ������������ ������� �����
 * ����������� �� ��������� URI ��������� ������ ��������.
 * 
 * ������� �������, �����, ����������� ������ ��������� "������" ������
 * � ���������� ����� ������, �� �������� ����� ��������� ����������
 */
public interface IGrabber {
	// ������� ������� ����� ��� ������ ��������� ���� ��������
	public InputStream getPage(String uri) throws IOException;
}
