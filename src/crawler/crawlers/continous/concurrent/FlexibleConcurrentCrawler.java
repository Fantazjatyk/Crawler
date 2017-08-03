/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler.crawlers.continous.concurrent;

import crawler.configuration.CrawlerConfiguration;
import crawler.scrapping.SearchEngine;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class FlexibleConcurrentCrawler extends ConcurrentCrawler {

    @Override
    public void appendFilters(SearchEngine se, CrawlerConfiguration conf) {
        se.addCollectors(conf.getCollectors().values());
    }

}
