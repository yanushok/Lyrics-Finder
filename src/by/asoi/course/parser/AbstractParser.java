package by.asoi.course.parser;

/*
 * ����������� ����� AbstractParser.
 * ����� ������� ��� ���� ����������� �� ���� �������-��������.
 * ���������� ����������� ����� �������, ����������� ��� ������
 * ������ ������� HTML.
 * 
 * ����� getLyrics �� ����� ������� ����������, � ���������� ����� �����.
 * ���� grabber �������� �������, ����������� ��� ��������� �������� ������
 * ������.
 * ���� url ������ ����� �������������� �������.
 * ������ getGrabber � setGrabber ���������� ��� ������� � ��������� ��������.
 */

public abstract class AbstractParser {
	// ���� ��������
	protected IGrabber grabber;
		
	// �����������, ������������� �������
	public AbstractParser(IGrabber grabber) {
		setGrabber(grabber);
	}
	
	// ���������� �������
	protected IGrabber getGrabber() {
        return grabber;
    }
 
	// ��������� ��������
	protected void setGrabber(IGrabber grabber) {
        this.grabber = grabber;
    }
	
	// ����� ��������� ������ �����
	public abstract String getLyrics(String uri);
}
