package by.asoi.course.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/*
 * ����� LyricsParserMusixmatch
 * �����-������ HTML � ����� Musixmatch.com
 * ��������� ����� Musixmatch.
 * ��������� ������������ ����� getLyrics
 * 
 * ����� ������������ ����� ������ �������������� ��������.
 * ��� ���������� �������� ��� ������ ��������� ����� ������������ ������
 * 1 ������ ������� ������.
 * ��� ����������� � ������� ��������� ������������ ������������� private
 * ������� �������� ������ ������� ����� ������. � ��� �� � �������
 * ������������ ������ getInstance, ������������� ������ �� ������ ������� ������
 */

public class LyricsParserMusixmatch extends AbstractParser {
	
	/*
	 * ���� ���� LyricsParserMusixmatch
	 * ��������� ������� ������
	 */
	private static LyricsParserMusixmatch parser;
	
	/*
	 * �������� �����������
	 * ��������� 2 ���������:
	 * url - ����� �������������� �������
	 * grabber - ������ ��������
	 * 
	 * ����� ������� �������� ����������� �������� ������
	 * �������� ������� ���������
	 */
	private LyricsParserMusixmatch(IGrabber grabber) {
		super(grabber);
	}
	
	/*
	 * ����� getInstance
	 * ������� �������� - ����� �������������� �������,
	 * ����� ��� ����, ����� �������� ��� ������������ ������� ������
	 * 
	 * ������������ �������� - ������ �� ������ ������� ������
	 * 
	 * � ���� ������ ����������� ������� ������ �� ������ ������� ������
	 * ���� ������ �� �������, �� �������� ����� ������, ���� �� ���������� ��������
	 * ������ ��������
	 * ����� ������������ ������ �� ��� ��������� ��������� ������� ������
	 * 
	 * ���� ����� ��������� ������ - Singleton (��������)
	 */
	public static LyricsParserMusixmatch getInstance() {
		if (parser == null) {
			parser = new LyricsParserMusixmatch(new LyricsGrabber());
		}
		return parser;
	}

	/*
	 * �����-������, "�������������" ����� ����� �� HTML ����.
	 * 
	 * ������� ���������:
	 * before - �������, ������� ��������� ��������������� ����� �������� �������
	 * text - HTML ������, �������������� ����� �������� ��� HTML ��������
	 * after - �������, ������� ��������� ��������������� ����� ������� ������
	 * 
	 * ������������ �������� - ������, �������������� ����� ����� �����
	 * 
	 * �������� before �������� HTML �����, ������ �������� ������� ������ �����
	 * �������� after �������� ����������� �����
	 * 
	 * � ���� ������ ��������� ������ ���, � ��������� �� ��� ���� �� ����,
	 * ������� ��� ���
	 * ����� ���������� ����������� ���, � ��������� �� ��� ����� ����,
	 * ������� ��� ���
	 * ������� ������ ������� ����� - ����� �����
	 */
	private String parser(String before, String text, String after) {
		String result = text;
		
		int a, b;
		
		if (before.isEmpty() || result.isEmpty() || after.isEmpty()) {
			return "";
		}
		
		a = result.indexOf(before);
		
		if (a == 0)
			return "";
		else {
			result = result.substring(a + before.length());
			b = result.indexOf(after);
			if (b > 0) {
				result = result.substring(1, b);
			}
		}
		
		return result;
	}

	/*
	 * �����, ������������ ����� �����
	 * ���������� ������ ������������ � ������� ������
	 * 
	 * ������������ �������� - ����� �����, ���������� �� ������ parser()
	 * 
	 * � ���� ������ ���������� ������� �����, ������������ ���������,
	 * � ���������� ������� getPage(url) ��������
	 * ��� ������� ������������ ������� getGrabber(), ������� ����������� ��
	 * �������� ������ (AbstractParser), ��� �� ����������� ���� url.
	 * ����� ���������� ������� �������� �� �������� ������.
	 */
	@Override
	public String getLyrics(String uri) {
		String result = "";
		
		try {
			// ��������� �������� ������ �� ��������
            BufferedReader reader = new BufferedReader(new InputStreamReader(getGrabber().getPage(uri), "UTF-8"));
            
            // ������, � ������� ���������� ����������� ���� ��� HTML ��������
            StringBuilder pageContent = new StringBuilder("");
            // ��������� ������ ��� ����������� ���������� �� �������� ������
            String line;
            
            // ��������������� ������ � HTML ����� ����� ���������
            // � ����� ����������� ������ �������� ������
            // �.�. BufferedReader ��� �������
            while ((line = reader.readLine()) != null) {
                pageContent.append(line + "\n");
            }
            
            // ������������� ��������� ���������� Jsoup ���
            // ��������� ������ HTML ���������
            // ������� ��������� User-Agent, ��������� ������ ������, ��� ������ ������ � ��������� ��������, � �� �������� ������� � �������� ���� ��������
            Document doc = Jsoup.connect(uri).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0").get();
            //Document doc = Jsoup.parse(pageContent.toString());
            
            // ��������� ����������� �������� �� ��� ID
            //Element elem = doc.getElementById("lyrics-html");
            Elements elements = doc.getElementsByClass("mxm-lyrics__content");
            
            /*
             * ����� �� ����� �������� ����� ������ ������� �����
             * ������� elem.text();
             * ������� � ����� ��������� ����� �����,
             * �� ���������� ��������� "�������" ������ �������� ������
             * � ���������� ����� ����� ������������ ������� ������
             * 
             * ������� ����� ������� ����� ����� ��������������.
             * �� ��� ��� ���, � ������� ���������� ����� �����
             * ����� ������������ �������� ������ �� ���������,
             * �� ���� �� �� ����� ������ ��� � ��� ����������� ������.
             * ��� ����� ������� ������� ���, ���������� ����� �����,
             * � ������� �� ���� ����������� ���. ��� ���������� ��� ��������
             * � ������, ������������� ����.
             */
            
            
            // �������� ������ ���������� ������� ������������ ����
            ////int firstTagEndIndex = elements.get(0).outerHtml().indexOf(">");
            // �������� ���� ��� �� ������
            ////String firstTag = elements.get(0).outerHtml().substring(0, firstTagEndIndex);
            
            // �������� ����� �����
            // ������� � ������ ������ ��� ���������� ����������� ���
            // ���� HTML ��������
            // � ����������� ���, ������� ���������� ��� ���� ������� �����
            ////result = parser(firstTag, doc.html(), "</p>");
            //result = parser(firstTag, pageContent.toString(), "</span>");
            
            for (Element element : elements) {
            	result += element.text();
            }
            //result = elements.get(0).text();

        } catch (IOException e) {
        	System.err.println(e.getMessage());
            e.printStackTrace();
        }
		
		return result;
	}
}
