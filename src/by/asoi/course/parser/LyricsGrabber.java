package by.asoi.course.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
/*
 * Класс LyricsGrabber.
 * Реализует интерфейс Grabber, и предоставляет реализацию объявленных
 * в интерфейсе методов.
 * 
 * Метод getPage принимает входным параметром URI адрес запрашиваемого ресурса.
 * В теле метода происходит соединение с ресурсом и возвращаться поток данных,
 * представляющий собой исходный код страницы по заданному URI
 */
public class LyricsGrabber implements IGrabber {
	// Получем входной поток для чтения исходного кода страницы
    @Override
    public InputStream getPage(String uri) throws IOException {
    	// Создаём объект URL для подключения к ресурсу
        URL url = new URL(uri);
        
        // Возвразаем входной поток на полученные данные
        return (InputStream) url.openStream();
    }
}
