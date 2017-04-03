/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.crawlers;

import crawler.configuration.CrawlerParams;
import crawler.crawlers.factories.CrawlersFactory;
import crawler.crawlers.factories.CrawlerTarget;
import crawler.configuration.CrawlerConfiguration;
import crawler.crawlers.factories.CrawlerMode;
import crawler.crawlers.oneshot.OneShotCrawler;
import crawler.data.Adress;
import crawler.data.ImageSource;
import crawler.data.Sentence;
import crawler.data.Text;
import crawler.scrapping.collectors.Collector;
import crawler.scrapping.collectors.SentenceCollector;
import crawler.scrapping.filters.FilterMode;
import crawler.scrapping.filters.ImageFormatFilter;
import crawler.scrapping.parsers.HtmlUnitParser;
import crawler.scrapping.parsers.JsoupParser;
import java.util.List;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class CrawlersFactoryTest {

    public CrawlersFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createContinousCrawler method, of class CrawlersFactory.
     */
    @Test
    public void testCreateContinousCrawler() {
    }

    /**
     * Test of prepareConfig method, of class CrawlersFactory.
     */
@Test
public void testOneShotCrawler(){
       OneShotCrawler crawler = (OneShotCrawler) CrawlersFactory.createSpecialCrawler(CrawlerMode.OneShot, CrawlerTarget.Sentences);
       CrawlerConfiguration conf = new CrawlerConfiguration();
       conf.put(CrawlerParams.url, "http://kobietydokodu.pl/kategoria/niezbednik-juniora/");
       conf.put(CrawlerParams.sentences,new String[]{"java"});
       crawler.init(conf);
       List foundSentences = crawler.getResults().getAllDistinctOf(Sentence.class);
       List foundUrls = crawler.getResults().getAllDistinctOf(Adress.class);
       List foundTexts = crawler.getResults().getAllDistinctOf(Text.class);
}


@Test
public void testOneShotImageCrawler(){
       IImageCrawler crawler = (IImageCrawler) CrawlersFactory.createSpecialCrawler(CrawlerMode.OneShot, CrawlerTarget.Images);
       CrawlerConfiguration conf = new CrawlerConfiguration();
       conf.put(CrawlerParams.url, "http://kobietydokodu.pl/kategoria/niezbednik-juniora/");
       crawler.addImagesFilter(new ImageFormatFilter(FilterMode.POST, "jpg"));
       crawler.init(conf);
       List foundImages = crawler.getResults().getAllDistinctOf(ImageSource.class);
       List foundUrls = crawler.getResults().getAllDistinctOf(Adress.class);
       List foundTexts = crawler.getResults().getAllDistinctOf(Text.class);
}


@Test
public void testOneShotGenericCrawler_Jsoup(){
     OneShotCrawler crawler = (OneShotCrawler) CrawlersFactory.createSpecialCrawler(CrawlerMode.OneShot, CrawlerTarget.Generic);
       CrawlerConfiguration conf = new CrawlerConfiguration();
       conf.put(CrawlerParams.url, "http://kobietydokodu.pl/kategoria/niezbednik-juniora/");
       conf.put(CrawlerParams.sentences,new String[]{"java"});
       crawler.getSearchEngine().setParser(new JsoupParser());
       crawler.init(conf);
       List foundSentences = crawler.getResults().getAllDistinctOf(Sentence.class);
       List foundUrls = crawler.getResults().getAllDistinctOf(Adress.class);
       List foundTexts = crawler.getResults().getAllDistinctOf(Text.class);
       List foundImages = crawler.getResults().getAllDistinctOf(ImageSource.class);
}



@Test
public void testOneShotGenericCrawler_HtmlUnit(){
     OneShotCrawler crawler = (OneShotCrawler) CrawlersFactory.createSpecialCrawler(CrawlerMode.OneShot, CrawlerTarget.Generic);
       CrawlerConfiguration conf = new CrawlerConfiguration();
       conf.put(CrawlerParams.url, "http://kobietydokodu.pl/kategoria/niezbednik-juniora/");
       conf.put(CrawlerParams.sentences,new String[]{"java"});
       crawler.getSearchEngine().setParser(new HtmlUnitParser());
       crawler.init(conf);
       List foundSentences = crawler.getResults().getAllDistinctOf(Sentence.class);
       List foundUrls = crawler.getResults().getAllDistinctOf(Adress.class);
       List foundTexts = crawler.getResults().getAllDistinctOf(Text.class);
       List foundImages = crawler.getResults().getAllDistinctOf(ImageSource.class);
}



@Test
public void testGenericCrawler(){
       Crawler crawler = CrawlersFactory.createSpecialCrawler(CrawlerMode.Continous, CrawlerTarget.Generic);
       CrawlerConfiguration conf = new CrawlerConfiguration();
       conf.put(CrawlerParams.url, "http://kobietydokodu.pl/kategoria/niezbednik-juniora/");
       conf.put(CrawlerParams.timeLimit, 5);
       crawler.init(conf);
       List foundUrls = crawler.getResults().getAllDistinctOf(Adress.class);
       List foundTexts = crawler.getResults().getAllDistinctOf(Text.class);
       List foundImages = crawler.getResults().getAllDistinctOf(ImageSource.class);
}

@Test
public void testMultithreadCrawler(){
    Crawler crawler = CrawlersFactory.createSpecialCrawler(CrawlerMode.Multithread, CrawlerTarget.Generic);
       CrawlerConfiguration conf = new CrawlerConfiguration();
       conf.put(CrawlerParams.url, "http://kobietydokodu.pl/kategoria/niezbednik-juniora/");
       conf.put(CrawlerParams.timeLimit, 5);
       crawler.init(conf);
       List foundUrls = crawler.getResults().getAllDistinctOf(Adress.class);
       List foundTexts = crawler.getResults().getAllDistinctOf(Text.class);
       List foundImages = crawler.getResults().getAllDistinctOf(ImageSource.class);
}

@Test
public void testImagesCrawler(){
    Crawler crawler = CrawlersFactory.createSpecialCrawler(CrawlerMode.Continous, CrawlerTarget.Images);
       CrawlerConfiguration conf = new CrawlerConfiguration();
       conf.put(CrawlerParams.url, "http://kobietydokodu.pl/kategoria/niezbednik-juniora/");
       conf.put(CrawlerParams.timeLimit, 5);
       crawler.init(conf);
       List foundUrls = crawler.getResults().getAllDistinctOf(Adress.class);
       List foundTexts = crawler.getResults().getAllDistinctOf(Text.class);
       List foundImages = crawler.getResults().getAllDistinctOf(ImageSource.class);
}

@Test
public void testValidator(){
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Collector collector = new SentenceCollector(new String[]{"java"});
    Set set = validator.validate(collector);
    assertTrue(set.isEmpty());
}
}


