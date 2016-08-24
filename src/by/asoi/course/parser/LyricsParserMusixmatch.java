package by.asoi.course.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/*
 * Класс LyricsParserMusixmatch
 * Класс-парсер HTML с сайта Musixmatch.com
 * Наследует класс Musixmatch.
 * Реализует обязательный метод getLyrics
 * 
 * Класс представляет собой шаблон проектирования Синглтон.
 * При реализации которого при работе программы может существовать только
 * 1 объект данного класса.
 * Это реализовано с помощью закрытого конструктора модификатором private
 * который доступен только изнутри этого класса. А так же с помощью
 * статического метода getInstance, возвращающего ссылку на объект данного класса
 */

public class LyricsParserMusixmatch extends AbstractParser {
	
	/*
	 * Поле типа LyricsParserMusixmatch
	 * Экземпляр данного класса
	 */
	private static LyricsParserMusixmatch parser;
	
	/*
	 * Закрытый конструктор
	 * Принимает 2 параметра:
	 * url - адрес запрашиваемого ресурса
	 * grabber - объект граббера
	 * 
	 * Явным образом вызывает конструктор базового класса
	 * которому передаёт параметры
	 */
	private LyricsParserMusixmatch(IGrabber grabber) {
		super(grabber);
	}
	
	/*
	 * Метод getInstance
	 * Входной параметр - адрес запрашиваемого ресурса,
	 * нужен для того, чтобы передать его конструктору данного класса
	 * 
	 * Возвращаемое значение - ссылка на объект данного класса
	 * 
	 * В теле метода проверяется наличие ссылки на объект данного класса
	 * Если ссылка не найдена, то создаётся новый объект, один из параметров которого
	 * объект граббера
	 * Иначе возвращается ссылка на уже созданный экземпляр данного класса
	 * 
	 * Этот метод реализует шаблон - Singleton (одиночка)
	 */
	public static LyricsParserMusixmatch getInstance() {
		if (parser == null) {
			parser = new LyricsParserMusixmatch(new LyricsGrabber());
		}
		return parser;
	}

	/*
	 * Метод-парсер, "вытаскивающий" текст песни из HTML кода.
	 * 
	 * Входные параметры:
	 * before - элемент, который находится непосредственно перед искомыми данными
	 * text - HTML строка, представляющая собой исходный код HTML страницы
	 * after - элемент, который находится непосредственно после искомых данных
	 * 
	 * возвращаемое значение - строка, представляющая собой текст песни
	 * 
	 * параметр before является HTML тегом, внутри которого следует искать текст
	 * параметр after является закрывающим тэгом
	 * 
	 * в теле метода находится нужный тэг, и удаляется всё что было до него,
	 * включая сам тэг
	 * далее находиться закрывающий тэг, и удаляется всё что после него,
	 * включая сам тэг
	 * остаётся только искомый текст - текст песни
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
	 * Метод, возвращающий текст песни
	 * Реализация метода объявленного в базовом классе
	 * 
	 * Возвращаемое значение - текст песни, полученный из метода parser()
	 * 
	 * в теле метода получается входной поток, возвращаемый граббером,
	 * а конкретнее методом getPage(url) граббера
	 * сам граббер возвращается методом getGrabber(), который наследуется из
	 * базового класса (AbstractParser), так же наследуется поле url.
	 * Далее происходит парсниг элемента из входного потока.
	 */
	@Override
	public String getLyrics(String uri) {
		String result = "";
		
		try {
			// Получение входного потока из граббера
            BufferedReader reader = new BufferedReader(new InputStreamReader(getGrabber().getPage(uri), "UTF-8"));
            
            // Строка, в которую постепенно добавляется весь код HTML страницы
            StringBuilder pageContent = new StringBuilder("");
            // Временная строка для построчного считывания из входного потока
            String line;
            
            // Конструирование строки с HTML кодом перед парсингом
            // В конце добавляется символ переноса строки
            // т.к. BufferedReader его съедает
            while ((line = reader.readLine()) != null) {
                pageContent.append(line + "\n");
            }
            
            // Использование сторонней библиотеки Jsoup для
            // получения дерева HTML документа
            // Передаём заголовок User-Agent, заставляя думать сервер, что запрос пришёл с реального браузера, и не обрезать контект в исходном коде страницы
            Document doc = Jsoup.connect(uri).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0").get();
            //Document doc = Jsoup.parse(pageContent.toString());
            
            // Получение конкретного элемента по его ID
            //Element elem = doc.getElementById("lyrics-html");
            Elements elements = doc.getElementsByClass("mxm-lyrics__content");
            
            /*
             * Далее из этого элемента можно просто достать текст
             * методом elem.text();
             * который и будет содержать текст песни,
             * но внутренние механизми "съедают" символ переноса строки
             * и полученный текст будет представлять слитную строку
             * 
             * Поэтому будем парсить текст песни самостоятельно.
             * Но так как тэг, в котором находиться текст песни
             * имеет динамическое значение одного из атрибутов,
             * то явно мы не может задать его в наш собственный парсер.
             * Для этого получим целиком тэг, содержащий текст песни,
             * и выделим из него открывающий тэг. Это необходимо для передачи
             * в парсер, реализованный выше.
             */
            
            
            // Получаем индекс последнего символа открывающего тэга
            ////int firstTagEndIndex = elements.get(0).outerHtml().indexOf(">");
            // Вырезаем этот тэг из строки
            ////String firstTag = elements.get(0).outerHtml().substring(0, firstTagEndIndex);
            
            // получаем текст песни
            // передаём в парсер только что полученный открывающий тэг
            // весь HTML документ
            // и закрывающий тэг, который фиксирован для всех страниц сайта
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
