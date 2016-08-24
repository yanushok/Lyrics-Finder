package by.asoi.course.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
/*
 * ����� LyricsGrabber.
 * ��������� ��������� Grabber, � ������������� ���������� �����������
 * � ���������� �������.
 * 
 * ����� getPage ��������� ������� ���������� URI ����� �������������� �������.
 * � ���� ������ ���������� ���������� � �������� � ������������ ����� ������,
 * �������������� ����� �������� ��� �������� �� ��������� URI
 */
public class LyricsGrabber implements IGrabber {
	// ������� ������� ����� ��� ������ ��������� ���� ��������
    @Override
    public InputStream getPage(String uri) throws IOException {
    	// ������ ������ URL ��� ����������� � �������
        URL url = new URL(uri);
        
        // ���������� ������� ����� �� ���������� ������
        return (InputStream) url.openStream();
    }
}
