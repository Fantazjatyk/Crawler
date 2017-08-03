Example of usage:

        FlexibleOneShotCrawler crawler = new FlexibleOneShotCrawler();
        SentencesCollector c = new SentencesCollector();
        c.setTarget("Learn"); // tells crawler which words he should looking for.
        ImagesCollector i = new ImagesCollector();

        crawler.configure()
                .initUrl("https://www.w3schools.com/") // tells crawler where he should start his work.
                .addCollector(c) 
                .addCollector(i);
				
        crawler.start(); 