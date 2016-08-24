package by.asoi.course.manager;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.track.Track;
import org.jmusixmatch.entity.track.TrackData;

import by.asoi.course.factory.ParserFactory;
import by.asoi.course.parser.AbstractParser;
import by.asoi.course.view.ILyrics;

/*
 * ����� ��������
 * �������� ������ ����������� ������� MVC
 * ��������� ����� ����� ��������(���) � ����������� �����������
 */

public class ParserManager implements Runnable {
	// ���� �������
	private AbstractParser parser;
	// ������� ��������
	private ParserFactory factory;
	// ������ �� ���������
	private ILyrics frame;
	
	// ���� ������� � �������� �����
	private String artist;
	private String song;
	
	// �����������, ��������� ������ �� ���������
	public ParserManager(ILyrics frame) {
		factory = new ParserFactory();
		this.frame = frame;
	}
	
	// ��������� ������� � �������� �����
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public void setSong(String song) {
		this.song = song;
	}
	
	// ��������� ������ �����
	// ����� ��������� �� ���������� �����
	// ����� ��� ����, ����� �� ������� ����������� ���������
	// � ��� ������ ����������� �����������
	public void getLyrics() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	// ��, ��� ���������� � ������
	@Override
	public void run() {
		// ����� ���� ���������� ���� � ������� MusixMatch
		// � ������������
		String apiKey = "aeda754a55418a343f5f70fa392cce22";
		MusixMatch musixMatch = new MusixMatch(apiKey);
		
		// �������� ���� �� ���� ������ ������� MusixMatch
		Track track = null;
		try {
			track = musixMatch.getMatchingTrack(song, artist);
		} catch (MusixMatchException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		// �������� ���������� � �����
		TrackData data = track.getTrack();
		// ������ ������
		parser = factory.createParser("MUSIXMATCH");
		// ������ �����
		String lyrics = parser.getLyrics(data.getTrackShareURL());
		// ������������� ����� ����� �� �����
		frame.setLyrics(lyrics);
	}
}
